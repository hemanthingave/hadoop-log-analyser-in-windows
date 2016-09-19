/*   1:    */ package org.apache.hadoop.mapreduce.lib.input;
/*   2:    */ 
/*   3:    */ import com.google.common.annotations.VisibleForTesting;
/*   4:    */ import com.google.common.collect.HashMultiset;
/*   5:    */ import com.google.common.collect.Multiset;
/*   6:    */ import java.io.IOException;
/*   7:    */ import java.util.ArrayList;
/*   8:    */ import java.util.Collection;
/*   9:    */ import java.util.Collections;
/*  10:    */ import java.util.HashMap;
/*  11:    */ import java.util.HashSet;
/*  12:    */ import java.util.Iterator;
/*  13:    */ import java.util.LinkedHashSet;
/*  14:    */ import java.util.List;
/*  15:    */ import java.util.Map;
/*  16:    */ import java.util.Map.Entry;
/*  17:    */ import java.util.Set;
/*  18:    */ import org.apache.commons.logging.Log;
/*  19:    */ import org.apache.commons.logging.LogFactory;
/*  20:    */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*  21:    */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*  22:    */ import org.apache.hadoop.conf.Configuration;
/*  23:    */ import org.apache.hadoop.fs.BlockLocation;
/*  24:    */ import org.apache.hadoop.fs.FileStatus;
/*  25:    */ import org.apache.hadoop.fs.FileSystem;
/*  26:    */ import org.apache.hadoop.fs.LocatedFileStatus;
/*  27:    */ import org.apache.hadoop.fs.Path;
/*  28:    */ import org.apache.hadoop.fs.PathFilter;
/*  29:    */ import org.apache.hadoop.io.compress.CompressionCodec;
/*  30:    */ import org.apache.hadoop.io.compress.CompressionCodecFactory;
/*  31:    */ import org.apache.hadoop.io.compress.SplittableCompressionCodec;
/*  32:    */ import org.apache.hadoop.mapreduce.InputSplit;
/*  33:    */ import org.apache.hadoop.mapreduce.JobContext;
/*  34:    */ import org.apache.hadoop.mapreduce.RecordReader;
/*  35:    */ import org.apache.hadoop.mapreduce.TaskAttemptContext;
/*  36:    */ import org.apache.hadoop.net.NodeBase;
/*  37:    */ 
/*  38:    */ @InterfaceAudience.Public
/*  39:    */ @InterfaceStability.Stable
/*  40:    */ public abstract class CombineFileInputFormat<K, V>
/*  41:    */   extends FileInputFormat<K, V>
/*  42:    */ {
/*  43: 88 */   private static final Log LOG = LogFactory.getLog(CombineFileInputFormat.class);
/*  44:    */   public static final String SPLIT_MINSIZE_PERNODE = "mapreduce.input.fileinputformat.split.minsize.per.node";
/*  45:    */   public static final String SPLIT_MINSIZE_PERRACK = "mapreduce.input.fileinputformat.split.minsize.per.rack";
/*  46: 95 */   private long maxSplitSize = 0L;
/*  47: 96 */   private long minSplitSizeNode = 0L;
/*  48: 97 */   private long minSplitSizeRack = 0L;
/*  49:101 */   private ArrayList<MultiPathFilter> pools = new ArrayList();
/*  50:104 */   private HashMap<String, Set<String>> rackToNodes = new HashMap();
/*  51:    */   
/*  52:    */   protected void setMaxSplitSize(long maxSplitSize)
/*  53:    */   {
/*  54:111 */     this.maxSplitSize = maxSplitSize;
/*  55:    */   }
/*  56:    */   
/*  57:    */   protected void setMinSplitSizeNode(long minSplitSizeNode)
/*  58:    */   {
/*  59:122 */     this.minSplitSizeNode = minSplitSizeNode;
/*  60:    */   }
/*  61:    */   
/*  62:    */   protected void setMinSplitSizeRack(long minSplitSizeRack)
/*  63:    */   {
/*  64:133 */     this.minSplitSizeRack = minSplitSizeRack;
/*  65:    */   }
/*  66:    */   
/*  67:    */   protected void createPool(List<PathFilter> filters)
/*  68:    */   {
/*  69:141 */     this.pools.add(new MultiPathFilter(filters));
/*  70:    */   }
/*  71:    */   
/*  72:    */   protected void createPool(PathFilter... filters)
/*  73:    */   {
/*  74:150 */     MultiPathFilter multi = new MultiPathFilter();
/*  75:151 */     for (PathFilter f : filters) {
/*  76:152 */       multi.add(f);
/*  77:    */     }
/*  78:154 */     this.pools.add(multi);
/*  79:    */   }
/*  80:    */   
/*  81:    */   protected boolean isSplitable(JobContext context, Path file)
/*  82:    */   {
/*  83:159 */     CompressionCodec codec = new CompressionCodecFactory(context.getConfiguration()).getCodec(file);
/*  84:161 */     if (null == codec) {
/*  85:162 */       return true;
/*  86:    */     }
/*  87:164 */     return codec instanceof SplittableCompressionCodec;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public List<InputSplit> getSplits(JobContext job)
/*  91:    */     throws IOException
/*  92:    */   {
/*  93:176 */     long minSizeNode = 0L;
/*  94:177 */     long minSizeRack = 0L;
/*  95:178 */     long maxSize = 0L;
/*  96:179 */     Configuration conf = job.getConfiguration();
/*  97:183 */     if (this.minSplitSizeNode != 0L) {
/*  98:184 */       minSizeNode = this.minSplitSizeNode;
/*  99:    */     } else {
/* 100:186 */       minSizeNode = conf.getLong("mapreduce.input.fileinputformat.split.minsize.per.node", 0L);
/* 101:    */     }
/* 102:188 */     if (this.minSplitSizeRack != 0L) {
/* 103:189 */       minSizeRack = this.minSplitSizeRack;
/* 104:    */     } else {
/* 105:191 */       minSizeRack = conf.getLong("mapreduce.input.fileinputformat.split.minsize.per.rack", 0L);
/* 106:    */     }
/* 107:193 */     if (this.maxSplitSize != 0L) {
/* 108:194 */       maxSize = this.maxSplitSize;
/* 109:    */     } else {
/* 110:196 */       maxSize = conf.getLong("mapreduce.input.fileinputformat.split.maxsize", 0L);
/* 111:    */     }
/* 112:200 */     if ((minSizeNode != 0L) && (maxSize != 0L) && (minSizeNode > maxSize)) {
/* 113:201 */       throw new IOException("Minimum split size pernode " + minSizeNode + " cannot be larger than maximum split size " + maxSize);
/* 114:    */     }
/* 115:205 */     if ((minSizeRack != 0L) && (maxSize != 0L) && (minSizeRack > maxSize)) {
/* 116:206 */       throw new IOException("Minimum split size per rack " + minSizeRack + " cannot be larger than maximum split size " + maxSize);
/* 117:    */     }
/* 118:210 */     if ((minSizeRack != 0L) && (minSizeNode > minSizeRack)) {
/* 119:211 */       throw new IOException("Minimum split size per node " + minSizeNode + " cannot be larger than minimum split " + "size per rack " + minSizeRack);
/* 120:    */     }
/* 121:217 */     List<FileStatus> stats = listStatus(job);
/* 122:218 */     List<InputSplit> splits = new ArrayList();
/* 123:219 */     if (stats.size() == 0) {
/* 124:220 */       return splits;
/* 125:    */     }
/* 126:226 */     for (MultiPathFilter onepool : this.pools)
/* 127:    */     {
/* 128:227 */       ArrayList<FileStatus> myPaths = new ArrayList();
/* 129:231 */       for (Iterator<FileStatus> iter = stats.iterator(); iter.hasNext();)
/* 130:    */       {
/* 131:232 */         FileStatus p = (FileStatus)iter.next();
/* 132:233 */         if (onepool.accept(p.getPath()))
/* 133:    */         {
/* 134:234 */           myPaths.add(p);
/* 135:235 */           iter.remove();
/* 136:    */         }
/* 137:    */       }
/* 138:239 */       getMoreSplits(job, myPaths, maxSize, minSizeNode, minSizeRack, splits);
/* 139:    */     }
/* 140:243 */     getMoreSplits(job, stats, maxSize, minSizeNode, minSizeRack, splits);
/* 141:    */     
/* 142:    */ 
/* 143:246 */     this.rackToNodes.clear();
/* 144:247 */     return splits;
/* 145:    */   }
/* 146:    */   
/* 147:    */   private void getMoreSplits(JobContext job, List<FileStatus> stats, long maxSize, long minSizeNode, long minSizeRack, List<InputSplit> splits)
/* 148:    */     throws IOException
/* 149:    */   {
/* 150:257 */     Configuration conf = job.getConfiguration();
/* 151:    */     
/* 152:    */ 
/* 153:    */ 
/* 154:    */ 
/* 155:    */ 
/* 156:263 */     HashMap<String, List<OneBlockInfo>> rackToBlocks = new HashMap();
/* 157:    */     
/* 158:    */ 
/* 159:    */ 
/* 160:267 */     HashMap<OneBlockInfo, String[]> blockToNodes = new HashMap();
/* 161:    */     
/* 162:    */ 
/* 163:    */ 
/* 164:271 */     HashMap<String, Set<OneBlockInfo>> nodeToBlocks = new HashMap();
/* 165:    */     
/* 166:    */ 
/* 167:274 */     OneFileInfo[] files = new OneFileInfo[stats.size()];
/* 168:275 */     if (stats.size() == 0) {
/* 169:276 */       return;
/* 170:    */     }
/* 171:280 */     long totLength = 0L;
/* 172:281 */     int i = 0;
/* 173:282 */     for (FileStatus stat : stats)
/* 174:    */     {
/* 175:283 */       files[i] = new OneFileInfo(stat, conf, isSplitable(job, stat.getPath()), rackToBlocks, blockToNodes, nodeToBlocks, this.rackToNodes, maxSize);
/* 176:    */       
/* 177:    */ 
/* 178:286 */       totLength += files[i].getLength();
/* 179:    */     }
/* 180:288 */     createSplits(nodeToBlocks, blockToNodes, rackToBlocks, totLength, maxSize, minSizeNode, minSizeRack, splits);
/* 181:    */   }
/* 182:    */   
/* 183:    */   @VisibleForTesting
/* 184:    */   void createSplits(Map<String, Set<OneBlockInfo>> nodeToBlocks, Map<OneBlockInfo, String[]> blockToNodes, Map<String, List<OneBlockInfo>> rackToBlocks, long totLength, long maxSize, long minSizeNode, long minSizeRack, List<InputSplit> splits)
/* 185:    */   {
/* 186:302 */     ArrayList<OneBlockInfo> validBlocks = new ArrayList();
/* 187:303 */     long curSplitSize = 0L;
/* 188:    */     
/* 189:305 */     int totalNodes = nodeToBlocks.size();
/* 190:306 */     long totalLength = totLength;
/* 191:    */     
/* 192:308 */     Multiset<String> splitsPerNode = HashMultiset.create();
/* 193:309 */     Set<String> completedNodes = new HashSet();
/* 194:    */     do
/* 195:    */     {
/* 196:317 */       Iterator<Map.Entry<String, Set<OneBlockInfo>>> iter = nodeToBlocks.entrySet().iterator();
/* 197:318 */       while (iter.hasNext())
/* 198:    */       {
/* 199:319 */         Map.Entry<String, Set<OneBlockInfo>> one = (Map.Entry)iter.next();
/* 200:    */         
/* 201:321 */         String node = (String)one.getKey();
/* 202:324 */         if (!completedNodes.contains(node))
/* 203:    */         {
/* 204:328 */           Set<OneBlockInfo> blocksInCurrentNode = (Set)one.getValue();
/* 205:    */           
/* 206:    */ 
/* 207:    */ 
/* 208:    */ 
/* 209:333 */           Iterator<OneBlockInfo> oneBlockIter = blocksInCurrentNode.iterator();
/* 210:334 */           while (oneBlockIter.hasNext())
/* 211:    */           {
/* 212:335 */             OneBlockInfo oneblock = (OneBlockInfo)oneBlockIter.next();
/* 213:339 */             if (!blockToNodes.containsKey(oneblock))
/* 214:    */             {
/* 215:340 */               oneBlockIter.remove();
/* 216:    */             }
/* 217:    */             else
/* 218:    */             {
/* 219:344 */               validBlocks.add(oneblock);
/* 220:345 */               blockToNodes.remove(oneblock);
/* 221:346 */               curSplitSize += oneblock.length;
/* 222:350 */               if ((maxSize != 0L) && (curSplitSize >= maxSize))
/* 223:    */               {
/* 224:352 */                 addCreatedSplit(splits, Collections.singleton(node), validBlocks);
/* 225:353 */                 totalLength -= curSplitSize;
/* 226:354 */                 curSplitSize = 0L;
/* 227:    */                 
/* 228:356 */                 splitsPerNode.add(node);
/* 229:    */                 
/* 230:    */ 
/* 231:    */ 
/* 232:360 */                 blocksInCurrentNode.removeAll(validBlocks);
/* 233:361 */                 validBlocks.clear();
/* 234:    */                 
/* 235:    */ 
/* 236:    */ 
/* 237:365 */                 break;
/* 238:    */               }
/* 239:    */             }
/* 240:    */           }
/* 241:369 */           if (validBlocks.size() != 0)
/* 242:    */           {
/* 243:380 */             if ((minSizeNode != 0L) && (curSplitSize >= minSizeNode) && (splitsPerNode.count(node) == 0))
/* 244:    */             {
/* 245:386 */               addCreatedSplit(splits, Collections.singleton(node), validBlocks);
/* 246:387 */               totalLength -= curSplitSize;
/* 247:388 */               splitsPerNode.add(node);
/* 248:    */               
/* 249:390 */               blocksInCurrentNode.removeAll(validBlocks);
/* 250:    */             }
/* 251:    */             else
/* 252:    */             {
/* 253:394 */               for (OneBlockInfo oneblock : validBlocks) {
/* 254:395 */                 blockToNodes.put(oneblock, oneblock.hosts);
/* 255:    */               }
/* 256:    */             }
/* 257:398 */             validBlocks.clear();
/* 258:399 */             curSplitSize = 0L;
/* 259:400 */             completedNodes.add(node);
/* 260:    */           }
/* 261:402 */           else if (blocksInCurrentNode.size() == 0)
/* 262:    */           {
/* 263:404 */             completedNodes.add(node);
/* 264:    */           }
/* 265:    */         }
/* 266:    */       }
/* 267:410 */     } while ((completedNodes.size() != totalNodes) && (totalLength != 0L));
/* 268:413 */     LOG.info("DEBUG: Terminated node allocation with : CompletedNodes: " + completedNodes.size() + ", size left: " + totalLength);
/* 269:    */     
/* 270:    */ 
/* 271:    */ 
/* 272:    */ 
/* 273:    */ 
/* 274:    */ 
/* 275:    */ 
/* 276:    */ 
/* 277:422 */     ArrayList<OneBlockInfo> overflowBlocks = new ArrayList();
/* 278:423 */     Set<String> racks = new HashSet();
/* 279:426 */     while (blockToNodes.size() > 0)
/* 280:    */     {
/* 281:436 */       Iterator<Map.Entry<String, List<OneBlockInfo>>> iter = rackToBlocks.entrySet().iterator();
/* 282:437 */       while (iter.hasNext())
/* 283:    */       {
/* 284:439 */         Map.Entry<String, List<OneBlockInfo>> one = (Map.Entry)iter.next();
/* 285:440 */         racks.add(one.getKey());
/* 286:441 */         List<OneBlockInfo> blocks = (List)one.getValue();
/* 287:    */         
/* 288:    */ 
/* 289:    */ 
/* 290:    */ 
/* 291:446 */         boolean createdSplit = false;
/* 292:447 */         for (OneBlockInfo oneblock : blocks) {
/* 293:448 */           if (blockToNodes.containsKey(oneblock))
/* 294:    */           {
/* 295:449 */             validBlocks.add(oneblock);
/* 296:450 */             blockToNodes.remove(oneblock);
/* 297:451 */             curSplitSize += oneblock.length;
/* 298:455 */             if ((maxSize != 0L) && (curSplitSize >= maxSize))
/* 299:    */             {
/* 300:457 */               addCreatedSplit(splits, getHosts(racks), validBlocks);
/* 301:458 */               createdSplit = true;
/* 302:459 */               break;
/* 303:    */             }
/* 304:    */           }
/* 305:    */         }
/* 306:465 */         if (createdSplit)
/* 307:    */         {
/* 308:466 */           curSplitSize = 0L;
/* 309:467 */           validBlocks.clear();
/* 310:468 */           racks.clear();
/* 311:    */         }
/* 312:    */         else
/* 313:    */         {
/* 314:472 */           if (!validBlocks.isEmpty()) {
/* 315:473 */             if ((minSizeRack != 0L) && (curSplitSize >= minSizeRack)) {
/* 316:476 */               addCreatedSplit(splits, getHosts(racks), validBlocks);
/* 317:    */             } else {
/* 318:481 */               overflowBlocks.addAll(validBlocks);
/* 319:    */             }
/* 320:    */           }
/* 321:484 */           curSplitSize = 0L;
/* 322:485 */           validBlocks.clear();
/* 323:486 */           racks.clear();
/* 324:    */         }
/* 325:    */       }
/* 326:    */     }
/* 327:490 */     assert (blockToNodes.isEmpty());
/* 328:491 */     assert (curSplitSize == 0L);
/* 329:492 */     assert (validBlocks.isEmpty());
/* 330:493 */     assert (racks.isEmpty());
/* 331:496 */     for (OneBlockInfo oneblock : overflowBlocks)
/* 332:    */     {
/* 333:497 */       validBlocks.add(oneblock);
/* 334:498 */       curSplitSize += oneblock.length;
/* 335:502 */       for (int i = 0; i < oneblock.racks.length; i++) {
/* 336:503 */         racks.add(oneblock.racks[i]);
/* 337:    */       }
/* 338:508 */       if ((maxSize != 0L) && (curSplitSize >= maxSize))
/* 339:    */       {
/* 340:510 */         addCreatedSplit(splits, getHosts(racks), validBlocks);
/* 341:511 */         curSplitSize = 0L;
/* 342:512 */         validBlocks.clear();
/* 343:513 */         racks.clear();
/* 344:    */       }
/* 345:    */     }
/* 346:518 */     if (!validBlocks.isEmpty()) {
/* 347:519 */       addCreatedSplit(splits, getHosts(racks), validBlocks);
/* 348:    */     }
/* 349:    */   }
/* 350:    */   
/* 351:    */   private void addCreatedSplit(List<InputSplit> splitList, Collection<String> locations, ArrayList<OneBlockInfo> validBlocks)
/* 352:    */   {
/* 353:531 */     Path[] fl = new Path[validBlocks.size()];
/* 354:532 */     long[] offset = new long[validBlocks.size()];
/* 355:533 */     long[] length = new long[validBlocks.size()];
/* 356:534 */     for (int i = 0; i < validBlocks.size(); i++)
/* 357:    */     {
/* 358:535 */       fl[i] = ((OneBlockInfo)validBlocks.get(i)).onepath;
/* 359:536 */       offset[i] = ((OneBlockInfo)validBlocks.get(i)).offset;
/* 360:537 */       length[i] = ((OneBlockInfo)validBlocks.get(i)).length;
/* 361:    */     }
/* 362:540 */     CombineFileSplit thissplit = new CombineFileSplit(fl, offset, length, (String[])locations.toArray(new String[0]));
/* 363:    */     
/* 364:542 */     splitList.add(thissplit);
/* 365:    */   }
/* 366:    */   
/* 367:    */   public abstract RecordReader<K, V> createRecordReader(InputSplit paramInputSplit, TaskAttemptContext paramTaskAttemptContext)
/* 368:    */     throws IOException;
/* 369:    */   
/* 370:    */   @VisibleForTesting
/* 371:    */   static class OneFileInfo
/* 372:    */   {
/* 373:567 */     private long fileSize = 0L;
/* 374:    */     private CombineFileInputFormat.OneBlockInfo[] blocks;
/* 375:    */     
/* 376:    */     OneFileInfo(FileStatus stat, Configuration conf, boolean isSplitable, HashMap<String, List<CombineFileInputFormat.OneBlockInfo>> rackToBlocks, HashMap<CombineFileInputFormat.OneBlockInfo, String[]> blockToNodes, HashMap<String, Set<CombineFileInputFormat.OneBlockInfo>> nodeToBlocks, HashMap<String, Set<String>> rackToNodes, long maxSize)
/* 377:    */       throws IOException
/* 378:    */     {
/* 379:    */       BlockLocation[] locations;
/* 380:    */       BlockLocation[] locations;
/* 381:571 */       if ((stat instanceof LocatedFileStatus))
/* 382:    */       {
/* 383:572 */         locations = ((LocatedFileStatus)stat).getBlockLocations();
/* 384:    */       }
/* 385:    */       else
/* 386:    */       {
/* 387:574 */         FileSystem fs = stat.getPath().getFileSystem(conf);
/* 388:575 */         locations = fs.getFileBlockLocations(stat, 0L, stat.getLen());
/* 389:    */       }
/* 390:578 */       if (locations == null)
/* 391:    */       {
/* 392:579 */         this.blocks = new CombineFileInputFormat.OneBlockInfo[0];
/* 393:    */       }
/* 394:    */       else
/* 395:    */       {
/* 396:582 */         if (locations.length == 0) {
/* 397:583 */           locations = new BlockLocation[] { new BlockLocation() };
/* 398:    */         }
/* 399:586 */         if (!isSplitable)
/* 400:    */         {
/* 401:589 */           this.blocks = new CombineFileInputFormat.OneBlockInfo[1];
/* 402:590 */           this.fileSize = stat.getLen();
/* 403:591 */           this.blocks[0] = new CombineFileInputFormat.OneBlockInfo(stat.getPath(), 0L, this.fileSize, locations[0].getHosts(), locations[0].getTopologyPaths());
/* 404:    */         }
/* 405:    */         else
/* 406:    */         {
/* 407:594 */           ArrayList<CombineFileInputFormat.OneBlockInfo> blocksList = new ArrayList(locations.length);
/* 408:596 */           for (int i = 0; i < locations.length; i++)
/* 409:    */           {
/* 410:597 */             this.fileSize += locations[i].getLength();
/* 411:    */             
/* 412:    */ 
/* 413:600 */             long left = locations[i].getLength();
/* 414:601 */             long myOffset = locations[i].getOffset();
/* 415:602 */             long myLength = 0L;
/* 416:    */             do
/* 417:    */             {
/* 418:604 */               if (maxSize == 0L) {
/* 419:605 */                 myLength = left;
/* 420:607 */               } else if ((left > maxSize) && (left < 2L * maxSize)) {
/* 421:613 */                 myLength = left / 2L;
/* 422:    */               } else {
/* 423:615 */                 myLength = Math.min(maxSize, left);
/* 424:    */               }
/* 425:618 */               CombineFileInputFormat.OneBlockInfo oneblock = new CombineFileInputFormat.OneBlockInfo(stat.getPath(), myOffset, myLength, locations[i].getHosts(), locations[i].getTopologyPaths());
/* 426:    */               
/* 427:    */ 
/* 428:621 */               left -= myLength;
/* 429:622 */               myOffset += myLength;
/* 430:    */               
/* 431:624 */               blocksList.add(oneblock);
/* 432:625 */             } while (left > 0L);
/* 433:    */           }
/* 434:627 */           this.blocks = ((CombineFileInputFormat.OneBlockInfo[])blocksList.toArray(new CombineFileInputFormat.OneBlockInfo[blocksList.size()]));
/* 435:    */         }
/* 436:630 */         populateBlockInfo(this.blocks, rackToBlocks, blockToNodes, nodeToBlocks, rackToNodes);
/* 437:    */       }
/* 438:    */     }
/* 439:    */     
/* 440:    */     @VisibleForTesting
/* 441:    */     static void populateBlockInfo(CombineFileInputFormat.OneBlockInfo[] blocks, Map<String, List<CombineFileInputFormat.OneBlockInfo>> rackToBlocks, Map<CombineFileInputFormat.OneBlockInfo, String[]> blockToNodes, Map<String, Set<CombineFileInputFormat.OneBlockInfo>> nodeToBlocks, Map<String, Set<String>> rackToNodes)
/* 442:    */     {
/* 443:641 */       for (CombineFileInputFormat.OneBlockInfo oneblock : blocks)
/* 444:    */       {
/* 445:643 */         blockToNodes.put(oneblock, oneblock.hosts);
/* 446:    */         
/* 447:    */ 
/* 448:    */ 
/* 449:647 */         String[] racks = null;
/* 450:648 */         if (oneblock.hosts.length == 0) {
/* 451:649 */           racks = new String[] { "/default-rack" };
/* 452:    */         } else {
/* 453:651 */           racks = oneblock.racks;
/* 454:    */         }
/* 455:655 */         for (int j = 0; j < racks.length; j++)
/* 456:    */         {
/* 457:656 */           String rack = racks[j];
/* 458:657 */           List<CombineFileInputFormat.OneBlockInfo> blklist = (List)rackToBlocks.get(rack);
/* 459:658 */           if (blklist == null)
/* 460:    */           {
/* 461:659 */             blklist = new ArrayList();
/* 462:660 */             rackToBlocks.put(rack, blklist);
/* 463:    */           }
/* 464:662 */           blklist.add(oneblock);
/* 465:663 */           if (!racks[j].equals("/default-rack")) {
/* 466:665 */             CombineFileInputFormat.addHostToRack(rackToNodes, racks[j], oneblock.hosts[j]);
/* 467:    */           }
/* 468:    */         }
/* 469:670 */         for (int j = 0; j < oneblock.hosts.length; j++)
/* 470:    */         {
/* 471:671 */           String node = oneblock.hosts[j];
/* 472:672 */           Set<CombineFileInputFormat.OneBlockInfo> blklist = (Set)nodeToBlocks.get(node);
/* 473:673 */           if (blklist == null)
/* 474:    */           {
/* 475:674 */             blklist = new LinkedHashSet();
/* 476:675 */             nodeToBlocks.put(node, blklist);
/* 477:    */           }
/* 478:677 */           blklist.add(oneblock);
/* 479:    */         }
/* 480:    */       }
/* 481:    */     }
/* 482:    */     
/* 483:    */     long getLength()
/* 484:    */     {
/* 485:683 */       return this.fileSize;
/* 486:    */     }
/* 487:    */     
/* 488:    */     CombineFileInputFormat.OneBlockInfo[] getBlocks()
/* 489:    */     {
/* 490:687 */       return this.blocks;
/* 491:    */     }
/* 492:    */   }
/* 493:    */   
/* 494:    */   @VisibleForTesting
/* 495:    */   static class OneBlockInfo
/* 496:    */   {
/* 497:    */     Path onepath;
/* 498:    */     long offset;
/* 499:    */     long length;
/* 500:    */     String[] hosts;
/* 501:    */     String[] racks;
/* 502:    */     
/* 503:    */     OneBlockInfo(Path path, long offset, long len, String[] hosts, String[] topologyPaths)
/* 504:    */     {
/* 505:704 */       this.onepath = path;
/* 506:705 */       this.offset = offset;
/* 507:706 */       this.hosts = hosts;
/* 508:707 */       this.length = len;
/* 509:708 */       assert ((hosts.length == topologyPaths.length) || (topologyPaths.length == 0));
/* 510:713 */       if (topologyPaths.length == 0)
/* 511:    */       {
/* 512:714 */         topologyPaths = new String[hosts.length];
/* 513:715 */         for (int i = 0; i < topologyPaths.length; i++) {
/* 514:716 */           topologyPaths[i] = new NodeBase(hosts[i], "/default-rack").toString();
/* 515:    */         }
/* 516:    */       }
/* 517:723 */       this.racks = new String[topologyPaths.length];
/* 518:724 */       for (int i = 0; i < topologyPaths.length; i++) {
/* 519:725 */         this.racks[i] = new NodeBase(topologyPaths[i]).getNetworkLocation();
/* 520:    */       }
/* 521:    */     }
/* 522:    */   }
/* 523:    */   
/* 524:    */   protected BlockLocation[] getFileBlockLocations(FileSystem fs, FileStatus stat)
/* 525:    */     throws IOException
/* 526:    */   {
/* 527:732 */     if ((stat instanceof LocatedFileStatus)) {
/* 528:733 */       return ((LocatedFileStatus)stat).getBlockLocations();
/* 529:    */     }
/* 530:735 */     return fs.getFileBlockLocations(stat, 0L, stat.getLen());
/* 531:    */   }
/* 532:    */   
/* 533:    */   private static void addHostToRack(Map<String, Set<String>> rackToNodes, String rack, String host)
/* 534:    */   {
/* 535:740 */     Set<String> hosts = (Set)rackToNodes.get(rack);
/* 536:741 */     if (hosts == null)
/* 537:    */     {
/* 538:742 */       hosts = new HashSet();
/* 539:743 */       rackToNodes.put(rack, hosts);
/* 540:    */     }
/* 541:745 */     hosts.add(host);
/* 542:    */   }
/* 543:    */   
/* 544:    */   private Set<String> getHosts(Set<String> racks)
/* 545:    */   {
/* 546:749 */     Set<String> hosts = new HashSet();
/* 547:750 */     for (String rack : racks) {
/* 548:751 */       if (this.rackToNodes.containsKey(rack)) {
/* 549:752 */         hosts.addAll((Collection)this.rackToNodes.get(rack));
/* 550:    */       }
/* 551:    */     }
/* 552:755 */     return hosts;
/* 553:    */   }
/* 554:    */   
/* 555:    */   private static class MultiPathFilter
/* 556:    */     implements PathFilter
/* 557:    */   {
/* 558:    */     private List<PathFilter> filters;
/* 559:    */     
/* 560:    */     public MultiPathFilter()
/* 561:    */     {
/* 562:766 */       this.filters = new ArrayList();
/* 563:    */     }
/* 564:    */     
/* 565:    */     public MultiPathFilter(List<PathFilter> filters)
/* 566:    */     {
/* 567:770 */       this.filters = filters;
/* 568:    */     }
/* 569:    */     
/* 570:    */     public void add(PathFilter one)
/* 571:    */     {
/* 572:774 */       this.filters.add(one);
/* 573:    */     }
/* 574:    */     
/* 575:    */     public boolean accept(Path path)
/* 576:    */     {
/* 577:778 */       for (PathFilter filter : this.filters) {
/* 578:779 */         if (filter.accept(path)) {
/* 579:780 */           return true;
/* 580:    */         }
/* 581:    */       }
/* 582:783 */       return false;
/* 583:    */     }
/* 584:    */     
/* 585:    */     public String toString()
/* 586:    */     {
/* 587:787 */       StringBuffer buf = new StringBuffer();
/* 588:788 */       buf.append("[");
/* 589:789 */       for (PathFilter f : this.filters)
/* 590:    */       {
/* 591:790 */         buf.append(f);
/* 592:791 */         buf.append(",");
/* 593:    */       }
/* 594:793 */       buf.append("]");
/* 595:794 */       return buf.toString();
/* 596:    */     }
/* 597:    */   }
/* 598:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.lib.input.CombineFileInputFormat
 * JD-Core Version:    0.7.0.1
 */