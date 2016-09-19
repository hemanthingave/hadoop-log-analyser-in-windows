/*  1:   */ package org.apache.hadoop.mapreduce.jobhistory;
/*  2:   */ 
/*  3:   */ import org.apache.avro.util.Utf8;
/*  4:   */ import org.apache.hadoop.classification.InterfaceAudience.Private;
/*  5:   */ import org.apache.hadoop.classification.InterfaceStability.Unstable;
/*  6:   */ import org.apache.hadoop.mapreduce.TaskID;
/*  7:   */ 
/*  8:   */ @InterfaceAudience.Private
/*  9:   */ @InterfaceStability.Unstable
/* 10:   */ public class TaskUpdatedEvent
/* 11:   */   implements HistoryEvent
/* 12:   */ {
/* 13:36 */   private TaskUpdated datum = new TaskUpdated();
/* 14:   */   
/* 15:   */   public TaskUpdatedEvent(TaskID id, long finishTime)
/* 16:   */   {
/* 17:44 */     this.datum.taskid = new Utf8(id.toString());
/* 18:45 */     this.datum.finishTime = finishTime;
/* 19:   */   }
/* 20:   */   
/* 21:   */   TaskUpdatedEvent() {}
/* 22:   */   
/* 23:   */   public Object getDatum()
/* 24:   */   {
/* 25:50 */     return this.datum;
/* 26:   */   }
/* 27:   */   
/* 28:   */   public void setDatum(Object datum)
/* 29:   */   {
/* 30:51 */     this.datum = ((TaskUpdated)datum);
/* 31:   */   }
/* 32:   */   
/* 33:   */   public TaskID getTaskId()
/* 34:   */   {
/* 35:54 */     return TaskID.forName(this.datum.taskid.toString());
/* 36:   */   }
/* 37:   */   
/* 38:   */   public long getFinishTime()
/* 39:   */   {
/* 40:56 */     return this.datum.finishTime;
/* 41:   */   }
/* 42:   */   
/* 43:   */   public EventType getEventType()
/* 44:   */   {
/* 45:59 */     return EventType.TASK_UPDATED;
/* 46:   */   }
/* 47:   */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.jobhistory.TaskUpdatedEvent
 * JD-Core Version:    0.7.0.1
 */