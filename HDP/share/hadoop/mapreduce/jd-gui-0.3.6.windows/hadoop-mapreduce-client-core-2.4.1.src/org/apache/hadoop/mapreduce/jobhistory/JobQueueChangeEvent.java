/*  1:   */ package org.apache.hadoop.mapreduce.jobhistory;
/*  2:   */ 
/*  3:   */ import org.apache.avro.util.Utf8;
/*  4:   */ import org.apache.hadoop.mapreduce.JobID;
/*  5:   */ 
/*  6:   */ public class JobQueueChangeEvent
/*  7:   */   implements HistoryEvent
/*  8:   */ {
/*  9:26 */   private JobQueueChange datum = new JobQueueChange();
/* 10:   */   
/* 11:   */   public JobQueueChangeEvent(JobID id, String queueName)
/* 12:   */   {
/* 13:29 */     this.datum.jobid = new Utf8(id.toString());
/* 14:30 */     this.datum.jobQueueName = new Utf8(queueName);
/* 15:   */   }
/* 16:   */   
/* 17:   */   JobQueueChangeEvent() {}
/* 18:   */   
/* 19:   */   public EventType getEventType()
/* 20:   */   {
/* 21:37 */     return EventType.JOB_QUEUE_CHANGED;
/* 22:   */   }
/* 23:   */   
/* 24:   */   public Object getDatum()
/* 25:   */   {
/* 26:42 */     return this.datum;
/* 27:   */   }
/* 28:   */   
/* 29:   */   public void setDatum(Object datum)
/* 30:   */   {
/* 31:47 */     this.datum = ((JobQueueChange)datum);
/* 32:   */   }
/* 33:   */   
/* 34:   */   public JobID getJobId()
/* 35:   */   {
/* 36:52 */     return JobID.forName(this.datum.jobid.toString());
/* 37:   */   }
/* 38:   */   
/* 39:   */   public String getJobQueueName()
/* 40:   */   {
/* 41:57 */     if (this.datum.jobQueueName != null) {
/* 42:58 */       return this.datum.jobQueueName.toString();
/* 43:   */     }
/* 44:60 */     return null;
/* 45:   */   }
/* 46:   */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.jobhistory.JobQueueChangeEvent
 * JD-Core Version:    0.7.0.1
 */