/*   1:    */ package org.apache.hadoop.mapreduce.jobhistory;
/*   2:    */ 
/*   3:    */ import java.util.Map;
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
/*  16:    */ public class JobSubmitted
/*  17:    */   extends SpecificRecordBase
/*  18:    */   implements SpecificRecord
/*  19:    */ {
/*  20: 10 */   public static final Schema SCHEMA$ = new Schema.Parser().parse("{\"type\":\"record\",\"name\":\"JobSubmitted\",\"namespace\":\"org.apache.hadoop.mapreduce.jobhistory\",\"fields\":[{\"name\":\"jobid\",\"type\":\"string\"},{\"name\":\"jobName\",\"type\":\"string\"},{\"name\":\"userName\",\"type\":\"string\"},{\"name\":\"submitTime\",\"type\":\"long\"},{\"name\":\"jobConfPath\",\"type\":\"string\"},{\"name\":\"acls\",\"type\":{\"type\":\"map\",\"values\":\"string\"}},{\"name\":\"jobQueueName\",\"type\":\"string\"},{\"name\":\"workflowId\",\"type\":\"string\"},{\"name\":\"workflowName\",\"type\":\"string\"},{\"name\":\"workflowNodeName\",\"type\":\"string\"},{\"name\":\"workflowAdjacencies\",\"type\":\"string\"},{\"name\":\"workflowTags\",\"type\":\"string\"}]}");
/*  21:    */   @Deprecated
/*  22:    */   public CharSequence jobid;
/*  23:    */   @Deprecated
/*  24:    */   public CharSequence jobName;
/*  25:    */   @Deprecated
/*  26:    */   public CharSequence userName;
/*  27:    */   @Deprecated
/*  28:    */   public long submitTime;
/*  29:    */   @Deprecated
/*  30:    */   public CharSequence jobConfPath;
/*  31:    */   @Deprecated
/*  32:    */   public Map<CharSequence, CharSequence> acls;
/*  33:    */   @Deprecated
/*  34:    */   public CharSequence jobQueueName;
/*  35:    */   @Deprecated
/*  36:    */   public CharSequence workflowId;
/*  37:    */   @Deprecated
/*  38:    */   public CharSequence workflowName;
/*  39:    */   @Deprecated
/*  40:    */   public CharSequence workflowNodeName;
/*  41:    */   @Deprecated
/*  42:    */   public CharSequence workflowAdjacencies;
/*  43:    */   @Deprecated
/*  44:    */   public CharSequence workflowTags;
/*  45:    */   
/*  46:    */   public static Schema getClassSchema()
/*  47:    */   {
/*  48: 11 */     return SCHEMA$;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public JobSubmitted() {}
/*  52:    */   
/*  53:    */   public JobSubmitted(CharSequence jobid, CharSequence jobName, CharSequence userName, Long submitTime, CharSequence jobConfPath, Map<CharSequence, CharSequence> acls, CharSequence jobQueueName, CharSequence workflowId, CharSequence workflowName, CharSequence workflowNodeName, CharSequence workflowAdjacencies, CharSequence workflowTags)
/*  54:    */   {
/*  55: 34 */     this.jobid = jobid;
/*  56: 35 */     this.jobName = jobName;
/*  57: 36 */     this.userName = userName;
/*  58: 37 */     this.submitTime = submitTime.longValue();
/*  59: 38 */     this.jobConfPath = jobConfPath;
/*  60: 39 */     this.acls = acls;
/*  61: 40 */     this.jobQueueName = jobQueueName;
/*  62: 41 */     this.workflowId = workflowId;
/*  63: 42 */     this.workflowName = workflowName;
/*  64: 43 */     this.workflowNodeName = workflowNodeName;
/*  65: 44 */     this.workflowAdjacencies = workflowAdjacencies;
/*  66: 45 */     this.workflowTags = workflowTags;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public Schema getSchema()
/*  70:    */   {
/*  71: 48 */     return SCHEMA$;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public Object get(int field$)
/*  75:    */   {
/*  76: 51 */     switch (field$)
/*  77:    */     {
/*  78:    */     case 0: 
/*  79: 52 */       return this.jobid;
/*  80:    */     case 1: 
/*  81: 53 */       return this.jobName;
/*  82:    */     case 2: 
/*  83: 54 */       return this.userName;
/*  84:    */     case 3: 
/*  85: 55 */       return Long.valueOf(this.submitTime);
/*  86:    */     case 4: 
/*  87: 56 */       return this.jobConfPath;
/*  88:    */     case 5: 
/*  89: 57 */       return this.acls;
/*  90:    */     case 6: 
/*  91: 58 */       return this.jobQueueName;
/*  92:    */     case 7: 
/*  93: 59 */       return this.workflowId;
/*  94:    */     case 8: 
/*  95: 60 */       return this.workflowName;
/*  96:    */     case 9: 
/*  97: 61 */       return this.workflowNodeName;
/*  98:    */     case 10: 
/*  99: 62 */       return this.workflowAdjacencies;
/* 100:    */     case 11: 
/* 101: 63 */       return this.workflowTags;
/* 102:    */     }
/* 103: 64 */     throw new AvroRuntimeException("Bad index");
/* 104:    */   }
/* 105:    */   
/* 106:    */   public void put(int field$, Object value$)
/* 107:    */   {
/* 108: 70 */     switch (field$)
/* 109:    */     {
/* 110:    */     case 0: 
/* 111: 71 */       this.jobid = ((CharSequence)value$); break;
/* 112:    */     case 1: 
/* 113: 72 */       this.jobName = ((CharSequence)value$); break;
/* 114:    */     case 2: 
/* 115: 73 */       this.userName = ((CharSequence)value$); break;
/* 116:    */     case 3: 
/* 117: 74 */       this.submitTime = ((Long)value$).longValue(); break;
/* 118:    */     case 4: 
/* 119: 75 */       this.jobConfPath = ((CharSequence)value$); break;
/* 120:    */     case 5: 
/* 121: 76 */       this.acls = ((Map)value$); break;
/* 122:    */     case 6: 
/* 123: 77 */       this.jobQueueName = ((CharSequence)value$); break;
/* 124:    */     case 7: 
/* 125: 78 */       this.workflowId = ((CharSequence)value$); break;
/* 126:    */     case 8: 
/* 127: 79 */       this.workflowName = ((CharSequence)value$); break;
/* 128:    */     case 9: 
/* 129: 80 */       this.workflowNodeName = ((CharSequence)value$); break;
/* 130:    */     case 10: 
/* 131: 81 */       this.workflowAdjacencies = ((CharSequence)value$); break;
/* 132:    */     case 11: 
/* 133: 82 */       this.workflowTags = ((CharSequence)value$); break;
/* 134:    */     default: 
/* 135: 83 */       throw new AvroRuntimeException("Bad index");
/* 136:    */     }
/* 137:    */   }
/* 138:    */   
/* 139:    */   public CharSequence getJobid()
/* 140:    */   {
/* 141: 91 */     return this.jobid;
/* 142:    */   }
/* 143:    */   
/* 144:    */   public void setJobid(CharSequence value)
/* 145:    */   {
/* 146: 99 */     this.jobid = value;
/* 147:    */   }
/* 148:    */   
/* 149:    */   public CharSequence getJobName()
/* 150:    */   {
/* 151:106 */     return this.jobName;
/* 152:    */   }
/* 153:    */   
/* 154:    */   public void setJobName(CharSequence value)
/* 155:    */   {
/* 156:114 */     this.jobName = value;
/* 157:    */   }
/* 158:    */   
/* 159:    */   public CharSequence getUserName()
/* 160:    */   {
/* 161:121 */     return this.userName;
/* 162:    */   }
/* 163:    */   
/* 164:    */   public void setUserName(CharSequence value)
/* 165:    */   {
/* 166:129 */     this.userName = value;
/* 167:    */   }
/* 168:    */   
/* 169:    */   public Long getSubmitTime()
/* 170:    */   {
/* 171:136 */     return Long.valueOf(this.submitTime);
/* 172:    */   }
/* 173:    */   
/* 174:    */   public void setSubmitTime(Long value)
/* 175:    */   {
/* 176:144 */     this.submitTime = value.longValue();
/* 177:    */   }
/* 178:    */   
/* 179:    */   public CharSequence getJobConfPath()
/* 180:    */   {
/* 181:151 */     return this.jobConfPath;
/* 182:    */   }
/* 183:    */   
/* 184:    */   public void setJobConfPath(CharSequence value)
/* 185:    */   {
/* 186:159 */     this.jobConfPath = value;
/* 187:    */   }
/* 188:    */   
/* 189:    */   public Map<CharSequence, CharSequence> getAcls()
/* 190:    */   {
/* 191:166 */     return this.acls;
/* 192:    */   }
/* 193:    */   
/* 194:    */   public void setAcls(Map<CharSequence, CharSequence> value)
/* 195:    */   {
/* 196:174 */     this.acls = value;
/* 197:    */   }
/* 198:    */   
/* 199:    */   public CharSequence getJobQueueName()
/* 200:    */   {
/* 201:181 */     return this.jobQueueName;
/* 202:    */   }
/* 203:    */   
/* 204:    */   public void setJobQueueName(CharSequence value)
/* 205:    */   {
/* 206:189 */     this.jobQueueName = value;
/* 207:    */   }
/* 208:    */   
/* 209:    */   public CharSequence getWorkflowId()
/* 210:    */   {
/* 211:196 */     return this.workflowId;
/* 212:    */   }
/* 213:    */   
/* 214:    */   public void setWorkflowId(CharSequence value)
/* 215:    */   {
/* 216:204 */     this.workflowId = value;
/* 217:    */   }
/* 218:    */   
/* 219:    */   public CharSequence getWorkflowName()
/* 220:    */   {
/* 221:211 */     return this.workflowName;
/* 222:    */   }
/* 223:    */   
/* 224:    */   public void setWorkflowName(CharSequence value)
/* 225:    */   {
/* 226:219 */     this.workflowName = value;
/* 227:    */   }
/* 228:    */   
/* 229:    */   public CharSequence getWorkflowNodeName()
/* 230:    */   {
/* 231:226 */     return this.workflowNodeName;
/* 232:    */   }
/* 233:    */   
/* 234:    */   public void setWorkflowNodeName(CharSequence value)
/* 235:    */   {
/* 236:234 */     this.workflowNodeName = value;
/* 237:    */   }
/* 238:    */   
/* 239:    */   public CharSequence getWorkflowAdjacencies()
/* 240:    */   {
/* 241:241 */     return this.workflowAdjacencies;
/* 242:    */   }
/* 243:    */   
/* 244:    */   public void setWorkflowAdjacencies(CharSequence value)
/* 245:    */   {
/* 246:249 */     this.workflowAdjacencies = value;
/* 247:    */   }
/* 248:    */   
/* 249:    */   public CharSequence getWorkflowTags()
/* 250:    */   {
/* 251:256 */     return this.workflowTags;
/* 252:    */   }
/* 253:    */   
/* 254:    */   public void setWorkflowTags(CharSequence value)
/* 255:    */   {
/* 256:264 */     this.workflowTags = value;
/* 257:    */   }
/* 258:    */   
/* 259:    */   public static Builder newBuilder()
/* 260:    */   {
/* 261:269 */     return new Builder(null);
/* 262:    */   }
/* 263:    */   
/* 264:    */   public static Builder newBuilder(Builder other)
/* 265:    */   {
/* 266:274 */     return new Builder(other, null);
/* 267:    */   }
/* 268:    */   
/* 269:    */   public static Builder newBuilder(JobSubmitted other)
/* 270:    */   {
/* 271:279 */     return new Builder(other, null);
/* 272:    */   }
/* 273:    */   
/* 274:    */   public static class Builder
/* 275:    */     extends SpecificRecordBuilderBase<JobSubmitted>
/* 276:    */     implements RecordBuilder<JobSubmitted>
/* 277:    */   {
/* 278:    */     private CharSequence jobid;
/* 279:    */     private CharSequence jobName;
/* 280:    */     private CharSequence userName;
/* 281:    */     private long submitTime;
/* 282:    */     private CharSequence jobConfPath;
/* 283:    */     private Map<CharSequence, CharSequence> acls;
/* 284:    */     private CharSequence jobQueueName;
/* 285:    */     private CharSequence workflowId;
/* 286:    */     private CharSequence workflowName;
/* 287:    */     private CharSequence workflowNodeName;
/* 288:    */     private CharSequence workflowAdjacencies;
/* 289:    */     private CharSequence workflowTags;
/* 290:    */     
/* 291:    */     private Builder()
/* 292:    */     {
/* 293:303 */       super();
/* 294:    */     }
/* 295:    */     
/* 296:    */     private Builder(Builder other)
/* 297:    */     {
/* 298:308 */       super();
/* 299:    */     }
/* 300:    */     
/* 301:    */     private Builder(JobSubmitted other)
/* 302:    */     {
/* 303:313 */       super();
/* 304:314 */       if (isValidValue(fields()[0], other.jobid))
/* 305:    */       {
/* 306:315 */         this.jobid = ((CharSequence)data().deepCopy(fields()[0].schema(), other.jobid));
/* 307:316 */         fieldSetFlags()[0] = 1;
/* 308:    */       }
/* 309:318 */       if (isValidValue(fields()[1], other.jobName))
/* 310:    */       {
/* 311:319 */         this.jobName = ((CharSequence)data().deepCopy(fields()[1].schema(), other.jobName));
/* 312:320 */         fieldSetFlags()[1] = 1;
/* 313:    */       }
/* 314:322 */       if (isValidValue(fields()[2], other.userName))
/* 315:    */       {
/* 316:323 */         this.userName = ((CharSequence)data().deepCopy(fields()[2].schema(), other.userName));
/* 317:324 */         fieldSetFlags()[2] = 1;
/* 318:    */       }
/* 319:326 */       if (isValidValue(fields()[3], Long.valueOf(other.submitTime)))
/* 320:    */       {
/* 321:327 */         this.submitTime = ((Long)data().deepCopy(fields()[3].schema(), Long.valueOf(other.submitTime))).longValue();
/* 322:328 */         fieldSetFlags()[3] = 1;
/* 323:    */       }
/* 324:330 */       if (isValidValue(fields()[4], other.jobConfPath))
/* 325:    */       {
/* 326:331 */         this.jobConfPath = ((CharSequence)data().deepCopy(fields()[4].schema(), other.jobConfPath));
/* 327:332 */         fieldSetFlags()[4] = 1;
/* 328:    */       }
/* 329:334 */       if (isValidValue(fields()[5], other.acls))
/* 330:    */       {
/* 331:335 */         this.acls = ((Map)data().deepCopy(fields()[5].schema(), other.acls));
/* 332:336 */         fieldSetFlags()[5] = 1;
/* 333:    */       }
/* 334:338 */       if (isValidValue(fields()[6], other.jobQueueName))
/* 335:    */       {
/* 336:339 */         this.jobQueueName = ((CharSequence)data().deepCopy(fields()[6].schema(), other.jobQueueName));
/* 337:340 */         fieldSetFlags()[6] = 1;
/* 338:    */       }
/* 339:342 */       if (isValidValue(fields()[7], other.workflowId))
/* 340:    */       {
/* 341:343 */         this.workflowId = ((CharSequence)data().deepCopy(fields()[7].schema(), other.workflowId));
/* 342:344 */         fieldSetFlags()[7] = 1;
/* 343:    */       }
/* 344:346 */       if (isValidValue(fields()[8], other.workflowName))
/* 345:    */       {
/* 346:347 */         this.workflowName = ((CharSequence)data().deepCopy(fields()[8].schema(), other.workflowName));
/* 347:348 */         fieldSetFlags()[8] = 1;
/* 348:    */       }
/* 349:350 */       if (isValidValue(fields()[9], other.workflowNodeName))
/* 350:    */       {
/* 351:351 */         this.workflowNodeName = ((CharSequence)data().deepCopy(fields()[9].schema(), other.workflowNodeName));
/* 352:352 */         fieldSetFlags()[9] = 1;
/* 353:    */       }
/* 354:354 */       if (isValidValue(fields()[10], other.workflowAdjacencies))
/* 355:    */       {
/* 356:355 */         this.workflowAdjacencies = ((CharSequence)data().deepCopy(fields()[10].schema(), other.workflowAdjacencies));
/* 357:356 */         fieldSetFlags()[10] = 1;
/* 358:    */       }
/* 359:358 */       if (isValidValue(fields()[11], other.workflowTags))
/* 360:    */       {
/* 361:359 */         this.workflowTags = ((CharSequence)data().deepCopy(fields()[11].schema(), other.workflowTags));
/* 362:360 */         fieldSetFlags()[11] = 1;
/* 363:    */       }
/* 364:    */     }
/* 365:    */     
/* 366:    */     public CharSequence getJobid()
/* 367:    */     {
/* 368:366 */       return this.jobid;
/* 369:    */     }
/* 370:    */     
/* 371:    */     public Builder setJobid(CharSequence value)
/* 372:    */     {
/* 373:371 */       validate(fields()[0], value);
/* 374:372 */       this.jobid = value;
/* 375:373 */       fieldSetFlags()[0] = 1;
/* 376:374 */       return this;
/* 377:    */     }
/* 378:    */     
/* 379:    */     public boolean hasJobid()
/* 380:    */     {
/* 381:379 */       return fieldSetFlags()[0];
/* 382:    */     }
/* 383:    */     
/* 384:    */     public Builder clearJobid()
/* 385:    */     {
/* 386:384 */       this.jobid = null;
/* 387:385 */       fieldSetFlags()[0] = 0;
/* 388:386 */       return this;
/* 389:    */     }
/* 390:    */     
/* 391:    */     public CharSequence getJobName()
/* 392:    */     {
/* 393:391 */       return this.jobName;
/* 394:    */     }
/* 395:    */     
/* 396:    */     public Builder setJobName(CharSequence value)
/* 397:    */     {
/* 398:396 */       validate(fields()[1], value);
/* 399:397 */       this.jobName = value;
/* 400:398 */       fieldSetFlags()[1] = 1;
/* 401:399 */       return this;
/* 402:    */     }
/* 403:    */     
/* 404:    */     public boolean hasJobName()
/* 405:    */     {
/* 406:404 */       return fieldSetFlags()[1];
/* 407:    */     }
/* 408:    */     
/* 409:    */     public Builder clearJobName()
/* 410:    */     {
/* 411:409 */       this.jobName = null;
/* 412:410 */       fieldSetFlags()[1] = 0;
/* 413:411 */       return this;
/* 414:    */     }
/* 415:    */     
/* 416:    */     public CharSequence getUserName()
/* 417:    */     {
/* 418:416 */       return this.userName;
/* 419:    */     }
/* 420:    */     
/* 421:    */     public Builder setUserName(CharSequence value)
/* 422:    */     {
/* 423:421 */       validate(fields()[2], value);
/* 424:422 */       this.userName = value;
/* 425:423 */       fieldSetFlags()[2] = 1;
/* 426:424 */       return this;
/* 427:    */     }
/* 428:    */     
/* 429:    */     public boolean hasUserName()
/* 430:    */     {
/* 431:429 */       return fieldSetFlags()[2];
/* 432:    */     }
/* 433:    */     
/* 434:    */     public Builder clearUserName()
/* 435:    */     {
/* 436:434 */       this.userName = null;
/* 437:435 */       fieldSetFlags()[2] = 0;
/* 438:436 */       return this;
/* 439:    */     }
/* 440:    */     
/* 441:    */     public Long getSubmitTime()
/* 442:    */     {
/* 443:441 */       return Long.valueOf(this.submitTime);
/* 444:    */     }
/* 445:    */     
/* 446:    */     public Builder setSubmitTime(long value)
/* 447:    */     {
/* 448:446 */       validate(fields()[3], Long.valueOf(value));
/* 449:447 */       this.submitTime = value;
/* 450:448 */       fieldSetFlags()[3] = 1;
/* 451:449 */       return this;
/* 452:    */     }
/* 453:    */     
/* 454:    */     public boolean hasSubmitTime()
/* 455:    */     {
/* 456:454 */       return fieldSetFlags()[3];
/* 457:    */     }
/* 458:    */     
/* 459:    */     public Builder clearSubmitTime()
/* 460:    */     {
/* 461:459 */       fieldSetFlags()[3] = 0;
/* 462:460 */       return this;
/* 463:    */     }
/* 464:    */     
/* 465:    */     public CharSequence getJobConfPath()
/* 466:    */     {
/* 467:465 */       return this.jobConfPath;
/* 468:    */     }
/* 469:    */     
/* 470:    */     public Builder setJobConfPath(CharSequence value)
/* 471:    */     {
/* 472:470 */       validate(fields()[4], value);
/* 473:471 */       this.jobConfPath = value;
/* 474:472 */       fieldSetFlags()[4] = 1;
/* 475:473 */       return this;
/* 476:    */     }
/* 477:    */     
/* 478:    */     public boolean hasJobConfPath()
/* 479:    */     {
/* 480:478 */       return fieldSetFlags()[4];
/* 481:    */     }
/* 482:    */     
/* 483:    */     public Builder clearJobConfPath()
/* 484:    */     {
/* 485:483 */       this.jobConfPath = null;
/* 486:484 */       fieldSetFlags()[4] = 0;
/* 487:485 */       return this;
/* 488:    */     }
/* 489:    */     
/* 490:    */     public Map<CharSequence, CharSequence> getAcls()
/* 491:    */     {
/* 492:490 */       return this.acls;
/* 493:    */     }
/* 494:    */     
/* 495:    */     public Builder setAcls(Map<CharSequence, CharSequence> value)
/* 496:    */     {
/* 497:495 */       validate(fields()[5], value);
/* 498:496 */       this.acls = value;
/* 499:497 */       fieldSetFlags()[5] = 1;
/* 500:498 */       return this;
/* 501:    */     }
/* 502:    */     
/* 503:    */     public boolean hasAcls()
/* 504:    */     {
/* 505:503 */       return fieldSetFlags()[5];
/* 506:    */     }
/* 507:    */     
/* 508:    */     public Builder clearAcls()
/* 509:    */     {
/* 510:508 */       this.acls = null;
/* 511:509 */       fieldSetFlags()[5] = 0;
/* 512:510 */       return this;
/* 513:    */     }
/* 514:    */     
/* 515:    */     public CharSequence getJobQueueName()
/* 516:    */     {
/* 517:515 */       return this.jobQueueName;
/* 518:    */     }
/* 519:    */     
/* 520:    */     public Builder setJobQueueName(CharSequence value)
/* 521:    */     {
/* 522:520 */       validate(fields()[6], value);
/* 523:521 */       this.jobQueueName = value;
/* 524:522 */       fieldSetFlags()[6] = 1;
/* 525:523 */       return this;
/* 526:    */     }
/* 527:    */     
/* 528:    */     public boolean hasJobQueueName()
/* 529:    */     {
/* 530:528 */       return fieldSetFlags()[6];
/* 531:    */     }
/* 532:    */     
/* 533:    */     public Builder clearJobQueueName()
/* 534:    */     {
/* 535:533 */       this.jobQueueName = null;
/* 536:534 */       fieldSetFlags()[6] = 0;
/* 537:535 */       return this;
/* 538:    */     }
/* 539:    */     
/* 540:    */     public CharSequence getWorkflowId()
/* 541:    */     {
/* 542:540 */       return this.workflowId;
/* 543:    */     }
/* 544:    */     
/* 545:    */     public Builder setWorkflowId(CharSequence value)
/* 546:    */     {
/* 547:545 */       validate(fields()[7], value);
/* 548:546 */       this.workflowId = value;
/* 549:547 */       fieldSetFlags()[7] = 1;
/* 550:548 */       return this;
/* 551:    */     }
/* 552:    */     
/* 553:    */     public boolean hasWorkflowId()
/* 554:    */     {
/* 555:553 */       return fieldSetFlags()[7];
/* 556:    */     }
/* 557:    */     
/* 558:    */     public Builder clearWorkflowId()
/* 559:    */     {
/* 560:558 */       this.workflowId = null;
/* 561:559 */       fieldSetFlags()[7] = 0;
/* 562:560 */       return this;
/* 563:    */     }
/* 564:    */     
/* 565:    */     public CharSequence getWorkflowName()
/* 566:    */     {
/* 567:565 */       return this.workflowName;
/* 568:    */     }
/* 569:    */     
/* 570:    */     public Builder setWorkflowName(CharSequence value)
/* 571:    */     {
/* 572:570 */       validate(fields()[8], value);
/* 573:571 */       this.workflowName = value;
/* 574:572 */       fieldSetFlags()[8] = 1;
/* 575:573 */       return this;
/* 576:    */     }
/* 577:    */     
/* 578:    */     public boolean hasWorkflowName()
/* 579:    */     {
/* 580:578 */       return fieldSetFlags()[8];
/* 581:    */     }
/* 582:    */     
/* 583:    */     public Builder clearWorkflowName()
/* 584:    */     {
/* 585:583 */       this.workflowName = null;
/* 586:584 */       fieldSetFlags()[8] = 0;
/* 587:585 */       return this;
/* 588:    */     }
/* 589:    */     
/* 590:    */     public CharSequence getWorkflowNodeName()
/* 591:    */     {
/* 592:590 */       return this.workflowNodeName;
/* 593:    */     }
/* 594:    */     
/* 595:    */     public Builder setWorkflowNodeName(CharSequence value)
/* 596:    */     {
/* 597:595 */       validate(fields()[9], value);
/* 598:596 */       this.workflowNodeName = value;
/* 599:597 */       fieldSetFlags()[9] = 1;
/* 600:598 */       return this;
/* 601:    */     }
/* 602:    */     
/* 603:    */     public boolean hasWorkflowNodeName()
/* 604:    */     {
/* 605:603 */       return fieldSetFlags()[9];
/* 606:    */     }
/* 607:    */     
/* 608:    */     public Builder clearWorkflowNodeName()
/* 609:    */     {
/* 610:608 */       this.workflowNodeName = null;
/* 611:609 */       fieldSetFlags()[9] = 0;
/* 612:610 */       return this;
/* 613:    */     }
/* 614:    */     
/* 615:    */     public CharSequence getWorkflowAdjacencies()
/* 616:    */     {
/* 617:615 */       return this.workflowAdjacencies;
/* 618:    */     }
/* 619:    */     
/* 620:    */     public Builder setWorkflowAdjacencies(CharSequence value)
/* 621:    */     {
/* 622:620 */       validate(fields()[10], value);
/* 623:621 */       this.workflowAdjacencies = value;
/* 624:622 */       fieldSetFlags()[10] = 1;
/* 625:623 */       return this;
/* 626:    */     }
/* 627:    */     
/* 628:    */     public boolean hasWorkflowAdjacencies()
/* 629:    */     {
/* 630:628 */       return fieldSetFlags()[10];
/* 631:    */     }
/* 632:    */     
/* 633:    */     public Builder clearWorkflowAdjacencies()
/* 634:    */     {
/* 635:633 */       this.workflowAdjacencies = null;
/* 636:634 */       fieldSetFlags()[10] = 0;
/* 637:635 */       return this;
/* 638:    */     }
/* 639:    */     
/* 640:    */     public CharSequence getWorkflowTags()
/* 641:    */     {
/* 642:640 */       return this.workflowTags;
/* 643:    */     }
/* 644:    */     
/* 645:    */     public Builder setWorkflowTags(CharSequence value)
/* 646:    */     {
/* 647:645 */       validate(fields()[11], value);
/* 648:646 */       this.workflowTags = value;
/* 649:647 */       fieldSetFlags()[11] = 1;
/* 650:648 */       return this;
/* 651:    */     }
/* 652:    */     
/* 653:    */     public boolean hasWorkflowTags()
/* 654:    */     {
/* 655:653 */       return fieldSetFlags()[11];
/* 656:    */     }
/* 657:    */     
/* 658:    */     public Builder clearWorkflowTags()
/* 659:    */     {
/* 660:658 */       this.workflowTags = null;
/* 661:659 */       fieldSetFlags()[11] = 0;
/* 662:660 */       return this;
/* 663:    */     }
/* 664:    */     
/* 665:    */     public JobSubmitted build()
/* 666:    */     {
/* 667:    */       try
/* 668:    */       {
/* 669:666 */         JobSubmitted record = new JobSubmitted();
/* 670:667 */         record.jobid = (fieldSetFlags()[0] != 0 ? this.jobid : (CharSequence)defaultValue(fields()[0]));
/* 671:668 */         record.jobName = (fieldSetFlags()[1] != 0 ? this.jobName : (CharSequence)defaultValue(fields()[1]));
/* 672:669 */         record.userName = (fieldSetFlags()[2] != 0 ? this.userName : (CharSequence)defaultValue(fields()[2]));
/* 673:670 */         record.submitTime = (fieldSetFlags()[3] != 0 ? this.submitTime : ((Long)defaultValue(fields()[3])).longValue());
/* 674:671 */         record.jobConfPath = (fieldSetFlags()[4] != 0 ? this.jobConfPath : (CharSequence)defaultValue(fields()[4]));
/* 675:672 */         record.acls = (fieldSetFlags()[5] != 0 ? this.acls : (Map)defaultValue(fields()[5]));
/* 676:673 */         record.jobQueueName = (fieldSetFlags()[6] != 0 ? this.jobQueueName : (CharSequence)defaultValue(fields()[6]));
/* 677:674 */         record.workflowId = (fieldSetFlags()[7] != 0 ? this.workflowId : (CharSequence)defaultValue(fields()[7]));
/* 678:675 */         record.workflowName = (fieldSetFlags()[8] != 0 ? this.workflowName : (CharSequence)defaultValue(fields()[8]));
/* 679:676 */         record.workflowNodeName = (fieldSetFlags()[9] != 0 ? this.workflowNodeName : (CharSequence)defaultValue(fields()[9]));
/* 680:677 */         record.workflowAdjacencies = (fieldSetFlags()[10] != 0 ? this.workflowAdjacencies : (CharSequence)defaultValue(fields()[10]));
/* 681:678 */         record.workflowTags = (fieldSetFlags()[11] != 0 ? this.workflowTags : (CharSequence)defaultValue(fields()[11]));
/* 682:679 */         return record;
/* 683:    */       }
/* 684:    */       catch (Exception e)
/* 685:    */       {
/* 686:681 */         throw new AvroRuntimeException(e);
/* 687:    */       }
/* 688:    */     }
/* 689:    */   }
/* 690:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.jobhistory.JobSubmitted
 * JD-Core Version:    0.7.0.1
 */