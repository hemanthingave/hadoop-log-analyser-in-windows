/*   1:    */ package org.apache.hadoop.mapred.join;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import java.util.Comparator;
/*   6:    */ import java.util.PriorityQueue;
/*   7:    */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*   8:    */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*   9:    */ import org.apache.hadoop.conf.Configurable;
/*  10:    */ import org.apache.hadoop.conf.Configuration;
/*  11:    */ import org.apache.hadoop.io.Writable;
/*  12:    */ import org.apache.hadoop.io.WritableComparable;
/*  13:    */ import org.apache.hadoop.io.WritableComparator;
/*  14:    */ import org.apache.hadoop.io.WritableUtils;
/*  15:    */ import org.apache.hadoop.mapred.RecordReader;
/*  16:    */ import org.apache.hadoop.util.ReflectionUtils;
/*  17:    */ 
/*  18:    */ @InterfaceAudience.Public
/*  19:    */ @InterfaceStability.Stable
/*  20:    */ public abstract class CompositeRecordReader<K extends WritableComparable, V extends Writable, X extends Writable>
/*  21:    */   implements Configurable
/*  22:    */ {
/*  23:    */   private int id;
/*  24:    */   private Configuration conf;
/*  25: 52 */   private final ResetableIterator<X> EMPTY = new ResetableIterator.EMPTY();
/*  26:    */   private WritableComparator cmp;
/*  27:    */   private Class<? extends WritableComparable> keyclass;
/*  28:    */   private PriorityQueue<ComposableRecordReader<K, ?>> q;
/*  29:    */   protected final CompositeRecordReader<K, V, X>.JoinCollector jc;
/*  30:    */   protected final ComposableRecordReader<K, ? extends V>[] kids;
/*  31:    */   
/*  32:    */   protected abstract boolean combine(Object[] paramArrayOfObject, TupleWritable paramTupleWritable);
/*  33:    */   
/*  34:    */   public CompositeRecordReader(int id, int capacity, Class<? extends WritableComparator> cmpcl)
/*  35:    */     throws IOException
/*  36:    */   {
/*  37: 73 */     assert (capacity > 0) : "Invalid capacity";
/*  38: 74 */     this.id = id;
/*  39: 75 */     if (null != cmpcl)
/*  40:    */     {
/*  41: 76 */       this.cmp = ((WritableComparator)ReflectionUtils.newInstance(cmpcl, null));
/*  42: 77 */       this.q = new PriorityQueue(3, new Comparator()
/*  43:    */       {
/*  44:    */         public int compare(ComposableRecordReader<K, ?> o1, ComposableRecordReader<K, ?> o2)
/*  45:    */         {
/*  46: 81 */           return CompositeRecordReader.this.cmp.compare(o1.key(), o2.key());
/*  47:    */         }
/*  48:    */       });
/*  49:    */     }
/*  50: 85 */     this.jc = new JoinCollector(capacity);
/*  51: 86 */     this.kids = new ComposableRecordReader[capacity];
/*  52:    */   }
/*  53:    */   
/*  54:    */   public int id()
/*  55:    */   {
/*  56: 93 */     return this.id;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public void setConf(Configuration conf)
/*  60:    */   {
/*  61:100 */     this.conf = conf;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public Configuration getConf()
/*  65:    */   {
/*  66:107 */     return this.conf;
/*  67:    */   }
/*  68:    */   
/*  69:    */   protected PriorityQueue<ComposableRecordReader<K, ?>> getRecordReaderQueue()
/*  70:    */   {
/*  71:114 */     return this.q;
/*  72:    */   }
/*  73:    */   
/*  74:    */   protected WritableComparator getComparator()
/*  75:    */   {
/*  76:122 */     return this.cmp;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public void add(ComposableRecordReader<K, ? extends V> rr)
/*  80:    */     throws IOException
/*  81:    */   {
/*  82:132 */     this.kids[rr.id()] = rr;
/*  83:133 */     if (null == this.q)
/*  84:    */     {
/*  85:134 */       this.cmp = WritableComparator.get(((WritableComparable)rr.createKey()).getClass());
/*  86:135 */       this.q = new PriorityQueue(3, new Comparator()
/*  87:    */       {
/*  88:    */         public int compare(ComposableRecordReader<K, ?> o1, ComposableRecordReader<K, ?> o2)
/*  89:    */         {
/*  90:139 */           return CompositeRecordReader.this.cmp.compare(o1.key(), o2.key());
/*  91:    */         }
/*  92:    */       });
/*  93:    */     }
/*  94:143 */     if (rr.hasNext()) {
/*  95:144 */       this.q.add(rr);
/*  96:    */     }
/*  97:    */   }
/*  98:    */   
/*  99:    */   class JoinCollector
/* 100:    */   {
/* 101:    */     private K key;
/* 102:    */     private ResetableIterator<X>[] iters;
/* 103:157 */     private int pos = -1;
/* 104:158 */     private boolean first = true;
/* 105:    */     
/* 106:    */     public JoinCollector(int card)
/* 107:    */     {
/* 108:166 */       this.iters = new ResetableIterator[card];
/* 109:167 */       for (int i = 0; i < this.iters.length; i++) {
/* 110:168 */         this.iters[i] = CompositeRecordReader.this.EMPTY;
/* 111:    */       }
/* 112:    */     }
/* 113:    */     
/* 114:    */     public void add(int id, ResetableIterator<X> i)
/* 115:    */       throws IOException
/* 116:    */     {
/* 117:177 */       this.iters[id] = i;
/* 118:    */     }
/* 119:    */     
/* 120:    */     public K key()
/* 121:    */     {
/* 122:184 */       return this.key;
/* 123:    */     }
/* 124:    */     
/* 125:    */     public void reset(K key)
/* 126:    */     {
/* 127:193 */       this.key = key;
/* 128:194 */       this.first = true;
/* 129:195 */       this.pos = (this.iters.length - 1);
/* 130:196 */       for (int i = 0; i < this.iters.length; i++) {
/* 131:197 */         this.iters[i].reset();
/* 132:    */       }
/* 133:    */     }
/* 134:    */     
/* 135:    */     public void clear()
/* 136:    */     {
/* 137:205 */       this.key = null;
/* 138:206 */       this.pos = -1;
/* 139:207 */       for (int i = 0; i < this.iters.length; i++)
/* 140:    */       {
/* 141:208 */         this.iters[i].clear();
/* 142:209 */         this.iters[i] = CompositeRecordReader.this.EMPTY;
/* 143:    */       }
/* 144:    */     }
/* 145:    */     
/* 146:    */     protected boolean hasNext()
/* 147:    */     {
/* 148:217 */       return this.pos >= 0;
/* 149:    */     }
/* 150:    */     
/* 151:    */     protected boolean next(TupleWritable val)
/* 152:    */       throws IOException
/* 153:    */     {
/* 154:228 */       if (this.first)
/* 155:    */       {
/* 156:229 */         int i = -1;
/* 157:230 */         for (this.pos = 0; this.pos < this.iters.length; this.pos += 1) {
/* 158:231 */           if ((this.iters[this.pos].hasNext()) && (this.iters[this.pos].next(val.get(this.pos))))
/* 159:    */           {
/* 160:232 */             i = this.pos;
/* 161:233 */             val.setWritten(i);
/* 162:    */           }
/* 163:    */         }
/* 164:236 */         this.pos = i;
/* 165:237 */         this.first = false;
/* 166:238 */         if (this.pos < 0)
/* 167:    */         {
/* 168:239 */           clear();
/* 169:240 */           return false;
/* 170:    */         }
/* 171:242 */         return true;
/* 172:    */       }
/* 173:244 */       while ((0 <= this.pos) && ((!this.iters[this.pos].hasNext()) || (!this.iters[this.pos].next(val.get(this.pos))))) {
/* 174:246 */         this.pos -= 1;
/* 175:    */       }
/* 176:248 */       if (this.pos < 0)
/* 177:    */       {
/* 178:249 */         clear();
/* 179:250 */         return false;
/* 180:    */       }
/* 181:252 */       val.setWritten(this.pos);
/* 182:253 */       for (int i = 0; i < this.pos; i++) {
/* 183:254 */         if (this.iters[i].replay(val.get(i))) {
/* 184:255 */           val.setWritten(i);
/* 185:    */         }
/* 186:    */       }
/* 187:258 */       while (this.pos + 1 < this.iters.length)
/* 188:    */       {
/* 189:259 */         this.pos += 1;
/* 190:260 */         this.iters[this.pos].reset();
/* 191:261 */         if ((this.iters[this.pos].hasNext()) && (this.iters[this.pos].next(val.get(this.pos)))) {
/* 192:262 */           val.setWritten(this.pos);
/* 193:    */         }
/* 194:    */       }
/* 195:265 */       return true;
/* 196:    */     }
/* 197:    */     
/* 198:    */     public boolean replay(TupleWritable val)
/* 199:    */       throws IOException
/* 200:    */     {
/* 201:276 */       assert (!this.first);
/* 202:277 */       boolean ret = false;
/* 203:278 */       for (int i = 0; i < this.iters.length; i++) {
/* 204:279 */         if (this.iters[i].replay(val.get(i)))
/* 205:    */         {
/* 206:280 */           val.setWritten(i);
/* 207:281 */           ret = true;
/* 208:    */         }
/* 209:    */       }
/* 210:284 */       return ret;
/* 211:    */     }
/* 212:    */     
/* 213:    */     public void close()
/* 214:    */       throws IOException
/* 215:    */     {
/* 216:291 */       for (int i = 0; i < this.iters.length; i++) {
/* 217:292 */         this.iters[i].close();
/* 218:    */       }
/* 219:    */     }
/* 220:    */     
/* 221:    */     public boolean flush(TupleWritable value)
/* 222:    */       throws IOException
/* 223:    */     {
/* 224:301 */       while (hasNext())
/* 225:    */       {
/* 226:302 */         value.clearWritten();
/* 227:303 */         if ((next(value)) && (CompositeRecordReader.this.combine(CompositeRecordReader.this.kids, value))) {
/* 228:304 */           return true;
/* 229:    */         }
/* 230:    */       }
/* 231:307 */       return false;
/* 232:    */     }
/* 233:    */   }
/* 234:    */   
/* 235:    */   public K key()
/* 236:    */   {
/* 237:316 */     if (this.jc.hasNext()) {
/* 238:317 */       return this.jc.key();
/* 239:    */     }
/* 240:319 */     if (!this.q.isEmpty()) {
/* 241:320 */       return ((ComposableRecordReader)this.q.peek()).key();
/* 242:    */     }
/* 243:322 */     return null;
/* 244:    */   }
/* 245:    */   
/* 246:    */   public void key(K key)
/* 247:    */     throws IOException
/* 248:    */   {
/* 249:329 */     WritableUtils.cloneInto(key, key());
/* 250:    */   }
/* 251:    */   
/* 252:    */   public boolean hasNext()
/* 253:    */   {
/* 254:336 */     return (this.jc.hasNext()) || (!this.q.isEmpty());
/* 255:    */   }
/* 256:    */   
/* 257:    */   public void skip(K key)
/* 258:    */     throws IOException
/* 259:    */   {
/* 260:343 */     ArrayList<ComposableRecordReader<K, ?>> tmp = new ArrayList();
/* 261:345 */     while ((!this.q.isEmpty()) && (this.cmp.compare(((ComposableRecordReader)this.q.peek()).key(), key) <= 0)) {
/* 262:346 */       tmp.add(this.q.poll());
/* 263:    */     }
/* 264:348 */     for (ComposableRecordReader<K, ?> rr : tmp)
/* 265:    */     {
/* 266:349 */       rr.skip(key);
/* 267:350 */       if (rr.hasNext()) {
/* 268:351 */         this.q.add(rr);
/* 269:    */       }
/* 270:    */     }
/* 271:    */   }
/* 272:    */   
/* 273:    */   protected abstract ResetableIterator<X> getDelegate();
/* 274:    */   
/* 275:    */   public void accept(JoinCollector jc, K key)
/* 276:    */     throws IOException
/* 277:    */   {
/* 278:369 */     if ((hasNext()) && (0 == this.cmp.compare(key, key())))
/* 279:    */     {
/* 280:370 */       fillJoinCollector(createKey());
/* 281:371 */       jc.add(this.id, getDelegate());
/* 282:372 */       return;
/* 283:    */     }
/* 284:374 */     jc.add(this.id, this.EMPTY);
/* 285:    */   }
/* 286:    */   
/* 287:    */   protected void fillJoinCollector(K iterkey)
/* 288:    */     throws IOException
/* 289:    */   {
/* 290:382 */     if (!this.q.isEmpty())
/* 291:    */     {
/* 292:383 */       ((ComposableRecordReader)this.q.peek()).key(iterkey);
/* 293:384 */       while (0 == this.cmp.compare(((ComposableRecordReader)this.q.peek()).key(), iterkey))
/* 294:    */       {
/* 295:385 */         ComposableRecordReader<K, ?> t = (ComposableRecordReader)this.q.poll();
/* 296:386 */         t.accept(this.jc, iterkey);
/* 297:387 */         if (t.hasNext()) {
/* 298:388 */           this.q.add(t);
/* 299:389 */         } else if (this.q.isEmpty()) {
/* 300:390 */           return;
/* 301:    */         }
/* 302:    */       }
/* 303:    */     }
/* 304:    */   }
/* 305:    */   
/* 306:    */   public int compareTo(ComposableRecordReader<K, ?> other)
/* 307:    */   {
/* 308:401 */     return this.cmp.compare(key(), other.key());
/* 309:    */   }
/* 310:    */   
/* 311:    */   public K createKey()
/* 312:    */   {
/* 313:410 */     if (null == this.keyclass)
/* 314:    */     {
/* 315:411 */       Class<?> cls = ((WritableComparable)this.kids[0].createKey()).getClass();
/* 316:412 */       for (RecordReader<K, ? extends Writable> rr : this.kids) {
/* 317:413 */         if (!cls.equals(((WritableComparable)rr.createKey()).getClass())) {
/* 318:414 */           throw new ClassCastException("Child key classes fail to agree");
/* 319:    */         }
/* 320:    */       }
/* 321:417 */       this.keyclass = cls.asSubclass(WritableComparable.class);
/* 322:    */     }
/* 323:419 */     return (WritableComparable)ReflectionUtils.newInstance(this.keyclass, getConf());
/* 324:    */   }
/* 325:    */   
/* 326:    */   protected TupleWritable createInternalValue()
/* 327:    */   {
/* 328:426 */     Writable[] vals = new Writable[this.kids.length];
/* 329:427 */     for (int i = 0; i < vals.length; i++) {
/* 330:428 */       vals[i] = ((Writable)this.kids[i].createValue());
/* 331:    */     }
/* 332:430 */     return new TupleWritable(vals);
/* 333:    */   }
/* 334:    */   
/* 335:    */   public long getPos()
/* 336:    */     throws IOException
/* 337:    */   {
/* 338:437 */     return 0L;
/* 339:    */   }
/* 340:    */   
/* 341:    */   public void close()
/* 342:    */     throws IOException
/* 343:    */   {
/* 344:444 */     if (this.kids != null) {
/* 345:445 */       for (RecordReader<K, ? extends Writable> rr : this.kids) {
/* 346:446 */         rr.close();
/* 347:    */       }
/* 348:    */     }
/* 349:449 */     if (this.jc != null) {
/* 350:450 */       this.jc.close();
/* 351:    */     }
/* 352:    */   }
/* 353:    */   
/* 354:    */   public float getProgress()
/* 355:    */     throws IOException
/* 356:    */   {
/* 357:458 */     float ret = 1.0F;
/* 358:459 */     for (RecordReader<K, ? extends Writable> rr : this.kids) {
/* 359:460 */       ret = Math.min(ret, rr.getProgress());
/* 360:    */     }
/* 361:462 */     return ret;
/* 362:    */   }
/* 363:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.join.CompositeRecordReader
 * JD-Core Version:    0.7.0.1
 */