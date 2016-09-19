/*    1:     */ package org.apache.hadoop.mapreduce;
/*    2:     */ 
/*    3:     */ import java.io.IOException;
/*    4:     */ import java.io.PrintStream;
/*    5:     */ import java.net.URI;
/*    6:     */ import java.security.PrivilegedExceptionAction;
/*    7:     */ import org.apache.commons.logging.Log;
/*    8:     */ import org.apache.commons.logging.LogFactory;
/*    9:     */ import org.apache.hadoop.classification.InterfaceAudience.Private;
/*   10:     */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*   11:     */ import org.apache.hadoop.classification.InterfaceStability.Evolving;
/*   12:     */ import org.apache.hadoop.conf.Configuration;
/*   13:     */ import org.apache.hadoop.conf.Configuration.IntegerRanges;
/*   14:     */ import org.apache.hadoop.fs.FileSystem;
/*   15:     */ import org.apache.hadoop.fs.Path;
/*   16:     */ import org.apache.hadoop.io.RawComparator;
/*   17:     */ import org.apache.hadoop.mapred.JobConf;
/*   18:     */ import org.apache.hadoop.mapreduce.filecache.DistributedCache;
/*   19:     */ import org.apache.hadoop.mapreduce.protocol.ClientProtocol;
/*   20:     */ import org.apache.hadoop.mapreduce.task.JobContextImpl;
/*   21:     */ import org.apache.hadoop.mapreduce.util.ConfigUtil;
/*   22:     */ import org.apache.hadoop.security.Credentials;
/*   23:     */ import org.apache.hadoop.security.UserGroupInformation;
/*   24:     */ import org.apache.hadoop.util.StringUtils;
/*   25:     */ 
/*   26:     */ @InterfaceAudience.Public
/*   27:     */ @InterfaceStability.Evolving
/*   28:     */ public class Job
/*   29:     */   extends JobContextImpl
/*   30:     */   implements JobContext
/*   31:     */ {
/*   32:  78 */   private static final Log LOG = LogFactory.getLog(Job.class);
/*   33:     */   private static final long MAX_JOBSTATUS_AGE = 2000L;
/*   34:     */   public static final String OUTPUT_FILTER = "mapreduce.client.output.filter";
/*   35:     */   public static final String COMPLETION_POLL_INTERVAL_KEY = "mapreduce.client.completion.pollinterval";
/*   36:     */   static final int DEFAULT_COMPLETION_POLL_INTERVAL = 5000;
/*   37:     */   public static final String PROGRESS_MONITOR_POLL_INTERVAL_KEY = "mapreduce.client.progressmonitor.pollinterval";
/*   38:     */   static final int DEFAULT_MONITOR_POLL_INTERVAL = 1000;
/*   39:     */   public static final String USED_GENERIC_PARSER = "mapreduce.client.genericoptionsparser.used";
/*   40:     */   public static final String SUBMIT_REPLICATION = "mapreduce.client.submit.file.replication";
/*   41:     */   private static final String TASKLOG_PULL_TIMEOUT_KEY = "mapreduce.client.tasklog.timeout";
/*   42:     */   private static final int DEFAULT_TASKLOG_TIMEOUT = 60000;
/*   43:     */   
/*   44:     */   @InterfaceStability.Evolving
/*   45:     */   public static enum JobState
/*   46:     */   {
/*   47:  81 */     DEFINE,  RUNNING;
/*   48:     */     
/*   49:     */     private JobState() {}
/*   50:     */   }
/*   51:     */   
/*   52:     */   @InterfaceStability.Evolving
/*   53:     */   public static enum TaskStatusFilter
/*   54:     */   {
/*   55: 105 */     NONE,  KILLED,  FAILED,  SUCCEEDED,  ALL;
/*   56:     */     
/*   57:     */     private TaskStatusFilter() {}
/*   58:     */   }
/*   59:     */   
/*   60:     */   static
/*   61:     */   {
/*   62: 108 */     ConfigUtil.loadResources();
/*   63:     */   }
/*   64:     */   
/*   65: 111 */   private JobState state = JobState.DEFINE;
/*   66:     */   private JobStatus status;
/*   67:     */   private long statustime;
/*   68:     */   private Cluster cluster;
/*   69:     */   
/*   70:     */   @Deprecated
/*   71:     */   public Job()
/*   72:     */     throws IOException
/*   73:     */   {
/*   74: 118 */     this(new Configuration());
/*   75:     */   }
/*   76:     */   
/*   77:     */   @Deprecated
/*   78:     */   public Job(Configuration conf)
/*   79:     */     throws IOException
/*   80:     */   {
/*   81: 123 */     this(new JobConf(conf));
/*   82:     */   }
/*   83:     */   
/*   84:     */   @Deprecated
/*   85:     */   public Job(Configuration conf, String jobName)
/*   86:     */     throws IOException
/*   87:     */   {
/*   88: 128 */     this(conf);
/*   89: 129 */     setJobName(jobName);
/*   90:     */   }
/*   91:     */   
/*   92:     */   Job(JobConf conf)
/*   93:     */     throws IOException
/*   94:     */   {
/*   95: 133 */     super(conf, null);
/*   96:     */     
/*   97: 135 */     this.credentials.mergeAll(this.ugi.getCredentials());
/*   98: 136 */     this.cluster = null;
/*   99:     */   }
/*  100:     */   
/*  101:     */   Job(JobStatus status, JobConf conf)
/*  102:     */     throws IOException
/*  103:     */   {
/*  104: 140 */     this(conf);
/*  105: 141 */     setJobID(status.getJobID());
/*  106: 142 */     this.status = status;
/*  107: 143 */     this.state = JobState.RUNNING;
/*  108:     */   }
/*  109:     */   
/*  110:     */   public static Job getInstance()
/*  111:     */     throws IOException
/*  112:     */   {
/*  113: 156 */     return getInstance(new Configuration());
/*  114:     */   }
/*  115:     */   
/*  116:     */   public static Job getInstance(Configuration conf)
/*  117:     */     throws IOException
/*  118:     */   {
/*  119: 175 */     JobConf jobConf = new JobConf(conf);
/*  120: 176 */     return new Job(jobConf);
/*  121:     */   }
/*  122:     */   
/*  123:     */   public static Job getInstance(Configuration conf, String jobName)
/*  124:     */     throws IOException
/*  125:     */   {
/*  126: 195 */     Job result = getInstance(conf);
/*  127: 196 */     result.setJobName(jobName);
/*  128: 197 */     return result;
/*  129:     */   }
/*  130:     */   
/*  131:     */   public static Job getInstance(JobStatus status, Configuration conf)
/*  132:     */     throws IOException
/*  133:     */   {
/*  134: 216 */     return new Job(status, new JobConf(conf));
/*  135:     */   }
/*  136:     */   
/*  137:     */   @Deprecated
/*  138:     */   public static Job getInstance(Cluster ignored)
/*  139:     */     throws IOException
/*  140:     */   {
/*  141: 234 */     return getInstance();
/*  142:     */   }
/*  143:     */   
/*  144:     */   @Deprecated
/*  145:     */   public static Job getInstance(Cluster ignored, Configuration conf)
/*  146:     */     throws IOException
/*  147:     */   {
/*  148: 255 */     return getInstance(conf);
/*  149:     */   }
/*  150:     */   
/*  151:     */   @InterfaceAudience.Private
/*  152:     */   public static Job getInstance(Cluster cluster, JobStatus status, Configuration conf)
/*  153:     */     throws IOException
/*  154:     */   {
/*  155: 276 */     Job job = getInstance(status, conf);
/*  156: 277 */     job.setCluster(cluster);
/*  157: 278 */     return job;
/*  158:     */   }
/*  159:     */   
/*  160:     */   private void ensureState(JobState state)
/*  161:     */     throws IllegalStateException
/*  162:     */   {
/*  163: 282 */     if (state != this.state) {
/*  164: 283 */       throw new IllegalStateException("Job in state " + this.state + " instead of " + state);
/*  165:     */     }
/*  166: 287 */     if ((state == JobState.RUNNING) && (this.cluster == null)) {
/*  167: 288 */       throw new IllegalStateException("Job in state " + this.state + ", but it isn't attached to any job tracker!");
/*  168:     */     }
/*  169:     */   }
/*  170:     */   
/*  171:     */   synchronized void ensureFreshStatus()
/*  172:     */     throws IOException
/*  173:     */   {
/*  174: 300 */     if (System.currentTimeMillis() - this.statustime > 2000L) {
/*  175: 301 */       updateStatus();
/*  176:     */     }
/*  177:     */   }
/*  178:     */   
/*  179:     */   synchronized void updateStatus()
/*  180:     */     throws IOException
/*  181:     */   {
/*  182:     */     try
/*  183:     */     {
/*  184: 311 */       this.status = ((JobStatus)this.ugi.doAs(new PrivilegedExceptionAction()
/*  185:     */       {
/*  186:     */         public JobStatus run()
/*  187:     */           throws IOException, InterruptedException
/*  188:     */         {
/*  189: 314 */           return Job.this.cluster.getClient().getJobStatus(Job.this.status.getJobID());
/*  190:     */         }
/*  191:     */       }));
/*  192:     */     }
/*  193:     */     catch (InterruptedException ie)
/*  194:     */     {
/*  195: 319 */       throw new IOException(ie);
/*  196:     */     }
/*  197: 321 */     if (this.status == null) {
/*  198: 322 */       throw new IOException("Job status not available ");
/*  199:     */     }
/*  200: 324 */     this.statustime = System.currentTimeMillis();
/*  201:     */   }
/*  202:     */   
/*  203:     */   public JobStatus getStatus()
/*  204:     */     throws IOException, InterruptedException
/*  205:     */   {
/*  206: 328 */     ensureState(JobState.RUNNING);
/*  207: 329 */     updateStatus();
/*  208: 330 */     return this.status;
/*  209:     */   }
/*  210:     */   
/*  211:     */   private void setStatus(JobStatus status)
/*  212:     */   {
/*  213: 334 */     this.status = status;
/*  214:     */   }
/*  215:     */   
/*  216:     */   public JobStatus.State getJobState()
/*  217:     */     throws IOException, InterruptedException
/*  218:     */   {
/*  219: 346 */     ensureState(JobState.RUNNING);
/*  220: 347 */     updateStatus();
/*  221: 348 */     return this.status.getState();
/*  222:     */   }
/*  223:     */   
/*  224:     */   public String getTrackingURL()
/*  225:     */   {
/*  226: 357 */     ensureState(JobState.RUNNING);
/*  227: 358 */     return this.status.getTrackingUrl().toString();
/*  228:     */   }
/*  229:     */   
/*  230:     */   public String getJobFile()
/*  231:     */   {
/*  232: 367 */     ensureState(JobState.RUNNING);
/*  233: 368 */     return this.status.getJobFile();
/*  234:     */   }
/*  235:     */   
/*  236:     */   public long getStartTime()
/*  237:     */   {
/*  238: 377 */     ensureState(JobState.RUNNING);
/*  239: 378 */     return this.status.getStartTime();
/*  240:     */   }
/*  241:     */   
/*  242:     */   public long getFinishTime()
/*  243:     */     throws IOException, InterruptedException
/*  244:     */   {
/*  245: 387 */     ensureState(JobState.RUNNING);
/*  246: 388 */     updateStatus();
/*  247: 389 */     return this.status.getFinishTime();
/*  248:     */   }
/*  249:     */   
/*  250:     */   public String getSchedulingInfo()
/*  251:     */   {
/*  252: 398 */     ensureState(JobState.RUNNING);
/*  253: 399 */     return this.status.getSchedulingInfo();
/*  254:     */   }
/*  255:     */   
/*  256:     */   public JobPriority getPriority()
/*  257:     */     throws IOException, InterruptedException
/*  258:     */   {
/*  259: 408 */     ensureState(JobState.RUNNING);
/*  260: 409 */     updateStatus();
/*  261: 410 */     return this.status.getPriority();
/*  262:     */   }
/*  263:     */   
/*  264:     */   public String getJobName()
/*  265:     */   {
/*  266: 417 */     if (this.state == JobState.DEFINE) {
/*  267: 418 */       return super.getJobName();
/*  268:     */     }
/*  269: 420 */     ensureState(JobState.RUNNING);
/*  270: 421 */     return this.status.getJobName();
/*  271:     */   }
/*  272:     */   
/*  273:     */   public String getHistoryUrl()
/*  274:     */     throws IOException, InterruptedException
/*  275:     */   {
/*  276: 425 */     ensureState(JobState.RUNNING);
/*  277: 426 */     updateStatus();
/*  278: 427 */     return this.status.getHistoryFile();
/*  279:     */   }
/*  280:     */   
/*  281:     */   public boolean isRetired()
/*  282:     */     throws IOException, InterruptedException
/*  283:     */   {
/*  284: 431 */     ensureState(JobState.RUNNING);
/*  285: 432 */     updateStatus();
/*  286: 433 */     return this.status.isRetired();
/*  287:     */   }
/*  288:     */   
/*  289:     */   @InterfaceAudience.Private
/*  290:     */   public Cluster getCluster()
/*  291:     */   {
/*  292: 438 */     return this.cluster;
/*  293:     */   }
/*  294:     */   
/*  295:     */   @InterfaceAudience.Private
/*  296:     */   private void setCluster(Cluster cluster)
/*  297:     */   {
/*  298: 444 */     this.cluster = cluster;
/*  299:     */   }
/*  300:     */   
/*  301:     */   public String toString()
/*  302:     */   {
/*  303: 452 */     ensureState(JobState.RUNNING);
/*  304: 453 */     String reasonforFailure = " ";
/*  305: 454 */     int numMaps = 0;
/*  306: 455 */     int numReduces = 0;
/*  307:     */     try
/*  308:     */     {
/*  309: 457 */       updateStatus();
/*  310: 458 */       if (this.status.getState().equals(JobStatus.State.FAILED)) {
/*  311: 459 */         reasonforFailure = getTaskFailureEventString();
/*  312:     */       }
/*  313: 460 */       numMaps = getTaskReports(TaskType.MAP).length;
/*  314: 461 */       numReduces = getTaskReports(TaskType.REDUCE).length;
/*  315:     */     }
/*  316:     */     catch (IOException e) {}catch (InterruptedException ie) {}
/*  317: 465 */     StringBuffer sb = new StringBuffer();
/*  318: 466 */     sb.append("Job: ").append(this.status.getJobID()).append("\n");
/*  319: 467 */     sb.append("Job File: ").append(this.status.getJobFile()).append("\n");
/*  320: 468 */     sb.append("Job Tracking URL : ").append(this.status.getTrackingUrl());
/*  321: 469 */     sb.append("\n");
/*  322: 470 */     sb.append("Uber job : ").append(this.status.isUber()).append("\n");
/*  323: 471 */     sb.append("Number of maps: ").append(numMaps).append("\n");
/*  324: 472 */     sb.append("Number of reduces: ").append(numReduces).append("\n");
/*  325: 473 */     sb.append("map() completion: ");
/*  326: 474 */     sb.append(this.status.getMapProgress()).append("\n");
/*  327: 475 */     sb.append("reduce() completion: ");
/*  328: 476 */     sb.append(this.status.getReduceProgress()).append("\n");
/*  329: 477 */     sb.append("Job state: ");
/*  330: 478 */     sb.append(this.status.getState()).append("\n");
/*  331: 479 */     sb.append("retired: ").append(this.status.isRetired()).append("\n");
/*  332: 480 */     sb.append("reason for failure: ").append(reasonforFailure);
/*  333: 481 */     return sb.toString();
/*  334:     */   }
/*  335:     */   
/*  336:     */   String getTaskFailureEventString()
/*  337:     */     throws IOException, InterruptedException
/*  338:     */   {
/*  339: 491 */     int failCount = 1;
/*  340: 492 */     TaskCompletionEvent lastEvent = null;
/*  341: 493 */     TaskCompletionEvent[] events = (TaskCompletionEvent[])this.ugi.doAs(new PrivilegedExceptionAction()
/*  342:     */     {
/*  343:     */       public TaskCompletionEvent[] run()
/*  344:     */         throws IOException, InterruptedException
/*  345:     */       {
/*  346: 498 */         return Job.this.cluster.getClient().getTaskCompletionEvents(Job.this.status.getJobID(), 0, 10);
/*  347:     */       }
/*  348:     */     });
/*  349: 502 */     for (TaskCompletionEvent event : events) {
/*  350: 503 */       if (event.getStatus().equals(TaskCompletionEvent.Status.FAILED))
/*  351:     */       {
/*  352: 504 */         failCount++;
/*  353: 505 */         lastEvent = event;
/*  354:     */       }
/*  355:     */     }
/*  356: 508 */     if (lastEvent == null) {
/*  357: 509 */       return "There are no failed tasks for the job. Job is failed due to some other reason and reason can be found in the logs.";
/*  358:     */     }
/*  359: 513 */     String[] taskAttemptID = lastEvent.getTaskAttemptId().toString().split("_", 2);
/*  360: 514 */     String taskID = taskAttemptID[1].substring(0, taskAttemptID[1].length() - 2);
/*  361: 515 */     return " task " + taskID + " failed " + failCount + " times " + "For details check tasktracker at: " + lastEvent.getTaskTrackerHttp();
/*  362:     */   }
/*  363:     */   
/*  364:     */   public TaskReport[] getTaskReports(TaskType type)
/*  365:     */     throws IOException, InterruptedException
/*  366:     */   {
/*  367: 529 */     ensureState(JobState.RUNNING);
/*  368: 530 */     final TaskType tmpType = type;
/*  369: 531 */     (TaskReport[])this.ugi.doAs(new PrivilegedExceptionAction()
/*  370:     */     {
/*  371:     */       public TaskReport[] run()
/*  372:     */         throws IOException, InterruptedException
/*  373:     */       {
/*  374: 533 */         return Job.this.cluster.getClient().getTaskReports(Job.this.getJobID(), tmpType);
/*  375:     */       }
/*  376:     */     });
/*  377:     */   }
/*  378:     */   
/*  379:     */   public float mapProgress()
/*  380:     */     throws IOException
/*  381:     */   {
/*  382: 546 */     ensureState(JobState.RUNNING);
/*  383: 547 */     ensureFreshStatus();
/*  384: 548 */     return this.status.getMapProgress();
/*  385:     */   }
/*  386:     */   
/*  387:     */   public float reduceProgress()
/*  388:     */     throws IOException
/*  389:     */   {
/*  390: 559 */     ensureState(JobState.RUNNING);
/*  391: 560 */     ensureFreshStatus();
/*  392: 561 */     return this.status.getReduceProgress();
/*  393:     */   }
/*  394:     */   
/*  395:     */   public float cleanupProgress()
/*  396:     */     throws IOException, InterruptedException
/*  397:     */   {
/*  398: 572 */     ensureState(JobState.RUNNING);
/*  399: 573 */     ensureFreshStatus();
/*  400: 574 */     return this.status.getCleanupProgress();
/*  401:     */   }
/*  402:     */   
/*  403:     */   public float setupProgress()
/*  404:     */     throws IOException
/*  405:     */   {
/*  406: 585 */     ensureState(JobState.RUNNING);
/*  407: 586 */     ensureFreshStatus();
/*  408: 587 */     return this.status.getSetupProgress();
/*  409:     */   }
/*  410:     */   
/*  411:     */   public boolean isComplete()
/*  412:     */     throws IOException
/*  413:     */   {
/*  414: 598 */     ensureState(JobState.RUNNING);
/*  415: 599 */     updateStatus();
/*  416: 600 */     return this.status.isJobComplete();
/*  417:     */   }
/*  418:     */   
/*  419:     */   public boolean isSuccessful()
/*  420:     */     throws IOException
/*  421:     */   {
/*  422: 610 */     ensureState(JobState.RUNNING);
/*  423: 611 */     updateStatus();
/*  424: 612 */     return this.status.getState() == JobStatus.State.SUCCEEDED;
/*  425:     */   }
/*  426:     */   
/*  427:     */   public void killJob()
/*  428:     */     throws IOException
/*  429:     */   {
/*  430: 622 */     ensureState(JobState.RUNNING);
/*  431:     */     try
/*  432:     */     {
/*  433: 624 */       this.cluster.getClient().killJob(getJobID());
/*  434:     */     }
/*  435:     */     catch (InterruptedException ie)
/*  436:     */     {
/*  437: 627 */       throw new IOException(ie);
/*  438:     */     }
/*  439:     */   }
/*  440:     */   
/*  441:     */   public void setPriority(JobPriority priority)
/*  442:     */     throws IOException, InterruptedException
/*  443:     */   {
/*  444: 638 */     if (this.state == JobState.DEFINE)
/*  445:     */     {
/*  446: 639 */       this.conf.setJobPriority(org.apache.hadoop.mapred.JobPriority.valueOf(priority.name()));
/*  447:     */     }
/*  448:     */     else
/*  449:     */     {
/*  450: 642 */       ensureState(JobState.RUNNING);
/*  451: 643 */       final JobPriority tmpPriority = priority;
/*  452: 644 */       this.ugi.doAs(new PrivilegedExceptionAction()
/*  453:     */       {
/*  454:     */         public Object run()
/*  455:     */           throws IOException, InterruptedException
/*  456:     */         {
/*  457: 647 */           Job.this.cluster.getClient().setJobPriority(Job.this.getJobID(), tmpPriority.toString());
/*  458: 648 */           return null;
/*  459:     */         }
/*  460:     */       });
/*  461:     */     }
/*  462:     */   }
/*  463:     */   
/*  464:     */   public TaskCompletionEvent[] getTaskCompletionEvents(final int startFrom, final int numEvents)
/*  465:     */     throws IOException, InterruptedException
/*  466:     */   {
/*  467: 664 */     ensureState(JobState.RUNNING);
/*  468: 665 */     (TaskCompletionEvent[])this.ugi.doAs(new PrivilegedExceptionAction()
/*  469:     */     {
/*  470:     */       public TaskCompletionEvent[] run()
/*  471:     */         throws IOException, InterruptedException
/*  472:     */       {
/*  473: 668 */         return Job.this.cluster.getClient().getTaskCompletionEvents(Job.this.getJobID(), startFrom, numEvents);
/*  474:     */       }
/*  475:     */     });
/*  476:     */   }
/*  477:     */   
/*  478:     */   public org.apache.hadoop.mapred.TaskCompletionEvent[] getTaskCompletionEvents(int startFrom)
/*  479:     */     throws IOException
/*  480:     */   {
/*  481:     */     try
/*  482:     */     {
/*  483: 684 */       TaskCompletionEvent[] events = getTaskCompletionEvents(startFrom, 10);
/*  484: 685 */       org.apache.hadoop.mapred.TaskCompletionEvent[] retEvents = new org.apache.hadoop.mapred.TaskCompletionEvent[events.length];
/*  485: 687 */       for (int i = 0; i < events.length; i++) {
/*  486: 688 */         retEvents[i] = org.apache.hadoop.mapred.TaskCompletionEvent.downgrade(events[i]);
/*  487:     */       }
/*  488: 691 */       return retEvents;
/*  489:     */     }
/*  490:     */     catch (InterruptedException ie)
/*  491:     */     {
/*  492: 693 */       throw new IOException(ie);
/*  493:     */     }
/*  494:     */   }
/*  495:     */   
/*  496:     */   @InterfaceAudience.Private
/*  497:     */   public boolean killTask(final TaskAttemptID taskId, final boolean shouldFail)
/*  498:     */     throws IOException
/*  499:     */   {
/*  500: 707 */     ensureState(JobState.RUNNING);
/*  501:     */     try
/*  502:     */     {
/*  503: 709 */       ((Boolean)this.ugi.doAs(new PrivilegedExceptionAction()
/*  504:     */       {
/*  505:     */         public Boolean run()
/*  506:     */           throws IOException, InterruptedException
/*  507:     */         {
/*  508: 711 */           return Boolean.valueOf(Job.this.cluster.getClient().killTask(taskId, shouldFail));
/*  509:     */         }
/*  510:     */       })).booleanValue();
/*  511:     */     }
/*  512:     */     catch (InterruptedException ie)
/*  513:     */     {
/*  514: 716 */       throw new IOException(ie);
/*  515:     */     }
/*  516:     */   }
/*  517:     */   
/*  518:     */   public void killTask(TaskAttemptID taskId)
/*  519:     */     throws IOException
/*  520:     */   {
/*  521: 728 */     killTask(taskId, false);
/*  522:     */   }
/*  523:     */   
/*  524:     */   public void failTask(TaskAttemptID taskId)
/*  525:     */     throws IOException
/*  526:     */   {
/*  527: 739 */     killTask(taskId, true);
/*  528:     */   }
/*  529:     */   
/*  530:     */   public Counters getCounters()
/*  531:     */     throws IOException
/*  532:     */   {
/*  533: 751 */     ensureState(JobState.RUNNING);
/*  534:     */     try
/*  535:     */     {
/*  536: 753 */       (Counters)this.ugi.doAs(new PrivilegedExceptionAction()
/*  537:     */       {
/*  538:     */         public Counters run()
/*  539:     */           throws IOException, InterruptedException
/*  540:     */         {
/*  541: 756 */           return Job.this.cluster.getClient().getJobCounters(Job.this.getJobID());
/*  542:     */         }
/*  543:     */       });
/*  544:     */     }
/*  545:     */     catch (InterruptedException ie)
/*  546:     */     {
/*  547: 761 */       throw new IOException(ie);
/*  548:     */     }
/*  549:     */   }
/*  550:     */   
/*  551:     */   public String[] getTaskDiagnostics(final TaskAttemptID taskid)
/*  552:     */     throws IOException, InterruptedException
/*  553:     */   {
/*  554: 773 */     ensureState(JobState.RUNNING);
/*  555: 774 */     (String[])this.ugi.doAs(new PrivilegedExceptionAction()
/*  556:     */     {
/*  557:     */       public String[] run()
/*  558:     */         throws IOException, InterruptedException
/*  559:     */       {
/*  560: 777 */         return Job.this.cluster.getClient().getTaskDiagnostics(taskid);
/*  561:     */       }
/*  562:     */     });
/*  563:     */   }
/*  564:     */   
/*  565:     */   public void setNumReduceTasks(int tasks)
/*  566:     */     throws IllegalStateException
/*  567:     */   {
/*  568: 788 */     ensureState(JobState.DEFINE);
/*  569: 789 */     this.conf.setNumReduceTasks(tasks);
/*  570:     */   }
/*  571:     */   
/*  572:     */   public void setWorkingDirectory(Path dir)
/*  573:     */     throws IOException
/*  574:     */   {
/*  575: 799 */     ensureState(JobState.DEFINE);
/*  576: 800 */     this.conf.setWorkingDirectory(dir);
/*  577:     */   }
/*  578:     */   
/*  579:     */   public void setInputFormatClass(Class<? extends InputFormat> cls)
/*  580:     */     throws IllegalStateException
/*  581:     */   {
/*  582: 810 */     ensureState(JobState.DEFINE);
/*  583: 811 */     this.conf.setClass("mapreduce.job.inputformat.class", cls, InputFormat.class);
/*  584:     */   }
/*  585:     */   
/*  586:     */   public void setOutputFormatClass(Class<? extends OutputFormat> cls)
/*  587:     */     throws IllegalStateException
/*  588:     */   {
/*  589: 822 */     ensureState(JobState.DEFINE);
/*  590: 823 */     this.conf.setClass("mapreduce.job.outputformat.class", cls, OutputFormat.class);
/*  591:     */   }
/*  592:     */   
/*  593:     */   public void setMapperClass(Class<? extends Mapper> cls)
/*  594:     */     throws IllegalStateException
/*  595:     */   {
/*  596: 834 */     ensureState(JobState.DEFINE);
/*  597: 835 */     this.conf.setClass("mapreduce.job.map.class", cls, Mapper.class);
/*  598:     */   }
/*  599:     */   
/*  600:     */   public void setJarByClass(Class<?> cls)
/*  601:     */   {
/*  602: 843 */     ensureState(JobState.DEFINE);
/*  603: 844 */     this.conf.setJarByClass(cls);
/*  604:     */   }
/*  605:     */   
/*  606:     */   public void setJar(String jar)
/*  607:     */   {
/*  608: 851 */     ensureState(JobState.DEFINE);
/*  609: 852 */     this.conf.setJar(jar);
/*  610:     */   }
/*  611:     */   
/*  612:     */   public void setUser(String user)
/*  613:     */   {
/*  614: 861 */     ensureState(JobState.DEFINE);
/*  615: 862 */     this.conf.setUser(user);
/*  616:     */   }
/*  617:     */   
/*  618:     */   public void setCombinerClass(Class<? extends Reducer> cls)
/*  619:     */     throws IllegalStateException
/*  620:     */   {
/*  621: 872 */     ensureState(JobState.DEFINE);
/*  622: 873 */     this.conf.setClass("mapreduce.job.combine.class", cls, Reducer.class);
/*  623:     */   }
/*  624:     */   
/*  625:     */   public void setReducerClass(Class<? extends Reducer> cls)
/*  626:     */     throws IllegalStateException
/*  627:     */   {
/*  628: 883 */     ensureState(JobState.DEFINE);
/*  629: 884 */     this.conf.setClass("mapreduce.job.reduce.class", cls, Reducer.class);
/*  630:     */   }
/*  631:     */   
/*  632:     */   public void setPartitionerClass(Class<? extends Partitioner> cls)
/*  633:     */     throws IllegalStateException
/*  634:     */   {
/*  635: 894 */     ensureState(JobState.DEFINE);
/*  636: 895 */     this.conf.setClass("mapreduce.job.partitioner.class", cls, Partitioner.class);
/*  637:     */   }
/*  638:     */   
/*  639:     */   public void setMapOutputKeyClass(Class<?> theClass)
/*  640:     */     throws IllegalStateException
/*  641:     */   {
/*  642: 909 */     ensureState(JobState.DEFINE);
/*  643: 910 */     this.conf.setMapOutputKeyClass(theClass);
/*  644:     */   }
/*  645:     */   
/*  646:     */   public void setMapOutputValueClass(Class<?> theClass)
/*  647:     */     throws IllegalStateException
/*  648:     */   {
/*  649: 923 */     ensureState(JobState.DEFINE);
/*  650: 924 */     this.conf.setMapOutputValueClass(theClass);
/*  651:     */   }
/*  652:     */   
/*  653:     */   public void setOutputKeyClass(Class<?> theClass)
/*  654:     */     throws IllegalStateException
/*  655:     */   {
/*  656: 935 */     ensureState(JobState.DEFINE);
/*  657: 936 */     this.conf.setOutputKeyClass(theClass);
/*  658:     */   }
/*  659:     */   
/*  660:     */   public void setOutputValueClass(Class<?> theClass)
/*  661:     */     throws IllegalStateException
/*  662:     */   {
/*  663: 947 */     ensureState(JobState.DEFINE);
/*  664: 948 */     this.conf.setOutputValueClass(theClass);
/*  665:     */   }
/*  666:     */   
/*  667:     */   public void setCombinerKeyGroupingComparatorClass(Class<? extends RawComparator> cls)
/*  668:     */     throws IllegalStateException
/*  669:     */   {
/*  670: 962 */     ensureState(JobState.DEFINE);
/*  671: 963 */     this.conf.setCombinerKeyGroupingComparator(cls);
/*  672:     */   }
/*  673:     */   
/*  674:     */   public void setSortComparatorClass(Class<? extends RawComparator> cls)
/*  675:     */     throws IllegalStateException
/*  676:     */   {
/*  677: 975 */     ensureState(JobState.DEFINE);
/*  678: 976 */     this.conf.setOutputKeyComparatorClass(cls);
/*  679:     */   }
/*  680:     */   
/*  681:     */   public void setGroupingComparatorClass(Class<? extends RawComparator> cls)
/*  682:     */     throws IllegalStateException
/*  683:     */   {
/*  684: 990 */     ensureState(JobState.DEFINE);
/*  685: 991 */     this.conf.setOutputValueGroupingComparator(cls);
/*  686:     */   }
/*  687:     */   
/*  688:     */   public void setJobName(String name)
/*  689:     */     throws IllegalStateException
/*  690:     */   {
/*  691:1001 */     ensureState(JobState.DEFINE);
/*  692:1002 */     this.conf.setJobName(name);
/*  693:     */   }
/*  694:     */   
/*  695:     */   public void setSpeculativeExecution(boolean speculativeExecution)
/*  696:     */   {
/*  697:1012 */     ensureState(JobState.DEFINE);
/*  698:1013 */     this.conf.setSpeculativeExecution(speculativeExecution);
/*  699:     */   }
/*  700:     */   
/*  701:     */   public void setMapSpeculativeExecution(boolean speculativeExecution)
/*  702:     */   {
/*  703:1024 */     ensureState(JobState.DEFINE);
/*  704:1025 */     this.conf.setMapSpeculativeExecution(speculativeExecution);
/*  705:     */   }
/*  706:     */   
/*  707:     */   public void setReduceSpeculativeExecution(boolean speculativeExecution)
/*  708:     */   {
/*  709:1036 */     ensureState(JobState.DEFINE);
/*  710:1037 */     this.conf.setReduceSpeculativeExecution(speculativeExecution);
/*  711:     */   }
/*  712:     */   
/*  713:     */   public void setJobSetupCleanupNeeded(boolean needed)
/*  714:     */   {
/*  715:1048 */     ensureState(JobState.DEFINE);
/*  716:1049 */     this.conf.setBoolean("mapreduce.job.committer.setup.cleanup.needed", needed);
/*  717:     */   }
/*  718:     */   
/*  719:     */   public void setCacheArchives(URI[] archives)
/*  720:     */   {
/*  721:1057 */     ensureState(JobState.DEFINE);
/*  722:1058 */     DistributedCache.setCacheArchives(archives, this.conf);
/*  723:     */   }
/*  724:     */   
/*  725:     */   public void setCacheFiles(URI[] files)
/*  726:     */   {
/*  727:1066 */     ensureState(JobState.DEFINE);
/*  728:1067 */     DistributedCache.setCacheFiles(files, this.conf);
/*  729:     */   }
/*  730:     */   
/*  731:     */   public void addCacheArchive(URI uri)
/*  732:     */   {
/*  733:1075 */     ensureState(JobState.DEFINE);
/*  734:1076 */     DistributedCache.addCacheArchive(uri, this.conf);
/*  735:     */   }
/*  736:     */   
/*  737:     */   public void addCacheFile(URI uri)
/*  738:     */   {
/*  739:1084 */     ensureState(JobState.DEFINE);
/*  740:1085 */     DistributedCache.addCacheFile(uri, this.conf);
/*  741:     */   }
/*  742:     */   
/*  743:     */   public void addFileToClassPath(Path file)
/*  744:     */     throws IOException
/*  745:     */   {
/*  746:1101 */     ensureState(JobState.DEFINE);
/*  747:1102 */     DistributedCache.addFileToClassPath(file, this.conf, file.getFileSystem(this.conf));
/*  748:     */   }
/*  749:     */   
/*  750:     */   public void addArchiveToClassPath(Path archive)
/*  751:     */     throws IOException
/*  752:     */   {
/*  753:1116 */     ensureState(JobState.DEFINE);
/*  754:1117 */     DistributedCache.addArchiveToClassPath(archive, this.conf, archive.getFileSystem(this.conf));
/*  755:     */   }
/*  756:     */   
/*  757:     */   @Deprecated
/*  758:     */   public void createSymlink()
/*  759:     */   {
/*  760:1126 */     ensureState(JobState.DEFINE);
/*  761:1127 */     DistributedCache.createSymlink(this.conf);
/*  762:     */   }
/*  763:     */   
/*  764:     */   public void setMaxMapAttempts(int n)
/*  765:     */   {
/*  766:1137 */     ensureState(JobState.DEFINE);
/*  767:1138 */     this.conf.setMaxMapAttempts(n);
/*  768:     */   }
/*  769:     */   
/*  770:     */   public void setMaxReduceAttempts(int n)
/*  771:     */   {
/*  772:1148 */     ensureState(JobState.DEFINE);
/*  773:1149 */     this.conf.setMaxReduceAttempts(n);
/*  774:     */   }
/*  775:     */   
/*  776:     */   public void setProfileEnabled(boolean newValue)
/*  777:     */   {
/*  778:1159 */     ensureState(JobState.DEFINE);
/*  779:1160 */     this.conf.setProfileEnabled(newValue);
/*  780:     */   }
/*  781:     */   
/*  782:     */   public void setProfileParams(String value)
/*  783:     */   {
/*  784:1173 */     ensureState(JobState.DEFINE);
/*  785:1174 */     this.conf.setProfileParams(value);
/*  786:     */   }
/*  787:     */   
/*  788:     */   public void setProfileTaskRange(boolean isMap, String newValue)
/*  789:     */   {
/*  790:1183 */     ensureState(JobState.DEFINE);
/*  791:1184 */     this.conf.setProfileTaskRange(isMap, newValue);
/*  792:     */   }
/*  793:     */   
/*  794:     */   private void ensureNotSet(String attr, String msg)
/*  795:     */     throws IOException
/*  796:     */   {
/*  797:1188 */     if (this.conf.get(attr) != null) {
/*  798:1189 */       throw new IOException(attr + " is incompatible with " + msg + " mode.");
/*  799:     */     }
/*  800:     */   }
/*  801:     */   
/*  802:     */   public void setCancelDelegationTokenUponJobCompletion(boolean value)
/*  803:     */   {
/*  804:1198 */     ensureState(JobState.DEFINE);
/*  805:1199 */     this.conf.setBoolean("mapreduce.job.complete.cancel.delegation.tokens", value);
/*  806:     */   }
/*  807:     */   
/*  808:     */   private void setUseNewAPI()
/*  809:     */     throws IOException
/*  810:     */   {
/*  811:1208 */     int numReduces = this.conf.getNumReduceTasks();
/*  812:1209 */     String oldMapperClass = "mapred.mapper.class";
/*  813:1210 */     String oldReduceClass = "mapred.reducer.class";
/*  814:1211 */     this.conf.setBooleanIfUnset("mapred.mapper.new-api", this.conf.get(oldMapperClass) == null);
/*  815:1213 */     if (this.conf.getUseNewMapper())
/*  816:     */     {
/*  817:1214 */       String mode = "new map API";
/*  818:1215 */       ensureNotSet("mapred.input.format.class", mode);
/*  819:1216 */       ensureNotSet(oldMapperClass, mode);
/*  820:1217 */       if (numReduces != 0) {
/*  821:1218 */         ensureNotSet("mapred.partitioner.class", mode);
/*  822:     */       } else {
/*  823:1220 */         ensureNotSet("mapred.output.format.class", mode);
/*  824:     */       }
/*  825:     */     }
/*  826:     */     else
/*  827:     */     {
/*  828:1223 */       String mode = "map compatability";
/*  829:1224 */       ensureNotSet("mapreduce.job.inputformat.class", mode);
/*  830:1225 */       ensureNotSet("mapreduce.job.map.class", mode);
/*  831:1226 */       if (numReduces != 0) {
/*  832:1227 */         ensureNotSet("mapreduce.job.partitioner.class", mode);
/*  833:     */       } else {
/*  834:1229 */         ensureNotSet("mapreduce.job.outputformat.class", mode);
/*  835:     */       }
/*  836:     */     }
/*  837:1232 */     if (numReduces != 0)
/*  838:     */     {
/*  839:1233 */       this.conf.setBooleanIfUnset("mapred.reducer.new-api", this.conf.get(oldReduceClass) == null);
/*  840:1235 */       if (this.conf.getUseNewReducer())
/*  841:     */       {
/*  842:1236 */         String mode = "new reduce API";
/*  843:1237 */         ensureNotSet("mapred.output.format.class", mode);
/*  844:1238 */         ensureNotSet(oldReduceClass, mode);
/*  845:     */       }
/*  846:     */       else
/*  847:     */       {
/*  848:1240 */         String mode = "reduce compatability";
/*  849:1241 */         ensureNotSet("mapreduce.job.outputformat.class", mode);
/*  850:1242 */         ensureNotSet("mapreduce.job.reduce.class", mode);
/*  851:     */       }
/*  852:     */     }
/*  853:     */   }
/*  854:     */   
/*  855:     */   private synchronized void connect()
/*  856:     */     throws IOException, InterruptedException, ClassNotFoundException
/*  857:     */   {
/*  858:1249 */     if (this.cluster == null) {
/*  859:1250 */       this.cluster = ((Cluster)this.ugi.doAs(new PrivilegedExceptionAction()
/*  860:     */       {
/*  861:     */         public Cluster run()
/*  862:     */           throws IOException, InterruptedException, ClassNotFoundException
/*  863:     */         {
/*  864:1255 */           return new Cluster(Job.this.getConfiguration());
/*  865:     */         }
/*  866:     */       }));
/*  867:     */     }
/*  868:     */   }
/*  869:     */   
/*  870:     */   boolean isConnected()
/*  871:     */   {
/*  872:1262 */     return this.cluster != null;
/*  873:     */   }
/*  874:     */   
/*  875:     */   @InterfaceAudience.Private
/*  876:     */   public JobSubmitter getJobSubmitter(FileSystem fs, ClientProtocol submitClient)
/*  877:     */     throws IOException
/*  878:     */   {
/*  879:1269 */     return new JobSubmitter(fs, submitClient);
/*  880:     */   }
/*  881:     */   
/*  882:     */   public void submit()
/*  883:     */     throws IOException, InterruptedException, ClassNotFoundException
/*  884:     */   {
/*  885:1277 */     ensureState(JobState.DEFINE);
/*  886:1278 */     setUseNewAPI();
/*  887:1279 */     connect();
/*  888:1280 */     final JobSubmitter submitter = getJobSubmitter(this.cluster.getFileSystem(), this.cluster.getClient());
/*  889:     */     
/*  890:1282 */     this.status = ((JobStatus)this.ugi.doAs(new PrivilegedExceptionAction()
/*  891:     */     {
/*  892:     */       public JobStatus run()
/*  893:     */         throws IOException, InterruptedException, ClassNotFoundException
/*  894:     */       {
/*  895:1285 */         return submitter.submitJobInternal(Job.this, Job.this.cluster);
/*  896:     */       }
/*  897:1287 */     }));
/*  898:1288 */     this.state = JobState.RUNNING;
/*  899:1289 */     LOG.info("The url to track the job: " + getTrackingURL());
/*  900:     */   }
/*  901:     */   
/*  902:     */   public boolean waitForCompletion(boolean verbose)
/*  903:     */     throws IOException, InterruptedException, ClassNotFoundException
/*  904:     */   {
/*  905:1302 */     if (this.state == JobState.DEFINE) {
/*  906:1303 */       submit();
/*  907:     */     }
/*  908:1305 */     if (verbose)
/*  909:     */     {
/*  910:1306 */       monitorAndPrintJob();
/*  911:     */     }
/*  912:     */     else
/*  913:     */     {
/*  914:1309 */       int completionPollIntervalMillis = getCompletionPollInterval(this.cluster.getConf());
/*  915:1311 */       while (!isComplete()) {
/*  916:     */         try
/*  917:     */         {
/*  918:1313 */           Thread.sleep(completionPollIntervalMillis);
/*  919:     */         }
/*  920:     */         catch (InterruptedException ie) {}
/*  921:     */       }
/*  922:     */     }
/*  923:1318 */     return isSuccessful();
/*  924:     */   }
/*  925:     */   
/*  926:     */   public boolean monitorAndPrintJob()
/*  927:     */     throws IOException, InterruptedException
/*  928:     */   {
/*  929:1329 */     String lastReport = null;
/*  930:     */     
/*  931:1331 */     Configuration clientConf = getConfiguration();
/*  932:1332 */     TaskStatusFilter filter = getTaskOutputFilter(clientConf);
/*  933:1333 */     JobID jobId = getJobID();
/*  934:1334 */     LOG.info("Running job: " + jobId);
/*  935:1335 */     int eventCounter = 0;
/*  936:1336 */     boolean profiling = getProfileEnabled();
/*  937:1337 */     Configuration.IntegerRanges mapRanges = getProfileTaskRange(true);
/*  938:1338 */     Configuration.IntegerRanges reduceRanges = getProfileTaskRange(false);
/*  939:1339 */     int progMonitorPollIntervalMillis = getProgressPollInterval(clientConf);
/*  940:     */     
/*  941:     */ 
/*  942:1342 */     boolean reportedAfterCompletion = false;
/*  943:1343 */     boolean reportedUberMode = false;
/*  944:1344 */     while ((!isComplete()) || (!reportedAfterCompletion))
/*  945:     */     {
/*  946:1345 */       if (isComplete()) {
/*  947:1346 */         reportedAfterCompletion = true;
/*  948:     */       } else {
/*  949:1348 */         Thread.sleep(progMonitorPollIntervalMillis);
/*  950:     */       }
/*  951:1350 */       if (this.status.getState() != JobStatus.State.PREP)
/*  952:     */       {
/*  953:1353 */         if (!reportedUberMode)
/*  954:     */         {
/*  955:1354 */           reportedUberMode = true;
/*  956:1355 */           LOG.info("Job " + jobId + " running in uber mode : " + isUber());
/*  957:     */         }
/*  958:1357 */         String report = " map " + StringUtils.formatPercent(mapProgress(), 0) + " reduce " + StringUtils.formatPercent(reduceProgress(), 0);
/*  959:1361 */         if (!report.equals(lastReport))
/*  960:     */         {
/*  961:1362 */           LOG.info(report);
/*  962:1363 */           lastReport = report;
/*  963:     */         }
/*  964:1366 */         TaskCompletionEvent[] events = getTaskCompletionEvents(eventCounter, 10);
/*  965:     */         
/*  966:1368 */         eventCounter += events.length;
/*  967:1369 */         printTaskEvents(events, filter, profiling, mapRanges, reduceRanges);
/*  968:     */       }
/*  969:     */     }
/*  970:1371 */     boolean success = isSuccessful();
/*  971:1372 */     if (success) {
/*  972:1373 */       LOG.info("Job " + jobId + " completed successfully");
/*  973:     */     } else {
/*  974:1375 */       LOG.info("Job " + jobId + " failed with state " + this.status.getState() + " due to: " + this.status.getFailureInfo());
/*  975:     */     }
/*  976:1378 */     Counters counters = getCounters();
/*  977:1379 */     if (counters != null) {
/*  978:1380 */       LOG.info(counters.toString());
/*  979:     */     }
/*  980:1382 */     return success;
/*  981:     */   }
/*  982:     */   
/*  983:     */   private boolean shouldDownloadProfile()
/*  984:     */   {
/*  985:1394 */     String profileParams = getProfileParams();
/*  986:1396 */     if (null == profileParams) {
/*  987:1397 */       return false;
/*  988:     */     }
/*  989:1401 */     String[] parts = profileParams.split("[ \\t]+");
/*  990:     */     
/*  991:     */ 
/*  992:1404 */     boolean hprofFound = false;
/*  993:1405 */     boolean fileFound = false;
/*  994:1406 */     for (String p : parts) {
/*  995:1407 */       if ((p.startsWith("-agentlib:hprof")) || (p.startsWith("-Xrunhprof")))
/*  996:     */       {
/*  997:1408 */         hprofFound = true;
/*  998:     */         
/*  999:     */ 
/* 1000:     */ 
/* 1001:     */ 
/* 1002:1413 */         String[] subparts = p.split(",");
/* 1003:1414 */         for (String sub : subparts) {
/* 1004:1415 */           if ((sub.startsWith("file=")) && (sub.length() != "file=".length())) {
/* 1005:1416 */             fileFound = true;
/* 1006:     */           }
/* 1007:     */         }
/* 1008:     */       }
/* 1009:     */     }
/* 1010:1422 */     return (hprofFound) && (fileFound);
/* 1011:     */   }
/* 1012:     */   
/* 1013:     */   private void printTaskEvents(TaskCompletionEvent[] events, TaskStatusFilter filter, boolean profiling, Configuration.IntegerRanges mapRanges, Configuration.IntegerRanges reduceRanges)
/* 1014:     */     throws IOException, InterruptedException
/* 1015:     */   {
/* 1016:1428 */     for (TaskCompletionEvent event : events) {
/* 1017:1429 */       switch (11.$SwitchMap$org$apache$hadoop$mapreduce$Job$TaskStatusFilter[filter.ordinal()])
/* 1018:     */       {
/* 1019:     */       case 1: 
/* 1020:     */         break;
/* 1021:     */       case 2: 
/* 1022:1433 */         if (event.getStatus() == TaskCompletionEvent.Status.SUCCEEDED) {
/* 1023:1435 */           LOG.info(event.toString());
/* 1024:     */         }
/* 1025:     */         break;
/* 1026:     */       case 3: 
/* 1027:1439 */         if (event.getStatus() == TaskCompletionEvent.Status.FAILED)
/* 1028:     */         {
/* 1029:1441 */           LOG.info(event.toString());
/* 1030:     */           
/* 1031:1443 */           TaskAttemptID taskId = event.getTaskAttemptId();
/* 1032:1444 */           String[] taskDiagnostics = getTaskDiagnostics(taskId);
/* 1033:1445 */           if (taskDiagnostics != null) {
/* 1034:1446 */             for (String diagnostics : taskDiagnostics) {
/* 1035:1447 */               System.err.println(diagnostics);
/* 1036:     */             }
/* 1037:     */           }
/* 1038:     */         }
/* 1039:1450 */         break;
/* 1040:     */       case 4: 
/* 1041:1453 */         if (event.getStatus() == TaskCompletionEvent.Status.KILLED) {
/* 1042:1454 */           LOG.info(event.toString());
/* 1043:     */         }
/* 1044:     */         break;
/* 1045:     */       case 5: 
/* 1046:1458 */         LOG.info(event.toString());
/* 1047:     */       }
/* 1048:     */     }
/* 1049:     */   }
/* 1050:     */   
/* 1051:     */   public static int getProgressPollInterval(Configuration conf)
/* 1052:     */   {
/* 1053:1467 */     int progMonitorPollIntervalMillis = conf.getInt("mapreduce.client.progressmonitor.pollinterval", 1000);
/* 1054:1469 */     if (progMonitorPollIntervalMillis < 1)
/* 1055:     */     {
/* 1056:1470 */       LOG.warn("mapreduce.client.progressmonitor.pollinterval has been set to an invalid value;  replacing with 1000");
/* 1057:     */       
/* 1058:     */ 
/* 1059:1473 */       progMonitorPollIntervalMillis = 1000;
/* 1060:     */     }
/* 1061:1475 */     return progMonitorPollIntervalMillis;
/* 1062:     */   }
/* 1063:     */   
/* 1064:     */   public static int getCompletionPollInterval(Configuration conf)
/* 1065:     */   {
/* 1066:1480 */     int completionPollIntervalMillis = conf.getInt("mapreduce.client.completion.pollinterval", 5000);
/* 1067:1482 */     if (completionPollIntervalMillis < 1)
/* 1068:     */     {
/* 1069:1483 */       LOG.warn("mapreduce.client.completion.pollinterval has been set to an invalid value; replacing with 5000");
/* 1070:     */       
/* 1071:     */ 
/* 1072:1486 */       completionPollIntervalMillis = 5000;
/* 1073:     */     }
/* 1074:1488 */     return completionPollIntervalMillis;
/* 1075:     */   }
/* 1076:     */   
/* 1077:     */   public static TaskStatusFilter getTaskOutputFilter(Configuration conf)
/* 1078:     */   {
/* 1079:1498 */     return TaskStatusFilter.valueOf(conf.get("mapreduce.client.output.filter", "FAILED"));
/* 1080:     */   }
/* 1081:     */   
/* 1082:     */   public static void setTaskOutputFilter(Configuration conf, TaskStatusFilter newValue)
/* 1083:     */   {
/* 1084:1509 */     conf.set("mapreduce.client.output.filter", newValue.toString());
/* 1085:     */   }
/* 1086:     */   
/* 1087:     */   public boolean isUber()
/* 1088:     */     throws IOException, InterruptedException
/* 1089:     */   {
/* 1090:1513 */     ensureState(JobState.RUNNING);
/* 1091:1514 */     updateStatus();
/* 1092:1515 */     return this.status.isUber();
/* 1093:     */   }
/* 1094:     */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.Job
 * JD-Core Version:    0.7.0.1
 */