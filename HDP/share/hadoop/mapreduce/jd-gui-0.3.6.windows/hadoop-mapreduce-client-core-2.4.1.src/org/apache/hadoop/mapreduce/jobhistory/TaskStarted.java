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
/*  15:    */ public class TaskStarted
/*  16:    */   extends SpecificRecordBase
/*  17:    */   implements SpecificRecord
/*  18:    */ {
/*  19: 10 */   public static final Schema SCHEMA$ = new Schema.Parser().parse("{\"type\":\"record\",\"name\":\"TaskStarted\",\"namespace\":\"org.apache.hadoop.mapreduce.jobhistory\",\"fields\":[{\"name\":\"taskid\",\"type\":\"string\"},{\"name\":\"taskType\",\"type\":\"string\"},{\"name\":\"startTime\",\"type\":\"long\"},{\"name\":\"splitLocations\",\"type\":\"string\"}]}");
/*  20:    */   @Deprecated
/*  21:    */   public CharSequence taskid;
/*  22:    */   @Deprecated
/*  23:    */   public CharSequence taskType;
/*  24:    */   @Deprecated
/*  25:    */   public long startTime;
/*  26:    */   @Deprecated
/*  27:    */   public CharSequence splitLocations;
/*  28:    */   
/*  29:    */   public static Schema getClassSchema()
/*  30:    */   {
/*  31: 11 */     return SCHEMA$;
/*  32:    */   }
/*  33:    */   
/*  34:    */   public TaskStarted() {}
/*  35:    */   
/*  36:    */   public TaskStarted(CharSequence taskid, CharSequence taskType, Long startTime, CharSequence splitLocations)
/*  37:    */   {
/*  38: 26 */     this.taskid = taskid;
/*  39: 27 */     this.taskType = taskType;
/*  40: 28 */     this.startTime = startTime.longValue();
/*  41: 29 */     this.splitLocations = splitLocations;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public Schema getSchema()
/*  45:    */   {
/*  46: 32 */     return SCHEMA$;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public Object get(int field$)
/*  50:    */   {
/*  51: 35 */     switch (field$)
/*  52:    */     {
/*  53:    */     case 0: 
/*  54: 36 */       return this.taskid;
/*  55:    */     case 1: 
/*  56: 37 */       return this.taskType;
/*  57:    */     case 2: 
/*  58: 38 */       return Long.valueOf(this.startTime);
/*  59:    */     case 3: 
/*  60: 39 */       return this.splitLocations;
/*  61:    */     }
/*  62: 40 */     throw new AvroRuntimeException("Bad index");
/*  63:    */   }
/*  64:    */   
/*  65:    */   public void put(int field$, Object value$)
/*  66:    */   {
/*  67: 46 */     switch (field$)
/*  68:    */     {
/*  69:    */     case 0: 
/*  70: 47 */       this.taskid = ((CharSequence)value$); break;
/*  71:    */     case 1: 
/*  72: 48 */       this.taskType = ((CharSequence)value$); break;
/*  73:    */     case 2: 
/*  74: 49 */       this.startTime = ((Long)value$).longValue(); break;
/*  75:    */     case 3: 
/*  76: 50 */       this.splitLocations = ((CharSequence)value$); break;
/*  77:    */     default: 
/*  78: 51 */       throw new AvroRuntimeException("Bad index");
/*  79:    */     }
/*  80:    */   }
/*  81:    */   
/*  82:    */   public CharSequence getTaskid()
/*  83:    */   {
/*  84: 59 */     return this.taskid;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public void setTaskid(CharSequence value)
/*  88:    */   {
/*  89: 67 */     this.taskid = value;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public CharSequence getTaskType()
/*  93:    */   {
/*  94: 74 */     return this.taskType;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public void setTaskType(CharSequence value)
/*  98:    */   {
/*  99: 82 */     this.taskType = value;
/* 100:    */   }
/* 101:    */   
/* 102:    */   public Long getStartTime()
/* 103:    */   {
/* 104: 89 */     return Long.valueOf(this.startTime);
/* 105:    */   }
/* 106:    */   
/* 107:    */   public void setStartTime(Long value)
/* 108:    */   {
/* 109: 97 */     this.startTime = value.longValue();
/* 110:    */   }
/* 111:    */   
/* 112:    */   public CharSequence getSplitLocations()
/* 113:    */   {
/* 114:104 */     return this.splitLocations;
/* 115:    */   }
/* 116:    */   
/* 117:    */   public void setSplitLocations(CharSequence value)
/* 118:    */   {
/* 119:112 */     this.splitLocations = value;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public static Builder newBuilder()
/* 123:    */   {
/* 124:117 */     return new Builder(null);
/* 125:    */   }
/* 126:    */   
/* 127:    */   public static Builder newBuilder(Builder other)
/* 128:    */   {
/* 129:122 */     return new Builder(other, null);
/* 130:    */   }
/* 131:    */   
/* 132:    */   public static Builder newBuilder(TaskStarted other)
/* 133:    */   {
/* 134:127 */     return new Builder(other, null);
/* 135:    */   }
/* 136:    */   
/* 137:    */   public static class Builder
/* 138:    */     extends SpecificRecordBuilderBase<TaskStarted>
/* 139:    */     implements RecordBuilder<TaskStarted>
/* 140:    */   {
/* 141:    */     private CharSequence taskid;
/* 142:    */     private CharSequence taskType;
/* 143:    */     private long startTime;
/* 144:    */     private CharSequence splitLocations;
/* 145:    */     
/* 146:    */     private Builder()
/* 147:    */     {
/* 148:143 */       super();
/* 149:    */     }
/* 150:    */     
/* 151:    */     private Builder(Builder other)
/* 152:    */     {
/* 153:148 */       super();
/* 154:    */     }
/* 155:    */     
/* 156:    */     private Builder(TaskStarted other)
/* 157:    */     {
/* 158:153 */       super();
/* 159:154 */       if (isValidValue(fields()[0], other.taskid))
/* 160:    */       {
/* 161:155 */         this.taskid = ((CharSequence)data().deepCopy(fields()[0].schema(), other.taskid));
/* 162:156 */         fieldSetFlags()[0] = 1;
/* 163:    */       }
/* 164:158 */       if (isValidValue(fields()[1], other.taskType))
/* 165:    */       {
/* 166:159 */         this.taskType = ((CharSequence)data().deepCopy(fields()[1].schema(), other.taskType));
/* 167:160 */         fieldSetFlags()[1] = 1;
/* 168:    */       }
/* 169:162 */       if (isValidValue(fields()[2], Long.valueOf(other.startTime)))
/* 170:    */       {
/* 171:163 */         this.startTime = ((Long)data().deepCopy(fields()[2].schema(), Long.valueOf(other.startTime))).longValue();
/* 172:164 */         fieldSetFlags()[2] = 1;
/* 173:    */       }
/* 174:166 */       if (isValidValue(fields()[3], other.splitLocations))
/* 175:    */       {
/* 176:167 */         this.splitLocations = ((CharSequence)data().deepCopy(fields()[3].schema(), other.splitLocations));
/* 177:168 */         fieldSetFlags()[3] = 1;
/* 178:    */       }
/* 179:    */     }
/* 180:    */     
/* 181:    */     public CharSequence getTaskid()
/* 182:    */     {
/* 183:174 */       return this.taskid;
/* 184:    */     }
/* 185:    */     
/* 186:    */     public Builder setTaskid(CharSequence value)
/* 187:    */     {
/* 188:179 */       validate(fields()[0], value);
/* 189:180 */       this.taskid = value;
/* 190:181 */       fieldSetFlags()[0] = 1;
/* 191:182 */       return this;
/* 192:    */     }
/* 193:    */     
/* 194:    */     public boolean hasTaskid()
/* 195:    */     {
/* 196:187 */       return fieldSetFlags()[0];
/* 197:    */     }
/* 198:    */     
/* 199:    */     public Builder clearTaskid()
/* 200:    */     {
/* 201:192 */       this.taskid = null;
/* 202:193 */       fieldSetFlags()[0] = 0;
/* 203:194 */       return this;
/* 204:    */     }
/* 205:    */     
/* 206:    */     public CharSequence getTaskType()
/* 207:    */     {
/* 208:199 */       return this.taskType;
/* 209:    */     }
/* 210:    */     
/* 211:    */     public Builder setTaskType(CharSequence value)
/* 212:    */     {
/* 213:204 */       validate(fields()[1], value);
/* 214:205 */       this.taskType = value;
/* 215:206 */       fieldSetFlags()[1] = 1;
/* 216:207 */       return this;
/* 217:    */     }
/* 218:    */     
/* 219:    */     public boolean hasTaskType()
/* 220:    */     {
/* 221:212 */       return fieldSetFlags()[1];
/* 222:    */     }
/* 223:    */     
/* 224:    */     public Builder clearTaskType()
/* 225:    */     {
/* 226:217 */       this.taskType = null;
/* 227:218 */       fieldSetFlags()[1] = 0;
/* 228:219 */       return this;
/* 229:    */     }
/* 230:    */     
/* 231:    */     public Long getStartTime()
/* 232:    */     {
/* 233:224 */       return Long.valueOf(this.startTime);
/* 234:    */     }
/* 235:    */     
/* 236:    */     public Builder setStartTime(long value)
/* 237:    */     {
/* 238:229 */       validate(fields()[2], Long.valueOf(value));
/* 239:230 */       this.startTime = value;
/* 240:231 */       fieldSetFlags()[2] = 1;
/* 241:232 */       return this;
/* 242:    */     }
/* 243:    */     
/* 244:    */     public boolean hasStartTime()
/* 245:    */     {
/* 246:237 */       return fieldSetFlags()[2];
/* 247:    */     }
/* 248:    */     
/* 249:    */     public Builder clearStartTime()
/* 250:    */     {
/* 251:242 */       fieldSetFlags()[2] = 0;
/* 252:243 */       return this;
/* 253:    */     }
/* 254:    */     
/* 255:    */     public CharSequence getSplitLocations()
/* 256:    */     {
/* 257:248 */       return this.splitLocations;
/* 258:    */     }
/* 259:    */     
/* 260:    */     public Builder setSplitLocations(CharSequence value)
/* 261:    */     {
/* 262:253 */       validate(fields()[3], value);
/* 263:254 */       this.splitLocations = value;
/* 264:255 */       fieldSetFlags()[3] = 1;
/* 265:256 */       return this;
/* 266:    */     }
/* 267:    */     
/* 268:    */     public boolean hasSplitLocations()
/* 269:    */     {
/* 270:261 */       return fieldSetFlags()[3];
/* 271:    */     }
/* 272:    */     
/* 273:    */     public Builder clearSplitLocations()
/* 274:    */     {
/* 275:266 */       this.splitLocations = null;
/* 276:267 */       fieldSetFlags()[3] = 0;
/* 277:268 */       return this;
/* 278:    */     }
/* 279:    */     
/* 280:    */     public TaskStarted build()
/* 281:    */     {
/* 282:    */       try
/* 283:    */       {
/* 284:274 */         TaskStarted record = new TaskStarted();
/* 285:275 */         record.taskid = (fieldSetFlags()[0] != 0 ? this.taskid : (CharSequence)defaultValue(fields()[0]));
/* 286:276 */         record.taskType = (fieldSetFlags()[1] != 0 ? this.taskType : (CharSequence)defaultValue(fields()[1]));
/* 287:277 */         record.startTime = (fieldSetFlags()[2] != 0 ? this.startTime : ((Long)defaultValue(fields()[2])).longValue());
/* 288:278 */         record.splitLocations = (fieldSetFlags()[3] != 0 ? this.splitLocations : (CharSequence)defaultValue(fields()[3]));
/* 289:279 */         return record;
/* 290:    */       }
/* 291:    */       catch (Exception e)
/* 292:    */       {
/* 293:281 */         throw new AvroRuntimeException(e);
/* 294:    */       }
/* 295:    */     }
/* 296:    */   }
/* 297:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.jobhistory.TaskStarted
 * JD-Core Version:    0.7.0.1
 */