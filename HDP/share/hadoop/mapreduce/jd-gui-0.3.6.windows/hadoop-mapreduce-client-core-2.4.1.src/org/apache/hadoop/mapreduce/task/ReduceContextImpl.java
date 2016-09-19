/*   1:    */ package org.apache.hadoop.mapreduce.task;
/*   2:    */ 
/*   3:    */ import java.io.DataOutputStream;
/*   4:    */ import java.io.IOException;
/*   5:    */ import java.util.Iterator;
/*   6:    */ import java.util.NoSuchElementException;
/*   7:    */ import org.apache.hadoop.classification.InterfaceAudience.Private;
/*   8:    */ import org.apache.hadoop.classification.InterfaceStability.Unstable;
/*   9:    */ import org.apache.hadoop.conf.Configuration;
/*  10:    */ import org.apache.hadoop.io.BytesWritable;
/*  11:    */ import org.apache.hadoop.io.DataInputBuffer;
/*  12:    */ import org.apache.hadoop.io.RawComparator;
/*  13:    */ import org.apache.hadoop.io.WritableUtils;
/*  14:    */ import org.apache.hadoop.io.serializer.Deserializer;
/*  15:    */ import org.apache.hadoop.io.serializer.SerializationFactory;
/*  16:    */ import org.apache.hadoop.io.serializer.Serializer;
/*  17:    */ import org.apache.hadoop.mapred.BackupStore;
/*  18:    */ import org.apache.hadoop.mapred.RawKeyValueIterator;
/*  19:    */ import org.apache.hadoop.mapreduce.Counter;
/*  20:    */ import org.apache.hadoop.mapreduce.OutputCommitter;
/*  21:    */ import org.apache.hadoop.mapreduce.RecordWriter;
/*  22:    */ import org.apache.hadoop.mapreduce.ReduceContext;
/*  23:    */ import org.apache.hadoop.mapreduce.ReduceContext.ValueIterator;
/*  24:    */ import org.apache.hadoop.mapreduce.StatusReporter;
/*  25:    */ import org.apache.hadoop.mapreduce.TaskAttemptID;
/*  26:    */ import org.apache.hadoop.util.Progressable;
/*  27:    */ 
/*  28:    */ @InterfaceAudience.Private
/*  29:    */ @InterfaceStability.Unstable
/*  30:    */ public class ReduceContextImpl<KEYIN, VALUEIN, KEYOUT, VALUEOUT>
/*  31:    */   extends TaskInputOutputContextImpl<KEYIN, VALUEIN, KEYOUT, VALUEOUT>
/*  32:    */   implements ReduceContext<KEYIN, VALUEIN, KEYOUT, VALUEOUT>
/*  33:    */ {
/*  34:    */   private RawKeyValueIterator input;
/*  35:    */   private Counter inputValueCounter;
/*  36:    */   private Counter inputKeyCounter;
/*  37:    */   private RawComparator<KEYIN> comparator;
/*  38:    */   private KEYIN key;
/*  39:    */   private VALUEIN value;
/*  40: 65 */   private boolean firstValue = false;
/*  41: 66 */   private boolean nextKeyIsSame = false;
/*  42:    */   private boolean hasMore;
/*  43:    */   protected Progressable reporter;
/*  44:    */   private Deserializer<KEYIN> keyDeserializer;
/*  45:    */   private Deserializer<VALUEIN> valueDeserializer;
/*  46: 71 */   private DataInputBuffer buffer = new DataInputBuffer();
/*  47: 72 */   private BytesWritable currentRawKey = new BytesWritable();
/*  48: 73 */   private ReduceContextImpl<KEYIN, VALUEIN, KEYOUT, VALUEOUT>.ValueIterable iterable = new ValueIterable();
/*  49: 74 */   private boolean isMarked = false;
/*  50:    */   private BackupStore<KEYIN, VALUEIN> backupStore;
/*  51:    */   private final SerializationFactory serializationFactory;
/*  52:    */   private final Class<KEYIN> keyClass;
/*  53:    */   private final Class<VALUEIN> valueClass;
/*  54:    */   private final Configuration conf;
/*  55:    */   private final TaskAttemptID taskid;
/*  56: 81 */   private int currentKeyLength = -1;
/*  57: 82 */   private int currentValueLength = -1;
/*  58:    */   
/*  59:    */   public ReduceContextImpl(Configuration conf, TaskAttemptID taskid, RawKeyValueIterator input, Counter inputKeyCounter, Counter inputValueCounter, RecordWriter<KEYOUT, VALUEOUT> output, OutputCommitter committer, StatusReporter reporter, RawComparator<KEYIN> comparator, Class<KEYIN> keyClass, Class<VALUEIN> valueClass)
/*  60:    */     throws InterruptedException, IOException
/*  61:    */   {
/*  62: 95 */     super(conf, taskid, output, committer, reporter);
/*  63: 96 */     this.input = input;
/*  64: 97 */     this.inputKeyCounter = inputKeyCounter;
/*  65: 98 */     this.inputValueCounter = inputValueCounter;
/*  66: 99 */     this.comparator = comparator;
/*  67:100 */     this.serializationFactory = new SerializationFactory(conf);
/*  68:101 */     this.keyDeserializer = this.serializationFactory.getDeserializer(keyClass);
/*  69:102 */     this.keyDeserializer.open(this.buffer);
/*  70:103 */     this.valueDeserializer = this.serializationFactory.getDeserializer(valueClass);
/*  71:104 */     this.valueDeserializer.open(this.buffer);
/*  72:105 */     this.hasMore = input.next();
/*  73:106 */     this.keyClass = keyClass;
/*  74:107 */     this.valueClass = valueClass;
/*  75:108 */     this.conf = conf;
/*  76:109 */     this.taskid = taskid;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public boolean nextKey()
/*  80:    */     throws IOException, InterruptedException
/*  81:    */   {
/*  82:114 */     while ((this.hasMore) && (this.nextKeyIsSame)) {
/*  83:115 */       nextKeyValue();
/*  84:    */     }
/*  85:117 */     if (this.hasMore)
/*  86:    */     {
/*  87:118 */       if (this.inputKeyCounter != null) {
/*  88:119 */         this.inputKeyCounter.increment(1L);
/*  89:    */       }
/*  90:121 */       return nextKeyValue();
/*  91:    */     }
/*  92:123 */     return false;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public boolean nextKeyValue()
/*  96:    */     throws IOException, InterruptedException
/*  97:    */   {
/*  98:132 */     if (!this.hasMore)
/*  99:    */     {
/* 100:133 */       this.key = null;
/* 101:134 */       this.value = null;
/* 102:135 */       return false;
/* 103:    */     }
/* 104:137 */     this.firstValue = (!this.nextKeyIsSame);
/* 105:138 */     DataInputBuffer nextKey = this.input.getKey();
/* 106:139 */     this.currentRawKey.set(nextKey.getData(), nextKey.getPosition(), nextKey.getLength() - nextKey.getPosition());
/* 107:    */     
/* 108:141 */     this.buffer.reset(this.currentRawKey.getBytes(), 0, this.currentRawKey.getLength());
/* 109:142 */     this.key = this.keyDeserializer.deserialize(this.key);
/* 110:143 */     DataInputBuffer nextVal = this.input.getValue();
/* 111:144 */     this.buffer.reset(nextVal.getData(), nextVal.getPosition(), nextVal.getLength() - nextVal.getPosition());
/* 112:    */     
/* 113:146 */     this.value = this.valueDeserializer.deserialize(this.value);
/* 114:    */     
/* 115:148 */     this.currentKeyLength = (nextKey.getLength() - nextKey.getPosition());
/* 116:149 */     this.currentValueLength = (nextVal.getLength() - nextVal.getPosition());
/* 117:151 */     if (this.isMarked) {
/* 118:152 */       this.backupStore.write(nextKey, nextVal);
/* 119:    */     }
/* 120:155 */     this.hasMore = this.input.next();
/* 121:156 */     if (this.hasMore)
/* 122:    */     {
/* 123:157 */       nextKey = this.input.getKey();
/* 124:158 */       this.nextKeyIsSame = (this.comparator.compare(this.currentRawKey.getBytes(), 0, this.currentRawKey.getLength(), nextKey.getData(), nextKey.getPosition(), nextKey.getLength() - nextKey.getPosition()) == 0);
/* 125:    */     }
/* 126:    */     else
/* 127:    */     {
/* 128:165 */       this.nextKeyIsSame = false;
/* 129:    */     }
/* 130:167 */     this.inputValueCounter.increment(1L);
/* 131:168 */     return true;
/* 132:    */   }
/* 133:    */   
/* 134:    */   public KEYIN getCurrentKey()
/* 135:    */   {
/* 136:172 */     return this.key;
/* 137:    */   }
/* 138:    */   
/* 139:    */   public VALUEIN getCurrentValue()
/* 140:    */   {
/* 141:177 */     return this.value;
/* 142:    */   }
/* 143:    */   
/* 144:    */   BackupStore<KEYIN, VALUEIN> getBackupStore()
/* 145:    */   {
/* 146:181 */     return this.backupStore;
/* 147:    */   }
/* 148:    */   
/* 149:    */   protected class ValueIterator
/* 150:    */     implements ReduceContext.ValueIterator<VALUEIN>
/* 151:    */   {
/* 152:186 */     private boolean inReset = false;
/* 153:187 */     private boolean clearMarkFlag = false;
/* 154:    */     
/* 155:    */     protected ValueIterator() {}
/* 156:    */     
/* 157:    */     public boolean hasNext()
/* 158:    */     {
/* 159:    */       try
/* 160:    */       {
/* 161:192 */         if ((this.inReset) && (ReduceContextImpl.this.backupStore.hasNext())) {
/* 162:193 */           return true;
/* 163:    */         }
/* 164:    */       }
/* 165:    */       catch (Exception e)
/* 166:    */       {
/* 167:196 */         e.printStackTrace();
/* 168:197 */         throw new RuntimeException("hasNext failed", e);
/* 169:    */       }
/* 170:199 */       return (ReduceContextImpl.this.firstValue) || (ReduceContextImpl.this.nextKeyIsSame);
/* 171:    */     }
/* 172:    */     
/* 173:    */     public VALUEIN next()
/* 174:    */     {
/* 175:204 */       if (this.inReset) {
/* 176:    */         try
/* 177:    */         {
/* 178:206 */           if (ReduceContextImpl.this.backupStore.hasNext())
/* 179:    */           {
/* 180:207 */             ReduceContextImpl.this.backupStore.next();
/* 181:208 */             DataInputBuffer next = ReduceContextImpl.this.backupStore.nextValue();
/* 182:209 */             ReduceContextImpl.this.buffer.reset(next.getData(), next.getPosition(), next.getLength() - next.getPosition());
/* 183:    */             
/* 184:211 */             ReduceContextImpl.this.value = ReduceContextImpl.this.valueDeserializer.deserialize(ReduceContextImpl.this.value);
/* 185:212 */             return ReduceContextImpl.this.value;
/* 186:    */           }
/* 187:214 */           this.inReset = false;
/* 188:215 */           ReduceContextImpl.this.backupStore.exitResetMode();
/* 189:216 */           if (this.clearMarkFlag)
/* 190:    */           {
/* 191:217 */             this.clearMarkFlag = false;
/* 192:218 */             ReduceContextImpl.this.isMarked = false;
/* 193:    */           }
/* 194:    */         }
/* 195:    */         catch (IOException e)
/* 196:    */         {
/* 197:222 */           e.printStackTrace();
/* 198:223 */           throw new RuntimeException("next value iterator failed", e);
/* 199:    */         }
/* 200:    */       }
/* 201:228 */       if (ReduceContextImpl.this.firstValue)
/* 202:    */       {
/* 203:229 */         ReduceContextImpl.this.firstValue = false;
/* 204:230 */         return ReduceContextImpl.this.value;
/* 205:    */       }
/* 206:234 */       if (!ReduceContextImpl.this.nextKeyIsSame) {
/* 207:235 */         throw new NoSuchElementException("iterate past last value");
/* 208:    */       }
/* 209:    */       try
/* 210:    */       {
/* 211:239 */         ReduceContextImpl.this.nextKeyValue();
/* 212:240 */         return ReduceContextImpl.this.value;
/* 213:    */       }
/* 214:    */       catch (IOException ie)
/* 215:    */       {
/* 216:242 */         throw new RuntimeException("next value iterator failed", ie);
/* 217:    */       }
/* 218:    */       catch (InterruptedException ie)
/* 219:    */       {
/* 220:245 */         throw new RuntimeException("next value iterator interrupted", ie);
/* 221:    */       }
/* 222:    */     }
/* 223:    */     
/* 224:    */     public void remove()
/* 225:    */     {
/* 226:251 */       throw new UnsupportedOperationException("remove not implemented");
/* 227:    */     }
/* 228:    */     
/* 229:    */     public void mark()
/* 230:    */       throws IOException
/* 231:    */     {
/* 232:256 */       if (ReduceContextImpl.this.getBackupStore() == null) {
/* 233:257 */         ReduceContextImpl.this.backupStore = new BackupStore(ReduceContextImpl.this.conf, ReduceContextImpl.this.taskid);
/* 234:    */       }
/* 235:259 */       ReduceContextImpl.this.isMarked = true;
/* 236:260 */       if (!this.inReset)
/* 237:    */       {
/* 238:261 */         ReduceContextImpl.this.backupStore.reinitialize();
/* 239:262 */         if (ReduceContextImpl.this.currentKeyLength == -1) {
/* 240:265 */           return;
/* 241:    */         }
/* 242:267 */         assert (ReduceContextImpl.this.currentValueLength != -1);
/* 243:268 */         int requestedSize = ReduceContextImpl.this.currentKeyLength + ReduceContextImpl.this.currentValueLength + WritableUtils.getVIntSize(ReduceContextImpl.this.currentKeyLength) + WritableUtils.getVIntSize(ReduceContextImpl.this.currentValueLength);
/* 244:    */         
/* 245:    */ 
/* 246:271 */         DataOutputStream out = ReduceContextImpl.this.backupStore.getOutputStream(requestedSize);
/* 247:272 */         writeFirstKeyValueBytes(out);
/* 248:273 */         ReduceContextImpl.this.backupStore.updateCounters(requestedSize);
/* 249:    */       }
/* 250:    */       else
/* 251:    */       {
/* 252:275 */         ReduceContextImpl.this.backupStore.mark();
/* 253:    */       }
/* 254:    */     }
/* 255:    */     
/* 256:    */     public void reset()
/* 257:    */       throws IOException
/* 258:    */     {
/* 259:284 */       if (this.clearMarkFlag)
/* 260:    */       {
/* 261:285 */         this.clearMarkFlag = false;
/* 262:286 */         ReduceContextImpl.this.backupStore.clearMark();
/* 263:287 */         throw new IOException("Reset called without a previous mark");
/* 264:    */       }
/* 265:290 */       if (!ReduceContextImpl.this.isMarked) {
/* 266:291 */         throw new IOException("Reset called without a previous mark");
/* 267:    */       }
/* 268:293 */       this.inReset = true;
/* 269:294 */       ReduceContextImpl.this.backupStore.reset();
/* 270:    */     }
/* 271:    */     
/* 272:    */     public void clearMark()
/* 273:    */       throws IOException
/* 274:    */     {
/* 275:299 */       if (ReduceContextImpl.this.getBackupStore() == null) {
/* 276:300 */         return;
/* 277:    */       }
/* 278:302 */       if (this.inReset)
/* 279:    */       {
/* 280:303 */         this.clearMarkFlag = true;
/* 281:304 */         ReduceContextImpl.this.backupStore.clearMark();
/* 282:    */       }
/* 283:    */       else
/* 284:    */       {
/* 285:306 */         this.inReset = ReduceContextImpl.this.isMarked = false;
/* 286:307 */         ReduceContextImpl.this.backupStore.reinitialize();
/* 287:    */       }
/* 288:    */     }
/* 289:    */     
/* 290:    */     public void resetBackupStore()
/* 291:    */       throws IOException
/* 292:    */     {
/* 293:317 */       if (ReduceContextImpl.this.getBackupStore() == null) {
/* 294:318 */         return;
/* 295:    */       }
/* 296:320 */       this.inReset = ReduceContextImpl.this.isMarked = false;
/* 297:321 */       ReduceContextImpl.this.backupStore.reinitialize();
/* 298:322 */       ReduceContextImpl.this.currentKeyLength = -1;
/* 299:    */     }
/* 300:    */     
/* 301:    */     private void writeFirstKeyValueBytes(DataOutputStream out)
/* 302:    */       throws IOException
/* 303:    */     {
/* 304:335 */       assert ((ReduceContextImpl.this.getCurrentKey() != null) && (ReduceContextImpl.this.getCurrentValue() != null));
/* 305:336 */       WritableUtils.writeVInt(out, ReduceContextImpl.this.currentKeyLength);
/* 306:337 */       WritableUtils.writeVInt(out, ReduceContextImpl.this.currentValueLength);
/* 307:338 */       Serializer<KEYIN> keySerializer = ReduceContextImpl.this.serializationFactory.getSerializer(ReduceContextImpl.this.keyClass);
/* 308:    */       
/* 309:340 */       keySerializer.open(out);
/* 310:341 */       keySerializer.serialize(ReduceContextImpl.this.getCurrentKey());
/* 311:    */       
/* 312:343 */       Serializer<VALUEIN> valueSerializer = ReduceContextImpl.this.serializationFactory.getSerializer(ReduceContextImpl.this.valueClass);
/* 313:    */       
/* 314:345 */       valueSerializer.open(out);
/* 315:346 */       valueSerializer.serialize(ReduceContextImpl.this.getCurrentValue());
/* 316:    */     }
/* 317:    */   }
/* 318:    */   
/* 319:    */   protected class ValueIterable
/* 320:    */     implements Iterable<VALUEIN>
/* 321:    */   {
/* 322:351 */     private ReduceContextImpl<KEYIN, VALUEIN, KEYOUT, VALUEOUT>.ValueIterator iterator = new ReduceContextImpl.ValueIterator(ReduceContextImpl.this);
/* 323:    */     
/* 324:    */     protected ValueIterable() {}
/* 325:    */     
/* 326:    */     public Iterator<VALUEIN> iterator()
/* 327:    */     {
/* 328:354 */       return this.iterator;
/* 329:    */     }
/* 330:    */   }
/* 331:    */   
/* 332:    */   public Iterable<VALUEIN> getValues()
/* 333:    */     throws IOException, InterruptedException
/* 334:    */   {
/* 335:366 */     return this.iterable;
/* 336:    */   }
/* 337:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.task.ReduceContextImpl
 * JD-Core Version:    0.7.0.1
 */