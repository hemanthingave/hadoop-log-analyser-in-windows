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
/*  13:    */ public class TaskFailedEvent
/*  14:    */   implements HistoryEvent
/*  15:    */ {
/*  16: 37 */   private TaskFailed datum = null;
/*  17:    */   private TaskAttemptID failedDueToAttempt;
/*  18:    */   private TaskID id;
/*  19:    */   private TaskType taskType;
/*  20:    */   private long finishTime;
/*  21:    */   private String status;
/*  22:    */   private String error;
/*  23:    */   private Counters counters;
/*  24: 47 */   private static final Counters EMPTY_COUNTERS = new Counters();
/*  25:    */   
/*  26:    */   public TaskFailedEvent(TaskID id, long finishTime, TaskType taskType, String error, String status, TaskAttemptID failedDueToAttempt, Counters counters)
/*  27:    */   {
/*  28: 62 */     this.id = id;
/*  29: 63 */     this.finishTime = finishTime;
/*  30: 64 */     this.taskType = taskType;
/*  31: 65 */     this.error = error;
/*  32: 66 */     this.status = status;
/*  33: 67 */     this.failedDueToAttempt = failedDueToAttempt;
/*  34: 68 */     this.counters = counters;
/*  35:    */   }
/*  36:    */   
/*  37:    */   public TaskFailedEvent(TaskID id, long finishTime, TaskType taskType, String error, String status, TaskAttemptID failedDueToAttempt)
/*  38:    */   {
/*  39: 74 */     this(id, finishTime, taskType, error, status, failedDueToAttempt, EMPTY_COUNTERS);
/*  40:    */   }
/*  41:    */   
/*  42:    */   TaskFailedEvent() {}
/*  43:    */   
/*  44:    */   public Object getDatum()
/*  45:    */   {
/*  46: 81 */     if (this.datum == null)
/*  47:    */     {
/*  48: 82 */       this.datum = new TaskFailed();
/*  49: 83 */       this.datum.taskid = new Utf8(this.id.toString());
/*  50: 84 */       this.datum.error = new Utf8(this.error);
/*  51: 85 */       this.datum.finishTime = this.finishTime;
/*  52: 86 */       this.datum.taskType = new Utf8(this.taskType.name());
/*  53: 87 */       this.datum.failedDueToAttempt = (this.failedDueToAttempt == null ? null : new Utf8(this.failedDueToAttempt.toString()));
/*  54:    */       
/*  55:    */ 
/*  56:    */ 
/*  57: 91 */       this.datum.status = new Utf8(this.status);
/*  58: 92 */       this.datum.counters = EventWriter.toAvro(this.counters);
/*  59:    */     }
/*  60: 94 */     return this.datum;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public void setDatum(Object odatum)
/*  64:    */   {
/*  65: 98 */     this.datum = ((TaskFailed)odatum);
/*  66: 99 */     this.id = TaskID.forName(this.datum.taskid.toString());
/*  67:    */     
/*  68:101 */     this.taskType = TaskType.valueOf(this.datum.taskType.toString());
/*  69:    */     
/*  70:103 */     this.finishTime = this.datum.finishTime;
/*  71:104 */     this.error = this.datum.error.toString();
/*  72:105 */     this.failedDueToAttempt = (this.datum.failedDueToAttempt == null ? null : TaskAttemptID.forName(this.datum.failedDueToAttempt.toString()));
/*  73:    */     
/*  74:    */ 
/*  75:    */ 
/*  76:    */ 
/*  77:110 */     this.status = this.datum.status.toString();
/*  78:111 */     this.counters = EventReader.fromAvro(this.datum.counters);
/*  79:    */   }
/*  80:    */   
/*  81:    */   public TaskID getTaskId()
/*  82:    */   {
/*  83:116 */     return this.id;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public String getError()
/*  87:    */   {
/*  88:118 */     return this.error;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public long getFinishTime()
/*  92:    */   {
/*  93:121 */     return this.finishTime;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public TaskType getTaskType()
/*  97:    */   {
/*  98:125 */     return this.taskType;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public TaskAttemptID getFailedAttemptID()
/* 102:    */   {
/* 103:129 */     return this.failedDueToAttempt;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public String getTaskStatus()
/* 107:    */   {
/* 108:132 */     return this.status;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public Counters getCounters()
/* 112:    */   {
/* 113:134 */     return this.counters;
/* 114:    */   }
/* 115:    */   
/* 116:    */   public EventType getEventType()
/* 117:    */   {
/* 118:137 */     return EventType.TASK_FAILED;
/* 119:    */   }
/* 120:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.jobhistory.TaskFailedEvent
 * JD-Core Version:    0.7.0.1
 */