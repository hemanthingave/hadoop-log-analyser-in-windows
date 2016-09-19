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
/*  15:    */ public class JobFinished
/*  16:    */   extends SpecificRecordBase
/*  17:    */   implements SpecificRecord
/*  18:    */ {
/*  19: 10 */   public static final Schema SCHEMA$ = new Schema.Parser().parse("{\"type\":\"record\",\"name\":\"JobFinished\",\"namespace\":\"org.apache.hadoop.mapreduce.jobhistory\",\"fields\":[{\"name\":\"jobid\",\"type\":\"string\"},{\"name\":\"finishTime\",\"type\":\"long\"},{\"name\":\"finishedMaps\",\"type\":\"int\"},{\"name\":\"finishedReduces\",\"type\":\"int\"},{\"name\":\"failedMaps\",\"type\":\"int\"},{\"name\":\"failedReduces\",\"type\":\"int\"},{\"name\":\"totalCounters\",\"type\":{\"type\":\"record\",\"name\":\"JhCounters\",\"fields\":[{\"name\":\"name\",\"type\":\"string\"},{\"name\":\"groups\",\"type\":{\"type\":\"array\",\"items\":{\"type\":\"record\",\"name\":\"JhCounterGroup\",\"fields\":[{\"name\":\"name\",\"type\":\"string\"},{\"name\":\"displayName\",\"type\":\"string\"},{\"name\":\"counts\",\"type\":{\"type\":\"array\",\"items\":{\"type\":\"record\",\"name\":\"JhCounter\",\"fields\":[{\"name\":\"name\",\"type\":\"string\"},{\"name\":\"displayName\",\"type\":\"string\"},{\"name\":\"value\",\"type\":\"long\"}]}}}]}}}]}},{\"name\":\"mapCounters\",\"type\":\"JhCounters\"},{\"name\":\"reduceCounters\",\"type\":\"JhCounters\"}]}");
/*  20:    */   @Deprecated
/*  21:    */   public CharSequence jobid;
/*  22:    */   @Deprecated
/*  23:    */   public long finishTime;
/*  24:    */   @Deprecated
/*  25:    */   public int finishedMaps;
/*  26:    */   @Deprecated
/*  27:    */   public int finishedReduces;
/*  28:    */   @Deprecated
/*  29:    */   public int failedMaps;
/*  30:    */   @Deprecated
/*  31:    */   public int failedReduces;
/*  32:    */   @Deprecated
/*  33:    */   public JhCounters totalCounters;
/*  34:    */   @Deprecated
/*  35:    */   public JhCounters mapCounters;
/*  36:    */   @Deprecated
/*  37:    */   public JhCounters reduceCounters;
/*  38:    */   
/*  39:    */   public static Schema getClassSchema()
/*  40:    */   {
/*  41: 11 */     return SCHEMA$;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public JobFinished() {}
/*  45:    */   
/*  46:    */   public JobFinished(CharSequence jobid, Long finishTime, Integer finishedMaps, Integer finishedReduces, Integer failedMaps, Integer failedReduces, JhCounters totalCounters, JhCounters mapCounters, JhCounters reduceCounters)
/*  47:    */   {
/*  48: 31 */     this.jobid = jobid;
/*  49: 32 */     this.finishTime = finishTime.longValue();
/*  50: 33 */     this.finishedMaps = finishedMaps.intValue();
/*  51: 34 */     this.finishedReduces = finishedReduces.intValue();
/*  52: 35 */     this.failedMaps = failedMaps.intValue();
/*  53: 36 */     this.failedReduces = failedReduces.intValue();
/*  54: 37 */     this.totalCounters = totalCounters;
/*  55: 38 */     this.mapCounters = mapCounters;
/*  56: 39 */     this.reduceCounters = reduceCounters;
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
/*  69: 46 */       return this.jobid;
/*  70:    */     case 1: 
/*  71: 47 */       return Long.valueOf(this.finishTime);
/*  72:    */     case 2: 
/*  73: 48 */       return Integer.valueOf(this.finishedMaps);
/*  74:    */     case 3: 
/*  75: 49 */       return Integer.valueOf(this.finishedReduces);
/*  76:    */     case 4: 
/*  77: 50 */       return Integer.valueOf(this.failedMaps);
/*  78:    */     case 5: 
/*  79: 51 */       return Integer.valueOf(this.failedReduces);
/*  80:    */     case 6: 
/*  81: 52 */       return this.totalCounters;
/*  82:    */     case 7: 
/*  83: 53 */       return this.mapCounters;
/*  84:    */     case 8: 
/*  85: 54 */       return this.reduceCounters;
/*  86:    */     }
/*  87: 55 */     throw new AvroRuntimeException("Bad index");
/*  88:    */   }
/*  89:    */   
/*  90:    */   public void put(int field$, Object value$)
/*  91:    */   {
/*  92: 61 */     switch (field$)
/*  93:    */     {
/*  94:    */     case 0: 
/*  95: 62 */       this.jobid = ((CharSequence)value$); break;
/*  96:    */     case 1: 
/*  97: 63 */       this.finishTime = ((Long)value$).longValue(); break;
/*  98:    */     case 2: 
/*  99: 64 */       this.finishedMaps = ((Integer)value$).intValue(); break;
/* 100:    */     case 3: 
/* 101: 65 */       this.finishedReduces = ((Integer)value$).intValue(); break;
/* 102:    */     case 4: 
/* 103: 66 */       this.failedMaps = ((Integer)value$).intValue(); break;
/* 104:    */     case 5: 
/* 105: 67 */       this.failedReduces = ((Integer)value$).intValue(); break;
/* 106:    */     case 6: 
/* 107: 68 */       this.totalCounters = ((JhCounters)value$); break;
/* 108:    */     case 7: 
/* 109: 69 */       this.mapCounters = ((JhCounters)value$); break;
/* 110:    */     case 8: 
/* 111: 70 */       this.reduceCounters = ((JhCounters)value$); break;
/* 112:    */     default: 
/* 113: 71 */       throw new AvroRuntimeException("Bad index");
/* 114:    */     }
/* 115:    */   }
/* 116:    */   
/* 117:    */   public CharSequence getJobid()
/* 118:    */   {
/* 119: 79 */     return this.jobid;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public void setJobid(CharSequence value)
/* 123:    */   {
/* 124: 87 */     this.jobid = value;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public Long getFinishTime()
/* 128:    */   {
/* 129: 94 */     return Long.valueOf(this.finishTime);
/* 130:    */   }
/* 131:    */   
/* 132:    */   public void setFinishTime(Long value)
/* 133:    */   {
/* 134:102 */     this.finishTime = value.longValue();
/* 135:    */   }
/* 136:    */   
/* 137:    */   public Integer getFinishedMaps()
/* 138:    */   {
/* 139:109 */     return Integer.valueOf(this.finishedMaps);
/* 140:    */   }
/* 141:    */   
/* 142:    */   public void setFinishedMaps(Integer value)
/* 143:    */   {
/* 144:117 */     this.finishedMaps = value.intValue();
/* 145:    */   }
/* 146:    */   
/* 147:    */   public Integer getFinishedReduces()
/* 148:    */   {
/* 149:124 */     return Integer.valueOf(this.finishedReduces);
/* 150:    */   }
/* 151:    */   
/* 152:    */   public void setFinishedReduces(Integer value)
/* 153:    */   {
/* 154:132 */     this.finishedReduces = value.intValue();
/* 155:    */   }
/* 156:    */   
/* 157:    */   public Integer getFailedMaps()
/* 158:    */   {
/* 159:139 */     return Integer.valueOf(this.failedMaps);
/* 160:    */   }
/* 161:    */   
/* 162:    */   public void setFailedMaps(Integer value)
/* 163:    */   {
/* 164:147 */     this.failedMaps = value.intValue();
/* 165:    */   }
/* 166:    */   
/* 167:    */   public Integer getFailedReduces()
/* 168:    */   {
/* 169:154 */     return Integer.valueOf(this.failedReduces);
/* 170:    */   }
/* 171:    */   
/* 172:    */   public void setFailedReduces(Integer value)
/* 173:    */   {
/* 174:162 */     this.failedReduces = value.intValue();
/* 175:    */   }
/* 176:    */   
/* 177:    */   public JhCounters getTotalCounters()
/* 178:    */   {
/* 179:169 */     return this.totalCounters;
/* 180:    */   }
/* 181:    */   
/* 182:    */   public void setTotalCounters(JhCounters value)
/* 183:    */   {
/* 184:177 */     this.totalCounters = value;
/* 185:    */   }
/* 186:    */   
/* 187:    */   public JhCounters getMapCounters()
/* 188:    */   {
/* 189:184 */     return this.mapCounters;
/* 190:    */   }
/* 191:    */   
/* 192:    */   public void setMapCounters(JhCounters value)
/* 193:    */   {
/* 194:192 */     this.mapCounters = value;
/* 195:    */   }
/* 196:    */   
/* 197:    */   public JhCounters getReduceCounters()
/* 198:    */   {
/* 199:199 */     return this.reduceCounters;
/* 200:    */   }
/* 201:    */   
/* 202:    */   public void setReduceCounters(JhCounters value)
/* 203:    */   {
/* 204:207 */     this.reduceCounters = value;
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
/* 217:    */   public static Builder newBuilder(JobFinished other)
/* 218:    */   {
/* 219:222 */     return new Builder(other, null);
/* 220:    */   }
/* 221:    */   
/* 222:    */   public static class Builder
/* 223:    */     extends SpecificRecordBuilderBase<JobFinished>
/* 224:    */     implements RecordBuilder<JobFinished>
/* 225:    */   {
/* 226:    */     private CharSequence jobid;
/* 227:    */     private long finishTime;
/* 228:    */     private int finishedMaps;
/* 229:    */     private int finishedReduces;
/* 230:    */     private int failedMaps;
/* 231:    */     private int failedReduces;
/* 232:    */     private JhCounters totalCounters;
/* 233:    */     private JhCounters mapCounters;
/* 234:    */     private JhCounters reduceCounters;
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
/* 246:    */     private Builder(JobFinished other)
/* 247:    */     {
/* 248:253 */       super();
/* 249:254 */       if (isValidValue(fields()[0], other.jobid))
/* 250:    */       {
/* 251:255 */         this.jobid = ((CharSequence)data().deepCopy(fields()[0].schema(), other.jobid));
/* 252:256 */         fieldSetFlags()[0] = 1;
/* 253:    */       }
/* 254:258 */       if (isValidValue(fields()[1], Long.valueOf(other.finishTime)))
/* 255:    */       {
/* 256:259 */         this.finishTime = ((Long)data().deepCopy(fields()[1].schema(), Long.valueOf(other.finishTime))).longValue();
/* 257:260 */         fieldSetFlags()[1] = 1;
/* 258:    */       }
/* 259:262 */       if (isValidValue(fields()[2], Integer.valueOf(other.finishedMaps)))
/* 260:    */       {
/* 261:263 */         this.finishedMaps = ((Integer)data().deepCopy(fields()[2].schema(), Integer.valueOf(other.finishedMaps))).intValue();
/* 262:264 */         fieldSetFlags()[2] = 1;
/* 263:    */       }
/* 264:266 */       if (isValidValue(fields()[3], Integer.valueOf(other.finishedReduces)))
/* 265:    */       {
/* 266:267 */         this.finishedReduces = ((Integer)data().deepCopy(fields()[3].schema(), Integer.valueOf(other.finishedReduces))).intValue();
/* 267:268 */         fieldSetFlags()[3] = 1;
/* 268:    */       }
/* 269:270 */       if (isValidValue(fields()[4], Integer.valueOf(other.failedMaps)))
/* 270:    */       {
/* 271:271 */         this.failedMaps = ((Integer)data().deepCopy(fields()[4].schema(), Integer.valueOf(other.failedMaps))).intValue();
/* 272:272 */         fieldSetFlags()[4] = 1;
/* 273:    */       }
/* 274:274 */       if (isValidValue(fields()[5], Integer.valueOf(other.failedReduces)))
/* 275:    */       {
/* 276:275 */         this.failedReduces = ((Integer)data().deepCopy(fields()[5].schema(), Integer.valueOf(other.failedReduces))).intValue();
/* 277:276 */         fieldSetFlags()[5] = 1;
/* 278:    */       }
/* 279:278 */       if (isValidValue(fields()[6], other.totalCounters))
/* 280:    */       {
/* 281:279 */         this.totalCounters = ((JhCounters)data().deepCopy(fields()[6].schema(), other.totalCounters));
/* 282:280 */         fieldSetFlags()[6] = 1;
/* 283:    */       }
/* 284:282 */       if (isValidValue(fields()[7], other.mapCounters))
/* 285:    */       {
/* 286:283 */         this.mapCounters = ((JhCounters)data().deepCopy(fields()[7].schema(), other.mapCounters));
/* 287:284 */         fieldSetFlags()[7] = 1;
/* 288:    */       }
/* 289:286 */       if (isValidValue(fields()[8], other.reduceCounters))
/* 290:    */       {
/* 291:287 */         this.reduceCounters = ((JhCounters)data().deepCopy(fields()[8].schema(), other.reduceCounters));
/* 292:288 */         fieldSetFlags()[8] = 1;
/* 293:    */       }
/* 294:    */     }
/* 295:    */     
/* 296:    */     public CharSequence getJobid()
/* 297:    */     {
/* 298:294 */       return this.jobid;
/* 299:    */     }
/* 300:    */     
/* 301:    */     public Builder setJobid(CharSequence value)
/* 302:    */     {
/* 303:299 */       validate(fields()[0], value);
/* 304:300 */       this.jobid = value;
/* 305:301 */       fieldSetFlags()[0] = 1;
/* 306:302 */       return this;
/* 307:    */     }
/* 308:    */     
/* 309:    */     public boolean hasJobid()
/* 310:    */     {
/* 311:307 */       return fieldSetFlags()[0];
/* 312:    */     }
/* 313:    */     
/* 314:    */     public Builder clearJobid()
/* 315:    */     {
/* 316:312 */       this.jobid = null;
/* 317:313 */       fieldSetFlags()[0] = 0;
/* 318:314 */       return this;
/* 319:    */     }
/* 320:    */     
/* 321:    */     public Long getFinishTime()
/* 322:    */     {
/* 323:319 */       return Long.valueOf(this.finishTime);
/* 324:    */     }
/* 325:    */     
/* 326:    */     public Builder setFinishTime(long value)
/* 327:    */     {
/* 328:324 */       validate(fields()[1], Long.valueOf(value));
/* 329:325 */       this.finishTime = value;
/* 330:326 */       fieldSetFlags()[1] = 1;
/* 331:327 */       return this;
/* 332:    */     }
/* 333:    */     
/* 334:    */     public boolean hasFinishTime()
/* 335:    */     {
/* 336:332 */       return fieldSetFlags()[1];
/* 337:    */     }
/* 338:    */     
/* 339:    */     public Builder clearFinishTime()
/* 340:    */     {
/* 341:337 */       fieldSetFlags()[1] = 0;
/* 342:338 */       return this;
/* 343:    */     }
/* 344:    */     
/* 345:    */     public Integer getFinishedMaps()
/* 346:    */     {
/* 347:343 */       return Integer.valueOf(this.finishedMaps);
/* 348:    */     }
/* 349:    */     
/* 350:    */     public Builder setFinishedMaps(int value)
/* 351:    */     {
/* 352:348 */       validate(fields()[2], Integer.valueOf(value));
/* 353:349 */       this.finishedMaps = value;
/* 354:350 */       fieldSetFlags()[2] = 1;
/* 355:351 */       return this;
/* 356:    */     }
/* 357:    */     
/* 358:    */     public boolean hasFinishedMaps()
/* 359:    */     {
/* 360:356 */       return fieldSetFlags()[2];
/* 361:    */     }
/* 362:    */     
/* 363:    */     public Builder clearFinishedMaps()
/* 364:    */     {
/* 365:361 */       fieldSetFlags()[2] = 0;
/* 366:362 */       return this;
/* 367:    */     }
/* 368:    */     
/* 369:    */     public Integer getFinishedReduces()
/* 370:    */     {
/* 371:367 */       return Integer.valueOf(this.finishedReduces);
/* 372:    */     }
/* 373:    */     
/* 374:    */     public Builder setFinishedReduces(int value)
/* 375:    */     {
/* 376:372 */       validate(fields()[3], Integer.valueOf(value));
/* 377:373 */       this.finishedReduces = value;
/* 378:374 */       fieldSetFlags()[3] = 1;
/* 379:375 */       return this;
/* 380:    */     }
/* 381:    */     
/* 382:    */     public boolean hasFinishedReduces()
/* 383:    */     {
/* 384:380 */       return fieldSetFlags()[3];
/* 385:    */     }
/* 386:    */     
/* 387:    */     public Builder clearFinishedReduces()
/* 388:    */     {
/* 389:385 */       fieldSetFlags()[3] = 0;
/* 390:386 */       return this;
/* 391:    */     }
/* 392:    */     
/* 393:    */     public Integer getFailedMaps()
/* 394:    */     {
/* 395:391 */       return Integer.valueOf(this.failedMaps);
/* 396:    */     }
/* 397:    */     
/* 398:    */     public Builder setFailedMaps(int value)
/* 399:    */     {
/* 400:396 */       validate(fields()[4], Integer.valueOf(value));
/* 401:397 */       this.failedMaps = value;
/* 402:398 */       fieldSetFlags()[4] = 1;
/* 403:399 */       return this;
/* 404:    */     }
/* 405:    */     
/* 406:    */     public boolean hasFailedMaps()
/* 407:    */     {
/* 408:404 */       return fieldSetFlags()[4];
/* 409:    */     }
/* 410:    */     
/* 411:    */     public Builder clearFailedMaps()
/* 412:    */     {
/* 413:409 */       fieldSetFlags()[4] = 0;
/* 414:410 */       return this;
/* 415:    */     }
/* 416:    */     
/* 417:    */     public Integer getFailedReduces()
/* 418:    */     {
/* 419:415 */       return Integer.valueOf(this.failedReduces);
/* 420:    */     }
/* 421:    */     
/* 422:    */     public Builder setFailedReduces(int value)
/* 423:    */     {
/* 424:420 */       validate(fields()[5], Integer.valueOf(value));
/* 425:421 */       this.failedReduces = value;
/* 426:422 */       fieldSetFlags()[5] = 1;
/* 427:423 */       return this;
/* 428:    */     }
/* 429:    */     
/* 430:    */     public boolean hasFailedReduces()
/* 431:    */     {
/* 432:428 */       return fieldSetFlags()[5];
/* 433:    */     }
/* 434:    */     
/* 435:    */     public Builder clearFailedReduces()
/* 436:    */     {
/* 437:433 */       fieldSetFlags()[5] = 0;
/* 438:434 */       return this;
/* 439:    */     }
/* 440:    */     
/* 441:    */     public JhCounters getTotalCounters()
/* 442:    */     {
/* 443:439 */       return this.totalCounters;
/* 444:    */     }
/* 445:    */     
/* 446:    */     public Builder setTotalCounters(JhCounters value)
/* 447:    */     {
/* 448:444 */       validate(fields()[6], value);
/* 449:445 */       this.totalCounters = value;
/* 450:446 */       fieldSetFlags()[6] = 1;
/* 451:447 */       return this;
/* 452:    */     }
/* 453:    */     
/* 454:    */     public boolean hasTotalCounters()
/* 455:    */     {
/* 456:452 */       return fieldSetFlags()[6];
/* 457:    */     }
/* 458:    */     
/* 459:    */     public Builder clearTotalCounters()
/* 460:    */     {
/* 461:457 */       this.totalCounters = null;
/* 462:458 */       fieldSetFlags()[6] = 0;
/* 463:459 */       return this;
/* 464:    */     }
/* 465:    */     
/* 466:    */     public JhCounters getMapCounters()
/* 467:    */     {
/* 468:464 */       return this.mapCounters;
/* 469:    */     }
/* 470:    */     
/* 471:    */     public Builder setMapCounters(JhCounters value)
/* 472:    */     {
/* 473:469 */       validate(fields()[7], value);
/* 474:470 */       this.mapCounters = value;
/* 475:471 */       fieldSetFlags()[7] = 1;
/* 476:472 */       return this;
/* 477:    */     }
/* 478:    */     
/* 479:    */     public boolean hasMapCounters()
/* 480:    */     {
/* 481:477 */       return fieldSetFlags()[7];
/* 482:    */     }
/* 483:    */     
/* 484:    */     public Builder clearMapCounters()
/* 485:    */     {
/* 486:482 */       this.mapCounters = null;
/* 487:483 */       fieldSetFlags()[7] = 0;
/* 488:484 */       return this;
/* 489:    */     }
/* 490:    */     
/* 491:    */     public JhCounters getReduceCounters()
/* 492:    */     {
/* 493:489 */       return this.reduceCounters;
/* 494:    */     }
/* 495:    */     
/* 496:    */     public Builder setReduceCounters(JhCounters value)
/* 497:    */     {
/* 498:494 */       validate(fields()[8], value);
/* 499:495 */       this.reduceCounters = value;
/* 500:496 */       fieldSetFlags()[8] = 1;
/* 501:497 */       return this;
/* 502:    */     }
/* 503:    */     
/* 504:    */     public boolean hasReduceCounters()
/* 505:    */     {
/* 506:502 */       return fieldSetFlags()[8];
/* 507:    */     }
/* 508:    */     
/* 509:    */     public Builder clearReduceCounters()
/* 510:    */     {
/* 511:507 */       this.reduceCounters = null;
/* 512:508 */       fieldSetFlags()[8] = 0;
/* 513:509 */       return this;
/* 514:    */     }
/* 515:    */     
/* 516:    */     public JobFinished build()
/* 517:    */     {
/* 518:    */       try
/* 519:    */       {
/* 520:515 */         JobFinished record = new JobFinished();
/* 521:516 */         record.jobid = (fieldSetFlags()[0] != 0 ? this.jobid : (CharSequence)defaultValue(fields()[0]));
/* 522:517 */         record.finishTime = (fieldSetFlags()[1] != 0 ? this.finishTime : ((Long)defaultValue(fields()[1])).longValue());
/* 523:518 */         record.finishedMaps = (fieldSetFlags()[2] != 0 ? this.finishedMaps : ((Integer)defaultValue(fields()[2])).intValue());
/* 524:519 */         record.finishedReduces = (fieldSetFlags()[3] != 0 ? this.finishedReduces : ((Integer)defaultValue(fields()[3])).intValue());
/* 525:520 */         record.failedMaps = (fieldSetFlags()[4] != 0 ? this.failedMaps : ((Integer)defaultValue(fields()[4])).intValue());
/* 526:521 */         record.failedReduces = (fieldSetFlags()[5] != 0 ? this.failedReduces : ((Integer)defaultValue(fields()[5])).intValue());
/* 527:522 */         record.totalCounters = (fieldSetFlags()[6] != 0 ? this.totalCounters : (JhCounters)defaultValue(fields()[6]));
/* 528:523 */         record.mapCounters = (fieldSetFlags()[7] != 0 ? this.mapCounters : (JhCounters)defaultValue(fields()[7]));
/* 529:524 */         record.reduceCounters = (fieldSetFlags()[8] != 0 ? this.reduceCounters : (JhCounters)defaultValue(fields()[8]));
/* 530:525 */         return record;
/* 531:    */       }
/* 532:    */       catch (Exception e)
/* 533:    */       {
/* 534:527 */         throw new AvroRuntimeException(e);
/* 535:    */       }
/* 536:    */     }
/* 537:    */   }
/* 538:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.jobhistory.JobFinished
 * JD-Core Version:    0.7.0.1
 */