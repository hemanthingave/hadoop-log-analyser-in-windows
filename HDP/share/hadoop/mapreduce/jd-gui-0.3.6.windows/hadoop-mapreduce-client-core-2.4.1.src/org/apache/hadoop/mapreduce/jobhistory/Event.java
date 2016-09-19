/*   1:    */ package org.apache.hadoop.mapreduce.jobhistory;
/*   2:    */ 
/*   3:    */ import org.apache.avro.AvroRuntimeException;
/*   4:    */ import org.apache.avro.Schema;
/*   5:    */ import org.apache.avro.Schema.Field;
/*   6:    */ import org.apache.avro.Schema.Parser;
/*   7:    */ import org.apache.avro.data.RecordBuilder;
/*   8:    */ import org.apache.avro.generic.GenericData;
/*   9:    */ import org.apache.avro.specific.AvroGenerated;
/*  10:    */ import org.apache.avro.specific.SpecificRecord;
/*  11:    */ import org.apache.avro.specific.SpecificRecordBase;
/*  12:    */ import org.apache.avro.specific.SpecificRecordBuilderBase;
/*  13:    */ 
/*  14:    */ @AvroGenerated
/*  15:    */ public class Event
/*  16:    */   extends SpecificRecordBase
/*  17:    */   implements SpecificRecord
/*  18:    */ {
/*  19: 10 */   public static final Schema SCHEMA$ = new Schema.Parser().parse("{\"type\":\"record\",\"name\":\"Event\",\"namespace\":\"org.apache.hadoop.mapreduce.jobhistory\",\"fields\":[{\"name\":\"type\",\"type\":{\"type\":\"enum\",\"name\":\"EventType\",\"symbols\":[\"JOB_SUBMITTED\",\"JOB_INITED\",\"JOB_FINISHED\",\"JOB_PRIORITY_CHANGED\",\"JOB_STATUS_CHANGED\",\"JOB_QUEUE_CHANGED\",\"JOB_FAILED\",\"JOB_KILLED\",\"JOB_ERROR\",\"JOB_INFO_CHANGED\",\"TASK_STARTED\",\"TASK_FINISHED\",\"TASK_FAILED\",\"TASK_UPDATED\",\"NORMALIZED_RESOURCE\",\"MAP_ATTEMPT_STARTED\",\"MAP_ATTEMPT_FINISHED\",\"MAP_ATTEMPT_FAILED\",\"MAP_ATTEMPT_KILLED\",\"REDUCE_ATTEMPT_STARTED\",\"REDUCE_ATTEMPT_FINISHED\",\"REDUCE_ATTEMPT_FAILED\",\"REDUCE_ATTEMPT_KILLED\",\"SETUP_ATTEMPT_STARTED\",\"SETUP_ATTEMPT_FINISHED\",\"SETUP_ATTEMPT_FAILED\",\"SETUP_ATTEMPT_KILLED\",\"CLEANUP_ATTEMPT_STARTED\",\"CLEANUP_ATTEMPT_FINISHED\",\"CLEANUP_ATTEMPT_FAILED\",\"CLEANUP_ATTEMPT_KILLED\",\"AM_STARTED\"]}},{\"name\":\"event\",\"type\":[{\"type\":\"record\",\"name\":\"JobFinished\",\"fields\":[{\"name\":\"jobid\",\"type\":\"string\"},{\"name\":\"finishTime\",\"type\":\"long\"},{\"name\":\"finishedMaps\",\"type\":\"int\"},{\"name\":\"finishedReduces\",\"type\":\"int\"},{\"name\":\"failedMaps\",\"type\":\"int\"},{\"name\":\"failedReduces\",\"type\":\"int\"},{\"name\":\"totalCounters\",\"type\":{\"type\":\"record\",\"name\":\"JhCounters\",\"fields\":[{\"name\":\"name\",\"type\":\"string\"},{\"name\":\"groups\",\"type\":{\"type\":\"array\",\"items\":{\"type\":\"record\",\"name\":\"JhCounterGroup\",\"fields\":[{\"name\":\"name\",\"type\":\"string\"},{\"name\":\"displayName\",\"type\":\"string\"},{\"name\":\"counts\",\"type\":{\"type\":\"array\",\"items\":{\"type\":\"record\",\"name\":\"JhCounter\",\"fields\":[{\"name\":\"name\",\"type\":\"string\"},{\"name\":\"displayName\",\"type\":\"string\"},{\"name\":\"value\",\"type\":\"long\"}]}}}]}}}]}},{\"name\":\"mapCounters\",\"type\":\"JhCounters\"},{\"name\":\"reduceCounters\",\"type\":\"JhCounters\"}]},{\"type\":\"record\",\"name\":\"JobInfoChange\",\"fields\":[{\"name\":\"jobid\",\"type\":\"string\"},{\"name\":\"submitTime\",\"type\":\"long\"},{\"name\":\"launchTime\",\"type\":\"long\"}]},{\"type\":\"record\",\"name\":\"JobInited\",\"fields\":[{\"name\":\"jobid\",\"type\":\"string\"},{\"name\":\"launchTime\",\"type\":\"long\"},{\"name\":\"totalMaps\",\"type\":\"int\"},{\"name\":\"totalReduces\",\"type\":\"int\"},{\"name\":\"jobStatus\",\"type\":\"string\"},{\"name\":\"uberized\",\"type\":\"boolean\"}]},{\"type\":\"record\",\"name\":\"AMStarted\",\"fields\":[{\"name\":\"applicationAttemptId\",\"type\":\"string\"},{\"name\":\"startTime\",\"type\":\"long\"},{\"name\":\"containerId\",\"type\":\"string\"},{\"name\":\"nodeManagerHost\",\"type\":\"string\"},{\"name\":\"nodeManagerPort\",\"type\":\"int\"},{\"name\":\"nodeManagerHttpPort\",\"type\":\"int\"}]},{\"type\":\"record\",\"name\":\"JobPriorityChange\",\"fields\":[{\"name\":\"jobid\",\"type\":\"string\"},{\"name\":\"priority\",\"type\":\"string\"}]},{\"type\":\"record\",\"name\":\"JobQueueChange\",\"fields\":[{\"name\":\"jobid\",\"type\":\"string\"},{\"name\":\"jobQueueName\",\"type\":\"string\"}]},{\"type\":\"record\",\"name\":\"JobStatusChanged\",\"fields\":[{\"name\":\"jobid\",\"type\":\"string\"},{\"name\":\"jobStatus\",\"type\":\"string\"}]},{\"type\":\"record\",\"name\":\"JobSubmitted\",\"fields\":[{\"name\":\"jobid\",\"type\":\"string\"},{\"name\":\"jobName\",\"type\":\"string\"},{\"name\":\"userName\",\"type\":\"string\"},{\"name\":\"submitTime\",\"type\":\"long\"},{\"name\":\"jobConfPath\",\"type\":\"string\"},{\"name\":\"acls\",\"type\":{\"type\":\"map\",\"values\":\"string\"}},{\"name\":\"jobQueueName\",\"type\":\"string\"},{\"name\":\"workflowId\",\"type\":\"string\"},{\"name\":\"workflowName\",\"type\":\"string\"},{\"name\":\"workflowNodeName\",\"type\":\"string\"},{\"name\":\"workflowAdjacencies\",\"type\":\"string\"},{\"name\":\"workflowTags\",\"type\":\"string\"}]},{\"type\":\"record\",\"name\":\"JobUnsuccessfulCompletion\",\"fields\":[{\"name\":\"jobid\",\"type\":\"string\"},{\"name\":\"finishTime\",\"type\":\"long\"},{\"name\":\"finishedMaps\",\"type\":\"int\"},{\"name\":\"finishedReduces\",\"type\":\"int\"},{\"name\":\"jobStatus\",\"type\":\"string\"},{\"name\":\"diagnostics\",\"type\":\"string\"}]},{\"type\":\"record\",\"name\":\"MapAttemptFinished\",\"fields\":[{\"name\":\"taskid\",\"type\":\"string\"},{\"name\":\"attemptId\",\"type\":\"string\"},{\"name\":\"taskType\",\"type\":\"string\"},{\"name\":\"taskStatus\",\"type\":\"string\"},{\"name\":\"mapFinishTime\",\"type\":\"long\"},{\"name\":\"finishTime\",\"type\":\"long\"},{\"name\":\"hostname\",\"type\":\"string\"},{\"name\":\"port\",\"type\":\"int\"},{\"name\":\"rackname\",\"type\":\"string\"},{\"name\":\"state\",\"type\":\"string\"},{\"name\":\"counters\",\"type\":\"JhCounters\"},{\"name\":\"clockSplits\",\"type\":{\"type\":\"array\",\"items\":\"int\"}},{\"name\":\"cpuUsages\",\"type\":{\"type\":\"array\",\"items\":\"int\"}},{\"name\":\"vMemKbytes\",\"type\":{\"type\":\"array\",\"items\":\"int\"}},{\"name\":\"physMemKbytes\",\"type\":{\"type\":\"array\",\"items\":\"int\"}}]},{\"type\":\"record\",\"name\":\"ReduceAttemptFinished\",\"fields\":[{\"name\":\"taskid\",\"type\":\"string\"},{\"name\":\"attemptId\",\"type\":\"string\"},{\"name\":\"taskType\",\"type\":\"string\"},{\"name\":\"taskStatus\",\"type\":\"string\"},{\"name\":\"shuffleFinishTime\",\"type\":\"long\"},{\"name\":\"sortFinishTime\",\"type\":\"long\"},{\"name\":\"finishTime\",\"type\":\"long\"},{\"name\":\"hostname\",\"type\":\"string\"},{\"name\":\"port\",\"type\":\"int\"},{\"name\":\"rackname\",\"type\":\"string\"},{\"name\":\"state\",\"type\":\"string\"},{\"name\":\"counters\",\"type\":\"JhCounters\"},{\"name\":\"clockSplits\",\"type\":{\"type\":\"array\",\"items\":\"int\"}},{\"name\":\"cpuUsages\",\"type\":{\"type\":\"array\",\"items\":\"int\"}},{\"name\":\"vMemKbytes\",\"type\":{\"type\":\"array\",\"items\":\"int\"}},{\"name\":\"physMemKbytes\",\"type\":{\"type\":\"array\",\"items\":\"int\"}}]},{\"type\":\"record\",\"name\":\"TaskAttemptFinished\",\"fields\":[{\"name\":\"taskid\",\"type\":\"string\"},{\"name\":\"attemptId\",\"type\":\"string\"},{\"name\":\"taskType\",\"type\":\"string\"},{\"name\":\"taskStatus\",\"type\":\"string\"},{\"name\":\"finishTime\",\"type\":\"long\"},{\"name\":\"rackname\",\"type\":\"string\"},{\"name\":\"hostname\",\"type\":\"string\"},{\"name\":\"state\",\"type\":\"string\"},{\"name\":\"counters\",\"type\":\"JhCounters\"}]},{\"type\":\"record\",\"name\":\"TaskAttemptStarted\",\"fields\":[{\"name\":\"taskid\",\"type\":\"string\"},{\"name\":\"taskType\",\"type\":\"string\"},{\"name\":\"attemptId\",\"type\":\"string\"},{\"name\":\"startTime\",\"type\":\"long\"},{\"name\":\"trackerName\",\"type\":\"string\"},{\"name\":\"httpPort\",\"type\":\"int\"},{\"name\":\"shufflePort\",\"type\":\"int\"},{\"name\":\"containerId\",\"type\":\"string\"},{\"name\":\"locality\",\"type\":\"string\"},{\"name\":\"avataar\",\"type\":\"string\"}]},{\"type\":\"record\",\"name\":\"TaskAttemptUnsuccessfulCompletion\",\"fields\":[{\"name\":\"taskid\",\"type\":\"string\"},{\"name\":\"taskType\",\"type\":\"string\"},{\"name\":\"attemptId\",\"type\":\"string\"},{\"name\":\"finishTime\",\"type\":\"long\"},{\"name\":\"hostname\",\"type\":\"string\"},{\"name\":\"port\",\"type\":\"int\"},{\"name\":\"rackname\",\"type\":\"string\"},{\"name\":\"status\",\"type\":\"string\"},{\"name\":\"error\",\"type\":\"string\"},{\"name\":\"counters\",\"type\":\"JhCounters\"},{\"name\":\"clockSplits\",\"type\":{\"type\":\"array\",\"items\":\"int\"}},{\"name\":\"cpuUsages\",\"type\":{\"type\":\"array\",\"items\":\"int\"}},{\"name\":\"vMemKbytes\",\"type\":{\"type\":\"array\",\"items\":\"int\"}},{\"name\":\"physMemKbytes\",\"type\":{\"type\":\"array\",\"items\":\"int\"}}]},{\"type\":\"record\",\"name\":\"TaskFailed\",\"fields\":[{\"name\":\"taskid\",\"type\":\"string\"},{\"name\":\"taskType\",\"type\":\"string\"},{\"name\":\"finishTime\",\"type\":\"long\"},{\"name\":\"error\",\"type\":\"string\"},{\"name\":\"failedDueToAttempt\",\"type\":[\"null\",\"string\"]},{\"name\":\"status\",\"type\":\"string\"},{\"name\":\"counters\",\"type\":\"JhCounters\"}]},{\"type\":\"record\",\"name\":\"TaskFinished\",\"fields\":[{\"name\":\"taskid\",\"type\":\"string\"},{\"name\":\"taskType\",\"type\":\"string\"},{\"name\":\"finishTime\",\"type\":\"long\"},{\"name\":\"status\",\"type\":\"string\"},{\"name\":\"counters\",\"type\":\"JhCounters\"},{\"name\":\"successfulAttemptId\",\"type\":\"string\"}]},{\"type\":\"record\",\"name\":\"TaskStarted\",\"fields\":[{\"name\":\"taskid\",\"type\":\"string\"},{\"name\":\"taskType\",\"type\":\"string\"},{\"name\":\"startTime\",\"type\":\"long\"},{\"name\":\"splitLocations\",\"type\":\"string\"}]},{\"type\":\"record\",\"name\":\"TaskUpdated\",\"fields\":[{\"name\":\"taskid\",\"type\":\"string\"},{\"name\":\"finishTime\",\"type\":\"long\"}]}]}]}");
/*  20:    */   @Deprecated
/*  21:    */   public EventType type;
/*  22:    */   @Deprecated
/*  23:    */   public Object event;
/*  24:    */   
/*  25:    */   public static Schema getClassSchema()
/*  26:    */   {
/*  27: 11 */     return SCHEMA$;
/*  28:    */   }
/*  29:    */   
/*  30:    */   public Event() {}
/*  31:    */   
/*  32:    */   public Event(EventType type, Object event)
/*  33:    */   {
/*  34: 24 */     this.type = type;
/*  35: 25 */     this.event = event;
/*  36:    */   }
/*  37:    */   
/*  38:    */   public Schema getSchema()
/*  39:    */   {
/*  40: 28 */     return SCHEMA$;
/*  41:    */   }
/*  42:    */   
/*  43:    */   public Object get(int field$)
/*  44:    */   {
/*  45: 31 */     switch (field$)
/*  46:    */     {
/*  47:    */     case 0: 
/*  48: 32 */       return this.type;
/*  49:    */     case 1: 
/*  50: 33 */       return this.event;
/*  51:    */     }
/*  52: 34 */     throw new AvroRuntimeException("Bad index");
/*  53:    */   }
/*  54:    */   
/*  55:    */   public void put(int field$, Object value$)
/*  56:    */   {
/*  57: 40 */     switch (field$)
/*  58:    */     {
/*  59:    */     case 0: 
/*  60: 41 */       this.type = ((EventType)value$); break;
/*  61:    */     case 1: 
/*  62: 42 */       this.event = value$; break;
/*  63:    */     default: 
/*  64: 43 */       throw new AvroRuntimeException("Bad index");
/*  65:    */     }
/*  66:    */   }
/*  67:    */   
/*  68:    */   public EventType getType()
/*  69:    */   {
/*  70: 51 */     return this.type;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public void setType(EventType value)
/*  74:    */   {
/*  75: 59 */     this.type = value;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public Object getEvent()
/*  79:    */   {
/*  80: 66 */     return this.event;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public void setEvent(Object value)
/*  84:    */   {
/*  85: 74 */     this.event = value;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public static Builder newBuilder()
/*  89:    */   {
/*  90: 79 */     return new Builder(null);
/*  91:    */   }
/*  92:    */   
/*  93:    */   public static Builder newBuilder(Builder other)
/*  94:    */   {
/*  95: 84 */     return new Builder(other, null);
/*  96:    */   }
/*  97:    */   
/*  98:    */   public static Builder newBuilder(Event other)
/*  99:    */   {
/* 100: 89 */     return new Builder(other, null);
/* 101:    */   }
/* 102:    */   
/* 103:    */   public static class Builder
/* 104:    */     extends SpecificRecordBuilderBase<Event>
/* 105:    */     implements RecordBuilder<Event>
/* 106:    */   {
/* 107:    */     private EventType type;
/* 108:    */     private Object event;
/* 109:    */     
/* 110:    */     private Builder()
/* 111:    */     {
/* 112:103 */       super();
/* 113:    */     }
/* 114:    */     
/* 115:    */     private Builder(Builder other)
/* 116:    */     {
/* 117:108 */       super();
/* 118:    */     }
/* 119:    */     
/* 120:    */     private Builder(Event other)
/* 121:    */     {
/* 122:113 */       super();
/* 123:114 */       if (isValidValue(fields()[0], other.type))
/* 124:    */       {
/* 125:115 */         this.type = ((EventType)data().deepCopy(fields()[0].schema(), other.type));
/* 126:116 */         fieldSetFlags()[0] = 1;
/* 127:    */       }
/* 128:118 */       if (isValidValue(fields()[1], other.event))
/* 129:    */       {
/* 130:119 */         this.event = data().deepCopy(fields()[1].schema(), other.event);
/* 131:120 */         fieldSetFlags()[1] = 1;
/* 132:    */       }
/* 133:    */     }
/* 134:    */     
/* 135:    */     public EventType getType()
/* 136:    */     {
/* 137:126 */       return this.type;
/* 138:    */     }
/* 139:    */     
/* 140:    */     public Builder setType(EventType value)
/* 141:    */     {
/* 142:131 */       validate(fields()[0], value);
/* 143:132 */       this.type = value;
/* 144:133 */       fieldSetFlags()[0] = 1;
/* 145:134 */       return this;
/* 146:    */     }
/* 147:    */     
/* 148:    */     public boolean hasType()
/* 149:    */     {
/* 150:139 */       return fieldSetFlags()[0];
/* 151:    */     }
/* 152:    */     
/* 153:    */     public Builder clearType()
/* 154:    */     {
/* 155:144 */       this.type = null;
/* 156:145 */       fieldSetFlags()[0] = 0;
/* 157:146 */       return this;
/* 158:    */     }
/* 159:    */     
/* 160:    */     public Object getEvent()
/* 161:    */     {
/* 162:151 */       return this.event;
/* 163:    */     }
/* 164:    */     
/* 165:    */     public Builder setEvent(Object value)
/* 166:    */     {
/* 167:156 */       validate(fields()[1], value);
/* 168:157 */       this.event = value;
/* 169:158 */       fieldSetFlags()[1] = 1;
/* 170:159 */       return this;
/* 171:    */     }
/* 172:    */     
/* 173:    */     public boolean hasEvent()
/* 174:    */     {
/* 175:164 */       return fieldSetFlags()[1];
/* 176:    */     }
/* 177:    */     
/* 178:    */     public Builder clearEvent()
/* 179:    */     {
/* 180:169 */       this.event = null;
/* 181:170 */       fieldSetFlags()[1] = 0;
/* 182:171 */       return this;
/* 183:    */     }
/* 184:    */     
/* 185:    */     public Event build()
/* 186:    */     {
/* 187:    */       try
/* 188:    */       {
/* 189:177 */         Event record = new Event();
/* 190:178 */         record.type = (fieldSetFlags()[0] != 0 ? this.type : (EventType)defaultValue(fields()[0]));
/* 191:179 */         record.event = (fieldSetFlags()[1] != 0 ? this.event : defaultValue(fields()[1]));
/* 192:180 */         return record;
/* 193:    */       }
/* 194:    */       catch (Exception e)
/* 195:    */       {
/* 196:182 */         throw new AvroRuntimeException(e);
/* 197:    */       }
/* 198:    */     }
/* 199:    */   }
/* 200:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.jobhistory.Event
 * JD-Core Version:    0.7.0.1
 */