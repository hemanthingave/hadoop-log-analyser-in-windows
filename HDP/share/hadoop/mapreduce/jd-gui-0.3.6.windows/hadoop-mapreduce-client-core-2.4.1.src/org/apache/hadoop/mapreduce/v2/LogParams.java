/*  1:   */ package org.apache.hadoop.mapreduce.v2;
/*  2:   */ 
/*  3:   */ public class LogParams
/*  4:   */ {
/*  5:   */   private String containerId;
/*  6:   */   private String applicationId;
/*  7:   */   private String nodeId;
/*  8:   */   private String owner;
/*  9:   */   
/* 10:   */   public LogParams(String containerIdStr, String applicationIdStr, String nodeIdStr, String owner)
/* 11:   */   {
/* 12:30 */     this.containerId = containerIdStr;
/* 13:31 */     this.applicationId = applicationIdStr;
/* 14:32 */     this.nodeId = nodeIdStr;
/* 15:33 */     this.owner = owner;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public String getContainerId()
/* 19:   */   {
/* 20:37 */     return this.containerId;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public void setContainerId(String containerId)
/* 24:   */   {
/* 25:41 */     this.containerId = containerId;
/* 26:   */   }
/* 27:   */   
/* 28:   */   public String getApplicationId()
/* 29:   */   {
/* 30:45 */     return this.applicationId;
/* 31:   */   }
/* 32:   */   
/* 33:   */   public void setApplicationId(String applicationId)
/* 34:   */   {
/* 35:49 */     this.applicationId = applicationId;
/* 36:   */   }
/* 37:   */   
/* 38:   */   public String getNodeId()
/* 39:   */   {
/* 40:53 */     return this.nodeId;
/* 41:   */   }
/* 42:   */   
/* 43:   */   public void setNodeId(String nodeId)
/* 44:   */   {
/* 45:57 */     this.nodeId = nodeId;
/* 46:   */   }
/* 47:   */   
/* 48:   */   public String getOwner()
/* 49:   */   {
/* 50:61 */     return this.owner;
/* 51:   */   }
/* 52:   */   
/* 53:   */   public String setOwner(String owner)
/* 54:   */   {
/* 55:65 */     return this.owner;
/* 56:   */   }
/* 57:   */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.v2.LogParams
 * JD-Core Version:    0.7.0.1
 */