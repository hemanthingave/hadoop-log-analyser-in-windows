/*   1:    */ package org.apache.hadoop.mapreduce;
/*   2:    */ 
/*   3:    */ import java.io.DataInput;
/*   4:    */ import java.io.DataOutput;
/*   5:    */ import java.io.IOException;
/*   6:    */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*   7:    */ import org.apache.hadoop.classification.InterfaceStability.Evolving;
/*   8:    */ import org.apache.hadoop.io.Writable;
/*   9:    */ import org.apache.hadoop.io.WritableUtils;
/*  10:    */ 
/*  11:    */ @InterfaceAudience.Public
/*  12:    */ @InterfaceStability.Evolving
/*  13:    */ public class TaskCompletionEvent
/*  14:    */   implements Writable
/*  15:    */ {
/*  16:    */   private int eventId;
/*  17:    */   private String taskTrackerHttp;
/*  18:    */   private int taskRunTime;
/*  19:    */   private TaskAttemptID taskId;
/*  20:    */   Status status;
/*  21:    */   
/*  22:    */   @InterfaceAudience.Public
/*  23:    */   @InterfaceStability.Evolving
/*  24:    */   public static enum Status
/*  25:    */   {
/*  26: 39 */     FAILED,  KILLED,  SUCCEEDED,  OBSOLETE,  TIPFAILED;
/*  27:    */     
/*  28:    */     private Status() {}
/*  29:    */   }
/*  30:    */   
/*  31: 46 */   boolean isMap = false;
/*  32:    */   private int idWithinJob;
/*  33: 48 */   public static final TaskCompletionEvent[] EMPTY_ARRAY = new TaskCompletionEvent[0];
/*  34:    */   
/*  35:    */   public TaskCompletionEvent()
/*  36:    */   {
/*  37: 55 */     this.taskId = new TaskAttemptID();
/*  38:    */   }
/*  39:    */   
/*  40:    */   public TaskCompletionEvent(int eventId, TaskAttemptID taskId, int idWithinJob, boolean isMap, Status status, String taskTrackerHttp)
/*  41:    */   {
/*  42: 74 */     this.taskId = taskId;
/*  43: 75 */     this.idWithinJob = idWithinJob;
/*  44: 76 */     this.isMap = isMap;
/*  45: 77 */     this.eventId = eventId;
/*  46: 78 */     this.status = status;
/*  47: 79 */     this.taskTrackerHttp = taskTrackerHttp;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public int getEventId()
/*  51:    */   {
/*  52: 86 */     return this.eventId;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public TaskAttemptID getTaskAttemptId()
/*  56:    */   {
/*  57: 94 */     return this.taskId;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public Status getStatus()
/*  61:    */   {
/*  62:102 */     return this.status;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public String getTaskTrackerHttp()
/*  66:    */   {
/*  67:109 */     return this.taskTrackerHttp;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public int getTaskRunTime()
/*  71:    */   {
/*  72:116 */     return this.taskRunTime;
/*  73:    */   }
/*  74:    */   
/*  75:    */   protected void setTaskRunTime(int taskCompletionTime)
/*  76:    */   {
/*  77:124 */     this.taskRunTime = taskCompletionTime;
/*  78:    */   }
/*  79:    */   
/*  80:    */   protected void setEventId(int eventId)
/*  81:    */   {
/*  82:132 */     this.eventId = eventId;
/*  83:    */   }
/*  84:    */   
/*  85:    */   protected void setTaskAttemptId(TaskAttemptID taskId)
/*  86:    */   {
/*  87:140 */     this.taskId = taskId;
/*  88:    */   }
/*  89:    */   
/*  90:    */   protected void setTaskStatus(Status status)
/*  91:    */   {
/*  92:148 */     this.status = status;
/*  93:    */   }
/*  94:    */   
/*  95:    */   protected void setTaskTrackerHttp(String taskTrackerHttp)
/*  96:    */   {
/*  97:156 */     this.taskTrackerHttp = taskTrackerHttp;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public String toString()
/* 101:    */   {
/* 102:161 */     StringBuffer buf = new StringBuffer();
/* 103:162 */     buf.append("Task Id : ");
/* 104:163 */     buf.append(this.taskId);
/* 105:164 */     buf.append(", Status : ");
/* 106:165 */     buf.append(this.status.name());
/* 107:166 */     return buf.toString();
/* 108:    */   }
/* 109:    */   
/* 110:    */   public boolean equals(Object o)
/* 111:    */   {
/* 112:171 */     if (o == null) {
/* 113:172 */       return false;
/* 114:    */     }
/* 115:173 */     if (o.getClass().equals(getClass()))
/* 116:    */     {
/* 117:174 */       TaskCompletionEvent event = (TaskCompletionEvent)o;
/* 118:175 */       return (this.isMap == event.isMapTask()) && (this.eventId == event.getEventId()) && (this.idWithinJob == event.idWithinJob()) && (this.status.equals(event.getStatus())) && (this.taskId.equals(event.getTaskAttemptId())) && (this.taskRunTime == event.getTaskRunTime()) && (this.taskTrackerHttp.equals(event.getTaskTrackerHttp()));
/* 119:    */     }
/* 120:183 */     return false;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public int hashCode()
/* 124:    */   {
/* 125:188 */     return toString().hashCode();
/* 126:    */   }
/* 127:    */   
/* 128:    */   public boolean isMapTask()
/* 129:    */   {
/* 130:192 */     return this.isMap;
/* 131:    */   }
/* 132:    */   
/* 133:    */   public int idWithinJob()
/* 134:    */   {
/* 135:196 */     return this.idWithinJob;
/* 136:    */   }
/* 137:    */   
/* 138:    */   public void write(DataOutput out)
/* 139:    */     throws IOException
/* 140:    */   {
/* 141:202 */     this.taskId.write(out);
/* 142:203 */     WritableUtils.writeVInt(out, this.idWithinJob);
/* 143:204 */     out.writeBoolean(this.isMap);
/* 144:205 */     WritableUtils.writeEnum(out, this.status);
/* 145:206 */     WritableUtils.writeString(out, this.taskTrackerHttp);
/* 146:207 */     WritableUtils.writeVInt(out, this.taskRunTime);
/* 147:208 */     WritableUtils.writeVInt(out, this.eventId);
/* 148:    */   }
/* 149:    */   
/* 150:    */   public void readFields(DataInput in)
/* 151:    */     throws IOException
/* 152:    */   {
/* 153:212 */     this.taskId.readFields(in);
/* 154:213 */     this.idWithinJob = WritableUtils.readVInt(in);
/* 155:214 */     this.isMap = in.readBoolean();
/* 156:215 */     this.status = ((Status)WritableUtils.readEnum(in, Status.class));
/* 157:216 */     this.taskTrackerHttp = WritableUtils.readString(in);
/* 158:217 */     this.taskRunTime = WritableUtils.readVInt(in);
/* 159:218 */     this.eventId = WritableUtils.readVInt(in);
/* 160:    */   }
/* 161:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.TaskCompletionEvent
 * JD-Core Version:    0.7.0.1
 */