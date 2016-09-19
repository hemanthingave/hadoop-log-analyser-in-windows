/*   1:    */ package org.apache.hadoop.mapred;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.Collection;
/*   5:    */ import java.util.List;
/*   6:    */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*   7:    */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*   8:    */ 
/*   9:    */ @InterfaceAudience.Public
/*  10:    */ @InterfaceStability.Stable
/*  11:    */ public class TaskReport
/*  12:    */   extends org.apache.hadoop.mapreduce.TaskReport
/*  13:    */ {
/*  14:    */   public TaskReport() {}
/*  15:    */   
/*  16:    */   @Deprecated
/*  17:    */   TaskReport(TaskID taskid, float progress, String state, String[] diagnostics, long startTime, long finishTime, Counters counters)
/*  18:    */   {
/*  19: 53 */     this(taskid, progress, state, diagnostics, null, startTime, finishTime, counters);
/*  20:    */   }
/*  21:    */   
/*  22:    */   TaskReport(TaskID taskid, float progress, String state, String[] diagnostics, TIPStatus currentStatus, long startTime, long finishTime, Counters counters)
/*  23:    */   {
/*  24: 72 */     super(taskid, progress, state, diagnostics, currentStatus, startTime, finishTime, new org.apache.hadoop.mapreduce.Counters(counters));
/*  25:    */   }
/*  26:    */   
/*  27:    */   static TaskReport downgrade(org.apache.hadoop.mapreduce.TaskReport report)
/*  28:    */   {
/*  29: 78 */     return new TaskReport(TaskID.downgrade(report.getTaskID()), report.getProgress(), report.getState(), report.getDiagnostics(), report.getCurrentStatus(), report.getStartTime(), report.getFinishTime(), Counters.downgrade(report.getTaskCounters()));
/*  30:    */   }
/*  31:    */   
/*  32:    */   static TaskReport[] downgradeArray(org.apache.hadoop.mapreduce.TaskReport[] reports)
/*  33:    */   {
/*  34: 86 */     List<TaskReport> ret = new ArrayList();
/*  35: 87 */     for (org.apache.hadoop.mapreduce.TaskReport report : reports) {
/*  36: 88 */       ret.add(downgrade(report));
/*  37:    */     }
/*  38: 90 */     return (TaskReport[])ret.toArray(new TaskReport[0]);
/*  39:    */   }
/*  40:    */   
/*  41:    */   public String getTaskId()
/*  42:    */   {
/*  43: 95 */     return TaskID.downgrade(super.getTaskID()).toString();
/*  44:    */   }
/*  45:    */   
/*  46:    */   public TaskID getTaskID()
/*  47:    */   {
/*  48:100 */     return TaskID.downgrade(super.getTaskID());
/*  49:    */   }
/*  50:    */   
/*  51:    */   public Counters getCounters()
/*  52:    */   {
/*  53:104 */     return Counters.downgrade(super.getTaskCounters());
/*  54:    */   }
/*  55:    */   
/*  56:    */   public void setSuccessfulAttempt(TaskAttemptID t)
/*  57:    */   {
/*  58:111 */     super.setSuccessfulAttemptId(t);
/*  59:    */   }
/*  60:    */   
/*  61:    */   public TaskAttemptID getSuccessfulTaskAttempt()
/*  62:    */   {
/*  63:117 */     return TaskAttemptID.downgrade(super.getSuccessfulTaskAttemptId());
/*  64:    */   }
/*  65:    */   
/*  66:    */   public void setRunningTaskAttempts(Collection<TaskAttemptID> runningAttempts)
/*  67:    */   {
/*  68:124 */     Collection<org.apache.hadoop.mapreduce.TaskAttemptID> attempts = new ArrayList();
/*  69:126 */     for (TaskAttemptID id : runningAttempts) {
/*  70:127 */       attempts.add(id);
/*  71:    */     }
/*  72:129 */     super.setRunningTaskAttemptIds(attempts);
/*  73:    */   }
/*  74:    */   
/*  75:    */   public Collection<TaskAttemptID> getRunningTaskAttempts()
/*  76:    */   {
/*  77:135 */     Collection<TaskAttemptID> attempts = new ArrayList();
/*  78:137 */     for (org.apache.hadoop.mapreduce.TaskAttemptID id : super.getRunningTaskAttemptIds()) {
/*  79:138 */       attempts.add(TaskAttemptID.downgrade(id));
/*  80:    */     }
/*  81:140 */     return attempts;
/*  82:    */   }
/*  83:    */   
/*  84:    */   protected void setFinishTime(long finishTime)
/*  85:    */   {
/*  86:148 */     super.setFinishTime(finishTime);
/*  87:    */   }
/*  88:    */   
/*  89:    */   protected void setStartTime(long startTime)
/*  90:    */   {
/*  91:155 */     super.setStartTime(startTime);
/*  92:    */   }
/*  93:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.TaskReport
 * JD-Core Version:    0.7.0.1
 */