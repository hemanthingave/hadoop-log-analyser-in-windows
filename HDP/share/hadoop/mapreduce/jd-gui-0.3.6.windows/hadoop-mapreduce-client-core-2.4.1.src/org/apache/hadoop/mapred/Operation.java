/*  1:   */ package org.apache.hadoop.mapred;
/*  2:   */ 
/*  3:   */ import org.apache.hadoop.classification.InterfaceAudience.Private;
/*  4:   */ import org.apache.hadoop.mapreduce.JobACL;
/*  5:   */ 
/*  6:   */ @InterfaceAudience.Private
/*  7:   */ public enum Operation
/*  8:   */ {
/*  9:29 */   VIEW_JOB_COUNTERS(QueueACL.ADMINISTER_JOBS, JobACL.VIEW_JOB),  VIEW_JOB_DETAILS(QueueACL.ADMINISTER_JOBS, JobACL.VIEW_JOB),  VIEW_TASK_LOGS(QueueACL.ADMINISTER_JOBS, JobACL.VIEW_JOB),  KILL_JOB(QueueACL.ADMINISTER_JOBS, JobACL.MODIFY_JOB),  FAIL_TASK(QueueACL.ADMINISTER_JOBS, JobACL.MODIFY_JOB),  KILL_TASK(QueueACL.ADMINISTER_JOBS, JobACL.MODIFY_JOB),  SET_JOB_PRIORITY(QueueACL.ADMINISTER_JOBS, JobACL.MODIFY_JOB),  SUBMIT_JOB(QueueACL.SUBMIT_JOB, null);
/* 10:   */   
/* 11:   */   public QueueACL qACLNeeded;
/* 12:   */   public JobACL jobACLNeeded;
/* 13:   */   
/* 14:   */   private Operation(QueueACL qACL, JobACL jobACL)
/* 15:   */   {
/* 16:42 */     this.qACLNeeded = qACL;
/* 17:43 */     this.jobACLNeeded = jobACL;
/* 18:   */   }
/* 19:   */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.Operation
 * JD-Core Version:    0.7.0.1
 */