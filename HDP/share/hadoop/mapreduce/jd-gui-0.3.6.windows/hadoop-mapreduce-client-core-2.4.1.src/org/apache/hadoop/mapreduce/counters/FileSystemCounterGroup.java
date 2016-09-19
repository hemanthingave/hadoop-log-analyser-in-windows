/*   1:    */ package org.apache.hadoop.mapreduce.counters;
/*   2:    */ 
/*   3:    */ import com.google.common.base.Joiner;
/*   4:    */ import com.google.common.base.Preconditions;
/*   5:    */ import com.google.common.collect.AbstractIterator;
/*   6:    */ import com.google.common.collect.Iterators;
/*   7:    */ import com.google.common.collect.Maps;
/*   8:    */ import java.io.DataInput;
/*   9:    */ import java.io.DataOutput;
/*  10:    */ import java.io.IOException;
/*  11:    */ import java.util.Arrays;
/*  12:    */ import java.util.Collection;
/*  13:    */ import java.util.Iterator;
/*  14:    */ import java.util.Locale;
/*  15:    */ import java.util.Map;
/*  16:    */ import java.util.Map.Entry;
/*  17:    */ import java.util.concurrent.ConcurrentMap;
/*  18:    */ import java.util.concurrent.ConcurrentSkipListMap;
/*  19:    */ import org.apache.hadoop.classification.InterfaceAudience.Private;
/*  20:    */ import org.apache.hadoop.io.WritableUtils;
/*  21:    */ import org.apache.hadoop.mapreduce.Counter;
/*  22:    */ import org.apache.hadoop.mapreduce.FileSystemCounter;
/*  23:    */ import org.apache.hadoop.mapreduce.util.ResourceBundles;
/*  24:    */ 
/*  25:    */ @InterfaceAudience.Private
/*  26:    */ public abstract class FileSystemCounterGroup<C extends Counter>
/*  27:    */   implements CounterGroupBase<C>
/*  28:    */ {
/*  29:    */   static final int MAX_NUM_SCHEMES = 100;
/*  30: 55 */   static final ConcurrentMap<String, String> schemes = ;
/*  31:    */   private final Map<String, Object[]> map;
/*  32:    */   private String displayName;
/*  33:    */   
/*  34:    */   public FileSystemCounterGroup()
/*  35:    */   {
/*  36: 59 */     this.map = new ConcurrentSkipListMap();
/*  37:    */   }
/*  38:    */   
/*  39: 63 */   private static final Joiner NAME_JOINER = Joiner.on('_');
/*  40: 64 */   private static final Joiner DISP_JOINER = Joiner.on(": ");
/*  41:    */   
/*  42:    */   @InterfaceAudience.Private
/*  43:    */   public static class FSCounter
/*  44:    */     extends AbstractCounter
/*  45:    */   {
/*  46:    */     final String scheme;
/*  47:    */     final FileSystemCounter key;
/*  48:    */     private long value;
/*  49:    */     
/*  50:    */     public FSCounter(String scheme, FileSystemCounter ref)
/*  51:    */     {
/*  52: 73 */       this.scheme = scheme;
/*  53: 74 */       this.key = ref;
/*  54:    */     }
/*  55:    */     
/*  56:    */     @InterfaceAudience.Private
/*  57:    */     public String getScheme()
/*  58:    */     {
/*  59: 79 */       return this.scheme;
/*  60:    */     }
/*  61:    */     
/*  62:    */     @InterfaceAudience.Private
/*  63:    */     public FileSystemCounter getFileSystemCounter()
/*  64:    */     {
/*  65: 84 */       return this.key;
/*  66:    */     }
/*  67:    */     
/*  68:    */     public String getName()
/*  69:    */     {
/*  70: 89 */       return FileSystemCounterGroup.NAME_JOINER.join(this.scheme, this.key.name(), new Object[0]);
/*  71:    */     }
/*  72:    */     
/*  73:    */     public String getDisplayName()
/*  74:    */     {
/*  75: 94 */       return FileSystemCounterGroup.DISP_JOINER.join(this.scheme, localizeCounterName(this.key.name()), new Object[0]);
/*  76:    */     }
/*  77:    */     
/*  78:    */     protected String localizeCounterName(String counterName)
/*  79:    */     {
/*  80: 98 */       return ResourceBundles.getCounterName(FileSystemCounter.class.getName(), counterName, counterName);
/*  81:    */     }
/*  82:    */     
/*  83:    */     public long getValue()
/*  84:    */     {
/*  85:104 */       return this.value;
/*  86:    */     }
/*  87:    */     
/*  88:    */     public void setValue(long value)
/*  89:    */     {
/*  90:109 */       this.value = value;
/*  91:    */     }
/*  92:    */     
/*  93:    */     public void increment(long incr)
/*  94:    */     {
/*  95:114 */       this.value += incr;
/*  96:    */     }
/*  97:    */     
/*  98:    */     public void write(DataOutput out)
/*  99:    */       throws IOException
/* 100:    */     {
/* 101:119 */       if (!$assertionsDisabled) {
/* 102:119 */         throw new AssertionError("shouldn't be called");
/* 103:    */       }
/* 104:    */     }
/* 105:    */     
/* 106:    */     public void readFields(DataInput in)
/* 107:    */       throws IOException
/* 108:    */     {
/* 109:124 */       if (!$assertionsDisabled) {
/* 110:124 */         throw new AssertionError("shouldn't be called");
/* 111:    */       }
/* 112:    */     }
/* 113:    */     
/* 114:    */     public Counter getUnderlyingCounter()
/* 115:    */     {
/* 116:129 */       return this;
/* 117:    */     }
/* 118:    */   }
/* 119:    */   
/* 120:    */   public String getName()
/* 121:    */   {
/* 122:135 */     return FileSystemCounter.class.getName();
/* 123:    */   }
/* 124:    */   
/* 125:    */   public String getDisplayName()
/* 126:    */   {
/* 127:140 */     if (this.displayName == null) {
/* 128:141 */       this.displayName = ResourceBundles.getCounterGroupName(getName(), "File System Counters");
/* 129:    */     }
/* 130:144 */     return this.displayName;
/* 131:    */   }
/* 132:    */   
/* 133:    */   public void setDisplayName(String displayName)
/* 134:    */   {
/* 135:149 */     this.displayName = displayName;
/* 136:    */   }
/* 137:    */   
/* 138:    */   public void addCounter(C counter)
/* 139:    */   {
/* 140:    */     C ours;
/* 141:    */     C ours;
/* 142:155 */     if ((counter instanceof FSCounter))
/* 143:    */     {
/* 144:156 */       FSCounter c = (FSCounter)counter;
/* 145:157 */       ours = findCounter(c.scheme, c.key);
/* 146:    */     }
/* 147:    */     else
/* 148:    */     {
/* 149:160 */       ours = findCounter(counter.getName());
/* 150:    */     }
/* 151:162 */     ours.setValue(counter.getValue());
/* 152:    */   }
/* 153:    */   
/* 154:    */   public C addCounter(String name, String displayName, long value)
/* 155:    */   {
/* 156:167 */     C counter = findCounter(name);
/* 157:168 */     counter.setValue(value);
/* 158:169 */     return counter;
/* 159:    */   }
/* 160:    */   
/* 161:    */   private String[] parseCounterName(String counterName)
/* 162:    */   {
/* 163:174 */     int schemeEnd = counterName.indexOf('_');
/* 164:175 */     if (schemeEnd < 0) {
/* 165:176 */       throw new IllegalArgumentException("bad fs counter name");
/* 166:    */     }
/* 167:178 */     return new String[] { counterName.substring(0, schemeEnd), counterName.substring(schemeEnd + 1) };
/* 168:    */   }
/* 169:    */   
/* 170:    */   public C findCounter(String counterName, String displayName)
/* 171:    */   {
/* 172:184 */     return findCounter(counterName);
/* 173:    */   }
/* 174:    */   
/* 175:    */   public C findCounter(String counterName, boolean create)
/* 176:    */   {
/* 177:    */     try
/* 178:    */     {
/* 179:190 */       String[] pair = parseCounterName(counterName);
/* 180:191 */       return findCounter(pair[0], FileSystemCounter.valueOf(pair[1]));
/* 181:    */     }
/* 182:    */     catch (Exception e)
/* 183:    */     {
/* 184:194 */       if (create) {
/* 185:194 */         throw new IllegalArgumentException(e);
/* 186:    */       }
/* 187:    */     }
/* 188:195 */     return null;
/* 189:    */   }
/* 190:    */   
/* 191:    */   public C findCounter(String counterName)
/* 192:    */   {
/* 193:201 */     return findCounter(counterName, true);
/* 194:    */   }
/* 195:    */   
/* 196:    */   public synchronized C findCounter(String scheme, FileSystemCounter key)
/* 197:    */   {
/* 198:206 */     String canonicalScheme = checkScheme(scheme);
/* 199:207 */     Object[] counters = (Object[])this.map.get(canonicalScheme);
/* 200:208 */     int ord = key.ordinal();
/* 201:209 */     if (counters == null)
/* 202:    */     {
/* 203:210 */       counters = new Object[FileSystemCounter.values().length];
/* 204:211 */       this.map.put(canonicalScheme, counters);
/* 205:212 */       counters[ord] = newCounter(canonicalScheme, key);
/* 206:    */     }
/* 207:214 */     else if (counters[ord] == null)
/* 208:    */     {
/* 209:215 */       counters[ord] = newCounter(canonicalScheme, key);
/* 210:    */     }
/* 211:217 */     return (Counter)counters[ord];
/* 212:    */   }
/* 213:    */   
/* 214:    */   private String checkScheme(String scheme)
/* 215:    */   {
/* 216:221 */     String fixed = scheme.toUpperCase(Locale.US);
/* 217:222 */     String interned = (String)schemes.putIfAbsent(fixed, fixed);
/* 218:223 */     if (schemes.size() > 100) {
/* 219:225 */       throw new IllegalArgumentException("too many schemes? " + schemes.size() + " when process scheme: " + scheme);
/* 220:    */     }
/* 221:228 */     return interned == null ? fixed : interned;
/* 222:    */   }
/* 223:    */   
/* 224:    */   protected abstract C newCounter(String paramString, FileSystemCounter paramFileSystemCounter);
/* 225:    */   
/* 226:    */   public int size()
/* 227:    */   {
/* 228:241 */     int n = 0;
/* 229:242 */     for (Object[] counters : this.map.values()) {
/* 230:243 */       n += numSetCounters(counters);
/* 231:    */     }
/* 232:245 */     return n;
/* 233:    */   }
/* 234:    */   
/* 235:    */   public void incrAllCounters(CounterGroupBase<C> other)
/* 236:    */   {
/* 237:251 */     if ((Preconditions.checkNotNull(other.getUnderlyingGroup(), "other group") instanceof FileSystemCounterGroup)) {
/* 238:253 */       for (Counter counter : other)
/* 239:    */       {
/* 240:254 */         FSCounter c = (FSCounter)counter.getUnderlyingCounter();
/* 241:255 */         findCounter(c.scheme, c.key).increment(counter.getValue());
/* 242:    */       }
/* 243:    */     }
/* 244:    */   }
/* 245:    */   
/* 246:    */   public void write(DataOutput out)
/* 247:    */     throws IOException
/* 248:    */   {
/* 249:265 */     WritableUtils.writeVInt(out, this.map.size());
/* 250:266 */     for (Map.Entry<String, Object[]> entry : this.map.entrySet())
/* 251:    */     {
/* 252:267 */       WritableUtils.writeString(out, (String)entry.getKey());
/* 253:    */       
/* 254:269 */       WritableUtils.writeVInt(out, numSetCounters((Object[])entry.getValue()));
/* 255:270 */       for (Object counter : (Object[])entry.getValue()) {
/* 256:271 */         if (counter != null)
/* 257:    */         {
/* 258:273 */           FSCounter c = (FSCounter)((Counter)counter).getUnderlyingCounter();
/* 259:274 */           WritableUtils.writeVInt(out, c.key.ordinal());
/* 260:275 */           WritableUtils.writeVLong(out, c.getValue());
/* 261:    */         }
/* 262:    */       }
/* 263:    */     }
/* 264:    */   }
/* 265:    */   
/* 266:    */   private int numSetCounters(Object[] counters)
/* 267:    */   {
/* 268:281 */     int n = 0;
/* 269:282 */     for (Object counter : counters) {
/* 270:282 */       if (counter != null) {
/* 271:282 */         n++;
/* 272:    */       }
/* 273:    */     }
/* 274:283 */     return n;
/* 275:    */   }
/* 276:    */   
/* 277:    */   public void readFields(DataInput in)
/* 278:    */     throws IOException
/* 279:    */   {
/* 280:288 */     int numSchemes = WritableUtils.readVInt(in);
/* 281:289 */     FileSystemCounter[] enums = FileSystemCounter.values();
/* 282:290 */     for (int i = 0; i < numSchemes; i++)
/* 283:    */     {
/* 284:291 */       String scheme = WritableUtils.readString(in);
/* 285:292 */       int numCounters = WritableUtils.readVInt(in);
/* 286:293 */       for (int j = 0; j < numCounters; j++) {
/* 287:294 */         findCounter(scheme, enums[WritableUtils.readVInt(in)]).setValue(WritableUtils.readVLong(in));
/* 288:    */       }
/* 289:    */     }
/* 290:    */   }
/* 291:    */   
/* 292:    */   public Iterator<C> iterator()
/* 293:    */   {
/* 294:302 */     new AbstractIterator()
/* 295:    */     {
/* 296:303 */       Iterator<Object[]> it = FileSystemCounterGroup.this.map.values().iterator();
/* 297:304 */       Object[] counters = this.it.hasNext() ? (Object[])this.it.next() : null;
/* 298:305 */       int i = 0;
/* 299:    */       
/* 300:    */       protected C computeNext()
/* 301:    */       {
/* 302:308 */         while (this.counters != null)
/* 303:    */         {
/* 304:309 */           while (this.i < this.counters.length)
/* 305:    */           {
/* 306:311 */             C counter = (Counter)this.counters[(this.i++)];
/* 307:312 */             if (counter != null) {
/* 308:312 */               return counter;
/* 309:    */             }
/* 310:    */           }
/* 311:314 */           this.i = 0;
/* 312:315 */           this.counters = (this.it.hasNext() ? (Object[])this.it.next() : null);
/* 313:    */         }
/* 314:317 */         return (Counter)endOfData();
/* 315:    */       }
/* 316:    */     };
/* 317:    */   }
/* 318:    */   
/* 319:    */   public synchronized boolean equals(Object genericRight)
/* 320:    */   {
/* 321:324 */     if ((genericRight instanceof CounterGroupBase))
/* 322:    */     {
/* 323:326 */       CounterGroupBase<C> right = (CounterGroupBase)genericRight;
/* 324:327 */       return Iterators.elementsEqual(iterator(), right.iterator());
/* 325:    */     }
/* 326:329 */     return false;
/* 327:    */   }
/* 328:    */   
/* 329:    */   public synchronized int hashCode()
/* 330:    */   {
/* 331:335 */     int hash = FileSystemCounter.class.hashCode();
/* 332:336 */     for (Object[] counters : this.map.values()) {
/* 333:337 */       if (counters != null) {
/* 334:337 */         hash ^= Arrays.hashCode(counters);
/* 335:    */       }
/* 336:    */     }
/* 337:339 */     return hash;
/* 338:    */   }
/* 339:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.counters.FileSystemCounterGroup
 * JD-Core Version:    0.7.0.1
 */