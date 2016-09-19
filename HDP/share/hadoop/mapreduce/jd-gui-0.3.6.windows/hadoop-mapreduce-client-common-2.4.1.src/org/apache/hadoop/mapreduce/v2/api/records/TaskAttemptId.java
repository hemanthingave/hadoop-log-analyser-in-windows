/*   1:    */ package org.apache.hadoop.mapreduce.v2.api.records;
/*   2:    */ 
/*   3:    */ import java.text.NumberFormat;
/*   4:    */ import org.apache.hadoop.yarn.api.records.ApplicationId;
/*   5:    */ 
/*   6:    */ public abstract class TaskAttemptId
/*   7:    */   implements Comparable<TaskAttemptId>
/*   8:    */ {
/*   9:    */   protected static final String TASKATTEMPT = "attempt";
/*  10:    */   
/*  11:    */   public abstract TaskId getTaskId();
/*  12:    */   
/*  13:    */   public abstract int getId();
/*  14:    */   
/*  15:    */   public abstract void setTaskId(TaskId paramTaskId);
/*  16:    */   
/*  17:    */   public abstract void setId(int paramInt);
/*  18:    */   
/*  19:    */   public int hashCode()
/*  20:    */   {
/*  21: 53 */     int prime = 31;
/*  22: 54 */     int result = 1;
/*  23: 55 */     result = 31 * result + getId();
/*  24: 56 */     result = 31 * result + (getTaskId() == null ? 0 : getTaskId().hashCode());
/*  25:    */     
/*  26: 58 */     return result;
/*  27:    */   }
/*  28:    */   
/*  29:    */   public boolean equals(Object obj)
/*  30:    */   {
/*  31: 63 */     if (this == obj) {
/*  32: 64 */       return true;
/*  33:    */     }
/*  34: 65 */     if (obj == null) {
/*  35: 66 */       return false;
/*  36:    */     }
/*  37: 67 */     if (getClass() != obj.getClass()) {
/*  38: 68 */       return false;
/*  39:    */     }
/*  40: 69 */     TaskAttemptId other = (TaskAttemptId)obj;
/*  41: 70 */     if (getId() != other.getId()) {
/*  42: 71 */       return false;
/*  43:    */     }
/*  44: 72 */     if (!getTaskId().equals(other.getTaskId())) {
/*  45: 73 */       return false;
/*  46:    */     }
/*  47: 74 */     return true;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public String toString()
/*  51:    */   {
/*  52: 79 */     StringBuilder builder = new StringBuilder("attempt");
/*  53: 80 */     TaskId taskId = getTaskId();
/*  54: 81 */     builder.append("_").append(taskId.getJobId().getAppId().getClusterTimestamp());
/*  55:    */     
/*  56: 83 */     builder.append("_").append(((NumberFormat)JobId.jobIdFormat.get()).format(getTaskId().getJobId().getAppId().getId()));
/*  57:    */     
/*  58:    */ 
/*  59: 86 */     builder.append("_");
/*  60: 87 */     builder.append(taskId.getTaskType() == TaskType.MAP ? "m" : "r");
/*  61: 88 */     builder.append("_").append(((NumberFormat)TaskId.taskIdFormat.get()).format(taskId.getId()));
/*  62:    */     
/*  63: 90 */     builder.append("_");
/*  64: 91 */     builder.append(getId());
/*  65: 92 */     return builder.toString();
/*  66:    */   }
/*  67:    */   
/*  68:    */   public int compareTo(TaskAttemptId other)
/*  69:    */   {
/*  70: 97 */     int taskIdComp = getTaskId().compareTo(other.getTaskId());
/*  71: 98 */     if (taskIdComp == 0) {
/*  72: 99 */       return getId() - other.getId();
/*  73:    */     }
/*  74:101 */     return taskIdComp;
/*  75:    */   }
/*  76:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-common-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.v2.api.records.TaskAttemptId
 * JD-Core Version:    0.7.0.1
 */