/*   1:    */ package org.apache.hadoop.mapred;
/*   2:    */ 
/*   3:    */ import com.google.common.base.Stopwatch;
/*   4:    */ import com.google.common.collect.Iterables;
/*   5:    */ import java.io.IOException;
/*   6:    */ import java.util.ArrayList;
/*   7:    */ import java.util.Collections;
/*   8:    */ import java.util.Comparator;
/*   9:    */ import java.util.HashSet;
/*  10:    */ import java.util.IdentityHashMap;
/*  11:    */ import java.util.LinkedList;
/*  12:    */ import java.util.List;
/*  13:    */ import java.util.Map;
/*  14:    */ import java.util.Set;
/*  15:    */ import org.apache.commons.logging.Log;
/*  16:    */ import org.apache.commons.logging.LogFactory;
/*  17:    */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*  18:    */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*  19:    */ import org.apache.hadoop.fs.BlockLocation;
/*  20:    */ import org.apache.hadoop.fs.FileStatus;
/*  21:    */ import org.apache.hadoop.fs.FileSystem;
/*  22:    */ import org.apache.hadoop.fs.LocatedFileStatus;
/*  23:    */ import org.apache.hadoop.fs.Path;
/*  24:    */ import org.apache.hadoop.fs.PathFilter;
/*  25:    */ import org.apache.hadoop.fs.RemoteIterator;
/*  26:    */ import org.apache.hadoop.mapreduce.security.TokenCache;
/*  27:    */ import org.apache.hadoop.net.NetworkTopology;
/*  28:    */ import org.apache.hadoop.net.Node;
/*  29:    */ import org.apache.hadoop.net.NodeBase;
/*  30:    */ import org.apache.hadoop.util.ReflectionUtils;
/*  31:    */ import org.apache.hadoop.util.StringUtils;
/*  32:    */ 
/*  33:    */ @InterfaceAudience.Public
/*  34:    */ @InterfaceStability.Stable
/*  35:    */ public abstract class FileInputFormat<K, V>
/*  36:    */   implements InputFormat<K, V>
/*  37:    */ {
/*  38: 67 */   public static final Log LOG = LogFactory.getLog(FileInputFormat.class);
/*  39:    */   public static final String NUM_INPUT_FILES = "mapreduce.input.fileinputformat.numinputfiles";
/*  40:    */   public static final String INPUT_DIR_RECURSIVE = "mapreduce.input.fileinputformat.input.dir.recursive";
/*  41:    */   private static final double SPLIT_SLOP = 1.1D;
/*  42:    */   private long minSplitSize;
/*  43:    */   
/*  44:    */   @Deprecated
/*  45:    */   public static enum Counter
/*  46:    */   {
/*  47: 72 */     BYTES_READ;
/*  48:    */     
/*  49:    */     private Counter() {}
/*  50:    */   }
/*  51:    */   
/*  52:    */   public FileInputFormat()
/*  53:    */   {
/*  54: 84 */     this.minSplitSize = 1L;
/*  55:    */   }
/*  56:    */   
/*  57: 85 */   private static final PathFilter hiddenFileFilter = new PathFilter()
/*  58:    */   {
/*  59:    */     public boolean accept(Path p)
/*  60:    */     {
/*  61: 87 */       String name = p.getName();
/*  62: 88 */       return (!name.startsWith("_")) && (!name.startsWith("."));
/*  63:    */     }
/*  64:    */   };
/*  65:    */   
/*  66:    */   protected void setMinSplitSize(long minSplitSize)
/*  67:    */   {
/*  68: 92 */     this.minSplitSize = minSplitSize;
/*  69:    */   }
/*  70:    */   
/*  71:    */   private static class MultiPathFilter
/*  72:    */     implements PathFilter
/*  73:    */   {
/*  74:    */     private List<PathFilter> filters;
/*  75:    */     
/*  76:    */     public MultiPathFilter(List<PathFilter> filters)
/*  77:    */     {
/*  78:104 */       this.filters = filters;
/*  79:    */     }
/*  80:    */     
/*  81:    */     public boolean accept(Path path)
/*  82:    */     {
/*  83:108 */       for (PathFilter filter : this.filters) {
/*  84:109 */         if (!filter.accept(path)) {
/*  85:110 */           return false;
/*  86:    */         }
/*  87:    */       }
/*  88:113 */       return true;
/*  89:    */     }
/*  90:    */   }
/*  91:    */   
/*  92:    */   protected boolean isSplitable(FileSystem fs, Path filename)
/*  93:    */   {
/*  94:130 */     return true;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public abstract RecordReader<K, V> getRecordReader(InputSplit paramInputSplit, JobConf paramJobConf, Reporter paramReporter)
/*  98:    */     throws IOException;
/*  99:    */   
/* 100:    */   public static void setInputPathFilter(JobConf conf, Class<? extends PathFilter> filter)
/* 101:    */   {
/* 102:145 */     conf.setClass("mapreduce.input.pathFilter.class", filter, PathFilter.class);
/* 103:    */   }
/* 104:    */   
/* 105:    */   public static PathFilter getInputPathFilter(JobConf conf)
/* 106:    */   {
/* 107:155 */     Class<? extends PathFilter> filterClass = conf.getClass("mapreduce.input.pathFilter.class", null, PathFilter.class);
/* 108:    */     
/* 109:    */ 
/* 110:158 */     return filterClass != null ? (PathFilter)ReflectionUtils.newInstance(filterClass, conf) : null;
/* 111:    */   }
/* 112:    */   
/* 113:    */   protected void addInputPathRecursively(List<FileStatus> result, FileSystem fs, Path path, PathFilter inputFilter)
/* 114:    */     throws IOException
/* 115:    */   {
/* 116:177 */     RemoteIterator<LocatedFileStatus> iter = fs.listLocatedStatus(path);
/* 117:178 */     while (iter.hasNext())
/* 118:    */     {
/* 119:179 */       LocatedFileStatus stat = (LocatedFileStatus)iter.next();
/* 120:180 */       if (inputFilter.accept(stat.getPath())) {
/* 121:181 */         if (stat.isDirectory()) {
/* 122:182 */           addInputPathRecursively(result, fs, stat.getPath(), inputFilter);
/* 123:    */         } else {
/* 124:184 */           result.add(stat);
/* 125:    */         }
/* 126:    */       }
/* 127:    */     }
/* 128:    */   }
/* 129:    */   
/* 130:    */   protected FileStatus[] listStatus(JobConf job)
/* 131:    */     throws IOException
/* 132:    */   {
/* 133:199 */     Path[] dirs = getInputPaths(job);
/* 134:200 */     if (dirs.length == 0) {
/* 135:201 */       throw new IOException("No input paths specified in job");
/* 136:    */     }
/* 137:205 */     TokenCache.obtainTokensForNamenodes(job.getCredentials(), dirs, job);
/* 138:    */     
/* 139:    */ 
/* 140:208 */     boolean recursive = job.getBoolean("mapreduce.input.fileinputformat.input.dir.recursive", false);
/* 141:    */     
/* 142:    */ 
/* 143:    */ 
/* 144:212 */     List<PathFilter> filters = new ArrayList();
/* 145:213 */     filters.add(hiddenFileFilter);
/* 146:214 */     PathFilter jobFilter = getInputPathFilter(job);
/* 147:215 */     if (jobFilter != null) {
/* 148:216 */       filters.add(jobFilter);
/* 149:    */     }
/* 150:218 */     PathFilter inputFilter = new MultiPathFilter(filters);
/* 151:    */     
/* 152:    */ 
/* 153:221 */     int numThreads = job.getInt("mapreduce.input.fileinputformat.list-status.num-threads", 1);
/* 154:    */     
/* 155:    */ 
/* 156:    */ 
/* 157:    */ 
/* 158:226 */     Stopwatch sw = new Stopwatch().start();
/* 159:    */     FileStatus[] result;
/* 160:    */     FileStatus[] result;
/* 161:227 */     if (numThreads == 1)
/* 162:    */     {
/* 163:228 */       List<FileStatus> locatedFiles = singleThreadedListStatus(job, dirs, inputFilter, recursive);
/* 164:229 */       result = (FileStatus[])locatedFiles.toArray(new FileStatus[locatedFiles.size()]);
/* 165:    */     }
/* 166:    */     else
/* 167:    */     {
/* 168:231 */       Iterable<FileStatus> locatedFiles = null;
/* 169:    */       try
/* 170:    */       {
/* 171:234 */         LocatedFileStatusFetcher locatedFileStatusFetcher = new LocatedFileStatusFetcher(job, dirs, recursive, inputFilter, false);
/* 172:    */         
/* 173:236 */         locatedFiles = locatedFileStatusFetcher.getFileStatuses();
/* 174:    */       }
/* 175:    */       catch (InterruptedException e)
/* 176:    */       {
/* 177:238 */         throw new IOException("Interrupted while getting file statuses");
/* 178:    */       }
/* 179:240 */       result = (FileStatus[])Iterables.toArray(locatedFiles, FileStatus.class);
/* 180:    */     }
/* 181:243 */     sw.stop();
/* 182:244 */     if (LOG.isDebugEnabled()) {
/* 183:245 */       LOG.debug("Time taken to get FileStatuses: " + sw.elapsedMillis());
/* 184:    */     }
/* 185:247 */     LOG.info("Total input paths to process : " + result.length);
/* 186:248 */     return result;
/* 187:    */   }
/* 188:    */   
/* 189:    */   private List<FileStatus> singleThreadedListStatus(JobConf job, Path[] dirs, PathFilter inputFilter, boolean recursive)
/* 190:    */     throws IOException
/* 191:    */   {
/* 192:253 */     List<FileStatus> result = new ArrayList();
/* 193:254 */     List<IOException> errors = new ArrayList();
/* 194:255 */     for (Path p : dirs)
/* 195:    */     {
/* 196:256 */       FileSystem fs = p.getFileSystem(job);
/* 197:257 */       FileStatus[] matches = fs.globStatus(p, inputFilter);
/* 198:258 */       if (matches == null) {
/* 199:259 */         errors.add(new IOException("Input path does not exist: " + p));
/* 200:260 */       } else if (matches.length == 0) {
/* 201:261 */         errors.add(new IOException("Input Pattern " + p + " matches 0 files"));
/* 202:    */       } else {
/* 203:263 */         for (FileStatus globStat : matches) {
/* 204:264 */           if (globStat.isDirectory())
/* 205:    */           {
/* 206:265 */             RemoteIterator<LocatedFileStatus> iter = fs.listLocatedStatus(globStat.getPath());
/* 207:267 */             while (iter.hasNext())
/* 208:    */             {
/* 209:268 */               LocatedFileStatus stat = (LocatedFileStatus)iter.next();
/* 210:269 */               if (inputFilter.accept(stat.getPath())) {
/* 211:270 */                 if ((recursive) && (stat.isDirectory())) {
/* 212:271 */                   addInputPathRecursively(result, fs, stat.getPath(), inputFilter);
/* 213:    */                 } else {
/* 214:274 */                   result.add(stat);
/* 215:    */                 }
/* 216:    */               }
/* 217:    */             }
/* 218:    */           }
/* 219:    */           else
/* 220:    */           {
/* 221:279 */             result.add(globStat);
/* 222:    */           }
/* 223:    */         }
/* 224:    */       }
/* 225:    */     }
/* 226:284 */     if (!errors.isEmpty()) {
/* 227:285 */       throw new InvalidInputException(errors);
/* 228:    */     }
/* 229:287 */     return result;
/* 230:    */   }
/* 231:    */   
/* 232:    */   protected FileSplit makeSplit(Path file, long start, long length, String[] hosts)
/* 233:    */   {
/* 234:296 */     return new FileSplit(file, start, length, hosts);
/* 235:    */   }
/* 236:    */   
/* 237:    */   public InputSplit[] getSplits(JobConf job, int numSplits)
/* 238:    */     throws IOException
/* 239:    */   {
/* 240:303 */     Stopwatch sw = new Stopwatch().start();
/* 241:304 */     FileStatus[] files = listStatus(job);
/* 242:    */     
/* 243:    */ 
/* 244:307 */     job.setLong("mapreduce.input.fileinputformat.numinputfiles", files.length);
/* 245:308 */     long totalSize = 0L;
/* 246:309 */     for (FileStatus file : files)
/* 247:    */     {
/* 248:310 */       if (file.isDirectory()) {
/* 249:311 */         throw new IOException("Not a file: " + file.getPath());
/* 250:    */       }
/* 251:313 */       totalSize += file.getLen();
/* 252:    */     }
/* 253:316 */     long goalSize = totalSize / (numSplits == 0 ? 1 : numSplits);
/* 254:317 */     long minSize = Math.max(job.getLong("mapreduce.input.fileinputformat.split.minsize", 1L), this.minSplitSize);
/* 255:    */     
/* 256:    */ 
/* 257:    */ 
/* 258:321 */     ArrayList<FileSplit> splits = new ArrayList(numSplits);
/* 259:322 */     NetworkTopology clusterMap = new NetworkTopology();
/* 260:323 */     for (FileStatus file : files)
/* 261:    */     {
/* 262:324 */       Path path = file.getPath();
/* 263:325 */       long length = file.getLen();
/* 264:326 */       if (length != 0L)
/* 265:    */       {
/* 266:327 */         FileSystem fs = path.getFileSystem(job);
/* 267:    */         BlockLocation[] blkLocations;
/* 268:    */         BlockLocation[] blkLocations;
/* 269:329 */         if ((file instanceof LocatedFileStatus)) {
/* 270:330 */           blkLocations = ((LocatedFileStatus)file).getBlockLocations();
/* 271:    */         } else {
/* 272:332 */           blkLocations = fs.getFileBlockLocations(file, 0L, length);
/* 273:    */         }
/* 274:334 */         if (isSplitable(fs, path))
/* 275:    */         {
/* 276:335 */           long blockSize = file.getBlockSize();
/* 277:336 */           long splitSize = computeSplitSize(goalSize, minSize, blockSize);
/* 278:    */           
/* 279:338 */           long bytesRemaining = length;
/* 280:339 */           while (bytesRemaining / splitSize > 1.1D)
/* 281:    */           {
/* 282:340 */             String[] splitHosts = getSplitHosts(blkLocations, length - bytesRemaining, splitSize, clusterMap);
/* 283:    */             
/* 284:342 */             splits.add(makeSplit(path, length - bytesRemaining, splitSize, splitHosts));
/* 285:    */             
/* 286:344 */             bytesRemaining -= splitSize;
/* 287:    */           }
/* 288:347 */           if (bytesRemaining != 0L)
/* 289:    */           {
/* 290:348 */             String[] splitHosts = getSplitHosts(blkLocations, length - bytesRemaining, bytesRemaining, clusterMap);
/* 291:    */             
/* 292:350 */             splits.add(makeSplit(path, length - bytesRemaining, bytesRemaining, splitHosts));
/* 293:    */           }
/* 294:    */         }
/* 295:    */         else
/* 296:    */         {
/* 297:354 */           String[] splitHosts = getSplitHosts(blkLocations, 0L, length, clusterMap);
/* 298:355 */           splits.add(makeSplit(path, 0L, length, splitHosts));
/* 299:    */         }
/* 300:    */       }
/* 301:    */       else
/* 302:    */       {
/* 303:359 */         splits.add(makeSplit(path, 0L, length, new String[0]));
/* 304:    */       }
/* 305:    */     }
/* 306:362 */     sw.stop();
/* 307:363 */     if (LOG.isDebugEnabled()) {
/* 308:364 */       LOG.debug("Total # of splits generated by getSplits: " + splits.size() + ", TimeTaken: " + sw.elapsedMillis());
/* 309:    */     }
/* 310:367 */     return (InputSplit[])splits.toArray(new FileSplit[splits.size()]);
/* 311:    */   }
/* 312:    */   
/* 313:    */   protected long computeSplitSize(long goalSize, long minSize, long blockSize)
/* 314:    */   {
/* 315:372 */     return Math.max(minSize, Math.min(goalSize, blockSize));
/* 316:    */   }
/* 317:    */   
/* 318:    */   protected int getBlockIndex(BlockLocation[] blkLocations, long offset)
/* 319:    */   {
/* 320:377 */     for (int i = 0; i < blkLocations.length; i++) {
/* 321:379 */       if ((blkLocations[i].getOffset() <= offset) && (offset < blkLocations[i].getOffset() + blkLocations[i].getLength())) {
/* 322:381 */         return i;
/* 323:    */       }
/* 324:    */     }
/* 325:384 */     BlockLocation last = blkLocations[(blkLocations.length - 1)];
/* 326:385 */     long fileLength = last.getOffset() + last.getLength() - 1L;
/* 327:386 */     throw new IllegalArgumentException("Offset " + offset + " is outside of file (0.." + fileLength + ")");
/* 328:    */   }
/* 329:    */   
/* 330:    */   public static void setInputPaths(JobConf conf, String commaSeparatedPaths)
/* 331:    */   {
/* 332:400 */     setInputPaths(conf, StringUtils.stringToPath(getPathStrings(commaSeparatedPaths)));
/* 333:    */   }
/* 334:    */   
/* 335:    */   public static void addInputPaths(JobConf conf, String commaSeparatedPaths)
/* 336:    */   {
/* 337:413 */     for (String str : getPathStrings(commaSeparatedPaths)) {
/* 338:414 */       addInputPath(conf, new Path(str));
/* 339:    */     }
/* 340:    */   }
/* 341:    */   
/* 342:    */   public static void setInputPaths(JobConf conf, Path... inputPaths)
/* 343:    */   {
/* 344:427 */     Path path = new Path(conf.getWorkingDirectory(), inputPaths[0]);
/* 345:428 */     StringBuffer str = new StringBuffer(StringUtils.escapeString(path.toString()));
/* 346:429 */     for (int i = 1; i < inputPaths.length; i++)
/* 347:    */     {
/* 348:430 */       str.append(",");
/* 349:431 */       path = new Path(conf.getWorkingDirectory(), inputPaths[i]);
/* 350:432 */       str.append(StringUtils.escapeString(path.toString()));
/* 351:    */     }
/* 352:434 */     conf.set("mapreduce.input.fileinputformat.inputdir", str.toString());
/* 353:    */   }
/* 354:    */   
/* 355:    */   public static void addInputPath(JobConf conf, Path path)
/* 356:    */   {
/* 357:446 */     path = new Path(conf.getWorkingDirectory(), path);
/* 358:447 */     String dirStr = StringUtils.escapeString(path.toString());
/* 359:448 */     String dirs = conf.get("mapreduce.input.fileinputformat.inputdir");
/* 360:    */     
/* 361:450 */     conf.set("mapreduce.input.fileinputformat.inputdir", dirs + "," + dirStr);
/* 362:    */   }
/* 363:    */   
/* 364:    */   private static String[] getPathStrings(String commaSeparatedPaths)
/* 365:    */   {
/* 366:457 */     int length = commaSeparatedPaths.length();
/* 367:458 */     int curlyOpen = 0;
/* 368:459 */     int pathStart = 0;
/* 369:460 */     boolean globPattern = false;
/* 370:461 */     List<String> pathStrings = new ArrayList();
/* 371:463 */     for (int i = 0; i < length; i++)
/* 372:    */     {
/* 373:464 */       char ch = commaSeparatedPaths.charAt(i);
/* 374:465 */       switch (ch)
/* 375:    */       {
/* 376:    */       case '{': 
/* 377:467 */         curlyOpen++;
/* 378:468 */         if (!globPattern) {
/* 379:469 */           globPattern = true;
/* 380:    */         }
/* 381:    */         break;
/* 382:    */       case '}': 
/* 383:474 */         curlyOpen--;
/* 384:475 */         if ((curlyOpen == 0) && (globPattern)) {
/* 385:476 */           globPattern = false;
/* 386:    */         }
/* 387:    */         break;
/* 388:    */       case ',': 
/* 389:481 */         if (!globPattern)
/* 390:    */         {
/* 391:482 */           pathStrings.add(commaSeparatedPaths.substring(pathStart, i));
/* 392:483 */           pathStart = i + 1;
/* 393:    */         }
/* 394:    */         break;
/* 395:    */       }
/* 396:    */     }
/* 397:491 */     pathStrings.add(commaSeparatedPaths.substring(pathStart, length));
/* 398:    */     
/* 399:493 */     return (String[])pathStrings.toArray(new String[0]);
/* 400:    */   }
/* 401:    */   
/* 402:    */   public static Path[] getInputPaths(JobConf conf)
/* 403:    */   {
/* 404:503 */     String dirs = conf.get("mapreduce.input.fileinputformat.inputdir", "");
/* 405:    */     
/* 406:505 */     String[] list = StringUtils.split(dirs);
/* 407:506 */     Path[] result = new Path[list.length];
/* 408:507 */     for (int i = 0; i < list.length; i++) {
/* 409:508 */       result[i] = new Path(StringUtils.unEscapeString(list[i]));
/* 410:    */     }
/* 411:510 */     return result;
/* 412:    */   }
/* 413:    */   
/* 414:    */   private void sortInDescendingOrder(List<NodeInfo> mylist)
/* 415:    */   {
/* 416:515 */     Collections.sort(mylist, new Comparator()
/* 417:    */     {
/* 418:    */       public int compare(FileInputFormat.NodeInfo obj1, FileInputFormat.NodeInfo obj2)
/* 419:    */       {
/* 420:518 */         if ((obj1 == null) || (obj2 == null)) {
/* 421:519 */           return -1;
/* 422:    */         }
/* 423:521 */         if (obj1.getValue() == obj2.getValue()) {
/* 424:522 */           return 0;
/* 425:    */         }
/* 426:525 */         return obj1.getValue() < obj2.getValue() ? 1 : -1;
/* 427:    */       }
/* 428:    */     });
/* 429:    */   }
/* 430:    */   
/* 431:    */   protected String[] getSplitHosts(BlockLocation[] blkLocations, long offset, long splitSize, NetworkTopology clusterMap)
/* 432:    */     throws IOException
/* 433:    */   {
/* 434:548 */     int startIndex = getBlockIndex(blkLocations, offset);
/* 435:    */     
/* 436:550 */     long bytesInThisBlock = blkLocations[startIndex].getOffset() + blkLocations[startIndex].getLength() - offset;
/* 437:554 */     if (bytesInThisBlock >= splitSize) {
/* 438:555 */       return blkLocations[startIndex].getHosts();
/* 439:    */     }
/* 440:558 */     long bytesInFirstBlock = bytesInThisBlock;
/* 441:559 */     int index = startIndex + 1;
/* 442:560 */     splitSize -= bytesInThisBlock;
/* 443:562 */     while (splitSize > 0L)
/* 444:    */     {
/* 445:563 */       bytesInThisBlock = Math.min(splitSize, blkLocations[(index++)].getLength());
/* 446:    */       
/* 447:565 */       splitSize -= bytesInThisBlock;
/* 448:    */     }
/* 449:568 */     long bytesInLastBlock = bytesInThisBlock;
/* 450:569 */     int endIndex = index - 1;
/* 451:    */     
/* 452:571 */     Map<Node, NodeInfo> hostsMap = new IdentityHashMap();
/* 453:572 */     Map<Node, NodeInfo> racksMap = new IdentityHashMap();
/* 454:573 */     String[] allTopos = new String[0];
/* 455:578 */     for (index = startIndex; index <= endIndex; index++)
/* 456:    */     {
/* 457:581 */       if (index == startIndex) {
/* 458:582 */         bytesInThisBlock = bytesInFirstBlock;
/* 459:584 */       } else if (index == endIndex) {
/* 460:585 */         bytesInThisBlock = bytesInLastBlock;
/* 461:    */       } else {
/* 462:588 */         bytesInThisBlock = blkLocations[index].getLength();
/* 463:    */       }
/* 464:591 */       allTopos = blkLocations[index].getTopologyPaths();
/* 465:595 */       if (allTopos.length == 0) {
/* 466:596 */         allTopos = fakeRacks(blkLocations, index);
/* 467:    */       }
/* 468:604 */       for (String topo : allTopos)
/* 469:    */       {
/* 470:609 */         Node node = clusterMap.getNode(topo);
/* 471:611 */         if (node == null)
/* 472:    */         {
/* 473:612 */           node = new NodeBase(topo);
/* 474:613 */           clusterMap.add(node);
/* 475:    */         }
/* 476:616 */         NodeInfo nodeInfo = (NodeInfo)hostsMap.get(node);
/* 477:    */         NodeInfo parentNodeInfo;
/* 478:618 */         if (nodeInfo == null)
/* 479:    */         {
/* 480:619 */           nodeInfo = new NodeInfo(node);
/* 481:620 */           hostsMap.put(node, nodeInfo);
/* 482:621 */           Node parentNode = node.getParent();
/* 483:622 */           NodeInfo parentNodeInfo = (NodeInfo)racksMap.get(parentNode);
/* 484:623 */           if (parentNodeInfo == null)
/* 485:    */           {
/* 486:624 */             parentNodeInfo = new NodeInfo(parentNode);
/* 487:625 */             racksMap.put(parentNode, parentNodeInfo);
/* 488:    */           }
/* 489:627 */           parentNodeInfo.addLeaf(nodeInfo);
/* 490:    */         }
/* 491:    */         else
/* 492:    */         {
/* 493:630 */           nodeInfo = (NodeInfo)hostsMap.get(node);
/* 494:631 */           Node parentNode = node.getParent();
/* 495:632 */           parentNodeInfo = (NodeInfo)racksMap.get(parentNode);
/* 496:    */         }
/* 497:635 */         nodeInfo.addValue(index, bytesInThisBlock);
/* 498:636 */         parentNodeInfo.addValue(index, bytesInThisBlock);
/* 499:    */       }
/* 500:    */     }
/* 501:642 */     return identifyHosts(allTopos.length, racksMap);
/* 502:    */   }
/* 503:    */   
/* 504:    */   private String[] identifyHosts(int replicationFactor, Map<Node, NodeInfo> racksMap)
/* 505:    */   {
/* 506:648 */     String[] retVal = new String[replicationFactor];
/* 507:    */     
/* 508:650 */     List<NodeInfo> rackList = new LinkedList();
/* 509:    */     
/* 510:652 */     rackList.addAll(racksMap.values());
/* 511:    */     
/* 512:    */ 
/* 513:655 */     sortInDescendingOrder(rackList);
/* 514:    */     
/* 515:657 */     boolean done = false;
/* 516:658 */     int index = 0;
/* 517:662 */     for (NodeInfo ni : rackList)
/* 518:    */     {
/* 519:664 */       Set<NodeInfo> hostSet = ni.getLeaves();
/* 520:    */       
/* 521:666 */       List<NodeInfo> hostList = new LinkedList();
/* 522:667 */       hostList.addAll(hostSet);
/* 523:    */       
/* 524:    */ 
/* 525:670 */       sortInDescendingOrder(hostList);
/* 526:672 */       for (NodeInfo host : hostList)
/* 527:    */       {
/* 528:674 */         retVal[(index++)] = host.node.getName().split(":")[0];
/* 529:675 */         if (index == replicationFactor)
/* 530:    */         {
/* 531:676 */           done = true;
/* 532:677 */           break;
/* 533:    */         }
/* 534:    */       }
/* 535:681 */       if (done == true) {
/* 536:    */         break;
/* 537:    */       }
/* 538:    */     }
/* 539:685 */     return retVal;
/* 540:    */   }
/* 541:    */   
/* 542:    */   private String[] fakeRacks(BlockLocation[] blkLocations, int index)
/* 543:    */     throws IOException
/* 544:    */   {
/* 545:690 */     String[] allHosts = blkLocations[index].getHosts();
/* 546:691 */     String[] allTopos = new String[allHosts.length];
/* 547:692 */     for (int i = 0; i < allHosts.length; i++) {
/* 548:693 */       allTopos[i] = ("/default-rack/" + allHosts[i]);
/* 549:    */     }
/* 550:695 */     return allTopos;
/* 551:    */   }
/* 552:    */   
/* 553:    */   private static class NodeInfo
/* 554:    */   {
/* 555:    */     final Node node;
/* 556:    */     final Set<Integer> blockIds;
/* 557:    */     final Set<NodeInfo> leaves;
/* 558:    */     private long value;
/* 559:    */     
/* 560:    */     NodeInfo(Node node)
/* 561:    */     {
/* 562:707 */       this.node = node;
/* 563:708 */       this.blockIds = new HashSet();
/* 564:709 */       this.leaves = new HashSet();
/* 565:    */     }
/* 566:    */     
/* 567:    */     long getValue()
/* 568:    */     {
/* 569:712 */       return this.value;
/* 570:    */     }
/* 571:    */     
/* 572:    */     void addValue(int blockIndex, long value)
/* 573:    */     {
/* 574:715 */       if (this.blockIds.add(Integer.valueOf(blockIndex)) == true) {
/* 575:716 */         this.value += value;
/* 576:    */       }
/* 577:    */     }
/* 578:    */     
/* 579:    */     Set<NodeInfo> getLeaves()
/* 580:    */     {
/* 581:720 */       return this.leaves;
/* 582:    */     }
/* 583:    */     
/* 584:    */     void addLeaf(NodeInfo nodeInfo)
/* 585:    */     {
/* 586:723 */       this.leaves.add(nodeInfo);
/* 587:    */     }
/* 588:    */   }
/* 589:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.FileInputFormat
 * JD-Core Version:    0.7.0.1
 */