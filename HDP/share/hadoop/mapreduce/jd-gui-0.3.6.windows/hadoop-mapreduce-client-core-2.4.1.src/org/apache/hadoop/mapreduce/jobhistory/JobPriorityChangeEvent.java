/*  1:   */ package org.apache.hadoop.mapreduce.jobhistory;
/*  2:   */ 
/*  3:   */ import org.apache.avro.util.Utf8;
/*  4:   */ import org.apache.hadoop.classification.InterfaceAudience.Private;
/*  5:   */ import org.apache.hadoop.classification.InterfaceStability.Unstable;
/*  6:   */ import org.apache.hadoop.mapred.JobPriority;
/*  7:   */ import org.apache.hadoop.mapreduce.JobID;
/*  8:   */ 
/*  9:   */ @InterfaceAudience.Private
/* 10:   */ @InterfaceStability.Unstable
/* 11:   */ public class JobPriorityChangeEvent
/* 12:   */   implements HistoryEvent
/* 13:   */ {
/* 14:37 */   private JobPriorityChange datum = new JobPriorityChange();
/* 15:   */   
/* 16:   */   public JobPriorityChangeEvent(JobID id, JobPriority priority)
/* 17:   */   {
/* 18:44 */     this.datum.jobid = new Utf8(id.toString());
/* 19:45 */     this.datum.priority = new Utf8(priority.name());
/* 20:   */   }
/* 21:   */   
/* 22:   */   JobPriorityChangeEvent() {}
/* 23:   */   
/* 24:   */   public Object getDatum()
/* 25:   */   {
/* 26:50 */     return this.datum;
/* 27:   */   }
/* 28:   */   
/* 29:   */   public void setDatum(Object datum)
/* 30:   */   {
/* 31:52 */     this.datum = ((JobPriorityChange)datum);
/* 32:   */   }
/* 33:   */   
/* 34:   */   public JobID getJobId()
/* 35:   */   {
/* 36:56 */     return JobID.forName(this.datum.jobid.toString());
/* 37:   */   }
/* 38:   */   
/* 39:   */   public JobPriority getPriority()
/* 40:   */   {
/* 41:59 */     return JobPriority.valueOf(this.datum.priority.toString());
/* 42:   */   }
/* 43:   */   
/* 44:   */   public EventType getEventType()
/* 45:   */   {
/* 46:63 */     return EventType.JOB_PRIORITY_CHANGED;
/* 47:   */   }
/* 48:   */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.jobhistory.JobPriorityChangeEvent
 * JD-Core Version:    0.7.0.1
 */