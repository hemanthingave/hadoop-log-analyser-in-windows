/*   1:    */ package org.apache.hadoop.mapred;
/*   2:    */ 
/*   3:    */ import com.google.common.util.concurrent.ThreadFactoryBuilder;
/*   4:    */ import java.io.ByteArrayInputStream;
/*   5:    */ import java.io.ByteArrayOutputStream;
/*   6:    */ import java.io.DataInputStream;
/*   7:    */ import java.io.DataOutputStream;
/*   8:    */ import java.io.IOException;
/*   9:    */ import java.io.OutputStream;
/*  10:    */ import java.net.URI;
/*  11:    */ import java.net.URL;
/*  12:    */ import java.util.ArrayList;
/*  13:    */ import java.util.Collections;
/*  14:    */ import java.util.HashMap;
/*  15:    */ import java.util.Iterator;
/*  16:    */ import java.util.List;
/*  17:    */ import java.util.Map;
/*  18:    */ import java.util.Random;
/*  19:    */ import java.util.concurrent.ExecutorService;
/*  20:    */ import java.util.concurrent.Executors;
/*  21:    */ import java.util.concurrent.ThreadFactory;
/*  22:    */ import java.util.concurrent.TimeUnit;
/*  23:    */ import java.util.concurrent.atomic.AtomicInteger;
/*  24:    */ import org.apache.commons.logging.Log;
/*  25:    */ import org.apache.commons.logging.LogFactory;
/*  26:    */ import org.apache.hadoop.classification.InterfaceAudience.Private;
/*  27:    */ import org.apache.hadoop.classification.InterfaceStability.Unstable;
/*  28:    */ import org.apache.hadoop.conf.Configuration;
/*  29:    */ import org.apache.hadoop.fs.FileSystem;
/*  30:    */ import org.apache.hadoop.fs.Path;
/*  31:    */ import org.apache.hadoop.io.Text;
/*  32:    */ import org.apache.hadoop.ipc.ProtocolSignature;
/*  33:    */ import org.apache.hadoop.mapreduce.Cluster.JobTrackerStatus;
/*  34:    */ import org.apache.hadoop.mapreduce.ClusterMetrics;
/*  35:    */ import org.apache.hadoop.mapreduce.JobStatus.State;
/*  36:    */ import org.apache.hadoop.mapreduce.OutputFormat;
/*  37:    */ import org.apache.hadoop.mapreduce.QueueAclsInfo;
/*  38:    */ import org.apache.hadoop.mapreduce.QueueInfo;
/*  39:    */ import org.apache.hadoop.mapreduce.TaskAttemptContext;
/*  40:    */ import org.apache.hadoop.mapreduce.TaskReport;
/*  41:    */ import org.apache.hadoop.mapreduce.TaskTrackerInfo;
/*  42:    */ import org.apache.hadoop.mapreduce.TaskType;
/*  43:    */ import org.apache.hadoop.mapreduce.protocol.ClientProtocol;
/*  44:    */ import org.apache.hadoop.mapreduce.security.token.delegation.DelegationTokenIdentifier;
/*  45:    */ import org.apache.hadoop.mapreduce.split.JobSplit.TaskSplitMetaInfo;
/*  46:    */ import org.apache.hadoop.mapreduce.split.SplitMetaInfoReader;
/*  47:    */ import org.apache.hadoop.mapreduce.task.TaskAttemptContextImpl;
/*  48:    */ import org.apache.hadoop.mapreduce.v2.LogParams;
/*  49:    */ import org.apache.hadoop.security.Credentials;
/*  50:    */ import org.apache.hadoop.security.UserGroupInformation;
/*  51:    */ import org.apache.hadoop.security.authorize.AccessControlList;
/*  52:    */ import org.apache.hadoop.security.token.Token;
/*  53:    */ import org.apache.hadoop.util.ReflectionUtils;
/*  54:    */ 
/*  55:    */ @InterfaceAudience.Private
/*  56:    */ @InterfaceStability.Unstable
/*  57:    */ public class LocalJobRunner
/*  58:    */   implements ClientProtocol
/*  59:    */ {
/*  60: 75 */   public static final Log LOG = LogFactory.getLog(LocalJobRunner.class);
/*  61:    */   public static final String LOCAL_MAX_MAPS = "mapreduce.local.map.tasks.maximum";
/*  62:    */   public static final String LOCAL_MAX_REDUCES = "mapreduce.local.reduce.tasks.maximum";
/*  63:    */   private FileSystem fs;
/*  64: 87 */   private HashMap<JobID, Job> jobs = new HashMap();
/*  65:    */   private JobConf conf;
/*  66: 89 */   private AtomicInteger map_tasks = new AtomicInteger(0);
/*  67: 90 */   private AtomicInteger reduce_tasks = new AtomicInteger(0);
/*  68: 91 */   final Random rand = new Random();
/*  69: 93 */   private LocalJobRunnerMetrics myMetrics = null;
/*  70:    */   private static final String jobDir = "localRunner/";
/*  71:    */   
/*  72:    */   public long getProtocolVersion(String protocol, long clientVersion)
/*  73:    */   {
/*  74: 98 */     return 37L;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public ProtocolSignature getProtocolSignature(String protocol, long clientVersion, int clientMethodsHash)
/*  78:    */     throws IOException
/*  79:    */   {
/*  80:104 */     return ProtocolSignature.getProtocolSignature(this, protocol, clientVersion, clientMethodsHash);
/*  81:    */   }
/*  82:    */   
/*  83:    */   private class Job
/*  84:    */     extends Thread
/*  85:    */     implements TaskUmbilicalProtocol
/*  86:    */   {
/*  87:    */     private Path systemJobDir;
/*  88:    */     private Path systemJobFile;
/*  89:    */     private Path localJobDir;
/*  90:    */     private Path localJobFile;
/*  91:    */     private JobID id;
/*  92:    */     private JobConf job;
/*  93:    */     private int numMapTasks;
/*  94:    */     private int numReduceTasks;
/*  95:    */     private float[] partialMapProgress;
/*  96:    */     private float[] partialReduceProgress;
/*  97:    */     private Counters[] mapCounters;
/*  98:    */     private Counters[] reduceCounters;
/*  99:    */     private JobStatus status;
/* 100:129 */     private List<TaskAttemptID> mapIds = Collections.synchronizedList(new ArrayList());
/* 101:    */     private JobProfile profile;
/* 102:    */     private FileSystem localFs;
/* 103:134 */     boolean killed = false;
/* 104:    */     private LocalDistributedCacheManager localDistributedCacheManager;
/* 105:    */     
/* 106:    */     public long getProtocolVersion(String protocol, long clientVersion)
/* 107:    */     {
/* 108:139 */       return 19L;
/* 109:    */     }
/* 110:    */     
/* 111:    */     public ProtocolSignature getProtocolSignature(String protocol, long clientVersion, int clientMethodsHash)
/* 112:    */       throws IOException
/* 113:    */     {
/* 114:145 */       return ProtocolSignature.getProtocolSignature(this, protocol, clientVersion, clientMethodsHash);
/* 115:    */     }
/* 116:    */     
/* 117:    */     public Job(JobID jobid, String jobSubmitDir)
/* 118:    */       throws IOException
/* 119:    */     {
/* 120:150 */       this.systemJobDir = new Path(jobSubmitDir);
/* 121:151 */       this.systemJobFile = new Path(this.systemJobDir, "job.xml");
/* 122:152 */       this.id = jobid;
/* 123:153 */       JobConf conf = new JobConf(this.systemJobFile);
/* 124:154 */       this.localFs = FileSystem.getLocal(conf);
/* 125:155 */       String user = UserGroupInformation.getCurrentUser().getShortUserName();
/* 126:156 */       this.localJobDir = this.localFs.makeQualified(new Path(new Path(conf.getLocalPath("localRunner/"), user), jobid.toString()));
/* 127:    */       
/* 128:158 */       this.localJobFile = new Path(this.localJobDir, this.id + ".xml");
/* 129:    */       
/* 130:    */ 
/* 131:    */ 
/* 132:162 */       this.localDistributedCacheManager = new LocalDistributedCacheManager();
/* 133:163 */       this.localDistributedCacheManager.setup(conf);
/* 134:    */       
/* 135:    */ 
/* 136:    */ 
/* 137:    */ 
/* 138:168 */       OutputStream out = this.localFs.create(this.localJobFile);
/* 139:    */       try
/* 140:    */       {
/* 141:170 */         conf.writeXml(out);
/* 142:    */       }
/* 143:    */       finally
/* 144:    */       {
/* 145:172 */         out.close();
/* 146:    */       }
/* 147:174 */       this.job = new JobConf(this.localJobFile);
/* 148:177 */       if (this.localDistributedCacheManager.hasLocalClasspaths()) {
/* 149:178 */         setContextClassLoader(this.localDistributedCacheManager.makeClassLoader(getContextClassLoader()));
/* 150:    */       }
/* 151:182 */       this.profile = new JobProfile(this.job.getUser(), this.id, this.systemJobFile.toString(), "http://localhost:8080/", this.job.getJobName());
/* 152:    */       
/* 153:184 */       this.status = new JobStatus(this.id, 0.0F, 0.0F, JobStatus.RUNNING, this.profile.getUser(), this.profile.getJobName(), this.profile.getJobFile(), this.profile.getURL().toString());
/* 154:    */       
/* 155:    */ 
/* 156:    */ 
/* 157:188 */       LocalJobRunner.this.jobs.put(this.id, this);
/* 158:    */       
/* 159:190 */       start();
/* 160:    */     }
/* 161:    */     
/* 162:    */     protected abstract class RunnableWithThrowable
/* 163:    */       implements Runnable
/* 164:    */     {
/* 165:    */       public volatile Throwable storedException;
/* 166:    */       
/* 167:    */       protected RunnableWithThrowable() {}
/* 168:    */     }
/* 169:    */     
/* 170:    */     protected class MapTaskRunnable
/* 171:    */       extends LocalJobRunner.Job.RunnableWithThrowable
/* 172:    */     {
/* 173:    */       private final int taskId;
/* 174:    */       private final JobSplit.TaskSplitMetaInfo info;
/* 175:    */       private final JobID jobId;
/* 176:    */       private final JobConf localConf;
/* 177:    */       private final Map<TaskAttemptID, MapOutputFile> mapOutputFiles;
/* 178:    */       
/* 179:    */       public MapTaskRunnable(int info, JobID taskId, Map<TaskAttemptID, MapOutputFile> jobId)
/* 180:    */       {
/* 181:212 */         super();
/* 182:213 */         this.info = info;
/* 183:214 */         this.taskId = taskId;
/* 184:215 */         this.mapOutputFiles = mapOutputFiles;
/* 185:216 */         this.jobId = jobId;
/* 186:217 */         this.localConf = new JobConf(LocalJobRunner.Job.this.job);
/* 187:    */       }
/* 188:    */       
/* 189:    */       public void run()
/* 190:    */       {
/* 191:    */         try
/* 192:    */         {
/* 193:222 */           TaskAttemptID mapId = new TaskAttemptID(new TaskID(this.jobId, TaskType.MAP, this.taskId), 0);
/* 194:    */           
/* 195:224 */           LocalJobRunner.LOG.info("Starting task: " + mapId);
/* 196:225 */           LocalJobRunner.Job.this.mapIds.add(mapId);
/* 197:226 */           MapTask map = new MapTask(LocalJobRunner.Job.this.systemJobFile.toString(), mapId, this.taskId, this.info.getSplitIndex(), 1);
/* 198:    */           
/* 199:228 */           map.setUser(UserGroupInformation.getCurrentUser().getShortUserName());
/* 200:    */           
/* 201:230 */           LocalJobRunner.setupChildMapredLocalDirs(map, this.localConf);
/* 202:    */           
/* 203:232 */           MapOutputFile mapOutput = new MROutputFiles();
/* 204:233 */           mapOutput.setConf(this.localConf);
/* 205:234 */           this.mapOutputFiles.put(mapId, mapOutput);
/* 206:    */           
/* 207:236 */           map.setJobFile(LocalJobRunner.Job.this.localJobFile.toString());
/* 208:237 */           this.localConf.setUser(map.getUser());
/* 209:238 */           map.localizeConfiguration(this.localConf);
/* 210:239 */           map.setConf(this.localConf);
/* 211:    */           try
/* 212:    */           {
/* 213:241 */             LocalJobRunner.this.map_tasks.getAndIncrement();
/* 214:242 */             LocalJobRunner.this.myMetrics.launchMap(mapId);
/* 215:243 */             map.run(this.localConf, LocalJobRunner.Job.this);
/* 216:244 */             LocalJobRunner.this.myMetrics.completeMap(mapId);
/* 217:    */           }
/* 218:    */           finally
/* 219:    */           {
/* 220:246 */             LocalJobRunner.this.map_tasks.getAndDecrement();
/* 221:    */           }
/* 222:249 */           LocalJobRunner.LOG.info("Finishing task: " + mapId);
/* 223:    */         }
/* 224:    */         catch (Throwable e)
/* 225:    */         {
/* 226:251 */           this.storedException = e;
/* 227:    */         }
/* 228:    */       }
/* 229:    */     }
/* 230:    */     
/* 231:    */     protected List<RunnableWithThrowable> getMapTaskRunnables(JobSplit.TaskSplitMetaInfo[] taskInfo, JobID jobId, Map<TaskAttemptID, MapOutputFile> mapOutputFiles)
/* 232:    */     {
/* 233:268 */       int numTasks = 0;
/* 234:269 */       ArrayList<RunnableWithThrowable> list = new ArrayList();
/* 235:271 */       for (JobSplit.TaskSplitMetaInfo task : taskInfo) {
/* 236:272 */         list.add(new MapTaskRunnable(task, numTasks++, jobId, mapOutputFiles));
/* 237:    */       }
/* 238:276 */       return list;
/* 239:    */     }
/* 240:    */     
/* 241:    */     protected class ReduceTaskRunnable
/* 242:    */       extends LocalJobRunner.Job.RunnableWithThrowable
/* 243:    */     {
/* 244:    */       private final int taskId;
/* 245:    */       private final JobID jobId;
/* 246:    */       private final JobConf localConf;
/* 247:    */       private final Map<TaskAttemptID, MapOutputFile> mapOutputFiles;
/* 248:    */       
/* 249:    */       public ReduceTaskRunnable(JobID taskId, Map<TaskAttemptID, MapOutputFile> jobId)
/* 250:    */       {
/* 251:290 */         super();
/* 252:291 */         this.taskId = taskId;
/* 253:292 */         this.jobId = jobId;
/* 254:293 */         this.mapOutputFiles = mapOutputFiles;
/* 255:294 */         this.localConf = new JobConf(LocalJobRunner.Job.this.job);
/* 256:295 */         this.localConf.set("mapreduce.jobtracker.address", "local");
/* 257:    */       }
/* 258:    */       
/* 259:    */       public void run()
/* 260:    */       {
/* 261:    */         try
/* 262:    */         {
/* 263:300 */           TaskAttemptID reduceId = new TaskAttemptID(new TaskID(this.jobId, TaskType.REDUCE, this.taskId), 0);
/* 264:    */           
/* 265:302 */           LocalJobRunner.LOG.info("Starting task: " + reduceId);
/* 266:    */           
/* 267:304 */           ReduceTask reduce = new ReduceTask(LocalJobRunner.Job.this.systemJobFile.toString(), reduceId, this.taskId, LocalJobRunner.Job.this.mapIds.size(), 1);
/* 268:    */           
/* 269:306 */           reduce.setUser(UserGroupInformation.getCurrentUser().getShortUserName());
/* 270:    */           
/* 271:308 */           LocalJobRunner.setupChildMapredLocalDirs(reduce, this.localConf);
/* 272:309 */           reduce.setLocalMapFiles(this.mapOutputFiles);
/* 273:311 */           if (!LocalJobRunner.Job.this.isInterrupted())
/* 274:    */           {
/* 275:312 */             reduce.setJobFile(LocalJobRunner.Job.this.localJobFile.toString());
/* 276:313 */             this.localConf.setUser(reduce.getUser());
/* 277:314 */             reduce.localizeConfiguration(this.localConf);
/* 278:315 */             reduce.setConf(this.localConf);
/* 279:    */             try
/* 280:    */             {
/* 281:317 */               LocalJobRunner.this.reduce_tasks.getAndIncrement();
/* 282:318 */               LocalJobRunner.this.myMetrics.launchReduce(reduce.getTaskID());
/* 283:319 */               reduce.run(this.localConf, LocalJobRunner.Job.this);
/* 284:320 */               LocalJobRunner.this.myMetrics.completeReduce(reduce.getTaskID());
/* 285:    */             }
/* 286:    */             finally
/* 287:    */             {
/* 288:322 */               LocalJobRunner.this.reduce_tasks.getAndDecrement();
/* 289:    */             }
/* 290:325 */             LocalJobRunner.LOG.info("Finishing task: " + reduceId);
/* 291:    */           }
/* 292:    */           else
/* 293:    */           {
/* 294:327 */             throw new InterruptedException();
/* 295:    */           }
/* 296:    */         }
/* 297:    */         catch (Throwable t)
/* 298:    */         {
/* 299:331 */           this.storedException = t;
/* 300:    */         }
/* 301:    */       }
/* 302:    */     }
/* 303:    */     
/* 304:    */     protected List<RunnableWithThrowable> getReduceTaskRunnables(JobID jobId, Map<TaskAttemptID, MapOutputFile> mapOutputFiles)
/* 305:    */     {
/* 306:346 */       int taskId = 0;
/* 307:347 */       ArrayList<RunnableWithThrowable> list = new ArrayList();
/* 308:349 */       for (int i = 0; i < this.numReduceTasks; i++) {
/* 309:350 */         list.add(new ReduceTaskRunnable(taskId++, jobId, mapOutputFiles));
/* 310:    */       }
/* 311:353 */       return list;
/* 312:    */     }
/* 313:    */     
/* 314:    */     private synchronized void initCounters(int numMaps, int numReduces)
/* 315:    */     {
/* 316:363 */       this.partialMapProgress = new float[numMaps];
/* 317:364 */       this.mapCounters = new Counters[numMaps];
/* 318:365 */       for (int i = 0; i < numMaps; i++) {
/* 319:366 */         this.mapCounters[i] = new Counters();
/* 320:    */       }
/* 321:369 */       this.partialReduceProgress = new float[numReduces];
/* 322:370 */       this.reduceCounters = new Counters[numReduces];
/* 323:371 */       for (int i = 0; i < numReduces; i++) {
/* 324:372 */         this.reduceCounters[i] = new Counters();
/* 325:    */       }
/* 326:375 */       this.numMapTasks = numMaps;
/* 327:376 */       this.numReduceTasks = numReduces;
/* 328:    */     }
/* 329:    */     
/* 330:    */     protected synchronized ExecutorService createMapExecutor()
/* 331:    */     {
/* 332:387 */       int maxMapThreads = this.job.getInt("mapreduce.local.map.tasks.maximum", 1);
/* 333:388 */       if (maxMapThreads < 1) {
/* 334:389 */         throw new IllegalArgumentException("Configured mapreduce.local.map.tasks.maximum must be >= 1");
/* 335:    */       }
/* 336:392 */       maxMapThreads = Math.min(maxMapThreads, this.numMapTasks);
/* 337:393 */       maxMapThreads = Math.max(maxMapThreads, 1);
/* 338:    */       
/* 339:395 */       LocalJobRunner.LOG.debug("Starting mapper thread pool executor.");
/* 340:396 */       LocalJobRunner.LOG.debug("Max local threads: " + maxMapThreads);
/* 341:397 */       LocalJobRunner.LOG.debug("Map tasks to process: " + this.numMapTasks);
/* 342:    */       
/* 343:    */ 
/* 344:400 */       ThreadFactory tf = new ThreadFactoryBuilder().setNameFormat("LocalJobRunner Map Task Executor #%d").build();
/* 345:    */       
/* 346:    */ 
/* 347:403 */       ExecutorService executor = Executors.newFixedThreadPool(maxMapThreads, tf);
/* 348:    */       
/* 349:405 */       return executor;
/* 350:    */     }
/* 351:    */     
/* 352:    */     protected synchronized ExecutorService createReduceExecutor()
/* 353:    */     {
/* 354:416 */       int maxReduceThreads = this.job.getInt("mapreduce.local.reduce.tasks.maximum", 1);
/* 355:417 */       if (maxReduceThreads < 1) {
/* 356:418 */         throw new IllegalArgumentException("Configured mapreduce.local.reduce.tasks.maximum must be >= 1");
/* 357:    */       }
/* 358:421 */       maxReduceThreads = Math.min(maxReduceThreads, this.numReduceTasks);
/* 359:422 */       maxReduceThreads = Math.max(maxReduceThreads, 1);
/* 360:    */       
/* 361:424 */       LocalJobRunner.LOG.debug("Starting reduce thread pool executor.");
/* 362:425 */       LocalJobRunner.LOG.debug("Max local threads: " + maxReduceThreads);
/* 363:426 */       LocalJobRunner.LOG.debug("Reduce tasks to process: " + this.numReduceTasks);
/* 364:    */       
/* 365:    */ 
/* 366:429 */       ExecutorService executor = Executors.newFixedThreadPool(maxReduceThreads);
/* 367:    */       
/* 368:431 */       return executor;
/* 369:    */     }
/* 370:    */     
/* 371:    */     private void runTasks(List<RunnableWithThrowable> runnables, ExecutorService service, String taskType)
/* 372:    */       throws Exception
/* 373:    */     {
/* 374:439 */       for (Runnable r : runnables) {
/* 375:440 */         service.submit(r);
/* 376:    */       }
/* 377:    */       try
/* 378:    */       {
/* 379:444 */         service.shutdown();
/* 380:    */         
/* 381:    */ 
/* 382:    */ 
/* 383:448 */         LocalJobRunner.LOG.info("Waiting for " + taskType + " tasks");
/* 384:449 */         service.awaitTermination(9223372036854775807L, TimeUnit.NANOSECONDS);
/* 385:    */       }
/* 386:    */       catch (InterruptedException ie)
/* 387:    */       {
/* 388:452 */         service.shutdownNow();
/* 389:453 */         throw ie;
/* 390:    */       }
/* 391:456 */       LocalJobRunner.LOG.info(taskType + " task executor complete.");
/* 392:460 */       for (RunnableWithThrowable r : runnables) {
/* 393:461 */         if (r.storedException != null) {
/* 394:462 */           throw new Exception(r.storedException);
/* 395:    */         }
/* 396:    */       }
/* 397:    */     }
/* 398:    */     
/* 399:    */     private org.apache.hadoop.mapreduce.OutputCommitter createOutputCommitter(boolean newApiCommitter, JobID jobId, Configuration conf)
/* 400:    */       throws Exception
/* 401:    */     {
/* 402:469 */       org.apache.hadoop.mapreduce.OutputCommitter committer = null;
/* 403:    */       
/* 404:471 */       LocalJobRunner.LOG.info("OutputCommitter set in config " + conf.get("mapred.output.committer.class"));
/* 405:474 */       if (newApiCommitter)
/* 406:    */       {
/* 407:475 */         org.apache.hadoop.mapreduce.TaskID taskId = new org.apache.hadoop.mapreduce.TaskID(jobId, TaskType.MAP, 0);
/* 408:    */         
/* 409:477 */         org.apache.hadoop.mapreduce.TaskAttemptID taskAttemptID = new org.apache.hadoop.mapreduce.TaskAttemptID(taskId, 0);
/* 410:    */         
/* 411:479 */         TaskAttemptContext taskContext = new TaskAttemptContextImpl(conf, taskAttemptID);
/* 412:    */         
/* 413:481 */         OutputFormat outputFormat = (OutputFormat)ReflectionUtils.newInstance(taskContext.getOutputFormatClass(), conf);
/* 414:    */         
/* 415:483 */         committer = outputFormat.getOutputCommitter(taskContext);
/* 416:    */       }
/* 417:    */       else
/* 418:    */       {
/* 419:485 */         committer = (org.apache.hadoop.mapreduce.OutputCommitter)ReflectionUtils.newInstance(conf.getClass("mapred.output.committer.class", FileOutputCommitter.class, OutputCommitter.class), conf);
/* 420:    */       }
/* 421:489 */       LocalJobRunner.LOG.info("OutputCommitter is " + committer.getClass().getName());
/* 422:490 */       return committer;
/* 423:    */     }
/* 424:    */     
/* 425:    */     public void run()
/* 426:    */     {
/* 427:495 */       JobID jobId = this.profile.getJobID();
/* 428:496 */       JobContext jContext = new JobContextImpl(this.job, jobId);
/* 429:    */       
/* 430:498 */       org.apache.hadoop.mapreduce.OutputCommitter outputCommitter = null;
/* 431:    */       try
/* 432:    */       {
/* 433:500 */         outputCommitter = createOutputCommitter(LocalJobRunner.this.conf.getUseNewMapper(), jobId, LocalJobRunner.this.conf);
/* 434:    */       }
/* 435:    */       catch (Exception e)
/* 436:    */       {
/* 437:502 */         LocalJobRunner.LOG.info("Failed to createOutputCommitter", e);
/* 438:503 */         return;
/* 439:    */       }
/* 440:    */       try
/* 441:    */       {
/* 442:507 */         JobSplit.TaskSplitMetaInfo[] taskSplitMetaInfos = SplitMetaInfoReader.readSplitMetaInfo(jobId, this.localFs, LocalJobRunner.this.conf, this.systemJobDir);
/* 443:    */         
/* 444:    */ 
/* 445:510 */         int numReduceTasks = this.job.getNumReduceTasks();
/* 446:511 */         outputCommitter.setupJob(jContext);
/* 447:512 */         this.status.setSetupProgress(1.0F);
/* 448:    */         
/* 449:514 */         Map<TaskAttemptID, MapOutputFile> mapOutputFiles = Collections.synchronizedMap(new HashMap());
/* 450:    */         
/* 451:    */ 
/* 452:517 */         List<RunnableWithThrowable> mapRunnables = getMapTaskRunnables(taskSplitMetaInfos, jobId, mapOutputFiles);
/* 453:    */         
/* 454:    */ 
/* 455:520 */         initCounters(mapRunnables.size(), numReduceTasks);
/* 456:521 */         ExecutorService mapService = createMapExecutor();
/* 457:522 */         runTasks(mapRunnables, mapService, "map");
/* 458:    */         try
/* 459:    */         {
/* 460:525 */           if (numReduceTasks > 0)
/* 461:    */           {
/* 462:526 */             List<RunnableWithThrowable> reduceRunnables = getReduceTaskRunnables(jobId, mapOutputFiles);
/* 463:    */             
/* 464:528 */             ExecutorService reduceService = createReduceExecutor();
/* 465:529 */             runTasks(reduceRunnables, reduceService, "reduce");
/* 466:    */           }
/* 467:    */         }
/* 468:    */         finally
/* 469:    */         {
/* 470:    */           Iterator i$;
/* 471:    */           MapOutputFile output;
/* 472:532 */           for (MapOutputFile output : mapOutputFiles.values()) {
/* 473:533 */             output.removeAll();
/* 474:    */           }
/* 475:    */         }
/* 476:537 */         outputCommitter.commitJob(jContext);
/* 477:538 */         this.status.setCleanupProgress(1.0F);
/* 478:540 */         if (this.killed) {
/* 479:541 */           this.status.setRunState(JobStatus.KILLED);
/* 480:    */         } else {
/* 481:543 */           this.status.setRunState(JobStatus.SUCCEEDED);
/* 482:    */         }
/* 483:546 */         JobEndNotifier.localRunnerNotification(this.job, this.status); return;
/* 484:    */       }
/* 485:    */       catch (Throwable t)
/* 486:    */       {
/* 487:    */         try
/* 488:    */         {
/* 489:549 */           outputCommitter.abortJob(jContext, JobStatus.State.FAILED);
/* 490:    */         }
/* 491:    */         catch (IOException ioe)
/* 492:    */         {
/* 493:552 */           LocalJobRunner.LOG.info("Error cleaning up job:" + this.id);
/* 494:    */         }
/* 495:554 */         this.status.setCleanupProgress(1.0F);
/* 496:555 */         if (this.killed) {
/* 497:556 */           this.status.setRunState(JobStatus.KILLED);
/* 498:    */         } else {
/* 499:558 */           this.status.setRunState(JobStatus.FAILED);
/* 500:    */         }
/* 501:560 */         LocalJobRunner.LOG.warn(this.id, t);
/* 502:    */         
/* 503:562 */         JobEndNotifier.localRunnerNotification(this.job, this.status);
/* 504:    */       }
/* 505:    */       finally
/* 506:    */       {
/* 507:    */         try
/* 508:    */         {
/* 509:566 */           LocalJobRunner.this.fs.delete(this.systemJobFile.getParent(), true);
/* 510:567 */           this.localFs.delete(this.localJobFile, true);
/* 511:    */           
/* 512:569 */           this.localDistributedCacheManager.close();
/* 513:    */         }
/* 514:    */         catch (IOException e)
/* 515:    */         {
/* 516:571 */           LocalJobRunner.LOG.warn("Error cleaning up " + this.id + ": " + e);
/* 517:    */         }
/* 518:    */       }
/* 519:    */     }
/* 520:    */     
/* 521:    */     public JvmTask getTask(JvmContext context)
/* 522:    */     {
/* 523:578 */       return null;
/* 524:    */     }
/* 525:    */     
/* 526:    */     public synchronized boolean statusUpdate(TaskAttemptID taskId, TaskStatus taskStatus)
/* 527:    */       throws IOException, InterruptedException
/* 528:    */     {
/* 529:583 */       ByteArrayOutputStream baos = new ByteArrayOutputStream();
/* 530:584 */       DataOutputStream dos = new DataOutputStream(baos);
/* 531:585 */       taskStatus.write(dos);
/* 532:586 */       dos.close();
/* 533:587 */       taskStatus = TaskStatus.createTaskStatus(taskStatus.getIsMap());
/* 534:588 */       taskStatus.readFields(new DataInputStream(new ByteArrayInputStream(baos.toByteArray())));
/* 535:    */       
/* 536:    */ 
/* 537:591 */       LocalJobRunner.LOG.info(taskStatus.getStateString());
/* 538:592 */       int mapTaskIndex = this.mapIds.indexOf(taskId);
/* 539:593 */       if (mapTaskIndex >= 0)
/* 540:    */       {
/* 541:595 */         float numTasks = this.numMapTasks;
/* 542:    */         
/* 543:597 */         this.partialMapProgress[mapTaskIndex] = taskStatus.getProgress();
/* 544:598 */         this.mapCounters[mapTaskIndex] = taskStatus.getCounters();
/* 545:    */         
/* 546:600 */         float partialProgress = 0.0F;
/* 547:601 */         for (float f : this.partialMapProgress) {
/* 548:602 */           partialProgress += f;
/* 549:    */         }
/* 550:604 */         this.status.setMapProgress(partialProgress / numTasks);
/* 551:    */       }
/* 552:    */       else
/* 553:    */       {
/* 554:607 */         int reduceTaskIndex = taskId.getTaskID().getId();
/* 555:608 */         float numTasks = this.numReduceTasks;
/* 556:    */         
/* 557:610 */         this.partialReduceProgress[reduceTaskIndex] = taskStatus.getProgress();
/* 558:611 */         this.reduceCounters[reduceTaskIndex] = taskStatus.getCounters();
/* 559:    */         
/* 560:613 */         float partialProgress = 0.0F;
/* 561:614 */         for (float f : this.partialReduceProgress) {
/* 562:615 */           partialProgress += f;
/* 563:    */         }
/* 564:617 */         this.status.setReduceProgress(partialProgress / numTasks);
/* 565:    */       }
/* 566:621 */       return true;
/* 567:    */     }
/* 568:    */     
/* 569:    */     public synchronized Counters getCurrentCounters()
/* 570:    */     {
/* 571:628 */       if (null == this.mapCounters) {
/* 572:630 */         return new Counters();
/* 573:    */       }
/* 574:633 */       Counters current = new Counters();
/* 575:634 */       for (Counters c : this.mapCounters) {
/* 576:635 */         current = Counters.sum(current, c);
/* 577:    */       }
/* 578:638 */       if ((null != this.reduceCounters) && (this.reduceCounters.length > 0)) {
/* 579:639 */         for (Counters c : this.reduceCounters) {
/* 580:640 */           current = Counters.sum(current, c);
/* 581:    */         }
/* 582:    */       }
/* 583:644 */       return current;
/* 584:    */     }
/* 585:    */     
/* 586:    */     public void commitPending(TaskAttemptID taskid, TaskStatus taskStatus)
/* 587:    */       throws IOException, InterruptedException
/* 588:    */     {
/* 589:654 */       statusUpdate(taskid, taskStatus);
/* 590:    */     }
/* 591:    */     
/* 592:    */     public void reportDiagnosticInfo(TaskAttemptID taskid, String trace) {}
/* 593:    */     
/* 594:    */     public void reportNextRecordRange(TaskAttemptID taskid, SortedRanges.Range range)
/* 595:    */       throws IOException
/* 596:    */     {
/* 597:663 */       LocalJobRunner.LOG.info("Task " + taskid + " reportedNextRecordRange " + range);
/* 598:    */     }
/* 599:    */     
/* 600:    */     public boolean ping(TaskAttemptID taskid)
/* 601:    */       throws IOException
/* 602:    */     {
/* 603:667 */       return true;
/* 604:    */     }
/* 605:    */     
/* 606:    */     public boolean canCommit(TaskAttemptID taskid)
/* 607:    */       throws IOException
/* 608:    */     {
/* 609:672 */       return true;
/* 610:    */     }
/* 611:    */     
/* 612:    */     public void done(TaskAttemptID taskId)
/* 613:    */       throws IOException
/* 614:    */     {
/* 615:676 */       int taskIndex = this.mapIds.indexOf(taskId);
/* 616:677 */       if (taskIndex >= 0) {
/* 617:678 */         this.status.setMapProgress(1.0F);
/* 618:    */       } else {
/* 619:680 */         this.status.setReduceProgress(1.0F);
/* 620:    */       }
/* 621:    */     }
/* 622:    */     
/* 623:    */     public synchronized void fsError(TaskAttemptID taskId, String message)
/* 624:    */       throws IOException
/* 625:    */     {
/* 626:686 */       LocalJobRunner.LOG.fatal("FSError: " + message + "from task: " + taskId);
/* 627:    */     }
/* 628:    */     
/* 629:    */     public void shuffleError(TaskAttemptID taskId, String message)
/* 630:    */       throws IOException
/* 631:    */     {
/* 632:690 */       LocalJobRunner.LOG.fatal("shuffleError: " + message + "from task: " + taskId);
/* 633:    */     }
/* 634:    */     
/* 635:    */     public synchronized void fatalError(TaskAttemptID taskId, String msg)
/* 636:    */       throws IOException
/* 637:    */     {
/* 638:695 */       LocalJobRunner.LOG.fatal("Fatal: " + msg + "from task: " + taskId);
/* 639:    */     }
/* 640:    */     
/* 641:    */     public MapTaskCompletionEventsUpdate getMapCompletionEvents(JobID jobId, int fromEventId, int maxLocs, TaskAttemptID id)
/* 642:    */       throws IOException
/* 643:    */     {
/* 644:700 */       return new MapTaskCompletionEventsUpdate(TaskCompletionEvent.EMPTY_ARRAY, false);
/* 645:    */     }
/* 646:    */   }
/* 647:    */   
/* 648:    */   public LocalJobRunner(Configuration conf)
/* 649:    */     throws IOException
/* 650:    */   {
/* 651:707 */     this(new JobConf(conf));
/* 652:    */   }
/* 653:    */   
/* 654:    */   @Deprecated
/* 655:    */   public LocalJobRunner(JobConf conf)
/* 656:    */     throws IOException
/* 657:    */   {
/* 658:712 */     this.fs = FileSystem.getLocal(conf);
/* 659:713 */     this.conf = conf;
/* 660:714 */     this.myMetrics = new LocalJobRunnerMetrics(new JobConf(conf));
/* 661:    */   }
/* 662:    */   
/* 663:719 */   private static int jobid = 0;
/* 664:    */   private int randid;
/* 665:    */   static final String TASK_CLEANUP_SUFFIX = ".cleanup";
/* 666:    */   static final String JOBCACHE = "jobcache";
/* 667:    */   
/* 668:    */   public synchronized org.apache.hadoop.mapreduce.JobID getNewJobID()
/* 669:    */   {
/* 670:725 */     return new org.apache.hadoop.mapreduce.JobID("local" + this.randid, ++jobid);
/* 671:    */   }
/* 672:    */   
/* 673:    */   public org.apache.hadoop.mapreduce.JobStatus submitJob(org.apache.hadoop.mapreduce.JobID jobid, String jobSubmitDir, Credentials credentials)
/* 674:    */     throws IOException
/* 675:    */   {
/* 676:731 */     Job job = new Job(JobID.downgrade(jobid), jobSubmitDir);
/* 677:732 */     job.job.setCredentials(credentials);
/* 678:733 */     return job.status;
/* 679:    */   }
/* 680:    */   
/* 681:    */   public void killJob(org.apache.hadoop.mapreduce.JobID id)
/* 682:    */   {
/* 683:738 */     ((Job)this.jobs.get(JobID.downgrade(id))).killed = true;
/* 684:739 */     ((Job)this.jobs.get(JobID.downgrade(id))).interrupt();
/* 685:    */   }
/* 686:    */   
/* 687:    */   public void setJobPriority(org.apache.hadoop.mapreduce.JobID id, String jp)
/* 688:    */     throws IOException
/* 689:    */   {
/* 690:744 */     throw new UnsupportedOperationException("Changing job priority in LocalJobRunner is not supported.");
/* 691:    */   }
/* 692:    */   
/* 693:    */   public boolean killTask(org.apache.hadoop.mapreduce.TaskAttemptID taskId, boolean shouldFail)
/* 694:    */     throws IOException
/* 695:    */   {
/* 696:751 */     throw new UnsupportedOperationException("Killing tasks in LocalJobRunner is not supported");
/* 697:    */   }
/* 698:    */   
/* 699:    */   public TaskReport[] getTaskReports(org.apache.hadoop.mapreduce.JobID id, TaskType type)
/* 700:    */   {
/* 701:757 */     return new TaskReport[0];
/* 702:    */   }
/* 703:    */   
/* 704:    */   public org.apache.hadoop.mapreduce.JobStatus getJobStatus(org.apache.hadoop.mapreduce.JobID id)
/* 705:    */   {
/* 706:762 */     Job job = (Job)this.jobs.get(JobID.downgrade(id));
/* 707:763 */     if (job != null) {
/* 708:764 */       return job.status;
/* 709:    */     }
/* 710:766 */     return null;
/* 711:    */   }
/* 712:    */   
/* 713:    */   public org.apache.hadoop.mapreduce.Counters getJobCounters(org.apache.hadoop.mapreduce.JobID id)
/* 714:    */   {
/* 715:771 */     Job job = (Job)this.jobs.get(JobID.downgrade(id));
/* 716:    */     
/* 717:773 */     return new org.apache.hadoop.mapreduce.Counters(job.getCurrentCounters());
/* 718:    */   }
/* 719:    */   
/* 720:    */   public String getFilesystemName()
/* 721:    */     throws IOException
/* 722:    */   {
/* 723:777 */     return this.fs.getUri().toString();
/* 724:    */   }
/* 725:    */   
/* 726:    */   public ClusterMetrics getClusterMetrics()
/* 727:    */   {
/* 728:781 */     int numMapTasks = this.map_tasks.get();
/* 729:782 */     int numReduceTasks = this.reduce_tasks.get();
/* 730:783 */     return new ClusterMetrics(numMapTasks, numReduceTasks, numMapTasks, numReduceTasks, 0, 0, 1, 1, this.jobs.size(), 1, 0, 0);
/* 731:    */   }
/* 732:    */   
/* 733:    */   public Cluster.JobTrackerStatus getJobTrackerStatus()
/* 734:    */   {
/* 735:788 */     return Cluster.JobTrackerStatus.RUNNING;
/* 736:    */   }
/* 737:    */   
/* 738:    */   public long getTaskTrackerExpiryInterval()
/* 739:    */     throws IOException, InterruptedException
/* 740:    */   {
/* 741:792 */     return 0L;
/* 742:    */   }
/* 743:    */   
/* 744:    */   public TaskTrackerInfo[] getActiveTrackers()
/* 745:    */     throws IOException, InterruptedException
/* 746:    */   {
/* 747:801 */     return new TaskTrackerInfo[0];
/* 748:    */   }
/* 749:    */   
/* 750:    */   public TaskTrackerInfo[] getBlacklistedTrackers()
/* 751:    */     throws IOException, InterruptedException
/* 752:    */   {
/* 753:810 */     return new TaskTrackerInfo[0];
/* 754:    */   }
/* 755:    */   
/* 756:    */   public org.apache.hadoop.mapreduce.TaskCompletionEvent[] getTaskCompletionEvents(org.apache.hadoop.mapreduce.JobID jobid, int fromEventId, int maxEvents)
/* 757:    */     throws IOException
/* 758:    */   {
/* 759:816 */     return org.apache.hadoop.mapreduce.TaskCompletionEvent.EMPTY_ARRAY;
/* 760:    */   }
/* 761:    */   
/* 762:    */   public org.apache.hadoop.mapreduce.JobStatus[] getAllJobs()
/* 763:    */   {
/* 764:819 */     return null;
/* 765:    */   }
/* 766:    */   
/* 767:    */   public String[] getTaskDiagnostics(org.apache.hadoop.mapreduce.TaskAttemptID taskid)
/* 768:    */     throws IOException
/* 769:    */   {
/* 770:828 */     return new String[0];
/* 771:    */   }
/* 772:    */   
/* 773:    */   public String getSystemDir()
/* 774:    */   {
/* 775:835 */     Path sysDir = new Path(this.conf.get("mapreduce.jobtracker.system.dir", "/tmp/hadoop/mapred/system"));
/* 776:    */     
/* 777:837 */     return this.fs.makeQualified(sysDir).toString();
/* 778:    */   }
/* 779:    */   
/* 780:    */   public AccessControlList getQueueAdmins(String queueName)
/* 781:    */     throws IOException
/* 782:    */   {
/* 783:844 */     return new AccessControlList(" ");
/* 784:    */   }
/* 785:    */   
/* 786:    */   public String getStagingAreaDir()
/* 787:    */     throws IOException
/* 788:    */   {
/* 789:851 */     Path stagingRootDir = new Path(this.conf.get("mapreduce.jobtracker.staging.root.dir", "/tmp/hadoop/mapred/staging"));
/* 790:    */     
/* 791:853 */     UserGroupInformation ugi = UserGroupInformation.getCurrentUser();
/* 792:    */     
/* 793:855 */     this.randid = this.rand.nextInt(2147483647);
/* 794:    */     String user;
/* 795:    */     String user;
/* 796:856 */     if (ugi != null) {
/* 797:857 */       user = ugi.getShortUserName() + this.randid;
/* 798:    */     } else {
/* 799:859 */       user = "dummy" + this.randid;
/* 800:    */     }
/* 801:861 */     return this.fs.makeQualified(new Path(stagingRootDir, user + "/.staging")).toString();
/* 802:    */   }
/* 803:    */   
/* 804:    */   public String getJobHistoryDir()
/* 805:    */   {
/* 806:865 */     return null;
/* 807:    */   }
/* 808:    */   
/* 809:    */   public QueueInfo[] getChildQueues(String queueName)
/* 810:    */     throws IOException
/* 811:    */   {
/* 812:870 */     return null;
/* 813:    */   }
/* 814:    */   
/* 815:    */   public QueueInfo[] getRootQueues()
/* 816:    */     throws IOException
/* 817:    */   {
/* 818:875 */     return null;
/* 819:    */   }
/* 820:    */   
/* 821:    */   public QueueInfo[] getQueues()
/* 822:    */     throws IOException
/* 823:    */   {
/* 824:880 */     return null;
/* 825:    */   }
/* 826:    */   
/* 827:    */   public QueueInfo getQueue(String queue)
/* 828:    */     throws IOException
/* 829:    */   {
/* 830:886 */     return null;
/* 831:    */   }
/* 832:    */   
/* 833:    */   public QueueAclsInfo[] getQueueAclsForCurrentUser()
/* 834:    */     throws IOException
/* 835:    */   {
/* 836:892 */     return null;
/* 837:    */   }
/* 838:    */   
/* 839:    */   public static void setLocalMaxRunningMaps(org.apache.hadoop.mapreduce.JobContext job, int maxMaps)
/* 840:    */   {
/* 841:903 */     job.getConfiguration().setInt("mapreduce.local.map.tasks.maximum", maxMaps);
/* 842:    */   }
/* 843:    */   
/* 844:    */   public static int getLocalMaxRunningMaps(org.apache.hadoop.mapreduce.JobContext job)
/* 845:    */   {
/* 846:912 */     return job.getConfiguration().getInt("mapreduce.local.map.tasks.maximum", 1);
/* 847:    */   }
/* 848:    */   
/* 849:    */   public static void setLocalMaxRunningReduces(org.apache.hadoop.mapreduce.JobContext job, int maxReduces)
/* 850:    */   {
/* 851:924 */     job.getConfiguration().setInt("mapreduce.local.reduce.tasks.maximum", maxReduces);
/* 852:    */   }
/* 853:    */   
/* 854:    */   public static int getLocalMaxRunningReduces(org.apache.hadoop.mapreduce.JobContext job)
/* 855:    */   {
/* 856:933 */     return job.getConfiguration().getInt("mapreduce.local.reduce.tasks.maximum", 1);
/* 857:    */   }
/* 858:    */   
/* 859:    */   public void cancelDelegationToken(Token<DelegationTokenIdentifier> token)
/* 860:    */     throws IOException, InterruptedException
/* 861:    */   {}
/* 862:    */   
/* 863:    */   public Token<DelegationTokenIdentifier> getDelegationToken(Text renewer)
/* 864:    */     throws IOException, InterruptedException
/* 865:    */   {
/* 866:945 */     return null;
/* 867:    */   }
/* 868:    */   
/* 869:    */   public long renewDelegationToken(Token<DelegationTokenIdentifier> token)
/* 870:    */     throws IOException, InterruptedException
/* 871:    */   {
/* 872:951 */     return 0L;
/* 873:    */   }
/* 874:    */   
/* 875:    */   public LogParams getLogFileParams(org.apache.hadoop.mapreduce.JobID jobID, org.apache.hadoop.mapreduce.TaskAttemptID taskAttemptID)
/* 876:    */     throws IOException, InterruptedException
/* 877:    */   {
/* 878:958 */     throw new UnsupportedOperationException("Not supported");
/* 879:    */   }
/* 880:    */   
/* 881:    */   static void setupChildMapredLocalDirs(Task t, JobConf conf)
/* 882:    */   {
/* 883:962 */     String[] localDirs = conf.getTrimmedStrings("mapreduce.cluster.local.dir");
/* 884:963 */     String jobId = t.getJobID().toString();
/* 885:964 */     String taskId = t.getTaskID().toString();
/* 886:965 */     boolean isCleanup = t.isTaskCleanupTask();
/* 887:966 */     String user = t.getUser();
/* 888:967 */     StringBuffer childMapredLocalDir = new StringBuffer(localDirs[0] + "/" + getLocalTaskDir(user, jobId, taskId, isCleanup));
/* 889:970 */     for (int i = 1; i < localDirs.length; i++) {
/* 890:971 */       childMapredLocalDir.append("," + localDirs[i] + "/" + getLocalTaskDir(user, jobId, taskId, isCleanup));
/* 891:    */     }
/* 892:974 */     LOG.debug("mapreduce.cluster.local.dir for child : " + childMapredLocalDir);
/* 893:975 */     conf.set("mapreduce.cluster.local.dir", childMapredLocalDir.toString());
/* 894:    */   }
/* 895:    */   
/* 896:    */   static String getLocalTaskDir(String user, String jobid, String taskid, boolean isCleanupAttempt)
/* 897:    */   {
/* 898:983 */     String taskDir = "localRunner//" + user + "/" + "jobcache" + "/" + jobid + "/" + taskid;
/* 899:985 */     if (isCleanupAttempt) {
/* 900:986 */       taskDir = taskDir + ".cleanup";
/* 901:    */     }
/* 902:988 */     return taskDir;
/* 903:    */   }
/* 904:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-common-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.LocalJobRunner
 * JD-Core Version:    0.7.0.1
 */