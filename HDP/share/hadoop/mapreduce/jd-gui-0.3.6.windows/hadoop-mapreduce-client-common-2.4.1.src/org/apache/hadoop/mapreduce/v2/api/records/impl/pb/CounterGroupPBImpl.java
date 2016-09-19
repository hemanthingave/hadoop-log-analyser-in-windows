/*   1:    */ package org.apache.hadoop.mapreduce.v2.api.records.impl.pb;
/*   2:    */ 
/*   3:    */ import java.util.HashMap;
/*   4:    */ import java.util.Iterator;
/*   5:    */ import java.util.List;
/*   6:    */ import java.util.Map;
/*   7:    */ import java.util.Set;
/*   8:    */ import org.apache.hadoop.mapreduce.v2.api.records.Counter;
/*   9:    */ import org.apache.hadoop.mapreduce.v2.api.records.CounterGroup;
/*  10:    */ import org.apache.hadoop.mapreduce.v2.proto.MRProtos.CounterGroupProto;
/*  11:    */ import org.apache.hadoop.mapreduce.v2.proto.MRProtos.CounterGroupProto.Builder;
/*  12:    */ import org.apache.hadoop.mapreduce.v2.proto.MRProtos.CounterGroupProtoOrBuilder;
/*  13:    */ import org.apache.hadoop.mapreduce.v2.proto.MRProtos.CounterProto;
/*  14:    */ import org.apache.hadoop.mapreduce.v2.proto.MRProtos.StringCounterMapProto;
/*  15:    */ import org.apache.hadoop.mapreduce.v2.proto.MRProtos.StringCounterMapProto.Builder;
/*  16:    */ import org.apache.hadoop.yarn.api.records.impl.pb.ProtoBase;
/*  17:    */ 
/*  18:    */ public class CounterGroupPBImpl
/*  19:    */   extends ProtoBase<MRProtos.CounterGroupProto>
/*  20:    */   implements CounterGroup
/*  21:    */ {
/*  22: 38 */   MRProtos.CounterGroupProto proto = MRProtos.CounterGroupProto.getDefaultInstance();
/*  23: 39 */   MRProtos.CounterGroupProto.Builder builder = null;
/*  24: 40 */   boolean viaProto = false;
/*  25: 42 */   private Map<String, Counter> counters = null;
/*  26:    */   
/*  27:    */   public CounterGroupPBImpl()
/*  28:    */   {
/*  29: 46 */     this.builder = MRProtos.CounterGroupProto.newBuilder();
/*  30:    */   }
/*  31:    */   
/*  32:    */   public CounterGroupPBImpl(MRProtos.CounterGroupProto proto)
/*  33:    */   {
/*  34: 50 */     this.proto = proto;
/*  35: 51 */     this.viaProto = true;
/*  36:    */   }
/*  37:    */   
/*  38:    */   public MRProtos.CounterGroupProto getProto()
/*  39:    */   {
/*  40: 55 */     mergeLocalToProto();
/*  41: 56 */     this.proto = (this.viaProto ? this.proto : this.builder.build());
/*  42: 57 */     this.viaProto = true;
/*  43: 58 */     return this.proto;
/*  44:    */   }
/*  45:    */   
/*  46:    */   private void mergeLocalToBuilder()
/*  47:    */   {
/*  48: 62 */     if (this.counters != null) {
/*  49: 63 */       addContersToProto();
/*  50:    */     }
/*  51:    */   }
/*  52:    */   
/*  53:    */   private void mergeLocalToProto()
/*  54:    */   {
/*  55: 68 */     if (this.viaProto) {
/*  56: 69 */       maybeInitBuilder();
/*  57:    */     }
/*  58: 70 */     mergeLocalToBuilder();
/*  59: 71 */     this.proto = this.builder.build();
/*  60: 72 */     this.viaProto = true;
/*  61:    */   }
/*  62:    */   
/*  63:    */   private void maybeInitBuilder()
/*  64:    */   {
/*  65: 76 */     if ((this.viaProto) || (this.builder == null)) {
/*  66: 77 */       this.builder = MRProtos.CounterGroupProto.newBuilder(this.proto);
/*  67:    */     }
/*  68: 79 */     this.viaProto = false;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public String getName()
/*  72:    */   {
/*  73: 85 */     MRProtos.CounterGroupProtoOrBuilder p = this.viaProto ? this.proto : this.builder;
/*  74: 86 */     if (!p.hasName()) {
/*  75: 87 */       return null;
/*  76:    */     }
/*  77: 89 */     return p.getName();
/*  78:    */   }
/*  79:    */   
/*  80:    */   public void setName(String name)
/*  81:    */   {
/*  82: 94 */     maybeInitBuilder();
/*  83: 95 */     if (name == null)
/*  84:    */     {
/*  85: 96 */       this.builder.clearName();
/*  86: 97 */       return;
/*  87:    */     }
/*  88: 99 */     this.builder.setName(name);
/*  89:    */   }
/*  90:    */   
/*  91:    */   public String getDisplayName()
/*  92:    */   {
/*  93:103 */     MRProtos.CounterGroupProtoOrBuilder p = this.viaProto ? this.proto : this.builder;
/*  94:104 */     if (!p.hasDisplayName()) {
/*  95:105 */       return null;
/*  96:    */     }
/*  97:107 */     return p.getDisplayName();
/*  98:    */   }
/*  99:    */   
/* 100:    */   public void setDisplayName(String displayName)
/* 101:    */   {
/* 102:112 */     maybeInitBuilder();
/* 103:113 */     if (displayName == null)
/* 104:    */     {
/* 105:114 */       this.builder.clearDisplayName();
/* 106:115 */       return;
/* 107:    */     }
/* 108:117 */     this.builder.setDisplayName(displayName);
/* 109:    */   }
/* 110:    */   
/* 111:    */   public Map<String, Counter> getAllCounters()
/* 112:    */   {
/* 113:121 */     initCounters();
/* 114:122 */     return this.counters;
/* 115:    */   }
/* 116:    */   
/* 117:    */   public Counter getCounter(String key)
/* 118:    */   {
/* 119:126 */     initCounters();
/* 120:127 */     return (Counter)this.counters.get(key);
/* 121:    */   }
/* 122:    */   
/* 123:    */   private void initCounters()
/* 124:    */   {
/* 125:131 */     if (this.counters != null) {
/* 126:132 */       return;
/* 127:    */     }
/* 128:134 */     MRProtos.CounterGroupProtoOrBuilder p = this.viaProto ? this.proto : this.builder;
/* 129:135 */     List<MRProtos.StringCounterMapProto> list = p.getCountersList();
/* 130:136 */     this.counters = new HashMap();
/* 131:138 */     for (MRProtos.StringCounterMapProto c : list) {
/* 132:139 */       this.counters.put(c.getKey(), convertFromProtoFormat(c.getValue()));
/* 133:    */     }
/* 134:    */   }
/* 135:    */   
/* 136:    */   public void addAllCounters(Map<String, Counter> counters)
/* 137:    */   {
/* 138:145 */     if (counters == null) {
/* 139:146 */       return;
/* 140:    */     }
/* 141:147 */     initCounters();
/* 142:148 */     this.counters.putAll(counters);
/* 143:    */   }
/* 144:    */   
/* 145:    */   private void addContersToProto()
/* 146:    */   {
/* 147:152 */     maybeInitBuilder();
/* 148:153 */     this.builder.clearCounters();
/* 149:154 */     if (this.counters == null) {
/* 150:155 */       return;
/* 151:    */     }
/* 152:156 */     Iterable<MRProtos.StringCounterMapProto> iterable = new Iterable()
/* 153:    */     {
/* 154:    */       public Iterator<MRProtos.StringCounterMapProto> iterator()
/* 155:    */       {
/* 156:160 */         new Iterator()
/* 157:    */         {
/* 158:162 */           Iterator<String> keyIter = CounterGroupPBImpl.this.counters.keySet().iterator();
/* 159:    */           
/* 160:    */           public void remove()
/* 161:    */           {
/* 162:166 */             throw new UnsupportedOperationException();
/* 163:    */           }
/* 164:    */           
/* 165:    */           public MRProtos.StringCounterMapProto next()
/* 166:    */           {
/* 167:171 */             String key = (String)this.keyIter.next();
/* 168:172 */             return MRProtos.StringCounterMapProto.newBuilder().setKey(key).setValue(CounterGroupPBImpl.this.convertToProtoFormat((Counter)CounterGroupPBImpl.this.counters.get(key))).build();
/* 169:    */           }
/* 170:    */           
/* 171:    */           public boolean hasNext()
/* 172:    */           {
/* 173:177 */             return this.keyIter.hasNext();
/* 174:    */           }
/* 175:    */         };
/* 176:    */       }
/* 177:181 */     };
/* 178:182 */     this.builder.addAllCounters(iterable);
/* 179:    */   }
/* 180:    */   
/* 181:    */   public void setCounter(String key, Counter val)
/* 182:    */   {
/* 183:186 */     initCounters();
/* 184:187 */     this.counters.put(key, val);
/* 185:    */   }
/* 186:    */   
/* 187:    */   public void removeCounter(String key)
/* 188:    */   {
/* 189:191 */     initCounters();
/* 190:192 */     this.counters.remove(key);
/* 191:    */   }
/* 192:    */   
/* 193:    */   public void clearCounters()
/* 194:    */   {
/* 195:196 */     initCounters();
/* 196:197 */     this.counters.clear();
/* 197:    */   }
/* 198:    */   
/* 199:    */   private CounterPBImpl convertFromProtoFormat(MRProtos.CounterProto p)
/* 200:    */   {
/* 201:201 */     return new CounterPBImpl(p);
/* 202:    */   }
/* 203:    */   
/* 204:    */   private MRProtos.CounterProto convertToProtoFormat(Counter t)
/* 205:    */   {
/* 206:205 */     return ((CounterPBImpl)t).getProto();
/* 207:    */   }
/* 208:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-common-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.v2.api.records.impl.pb.CounterGroupPBImpl
 * JD-Core Version:    0.7.0.1
 */