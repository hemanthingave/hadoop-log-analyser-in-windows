/*   1:    */ package org.apache.hadoop.mapreduce.v2.jobhistory;
/*   2:    */ 
/*   3:    */ import java.io.File;
/*   4:    */ import java.io.IOException;
/*   5:    */ import java.util.ArrayList;
/*   6:    */ import java.util.Calendar;
/*   7:    */ import java.util.Collection;
/*   8:    */ import java.util.LinkedList;
/*   9:    */ import java.util.List;
/*  10:    */ import java.util.concurrent.atomic.AtomicBoolean;
/*  11:    */ import java.util.regex.Matcher;
/*  12:    */ import java.util.regex.Pattern;
/*  13:    */ import org.apache.hadoop.classification.InterfaceAudience.Private;
/*  14:    */ import org.apache.hadoop.classification.InterfaceStability.Unstable;
/*  15:    */ import org.apache.hadoop.conf.Configuration;
/*  16:    */ import org.apache.hadoop.fs.FileContext;
/*  17:    */ import org.apache.hadoop.fs.FileStatus;
/*  18:    */ import org.apache.hadoop.fs.Path;
/*  19:    */ import org.apache.hadoop.fs.PathFilter;
/*  20:    */ import org.apache.hadoop.fs.RemoteIterator;
/*  21:    */ import org.apache.hadoop.fs.permission.FsPermission;
/*  22:    */ import org.apache.hadoop.mapreduce.TypeConverter;
/*  23:    */ import org.apache.hadoop.mapreduce.v2.api.records.JobId;
/*  24:    */ import org.apache.hadoop.mapreduce.v2.util.MRApps;
/*  25:    */ import org.apache.hadoop.security.UserGroupInformation;
/*  26:    */ import org.apache.hadoop.yarn.api.records.ApplicationAttemptId;
/*  27:    */ 
/*  28:    */ @InterfaceAudience.Private
/*  29:    */ @InterfaceStability.Unstable
/*  30:    */ public class JobHistoryUtils
/*  31:    */ {
/*  32: 55 */   public static final FsPermission HISTORY_STAGING_DIR_PERMISSIONS = FsPermission.createImmutable();
/*  33: 62 */   public static final FsPermission HISTORY_STAGING_USER_DIR_PERMISSIONS = FsPermission.createImmutable((short)448);
/*  34: 70 */   public static final FsPermission HISTORY_DONE_DIR_PERMISSION = FsPermission.createImmutable((short)504);
/*  35: 73 */   public static final FsPermission HISTORY_DONE_FILE_PERMISSION = FsPermission.createImmutable((short)504);
/*  36: 79 */   public static final FsPermission HISTORY_DONE_DIR_UMASK = FsPermission.createImmutable((short)7);
/*  37: 86 */   public static final FsPermission HISTORY_INTERMEDIATE_DONE_DIR_PERMISSIONS = FsPermission.createImmutable((short)1023);
/*  38: 92 */   public static final FsPermission HISTORY_INTERMEDIATE_USER_DIR_PERMISSIONS = FsPermission.createImmutable((short)504);
/*  39: 95 */   public static final FsPermission HISTORY_INTERMEDIATE_FILE_PERMISSIONS = FsPermission.createImmutable((short)504);
/*  40:    */   public static final String CONF_FILE_NAME_SUFFIX = "_conf.xml";
/*  41:    */   public static final String SUMMARY_FILE_NAME_SUFFIX = ".summary";
/*  42:    */   public static final String JOB_HISTORY_FILE_EXTENSION = ".jhist";
/*  43:    */   public static final int VERSION = 4;
/*  44:    */   public static final int SERIAL_NUMBER_DIRECTORY_DIGITS = 6;
/*  45:    */   public static final String TIMESTAMP_DIR_REGEX = "\\d{4}\\/\\d{2}\\/\\d{2}";
/*  46:118 */   public static final Pattern TIMESTAMP_DIR_PATTERN = Pattern.compile("\\d{4}\\/\\d{2}\\/\\d{2}");
/*  47:119 */   private static final String TIMESTAMP_DIR_FORMAT = "%04d" + File.separator + "%02d" + File.separator + "%02d";
/*  48:121 */   private static final PathFilter CONF_FILTER = new PathFilter()
/*  49:    */   {
/*  50:    */     public boolean accept(Path path)
/*  51:    */     {
/*  52:124 */       return path.getName().endsWith("_conf.xml");
/*  53:    */     }
/*  54:    */   };
/*  55:128 */   private static final PathFilter JOB_HISTORY_FILE_FILTER = new PathFilter()
/*  56:    */   {
/*  57:    */     public boolean accept(Path path)
/*  58:    */     {
/*  59:131 */       return path.getName().endsWith(".jhist");
/*  60:    */     }
/*  61:    */   };
/*  62:    */   
/*  63:    */   public static boolean isValidJobHistoryFileName(String pathString)
/*  64:    */   {
/*  65:141 */     return pathString.endsWith(".jhist");
/*  66:    */   }
/*  67:    */   
/*  68:    */   public static org.apache.hadoop.mapreduce.JobID getJobIDFromHistoryFilePath(String pathString)
/*  69:    */     throws IOException
/*  70:    */   {
/*  71:151 */     String[] parts = pathString.split("/");
/*  72:152 */     String fileNamePart = parts[(parts.length - 1)];
/*  73:153 */     JobIndexInfo jobIndexInfo = FileNameIndexUtils.getIndexInfo(fileNamePart);
/*  74:154 */     return TypeConverter.fromYarn(jobIndexInfo.getJobId());
/*  75:    */   }
/*  76:    */   
/*  77:    */   public static PathFilter getConfFileFilter()
/*  78:    */   {
/*  79:162 */     return CONF_FILTER;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public static PathFilter getHistoryFileFilter()
/*  83:    */   {
/*  84:170 */     return JOB_HISTORY_FILE_FILTER;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public static String getConfiguredHistoryStagingDirPrefix(Configuration conf, String jobId)
/*  88:    */     throws IOException
/*  89:    */   {
/*  90:182 */     String user = UserGroupInformation.getCurrentUser().getShortUserName();
/*  91:183 */     Path stagingPath = MRApps.getStagingAreaDir(conf, user);
/*  92:184 */     Path path = new Path(stagingPath, jobId);
/*  93:185 */     String logDir = path.toString();
/*  94:186 */     return logDir;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public static String getConfiguredHistoryIntermediateDoneDirPrefix(Configuration conf)
/*  98:    */   {
/*  99:196 */     String doneDirPrefix = conf.get("mapreduce.jobhistory.intermediate-done-dir");
/* 100:198 */     if (doneDirPrefix == null) {
/* 101:199 */       doneDirPrefix = conf.get("yarn.app.mapreduce.am.staging-dir", "/tmp/hadoop-yarn/staging") + "/history/done_intermediate";
/* 102:    */     }
/* 103:203 */     return doneDirPrefix;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public static String getConfiguredHistoryServerDoneDirPrefix(Configuration conf)
/* 107:    */   {
/* 108:213 */     String doneDirPrefix = conf.get("mapreduce.jobhistory.done-dir");
/* 109:214 */     if (doneDirPrefix == null) {
/* 110:215 */       doneDirPrefix = conf.get("yarn.app.mapreduce.am.staging-dir", "/tmp/hadoop-yarn/staging") + "/history/done";
/* 111:    */     }
/* 112:219 */     return doneDirPrefix;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public static String getHistoryIntermediateDoneDirForUser(Configuration conf)
/* 116:    */     throws IOException
/* 117:    */   {
/* 118:228 */     return getConfiguredHistoryIntermediateDoneDirPrefix(conf) + File.separator + UserGroupInformation.getCurrentUser().getShortUserName();
/* 119:    */   }
/* 120:    */   
/* 121:    */   public static boolean shouldCreateNonUserDirectory(Configuration conf)
/* 122:    */   {
/* 123:235 */     return conf.getBoolean("yarn.app.mapreduce.am.create-intermediate-jh-base-dir", true);
/* 124:    */   }
/* 125:    */   
/* 126:    */   public static Path getStagingJobHistoryFile(Path dir, JobId jobId, int attempt)
/* 127:    */   {
/* 128:242 */     return getStagingJobHistoryFile(dir, TypeConverter.fromYarn(jobId).toString(), attempt);
/* 129:    */   }
/* 130:    */   
/* 131:    */   public static Path getStagingJobHistoryFile(Path dir, String jobId, int attempt)
/* 132:    */   {
/* 133:249 */     return new Path(dir, jobId + "_" + attempt + ".jhist");
/* 134:    */   }
/* 135:    */   
/* 136:    */   public static String getIntermediateConfFileName(JobId jobId)
/* 137:    */   {
/* 138:259 */     return TypeConverter.fromYarn(jobId).toString() + "_conf.xml";
/* 139:    */   }
/* 140:    */   
/* 141:    */   public static String getIntermediateSummaryFileName(JobId jobId)
/* 142:    */   {
/* 143:268 */     return TypeConverter.fromYarn(jobId).toString() + ".summary";
/* 144:    */   }
/* 145:    */   
/* 146:    */   public static Path getStagingConfFile(Path logDir, JobId jobId, int attempt)
/* 147:    */   {
/* 148:280 */     Path jobFilePath = null;
/* 149:281 */     if (logDir != null) {
/* 150:282 */       jobFilePath = new Path(logDir, TypeConverter.fromYarn(jobId).toString() + "_" + attempt + "_conf.xml");
/* 151:    */     }
/* 152:285 */     return jobFilePath;
/* 153:    */   }
/* 154:    */   
/* 155:    */   public static String serialNumberDirectoryComponent(JobId id, String serialNumberFormat)
/* 156:    */   {
/* 157:295 */     return String.format(serialNumberFormat, new Object[] { Integer.valueOf(jobSerialNumber(id)) }).substring(0, 6);
/* 158:    */   }
/* 159:    */   
/* 160:    */   public static String getTimestampPartFromPath(String path)
/* 161:    */   {
/* 162:305 */     Matcher matcher = TIMESTAMP_DIR_PATTERN.matcher(path);
/* 163:306 */     if (matcher.find())
/* 164:    */     {
/* 165:307 */       String matched = matcher.group();
/* 166:308 */       String ret = matched.intern();
/* 167:309 */       return ret;
/* 168:    */     }
/* 169:311 */     return null;
/* 170:    */   }
/* 171:    */   
/* 172:    */   public static String historyLogSubdirectory(JobId id, String timestampComponent, String serialNumberFormat)
/* 173:    */   {
/* 174:324 */     String result = "";
/* 175:325 */     String serialNumberDirectory = serialNumberDirectoryComponent(id, serialNumberFormat);
/* 176:    */     
/* 177:327 */     result = result + timestampComponent + File.separator + serialNumberDirectory + File.separator;
/* 178:    */     
/* 179:    */ 
/* 180:    */ 
/* 181:    */ 
/* 182:332 */     return result;
/* 183:    */   }
/* 184:    */   
/* 185:    */   public static String timestampDirectoryComponent(long millisecondTime)
/* 186:    */   {
/* 187:341 */     Calendar timestamp = Calendar.getInstance();
/* 188:342 */     timestamp.setTimeInMillis(millisecondTime);
/* 189:343 */     String dateString = null;
/* 190:344 */     dateString = String.format(TIMESTAMP_DIR_FORMAT, new Object[] { Integer.valueOf(timestamp.get(1)), Integer.valueOf(timestamp.get(2) + 1), Integer.valueOf(timestamp.get(5)) });
/* 191:    */     
/* 192:    */ 
/* 193:    */ 
/* 194:    */ 
/* 195:    */ 
/* 196:    */ 
/* 197:351 */     dateString = dateString.intern();
/* 198:352 */     return dateString;
/* 199:    */   }
/* 200:    */   
/* 201:    */   public static String doneSubdirsBeforeSerialTail()
/* 202:    */   {
/* 203:357 */     String result = "/*/*/*";
/* 204:358 */     return result;
/* 205:    */   }
/* 206:    */   
/* 207:    */   public static int jobSerialNumber(JobId id)
/* 208:    */   {
/* 209:367 */     return id.getId();
/* 210:    */   }
/* 211:    */   
/* 212:    */   public static List<FileStatus> localGlobber(FileContext fc, Path root, String tail)
/* 213:    */     throws IOException
/* 214:    */   {
/* 215:372 */     return localGlobber(fc, root, tail, null);
/* 216:    */   }
/* 217:    */   
/* 218:    */   public static List<FileStatus> localGlobber(FileContext fc, Path root, String tail, PathFilter filter)
/* 219:    */     throws IOException
/* 220:    */   {
/* 221:377 */     return localGlobber(fc, root, tail, filter, null);
/* 222:    */   }
/* 223:    */   
/* 224:    */   public static List<FileStatus> localGlobber(FileContext fc, Path root, String tail, PathFilter filter, AtomicBoolean hasFlatFiles)
/* 225:    */     throws IOException
/* 226:    */   {
/* 227:384 */     if (tail.equals("")) {
/* 228:385 */       return listFilteredStatus(fc, root, filter);
/* 229:    */     }
/* 230:388 */     if (tail.startsWith("/*"))
/* 231:    */     {
/* 232:389 */       Path[] subdirs = filteredStat2Paths(remoteIterToList(fc.listStatus(root)), true, hasFlatFiles);
/* 233:    */       
/* 234:    */ 
/* 235:392 */       List<List<FileStatus>> subsubdirs = new LinkedList();
/* 236:    */       
/* 237:394 */       int subsubdirCount = 0;
/* 238:396 */       if (subdirs.length == 0) {
/* 239:397 */         return new LinkedList();
/* 240:    */       }
/* 241:400 */       String newTail = tail.substring(2);
/* 242:402 */       for (int i = 0; i < subdirs.length; i++)
/* 243:    */       {
/* 244:403 */         subsubdirs.add(localGlobber(fc, subdirs[i], newTail, filter, null));
/* 245:    */         
/* 246:    */ 
/* 247:406 */         subsubdirCount += ((List)subsubdirs.get(i)).size();
/* 248:    */       }
/* 249:409 */       List<FileStatus> result = new LinkedList();
/* 250:411 */       for (int i = 0; i < subsubdirs.size(); i++) {
/* 251:412 */         result.addAll((Collection)subsubdirs.get(i));
/* 252:    */       }
/* 253:415 */       return result;
/* 254:    */     }
/* 255:418 */     if (tail.startsWith("/"))
/* 256:    */     {
/* 257:419 */       int split = tail.indexOf('/', 1);
/* 258:421 */       if (split < 0) {
/* 259:422 */         return listFilteredStatus(fc, new Path(root, tail.substring(1)), filter);
/* 260:    */       }
/* 261:424 */       String thisSegment = tail.substring(1, split);
/* 262:425 */       String newTail = tail.substring(split);
/* 263:426 */       return localGlobber(fc, new Path(root, thisSegment), newTail, filter, hasFlatFiles);
/* 264:    */     }
/* 265:431 */     IOException e = new IOException("localGlobber: bad tail");
/* 266:    */     
/* 267:433 */     throw e;
/* 268:    */   }
/* 269:    */   
/* 270:    */   private static List<FileStatus> listFilteredStatus(FileContext fc, Path root, PathFilter filter)
/* 271:    */     throws IOException
/* 272:    */   {
/* 273:438 */     List<FileStatus> fsList = remoteIterToList(fc.listStatus(root));
/* 274:439 */     if (filter == null) {
/* 275:440 */       return fsList;
/* 276:    */     }
/* 277:442 */     List<FileStatus> filteredList = new LinkedList();
/* 278:443 */     for (FileStatus fs : fsList) {
/* 279:444 */       if (filter.accept(fs.getPath())) {
/* 280:445 */         filteredList.add(fs);
/* 281:    */       }
/* 282:    */     }
/* 283:448 */     return filteredList;
/* 284:    */   }
/* 285:    */   
/* 286:    */   private static List<FileStatus> remoteIterToList(RemoteIterator<FileStatus> rIter)
/* 287:    */     throws IOException
/* 288:    */   {
/* 289:454 */     List<FileStatus> fsList = new LinkedList();
/* 290:455 */     if (rIter == null) {
/* 291:456 */       return fsList;
/* 292:    */     }
/* 293:457 */     while (rIter.hasNext()) {
/* 294:458 */       fsList.add(rIter.next());
/* 295:    */     }
/* 296:460 */     return fsList;
/* 297:    */   }
/* 298:    */   
/* 299:    */   private static Path[] filteredStat2Paths(List<FileStatus> stats, boolean dirs, AtomicBoolean hasMismatches)
/* 300:    */   {
/* 301:467 */     int resultCount = 0;
/* 302:469 */     if (hasMismatches == null) {
/* 303:470 */       hasMismatches = new AtomicBoolean(false);
/* 304:    */     }
/* 305:473 */     for (int i = 0; i < stats.size(); i++) {
/* 306:474 */       if (((FileStatus)stats.get(i)).isDirectory() == dirs) {
/* 307:475 */         stats.set(resultCount++, stats.get(i));
/* 308:    */       } else {
/* 309:477 */         hasMismatches.set(true);
/* 310:    */       }
/* 311:    */     }
/* 312:481 */     Path[] result = new Path[resultCount];
/* 313:482 */     for (int i = 0; i < resultCount; i++) {
/* 314:483 */       result[i] = ((FileStatus)stats.get(i)).getPath();
/* 315:    */     }
/* 316:486 */     return result;
/* 317:    */   }
/* 318:    */   
/* 319:    */   public static Path getPreviousJobHistoryPath(Configuration conf, ApplicationAttemptId applicationAttemptId)
/* 320:    */     throws IOException
/* 321:    */   {
/* 322:492 */     String jobId = TypeConverter.fromYarn(applicationAttemptId.getApplicationId()).toString();
/* 323:    */     
/* 324:    */ 
/* 325:495 */     String jobhistoryDir = getConfiguredHistoryStagingDirPrefix(conf, jobId);
/* 326:    */     
/* 327:497 */     Path histDirPath = FileContext.getFileContext(conf).makeQualified(new Path(jobhistoryDir));
/* 328:    */     
/* 329:499 */     FileContext fc = FileContext.getFileContext(histDirPath.toUri(), conf);
/* 330:500 */     return fc.makeQualified(getStagingJobHistoryFile(histDirPath, jobId, applicationAttemptId.getAttemptId() - 1));
/* 331:    */   }
/* 332:    */   
/* 333:    */   public static List<FileStatus> getHistoryDirsForCleaning(FileContext fc, Path root, long cutoff)
/* 334:    */     throws IOException
/* 335:    */   {
/* 336:518 */     List<FileStatus> fsList = new ArrayList();
/* 337:519 */     Calendar cCal = Calendar.getInstance();
/* 338:520 */     cCal.setTimeInMillis(cutoff);
/* 339:521 */     int cYear = cCal.get(1);
/* 340:522 */     int cMonth = cCal.get(2) + 1;
/* 341:523 */     int cDate = cCal.get(5);
/* 342:    */     
/* 343:525 */     RemoteIterator<FileStatus> yearDirIt = fc.listStatus(root);
/* 344:526 */     while (yearDirIt.hasNext())
/* 345:    */     {
/* 346:527 */       FileStatus yearDir = (FileStatus)yearDirIt.next();
/* 347:    */       try
/* 348:    */       {
/* 349:529 */         int year = Integer.parseInt(yearDir.getPath().getName());
/* 350:530 */         if (year <= cYear)
/* 351:    */         {
/* 352:531 */           RemoteIterator<FileStatus> monthDirIt = fc.listStatus(yearDir.getPath());
/* 353:533 */           while (monthDirIt.hasNext())
/* 354:    */           {
/* 355:534 */             FileStatus monthDir = (FileStatus)monthDirIt.next();
/* 356:    */             try
/* 357:    */             {
/* 358:536 */               int month = Integer.parseInt(monthDir.getPath().getName());
/* 359:539 */               if ((year < cYear) || (month <= cMonth))
/* 360:    */               {
/* 361:540 */                 RemoteIterator<FileStatus> dateDirIt = fc.listStatus(monthDir.getPath());
/* 362:542 */                 while (dateDirIt.hasNext())
/* 363:    */                 {
/* 364:543 */                   FileStatus dateDir = (FileStatus)dateDirIt.next();
/* 365:    */                   try
/* 366:    */                   {
/* 367:545 */                     int date = Integer.parseInt(dateDir.getPath().getName());
/* 368:549 */                     if ((year < cYear) || (month < cMonth) || (date <= cDate)) {
/* 369:550 */                       fsList.addAll(remoteIterToList(fc.listStatus(dateDir.getPath())));
/* 370:    */                     }
/* 371:    */                   }
/* 372:    */                   catch (NumberFormatException nfe) {}
/* 373:    */                 }
/* 374:    */               }
/* 375:    */             }
/* 376:    */             catch (NumberFormatException nfe) {}
/* 377:    */           }
/* 378:    */         }
/* 379:    */       }
/* 380:    */       catch (NumberFormatException nfe) {}
/* 381:    */     }
/* 382:569 */     return fsList;
/* 383:    */   }
/* 384:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-common-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.v2.jobhistory.JobHistoryUtils
 * JD-Core Version:    0.7.0.1
 */