/*  1:   */ package org.apache.hadoop.mapreduce;
/*  2:   */ 
/*  3:   */ import org.apache.hadoop.classification.InterfaceAudience.Private;
/*  4:   */ 
/*  5:   */ @InterfaceAudience.Private
/*  6:   */ public enum JobACL
/*  7:   */ {
/*  8:32 */   VIEW_JOB("mapreduce.job.acl-view-job"),  MODIFY_JOB("mapreduce.job.acl-modify-job");
/*  9:   */   
/* 10:   */   String aclName;
/* 11:   */   
/* 12:   */   private JobACL(String name)
/* 13:   */   {
/* 14:44 */     this.aclName = name;
/* 15:   */   }
/* 16:   */   
/* 17:   */   public String getAclName()
/* 18:   */   {
/* 19:54 */     return this.aclName;
/* 20:   */   }
/* 21:   */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.JobACL
 * JD-Core Version:    0.7.0.1
 */