/*   1:    */ package org.apache.hadoop.mapreduce.lib.jobcontrol;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import java.util.Collection;
/*   6:    */ import java.util.Iterator;
/*   7:    */ import java.util.LinkedList;
/*   8:    */ import java.util.List;
/*   9:    */ import org.apache.commons.logging.Log;
/*  10:    */ import org.apache.commons.logging.LogFactory;
/*  11:    */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*  12:    */ import org.apache.hadoop.classification.InterfaceStability.Evolving;
/*  13:    */ import org.apache.hadoop.mapred.jobcontrol.Job;
/*  14:    */ import org.apache.hadoop.util.StringUtils;
/*  15:    */ 
/*  16:    */ @InterfaceAudience.Public
/*  17:    */ @InterfaceStability.Evolving
/*  18:    */ public class JobControl
/*  19:    */   implements Runnable
/*  20:    */ {
/*  21: 56 */   private static final Log LOG = LogFactory.getLog(JobControl.class);
/*  22:    */   private ThreadState runnerState;
/*  23:    */   
/*  24:    */   public static enum ThreadState
/*  25:    */   {
/*  26: 59 */     RUNNING,  SUSPENDED,  STOPPED,  STOPPING,  READY;
/*  27:    */     
/*  28:    */     private ThreadState() {}
/*  29:    */   }
/*  30:    */   
/*  31: 63 */   private LinkedList<ControlledJob> jobsInProgress = new LinkedList();
/*  32: 64 */   private LinkedList<ControlledJob> successfulJobs = new LinkedList();
/*  33: 65 */   private LinkedList<ControlledJob> failedJobs = new LinkedList();
/*  34:    */   private long nextJobID;
/*  35:    */   private String groupName;
/*  36:    */   
/*  37:    */   public JobControl(String groupName)
/*  38:    */   {
/*  39: 75 */     this.nextJobID = -1L;
/*  40: 76 */     this.groupName = groupName;
/*  41: 77 */     this.runnerState = ThreadState.READY;
/*  42:    */   }
/*  43:    */   
/*  44:    */   private static List<ControlledJob> toList(LinkedList<ControlledJob> jobs)
/*  45:    */   {
/*  46: 82 */     ArrayList<ControlledJob> retv = new ArrayList();
/*  47: 83 */     for (ControlledJob job : jobs) {
/*  48: 84 */       retv.add(job);
/*  49:    */     }
/*  50: 86 */     return retv;
/*  51:    */   }
/*  52:    */   
/*  53:    */   private synchronized List<ControlledJob> getJobsIn(ControlledJob.State state)
/*  54:    */   {
/*  55: 90 */     LinkedList<ControlledJob> l = new LinkedList();
/*  56: 91 */     for (ControlledJob j : this.jobsInProgress) {
/*  57: 92 */       if (j.getJobState() == state) {
/*  58: 93 */         l.add(j);
/*  59:    */       }
/*  60:    */     }
/*  61: 96 */     return l;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public List<ControlledJob> getWaitingJobList()
/*  65:    */   {
/*  66:103 */     return getJobsIn(ControlledJob.State.WAITING);
/*  67:    */   }
/*  68:    */   
/*  69:    */   public List<ControlledJob> getRunningJobList()
/*  70:    */   {
/*  71:110 */     return getJobsIn(ControlledJob.State.RUNNING);
/*  72:    */   }
/*  73:    */   
/*  74:    */   public List<ControlledJob> getReadyJobsList()
/*  75:    */   {
/*  76:117 */     return getJobsIn(ControlledJob.State.READY);
/*  77:    */   }
/*  78:    */   
/*  79:    */   public synchronized List<ControlledJob> getSuccessfulJobList()
/*  80:    */   {
/*  81:124 */     return toList(this.successfulJobs);
/*  82:    */   }
/*  83:    */   
/*  84:    */   public synchronized List<ControlledJob> getFailedJobList()
/*  85:    */   {
/*  86:128 */     return toList(this.failedJobs);
/*  87:    */   }
/*  88:    */   
/*  89:    */   private String getNextJobID()
/*  90:    */   {
/*  91:132 */     this.nextJobID += 1L;
/*  92:133 */     return this.groupName + this.nextJobID;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public synchronized String addJob(ControlledJob aJob)
/*  96:    */   {
/*  97:141 */     String id = getNextJobID();
/*  98:142 */     aJob.setJobID(id);
/*  99:143 */     aJob.setJobState(ControlledJob.State.WAITING);
/* 100:144 */     this.jobsInProgress.add(aJob);
/* 101:145 */     return id;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public synchronized String addJob(Job aJob)
/* 105:    */   {
/* 106:153 */     return addJob(aJob);
/* 107:    */   }
/* 108:    */   
/* 109:    */   public void addJobCollection(Collection<ControlledJob> jobs)
/* 110:    */   {
/* 111:162 */     for (ControlledJob job : jobs) {
/* 112:163 */       addJob(job);
/* 113:    */     }
/* 114:    */   }
/* 115:    */   
/* 116:    */   public ThreadState getThreadState()
/* 117:    */   {
/* 118:171 */     return this.runnerState;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public void stop()
/* 122:    */   {
/* 123:179 */     this.runnerState = ThreadState.STOPPING;
/* 124:    */   }
/* 125:    */   
/* 126:    */   public void suspend()
/* 127:    */   {
/* 128:186 */     if (this.runnerState == ThreadState.RUNNING) {
/* 129:187 */       this.runnerState = ThreadState.SUSPENDED;
/* 130:    */     }
/* 131:    */   }
/* 132:    */   
/* 133:    */   public void resume()
/* 134:    */   {
/* 135:195 */     if (this.runnerState == ThreadState.SUSPENDED) {
/* 136:196 */       this.runnerState = ThreadState.RUNNING;
/* 137:    */     }
/* 138:    */   }
/* 139:    */   
/* 140:    */   public synchronized boolean allFinished()
/* 141:    */   {
/* 142:201 */     return this.jobsInProgress.isEmpty();
/* 143:    */   }
/* 144:    */   
/* 145:    */   public void run()
/* 146:    */   {
/* 147:    */     try
/* 148:    */     {
/* 149:213 */       this.runnerState = ThreadState.RUNNING;
/* 150:    */       for (;;)
/* 151:    */       {
/* 152:215 */         if (this.runnerState == ThreadState.SUSPENDED)
/* 153:    */         {
/* 154:    */           try
/* 155:    */           {
/* 156:217 */             Thread.sleep(5000L);
/* 157:    */           }
/* 158:    */           catch (Exception e) {}
/* 159:    */         }
/* 160:    */         else
/* 161:    */         {
/* 162:224 */           synchronized (this)
/* 163:    */           {
/* 164:225 */             Iterator<ControlledJob> it = this.jobsInProgress.iterator();
/* 165:226 */             while (it.hasNext())
/* 166:    */             {
/* 167:227 */               ControlledJob j = (ControlledJob)it.next();
/* 168:228 */               LOG.debug("Checking state of job " + j);
/* 169:229 */               switch (1.$SwitchMap$org$apache$hadoop$mapreduce$lib$jobcontrol$ControlledJob$State[j.checkState().ordinal()])
/* 170:    */               {
/* 171:    */               case 1: 
/* 172:231 */                 this.successfulJobs.add(j);
/* 173:232 */                 it.remove();
/* 174:233 */                 break;
/* 175:    */               case 2: 
/* 176:    */               case 3: 
/* 177:236 */                 this.failedJobs.add(j);
/* 178:237 */                 it.remove();
/* 179:238 */                 break;
/* 180:    */               case 4: 
/* 181:240 */                 j.submit();
/* 182:    */               }
/* 183:    */             }
/* 184:    */           }
/* 185:250 */           if ((this.runnerState == ThreadState.RUNNING) || (this.runnerState == ThreadState.SUSPENDED))
/* 186:    */           {
/* 187:    */             try
/* 188:    */             {
/* 189:255 */               Thread.sleep(5000L);
/* 190:    */             }
/* 191:    */             catch (Exception e) {}
/* 192:260 */             if ((this.runnerState != ThreadState.RUNNING) && (this.runnerState != ThreadState.SUSPENDED)) {
/* 193:    */               break;
/* 194:    */             }
/* 195:    */           }
/* 196:    */         }
/* 197:    */       }
/* 198:    */     }
/* 199:    */     catch (Throwable t)
/* 200:    */     {
/* 201:266 */       LOG.error("Error while trying to run jobs.", t);
/* 202:    */       
/* 203:268 */       failAllJobs(t);
/* 204:    */     }
/* 205:270 */     this.runnerState = ThreadState.STOPPED;
/* 206:    */   }
/* 207:    */   
/* 208:    */   private synchronized void failAllJobs(Throwable t)
/* 209:    */   {
/* 210:274 */     String message = "Unexpected System Error Occured: " + StringUtils.stringifyException(t);
/* 211:    */     
/* 212:276 */     Iterator<ControlledJob> it = this.jobsInProgress.iterator();
/* 213:277 */     while (it.hasNext())
/* 214:    */     {
/* 215:278 */       ControlledJob j = (ControlledJob)it.next();
/* 216:    */       try
/* 217:    */       {
/* 218:280 */         j.failJob(message);
/* 219:    */       }
/* 220:    */       catch (IOException e)
/* 221:    */       {
/* 222:282 */         LOG.error("Error while tyring to clean up " + j.getJobName(), e);
/* 223:    */       }
/* 224:    */       catch (InterruptedException e)
/* 225:    */       {
/* 226:284 */         LOG.error("Error while tyring to clean up " + j.getJobName(), e);
/* 227:    */       }
/* 228:    */       finally
/* 229:    */       {
/* 230:286 */         this.failedJobs.add(j);
/* 231:287 */         it.remove();
/* 232:    */       }
/* 233:    */     }
/* 234:    */   }
/* 235:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.lib.jobcontrol.JobControl
 * JD-Core Version:    0.7.0.1
 */