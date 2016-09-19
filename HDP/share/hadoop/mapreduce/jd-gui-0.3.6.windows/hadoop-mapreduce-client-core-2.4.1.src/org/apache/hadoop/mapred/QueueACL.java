/*  1:   */ package org.apache.hadoop.mapred;
/*  2:   */ 
/*  3:   */ import org.apache.hadoop.classification.InterfaceAudience.Private;
/*  4:   */ 
/*  5:   */ @InterfaceAudience.Private
/*  6:   */ public enum QueueACL
/*  7:   */ {
/*  8:29 */   SUBMIT_JOB("acl-submit-job"),  ADMINISTER_JOBS("acl-administer-jobs");
/*  9:   */   
/* 10:   */   private final String aclName;
/* 11:   */   
/* 12:   */   private QueueACL(String aclName)
/* 13:   */   {
/* 14:42 */     this.aclName = aclName;
/* 15:   */   }
/* 16:   */   
/* 17:   */   public final String getAclName()
/* 18:   */   {
/* 19:46 */     return this.aclName;
/* 20:   */   }
/* 21:   */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.QueueACL
 * JD-Core Version:    0.7.0.1
 */