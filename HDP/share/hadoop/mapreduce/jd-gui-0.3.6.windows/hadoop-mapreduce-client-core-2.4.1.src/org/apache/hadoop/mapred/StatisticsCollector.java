/*   1:    */ package org.apache.hadoop.mapred;
/*   2:    */ 
/*   3:    */ import java.util.Collections;
/*   4:    */ import java.util.HashMap;
/*   5:    */ import java.util.LinkedHashMap;
/*   6:    */ import java.util.LinkedList;
/*   7:    */ import java.util.Map;
/*   8:    */ import java.util.Timer;
/*   9:    */ import java.util.TimerTask;
/*  10:    */ 
/*  11:    */ class StatisticsCollector
/*  12:    */ {
/*  13:    */   private static final int DEFAULT_PERIOD = 5;
/*  14: 38 */   static final TimeWindow SINCE_START = new TimeWindow("Since Start", -1, -1);
/*  15: 41 */   static final TimeWindow LAST_WEEK = new TimeWindow("Last Week", 604800, 3600);
/*  16: 44 */   static final TimeWindow LAST_DAY = new TimeWindow("Last Day", 86400, 3600);
/*  17: 47 */   static final TimeWindow LAST_HOUR = new TimeWindow("Last Hour", 3600, 60);
/*  18: 50 */   static final TimeWindow LAST_MINUTE = new TimeWindow("Last Minute", 60, 10);
/*  19: 52 */   static final TimeWindow[] DEFAULT_COLLECT_WINDOWS = { SINCE_START, LAST_DAY, LAST_HOUR };
/*  20:    */   private final int period;
/*  21:    */   private boolean started;
/*  22: 61 */   private final Map<TimeWindow, StatUpdater> updaters = new LinkedHashMap();
/*  23: 63 */   private final Map<String, Stat> statistics = new HashMap();
/*  24:    */   
/*  25:    */   StatisticsCollector()
/*  26:    */   {
/*  27: 66 */     this(5);
/*  28:    */   }
/*  29:    */   
/*  30:    */   StatisticsCollector(int period)
/*  31:    */   {
/*  32: 70 */     this.period = period;
/*  33:    */   }
/*  34:    */   
/*  35:    */   synchronized void start()
/*  36:    */   {
/*  37: 74 */     if (this.started) {
/*  38: 75 */       return;
/*  39:    */     }
/*  40: 77 */     Timer timer = new Timer("Timer thread for monitoring ", true);
/*  41: 78 */     TimerTask task = new TimerTask()
/*  42:    */     {
/*  43:    */       public void run()
/*  44:    */       {
/*  45: 80 */         StatisticsCollector.this.update();
/*  46:    */       }
/*  47: 82 */     };
/*  48: 83 */     long millis = this.period * 1000;
/*  49: 84 */     timer.scheduleAtFixedRate(task, millis, millis);
/*  50: 85 */     this.started = true;
/*  51:    */   }
/*  52:    */   
/*  53:    */   protected synchronized void update()
/*  54:    */   {
/*  55: 89 */     for (StatUpdater c : this.updaters.values()) {
/*  56: 90 */       c.update();
/*  57:    */     }
/*  58:    */   }
/*  59:    */   
/*  60:    */   Map<TimeWindow, StatUpdater> getUpdaters()
/*  61:    */   {
/*  62: 95 */     return Collections.unmodifiableMap(this.updaters);
/*  63:    */   }
/*  64:    */   
/*  65:    */   Map<String, Stat> getStatistics()
/*  66:    */   {
/*  67: 99 */     return Collections.unmodifiableMap(this.statistics);
/*  68:    */   }
/*  69:    */   
/*  70:    */   synchronized Stat createStat(String name)
/*  71:    */   {
/*  72:103 */     return createStat(name, DEFAULT_COLLECT_WINDOWS);
/*  73:    */   }
/*  74:    */   
/*  75:    */   synchronized Stat createStat(String name, TimeWindow[] windows)
/*  76:    */   {
/*  77:107 */     if (this.statistics.get(name) != null) {
/*  78:108 */       throw new RuntimeException("Stat with name " + name + " is already defined");
/*  79:    */     }
/*  80:111 */     Map<TimeWindow, StatisticsCollector.Stat.TimeStat> timeStats = new LinkedHashMap();
/*  81:113 */     for (TimeWindow window : windows)
/*  82:    */     {
/*  83:114 */       StatUpdater collector = (StatUpdater)this.updaters.get(window);
/*  84:115 */       if (collector == null)
/*  85:    */       {
/*  86:116 */         if (SINCE_START.equals(window)) {
/*  87:117 */           collector = new StatUpdater(null);
/*  88:    */         } else {
/*  89:119 */           collector = new TimeWindowStatUpdater(window, this.period);
/*  90:    */         }
/*  91:121 */         this.updaters.put(window, collector);
/*  92:    */       }
/*  93:123 */       StatisticsCollector.Stat.TimeStat timeStat = new StatisticsCollector.Stat.TimeStat();
/*  94:124 */       collector.addTimeStat(name, timeStat);
/*  95:125 */       timeStats.put(window, timeStat);
/*  96:    */     }
/*  97:128 */     Stat stat = new Stat(name, timeStats, null);
/*  98:129 */     this.statistics.put(name, stat);
/*  99:130 */     return stat;
/* 100:    */   }
/* 101:    */   
/* 102:    */   synchronized Stat removeStat(String name)
/* 103:    */   {
/* 104:134 */     Stat stat = (Stat)this.statistics.remove(name);
/* 105:135 */     if (stat != null) {
/* 106:136 */       for (StatUpdater collector : this.updaters.values()) {
/* 107:137 */         collector.removeTimeStat(name);
/* 108:    */       }
/* 109:    */     }
/* 110:140 */     return stat;
/* 111:    */   }
/* 112:    */   
/* 113:    */   static class TimeWindow
/* 114:    */   {
/* 115:    */     final String name;
/* 116:    */     final int windowSize;
/* 117:    */     final int updateGranularity;
/* 118:    */     
/* 119:    */     TimeWindow(String name, int windowSize, int updateGranularity)
/* 120:    */     {
/* 121:148 */       if (updateGranularity > windowSize) {
/* 122:149 */         throw new RuntimeException("Invalid TimeWindow: updateGranularity > windowSize");
/* 123:    */       }
/* 124:152 */       this.name = name;
/* 125:153 */       this.windowSize = windowSize;
/* 126:154 */       this.updateGranularity = updateGranularity;
/* 127:    */     }
/* 128:    */     
/* 129:    */     public int hashCode()
/* 130:    */     {
/* 131:158 */       return this.name.hashCode() + this.updateGranularity + this.windowSize;
/* 132:    */     }
/* 133:    */     
/* 134:    */     public boolean equals(Object obj)
/* 135:    */     {
/* 136:162 */       if (this == obj) {
/* 137:163 */         return true;
/* 138:    */       }
/* 139:164 */       if (obj == null) {
/* 140:165 */         return false;
/* 141:    */       }
/* 142:166 */       if (getClass() != obj.getClass()) {
/* 143:167 */         return false;
/* 144:    */       }
/* 145:168 */       TimeWindow other = (TimeWindow)obj;
/* 146:169 */       if (this.name == null)
/* 147:    */       {
/* 148:170 */         if (other.name != null) {
/* 149:171 */           return false;
/* 150:    */         }
/* 151:    */       }
/* 152:172 */       else if (!this.name.equals(other.name)) {
/* 153:173 */         return false;
/* 154:    */       }
/* 155:174 */       if (this.updateGranularity != other.updateGranularity) {
/* 156:175 */         return false;
/* 157:    */       }
/* 158:176 */       if (this.windowSize != other.windowSize) {
/* 159:177 */         return false;
/* 160:    */       }
/* 161:178 */       return true;
/* 162:    */     }
/* 163:    */   }
/* 164:    */   
/* 165:    */   static class Stat
/* 166:    */   {
/* 167:    */     final String name;
/* 168:    */     private Map<StatisticsCollector.TimeWindow, TimeStat> timeStats;
/* 169:    */     
/* 170:    */     private Stat(String name, Map<StatisticsCollector.TimeWindow, TimeStat> timeStats)
/* 171:    */     {
/* 172:187 */       this.name = name;
/* 173:188 */       this.timeStats = timeStats;
/* 174:    */     }
/* 175:    */     
/* 176:    */     public synchronized void inc(int incr)
/* 177:    */     {
/* 178:192 */       for (TimeStat ts : this.timeStats.values()) {
/* 179:193 */         ts.inc(incr);
/* 180:    */       }
/* 181:    */     }
/* 182:    */     
/* 183:    */     public synchronized void inc()
/* 184:    */     {
/* 185:198 */       inc(1);
/* 186:    */     }
/* 187:    */     
/* 188:    */     public synchronized Map<StatisticsCollector.TimeWindow, TimeStat> getValues()
/* 189:    */     {
/* 190:202 */       return Collections.unmodifiableMap(this.timeStats);
/* 191:    */     }
/* 192:    */     
/* 193:    */     static class TimeStat
/* 194:    */     {
/* 195:206 */       private final LinkedList<Integer> buckets = new LinkedList();
/* 196:    */       private int value;
/* 197:    */       private int currentValue;
/* 198:    */       
/* 199:    */       public synchronized int getValue()
/* 200:    */       {
/* 201:211 */         return this.value;
/* 202:    */       }
/* 203:    */       
/* 204:    */       private synchronized void inc(int i)
/* 205:    */       {
/* 206:215 */         this.currentValue += i;
/* 207:    */       }
/* 208:    */       
/* 209:    */       private synchronized void addBucket()
/* 210:    */       {
/* 211:219 */         this.buckets.addLast(Integer.valueOf(this.currentValue));
/* 212:220 */         setValueToCurrent();
/* 213:    */       }
/* 214:    */       
/* 215:    */       private synchronized void setValueToCurrent()
/* 216:    */       {
/* 217:224 */         this.value += this.currentValue;
/* 218:225 */         this.currentValue = 0;
/* 219:    */       }
/* 220:    */       
/* 221:    */       private synchronized void removeBucket()
/* 222:    */       {
/* 223:229 */         int removed = ((Integer)this.buckets.removeFirst()).intValue();
/* 224:230 */         this.value -= removed;
/* 225:    */       }
/* 226:    */     }
/* 227:    */   }
/* 228:    */   
/* 229:    */   private static class StatUpdater
/* 230:    */   {
/* 231:237 */     protected final Map<String, StatisticsCollector.Stat.TimeStat> statToCollect = new HashMap();
/* 232:    */     
/* 233:    */     synchronized void addTimeStat(String name, StatisticsCollector.Stat.TimeStat s)
/* 234:    */     {
/* 235:241 */       this.statToCollect.put(name, s);
/* 236:    */     }
/* 237:    */     
/* 238:    */     synchronized StatisticsCollector.Stat.TimeStat removeTimeStat(String name)
/* 239:    */     {
/* 240:245 */       return (StatisticsCollector.Stat.TimeStat)this.statToCollect.remove(name);
/* 241:    */     }
/* 242:    */     
/* 243:    */     synchronized void update()
/* 244:    */     {
/* 245:249 */       for (StatisticsCollector.Stat.TimeStat stat : this.statToCollect.values()) {
/* 246:250 */         StatisticsCollector.Stat.TimeStat.access$300(stat);
/* 247:    */       }
/* 248:    */     }
/* 249:    */   }
/* 250:    */   
/* 251:    */   private static class TimeWindowStatUpdater
/* 252:    */     extends StatisticsCollector.StatUpdater
/* 253:    */   {
/* 254:    */     final int collectBuckets;
/* 255:    */     final int updatesPerBucket;
/* 256:    */     private int updates;
/* 257:    */     private int buckets;
/* 258:    */     
/* 259:    */     TimeWindowStatUpdater(StatisticsCollector.TimeWindow w, int updatePeriod)
/* 260:    */     {
/* 261:267 */       super();
/* 262:268 */       if (updatePeriod > w.updateGranularity) {
/* 263:269 */         throw new RuntimeException("Invalid conf: updatePeriod > updateGranularity");
/* 264:    */       }
/* 265:272 */       this.collectBuckets = (w.windowSize / w.updateGranularity);
/* 266:273 */       this.updatesPerBucket = (w.updateGranularity / updatePeriod);
/* 267:    */     }
/* 268:    */     
/* 269:    */     synchronized void update()
/* 270:    */     {
/* 271:277 */       this.updates += 1;
/* 272:278 */       if (this.updates == this.updatesPerBucket)
/* 273:    */       {
/* 274:279 */         for (StatisticsCollector.Stat.TimeStat stat : this.statToCollect.values()) {
/* 275:280 */           StatisticsCollector.Stat.TimeStat.access$400(stat);
/* 276:    */         }
/* 277:282 */         this.updates = 0;
/* 278:283 */         this.buckets += 1;
/* 279:284 */         if (this.buckets > this.collectBuckets)
/* 280:    */         {
/* 281:285 */           for (StatisticsCollector.Stat.TimeStat stat : this.statToCollect.values()) {
/* 282:286 */             StatisticsCollector.Stat.TimeStat.access$500(stat);
/* 283:    */           }
/* 284:288 */           this.buckets -= 1;
/* 285:    */         }
/* 286:    */       }
/* 287:    */     }
/* 288:    */   }
/* 289:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.StatisticsCollector
 * JD-Core Version:    0.7.0.1
 */