/*   1:    */ package org.apache.hadoop.mapred;
/*   2:    */ 
/*   3:    */ import java.util.Map;
/*   4:    */ import org.apache.hadoop.classification.InterfaceAudience.Private;
/*   5:    */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*   6:    */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*   7:    */ import org.apache.hadoop.mapreduce.JobACL;
/*   8:    */ import org.apache.hadoop.mapreduce.JobStatus.State;
/*   9:    */ import org.apache.hadoop.security.authorize.AccessControlList;
/*  10:    */ 
/*  11:    */ @InterfaceAudience.Public
/*  12:    */ @InterfaceStability.Stable
/*  13:    */ public class JobStatus
/*  14:    */   extends org.apache.hadoop.mapreduce.JobStatus
/*  15:    */ {
/*  16: 37 */   public static final int RUNNING = JobStatus.State.RUNNING.getValue();
/*  17: 39 */   public static final int SUCCEEDED = JobStatus.State.SUCCEEDED.getValue();
/*  18: 41 */   public static final int FAILED = JobStatus.State.FAILED.getValue();
/*  19: 43 */   public static final int PREP = JobStatus.State.PREP.getValue();
/*  20: 45 */   public static final int KILLED = JobStatus.State.KILLED.getValue();
/*  21:    */   private static final String UNKNOWN = "UNKNOWN";
/*  22: 50 */   private static final String[] runStates = { "UNKNOWN", "RUNNING", "SUCCEEDED", "FAILED", "PREP", "KILLED" };
/*  23:    */   
/*  24:    */   public static String getJobRunState(int state)
/*  25:    */   {
/*  26: 59 */     if ((state < 1) || (state >= runStates.length)) {
/*  27: 60 */       return "UNKNOWN";
/*  28:    */     }
/*  29: 62 */     return runStates[state];
/*  30:    */   }
/*  31:    */   
/*  32:    */   static JobStatus.State getEnum(int state)
/*  33:    */   {
/*  34: 66 */     switch (state)
/*  35:    */     {
/*  36:    */     case 1: 
/*  37: 67 */       return JobStatus.State.RUNNING;
/*  38:    */     case 2: 
/*  39: 68 */       return JobStatus.State.SUCCEEDED;
/*  40:    */     case 3: 
/*  41: 69 */       return JobStatus.State.FAILED;
/*  42:    */     case 4: 
/*  43: 70 */       return JobStatus.State.PREP;
/*  44:    */     case 5: 
/*  45: 71 */       return JobStatus.State.KILLED;
/*  46:    */     }
/*  47: 73 */     return null;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public JobStatus() {}
/*  51:    */   
/*  52:    */   @Deprecated
/*  53:    */   public JobStatus(JobID jobid, float mapProgress, float reduceProgress, float cleanupProgress, int runState)
/*  54:    */   {
/*  55: 84 */     this(jobid, mapProgress, reduceProgress, cleanupProgress, runState, null, null, null, null);
/*  56:    */   }
/*  57:    */   
/*  58:    */   @Deprecated
/*  59:    */   public JobStatus(JobID jobid, float mapProgress, float reduceProgress, int runState)
/*  60:    */   {
/*  61: 98 */     this(jobid, mapProgress, reduceProgress, runState, null, null, null, null);
/*  62:    */   }
/*  63:    */   
/*  64:    */   @Deprecated
/*  65:    */   public JobStatus(JobID jobid, float mapProgress, float reduceProgress, float cleanupProgress, int runState, JobPriority jp)
/*  66:    */   {
/*  67:112 */     this(jobid, mapProgress, reduceProgress, cleanupProgress, runState, jp, null, null, null, null);
/*  68:    */   }
/*  69:    */   
/*  70:    */   @Deprecated
/*  71:    */   public JobStatus(JobID jobid, float setupProgress, float mapProgress, float reduceProgress, float cleanupProgress, int runState, JobPriority jp)
/*  72:    */   {
/*  73:130 */     this(jobid, setupProgress, mapProgress, reduceProgress, cleanupProgress, runState, jp, null, null, null, null);
/*  74:    */   }
/*  75:    */   
/*  76:    */   public JobStatus(JobID jobid, float mapProgress, float reduceProgress, float cleanupProgress, int runState, String user, String jobName, String jobFile, String trackingUrl)
/*  77:    */   {
/*  78:150 */     this(jobid, mapProgress, reduceProgress, cleanupProgress, runState, JobPriority.NORMAL, user, jobName, jobFile, trackingUrl);
/*  79:    */   }
/*  80:    */   
/*  81:    */   public JobStatus(JobID jobid, float mapProgress, float reduceProgress, int runState, String user, String jobName, String jobFile, String trackingUrl)
/*  82:    */   {
/*  83:168 */     this(jobid, mapProgress, reduceProgress, 0.0F, runState, user, jobName, jobFile, trackingUrl);
/*  84:    */   }
/*  85:    */   
/*  86:    */   public JobStatus(JobID jobid, float mapProgress, float reduceProgress, float cleanupProgress, int runState, JobPriority jp, String user, String jobName, String jobFile, String trackingUrl)
/*  87:    */   {
/*  88:188 */     this(jobid, 0.0F, mapProgress, reduceProgress, cleanupProgress, runState, jp, user, jobName, jobFile, trackingUrl);
/*  89:    */   }
/*  90:    */   
/*  91:    */   public JobStatus(JobID jobid, float setupProgress, float mapProgress, float reduceProgress, float cleanupProgress, int runState, JobPriority jp, String user, String jobName, String jobFile, String trackingUrl)
/*  92:    */   {
/*  93:211 */     this(jobid, setupProgress, mapProgress, reduceProgress, cleanupProgress, runState, jp, user, jobName, "default", jobFile, trackingUrl);
/*  94:    */   }
/*  95:    */   
/*  96:    */   public JobStatus(JobID jobid, float setupProgress, float mapProgress, float reduceProgress, float cleanupProgress, int runState, JobPriority jp, String user, String jobName, String jobFile, String trackingUrl, boolean isUber)
/*  97:    */   {
/*  98:234 */     this(jobid, setupProgress, mapProgress, reduceProgress, cleanupProgress, runState, jp, user, jobName, "default", jobFile, trackingUrl, isUber);
/*  99:    */   }
/* 100:    */   
/* 101:    */   public JobStatus(JobID jobid, float setupProgress, float mapProgress, float reduceProgress, float cleanupProgress, int runState, JobPriority jp, String user, String jobName, String queue, String jobFile, String trackingUrl)
/* 102:    */   {
/* 103:258 */     this(jobid, setupProgress, mapProgress, reduceProgress, cleanupProgress, runState, jp, user, jobName, queue, jobFile, trackingUrl, false);
/* 104:    */   }
/* 105:    */   
/* 106:    */   public JobStatus(JobID jobid, float setupProgress, float mapProgress, float reduceProgress, float cleanupProgress, int runState, JobPriority jp, String user, String jobName, String queue, String jobFile, String trackingUrl, boolean isUber)
/* 107:    */   {
/* 108:284 */     super(jobid, setupProgress, mapProgress, reduceProgress, cleanupProgress, getEnum(runState), org.apache.hadoop.mapreduce.JobPriority.valueOf(jp.name()), user, jobName, queue, jobFile, trackingUrl, isUber);
/* 109:    */   }
/* 110:    */   
/* 111:    */   public static JobStatus downgrade(org.apache.hadoop.mapreduce.JobStatus stat)
/* 112:    */   {
/* 113:290 */     JobStatus old = new JobStatus(JobID.downgrade(stat.getJobID()), stat.getSetupProgress(), stat.getMapProgress(), stat.getReduceProgress(), stat.getCleanupProgress(), stat.getState().getValue(), JobPriority.valueOf(stat.getPriority().name()), stat.getUsername(), stat.getJobName(), stat.getQueue(), stat.getJobFile(), stat.getTrackingUrl(), stat.isUber());
/* 114:    */     
/* 115:    */ 
/* 116:    */ 
/* 117:    */ 
/* 118:    */ 
/* 119:296 */     old.setStartTime(stat.getStartTime());
/* 120:297 */     old.setFinishTime(stat.getFinishTime());
/* 121:298 */     old.setSchedulingInfo(stat.getSchedulingInfo());
/* 122:299 */     old.setHistoryFile(stat.getHistoryFile());
/* 123:300 */     return old;
/* 124:    */   }
/* 125:    */   
/* 126:    */   @Deprecated
/* 127:    */   public String getJobId()
/* 128:    */   {
/* 129:306 */     return getJobID().toString();
/* 130:    */   }
/* 131:    */   
/* 132:    */   public JobID getJobID()
/* 133:    */   {
/* 134:311 */     return JobID.downgrade(super.getJobID());
/* 135:    */   }
/* 136:    */   
/* 137:    */   public synchronized JobPriority getJobPriority()
/* 138:    */   {
/* 139:318 */     return JobPriority.valueOf(super.getPriority().name());
/* 140:    */   }
/* 141:    */   
/* 142:    */   protected synchronized void setMapProgress(float p)
/* 143:    */   {
/* 144:326 */     super.setMapProgress(p);
/* 145:    */   }
/* 146:    */   
/* 147:    */   protected synchronized void setCleanupProgress(float p)
/* 148:    */   {
/* 149:334 */     super.setCleanupProgress(p);
/* 150:    */   }
/* 151:    */   
/* 152:    */   protected synchronized void setSetupProgress(float p)
/* 153:    */   {
/* 154:342 */     super.setSetupProgress(p);
/* 155:    */   }
/* 156:    */   
/* 157:    */   protected synchronized void setReduceProgress(float p)
/* 158:    */   {
/* 159:350 */     super.setReduceProgress(p);
/* 160:    */   }
/* 161:    */   
/* 162:    */   protected synchronized void setFinishTime(long finishTime)
/* 163:    */   {
/* 164:358 */     super.setFinishTime(finishTime);
/* 165:    */   }
/* 166:    */   
/* 167:    */   protected synchronized void setHistoryFile(String historyFile)
/* 168:    */   {
/* 169:365 */     super.setHistoryFile(historyFile);
/* 170:    */   }
/* 171:    */   
/* 172:    */   protected synchronized void setTrackingUrl(String trackingUrl)
/* 173:    */   {
/* 174:372 */     super.setTrackingUrl(trackingUrl);
/* 175:    */   }
/* 176:    */   
/* 177:    */   protected synchronized void setRetired()
/* 178:    */   {
/* 179:379 */     super.setRetired();
/* 180:    */   }
/* 181:    */   
/* 182:    */   @InterfaceAudience.Private
/* 183:    */   public synchronized void setRunState(int state)
/* 184:    */   {
/* 185:392 */     super.setState(getEnum(state));
/* 186:    */   }
/* 187:    */   
/* 188:    */   public synchronized int getRunState()
/* 189:    */   {
/* 190:398 */     return super.getState().getValue();
/* 191:    */   }
/* 192:    */   
/* 193:    */   protected synchronized void setStartTime(long startTime)
/* 194:    */   {
/* 195:406 */     super.setStartTime(startTime);
/* 196:    */   }
/* 197:    */   
/* 198:    */   protected synchronized void setUsername(String userName)
/* 199:    */   {
/* 200:413 */     super.setUsername(userName);
/* 201:    */   }
/* 202:    */   
/* 203:    */   @InterfaceAudience.Private
/* 204:    */   public synchronized void setSchedulingInfo(String schedulingInfo)
/* 205:    */   {
/* 206:426 */     super.setSchedulingInfo(schedulingInfo);
/* 207:    */   }
/* 208:    */   
/* 209:    */   protected synchronized void setJobACLs(Map<JobACL, AccessControlList> acls)
/* 210:    */   {
/* 211:430 */     super.setJobACLs(acls);
/* 212:    */   }
/* 213:    */   
/* 214:    */   public synchronized void setFailureInfo(String failureInfo)
/* 215:    */   {
/* 216:434 */     super.setFailureInfo(failureInfo);
/* 217:    */   }
/* 218:    */   
/* 219:    */   public synchronized void setJobPriority(JobPriority jp)
/* 220:    */   {
/* 221:442 */     super.setPriority(org.apache.hadoop.mapreduce.JobPriority.valueOf(jp.name()));
/* 222:    */   }
/* 223:    */   
/* 224:    */   public synchronized float mapProgress()
/* 225:    */   {
/* 226:449 */     return super.getMapProgress();
/* 227:    */   }
/* 228:    */   
/* 229:    */   public synchronized float cleanupProgress()
/* 230:    */   {
/* 231:455 */     return super.getCleanupProgress();
/* 232:    */   }
/* 233:    */   
/* 234:    */   public synchronized float setupProgress()
/* 235:    */   {
/* 236:462 */     return super.getSetupProgress();
/* 237:    */   }
/* 238:    */   
/* 239:    */   public synchronized float reduceProgress()
/* 240:    */   {
/* 241:469 */     return super.getReduceProgress();
/* 242:    */   }
/* 243:    */   
/* 244:    */   static int getOldNewJobRunState(JobStatus.State state)
/* 245:    */   {
/* 246:475 */     return state.getValue();
/* 247:    */   }
/* 248:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.JobStatus
 * JD-Core Version:    0.7.0.1
 */