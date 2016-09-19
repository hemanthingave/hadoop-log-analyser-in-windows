/*   1:    */ package org.apache.hadoop.mapred.jobcontrol;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import org.apache.commons.logging.Log;
/*   6:    */ import org.apache.commons.logging.LogFactory;
/*   7:    */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*   8:    */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*   9:    */ import org.apache.hadoop.mapred.JobClient;
/*  10:    */ import org.apache.hadoop.mapred.JobConf;
/*  11:    */ import org.apache.hadoop.mapreduce.lib.jobcontrol.ControlledJob;
/*  12:    */ import org.apache.hadoop.mapreduce.lib.jobcontrol.ControlledJob.State;
/*  13:    */ 
/*  14:    */ @InterfaceAudience.Public
/*  15:    */ @InterfaceStability.Stable
/*  16:    */ public class Job
/*  17:    */   extends ControlledJob
/*  18:    */ {
/*  19: 38 */   static final Log LOG = LogFactory.getLog(Job.class);
/*  20:    */   public static final int SUCCESS = 0;
/*  21:    */   public static final int WAITING = 1;
/*  22:    */   public static final int RUNNING = 2;
/*  23:    */   public static final int READY = 3;
/*  24:    */   public static final int FAILED = 4;
/*  25:    */   public static final int DEPENDENT_FAILED = 5;
/*  26:    */   
/*  27:    */   public Job(JobConf jobConf, ArrayList<?> dependingJobs)
/*  28:    */     throws IOException
/*  29:    */   {
/*  30: 54 */     super(new org.apache.hadoop.mapreduce.Job(jobConf), dependingJobs);
/*  31:    */   }
/*  32:    */   
/*  33:    */   public Job(JobConf conf)
/*  34:    */     throws IOException
/*  35:    */   {
/*  36: 59 */     super(conf);
/*  37:    */   }
/*  38:    */   
/*  39:    */   public org.apache.hadoop.mapred.JobID getAssignedJobID()
/*  40:    */   {
/*  41: 66 */     org.apache.hadoop.mapreduce.JobID temp = super.getMapredJobId();
/*  42: 67 */     if (temp == null) {
/*  43: 68 */       return null;
/*  44:    */     }
/*  45: 70 */     return org.apache.hadoop.mapred.JobID.downgrade(temp);
/*  46:    */   }
/*  47:    */   
/*  48:    */   @Deprecated
/*  49:    */   public void setAssignedJobID(org.apache.hadoop.mapred.JobID mapredJobID) {}
/*  50:    */   
/*  51:    */   public synchronized JobConf getJobConf()
/*  52:    */   {
/*  53: 86 */     return new JobConf(super.getJob().getConfiguration());
/*  54:    */   }
/*  55:    */   
/*  56:    */   public synchronized void setJobConf(JobConf jobConf)
/*  57:    */   {
/*  58:    */     try
/*  59:    */     {
/*  60: 96 */       super.setJob(new org.apache.hadoop.mapreduce.Job(jobConf));
/*  61:    */     }
/*  62:    */     catch (IOException ioe)
/*  63:    */     {
/*  64: 98 */       LOG.info("Exception" + ioe);
/*  65:    */     }
/*  66:    */   }
/*  67:    */   
/*  68:    */   public synchronized int getState()
/*  69:    */   {
/*  70:106 */     ControlledJob.State state = super.getJobState();
/*  71:107 */     if (state == ControlledJob.State.SUCCESS) {
/*  72:108 */       return 0;
/*  73:    */     }
/*  74:110 */     if (state == ControlledJob.State.WAITING) {
/*  75:111 */       return 1;
/*  76:    */     }
/*  77:113 */     if (state == ControlledJob.State.RUNNING) {
/*  78:114 */       return 2;
/*  79:    */     }
/*  80:116 */     if (state == ControlledJob.State.READY) {
/*  81:117 */       return 3;
/*  82:    */     }
/*  83:119 */     if (state == ControlledJob.State.FAILED) {
/*  84:120 */       return 4;
/*  85:    */     }
/*  86:122 */     if (state == ControlledJob.State.DEPENDENT_FAILED) {
/*  87:123 */       return 5;
/*  88:    */     }
/*  89:125 */     return -1;
/*  90:    */   }
/*  91:    */   
/*  92:    */   @Deprecated
/*  93:    */   protected synchronized void setState(int state) {}
/*  94:    */   
/*  95:    */   public synchronized boolean addDependingJob(Job dependingJob)
/*  96:    */   {
/*  97:149 */     return super.addDependingJob(dependingJob);
/*  98:    */   }
/*  99:    */   
/* 100:    */   public JobClient getJobClient()
/* 101:    */   {
/* 102:    */     try
/* 103:    */     {
/* 104:157 */       return new JobClient(super.getJob().getConfiguration());
/* 105:    */     }
/* 106:    */     catch (IOException ioe) {}
/* 107:159 */     return null;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public ArrayList<Job> getDependingJobs()
/* 111:    */   {
/* 112:167 */     return JobControl.castToJobList(super.getDependentJobs());
/* 113:    */   }
/* 114:    */   
/* 115:    */   public synchronized String getMapredJobID()
/* 116:    */   {
/* 117:174 */     if (super.getMapredJobId() != null) {
/* 118:175 */       return super.getMapredJobId().toString();
/* 119:    */     }
/* 120:177 */     return null;
/* 121:    */   }
/* 122:    */   
/* 123:    */   @Deprecated
/* 124:    */   public synchronized void setMapredJobID(String mapredJobID)
/* 125:    */   {
/* 126:189 */     setAssignedJobID(org.apache.hadoop.mapred.JobID.forName(mapredJobID));
/* 127:    */   }
/* 128:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.jobcontrol.Job
 * JD-Core Version:    0.7.0.1
 */