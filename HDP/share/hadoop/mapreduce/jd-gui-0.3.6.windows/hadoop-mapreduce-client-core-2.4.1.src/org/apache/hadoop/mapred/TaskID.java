/*   1:    */ package org.apache.hadoop.mapred;
/*   2:    */ 
/*   3:    */ import java.io.DataInput;
/*   4:    */ import java.io.IOException;
/*   5:    */ import java.text.NumberFormat;
/*   6:    */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*   7:    */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*   8:    */ import org.apache.hadoop.mapreduce.TaskType;
/*   9:    */ 
/*  10:    */ @InterfaceAudience.Public
/*  11:    */ @InterfaceStability.Stable
/*  12:    */ public class TaskID
/*  13:    */   extends org.apache.hadoop.mapreduce.TaskID
/*  14:    */ {
/*  15:    */   @Deprecated
/*  16:    */   public TaskID(org.apache.hadoop.mapreduce.JobID jobId, boolean isMap, int id)
/*  17:    */   {
/*  18: 63 */     this(jobId, isMap ? TaskType.MAP : TaskType.REDUCE, id);
/*  19:    */   }
/*  20:    */   
/*  21:    */   @Deprecated
/*  22:    */   public TaskID(String jtIdentifier, int jobId, boolean isMap, int id)
/*  23:    */   {
/*  24: 77 */     this(jtIdentifier, jobId, isMap ? TaskType.MAP : TaskType.REDUCE, id);
/*  25:    */   }
/*  26:    */   
/*  27:    */   public TaskID(org.apache.hadoop.mapreduce.JobID jobId, TaskType type, int id)
/*  28:    */   {
/*  29: 87 */     super(jobId, type, id);
/*  30:    */   }
/*  31:    */   
/*  32:    */   public TaskID(String jtIdentifier, int jobId, TaskType type, int id)
/*  33:    */   {
/*  34: 98 */     this(new JobID(jtIdentifier, jobId), type, id);
/*  35:    */   }
/*  36:    */   
/*  37:    */   public TaskID()
/*  38:    */   {
/*  39:102 */     super(new JobID(), TaskType.REDUCE, 0);
/*  40:    */   }
/*  41:    */   
/*  42:    */   public static TaskID downgrade(org.apache.hadoop.mapreduce.TaskID old)
/*  43:    */   {
/*  44:111 */     if ((old instanceof TaskID)) {
/*  45:112 */       return (TaskID)old;
/*  46:    */     }
/*  47:114 */     return new TaskID(JobID.downgrade(old.getJobID()), old.getTaskType(), old.getId());
/*  48:    */   }
/*  49:    */   
/*  50:    */   @Deprecated
/*  51:    */   public static TaskID read(DataInput in)
/*  52:    */     throws IOException
/*  53:    */   {
/*  54:121 */     TaskID tipId = new TaskID();
/*  55:122 */     tipId.readFields(in);
/*  56:123 */     return tipId;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public JobID getJobID()
/*  60:    */   {
/*  61:127 */     return (JobID)super.getJobID();
/*  62:    */   }
/*  63:    */   
/*  64:    */   @Deprecated
/*  65:    */   public static String getTaskIDsPattern(String jtIdentifier, Integer jobId, Boolean isMap, Integer taskId)
/*  66:    */   {
/*  67:151 */     return getTaskIDsPattern(jtIdentifier, jobId, isMap.booleanValue() ? TaskType.MAP : TaskType.REDUCE, taskId);
/*  68:    */   }
/*  69:    */   
/*  70:    */   @Deprecated
/*  71:    */   public static String getTaskIDsPattern(String jtIdentifier, Integer jobId, TaskType type, Integer taskId)
/*  72:    */   {
/*  73:174 */     StringBuilder builder = new StringBuilder("task").append('_').append(getTaskIDsPatternWOPrefix(jtIdentifier, jobId, type, taskId));
/*  74:    */     
/*  75:176 */     return builder.toString();
/*  76:    */   }
/*  77:    */   
/*  78:    */   @Deprecated
/*  79:    */   static StringBuilder getTaskIDsPatternWOPrefix(String jtIdentifier, Integer jobId, TaskType type, Integer taskId)
/*  80:    */   {
/*  81:182 */     StringBuilder builder = new StringBuilder();
/*  82:183 */     builder.append(JobID.getJobIDsPatternWOPrefix(jtIdentifier, jobId)).append('_').append(type != null ? Character.valueOf(org.apache.hadoop.mapreduce.TaskID.getRepresentingCharacter(type)) : org.apache.hadoop.mapreduce.TaskID.getAllTaskTypes()).append('_').append(taskId != null ? idFormat.format(taskId) : "[0-9]*");
/*  83:    */     
/*  84:    */ 
/*  85:    */ 
/*  86:    */ 
/*  87:    */ 
/*  88:    */ 
/*  89:190 */     return builder;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public static TaskID forName(String str)
/*  93:    */     throws IllegalArgumentException
/*  94:    */   {
/*  95:195 */     return (TaskID)org.apache.hadoop.mapreduce.TaskID.forName(str);
/*  96:    */   }
/*  97:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.TaskID
 * JD-Core Version:    0.7.0.1
 */