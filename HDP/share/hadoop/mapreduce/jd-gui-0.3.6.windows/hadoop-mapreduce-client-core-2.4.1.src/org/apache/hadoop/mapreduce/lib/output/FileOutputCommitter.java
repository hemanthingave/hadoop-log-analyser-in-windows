/*   1:    */ package org.apache.hadoop.mapreduce.lib.output;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import org.apache.commons.logging.Log;
/*   5:    */ import org.apache.commons.logging.LogFactory;
/*   6:    */ import org.apache.hadoop.classification.InterfaceAudience.Private;
/*   7:    */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*   8:    */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*   9:    */ import org.apache.hadoop.conf.Configuration;
/*  10:    */ import org.apache.hadoop.fs.FSDataOutputStream;
/*  11:    */ import org.apache.hadoop.fs.FileStatus;
/*  12:    */ import org.apache.hadoop.fs.FileSystem;
/*  13:    */ import org.apache.hadoop.fs.Path;
/*  14:    */ import org.apache.hadoop.fs.PathFilter;
/*  15:    */ import org.apache.hadoop.mapreduce.JobContext;
/*  16:    */ import org.apache.hadoop.mapreduce.JobStatus.State;
/*  17:    */ import org.apache.hadoop.mapreduce.OutputCommitter;
/*  18:    */ import org.apache.hadoop.mapreduce.TaskAttemptContext;
/*  19:    */ import org.apache.hadoop.mapreduce.TaskAttemptID;
/*  20:    */ 
/*  21:    */ @InterfaceAudience.Public
/*  22:    */ @InterfaceStability.Stable
/*  23:    */ public class FileOutputCommitter
/*  24:    */   extends OutputCommitter
/*  25:    */ {
/*  26: 45 */   private static final Log LOG = LogFactory.getLog(FileOutputCommitter.class);
/*  27:    */   public static final String PENDING_DIR_NAME = "_temporary";
/*  28:    */   @Deprecated
/*  29:    */   protected static final String TEMP_DIR_NAME = "_temporary";
/*  30:    */   public static final String SUCCEEDED_FILE_NAME = "_SUCCESS";
/*  31:    */   public static final String SUCCESSFUL_JOB_OUTPUT_DIR_MARKER = "mapreduce.fileoutputcommitter.marksuccessfuljobs";
/*  32: 62 */   private Path outputPath = null;
/*  33: 63 */   private Path workPath = null;
/*  34:    */   
/*  35:    */   public FileOutputCommitter(Path outputPath, TaskAttemptContext context)
/*  36:    */     throws IOException
/*  37:    */   {
/*  38: 74 */     this(outputPath, context);
/*  39: 75 */     if (outputPath != null) {
/*  40: 76 */       this.workPath = getTaskAttemptPath(context, outputPath);
/*  41:    */     }
/*  42:    */   }
/*  43:    */   
/*  44:    */   @InterfaceAudience.Private
/*  45:    */   public FileOutputCommitter(Path outputPath, JobContext context)
/*  46:    */     throws IOException
/*  47:    */   {
/*  48: 90 */     if (outputPath != null)
/*  49:    */     {
/*  50: 91 */       FileSystem fs = outputPath.getFileSystem(context.getConfiguration());
/*  51: 92 */       this.outputPath = fs.makeQualified(outputPath);
/*  52:    */     }
/*  53:    */   }
/*  54:    */   
/*  55:    */   private Path getOutputPath()
/*  56:    */   {
/*  57:101 */     return this.outputPath;
/*  58:    */   }
/*  59:    */   
/*  60:    */   private boolean hasOutputPath()
/*  61:    */   {
/*  62:108 */     return this.outputPath != null;
/*  63:    */   }
/*  64:    */   
/*  65:    */   private Path getPendingJobAttemptsPath()
/*  66:    */   {
/*  67:116 */     return getPendingJobAttemptsPath(getOutputPath());
/*  68:    */   }
/*  69:    */   
/*  70:    */   private static Path getPendingJobAttemptsPath(Path out)
/*  71:    */   {
/*  72:125 */     return new Path(out, "_temporary");
/*  73:    */   }
/*  74:    */   
/*  75:    */   private static int getAppAttemptId(JobContext context)
/*  76:    */   {
/*  77:134 */     return context.getConfiguration().getInt("mapreduce.job.application.attempt.id", 0);
/*  78:    */   }
/*  79:    */   
/*  80:    */   public Path getJobAttemptPath(JobContext context)
/*  81:    */   {
/*  82:145 */     return getJobAttemptPath(context, getOutputPath());
/*  83:    */   }
/*  84:    */   
/*  85:    */   public static Path getJobAttemptPath(JobContext context, Path out)
/*  86:    */   {
/*  87:156 */     return getJobAttemptPath(getAppAttemptId(context), out);
/*  88:    */   }
/*  89:    */   
/*  90:    */   protected Path getJobAttemptPath(int appAttemptId)
/*  91:    */   {
/*  92:165 */     return getJobAttemptPath(appAttemptId, getOutputPath());
/*  93:    */   }
/*  94:    */   
/*  95:    */   private static Path getJobAttemptPath(int appAttemptId, Path out)
/*  96:    */   {
/*  97:174 */     return new Path(getPendingJobAttemptsPath(out), String.valueOf(appAttemptId));
/*  98:    */   }
/*  99:    */   
/* 100:    */   private Path getPendingTaskAttemptsPath(JobContext context)
/* 101:    */   {
/* 102:183 */     return getPendingTaskAttemptsPath(context, getOutputPath());
/* 103:    */   }
/* 104:    */   
/* 105:    */   private static Path getPendingTaskAttemptsPath(JobContext context, Path out)
/* 106:    */   {
/* 107:192 */     return new Path(getJobAttemptPath(context, out), "_temporary");
/* 108:    */   }
/* 109:    */   
/* 110:    */   public Path getTaskAttemptPath(TaskAttemptContext context)
/* 111:    */   {
/* 112:203 */     return new Path(getPendingTaskAttemptsPath(context), String.valueOf(context.getTaskAttemptID()));
/* 113:    */   }
/* 114:    */   
/* 115:    */   public static Path getTaskAttemptPath(TaskAttemptContext context, Path out)
/* 116:    */   {
/* 117:216 */     return new Path(getPendingTaskAttemptsPath(context, out), String.valueOf(context.getTaskAttemptID()));
/* 118:    */   }
/* 119:    */   
/* 120:    */   public Path getCommittedTaskPath(TaskAttemptContext context)
/* 121:    */   {
/* 122:228 */     return getCommittedTaskPath(getAppAttemptId(context), context);
/* 123:    */   }
/* 124:    */   
/* 125:    */   public static Path getCommittedTaskPath(TaskAttemptContext context, Path out)
/* 126:    */   {
/* 127:232 */     return getCommittedTaskPath(getAppAttemptId(context), context, out);
/* 128:    */   }
/* 129:    */   
/* 130:    */   protected Path getCommittedTaskPath(int appAttemptId, TaskAttemptContext context)
/* 131:    */   {
/* 132:243 */     return new Path(getJobAttemptPath(appAttemptId), String.valueOf(context.getTaskAttemptID().getTaskID()));
/* 133:    */   }
/* 134:    */   
/* 135:    */   private static Path getCommittedTaskPath(int appAttemptId, TaskAttemptContext context, Path out)
/* 136:    */   {
/* 137:248 */     return new Path(getJobAttemptPath(appAttemptId, out), String.valueOf(context.getTaskAttemptID().getTaskID()));
/* 138:    */   }
/* 139:    */   
/* 140:    */   private static class CommittedTaskFilter
/* 141:    */     implements PathFilter
/* 142:    */   {
/* 143:    */     public boolean accept(Path path)
/* 144:    */     {
/* 145:255 */       return !"_temporary".equals(path.getName());
/* 146:    */     }
/* 147:    */   }
/* 148:    */   
/* 149:    */   private FileStatus[] getAllCommittedTaskPaths(JobContext context)
/* 150:    */     throws IOException
/* 151:    */   {
/* 152:267 */     Path jobAttemptPath = getJobAttemptPath(context);
/* 153:268 */     FileSystem fs = jobAttemptPath.getFileSystem(context.getConfiguration());
/* 154:269 */     return fs.listStatus(jobAttemptPath, new CommittedTaskFilter(null));
/* 155:    */   }
/* 156:    */   
/* 157:    */   public Path getWorkPath()
/* 158:    */     throws IOException
/* 159:    */   {
/* 160:278 */     return this.workPath;
/* 161:    */   }
/* 162:    */   
/* 163:    */   public void setupJob(JobContext context)
/* 164:    */     throws IOException
/* 165:    */   {
/* 166:287 */     if (hasOutputPath())
/* 167:    */     {
/* 168:288 */       Path jobAttemptPath = getJobAttemptPath(context);
/* 169:289 */       FileSystem fs = jobAttemptPath.getFileSystem(context.getConfiguration());
/* 170:291 */       if (!fs.mkdirs(jobAttemptPath)) {
/* 171:292 */         LOG.error("Mkdirs failed to create " + jobAttemptPath);
/* 172:    */       }
/* 173:    */     }
/* 174:    */     else
/* 175:    */     {
/* 176:295 */       LOG.warn("Output Path is null in setupJob()");
/* 177:    */     }
/* 178:    */   }
/* 179:    */   
/* 180:    */   public void commitJob(JobContext context)
/* 181:    */     throws IOException
/* 182:    */   {
/* 183:306 */     if (hasOutputPath())
/* 184:    */     {
/* 185:307 */       Path finalOutput = getOutputPath();
/* 186:308 */       FileSystem fs = finalOutput.getFileSystem(context.getConfiguration());
/* 187:309 */       for (FileStatus stat : getAllCommittedTaskPaths(context)) {
/* 188:310 */         mergePaths(fs, stat, finalOutput);
/* 189:    */       }
/* 190:314 */       cleanupJob(context);
/* 191:317 */       if (context.getConfiguration().getBoolean("mapreduce.fileoutputcommitter.marksuccessfuljobs", true))
/* 192:    */       {
/* 193:318 */         Path markerPath = new Path(this.outputPath, "_SUCCESS");
/* 194:319 */         fs.create(markerPath).close();
/* 195:    */       }
/* 196:    */     }
/* 197:    */     else
/* 198:    */     {
/* 199:322 */       LOG.warn("Output Path is null in commitJob()");
/* 200:    */     }
/* 201:    */   }
/* 202:    */   
/* 203:    */   private static void mergePaths(FileSystem fs, FileStatus from, Path to)
/* 204:    */     throws IOException
/* 205:    */   {
/* 206:337 */     LOG.debug("Merging data from " + from + " to " + to);
/* 207:338 */     if (from.isFile())
/* 208:    */     {
/* 209:339 */       if ((fs.exists(to)) && 
/* 210:340 */         (!fs.delete(to, true))) {
/* 211:341 */         throw new IOException("Failed to delete " + to);
/* 212:    */       }
/* 213:345 */       if (!fs.rename(from.getPath(), to)) {
/* 214:346 */         throw new IOException("Failed to rename " + from + " to " + to);
/* 215:    */       }
/* 216:    */     }
/* 217:348 */     else if (from.isDirectory())
/* 218:    */     {
/* 219:349 */       if (fs.exists(to))
/* 220:    */       {
/* 221:350 */         FileStatus toStat = fs.getFileStatus(to);
/* 222:351 */         if (!toStat.isDirectory())
/* 223:    */         {
/* 224:352 */           if (!fs.delete(to, true)) {
/* 225:353 */             throw new IOException("Failed to delete " + to);
/* 226:    */           }
/* 227:355 */           if (!fs.rename(from.getPath(), to)) {
/* 228:356 */             throw new IOException("Failed to rename " + from + " to " + to);
/* 229:    */           }
/* 230:    */         }
/* 231:    */         else
/* 232:    */         {
/* 233:360 */           for (FileStatus subFrom : fs.listStatus(from.getPath()))
/* 234:    */           {
/* 235:361 */             Path subTo = new Path(to, subFrom.getPath().getName());
/* 236:362 */             mergePaths(fs, subFrom, subTo);
/* 237:    */           }
/* 238:    */         }
/* 239:    */       }
/* 240:367 */       else if (!fs.rename(from.getPath(), to))
/* 241:    */       {
/* 242:368 */         throw new IOException("Failed to rename " + from + " to " + to);
/* 243:    */       }
/* 244:    */     }
/* 245:    */   }
/* 246:    */   
/* 247:    */   @Deprecated
/* 248:    */   public void cleanupJob(JobContext context)
/* 249:    */     throws IOException
/* 250:    */   {
/* 251:377 */     if (hasOutputPath())
/* 252:    */     {
/* 253:378 */       Path pendingJobAttemptsPath = getPendingJobAttemptsPath();
/* 254:379 */       FileSystem fs = pendingJobAttemptsPath.getFileSystem(context.getConfiguration());
/* 255:    */       
/* 256:381 */       fs.delete(pendingJobAttemptsPath, true);
/* 257:    */     }
/* 258:    */     else
/* 259:    */     {
/* 260:383 */       LOG.warn("Output Path is null in cleanupJob()");
/* 261:    */     }
/* 262:    */   }
/* 263:    */   
/* 264:    */   public void abortJob(JobContext context, JobStatus.State state)
/* 265:    */     throws IOException
/* 266:    */   {
/* 267:395 */     cleanupJob(context);
/* 268:    */   }
/* 269:    */   
/* 270:    */   public void setupTask(TaskAttemptContext context)
/* 271:    */     throws IOException
/* 272:    */   {}
/* 273:    */   
/* 274:    */   public void commitTask(TaskAttemptContext context)
/* 275:    */     throws IOException
/* 276:    */   {
/* 277:415 */     commitTask(context, null);
/* 278:    */   }
/* 279:    */   
/* 280:    */   @InterfaceAudience.Private
/* 281:    */   public void commitTask(TaskAttemptContext context, Path taskAttemptPath)
/* 282:    */     throws IOException
/* 283:    */   {
/* 284:421 */     TaskAttemptID attemptId = context.getTaskAttemptID();
/* 285:422 */     if (hasOutputPath())
/* 286:    */     {
/* 287:423 */       context.progress();
/* 288:424 */       if (taskAttemptPath == null) {
/* 289:425 */         taskAttemptPath = getTaskAttemptPath(context);
/* 290:    */       }
/* 291:427 */       Path committedTaskPath = getCommittedTaskPath(context);
/* 292:428 */       FileSystem fs = taskAttemptPath.getFileSystem(context.getConfiguration());
/* 293:429 */       if (fs.exists(taskAttemptPath))
/* 294:    */       {
/* 295:430 */         if ((fs.exists(committedTaskPath)) && 
/* 296:431 */           (!fs.delete(committedTaskPath, true))) {
/* 297:432 */           throw new IOException("Could not delete " + committedTaskPath);
/* 298:    */         }
/* 299:435 */         if (!fs.rename(taskAttemptPath, committedTaskPath)) {
/* 300:436 */           throw new IOException("Could not rename " + taskAttemptPath + " to " + committedTaskPath);
/* 301:    */         }
/* 302:439 */         LOG.info("Saved output of task '" + attemptId + "' to " + committedTaskPath);
/* 303:    */       }
/* 304:    */       else
/* 305:    */       {
/* 306:442 */         LOG.warn("No Output found for " + attemptId);
/* 307:    */       }
/* 308:    */     }
/* 309:    */     else
/* 310:    */     {
/* 311:445 */       LOG.warn("Output Path is null in commitTask()");
/* 312:    */     }
/* 313:    */   }
/* 314:    */   
/* 315:    */   public void abortTask(TaskAttemptContext context)
/* 316:    */     throws IOException
/* 317:    */   {
/* 318:455 */     abortTask(context, null);
/* 319:    */   }
/* 320:    */   
/* 321:    */   @InterfaceAudience.Private
/* 322:    */   public void abortTask(TaskAttemptContext context, Path taskAttemptPath)
/* 323:    */     throws IOException
/* 324:    */   {
/* 325:460 */     if (hasOutputPath())
/* 326:    */     {
/* 327:461 */       context.progress();
/* 328:462 */       if (taskAttemptPath == null) {
/* 329:463 */         taskAttemptPath = getTaskAttemptPath(context);
/* 330:    */       }
/* 331:465 */       FileSystem fs = taskAttemptPath.getFileSystem(context.getConfiguration());
/* 332:466 */       if (!fs.delete(taskAttemptPath, true)) {
/* 333:467 */         LOG.warn("Could not delete " + taskAttemptPath);
/* 334:    */       }
/* 335:    */     }
/* 336:    */     else
/* 337:    */     {
/* 338:470 */       LOG.warn("Output Path is null in abortTask()");
/* 339:    */     }
/* 340:    */   }
/* 341:    */   
/* 342:    */   public boolean needsTaskCommit(TaskAttemptContext context)
/* 343:    */     throws IOException
/* 344:    */   {
/* 345:481 */     return needsTaskCommit(context, null);
/* 346:    */   }
/* 347:    */   
/* 348:    */   @InterfaceAudience.Private
/* 349:    */   public boolean needsTaskCommit(TaskAttemptContext context, Path taskAttemptPath)
/* 350:    */     throws IOException
/* 351:    */   {
/* 352:487 */     if (hasOutputPath())
/* 353:    */     {
/* 354:488 */       if (taskAttemptPath == null) {
/* 355:489 */         taskAttemptPath = getTaskAttemptPath(context);
/* 356:    */       }
/* 357:491 */       FileSystem fs = taskAttemptPath.getFileSystem(context.getConfiguration());
/* 358:492 */       return fs.exists(taskAttemptPath);
/* 359:    */     }
/* 360:494 */     return false;
/* 361:    */   }
/* 362:    */   
/* 363:    */   public boolean isRecoverySupported()
/* 364:    */   {
/* 365:499 */     return true;
/* 366:    */   }
/* 367:    */   
/* 368:    */   public void recoverTask(TaskAttemptContext context)
/* 369:    */     throws IOException
/* 370:    */   {
/* 371:505 */     if (hasOutputPath())
/* 372:    */     {
/* 373:506 */       context.progress();
/* 374:507 */       TaskAttemptID attemptId = context.getTaskAttemptID();
/* 375:508 */       int previousAttempt = getAppAttemptId(context) - 1;
/* 376:509 */       if (previousAttempt < 0) {
/* 377:510 */         throw new IOException("Cannot recover task output for first attempt...");
/* 378:    */       }
/* 379:513 */       Path committedTaskPath = getCommittedTaskPath(context);
/* 380:514 */       Path previousCommittedTaskPath = getCommittedTaskPath(previousAttempt, context);
/* 381:    */       
/* 382:516 */       FileSystem fs = committedTaskPath.getFileSystem(context.getConfiguration());
/* 383:    */       
/* 384:518 */       LOG.debug("Trying to recover task from " + previousCommittedTaskPath + " into " + committedTaskPath);
/* 385:520 */       if (fs.exists(previousCommittedTaskPath))
/* 386:    */       {
/* 387:521 */         if ((fs.exists(committedTaskPath)) && 
/* 388:522 */           (!fs.delete(committedTaskPath, true))) {
/* 389:523 */           throw new IOException("Could not delete " + committedTaskPath);
/* 390:    */         }
/* 391:527 */         Path committedParent = committedTaskPath.getParent();
/* 392:528 */         fs.mkdirs(committedParent);
/* 393:529 */         if (!fs.rename(previousCommittedTaskPath, committedTaskPath)) {
/* 394:530 */           throw new IOException("Could not rename " + previousCommittedTaskPath + " to " + committedTaskPath);
/* 395:    */         }
/* 396:533 */         LOG.info("Saved output of " + attemptId + " to " + committedTaskPath);
/* 397:    */       }
/* 398:    */       else
/* 399:    */       {
/* 400:535 */         LOG.warn(attemptId + " had no output to recover.");
/* 401:    */       }
/* 402:    */     }
/* 403:    */     else
/* 404:    */     {
/* 405:538 */       LOG.warn("Output Path is null in recoverTask()");
/* 406:    */     }
/* 407:    */   }
/* 408:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.lib.output.FileOutputCommitter
 * JD-Core Version:    0.7.0.1
 */