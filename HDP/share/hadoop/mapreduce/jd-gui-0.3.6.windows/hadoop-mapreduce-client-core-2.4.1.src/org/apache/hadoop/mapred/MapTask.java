/*    1:     */ package org.apache.hadoop.mapred;
/*    2:     */ 
/*    3:     */ import java.io.DataInput;
/*    4:     */ import java.io.DataOutput;
/*    5:     */ import java.io.DataOutputStream;
/*    6:     */ import java.io.File;
/*    7:     */ import java.io.IOException;
/*    8:     */ import java.io.OutputStream;
/*    9:     */ import java.nio.ByteBuffer;
/*   10:     */ import java.nio.ByteOrder;
/*   11:     */ import java.nio.IntBuffer;
/*   12:     */ import java.util.ArrayList;
/*   13:     */ import java.util.List;
/*   14:     */ import java.util.concurrent.locks.Condition;
/*   15:     */ import java.util.concurrent.locks.ReentrantLock;
/*   16:     */ import org.apache.commons.logging.Log;
/*   17:     */ import org.apache.commons.logging.LogFactory;
/*   18:     */ import org.apache.hadoop.classification.InterfaceAudience.LimitedPrivate;
/*   19:     */ import org.apache.hadoop.classification.InterfaceStability.Unstable;
/*   20:     */ import org.apache.hadoop.fs.FSDataInputStream;
/*   21:     */ import org.apache.hadoop.fs.FSDataOutputStream;
/*   22:     */ import org.apache.hadoop.fs.FileStatus;
/*   23:     */ import org.apache.hadoop.fs.FileSystem;
/*   24:     */ import org.apache.hadoop.fs.FileSystem.Statistics;
/*   25:     */ import org.apache.hadoop.fs.LocalFileSystem;
/*   26:     */ import org.apache.hadoop.fs.Path;
/*   27:     */ import org.apache.hadoop.fs.RawLocalFileSystem;
/*   28:     */ import org.apache.hadoop.io.DataInputBuffer;
/*   29:     */ import org.apache.hadoop.io.RawComparator;
/*   30:     */ import org.apache.hadoop.io.SequenceFile;
/*   31:     */ import org.apache.hadoop.io.SequenceFile.CompressionType;
/*   32:     */ import org.apache.hadoop.io.SequenceFile.Writer;
/*   33:     */ import org.apache.hadoop.io.Text;
/*   34:     */ import org.apache.hadoop.io.compress.CompressionCodec;
/*   35:     */ import org.apache.hadoop.io.compress.DefaultCodec;
/*   36:     */ import org.apache.hadoop.io.serializer.Deserializer;
/*   37:     */ import org.apache.hadoop.io.serializer.SerializationFactory;
/*   38:     */ import org.apache.hadoop.io.serializer.Serializer;
/*   39:     */ import org.apache.hadoop.mapreduce.Counter;
/*   40:     */ import org.apache.hadoop.mapreduce.JobContext;
/*   41:     */ import org.apache.hadoop.mapreduce.MRJobConfig;
/*   42:     */ import org.apache.hadoop.mapreduce.MapContext;
/*   43:     */ import org.apache.hadoop.mapreduce.Mapper;
/*   44:     */ import org.apache.hadoop.mapreduce.TaskCounter;
/*   45:     */ import org.apache.hadoop.mapreduce.TaskType;
/*   46:     */ import org.apache.hadoop.mapreduce.lib.input.FileInputFormatCounter;
/*   47:     */ import org.apache.hadoop.mapreduce.lib.map.WrappedMapper;
/*   48:     */ import org.apache.hadoop.mapreduce.lib.output.FileOutputFormatCounter;
/*   49:     */ import org.apache.hadoop.mapreduce.split.JobSplit.TaskSplitIndex;
/*   50:     */ import org.apache.hadoop.mapreduce.task.MapContextImpl;
/*   51:     */ import org.apache.hadoop.mapreduce.task.TaskAttemptContextImpl;
/*   52:     */ import org.apache.hadoop.util.IndexedSortable;
/*   53:     */ import org.apache.hadoop.util.IndexedSorter;
/*   54:     */ import org.apache.hadoop.util.Progress;
/*   55:     */ import org.apache.hadoop.util.QuickSort;
/*   56:     */ import org.apache.hadoop.util.ReflectionUtils;
/*   57:     */ import org.apache.hadoop.util.StringInterner;
/*   58:     */ import org.apache.hadoop.util.StringUtils;
/*   59:     */ 
/*   60:     */ @InterfaceAudience.LimitedPrivate({"MapReduce"})
/*   61:     */ @InterfaceStability.Unstable
/*   62:     */ public class MapTask
/*   63:     */   extends Task
/*   64:     */ {
/*   65:     */   public static final int MAP_OUTPUT_INDEX_RECORD_LENGTH = 24;
/*   66:  86 */   private JobSplit.TaskSplitIndex splitMetaInfo = new JobSplit.TaskSplitIndex();
/*   67:     */   private static final int APPROX_HEADER_LENGTH = 150;
/*   68:  89 */   private static final Log LOG = LogFactory.getLog(MapTask.class.getName());
/*   69:     */   private Progress mapPhase;
/*   70:     */   private Progress sortPhase;
/*   71:     */   
/*   72:     */   public MapTask()
/*   73:     */   {
/*   74:  95 */     setPhase(TaskStatus.Phase.MAP);
/*   75:  96 */     getProgress().setStatus("map");
/*   76:     */   }
/*   77:     */   
/*   78:     */   public MapTask(String jobFile, TaskAttemptID taskId, int partition, JobSplit.TaskSplitIndex splitIndex, int numSlotsRequired)
/*   79:     */   {
/*   80: 106 */     super(jobFile, taskId, partition, numSlotsRequired);setPhase(TaskStatus.Phase.MAP);getProgress().setStatus("map");
/*   81: 107 */     this.splitMetaInfo = splitIndex;
/*   82:     */   }
/*   83:     */   
/*   84:     */   public boolean isMapTask()
/*   85:     */   {
/*   86: 112 */     return true;
/*   87:     */   }
/*   88:     */   
/*   89:     */   public void localizeConfiguration(JobConf conf)
/*   90:     */     throws IOException
/*   91:     */   {
/*   92: 118 */     super.localizeConfiguration(conf);
/*   93:     */   }
/*   94:     */   
/*   95:     */   public void write(DataOutput out)
/*   96:     */     throws IOException
/*   97:     */   {
/*   98: 123 */     super.write(out);
/*   99: 124 */     if (isMapOrReduce())
/*  100:     */     {
/*  101: 125 */       this.splitMetaInfo.write(out);
/*  102: 126 */       this.splitMetaInfo = null;
/*  103:     */     }
/*  104:     */   }
/*  105:     */   
/*  106:     */   public void readFields(DataInput in)
/*  107:     */     throws IOException
/*  108:     */   {
/*  109: 132 */     super.readFields(in);
/*  110: 133 */     if (isMapOrReduce()) {
/*  111: 134 */       this.splitMetaInfo.readFields(in);
/*  112:     */     }
/*  113:     */   }
/*  114:     */   
/*  115:     */   class TrackedRecordReader<K, V>
/*  116:     */     implements RecordReader<K, V>
/*  117:     */   {
/*  118:     */     private RecordReader<K, V> rawIn;
/*  119:     */     private Counters.Counter fileInputByteCounter;
/*  120:     */     private Counters.Counter inputRecordCounter;
/*  121:     */     private Task.TaskReporter reporter;
/*  122: 150 */     private long bytesInPrev = -1L;
/*  123: 151 */     private long bytesInCurr = -1L;
/*  124:     */     private final List<FileSystem.Statistics> fsStats;
/*  125:     */     
/*  126:     */     TrackedRecordReader(Task.TaskReporter reporter, JobConf job)
/*  127:     */       throws IOException
/*  128:     */     {
/*  129: 156 */       this.inputRecordCounter = reporter.getCounter(TaskCounter.MAP_INPUT_RECORDS);
/*  130: 157 */       this.fileInputByteCounter = reporter.getCounter(FileInputFormatCounter.BYTES_READ);
/*  131: 158 */       this.reporter = reporter;
/*  132:     */       
/*  133: 160 */       List<FileSystem.Statistics> matchedStats = null;
/*  134: 161 */       if ((this.reporter.getInputSplit() instanceof FileSplit)) {
/*  135: 162 */         matchedStats = Task.getFsStatistics(((FileSplit)this.reporter.getInputSplit()).getPath(), job);
/*  136:     */       }
/*  137: 165 */       this.fsStats = matchedStats;
/*  138:     */       
/*  139: 167 */       this.bytesInPrev = getInputBytes(this.fsStats);
/*  140: 168 */       this.rawIn = job.getInputFormat().getRecordReader(reporter.getInputSplit(), job, reporter);
/*  141:     */       
/*  142: 170 */       this.bytesInCurr = getInputBytes(this.fsStats);
/*  143: 171 */       this.fileInputByteCounter.increment(this.bytesInCurr - this.bytesInPrev);
/*  144:     */     }
/*  145:     */     
/*  146:     */     public K createKey()
/*  147:     */     {
/*  148: 175 */       return this.rawIn.createKey();
/*  149:     */     }
/*  150:     */     
/*  151:     */     public V createValue()
/*  152:     */     {
/*  153: 179 */       return this.rawIn.createValue();
/*  154:     */     }
/*  155:     */     
/*  156:     */     public synchronized boolean next(K key, V value)
/*  157:     */       throws IOException
/*  158:     */     {
/*  159: 184 */       boolean ret = moveToNext(key, value);
/*  160: 185 */       if (ret) {
/*  161: 186 */         incrCounters();
/*  162:     */       }
/*  163: 188 */       return ret;
/*  164:     */     }
/*  165:     */     
/*  166:     */     protected void incrCounters()
/*  167:     */     {
/*  168: 192 */       this.inputRecordCounter.increment(1L);
/*  169:     */     }
/*  170:     */     
/*  171:     */     protected synchronized boolean moveToNext(K key, V value)
/*  172:     */       throws IOException
/*  173:     */     {
/*  174: 197 */       this.bytesInPrev = getInputBytes(this.fsStats);
/*  175: 198 */       boolean ret = this.rawIn.next(key, value);
/*  176: 199 */       this.bytesInCurr = getInputBytes(this.fsStats);
/*  177: 200 */       this.fileInputByteCounter.increment(this.bytesInCurr - this.bytesInPrev);
/*  178: 201 */       this.reporter.setProgress(getProgress());
/*  179: 202 */       return ret;
/*  180:     */     }
/*  181:     */     
/*  182:     */     public long getPos()
/*  183:     */       throws IOException
/*  184:     */     {
/*  185: 205 */       return this.rawIn.getPos();
/*  186:     */     }
/*  187:     */     
/*  188:     */     public void close()
/*  189:     */       throws IOException
/*  190:     */     {
/*  191: 208 */       this.bytesInPrev = getInputBytes(this.fsStats);
/*  192: 209 */       this.rawIn.close();
/*  193: 210 */       this.bytesInCurr = getInputBytes(this.fsStats);
/*  194: 211 */       this.fileInputByteCounter.increment(this.bytesInCurr - this.bytesInPrev);
/*  195:     */     }
/*  196:     */     
/*  197:     */     public float getProgress()
/*  198:     */       throws IOException
/*  199:     */     {
/*  200: 215 */       return this.rawIn.getProgress();
/*  201:     */     }
/*  202:     */     
/*  203:     */     Task.TaskReporter getTaskReporter()
/*  204:     */     {
/*  205: 218 */       return this.reporter;
/*  206:     */     }
/*  207:     */     
/*  208:     */     private long getInputBytes(List<FileSystem.Statistics> stats)
/*  209:     */     {
/*  210: 222 */       if (stats == null) {
/*  211: 222 */         return 0L;
/*  212:     */       }
/*  213: 223 */       long bytesRead = 0L;
/*  214: 224 */       for (FileSystem.Statistics stat : stats) {
/*  215: 225 */         bytesRead += stat.getBytesRead();
/*  216:     */       }
/*  217: 227 */       return bytesRead;
/*  218:     */     }
/*  219:     */   }
/*  220:     */   
/*  221:     */   class SkippingRecordReader<K, V>
/*  222:     */     extends MapTask.TrackedRecordReader<K, V>
/*  223:     */   {
/*  224:     */     private SortedRanges.SkipRangeIterator skipIt;
/*  225:     */     private SequenceFile.Writer skipWriter;
/*  226:     */     private boolean toWriteSkipRecs;
/*  227:     */     private TaskUmbilicalProtocol umbilical;
/*  228:     */     private Counters.Counter skipRecCounter;
/*  229: 241 */     private long recIndex = -1L;
/*  230:     */     
/*  231:     */     SkippingRecordReader(TaskUmbilicalProtocol umbilical, Task.TaskReporter reporter, JobConf job)
/*  232:     */       throws IOException
/*  233:     */     {
/*  234: 245 */       super(reporter, job);
/*  235: 246 */       this.umbilical = umbilical;
/*  236: 247 */       this.skipRecCounter = reporter.getCounter(TaskCounter.MAP_SKIPPED_RECORDS);
/*  237: 248 */       this.toWriteSkipRecs = ((MapTask.this.toWriteSkipRecs()) && (SkipBadRecords.getSkipOutputPath(MapTask.this.conf) != null));
/*  238:     */       
/*  239: 250 */       this.skipIt = MapTask.this.getSkipRanges().skipRangeIterator();
/*  240:     */     }
/*  241:     */     
/*  242:     */     public synchronized boolean next(K key, V value)
/*  243:     */       throws IOException
/*  244:     */     {
/*  245: 255 */       if (!this.skipIt.hasNext())
/*  246:     */       {
/*  247: 256 */         MapTask.LOG.warn("Further records got skipped.");
/*  248: 257 */         return false;
/*  249:     */       }
/*  250: 259 */       boolean ret = moveToNext(key, value);
/*  251: 260 */       long nextRecIndex = this.skipIt.next().longValue();
/*  252: 261 */       long skip = 0L;
/*  253: 262 */       while ((this.recIndex < nextRecIndex) && (ret))
/*  254:     */       {
/*  255: 263 */         if (this.toWriteSkipRecs) {
/*  256: 264 */           writeSkippedRec(key, value);
/*  257:     */         }
/*  258: 266 */         ret = moveToNext(key, value);
/*  259: 267 */         skip += 1L;
/*  260:     */       }
/*  261: 270 */       if ((skip > 0L) && (this.skipIt.skippedAllRanges()) && (this.skipWriter != null)) {
/*  262: 271 */         this.skipWriter.close();
/*  263:     */       }
/*  264: 273 */       this.skipRecCounter.increment(skip);
/*  265: 274 */       MapTask.this.reportNextRecordRange(this.umbilical, this.recIndex);
/*  266: 275 */       if (ret) {
/*  267: 276 */         incrCounters();
/*  268:     */       }
/*  269: 278 */       return ret;
/*  270:     */     }
/*  271:     */     
/*  272:     */     protected synchronized boolean moveToNext(K key, V value)
/*  273:     */       throws IOException
/*  274:     */     {
/*  275: 283 */       this.recIndex += 1L;
/*  276: 284 */       return super.moveToNext(key, value);
/*  277:     */     }
/*  278:     */     
/*  279:     */     private void writeSkippedRec(K key, V value)
/*  280:     */       throws IOException
/*  281:     */     {
/*  282: 289 */       if (this.skipWriter == null)
/*  283:     */       {
/*  284: 290 */         Path skipDir = SkipBadRecords.getSkipOutputPath(MapTask.this.conf);
/*  285: 291 */         Path skipFile = new Path(skipDir, MapTask.this.getTaskID().toString());
/*  286: 292 */         this.skipWriter = SequenceFile.createWriter(skipFile.getFileSystem(MapTask.this.conf), MapTask.this.conf, skipFile, createKey().getClass(), createValue().getClass(), SequenceFile.CompressionType.BLOCK, getTaskReporter());
/*  287:     */       }
/*  288: 299 */       this.skipWriter.append(key, value);
/*  289:     */     }
/*  290:     */   }
/*  291:     */   
/*  292:     */   public void run(JobConf job, TaskUmbilicalProtocol umbilical)
/*  293:     */     throws IOException, ClassNotFoundException, InterruptedException
/*  294:     */   {
/*  295: 306 */     this.umbilical = umbilical;
/*  296: 308 */     if (isMapTask()) {
/*  297: 311 */       if (this.conf.getNumReduceTasks() == 0)
/*  298:     */       {
/*  299: 312 */         this.mapPhase = getProgress().addPhase("map", 1.0F);
/*  300:     */       }
/*  301:     */       else
/*  302:     */       {
/*  303: 316 */         this.mapPhase = getProgress().addPhase("map", 0.667F);
/*  304: 317 */         this.sortPhase = getProgress().addPhase("sort", 0.333F);
/*  305:     */       }
/*  306:     */     }
/*  307: 320 */     Task.TaskReporter reporter = startReporter(umbilical);
/*  308:     */     
/*  309: 322 */     boolean useNewApi = job.getUseNewMapper();
/*  310: 323 */     initialize(job, getJobID(), reporter, useNewApi);
/*  311: 326 */     if (this.jobCleanup)
/*  312:     */     {
/*  313: 327 */       runJobCleanupTask(umbilical, reporter);
/*  314: 328 */       return;
/*  315:     */     }
/*  316: 330 */     if (this.jobSetup)
/*  317:     */     {
/*  318: 331 */       runJobSetupTask(umbilical, reporter);
/*  319: 332 */       return;
/*  320:     */     }
/*  321: 334 */     if (this.taskCleanup)
/*  322:     */     {
/*  323: 335 */       runTaskCleanupTask(umbilical, reporter);
/*  324: 336 */       return;
/*  325:     */     }
/*  326: 339 */     if (useNewApi) {
/*  327: 340 */       runNewMapper(job, this.splitMetaInfo, umbilical, reporter);
/*  328:     */     } else {
/*  329: 342 */       runOldMapper(job, this.splitMetaInfo, umbilical, reporter);
/*  330:     */     }
/*  331: 344 */     done(umbilical, reporter);
/*  332:     */   }
/*  333:     */   
/*  334:     */   public Progress getSortPhase()
/*  335:     */   {
/*  336: 348 */     return this.sortPhase;
/*  337:     */   }
/*  338:     */   
/*  339:     */   private <T> T getSplitDetails(Path file, long offset)
/*  340:     */     throws IOException
/*  341:     */   {
/*  342: 354 */     FileSystem fs = file.getFileSystem(this.conf);
/*  343: 355 */     FSDataInputStream inFile = fs.open(file);
/*  344: 356 */     inFile.seek(offset);
/*  345: 357 */     String className = StringInterner.weakIntern(Text.readString(inFile));
/*  346:     */     Class<T> cls;
/*  347:     */     try
/*  348:     */     {
/*  349: 360 */       cls = this.conf.getClassByName(className);
/*  350:     */     }
/*  351:     */     catch (ClassNotFoundException ce)
/*  352:     */     {
/*  353: 362 */       IOException wrap = new IOException("Split class " + className + " not found");
/*  354:     */       
/*  355: 364 */       wrap.initCause(ce);
/*  356: 365 */       throw wrap;
/*  357:     */     }
/*  358: 367 */     SerializationFactory factory = new SerializationFactory(this.conf);
/*  359: 368 */     Deserializer<T> deserializer = factory.getDeserializer(cls);
/*  360:     */     
/*  361: 370 */     deserializer.open(inFile);
/*  362: 371 */     T split = deserializer.deserialize(null);
/*  363: 372 */     long pos = inFile.getPos();
/*  364: 373 */     ((Counters.Counter)getCounters().findCounter(TaskCounter.SPLIT_RAW_BYTES)).increment(pos - offset);
/*  365:     */     
/*  366: 375 */     inFile.close();
/*  367: 376 */     return split;
/*  368:     */   }
/*  369:     */   
/*  370:     */   private <KEY, VALUE> MapOutputCollector<KEY, VALUE> createSortingCollector(JobConf job, Task.TaskReporter reporter)
/*  371:     */     throws IOException, ClassNotFoundException
/*  372:     */   {
/*  373: 383 */     MapOutputCollector<KEY, VALUE> collector = (MapOutputCollector)ReflectionUtils.newInstance(job.getClass("mapreduce.job.map.output.collector.class", MapOutputBuffer.class, MapOutputCollector.class), job);
/*  374:     */     
/*  375:     */ 
/*  376:     */ 
/*  377:     */ 
/*  378: 388 */     LOG.info("Map output collector class = " + collector.getClass().getName());
/*  379: 389 */     MapOutputCollector.Context context = new MapOutputCollector.Context(this, job, reporter);
/*  380:     */     
/*  381: 391 */     collector.init(context);
/*  382: 392 */     return collector;
/*  383:     */   }
/*  384:     */   
/*  385:     */   private <INKEY, INVALUE, OUTKEY, OUTVALUE> void runOldMapper(JobConf job, JobSplit.TaskSplitIndex splitIndex, TaskUmbilicalProtocol umbilical, Task.TaskReporter reporter)
/*  386:     */     throws IOException, InterruptedException, ClassNotFoundException
/*  387:     */   {
/*  388: 403 */     InputSplit inputSplit = (InputSplit)getSplitDetails(new Path(splitIndex.getSplitLocation()), splitIndex.getStartOffset());
/*  389:     */     
/*  390:     */ 
/*  391: 406 */     updateJobWithSplit(job, inputSplit);
/*  392: 407 */     reporter.setInputSplit(inputSplit);
/*  393:     */     
/*  394: 409 */     RecordReader<INKEY, INVALUE> in = isSkipping() ? new SkippingRecordReader(umbilical, reporter, job) : new TrackedRecordReader(reporter, job);
/*  395:     */     
/*  396:     */ 
/*  397: 412 */     job.setBoolean("mapreduce.job.skiprecords", isSkipping());
/*  398:     */     
/*  399:     */ 
/*  400: 415 */     int numReduceTasks = this.conf.getNumReduceTasks();
/*  401: 416 */     LOG.info("numReduceTasks: " + numReduceTasks);
/*  402: 417 */     MapOutputCollector<OUTKEY, OUTVALUE> collector = null;
/*  403: 418 */     if (numReduceTasks > 0)
/*  404:     */     {
/*  405: 419 */       collector = createSortingCollector(job, reporter);
/*  406:     */     }
/*  407:     */     else
/*  408:     */     {
/*  409: 421 */       collector = new DirectMapOutputCollector();
/*  410: 422 */       MapOutputCollector.Context context = new MapOutputCollector.Context(this, job, reporter);
/*  411:     */       
/*  412: 424 */       collector.init(context);
/*  413:     */     }
/*  414: 426 */     MapRunnable<INKEY, INVALUE, OUTKEY, OUTVALUE> runner = (MapRunnable)ReflectionUtils.newInstance(job.getMapRunnerClass(), job);
/*  415:     */     try
/*  416:     */     {
/*  417: 430 */       runner.run(in, new OldOutputCollector(collector, this.conf), reporter);
/*  418: 431 */       this.mapPhase.complete();
/*  419: 433 */       if (numReduceTasks > 0) {
/*  420: 434 */         setPhase(TaskStatus.Phase.SORT);
/*  421:     */       }
/*  422: 436 */       statusUpdate(umbilical);
/*  423: 437 */       collector.flush();
/*  424:     */       
/*  425: 439 */       in.close();
/*  426: 440 */       in = null;
/*  427:     */       
/*  428: 442 */       collector.close();
/*  429: 443 */       collector = null;
/*  430:     */     }
/*  431:     */     finally
/*  432:     */     {
/*  433: 445 */       closeQuietly(in);
/*  434: 446 */       closeQuietly(collector);
/*  435:     */     }
/*  436:     */   }
/*  437:     */   
/*  438:     */   private void updateJobWithSplit(JobConf job, InputSplit inputSplit)
/*  439:     */   {
/*  440: 456 */     if ((inputSplit instanceof FileSplit))
/*  441:     */     {
/*  442: 457 */       FileSplit fileSplit = (FileSplit)inputSplit;
/*  443: 458 */       job.set("mapreduce.map.input.file", fileSplit.getPath().toString());
/*  444: 459 */       job.setLong("mapreduce.map.input.start", fileSplit.getStart());
/*  445: 460 */       job.setLong("mapreduce.map.input.length", fileSplit.getLength());
/*  446:     */     }
/*  447: 462 */     LOG.info("Processing split: " + inputSplit);
/*  448:     */   }
/*  449:     */   
/*  450:     */   static class NewTrackingRecordReader<K, V>
/*  451:     */     extends org.apache.hadoop.mapreduce.RecordReader<K, V>
/*  452:     */   {
/*  453:     */     private final org.apache.hadoop.mapreduce.RecordReader<K, V> real;
/*  454:     */     private final Counter inputRecordCounter;
/*  455:     */     private final Counter fileInputByteCounter;
/*  456:     */     private final Task.TaskReporter reporter;
/*  457:     */     private final List<FileSystem.Statistics> fsStats;
/*  458:     */     
/*  459:     */     NewTrackingRecordReader(org.apache.hadoop.mapreduce.InputSplit split, org.apache.hadoop.mapreduce.InputFormat<K, V> inputFormat, Task.TaskReporter reporter, org.apache.hadoop.mapreduce.TaskAttemptContext taskContext)
/*  460:     */       throws InterruptedException, IOException
/*  461:     */     {
/*  462: 478 */       this.reporter = reporter;
/*  463: 479 */       this.inputRecordCounter = reporter.getCounter(TaskCounter.MAP_INPUT_RECORDS);
/*  464:     */       
/*  465: 481 */       this.fileInputByteCounter = reporter.getCounter(FileInputFormatCounter.BYTES_READ);
/*  466:     */       
/*  467:     */ 
/*  468: 484 */       List<FileSystem.Statistics> matchedStats = null;
/*  469: 485 */       if ((split instanceof org.apache.hadoop.mapreduce.lib.input.FileSplit)) {
/*  470: 486 */         matchedStats = Task.getFsStatistics(((org.apache.hadoop.mapreduce.lib.input.FileSplit)split).getPath(), taskContext.getConfiguration());
/*  471:     */       }
/*  472: 489 */       this.fsStats = matchedStats;
/*  473:     */       
/*  474: 491 */       long bytesInPrev = getInputBytes(this.fsStats);
/*  475: 492 */       this.real = inputFormat.createRecordReader(split, taskContext);
/*  476: 493 */       long bytesInCurr = getInputBytes(this.fsStats);
/*  477: 494 */       this.fileInputByteCounter.increment(bytesInCurr - bytesInPrev);
/*  478:     */     }
/*  479:     */     
/*  480:     */     public void close()
/*  481:     */       throws IOException
/*  482:     */     {
/*  483: 499 */       long bytesInPrev = getInputBytes(this.fsStats);
/*  484: 500 */       this.real.close();
/*  485: 501 */       long bytesInCurr = getInputBytes(this.fsStats);
/*  486: 502 */       this.fileInputByteCounter.increment(bytesInCurr - bytesInPrev);
/*  487:     */     }
/*  488:     */     
/*  489:     */     public K getCurrentKey()
/*  490:     */       throws IOException, InterruptedException
/*  491:     */     {
/*  492: 507 */       return this.real.getCurrentKey();
/*  493:     */     }
/*  494:     */     
/*  495:     */     public V getCurrentValue()
/*  496:     */       throws IOException, InterruptedException
/*  497:     */     {
/*  498: 512 */       return this.real.getCurrentValue();
/*  499:     */     }
/*  500:     */     
/*  501:     */     public float getProgress()
/*  502:     */       throws IOException, InterruptedException
/*  503:     */     {
/*  504: 517 */       return this.real.getProgress();
/*  505:     */     }
/*  506:     */     
/*  507:     */     public void initialize(org.apache.hadoop.mapreduce.InputSplit split, org.apache.hadoop.mapreduce.TaskAttemptContext context)
/*  508:     */       throws IOException, InterruptedException
/*  509:     */     {
/*  510: 524 */       long bytesInPrev = getInputBytes(this.fsStats);
/*  511: 525 */       this.real.initialize(split, context);
/*  512: 526 */       long bytesInCurr = getInputBytes(this.fsStats);
/*  513: 527 */       this.fileInputByteCounter.increment(bytesInCurr - bytesInPrev);
/*  514:     */     }
/*  515:     */     
/*  516:     */     public boolean nextKeyValue()
/*  517:     */       throws IOException, InterruptedException
/*  518:     */     {
/*  519: 532 */       long bytesInPrev = getInputBytes(this.fsStats);
/*  520: 533 */       boolean result = this.real.nextKeyValue();
/*  521: 534 */       long bytesInCurr = getInputBytes(this.fsStats);
/*  522: 535 */       if (result) {
/*  523: 536 */         this.inputRecordCounter.increment(1L);
/*  524:     */       }
/*  525: 538 */       this.fileInputByteCounter.increment(bytesInCurr - bytesInPrev);
/*  526: 539 */       this.reporter.setProgress(getProgress());
/*  527: 540 */       return result;
/*  528:     */     }
/*  529:     */     
/*  530:     */     private long getInputBytes(List<FileSystem.Statistics> stats)
/*  531:     */     {
/*  532: 544 */       if (stats == null) {
/*  533: 544 */         return 0L;
/*  534:     */       }
/*  535: 545 */       long bytesRead = 0L;
/*  536: 546 */       for (FileSystem.Statistics stat : stats) {
/*  537: 547 */         bytesRead += stat.getBytesRead();
/*  538:     */       }
/*  539: 549 */       return bytesRead;
/*  540:     */     }
/*  541:     */   }
/*  542:     */   
/*  543:     */   private static class OldOutputCollector<K, V>
/*  544:     */     implements OutputCollector<K, V>
/*  545:     */   {
/*  546:     */     private final Partitioner<K, V> partitioner;
/*  547:     */     private final MapOutputCollector<K, V> collector;
/*  548:     */     private final int numPartitions;
/*  549:     */     
/*  550:     */     OldOutputCollector(MapOutputCollector<K, V> collector, JobConf conf)
/*  551:     */     {
/*  552: 567 */       this.numPartitions = conf.getNumReduceTasks();
/*  553: 568 */       if (this.numPartitions > 1) {
/*  554: 569 */         this.partitioner = ((Partitioner)ReflectionUtils.newInstance(conf.getPartitionerClass(), conf));
/*  555:     */       } else {
/*  556: 572 */         this.partitioner = new Partitioner()
/*  557:     */         {
/*  558:     */           public void configure(JobConf job) {}
/*  559:     */           
/*  560:     */           public int getPartition(K key, V value, int numPartitions)
/*  561:     */           {
/*  562: 577 */             return numPartitions - 1;
/*  563:     */           }
/*  564:     */         };
/*  565:     */       }
/*  566: 581 */       this.collector = collector;
/*  567:     */     }
/*  568:     */     
/*  569:     */     public void collect(K key, V value)
/*  570:     */       throws IOException
/*  571:     */     {
/*  572:     */       try
/*  573:     */       {
/*  574: 587 */         this.collector.collect(key, value, this.partitioner.getPartition(key, value, this.numPartitions));
/*  575:     */       }
/*  576:     */       catch (InterruptedException ie)
/*  577:     */       {
/*  578: 590 */         Thread.currentThread().interrupt();
/*  579: 591 */         throw new IOException("interrupt exception", ie);
/*  580:     */       }
/*  581:     */     }
/*  582:     */   }
/*  583:     */   
/*  584:     */   private class NewDirectOutputCollector<K, V>
/*  585:     */     extends org.apache.hadoop.mapreduce.RecordWriter<K, V>
/*  586:     */   {
/*  587:     */     private final org.apache.hadoop.mapreduce.RecordWriter out;
/*  588:     */     private final Task.TaskReporter reporter;
/*  589:     */     private final Counters.Counter mapOutputRecordCounter;
/*  590:     */     private final Counters.Counter fileOutputByteCounter;
/*  591:     */     private final List<FileSystem.Statistics> fsStats;
/*  592:     */     
/*  593:     */     NewDirectOutputCollector(MRJobConfig jobContext, JobConf job, TaskUmbilicalProtocol umbilical, Task.TaskReporter reporter)
/*  594:     */       throws IOException, ClassNotFoundException, InterruptedException
/*  595:     */     {
/*  596: 610 */       this.reporter = reporter;
/*  597: 611 */       this.mapOutputRecordCounter = reporter.getCounter(TaskCounter.MAP_OUTPUT_RECORDS);
/*  598:     */       
/*  599: 613 */       this.fileOutputByteCounter = reporter.getCounter(FileOutputFormatCounter.BYTES_WRITTEN);
/*  600:     */       
/*  601:     */ 
/*  602: 616 */       List<FileSystem.Statistics> matchedStats = null;
/*  603: 617 */       if ((MapTask.this.outputFormat instanceof org.apache.hadoop.mapreduce.lib.output.FileOutputFormat)) {
/*  604: 618 */         matchedStats = Task.getFsStatistics(org.apache.hadoop.mapreduce.lib.output.FileOutputFormat.getOutputPath(MapTask.this.taskContext), MapTask.this.taskContext.getConfiguration());
/*  605:     */       }
/*  606: 621 */       this.fsStats = matchedStats;
/*  607:     */       
/*  608: 623 */       long bytesOutPrev = getOutputBytes(this.fsStats);
/*  609: 624 */       this.out = MapTask.this.outputFormat.getRecordWriter(MapTask.this.taskContext);
/*  610: 625 */       long bytesOutCurr = getOutputBytes(this.fsStats);
/*  611: 626 */       this.fileOutputByteCounter.increment(bytesOutCurr - bytesOutPrev);
/*  612:     */     }
/*  613:     */     
/*  614:     */     public void write(K key, V value)
/*  615:     */       throws IOException, InterruptedException
/*  616:     */     {
/*  617: 633 */       this.reporter.progress();
/*  618: 634 */       long bytesOutPrev = getOutputBytes(this.fsStats);
/*  619: 635 */       this.out.write(key, value);
/*  620: 636 */       long bytesOutCurr = getOutputBytes(this.fsStats);
/*  621: 637 */       this.fileOutputByteCounter.increment(bytesOutCurr - bytesOutPrev);
/*  622: 638 */       this.mapOutputRecordCounter.increment(1L);
/*  623:     */     }
/*  624:     */     
/*  625:     */     public void close(org.apache.hadoop.mapreduce.TaskAttemptContext context)
/*  626:     */       throws IOException, InterruptedException
/*  627:     */     {
/*  628: 644 */       this.reporter.progress();
/*  629: 645 */       if (this.out != null)
/*  630:     */       {
/*  631: 646 */         long bytesOutPrev = getOutputBytes(this.fsStats);
/*  632: 647 */         this.out.close(context);
/*  633: 648 */         long bytesOutCurr = getOutputBytes(this.fsStats);
/*  634: 649 */         this.fileOutputByteCounter.increment(bytesOutCurr - bytesOutPrev);
/*  635:     */       }
/*  636:     */     }
/*  637:     */     
/*  638:     */     private long getOutputBytes(List<FileSystem.Statistics> stats)
/*  639:     */     {
/*  640: 654 */       if (stats == null) {
/*  641: 654 */         return 0L;
/*  642:     */       }
/*  643: 655 */       long bytesWritten = 0L;
/*  644: 656 */       for (FileSystem.Statistics stat : stats) {
/*  645: 657 */         bytesWritten += stat.getBytesWritten();
/*  646:     */       }
/*  647: 659 */       return bytesWritten;
/*  648:     */     }
/*  649:     */   }
/*  650:     */   
/*  651:     */   private class NewOutputCollector<K, V>
/*  652:     */     extends org.apache.hadoop.mapreduce.RecordWriter<K, V>
/*  653:     */   {
/*  654:     */     private final MapOutputCollector<K, V> collector;
/*  655:     */     private final org.apache.hadoop.mapreduce.Partitioner<K, V> partitioner;
/*  656:     */     private final int partitions;
/*  657:     */     
/*  658:     */     NewOutputCollector(JobContext jobContext, JobConf job, TaskUmbilicalProtocol umbilical, Task.TaskReporter reporter)
/*  659:     */       throws IOException, ClassNotFoundException
/*  660:     */     {
/*  661: 675 */       this.collector = MapTask.this.createSortingCollector(job, reporter);
/*  662: 676 */       this.partitions = jobContext.getNumReduceTasks();
/*  663: 677 */       if (this.partitions > 1) {
/*  664: 678 */         this.partitioner = ((org.apache.hadoop.mapreduce.Partitioner)ReflectionUtils.newInstance(jobContext.getPartitionerClass(), job));
/*  665:     */       } else {
/*  666: 681 */         this.partitioner = new org.apache.hadoop.mapreduce.Partitioner()
/*  667:     */         {
/*  668:     */           public int getPartition(K key, V value, int numPartitions)
/*  669:     */           {
/*  670: 684 */             return MapTask.NewOutputCollector.this.partitions - 1;
/*  671:     */           }
/*  672:     */         };
/*  673:     */       }
/*  674:     */     }
/*  675:     */     
/*  676:     */     public void write(K key, V value)
/*  677:     */       throws IOException, InterruptedException
/*  678:     */     {
/*  679: 692 */       this.collector.collect(key, value, this.partitioner.getPartition(key, value, this.partitions));
/*  680:     */     }
/*  681:     */     
/*  682:     */     public void close(org.apache.hadoop.mapreduce.TaskAttemptContext context)
/*  683:     */       throws IOException, InterruptedException
/*  684:     */     {
/*  685:     */       try
/*  686:     */       {
/*  687: 700 */         this.collector.flush();
/*  688:     */       }
/*  689:     */       catch (ClassNotFoundException cnf)
/*  690:     */       {
/*  691: 702 */         throw new IOException("can't find class ", cnf);
/*  692:     */       }
/*  693: 704 */       this.collector.close();
/*  694:     */     }
/*  695:     */   }
/*  696:     */   
/*  697:     */   private <INKEY, INVALUE, OUTKEY, OUTVALUE> void runNewMapper(JobConf job, JobSplit.TaskSplitIndex splitIndex, TaskUmbilicalProtocol umbilical, Task.TaskReporter reporter)
/*  698:     */     throws IOException, ClassNotFoundException, InterruptedException
/*  699:     */   {
/*  700: 717 */     org.apache.hadoop.mapreduce.TaskAttemptContext taskContext = new TaskAttemptContextImpl(job, getTaskID(), reporter);
/*  701:     */     
/*  702:     */ 
/*  703:     */ 
/*  704:     */ 
/*  705: 722 */     Mapper<INKEY, INVALUE, OUTKEY, OUTVALUE> mapper = (Mapper)ReflectionUtils.newInstance(taskContext.getMapperClass(), job);
/*  706:     */     
/*  707:     */ 
/*  708:     */ 
/*  709: 726 */     org.apache.hadoop.mapreduce.InputFormat<INKEY, INVALUE> inputFormat = (org.apache.hadoop.mapreduce.InputFormat)ReflectionUtils.newInstance(taskContext.getInputFormatClass(), job);
/*  710:     */     
/*  711:     */ 
/*  712:     */ 
/*  713: 730 */     org.apache.hadoop.mapreduce.InputSplit split = null;
/*  714: 731 */     split = (org.apache.hadoop.mapreduce.InputSplit)getSplitDetails(new Path(splitIndex.getSplitLocation()), splitIndex.getStartOffset());
/*  715:     */     
/*  716: 733 */     LOG.info("Processing split: " + split);
/*  717:     */     
/*  718: 735 */     org.apache.hadoop.mapreduce.RecordReader<INKEY, INVALUE> input = new NewTrackingRecordReader(split, inputFormat, reporter, taskContext);
/*  719:     */     
/*  720:     */ 
/*  721:     */ 
/*  722: 739 */     job.setBoolean("mapreduce.job.skiprecords", isSkipping());
/*  723: 740 */     org.apache.hadoop.mapreduce.RecordWriter output = null;
/*  724: 743 */     if (job.getNumReduceTasks() == 0) {
/*  725: 744 */       output = new NewDirectOutputCollector(taskContext, job, umbilical, reporter);
/*  726:     */     } else {
/*  727: 747 */       output = new NewOutputCollector(taskContext, job, umbilical, reporter);
/*  728:     */     }
/*  729: 751 */     MapContext<INKEY, INVALUE, OUTKEY, OUTVALUE> mapContext = new MapContextImpl(job, getTaskID(), input, output, this.committer, reporter, split);
/*  730:     */     
/*  731:     */ 
/*  732:     */ 
/*  733:     */ 
/*  734:     */ 
/*  735:     */ 
/*  736: 758 */     Mapper<INKEY, INVALUE, OUTKEY, OUTVALUE>.Context mapperContext = new WrappedMapper().getMapContext(mapContext);
/*  737:     */     try
/*  738:     */     {
/*  739: 763 */       input.initialize(split, mapperContext);
/*  740: 764 */       mapper.run(mapperContext);
/*  741: 765 */       this.mapPhase.complete();
/*  742: 766 */       setPhase(TaskStatus.Phase.SORT);
/*  743: 767 */       statusUpdate(umbilical);
/*  744: 768 */       input.close();
/*  745: 769 */       input = null;
/*  746: 770 */       output.close(mapperContext);
/*  747: 771 */       output = null;
/*  748:     */     }
/*  749:     */     finally
/*  750:     */     {
/*  751: 773 */       closeQuietly(input);
/*  752: 774 */       closeQuietly(output, mapperContext);
/*  753:     */     }
/*  754:     */   }
/*  755:     */   
/*  756:     */   class DirectMapOutputCollector<K, V>
/*  757:     */     implements MapOutputCollector<K, V>
/*  758:     */   {
/*  759: 781 */     private RecordWriter<K, V> out = null;
/*  760: 783 */     private Task.TaskReporter reporter = null;
/*  761:     */     private Counters.Counter mapOutputRecordCounter;
/*  762:     */     private Counters.Counter fileOutputByteCounter;
/*  763:     */     private List<FileSystem.Statistics> fsStats;
/*  764:     */     
/*  765:     */     public DirectMapOutputCollector() {}
/*  766:     */     
/*  767:     */     public void init(MapOutputCollector.Context context)
/*  768:     */       throws IOException, ClassNotFoundException
/*  769:     */     {
/*  770: 795 */       this.reporter = context.getReporter();
/*  771: 796 */       JobConf job = context.getJobConf();
/*  772: 797 */       String finalName = Task.getOutputName(MapTask.this.getPartition());
/*  773: 798 */       FileSystem fs = FileSystem.get(job);
/*  774:     */       
/*  775: 800 */       OutputFormat<K, V> outputFormat = job.getOutputFormat();
/*  776: 801 */       this.mapOutputRecordCounter = this.reporter.getCounter(TaskCounter.MAP_OUTPUT_RECORDS);
/*  777:     */       
/*  778: 803 */       this.fileOutputByteCounter = this.reporter.getCounter(FileOutputFormatCounter.BYTES_WRITTEN);
/*  779:     */       
/*  780:     */ 
/*  781: 806 */       List<FileSystem.Statistics> matchedStats = null;
/*  782: 807 */       if ((outputFormat instanceof FileOutputFormat)) {
/*  783: 808 */         matchedStats = Task.getFsStatistics(FileOutputFormat.getOutputPath(job), job);
/*  784:     */       }
/*  785: 810 */       this.fsStats = matchedStats;
/*  786:     */       
/*  787: 812 */       long bytesOutPrev = getOutputBytes(this.fsStats);
/*  788: 813 */       this.out = job.getOutputFormat().getRecordWriter(fs, job, finalName, this.reporter);
/*  789: 814 */       long bytesOutCurr = getOutputBytes(this.fsStats);
/*  790: 815 */       this.fileOutputByteCounter.increment(bytesOutCurr - bytesOutPrev);
/*  791:     */     }
/*  792:     */     
/*  793:     */     public void close()
/*  794:     */       throws IOException
/*  795:     */     {
/*  796: 819 */       if (this.out != null)
/*  797:     */       {
/*  798: 820 */         long bytesOutPrev = getOutputBytes(this.fsStats);
/*  799: 821 */         this.out.close(this.reporter);
/*  800: 822 */         long bytesOutCurr = getOutputBytes(this.fsStats);
/*  801: 823 */         this.fileOutputByteCounter.increment(bytesOutCurr - bytesOutPrev);
/*  802:     */       }
/*  803:     */     }
/*  804:     */     
/*  805:     */     public void flush()
/*  806:     */       throws IOException, InterruptedException, ClassNotFoundException
/*  807:     */     {}
/*  808:     */     
/*  809:     */     public void collect(K key, V value, int partition)
/*  810:     */       throws IOException
/*  811:     */     {
/*  812: 833 */       this.reporter.progress();
/*  813: 834 */       long bytesOutPrev = getOutputBytes(this.fsStats);
/*  814: 835 */       this.out.write(key, value);
/*  815: 836 */       long bytesOutCurr = getOutputBytes(this.fsStats);
/*  816: 837 */       this.fileOutputByteCounter.increment(bytesOutCurr - bytesOutPrev);
/*  817: 838 */       this.mapOutputRecordCounter.increment(1L);
/*  818:     */     }
/*  819:     */     
/*  820:     */     private long getOutputBytes(List<FileSystem.Statistics> stats)
/*  821:     */     {
/*  822: 842 */       if (stats == null) {
/*  823: 842 */         return 0L;
/*  824:     */       }
/*  825: 843 */       long bytesWritten = 0L;
/*  826: 844 */       for (FileSystem.Statistics stat : stats) {
/*  827: 845 */         bytesWritten += stat.getBytesWritten();
/*  828:     */       }
/*  829: 847 */       return bytesWritten;
/*  830:     */     }
/*  831:     */   }
/*  832:     */   
/*  833:     */   @InterfaceAudience.LimitedPrivate({"MapReduce"})
/*  834:     */   @InterfaceStability.Unstable
/*  835:     */   public static class MapOutputBuffer<K, V>
/*  836:     */     implements MapOutputCollector<K, V>, IndexedSortable
/*  837:     */   {
/*  838:     */     private int partitions;
/*  839:     */     private JobConf job;
/*  840:     */     private Task.TaskReporter reporter;
/*  841:     */     private Class<K> keyClass;
/*  842:     */     private Class<V> valClass;
/*  843:     */     private RawComparator<K> comparator;
/*  844:     */     private SerializationFactory serializationFactory;
/*  845:     */     private Serializer<K> keySerializer;
/*  846:     */     private Serializer<V> valSerializer;
/*  847:     */     private Task.CombinerRunner<K, V> combinerRunner;
/*  848:     */     private Task.CombineOutputCollector<K, V> combineCollector;
/*  849:     */     private CompressionCodec codec;
/*  850:     */     private IntBuffer kvmeta;
/*  851:     */     int kvstart;
/*  852:     */     int kvend;
/*  853:     */     int kvindex;
/*  854:     */     int equator;
/*  855:     */     int bufstart;
/*  856:     */     int bufend;
/*  857:     */     int bufmark;
/*  858:     */     int bufindex;
/*  859:     */     int bufvoid;
/*  860:     */     byte[] kvbuffer;
/*  861: 885 */     private final byte[] b0 = new byte[0];
/*  862:     */     private static final int VALSTART = 0;
/*  863:     */     private static final int KEYSTART = 1;
/*  864:     */     private static final int PARTITION = 2;
/*  865:     */     private static final int VALLEN = 3;
/*  866:     */     private static final int NMETA = 4;
/*  867:     */     private static final int METASIZE = 16;
/*  868:     */     private int maxRec;
/*  869:     */     private int softLimit;
/*  870:     */     boolean spillInProgress;
/*  871:     */     int bufferRemaining;
/*  872: 899 */     volatile Throwable sortSpillException = null;
/*  873: 901 */     int numSpills = 0;
/*  874:     */     private int minSpillsForCombine;
/*  875:     */     private IndexedSorter sorter;
/*  876: 904 */     final ReentrantLock spillLock = new ReentrantLock();
/*  877: 905 */     final Condition spillDone = this.spillLock.newCondition();
/*  878: 906 */     final Condition spillReady = this.spillLock.newCondition();
/*  879: 907 */     final MapOutputBuffer<K, V>.BlockingBuffer bb = new BlockingBuffer();
/*  880: 908 */     volatile boolean spillThreadRunning = false;
/*  881: 909 */     final MapOutputBuffer<K, V>.SpillThread spillThread = new SpillThread();
/*  882:     */     private FileSystem rfs;
/*  883:     */     private Counters.Counter mapOutputByteCounter;
/*  884:     */     private Counters.Counter mapOutputRecordCounter;
/*  885:     */     private Counters.Counter fileOutputByteCounter;
/*  886: 918 */     final ArrayList<SpillRecord> indexCacheList = new ArrayList();
/*  887:     */     private int totalIndexCacheMemory;
/*  888:     */     private int indexCacheMemoryLimit;
/*  889:     */     private static final int INDEX_CACHE_MEMORY_LIMIT_DEFAULT = 1048576;
/*  890:     */     private MapTask mapTask;
/*  891:     */     private MapOutputFile mapOutputFile;
/*  892:     */     private Progress sortPhase;
/*  893:     */     private Counters.Counter spilledRecordsCounter;
/*  894:     */     
/*  895:     */     public void init(MapOutputCollector.Context context)
/*  896:     */       throws IOException, ClassNotFoundException
/*  897:     */     {
/*  898: 935 */       this.job = context.getJobConf();
/*  899: 936 */       this.reporter = context.getReporter();
/*  900: 937 */       this.mapTask = context.getMapTask();
/*  901: 938 */       this.mapOutputFile = this.mapTask.getMapOutputFile();
/*  902: 939 */       this.sortPhase = this.mapTask.getSortPhase();
/*  903: 940 */       this.spilledRecordsCounter = this.reporter.getCounter(TaskCounter.SPILLED_RECORDS);
/*  904: 941 */       this.partitions = this.job.getNumReduceTasks();
/*  905: 942 */       this.rfs = FileSystem.getLocal(this.job).getRaw();
/*  906:     */       
/*  907:     */ 
/*  908: 945 */       float spillper = this.job.getFloat("mapreduce.map.sort.spill.percent", 0.8F);
/*  909:     */       
/*  910: 947 */       int sortmb = this.job.getInt("mapreduce.task.io.sort.mb", 100);
/*  911: 948 */       this.indexCacheMemoryLimit = this.job.getInt("mapreduce.task.index.cache.limit.bytes", 1048576);
/*  912: 950 */       if ((spillper > 1.0F) || (spillper <= 0.0F)) {
/*  913: 951 */         throw new IOException("Invalid \"mapreduce.map.sort.spill.percent\": " + spillper);
/*  914:     */       }
/*  915: 954 */       if ((sortmb & 0x7FF) != sortmb) {
/*  916: 955 */         throw new IOException("Invalid \"mapreduce.task.io.sort.mb\": " + sortmb);
/*  917:     */       }
/*  918: 958 */       this.sorter = ((IndexedSorter)ReflectionUtils.newInstance(this.job.getClass("map.sort.class", QuickSort.class, IndexedSorter.class), this.job));
/*  919:     */       
/*  920:     */ 
/*  921: 961 */       int maxMemUsage = sortmb << 20;
/*  922: 962 */       maxMemUsage -= maxMemUsage % 16;
/*  923: 963 */       this.kvbuffer = new byte[maxMemUsage];
/*  924: 964 */       this.bufvoid = this.kvbuffer.length;
/*  925: 965 */       this.kvmeta = ByteBuffer.wrap(this.kvbuffer).order(ByteOrder.nativeOrder()).asIntBuffer();
/*  926:     */       
/*  927:     */ 
/*  928: 968 */       setEquator(0);
/*  929: 969 */       this.bufstart = (this.bufend = this.bufindex = this.equator);
/*  930: 970 */       this.kvstart = (this.kvend = this.kvindex);
/*  931:     */       
/*  932: 972 */       this.maxRec = (this.kvmeta.capacity() / 4);
/*  933: 973 */       this.softLimit = ((int)(this.kvbuffer.length * spillper));
/*  934: 974 */       this.bufferRemaining = this.softLimit;
/*  935: 975 */       MapTask.LOG.info("mapreduce.task.io.sort.mb: " + sortmb);
/*  936: 976 */       MapTask.LOG.info("soft limit at " + this.softLimit);
/*  937: 977 */       MapTask.LOG.info("bufstart = " + this.bufstart + "; bufvoid = " + this.bufvoid);
/*  938: 978 */       MapTask.LOG.info("kvstart = " + this.kvstart + "; length = " + this.maxRec);
/*  939:     */       
/*  940:     */ 
/*  941: 981 */       this.comparator = this.job.getOutputKeyComparator();
/*  942: 982 */       this.keyClass = this.job.getMapOutputKeyClass();
/*  943: 983 */       this.valClass = this.job.getMapOutputValueClass();
/*  944: 984 */       this.serializationFactory = new SerializationFactory(this.job);
/*  945: 985 */       this.keySerializer = this.serializationFactory.getSerializer(this.keyClass);
/*  946: 986 */       this.keySerializer.open(this.bb);
/*  947: 987 */       this.valSerializer = this.serializationFactory.getSerializer(this.valClass);
/*  948: 988 */       this.valSerializer.open(this.bb);
/*  949:     */       
/*  950:     */ 
/*  951: 991 */       this.mapOutputByteCounter = this.reporter.getCounter(TaskCounter.MAP_OUTPUT_BYTES);
/*  952: 992 */       this.mapOutputRecordCounter = this.reporter.getCounter(TaskCounter.MAP_OUTPUT_RECORDS);
/*  953:     */       
/*  954: 994 */       this.fileOutputByteCounter = this.reporter.getCounter(TaskCounter.MAP_OUTPUT_MATERIALIZED_BYTES);
/*  955: 998 */       if (this.job.getCompressMapOutput())
/*  956:     */       {
/*  957: 999 */         Class<? extends CompressionCodec> codecClass = this.job.getMapOutputCompressorClass(DefaultCodec.class);
/*  958:     */         
/*  959:1001 */         this.codec = ((CompressionCodec)ReflectionUtils.newInstance(codecClass, this.job));
/*  960:     */       }
/*  961:     */       else
/*  962:     */       {
/*  963:1003 */         this.codec = null;
/*  964:     */       }
/*  965:1007 */       Counters.Counter combineInputCounter = this.reporter.getCounter(TaskCounter.COMBINE_INPUT_RECORDS);
/*  966:     */       
/*  967:1009 */       this.combinerRunner = Task.CombinerRunner.create(this.job, getTaskID(), combineInputCounter, this.reporter, null);
/*  968:1012 */       if (this.combinerRunner != null)
/*  969:     */       {
/*  970:1013 */         Counters.Counter combineOutputCounter = this.reporter.getCounter(TaskCounter.COMBINE_OUTPUT_RECORDS);
/*  971:     */         
/*  972:1015 */         this.combineCollector = new Task.CombineOutputCollector(combineOutputCounter, this.reporter, this.job);
/*  973:     */       }
/*  974:     */       else
/*  975:     */       {
/*  976:1017 */         this.combineCollector = null;
/*  977:     */       }
/*  978:1019 */       this.spillInProgress = false;
/*  979:1020 */       this.minSpillsForCombine = this.job.getInt("mapreduce.map.combine.minspills", 3);
/*  980:1021 */       this.spillThread.setDaemon(true);
/*  981:1022 */       this.spillThread.setName("SpillThread");
/*  982:1023 */       this.spillLock.lock();
/*  983:     */       try
/*  984:     */       {
/*  985:1025 */         this.spillThread.start();
/*  986:1026 */         while (!this.spillThreadRunning) {
/*  987:1027 */           this.spillDone.await();
/*  988:     */         }
/*  989:     */       }
/*  990:     */       catch (InterruptedException e)
/*  991:     */       {
/*  992:1030 */         throw new IOException("Spill thread failed to initialize", e);
/*  993:     */       }
/*  994:     */       finally
/*  995:     */       {
/*  996:1032 */         this.spillLock.unlock();
/*  997:     */       }
/*  998:1034 */       if (this.sortSpillException != null) {
/*  999:1035 */         throw new IOException("Spill thread failed to initialize", this.sortSpillException);
/* 1000:     */       }
/* 1001:     */     }
/* 1002:     */     
/* 1003:     */     public synchronized void collect(K key, V value, int partition)
/* 1004:     */       throws IOException
/* 1005:     */     {
/* 1006:1047 */       this.reporter.progress();
/* 1007:1048 */       if (key.getClass() != this.keyClass) {
/* 1008:1049 */         throw new IOException("Type mismatch in key from map: expected " + this.keyClass.getName() + ", received " + key.getClass().getName());
/* 1009:     */       }
/* 1010:1053 */       if (value.getClass() != this.valClass) {
/* 1011:1054 */         throw new IOException("Type mismatch in value from map: expected " + this.valClass.getName() + ", received " + value.getClass().getName());
/* 1012:     */       }
/* 1013:1058 */       if ((partition < 0) || (partition >= this.partitions)) {
/* 1014:1059 */         throw new IOException("Illegal partition for " + key + " (" + partition + ")");
/* 1015:     */       }
/* 1016:1062 */       checkSpillException();
/* 1017:1063 */       this.bufferRemaining -= 16;
/* 1018:1064 */       if (this.bufferRemaining <= 0)
/* 1019:     */       {
/* 1020:1067 */         this.spillLock.lock();
/* 1021:     */         try
/* 1022:     */         {
/* 1023:1070 */           if (!this.spillInProgress)
/* 1024:     */           {
/* 1025:1071 */             int kvbidx = 4 * this.kvindex;
/* 1026:1072 */             int kvbend = 4 * this.kvend;
/* 1027:     */             
/* 1028:     */ 
/* 1029:     */ 
/* 1030:1076 */             int bUsed = distanceTo(kvbidx, this.bufindex);
/* 1031:1077 */             boolean bufsoftlimit = bUsed >= this.softLimit;
/* 1032:1078 */             if ((kvbend + 16) % this.kvbuffer.length != this.equator - this.equator % 16)
/* 1033:     */             {
/* 1034:1081 */               resetSpill();
/* 1035:1082 */               this.bufferRemaining = (Math.min(distanceTo(this.bufindex, kvbidx) - 32, this.softLimit - bUsed) - 16);
/* 1036:     */             }
/* 1037:1086 */             else if ((bufsoftlimit) && (this.kvindex != this.kvend))
/* 1038:     */             {
/* 1039:1089 */               startSpill();
/* 1040:1090 */               int avgRec = (int)(this.mapOutputByteCounter.getCounter() / this.mapOutputRecordCounter.getCounter());
/* 1041:     */               
/* 1042:     */ 
/* 1043:     */ 
/* 1044:     */ 
/* 1045:1095 */               int distkvi = distanceTo(this.bufindex, kvbidx);
/* 1046:1096 */               int newPos = (this.bufindex + Math.max(31, Math.min(distkvi / 2, distkvi / (16 + avgRec) * 16))) % this.kvbuffer.length;
/* 1047:     */               
/* 1048:     */ 
/* 1049:     */ 
/* 1050:     */ 
/* 1051:1101 */               setEquator(newPos);
/* 1052:1102 */               this.bufmark = (this.bufindex = newPos);
/* 1053:1103 */               int serBound = 4 * this.kvend;
/* 1054:     */               
/* 1055:     */ 
/* 1056:     */ 
/* 1057:1107 */               this.bufferRemaining = (Math.min(distanceTo(this.bufend, newPos), Math.min(distanceTo(newPos, serBound), this.softLimit)) - 32);
/* 1058:     */             }
/* 1059:     */           }
/* 1060:     */         }
/* 1061:     */         finally
/* 1062:     */         {
/* 1063:1119 */           this.spillLock.unlock();
/* 1064:     */         }
/* 1065:     */       }
/* 1066:     */       try
/* 1067:     */       {
/* 1068:1125 */         int keystart = this.bufindex;
/* 1069:1126 */         this.keySerializer.serialize(key);
/* 1070:1127 */         if (this.bufindex < keystart)
/* 1071:     */         {
/* 1072:1129 */           this.bb.shiftBufferedKey();
/* 1073:1130 */           keystart = 0;
/* 1074:     */         }
/* 1075:1133 */         int valstart = this.bufindex;
/* 1076:1134 */         this.valSerializer.serialize(value);
/* 1077:     */         
/* 1078:     */ 
/* 1079:     */ 
/* 1080:     */ 
/* 1081:     */ 
/* 1082:     */ 
/* 1083:1141 */         this.bb.write(this.b0, 0, 0);
/* 1084:     */         
/* 1085:     */ 
/* 1086:     */ 
/* 1087:1145 */         int valend = this.bb.markRecord();
/* 1088:     */         
/* 1089:1147 */         this.mapOutputRecordCounter.increment(1L);
/* 1090:1148 */         this.mapOutputByteCounter.increment(distanceTo(keystart, valend, this.bufvoid));
/* 1091:     */         
/* 1092:     */ 
/* 1093:     */ 
/* 1094:1152 */         this.kvmeta.put(this.kvindex + 2, partition);
/* 1095:1153 */         this.kvmeta.put(this.kvindex + 1, keystart);
/* 1096:1154 */         this.kvmeta.put(this.kvindex + 0, valstart);
/* 1097:1155 */         this.kvmeta.put(this.kvindex + 3, distanceTo(valstart, valend));
/* 1098:     */         
/* 1099:1157 */         this.kvindex = ((this.kvindex - 4 + this.kvmeta.capacity()) % this.kvmeta.capacity());
/* 1100:     */       }
/* 1101:     */       catch (MapTask.MapBufferTooSmallException e)
/* 1102:     */       {
/* 1103:1159 */         MapTask.LOG.info("Record too large for in-memory buffer: " + e.getMessage());
/* 1104:1160 */         spillSingleRecord(key, value, partition);
/* 1105:1161 */         this.mapOutputRecordCounter.increment(1L);
/* 1106:1162 */         return;
/* 1107:     */       }
/* 1108:     */     }
/* 1109:     */     
/* 1110:     */     private TaskAttemptID getTaskID()
/* 1111:     */     {
/* 1112:1167 */       return this.mapTask.getTaskID();
/* 1113:     */     }
/* 1114:     */     
/* 1115:     */     private void setEquator(int pos)
/* 1116:     */     {
/* 1117:1176 */       this.equator = pos;
/* 1118:     */       
/* 1119:1178 */       int aligned = pos - pos % 16;
/* 1120:     */       
/* 1121:1180 */       this.kvindex = ((int)((aligned - 16L + this.kvbuffer.length) % this.kvbuffer.length) / 4);
/* 1122:     */       
/* 1123:1182 */       MapTask.LOG.info("(EQUATOR) " + pos + " kvi " + this.kvindex + "(" + this.kvindex * 4 + ")");
/* 1124:     */     }
/* 1125:     */     
/* 1126:     */     private void resetSpill()
/* 1127:     */     {
/* 1128:1192 */       int e = this.equator;
/* 1129:1193 */       this.bufstart = (this.bufend = e);
/* 1130:1194 */       int aligned = e - e % 16;
/* 1131:     */       
/* 1132:     */ 
/* 1133:1197 */       this.kvstart = (this.kvend = (int)((aligned - 16L + this.kvbuffer.length) % this.kvbuffer.length) / 4);
/* 1134:     */       
/* 1135:1199 */       MapTask.LOG.info("(RESET) equator " + e + " kv " + this.kvstart + "(" + this.kvstart * 4 + ")" + " kvi " + this.kvindex + "(" + this.kvindex * 4 + ")");
/* 1136:     */     }
/* 1137:     */     
/* 1138:     */     final int distanceTo(int i, int j)
/* 1139:     */     {
/* 1140:1209 */       return distanceTo(i, j, this.kvbuffer.length);
/* 1141:     */     }
/* 1142:     */     
/* 1143:     */     int distanceTo(int i, int j, int mod)
/* 1144:     */     {
/* 1145:1217 */       return i <= j ? j - i : mod - i + j;
/* 1146:     */     }
/* 1147:     */     
/* 1148:     */     int offsetFor(int metapos)
/* 1149:     */     {
/* 1150:1227 */       return metapos * 4;
/* 1151:     */     }
/* 1152:     */     
/* 1153:     */     public int compare(int mi, int mj)
/* 1154:     */     {
/* 1155:1236 */       int kvi = offsetFor(mi % this.maxRec);
/* 1156:1237 */       int kvj = offsetFor(mj % this.maxRec);
/* 1157:1238 */       int kvip = this.kvmeta.get(kvi + 2);
/* 1158:1239 */       int kvjp = this.kvmeta.get(kvj + 2);
/* 1159:1241 */       if (kvip != kvjp) {
/* 1160:1242 */         return kvip - kvjp;
/* 1161:     */       }
/* 1162:1245 */       return this.comparator.compare(this.kvbuffer, this.kvmeta.get(kvi + 1), this.kvmeta.get(kvi + 0) - this.kvmeta.get(kvi + 1), this.kvbuffer, this.kvmeta.get(kvj + 1), this.kvmeta.get(kvj + 0) - this.kvmeta.get(kvj + 1));
/* 1163:     */     }
/* 1164:     */     
/* 1165:1253 */     final byte[] META_BUFFER_TMP = new byte[16];
/* 1166:     */     
/* 1167:     */     public void swap(int mi, int mj)
/* 1168:     */     {
/* 1169:1259 */       int iOff = mi % this.maxRec * 16;
/* 1170:1260 */       int jOff = mj % this.maxRec * 16;
/* 1171:1261 */       System.arraycopy(this.kvbuffer, iOff, this.META_BUFFER_TMP, 0, 16);
/* 1172:1262 */       System.arraycopy(this.kvbuffer, jOff, this.kvbuffer, iOff, 16);
/* 1173:1263 */       System.arraycopy(this.META_BUFFER_TMP, 0, this.kvbuffer, jOff, 16);
/* 1174:     */     }
/* 1175:     */     
/* 1176:     */     protected class BlockingBuffer
/* 1177:     */       extends DataOutputStream
/* 1178:     */     {
/* 1179:     */       public BlockingBuffer()
/* 1180:     */       {
/* 1181:1272 */         super();
/* 1182:     */       }
/* 1183:     */       
/* 1184:     */       public int markRecord()
/* 1185:     */       {
/* 1186:1280 */         MapTask.MapOutputBuffer.this.bufmark = MapTask.MapOutputBuffer.this.bufindex;
/* 1187:1281 */         return MapTask.MapOutputBuffer.this.bufindex;
/* 1188:     */       }
/* 1189:     */       
/* 1190:     */       protected void shiftBufferedKey()
/* 1191:     */         throws IOException
/* 1192:     */       {
/* 1193:1298 */         int headbytelen = MapTask.MapOutputBuffer.this.bufvoid - MapTask.MapOutputBuffer.this.bufmark;
/* 1194:1299 */         MapTask.MapOutputBuffer.this.bufvoid = MapTask.MapOutputBuffer.this.bufmark;
/* 1195:1300 */         int kvbidx = 4 * MapTask.MapOutputBuffer.this.kvindex;
/* 1196:1301 */         int kvbend = 4 * MapTask.MapOutputBuffer.this.kvend;
/* 1197:1302 */         int avail = Math.min(MapTask.MapOutputBuffer.this.distanceTo(0, kvbidx), MapTask.MapOutputBuffer.this.distanceTo(0, kvbend));
/* 1198:1304 */         if (MapTask.MapOutputBuffer.this.bufindex + headbytelen < avail)
/* 1199:     */         {
/* 1200:1305 */           System.arraycopy(MapTask.MapOutputBuffer.this.kvbuffer, 0, MapTask.MapOutputBuffer.this.kvbuffer, headbytelen, MapTask.MapOutputBuffer.this.bufindex);
/* 1201:1306 */           System.arraycopy(MapTask.MapOutputBuffer.this.kvbuffer, MapTask.MapOutputBuffer.this.bufvoid, MapTask.MapOutputBuffer.this.kvbuffer, 0, headbytelen);
/* 1202:1307 */           MapTask.MapOutputBuffer.this.bufindex += headbytelen;
/* 1203:1308 */           MapTask.MapOutputBuffer.this.bufferRemaining -= MapTask.MapOutputBuffer.this.kvbuffer.length - MapTask.MapOutputBuffer.this.bufvoid;
/* 1204:     */         }
/* 1205:     */         else
/* 1206:     */         {
/* 1207:1310 */           byte[] keytmp = new byte[MapTask.MapOutputBuffer.this.bufindex];
/* 1208:1311 */           System.arraycopy(MapTask.MapOutputBuffer.this.kvbuffer, 0, keytmp, 0, MapTask.MapOutputBuffer.this.bufindex);
/* 1209:1312 */           MapTask.MapOutputBuffer.this.bufindex = 0;
/* 1210:1313 */           this.out.write(MapTask.MapOutputBuffer.this.kvbuffer, MapTask.MapOutputBuffer.this.bufmark, headbytelen);
/* 1211:1314 */           this.out.write(keytmp);
/* 1212:     */         }
/* 1213:     */       }
/* 1214:     */     }
/* 1215:     */     
/* 1216:     */     public class Buffer
/* 1217:     */       extends OutputStream
/* 1218:     */     {
/* 1219:1320 */       private final byte[] scratch = new byte[1];
/* 1220:     */       
/* 1221:     */       public Buffer() {}
/* 1222:     */       
/* 1223:     */       public void write(int v)
/* 1224:     */         throws IOException
/* 1225:     */       {
/* 1226:1325 */         this.scratch[0] = ((byte)v);
/* 1227:1326 */         write(this.scratch, 0, 1);
/* 1228:     */       }
/* 1229:     */       
/* 1230:     */       public void write(byte[] b, int off, int len)
/* 1231:     */         throws IOException
/* 1232:     */       {
/* 1233:1341 */         MapTask.MapOutputBuffer.this.bufferRemaining -= len;
/* 1234:1342 */         if (MapTask.MapOutputBuffer.this.bufferRemaining <= 0)
/* 1235:     */         {
/* 1236:1345 */           boolean blockwrite = false;
/* 1237:1346 */           MapTask.MapOutputBuffer.this.spillLock.lock();
/* 1238:     */           try
/* 1239:     */           {
/* 1240:     */             do
/* 1241:     */             {
/* 1242:1349 */               MapTask.MapOutputBuffer.this.checkSpillException();
/* 1243:     */               
/* 1244:1351 */               int kvbidx = 4 * MapTask.MapOutputBuffer.this.kvindex;
/* 1245:1352 */               int kvbend = 4 * MapTask.MapOutputBuffer.this.kvend;
/* 1246:     */               
/* 1247:1354 */               int distkvi = MapTask.MapOutputBuffer.this.distanceTo(MapTask.MapOutputBuffer.this.bufindex, kvbidx);
/* 1248:     */               
/* 1249:1356 */               int distkve = MapTask.MapOutputBuffer.this.distanceTo(MapTask.MapOutputBuffer.this.bufindex, kvbend);
/* 1250:     */               
/* 1251:     */ 
/* 1252:     */ 
/* 1253:     */ 
/* 1254:     */ 
/* 1255:     */ 
/* 1256:     */ 
/* 1257:     */ 
/* 1258:     */ 
/* 1259:1366 */               blockwrite = distkvi <= len + 32;
/* 1260:1370 */               if ((!MapTask.MapOutputBuffer.this.spillInProgress) && 
/* 1261:1371 */                 (blockwrite))
/* 1262:     */               {
/* 1263:1372 */                 if ((kvbend + 16) % MapTask.MapOutputBuffer.this.kvbuffer.length != MapTask.MapOutputBuffer.this.equator - MapTask.MapOutputBuffer.this.equator % 16)
/* 1264:     */                 {
/* 1265:1377 */                   MapTask.MapOutputBuffer.this.resetSpill();
/* 1266:1378 */                   MapTask.MapOutputBuffer.this.bufferRemaining = (Math.min(distkvi - 32, MapTask.MapOutputBuffer.this.softLimit - MapTask.MapOutputBuffer.this.distanceTo(kvbidx, MapTask.MapOutputBuffer.this.bufindex)) - len);
/* 1267:     */                   
/* 1268:     */ 
/* 1269:1381 */                   continue;
/* 1270:     */                 }
/* 1271:1384 */                 if (MapTask.MapOutputBuffer.this.kvindex != MapTask.MapOutputBuffer.this.kvend)
/* 1272:     */                 {
/* 1273:1385 */                   MapTask.MapOutputBuffer.this.startSpill();
/* 1274:     */                   
/* 1275:     */ 
/* 1276:     */ 
/* 1277:     */ 
/* 1278:1390 */                   MapTask.MapOutputBuffer.this.setEquator(MapTask.MapOutputBuffer.this.bufmark);
/* 1279:     */                 }
/* 1280:     */                 else
/* 1281:     */                 {
/* 1282:1395 */                   int size = MapTask.MapOutputBuffer.this.distanceTo(MapTask.MapOutputBuffer.this.bufstart, MapTask.MapOutputBuffer.this.bufindex) + len;
/* 1283:1396 */                   MapTask.MapOutputBuffer.this.setEquator(0);
/* 1284:1397 */                   MapTask.MapOutputBuffer.this.bufstart = (MapTask.MapOutputBuffer.this.bufend = MapTask.MapOutputBuffer.this.bufindex = MapTask.MapOutputBuffer.this.equator);
/* 1285:1398 */                   MapTask.MapOutputBuffer.this.kvstart = (MapTask.MapOutputBuffer.this.kvend = MapTask.MapOutputBuffer.this.kvindex);
/* 1286:1399 */                   MapTask.MapOutputBuffer.this.bufvoid = MapTask.MapOutputBuffer.this.kvbuffer.length;
/* 1287:1400 */                   throw new MapTask.MapBufferTooSmallException(size + " bytes");
/* 1288:     */                 }
/* 1289:     */               }
/* 1290:1405 */               if (blockwrite) {
/* 1291:     */                 try
/* 1292:     */                 {
/* 1293:1408 */                   while (MapTask.MapOutputBuffer.this.spillInProgress)
/* 1294:     */                   {
/* 1295:1409 */                     MapTask.MapOutputBuffer.this.reporter.progress();
/* 1296:1410 */                     MapTask.MapOutputBuffer.this.spillDone.await();
/* 1297:     */                   }
/* 1298:     */                 }
/* 1299:     */                 catch (InterruptedException e)
/* 1300:     */                 {
/* 1301:1413 */                   throw new IOException("Buffer interrupted while waiting for the writer", e);
/* 1302:     */                 }
/* 1303:     */               }
/* 1304:1417 */             } while (blockwrite);
/* 1305:     */           }
/* 1306:     */           finally
/* 1307:     */           {
/* 1308:1419 */             MapTask.MapOutputBuffer.this.spillLock.unlock();
/* 1309:     */           }
/* 1310:     */         }
/* 1311:1423 */         if (MapTask.MapOutputBuffer.this.bufindex + len > MapTask.MapOutputBuffer.this.bufvoid)
/* 1312:     */         {
/* 1313:1424 */           int gaplen = MapTask.MapOutputBuffer.this.bufvoid - MapTask.MapOutputBuffer.this.bufindex;
/* 1314:1425 */           System.arraycopy(b, off, MapTask.MapOutputBuffer.this.kvbuffer, MapTask.MapOutputBuffer.this.bufindex, gaplen);
/* 1315:1426 */           len -= gaplen;
/* 1316:1427 */           off += gaplen;
/* 1317:1428 */           MapTask.MapOutputBuffer.this.bufindex = 0;
/* 1318:     */         }
/* 1319:1430 */         System.arraycopy(b, off, MapTask.MapOutputBuffer.this.kvbuffer, MapTask.MapOutputBuffer.this.bufindex, len);
/* 1320:1431 */         MapTask.MapOutputBuffer.this.bufindex += len;
/* 1321:     */       }
/* 1322:     */     }
/* 1323:     */     
/* 1324:     */     public void flush()
/* 1325:     */       throws IOException, ClassNotFoundException, InterruptedException
/* 1326:     */     {
/* 1327:1437 */       MapTask.LOG.info("Starting flush of map output");
/* 1328:1438 */       this.spillLock.lock();
/* 1329:     */       try
/* 1330:     */       {
/* 1331:1440 */         while (this.spillInProgress)
/* 1332:     */         {
/* 1333:1441 */           this.reporter.progress();
/* 1334:1442 */           this.spillDone.await();
/* 1335:     */         }
/* 1336:1444 */         checkSpillException();
/* 1337:     */         
/* 1338:1446 */         int kvbend = 4 * this.kvend;
/* 1339:1447 */         if ((kvbend + 16) % this.kvbuffer.length != this.equator - this.equator % 16) {
/* 1340:1450 */           resetSpill();
/* 1341:     */         }
/* 1342:1452 */         if (this.kvindex != this.kvend)
/* 1343:     */         {
/* 1344:1453 */           this.kvend = ((this.kvindex + 4) % this.kvmeta.capacity());
/* 1345:1454 */           this.bufend = this.bufmark;
/* 1346:1455 */           MapTask.LOG.info("Spilling map output");
/* 1347:1456 */           MapTask.LOG.info("bufstart = " + this.bufstart + "; bufend = " + this.bufmark + "; bufvoid = " + this.bufvoid);
/* 1348:     */           
/* 1349:1458 */           MapTask.LOG.info("kvstart = " + this.kvstart + "(" + this.kvstart * 4 + "); kvend = " + this.kvend + "(" + this.kvend * 4 + "); length = " + (distanceTo(this.kvend, this.kvstart, this.kvmeta.capacity()) + 1) + "/" + this.maxRec);
/* 1350:     */           
/* 1351:     */ 
/* 1352:     */ 
/* 1353:1462 */           sortAndSpill();
/* 1354:     */         }
/* 1355:     */       }
/* 1356:     */       catch (InterruptedException e)
/* 1357:     */       {
/* 1358:1465 */         throw new IOException("Interrupted while waiting for the writer", e);
/* 1359:     */       }
/* 1360:     */       finally
/* 1361:     */       {
/* 1362:1467 */         this.spillLock.unlock();
/* 1363:     */       }
/* 1364:1469 */       assert (!this.spillLock.isHeldByCurrentThread());
/* 1365:     */       try
/* 1366:     */       {
/* 1367:1477 */         this.spillThread.interrupt();
/* 1368:1478 */         this.spillThread.join();
/* 1369:     */       }
/* 1370:     */       catch (InterruptedException e)
/* 1371:     */       {
/* 1372:1480 */         throw new IOException("Spill failed", e);
/* 1373:     */       }
/* 1374:1483 */       this.kvbuffer = null;
/* 1375:1484 */       mergeParts();
/* 1376:1485 */       Path outputPath = this.mapOutputFile.getOutputFile();
/* 1377:1486 */       this.fileOutputByteCounter.increment(this.rfs.getFileStatus(outputPath).getLen());
/* 1378:     */     }
/* 1379:     */     
/* 1380:     */     public void close() {}
/* 1381:     */     
/* 1382:     */     protected class SpillThread
/* 1383:     */       extends Thread
/* 1384:     */     {
/* 1385:     */       protected SpillThread() {}
/* 1386:     */       
/* 1387:     */       public void run()
/* 1388:     */       {
/* 1389:1495 */         MapTask.MapOutputBuffer.this.spillLock.lock();
/* 1390:1496 */         MapTask.MapOutputBuffer.this.spillThreadRunning = true;
/* 1391:     */         try
/* 1392:     */         {
/* 1393:     */           for (;;)
/* 1394:     */           {
/* 1395:1499 */             MapTask.MapOutputBuffer.this.spillDone.signal();
/* 1396:1500 */             while (!MapTask.MapOutputBuffer.this.spillInProgress) {
/* 1397:1501 */               MapTask.MapOutputBuffer.this.spillReady.await();
/* 1398:     */             }
/* 1399:     */             try
/* 1400:     */             {
/* 1401:1504 */               MapTask.MapOutputBuffer.this.spillLock.unlock();
/* 1402:1505 */               MapTask.MapOutputBuffer.this.sortAndSpill();
/* 1403:     */             }
/* 1404:     */             catch (Throwable t)
/* 1405:     */             {
/* 1406:1507 */               MapTask.MapOutputBuffer.this.sortSpillException = t;
/* 1407:     */             }
/* 1408:     */             finally
/* 1409:     */             {
/* 1410:1509 */               MapTask.MapOutputBuffer.this.spillLock.lock();
/* 1411:1510 */               if (MapTask.MapOutputBuffer.this.bufend < MapTask.MapOutputBuffer.this.bufstart) {
/* 1412:1511 */                 MapTask.MapOutputBuffer.this.bufvoid = MapTask.MapOutputBuffer.this.kvbuffer.length;
/* 1413:     */               }
/* 1414:1513 */               MapTask.MapOutputBuffer.this.kvstart = MapTask.MapOutputBuffer.this.kvend;
/* 1415:1514 */               MapTask.MapOutputBuffer.this.bufstart = MapTask.MapOutputBuffer.this.bufend;
/* 1416:1515 */               MapTask.MapOutputBuffer.this.spillInProgress = false;
/* 1417:     */             }
/* 1418:     */           }
/* 1419:     */         }
/* 1420:     */         catch (InterruptedException e)
/* 1421:     */         {
/* 1422:1519 */           Thread.currentThread().interrupt();
/* 1423:     */         }
/* 1424:     */         finally
/* 1425:     */         {
/* 1426:1521 */           MapTask.MapOutputBuffer.this.spillLock.unlock();
/* 1427:1522 */           MapTask.MapOutputBuffer.this.spillThreadRunning = false;
/* 1428:     */         }
/* 1429:     */       }
/* 1430:     */     }
/* 1431:     */     
/* 1432:     */     private void checkSpillException()
/* 1433:     */       throws IOException
/* 1434:     */     {
/* 1435:1528 */       Throwable lspillException = this.sortSpillException;
/* 1436:1529 */       if (lspillException != null)
/* 1437:     */       {
/* 1438:1530 */         if ((lspillException instanceof Error))
/* 1439:     */         {
/* 1440:1531 */           String logMsg = "Task " + getTaskID() + " failed : " + StringUtils.stringifyException(lspillException);
/* 1441:     */           
/* 1442:1533 */           this.mapTask.reportFatalError(getTaskID(), lspillException, logMsg);
/* 1443:     */         }
/* 1444:1535 */         throw new IOException("Spill failed", lspillException);
/* 1445:     */       }
/* 1446:     */     }
/* 1447:     */     
/* 1448:     */     private void startSpill()
/* 1449:     */     {
/* 1450:1540 */       assert (!this.spillInProgress);
/* 1451:1541 */       this.kvend = ((this.kvindex + 4) % this.kvmeta.capacity());
/* 1452:1542 */       this.bufend = this.bufmark;
/* 1453:1543 */       this.spillInProgress = true;
/* 1454:1544 */       MapTask.LOG.info("Spilling map output");
/* 1455:1545 */       MapTask.LOG.info("bufstart = " + this.bufstart + "; bufend = " + this.bufmark + "; bufvoid = " + this.bufvoid);
/* 1456:     */       
/* 1457:1547 */       MapTask.LOG.info("kvstart = " + this.kvstart + "(" + this.kvstart * 4 + "); kvend = " + this.kvend + "(" + this.kvend * 4 + "); length = " + (distanceTo(this.kvend, this.kvstart, this.kvmeta.capacity()) + 1) + "/" + this.maxRec);
/* 1458:     */       
/* 1459:     */ 
/* 1460:     */ 
/* 1461:1551 */       this.spillReady.signal();
/* 1462:     */     }
/* 1463:     */     
/* 1464:     */     private void sortAndSpill()
/* 1465:     */       throws IOException, ClassNotFoundException, InterruptedException
/* 1466:     */     {
/* 1467:1558 */       long size = (this.bufend >= this.bufstart ? this.bufend - this.bufstart : this.bufvoid - this.bufend + this.bufstart) + this.partitions * 150;
/* 1468:     */       
/* 1469:     */ 
/* 1470:     */ 
/* 1471:1562 */       FSDataOutputStream out = null;
/* 1472:     */       try
/* 1473:     */       {
/* 1474:1565 */         SpillRecord spillRec = new SpillRecord(this.partitions);
/* 1475:1566 */         Path filename = this.mapOutputFile.getSpillFileForWrite(this.numSpills, size);
/* 1476:     */         
/* 1477:1568 */         out = this.rfs.create(filename);
/* 1478:     */         
/* 1479:1570 */         int mstart = this.kvend / 4;
/* 1480:1571 */         int mend = 1 + (this.kvstart >= this.kvend ? this.kvstart : this.kvmeta.capacity() + this.kvstart) / 4;
/* 1481:     */         
/* 1482:     */ 
/* 1483:     */ 
/* 1484:1575 */         this.sorter.sort(this, mstart, mend, this.reporter);
/* 1485:1576 */         int spindex = mstart;
/* 1486:1577 */         IndexRecord rec = new IndexRecord();
/* 1487:1578 */         MapOutputBuffer<K, V>.InMemValBytes value = new InMemValBytes();
/* 1488:1579 */         for (int i = 0; i < this.partitions; i++)
/* 1489:     */         {
/* 1490:1580 */           IFile.Writer<K, V> writer = null;
/* 1491:     */           try
/* 1492:     */           {
/* 1493:1582 */             long segmentStart = out.getPos();
/* 1494:1583 */             writer = new IFile.Writer(this.job, out, this.keyClass, this.valClass, this.codec, this.spilledRecordsCounter);
/* 1495:1585 */             if (this.combinerRunner == null)
/* 1496:     */             {
/* 1497:1587 */               DataInputBuffer key = new DataInputBuffer();
/* 1498:1589 */               while ((spindex < mend) && (this.kvmeta.get(offsetFor(spindex % this.maxRec) + 2) == i))
/* 1499:     */               {
/* 1500:1590 */                 int kvoff = offsetFor(spindex % this.maxRec);
/* 1501:1591 */                 int keystart = this.kvmeta.get(kvoff + 1);
/* 1502:1592 */                 int valstart = this.kvmeta.get(kvoff + 0);
/* 1503:1593 */                 key.reset(this.kvbuffer, keystart, valstart - keystart);
/* 1504:1594 */                 getVBytesForOffset(kvoff, value);
/* 1505:1595 */                 writer.append(key, value);
/* 1506:1596 */                 spindex++;
/* 1507:     */               }
/* 1508:     */             }
/* 1509:     */             else
/* 1510:     */             {
/* 1511:1599 */               int spstart = spindex;
/* 1512:1602 */               while ((spindex < mend) && (this.kvmeta.get(offsetFor(spindex % this.maxRec) + 2) == i)) {
/* 1513:1603 */                 spindex++;
/* 1514:     */               }
/* 1515:1607 */               if (spstart != spindex)
/* 1516:     */               {
/* 1517:1608 */                 this.combineCollector.setWriter(writer);
/* 1518:1609 */                 RawKeyValueIterator kvIter = new MRResultIterator(spstart, spindex);
/* 1519:     */                 
/* 1520:1611 */                 this.combinerRunner.combine(kvIter, this.combineCollector);
/* 1521:     */               }
/* 1522:     */             }
/* 1523:1616 */             writer.close();
/* 1524:     */             
/* 1525:     */ 
/* 1526:1619 */             rec.startOffset = segmentStart;
/* 1527:1620 */             rec.rawLength = writer.getRawLength();
/* 1528:1621 */             rec.partLength = writer.getCompressedLength();
/* 1529:1622 */             spillRec.putIndex(rec, i);
/* 1530:     */             
/* 1531:1624 */             writer = null;
/* 1532:1626 */             if (null != writer) {
/* 1533:1626 */               writer.close();
/* 1534:     */             }
/* 1535:     */           }
/* 1536:     */           finally
/* 1537:     */           {
/* 1538:1626 */             if (null != writer) {
/* 1539:1626 */               writer.close();
/* 1540:     */             }
/* 1541:     */           }
/* 1542:     */         }
/* 1543:1630 */         if (this.totalIndexCacheMemory >= this.indexCacheMemoryLimit)
/* 1544:     */         {
/* 1545:1632 */           Path indexFilename = this.mapOutputFile.getSpillIndexFileForWrite(this.numSpills, this.partitions * 24);
/* 1546:     */           
/* 1547:     */ 
/* 1548:1635 */           spillRec.writeToFile(indexFilename, this.job);
/* 1549:     */         }
/* 1550:     */         else
/* 1551:     */         {
/* 1552:1637 */           this.indexCacheList.add(spillRec);
/* 1553:1638 */           this.totalIndexCacheMemory += spillRec.size() * 24;
/* 1554:     */         }
/* 1555:1641 */         MapTask.LOG.info("Finished spill " + this.numSpills);
/* 1556:1642 */         this.numSpills += 1;
/* 1557:     */       }
/* 1558:     */       finally
/* 1559:     */       {
/* 1560:1644 */         if (out != null) {
/* 1561:1644 */           out.close();
/* 1562:     */         }
/* 1563:     */       }
/* 1564:     */     }
/* 1565:     */     
/* 1566:     */     private void spillSingleRecord(K key, V value, int partition)
/* 1567:     */       throws IOException
/* 1568:     */     {
/* 1569:1655 */       long size = this.kvbuffer.length + this.partitions * 150;
/* 1570:1656 */       FSDataOutputStream out = null;
/* 1571:     */       try
/* 1572:     */       {
/* 1573:1659 */         SpillRecord spillRec = new SpillRecord(this.partitions);
/* 1574:1660 */         Path filename = this.mapOutputFile.getSpillFileForWrite(this.numSpills, size);
/* 1575:     */         
/* 1576:1662 */         out = this.rfs.create(filename);
/* 1577:     */         
/* 1578:     */ 
/* 1579:1665 */         IndexRecord rec = new IndexRecord();
/* 1580:1666 */         for (int i = 0; i < this.partitions; i++)
/* 1581:     */         {
/* 1582:1667 */           IFile.Writer<K, V> writer = null;
/* 1583:     */           try
/* 1584:     */           {
/* 1585:1669 */             long segmentStart = out.getPos();
/* 1586:     */             
/* 1587:1671 */             writer = new IFile.Writer(this.job, out, this.keyClass, this.valClass, this.codec, this.spilledRecordsCounter);
/* 1588:1674 */             if (i == partition)
/* 1589:     */             {
/* 1590:1675 */               long recordStart = out.getPos();
/* 1591:1676 */               writer.append(key, value);
/* 1592:     */               
/* 1593:     */ 
/* 1594:1679 */               this.mapOutputByteCounter.increment(out.getPos() - recordStart);
/* 1595:     */             }
/* 1596:1681 */             writer.close();
/* 1597:     */             
/* 1598:     */ 
/* 1599:1684 */             rec.startOffset = segmentStart;
/* 1600:1685 */             rec.rawLength = writer.getRawLength();
/* 1601:1686 */             rec.partLength = writer.getCompressedLength();
/* 1602:1687 */             spillRec.putIndex(rec, i);
/* 1603:     */             
/* 1604:1689 */             writer = null;
/* 1605:     */           }
/* 1606:     */           catch (IOException e)
/* 1607:     */           {
/* 1608:1691 */             if (null != writer) {
/* 1609:1691 */               writer.close();
/* 1610:     */             }
/* 1611:1692 */             throw e;
/* 1612:     */           }
/* 1613:     */         }
/* 1614:1695 */         if (this.totalIndexCacheMemory >= this.indexCacheMemoryLimit)
/* 1615:     */         {
/* 1616:1697 */           Path indexFilename = this.mapOutputFile.getSpillIndexFileForWrite(this.numSpills, this.partitions * 24);
/* 1617:     */           
/* 1618:     */ 
/* 1619:1700 */           spillRec.writeToFile(indexFilename, this.job);
/* 1620:     */         }
/* 1621:     */         else
/* 1622:     */         {
/* 1623:1702 */           this.indexCacheList.add(spillRec);
/* 1624:1703 */           this.totalIndexCacheMemory += spillRec.size() * 24;
/* 1625:     */         }
/* 1626:1706 */         this.numSpills += 1;
/* 1627:     */       }
/* 1628:     */       finally
/* 1629:     */       {
/* 1630:1708 */         if (out != null) {
/* 1631:1708 */           out.close();
/* 1632:     */         }
/* 1633:     */       }
/* 1634:     */     }
/* 1635:     */     
/* 1636:     */     private void getVBytesForOffset(int kvoff, MapOutputBuffer<K, V>.InMemValBytes vbytes)
/* 1637:     */     {
/* 1638:1719 */       int vallen = this.kvmeta.get(kvoff + 3);
/* 1639:1720 */       assert (vallen >= 0);
/* 1640:1721 */       vbytes.reset(this.kvbuffer, this.kvmeta.get(kvoff + 0), vallen);
/* 1641:     */     }
/* 1642:     */     
/* 1643:     */     protected class InMemValBytes
/* 1644:     */       extends DataInputBuffer
/* 1645:     */     {
/* 1646:     */       private byte[] buffer;
/* 1647:     */       private int start;
/* 1648:     */       private int length;
/* 1649:     */       
/* 1650:     */       protected InMemValBytes() {}
/* 1651:     */       
/* 1652:     */       public void reset(byte[] buffer, int start, int length)
/* 1653:     */       {
/* 1654:1733 */         this.buffer = buffer;
/* 1655:1734 */         this.start = start;
/* 1656:1735 */         this.length = length;
/* 1657:1737 */         if (start + length > MapTask.MapOutputBuffer.this.bufvoid)
/* 1658:     */         {
/* 1659:1738 */           this.buffer = new byte[this.length];
/* 1660:1739 */           int taillen = MapTask.MapOutputBuffer.this.bufvoid - start;
/* 1661:1740 */           System.arraycopy(buffer, start, this.buffer, 0, taillen);
/* 1662:1741 */           System.arraycopy(buffer, 0, this.buffer, taillen, length - taillen);
/* 1663:1742 */           this.start = 0;
/* 1664:     */         }
/* 1665:1745 */         super.reset(this.buffer, this.start, this.length);
/* 1666:     */       }
/* 1667:     */     }
/* 1668:     */     
/* 1669:     */     protected class MRResultIterator
/* 1670:     */       implements RawKeyValueIterator
/* 1671:     */     {
/* 1672:1750 */       private final DataInputBuffer keybuf = new DataInputBuffer();
/* 1673:1751 */       private final MapTask.MapOutputBuffer<K, V>.InMemValBytes vbytes = new MapTask.MapOutputBuffer.InMemValBytes(MapTask.MapOutputBuffer.this);
/* 1674:     */       private final int end;
/* 1675:     */       private int current;
/* 1676:     */       
/* 1677:     */       public MRResultIterator(int start, int end)
/* 1678:     */       {
/* 1679:1755 */         this.end = end;
/* 1680:1756 */         this.current = (start - 1);
/* 1681:     */       }
/* 1682:     */       
/* 1683:     */       public boolean next()
/* 1684:     */         throws IOException
/* 1685:     */       {
/* 1686:1759 */         return ++this.current < this.end;
/* 1687:     */       }
/* 1688:     */       
/* 1689:     */       public DataInputBuffer getKey()
/* 1690:     */         throws IOException
/* 1691:     */       {
/* 1692:1762 */         int kvoff = MapTask.MapOutputBuffer.this.offsetFor(this.current % MapTask.MapOutputBuffer.this.maxRec);
/* 1693:1763 */         this.keybuf.reset(MapTask.MapOutputBuffer.this.kvbuffer, MapTask.MapOutputBuffer.this.kvmeta.get(kvoff + 1), MapTask.MapOutputBuffer.this.kvmeta.get(kvoff + 0) - MapTask.MapOutputBuffer.this.kvmeta.get(kvoff + 1));
/* 1694:     */         
/* 1695:1765 */         return this.keybuf;
/* 1696:     */       }
/* 1697:     */       
/* 1698:     */       public DataInputBuffer getValue()
/* 1699:     */         throws IOException
/* 1700:     */       {
/* 1701:1768 */         MapTask.MapOutputBuffer.this.getVBytesForOffset(MapTask.MapOutputBuffer.this.offsetFor(this.current % MapTask.MapOutputBuffer.this.maxRec), this.vbytes);
/* 1702:1769 */         return this.vbytes;
/* 1703:     */       }
/* 1704:     */       
/* 1705:     */       public Progress getProgress()
/* 1706:     */       {
/* 1707:1772 */         return null;
/* 1708:     */       }
/* 1709:     */       
/* 1710:     */       public void close() {}
/* 1711:     */     }
/* 1712:     */     
/* 1713:     */     private void mergeParts()
/* 1714:     */       throws IOException, InterruptedException, ClassNotFoundException
/* 1715:     */     {
/* 1716:1780 */       long finalOutFileSize = 0L;
/* 1717:1781 */       long finalIndexFileSize = 0L;
/* 1718:1782 */       Path[] filename = new Path[this.numSpills];
/* 1719:1783 */       TaskAttemptID mapId = getTaskID();
/* 1720:1785 */       for (int i = 0; i < this.numSpills; i++)
/* 1721:     */       {
/* 1722:1786 */         filename[i] = this.mapOutputFile.getSpillFile(i);
/* 1723:1787 */         finalOutFileSize += this.rfs.getFileStatus(filename[i]).getLen();
/* 1724:     */       }
/* 1725:1789 */       if (this.numSpills == 1)
/* 1726:     */       {
/* 1727:1790 */         sameVolRename(filename[0], this.mapOutputFile.getOutputFileForWriteInVolume(filename[0]));
/* 1728:1792 */         if (this.indexCacheList.size() == 0) {
/* 1729:1793 */           sameVolRename(this.mapOutputFile.getSpillIndexFile(0), this.mapOutputFile.getOutputIndexFileForWriteInVolume(filename[0]));
/* 1730:     */         } else {
/* 1731:1796 */           ((SpillRecord)this.indexCacheList.get(0)).writeToFile(this.mapOutputFile.getOutputIndexFileForWriteInVolume(filename[0]), this.job);
/* 1732:     */         }
/* 1733:1799 */         this.sortPhase.complete();
/* 1734:1800 */         return;
/* 1735:     */       }
/* 1736:1804 */       for (int i = this.indexCacheList.size(); i < this.numSpills; i++)
/* 1737:     */       {
/* 1738:1805 */         Path indexFileName = this.mapOutputFile.getSpillIndexFile(i);
/* 1739:1806 */         this.indexCacheList.add(new SpillRecord(indexFileName, this.job));
/* 1740:     */       }
/* 1741:1811 */       finalOutFileSize += this.partitions * 150;
/* 1742:1812 */       finalIndexFileSize = this.partitions * 24;
/* 1743:1813 */       Path finalOutputFile = this.mapOutputFile.getOutputFileForWrite(finalOutFileSize);
/* 1744:     */       
/* 1745:1815 */       Path finalIndexFile = this.mapOutputFile.getOutputIndexFileForWrite(finalIndexFileSize);
/* 1746:     */       
/* 1747:     */ 
/* 1748:     */ 
/* 1749:1819 */       FSDataOutputStream finalOut = this.rfs.create(finalOutputFile, true, 4096);
/* 1750:1821 */       if (this.numSpills == 0)
/* 1751:     */       {
/* 1752:1823 */         IndexRecord rec = new IndexRecord();
/* 1753:1824 */         SpillRecord sr = new SpillRecord(this.partitions);
/* 1754:     */         try
/* 1755:     */         {
/* 1756:1826 */           for (int i = 0; i < this.partitions; i++)
/* 1757:     */           {
/* 1758:1827 */             long segmentStart = finalOut.getPos();
/* 1759:1828 */             IFile.Writer<K, V> writer = new IFile.Writer(this.job, finalOut, this.keyClass, this.valClass, this.codec, null);
/* 1760:     */             
/* 1761:1830 */             writer.close();
/* 1762:1831 */             rec.startOffset = segmentStart;
/* 1763:1832 */             rec.rawLength = writer.getRawLength();
/* 1764:1833 */             rec.partLength = writer.getCompressedLength();
/* 1765:1834 */             sr.putIndex(rec, i);
/* 1766:     */           }
/* 1767:1836 */           sr.writeToFile(finalIndexFile, this.job);
/* 1768:     */         }
/* 1769:     */         finally
/* 1770:     */         {
/* 1771:1838 */           finalOut.close();
/* 1772:     */         }
/* 1773:1840 */         this.sortPhase.complete();
/* 1774:1841 */         return;
/* 1775:     */       }
/* 1776:1844 */       this.sortPhase.addPhases(this.partitions);
/* 1777:     */       
/* 1778:1846 */       IndexRecord rec = new IndexRecord();
/* 1779:1847 */       SpillRecord spillRec = new SpillRecord(this.partitions);
/* 1780:1848 */       for (int parts = 0; parts < this.partitions; parts++)
/* 1781:     */       {
/* 1782:1850 */         List<Merger.Segment<K, V>> segmentList = new ArrayList(this.numSpills);
/* 1783:1852 */         for (int i = 0; i < this.numSpills; i++)
/* 1784:     */         {
/* 1785:1853 */           IndexRecord indexRecord = ((SpillRecord)this.indexCacheList.get(i)).getIndex(parts);
/* 1786:     */           
/* 1787:1855 */           Object s = new Merger.Segment(this.job, this.rfs, filename[i], indexRecord.startOffset, indexRecord.partLength, this.codec, true);
/* 1788:     */           
/* 1789:     */ 
/* 1790:1858 */           segmentList.add(i, s);
/* 1791:1860 */           if (MapTask.LOG.isDebugEnabled()) {
/* 1792:1861 */             MapTask.LOG.debug("MapId=" + mapId + " Reducer=" + parts + "Spill =" + i + "(" + indexRecord.startOffset + "," + indexRecord.rawLength + ", " + indexRecord.partLength + ")");
/* 1793:     */           }
/* 1794:     */         }
/* 1795:1867 */         int mergeFactor = this.job.getInt("mapreduce.task.io.sort.factor", 100);
/* 1796:     */         
/* 1797:1869 */         boolean sortSegments = segmentList.size() > mergeFactor;
/* 1798:     */         
/* 1799:     */ 
/* 1800:1872 */         RawKeyValueIterator kvIter = Merger.merge(this.job, this.rfs, this.keyClass, this.valClass, this.codec, segmentList, mergeFactor, new Path(mapId.toString()), this.job.getOutputKeyComparator(), this.reporter, sortSegments, null, this.spilledRecordsCounter, this.sortPhase.phase(), TaskType.MAP);
/* 1801:     */         
/* 1802:     */ 
/* 1803:     */ 
/* 1804:     */ 
/* 1805:     */ 
/* 1806:     */ 
/* 1807:     */ 
/* 1808:     */ 
/* 1809:1881 */         long segmentStart = finalOut.getPos();
/* 1810:1882 */         IFile.Writer<K, V> writer = new IFile.Writer(this.job, finalOut, this.keyClass, this.valClass, this.codec, this.spilledRecordsCounter);
/* 1811:1885 */         if ((this.combinerRunner == null) || (this.numSpills < this.minSpillsForCombine))
/* 1812:     */         {
/* 1813:1886 */           Merger.writeFile(kvIter, writer, this.reporter, this.job);
/* 1814:     */         }
/* 1815:     */         else
/* 1816:     */         {
/* 1817:1888 */           this.combineCollector.setWriter(writer);
/* 1818:1889 */           this.combinerRunner.combine(kvIter, this.combineCollector);
/* 1819:     */         }
/* 1820:1893 */         writer.close();
/* 1821:     */         
/* 1822:1895 */         this.sortPhase.startNextPhase();
/* 1823:     */         
/* 1824:     */ 
/* 1825:1898 */         rec.startOffset = segmentStart;
/* 1826:1899 */         rec.rawLength = writer.getRawLength();
/* 1827:1900 */         rec.partLength = writer.getCompressedLength();
/* 1828:1901 */         spillRec.putIndex(rec, parts);
/* 1829:     */       }
/* 1830:1903 */       spillRec.writeToFile(finalIndexFile, this.job);
/* 1831:1904 */       finalOut.close();
/* 1832:1905 */       for (int i = 0; i < this.numSpills; i++) {
/* 1833:1906 */         this.rfs.delete(filename[i], true);
/* 1834:     */       }
/* 1835:     */     }
/* 1836:     */     
/* 1837:     */     private void sameVolRename(Path srcPath, Path dstPath)
/* 1838:     */       throws IOException
/* 1839:     */     {
/* 1840:1919 */       RawLocalFileSystem rfs = (RawLocalFileSystem)this.rfs;
/* 1841:1920 */       File src = rfs.pathToFile(srcPath);
/* 1842:1921 */       File dst = rfs.pathToFile(dstPath);
/* 1843:1922 */       if ((!dst.getParentFile().exists()) && 
/* 1844:1923 */         (!dst.getParentFile().mkdirs())) {
/* 1845:1924 */         throw new IOException("Unable to rename " + src + " to " + dst + ": couldn't create parent directory");
/* 1846:     */       }
/* 1847:1929 */       if (!src.renameTo(dst)) {
/* 1848:1930 */         throw new IOException("Unable to rename " + src + " to " + dst);
/* 1849:     */       }
/* 1850:     */     }
/* 1851:     */   }
/* 1852:     */   
/* 1853:     */   private static class MapBufferTooSmallException
/* 1854:     */     extends IOException
/* 1855:     */   {
/* 1856:     */     public MapBufferTooSmallException(String s)
/* 1857:     */     {
/* 1858:1942 */       super();
/* 1859:     */     }
/* 1860:     */   }
/* 1861:     */   
/* 1862:     */   private <INKEY, INVALUE, OUTKEY, OUTVALUE> void closeQuietly(RecordReader<INKEY, INVALUE> c)
/* 1863:     */   {
/* 1864:1948 */     if (c != null) {
/* 1865:     */       try
/* 1866:     */       {
/* 1867:1950 */         c.close();
/* 1868:     */       }
/* 1869:     */       catch (IOException ie)
/* 1870:     */       {
/* 1871:1953 */         LOG.info("Ignoring exception during close for " + c, ie);
/* 1872:     */       }
/* 1873:     */     }
/* 1874:     */   }
/* 1875:     */   
/* 1876:     */   private <OUTKEY, OUTVALUE> void closeQuietly(MapOutputCollector<OUTKEY, OUTVALUE> c)
/* 1877:     */   {
/* 1878:1960 */     if (c != null) {
/* 1879:     */       try
/* 1880:     */       {
/* 1881:1962 */         c.close();
/* 1882:     */       }
/* 1883:     */       catch (Exception ie)
/* 1884:     */       {
/* 1885:1965 */         LOG.info("Ignoring exception during close for " + c, ie);
/* 1886:     */       }
/* 1887:     */     }
/* 1888:     */   }
/* 1889:     */   
/* 1890:     */   private <INKEY, INVALUE, OUTKEY, OUTVALUE> void closeQuietly(org.apache.hadoop.mapreduce.RecordReader<INKEY, INVALUE> c)
/* 1891:     */   {
/* 1892:1973 */     if (c != null) {
/* 1893:     */       try
/* 1894:     */       {
/* 1895:1975 */         c.close();
/* 1896:     */       }
/* 1897:     */       catch (Exception ie)
/* 1898:     */       {
/* 1899:1978 */         LOG.info("Ignoring exception during close for " + c, ie);
/* 1900:     */       }
/* 1901:     */     }
/* 1902:     */   }
/* 1903:     */   
/* 1904:     */   private <INKEY, INVALUE, OUTKEY, OUTVALUE> void closeQuietly(org.apache.hadoop.mapreduce.RecordWriter<OUTKEY, OUTVALUE> c, Mapper<INKEY, INVALUE, OUTKEY, OUTVALUE>.Context mapperContext)
/* 1905:     */   {
/* 1906:1988 */     if (c != null) {
/* 1907:     */       try
/* 1908:     */       {
/* 1909:1990 */         c.close(mapperContext);
/* 1910:     */       }
/* 1911:     */       catch (Exception ie)
/* 1912:     */       {
/* 1913:1993 */         LOG.info("Ignoring exception during close for " + c, ie);
/* 1914:     */       }
/* 1915:     */     }
/* 1916:     */   }
/* 1917:     */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.MapTask
 * JD-Core Version:    0.7.0.1
 */