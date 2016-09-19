/*   1:    */ package org.apache.hadoop.mapreduce.lib.input;
/*   2:    */ 
/*   3:    */ import com.google.common.base.Stopwatch;
/*   4:    */ import com.google.common.collect.Lists;
/*   5:    */ import java.io.IOException;
/*   6:    */ import java.util.ArrayList;
/*   7:    */ import java.util.List;
/*   8:    */ import org.apache.commons.logging.Log;
/*   9:    */ import org.apache.commons.logging.LogFactory;
/*  10:    */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*  11:    */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*  12:    */ import org.apache.hadoop.conf.Configuration;
/*  13:    */ import org.apache.hadoop.fs.BlockLocation;
/*  14:    */ import org.apache.hadoop.fs.FileStatus;
/*  15:    */ import org.apache.hadoop.fs.FileSystem;
/*  16:    */ import org.apache.hadoop.fs.LocatedFileStatus;
/*  17:    */ import org.apache.hadoop.fs.Path;
/*  18:    */ import org.apache.hadoop.fs.PathFilter;
/*  19:    */ import org.apache.hadoop.fs.RemoteIterator;
/*  20:    */ import org.apache.hadoop.mapred.LocatedFileStatusFetcher;
/*  21:    */ import org.apache.hadoop.mapreduce.InputFormat;
/*  22:    */ import org.apache.hadoop.mapreduce.InputSplit;
/*  23:    */ import org.apache.hadoop.mapreduce.Job;
/*  24:    */ import org.apache.hadoop.mapreduce.JobContext;
/*  25:    */ import org.apache.hadoop.mapreduce.security.TokenCache;
/*  26:    */ import org.apache.hadoop.util.ReflectionUtils;
/*  27:    */ import org.apache.hadoop.util.StringUtils;
/*  28:    */ 
/*  29:    */ @InterfaceAudience.Public
/*  30:    */ @InterfaceStability.Stable
/*  31:    */ public abstract class FileInputFormat<K, V>
/*  32:    */   extends InputFormat<K, V>
/*  33:    */ {
/*  34:    */   public static final String INPUT_DIR = "mapreduce.input.fileinputformat.inputdir";
/*  35:    */   public static final String SPLIT_MAXSIZE = "mapreduce.input.fileinputformat.split.maxsize";
/*  36:    */   public static final String SPLIT_MINSIZE = "mapreduce.input.fileinputformat.split.minsize";
/*  37:    */   public static final String PATHFILTER_CLASS = "mapreduce.input.pathFilter.class";
/*  38:    */   public static final String NUM_INPUT_FILES = "mapreduce.input.fileinputformat.numinputfiles";
/*  39:    */   public static final String INPUT_DIR_RECURSIVE = "mapreduce.input.fileinputformat.input.dir.recursive";
/*  40:    */   public static final String LIST_STATUS_NUM_THREADS = "mapreduce.input.fileinputformat.list-status.num-threads";
/*  41:    */   public static final int DEFAULT_LIST_STATUS_NUM_THREADS = 1;
/*  42: 79 */   private static final Log LOG = LogFactory.getLog(FileInputFormat.class);
/*  43:    */   private static final double SPLIT_SLOP = 1.1D;
/*  44:    */   
/*  45:    */   @Deprecated
/*  46:    */   public static enum Counter
/*  47:    */   {
/*  48: 85 */     BYTES_READ;
/*  49:    */     
/*  50:    */     private Counter() {}
/*  51:    */   }
/*  52:    */   
/*  53: 88 */   private static final PathFilter hiddenFileFilter = new PathFilter()
/*  54:    */   {
/*  55:    */     public boolean accept(Path p)
/*  56:    */     {
/*  57: 90 */       String name = p.getName();
/*  58: 91 */       return (!name.startsWith("_")) && (!name.startsWith("."));
/*  59:    */     }
/*  60:    */   };
/*  61:    */   
/*  62:    */   private static class MultiPathFilter
/*  63:    */     implements PathFilter
/*  64:    */   {
/*  65:    */     private List<PathFilter> filters;
/*  66:    */     
/*  67:    */     public MultiPathFilter(List<PathFilter> filters)
/*  68:    */     {
/*  69:104 */       this.filters = filters;
/*  70:    */     }
/*  71:    */     
/*  72:    */     public boolean accept(Path path)
/*  73:    */     {
/*  74:108 */       for (PathFilter filter : this.filters) {
/*  75:109 */         if (!filter.accept(path)) {
/*  76:110 */           return false;
/*  77:    */         }
/*  78:    */       }
/*  79:113 */       return true;
/*  80:    */     }
/*  81:    */   }
/*  82:    */   
/*  83:    */   public static void setInputDirRecursive(Job job, boolean inputDirRecursive)
/*  84:    */   {
/*  85:124 */     job.getConfiguration().setBoolean("mapreduce.input.fileinputformat.input.dir.recursive", inputDirRecursive);
/*  86:    */   }
/*  87:    */   
/*  88:    */   public static boolean getInputDirRecursive(JobContext job)
/*  89:    */   {
/*  90:134 */     return job.getConfiguration().getBoolean("mapreduce.input.fileinputformat.input.dir.recursive", false);
/*  91:    */   }
/*  92:    */   
/*  93:    */   protected long getFormatMinSplitSize()
/*  94:    */   {
/*  95:143 */     return 1L;
/*  96:    */   }
/*  97:    */   
/*  98:    */   protected boolean isSplitable(JobContext context, Path filename)
/*  99:    */   {
/* 100:159 */     return true;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public static void setInputPathFilter(Job job, Class<? extends PathFilter> filter)
/* 104:    */   {
/* 105:169 */     job.getConfiguration().setClass("mapreduce.input.pathFilter.class", filter, PathFilter.class);
/* 106:    */   }
/* 107:    */   
/* 108:    */   public static void setMinInputSplitSize(Job job, long size)
/* 109:    */   {
/* 110:180 */     job.getConfiguration().setLong("mapreduce.input.fileinputformat.split.minsize", size);
/* 111:    */   }
/* 112:    */   
/* 113:    */   public static long getMinSplitSize(JobContext job)
/* 114:    */   {
/* 115:189 */     return job.getConfiguration().getLong("mapreduce.input.fileinputformat.split.minsize", 1L);
/* 116:    */   }
/* 117:    */   
/* 118:    */   public static void setMaxInputSplitSize(Job job, long size)
/* 119:    */   {
/* 120:199 */     job.getConfiguration().setLong("mapreduce.input.fileinputformat.split.maxsize", size);
/* 121:    */   }
/* 122:    */   
/* 123:    */   public static long getMaxSplitSize(JobContext context)
/* 124:    */   {
/* 125:208 */     return context.getConfiguration().getLong("mapreduce.input.fileinputformat.split.maxsize", 9223372036854775807L);
/* 126:    */   }
/* 127:    */   
/* 128:    */   public static PathFilter getInputPathFilter(JobContext context)
/* 129:    */   {
/* 130:218 */     Configuration conf = context.getConfiguration();
/* 131:219 */     Class<?> filterClass = conf.getClass("mapreduce.input.pathFilter.class", null, PathFilter.class);
/* 132:    */     
/* 133:221 */     return filterClass != null ? (PathFilter)ReflectionUtils.newInstance(filterClass, conf) : null;
/* 134:    */   }
/* 135:    */   
/* 136:    */   protected List<FileStatus> listStatus(JobContext job)
/* 137:    */     throws IOException
/* 138:    */   {
/* 139:235 */     Path[] dirs = getInputPaths(job);
/* 140:236 */     if (dirs.length == 0) {
/* 141:237 */       throw new IOException("No input paths specified in job");
/* 142:    */     }
/* 143:241 */     TokenCache.obtainTokensForNamenodes(job.getCredentials(), dirs, job.getConfiguration());
/* 144:    */     
/* 145:    */ 
/* 146:    */ 
/* 147:245 */     boolean recursive = getInputDirRecursive(job);
/* 148:    */     
/* 149:    */ 
/* 150:    */ 
/* 151:249 */     List<PathFilter> filters = new ArrayList();
/* 152:250 */     filters.add(hiddenFileFilter);
/* 153:251 */     PathFilter jobFilter = getInputPathFilter(job);
/* 154:252 */     if (jobFilter != null) {
/* 155:253 */       filters.add(jobFilter);
/* 156:    */     }
/* 157:255 */     PathFilter inputFilter = new MultiPathFilter(filters);
/* 158:    */     
/* 159:257 */     List<FileStatus> result = null;
/* 160:    */     
/* 161:259 */     int numThreads = job.getConfiguration().getInt("mapreduce.input.fileinputformat.list-status.num-threads", 1);
/* 162:    */     
/* 163:261 */     Stopwatch sw = new Stopwatch().start();
/* 164:262 */     if (numThreads == 1)
/* 165:    */     {
/* 166:263 */       result = singleThreadedListStatus(job, dirs, inputFilter, recursive);
/* 167:    */     }
/* 168:    */     else
/* 169:    */     {
/* 170:265 */       Iterable<FileStatus> locatedFiles = null;
/* 171:    */       try
/* 172:    */       {
/* 173:267 */         LocatedFileStatusFetcher locatedFileStatusFetcher = new LocatedFileStatusFetcher(job.getConfiguration(), dirs, recursive, inputFilter, true);
/* 174:    */         
/* 175:269 */         locatedFiles = locatedFileStatusFetcher.getFileStatuses();
/* 176:    */       }
/* 177:    */       catch (InterruptedException e)
/* 178:    */       {
/* 179:271 */         throw new IOException("Interrupted while getting file statuses");
/* 180:    */       }
/* 181:273 */       result = Lists.newArrayList(locatedFiles);
/* 182:    */     }
/* 183:276 */     sw.stop();
/* 184:277 */     if (LOG.isDebugEnabled()) {
/* 185:278 */       LOG.debug("Time taken to get FileStatuses: " + sw.elapsedMillis());
/* 186:    */     }
/* 187:280 */     LOG.info("Total input paths to process : " + result.size());
/* 188:281 */     return result;
/* 189:    */   }
/* 190:    */   
/* 191:    */   private List<FileStatus> singleThreadedListStatus(JobContext job, Path[] dirs, PathFilter inputFilter, boolean recursive)
/* 192:    */     throws IOException
/* 193:    */   {
/* 194:286 */     List<FileStatus> result = new ArrayList();
/* 195:287 */     List<IOException> errors = new ArrayList();
/* 196:288 */     for (int i = 0; i < dirs.length; i++)
/* 197:    */     {
/* 198:289 */       Path p = dirs[i];
/* 199:290 */       FileSystem fs = p.getFileSystem(job.getConfiguration());
/* 200:291 */       FileStatus[] matches = fs.globStatus(p, inputFilter);
/* 201:292 */       if (matches == null) {
/* 202:293 */         errors.add(new IOException("Input path does not exist: " + p));
/* 203:294 */       } else if (matches.length == 0) {
/* 204:295 */         errors.add(new IOException("Input Pattern " + p + " matches 0 files"));
/* 205:    */       } else {
/* 206:297 */         for (FileStatus globStat : matches) {
/* 207:298 */           if (globStat.isDirectory())
/* 208:    */           {
/* 209:299 */             RemoteIterator<LocatedFileStatus> iter = fs.listLocatedStatus(globStat.getPath());
/* 210:301 */             while (iter.hasNext())
/* 211:    */             {
/* 212:302 */               LocatedFileStatus stat = (LocatedFileStatus)iter.next();
/* 213:303 */               if (inputFilter.accept(stat.getPath())) {
/* 214:304 */                 if ((recursive) && (stat.isDirectory())) {
/* 215:305 */                   addInputPathRecursively(result, fs, stat.getPath(), inputFilter);
/* 216:    */                 } else {
/* 217:308 */                   result.add(stat);
/* 218:    */                 }
/* 219:    */               }
/* 220:    */             }
/* 221:    */           }
/* 222:    */           else
/* 223:    */           {
/* 224:313 */             result.add(globStat);
/* 225:    */           }
/* 226:    */         }
/* 227:    */       }
/* 228:    */     }
/* 229:319 */     if (!errors.isEmpty()) {
/* 230:320 */       throw new InvalidInputException(errors);
/* 231:    */     }
/* 232:322 */     return result;
/* 233:    */   }
/* 234:    */   
/* 235:    */   protected void addInputPathRecursively(List<FileStatus> result, FileSystem fs, Path path, PathFilter inputFilter)
/* 236:    */     throws IOException
/* 237:    */   {
/* 238:340 */     RemoteIterator<LocatedFileStatus> iter = fs.listLocatedStatus(path);
/* 239:341 */     while (iter.hasNext())
/* 240:    */     {
/* 241:342 */       LocatedFileStatus stat = (LocatedFileStatus)iter.next();
/* 242:343 */       if (inputFilter.accept(stat.getPath())) {
/* 243:344 */         if (stat.isDirectory()) {
/* 244:345 */           addInputPathRecursively(result, fs, stat.getPath(), inputFilter);
/* 245:    */         } else {
/* 246:347 */           result.add(stat);
/* 247:    */         }
/* 248:    */       }
/* 249:    */     }
/* 250:    */   }
/* 251:    */   
/* 252:    */   protected FileSplit makeSplit(Path file, long start, long length, String[] hosts)
/* 253:    */   {
/* 254:360 */     return new FileSplit(file, start, length, hosts);
/* 255:    */   }
/* 256:    */   
/* 257:    */   public List<InputSplit> getSplits(JobContext job)
/* 258:    */     throws IOException
/* 259:    */   {
/* 260:369 */     Stopwatch sw = new Stopwatch().start();
/* 261:370 */     long minSize = Math.max(getFormatMinSplitSize(), getMinSplitSize(job));
/* 262:371 */     long maxSize = getMaxSplitSize(job);
/* 263:    */     
/* 264:    */ 
/* 265:374 */     List<InputSplit> splits = new ArrayList();
/* 266:375 */     List<FileStatus> files = listStatus(job);
/* 267:376 */     for (FileStatus file : files)
/* 268:    */     {
/* 269:377 */       Path path = file.getPath();
/* 270:378 */       long length = file.getLen();
/* 271:379 */       if (length != 0L)
/* 272:    */       {
/* 273:    */         BlockLocation[] blkLocations;
/* 274:    */         BlockLocation[] blkLocations;
/* 275:381 */         if ((file instanceof LocatedFileStatus))
/* 276:    */         {
/* 277:382 */           blkLocations = ((LocatedFileStatus)file).getBlockLocations();
/* 278:    */         }
/* 279:    */         else
/* 280:    */         {
/* 281:384 */           FileSystem fs = path.getFileSystem(job.getConfiguration());
/* 282:385 */           blkLocations = fs.getFileBlockLocations(file, 0L, length);
/* 283:    */         }
/* 284:387 */         if (isSplitable(job, path))
/* 285:    */         {
/* 286:388 */           long blockSize = file.getBlockSize();
/* 287:389 */           long splitSize = computeSplitSize(blockSize, minSize, maxSize);
/* 288:    */           
/* 289:391 */           long bytesRemaining = length;
/* 290:392 */           while (bytesRemaining / splitSize > 1.1D)
/* 291:    */           {
/* 292:393 */             int blkIndex = getBlockIndex(blkLocations, length - bytesRemaining);
/* 293:394 */             splits.add(makeSplit(path, length - bytesRemaining, splitSize, blkLocations[blkIndex].getHosts()));
/* 294:    */             
/* 295:396 */             bytesRemaining -= splitSize;
/* 296:    */           }
/* 297:399 */           if (bytesRemaining != 0L)
/* 298:    */           {
/* 299:400 */             int blkIndex = getBlockIndex(blkLocations, length - bytesRemaining);
/* 300:401 */             splits.add(makeSplit(path, length - bytesRemaining, bytesRemaining, blkLocations[blkIndex].getHosts()));
/* 301:    */           }
/* 302:    */         }
/* 303:    */         else
/* 304:    */         {
/* 305:405 */           splits.add(makeSplit(path, 0L, length, blkLocations[0].getHosts()));
/* 306:    */         }
/* 307:    */       }
/* 308:    */       else
/* 309:    */       {
/* 310:409 */         splits.add(makeSplit(path, 0L, length, new String[0]));
/* 311:    */       }
/* 312:    */     }
/* 313:413 */     job.getConfiguration().setLong("mapreduce.input.fileinputformat.numinputfiles", files.size());
/* 314:414 */     sw.stop();
/* 315:415 */     if (LOG.isDebugEnabled()) {
/* 316:416 */       LOG.debug("Total # of splits generated by getSplits: " + splits.size() + ", TimeTaken: " + sw.elapsedMillis());
/* 317:    */     }
/* 318:419 */     return splits;
/* 319:    */   }
/* 320:    */   
/* 321:    */   protected long computeSplitSize(long blockSize, long minSize, long maxSize)
/* 322:    */   {
/* 323:424 */     return Math.max(minSize, Math.min(maxSize, blockSize));
/* 324:    */   }
/* 325:    */   
/* 326:    */   protected int getBlockIndex(BlockLocation[] blkLocations, long offset)
/* 327:    */   {
/* 328:429 */     for (int i = 0; i < blkLocations.length; i++) {
/* 329:431 */       if ((blkLocations[i].getOffset() <= offset) && (offset < blkLocations[i].getOffset() + blkLocations[i].getLength())) {
/* 330:433 */         return i;
/* 331:    */       }
/* 332:    */     }
/* 333:436 */     BlockLocation last = blkLocations[(blkLocations.length - 1)];
/* 334:437 */     long fileLength = last.getOffset() + last.getLength() - 1L;
/* 335:438 */     throw new IllegalArgumentException("Offset " + offset + " is outside of file (0.." + fileLength + ")");
/* 336:    */   }
/* 337:    */   
/* 338:    */   public static void setInputPaths(Job job, String commaSeparatedPaths)
/* 339:    */     throws IOException
/* 340:    */   {
/* 341:454 */     setInputPaths(job, StringUtils.stringToPath(getPathStrings(commaSeparatedPaths)));
/* 342:    */   }
/* 343:    */   
/* 344:    */   public static void addInputPaths(Job job, String commaSeparatedPaths)
/* 345:    */     throws IOException
/* 346:    */   {
/* 347:469 */     for (String str : getPathStrings(commaSeparatedPaths)) {
/* 348:470 */       addInputPath(job, new Path(str));
/* 349:    */     }
/* 350:    */   }
/* 351:    */   
/* 352:    */   public static void setInputPaths(Job job, Path... inputPaths)
/* 353:    */     throws IOException
/* 354:    */   {
/* 355:484 */     Configuration conf = job.getConfiguration();
/* 356:485 */     Path path = inputPaths[0].getFileSystem(conf).makeQualified(inputPaths[0]);
/* 357:486 */     StringBuffer str = new StringBuffer(StringUtils.escapeString(path.toString()));
/* 358:487 */     for (int i = 1; i < inputPaths.length; i++)
/* 359:    */     {
/* 360:488 */       str.append(",");
/* 361:489 */       path = inputPaths[i].getFileSystem(conf).makeQualified(inputPaths[i]);
/* 362:490 */       str.append(StringUtils.escapeString(path.toString()));
/* 363:    */     }
/* 364:492 */     conf.set("mapreduce.input.fileinputformat.inputdir", str.toString());
/* 365:    */   }
/* 366:    */   
/* 367:    */   public static void addInputPath(Job job, Path path)
/* 368:    */     throws IOException
/* 369:    */   {
/* 370:504 */     Configuration conf = job.getConfiguration();
/* 371:505 */     path = path.getFileSystem(conf).makeQualified(path);
/* 372:506 */     String dirStr = StringUtils.escapeString(path.toString());
/* 373:507 */     String dirs = conf.get("mapreduce.input.fileinputformat.inputdir");
/* 374:508 */     conf.set("mapreduce.input.fileinputformat.inputdir", dirs + "," + dirStr);
/* 375:    */   }
/* 376:    */   
/* 377:    */   private static String[] getPathStrings(String commaSeparatedPaths)
/* 378:    */   {
/* 379:513 */     int length = commaSeparatedPaths.length();
/* 380:514 */     int curlyOpen = 0;
/* 381:515 */     int pathStart = 0;
/* 382:516 */     boolean globPattern = false;
/* 383:517 */     List<String> pathStrings = new ArrayList();
/* 384:519 */     for (int i = 0; i < length; i++)
/* 385:    */     {
/* 386:520 */       char ch = commaSeparatedPaths.charAt(i);
/* 387:521 */       switch (ch)
/* 388:    */       {
/* 389:    */       case '{': 
/* 390:523 */         curlyOpen++;
/* 391:524 */         if (!globPattern) {
/* 392:525 */           globPattern = true;
/* 393:    */         }
/* 394:    */         break;
/* 395:    */       case '}': 
/* 396:530 */         curlyOpen--;
/* 397:531 */         if ((curlyOpen == 0) && (globPattern)) {
/* 398:532 */           globPattern = false;
/* 399:    */         }
/* 400:    */         break;
/* 401:    */       case ',': 
/* 402:537 */         if (!globPattern)
/* 403:    */         {
/* 404:538 */           pathStrings.add(commaSeparatedPaths.substring(pathStart, i));
/* 405:539 */           pathStart = i + 1;
/* 406:    */         }
/* 407:    */         break;
/* 408:    */       }
/* 409:    */     }
/* 410:547 */     pathStrings.add(commaSeparatedPaths.substring(pathStart, length));
/* 411:    */     
/* 412:549 */     return (String[])pathStrings.toArray(new String[0]);
/* 413:    */   }
/* 414:    */   
/* 415:    */   public static Path[] getInputPaths(JobContext context)
/* 416:    */   {
/* 417:559 */     String dirs = context.getConfiguration().get("mapreduce.input.fileinputformat.inputdir", "");
/* 418:560 */     String[] list = StringUtils.split(dirs);
/* 419:561 */     Path[] result = new Path[list.length];
/* 420:562 */     for (int i = 0; i < list.length; i++) {
/* 421:563 */       result[i] = new Path(StringUtils.unEscapeString(list[i]));
/* 422:    */     }
/* 423:565 */     return result;
/* 424:    */   }
/* 425:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.lib.input.FileInputFormat
 * JD-Core Version:    0.7.0.1
 */