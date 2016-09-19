/*   1:    */ package org.apache.hadoop.mapred;
/*   2:    */ 
/*   3:    */ import org.apache.hadoop.classification.InterfaceAudience.Private;
/*   4:    */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*   5:    */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*   6:    */ 
/*   7:    */ @InterfaceAudience.Public
/*   8:    */ @InterfaceStability.Stable
/*   9:    */ public class TaskCompletionEvent
/*  10:    */   extends org.apache.hadoop.mapreduce.TaskCompletionEvent
/*  11:    */ {
/*  12:    */   public TaskCompletionEvent() {}
/*  13:    */   
/*  14:    */   @InterfaceAudience.Public
/*  15:    */   @InterfaceStability.Stable
/*  16:    */   public static enum Status
/*  17:    */   {
/*  18: 35 */     FAILED,  KILLED,  SUCCEEDED,  OBSOLETE,  TIPFAILED;
/*  19:    */     
/*  20:    */     private Status() {}
/*  21:    */   }
/*  22:    */   
/*  23: 37 */   public static final TaskCompletionEvent[] EMPTY_ARRAY = new TaskCompletionEvent[0];
/*  24:    */   
/*  25:    */   public TaskCompletionEvent(int eventId, TaskAttemptID taskId, int idWithinJob, boolean isMap, Status status, String taskTrackerHttp)
/*  26:    */   {
/*  27: 62 */     super(eventId, taskId, idWithinJob, isMap, org.apache.hadoop.mapreduce.TaskCompletionEvent.Status.valueOf(status.name()), taskTrackerHttp);
/*  28:    */   }
/*  29:    */   
/*  30:    */   @InterfaceAudience.Private
/*  31:    */   public static TaskCompletionEvent downgrade(org.apache.hadoop.mapreduce.TaskCompletionEvent event)
/*  32:    */   {
/*  33: 69 */     return new TaskCompletionEvent(event.getEventId(), TaskAttemptID.downgrade(event.getTaskAttemptId()), event.idWithinJob(), event.isMapTask(), Status.valueOf(event.getStatus().name()), event.getTaskTrackerHttp());
/*  34:    */   }
/*  35:    */   
/*  36:    */   @Deprecated
/*  37:    */   public String getTaskId()
/*  38:    */   {
/*  39: 81 */     return getTaskAttemptId().toString();
/*  40:    */   }
/*  41:    */   
/*  42:    */   public TaskAttemptID getTaskAttemptId()
/*  43:    */   {
/*  44: 89 */     return TaskAttemptID.downgrade(super.getTaskAttemptId());
/*  45:    */   }
/*  46:    */   
/*  47:    */   public Status getTaskStatus()
/*  48:    */   {
/*  49: 97 */     return Status.valueOf(super.getStatus().name());
/*  50:    */   }
/*  51:    */   
/*  52:    */   @Deprecated
/*  53:    */   public void setTaskId(String taskId)
/*  54:    */   {
/*  55:107 */     setTaskAttemptId(TaskAttemptID.forName(taskId));
/*  56:    */   }
/*  57:    */   
/*  58:    */   @Deprecated
/*  59:    */   public void setTaskID(TaskAttemptID taskId)
/*  60:    */   {
/*  61:117 */     setTaskAttemptId(taskId);
/*  62:    */   }
/*  63:    */   
/*  64:    */   protected void setTaskAttemptId(TaskAttemptID taskId)
/*  65:    */   {
/*  66:125 */     super.setTaskAttemptId(taskId);
/*  67:    */   }
/*  68:    */   
/*  69:    */   @InterfaceAudience.Private
/*  70:    */   public void setTaskStatus(Status status)
/*  71:    */   {
/*  72:134 */     super.setTaskStatus(org.apache.hadoop.mapreduce.TaskCompletionEvent.Status.valueOf(status.name()));
/*  73:    */   }
/*  74:    */   
/*  75:    */   @InterfaceAudience.Private
/*  76:    */   public void setTaskRunTime(int taskCompletionTime)
/*  77:    */   {
/*  78:144 */     super.setTaskRunTime(taskCompletionTime);
/*  79:    */   }
/*  80:    */   
/*  81:    */   @InterfaceAudience.Private
/*  82:    */   public void setEventId(int eventId)
/*  83:    */   {
/*  84:153 */     super.setEventId(eventId);
/*  85:    */   }
/*  86:    */   
/*  87:    */   @InterfaceAudience.Private
/*  88:    */   public void setTaskTrackerHttp(String taskTrackerHttp)
/*  89:    */   {
/*  90:162 */     super.setTaskTrackerHttp(taskTrackerHttp);
/*  91:    */   }
/*  92:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.TaskCompletionEvent
 * JD-Core Version:    0.7.0.1
 */