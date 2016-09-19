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
/*  13:    */ public class TaskAttemptFinishedEvent
/*  14:    */   implements HistoryEvent
/*  15:    */ {
/*  16: 37 */   private TaskAttemptFinished datum = null;
/*  17:    */   private TaskAttemptID attemptId;
/*  18:    */   private TaskType taskType;
/*  19:    */   private String taskStatus;
/*  20:    */   private long finishTime;
/*  21:    */   private String rackName;
/*  22:    */   private String hostname;
/*  23:    */   private String state;
/*  24:    */   private Counters counters;
/*  25:    */   
/*  26:    */   public TaskAttemptFinishedEvent(TaskAttemptID id, TaskType taskType, String taskStatus, long finishTime, String rackName, String hostname, String state, Counters counters)
/*  27:    */   {
/*  28: 63 */     this.attemptId = id;
/*  29: 64 */     this.taskType = taskType;
/*  30: 65 */     this.taskStatus = taskStatus;
/*  31: 66 */     this.finishTime = finishTime;
/*  32: 67 */     this.rackName = rackName;
/*  33: 68 */     this.hostname = hostname;
/*  34: 69 */     this.state = state;
/*  35: 70 */     this.counters = counters;
/*  36:    */   }
/*  37:    */   
/*  38:    */   TaskAttemptFinishedEvent() {}
/*  39:    */   
/*  40:    */   public Object getDatum()
/*  41:    */   {
/*  42: 76 */     if (this.datum == null)
/*  43:    */     {
/*  44: 77 */       this.datum = new TaskAttemptFinished();
/*  45: 78 */       this.datum.taskid = new Utf8(this.attemptId.getTaskID().toString());
/*  46: 79 */       this.datum.attemptId = new Utf8(this.attemptId.toString());
/*  47: 80 */       this.datum.taskType = new Utf8(this.taskType.name());
/*  48: 81 */       this.datum.taskStatus = new Utf8(this.taskStatus);
/*  49: 82 */       this.datum.finishTime = this.finishTime;
/*  50: 83 */       if (this.rackName != null) {
/*  51: 84 */         this.datum.rackname = new Utf8(this.rackName);
/*  52:    */       }
/*  53: 86 */       this.datum.hostname = new Utf8(this.hostname);
/*  54: 87 */       this.datum.state = new Utf8(this.state);
/*  55: 88 */       this.datum.counters = EventWriter.toAvro(this.counters);
/*  56:    */     }
/*  57: 90 */     return this.datum;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public void setDatum(Object oDatum)
/*  61:    */   {
/*  62: 93 */     this.datum = ((TaskAttemptFinished)oDatum);
/*  63: 94 */     this.attemptId = TaskAttemptID.forName(this.datum.attemptId.toString());
/*  64: 95 */     this.taskType = TaskType.valueOf(this.datum.taskType.toString());
/*  65: 96 */     this.taskStatus = this.datum.taskStatus.toString();
/*  66: 97 */     this.finishTime = this.datum.finishTime;
/*  67: 98 */     this.rackName = this.datum.rackname.toString();
/*  68: 99 */     this.hostname = this.datum.hostname.toString();
/*  69:100 */     this.state = this.datum.state.toString();
/*  70:101 */     this.counters = EventReader.fromAvro(this.datum.counters);
/*  71:    */   }
/*  72:    */   
/*  73:    */   public TaskID getTaskId()
/*  74:    */   {
/*  75:105 */     return this.attemptId.getTaskID();
/*  76:    */   }
/*  77:    */   
/*  78:    */   public TaskAttemptID getAttemptId()
/*  79:    */   {
/*  80:108 */     return this.attemptId;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public TaskType getTaskType()
/*  84:    */   {
/*  85:112 */     return this.taskType;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public String getTaskStatus()
/*  89:    */   {
/*  90:115 */     return this.taskStatus.toString();
/*  91:    */   }
/*  92:    */   
/*  93:    */   public long getFinishTime()
/*  94:    */   {
/*  95:117 */     return this.finishTime;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public String getHostname()
/*  99:    */   {
/* 100:119 */     return this.hostname.toString();
/* 101:    */   }
/* 102:    */   
/* 103:    */   public String getRackName()
/* 104:    */   {
/* 105:123 */     return this.rackName == null ? null : this.rackName.toString();
/* 106:    */   }
/* 107:    */   
/* 108:    */   public String getState()
/* 109:    */   {
/* 110:127 */     return this.state.toString();
/* 111:    */   }
/* 112:    */   
/* 113:    */   Counters getCounters()
/* 114:    */   {
/* 115:129 */     return this.counters;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public EventType getEventType()
/* 119:    */   {
/* 120:134 */     return getTaskId().getTaskType() == TaskType.MAP ? EventType.MAP_ATTEMPT_FINISHED : EventType.REDUCE_ATTEMPT_FINISHED;
/* 121:    */   }
/* 122:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.jobhistory.TaskAttemptFinishedEvent
 * JD-Core Version:    0.7.0.1
 */