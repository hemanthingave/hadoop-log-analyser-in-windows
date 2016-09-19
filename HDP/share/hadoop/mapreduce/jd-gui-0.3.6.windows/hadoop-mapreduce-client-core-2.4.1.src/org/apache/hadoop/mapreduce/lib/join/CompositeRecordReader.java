/*   1:    */ package org.apache.hadoop.mapreduce.lib.join;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import java.util.Comparator;
/*   6:    */ import java.util.PriorityQueue;
/*   7:    */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*   8:    */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*   9:    */ import org.apache.hadoop.conf.Configurable;
/*  10:    */ import org.apache.hadoop.conf.Configuration;
/*  11:    */ import org.apache.hadoop.io.NullWritable;
/*  12:    */ import org.apache.hadoop.io.Writable;
/*  13:    */ import org.apache.hadoop.io.WritableComparable;
/*  14:    */ import org.apache.hadoop.io.WritableComparator;
/*  15:    */ import org.apache.hadoop.mapreduce.InputSplit;
/*  16:    */ import org.apache.hadoop.mapreduce.RecordReader;
/*  17:    */ import org.apache.hadoop.mapreduce.TaskAttemptContext;
/*  18:    */ import org.apache.hadoop.util.ReflectionUtils;
/*  19:    */ 
/*  20:    */ @InterfaceAudience.Public
/*  21:    */ @InterfaceStability.Stable
/*  22:    */ public abstract class CompositeRecordReader<K extends WritableComparable<?>, V extends Writable, X extends Writable>
/*  23:    */   extends ComposableRecordReader<K, X>
/*  24:    */   implements Configurable
/*  25:    */ {
/*  26:    */   private int id;
/*  27:    */   protected Configuration conf;
/*  28: 54 */   private final ResetableIterator<X> EMPTY = new ResetableIterator.EMPTY();
/*  29:    */   private WritableComparator cmp;
/*  30: 57 */   protected Class<? extends WritableComparable> keyclass = null;
/*  31:    */   private PriorityQueue<ComposableRecordReader<K, ?>> q;
/*  32:    */   protected final CompositeRecordReader<K, V, X>.JoinCollector jc;
/*  33:    */   protected final ComposableRecordReader<K, ? extends V>[] kids;
/*  34:    */   protected K key;
/*  35:    */   protected X value;
/*  36:    */   
/*  37:    */   protected abstract boolean combine(Object[] paramArrayOfObject, TupleWritable paramTupleWritable);
/*  38:    */   
/*  39:    */   public CompositeRecordReader(int id, int capacity, Class<? extends WritableComparator> cmpcl)
/*  40:    */     throws IOException
/*  41:    */   {
/*  42: 79 */     assert (capacity > 0) : "Invalid capacity";
/*  43: 80 */     this.id = id;
/*  44: 81 */     if (null != cmpcl)
/*  45:    */     {
/*  46: 82 */       this.cmp = ((WritableComparator)ReflectionUtils.newInstance(cmpcl, null));
/*  47: 83 */       this.q = new PriorityQueue(3, new Comparator()
/*  48:    */       {
/*  49:    */         public int compare(ComposableRecordReader<K, ?> o1, ComposableRecordReader<K, ?> o2)
/*  50:    */         {
/*  51: 87 */           return CompositeRecordReader.this.cmp.compare(o1.key(), o2.key());
/*  52:    */         }
/*  53:    */       });
/*  54:    */     }
/*  55: 91 */     this.jc = new JoinCollector(capacity);
/*  56: 92 */     this.kids = new ComposableRecordReader[capacity];
/*  57:    */   }
/*  58:    */   
/*  59:    */   public void initialize(InputSplit split, TaskAttemptContext context)
/*  60:    */     throws IOException, InterruptedException
/*  61:    */   {
/*  62: 98 */     if (this.kids != null) {
/*  63: 99 */       for (int i = 0; i < this.kids.length; i++)
/*  64:    */       {
/*  65:100 */         this.kids[i].initialize(((CompositeInputSplit)split).get(i), context);
/*  66:101 */         if (this.kids[i].key() != null)
/*  67:    */         {
/*  68:106 */           if (this.keyclass == null) {
/*  69:107 */             this.keyclass = this.kids[i].createKey().getClass().asSubclass(WritableComparable.class);
/*  70:    */           }
/*  71:111 */           if (null == this.q)
/*  72:    */           {
/*  73:112 */             this.cmp = WritableComparator.get(this.keyclass);
/*  74:113 */             this.q = new PriorityQueue(3, new Comparator()
/*  75:    */             {
/*  76:    */               public int compare(ComposableRecordReader<K, ?> o1, ComposableRecordReader<K, ?> o2)
/*  77:    */               {
/*  78:117 */                 return CompositeRecordReader.this.cmp.compare(o1.key(), o2.key());
/*  79:    */               }
/*  80:    */             });
/*  81:    */           }
/*  82:122 */           if (!this.keyclass.equals(this.kids[i].key().getClass())) {
/*  83:123 */             throw new ClassCastException("Child key classes fail to agree");
/*  84:    */           }
/*  85:127 */           if (this.kids[i].hasNext()) {
/*  86:128 */             this.q.add(this.kids[i]);
/*  87:    */           }
/*  88:    */         }
/*  89:    */       }
/*  90:    */     }
/*  91:    */   }
/*  92:    */   
/*  93:    */   public int id()
/*  94:    */   {
/*  95:138 */     return this.id;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public void setConf(Configuration conf)
/*  99:    */   {
/* 100:145 */     this.conf = conf;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public Configuration getConf()
/* 104:    */   {
/* 105:152 */     return this.conf;
/* 106:    */   }
/* 107:    */   
/* 108:    */   protected PriorityQueue<ComposableRecordReader<K, ?>> getRecordReaderQueue()
/* 109:    */   {
/* 110:159 */     return this.q;
/* 111:    */   }
/* 112:    */   
/* 113:    */   protected WritableComparator getComparator()
/* 114:    */   {
/* 115:167 */     return this.cmp;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public void add(ComposableRecordReader<K, ? extends V> rr)
/* 119:    */     throws IOException, InterruptedException
/* 120:    */   {
/* 121:178 */     this.kids[rr.id()] = rr;
/* 122:    */   }
/* 123:    */   
/* 124:    */   public class JoinCollector
/* 125:    */   {
/* 126:    */     private K key;
/* 127:    */     private ResetableIterator<X>[] iters;
/* 128:190 */     private int pos = -1;
/* 129:191 */     private boolean first = true;
/* 130:    */     
/* 131:    */     public JoinCollector(int card)
/* 132:    */     {
/* 133:199 */       this.iters = new ResetableIterator[card];
/* 134:200 */       for (int i = 0; i < this.iters.length; i++) {
/* 135:201 */         this.iters[i] = CompositeRecordReader.this.EMPTY;
/* 136:    */       }
/* 137:    */     }
/* 138:    */     
/* 139:    */     public void add(int id, ResetableIterator<X> i)
/* 140:    */       throws IOException
/* 141:    */     {
/* 142:210 */       this.iters[id] = i;
/* 143:    */     }
/* 144:    */     
/* 145:    */     public K key()
/* 146:    */     {
/* 147:217 */       return this.key;
/* 148:    */     }
/* 149:    */     
/* 150:    */     public void reset(K key)
/* 151:    */     {
/* 152:226 */       this.key = key;
/* 153:227 */       this.first = true;
/* 154:228 */       this.pos = (this.iters.length - 1);
/* 155:229 */       for (int i = 0; i < this.iters.length; i++) {
/* 156:230 */         this.iters[i].reset();
/* 157:    */       }
/* 158:    */     }
/* 159:    */     
/* 160:    */     public void clear()
/* 161:    */     {
/* 162:238 */       this.key = null;
/* 163:239 */       this.pos = -1;
/* 164:240 */       for (int i = 0; i < this.iters.length; i++)
/* 165:    */       {
/* 166:241 */         this.iters[i].clear();
/* 167:242 */         this.iters[i] = CompositeRecordReader.this.EMPTY;
/* 168:    */       }
/* 169:    */     }
/* 170:    */     
/* 171:    */     public boolean hasNext()
/* 172:    */     {
/* 173:250 */       return this.pos >= 0;
/* 174:    */     }
/* 175:    */     
/* 176:    */     protected boolean next(TupleWritable val)
/* 177:    */       throws IOException
/* 178:    */     {
/* 179:261 */       if (this.first)
/* 180:    */       {
/* 181:262 */         int i = -1;
/* 182:263 */         for (this.pos = 0; this.pos < this.iters.length; this.pos += 1) {
/* 183:264 */           if ((this.iters[this.pos].hasNext()) && (this.iters[this.pos].next(val.get(this.pos))))
/* 184:    */           {
/* 185:265 */             i = this.pos;
/* 186:266 */             val.setWritten(i);
/* 187:    */           }
/* 188:    */         }
/* 189:269 */         this.pos = i;
/* 190:270 */         this.first = false;
/* 191:271 */         if (this.pos < 0)
/* 192:    */         {
/* 193:272 */           clear();
/* 194:273 */           return false;
/* 195:    */         }
/* 196:275 */         return true;
/* 197:    */       }
/* 198:277 */       while ((0 <= this.pos) && ((!this.iters[this.pos].hasNext()) || (!this.iters[this.pos].next(val.get(this.pos))))) {
/* 199:279 */         this.pos -= 1;
/* 200:    */       }
/* 201:281 */       if (this.pos < 0)
/* 202:    */       {
/* 203:282 */         clear();
/* 204:283 */         return false;
/* 205:    */       }
/* 206:285 */       val.setWritten(this.pos);
/* 207:286 */       for (int i = 0; i < this.pos; i++) {
/* 208:287 */         if (this.iters[i].replay(val.get(i))) {
/* 209:288 */           val.setWritten(i);
/* 210:    */         }
/* 211:    */       }
/* 212:291 */       while (this.pos + 1 < this.iters.length)
/* 213:    */       {
/* 214:292 */         this.pos += 1;
/* 215:293 */         this.iters[this.pos].reset();
/* 216:294 */         if ((this.iters[this.pos].hasNext()) && (this.iters[this.pos].next(val.get(this.pos)))) {
/* 217:295 */           val.setWritten(this.pos);
/* 218:    */         }
/* 219:    */       }
/* 220:298 */       return true;
/* 221:    */     }
/* 222:    */     
/* 223:    */     public boolean replay(TupleWritable val)
/* 224:    */       throws IOException
/* 225:    */     {
/* 226:309 */       assert (!this.first);
/* 227:310 */       boolean ret = false;
/* 228:311 */       for (int i = 0; i < this.iters.length; i++) {
/* 229:312 */         if (this.iters[i].replay(val.get(i)))
/* 230:    */         {
/* 231:313 */           val.setWritten(i);
/* 232:314 */           ret = true;
/* 233:    */         }
/* 234:    */       }
/* 235:317 */       return ret;
/* 236:    */     }
/* 237:    */     
/* 238:    */     public void close()
/* 239:    */       throws IOException
/* 240:    */     {
/* 241:324 */       for (int i = 0; i < this.iters.length; i++) {
/* 242:325 */         this.iters[i].close();
/* 243:    */       }
/* 244:    */     }
/* 245:    */     
/* 246:    */     public boolean flush(TupleWritable value)
/* 247:    */       throws IOException
/* 248:    */     {
/* 249:334 */       while (hasNext())
/* 250:    */       {
/* 251:335 */         value.clearWritten();
/* 252:336 */         if ((next(value)) && (CompositeRecordReader.this.combine(CompositeRecordReader.this.kids, value))) {
/* 253:337 */           return true;
/* 254:    */         }
/* 255:    */       }
/* 256:340 */       return false;
/* 257:    */     }
/* 258:    */   }
/* 259:    */   
/* 260:    */   public K key()
/* 261:    */   {
/* 262:349 */     if (this.jc.hasNext()) {
/* 263:350 */       return this.jc.key();
/* 264:    */     }
/* 265:352 */     if (!this.q.isEmpty()) {
/* 266:353 */       return ((ComposableRecordReader)this.q.peek()).key();
/* 267:    */     }
/* 268:355 */     return null;
/* 269:    */   }
/* 270:    */   
/* 271:    */   public void key(K key)
/* 272:    */     throws IOException
/* 273:    */   {
/* 274:362 */     ReflectionUtils.copy(this.conf, key(), key);
/* 275:    */   }
/* 276:    */   
/* 277:    */   public K getCurrentKey()
/* 278:    */   {
/* 279:366 */     return this.key;
/* 280:    */   }
/* 281:    */   
/* 282:    */   public boolean hasNext()
/* 283:    */   {
/* 284:373 */     return (this.jc.hasNext()) || (!this.q.isEmpty());
/* 285:    */   }
/* 286:    */   
/* 287:    */   public void skip(K key)
/* 288:    */     throws IOException, InterruptedException
/* 289:    */   {
/* 290:380 */     ArrayList<ComposableRecordReader<K, ?>> tmp = new ArrayList();
/* 291:382 */     while ((!this.q.isEmpty()) && (this.cmp.compare(((ComposableRecordReader)this.q.peek()).key(), key) <= 0)) {
/* 292:383 */       tmp.add(this.q.poll());
/* 293:    */     }
/* 294:385 */     for (ComposableRecordReader<K, ?> rr : tmp)
/* 295:    */     {
/* 296:386 */       rr.skip(key);
/* 297:387 */       if (rr.hasNext()) {
/* 298:388 */         this.q.add(rr);
/* 299:    */       }
/* 300:    */     }
/* 301:    */   }
/* 302:    */   
/* 303:    */   protected abstract ResetableIterator<X> getDelegate();
/* 304:    */   
/* 305:    */   public void accept(JoinCollector jc, K key)
/* 306:    */     throws IOException, InterruptedException
/* 307:    */   {
/* 308:407 */     if ((hasNext()) && (0 == this.cmp.compare(key, key())))
/* 309:    */     {
/* 310:408 */       fillJoinCollector(createKey());
/* 311:409 */       jc.add(this.id, getDelegate());
/* 312:410 */       return;
/* 313:    */     }
/* 314:412 */     jc.add(this.id, this.EMPTY);
/* 315:    */   }
/* 316:    */   
/* 317:    */   protected void fillJoinCollector(K iterkey)
/* 318:    */     throws IOException, InterruptedException
/* 319:    */   {
/* 320:421 */     if (!this.q.isEmpty())
/* 321:    */     {
/* 322:422 */       ((ComposableRecordReader)this.q.peek()).key(iterkey);
/* 323:423 */       while (0 == this.cmp.compare(((ComposableRecordReader)this.q.peek()).key(), iterkey))
/* 324:    */       {
/* 325:424 */         ComposableRecordReader<K, ?> t = (ComposableRecordReader)this.q.poll();
/* 326:425 */         t.accept(this.jc, iterkey);
/* 327:426 */         if (t.hasNext()) {
/* 328:427 */           this.q.add(t);
/* 329:428 */         } else if (this.q.isEmpty()) {
/* 330:429 */           return;
/* 331:    */         }
/* 332:    */       }
/* 333:    */     }
/* 334:    */   }
/* 335:    */   
/* 336:    */   public int compareTo(ComposableRecordReader<K, ?> other)
/* 337:    */   {
/* 338:440 */     return this.cmp.compare(key(), other.key());
/* 339:    */   }
/* 340:    */   
/* 341:    */   protected K createKey()
/* 342:    */   {
/* 343:449 */     if ((this.keyclass == null) || (this.keyclass.equals(NullWritable.class))) {
/* 344:450 */       return NullWritable.get();
/* 345:    */     }
/* 346:452 */     return (WritableComparable)ReflectionUtils.newInstance(this.keyclass, getConf());
/* 347:    */   }
/* 348:    */   
/* 349:    */   protected TupleWritable createTupleWritable()
/* 350:    */   {
/* 351:459 */     Writable[] vals = new Writable[this.kids.length];
/* 352:460 */     for (int i = 0; i < vals.length; i++) {
/* 353:461 */       vals[i] = this.kids[i].createValue();
/* 354:    */     }
/* 355:463 */     return new TupleWritable(vals);
/* 356:    */   }
/* 357:    */   
/* 358:    */   public X getCurrentValue()
/* 359:    */     throws IOException, InterruptedException
/* 360:    */   {
/* 361:469 */     return this.value;
/* 362:    */   }
/* 363:    */   
/* 364:    */   public void close()
/* 365:    */     throws IOException
/* 366:    */   {
/* 367:476 */     if (this.kids != null) {
/* 368:477 */       for (RecordReader<K, ? extends Writable> rr : this.kids) {
/* 369:478 */         rr.close();
/* 370:    */       }
/* 371:    */     }
/* 372:481 */     if (this.jc != null) {
/* 373:482 */       this.jc.close();
/* 374:    */     }
/* 375:    */   }
/* 376:    */   
/* 377:    */   public float getProgress()
/* 378:    */     throws IOException, InterruptedException
/* 379:    */   {
/* 380:490 */     float ret = 1.0F;
/* 381:491 */     for (RecordReader<K, ? extends Writable> rr : this.kids) {
/* 382:492 */       ret = Math.min(ret, rr.getProgress());
/* 383:    */     }
/* 384:494 */     return ret;
/* 385:    */   }
/* 386:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.lib.join.CompositeRecordReader
 * JD-Core Version:    0.7.0.1
 */