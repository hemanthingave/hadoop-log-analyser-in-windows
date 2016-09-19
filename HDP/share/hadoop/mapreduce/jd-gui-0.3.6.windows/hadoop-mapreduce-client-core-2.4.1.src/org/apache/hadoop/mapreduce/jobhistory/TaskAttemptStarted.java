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
/*  15:    */ public class TaskAttemptStarted
/*  16:    */   extends SpecificRecordBase
/*  17:    */   implements SpecificRecord
/*  18:    */ {
/*  19: 10 */   public static final Schema SCHEMA$ = new Schema.Parser().parse("{\"type\":\"record\",\"name\":\"TaskAttemptStarted\",\"namespace\":\"org.apache.hadoop.mapreduce.jobhistory\",\"fields\":[{\"name\":\"taskid\",\"type\":\"string\"},{\"name\":\"taskType\",\"type\":\"string\"},{\"name\":\"attemptId\",\"type\":\"string\"},{\"name\":\"startTime\",\"type\":\"long\"},{\"name\":\"trackerName\",\"type\":\"string\"},{\"name\":\"httpPort\",\"type\":\"int\"},{\"name\":\"shufflePort\",\"type\":\"int\"},{\"name\":\"containerId\",\"type\":\"string\"},{\"name\":\"locality\",\"type\":\"string\"},{\"name\":\"avataar\",\"type\":\"string\"}]}");
/*  20:    */   @Deprecated
/*  21:    */   public CharSequence taskid;
/*  22:    */   @Deprecated
/*  23:    */   public CharSequence taskType;
/*  24:    */   @Deprecated
/*  25:    */   public CharSequence attemptId;
/*  26:    */   @Deprecated
/*  27:    */   public long startTime;
/*  28:    */   @Deprecated
/*  29:    */   public CharSequence trackerName;
/*  30:    */   @Deprecated
/*  31:    */   public int httpPort;
/*  32:    */   @Deprecated
/*  33:    */   public int shufflePort;
/*  34:    */   @Deprecated
/*  35:    */   public CharSequence containerId;
/*  36:    */   @Deprecated
/*  37:    */   public CharSequence locality;
/*  38:    */   @Deprecated
/*  39:    */   public CharSequence avataar;
/*  40:    */   
/*  41:    */   public static Schema getClassSchema()
/*  42:    */   {
/*  43: 11 */     return SCHEMA$;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public TaskAttemptStarted() {}
/*  47:    */   
/*  48:    */   public TaskAttemptStarted(CharSequence taskid, CharSequence taskType, CharSequence attemptId, Long startTime, CharSequence trackerName, Integer httpPort, Integer shufflePort, CharSequence containerId, CharSequence locality, CharSequence avataar)
/*  49:    */   {
/*  50: 32 */     this.taskid = taskid;
/*  51: 33 */     this.taskType = taskType;
/*  52: 34 */     this.attemptId = attemptId;
/*  53: 35 */     this.startTime = startTime.longValue();
/*  54: 36 */     this.trackerName = trackerName;
/*  55: 37 */     this.httpPort = httpPort.intValue();
/*  56: 38 */     this.shufflePort = shufflePort.intValue();
/*  57: 39 */     this.containerId = containerId;
/*  58: 40 */     this.locality = locality;
/*  59: 41 */     this.avataar = avataar;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public Schema getSchema()
/*  63:    */   {
/*  64: 44 */     return SCHEMA$;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public Object get(int field$)
/*  68:    */   {
/*  69: 47 */     switch (field$)
/*  70:    */     {
/*  71:    */     case 0: 
/*  72: 48 */       return this.taskid;
/*  73:    */     case 1: 
/*  74: 49 */       return this.taskType;
/*  75:    */     case 2: 
/*  76: 50 */       return this.attemptId;
/*  77:    */     case 3: 
/*  78: 51 */       return Long.valueOf(this.startTime);
/*  79:    */     case 4: 
/*  80: 52 */       return this.trackerName;
/*  81:    */     case 5: 
/*  82: 53 */       return Integer.valueOf(this.httpPort);
/*  83:    */     case 6: 
/*  84: 54 */       return Integer.valueOf(this.shufflePort);
/*  85:    */     case 7: 
/*  86: 55 */       return this.containerId;
/*  87:    */     case 8: 
/*  88: 56 */       return this.locality;
/*  89:    */     case 9: 
/*  90: 57 */       return this.avataar;
/*  91:    */     }
/*  92: 58 */     throw new AvroRuntimeException("Bad index");
/*  93:    */   }
/*  94:    */   
/*  95:    */   public void put(int field$, Object value$)
/*  96:    */   {
/*  97: 64 */     switch (field$)
/*  98:    */     {
/*  99:    */     case 0: 
/* 100: 65 */       this.taskid = ((CharSequence)value$); break;
/* 101:    */     case 1: 
/* 102: 66 */       this.taskType = ((CharSequence)value$); break;
/* 103:    */     case 2: 
/* 104: 67 */       this.attemptId = ((CharSequence)value$); break;
/* 105:    */     case 3: 
/* 106: 68 */       this.startTime = ((Long)value$).longValue(); break;
/* 107:    */     case 4: 
/* 108: 69 */       this.trackerName = ((CharSequence)value$); break;
/* 109:    */     case 5: 
/* 110: 70 */       this.httpPort = ((Integer)value$).intValue(); break;
/* 111:    */     case 6: 
/* 112: 71 */       this.shufflePort = ((Integer)value$).intValue(); break;
/* 113:    */     case 7: 
/* 114: 72 */       this.containerId = ((CharSequence)value$); break;
/* 115:    */     case 8: 
/* 116: 73 */       this.locality = ((CharSequence)value$); break;
/* 117:    */     case 9: 
/* 118: 74 */       this.avataar = ((CharSequence)value$); break;
/* 119:    */     default: 
/* 120: 75 */       throw new AvroRuntimeException("Bad index");
/* 121:    */     }
/* 122:    */   }
/* 123:    */   
/* 124:    */   public CharSequence getTaskid()
/* 125:    */   {
/* 126: 83 */     return this.taskid;
/* 127:    */   }
/* 128:    */   
/* 129:    */   public void setTaskid(CharSequence value)
/* 130:    */   {
/* 131: 91 */     this.taskid = value;
/* 132:    */   }
/* 133:    */   
/* 134:    */   public CharSequence getTaskType()
/* 135:    */   {
/* 136: 98 */     return this.taskType;
/* 137:    */   }
/* 138:    */   
/* 139:    */   public void setTaskType(CharSequence value)
/* 140:    */   {
/* 141:106 */     this.taskType = value;
/* 142:    */   }
/* 143:    */   
/* 144:    */   public CharSequence getAttemptId()
/* 145:    */   {
/* 146:113 */     return this.attemptId;
/* 147:    */   }
/* 148:    */   
/* 149:    */   public void setAttemptId(CharSequence value)
/* 150:    */   {
/* 151:121 */     this.attemptId = value;
/* 152:    */   }
/* 153:    */   
/* 154:    */   public Long getStartTime()
/* 155:    */   {
/* 156:128 */     return Long.valueOf(this.startTime);
/* 157:    */   }
/* 158:    */   
/* 159:    */   public void setStartTime(Long value)
/* 160:    */   {
/* 161:136 */     this.startTime = value.longValue();
/* 162:    */   }
/* 163:    */   
/* 164:    */   public CharSequence getTrackerName()
/* 165:    */   {
/* 166:143 */     return this.trackerName;
/* 167:    */   }
/* 168:    */   
/* 169:    */   public void setTrackerName(CharSequence value)
/* 170:    */   {
/* 171:151 */     this.trackerName = value;
/* 172:    */   }
/* 173:    */   
/* 174:    */   public Integer getHttpPort()
/* 175:    */   {
/* 176:158 */     return Integer.valueOf(this.httpPort);
/* 177:    */   }
/* 178:    */   
/* 179:    */   public void setHttpPort(Integer value)
/* 180:    */   {
/* 181:166 */     this.httpPort = value.intValue();
/* 182:    */   }
/* 183:    */   
/* 184:    */   public Integer getShufflePort()
/* 185:    */   {
/* 186:173 */     return Integer.valueOf(this.shufflePort);
/* 187:    */   }
/* 188:    */   
/* 189:    */   public void setShufflePort(Integer value)
/* 190:    */   {
/* 191:181 */     this.shufflePort = value.intValue();
/* 192:    */   }
/* 193:    */   
/* 194:    */   public CharSequence getContainerId()
/* 195:    */   {
/* 196:188 */     return this.containerId;
/* 197:    */   }
/* 198:    */   
/* 199:    */   public void setContainerId(CharSequence value)
/* 200:    */   {
/* 201:196 */     this.containerId = value;
/* 202:    */   }
/* 203:    */   
/* 204:    */   public CharSequence getLocality()
/* 205:    */   {
/* 206:203 */     return this.locality;
/* 207:    */   }
/* 208:    */   
/* 209:    */   public void setLocality(CharSequence value)
/* 210:    */   {
/* 211:211 */     this.locality = value;
/* 212:    */   }
/* 213:    */   
/* 214:    */   public CharSequence getAvataar()
/* 215:    */   {
/* 216:218 */     return this.avataar;
/* 217:    */   }
/* 218:    */   
/* 219:    */   public void setAvataar(CharSequence value)
/* 220:    */   {
/* 221:226 */     this.avataar = value;
/* 222:    */   }
/* 223:    */   
/* 224:    */   public static Builder newBuilder()
/* 225:    */   {
/* 226:231 */     return new Builder(null);
/* 227:    */   }
/* 228:    */   
/* 229:    */   public static Builder newBuilder(Builder other)
/* 230:    */   {
/* 231:236 */     return new Builder(other, null);
/* 232:    */   }
/* 233:    */   
/* 234:    */   public static Builder newBuilder(TaskAttemptStarted other)
/* 235:    */   {
/* 236:241 */     return new Builder(other, null);
/* 237:    */   }
/* 238:    */   
/* 239:    */   public static class Builder
/* 240:    */     extends SpecificRecordBuilderBase<TaskAttemptStarted>
/* 241:    */     implements RecordBuilder<TaskAttemptStarted>
/* 242:    */   {
/* 243:    */     private CharSequence taskid;
/* 244:    */     private CharSequence taskType;
/* 245:    */     private CharSequence attemptId;
/* 246:    */     private long startTime;
/* 247:    */     private CharSequence trackerName;
/* 248:    */     private int httpPort;
/* 249:    */     private int shufflePort;
/* 250:    */     private CharSequence containerId;
/* 251:    */     private CharSequence locality;
/* 252:    */     private CharSequence avataar;
/* 253:    */     
/* 254:    */     private Builder()
/* 255:    */     {
/* 256:263 */       super();
/* 257:    */     }
/* 258:    */     
/* 259:    */     private Builder(Builder other)
/* 260:    */     {
/* 261:268 */       super();
/* 262:    */     }
/* 263:    */     
/* 264:    */     private Builder(TaskAttemptStarted other)
/* 265:    */     {
/* 266:273 */       super();
/* 267:274 */       if (isValidValue(fields()[0], other.taskid))
/* 268:    */       {
/* 269:275 */         this.taskid = ((CharSequence)data().deepCopy(fields()[0].schema(), other.taskid));
/* 270:276 */         fieldSetFlags()[0] = 1;
/* 271:    */       }
/* 272:278 */       if (isValidValue(fields()[1], other.taskType))
/* 273:    */       {
/* 274:279 */         this.taskType = ((CharSequence)data().deepCopy(fields()[1].schema(), other.taskType));
/* 275:280 */         fieldSetFlags()[1] = 1;
/* 276:    */       }
/* 277:282 */       if (isValidValue(fields()[2], other.attemptId))
/* 278:    */       {
/* 279:283 */         this.attemptId = ((CharSequence)data().deepCopy(fields()[2].schema(), other.attemptId));
/* 280:284 */         fieldSetFlags()[2] = 1;
/* 281:    */       }
/* 282:286 */       if (isValidValue(fields()[3], Long.valueOf(other.startTime)))
/* 283:    */       {
/* 284:287 */         this.startTime = ((Long)data().deepCopy(fields()[3].schema(), Long.valueOf(other.startTime))).longValue();
/* 285:288 */         fieldSetFlags()[3] = 1;
/* 286:    */       }
/* 287:290 */       if (isValidValue(fields()[4], other.trackerName))
/* 288:    */       {
/* 289:291 */         this.trackerName = ((CharSequence)data().deepCopy(fields()[4].schema(), other.trackerName));
/* 290:292 */         fieldSetFlags()[4] = 1;
/* 291:    */       }
/* 292:294 */       if (isValidValue(fields()[5], Integer.valueOf(other.httpPort)))
/* 293:    */       {
/* 294:295 */         this.httpPort = ((Integer)data().deepCopy(fields()[5].schema(), Integer.valueOf(other.httpPort))).intValue();
/* 295:296 */         fieldSetFlags()[5] = 1;
/* 296:    */       }
/* 297:298 */       if (isValidValue(fields()[6], Integer.valueOf(other.shufflePort)))
/* 298:    */       {
/* 299:299 */         this.shufflePort = ((Integer)data().deepCopy(fields()[6].schema(), Integer.valueOf(other.shufflePort))).intValue();
/* 300:300 */         fieldSetFlags()[6] = 1;
/* 301:    */       }
/* 302:302 */       if (isValidValue(fields()[7], other.containerId))
/* 303:    */       {
/* 304:303 */         this.containerId = ((CharSequence)data().deepCopy(fields()[7].schema(), other.containerId));
/* 305:304 */         fieldSetFlags()[7] = 1;
/* 306:    */       }
/* 307:306 */       if (isValidValue(fields()[8], other.locality))
/* 308:    */       {
/* 309:307 */         this.locality = ((CharSequence)data().deepCopy(fields()[8].schema(), other.locality));
/* 310:308 */         fieldSetFlags()[8] = 1;
/* 311:    */       }
/* 312:310 */       if (isValidValue(fields()[9], other.avataar))
/* 313:    */       {
/* 314:311 */         this.avataar = ((CharSequence)data().deepCopy(fields()[9].schema(), other.avataar));
/* 315:312 */         fieldSetFlags()[9] = 1;
/* 316:    */       }
/* 317:    */     }
/* 318:    */     
/* 319:    */     public CharSequence getTaskid()
/* 320:    */     {
/* 321:318 */       return this.taskid;
/* 322:    */     }
/* 323:    */     
/* 324:    */     public Builder setTaskid(CharSequence value)
/* 325:    */     {
/* 326:323 */       validate(fields()[0], value);
/* 327:324 */       this.taskid = value;
/* 328:325 */       fieldSetFlags()[0] = 1;
/* 329:326 */       return this;
/* 330:    */     }
/* 331:    */     
/* 332:    */     public boolean hasTaskid()
/* 333:    */     {
/* 334:331 */       return fieldSetFlags()[0];
/* 335:    */     }
/* 336:    */     
/* 337:    */     public Builder clearTaskid()
/* 338:    */     {
/* 339:336 */       this.taskid = null;
/* 340:337 */       fieldSetFlags()[0] = 0;
/* 341:338 */       return this;
/* 342:    */     }
/* 343:    */     
/* 344:    */     public CharSequence getTaskType()
/* 345:    */     {
/* 346:343 */       return this.taskType;
/* 347:    */     }
/* 348:    */     
/* 349:    */     public Builder setTaskType(CharSequence value)
/* 350:    */     {
/* 351:348 */       validate(fields()[1], value);
/* 352:349 */       this.taskType = value;
/* 353:350 */       fieldSetFlags()[1] = 1;
/* 354:351 */       return this;
/* 355:    */     }
/* 356:    */     
/* 357:    */     public boolean hasTaskType()
/* 358:    */     {
/* 359:356 */       return fieldSetFlags()[1];
/* 360:    */     }
/* 361:    */     
/* 362:    */     public Builder clearTaskType()
/* 363:    */     {
/* 364:361 */       this.taskType = null;
/* 365:362 */       fieldSetFlags()[1] = 0;
/* 366:363 */       return this;
/* 367:    */     }
/* 368:    */     
/* 369:    */     public CharSequence getAttemptId()
/* 370:    */     {
/* 371:368 */       return this.attemptId;
/* 372:    */     }
/* 373:    */     
/* 374:    */     public Builder setAttemptId(CharSequence value)
/* 375:    */     {
/* 376:373 */       validate(fields()[2], value);
/* 377:374 */       this.attemptId = value;
/* 378:375 */       fieldSetFlags()[2] = 1;
/* 379:376 */       return this;
/* 380:    */     }
/* 381:    */     
/* 382:    */     public boolean hasAttemptId()
/* 383:    */     {
/* 384:381 */       return fieldSetFlags()[2];
/* 385:    */     }
/* 386:    */     
/* 387:    */     public Builder clearAttemptId()
/* 388:    */     {
/* 389:386 */       this.attemptId = null;
/* 390:387 */       fieldSetFlags()[2] = 0;
/* 391:388 */       return this;
/* 392:    */     }
/* 393:    */     
/* 394:    */     public Long getStartTime()
/* 395:    */     {
/* 396:393 */       return Long.valueOf(this.startTime);
/* 397:    */     }
/* 398:    */     
/* 399:    */     public Builder setStartTime(long value)
/* 400:    */     {
/* 401:398 */       validate(fields()[3], Long.valueOf(value));
/* 402:399 */       this.startTime = value;
/* 403:400 */       fieldSetFlags()[3] = 1;
/* 404:401 */       return this;
/* 405:    */     }
/* 406:    */     
/* 407:    */     public boolean hasStartTime()
/* 408:    */     {
/* 409:406 */       return fieldSetFlags()[3];
/* 410:    */     }
/* 411:    */     
/* 412:    */     public Builder clearStartTime()
/* 413:    */     {
/* 414:411 */       fieldSetFlags()[3] = 0;
/* 415:412 */       return this;
/* 416:    */     }
/* 417:    */     
/* 418:    */     public CharSequence getTrackerName()
/* 419:    */     {
/* 420:417 */       return this.trackerName;
/* 421:    */     }
/* 422:    */     
/* 423:    */     public Builder setTrackerName(CharSequence value)
/* 424:    */     {
/* 425:422 */       validate(fields()[4], value);
/* 426:423 */       this.trackerName = value;
/* 427:424 */       fieldSetFlags()[4] = 1;
/* 428:425 */       return this;
/* 429:    */     }
/* 430:    */     
/* 431:    */     public boolean hasTrackerName()
/* 432:    */     {
/* 433:430 */       return fieldSetFlags()[4];
/* 434:    */     }
/* 435:    */     
/* 436:    */     public Builder clearTrackerName()
/* 437:    */     {
/* 438:435 */       this.trackerName = null;
/* 439:436 */       fieldSetFlags()[4] = 0;
/* 440:437 */       return this;
/* 441:    */     }
/* 442:    */     
/* 443:    */     public Integer getHttpPort()
/* 444:    */     {
/* 445:442 */       return Integer.valueOf(this.httpPort);
/* 446:    */     }
/* 447:    */     
/* 448:    */     public Builder setHttpPort(int value)
/* 449:    */     {
/* 450:447 */       validate(fields()[5], Integer.valueOf(value));
/* 451:448 */       this.httpPort = value;
/* 452:449 */       fieldSetFlags()[5] = 1;
/* 453:450 */       return this;
/* 454:    */     }
/* 455:    */     
/* 456:    */     public boolean hasHttpPort()
/* 457:    */     {
/* 458:455 */       return fieldSetFlags()[5];
/* 459:    */     }
/* 460:    */     
/* 461:    */     public Builder clearHttpPort()
/* 462:    */     {
/* 463:460 */       fieldSetFlags()[5] = 0;
/* 464:461 */       return this;
/* 465:    */     }
/* 466:    */     
/* 467:    */     public Integer getShufflePort()
/* 468:    */     {
/* 469:466 */       return Integer.valueOf(this.shufflePort);
/* 470:    */     }
/* 471:    */     
/* 472:    */     public Builder setShufflePort(int value)
/* 473:    */     {
/* 474:471 */       validate(fields()[6], Integer.valueOf(value));
/* 475:472 */       this.shufflePort = value;
/* 476:473 */       fieldSetFlags()[6] = 1;
/* 477:474 */       return this;
/* 478:    */     }
/* 479:    */     
/* 480:    */     public boolean hasShufflePort()
/* 481:    */     {
/* 482:479 */       return fieldSetFlags()[6];
/* 483:    */     }
/* 484:    */     
/* 485:    */     public Builder clearShufflePort()
/* 486:    */     {
/* 487:484 */       fieldSetFlags()[6] = 0;
/* 488:485 */       return this;
/* 489:    */     }
/* 490:    */     
/* 491:    */     public CharSequence getContainerId()
/* 492:    */     {
/* 493:490 */       return this.containerId;
/* 494:    */     }
/* 495:    */     
/* 496:    */     public Builder setContainerId(CharSequence value)
/* 497:    */     {
/* 498:495 */       validate(fields()[7], value);
/* 499:496 */       this.containerId = value;
/* 500:497 */       fieldSetFlags()[7] = 1;
/* 501:498 */       return this;
/* 502:    */     }
/* 503:    */     
/* 504:    */     public boolean hasContainerId()
/* 505:    */     {
/* 506:503 */       return fieldSetFlags()[7];
/* 507:    */     }
/* 508:    */     
/* 509:    */     public Builder clearContainerId()
/* 510:    */     {
/* 511:508 */       this.containerId = null;
/* 512:509 */       fieldSetFlags()[7] = 0;
/* 513:510 */       return this;
/* 514:    */     }
/* 515:    */     
/* 516:    */     public CharSequence getLocality()
/* 517:    */     {
/* 518:515 */       return this.locality;
/* 519:    */     }
/* 520:    */     
/* 521:    */     public Builder setLocality(CharSequence value)
/* 522:    */     {
/* 523:520 */       validate(fields()[8], value);
/* 524:521 */       this.locality = value;
/* 525:522 */       fieldSetFlags()[8] = 1;
/* 526:523 */       return this;
/* 527:    */     }
/* 528:    */     
/* 529:    */     public boolean hasLocality()
/* 530:    */     {
/* 531:528 */       return fieldSetFlags()[8];
/* 532:    */     }
/* 533:    */     
/* 534:    */     public Builder clearLocality()
/* 535:    */     {
/* 536:533 */       this.locality = null;
/* 537:534 */       fieldSetFlags()[8] = 0;
/* 538:535 */       return this;
/* 539:    */     }
/* 540:    */     
/* 541:    */     public CharSequence getAvataar()
/* 542:    */     {
/* 543:540 */       return this.avataar;
/* 544:    */     }
/* 545:    */     
/* 546:    */     public Builder setAvataar(CharSequence value)
/* 547:    */     {
/* 548:545 */       validate(fields()[9], value);
/* 549:546 */       this.avataar = value;
/* 550:547 */       fieldSetFlags()[9] = 1;
/* 551:548 */       return this;
/* 552:    */     }
/* 553:    */     
/* 554:    */     public boolean hasAvataar()
/* 555:    */     {
/* 556:553 */       return fieldSetFlags()[9];
/* 557:    */     }
/* 558:    */     
/* 559:    */     public Builder clearAvataar()
/* 560:    */     {
/* 561:558 */       this.avataar = null;
/* 562:559 */       fieldSetFlags()[9] = 0;
/* 563:560 */       return this;
/* 564:    */     }
/* 565:    */     
/* 566:    */     public TaskAttemptStarted build()
/* 567:    */     {
/* 568:    */       try
/* 569:    */       {
/* 570:566 */         TaskAttemptStarted record = new TaskAttemptStarted();
/* 571:567 */         record.taskid = (fieldSetFlags()[0] != 0 ? this.taskid : (CharSequence)defaultValue(fields()[0]));
/* 572:568 */         record.taskType = (fieldSetFlags()[1] != 0 ? this.taskType : (CharSequence)defaultValue(fields()[1]));
/* 573:569 */         record.attemptId = (fieldSetFlags()[2] != 0 ? this.attemptId : (CharSequence)defaultValue(fields()[2]));
/* 574:570 */         record.startTime = (fieldSetFlags()[3] != 0 ? this.startTime : ((Long)defaultValue(fields()[3])).longValue());
/* 575:571 */         record.trackerName = (fieldSetFlags()[4] != 0 ? this.trackerName : (CharSequence)defaultValue(fields()[4]));
/* 576:572 */         record.httpPort = (fieldSetFlags()[5] != 0 ? this.httpPort : ((Integer)defaultValue(fields()[5])).intValue());
/* 577:573 */         record.shufflePort = (fieldSetFlags()[6] != 0 ? this.shufflePort : ((Integer)defaultValue(fields()[6])).intValue());
/* 578:574 */         record.containerId = (fieldSetFlags()[7] != 0 ? this.containerId : (CharSequence)defaultValue(fields()[7]));
/* 579:575 */         record.locality = (fieldSetFlags()[8] != 0 ? this.locality : (CharSequence)defaultValue(fields()[8]));
/* 580:576 */         record.avataar = (fieldSetFlags()[9] != 0 ? this.avataar : (CharSequence)defaultValue(fields()[9]));
/* 581:577 */         return record;
/* 582:    */       }
/* 583:    */       catch (Exception e)
/* 584:    */       {
/* 585:579 */         throw new AvroRuntimeException(e);
/* 586:    */       }
/* 587:    */     }
/* 588:    */   }
/* 589:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.jobhistory.TaskAttemptStarted
 * JD-Core Version:    0.7.0.1
 */