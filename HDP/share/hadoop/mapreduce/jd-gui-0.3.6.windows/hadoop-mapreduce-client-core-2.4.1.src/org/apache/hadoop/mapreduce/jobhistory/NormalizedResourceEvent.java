/*  1:   */ package org.apache.hadoop.mapreduce.jobhistory;
/*  2:   */ 
/*  3:   */ import org.apache.hadoop.classification.InterfaceAudience.Private;
/*  4:   */ import org.apache.hadoop.classification.InterfaceStability.Unstable;
/*  5:   */ import org.apache.hadoop.mapreduce.TaskType;
/*  6:   */ 
/*  7:   */ @InterfaceAudience.Private
/*  8:   */ @InterfaceStability.Unstable
/*  9:   */ public class NormalizedResourceEvent
/* 10:   */   implements HistoryEvent
/* 11:   */ {
/* 12:   */   private int memory;
/* 13:   */   private TaskType taskType;
/* 14:   */   
/* 15:   */   public NormalizedResourceEvent(TaskType taskType, int memory)
/* 16:   */   {
/* 17:40 */     this.memory = memory;
/* 18:41 */     this.taskType = taskType;
/* 19:   */   }
/* 20:   */   
/* 21:   */   public TaskType getTaskType()
/* 22:   */   {
/* 23:49 */     return this.taskType;
/* 24:   */   }
/* 25:   */   
/* 26:   */   public int getMemory()
/* 27:   */   {
/* 28:57 */     return this.memory;
/* 29:   */   }
/* 30:   */   
/* 31:   */   public EventType getEventType()
/* 32:   */   {
/* 33:62 */     return EventType.NORMALIZED_RESOURCE;
/* 34:   */   }
/* 35:   */   
/* 36:   */   public Object getDatum()
/* 37:   */   {
/* 38:67 */     throw new UnsupportedOperationException("Not a seriable object");
/* 39:   */   }
/* 40:   */   
/* 41:   */   public void setDatum(Object datum)
/* 42:   */   {
/* 43:72 */     throw new UnsupportedOperationException("Not a seriable object");
/* 44:   */   }
/* 45:   */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.jobhistory.NormalizedResourceEvent
 * JD-Core Version:    0.7.0.1
 */