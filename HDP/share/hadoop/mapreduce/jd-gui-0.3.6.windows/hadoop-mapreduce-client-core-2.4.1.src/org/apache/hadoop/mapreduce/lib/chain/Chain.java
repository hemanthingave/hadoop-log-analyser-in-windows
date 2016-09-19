/*   1:    */ package org.apache.hadoop.mapreduce.lib.chain;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import java.util.List;
/*   6:    */ import java.util.Map.Entry;
/*   7:    */ import org.apache.hadoop.classification.InterfaceAudience.Private;
/*   8:    */ import org.apache.hadoop.classification.InterfaceStability.Unstable;
/*   9:    */ import org.apache.hadoop.conf.Configuration;
/*  10:    */ import org.apache.hadoop.io.DefaultStringifier;
/*  11:    */ import org.apache.hadoop.io.Stringifier;
/*  12:    */ import org.apache.hadoop.mapreduce.InputSplit;
/*  13:    */ import org.apache.hadoop.mapreduce.Job;
/*  14:    */ import org.apache.hadoop.mapreduce.MapContext;
/*  15:    */ import org.apache.hadoop.mapreduce.Mapper;
/*  16:    */ import org.apache.hadoop.mapreduce.Mapper.Context;
/*  17:    */ import org.apache.hadoop.mapreduce.RecordReader;
/*  18:    */ import org.apache.hadoop.mapreduce.RecordWriter;
/*  19:    */ import org.apache.hadoop.mapreduce.ReduceContext;
/*  20:    */ import org.apache.hadoop.mapreduce.Reducer;
/*  21:    */ import org.apache.hadoop.mapreduce.Reducer.Context;
/*  22:    */ import org.apache.hadoop.mapreduce.TaskAttemptContext;
/*  23:    */ import org.apache.hadoop.mapreduce.TaskInputOutputContext;
/*  24:    */ import org.apache.hadoop.mapreduce.lib.map.WrappedMapper;
/*  25:    */ import org.apache.hadoop.mapreduce.lib.reduce.WrappedReducer;
/*  26:    */ import org.apache.hadoop.util.ReflectionUtils;
/*  27:    */ 
/*  28:    */ @InterfaceAudience.Private
/*  29:    */ @InterfaceStability.Unstable
/*  30:    */ public class Chain
/*  31:    */ {
/*  32:    */   protected static final String CHAIN_MAPPER = "mapreduce.chain.mapper";
/*  33:    */   protected static final String CHAIN_REDUCER = "mapreduce.chain.reducer";
/*  34:    */   protected static final String CHAIN_MAPPER_SIZE = ".size";
/*  35:    */   protected static final String CHAIN_MAPPER_CLASS = ".mapper.class.";
/*  36:    */   protected static final String CHAIN_MAPPER_CONFIG = ".mapper.config.";
/*  37:    */   protected static final String CHAIN_REDUCER_CLASS = ".reducer.class";
/*  38:    */   protected static final String CHAIN_REDUCER_CONFIG = ".reducer.config";
/*  39:    */   protected static final String MAPPER_INPUT_KEY_CLASS = "mapreduce.chain.mapper.input.key.class";
/*  40:    */   protected static final String MAPPER_INPUT_VALUE_CLASS = "mapreduce.chain.mapper.input.value.class";
/*  41:    */   protected static final String MAPPER_OUTPUT_KEY_CLASS = "mapreduce.chain.mapper.output.key.class";
/*  42:    */   protected static final String MAPPER_OUTPUT_VALUE_CLASS = "mapreduce.chain.mapper.output.value.class";
/*  43:    */   protected static final String REDUCER_INPUT_KEY_CLASS = "mapreduce.chain.reducer.input.key.class";
/*  44:    */   protected static final String REDUCER_INPUT_VALUE_CLASS = "maperduce.chain.reducer.input.value.class";
/*  45:    */   protected static final String REDUCER_OUTPUT_KEY_CLASS = "mapreduce.chain.reducer.output.key.class";
/*  46:    */   protected static final String REDUCER_OUTPUT_VALUE_CLASS = "mapreduce.chain.reducer.output.value.class";
/*  47:    */   protected boolean isMap;
/*  48: 79 */   private List<Mapper> mappers = new ArrayList();
/*  49:    */   private Reducer<?, ?, ?, ?> reducer;
/*  50: 82 */   private List<Configuration> confList = new ArrayList();
/*  51:    */   private Configuration rConf;
/*  52: 84 */   private List<Thread> threads = new ArrayList();
/*  53: 85 */   private List<ChainBlockingQueue<?>> blockingQueues = new ArrayList();
/*  54: 87 */   private Throwable throwable = null;
/*  55:    */   
/*  56:    */   protected Chain(boolean isMap)
/*  57:    */   {
/*  58: 97 */     this.isMap = isMap;
/*  59:    */   }
/*  60:    */   
/*  61:    */   static class KeyValuePair<K, V>
/*  62:    */   {
/*  63:    */     K key;
/*  64:    */     V value;
/*  65:    */     boolean endOfInput;
/*  66:    */     
/*  67:    */     KeyValuePair(K key, V value)
/*  68:    */     {
/*  69:106 */       this.key = key;
/*  70:107 */       this.value = value;
/*  71:108 */       this.endOfInput = false;
/*  72:    */     }
/*  73:    */     
/*  74:    */     KeyValuePair(boolean eof)
/*  75:    */     {
/*  76:112 */       this.key = null;
/*  77:113 */       this.value = null;
/*  78:114 */       this.endOfInput = eof;
/*  79:    */     }
/*  80:    */   }
/*  81:    */   
/*  82:    */   private static class ChainRecordReader<KEYIN, VALUEIN>
/*  83:    */     extends RecordReader<KEYIN, VALUEIN>
/*  84:    */   {
/*  85:    */     private Class<?> keyClass;
/*  86:    */     private Class<?> valueClass;
/*  87:    */     private KEYIN key;
/*  88:    */     private VALUEIN value;
/*  89:    */     private Configuration conf;
/*  90:126 */     TaskInputOutputContext<KEYIN, VALUEIN, ?, ?> inputContext = null;
/*  91:127 */     Chain.ChainBlockingQueue<Chain.KeyValuePair<KEYIN, VALUEIN>> inputQueue = null;
/*  92:    */     
/*  93:    */     ChainRecordReader(Class<?> keyClass, Class<?> valueClass, Chain.ChainBlockingQueue<Chain.KeyValuePair<KEYIN, VALUEIN>> inputQueue, Configuration conf)
/*  94:    */     {
/*  95:133 */       this.keyClass = keyClass;
/*  96:134 */       this.valueClass = valueClass;
/*  97:135 */       this.inputQueue = inputQueue;
/*  98:136 */       this.conf = conf;
/*  99:    */     }
/* 100:    */     
/* 101:    */     ChainRecordReader(TaskInputOutputContext<KEYIN, VALUEIN, ?, ?> context)
/* 102:    */     {
/* 103:141 */       this.inputContext = context;
/* 104:    */     }
/* 105:    */     
/* 106:    */     public void initialize(InputSplit split, TaskAttemptContext context)
/* 107:    */       throws IOException, InterruptedException
/* 108:    */     {}
/* 109:    */     
/* 110:    */     public boolean nextKeyValue()
/* 111:    */       throws IOException, InterruptedException
/* 112:    */     {
/* 113:154 */       if (this.inputQueue != null) {
/* 114:155 */         return readFromQueue();
/* 115:    */       }
/* 116:156 */       if (this.inputContext.nextKeyValue())
/* 117:    */       {
/* 118:157 */         this.key = this.inputContext.getCurrentKey();
/* 119:158 */         this.value = this.inputContext.getCurrentValue();
/* 120:159 */         return true;
/* 121:    */       }
/* 122:161 */       return false;
/* 123:    */     }
/* 124:    */     
/* 125:    */     private boolean readFromQueue()
/* 126:    */       throws IOException, InterruptedException
/* 127:    */     {
/* 128:167 */       Chain.KeyValuePair<KEYIN, VALUEIN> kv = null;
/* 129:    */       
/* 130:    */ 
/* 131:170 */       kv = (Chain.KeyValuePair)this.inputQueue.dequeue();
/* 132:171 */       if (kv.endOfInput) {
/* 133:172 */         return false;
/* 134:    */       }
/* 135:174 */       this.key = ReflectionUtils.newInstance(this.keyClass, this.conf);
/* 136:175 */       this.value = ReflectionUtils.newInstance(this.valueClass, this.conf);
/* 137:176 */       ReflectionUtils.copy(this.conf, kv.key, this.key);
/* 138:177 */       ReflectionUtils.copy(this.conf, kv.value, this.value);
/* 139:178 */       return true;
/* 140:    */     }
/* 141:    */     
/* 142:    */     public KEYIN getCurrentKey()
/* 143:    */       throws IOException, InterruptedException
/* 144:    */     {
/* 145:189 */       return this.key;
/* 146:    */     }
/* 147:    */     
/* 148:    */     public VALUEIN getCurrentValue()
/* 149:    */       throws IOException, InterruptedException
/* 150:    */     {
/* 151:200 */       return this.value;
/* 152:    */     }
/* 153:    */     
/* 154:    */     public void close()
/* 155:    */       throws IOException
/* 156:    */     {}
/* 157:    */     
/* 158:    */     public float getProgress()
/* 159:    */       throws IOException, InterruptedException
/* 160:    */     {
/* 161:209 */       return 0.0F;
/* 162:    */     }
/* 163:    */   }
/* 164:    */   
/* 165:    */   private static class ChainRecordWriter<KEYOUT, VALUEOUT>
/* 166:    */     extends RecordWriter<KEYOUT, VALUEOUT>
/* 167:    */   {
/* 168:217 */     TaskInputOutputContext<?, ?, KEYOUT, VALUEOUT> outputContext = null;
/* 169:218 */     Chain.ChainBlockingQueue<Chain.KeyValuePair<KEYOUT, VALUEOUT>> outputQueue = null;
/* 170:    */     KEYOUT keyout;
/* 171:    */     VALUEOUT valueout;
/* 172:    */     Configuration conf;
/* 173:    */     Class<?> keyClass;
/* 174:    */     Class<?> valueClass;
/* 175:    */     
/* 176:    */     ChainRecordWriter(TaskInputOutputContext<?, ?, KEYOUT, VALUEOUT> context)
/* 177:    */     {
/* 178:227 */       this.outputContext = context;
/* 179:    */     }
/* 180:    */     
/* 181:    */     ChainRecordWriter(Class<?> keyClass, Class<?> valueClass, Chain.ChainBlockingQueue<Chain.KeyValuePair<KEYOUT, VALUEOUT>> output, Configuration conf)
/* 182:    */     {
/* 183:234 */       this.keyClass = keyClass;
/* 184:235 */       this.valueClass = valueClass;
/* 185:236 */       this.outputQueue = output;
/* 186:237 */       this.conf = conf;
/* 187:    */     }
/* 188:    */     
/* 189:    */     public void write(KEYOUT key, VALUEOUT value)
/* 190:    */       throws IOException, InterruptedException
/* 191:    */     {
/* 192:251 */       if (this.outputQueue != null) {
/* 193:252 */         writeToQueue(key, value);
/* 194:    */       } else {
/* 195:254 */         this.outputContext.write(key, value);
/* 196:    */       }
/* 197:    */     }
/* 198:    */     
/* 199:    */     private void writeToQueue(KEYOUT key, VALUEOUT value)
/* 200:    */       throws IOException, InterruptedException
/* 201:    */     {
/* 202:261 */       this.keyout = ReflectionUtils.newInstance(this.keyClass, this.conf);
/* 203:262 */       this.valueout = ReflectionUtils.newInstance(this.valueClass, this.conf);
/* 204:263 */       ReflectionUtils.copy(this.conf, key, this.keyout);
/* 205:264 */       ReflectionUtils.copy(this.conf, value, this.valueout);
/* 206:    */       
/* 207:    */ 
/* 208:267 */       this.outputQueue.enqueue(new Chain.KeyValuePair(this.keyout, this.valueout));
/* 209:    */     }
/* 210:    */     
/* 211:    */     public void close(TaskAttemptContext context)
/* 212:    */       throws IOException, InterruptedException
/* 213:    */     {
/* 214:279 */       if (this.outputQueue != null) {
/* 215:281 */         this.outputQueue.enqueue(new Chain.KeyValuePair(true));
/* 216:    */       }
/* 217:    */     }
/* 218:    */   }
/* 219:    */   
/* 220:    */   private synchronized Throwable getThrowable()
/* 221:    */   {
/* 222:288 */     return this.throwable;
/* 223:    */   }
/* 224:    */   
/* 225:    */   private synchronized boolean setIfUnsetThrowable(Throwable th)
/* 226:    */   {
/* 227:292 */     if (this.throwable == null)
/* 228:    */     {
/* 229:293 */       this.throwable = th;
/* 230:294 */       return true;
/* 231:    */     }
/* 232:296 */     return false;
/* 233:    */   }
/* 234:    */   
/* 235:    */   private class MapRunner<KEYIN, VALUEIN, KEYOUT, VALUEOUT>
/* 236:    */     extends Thread
/* 237:    */   {
/* 238:    */     private Mapper<KEYIN, VALUEIN, KEYOUT, VALUEOUT> mapper;
/* 239:    */     private Mapper<KEYIN, VALUEIN, KEYOUT, VALUEOUT>.Context chainContext;
/* 240:    */     private RecordReader<KEYIN, VALUEIN> rr;
/* 241:    */     private RecordWriter<KEYOUT, VALUEOUT> rw;
/* 242:    */     
/* 243:    */     public MapRunner(Mapper<KEYIN, VALUEIN, KEYOUT, VALUEOUT>.Context mapper, RecordReader<KEYIN, VALUEIN> mapperContext, RecordWriter<KEYOUT, VALUEOUT> rr)
/* 244:    */       throws IOException, InterruptedException
/* 245:    */     {
/* 246:309 */       this.mapper = mapper;
/* 247:310 */       this.rr = rr;
/* 248:311 */       this.rw = rw;
/* 249:312 */       this.chainContext = mapperContext;
/* 250:    */     }
/* 251:    */     
/* 252:    */     public void run()
/* 253:    */     {
/* 254:317 */       if (Chain.this.getThrowable() != null) {
/* 255:318 */         return;
/* 256:    */       }
/* 257:    */       try
/* 258:    */       {
/* 259:321 */         this.mapper.run(this.chainContext);
/* 260:322 */         this.rr.close();
/* 261:323 */         this.rw.close(this.chainContext);
/* 262:    */       }
/* 263:    */       catch (Throwable th)
/* 264:    */       {
/* 265:325 */         if (Chain.this.setIfUnsetThrowable(th)) {
/* 266:326 */           Chain.this.interruptAllThreads();
/* 267:    */         }
/* 268:    */       }
/* 269:    */     }
/* 270:    */   }
/* 271:    */   
/* 272:    */   private class ReduceRunner<KEYIN, VALUEIN, KEYOUT, VALUEOUT>
/* 273:    */     extends Thread
/* 274:    */   {
/* 275:    */     private Reducer<KEYIN, VALUEIN, KEYOUT, VALUEOUT> reducer;
/* 276:    */     private Reducer<KEYIN, VALUEIN, KEYOUT, VALUEOUT>.Context chainContext;
/* 277:    */     private RecordWriter<KEYOUT, VALUEOUT> rw;
/* 278:    */     
/* 279:    */     ReduceRunner(Reducer<KEYIN, VALUEIN, KEYOUT, VALUEOUT> context, RecordWriter<KEYOUT, VALUEOUT> reducer)
/* 280:    */       throws IOException, InterruptedException
/* 281:    */     {
/* 282:341 */       this.reducer = reducer;
/* 283:342 */       this.chainContext = context;
/* 284:343 */       this.rw = rw;
/* 285:    */     }
/* 286:    */     
/* 287:    */     public void run()
/* 288:    */     {
/* 289:    */       try
/* 290:    */       {
/* 291:349 */         this.reducer.run(this.chainContext);
/* 292:350 */         this.rw.close(this.chainContext);
/* 293:    */       }
/* 294:    */       catch (Throwable th)
/* 295:    */       {
/* 296:352 */         if (Chain.this.setIfUnsetThrowable(th)) {
/* 297:353 */           Chain.this.interruptAllThreads();
/* 298:    */         }
/* 299:    */       }
/* 300:    */     }
/* 301:    */   }
/* 302:    */   
/* 303:    */   Configuration getConf(int index)
/* 304:    */   {
/* 305:360 */     return (Configuration)this.confList.get(index);
/* 306:    */   }
/* 307:    */   
/* 308:    */   private <KEYIN, VALUEIN, KEYOUT, VALUEOUT> Mapper<KEYIN, VALUEIN, KEYOUT, VALUEOUT>.Context createMapContext(RecordReader<KEYIN, VALUEIN> rr, RecordWriter<KEYOUT, VALUEOUT> rw, TaskInputOutputContext<KEYIN, VALUEIN, KEYOUT, VALUEOUT> context, Configuration conf)
/* 309:    */   {
/* 310:372 */     MapContext<KEYIN, VALUEIN, KEYOUT, VALUEOUT> mapContext = new ChainMapContextImpl(context, rr, rw, conf);
/* 311:    */     
/* 312:    */ 
/* 313:375 */     Mapper<KEYIN, VALUEIN, KEYOUT, VALUEOUT>.Context mapperContext = new WrappedMapper().getMapContext(mapContext);
/* 314:    */     
/* 315:    */ 
/* 316:378 */     return mapperContext;
/* 317:    */   }
/* 318:    */   
/* 319:    */   void runMapper(TaskInputOutputContext context, int index)
/* 320:    */     throws IOException, InterruptedException
/* 321:    */   {
/* 322:384 */     Mapper mapper = (Mapper)this.mappers.get(index);
/* 323:385 */     RecordReader rr = new ChainRecordReader(context);
/* 324:386 */     RecordWriter rw = new ChainRecordWriter(context);
/* 325:387 */     Mapper.Context mapperContext = createMapContext(rr, rw, context, getConf(index));
/* 326:    */     
/* 327:389 */     mapper.run(mapperContext);
/* 328:390 */     rr.close();
/* 329:391 */     rw.close(context);
/* 330:    */   }
/* 331:    */   
/* 332:    */   void addMapper(TaskInputOutputContext inputContext, ChainBlockingQueue<KeyValuePair<?, ?>> output, int index)
/* 333:    */     throws IOException, InterruptedException
/* 334:    */   {
/* 335:402 */     Configuration conf = getConf(index);
/* 336:403 */     Class<?> keyOutClass = conf.getClass("mapreduce.chain.mapper.output.key.class", Object.class);
/* 337:404 */     Class<?> valueOutClass = conf.getClass("mapreduce.chain.mapper.output.value.class", Object.class);
/* 338:    */     
/* 339:    */ 
/* 340:407 */     RecordReader rr = new ChainRecordReader(inputContext);
/* 341:408 */     RecordWriter rw = new ChainRecordWriter(keyOutClass, valueOutClass, output, conf);
/* 342:    */     
/* 343:410 */     Mapper.Context mapperContext = createMapContext(rr, rw, (MapContext)inputContext, getConf(index));
/* 344:    */     
/* 345:412 */     MapRunner runner = new MapRunner((Mapper)this.mappers.get(index), mapperContext, rr, rw);
/* 346:413 */     this.threads.add(runner);
/* 347:    */   }
/* 348:    */   
/* 349:    */   void addMapper(ChainBlockingQueue<KeyValuePair<?, ?>> input, TaskInputOutputContext outputContext, int index)
/* 350:    */     throws IOException, InterruptedException
/* 351:    */   {
/* 352:424 */     Configuration conf = getConf(index);
/* 353:425 */     Class<?> keyClass = conf.getClass("mapreduce.chain.mapper.input.key.class", Object.class);
/* 354:426 */     Class<?> valueClass = conf.getClass("mapreduce.chain.mapper.input.value.class", Object.class);
/* 355:427 */     RecordReader rr = new ChainRecordReader(keyClass, valueClass, input, conf);
/* 356:428 */     RecordWriter rw = new ChainRecordWriter(outputContext);
/* 357:429 */     MapRunner runner = new MapRunner((Mapper)this.mappers.get(index), createMapContext(rr, rw, outputContext, getConf(index)), rr, rw);
/* 358:    */     
/* 359:431 */     this.threads.add(runner);
/* 360:    */   }
/* 361:    */   
/* 362:    */   void addMapper(ChainBlockingQueue<KeyValuePair<?, ?>> input, ChainBlockingQueue<KeyValuePair<?, ?>> output, TaskInputOutputContext context, int index)
/* 363:    */     throws IOException, InterruptedException
/* 364:    */   {
/* 365:442 */     Configuration conf = getConf(index);
/* 366:443 */     Class<?> keyClass = conf.getClass("mapreduce.chain.mapper.input.key.class", Object.class);
/* 367:444 */     Class<?> valueClass = conf.getClass("mapreduce.chain.mapper.input.value.class", Object.class);
/* 368:445 */     Class<?> keyOutClass = conf.getClass("mapreduce.chain.mapper.output.key.class", Object.class);
/* 369:446 */     Class<?> valueOutClass = conf.getClass("mapreduce.chain.mapper.output.value.class", Object.class);
/* 370:    */     
/* 371:448 */     RecordReader rr = new ChainRecordReader(keyClass, valueClass, input, conf);
/* 372:449 */     RecordWriter rw = new ChainRecordWriter(keyOutClass, valueOutClass, output, conf);
/* 373:    */     
/* 374:451 */     MapRunner runner = new MapRunner((Mapper)this.mappers.get(index), createMapContext(rr, rw, context, getConf(index)), rr, rw);
/* 375:    */     
/* 376:453 */     this.threads.add(runner);
/* 377:    */   }
/* 378:    */   
/* 379:    */   private <KEYIN, VALUEIN, KEYOUT, VALUEOUT> Reducer<KEYIN, VALUEIN, KEYOUT, VALUEOUT>.Context createReduceContext(RecordWriter<KEYOUT, VALUEOUT> rw, ReduceContext<KEYIN, VALUEIN, KEYOUT, VALUEOUT> context, Configuration conf)
/* 380:    */   {
/* 381:465 */     ReduceContext<KEYIN, VALUEIN, KEYOUT, VALUEOUT> reduceContext = new ChainReduceContextImpl(context, rw, conf);
/* 382:    */     
/* 383:    */ 
/* 384:468 */     Reducer<KEYIN, VALUEIN, KEYOUT, VALUEOUT>.Context reducerContext = new WrappedReducer().getReducerContext(reduceContext);
/* 385:    */     
/* 386:    */ 
/* 387:471 */     return reducerContext;
/* 388:    */   }
/* 389:    */   
/* 390:    */   <KEYIN, VALUEIN, KEYOUT, VALUEOUT> void runReducer(TaskInputOutputContext<KEYIN, VALUEIN, KEYOUT, VALUEOUT> context)
/* 391:    */     throws IOException, InterruptedException
/* 392:    */   {
/* 393:479 */     RecordWriter<KEYOUT, VALUEOUT> rw = new ChainRecordWriter(context);
/* 394:    */     
/* 395:481 */     Reducer.Context reducerContext = createReduceContext(rw, (ReduceContext)context, this.rConf);
/* 396:    */     
/* 397:483 */     this.reducer.run(reducerContext);
/* 398:484 */     rw.close(context);
/* 399:    */   }
/* 400:    */   
/* 401:    */   void addReducer(TaskInputOutputContext inputContext, ChainBlockingQueue<KeyValuePair<?, ?>> outputQueue)
/* 402:    */     throws IOException, InterruptedException
/* 403:    */   {
/* 404:495 */     Class<?> keyOutClass = this.rConf.getClass("mapreduce.chain.reducer.output.key.class", Object.class);
/* 405:    */     
/* 406:497 */     Class<?> valueOutClass = this.rConf.getClass("mapreduce.chain.reducer.output.value.class", Object.class);
/* 407:    */     
/* 408:499 */     RecordWriter rw = new ChainRecordWriter(keyOutClass, valueOutClass, outputQueue, this.rConf);
/* 409:    */     
/* 410:501 */     Reducer.Context reducerContext = createReduceContext(rw, (ReduceContext)inputContext, this.rConf);
/* 411:    */     
/* 412:503 */     ReduceRunner runner = new ReduceRunner(reducerContext, this.reducer, rw);
/* 413:504 */     this.threads.add(runner);
/* 414:    */   }
/* 415:    */   
/* 416:    */   void startAllThreads()
/* 417:    */   {
/* 418:509 */     for (Thread thread : this.threads) {
/* 419:510 */       thread.start();
/* 420:    */     }
/* 421:    */   }
/* 422:    */   
/* 423:    */   void joinAllThreads()
/* 424:    */     throws IOException, InterruptedException
/* 425:    */   {
/* 426:516 */     for (Thread thread : this.threads) {
/* 427:517 */       thread.join();
/* 428:    */     }
/* 429:519 */     Throwable th = getThrowable();
/* 430:520 */     if (th != null)
/* 431:    */     {
/* 432:521 */       if ((th instanceof IOException)) {
/* 433:522 */         throw ((IOException)th);
/* 434:    */       }
/* 435:523 */       if ((th instanceof InterruptedException)) {
/* 436:524 */         throw ((InterruptedException)th);
/* 437:    */       }
/* 438:526 */       throw new RuntimeException(th);
/* 439:    */     }
/* 440:    */   }
/* 441:    */   
/* 442:    */   private synchronized void interruptAllThreads()
/* 443:    */   {
/* 444:533 */     for (Thread th : this.threads) {
/* 445:534 */       th.interrupt();
/* 446:    */     }
/* 447:536 */     for (ChainBlockingQueue<?> queue : this.blockingQueues) {
/* 448:537 */       queue.interrupt();
/* 449:    */     }
/* 450:    */   }
/* 451:    */   
/* 452:    */   protected static String getPrefix(boolean isMap)
/* 453:    */   {
/* 454:550 */     return isMap ? "mapreduce.chain.mapper" : "mapreduce.chain.reducer";
/* 455:    */   }
/* 456:    */   
/* 457:    */   protected static int getIndex(Configuration conf, String prefix)
/* 458:    */   {
/* 459:554 */     return conf.getInt(prefix + ".size", 0);
/* 460:    */   }
/* 461:    */   
/* 462:    */   protected static Configuration getChainElementConf(Configuration jobConf, String confKey)
/* 463:    */   {
/* 464:577 */     Configuration conf = null;
/* 465:    */     try
/* 466:    */     {
/* 467:579 */       Stringifier<Configuration> stringifier = new DefaultStringifier(jobConf, Configuration.class);
/* 468:    */       
/* 469:581 */       String confString = jobConf.get(confKey, null);
/* 470:582 */       if (confString != null) {
/* 471:583 */         conf = (Configuration)stringifier.fromString(jobConf.get(confKey, null));
/* 472:    */       }
/* 473:    */     }
/* 474:    */     catch (IOException ioex)
/* 475:    */     {
/* 476:586 */       throw new RuntimeException(ioex);
/* 477:    */     }
/* 478:591 */     jobConf = new Configuration(jobConf);
/* 479:593 */     if (conf != null) {
/* 480:594 */       for (Map.Entry<String, String> entry : conf) {
/* 481:595 */         jobConf.set((String)entry.getKey(), (String)entry.getValue());
/* 482:    */       }
/* 483:    */     }
/* 484:598 */     return jobConf;
/* 485:    */   }
/* 486:    */   
/* 487:    */   protected static void addMapper(boolean isMap, Job job, Class<? extends Mapper> klass, Class<?> inputKeyClass, Class<?> inputValueClass, Class<?> outputKeyClass, Class<?> outputValueClass, Configuration mapperConf)
/* 488:    */   {
/* 489:633 */     String prefix = getPrefix(isMap);
/* 490:634 */     Configuration jobConf = job.getConfiguration();
/* 491:    */     
/* 492:    */ 
/* 493:637 */     checkReducerAlreadySet(isMap, jobConf, prefix, true);
/* 494:    */     
/* 495:    */ 
/* 496:640 */     int index = getIndex(jobConf, prefix);
/* 497:641 */     jobConf.setClass(prefix + ".mapper.class." + index, klass, Mapper.class);
/* 498:    */     
/* 499:643 */     validateKeyValueTypes(isMap, jobConf, inputKeyClass, inputValueClass, outputKeyClass, outputValueClass, index, prefix);
/* 500:    */     
/* 501:    */ 
/* 502:646 */     setMapperConf(isMap, jobConf, inputKeyClass, inputValueClass, outputKeyClass, outputValueClass, mapperConf, index, prefix);
/* 503:    */   }
/* 504:    */   
/* 505:    */   protected static void checkReducerAlreadySet(boolean isMap, Configuration jobConf, String prefix, boolean shouldSet)
/* 506:    */   {
/* 507:653 */     if (!isMap) {
/* 508:654 */       if (shouldSet)
/* 509:    */       {
/* 510:655 */         if (jobConf.getClass(prefix + ".reducer.class", null) == null) {
/* 511:656 */           throw new IllegalStateException("A Mapper can be added to the chain only after the Reducer has been set");
/* 512:    */         }
/* 513:    */       }
/* 514:661 */       else if (jobConf.getClass(prefix + ".reducer.class", null) != null) {
/* 515:662 */         throw new IllegalStateException("Reducer has been already set");
/* 516:    */       }
/* 517:    */     }
/* 518:    */   }
/* 519:    */   
/* 520:    */   protected static void validateKeyValueTypes(boolean isMap, Configuration jobConf, Class<?> inputKeyClass, Class<?> inputValueClass, Class<?> outputKeyClass, Class<?> outputValueClass, int index, String prefix)
/* 521:    */   {
/* 522:675 */     if ((!isMap) && (index == 0))
/* 523:    */     {
/* 524:676 */       Configuration reducerConf = getChainElementConf(jobConf, prefix + ".reducer.config");
/* 525:678 */       if (!inputKeyClass.isAssignableFrom(reducerConf.getClass("mapreduce.chain.reducer.output.key.class", null))) {
/* 526:680 */         throw new IllegalArgumentException("The Reducer output key class does not match the Mapper input key class");
/* 527:    */       }
/* 528:683 */       if (!inputValueClass.isAssignableFrom(reducerConf.getClass("mapreduce.chain.reducer.output.value.class", null))) {
/* 529:685 */         throw new IllegalArgumentException("The Reducer output value class does not match the Mapper input value class");
/* 530:    */       }
/* 531:    */     }
/* 532:688 */     else if (index > 0)
/* 533:    */     {
/* 534:691 */       Configuration previousMapperConf = getChainElementConf(jobConf, prefix + ".mapper.config." + (index - 1));
/* 535:693 */       if (!inputKeyClass.isAssignableFrom(previousMapperConf.getClass("mapreduce.chain.mapper.output.key.class", null))) {
/* 536:695 */         throw new IllegalArgumentException("The specified Mapper input key class does not match the previous Mapper's output key class.");
/* 537:    */       }
/* 538:698 */       if (!inputValueClass.isAssignableFrom(previousMapperConf.getClass("mapreduce.chain.mapper.output.value.class", null))) {
/* 539:700 */         throw new IllegalArgumentException("The specified Mapper input value class does not match the previous Mapper's output value class.");
/* 540:    */       }
/* 541:    */     }
/* 542:    */   }
/* 543:    */   
/* 544:    */   protected static void setMapperConf(boolean isMap, Configuration jobConf, Class<?> inputKeyClass, Class<?> inputValueClass, Class<?> outputKeyClass, Class<?> outputValueClass, Configuration mapperConf, int index, String prefix)
/* 545:    */   {
/* 546:711 */     if (mapperConf == null) {
/* 547:715 */       mapperConf = new Configuration(true);
/* 548:    */     }
/* 549:719 */     mapperConf.setClass("mapreduce.chain.mapper.input.key.class", inputKeyClass, Object.class);
/* 550:720 */     mapperConf.setClass("mapreduce.chain.mapper.input.value.class", inputValueClass, Object.class);
/* 551:    */     
/* 552:722 */     mapperConf.setClass("mapreduce.chain.mapper.output.key.class", outputKeyClass, Object.class);
/* 553:723 */     mapperConf.setClass("mapreduce.chain.mapper.output.value.class", outputValueClass, Object.class);
/* 554:    */     
/* 555:    */ 
/* 556:726 */     Stringifier<Configuration> stringifier = new DefaultStringifier(jobConf, Configuration.class);
/* 557:    */     try
/* 558:    */     {
/* 559:729 */       jobConf.set(prefix + ".mapper.config." + index, stringifier.toString(new Configuration(mapperConf)));
/* 560:    */     }
/* 561:    */     catch (IOException ioEx)
/* 562:    */     {
/* 563:732 */       throw new RuntimeException(ioEx);
/* 564:    */     }
/* 565:736 */     jobConf.setInt(prefix + ".size", index + 1);
/* 566:    */   }
/* 567:    */   
/* 568:    */   protected static void setReducer(Job job, Class<? extends Reducer> klass, Class<?> inputKeyClass, Class<?> inputValueClass, Class<?> outputKeyClass, Class<?> outputValueClass, Configuration reducerConf)
/* 569:    */   {
/* 570:769 */     String prefix = getPrefix(false);
/* 571:770 */     Configuration jobConf = job.getConfiguration();
/* 572:771 */     checkReducerAlreadySet(false, jobConf, prefix, false);
/* 573:    */     
/* 574:773 */     jobConf.setClass(prefix + ".reducer.class", klass, Reducer.class);
/* 575:    */     
/* 576:775 */     setReducerConf(jobConf, inputKeyClass, inputValueClass, outputKeyClass, outputValueClass, reducerConf, prefix);
/* 577:    */   }
/* 578:    */   
/* 579:    */   protected static void setReducerConf(Configuration jobConf, Class<?> inputKeyClass, Class<?> inputValueClass, Class<?> outputKeyClass, Class<?> outputValueClass, Configuration reducerConf, String prefix)
/* 580:    */   {
/* 581:784 */     if (reducerConf == null) {
/* 582:788 */       reducerConf = new Configuration(false);
/* 583:    */     }
/* 584:793 */     reducerConf.setClass("mapreduce.chain.reducer.input.key.class", inputKeyClass, Object.class);
/* 585:794 */     reducerConf.setClass("maperduce.chain.reducer.input.value.class", inputValueClass, Object.class);
/* 586:    */     
/* 587:796 */     reducerConf.setClass("mapreduce.chain.reducer.output.key.class", outputKeyClass, Object.class);
/* 588:    */     
/* 589:798 */     reducerConf.setClass("mapreduce.chain.reducer.output.value.class", outputValueClass, Object.class);
/* 590:    */     
/* 591:    */ 
/* 592:    */ 
/* 593:802 */     Stringifier<Configuration> stringifier = new DefaultStringifier(jobConf, Configuration.class);
/* 594:    */     try
/* 595:    */     {
/* 596:805 */       jobConf.set(prefix + ".reducer.config", stringifier.toString(new Configuration(reducerConf)));
/* 597:    */     }
/* 598:    */     catch (IOException ioEx)
/* 599:    */     {
/* 600:808 */       throw new RuntimeException(ioEx);
/* 601:    */     }
/* 602:    */   }
/* 603:    */   
/* 604:    */   void setup(Configuration jobConf)
/* 605:    */   {
/* 606:820 */     String prefix = getPrefix(this.isMap);
/* 607:    */     
/* 608:822 */     int index = jobConf.getInt(prefix + ".size", 0);
/* 609:823 */     for (int i = 0; i < index; i++)
/* 610:    */     {
/* 611:824 */       Class<? extends Mapper> klass = jobConf.getClass(prefix + ".mapper.class." + i, null, Mapper.class);
/* 612:    */       
/* 613:826 */       Configuration mConf = getChainElementConf(jobConf, prefix + ".mapper.config." + i);
/* 614:    */       
/* 615:828 */       this.confList.add(mConf);
/* 616:829 */       Mapper mapper = (Mapper)ReflectionUtils.newInstance(klass, mConf);
/* 617:830 */       this.mappers.add(mapper);
/* 618:    */     }
/* 619:834 */     Class<? extends Reducer> klass = jobConf.getClass(prefix + ".reducer.class", null, Reducer.class);
/* 620:836 */     if (klass != null)
/* 621:    */     {
/* 622:837 */       this.rConf = getChainElementConf(jobConf, prefix + ".reducer.config");
/* 623:838 */       this.reducer = ((Reducer)ReflectionUtils.newInstance(klass, this.rConf));
/* 624:    */     }
/* 625:    */   }
/* 626:    */   
/* 627:    */   List<Mapper> getAllMappers()
/* 628:    */   {
/* 629:844 */     return this.mappers;
/* 630:    */   }
/* 631:    */   
/* 632:    */   Reducer<?, ?, ?, ?> getReducer()
/* 633:    */   {
/* 634:853 */     return this.reducer;
/* 635:    */   }
/* 636:    */   
/* 637:    */   ChainBlockingQueue<KeyValuePair<?, ?>> createBlockingQueue()
/* 638:    */   {
/* 639:862 */     return new ChainBlockingQueue();
/* 640:    */   }
/* 641:    */   
/* 642:    */   class ChainBlockingQueue<E>
/* 643:    */   {
/* 644:871 */     E element = null;
/* 645:872 */     boolean isInterrupted = false;
/* 646:    */     
/* 647:    */     ChainBlockingQueue()
/* 648:    */     {
/* 649:875 */       Chain.this.blockingQueues.add(this);
/* 650:    */     }
/* 651:    */     
/* 652:    */     synchronized void enqueue(E e)
/* 653:    */       throws InterruptedException
/* 654:    */     {
/* 655:879 */       while (this.element != null)
/* 656:    */       {
/* 657:880 */         if (this.isInterrupted) {
/* 658:881 */           throw new InterruptedException();
/* 659:    */         }
/* 660:883 */         wait();
/* 661:    */       }
/* 662:885 */       this.element = e;
/* 663:886 */       notify();
/* 664:    */     }
/* 665:    */     
/* 666:    */     synchronized E dequeue()
/* 667:    */       throws InterruptedException
/* 668:    */     {
/* 669:890 */       while (this.element == null)
/* 670:    */       {
/* 671:891 */         if (this.isInterrupted) {
/* 672:892 */           throw new InterruptedException();
/* 673:    */         }
/* 674:894 */         wait();
/* 675:    */       }
/* 676:896 */       E e = this.element;
/* 677:897 */       this.element = null;
/* 678:898 */       notify();
/* 679:899 */       return e;
/* 680:    */     }
/* 681:    */     
/* 682:    */     synchronized void interrupt()
/* 683:    */     {
/* 684:903 */       this.isInterrupted = true;
/* 685:904 */       notifyAll();
/* 686:    */     }
/* 687:    */   }
/* 688:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.lib.chain.Chain
 * JD-Core Version:    0.7.0.1
 */