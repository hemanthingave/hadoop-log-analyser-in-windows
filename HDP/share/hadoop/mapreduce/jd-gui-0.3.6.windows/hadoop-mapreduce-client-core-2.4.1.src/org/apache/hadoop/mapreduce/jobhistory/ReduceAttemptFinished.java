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
/*  16:    */ public class ReduceAttemptFinished
/*  17:    */   extends SpecificRecordBase
/*  18:    */   implements SpecificRecord
/*  19:    */ {
/*  20: 10 */   public static final Schema SCHEMA$ = new Schema.Parser().parse("{\"type\":\"record\",\"name\":\"ReduceAttemptFinished\",\"namespace\":\"org.apache.hadoop.mapreduce.jobhistory\",\"fields\":[{\"name\":\"taskid\",\"type\":\"string\"},{\"name\":\"attemptId\",\"type\":\"string\"},{\"name\":\"taskType\",\"type\":\"string\"},{\"name\":\"taskStatus\",\"type\":\"string\"},{\"name\":\"shuffleFinishTime\",\"type\":\"long\"},{\"name\":\"sortFinishTime\",\"type\":\"long\"},{\"name\":\"finishTime\",\"type\":\"long\"},{\"name\":\"hostname\",\"type\":\"string\"},{\"name\":\"port\",\"type\":\"int\"},{\"name\":\"rackname\",\"type\":\"string\"},{\"name\":\"state\",\"type\":\"string\"},{\"name\":\"counters\",\"type\":{\"type\":\"record\",\"name\":\"JhCounters\",\"fields\":[{\"name\":\"name\",\"type\":\"string\"},{\"name\":\"groups\",\"type\":{\"type\":\"array\",\"items\":{\"type\":\"record\",\"name\":\"JhCounterGroup\",\"fields\":[{\"name\":\"name\",\"type\":\"string\"},{\"name\":\"displayName\",\"type\":\"string\"},{\"name\":\"counts\",\"type\":{\"type\":\"array\",\"items\":{\"type\":\"record\",\"name\":\"JhCounter\",\"fields\":[{\"name\":\"name\",\"type\":\"string\"},{\"name\":\"displayName\",\"type\":\"string\"},{\"name\":\"value\",\"type\":\"long\"}]}}}]}}}]}},{\"name\":\"clockSplits\",\"type\":{\"type\":\"array\",\"items\":\"int\"}},{\"name\":\"cpuUsages\",\"type\":{\"type\":\"array\",\"items\":\"int\"}},{\"name\":\"vMemKbytes\",\"type\":{\"type\":\"array\",\"items\":\"int\"}},{\"name\":\"physMemKbytes\",\"type\":{\"type\":\"array\",\"items\":\"int\"}}]}");
/*  21:    */   @Deprecated
/*  22:    */   public CharSequence taskid;
/*  23:    */   @Deprecated
/*  24:    */   public CharSequence attemptId;
/*  25:    */   @Deprecated
/*  26:    */   public CharSequence taskType;
/*  27:    */   @Deprecated
/*  28:    */   public CharSequence taskStatus;
/*  29:    */   @Deprecated
/*  30:    */   public long shuffleFinishTime;
/*  31:    */   @Deprecated
/*  32:    */   public long sortFinishTime;
/*  33:    */   @Deprecated
/*  34:    */   public long finishTime;
/*  35:    */   @Deprecated
/*  36:    */   public CharSequence hostname;
/*  37:    */   @Deprecated
/*  38:    */   public int port;
/*  39:    */   @Deprecated
/*  40:    */   public CharSequence rackname;
/*  41:    */   @Deprecated
/*  42:    */   public CharSequence state;
/*  43:    */   @Deprecated
/*  44:    */   public JhCounters counters;
/*  45:    */   @Deprecated
/*  46:    */   public List<Integer> clockSplits;
/*  47:    */   @Deprecated
/*  48:    */   public List<Integer> cpuUsages;
/*  49:    */   @Deprecated
/*  50:    */   public List<Integer> vMemKbytes;
/*  51:    */   @Deprecated
/*  52:    */   public List<Integer> physMemKbytes;
/*  53:    */   
/*  54:    */   public static Schema getClassSchema()
/*  55:    */   {
/*  56: 11 */     return SCHEMA$;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public ReduceAttemptFinished() {}
/*  60:    */   
/*  61:    */   public ReduceAttemptFinished(CharSequence taskid, CharSequence attemptId, CharSequence taskType, CharSequence taskStatus, Long shuffleFinishTime, Long sortFinishTime, Long finishTime, CharSequence hostname, Integer port, CharSequence rackname, CharSequence state, JhCounters counters, List<Integer> clockSplits, List<Integer> cpuUsages, List<Integer> vMemKbytes, List<Integer> physMemKbytes)
/*  62:    */   {
/*  63: 38 */     this.taskid = taskid;
/*  64: 39 */     this.attemptId = attemptId;
/*  65: 40 */     this.taskType = taskType;
/*  66: 41 */     this.taskStatus = taskStatus;
/*  67: 42 */     this.shuffleFinishTime = shuffleFinishTime.longValue();
/*  68: 43 */     this.sortFinishTime = sortFinishTime.longValue();
/*  69: 44 */     this.finishTime = finishTime.longValue();
/*  70: 45 */     this.hostname = hostname;
/*  71: 46 */     this.port = port.intValue();
/*  72: 47 */     this.rackname = rackname;
/*  73: 48 */     this.state = state;
/*  74: 49 */     this.counters = counters;
/*  75: 50 */     this.clockSplits = clockSplits;
/*  76: 51 */     this.cpuUsages = cpuUsages;
/*  77: 52 */     this.vMemKbytes = vMemKbytes;
/*  78: 53 */     this.physMemKbytes = physMemKbytes;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public Schema getSchema()
/*  82:    */   {
/*  83: 56 */     return SCHEMA$;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public Object get(int field$)
/*  87:    */   {
/*  88: 59 */     switch (field$)
/*  89:    */     {
/*  90:    */     case 0: 
/*  91: 60 */       return this.taskid;
/*  92:    */     case 1: 
/*  93: 61 */       return this.attemptId;
/*  94:    */     case 2: 
/*  95: 62 */       return this.taskType;
/*  96:    */     case 3: 
/*  97: 63 */       return this.taskStatus;
/*  98:    */     case 4: 
/*  99: 64 */       return Long.valueOf(this.shuffleFinishTime);
/* 100:    */     case 5: 
/* 101: 65 */       return Long.valueOf(this.sortFinishTime);
/* 102:    */     case 6: 
/* 103: 66 */       return Long.valueOf(this.finishTime);
/* 104:    */     case 7: 
/* 105: 67 */       return this.hostname;
/* 106:    */     case 8: 
/* 107: 68 */       return Integer.valueOf(this.port);
/* 108:    */     case 9: 
/* 109: 69 */       return this.rackname;
/* 110:    */     case 10: 
/* 111: 70 */       return this.state;
/* 112:    */     case 11: 
/* 113: 71 */       return this.counters;
/* 114:    */     case 12: 
/* 115: 72 */       return this.clockSplits;
/* 116:    */     case 13: 
/* 117: 73 */       return this.cpuUsages;
/* 118:    */     case 14: 
/* 119: 74 */       return this.vMemKbytes;
/* 120:    */     case 15: 
/* 121: 75 */       return this.physMemKbytes;
/* 122:    */     }
/* 123: 76 */     throw new AvroRuntimeException("Bad index");
/* 124:    */   }
/* 125:    */   
/* 126:    */   public void put(int field$, Object value$)
/* 127:    */   {
/* 128: 82 */     switch (field$)
/* 129:    */     {
/* 130:    */     case 0: 
/* 131: 83 */       this.taskid = ((CharSequence)value$); break;
/* 132:    */     case 1: 
/* 133: 84 */       this.attemptId = ((CharSequence)value$); break;
/* 134:    */     case 2: 
/* 135: 85 */       this.taskType = ((CharSequence)value$); break;
/* 136:    */     case 3: 
/* 137: 86 */       this.taskStatus = ((CharSequence)value$); break;
/* 138:    */     case 4: 
/* 139: 87 */       this.shuffleFinishTime = ((Long)value$).longValue(); break;
/* 140:    */     case 5: 
/* 141: 88 */       this.sortFinishTime = ((Long)value$).longValue(); break;
/* 142:    */     case 6: 
/* 143: 89 */       this.finishTime = ((Long)value$).longValue(); break;
/* 144:    */     case 7: 
/* 145: 90 */       this.hostname = ((CharSequence)value$); break;
/* 146:    */     case 8: 
/* 147: 91 */       this.port = ((Integer)value$).intValue(); break;
/* 148:    */     case 9: 
/* 149: 92 */       this.rackname = ((CharSequence)value$); break;
/* 150:    */     case 10: 
/* 151: 93 */       this.state = ((CharSequence)value$); break;
/* 152:    */     case 11: 
/* 153: 94 */       this.counters = ((JhCounters)value$); break;
/* 154:    */     case 12: 
/* 155: 95 */       this.clockSplits = ((List)value$); break;
/* 156:    */     case 13: 
/* 157: 96 */       this.cpuUsages = ((List)value$); break;
/* 158:    */     case 14: 
/* 159: 97 */       this.vMemKbytes = ((List)value$); break;
/* 160:    */     case 15: 
/* 161: 98 */       this.physMemKbytes = ((List)value$); break;
/* 162:    */     default: 
/* 163: 99 */       throw new AvroRuntimeException("Bad index");
/* 164:    */     }
/* 165:    */   }
/* 166:    */   
/* 167:    */   public CharSequence getTaskid()
/* 168:    */   {
/* 169:107 */     return this.taskid;
/* 170:    */   }
/* 171:    */   
/* 172:    */   public void setTaskid(CharSequence value)
/* 173:    */   {
/* 174:115 */     this.taskid = value;
/* 175:    */   }
/* 176:    */   
/* 177:    */   public CharSequence getAttemptId()
/* 178:    */   {
/* 179:122 */     return this.attemptId;
/* 180:    */   }
/* 181:    */   
/* 182:    */   public void setAttemptId(CharSequence value)
/* 183:    */   {
/* 184:130 */     this.attemptId = value;
/* 185:    */   }
/* 186:    */   
/* 187:    */   public CharSequence getTaskType()
/* 188:    */   {
/* 189:137 */     return this.taskType;
/* 190:    */   }
/* 191:    */   
/* 192:    */   public void setTaskType(CharSequence value)
/* 193:    */   {
/* 194:145 */     this.taskType = value;
/* 195:    */   }
/* 196:    */   
/* 197:    */   public CharSequence getTaskStatus()
/* 198:    */   {
/* 199:152 */     return this.taskStatus;
/* 200:    */   }
/* 201:    */   
/* 202:    */   public void setTaskStatus(CharSequence value)
/* 203:    */   {
/* 204:160 */     this.taskStatus = value;
/* 205:    */   }
/* 206:    */   
/* 207:    */   public Long getShuffleFinishTime()
/* 208:    */   {
/* 209:167 */     return Long.valueOf(this.shuffleFinishTime);
/* 210:    */   }
/* 211:    */   
/* 212:    */   public void setShuffleFinishTime(Long value)
/* 213:    */   {
/* 214:175 */     this.shuffleFinishTime = value.longValue();
/* 215:    */   }
/* 216:    */   
/* 217:    */   public Long getSortFinishTime()
/* 218:    */   {
/* 219:182 */     return Long.valueOf(this.sortFinishTime);
/* 220:    */   }
/* 221:    */   
/* 222:    */   public void setSortFinishTime(Long value)
/* 223:    */   {
/* 224:190 */     this.sortFinishTime = value.longValue();
/* 225:    */   }
/* 226:    */   
/* 227:    */   public Long getFinishTime()
/* 228:    */   {
/* 229:197 */     return Long.valueOf(this.finishTime);
/* 230:    */   }
/* 231:    */   
/* 232:    */   public void setFinishTime(Long value)
/* 233:    */   {
/* 234:205 */     this.finishTime = value.longValue();
/* 235:    */   }
/* 236:    */   
/* 237:    */   public CharSequence getHostname()
/* 238:    */   {
/* 239:212 */     return this.hostname;
/* 240:    */   }
/* 241:    */   
/* 242:    */   public void setHostname(CharSequence value)
/* 243:    */   {
/* 244:220 */     this.hostname = value;
/* 245:    */   }
/* 246:    */   
/* 247:    */   public Integer getPort()
/* 248:    */   {
/* 249:227 */     return Integer.valueOf(this.port);
/* 250:    */   }
/* 251:    */   
/* 252:    */   public void setPort(Integer value)
/* 253:    */   {
/* 254:235 */     this.port = value.intValue();
/* 255:    */   }
/* 256:    */   
/* 257:    */   public CharSequence getRackname()
/* 258:    */   {
/* 259:242 */     return this.rackname;
/* 260:    */   }
/* 261:    */   
/* 262:    */   public void setRackname(CharSequence value)
/* 263:    */   {
/* 264:250 */     this.rackname = value;
/* 265:    */   }
/* 266:    */   
/* 267:    */   public CharSequence getState()
/* 268:    */   {
/* 269:257 */     return this.state;
/* 270:    */   }
/* 271:    */   
/* 272:    */   public void setState(CharSequence value)
/* 273:    */   {
/* 274:265 */     this.state = value;
/* 275:    */   }
/* 276:    */   
/* 277:    */   public JhCounters getCounters()
/* 278:    */   {
/* 279:272 */     return this.counters;
/* 280:    */   }
/* 281:    */   
/* 282:    */   public void setCounters(JhCounters value)
/* 283:    */   {
/* 284:280 */     this.counters = value;
/* 285:    */   }
/* 286:    */   
/* 287:    */   public List<Integer> getClockSplits()
/* 288:    */   {
/* 289:287 */     return this.clockSplits;
/* 290:    */   }
/* 291:    */   
/* 292:    */   public void setClockSplits(List<Integer> value)
/* 293:    */   {
/* 294:295 */     this.clockSplits = value;
/* 295:    */   }
/* 296:    */   
/* 297:    */   public List<Integer> getCpuUsages()
/* 298:    */   {
/* 299:302 */     return this.cpuUsages;
/* 300:    */   }
/* 301:    */   
/* 302:    */   public void setCpuUsages(List<Integer> value)
/* 303:    */   {
/* 304:310 */     this.cpuUsages = value;
/* 305:    */   }
/* 306:    */   
/* 307:    */   public List<Integer> getVMemKbytes()
/* 308:    */   {
/* 309:317 */     return this.vMemKbytes;
/* 310:    */   }
/* 311:    */   
/* 312:    */   public void setVMemKbytes(List<Integer> value)
/* 313:    */   {
/* 314:325 */     this.vMemKbytes = value;
/* 315:    */   }
/* 316:    */   
/* 317:    */   public List<Integer> getPhysMemKbytes()
/* 318:    */   {
/* 319:332 */     return this.physMemKbytes;
/* 320:    */   }
/* 321:    */   
/* 322:    */   public void setPhysMemKbytes(List<Integer> value)
/* 323:    */   {
/* 324:340 */     this.physMemKbytes = value;
/* 325:    */   }
/* 326:    */   
/* 327:    */   public static Builder newBuilder()
/* 328:    */   {
/* 329:345 */     return new Builder(null);
/* 330:    */   }
/* 331:    */   
/* 332:    */   public static Builder newBuilder(Builder other)
/* 333:    */   {
/* 334:350 */     return new Builder(other, null);
/* 335:    */   }
/* 336:    */   
/* 337:    */   public static Builder newBuilder(ReduceAttemptFinished other)
/* 338:    */   {
/* 339:355 */     return new Builder(other, null);
/* 340:    */   }
/* 341:    */   
/* 342:    */   public static class Builder
/* 343:    */     extends SpecificRecordBuilderBase<ReduceAttemptFinished>
/* 344:    */     implements RecordBuilder<ReduceAttemptFinished>
/* 345:    */   {
/* 346:    */     private CharSequence taskid;
/* 347:    */     private CharSequence attemptId;
/* 348:    */     private CharSequence taskType;
/* 349:    */     private CharSequence taskStatus;
/* 350:    */     private long shuffleFinishTime;
/* 351:    */     private long sortFinishTime;
/* 352:    */     private long finishTime;
/* 353:    */     private CharSequence hostname;
/* 354:    */     private int port;
/* 355:    */     private CharSequence rackname;
/* 356:    */     private CharSequence state;
/* 357:    */     private JhCounters counters;
/* 358:    */     private List<Integer> clockSplits;
/* 359:    */     private List<Integer> cpuUsages;
/* 360:    */     private List<Integer> vMemKbytes;
/* 361:    */     private List<Integer> physMemKbytes;
/* 362:    */     
/* 363:    */     private Builder()
/* 364:    */     {
/* 365:383 */       super();
/* 366:    */     }
/* 367:    */     
/* 368:    */     private Builder(Builder other)
/* 369:    */     {
/* 370:388 */       super();
/* 371:    */     }
/* 372:    */     
/* 373:    */     private Builder(ReduceAttemptFinished other)
/* 374:    */     {
/* 375:393 */       super();
/* 376:394 */       if (isValidValue(fields()[0], other.taskid))
/* 377:    */       {
/* 378:395 */         this.taskid = ((CharSequence)data().deepCopy(fields()[0].schema(), other.taskid));
/* 379:396 */         fieldSetFlags()[0] = 1;
/* 380:    */       }
/* 381:398 */       if (isValidValue(fields()[1], other.attemptId))
/* 382:    */       {
/* 383:399 */         this.attemptId = ((CharSequence)data().deepCopy(fields()[1].schema(), other.attemptId));
/* 384:400 */         fieldSetFlags()[1] = 1;
/* 385:    */       }
/* 386:402 */       if (isValidValue(fields()[2], other.taskType))
/* 387:    */       {
/* 388:403 */         this.taskType = ((CharSequence)data().deepCopy(fields()[2].schema(), other.taskType));
/* 389:404 */         fieldSetFlags()[2] = 1;
/* 390:    */       }
/* 391:406 */       if (isValidValue(fields()[3], other.taskStatus))
/* 392:    */       {
/* 393:407 */         this.taskStatus = ((CharSequence)data().deepCopy(fields()[3].schema(), other.taskStatus));
/* 394:408 */         fieldSetFlags()[3] = 1;
/* 395:    */       }
/* 396:410 */       if (isValidValue(fields()[4], Long.valueOf(other.shuffleFinishTime)))
/* 397:    */       {
/* 398:411 */         this.shuffleFinishTime = ((Long)data().deepCopy(fields()[4].schema(), Long.valueOf(other.shuffleFinishTime))).longValue();
/* 399:412 */         fieldSetFlags()[4] = 1;
/* 400:    */       }
/* 401:414 */       if (isValidValue(fields()[5], Long.valueOf(other.sortFinishTime)))
/* 402:    */       {
/* 403:415 */         this.sortFinishTime = ((Long)data().deepCopy(fields()[5].schema(), Long.valueOf(other.sortFinishTime))).longValue();
/* 404:416 */         fieldSetFlags()[5] = 1;
/* 405:    */       }
/* 406:418 */       if (isValidValue(fields()[6], Long.valueOf(other.finishTime)))
/* 407:    */       {
/* 408:419 */         this.finishTime = ((Long)data().deepCopy(fields()[6].schema(), Long.valueOf(other.finishTime))).longValue();
/* 409:420 */         fieldSetFlags()[6] = 1;
/* 410:    */       }
/* 411:422 */       if (isValidValue(fields()[7], other.hostname))
/* 412:    */       {
/* 413:423 */         this.hostname = ((CharSequence)data().deepCopy(fields()[7].schema(), other.hostname));
/* 414:424 */         fieldSetFlags()[7] = 1;
/* 415:    */       }
/* 416:426 */       if (isValidValue(fields()[8], Integer.valueOf(other.port)))
/* 417:    */       {
/* 418:427 */         this.port = ((Integer)data().deepCopy(fields()[8].schema(), Integer.valueOf(other.port))).intValue();
/* 419:428 */         fieldSetFlags()[8] = 1;
/* 420:    */       }
/* 421:430 */       if (isValidValue(fields()[9], other.rackname))
/* 422:    */       {
/* 423:431 */         this.rackname = ((CharSequence)data().deepCopy(fields()[9].schema(), other.rackname));
/* 424:432 */         fieldSetFlags()[9] = 1;
/* 425:    */       }
/* 426:434 */       if (isValidValue(fields()[10], other.state))
/* 427:    */       {
/* 428:435 */         this.state = ((CharSequence)data().deepCopy(fields()[10].schema(), other.state));
/* 429:436 */         fieldSetFlags()[10] = 1;
/* 430:    */       }
/* 431:438 */       if (isValidValue(fields()[11], other.counters))
/* 432:    */       {
/* 433:439 */         this.counters = ((JhCounters)data().deepCopy(fields()[11].schema(), other.counters));
/* 434:440 */         fieldSetFlags()[11] = 1;
/* 435:    */       }
/* 436:442 */       if (isValidValue(fields()[12], other.clockSplits))
/* 437:    */       {
/* 438:443 */         this.clockSplits = ((List)data().deepCopy(fields()[12].schema(), other.clockSplits));
/* 439:444 */         fieldSetFlags()[12] = 1;
/* 440:    */       }
/* 441:446 */       if (isValidValue(fields()[13], other.cpuUsages))
/* 442:    */       {
/* 443:447 */         this.cpuUsages = ((List)data().deepCopy(fields()[13].schema(), other.cpuUsages));
/* 444:448 */         fieldSetFlags()[13] = 1;
/* 445:    */       }
/* 446:450 */       if (isValidValue(fields()[14], other.vMemKbytes))
/* 447:    */       {
/* 448:451 */         this.vMemKbytes = ((List)data().deepCopy(fields()[14].schema(), other.vMemKbytes));
/* 449:452 */         fieldSetFlags()[14] = 1;
/* 450:    */       }
/* 451:454 */       if (isValidValue(fields()[15], other.physMemKbytes))
/* 452:    */       {
/* 453:455 */         this.physMemKbytes = ((List)data().deepCopy(fields()[15].schema(), other.physMemKbytes));
/* 454:456 */         fieldSetFlags()[15] = 1;
/* 455:    */       }
/* 456:    */     }
/* 457:    */     
/* 458:    */     public CharSequence getTaskid()
/* 459:    */     {
/* 460:462 */       return this.taskid;
/* 461:    */     }
/* 462:    */     
/* 463:    */     public Builder setTaskid(CharSequence value)
/* 464:    */     {
/* 465:467 */       validate(fields()[0], value);
/* 466:468 */       this.taskid = value;
/* 467:469 */       fieldSetFlags()[0] = 1;
/* 468:470 */       return this;
/* 469:    */     }
/* 470:    */     
/* 471:    */     public boolean hasTaskid()
/* 472:    */     {
/* 473:475 */       return fieldSetFlags()[0];
/* 474:    */     }
/* 475:    */     
/* 476:    */     public Builder clearTaskid()
/* 477:    */     {
/* 478:480 */       this.taskid = null;
/* 479:481 */       fieldSetFlags()[0] = 0;
/* 480:482 */       return this;
/* 481:    */     }
/* 482:    */     
/* 483:    */     public CharSequence getAttemptId()
/* 484:    */     {
/* 485:487 */       return this.attemptId;
/* 486:    */     }
/* 487:    */     
/* 488:    */     public Builder setAttemptId(CharSequence value)
/* 489:    */     {
/* 490:492 */       validate(fields()[1], value);
/* 491:493 */       this.attemptId = value;
/* 492:494 */       fieldSetFlags()[1] = 1;
/* 493:495 */       return this;
/* 494:    */     }
/* 495:    */     
/* 496:    */     public boolean hasAttemptId()
/* 497:    */     {
/* 498:500 */       return fieldSetFlags()[1];
/* 499:    */     }
/* 500:    */     
/* 501:    */     public Builder clearAttemptId()
/* 502:    */     {
/* 503:505 */       this.attemptId = null;
/* 504:506 */       fieldSetFlags()[1] = 0;
/* 505:507 */       return this;
/* 506:    */     }
/* 507:    */     
/* 508:    */     public CharSequence getTaskType()
/* 509:    */     {
/* 510:512 */       return this.taskType;
/* 511:    */     }
/* 512:    */     
/* 513:    */     public Builder setTaskType(CharSequence value)
/* 514:    */     {
/* 515:517 */       validate(fields()[2], value);
/* 516:518 */       this.taskType = value;
/* 517:519 */       fieldSetFlags()[2] = 1;
/* 518:520 */       return this;
/* 519:    */     }
/* 520:    */     
/* 521:    */     public boolean hasTaskType()
/* 522:    */     {
/* 523:525 */       return fieldSetFlags()[2];
/* 524:    */     }
/* 525:    */     
/* 526:    */     public Builder clearTaskType()
/* 527:    */     {
/* 528:530 */       this.taskType = null;
/* 529:531 */       fieldSetFlags()[2] = 0;
/* 530:532 */       return this;
/* 531:    */     }
/* 532:    */     
/* 533:    */     public CharSequence getTaskStatus()
/* 534:    */     {
/* 535:537 */       return this.taskStatus;
/* 536:    */     }
/* 537:    */     
/* 538:    */     public Builder setTaskStatus(CharSequence value)
/* 539:    */     {
/* 540:542 */       validate(fields()[3], value);
/* 541:543 */       this.taskStatus = value;
/* 542:544 */       fieldSetFlags()[3] = 1;
/* 543:545 */       return this;
/* 544:    */     }
/* 545:    */     
/* 546:    */     public boolean hasTaskStatus()
/* 547:    */     {
/* 548:550 */       return fieldSetFlags()[3];
/* 549:    */     }
/* 550:    */     
/* 551:    */     public Builder clearTaskStatus()
/* 552:    */     {
/* 553:555 */       this.taskStatus = null;
/* 554:556 */       fieldSetFlags()[3] = 0;
/* 555:557 */       return this;
/* 556:    */     }
/* 557:    */     
/* 558:    */     public Long getShuffleFinishTime()
/* 559:    */     {
/* 560:562 */       return Long.valueOf(this.shuffleFinishTime);
/* 561:    */     }
/* 562:    */     
/* 563:    */     public Builder setShuffleFinishTime(long value)
/* 564:    */     {
/* 565:567 */       validate(fields()[4], Long.valueOf(value));
/* 566:568 */       this.shuffleFinishTime = value;
/* 567:569 */       fieldSetFlags()[4] = 1;
/* 568:570 */       return this;
/* 569:    */     }
/* 570:    */     
/* 571:    */     public boolean hasShuffleFinishTime()
/* 572:    */     {
/* 573:575 */       return fieldSetFlags()[4];
/* 574:    */     }
/* 575:    */     
/* 576:    */     public Builder clearShuffleFinishTime()
/* 577:    */     {
/* 578:580 */       fieldSetFlags()[4] = 0;
/* 579:581 */       return this;
/* 580:    */     }
/* 581:    */     
/* 582:    */     public Long getSortFinishTime()
/* 583:    */     {
/* 584:586 */       return Long.valueOf(this.sortFinishTime);
/* 585:    */     }
/* 586:    */     
/* 587:    */     public Builder setSortFinishTime(long value)
/* 588:    */     {
/* 589:591 */       validate(fields()[5], Long.valueOf(value));
/* 590:592 */       this.sortFinishTime = value;
/* 591:593 */       fieldSetFlags()[5] = 1;
/* 592:594 */       return this;
/* 593:    */     }
/* 594:    */     
/* 595:    */     public boolean hasSortFinishTime()
/* 596:    */     {
/* 597:599 */       return fieldSetFlags()[5];
/* 598:    */     }
/* 599:    */     
/* 600:    */     public Builder clearSortFinishTime()
/* 601:    */     {
/* 602:604 */       fieldSetFlags()[5] = 0;
/* 603:605 */       return this;
/* 604:    */     }
/* 605:    */     
/* 606:    */     public Long getFinishTime()
/* 607:    */     {
/* 608:610 */       return Long.valueOf(this.finishTime);
/* 609:    */     }
/* 610:    */     
/* 611:    */     public Builder setFinishTime(long value)
/* 612:    */     {
/* 613:615 */       validate(fields()[6], Long.valueOf(value));
/* 614:616 */       this.finishTime = value;
/* 615:617 */       fieldSetFlags()[6] = 1;
/* 616:618 */       return this;
/* 617:    */     }
/* 618:    */     
/* 619:    */     public boolean hasFinishTime()
/* 620:    */     {
/* 621:623 */       return fieldSetFlags()[6];
/* 622:    */     }
/* 623:    */     
/* 624:    */     public Builder clearFinishTime()
/* 625:    */     {
/* 626:628 */       fieldSetFlags()[6] = 0;
/* 627:629 */       return this;
/* 628:    */     }
/* 629:    */     
/* 630:    */     public CharSequence getHostname()
/* 631:    */     {
/* 632:634 */       return this.hostname;
/* 633:    */     }
/* 634:    */     
/* 635:    */     public Builder setHostname(CharSequence value)
/* 636:    */     {
/* 637:639 */       validate(fields()[7], value);
/* 638:640 */       this.hostname = value;
/* 639:641 */       fieldSetFlags()[7] = 1;
/* 640:642 */       return this;
/* 641:    */     }
/* 642:    */     
/* 643:    */     public boolean hasHostname()
/* 644:    */     {
/* 645:647 */       return fieldSetFlags()[7];
/* 646:    */     }
/* 647:    */     
/* 648:    */     public Builder clearHostname()
/* 649:    */     {
/* 650:652 */       this.hostname = null;
/* 651:653 */       fieldSetFlags()[7] = 0;
/* 652:654 */       return this;
/* 653:    */     }
/* 654:    */     
/* 655:    */     public Integer getPort()
/* 656:    */     {
/* 657:659 */       return Integer.valueOf(this.port);
/* 658:    */     }
/* 659:    */     
/* 660:    */     public Builder setPort(int value)
/* 661:    */     {
/* 662:664 */       validate(fields()[8], Integer.valueOf(value));
/* 663:665 */       this.port = value;
/* 664:666 */       fieldSetFlags()[8] = 1;
/* 665:667 */       return this;
/* 666:    */     }
/* 667:    */     
/* 668:    */     public boolean hasPort()
/* 669:    */     {
/* 670:672 */       return fieldSetFlags()[8];
/* 671:    */     }
/* 672:    */     
/* 673:    */     public Builder clearPort()
/* 674:    */     {
/* 675:677 */       fieldSetFlags()[8] = 0;
/* 676:678 */       return this;
/* 677:    */     }
/* 678:    */     
/* 679:    */     public CharSequence getRackname()
/* 680:    */     {
/* 681:683 */       return this.rackname;
/* 682:    */     }
/* 683:    */     
/* 684:    */     public Builder setRackname(CharSequence value)
/* 685:    */     {
/* 686:688 */       validate(fields()[9], value);
/* 687:689 */       this.rackname = value;
/* 688:690 */       fieldSetFlags()[9] = 1;
/* 689:691 */       return this;
/* 690:    */     }
/* 691:    */     
/* 692:    */     public boolean hasRackname()
/* 693:    */     {
/* 694:696 */       return fieldSetFlags()[9];
/* 695:    */     }
/* 696:    */     
/* 697:    */     public Builder clearRackname()
/* 698:    */     {
/* 699:701 */       this.rackname = null;
/* 700:702 */       fieldSetFlags()[9] = 0;
/* 701:703 */       return this;
/* 702:    */     }
/* 703:    */     
/* 704:    */     public CharSequence getState()
/* 705:    */     {
/* 706:708 */       return this.state;
/* 707:    */     }
/* 708:    */     
/* 709:    */     public Builder setState(CharSequence value)
/* 710:    */     {
/* 711:713 */       validate(fields()[10], value);
/* 712:714 */       this.state = value;
/* 713:715 */       fieldSetFlags()[10] = 1;
/* 714:716 */       return this;
/* 715:    */     }
/* 716:    */     
/* 717:    */     public boolean hasState()
/* 718:    */     {
/* 719:721 */       return fieldSetFlags()[10];
/* 720:    */     }
/* 721:    */     
/* 722:    */     public Builder clearState()
/* 723:    */     {
/* 724:726 */       this.state = null;
/* 725:727 */       fieldSetFlags()[10] = 0;
/* 726:728 */       return this;
/* 727:    */     }
/* 728:    */     
/* 729:    */     public JhCounters getCounters()
/* 730:    */     {
/* 731:733 */       return this.counters;
/* 732:    */     }
/* 733:    */     
/* 734:    */     public Builder setCounters(JhCounters value)
/* 735:    */     {
/* 736:738 */       validate(fields()[11], value);
/* 737:739 */       this.counters = value;
/* 738:740 */       fieldSetFlags()[11] = 1;
/* 739:741 */       return this;
/* 740:    */     }
/* 741:    */     
/* 742:    */     public boolean hasCounters()
/* 743:    */     {
/* 744:746 */       return fieldSetFlags()[11];
/* 745:    */     }
/* 746:    */     
/* 747:    */     public Builder clearCounters()
/* 748:    */     {
/* 749:751 */       this.counters = null;
/* 750:752 */       fieldSetFlags()[11] = 0;
/* 751:753 */       return this;
/* 752:    */     }
/* 753:    */     
/* 754:    */     public List<Integer> getClockSplits()
/* 755:    */     {
/* 756:758 */       return this.clockSplits;
/* 757:    */     }
/* 758:    */     
/* 759:    */     public Builder setClockSplits(List<Integer> value)
/* 760:    */     {
/* 761:763 */       validate(fields()[12], value);
/* 762:764 */       this.clockSplits = value;
/* 763:765 */       fieldSetFlags()[12] = 1;
/* 764:766 */       return this;
/* 765:    */     }
/* 766:    */     
/* 767:    */     public boolean hasClockSplits()
/* 768:    */     {
/* 769:771 */       return fieldSetFlags()[12];
/* 770:    */     }
/* 771:    */     
/* 772:    */     public Builder clearClockSplits()
/* 773:    */     {
/* 774:776 */       this.clockSplits = null;
/* 775:777 */       fieldSetFlags()[12] = 0;
/* 776:778 */       return this;
/* 777:    */     }
/* 778:    */     
/* 779:    */     public List<Integer> getCpuUsages()
/* 780:    */     {
/* 781:783 */       return this.cpuUsages;
/* 782:    */     }
/* 783:    */     
/* 784:    */     public Builder setCpuUsages(List<Integer> value)
/* 785:    */     {
/* 786:788 */       validate(fields()[13], value);
/* 787:789 */       this.cpuUsages = value;
/* 788:790 */       fieldSetFlags()[13] = 1;
/* 789:791 */       return this;
/* 790:    */     }
/* 791:    */     
/* 792:    */     public boolean hasCpuUsages()
/* 793:    */     {
/* 794:796 */       return fieldSetFlags()[13];
/* 795:    */     }
/* 796:    */     
/* 797:    */     public Builder clearCpuUsages()
/* 798:    */     {
/* 799:801 */       this.cpuUsages = null;
/* 800:802 */       fieldSetFlags()[13] = 0;
/* 801:803 */       return this;
/* 802:    */     }
/* 803:    */     
/* 804:    */     public List<Integer> getVMemKbytes()
/* 805:    */     {
/* 806:808 */       return this.vMemKbytes;
/* 807:    */     }
/* 808:    */     
/* 809:    */     public Builder setVMemKbytes(List<Integer> value)
/* 810:    */     {
/* 811:813 */       validate(fields()[14], value);
/* 812:814 */       this.vMemKbytes = value;
/* 813:815 */       fieldSetFlags()[14] = 1;
/* 814:816 */       return this;
/* 815:    */     }
/* 816:    */     
/* 817:    */     public boolean hasVMemKbytes()
/* 818:    */     {
/* 819:821 */       return fieldSetFlags()[14];
/* 820:    */     }
/* 821:    */     
/* 822:    */     public Builder clearVMemKbytes()
/* 823:    */     {
/* 824:826 */       this.vMemKbytes = null;
/* 825:827 */       fieldSetFlags()[14] = 0;
/* 826:828 */       return this;
/* 827:    */     }
/* 828:    */     
/* 829:    */     public List<Integer> getPhysMemKbytes()
/* 830:    */     {
/* 831:833 */       return this.physMemKbytes;
/* 832:    */     }
/* 833:    */     
/* 834:    */     public Builder setPhysMemKbytes(List<Integer> value)
/* 835:    */     {
/* 836:838 */       validate(fields()[15], value);
/* 837:839 */       this.physMemKbytes = value;
/* 838:840 */       fieldSetFlags()[15] = 1;
/* 839:841 */       return this;
/* 840:    */     }
/* 841:    */     
/* 842:    */     public boolean hasPhysMemKbytes()
/* 843:    */     {
/* 844:846 */       return fieldSetFlags()[15];
/* 845:    */     }
/* 846:    */     
/* 847:    */     public Builder clearPhysMemKbytes()
/* 848:    */     {
/* 849:851 */       this.physMemKbytes = null;
/* 850:852 */       fieldSetFlags()[15] = 0;
/* 851:853 */       return this;
/* 852:    */     }
/* 853:    */     
/* 854:    */     public ReduceAttemptFinished build()
/* 855:    */     {
/* 856:    */       try
/* 857:    */       {
/* 858:859 */         ReduceAttemptFinished record = new ReduceAttemptFinished();
/* 859:860 */         record.taskid = (fieldSetFlags()[0] != 0 ? this.taskid : (CharSequence)defaultValue(fields()[0]));
/* 860:861 */         record.attemptId = (fieldSetFlags()[1] != 0 ? this.attemptId : (CharSequence)defaultValue(fields()[1]));
/* 861:862 */         record.taskType = (fieldSetFlags()[2] != 0 ? this.taskType : (CharSequence)defaultValue(fields()[2]));
/* 862:863 */         record.taskStatus = (fieldSetFlags()[3] != 0 ? this.taskStatus : (CharSequence)defaultValue(fields()[3]));
/* 863:864 */         record.shuffleFinishTime = (fieldSetFlags()[4] != 0 ? this.shuffleFinishTime : ((Long)defaultValue(fields()[4])).longValue());
/* 864:865 */         record.sortFinishTime = (fieldSetFlags()[5] != 0 ? this.sortFinishTime : ((Long)defaultValue(fields()[5])).longValue());
/* 865:866 */         record.finishTime = (fieldSetFlags()[6] != 0 ? this.finishTime : ((Long)defaultValue(fields()[6])).longValue());
/* 866:867 */         record.hostname = (fieldSetFlags()[7] != 0 ? this.hostname : (CharSequence)defaultValue(fields()[7]));
/* 867:868 */         record.port = (fieldSetFlags()[8] != 0 ? this.port : ((Integer)defaultValue(fields()[8])).intValue());
/* 868:869 */         record.rackname = (fieldSetFlags()[9] != 0 ? this.rackname : (CharSequence)defaultValue(fields()[9]));
/* 869:870 */         record.state = (fieldSetFlags()[10] != 0 ? this.state : (CharSequence)defaultValue(fields()[10]));
/* 870:871 */         record.counters = (fieldSetFlags()[11] != 0 ? this.counters : (JhCounters)defaultValue(fields()[11]));
/* 871:872 */         record.clockSplits = (fieldSetFlags()[12] != 0 ? this.clockSplits : (List)defaultValue(fields()[12]));
/* 872:873 */         record.cpuUsages = (fieldSetFlags()[13] != 0 ? this.cpuUsages : (List)defaultValue(fields()[13]));
/* 873:874 */         record.vMemKbytes = (fieldSetFlags()[14] != 0 ? this.vMemKbytes : (List)defaultValue(fields()[14]));
/* 874:875 */         record.physMemKbytes = (fieldSetFlags()[15] != 0 ? this.physMemKbytes : (List)defaultValue(fields()[15]));
/* 875:876 */         return record;
/* 876:    */       }
/* 877:    */       catch (Exception e)
/* 878:    */       {
/* 879:878 */         throw new AvroRuntimeException(e);
/* 880:    */       }
/* 881:    */     }
/* 882:    */   }
/* 883:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.jobhistory.ReduceAttemptFinished
 * JD-Core Version:    0.7.0.1
 */