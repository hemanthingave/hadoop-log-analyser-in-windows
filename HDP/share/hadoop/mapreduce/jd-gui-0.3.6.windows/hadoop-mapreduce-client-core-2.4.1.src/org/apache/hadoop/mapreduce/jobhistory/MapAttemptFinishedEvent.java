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
/*  14:    */ public class MapAttemptFinishedEvent
/*  15:    */   implements HistoryEvent
/*  16:    */ {
/*  17: 38 */   private MapAttemptFinished datum = null;
/*  18:    */   private TaskAttemptID attemptId;
/*  19:    */   private TaskType taskType;
/*  20:    */   private String taskStatus;
/*  21:    */   private long finishTime;
/*  22:    */   private String hostname;
/*  23:    */   private String rackName;
/*  24:    */   private int port;
/*  25:    */   private long mapFinishTime;
/*  26:    */   private String state;
/*  27:    */   private Counters counters;
/*  28:    */   int[][] allSplits;
/*  29:    */   int[] clockSplits;
/*  30:    */   int[] cpuUsages;
/*  31:    */   int[] vMemKbytes;
/*  32:    */   int[] physMemKbytes;
/*  33:    */   
/*  34:    */   public MapAttemptFinishedEvent(TaskAttemptID id, TaskType taskType, String taskStatus, long mapFinishTime, long finishTime, String hostname, int port, String rackName, String state, Counters counters, int[][] allSplits)
/*  35:    */   {
/*  36: 80 */     this.attemptId = id;
/*  37: 81 */     this.taskType = taskType;
/*  38: 82 */     this.taskStatus = taskStatus;
/*  39: 83 */     this.mapFinishTime = mapFinishTime;
/*  40: 84 */     this.finishTime = finishTime;
/*  41: 85 */     this.hostname = hostname;
/*  42: 86 */     this.rackName = rackName;
/*  43: 87 */     this.port = port;
/*  44: 88 */     this.state = state;
/*  45: 89 */     this.counters = counters;
/*  46: 90 */     this.allSplits = allSplits;
/*  47: 91 */     this.clockSplits = ProgressSplitsBlock.arrayGetWallclockTime(allSplits);
/*  48: 92 */     this.cpuUsages = ProgressSplitsBlock.arrayGetCPUTime(allSplits);
/*  49: 93 */     this.vMemKbytes = ProgressSplitsBlock.arrayGetVMemKbytes(allSplits);
/*  50: 94 */     this.physMemKbytes = ProgressSplitsBlock.arrayGetPhysMemKbytes(allSplits);
/*  51:    */   }
/*  52:    */   
/*  53:    */   @Deprecated
/*  54:    */   public MapAttemptFinishedEvent(TaskAttemptID id, TaskType taskType, String taskStatus, long mapFinishTime, long finishTime, String hostname, String state, Counters counters)
/*  55:    */   {
/*  56:118 */     this(id, taskType, taskStatus, mapFinishTime, finishTime, hostname, -1, "", state, counters, (int[][])null);
/*  57:    */   }
/*  58:    */   
/*  59:    */   MapAttemptFinishedEvent() {}
/*  60:    */   
/*  61:    */   public Object getDatum()
/*  62:    */   {
/*  63:126 */     if (this.datum == null)
/*  64:    */     {
/*  65:127 */       this.datum = new MapAttemptFinished();
/*  66:128 */       this.datum.taskid = new Utf8(this.attemptId.getTaskID().toString());
/*  67:129 */       this.datum.attemptId = new Utf8(this.attemptId.toString());
/*  68:130 */       this.datum.taskType = new Utf8(this.taskType.name());
/*  69:131 */       this.datum.taskStatus = new Utf8(this.taskStatus);
/*  70:132 */       this.datum.mapFinishTime = this.mapFinishTime;
/*  71:133 */       this.datum.finishTime = this.finishTime;
/*  72:134 */       this.datum.hostname = new Utf8(this.hostname);
/*  73:135 */       this.datum.port = this.port;
/*  74:136 */       if (this.rackName != null) {
/*  75:137 */         this.datum.rackname = new Utf8(this.rackName);
/*  76:    */       }
/*  77:139 */       this.datum.state = new Utf8(this.state);
/*  78:140 */       this.datum.counters = EventWriter.toAvro(this.counters);
/*  79:    */       
/*  80:142 */       this.datum.clockSplits = AvroArrayUtils.toAvro(ProgressSplitsBlock.arrayGetWallclockTime(this.allSplits));
/*  81:    */       
/*  82:144 */       this.datum.cpuUsages = AvroArrayUtils.toAvro(ProgressSplitsBlock.arrayGetCPUTime(this.allSplits));
/*  83:    */       
/*  84:146 */       this.datum.vMemKbytes = AvroArrayUtils.toAvro(ProgressSplitsBlock.arrayGetVMemKbytes(this.allSplits));
/*  85:    */       
/*  86:148 */       this.datum.physMemKbytes = AvroArrayUtils.toAvro(ProgressSplitsBlock.arrayGetPhysMemKbytes(this.allSplits));
/*  87:    */     }
/*  88:151 */     return this.datum;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public void setDatum(Object oDatum)
/*  92:    */   {
/*  93:155 */     this.datum = ((MapAttemptFinished)oDatum);
/*  94:156 */     this.attemptId = TaskAttemptID.forName(this.datum.attemptId.toString());
/*  95:157 */     this.taskType = TaskType.valueOf(this.datum.taskType.toString());
/*  96:158 */     this.taskStatus = this.datum.taskStatus.toString();
/*  97:159 */     this.mapFinishTime = this.datum.mapFinishTime;
/*  98:160 */     this.finishTime = this.datum.finishTime;
/*  99:161 */     this.hostname = this.datum.hostname.toString();
/* 100:162 */     this.rackName = this.datum.rackname.toString();
/* 101:163 */     this.port = this.datum.port;
/* 102:164 */     this.state = this.datum.state.toString();
/* 103:165 */     this.counters = EventReader.fromAvro(this.datum.counters);
/* 104:166 */     this.clockSplits = AvroArrayUtils.fromAvro(this.datum.clockSplits);
/* 105:167 */     this.cpuUsages = AvroArrayUtils.fromAvro(this.datum.cpuUsages);
/* 106:168 */     this.vMemKbytes = AvroArrayUtils.fromAvro(this.datum.vMemKbytes);
/* 107:169 */     this.physMemKbytes = AvroArrayUtils.fromAvro(this.datum.physMemKbytes);
/* 108:    */   }
/* 109:    */   
/* 110:    */   public TaskID getTaskId()
/* 111:    */   {
/* 112:173 */     return this.attemptId.getTaskID();
/* 113:    */   }
/* 114:    */   
/* 115:    */   public TaskAttemptID getAttemptId()
/* 116:    */   {
/* 117:176 */     return this.attemptId;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public TaskType getTaskType()
/* 121:    */   {
/* 122:181 */     return this.taskType;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public String getTaskStatus()
/* 126:    */   {
/* 127:184 */     return this.taskStatus.toString();
/* 128:    */   }
/* 129:    */   
/* 130:    */   public long getMapFinishTime()
/* 131:    */   {
/* 132:186 */     return this.mapFinishTime;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public long getFinishTime()
/* 136:    */   {
/* 137:188 */     return this.finishTime;
/* 138:    */   }
/* 139:    */   
/* 140:    */   public String getHostname()
/* 141:    */   {
/* 142:190 */     return this.hostname.toString();
/* 143:    */   }
/* 144:    */   
/* 145:    */   public int getPort()
/* 146:    */   {
/* 147:192 */     return this.port;
/* 148:    */   }
/* 149:    */   
/* 150:    */   public String getRackName()
/* 151:    */   {
/* 152:196 */     return this.rackName == null ? null : this.rackName.toString();
/* 153:    */   }
/* 154:    */   
/* 155:    */   public String getState()
/* 156:    */   {
/* 157:200 */     return this.state.toString();
/* 158:    */   }
/* 159:    */   
/* 160:    */   Counters getCounters()
/* 161:    */   {
/* 162:202 */     return this.counters;
/* 163:    */   }
/* 164:    */   
/* 165:    */   public EventType getEventType()
/* 166:    */   {
/* 167:205 */     return EventType.MAP_ATTEMPT_FINISHED;
/* 168:    */   }
/* 169:    */   
/* 170:    */   public int[] getClockSplits()
/* 171:    */   {
/* 172:209 */     return this.clockSplits;
/* 173:    */   }
/* 174:    */   
/* 175:    */   public int[] getCpuUsages()
/* 176:    */   {
/* 177:212 */     return this.cpuUsages;
/* 178:    */   }
/* 179:    */   
/* 180:    */   public int[] getVMemKbytes()
/* 181:    */   {
/* 182:215 */     return this.vMemKbytes;
/* 183:    */   }
/* 184:    */   
/* 185:    */   public int[] getPhysMemKbytes()
/* 186:    */   {
/* 187:218 */     return this.physMemKbytes;
/* 188:    */   }
/* 189:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.jobhistory.MapAttemptFinishedEvent
 * JD-Core Version:    0.7.0.1
 */