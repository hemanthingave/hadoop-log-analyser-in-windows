/*   1:    */ package org.apache.hadoop.mapreduce.v2.api.records;
/*   2:    */ 
/*   3:    */ import java.text.NumberFormat;
/*   4:    */ import org.apache.hadoop.yarn.api.records.ApplicationId;
/*   5:    */ 
/*   6:    */ public abstract class TaskId
/*   7:    */   implements Comparable<TaskId>
/*   8:    */ {
/*   9:    */   protected static final String TASK = "task";
/*  10: 61 */   static final ThreadLocal<NumberFormat> taskIdFormat = new ThreadLocal()
/*  11:    */   {
/*  12:    */     public NumberFormat initialValue()
/*  13:    */     {
/*  14: 65 */       NumberFormat fmt = NumberFormat.getInstance();
/*  15: 66 */       fmt.setGroupingUsed(false);
/*  16: 67 */       fmt.setMinimumIntegerDigits(6);
/*  17: 68 */       return fmt;
/*  18:    */     }
/*  19:    */   };
/*  20:    */   
/*  21:    */   public abstract JobId getJobId();
/*  22:    */   
/*  23:    */   public abstract TaskType getTaskType();
/*  24:    */   
/*  25:    */   public abstract int getId();
/*  26:    */   
/*  27:    */   public abstract void setJobId(JobId paramJobId);
/*  28:    */   
/*  29:    */   public abstract void setTaskType(TaskType paramTaskType);
/*  30:    */   
/*  31:    */   public abstract void setId(int paramInt);
/*  32:    */   
/*  33:    */   public int hashCode()
/*  34:    */   {
/*  35: 74 */     int prime = 31;
/*  36: 75 */     int result = 1;
/*  37: 76 */     result = 31 * result + getId();
/*  38: 77 */     result = 31 * result + getJobId().hashCode();
/*  39: 78 */     result = 31 * result + getTaskType().hashCode();
/*  40: 79 */     return result;
/*  41:    */   }
/*  42:    */   
/*  43:    */   public boolean equals(Object obj)
/*  44:    */   {
/*  45: 84 */     if (this == obj) {
/*  46: 85 */       return true;
/*  47:    */     }
/*  48: 86 */     if (obj == null) {
/*  49: 87 */       return false;
/*  50:    */     }
/*  51: 88 */     if (getClass() != obj.getClass()) {
/*  52: 89 */       return false;
/*  53:    */     }
/*  54: 90 */     TaskId other = (TaskId)obj;
/*  55: 91 */     if (getId() != other.getId()) {
/*  56: 92 */       return false;
/*  57:    */     }
/*  58: 93 */     if (!getJobId().equals(other.getJobId())) {
/*  59: 94 */       return false;
/*  60:    */     }
/*  61: 95 */     if (getTaskType() != other.getTaskType()) {
/*  62: 96 */       return false;
/*  63:    */     }
/*  64: 97 */     return true;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public String toString()
/*  68:    */   {
/*  69:102 */     StringBuilder builder = new StringBuilder("task");
/*  70:103 */     JobId jobId = getJobId();
/*  71:104 */     builder.append("_").append(jobId.getAppId().getClusterTimestamp());
/*  72:105 */     builder.append("_").append(((NumberFormat)JobId.jobIdFormat.get()).format(jobId.getAppId().getId()));
/*  73:    */     
/*  74:107 */     builder.append("_");
/*  75:108 */     builder.append(getTaskType() == TaskType.MAP ? "m" : "r").append("_");
/*  76:109 */     builder.append(((NumberFormat)taskIdFormat.get()).format(getId()));
/*  77:110 */     return builder.toString();
/*  78:    */   }
/*  79:    */   
/*  80:    */   public int compareTo(TaskId other)
/*  81:    */   {
/*  82:115 */     int jobIdComp = getJobId().compareTo(other.getJobId());
/*  83:116 */     if (jobIdComp == 0)
/*  84:    */     {
/*  85:117 */       if (getTaskType() == other.getTaskType()) {
/*  86:118 */         return getId() - other.getId();
/*  87:    */       }
/*  88:120 */       return getTaskType().compareTo(other.getTaskType());
/*  89:    */     }
/*  90:123 */     return jobIdComp;
/*  91:    */   }
/*  92:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-common-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.v2.api.records.TaskId
 * JD-Core Version:    0.7.0.1
 */