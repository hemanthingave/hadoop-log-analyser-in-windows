/*   1:    */ package org.apache.hadoop.mapreduce;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.Collection;
/*   5:    */ import java.util.HashMap;
/*   6:    */ import java.util.Iterator;
/*   7:    */ import java.util.List;
/*   8:    */ import java.util.Map;
/*   9:    */ import org.apache.hadoop.conf.Configuration;
/*  10:    */ import org.apache.hadoop.mapred.Counters.Counter;
/*  11:    */ import org.apache.hadoop.mapred.Counters.Group;
/*  12:    */ import org.apache.hadoop.mapred.TIPStatus;
/*  13:    */ import org.apache.hadoop.mapred.TaskCompletionEvent;
/*  14:    */ import org.apache.hadoop.mapred.TaskCompletionEvent.Status;
/*  15:    */ import org.apache.hadoop.mapred.TaskStatus.Phase;
/*  16:    */ import org.apache.hadoop.mapred.TaskStatus.State;
/*  17:    */ import org.apache.hadoop.mapreduce.v2.api.records.JobId;
/*  18:    */ import org.apache.hadoop.mapreduce.v2.api.records.JobReport;
/*  19:    */ import org.apache.hadoop.mapreduce.v2.api.records.JobState;
/*  20:    */ import org.apache.hadoop.mapreduce.v2.api.records.Phase;
/*  21:    */ import org.apache.hadoop.mapreduce.v2.api.records.TaskAttemptCompletionEvent;
/*  22:    */ import org.apache.hadoop.mapreduce.v2.api.records.TaskAttemptCompletionEventStatus;
/*  23:    */ import org.apache.hadoop.mapreduce.v2.api.records.TaskAttemptId;
/*  24:    */ import org.apache.hadoop.mapreduce.v2.api.records.TaskAttemptState;
/*  25:    */ import org.apache.hadoop.mapreduce.v2.api.records.TaskId;
/*  26:    */ import org.apache.hadoop.mapreduce.v2.api.records.TaskState;
/*  27:    */ import org.apache.hadoop.mapreduce.v2.util.MRApps;
/*  28:    */ import org.apache.hadoop.yarn.api.records.ApplicationId;
/*  29:    */ import org.apache.hadoop.yarn.api.records.ApplicationReport;
/*  30:    */ import org.apache.hadoop.yarn.api.records.ApplicationResourceUsageReport;
/*  31:    */ import org.apache.hadoop.yarn.api.records.FinalApplicationStatus;
/*  32:    */ import org.apache.hadoop.yarn.api.records.NodeId;
/*  33:    */ import org.apache.hadoop.yarn.api.records.NodeReport;
/*  34:    */ import org.apache.hadoop.yarn.api.records.QueueACL;
/*  35:    */ import org.apache.hadoop.yarn.api.records.QueueUserACLInfo;
/*  36:    */ import org.apache.hadoop.yarn.api.records.Resource;
/*  37:    */ import org.apache.hadoop.yarn.api.records.YarnApplicationState;
/*  38:    */ import org.apache.hadoop.yarn.exceptions.YarnRuntimeException;
/*  39:    */ import org.apache.hadoop.yarn.factories.RecordFactory;
/*  40:    */ import org.apache.hadoop.yarn.factory.providers.RecordFactoryProvider;
/*  41:    */ 
/*  42:    */ public class TypeConverter
/*  43:    */ {
/*  44: 62 */   private static RecordFactory recordFactory = RecordFactoryProvider.getRecordFactory(null);
/*  45:    */   private static final String TT_NAME_PREFIX = "tracker_";
/*  46:    */   
/*  47:    */   public static org.apache.hadoop.mapred.JobID fromYarn(JobId id)
/*  48:    */   {
/*  49: 66 */     String identifier = fromClusterTimeStamp(id.getAppId().getClusterTimestamp());
/*  50: 67 */     return new org.apache.hadoop.mapred.JobID(identifier, id.getId());
/*  51:    */   }
/*  52:    */   
/*  53:    */   public static JobID fromYarn(ApplicationId appID)
/*  54:    */   {
/*  55: 72 */     String identifier = fromClusterTimeStamp(appID.getClusterTimestamp());
/*  56: 73 */     return new org.apache.hadoop.mapred.JobID(identifier, appID.getId());
/*  57:    */   }
/*  58:    */   
/*  59:    */   public static JobId toYarn(JobID id)
/*  60:    */   {
/*  61: 77 */     JobId jobId = (JobId)recordFactory.newRecordInstance(JobId.class);
/*  62: 78 */     jobId.setId(id.getId());
/*  63:    */     
/*  64: 80 */     ApplicationId appId = ApplicationId.newInstance(toClusterTimeStamp(id.getJtIdentifier()), id.getId());
/*  65:    */     
/*  66: 82 */     jobId.setAppId(appId);
/*  67: 83 */     return jobId;
/*  68:    */   }
/*  69:    */   
/*  70:    */   private static String fromClusterTimeStamp(long clusterTimeStamp)
/*  71:    */   {
/*  72: 87 */     return Long.toString(clusterTimeStamp);
/*  73:    */   }
/*  74:    */   
/*  75:    */   private static long toClusterTimeStamp(String identifier)
/*  76:    */   {
/*  77: 91 */     return Long.parseLong(identifier);
/*  78:    */   }
/*  79:    */   
/*  80:    */   public static TaskType fromYarn(org.apache.hadoop.mapreduce.v2.api.records.TaskType taskType)
/*  81:    */   {
/*  82: 96 */     switch (taskType)
/*  83:    */     {
/*  84:    */     case MAP: 
/*  85: 98 */       return TaskType.MAP;
/*  86:    */     case REDUCE: 
/*  87:100 */       return TaskType.REDUCE;
/*  88:    */     }
/*  89:102 */     throw new YarnRuntimeException("Unrecognized task type: " + taskType);
/*  90:    */   }
/*  91:    */   
/*  92:    */   public static org.apache.hadoop.mapreduce.v2.api.records.TaskType toYarn(TaskType taskType)
/*  93:    */   {
/*  94:108 */     switch (taskType)
/*  95:    */     {
/*  96:    */     case MAP: 
/*  97:110 */       return org.apache.hadoop.mapreduce.v2.api.records.TaskType.MAP;
/*  98:    */     case REDUCE: 
/*  99:112 */       return org.apache.hadoop.mapreduce.v2.api.records.TaskType.REDUCE;
/* 100:    */     }
/* 101:114 */     throw new YarnRuntimeException("Unrecognized task type: " + taskType);
/* 102:    */   }
/* 103:    */   
/* 104:    */   public static org.apache.hadoop.mapred.TaskID fromYarn(TaskId id)
/* 105:    */   {
/* 106:119 */     return new org.apache.hadoop.mapred.TaskID(fromYarn(id.getJobId()), fromYarn(id.getTaskType()), id.getId());
/* 107:    */   }
/* 108:    */   
/* 109:    */   public static TaskId toYarn(TaskID id)
/* 110:    */   {
/* 111:124 */     TaskId taskId = (TaskId)recordFactory.newRecordInstance(TaskId.class);
/* 112:125 */     taskId.setId(id.getId());
/* 113:126 */     taskId.setTaskType(toYarn(id.getTaskType()));
/* 114:127 */     taskId.setJobId(toYarn(id.getJobID()));
/* 115:128 */     return taskId;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public static TaskAttemptState toYarn(TaskStatus.State state)
/* 119:    */   {
/* 120:133 */     switch (1.$SwitchMap$org$apache$hadoop$mapred$TaskStatus$State[state.ordinal()])
/* 121:    */     {
/* 122:    */     case 1: 
/* 123:135 */       return TaskAttemptState.COMMIT_PENDING;
/* 124:    */     case 2: 
/* 125:    */     case 3: 
/* 126:138 */       return TaskAttemptState.FAILED;
/* 127:    */     case 4: 
/* 128:    */     case 5: 
/* 129:141 */       return TaskAttemptState.KILLED;
/* 130:    */     case 6: 
/* 131:143 */       return TaskAttemptState.RUNNING;
/* 132:    */     case 7: 
/* 133:145 */       return TaskAttemptState.SUCCEEDED;
/* 134:    */     case 8: 
/* 135:147 */       return TaskAttemptState.STARTING;
/* 136:    */     }
/* 137:149 */     throw new YarnRuntimeException("Unrecognized State: " + state);
/* 138:    */   }
/* 139:    */   
/* 140:    */   public static Phase toYarn(TaskStatus.Phase phase)
/* 141:    */   {
/* 142:154 */     switch (1.$SwitchMap$org$apache$hadoop$mapred$TaskStatus$Phase[phase.ordinal()])
/* 143:    */     {
/* 144:    */     case 1: 
/* 145:156 */       return Phase.STARTING;
/* 146:    */     case 2: 
/* 147:158 */       return Phase.MAP;
/* 148:    */     case 3: 
/* 149:160 */       return Phase.SHUFFLE;
/* 150:    */     case 4: 
/* 151:162 */       return Phase.SORT;
/* 152:    */     case 5: 
/* 153:164 */       return Phase.REDUCE;
/* 154:    */     case 6: 
/* 155:166 */       return Phase.CLEANUP;
/* 156:    */     }
/* 157:168 */     throw new YarnRuntimeException("Unrecognized Phase: " + phase);
/* 158:    */   }
/* 159:    */   
/* 160:    */   public static TaskCompletionEvent[] fromYarn(TaskAttemptCompletionEvent[] newEvents)
/* 161:    */   {
/* 162:173 */     TaskCompletionEvent[] oldEvents = new TaskCompletionEvent[newEvents.length];
/* 163:    */     
/* 164:175 */     int i = 0;
/* 165:177 */     for (TaskAttemptCompletionEvent newEvent : newEvents) {
/* 166:178 */       oldEvents[(i++)] = fromYarn(newEvent);
/* 167:    */     }
/* 168:180 */     return oldEvents;
/* 169:    */   }
/* 170:    */   
/* 171:    */   public static TaskCompletionEvent fromYarn(TaskAttemptCompletionEvent newEvent)
/* 172:    */   {
/* 173:185 */     return new TaskCompletionEvent(newEvent.getEventId(), fromYarn(newEvent.getAttemptId()), newEvent.getAttemptId().getId(), newEvent.getAttemptId().getTaskId().getTaskType().equals(org.apache.hadoop.mapreduce.v2.api.records.TaskType.MAP), fromYarn(newEvent.getStatus()), newEvent.getMapOutputServerAddress());
/* 174:    */   }
/* 175:    */   
/* 176:    */   public static TaskCompletionEvent.Status fromYarn(TaskAttemptCompletionEventStatus newStatus)
/* 177:    */   {
/* 178:194 */     switch (1.$SwitchMap$org$apache$hadoop$mapreduce$v2$api$records$TaskAttemptCompletionEventStatus[newStatus.ordinal()])
/* 179:    */     {
/* 180:    */     case 1: 
/* 181:196 */       return TaskCompletionEvent.Status.FAILED;
/* 182:    */     case 2: 
/* 183:198 */       return TaskCompletionEvent.Status.KILLED;
/* 184:    */     case 3: 
/* 185:200 */       return TaskCompletionEvent.Status.OBSOLETE;
/* 186:    */     case 4: 
/* 187:202 */       return TaskCompletionEvent.Status.SUCCEEDED;
/* 188:    */     case 5: 
/* 189:204 */       return TaskCompletionEvent.Status.TIPFAILED;
/* 190:    */     }
/* 191:206 */     throw new YarnRuntimeException("Unrecognized status: " + newStatus);
/* 192:    */   }
/* 193:    */   
/* 194:    */   public static org.apache.hadoop.mapred.TaskAttemptID fromYarn(TaskAttemptId id)
/* 195:    */   {
/* 196:211 */     return new org.apache.hadoop.mapred.TaskAttemptID(fromYarn(id.getTaskId()), id.getId());
/* 197:    */   }
/* 198:    */   
/* 199:    */   public static TaskAttemptId toYarn(org.apache.hadoop.mapred.TaskAttemptID id)
/* 200:    */   {
/* 201:217 */     TaskAttemptId taskAttemptId = (TaskAttemptId)recordFactory.newRecordInstance(TaskAttemptId.class);
/* 202:218 */     taskAttemptId.setTaskId(toYarn(id.getTaskID()));
/* 203:219 */     taskAttemptId.setId(id.getId());
/* 204:220 */     return taskAttemptId;
/* 205:    */   }
/* 206:    */   
/* 207:    */   public static TaskAttemptId toYarn(TaskAttemptID id)
/* 208:    */   {
/* 209:225 */     TaskAttemptId taskAttemptId = (TaskAttemptId)recordFactory.newRecordInstance(TaskAttemptId.class);
/* 210:226 */     taskAttemptId.setTaskId(toYarn(id.getTaskID()));
/* 211:227 */     taskAttemptId.setId(id.getId());
/* 212:228 */     return taskAttemptId;
/* 213:    */   }
/* 214:    */   
/* 215:    */   public static Counters fromYarn(org.apache.hadoop.mapreduce.v2.api.records.Counters yCntrs)
/* 216:    */   {
/* 217:233 */     if (yCntrs == null) {
/* 218:234 */       return null;
/* 219:    */     }
/* 220:236 */     Counters counters = new Counters();
/* 221:238 */     for (Iterator i$ = yCntrs.getAllCounterGroups().values().iterator(); i$.hasNext();)
/* 222:    */     {
/* 223:238 */       yGrp = (org.apache.hadoop.mapreduce.v2.api.records.CounterGroup)i$.next();
/* 224:239 */       counters.addGroup(yGrp.getName(), yGrp.getDisplayName());
/* 225:240 */       for (org.apache.hadoop.mapreduce.v2.api.records.Counter yCntr : yGrp.getAllCounters().values())
/* 226:    */       {
/* 227:241 */         Counter c = counters.findCounter(yGrp.getName(), yCntr.getName());
/* 228:    */         
/* 229:    */ 
/* 230:244 */         c.setValue(yCntr.getValue());
/* 231:    */       }
/* 232:    */     }
/* 233:    */     org.apache.hadoop.mapreduce.v2.api.records.CounterGroup yGrp;
/* 234:247 */     return counters;
/* 235:    */   }
/* 236:    */   
/* 237:    */   public static org.apache.hadoop.mapreduce.v2.api.records.Counters toYarn(org.apache.hadoop.mapred.Counters counters)
/* 238:    */   {
/* 239:251 */     if (counters == null) {
/* 240:252 */       return null;
/* 241:    */     }
/* 242:254 */     org.apache.hadoop.mapreduce.v2.api.records.Counters yCntrs = (org.apache.hadoop.mapreduce.v2.api.records.Counters)recordFactory.newRecordInstance(org.apache.hadoop.mapreduce.v2.api.records.Counters.class);
/* 243:255 */     yCntrs.addAllCounterGroups(new HashMap());
/* 244:256 */     for (Counters.Group grp : counters)
/* 245:    */     {
/* 246:257 */       org.apache.hadoop.mapreduce.v2.api.records.CounterGroup yGrp = (org.apache.hadoop.mapreduce.v2.api.records.CounterGroup)recordFactory.newRecordInstance(org.apache.hadoop.mapreduce.v2.api.records.CounterGroup.class);
/* 247:258 */       yGrp.setName(grp.getName());
/* 248:259 */       yGrp.setDisplayName(grp.getDisplayName());
/* 249:260 */       yGrp.addAllCounters(new HashMap());
/* 250:261 */       for (Counters.Counter cntr : grp)
/* 251:    */       {
/* 252:262 */         org.apache.hadoop.mapreduce.v2.api.records.Counter yCntr = (org.apache.hadoop.mapreduce.v2.api.records.Counter)recordFactory.newRecordInstance(org.apache.hadoop.mapreduce.v2.api.records.Counter.class);
/* 253:263 */         yCntr.setName(cntr.getName());
/* 254:264 */         yCntr.setDisplayName(cntr.getDisplayName());
/* 255:265 */         yCntr.setValue(cntr.getValue());
/* 256:266 */         yGrp.setCounter(yCntr.getName(), yCntr);
/* 257:    */       }
/* 258:268 */       yCntrs.setCounterGroup(yGrp.getName(), yGrp);
/* 259:    */     }
/* 260:270 */     return yCntrs;
/* 261:    */   }
/* 262:    */   
/* 263:    */   public static org.apache.hadoop.mapreduce.v2.api.records.Counters toYarn(Counters counters)
/* 264:    */   {
/* 265:274 */     if (counters == null) {
/* 266:275 */       return null;
/* 267:    */     }
/* 268:277 */     org.apache.hadoop.mapreduce.v2.api.records.Counters yCntrs = (org.apache.hadoop.mapreduce.v2.api.records.Counters)recordFactory.newRecordInstance(org.apache.hadoop.mapreduce.v2.api.records.Counters.class);
/* 269:278 */     yCntrs.addAllCounterGroups(new HashMap());
/* 270:279 */     for (CounterGroup grp : counters)
/* 271:    */     {
/* 272:280 */       org.apache.hadoop.mapreduce.v2.api.records.CounterGroup yGrp = (org.apache.hadoop.mapreduce.v2.api.records.CounterGroup)recordFactory.newRecordInstance(org.apache.hadoop.mapreduce.v2.api.records.CounterGroup.class);
/* 273:281 */       yGrp.setName(grp.getName());
/* 274:282 */       yGrp.setDisplayName(grp.getDisplayName());
/* 275:283 */       yGrp.addAllCounters(new HashMap());
/* 276:284 */       for (Counter cntr : grp)
/* 277:    */       {
/* 278:285 */         org.apache.hadoop.mapreduce.v2.api.records.Counter yCntr = (org.apache.hadoop.mapreduce.v2.api.records.Counter)recordFactory.newRecordInstance(org.apache.hadoop.mapreduce.v2.api.records.Counter.class);
/* 279:286 */         yCntr.setName(cntr.getName());
/* 280:287 */         yCntr.setDisplayName(cntr.getDisplayName());
/* 281:288 */         yCntr.setValue(cntr.getValue());
/* 282:289 */         yGrp.setCounter(yCntr.getName(), yCntr);
/* 283:    */       }
/* 284:291 */       yCntrs.setCounterGroup(yGrp.getName(), yGrp);
/* 285:    */     }
/* 286:293 */     return yCntrs;
/* 287:    */   }
/* 288:    */   
/* 289:    */   public static JobStatus fromYarn(JobReport jobreport, String trackingUrl)
/* 290:    */   {
/* 291:297 */     org.apache.hadoop.mapred.JobPriority jobPriority = org.apache.hadoop.mapred.JobPriority.NORMAL;
/* 292:298 */     JobStatus jobStatus = new org.apache.hadoop.mapred.JobStatus(fromYarn(jobreport.getJobId()), jobreport.getSetupProgress(), jobreport.getMapProgress(), jobreport.getReduceProgress(), jobreport.getCleanupProgress(), fromYarn(jobreport.getJobState()), jobPriority, jobreport.getUser(), jobreport.getJobName(), jobreport.getJobFile(), trackingUrl, jobreport.isUber());
/* 293:    */     
/* 294:    */ 
/* 295:    */ 
/* 296:    */ 
/* 297:    */ 
/* 298:304 */     jobStatus.setStartTime(jobreport.getStartTime());
/* 299:305 */     jobStatus.setFinishTime(jobreport.getFinishTime());
/* 300:306 */     jobStatus.setFailureInfo(jobreport.getDiagnostics());
/* 301:307 */     return jobStatus;
/* 302:    */   }
/* 303:    */   
/* 304:    */   public static QueueState fromYarn(org.apache.hadoop.yarn.api.records.QueueState state)
/* 305:    */   {
/* 306:312 */     QueueState qState = QueueState.getState(state.toString().toLowerCase());
/* 307:    */     
/* 308:    */ 
/* 309:315 */     return qState;
/* 310:    */   }
/* 311:    */   
/* 312:    */   public static int fromYarn(JobState state)
/* 313:    */   {
/* 314:320 */     switch (1.$SwitchMap$org$apache$hadoop$mapreduce$v2$api$records$JobState[state.ordinal()])
/* 315:    */     {
/* 316:    */     case 1: 
/* 317:    */     case 2: 
/* 318:323 */       return org.apache.hadoop.mapred.JobStatus.PREP;
/* 319:    */     case 3: 
/* 320:325 */       return org.apache.hadoop.mapred.JobStatus.RUNNING;
/* 321:    */     case 4: 
/* 322:327 */       return org.apache.hadoop.mapred.JobStatus.KILLED;
/* 323:    */     case 5: 
/* 324:329 */       return org.apache.hadoop.mapred.JobStatus.SUCCEEDED;
/* 325:    */     case 6: 
/* 326:    */     case 7: 
/* 327:332 */       return org.apache.hadoop.mapred.JobStatus.FAILED;
/* 328:    */     }
/* 329:334 */     throw new YarnRuntimeException("Unrecognized job state: " + state);
/* 330:    */   }
/* 331:    */   
/* 332:    */   public static TIPStatus fromYarn(TaskState state)
/* 333:    */   {
/* 334:339 */     switch (1.$SwitchMap$org$apache$hadoop$mapreduce$v2$api$records$TaskState[state.ordinal()])
/* 335:    */     {
/* 336:    */     case 1: 
/* 337:    */     case 2: 
/* 338:342 */       return TIPStatus.PENDING;
/* 339:    */     case 3: 
/* 340:344 */       return TIPStatus.RUNNING;
/* 341:    */     case 4: 
/* 342:346 */       return TIPStatus.KILLED;
/* 343:    */     case 5: 
/* 344:348 */       return TIPStatus.COMPLETE;
/* 345:    */     case 6: 
/* 346:350 */       return TIPStatus.FAILED;
/* 347:    */     }
/* 348:352 */     throw new YarnRuntimeException("Unrecognized task state: " + state);
/* 349:    */   }
/* 350:    */   
/* 351:    */   public static TaskReport fromYarn(org.apache.hadoop.mapreduce.v2.api.records.TaskReport report)
/* 352:    */   {
/* 353:356 */     String[] diagnostics = null;
/* 354:    */     int i;
/* 355:357 */     if (report.getDiagnosticsList() != null)
/* 356:    */     {
/* 357:358 */       diagnostics = new String[report.getDiagnosticsCount()];
/* 358:359 */       i = 0;
/* 359:360 */       for (String cs : report.getDiagnosticsList()) {
/* 360:361 */         diagnostics[(i++)] = cs.toString();
/* 361:    */       }
/* 362:    */     }
/* 363:    */     else
/* 364:    */     {
/* 365:364 */       diagnostics = new String[0];
/* 366:    */     }
/* 367:367 */     TaskReport rep = new TaskReport(fromYarn(report.getTaskId()), report.getProgress(), report.getTaskState().toString(), diagnostics, fromYarn(report.getTaskState()), report.getStartTime(), report.getFinishTime(), fromYarn(report.getCounters()));
/* 368:    */     
/* 369:    */ 
/* 370:    */ 
/* 371:371 */     List<TaskAttemptID> runningAtts = new ArrayList();
/* 372:374 */     for (TaskAttemptId id : report.getRunningAttemptsList()) {
/* 373:375 */       runningAtts.add(fromYarn(id));
/* 374:    */     }
/* 375:377 */     rep.setRunningTaskAttemptIds(runningAtts);
/* 376:378 */     if (report.getSuccessfulAttempt() != null) {
/* 377:379 */       rep.setSuccessfulAttemptId(fromYarn(report.getSuccessfulAttempt()));
/* 378:    */     }
/* 379:381 */     return rep;
/* 380:    */   }
/* 381:    */   
/* 382:    */   public static List<TaskReport> fromYarn(List<org.apache.hadoop.mapreduce.v2.api.records.TaskReport> taskReports)
/* 383:    */   {
/* 384:386 */     List<TaskReport> reports = new ArrayList();
/* 385:387 */     for (org.apache.hadoop.mapreduce.v2.api.records.TaskReport r : taskReports) {
/* 386:388 */       reports.add(fromYarn(r));
/* 387:    */     }
/* 388:390 */     return reports;
/* 389:    */   }
/* 390:    */   
/* 391:    */   public static JobStatus.State fromYarn(YarnApplicationState yarnApplicationState, FinalApplicationStatus finalApplicationStatus)
/* 392:    */   {
/* 393:395 */     switch (1.$SwitchMap$org$apache$hadoop$yarn$api$records$YarnApplicationState[yarnApplicationState.ordinal()])
/* 394:    */     {
/* 395:    */     case 1: 
/* 396:    */     case 2: 
/* 397:    */     case 3: 
/* 398:    */     case 4: 
/* 399:400 */       return JobStatus.State.PREP;
/* 400:    */     case 5: 
/* 401:402 */       return JobStatus.State.RUNNING;
/* 402:    */     case 6: 
/* 403:404 */       if (finalApplicationStatus == FinalApplicationStatus.SUCCEEDED) {
/* 404:405 */         return JobStatus.State.SUCCEEDED;
/* 405:    */       }
/* 406:406 */       if (finalApplicationStatus == FinalApplicationStatus.KILLED) {
/* 407:407 */         return JobStatus.State.KILLED;
/* 408:    */       }
/* 409:    */     case 7: 
/* 410:410 */       return JobStatus.State.FAILED;
/* 411:    */     case 8: 
/* 412:412 */       return JobStatus.State.KILLED;
/* 413:    */     }
/* 414:414 */     throw new YarnRuntimeException("Unrecognized application state: " + yarnApplicationState);
/* 415:    */   }
/* 416:    */   
/* 417:    */   public static TaskTrackerInfo fromYarn(NodeReport node)
/* 418:    */   {
/* 419:419 */     TaskTrackerInfo taskTracker = new TaskTrackerInfo("tracker_" + node.getNodeId().toString());
/* 420:    */     
/* 421:421 */     return taskTracker;
/* 422:    */   }
/* 423:    */   
/* 424:    */   public static TaskTrackerInfo[] fromYarnNodes(List<NodeReport> nodes)
/* 425:    */   {
/* 426:425 */     List<TaskTrackerInfo> taskTrackers = new ArrayList();
/* 427:426 */     for (NodeReport node : nodes) {
/* 428:427 */       taskTrackers.add(fromYarn(node));
/* 429:    */     }
/* 430:429 */     return (TaskTrackerInfo[])taskTrackers.toArray(new TaskTrackerInfo[nodes.size()]);
/* 431:    */   }
/* 432:    */   
/* 433:    */   public static JobStatus fromYarn(ApplicationReport application, String jobFile)
/* 434:    */   {
/* 435:434 */     String trackingUrl = application.getTrackingUrl();
/* 436:435 */     trackingUrl = trackingUrl == null ? "" : trackingUrl;
/* 437:436 */     JobStatus jobStatus = new JobStatus(fromYarn(application.getApplicationId()), 0.0F, 0.0F, 0.0F, 0.0F, fromYarn(application.getYarnApplicationState(), application.getFinalApplicationStatus()), JobPriority.NORMAL, application.getUser(), application.getName(), application.getQueue(), jobFile, trackingUrl, false);
/* 438:    */     
/* 439:    */ 
/* 440:    */ 
/* 441:    */ 
/* 442:    */ 
/* 443:    */ 
/* 444:    */ 
/* 445:    */ 
/* 446:445 */     jobStatus.setSchedulingInfo(trackingUrl);
/* 447:446 */     jobStatus.setStartTime(application.getStartTime());
/* 448:447 */     jobStatus.setFinishTime(application.getFinishTime());
/* 449:448 */     jobStatus.setFailureInfo(application.getDiagnostics());
/* 450:449 */     ApplicationResourceUsageReport resourceUsageReport = application.getApplicationResourceUsageReport();
/* 451:451 */     if (resourceUsageReport != null)
/* 452:    */     {
/* 453:452 */       jobStatus.setNeededMem(resourceUsageReport.getNeededResources().getMemory());
/* 454:    */       
/* 455:454 */       jobStatus.setNumReservedSlots(resourceUsageReport.getNumReservedContainers());
/* 456:    */       
/* 457:456 */       jobStatus.setNumUsedSlots(resourceUsageReport.getNumUsedContainers());
/* 458:457 */       jobStatus.setReservedMem(resourceUsageReport.getReservedResources().getMemory());
/* 459:    */       
/* 460:459 */       jobStatus.setUsedMem(resourceUsageReport.getUsedResources().getMemory());
/* 461:    */     }
/* 462:461 */     return jobStatus;
/* 463:    */   }
/* 464:    */   
/* 465:    */   public static JobStatus[] fromYarnApps(List<ApplicationReport> applications, Configuration conf)
/* 466:    */   {
/* 467:466 */     List<JobStatus> jobStatuses = new ArrayList();
/* 468:467 */     for (ApplicationReport application : applications)
/* 469:    */     {
/* 470:469 */       JobID jobId = fromYarn(application.getApplicationId());
/* 471:    */       
/* 472:471 */       jobStatuses.add(fromYarn(application, MRApps.getJobFile(conf, application.getUser(), jobId)));
/* 473:    */     }
/* 474:474 */     return (JobStatus[])jobStatuses.toArray(new JobStatus[jobStatuses.size()]);
/* 475:    */   }
/* 476:    */   
/* 477:    */   public static QueueInfo fromYarn(org.apache.hadoop.yarn.api.records.QueueInfo queueInfo, Configuration conf)
/* 478:    */   {
/* 479:480 */     QueueInfo toReturn = new QueueInfo(queueInfo.getQueueName(), "Capacity: " + queueInfo.getCapacity() * 100.0F + ", MaximumCapacity: " + (queueInfo.getMaximumCapacity() < 0.0F ? "UNDEFINED" : Float.valueOf(queueInfo.getMaximumCapacity() * 100.0F)) + ", CurrentCapacity: " + queueInfo.getCurrentCapacity() * 100.0F, fromYarn(queueInfo.getQueueState()), fromYarnApps(queueInfo.getApplications(), conf));
/* 480:    */     
/* 481:    */ 
/* 482:    */ 
/* 483:    */ 
/* 484:    */ 
/* 485:486 */     List<QueueInfo> childQueues = new ArrayList();
/* 486:488 */     for (org.apache.hadoop.yarn.api.records.QueueInfo childQueue : queueInfo.getChildQueues()) {
/* 487:489 */       childQueues.add(fromYarn(childQueue, conf));
/* 488:    */     }
/* 489:491 */     toReturn.setQueueChildren(childQueues);
/* 490:492 */     return toReturn;
/* 491:    */   }
/* 492:    */   
/* 493:    */   public static QueueInfo[] fromYarnQueueInfo(List<org.apache.hadoop.yarn.api.records.QueueInfo> queues, Configuration conf)
/* 494:    */   {
/* 495:498 */     List<QueueInfo> queueInfos = new ArrayList(queues.size());
/* 496:499 */     for (org.apache.hadoop.yarn.api.records.QueueInfo queue : queues) {
/* 497:500 */       queueInfos.add(fromYarn(queue, conf));
/* 498:    */     }
/* 499:502 */     return (QueueInfo[])queueInfos.toArray(new QueueInfo[queueInfos.size()]);
/* 500:    */   }
/* 501:    */   
/* 502:    */   public static QueueAclsInfo[] fromYarnQueueUserAclsInfo(List<QueueUserACLInfo> userAcls)
/* 503:    */   {
/* 504:507 */     List<QueueAclsInfo> acls = new ArrayList();
/* 505:508 */     for (QueueUserACLInfo aclInfo : userAcls)
/* 506:    */     {
/* 507:509 */       List<String> operations = new ArrayList();
/* 508:510 */       for (QueueACL qAcl : aclInfo.getUserAcls()) {
/* 509:511 */         operations.add(qAcl.toString());
/* 510:    */       }
/* 511:514 */       QueueAclsInfo acl = new QueueAclsInfo(aclInfo.getQueueName(), (String[])operations.toArray(new String[operations.size()]));
/* 512:    */       
/* 513:    */ 
/* 514:517 */       acls.add(acl);
/* 515:    */     }
/* 516:519 */     return (QueueAclsInfo[])acls.toArray(new QueueAclsInfo[acls.size()]);
/* 517:    */   }
/* 518:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-common-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.TypeConverter
 * JD-Core Version:    0.7.0.1
 */