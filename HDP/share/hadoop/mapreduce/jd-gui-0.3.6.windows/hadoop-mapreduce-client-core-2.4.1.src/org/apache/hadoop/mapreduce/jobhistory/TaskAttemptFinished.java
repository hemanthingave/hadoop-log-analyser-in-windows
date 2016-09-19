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
/*  15:    */ public class TaskAttemptFinished
/*  16:    */   extends SpecificRecordBase
/*  17:    */   implements SpecificRecord
/*  18:    */ {
/*  19: 10 */   public static final Schema SCHEMA$ = new Schema.Parser().parse("{\"type\":\"record\",\"name\":\"TaskAttemptFinished\",\"namespace\":\"org.apache.hadoop.mapreduce.jobhistory\",\"fields\":[{\"name\":\"taskid\",\"type\":\"string\"},{\"name\":\"attemptId\",\"type\":\"string\"},{\"name\":\"taskType\",\"type\":\"string\"},{\"name\":\"taskStatus\",\"type\":\"string\"},{\"name\":\"finishTime\",\"type\":\"long\"},{\"name\":\"rackname\",\"type\":\"string\"},{\"name\":\"hostname\",\"type\":\"string\"},{\"name\":\"state\",\"type\":\"string\"},{\"name\":\"counters\",\"type\":{\"type\":\"record\",\"name\":\"JhCounters\",\"fields\":[{\"name\":\"name\",\"type\":\"string\"},{\"name\":\"groups\",\"type\":{\"type\":\"array\",\"items\":{\"type\":\"record\",\"name\":\"JhCounterGroup\",\"fields\":[{\"name\":\"name\",\"type\":\"string\"},{\"name\":\"displayName\",\"type\":\"string\"},{\"name\":\"counts\",\"type\":{\"type\":\"array\",\"items\":{\"type\":\"record\",\"name\":\"JhCounter\",\"fields\":[{\"name\":\"name\",\"type\":\"string\"},{\"name\":\"displayName\",\"type\":\"string\"},{\"name\":\"value\",\"type\":\"long\"}]}}}]}}}]}}]}");
/*  20:    */   @Deprecated
/*  21:    */   public CharSequence taskid;
/*  22:    */   @Deprecated
/*  23:    */   public CharSequence attemptId;
/*  24:    */   @Deprecated
/*  25:    */   public CharSequence taskType;
/*  26:    */   @Deprecated
/*  27:    */   public CharSequence taskStatus;
/*  28:    */   @Deprecated
/*  29:    */   public long finishTime;
/*  30:    */   @Deprecated
/*  31:    */   public CharSequence rackname;
/*  32:    */   @Deprecated
/*  33:    */   public CharSequence hostname;
/*  34:    */   @Deprecated
/*  35:    */   public CharSequence state;
/*  36:    */   @Deprecated
/*  37:    */   public JhCounters counters;
/*  38:    */   
/*  39:    */   public static Schema getClassSchema()
/*  40:    */   {
/*  41: 11 */     return SCHEMA$;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public TaskAttemptFinished() {}
/*  45:    */   
/*  46:    */   public TaskAttemptFinished(CharSequence taskid, CharSequence attemptId, CharSequence taskType, CharSequence taskStatus, Long finishTime, CharSequence rackname, CharSequence hostname, CharSequence state, JhCounters counters)
/*  47:    */   {
/*  48: 31 */     this.taskid = taskid;
/*  49: 32 */     this.attemptId = attemptId;
/*  50: 33 */     this.taskType = taskType;
/*  51: 34 */     this.taskStatus = taskStatus;
/*  52: 35 */     this.finishTime = finishTime.longValue();
/*  53: 36 */     this.rackname = rackname;
/*  54: 37 */     this.hostname = hostname;
/*  55: 38 */     this.state = state;
/*  56: 39 */     this.counters = counters;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public Schema getSchema()
/*  60:    */   {
/*  61: 42 */     return SCHEMA$;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public Object get(int field$)
/*  65:    */   {
/*  66: 45 */     switch (field$)
/*  67:    */     {
/*  68:    */     case 0: 
/*  69: 46 */       return this.taskid;
/*  70:    */     case 1: 
/*  71: 47 */       return this.attemptId;
/*  72:    */     case 2: 
/*  73: 48 */       return this.taskType;
/*  74:    */     case 3: 
/*  75: 49 */       return this.taskStatus;
/*  76:    */     case 4: 
/*  77: 50 */       return Long.valueOf(this.finishTime);
/*  78:    */     case 5: 
/*  79: 51 */       return this.rackname;
/*  80:    */     case 6: 
/*  81: 52 */       return this.hostname;
/*  82:    */     case 7: 
/*  83: 53 */       return this.state;
/*  84:    */     case 8: 
/*  85: 54 */       return this.counters;
/*  86:    */     }
/*  87: 55 */     throw new AvroRuntimeException("Bad index");
/*  88:    */   }
/*  89:    */   
/*  90:    */   public void put(int field$, Object value$)
/*  91:    */   {
/*  92: 61 */     switch (field$)
/*  93:    */     {
/*  94:    */     case 0: 
/*  95: 62 */       this.taskid = ((CharSequence)value$); break;
/*  96:    */     case 1: 
/*  97: 63 */       this.attemptId = ((CharSequence)value$); break;
/*  98:    */     case 2: 
/*  99: 64 */       this.taskType = ((CharSequence)value$); break;
/* 100:    */     case 3: 
/* 101: 65 */       this.taskStatus = ((CharSequence)value$); break;
/* 102:    */     case 4: 
/* 103: 66 */       this.finishTime = ((Long)value$).longValue(); break;
/* 104:    */     case 5: 
/* 105: 67 */       this.rackname = ((CharSequence)value$); break;
/* 106:    */     case 6: 
/* 107: 68 */       this.hostname = ((CharSequence)value$); break;
/* 108:    */     case 7: 
/* 109: 69 */       this.state = ((CharSequence)value$); break;
/* 110:    */     case 8: 
/* 111: 70 */       this.counters = ((JhCounters)value$); break;
/* 112:    */     default: 
/* 113: 71 */       throw new AvroRuntimeException("Bad index");
/* 114:    */     }
/* 115:    */   }
/* 116:    */   
/* 117:    */   public CharSequence getTaskid()
/* 118:    */   {
/* 119: 79 */     return this.taskid;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public void setTaskid(CharSequence value)
/* 123:    */   {
/* 124: 87 */     this.taskid = value;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public CharSequence getAttemptId()
/* 128:    */   {
/* 129: 94 */     return this.attemptId;
/* 130:    */   }
/* 131:    */   
/* 132:    */   public void setAttemptId(CharSequence value)
/* 133:    */   {
/* 134:102 */     this.attemptId = value;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public CharSequence getTaskType()
/* 138:    */   {
/* 139:109 */     return this.taskType;
/* 140:    */   }
/* 141:    */   
/* 142:    */   public void setTaskType(CharSequence value)
/* 143:    */   {
/* 144:117 */     this.taskType = value;
/* 145:    */   }
/* 146:    */   
/* 147:    */   public CharSequence getTaskStatus()
/* 148:    */   {
/* 149:124 */     return this.taskStatus;
/* 150:    */   }
/* 151:    */   
/* 152:    */   public void setTaskStatus(CharSequence value)
/* 153:    */   {
/* 154:132 */     this.taskStatus = value;
/* 155:    */   }
/* 156:    */   
/* 157:    */   public Long getFinishTime()
/* 158:    */   {
/* 159:139 */     return Long.valueOf(this.finishTime);
/* 160:    */   }
/* 161:    */   
/* 162:    */   public void setFinishTime(Long value)
/* 163:    */   {
/* 164:147 */     this.finishTime = value.longValue();
/* 165:    */   }
/* 166:    */   
/* 167:    */   public CharSequence getRackname()
/* 168:    */   {
/* 169:154 */     return this.rackname;
/* 170:    */   }
/* 171:    */   
/* 172:    */   public void setRackname(CharSequence value)
/* 173:    */   {
/* 174:162 */     this.rackname = value;
/* 175:    */   }
/* 176:    */   
/* 177:    */   public CharSequence getHostname()
/* 178:    */   {
/* 179:169 */     return this.hostname;
/* 180:    */   }
/* 181:    */   
/* 182:    */   public void setHostname(CharSequence value)
/* 183:    */   {
/* 184:177 */     this.hostname = value;
/* 185:    */   }
/* 186:    */   
/* 187:    */   public CharSequence getState()
/* 188:    */   {
/* 189:184 */     return this.state;
/* 190:    */   }
/* 191:    */   
/* 192:    */   public void setState(CharSequence value)
/* 193:    */   {
/* 194:192 */     this.state = value;
/* 195:    */   }
/* 196:    */   
/* 197:    */   public JhCounters getCounters()
/* 198:    */   {
/* 199:199 */     return this.counters;
/* 200:    */   }
/* 201:    */   
/* 202:    */   public void setCounters(JhCounters value)
/* 203:    */   {
/* 204:207 */     this.counters = value;
/* 205:    */   }
/* 206:    */   
/* 207:    */   public static Builder newBuilder()
/* 208:    */   {
/* 209:212 */     return new Builder(null);
/* 210:    */   }
/* 211:    */   
/* 212:    */   public static Builder newBuilder(Builder other)
/* 213:    */   {
/* 214:217 */     return new Builder(other, null);
/* 215:    */   }
/* 216:    */   
/* 217:    */   public static Builder newBuilder(TaskAttemptFinished other)
/* 218:    */   {
/* 219:222 */     return new Builder(other, null);
/* 220:    */   }
/* 221:    */   
/* 222:    */   public static class Builder
/* 223:    */     extends SpecificRecordBuilderBase<TaskAttemptFinished>
/* 224:    */     implements RecordBuilder<TaskAttemptFinished>
/* 225:    */   {
/* 226:    */     private CharSequence taskid;
/* 227:    */     private CharSequence attemptId;
/* 228:    */     private CharSequence taskType;
/* 229:    */     private CharSequence taskStatus;
/* 230:    */     private long finishTime;
/* 231:    */     private CharSequence rackname;
/* 232:    */     private CharSequence hostname;
/* 233:    */     private CharSequence state;
/* 234:    */     private JhCounters counters;
/* 235:    */     
/* 236:    */     private Builder()
/* 237:    */     {
/* 238:243 */       super();
/* 239:    */     }
/* 240:    */     
/* 241:    */     private Builder(Builder other)
/* 242:    */     {
/* 243:248 */       super();
/* 244:    */     }
/* 245:    */     
/* 246:    */     private Builder(TaskAttemptFinished other)
/* 247:    */     {
/* 248:253 */       super();
/* 249:254 */       if (isValidValue(fields()[0], other.taskid))
/* 250:    */       {
/* 251:255 */         this.taskid = ((CharSequence)data().deepCopy(fields()[0].schema(), other.taskid));
/* 252:256 */         fieldSetFlags()[0] = 1;
/* 253:    */       }
/* 254:258 */       if (isValidValue(fields()[1], other.attemptId))
/* 255:    */       {
/* 256:259 */         this.attemptId = ((CharSequence)data().deepCopy(fields()[1].schema(), other.attemptId));
/* 257:260 */         fieldSetFlags()[1] = 1;
/* 258:    */       }
/* 259:262 */       if (isValidValue(fields()[2], other.taskType))
/* 260:    */       {
/* 261:263 */         this.taskType = ((CharSequence)data().deepCopy(fields()[2].schema(), other.taskType));
/* 262:264 */         fieldSetFlags()[2] = 1;
/* 263:    */       }
/* 264:266 */       if (isValidValue(fields()[3], other.taskStatus))
/* 265:    */       {
/* 266:267 */         this.taskStatus = ((CharSequence)data().deepCopy(fields()[3].schema(), other.taskStatus));
/* 267:268 */         fieldSetFlags()[3] = 1;
/* 268:    */       }
/* 269:270 */       if (isValidValue(fields()[4], Long.valueOf(other.finishTime)))
/* 270:    */       {
/* 271:271 */         this.finishTime = ((Long)data().deepCopy(fields()[4].schema(), Long.valueOf(other.finishTime))).longValue();
/* 272:272 */         fieldSetFlags()[4] = 1;
/* 273:    */       }
/* 274:274 */       if (isValidValue(fields()[5], other.rackname))
/* 275:    */       {
/* 276:275 */         this.rackname = ((CharSequence)data().deepCopy(fields()[5].schema(), other.rackname));
/* 277:276 */         fieldSetFlags()[5] = 1;
/* 278:    */       }
/* 279:278 */       if (isValidValue(fields()[6], other.hostname))
/* 280:    */       {
/* 281:279 */         this.hostname = ((CharSequence)data().deepCopy(fields()[6].schema(), other.hostname));
/* 282:280 */         fieldSetFlags()[6] = 1;
/* 283:    */       }
/* 284:282 */       if (isValidValue(fields()[7], other.state))
/* 285:    */       {
/* 286:283 */         this.state = ((CharSequence)data().deepCopy(fields()[7].schema(), other.state));
/* 287:284 */         fieldSetFlags()[7] = 1;
/* 288:    */       }
/* 289:286 */       if (isValidValue(fields()[8], other.counters))
/* 290:    */       {
/* 291:287 */         this.counters = ((JhCounters)data().deepCopy(fields()[8].schema(), other.counters));
/* 292:288 */         fieldSetFlags()[8] = 1;
/* 293:    */       }
/* 294:    */     }
/* 295:    */     
/* 296:    */     public CharSequence getTaskid()
/* 297:    */     {
/* 298:294 */       return this.taskid;
/* 299:    */     }
/* 300:    */     
/* 301:    */     public Builder setTaskid(CharSequence value)
/* 302:    */     {
/* 303:299 */       validate(fields()[0], value);
/* 304:300 */       this.taskid = value;
/* 305:301 */       fieldSetFlags()[0] = 1;
/* 306:302 */       return this;
/* 307:    */     }
/* 308:    */     
/* 309:    */     public boolean hasTaskid()
/* 310:    */     {
/* 311:307 */       return fieldSetFlags()[0];
/* 312:    */     }
/* 313:    */     
/* 314:    */     public Builder clearTaskid()
/* 315:    */     {
/* 316:312 */       this.taskid = null;
/* 317:313 */       fieldSetFlags()[0] = 0;
/* 318:314 */       return this;
/* 319:    */     }
/* 320:    */     
/* 321:    */     public CharSequence getAttemptId()
/* 322:    */     {
/* 323:319 */       return this.attemptId;
/* 324:    */     }
/* 325:    */     
/* 326:    */     public Builder setAttemptId(CharSequence value)
/* 327:    */     {
/* 328:324 */       validate(fields()[1], value);
/* 329:325 */       this.attemptId = value;
/* 330:326 */       fieldSetFlags()[1] = 1;
/* 331:327 */       return this;
/* 332:    */     }
/* 333:    */     
/* 334:    */     public boolean hasAttemptId()
/* 335:    */     {
/* 336:332 */       return fieldSetFlags()[1];
/* 337:    */     }
/* 338:    */     
/* 339:    */     public Builder clearAttemptId()
/* 340:    */     {
/* 341:337 */       this.attemptId = null;
/* 342:338 */       fieldSetFlags()[1] = 0;
/* 343:339 */       return this;
/* 344:    */     }
/* 345:    */     
/* 346:    */     public CharSequence getTaskType()
/* 347:    */     {
/* 348:344 */       return this.taskType;
/* 349:    */     }
/* 350:    */     
/* 351:    */     public Builder setTaskType(CharSequence value)
/* 352:    */     {
/* 353:349 */       validate(fields()[2], value);
/* 354:350 */       this.taskType = value;
/* 355:351 */       fieldSetFlags()[2] = 1;
/* 356:352 */       return this;
/* 357:    */     }
/* 358:    */     
/* 359:    */     public boolean hasTaskType()
/* 360:    */     {
/* 361:357 */       return fieldSetFlags()[2];
/* 362:    */     }
/* 363:    */     
/* 364:    */     public Builder clearTaskType()
/* 365:    */     {
/* 366:362 */       this.taskType = null;
/* 367:363 */       fieldSetFlags()[2] = 0;
/* 368:364 */       return this;
/* 369:    */     }
/* 370:    */     
/* 371:    */     public CharSequence getTaskStatus()
/* 372:    */     {
/* 373:369 */       return this.taskStatus;
/* 374:    */     }
/* 375:    */     
/* 376:    */     public Builder setTaskStatus(CharSequence value)
/* 377:    */     {
/* 378:374 */       validate(fields()[3], value);
/* 379:375 */       this.taskStatus = value;
/* 380:376 */       fieldSetFlags()[3] = 1;
/* 381:377 */       return this;
/* 382:    */     }
/* 383:    */     
/* 384:    */     public boolean hasTaskStatus()
/* 385:    */     {
/* 386:382 */       return fieldSetFlags()[3];
/* 387:    */     }
/* 388:    */     
/* 389:    */     public Builder clearTaskStatus()
/* 390:    */     {
/* 391:387 */       this.taskStatus = null;
/* 392:388 */       fieldSetFlags()[3] = 0;
/* 393:389 */       return this;
/* 394:    */     }
/* 395:    */     
/* 396:    */     public Long getFinishTime()
/* 397:    */     {
/* 398:394 */       return Long.valueOf(this.finishTime);
/* 399:    */     }
/* 400:    */     
/* 401:    */     public Builder setFinishTime(long value)
/* 402:    */     {
/* 403:399 */       validate(fields()[4], Long.valueOf(value));
/* 404:400 */       this.finishTime = value;
/* 405:401 */       fieldSetFlags()[4] = 1;
/* 406:402 */       return this;
/* 407:    */     }
/* 408:    */     
/* 409:    */     public boolean hasFinishTime()
/* 410:    */     {
/* 411:407 */       return fieldSetFlags()[4];
/* 412:    */     }
/* 413:    */     
/* 414:    */     public Builder clearFinishTime()
/* 415:    */     {
/* 416:412 */       fieldSetFlags()[4] = 0;
/* 417:413 */       return this;
/* 418:    */     }
/* 419:    */     
/* 420:    */     public CharSequence getRackname()
/* 421:    */     {
/* 422:418 */       return this.rackname;
/* 423:    */     }
/* 424:    */     
/* 425:    */     public Builder setRackname(CharSequence value)
/* 426:    */     {
/* 427:423 */       validate(fields()[5], value);
/* 428:424 */       this.rackname = value;
/* 429:425 */       fieldSetFlags()[5] = 1;
/* 430:426 */       return this;
/* 431:    */     }
/* 432:    */     
/* 433:    */     public boolean hasRackname()
/* 434:    */     {
/* 435:431 */       return fieldSetFlags()[5];
/* 436:    */     }
/* 437:    */     
/* 438:    */     public Builder clearRackname()
/* 439:    */     {
/* 440:436 */       this.rackname = null;
/* 441:437 */       fieldSetFlags()[5] = 0;
/* 442:438 */       return this;
/* 443:    */     }
/* 444:    */     
/* 445:    */     public CharSequence getHostname()
/* 446:    */     {
/* 447:443 */       return this.hostname;
/* 448:    */     }
/* 449:    */     
/* 450:    */     public Builder setHostname(CharSequence value)
/* 451:    */     {
/* 452:448 */       validate(fields()[6], value);
/* 453:449 */       this.hostname = value;
/* 454:450 */       fieldSetFlags()[6] = 1;
/* 455:451 */       return this;
/* 456:    */     }
/* 457:    */     
/* 458:    */     public boolean hasHostname()
/* 459:    */     {
/* 460:456 */       return fieldSetFlags()[6];
/* 461:    */     }
/* 462:    */     
/* 463:    */     public Builder clearHostname()
/* 464:    */     {
/* 465:461 */       this.hostname = null;
/* 466:462 */       fieldSetFlags()[6] = 0;
/* 467:463 */       return this;
/* 468:    */     }
/* 469:    */     
/* 470:    */     public CharSequence getState()
/* 471:    */     {
/* 472:468 */       return this.state;
/* 473:    */     }
/* 474:    */     
/* 475:    */     public Builder setState(CharSequence value)
/* 476:    */     {
/* 477:473 */       validate(fields()[7], value);
/* 478:474 */       this.state = value;
/* 479:475 */       fieldSetFlags()[7] = 1;
/* 480:476 */       return this;
/* 481:    */     }
/* 482:    */     
/* 483:    */     public boolean hasState()
/* 484:    */     {
/* 485:481 */       return fieldSetFlags()[7];
/* 486:    */     }
/* 487:    */     
/* 488:    */     public Builder clearState()
/* 489:    */     {
/* 490:486 */       this.state = null;
/* 491:487 */       fieldSetFlags()[7] = 0;
/* 492:488 */       return this;
/* 493:    */     }
/* 494:    */     
/* 495:    */     public JhCounters getCounters()
/* 496:    */     {
/* 497:493 */       return this.counters;
/* 498:    */     }
/* 499:    */     
/* 500:    */     public Builder setCounters(JhCounters value)
/* 501:    */     {
/* 502:498 */       validate(fields()[8], value);
/* 503:499 */       this.counters = value;
/* 504:500 */       fieldSetFlags()[8] = 1;
/* 505:501 */       return this;
/* 506:    */     }
/* 507:    */     
/* 508:    */     public boolean hasCounters()
/* 509:    */     {
/* 510:506 */       return fieldSetFlags()[8];
/* 511:    */     }
/* 512:    */     
/* 513:    */     public Builder clearCounters()
/* 514:    */     {
/* 515:511 */       this.counters = null;
/* 516:512 */       fieldSetFlags()[8] = 0;
/* 517:513 */       return this;
/* 518:    */     }
/* 519:    */     
/* 520:    */     public TaskAttemptFinished build()
/* 521:    */     {
/* 522:    */       try
/* 523:    */       {
/* 524:519 */         TaskAttemptFinished record = new TaskAttemptFinished();
/* 525:520 */         record.taskid = (fieldSetFlags()[0] != 0 ? this.taskid : (CharSequence)defaultValue(fields()[0]));
/* 526:521 */         record.attemptId = (fieldSetFlags()[1] != 0 ? this.attemptId : (CharSequence)defaultValue(fields()[1]));
/* 527:522 */         record.taskType = (fieldSetFlags()[2] != 0 ? this.taskType : (CharSequence)defaultValue(fields()[2]));
/* 528:523 */         record.taskStatus = (fieldSetFlags()[3] != 0 ? this.taskStatus : (CharSequence)defaultValue(fields()[3]));
/* 529:524 */         record.finishTime = (fieldSetFlags()[4] != 0 ? this.finishTime : ((Long)defaultValue(fields()[4])).longValue());
/* 530:525 */         record.rackname = (fieldSetFlags()[5] != 0 ? this.rackname : (CharSequence)defaultValue(fields()[5]));
/* 531:526 */         record.hostname = (fieldSetFlags()[6] != 0 ? this.hostname : (CharSequence)defaultValue(fields()[6]));
/* 532:527 */         record.state = (fieldSetFlags()[7] != 0 ? this.state : (CharSequence)defaultValue(fields()[7]));
/* 533:528 */         record.counters = (fieldSetFlags()[8] != 0 ? this.counters : (JhCounters)defaultValue(fields()[8]));
/* 534:529 */         return record;
/* 535:    */       }
/* 536:    */       catch (Exception e)
/* 537:    */       {
/* 538:531 */         throw new AvroRuntimeException(e);
/* 539:    */       }
/* 540:    */     }
/* 541:    */   }
/* 542:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.jobhistory.TaskAttemptFinished
 * JD-Core Version:    0.7.0.1
 */