/*  1:   */ package org.apache.hadoop.mapreduce.jobhistory;
/*  2:   */ 
/*  3:   */ import org.apache.avro.util.Utf8;
/*  4:   */ import org.apache.hadoop.classification.InterfaceAudience.Private;
/*  5:   */ import org.apache.hadoop.classification.InterfaceStability.Unstable;
/*  6:   */ import org.apache.hadoop.mapreduce.TaskID;
/*  7:   */ import org.apache.hadoop.mapreduce.TaskType;
/*  8:   */ 
/*  9:   */ @InterfaceAudience.Private
/* 10:   */ @InterfaceStability.Unstable
/* 11:   */ public class TaskStartedEvent
/* 12:   */   implements HistoryEvent
/* 13:   */ {
/* 14:34 */   private TaskStarted datum = new TaskStarted();
/* 15:   */   
/* 16:   */   public TaskStartedEvent(TaskID id, long startTime, TaskType taskType, String splitLocations)
/* 17:   */   {
/* 18:45 */     this.datum.taskid = new Utf8(id.toString());
/* 19:46 */     this.datum.splitLocations = new Utf8(splitLocations);
/* 20:47 */     this.datum.startTime = startTime;
/* 21:48 */     this.datum.taskType = new Utf8(taskType.name());
/* 22:   */   }
/* 23:   */   
/* 24:   */   TaskStartedEvent() {}
/* 25:   */   
/* 26:   */   public Object getDatum()
/* 27:   */   {
/* 28:53 */     return this.datum;
/* 29:   */   }
/* 30:   */   
/* 31:   */   public void setDatum(Object datum)
/* 32:   */   {
/* 33:54 */     this.datum = ((TaskStarted)datum);
/* 34:   */   }
/* 35:   */   
/* 36:   */   public TaskID getTaskId()
/* 37:   */   {
/* 38:57 */     return TaskID.forName(this.datum.taskid.toString());
/* 39:   */   }
/* 40:   */   
/* 41:   */   public String getSplitLocations()
/* 42:   */   {
/* 43:59 */     return this.datum.splitLocations.toString();
/* 44:   */   }
/* 45:   */   
/* 46:   */   public long getStartTime()
/* 47:   */   {
/* 48:61 */     return this.datum.startTime;
/* 49:   */   }
/* 50:   */   
/* 51:   */   public TaskType getTaskType()
/* 52:   */   {
/* 53:64 */     return TaskType.valueOf(this.datum.taskType.toString());
/* 54:   */   }
/* 55:   */   
/* 56:   */   public EventType getEventType()
/* 57:   */   {
/* 58:68 */     return EventType.TASK_STARTED;
/* 59:   */   }
/* 60:   */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.jobhistory.TaskStartedEvent
 * JD-Core Version:    0.7.0.1
 */