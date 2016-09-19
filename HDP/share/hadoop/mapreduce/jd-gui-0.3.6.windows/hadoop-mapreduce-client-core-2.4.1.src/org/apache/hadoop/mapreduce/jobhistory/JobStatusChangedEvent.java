/*  1:   */ package org.apache.hadoop.mapreduce.jobhistory;
/*  2:   */ 
/*  3:   */ import org.apache.avro.util.Utf8;
/*  4:   */ import org.apache.hadoop.classification.InterfaceAudience.Private;
/*  5:   */ import org.apache.hadoop.classification.InterfaceStability.Unstable;
/*  6:   */ import org.apache.hadoop.mapreduce.JobID;
/*  7:   */ 
/*  8:   */ @InterfaceAudience.Private
/*  9:   */ @InterfaceStability.Unstable
/* 10:   */ public class JobStatusChangedEvent
/* 11:   */   implements HistoryEvent
/* 12:   */ {
/* 13:36 */   private JobStatusChanged datum = new JobStatusChanged();
/* 14:   */   
/* 15:   */   public JobStatusChangedEvent(JobID id, String jobStatus)
/* 16:   */   {
/* 17:44 */     this.datum.jobid = new Utf8(id.toString());
/* 18:45 */     this.datum.jobStatus = new Utf8(jobStatus);
/* 19:   */   }
/* 20:   */   
/* 21:   */   JobStatusChangedEvent() {}
/* 22:   */   
/* 23:   */   public Object getDatum()
/* 24:   */   {
/* 25:50 */     return this.datum;
/* 26:   */   }
/* 27:   */   
/* 28:   */   public void setDatum(Object datum)
/* 29:   */   {
/* 30:52 */     this.datum = ((JobStatusChanged)datum);
/* 31:   */   }
/* 32:   */   
/* 33:   */   public JobID getJobId()
/* 34:   */   {
/* 35:56 */     return JobID.forName(this.datum.jobid.toString());
/* 36:   */   }
/* 37:   */   
/* 38:   */   public String getStatus()
/* 39:   */   {
/* 40:58 */     return this.datum.jobStatus.toString();
/* 41:   */   }
/* 42:   */   
/* 43:   */   public EventType getEventType()
/* 44:   */   {
/* 45:61 */     return EventType.JOB_STATUS_CHANGED;
/* 46:   */   }
/* 47:   */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.jobhistory.JobStatusChangedEvent
 * JD-Core Version:    0.7.0.1
 */