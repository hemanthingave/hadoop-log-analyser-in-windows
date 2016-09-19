/*  1:   */ package org.apache.hadoop.mapred;
/*  2:   */ 
/*  3:   */ import org.apache.hadoop.classification.InterfaceAudience.Private;
/*  4:   */ import org.apache.hadoop.classification.InterfaceStability.Unstable;
/*  5:   */ import org.apache.hadoop.mapreduce.JobID;
/*  6:   */ import org.apache.hadoop.util.Progressable;
/*  7:   */ 
/*  8:   */ @InterfaceAudience.Private
/*  9:   */ @InterfaceStability.Unstable
/* 10:   */ public class JobContextImpl
/* 11:   */   extends org.apache.hadoop.mapreduce.task.JobContextImpl
/* 12:   */   implements JobContext
/* 13:   */ {
/* 14:   */   private JobConf job;
/* 15:   */   private Progressable progress;
/* 16:   */   
/* 17:   */   public JobContextImpl(JobConf conf, JobID jobId, Progressable progress)
/* 18:   */   {
/* 19:34 */     super(conf, jobId);
/* 20:35 */     this.job = conf;
/* 21:36 */     this.progress = progress;
/* 22:   */   }
/* 23:   */   
/* 24:   */   public JobContextImpl(JobConf conf, JobID jobId)
/* 25:   */   {
/* 26:40 */     this(conf, jobId, Reporter.NULL);
/* 27:   */   }
/* 28:   */   
/* 29:   */   public JobConf getJobConf()
/* 30:   */   {
/* 31:49 */     return this.job;
/* 32:   */   }
/* 33:   */   
/* 34:   */   public Progressable getProgressible()
/* 35:   */   {
/* 36:58 */     return this.progress;
/* 37:   */   }
/* 38:   */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.JobContextImpl
 * JD-Core Version:    0.7.0.1
 */