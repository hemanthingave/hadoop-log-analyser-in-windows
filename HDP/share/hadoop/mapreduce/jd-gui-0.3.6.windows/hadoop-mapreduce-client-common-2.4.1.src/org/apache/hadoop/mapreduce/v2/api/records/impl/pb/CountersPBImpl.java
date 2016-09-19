/*   1:    */ package org.apache.hadoop.mapreduce.v2.api.records.impl.pb;
/*   2:    */ 
/*   3:    */ import java.util.HashMap;
/*   4:    */ import java.util.Iterator;
/*   5:    */ import java.util.List;
/*   6:    */ import java.util.Map;
/*   7:    */ import java.util.Set;
/*   8:    */ import org.apache.hadoop.mapreduce.v2.api.records.Counter;
/*   9:    */ import org.apache.hadoop.mapreduce.v2.api.records.CounterGroup;
/*  10:    */ import org.apache.hadoop.mapreduce.v2.api.records.Counters;
/*  11:    */ import org.apache.hadoop.mapreduce.v2.proto.MRProtos.CounterGroupProto;
/*  12:    */ import org.apache.hadoop.mapreduce.v2.proto.MRProtos.CountersProto;
/*  13:    */ import org.apache.hadoop.mapreduce.v2.proto.MRProtos.CountersProto.Builder;
/*  14:    */ import org.apache.hadoop.mapreduce.v2.proto.MRProtos.CountersProtoOrBuilder;
/*  15:    */ import org.apache.hadoop.mapreduce.v2.proto.MRProtos.StringCounterGroupMapProto;
/*  16:    */ import org.apache.hadoop.mapreduce.v2.proto.MRProtos.StringCounterGroupMapProto.Builder;
/*  17:    */ import org.apache.hadoop.yarn.api.records.impl.pb.ProtoBase;
/*  18:    */ 
/*  19:    */ public class CountersPBImpl
/*  20:    */   extends ProtoBase<MRProtos.CountersProto>
/*  21:    */   implements Counters
/*  22:    */ {
/*  23: 39 */   MRProtos.CountersProto proto = MRProtos.CountersProto.getDefaultInstance();
/*  24: 40 */   MRProtos.CountersProto.Builder builder = null;
/*  25: 41 */   boolean viaProto = false;
/*  26: 43 */   private Map<String, CounterGroup> counterGroups = null;
/*  27:    */   
/*  28:    */   public CountersPBImpl()
/*  29:    */   {
/*  30: 47 */     this.builder = MRProtos.CountersProto.newBuilder();
/*  31:    */   }
/*  32:    */   
/*  33:    */   public CountersPBImpl(MRProtos.CountersProto proto)
/*  34:    */   {
/*  35: 51 */     this.proto = proto;
/*  36: 52 */     this.viaProto = true;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public MRProtos.CountersProto getProto()
/*  40:    */   {
/*  41: 56 */     mergeLocalToProto();
/*  42: 57 */     this.proto = (this.viaProto ? this.proto : this.builder.build());
/*  43: 58 */     this.viaProto = true;
/*  44: 59 */     return this.proto;
/*  45:    */   }
/*  46:    */   
/*  47:    */   private void mergeLocalToBuilder()
/*  48:    */   {
/*  49: 63 */     if (this.counterGroups != null) {
/*  50: 64 */       addCounterGroupsToProto();
/*  51:    */     }
/*  52:    */   }
/*  53:    */   
/*  54:    */   private void mergeLocalToProto()
/*  55:    */   {
/*  56: 69 */     if (this.viaProto) {
/*  57: 70 */       maybeInitBuilder();
/*  58:    */     }
/*  59: 71 */     mergeLocalToBuilder();
/*  60: 72 */     this.proto = this.builder.build();
/*  61: 73 */     this.viaProto = true;
/*  62:    */   }
/*  63:    */   
/*  64:    */   private void maybeInitBuilder()
/*  65:    */   {
/*  66: 77 */     if ((this.viaProto) || (this.builder == null)) {
/*  67: 78 */       this.builder = MRProtos.CountersProto.newBuilder(this.proto);
/*  68:    */     }
/*  69: 80 */     this.viaProto = false;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public Map<String, CounterGroup> getAllCounterGroups()
/*  73:    */   {
/*  74: 86 */     initCounterGroups();
/*  75: 87 */     return this.counterGroups;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public CounterGroup getCounterGroup(String key)
/*  79:    */   {
/*  80: 91 */     initCounterGroups();
/*  81: 92 */     return (CounterGroup)this.counterGroups.get(key);
/*  82:    */   }
/*  83:    */   
/*  84:    */   public Counter getCounter(Enum<?> key)
/*  85:    */   {
/*  86: 96 */     CounterGroup group = getCounterGroup(key.getDeclaringClass().getName());
/*  87: 97 */     return group == null ? null : group.getCounter(key.name());
/*  88:    */   }
/*  89:    */   
/*  90:    */   public void incrCounter(Enum<?> key, long amount)
/*  91:    */   {
/*  92:102 */     String groupName = key.getDeclaringClass().getName();
/*  93:103 */     if (getCounterGroup(groupName) == null)
/*  94:    */     {
/*  95:104 */       CounterGroup cGrp = new CounterGroupPBImpl();
/*  96:105 */       cGrp.setName(groupName);
/*  97:106 */       cGrp.setDisplayName(groupName);
/*  98:107 */       setCounterGroup(groupName, cGrp);
/*  99:    */     }
/* 100:109 */     if (getCounterGroup(groupName).getCounter(key.name()) == null)
/* 101:    */     {
/* 102:110 */       Counter c = new CounterPBImpl();
/* 103:111 */       c.setName(key.name());
/* 104:112 */       c.setDisplayName(key.name());
/* 105:113 */       c.setValue(0L);
/* 106:114 */       getCounterGroup(groupName).setCounter(key.name(), c);
/* 107:    */     }
/* 108:116 */     Counter counter = getCounterGroup(groupName).getCounter(key.name());
/* 109:117 */     counter.setValue(counter.getValue() + amount);
/* 110:    */   }
/* 111:    */   
/* 112:    */   private void initCounterGroups()
/* 113:    */   {
/* 114:121 */     if (this.counterGroups != null) {
/* 115:122 */       return;
/* 116:    */     }
/* 117:124 */     MRProtos.CountersProtoOrBuilder p = this.viaProto ? this.proto : this.builder;
/* 118:125 */     List<MRProtos.StringCounterGroupMapProto> list = p.getCounterGroupsList();
/* 119:126 */     this.counterGroups = new HashMap();
/* 120:128 */     for (MRProtos.StringCounterGroupMapProto c : list) {
/* 121:129 */       this.counterGroups.put(c.getKey(), convertFromProtoFormat(c.getValue()));
/* 122:    */     }
/* 123:    */   }
/* 124:    */   
/* 125:    */   public void addAllCounterGroups(Map<String, CounterGroup> counterGroups)
/* 126:    */   {
/* 127:135 */     if (counterGroups == null) {
/* 128:136 */       return;
/* 129:    */     }
/* 130:137 */     initCounterGroups();
/* 131:138 */     this.counterGroups.putAll(counterGroups);
/* 132:    */   }
/* 133:    */   
/* 134:    */   private void addCounterGroupsToProto()
/* 135:    */   {
/* 136:142 */     maybeInitBuilder();
/* 137:143 */     this.builder.clearCounterGroups();
/* 138:144 */     if (this.counterGroups == null) {
/* 139:145 */       return;
/* 140:    */     }
/* 141:146 */     Iterable<MRProtos.StringCounterGroupMapProto> iterable = new Iterable()
/* 142:    */     {
/* 143:    */       public Iterator<MRProtos.StringCounterGroupMapProto> iterator()
/* 144:    */       {
/* 145:150 */         new Iterator()
/* 146:    */         {
/* 147:152 */           Iterator<String> keyIter = CountersPBImpl.this.counterGroups.keySet().iterator();
/* 148:    */           
/* 149:    */           public void remove()
/* 150:    */           {
/* 151:156 */             throw new UnsupportedOperationException();
/* 152:    */           }
/* 153:    */           
/* 154:    */           public MRProtos.StringCounterGroupMapProto next()
/* 155:    */           {
/* 156:161 */             String key = (String)this.keyIter.next();
/* 157:162 */             return MRProtos.StringCounterGroupMapProto.newBuilder().setKey(key).setValue(CountersPBImpl.this.convertToProtoFormat((CounterGroup)CountersPBImpl.this.counterGroups.get(key))).build();
/* 158:    */           }
/* 159:    */           
/* 160:    */           public boolean hasNext()
/* 161:    */           {
/* 162:167 */             return this.keyIter.hasNext();
/* 163:    */           }
/* 164:    */         };
/* 165:    */       }
/* 166:171 */     };
/* 167:172 */     this.builder.addAllCounterGroups(iterable);
/* 168:    */   }
/* 169:    */   
/* 170:    */   public void setCounterGroup(String key, CounterGroup val)
/* 171:    */   {
/* 172:176 */     initCounterGroups();
/* 173:177 */     this.counterGroups.put(key, val);
/* 174:    */   }
/* 175:    */   
/* 176:    */   public void removeCounterGroup(String key)
/* 177:    */   {
/* 178:181 */     initCounterGroups();
/* 179:182 */     this.counterGroups.remove(key);
/* 180:    */   }
/* 181:    */   
/* 182:    */   public void clearCounterGroups()
/* 183:    */   {
/* 184:186 */     initCounterGroups();
/* 185:187 */     this.counterGroups.clear();
/* 186:    */   }
/* 187:    */   
/* 188:    */   private CounterGroupPBImpl convertFromProtoFormat(MRProtos.CounterGroupProto p)
/* 189:    */   {
/* 190:191 */     return new CounterGroupPBImpl(p);
/* 191:    */   }
/* 192:    */   
/* 193:    */   private MRProtos.CounterGroupProto convertToProtoFormat(CounterGroup t)
/* 194:    */   {
/* 195:195 */     return ((CounterGroupPBImpl)t).getProto();
/* 196:    */   }
/* 197:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-common-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.v2.api.records.impl.pb.CountersPBImpl
 * JD-Core Version:    0.7.0.1
 */