/*   1:    */ package org.apache.hadoop.mapreduce.jobhistory;
/*   2:    */ 
/*   3:    */ import org.apache.avro.util.Utf8;
/*   4:    */ import org.apache.hadoop.classification.InterfaceAudience.Private;
/*   5:    */ import org.apache.hadoop.classification.InterfaceStability.Unstable;
/*   6:    */ import org.apache.hadoop.mapreduce.TaskAttemptID;
/*   7:    */ import org.apache.hadoop.mapreduce.TaskID;
/*   8:    */ import org.apache.hadoop.mapreduce.TaskType;
/*   9:    */ import org.apache.hadoop.yarn.api.records.ContainerId;
/*  10:    */ import org.apache.hadoop.yarn.util.ConverterUtils;
/*  11:    */ 
/*  12:    */ @InterfaceAudience.Private
/*  13:    */ @InterfaceStability.Unstable
/*  14:    */ public class TaskAttemptStartedEvent
/*  15:    */   implements HistoryEvent
/*  16:    */ {
/*  17: 38 */   private TaskAttemptStarted datum = new TaskAttemptStarted();
/*  18:    */   
/*  19:    */   public TaskAttemptStartedEvent(TaskAttemptID attemptId, TaskType taskType, long startTime, String trackerName, int httpPort, int shufflePort, ContainerId containerId, String locality, String avataar)
/*  20:    */   {
/*  21: 56 */     this.datum.attemptId = new Utf8(attemptId.toString());
/*  22: 57 */     this.datum.taskid = new Utf8(attemptId.getTaskID().toString());
/*  23: 58 */     this.datum.startTime = startTime;
/*  24: 59 */     this.datum.taskType = new Utf8(taskType.name());
/*  25: 60 */     this.datum.trackerName = new Utf8(trackerName);
/*  26: 61 */     this.datum.httpPort = httpPort;
/*  27: 62 */     this.datum.shufflePort = shufflePort;
/*  28: 63 */     this.datum.containerId = new Utf8(containerId.toString());
/*  29: 64 */     if (locality != null) {
/*  30: 65 */       this.datum.locality = new Utf8(locality);
/*  31:    */     }
/*  32: 67 */     if (avataar != null) {
/*  33: 68 */       this.datum.avataar = new Utf8(avataar);
/*  34:    */     }
/*  35:    */   }
/*  36:    */   
/*  37:    */   public TaskAttemptStartedEvent(TaskAttemptID attemptId, TaskType taskType, long startTime, String trackerName, int httpPort, int shufflePort, String locality, String avataar)
/*  38:    */   {
/*  39: 77 */     this(attemptId, taskType, startTime, trackerName, httpPort, shufflePort, ConverterUtils.toContainerId("container_-1_-1_-1_-1"), locality, avataar);
/*  40:    */   }
/*  41:    */   
/*  42:    */   TaskAttemptStartedEvent() {}
/*  43:    */   
/*  44:    */   public Object getDatum()
/*  45:    */   {
/*  46: 83 */     return this.datum;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public void setDatum(Object datum)
/*  50:    */   {
/*  51: 85 */     this.datum = ((TaskAttemptStarted)datum);
/*  52:    */   }
/*  53:    */   
/*  54:    */   public TaskID getTaskId()
/*  55:    */   {
/*  56: 89 */     return TaskID.forName(this.datum.taskid.toString());
/*  57:    */   }
/*  58:    */   
/*  59:    */   public String getTrackerName()
/*  60:    */   {
/*  61: 91 */     return this.datum.trackerName.toString();
/*  62:    */   }
/*  63:    */   
/*  64:    */   public long getStartTime()
/*  65:    */   {
/*  66: 93 */     return this.datum.startTime;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public TaskType getTaskType()
/*  70:    */   {
/*  71: 96 */     return TaskType.valueOf(this.datum.taskType.toString());
/*  72:    */   }
/*  73:    */   
/*  74:    */   public int getHttpPort()
/*  75:    */   {
/*  76: 99 */     return this.datum.httpPort;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public int getShufflePort()
/*  80:    */   {
/*  81:101 */     return this.datum.shufflePort;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public TaskAttemptID getTaskAttemptId()
/*  85:    */   {
/*  86:104 */     return TaskAttemptID.forName(this.datum.attemptId.toString());
/*  87:    */   }
/*  88:    */   
/*  89:    */   public EventType getEventType()
/*  90:    */   {
/*  91:110 */     return getTaskId().getTaskType() == TaskType.MAP ? EventType.MAP_ATTEMPT_STARTED : EventType.REDUCE_ATTEMPT_STARTED;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public ContainerId getContainerId()
/*  95:    */   {
/*  96:116 */     return ConverterUtils.toContainerId(this.datum.containerId.toString());
/*  97:    */   }
/*  98:    */   
/*  99:    */   public String getLocality()
/* 100:    */   {
/* 101:120 */     if (this.datum.locality != null) {
/* 102:121 */       return this.datum.locality.toString();
/* 103:    */     }
/* 104:123 */     return null;
/* 105:    */   }
/* 106:    */   
/* 107:    */   public String getAvataar()
/* 108:    */   {
/* 109:127 */     if (this.datum.avataar != null) {
/* 110:128 */       return this.datum.avataar.toString();
/* 111:    */     }
/* 112:130 */     return null;
/* 113:    */   }
/* 114:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.jobhistory.TaskAttemptStartedEvent
 * JD-Core Version:    0.7.0.1
 */