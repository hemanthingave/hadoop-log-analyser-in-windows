/*   1:    */ package org.apache.hadoop.mapred;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.util.concurrent.Delayed;
/*   5:    */ import java.util.concurrent.TimeUnit;
/*   6:    */ import org.apache.commons.httpclient.HttpClient;
/*   7:    */ import org.apache.commons.httpclient.HttpMethod;
/*   8:    */ import org.apache.commons.httpclient.URI;
/*   9:    */ import org.apache.commons.httpclient.methods.GetMethod;
/*  10:    */ import org.apache.commons.httpclient.params.HttpClientParams;
/*  11:    */ import org.apache.commons.logging.Log;
/*  12:    */ import org.apache.commons.logging.LogFactory;
/*  13:    */ import org.apache.hadoop.classification.InterfaceAudience.Private;
/*  14:    */ import org.apache.hadoop.classification.InterfaceStability.Unstable;
/*  15:    */ 
/*  16:    */ @InterfaceAudience.Private
/*  17:    */ @InterfaceStability.Unstable
/*  18:    */ public class JobEndNotifier
/*  19:    */ {
/*  20: 37 */   private static final Log LOG = LogFactory.getLog(JobEndNotifier.class.getName());
/*  21:    */   
/*  22:    */   private static JobEndStatusInfo createNotification(JobConf conf, JobStatus status)
/*  23:    */   {
/*  24: 44 */     JobEndStatusInfo notification = null;
/*  25: 45 */     String uri = conf.getJobEndNotificationURI();
/*  26: 46 */     if (uri != null)
/*  27:    */     {
/*  28: 47 */       int retryAttempts = conf.getInt("mapreduce.job.end-notification.retry.attempts", 0);
/*  29: 48 */       long retryInterval = conf.getInt("mapreduce.job.end-notification.retry.interval", 30000);
/*  30: 49 */       int timeout = conf.getInt("mapreduce.job.end-notification.timeout", 5000);
/*  31: 51 */       if (uri.contains("$jobId")) {
/*  32: 52 */         uri = uri.replace("$jobId", status.getJobID().toString());
/*  33:    */       }
/*  34: 54 */       if (uri.contains("$jobStatus"))
/*  35:    */       {
/*  36: 55 */         String statusStr = status.getRunState() == JobStatus.FAILED ? "FAILED" : status.getRunState() == JobStatus.SUCCEEDED ? "SUCCEEDED" : "KILLED";
/*  37:    */         
/*  38:    */ 
/*  39: 58 */         uri = uri.replace("$jobStatus", statusStr);
/*  40:    */       }
/*  41: 60 */       notification = new JobEndStatusInfo(uri, retryAttempts, retryInterval, timeout);
/*  42:    */     }
/*  43: 63 */     return notification;
/*  44:    */   }
/*  45:    */   
/*  46:    */   private static int httpNotification(String uri, int timeout)
/*  47:    */     throws IOException
/*  48:    */   {
/*  49: 68 */     URI url = new URI(uri, false);
/*  50: 69 */     HttpClient httpClient = new HttpClient();
/*  51: 70 */     httpClient.getParams().setSoTimeout(timeout);
/*  52: 71 */     httpClient.getParams().setConnectionManagerTimeout(timeout);
/*  53:    */     
/*  54: 73 */     HttpMethod method = new GetMethod(url.getEscapedURI());
/*  55: 74 */     method.setRequestHeader("Accept", "*/*");
/*  56: 75 */     return httpClient.executeMethod(method);
/*  57:    */   }
/*  58:    */   
/*  59:    */   public static void localRunnerNotification(JobConf conf, JobStatus status)
/*  60:    */   {
/*  61: 81 */     JobEndStatusInfo notification = createNotification(conf, status);
/*  62: 82 */     if (notification != null) {
/*  63:    */       do
/*  64:    */       {
/*  65:    */         try
/*  66:    */         {
/*  67: 85 */           int code = httpNotification(notification.getUri(), notification.getTimeout());
/*  68: 87 */           if (code != 200) {
/*  69: 88 */             throw new IOException("Invalid response status code: " + code);
/*  70:    */           }
/*  71:    */         }
/*  72:    */         catch (IOException ioex)
/*  73:    */         {
/*  74: 95 */           LOG.error("Notification error [" + notification.getUri() + "]", ioex);
/*  75:    */         }
/*  76:    */         catch (Exception ex)
/*  77:    */         {
/*  78: 98 */           LOG.error("Notification error [" + notification.getUri() + "]", ex);
/*  79:    */         }
/*  80:    */         try
/*  81:    */         {
/*  82:101 */           Thread.sleep(notification.getRetryInterval());
/*  83:    */         }
/*  84:    */         catch (InterruptedException iex)
/*  85:    */         {
/*  86:104 */           LOG.error("Notification retry error [" + notification + "]", iex);
/*  87:    */         }
/*  88:106 */       } while (notification.configureForRetry());
/*  89:    */     }
/*  90:    */   }
/*  91:    */   
/*  92:    */   private static class JobEndStatusInfo
/*  93:    */     implements Delayed
/*  94:    */   {
/*  95:    */     private String uri;
/*  96:    */     private int retryAttempts;
/*  97:    */     private long retryInterval;
/*  98:    */     private long delayTime;
/*  99:    */     private int timeout;
/* 100:    */     
/* 101:    */     JobEndStatusInfo(String uri, int retryAttempts, long retryInterval, int timeout)
/* 102:    */     {
/* 103:119 */       this.uri = uri;
/* 104:120 */       this.retryAttempts = retryAttempts;
/* 105:121 */       this.retryInterval = retryInterval;
/* 106:122 */       this.delayTime = System.currentTimeMillis();
/* 107:123 */       this.timeout = timeout;
/* 108:    */     }
/* 109:    */     
/* 110:    */     public String getUri()
/* 111:    */     {
/* 112:127 */       return this.uri;
/* 113:    */     }
/* 114:    */     
/* 115:    */     public int getRetryAttempts()
/* 116:    */     {
/* 117:131 */       return this.retryAttempts;
/* 118:    */     }
/* 119:    */     
/* 120:    */     public long getRetryInterval()
/* 121:    */     {
/* 122:135 */       return this.retryInterval;
/* 123:    */     }
/* 124:    */     
/* 125:    */     public int getTimeout()
/* 126:    */     {
/* 127:139 */       return this.timeout;
/* 128:    */     }
/* 129:    */     
/* 130:    */     public boolean configureForRetry()
/* 131:    */     {
/* 132:143 */       boolean retry = false;
/* 133:144 */       if (getRetryAttempts() > 0)
/* 134:    */       {
/* 135:145 */         retry = true;
/* 136:146 */         this.delayTime = (System.currentTimeMillis() + this.retryInterval);
/* 137:    */       }
/* 138:148 */       this.retryAttempts -= 1;
/* 139:149 */       return retry;
/* 140:    */     }
/* 141:    */     
/* 142:    */     public long getDelay(TimeUnit unit)
/* 143:    */     {
/* 144:153 */       long n = this.delayTime - System.currentTimeMillis();
/* 145:154 */       return unit.convert(n, TimeUnit.MILLISECONDS);
/* 146:    */     }
/* 147:    */     
/* 148:    */     public int compareTo(Delayed d)
/* 149:    */     {
/* 150:158 */       return (int)(this.delayTime - ((JobEndStatusInfo)d).delayTime);
/* 151:    */     }
/* 152:    */     
/* 153:    */     public boolean equals(Object o)
/* 154:    */     {
/* 155:163 */       if (!(o instanceof JobEndStatusInfo)) {
/* 156:164 */         return false;
/* 157:    */       }
/* 158:166 */       if (this.delayTime == ((JobEndStatusInfo)o).delayTime) {
/* 159:167 */         return true;
/* 160:    */       }
/* 161:169 */       return false;
/* 162:    */     }
/* 163:    */     
/* 164:    */     public int hashCode()
/* 165:    */     {
/* 166:174 */       return 629 + (int)(this.delayTime ^ this.delayTime >>> 32);
/* 167:    */     }
/* 168:    */     
/* 169:    */     public String toString()
/* 170:    */     {
/* 171:179 */       return "URL: " + this.uri + " remaining retries: " + this.retryAttempts + " interval: " + this.retryInterval;
/* 172:    */     }
/* 173:    */   }
/* 174:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.JobEndNotifier
 * JD-Core Version:    0.7.0.1
 */