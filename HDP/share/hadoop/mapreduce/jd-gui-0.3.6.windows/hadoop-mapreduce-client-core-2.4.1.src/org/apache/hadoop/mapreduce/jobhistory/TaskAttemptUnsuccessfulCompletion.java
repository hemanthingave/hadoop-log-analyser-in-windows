/*   1:    */ package org.apache.hadoop.mapreduce.jobhistory;
/*   2:    */ 
/*   3:    */ import java.util.List;
/*   4:    */ import org.apache.avro.AvroRuntimeException;
/*   5:    */ import org.apache.avro.Schema;
/*   6:    */ import org.apache.avro.Schema.Field;
/*   7:    */ import org.apache.avro.Schema.Parser;
/*   8:    */ import org.apache.avro.data.RecordBuilder;
/*   9:    */ import org.apache.avro.generic.GenericData;
/*  10:    */ import org.apache.avro.specific.AvroGenerated;
/*  11:    */ import org.apache.avro.specific.SpecificRecord;
/*  12:    */ import org.apache.avro.specific.SpecificRecordBase;
/*  13:    */ import org.apache.avro.specific.SpecificRecordBuilderBase;
/*  14:    */ 
/*  15:    */ @AvroGenerated
/*  16:    */ public class TaskAttemptUnsuccessfulCompletion
/*  17:    */   extends SpecificRecordBase
/*  18:    */   implements SpecificRecord
/*  19:    */ {
/*  20: 10 */   public static final Schema SCHEMA$ = new Schema.Parser().parse("{\"type\":\"record\",\"name\":\"TaskAttemptUnsuccessfulCompletion\",\"namespace\":\"org.apache.hadoop.mapreduce.jobhistory\",\"fields\":[{\"name\":\"taskid\",\"type\":\"string\"},{\"name\":\"taskType\",\"type\":\"string\"},{\"name\":\"attemptId\",\"type\":\"string\"},{\"name\":\"finishTime\",\"type\":\"long\"},{\"name\":\"hostname\",\"type\":\"string\"},{\"name\":\"port\",\"type\":\"int\"},{\"name\":\"rackname\",\"type\":\"string\"},{\"name\":\"status\",\"type\":\"string\"},{\"name\":\"error\",\"type\":\"string\"},{\"name\":\"counters\",\"type\":{\"type\":\"record\",\"name\":\"JhCounters\",\"fields\":[{\"name\":\"name\",\"type\":\"string\"},{\"name\":\"groups\",\"type\":{\"type\":\"array\",\"items\":{\"type\":\"record\",\"name\":\"JhCounterGroup\",\"fields\":[{\"name\":\"name\",\"type\":\"string\"},{\"name\":\"displayName\",\"type\":\"string\"},{\"name\":\"counts\",\"type\":{\"type\":\"array\",\"items\":{\"type\":\"record\",\"name\":\"JhCounter\",\"fields\":[{\"name\":\"name\",\"type\":\"string\"},{\"name\":\"displayName\",\"type\":\"string\"},{\"name\":\"value\",\"type\":\"long\"}]}}}]}}}]}},{\"name\":\"clockSplits\",\"type\":{\"type\":\"array\",\"items\":\"int\"}},{\"name\":\"cpuUsages\",\"type\":{\"type\":\"array\",\"items\":\"int\"}},{\"name\":\"vMemKbytes\",\"type\":{\"type\":\"array\",\"items\":\"int\"}},{\"name\":\"physMemKbytes\",\"type\":{\"type\":\"array\",\"items\":\"int\"}}]}");
/*  21:    */   @Deprecated
/*  22:    */   public CharSequence taskid;
/*  23:    */   @Deprecated
/*  24:    */   public CharSequence taskType;
/*  25:    */   @Deprecated
/*  26:    */   public CharSequence attemptId;
/*  27:    */   @Deprecated
/*  28:    */   public long finishTime;
/*  29:    */   @Deprecated
/*  30:    */   public CharSequence hostname;
/*  31:    */   @Deprecated
/*  32:    */   public int port;
/*  33:    */   @Deprecated
/*  34:    */   public CharSequence rackname;
/*  35:    */   @Deprecated
/*  36:    */   public CharSequence status;
/*  37:    */   @Deprecated
/*  38:    */   public CharSequence error;
/*  39:    */   @Deprecated
/*  40:    */   public JhCounters counters;
/*  41:    */   @Deprecated
/*  42:    */   public List<Integer> clockSplits;
/*  43:    */   @Deprecated
/*  44:    */   public List<Integer> cpuUsages;
/*  45:    */   @Deprecated
/*  46:    */   public List<Integer> vMemKbytes;
/*  47:    */   @Deprecated
/*  48:    */   public List<Integer> physMemKbytes;
/*  49:    */   
/*  50:    */   public static Schema getClassSchema()
/*  51:    */   {
/*  52: 11 */     return SCHEMA$;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public TaskAttemptUnsuccessfulCompletion() {}
/*  56:    */   
/*  57:    */   public TaskAttemptUnsuccessfulCompletion(CharSequence taskid, CharSequence taskType, CharSequence attemptId, Long finishTime, CharSequence hostname, Integer port, CharSequence rackname, CharSequence status, CharSequence error, JhCounters counters, List<Integer> clockSplits, List<Integer> cpuUsages, List<Integer> vMemKbytes, List<Integer> physMemKbytes)
/*  58:    */   {
/*  59: 36 */     this.taskid = taskid;
/*  60: 37 */     this.taskType = taskType;
/*  61: 38 */     this.attemptId = attemptId;
/*  62: 39 */     this.finishTime = finishTime.longValue();
/*  63: 40 */     this.hostname = hostname;
/*  64: 41 */     this.port = port.intValue();
/*  65: 42 */     this.rackname = rackname;
/*  66: 43 */     this.status = status;
/*  67: 44 */     this.error = error;
/*  68: 45 */     this.counters = counters;
/*  69: 46 */     this.clockSplits = clockSplits;
/*  70: 47 */     this.cpuUsages = cpuUsages;
/*  71: 48 */     this.vMemKbytes = vMemKbytes;
/*  72: 49 */     this.physMemKbytes = physMemKbytes;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public Schema getSchema()
/*  76:    */   {
/*  77: 52 */     return SCHEMA$;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public Object get(int field$)
/*  81:    */   {
/*  82: 55 */     switch (field$)
/*  83:    */     {
/*  84:    */     case 0: 
/*  85: 56 */       return this.taskid;
/*  86:    */     case 1: 
/*  87: 57 */       return this.taskType;
/*  88:    */     case 2: 
/*  89: 58 */       return this.attemptId;
/*  90:    */     case 3: 
/*  91: 59 */       return Long.valueOf(this.finishTime);
/*  92:    */     case 4: 
/*  93: 60 */       return this.hostname;
/*  94:    */     case 5: 
/*  95: 61 */       return Integer.valueOf(this.port);
/*  96:    */     case 6: 
/*  97: 62 */       return this.rackname;
/*  98:    */     case 7: 
/*  99: 63 */       return this.status;
/* 100:    */     case 8: 
/* 101: 64 */       return this.error;
/* 102:    */     case 9: 
/* 103: 65 */       return this.counters;
/* 104:    */     case 10: 
/* 105: 66 */       return this.clockSplits;
/* 106:    */     case 11: 
/* 107: 67 */       return this.cpuUsages;
/* 108:    */     case 12: 
/* 109: 68 */       return this.vMemKbytes;
/* 110:    */     case 13: 
/* 111: 69 */       return this.physMemKbytes;
/* 112:    */     }
/* 113: 70 */     throw new AvroRuntimeException("Bad index");
/* 114:    */   }
/* 115:    */   
/* 116:    */   public void put(int field$, Object value$)
/* 117:    */   {
/* 118: 76 */     switch (field$)
/* 119:    */     {
/* 120:    */     case 0: 
/* 121: 77 */       this.taskid = ((CharSequence)value$); break;
/* 122:    */     case 1: 
/* 123: 78 */       this.taskType = ((CharSequence)value$); break;
/* 124:    */     case 2: 
/* 125: 79 */       this.attemptId = ((CharSequence)value$); break;
/* 126:    */     case 3: 
/* 127: 80 */       this.finishTime = ((Long)value$).longValue(); break;
/* 128:    */     case 4: 
/* 129: 81 */       this.hostname = ((CharSequence)value$); break;
/* 130:    */     case 5: 
/* 131: 82 */       this.port = ((Integer)value$).intValue(); break;
/* 132:    */     case 6: 
/* 133: 83 */       this.rackname = ((CharSequence)value$); break;
/* 134:    */     case 7: 
/* 135: 84 */       this.status = ((CharSequence)value$); break;
/* 136:    */     case 8: 
/* 137: 85 */       this.error = ((CharSequence)value$); break;
/* 138:    */     case 9: 
/* 139: 86 */       this.counters = ((JhCounters)value$); break;
/* 140:    */     case 10: 
/* 141: 87 */       this.clockSplits = ((List)value$); break;
/* 142:    */     case 11: 
/* 143: 88 */       this.cpuUsages = ((List)value$); break;
/* 144:    */     case 12: 
/* 145: 89 */       this.vMemKbytes = ((List)value$); break;
/* 146:    */     case 13: 
/* 147: 90 */       this.physMemKbytes = ((List)value$); break;
/* 148:    */     default: 
/* 149: 91 */       throw new AvroRuntimeException("Bad index");
/* 150:    */     }
/* 151:    */   }
/* 152:    */   
/* 153:    */   public CharSequence getTaskid()
/* 154:    */   {
/* 155: 99 */     return this.taskid;
/* 156:    */   }
/* 157:    */   
/* 158:    */   public void setTaskid(CharSequence value)
/* 159:    */   {
/* 160:107 */     this.taskid = value;
/* 161:    */   }
/* 162:    */   
/* 163:    */   public CharSequence getTaskType()
/* 164:    */   {
/* 165:114 */     return this.taskType;
/* 166:    */   }
/* 167:    */   
/* 168:    */   public void setTaskType(CharSequence value)
/* 169:    */   {
/* 170:122 */     this.taskType = value;
/* 171:    */   }
/* 172:    */   
/* 173:    */   public CharSequence getAttemptId()
/* 174:    */   {
/* 175:129 */     return this.attemptId;
/* 176:    */   }
/* 177:    */   
/* 178:    */   public void setAttemptId(CharSequence value)
/* 179:    */   {
/* 180:137 */     this.attemptId = value;
/* 181:    */   }
/* 182:    */   
/* 183:    */   public Long getFinishTime()
/* 184:    */   {
/* 185:144 */     return Long.valueOf(this.finishTime);
/* 186:    */   }
/* 187:    */   
/* 188:    */   public void setFinishTime(Long value)
/* 189:    */   {
/* 190:152 */     this.finishTime = value.longValue();
/* 191:    */   }
/* 192:    */   
/* 193:    */   public CharSequence getHostname()
/* 194:    */   {
/* 195:159 */     return this.hostname;
/* 196:    */   }
/* 197:    */   
/* 198:    */   public void setHostname(CharSequence value)
/* 199:    */   {
/* 200:167 */     this.hostname = value;
/* 201:    */   }
/* 202:    */   
/* 203:    */   public Integer getPort()
/* 204:    */   {
/* 205:174 */     return Integer.valueOf(this.port);
/* 206:    */   }
/* 207:    */   
/* 208:    */   public void setPort(Integer value)
/* 209:    */   {
/* 210:182 */     this.port = value.intValue();
/* 211:    */   }
/* 212:    */   
/* 213:    */   public CharSequence getRackname()
/* 214:    */   {
/* 215:189 */     return this.rackname;
/* 216:    */   }
/* 217:    */   
/* 218:    */   public void setRackname(CharSequence value)
/* 219:    */   {
/* 220:197 */     this.rackname = value;
/* 221:    */   }
/* 222:    */   
/* 223:    */   public CharSequence getStatus()
/* 224:    */   {
/* 225:204 */     return this.status;
/* 226:    */   }
/* 227:    */   
/* 228:    */   public void setStatus(CharSequence value)
/* 229:    */   {
/* 230:212 */     this.status = value;
/* 231:    */   }
/* 232:    */   
/* 233:    */   public CharSequence getError()
/* 234:    */   {
/* 235:219 */     return this.error;
/* 236:    */   }
/* 237:    */   
/* 238:    */   public void setError(CharSequence value)
/* 239:    */   {
/* 240:227 */     this.error = value;
/* 241:    */   }
/* 242:    */   
/* 243:    */   public JhCounters getCounters()
/* 244:    */   {
/* 245:234 */     return this.counters;
/* 246:    */   }
/* 247:    */   
/* 248:    */   public void setCounters(JhCounters value)
/* 249:    */   {
/* 250:242 */     this.counters = value;
/* 251:    */   }
/* 252:    */   
/* 253:    */   public List<Integer> getClockSplits()
/* 254:    */   {
/* 255:249 */     return this.clockSplits;
/* 256:    */   }
/* 257:    */   
/* 258:    */   public void setClockSplits(List<Integer> value)
/* 259:    */   {
/* 260:257 */     this.clockSplits = value;
/* 261:    */   }
/* 262:    */   
/* 263:    */   public List<Integer> getCpuUsages()
/* 264:    */   {
/* 265:264 */     return this.cpuUsages;
/* 266:    */   }
/* 267:    */   
/* 268:    */   public void setCpuUsages(List<Integer> value)
/* 269:    */   {
/* 270:272 */     this.cpuUsages = value;
/* 271:    */   }
/* 272:    */   
/* 273:    */   public List<Integer> getVMemKbytes()
/* 274:    */   {
/* 275:279 */     return this.vMemKbytes;
/* 276:    */   }
/* 277:    */   
/* 278:    */   public void setVMemKbytes(List<Integer> value)
/* 279:    */   {
/* 280:287 */     this.vMemKbytes = value;
/* 281:    */   }
/* 282:    */   
/* 283:    */   public List<Integer> getPhysMemKbytes()
/* 284:    */   {
/* 285:294 */     return this.physMemKbytes;
/* 286:    */   }
/* 287:    */   
/* 288:    */   public void setPhysMemKbytes(List<Integer> value)
/* 289:    */   {
/* 290:302 */     this.physMemKbytes = value;
/* 291:    */   }
/* 292:    */   
/* 293:    */   public static Builder newBuilder()
/* 294:    */   {
/* 295:307 */     return new Builder(null);
/* 296:    */   }
/* 297:    */   
/* 298:    */   public static Builder newBuilder(Builder other)
/* 299:    */   {
/* 300:312 */     return new Builder(other, null);
/* 301:    */   }
/* 302:    */   
/* 303:    */   public static Builder newBuilder(TaskAttemptUnsuccessfulCompletion other)
/* 304:    */   {
/* 305:317 */     return new Builder(other, null);
/* 306:    */   }
/* 307:    */   
/* 308:    */   public static class Builder
/* 309:    */     extends SpecificRecordBuilderBase<TaskAttemptUnsuccessfulCompletion>
/* 310:    */     implements RecordBuilder<TaskAttemptUnsuccessfulCompletion>
/* 311:    */   {
/* 312:    */     private CharSequence taskid;
/* 313:    */     private CharSequence taskType;
/* 314:    */     private CharSequence attemptId;
/* 315:    */     private long finishTime;
/* 316:    */     private CharSequence hostname;
/* 317:    */     private int port;
/* 318:    */     private CharSequence rackname;
/* 319:    */     private CharSequence status;
/* 320:    */     private CharSequence error;
/* 321:    */     private JhCounters counters;
/* 322:    */     private List<Integer> clockSplits;
/* 323:    */     private List<Integer> cpuUsages;
/* 324:    */     private List<Integer> vMemKbytes;
/* 325:    */     private List<Integer> physMemKbytes;
/* 326:    */     
/* 327:    */     private Builder()
/* 328:    */     {
/* 329:343 */       super();
/* 330:    */     }
/* 331:    */     
/* 332:    */     private Builder(Builder other)
/* 333:    */     {
/* 334:348 */       super();
/* 335:    */     }
/* 336:    */     
/* 337:    */     private Builder(TaskAttemptUnsuccessfulCompletion other)
/* 338:    */     {
/* 339:353 */       super();
/* 340:354 */       if (isValidValue(fields()[0], other.taskid))
/* 341:    */       {
/* 342:355 */         this.taskid = ((CharSequence)data().deepCopy(fields()[0].schema(), other.taskid));
/* 343:356 */         fieldSetFlags()[0] = 1;
/* 344:    */       }
/* 345:358 */       if (isValidValue(fields()[1], other.taskType))
/* 346:    */       {
/* 347:359 */         this.taskType = ((CharSequence)data().deepCopy(fields()[1].schema(), other.taskType));
/* 348:360 */         fieldSetFlags()[1] = 1;
/* 349:    */       }
/* 350:362 */       if (isValidValue(fields()[2], other.attemptId))
/* 351:    */       {
/* 352:363 */         this.attemptId = ((CharSequence)data().deepCopy(fields()[2].schema(), other.attemptId));
/* 353:364 */         fieldSetFlags()[2] = 1;
/* 354:    */       }
/* 355:366 */       if (isValidValue(fields()[3], Long.valueOf(other.finishTime)))
/* 356:    */       {
/* 357:367 */         this.finishTime = ((Long)data().deepCopy(fields()[3].schema(), Long.valueOf(other.finishTime))).longValue();
/* 358:368 */         fieldSetFlags()[3] = 1;
/* 359:    */       }
/* 360:370 */       if (isValidValue(fields()[4], other.hostname))
/* 361:    */       {
/* 362:371 */         this.hostname = ((CharSequence)data().deepCopy(fields()[4].schema(), other.hostname));
/* 363:372 */         fieldSetFlags()[4] = 1;
/* 364:    */       }
/* 365:374 */       if (isValidValue(fields()[5], Integer.valueOf(other.port)))
/* 366:    */       {
/* 367:375 */         this.port = ((Integer)data().deepCopy(fields()[5].schema(), Integer.valueOf(other.port))).intValue();
/* 368:376 */         fieldSetFlags()[5] = 1;
/* 369:    */       }
/* 370:378 */       if (isValidValue(fields()[6], other.rackname))
/* 371:    */       {
/* 372:379 */         this.rackname = ((CharSequence)data().deepCopy(fields()[6].schema(), other.rackname));
/* 373:380 */         fieldSetFlags()[6] = 1;
/* 374:    */       }
/* 375:382 */       if (isValidValue(fields()[7], other.status))
/* 376:    */       {
/* 377:383 */         this.status = ((CharSequence)data().deepCopy(fields()[7].schema(), other.status));
/* 378:384 */         fieldSetFlags()[7] = 1;
/* 379:    */       }
/* 380:386 */       if (isValidValue(fields()[8], other.error))
/* 381:    */       {
/* 382:387 */         this.error = ((CharSequence)data().deepCopy(fields()[8].schema(), other.error));
/* 383:388 */         fieldSetFlags()[8] = 1;
/* 384:    */       }
/* 385:390 */       if (isValidValue(fields()[9], other.counters))
/* 386:    */       {
/* 387:391 */         this.counters = ((JhCounters)data().deepCopy(fields()[9].schema(), other.counters));
/* 388:392 */         fieldSetFlags()[9] = 1;
/* 389:    */       }
/* 390:394 */       if (isValidValue(fields()[10], other.clockSplits))
/* 391:    */       {
/* 392:395 */         this.clockSplits = ((List)data().deepCopy(fields()[10].schema(), other.clockSplits));
/* 393:396 */         fieldSetFlags()[10] = 1;
/* 394:    */       }
/* 395:398 */       if (isValidValue(fields()[11], other.cpuUsages))
/* 396:    */       {
/* 397:399 */         this.cpuUsages = ((List)data().deepCopy(fields()[11].schema(), other.cpuUsages));
/* 398:400 */         fieldSetFlags()[11] = 1;
/* 399:    */       }
/* 400:402 */       if (isValidValue(fields()[12], other.vMemKbytes))
/* 401:    */       {
/* 402:403 */         this.vMemKbytes = ((List)data().deepCopy(fields()[12].schema(), other.vMemKbytes));
/* 403:404 */         fieldSetFlags()[12] = 1;
/* 404:    */       }
/* 405:406 */       if (isValidValue(fields()[13], other.physMemKbytes))
/* 406:    */       {
/* 407:407 */         this.physMemKbytes = ((List)data().deepCopy(fields()[13].schema(), other.physMemKbytes));
/* 408:408 */         fieldSetFlags()[13] = 1;
/* 409:    */       }
/* 410:    */     }
/* 411:    */     
/* 412:    */     public CharSequence getTaskid()
/* 413:    */     {
/* 414:414 */       return this.taskid;
/* 415:    */     }
/* 416:    */     
/* 417:    */     public Builder setTaskid(CharSequence value)
/* 418:    */     {
/* 419:419 */       validate(fields()[0], value);
/* 420:420 */       this.taskid = value;
/* 421:421 */       fieldSetFlags()[0] = 1;
/* 422:422 */       return this;
/* 423:    */     }
/* 424:    */     
/* 425:    */     public boolean hasTaskid()
/* 426:    */     {
/* 427:427 */       return fieldSetFlags()[0];
/* 428:    */     }
/* 429:    */     
/* 430:    */     public Builder clearTaskid()
/* 431:    */     {
/* 432:432 */       this.taskid = null;
/* 433:433 */       fieldSetFlags()[0] = 0;
/* 434:434 */       return this;
/* 435:    */     }
/* 436:    */     
/* 437:    */     public CharSequence getTaskType()
/* 438:    */     {
/* 439:439 */       return this.taskType;
/* 440:    */     }
/* 441:    */     
/* 442:    */     public Builder setTaskType(CharSequence value)
/* 443:    */     {
/* 444:444 */       validate(fields()[1], value);
/* 445:445 */       this.taskType = value;
/* 446:446 */       fieldSetFlags()[1] = 1;
/* 447:447 */       return this;
/* 448:    */     }
/* 449:    */     
/* 450:    */     public boolean hasTaskType()
/* 451:    */     {
/* 452:452 */       return fieldSetFlags()[1];
/* 453:    */     }
/* 454:    */     
/* 455:    */     public Builder clearTaskType()
/* 456:    */     {
/* 457:457 */       this.taskType = null;
/* 458:458 */       fieldSetFlags()[1] = 0;
/* 459:459 */       return this;
/* 460:    */     }
/* 461:    */     
/* 462:    */     public CharSequence getAttemptId()
/* 463:    */     {
/* 464:464 */       return this.attemptId;
/* 465:    */     }
/* 466:    */     
/* 467:    */     public Builder setAttemptId(CharSequence value)
/* 468:    */     {
/* 469:469 */       validate(fields()[2], value);
/* 470:470 */       this.attemptId = value;
/* 471:471 */       fieldSetFlags()[2] = 1;
/* 472:472 */       return this;
/* 473:    */     }
/* 474:    */     
/* 475:    */     public boolean hasAttemptId()
/* 476:    */     {
/* 477:477 */       return fieldSetFlags()[2];
/* 478:    */     }
/* 479:    */     
/* 480:    */     public Builder clearAttemptId()
/* 481:    */     {
/* 482:482 */       this.attemptId = null;
/* 483:483 */       fieldSetFlags()[2] = 0;
/* 484:484 */       return this;
/* 485:    */     }
/* 486:    */     
/* 487:    */     public Long getFinishTime()
/* 488:    */     {
/* 489:489 */       return Long.valueOf(this.finishTime);
/* 490:    */     }
/* 491:    */     
/* 492:    */     public Builder setFinishTime(long value)
/* 493:    */     {
/* 494:494 */       validate(fields()[3], Long.valueOf(value));
/* 495:495 */       this.finishTime = value;
/* 496:496 */       fieldSetFlags()[3] = 1;
/* 497:497 */       return this;
/* 498:    */     }
/* 499:    */     
/* 500:    */     public boolean hasFinishTime()
/* 501:    */     {
/* 502:502 */       return fieldSetFlags()[3];
/* 503:    */     }
/* 504:    */     
/* 505:    */     public Builder clearFinishTime()
/* 506:    */     {
/* 507:507 */       fieldSetFlags()[3] = 0;
/* 508:508 */       return this;
/* 509:    */     }
/* 510:    */     
/* 511:    */     public CharSequence getHostname()
/* 512:    */     {
/* 513:513 */       return this.hostname;
/* 514:    */     }
/* 515:    */     
/* 516:    */     public Builder setHostname(CharSequence value)
/* 517:    */     {
/* 518:518 */       validate(fields()[4], value);
/* 519:519 */       this.hostname = value;
/* 520:520 */       fieldSetFlags()[4] = 1;
/* 521:521 */       return this;
/* 522:    */     }
/* 523:    */     
/* 524:    */     public boolean hasHostname()
/* 525:    */     {
/* 526:526 */       return fieldSetFlags()[4];
/* 527:    */     }
/* 528:    */     
/* 529:    */     public Builder clearHostname()
/* 530:    */     {
/* 531:531 */       this.hostname = null;
/* 532:532 */       fieldSetFlags()[4] = 0;
/* 533:533 */       return this;
/* 534:    */     }
/* 535:    */     
/* 536:    */     public Integer getPort()
/* 537:    */     {
/* 538:538 */       return Integer.valueOf(this.port);
/* 539:    */     }
/* 540:    */     
/* 541:    */     public Builder setPort(int value)
/* 542:    */     {
/* 543:543 */       validate(fields()[5], Integer.valueOf(value));
/* 544:544 */       this.port = value;
/* 545:545 */       fieldSetFlags()[5] = 1;
/* 546:546 */       return this;
/* 547:    */     }
/* 548:    */     
/* 549:    */     public boolean hasPort()
/* 550:    */     {
/* 551:551 */       return fieldSetFlags()[5];
/* 552:    */     }
/* 553:    */     
/* 554:    */     public Builder clearPort()
/* 555:    */     {
/* 556:556 */       fieldSetFlags()[5] = 0;
/* 557:557 */       return this;
/* 558:    */     }
/* 559:    */     
/* 560:    */     public CharSequence getRackname()
/* 561:    */     {
/* 562:562 */       return this.rackname;
/* 563:    */     }
/* 564:    */     
/* 565:    */     public Builder setRackname(CharSequence value)
/* 566:    */     {
/* 567:567 */       validate(fields()[6], value);
/* 568:568 */       this.rackname = value;
/* 569:569 */       fieldSetFlags()[6] = 1;
/* 570:570 */       return this;
/* 571:    */     }
/* 572:    */     
/* 573:    */     public boolean hasRackname()
/* 574:    */     {
/* 575:575 */       return fieldSetFlags()[6];
/* 576:    */     }
/* 577:    */     
/* 578:    */     public Builder clearRackname()
/* 579:    */     {
/* 580:580 */       this.rackname = null;
/* 581:581 */       fieldSetFlags()[6] = 0;
/* 582:582 */       return this;
/* 583:    */     }
/* 584:    */     
/* 585:    */     public CharSequence getStatus()
/* 586:    */     {
/* 587:587 */       return this.status;
/* 588:    */     }
/* 589:    */     
/* 590:    */     public Builder setStatus(CharSequence value)
/* 591:    */     {
/* 592:592 */       validate(fields()[7], value);
/* 593:593 */       this.status = value;
/* 594:594 */       fieldSetFlags()[7] = 1;
/* 595:595 */       return this;
/* 596:    */     }
/* 597:    */     
/* 598:    */     public boolean hasStatus()
/* 599:    */     {
/* 600:600 */       return fieldSetFlags()[7];
/* 601:    */     }
/* 602:    */     
/* 603:    */     public Builder clearStatus()
/* 604:    */     {
/* 605:605 */       this.status = null;
/* 606:606 */       fieldSetFlags()[7] = 0;
/* 607:607 */       return this;
/* 608:    */     }
/* 609:    */     
/* 610:    */     public CharSequence getError()
/* 611:    */     {
/* 612:612 */       return this.error;
/* 613:    */     }
/* 614:    */     
/* 615:    */     public Builder setError(CharSequence value)
/* 616:    */     {
/* 617:617 */       validate(fields()[8], value);
/* 618:618 */       this.error = value;
/* 619:619 */       fieldSetFlags()[8] = 1;
/* 620:620 */       return this;
/* 621:    */     }
/* 622:    */     
/* 623:    */     public boolean hasError()
/* 624:    */     {
/* 625:625 */       return fieldSetFlags()[8];
/* 626:    */     }
/* 627:    */     
/* 628:    */     public Builder clearError()
/* 629:    */     {
/* 630:630 */       this.error = null;
/* 631:631 */       fieldSetFlags()[8] = 0;
/* 632:632 */       return this;
/* 633:    */     }
/* 634:    */     
/* 635:    */     public JhCounters getCounters()
/* 636:    */     {
/* 637:637 */       return this.counters;
/* 638:    */     }
/* 639:    */     
/* 640:    */     public Builder setCounters(JhCounters value)
/* 641:    */     {
/* 642:642 */       validate(fields()[9], value);
/* 643:643 */       this.counters = value;
/* 644:644 */       fieldSetFlags()[9] = 1;
/* 645:645 */       return this;
/* 646:    */     }
/* 647:    */     
/* 648:    */     public boolean hasCounters()
/* 649:    */     {
/* 650:650 */       return fieldSetFlags()[9];
/* 651:    */     }
/* 652:    */     
/* 653:    */     public Builder clearCounters()
/* 654:    */     {
/* 655:655 */       this.counters = null;
/* 656:656 */       fieldSetFlags()[9] = 0;
/* 657:657 */       return this;
/* 658:    */     }
/* 659:    */     
/* 660:    */     public List<Integer> getClockSplits()
/* 661:    */     {
/* 662:662 */       return this.clockSplits;
/* 663:    */     }
/* 664:    */     
/* 665:    */     public Builder setClockSplits(List<Integer> value)
/* 666:    */     {
/* 667:667 */       validate(fields()[10], value);
/* 668:668 */       this.clockSplits = value;
/* 669:669 */       fieldSetFlags()[10] = 1;
/* 670:670 */       return this;
/* 671:    */     }
/* 672:    */     
/* 673:    */     public boolean hasClockSplits()
/* 674:    */     {
/* 675:675 */       return fieldSetFlags()[10];
/* 676:    */     }
/* 677:    */     
/* 678:    */     public Builder clearClockSplits()
/* 679:    */     {
/* 680:680 */       this.clockSplits = null;
/* 681:681 */       fieldSetFlags()[10] = 0;
/* 682:682 */       return this;
/* 683:    */     }
/* 684:    */     
/* 685:    */     public List<Integer> getCpuUsages()
/* 686:    */     {
/* 687:687 */       return this.cpuUsages;
/* 688:    */     }
/* 689:    */     
/* 690:    */     public Builder setCpuUsages(List<Integer> value)
/* 691:    */     {
/* 692:692 */       validate(fields()[11], value);
/* 693:693 */       this.cpuUsages = value;
/* 694:694 */       fieldSetFlags()[11] = 1;
/* 695:695 */       return this;
/* 696:    */     }
/* 697:    */     
/* 698:    */     public boolean hasCpuUsages()
/* 699:    */     {
/* 700:700 */       return fieldSetFlags()[11];
/* 701:    */     }
/* 702:    */     
/* 703:    */     public Builder clearCpuUsages()
/* 704:    */     {
/* 705:705 */       this.cpuUsages = null;
/* 706:706 */       fieldSetFlags()[11] = 0;
/* 707:707 */       return this;
/* 708:    */     }
/* 709:    */     
/* 710:    */     public List<Integer> getVMemKbytes()
/* 711:    */     {
/* 712:712 */       return this.vMemKbytes;
/* 713:    */     }
/* 714:    */     
/* 715:    */     public Builder setVMemKbytes(List<Integer> value)
/* 716:    */     {
/* 717:717 */       validate(fields()[12], value);
/* 718:718 */       this.vMemKbytes = value;
/* 719:719 */       fieldSetFlags()[12] = 1;
/* 720:720 */       return this;
/* 721:    */     }
/* 722:    */     
/* 723:    */     public boolean hasVMemKbytes()
/* 724:    */     {
/* 725:725 */       return fieldSetFlags()[12];
/* 726:    */     }
/* 727:    */     
/* 728:    */     public Builder clearVMemKbytes()
/* 729:    */     {
/* 730:730 */       this.vMemKbytes = null;
/* 731:731 */       fieldSetFlags()[12] = 0;
/* 732:732 */       return this;
/* 733:    */     }
/* 734:    */     
/* 735:    */     public List<Integer> getPhysMemKbytes()
/* 736:    */     {
/* 737:737 */       return this.physMemKbytes;
/* 738:    */     }
/* 739:    */     
/* 740:    */     public Builder setPhysMemKbytes(List<Integer> value)
/* 741:    */     {
/* 742:742 */       validate(fields()[13], value);
/* 743:743 */       this.physMemKbytes = value;
/* 744:744 */       fieldSetFlags()[13] = 1;
/* 745:745 */       return this;
/* 746:    */     }
/* 747:    */     
/* 748:    */     public boolean hasPhysMemKbytes()
/* 749:    */     {
/* 750:750 */       return fieldSetFlags()[13];
/* 751:    */     }
/* 752:    */     
/* 753:    */     public Builder clearPhysMemKbytes()
/* 754:    */     {
/* 755:755 */       this.physMemKbytes = null;
/* 756:756 */       fieldSetFlags()[13] = 0;
/* 757:757 */       return this;
/* 758:    */     }
/* 759:    */     
/* 760:    */     public TaskAttemptUnsuccessfulCompletion build()
/* 761:    */     {
/* 762:    */       try
/* 763:    */       {
/* 764:763 */         TaskAttemptUnsuccessfulCompletion record = new TaskAttemptUnsuccessfulCompletion();
/* 765:764 */         record.taskid = (fieldSetFlags()[0] != 0 ? this.taskid : (CharSequence)defaultValue(fields()[0]));
/* 766:765 */         record.taskType = (fieldSetFlags()[1] != 0 ? this.taskType : (CharSequence)defaultValue(fields()[1]));
/* 767:766 */         record.attemptId = (fieldSetFlags()[2] != 0 ? this.attemptId : (CharSequence)defaultValue(fields()[2]));
/* 768:767 */         record.finishTime = (fieldSetFlags()[3] != 0 ? this.finishTime : ((Long)defaultValue(fields()[3])).longValue());
/* 769:768 */         record.hostname = (fieldSetFlags()[4] != 0 ? this.hostname : (CharSequence)defaultValue(fields()[4]));
/* 770:769 */         record.port = (fieldSetFlags()[5] != 0 ? this.port : ((Integer)defaultValue(fields()[5])).intValue());
/* 771:770 */         record.rackname = (fieldSetFlags()[6] != 0 ? this.rackname : (CharSequence)defaultValue(fields()[6]));
/* 772:771 */         record.status = (fieldSetFlags()[7] != 0 ? this.status : (CharSequence)defaultValue(fields()[7]));
/* 773:772 */         record.error = (fieldSetFlags()[8] != 0 ? this.error : (CharSequence)defaultValue(fields()[8]));
/* 774:773 */         record.counters = (fieldSetFlags()[9] != 0 ? this.counters : (JhCounters)defaultValue(fields()[9]));
/* 775:774 */         record.clockSplits = (fieldSetFlags()[10] != 0 ? this.clockSplits : (List)defaultValue(fields()[10]));
/* 776:775 */         record.cpuUsages = (fieldSetFlags()[11] != 0 ? this.cpuUsages : (List)defaultValue(fields()[11]));
/* 777:776 */         record.vMemKbytes = (fieldSetFlags()[12] != 0 ? this.vMemKbytes : (List)defaultValue(fields()[12]));
/* 778:777 */         record.physMemKbytes = (fieldSetFlags()[13] != 0 ? this.physMemKbytes : (List)defaultValue(fields()[13]));
/* 779:778 */         return record;
/* 780:    */       }
/* 781:    */       catch (Exception e)
/* 782:    */       {
/* 783:780 */         throw new AvroRuntimeException(e);
/* 784:    */       }
/* 785:    */     }
/* 786:    */   }
/* 787:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.jobhistory.TaskAttemptUnsuccessfulCompletion
 * JD-Core Version:    0.7.0.1
 */