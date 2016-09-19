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
/*  15:    */ public class TaskFinished
/*  16:    */   extends SpecificRecordBase
/*  17:    */   implements SpecificRecord
/*  18:    */ {
/*  19: 10 */   public static final Schema SCHEMA$ = new Schema.Parser().parse("{\"type\":\"record\",\"name\":\"TaskFinished\",\"namespace\":\"org.apache.hadoop.mapreduce.jobhistory\",\"fields\":[{\"name\":\"taskid\",\"type\":\"string\"},{\"name\":\"taskType\",\"type\":\"string\"},{\"name\":\"finishTime\",\"type\":\"long\"},{\"name\":\"status\",\"type\":\"string\"},{\"name\":\"counters\",\"type\":{\"type\":\"record\",\"name\":\"JhCounters\",\"fields\":[{\"name\":\"name\",\"type\":\"string\"},{\"name\":\"groups\",\"type\":{\"type\":\"array\",\"items\":{\"type\":\"record\",\"name\":\"JhCounterGroup\",\"fields\":[{\"name\":\"name\",\"type\":\"string\"},{\"name\":\"displayName\",\"type\":\"string\"},{\"name\":\"counts\",\"type\":{\"type\":\"array\",\"items\":{\"type\":\"record\",\"name\":\"JhCounter\",\"fields\":[{\"name\":\"name\",\"type\":\"string\"},{\"name\":\"displayName\",\"type\":\"string\"},{\"name\":\"value\",\"type\":\"long\"}]}}}]}}}]}},{\"name\":\"successfulAttemptId\",\"type\":\"string\"}]}");
/*  20:    */   @Deprecated
/*  21:    */   public CharSequence taskid;
/*  22:    */   @Deprecated
/*  23:    */   public CharSequence taskType;
/*  24:    */   @Deprecated
/*  25:    */   public long finishTime;
/*  26:    */   @Deprecated
/*  27:    */   public CharSequence status;
/*  28:    */   @Deprecated
/*  29:    */   public JhCounters counters;
/*  30:    */   @Deprecated
/*  31:    */   public CharSequence successfulAttemptId;
/*  32:    */   
/*  33:    */   public static Schema getClassSchema()
/*  34:    */   {
/*  35: 11 */     return SCHEMA$;
/*  36:    */   }
/*  37:    */   
/*  38:    */   public TaskFinished() {}
/*  39:    */   
/*  40:    */   public TaskFinished(CharSequence taskid, CharSequence taskType, Long finishTime, CharSequence status, JhCounters counters, CharSequence successfulAttemptId)
/*  41:    */   {
/*  42: 28 */     this.taskid = taskid;
/*  43: 29 */     this.taskType = taskType;
/*  44: 30 */     this.finishTime = finishTime.longValue();
/*  45: 31 */     this.status = status;
/*  46: 32 */     this.counters = counters;
/*  47: 33 */     this.successfulAttemptId = successfulAttemptId;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public Schema getSchema()
/*  51:    */   {
/*  52: 36 */     return SCHEMA$;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public Object get(int field$)
/*  56:    */   {
/*  57: 39 */     switch (field$)
/*  58:    */     {
/*  59:    */     case 0: 
/*  60: 40 */       return this.taskid;
/*  61:    */     case 1: 
/*  62: 41 */       return this.taskType;
/*  63:    */     case 2: 
/*  64: 42 */       return Long.valueOf(this.finishTime);
/*  65:    */     case 3: 
/*  66: 43 */       return this.status;
/*  67:    */     case 4: 
/*  68: 44 */       return this.counters;
/*  69:    */     case 5: 
/*  70: 45 */       return this.successfulAttemptId;
/*  71:    */     }
/*  72: 46 */     throw new AvroRuntimeException("Bad index");
/*  73:    */   }
/*  74:    */   
/*  75:    */   public void put(int field$, Object value$)
/*  76:    */   {
/*  77: 52 */     switch (field$)
/*  78:    */     {
/*  79:    */     case 0: 
/*  80: 53 */       this.taskid = ((CharSequence)value$); break;
/*  81:    */     case 1: 
/*  82: 54 */       this.taskType = ((CharSequence)value$); break;
/*  83:    */     case 2: 
/*  84: 55 */       this.finishTime = ((Long)value$).longValue(); break;
/*  85:    */     case 3: 
/*  86: 56 */       this.status = ((CharSequence)value$); break;
/*  87:    */     case 4: 
/*  88: 57 */       this.counters = ((JhCounters)value$); break;
/*  89:    */     case 5: 
/*  90: 58 */       this.successfulAttemptId = ((CharSequence)value$); break;
/*  91:    */     default: 
/*  92: 59 */       throw new AvroRuntimeException("Bad index");
/*  93:    */     }
/*  94:    */   }
/*  95:    */   
/*  96:    */   public CharSequence getTaskid()
/*  97:    */   {
/*  98: 67 */     return this.taskid;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public void setTaskid(CharSequence value)
/* 102:    */   {
/* 103: 75 */     this.taskid = value;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public CharSequence getTaskType()
/* 107:    */   {
/* 108: 82 */     return this.taskType;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public void setTaskType(CharSequence value)
/* 112:    */   {
/* 113: 90 */     this.taskType = value;
/* 114:    */   }
/* 115:    */   
/* 116:    */   public Long getFinishTime()
/* 117:    */   {
/* 118: 97 */     return Long.valueOf(this.finishTime);
/* 119:    */   }
/* 120:    */   
/* 121:    */   public void setFinishTime(Long value)
/* 122:    */   {
/* 123:105 */     this.finishTime = value.longValue();
/* 124:    */   }
/* 125:    */   
/* 126:    */   public CharSequence getStatus()
/* 127:    */   {
/* 128:112 */     return this.status;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public void setStatus(CharSequence value)
/* 132:    */   {
/* 133:120 */     this.status = value;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public JhCounters getCounters()
/* 137:    */   {
/* 138:127 */     return this.counters;
/* 139:    */   }
/* 140:    */   
/* 141:    */   public void setCounters(JhCounters value)
/* 142:    */   {
/* 143:135 */     this.counters = value;
/* 144:    */   }
/* 145:    */   
/* 146:    */   public CharSequence getSuccessfulAttemptId()
/* 147:    */   {
/* 148:142 */     return this.successfulAttemptId;
/* 149:    */   }
/* 150:    */   
/* 151:    */   public void setSuccessfulAttemptId(CharSequence value)
/* 152:    */   {
/* 153:150 */     this.successfulAttemptId = value;
/* 154:    */   }
/* 155:    */   
/* 156:    */   public static Builder newBuilder()
/* 157:    */   {
/* 158:155 */     return new Builder(null);
/* 159:    */   }
/* 160:    */   
/* 161:    */   public static Builder newBuilder(Builder other)
/* 162:    */   {
/* 163:160 */     return new Builder(other, null);
/* 164:    */   }
/* 165:    */   
/* 166:    */   public static Builder newBuilder(TaskFinished other)
/* 167:    */   {
/* 168:165 */     return new Builder(other, null);
/* 169:    */   }
/* 170:    */   
/* 171:    */   public static class Builder
/* 172:    */     extends SpecificRecordBuilderBase<TaskFinished>
/* 173:    */     implements RecordBuilder<TaskFinished>
/* 174:    */   {
/* 175:    */     private CharSequence taskid;
/* 176:    */     private CharSequence taskType;
/* 177:    */     private long finishTime;
/* 178:    */     private CharSequence status;
/* 179:    */     private JhCounters counters;
/* 180:    */     private CharSequence successfulAttemptId;
/* 181:    */     
/* 182:    */     private Builder()
/* 183:    */     {
/* 184:183 */       super();
/* 185:    */     }
/* 186:    */     
/* 187:    */     private Builder(Builder other)
/* 188:    */     {
/* 189:188 */       super();
/* 190:    */     }
/* 191:    */     
/* 192:    */     private Builder(TaskFinished other)
/* 193:    */     {
/* 194:193 */       super();
/* 195:194 */       if (isValidValue(fields()[0], other.taskid))
/* 196:    */       {
/* 197:195 */         this.taskid = ((CharSequence)data().deepCopy(fields()[0].schema(), other.taskid));
/* 198:196 */         fieldSetFlags()[0] = 1;
/* 199:    */       }
/* 200:198 */       if (isValidValue(fields()[1], other.taskType))
/* 201:    */       {
/* 202:199 */         this.taskType = ((CharSequence)data().deepCopy(fields()[1].schema(), other.taskType));
/* 203:200 */         fieldSetFlags()[1] = 1;
/* 204:    */       }
/* 205:202 */       if (isValidValue(fields()[2], Long.valueOf(other.finishTime)))
/* 206:    */       {
/* 207:203 */         this.finishTime = ((Long)data().deepCopy(fields()[2].schema(), Long.valueOf(other.finishTime))).longValue();
/* 208:204 */         fieldSetFlags()[2] = 1;
/* 209:    */       }
/* 210:206 */       if (isValidValue(fields()[3], other.status))
/* 211:    */       {
/* 212:207 */         this.status = ((CharSequence)data().deepCopy(fields()[3].schema(), other.status));
/* 213:208 */         fieldSetFlags()[3] = 1;
/* 214:    */       }
/* 215:210 */       if (isValidValue(fields()[4], other.counters))
/* 216:    */       {
/* 217:211 */         this.counters = ((JhCounters)data().deepCopy(fields()[4].schema(), other.counters));
/* 218:212 */         fieldSetFlags()[4] = 1;
/* 219:    */       }
/* 220:214 */       if (isValidValue(fields()[5], other.successfulAttemptId))
/* 221:    */       {
/* 222:215 */         this.successfulAttemptId = ((CharSequence)data().deepCopy(fields()[5].schema(), other.successfulAttemptId));
/* 223:216 */         fieldSetFlags()[5] = 1;
/* 224:    */       }
/* 225:    */     }
/* 226:    */     
/* 227:    */     public CharSequence getTaskid()
/* 228:    */     {
/* 229:222 */       return this.taskid;
/* 230:    */     }
/* 231:    */     
/* 232:    */     public Builder setTaskid(CharSequence value)
/* 233:    */     {
/* 234:227 */       validate(fields()[0], value);
/* 235:228 */       this.taskid = value;
/* 236:229 */       fieldSetFlags()[0] = 1;
/* 237:230 */       return this;
/* 238:    */     }
/* 239:    */     
/* 240:    */     public boolean hasTaskid()
/* 241:    */     {
/* 242:235 */       return fieldSetFlags()[0];
/* 243:    */     }
/* 244:    */     
/* 245:    */     public Builder clearTaskid()
/* 246:    */     {
/* 247:240 */       this.taskid = null;
/* 248:241 */       fieldSetFlags()[0] = 0;
/* 249:242 */       return this;
/* 250:    */     }
/* 251:    */     
/* 252:    */     public CharSequence getTaskType()
/* 253:    */     {
/* 254:247 */       return this.taskType;
/* 255:    */     }
/* 256:    */     
/* 257:    */     public Builder setTaskType(CharSequence value)
/* 258:    */     {
/* 259:252 */       validate(fields()[1], value);
/* 260:253 */       this.taskType = value;
/* 261:254 */       fieldSetFlags()[1] = 1;
/* 262:255 */       return this;
/* 263:    */     }
/* 264:    */     
/* 265:    */     public boolean hasTaskType()
/* 266:    */     {
/* 267:260 */       return fieldSetFlags()[1];
/* 268:    */     }
/* 269:    */     
/* 270:    */     public Builder clearTaskType()
/* 271:    */     {
/* 272:265 */       this.taskType = null;
/* 273:266 */       fieldSetFlags()[1] = 0;
/* 274:267 */       return this;
/* 275:    */     }
/* 276:    */     
/* 277:    */     public Long getFinishTime()
/* 278:    */     {
/* 279:272 */       return Long.valueOf(this.finishTime);
/* 280:    */     }
/* 281:    */     
/* 282:    */     public Builder setFinishTime(long value)
/* 283:    */     {
/* 284:277 */       validate(fields()[2], Long.valueOf(value));
/* 285:278 */       this.finishTime = value;
/* 286:279 */       fieldSetFlags()[2] = 1;
/* 287:280 */       return this;
/* 288:    */     }
/* 289:    */     
/* 290:    */     public boolean hasFinishTime()
/* 291:    */     {
/* 292:285 */       return fieldSetFlags()[2];
/* 293:    */     }
/* 294:    */     
/* 295:    */     public Builder clearFinishTime()
/* 296:    */     {
/* 297:290 */       fieldSetFlags()[2] = 0;
/* 298:291 */       return this;
/* 299:    */     }
/* 300:    */     
/* 301:    */     public CharSequence getStatus()
/* 302:    */     {
/* 303:296 */       return this.status;
/* 304:    */     }
/* 305:    */     
/* 306:    */     public Builder setStatus(CharSequence value)
/* 307:    */     {
/* 308:301 */       validate(fields()[3], value);
/* 309:302 */       this.status = value;
/* 310:303 */       fieldSetFlags()[3] = 1;
/* 311:304 */       return this;
/* 312:    */     }
/* 313:    */     
/* 314:    */     public boolean hasStatus()
/* 315:    */     {
/* 316:309 */       return fieldSetFlags()[3];
/* 317:    */     }
/* 318:    */     
/* 319:    */     public Builder clearStatus()
/* 320:    */     {
/* 321:314 */       this.status = null;
/* 322:315 */       fieldSetFlags()[3] = 0;
/* 323:316 */       return this;
/* 324:    */     }
/* 325:    */     
/* 326:    */     public JhCounters getCounters()
/* 327:    */     {
/* 328:321 */       return this.counters;
/* 329:    */     }
/* 330:    */     
/* 331:    */     public Builder setCounters(JhCounters value)
/* 332:    */     {
/* 333:326 */       validate(fields()[4], value);
/* 334:327 */       this.counters = value;
/* 335:328 */       fieldSetFlags()[4] = 1;
/* 336:329 */       return this;
/* 337:    */     }
/* 338:    */     
/* 339:    */     public boolean hasCounters()
/* 340:    */     {
/* 341:334 */       return fieldSetFlags()[4];
/* 342:    */     }
/* 343:    */     
/* 344:    */     public Builder clearCounters()
/* 345:    */     {
/* 346:339 */       this.counters = null;
/* 347:340 */       fieldSetFlags()[4] = 0;
/* 348:341 */       return this;
/* 349:    */     }
/* 350:    */     
/* 351:    */     public CharSequence getSuccessfulAttemptId()
/* 352:    */     {
/* 353:346 */       return this.successfulAttemptId;
/* 354:    */     }
/* 355:    */     
/* 356:    */     public Builder setSuccessfulAttemptId(CharSequence value)
/* 357:    */     {
/* 358:351 */       validate(fields()[5], value);
/* 359:352 */       this.successfulAttemptId = value;
/* 360:353 */       fieldSetFlags()[5] = 1;
/* 361:354 */       return this;
/* 362:    */     }
/* 363:    */     
/* 364:    */     public boolean hasSuccessfulAttemptId()
/* 365:    */     {
/* 366:359 */       return fieldSetFlags()[5];
/* 367:    */     }
/* 368:    */     
/* 369:    */     public Builder clearSuccessfulAttemptId()
/* 370:    */     {
/* 371:364 */       this.successfulAttemptId = null;
/* 372:365 */       fieldSetFlags()[5] = 0;
/* 373:366 */       return this;
/* 374:    */     }
/* 375:    */     
/* 376:    */     public TaskFinished build()
/* 377:    */     {
/* 378:    */       try
/* 379:    */       {
/* 380:372 */         TaskFinished record = new TaskFinished();
/* 381:373 */         record.taskid = (fieldSetFlags()[0] != 0 ? this.taskid : (CharSequence)defaultValue(fields()[0]));
/* 382:374 */         record.taskType = (fieldSetFlags()[1] != 0 ? this.taskType : (CharSequence)defaultValue(fields()[1]));
/* 383:375 */         record.finishTime = (fieldSetFlags()[2] != 0 ? this.finishTime : ((Long)defaultValue(fields()[2])).longValue());
/* 384:376 */         record.status = (fieldSetFlags()[3] != 0 ? this.status : (CharSequence)defaultValue(fields()[3]));
/* 385:377 */         record.counters = (fieldSetFlags()[4] != 0 ? this.counters : (JhCounters)defaultValue(fields()[4]));
/* 386:378 */         record.successfulAttemptId = (fieldSetFlags()[5] != 0 ? this.successfulAttemptId : (CharSequence)defaultValue(fields()[5]));
/* 387:379 */         return record;
/* 388:    */       }
/* 389:    */       catch (Exception e)
/* 390:    */       {
/* 391:381 */         throw new AvroRuntimeException(e);
/* 392:    */       }
/* 393:    */     }
/* 394:    */   }
/* 395:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.jobhistory.TaskFinished
 * JD-Core Version:    0.7.0.1
 */