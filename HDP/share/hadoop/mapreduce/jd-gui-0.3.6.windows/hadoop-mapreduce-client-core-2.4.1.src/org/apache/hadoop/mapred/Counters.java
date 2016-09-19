/*   1:    */ package org.apache.hadoop.mapred;
/*   2:    */ 
/*   3:    */ import com.google.common.collect.Iterators;
/*   4:    */ import java.io.DataInput;
/*   5:    */ import java.io.DataOutput;
/*   6:    */ import java.io.IOException;
/*   7:    */ import java.text.ParseException;
/*   8:    */ import java.util.Collection;
/*   9:    */ import java.util.HashMap;
/*  10:    */ import java.util.Iterator;
/*  11:    */ import org.apache.commons.collections.IteratorUtils;
/*  12:    */ import org.apache.commons.logging.Log;
/*  13:    */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*  14:    */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*  15:    */ import org.apache.hadoop.mapreduce.Counter;
/*  16:    */ import org.apache.hadoop.mapreduce.FileSystemCounter;
/*  17:    */ import org.apache.hadoop.mapreduce.counters.AbstractCounterGroup;
/*  18:    */ import org.apache.hadoop.mapreduce.counters.AbstractCounters;
/*  19:    */ import org.apache.hadoop.mapreduce.counters.CounterGroupBase;
/*  20:    */ import org.apache.hadoop.mapreduce.counters.CounterGroupFactory;
/*  21:    */ import org.apache.hadoop.mapreduce.counters.CounterGroupFactory.FrameworkGroupFactory;
/*  22:    */ import org.apache.hadoop.mapreduce.counters.FileSystemCounterGroup;
/*  23:    */ import org.apache.hadoop.mapreduce.counters.FileSystemCounterGroup.FSCounter;
/*  24:    */ import org.apache.hadoop.mapreduce.counters.FrameworkCounterGroup;
/*  25:    */ import org.apache.hadoop.mapreduce.counters.FrameworkCounterGroup.FrameworkCounter;
/*  26:    */ import org.apache.hadoop.mapreduce.counters.GenericCounter;
/*  27:    */ import org.apache.hadoop.mapreduce.counters.Limits;
/*  28:    */ import org.apache.hadoop.mapreduce.lib.input.FileInputFormatCounter;
/*  29:    */ import org.apache.hadoop.mapreduce.lib.output.FileOutputFormatCounter;
/*  30:    */ import org.apache.hadoop.mapreduce.util.CountersStrings;
/*  31:    */ 
/*  32:    */ @InterfaceAudience.Public
/*  33:    */ @InterfaceStability.Stable
/*  34:    */ public class Counters
/*  35:    */   extends AbstractCounters<Counter, Group>
/*  36:    */ {
/*  37: 65 */   public static int MAX_COUNTER_LIMIT = ;
/*  38: 66 */   public static int MAX_GROUP_LIMIT = Limits.getGroupsMax();
/*  39: 67 */   private static HashMap<String, String> depricatedCounterMap = new HashMap();
/*  40:    */   
/*  41:    */   static
/*  42:    */   {
/*  43: 71 */     initDepricatedMap();
/*  44:    */   }
/*  45:    */   
/*  46:    */   public Counters()
/*  47:    */   {
/*  48: 75 */     super(groupFactory);
/*  49:    */   }
/*  50:    */   
/*  51:    */   public Counters(org.apache.hadoop.mapreduce.Counters newCounters)
/*  52:    */   {
/*  53: 79 */     super(newCounters, groupFactory);
/*  54:    */   }
/*  55:    */   
/*  56:    */   private static void initDepricatedMap()
/*  57:    */   {
/*  58: 84 */     depricatedCounterMap.put(FileInputFormat.Counter.class.getName(), FileInputFormatCounter.class.getName());
/*  59:    */     
/*  60: 86 */     depricatedCounterMap.put(FileOutputFormat.Counter.class.getName(), FileOutputFormatCounter.class.getName());
/*  61:    */     
/*  62: 88 */     depricatedCounterMap.put(org.apache.hadoop.mapreduce.lib.input.FileInputFormat.Counter.class.getName(), FileInputFormatCounter.class.getName());
/*  63:    */     
/*  64:    */ 
/*  65: 91 */     depricatedCounterMap.put(org.apache.hadoop.mapreduce.lib.output.FileOutputFormat.Counter.class.getName(), FileOutputFormatCounter.class.getName());
/*  66:    */   }
/*  67:    */   
/*  68:    */   private static String getNewGroupKey(String oldGroup)
/*  69:    */   {
/*  70: 97 */     if (depricatedCounterMap.containsKey(oldGroup)) {
/*  71: 98 */       return (String)depricatedCounterMap.get(oldGroup);
/*  72:    */     }
/*  73:100 */     return null;
/*  74:    */   }
/*  75:    */   
/*  76:    */   static Counters downgrade(org.apache.hadoop.mapreduce.Counters newCounters)
/*  77:    */   {
/*  78:109 */     return new Counters(newCounters);
/*  79:    */   }
/*  80:    */   
/*  81:    */   public synchronized Group getGroup(String groupName)
/*  82:    */   {
/*  83:113 */     return (Group)super.getGroup(groupName);
/*  84:    */   }
/*  85:    */   
/*  86:    */   public synchronized Collection<String> getGroupNames()
/*  87:    */   {
/*  88:118 */     return IteratorUtils.toList(super.getGroupNames().iterator());
/*  89:    */   }
/*  90:    */   
/*  91:    */   public synchronized String makeCompactString()
/*  92:    */   {
/*  93:122 */     StringBuilder builder = new StringBuilder();
/*  94:123 */     boolean first = true;
/*  95:124 */     for (Iterator i$ = iterator(); i$.hasNext();)
/*  96:    */     {
/*  97:124 */       group = (Group)i$.next();
/*  98:125 */       for (Counter counter : group)
/*  99:    */       {
/* 100:126 */         if (first) {
/* 101:127 */           first = false;
/* 102:    */         } else {
/* 103:129 */           builder.append(',');
/* 104:    */         }
/* 105:131 */         builder.append(group.getDisplayName());
/* 106:132 */         builder.append('.');
/* 107:133 */         builder.append(counter.getDisplayName());
/* 108:134 */         builder.append(':');
/* 109:135 */         builder.append(counter.getCounter());
/* 110:    */       }
/* 111:    */     }
/* 112:    */     Group group;
/* 113:138 */     return builder.toString();
/* 114:    */   }
/* 115:    */   
/* 116:    */   @InterfaceAudience.Public
/* 117:    */   @InterfaceStability.Stable
/* 118:    */   public static class Counter
/* 119:    */     implements Counter
/* 120:    */   {
/* 121:    */     Counter realCounter;
/* 122:    */     
/* 123:    */     Counter(Counter counter)
/* 124:    */     {
/* 125:150 */       this.realCounter = counter;
/* 126:    */     }
/* 127:    */     
/* 128:    */     public Counter()
/* 129:    */     {
/* 130:154 */       this(new GenericCounter());
/* 131:    */     }
/* 132:    */     
/* 133:    */     public void setDisplayName(String displayName)
/* 134:    */     {
/* 135:160 */       this.realCounter.setDisplayName(displayName);
/* 136:    */     }
/* 137:    */     
/* 138:    */     public String getName()
/* 139:    */     {
/* 140:165 */       return this.realCounter.getName();
/* 141:    */     }
/* 142:    */     
/* 143:    */     public String getDisplayName()
/* 144:    */     {
/* 145:170 */       return this.realCounter.getDisplayName();
/* 146:    */     }
/* 147:    */     
/* 148:    */     public long getValue()
/* 149:    */     {
/* 150:175 */       return this.realCounter.getValue();
/* 151:    */     }
/* 152:    */     
/* 153:    */     public void setValue(long value)
/* 154:    */     {
/* 155:180 */       this.realCounter.setValue(value);
/* 156:    */     }
/* 157:    */     
/* 158:    */     public void increment(long incr)
/* 159:    */     {
/* 160:185 */       this.realCounter.increment(incr);
/* 161:    */     }
/* 162:    */     
/* 163:    */     public void write(DataOutput out)
/* 164:    */       throws IOException
/* 165:    */     {
/* 166:190 */       this.realCounter.write(out);
/* 167:    */     }
/* 168:    */     
/* 169:    */     public void readFields(DataInput in)
/* 170:    */       throws IOException
/* 171:    */     {
/* 172:195 */       this.realCounter.readFields(in);
/* 173:    */     }
/* 174:    */     
/* 175:    */     public String makeEscapedCompactString()
/* 176:    */     {
/* 177:204 */       return CountersStrings.toEscapedCompactString(this.realCounter);
/* 178:    */     }
/* 179:    */     
/* 180:    */     @Deprecated
/* 181:    */     public boolean contentEquals(Counter counter)
/* 182:    */     {
/* 183:215 */       return this.realCounter.equals(counter.getUnderlyingCounter());
/* 184:    */     }
/* 185:    */     
/* 186:    */     public long getCounter()
/* 187:    */     {
/* 188:222 */       return this.realCounter.getValue();
/* 189:    */     }
/* 190:    */     
/* 191:    */     public Counter getUnderlyingCounter()
/* 192:    */     {
/* 193:227 */       return this.realCounter;
/* 194:    */     }
/* 195:    */     
/* 196:    */     public synchronized boolean equals(Object genericRight)
/* 197:    */     {
/* 198:232 */       if ((genericRight instanceof Counter)) {
/* 199:233 */         synchronized (genericRight)
/* 200:    */         {
/* 201:234 */           Counter right = (Counter)genericRight;
/* 202:235 */           return (getName().equals(right.getName())) && (getDisplayName().equals(right.getDisplayName())) && (getValue() == right.getValue());
/* 203:    */         }
/* 204:    */       }
/* 205:240 */       return false;
/* 206:    */     }
/* 207:    */     
/* 208:    */     public int hashCode()
/* 209:    */     {
/* 210:245 */       return this.realCounter.hashCode();
/* 211:    */     }
/* 212:    */   }
/* 213:    */   
/* 214:    */   @InterfaceAudience.Public
/* 215:    */   @InterfaceStability.Stable
/* 216:    */   public static class Group
/* 217:    */     implements CounterGroupBase<Counters.Counter>
/* 218:    */   {
/* 219:    */     private CounterGroupBase<Counters.Counter> realGroup;
/* 220:    */     
/* 221:    */     protected Group()
/* 222:    */     {
/* 223:263 */       this.realGroup = null;
/* 224:    */     }
/* 225:    */     
/* 226:    */     Group(Counters.GenericGroup group)
/* 227:    */     {
/* 228:267 */       this.realGroup = group;
/* 229:    */     }
/* 230:    */     
/* 231:    */     Group(Counters.FSGroupImpl group)
/* 232:    */     {
/* 233:270 */       this.realGroup = group;
/* 234:    */     }
/* 235:    */     
/* 236:    */     Group(Counters.FrameworkGroupImpl group)
/* 237:    */     {
/* 238:275 */       this.realGroup = group;
/* 239:    */     }
/* 240:    */     
/* 241:    */     public long getCounter(String counterName)
/* 242:    */     {
/* 243:284 */       return Counters.getCounterValue(this.realGroup, counterName);
/* 244:    */     }
/* 245:    */     
/* 246:    */     public String makeEscapedCompactString()
/* 247:    */     {
/* 248:293 */       return CountersStrings.toEscapedCompactString(this.realGroup);
/* 249:    */     }
/* 250:    */     
/* 251:    */     @Deprecated
/* 252:    */     public Counters.Counter getCounter(int id, String name)
/* 253:    */     {
/* 254:305 */       return findCounter(name);
/* 255:    */     }
/* 256:    */     
/* 257:    */     public Counters.Counter getCounterForName(String name)
/* 258:    */     {
/* 259:314 */       return findCounter(name);
/* 260:    */     }
/* 261:    */     
/* 262:    */     public void write(DataOutput out)
/* 263:    */       throws IOException
/* 264:    */     {
/* 265:319 */       this.realGroup.write(out);
/* 266:    */     }
/* 267:    */     
/* 268:    */     public void readFields(DataInput in)
/* 269:    */       throws IOException
/* 270:    */     {
/* 271:324 */       this.realGroup.readFields(in);
/* 272:    */     }
/* 273:    */     
/* 274:    */     public Iterator<Counters.Counter> iterator()
/* 275:    */     {
/* 276:329 */       return this.realGroup.iterator();
/* 277:    */     }
/* 278:    */     
/* 279:    */     public String getName()
/* 280:    */     {
/* 281:334 */       return this.realGroup.getName();
/* 282:    */     }
/* 283:    */     
/* 284:    */     public String getDisplayName()
/* 285:    */     {
/* 286:339 */       return this.realGroup.getDisplayName();
/* 287:    */     }
/* 288:    */     
/* 289:    */     public void setDisplayName(String displayName)
/* 290:    */     {
/* 291:344 */       this.realGroup.setDisplayName(displayName);
/* 292:    */     }
/* 293:    */     
/* 294:    */     public void addCounter(Counters.Counter counter)
/* 295:    */     {
/* 296:349 */       this.realGroup.addCounter(counter);
/* 297:    */     }
/* 298:    */     
/* 299:    */     public Counters.Counter addCounter(String name, String displayName, long value)
/* 300:    */     {
/* 301:354 */       return (Counters.Counter)this.realGroup.addCounter(name, displayName, value);
/* 302:    */     }
/* 303:    */     
/* 304:    */     public Counters.Counter findCounter(String counterName, String displayName)
/* 305:    */     {
/* 306:359 */       return (Counters.Counter)this.realGroup.findCounter(counterName, displayName);
/* 307:    */     }
/* 308:    */     
/* 309:    */     public Counters.Counter findCounter(String counterName, boolean create)
/* 310:    */     {
/* 311:364 */       return (Counters.Counter)this.realGroup.findCounter(counterName, create);
/* 312:    */     }
/* 313:    */     
/* 314:    */     public Counters.Counter findCounter(String counterName)
/* 315:    */     {
/* 316:369 */       return (Counters.Counter)this.realGroup.findCounter(counterName);
/* 317:    */     }
/* 318:    */     
/* 319:    */     public int size()
/* 320:    */     {
/* 321:374 */       return this.realGroup.size();
/* 322:    */     }
/* 323:    */     
/* 324:    */     public void incrAllCounters(CounterGroupBase<Counters.Counter> rightGroup)
/* 325:    */     {
/* 326:379 */       this.realGroup.incrAllCounters(rightGroup);
/* 327:    */     }
/* 328:    */     
/* 329:    */     public CounterGroupBase<Counters.Counter> getUnderlyingGroup()
/* 330:    */     {
/* 331:384 */       return this.realGroup;
/* 332:    */     }
/* 333:    */     
/* 334:    */     public synchronized boolean equals(Object genericRight)
/* 335:    */     {
/* 336:389 */       if ((genericRight instanceof CounterGroupBase))
/* 337:    */       {
/* 338:391 */         CounterGroupBase<Counters.Counter> right = ((CounterGroupBase)genericRight).getUnderlyingGroup();
/* 339:    */         
/* 340:393 */         return Iterators.elementsEqual(iterator(), right.iterator());
/* 341:    */       }
/* 342:395 */       return false;
/* 343:    */     }
/* 344:    */     
/* 345:    */     public int hashCode()
/* 346:    */     {
/* 347:400 */       return this.realGroup.hashCode();
/* 348:    */     }
/* 349:    */   }
/* 350:    */   
/* 351:    */   static long getCounterValue(CounterGroupBase<Counter> group, String counterName)
/* 352:    */   {
/* 353:406 */     Counter counter = (Counter)group.findCounter(counterName, false);
/* 354:407 */     if (counter != null) {
/* 355:407 */       return counter.getValue();
/* 356:    */     }
/* 357:408 */     return 0L;
/* 358:    */   }
/* 359:    */   
/* 360:    */   private static class GenericGroup
/* 361:    */     extends AbstractCounterGroup<Counters.Counter>
/* 362:    */   {
/* 363:    */     GenericGroup(String name, String displayName, Limits limits)
/* 364:    */     {
/* 365:415 */       super(displayName, limits);
/* 366:    */     }
/* 367:    */     
/* 368:    */     protected Counters.Counter newCounter(String counterName, String displayName, long value)
/* 369:    */     {
/* 370:421 */       return new Counters.Counter(new GenericCounter(counterName, displayName, value));
/* 371:    */     }
/* 372:    */     
/* 373:    */     protected Counters.Counter newCounter()
/* 374:    */     {
/* 375:426 */       return new Counters.Counter();
/* 376:    */     }
/* 377:    */     
/* 378:    */     public CounterGroupBase<Counters.Counter> getUnderlyingGroup()
/* 379:    */     {
/* 380:431 */       return this;
/* 381:    */     }
/* 382:    */   }
/* 383:    */   
/* 384:    */   private static class FrameworkGroupImpl<T extends Enum<T>>
/* 385:    */     extends FrameworkCounterGroup<T, Counters.Counter>
/* 386:    */   {
/* 387:    */     FrameworkGroupImpl(Class<T> cls)
/* 388:    */     {
/* 389:440 */       super();
/* 390:    */     }
/* 391:    */     
/* 392:    */     protected Counters.Counter newCounter(T key)
/* 393:    */     {
/* 394:445 */       return new Counters.Counter(new FrameworkCounterGroup.FrameworkCounter(key, getName()));
/* 395:    */     }
/* 396:    */     
/* 397:    */     public CounterGroupBase<Counters.Counter> getUnderlyingGroup()
/* 398:    */     {
/* 399:450 */       return this;
/* 400:    */     }
/* 401:    */   }
/* 402:    */   
/* 403:    */   private static class FSGroupImpl
/* 404:    */     extends FileSystemCounterGroup<Counters.Counter>
/* 405:    */   {
/* 406:    */     protected Counters.Counter newCounter(String scheme, FileSystemCounter key)
/* 407:    */     {
/* 408:459 */       return new Counters.Counter(new FileSystemCounterGroup.FSCounter(scheme, key));
/* 409:    */     }
/* 410:    */     
/* 411:    */     public CounterGroupBase<Counters.Counter> getUnderlyingGroup()
/* 412:    */     {
/* 413:464 */       return this;
/* 414:    */     }
/* 415:    */   }
/* 416:    */   
/* 417:    */   public synchronized Counter findCounter(String group, String name)
/* 418:    */   {
/* 419:469 */     if (name.equals("MAP_INPUT_BYTES"))
/* 420:    */     {
/* 421:470 */       LOG.warn("Counter name MAP_INPUT_BYTES is deprecated. Use FileInputFormatCounters as group name and  BYTES_READ as counter name instead");
/* 422:    */       
/* 423:    */ 
/* 424:473 */       return (Counter)findCounter(FileInputFormatCounter.BYTES_READ);
/* 425:    */     }
/* 426:475 */     String newGroupKey = getNewGroupKey(group);
/* 427:476 */     if (newGroupKey != null) {
/* 428:477 */       group = newGroupKey;
/* 429:    */     }
/* 430:479 */     return getGroup(group).getCounterForName(name);
/* 431:    */   }
/* 432:    */   
/* 433:    */   static class GroupFactory
/* 434:    */     extends CounterGroupFactory<Counters.Counter, Counters.Group>
/* 435:    */   {
/* 436:    */     protected <T extends Enum<T>> CounterGroupFactory.FrameworkGroupFactory<Counters.Group> newFrameworkGroupFactory(final Class<T> cls)
/* 437:    */     {
/* 438:492 */       new CounterGroupFactory.FrameworkGroupFactory()
/* 439:    */       {
/* 440:    */         public Counters.Group newGroup(String name)
/* 441:    */         {
/* 442:494 */           return new Counters.Group(new Counters.FrameworkGroupImpl(cls));
/* 443:    */         }
/* 444:    */       };
/* 445:    */     }
/* 446:    */     
/* 447:    */     protected Counters.Group newGenericGroup(String name, String displayName, Limits limits)
/* 448:    */     {
/* 449:502 */       return new Counters.Group(new Counters.GenericGroup(name, displayName, limits));
/* 450:    */     }
/* 451:    */     
/* 452:    */     protected Counters.Group newFileSystemGroup()
/* 453:    */     {
/* 454:507 */       return new Counters.Group(new Counters.FSGroupImpl(null));
/* 455:    */     }
/* 456:    */   }
/* 457:    */   
/* 458:511 */   private static final GroupFactory groupFactory = new GroupFactory();
/* 459:    */   
/* 460:    */   @Deprecated
/* 461:    */   public Counter findCounter(String group, int id, String name)
/* 462:    */   {
/* 463:523 */     return findCounter(group, name);
/* 464:    */   }
/* 465:    */   
/* 466:    */   public void incrCounter(Enum<?> key, long amount)
/* 467:    */   {
/* 468:533 */     ((Counter)findCounter(key)).increment(amount);
/* 469:    */   }
/* 470:    */   
/* 471:    */   public void incrCounter(String group, String counter, long amount)
/* 472:    */   {
/* 473:544 */     findCounter(group, counter).increment(amount);
/* 474:    */   }
/* 475:    */   
/* 476:    */   public synchronized long getCounter(Enum<?> key)
/* 477:    */   {
/* 478:554 */     return ((Counter)findCounter(key)).getValue();
/* 479:    */   }
/* 480:    */   
/* 481:    */   public synchronized void incrAllCounters(Counters other)
/* 482:    */   {
/* 483:563 */     for (Group otherGroup : other)
/* 484:    */     {
/* 485:564 */       group = getGroup(otherGroup.getName());
/* 486:565 */       group.setDisplayName(otherGroup.getDisplayName());
/* 487:566 */       for (Counter otherCounter : otherGroup)
/* 488:    */       {
/* 489:567 */         Counter counter = group.getCounterForName(otherCounter.getName());
/* 490:568 */         counter.setDisplayName(otherCounter.getDisplayName());
/* 491:569 */         counter.increment(otherCounter.getValue());
/* 492:    */       }
/* 493:    */     }
/* 494:    */     Group group;
/* 495:    */   }
/* 496:    */   
/* 497:    */   /**
/* 498:    */    * @deprecated
/* 499:    */    */
/* 500:    */   public int size()
/* 501:    */   {
/* 502:579 */     return countCounters();
/* 503:    */   }
/* 504:    */   
/* 505:    */   public static Counters sum(Counters a, Counters b)
/* 506:    */   {
/* 507:589 */     Counters counters = new Counters();
/* 508:590 */     counters.incrAllCounters(a);
/* 509:591 */     counters.incrAllCounters(b);
/* 510:592 */     return counters;
/* 511:    */   }
/* 512:    */   
/* 513:    */   public void log(Log log)
/* 514:    */   {
/* 515:600 */     log.info("Counters: " + size());
/* 516:601 */     for (Group group : this)
/* 517:    */     {
/* 518:602 */       log.info("  " + group.getDisplayName());
/* 519:603 */       for (Counter counter : group) {
/* 520:604 */         log.info("    " + counter.getDisplayName() + "=" + counter.getCounter());
/* 521:    */       }
/* 522:    */     }
/* 523:    */   }
/* 524:    */   
/* 525:    */   public String makeEscapedCompactString()
/* 526:    */   {
/* 527:617 */     return CountersStrings.toEscapedCompactString(this);
/* 528:    */   }
/* 529:    */   
/* 530:    */   public static Counters fromEscapedCompactString(String compactString)
/* 531:    */     throws ParseException
/* 532:    */   {
/* 533:629 */     return (Counters)CountersStrings.parseEscapedCompactString(compactString, new Counters());
/* 534:    */   }
/* 535:    */   
/* 536:    */   public static class CountersExceededException
/* 537:    */     extends RuntimeException
/* 538:    */   {
/* 539:    */     private static final long serialVersionUID = 1L;
/* 540:    */     
/* 541:    */     public CountersExceededException(String msg)
/* 542:    */     {
/* 543:640 */       super();
/* 544:    */     }
/* 545:    */     
/* 546:    */     public CountersExceededException(CountersExceededException cause)
/* 547:    */     {
/* 548:645 */       super();
/* 549:    */     }
/* 550:    */   }
/* 551:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.Counters
 * JD-Core Version:    0.7.0.1
 */