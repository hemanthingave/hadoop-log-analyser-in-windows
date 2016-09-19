/*   1:    */ package org.apache.hadoop.mapreduce.jobhistory;
/*   2:    */ 
/*   3:    */ import org.apache.avro.util.Utf8;
/*   4:    */ import org.apache.hadoop.classification.InterfaceAudience.Private;
/*   5:    */ import org.apache.hadoop.classification.InterfaceStability.Unstable;
/*   6:    */ import org.apache.hadoop.mapred.ProgressSplitsBlock;
/*   7:    */ import org.apache.hadoop.mapreduce.Counters;
/*   8:    */ import org.apache.hadoop.mapreduce.TaskAttemptID;
/*   9:    */ import org.apache.hadoop.mapreduce.TaskID;
/*  10:    */ import org.apache.hadoop.mapreduce.TaskType;
/*  11:    */ 
/*  12:    */ @InterfaceAudience.Private
/*  13:    */ @InterfaceStability.Unstable
/*  14:    */ public class ReduceAttemptFinishedEvent
/*  15:    */   implements HistoryEvent
/*  16:    */ {
/*  17: 38 */   private ReduceAttemptFinished datum = null;
/*  18:    */   private TaskAttemptID attemptId;
/*  19:    */   private TaskType taskType;
/*  20:    */   private String taskStatus;
/*  21:    */   private long shuffleFinishTime;
/*  22:    */   private long sortFinishTime;
/*  23:    */   private long finishTime;
/*  24:    */   private String hostname;
/*  25:    */   private String rackName;
/*  26:    */   private int port;
/*  27:    */   private String state;
/*  28:    */   private Counters counters;
/*  29:    */   int[][] allSplits;
/*  30:    */   int[] clockSplits;
/*  31:    */   int[] cpuUsages;
/*  32:    */   int[] vMemKbytes;
/*  33:    */   int[] physMemKbytes;
/*  34:    */   
/*  35:    */   public ReduceAttemptFinishedEvent(TaskAttemptID id, TaskType taskType, String taskStatus, long shuffleFinishTime, long sortFinishTime, long finishTime, String hostname, int port, String rackName, String state, Counters counters, int[][] allSplits)
/*  36:    */   {
/*  37: 80 */     this.attemptId = id;
/*  38: 81 */     this.taskType = taskType;
/*  39: 82 */     this.taskStatus = taskStatus;
/*  40: 83 */     this.shuffleFinishTime = shuffleFinishTime;
/*  41: 84 */     this.sortFinishTime = sortFinishTime;
/*  42: 85 */     this.finishTime = finishTime;
/*  43: 86 */     this.hostname = hostname;
/*  44: 87 */     this.rackName = rackName;
/*  45: 88 */     this.port = port;
/*  46: 89 */     this.state = state;
/*  47: 90 */     this.counters = counters;
/*  48: 91 */     this.allSplits = allSplits;
/*  49: 92 */     this.clockSplits = ProgressSplitsBlock.arrayGetWallclockTime(allSplits);
/*  50: 93 */     this.cpuUsages = ProgressSplitsBlock.arrayGetCPUTime(allSplits);
/*  51: 94 */     this.vMemKbytes = ProgressSplitsBlock.arrayGetVMemKbytes(allSplits);
/*  52: 95 */     this.physMemKbytes = ProgressSplitsBlock.arrayGetPhysMemKbytes(allSplits);
/*  53:    */   }
/*  54:    */   
/*  55:    */   /**
/*  56:    */    * @deprecated
/*  57:    */    */
/*  58:    */   public ReduceAttemptFinishedEvent(TaskAttemptID id, TaskType taskType, String taskStatus, long shuffleFinishTime, long sortFinishTime, long finishTime, String hostname, String state, Counters counters)
/*  59:    */   {
/*  60:119 */     this(id, taskType, taskStatus, shuffleFinishTime, sortFinishTime, finishTime, hostname, -1, "", state, counters, (int[][])null);
/*  61:    */   }
/*  62:    */   
/*  63:    */   ReduceAttemptFinishedEvent() {}
/*  64:    */   
/*  65:    */   public Object getDatum()
/*  66:    */   {
/*  67:127 */     if (this.datum == null)
/*  68:    */     {
/*  69:128 */       this.datum = new ReduceAttemptFinished();
/*  70:129 */       this.datum.taskid = new Utf8(this.attemptId.getTaskID().toString());
/*  71:130 */       this.datum.attemptId = new Utf8(this.attemptId.toString());
/*  72:131 */       this.datum.taskType = new Utf8(this.taskType.name());
/*  73:132 */       this.datum.taskStatus = new Utf8(this.taskStatus);
/*  74:133 */       this.datum.shuffleFinishTime = this.shuffleFinishTime;
/*  75:134 */       this.datum.sortFinishTime = this.sortFinishTime;
/*  76:135 */       this.datum.finishTime = this.finishTime;
/*  77:136 */       this.datum.hostname = new Utf8(this.hostname);
/*  78:137 */       this.datum.port = this.port;
/*  79:138 */       if (this.rackName != null) {
/*  80:139 */         this.datum.rackname = new Utf8(this.rackName);
/*  81:    */       }
/*  82:141 */       this.datum.state = new Utf8(this.state);
/*  83:142 */       this.datum.counters = EventWriter.toAvro(this.counters);
/*  84:    */       
/*  85:144 */       this.datum.clockSplits = AvroArrayUtils.toAvro(ProgressSplitsBlock.arrayGetWallclockTime(this.allSplits));
/*  86:    */       
/*  87:146 */       this.datum.cpuUsages = AvroArrayUtils.toAvro(ProgressSplitsBlock.arrayGetCPUTime(this.allSplits));
/*  88:    */       
/*  89:148 */       this.datum.vMemKbytes = AvroArrayUtils.toAvro(ProgressSplitsBlock.arrayGetVMemKbytes(this.allSplits));
/*  90:    */       
/*  91:150 */       this.datum.physMemKbytes = AvroArrayUtils.toAvro(ProgressSplitsBlock.arrayGetPhysMemKbytes(this.allSplits));
/*  92:    */     }
/*  93:153 */     return this.datum;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public void setDatum(Object oDatum)
/*  97:    */   {
/*  98:157 */     this.datum = ((ReduceAttemptFinished)oDatum);
/*  99:158 */     this.attemptId = TaskAttemptID.forName(this.datum.attemptId.toString());
/* 100:159 */     this.taskType = TaskType.valueOf(this.datum.taskType.toString());
/* 101:160 */     this.taskStatus = this.datum.taskStatus.toString();
/* 102:161 */     this.shuffleFinishTime = this.datum.shuffleFinishTime;
/* 103:162 */     this.sortFinishTime = this.datum.sortFinishTime;
/* 104:163 */     this.finishTime = this.datum.finishTime;
/* 105:164 */     this.hostname = this.datum.hostname.toString();
/* 106:165 */     this.rackName = this.datum.rackname.toString();
/* 107:166 */     this.port = this.datum.port;
/* 108:167 */     this.state = this.datum.state.toString();
/* 109:168 */     this.counters = EventReader.fromAvro(this.datum.counters);
/* 110:169 */     this.clockSplits = AvroArrayUtils.fromAvro(this.datum.clockSplits);
/* 111:170 */     this.cpuUsages = AvroArrayUtils.fromAvro(this.datum.cpuUsages);
/* 112:171 */     this.vMemKbytes = AvroArrayUtils.fromAvro(this.datum.vMemKbytes);
/* 113:172 */     this.physMemKbytes = AvroArrayUtils.fromAvro(this.datum.physMemKbytes);
/* 114:    */   }
/* 115:    */   
/* 116:    */   public TaskID getTaskId()
/* 117:    */   {
/* 118:176 */     return this.attemptId.getTaskID();
/* 119:    */   }
/* 120:    */   
/* 121:    */   public TaskAttemptID getAttemptId()
/* 122:    */   {
/* 123:179 */     return this.attemptId;
/* 124:    */   }
/* 125:    */   
/* 126:    */   public TaskType getTaskType()
/* 127:    */   {
/* 128:183 */     return this.taskType;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public String getTaskStatus()
/* 132:    */   {
/* 133:186 */     return this.taskStatus.toString();
/* 134:    */   }
/* 135:    */   
/* 136:    */   public long getSortFinishTime()
/* 137:    */   {
/* 138:188 */     return this.sortFinishTime;
/* 139:    */   }
/* 140:    */   
/* 141:    */   public long getShuffleFinishTime()
/* 142:    */   {
/* 143:190 */     return this.shuffleFinishTime;
/* 144:    */   }
/* 145:    */   
/* 146:    */   public long getFinishTime()
/* 147:    */   {
/* 148:192 */     return this.finishTime;
/* 149:    */   }
/* 150:    */   
/* 151:    */   public String getHostname()
/* 152:    */   {
/* 153:194 */     return this.hostname.toString();
/* 154:    */   }
/* 155:    */   
/* 156:    */   public int getPort()
/* 157:    */   {
/* 158:196 */     return this.port;
/* 159:    */   }
/* 160:    */   
/* 161:    */   public String getRackName()
/* 162:    */   {
/* 163:200 */     return this.rackName == null ? null : this.rackName.toString();
/* 164:    */   }
/* 165:    */   
/* 166:    */   public String getState()
/* 167:    */   {
/* 168:204 */     return this.state.toString();
/* 169:    */   }
/* 170:    */   
/* 171:    */   Counters getCounters()
/* 172:    */   {
/* 173:206 */     return this.counters;
/* 174:    */   }
/* 175:    */   
/* 176:    */   public EventType getEventType()
/* 177:    */   {
/* 178:209 */     return EventType.REDUCE_ATTEMPT_FINISHED;
/* 179:    */   }
/* 180:    */   
/* 181:    */   public int[] getClockSplits()
/* 182:    */   {
/* 183:214 */     return this.clockSplits;
/* 184:    */   }
/* 185:    */   
/* 186:    */   public int[] getCpuUsages()
/* 187:    */   {
/* 188:217 */     return this.cpuUsages;
/* 189:    */   }
/* 190:    */   
/* 191:    */   public int[] getVMemKbytes()
/* 192:    */   {
/* 193:220 */     return this.vMemKbytes;
/* 194:    */   }
/* 195:    */   
/* 196:    */   public int[] getPhysMemKbytes()
/* 197:    */   {
/* 198:223 */     return this.physMemKbytes;
/* 199:    */   }
/* 200:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.jobhistory.ReduceAttemptFinishedEvent
 * JD-Core Version:    0.7.0.1
 */