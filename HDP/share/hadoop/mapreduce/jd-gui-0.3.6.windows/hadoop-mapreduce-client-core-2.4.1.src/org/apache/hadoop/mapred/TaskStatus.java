/*   1:    */ package org.apache.hadoop.mapred;
/*   2:    */ 
/*   3:    */ import java.io.DataInput;
/*   4:    */ import java.io.DataOutput;
/*   5:    */ import java.io.IOException;
/*   6:    */ import java.util.List;
/*   7:    */ import org.apache.commons.logging.Log;
/*   8:    */ import org.apache.commons.logging.LogFactory;
/*   9:    */ import org.apache.hadoop.classification.InterfaceAudience.Private;
/*  10:    */ import org.apache.hadoop.classification.InterfaceStability.Unstable;
/*  11:    */ import org.apache.hadoop.io.Text;
/*  12:    */ import org.apache.hadoop.io.Writable;
/*  13:    */ import org.apache.hadoop.io.WritableUtils;
/*  14:    */ import org.apache.hadoop.util.StringInterner;
/*  15:    */ import org.apache.hadoop.util.StringUtils;
/*  16:    */ 
/*  17:    */ @InterfaceAudience.Private
/*  18:    */ @InterfaceStability.Unstable
/*  19:    */ public abstract class TaskStatus
/*  20:    */   implements Writable, Cloneable
/*  21:    */ {
/*  22: 42 */   static final Log LOG = LogFactory.getLog(TaskStatus.class.getName());
/*  23:    */   private final TaskAttemptID taskid;
/*  24:    */   private float progress;
/*  25:    */   private volatile State runState;
/*  26:    */   private String diagnosticInfo;
/*  27:    */   private String stateString;
/*  28:    */   private String taskTracker;
/*  29:    */   private int numSlots;
/*  30:    */   private long startTime;
/*  31:    */   private long finishTime;
/*  32:    */   
/*  33:    */   @InterfaceAudience.Private
/*  34:    */   @InterfaceStability.Unstable
/*  35:    */   public static enum Phase
/*  36:    */   {
/*  37: 48 */     STARTING,  MAP,  SHUFFLE,  SORT,  REDUCE,  CLEANUP;
/*  38:    */     
/*  39:    */     private Phase() {}
/*  40:    */   }
/*  41:    */   
/*  42:    */   @InterfaceAudience.Private
/*  43:    */   @InterfaceStability.Unstable
/*  44:    */   public static enum State
/*  45:    */   {
/*  46: 53 */     RUNNING,  SUCCEEDED,  FAILED,  UNASSIGNED,  KILLED,  COMMIT_PENDING,  FAILED_UNCLEAN,  KILLED_UNCLEAN;
/*  47:    */     
/*  48:    */     private State() {}
/*  49:    */   }
/*  50:    */   
/*  51: 66 */   private long outputSize = -1L;
/*  52: 68 */   private volatile Phase phase = Phase.STARTING;
/*  53:    */   private Counters counters;
/*  54:    */   private boolean includeAllCounters;
/*  55: 71 */   private SortedRanges.Range nextRecordRange = new SortedRanges.Range();
/*  56:    */   static final int MAX_STRING_SIZE = 1024;
/*  57:    */   
/*  58:    */   protected int getMaxStringSize()
/*  59:    */   {
/*  60: 83 */     return 1024;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public TaskStatus()
/*  64:    */   {
/*  65: 87 */     this.taskid = new TaskAttemptID();
/*  66: 88 */     this.numSlots = 0;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public TaskStatus(TaskAttemptID taskid, float progress, int numSlots, State runState, String diagnosticInfo, String stateString, String taskTracker, Phase phase, Counters counters)
/*  70:    */   {
/*  71: 95 */     this.taskid = taskid;
/*  72: 96 */     this.progress = progress;
/*  73: 97 */     this.numSlots = numSlots;
/*  74: 98 */     this.runState = runState;
/*  75: 99 */     setDiagnosticInfo(diagnosticInfo);
/*  76:100 */     setStateString(stateString);
/*  77:101 */     this.taskTracker = taskTracker;
/*  78:102 */     this.phase = phase;
/*  79:103 */     this.counters = counters;
/*  80:104 */     this.includeAllCounters = true;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public TaskAttemptID getTaskID()
/*  84:    */   {
/*  85:107 */     return this.taskid;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public abstract boolean getIsMap();
/*  89:    */   
/*  90:    */   public int getNumSlots()
/*  91:    */   {
/*  92:110 */     return this.numSlots;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public float getProgress()
/*  96:    */   {
/*  97:113 */     return this.progress;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public void setProgress(float progress)
/* 101:    */   {
/* 102:115 */     this.progress = progress;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public State getRunState()
/* 106:    */   {
/* 107:117 */     return this.runState;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public String getTaskTracker()
/* 111:    */   {
/* 112:118 */     return this.taskTracker;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public void setTaskTracker(String tracker)
/* 116:    */   {
/* 117:119 */     this.taskTracker = tracker;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public void setRunState(State runState)
/* 121:    */   {
/* 122:120 */     this.runState = runState;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public String getDiagnosticInfo()
/* 126:    */   {
/* 127:121 */     return this.diagnosticInfo;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public void setDiagnosticInfo(String info)
/* 131:    */   {
/* 132:124 */     if ((this.diagnosticInfo != null) && (this.diagnosticInfo.length() == getMaxStringSize()))
/* 133:    */     {
/* 134:126 */       LOG.info("task-diagnostic-info for task " + this.taskid + " : " + info);
/* 135:127 */       return;
/* 136:    */     }
/* 137:129 */     this.diagnosticInfo = (this.diagnosticInfo == null ? info : this.diagnosticInfo.concat(info));
/* 138:132 */     if ((this.diagnosticInfo != null) && (this.diagnosticInfo.length() > getMaxStringSize()))
/* 139:    */     {
/* 140:134 */       LOG.info("task-diagnostic-info for task " + this.taskid + " : " + this.diagnosticInfo);
/* 141:    */       
/* 142:136 */       this.diagnosticInfo = this.diagnosticInfo.substring(0, getMaxStringSize());
/* 143:    */     }
/* 144:    */   }
/* 145:    */   
/* 146:    */   public String getStateString()
/* 147:    */   {
/* 148:139 */     return this.stateString;
/* 149:    */   }
/* 150:    */   
/* 151:    */   public void setStateString(String stateString)
/* 152:    */   {
/* 153:144 */     if (stateString != null) {
/* 154:145 */       if (stateString.length() <= getMaxStringSize())
/* 155:    */       {
/* 156:146 */         this.stateString = stateString;
/* 157:    */       }
/* 158:    */       else
/* 159:    */       {
/* 160:149 */         LOG.info("state-string for task " + this.taskid + " : " + stateString);
/* 161:    */         
/* 162:151 */         this.stateString = stateString.substring(0, getMaxStringSize());
/* 163:    */       }
/* 164:    */     }
/* 165:    */   }
/* 166:    */   
/* 167:    */   public SortedRanges.Range getNextRecordRange()
/* 168:    */   {
/* 169:161 */     return this.nextRecordRange;
/* 170:    */   }
/* 171:    */   
/* 172:    */   public void setNextRecordRange(SortedRanges.Range nextRecordRange)
/* 173:    */   {
/* 174:169 */     this.nextRecordRange = nextRecordRange;
/* 175:    */   }
/* 176:    */   
/* 177:    */   public long getFinishTime()
/* 178:    */   {
/* 179:181 */     return this.finishTime;
/* 180:    */   }
/* 181:    */   
/* 182:    */   void setFinishTime(long finishTime)
/* 183:    */   {
/* 184:192 */     if ((getStartTime() > 0L) && (finishTime > 0L)) {
/* 185:193 */       this.finishTime = finishTime;
/* 186:    */     } else {
/* 187:196 */       LOG.error("Trying to set finish time for task " + this.taskid + " when no start time is set, stackTrace is : " + StringUtils.stringifyException(new Exception()));
/* 188:    */     }
/* 189:    */   }
/* 190:    */   
/* 191:    */   public long getShuffleFinishTime()
/* 192:    */   {
/* 193:210 */     return 0L;
/* 194:    */   }
/* 195:    */   
/* 196:    */   void setShuffleFinishTime(long shuffleFinishTime) {}
/* 197:    */   
/* 198:    */   public long getMapFinishTime()
/* 199:    */   {
/* 200:228 */     return 0L;
/* 201:    */   }
/* 202:    */   
/* 203:    */   void setMapFinishTime(long mapFinishTime) {}
/* 204:    */   
/* 205:    */   public long getSortFinishTime()
/* 206:    */   {
/* 207:245 */     return 0L;
/* 208:    */   }
/* 209:    */   
/* 210:    */   void setSortFinishTime(long sortFinishTime) {}
/* 211:    */   
/* 212:    */   public long getStartTime()
/* 213:    */   {
/* 214:260 */     return this.startTime;
/* 215:    */   }
/* 216:    */   
/* 217:    */   void setStartTime(long startTime)
/* 218:    */   {
/* 219:270 */     if (startTime > 0L) {
/* 220:271 */       this.startTime = startTime;
/* 221:    */     } else {
/* 222:274 */       LOG.error("Trying to set illegal startTime for task : " + this.taskid + ".Stack trace is : " + StringUtils.stringifyException(new Exception()));
/* 223:    */     }
/* 224:    */   }
/* 225:    */   
/* 226:    */   public Phase getPhase()
/* 227:    */   {
/* 228:285 */     return this.phase;
/* 229:    */   }
/* 230:    */   
/* 231:    */   public void setPhase(Phase phase)
/* 232:    */   {
/* 233:292 */     Phase oldPhase = getPhase();
/* 234:293 */     if (oldPhase != phase)
/* 235:    */     {
/* 236:295 */       if (phase == Phase.SORT)
/* 237:    */       {
/* 238:296 */         if (oldPhase == Phase.MAP) {
/* 239:297 */           setMapFinishTime(System.currentTimeMillis());
/* 240:    */         } else {
/* 241:300 */           setShuffleFinishTime(System.currentTimeMillis());
/* 242:    */         }
/* 243:    */       }
/* 244:302 */       else if (phase == Phase.REDUCE) {
/* 245:303 */         setSortFinishTime(System.currentTimeMillis());
/* 246:    */       }
/* 247:305 */       this.phase = phase;
/* 248:    */     }
/* 249:    */   }
/* 250:    */   
/* 251:    */   boolean inTaskCleanupPhase()
/* 252:    */   {
/* 253:310 */     return (this.phase == Phase.CLEANUP) && ((this.runState == State.FAILED_UNCLEAN) || (this.runState == State.KILLED_UNCLEAN));
/* 254:    */   }
/* 255:    */   
/* 256:    */   public boolean getIncludeAllCounters()
/* 257:    */   {
/* 258:316 */     return this.includeAllCounters;
/* 259:    */   }
/* 260:    */   
/* 261:    */   public void setIncludeAllCounters(boolean send)
/* 262:    */   {
/* 263:320 */     this.includeAllCounters = send;
/* 264:321 */     this.counters.setWriteAllCounters(send);
/* 265:    */   }
/* 266:    */   
/* 267:    */   public Counters getCounters()
/* 268:    */   {
/* 269:328 */     return this.counters;
/* 270:    */   }
/* 271:    */   
/* 272:    */   public void setCounters(Counters counters)
/* 273:    */   {
/* 274:335 */     this.counters = counters;
/* 275:    */   }
/* 276:    */   
/* 277:    */   public long getOutputSize()
/* 278:    */   {
/* 279:342 */     return this.outputSize;
/* 280:    */   }
/* 281:    */   
/* 282:    */   void setOutputSize(long l)
/* 283:    */   {
/* 284:350 */     this.outputSize = l;
/* 285:    */   }
/* 286:    */   
/* 287:    */   public List<TaskAttemptID> getFetchFailedMaps()
/* 288:    */   {
/* 289:359 */     return null;
/* 290:    */   }
/* 291:    */   
/* 292:    */   public abstract void addFetchFailedMap(TaskAttemptID paramTaskAttemptID);
/* 293:    */   
/* 294:    */   synchronized void statusUpdate(float progress, String state, Counters counters)
/* 295:    */   {
/* 296:381 */     setProgress(progress);
/* 297:382 */     setStateString(state);
/* 298:383 */     setCounters(counters);
/* 299:    */   }
/* 300:    */   
/* 301:    */   synchronized void statusUpdate(TaskStatus status)
/* 302:    */   {
/* 303:392 */     setProgress(status.getProgress());
/* 304:393 */     this.runState = status.getRunState();
/* 305:394 */     setStateString(status.getStateString());
/* 306:395 */     this.nextRecordRange = status.getNextRecordRange();
/* 307:    */     
/* 308:397 */     setDiagnosticInfo(status.getDiagnosticInfo());
/* 309:399 */     if (status.getStartTime() > 0L) {
/* 310:400 */       setStartTime(status.getStartTime());
/* 311:    */     }
/* 312:402 */     if (status.getFinishTime() > 0L) {
/* 313:403 */       setFinishTime(status.getFinishTime());
/* 314:    */     }
/* 315:406 */     this.phase = status.getPhase();
/* 316:407 */     this.counters = status.getCounters();
/* 317:408 */     this.outputSize = status.outputSize;
/* 318:    */   }
/* 319:    */   
/* 320:    */   synchronized void statusUpdate(State runState, float progress, String state, Phase phase, long finishTime)
/* 321:    */   {
/* 322:428 */     setRunState(runState);
/* 323:429 */     setProgress(progress);
/* 324:430 */     setStateString(state);
/* 325:431 */     setPhase(phase);
/* 326:432 */     if (finishTime > 0L) {
/* 327:433 */       setFinishTime(finishTime);
/* 328:    */     }
/* 329:    */   }
/* 330:    */   
/* 331:    */   synchronized void clearStatus()
/* 332:    */   {
/* 333:444 */     this.diagnosticInfo = "";
/* 334:    */   }
/* 335:    */   
/* 336:    */   public Object clone()
/* 337:    */   {
/* 338:    */     try
/* 339:    */     {
/* 340:450 */       return super.clone();
/* 341:    */     }
/* 342:    */     catch (CloneNotSupportedException cnse)
/* 343:    */     {
/* 344:453 */       throw new InternalError(cnse.toString());
/* 345:    */     }
/* 346:    */   }
/* 347:    */   
/* 348:    */   public void write(DataOutput out)
/* 349:    */     throws IOException
/* 350:    */   {
/* 351:461 */     this.taskid.write(out);
/* 352:462 */     out.writeFloat(this.progress);
/* 353:463 */     out.writeInt(this.numSlots);
/* 354:464 */     WritableUtils.writeEnum(out, this.runState);
/* 355:465 */     Text.writeString(out, this.diagnosticInfo);
/* 356:466 */     Text.writeString(out, this.stateString);
/* 357:467 */     WritableUtils.writeEnum(out, this.phase);
/* 358:468 */     out.writeLong(this.startTime);
/* 359:469 */     out.writeLong(this.finishTime);
/* 360:470 */     out.writeBoolean(this.includeAllCounters);
/* 361:471 */     out.writeLong(this.outputSize);
/* 362:472 */     this.counters.write(out);
/* 363:473 */     this.nextRecordRange.write(out);
/* 364:    */   }
/* 365:    */   
/* 366:    */   public void readFields(DataInput in)
/* 367:    */     throws IOException
/* 368:    */   {
/* 369:477 */     this.taskid.readFields(in);
/* 370:478 */     setProgress(in.readFloat());
/* 371:479 */     this.numSlots = in.readInt();
/* 372:480 */     this.runState = ((State)WritableUtils.readEnum(in, State.class));
/* 373:481 */     setDiagnosticInfo(StringInterner.weakIntern(Text.readString(in)));
/* 374:482 */     setStateString(StringInterner.weakIntern(Text.readString(in)));
/* 375:483 */     this.phase = ((Phase)WritableUtils.readEnum(in, Phase.class));
/* 376:484 */     this.startTime = in.readLong();
/* 377:485 */     this.finishTime = in.readLong();
/* 378:486 */     this.counters = new Counters();
/* 379:487 */     this.includeAllCounters = in.readBoolean();
/* 380:488 */     this.outputSize = in.readLong();
/* 381:489 */     this.counters.readFields(in);
/* 382:490 */     this.nextRecordRange.readFields(in);
/* 383:    */   }
/* 384:    */   
/* 385:    */   static TaskStatus createTaskStatus(DataInput in, TaskAttemptID taskId, float progress, int numSlots, State runState, String diagnosticInfo, String stateString, String taskTracker, Phase phase, Counters counters)
/* 386:    */     throws IOException
/* 387:    */   {
/* 388:503 */     boolean isMap = in.readBoolean();
/* 389:504 */     return createTaskStatus(isMap, taskId, progress, numSlots, runState, diagnosticInfo, stateString, taskTracker, phase, counters);
/* 390:    */   }
/* 391:    */   
/* 392:    */   static TaskStatus createTaskStatus(boolean isMap, TaskAttemptID taskId, float progress, int numSlots, State runState, String diagnosticInfo, String stateString, String taskTracker, Phase phase, Counters counters)
/* 393:    */   {
/* 394:514 */     return isMap ? new MapTaskStatus(taskId, progress, numSlots, runState, diagnosticInfo, stateString, taskTracker, phase, counters) : new ReduceTaskStatus(taskId, progress, numSlots, runState, diagnosticInfo, stateString, taskTracker, phase, counters);
/* 395:    */   }
/* 396:    */   
/* 397:    */   static TaskStatus createTaskStatus(boolean isMap)
/* 398:    */   {
/* 399:523 */     return isMap ? new MapTaskStatus() : new ReduceTaskStatus();
/* 400:    */   }
/* 401:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.TaskStatus
 * JD-Core Version:    0.7.0.1
 */