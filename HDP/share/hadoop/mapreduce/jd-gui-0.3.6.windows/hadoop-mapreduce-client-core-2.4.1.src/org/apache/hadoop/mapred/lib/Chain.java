/*   1:    */ package org.apache.hadoop.mapred.lib;
/*   2:    */ 
/*   3:    */ import java.io.ByteArrayInputStream;
/*   4:    */ import java.io.IOException;
/*   5:    */ import java.util.ArrayList;
/*   6:    */ import java.util.List;
/*   7:    */ import org.apache.hadoop.io.DataOutputBuffer;
/*   8:    */ import org.apache.hadoop.io.serializer.Deserializer;
/*   9:    */ import org.apache.hadoop.io.serializer.Serialization;
/*  10:    */ import org.apache.hadoop.io.serializer.SerializationFactory;
/*  11:    */ import org.apache.hadoop.io.serializer.Serializer;
/*  12:    */ import org.apache.hadoop.mapred.JobConf;
/*  13:    */ import org.apache.hadoop.mapred.Mapper;
/*  14:    */ import org.apache.hadoop.mapred.OutputCollector;
/*  15:    */ import org.apache.hadoop.mapred.Reducer;
/*  16:    */ import org.apache.hadoop.mapred.Reporter;
/*  17:    */ import org.apache.hadoop.util.GenericsUtil;
/*  18:    */ import org.apache.hadoop.util.ReflectionUtils;
/*  19:    */ 
/*  20:    */ class Chain
/*  21:    */   extends org.apache.hadoop.mapreduce.lib.chain.Chain
/*  22:    */ {
/*  23:    */   private static final String MAPPER_BY_VALUE = "chain.mapper.byValue";
/*  24:    */   private static final String REDUCER_BY_VALUE = "chain.reducer.byValue";
/*  25:    */   private JobConf chainJobConf;
/*  26: 46 */   private List<Mapper> mappers = new ArrayList();
/*  27:    */   private Reducer reducer;
/*  28: 51 */   private List<Serialization> mappersKeySerialization = new ArrayList();
/*  29: 53 */   private List<Serialization> mappersValueSerialization = new ArrayList();
/*  30:    */   private Serialization reducerKeySerialization;
/*  31:    */   private Serialization reducerValueSerialization;
/*  32:    */   
/*  33:    */   Chain(boolean isMap)
/*  34:    */   {
/*  35: 65 */     super(isMap);
/*  36:    */   }
/*  37:    */   
/*  38:    */   public static <K1, V1, K2, V2> void addMapper(boolean isMap, JobConf jobConf, Class<? extends Mapper<K1, V1, K2, V2>> klass, Class<? extends K1> inputKeyClass, Class<? extends V1> inputValueClass, Class<? extends K2> outputKeyClass, Class<? extends V2> outputValueClass, boolean byValue, JobConf mapperConf)
/*  39:    */   {
/*  40: 95 */     String prefix = getPrefix(isMap);
/*  41:    */     
/*  42:    */ 
/*  43: 98 */     checkReducerAlreadySet(isMap, jobConf, prefix, true);
/*  44:    */     
/*  45:    */ 
/*  46:101 */     int index = getIndex(jobConf, prefix);
/*  47:102 */     jobConf.setClass(prefix + ".mapper.class." + index, klass, Mapper.class);
/*  48:    */     
/*  49:104 */     validateKeyValueTypes(isMap, jobConf, inputKeyClass, inputValueClass, outputKeyClass, outputValueClass, index, prefix);
/*  50:108 */     if (mapperConf == null) {
/*  51:112 */       mapperConf = new JobConf(true);
/*  52:    */     }
/*  53:115 */     mapperConf.setBoolean("chain.mapper.byValue", byValue);
/*  54:    */     
/*  55:117 */     setMapperConf(isMap, jobConf, inputKeyClass, inputValueClass, outputKeyClass, outputValueClass, mapperConf, index, prefix);
/*  56:    */   }
/*  57:    */   
/*  58:    */   public static <K1, V1, K2, V2> void setReducer(JobConf jobConf, Class<? extends Reducer<K1, V1, K2, V2>> klass, Class<? extends K1> inputKeyClass, Class<? extends V1> inputValueClass, Class<? extends K2> outputKeyClass, Class<? extends V2> outputValueClass, boolean byValue, JobConf reducerConf)
/*  59:    */   {
/*  60:146 */     String prefix = getPrefix(false);
/*  61:147 */     checkReducerAlreadySet(false, jobConf, prefix, false);
/*  62:    */     
/*  63:149 */     jobConf.setClass(prefix + ".reducer.class", klass, Reducer.class);
/*  64:152 */     if (reducerConf == null) {
/*  65:156 */       reducerConf = new JobConf(false);
/*  66:    */     }
/*  67:161 */     reducerConf.setBoolean("chain.reducer.byValue", byValue);
/*  68:    */     
/*  69:163 */     setReducerConf(jobConf, inputKeyClass, inputValueClass, outputKeyClass, outputValueClass, reducerConf, prefix);
/*  70:    */   }
/*  71:    */   
/*  72:    */   public void configure(JobConf jobConf)
/*  73:    */   {
/*  74:173 */     String prefix = getPrefix(this.isMap);
/*  75:174 */     this.chainJobConf = jobConf;
/*  76:175 */     SerializationFactory serializationFactory = new SerializationFactory(this.chainJobConf);
/*  77:    */     
/*  78:177 */     int index = jobConf.getInt(prefix + ".size", 0);
/*  79:178 */     for (int i = 0; i < index; i++)
/*  80:    */     {
/*  81:179 */       Class<? extends Mapper> klass = jobConf.getClass(prefix + ".mapper.class." + i, null, Mapper.class);
/*  82:    */       
/*  83:181 */       JobConf mConf = new JobConf(getChainElementConf(jobConf, prefix + ".mapper.config." + i));
/*  84:    */       
/*  85:183 */       Mapper mapper = (Mapper)ReflectionUtils.newInstance(klass, mConf);
/*  86:184 */       this.mappers.add(mapper);
/*  87:186 */       if (mConf.getBoolean("chain.mapper.byValue", true))
/*  88:    */       {
/*  89:187 */         this.mappersKeySerialization.add(serializationFactory.getSerialization(mConf.getClass("mapreduce.chain.mapper.output.key.class", null)));
/*  90:    */         
/*  91:189 */         this.mappersValueSerialization.add(serializationFactory.getSerialization(mConf.getClass("mapreduce.chain.mapper.output.value.class", null)));
/*  92:    */       }
/*  93:    */       else
/*  94:    */       {
/*  95:192 */         this.mappersKeySerialization.add(null);
/*  96:193 */         this.mappersValueSerialization.add(null);
/*  97:    */       }
/*  98:    */     }
/*  99:196 */     Class<? extends Reducer> klass = jobConf.getClass(prefix + ".reducer.class", null, Reducer.class);
/* 100:198 */     if (klass != null)
/* 101:    */     {
/* 102:199 */       JobConf rConf = new JobConf(getChainElementConf(jobConf, prefix + ".reducer.config"));
/* 103:    */       
/* 104:201 */       this.reducer = ((Reducer)ReflectionUtils.newInstance(klass, rConf));
/* 105:202 */       if (rConf.getBoolean("chain.reducer.byValue", true))
/* 106:    */       {
/* 107:203 */         this.reducerKeySerialization = serializationFactory.getSerialization(rConf.getClass("mapreduce.chain.reducer.output.key.class", null));
/* 108:    */         
/* 109:205 */         this.reducerValueSerialization = serializationFactory.getSerialization(rConf.getClass("mapreduce.chain.reducer.output.value.class", null));
/* 110:    */       }
/* 111:    */       else
/* 112:    */       {
/* 113:208 */         this.reducerKeySerialization = null;
/* 114:209 */         this.reducerValueSerialization = null;
/* 115:    */       }
/* 116:    */     }
/* 117:    */   }
/* 118:    */   
/* 119:    */   protected JobConf getChainJobConf()
/* 120:    */   {
/* 121:220 */     return this.chainJobConf;
/* 122:    */   }
/* 123:    */   
/* 124:    */   public Mapper getFirstMap()
/* 125:    */   {
/* 126:229 */     return this.mappers.size() > 0 ? (Mapper)this.mappers.get(0) : null;
/* 127:    */   }
/* 128:    */   
/* 129:    */   public Reducer getReducer()
/* 130:    */   {
/* 131:238 */     return this.reducer;
/* 132:    */   }
/* 133:    */   
/* 134:    */   public OutputCollector getMapperCollector(int mapperIndex, OutputCollector output, Reporter reporter)
/* 135:    */   {
/* 136:253 */     Serialization keySerialization = (Serialization)this.mappersKeySerialization.get(mapperIndex);
/* 137:254 */     Serialization valueSerialization = (Serialization)this.mappersValueSerialization.get(mapperIndex);
/* 138:    */     
/* 139:256 */     return new ChainOutputCollector(mapperIndex, keySerialization, valueSerialization, output, reporter);
/* 140:    */   }
/* 141:    */   
/* 142:    */   public OutputCollector getReducerCollector(OutputCollector output, Reporter reporter)
/* 143:    */   {
/* 144:270 */     return new ChainOutputCollector(this.reducerKeySerialization, this.reducerValueSerialization, output, reporter);
/* 145:    */   }
/* 146:    */   
/* 147:    */   public void close()
/* 148:    */     throws IOException
/* 149:    */   {
/* 150:282 */     for (Mapper map : this.mappers) {
/* 151:283 */       map.close();
/* 152:    */     }
/* 153:285 */     if (this.reducer != null) {
/* 154:286 */       this.reducer.close();
/* 155:    */     }
/* 156:    */   }
/* 157:    */   
/* 158:293 */   private ThreadLocal<DataOutputBuffer> threadLocalDataOutputBuffer = new ThreadLocal()
/* 159:    */   {
/* 160:    */     protected DataOutputBuffer initialValue()
/* 161:    */     {
/* 162:296 */       return new DataOutputBuffer(1024);
/* 163:    */     }
/* 164:    */   };
/* 165:    */   
/* 166:    */   private class ChainOutputCollector<K, V>
/* 167:    */     implements OutputCollector<K, V>
/* 168:    */   {
/* 169:    */     private int nextMapperIndex;
/* 170:    */     private Serialization<K> keySerialization;
/* 171:    */     private Serialization<V> valueSerialization;
/* 172:    */     private OutputCollector output;
/* 173:    */     private Reporter reporter;
/* 174:    */     
/* 175:    */     public ChainOutputCollector(Serialization<K> index, Serialization<V> keySerialization, OutputCollector valueSerialization, Reporter output)
/* 176:    */     {
/* 177:320 */       this.nextMapperIndex = (index + 1);
/* 178:321 */       this.keySerialization = keySerialization;
/* 179:322 */       this.valueSerialization = valueSerialization;
/* 180:323 */       this.output = output;
/* 181:324 */       this.reporter = reporter;
/* 182:    */     }
/* 183:    */     
/* 184:    */     public ChainOutputCollector(Serialization<V> keySerialization, OutputCollector valueSerialization, Reporter output)
/* 185:    */     {
/* 186:333 */       this.nextMapperIndex = 0;
/* 187:334 */       this.keySerialization = keySerialization;
/* 188:335 */       this.valueSerialization = valueSerialization;
/* 189:336 */       this.output = output;
/* 190:337 */       this.reporter = reporter;
/* 191:    */     }
/* 192:    */     
/* 193:    */     public void collect(K key, V value)
/* 194:    */       throws IOException
/* 195:    */     {
/* 196:342 */       if (this.nextMapperIndex < Chain.this.mappers.size())
/* 197:    */       {
/* 198:346 */         if (this.keySerialization != null)
/* 199:    */         {
/* 200:347 */           key = makeCopyForPassByValue(this.keySerialization, key);
/* 201:348 */           value = makeCopyForPassByValue(this.valueSerialization, value);
/* 202:    */         }
/* 203:352 */         Serialization nextKeySerialization = (Serialization)Chain.this.mappersKeySerialization.get(this.nextMapperIndex);
/* 204:    */         
/* 205:354 */         Serialization nextValueSerialization = (Serialization)Chain.this.mappersValueSerialization.get(this.nextMapperIndex);
/* 206:    */         
/* 207:356 */         Mapper nextMapper = (Mapper)Chain.this.mappers.get(this.nextMapperIndex);
/* 208:    */         
/* 209:    */ 
/* 210:359 */         nextMapper.map(key, value, new ChainOutputCollector(Chain.this, this.nextMapperIndex, nextKeySerialization, nextValueSerialization, this.output, this.reporter), this.reporter);
/* 211:    */       }
/* 212:    */       else
/* 213:    */       {
/* 214:367 */         this.output.collect(key, value);
/* 215:    */       }
/* 216:    */     }
/* 217:    */     
/* 218:    */     private <E> E makeCopyForPassByValue(Serialization<E> serialization, E obj)
/* 219:    */       throws IOException
/* 220:    */     {
/* 221:373 */       Serializer<E> ser = serialization.getSerializer(GenericsUtil.getClass(obj));
/* 222:    */       
/* 223:375 */       Deserializer<E> deser = serialization.getDeserializer(GenericsUtil.getClass(obj));
/* 224:    */       
/* 225:    */ 
/* 226:378 */       DataOutputBuffer dof = (DataOutputBuffer)Chain.this.threadLocalDataOutputBuffer.get();
/* 227:    */       
/* 228:380 */       dof.reset();
/* 229:381 */       ser.open(dof);
/* 230:382 */       ser.serialize(obj);
/* 231:383 */       ser.close();
/* 232:384 */       obj = ReflectionUtils.newInstance(GenericsUtil.getClass(obj), Chain.this.getChainJobConf());
/* 233:    */       
/* 234:386 */       ByteArrayInputStream bais = new ByteArrayInputStream(dof.getData(), 0, dof.getLength());
/* 235:    */       
/* 236:388 */       deser.open(bais);
/* 237:389 */       deser.deserialize(obj);
/* 238:390 */       deser.close();
/* 239:391 */       return obj;
/* 240:    */     }
/* 241:    */   }
/* 242:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.lib.Chain
 * JD-Core Version:    0.7.0.1
 */