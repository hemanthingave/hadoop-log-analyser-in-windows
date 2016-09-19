/*   1:    */ package org.apache.hadoop.mapreduce.jobhistory;
/*   2:    */ 
/*   3:    */ import org.apache.avro.util.Utf8;
/*   4:    */ import org.apache.hadoop.classification.InterfaceAudience.Private;
/*   5:    */ import org.apache.hadoop.classification.InterfaceStability.Unstable;
/*   6:    */ import org.apache.hadoop.mapreduce.Counters;
/*   7:    */ import org.apache.hadoop.mapreduce.TaskAttemptID;
/*   8:    */ import org.apache.hadoop.mapreduce.TaskID;
/*   9:    */ import org.apache.hadoop.mapreduce.TaskType;
/*  10:    */ 
/*  11:    */ @InterfaceAudience.Private
/*  12:    */ @InterfaceStability.Unstable
/*  13:    */ public class TaskFinishedEvent
/*  14:    */   implements HistoryEvent
/*  15:    */ {
/*  16: 37 */   private TaskFinished datum = null;
/*  17:    */   private TaskID taskid;
/*  18:    */   private TaskAttemptID successfulAttemptId;
/*  19:    */   private long finishTime;
/*  20:    */   private TaskType taskType;
/*  21:    */   private String status;
/*  22:    */   private Counters counters;
/*  23:    */   
/*  24:    */   public TaskFinishedEvent(TaskID id, TaskAttemptID attemptId, long finishTime, TaskType taskType, String status, Counters counters)
/*  25:    */   {
/*  26: 58 */     this.taskid = id;
/*  27: 59 */     this.successfulAttemptId = attemptId;
/*  28: 60 */     this.finishTime = finishTime;
/*  29: 61 */     this.taskType = taskType;
/*  30: 62 */     this.status = status;
/*  31: 63 */     this.counters = counters;
/*  32:    */   }
/*  33:    */   
/*  34:    */   TaskFinishedEvent() {}
/*  35:    */   
/*  36:    */   public Object getDatum()
/*  37:    */   {
/*  38: 69 */     if (this.datum == null)
/*  39:    */     {
/*  40: 70 */       this.datum = new TaskFinished();
/*  41: 71 */       this.datum.taskid = new Utf8(this.taskid.toString());
/*  42: 72 */       if (this.successfulAttemptId != null) {
/*  43: 74 */         this.datum.successfulAttemptId = new Utf8(this.successfulAttemptId.toString());
/*  44:    */       }
/*  45: 76 */       this.datum.finishTime = this.finishTime;
/*  46: 77 */       this.datum.counters = EventWriter.toAvro(this.counters);
/*  47: 78 */       this.datum.taskType = new Utf8(this.taskType.name());
/*  48: 79 */       this.datum.status = new Utf8(this.status);
/*  49:    */     }
/*  50: 81 */     return this.datum;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public void setDatum(Object oDatum)
/*  54:    */   {
/*  55: 85 */     this.datum = ((TaskFinished)oDatum);
/*  56: 86 */     this.taskid = TaskID.forName(this.datum.taskid.toString());
/*  57: 87 */     if (this.datum.successfulAttemptId != null) {
/*  58: 88 */       this.successfulAttemptId = TaskAttemptID.forName(this.datum.successfulAttemptId.toString());
/*  59:    */     }
/*  60: 91 */     this.finishTime = this.datum.finishTime;
/*  61: 92 */     this.taskType = TaskType.valueOf(this.datum.taskType.toString());
/*  62: 93 */     this.status = this.datum.status.toString();
/*  63: 94 */     this.counters = EventReader.fromAvro(this.datum.counters);
/*  64:    */   }
/*  65:    */   
/*  66:    */   public TaskID getTaskId()
/*  67:    */   {
/*  68: 98 */     return this.taskid;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public TaskAttemptID getSuccessfulTaskAttemptId()
/*  72:    */   {
/*  73:101 */     return this.successfulAttemptId;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public long getFinishTime()
/*  77:    */   {
/*  78:104 */     return this.finishTime;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public Counters getCounters()
/*  82:    */   {
/*  83:106 */     return this.counters;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public TaskType getTaskType()
/*  87:    */   {
/*  88:109 */     return this.taskType;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public String getTaskStatus()
/*  92:    */   {
/*  93:112 */     return this.status.toString();
/*  94:    */   }
/*  95:    */   
/*  96:    */   public EventType getEventType()
/*  97:    */   {
/*  98:115 */     return EventType.TASK_FINISHED;
/*  99:    */   }
/* 100:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.jobhistory.TaskFinishedEvent
 * JD-Core Version:    0.7.0.1
 */