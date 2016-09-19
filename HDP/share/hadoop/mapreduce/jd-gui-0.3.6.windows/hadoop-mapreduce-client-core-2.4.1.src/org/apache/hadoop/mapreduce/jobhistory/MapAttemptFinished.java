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
/*  16:    */ public class MapAttemptFinished
/*  17:    */   extends SpecificRecordBase
/*  18:    */   implements SpecificRecord
/*  19:    */ {
/*  20: 10 */   public static final Schema SCHEMA$ = new Schema.Parser().parse("{\"type\":\"record\",\"name\":\"MapAttemptFinished\",\"namespace\":\"org.apache.hadoop.mapreduce.jobhistory\",\"fields\":[{\"name\":\"taskid\",\"type\":\"string\"},{\"name\":\"attemptId\",\"type\":\"string\"},{\"name\":\"taskType\",\"type\":\"string\"},{\"name\":\"taskStatus\",\"type\":\"string\"},{\"name\":\"mapFinishTime\",\"type\":\"long\"},{\"name\":\"finishTime\",\"type\":\"long\"},{\"name\":\"hostname\",\"type\":\"string\"},{\"name\":\"port\",\"type\":\"int\"},{\"name\":\"rackname\",\"type\":\"string\"},{\"name\":\"state\",\"type\":\"string\"},{\"name\":\"counters\",\"type\":{\"type\":\"record\",\"name\":\"JhCounters\",\"fields\":[{\"name\":\"name\",\"type\":\"string\"},{\"name\":\"groups\",\"type\":{\"type\":\"array\",\"items\":{\"type\":\"record\",\"name\":\"JhCounterGroup\",\"fields\":[{\"name\":\"name\",\"type\":\"string\"},{\"name\":\"displayName\",\"type\":\"string\"},{\"name\":\"counts\",\"type\":{\"type\":\"array\",\"items\":{\"type\":\"record\",\"name\":\"JhCounter\",\"fields\":[{\"name\":\"name\",\"type\":\"string\"},{\"name\":\"displayName\",\"type\":\"string\"},{\"name\":\"value\",\"type\":\"long\"}]}}}]}}}]}},{\"name\":\"clockSplits\",\"type\":{\"type\":\"array\",\"items\":\"int\"}},{\"name\":\"cpuUsages\",\"type\":{\"type\":\"array\",\"items\":\"int\"}},{\"name\":\"vMemKbytes\",\"type\":{\"type\":\"array\",\"items\":\"int\"}},{\"name\":\"physMemKbytes\",\"type\":{\"type\":\"array\",\"items\":\"int\"}}]}");
/*  21:    */   @Deprecated
/*  22:    */   public CharSequence taskid;
/*  23:    */   @Deprecated
/*  24:    */   public CharSequence attemptId;
/*  25:    */   @Deprecated
/*  26:    */   public CharSequence taskType;
/*  27:    */   @Deprecated
/*  28:    */   public CharSequence taskStatus;
/*  29:    */   @Deprecated
/*  30:    */   public long mapFinishTime;
/*  31:    */   @Deprecated
/*  32:    */   public long finishTime;
/*  33:    */   @Deprecated
/*  34:    */   public CharSequence hostname;
/*  35:    */   @Deprecated
/*  36:    */   public int port;
/*  37:    */   @Deprecated
/*  38:    */   public CharSequence rackname;
/*  39:    */   @Deprecated
/*  40:    */   public CharSequence state;
/*  41:    */   @Deprecated
/*  42:    */   public JhCounters counters;
/*  43:    */   @Deprecated
/*  44:    */   public List<Integer> clockSplits;
/*  45:    */   @Deprecated
/*  46:    */   public List<Integer> cpuUsages;
/*  47:    */   @Deprecated
/*  48:    */   public List<Integer> vMemKbytes;
/*  49:    */   @Deprecated
/*  50:    */   public List<Integer> physMemKbytes;
/*  51:    */   
/*  52:    */   public static Schema getClassSchema()
/*  53:    */   {
/*  54: 11 */     return SCHEMA$;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public MapAttemptFinished() {}
/*  58:    */   
/*  59:    */   public MapAttemptFinished(CharSequence taskid, CharSequence attemptId, CharSequence taskType, CharSequence taskStatus, Long mapFinishTime, Long finishTime, CharSequence hostname, Integer port, CharSequence rackname, CharSequence state, JhCounters counters, List<Integer> clockSplits, List<Integer> cpuUsages, List<Integer> vMemKbytes, List<Integer> physMemKbytes)
/*  60:    */   {
/*  61: 37 */     this.taskid = taskid;
/*  62: 38 */     this.attemptId = attemptId;
/*  63: 39 */     this.taskType = taskType;
/*  64: 40 */     this.taskStatus = taskStatus;
/*  65: 41 */     this.mapFinishTime = mapFinishTime.longValue();
/*  66: 42 */     this.finishTime = finishTime.longValue();
/*  67: 43 */     this.hostname = hostname;
/*  68: 44 */     this.port = port.intValue();
/*  69: 45 */     this.rackname = rackname;
/*  70: 46 */     this.state = state;
/*  71: 47 */     this.counters = counters;
/*  72: 48 */     this.clockSplits = clockSplits;
/*  73: 49 */     this.cpuUsages = cpuUsages;
/*  74: 50 */     this.vMemKbytes = vMemKbytes;
/*  75: 51 */     this.physMemKbytes = physMemKbytes;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public Schema getSchema()
/*  79:    */   {
/*  80: 54 */     return SCHEMA$;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public Object get(int field$)
/*  84:    */   {
/*  85: 57 */     switch (field$)
/*  86:    */     {
/*  87:    */     case 0: 
/*  88: 58 */       return this.taskid;
/*  89:    */     case 1: 
/*  90: 59 */       return this.attemptId;
/*  91:    */     case 2: 
/*  92: 60 */       return this.taskType;
/*  93:    */     case 3: 
/*  94: 61 */       return this.taskStatus;
/*  95:    */     case 4: 
/*  96: 62 */       return Long.valueOf(this.mapFinishTime);
/*  97:    */     case 5: 
/*  98: 63 */       return Long.valueOf(this.finishTime);
/*  99:    */     case 6: 
/* 100: 64 */       return this.hostname;
/* 101:    */     case 7: 
/* 102: 65 */       return Integer.valueOf(this.port);
/* 103:    */     case 8: 
/* 104: 66 */       return this.rackname;
/* 105:    */     case 9: 
/* 106: 67 */       return this.state;
/* 107:    */     case 10: 
/* 108: 68 */       return this.counters;
/* 109:    */     case 11: 
/* 110: 69 */       return this.clockSplits;
/* 111:    */     case 12: 
/* 112: 70 */       return this.cpuUsages;
/* 113:    */     case 13: 
/* 114: 71 */       return this.vMemKbytes;
/* 115:    */     case 14: 
/* 116: 72 */       return this.physMemKbytes;
/* 117:    */     }
/* 118: 73 */     throw new AvroRuntimeException("Bad index");
/* 119:    */   }
/* 120:    */   
/* 121:    */   public void put(int field$, Object value$)
/* 122:    */   {
/* 123: 79 */     switch (field$)
/* 124:    */     {
/* 125:    */     case 0: 
/* 126: 80 */       this.taskid = ((CharSequence)value$); break;
/* 127:    */     case 1: 
/* 128: 81 */       this.attemptId = ((CharSequence)value$); break;
/* 129:    */     case 2: 
/* 130: 82 */       this.taskType = ((CharSequence)value$); break;
/* 131:    */     case 3: 
/* 132: 83 */       this.taskStatus = ((CharSequence)value$); break;
/* 133:    */     case 4: 
/* 134: 84 */       this.mapFinishTime = ((Long)value$).longValue(); break;
/* 135:    */     case 5: 
/* 136: 85 */       this.finishTime = ((Long)value$).longValue(); break;
/* 137:    */     case 6: 
/* 138: 86 */       this.hostname = ((CharSequence)value$); break;
/* 139:    */     case 7: 
/* 140: 87 */       this.port = ((Integer)value$).intValue(); break;
/* 141:    */     case 8: 
/* 142: 88 */       this.rackname = ((CharSequence)value$); break;
/* 143:    */     case 9: 
/* 144: 89 */       this.state = ((CharSequence)value$); break;
/* 145:    */     case 10: 
/* 146: 90 */       this.counters = ((JhCounters)value$); break;
/* 147:    */     case 11: 
/* 148: 91 */       this.clockSplits = ((List)value$); break;
/* 149:    */     case 12: 
/* 150: 92 */       this.cpuUsages = ((List)value$); break;
/* 151:    */     case 13: 
/* 152: 93 */       this.vMemKbytes = ((List)value$); break;
/* 153:    */     case 14: 
/* 154: 94 */       this.physMemKbytes = ((List)value$); break;
/* 155:    */     default: 
/* 156: 95 */       throw new AvroRuntimeException("Bad index");
/* 157:    */     }
/* 158:    */   }
/* 159:    */   
/* 160:    */   public CharSequence getTaskid()
/* 161:    */   {
/* 162:103 */     return this.taskid;
/* 163:    */   }
/* 164:    */   
/* 165:    */   public void setTaskid(CharSequence value)
/* 166:    */   {
/* 167:111 */     this.taskid = value;
/* 168:    */   }
/* 169:    */   
/* 170:    */   public CharSequence getAttemptId()
/* 171:    */   {
/* 172:118 */     return this.attemptId;
/* 173:    */   }
/* 174:    */   
/* 175:    */   public void setAttemptId(CharSequence value)
/* 176:    */   {
/* 177:126 */     this.attemptId = value;
/* 178:    */   }
/* 179:    */   
/* 180:    */   public CharSequence getTaskType()
/* 181:    */   {
/* 182:133 */     return this.taskType;
/* 183:    */   }
/* 184:    */   
/* 185:    */   public void setTaskType(CharSequence value)
/* 186:    */   {
/* 187:141 */     this.taskType = value;
/* 188:    */   }
/* 189:    */   
/* 190:    */   public CharSequence getTaskStatus()
/* 191:    */   {
/* 192:148 */     return this.taskStatus;
/* 193:    */   }
/* 194:    */   
/* 195:    */   public void setTaskStatus(CharSequence value)
/* 196:    */   {
/* 197:156 */     this.taskStatus = value;
/* 198:    */   }
/* 199:    */   
/* 200:    */   public Long getMapFinishTime()
/* 201:    */   {
/* 202:163 */     return Long.valueOf(this.mapFinishTime);
/* 203:    */   }
/* 204:    */   
/* 205:    */   public void setMapFinishTime(Long value)
/* 206:    */   {
/* 207:171 */     this.mapFinishTime = value.longValue();
/* 208:    */   }
/* 209:    */   
/* 210:    */   public Long getFinishTime()
/* 211:    */   {
/* 212:178 */     return Long.valueOf(this.finishTime);
/* 213:    */   }
/* 214:    */   
/* 215:    */   public void setFinishTime(Long value)
/* 216:    */   {
/* 217:186 */     this.finishTime = value.longValue();
/* 218:    */   }
/* 219:    */   
/* 220:    */   public CharSequence getHostname()
/* 221:    */   {
/* 222:193 */     return this.hostname;
/* 223:    */   }
/* 224:    */   
/* 225:    */   public void setHostname(CharSequence value)
/* 226:    */   {
/* 227:201 */     this.hostname = value;
/* 228:    */   }
/* 229:    */   
/* 230:    */   public Integer getPort()
/* 231:    */   {
/* 232:208 */     return Integer.valueOf(this.port);
/* 233:    */   }
/* 234:    */   
/* 235:    */   public void setPort(Integer value)
/* 236:    */   {
/* 237:216 */     this.port = value.intValue();
/* 238:    */   }
/* 239:    */   
/* 240:    */   public CharSequence getRackname()
/* 241:    */   {
/* 242:223 */     return this.rackname;
/* 243:    */   }
/* 244:    */   
/* 245:    */   public void setRackname(CharSequence value)
/* 246:    */   {
/* 247:231 */     this.rackname = value;
/* 248:    */   }
/* 249:    */   
/* 250:    */   public CharSequence getState()
/* 251:    */   {
/* 252:238 */     return this.state;
/* 253:    */   }
/* 254:    */   
/* 255:    */   public void setState(CharSequence value)
/* 256:    */   {
/* 257:246 */     this.state = value;
/* 258:    */   }
/* 259:    */   
/* 260:    */   public JhCounters getCounters()
/* 261:    */   {
/* 262:253 */     return this.counters;
/* 263:    */   }
/* 264:    */   
/* 265:    */   public void setCounters(JhCounters value)
/* 266:    */   {
/* 267:261 */     this.counters = value;
/* 268:    */   }
/* 269:    */   
/* 270:    */   public List<Integer> getClockSplits()
/* 271:    */   {
/* 272:268 */     return this.clockSplits;
/* 273:    */   }
/* 274:    */   
/* 275:    */   public void setClockSplits(List<Integer> value)
/* 276:    */   {
/* 277:276 */     this.clockSplits = value;
/* 278:    */   }
/* 279:    */   
/* 280:    */   public List<Integer> getCpuUsages()
/* 281:    */   {
/* 282:283 */     return this.cpuUsages;
/* 283:    */   }
/* 284:    */   
/* 285:    */   public void setCpuUsages(List<Integer> value)
/* 286:    */   {
/* 287:291 */     this.cpuUsages = value;
/* 288:    */   }
/* 289:    */   
/* 290:    */   public List<Integer> getVMemKbytes()
/* 291:    */   {
/* 292:298 */     return this.vMemKbytes;
/* 293:    */   }
/* 294:    */   
/* 295:    */   public void setVMemKbytes(List<Integer> value)
/* 296:    */   {
/* 297:306 */     this.vMemKbytes = value;
/* 298:    */   }
/* 299:    */   
/* 300:    */   public List<Integer> getPhysMemKbytes()
/* 301:    */   {
/* 302:313 */     return this.physMemKbytes;
/* 303:    */   }
/* 304:    */   
/* 305:    */   public void setPhysMemKbytes(List<Integer> value)
/* 306:    */   {
/* 307:321 */     this.physMemKbytes = value;
/* 308:    */   }
/* 309:    */   
/* 310:    */   public static Builder newBuilder()
/* 311:    */   {
/* 312:326 */     return new Builder(null);
/* 313:    */   }
/* 314:    */   
/* 315:    */   public static Builder newBuilder(Builder other)
/* 316:    */   {
/* 317:331 */     return new Builder(other, null);
/* 318:    */   }
/* 319:    */   
/* 320:    */   public static Builder newBuilder(MapAttemptFinished other)
/* 321:    */   {
/* 322:336 */     return new Builder(other, null);
/* 323:    */   }
/* 324:    */   
/* 325:    */   public static class Builder
/* 326:    */     extends SpecificRecordBuilderBase<MapAttemptFinished>
/* 327:    */     implements RecordBuilder<MapAttemptFinished>
/* 328:    */   {
/* 329:    */     private CharSequence taskid;
/* 330:    */     private CharSequence attemptId;
/* 331:    */     private CharSequence taskType;
/* 332:    */     private CharSequence taskStatus;
/* 333:    */     private long mapFinishTime;
/* 334:    */     private long finishTime;
/* 335:    */     private CharSequence hostname;
/* 336:    */     private int port;
/* 337:    */     private CharSequence rackname;
/* 338:    */     private CharSequence state;
/* 339:    */     private JhCounters counters;
/* 340:    */     private List<Integer> clockSplits;
/* 341:    */     private List<Integer> cpuUsages;
/* 342:    */     private List<Integer> vMemKbytes;
/* 343:    */     private List<Integer> physMemKbytes;
/* 344:    */     
/* 345:    */     private Builder()
/* 346:    */     {
/* 347:363 */       super();
/* 348:    */     }
/* 349:    */     
/* 350:    */     private Builder(Builder other)
/* 351:    */     {
/* 352:368 */       super();
/* 353:    */     }
/* 354:    */     
/* 355:    */     private Builder(MapAttemptFinished other)
/* 356:    */     {
/* 357:373 */       super();
/* 358:374 */       if (isValidValue(fields()[0], other.taskid))
/* 359:    */       {
/* 360:375 */         this.taskid = ((CharSequence)data().deepCopy(fields()[0].schema(), other.taskid));
/* 361:376 */         fieldSetFlags()[0] = 1;
/* 362:    */       }
/* 363:378 */       if (isValidValue(fields()[1], other.attemptId))
/* 364:    */       {
/* 365:379 */         this.attemptId = ((CharSequence)data().deepCopy(fields()[1].schema(), other.attemptId));
/* 366:380 */         fieldSetFlags()[1] = 1;
/* 367:    */       }
/* 368:382 */       if (isValidValue(fields()[2], other.taskType))
/* 369:    */       {
/* 370:383 */         this.taskType = ((CharSequence)data().deepCopy(fields()[2].schema(), other.taskType));
/* 371:384 */         fieldSetFlags()[2] = 1;
/* 372:    */       }
/* 373:386 */       if (isValidValue(fields()[3], other.taskStatus))
/* 374:    */       {
/* 375:387 */         this.taskStatus = ((CharSequence)data().deepCopy(fields()[3].schema(), other.taskStatus));
/* 376:388 */         fieldSetFlags()[3] = 1;
/* 377:    */       }
/* 378:390 */       if (isValidValue(fields()[4], Long.valueOf(other.mapFinishTime)))
/* 379:    */       {
/* 380:391 */         this.mapFinishTime = ((Long)data().deepCopy(fields()[4].schema(), Long.valueOf(other.mapFinishTime))).longValue();
/* 381:392 */         fieldSetFlags()[4] = 1;
/* 382:    */       }
/* 383:394 */       if (isValidValue(fields()[5], Long.valueOf(other.finishTime)))
/* 384:    */       {
/* 385:395 */         this.finishTime = ((Long)data().deepCopy(fields()[5].schema(), Long.valueOf(other.finishTime))).longValue();
/* 386:396 */         fieldSetFlags()[5] = 1;
/* 387:    */       }
/* 388:398 */       if (isValidValue(fields()[6], other.hostname))
/* 389:    */       {
/* 390:399 */         this.hostname = ((CharSequence)data().deepCopy(fields()[6].schema(), other.hostname));
/* 391:400 */         fieldSetFlags()[6] = 1;
/* 392:    */       }
/* 393:402 */       if (isValidValue(fields()[7], Integer.valueOf(other.port)))
/* 394:    */       {
/* 395:403 */         this.port = ((Integer)data().deepCopy(fields()[7].schema(), Integer.valueOf(other.port))).intValue();
/* 396:404 */         fieldSetFlags()[7] = 1;
/* 397:    */       }
/* 398:406 */       if (isValidValue(fields()[8], other.rackname))
/* 399:    */       {
/* 400:407 */         this.rackname = ((CharSequence)data().deepCopy(fields()[8].schema(), other.rackname));
/* 401:408 */         fieldSetFlags()[8] = 1;
/* 402:    */       }
/* 403:410 */       if (isValidValue(fields()[9], other.state))
/* 404:    */       {
/* 405:411 */         this.state = ((CharSequence)data().deepCopy(fields()[9].schema(), other.state));
/* 406:412 */         fieldSetFlags()[9] = 1;
/* 407:    */       }
/* 408:414 */       if (isValidValue(fields()[10], other.counters))
/* 409:    */       {
/* 410:415 */         this.counters = ((JhCounters)data().deepCopy(fields()[10].schema(), other.counters));
/* 411:416 */         fieldSetFlags()[10] = 1;
/* 412:    */       }
/* 413:418 */       if (isValidValue(fields()[11], other.clockSplits))
/* 414:    */       {
/* 415:419 */         this.clockSplits = ((List)data().deepCopy(fields()[11].schema(), other.clockSplits));
/* 416:420 */         fieldSetFlags()[11] = 1;
/* 417:    */       }
/* 418:422 */       if (isValidValue(fields()[12], other.cpuUsages))
/* 419:    */       {
/* 420:423 */         this.cpuUsages = ((List)data().deepCopy(fields()[12].schema(), other.cpuUsages));
/* 421:424 */         fieldSetFlags()[12] = 1;
/* 422:    */       }
/* 423:426 */       if (isValidValue(fields()[13], other.vMemKbytes))
/* 424:    */       {
/* 425:427 */         this.vMemKbytes = ((List)data().deepCopy(fields()[13].schema(), other.vMemKbytes));
/* 426:428 */         fieldSetFlags()[13] = 1;
/* 427:    */       }
/* 428:430 */       if (isValidValue(fields()[14], other.physMemKbytes))
/* 429:    */       {
/* 430:431 */         this.physMemKbytes = ((List)data().deepCopy(fields()[14].schema(), other.physMemKbytes));
/* 431:432 */         fieldSetFlags()[14] = 1;
/* 432:    */       }
/* 433:    */     }
/* 434:    */     
/* 435:    */     public CharSequence getTaskid()
/* 436:    */     {
/* 437:438 */       return this.taskid;
/* 438:    */     }
/* 439:    */     
/* 440:    */     public Builder setTaskid(CharSequence value)
/* 441:    */     {
/* 442:443 */       validate(fields()[0], value);
/* 443:444 */       this.taskid = value;
/* 444:445 */       fieldSetFlags()[0] = 1;
/* 445:446 */       return this;
/* 446:    */     }
/* 447:    */     
/* 448:    */     public boolean hasTaskid()
/* 449:    */     {
/* 450:451 */       return fieldSetFlags()[0];
/* 451:    */     }
/* 452:    */     
/* 453:    */     public Builder clearTaskid()
/* 454:    */     {
/* 455:456 */       this.taskid = null;
/* 456:457 */       fieldSetFlags()[0] = 0;
/* 457:458 */       return this;
/* 458:    */     }
/* 459:    */     
/* 460:    */     public CharSequence getAttemptId()
/* 461:    */     {
/* 462:463 */       return this.attemptId;
/* 463:    */     }
/* 464:    */     
/* 465:    */     public Builder setAttemptId(CharSequence value)
/* 466:    */     {
/* 467:468 */       validate(fields()[1], value);
/* 468:469 */       this.attemptId = value;
/* 469:470 */       fieldSetFlags()[1] = 1;
/* 470:471 */       return this;
/* 471:    */     }
/* 472:    */     
/* 473:    */     public boolean hasAttemptId()
/* 474:    */     {
/* 475:476 */       return fieldSetFlags()[1];
/* 476:    */     }
/* 477:    */     
/* 478:    */     public Builder clearAttemptId()
/* 479:    */     {
/* 480:481 */       this.attemptId = null;
/* 481:482 */       fieldSetFlags()[1] = 0;
/* 482:483 */       return this;
/* 483:    */     }
/* 484:    */     
/* 485:    */     public CharSequence getTaskType()
/* 486:    */     {
/* 487:488 */       return this.taskType;
/* 488:    */     }
/* 489:    */     
/* 490:    */     public Builder setTaskType(CharSequence value)
/* 491:    */     {
/* 492:493 */       validate(fields()[2], value);
/* 493:494 */       this.taskType = value;
/* 494:495 */       fieldSetFlags()[2] = 1;
/* 495:496 */       return this;
/* 496:    */     }
/* 497:    */     
/* 498:    */     public boolean hasTaskType()
/* 499:    */     {
/* 500:501 */       return fieldSetFlags()[2];
/* 501:    */     }
/* 502:    */     
/* 503:    */     public Builder clearTaskType()
/* 504:    */     {
/* 505:506 */       this.taskType = null;
/* 506:507 */       fieldSetFlags()[2] = 0;
/* 507:508 */       return this;
/* 508:    */     }
/* 509:    */     
/* 510:    */     public CharSequence getTaskStatus()
/* 511:    */     {
/* 512:513 */       return this.taskStatus;
/* 513:    */     }
/* 514:    */     
/* 515:    */     public Builder setTaskStatus(CharSequence value)
/* 516:    */     {
/* 517:518 */       validate(fields()[3], value);
/* 518:519 */       this.taskStatus = value;
/* 519:520 */       fieldSetFlags()[3] = 1;
/* 520:521 */       return this;
/* 521:    */     }
/* 522:    */     
/* 523:    */     public boolean hasTaskStatus()
/* 524:    */     {
/* 525:526 */       return fieldSetFlags()[3];
/* 526:    */     }
/* 527:    */     
/* 528:    */     public Builder clearTaskStatus()
/* 529:    */     {
/* 530:531 */       this.taskStatus = null;
/* 531:532 */       fieldSetFlags()[3] = 0;
/* 532:533 */       return this;
/* 533:    */     }
/* 534:    */     
/* 535:    */     public Long getMapFinishTime()
/* 536:    */     {
/* 537:538 */       return Long.valueOf(this.mapFinishTime);
/* 538:    */     }
/* 539:    */     
/* 540:    */     public Builder setMapFinishTime(long value)
/* 541:    */     {
/* 542:543 */       validate(fields()[4], Long.valueOf(value));
/* 543:544 */       this.mapFinishTime = value;
/* 544:545 */       fieldSetFlags()[4] = 1;
/* 545:546 */       return this;
/* 546:    */     }
/* 547:    */     
/* 548:    */     public boolean hasMapFinishTime()
/* 549:    */     {
/* 550:551 */       return fieldSetFlags()[4];
/* 551:    */     }
/* 552:    */     
/* 553:    */     public Builder clearMapFinishTime()
/* 554:    */     {
/* 555:556 */       fieldSetFlags()[4] = 0;
/* 556:557 */       return this;
/* 557:    */     }
/* 558:    */     
/* 559:    */     public Long getFinishTime()
/* 560:    */     {
/* 561:562 */       return Long.valueOf(this.finishTime);
/* 562:    */     }
/* 563:    */     
/* 564:    */     public Builder setFinishTime(long value)
/* 565:    */     {
/* 566:567 */       validate(fields()[5], Long.valueOf(value));
/* 567:568 */       this.finishTime = value;
/* 568:569 */       fieldSetFlags()[5] = 1;
/* 569:570 */       return this;
/* 570:    */     }
/* 571:    */     
/* 572:    */     public boolean hasFinishTime()
/* 573:    */     {
/* 574:575 */       return fieldSetFlags()[5];
/* 575:    */     }
/* 576:    */     
/* 577:    */     public Builder clearFinishTime()
/* 578:    */     {
/* 579:580 */       fieldSetFlags()[5] = 0;
/* 580:581 */       return this;
/* 581:    */     }
/* 582:    */     
/* 583:    */     public CharSequence getHostname()
/* 584:    */     {
/* 585:586 */       return this.hostname;
/* 586:    */     }
/* 587:    */     
/* 588:    */     public Builder setHostname(CharSequence value)
/* 589:    */     {
/* 590:591 */       validate(fields()[6], value);
/* 591:592 */       this.hostname = value;
/* 592:593 */       fieldSetFlags()[6] = 1;
/* 593:594 */       return this;
/* 594:    */     }
/* 595:    */     
/* 596:    */     public boolean hasHostname()
/* 597:    */     {
/* 598:599 */       return fieldSetFlags()[6];
/* 599:    */     }
/* 600:    */     
/* 601:    */     public Builder clearHostname()
/* 602:    */     {
/* 603:604 */       this.hostname = null;
/* 604:605 */       fieldSetFlags()[6] = 0;
/* 605:606 */       return this;
/* 606:    */     }
/* 607:    */     
/* 608:    */     public Integer getPort()
/* 609:    */     {
/* 610:611 */       return Integer.valueOf(this.port);
/* 611:    */     }
/* 612:    */     
/* 613:    */     public Builder setPort(int value)
/* 614:    */     {
/* 615:616 */       validate(fields()[7], Integer.valueOf(value));
/* 616:617 */       this.port = value;
/* 617:618 */       fieldSetFlags()[7] = 1;
/* 618:619 */       return this;
/* 619:    */     }
/* 620:    */     
/* 621:    */     public boolean hasPort()
/* 622:    */     {
/* 623:624 */       return fieldSetFlags()[7];
/* 624:    */     }
/* 625:    */     
/* 626:    */     public Builder clearPort()
/* 627:    */     {
/* 628:629 */       fieldSetFlags()[7] = 0;
/* 629:630 */       return this;
/* 630:    */     }
/* 631:    */     
/* 632:    */     public CharSequence getRackname()
/* 633:    */     {
/* 634:635 */       return this.rackname;
/* 635:    */     }
/* 636:    */     
/* 637:    */     public Builder setRackname(CharSequence value)
/* 638:    */     {
/* 639:640 */       validate(fields()[8], value);
/* 640:641 */       this.rackname = value;
/* 641:642 */       fieldSetFlags()[8] = 1;
/* 642:643 */       return this;
/* 643:    */     }
/* 644:    */     
/* 645:    */     public boolean hasRackname()
/* 646:    */     {
/* 647:648 */       return fieldSetFlags()[8];
/* 648:    */     }
/* 649:    */     
/* 650:    */     public Builder clearRackname()
/* 651:    */     {
/* 652:653 */       this.rackname = null;
/* 653:654 */       fieldSetFlags()[8] = 0;
/* 654:655 */       return this;
/* 655:    */     }
/* 656:    */     
/* 657:    */     public CharSequence getState()
/* 658:    */     {
/* 659:660 */       return this.state;
/* 660:    */     }
/* 661:    */     
/* 662:    */     public Builder setState(CharSequence value)
/* 663:    */     {
/* 664:665 */       validate(fields()[9], value);
/* 665:666 */       this.state = value;
/* 666:667 */       fieldSetFlags()[9] = 1;
/* 667:668 */       return this;
/* 668:    */     }
/* 669:    */     
/* 670:    */     public boolean hasState()
/* 671:    */     {
/* 672:673 */       return fieldSetFlags()[9];
/* 673:    */     }
/* 674:    */     
/* 675:    */     public Builder clearState()
/* 676:    */     {
/* 677:678 */       this.state = null;
/* 678:679 */       fieldSetFlags()[9] = 0;
/* 679:680 */       return this;
/* 680:    */     }
/* 681:    */     
/* 682:    */     public JhCounters getCounters()
/* 683:    */     {
/* 684:685 */       return this.counters;
/* 685:    */     }
/* 686:    */     
/* 687:    */     public Builder setCounters(JhCounters value)
/* 688:    */     {
/* 689:690 */       validate(fields()[10], value);
/* 690:691 */       this.counters = value;
/* 691:692 */       fieldSetFlags()[10] = 1;
/* 692:693 */       return this;
/* 693:    */     }
/* 694:    */     
/* 695:    */     public boolean hasCounters()
/* 696:    */     {
/* 697:698 */       return fieldSetFlags()[10];
/* 698:    */     }
/* 699:    */     
/* 700:    */     public Builder clearCounters()
/* 701:    */     {
/* 702:703 */       this.counters = null;
/* 703:704 */       fieldSetFlags()[10] = 0;
/* 704:705 */       return this;
/* 705:    */     }
/* 706:    */     
/* 707:    */     public List<Integer> getClockSplits()
/* 708:    */     {
/* 709:710 */       return this.clockSplits;
/* 710:    */     }
/* 711:    */     
/* 712:    */     public Builder setClockSplits(List<Integer> value)
/* 713:    */     {
/* 714:715 */       validate(fields()[11], value);
/* 715:716 */       this.clockSplits = value;
/* 716:717 */       fieldSetFlags()[11] = 1;
/* 717:718 */       return this;
/* 718:    */     }
/* 719:    */     
/* 720:    */     public boolean hasClockSplits()
/* 721:    */     {
/* 722:723 */       return fieldSetFlags()[11];
/* 723:    */     }
/* 724:    */     
/* 725:    */     public Builder clearClockSplits()
/* 726:    */     {
/* 727:728 */       this.clockSplits = null;
/* 728:729 */       fieldSetFlags()[11] = 0;
/* 729:730 */       return this;
/* 730:    */     }
/* 731:    */     
/* 732:    */     public List<Integer> getCpuUsages()
/* 733:    */     {
/* 734:735 */       return this.cpuUsages;
/* 735:    */     }
/* 736:    */     
/* 737:    */     public Builder setCpuUsages(List<Integer> value)
/* 738:    */     {
/* 739:740 */       validate(fields()[12], value);
/* 740:741 */       this.cpuUsages = value;
/* 741:742 */       fieldSetFlags()[12] = 1;
/* 742:743 */       return this;
/* 743:    */     }
/* 744:    */     
/* 745:    */     public boolean hasCpuUsages()
/* 746:    */     {
/* 747:748 */       return fieldSetFlags()[12];
/* 748:    */     }
/* 749:    */     
/* 750:    */     public Builder clearCpuUsages()
/* 751:    */     {
/* 752:753 */       this.cpuUsages = null;
/* 753:754 */       fieldSetFlags()[12] = 0;
/* 754:755 */       return this;
/* 755:    */     }
/* 756:    */     
/* 757:    */     public List<Integer> getVMemKbytes()
/* 758:    */     {
/* 759:760 */       return this.vMemKbytes;
/* 760:    */     }
/* 761:    */     
/* 762:    */     public Builder setVMemKbytes(List<Integer> value)
/* 763:    */     {
/* 764:765 */       validate(fields()[13], value);
/* 765:766 */       this.vMemKbytes = value;
/* 766:767 */       fieldSetFlags()[13] = 1;
/* 767:768 */       return this;
/* 768:    */     }
/* 769:    */     
/* 770:    */     public boolean hasVMemKbytes()
/* 771:    */     {
/* 772:773 */       return fieldSetFlags()[13];
/* 773:    */     }
/* 774:    */     
/* 775:    */     public Builder clearVMemKbytes()
/* 776:    */     {
/* 777:778 */       this.vMemKbytes = null;
/* 778:779 */       fieldSetFlags()[13] = 0;
/* 779:780 */       return this;
/* 780:    */     }
/* 781:    */     
/* 782:    */     public List<Integer> getPhysMemKbytes()
/* 783:    */     {
/* 784:785 */       return this.physMemKbytes;
/* 785:    */     }
/* 786:    */     
/* 787:    */     public Builder setPhysMemKbytes(List<Integer> value)
/* 788:    */     {
/* 789:790 */       validate(fields()[14], value);
/* 790:791 */       this.physMemKbytes = value;
/* 791:792 */       fieldSetFlags()[14] = 1;
/* 792:793 */       return this;
/* 793:    */     }
/* 794:    */     
/* 795:    */     public boolean hasPhysMemKbytes()
/* 796:    */     {
/* 797:798 */       return fieldSetFlags()[14];
/* 798:    */     }
/* 799:    */     
/* 800:    */     public Builder clearPhysMemKbytes()
/* 801:    */     {
/* 802:803 */       this.physMemKbytes = null;
/* 803:804 */       fieldSetFlags()[14] = 0;
/* 804:805 */       return this;
/* 805:    */     }
/* 806:    */     
/* 807:    */     public MapAttemptFinished build()
/* 808:    */     {
/* 809:    */       try
/* 810:    */       {
/* 811:811 */         MapAttemptFinished record = new MapAttemptFinished();
/* 812:812 */         record.taskid = (fieldSetFlags()[0] != 0 ? this.taskid : (CharSequence)defaultValue(fields()[0]));
/* 813:813 */         record.attemptId = (fieldSetFlags()[1] != 0 ? this.attemptId : (CharSequence)defaultValue(fields()[1]));
/* 814:814 */         record.taskType = (fieldSetFlags()[2] != 0 ? this.taskType : (CharSequence)defaultValue(fields()[2]));
/* 815:815 */         record.taskStatus = (fieldSetFlags()[3] != 0 ? this.taskStatus : (CharSequence)defaultValue(fields()[3]));
/* 816:816 */         record.mapFinishTime = (fieldSetFlags()[4] != 0 ? this.mapFinishTime : ((Long)defaultValue(fields()[4])).longValue());
/* 817:817 */         record.finishTime = (fieldSetFlags()[5] != 0 ? this.finishTime : ((Long)defaultValue(fields()[5])).longValue());
/* 818:818 */         record.hostname = (fieldSetFlags()[6] != 0 ? this.hostname : (CharSequence)defaultValue(fields()[6]));
/* 819:819 */         record.port = (fieldSetFlags()[7] != 0 ? this.port : ((Integer)defaultValue(fields()[7])).intValue());
/* 820:820 */         record.rackname = (fieldSetFlags()[8] != 0 ? this.rackname : (CharSequence)defaultValue(fields()[8]));
/* 821:821 */         record.state = (fieldSetFlags()[9] != 0 ? this.state : (CharSequence)defaultValue(fields()[9]));
/* 822:822 */         record.counters = (fieldSetFlags()[10] != 0 ? this.counters : (JhCounters)defaultValue(fields()[10]));
/* 823:823 */         record.clockSplits = (fieldSetFlags()[11] != 0 ? this.clockSplits : (List)defaultValue(fields()[11]));
/* 824:824 */         record.cpuUsages = (fieldSetFlags()[12] != 0 ? this.cpuUsages : (List)defaultValue(fields()[12]));
/* 825:825 */         record.vMemKbytes = (fieldSetFlags()[13] != 0 ? this.vMemKbytes : (List)defaultValue(fields()[13]));
/* 826:826 */         record.physMemKbytes = (fieldSetFlags()[14] != 0 ? this.physMemKbytes : (List)defaultValue(fields()[14]));
/* 827:827 */         return record;
/* 828:    */       }
/* 829:    */       catch (Exception e)
/* 830:    */       {
/* 831:829 */         throw new AvroRuntimeException(e);
/* 832:    */       }
/* 833:    */     }
/* 834:    */   }
/* 835:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.jobhistory.MapAttemptFinished
 * JD-Core Version:    0.7.0.1
 */