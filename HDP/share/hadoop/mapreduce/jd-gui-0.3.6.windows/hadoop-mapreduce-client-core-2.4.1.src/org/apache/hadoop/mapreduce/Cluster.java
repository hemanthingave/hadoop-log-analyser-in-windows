/*   1:    */ package org.apache.hadoop.mapreduce;
/*   2:    */ 
/*   3:    */ import java.io.FileNotFoundException;
/*   4:    */ import java.io.IOException;
/*   5:    */ import java.net.InetSocketAddress;
/*   6:    */ import java.security.PrivilegedExceptionAction;
/*   7:    */ import java.util.ArrayList;
/*   8:    */ import java.util.List;
/*   9:    */ import java.util.ServiceLoader;
/*  10:    */ import org.apache.commons.logging.Log;
/*  11:    */ import org.apache.commons.logging.LogFactory;
/*  12:    */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*  13:    */ import org.apache.hadoop.classification.InterfaceStability.Evolving;
/*  14:    */ import org.apache.hadoop.conf.Configuration;
/*  15:    */ import org.apache.hadoop.fs.FileSystem;
/*  16:    */ import org.apache.hadoop.fs.Path;
/*  17:    */ import org.apache.hadoop.io.Text;
/*  18:    */ import org.apache.hadoop.mapred.JobConf;
/*  19:    */ import org.apache.hadoop.mapreduce.protocol.ClientProtocol;
/*  20:    */ import org.apache.hadoop.mapreduce.protocol.ClientProtocolProvider;
/*  21:    */ import org.apache.hadoop.mapreduce.security.token.delegation.DelegationTokenIdentifier;
/*  22:    */ import org.apache.hadoop.mapreduce.util.ConfigUtil;
/*  23:    */ import org.apache.hadoop.mapreduce.v2.LogParams;
/*  24:    */ import org.apache.hadoop.security.UserGroupInformation;
/*  25:    */ import org.apache.hadoop.security.token.SecretManager.InvalidToken;
/*  26:    */ import org.apache.hadoop.security.token.Token;
/*  27:    */ 
/*  28:    */ @InterfaceAudience.Public
/*  29:    */ @InterfaceStability.Evolving
/*  30:    */ public class Cluster
/*  31:    */ {
/*  32:    */   private ClientProtocolProvider clientProtocolProvider;
/*  33:    */   private ClientProtocol client;
/*  34:    */   private UserGroupInformation ugi;
/*  35:    */   private Configuration conf;
/*  36:    */   
/*  37:    */   @InterfaceStability.Evolving
/*  38:    */   public static enum JobTrackerStatus
/*  39:    */   {
/*  40: 55 */     INITIALIZING,  RUNNING;
/*  41:    */     
/*  42:    */     private JobTrackerStatus() {}
/*  43:    */   }
/*  44:    */   
/*  45: 61 */   private FileSystem fs = null;
/*  46: 62 */   private Path sysDir = null;
/*  47: 63 */   private Path stagingAreaDir = null;
/*  48: 64 */   private Path jobHistoryDir = null;
/*  49: 65 */   private static final Log LOG = LogFactory.getLog(Cluster.class);
/*  50: 67 */   private static ServiceLoader<ClientProtocolProvider> frameworkLoader = ServiceLoader.load(ClientProtocolProvider.class);
/*  51:    */   
/*  52:    */   static
/*  53:    */   {
/*  54: 71 */     ConfigUtil.loadResources();
/*  55:    */   }
/*  56:    */   
/*  57:    */   public Cluster(Configuration conf)
/*  58:    */     throws IOException
/*  59:    */   {
/*  60: 75 */     this(null, conf);
/*  61:    */   }
/*  62:    */   
/*  63:    */   public Cluster(InetSocketAddress jobTrackAddr, Configuration conf)
/*  64:    */     throws IOException
/*  65:    */   {
/*  66: 80 */     this.conf = conf;
/*  67: 81 */     this.ugi = UserGroupInformation.getCurrentUser();
/*  68: 82 */     initialize(jobTrackAddr, conf);
/*  69:    */   }
/*  70:    */   
/*  71:    */   private void initialize(InetSocketAddress jobTrackAddr, Configuration conf)
/*  72:    */     throws IOException
/*  73:    */   {
/*  74: 88 */     synchronized (frameworkLoader)
/*  75:    */     {
/*  76: 89 */       for (ClientProtocolProvider provider : frameworkLoader)
/*  77:    */       {
/*  78: 90 */         LOG.debug("Trying ClientProtocolProvider : " + provider.getClass().getName());
/*  79:    */         
/*  80: 92 */         ClientProtocol clientProtocol = null;
/*  81:    */         try
/*  82:    */         {
/*  83: 94 */           if (jobTrackAddr == null) {
/*  84: 95 */             clientProtocol = provider.create(conf);
/*  85:    */           } else {
/*  86: 97 */             clientProtocol = provider.create(jobTrackAddr, conf);
/*  87:    */           }
/*  88:100 */           if (clientProtocol != null)
/*  89:    */           {
/*  90:101 */             this.clientProtocolProvider = provider;
/*  91:102 */             this.client = clientProtocol;
/*  92:103 */             LOG.debug("Picked " + provider.getClass().getName() + " as the ClientProtocolProvider");
/*  93:    */             
/*  94:105 */             break;
/*  95:    */           }
/*  96:108 */           LOG.debug("Cannot pick " + provider.getClass().getName() + " as the ClientProtocolProvider - returned null protocol");
/*  97:    */         }
/*  98:    */         catch (Exception e)
/*  99:    */         {
/* 100:113 */           LOG.info("Failed to use " + provider.getClass().getName() + " due to error: " + e.getMessage());
/* 101:    */         }
/* 102:    */       }
/* 103:    */     }
/* 104:119 */     if ((null == this.clientProtocolProvider) || (null == this.client)) {
/* 105:120 */       throw new IOException("Cannot initialize Cluster. Please check your configuration for mapreduce.framework.name and the correspond server addresses.");
/* 106:    */     }
/* 107:    */   }
/* 108:    */   
/* 109:    */   ClientProtocol getClient()
/* 110:    */   {
/* 111:128 */     return this.client;
/* 112:    */   }
/* 113:    */   
/* 114:    */   Configuration getConf()
/* 115:    */   {
/* 116:132 */     return this.conf;
/* 117:    */   }
/* 118:    */   
/* 119:    */   public synchronized void close()
/* 120:    */     throws IOException
/* 121:    */   {
/* 122:139 */     this.clientProtocolProvider.close(this.client);
/* 123:    */   }
/* 124:    */   
/* 125:    */   private Job[] getJobs(JobStatus[] stats)
/* 126:    */     throws IOException
/* 127:    */   {
/* 128:143 */     List<Job> jobs = new ArrayList();
/* 129:144 */     for (JobStatus stat : stats) {
/* 130:145 */       jobs.add(Job.getInstance(this, stat, new JobConf(stat.getJobFile())));
/* 131:    */     }
/* 132:147 */     return (Job[])jobs.toArray(new Job[0]);
/* 133:    */   }
/* 134:    */   
/* 135:    */   public synchronized FileSystem getFileSystem()
/* 136:    */     throws IOException, InterruptedException
/* 137:    */   {
/* 138:159 */     if (this.fs == null) {
/* 139:    */       try
/* 140:    */       {
/* 141:161 */         this.fs = ((FileSystem)this.ugi.doAs(new PrivilegedExceptionAction()
/* 142:    */         {
/* 143:    */           public FileSystem run()
/* 144:    */             throws IOException, InterruptedException
/* 145:    */           {
/* 146:163 */             Path sysDir = new Path(Cluster.this.client.getSystemDir());
/* 147:164 */             return sysDir.getFileSystem(Cluster.this.getConf());
/* 148:    */           }
/* 149:    */         }));
/* 150:    */       }
/* 151:    */       catch (InterruptedException e)
/* 152:    */       {
/* 153:168 */         throw new RuntimeException(e);
/* 154:    */       }
/* 155:    */     }
/* 156:171 */     return this.fs;
/* 157:    */   }
/* 158:    */   
/* 159:    */   public Job getJob(JobID jobId)
/* 160:    */     throws IOException, InterruptedException
/* 161:    */   {
/* 162:183 */     JobStatus status = this.client.getJobStatus(jobId);
/* 163:184 */     if (status != null)
/* 164:    */     {
/* 165:    */       JobConf conf;
/* 166:    */       try
/* 167:    */       {
/* 168:187 */         conf = new JobConf(status.getJobFile());
/* 169:    */       }
/* 170:    */       catch (RuntimeException ex)
/* 171:    */       {
/* 172:190 */         if ((ex.getCause() instanceof FileNotFoundException)) {
/* 173:191 */           return null;
/* 174:    */         }
/* 175:193 */         throw ex;
/* 176:    */       }
/* 177:196 */       return Job.getInstance(this, status, conf);
/* 178:    */     }
/* 179:198 */     return null;
/* 180:    */   }
/* 181:    */   
/* 182:    */   public QueueInfo[] getQueues()
/* 183:    */     throws IOException, InterruptedException
/* 184:    */   {
/* 185:209 */     return this.client.getQueues();
/* 186:    */   }
/* 187:    */   
/* 188:    */   public QueueInfo getQueue(String name)
/* 189:    */     throws IOException, InterruptedException
/* 190:    */   {
/* 191:222 */     return this.client.getQueue(name);
/* 192:    */   }
/* 193:    */   
/* 194:    */   public LogParams getLogParams(JobID jobID, TaskAttemptID taskAttemptID)
/* 195:    */     throws IOException, InterruptedException
/* 196:    */   {
/* 197:235 */     return this.client.getLogFileParams(jobID, taskAttemptID);
/* 198:    */   }
/* 199:    */   
/* 200:    */   public ClusterMetrics getClusterStatus()
/* 201:    */     throws IOException, InterruptedException
/* 202:    */   {
/* 203:246 */     return this.client.getClusterMetrics();
/* 204:    */   }
/* 205:    */   
/* 206:    */   public TaskTrackerInfo[] getActiveTaskTrackers()
/* 207:    */     throws IOException, InterruptedException
/* 208:    */   {
/* 209:258 */     return this.client.getActiveTrackers();
/* 210:    */   }
/* 211:    */   
/* 212:    */   public TaskTrackerInfo[] getBlackListedTaskTrackers()
/* 213:    */     throws IOException, InterruptedException
/* 214:    */   {
/* 215:270 */     return this.client.getBlacklistedTrackers();
/* 216:    */   }
/* 217:    */   
/* 218:    */   @Deprecated
/* 219:    */   public Job[] getAllJobs()
/* 220:    */     throws IOException, InterruptedException
/* 221:    */   {
/* 222:283 */     return getJobs(this.client.getAllJobs());
/* 223:    */   }
/* 224:    */   
/* 225:    */   public JobStatus[] getAllJobStatuses()
/* 226:    */     throws IOException, InterruptedException
/* 227:    */   {
/* 228:293 */     return this.client.getAllJobs();
/* 229:    */   }
/* 230:    */   
/* 231:    */   public Path getSystemDir()
/* 232:    */     throws IOException, InterruptedException
/* 233:    */   {
/* 234:303 */     if (this.sysDir == null) {
/* 235:304 */       this.sysDir = new Path(this.client.getSystemDir());
/* 236:    */     }
/* 237:306 */     return this.sysDir;
/* 238:    */   }
/* 239:    */   
/* 240:    */   public Path getStagingAreaDir()
/* 241:    */     throws IOException, InterruptedException
/* 242:    */   {
/* 243:316 */     if (this.stagingAreaDir == null) {
/* 244:317 */       this.stagingAreaDir = new Path(this.client.getStagingAreaDir());
/* 245:    */     }
/* 246:319 */     return this.stagingAreaDir;
/* 247:    */   }
/* 248:    */   
/* 249:    */   public String getJobHistoryUrl(JobID jobId)
/* 250:    */     throws IOException, InterruptedException
/* 251:    */   {
/* 252:333 */     if (this.jobHistoryDir == null) {
/* 253:334 */       this.jobHistoryDir = new Path(this.client.getJobHistoryDir());
/* 254:    */     }
/* 255:336 */     return new Path(this.jobHistoryDir, jobId.toString() + "_" + this.ugi.getShortUserName()).toString();
/* 256:    */   }
/* 257:    */   
/* 258:    */   public QueueAclsInfo[] getQueueAclsForCurrentUser()
/* 259:    */     throws IOException, InterruptedException
/* 260:    */   {
/* 261:347 */     return this.client.getQueueAclsForCurrentUser();
/* 262:    */   }
/* 263:    */   
/* 264:    */   public QueueInfo[] getRootQueues()
/* 265:    */     throws IOException, InterruptedException
/* 266:    */   {
/* 267:356 */     return this.client.getRootQueues();
/* 268:    */   }
/* 269:    */   
/* 270:    */   public QueueInfo[] getChildQueues(String queueName)
/* 271:    */     throws IOException, InterruptedException
/* 272:    */   {
/* 273:367 */     return this.client.getChildQueues(queueName);
/* 274:    */   }
/* 275:    */   
/* 276:    */   public JobTrackerStatus getJobTrackerStatus()
/* 277:    */     throws IOException, InterruptedException
/* 278:    */   {
/* 279:379 */     return this.client.getJobTrackerStatus();
/* 280:    */   }
/* 281:    */   
/* 282:    */   public long getTaskTrackerExpiryInterval()
/* 283:    */     throws IOException, InterruptedException
/* 284:    */   {
/* 285:388 */     return this.client.getTaskTrackerExpiryInterval();
/* 286:    */   }
/* 287:    */   
/* 288:    */   public Token<DelegationTokenIdentifier> getDelegationToken(Text renewer)
/* 289:    */     throws IOException, InterruptedException
/* 290:    */   {
/* 291:400 */     return this.client.getDelegationToken(renewer);
/* 292:    */   }
/* 293:    */   
/* 294:    */   /**
/* 295:    */    * @deprecated
/* 296:    */    */
/* 297:    */   public long renewDelegationToken(Token<DelegationTokenIdentifier> token)
/* 298:    */     throws SecretManager.InvalidToken, IOException, InterruptedException
/* 299:    */   {
/* 300:414 */     return token.renew(getConf());
/* 301:    */   }
/* 302:    */   
/* 303:    */   /**
/* 304:    */    * @deprecated
/* 305:    */    */
/* 306:    */   public void cancelDelegationToken(Token<DelegationTokenIdentifier> token)
/* 307:    */     throws IOException, InterruptedException
/* 308:    */   {
/* 309:426 */     token.cancel(getConf());
/* 310:    */   }
/* 311:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.Cluster
 * JD-Core Version:    0.7.0.1
 */