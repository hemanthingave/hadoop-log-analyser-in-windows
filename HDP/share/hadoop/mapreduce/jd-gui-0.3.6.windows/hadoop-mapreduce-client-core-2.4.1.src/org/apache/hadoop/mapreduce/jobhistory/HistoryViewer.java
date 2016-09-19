/*   1:    */ package org.apache.hadoop.mapreduce.jobhistory;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.PrintStream;
/*   5:    */ import java.text.DecimalFormat;
/*   6:    */ import java.text.Format;
/*   7:    */ import java.text.SimpleDateFormat;
/*   8:    */ import java.util.Arrays;
/*   9:    */ import java.util.Collection;
/*  10:    */ import java.util.Comparator;
/*  11:    */ import java.util.HashMap;
/*  12:    */ import java.util.Iterator;
/*  13:    */ import java.util.Map;
/*  14:    */ import java.util.Map.Entry;
/*  15:    */ import java.util.Set;
/*  16:    */ import java.util.TreeSet;
/*  17:    */ import org.apache.hadoop.classification.InterfaceAudience.Private;
/*  18:    */ import org.apache.hadoop.classification.InterfaceStability.Unstable;
/*  19:    */ import org.apache.hadoop.conf.Configuration;
/*  20:    */ import org.apache.hadoop.fs.FileSystem;
/*  21:    */ import org.apache.hadoop.fs.Path;
/*  22:    */ import org.apache.hadoop.mapred.JobStatus;
/*  23:    */ import org.apache.hadoop.mapred.TaskStatus.State;
/*  24:    */ import org.apache.hadoop.mapreduce.Counter;
/*  25:    */ import org.apache.hadoop.mapreduce.CounterGroup;
/*  26:    */ import org.apache.hadoop.mapreduce.Counters;
/*  27:    */ import org.apache.hadoop.mapreduce.JobID;
/*  28:    */ import org.apache.hadoop.mapreduce.TaskAttemptID;
/*  29:    */ import org.apache.hadoop.mapreduce.TaskID;
/*  30:    */ import org.apache.hadoop.mapreduce.TaskType;
/*  31:    */ import org.apache.hadoop.mapreduce.util.HostUtil;
/*  32:    */ import org.apache.hadoop.util.StringUtils;
/*  33:    */ import org.apache.hadoop.yarn.webapp.util.WebAppUtils;
/*  34:    */ 
/*  35:    */ @InterfaceAudience.Private
/*  36:    */ @InterfaceStability.Unstable
/*  37:    */ public class HistoryViewer
/*  38:    */ {
/*  39: 57 */   private static SimpleDateFormat dateFormat = new SimpleDateFormat("d-MMM-yyyy HH:mm:ss");
/*  40:    */   private FileSystem fs;
/*  41:    */   private JobHistoryParser.JobInfo job;
/*  42:    */   private String jobId;
/*  43:    */   private boolean printAll;
/*  44:    */   
/*  45:    */   public HistoryViewer(String historyFile, Configuration conf, boolean printAll)
/*  46:    */     throws IOException
/*  47:    */   {
/*  48: 74 */     this.printAll = printAll;
/*  49: 75 */     String errorMsg = "Unable to initialize History Viewer";
/*  50:    */     try
/*  51:    */     {
/*  52: 77 */       Path jobFile = new Path(historyFile);
/*  53: 78 */       this.fs = jobFile.getFileSystem(conf);
/*  54: 79 */       String[] jobDetails = jobFile.getName().split("_");
/*  55: 81 */       if (jobDetails.length < 2)
/*  56:    */       {
/*  57: 83 */         System.err.println("Ignore unrecognized file: " + jobFile.getName());
/*  58: 84 */         throw new IOException(errorMsg);
/*  59:    */       }
/*  60: 86 */       JobHistoryParser parser = new JobHistoryParser(this.fs, jobFile);
/*  61: 87 */       this.job = parser.parse();
/*  62: 88 */       this.jobId = this.job.getJobId().toString();
/*  63:    */     }
/*  64:    */     catch (Exception e)
/*  65:    */     {
/*  66: 90 */       throw new IOException(errorMsg, e);
/*  67:    */     }
/*  68:    */   }
/*  69:    */   
/*  70:    */   public void print()
/*  71:    */     throws IOException
/*  72:    */   {
/*  73: 99 */     printJobDetails();
/*  74:100 */     printTaskSummary();
/*  75:101 */     printJobAnalysis();
/*  76:102 */     printTasks(TaskType.JOB_SETUP, TaskStatus.State.FAILED.toString());
/*  77:103 */     printTasks(TaskType.JOB_SETUP, TaskStatus.State.KILLED.toString());
/*  78:104 */     printTasks(TaskType.MAP, TaskStatus.State.FAILED.toString());
/*  79:105 */     printTasks(TaskType.MAP, TaskStatus.State.KILLED.toString());
/*  80:106 */     printTasks(TaskType.REDUCE, TaskStatus.State.FAILED.toString());
/*  81:107 */     printTasks(TaskType.REDUCE, TaskStatus.State.KILLED.toString());
/*  82:108 */     printTasks(TaskType.JOB_CLEANUP, TaskStatus.State.FAILED.toString());
/*  83:109 */     printTasks(TaskType.JOB_CLEANUP, JobStatus.getJobRunState(JobStatus.KILLED));
/*  84:111 */     if (this.printAll)
/*  85:    */     {
/*  86:112 */       printTasks(TaskType.JOB_SETUP, TaskStatus.State.SUCCEEDED.toString());
/*  87:113 */       printTasks(TaskType.MAP, TaskStatus.State.SUCCEEDED.toString());
/*  88:114 */       printTasks(TaskType.REDUCE, TaskStatus.State.SUCCEEDED.toString());
/*  89:115 */       printTasks(TaskType.JOB_CLEANUP, TaskStatus.State.SUCCEEDED.toString());
/*  90:116 */       printAllTaskAttempts(TaskType.JOB_SETUP);
/*  91:117 */       printAllTaskAttempts(TaskType.MAP);
/*  92:118 */       printAllTaskAttempts(TaskType.REDUCE);
/*  93:119 */       printAllTaskAttempts(TaskType.JOB_CLEANUP);
/*  94:    */     }
/*  95:122 */     FilteredJob filter = new FilteredJob(this.job, TaskStatus.State.FAILED.toString());
/*  96:    */     
/*  97:124 */     printFailedAttempts(filter);
/*  98:    */     
/*  99:126 */     filter = new FilteredJob(this.job, TaskStatus.State.KILLED.toString());
/* 100:    */     
/* 101:128 */     printFailedAttempts(filter);
/* 102:    */   }
/* 103:    */   
/* 104:    */   private void printJobDetails()
/* 105:    */   {
/* 106:132 */     StringBuffer jobDetails = new StringBuffer();
/* 107:133 */     jobDetails.append("\nHadoop job: ").append(this.job.getJobId());
/* 108:134 */     jobDetails.append("\n=====================================");
/* 109:135 */     jobDetails.append("\nUser: ").append(this.job.getUsername());
/* 110:136 */     jobDetails.append("\nJobName: ").append(this.job.getJobname());
/* 111:137 */     jobDetails.append("\nJobConf: ").append(this.job.getJobConfPath());
/* 112:138 */     jobDetails.append("\nSubmitted At: ").append(StringUtils.getFormattedTimeWithDiff(dateFormat, this.job.getSubmitTime(), 0L));
/* 113:    */     
/* 114:    */ 
/* 115:141 */     jobDetails.append("\nLaunched At: ").append(StringUtils.getFormattedTimeWithDiff(dateFormat, this.job.getLaunchTime(), this.job.getSubmitTime()));
/* 116:    */     
/* 117:    */ 
/* 118:    */ 
/* 119:145 */     jobDetails.append("\nFinished At: ").append(StringUtils.getFormattedTimeWithDiff(dateFormat, this.job.getFinishTime(), this.job.getLaunchTime()));
/* 120:    */     
/* 121:    */ 
/* 122:    */ 
/* 123:149 */     jobDetails.append("\nStatus: ").append(this.job.getJobStatus() == null ? "Incomplete" : this.job.getJobStatus());
/* 124:    */     
/* 125:151 */     printCounters(jobDetails, this.job.getTotalCounters(), this.job.getMapCounters(), this.job.getReduceCounters());
/* 126:    */     
/* 127:153 */     jobDetails.append("\n");
/* 128:154 */     jobDetails.append("\n=====================================");
/* 129:155 */     System.out.println(jobDetails.toString());
/* 130:    */   }
/* 131:    */   
/* 132:    */   private void printCounters(StringBuffer buff, Counters totalCounters, Counters mapCounters, Counters reduceCounters)
/* 133:    */   {
/* 134:161 */     if (totalCounters == null) {
/* 135:162 */       return;
/* 136:    */     }
/* 137:164 */     buff.append("\nCounters: \n\n");
/* 138:165 */     buff.append(String.format("|%1$-30s|%2$-30s|%3$-10s|%4$-10s|%5$-10s|", new Object[] { "Group Name", "Counter name", "Map Value", "Reduce Value", "Total Value" }));
/* 139:    */     
/* 140:    */ 
/* 141:    */ 
/* 142:    */ 
/* 143:    */ 
/* 144:171 */     buff.append("\n---------------------------------------------------------------------------------------");
/* 145:173 */     for (String groupName : totalCounters.getGroupNames())
/* 146:    */     {
/* 147:174 */       CounterGroup totalGroup = (CounterGroup)totalCounters.getGroup(groupName);
/* 148:175 */       CounterGroup mapGroup = (CounterGroup)mapCounters.getGroup(groupName);
/* 149:176 */       CounterGroup reduceGroup = (CounterGroup)reduceCounters.getGroup(groupName);
/* 150:    */       
/* 151:178 */       Format decimal = new DecimalFormat();
/* 152:179 */       Iterator<Counter> ctrItr = totalGroup.iterator();
/* 153:181 */       while (ctrItr.hasNext())
/* 154:    */       {
/* 155:182 */         Counter counter = (Counter)ctrItr.next();
/* 156:183 */         String name = counter.getName();
/* 157:184 */         String mapValue = decimal.format(Long.valueOf(mapGroup.findCounter(name).getValue()));
/* 158:    */         
/* 159:186 */         String reduceValue = decimal.format(Long.valueOf(reduceGroup.findCounter(name).getValue()));
/* 160:    */         
/* 161:188 */         String totalValue = decimal.format(Long.valueOf(counter.getValue()));
/* 162:    */         
/* 163:    */ 
/* 164:191 */         buff.append(String.format("%n|%1$-30s|%2$-30s|%3$-10s|%4$-10s|%5$-10s", new Object[] { totalGroup.getDisplayName(), counter.getDisplayName(), mapValue, reduceValue, totalValue }));
/* 165:    */       }
/* 166:    */     }
/* 167:    */   }
/* 168:    */   
/* 169:    */   private void printAllTaskAttempts(TaskType taskType)
/* 170:    */   {
/* 171:201 */     Map<TaskID, JobHistoryParser.TaskInfo> tasks = this.job.getAllTasks();
/* 172:202 */     StringBuffer taskList = new StringBuffer();
/* 173:203 */     taskList.append("\n").append(taskType);
/* 174:204 */     taskList.append(" task list for ").append(this.job.getJobId());
/* 175:205 */     taskList.append("\nTaskId\t\tStartTime");
/* 176:206 */     if (TaskType.REDUCE.equals(taskType)) {
/* 177:207 */       taskList.append("\tShuffleFinished\tSortFinished");
/* 178:    */     }
/* 179:209 */     taskList.append("\tFinishTime\tHostName\tError\tTaskLogs");
/* 180:210 */     taskList.append("\n====================================================");
/* 181:211 */     System.out.println(taskList.toString());
/* 182:212 */     for (Iterator i$ = tasks.values().iterator(); i$.hasNext();)
/* 183:    */     {
/* 184:212 */       task = (JobHistoryParser.TaskInfo)i$.next();
/* 185:214 */       for (JobHistoryParser.TaskAttemptInfo attempt : task.getAllTaskAttempts().values()) {
/* 186:215 */         if (taskType.equals(task.getTaskType()))
/* 187:    */         {
/* 188:216 */           taskList.setLength(0);
/* 189:217 */           taskList.append(attempt.getAttemptId()).append("\t");
/* 190:218 */           taskList.append(StringUtils.getFormattedTimeWithDiff(dateFormat, attempt.getStartTime(), 0L)).append("\t");
/* 191:220 */           if (TaskType.REDUCE.equals(taskType))
/* 192:    */           {
/* 193:221 */             taskList.append(StringUtils.getFormattedTimeWithDiff(dateFormat, attempt.getShuffleFinishTime(), attempt.getStartTime()));
/* 194:    */             
/* 195:    */ 
/* 196:224 */             taskList.append("\t");
/* 197:225 */             taskList.append(StringUtils.getFormattedTimeWithDiff(dateFormat, attempt.getSortFinishTime(), attempt.getShuffleFinishTime()));
/* 198:    */           }
/* 199:229 */           taskList.append(StringUtils.getFormattedTimeWithDiff(dateFormat, attempt.getFinishTime(), attempt.getStartTime()));
/* 200:    */           
/* 201:    */ 
/* 202:232 */           taskList.append("\t");
/* 203:233 */           taskList.append(attempt.getHostname()).append("\t");
/* 204:234 */           taskList.append(attempt.getError());
/* 205:235 */           String taskLogsUrl = getTaskLogsUrl(WebAppUtils.getHttpSchemePrefix(this.fs.getConf()), attempt);
/* 206:    */           
/* 207:237 */           taskList.append(taskLogsUrl != null ? taskLogsUrl : "n/a");
/* 208:238 */           System.out.println(taskList.toString());
/* 209:    */         }
/* 210:    */       }
/* 211:    */     }
/* 212:    */     JobHistoryParser.TaskInfo task;
/* 213:    */   }
/* 214:    */   
/* 215:    */   private void printTaskSummary()
/* 216:    */   {
/* 217:245 */     SummarizedJob ts = new SummarizedJob(this.job);
/* 218:246 */     StringBuffer taskSummary = new StringBuffer();
/* 219:247 */     taskSummary.append("\nTask Summary");
/* 220:248 */     taskSummary.append("\n============================");
/* 221:249 */     taskSummary.append("\nKind\tTotal\t");
/* 222:250 */     taskSummary.append("Successful\tFailed\tKilled\tStartTime\tFinishTime");
/* 223:251 */     taskSummary.append("\n");
/* 224:252 */     taskSummary.append("\nSetup\t").append(ts.totalSetups);
/* 225:253 */     taskSummary.append("\t").append(ts.numFinishedSetups);
/* 226:254 */     taskSummary.append("\t\t").append(ts.numFailedSetups);
/* 227:255 */     taskSummary.append("\t").append(ts.numKilledSetups);
/* 228:256 */     taskSummary.append("\t").append(StringUtils.getFormattedTimeWithDiff(dateFormat, ts.setupStarted, 0L));
/* 229:    */     
/* 230:258 */     taskSummary.append("\t").append(StringUtils.getFormattedTimeWithDiff(dateFormat, ts.setupFinished, ts.setupStarted));
/* 231:    */     
/* 232:260 */     taskSummary.append("\nMap\t").append(ts.totalMaps);
/* 233:261 */     taskSummary.append("\t").append(this.job.getFinishedMaps());
/* 234:262 */     taskSummary.append("\t\t").append(ts.numFailedMaps);
/* 235:263 */     taskSummary.append("\t").append(ts.numKilledMaps);
/* 236:264 */     taskSummary.append("\t").append(StringUtils.getFormattedTimeWithDiff(dateFormat, ts.mapStarted, 0L));
/* 237:    */     
/* 238:266 */     taskSummary.append("\t").append(StringUtils.getFormattedTimeWithDiff(dateFormat, ts.mapFinished, ts.mapStarted));
/* 239:    */     
/* 240:268 */     taskSummary.append("\nReduce\t").append(ts.totalReduces);
/* 241:269 */     taskSummary.append("\t").append(this.job.getFinishedReduces());
/* 242:270 */     taskSummary.append("\t\t").append(ts.numFailedReduces);
/* 243:271 */     taskSummary.append("\t").append(ts.numKilledReduces);
/* 244:272 */     taskSummary.append("\t").append(StringUtils.getFormattedTimeWithDiff(dateFormat, ts.reduceStarted, 0L));
/* 245:    */     
/* 246:274 */     taskSummary.append("\t").append(StringUtils.getFormattedTimeWithDiff(dateFormat, ts.reduceFinished, ts.reduceStarted));
/* 247:    */     
/* 248:276 */     taskSummary.append("\nCleanup\t").append(ts.totalCleanups);
/* 249:277 */     taskSummary.append("\t").append(ts.numFinishedCleanups);
/* 250:278 */     taskSummary.append("\t\t").append(ts.numFailedCleanups);
/* 251:279 */     taskSummary.append("\t").append(ts.numKilledCleanups);
/* 252:280 */     taskSummary.append("\t").append(StringUtils.getFormattedTimeWithDiff(dateFormat, ts.cleanupStarted, 0L));
/* 253:    */     
/* 254:282 */     taskSummary.append("\t").append(StringUtils.getFormattedTimeWithDiff(dateFormat, ts.cleanupFinished, ts.cleanupStarted));
/* 255:    */     
/* 256:    */ 
/* 257:285 */     taskSummary.append("\n============================\n");
/* 258:286 */     System.out.println(taskSummary.toString());
/* 259:    */   }
/* 260:    */   
/* 261:    */   private void printJobAnalysis()
/* 262:    */   {
/* 263:290 */     if (!this.job.getJobStatus().equals(JobStatus.getJobRunState(JobStatus.SUCCEEDED)))
/* 264:    */     {
/* 265:292 */       System.out.println("No Analysis available as job did not finish");
/* 266:293 */       return;
/* 267:    */     }
/* 268:296 */     AnalyzedJob avg = new AnalyzedJob(this.job);
/* 269:    */     
/* 270:298 */     System.out.println("\nAnalysis");
/* 271:299 */     System.out.println("=========");
/* 272:300 */     printAnalysis(avg.getMapTasks(), this.cMap, "map", avg.getAvgMapTime(), 10);
/* 273:301 */     printLast(avg.getMapTasks(), "map", this.cFinishMapRed);
/* 274:303 */     if (avg.getReduceTasks().length > 0)
/* 275:    */     {
/* 276:304 */       printAnalysis(avg.getReduceTasks(), this.cShuffle, "shuffle", avg.getAvgShuffleTime(), 10);
/* 277:    */       
/* 278:306 */       printLast(avg.getReduceTasks(), "shuffle", this.cFinishShuffle);
/* 279:    */       
/* 280:308 */       printAnalysis(avg.getReduceTasks(), this.cReduce, "reduce", avg.getAvgReduceTime(), 10);
/* 281:    */       
/* 282:310 */       printLast(avg.getReduceTasks(), "reduce", this.cFinishMapRed);
/* 283:    */     }
/* 284:312 */     System.out.println("=========");
/* 285:    */   }
/* 286:    */   
/* 287:    */   private void printAnalysis(JobHistoryParser.TaskAttemptInfo[] tasks, Comparator<JobHistoryParser.TaskAttemptInfo> cmp, String taskType, long avg, int showTasks)
/* 288:    */   {
/* 289:320 */     Arrays.sort(tasks, cmp);
/* 290:321 */     JobHistoryParser.TaskAttemptInfo min = tasks[(tasks.length - 1)];
/* 291:322 */     StringBuffer details = new StringBuffer();
/* 292:323 */     details.append("\nTime taken by best performing ");
/* 293:324 */     details.append(taskType).append(" task ");
/* 294:325 */     details.append(min.getAttemptId().getTaskID().toString()).append(": ");
/* 295:326 */     if ("map".equals(taskType)) {
/* 296:327 */       details.append(StringUtils.formatTimeDiff(min.getFinishTime(), min.getStartTime()));
/* 297:330 */     } else if ("shuffle".equals(taskType)) {
/* 298:331 */       details.append(StringUtils.formatTimeDiff(min.getShuffleFinishTime(), min.getStartTime()));
/* 299:    */     } else {
/* 300:335 */       details.append(StringUtils.formatTimeDiff(min.getFinishTime(), min.getShuffleFinishTime()));
/* 301:    */     }
/* 302:339 */     details.append("\nAverage time taken by ");
/* 303:340 */     details.append(taskType).append(" tasks: ");
/* 304:341 */     details.append(StringUtils.formatTimeDiff(avg, 0L));
/* 305:342 */     details.append("\nWorse performing ");
/* 306:343 */     details.append(taskType).append(" tasks: ");
/* 307:344 */     details.append("\nTaskId\t\tTimetaken");
/* 308:345 */     System.out.println(details.toString());
/* 309:346 */     for (int i = 0; (i < showTasks) && (i < tasks.length); i++)
/* 310:    */     {
/* 311:347 */       details.setLength(0);
/* 312:348 */       details.append(tasks[i].getAttemptId().getTaskID()).append(" ");
/* 313:349 */       if ("map".equals(taskType)) {
/* 314:350 */         details.append(StringUtils.formatTimeDiff(tasks[i].getFinishTime(), tasks[i].getStartTime()));
/* 315:353 */       } else if ("shuffle".equals(taskType)) {
/* 316:354 */         details.append(StringUtils.formatTimeDiff(tasks[i].getShuffleFinishTime(), tasks[i].getStartTime()));
/* 317:    */       } else {
/* 318:358 */         details.append(StringUtils.formatTimeDiff(tasks[i].getFinishTime(), tasks[i].getShuffleFinishTime()));
/* 319:    */       }
/* 320:362 */       System.out.println(details.toString());
/* 321:    */     }
/* 322:    */   }
/* 323:    */   
/* 324:    */   private void printLast(JobHistoryParser.TaskAttemptInfo[] tasks, String taskType, Comparator<JobHistoryParser.TaskAttemptInfo> cmp)
/* 325:    */   {
/* 326:370 */     Arrays.sort(tasks, this.cFinishMapRed);
/* 327:371 */     JobHistoryParser.TaskAttemptInfo last = tasks[0];
/* 328:372 */     StringBuffer lastBuf = new StringBuffer();
/* 329:373 */     lastBuf.append("The last ").append(taskType);
/* 330:374 */     lastBuf.append(" task ").append(last.getAttemptId().getTaskID());
/* 331:    */     Long finishTime;
/* 332:    */     Long finishTime;
/* 333:376 */     if ("shuffle".equals(taskType)) {
/* 334:377 */       finishTime = Long.valueOf(last.getShuffleFinishTime());
/* 335:    */     } else {
/* 336:379 */       finishTime = Long.valueOf(last.getFinishTime());
/* 337:    */     }
/* 338:381 */     lastBuf.append(" finished at (relative to the Job launch time): ");
/* 339:382 */     lastBuf.append(StringUtils.getFormattedTimeWithDiff(dateFormat, finishTime.longValue(), this.job.getLaunchTime()));
/* 340:    */     
/* 341:384 */     System.out.println(lastBuf.toString());
/* 342:    */   }
/* 343:    */   
/* 344:    */   private void printTasks(TaskType taskType, String status)
/* 345:    */   {
/* 346:388 */     Map<TaskID, JobHistoryParser.TaskInfo> tasks = this.job.getAllTasks();
/* 347:389 */     StringBuffer header = new StringBuffer();
/* 348:390 */     header.append("\n").append(status).append(" ");
/* 349:391 */     header.append(taskType).append(" task list for ").append(this.jobId);
/* 350:392 */     header.append("\nTaskId\t\tStartTime\tFinishTime\tError");
/* 351:393 */     if (TaskType.MAP.equals(taskType)) {
/* 352:394 */       header.append("\tInputSplits");
/* 353:    */     }
/* 354:396 */     header.append("\n====================================================");
/* 355:397 */     StringBuffer taskList = new StringBuffer();
/* 356:398 */     for (JobHistoryParser.TaskInfo task : tasks.values()) {
/* 357:399 */       if ((taskType.equals(task.getTaskType())) && ((status.equals(task.getTaskStatus())) || (status.equalsIgnoreCase("ALL"))))
/* 358:    */       {
/* 359:402 */         taskList.setLength(0);
/* 360:403 */         taskList.append(task.getTaskId());
/* 361:404 */         taskList.append("\t").append(StringUtils.getFormattedTimeWithDiff(dateFormat, task.getStartTime(), 0L));
/* 362:    */         
/* 363:406 */         taskList.append("\t").append(StringUtils.getFormattedTimeWithDiff(dateFormat, task.getFinishTime(), task.getStartTime()));
/* 364:    */         
/* 365:    */ 
/* 366:409 */         taskList.append("\t").append(task.getError());
/* 367:410 */         if (TaskType.MAP.equals(taskType)) {
/* 368:411 */           taskList.append("\t").append(task.getSplitLocations());
/* 369:    */         }
/* 370:413 */         if (taskList != null)
/* 371:    */         {
/* 372:414 */           System.out.println(header.toString());
/* 373:415 */           System.out.println(taskList.toString());
/* 374:    */         }
/* 375:    */       }
/* 376:    */     }
/* 377:    */   }
/* 378:    */   
/* 379:    */   private void printFailedAttempts(FilteredJob filteredJob)
/* 380:    */   {
/* 381:422 */     Map<String, Set<TaskID>> badNodes = filteredJob.getFilteredMap();
/* 382:423 */     StringBuffer attempts = new StringBuffer();
/* 383:424 */     if (badNodes.size() > 0)
/* 384:    */     {
/* 385:425 */       attempts.append("\n").append(filteredJob.getFilter());
/* 386:426 */       attempts.append(" task attempts by nodes");
/* 387:427 */       attempts.append("\nHostname\tFailedTasks");
/* 388:428 */       attempts.append("\n===============================");
/* 389:429 */       System.out.println(attempts.toString());
/* 390:431 */       for (Map.Entry<String, Set<TaskID>> entry : badNodes.entrySet())
/* 391:    */       {
/* 392:432 */         String node = (String)entry.getKey();
/* 393:433 */         Set<TaskID> failedTasks = (Set)entry.getValue();
/* 394:434 */         attempts.setLength(0);
/* 395:435 */         attempts.append(node).append("\t");
/* 396:436 */         for (TaskID t : failedTasks) {
/* 397:437 */           attempts.append(t).append(", ");
/* 398:    */         }
/* 399:439 */         System.out.println(attempts.toString());
/* 400:    */       }
/* 401:    */     }
/* 402:    */   }
/* 403:    */   
/* 404:    */   public static String getTaskLogsUrl(String scheme, JobHistoryParser.TaskAttemptInfo attempt)
/* 405:    */   {
/* 406:453 */     if ((attempt.getHttpPort() == -1) || (attempt.getTrackerName().equals("")) || (attempt.getAttemptId() == null)) {
/* 407:456 */       return null;
/* 408:    */     }
/* 409:459 */     String taskTrackerName = HostUtil.convertTrackerNameToHostName(attempt.getTrackerName());
/* 410:    */     
/* 411:    */ 
/* 412:462 */     return HostUtil.getTaskLogUrl(scheme, taskTrackerName, Integer.toString(attempt.getHttpPort()), attempt.getAttemptId().toString());
/* 413:    */   }
/* 414:    */   
/* 415:467 */   private Comparator<JobHistoryParser.TaskAttemptInfo> cMap = new Comparator()
/* 416:    */   {
/* 417:    */     public int compare(JobHistoryParser.TaskAttemptInfo t1, JobHistoryParser.TaskAttemptInfo t2)
/* 418:    */     {
/* 419:471 */       long l1 = t1.getFinishTime() - t1.getStartTime();
/* 420:472 */       long l2 = t2.getFinishTime() - t2.getStartTime();
/* 421:473 */       return l2 == l1 ? 0 : l2 < l1 ? -1 : 1;
/* 422:    */     }
/* 423:    */   };
/* 424:477 */   private Comparator<JobHistoryParser.TaskAttemptInfo> cShuffle = new Comparator()
/* 425:    */   {
/* 426:    */     public int compare(JobHistoryParser.TaskAttemptInfo t1, JobHistoryParser.TaskAttemptInfo t2)
/* 427:    */     {
/* 428:481 */       long l1 = t1.getShuffleFinishTime() - t1.getStartTime();
/* 429:482 */       long l2 = t2.getShuffleFinishTime() - t2.getStartTime();
/* 430:483 */       return l2 == l1 ? 0 : l2 < l1 ? -1 : 1;
/* 431:    */     }
/* 432:    */   };
/* 433:487 */   private Comparator<JobHistoryParser.TaskAttemptInfo> cFinishShuffle = new Comparator()
/* 434:    */   {
/* 435:    */     public int compare(JobHistoryParser.TaskAttemptInfo t1, JobHistoryParser.TaskAttemptInfo t2)
/* 436:    */     {
/* 437:491 */       long l1 = t1.getShuffleFinishTime();
/* 438:492 */       long l2 = t2.getShuffleFinishTime();
/* 439:493 */       return l2 == l1 ? 0 : l2 < l1 ? -1 : 1;
/* 440:    */     }
/* 441:    */   };
/* 442:497 */   private Comparator<JobHistoryParser.TaskAttemptInfo> cFinishMapRed = new Comparator()
/* 443:    */   {
/* 444:    */     public int compare(JobHistoryParser.TaskAttemptInfo t1, JobHistoryParser.TaskAttemptInfo t2)
/* 445:    */     {
/* 446:501 */       long l1 = t1.getFinishTime();
/* 447:502 */       long l2 = t2.getFinishTime();
/* 448:503 */       return l2 == l1 ? 0 : l2 < l1 ? -1 : 1;
/* 449:    */     }
/* 450:    */   };
/* 451:507 */   private Comparator<JobHistoryParser.TaskAttemptInfo> cReduce = new Comparator()
/* 452:    */   {
/* 453:    */     public int compare(JobHistoryParser.TaskAttemptInfo t1, JobHistoryParser.TaskAttemptInfo t2)
/* 454:    */     {
/* 455:511 */       long l1 = t1.getFinishTime() - t1.getShuffleFinishTime();
/* 456:    */       
/* 457:513 */       long l2 = t2.getFinishTime() - t2.getShuffleFinishTime();
/* 458:    */       
/* 459:515 */       return l2 == l1 ? 0 : l2 < l1 ? -1 : 1;
/* 460:    */     }
/* 461:    */   };
/* 462:    */   
/* 463:    */   public static class SummarizedJob
/* 464:    */   {
/* 465:    */     Map<TaskID, JobHistoryParser.TaskInfo> tasks;
/* 466:526 */     int totalMaps = 0;
/* 467:527 */     int totalReduces = 0;
/* 468:528 */     int totalCleanups = 0;
/* 469:529 */     int totalSetups = 0;
/* 470:530 */     int numFailedMaps = 0;
/* 471:531 */     int numKilledMaps = 0;
/* 472:532 */     int numFailedReduces = 0;
/* 473:533 */     int numKilledReduces = 0;
/* 474:534 */     int numFinishedCleanups = 0;
/* 475:535 */     int numFailedCleanups = 0;
/* 476:536 */     int numKilledCleanups = 0;
/* 477:537 */     int numFinishedSetups = 0;
/* 478:538 */     int numFailedSetups = 0;
/* 479:539 */     int numKilledSetups = 0;
/* 480:540 */     long mapStarted = 0L;
/* 481:541 */     long mapFinished = 0L;
/* 482:542 */     long reduceStarted = 0L;
/* 483:543 */     long reduceFinished = 0L;
/* 484:544 */     long cleanupStarted = 0L;
/* 485:545 */     long cleanupFinished = 0L;
/* 486:546 */     long setupStarted = 0L;
/* 487:547 */     long setupFinished = 0L;
/* 488:    */     
/* 489:    */     public int getTotalMaps()
/* 490:    */     {
/* 491:550 */       return this.totalMaps;
/* 492:    */     }
/* 493:    */     
/* 494:    */     public int getTotalReduces()
/* 495:    */     {
/* 496:552 */       return this.totalReduces;
/* 497:    */     }
/* 498:    */     
/* 499:    */     public int getTotalCleanups()
/* 500:    */     {
/* 501:554 */       return this.totalCleanups;
/* 502:    */     }
/* 503:    */     
/* 504:    */     public int getTotalSetups()
/* 505:    */     {
/* 506:556 */       return this.totalSetups;
/* 507:    */     }
/* 508:    */     
/* 509:    */     public int getNumFailedMaps()
/* 510:    */     {
/* 511:558 */       return this.numFailedMaps;
/* 512:    */     }
/* 513:    */     
/* 514:    */     public int getNumKilledMaps()
/* 515:    */     {
/* 516:560 */       return this.numKilledMaps;
/* 517:    */     }
/* 518:    */     
/* 519:    */     public int getNumFailedReduces()
/* 520:    */     {
/* 521:562 */       return this.numFailedReduces;
/* 522:    */     }
/* 523:    */     
/* 524:    */     public int getNumKilledReduces()
/* 525:    */     {
/* 526:564 */       return this.numKilledReduces;
/* 527:    */     }
/* 528:    */     
/* 529:    */     public int getNumFinishedCleanups()
/* 530:    */     {
/* 531:566 */       return this.numFinishedCleanups;
/* 532:    */     }
/* 533:    */     
/* 534:    */     public int getNumFailedCleanups()
/* 535:    */     {
/* 536:568 */       return this.numFailedCleanups;
/* 537:    */     }
/* 538:    */     
/* 539:    */     public int getNumKilledCleanups()
/* 540:    */     {
/* 541:570 */       return this.numKilledCleanups;
/* 542:    */     }
/* 543:    */     
/* 544:    */     public int getNumFinishedSetups()
/* 545:    */     {
/* 546:572 */       return this.numFinishedSetups;
/* 547:    */     }
/* 548:    */     
/* 549:    */     public int getNumFailedSetups()
/* 550:    */     {
/* 551:574 */       return this.numFailedSetups;
/* 552:    */     }
/* 553:    */     
/* 554:    */     public int getNumKilledSetups()
/* 555:    */     {
/* 556:576 */       return this.numKilledSetups;
/* 557:    */     }
/* 558:    */     
/* 559:    */     public long getMapStarted()
/* 560:    */     {
/* 561:578 */       return this.mapStarted;
/* 562:    */     }
/* 563:    */     
/* 564:    */     public long getMapFinished()
/* 565:    */     {
/* 566:580 */       return this.mapFinished;
/* 567:    */     }
/* 568:    */     
/* 569:    */     public long getReduceStarted()
/* 570:    */     {
/* 571:582 */       return this.reduceStarted;
/* 572:    */     }
/* 573:    */     
/* 574:    */     public long getReduceFinished()
/* 575:    */     {
/* 576:584 */       return this.reduceFinished;
/* 577:    */     }
/* 578:    */     
/* 579:    */     public long getCleanupStarted()
/* 580:    */     {
/* 581:586 */       return this.cleanupStarted;
/* 582:    */     }
/* 583:    */     
/* 584:    */     public long getCleanupFinished()
/* 585:    */     {
/* 586:588 */       return this.cleanupFinished;
/* 587:    */     }
/* 588:    */     
/* 589:    */     public long getSetupStarted()
/* 590:    */     {
/* 591:590 */       return this.setupStarted;
/* 592:    */     }
/* 593:    */     
/* 594:    */     public long getSetupFinished()
/* 595:    */     {
/* 596:592 */       return this.setupFinished;
/* 597:    */     }
/* 598:    */     
/* 599:    */     public SummarizedJob(JobHistoryParser.JobInfo job)
/* 600:    */     {
/* 601:596 */       this.tasks = job.getAllTasks();
/* 602:598 */       for (JobHistoryParser.TaskInfo task : this.tasks.values())
/* 603:    */       {
/* 604:599 */         Map<TaskAttemptID, JobHistoryParser.TaskAttemptInfo> attempts = task.getAllTaskAttempts();
/* 605:602 */         for (JobHistoryParser.TaskAttemptInfo attempt : attempts.values())
/* 606:    */         {
/* 607:603 */           long startTime = attempt.getStartTime();
/* 608:604 */           long finishTime = attempt.getFinishTime();
/* 609:605 */           if (attempt.getTaskType().equals(TaskType.MAP))
/* 610:    */           {
/* 611:606 */             if ((this.mapStarted == 0L) || (this.mapStarted > startTime)) {
/* 612:607 */               this.mapStarted = startTime;
/* 613:    */             }
/* 614:609 */             if (this.mapFinished < finishTime) {
/* 615:610 */               this.mapFinished = finishTime;
/* 616:    */             }
/* 617:612 */             this.totalMaps += 1;
/* 618:613 */             if (attempt.getTaskStatus().equals(TaskStatus.State.FAILED.toString())) {
/* 619:615 */               this.numFailedMaps += 1;
/* 620:616 */             } else if (attempt.getTaskStatus().equals(TaskStatus.State.KILLED.toString())) {
/* 621:618 */               this.numKilledMaps += 1;
/* 622:    */             }
/* 623:    */           }
/* 624:620 */           else if (attempt.getTaskType().equals(TaskType.REDUCE))
/* 625:    */           {
/* 626:621 */             if ((this.reduceStarted == 0L) || (this.reduceStarted > startTime)) {
/* 627:622 */               this.reduceStarted = startTime;
/* 628:    */             }
/* 629:624 */             if (this.reduceFinished < finishTime) {
/* 630:625 */               this.reduceFinished = finishTime;
/* 631:    */             }
/* 632:627 */             this.totalReduces += 1;
/* 633:628 */             if (attempt.getTaskStatus().equals(TaskStatus.State.FAILED.toString())) {
/* 634:630 */               this.numFailedReduces += 1;
/* 635:631 */             } else if (attempt.getTaskStatus().equals(TaskStatus.State.KILLED.toString())) {
/* 636:633 */               this.numKilledReduces += 1;
/* 637:    */             }
/* 638:    */           }
/* 639:635 */           else if (attempt.getTaskType().equals(TaskType.JOB_CLEANUP))
/* 640:    */           {
/* 641:636 */             if ((this.cleanupStarted == 0L) || (this.cleanupStarted > startTime)) {
/* 642:637 */               this.cleanupStarted = startTime;
/* 643:    */             }
/* 644:639 */             if (this.cleanupFinished < finishTime) {
/* 645:640 */               this.cleanupFinished = finishTime;
/* 646:    */             }
/* 647:642 */             this.totalCleanups += 1;
/* 648:643 */             if (attempt.getTaskStatus().equals(TaskStatus.State.SUCCEEDED.toString())) {
/* 649:645 */               this.numFinishedCleanups += 1;
/* 650:646 */             } else if (attempt.getTaskStatus().equals(TaskStatus.State.FAILED.toString())) {
/* 651:648 */               this.numFailedCleanups += 1;
/* 652:649 */             } else if (attempt.getTaskStatus().equals(TaskStatus.State.KILLED.toString())) {
/* 653:651 */               this.numKilledCleanups += 1;
/* 654:    */             }
/* 655:    */           }
/* 656:653 */           else if (attempt.getTaskType().equals(TaskType.JOB_SETUP))
/* 657:    */           {
/* 658:654 */             if ((this.setupStarted == 0L) || (this.setupStarted > startTime)) {
/* 659:655 */               this.setupStarted = startTime;
/* 660:    */             }
/* 661:657 */             if (this.setupFinished < finishTime) {
/* 662:658 */               this.setupFinished = finishTime;
/* 663:    */             }
/* 664:660 */             this.totalSetups += 1;
/* 665:661 */             if (attempt.getTaskStatus().equals(TaskStatus.State.SUCCEEDED.toString())) {
/* 666:663 */               this.numFinishedSetups += 1;
/* 667:664 */             } else if (attempt.getTaskStatus().equals(TaskStatus.State.FAILED.toString())) {
/* 668:666 */               this.numFailedSetups += 1;
/* 669:667 */             } else if (attempt.getTaskStatus().equals(TaskStatus.State.KILLED.toString())) {
/* 670:669 */               this.numKilledSetups += 1;
/* 671:    */             }
/* 672:    */           }
/* 673:    */         }
/* 674:    */       }
/* 675:    */     }
/* 676:    */   }
/* 677:    */   
/* 678:    */   public static class AnalyzedJob
/* 679:    */   {
/* 680:    */     private long avgMapTime;
/* 681:    */     private long avgReduceTime;
/* 682:    */     private long avgShuffleTime;
/* 683:    */     private JobHistoryParser.TaskAttemptInfo[] mapTasks;
/* 684:    */     private JobHistoryParser.TaskAttemptInfo[] reduceTasks;
/* 685:    */     
/* 686:    */     public long getAvgMapTime()
/* 687:    */     {
/* 688:691 */       return this.avgMapTime;
/* 689:    */     }
/* 690:    */     
/* 691:    */     public long getAvgReduceTime()
/* 692:    */     {
/* 693:693 */       return this.avgReduceTime;
/* 694:    */     }
/* 695:    */     
/* 696:    */     public long getAvgShuffleTime()
/* 697:    */     {
/* 698:695 */       return this.avgShuffleTime;
/* 699:    */     }
/* 700:    */     
/* 701:    */     public JobHistoryParser.TaskAttemptInfo[] getMapTasks()
/* 702:    */     {
/* 703:698 */       return this.mapTasks;
/* 704:    */     }
/* 705:    */     
/* 706:    */     public JobHistoryParser.TaskAttemptInfo[] getReduceTasks()
/* 707:    */     {
/* 708:702 */       return this.reduceTasks;
/* 709:    */     }
/* 710:    */     
/* 711:    */     public AnalyzedJob(JobHistoryParser.JobInfo job)
/* 712:    */     {
/* 713:706 */       Map<TaskID, JobHistoryParser.TaskInfo> tasks = job.getAllTasks();
/* 714:707 */       int finishedMaps = (int)job.getFinishedMaps();
/* 715:708 */       int finishedReduces = (int)job.getFinishedReduces();
/* 716:709 */       this.mapTasks = new JobHistoryParser.TaskAttemptInfo[finishedMaps];
/* 717:    */       
/* 718:711 */       this.reduceTasks = new JobHistoryParser.TaskAttemptInfo[finishedReduces];
/* 719:    */       
/* 720:713 */       int mapIndex = 0;int reduceIndex = 0;
/* 721:714 */       this.avgMapTime = 0L;
/* 722:715 */       this.avgReduceTime = 0L;
/* 723:716 */       this.avgShuffleTime = 0L;
/* 724:718 */       for (JobHistoryParser.TaskInfo task : tasks.values())
/* 725:    */       {
/* 726:719 */         Map<TaskAttemptID, JobHistoryParser.TaskAttemptInfo> attempts = task.getAllTaskAttempts();
/* 727:721 */         for (JobHistoryParser.TaskAttemptInfo attempt : attempts.values()) {
/* 728:722 */           if (attempt.getTaskStatus().equals(TaskStatus.State.SUCCEEDED.toString()))
/* 729:    */           {
/* 730:724 */             long avgFinishTime = attempt.getFinishTime() - attempt.getStartTime();
/* 731:726 */             if (attempt.getTaskType().equals(TaskType.MAP))
/* 732:    */             {
/* 733:727 */               this.mapTasks[(mapIndex++)] = attempt;
/* 734:728 */               this.avgMapTime += avgFinishTime; break;
/* 735:    */             }
/* 736:729 */             if (!attempt.getTaskType().equals(TaskType.REDUCE)) {
/* 737:    */               break;
/* 738:    */             }
/* 739:730 */             this.reduceTasks[(reduceIndex++)] = attempt;
/* 740:731 */             this.avgShuffleTime += attempt.getShuffleFinishTime() - attempt.getStartTime();
/* 741:    */             
/* 742:733 */             this.avgReduceTime += attempt.getFinishTime() - attempt.getShuffleFinishTime(); break;
/* 743:    */           }
/* 744:    */         }
/* 745:    */       }
/* 746:740 */       if (finishedMaps > 0) {
/* 747:741 */         this.avgMapTime /= finishedMaps;
/* 748:    */       }
/* 749:743 */       if (finishedReduces > 0)
/* 750:    */       {
/* 751:744 */         this.avgReduceTime /= finishedReduces;
/* 752:745 */         this.avgShuffleTime /= finishedReduces;
/* 753:    */       }
/* 754:    */     }
/* 755:    */   }
/* 756:    */   
/* 757:    */   public static class FilteredJob
/* 758:    */   {
/* 759:756 */     private Map<String, Set<TaskID>> badNodesToFilteredTasks = new HashMap();
/* 760:    */     private String filter;
/* 761:    */     
/* 762:    */     public Map<String, Set<TaskID>> getFilteredMap()
/* 763:    */     {
/* 764:763 */       return this.badNodesToFilteredTasks;
/* 765:    */     }
/* 766:    */     
/* 767:    */     public String getFilter()
/* 768:    */     {
/* 769:767 */       return this.filter;
/* 770:    */     }
/* 771:    */     
/* 772:    */     public FilteredJob(JobHistoryParser.JobInfo job, String status)
/* 773:    */     {
/* 774:772 */       this.filter = status;
/* 775:    */       
/* 776:774 */       Map<TaskID, JobHistoryParser.TaskInfo> tasks = job.getAllTasks();
/* 777:776 */       for (JobHistoryParser.TaskInfo task : tasks.values())
/* 778:    */       {
/* 779:777 */         Map<TaskAttemptID, JobHistoryParser.TaskAttemptInfo> attempts = task.getAllTaskAttempts();
/* 780:779 */         for (JobHistoryParser.TaskAttemptInfo attempt : attempts.values()) {
/* 781:780 */           if (attempt.getTaskStatus().equals(status))
/* 782:    */           {
/* 783:781 */             String hostname = attempt.getHostname();
/* 784:782 */             TaskID id = attempt.getAttemptId().getTaskID();
/* 785:    */             
/* 786:784 */             Set<TaskID> set = (Set)this.badNodesToFilteredTasks.get(hostname);
/* 787:786 */             if (set == null)
/* 788:    */             {
/* 789:787 */               set = new TreeSet();
/* 790:788 */               set.add(id);
/* 791:789 */               this.badNodesToFilteredTasks.put(hostname, set);
/* 792:    */             }
/* 793:    */             else
/* 794:    */             {
/* 795:791 */               set.add(id);
/* 796:    */             }
/* 797:    */           }
/* 798:    */         }
/* 799:    */       }
/* 800:    */     }
/* 801:    */   }
/* 802:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.jobhistory.HistoryViewer
 * JD-Core Version:    0.7.0.1
 */