/*   1:    */ package org.apache.hadoop.mapreduce.jobhistory;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.PrintStream;
/*   5:    */ import java.util.HashMap;
/*   6:    */ import java.util.LinkedList;
/*   7:    */ import java.util.List;
/*   8:    */ import java.util.Map;
/*   9:    */ import org.apache.commons.logging.Log;
/*  10:    */ import org.apache.commons.logging.LogFactory;
/*  11:    */ import org.apache.hadoop.classification.InterfaceAudience.Private;
/*  12:    */ import org.apache.hadoop.classification.InterfaceStability.Unstable;
/*  13:    */ import org.apache.hadoop.fs.FSDataInputStream;
/*  14:    */ import org.apache.hadoop.fs.FileSystem;
/*  15:    */ import org.apache.hadoop.fs.Path;
/*  16:    */ import org.apache.hadoop.mapred.JobPriority;
/*  17:    */ import org.apache.hadoop.mapred.JobStatus;
/*  18:    */ import org.apache.hadoop.mapred.TaskStatus.State;
/*  19:    */ import org.apache.hadoop.mapreduce.Counters;
/*  20:    */ import org.apache.hadoop.mapreduce.JobACL;
/*  21:    */ import org.apache.hadoop.mapreduce.JobID;
/*  22:    */ import org.apache.hadoop.mapreduce.TaskAttemptID;
/*  23:    */ import org.apache.hadoop.mapreduce.TaskID;
/*  24:    */ import org.apache.hadoop.mapreduce.TaskType;
/*  25:    */ import org.apache.hadoop.security.authorize.AccessControlList;
/*  26:    */ import org.apache.hadoop.util.StringInterner;
/*  27:    */ import org.apache.hadoop.yarn.api.records.ApplicationAttemptId;
/*  28:    */ import org.apache.hadoop.yarn.api.records.ContainerId;
/*  29:    */ 
/*  30:    */ @InterfaceAudience.Private
/*  31:    */ @InterfaceStability.Unstable
/*  32:    */ public class JobHistoryParser
/*  33:    */   implements HistoryEventHandler
/*  34:    */ {
/*  35: 59 */   private static final Log LOG = LogFactory.getLog(JobHistoryParser.class);
/*  36:    */   private final FSDataInputStream in;
/*  37: 62 */   private JobInfo info = null;
/*  38: 64 */   private IOException parseException = null;
/*  39:    */   
/*  40:    */   public JobHistoryParser(FileSystem fs, String file)
/*  41:    */     throws IOException
/*  42:    */   {
/*  43: 74 */     this(fs, new Path(file));
/*  44:    */   }
/*  45:    */   
/*  46:    */   public JobHistoryParser(FileSystem fs, Path historyFile)
/*  47:    */     throws IOException
/*  48:    */   {
/*  49: 86 */     this(fs.open(historyFile));
/*  50:    */   }
/*  51:    */   
/*  52:    */   public JobHistoryParser(FSDataInputStream in)
/*  53:    */   {
/*  54: 94 */     this.in = in;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public synchronized void parse(HistoryEventHandler handler)
/*  58:    */     throws IOException
/*  59:    */   {
/*  60: 99 */     parse(new EventReader(this.in), handler);
/*  61:    */   }
/*  62:    */   
/*  63:    */   @InterfaceAudience.Private
/*  64:    */   public synchronized void parse(EventReader reader, HistoryEventHandler handler)
/*  65:    */     throws IOException
/*  66:    */   {
/*  67:108 */     int eventCtr = 0;
/*  68:    */     try
/*  69:    */     {
/*  70:    */       HistoryEvent event;
/*  71:111 */       while ((event = reader.getNextEvent()) != null)
/*  72:    */       {
/*  73:112 */         handler.handleEvent(event);
/*  74:113 */         eventCtr++;
/*  75:    */       }
/*  76:    */     }
/*  77:    */     catch (IOException ioe)
/*  78:    */     {
/*  79:116 */       LOG.info("Caught exception parsing history file after " + eventCtr + " events", ioe);
/*  80:    */       
/*  81:118 */       this.parseException = ioe;
/*  82:    */     }
/*  83:    */     finally
/*  84:    */     {
/*  85:120 */       this.in.close();
/*  86:    */     }
/*  87:    */   }
/*  88:    */   
/*  89:    */   public synchronized JobInfo parse()
/*  90:    */     throws IOException
/*  91:    */   {
/*  92:139 */     return parse(new EventReader(this.in));
/*  93:    */   }
/*  94:    */   
/*  95:    */   @InterfaceAudience.Private
/*  96:    */   public synchronized JobInfo parse(EventReader reader)
/*  97:    */     throws IOException
/*  98:    */   {
/*  99:148 */     if (this.info != null) {
/* 100:149 */       return this.info;
/* 101:    */     }
/* 102:152 */     this.info = new JobInfo();
/* 103:153 */     parse(reader, this);
/* 104:154 */     return this.info;
/* 105:    */   }
/* 106:    */   
/* 107:    */   public synchronized IOException getParseException()
/* 108:    */   {
/* 109:164 */     return this.parseException;
/* 110:    */   }
/* 111:    */   
/* 112:    */   public void handleEvent(HistoryEvent event)
/* 113:    */   {
/* 114:169 */     EventType type = event.getEventType();
/* 115:171 */     switch (1.$SwitchMap$org$apache$hadoop$mapreduce$jobhistory$EventType[type.ordinal()])
/* 116:    */     {
/* 117:    */     case 1: 
/* 118:173 */       handleJobSubmittedEvent((JobSubmittedEvent)event);
/* 119:174 */       break;
/* 120:    */     case 2: 
/* 121:    */       break;
/* 122:    */     case 3: 
/* 123:178 */       handleJobInfoChangeEvent((JobInfoChangeEvent)event);
/* 124:179 */       break;
/* 125:    */     case 4: 
/* 126:181 */       handleJobInitedEvent((JobInitedEvent)event);
/* 127:182 */       break;
/* 128:    */     case 5: 
/* 129:184 */       handleJobPriorityChangeEvent((JobPriorityChangeEvent)event);
/* 130:185 */       break;
/* 131:    */     case 6: 
/* 132:187 */       handleJobQueueChangeEvent((JobQueueChangeEvent)event);
/* 133:188 */       break;
/* 134:    */     case 7: 
/* 135:    */     case 8: 
/* 136:    */     case 9: 
/* 137:192 */       handleJobFailedEvent((JobUnsuccessfulCompletionEvent)event);
/* 138:193 */       break;
/* 139:    */     case 10: 
/* 140:195 */       handleJobFinishedEvent((JobFinishedEvent)event);
/* 141:196 */       break;
/* 142:    */     case 11: 
/* 143:198 */       handleTaskStartedEvent((TaskStartedEvent)event);
/* 144:199 */       break;
/* 145:    */     case 12: 
/* 146:201 */       handleTaskFailedEvent((TaskFailedEvent)event);
/* 147:202 */       break;
/* 148:    */     case 13: 
/* 149:204 */       handleTaskUpdatedEvent((TaskUpdatedEvent)event);
/* 150:205 */       break;
/* 151:    */     case 14: 
/* 152:207 */       handleTaskFinishedEvent((TaskFinishedEvent)event);
/* 153:208 */       break;
/* 154:    */     case 15: 
/* 155:    */     case 16: 
/* 156:    */     case 17: 
/* 157:    */     case 18: 
/* 158:213 */       handleTaskAttemptStartedEvent((TaskAttemptStartedEvent)event);
/* 159:214 */       break;
/* 160:    */     case 19: 
/* 161:    */     case 20: 
/* 162:    */     case 21: 
/* 163:    */     case 22: 
/* 164:    */     case 23: 
/* 165:    */     case 24: 
/* 166:    */     case 25: 
/* 167:    */     case 26: 
/* 168:223 */       handleTaskAttemptFailedEvent((TaskAttemptUnsuccessfulCompletionEvent)event);
/* 169:    */       
/* 170:225 */       break;
/* 171:    */     case 27: 
/* 172:227 */       handleMapAttemptFinishedEvent((MapAttemptFinishedEvent)event);
/* 173:228 */       break;
/* 174:    */     case 28: 
/* 175:230 */       handleReduceAttemptFinishedEvent((ReduceAttemptFinishedEvent)event);
/* 176:231 */       break;
/* 177:    */     case 29: 
/* 178:    */     case 30: 
/* 179:234 */       handleTaskAttemptFinishedEvent((TaskAttemptFinishedEvent)event);
/* 180:235 */       break;
/* 181:    */     case 31: 
/* 182:237 */       handleAMStartedEvent((AMStartedEvent)event);
/* 183:238 */       break;
/* 184:    */     }
/* 185:    */   }
/* 186:    */   
/* 187:    */   private void handleTaskAttemptFinishedEvent(TaskAttemptFinishedEvent event)
/* 188:    */   {
/* 189:245 */     TaskInfo taskInfo = (TaskInfo)this.info.tasksMap.get(event.getTaskId());
/* 190:246 */     TaskAttemptInfo attemptInfo = (TaskAttemptInfo)taskInfo.attemptsMap.get(event.getAttemptId());
/* 191:    */     
/* 192:248 */     attemptInfo.finishTime = event.getFinishTime();
/* 193:249 */     attemptInfo.status = StringInterner.weakIntern(event.getTaskStatus());
/* 194:250 */     attemptInfo.state = StringInterner.weakIntern(event.getState());
/* 195:251 */     attemptInfo.counters = event.getCounters();
/* 196:252 */     attemptInfo.hostname = StringInterner.weakIntern(event.getHostname());
/* 197:253 */     this.info.completedTaskAttemptsMap.put(event.getAttemptId(), attemptInfo);
/* 198:    */   }
/* 199:    */   
/* 200:    */   private void handleReduceAttemptFinishedEvent(ReduceAttemptFinishedEvent event)
/* 201:    */   {
/* 202:258 */     TaskInfo taskInfo = (TaskInfo)this.info.tasksMap.get(event.getTaskId());
/* 203:259 */     TaskAttemptInfo attemptInfo = (TaskAttemptInfo)taskInfo.attemptsMap.get(event.getAttemptId());
/* 204:    */     
/* 205:261 */     attemptInfo.finishTime = event.getFinishTime();
/* 206:262 */     attemptInfo.status = StringInterner.weakIntern(event.getTaskStatus());
/* 207:263 */     attemptInfo.state = StringInterner.weakIntern(event.getState());
/* 208:264 */     attemptInfo.shuffleFinishTime = event.getShuffleFinishTime();
/* 209:265 */     attemptInfo.sortFinishTime = event.getSortFinishTime();
/* 210:266 */     attemptInfo.counters = event.getCounters();
/* 211:267 */     attemptInfo.hostname = StringInterner.weakIntern(event.getHostname());
/* 212:268 */     attemptInfo.port = event.getPort();
/* 213:269 */     attemptInfo.rackname = StringInterner.weakIntern(event.getRackName());
/* 214:270 */     this.info.completedTaskAttemptsMap.put(event.getAttemptId(), attemptInfo);
/* 215:    */   }
/* 216:    */   
/* 217:    */   private void handleMapAttemptFinishedEvent(MapAttemptFinishedEvent event)
/* 218:    */   {
/* 219:274 */     TaskInfo taskInfo = (TaskInfo)this.info.tasksMap.get(event.getTaskId());
/* 220:275 */     TaskAttemptInfo attemptInfo = (TaskAttemptInfo)taskInfo.attemptsMap.get(event.getAttemptId());
/* 221:    */     
/* 222:277 */     attemptInfo.finishTime = event.getFinishTime();
/* 223:278 */     attemptInfo.status = StringInterner.weakIntern(event.getTaskStatus());
/* 224:279 */     attemptInfo.state = StringInterner.weakIntern(event.getState());
/* 225:280 */     attemptInfo.mapFinishTime = event.getMapFinishTime();
/* 226:281 */     attemptInfo.counters = event.getCounters();
/* 227:282 */     attemptInfo.hostname = StringInterner.weakIntern(event.getHostname());
/* 228:283 */     attemptInfo.port = event.getPort();
/* 229:284 */     attemptInfo.rackname = StringInterner.weakIntern(event.getRackName());
/* 230:285 */     this.info.completedTaskAttemptsMap.put(event.getAttemptId(), attemptInfo);
/* 231:    */   }
/* 232:    */   
/* 233:    */   private void handleTaskAttemptFailedEvent(TaskAttemptUnsuccessfulCompletionEvent event)
/* 234:    */   {
/* 235:290 */     TaskInfo taskInfo = (TaskInfo)this.info.tasksMap.get(event.getTaskId());
/* 236:291 */     TaskAttemptInfo attemptInfo = (TaskAttemptInfo)taskInfo.attemptsMap.get(event.getTaskAttemptId());
/* 237:    */     
/* 238:293 */     attemptInfo.finishTime = event.getFinishTime();
/* 239:294 */     attemptInfo.error = StringInterner.weakIntern(event.getError());
/* 240:295 */     attemptInfo.status = StringInterner.weakIntern(event.getTaskStatus());
/* 241:296 */     attemptInfo.hostname = StringInterner.weakIntern(event.getHostname());
/* 242:297 */     attemptInfo.port = event.getPort();
/* 243:298 */     attemptInfo.rackname = StringInterner.weakIntern(event.getRackName());
/* 244:299 */     attemptInfo.shuffleFinishTime = event.getFinishTime();
/* 245:300 */     attemptInfo.sortFinishTime = event.getFinishTime();
/* 246:301 */     attemptInfo.mapFinishTime = event.getFinishTime();
/* 247:302 */     attemptInfo.counters = event.getCounters();
/* 248:303 */     if (TaskStatus.State.SUCCEEDED.toString().equals(taskInfo.status)) {
/* 249:306 */       if (attemptInfo.getAttemptId().equals(taskInfo.getSuccessfulAttemptId()))
/* 250:    */       {
/* 251:311 */         taskInfo.counters = null;
/* 252:312 */         taskInfo.finishTime = -1L;
/* 253:313 */         taskInfo.status = null;
/* 254:314 */         taskInfo.successfulAttemptId = null;
/* 255:    */       }
/* 256:    */     }
/* 257:317 */     this.info.completedTaskAttemptsMap.put(event.getTaskAttemptId(), attemptInfo);
/* 258:    */   }
/* 259:    */   
/* 260:    */   private void handleTaskAttemptStartedEvent(TaskAttemptStartedEvent event)
/* 261:    */   {
/* 262:321 */     TaskAttemptID attemptId = event.getTaskAttemptId();
/* 263:322 */     TaskInfo taskInfo = (TaskInfo)this.info.tasksMap.get(event.getTaskId());
/* 264:    */     
/* 265:324 */     TaskAttemptInfo attemptInfo = new TaskAttemptInfo();
/* 266:325 */     attemptInfo.startTime = event.getStartTime();
/* 267:326 */     attemptInfo.attemptId = event.getTaskAttemptId();
/* 268:327 */     attemptInfo.httpPort = event.getHttpPort();
/* 269:328 */     attemptInfo.trackerName = StringInterner.weakIntern(event.getTrackerName());
/* 270:329 */     attemptInfo.taskType = event.getTaskType();
/* 271:330 */     attemptInfo.shufflePort = event.getShufflePort();
/* 272:331 */     attemptInfo.containerId = event.getContainerId();
/* 273:    */     
/* 274:333 */     taskInfo.attemptsMap.put(attemptId, attemptInfo);
/* 275:    */   }
/* 276:    */   
/* 277:    */   private void handleTaskFinishedEvent(TaskFinishedEvent event)
/* 278:    */   {
/* 279:337 */     TaskInfo taskInfo = (TaskInfo)this.info.tasksMap.get(event.getTaskId());
/* 280:338 */     taskInfo.counters = event.getCounters();
/* 281:339 */     taskInfo.finishTime = event.getFinishTime();
/* 282:340 */     taskInfo.status = TaskStatus.State.SUCCEEDED.toString();
/* 283:341 */     taskInfo.successfulAttemptId = event.getSuccessfulTaskAttemptId();
/* 284:    */   }
/* 285:    */   
/* 286:    */   private void handleTaskUpdatedEvent(TaskUpdatedEvent event)
/* 287:    */   {
/* 288:345 */     TaskInfo taskInfo = (TaskInfo)this.info.tasksMap.get(event.getTaskId());
/* 289:346 */     taskInfo.finishTime = event.getFinishTime();
/* 290:    */   }
/* 291:    */   
/* 292:    */   private void handleTaskFailedEvent(TaskFailedEvent event)
/* 293:    */   {
/* 294:350 */     TaskInfo taskInfo = (TaskInfo)this.info.tasksMap.get(event.getTaskId());
/* 295:351 */     taskInfo.status = TaskStatus.State.FAILED.toString();
/* 296:352 */     taskInfo.finishTime = event.getFinishTime();
/* 297:353 */     taskInfo.error = StringInterner.weakIntern(event.getError());
/* 298:354 */     taskInfo.failedDueToAttemptId = event.getFailedAttemptID();
/* 299:355 */     taskInfo.counters = event.getCounters();
/* 300:    */   }
/* 301:    */   
/* 302:    */   private void handleTaskStartedEvent(TaskStartedEvent event)
/* 303:    */   {
/* 304:359 */     TaskInfo taskInfo = new TaskInfo();
/* 305:360 */     taskInfo.taskId = event.getTaskId();
/* 306:361 */     taskInfo.startTime = event.getStartTime();
/* 307:362 */     taskInfo.taskType = event.getTaskType();
/* 308:363 */     taskInfo.splitLocations = event.getSplitLocations();
/* 309:364 */     this.info.tasksMap.put(event.getTaskId(), taskInfo);
/* 310:    */   }
/* 311:    */   
/* 312:    */   private void handleJobFailedEvent(JobUnsuccessfulCompletionEvent event)
/* 313:    */   {
/* 314:368 */     this.info.finishTime = event.getFinishTime();
/* 315:369 */     this.info.finishedMaps = event.getFinishedMaps();
/* 316:370 */     this.info.finishedReduces = event.getFinishedReduces();
/* 317:371 */     this.info.jobStatus = StringInterner.weakIntern(event.getStatus());
/* 318:372 */     this.info.errorInfo = StringInterner.weakIntern(event.getDiagnostics());
/* 319:    */   }
/* 320:    */   
/* 321:    */   private void handleJobFinishedEvent(JobFinishedEvent event)
/* 322:    */   {
/* 323:376 */     this.info.finishTime = event.getFinishTime();
/* 324:377 */     this.info.finishedMaps = event.getFinishedMaps();
/* 325:378 */     this.info.finishedReduces = event.getFinishedReduces();
/* 326:379 */     this.info.failedMaps = event.getFailedMaps();
/* 327:380 */     this.info.failedReduces = event.getFailedReduces();
/* 328:381 */     this.info.totalCounters = event.getTotalCounters();
/* 329:382 */     this.info.mapCounters = event.getMapCounters();
/* 330:383 */     this.info.reduceCounters = event.getReduceCounters();
/* 331:384 */     this.info.jobStatus = JobStatus.getJobRunState(JobStatus.SUCCEEDED);
/* 332:    */   }
/* 333:    */   
/* 334:    */   private void handleJobPriorityChangeEvent(JobPriorityChangeEvent event)
/* 335:    */   {
/* 336:388 */     this.info.priority = event.getPriority();
/* 337:    */   }
/* 338:    */   
/* 339:    */   private void handleJobQueueChangeEvent(JobQueueChangeEvent event)
/* 340:    */   {
/* 341:392 */     this.info.jobQueueName = event.getJobQueueName();
/* 342:    */   }
/* 343:    */   
/* 344:    */   private void handleJobInitedEvent(JobInitedEvent event)
/* 345:    */   {
/* 346:396 */     this.info.launchTime = event.getLaunchTime();
/* 347:397 */     this.info.totalMaps = event.getTotalMaps();
/* 348:398 */     this.info.totalReduces = event.getTotalReduces();
/* 349:399 */     this.info.uberized = event.getUberized();
/* 350:    */   }
/* 351:    */   
/* 352:    */   private void handleAMStartedEvent(AMStartedEvent event)
/* 353:    */   {
/* 354:403 */     AMInfo amInfo = new AMInfo();
/* 355:404 */     amInfo.appAttemptId = event.getAppAttemptId();
/* 356:405 */     amInfo.startTime = event.getStartTime();
/* 357:406 */     amInfo.containerId = event.getContainerId();
/* 358:407 */     amInfo.nodeManagerHost = StringInterner.weakIntern(event.getNodeManagerHost());
/* 359:408 */     amInfo.nodeManagerPort = event.getNodeManagerPort();
/* 360:409 */     amInfo.nodeManagerHttpPort = event.getNodeManagerHttpPort();
/* 361:410 */     if (this.info.amInfos == null) {
/* 362:411 */       this.info.amInfos = new LinkedList();
/* 363:    */     }
/* 364:413 */     this.info.amInfos.add(amInfo);
/* 365:414 */     this.info.latestAmInfo = amInfo;
/* 366:    */   }
/* 367:    */   
/* 368:    */   private void handleJobInfoChangeEvent(JobInfoChangeEvent event)
/* 369:    */   {
/* 370:418 */     this.info.submitTime = event.getSubmitTime();
/* 371:419 */     this.info.launchTime = event.getLaunchTime();
/* 372:    */   }
/* 373:    */   
/* 374:    */   private void handleJobSubmittedEvent(JobSubmittedEvent event)
/* 375:    */   {
/* 376:423 */     this.info.jobid = event.getJobId();
/* 377:424 */     this.info.jobname = event.getJobName();
/* 378:425 */     this.info.username = StringInterner.weakIntern(event.getUserName());
/* 379:426 */     this.info.submitTime = event.getSubmitTime();
/* 380:427 */     this.info.jobConfPath = event.getJobConfPath();
/* 381:428 */     this.info.jobACLs = event.getJobAcls();
/* 382:429 */     this.info.jobQueueName = StringInterner.weakIntern(event.getJobQueueName());
/* 383:    */   }
/* 384:    */   
/* 385:    */   public static class JobInfo
/* 386:    */   {
/* 387:436 */     String errorInfo = "";
/* 388:    */     long submitTime;
/* 389:    */     long finishTime;
/* 390:    */     JobID jobid;
/* 391:    */     String username;
/* 392:    */     String jobname;
/* 393:    */     String jobQueueName;
/* 394:    */     String jobConfPath;
/* 395:    */     long launchTime;
/* 396:    */     int totalMaps;
/* 397:    */     int totalReduces;
/* 398:    */     int failedMaps;
/* 399:    */     int failedReduces;
/* 400:    */     int finishedMaps;
/* 401:    */     int finishedReduces;
/* 402:    */     String jobStatus;
/* 403:    */     Counters totalCounters;
/* 404:    */     Counters mapCounters;
/* 405:    */     Counters reduceCounters;
/* 406:    */     JobPriority priority;
/* 407:    */     Map<JobACL, AccessControlList> jobACLs;
/* 408:    */     Map<TaskID, JobHistoryParser.TaskInfo> tasksMap;
/* 409:    */     Map<TaskAttemptID, JobHistoryParser.TaskAttemptInfo> completedTaskAttemptsMap;
/* 410:    */     List<JobHistoryParser.AMInfo> amInfos;
/* 411:    */     JobHistoryParser.AMInfo latestAmInfo;
/* 412:    */     boolean uberized;
/* 413:    */     
/* 414:    */     public JobInfo()
/* 415:    */     {
/* 416:468 */       this.submitTime = (this.launchTime = this.finishTime = -1L);
/* 417:469 */       this.totalMaps = (this.totalReduces = this.failedMaps = this.failedReduces = 0);
/* 418:470 */       this.finishedMaps = (this.finishedReduces = 0);
/* 419:471 */       this.username = (this.jobname = this.jobConfPath = this.jobQueueName = "");
/* 420:472 */       this.tasksMap = new HashMap();
/* 421:473 */       this.completedTaskAttemptsMap = new HashMap();
/* 422:474 */       this.jobACLs = new HashMap();
/* 423:475 */       this.priority = JobPriority.NORMAL;
/* 424:    */     }
/* 425:    */     
/* 426:    */     public void printAll()
/* 427:    */     {
/* 428:480 */       System.out.println("JOBNAME: " + this.jobname);
/* 429:481 */       System.out.println("USERNAME: " + this.username);
/* 430:482 */       System.out.println("JOB_QUEUE_NAME: " + this.jobQueueName);
/* 431:483 */       System.out.println("SUBMIT_TIME" + this.submitTime);
/* 432:484 */       System.out.println("LAUNCH_TIME: " + this.launchTime);
/* 433:485 */       System.out.println("JOB_STATUS: " + this.jobStatus);
/* 434:486 */       System.out.println("PRIORITY: " + this.priority);
/* 435:487 */       System.out.println("TOTAL_MAPS: " + this.totalMaps);
/* 436:488 */       System.out.println("TOTAL_REDUCES: " + this.totalReduces);
/* 437:489 */       if (this.mapCounters != null) {
/* 438:490 */         System.out.println("MAP_COUNTERS:" + this.mapCounters.toString());
/* 439:    */       }
/* 440:492 */       if (this.reduceCounters != null) {
/* 441:493 */         System.out.println("REDUCE_COUNTERS:" + this.reduceCounters.toString());
/* 442:    */       }
/* 443:495 */       if (this.totalCounters != null) {
/* 444:496 */         System.out.println("TOTAL_COUNTERS: " + this.totalCounters.toString());
/* 445:    */       }
/* 446:498 */       System.out.println("UBERIZED: " + this.uberized);
/* 447:499 */       if (this.amInfos != null) {
/* 448:500 */         for (JobHistoryParser.AMInfo amInfo : this.amInfos) {
/* 449:501 */           amInfo.printAll();
/* 450:    */         }
/* 451:    */       }
/* 452:504 */       for (JobHistoryParser.TaskInfo ti : this.tasksMap.values()) {
/* 453:505 */         ti.printAll();
/* 454:    */       }
/* 455:    */     }
/* 456:    */     
/* 457:    */     public long getSubmitTime()
/* 458:    */     {
/* 459:510 */       return this.submitTime;
/* 460:    */     }
/* 461:    */     
/* 462:    */     public long getFinishTime()
/* 463:    */     {
/* 464:512 */       return this.finishTime;
/* 465:    */     }
/* 466:    */     
/* 467:    */     public JobID getJobId()
/* 468:    */     {
/* 469:514 */       return this.jobid;
/* 470:    */     }
/* 471:    */     
/* 472:    */     public String getUsername()
/* 473:    */     {
/* 474:516 */       return this.username;
/* 475:    */     }
/* 476:    */     
/* 477:    */     public String getJobname()
/* 478:    */     {
/* 479:518 */       return this.jobname;
/* 480:    */     }
/* 481:    */     
/* 482:    */     public String getJobQueueName()
/* 483:    */     {
/* 484:520 */       return this.jobQueueName;
/* 485:    */     }
/* 486:    */     
/* 487:    */     public String getJobConfPath()
/* 488:    */     {
/* 489:522 */       return this.jobConfPath;
/* 490:    */     }
/* 491:    */     
/* 492:    */     public long getLaunchTime()
/* 493:    */     {
/* 494:524 */       return this.launchTime;
/* 495:    */     }
/* 496:    */     
/* 497:    */     public long getTotalMaps()
/* 498:    */     {
/* 499:526 */       return this.totalMaps;
/* 500:    */     }
/* 501:    */     
/* 502:    */     public long getTotalReduces()
/* 503:    */     {
/* 504:528 */       return this.totalReduces;
/* 505:    */     }
/* 506:    */     
/* 507:    */     public long getFailedMaps()
/* 508:    */     {
/* 509:530 */       return this.failedMaps;
/* 510:    */     }
/* 511:    */     
/* 512:    */     public long getFailedReduces()
/* 513:    */     {
/* 514:532 */       return this.failedReduces;
/* 515:    */     }
/* 516:    */     
/* 517:    */     public long getFinishedMaps()
/* 518:    */     {
/* 519:534 */       return this.finishedMaps;
/* 520:    */     }
/* 521:    */     
/* 522:    */     public long getFinishedReduces()
/* 523:    */     {
/* 524:536 */       return this.finishedReduces;
/* 525:    */     }
/* 526:    */     
/* 527:    */     public String getJobStatus()
/* 528:    */     {
/* 529:538 */       return this.jobStatus;
/* 530:    */     }
/* 531:    */     
/* 532:    */     public String getErrorInfo()
/* 533:    */     {
/* 534:539 */       return this.errorInfo;
/* 535:    */     }
/* 536:    */     
/* 537:    */     public Counters getTotalCounters()
/* 538:    */     {
/* 539:541 */       return this.totalCounters;
/* 540:    */     }
/* 541:    */     
/* 542:    */     public Counters getMapCounters()
/* 543:    */     {
/* 544:543 */       return this.mapCounters;
/* 545:    */     }
/* 546:    */     
/* 547:    */     public Counters getReduceCounters()
/* 548:    */     {
/* 549:545 */       return this.reduceCounters;
/* 550:    */     }
/* 551:    */     
/* 552:    */     public Map<TaskID, JobHistoryParser.TaskInfo> getAllTasks()
/* 553:    */     {
/* 554:547 */       return this.tasksMap;
/* 555:    */     }
/* 556:    */     
/* 557:    */     public Map<TaskAttemptID, JobHistoryParser.TaskAttemptInfo> getAllCompletedTaskAttempts()
/* 558:    */     {
/* 559:549 */       return this.completedTaskAttemptsMap;
/* 560:    */     }
/* 561:    */     
/* 562:    */     public String getPriority()
/* 563:    */     {
/* 564:551 */       return this.priority.toString();
/* 565:    */     }
/* 566:    */     
/* 567:    */     public Map<JobACL, AccessControlList> getJobACLs()
/* 568:    */     {
/* 569:552 */       return this.jobACLs;
/* 570:    */     }
/* 571:    */     
/* 572:    */     public boolean getUberized()
/* 573:    */     {
/* 574:554 */       return this.uberized;
/* 575:    */     }
/* 576:    */     
/* 577:    */     public List<JobHistoryParser.AMInfo> getAMInfos()
/* 578:    */     {
/* 579:556 */       return this.amInfos;
/* 580:    */     }
/* 581:    */     
/* 582:    */     public JobHistoryParser.AMInfo getLatestAMInfo()
/* 583:    */     {
/* 584:558 */       return this.latestAmInfo;
/* 585:    */     }
/* 586:    */   }
/* 587:    */   
/* 588:    */   public static class TaskInfo
/* 589:    */   {
/* 590:    */     TaskID taskId;
/* 591:    */     long startTime;
/* 592:    */     long finishTime;
/* 593:    */     TaskType taskType;
/* 594:    */     String splitLocations;
/* 595:    */     Counters counters;
/* 596:    */     String status;
/* 597:    */     String error;
/* 598:    */     TaskAttemptID failedDueToAttemptId;
/* 599:    */     TaskAttemptID successfulAttemptId;
/* 600:    */     Map<TaskAttemptID, JobHistoryParser.TaskAttemptInfo> attemptsMap;
/* 601:    */     
/* 602:    */     public TaskInfo()
/* 603:    */     {
/* 604:578 */       this.startTime = (this.finishTime = -1L);
/* 605:579 */       this.error = (this.splitLocations = "");
/* 606:580 */       this.attemptsMap = new HashMap();
/* 607:    */     }
/* 608:    */     
/* 609:    */     public void printAll()
/* 610:    */     {
/* 611:584 */       System.out.println("TASK_ID:" + this.taskId.toString());
/* 612:585 */       System.out.println("START_TIME: " + this.startTime);
/* 613:586 */       System.out.println("FINISH_TIME:" + this.finishTime);
/* 614:587 */       System.out.println("TASK_TYPE:" + this.taskType);
/* 615:588 */       if (this.counters != null) {
/* 616:589 */         System.out.println("COUNTERS:" + this.counters.toString());
/* 617:    */       }
/* 618:592 */       for (JobHistoryParser.TaskAttemptInfo tinfo : this.attemptsMap.values()) {
/* 619:593 */         tinfo.printAll();
/* 620:    */       }
/* 621:    */     }
/* 622:    */     
/* 623:    */     public TaskID getTaskId()
/* 624:    */     {
/* 625:598 */       return this.taskId;
/* 626:    */     }
/* 627:    */     
/* 628:    */     public long getStartTime()
/* 629:    */     {
/* 630:600 */       return this.startTime;
/* 631:    */     }
/* 632:    */     
/* 633:    */     public long getFinishTime()
/* 634:    */     {
/* 635:602 */       return this.finishTime;
/* 636:    */     }
/* 637:    */     
/* 638:    */     public TaskType getTaskType()
/* 639:    */     {
/* 640:604 */       return this.taskType;
/* 641:    */     }
/* 642:    */     
/* 643:    */     public String getSplitLocations()
/* 644:    */     {
/* 645:606 */       return this.splitLocations;
/* 646:    */     }
/* 647:    */     
/* 648:    */     public Counters getCounters()
/* 649:    */     {
/* 650:608 */       return this.counters;
/* 651:    */     }
/* 652:    */     
/* 653:    */     public String getTaskStatus()
/* 654:    */     {
/* 655:610 */       return this.status;
/* 656:    */     }
/* 657:    */     
/* 658:    */     public TaskAttemptID getFailedDueToAttemptId()
/* 659:    */     {
/* 660:613 */       return this.failedDueToAttemptId;
/* 661:    */     }
/* 662:    */     
/* 663:    */     public TaskAttemptID getSuccessfulAttemptId()
/* 664:    */     {
/* 665:617 */       return this.successfulAttemptId;
/* 666:    */     }
/* 667:    */     
/* 668:    */     public String getError()
/* 669:    */     {
/* 670:620 */       return this.error;
/* 671:    */     }
/* 672:    */     
/* 673:    */     public Map<TaskAttemptID, JobHistoryParser.TaskAttemptInfo> getAllTaskAttempts()
/* 674:    */     {
/* 675:623 */       return this.attemptsMap;
/* 676:    */     }
/* 677:    */   }
/* 678:    */   
/* 679:    */   public static class TaskAttemptInfo
/* 680:    */   {
/* 681:    */     TaskAttemptID attemptId;
/* 682:    */     long startTime;
/* 683:    */     long finishTime;
/* 684:    */     long shuffleFinishTime;
/* 685:    */     long sortFinishTime;
/* 686:    */     long mapFinishTime;
/* 687:    */     String error;
/* 688:    */     String status;
/* 689:    */     String state;
/* 690:    */     TaskType taskType;
/* 691:    */     String trackerName;
/* 692:    */     Counters counters;
/* 693:    */     int httpPort;
/* 694:    */     int shufflePort;
/* 695:    */     String hostname;
/* 696:    */     int port;
/* 697:    */     String rackname;
/* 698:    */     ContainerId containerId;
/* 699:    */     
/* 700:    */     public TaskAttemptInfo()
/* 701:    */     {
/* 702:654 */       this.startTime = (this.finishTime = this.shuffleFinishTime = this.sortFinishTime = this.mapFinishTime = -1L);
/* 703:    */       
/* 704:656 */       this.error = (this.state = this.trackerName = this.hostname = this.rackname = "");
/* 705:657 */       this.port = -1;
/* 706:658 */       this.httpPort = -1;
/* 707:659 */       this.shufflePort = -1;
/* 708:    */     }
/* 709:    */     
/* 710:    */     public void printAll()
/* 711:    */     {
/* 712:665 */       System.out.println("ATTEMPT_ID:" + this.attemptId.toString());
/* 713:666 */       System.out.println("START_TIME: " + this.startTime);
/* 714:667 */       System.out.println("FINISH_TIME:" + this.finishTime);
/* 715:668 */       System.out.println("ERROR:" + this.error);
/* 716:669 */       System.out.println("TASK_STATUS:" + this.status);
/* 717:670 */       System.out.println("STATE:" + this.state);
/* 718:671 */       System.out.println("TASK_TYPE:" + this.taskType);
/* 719:672 */       System.out.println("TRACKER_NAME:" + this.trackerName);
/* 720:673 */       System.out.println("HTTP_PORT:" + this.httpPort);
/* 721:674 */       System.out.println("SHUFFLE_PORT:" + this.shufflePort);
/* 722:675 */       System.out.println("CONTIANER_ID:" + this.containerId);
/* 723:676 */       if (this.counters != null) {
/* 724:677 */         System.out.println("COUNTERS:" + this.counters.toString());
/* 725:    */       }
/* 726:    */     }
/* 727:    */     
/* 728:    */     public TaskAttemptID getAttemptId()
/* 729:    */     {
/* 730:682 */       return this.attemptId;
/* 731:    */     }
/* 732:    */     
/* 733:    */     public long getStartTime()
/* 734:    */     {
/* 735:684 */       return this.startTime;
/* 736:    */     }
/* 737:    */     
/* 738:    */     public long getFinishTime()
/* 739:    */     {
/* 740:686 */       return this.finishTime;
/* 741:    */     }
/* 742:    */     
/* 743:    */     public long getShuffleFinishTime()
/* 744:    */     {
/* 745:688 */       return this.shuffleFinishTime;
/* 746:    */     }
/* 747:    */     
/* 748:    */     public long getSortFinishTime()
/* 749:    */     {
/* 750:690 */       return this.sortFinishTime;
/* 751:    */     }
/* 752:    */     
/* 753:    */     public long getMapFinishTime()
/* 754:    */     {
/* 755:692 */       return this.mapFinishTime;
/* 756:    */     }
/* 757:    */     
/* 758:    */     public String getError()
/* 759:    */     {
/* 760:694 */       return this.error;
/* 761:    */     }
/* 762:    */     
/* 763:    */     public String getState()
/* 764:    */     {
/* 765:696 */       return this.state;
/* 766:    */     }
/* 767:    */     
/* 768:    */     public String getTaskStatus()
/* 769:    */     {
/* 770:698 */       return this.status;
/* 771:    */     }
/* 772:    */     
/* 773:    */     public TaskType getTaskType()
/* 774:    */     {
/* 775:700 */       return this.taskType;
/* 776:    */     }
/* 777:    */     
/* 778:    */     public String getTrackerName()
/* 779:    */     {
/* 780:702 */       return this.trackerName;
/* 781:    */     }
/* 782:    */     
/* 783:    */     public String getHostname()
/* 784:    */     {
/* 785:704 */       return this.hostname;
/* 786:    */     }
/* 787:    */     
/* 788:    */     public int getPort()
/* 789:    */     {
/* 790:706 */       return this.port;
/* 791:    */     }
/* 792:    */     
/* 793:    */     public String getRackname()
/* 794:    */     {
/* 795:708 */       return this.rackname;
/* 796:    */     }
/* 797:    */     
/* 798:    */     public Counters getCounters()
/* 799:    */     {
/* 800:710 */       return this.counters;
/* 801:    */     }
/* 802:    */     
/* 803:    */     public int getHttpPort()
/* 804:    */     {
/* 805:712 */       return this.httpPort;
/* 806:    */     }
/* 807:    */     
/* 808:    */     public int getShufflePort()
/* 809:    */     {
/* 810:714 */       return this.shufflePort;
/* 811:    */     }
/* 812:    */     
/* 813:    */     public ContainerId getContainerId()
/* 814:    */     {
/* 815:716 */       return this.containerId;
/* 816:    */     }
/* 817:    */   }
/* 818:    */   
/* 819:    */   public static class AMInfo
/* 820:    */   {
/* 821:    */     ApplicationAttemptId appAttemptId;
/* 822:    */     long startTime;
/* 823:    */     ContainerId containerId;
/* 824:    */     String nodeManagerHost;
/* 825:    */     int nodeManagerPort;
/* 826:    */     int nodeManagerHttpPort;
/* 827:    */     
/* 828:    */     public AMInfo()
/* 829:    */     {
/* 830:735 */       this.startTime = -1L;
/* 831:736 */       this.nodeManagerHost = "";
/* 832:737 */       this.nodeManagerHttpPort = -1;
/* 833:    */     }
/* 834:    */     
/* 835:    */     public AMInfo(ApplicationAttemptId appAttemptId, long startTime, ContainerId containerId, String nodeManagerHost, int nodeManagerPort, int nodeManagerHttpPort)
/* 836:    */     {
/* 837:743 */       this.appAttemptId = appAttemptId;
/* 838:744 */       this.startTime = startTime;
/* 839:745 */       this.containerId = containerId;
/* 840:746 */       this.nodeManagerHost = nodeManagerHost;
/* 841:747 */       this.nodeManagerPort = nodeManagerPort;
/* 842:748 */       this.nodeManagerHttpPort = nodeManagerHttpPort;
/* 843:    */     }
/* 844:    */     
/* 845:    */     public void printAll()
/* 846:    */     {
/* 847:755 */       System.out.println("APPLICATION_ATTEMPT_ID:" + this.appAttemptId.toString());
/* 848:756 */       System.out.println("START_TIME: " + this.startTime);
/* 849:757 */       System.out.println("CONTAINER_ID: " + this.containerId.toString());
/* 850:758 */       System.out.println("NODE_MANAGER_HOST: " + this.nodeManagerHost);
/* 851:759 */       System.out.println("NODE_MANAGER_PORT: " + this.nodeManagerPort);
/* 852:760 */       System.out.println("NODE_MANAGER_HTTP_PORT: " + this.nodeManagerHttpPort);
/* 853:    */     }
/* 854:    */     
/* 855:    */     public ApplicationAttemptId getAppAttemptId()
/* 856:    */     {
/* 857:765 */       return this.appAttemptId;
/* 858:    */     }
/* 859:    */     
/* 860:    */     public long getStartTime()
/* 861:    */     {
/* 862:770 */       return this.startTime;
/* 863:    */     }
/* 864:    */     
/* 865:    */     public ContainerId getContainerId()
/* 866:    */     {
/* 867:775 */       return this.containerId;
/* 868:    */     }
/* 869:    */     
/* 870:    */     public String getNodeManagerHost()
/* 871:    */     {
/* 872:780 */       return this.nodeManagerHost;
/* 873:    */     }
/* 874:    */     
/* 875:    */     public int getNodeManagerPort()
/* 876:    */     {
/* 877:785 */       return this.nodeManagerPort;
/* 878:    */     }
/* 879:    */     
/* 880:    */     public int getNodeManagerHttpPort()
/* 881:    */     {
/* 882:790 */       return this.nodeManagerHttpPort;
/* 883:    */     }
/* 884:    */   }
/* 885:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.jobhistory.JobHistoryParser
 * JD-Core Version:    0.7.0.1
 */