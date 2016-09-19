/*   1:    */ package org.apache.hadoop.mapreduce;
/*   2:    */ 
/*   3:    */ import java.io.DataInput;
/*   4:    */ import java.io.DataOutput;
/*   5:    */ import java.io.IOException;
/*   6:    */ import java.util.HashMap;
/*   7:    */ import java.util.Map;
/*   8:    */ import java.util.Map.Entry;
/*   9:    */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*  10:    */ import org.apache.hadoop.classification.InterfaceStability.Evolving;
/*  11:    */ import org.apache.hadoop.io.Text;
/*  12:    */ import org.apache.hadoop.io.Writable;
/*  13:    */ import org.apache.hadoop.io.WritableFactories;
/*  14:    */ import org.apache.hadoop.io.WritableFactory;
/*  15:    */ import org.apache.hadoop.io.WritableUtils;
/*  16:    */ import org.apache.hadoop.security.authorize.AccessControlList;
/*  17:    */ import org.apache.hadoop.util.StringInterner;
/*  18:    */ 
/*  19:    */ @InterfaceAudience.Public
/*  20:    */ @InterfaceStability.Evolving
/*  21:    */ public class JobStatus
/*  22:    */   implements Writable, Cloneable
/*  23:    */ {
/*  24:    */   private JobID jobid;
/*  25:    */   private float mapProgress;
/*  26:    */   private float reduceProgress;
/*  27:    */   private float cleanupProgress;
/*  28:    */   private float setupProgress;
/*  29:    */   private State runState;
/*  30:    */   private long startTime;
/*  31:    */   private String user;
/*  32:    */   private String queue;
/*  33:    */   private JobPriority priority;
/*  34:    */   
/*  35:    */   static
/*  36:    */   {
/*  37: 45 */     WritableFactories.setFactory(JobStatus.class, new WritableFactory()
/*  38:    */     {
/*  39:    */       public Writable newInstance()
/*  40:    */       {
/*  41: 48 */         return new JobStatus();
/*  42:    */       }
/*  43:    */     });
/*  44:    */   }
/*  45:    */   
/*  46:    */   public static enum State
/*  47:    */   {
/*  48: 56 */     RUNNING(1),  SUCCEEDED(2),  FAILED(3),  PREP(4),  KILLED(5);
/*  49:    */     
/*  50:    */     int value;
/*  51:    */     
/*  52:    */     private State(int value)
/*  53:    */     {
/*  54: 65 */       this.value = value;
/*  55:    */     }
/*  56:    */     
/*  57:    */     public int getValue()
/*  58:    */     {
/*  59: 69 */       return this.value;
/*  60:    */     }
/*  61:    */   }
/*  62:    */   
/*  63: 84 */   private String schedulingInfo = "NA";
/*  64: 85 */   private String failureInfo = "NA";
/*  65: 87 */   private Map<JobACL, AccessControlList> jobACLs = new HashMap();
/*  66:    */   private String jobName;
/*  67:    */   private String jobFile;
/*  68:    */   private long finishTime;
/*  69:    */   private boolean isRetired;
/*  70: 94 */   private String historyFile = "";
/*  71: 95 */   private String trackingUrl = "";
/*  72:    */   private int numUsedSlots;
/*  73:    */   private int numReservedSlots;
/*  74:    */   private int usedMem;
/*  75:    */   private int reservedMem;
/*  76:    */   private int neededMem;
/*  77:    */   private boolean isUber;
/*  78:    */   
/*  79:    */   public JobStatus(JobID jobid, float setupProgress, float mapProgress, float reduceProgress, float cleanupProgress, State runState, JobPriority jp, String user, String jobName, String jobFile, String trackingUrl)
/*  80:    */   {
/*  81:126 */     this(jobid, setupProgress, mapProgress, reduceProgress, cleanupProgress, runState, jp, user, jobName, "default", jobFile, trackingUrl, false);
/*  82:    */   }
/*  83:    */   
/*  84:    */   public JobStatus(JobID jobid, float setupProgress, float mapProgress, float reduceProgress, float cleanupProgress, State runState, JobPriority jp, String user, String jobName, String queue, String jobFile, String trackingUrl)
/*  85:    */   {
/*  86:150 */     this(jobid, setupProgress, mapProgress, reduceProgress, cleanupProgress, runState, jp, user, jobName, queue, jobFile, trackingUrl, false);
/*  87:    */   }
/*  88:    */   
/*  89:    */   public JobStatus(JobID jobid, float setupProgress, float mapProgress, float reduceProgress, float cleanupProgress, State runState, JobPriority jp, String user, String jobName, String queue, String jobFile, String trackingUrl, boolean isUber)
/*  90:    */   {
/*  91:175 */     this.jobid = jobid;
/*  92:176 */     this.setupProgress = setupProgress;
/*  93:177 */     this.mapProgress = mapProgress;
/*  94:178 */     this.reduceProgress = reduceProgress;
/*  95:179 */     this.cleanupProgress = cleanupProgress;
/*  96:180 */     this.runState = runState;
/*  97:181 */     this.user = user;
/*  98:182 */     this.queue = queue;
/*  99:183 */     if (jp == null) {
/* 100:184 */       throw new IllegalArgumentException("Job Priority cannot be null.");
/* 101:    */     }
/* 102:186 */     this.priority = jp;
/* 103:187 */     this.jobName = jobName;
/* 104:188 */     this.jobFile = jobFile;
/* 105:189 */     this.trackingUrl = trackingUrl;
/* 106:190 */     this.isUber = isUber;
/* 107:    */   }
/* 108:    */   
/* 109:    */   protected synchronized void setMapProgress(float p)
/* 110:    */   {
/* 111:199 */     this.mapProgress = ((float)Math.min(1.0D, Math.max(0.0D, p)));
/* 112:    */   }
/* 113:    */   
/* 114:    */   protected synchronized void setCleanupProgress(float p)
/* 115:    */   {
/* 116:207 */     this.cleanupProgress = ((float)Math.min(1.0D, Math.max(0.0D, p)));
/* 117:    */   }
/* 118:    */   
/* 119:    */   protected synchronized void setSetupProgress(float p)
/* 120:    */   {
/* 121:215 */     this.setupProgress = ((float)Math.min(1.0D, Math.max(0.0D, p)));
/* 122:    */   }
/* 123:    */   
/* 124:    */   protected synchronized void setReduceProgress(float p)
/* 125:    */   {
/* 126:223 */     this.reduceProgress = ((float)Math.min(1.0D, Math.max(0.0D, p)));
/* 127:    */   }
/* 128:    */   
/* 129:    */   protected synchronized void setPriority(JobPriority jp)
/* 130:    */   {
/* 131:231 */     if (jp == null) {
/* 132:232 */       throw new IllegalArgumentException("Job priority cannot be null.");
/* 133:    */     }
/* 134:234 */     this.priority = jp;
/* 135:    */   }
/* 136:    */   
/* 137:    */   protected synchronized void setFinishTime(long finishTime)
/* 138:    */   {
/* 139:242 */     this.finishTime = finishTime;
/* 140:    */   }
/* 141:    */   
/* 142:    */   protected synchronized void setHistoryFile(String historyFile)
/* 143:    */   {
/* 144:249 */     this.historyFile = historyFile;
/* 145:    */   }
/* 146:    */   
/* 147:    */   protected synchronized void setTrackingUrl(String trackingUrl)
/* 148:    */   {
/* 149:256 */     this.trackingUrl = trackingUrl;
/* 150:    */   }
/* 151:    */   
/* 152:    */   protected synchronized void setRetired()
/* 153:    */   {
/* 154:263 */     this.isRetired = true;
/* 155:    */   }
/* 156:    */   
/* 157:    */   protected synchronized void setState(State state)
/* 158:    */   {
/* 159:270 */     this.runState = state;
/* 160:    */   }
/* 161:    */   
/* 162:    */   protected synchronized void setStartTime(long startTime)
/* 163:    */   {
/* 164:278 */     this.startTime = startTime;
/* 165:    */   }
/* 166:    */   
/* 167:    */   protected synchronized void setUsername(String userName)
/* 168:    */   {
/* 169:285 */     this.user = userName;
/* 170:    */   }
/* 171:    */   
/* 172:    */   protected synchronized void setSchedulingInfo(String schedulingInfo)
/* 173:    */   {
/* 174:294 */     this.schedulingInfo = schedulingInfo;
/* 175:    */   }
/* 176:    */   
/* 177:    */   protected synchronized void setJobACLs(Map<JobACL, AccessControlList> acls)
/* 178:    */   {
/* 179:303 */     this.jobACLs = acls;
/* 180:    */   }
/* 181:    */   
/* 182:    */   protected synchronized void setQueue(String queue)
/* 183:    */   {
/* 184:311 */     this.queue = queue;
/* 185:    */   }
/* 186:    */   
/* 187:    */   protected synchronized void setFailureInfo(String failureInfo)
/* 188:    */   {
/* 189:319 */     this.failureInfo = failureInfo;
/* 190:    */   }
/* 191:    */   
/* 192:    */   public synchronized String getQueue()
/* 193:    */   {
/* 194:327 */     return this.queue;
/* 195:    */   }
/* 196:    */   
/* 197:    */   public synchronized float getMapProgress()
/* 198:    */   {
/* 199:333 */     return this.mapProgress;
/* 200:    */   }
/* 201:    */   
/* 202:    */   public synchronized float getCleanupProgress()
/* 203:    */   {
/* 204:338 */     return this.cleanupProgress;
/* 205:    */   }
/* 206:    */   
/* 207:    */   public synchronized float getSetupProgress()
/* 208:    */   {
/* 209:343 */     return this.setupProgress;
/* 210:    */   }
/* 211:    */   
/* 212:    */   public synchronized float getReduceProgress()
/* 213:    */   {
/* 214:348 */     return this.reduceProgress;
/* 215:    */   }
/* 216:    */   
/* 217:    */   public synchronized State getState()
/* 218:    */   {
/* 219:353 */     return this.runState;
/* 220:    */   }
/* 221:    */   
/* 222:    */   public synchronized long getStartTime()
/* 223:    */   {
/* 224:358 */     return this.startTime;
/* 225:    */   }
/* 226:    */   
/* 227:    */   public Object clone()
/* 228:    */   {
/* 229:    */     try
/* 230:    */     {
/* 231:363 */       return super.clone();
/* 232:    */     }
/* 233:    */     catch (CloneNotSupportedException cnse)
/* 234:    */     {
/* 235:366 */       throw new InternalError(cnse.toString());
/* 236:    */     }
/* 237:    */   }
/* 238:    */   
/* 239:    */   public JobID getJobID()
/* 240:    */   {
/* 241:373 */     return this.jobid;
/* 242:    */   }
/* 243:    */   
/* 244:    */   public synchronized String getUsername()
/* 245:    */   {
/* 246:378 */     return this.user;
/* 247:    */   }
/* 248:    */   
/* 249:    */   public synchronized String getSchedulingInfo()
/* 250:    */   {
/* 251:385 */     return this.schedulingInfo;
/* 252:    */   }
/* 253:    */   
/* 254:    */   public synchronized Map<JobACL, AccessControlList> getJobACLs()
/* 255:    */   {
/* 256:394 */     return this.jobACLs;
/* 257:    */   }
/* 258:    */   
/* 259:    */   public synchronized JobPriority getPriority()
/* 260:    */   {
/* 261:401 */     return this.priority;
/* 262:    */   }
/* 263:    */   
/* 264:    */   public synchronized String getFailureInfo()
/* 265:    */   {
/* 266:408 */     return this.failureInfo;
/* 267:    */   }
/* 268:    */   
/* 269:    */   public synchronized boolean isJobComplete()
/* 270:    */   {
/* 271:416 */     return (this.runState == State.SUCCEEDED) || (this.runState == State.FAILED) || (this.runState == State.KILLED);
/* 272:    */   }
/* 273:    */   
/* 274:    */   public synchronized void write(DataOutput out)
/* 275:    */     throws IOException
/* 276:    */   {
/* 277:425 */     this.jobid.write(out);
/* 278:426 */     out.writeFloat(this.setupProgress);
/* 279:427 */     out.writeFloat(this.mapProgress);
/* 280:428 */     out.writeFloat(this.reduceProgress);
/* 281:429 */     out.writeFloat(this.cleanupProgress);
/* 282:430 */     WritableUtils.writeEnum(out, this.runState);
/* 283:431 */     out.writeLong(this.startTime);
/* 284:432 */     Text.writeString(out, this.user);
/* 285:433 */     WritableUtils.writeEnum(out, this.priority);
/* 286:434 */     Text.writeString(out, this.schedulingInfo);
/* 287:435 */     out.writeLong(this.finishTime);
/* 288:436 */     out.writeBoolean(this.isRetired);
/* 289:437 */     Text.writeString(out, this.historyFile);
/* 290:438 */     Text.writeString(out, this.jobName);
/* 291:439 */     Text.writeString(out, this.trackingUrl);
/* 292:440 */     Text.writeString(out, this.jobFile);
/* 293:441 */     out.writeBoolean(this.isUber);
/* 294:    */     
/* 295:    */ 
/* 296:444 */     out.writeInt(this.jobACLs.size());
/* 297:445 */     for (Map.Entry<JobACL, AccessControlList> entry : this.jobACLs.entrySet())
/* 298:    */     {
/* 299:446 */       WritableUtils.writeEnum(out, (Enum)entry.getKey());
/* 300:447 */       ((AccessControlList)entry.getValue()).write(out);
/* 301:    */     }
/* 302:    */   }
/* 303:    */   
/* 304:    */   public synchronized void readFields(DataInput in)
/* 305:    */     throws IOException
/* 306:    */   {
/* 307:452 */     this.jobid = new JobID();
/* 308:453 */     this.jobid.readFields(in);
/* 309:454 */     this.setupProgress = in.readFloat();
/* 310:455 */     this.mapProgress = in.readFloat();
/* 311:456 */     this.reduceProgress = in.readFloat();
/* 312:457 */     this.cleanupProgress = in.readFloat();
/* 313:458 */     this.runState = ((State)WritableUtils.readEnum(in, State.class));
/* 314:459 */     this.startTime = in.readLong();
/* 315:460 */     this.user = StringInterner.weakIntern(Text.readString(in));
/* 316:461 */     this.priority = ((JobPriority)WritableUtils.readEnum(in, JobPriority.class));
/* 317:462 */     this.schedulingInfo = StringInterner.weakIntern(Text.readString(in));
/* 318:463 */     this.finishTime = in.readLong();
/* 319:464 */     this.isRetired = in.readBoolean();
/* 320:465 */     this.historyFile = StringInterner.weakIntern(Text.readString(in));
/* 321:466 */     this.jobName = StringInterner.weakIntern(Text.readString(in));
/* 322:467 */     this.trackingUrl = StringInterner.weakIntern(Text.readString(in));
/* 323:468 */     this.jobFile = StringInterner.weakIntern(Text.readString(in));
/* 324:469 */     this.isUber = in.readBoolean();
/* 325:    */     
/* 326:    */ 
/* 327:472 */     int numACLs = in.readInt();
/* 328:473 */     for (int i = 0; i < numACLs; i++)
/* 329:    */     {
/* 330:474 */       JobACL aclType = (JobACL)WritableUtils.readEnum(in, JobACL.class);
/* 331:475 */       AccessControlList acl = new AccessControlList(" ");
/* 332:476 */       acl.readFields(in);
/* 333:477 */       this.jobACLs.put(aclType, acl);
/* 334:    */     }
/* 335:    */   }
/* 336:    */   
/* 337:    */   public String getJobName()
/* 338:    */   {
/* 339:485 */     return this.jobName;
/* 340:    */   }
/* 341:    */   
/* 342:    */   public String getJobFile()
/* 343:    */   {
/* 344:492 */     return this.jobFile;
/* 345:    */   }
/* 346:    */   
/* 347:    */   public synchronized String getTrackingUrl()
/* 348:    */   {
/* 349:499 */     return this.trackingUrl;
/* 350:    */   }
/* 351:    */   
/* 352:    */   public synchronized long getFinishTime()
/* 353:    */   {
/* 354:506 */     return this.finishTime;
/* 355:    */   }
/* 356:    */   
/* 357:    */   public synchronized boolean isRetired()
/* 358:    */   {
/* 359:513 */     return this.isRetired;
/* 360:    */   }
/* 361:    */   
/* 362:    */   public synchronized String getHistoryFile()
/* 363:    */   {
/* 364:521 */     return this.historyFile;
/* 365:    */   }
/* 366:    */   
/* 367:    */   public int getNumUsedSlots()
/* 368:    */   {
/* 369:528 */     return this.numUsedSlots;
/* 370:    */   }
/* 371:    */   
/* 372:    */   public void setNumUsedSlots(int n)
/* 373:    */   {
/* 374:535 */     this.numUsedSlots = n;
/* 375:    */   }
/* 376:    */   
/* 377:    */   public int getNumReservedSlots()
/* 378:    */   {
/* 379:542 */     return this.numReservedSlots;
/* 380:    */   }
/* 381:    */   
/* 382:    */   public void setNumReservedSlots(int n)
/* 383:    */   {
/* 384:549 */     this.numReservedSlots = n;
/* 385:    */   }
/* 386:    */   
/* 387:    */   public int getUsedMem()
/* 388:    */   {
/* 389:556 */     return this.usedMem;
/* 390:    */   }
/* 391:    */   
/* 392:    */   public void setUsedMem(int m)
/* 393:    */   {
/* 394:563 */     this.usedMem = m;
/* 395:    */   }
/* 396:    */   
/* 397:    */   public int getReservedMem()
/* 398:    */   {
/* 399:570 */     return this.reservedMem;
/* 400:    */   }
/* 401:    */   
/* 402:    */   public void setReservedMem(int r)
/* 403:    */   {
/* 404:577 */     this.reservedMem = r;
/* 405:    */   }
/* 406:    */   
/* 407:    */   public int getNeededMem()
/* 408:    */   {
/* 409:584 */     return this.neededMem;
/* 410:    */   }
/* 411:    */   
/* 412:    */   public void setNeededMem(int n)
/* 413:    */   {
/* 414:591 */     this.neededMem = n;
/* 415:    */   }
/* 416:    */   
/* 417:    */   public synchronized boolean isUber()
/* 418:    */   {
/* 419:599 */     return this.isUber;
/* 420:    */   }
/* 421:    */   
/* 422:    */   public synchronized void setUber(boolean isUber)
/* 423:    */   {
/* 424:607 */     this.isUber = isUber;
/* 425:    */   }
/* 426:    */   
/* 427:    */   public String toString()
/* 428:    */   {
/* 429:611 */     StringBuffer buffer = new StringBuffer();
/* 430:612 */     buffer.append("job-id : " + this.jobid);
/* 431:613 */     buffer.append("uber-mode : " + this.isUber);
/* 432:614 */     buffer.append("map-progress : " + this.mapProgress);
/* 433:615 */     buffer.append("reduce-progress : " + this.reduceProgress);
/* 434:616 */     buffer.append("cleanup-progress : " + this.cleanupProgress);
/* 435:617 */     buffer.append("setup-progress : " + this.setupProgress);
/* 436:618 */     buffer.append("runstate : " + this.runState);
/* 437:619 */     buffer.append("start-time : " + this.startTime);
/* 438:620 */     buffer.append("user-name : " + this.user);
/* 439:621 */     buffer.append("priority : " + this.priority);
/* 440:622 */     buffer.append("scheduling-info : " + this.schedulingInfo);
/* 441:623 */     buffer.append("num-used-slots" + this.numUsedSlots);
/* 442:624 */     buffer.append("num-reserved-slots" + this.numReservedSlots);
/* 443:625 */     buffer.append("used-mem" + this.usedMem);
/* 444:626 */     buffer.append("reserved-mem" + this.reservedMem);
/* 445:627 */     buffer.append("needed-mem" + this.neededMem);
/* 446:628 */     return buffer.toString();
/* 447:    */   }
/* 448:    */   
/* 449:    */   public JobStatus() {}
/* 450:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.JobStatus
 * JD-Core Version:    0.7.0.1
 */