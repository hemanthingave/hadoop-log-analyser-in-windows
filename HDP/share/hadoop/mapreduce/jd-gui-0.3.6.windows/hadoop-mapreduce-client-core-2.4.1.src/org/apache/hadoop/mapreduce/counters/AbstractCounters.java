/*   1:    */ package org.apache.hadoop.mapreduce.counters;
/*   2:    */ 
/*   3:    */ import com.google.common.collect.Iterables;
/*   4:    */ import com.google.common.collect.Iterators;
/*   5:    */ import com.google.common.collect.Maps;
/*   6:    */ import java.io.DataInput;
/*   7:    */ import java.io.DataOutput;
/*   8:    */ import java.io.IOException;
/*   9:    */ import java.util.Collection;
/*  10:    */ import java.util.HashSet;
/*  11:    */ import java.util.Iterator;
/*  12:    */ import java.util.Map;
/*  13:    */ import java.util.Map.Entry;
/*  14:    */ import java.util.concurrent.ConcurrentSkipListMap;
/*  15:    */ import org.apache.commons.logging.Log;
/*  16:    */ import org.apache.commons.logging.LogFactory;
/*  17:    */ import org.apache.hadoop.classification.InterfaceAudience.Private;
/*  18:    */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*  19:    */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*  20:    */ import org.apache.hadoop.io.Text;
/*  21:    */ import org.apache.hadoop.io.Writable;
/*  22:    */ import org.apache.hadoop.io.WritableUtils;
/*  23:    */ import org.apache.hadoop.mapreduce.Counter;
/*  24:    */ import org.apache.hadoop.mapreduce.FileSystemCounter;
/*  25:    */ import org.apache.hadoop.mapreduce.JobCounter;
/*  26:    */ import org.apache.hadoop.mapreduce.TaskCounter;
/*  27:    */ import org.apache.hadoop.util.StringInterner;
/*  28:    */ 
/*  29:    */ @InterfaceAudience.Public
/*  30:    */ @InterfaceStability.Stable
/*  31:    */ public abstract class AbstractCounters<C extends Counter, G extends CounterGroupBase<C>>
/*  32:    */   implements Writable, Iterable<G>
/*  33:    */ {
/*  34: 62 */   protected static final Log LOG = LogFactory.getLog("mapreduce.Counters");
/*  35: 67 */   private Map<Enum<?>, C> cache = Maps.newIdentityHashMap();
/*  36: 69 */   private Map<String, G> fgroups = new ConcurrentSkipListMap();
/*  37: 71 */   private Map<String, G> groups = new ConcurrentSkipListMap();
/*  38:    */   private final CounterGroupFactory<C, G> groupFactory;
/*  39:    */   
/*  40:    */   static enum GroupType
/*  41:    */   {
/*  42: 75 */     FRAMEWORK,  FILESYSTEM;
/*  43:    */     
/*  44:    */     private GroupType() {}
/*  45:    */   }
/*  46:    */   
/*  47: 78 */   private boolean writeAllCounters = true;
/*  48: 80 */   private static final Map<String, String> legacyMap = Maps.newHashMap();
/*  49:    */   
/*  50:    */   static
/*  51:    */   {
/*  52: 82 */     legacyMap.put("org.apache.hadoop.mapred.Task$Counter", TaskCounter.class.getName());
/*  53:    */     
/*  54: 84 */     legacyMap.put("org.apache.hadoop.mapred.JobInProgress$Counter", JobCounter.class.getName());
/*  55:    */     
/*  56: 86 */     legacyMap.put("FileSystemCounters", FileSystemCounter.class.getName());
/*  57:    */   }
/*  58:    */   
/*  59: 89 */   private final Limits limits = new Limits();
/*  60:    */   
/*  61:    */   @InterfaceAudience.Private
/*  62:    */   public AbstractCounters(CounterGroupFactory<C, G> gf)
/*  63:    */   {
/*  64: 93 */     this.groupFactory = gf;
/*  65:    */   }
/*  66:    */   
/*  67:    */   @InterfaceAudience.Private
/*  68:    */   public <C1 extends Counter, G1 extends CounterGroupBase<C1>> AbstractCounters(AbstractCounters<C1, G1> counters, CounterGroupFactory<C, G> groupFactory)
/*  69:    */   {
/*  70:107 */     this.groupFactory = groupFactory;
/*  71:108 */     for (G1 group : counters)
/*  72:    */     {
/*  73:109 */       String name = group.getName();
/*  74:110 */       newGroup = groupFactory.newGroup(name, group.getDisplayName(), this.limits);
/*  75:111 */       (CounterGroupFactory.isFrameworkGroup(name) ? this.fgroups : this.groups).put(name, newGroup);
/*  76:112 */       for (Counter counter : group) {
/*  77:113 */         newGroup.addCounter(counter.getName(), counter.getDisplayName(), counter.getValue());
/*  78:    */       }
/*  79:    */     }
/*  80:    */     G newGroup;
/*  81:    */   }
/*  82:    */   
/*  83:    */   @InterfaceAudience.Private
/*  84:    */   public synchronized G addGroup(G group)
/*  85:    */   {
/*  86:125 */     String name = group.getName();
/*  87:126 */     if (CounterGroupFactory.isFrameworkGroup(name))
/*  88:    */     {
/*  89:127 */       this.fgroups.put(name, group);
/*  90:    */     }
/*  91:    */     else
/*  92:    */     {
/*  93:129 */       this.limits.checkGroups(this.groups.size() + 1);
/*  94:130 */       this.groups.put(name, group);
/*  95:    */     }
/*  96:132 */     return group;
/*  97:    */   }
/*  98:    */   
/*  99:    */   @InterfaceAudience.Private
/* 100:    */   public G addGroup(String name, String displayName)
/* 101:    */   {
/* 102:143 */     return addGroup(this.groupFactory.newGroup(name, displayName, this.limits));
/* 103:    */   }
/* 104:    */   
/* 105:    */   public C findCounter(String groupName, String counterName)
/* 106:    */   {
/* 107:153 */     G grp = getGroup(groupName);
/* 108:154 */     return grp.findCounter(counterName);
/* 109:    */   }
/* 110:    */   
/* 111:    */   public synchronized C findCounter(Enum<?> key)
/* 112:    */   {
/* 113:164 */     C counter = (Counter)this.cache.get(key);
/* 114:165 */     if (counter == null)
/* 115:    */     {
/* 116:166 */       counter = findCounter(key.getDeclaringClass().getName(), key.name());
/* 117:167 */       this.cache.put(key, counter);
/* 118:    */     }
/* 119:169 */     return counter;
/* 120:    */   }
/* 121:    */   
/* 122:    */   @InterfaceAudience.Private
/* 123:    */   public synchronized C findCounter(String scheme, FileSystemCounter key)
/* 124:    */   {
/* 125:180 */     return ((FileSystemCounterGroup)getGroup(FileSystemCounter.class.getName()).getUnderlyingGroup()).findCounter(scheme, key);
/* 126:    */   }
/* 127:    */   
/* 128:    */   public synchronized Iterable<String> getGroupNames()
/* 129:    */   {
/* 130:190 */     HashSet<String> deprecated = new HashSet();
/* 131:191 */     for (Map.Entry<String, String> entry : legacyMap.entrySet())
/* 132:    */     {
/* 133:192 */       String newGroup = (String)entry.getValue();
/* 134:193 */       boolean isFGroup = CounterGroupFactory.isFrameworkGroup(newGroup);
/* 135:194 */       if (isFGroup ? this.fgroups.containsKey(newGroup) : this.groups.containsKey(newGroup)) {
/* 136:195 */         deprecated.add(entry.getKey());
/* 137:    */       }
/* 138:    */     }
/* 139:198 */     return Iterables.concat(this.fgroups.keySet(), this.groups.keySet(), deprecated);
/* 140:    */   }
/* 141:    */   
/* 142:    */   public Iterator<G> iterator()
/* 143:    */   {
/* 144:203 */     return Iterators.concat(this.fgroups.values().iterator(), this.groups.values().iterator());
/* 145:    */   }
/* 146:    */   
/* 147:    */   public synchronized G getGroup(String groupName)
/* 148:    */   {
/* 149:216 */     boolean groupNameInLegacyMap = true;
/* 150:217 */     String newGroupName = (String)legacyMap.get(groupName);
/* 151:218 */     if (newGroupName == null)
/* 152:    */     {
/* 153:219 */       groupNameInLegacyMap = false;
/* 154:220 */       newGroupName = Limits.filterGroupName(groupName);
/* 155:    */     }
/* 156:223 */     boolean isFGroup = CounterGroupFactory.isFrameworkGroup(newGroupName);
/* 157:224 */     G group = isFGroup ? (CounterGroupBase)this.fgroups.get(newGroupName) : (CounterGroupBase)this.groups.get(newGroupName);
/* 158:225 */     if (group == null)
/* 159:    */     {
/* 160:226 */       group = this.groupFactory.newGroup(newGroupName, this.limits);
/* 161:227 */       if (isFGroup)
/* 162:    */       {
/* 163:228 */         this.fgroups.put(newGroupName, group);
/* 164:    */       }
/* 165:    */       else
/* 166:    */       {
/* 167:230 */         this.limits.checkGroups(this.groups.size() + 1);
/* 168:231 */         this.groups.put(newGroupName, group);
/* 169:    */       }
/* 170:233 */       if (groupNameInLegacyMap) {
/* 171:234 */         LOG.warn("Group " + groupName + " is deprecated. Use " + newGroupName + " instead");
/* 172:    */       }
/* 173:    */     }
/* 174:238 */     return group;
/* 175:    */   }
/* 176:    */   
/* 177:    */   public synchronized int countCounters()
/* 178:    */   {
/* 179:247 */     int result = 0;
/* 180:248 */     for (G group : this) {
/* 181:249 */       result += group.size();
/* 182:    */     }
/* 183:251 */     return result;
/* 184:    */   }
/* 185:    */   
/* 186:    */   public synchronized void write(DataOutput out)
/* 187:    */     throws IOException
/* 188:    */   {
/* 189:260 */     WritableUtils.writeVInt(out, this.groupFactory.version());
/* 190:261 */     WritableUtils.writeVInt(out, this.fgroups.size());
/* 191:262 */     for (G group : this.fgroups.values()) {
/* 192:263 */       if ((group.getUnderlyingGroup() instanceof FrameworkCounterGroup))
/* 193:    */       {
/* 194:264 */         WritableUtils.writeVInt(out, GroupType.FRAMEWORK.ordinal());
/* 195:265 */         WritableUtils.writeVInt(out, CounterGroupFactory.getFrameworkGroupId(group.getName()));
/* 196:266 */         group.write(out);
/* 197:    */       }
/* 198:267 */       else if ((group.getUnderlyingGroup() instanceof FileSystemCounterGroup))
/* 199:    */       {
/* 200:268 */         WritableUtils.writeVInt(out, GroupType.FILESYSTEM.ordinal());
/* 201:269 */         group.write(out);
/* 202:    */       }
/* 203:    */     }
/* 204:272 */     if (this.writeAllCounters)
/* 205:    */     {
/* 206:273 */       WritableUtils.writeVInt(out, this.groups.size());
/* 207:274 */       for (G group : this.groups.values())
/* 208:    */       {
/* 209:275 */         Text.writeString(out, group.getName());
/* 210:276 */         group.write(out);
/* 211:    */       }
/* 212:    */     }
/* 213:    */     else
/* 214:    */     {
/* 215:279 */       WritableUtils.writeVInt(out, 0);
/* 216:    */     }
/* 217:    */   }
/* 218:    */   
/* 219:    */   public synchronized void readFields(DataInput in)
/* 220:    */     throws IOException
/* 221:    */   {
/* 222:285 */     int version = WritableUtils.readVInt(in);
/* 223:286 */     if (version != this.groupFactory.version()) {
/* 224:287 */       throw new IOException("Counters version mismatch, expected " + this.groupFactory.version() + " got " + version);
/* 225:    */     }
/* 226:290 */     int numFGroups = WritableUtils.readVInt(in);
/* 227:291 */     this.fgroups.clear();
/* 228:292 */     GroupType[] groupTypes = GroupType.values();
/* 229:293 */     while (numFGroups-- > 0)
/* 230:    */     {
/* 231:294 */       GroupType groupType = groupTypes[WritableUtils.readVInt(in)];
/* 232:    */       G group;
/* 233:296 */       switch (1.$SwitchMap$org$apache$hadoop$mapreduce$counters$AbstractCounters$GroupType[groupType.ordinal()])
/* 234:    */       {
/* 235:    */       case 1: 
/* 236:298 */         group = this.groupFactory.newFileSystemGroup();
/* 237:299 */         break;
/* 238:    */       case 2: 
/* 239:301 */         group = this.groupFactory.newFrameworkGroup(WritableUtils.readVInt(in));
/* 240:302 */         break;
/* 241:    */       default: 
/* 242:304 */         throw new IOException("Unexpected counter group type: " + groupType);
/* 243:    */       }
/* 244:306 */       group.readFields(in);
/* 245:307 */       this.fgroups.put(group.getName(), group);
/* 246:    */     }
/* 247:309 */     int numGroups = WritableUtils.readVInt(in);
/* 248:310 */     while (numGroups-- > 0)
/* 249:    */     {
/* 250:311 */       this.limits.checkGroups(this.groups.size() + 1);
/* 251:312 */       G group = this.groupFactory.newGenericGroup(StringInterner.weakIntern(Text.readString(in)), null, this.limits);
/* 252:    */       
/* 253:314 */       group.readFields(in);
/* 254:315 */       this.groups.put(group.getName(), group);
/* 255:    */     }
/* 256:    */   }
/* 257:    */   
/* 258:    */   public synchronized String toString()
/* 259:    */   {
/* 260:325 */     StringBuilder sb = new StringBuilder("Counters: " + countCounters());
/* 261:326 */     for (G group : this)
/* 262:    */     {
/* 263:327 */       sb.append("\n\t").append(group.getDisplayName());
/* 264:328 */       for (Counter counter : group) {
/* 265:329 */         sb.append("\n\t\t").append(counter.getDisplayName()).append("=").append(counter.getValue());
/* 266:    */       }
/* 267:    */     }
/* 268:333 */     return sb.toString();
/* 269:    */   }
/* 270:    */   
/* 271:    */   public synchronized void incrAllCounters(AbstractCounters<C, G> other)
/* 272:    */   {
/* 273:342 */     for (G right : other)
/* 274:    */     {
/* 275:343 */       String groupName = right.getName();
/* 276:344 */       G left = (CounterGroupBase)(CounterGroupFactory.isFrameworkGroup(groupName) ? this.fgroups : this.groups).get(groupName);
/* 277:345 */       if (left == null) {
/* 278:346 */         left = addGroup(groupName, right.getDisplayName());
/* 279:    */       }
/* 280:348 */       left.incrAllCounters(right);
/* 281:    */     }
/* 282:    */   }
/* 283:    */   
/* 284:    */   public boolean equals(Object genericRight)
/* 285:    */   {
/* 286:355 */     if ((genericRight instanceof AbstractCounters)) {
/* 287:356 */       return Iterators.elementsEqual(iterator(), ((AbstractCounters)genericRight).iterator());
/* 288:    */     }
/* 289:359 */     return false;
/* 290:    */   }
/* 291:    */   
/* 292:    */   public int hashCode()
/* 293:    */   {
/* 294:364 */     return this.groups.hashCode();
/* 295:    */   }
/* 296:    */   
/* 297:    */   @InterfaceAudience.Private
/* 298:    */   public void setWriteAllCounters(boolean send)
/* 299:    */   {
/* 300:375 */     this.writeAllCounters = send;
/* 301:    */   }
/* 302:    */   
/* 303:    */   @InterfaceAudience.Private
/* 304:    */   public boolean getWriteAllCounters()
/* 305:    */   {
/* 306:384 */     return this.writeAllCounters;
/* 307:    */   }
/* 308:    */   
/* 309:    */   @InterfaceAudience.Private
/* 310:    */   public Limits limits()
/* 311:    */   {
/* 312:389 */     return this.limits;
/* 313:    */   }
/* 314:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.counters.AbstractCounters
 * JD-Core Version:    0.7.0.1
 */