/*   1:    */ package org.apache.hadoop.mapreduce.lib.jobcontrol;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import java.util.List;
/*   6:    */ import org.apache.commons.logging.Log;
/*   7:    */ import org.apache.commons.logging.LogFactory;
/*   8:    */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*   9:    */ import org.apache.hadoop.classification.InterfaceStability.Evolving;
/*  10:    */ import org.apache.hadoop.conf.Configuration;
/*  11:    */ import org.apache.hadoop.fs.FileSystem;
/*  12:    */ import org.apache.hadoop.fs.Path;
/*  13:    */ import org.apache.hadoop.mapreduce.Job;
/*  14:    */ import org.apache.hadoop.mapreduce.JobID;
/*  15:    */ import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
/*  16:    */ import org.apache.hadoop.util.StringUtils;
/*  17:    */ 
/*  18:    */ @InterfaceAudience.Public
/*  19:    */ @InterfaceStability.Evolving
/*  20:    */ public class ControlledJob
/*  21:    */ {
/*  22: 52 */   private static final Log LOG = LogFactory.getLog(ControlledJob.class);
/*  23:    */   public static final String CREATE_DIR = "mapreduce.jobcontrol.createdir.ifnotexist";
/*  24:    */   private State state;
/*  25:    */   private String controlID;
/*  26:    */   private Job job;
/*  27:    */   private String message;
/*  28:    */   private List<ControlledJob> dependingJobs;
/*  29:    */   
/*  30:    */   public static enum State
/*  31:    */   {
/*  32: 55 */     SUCCESS,  WAITING,  RUNNING,  READY,  FAILED,  DEPENDENT_FAILED;
/*  33:    */     
/*  34:    */     private State() {}
/*  35:    */   }
/*  36:    */   
/*  37:    */   public ControlledJob(Job job, List<ControlledJob> dependingJobs)
/*  38:    */     throws IOException
/*  39:    */   {
/*  40: 73 */     this.job = job;
/*  41: 74 */     this.dependingJobs = dependingJobs;
/*  42: 75 */     this.state = State.WAITING;
/*  43: 76 */     this.controlID = "unassigned";
/*  44: 77 */     this.message = "just initialized";
/*  45:    */   }
/*  46:    */   
/*  47:    */   public ControlledJob(Configuration conf)
/*  48:    */     throws IOException
/*  49:    */   {
/*  50: 87 */     this(new Job(conf), null);
/*  51:    */   }
/*  52:    */   
/*  53:    */   public String toString()
/*  54:    */   {
/*  55: 92 */     StringBuffer sb = new StringBuffer();
/*  56: 93 */     sb.append("job name:\t").append(this.job.getJobName()).append("\n");
/*  57: 94 */     sb.append("job id:\t").append(this.controlID).append("\n");
/*  58: 95 */     sb.append("job state:\t").append(this.state).append("\n");
/*  59: 96 */     sb.append("job mapred id:\t").append(this.job.getJobID()).append("\n");
/*  60: 97 */     sb.append("job message:\t").append(this.message).append("\n");
/*  61: 99 */     if ((this.dependingJobs == null) || (this.dependingJobs.size() == 0))
/*  62:    */     {
/*  63:100 */       sb.append("job has no depending job:\t").append("\n");
/*  64:    */     }
/*  65:    */     else
/*  66:    */     {
/*  67:102 */       sb.append("job has ").append(this.dependingJobs.size()).append(" dependeng jobs:\n");
/*  68:104 */       for (int i = 0; i < this.dependingJobs.size(); i++)
/*  69:    */       {
/*  70:105 */         sb.append("\t depending job ").append(i).append(":\t");
/*  71:106 */         sb.append(((ControlledJob)this.dependingJobs.get(i)).getJobName()).append("\n");
/*  72:    */       }
/*  73:    */     }
/*  74:109 */     return sb.toString();
/*  75:    */   }
/*  76:    */   
/*  77:    */   public String getJobName()
/*  78:    */   {
/*  79:116 */     return this.job.getJobName();
/*  80:    */   }
/*  81:    */   
/*  82:    */   public void setJobName(String jobName)
/*  83:    */   {
/*  84:124 */     this.job.setJobName(jobName);
/*  85:    */   }
/*  86:    */   
/*  87:    */   public String getJobID()
/*  88:    */   {
/*  89:131 */     return this.controlID;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public void setJobID(String id)
/*  93:    */   {
/*  94:139 */     this.controlID = id;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public synchronized JobID getMapredJobId()
/*  98:    */   {
/*  99:146 */     return this.job.getJobID();
/* 100:    */   }
/* 101:    */   
/* 102:    */   public synchronized Job getJob()
/* 103:    */   {
/* 104:153 */     return this.job;
/* 105:    */   }
/* 106:    */   
/* 107:    */   public synchronized void setJob(Job job)
/* 108:    */   {
/* 109:161 */     this.job = job;
/* 110:    */   }
/* 111:    */   
/* 112:    */   public synchronized State getJobState()
/* 113:    */   {
/* 114:168 */     return this.state;
/* 115:    */   }
/* 116:    */   
/* 117:    */   protected synchronized void setJobState(State state)
/* 118:    */   {
/* 119:176 */     this.state = state;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public synchronized String getMessage()
/* 123:    */   {
/* 124:183 */     return this.message;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public synchronized void setMessage(String message)
/* 128:    */   {
/* 129:191 */     this.message = message;
/* 130:    */   }
/* 131:    */   
/* 132:    */   public List<ControlledJob> getDependentJobs()
/* 133:    */   {
/* 134:198 */     return this.dependingJobs;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public synchronized boolean addDependingJob(ControlledJob dependingJob)
/* 138:    */   {
/* 139:210 */     if (this.state == State.WAITING)
/* 140:    */     {
/* 141:211 */       if (this.dependingJobs == null) {
/* 142:212 */         this.dependingJobs = new ArrayList();
/* 143:    */       }
/* 144:214 */       return this.dependingJobs.add(dependingJob);
/* 145:    */     }
/* 146:216 */     return false;
/* 147:    */   }
/* 148:    */   
/* 149:    */   public synchronized boolean isCompleted()
/* 150:    */   {
/* 151:224 */     return (this.state == State.FAILED) || (this.state == State.DEPENDENT_FAILED) || (this.state == State.SUCCESS);
/* 152:    */   }
/* 153:    */   
/* 154:    */   public synchronized boolean isReady()
/* 155:    */   {
/* 156:233 */     return this.state == State.READY;
/* 157:    */   }
/* 158:    */   
/* 159:    */   public void killJob()
/* 160:    */     throws IOException, InterruptedException
/* 161:    */   {
/* 162:237 */     this.job.killJob();
/* 163:    */   }
/* 164:    */   
/* 165:    */   public synchronized void failJob(String message)
/* 166:    */     throws IOException, InterruptedException
/* 167:    */   {
/* 168:    */     try
/* 169:    */     {
/* 170:242 */       if ((this.job != null) && (this.state == State.RUNNING)) {
/* 171:243 */         this.job.killJob();
/* 172:    */       }
/* 173:    */     }
/* 174:    */     finally
/* 175:    */     {
/* 176:246 */       this.state = State.FAILED;
/* 177:247 */       this.message = message;
/* 178:    */     }
/* 179:    */   }
/* 180:    */   
/* 181:    */   private void checkRunningState()
/* 182:    */     throws IOException, InterruptedException
/* 183:    */   {
/* 184:    */     try
/* 185:    */     {
/* 186:257 */       if (this.job.isComplete()) {
/* 187:258 */         if (this.job.isSuccessful())
/* 188:    */         {
/* 189:259 */           this.state = State.SUCCESS;
/* 190:    */         }
/* 191:    */         else
/* 192:    */         {
/* 193:261 */           this.state = State.FAILED;
/* 194:262 */           this.message = "Job failed!";
/* 195:    */         }
/* 196:    */       }
/* 197:    */     }
/* 198:    */     catch (IOException ioe)
/* 199:    */     {
/* 200:266 */       this.state = State.FAILED;
/* 201:267 */       this.message = StringUtils.stringifyException(ioe);
/* 202:    */       try
/* 203:    */       {
/* 204:269 */         if (this.job != null) {
/* 205:270 */           this.job.killJob();
/* 206:    */         }
/* 207:    */       }
/* 208:    */       catch (IOException e) {}
/* 209:    */     }
/* 210:    */   }
/* 211:    */   
/* 212:    */   synchronized State checkState()
/* 213:    */     throws IOException, InterruptedException
/* 214:    */   {
/* 215:281 */     if (this.state == State.RUNNING) {
/* 216:282 */       checkRunningState();
/* 217:    */     }
/* 218:284 */     if (this.state != State.WAITING) {
/* 219:285 */       return this.state;
/* 220:    */     }
/* 221:287 */     if ((this.dependingJobs == null) || (this.dependingJobs.size() == 0))
/* 222:    */     {
/* 223:288 */       this.state = State.READY;
/* 224:289 */       return this.state;
/* 225:    */     }
/* 226:291 */     ControlledJob pred = null;
/* 227:292 */     int n = this.dependingJobs.size();
/* 228:293 */     for (int i = 0; i < n; i++)
/* 229:    */     {
/* 230:294 */       pred = (ControlledJob)this.dependingJobs.get(i);
/* 231:295 */       State s = pred.checkState();
/* 232:296 */       if ((s == State.WAITING) || (s == State.READY) || (s == State.RUNNING)) {
/* 233:    */         break;
/* 234:    */       }
/* 235:300 */       if ((s == State.FAILED) || (s == State.DEPENDENT_FAILED))
/* 236:    */       {
/* 237:301 */         this.state = State.DEPENDENT_FAILED;
/* 238:302 */         this.message = ("depending job " + i + " with jobID " + pred.getJobID() + " failed. " + pred.getMessage());
/* 239:    */         
/* 240:304 */         break;
/* 241:    */       }
/* 242:307 */       if (i == n - 1) {
/* 243:308 */         this.state = State.READY;
/* 244:    */       }
/* 245:    */     }
/* 246:312 */     return this.state;
/* 247:    */   }
/* 248:    */   
/* 249:    */   protected synchronized void submit()
/* 250:    */   {
/* 251:    */     try
/* 252:    */     {
/* 253:321 */       Configuration conf = this.job.getConfiguration();
/* 254:322 */       if (conf.getBoolean("mapreduce.jobcontrol.createdir.ifnotexist", false))
/* 255:    */       {
/* 256:323 */         FileSystem fs = FileSystem.get(conf);
/* 257:324 */         Path[] inputPaths = FileInputFormat.getInputPaths(this.job);
/* 258:325 */         for (int i = 0; i < inputPaths.length; i++) {
/* 259:326 */           if (!fs.exists(inputPaths[i])) {
/* 260:    */             try
/* 261:    */             {
/* 262:328 */               fs.mkdirs(inputPaths[i]);
/* 263:    */             }
/* 264:    */             catch (IOException e) {}
/* 265:    */           }
/* 266:    */         }
/* 267:    */       }
/* 268:335 */       this.job.submit();
/* 269:336 */       this.state = State.RUNNING;
/* 270:    */     }
/* 271:    */     catch (Exception ioe)
/* 272:    */     {
/* 273:338 */       LOG.info(getJobName() + " got an error while submitting ", ioe);
/* 274:339 */       this.state = State.FAILED;
/* 275:340 */       this.message = StringUtils.stringifyException(ioe);
/* 276:    */     }
/* 277:    */   }
/* 278:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.lib.jobcontrol.ControlledJob
 * JD-Core Version:    0.7.0.1
 */