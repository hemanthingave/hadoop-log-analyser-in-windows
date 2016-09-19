/*  1:   */ package org.apache.hadoop.mapreduce.jobhistory;
/*  2:   */ 
/*  3:   */ import org.apache.avro.util.Utf8;
/*  4:   */ import org.apache.hadoop.classification.InterfaceAudience.Private;
/*  5:   */ import org.apache.hadoop.classification.InterfaceStability.Unstable;
/*  6:   */ import org.apache.hadoop.mapreduce.JobID;
/*  7:   */ 
/*  8:   */ @InterfaceAudience.Private
/*  9:   */ @InterfaceStability.Unstable
/* 10:   */ public class JobInfoChangeEvent
/* 11:   */   implements HistoryEvent
/* 12:   */ {
/* 13:36 */   private JobInfoChange datum = new JobInfoChange();
/* 14:   */   
/* 15:   */   public JobInfoChangeEvent(JobID id, long submitTime, long launchTime)
/* 16:   */   {
/* 17:45 */     this.datum.jobid = new Utf8(id.toString());
/* 18:46 */     this.datum.submitTime = submitTime;
/* 19:47 */     this.datum.launchTime = launchTime;
/* 20:   */   }
/* 21:   */   
/* 22:   */   JobInfoChangeEvent() {}
/* 23:   */   
/* 24:   */   public Object getDatum()
/* 25:   */   {
/* 26:52 */     return this.datum;
/* 27:   */   }
/* 28:   */   
/* 29:   */   public void setDatum(Object datum)
/* 30:   */   {
/* 31:54 */     this.datum = ((JobInfoChange)datum);
/* 32:   */   }
/* 33:   */   
/* 34:   */   public JobID getJobId()
/* 35:   */   {
/* 36:58 */     return JobID.forName(this.datum.jobid.toString());
/* 37:   */   }
/* 38:   */   
/* 39:   */   public long getSubmitTime()
/* 40:   */   {
/* 41:60 */     return this.datum.submitTime;
/* 42:   */   }
/* 43:   */   
/* 44:   */   public long getLaunchTime()
/* 45:   */   {
/* 46:62 */     return this.datum.launchTime;
/* 47:   */   }
/* 48:   */   
/* 49:   */   public EventType getEventType()
/* 50:   */   {
/* 51:65 */     return EventType.JOB_INFO_CHANGED;
/* 52:   */   }
/* 53:   */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.jobhistory.JobInfoChangeEvent
 * JD-Core Version:    0.7.0.1
 */