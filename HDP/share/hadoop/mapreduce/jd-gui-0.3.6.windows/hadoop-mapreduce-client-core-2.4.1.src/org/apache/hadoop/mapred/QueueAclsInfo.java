/*  1:   */ package org.apache.hadoop.mapred;
/*  2:   */ 
/*  3:   */ class QueueAclsInfo
/*  4:   */   extends org.apache.hadoop.mapreduce.QueueAclsInfo
/*  5:   */ {
/*  6:   */   QueueAclsInfo() {}
/*  7:   */   
/*  8:   */   QueueAclsInfo(String queueName, String[] operations)
/*  9:   */   {
/* 10:43 */     super(queueName, operations);
/* 11:   */   }
/* 12:   */   
/* 13:   */   public static QueueAclsInfo downgrade(org.apache.hadoop.mapreduce.QueueAclsInfo acl)
/* 14:   */   {
/* 15:48 */     return new QueueAclsInfo(acl.getQueueName(), acl.getOperations());
/* 16:   */   }
/* 17:   */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.QueueAclsInfo
 * JD-Core Version:    0.7.0.1
 */