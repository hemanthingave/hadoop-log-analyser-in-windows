/*   1:    */ package org.apache.hadoop.mapreduce.counters;
/*   2:    */ 
/*   3:    */ import com.google.common.base.Preconditions;
/*   4:    */ import com.google.common.collect.AbstractIterator;
/*   5:    */ import com.google.common.collect.Iterators;
/*   6:    */ import java.io.DataInput;
/*   7:    */ import java.io.DataOutput;
/*   8:    */ import java.io.IOException;
/*   9:    */ import java.util.Arrays;
/*  10:    */ import java.util.Iterator;
/*  11:    */ import org.apache.commons.logging.Log;
/*  12:    */ import org.apache.commons.logging.LogFactory;
/*  13:    */ import org.apache.hadoop.classification.InterfaceAudience.Private;
/*  14:    */ import org.apache.hadoop.io.WritableUtils;
/*  15:    */ import org.apache.hadoop.mapreduce.Counter;
/*  16:    */ import org.apache.hadoop.mapreduce.util.ResourceBundles;
/*  17:    */ 
/*  18:    */ @InterfaceAudience.Private
/*  19:    */ public abstract class FrameworkCounterGroup<T extends Enum<T>, C extends Counter>
/*  20:    */   implements CounterGroupBase<C>
/*  21:    */ {
/*  22: 50 */   private static final Log LOG = LogFactory.getLog(FrameworkCounterGroup.class);
/*  23:    */   private final Class<T> enumClass;
/*  24:    */   private final Object[] counters;
/*  25: 54 */   private String displayName = null;
/*  26:    */   
/*  27:    */   @InterfaceAudience.Private
/*  28:    */   public static class FrameworkCounter<T extends Enum<T>>
/*  29:    */     extends AbstractCounter
/*  30:    */   {
/*  31:    */     final T key;
/*  32:    */     final String groupName;
/*  33:    */     private long value;
/*  34:    */     
/*  35:    */     public FrameworkCounter(T ref, String groupName)
/*  36:    */     {
/*  37: 67 */       this.key = ref;
/*  38: 68 */       this.groupName = groupName;
/*  39:    */     }
/*  40:    */     
/*  41:    */     @InterfaceAudience.Private
/*  42:    */     public T getKey()
/*  43:    */     {
/*  44: 73 */       return this.key;
/*  45:    */     }
/*  46:    */     
/*  47:    */     @InterfaceAudience.Private
/*  48:    */     public String getGroupName()
/*  49:    */     {
/*  50: 78 */       return this.groupName;
/*  51:    */     }
/*  52:    */     
/*  53:    */     public String getName()
/*  54:    */     {
/*  55: 83 */       return this.key.name();
/*  56:    */     }
/*  57:    */     
/*  58:    */     public String getDisplayName()
/*  59:    */     {
/*  60: 88 */       return ResourceBundles.getCounterName(this.groupName, getName(), getName());
/*  61:    */     }
/*  62:    */     
/*  63:    */     public long getValue()
/*  64:    */     {
/*  65: 93 */       return this.value;
/*  66:    */     }
/*  67:    */     
/*  68:    */     public void setValue(long value)
/*  69:    */     {
/*  70: 98 */       this.value = value;
/*  71:    */     }
/*  72:    */     
/*  73:    */     public void increment(long incr)
/*  74:    */     {
/*  75:103 */       this.value += incr;
/*  76:    */     }
/*  77:    */     
/*  78:    */     public void write(DataOutput out)
/*  79:    */       throws IOException
/*  80:    */     {
/*  81:108 */       if (!$assertionsDisabled) {
/*  82:108 */         throw new AssertionError("shouldn't be called");
/*  83:    */       }
/*  84:    */     }
/*  85:    */     
/*  86:    */     public void readFields(DataInput in)
/*  87:    */       throws IOException
/*  88:    */     {
/*  89:113 */       if (!$assertionsDisabled) {
/*  90:113 */         throw new AssertionError("shouldn't be called");
/*  91:    */       }
/*  92:    */     }
/*  93:    */     
/*  94:    */     public Counter getUnderlyingCounter()
/*  95:    */     {
/*  96:118 */       return this;
/*  97:    */     }
/*  98:    */   }
/*  99:    */   
/* 100:    */   public FrameworkCounterGroup(Class<T> enumClass)
/* 101:    */   {
/* 102:124 */     this.enumClass = enumClass;
/* 103:125 */     T[] enums = (Enum[])enumClass.getEnumConstants();
/* 104:126 */     this.counters = new Object[enums.length];
/* 105:    */   }
/* 106:    */   
/* 107:    */   public String getName()
/* 108:    */   {
/* 109:131 */     return this.enumClass.getName();
/* 110:    */   }
/* 111:    */   
/* 112:    */   public String getDisplayName()
/* 113:    */   {
/* 114:136 */     if (this.displayName == null) {
/* 115:137 */       this.displayName = ResourceBundles.getCounterGroupName(getName(), getName());
/* 116:    */     }
/* 117:139 */     return this.displayName;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public void setDisplayName(String displayName)
/* 121:    */   {
/* 122:144 */     this.displayName = displayName;
/* 123:    */   }
/* 124:    */   
/* 125:    */   private T valueOf(String name)
/* 126:    */   {
/* 127:148 */     return Enum.valueOf(this.enumClass, name);
/* 128:    */   }
/* 129:    */   
/* 130:    */   public void addCounter(C counter)
/* 131:    */   {
/* 132:153 */     C ours = findCounter(counter.getName());
/* 133:154 */     ours.setValue(counter.getValue());
/* 134:    */   }
/* 135:    */   
/* 136:    */   public C addCounter(String name, String displayName, long value)
/* 137:    */   {
/* 138:159 */     C counter = findCounter(name);
/* 139:160 */     counter.setValue(value);
/* 140:161 */     return counter;
/* 141:    */   }
/* 142:    */   
/* 143:    */   public C findCounter(String counterName, String displayName)
/* 144:    */   {
/* 145:166 */     return findCounter(counterName);
/* 146:    */   }
/* 147:    */   
/* 148:    */   public C findCounter(String counterName, boolean create)
/* 149:    */   {
/* 150:    */     try
/* 151:    */     {
/* 152:172 */       return findCounter(valueOf(counterName));
/* 153:    */     }
/* 154:    */     catch (Exception e)
/* 155:    */     {
/* 156:175 */       if (create) {
/* 157:175 */         throw new IllegalArgumentException(e);
/* 158:    */       }
/* 159:    */     }
/* 160:176 */     return null;
/* 161:    */   }
/* 162:    */   
/* 163:    */   public C findCounter(String counterName)
/* 164:    */   {
/* 165:182 */     return findCounter(valueOf(counterName));
/* 166:    */   }
/* 167:    */   
/* 168:    */   private C findCounter(T key)
/* 169:    */   {
/* 170:187 */     int i = key.ordinal();
/* 171:188 */     if (this.counters[i] == null) {
/* 172:189 */       this.counters[i] = newCounter(key);
/* 173:    */     }
/* 174:191 */     return (Counter)this.counters[i];
/* 175:    */   }
/* 176:    */   
/* 177:    */   protected abstract C newCounter(T paramT);
/* 178:    */   
/* 179:    */   public int size()
/* 180:    */   {
/* 181:203 */     int n = 0;
/* 182:204 */     for (int i = 0; i < this.counters.length; i++) {
/* 183:205 */       if (this.counters[i] != null) {
/* 184:205 */         n++;
/* 185:    */       }
/* 186:    */     }
/* 187:207 */     return n;
/* 188:    */   }
/* 189:    */   
/* 190:    */   public void incrAllCounters(CounterGroupBase<C> other)
/* 191:    */   {
/* 192:213 */     if ((Preconditions.checkNotNull(other, "other counter group") instanceof FrameworkCounterGroup)) {
/* 193:215 */       for (Counter counter : other) {
/* 194:216 */         findCounter(((FrameworkCounter)counter).key.name()).increment(counter.getValue());
/* 195:    */       }
/* 196:    */     }
/* 197:    */   }
/* 198:    */   
/* 199:    */   public void write(DataOutput out)
/* 200:    */     throws IOException
/* 201:    */   {
/* 202:228 */     WritableUtils.writeVInt(out, size());
/* 203:229 */     for (int i = 0; i < this.counters.length; i++)
/* 204:    */     {
/* 205:230 */       Counter counter = (Counter)this.counters[i];
/* 206:231 */       if (counter != null)
/* 207:    */       {
/* 208:232 */         WritableUtils.writeVInt(out, i);
/* 209:233 */         WritableUtils.writeVLong(out, counter.getValue());
/* 210:    */       }
/* 211:    */     }
/* 212:    */   }
/* 213:    */   
/* 214:    */   public void readFields(DataInput in)
/* 215:    */     throws IOException
/* 216:    */   {
/* 217:240 */     clear();
/* 218:241 */     int len = WritableUtils.readVInt(in);
/* 219:242 */     T[] enums = (Enum[])this.enumClass.getEnumConstants();
/* 220:243 */     for (int i = 0; i < len; i++)
/* 221:    */     {
/* 222:244 */       int ord = WritableUtils.readVInt(in);
/* 223:245 */       Counter counter = newCounter(enums[ord]);
/* 224:246 */       counter.setValue(WritableUtils.readVLong(in));
/* 225:247 */       this.counters[ord] = counter;
/* 226:    */     }
/* 227:    */   }
/* 228:    */   
/* 229:    */   private void clear()
/* 230:    */   {
/* 231:252 */     for (int i = 0; i < this.counters.length; i++) {
/* 232:253 */       this.counters[i] = null;
/* 233:    */     }
/* 234:    */   }
/* 235:    */   
/* 236:    */   public Iterator<C> iterator()
/* 237:    */   {
/* 238:259 */     new AbstractIterator()
/* 239:    */     {
/* 240:260 */       int i = 0;
/* 241:    */       
/* 242:    */       protected C computeNext()
/* 243:    */       {
/* 244:263 */         while (this.i < FrameworkCounterGroup.this.counters.length)
/* 245:    */         {
/* 246:265 */           C counter = (Counter)FrameworkCounterGroup.this.counters[(this.i++)];
/* 247:266 */           if (counter != null) {
/* 248:266 */             return counter;
/* 249:    */           }
/* 250:    */         }
/* 251:268 */         return (Counter)endOfData();
/* 252:    */       }
/* 253:    */     };
/* 254:    */   }
/* 255:    */   
/* 256:    */   public boolean equals(Object genericRight)
/* 257:    */   {
/* 258:275 */     if ((genericRight instanceof CounterGroupBase))
/* 259:    */     {
/* 260:277 */       CounterGroupBase<C> right = (CounterGroupBase)genericRight;
/* 261:278 */       return Iterators.elementsEqual(iterator(), right.iterator());
/* 262:    */     }
/* 263:280 */     return false;
/* 264:    */   }
/* 265:    */   
/* 266:    */   public synchronized int hashCode()
/* 267:    */   {
/* 268:286 */     return Arrays.deepHashCode(new Object[] { this.enumClass, this.counters, this.displayName });
/* 269:    */   }
/* 270:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.counters.FrameworkCounterGroup
 * JD-Core Version:    0.7.0.1
 */