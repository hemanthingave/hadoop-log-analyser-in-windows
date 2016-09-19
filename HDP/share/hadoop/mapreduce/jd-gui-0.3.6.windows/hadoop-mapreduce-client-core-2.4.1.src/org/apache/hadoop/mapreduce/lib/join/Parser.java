/*   1:    */ package org.apache.hadoop.mapreduce.lib.join;
/*   2:    */ 
/*   3:    */ import java.io.CharArrayReader;
/*   4:    */ import java.io.IOException;
/*   5:    */ import java.io.StreamTokenizer;
/*   6:    */ import java.lang.reflect.Constructor;
/*   7:    */ import java.lang.reflect.InvocationTargetException;
/*   8:    */ import java.util.ArrayList;
/*   9:    */ import java.util.HashMap;
/*  10:    */ import java.util.Iterator;
/*  11:    */ import java.util.LinkedList;
/*  12:    */ import java.util.List;
/*  13:    */ import java.util.ListIterator;
/*  14:    */ import java.util.Map;
/*  15:    */ import java.util.Stack;
/*  16:    */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*  17:    */ import org.apache.hadoop.classification.InterfaceStability.Evolving;
/*  18:    */ import org.apache.hadoop.conf.Configuration;
/*  19:    */ import org.apache.hadoop.io.WritableComparator;
/*  20:    */ import org.apache.hadoop.mapreduce.Counter;
/*  21:    */ import org.apache.hadoop.mapreduce.InputFormat;
/*  22:    */ import org.apache.hadoop.mapreduce.InputSplit;
/*  23:    */ import org.apache.hadoop.mapreduce.Job;
/*  24:    */ import org.apache.hadoop.mapreduce.JobContext;
/*  25:    */ import org.apache.hadoop.mapreduce.RecordReader;
/*  26:    */ import org.apache.hadoop.mapreduce.StatusReporter;
/*  27:    */ import org.apache.hadoop.mapreduce.TaskAttemptContext;
/*  28:    */ import org.apache.hadoop.mapreduce.TaskAttemptID;
/*  29:    */ import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
/*  30:    */ import org.apache.hadoop.mapreduce.task.JobContextImpl;
/*  31:    */ import org.apache.hadoop.mapreduce.task.TaskAttemptContextImpl;
/*  32:    */ import org.apache.hadoop.util.ReflectionUtils;
/*  33:    */ 
/*  34:    */ @InterfaceAudience.Public
/*  35:    */ @InterfaceStability.Evolving
/*  36:    */ public class Parser
/*  37:    */ {
/*  38:    */   @InterfaceAudience.Public
/*  39:    */   @InterfaceStability.Evolving
/*  40:    */   public static enum TType
/*  41:    */   {
/*  42: 78 */     CIF,  IDENT,  COMMA,  LPAREN,  RPAREN,  QUOT,  NUM;
/*  43:    */     
/*  44:    */     private TType() {}
/*  45:    */   }
/*  46:    */   
/*  47:    */   @InterfaceAudience.Public
/*  48:    */   @InterfaceStability.Evolving
/*  49:    */   public static class Token
/*  50:    */   {
/*  51:    */     private Parser.TType type;
/*  52:    */     
/*  53:    */     Token(Parser.TType type)
/*  54:    */     {
/*  55: 91 */       this.type = type;
/*  56:    */     }
/*  57:    */     
/*  58:    */     public Parser.TType getType()
/*  59:    */     {
/*  60: 94 */       return this.type;
/*  61:    */     }
/*  62:    */     
/*  63:    */     public Parser.Node getNode()
/*  64:    */       throws IOException
/*  65:    */     {
/*  66: 97 */       throw new IOException("Expected nodetype");
/*  67:    */     }
/*  68:    */     
/*  69:    */     public double getNum()
/*  70:    */       throws IOException
/*  71:    */     {
/*  72:101 */       throw new IOException("Expected numtype");
/*  73:    */     }
/*  74:    */     
/*  75:    */     public String getStr()
/*  76:    */       throws IOException
/*  77:    */     {
/*  78:105 */       throw new IOException("Expected strtype");
/*  79:    */     }
/*  80:    */   }
/*  81:    */   
/*  82:    */   @InterfaceAudience.Public
/*  83:    */   @InterfaceStability.Evolving
/*  84:    */   public static class NumToken
/*  85:    */     extends Parser.Token
/*  86:    */   {
/*  87:    */     private double num;
/*  88:    */     
/*  89:    */     public NumToken(double num)
/*  90:    */     {
/*  91:114 */       super();
/*  92:115 */       this.num = num;
/*  93:    */     }
/*  94:    */     
/*  95:    */     public double getNum()
/*  96:    */     {
/*  97:117 */       return this.num;
/*  98:    */     }
/*  99:    */   }
/* 100:    */   
/* 101:    */   @InterfaceAudience.Public
/* 102:    */   @InterfaceStability.Evolving
/* 103:    */   public static class NodeToken
/* 104:    */     extends Parser.Token
/* 105:    */   {
/* 106:    */     private Parser.Node node;
/* 107:    */     
/* 108:    */     NodeToken(Parser.Node node)
/* 109:    */     {
/* 110:125 */       super();
/* 111:126 */       this.node = node;
/* 112:    */     }
/* 113:    */     
/* 114:    */     public Parser.Node getNode()
/* 115:    */     {
/* 116:129 */       return this.node;
/* 117:    */     }
/* 118:    */   }
/* 119:    */   
/* 120:    */   @InterfaceAudience.Public
/* 121:    */   @InterfaceStability.Evolving
/* 122:    */   public static class StrToken
/* 123:    */     extends Parser.Token
/* 124:    */   {
/* 125:    */     private String str;
/* 126:    */     
/* 127:    */     public StrToken(Parser.TType type, String str)
/* 128:    */     {
/* 129:138 */       super();
/* 130:139 */       this.str = str;
/* 131:    */     }
/* 132:    */     
/* 133:    */     public String getStr()
/* 134:    */     {
/* 135:142 */       return this.str;
/* 136:    */     }
/* 137:    */   }
/* 138:    */   
/* 139:    */   private static class Lexer
/* 140:    */   {
/* 141:    */     private StreamTokenizer tok;
/* 142:    */     
/* 143:    */     Lexer(String s)
/* 144:    */     {
/* 145:156 */       this.tok = new StreamTokenizer(new CharArrayReader(s.toCharArray()));
/* 146:157 */       this.tok.quoteChar(34);
/* 147:158 */       this.tok.parseNumbers();
/* 148:159 */       this.tok.ordinaryChar(44);
/* 149:160 */       this.tok.ordinaryChar(40);
/* 150:161 */       this.tok.ordinaryChar(41);
/* 151:162 */       this.tok.wordChars(36, 36);
/* 152:163 */       this.tok.wordChars(95, 95);
/* 153:    */     }
/* 154:    */     
/* 155:    */     Parser.Token next()
/* 156:    */       throws IOException
/* 157:    */     {
/* 158:167 */       int type = this.tok.nextToken();
/* 159:168 */       switch (type)
/* 160:    */       {
/* 161:    */       case -1: 
/* 162:    */       case 10: 
/* 163:171 */         return null;
/* 164:    */       case -2: 
/* 165:173 */         return new Parser.NumToken(this.tok.nval);
/* 166:    */       case -3: 
/* 167:175 */         return new Parser.StrToken(Parser.TType.IDENT, this.tok.sval);
/* 168:    */       case 34: 
/* 169:177 */         return new Parser.StrToken(Parser.TType.QUOT, this.tok.sval);
/* 170:    */       }
/* 171:179 */       switch (type)
/* 172:    */       {
/* 173:    */       case 44: 
/* 174:181 */         return new Parser.Token(Parser.TType.COMMA);
/* 175:    */       case 40: 
/* 176:183 */         return new Parser.Token(Parser.TType.LPAREN);
/* 177:    */       case 41: 
/* 178:185 */         return new Parser.Token(Parser.TType.RPAREN);
/* 179:    */       }
/* 180:187 */       throw new IOException("Unexpected: " + type);
/* 181:    */     }
/* 182:    */   }
/* 183:    */   
/* 184:    */   @InterfaceAudience.Public
/* 185:    */   @InterfaceStability.Evolving
/* 186:    */   public static abstract class Node
/* 187:    */     extends ComposableInputFormat
/* 188:    */   {
/* 189:    */     static Node forIdent(String ident)
/* 190:    */       throws IOException
/* 191:    */     {
/* 192:    */       try
/* 193:    */       {
/* 194:207 */         if (!nodeCstrMap.containsKey(ident)) {
/* 195:208 */           throw new IOException("No nodetype for " + ident);
/* 196:    */         }
/* 197:210 */         return (Node)((Constructor)nodeCstrMap.get(ident)).newInstance(new Object[] { ident });
/* 198:    */       }
/* 199:    */       catch (IllegalAccessException e)
/* 200:    */       {
/* 201:212 */         throw new IOException(e);
/* 202:    */       }
/* 203:    */       catch (InstantiationException e)
/* 204:    */       {
/* 205:214 */         throw new IOException(e);
/* 206:    */       }
/* 207:    */       catch (InvocationTargetException e)
/* 208:    */       {
/* 209:216 */         throw new IOException(e);
/* 210:    */       }
/* 211:    */     }
/* 212:    */     
/* 213:220 */     private static final Class<?>[] ncstrSig = { String.class };
/* 214:222 */     private static final Map<String, Constructor<? extends Node>> nodeCstrMap = new HashMap();
/* 215:225 */     protected static final Map<String, Constructor<? extends ComposableRecordReader>> rrCstrMap = new HashMap();
/* 216:    */     
/* 217:    */     protected static void addIdentifier(String ident, Class<?>[] mcstrSig, Class<? extends Node> nodetype, Class<? extends ComposableRecordReader> cl)
/* 218:    */       throws NoSuchMethodException
/* 219:    */     {
/* 220:239 */       Constructor<? extends Node> ncstr = nodetype.getDeclaredConstructor(ncstrSig);
/* 221:    */       
/* 222:241 */       ncstr.setAccessible(true);
/* 223:242 */       nodeCstrMap.put(ident, ncstr);
/* 224:243 */       Constructor<? extends ComposableRecordReader> mcstr = cl.getDeclaredConstructor(mcstrSig);
/* 225:    */       
/* 226:245 */       mcstr.setAccessible(true);
/* 227:246 */       rrCstrMap.put(ident, mcstr);
/* 228:    */     }
/* 229:    */     
/* 230:250 */     protected int id = -1;
/* 231:    */     protected String ident;
/* 232:    */     protected Class<? extends WritableComparator> cmpcl;
/* 233:    */     
/* 234:    */     protected Node(String ident)
/* 235:    */     {
/* 236:255 */       this.ident = ident;
/* 237:    */     }
/* 238:    */     
/* 239:    */     protected void setID(int id)
/* 240:    */     {
/* 241:259 */       this.id = id;
/* 242:    */     }
/* 243:    */     
/* 244:    */     protected void setKeyComparator(Class<? extends WritableComparator> cmpcl)
/* 245:    */     {
/* 246:264 */       this.cmpcl = cmpcl;
/* 247:    */     }
/* 248:    */     
/* 249:    */     abstract void parse(List<Parser.Token> paramList, Configuration paramConfiguration)
/* 250:    */       throws IOException;
/* 251:    */   }
/* 252:    */   
/* 253:    */   static class WNode
/* 254:    */     extends Parser.Node
/* 255:    */   {
/* 256:274 */     private static final Class<?>[] cstrSig = { Integer.TYPE, RecordReader.class, Class.class };
/* 257:    */     private String indir;
/* 258:    */     private InputFormat<?, ?> inf;
/* 259:    */     
/* 260:    */     static void addIdentifier(String ident, Class<? extends ComposableRecordReader> cl)
/* 261:    */       throws NoSuchMethodException
/* 262:    */     {
/* 263:281 */       Parser.Node.addIdentifier(ident, cstrSig, WNode.class, cl);
/* 264:    */     }
/* 265:    */     
/* 266:    */     public WNode(String ident)
/* 267:    */     {
/* 268:288 */       super();
/* 269:    */     }
/* 270:    */     
/* 271:    */     public void parse(List<Parser.Token> ll, Configuration conf)
/* 272:    */       throws IOException
/* 273:    */     {
/* 274:297 */       StringBuilder sb = new StringBuilder();
/* 275:298 */       Iterator<Parser.Token> i = ll.iterator();
/* 276:299 */       while (i.hasNext())
/* 277:    */       {
/* 278:300 */         Parser.Token t = (Parser.Token)i.next();
/* 279:301 */         if (Parser.TType.COMMA.equals(t.getType())) {
/* 280:    */           try
/* 281:    */           {
/* 282:303 */             this.inf = ((InputFormat)ReflectionUtils.newInstance(conf.getClassByName(sb.toString()), conf));
/* 283:    */           }
/* 284:    */           catch (ClassNotFoundException e)
/* 285:    */           {
/* 286:306 */             throw new IOException(e);
/* 287:    */           }
/* 288:    */           catch (IllegalArgumentException e)
/* 289:    */           {
/* 290:308 */             throw new IOException(e);
/* 291:    */           }
/* 292:    */         }
/* 293:312 */         sb.append(t.getStr());
/* 294:    */       }
/* 295:314 */       if (!i.hasNext()) {
/* 296:315 */         throw new IOException("Parse error");
/* 297:    */       }
/* 298:317 */       Parser.Token t = (Parser.Token)i.next();
/* 299:318 */       if (!Parser.TType.QUOT.equals(t.getType())) {
/* 300:319 */         throw new IOException("Expected quoted string");
/* 301:    */       }
/* 302:321 */       this.indir = t.getStr();
/* 303:    */     }
/* 304:    */     
/* 305:    */     private Configuration getConf(Configuration jconf)
/* 306:    */       throws IOException
/* 307:    */     {
/* 308:326 */       Job job = new Job(jconf);
/* 309:327 */       FileInputFormat.setInputPaths(job, this.indir);
/* 310:328 */       return job.getConfiguration();
/* 311:    */     }
/* 312:    */     
/* 313:    */     public List<InputSplit> getSplits(JobContext context)
/* 314:    */       throws IOException, InterruptedException
/* 315:    */     {
/* 316:333 */       return this.inf.getSplits(new JobContextImpl(getConf(context.getConfiguration()), context.getJobID()));
/* 317:    */     }
/* 318:    */     
/* 319:    */     public ComposableRecordReader<?, ?> createRecordReader(InputSplit split, TaskAttemptContext taskContext)
/* 320:    */       throws IOException, InterruptedException
/* 321:    */     {
/* 322:    */       try
/* 323:    */       {
/* 324:342 */         if (!rrCstrMap.containsKey(this.ident)) {
/* 325:343 */           throw new IOException("No RecordReader for " + this.ident);
/* 326:    */         }
/* 327:345 */         Configuration conf = getConf(taskContext.getConfiguration());
/* 328:346 */         TaskAttemptContext context = new TaskAttemptContextImpl(conf, TaskAttemptID.forName(conf.get("mapreduce.task.attempt.id")), new Parser.WrappedStatusReporter(taskContext));
/* 329:    */         
/* 330:    */ 
/* 331:    */ 
/* 332:350 */         return (ComposableRecordReader)((Constructor)rrCstrMap.get(this.ident)).newInstance(new Object[] { Integer.valueOf(this.id), this.inf.createRecordReader(split, context), this.cmpcl });
/* 333:    */       }
/* 334:    */       catch (IllegalAccessException e)
/* 335:    */       {
/* 336:353 */         throw new IOException(e);
/* 337:    */       }
/* 338:    */       catch (InstantiationException e)
/* 339:    */       {
/* 340:355 */         throw new IOException(e);
/* 341:    */       }
/* 342:    */       catch (InvocationTargetException e)
/* 343:    */       {
/* 344:357 */         throw new IOException(e);
/* 345:    */       }
/* 346:    */     }
/* 347:    */     
/* 348:    */     public String toString()
/* 349:    */     {
/* 350:362 */       return this.ident + "(" + this.inf.getClass().getName() + ",\"" + this.indir + "\")";
/* 351:    */     }
/* 352:    */   }
/* 353:    */   
/* 354:    */   private static class WrappedStatusReporter
/* 355:    */     extends StatusReporter
/* 356:    */   {
/* 357:    */     TaskAttemptContext context;
/* 358:    */     
/* 359:    */     public WrappedStatusReporter(TaskAttemptContext context)
/* 360:    */     {
/* 361:371 */       this.context = context;
/* 362:    */     }
/* 363:    */     
/* 364:    */     public Counter getCounter(Enum<?> name)
/* 365:    */     {
/* 366:375 */       return this.context.getCounter(name);
/* 367:    */     }
/* 368:    */     
/* 369:    */     public Counter getCounter(String group, String name)
/* 370:    */     {
/* 371:380 */       return this.context.getCounter(group, name);
/* 372:    */     }
/* 373:    */     
/* 374:    */     public void progress()
/* 375:    */     {
/* 376:385 */       this.context.progress();
/* 377:    */     }
/* 378:    */     
/* 379:    */     public float getProgress()
/* 380:    */     {
/* 381:390 */       return this.context.getProgress();
/* 382:    */     }
/* 383:    */     
/* 384:    */     public void setStatus(String status)
/* 385:    */     {
/* 386:395 */       this.context.setStatus(status);
/* 387:    */     }
/* 388:    */   }
/* 389:    */   
/* 390:    */   static class CNode
/* 391:    */     extends Parser.Node
/* 392:    */   {
/* 393:404 */     private static final Class<?>[] cstrSig = { Integer.TYPE, Configuration.class, Integer.TYPE, Class.class };
/* 394:    */     
/* 395:    */     static void addIdentifier(String ident, Class<? extends ComposableRecordReader> cl)
/* 396:    */       throws NoSuchMethodException
/* 397:    */     {
/* 398:411 */       Parser.Node.addIdentifier(ident, cstrSig, CNode.class, cl);
/* 399:    */     }
/* 400:    */     
/* 401:415 */     private ArrayList<Parser.Node> kids = new ArrayList();
/* 402:    */     
/* 403:    */     public CNode(String ident)
/* 404:    */     {
/* 405:418 */       super();
/* 406:    */     }
/* 407:    */     
/* 408:    */     public void setKeyComparator(Class<? extends WritableComparator> cmpcl)
/* 409:    */     {
/* 410:423 */       super.setKeyComparator(cmpcl);
/* 411:424 */       for (Parser.Node n : this.kids) {
/* 412:425 */         n.setKeyComparator(cmpcl);
/* 413:    */       }
/* 414:    */     }
/* 415:    */     
/* 416:    */     public List<InputSplit> getSplits(JobContext job)
/* 417:    */       throws IOException, InterruptedException
/* 418:    */     {
/* 419:436 */       List<List<InputSplit>> splits = new ArrayList(this.kids.size());
/* 420:438 */       for (int i = 0; i < this.kids.size(); i++)
/* 421:    */       {
/* 422:439 */         List<InputSplit> tmp = ((Parser.Node)this.kids.get(i)).getSplits(job);
/* 423:440 */         if (null == tmp) {
/* 424:441 */           throw new IOException("Error gathering splits from child RReader");
/* 425:    */         }
/* 426:443 */         if ((i > 0) && (((List)splits.get(i - 1)).size() != tmp.size())) {
/* 427:444 */           throw new IOException("Inconsistent split cardinality from child " + i + " (" + ((List)splits.get(i - 1)).size() + "/" + tmp.size() + ")");
/* 428:    */         }
/* 429:447 */         splits.add(i, tmp);
/* 430:    */       }
/* 431:449 */       int size = ((List)splits.get(0)).size();
/* 432:450 */       List<InputSplit> ret = new ArrayList();
/* 433:451 */       for (int i = 0; i < size; i++)
/* 434:    */       {
/* 435:452 */         CompositeInputSplit split = new CompositeInputSplit(splits.size());
/* 436:453 */         for (int j = 0; j < splits.size(); j++) {
/* 437:454 */           split.add((InputSplit)((List)splits.get(j)).get(i));
/* 438:    */         }
/* 439:456 */         ret.add(split);
/* 440:    */       }
/* 441:458 */       return ret;
/* 442:    */     }
/* 443:    */     
/* 444:    */     public ComposableRecordReader createRecordReader(InputSplit split, TaskAttemptContext taskContext)
/* 445:    */       throws IOException, InterruptedException
/* 446:    */     {
/* 447:465 */       if (!(split instanceof CompositeInputSplit)) {
/* 448:466 */         throw new IOException("Invalid split type:" + split.getClass().getName());
/* 449:    */       }
/* 450:469 */       CompositeInputSplit spl = (CompositeInputSplit)split;
/* 451:470 */       int capacity = this.kids.size();
/* 452:471 */       CompositeRecordReader ret = null;
/* 453:    */       try
/* 454:    */       {
/* 455:473 */         if (!rrCstrMap.containsKey(this.ident)) {
/* 456:474 */           throw new IOException("No RecordReader for " + this.ident);
/* 457:    */         }
/* 458:476 */         ret = (CompositeRecordReader)((Constructor)rrCstrMap.get(this.ident)).newInstance(new Object[] { Integer.valueOf(this.id), taskContext.getConfiguration(), Integer.valueOf(capacity), this.cmpcl });
/* 459:    */       }
/* 460:    */       catch (IllegalAccessException e)
/* 461:    */       {
/* 462:479 */         throw new IOException(e);
/* 463:    */       }
/* 464:    */       catch (InstantiationException e)
/* 465:    */       {
/* 466:481 */         throw new IOException(e);
/* 467:    */       }
/* 468:    */       catch (InvocationTargetException e)
/* 469:    */       {
/* 470:483 */         throw new IOException(e);
/* 471:    */       }
/* 472:485 */       for (int i = 0; i < capacity; i++) {
/* 473:486 */         ret.add(((Parser.Node)this.kids.get(i)).createRecordReader(spl.get(i), taskContext));
/* 474:    */       }
/* 475:488 */       return ret;
/* 476:    */     }
/* 477:    */     
/* 478:    */     public void parse(List<Parser.Token> args, Configuration conf)
/* 479:    */       throws IOException
/* 480:    */     {
/* 481:496 */       ListIterator<Parser.Token> i = args.listIterator();
/* 482:497 */       while (i.hasNext())
/* 483:    */       {
/* 484:498 */         Parser.Token t = (Parser.Token)i.next();
/* 485:499 */         t.getNode().setID(i.previousIndex() >> 1);
/* 486:500 */         this.kids.add(t.getNode());
/* 487:501 */         if ((i.hasNext()) && (!Parser.TType.COMMA.equals(((Parser.Token)i.next()).getType()))) {
/* 488:502 */           throw new IOException("Expected ','");
/* 489:    */         }
/* 490:    */       }
/* 491:    */     }
/* 492:    */     
/* 493:    */     public String toString()
/* 494:    */     {
/* 495:508 */       StringBuilder sb = new StringBuilder();
/* 496:509 */       sb.append(this.ident + "(");
/* 497:510 */       for (Parser.Node n : this.kids) {
/* 498:511 */         sb.append(n.toString() + ",");
/* 499:    */       }
/* 500:513 */       sb.setCharAt(sb.length() - 1, ')');
/* 501:514 */       return sb.toString();
/* 502:    */     }
/* 503:    */   }
/* 504:    */   
/* 505:    */   private static Token reduce(Stack<Token> st, Configuration conf)
/* 506:    */     throws IOException
/* 507:    */   {
/* 508:520 */     LinkedList<Token> args = new LinkedList();
/* 509:521 */     while ((!st.isEmpty()) && (!TType.LPAREN.equals(((Token)st.peek()).getType()))) {
/* 510:522 */       args.addFirst(st.pop());
/* 511:    */     }
/* 512:524 */     if (st.isEmpty()) {
/* 513:525 */       throw new IOException("Unmatched ')'");
/* 514:    */     }
/* 515:527 */     st.pop();
/* 516:528 */     if ((st.isEmpty()) || (!TType.IDENT.equals(((Token)st.peek()).getType()))) {
/* 517:529 */       throw new IOException("Identifier expected");
/* 518:    */     }
/* 519:531 */     Node n = Node.forIdent(((Token)st.pop()).getStr());
/* 520:532 */     n.parse(args, conf);
/* 521:533 */     return new NodeToken(n);
/* 522:    */   }
/* 523:    */   
/* 524:    */   static Node parse(String expr, Configuration conf)
/* 525:    */     throws IOException
/* 526:    */   {
/* 527:541 */     if (null == expr) {
/* 528:542 */       throw new IOException("Expression is null");
/* 529:    */     }
/* 530:544 */     Class<? extends WritableComparator> cmpcl = conf.getClass("mapreduce.join.keycomparator", null, WritableComparator.class);
/* 531:    */     
/* 532:546 */     Lexer lex = new Lexer(expr);
/* 533:547 */     Stack<Token> st = new Stack();
/* 534:    */     Token tok;
/* 535:549 */     while ((tok = lex.next()) != null) {
/* 536:550 */       if (TType.RPAREN.equals(tok.getType())) {
/* 537:551 */         st.push(reduce(st, conf));
/* 538:    */       } else {
/* 539:553 */         st.push(tok);
/* 540:    */       }
/* 541:    */     }
/* 542:556 */     if ((st.size() == 1) && (TType.CIF.equals(((Token)st.peek()).getType())))
/* 543:    */     {
/* 544:557 */       Node ret = ((Token)st.pop()).getNode();
/* 545:558 */       if (cmpcl != null) {
/* 546:559 */         ret.setKeyComparator(cmpcl);
/* 547:    */       }
/* 548:561 */       return ret;
/* 549:    */     }
/* 550:563 */     throw new IOException("Missing ')'");
/* 551:    */   }
/* 552:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.lib.join.Parser
 * JD-Core Version:    0.7.0.1
 */