/*    1:     */ package org.apache.hadoop.mapred;
/*    2:     */ 
/*    3:     */ import java.io.FileNotFoundException;
/*    4:     */ import java.io.IOException;
/*    5:     */ import java.net.InetSocketAddress;
/*    6:     */ import java.net.URL;
/*    7:     */ import java.security.PrivilegedExceptionAction;
/*    8:     */ import java.util.ArrayList;
/*    9:     */ import java.util.Collection;
/*   10:     */ import java.util.List;
/*   11:     */ import org.apache.hadoop.classification.InterfaceAudience.Private;
/*   12:     */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*   13:     */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*   14:     */ import org.apache.hadoop.conf.Configuration;
/*   15:     */ import org.apache.hadoop.fs.FileStatus;
/*   16:     */ import org.apache.hadoop.fs.FileSystem;
/*   17:     */ import org.apache.hadoop.fs.Path;
/*   18:     */ import org.apache.hadoop.io.Text;
/*   19:     */ import org.apache.hadoop.mapreduce.Cluster;
/*   20:     */ import org.apache.hadoop.mapreduce.ClusterMetrics;
/*   21:     */ import org.apache.hadoop.mapreduce.Job;
/*   22:     */ import org.apache.hadoop.mapreduce.JobPriority;
/*   23:     */ import org.apache.hadoop.mapreduce.JobStatus.State;
/*   24:     */ import org.apache.hadoop.mapreduce.QueueInfo;
/*   25:     */ import org.apache.hadoop.mapreduce.TaskTrackerInfo;
/*   26:     */ import org.apache.hadoop.mapreduce.TaskType;
/*   27:     */ import org.apache.hadoop.mapreduce.security.token.delegation.DelegationTokenIdentifier;
/*   28:     */ import org.apache.hadoop.mapreduce.tools.CLI;
/*   29:     */ import org.apache.hadoop.mapreduce.util.ConfigUtil;
/*   30:     */ import org.apache.hadoop.security.UserGroupInformation;
/*   31:     */ import org.apache.hadoop.security.token.SecretManager.InvalidToken;
/*   32:     */ import org.apache.hadoop.security.token.Token;
/*   33:     */ import org.apache.hadoop.util.ToolRunner;
/*   34:     */ 
/*   35:     */ @InterfaceAudience.Public
/*   36:     */ @InterfaceStability.Stable
/*   37:     */ public class JobClient
/*   38:     */   extends CLI
/*   39:     */ {
/*   40:     */   @InterfaceAudience.Private
/*   41:     */   public static final String MAPREDUCE_CLIENT_RETRY_POLICY_ENABLED_KEY = "mapreduce.jobclient.retry.policy.enabled";
/*   42:     */   @InterfaceAudience.Private
/*   43:     */   public static final boolean MAPREDUCE_CLIENT_RETRY_POLICY_ENABLED_DEFAULT = false;
/*   44:     */   @InterfaceAudience.Private
/*   45:     */   public static final String MAPREDUCE_CLIENT_RETRY_POLICY_SPEC_KEY = "mapreduce.jobclient.retry.policy.spec";
/*   46:     */   @InterfaceAudience.Private
/*   47:     */   public static final String MAPREDUCE_CLIENT_RETRY_POLICY_SPEC_DEFAULT = "10000,6,60000,10";
/*   48:     */   public JobClient() {}
/*   49:     */   
/*   50:     */   public static enum TaskStatusFilter
/*   51:     */   {
/*   52: 154 */     NONE,  KILLED,  FAILED,  SUCCEEDED,  ALL;
/*   53:     */     
/*   54:     */     private TaskStatusFilter() {}
/*   55:     */   }
/*   56:     */   
/*   57: 155 */   private TaskStatusFilter taskOutputFilter = TaskStatusFilter.FAILED;
/*   58:     */   UserGroupInformation clientUgi;
/*   59:     */   
/*   60:     */   static class NetworkedJob
/*   61:     */     implements RunningJob
/*   62:     */   {
/*   63:     */     Job job;
/*   64:     */     
/*   65:     */     public NetworkedJob(JobStatus status, Cluster cluster)
/*   66:     */       throws IOException
/*   67:     */     {
/*   68: 176 */       this(status, cluster, new JobConf(status.getJobFile()));
/*   69:     */     }
/*   70:     */     
/*   71:     */     private NetworkedJob(JobStatus status, Cluster cluster, JobConf conf)
/*   72:     */       throws IOException
/*   73:     */     {
/*   74: 181 */       this(Job.getInstance(cluster, status, conf));
/*   75:     */     }
/*   76:     */     
/*   77:     */     public NetworkedJob(Job job)
/*   78:     */       throws IOException
/*   79:     */     {
/*   80: 185 */       this.job = job;
/*   81:     */     }
/*   82:     */     
/*   83:     */     public Configuration getConfiguration()
/*   84:     */     {
/*   85: 189 */       return this.job.getConfiguration();
/*   86:     */     }
/*   87:     */     
/*   88:     */     public JobID getID()
/*   89:     */     {
/*   90: 196 */       return JobID.downgrade(this.job.getJobID());
/*   91:     */     }
/*   92:     */     
/*   93:     */     @Deprecated
/*   94:     */     public String getJobID()
/*   95:     */     {
/*   96: 203 */       return getID().toString();
/*   97:     */     }
/*   98:     */     
/*   99:     */     public String getJobName()
/*  100:     */     {
/*  101: 210 */       return this.job.getJobName();
/*  102:     */     }
/*  103:     */     
/*  104:     */     public String getJobFile()
/*  105:     */     {
/*  106: 217 */       return this.job.getJobFile();
/*  107:     */     }
/*  108:     */     
/*  109:     */     public String getTrackingURL()
/*  110:     */     {
/*  111: 224 */       return this.job.getTrackingURL();
/*  112:     */     }
/*  113:     */     
/*  114:     */     public float mapProgress()
/*  115:     */       throws IOException
/*  116:     */     {
/*  117: 232 */       return this.job.mapProgress();
/*  118:     */     }
/*  119:     */     
/*  120:     */     public float reduceProgress()
/*  121:     */       throws IOException
/*  122:     */     {
/*  123: 240 */       return this.job.reduceProgress();
/*  124:     */     }
/*  125:     */     
/*  126:     */     public float cleanupProgress()
/*  127:     */       throws IOException
/*  128:     */     {
/*  129:     */       try
/*  130:     */       {
/*  131: 249 */         return this.job.cleanupProgress();
/*  132:     */       }
/*  133:     */       catch (InterruptedException ie)
/*  134:     */       {
/*  135: 251 */         throw new IOException(ie);
/*  136:     */       }
/*  137:     */     }
/*  138:     */     
/*  139:     */     public float setupProgress()
/*  140:     */       throws IOException
/*  141:     */     {
/*  142: 260 */       return this.job.setupProgress();
/*  143:     */     }
/*  144:     */     
/*  145:     */     public synchronized boolean isComplete()
/*  146:     */       throws IOException
/*  147:     */     {
/*  148: 267 */       return this.job.isComplete();
/*  149:     */     }
/*  150:     */     
/*  151:     */     public synchronized boolean isSuccessful()
/*  152:     */       throws IOException
/*  153:     */     {
/*  154: 274 */       return this.job.isSuccessful();
/*  155:     */     }
/*  156:     */     
/*  157:     */     public void waitForCompletion()
/*  158:     */       throws IOException
/*  159:     */     {
/*  160:     */       try
/*  161:     */       {
/*  162: 282 */         this.job.waitForCompletion(false);
/*  163:     */       }
/*  164:     */       catch (InterruptedException ie)
/*  165:     */       {
/*  166: 284 */         throw new IOException(ie);
/*  167:     */       }
/*  168:     */       catch (ClassNotFoundException ce)
/*  169:     */       {
/*  170: 286 */         throw new IOException(ce);
/*  171:     */       }
/*  172:     */     }
/*  173:     */     
/*  174:     */     public synchronized int getJobState()
/*  175:     */       throws IOException
/*  176:     */     {
/*  177:     */       try
/*  178:     */       {
/*  179: 295 */         return this.job.getJobState().getValue();
/*  180:     */       }
/*  181:     */       catch (InterruptedException ie)
/*  182:     */       {
/*  183: 297 */         throw new IOException(ie);
/*  184:     */       }
/*  185:     */     }
/*  186:     */     
/*  187:     */     public synchronized void killJob()
/*  188:     */       throws IOException
/*  189:     */     {
/*  190: 305 */       this.job.killJob();
/*  191:     */     }
/*  192:     */     
/*  193:     */     public synchronized void setJobPriority(String priority)
/*  194:     */       throws IOException
/*  195:     */     {
/*  196:     */       try
/*  197:     */       {
/*  198: 315 */         this.job.setPriority(JobPriority.valueOf(priority));
/*  199:     */       }
/*  200:     */       catch (InterruptedException ie)
/*  201:     */       {
/*  202: 318 */         throw new IOException(ie);
/*  203:     */       }
/*  204:     */     }
/*  205:     */     
/*  206:     */     public synchronized void killTask(TaskAttemptID taskId, boolean shouldFail)
/*  207:     */       throws IOException
/*  208:     */     {
/*  209: 330 */       if (shouldFail) {
/*  210: 331 */         this.job.failTask(taskId);
/*  211:     */       } else {
/*  212: 333 */         this.job.killTask(taskId);
/*  213:     */       }
/*  214:     */     }
/*  215:     */     
/*  216:     */     @Deprecated
/*  217:     */     public synchronized void killTask(String taskId, boolean shouldFail)
/*  218:     */       throws IOException
/*  219:     */     {
/*  220: 340 */       killTask(TaskAttemptID.forName(taskId), shouldFail);
/*  221:     */     }
/*  222:     */     
/*  223:     */     public synchronized TaskCompletionEvent[] getTaskCompletionEvents(int startFrom)
/*  224:     */       throws IOException
/*  225:     */     {
/*  226:     */       try
/*  227:     */       {
/*  228: 349 */         org.apache.hadoop.mapreduce.TaskCompletionEvent[] acls = this.job.getTaskCompletionEvents(startFrom, 10);
/*  229:     */         
/*  230: 351 */         TaskCompletionEvent[] ret = new TaskCompletionEvent[acls.length];
/*  231: 352 */         for (int i = 0; i < acls.length; i++) {
/*  232: 353 */           ret[i] = TaskCompletionEvent.downgrade(acls[i]);
/*  233:     */         }
/*  234: 355 */         return ret;
/*  235:     */       }
/*  236:     */       catch (InterruptedException ie)
/*  237:     */       {
/*  238: 357 */         throw new IOException(ie);
/*  239:     */       }
/*  240:     */     }
/*  241:     */     
/*  242:     */     public String toString()
/*  243:     */     {
/*  244: 366 */       return this.job.toString();
/*  245:     */     }
/*  246:     */     
/*  247:     */     public Counters getCounters()
/*  248:     */       throws IOException
/*  249:     */     {
/*  250: 373 */       Counters result = null;
/*  251: 374 */       org.apache.hadoop.mapreduce.Counters temp = this.job.getCounters();
/*  252: 375 */       if (temp != null) {
/*  253: 376 */         result = Counters.downgrade(temp);
/*  254:     */       }
/*  255: 378 */       return result;
/*  256:     */     }
/*  257:     */     
/*  258:     */     public String[] getTaskDiagnostics(TaskAttemptID id)
/*  259:     */       throws IOException
/*  260:     */     {
/*  261:     */       try
/*  262:     */       {
/*  263: 384 */         return this.job.getTaskDiagnostics(id);
/*  264:     */       }
/*  265:     */       catch (InterruptedException ie)
/*  266:     */       {
/*  267: 386 */         throw new IOException(ie);
/*  268:     */       }
/*  269:     */     }
/*  270:     */     
/*  271:     */     public String getHistoryUrl()
/*  272:     */       throws IOException
/*  273:     */     {
/*  274:     */       try
/*  275:     */       {
/*  276: 392 */         return this.job.getHistoryUrl();
/*  277:     */       }
/*  278:     */       catch (InterruptedException ie)
/*  279:     */       {
/*  280: 394 */         throw new IOException(ie);
/*  281:     */       }
/*  282:     */     }
/*  283:     */     
/*  284:     */     public boolean isRetired()
/*  285:     */       throws IOException
/*  286:     */     {
/*  287:     */       try
/*  288:     */       {
/*  289: 400 */         return this.job.isRetired();
/*  290:     */       }
/*  291:     */       catch (InterruptedException ie)
/*  292:     */       {
/*  293: 402 */         throw new IOException(ie);
/*  294:     */       }
/*  295:     */     }
/*  296:     */     
/*  297:     */     boolean monitorAndPrintJob()
/*  298:     */       throws IOException, InterruptedException
/*  299:     */     {
/*  300: 407 */       return this.job.monitorAndPrintJob();
/*  301:     */     }
/*  302:     */     
/*  303:     */     public String getFailureInfo()
/*  304:     */       throws IOException
/*  305:     */     {
/*  306:     */       try
/*  307:     */       {
/*  308: 413 */         return this.job.getStatus().getFailureInfo();
/*  309:     */       }
/*  310:     */       catch (InterruptedException ie)
/*  311:     */       {
/*  312: 415 */         throw new IOException(ie);
/*  313:     */       }
/*  314:     */     }
/*  315:     */     
/*  316:     */     public JobStatus getJobStatus()
/*  317:     */       throws IOException
/*  318:     */     {
/*  319:     */       try
/*  320:     */       {
/*  321: 422 */         return JobStatus.downgrade(this.job.getStatus());
/*  322:     */       }
/*  323:     */       catch (InterruptedException ie)
/*  324:     */       {
/*  325: 424 */         throw new IOException(ie);
/*  326:     */       }
/*  327:     */     }
/*  328:     */   }
/*  329:     */   
/*  330:     */   public JobClient(JobConf conf)
/*  331:     */     throws IOException
/*  332:     */   {
/*  333: 449 */     init(conf);
/*  334:     */   }
/*  335:     */   
/*  336:     */   public JobClient(Configuration conf)
/*  337:     */     throws IOException
/*  338:     */   {
/*  339: 460 */     init(new JobConf(conf));
/*  340:     */   }
/*  341:     */   
/*  342:     */   public void init(JobConf conf)
/*  343:     */     throws IOException
/*  344:     */   {
/*  345: 469 */     setConf(conf);
/*  346: 470 */     this.cluster = new Cluster(conf);
/*  347: 471 */     this.clientUgi = UserGroupInformation.getCurrentUser();
/*  348:     */   }
/*  349:     */   
/*  350:     */   public JobClient(InetSocketAddress jobTrackAddr, Configuration conf)
/*  351:     */     throws IOException
/*  352:     */   {
/*  353: 482 */     this.cluster = new Cluster(jobTrackAddr, conf);
/*  354: 483 */     this.clientUgi = UserGroupInformation.getCurrentUser();
/*  355:     */   }
/*  356:     */   
/*  357:     */   public synchronized void close()
/*  358:     */     throws IOException
/*  359:     */   {
/*  360: 490 */     this.cluster.close();
/*  361:     */   }
/*  362:     */   
/*  363:     */   public synchronized FileSystem getFs()
/*  364:     */     throws IOException
/*  365:     */   {
/*  366:     */     try
/*  367:     */     {
/*  368: 501 */       return this.cluster.getFileSystem();
/*  369:     */     }
/*  370:     */     catch (InterruptedException ie)
/*  371:     */     {
/*  372: 503 */       throw new IOException(ie);
/*  373:     */     }
/*  374:     */   }
/*  375:     */   
/*  376:     */   public Cluster getClusterHandle()
/*  377:     */   {
/*  378: 511 */     return this.cluster;
/*  379:     */   }
/*  380:     */   
/*  381:     */   public RunningJob submitJob(String jobFile)
/*  382:     */     throws FileNotFoundException, InvalidJobConfException, IOException
/*  383:     */   {
/*  384: 531 */     JobConf job = new JobConf(jobFile);
/*  385: 532 */     return submitJob(job);
/*  386:     */   }
/*  387:     */   
/*  388:     */   public RunningJob submitJob(JobConf conf)
/*  389:     */     throws FileNotFoundException, IOException
/*  390:     */   {
/*  391: 548 */     return submitJobInternal(conf);
/*  392:     */   }
/*  393:     */   
/*  394:     */   @InterfaceAudience.Private
/*  395:     */   public RunningJob submitJobInternal(final JobConf conf)
/*  396:     */     throws FileNotFoundException, IOException
/*  397:     */   {
/*  398:     */     try
/*  399:     */     {
/*  400: 555 */       conf.setBooleanIfUnset("mapred.mapper.new-api", false);
/*  401: 556 */       conf.setBooleanIfUnset("mapred.reducer.new-api", false);
/*  402: 557 */       Job job = (Job)this.clientUgi.doAs(new PrivilegedExceptionAction()
/*  403:     */       {
/*  404:     */         public Job run()
/*  405:     */           throws IOException, ClassNotFoundException, InterruptedException
/*  406:     */         {
/*  407: 561 */           Job job = Job.getInstance(conf);
/*  408: 562 */           job.submit();
/*  409: 563 */           return job;
/*  410:     */         }
/*  411: 568 */       });
/*  412: 569 */       this.cluster = job.getCluster();
/*  413: 570 */       return new NetworkedJob(job);
/*  414:     */     }
/*  415:     */     catch (InterruptedException ie)
/*  416:     */     {
/*  417: 572 */       throw new IOException("interrupted", ie);
/*  418:     */     }
/*  419:     */   }
/*  420:     */   
/*  421:     */   private Job getJobUsingCluster(final JobID jobid)
/*  422:     */     throws IOException, InterruptedException
/*  423:     */   {
/*  424: 578 */     (Job)this.clientUgi.doAs(new PrivilegedExceptionAction()
/*  425:     */     {
/*  426:     */       public Job run()
/*  427:     */         throws IOException, InterruptedException
/*  428:     */       {
/*  429: 580 */         return JobClient.this.cluster.getJob(jobid);
/*  430:     */       }
/*  431:     */     });
/*  432:     */   }
/*  433:     */   
/*  434:     */   public RunningJob getJob(JobID jobid)
/*  435:     */     throws IOException
/*  436:     */   {
/*  437:     */     try
/*  438:     */     {
/*  439: 596 */       Job job = getJobUsingCluster(jobid);
/*  440: 597 */       if (job != null)
/*  441:     */       {
/*  442: 598 */         JobStatus status = JobStatus.downgrade(job.getStatus());
/*  443: 599 */         if (status != null) {
/*  444: 600 */           return new NetworkedJob(status, this.cluster, new JobConf(job.getConfiguration()), null);
/*  445:     */         }
/*  446:     */       }
/*  447:     */     }
/*  448:     */     catch (InterruptedException ie)
/*  449:     */     {
/*  450: 605 */       throw new IOException(ie);
/*  451:     */     }
/*  452: 607 */     return null;
/*  453:     */   }
/*  454:     */   
/*  455:     */   @Deprecated
/*  456:     */   public RunningJob getJob(String jobid)
/*  457:     */     throws IOException
/*  458:     */   {
/*  459: 614 */     return getJob(JobID.forName(jobid));
/*  460:     */   }
/*  461:     */   
/*  462: 617 */   private static final TaskReport[] EMPTY_TASK_REPORTS = new TaskReport[0];
/*  463:     */   
/*  464:     */   public TaskReport[] getMapTaskReports(JobID jobId)
/*  465:     */     throws IOException
/*  466:     */   {
/*  467: 627 */     return getTaskReports(jobId, TaskType.MAP);
/*  468:     */   }
/*  469:     */   
/*  470:     */   private TaskReport[] getTaskReports(JobID jobId, TaskType type)
/*  471:     */     throws IOException
/*  472:     */   {
/*  473:     */     try
/*  474:     */     {
/*  475: 633 */       Job j = getJobUsingCluster(jobId);
/*  476: 634 */       if (j == null) {
/*  477: 635 */         return EMPTY_TASK_REPORTS;
/*  478:     */       }
/*  479: 637 */       return TaskReport.downgradeArray(j.getTaskReports(type));
/*  480:     */     }
/*  481:     */     catch (InterruptedException ie)
/*  482:     */     {
/*  483: 639 */       throw new IOException(ie);
/*  484:     */     }
/*  485:     */   }
/*  486:     */   
/*  487:     */   @Deprecated
/*  488:     */   public TaskReport[] getMapTaskReports(String jobId)
/*  489:     */     throws IOException
/*  490:     */   {
/*  491: 646 */     return getMapTaskReports(JobID.forName(jobId));
/*  492:     */   }
/*  493:     */   
/*  494:     */   public TaskReport[] getReduceTaskReports(JobID jobId)
/*  495:     */     throws IOException
/*  496:     */   {
/*  497: 657 */     return getTaskReports(jobId, TaskType.REDUCE);
/*  498:     */   }
/*  499:     */   
/*  500:     */   public TaskReport[] getCleanupTaskReports(JobID jobId)
/*  501:     */     throws IOException
/*  502:     */   {
/*  503: 668 */     return getTaskReports(jobId, TaskType.JOB_CLEANUP);
/*  504:     */   }
/*  505:     */   
/*  506:     */   public TaskReport[] getSetupTaskReports(JobID jobId)
/*  507:     */     throws IOException
/*  508:     */   {
/*  509: 679 */     return getTaskReports(jobId, TaskType.JOB_SETUP);
/*  510:     */   }
/*  511:     */   
/*  512:     */   @Deprecated
/*  513:     */   public TaskReport[] getReduceTaskReports(String jobId)
/*  514:     */     throws IOException
/*  515:     */   {
/*  516: 686 */     return getReduceTaskReports(JobID.forName(jobId));
/*  517:     */   }
/*  518:     */   
/*  519:     */   public void displayTasks(JobID jobId, String type, String state)
/*  520:     */     throws IOException
/*  521:     */   {
/*  522:     */     try
/*  523:     */     {
/*  524: 701 */       Job job = getJobUsingCluster(jobId);
/*  525: 702 */       super.displayTasks(job, type, state);
/*  526:     */     }
/*  527:     */     catch (InterruptedException ie)
/*  528:     */     {
/*  529: 704 */       throw new IOException(ie);
/*  530:     */     }
/*  531:     */   }
/*  532:     */   
/*  533:     */   public ClusterStatus getClusterStatus()
/*  534:     */     throws IOException
/*  535:     */   {
/*  536:     */     try
/*  537:     */     {
/*  538: 717 */       (ClusterStatus)this.clientUgi.doAs(new PrivilegedExceptionAction()
/*  539:     */       {
/*  540:     */         public ClusterStatus run()
/*  541:     */           throws IOException, InterruptedException
/*  542:     */         {
/*  543: 719 */           ClusterMetrics metrics = JobClient.this.cluster.getClusterStatus();
/*  544: 720 */           return new ClusterStatus(metrics.getTaskTrackerCount(), metrics.getBlackListedTaskTrackerCount(), JobClient.this.cluster.getTaskTrackerExpiryInterval(), metrics.getOccupiedMapSlots(), metrics.getOccupiedReduceSlots(), metrics.getMapSlotCapacity(), metrics.getReduceSlotCapacity(), JobClient.this.cluster.getJobTrackerStatus(), metrics.getDecommissionedTaskTrackerCount(), metrics.getGrayListedTaskTrackerCount());
/*  545:     */         }
/*  546:     */       });
/*  547:     */     }
/*  548:     */     catch (InterruptedException ie)
/*  549:     */     {
/*  550: 730 */       throw new IOException(ie);
/*  551:     */     }
/*  552:     */   }
/*  553:     */   
/*  554:     */   private Collection<String> arrayToStringList(TaskTrackerInfo[] objs)
/*  555:     */   {
/*  556: 735 */     Collection<String> list = new ArrayList();
/*  557: 736 */     for (TaskTrackerInfo info : objs) {
/*  558: 737 */       list.add(info.getTaskTrackerName());
/*  559:     */     }
/*  560: 739 */     return list;
/*  561:     */   }
/*  562:     */   
/*  563:     */   private Collection<ClusterStatus.BlackListInfo> arrayToBlackListInfo(TaskTrackerInfo[] objs)
/*  564:     */   {
/*  565: 743 */     Collection<ClusterStatus.BlackListInfo> list = new ArrayList();
/*  566: 744 */     for (TaskTrackerInfo info : objs)
/*  567:     */     {
/*  568: 745 */       ClusterStatus.BlackListInfo binfo = new ClusterStatus.BlackListInfo();
/*  569: 746 */       binfo.setTrackerName(info.getTaskTrackerName());
/*  570: 747 */       binfo.setReasonForBlackListing(info.getReasonForBlacklist());
/*  571: 748 */       binfo.setBlackListReport(info.getBlacklistReport());
/*  572: 749 */       list.add(binfo);
/*  573:     */     }
/*  574: 751 */     return list;
/*  575:     */   }
/*  576:     */   
/*  577:     */   public ClusterStatus getClusterStatus(boolean detailed)
/*  578:     */     throws IOException
/*  579:     */   {
/*  580:     */     try
/*  581:     */     {
/*  582: 765 */       (ClusterStatus)this.clientUgi.doAs(new PrivilegedExceptionAction()
/*  583:     */       {
/*  584:     */         public ClusterStatus run()
/*  585:     */           throws IOException, InterruptedException
/*  586:     */         {
/*  587: 767 */           ClusterMetrics metrics = JobClient.this.cluster.getClusterStatus();
/*  588: 768 */           return new ClusterStatus(JobClient.this.arrayToStringList(JobClient.this.cluster.getActiveTaskTrackers()), JobClient.this.arrayToBlackListInfo(JobClient.this.cluster.getBlackListedTaskTrackers()), JobClient.this.cluster.getTaskTrackerExpiryInterval(), metrics.getOccupiedMapSlots(), metrics.getOccupiedReduceSlots(), metrics.getMapSlotCapacity(), metrics.getReduceSlotCapacity(), JobClient.this.cluster.getJobTrackerStatus());
/*  589:     */         }
/*  590:     */       });
/*  591:     */     }
/*  592:     */     catch (InterruptedException ie)
/*  593:     */     {
/*  594: 777 */       throw new IOException(ie);
/*  595:     */     }
/*  596:     */   }
/*  597:     */   
/*  598:     */   public JobStatus[] jobsToComplete()
/*  599:     */     throws IOException
/*  600:     */   {
/*  601: 789 */     List<JobStatus> stats = new ArrayList();
/*  602: 790 */     for (JobStatus stat : getAllJobs()) {
/*  603: 791 */       if (!stat.isJobComplete()) {
/*  604: 792 */         stats.add(stat);
/*  605:     */       }
/*  606:     */     }
/*  607: 795 */     return (JobStatus[])stats.toArray(new JobStatus[0]);
/*  608:     */   }
/*  609:     */   
/*  610:     */   public JobStatus[] getAllJobs()
/*  611:     */     throws IOException
/*  612:     */   {
/*  613:     */     try
/*  614:     */     {
/*  615: 806 */       org.apache.hadoop.mapreduce.JobStatus[] jobs = (org.apache.hadoop.mapreduce.JobStatus[])this.clientUgi.doAs(new PrivilegedExceptionAction()
/*  616:     */       {
/*  617:     */         public org.apache.hadoop.mapreduce.JobStatus[] run()
/*  618:     */           throws IOException, InterruptedException
/*  619:     */         {
/*  620: 811 */           return JobClient.this.cluster.getAllJobStatuses();
/*  621:     */         }
/*  622: 813 */       });
/*  623: 814 */       JobStatus[] stats = new JobStatus[jobs.length];
/*  624: 815 */       for (int i = 0; i < jobs.length; i++) {
/*  625: 816 */         stats[i] = JobStatus.downgrade(jobs[i]);
/*  626:     */       }
/*  627: 818 */       return stats;
/*  628:     */     }
/*  629:     */     catch (InterruptedException ie)
/*  630:     */     {
/*  631: 820 */       throw new IOException(ie);
/*  632:     */     }
/*  633:     */   }
/*  634:     */   
/*  635:     */   public static RunningJob runJob(JobConf job)
/*  636:     */     throws IOException
/*  637:     */   {
/*  638: 832 */     JobClient jc = new JobClient(job);
/*  639: 833 */     RunningJob rj = jc.submitJob(job);
/*  640:     */     try
/*  641:     */     {
/*  642: 835 */       if (!jc.monitorAndPrintJob(job, rj)) {
/*  643: 836 */         throw new IOException("Job failed!");
/*  644:     */       }
/*  645:     */     }
/*  646:     */     catch (InterruptedException ie)
/*  647:     */     {
/*  648: 839 */       Thread.currentThread().interrupt();
/*  649:     */     }
/*  650: 841 */     return rj;
/*  651:     */   }
/*  652:     */   
/*  653:     */   public boolean monitorAndPrintJob(JobConf conf, RunningJob job)
/*  654:     */     throws IOException, InterruptedException
/*  655:     */   {
/*  656: 855 */     return ((NetworkedJob)job).monitorAndPrintJob();
/*  657:     */   }
/*  658:     */   
/*  659:     */   static String getTaskLogURL(TaskAttemptID taskId, String baseUrl)
/*  660:     */   {
/*  661: 859 */     return baseUrl + "/tasklog?plaintext=true&attemptid=" + taskId;
/*  662:     */   }
/*  663:     */   
/*  664:     */   static Configuration getConfiguration(String jobTrackerSpec)
/*  665:     */   {
/*  666: 864 */     Configuration conf = new Configuration();
/*  667: 865 */     if (jobTrackerSpec != null) {
/*  668: 866 */       if (jobTrackerSpec.indexOf(":") >= 0)
/*  669:     */       {
/*  670: 867 */         conf.set("mapred.job.tracker", jobTrackerSpec);
/*  671:     */       }
/*  672:     */       else
/*  673:     */       {
/*  674: 869 */         String classpathFile = "hadoop-" + jobTrackerSpec + ".xml";
/*  675: 870 */         URL validate = conf.getResource(classpathFile);
/*  676: 871 */         if (validate == null) {
/*  677: 872 */           throw new RuntimeException(classpathFile + " not found on CLASSPATH");
/*  678:     */         }
/*  679: 874 */         conf.addResource(classpathFile);
/*  680:     */       }
/*  681:     */     }
/*  682: 877 */     return conf;
/*  683:     */   }
/*  684:     */   
/*  685:     */   @Deprecated
/*  686:     */   public void setTaskOutputFilter(TaskStatusFilter newValue)
/*  687:     */   {
/*  688: 887 */     this.taskOutputFilter = newValue;
/*  689:     */   }
/*  690:     */   
/*  691:     */   public static TaskStatusFilter getTaskOutputFilter(JobConf job)
/*  692:     */   {
/*  693: 897 */     return TaskStatusFilter.valueOf(job.get("jobclient.output.filter", "FAILED"));
/*  694:     */   }
/*  695:     */   
/*  696:     */   public static void setTaskOutputFilter(JobConf job, TaskStatusFilter newValue)
/*  697:     */   {
/*  698: 909 */     job.set("jobclient.output.filter", newValue.toString());
/*  699:     */   }
/*  700:     */   
/*  701:     */   @Deprecated
/*  702:     */   public TaskStatusFilter getTaskOutputFilter()
/*  703:     */   {
/*  704: 918 */     return this.taskOutputFilter;
/*  705:     */   }
/*  706:     */   
/*  707:     */   protected long getCounter(org.apache.hadoop.mapreduce.Counters cntrs, String counterGroupName, String counterName)
/*  708:     */     throws IOException
/*  709:     */   {
/*  710: 923 */     Counters counters = Counters.downgrade(cntrs);
/*  711: 924 */     return counters.findCounter(counterGroupName, counterName).getValue();
/*  712:     */   }
/*  713:     */   
/*  714:     */   public int getDefaultMaps()
/*  715:     */     throws IOException
/*  716:     */   {
/*  717:     */     try
/*  718:     */     {
/*  719: 935 */       ((Integer)this.clientUgi.doAs(new PrivilegedExceptionAction()
/*  720:     */       {
/*  721:     */         public Integer run()
/*  722:     */           throws IOException, InterruptedException
/*  723:     */         {
/*  724: 938 */           return Integer.valueOf(JobClient.this.cluster.getClusterStatus().getMapSlotCapacity());
/*  725:     */         }
/*  726:     */       })).intValue();
/*  727:     */     }
/*  728:     */     catch (InterruptedException ie)
/*  729:     */     {
/*  730: 942 */       throw new IOException(ie);
/*  731:     */     }
/*  732:     */   }
/*  733:     */   
/*  734:     */   public int getDefaultReduces()
/*  735:     */     throws IOException
/*  736:     */   {
/*  737:     */     try
/*  738:     */     {
/*  739: 954 */       ((Integer)this.clientUgi.doAs(new PrivilegedExceptionAction()
/*  740:     */       {
/*  741:     */         public Integer run()
/*  742:     */           throws IOException, InterruptedException
/*  743:     */         {
/*  744: 957 */           return Integer.valueOf(JobClient.this.cluster.getClusterStatus().getReduceSlotCapacity());
/*  745:     */         }
/*  746:     */       })).intValue();
/*  747:     */     }
/*  748:     */     catch (InterruptedException ie)
/*  749:     */     {
/*  750: 961 */       throw new IOException(ie);
/*  751:     */     }
/*  752:     */   }
/*  753:     */   
/*  754:     */   public Path getSystemDir()
/*  755:     */   {
/*  756:     */     try
/*  757:     */     {
/*  758: 972 */       (Path)this.clientUgi.doAs(new PrivilegedExceptionAction()
/*  759:     */       {
/*  760:     */         public Path run()
/*  761:     */           throws IOException, InterruptedException
/*  762:     */         {
/*  763: 975 */           return JobClient.this.cluster.getSystemDir();
/*  764:     */         }
/*  765:     */       });
/*  766:     */     }
/*  767:     */     catch (IOException ioe)
/*  768:     */     {
/*  769: 979 */       return null;
/*  770:     */     }
/*  771:     */     catch (InterruptedException ie) {}
/*  772: 981 */     return null;
/*  773:     */   }
/*  774:     */   
/*  775:     */   public static boolean isJobDirValid(Path jobDirPath, FileSystem fs)
/*  776:     */     throws IOException
/*  777:     */   {
/*  778: 991 */     FileStatus[] contents = fs.listStatus(jobDirPath);
/*  779: 992 */     int matchCount = 0;
/*  780: 993 */     if ((contents != null) && (contents.length >= 2))
/*  781:     */     {
/*  782: 994 */       for (FileStatus status : contents)
/*  783:     */       {
/*  784: 995 */         if ("job.xml".equals(status.getPath().getName())) {
/*  785: 996 */           matchCount++;
/*  786:     */         }
/*  787: 998 */         if ("job.split".equals(status.getPath().getName())) {
/*  788: 999 */           matchCount++;
/*  789:     */         }
/*  790:     */       }
/*  791:1002 */       if (matchCount == 2) {
/*  792:1003 */         return true;
/*  793:     */       }
/*  794:     */     }
/*  795:1006 */     return false;
/*  796:     */   }
/*  797:     */   
/*  798:     */   public Path getStagingAreaDir()
/*  799:     */     throws IOException
/*  800:     */   {
/*  801:     */     try
/*  802:     */     {
/*  803:1017 */       (Path)this.clientUgi.doAs(new PrivilegedExceptionAction()
/*  804:     */       {
/*  805:     */         public Path run()
/*  806:     */           throws IOException, InterruptedException
/*  807:     */         {
/*  808:1020 */           return JobClient.this.cluster.getStagingAreaDir();
/*  809:     */         }
/*  810:     */       });
/*  811:     */     }
/*  812:     */     catch (InterruptedException ie)
/*  813:     */     {
/*  814:1025 */       throw new RuntimeException(ie);
/*  815:     */     }
/*  816:     */   }
/*  817:     */   
/*  818:     */   private JobQueueInfo getJobQueueInfo(QueueInfo queue)
/*  819:     */   {
/*  820:1030 */     JobQueueInfo ret = new JobQueueInfo(queue);
/*  821:1032 */     if (queue.getQueueChildren().size() > 0)
/*  822:     */     {
/*  823:1033 */       List<JobQueueInfo> childQueues = new ArrayList(queue.getQueueChildren().size());
/*  824:1035 */       for (QueueInfo child : queue.getQueueChildren()) {
/*  825:1036 */         childQueues.add(getJobQueueInfo(child));
/*  826:     */       }
/*  827:1038 */       ret.setChildren(childQueues);
/*  828:     */     }
/*  829:1040 */     return ret;
/*  830:     */   }
/*  831:     */   
/*  832:     */   private JobQueueInfo[] getJobQueueInfoArray(QueueInfo[] queues)
/*  833:     */     throws IOException
/*  834:     */   {
/*  835:1045 */     JobQueueInfo[] ret = new JobQueueInfo[queues.length];
/*  836:1046 */     for (int i = 0; i < queues.length; i++) {
/*  837:1047 */       ret[i] = getJobQueueInfo(queues[i]);
/*  838:     */     }
/*  839:1049 */     return ret;
/*  840:     */   }
/*  841:     */   
/*  842:     */   public JobQueueInfo[] getRootQueues()
/*  843:     */     throws IOException
/*  844:     */   {
/*  845:     */     try
/*  846:     */     {
/*  847:1061 */       (JobQueueInfo[])this.clientUgi.doAs(new PrivilegedExceptionAction()
/*  848:     */       {
/*  849:     */         public JobQueueInfo[] run()
/*  850:     */           throws IOException, InterruptedException
/*  851:     */         {
/*  852:1063 */           return JobClient.this.getJobQueueInfoArray(JobClient.this.cluster.getRootQueues());
/*  853:     */         }
/*  854:     */       });
/*  855:     */     }
/*  856:     */     catch (InterruptedException ie)
/*  857:     */     {
/*  858:1067 */       throw new IOException(ie);
/*  859:     */     }
/*  860:     */   }
/*  861:     */   
/*  862:     */   public JobQueueInfo[] getChildQueues(final String queueName)
/*  863:     */     throws IOException
/*  864:     */   {
/*  865:     */     try
/*  866:     */     {
/*  867:1081 */       (JobQueueInfo[])this.clientUgi.doAs(new PrivilegedExceptionAction()
/*  868:     */       {
/*  869:     */         public JobQueueInfo[] run()
/*  870:     */           throws IOException, InterruptedException
/*  871:     */         {
/*  872:1083 */           return JobClient.this.getJobQueueInfoArray(JobClient.this.cluster.getChildQueues(queueName));
/*  873:     */         }
/*  874:     */       });
/*  875:     */     }
/*  876:     */     catch (InterruptedException ie)
/*  877:     */     {
/*  878:1087 */       throw new IOException(ie);
/*  879:     */     }
/*  880:     */   }
/*  881:     */   
/*  882:     */   public JobQueueInfo[] getQueues()
/*  883:     */     throws IOException
/*  884:     */   {
/*  885:     */     try
/*  886:     */     {
/*  887:1100 */       (JobQueueInfo[])this.clientUgi.doAs(new PrivilegedExceptionAction()
/*  888:     */       {
/*  889:     */         public JobQueueInfo[] run()
/*  890:     */           throws IOException, InterruptedException
/*  891:     */         {
/*  892:1102 */           return JobClient.this.getJobQueueInfoArray(JobClient.this.cluster.getQueues());
/*  893:     */         }
/*  894:     */       });
/*  895:     */     }
/*  896:     */     catch (InterruptedException ie)
/*  897:     */     {
/*  898:1106 */       throw new IOException(ie);
/*  899:     */     }
/*  900:     */   }
/*  901:     */   
/*  902:     */   public JobStatus[] getJobsFromQueue(final String queueName)
/*  903:     */     throws IOException
/*  904:     */   {
/*  905:     */     try
/*  906:     */     {
/*  907:1120 */       QueueInfo queue = (QueueInfo)this.clientUgi.doAs(new PrivilegedExceptionAction()
/*  908:     */       {
/*  909:     */         public QueueInfo run()
/*  910:     */           throws IOException, InterruptedException
/*  911:     */         {
/*  912:1123 */           return JobClient.this.cluster.getQueue(queueName);
/*  913:     */         }
/*  914:     */       });
/*  915:1126 */       if (queue == null) {
/*  916:1127 */         return null;
/*  917:     */       }
/*  918:1129 */       org.apache.hadoop.mapreduce.JobStatus[] stats = queue.getJobStatuses();
/*  919:     */       
/*  920:1131 */       JobStatus[] ret = new JobStatus[stats.length];
/*  921:1132 */       for (int i = 0; i < stats.length; i++) {
/*  922:1133 */         ret[i] = JobStatus.downgrade(stats[i]);
/*  923:     */       }
/*  924:1135 */       return ret;
/*  925:     */     }
/*  926:     */     catch (InterruptedException ie)
/*  927:     */     {
/*  928:1137 */       throw new IOException(ie);
/*  929:     */     }
/*  930:     */   }
/*  931:     */   
/*  932:     */   public JobQueueInfo getQueueInfo(final String queueName)
/*  933:     */     throws IOException
/*  934:     */   {
/*  935:     */     try
/*  936:     */     {
/*  937:1150 */       QueueInfo queueInfo = (QueueInfo)this.clientUgi.doAs(new PrivilegedExceptionAction()
/*  938:     */       {
/*  939:     */         public QueueInfo run()
/*  940:     */           throws IOException, InterruptedException
/*  941:     */         {
/*  942:1153 */           return JobClient.this.cluster.getQueue(queueName);
/*  943:     */         }
/*  944:     */       });
/*  945:1156 */       if (queueInfo != null) {
/*  946:1157 */         return new JobQueueInfo(queueInfo);
/*  947:     */       }
/*  948:1159 */       return null;
/*  949:     */     }
/*  950:     */     catch (InterruptedException ie)
/*  951:     */     {
/*  952:1161 */       throw new IOException(ie);
/*  953:     */     }
/*  954:     */   }
/*  955:     */   
/*  956:     */   public QueueAclsInfo[] getQueueAclsForCurrentUser()
/*  957:     */     throws IOException
/*  958:     */   {
/*  959:     */     try
/*  960:     */     {
/*  961:1172 */       org.apache.hadoop.mapreduce.QueueAclsInfo[] acls = (org.apache.hadoop.mapreduce.QueueAclsInfo[])this.clientUgi.doAs(new PrivilegedExceptionAction()
/*  962:     */       {
/*  963:     */         public org.apache.hadoop.mapreduce.QueueAclsInfo[] run()
/*  964:     */           throws IOException, InterruptedException
/*  965:     */         {
/*  966:1178 */           return JobClient.this.cluster.getQueueAclsForCurrentUser();
/*  967:     */         }
/*  968:1180 */       });
/*  969:1181 */       QueueAclsInfo[] ret = new QueueAclsInfo[acls.length];
/*  970:1182 */       for (int i = 0; i < acls.length; i++) {
/*  971:1183 */         ret[i] = QueueAclsInfo.downgrade(acls[i]);
/*  972:     */       }
/*  973:1185 */       return ret;
/*  974:     */     }
/*  975:     */     catch (InterruptedException ie)
/*  976:     */     {
/*  977:1187 */       throw new IOException(ie);
/*  978:     */     }
/*  979:     */   }
/*  980:     */   
/*  981:     */   public Token<DelegationTokenIdentifier> getDelegationToken(final Text renewer)
/*  982:     */     throws IOException, InterruptedException
/*  983:     */   {
/*  984:1199 */     (Token)this.clientUgi.doAs(new PrivilegedExceptionAction()
/*  985:     */     {
/*  986:     */       public Token<DelegationTokenIdentifier> run()
/*  987:     */         throws IOException, InterruptedException
/*  988:     */       {
/*  989:1203 */         return JobClient.this.cluster.getDelegationToken(renewer);
/*  990:     */       }
/*  991:     */     });
/*  992:     */   }
/*  993:     */   
/*  994:     */   /**
/*  995:     */    * @deprecated
/*  996:     */    */
/*  997:     */   public long renewDelegationToken(Token<DelegationTokenIdentifier> token)
/*  998:     */     throws SecretManager.InvalidToken, IOException, InterruptedException
/*  999:     */   {
/* 1000:1219 */     return token.renew(getConf());
/* 1001:     */   }
/* 1002:     */   
/* 1003:     */   /**
/* 1004:     */    * @deprecated
/* 1005:     */    */
/* 1006:     */   public void cancelDelegationToken(Token<DelegationTokenIdentifier> token)
/* 1007:     */     throws SecretManager.InvalidToken, IOException, InterruptedException
/* 1008:     */   {
/* 1009:1231 */     token.cancel(getConf());
/* 1010:     */   }
/* 1011:     */   
/* 1012:     */   public static void main(String[] argv)
/* 1013:     */     throws Exception
/* 1014:     */   {
/* 1015:1237 */     int res = ToolRunner.run(new JobClient(), argv);
/* 1016:1238 */     System.exit(res);
/* 1017:     */   }
/* 1018:     */   
/* 1019:     */   static {}
/* 1020:     */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.JobClient
 * JD-Core Version:    0.7.0.1
 */