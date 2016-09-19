/*   1:    */ package org.apache.hadoop.mapred;
/*   2:    */ 
/*   3:    */ import com.google.common.base.Charsets;
/*   4:    */ import java.io.BufferedOutputStream;
/*   5:    */ import java.io.BufferedReader;
/*   6:    */ import java.io.Closeable;
/*   7:    */ import java.io.DataOutputStream;
/*   8:    */ import java.io.File;
/*   9:    */ import java.io.FileInputStream;
/*  10:    */ import java.io.Flushable;
/*  11:    */ import java.io.IOException;
/*  12:    */ import java.io.InputStream;
/*  13:    */ import java.io.InputStreamReader;
/*  14:    */ import java.io.PrintStream;
/*  15:    */ import java.util.ArrayList;
/*  16:    */ import java.util.Enumeration;
/*  17:    */ import java.util.List;
/*  18:    */ import java.util.concurrent.Executors;
/*  19:    */ import java.util.concurrent.ScheduledExecutorService;
/*  20:    */ import java.util.concurrent.ThreadFactory;
/*  21:    */ import java.util.concurrent.TimeUnit;
/*  22:    */ import org.apache.commons.logging.Log;
/*  23:    */ import org.apache.commons.logging.LogFactory;
/*  24:    */ import org.apache.hadoop.classification.InterfaceAudience.Private;
/*  25:    */ import org.apache.hadoop.conf.Configuration;
/*  26:    */ import org.apache.hadoop.fs.FileStatus;
/*  27:    */ import org.apache.hadoop.fs.FileSystem;
/*  28:    */ import org.apache.hadoop.fs.FileUtil;
/*  29:    */ import org.apache.hadoop.fs.LocalFileSystem;
/*  30:    */ import org.apache.hadoop.fs.Path;
/*  31:    */ import org.apache.hadoop.io.IOUtils;
/*  32:    */ import org.apache.hadoop.io.SecureIOUtils;
/*  33:    */ import org.apache.hadoop.mapreduce.JobID;
/*  34:    */ import org.apache.hadoop.mapreduce.util.ProcessTree;
/*  35:    */ import org.apache.hadoop.util.Shell;
/*  36:    */ import org.apache.hadoop.util.ShutdownHookManager;
/*  37:    */ import org.apache.hadoop.util.StringUtils;
/*  38:    */ import org.apache.log4j.Appender;
/*  39:    */ import org.apache.log4j.LogManager;
/*  40:    */ import org.apache.log4j.Logger;
/*  41:    */ import org.apache.log4j.spi.LoggerRepository;
/*  42:    */ 
/*  43:    */ @InterfaceAudience.Private
/*  44:    */ public class TaskLog
/*  45:    */ {
/*  46: 68 */   private static final Log LOG = LogFactory.getLog(TaskLog.class);
/*  47:    */   static final String USERLOGS_DIR_NAME = "userlogs";
/*  48: 73 */   private static final File LOG_DIR = new File(getBaseLogDir(), "userlogs").getAbsoluteFile();
/*  49: 77 */   static LocalFileSystem localFS = null;
/*  50:    */   private static long prevOutLength;
/*  51:    */   private static long prevErrLength;
/*  52:    */   private static long prevLogLength;
/*  53:    */   
/*  54:    */   public static String getMRv2LogDir()
/*  55:    */   {
/*  56: 80 */     return System.getProperty("yarn.app.container.log.dir");
/*  57:    */   }
/*  58:    */   
/*  59:    */   public static File getTaskLogFile(TaskAttemptID taskid, boolean isCleanup, LogName filter)
/*  60:    */   {
/*  61: 85 */     if (getMRv2LogDir() != null) {
/*  62: 86 */       return new File(getMRv2LogDir(), filter.toString());
/*  63:    */     }
/*  64: 88 */     return new File(getAttemptDir(taskid, isCleanup), filter.toString());
/*  65:    */   }
/*  66:    */   
/*  67:    */   static File getRealTaskLogFileLocation(TaskAttemptID taskid, boolean isCleanup, LogName filter)
/*  68:    */   {
/*  69:    */     LogFileDetail l;
/*  70:    */     try
/*  71:    */     {
/*  72: 96 */       l = getLogFileDetail(taskid, filter, isCleanup);
/*  73:    */     }
/*  74:    */     catch (IOException ie)
/*  75:    */     {
/*  76: 98 */       LOG.error("getTaskLogFileDetail threw an exception " + ie);
/*  77: 99 */       return null;
/*  78:    */     }
/*  79:101 */     return new File(l.location, filter.toString());
/*  80:    */   }
/*  81:    */   
/*  82:    */   private static LogFileDetail getLogFileDetail(TaskAttemptID taskid, LogName filter, boolean isCleanup)
/*  83:    */     throws IOException
/*  84:    */   {
/*  85:114 */     File indexFile = getIndexFile(taskid, isCleanup);
/*  86:115 */     BufferedReader fis = new BufferedReader(new InputStreamReader(SecureIOUtils.openForRead(indexFile, obtainLogDirOwner(taskid), null), Charsets.UTF_8));
/*  87:    */     
/*  88:    */ 
/*  89:    */ 
/*  90:    */ 
/*  91:    */ 
/*  92:    */ 
/*  93:    */ 
/*  94:123 */     LogFileDetail l = new LogFileDetail(null);
/*  95:124 */     String str = null;
/*  96:    */     try
/*  97:    */     {
/*  98:126 */       str = fis.readLine();
/*  99:127 */       if (str == null) {
/* 100:128 */         throw new IOException("Index file for the log of " + taskid + " doesn't exist.");
/* 101:    */       }
/* 102:131 */       l.location = str.substring(str.indexOf("LOG_DIR:") + "LOG_DIR:".length());
/* 103:137 */       if ((filter.equals(LogName.DEBUGOUT)) || (filter.equals(LogName.PROFILE)))
/* 104:    */       {
/* 105:138 */         l.length = new File(l.location, filter.toString()).length();
/* 106:139 */         l.start = 0L;
/* 107:140 */         fis.close();
/* 108:141 */         return l;
/* 109:    */       }
/* 110:143 */       str = fis.readLine();
/* 111:144 */       while (str != null)
/* 112:    */       {
/* 113:146 */         if (str.contains(filter.toString()))
/* 114:    */         {
/* 115:147 */           str = str.substring(filter.toString().length() + 1);
/* 116:148 */           String[] startAndLen = str.split(" ");
/* 117:149 */           l.start = Long.parseLong(startAndLen[0]);
/* 118:150 */           l.length = Long.parseLong(startAndLen[1]);
/* 119:151 */           break;
/* 120:    */         }
/* 121:153 */         str = fis.readLine();
/* 122:    */       }
/* 123:155 */       fis.close();
/* 124:156 */       fis = null;
/* 125:    */     }
/* 126:    */     finally
/* 127:    */     {
/* 128:158 */       IOUtils.cleanup(LOG, new Closeable[] { fis });
/* 129:    */     }
/* 130:160 */     return l;
/* 131:    */   }
/* 132:    */   
/* 133:    */   private static File getTmpIndexFile(TaskAttemptID taskid, boolean isCleanup)
/* 134:    */   {
/* 135:164 */     return new File(getAttemptDir(taskid, isCleanup), "log.tmp");
/* 136:    */   }
/* 137:    */   
/* 138:    */   static File getIndexFile(TaskAttemptID taskid, boolean isCleanup)
/* 139:    */   {
/* 140:168 */     return new File(getAttemptDir(taskid, isCleanup), "log.index");
/* 141:    */   }
/* 142:    */   
/* 143:    */   static String obtainLogDirOwner(TaskAttemptID taskid)
/* 144:    */     throws IOException
/* 145:    */   {
/* 146:176 */     Configuration conf = new Configuration();
/* 147:177 */     FileSystem raw = FileSystem.getLocal(conf).getRaw();
/* 148:178 */     Path jobLogDir = new Path(getJobDir(taskid.getJobID()).getAbsolutePath());
/* 149:179 */     FileStatus jobStat = raw.getFileStatus(jobLogDir);
/* 150:180 */     return jobStat.getOwner();
/* 151:    */   }
/* 152:    */   
/* 153:    */   static String getBaseLogDir()
/* 154:    */   {
/* 155:184 */     return System.getProperty("hadoop.log.dir");
/* 156:    */   }
/* 157:    */   
/* 158:    */   static File getAttemptDir(TaskAttemptID taskid, boolean isCleanup)
/* 159:    */   {
/* 160:188 */     String cleanupSuffix = isCleanup ? ".cleanup" : "";
/* 161:189 */     return new File(getJobDir(taskid.getJobID()), taskid + cleanupSuffix);
/* 162:    */   }
/* 163:    */   
/* 164:    */   private static synchronized void writeToIndexFile(String logLocation, boolean isCleanup)
/* 165:    */     throws IOException
/* 166:    */   {
/* 167:200 */     File tmpIndexFile = getTmpIndexFile(currentTaskid, isCleanup);
/* 168:    */     
/* 169:202 */     BufferedOutputStream bos = new BufferedOutputStream(SecureIOUtils.createForWrite(tmpIndexFile, 420));
/* 170:    */     
/* 171:    */ 
/* 172:205 */     DataOutputStream dos = new DataOutputStream(bos);
/* 173:    */     try
/* 174:    */     {
/* 175:212 */       dos.writeBytes("LOG_DIR:" + logLocation + "\n" + LogName.STDOUT.toString() + ":");
/* 176:    */       
/* 177:214 */       dos.writeBytes(Long.toString(prevOutLength) + " ");
/* 178:215 */       dos.writeBytes(Long.toString(new File(logLocation, LogName.STDOUT.toString()).length() - prevOutLength) + "\n" + LogName.STDERR + ":");
/* 179:    */       
/* 180:    */ 
/* 181:218 */       dos.writeBytes(Long.toString(prevErrLength) + " ");
/* 182:219 */       dos.writeBytes(Long.toString(new File(logLocation, LogName.STDERR.toString()).length() - prevErrLength) + "\n" + LogName.SYSLOG.toString() + ":");
/* 183:    */       
/* 184:    */ 
/* 185:222 */       dos.writeBytes(Long.toString(prevLogLength) + " ");
/* 186:223 */       dos.writeBytes(Long.toString(new File(logLocation, LogName.SYSLOG.toString()).length() - prevLogLength) + "\n");
/* 187:    */       
/* 188:    */ 
/* 189:226 */       dos.close();
/* 190:227 */       dos = null;
/* 191:    */     }
/* 192:    */     finally
/* 193:    */     {
/* 194:229 */       IOUtils.cleanup(LOG, new Closeable[] { dos });
/* 195:    */     }
/* 196:232 */     File indexFile = getIndexFile(currentTaskid, isCleanup);
/* 197:233 */     Path indexFilePath = new Path(indexFile.getAbsolutePath());
/* 198:234 */     Path tmpIndexFilePath = new Path(tmpIndexFile.getAbsolutePath());
/* 199:236 */     if (localFS == null) {
/* 200:237 */       localFS = FileSystem.getLocal(new Configuration());
/* 201:    */     }
/* 202:239 */     localFS.rename(tmpIndexFilePath, indexFilePath);
/* 203:    */   }
/* 204:    */   
/* 205:    */   private static void resetPrevLengths(String logLocation)
/* 206:    */   {
/* 207:242 */     prevOutLength = new File(logLocation, LogName.STDOUT.toString()).length();
/* 208:243 */     prevErrLength = new File(logLocation, LogName.STDERR.toString()).length();
/* 209:244 */     prevLogLength = new File(logLocation, LogName.SYSLOG.toString()).length();
/* 210:    */   }
/* 211:    */   
/* 212:246 */   private static volatile TaskAttemptID currentTaskid = null;
/* 213:    */   private static final String bashCommand = "bash";
/* 214:    */   private static final String tailCommand = "tail";
/* 215:    */   
/* 216:    */   public static synchronized void syncLogs(String logLocation, TaskAttemptID taskid, boolean isCleanup)
/* 217:    */     throws IOException
/* 218:    */   {
/* 219:253 */     System.out.flush();
/* 220:254 */     System.err.flush();
/* 221:255 */     Enumeration<Logger> allLoggers = LogManager.getCurrentLoggers();
/* 222:256 */     while (allLoggers.hasMoreElements())
/* 223:    */     {
/* 224:257 */       Logger l = (Logger)allLoggers.nextElement();
/* 225:258 */       Enumeration<Appender> allAppenders = l.getAllAppenders();
/* 226:259 */       while (allAppenders.hasMoreElements())
/* 227:    */       {
/* 228:260 */         Appender a = (Appender)allAppenders.nextElement();
/* 229:261 */         if ((a instanceof TaskLogAppender)) {
/* 230:262 */           ((TaskLogAppender)a).flush();
/* 231:    */         }
/* 232:    */       }
/* 233:    */     }
/* 234:266 */     if (currentTaskid != taskid)
/* 235:    */     {
/* 236:267 */       currentTaskid = taskid;
/* 237:268 */       resetPrevLengths(logLocation);
/* 238:    */     }
/* 239:270 */     writeToIndexFile(logLocation, isCleanup);
/* 240:    */   }
/* 241:    */   
/* 242:    */   public static synchronized void syncLogsShutdown(ScheduledExecutorService scheduler)
/* 243:    */   {
/* 244:278 */     System.out.flush();
/* 245:279 */     System.err.flush();
/* 246:281 */     if (scheduler != null) {
/* 247:282 */       scheduler.shutdownNow();
/* 248:    */     }
/* 249:286 */     LogManager.shutdown();
/* 250:    */   }
/* 251:    */   
/* 252:    */   public static synchronized void syncLogs()
/* 253:    */   {
/* 254:293 */     System.out.flush();
/* 255:294 */     System.err.flush();
/* 256:    */     
/* 257:    */ 
/* 258:    */ 
/* 259:298 */     Logger rootLogger = Logger.getRootLogger();
/* 260:299 */     flushAppenders(rootLogger);
/* 261:300 */     Enumeration<Logger> allLoggers = rootLogger.getLoggerRepository().getCurrentLoggers();
/* 262:302 */     while (allLoggers.hasMoreElements())
/* 263:    */     {
/* 264:303 */       Logger l = (Logger)allLoggers.nextElement();
/* 265:304 */       flushAppenders(l);
/* 266:    */     }
/* 267:    */   }
/* 268:    */   
/* 269:    */   private static void flushAppenders(Logger l)
/* 270:    */   {
/* 271:310 */     Enumeration<Appender> allAppenders = l.getAllAppenders();
/* 272:311 */     while (allAppenders.hasMoreElements())
/* 273:    */     {
/* 274:312 */       Appender a = (Appender)allAppenders.nextElement();
/* 275:313 */       if ((a instanceof Flushable)) {
/* 276:    */         try
/* 277:    */         {
/* 278:315 */           ((Flushable)a).flush();
/* 279:    */         }
/* 280:    */         catch (IOException ioe)
/* 281:    */         {
/* 282:317 */           System.err.println(a + ": Failed to flush!" + StringUtils.stringifyException(ioe));
/* 283:    */         }
/* 284:    */       }
/* 285:    */     }
/* 286:    */   }
/* 287:    */   
/* 288:    */   public static ScheduledExecutorService createLogSyncer()
/* 289:    */   {
/* 290:325 */     ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor(new ThreadFactory()
/* 291:    */     {
/* 292:    */       public Thread newThread(Runnable r)
/* 293:    */       {
/* 294:330 */         Thread t = Executors.defaultThreadFactory().newThread(r);
/* 295:331 */         t.setDaemon(true);
/* 296:332 */         t.setName("Thread for syncLogs");
/* 297:333 */         return t;
/* 298:    */       }
/* 299:335 */     });
/* 300:336 */     ShutdownHookManager.get().addShutdownHook(new Runnable()
/* 301:    */     {
/* 302:    */       public void run()
/* 303:    */       {
/* 304:339 */         TaskLog.syncLogsShutdown(this.val$scheduler);
/* 305:    */       }
/* 306:339 */     }, 50);
/* 307:    */     
/* 308:    */ 
/* 309:342 */     scheduler.scheduleWithFixedDelay(new Runnable()
/* 310:    */     {
/* 311:    */       public void run() {}
/* 312:342 */     }, 0L, 5L, TimeUnit.SECONDS);
/* 313:    */     
/* 314:    */ 
/* 315:    */ 
/* 316:    */ 
/* 317:    */ 
/* 318:    */ 
/* 319:349 */     return scheduler;
/* 320:    */   }
/* 321:    */   
/* 322:    */   private static class LogFileDetail
/* 323:    */   {
/* 324:    */     static final String LOCATION = "LOG_DIR:";
/* 325:    */     String location;
/* 326:    */     long start;
/* 327:    */     long length;
/* 328:    */   }
/* 329:    */   
/* 330:    */   @InterfaceAudience.Private
/* 331:    */   public static enum LogName
/* 332:    */   {
/* 333:358 */     STDOUT("stdout"),  STDERR("stderr"),  SYSLOG("syslog"),  PROFILE("profile.out"),  DEBUGOUT("debugout");
/* 334:    */     
/* 335:    */     private String prefix;
/* 336:    */     
/* 337:    */     private LogName(String prefix)
/* 338:    */     {
/* 339:375 */       this.prefix = prefix;
/* 340:    */     }
/* 341:    */     
/* 342:    */     public String toString()
/* 343:    */     {
/* 344:380 */       return this.prefix;
/* 345:    */     }
/* 346:    */   }
/* 347:    */   
/* 348:    */   public static class Reader
/* 349:    */     extends InputStream
/* 350:    */   {
/* 351:    */     private long bytesRemaining;
/* 352:    */     private FileInputStream file;
/* 353:    */     
/* 354:    */     public Reader(TaskAttemptID taskid, TaskLog.LogName kind, long start, long end, boolean isCleanup)
/* 355:    */       throws IOException
/* 356:    */     {
/* 357:403 */       TaskLog.LogFileDetail fileDetail = TaskLog.getLogFileDetail(taskid, kind, isCleanup);
/* 358:    */       
/* 359:405 */       long size = fileDetail.length;
/* 360:406 */       if (start < 0L) {
/* 361:407 */         start += size + 1L;
/* 362:    */       }
/* 363:409 */       if (end < 0L) {
/* 364:410 */         end += size + 1L;
/* 365:    */       }
/* 366:412 */       start = Math.max(0L, Math.min(start, size));
/* 367:413 */       end = Math.max(0L, Math.min(end, size));
/* 368:414 */       start += fileDetail.start;
/* 369:415 */       end += fileDetail.start;
/* 370:416 */       this.bytesRemaining = (end - start);
/* 371:417 */       String owner = TaskLog.obtainLogDirOwner(taskid);
/* 372:418 */       this.file = SecureIOUtils.openForRead(new File(fileDetail.location, kind.toString()), owner, null);
/* 373:    */       
/* 374:    */ 
/* 375:421 */       long pos = 0L;
/* 376:422 */       while (pos < start)
/* 377:    */       {
/* 378:423 */         long result = this.file.skip(start - pos);
/* 379:424 */         if (result < 0L)
/* 380:    */         {
/* 381:425 */           this.bytesRemaining = 0L;
/* 382:426 */           break;
/* 383:    */         }
/* 384:428 */         pos += result;
/* 385:    */       }
/* 386:    */     }
/* 387:    */     
/* 388:    */     public int read()
/* 389:    */       throws IOException
/* 390:    */     {
/* 391:434 */       int result = -1;
/* 392:435 */       if (this.bytesRemaining > 0L)
/* 393:    */       {
/* 394:436 */         this.bytesRemaining -= 1L;
/* 395:437 */         result = this.file.read();
/* 396:    */       }
/* 397:439 */       return result;
/* 398:    */     }
/* 399:    */     
/* 400:    */     public int read(byte[] buffer, int offset, int length)
/* 401:    */       throws IOException
/* 402:    */     {
/* 403:444 */       length = (int)Math.min(length, this.bytesRemaining);
/* 404:445 */       int bytes = this.file.read(buffer, offset, length);
/* 405:446 */       if (bytes > 0) {
/* 406:447 */         this.bytesRemaining -= bytes;
/* 407:    */       }
/* 408:449 */       return bytes;
/* 409:    */     }
/* 410:    */     
/* 411:    */     public int available()
/* 412:    */       throws IOException
/* 413:    */     {
/* 414:454 */       return (int)Math.min(this.bytesRemaining, this.file.available());
/* 415:    */     }
/* 416:    */     
/* 417:    */     public void close()
/* 418:    */       throws IOException
/* 419:    */     {
/* 420:459 */       this.file.close();
/* 421:    */     }
/* 422:    */   }
/* 423:    */   
/* 424:    */   public static long getTaskLogLength(JobConf conf)
/* 425:    */   {
/* 426:472 */     return conf.getLong("mapreduce.task.userlog.limit.kb", 0L) * 1024L;
/* 427:    */   }
/* 428:    */   
/* 429:    */   public static List<String> captureOutAndError(List<String> setup, List<String> cmd, File stdoutFilename, File stderrFilename, long tailLength, boolean useSetsid)
/* 430:    */     throws IOException
/* 431:    */   {
/* 432:496 */     List<String> result = new ArrayList(3);
/* 433:497 */     result.add("bash");
/* 434:498 */     result.add("-c");
/* 435:499 */     String mergedCmd = buildCommandLine(setup, cmd, stdoutFilename, stderrFilename, tailLength, useSetsid);
/* 436:    */     
/* 437:    */ 
/* 438:502 */     result.add(mergedCmd);
/* 439:503 */     return result;
/* 440:    */   }
/* 441:    */   
/* 442:    */   static String buildCommandLine(List<String> setup, List<String> cmd, File stdoutFilename, File stderrFilename, long tailLength, boolean useSetsid)
/* 443:    */     throws IOException
/* 444:    */   {
/* 445:523 */     String stdout = FileUtil.makeShellPath(stdoutFilename);
/* 446:524 */     String stderr = FileUtil.makeShellPath(stderrFilename);
/* 447:525 */     StringBuffer mergedCmd = new StringBuffer();
/* 448:529 */     if (!Shell.WINDOWS) {
/* 449:530 */       mergedCmd.append(" export JVM_PID=`echo $$` ; ");
/* 450:    */     }
/* 451:533 */     if ((setup != null) && (setup.size() > 0))
/* 452:    */     {
/* 453:534 */       mergedCmd.append(addCommand(setup, false));
/* 454:535 */       mergedCmd.append(";");
/* 455:    */     }
/* 456:537 */     if (tailLength > 0L) {
/* 457:538 */       mergedCmd.append("(");
/* 458:539 */     } else if ((ProcessTree.isSetsidAvailable) && (useSetsid) && (!Shell.WINDOWS)) {
/* 459:541 */       mergedCmd.append("exec setsid ");
/* 460:    */     } else {
/* 461:543 */       mergedCmd.append("exec ");
/* 462:    */     }
/* 463:545 */     mergedCmd.append(addCommand(cmd, true));
/* 464:546 */     mergedCmd.append(" < /dev/null ");
/* 465:547 */     if (tailLength > 0L)
/* 466:    */     {
/* 467:548 */       mergedCmd.append(" | ");
/* 468:549 */       mergedCmd.append("tail");
/* 469:550 */       mergedCmd.append(" -c ");
/* 470:551 */       mergedCmd.append(tailLength);
/* 471:552 */       mergedCmd.append(" >> ");
/* 472:553 */       mergedCmd.append(stdout);
/* 473:554 */       mergedCmd.append(" ; exit $PIPESTATUS ) 2>&1 | ");
/* 474:555 */       mergedCmd.append("tail");
/* 475:556 */       mergedCmd.append(" -c ");
/* 476:557 */       mergedCmd.append(tailLength);
/* 477:558 */       mergedCmd.append(" >> ");
/* 478:559 */       mergedCmd.append(stderr);
/* 479:560 */       mergedCmd.append(" ; exit $PIPESTATUS");
/* 480:    */     }
/* 481:    */     else
/* 482:    */     {
/* 483:562 */       mergedCmd.append(" 1>> ");
/* 484:563 */       mergedCmd.append(stdout);
/* 485:564 */       mergedCmd.append(" 2>> ");
/* 486:565 */       mergedCmd.append(stderr);
/* 487:    */     }
/* 488:567 */     return mergedCmd.toString();
/* 489:    */   }
/* 490:    */   
/* 491:    */   static String buildDebugScriptCommandLine(List<String> cmd, String debugout)
/* 492:    */     throws IOException
/* 493:    */   {
/* 494:581 */     StringBuilder mergedCmd = new StringBuilder();
/* 495:582 */     mergedCmd.append("exec ");
/* 496:583 */     boolean isExecutable = true;
/* 497:584 */     for (String s : cmd)
/* 498:    */     {
/* 499:585 */       if (isExecutable)
/* 500:    */       {
/* 501:588 */         mergedCmd.append(FileUtil.makeShellPath(new File(s)));
/* 502:589 */         isExecutable = false;
/* 503:    */       }
/* 504:    */       else
/* 505:    */       {
/* 506:591 */         mergedCmd.append(s);
/* 507:    */       }
/* 508:593 */       mergedCmd.append(" ");
/* 509:    */     }
/* 510:595 */     mergedCmd.append(" < /dev/null ");
/* 511:596 */     mergedCmd.append(" >");
/* 512:597 */     mergedCmd.append(debugout);
/* 513:598 */     mergedCmd.append(" 2>&1 ");
/* 514:599 */     return mergedCmd.toString();
/* 515:    */   }
/* 516:    */   
/* 517:    */   public static String addCommand(List<String> cmd, boolean isExecutable)
/* 518:    */     throws IOException
/* 519:    */   {
/* 520:612 */     StringBuffer command = new StringBuffer();
/* 521:613 */     for (String s : cmd)
/* 522:    */     {
/* 523:614 */       command.append('\'');
/* 524:615 */       if (isExecutable)
/* 525:    */       {
/* 526:618 */         command.append(FileUtil.makeShellPath(new File(s)));
/* 527:619 */         isExecutable = false;
/* 528:    */       }
/* 529:    */       else
/* 530:    */       {
/* 531:621 */         command.append(s);
/* 532:    */       }
/* 533:623 */       command.append('\'');
/* 534:624 */       command.append(" ");
/* 535:    */     }
/* 536:626 */     return command.toString();
/* 537:    */   }
/* 538:    */   
/* 539:    */   static File getUserLogDir()
/* 540:    */   {
/* 541:636 */     if (!LOG_DIR.exists())
/* 542:    */     {
/* 543:637 */       boolean b = LOG_DIR.mkdirs();
/* 544:638 */       if (!b) {
/* 545:639 */         LOG.debug("mkdirs failed. Ignoring.");
/* 546:    */       }
/* 547:    */     }
/* 548:642 */     return LOG_DIR;
/* 549:    */   }
/* 550:    */   
/* 551:    */   public static File getJobDir(JobID jobid)
/* 552:    */   {
/* 553:652 */     return new File(getUserLogDir(), jobid.toString());
/* 554:    */   }
/* 555:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.TaskLog
 * JD-Core Version:    0.7.0.1
 */