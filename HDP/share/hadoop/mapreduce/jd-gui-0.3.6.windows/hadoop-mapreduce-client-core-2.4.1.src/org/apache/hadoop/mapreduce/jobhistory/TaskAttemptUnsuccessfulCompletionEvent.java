/*   1:    */ package org.apache.hadoop.mapreduce.jobhistory;
/*   2:    */ 
/*   3:    */ import org.apache.avro.util.Utf8;
/*   4:    */ import org.apache.hadoop.classification.InterfaceAudience.Private;
/*   5:    */ import org.apache.hadoop.classification.InterfaceStability.Unstable;
/*   6:    */ import org.apache.hadoop.mapred.ProgressSplitsBlock;
/*   7:    */ import org.apache.hadoop.mapred.TaskStatus.State;
/*   8:    */ import org.apache.hadoop.mapreduce.Counters;
/*   9:    */ import org.apache.hadoop.mapreduce.TaskAttemptID;
/*  10:    */ import org.apache.hadoop.mapreduce.TaskID;
/*  11:    */ import org.apache.hadoop.mapreduce.TaskType;
/*  12:    */ 
/*  13:    */ @InterfaceAudience.Private
/*  14:    */ @InterfaceStability.Unstable
/*  15:    */ public class TaskAttemptUnsuccessfulCompletionEvent
/*  16:    */   implements HistoryEvent
/*  17:    */ {
/*  18: 41 */   private TaskAttemptUnsuccessfulCompletion datum = null;
/*  19:    */   private TaskAttemptID attemptId;
/*  20:    */   private TaskType taskType;
/*  21:    */   private String status;
/*  22:    */   private long finishTime;
/*  23:    */   private String hostname;
/*  24:    */   private int port;
/*  25:    */   private String rackName;
/*  26:    */   private String error;
/*  27:    */   private Counters counters;
/*  28:    */   int[][] allSplits;
/*  29:    */   int[] clockSplits;
/*  30:    */   int[] cpuUsages;
/*  31:    */   int[] vMemKbytes;
/*  32:    */   int[] physMemKbytes;
/*  33: 57 */   private static final Counters EMPTY_COUNTERS = new Counters();
/*  34:    */   
/*  35:    */   public TaskAttemptUnsuccessfulCompletionEvent(TaskAttemptID id, TaskType taskType, String status, long finishTime, String hostname, int port, String rackName, String error, Counters counters, int[][] allSplits)
/*  36:    */   {
/*  37: 80 */     this.attemptId = id;
/*  38: 81 */     this.taskType = taskType;
/*  39: 82 */     this.status = status;
/*  40: 83 */     this.finishTime = finishTime;
/*  41: 84 */     this.hostname = hostname;
/*  42: 85 */     this.port = port;
/*  43: 86 */     this.rackName = rackName;
/*  44: 87 */     this.error = error;
/*  45: 88 */     this.counters = counters;
/*  46: 89 */     this.allSplits = allSplits;
/*  47: 90 */     this.clockSplits = ProgressSplitsBlock.arrayGetWallclockTime(allSplits);
/*  48:    */     
/*  49: 92 */     this.cpuUsages = ProgressSplitsBlock.arrayGetCPUTime(allSplits);
/*  50:    */     
/*  51: 94 */     this.vMemKbytes = ProgressSplitsBlock.arrayGetVMemKbytes(allSplits);
/*  52:    */     
/*  53: 96 */     this.physMemKbytes = ProgressSplitsBlock.arrayGetPhysMemKbytes(allSplits);
/*  54:    */   }
/*  55:    */   
/*  56:    */   /**
/*  57:    */    * @deprecated
/*  58:    */    */
/*  59:    */   public TaskAttemptUnsuccessfulCompletionEvent(TaskAttemptID id, TaskType taskType, String status, long finishTime, String hostname, String error)
/*  60:    */   {
/*  61:118 */     this(id, taskType, status, finishTime, hostname, -1, "", error, EMPTY_COUNTERS, (int[][])null);
/*  62:    */   }
/*  63:    */   
/*  64:    */   public TaskAttemptUnsuccessfulCompletionEvent(TaskAttemptID id, TaskType taskType, String status, long finishTime, String hostname, int port, String rackName, String error, int[][] allSplits)
/*  65:    */   {
/*  66:127 */     this(id, taskType, status, finishTime, hostname, port, rackName, error, EMPTY_COUNTERS, (int[][])null);
/*  67:    */   }
/*  68:    */   
/*  69:    */   TaskAttemptUnsuccessfulCompletionEvent() {}
/*  70:    */   
/*  71:    */   public Object getDatum()
/*  72:    */   {
/*  73:134 */     if (this.datum == null)
/*  74:    */     {
/*  75:135 */       this.datum = new TaskAttemptUnsuccessfulCompletion();
/*  76:136 */       this.datum.taskid = new Utf8(this.attemptId.getTaskID().toString());
/*  77:137 */       this.datum.taskType = new Utf8(this.taskType.name());
/*  78:138 */       this.datum.attemptId = new Utf8(this.attemptId.toString());
/*  79:139 */       this.datum.finishTime = this.finishTime;
/*  80:140 */       this.datum.hostname = new Utf8(this.hostname);
/*  81:141 */       if (this.rackName != null) {
/*  82:142 */         this.datum.rackname = new Utf8(this.rackName);
/*  83:    */       }
/*  84:144 */       this.datum.port = this.port;
/*  85:145 */       this.datum.error = new Utf8(this.error);
/*  86:146 */       this.datum.status = new Utf8(this.status);
/*  87:    */       
/*  88:148 */       this.datum.counters = EventWriter.toAvro(this.counters);
/*  89:    */       
/*  90:150 */       this.datum.clockSplits = AvroArrayUtils.toAvro(ProgressSplitsBlock.arrayGetWallclockTime(this.allSplits));
/*  91:    */       
/*  92:152 */       this.datum.cpuUsages = AvroArrayUtils.toAvro(ProgressSplitsBlock.arrayGetCPUTime(this.allSplits));
/*  93:    */       
/*  94:154 */       this.datum.vMemKbytes = AvroArrayUtils.toAvro(ProgressSplitsBlock.arrayGetVMemKbytes(this.allSplits));
/*  95:    */       
/*  96:156 */       this.datum.physMemKbytes = AvroArrayUtils.toAvro(ProgressSplitsBlock.arrayGetPhysMemKbytes(this.allSplits));
/*  97:    */     }
/*  98:159 */     return this.datum;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public void setDatum(Object odatum)
/* 102:    */   {
/* 103:165 */     this.datum = ((TaskAttemptUnsuccessfulCompletion)odatum);
/* 104:    */     
/* 105:167 */     this.attemptId = TaskAttemptID.forName(this.datum.attemptId.toString());
/* 106:    */     
/* 107:169 */     this.taskType = TaskType.valueOf(this.datum.taskType.toString());
/* 108:    */     
/* 109:171 */     this.finishTime = this.datum.finishTime;
/* 110:172 */     this.hostname = this.datum.hostname.toString();
/* 111:173 */     this.rackName = this.datum.rackname.toString();
/* 112:174 */     this.port = this.datum.port;
/* 113:175 */     this.status = this.datum.status.toString();
/* 114:176 */     this.error = this.datum.error.toString();
/* 115:177 */     this.counters = EventReader.fromAvro(this.datum.counters);
/* 116:    */     
/* 117:179 */     this.clockSplits = AvroArrayUtils.fromAvro(this.datum.clockSplits);
/* 118:    */     
/* 119:181 */     this.cpuUsages = AvroArrayUtils.fromAvro(this.datum.cpuUsages);
/* 120:    */     
/* 121:183 */     this.vMemKbytes = AvroArrayUtils.fromAvro(this.datum.vMemKbytes);
/* 122:    */     
/* 123:185 */     this.physMemKbytes = AvroArrayUtils.fromAvro(this.datum.physMemKbytes);
/* 124:    */   }
/* 125:    */   
/* 126:    */   public TaskID getTaskId()
/* 127:    */   {
/* 128:191 */     return this.attemptId.getTaskID();
/* 129:    */   }
/* 130:    */   
/* 131:    */   public TaskType getTaskType()
/* 132:    */   {
/* 133:195 */     return TaskType.valueOf(this.taskType.toString());
/* 134:    */   }
/* 135:    */   
/* 136:    */   public TaskAttemptID getTaskAttemptId()
/* 137:    */   {
/* 138:199 */     return this.attemptId;
/* 139:    */   }
/* 140:    */   
/* 141:    */   public long getFinishTime()
/* 142:    */   {
/* 143:202 */     return this.finishTime;
/* 144:    */   }
/* 145:    */   
/* 146:    */   public String getHostname()
/* 147:    */   {
/* 148:204 */     return this.hostname;
/* 149:    */   }
/* 150:    */   
/* 151:    */   public int getPort()
/* 152:    */   {
/* 153:206 */     return this.port;
/* 154:    */   }
/* 155:    */   
/* 156:    */   public String getRackName()
/* 157:    */   {
/* 158:210 */     return this.rackName == null ? null : this.rackName.toString();
/* 159:    */   }
/* 160:    */   
/* 161:    */   public String getError()
/* 162:    */   {
/* 163:214 */     return this.error.toString();
/* 164:    */   }
/* 165:    */   
/* 166:    */   public String getTaskStatus()
/* 167:    */   {
/* 168:217 */     return this.status.toString();
/* 169:    */   }
/* 170:    */   
/* 171:    */   Counters getCounters()
/* 172:    */   {
/* 173:220 */     return this.counters;
/* 174:    */   }
/* 175:    */   
/* 176:    */   public EventType getEventType()
/* 177:    */   {
/* 178:226 */     boolean failed = TaskStatus.State.FAILED.toString().equals(getTaskStatus());
/* 179:227 */     return failed ? EventType.REDUCE_ATTEMPT_FAILED : getTaskId().getTaskType() == TaskType.MAP ? EventType.MAP_ATTEMPT_KILLED : failed ? EventType.MAP_ATTEMPT_FAILED : EventType.REDUCE_ATTEMPT_KILLED;
/* 180:    */   }
/* 181:    */   
/* 182:    */   public int[] getClockSplits()
/* 183:    */   {
/* 184:239 */     return this.clockSplits;
/* 185:    */   }
/* 186:    */   
/* 187:    */   public int[] getCpuUsages()
/* 188:    */   {
/* 189:242 */     return this.cpuUsages;
/* 190:    */   }
/* 191:    */   
/* 192:    */   public int[] getVMemKbytes()
/* 193:    */   {
/* 194:245 */     return this.vMemKbytes;
/* 195:    */   }
/* 196:    */   
/* 197:    */   public int[] getPhysMemKbytes()
/* 198:    */   {
/* 199:248 */     return this.physMemKbytes;
/* 200:    */   }
/* 201:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.jobhistory.TaskAttemptUnsuccessfulCompletionEvent
 * JD-Core Version:    0.7.0.1
 */