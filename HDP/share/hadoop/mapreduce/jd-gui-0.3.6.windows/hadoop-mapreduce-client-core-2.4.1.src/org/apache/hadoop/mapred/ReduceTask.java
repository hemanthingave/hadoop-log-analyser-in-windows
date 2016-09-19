/*   1:    */ package org.apache.hadoop.mapred;
/*   2:    */ 
/*   3:    */ import java.io.Closeable;
/*   4:    */ import java.io.DataInput;
/*   5:    */ import java.io.DataOutput;
/*   6:    */ import java.io.IOException;
/*   7:    */ import java.util.ArrayList;
/*   8:    */ import java.util.Comparator;
/*   9:    */ import java.util.List;
/*  10:    */ import java.util.Map;
/*  11:    */ import java.util.SortedSet;
/*  12:    */ import java.util.TreeSet;
/*  13:    */ import org.apache.commons.logging.Log;
/*  14:    */ import org.apache.commons.logging.LogFactory;
/*  15:    */ import org.apache.hadoop.classification.InterfaceAudience.Private;
/*  16:    */ import org.apache.hadoop.classification.InterfaceStability.Unstable;
/*  17:    */ import org.apache.hadoop.conf.Configuration;
/*  18:    */ import org.apache.hadoop.fs.FileStatus;
/*  19:    */ import org.apache.hadoop.fs.FileSystem;
/*  20:    */ import org.apache.hadoop.fs.FileSystem.Statistics;
/*  21:    */ import org.apache.hadoop.fs.Path;
/*  22:    */ import org.apache.hadoop.io.DataInputBuffer;
/*  23:    */ import org.apache.hadoop.io.IOUtils;
/*  24:    */ import org.apache.hadoop.io.RawComparator;
/*  25:    */ import org.apache.hadoop.io.SequenceFile;
/*  26:    */ import org.apache.hadoop.io.SequenceFile.CompressionType;
/*  27:    */ import org.apache.hadoop.io.SequenceFile.Writer;
/*  28:    */ import org.apache.hadoop.io.Writable;
/*  29:    */ import org.apache.hadoop.io.WritableFactories;
/*  30:    */ import org.apache.hadoop.io.WritableFactory;
/*  31:    */ import org.apache.hadoop.io.compress.CompressionCodec;
/*  32:    */ import org.apache.hadoop.io.compress.DefaultCodec;
/*  33:    */ import org.apache.hadoop.mapreduce.Counter;
/*  34:    */ import org.apache.hadoop.mapreduce.Reducer.Context;
/*  35:    */ import org.apache.hadoop.mapreduce.TaskAttemptContext;
/*  36:    */ import org.apache.hadoop.mapreduce.TaskCounter;
/*  37:    */ import org.apache.hadoop.mapreduce.lib.output.FileOutputFormatCounter;
/*  38:    */ import org.apache.hadoop.mapreduce.task.TaskAttemptContextImpl;
/*  39:    */ import org.apache.hadoop.mapreduce.task.reduce.Shuffle;
/*  40:    */ import org.apache.hadoop.util.Progress;
/*  41:    */ import org.apache.hadoop.util.Progressable;
/*  42:    */ import org.apache.hadoop.util.ReflectionUtils;
/*  43:    */ 
/*  44:    */ @InterfaceAudience.Private
/*  45:    */ @InterfaceStability.Unstable
/*  46:    */ public class ReduceTask
/*  47:    */   extends Task
/*  48:    */ {
/*  49:    */   static
/*  50:    */   {
/*  51: 66 */     WritableFactories.setFactory(ReduceTask.class, new WritableFactory()
/*  52:    */     {
/*  53:    */       public Writable newInstance()
/*  54:    */       {
/*  55: 69 */         return new ReduceTask();
/*  56:    */       }
/*  57:    */     });
/*  58:    */   }
/*  59:    */   
/*  60: 73 */   private static final Log LOG = LogFactory.getLog(ReduceTask.class.getName());
/*  61:    */   private int numMaps;
/*  62:    */   private CompressionCodec codec;
/*  63:    */   private Map<TaskAttemptID, MapOutputFile> localMapFiles;
/*  64:    */   private Progress copyPhase;
/*  65:    */   private Progress sortPhase;
/*  66:    */   private Progress reducePhase;
/*  67:    */   private Counters.Counter shuffledMapsCounter;
/*  68:    */   private Counters.Counter reduceShuffleBytes;
/*  69:    */   private Counters.Counter reduceInputKeyCounter;
/*  70:    */   private Counters.Counter reduceInputValueCounter;
/*  71:    */   private Counters.Counter reduceOutputCounter;
/*  72:    */   private Counters.Counter reduceCombineInputCounter;
/*  73:    */   private Counters.Counter reduceCombineOutputCounter;
/*  74:    */   private Counters.Counter fileOutputByteCounter;
/*  75:    */   private Comparator<FileStatus> mapOutputFileComparator;
/*  76:    */   private final SortedSet<FileStatus> mapOutputFilesOnDisk;
/*  77:    */   
/*  78:    */   public ReduceTask()
/*  79:    */   {
/*  80: 84 */     getProgress().setStatus("reduce");
/*  81: 85 */     setPhase(TaskStatus.Phase.SHUFFLE);
/*  82:    */     
/*  83:    */ 
/*  84:    */ 
/*  85:    */ 
/*  86:    */ 
/*  87: 91 */     this.shuffledMapsCounter = ((Counters.Counter)getCounters().findCounter(TaskCounter.SHUFFLED_MAPS));
/*  88:    */     
/*  89: 93 */     this.reduceShuffleBytes = ((Counters.Counter)getCounters().findCounter(TaskCounter.REDUCE_SHUFFLE_BYTES));
/*  90:    */     
/*  91: 95 */     this.reduceInputKeyCounter = ((Counters.Counter)getCounters().findCounter(TaskCounter.REDUCE_INPUT_GROUPS));
/*  92:    */     
/*  93: 97 */     this.reduceInputValueCounter = ((Counters.Counter)getCounters().findCounter(TaskCounter.REDUCE_INPUT_RECORDS));
/*  94:    */     
/*  95: 99 */     this.reduceOutputCounter = ((Counters.Counter)getCounters().findCounter(TaskCounter.REDUCE_OUTPUT_RECORDS));
/*  96:    */     
/*  97:101 */     this.reduceCombineInputCounter = ((Counters.Counter)getCounters().findCounter(TaskCounter.COMBINE_INPUT_RECORDS));
/*  98:    */     
/*  99:103 */     this.reduceCombineOutputCounter = ((Counters.Counter)getCounters().findCounter(TaskCounter.COMBINE_OUTPUT_RECORDS));
/* 100:    */     
/* 101:105 */     this.fileOutputByteCounter = ((Counters.Counter)getCounters().findCounter(FileOutputFormatCounter.BYTES_WRITTEN));
/* 102:    */     
/* 103:    */ 
/* 104:    */ 
/* 105:    */ 
/* 106:    */ 
/* 107:    */ 
/* 108:112 */     this.mapOutputFileComparator = new Comparator()
/* 109:    */     {
/* 110:    */       public int compare(FileStatus a, FileStatus b)
/* 111:    */       {
/* 112:115 */         if (a.getLen() < b.getLen()) {
/* 113:116 */           return -1;
/* 114:    */         }
/* 115:117 */         if (a.getLen() == b.getLen())
/* 116:    */         {
/* 117:118 */           if (a.getPath().toString().equals(b.getPath().toString())) {
/* 118:119 */             return 0;
/* 119:    */           }
/* 120:121 */           return -1;
/* 121:    */         }
/* 122:123 */         return 1;
/* 123:    */       }
/* 124:127 */     };
/* 125:128 */     this.mapOutputFilesOnDisk = new TreeSet(this.mapOutputFileComparator);
/* 126:    */   }
/* 127:    */   
/* 128:    */   public ReduceTask(String jobFile, TaskAttemptID taskId, int partition, int numMaps, int numSlotsRequired)
/* 129:    */   {
/* 130:137 */     super(jobFile, taskId, partition, numSlotsRequired);getProgress().setStatus("reduce");setPhase(TaskStatus.Phase.SHUFFLE);this.shuffledMapsCounter = ((Counters.Counter)getCounters().findCounter(TaskCounter.SHUFFLED_MAPS));this.reduceShuffleBytes = ((Counters.Counter)getCounters().findCounter(TaskCounter.REDUCE_SHUFFLE_BYTES));this.reduceInputKeyCounter = ((Counters.Counter)getCounters().findCounter(TaskCounter.REDUCE_INPUT_GROUPS));this.reduceInputValueCounter = ((Counters.Counter)getCounters().findCounter(TaskCounter.REDUCE_INPUT_RECORDS));this.reduceOutputCounter = ((Counters.Counter)getCounters().findCounter(TaskCounter.REDUCE_OUTPUT_RECORDS));this.reduceCombineInputCounter = ((Counters.Counter)getCounters().findCounter(TaskCounter.COMBINE_INPUT_RECORDS));this.reduceCombineOutputCounter = ((Counters.Counter)getCounters().findCounter(TaskCounter.COMBINE_OUTPUT_RECORDS));this.fileOutputByteCounter = ((Counters.Counter)getCounters().findCounter(FileOutputFormatCounter.BYTES_WRITTEN));this.mapOutputFileComparator = new Comparator()
/* 131:    */     {
/* 132:    */       public int compare(FileStatus a, FileStatus b)
/* 133:    */       {
/* 134:115 */         if (a.getLen() < b.getLen()) {
/* 135:116 */           return -1;
/* 136:    */         }
/* 137:117 */         if (a.getLen() == b.getLen())
/* 138:    */         {
/* 139:118 */           if (a.getPath().toString().equals(b.getPath().toString())) {
/* 140:119 */             return 0;
/* 141:    */           }
/* 142:121 */           return -1;
/* 143:    */         }
/* 144:123 */         return 1;
/* 145:    */       }
/* 146:128 */     };this.mapOutputFilesOnDisk = new TreeSet(this.mapOutputFileComparator);
/* 147:    */     
/* 148:    */ 
/* 149:    */ 
/* 150:    */ 
/* 151:    */ 
/* 152:    */ 
/* 153:    */ 
/* 154:    */ 
/* 155:    */ 
/* 156:138 */     this.numMaps = numMaps;
/* 157:    */   }
/* 158:    */   
/* 159:    */   public void setLocalMapFiles(Map<TaskAttemptID, MapOutputFile> mapFiles)
/* 160:    */   {
/* 161:149 */     this.localMapFiles = mapFiles;
/* 162:    */   }
/* 163:    */   
/* 164:    */   private CompressionCodec initCodec()
/* 165:    */   {
/* 166:154 */     if (this.conf.getCompressMapOutput())
/* 167:    */     {
/* 168:155 */       Class<? extends CompressionCodec> codecClass = this.conf.getMapOutputCompressorClass(DefaultCodec.class);
/* 169:    */       
/* 170:157 */       return (CompressionCodec)ReflectionUtils.newInstance(codecClass, this.conf);
/* 171:    */     }
/* 172:160 */     return null;
/* 173:    */   }
/* 174:    */   
/* 175:    */   public boolean isMapTask()
/* 176:    */   {
/* 177:165 */     return false;
/* 178:    */   }
/* 179:    */   
/* 180:    */   public int getNumMaps()
/* 181:    */   {
/* 182:168 */     return this.numMaps;
/* 183:    */   }
/* 184:    */   
/* 185:    */   public void localizeConfiguration(JobConf conf)
/* 186:    */     throws IOException
/* 187:    */   {
/* 188:175 */     super.localizeConfiguration(conf);
/* 189:176 */     conf.setNumMapTasks(this.numMaps);
/* 190:    */   }
/* 191:    */   
/* 192:    */   public void write(DataOutput out)
/* 193:    */     throws IOException
/* 194:    */   {
/* 195:181 */     super.write(out);
/* 196:    */     
/* 197:183 */     out.writeInt(this.numMaps);
/* 198:    */   }
/* 199:    */   
/* 200:    */   public void readFields(DataInput in)
/* 201:    */     throws IOException
/* 202:    */   {
/* 203:188 */     super.readFields(in);
/* 204:    */     
/* 205:190 */     this.numMaps = in.readInt();
/* 206:    */   }
/* 207:    */   
/* 208:    */   private Path[] getMapFiles(FileSystem fs)
/* 209:    */     throws IOException
/* 210:    */   {
/* 211:195 */     List<Path> fileList = new ArrayList();
/* 212:196 */     for (int i = 0; i < this.numMaps; i++) {
/* 213:197 */       fileList.add(this.mapOutputFile.getInputFile(i));
/* 214:    */     }
/* 215:199 */     return (Path[])fileList.toArray(new Path[0]);
/* 216:    */   }
/* 217:    */   
/* 218:    */   private class ReduceValuesIterator<KEY, VALUE>
/* 219:    */     extends Task.ValuesIterator<KEY, VALUE>
/* 220:    */   {
/* 221:    */     public ReduceValuesIterator(RawComparator<KEY> in, Class<KEY> comparator, Class<VALUE> keyClass, Configuration valClass, Progressable conf)
/* 222:    */       throws IOException
/* 223:    */     {
/* 224:210 */       super(comparator, keyClass, valClass, conf, reporter);
/* 225:    */     }
/* 226:    */     
/* 227:    */     public VALUE next()
/* 228:    */     {
/* 229:215 */       ReduceTask.this.reduceInputValueCounter.increment(1L);
/* 230:216 */       return moveToNext();
/* 231:    */     }
/* 232:    */     
/* 233:    */     protected VALUE moveToNext()
/* 234:    */     {
/* 235:220 */       return super.next();
/* 236:    */     }
/* 237:    */     
/* 238:    */     public void informReduceProgress()
/* 239:    */     {
/* 240:224 */       ReduceTask.this.reducePhase.set(this.in.getProgress().getProgress());
/* 241:225 */       this.reporter.progress();
/* 242:    */     }
/* 243:    */   }
/* 244:    */   
/* 245:    */   private class SkippingReduceValuesIterator<KEY, VALUE>
/* 246:    */     extends ReduceTask.ReduceValuesIterator<KEY, VALUE>
/* 247:    */   {
/* 248:    */     private SortedRanges.SkipRangeIterator skipIt;
/* 249:    */     private TaskUmbilicalProtocol umbilical;
/* 250:    */     private Counters.Counter skipGroupCounter;
/* 251:    */     private Counters.Counter skipRecCounter;
/* 252:235 */     private long grpIndex = -1L;
/* 253:    */     private Class<KEY> keyClass;
/* 254:    */     private Class<VALUE> valClass;
/* 255:    */     private SequenceFile.Writer skipWriter;
/* 256:    */     private boolean toWriteSkipRecs;
/* 257:    */     private boolean hasNext;
/* 258:    */     private Task.TaskReporter reporter;
/* 259:    */     
/* 260:    */     public SkippingReduceValuesIterator(RawComparator<KEY> in, Class<KEY> comparator, Class<VALUE> keyClass, Configuration valClass, Task.TaskReporter conf, TaskUmbilicalProtocol reporter)
/* 261:    */       throws IOException
/* 262:    */     {
/* 263:247 */       super(in, comparator, keyClass, valClass, conf, reporter);
/* 264:248 */       this.umbilical = umbilical;
/* 265:249 */       this.skipGroupCounter = reporter.getCounter(TaskCounter.REDUCE_SKIPPED_GROUPS);
/* 266:    */       
/* 267:251 */       this.skipRecCounter = reporter.getCounter(TaskCounter.REDUCE_SKIPPED_RECORDS);
/* 268:    */       
/* 269:253 */       this.toWriteSkipRecs = ((ReduceTask.this.toWriteSkipRecs()) && (SkipBadRecords.getSkipOutputPath(conf) != null));
/* 270:    */       
/* 271:255 */       this.keyClass = keyClass;
/* 272:256 */       this.valClass = valClass;
/* 273:257 */       this.reporter = reporter;
/* 274:258 */       this.skipIt = ReduceTask.this.getSkipRanges().skipRangeIterator();
/* 275:259 */       mayBeSkip();
/* 276:    */     }
/* 277:    */     
/* 278:    */     public void nextKey()
/* 279:    */       throws IOException
/* 280:    */     {
/* 281:263 */       super.nextKey();
/* 282:264 */       mayBeSkip();
/* 283:    */     }
/* 284:    */     
/* 285:    */     public boolean more()
/* 286:    */     {
/* 287:268 */       return (super.more()) && (this.hasNext);
/* 288:    */     }
/* 289:    */     
/* 290:    */     private void mayBeSkip()
/* 291:    */       throws IOException
/* 292:    */     {
/* 293:272 */       this.hasNext = this.skipIt.hasNext();
/* 294:273 */       if (!this.hasNext)
/* 295:    */       {
/* 296:274 */         ReduceTask.LOG.warn("Further groups got skipped.");
/* 297:275 */         return;
/* 298:    */       }
/* 299:277 */       this.grpIndex += 1L;
/* 300:278 */       long nextGrpIndex = this.skipIt.next().longValue();
/* 301:279 */       long skip = 0L;
/* 302:280 */       long skipRec = 0L;
/* 303:281 */       while ((this.grpIndex < nextGrpIndex) && (super.more()))
/* 304:    */       {
/* 305:282 */         while (hasNext())
/* 306:    */         {
/* 307:283 */           VALUE value = moveToNext();
/* 308:284 */           if (this.toWriteSkipRecs) {
/* 309:285 */             writeSkippedRec(getKey(), value);
/* 310:    */           }
/* 311:287 */           skipRec += 1L;
/* 312:    */         }
/* 313:289 */         super.nextKey();
/* 314:290 */         this.grpIndex += 1L;
/* 315:291 */         skip += 1L;
/* 316:    */       }
/* 317:295 */       if ((skip > 0L) && (this.skipIt.skippedAllRanges()) && (this.skipWriter != null)) {
/* 318:296 */         this.skipWriter.close();
/* 319:    */       }
/* 320:298 */       this.skipGroupCounter.increment(skip);
/* 321:299 */       this.skipRecCounter.increment(skipRec);
/* 322:300 */       ReduceTask.this.reportNextRecordRange(this.umbilical, this.grpIndex);
/* 323:    */     }
/* 324:    */     
/* 325:    */     private void writeSkippedRec(KEY key, VALUE value)
/* 326:    */       throws IOException
/* 327:    */     {
/* 328:305 */       if (this.skipWriter == null)
/* 329:    */       {
/* 330:306 */         Path skipDir = SkipBadRecords.getSkipOutputPath(ReduceTask.this.conf);
/* 331:307 */         Path skipFile = new Path(skipDir, ReduceTask.this.getTaskID().toString());
/* 332:308 */         this.skipWriter = SequenceFile.createWriter(skipFile.getFileSystem(ReduceTask.this.conf), ReduceTask.this.conf, skipFile, this.keyClass, this.valClass, SequenceFile.CompressionType.BLOCK, this.reporter);
/* 333:    */       }
/* 334:313 */       this.skipWriter.append(key, value);
/* 335:    */     }
/* 336:    */   }
/* 337:    */   
/* 338:    */   public void run(JobConf job, TaskUmbilicalProtocol umbilical)
/* 339:    */     throws IOException, InterruptedException, ClassNotFoundException
/* 340:    */   {
/* 341:321 */     job.setBoolean("mapreduce.job.skiprecords", isSkipping());
/* 342:323 */     if (isMapOrReduce())
/* 343:    */     {
/* 344:324 */       this.copyPhase = getProgress().addPhase("copy");
/* 345:325 */       this.sortPhase = getProgress().addPhase("sort");
/* 346:326 */       this.reducePhase = getProgress().addPhase("reduce");
/* 347:    */     }
/* 348:329 */     Task.TaskReporter reporter = startReporter(umbilical);
/* 349:    */     
/* 350:331 */     boolean useNewApi = job.getUseNewReducer();
/* 351:332 */     initialize(job, getJobID(), reporter, useNewApi);
/* 352:335 */     if (this.jobCleanup)
/* 353:    */     {
/* 354:336 */       runJobCleanupTask(umbilical, reporter);
/* 355:337 */       return;
/* 356:    */     }
/* 357:339 */     if (this.jobSetup)
/* 358:    */     {
/* 359:340 */       runJobSetupTask(umbilical, reporter);
/* 360:341 */       return;
/* 361:    */     }
/* 362:343 */     if (this.taskCleanup)
/* 363:    */     {
/* 364:344 */       runTaskCleanupTask(umbilical, reporter);
/* 365:345 */       return;
/* 366:    */     }
/* 367:349 */     this.codec = initCodec();
/* 368:350 */     RawKeyValueIterator rIter = null;
/* 369:351 */     ShuffleConsumerPlugin shuffleConsumerPlugin = null;
/* 370:    */     
/* 371:353 */     Class combinerClass = this.conf.getCombinerClass();
/* 372:354 */     Task.CombineOutputCollector combineCollector = null != combinerClass ? new Task.CombineOutputCollector(this.reduceCombineOutputCounter, reporter, this.conf) : null;
/* 373:    */     
/* 374:    */ 
/* 375:    */ 
/* 376:358 */     Class<? extends ShuffleConsumerPlugin> clazz = job.getClass("mapreduce.job.reduce.shuffle.consumer.plugin.class", Shuffle.class, ShuffleConsumerPlugin.class);
/* 377:    */     
/* 378:    */ 
/* 379:361 */     shuffleConsumerPlugin = (ShuffleConsumerPlugin)ReflectionUtils.newInstance(clazz, job);
/* 380:362 */     LOG.info("Using ShuffleConsumerPlugin: " + shuffleConsumerPlugin);
/* 381:    */     
/* 382:364 */     ShuffleConsumerPlugin.Context shuffleContext = new ShuffleConsumerPlugin.Context(getTaskID(), job, FileSystem.getLocal(job), umbilical, this.lDirAlloc, reporter, this.codec, combinerClass, combineCollector, this.spilledRecordsCounter, this.reduceCombineInputCounter, this.shuffledMapsCounter, this.reduceShuffleBytes, this.failedShuffleCounter, this.mergedMapOutputsCounter, this.taskStatus, this.copyPhase, this.sortPhase, this, this.mapOutputFile, this.localMapFiles);
/* 383:    */     
/* 384:    */ 
/* 385:    */ 
/* 386:    */ 
/* 387:    */ 
/* 388:    */ 
/* 389:    */ 
/* 390:    */ 
/* 391:    */ 
/* 392:374 */     shuffleConsumerPlugin.init(shuffleContext);
/* 393:    */     
/* 394:376 */     rIter = shuffleConsumerPlugin.run();
/* 395:    */     
/* 396:    */ 
/* 397:379 */     this.mapOutputFilesOnDisk.clear();
/* 398:    */     
/* 399:381 */     this.sortPhase.complete();
/* 400:382 */     setPhase(TaskStatus.Phase.REDUCE);
/* 401:383 */     statusUpdate(umbilical);
/* 402:384 */     Class keyClass = job.getMapOutputKeyClass();
/* 403:385 */     Class valueClass = job.getMapOutputValueClass();
/* 404:386 */     RawComparator comparator = job.getOutputValueGroupingComparator();
/* 405:388 */     if (useNewApi) {
/* 406:389 */       runNewReducer(job, umbilical, reporter, rIter, comparator, keyClass, valueClass);
/* 407:    */     } else {
/* 408:392 */       runOldReducer(job, umbilical, reporter, rIter, comparator, keyClass, valueClass);
/* 409:    */     }
/* 410:396 */     shuffleConsumerPlugin.close();
/* 411:397 */     done(umbilical, reporter);
/* 412:    */   }
/* 413:    */   
/* 414:    */   private <INKEY, INVALUE, OUTKEY, OUTVALUE> void runOldReducer(JobConf job, TaskUmbilicalProtocol umbilical, final Task.TaskReporter reporter, RawKeyValueIterator rIter, RawComparator<INKEY> comparator, Class<INKEY> keyClass, Class<INVALUE> valueClass)
/* 415:    */     throws IOException
/* 416:    */   {
/* 417:409 */     Reducer<INKEY, INVALUE, OUTKEY, OUTVALUE> reducer = (Reducer)ReflectionUtils.newInstance(job.getReducerClass(), job);
/* 418:    */     
/* 419:    */ 
/* 420:412 */     String finalName = getOutputName(getPartition());
/* 421:    */     
/* 422:414 */     RecordWriter<OUTKEY, OUTVALUE> out = new OldTrackingRecordWriter(this, job, reporter, finalName);
/* 423:    */     
/* 424:416 */     final RecordWriter<OUTKEY, OUTVALUE> finalOut = out;
/* 425:    */     
/* 426:418 */     OutputCollector<OUTKEY, OUTVALUE> collector = new OutputCollector()
/* 427:    */     {
/* 428:    */       public void collect(OUTKEY key, OUTVALUE value)
/* 429:    */         throws IOException
/* 430:    */       {
/* 431:422 */         finalOut.write(key, value);
/* 432:    */         
/* 433:424 */         reporter.progress();
/* 434:    */       }
/* 435:    */     };
/* 436:    */     try
/* 437:    */     {
/* 438:431 */       boolean incrProcCount = (SkipBadRecords.getReducerMaxSkipGroups(job) > 0L) && (SkipBadRecords.getAutoIncrReducerProcCount(job));
/* 439:    */       
/* 440:    */ 
/* 441:434 */       ReduceValuesIterator<INKEY, INVALUE> values = isSkipping() ? new SkippingReduceValuesIterator(rIter, comparator, keyClass, valueClass, job, reporter, umbilical) : new ReduceValuesIterator(rIter, job.getOutputValueGroupingComparator(), keyClass, valueClass, job, reporter);
/* 442:    */       
/* 443:    */ 
/* 444:    */ 
/* 445:    */ 
/* 446:    */ 
/* 447:    */ 
/* 448:441 */       values.informReduceProgress();
/* 449:442 */       while (values.more())
/* 450:    */       {
/* 451:443 */         this.reduceInputKeyCounter.increment(1L);
/* 452:444 */         reducer.reduce(values.getKey(), values, collector, reporter);
/* 453:445 */         if (incrProcCount) {
/* 454:446 */           reporter.incrCounter("SkippingTaskCounters", "ReduceProcessedGroups", 1L);
/* 455:    */         }
/* 456:449 */         values.nextKey();
/* 457:450 */         values.informReduceProgress();
/* 458:    */       }
/* 459:453 */       reducer.close();
/* 460:454 */       reducer = null;
/* 461:    */       
/* 462:456 */       out.close(reporter);
/* 463:457 */       out = null;
/* 464:    */     }
/* 465:    */     finally
/* 466:    */     {
/* 467:459 */       IOUtils.cleanup(LOG, new Closeable[] { reducer });
/* 468:460 */       closeQuietly(out, reporter);
/* 469:    */     }
/* 470:    */   }
/* 471:    */   
/* 472:    */   static class OldTrackingRecordWriter<K, V>
/* 473:    */     implements RecordWriter<K, V>
/* 474:    */   {
/* 475:    */     private final RecordWriter<K, V> real;
/* 476:    */     private final Counters.Counter reduceOutputCounter;
/* 477:    */     private final Counters.Counter fileOutputByteCounter;
/* 478:    */     private final List<FileSystem.Statistics> fsStats;
/* 479:    */     
/* 480:    */     public OldTrackingRecordWriter(ReduceTask reduce, JobConf job, Task.TaskReporter reporter, String finalName)
/* 481:    */       throws IOException
/* 482:    */     {
/* 483:474 */       this.reduceOutputCounter = reduce.reduceOutputCounter;
/* 484:475 */       this.fileOutputByteCounter = reduce.fileOutputByteCounter;
/* 485:476 */       List<FileSystem.Statistics> matchedStats = null;
/* 486:477 */       if ((job.getOutputFormat() instanceof FileOutputFormat)) {
/* 487:478 */         matchedStats = Task.getFsStatistics(FileOutputFormat.getOutputPath(job), job);
/* 488:    */       }
/* 489:480 */       this.fsStats = matchedStats;
/* 490:    */       
/* 491:482 */       FileSystem fs = FileSystem.get(job);
/* 492:483 */       long bytesOutPrev = getOutputBytes(this.fsStats);
/* 493:484 */       this.real = job.getOutputFormat().getRecordWriter(fs, job, finalName, reporter);
/* 494:    */       
/* 495:486 */       long bytesOutCurr = getOutputBytes(this.fsStats);
/* 496:487 */       this.fileOutputByteCounter.increment(bytesOutCurr - bytesOutPrev);
/* 497:    */     }
/* 498:    */     
/* 499:    */     public void write(K key, V value)
/* 500:    */       throws IOException
/* 501:    */     {
/* 502:492 */       long bytesOutPrev = getOutputBytes(this.fsStats);
/* 503:493 */       this.real.write(key, value);
/* 504:494 */       long bytesOutCurr = getOutputBytes(this.fsStats);
/* 505:495 */       this.fileOutputByteCounter.increment(bytesOutCurr - bytesOutPrev);
/* 506:496 */       this.reduceOutputCounter.increment(1L);
/* 507:    */     }
/* 508:    */     
/* 509:    */     public void close(Reporter reporter)
/* 510:    */       throws IOException
/* 511:    */     {
/* 512:501 */       long bytesOutPrev = getOutputBytes(this.fsStats);
/* 513:502 */       this.real.close(reporter);
/* 514:503 */       long bytesOutCurr = getOutputBytes(this.fsStats);
/* 515:504 */       this.fileOutputByteCounter.increment(bytesOutCurr - bytesOutPrev);
/* 516:    */     }
/* 517:    */     
/* 518:    */     private long getOutputBytes(List<FileSystem.Statistics> stats)
/* 519:    */     {
/* 520:508 */       if (stats == null) {
/* 521:508 */         return 0L;
/* 522:    */       }
/* 523:509 */       long bytesWritten = 0L;
/* 524:510 */       for (FileSystem.Statistics stat : stats) {
/* 525:511 */         bytesWritten += stat.getBytesWritten();
/* 526:    */       }
/* 527:513 */       return bytesWritten;
/* 528:    */     }
/* 529:    */   }
/* 530:    */   
/* 531:    */   static class NewTrackingRecordWriter<K, V>
/* 532:    */     extends org.apache.hadoop.mapreduce.RecordWriter<K, V>
/* 533:    */   {
/* 534:    */     private final org.apache.hadoop.mapreduce.RecordWriter<K, V> real;
/* 535:    */     private final Counter outputRecordCounter;
/* 536:    */     private final Counter fileOutputByteCounter;
/* 537:    */     private final List<FileSystem.Statistics> fsStats;
/* 538:    */     
/* 539:    */     NewTrackingRecordWriter(ReduceTask reduce, TaskAttemptContext taskContext)
/* 540:    */       throws InterruptedException, IOException
/* 541:    */     {
/* 542:528 */       this.outputRecordCounter = reduce.reduceOutputCounter;
/* 543:529 */       this.fileOutputByteCounter = reduce.fileOutputByteCounter;
/* 544:    */       
/* 545:531 */       List<FileSystem.Statistics> matchedStats = null;
/* 546:532 */       if ((reduce.outputFormat instanceof org.apache.hadoop.mapreduce.lib.output.FileOutputFormat)) {
/* 547:533 */         matchedStats = Task.getFsStatistics(org.apache.hadoop.mapreduce.lib.output.FileOutputFormat.getOutputPath(taskContext), taskContext.getConfiguration());
/* 548:    */       }
/* 549:537 */       this.fsStats = matchedStats;
/* 550:    */       
/* 551:539 */       long bytesOutPrev = getOutputBytes(this.fsStats);
/* 552:540 */       this.real = reduce.outputFormat.getRecordWriter(taskContext);
/* 553:    */       
/* 554:542 */       long bytesOutCurr = getOutputBytes(this.fsStats);
/* 555:543 */       this.fileOutputByteCounter.increment(bytesOutCurr - bytesOutPrev);
/* 556:    */     }
/* 557:    */     
/* 558:    */     public void close(TaskAttemptContext context)
/* 559:    */       throws IOException, InterruptedException
/* 560:    */     {
/* 561:549 */       long bytesOutPrev = getOutputBytes(this.fsStats);
/* 562:550 */       this.real.close(context);
/* 563:551 */       long bytesOutCurr = getOutputBytes(this.fsStats);
/* 564:552 */       this.fileOutputByteCounter.increment(bytesOutCurr - bytesOutPrev);
/* 565:    */     }
/* 566:    */     
/* 567:    */     public void write(K key, V value)
/* 568:    */       throws IOException, InterruptedException
/* 569:    */     {
/* 570:557 */       long bytesOutPrev = getOutputBytes(this.fsStats);
/* 571:558 */       this.real.write(key, value);
/* 572:559 */       long bytesOutCurr = getOutputBytes(this.fsStats);
/* 573:560 */       this.fileOutputByteCounter.increment(bytesOutCurr - bytesOutPrev);
/* 574:561 */       this.outputRecordCounter.increment(1L);
/* 575:    */     }
/* 576:    */     
/* 577:    */     private long getOutputBytes(List<FileSystem.Statistics> stats)
/* 578:    */     {
/* 579:565 */       if (stats == null) {
/* 580:565 */         return 0L;
/* 581:    */       }
/* 582:566 */       long bytesWritten = 0L;
/* 583:567 */       for (FileSystem.Statistics stat : stats) {
/* 584:568 */         bytesWritten += stat.getBytesWritten();
/* 585:    */       }
/* 586:570 */       return bytesWritten;
/* 587:    */     }
/* 588:    */   }
/* 589:    */   
/* 590:    */   private <INKEY, INVALUE, OUTKEY, OUTVALUE> void runNewReducer(JobConf job, TaskUmbilicalProtocol umbilical, final Task.TaskReporter reporter, RawKeyValueIterator rIter, RawComparator<INKEY> comparator, Class<INKEY> keyClass, Class<INVALUE> valueClass)
/* 591:    */     throws IOException, InterruptedException, ClassNotFoundException
/* 592:    */   {
/* 593:586 */     final RawKeyValueIterator rawIter = rIter;
/* 594:587 */     rIter = new RawKeyValueIterator()
/* 595:    */     {
/* 596:    */       public void close()
/* 597:    */         throws IOException
/* 598:    */       {
/* 599:589 */         rawIter.close();
/* 600:    */       }
/* 601:    */       
/* 602:    */       public DataInputBuffer getKey()
/* 603:    */         throws IOException
/* 604:    */       {
/* 605:592 */         return rawIter.getKey();
/* 606:    */       }
/* 607:    */       
/* 608:    */       public Progress getProgress()
/* 609:    */       {
/* 610:595 */         return rawIter.getProgress();
/* 611:    */       }
/* 612:    */       
/* 613:    */       public DataInputBuffer getValue()
/* 614:    */         throws IOException
/* 615:    */       {
/* 616:598 */         return rawIter.getValue();
/* 617:    */       }
/* 618:    */       
/* 619:    */       public boolean next()
/* 620:    */         throws IOException
/* 621:    */       {
/* 622:601 */         boolean ret = rawIter.next();
/* 623:602 */         reporter.setProgress(rawIter.getProgress().getProgress());
/* 624:603 */         return ret;
/* 625:    */       }
/* 626:606 */     };
/* 627:607 */     TaskAttemptContext taskContext = new TaskAttemptContextImpl(job, getTaskID(), reporter);
/* 628:    */     
/* 629:    */ 
/* 630:    */ 
/* 631:611 */     org.apache.hadoop.mapreduce.Reducer<INKEY, INVALUE, OUTKEY, OUTVALUE> reducer = (org.apache.hadoop.mapreduce.Reducer)ReflectionUtils.newInstance(taskContext.getReducerClass(), job);
/* 632:    */     
/* 633:    */ 
/* 634:614 */     org.apache.hadoop.mapreduce.RecordWriter<OUTKEY, OUTVALUE> trackedRW = new NewTrackingRecordWriter(this, taskContext);
/* 635:    */     
/* 636:616 */     job.setBoolean("mapred.skip.on", isSkipping());
/* 637:617 */     job.setBoolean("mapreduce.job.skiprecords", isSkipping());
/* 638:    */     
/* 639:619 */     Reducer.Context reducerContext = createReduceContext(reducer, job, getTaskID(), rIter, this.reduceInputKeyCounter, this.reduceInputValueCounter, trackedRW, this.committer, reporter, comparator, keyClass, valueClass);
/* 640:    */     try
/* 641:    */     {
/* 642:627 */       reducer.run(reducerContext);
/* 643:    */     }
/* 644:    */     finally
/* 645:    */     {
/* 646:629 */       trackedRW.close(reducerContext);
/* 647:    */     }
/* 648:    */   }
/* 649:    */   
/* 650:    */   private <OUTKEY, OUTVALUE> void closeQuietly(RecordWriter<OUTKEY, OUTVALUE> c, Reporter r)
/* 651:    */   {
/* 652:635 */     if (c != null) {
/* 653:    */       try
/* 654:    */       {
/* 655:637 */         c.close(r);
/* 656:    */       }
/* 657:    */       catch (Exception e)
/* 658:    */       {
/* 659:639 */         LOG.info("Exception in closing " + c, e);
/* 660:    */       }
/* 661:    */     }
/* 662:    */   }
/* 663:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.ReduceTask
 * JD-Core Version:    0.7.0.1
 */