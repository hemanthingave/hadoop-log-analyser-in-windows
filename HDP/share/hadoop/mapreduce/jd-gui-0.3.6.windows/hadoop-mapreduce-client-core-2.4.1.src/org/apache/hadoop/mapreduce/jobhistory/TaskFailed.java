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
/*  15:    */ public class TaskFailed
/*  16:    */   extends SpecificRecordBase
/*  17:    */   implements SpecificRecord
/*  18:    */ {
/*  19: 10 */   public static final Schema SCHEMA$ = new Schema.Parser().parse("{\"type\":\"record\",\"name\":\"TaskFailed\",\"namespace\":\"org.apache.hadoop.mapreduce.jobhistory\",\"fields\":[{\"name\":\"taskid\",\"type\":\"string\"},{\"name\":\"taskType\",\"type\":\"string\"},{\"name\":\"finishTime\",\"type\":\"long\"},{\"name\":\"error\",\"type\":\"string\"},{\"name\":\"failedDueToAttempt\",\"type\":[\"null\",\"string\"]},{\"name\":\"status\",\"type\":\"string\"},{\"name\":\"counters\",\"type\":{\"type\":\"record\",\"name\":\"JhCounters\",\"fields\":[{\"name\":\"name\",\"type\":\"string\"},{\"name\":\"groups\",\"type\":{\"type\":\"array\",\"items\":{\"type\":\"record\",\"name\":\"JhCounterGroup\",\"fields\":[{\"name\":\"name\",\"type\":\"string\"},{\"name\":\"displayName\",\"type\":\"string\"},{\"name\":\"counts\",\"type\":{\"type\":\"array\",\"items\":{\"type\":\"record\",\"name\":\"JhCounter\",\"fields\":[{\"name\":\"name\",\"type\":\"string\"},{\"name\":\"displayName\",\"type\":\"string\"},{\"name\":\"value\",\"type\":\"long\"}]}}}]}}}]}}]}");
/*  20:    */   @Deprecated
/*  21:    */   public CharSequence taskid;
/*  22:    */   @Deprecated
/*  23:    */   public CharSequence taskType;
/*  24:    */   @Deprecated
/*  25:    */   public long finishTime;
/*  26:    */   @Deprecated
/*  27:    */   public CharSequence error;
/*  28:    */   @Deprecated
/*  29:    */   public CharSequence failedDueToAttempt;
/*  30:    */   @Deprecated
/*  31:    */   public CharSequence status;
/*  32:    */   @Deprecated
/*  33:    */   public JhCounters counters;
/*  34:    */   
/*  35:    */   public static Schema getClassSchema()
/*  36:    */   {
/*  37: 11 */     return SCHEMA$;
/*  38:    */   }
/*  39:    */   
/*  40:    */   public TaskFailed() {}
/*  41:    */   
/*  42:    */   public TaskFailed(CharSequence taskid, CharSequence taskType, Long finishTime, CharSequence error, CharSequence failedDueToAttempt, CharSequence status, JhCounters counters)
/*  43:    */   {
/*  44: 29 */     this.taskid = taskid;
/*  45: 30 */     this.taskType = taskType;
/*  46: 31 */     this.finishTime = finishTime.longValue();
/*  47: 32 */     this.error = error;
/*  48: 33 */     this.failedDueToAttempt = failedDueToAttempt;
/*  49: 34 */     this.status = status;
/*  50: 35 */     this.counters = counters;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public Schema getSchema()
/*  54:    */   {
/*  55: 38 */     return SCHEMA$;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public Object get(int field$)
/*  59:    */   {
/*  60: 41 */     switch (field$)
/*  61:    */     {
/*  62:    */     case 0: 
/*  63: 42 */       return this.taskid;
/*  64:    */     case 1: 
/*  65: 43 */       return this.taskType;
/*  66:    */     case 2: 
/*  67: 44 */       return Long.valueOf(this.finishTime);
/*  68:    */     case 3: 
/*  69: 45 */       return this.error;
/*  70:    */     case 4: 
/*  71: 46 */       return this.failedDueToAttempt;
/*  72:    */     case 5: 
/*  73: 47 */       return this.status;
/*  74:    */     case 6: 
/*  75: 48 */       return this.counters;
/*  76:    */     }
/*  77: 49 */     throw new AvroRuntimeException("Bad index");
/*  78:    */   }
/*  79:    */   
/*  80:    */   public void put(int field$, Object value$)
/*  81:    */   {
/*  82: 55 */     switch (field$)
/*  83:    */     {
/*  84:    */     case 0: 
/*  85: 56 */       this.taskid = ((CharSequence)value$); break;
/*  86:    */     case 1: 
/*  87: 57 */       this.taskType = ((CharSequence)value$); break;
/*  88:    */     case 2: 
/*  89: 58 */       this.finishTime = ((Long)value$).longValue(); break;
/*  90:    */     case 3: 
/*  91: 59 */       this.error = ((CharSequence)value$); break;
/*  92:    */     case 4: 
/*  93: 60 */       this.failedDueToAttempt = ((CharSequence)value$); break;
/*  94:    */     case 5: 
/*  95: 61 */       this.status = ((CharSequence)value$); break;
/*  96:    */     case 6: 
/*  97: 62 */       this.counters = ((JhCounters)value$); break;
/*  98:    */     default: 
/*  99: 63 */       throw new AvroRuntimeException("Bad index");
/* 100:    */     }
/* 101:    */   }
/* 102:    */   
/* 103:    */   public CharSequence getTaskid()
/* 104:    */   {
/* 105: 71 */     return this.taskid;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public void setTaskid(CharSequence value)
/* 109:    */   {
/* 110: 79 */     this.taskid = value;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public CharSequence getTaskType()
/* 114:    */   {
/* 115: 86 */     return this.taskType;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public void setTaskType(CharSequence value)
/* 119:    */   {
/* 120: 94 */     this.taskType = value;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public Long getFinishTime()
/* 124:    */   {
/* 125:101 */     return Long.valueOf(this.finishTime);
/* 126:    */   }
/* 127:    */   
/* 128:    */   public void setFinishTime(Long value)
/* 129:    */   {
/* 130:109 */     this.finishTime = value.longValue();
/* 131:    */   }
/* 132:    */   
/* 133:    */   public CharSequence getError()
/* 134:    */   {
/* 135:116 */     return this.error;
/* 136:    */   }
/* 137:    */   
/* 138:    */   public void setError(CharSequence value)
/* 139:    */   {
/* 140:124 */     this.error = value;
/* 141:    */   }
/* 142:    */   
/* 143:    */   public CharSequence getFailedDueToAttempt()
/* 144:    */   {
/* 145:131 */     return this.failedDueToAttempt;
/* 146:    */   }
/* 147:    */   
/* 148:    */   public void setFailedDueToAttempt(CharSequence value)
/* 149:    */   {
/* 150:139 */     this.failedDueToAttempt = value;
/* 151:    */   }
/* 152:    */   
/* 153:    */   public CharSequence getStatus()
/* 154:    */   {
/* 155:146 */     return this.status;
/* 156:    */   }
/* 157:    */   
/* 158:    */   public void setStatus(CharSequence value)
/* 159:    */   {
/* 160:154 */     this.status = value;
/* 161:    */   }
/* 162:    */   
/* 163:    */   public JhCounters getCounters()
/* 164:    */   {
/* 165:161 */     return this.counters;
/* 166:    */   }
/* 167:    */   
/* 168:    */   public void setCounters(JhCounters value)
/* 169:    */   {
/* 170:169 */     this.counters = value;
/* 171:    */   }
/* 172:    */   
/* 173:    */   public static Builder newBuilder()
/* 174:    */   {
/* 175:174 */     return new Builder(null);
/* 176:    */   }
/* 177:    */   
/* 178:    */   public static Builder newBuilder(Builder other)
/* 179:    */   {
/* 180:179 */     return new Builder(other, null);
/* 181:    */   }
/* 182:    */   
/* 183:    */   public static Builder newBuilder(TaskFailed other)
/* 184:    */   {
/* 185:184 */     return new Builder(other, null);
/* 186:    */   }
/* 187:    */   
/* 188:    */   public static class Builder
/* 189:    */     extends SpecificRecordBuilderBase<TaskFailed>
/* 190:    */     implements RecordBuilder<TaskFailed>
/* 191:    */   {
/* 192:    */     private CharSequence taskid;
/* 193:    */     private CharSequence taskType;
/* 194:    */     private long finishTime;
/* 195:    */     private CharSequence error;
/* 196:    */     private CharSequence failedDueToAttempt;
/* 197:    */     private CharSequence status;
/* 198:    */     private JhCounters counters;
/* 199:    */     
/* 200:    */     private Builder()
/* 201:    */     {
/* 202:203 */       super();
/* 203:    */     }
/* 204:    */     
/* 205:    */     private Builder(Builder other)
/* 206:    */     {
/* 207:208 */       super();
/* 208:    */     }
/* 209:    */     
/* 210:    */     private Builder(TaskFailed other)
/* 211:    */     {
/* 212:213 */       super();
/* 213:214 */       if (isValidValue(fields()[0], other.taskid))
/* 214:    */       {
/* 215:215 */         this.taskid = ((CharSequence)data().deepCopy(fields()[0].schema(), other.taskid));
/* 216:216 */         fieldSetFlags()[0] = 1;
/* 217:    */       }
/* 218:218 */       if (isValidValue(fields()[1], other.taskType))
/* 219:    */       {
/* 220:219 */         this.taskType = ((CharSequence)data().deepCopy(fields()[1].schema(), other.taskType));
/* 221:220 */         fieldSetFlags()[1] = 1;
/* 222:    */       }
/* 223:222 */       if (isValidValue(fields()[2], Long.valueOf(other.finishTime)))
/* 224:    */       {
/* 225:223 */         this.finishTime = ((Long)data().deepCopy(fields()[2].schema(), Long.valueOf(other.finishTime))).longValue();
/* 226:224 */         fieldSetFlags()[2] = 1;
/* 227:    */       }
/* 228:226 */       if (isValidValue(fields()[3], other.error))
/* 229:    */       {
/* 230:227 */         this.error = ((CharSequence)data().deepCopy(fields()[3].schema(), other.error));
/* 231:228 */         fieldSetFlags()[3] = 1;
/* 232:    */       }
/* 233:230 */       if (isValidValue(fields()[4], other.failedDueToAttempt))
/* 234:    */       {
/* 235:231 */         this.failedDueToAttempt = ((CharSequence)data().deepCopy(fields()[4].schema(), other.failedDueToAttempt));
/* 236:232 */         fieldSetFlags()[4] = 1;
/* 237:    */       }
/* 238:234 */       if (isValidValue(fields()[5], other.status))
/* 239:    */       {
/* 240:235 */         this.status = ((CharSequence)data().deepCopy(fields()[5].schema(), other.status));
/* 241:236 */         fieldSetFlags()[5] = 1;
/* 242:    */       }
/* 243:238 */       if (isValidValue(fields()[6], other.counters))
/* 244:    */       {
/* 245:239 */         this.counters = ((JhCounters)data().deepCopy(fields()[6].schema(), other.counters));
/* 246:240 */         fieldSetFlags()[6] = 1;
/* 247:    */       }
/* 248:    */     }
/* 249:    */     
/* 250:    */     public CharSequence getTaskid()
/* 251:    */     {
/* 252:246 */       return this.taskid;
/* 253:    */     }
/* 254:    */     
/* 255:    */     public Builder setTaskid(CharSequence value)
/* 256:    */     {
/* 257:251 */       validate(fields()[0], value);
/* 258:252 */       this.taskid = value;
/* 259:253 */       fieldSetFlags()[0] = 1;
/* 260:254 */       return this;
/* 261:    */     }
/* 262:    */     
/* 263:    */     public boolean hasTaskid()
/* 264:    */     {
/* 265:259 */       return fieldSetFlags()[0];
/* 266:    */     }
/* 267:    */     
/* 268:    */     public Builder clearTaskid()
/* 269:    */     {
/* 270:264 */       this.taskid = null;
/* 271:265 */       fieldSetFlags()[0] = 0;
/* 272:266 */       return this;
/* 273:    */     }
/* 274:    */     
/* 275:    */     public CharSequence getTaskType()
/* 276:    */     {
/* 277:271 */       return this.taskType;
/* 278:    */     }
/* 279:    */     
/* 280:    */     public Builder setTaskType(CharSequence value)
/* 281:    */     {
/* 282:276 */       validate(fields()[1], value);
/* 283:277 */       this.taskType = value;
/* 284:278 */       fieldSetFlags()[1] = 1;
/* 285:279 */       return this;
/* 286:    */     }
/* 287:    */     
/* 288:    */     public boolean hasTaskType()
/* 289:    */     {
/* 290:284 */       return fieldSetFlags()[1];
/* 291:    */     }
/* 292:    */     
/* 293:    */     public Builder clearTaskType()
/* 294:    */     {
/* 295:289 */       this.taskType = null;
/* 296:290 */       fieldSetFlags()[1] = 0;
/* 297:291 */       return this;
/* 298:    */     }
/* 299:    */     
/* 300:    */     public Long getFinishTime()
/* 301:    */     {
/* 302:296 */       return Long.valueOf(this.finishTime);
/* 303:    */     }
/* 304:    */     
/* 305:    */     public Builder setFinishTime(long value)
/* 306:    */     {
/* 307:301 */       validate(fields()[2], Long.valueOf(value));
/* 308:302 */       this.finishTime = value;
/* 309:303 */       fieldSetFlags()[2] = 1;
/* 310:304 */       return this;
/* 311:    */     }
/* 312:    */     
/* 313:    */     public boolean hasFinishTime()
/* 314:    */     {
/* 315:309 */       return fieldSetFlags()[2];
/* 316:    */     }
/* 317:    */     
/* 318:    */     public Builder clearFinishTime()
/* 319:    */     {
/* 320:314 */       fieldSetFlags()[2] = 0;
/* 321:315 */       return this;
/* 322:    */     }
/* 323:    */     
/* 324:    */     public CharSequence getError()
/* 325:    */     {
/* 326:320 */       return this.error;
/* 327:    */     }
/* 328:    */     
/* 329:    */     public Builder setError(CharSequence value)
/* 330:    */     {
/* 331:325 */       validate(fields()[3], value);
/* 332:326 */       this.error = value;
/* 333:327 */       fieldSetFlags()[3] = 1;
/* 334:328 */       return this;
/* 335:    */     }
/* 336:    */     
/* 337:    */     public boolean hasError()
/* 338:    */     {
/* 339:333 */       return fieldSetFlags()[3];
/* 340:    */     }
/* 341:    */     
/* 342:    */     public Builder clearError()
/* 343:    */     {
/* 344:338 */       this.error = null;
/* 345:339 */       fieldSetFlags()[3] = 0;
/* 346:340 */       return this;
/* 347:    */     }
/* 348:    */     
/* 349:    */     public CharSequence getFailedDueToAttempt()
/* 350:    */     {
/* 351:345 */       return this.failedDueToAttempt;
/* 352:    */     }
/* 353:    */     
/* 354:    */     public Builder setFailedDueToAttempt(CharSequence value)
/* 355:    */     {
/* 356:350 */       validate(fields()[4], value);
/* 357:351 */       this.failedDueToAttempt = value;
/* 358:352 */       fieldSetFlags()[4] = 1;
/* 359:353 */       return this;
/* 360:    */     }
/* 361:    */     
/* 362:    */     public boolean hasFailedDueToAttempt()
/* 363:    */     {
/* 364:358 */       return fieldSetFlags()[4];
/* 365:    */     }
/* 366:    */     
/* 367:    */     public Builder clearFailedDueToAttempt()
/* 368:    */     {
/* 369:363 */       this.failedDueToAttempt = null;
/* 370:364 */       fieldSetFlags()[4] = 0;
/* 371:365 */       return this;
/* 372:    */     }
/* 373:    */     
/* 374:    */     public CharSequence getStatus()
/* 375:    */     {
/* 376:370 */       return this.status;
/* 377:    */     }
/* 378:    */     
/* 379:    */     public Builder setStatus(CharSequence value)
/* 380:    */     {
/* 381:375 */       validate(fields()[5], value);
/* 382:376 */       this.status = value;
/* 383:377 */       fieldSetFlags()[5] = 1;
/* 384:378 */       return this;
/* 385:    */     }
/* 386:    */     
/* 387:    */     public boolean hasStatus()
/* 388:    */     {
/* 389:383 */       return fieldSetFlags()[5];
/* 390:    */     }
/* 391:    */     
/* 392:    */     public Builder clearStatus()
/* 393:    */     {
/* 394:388 */       this.status = null;
/* 395:389 */       fieldSetFlags()[5] = 0;
/* 396:390 */       return this;
/* 397:    */     }
/* 398:    */     
/* 399:    */     public JhCounters getCounters()
/* 400:    */     {
/* 401:395 */       return this.counters;
/* 402:    */     }
/* 403:    */     
/* 404:    */     public Builder setCounters(JhCounters value)
/* 405:    */     {
/* 406:400 */       validate(fields()[6], value);
/* 407:401 */       this.counters = value;
/* 408:402 */       fieldSetFlags()[6] = 1;
/* 409:403 */       return this;
/* 410:    */     }
/* 411:    */     
/* 412:    */     public boolean hasCounters()
/* 413:    */     {
/* 414:408 */       return fieldSetFlags()[6];
/* 415:    */     }
/* 416:    */     
/* 417:    */     public Builder clearCounters()
/* 418:    */     {
/* 419:413 */       this.counters = null;
/* 420:414 */       fieldSetFlags()[6] = 0;
/* 421:415 */       return this;
/* 422:    */     }
/* 423:    */     
/* 424:    */     public TaskFailed build()
/* 425:    */     {
/* 426:    */       try
/* 427:    */       {
/* 428:421 */         TaskFailed record = new TaskFailed();
/* 429:422 */         record.taskid = (fieldSetFlags()[0] != 0 ? this.taskid : (CharSequence)defaultValue(fields()[0]));
/* 430:423 */         record.taskType = (fieldSetFlags()[1] != 0 ? this.taskType : (CharSequence)defaultValue(fields()[1]));
/* 431:424 */         record.finishTime = (fieldSetFlags()[2] != 0 ? this.finishTime : ((Long)defaultValue(fields()[2])).longValue());
/* 432:425 */         record.error = (fieldSetFlags()[3] != 0 ? this.error : (CharSequence)defaultValue(fields()[3]));
/* 433:426 */         record.failedDueToAttempt = (fieldSetFlags()[4] != 0 ? this.failedDueToAttempt : (CharSequence)defaultValue(fields()[4]));
/* 434:427 */         record.status = (fieldSetFlags()[5] != 0 ? this.status : (CharSequence)defaultValue(fields()[5]));
/* 435:428 */         record.counters = (fieldSetFlags()[6] != 0 ? this.counters : (JhCounters)defaultValue(fields()[6]));
/* 436:429 */         return record;
/* 437:    */       }
/* 438:    */       catch (Exception e)
/* 439:    */       {
/* 440:431 */         throw new AvroRuntimeException(e);
/* 441:    */       }
/* 442:    */     }
/* 443:    */   }
/* 444:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.jobhistory.TaskFailed
 * JD-Core Version:    0.7.0.1
 */