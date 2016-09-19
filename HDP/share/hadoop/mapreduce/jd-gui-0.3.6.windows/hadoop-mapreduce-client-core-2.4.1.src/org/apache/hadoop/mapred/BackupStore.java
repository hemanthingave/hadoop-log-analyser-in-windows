/*   1:    */ package org.apache.hadoop.mapred;
/*   2:    */ 
/*   3:    */ import java.io.DataOutputStream;
/*   4:    */ import java.io.IOException;
/*   5:    */ import java.io.InputStream;
/*   6:    */ import java.net.URI;
/*   7:    */ import java.util.Iterator;
/*   8:    */ import java.util.LinkedList;
/*   9:    */ import java.util.List;
/*  10:    */ import java.util.NoSuchElementException;
/*  11:    */ import org.apache.commons.logging.Log;
/*  12:    */ import org.apache.commons.logging.LogFactory;
/*  13:    */ import org.apache.hadoop.classification.InterfaceAudience.Private;
/*  14:    */ import org.apache.hadoop.classification.InterfaceStability.Unstable;
/*  15:    */ import org.apache.hadoop.conf.Configuration;
/*  16:    */ import org.apache.hadoop.fs.FileSystem;
/*  17:    */ import org.apache.hadoop.fs.LocalDirAllocator;
/*  18:    */ import org.apache.hadoop.fs.Path;
/*  19:    */ import org.apache.hadoop.io.DataInputBuffer;
/*  20:    */ import org.apache.hadoop.io.DataOutputBuffer;
/*  21:    */ import org.apache.hadoop.io.WritableUtils;
/*  22:    */ import org.apache.hadoop.mapreduce.task.reduce.InMemoryReader;
/*  23:    */ 
/*  24:    */ @InterfaceAudience.Private
/*  25:    */ @InterfaceStability.Unstable
/*  26:    */ public class BackupStore<K, V>
/*  27:    */ {
/*  28: 61 */   private static final Log LOG = LogFactory.getLog(BackupStore.class.getName());
/*  29:    */   private static final int MAX_VINT_SIZE = 9;
/*  30:    */   private static final int EOF_MARKER_SIZE = 18;
/*  31:    */   private final org.apache.hadoop.mapreduce.TaskAttemptID tid;
/*  32:    */   private BackupStore<K, V>.MemoryCache memCache;
/*  33:    */   private BackupStore<K, V>.FileCache fileCache;
/*  34: 69 */   List<Merger.Segment<K, V>> segmentList = new LinkedList();
/*  35: 70 */   private int readSegmentIndex = 0;
/*  36: 71 */   private int firstSegmentOffset = 0;
/*  37: 73 */   private int currentKVOffset = 0;
/*  38: 74 */   private int nextKVOffset = -1;
/*  39: 76 */   private DataInputBuffer currentKey = null;
/*  40: 77 */   private DataInputBuffer currentValue = new DataInputBuffer();
/*  41: 78 */   private DataInputBuffer currentDiskValue = new DataInputBuffer();
/*  42: 80 */   private boolean hasMore = false;
/*  43: 81 */   private boolean inReset = false;
/*  44: 82 */   private boolean clearMarkFlag = false;
/*  45: 83 */   private boolean lastSegmentEOF = false;
/*  46:    */   private Configuration conf;
/*  47:    */   
/*  48:    */   public BackupStore(Configuration conf, org.apache.hadoop.mapreduce.TaskAttemptID taskid)
/*  49:    */     throws IOException
/*  50:    */   {
/*  51: 90 */     float bufferPercent = conf.getFloat("mapreduce.reduce.markreset.buffer.percent", 0.0F);
/*  52: 93 */     if ((bufferPercent > 1.0D) || (bufferPercent < 0.0D)) {
/*  53: 94 */       throw new IOException("mapreduce.reduce.markreset.buffer.percent" + bufferPercent);
/*  54:    */     }
/*  55: 98 */     int maxSize = (int)Math.min((float)Runtime.getRuntime().maxMemory() * bufferPercent, 2.147484E+009F);
/*  56:    */     
/*  57:    */ 
/*  58:    */ 
/*  59:102 */     int tmp = conf.getInt("mapreduce.reduce.markreset.buffer.size", 0);
/*  60:103 */     if (tmp > 0) {
/*  61:104 */       maxSize = tmp;
/*  62:    */     }
/*  63:107 */     this.memCache = new MemoryCache(maxSize);
/*  64:108 */     this.fileCache = new FileCache(conf);
/*  65:109 */     this.tid = taskid;
/*  66:    */     
/*  67:111 */     this.conf = conf;
/*  68:    */     
/*  69:113 */     LOG.info("Created a new BackupStore with a memory of " + maxSize);
/*  70:    */   }
/*  71:    */   
/*  72:    */   public void write(DataInputBuffer key, DataInputBuffer value)
/*  73:    */     throws IOException
/*  74:    */   {
/*  75:127 */     assert ((key != null) && (value != null));
/*  76:129 */     if (this.fileCache.isActive())
/*  77:    */     {
/*  78:130 */       this.fileCache.write(key, value);
/*  79:131 */       return;
/*  80:    */     }
/*  81:134 */     if (this.memCache.reserveSpace(key, value))
/*  82:    */     {
/*  83:135 */       this.memCache.write(key, value);
/*  84:    */     }
/*  85:    */     else
/*  86:    */     {
/*  87:137 */       this.fileCache.activate();
/*  88:138 */       this.fileCache.write(key, value);
/*  89:    */     }
/*  90:    */   }
/*  91:    */   
/*  92:    */   public void mark()
/*  93:    */     throws IOException
/*  94:    */   {
/*  95:149 */     if (this.nextKVOffset == 0)
/*  96:    */     {
/*  97:150 */       assert (this.readSegmentIndex != 0);
/*  98:151 */       assert (this.currentKVOffset != 0);
/*  99:152 */       this.readSegmentIndex -= 1;
/* 100:    */     }
/* 101:157 */     int i = 0;
/* 102:158 */     Iterator<Merger.Segment<K, V>> itr = this.segmentList.iterator();
/* 103:159 */     while (itr.hasNext())
/* 104:    */     {
/* 105:160 */       Merger.Segment<K, V> s = (Merger.Segment)itr.next();
/* 106:161 */       if (i == this.readSegmentIndex) {
/* 107:    */         break;
/* 108:    */       }
/* 109:164 */       s.close();
/* 110:165 */       itr.remove();
/* 111:166 */       i++;
/* 112:167 */       LOG.debug("Dropping a segment");
/* 113:    */     }
/* 114:173 */     this.firstSegmentOffset = this.currentKVOffset;
/* 115:174 */     this.readSegmentIndex = 0;
/* 116:    */     
/* 117:176 */     LOG.debug("Setting the FirsSegmentOffset to " + this.currentKVOffset);
/* 118:    */   }
/* 119:    */   
/* 120:    */   public void reset()
/* 121:    */     throws IOException
/* 122:    */   {
/* 123:184 */     if (!this.inReset) {
/* 124:185 */       if (this.fileCache.isActive) {
/* 125:186 */         this.fileCache.createInDiskSegment();
/* 126:    */       } else {
/* 127:188 */         this.memCache.createInMemorySegment();
/* 128:    */       }
/* 129:    */     }
/* 130:192 */     this.inReset = true;
/* 131:196 */     for (int i = 0; i < this.segmentList.size(); i++)
/* 132:    */     {
/* 133:197 */       Merger.Segment<K, V> s = (Merger.Segment)this.segmentList.get(i);
/* 134:198 */       if (s.inMemory())
/* 135:    */       {
/* 136:199 */         int offset = i == 0 ? this.firstSegmentOffset : 0;
/* 137:200 */         s.getReader().reset(offset);
/* 138:    */       }
/* 139:    */       else
/* 140:    */       {
/* 141:202 */         s.closeReader();
/* 142:203 */         if (i == 0)
/* 143:    */         {
/* 144:204 */           s.reinitReader(this.firstSegmentOffset);
/* 145:205 */           s.getReader().disableChecksumValidation();
/* 146:    */         }
/* 147:    */       }
/* 148:    */     }
/* 149:210 */     this.currentKVOffset = this.firstSegmentOffset;
/* 150:211 */     this.nextKVOffset = -1;
/* 151:212 */     this.readSegmentIndex = 0;
/* 152:213 */     this.hasMore = false;
/* 153:214 */     this.lastSegmentEOF = false;
/* 154:    */     
/* 155:216 */     LOG.debug("Reset - First segment offset is " + this.firstSegmentOffset + " Segment List Size is " + this.segmentList.size());
/* 156:    */   }
/* 157:    */   
/* 158:    */   public boolean hasNext()
/* 159:    */     throws IOException
/* 160:    */   {
/* 161:222 */     if (this.lastSegmentEOF) {
/* 162:223 */       return false;
/* 163:    */     }
/* 164:231 */     if (this.hasMore) {
/* 165:232 */       return true;
/* 166:    */     }
/* 167:235 */     Merger.Segment<K, V> seg = (Merger.Segment)this.segmentList.get(this.readSegmentIndex);
/* 168:    */     
/* 169:    */ 
/* 170:238 */     this.nextKVOffset = ((int)seg.getActualPosition());
/* 171:239 */     if (seg.nextRawKey())
/* 172:    */     {
/* 173:240 */       this.currentKey = seg.getKey();
/* 174:241 */       seg.getValue(this.currentValue);
/* 175:242 */       this.hasMore = true;
/* 176:243 */       return true;
/* 177:    */     }
/* 178:245 */     if (!seg.inMemory()) {
/* 179:246 */       seg.closeReader();
/* 180:    */     }
/* 181:251 */     if (this.readSegmentIndex == this.segmentList.size() - 1)
/* 182:    */     {
/* 183:252 */       this.nextKVOffset = -1;
/* 184:253 */       this.lastSegmentEOF = true;
/* 185:254 */       return false;
/* 186:    */     }
/* 187:257 */     this.nextKVOffset = 0;
/* 188:258 */     this.readSegmentIndex += 1;
/* 189:    */     
/* 190:260 */     Merger.Segment<K, V> nextSegment = (Merger.Segment)this.segmentList.get(this.readSegmentIndex);
/* 191:266 */     if (!nextSegment.inMemory())
/* 192:    */     {
/* 193:267 */       this.currentValue.reset(this.currentDiskValue.getData(), this.currentDiskValue.getLength());
/* 194:    */       
/* 195:269 */       nextSegment.init(null);
/* 196:    */     }
/* 197:272 */     if (nextSegment.nextRawKey())
/* 198:    */     {
/* 199:273 */       this.currentKey = nextSegment.getKey();
/* 200:274 */       nextSegment.getValue(this.currentValue);
/* 201:275 */       this.hasMore = true;
/* 202:276 */       return true;
/* 203:    */     }
/* 204:278 */     throw new IOException("New segment did not have even one K/V");
/* 205:    */   }
/* 206:    */   
/* 207:    */   public void next()
/* 208:    */     throws IOException
/* 209:    */   {
/* 210:283 */     if (!hasNext()) {
/* 211:284 */       throw new NoSuchElementException("iterate past last value");
/* 212:    */     }
/* 213:287 */     this.hasMore = false;
/* 214:288 */     this.currentKVOffset = this.nextKVOffset;
/* 215:289 */     this.nextKVOffset = -1;
/* 216:    */   }
/* 217:    */   
/* 218:    */   public DataInputBuffer nextValue()
/* 219:    */   {
/* 220:293 */     return this.currentValue;
/* 221:    */   }
/* 222:    */   
/* 223:    */   public DataInputBuffer nextKey()
/* 224:    */   {
/* 225:297 */     return this.currentKey;
/* 226:    */   }
/* 227:    */   
/* 228:    */   public void reinitialize()
/* 229:    */     throws IOException
/* 230:    */   {
/* 231:301 */     if (this.segmentList.size() != 0) {
/* 232:302 */       clearSegmentList();
/* 233:    */     }
/* 234:304 */     this.memCache.reinitialize(true);
/* 235:305 */     this.fileCache.reinitialize();
/* 236:306 */     this.readSegmentIndex = (this.firstSegmentOffset = 0);
/* 237:307 */     this.currentKVOffset = 0;
/* 238:308 */     this.nextKVOffset = -1;
/* 239:309 */     this.hasMore = (this.inReset = this.clearMarkFlag = 0);
/* 240:    */   }
/* 241:    */   
/* 242:    */   public void exitResetMode()
/* 243:    */     throws IOException
/* 244:    */   {
/* 245:317 */     this.inReset = false;
/* 246:318 */     if (this.clearMarkFlag)
/* 247:    */     {
/* 248:321 */       reinitialize();
/* 249:322 */       return;
/* 250:    */     }
/* 251:324 */     if (!this.fileCache.isActive) {
/* 252:325 */       this.memCache.reinitialize(false);
/* 253:    */     }
/* 254:    */   }
/* 255:    */   
/* 256:    */   public DataOutputStream getOutputStream(int length)
/* 257:    */     throws IOException
/* 258:    */   {
/* 259:334 */     if (this.memCache.reserveSpace(length)) {
/* 260:335 */       return this.memCache.dataOut;
/* 261:    */     }
/* 262:337 */     this.fileCache.activate();
/* 263:338 */     return this.fileCache.writer.getOutputStream();
/* 264:    */   }
/* 265:    */   
/* 266:    */   public void updateCounters(int length)
/* 267:    */   {
/* 268:347 */     if (this.fileCache.isActive) {
/* 269:348 */       this.fileCache.writer.updateCountersForExternalAppend(length);
/* 270:    */     } else {
/* 271:350 */       MemoryCache.access$312(this.memCache, length);
/* 272:    */     }
/* 273:    */   }
/* 274:    */   
/* 275:    */   public void clearMark()
/* 276:    */     throws IOException
/* 277:    */   {
/* 278:355 */     if (this.inReset) {
/* 279:359 */       this.clearMarkFlag = true;
/* 280:    */     } else {
/* 281:361 */       reinitialize();
/* 282:    */     }
/* 283:    */   }
/* 284:    */   
/* 285:    */   private void clearSegmentList()
/* 286:    */     throws IOException
/* 287:    */   {
/* 288:366 */     for (Merger.Segment<K, V> segment : this.segmentList)
/* 289:    */     {
/* 290:367 */       long len = segment.getLength();
/* 291:368 */       segment.close();
/* 292:369 */       if (segment.inMemory()) {
/* 293:370 */         this.memCache.unreserve(len);
/* 294:    */       }
/* 295:    */     }
/* 296:373 */     this.segmentList.clear();
/* 297:    */   }
/* 298:    */   
/* 299:    */   class MemoryCache
/* 300:    */   {
/* 301:    */     private DataOutputBuffer dataOut;
/* 302:    */     private int blockSize;
/* 303:    */     private int usedSize;
/* 304:    */     private final BackupStore.BackupRamManager ramManager;
/* 305:383 */     private int defaultBlockSize = 1048576;
/* 306:    */     
/* 307:    */     public MemoryCache(int maxSize)
/* 308:    */     {
/* 309:386 */       this.ramManager = new BackupStore.BackupRamManager(maxSize);
/* 310:387 */       if (maxSize < this.defaultBlockSize) {
/* 311:388 */         this.defaultBlockSize = maxSize;
/* 312:    */       }
/* 313:    */     }
/* 314:    */     
/* 315:    */     public void unreserve(long len)
/* 316:    */     {
/* 317:393 */       this.ramManager.unreserve((int)len);
/* 318:    */     }
/* 319:    */     
/* 320:    */     void reinitialize(boolean clearAll)
/* 321:    */     {
/* 322:402 */       if (clearAll) {
/* 323:403 */         this.ramManager.reinitialize();
/* 324:    */       }
/* 325:405 */       int allocatedSize = createNewMemoryBlock(this.defaultBlockSize, this.defaultBlockSize);
/* 326:    */       
/* 327:407 */       assert ((allocatedSize == this.defaultBlockSize) || (allocatedSize == 0));
/* 328:408 */       BackupStore.LOG.debug("Created a new mem block of " + allocatedSize);
/* 329:    */     }
/* 330:    */     
/* 331:    */     private int createNewMemoryBlock(int requestedSize, int minSize)
/* 332:    */     {
/* 333:412 */       int allocatedSize = this.ramManager.reserve(requestedSize, minSize);
/* 334:413 */       this.usedSize = 0;
/* 335:414 */       if (allocatedSize == 0)
/* 336:    */       {
/* 337:415 */         this.dataOut = null;
/* 338:416 */         this.blockSize = 0;
/* 339:    */       }
/* 340:    */       else
/* 341:    */       {
/* 342:418 */         this.dataOut = new DataOutputBuffer(allocatedSize);
/* 343:419 */         this.blockSize = allocatedSize;
/* 344:    */       }
/* 345:421 */       return allocatedSize;
/* 346:    */     }
/* 347:    */     
/* 348:    */     boolean reserveSpace(int length)
/* 349:    */       throws IOException
/* 350:    */     {
/* 351:432 */       int availableSize = this.blockSize - this.usedSize;
/* 352:433 */       if (availableSize >= length + 18) {
/* 353:434 */         return true;
/* 354:    */       }
/* 355:437 */       assert (!BackupStore.this.inReset);
/* 356:    */       
/* 357:439 */       createInMemorySegment();
/* 358:    */       
/* 359:    */ 
/* 360:442 */       int tmp = Math.max(length + 18, this.defaultBlockSize);
/* 361:443 */       availableSize = createNewMemoryBlock(tmp, length + 18);
/* 362:    */       
/* 363:    */ 
/* 364:446 */       return availableSize != 0;
/* 365:    */     }
/* 366:    */     
/* 367:    */     boolean reserveSpace(DataInputBuffer key, DataInputBuffer value)
/* 368:    */       throws IOException
/* 369:    */     {
/* 370:451 */       int keyLength = key.getLength() - key.getPosition();
/* 371:452 */       int valueLength = value.getLength() - value.getPosition();
/* 372:    */       
/* 373:454 */       int requestedSize = keyLength + valueLength + WritableUtils.getVIntSize(keyLength) + WritableUtils.getVIntSize(valueLength);
/* 374:    */       
/* 375:    */ 
/* 376:457 */       return reserveSpace(requestedSize);
/* 377:    */     }
/* 378:    */     
/* 379:    */     public void write(DataInputBuffer key, DataInputBuffer value)
/* 380:    */       throws IOException
/* 381:    */     {
/* 382:468 */       int keyLength = key.getLength() - key.getPosition();
/* 383:469 */       int valueLength = value.getLength() - value.getPosition();
/* 384:470 */       WritableUtils.writeVInt(this.dataOut, keyLength);
/* 385:471 */       WritableUtils.writeVInt(this.dataOut, valueLength);
/* 386:472 */       this.dataOut.write(key.getData(), key.getPosition(), keyLength);
/* 387:473 */       this.dataOut.write(value.getData(), value.getPosition(), valueLength);
/* 388:474 */       this.usedSize += keyLength + valueLength + WritableUtils.getVIntSize(keyLength) + WritableUtils.getVIntSize(valueLength);
/* 389:    */       
/* 390:    */ 
/* 391:477 */       BackupStore.LOG.debug("ID: " + BackupStore.this.segmentList.size() + " WRITE TO MEM");
/* 392:    */     }
/* 393:    */     
/* 394:    */     void createInMemorySegment()
/* 395:    */       throws IOException
/* 396:    */     {
/* 397:488 */       if (this.usedSize == 0)
/* 398:    */       {
/* 399:489 */         this.ramManager.unreserve(this.blockSize);
/* 400:490 */         return;
/* 401:    */       }
/* 402:495 */       assert (this.blockSize - this.usedSize >= 18);
/* 403:    */       
/* 404:497 */       WritableUtils.writeVInt(this.dataOut, -1);
/* 405:498 */       WritableUtils.writeVInt(this.dataOut, -1);
/* 406:    */       
/* 407:500 */       this.usedSize += 18;
/* 408:    */       
/* 409:502 */       this.ramManager.unreserve(this.blockSize - this.usedSize);
/* 410:    */       
/* 411:504 */       IFile.Reader<K, V> reader = new InMemoryReader(null, (TaskAttemptID)BackupStore.this.tid, this.dataOut.getData(), 0, this.usedSize, BackupStore.this.conf);
/* 412:    */       
/* 413:    */ 
/* 414:    */ 
/* 415:508 */       Merger.Segment<K, V> segment = new Merger.Segment(reader, false);
/* 416:509 */       BackupStore.this.segmentList.add(segment);
/* 417:510 */       BackupStore.LOG.debug("Added Memory Segment to List. List Size is " + BackupStore.this.segmentList.size());
/* 418:    */     }
/* 419:    */   }
/* 420:    */   
/* 421:    */   class FileCache
/* 422:    */   {
/* 423:    */     private LocalDirAllocator lDirAlloc;
/* 424:    */     private final Configuration conf;
/* 425:    */     private final FileSystem fs;
/* 426:519 */     private boolean isActive = false;
/* 427:521 */     private Path file = null;
/* 428:522 */     private IFile.Writer<K, V> writer = null;
/* 429:523 */     private int spillNumber = 0;
/* 430:    */     
/* 431:    */     public FileCache(Configuration conf)
/* 432:    */       throws IOException
/* 433:    */     {
/* 434:527 */       this.conf = conf;
/* 435:528 */       this.fs = FileSystem.getLocal(conf);
/* 436:529 */       this.lDirAlloc = new LocalDirAllocator("mapreduce.cluster.local.dir");
/* 437:    */     }
/* 438:    */     
/* 439:    */     void write(DataInputBuffer key, DataInputBuffer value)
/* 440:    */       throws IOException
/* 441:    */     {
/* 442:534 */       if (this.writer == null)
/* 443:    */       {
/* 444:537 */         assert (this.spillNumber != 0);
/* 445:538 */         this.writer = createSpillFile();
/* 446:    */       }
/* 447:540 */       this.writer.append(key, value);
/* 448:541 */       BackupStore.LOG.debug("ID: " + BackupStore.this.segmentList.size() + " WRITE TO DISK");
/* 449:    */     }
/* 450:    */     
/* 451:    */     void reinitialize()
/* 452:    */     {
/* 453:545 */       this.spillNumber = 0;
/* 454:546 */       this.writer = null;
/* 455:547 */       this.isActive = false;
/* 456:    */     }
/* 457:    */     
/* 458:    */     void activate()
/* 459:    */       throws IOException
/* 460:    */     {
/* 461:551 */       this.isActive = true;
/* 462:552 */       this.writer = createSpillFile();
/* 463:    */     }
/* 464:    */     
/* 465:    */     void createInDiskSegment()
/* 466:    */       throws IOException
/* 467:    */     {
/* 468:556 */       assert (this.writer != null);
/* 469:557 */       this.writer.close();
/* 470:558 */       Merger.Segment<K, V> s = new Merger.Segment(this.conf, this.fs, this.file, null, true);
/* 471:559 */       this.writer = null;
/* 472:560 */       BackupStore.this.segmentList.add(s);
/* 473:561 */       BackupStore.LOG.debug("Disk Segment added to List. Size is " + BackupStore.this.segmentList.size());
/* 474:    */     }
/* 475:    */     
/* 476:    */     boolean isActive()
/* 477:    */     {
/* 478:564 */       return this.isActive;
/* 479:    */     }
/* 480:    */     
/* 481:    */     private IFile.Writer<K, V> createSpillFile()
/* 482:    */       throws IOException
/* 483:    */     {
/* 484:567 */       Path tmp = new Path("output/backup_" + BackupStore.this.tid.getId() + "_" + this.spillNumber++ + ".out");
/* 485:    */       
/* 486:    */ 
/* 487:    */ 
/* 488:571 */       BackupStore.LOG.info("Created file: " + tmp);
/* 489:    */       
/* 490:573 */       this.file = this.lDirAlloc.getLocalPathForWrite(tmp.toUri().getPath(), -1L, this.conf);
/* 491:    */       
/* 492:575 */       return new IFile.Writer(this.conf, this.fs, this.file);
/* 493:    */     }
/* 494:    */   }
/* 495:    */   
/* 496:    */   static class BackupRamManager
/* 497:    */     implements RamManager
/* 498:    */   {
/* 499:581 */     private int availableSize = 0;
/* 500:    */     private final int maxSize;
/* 501:    */     
/* 502:    */     public BackupRamManager(int size)
/* 503:    */     {
/* 504:585 */       this.availableSize = (this.maxSize = size);
/* 505:    */     }
/* 506:    */     
/* 507:    */     public boolean reserve(int requestedSize, InputStream in)
/* 508:    */     {
/* 509:590 */       BackupStore.LOG.warn("Reserve(int, InputStream) not supported by BackupRamManager");
/* 510:591 */       return false;
/* 511:    */     }
/* 512:    */     
/* 513:    */     int reserve(int requestedSize)
/* 514:    */     {
/* 515:595 */       if (this.availableSize == 0) {
/* 516:596 */         return 0;
/* 517:    */       }
/* 518:598 */       int reservedSize = Math.min(requestedSize, this.availableSize);
/* 519:599 */       this.availableSize -= reservedSize;
/* 520:600 */       BackupStore.LOG.debug("Reserving: " + reservedSize + " Requested: " + requestedSize);
/* 521:601 */       return reservedSize;
/* 522:    */     }
/* 523:    */     
/* 524:    */     int reserve(int requestedSize, int minSize)
/* 525:    */     {
/* 526:605 */       if (this.availableSize < minSize)
/* 527:    */       {
/* 528:606 */         BackupStore.LOG.debug("No Space available. Available: " + this.availableSize + " MinSize: " + minSize);
/* 529:    */         
/* 530:608 */         return 0;
/* 531:    */       }
/* 532:610 */       return reserve(requestedSize);
/* 533:    */     }
/* 534:    */     
/* 535:    */     public void unreserve(int requestedSize)
/* 536:    */     {
/* 537:615 */       this.availableSize += requestedSize;
/* 538:616 */       BackupStore.LOG.debug("Unreserving: " + requestedSize + ". Available: " + this.availableSize);
/* 539:    */     }
/* 540:    */     
/* 541:    */     void reinitialize()
/* 542:    */     {
/* 543:621 */       this.availableSize = this.maxSize;
/* 544:    */     }
/* 545:    */   }
/* 546:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.BackupStore
 * JD-Core Version:    0.7.0.1
 */