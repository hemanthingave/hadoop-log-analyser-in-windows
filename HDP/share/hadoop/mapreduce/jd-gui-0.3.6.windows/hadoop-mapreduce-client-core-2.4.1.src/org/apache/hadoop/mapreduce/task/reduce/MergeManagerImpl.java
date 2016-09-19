/*   1:    */ package org.apache.hadoop.mapreduce.task.reduce;
/*   2:    */ 
/*   3:    */ import com.google.common.annotations.VisibleForTesting;
/*   4:    */ import java.io.IOException;
/*   5:    */ import java.util.ArrayList;
/*   6:    */ import java.util.Collections;
/*   7:    */ import java.util.Comparator;
/*   8:    */ import java.util.List;
/*   9:    */ import java.util.Set;
/*  10:    */ import java.util.TreeSet;
/*  11:    */ import org.apache.commons.logging.Log;
/*  12:    */ import org.apache.commons.logging.LogFactory;
/*  13:    */ import org.apache.hadoop.classification.InterfaceAudience.LimitedPrivate;
/*  14:    */ import org.apache.hadoop.classification.InterfaceStability.Unstable;
/*  15:    */ import org.apache.hadoop.fs.ChecksumFileSystem;
/*  16:    */ import org.apache.hadoop.fs.FileStatus;
/*  17:    */ import org.apache.hadoop.fs.FileSystem;
/*  18:    */ import org.apache.hadoop.fs.LocalDirAllocator;
/*  19:    */ import org.apache.hadoop.fs.LocalFileSystem;
/*  20:    */ import org.apache.hadoop.fs.Path;
/*  21:    */ import org.apache.hadoop.io.DataInputBuffer;
/*  22:    */ import org.apache.hadoop.io.RawComparator;
/*  23:    */ import org.apache.hadoop.io.compress.CompressionCodec;
/*  24:    */ import org.apache.hadoop.mapred.Counters.Counter;
/*  25:    */ import org.apache.hadoop.mapred.IFile.Reader;
/*  26:    */ import org.apache.hadoop.mapred.IFile.Writer;
/*  27:    */ import org.apache.hadoop.mapred.JobConf;
/*  28:    */ import org.apache.hadoop.mapred.MapOutputFile;
/*  29:    */ import org.apache.hadoop.mapred.Merger;
/*  30:    */ import org.apache.hadoop.mapred.Merger.Segment;
/*  31:    */ import org.apache.hadoop.mapred.RawKeyValueIterator;
/*  32:    */ import org.apache.hadoop.mapred.Reducer;
/*  33:    */ import org.apache.hadoop.mapred.Reporter;
/*  34:    */ import org.apache.hadoop.mapred.Task;
/*  35:    */ import org.apache.hadoop.mapred.Task.CombineOutputCollector;
/*  36:    */ import org.apache.hadoop.mapred.Task.CombineValuesIterator;
/*  37:    */ import org.apache.hadoop.mapreduce.TaskAttemptID;
/*  38:    */ import org.apache.hadoop.mapreduce.TaskID;
/*  39:    */ import org.apache.hadoop.util.Progress;
/*  40:    */ import org.apache.hadoop.util.ReflectionUtils;
/*  41:    */ 
/*  42:    */ @InterfaceAudience.LimitedPrivate({"MapReduce"})
/*  43:    */ @InterfaceStability.Unstable
/*  44:    */ public class MergeManagerImpl<K, V>
/*  45:    */   implements MergeManager<K, V>
/*  46:    */ {
/*  47: 68 */   private static final Log LOG = LogFactory.getLog(MergeManagerImpl.class);
/*  48:    */   private static final float DEFAULT_SHUFFLE_MEMORY_LIMIT_PERCENT = 0.25F;
/*  49:    */   private final TaskAttemptID reduceId;
/*  50:    */   private final JobConf jobConf;
/*  51:    */   private final FileSystem localFS;
/*  52:    */   private final FileSystem rfs;
/*  53:    */   private final LocalDirAllocator localDirAllocator;
/*  54:    */   protected MapOutputFile mapOutputFile;
/*  55: 84 */   Set<InMemoryMapOutput<K, V>> inMemoryMergedMapOutputs = new TreeSet(new MapOutput.MapOutputComparator());
/*  56:    */   private MergeManagerImpl<K, V>.IntermediateMemoryToMemoryMerger memToMemMerger;
/*  57: 88 */   Set<InMemoryMapOutput<K, V>> inMemoryMapOutputs = new TreeSet(new MapOutput.MapOutputComparator());
/*  58:    */   private final MergeThread<InMemoryMapOutput<K, V>, K, V> inMemoryMerger;
/*  59: 92 */   Set<CompressAwarePath> onDiskMapOutputs = new TreeSet();
/*  60:    */   private final MergeManagerImpl<K, V>.OnDiskMerger onDiskMerger;
/*  61:    */   private final long memoryLimit;
/*  62:    */   private long usedMemory;
/*  63:    */   private long commitMemory;
/*  64:    */   private final long maxSingleShuffleLimit;
/*  65:    */   private final int memToMemMergeOutputsThreshold;
/*  66:    */   private final long mergeThreshold;
/*  67:    */   private final int ioSortFactor;
/*  68:    */   private final Reporter reporter;
/*  69:    */   private final ExceptionReporter exceptionReporter;
/*  70:    */   private final Class<? extends Reducer> combinerClass;
/*  71:    */   private final Task.CombineOutputCollector<K, V> combineCollector;
/*  72:    */   private final Counters.Counter spilledRecordsCounter;
/*  73:    */   private final Counters.Counter reduceCombineInputCounter;
/*  74:    */   private final Counters.Counter mergedMapOutputsCounter;
/*  75:    */   private final CompressionCodec codec;
/*  76:    */   private final Progress mergePhase;
/*  77:    */   
/*  78:    */   public MergeManagerImpl(TaskAttemptID reduceId, JobConf jobConf, FileSystem localFS, LocalDirAllocator localDirAllocator, Reporter reporter, CompressionCodec codec, Class<? extends Reducer> combinerClass, Task.CombineOutputCollector<K, V> combineCollector, Counters.Counter spilledRecordsCounter, Counters.Counter reduceCombineInputCounter, Counters.Counter mergedMapOutputsCounter, ExceptionReporter exceptionReporter, Progress mergePhase, MapOutputFile mapOutputFile)
/*  79:    */   {
/*  80:140 */     this.reduceId = reduceId;
/*  81:141 */     this.jobConf = jobConf;
/*  82:142 */     this.localDirAllocator = localDirAllocator;
/*  83:143 */     this.exceptionReporter = exceptionReporter;
/*  84:    */     
/*  85:145 */     this.reporter = reporter;
/*  86:146 */     this.codec = codec;
/*  87:147 */     this.combinerClass = combinerClass;
/*  88:148 */     this.combineCollector = combineCollector;
/*  89:149 */     this.reduceCombineInputCounter = reduceCombineInputCounter;
/*  90:150 */     this.spilledRecordsCounter = spilledRecordsCounter;
/*  91:151 */     this.mergedMapOutputsCounter = mergedMapOutputsCounter;
/*  92:152 */     this.mapOutputFile = mapOutputFile;
/*  93:153 */     this.mapOutputFile.setConf(jobConf);
/*  94:    */     
/*  95:155 */     this.localFS = localFS;
/*  96:156 */     this.rfs = ((LocalFileSystem)localFS).getRaw();
/*  97:    */     
/*  98:158 */     float maxInMemCopyUse = jobConf.getFloat("mapreduce.reduce.shuffle.input.buffer.percent", 0.9F);
/*  99:160 */     if ((maxInMemCopyUse > 1.0D) || (maxInMemCopyUse < 0.0D)) {
/* 100:161 */       throw new IllegalArgumentException("Invalid value for mapreduce.reduce.shuffle.input.buffer.percent: " + maxInMemCopyUse);
/* 101:    */     }
/* 102:167 */     this.memoryLimit = (((float)jobConf.getLong("mapreduce.reduce.memory.totalbytes", Math.min(Runtime.getRuntime().maxMemory(), 2147483647L)) * maxInMemCopyUse));
/* 103:    */     
/* 104:    */ 
/* 105:    */ 
/* 106:    */ 
/* 107:172 */     this.ioSortFactor = jobConf.getInt("mapreduce.task.io.sort.factor", 100);
/* 108:    */     
/* 109:174 */     float singleShuffleMemoryLimitPercent = jobConf.getFloat("mapreduce.reduce.shuffle.memory.limit.percent", 0.25F);
/* 110:177 */     if ((singleShuffleMemoryLimitPercent <= 0.0F) || (singleShuffleMemoryLimitPercent > 1.0F)) {
/* 111:179 */       throw new IllegalArgumentException("Invalid value for mapreduce.reduce.shuffle.memory.limit.percent: " + singleShuffleMemoryLimitPercent);
/* 112:    */     }
/* 113:184 */     this.usedMemory = 0L;
/* 114:185 */     this.commitMemory = 0L;
/* 115:186 */     this.maxSingleShuffleLimit = (((float)this.memoryLimit * singleShuffleMemoryLimitPercent));
/* 116:    */     
/* 117:188 */     this.memToMemMergeOutputsThreshold = jobConf.getInt("mapreduce.reduce.merge.memtomem.threshold", this.ioSortFactor);
/* 118:    */     
/* 119:190 */     this.mergeThreshold = (((float)this.memoryLimit * jobConf.getFloat("mapreduce.reduce.shuffle.merge.percent", 0.9F)));
/* 120:    */     
/* 121:    */ 
/* 122:193 */     LOG.info("MergerManager: memoryLimit=" + this.memoryLimit + ", " + "maxSingleShuffleLimit=" + this.maxSingleShuffleLimit + ", " + "mergeThreshold=" + this.mergeThreshold + ", " + "ioSortFactor=" + this.ioSortFactor + ", " + "memToMemMergeOutputsThreshold=" + this.memToMemMergeOutputsThreshold);
/* 123:199 */     if (this.maxSingleShuffleLimit >= this.mergeThreshold) {
/* 124:200 */       throw new RuntimeException("Invlaid configuration: maxSingleShuffleLimit should be less than mergeThresholdmaxSingleShuffleLimit: " + this.maxSingleShuffleLimit + "mergeThreshold: " + this.mergeThreshold);
/* 125:    */     }
/* 126:206 */     boolean allowMemToMemMerge = jobConf.getBoolean("mapreduce.reduce.merge.memtomem.enabled", false);
/* 127:208 */     if (allowMemToMemMerge)
/* 128:    */     {
/* 129:209 */       this.memToMemMerger = new IntermediateMemoryToMemoryMerger(this, this.memToMemMergeOutputsThreshold);
/* 130:    */       
/* 131:    */ 
/* 132:212 */       this.memToMemMerger.start();
/* 133:    */     }
/* 134:    */     else
/* 135:    */     {
/* 136:214 */       this.memToMemMerger = null;
/* 137:    */     }
/* 138:217 */     this.inMemoryMerger = createInMemoryMerger();
/* 139:218 */     this.inMemoryMerger.start();
/* 140:    */     
/* 141:220 */     this.onDiskMerger = new OnDiskMerger(this);
/* 142:221 */     this.onDiskMerger.start();
/* 143:    */     
/* 144:223 */     this.mergePhase = mergePhase;
/* 145:    */   }
/* 146:    */   
/* 147:    */   protected MergeThread<InMemoryMapOutput<K, V>, K, V> createInMemoryMerger()
/* 148:    */   {
/* 149:227 */     return new InMemoryMerger(this);
/* 150:    */   }
/* 151:    */   
/* 152:    */   TaskAttemptID getReduceId()
/* 153:    */   {
/* 154:231 */     return this.reduceId;
/* 155:    */   }
/* 156:    */   
/* 157:    */   @VisibleForTesting
/* 158:    */   ExceptionReporter getExceptionReporter()
/* 159:    */   {
/* 160:236 */     return this.exceptionReporter;
/* 161:    */   }
/* 162:    */   
/* 163:    */   public void waitForResource()
/* 164:    */     throws InterruptedException
/* 165:    */   {
/* 166:241 */     this.inMemoryMerger.waitForMerge();
/* 167:    */   }
/* 168:    */   
/* 169:    */   private boolean canShuffleToMemory(long requestedSize)
/* 170:    */   {
/* 171:245 */     return requestedSize < this.maxSingleShuffleLimit;
/* 172:    */   }
/* 173:    */   
/* 174:    */   public synchronized MapOutput<K, V> reserve(TaskAttemptID mapId, long requestedSize, int fetcher)
/* 175:    */     throws IOException
/* 176:    */   {
/* 177:253 */     if (!canShuffleToMemory(requestedSize))
/* 178:    */     {
/* 179:254 */       LOG.info(mapId + ": Shuffling to disk since " + requestedSize + " is greater than maxSingleShuffleLimit (" + this.maxSingleShuffleLimit + ")");
/* 180:    */       
/* 181:    */ 
/* 182:257 */       return new OnDiskMapOutput(mapId, this.reduceId, this, requestedSize, this.jobConf, this.mapOutputFile, fetcher, true);
/* 183:    */     }
/* 184:276 */     if (this.usedMemory > this.memoryLimit)
/* 185:    */     {
/* 186:277 */       LOG.debug(mapId + ": Stalling shuffle since usedMemory (" + this.usedMemory + ") is greater than memoryLimit (" + this.memoryLimit + ")." + " CommitMemory is (" + this.commitMemory + ")");
/* 187:    */       
/* 188:    */ 
/* 189:280 */       return null;
/* 190:    */     }
/* 191:284 */     LOG.debug(mapId + ": Proceeding with shuffle since usedMemory (" + this.usedMemory + ") is lesser than memoryLimit (" + this.memoryLimit + ")." + "CommitMemory is (" + this.commitMemory + ")");
/* 192:    */     
/* 193:    */ 
/* 194:287 */     return unconditionalReserve(mapId, requestedSize, true);
/* 195:    */   }
/* 196:    */   
/* 197:    */   private synchronized InMemoryMapOutput<K, V> unconditionalReserve(TaskAttemptID mapId, long requestedSize, boolean primaryMapOutput)
/* 198:    */   {
/* 199:296 */     this.usedMemory += requestedSize;
/* 200:297 */     return new InMemoryMapOutput(this.jobConf, mapId, this, (int)requestedSize, this.codec, primaryMapOutput);
/* 201:    */   }
/* 202:    */   
/* 203:    */   synchronized void unreserve(long size)
/* 204:    */   {
/* 205:302 */     this.usedMemory -= size;
/* 206:    */   }
/* 207:    */   
/* 208:    */   public synchronized void closeInMemoryFile(InMemoryMapOutput<K, V> mapOutput)
/* 209:    */   {
/* 210:306 */     this.inMemoryMapOutputs.add(mapOutput);
/* 211:307 */     LOG.info("closeInMemoryFile -> map-output of size: " + mapOutput.getSize() + ", inMemoryMapOutputs.size() -> " + this.inMemoryMapOutputs.size() + ", commitMemory -> " + this.commitMemory + ", usedMemory ->" + this.usedMemory);
/* 212:    */     
/* 213:    */ 
/* 214:    */ 
/* 215:311 */     this.commitMemory += mapOutput.getSize();
/* 216:314 */     if (this.commitMemory >= this.mergeThreshold)
/* 217:    */     {
/* 218:315 */       LOG.info("Starting inMemoryMerger's merge since commitMemory=" + this.commitMemory + " > mergeThreshold=" + this.mergeThreshold + ". Current usedMemory=" + this.usedMemory);
/* 219:    */       
/* 220:    */ 
/* 221:318 */       this.inMemoryMapOutputs.addAll(this.inMemoryMergedMapOutputs);
/* 222:319 */       this.inMemoryMergedMapOutputs.clear();
/* 223:320 */       this.inMemoryMerger.startMerge(this.inMemoryMapOutputs);
/* 224:321 */       this.commitMemory = 0L;
/* 225:    */     }
/* 226:324 */     if ((this.memToMemMerger != null) && 
/* 227:325 */       (this.inMemoryMapOutputs.size() >= this.memToMemMergeOutputsThreshold)) {
/* 228:326 */       this.memToMemMerger.startMerge(this.inMemoryMapOutputs);
/* 229:    */     }
/* 230:    */   }
/* 231:    */   
/* 232:    */   public synchronized void closeInMemoryMergedFile(InMemoryMapOutput<K, V> mapOutput)
/* 233:    */   {
/* 234:333 */     this.inMemoryMergedMapOutputs.add(mapOutput);
/* 235:334 */     LOG.info("closeInMemoryMergedFile -> size: " + mapOutput.getSize() + ", inMemoryMergedMapOutputs.size() -> " + this.inMemoryMergedMapOutputs.size());
/* 236:    */   }
/* 237:    */   
/* 238:    */   public synchronized void closeOnDiskFile(CompressAwarePath file)
/* 239:    */   {
/* 240:340 */     this.onDiskMapOutputs.add(file);
/* 241:342 */     if (this.onDiskMapOutputs.size() >= 2 * this.ioSortFactor - 1) {
/* 242:343 */       this.onDiskMerger.startMerge(this.onDiskMapOutputs);
/* 243:    */     }
/* 244:    */   }
/* 245:    */   
/* 246:    */   public RawKeyValueIterator close()
/* 247:    */     throws Throwable
/* 248:    */   {
/* 249:350 */     if (this.memToMemMerger != null) {
/* 250:351 */       this.memToMemMerger.close();
/* 251:    */     }
/* 252:353 */     this.inMemoryMerger.close();
/* 253:354 */     this.onDiskMerger.close();
/* 254:    */     
/* 255:356 */     List<InMemoryMapOutput<K, V>> memory = new ArrayList(this.inMemoryMergedMapOutputs);
/* 256:    */     
/* 257:358 */     this.inMemoryMergedMapOutputs.clear();
/* 258:359 */     memory.addAll(this.inMemoryMapOutputs);
/* 259:360 */     this.inMemoryMapOutputs.clear();
/* 260:361 */     List<CompressAwarePath> disk = new ArrayList(this.onDiskMapOutputs);
/* 261:362 */     this.onDiskMapOutputs.clear();
/* 262:363 */     return finalMerge(this.jobConf, this.rfs, memory, disk);
/* 263:    */   }
/* 264:    */   
/* 265:    */   private class IntermediateMemoryToMemoryMerger
/* 266:    */     extends MergeThread<InMemoryMapOutput<K, V>, K, V>
/* 267:    */   {
/* 268:    */     public IntermediateMemoryToMemoryMerger(int manager)
/* 269:    */     {
/* 270:371 */       super(mergeFactor, MergeManagerImpl.this.exceptionReporter);
/* 271:372 */       setName("InMemoryMerger - Thread to do in-memory merge of in-memory shuffled map-outputs");
/* 272:    */       
/* 273:374 */       setDaemon(true);
/* 274:    */     }
/* 275:    */     
/* 276:    */     public void merge(List<InMemoryMapOutput<K, V>> inputs)
/* 277:    */       throws IOException
/* 278:    */     {
/* 279:379 */       if ((inputs == null) || (inputs.size() == 0)) {
/* 280:380 */         return;
/* 281:    */       }
/* 282:383 */       TaskAttemptID dummyMapId = ((InMemoryMapOutput)inputs.get(0)).getMapId();
/* 283:384 */       List<Merger.Segment<K, V>> inMemorySegments = new ArrayList();
/* 284:385 */       long mergeOutputSize = MergeManagerImpl.this.createInMemorySegments(inputs, inMemorySegments, 0L);
/* 285:    */       
/* 286:387 */       int noInMemorySegments = inMemorySegments.size();
/* 287:    */       
/* 288:389 */       InMemoryMapOutput<K, V> mergedMapOutputs = MergeManagerImpl.this.unconditionalReserve(dummyMapId, mergeOutputSize, false);
/* 289:    */       
/* 290:    */ 
/* 291:392 */       IFile.Writer<K, V> writer = new InMemoryWriter(mergedMapOutputs.getArrayStream());
/* 292:    */       
/* 293:    */ 
/* 294:395 */       MergeManagerImpl.LOG.info("Initiating Memory-to-Memory merge with " + noInMemorySegments + " segments of total-size: " + mergeOutputSize);
/* 295:    */       
/* 296:    */ 
/* 297:398 */       RawKeyValueIterator rIter = Merger.merge(MergeManagerImpl.this.jobConf, MergeManagerImpl.this.rfs, MergeManagerImpl.this.jobConf.getMapOutputKeyClass(), MergeManagerImpl.this.jobConf.getMapOutputValueClass(), inMemorySegments, inMemorySegments.size(), new Path(MergeManagerImpl.this.reduceId.toString()), MergeManagerImpl.this.jobConf.getOutputKeyComparator(), MergeManagerImpl.this.reporter, null, null, null);
/* 298:    */       
/* 299:    */ 
/* 300:    */ 
/* 301:    */ 
/* 302:    */ 
/* 303:    */ 
/* 304:    */ 
/* 305:406 */       Merger.writeFile(rIter, writer, MergeManagerImpl.this.reporter, MergeManagerImpl.this.jobConf);
/* 306:407 */       writer.close();
/* 307:    */       
/* 308:409 */       MergeManagerImpl.LOG.info(MergeManagerImpl.this.reduceId + " Memory-to-Memory merge of the " + noInMemorySegments + " files in-memory complete.");
/* 309:    */       
/* 310:    */ 
/* 311:    */ 
/* 312:    */ 
/* 313:414 */       MergeManagerImpl.this.closeInMemoryMergedFile(mergedMapOutputs);
/* 314:    */     }
/* 315:    */   }
/* 316:    */   
/* 317:    */   private class InMemoryMerger
/* 318:    */     extends MergeThread<InMemoryMapOutput<K, V>, K, V>
/* 319:    */   {
/* 320:    */     public InMemoryMerger()
/* 321:    */     {
/* 322:421 */       super(2147483647, MergeManagerImpl.this.exceptionReporter);
/* 323:422 */       setName("InMemoryMerger - Thread to merge in-memory shuffled map-outputs");
/* 324:    */       
/* 325:424 */       setDaemon(true);
/* 326:    */     }
/* 327:    */     
/* 328:    */     public void merge(List<InMemoryMapOutput<K, V>> inputs)
/* 329:    */       throws IOException
/* 330:    */     {
/* 331:429 */       if ((inputs == null) || (inputs.size() == 0)) {
/* 332:430 */         return;
/* 333:    */       }
/* 334:442 */       TaskAttemptID mapId = ((InMemoryMapOutput)inputs.get(0)).getMapId();
/* 335:443 */       TaskID mapTaskId = mapId.getTaskID();
/* 336:    */       
/* 337:445 */       List<Merger.Segment<K, V>> inMemorySegments = new ArrayList();
/* 338:446 */       long mergeOutputSize = MergeManagerImpl.this.createInMemorySegments(inputs, inMemorySegments, 0L);
/* 339:    */       
/* 340:448 */       int noInMemorySegments = inMemorySegments.size();
/* 341:    */       
/* 342:450 */       Path outputPath = MergeManagerImpl.this.mapOutputFile.getInputFileForWrite(mapTaskId, mergeOutputSize).suffix(Task.MERGED_OUTPUT_PREFIX);
/* 343:    */       
/* 344:    */ 
/* 345:    */ 
/* 346:    */ 
/* 347:455 */       IFile.Writer<K, V> writer = new IFile.Writer(MergeManagerImpl.this.jobConf, MergeManagerImpl.this.rfs, outputPath, MergeManagerImpl.this.jobConf.getMapOutputKeyClass(), MergeManagerImpl.this.jobConf.getMapOutputValueClass(), MergeManagerImpl.this.codec, null);
/* 348:    */       
/* 349:    */ 
/* 350:    */ 
/* 351:    */ 
/* 352:    */ 
/* 353:461 */       RawKeyValueIterator rIter = null;
/* 354:    */       MergeManagerImpl.CompressAwarePath compressAwarePath;
/* 355:    */       try
/* 356:    */       {
/* 357:464 */         MergeManagerImpl.LOG.info("Initiating in-memory merge with " + noInMemorySegments + " segments...");
/* 358:    */         
/* 359:    */ 
/* 360:467 */         rIter = Merger.merge(MergeManagerImpl.this.jobConf, MergeManagerImpl.this.rfs, MergeManagerImpl.this.jobConf.getMapOutputKeyClass(), MergeManagerImpl.this.jobConf.getMapOutputValueClass(), inMemorySegments, inMemorySegments.size(), new Path(MergeManagerImpl.this.reduceId.toString()), MergeManagerImpl.this.jobConf.getOutputKeyComparator(), MergeManagerImpl.this.reporter, MergeManagerImpl.this.spilledRecordsCounter, null, null);
/* 361:475 */         if (null == MergeManagerImpl.this.combinerClass)
/* 362:    */         {
/* 363:476 */           Merger.writeFile(rIter, writer, MergeManagerImpl.this.reporter, MergeManagerImpl.this.jobConf);
/* 364:    */         }
/* 365:    */         else
/* 366:    */         {
/* 367:478 */           MergeManagerImpl.this.combineCollector.setWriter(writer);
/* 368:479 */           MergeManagerImpl.this.combineAndSpill(rIter, MergeManagerImpl.this.reduceCombineInputCounter);
/* 369:    */         }
/* 370:481 */         writer.close();
/* 371:482 */         compressAwarePath = new MergeManagerImpl.CompressAwarePath(outputPath, writer.getRawLength(), writer.getCompressedLength());
/* 372:    */         
/* 373:    */ 
/* 374:485 */         MergeManagerImpl.LOG.info(MergeManagerImpl.this.reduceId + " Merge of the " + noInMemorySegments + " files in-memory complete." + " Local file is " + outputPath + " of size " + MergeManagerImpl.this.localFS.getFileStatus(outputPath).getLen());
/* 375:    */       }
/* 376:    */       catch (IOException e)
/* 377:    */       {
/* 378:493 */         MergeManagerImpl.this.localFS.delete(outputPath, true);
/* 379:494 */         throw e;
/* 380:    */       }
/* 381:498 */       MergeManagerImpl.this.closeOnDiskFile(compressAwarePath);
/* 382:    */     }
/* 383:    */   }
/* 384:    */   
/* 385:    */   private class OnDiskMerger
/* 386:    */     extends MergeThread<MergeManagerImpl.CompressAwarePath, K, V>
/* 387:    */   {
/* 388:    */     public OnDiskMerger()
/* 389:    */     {
/* 390:506 */       super(MergeManagerImpl.this.ioSortFactor, MergeManagerImpl.this.exceptionReporter);
/* 391:507 */       setName("OnDiskMerger - Thread to merge on-disk map-outputs");
/* 392:508 */       setDaemon(true);
/* 393:    */     }
/* 394:    */     
/* 395:    */     public void merge(List<MergeManagerImpl.CompressAwarePath> inputs)
/* 396:    */       throws IOException
/* 397:    */     {
/* 398:514 */       if ((inputs == null) || (inputs.isEmpty()))
/* 399:    */       {
/* 400:515 */         MergeManagerImpl.LOG.info("No ondisk files to merge...");
/* 401:516 */         return;
/* 402:    */       }
/* 403:519 */       long approxOutputSize = 0L;
/* 404:520 */       int bytesPerSum = MergeManagerImpl.this.jobConf.getInt("io.bytes.per.checksum", 512);
/* 405:    */       
/* 406:    */ 
/* 407:523 */       MergeManagerImpl.LOG.info("OnDiskMerger: We have  " + inputs.size() + " map outputs on disk. Triggering merge...");
/* 408:527 */       for (MergeManagerImpl.CompressAwarePath file : inputs) {
/* 409:528 */         approxOutputSize += MergeManagerImpl.this.localFS.getFileStatus(file).getLen();
/* 410:    */       }
/* 411:532 */       approxOutputSize += ChecksumFileSystem.getChecksumLength(approxOutputSize, bytesPerSum);
/* 412:    */       
/* 413:    */ 
/* 414:    */ 
/* 415:536 */       Path outputPath = MergeManagerImpl.this.localDirAllocator.getLocalPathForWrite(((MergeManagerImpl.CompressAwarePath)inputs.get(0)).toString(), approxOutputSize, MergeManagerImpl.this.jobConf).suffix(Task.MERGED_OUTPUT_PREFIX);
/* 416:    */       
/* 417:    */ 
/* 418:539 */       IFile.Writer<K, V> writer = new IFile.Writer(MergeManagerImpl.this.jobConf, MergeManagerImpl.this.rfs, outputPath, MergeManagerImpl.this.jobConf.getMapOutputKeyClass(), MergeManagerImpl.this.jobConf.getMapOutputValueClass(), MergeManagerImpl.this.codec, null);
/* 419:    */       
/* 420:    */ 
/* 421:    */ 
/* 422:    */ 
/* 423:544 */       RawKeyValueIterator iter = null;
/* 424:    */       
/* 425:546 */       Path tmpDir = new Path(MergeManagerImpl.this.reduceId.toString());
/* 426:    */       MergeManagerImpl.CompressAwarePath compressAwarePath;
/* 427:    */       try
/* 428:    */       {
/* 429:548 */         iter = Merger.merge(MergeManagerImpl.this.jobConf, MergeManagerImpl.this.rfs, MergeManagerImpl.this.jobConf.getMapOutputKeyClass(), MergeManagerImpl.this.jobConf.getMapOutputValueClass(), MergeManagerImpl.this.codec, (Path[])inputs.toArray(new Path[inputs.size()]), true, MergeManagerImpl.this.ioSortFactor, tmpDir, MergeManagerImpl.this.jobConf.getOutputKeyComparator(), MergeManagerImpl.this.reporter, MergeManagerImpl.this.spilledRecordsCounter, null, MergeManagerImpl.this.mergedMapOutputsCounter, null);
/* 430:    */         
/* 431:    */ 
/* 432:    */ 
/* 433:    */ 
/* 434:    */ 
/* 435:    */ 
/* 436:    */ 
/* 437:    */ 
/* 438:557 */         Merger.writeFile(iter, writer, MergeManagerImpl.this.reporter, MergeManagerImpl.this.jobConf);
/* 439:558 */         writer.close();
/* 440:559 */         compressAwarePath = new MergeManagerImpl.CompressAwarePath(outputPath, writer.getRawLength(), writer.getCompressedLength());
/* 441:    */       }
/* 442:    */       catch (IOException e)
/* 443:    */       {
/* 444:562 */         MergeManagerImpl.this.localFS.delete(outputPath, true);
/* 445:563 */         throw e;
/* 446:    */       }
/* 447:566 */       MergeManagerImpl.this.closeOnDiskFile(compressAwarePath);
/* 448:    */       
/* 449:568 */       MergeManagerImpl.LOG.info(MergeManagerImpl.this.reduceId + " Finished merging " + inputs.size() + " map output files on disk of total-size " + approxOutputSize + "." + " Local output file is " + outputPath + " of size " + MergeManagerImpl.this.localFS.getFileStatus(outputPath).getLen());
/* 450:    */     }
/* 451:    */   }
/* 452:    */   
/* 453:    */   private void combineAndSpill(RawKeyValueIterator kvIter, Counters.Counter inCounter)
/* 454:    */     throws IOException
/* 455:    */   {
/* 456:580 */     JobConf job = this.jobConf;
/* 457:581 */     Reducer combiner = (Reducer)ReflectionUtils.newInstance(this.combinerClass, job);
/* 458:582 */     Class<K> keyClass = job.getMapOutputKeyClass();
/* 459:583 */     Class<V> valClass = job.getMapOutputValueClass();
/* 460:584 */     RawComparator<K> comparator = job.getCombinerKeyGroupingComparator();
/* 461:    */     try
/* 462:    */     {
/* 463:587 */       Task.CombineValuesIterator values = new Task.CombineValuesIterator(kvIter, comparator, keyClass, valClass, job, Reporter.NULL, inCounter);
/* 464:590 */       while (values.more())
/* 465:    */       {
/* 466:591 */         combiner.reduce(values.getKey(), values, this.combineCollector, Reporter.NULL);
/* 467:    */         
/* 468:593 */         values.nextKey();
/* 469:    */       }
/* 470:    */     }
/* 471:    */     finally
/* 472:    */     {
/* 473:596 */       combiner.close();
/* 474:    */     }
/* 475:    */   }
/* 476:    */   
/* 477:    */   private long createInMemorySegments(List<InMemoryMapOutput<K, V>> inMemoryMapOutputs, List<Merger.Segment<K, V>> inMemorySegments, long leaveBytes)
/* 478:    */     throws IOException
/* 479:    */   {
/* 480:604 */     long totalSize = 0L;
/* 481:    */     
/* 482:    */ 
/* 483:607 */     long fullSize = 0L;
/* 484:608 */     for (InMemoryMapOutput<K, V> mo : inMemoryMapOutputs) {
/* 485:609 */       fullSize += mo.getMemory().length;
/* 486:    */     }
/* 487:611 */     while (fullSize > leaveBytes)
/* 488:    */     {
/* 489:612 */       InMemoryMapOutput<K, V> mo = (InMemoryMapOutput)inMemoryMapOutputs.remove(0);
/* 490:613 */       byte[] data = mo.getMemory();
/* 491:614 */       long size = data.length;
/* 492:615 */       totalSize += size;
/* 493:616 */       fullSize -= size;
/* 494:617 */       IFile.Reader<K, V> reader = new InMemoryReader(this, mo.getMapId(), data, 0, (int)size, this.jobConf);
/* 495:    */       
/* 496:    */ 
/* 497:620 */       inMemorySegments.add(new Merger.Segment(reader, true, mo.isPrimaryMapOutput() ? this.mergedMapOutputsCounter : null));
/* 498:    */     }
/* 499:624 */     return totalSize;
/* 500:    */   }
/* 501:    */   
/* 502:    */   class RawKVIteratorReader
/* 503:    */     extends IFile.Reader<K, V>
/* 504:    */   {
/* 505:    */     private final RawKeyValueIterator kvIter;
/* 506:    */     
/* 507:    */     public RawKVIteratorReader(RawKeyValueIterator kvIter, long size)
/* 508:    */       throws IOException
/* 509:    */     {
/* 510:633 */       super(null, size, null, MergeManagerImpl.this.spilledRecordsCounter);
/* 511:634 */       this.kvIter = kvIter;
/* 512:    */     }
/* 513:    */     
/* 514:    */     public boolean nextRawKey(DataInputBuffer key)
/* 515:    */       throws IOException
/* 516:    */     {
/* 517:637 */       if (this.kvIter.next())
/* 518:    */       {
/* 519:638 */         DataInputBuffer kb = this.kvIter.getKey();
/* 520:639 */         int kp = kb.getPosition();
/* 521:640 */         int klen = kb.getLength() - kp;
/* 522:641 */         key.reset(kb.getData(), kp, klen);
/* 523:642 */         this.bytesRead += klen;
/* 524:643 */         return true;
/* 525:    */       }
/* 526:645 */       return false;
/* 527:    */     }
/* 528:    */     
/* 529:    */     public void nextRawValue(DataInputBuffer value)
/* 530:    */       throws IOException
/* 531:    */     {
/* 532:648 */       DataInputBuffer vb = this.kvIter.getValue();
/* 533:649 */       int vp = vb.getPosition();
/* 534:650 */       int vlen = vb.getLength() - vp;
/* 535:651 */       value.reset(vb.getData(), vp, vlen);
/* 536:652 */       this.bytesRead += vlen;
/* 537:    */     }
/* 538:    */     
/* 539:    */     public long getPosition()
/* 540:    */       throws IOException
/* 541:    */     {
/* 542:655 */       return this.bytesRead;
/* 543:    */     }
/* 544:    */     
/* 545:    */     public void close()
/* 546:    */       throws IOException
/* 547:    */     {
/* 548:659 */       this.kvIter.close();
/* 549:    */     }
/* 550:    */   }
/* 551:    */   
/* 552:    */   private RawKeyValueIterator finalMerge(JobConf job, FileSystem fs, List<InMemoryMapOutput<K, V>> inMemoryMapOutputs, List<CompressAwarePath> onDiskMapOutputs)
/* 553:    */     throws IOException
/* 554:    */   {
/* 555:667 */     LOG.info("finalMerge called with " + inMemoryMapOutputs.size() + " in-memory map-outputs and " + onDiskMapOutputs.size() + " on-disk map-outputs");
/* 556:    */     
/* 557:    */ 
/* 558:    */ 
/* 559:671 */     float maxRedPer = job.getFloat("mapreduce.reduce.input.buffer.percent", 0.0F);
/* 560:673 */     if ((maxRedPer > 1.0D) || (maxRedPer < 0.0D)) {
/* 561:674 */       throw new IOException("mapreduce.reduce.input.buffer.percent" + maxRedPer);
/* 562:    */     }
/* 563:677 */     int maxInMemReduce = (int)Math.min((float)Runtime.getRuntime().maxMemory() * maxRedPer, 2.147484E+009F);
/* 564:    */     
/* 565:    */ 
/* 566:    */ 
/* 567:    */ 
/* 568:682 */     Class<K> keyClass = job.getMapOutputKeyClass();
/* 569:683 */     Class<V> valueClass = job.getMapOutputValueClass();
/* 570:684 */     boolean keepInputs = job.getKeepFailedTaskFiles();
/* 571:685 */     Path tmpDir = new Path(this.reduceId.toString());
/* 572:686 */     RawComparator<K> comparator = job.getOutputKeyComparator();
/* 573:    */     
/* 574:    */ 
/* 575:    */ 
/* 576:690 */     List<Merger.Segment<K, V>> memDiskSegments = new ArrayList();
/* 577:691 */     long inMemToDiskBytes = 0L;
/* 578:692 */     boolean mergePhaseFinished = false;
/* 579:693 */     if (inMemoryMapOutputs.size() > 0)
/* 580:    */     {
/* 581:694 */       TaskID mapId = ((InMemoryMapOutput)inMemoryMapOutputs.get(0)).getMapId().getTaskID();
/* 582:695 */       inMemToDiskBytes = createInMemorySegments(inMemoryMapOutputs, memDiskSegments, maxInMemReduce);
/* 583:    */       
/* 584:    */ 
/* 585:698 */       int numMemDiskSegments = memDiskSegments.size();
/* 586:699 */       if ((numMemDiskSegments > 0) && (this.ioSortFactor > onDiskMapOutputs.size()))
/* 587:    */       {
/* 588:709 */         mergePhaseFinished = true;
/* 589:    */         
/* 590:711 */         Path outputPath = this.mapOutputFile.getInputFileForWrite(mapId, inMemToDiskBytes).suffix(Task.MERGED_OUTPUT_PREFIX);
/* 591:    */         
/* 592:    */ 
/* 593:    */ 
/* 594:715 */         RawKeyValueIterator rIter = Merger.merge(job, fs, keyClass, valueClass, memDiskSegments, numMemDiskSegments, tmpDir, comparator, this.reporter, this.spilledRecordsCounter, null, this.mergePhase);
/* 595:    */         
/* 596:    */ 
/* 597:    */ 
/* 598:719 */         IFile.Writer<K, V> writer = new IFile.Writer(job, fs, outputPath, keyClass, valueClass, this.codec, null);
/* 599:    */         try
/* 600:    */         {
/* 601:722 */           Merger.writeFile(rIter, writer, this.reporter, job);
/* 602:723 */           writer.close();
/* 603:724 */           onDiskMapOutputs.add(new CompressAwarePath(outputPath, writer.getRawLength(), writer.getCompressedLength()));
/* 604:    */           
/* 605:726 */           writer = null;
/* 606:738 */           if (null != writer) {
/* 607:739 */             writer.close();
/* 608:    */           }
/* 609:    */         }
/* 610:    */         catch (IOException e)
/* 611:    */         {
/* 612:729 */           if (null != outputPath) {
/* 613:    */             try
/* 614:    */             {
/* 615:731 */               fs.delete(outputPath, true);
/* 616:    */             }
/* 617:    */             catch (IOException ie) {}
/* 618:    */           }
/* 619:736 */           throw e;
/* 620:    */         }
/* 621:    */         finally
/* 622:    */         {
/* 623:738 */           if (null != writer) {
/* 624:739 */             writer.close();
/* 625:    */           }
/* 626:    */         }
/* 627:742 */         LOG.info("Merged " + numMemDiskSegments + " segments, " + inMemToDiskBytes + " bytes to disk to satisfy " + "reduce memory limit");
/* 628:    */         
/* 629:    */ 
/* 630:745 */         inMemToDiskBytes = 0L;
/* 631:746 */         memDiskSegments.clear();
/* 632:    */       }
/* 633:747 */       else if (inMemToDiskBytes != 0L)
/* 634:    */       {
/* 635:748 */         LOG.info("Keeping " + numMemDiskSegments + " segments, " + inMemToDiskBytes + " bytes in memory for " + "intermediate, on-disk merge");
/* 636:    */       }
/* 637:    */     }
/* 638:755 */     List<Merger.Segment<K, V>> diskSegments = new ArrayList();
/* 639:756 */     long onDiskBytes = inMemToDiskBytes;
/* 640:757 */     long rawBytes = inMemToDiskBytes;
/* 641:758 */     CompressAwarePath[] onDisk = (CompressAwarePath[])onDiskMapOutputs.toArray(new CompressAwarePath[onDiskMapOutputs.size()]);
/* 642:760 */     for (CompressAwarePath file : onDisk)
/* 643:    */     {
/* 644:761 */       long fileLength = fs.getFileStatus(file).getLen();
/* 645:762 */       onDiskBytes += fileLength;
/* 646:763 */       rawBytes += (file.getRawDataLength() > 0L ? file.getRawDataLength() : fileLength);
/* 647:    */       
/* 648:765 */       LOG.debug("Disk file: " + file + " Length is " + fileLength);
/* 649:766 */       diskSegments.add(new Merger.Segment(job, fs, file, this.codec, keepInputs, file.toString().endsWith(Task.MERGED_OUTPUT_PREFIX) ? null : this.mergedMapOutputsCounter, file.getRawDataLength()));
/* 650:    */     }
/* 651:772 */     LOG.info("Merging " + onDisk.length + " files, " + onDiskBytes + " bytes from disk");
/* 652:    */     
/* 653:774 */     Collections.sort(diskSegments, new Comparator()
/* 654:    */     {
/* 655:    */       public int compare(Merger.Segment<K, V> o1, Merger.Segment<K, V> o2)
/* 656:    */       {
/* 657:776 */         if (o1.getLength() == o2.getLength()) {
/* 658:777 */           return 0;
/* 659:    */         }
/* 660:779 */         return o1.getLength() < o2.getLength() ? -1 : 1;
/* 661:    */       }
/* 662:783 */     });
/* 663:784 */     List<Merger.Segment<K, V>> finalSegments = new ArrayList();
/* 664:785 */     long inMemBytes = createInMemorySegments(inMemoryMapOutputs, finalSegments, 0L);
/* 665:    */     
/* 666:787 */     LOG.info("Merging " + finalSegments.size() + " segments, " + inMemBytes + " bytes from memory into reduce");
/* 667:789 */     if (0L != onDiskBytes)
/* 668:    */     {
/* 669:790 */       int numInMemSegments = memDiskSegments.size();
/* 670:791 */       diskSegments.addAll(0, memDiskSegments);
/* 671:792 */       memDiskSegments.clear();
/* 672:    */       
/* 673:    */ 
/* 674:795 */       Progress thisPhase = mergePhaseFinished ? null : this.mergePhase;
/* 675:796 */       RawKeyValueIterator diskMerge = Merger.merge(job, fs, keyClass, valueClass, this.codec, diskSegments, this.ioSortFactor, numInMemSegments, tmpDir, comparator, this.reporter, false, this.spilledRecordsCounter, null, thisPhase);
/* 676:    */       
/* 677:    */ 
/* 678:    */ 
/* 679:800 */       diskSegments.clear();
/* 680:801 */       if (0 == finalSegments.size()) {
/* 681:802 */         return diskMerge;
/* 682:    */       }
/* 683:804 */       finalSegments.add(new Merger.Segment(new RawKVIteratorReader(diskMerge, onDiskBytes), true, rawBytes));
/* 684:    */     }
/* 685:807 */     return Merger.merge(job, fs, keyClass, valueClass, finalSegments, finalSegments.size(), tmpDir, comparator, this.reporter, this.spilledRecordsCounter, null, null);
/* 686:    */   }
/* 687:    */   
/* 688:    */   static class CompressAwarePath
/* 689:    */     extends Path
/* 690:    */   {
/* 691:    */     private long rawDataLength;
/* 692:    */     private long compressedSize;
/* 693:    */     
/* 694:    */     public CompressAwarePath(Path path, long rawDataLength, long compressSize)
/* 695:    */     {
/* 696:819 */       super();
/* 697:820 */       this.rawDataLength = rawDataLength;
/* 698:821 */       this.compressedSize = compressSize;
/* 699:    */     }
/* 700:    */     
/* 701:    */     public long getRawDataLength()
/* 702:    */     {
/* 703:825 */       return this.rawDataLength;
/* 704:    */     }
/* 705:    */     
/* 706:    */     public long getCompressedSize()
/* 707:    */     {
/* 708:829 */       return this.compressedSize;
/* 709:    */     }
/* 710:    */     
/* 711:    */     public boolean equals(Object other)
/* 712:    */     {
/* 713:834 */       return super.equals(other);
/* 714:    */     }
/* 715:    */     
/* 716:    */     public int hashCode()
/* 717:    */     {
/* 718:839 */       return super.hashCode();
/* 719:    */     }
/* 720:    */     
/* 721:    */     public int compareTo(Object obj)
/* 722:    */     {
/* 723:844 */       if ((obj instanceof CompressAwarePath))
/* 724:    */       {
/* 725:845 */         CompressAwarePath compPath = (CompressAwarePath)obj;
/* 726:846 */         if (this.compressedSize < compPath.getCompressedSize()) {
/* 727:847 */           return -1;
/* 728:    */         }
/* 729:848 */         if (getCompressedSize() > compPath.getCompressedSize()) {
/* 730:849 */           return 1;
/* 731:    */         }
/* 732:    */       }
/* 733:854 */       return super.compareTo(obj);
/* 734:    */     }
/* 735:    */   }
/* 736:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.task.reduce.MergeManagerImpl
 * JD-Core Version:    0.7.0.1
 */