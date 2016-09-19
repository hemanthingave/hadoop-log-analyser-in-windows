/*   1:    */ package org.apache.hadoop.mapreduce.tools;
/*   2:    */ 
/*   3:    */ import com.google.common.base.Charsets;
/*   4:    */ import java.io.IOException;
/*   5:    */ import java.io.OutputStreamWriter;
/*   6:    */ import java.io.PrintStream;
/*   7:    */ import java.io.PrintWriter;
/*   8:    */ import java.util.ArrayList;
/*   9:    */ import java.util.Arrays;
/*  10:    */ import java.util.HashSet;
/*  11:    */ import java.util.List;
/*  12:    */ import java.util.Set;
/*  13:    */ import org.apache.commons.lang.StringUtils;
/*  14:    */ import org.apache.commons.logging.Log;
/*  15:    */ import org.apache.commons.logging.LogFactory;
/*  16:    */ import org.apache.hadoop.classification.InterfaceAudience.Private;
/*  17:    */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*  18:    */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*  19:    */ import org.apache.hadoop.conf.Configuration;
/*  20:    */ import org.apache.hadoop.conf.Configured;
/*  21:    */ import org.apache.hadoop.ipc.RemoteException;
/*  22:    */ import org.apache.hadoop.mapred.JobConf;
/*  23:    */ import org.apache.hadoop.mapred.TIPStatus;
/*  24:    */ import org.apache.hadoop.mapreduce.Cluster;
/*  25:    */ import org.apache.hadoop.mapreduce.Counter;
/*  26:    */ import org.apache.hadoop.mapreduce.Counters;
/*  27:    */ import org.apache.hadoop.mapreduce.Job;
/*  28:    */ import org.apache.hadoop.mapreduce.JobID;
/*  29:    */ import org.apache.hadoop.mapreduce.JobPriority;
/*  30:    */ import org.apache.hadoop.mapreduce.JobStatus;
/*  31:    */ import org.apache.hadoop.mapreduce.TaskAttemptID;
/*  32:    */ import org.apache.hadoop.mapreduce.TaskCompletionEvent;
/*  33:    */ import org.apache.hadoop.mapreduce.TaskReport;
/*  34:    */ import org.apache.hadoop.mapreduce.TaskTrackerInfo;
/*  35:    */ import org.apache.hadoop.mapreduce.TaskType;
/*  36:    */ import org.apache.hadoop.mapreduce.jobhistory.HistoryViewer;
/*  37:    */ import org.apache.hadoop.mapreduce.v2.LogParams;
/*  38:    */ import org.apache.hadoop.security.AccessControlException;
/*  39:    */ import org.apache.hadoop.util.ExitUtil;
/*  40:    */ import org.apache.hadoop.util.Tool;
/*  41:    */ import org.apache.hadoop.util.ToolRunner;
/*  42:    */ import org.apache.hadoop.yarn.logaggregation.LogCLIHelpers;
/*  43:    */ 
/*  44:    */ @InterfaceAudience.Public
/*  45:    */ @InterfaceStability.Stable
/*  46:    */ public class CLI
/*  47:    */   extends Configured
/*  48:    */   implements Tool
/*  49:    */ {
/*  50: 67 */   private static final Log LOG = LogFactory.getLog(CLI.class);
/*  51:    */   protected Cluster cluster;
/*  52: 69 */   private static final Set<String> taskTypes = new HashSet(Arrays.asList(new String[] { "MAP", "REDUCE" }));
/*  53: 71 */   private final Set<String> taskStates = new HashSet(Arrays.asList(new String[] { "running", "completed", "pending", "failed", "killed" }));
/*  54:    */   
/*  55:    */   public CLI() {}
/*  56:    */   
/*  57:    */   public CLI(Configuration conf)
/*  58:    */   {
/*  59: 78 */     setConf(conf);
/*  60:    */   }
/*  61:    */   
/*  62:    */   public int run(String[] argv)
/*  63:    */     throws Exception
/*  64:    */   {
/*  65: 82 */     int exitCode = -1;
/*  66: 83 */     if (argv.length < 1)
/*  67:    */     {
/*  68: 84 */       displayUsage("");
/*  69: 85 */       return exitCode;
/*  70:    */     }
/*  71: 88 */     String cmd = argv[0];
/*  72: 89 */     String submitJobFile = null;
/*  73: 90 */     String jobid = null;
/*  74: 91 */     String taskid = null;
/*  75: 92 */     String historyFile = null;
/*  76: 93 */     String counterGroupName = null;
/*  77: 94 */     String counterName = null;
/*  78: 95 */     JobPriority jp = null;
/*  79: 96 */     String taskType = null;
/*  80: 97 */     String taskState = null;
/*  81: 98 */     int fromEvent = 0;
/*  82: 99 */     int nEvents = 0;
/*  83:100 */     boolean getStatus = false;
/*  84:101 */     boolean getCounter = false;
/*  85:102 */     boolean killJob = false;
/*  86:103 */     boolean listEvents = false;
/*  87:104 */     boolean viewHistory = false;
/*  88:105 */     boolean viewAllHistory = false;
/*  89:106 */     boolean listJobs = false;
/*  90:107 */     boolean listAllJobs = false;
/*  91:108 */     boolean listActiveTrackers = false;
/*  92:109 */     boolean listBlacklistedTrackers = false;
/*  93:110 */     boolean displayTasks = false;
/*  94:111 */     boolean killTask = false;
/*  95:112 */     boolean failTask = false;
/*  96:113 */     boolean setJobPriority = false;
/*  97:114 */     boolean logs = false;
/*  98:116 */     if ("-submit".equals(cmd))
/*  99:    */     {
/* 100:117 */       if (argv.length != 2)
/* 101:    */       {
/* 102:118 */         displayUsage(cmd);
/* 103:119 */         return exitCode;
/* 104:    */       }
/* 105:121 */       submitJobFile = argv[1];
/* 106:    */     }
/* 107:122 */     else if ("-status".equals(cmd))
/* 108:    */     {
/* 109:123 */       if (argv.length != 2)
/* 110:    */       {
/* 111:124 */         displayUsage(cmd);
/* 112:125 */         return exitCode;
/* 113:    */       }
/* 114:127 */       jobid = argv[1];
/* 115:128 */       getStatus = true;
/* 116:    */     }
/* 117:129 */     else if ("-counter".equals(cmd))
/* 118:    */     {
/* 119:130 */       if (argv.length != 4)
/* 120:    */       {
/* 121:131 */         displayUsage(cmd);
/* 122:132 */         return exitCode;
/* 123:    */       }
/* 124:134 */       getCounter = true;
/* 125:135 */       jobid = argv[1];
/* 126:136 */       counterGroupName = argv[2];
/* 127:137 */       counterName = argv[3];
/* 128:    */     }
/* 129:138 */     else if ("-kill".equals(cmd))
/* 130:    */     {
/* 131:139 */       if (argv.length != 2)
/* 132:    */       {
/* 133:140 */         displayUsage(cmd);
/* 134:141 */         return exitCode;
/* 135:    */       }
/* 136:143 */       jobid = argv[1];
/* 137:144 */       killJob = true;
/* 138:    */     }
/* 139:145 */     else if ("-set-priority".equals(cmd))
/* 140:    */     {
/* 141:146 */       if (argv.length != 3)
/* 142:    */       {
/* 143:147 */         displayUsage(cmd);
/* 144:148 */         return exitCode;
/* 145:    */       }
/* 146:150 */       jobid = argv[1];
/* 147:    */       try
/* 148:    */       {
/* 149:152 */         jp = JobPriority.valueOf(argv[2]);
/* 150:    */       }
/* 151:    */       catch (IllegalArgumentException iae)
/* 152:    */       {
/* 153:154 */         LOG.info(iae);
/* 154:155 */         displayUsage(cmd);
/* 155:156 */         return exitCode;
/* 156:    */       }
/* 157:158 */       setJobPriority = true;
/* 158:    */     }
/* 159:159 */     else if ("-events".equals(cmd))
/* 160:    */     {
/* 161:160 */       if (argv.length != 4)
/* 162:    */       {
/* 163:161 */         displayUsage(cmd);
/* 164:162 */         return exitCode;
/* 165:    */       }
/* 166:164 */       jobid = argv[1];
/* 167:165 */       fromEvent = Integer.parseInt(argv[2]);
/* 168:166 */       nEvents = Integer.parseInt(argv[3]);
/* 169:167 */       listEvents = true;
/* 170:    */     }
/* 171:168 */     else if ("-history".equals(cmd))
/* 172:    */     {
/* 173:169 */       if ((argv.length != 2) && ((argv.length != 3) || (!"all".equals(argv[1]))))
/* 174:    */       {
/* 175:170 */         displayUsage(cmd);
/* 176:171 */         return exitCode;
/* 177:    */       }
/* 178:173 */       viewHistory = true;
/* 179:174 */       if ((argv.length == 3) && ("all".equals(argv[1])))
/* 180:    */       {
/* 181:175 */         viewAllHistory = true;
/* 182:176 */         historyFile = argv[2];
/* 183:    */       }
/* 184:    */       else
/* 185:    */       {
/* 186:178 */         historyFile = argv[1];
/* 187:    */       }
/* 188:    */     }
/* 189:180 */     else if ("-list".equals(cmd))
/* 190:    */     {
/* 191:181 */       if ((argv.length != 1) && ((argv.length != 2) || (!"all".equals(argv[1]))))
/* 192:    */       {
/* 193:182 */         displayUsage(cmd);
/* 194:183 */         return exitCode;
/* 195:    */       }
/* 196:185 */       if ((argv.length == 2) && ("all".equals(argv[1]))) {
/* 197:186 */         listAllJobs = true;
/* 198:    */       } else {
/* 199:188 */         listJobs = true;
/* 200:    */       }
/* 201:    */     }
/* 202:190 */     else if ("-kill-task".equals(cmd))
/* 203:    */     {
/* 204:191 */       if (argv.length != 2)
/* 205:    */       {
/* 206:192 */         displayUsage(cmd);
/* 207:193 */         return exitCode;
/* 208:    */       }
/* 209:195 */       killTask = true;
/* 210:196 */       taskid = argv[1];
/* 211:    */     }
/* 212:197 */     else if ("-fail-task".equals(cmd))
/* 213:    */     {
/* 214:198 */       if (argv.length != 2)
/* 215:    */       {
/* 216:199 */         displayUsage(cmd);
/* 217:200 */         return exitCode;
/* 218:    */       }
/* 219:202 */       failTask = true;
/* 220:203 */       taskid = argv[1];
/* 221:    */     }
/* 222:204 */     else if ("-list-active-trackers".equals(cmd))
/* 223:    */     {
/* 224:205 */       if (argv.length != 1)
/* 225:    */       {
/* 226:206 */         displayUsage(cmd);
/* 227:207 */         return exitCode;
/* 228:    */       }
/* 229:209 */       listActiveTrackers = true;
/* 230:    */     }
/* 231:210 */     else if ("-list-blacklisted-trackers".equals(cmd))
/* 232:    */     {
/* 233:211 */       if (argv.length != 1)
/* 234:    */       {
/* 235:212 */         displayUsage(cmd);
/* 236:213 */         return exitCode;
/* 237:    */       }
/* 238:215 */       listBlacklistedTrackers = true;
/* 239:    */     }
/* 240:216 */     else if ("-list-attempt-ids".equals(cmd))
/* 241:    */     {
/* 242:217 */       if (argv.length != 4)
/* 243:    */       {
/* 244:218 */         displayUsage(cmd);
/* 245:219 */         return exitCode;
/* 246:    */       }
/* 247:221 */       jobid = argv[1];
/* 248:222 */       taskType = argv[2];
/* 249:223 */       taskState = argv[3];
/* 250:224 */       displayTasks = true;
/* 251:225 */       if (!taskTypes.contains(taskType.toUpperCase()))
/* 252:    */       {
/* 253:226 */         System.out.println("Error: Invalid task-type: " + taskType);
/* 254:227 */         displayUsage(cmd);
/* 255:228 */         return exitCode;
/* 256:    */       }
/* 257:230 */       if (!this.taskStates.contains(taskState.toLowerCase()))
/* 258:    */       {
/* 259:231 */         System.out.println("Error: Invalid task-state: " + taskState);
/* 260:232 */         displayUsage(cmd);
/* 261:233 */         return exitCode;
/* 262:    */       }
/* 263:    */     }
/* 264:235 */     else if ("-logs".equals(cmd))
/* 265:    */     {
/* 266:236 */       if ((argv.length == 2) || (argv.length == 3))
/* 267:    */       {
/* 268:237 */         logs = true;
/* 269:238 */         jobid = argv[1];
/* 270:239 */         if (argv.length == 3) {
/* 271:240 */           taskid = argv[2];
/* 272:    */         } else {
/* 273:242 */           taskid = null;
/* 274:    */         }
/* 275:    */       }
/* 276:    */       else
/* 277:    */       {
/* 278:245 */         displayUsage(cmd);
/* 279:246 */         return exitCode;
/* 280:    */       }
/* 281:    */     }
/* 282:    */     else
/* 283:    */     {
/* 284:249 */       displayUsage(cmd);
/* 285:250 */       return exitCode;
/* 286:    */     }
/* 287:254 */     this.cluster = createCluster();
/* 288:    */     try
/* 289:    */     {
/* 290:258 */       if (submitJobFile != null)
/* 291:    */       {
/* 292:259 */         Job job = Job.getInstance(new JobConf(submitJobFile));
/* 293:260 */         job.submit();
/* 294:261 */         System.out.println("Created job " + job.getJobID());
/* 295:262 */         exitCode = 0;
/* 296:    */       }
/* 297:263 */       else if (getStatus)
/* 298:    */       {
/* 299:264 */         Job job = this.cluster.getJob(JobID.forName(jobid));
/* 300:265 */         if (job == null)
/* 301:    */         {
/* 302:266 */           System.out.println("Could not find job " + jobid);
/* 303:    */         }
/* 304:    */         else
/* 305:    */         {
/* 306:268 */           Counters counters = job.getCounters();
/* 307:269 */           System.out.println();
/* 308:270 */           System.out.println(job);
/* 309:271 */           if (counters != null) {
/* 310:272 */             System.out.println(counters);
/* 311:    */           } else {
/* 312:274 */             System.out.println("Counters not available. Job is retired.");
/* 313:    */           }
/* 314:276 */           exitCode = 0;
/* 315:    */         }
/* 316:    */       }
/* 317:278 */       else if (getCounter)
/* 318:    */       {
/* 319:279 */         Job job = this.cluster.getJob(JobID.forName(jobid));
/* 320:280 */         if (job == null)
/* 321:    */         {
/* 322:281 */           System.out.println("Could not find job " + jobid);
/* 323:    */         }
/* 324:    */         else
/* 325:    */         {
/* 326:283 */           Counters counters = job.getCounters();
/* 327:284 */           if (counters == null)
/* 328:    */           {
/* 329:285 */             System.out.println("Counters not available for retired job " + jobid);
/* 330:    */             
/* 331:287 */             exitCode = -1;
/* 332:    */           }
/* 333:    */           else
/* 334:    */           {
/* 335:289 */             System.out.println(getCounter(counters, counterGroupName, counterName));
/* 336:    */             
/* 337:291 */             exitCode = 0;
/* 338:    */           }
/* 339:    */         }
/* 340:    */       }
/* 341:294 */       else if (killJob)
/* 342:    */       {
/* 343:295 */         Job job = this.cluster.getJob(JobID.forName(jobid));
/* 344:296 */         if (job == null)
/* 345:    */         {
/* 346:297 */           System.out.println("Could not find job " + jobid);
/* 347:    */         }
/* 348:    */         else
/* 349:    */         {
/* 350:299 */           job.killJob();
/* 351:300 */           System.out.println("Killed job " + jobid);
/* 352:301 */           exitCode = 0;
/* 353:    */         }
/* 354:    */       }
/* 355:303 */       else if (setJobPriority)
/* 356:    */       {
/* 357:304 */         Job job = this.cluster.getJob(JobID.forName(jobid));
/* 358:305 */         if (job == null)
/* 359:    */         {
/* 360:306 */           System.out.println("Could not find job " + jobid);
/* 361:    */         }
/* 362:    */         else
/* 363:    */         {
/* 364:308 */           job.setPriority(jp);
/* 365:309 */           System.out.println("Changed job priority.");
/* 366:310 */           exitCode = 0;
/* 367:    */         }
/* 368:    */       }
/* 369:312 */       else if (viewHistory)
/* 370:    */       {
/* 371:313 */         viewHistory(historyFile, viewAllHistory);
/* 372:314 */         exitCode = 0;
/* 373:    */       }
/* 374:315 */       else if (listEvents)
/* 375:    */       {
/* 376:316 */         listEvents(this.cluster.getJob(JobID.forName(jobid)), fromEvent, nEvents);
/* 377:317 */         exitCode = 0;
/* 378:    */       }
/* 379:318 */       else if (listJobs)
/* 380:    */       {
/* 381:319 */         listJobs(this.cluster);
/* 382:320 */         exitCode = 0;
/* 383:    */       }
/* 384:321 */       else if (listAllJobs)
/* 385:    */       {
/* 386:322 */         listAllJobs(this.cluster);
/* 387:323 */         exitCode = 0;
/* 388:    */       }
/* 389:324 */       else if (listActiveTrackers)
/* 390:    */       {
/* 391:325 */         listActiveTrackers(this.cluster);
/* 392:326 */         exitCode = 0;
/* 393:    */       }
/* 394:327 */       else if (listBlacklistedTrackers)
/* 395:    */       {
/* 396:328 */         listBlacklistedTrackers(this.cluster);
/* 397:329 */         exitCode = 0;
/* 398:    */       }
/* 399:330 */       else if (displayTasks)
/* 400:    */       {
/* 401:331 */         displayTasks(this.cluster.getJob(JobID.forName(jobid)), taskType, taskState);
/* 402:332 */         exitCode = 0;
/* 403:    */       }
/* 404:333 */       else if (killTask)
/* 405:    */       {
/* 406:334 */         TaskAttemptID taskID = TaskAttemptID.forName(taskid);
/* 407:335 */         Job job = this.cluster.getJob(taskID.getJobID());
/* 408:336 */         if (job == null)
/* 409:    */         {
/* 410:337 */           System.out.println("Could not find job " + jobid);
/* 411:    */         }
/* 412:338 */         else if (job.killTask(taskID, false))
/* 413:    */         {
/* 414:339 */           System.out.println("Killed task " + taskid);
/* 415:340 */           exitCode = 0;
/* 416:    */         }
/* 417:    */         else
/* 418:    */         {
/* 419:342 */           System.out.println("Could not kill task " + taskid);
/* 420:343 */           exitCode = -1;
/* 421:    */         }
/* 422:    */       }
/* 423:345 */       else if (failTask)
/* 424:    */       {
/* 425:346 */         TaskAttemptID taskID = TaskAttemptID.forName(taskid);
/* 426:347 */         Job job = this.cluster.getJob(taskID.getJobID());
/* 427:348 */         if (job == null)
/* 428:    */         {
/* 429:349 */           System.out.println("Could not find job " + jobid);
/* 430:    */         }
/* 431:350 */         else if (job.killTask(taskID, true))
/* 432:    */         {
/* 433:351 */           System.out.println("Killed task " + taskID + " by failing it");
/* 434:352 */           exitCode = 0;
/* 435:    */         }
/* 436:    */         else
/* 437:    */         {
/* 438:354 */           System.out.println("Could not fail task " + taskid);
/* 439:355 */           exitCode = -1;
/* 440:    */         }
/* 441:    */       }
/* 442:357 */       else if (logs)
/* 443:    */       {
/* 444:    */         try
/* 445:    */         {
/* 446:359 */           JobID jobID = JobID.forName(jobid);
/* 447:360 */           TaskAttemptID taskAttemptID = TaskAttemptID.forName(taskid);
/* 448:361 */           LogParams logParams = this.cluster.getLogParams(jobID, taskAttemptID);
/* 449:362 */           LogCLIHelpers logDumper = new LogCLIHelpers();
/* 450:363 */           logDumper.setConf(getConf());
/* 451:364 */           exitCode = logDumper.dumpAContainersLogs(logParams.getApplicationId(), logParams.getContainerId(), logParams.getNodeId(), logParams.getOwner());
/* 452:    */         }
/* 453:    */         catch (IOException e)
/* 454:    */         {
/* 455:368 */           if ((e instanceof RemoteException)) {
/* 456:369 */             throw e;
/* 457:    */           }
/* 458:371 */           System.out.println(e.getMessage());
/* 459:    */         }
/* 460:    */       }
/* 461:    */     }
/* 462:    */     catch (RemoteException re)
/* 463:    */     {
/* 464:375 */       IOException unwrappedException = re.unwrapRemoteException();
/* 465:376 */       if ((unwrappedException instanceof AccessControlException)) {
/* 466:377 */         System.out.println(unwrappedException.getMessage());
/* 467:    */       } else {
/* 468:379 */         throw re;
/* 469:    */       }
/* 470:    */     }
/* 471:    */     finally
/* 472:    */     {
/* 473:382 */       this.cluster.close();
/* 474:    */     }
/* 475:384 */     return exitCode;
/* 476:    */   }
/* 477:    */   
/* 478:    */   Cluster createCluster()
/* 479:    */     throws IOException
/* 480:    */   {
/* 481:388 */     return new Cluster(getConf());
/* 482:    */   }
/* 483:    */   
/* 484:    */   private String getJobPriorityNames()
/* 485:    */   {
/* 486:392 */     StringBuffer sb = new StringBuffer();
/* 487:393 */     for (JobPriority p : JobPriority.values()) {
/* 488:394 */       sb.append(p.name()).append(" ");
/* 489:    */     }
/* 490:396 */     return sb.substring(0, sb.length() - 1);
/* 491:    */   }
/* 492:    */   
/* 493:    */   private String getTaskTypes()
/* 494:    */   {
/* 495:400 */     return StringUtils.join(taskTypes, " ");
/* 496:    */   }
/* 497:    */   
/* 498:    */   private void displayUsage(String cmd)
/* 499:    */   {
/* 500:407 */     String prefix = "Usage: CLI ";
/* 501:408 */     String jobPriorityValues = getJobPriorityNames();
/* 502:409 */     String taskStates = "running, completed";
/* 503:411 */     if ("-submit".equals(cmd))
/* 504:    */     {
/* 505:412 */       System.err.println(prefix + "[" + cmd + " <job-file>]");
/* 506:    */     }
/* 507:413 */     else if (("-status".equals(cmd)) || ("-kill".equals(cmd)))
/* 508:    */     {
/* 509:414 */       System.err.println(prefix + "[" + cmd + " <job-id>]");
/* 510:    */     }
/* 511:415 */     else if ("-counter".equals(cmd))
/* 512:    */     {
/* 513:416 */       System.err.println(prefix + "[" + cmd + " <job-id> <group-name> <counter-name>]");
/* 514:    */     }
/* 515:418 */     else if ("-events".equals(cmd))
/* 516:    */     {
/* 517:419 */       System.err.println(prefix + "[" + cmd + " <job-id> <from-event-#> <#-of-events>]. Event #s start from 1.");
/* 518:    */     }
/* 519:421 */     else if ("-history".equals(cmd))
/* 520:    */     {
/* 521:422 */       System.err.println(prefix + "[" + cmd + " <jobHistoryFile>]");
/* 522:    */     }
/* 523:423 */     else if ("-list".equals(cmd))
/* 524:    */     {
/* 525:424 */       System.err.println(prefix + "[" + cmd + " [all]]");
/* 526:    */     }
/* 527:425 */     else if (("-kill-task".equals(cmd)) || ("-fail-task".equals(cmd)))
/* 528:    */     {
/* 529:426 */       System.err.println(prefix + "[" + cmd + " <task-attempt-id>]");
/* 530:    */     }
/* 531:427 */     else if ("-set-priority".equals(cmd))
/* 532:    */     {
/* 533:428 */       System.err.println(prefix + "[" + cmd + " <job-id> <priority>]. " + "Valid values for priorities are: " + jobPriorityValues);
/* 534:    */     }
/* 535:431 */     else if ("-list-active-trackers".equals(cmd))
/* 536:    */     {
/* 537:432 */       System.err.println(prefix + "[" + cmd + "]");
/* 538:    */     }
/* 539:433 */     else if ("-list-blacklisted-trackers".equals(cmd))
/* 540:    */     {
/* 541:434 */       System.err.println(prefix + "[" + cmd + "]");
/* 542:    */     }
/* 543:435 */     else if ("-list-attempt-ids".equals(cmd))
/* 544:    */     {
/* 545:436 */       System.err.println(prefix + "[" + cmd + " <job-id> <task-type> <task-state>]. " + "Valid values for <task-type> are " + getTaskTypes() + ". " + "Valid values for <task-state> are " + taskStates);
/* 546:    */     }
/* 547:440 */     else if ("-logs".equals(cmd))
/* 548:    */     {
/* 549:441 */       System.err.println(prefix + "[" + cmd + " <job-id> <task-attempt-id>]. " + " <task-attempt-id> is optional to get task attempt logs.");
/* 550:    */     }
/* 551:    */     else
/* 552:    */     {
/* 553:445 */       System.err.printf(prefix + "<command> <args>%n", new Object[0]);
/* 554:446 */       System.err.printf("\t[-submit <job-file>]%n", new Object[0]);
/* 555:447 */       System.err.printf("\t[-status <job-id>]%n", new Object[0]);
/* 556:448 */       System.err.printf("\t[-counter <job-id> <group-name> <counter-name>]%n", new Object[0]);
/* 557:449 */       System.err.printf("\t[-kill <job-id>]%n", new Object[0]);
/* 558:450 */       System.err.printf("\t[-set-priority <job-id> <priority>]. Valid values for priorities are: " + jobPriorityValues + "%n", new Object[0]);
/* 559:    */       
/* 560:452 */       System.err.printf("\t[-events <job-id> <from-event-#> <#-of-events>]%n", new Object[0]);
/* 561:453 */       System.err.printf("\t[-history <jobHistoryFile>]%n", new Object[0]);
/* 562:454 */       System.err.printf("\t[-list [all]]%n", new Object[0]);
/* 563:455 */       System.err.printf("\t[-list-active-trackers]%n", new Object[0]);
/* 564:456 */       System.err.printf("\t[-list-blacklisted-trackers]%n", new Object[0]);
/* 565:457 */       System.err.println("\t[-list-attempt-ids <job-id> <task-type> <task-state>]. Valid values for <task-type> are " + getTaskTypes() + ". " + "Valid values for <task-state> are " + taskStates);
/* 566:    */       
/* 567:    */ 
/* 568:    */ 
/* 569:461 */       System.err.printf("\t[-kill-task <task-attempt-id>]%n", new Object[0]);
/* 570:462 */       System.err.printf("\t[-fail-task <task-attempt-id>]%n", new Object[0]);
/* 571:463 */       System.err.printf("\t[-logs <job-id> <task-attempt-id>]%n%n", new Object[0]);
/* 572:464 */       ToolRunner.printGenericCommandUsage(System.out);
/* 573:    */     }
/* 574:    */   }
/* 575:    */   
/* 576:    */   private void viewHistory(String historyFile, boolean all)
/* 577:    */     throws IOException
/* 578:    */   {
/* 579:470 */     HistoryViewer historyViewer = new HistoryViewer(historyFile, getConf(), all);
/* 580:    */     
/* 581:472 */     historyViewer.print();
/* 582:    */   }
/* 583:    */   
/* 584:    */   protected long getCounter(Counters counters, String counterGroupName, String counterName)
/* 585:    */     throws IOException
/* 586:    */   {
/* 587:477 */     return counters.findCounter(counterGroupName, counterName).getValue();
/* 588:    */   }
/* 589:    */   
/* 590:    */   private void listEvents(Job job, int fromEventId, int numEvents)
/* 591:    */     throws IOException, InterruptedException
/* 592:    */   {
/* 593:487 */     TaskCompletionEvent[] events = job.getTaskCompletionEvents(fromEventId, numEvents);
/* 594:    */     
/* 595:489 */     System.out.println("Task completion events for " + job.getJobID());
/* 596:490 */     System.out.println("Number of events (from " + fromEventId + ") are: " + events.length);
/* 597:492 */     for (TaskCompletionEvent event : events) {
/* 598:493 */       System.out.println(event.getStatus() + " " + event.getTaskAttemptId() + " " + getTaskLogURL(event.getTaskAttemptId(), event.getTaskTrackerHttp()));
/* 599:    */     }
/* 600:    */   }
/* 601:    */   
/* 602:    */   protected static String getTaskLogURL(TaskAttemptID taskId, String baseUrl)
/* 603:    */   {
/* 604:500 */     return baseUrl + "/tasklog?plaintext=true&attemptid=" + taskId;
/* 605:    */   }
/* 606:    */   
/* 607:    */   private void listJobs(Cluster cluster)
/* 608:    */     throws IOException, InterruptedException
/* 609:    */   {
/* 610:510 */     List<JobStatus> runningJobs = new ArrayList();
/* 611:511 */     for (JobStatus job : cluster.getAllJobStatuses()) {
/* 612:512 */       if (!job.isJobComplete()) {
/* 613:513 */         runningJobs.add(job);
/* 614:    */       }
/* 615:    */     }
/* 616:516 */     displayJobList((JobStatus[])runningJobs.toArray(new JobStatus[0]));
/* 617:    */   }
/* 618:    */   
/* 619:    */   private void listAllJobs(Cluster cluster)
/* 620:    */     throws IOException, InterruptedException
/* 621:    */   {
/* 622:525 */     displayJobList(cluster.getAllJobStatuses());
/* 623:    */   }
/* 624:    */   
/* 625:    */   private void listActiveTrackers(Cluster cluster)
/* 626:    */     throws IOException, InterruptedException
/* 627:    */   {
/* 628:533 */     TaskTrackerInfo[] trackers = cluster.getActiveTaskTrackers();
/* 629:534 */     for (TaskTrackerInfo tracker : trackers) {
/* 630:535 */       System.out.println(tracker.getTaskTrackerName());
/* 631:    */     }
/* 632:    */   }
/* 633:    */   
/* 634:    */   private void listBlacklistedTrackers(Cluster cluster)
/* 635:    */     throws IOException, InterruptedException
/* 636:    */   {
/* 637:544 */     TaskTrackerInfo[] trackers = cluster.getBlackListedTaskTrackers();
/* 638:545 */     if (trackers.length > 0) {
/* 639:546 */       System.out.println("BlackListedNode \t Reason");
/* 640:    */     }
/* 641:548 */     for (TaskTrackerInfo tracker : trackers) {
/* 642:549 */       System.out.println(tracker.getTaskTrackerName() + "\t" + tracker.getReasonForBlacklist());
/* 643:    */     }
/* 644:    */   }
/* 645:    */   
/* 646:    */   private void printTaskAttempts(TaskReport report)
/* 647:    */   {
/* 648:555 */     if (report.getCurrentStatus() == TIPStatus.COMPLETE) {
/* 649:556 */       System.out.println(report.getSuccessfulTaskAttemptId());
/* 650:557 */     } else if (report.getCurrentStatus() == TIPStatus.RUNNING) {
/* 651:559 */       for (TaskAttemptID t : report.getRunningTaskAttemptIds()) {
/* 652:560 */         System.out.println(t);
/* 653:    */       }
/* 654:    */     }
/* 655:    */   }
/* 656:    */   
/* 657:    */   protected void displayTasks(Job job, String type, String state)
/* 658:    */     throws IOException, InterruptedException
/* 659:    */   {
/* 660:576 */     TaskReport[] reports = job.getTaskReports(TaskType.valueOf(type.toUpperCase()));
/* 661:577 */     for (TaskReport report : reports)
/* 662:    */     {
/* 663:578 */       TIPStatus status = report.getCurrentStatus();
/* 664:579 */       if (((state.equalsIgnoreCase("pending")) && (status == TIPStatus.PENDING)) || ((state.equalsIgnoreCase("running")) && (status == TIPStatus.RUNNING)) || ((state.equalsIgnoreCase("completed")) && (status == TIPStatus.COMPLETE)) || ((state.equalsIgnoreCase("failed")) && (status == TIPStatus.FAILED)) || ((state.equalsIgnoreCase("killed")) && (status == TIPStatus.KILLED))) {
/* 665:584 */         printTaskAttempts(report);
/* 666:    */       }
/* 667:    */     }
/* 668:    */   }
/* 669:    */   
/* 670:    */   public void displayJobList(JobStatus[] jobs)
/* 671:    */     throws IOException, InterruptedException
/* 672:    */   {
/* 673:591 */     displayJobList(jobs, new PrintWriter(new OutputStreamWriter(System.out, Charsets.UTF_8)));
/* 674:    */   }
/* 675:    */   
/* 676:    */   @InterfaceAudience.Private
/* 677:596 */   public static String headerPattern = "%23s\t%10s\t%14s\t%12s\t%12s\t%10s\t%15s\t%15s\t%8s\t%8s\t%10s\t%10s\n";
/* 678:    */   @InterfaceAudience.Private
/* 679:598 */   public static String dataPattern = "%23s\t%10s\t%14d\t%12s\t%12s\t%10s\t%15s\t%15s\t%8s\t%8s\t%10s\t%10s\n";
/* 680:599 */   private static String memPattern = "%dM";
/* 681:600 */   private static String UNAVAILABLE = "N/A";
/* 682:    */   
/* 683:    */   @InterfaceAudience.Private
/* 684:    */   public void displayJobList(JobStatus[] jobs, PrintWriter writer)
/* 685:    */   {
/* 686:604 */     writer.println("Total jobs:" + jobs.length);
/* 687:605 */     writer.printf(headerPattern, new Object[] { "JobId", "State", "StartTime", "UserName", "Queue", "Priority", "UsedContainers", "RsvdContainers", "UsedMem", "RsvdMem", "NeededMem", "AM info" });
/* 688:608 */     for (JobStatus job : jobs)
/* 689:    */     {
/* 690:609 */       int numUsedSlots = job.getNumUsedSlots();
/* 691:610 */       int numReservedSlots = job.getNumReservedSlots();
/* 692:611 */       int usedMem = job.getUsedMem();
/* 693:612 */       int rsvdMem = job.getReservedMem();
/* 694:613 */       int neededMem = job.getNeededMem();
/* 695:614 */       writer.printf(dataPattern, new Object[] { job.getJobID().toString(), job.getState(), Long.valueOf(job.getStartTime()), job.getUsername(), job.getQueue(), job.getPriority().name(), numUsedSlots < 0 ? UNAVAILABLE : Integer.valueOf(numUsedSlots), numReservedSlots < 0 ? UNAVAILABLE : Integer.valueOf(numReservedSlots), usedMem < 0 ? UNAVAILABLE : String.format(memPattern, new Object[] { Integer.valueOf(usedMem) }), rsvdMem < 0 ? UNAVAILABLE : String.format(memPattern, new Object[] { Integer.valueOf(rsvdMem) }), neededMem < 0 ? UNAVAILABLE : String.format(memPattern, new Object[] { Integer.valueOf(neededMem) }), job.getSchedulingInfo() });
/* 696:    */     }
/* 697:625 */     writer.flush();
/* 698:    */   }
/* 699:    */   
/* 700:    */   public static void main(String[] argv)
/* 701:    */     throws Exception
/* 702:    */   {
/* 703:629 */     int res = ToolRunner.run(new CLI(), argv);
/* 704:630 */     ExitUtil.terminate(res);
/* 705:    */   }
/* 706:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.tools.CLI
 * JD-Core Version:    0.7.0.1
 */