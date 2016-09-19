/*  1:   */ package org.apache.hadoop.mapreduce.jobhistory;
/*  2:   */ 
/*  3:   */ import org.apache.avro.util.Utf8;
/*  4:   */ import org.apache.hadoop.classification.InterfaceAudience.Private;
/*  5:   */ import org.apache.hadoop.classification.InterfaceStability.Unstable;
/*  6:   */ import org.apache.hadoop.mapreduce.JobID;
/*  7:   */ 
/*  8:   */ @InterfaceAudience.Private
/*  9:   */ @InterfaceStability.Unstable
/* 10:   */ public class JobInitedEvent
/* 11:   */   implements HistoryEvent
/* 12:   */ {
/* 13:34 */   private JobInited datum = new JobInited();
/* 14:   */   
/* 15:   */   public JobInitedEvent(JobID id, long launchTime, int totalMaps, int totalReduces, String jobStatus, boolean uberized)
/* 16:   */   {
/* 17:47 */     this.datum.jobid = new Utf8(id.toString());
/* 18:48 */     this.datum.launchTime = launchTime;
/* 19:49 */     this.datum.totalMaps = totalMaps;
/* 20:50 */     this.datum.totalReduces = totalReduces;
/* 21:51 */     this.datum.jobStatus = new Utf8(jobStatus);
/* 22:52 */     this.datum.uberized = uberized;
/* 23:   */   }
/* 24:   */   
/* 25:   */   JobInitedEvent() {}
/* 26:   */   
/* 27:   */   public Object getDatum()
/* 28:   */   {
/* 29:57 */     return this.datum;
/* 30:   */   }
/* 31:   */   
/* 32:   */   public void setDatum(Object datum)
/* 33:   */   {
/* 34:58 */     this.datum = ((JobInited)datum);
/* 35:   */   }
/* 36:   */   
/* 37:   */   public JobID getJobId()
/* 38:   */   {
/* 39:61 */     return JobID.forName(this.datum.jobid.toString());
/* 40:   */   }
/* 41:   */   
/* 42:   */   public long getLaunchTime()
/* 43:   */   {
/* 44:63 */     return this.datum.launchTime;
/* 45:   */   }
/* 46:   */   
/* 47:   */   public int getTotalMaps()
/* 48:   */   {
/* 49:65 */     return this.datum.totalMaps;
/* 50:   */   }
/* 51:   */   
/* 52:   */   public int getTotalReduces()
/* 53:   */   {
/* 54:67 */     return this.datum.totalReduces;
/* 55:   */   }
/* 56:   */   
/* 57:   */   public String getStatus()
/* 58:   */   {
/* 59:69 */     return this.datum.jobStatus.toString();
/* 60:   */   }
/* 61:   */   
/* 62:   */   public EventType getEventType()
/* 63:   */   {
/* 64:72 */     return EventType.JOB_INITED;
/* 65:   */   }
/* 66:   */   
/* 67:   */   public boolean getUberized()
/* 68:   */   {
/* 69:75 */     return this.datum.uberized;
/* 70:   */   }
/* 71:   */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.jobhistory.JobInitedEvent
 * JD-Core Version:    0.7.0.1
 */