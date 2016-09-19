/*   1:    */ package org.apache.hadoop.mapred;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import java.util.Collections;
/*   6:    */ import java.util.Comparator;
/*   7:    */ import java.util.List;
/*   8:    */ import org.apache.commons.logging.Log;
/*   9:    */ import org.apache.commons.logging.LogFactory;
/*  10:    */ import org.apache.hadoop.classification.InterfaceAudience.Private;
/*  11:    */ import org.apache.hadoop.classification.InterfaceStability.Unstable;
/*  12:    */ import org.apache.hadoop.conf.Configuration;
/*  13:    */ import org.apache.hadoop.fs.ChecksumFileSystem;
/*  14:    */ import org.apache.hadoop.fs.FSDataInputStream;
/*  15:    */ import org.apache.hadoop.fs.FileStatus;
/*  16:    */ import org.apache.hadoop.fs.FileSystem;
/*  17:    */ import org.apache.hadoop.fs.LocalDirAllocator;
/*  18:    */ import org.apache.hadoop.fs.Path;
/*  19:    */ import org.apache.hadoop.io.DataInputBuffer;
/*  20:    */ import org.apache.hadoop.io.RawComparator;
/*  21:    */ import org.apache.hadoop.io.compress.CompressionCodec;
/*  22:    */ import org.apache.hadoop.mapreduce.TaskType;
/*  23:    */ import org.apache.hadoop.util.PriorityQueue;
/*  24:    */ import org.apache.hadoop.util.Progress;
/*  25:    */ import org.apache.hadoop.util.Progressable;
/*  26:    */ 
/*  27:    */ @InterfaceAudience.Private
/*  28:    */ @InterfaceStability.Unstable
/*  29:    */ public class Merger
/*  30:    */ {
/*  31: 54 */   private static final Log LOG = LogFactory.getLog(Merger.class);
/*  32: 57 */   private static LocalDirAllocator lDirAlloc = new LocalDirAllocator("mapreduce.cluster.local.dir");
/*  33:    */   
/*  34:    */   public static <K, V> RawKeyValueIterator merge(Configuration conf, FileSystem fs, Class<K> keyClass, Class<V> valueClass, CompressionCodec codec, Path[] inputs, boolean deleteInputs, int mergeFactor, Path tmpDir, RawComparator<K> comparator, Progressable reporter, Counters.Counter readsCounter, Counters.Counter writesCounter, Progress mergePhase)
/*  35:    */     throws IOException
/*  36:    */   {
/*  37: 71 */     return new MergeQueue(conf, fs, inputs, deleteInputs, codec, comparator, reporter, null, TaskType.REDUCE).merge(keyClass, valueClass, mergeFactor, tmpDir, readsCounter, writesCounter, mergePhase);
/*  38:    */   }
/*  39:    */   
/*  40:    */   public static <K, V> RawKeyValueIterator merge(Configuration conf, FileSystem fs, Class<K> keyClass, Class<V> valueClass, CompressionCodec codec, Path[] inputs, boolean deleteInputs, int mergeFactor, Path tmpDir, RawComparator<K> comparator, Progressable reporter, Counters.Counter readsCounter, Counters.Counter writesCounter, Counters.Counter mergedMapOutputsCounter, Progress mergePhase)
/*  41:    */     throws IOException
/*  42:    */   {
/*  43: 93 */     return new MergeQueue(conf, fs, inputs, deleteInputs, codec, comparator, reporter, mergedMapOutputsCounter, TaskType.REDUCE).merge(keyClass, valueClass, mergeFactor, tmpDir, readsCounter, writesCounter, mergePhase);
/*  44:    */   }
/*  45:    */   
/*  46:    */   public static <K, V> RawKeyValueIterator merge(Configuration conf, FileSystem fs, Class<K> keyClass, Class<V> valueClass, List<Segment<K, V>> segments, int mergeFactor, Path tmpDir, RawComparator<K> comparator, Progressable reporter, Counters.Counter readsCounter, Counters.Counter writesCounter, Progress mergePhase)
/*  47:    */     throws IOException
/*  48:    */   {
/*  49:113 */     return merge(conf, fs, keyClass, valueClass, segments, mergeFactor, tmpDir, comparator, reporter, false, readsCounter, writesCounter, mergePhase);
/*  50:    */   }
/*  51:    */   
/*  52:    */   public static <K, V> RawKeyValueIterator merge(Configuration conf, FileSystem fs, Class<K> keyClass, Class<V> valueClass, List<Segment<K, V>> segments, int mergeFactor, Path tmpDir, RawComparator<K> comparator, Progressable reporter, boolean sortSegments, Counters.Counter readsCounter, Counters.Counter writesCounter, Progress mergePhase)
/*  53:    */     throws IOException
/*  54:    */   {
/*  55:129 */     return new MergeQueue(conf, fs, segments, comparator, reporter, sortSegments, TaskType.REDUCE).merge(keyClass, valueClass, mergeFactor, tmpDir, readsCounter, writesCounter, mergePhase);
/*  56:    */   }
/*  57:    */   
/*  58:    */   public static <K, V> RawKeyValueIterator merge(Configuration conf, FileSystem fs, Class<K> keyClass, Class<V> valueClass, CompressionCodec codec, List<Segment<K, V>> segments, int mergeFactor, Path tmpDir, RawComparator<K> comparator, Progressable reporter, boolean sortSegments, Counters.Counter readsCounter, Counters.Counter writesCounter, Progress mergePhase, TaskType taskType)
/*  59:    */     throws IOException
/*  60:    */   {
/*  61:150 */     return new MergeQueue(conf, fs, segments, comparator, reporter, sortSegments, codec, taskType).merge(keyClass, valueClass, mergeFactor, tmpDir, readsCounter, writesCounter, mergePhase);
/*  62:    */   }
/*  63:    */   
/*  64:    */   public static <K, V> RawKeyValueIterator merge(Configuration conf, FileSystem fs, Class<K> keyClass, Class<V> valueClass, List<Segment<K, V>> segments, int mergeFactor, int inMemSegments, Path tmpDir, RawComparator<K> comparator, Progressable reporter, boolean sortSegments, Counters.Counter readsCounter, Counters.Counter writesCounter, Progress mergePhase)
/*  65:    */     throws IOException
/*  66:    */   {
/*  67:169 */     return new MergeQueue(conf, fs, segments, comparator, reporter, sortSegments, TaskType.REDUCE).merge(keyClass, valueClass, mergeFactor, inMemSegments, tmpDir, readsCounter, writesCounter, mergePhase);
/*  68:    */   }
/*  69:    */   
/*  70:    */   public static <K, V> RawKeyValueIterator merge(Configuration conf, FileSystem fs, Class<K> keyClass, Class<V> valueClass, CompressionCodec codec, List<Segment<K, V>> segments, int mergeFactor, int inMemSegments, Path tmpDir, RawComparator<K> comparator, Progressable reporter, boolean sortSegments, Counters.Counter readsCounter, Counters.Counter writesCounter, Progress mergePhase)
/*  71:    */     throws IOException
/*  72:    */   {
/*  73:191 */     return new MergeQueue(conf, fs, segments, comparator, reporter, sortSegments, codec, TaskType.REDUCE).merge(keyClass, valueClass, mergeFactor, inMemSegments, tmpDir, readsCounter, writesCounter, mergePhase);
/*  74:    */   }
/*  75:    */   
/*  76:    */   public static <K, V> void writeFile(RawKeyValueIterator records, IFile.Writer<K, V> writer, Progressable progressable, Configuration conf)
/*  77:    */     throws IOException
/*  78:    */   {
/*  79:204 */     long progressBar = conf.getLong("mapreduce.task.merge.progress.records", 10000L);
/*  80:    */     
/*  81:206 */     long recordCtr = 0L;
/*  82:207 */     while (records.next())
/*  83:    */     {
/*  84:208 */       writer.append(records.getKey(), records.getValue());
/*  85:210 */       if (recordCtr++ % progressBar == 0L) {
/*  86:211 */         progressable.progress();
/*  87:    */       }
/*  88:    */     }
/*  89:    */   }
/*  90:    */   
/*  91:    */   @InterfaceAudience.Private
/*  92:    */   @InterfaceStability.Unstable
/*  93:    */   public static class Segment<K, V>
/*  94:    */   {
/*  95:219 */     IFile.Reader<K, V> reader = null;
/*  96:220 */     final DataInputBuffer key = new DataInputBuffer();
/*  97:222 */     Configuration conf = null;
/*  98:223 */     FileSystem fs = null;
/*  99:224 */     Path file = null;
/* 100:225 */     boolean preserve = false;
/* 101:226 */     CompressionCodec codec = null;
/* 102:227 */     long segmentOffset = 0L;
/* 103:228 */     long segmentLength = -1L;
/* 104:229 */     long rawDataLength = -1L;
/* 105:231 */     Counters.Counter mapOutputsCounter = null;
/* 106:    */     
/* 107:    */     public Segment(Configuration conf, FileSystem fs, Path file, CompressionCodec codec, boolean preserve)
/* 108:    */       throws IOException
/* 109:    */     {
/* 110:236 */       this(conf, fs, file, codec, preserve, null);
/* 111:    */     }
/* 112:    */     
/* 113:    */     public Segment(Configuration conf, FileSystem fs, Path file, CompressionCodec codec, boolean preserve, Counters.Counter mergedMapOutputsCounter)
/* 114:    */       throws IOException
/* 115:    */     {
/* 116:243 */       this(conf, fs, file, 0L, fs.getFileStatus(file).getLen(), codec, preserve, mergedMapOutputsCounter);
/* 117:    */     }
/* 118:    */     
/* 119:    */     public Segment(Configuration conf, FileSystem fs, Path file, CompressionCodec codec, boolean preserve, Counters.Counter mergedMapOutputsCounter, long rawDataLength)
/* 120:    */       throws IOException
/* 121:    */     {
/* 122:251 */       this(conf, fs, file, 0L, fs.getFileStatus(file).getLen(), codec, preserve, mergedMapOutputsCounter);
/* 123:    */       
/* 124:253 */       this.rawDataLength = rawDataLength;
/* 125:    */     }
/* 126:    */     
/* 127:    */     public Segment(Configuration conf, FileSystem fs, Path file, long segmentOffset, long segmentLength, CompressionCodec codec, boolean preserve)
/* 128:    */       throws IOException
/* 129:    */     {
/* 130:260 */       this(conf, fs, file, segmentOffset, segmentLength, codec, preserve, null);
/* 131:    */     }
/* 132:    */     
/* 133:    */     public Segment(Configuration conf, FileSystem fs, Path file, long segmentOffset, long segmentLength, CompressionCodec codec, boolean preserve, Counters.Counter mergedMapOutputsCounter)
/* 134:    */       throws IOException
/* 135:    */     {
/* 136:267 */       this.conf = conf;
/* 137:268 */       this.fs = fs;
/* 138:269 */       this.file = file;
/* 139:270 */       this.codec = codec;
/* 140:271 */       this.preserve = preserve;
/* 141:    */       
/* 142:273 */       this.segmentOffset = segmentOffset;
/* 143:274 */       this.segmentLength = segmentLength;
/* 144:    */       
/* 145:276 */       this.mapOutputsCounter = mergedMapOutputsCounter;
/* 146:    */     }
/* 147:    */     
/* 148:    */     public Segment(IFile.Reader<K, V> reader, boolean preserve)
/* 149:    */     {
/* 150:280 */       this(reader, preserve, null);
/* 151:    */     }
/* 152:    */     
/* 153:    */     public Segment(IFile.Reader<K, V> reader, boolean preserve, long rawDataLength)
/* 154:    */     {
/* 155:284 */       this(reader, preserve, null);
/* 156:285 */       this.rawDataLength = rawDataLength;
/* 157:    */     }
/* 158:    */     
/* 159:    */     public Segment(IFile.Reader<K, V> reader, boolean preserve, Counters.Counter mapOutputsCounter)
/* 160:    */     {
/* 161:290 */       this.reader = reader;
/* 162:291 */       this.preserve = preserve;
/* 163:    */       
/* 164:293 */       this.segmentLength = reader.getLength();
/* 165:    */       
/* 166:295 */       this.mapOutputsCounter = mapOutputsCounter;
/* 167:    */     }
/* 168:    */     
/* 169:    */     void init(Counters.Counter readsCounter)
/* 170:    */       throws IOException
/* 171:    */     {
/* 172:299 */       if (this.reader == null)
/* 173:    */       {
/* 174:300 */         FSDataInputStream in = this.fs.open(this.file);
/* 175:301 */         in.seek(this.segmentOffset);
/* 176:302 */         this.reader = new IFile.Reader(this.conf, in, this.segmentLength, this.codec, readsCounter);
/* 177:    */       }
/* 178:305 */       if (this.mapOutputsCounter != null) {
/* 179:306 */         this.mapOutputsCounter.increment(1L);
/* 180:    */       }
/* 181:    */     }
/* 182:    */     
/* 183:    */     boolean inMemory()
/* 184:    */     {
/* 185:311 */       return this.fs == null;
/* 186:    */     }
/* 187:    */     
/* 188:    */     DataInputBuffer getKey()
/* 189:    */     {
/* 190:314 */       return this.key;
/* 191:    */     }
/* 192:    */     
/* 193:    */     DataInputBuffer getValue(DataInputBuffer value)
/* 194:    */       throws IOException
/* 195:    */     {
/* 196:317 */       nextRawValue(value);
/* 197:318 */       return value;
/* 198:    */     }
/* 199:    */     
/* 200:    */     public long getLength()
/* 201:    */     {
/* 202:322 */       return this.reader == null ? this.segmentLength : this.reader.getLength();
/* 203:    */     }
/* 204:    */     
/* 205:    */     public long getRawDataLength()
/* 206:    */     {
/* 207:327 */       return this.rawDataLength > 0L ? this.rawDataLength : getLength();
/* 208:    */     }
/* 209:    */     
/* 210:    */     boolean nextRawKey()
/* 211:    */       throws IOException
/* 212:    */     {
/* 213:331 */       return this.reader.nextRawKey(this.key);
/* 214:    */     }
/* 215:    */     
/* 216:    */     void nextRawValue(DataInputBuffer value)
/* 217:    */       throws IOException
/* 218:    */     {
/* 219:335 */       this.reader.nextRawValue(value);
/* 220:    */     }
/* 221:    */     
/* 222:    */     void closeReader()
/* 223:    */       throws IOException
/* 224:    */     {
/* 225:339 */       if (this.reader != null)
/* 226:    */       {
/* 227:340 */         this.reader.close();
/* 228:341 */         this.reader = null;
/* 229:    */       }
/* 230:    */     }
/* 231:    */     
/* 232:    */     void close()
/* 233:    */       throws IOException
/* 234:    */     {
/* 235:346 */       closeReader();
/* 236:347 */       if ((!this.preserve) && (this.fs != null)) {
/* 237:348 */         this.fs.delete(this.file, false);
/* 238:    */       }
/* 239:    */     }
/* 240:    */     
/* 241:    */     public long getPosition()
/* 242:    */       throws IOException
/* 243:    */     {
/* 244:353 */       return this.reader.getPosition();
/* 245:    */     }
/* 246:    */     
/* 247:    */     long getActualPosition()
/* 248:    */       throws IOException
/* 249:    */     {
/* 250:359 */       return this.segmentOffset + this.reader.getPosition();
/* 251:    */     }
/* 252:    */     
/* 253:    */     IFile.Reader<K, V> getReader()
/* 254:    */     {
/* 255:363 */       return this.reader;
/* 256:    */     }
/* 257:    */     
/* 258:    */     void reinitReader(int offset)
/* 259:    */       throws IOException
/* 260:    */     {
/* 261:369 */       if (!inMemory())
/* 262:    */       {
/* 263:370 */         closeReader();
/* 264:371 */         this.segmentOffset = offset;
/* 265:372 */         this.segmentLength = (this.fs.getFileStatus(this.file).getLen() - this.segmentOffset);
/* 266:373 */         init(null);
/* 267:    */       }
/* 268:    */     }
/* 269:    */   }
/* 270:    */   
/* 271:    */   private static class MergeQueue<K, V>
/* 272:    */     extends PriorityQueue<Merger.Segment<K, V>>
/* 273:    */     implements RawKeyValueIterator
/* 274:    */   {
/* 275:    */     Configuration conf;
/* 276:    */     FileSystem fs;
/* 277:    */     CompressionCodec codec;
/* 278:385 */     List<Merger.Segment<K, V>> segments = new ArrayList();
/* 279:    */     RawComparator<K> comparator;
/* 280:    */     private long totalBytesProcessed;
/* 281:    */     private float progPerByte;
/* 282:391 */     private Progress mergeProgress = new Progress();
/* 283:    */     Progressable reporter;
/* 284:    */     DataInputBuffer key;
/* 285:396 */     final DataInputBuffer value = new DataInputBuffer();
/* 286:397 */     final DataInputBuffer diskIFileValue = new DataInputBuffer();
/* 287:403 */     private boolean includeFinalMerge = false;
/* 288:    */     Merger.Segment<K, V> minSegment;
/* 289:    */     
/* 290:    */     private void considerFinalMergeForProgress()
/* 291:    */     {
/* 292:411 */       this.includeFinalMerge = true;
/* 293:    */     }
/* 294:    */     
/* 295:415 */     Comparator<Merger.Segment<K, V>> segmentComparator = new Comparator()
/* 296:    */     {
/* 297:    */       public int compare(Merger.Segment<K, V> o1, Merger.Segment<K, V> o2)
/* 298:    */       {
/* 299:418 */         if (o1.getLength() == o2.getLength()) {
/* 300:419 */           return 0;
/* 301:    */         }
/* 302:422 */         return o1.getLength() < o2.getLength() ? -1 : 1;
/* 303:    */       }
/* 304:    */     };
/* 305:    */     
/* 306:    */     public MergeQueue(Configuration conf, FileSystem fs, Path[] inputs, boolean deleteInputs, CompressionCodec codec, RawComparator<K> comparator, Progressable reporter)
/* 307:    */       throws IOException
/* 308:    */     {
/* 309:432 */       this(conf, fs, inputs, deleteInputs, codec, comparator, reporter, null, TaskType.REDUCE);
/* 310:    */     }
/* 311:    */     
/* 312:    */     public MergeQueue(Configuration conf, FileSystem fs, Path[] inputs, boolean deleteInputs, CompressionCodec codec, RawComparator<K> comparator, Progressable reporter, Counters.Counter mergedMapOutputsCounter, TaskType taskType)
/* 313:    */       throws IOException
/* 314:    */     {
/* 315:443 */       this.conf = conf;
/* 316:444 */       this.fs = fs;
/* 317:445 */       this.codec = codec;
/* 318:446 */       this.comparator = comparator;
/* 319:447 */       this.reporter = reporter;
/* 320:449 */       if (taskType == TaskType.MAP) {
/* 321:450 */         considerFinalMergeForProgress();
/* 322:    */       }
/* 323:453 */       for (Path file : inputs)
/* 324:    */       {
/* 325:454 */         Merger.LOG.debug("MergeQ: adding: " + file);
/* 326:455 */         this.segments.add(new Merger.Segment(conf, fs, file, codec, !deleteInputs, file.toString().endsWith(Task.MERGED_OUTPUT_PREFIX) ? null : mergedMapOutputsCounter));
/* 327:    */       }
/* 328:462 */       Collections.sort(this.segments, this.segmentComparator);
/* 329:    */     }
/* 330:    */     
/* 331:    */     public MergeQueue(Configuration conf, FileSystem fs, List<Merger.Segment<K, V>> segments, RawComparator<K> comparator, Progressable reporter)
/* 332:    */     {
/* 333:468 */       this(conf, fs, segments, comparator, reporter, false, TaskType.REDUCE);
/* 334:    */     }
/* 335:    */     
/* 336:    */     public MergeQueue(Configuration conf, FileSystem fs, List<Merger.Segment<K, V>> segments, RawComparator<K> comparator, Progressable reporter, boolean sortSegments, TaskType taskType)
/* 337:    */     {
/* 338:474 */       this.conf = conf;
/* 339:475 */       this.fs = fs;
/* 340:476 */       this.comparator = comparator;
/* 341:477 */       this.segments = segments;
/* 342:478 */       this.reporter = reporter;
/* 343:479 */       if (taskType == TaskType.MAP) {
/* 344:480 */         considerFinalMergeForProgress();
/* 345:    */       }
/* 346:482 */       if (sortSegments) {
/* 347:483 */         Collections.sort(segments, this.segmentComparator);
/* 348:    */       }
/* 349:    */     }
/* 350:    */     
/* 351:    */     public MergeQueue(Configuration conf, FileSystem fs, List<Merger.Segment<K, V>> segments, RawComparator<K> comparator, Progressable reporter, boolean sortSegments, CompressionCodec codec, TaskType taskType)
/* 352:    */     {
/* 353:491 */       this(conf, fs, segments, comparator, reporter, sortSegments, taskType);
/* 354:    */       
/* 355:493 */       this.codec = codec;
/* 356:    */     }
/* 357:    */     
/* 358:    */     public void close()
/* 359:    */       throws IOException
/* 360:    */     {
/* 361:    */       Merger.Segment<K, V> segment;
/* 362:498 */       while ((segment = (Merger.Segment)pop()) != null) {
/* 363:499 */         segment.close();
/* 364:    */       }
/* 365:    */     }
/* 366:    */     
/* 367:    */     public DataInputBuffer getKey()
/* 368:    */       throws IOException
/* 369:    */     {
/* 370:504 */       return this.key;
/* 371:    */     }
/* 372:    */     
/* 373:    */     public DataInputBuffer getValue()
/* 374:    */       throws IOException
/* 375:    */     {
/* 376:508 */       return this.value;
/* 377:    */     }
/* 378:    */     
/* 379:    */     private void adjustPriorityQueue(Merger.Segment<K, V> reader)
/* 380:    */       throws IOException
/* 381:    */     {
/* 382:512 */       long startPos = reader.getPosition();
/* 383:513 */       boolean hasNext = reader.nextRawKey();
/* 384:514 */       long endPos = reader.getPosition();
/* 385:515 */       this.totalBytesProcessed += endPos - startPos;
/* 386:516 */       this.mergeProgress.set((float)this.totalBytesProcessed * this.progPerByte);
/* 387:517 */       if (hasNext)
/* 388:    */       {
/* 389:518 */         adjustTop();
/* 390:    */       }
/* 391:    */       else
/* 392:    */       {
/* 393:520 */         pop();
/* 394:521 */         reader.close();
/* 395:    */       }
/* 396:    */     }
/* 397:    */     
/* 398:    */     public boolean next()
/* 399:    */       throws IOException
/* 400:    */     {
/* 401:526 */       if (size() == 0) {
/* 402:527 */         return false;
/* 403:    */       }
/* 404:529 */       if (this.minSegment != null)
/* 405:    */       {
/* 406:533 */         adjustPriorityQueue(this.minSegment);
/* 407:534 */         if (size() == 0)
/* 408:    */         {
/* 409:535 */           this.minSegment = null;
/* 410:536 */           return false;
/* 411:    */         }
/* 412:    */       }
/* 413:539 */       this.minSegment = ((Merger.Segment)top());
/* 414:540 */       long startPos = this.minSegment.getPosition();
/* 415:541 */       this.key = this.minSegment.getKey();
/* 416:542 */       if (!this.minSegment.inMemory())
/* 417:    */       {
/* 418:552 */         this.minSegment.getValue(this.diskIFileValue);
/* 419:553 */         this.value.reset(this.diskIFileValue.getData(), this.diskIFileValue.getLength());
/* 420:    */       }
/* 421:    */       else
/* 422:    */       {
/* 423:555 */         this.minSegment.getValue(this.value);
/* 424:    */       }
/* 425:557 */       long endPos = this.minSegment.getPosition();
/* 426:558 */       this.totalBytesProcessed += endPos - startPos;
/* 427:559 */       this.mergeProgress.set((float)this.totalBytesProcessed * this.progPerByte);
/* 428:560 */       return true;
/* 429:    */     }
/* 430:    */     
/* 431:    */     protected boolean lessThan(Object a, Object b)
/* 432:    */     {
/* 433:565 */       DataInputBuffer key1 = ((Merger.Segment)a).getKey();
/* 434:566 */       DataInputBuffer key2 = ((Merger.Segment)b).getKey();
/* 435:567 */       int s1 = key1.getPosition();
/* 436:568 */       int l1 = key1.getLength() - s1;
/* 437:569 */       int s2 = key2.getPosition();
/* 438:570 */       int l2 = key2.getLength() - s2;
/* 439:    */       
/* 440:572 */       return this.comparator.compare(key1.getData(), s1, l1, key2.getData(), s2, l2) < 0;
/* 441:    */     }
/* 442:    */     
/* 443:    */     public RawKeyValueIterator merge(Class<K> keyClass, Class<V> valueClass, int factor, Path tmpDir, Counters.Counter readsCounter, Counters.Counter writesCounter, Progress mergePhase)
/* 444:    */       throws IOException
/* 445:    */     {
/* 446:581 */       return merge(keyClass, valueClass, factor, 0, tmpDir, readsCounter, writesCounter, mergePhase);
/* 447:    */     }
/* 448:    */     
/* 449:    */     RawKeyValueIterator merge(Class<K> keyClass, Class<V> valueClass, int factor, int inMem, Path tmpDir, Counters.Counter readsCounter, Counters.Counter writesCounter, Progress mergePhase)
/* 450:    */       throws IOException
/* 451:    */     {
/* 452:591 */       Merger.LOG.info("Merging " + this.segments.size() + " sorted segments");
/* 453:    */       
/* 454:    */ 
/* 455:    */ 
/* 456:    */ 
/* 457:    */ 
/* 458:    */ 
/* 459:    */ 
/* 460:599 */       int numSegments = this.segments.size();
/* 461:600 */       int origFactor = factor;
/* 462:601 */       int passNo = 1;
/* 463:602 */       if (mergePhase != null) {
/* 464:603 */         this.mergeProgress = mergePhase;
/* 465:    */       }
/* 466:606 */       long totalBytes = computeBytesInMerges(factor, inMem);
/* 467:607 */       if (totalBytes != 0L) {
/* 468:608 */         this.progPerByte = (1.0F / (float)totalBytes);
/* 469:    */       }
/* 470:    */       for (;;)
/* 471:    */       {
/* 472:617 */         factor = getPassFactor(factor, passNo, numSegments - inMem);
/* 473:618 */         if (1 == passNo) {
/* 474:619 */           factor += inMem;
/* 475:    */         }
/* 476:621 */         List<Merger.Segment<K, V>> segmentsToMerge = new ArrayList();
/* 477:    */         
/* 478:623 */         int segmentsConsidered = 0;
/* 479:624 */         int numSegmentsToConsider = factor;
/* 480:625 */         long startBytes = 0L;
/* 481:    */         for (;;)
/* 482:    */         {
/* 483:629 */           List<Merger.Segment<K, V>> mStream = getSegmentDescriptors(numSegmentsToConsider);
/* 484:631 */           for (Merger.Segment<K, V> segment : mStream)
/* 485:    */           {
/* 486:634 */             segment.init(readsCounter);
/* 487:635 */             long startPos = segment.getPosition();
/* 488:636 */             boolean hasNext = segment.nextRawKey();
/* 489:637 */             long endPos = segment.getPosition();
/* 490:639 */             if (hasNext)
/* 491:    */             {
/* 492:640 */               startBytes += endPos - startPos;
/* 493:641 */               segmentsToMerge.add(segment);
/* 494:642 */               segmentsConsidered++;
/* 495:    */             }
/* 496:    */             else
/* 497:    */             {
/* 498:645 */               segment.close();
/* 499:646 */               numSegments--;
/* 500:    */             }
/* 501:    */           }
/* 502:651 */           if ((segmentsConsidered == factor) || (this.segments.size() == 0)) {
/* 503:    */             break;
/* 504:    */           }
/* 505:656 */           numSegmentsToConsider = factor - segmentsConsidered;
/* 506:    */         }
/* 507:660 */         initialize(segmentsToMerge.size());
/* 508:661 */         clear();
/* 509:662 */         for (Merger.Segment<K, V> segment : segmentsToMerge) {
/* 510:663 */           put(segment);
/* 511:    */         }
/* 512:668 */         if (numSegments <= factor)
/* 513:    */         {
/* 514:669 */           if (!this.includeFinalMerge)
/* 515:    */           {
/* 516:675 */             this.totalBytesProcessed = 0L;
/* 517:676 */             totalBytes = 0L;
/* 518:677 */             for (int i = 0; i < segmentsToMerge.size(); i++) {
/* 519:678 */               totalBytes += ((Merger.Segment)segmentsToMerge.get(i)).getRawDataLength();
/* 520:    */             }
/* 521:    */           }
/* 522:681 */           if (totalBytes != 0L) {
/* 523:682 */             this.progPerByte = (1.0F / (float)totalBytes);
/* 524:    */           }
/* 525:684 */           this.totalBytesProcessed += startBytes;
/* 526:685 */           if (totalBytes != 0L) {
/* 527:686 */             this.mergeProgress.set((float)this.totalBytesProcessed * this.progPerByte);
/* 528:    */           } else {
/* 529:688 */             this.mergeProgress.set(1.0F);
/* 530:    */           }
/* 531:690 */           Merger.LOG.info("Down to the last merge-pass, with " + numSegments + " segments left of total size: " + (totalBytes - this.totalBytesProcessed) + " bytes");
/* 532:    */           
/* 533:    */ 
/* 534:693 */           return this;
/* 535:    */         }
/* 536:695 */         Merger.LOG.info("Merging " + segmentsToMerge.size() + " intermediate segments out of a total of " + (this.segments.size() + segmentsToMerge.size()));
/* 537:    */         
/* 538:    */ 
/* 539:    */ 
/* 540:699 */         long bytesProcessedInPrevMerges = this.totalBytesProcessed;
/* 541:700 */         this.totalBytesProcessed += startBytes;
/* 542:    */         
/* 543:    */ 
/* 544:    */ 
/* 545:704 */         long approxOutputSize = 0L;
/* 546:705 */         for (Merger.Segment<K, V> s : segmentsToMerge) {
/* 547:706 */           approxOutputSize = (approxOutputSize + (s.getLength() + ChecksumFileSystem.getApproxChkSumLength(s.getLength())));
/* 548:    */         }
/* 549:710 */         Path tmpFilename = new Path(tmpDir, "intermediate").suffix("." + passNo);
/* 550:    */         
/* 551:    */ 
/* 552:713 */         Path outputFile = Merger.lDirAlloc.getLocalPathForWrite(tmpFilename.toString(), approxOutputSize, this.conf);
/* 553:    */         
/* 554:    */ 
/* 555:    */ 
/* 556:717 */         IFile.Writer<K, V> writer = new IFile.Writer(this.conf, this.fs, outputFile, keyClass, valueClass, this.codec, writesCounter);
/* 557:    */         
/* 558:    */ 
/* 559:720 */         Merger.writeFile(this, writer, this.reporter, this.conf);
/* 560:721 */         writer.close();
/* 561:    */         
/* 562:    */ 
/* 563:    */ 
/* 564:725 */         close();
/* 565:    */         
/* 566:    */ 
/* 567:728 */         Merger.Segment<K, V> tempSegment = new Merger.Segment(this.conf, this.fs, outputFile, this.codec, false);
/* 568:    */         
/* 569:    */ 
/* 570:    */ 
/* 571:732 */         int pos = Collections.binarySearch(this.segments, tempSegment, this.segmentComparator);
/* 572:734 */         if (pos < 0) {
/* 573:736 */           pos = -pos - 1;
/* 574:    */         }
/* 575:738 */         this.segments.add(pos, tempSegment);
/* 576:739 */         numSegments = this.segments.size();
/* 577:    */         
/* 578:    */ 
/* 579:    */ 
/* 580:    */ 
/* 581:    */ 
/* 582:745 */         long inputBytesOfThisMerge = this.totalBytesProcessed - bytesProcessedInPrevMerges;
/* 583:    */         
/* 584:747 */         totalBytes -= inputBytesOfThisMerge - tempSegment.getRawDataLength();
/* 585:748 */         if (totalBytes != 0L) {
/* 586:749 */           this.progPerByte = (1.0F / (float)totalBytes);
/* 587:    */         }
/* 588:752 */         passNo++;
/* 589:    */         
/* 590:    */ 
/* 591:    */ 
/* 592:756 */         factor = origFactor;
/* 593:    */       }
/* 594:    */     }
/* 595:    */     
/* 596:    */     private int getPassFactor(int factor, int passNo, int numSegments)
/* 597:    */     {
/* 598:767 */       if ((passNo > 1) || (numSegments <= factor) || (factor == 1)) {
/* 599:768 */         return factor;
/* 600:    */       }
/* 601:769 */       int mod = (numSegments - 1) % (factor - 1);
/* 602:770 */       if (mod == 0) {
/* 603:771 */         return factor;
/* 604:    */       }
/* 605:772 */       return mod + 1;
/* 606:    */     }
/* 607:    */     
/* 608:    */     private List<Merger.Segment<K, V>> getSegmentDescriptors(int numDescriptors)
/* 609:    */     {
/* 610:779 */       if (numDescriptors > this.segments.size())
/* 611:    */       {
/* 612:780 */         List<Merger.Segment<K, V>> subList = new ArrayList(this.segments);
/* 613:781 */         this.segments.clear();
/* 614:782 */         return subList;
/* 615:    */       }
/* 616:785 */       List<Merger.Segment<K, V>> subList = new ArrayList(this.segments.subList(0, numDescriptors));
/* 617:787 */       for (int i = 0; i < numDescriptors; i++) {
/* 618:788 */         this.segments.remove(0);
/* 619:    */       }
/* 620:790 */       return subList;
/* 621:    */     }
/* 622:    */     
/* 623:    */     long computeBytesInMerges(int factor, int inMem)
/* 624:    */     {
/* 625:802 */       int numSegments = this.segments.size();
/* 626:803 */       List<Long> segmentSizes = new ArrayList(numSegments);
/* 627:804 */       long totalBytes = 0L;
/* 628:805 */       int n = numSegments - inMem;
/* 629:    */       
/* 630:807 */       int f = getPassFactor(factor, 1, n) + inMem;
/* 631:808 */       n = numSegments;
/* 632:810 */       for (int i = 0; i < numSegments; i++) {
/* 633:813 */         segmentSizes.add(Long.valueOf(((Merger.Segment)this.segments.get(i)).getRawDataLength()));
/* 634:    */       }
/* 635:819 */       boolean considerFinalMerge = this.includeFinalMerge;
/* 636:821 */       while ((n > f) || (considerFinalMerge))
/* 637:    */       {
/* 638:822 */         if (n <= f) {
/* 639:823 */           considerFinalMerge = false;
/* 640:    */         }
/* 641:825 */         long mergedSize = 0L;
/* 642:826 */         f = Math.min(f, segmentSizes.size());
/* 643:827 */         for (int j = 0; j < f; j++) {
/* 644:828 */           mergedSize += ((Long)segmentSizes.remove(0)).longValue();
/* 645:    */         }
/* 646:830 */         totalBytes += mergedSize;
/* 647:    */         
/* 648:    */ 
/* 649:833 */         int pos = Collections.binarySearch(segmentSizes, Long.valueOf(mergedSize));
/* 650:834 */         if (pos < 0) {
/* 651:835 */           pos = -pos - 1;
/* 652:    */         }
/* 653:837 */         segmentSizes.add(pos, Long.valueOf(mergedSize));
/* 654:    */         
/* 655:839 */         n -= f - 1;
/* 656:840 */         f = factor;
/* 657:    */       }
/* 658:843 */       return totalBytes;
/* 659:    */     }
/* 660:    */     
/* 661:    */     public Progress getProgress()
/* 662:    */     {
/* 663:847 */       return this.mergeProgress;
/* 664:    */     }
/* 665:    */   }
/* 666:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.Merger
 * JD-Core Version:    0.7.0.1
 */