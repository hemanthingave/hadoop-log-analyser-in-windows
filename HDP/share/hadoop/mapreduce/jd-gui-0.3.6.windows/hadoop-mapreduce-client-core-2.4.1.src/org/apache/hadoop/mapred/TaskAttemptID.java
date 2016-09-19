/*   1:    */ package org.apache.hadoop.mapred;
/*   2:    */ 
/*   3:    */ import java.io.DataInput;
/*   4:    */ import java.io.IOException;
/*   5:    */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*   6:    */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*   7:    */ import org.apache.hadoop.mapreduce.TaskType;
/*   8:    */ 
/*   9:    */ @InterfaceAudience.Public
/*  10:    */ @InterfaceStability.Stable
/*  11:    */ public class TaskAttemptID
/*  12:    */   extends org.apache.hadoop.mapreduce.TaskAttemptID
/*  13:    */ {
/*  14:    */   public TaskAttemptID(TaskID taskId, int id)
/*  15:    */   {
/*  16: 58 */     super(taskId, id);
/*  17:    */   }
/*  18:    */   
/*  19:    */   @Deprecated
/*  20:    */   public TaskAttemptID(String jtIdentifier, int jobId, boolean isMap, int taskId, int id)
/*  21:    */   {
/*  22: 73 */     this(jtIdentifier, jobId, isMap ? TaskType.MAP : TaskType.REDUCE, taskId, id);
/*  23:    */   }
/*  24:    */   
/*  25:    */   public TaskAttemptID(String jtIdentifier, int jobId, TaskType type, int taskId, int id)
/*  26:    */   {
/*  27: 87 */     this(new TaskID(jtIdentifier, jobId, type, taskId), id);
/*  28:    */   }
/*  29:    */   
/*  30:    */   public TaskAttemptID()
/*  31:    */   {
/*  32: 91 */     super(new TaskID(), 0);
/*  33:    */   }
/*  34:    */   
/*  35:    */   public static TaskAttemptID downgrade(org.apache.hadoop.mapreduce.TaskAttemptID old)
/*  36:    */   {
/*  37:101 */     if ((old instanceof TaskAttemptID)) {
/*  38:102 */       return (TaskAttemptID)old;
/*  39:    */     }
/*  40:104 */     return new TaskAttemptID(TaskID.downgrade(old.getTaskID()), old.getId());
/*  41:    */   }
/*  42:    */   
/*  43:    */   public TaskID getTaskID()
/*  44:    */   {
/*  45:109 */     return (TaskID)super.getTaskID();
/*  46:    */   }
/*  47:    */   
/*  48:    */   public JobID getJobID()
/*  49:    */   {
/*  50:113 */     return (JobID)super.getJobID();
/*  51:    */   }
/*  52:    */   
/*  53:    */   @Deprecated
/*  54:    */   public static TaskAttemptID read(DataInput in)
/*  55:    */     throws IOException
/*  56:    */   {
/*  57:118 */     TaskAttemptID taskId = new TaskAttemptID();
/*  58:119 */     taskId.readFields(in);
/*  59:120 */     return taskId;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public static TaskAttemptID forName(String str)
/*  63:    */     throws IllegalArgumentException
/*  64:    */   {
/*  65:129 */     return (TaskAttemptID)org.apache.hadoop.mapreduce.TaskAttemptID.forName(str);
/*  66:    */   }
/*  67:    */   
/*  68:    */   @Deprecated
/*  69:    */   public static String getTaskAttemptIDsPattern(String jtIdentifier, Integer jobId, Boolean isMap, Integer taskId, Integer attemptId)
/*  70:    */   {
/*  71:154 */     return getTaskAttemptIDsPattern(jtIdentifier, jobId, isMap.booleanValue() ? TaskType.MAP : TaskType.REDUCE, taskId, attemptId);
/*  72:    */   }
/*  73:    */   
/*  74:    */   @Deprecated
/*  75:    */   public static String getTaskAttemptIDsPattern(String jtIdentifier, Integer jobId, TaskType type, Integer taskId, Integer attemptId)
/*  76:    */   {
/*  77:179 */     StringBuilder builder = new StringBuilder("attempt").append('_');
/*  78:180 */     builder.append(getTaskAttemptIDsPatternWOPrefix(jtIdentifier, jobId, type, taskId, attemptId));
/*  79:    */     
/*  80:182 */     return builder.toString();
/*  81:    */   }
/*  82:    */   
/*  83:    */   @Deprecated
/*  84:    */   static StringBuilder getTaskAttemptIDsPatternWOPrefix(String jtIdentifier, Integer jobId, TaskType type, Integer taskId, Integer attemptId)
/*  85:    */   {
/*  86:188 */     StringBuilder builder = new StringBuilder();
/*  87:189 */     builder.append(TaskID.getTaskIDsPatternWOPrefix(jtIdentifier, jobId, type, taskId)).append('_').append(attemptId != null ? attemptId : "[0-9]*");
/*  88:    */     
/*  89:    */ 
/*  90:    */ 
/*  91:193 */     return builder;
/*  92:    */   }
/*  93:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.TaskAttemptID
 * JD-Core Version:    0.7.0.1
 */