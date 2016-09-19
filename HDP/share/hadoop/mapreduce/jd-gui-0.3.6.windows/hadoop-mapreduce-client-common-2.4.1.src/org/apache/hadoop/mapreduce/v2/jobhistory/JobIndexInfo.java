/*   1:    */ package org.apache.hadoop.mapreduce.v2.jobhistory;
/*   2:    */ 
/*   3:    */ import org.apache.hadoop.mapreduce.v2.api.records.JobId;
/*   4:    */ 
/*   5:    */ public class JobIndexInfo
/*   6:    */ {
/*   7:    */   private long submitTime;
/*   8:    */   private long finishTime;
/*   9:    */   private String user;
/*  10:    */   private String queueName;
/*  11:    */   private String jobName;
/*  12:    */   private JobId jobId;
/*  13:    */   private int numMaps;
/*  14:    */   private int numReduces;
/*  15:    */   private String jobStatus;
/*  16:    */   private long jobStartTime;
/*  17:    */   
/*  18:    */   public JobIndexInfo() {}
/*  19:    */   
/*  20:    */   public JobIndexInfo(long submitTime, long finishTime, String user, String jobName, JobId jobId, int numMaps, int numReduces, String jobStatus)
/*  21:    */   {
/*  22: 45 */     this(submitTime, finishTime, user, jobName, jobId, numMaps, numReduces, jobStatus, "default");
/*  23:    */   }
/*  24:    */   
/*  25:    */   public JobIndexInfo(long submitTime, long finishTime, String user, String jobName, JobId jobId, int numMaps, int numReduces, String jobStatus, String queueName)
/*  26:    */   {
/*  27: 52 */     this.submitTime = submitTime;
/*  28: 53 */     this.finishTime = finishTime;
/*  29: 54 */     this.user = user;
/*  30: 55 */     this.jobName = jobName;
/*  31: 56 */     this.jobId = jobId;
/*  32: 57 */     this.numMaps = numMaps;
/*  33: 58 */     this.numReduces = numReduces;
/*  34: 59 */     this.jobStatus = jobStatus;
/*  35: 60 */     this.jobStartTime = -1L;
/*  36: 61 */     this.queueName = queueName;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public long getSubmitTime()
/*  40:    */   {
/*  41: 65 */     return this.submitTime;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public void setSubmitTime(long submitTime)
/*  45:    */   {
/*  46: 68 */     this.submitTime = submitTime;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public long getFinishTime()
/*  50:    */   {
/*  51: 71 */     return this.finishTime;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public void setFinishTime(long finishTime)
/*  55:    */   {
/*  56: 74 */     this.finishTime = finishTime;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public String getUser()
/*  60:    */   {
/*  61: 77 */     return this.user;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public void setUser(String user)
/*  65:    */   {
/*  66: 80 */     this.user = user;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public String getQueueName()
/*  70:    */   {
/*  71: 83 */     return this.queueName;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public void setQueueName(String queueName)
/*  75:    */   {
/*  76: 86 */     this.queueName = queueName;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public String getJobName()
/*  80:    */   {
/*  81: 89 */     return this.jobName;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public void setJobName(String jobName)
/*  85:    */   {
/*  86: 92 */     this.jobName = jobName;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public JobId getJobId()
/*  90:    */   {
/*  91: 95 */     return this.jobId;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public void setJobId(JobId jobId)
/*  95:    */   {
/*  96: 98 */     this.jobId = jobId;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public int getNumMaps()
/* 100:    */   {
/* 101:101 */     return this.numMaps;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public void setNumMaps(int numMaps)
/* 105:    */   {
/* 106:104 */     this.numMaps = numMaps;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public int getNumReduces()
/* 110:    */   {
/* 111:107 */     return this.numReduces;
/* 112:    */   }
/* 113:    */   
/* 114:    */   public void setNumReduces(int numReduces)
/* 115:    */   {
/* 116:110 */     this.numReduces = numReduces;
/* 117:    */   }
/* 118:    */   
/* 119:    */   public String getJobStatus()
/* 120:    */   {
/* 121:113 */     return this.jobStatus;
/* 122:    */   }
/* 123:    */   
/* 124:    */   public void setJobStatus(String jobStatus)
/* 125:    */   {
/* 126:116 */     this.jobStatus = jobStatus;
/* 127:    */   }
/* 128:    */   
/* 129:    */   public long getJobStartTime()
/* 130:    */   {
/* 131:119 */     return this.jobStartTime;
/* 132:    */   }
/* 133:    */   
/* 134:    */   public void setJobStartTime(long lTime)
/* 135:    */   {
/* 136:122 */     this.jobStartTime = lTime;
/* 137:    */   }
/* 138:    */   
/* 139:    */   public String toString()
/* 140:    */   {
/* 141:127 */     return "JobIndexInfo [submitTime=" + this.submitTime + ", finishTime=" + this.finishTime + ", user=" + this.user + ", jobName=" + this.jobName + ", jobId=" + this.jobId + ", numMaps=" + this.numMaps + ", numReduces=" + this.numReduces + ", jobStatus=" + this.jobStatus + "]";
/* 142:    */   }
/* 143:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-common-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.v2.jobhistory.JobIndexInfo
 * JD-Core Version:    0.7.0.1
 */