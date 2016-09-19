/*   1:    */ package org.apache.hadoop.mapred.join;
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
/*  18:    */ import org.apache.hadoop.io.WritableComparator;
/*  19:    */ import org.apache.hadoop.mapred.FileInputFormat;
/*  20:    */ import org.apache.hadoop.mapred.InputFormat;
/*  21:    */ import org.apache.hadoop.mapred.InputSplit;
/*  22:    */ import org.apache.hadoop.mapred.JobConf;
/*  23:    */ import org.apache.hadoop.mapred.RecordReader;
/*  24:    */ import org.apache.hadoop.mapred.Reporter;
/*  25:    */ import org.apache.hadoop.util.ReflectionUtils;
/*  26:    */ 
/*  27:    */ @InterfaceAudience.Public
/*  28:    */ @InterfaceStability.Evolving
/*  29:    */ public class Parser
/*  30:    */ {
/*  31:    */   @InterfaceAudience.Public
/*  32:    */   @InterfaceStability.Evolving
/*  33:    */   public static enum TType
/*  34:    */   {
/*  35: 70 */     CIF,  IDENT,  COMMA,  LPAREN,  RPAREN,  QUOT,  NUM;
/*  36:    */     
/*  37:    */     private TType() {}
/*  38:    */   }
/*  39:    */   
/*  40:    */   @InterfaceAudience.Public
/*  41:    */   @InterfaceStability.Evolving
/*  42:    */   public static class Token
/*  43:    */   {
/*  44:    */     private Parser.TType type;
/*  45:    */     
/*  46:    */     Token(Parser.TType type)
/*  47:    */     {
/*  48: 83 */       this.type = type;
/*  49:    */     }
/*  50:    */     
/*  51:    */     public Parser.TType getType()
/*  52:    */     {
/*  53: 86 */       return this.type;
/*  54:    */     }
/*  55:    */     
/*  56:    */     public Parser.Node getNode()
/*  57:    */       throws IOException
/*  58:    */     {
/*  59: 88 */       throw new IOException("Expected nodetype");
/*  60:    */     }
/*  61:    */     
/*  62:    */     public double getNum()
/*  63:    */       throws IOException
/*  64:    */     {
/*  65: 91 */       throw new IOException("Expected numtype");
/*  66:    */     }
/*  67:    */     
/*  68:    */     public String getStr()
/*  69:    */       throws IOException
/*  70:    */     {
/*  71: 94 */       throw new IOException("Expected strtype");
/*  72:    */     }
/*  73:    */   }
/*  74:    */   
/*  75:    */   @InterfaceAudience.Public
/*  76:    */   @InterfaceStability.Evolving
/*  77:    */   public static class NumToken
/*  78:    */     extends Parser.Token
/*  79:    */   {
/*  80:    */     private double num;
/*  81:    */     
/*  82:    */     public NumToken(double num)
/*  83:    */     {
/*  84:103 */       super();
/*  85:104 */       this.num = num;
/*  86:    */     }
/*  87:    */     
/*  88:    */     public double getNum()
/*  89:    */     {
/*  90:106 */       return this.num;
/*  91:    */     }
/*  92:    */   }
/*  93:    */   
/*  94:    */   @InterfaceAudience.Public
/*  95:    */   @InterfaceStability.Evolving
/*  96:    */   public static class NodeToken
/*  97:    */     extends Parser.Token
/*  98:    */   {
/*  99:    */     private Parser.Node node;
/* 100:    */     
/* 101:    */     NodeToken(Parser.Node node)
/* 102:    */     {
/* 103:114 */       super();
/* 104:115 */       this.node = node;
/* 105:    */     }
/* 106:    */     
/* 107:    */     public Parser.Node getNode()
/* 108:    */     {
/* 109:118 */       return this.node;
/* 110:    */     }
/* 111:    */   }
/* 112:    */   
/* 113:    */   @InterfaceAudience.Public
/* 114:    */   @InterfaceStability.Evolving
/* 115:    */   public static class StrToken
/* 116:    */     extends Parser.Token
/* 117:    */   {
/* 118:    */     private String str;
/* 119:    */     
/* 120:    */     public StrToken(Parser.TType type, String str)
/* 121:    */     {
/* 122:127 */       super();
/* 123:128 */       this.str = str;
/* 124:    */     }
/* 125:    */     
/* 126:    */     public String getStr()
/* 127:    */     {
/* 128:131 */       return this.str;
/* 129:    */     }
/* 130:    */   }
/* 131:    */   
/* 132:    */   private static class Lexer
/* 133:    */   {
/* 134:    */     private StreamTokenizer tok;
/* 135:    */     
/* 136:    */     Lexer(String s)
/* 137:    */     {
/* 138:145 */       this.tok = new StreamTokenizer(new CharArrayReader(s.toCharArray()));
/* 139:146 */       this.tok.quoteChar(34);
/* 140:147 */       this.tok.parseNumbers();
/* 141:148 */       this.tok.ordinaryChar(44);
/* 142:149 */       this.tok.ordinaryChar(40);
/* 143:150 */       this.tok.ordinaryChar(41);
/* 144:151 */       this.tok.wordChars(36, 36);
/* 145:152 */       this.tok.wordChars(95, 95);
/* 146:    */     }
/* 147:    */     
/* 148:    */     Parser.Token next()
/* 149:    */       throws IOException
/* 150:    */     {
/* 151:156 */       int type = this.tok.nextToken();
/* 152:157 */       switch (type)
/* 153:    */       {
/* 154:    */       case -1: 
/* 155:    */       case 10: 
/* 156:160 */         return null;
/* 157:    */       case -2: 
/* 158:162 */         return new Parser.NumToken(this.tok.nval);
/* 159:    */       case -3: 
/* 160:164 */         return new Parser.StrToken(Parser.TType.IDENT, this.tok.sval);
/* 161:    */       case 34: 
/* 162:166 */         return new Parser.StrToken(Parser.TType.QUOT, this.tok.sval);
/* 163:    */       }
/* 164:168 */       switch (type)
/* 165:    */       {
/* 166:    */       case 44: 
/* 167:170 */         return new Parser.Token(Parser.TType.COMMA);
/* 168:    */       case 40: 
/* 169:172 */         return new Parser.Token(Parser.TType.LPAREN);
/* 170:    */       case 41: 
/* 171:174 */         return new Parser.Token(Parser.TType.RPAREN);
/* 172:    */       }
/* 173:176 */       throw new IOException("Unexpected: " + type);
/* 174:    */     }
/* 175:    */   }
/* 176:    */   
/* 177:    */   @InterfaceAudience.Public
/* 178:    */   @InterfaceStability.Evolving
/* 179:    */   public static abstract class Node
/* 180:    */     implements ComposableInputFormat
/* 181:    */   {
/* 182:    */     static Node forIdent(String ident)
/* 183:    */       throws IOException
/* 184:    */     {
/* 185:    */       try
/* 186:    */       {
/* 187:195 */         if (!nodeCstrMap.containsKey(ident)) {
/* 188:196 */           throw new IOException("No nodetype for " + ident);
/* 189:    */         }
/* 190:198 */         return (Node)((Constructor)nodeCstrMap.get(ident)).newInstance(new Object[] { ident });
/* 191:    */       }
/* 192:    */       catch (IllegalAccessException e)
/* 193:    */       {
/* 194:200 */         throw ((IOException)new IOException().initCause(e));
/* 195:    */       }
/* 196:    */       catch (InstantiationException e)
/* 197:    */       {
/* 198:202 */         throw ((IOException)new IOException().initCause(e));
/* 199:    */       }
/* 200:    */       catch (InvocationTargetException e)
/* 201:    */       {
/* 202:204 */         throw ((IOException)new IOException().initCause(e));
/* 203:    */       }
/* 204:    */     }
/* 205:    */     
/* 206:208 */     private static final Class<?>[] ncstrSig = { String.class };
/* 207:210 */     private static final Map<String, Constructor<? extends Node>> nodeCstrMap = new HashMap();
/* 208:213 */     protected static final Map<String, Constructor<? extends ComposableRecordReader>> rrCstrMap = new HashMap();
/* 209:    */     
/* 210:    */     protected static void addIdentifier(String ident, Class<?>[] mcstrSig, Class<? extends Node> nodetype, Class<? extends ComposableRecordReader> cl)
/* 211:    */       throws NoSuchMethodException
/* 212:    */     {
/* 213:227 */       Constructor<? extends Node> ncstr = nodetype.getDeclaredConstructor(ncstrSig);
/* 214:    */       
/* 215:229 */       ncstr.setAccessible(true);
/* 216:230 */       nodeCstrMap.put(ident, ncstr);
/* 217:231 */       Constructor<? extends ComposableRecordReader> mcstr = cl.getDeclaredConstructor(mcstrSig);
/* 218:    */       
/* 219:233 */       mcstr.setAccessible(true);
/* 220:234 */       rrCstrMap.put(ident, mcstr);
/* 221:    */     }
/* 222:    */     
/* 223:238 */     protected int id = -1;
/* 224:    */     protected String ident;
/* 225:    */     protected Class<? extends WritableComparator> cmpcl;
/* 226:    */     
/* 227:    */     protected Node(String ident)
/* 228:    */     {
/* 229:243 */       this.ident = ident;
/* 230:    */     }
/* 231:    */     
/* 232:    */     protected void setID(int id)
/* 233:    */     {
/* 234:247 */       this.id = id;
/* 235:    */     }
/* 236:    */     
/* 237:    */     protected void setKeyComparator(Class<? extends WritableComparator> cmpcl)
/* 238:    */     {
/* 239:251 */       this.cmpcl = cmpcl;
/* 240:    */     }
/* 241:    */     
/* 242:    */     abstract void parse(List<Parser.Token> paramList, JobConf paramJobConf)
/* 243:    */       throws IOException;
/* 244:    */   }
/* 245:    */   
/* 246:    */   static class WNode
/* 247:    */     extends Parser.Node
/* 248:    */   {
/* 249:260 */     private static final Class<?>[] cstrSig = { Integer.TYPE, RecordReader.class, Class.class };
/* 250:    */     private String indir;
/* 251:    */     private InputFormat inf;
/* 252:    */     
/* 253:    */     static void addIdentifier(String ident, Class<? extends ComposableRecordReader> cl)
/* 254:    */       throws NoSuchMethodException
/* 255:    */     {
/* 256:266 */       Parser.Node.addIdentifier(ident, cstrSig, WNode.class, cl);
/* 257:    */     }
/* 258:    */     
/* 259:    */     public WNode(String ident)
/* 260:    */     {
/* 261:273 */       super();
/* 262:    */     }
/* 263:    */     
/* 264:    */     public void parse(List<Parser.Token> ll, JobConf job)
/* 265:    */       throws IOException
/* 266:    */     {
/* 267:281 */       StringBuilder sb = new StringBuilder();
/* 268:282 */       Iterator<Parser.Token> i = ll.iterator();
/* 269:283 */       while (i.hasNext())
/* 270:    */       {
/* 271:284 */         Parser.Token t = (Parser.Token)i.next();
/* 272:285 */         if (Parser.TType.COMMA.equals(t.getType())) {
/* 273:    */           try
/* 274:    */           {
/* 275:287 */             this.inf = ((InputFormat)ReflectionUtils.newInstance(job.getClassByName(sb.toString()), job));
/* 276:    */           }
/* 277:    */           catch (ClassNotFoundException e)
/* 278:    */           {
/* 279:291 */             throw ((IOException)new IOException().initCause(e));
/* 280:    */           }
/* 281:    */           catch (IllegalArgumentException e)
/* 282:    */           {
/* 283:293 */             throw ((IOException)new IOException().initCause(e));
/* 284:    */           }
/* 285:    */         }
/* 286:297 */         sb.append(t.getStr());
/* 287:    */       }
/* 288:299 */       if (!i.hasNext()) {
/* 289:300 */         throw new IOException("Parse error");
/* 290:    */       }
/* 291:302 */       Parser.Token t = (Parser.Token)i.next();
/* 292:303 */       if (!Parser.TType.QUOT.equals(t.getType())) {
/* 293:304 */         throw new IOException("Expected quoted string");
/* 294:    */       }
/* 295:306 */       this.indir = t.getStr();
/* 296:    */     }
/* 297:    */     
/* 298:    */     private JobConf getConf(JobConf job)
/* 299:    */     {
/* 300:311 */       JobConf conf = new JobConf(job);
/* 301:312 */       FileInputFormat.setInputPaths(conf, this.indir);
/* 302:313 */       conf.setClassLoader(job.getClassLoader());
/* 303:314 */       return conf;
/* 304:    */     }
/* 305:    */     
/* 306:    */     public InputSplit[] getSplits(JobConf job, int numSplits)
/* 307:    */       throws IOException
/* 308:    */     {
/* 309:319 */       return this.inf.getSplits(getConf(job), numSplits);
/* 310:    */     }
/* 311:    */     
/* 312:    */     public ComposableRecordReader getRecordReader(InputSplit split, JobConf job, Reporter reporter)
/* 313:    */       throws IOException
/* 314:    */     {
/* 315:    */       try
/* 316:    */       {
/* 317:325 */         if (!rrCstrMap.containsKey(this.ident)) {
/* 318:326 */           throw new IOException("No RecordReader for " + this.ident);
/* 319:    */         }
/* 320:328 */         return (ComposableRecordReader)((Constructor)rrCstrMap.get(this.ident)).newInstance(new Object[] { Integer.valueOf(this.id), this.inf.getRecordReader(split, getConf(job), reporter), this.cmpcl });
/* 321:    */       }
/* 322:    */       catch (IllegalAccessException e)
/* 323:    */       {
/* 324:331 */         throw ((IOException)new IOException().initCause(e));
/* 325:    */       }
/* 326:    */       catch (InstantiationException e)
/* 327:    */       {
/* 328:333 */         throw ((IOException)new IOException().initCause(e));
/* 329:    */       }
/* 330:    */       catch (InvocationTargetException e)
/* 331:    */       {
/* 332:335 */         throw ((IOException)new IOException().initCause(e));
/* 333:    */       }
/* 334:    */     }
/* 335:    */     
/* 336:    */     public String toString()
/* 337:    */     {
/* 338:340 */       return this.ident + "(" + this.inf.getClass().getName() + ",\"" + this.indir + "\")";
/* 339:    */     }
/* 340:    */   }
/* 341:    */   
/* 342:    */   static class CNode
/* 343:    */     extends Parser.Node
/* 344:    */   {
/* 345:349 */     private static final Class<?>[] cstrSig = { Integer.TYPE, JobConf.class, Integer.TYPE, Class.class };
/* 346:    */     
/* 347:    */     static void addIdentifier(String ident, Class<? extends ComposableRecordReader> cl)
/* 348:    */       throws NoSuchMethodException
/* 349:    */     {
/* 350:355 */       Parser.Node.addIdentifier(ident, cstrSig, CNode.class, cl);
/* 351:    */     }
/* 352:    */     
/* 353:359 */     private ArrayList<Parser.Node> kids = new ArrayList();
/* 354:    */     
/* 355:    */     public CNode(String ident)
/* 356:    */     {
/* 357:362 */       super();
/* 358:    */     }
/* 359:    */     
/* 360:    */     public void setKeyComparator(Class<? extends WritableComparator> cmpcl)
/* 361:    */     {
/* 362:366 */       super.setKeyComparator(cmpcl);
/* 363:367 */       for (Parser.Node n : this.kids) {
/* 364:368 */         n.setKeyComparator(cmpcl);
/* 365:    */       }
/* 366:    */     }
/* 367:    */     
/* 368:    */     public InputSplit[] getSplits(JobConf job, int numSplits)
/* 369:    */       throws IOException
/* 370:    */     {
/* 371:378 */       InputSplit[][] splits = new InputSplit[this.kids.size()][];
/* 372:379 */       for (int i = 0; i < this.kids.size(); i++)
/* 373:    */       {
/* 374:380 */         InputSplit[] tmp = ((Parser.Node)this.kids.get(i)).getSplits(job, numSplits);
/* 375:381 */         if (null == tmp) {
/* 376:382 */           throw new IOException("Error gathering splits from child RReader");
/* 377:    */         }
/* 378:384 */         if ((i > 0) && (splits[(i - 1)].length != tmp.length)) {
/* 379:385 */           throw new IOException("Inconsistent split cardinality from child " + i + " (" + splits[(i - 1)].length + "/" + tmp.length + ")");
/* 380:    */         }
/* 381:388 */         splits[i] = tmp;
/* 382:    */       }
/* 383:390 */       int size = splits[0].length;
/* 384:391 */       CompositeInputSplit[] ret = new CompositeInputSplit[size];
/* 385:392 */       for (int i = 0; i < size; i++)
/* 386:    */       {
/* 387:393 */         ret[i] = new CompositeInputSplit(splits.length);
/* 388:394 */         for (int j = 0; j < splits.length; j++) {
/* 389:395 */           ret[i].add(splits[j][i]);
/* 390:    */         }
/* 391:    */       }
/* 392:398 */       return ret;
/* 393:    */     }
/* 394:    */     
/* 395:    */     public ComposableRecordReader getRecordReader(InputSplit split, JobConf job, Reporter reporter)
/* 396:    */       throws IOException
/* 397:    */     {
/* 398:404 */       if (!(split instanceof CompositeInputSplit)) {
/* 399:405 */         throw new IOException("Invalid split type:" + split.getClass().getName());
/* 400:    */       }
/* 401:408 */       CompositeInputSplit spl = (CompositeInputSplit)split;
/* 402:409 */       int capacity = this.kids.size();
/* 403:410 */       CompositeRecordReader ret = null;
/* 404:    */       try
/* 405:    */       {
/* 406:412 */         if (!rrCstrMap.containsKey(this.ident)) {
/* 407:413 */           throw new IOException("No RecordReader for " + this.ident);
/* 408:    */         }
/* 409:415 */         ret = (CompositeRecordReader)((Constructor)rrCstrMap.get(this.ident)).newInstance(new Object[] { Integer.valueOf(this.id), job, Integer.valueOf(capacity), this.cmpcl });
/* 410:    */       }
/* 411:    */       catch (IllegalAccessException e)
/* 412:    */       {
/* 413:418 */         throw ((IOException)new IOException().initCause(e));
/* 414:    */       }
/* 415:    */       catch (InstantiationException e)
/* 416:    */       {
/* 417:420 */         throw ((IOException)new IOException().initCause(e));
/* 418:    */       }
/* 419:    */       catch (InvocationTargetException e)
/* 420:    */       {
/* 421:422 */         throw ((IOException)new IOException().initCause(e));
/* 422:    */       }
/* 423:424 */       for (int i = 0; i < capacity; i++) {
/* 424:425 */         ret.add(((Parser.Node)this.kids.get(i)).getRecordReader(spl.get(i), job, reporter));
/* 425:    */       }
/* 426:427 */       return (ComposableRecordReader)ret;
/* 427:    */     }
/* 428:    */     
/* 429:    */     public void parse(List<Parser.Token> args, JobConf job)
/* 430:    */       throws IOException
/* 431:    */     {
/* 432:434 */       ListIterator<Parser.Token> i = args.listIterator();
/* 433:435 */       while (i.hasNext())
/* 434:    */       {
/* 435:436 */         Parser.Token t = (Parser.Token)i.next();
/* 436:437 */         t.getNode().setID(i.previousIndex() >> 1);
/* 437:438 */         this.kids.add(t.getNode());
/* 438:439 */         if ((i.hasNext()) && (!Parser.TType.COMMA.equals(((Parser.Token)i.next()).getType()))) {
/* 439:440 */           throw new IOException("Expected ','");
/* 440:    */         }
/* 441:    */       }
/* 442:    */     }
/* 443:    */     
/* 444:    */     public String toString()
/* 445:    */     {
/* 446:446 */       StringBuilder sb = new StringBuilder();
/* 447:447 */       sb.append(this.ident + "(");
/* 448:448 */       for (Parser.Node n : this.kids) {
/* 449:449 */         sb.append(n.toString() + ",");
/* 450:    */       }
/* 451:451 */       sb.setCharAt(sb.length() - 1, ')');
/* 452:452 */       return sb.toString();
/* 453:    */     }
/* 454:    */   }
/* 455:    */   
/* 456:    */   private static Token reduce(Stack<Token> st, JobConf job)
/* 457:    */     throws IOException
/* 458:    */   {
/* 459:457 */     LinkedList<Token> args = new LinkedList();
/* 460:458 */     while ((!st.isEmpty()) && (!TType.LPAREN.equals(((Token)st.peek()).getType()))) {
/* 461:459 */       args.addFirst(st.pop());
/* 462:    */     }
/* 463:461 */     if (st.isEmpty()) {
/* 464:462 */       throw new IOException("Unmatched ')'");
/* 465:    */     }
/* 466:464 */     st.pop();
/* 467:465 */     if ((st.isEmpty()) || (!TType.IDENT.equals(((Token)st.peek()).getType()))) {
/* 468:466 */       throw new IOException("Identifier expected");
/* 469:    */     }
/* 470:468 */     Node n = Node.forIdent(((Token)st.pop()).getStr());
/* 471:469 */     n.parse(args, job);
/* 472:470 */     return new NodeToken(n);
/* 473:    */   }
/* 474:    */   
/* 475:    */   static Node parse(String expr, JobConf job)
/* 476:    */     throws IOException
/* 477:    */   {
/* 478:478 */     if (null == expr) {
/* 479:479 */       throw new IOException("Expression is null");
/* 480:    */     }
/* 481:481 */     Class<? extends WritableComparator> cmpcl = job.getClass("mapred.join.keycomparator", null, WritableComparator.class);
/* 482:    */     
/* 483:483 */     Lexer lex = new Lexer(expr);
/* 484:484 */     Stack<Token> st = new Stack();
/* 485:    */     Token tok;
/* 486:486 */     while ((tok = lex.next()) != null) {
/* 487:487 */       if (TType.RPAREN.equals(tok.getType())) {
/* 488:488 */         st.push(reduce(st, job));
/* 489:    */       } else {
/* 490:490 */         st.push(tok);
/* 491:    */       }
/* 492:    */     }
/* 493:493 */     if ((st.size() == 1) && (TType.CIF.equals(((Token)st.peek()).getType())))
/* 494:    */     {
/* 495:494 */       Node ret = ((Token)st.pop()).getNode();
/* 496:495 */       if (cmpcl != null) {
/* 497:496 */         ret.setKeyComparator(cmpcl);
/* 498:    */       }
/* 499:498 */       return ret;
/* 500:    */     }
/* 501:500 */     throw new IOException("Missing ')'");
/* 502:    */   }
/* 503:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.join.Parser
 * JD-Core Version:    0.7.0.1
 */