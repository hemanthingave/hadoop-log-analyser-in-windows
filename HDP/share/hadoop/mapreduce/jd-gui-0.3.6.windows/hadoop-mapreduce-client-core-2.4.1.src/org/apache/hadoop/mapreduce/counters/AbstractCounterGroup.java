/*   1:    */ package org.apache.hadoop.mapreduce.counters;
/*   2:    */ 
/*   3:    */ import com.google.common.collect.Iterators;
/*   4:    */ import java.io.DataInput;
/*   5:    */ import java.io.DataOutput;
/*   6:    */ import java.io.IOException;
/*   7:    */ import java.util.Collection;
/*   8:    */ import java.util.Iterator;
/*   9:    */ import java.util.concurrent.ConcurrentMap;
/*  10:    */ import java.util.concurrent.ConcurrentSkipListMap;
/*  11:    */ import org.apache.hadoop.classification.InterfaceAudience.Private;
/*  12:    */ import org.apache.hadoop.io.Text;
/*  13:    */ import org.apache.hadoop.io.WritableUtils;
/*  14:    */ import org.apache.hadoop.mapreduce.Counter;
/*  15:    */ import org.apache.hadoop.mapreduce.util.ResourceBundles;
/*  16:    */ import org.apache.hadoop.util.StringInterner;
/*  17:    */ 
/*  18:    */ @InterfaceAudience.Private
/*  19:    */ public abstract class AbstractCounterGroup<T extends Counter>
/*  20:    */   implements CounterGroupBase<T>
/*  21:    */ {
/*  22:    */   private final String name;
/*  23:    */   private String displayName;
/*  24: 49 */   private final ConcurrentMap<String, T> counters = new ConcurrentSkipListMap();
/*  25:    */   private final Limits limits;
/*  26:    */   
/*  27:    */   public AbstractCounterGroup(String name, String displayName, Limits limits)
/*  28:    */   {
/*  29: 55 */     this.name = name;
/*  30: 56 */     this.displayName = displayName;
/*  31: 57 */     this.limits = limits;
/*  32:    */   }
/*  33:    */   
/*  34:    */   public String getName()
/*  35:    */   {
/*  36: 62 */     return this.name;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public synchronized String getDisplayName()
/*  40:    */   {
/*  41: 67 */     return this.displayName;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public synchronized void setDisplayName(String displayName)
/*  45:    */   {
/*  46: 72 */     this.displayName = displayName;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public synchronized void addCounter(T counter)
/*  50:    */   {
/*  51: 77 */     this.counters.put(counter.getName(), counter);
/*  52: 78 */     this.limits.incrCounters();
/*  53:    */   }
/*  54:    */   
/*  55:    */   public synchronized T addCounter(String counterName, String displayName, long value)
/*  56:    */   {
/*  57: 84 */     String saveName = Limits.filterCounterName(counterName);
/*  58: 85 */     T counter = findCounterImpl(saveName, false);
/*  59: 86 */     if (counter == null) {
/*  60: 87 */       return addCounterImpl(saveName, displayName, value);
/*  61:    */     }
/*  62: 89 */     counter.setValue(value);
/*  63: 90 */     return counter;
/*  64:    */   }
/*  65:    */   
/*  66:    */   private T addCounterImpl(String name, String displayName, long value)
/*  67:    */   {
/*  68: 94 */     T counter = newCounter(name, displayName, value);
/*  69: 95 */     addCounter(counter);
/*  70: 96 */     return counter;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public synchronized T findCounter(String counterName, String displayName)
/*  74:    */   {
/*  75:103 */     String saveName = Limits.filterCounterName(counterName);
/*  76:104 */     T counter = findCounterImpl(saveName, false);
/*  77:105 */     if (counter == null) {
/*  78:106 */       return addCounterImpl(saveName, displayName, 0L);
/*  79:    */     }
/*  80:108 */     return counter;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public T findCounter(String counterName, boolean create)
/*  84:    */   {
/*  85:113 */     return findCounterImpl(Limits.filterCounterName(counterName), create);
/*  86:    */   }
/*  87:    */   
/*  88:    */   private synchronized T findCounterImpl(String counterName, boolean create)
/*  89:    */   {
/*  90:119 */     T counter = (Counter)this.counters.get(counterName);
/*  91:120 */     if ((counter == null) && (create))
/*  92:    */     {
/*  93:121 */       String localized = ResourceBundles.getCounterName(getName(), counterName, counterName);
/*  94:    */       
/*  95:123 */       return addCounterImpl(counterName, localized, 0L);
/*  96:    */     }
/*  97:125 */     return counter;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public T findCounter(String counterName)
/* 101:    */   {
/* 102:130 */     return findCounter(counterName, true);
/* 103:    */   }
/* 104:    */   
/* 105:    */   protected abstract T newCounter(String paramString1, String paramString2, long paramLong);
/* 106:    */   
/* 107:    */   protected abstract T newCounter();
/* 108:    */   
/* 109:    */   public Iterator<T> iterator()
/* 110:    */   {
/* 111:151 */     return this.counters.values().iterator();
/* 112:    */   }
/* 113:    */   
/* 114:    */   public synchronized void write(DataOutput out)
/* 115:    */     throws IOException
/* 116:    */   {
/* 117:159 */     Text.writeString(out, this.displayName);
/* 118:160 */     WritableUtils.writeVInt(out, this.counters.size());
/* 119:161 */     for (Counter counter : this.counters.values()) {
/* 120:162 */       counter.write(out);
/* 121:    */     }
/* 122:    */   }
/* 123:    */   
/* 124:    */   public synchronized void readFields(DataInput in)
/* 125:    */     throws IOException
/* 126:    */   {
/* 127:168 */     this.displayName = StringInterner.weakIntern(Text.readString(in));
/* 128:169 */     this.counters.clear();
/* 129:170 */     int size = WritableUtils.readVInt(in);
/* 130:171 */     for (int i = 0; i < size; i++)
/* 131:    */     {
/* 132:172 */       T counter = newCounter();
/* 133:173 */       counter.readFields(in);
/* 134:174 */       this.counters.put(counter.getName(), counter);
/* 135:175 */       this.limits.incrCounters();
/* 136:    */     }
/* 137:    */   }
/* 138:    */   
/* 139:    */   public synchronized int size()
/* 140:    */   {
/* 141:181 */     return this.counters.size();
/* 142:    */   }
/* 143:    */   
/* 144:    */   public synchronized boolean equals(Object genericRight)
/* 145:    */   {
/* 146:186 */     if ((genericRight instanceof CounterGroupBase))
/* 147:    */     {
/* 148:188 */       CounterGroupBase<T> right = (CounterGroupBase)genericRight;
/* 149:189 */       return Iterators.elementsEqual(iterator(), right.iterator());
/* 150:    */     }
/* 151:191 */     return false;
/* 152:    */   }
/* 153:    */   
/* 154:    */   public synchronized int hashCode()
/* 155:    */   {
/* 156:196 */     return this.counters.hashCode();
/* 157:    */   }
/* 158:    */   
/* 159:    */   public void incrAllCounters(CounterGroupBase<T> rightGroup)
/* 160:    */   {
/* 161:    */     try
/* 162:    */     {
/* 163:202 */       for (Counter right : rightGroup)
/* 164:    */       {
/* 165:203 */         Counter left = findCounter(right.getName(), right.getDisplayName());
/* 166:204 */         left.increment(right.getValue());
/* 167:    */       }
/* 168:    */     }
/* 169:    */     catch (LimitExceededException e)
/* 170:    */     {
/* 171:207 */       this.counters.clear();
/* 172:208 */       throw e;
/* 173:    */     }
/* 174:    */   }
/* 175:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.counters.AbstractCounterGroup
 * JD-Core Version:    0.7.0.1
 */