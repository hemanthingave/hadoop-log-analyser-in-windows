/*   1:    */ package org.apache.hadoop.mapreduce.jobhistory;
/*   2:    */ 
/*   3:    */ import com.google.common.base.Joiner;
/*   4:    */ import java.util.Collections;
/*   5:    */ import org.apache.avro.util.Utf8;
/*   6:    */ import org.apache.hadoop.classification.InterfaceAudience.Private;
/*   7:    */ import org.apache.hadoop.classification.InterfaceStability.Unstable;
/*   8:    */ import org.apache.hadoop.mapreduce.JobID;
/*   9:    */ 
/*  10:    */ @InterfaceAudience.Private
/*  11:    */ @InterfaceStability.Unstable
/*  12:    */ public class JobUnsuccessfulCompletionEvent
/*  13:    */   implements HistoryEvent
/*  14:    */ {
/*  15:    */   private static final String NODIAGS = "";
/*  16: 38 */   private static final Iterable<String> NODIAGS_LIST = Collections.singletonList("");
/*  17: 41 */   private JobUnsuccessfulCompletion datum = new JobUnsuccessfulCompletion();
/*  18:    */   
/*  19:    */   public JobUnsuccessfulCompletionEvent(JobID id, long finishTime, int finishedMaps, int finishedReduces, String status)
/*  20:    */   {
/*  21: 55 */     this(id, finishTime, finishedMaps, finishedReduces, status, NODIAGS_LIST);
/*  22:    */   }
/*  23:    */   
/*  24:    */   public JobUnsuccessfulCompletionEvent(JobID id, long finishTime, int finishedMaps, int finishedReduces, String status, Iterable<String> diagnostics)
/*  25:    */   {
/*  26: 72 */     this.datum.setJobid(new Utf8(id.toString()));
/*  27: 73 */     this.datum.setFinishTime(Long.valueOf(finishTime));
/*  28: 74 */     this.datum.setFinishedMaps(Integer.valueOf(finishedMaps));
/*  29: 75 */     this.datum.setFinishedReduces(Integer.valueOf(finishedReduces));
/*  30: 76 */     this.datum.setJobStatus(new Utf8(status));
/*  31: 77 */     if (diagnostics == null) {
/*  32: 78 */       diagnostics = NODIAGS_LIST;
/*  33:    */     }
/*  34: 80 */     this.datum.setDiagnostics(new Utf8(Joiner.on('\n').skipNulls().join(diagnostics)));
/*  35:    */   }
/*  36:    */   
/*  37:    */   JobUnsuccessfulCompletionEvent() {}
/*  38:    */   
/*  39:    */   public Object getDatum()
/*  40:    */   {
/*  41: 86 */     return this.datum;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public void setDatum(Object datum)
/*  45:    */   {
/*  46: 88 */     this.datum = ((JobUnsuccessfulCompletion)datum);
/*  47:    */   }
/*  48:    */   
/*  49:    */   public JobID getJobId()
/*  50:    */   {
/*  51: 92 */     return JobID.forName(this.datum.jobid.toString());
/*  52:    */   }
/*  53:    */   
/*  54:    */   public long getFinishTime()
/*  55:    */   {
/*  56: 94 */     return this.datum.getFinishTime().longValue();
/*  57:    */   }
/*  58:    */   
/*  59:    */   public int getFinishedMaps()
/*  60:    */   {
/*  61: 96 */     return this.datum.getFinishedMaps().intValue();
/*  62:    */   }
/*  63:    */   
/*  64:    */   public int getFinishedReduces()
/*  65:    */   {
/*  66: 98 */     return this.datum.getFinishedReduces().intValue();
/*  67:    */   }
/*  68:    */   
/*  69:    */   public String getStatus()
/*  70:    */   {
/*  71:100 */     return this.datum.getJobStatus().toString();
/*  72:    */   }
/*  73:    */   
/*  74:    */   public EventType getEventType()
/*  75:    */   {
/*  76:103 */     if ("FAILED".equals(getStatus())) {
/*  77:104 */       return EventType.JOB_FAILED;
/*  78:    */     }
/*  79:105 */     if ("ERROR".equals(getStatus())) {
/*  80:106 */       return EventType.JOB_ERROR;
/*  81:    */     }
/*  82:108 */     return EventType.JOB_KILLED;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public String getDiagnostics()
/*  86:    */   {
/*  87:117 */     CharSequence diagnostics = this.datum.getDiagnostics();
/*  88:118 */     return diagnostics == null ? "" : diagnostics.toString();
/*  89:    */   }
/*  90:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.jobhistory.JobUnsuccessfulCompletionEvent
 * JD-Core Version:    0.7.0.1
 */