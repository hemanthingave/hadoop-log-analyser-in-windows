/*    1:     */ package org.apache.hadoop.mapred;
/*    2:     */ 
/*    3:     */ import java.io.DataInput;
/*    4:     */ import java.io.DataOutput;
/*    5:     */ import java.io.IOException;
/*    6:     */ import java.lang.management.GarbageCollectorMXBean;
/*    7:     */ import java.lang.management.ManagementFactory;
/*    8:     */ import java.net.URI;
/*    9:     */ import java.text.NumberFormat;
/*   10:     */ import java.util.ArrayList;
/*   11:     */ import java.util.HashMap;
/*   12:     */ import java.util.Iterator;
/*   13:     */ import java.util.List;
/*   14:     */ import java.util.Map;
/*   15:     */ import java.util.Map.Entry;
/*   16:     */ import java.util.NoSuchElementException;
/*   17:     */ import java.util.concurrent.atomic.AtomicBoolean;
/*   18:     */ import javax.crypto.SecretKey;
/*   19:     */ import org.apache.commons.logging.Log;
/*   20:     */ import org.apache.commons.logging.LogFactory;
/*   21:     */ import org.apache.hadoop.classification.InterfaceAudience.LimitedPrivate;
/*   22:     */ import org.apache.hadoop.classification.InterfaceAudience.Private;
/*   23:     */ import org.apache.hadoop.classification.InterfaceStability.Unstable;
/*   24:     */ import org.apache.hadoop.conf.Configurable;
/*   25:     */ import org.apache.hadoop.conf.Configuration;
/*   26:     */ import org.apache.hadoop.fs.FileStatus;
/*   27:     */ import org.apache.hadoop.fs.FileSystem;
/*   28:     */ import org.apache.hadoop.fs.FileSystem.Statistics;
/*   29:     */ import org.apache.hadoop.fs.LocalDirAllocator;
/*   30:     */ import org.apache.hadoop.fs.Path;
/*   31:     */ import org.apache.hadoop.io.BytesWritable;
/*   32:     */ import org.apache.hadoop.io.DataInputBuffer;
/*   33:     */ import org.apache.hadoop.io.RawComparator;
/*   34:     */ import org.apache.hadoop.io.Text;
/*   35:     */ import org.apache.hadoop.io.Writable;
/*   36:     */ import org.apache.hadoop.io.WritableUtils;
/*   37:     */ import org.apache.hadoop.io.serializer.Deserializer;
/*   38:     */ import org.apache.hadoop.io.serializer.SerializationFactory;
/*   39:     */ import org.apache.hadoop.mapreduce.Counter;
/*   40:     */ import org.apache.hadoop.mapreduce.FileSystemCounter;
/*   41:     */ import org.apache.hadoop.mapreduce.JobStatus.State;
/*   42:     */ import org.apache.hadoop.mapreduce.OutputFormat;
/*   43:     */ import org.apache.hadoop.mapreduce.RecordWriter;
/*   44:     */ import org.apache.hadoop.mapreduce.ReduceContext;
/*   45:     */ import org.apache.hadoop.mapreduce.Reducer.Context;
/*   46:     */ import org.apache.hadoop.mapreduce.StatusReporter;
/*   47:     */ import org.apache.hadoop.mapreduce.TaskCounter;
/*   48:     */ import org.apache.hadoop.mapreduce.lib.reduce.WrappedReducer;
/*   49:     */ import org.apache.hadoop.mapreduce.task.ReduceContextImpl;
/*   50:     */ import org.apache.hadoop.net.NetUtils;
/*   51:     */ import org.apache.hadoop.util.Progress;
/*   52:     */ import org.apache.hadoop.util.Progressable;
/*   53:     */ import org.apache.hadoop.util.ReflectionUtils;
/*   54:     */ import org.apache.hadoop.util.StringInterner;
/*   55:     */ import org.apache.hadoop.util.StringUtils;
/*   56:     */ import org.apache.hadoop.yarn.util.ResourceCalculatorProcessTree;
/*   57:     */ 
/*   58:     */ @InterfaceAudience.LimitedPrivate({"MapReduce"})
/*   59:     */ @InterfaceStability.Unstable
/*   60:     */ public abstract class Task
/*   61:     */   implements Writable, Configurable
/*   62:     */ {
/*   63:  78 */   private static final Log LOG = LogFactory.getLog(Task.class);
/*   64:  81 */   public static String MERGED_OUTPUT_PREFIX = ".merged";
/*   65:     */   public static final long DEFAULT_COMBINE_RECORDS_BEFORE_PROGRESS = 10000L;
/*   66:     */   protected static final String FILESYSTEM_COUNTER_GROUP = "FileSystemCounters";
/*   67:     */   
/*   68:     */   @Deprecated
/*   69:     */   public static enum Counter
/*   70:     */   {
/*   71:  89 */     MAP_INPUT_RECORDS,  MAP_OUTPUT_RECORDS,  MAP_SKIPPED_RECORDS,  MAP_INPUT_BYTES,  MAP_OUTPUT_BYTES,  MAP_OUTPUT_MATERIALIZED_BYTES,  COMBINE_INPUT_RECORDS,  COMBINE_OUTPUT_RECORDS,  REDUCE_INPUT_GROUPS,  REDUCE_SHUFFLE_BYTES,  REDUCE_INPUT_RECORDS,  REDUCE_OUTPUT_RECORDS,  REDUCE_SKIPPED_GROUPS,  REDUCE_SKIPPED_RECORDS,  SPILLED_RECORDS,  SPLIT_RAW_BYTES,  CPU_MILLISECONDS,  PHYSICAL_MEMORY_BYTES,  VIRTUAL_MEMORY_BYTES,  COMMITTED_HEAP_BYTES;
/*   72:     */     
/*   73:     */     private Counter() {}
/*   74:     */   }
/*   75:     */   
/*   76:     */   protected static String[] getFileSystemCounterNames(String uriScheme)
/*   77:     */   {
/*   78: 117 */     String scheme = uriScheme.toUpperCase();
/*   79: 118 */     return new String[] { scheme + "_BYTES_READ", scheme + "_BYTES_WRITTEN" };
/*   80:     */   }
/*   81:     */   
/*   82: 132 */   private static final NumberFormat NUMBER_FORMAT = NumberFormat.getInstance();
/*   83:     */   private String jobFile;
/*   84:     */   private String user;
/*   85:     */   private TaskAttemptID taskId;
/*   86:     */   private int partition;
/*   87:     */   TaskStatus taskStatus;
/*   88:     */   protected JobStatus.State jobRunStateForCleanup;
/*   89:     */   
/*   90:     */   static
/*   91:     */   {
/*   92: 134 */     NUMBER_FORMAT.setMinimumIntegerDigits(5);
/*   93: 135 */     NUMBER_FORMAT.setGroupingUsed(false);
/*   94:     */   }
/*   95:     */   
/*   96:     */   static synchronized String getOutputName(int partition)
/*   97:     */   {
/*   98: 139 */     return "part-" + NUMBER_FORMAT.format(partition);
/*   99:     */   }
/*  100:     */   
/*  101: 152 */   protected boolean jobCleanup = false;
/*  102: 153 */   protected boolean jobSetup = false;
/*  103: 154 */   protected boolean taskCleanup = false;
/*  104: 159 */   protected BytesWritable extraData = new BytesWritable();
/*  105: 162 */   private SortedRanges skipRanges = new SortedRanges();
/*  106: 163 */   private boolean skipping = false;
/*  107: 164 */   private boolean writeSkipRecs = true;
/*  108:     */   private volatile long currentRecStartIndex;
/*  109: 168 */   private Iterator<Long> currentRecIndexIterator = this.skipRanges.skipRangeIterator();
/*  110:     */   private ResourceCalculatorProcessTree pTree;
/*  111: 172 */   private long initCpuCumulativeTime = 0L;
/*  112:     */   protected JobConf conf;
/*  113:     */   protected MapOutputFile mapOutputFile;
/*  114:     */   protected LocalDirAllocator lDirAlloc;
/*  115:     */   private static final int MAX_RETRIES = 10;
/*  116:     */   protected JobContext jobContext;
/*  117:     */   protected TaskAttemptContext taskContext;
/*  118:     */   protected OutputFormat<?, ?> outputFormat;
/*  119:     */   protected org.apache.hadoop.mapreduce.OutputCommitter committer;
/*  120:     */   protected final Counters.Counter spilledRecordsCounter;
/*  121:     */   protected final Counters.Counter failedShuffleCounter;
/*  122:     */   protected final Counters.Counter mergedMapOutputsCounter;
/*  123:     */   private int numSlotsRequired;
/*  124:     */   protected TaskUmbilicalProtocol umbilical;
/*  125:     */   protected SecretKey tokenSecret;
/*  126:     */   protected SecretKey shuffleSecret;
/*  127:     */   protected GcTimeUpdater gcUpdater;
/*  128:     */   public static final int PROGRESS_INTERVAL = 3000;
/*  129:     */   
/*  130:     */   public Task()
/*  131:     */   {
/*  132: 196 */     this.taskStatus = TaskStatus.createTaskStatus(isMapTask());
/*  133: 197 */     this.taskId = new TaskAttemptID();
/*  134: 198 */     this.spilledRecordsCounter = ((Counters.Counter)this.counters.findCounter(TaskCounter.SPILLED_RECORDS));
/*  135:     */     
/*  136: 200 */     this.failedShuffleCounter = ((Counters.Counter)this.counters.findCounter(TaskCounter.FAILED_SHUFFLE));
/*  137:     */     
/*  138: 202 */     this.mergedMapOutputsCounter = ((Counters.Counter)this.counters.findCounter(TaskCounter.MERGED_MAP_OUTPUTS));
/*  139:     */     
/*  140: 204 */     this.gcUpdater = new GcTimeUpdater();
/*  141:     */   }
/*  142:     */   
/*  143:     */   public Task(String jobFile, TaskAttemptID taskId, int partition, int numSlotsRequired)
/*  144:     */   {
/*  145: 209 */     this.jobFile = jobFile;
/*  146: 210 */     this.taskId = taskId;
/*  147:     */     
/*  148: 212 */     this.partition = partition;
/*  149: 213 */     this.numSlotsRequired = numSlotsRequired;
/*  150: 214 */     this.taskStatus = TaskStatus.createTaskStatus(isMapTask(), this.taskId, 0.0F, numSlotsRequired, TaskStatus.State.UNASSIGNED, "", "", "", isMapTask() ? TaskStatus.Phase.MAP : TaskStatus.Phase.SHUFFLE, this.counters);
/*  151:     */     
/*  152:     */ 
/*  153:     */ 
/*  154:     */ 
/*  155:     */ 
/*  156:     */ 
/*  157:     */ 
/*  158: 222 */     this.spilledRecordsCounter = ((Counters.Counter)this.counters.findCounter(TaskCounter.SPILLED_RECORDS));
/*  159: 223 */     this.failedShuffleCounter = ((Counters.Counter)this.counters.findCounter(TaskCounter.FAILED_SHUFFLE));
/*  160: 224 */     this.mergedMapOutputsCounter = ((Counters.Counter)this.counters.findCounter(TaskCounter.MERGED_MAP_OUTPUTS));
/*  161:     */     
/*  162: 226 */     this.gcUpdater = new GcTimeUpdater();
/*  163:     */   }
/*  164:     */   
/*  165:     */   public void setJobFile(String jobFile)
/*  166:     */   {
/*  167: 232 */     this.jobFile = jobFile;
/*  168:     */   }
/*  169:     */   
/*  170:     */   public String getJobFile()
/*  171:     */   {
/*  172: 233 */     return this.jobFile;
/*  173:     */   }
/*  174:     */   
/*  175:     */   public TaskAttemptID getTaskID()
/*  176:     */   {
/*  177: 234 */     return this.taskId;
/*  178:     */   }
/*  179:     */   
/*  180:     */   public int getNumSlotsRequired()
/*  181:     */   {
/*  182: 236 */     return this.numSlotsRequired;
/*  183:     */   }
/*  184:     */   
/*  185:     */   Counters getCounters()
/*  186:     */   {
/*  187: 239 */     return this.counters;
/*  188:     */   }
/*  189:     */   
/*  190:     */   public JobID getJobID()
/*  191:     */   {
/*  192: 246 */     return this.taskId.getJobID();
/*  193:     */   }
/*  194:     */   
/*  195:     */   public void setJobTokenSecret(SecretKey tokenSecret)
/*  196:     */   {
/*  197: 254 */     this.tokenSecret = tokenSecret;
/*  198:     */   }
/*  199:     */   
/*  200:     */   public SecretKey getJobTokenSecret()
/*  201:     */   {
/*  202: 262 */     return this.tokenSecret;
/*  203:     */   }
/*  204:     */   
/*  205:     */   public void setShuffleSecret(SecretKey shuffleSecret)
/*  206:     */   {
/*  207: 270 */     this.shuffleSecret = shuffleSecret;
/*  208:     */   }
/*  209:     */   
/*  210:     */   public SecretKey getShuffleSecret()
/*  211:     */   {
/*  212: 278 */     return this.shuffleSecret;
/*  213:     */   }
/*  214:     */   
/*  215:     */   public int getPartition()
/*  216:     */   {
/*  217: 286 */     return this.partition;
/*  218:     */   }
/*  219:     */   
/*  220:     */   public synchronized TaskStatus.Phase getPhase()
/*  221:     */   {
/*  222: 294 */     return this.taskStatus.getPhase();
/*  223:     */   }
/*  224:     */   
/*  225:     */   protected synchronized void setPhase(TaskStatus.Phase phase)
/*  226:     */   {
/*  227: 301 */     this.taskStatus.setPhase(phase);
/*  228:     */   }
/*  229:     */   
/*  230:     */   protected boolean toWriteSkipRecs()
/*  231:     */   {
/*  232: 308 */     return this.writeSkipRecs;
/*  233:     */   }
/*  234:     */   
/*  235:     */   protected void setWriteSkipRecs(boolean writeSkipRecs)
/*  236:     */   {
/*  237: 315 */     this.writeSkipRecs = writeSkipRecs;
/*  238:     */   }
/*  239:     */   
/*  240:     */   protected void reportFatalError(TaskAttemptID id, Throwable throwable, String logMsg)
/*  241:     */   {
/*  242: 323 */     LOG.fatal(logMsg);
/*  243: 324 */     Throwable tCause = throwable.getCause();
/*  244: 325 */     String cause = tCause == null ? StringUtils.stringifyException(throwable) : StringUtils.stringifyException(tCause);
/*  245:     */     try
/*  246:     */     {
/*  247: 329 */       this.umbilical.fatalError(id, cause);
/*  248:     */     }
/*  249:     */     catch (IOException ioe)
/*  250:     */     {
/*  251: 331 */       LOG.fatal("Failed to contact the tasktracker", ioe);
/*  252: 332 */       System.exit(-1);
/*  253:     */     }
/*  254:     */   }
/*  255:     */   
/*  256:     */   protected static List<FileSystem.Statistics> getFsStatistics(Path path, Configuration conf)
/*  257:     */     throws IOException
/*  258:     */   {
/*  259: 346 */     List<FileSystem.Statistics> matchedStats = new ArrayList();
/*  260: 347 */     path = path.getFileSystem(conf).makeQualified(path);
/*  261: 348 */     String scheme = path.toUri().getScheme();
/*  262: 349 */     for (FileSystem.Statistics stats : FileSystem.getAllStatistics()) {
/*  263: 350 */       if (stats.getScheme().equals(scheme)) {
/*  264: 351 */         matchedStats.add(stats);
/*  265:     */       }
/*  266:     */     }
/*  267: 354 */     return matchedStats;
/*  268:     */   }
/*  269:     */   
/*  270:     */   public SortedRanges getSkipRanges()
/*  271:     */   {
/*  272: 361 */     return this.skipRanges;
/*  273:     */   }
/*  274:     */   
/*  275:     */   public void setSkipRanges(SortedRanges skipRanges)
/*  276:     */   {
/*  277: 368 */     this.skipRanges = skipRanges;
/*  278:     */   }
/*  279:     */   
/*  280:     */   public boolean isSkipping()
/*  281:     */   {
/*  282: 375 */     return this.skipping;
/*  283:     */   }
/*  284:     */   
/*  285:     */   public void setSkipping(boolean skipping)
/*  286:     */   {
/*  287: 383 */     this.skipping = skipping;
/*  288:     */   }
/*  289:     */   
/*  290:     */   synchronized TaskStatus.State getState()
/*  291:     */   {
/*  292: 393 */     return this.taskStatus.getRunState();
/*  293:     */   }
/*  294:     */   
/*  295:     */   synchronized void setState(TaskStatus.State state)
/*  296:     */   {
/*  297: 400 */     this.taskStatus.setRunState(state);
/*  298:     */   }
/*  299:     */   
/*  300:     */   void setTaskCleanupTask()
/*  301:     */   {
/*  302: 404 */     this.taskCleanup = true;
/*  303:     */   }
/*  304:     */   
/*  305:     */   boolean isTaskCleanupTask()
/*  306:     */   {
/*  307: 408 */     return this.taskCleanup;
/*  308:     */   }
/*  309:     */   
/*  310:     */   boolean isJobCleanupTask()
/*  311:     */   {
/*  312: 412 */     return this.jobCleanup;
/*  313:     */   }
/*  314:     */   
/*  315:     */   boolean isJobAbortTask()
/*  316:     */   {
/*  317: 418 */     return (isJobCleanupTask()) && ((this.jobRunStateForCleanup == JobStatus.State.KILLED) || (this.jobRunStateForCleanup == JobStatus.State.FAILED));
/*  318:     */   }
/*  319:     */   
/*  320:     */   boolean isJobSetupTask()
/*  321:     */   {
/*  322: 424 */     return this.jobSetup;
/*  323:     */   }
/*  324:     */   
/*  325:     */   void setJobSetupTask()
/*  326:     */   {
/*  327: 428 */     this.jobSetup = true;
/*  328:     */   }
/*  329:     */   
/*  330:     */   void setJobCleanupTask()
/*  331:     */   {
/*  332: 432 */     this.jobCleanup = true;
/*  333:     */   }
/*  334:     */   
/*  335:     */   void setJobCleanupTaskState(JobStatus.State status)
/*  336:     */   {
/*  337: 440 */     this.jobRunStateForCleanup = status;
/*  338:     */   }
/*  339:     */   
/*  340:     */   boolean isMapOrReduce()
/*  341:     */   {
/*  342: 444 */     return (!this.jobSetup) && (!this.jobCleanup) && (!this.taskCleanup);
/*  343:     */   }
/*  344:     */   
/*  345:     */   String getUser()
/*  346:     */   {
/*  347: 455 */     return this.user;
/*  348:     */   }
/*  349:     */   
/*  350:     */   void setUser(String user)
/*  351:     */   {
/*  352: 459 */     this.user = user;
/*  353:     */   }
/*  354:     */   
/*  355:     */   public void write(DataOutput out)
/*  356:     */     throws IOException
/*  357:     */   {
/*  358: 467 */     Text.writeString(out, this.jobFile);
/*  359: 468 */     this.taskId.write(out);
/*  360: 469 */     out.writeInt(this.partition);
/*  361: 470 */     out.writeInt(this.numSlotsRequired);
/*  362: 471 */     this.taskStatus.write(out);
/*  363: 472 */     this.skipRanges.write(out);
/*  364: 473 */     out.writeBoolean(this.skipping);
/*  365: 474 */     out.writeBoolean(this.jobCleanup);
/*  366: 475 */     if (this.jobCleanup) {
/*  367: 476 */       WritableUtils.writeEnum(out, this.jobRunStateForCleanup);
/*  368:     */     }
/*  369: 478 */     out.writeBoolean(this.jobSetup);
/*  370: 479 */     out.writeBoolean(this.writeSkipRecs);
/*  371: 480 */     out.writeBoolean(this.taskCleanup);
/*  372: 481 */     Text.writeString(out, this.user);
/*  373: 482 */     this.extraData.write(out);
/*  374:     */   }
/*  375:     */   
/*  376:     */   public void readFields(DataInput in)
/*  377:     */     throws IOException
/*  378:     */   {
/*  379: 486 */     this.jobFile = StringInterner.weakIntern(Text.readString(in));
/*  380: 487 */     this.taskId = TaskAttemptID.read(in);
/*  381: 488 */     this.partition = in.readInt();
/*  382: 489 */     this.numSlotsRequired = in.readInt();
/*  383: 490 */     this.taskStatus.readFields(in);
/*  384: 491 */     this.skipRanges.readFields(in);
/*  385: 492 */     this.currentRecIndexIterator = this.skipRanges.skipRangeIterator();
/*  386: 493 */     this.currentRecStartIndex = ((Long)this.currentRecIndexIterator.next()).longValue();
/*  387: 494 */     this.skipping = in.readBoolean();
/*  388: 495 */     this.jobCleanup = in.readBoolean();
/*  389: 496 */     if (this.jobCleanup) {
/*  390: 497 */       this.jobRunStateForCleanup = ((JobStatus.State)WritableUtils.readEnum(in, JobStatus.State.class));
/*  391:     */     }
/*  392: 500 */     this.jobSetup = in.readBoolean();
/*  393: 501 */     this.writeSkipRecs = in.readBoolean();
/*  394: 502 */     this.taskCleanup = in.readBoolean();
/*  395: 503 */     if (this.taskCleanup) {
/*  396: 504 */       setPhase(TaskStatus.Phase.CLEANUP);
/*  397:     */     }
/*  398: 506 */     this.user = StringInterner.weakIntern(Text.readString(in));
/*  399: 507 */     this.extraData.readFields(in);
/*  400:     */   }
/*  401:     */   
/*  402:     */   public String toString()
/*  403:     */   {
/*  404: 511 */     return this.taskId.toString();
/*  405:     */   }
/*  406:     */   
/*  407:     */   public void localizeConfiguration(JobConf conf)
/*  408:     */     throws IOException
/*  409:     */   {
/*  410: 517 */     conf.set("mapreduce.task.id", this.taskId.getTaskID().toString());
/*  411: 518 */     conf.set("mapreduce.task.attempt.id", this.taskId.toString());
/*  412: 519 */     conf.setBoolean("mapreduce.task.ismap", isMapTask());
/*  413: 520 */     conf.setInt("mapreduce.task.partition", this.partition);
/*  414: 521 */     conf.set("mapreduce.job.id", this.taskId.getJobID().toString());
/*  415:     */   }
/*  416:     */   
/*  417: 534 */   private transient Progress taskProgress = new Progress();
/*  418: 537 */   private transient Counters counters = new Counters();
/*  419: 540 */   private AtomicBoolean taskDone = new AtomicBoolean(false);
/*  420:     */   
/*  421:     */   public Progress getProgress()
/*  422:     */   {
/*  423: 544 */     return this.taskProgress;
/*  424:     */   }
/*  425:     */   
/*  426:     */   public void initialize(JobConf job, JobID id, Reporter reporter, boolean useNewApi)
/*  427:     */     throws IOException, ClassNotFoundException, InterruptedException
/*  428:     */   {
/*  429: 551 */     this.jobContext = new JobContextImpl(job, id, reporter);
/*  430: 552 */     this.taskContext = new TaskAttemptContextImpl(job, this.taskId, reporter);
/*  431: 553 */     if (getState() == TaskStatus.State.UNASSIGNED) {
/*  432: 554 */       setState(TaskStatus.State.RUNNING);
/*  433:     */     }
/*  434: 556 */     if (useNewApi)
/*  435:     */     {
/*  436: 557 */       if (LOG.isDebugEnabled()) {
/*  437: 558 */         LOG.debug("using new api for output committer");
/*  438:     */       }
/*  439: 560 */       this.outputFormat = ((OutputFormat)ReflectionUtils.newInstance(this.taskContext.getOutputFormatClass(), job));
/*  440:     */       
/*  441: 562 */       this.committer = this.outputFormat.getOutputCommitter(this.taskContext);
/*  442:     */     }
/*  443:     */     else
/*  444:     */     {
/*  445: 564 */       this.committer = this.conf.getOutputCommitter();
/*  446:     */     }
/*  447: 566 */     Path outputPath = FileOutputFormat.getOutputPath(this.conf);
/*  448: 567 */     if (outputPath != null) {
/*  449: 568 */       if ((this.committer instanceof FileOutputCommitter)) {
/*  450: 569 */         FileOutputFormat.setWorkOutputPath(this.conf, ((FileOutputCommitter)this.committer).getTaskAttemptPath(this.taskContext));
/*  451:     */       } else {
/*  452: 572 */         FileOutputFormat.setWorkOutputPath(this.conf, outputPath);
/*  453:     */       }
/*  454:     */     }
/*  455: 575 */     this.committer.setupTask(this.taskContext);
/*  456: 576 */     Class<? extends ResourceCalculatorProcessTree> clazz = this.conf.getClass("mapreduce.job.process-tree.class", null, ResourceCalculatorProcessTree.class);
/*  457:     */     
/*  458:     */ 
/*  459: 579 */     this.pTree = ResourceCalculatorProcessTree.getResourceCalculatorProcessTree((String)System.getenv().get("JVM_PID"), clazz, this.conf);
/*  460:     */     
/*  461: 581 */     LOG.info(" Using ResourceCalculatorProcessTree : " + this.pTree);
/*  462: 582 */     if (this.pTree != null)
/*  463:     */     {
/*  464: 583 */       this.pTree.updateProcessTree();
/*  465: 584 */       this.initCpuCumulativeTime = this.pTree.getCumulativeCpuTime();
/*  466:     */     }
/*  467:     */   }
/*  468:     */   
/*  469:     */   public static String normalizeStatus(String status, Configuration conf)
/*  470:     */   {
/*  471: 591 */     int progressStatusLength = conf.getInt("mapreduce.task.max.status.length", 512);
/*  472: 594 */     if (status.length() > progressStatusLength)
/*  473:     */     {
/*  474: 595 */       LOG.warn("Task status: \"" + status + "\" truncated to max limit (" + progressStatusLength + " characters)");
/*  475:     */       
/*  476: 597 */       status = status.substring(0, progressStatusLength);
/*  477:     */     }
/*  478: 599 */     return status;
/*  479:     */   }
/*  480:     */   
/*  481:     */   @InterfaceAudience.LimitedPrivate({"MapReduce"})
/*  482:     */   @InterfaceStability.Unstable
/*  483:     */   public class TaskReporter
/*  484:     */     extends StatusReporter
/*  485:     */     implements Runnable, Reporter
/*  486:     */   {
/*  487:     */     private TaskUmbilicalProtocol umbilical;
/*  488: 608 */     private InputSplit split = null;
/*  489:     */     private Progress taskProgress;
/*  490: 610 */     private Thread pingThread = null;
/*  491: 611 */     private boolean done = true;
/*  492: 612 */     private Object lock = new Object();
/*  493: 619 */     private AtomicBoolean progressFlag = new AtomicBoolean(false);
/*  494:     */     
/*  495:     */     TaskReporter(Progress taskProgress, TaskUmbilicalProtocol umbilical)
/*  496:     */     {
/*  497: 623 */       this.umbilical = umbilical;
/*  498: 624 */       this.taskProgress = taskProgress;
/*  499:     */     }
/*  500:     */     
/*  501:     */     void setProgressFlag()
/*  502:     */     {
/*  503: 629 */       this.progressFlag.set(true);
/*  504:     */     }
/*  505:     */     
/*  506:     */     boolean resetProgressFlag()
/*  507:     */     {
/*  508: 632 */       return this.progressFlag.getAndSet(false);
/*  509:     */     }
/*  510:     */     
/*  511:     */     public void setStatus(String status)
/*  512:     */     {
/*  513: 635 */       this.taskProgress.setStatus(Task.normalizeStatus(status, Task.this.conf));
/*  514:     */       
/*  515: 637 */       setProgressFlag();
/*  516:     */     }
/*  517:     */     
/*  518:     */     public void setProgress(float progress)
/*  519:     */     {
/*  520: 642 */       this.taskProgress.phase().set(progress);
/*  521:     */       
/*  522: 644 */       setProgressFlag();
/*  523:     */     }
/*  524:     */     
/*  525:     */     public float getProgress()
/*  526:     */     {
/*  527: 648 */       return this.taskProgress.getProgress();
/*  528:     */     }
/*  529:     */     
/*  530:     */     public void progress()
/*  531:     */     {
/*  532: 653 */       setProgressFlag();
/*  533:     */     }
/*  534:     */     
/*  535:     */     public Counters.Counter getCounter(String group, String name)
/*  536:     */     {
/*  537: 656 */       Counters.Counter counter = null;
/*  538: 657 */       if (Task.this.counters != null) {
/*  539: 658 */         counter = Task.this.counters.findCounter(group, name);
/*  540:     */       }
/*  541: 660 */       return counter;
/*  542:     */     }
/*  543:     */     
/*  544:     */     public Counters.Counter getCounter(Enum<?> name)
/*  545:     */     {
/*  546: 663 */       return Task.this.counters == null ? null : (Counters.Counter)Task.this.counters.findCounter(name);
/*  547:     */     }
/*  548:     */     
/*  549:     */     public void incrCounter(Enum key, long amount)
/*  550:     */     {
/*  551: 666 */       if (Task.this.counters != null) {
/*  552: 667 */         Task.this.counters.incrCounter(key, amount);
/*  553:     */       }
/*  554: 669 */       setProgressFlag();
/*  555:     */     }
/*  556:     */     
/*  557:     */     public void incrCounter(String group, String counter, long amount)
/*  558:     */     {
/*  559: 672 */       if (Task.this.counters != null) {
/*  560: 673 */         Task.this.counters.incrCounter(group, counter, amount);
/*  561:     */       }
/*  562: 675 */       if ((Task.this.skipping) && ("SkippingTaskCounters".equals(group)) && (("MapProcessedRecords".equals(counter)) || ("ReduceProcessedGroups".equals(counter)))) {
/*  563: 682 */         for (int i = 0; i < amount; i++) {
/*  564: 683 */           Task.this.currentRecStartIndex = ((Long)Task.this.currentRecIndexIterator.next()).longValue();
/*  565:     */         }
/*  566:     */       }
/*  567: 686 */       setProgressFlag();
/*  568:     */     }
/*  569:     */     
/*  570:     */     public void setInputSplit(InputSplit split)
/*  571:     */     {
/*  572: 689 */       this.split = split;
/*  573:     */     }
/*  574:     */     
/*  575:     */     public InputSplit getInputSplit()
/*  576:     */       throws UnsupportedOperationException
/*  577:     */     {
/*  578: 692 */       if (this.split == null) {
/*  579: 693 */         throw new UnsupportedOperationException("Input only available on map");
/*  580:     */       }
/*  581: 695 */       return this.split;
/*  582:     */     }
/*  583:     */     
/*  584:     */     public void run()
/*  585:     */     {
/*  586: 704 */       int MAX_RETRIES = 3;
/*  587: 705 */       int remainingRetries = 3;
/*  588:     */       
/*  589: 707 */       boolean sendProgress = resetProgressFlag();
/*  590: 708 */       while (!Task.this.taskDone.get())
/*  591:     */       {
/*  592: 709 */         synchronized (this.lock)
/*  593:     */         {
/*  594: 710 */           this.done = false;
/*  595:     */         }
/*  596:     */         try
/*  597:     */         {
/*  598: 713 */           boolean taskFound = true;
/*  599: 715 */           synchronized (this.lock)
/*  600:     */           {
/*  601: 716 */             if (Task.this.taskDone.get()) {
/*  602:     */               break;
/*  603:     */             }
/*  604: 719 */             this.lock.wait(3000L);
/*  605:     */           }
/*  606: 721 */           if (Task.this.taskDone.get()) {
/*  607:     */             break;
/*  608:     */           }
/*  609: 725 */           if (sendProgress)
/*  610:     */           {
/*  611: 727 */             Task.this.updateCounters();
/*  612: 728 */             Task.this.taskStatus.statusUpdate(this.taskProgress.get(), this.taskProgress.toString(), Task.this.counters);
/*  613:     */             
/*  614:     */ 
/*  615: 731 */             taskFound = this.umbilical.statusUpdate(Task.this.taskId, Task.this.taskStatus);
/*  616: 732 */             Task.this.taskStatus.clearStatus();
/*  617:     */           }
/*  618:     */           else
/*  619:     */           {
/*  620: 736 */             taskFound = this.umbilical.ping(Task.this.taskId);
/*  621:     */           }
/*  622: 741 */           if (!taskFound)
/*  623:     */           {
/*  624: 742 */             Task.LOG.warn("Parent died.  Exiting " + Task.this.taskId);
/*  625: 743 */             resetDoneFlag();
/*  626: 744 */             System.exit(66);
/*  627:     */           }
/*  628: 747 */           sendProgress = resetProgressFlag();
/*  629: 748 */           remainingRetries = 3;
/*  630:     */         }
/*  631:     */         catch (Throwable t)
/*  632:     */         {
/*  633: 751 */           Task.LOG.info("Communication exception: " + StringUtils.stringifyException(t));
/*  634: 752 */           remainingRetries--;
/*  635: 753 */           if (remainingRetries == 0)
/*  636:     */           {
/*  637: 754 */             ReflectionUtils.logThreadInfo(Task.LOG, "Communication exception", 0L);
/*  638: 755 */             Task.LOG.warn("Last retry, killing " + Task.this.taskId);
/*  639: 756 */             resetDoneFlag();
/*  640: 757 */             System.exit(65);
/*  641:     */           }
/*  642:     */         }
/*  643:     */       }
/*  644: 762 */       resetDoneFlag();
/*  645:     */     }
/*  646:     */     
/*  647:     */     void resetDoneFlag()
/*  648:     */     {
/*  649: 765 */       synchronized (this.lock)
/*  650:     */       {
/*  651: 766 */         this.done = true;
/*  652: 767 */         this.lock.notify();
/*  653:     */       }
/*  654:     */     }
/*  655:     */     
/*  656:     */     public void startCommunicationThread()
/*  657:     */     {
/*  658: 771 */       if (this.pingThread == null)
/*  659:     */       {
/*  660: 772 */         this.pingThread = new Thread(this, "communication thread");
/*  661: 773 */         this.pingThread.setDaemon(true);
/*  662: 774 */         this.pingThread.start();
/*  663:     */       }
/*  664:     */     }
/*  665:     */     
/*  666:     */     public void stopCommunicationThread()
/*  667:     */       throws InterruptedException
/*  668:     */     {
/*  669: 778 */       if (this.pingThread != null)
/*  670:     */       {
/*  671: 781 */         synchronized (this.lock)
/*  672:     */         {
/*  673: 783 */           this.lock.notify();
/*  674:     */         }
/*  675: 786 */         synchronized (this.lock)
/*  676:     */         {
/*  677: 787 */           while (!this.done) {
/*  678: 788 */             this.lock.wait();
/*  679:     */           }
/*  680:     */         }
/*  681: 791 */         this.pingThread.interrupt();
/*  682: 792 */         this.pingThread.join();
/*  683:     */       }
/*  684:     */     }
/*  685:     */   }
/*  686:     */   
/*  687:     */   protected void reportNextRecordRange(TaskUmbilicalProtocol umbilical, long nextRecIndex)
/*  688:     */     throws IOException
/*  689:     */   {
/*  690: 808 */     long len = nextRecIndex - this.currentRecStartIndex + 1L;
/*  691: 809 */     SortedRanges.Range range = new SortedRanges.Range(this.currentRecStartIndex, len);
/*  692:     */     
/*  693: 811 */     this.taskStatus.setNextRecordRange(range);
/*  694: 812 */     if (LOG.isDebugEnabled()) {
/*  695: 813 */       LOG.debug("sending reportNextRecordRange " + range);
/*  696:     */     }
/*  697: 815 */     umbilical.reportNextRecordRange(this.taskId, range);
/*  698:     */   }
/*  699:     */   
/*  700:     */   TaskReporter startReporter(TaskUmbilicalProtocol umbilical)
/*  701:     */   {
/*  702: 823 */     TaskReporter reporter = new TaskReporter(getProgress(), umbilical);
/*  703: 824 */     reporter.startCommunicationThread();
/*  704: 825 */     return reporter;
/*  705:     */   }
/*  706:     */   
/*  707:     */   void updateResourceCounters()
/*  708:     */   {
/*  709: 833 */     updateHeapUsageCounter();
/*  710: 836 */     if (this.pTree == null) {
/*  711: 837 */       return;
/*  712:     */     }
/*  713: 839 */     this.pTree.updateProcessTree();
/*  714: 840 */     long cpuTime = this.pTree.getCumulativeCpuTime();
/*  715: 841 */     long pMem = this.pTree.getCumulativeRssmem();
/*  716: 842 */     long vMem = this.pTree.getCumulativeVmem();
/*  717:     */     
/*  718: 844 */     cpuTime -= this.initCpuCumulativeTime;
/*  719: 845 */     ((Counters.Counter)this.counters.findCounter(TaskCounter.CPU_MILLISECONDS)).setValue(cpuTime);
/*  720: 846 */     ((Counters.Counter)this.counters.findCounter(TaskCounter.PHYSICAL_MEMORY_BYTES)).setValue(pMem);
/*  721: 847 */     ((Counters.Counter)this.counters.findCounter(TaskCounter.VIRTUAL_MEMORY_BYTES)).setValue(vMem);
/*  722:     */   }
/*  723:     */   
/*  724:     */   class GcTimeUpdater
/*  725:     */   {
/*  726: 854 */     private long lastGcMillis = 0L;
/*  727: 855 */     private List<GarbageCollectorMXBean> gcBeans = null;
/*  728:     */     
/*  729:     */     public GcTimeUpdater()
/*  730:     */     {
/*  731: 858 */       this.gcBeans = ManagementFactory.getGarbageCollectorMXBeans();
/*  732: 859 */       getElapsedGc();
/*  733:     */     }
/*  734:     */     
/*  735:     */     protected long getElapsedGc()
/*  736:     */     {
/*  737: 867 */       long thisGcMillis = 0L;
/*  738: 868 */       for (GarbageCollectorMXBean gcBean : this.gcBeans) {
/*  739: 869 */         thisGcMillis += gcBean.getCollectionTime();
/*  740:     */       }
/*  741: 872 */       long delta = thisGcMillis - this.lastGcMillis;
/*  742: 873 */       this.lastGcMillis = thisGcMillis;
/*  743: 874 */       return delta;
/*  744:     */     }
/*  745:     */     
/*  746:     */     public void incrementGcCounter()
/*  747:     */     {
/*  748: 881 */       if (null == Task.this.counters) {
/*  749: 882 */         return;
/*  750:     */       }
/*  751: 885 */       Counters.Counter gcCounter = (Counters.Counter)Task.this.counters.findCounter(TaskCounter.GC_TIME_MILLIS);
/*  752: 887 */       if (null != gcCounter) {
/*  753: 888 */         gcCounter.increment(getElapsedGc());
/*  754:     */       }
/*  755:     */     }
/*  756:     */   }
/*  757:     */   
/*  758:     */   class FileSystemStatisticUpdater
/*  759:     */   {
/*  760:     */     private List<FileSystem.Statistics> stats;
/*  761:     */     private Counters.Counter readBytesCounter;
/*  762:     */     private Counters.Counter writeBytesCounter;
/*  763:     */     private Counters.Counter readOpsCounter;
/*  764:     */     private Counters.Counter largeReadOpsCounter;
/*  765:     */     private Counters.Counter writeOpsCounter;
/*  766:     */     private String scheme;
/*  767:     */     
/*  768:     */     FileSystemStatisticUpdater(String stats)
/*  769:     */     {
/*  770: 903 */       this.stats = stats;
/*  771: 904 */       this.scheme = scheme;
/*  772:     */     }
/*  773:     */     
/*  774:     */     void updateCounters()
/*  775:     */     {
/*  776: 908 */       if (this.readBytesCounter == null) {
/*  777: 909 */         this.readBytesCounter = ((Counters.Counter)Task.this.counters.findCounter(this.scheme, FileSystemCounter.BYTES_READ));
/*  778:     */       }
/*  779: 912 */       if (this.writeBytesCounter == null) {
/*  780: 913 */         this.writeBytesCounter = ((Counters.Counter)Task.this.counters.findCounter(this.scheme, FileSystemCounter.BYTES_WRITTEN));
/*  781:     */       }
/*  782: 916 */       if (this.readOpsCounter == null) {
/*  783: 917 */         this.readOpsCounter = ((Counters.Counter)Task.this.counters.findCounter(this.scheme, FileSystemCounter.READ_OPS));
/*  784:     */       }
/*  785: 920 */       if (this.largeReadOpsCounter == null) {
/*  786: 921 */         this.largeReadOpsCounter = ((Counters.Counter)Task.this.counters.findCounter(this.scheme, FileSystemCounter.LARGE_READ_OPS));
/*  787:     */       }
/*  788: 924 */       if (this.writeOpsCounter == null) {
/*  789: 925 */         this.writeOpsCounter = ((Counters.Counter)Task.this.counters.findCounter(this.scheme, FileSystemCounter.WRITE_OPS));
/*  790:     */       }
/*  791: 928 */       long readBytes = 0L;
/*  792: 929 */       long writeBytes = 0L;
/*  793: 930 */       long readOps = 0L;
/*  794: 931 */       long largeReadOps = 0L;
/*  795: 932 */       long writeOps = 0L;
/*  796: 933 */       for (FileSystem.Statistics stat : this.stats)
/*  797:     */       {
/*  798: 934 */         readBytes += stat.getBytesRead();
/*  799: 935 */         writeBytes += stat.getBytesWritten();
/*  800: 936 */         readOps += stat.getReadOps();
/*  801: 937 */         largeReadOps += stat.getLargeReadOps();
/*  802: 938 */         writeOps += stat.getWriteOps();
/*  803:     */       }
/*  804: 940 */       this.readBytesCounter.setValue(readBytes);
/*  805: 941 */       this.writeBytesCounter.setValue(writeBytes);
/*  806: 942 */       this.readOpsCounter.setValue(readOps);
/*  807: 943 */       this.largeReadOpsCounter.setValue(largeReadOps);
/*  808: 944 */       this.writeOpsCounter.setValue(writeOps);
/*  809:     */     }
/*  810:     */   }
/*  811:     */   
/*  812: 951 */   private Map<String, FileSystemStatisticUpdater> statisticUpdaters = new HashMap();
/*  813:     */   
/*  814:     */   private synchronized void updateCounters()
/*  815:     */   {
/*  816: 955 */     Map<String, List<FileSystem.Statistics>> map = new HashMap();
/*  817: 957 */     for (FileSystem.Statistics stat : FileSystem.getAllStatistics())
/*  818:     */     {
/*  819: 958 */       String uriScheme = stat.getScheme();
/*  820: 959 */       if (map.containsKey(uriScheme))
/*  821:     */       {
/*  822: 960 */         List<FileSystem.Statistics> list = (List)map.get(uriScheme);
/*  823: 961 */         list.add(stat);
/*  824:     */       }
/*  825:     */       else
/*  826:     */       {
/*  827: 963 */         List<FileSystem.Statistics> list = new ArrayList();
/*  828: 964 */         list.add(stat);
/*  829: 965 */         map.put(uriScheme, list);
/*  830:     */       }
/*  831:     */     }
/*  832: 968 */     for (Map.Entry<String, List<FileSystem.Statistics>> entry : map.entrySet())
/*  833:     */     {
/*  834: 969 */       FileSystemStatisticUpdater updater = (FileSystemStatisticUpdater)this.statisticUpdaters.get(entry.getKey());
/*  835: 970 */       if (updater == null)
/*  836:     */       {
/*  837: 971 */         updater = new FileSystemStatisticUpdater((List)entry.getValue(), (String)entry.getKey());
/*  838: 972 */         this.statisticUpdaters.put(entry.getKey(), updater);
/*  839:     */       }
/*  840: 974 */       updater.updateCounters();
/*  841:     */     }
/*  842: 977 */     this.gcUpdater.incrementGcCounter();
/*  843: 978 */     updateResourceCounters();
/*  844:     */   }
/*  845:     */   
/*  846:     */   private void updateHeapUsageCounter()
/*  847:     */   {
/*  848: 987 */     long currentHeapUsage = Runtime.getRuntime().totalMemory();
/*  849: 988 */     ((Counters.Counter)this.counters.findCounter(TaskCounter.COMMITTED_HEAP_BYTES)).setValue(currentHeapUsage);
/*  850:     */   }
/*  851:     */   
/*  852:     */   public void done(TaskUmbilicalProtocol umbilical, TaskReporter reporter)
/*  853:     */     throws IOException, InterruptedException
/*  854:     */   {
/*  855: 995 */     LOG.info("Task:" + this.taskId + " is done." + " And is in the process of committing");
/*  856:     */     
/*  857: 997 */     updateCounters();
/*  858:     */     
/*  859: 999 */     boolean commitRequired = isCommitRequired();
/*  860:1000 */     if (commitRequired)
/*  861:     */     {
/*  862:1001 */       int retries = 10;
/*  863:1002 */       setState(TaskStatus.State.COMMIT_PENDING);
/*  864:     */       for (;;)
/*  865:     */       {
/*  866:     */         try
/*  867:     */         {
/*  868:1006 */           umbilical.commitPending(this.taskId, this.taskStatus);
/*  869:     */         }
/*  870:     */         catch (InterruptedException ie) {}catch (IOException ie)
/*  871:     */         {
/*  872:1011 */           LOG.warn("Failure sending commit pending: " + StringUtils.stringifyException(ie));
/*  873:     */           
/*  874:1013 */           retries--;
/*  875:1013 */           if (retries == 0) {
/*  876:1014 */             System.exit(67);
/*  877:     */           }
/*  878:     */         }
/*  879:     */       }
/*  880:1019 */       commit(umbilical, reporter, this.committer);
/*  881:     */     }
/*  882:1021 */     this.taskDone.set(true);
/*  883:1022 */     reporter.stopCommunicationThread();
/*  884:     */     
/*  885:     */ 
/*  886:1025 */     updateCounters();
/*  887:1026 */     sendLastUpdate(umbilical);
/*  888:     */     
/*  889:1028 */     sendDone(umbilical);
/*  890:     */   }
/*  891:     */   
/*  892:     */   boolean isCommitRequired()
/*  893:     */     throws IOException
/*  894:     */   {
/*  895:1040 */     boolean commitRequired = false;
/*  896:1041 */     if (isMapOrReduce()) {
/*  897:1042 */       commitRequired = this.committer.needsTaskCommit(this.taskContext);
/*  898:     */     }
/*  899:1044 */     return commitRequired;
/*  900:     */   }
/*  901:     */   
/*  902:     */   public void statusUpdate(TaskUmbilicalProtocol umbilical)
/*  903:     */     throws IOException
/*  904:     */   {
/*  905:1054 */     int retries = 10;
/*  906:     */     for (;;)
/*  907:     */     {
/*  908:     */       try
/*  909:     */       {
/*  910:1057 */         if (!umbilical.statusUpdate(getTaskID(), this.taskStatus))
/*  911:     */         {
/*  912:1058 */           LOG.warn("Parent died.  Exiting " + this.taskId);
/*  913:1059 */           System.exit(66);
/*  914:     */         }
/*  915:1061 */         this.taskStatus.clearStatus();
/*  916:1062 */         return;
/*  917:     */       }
/*  918:     */       catch (InterruptedException ie)
/*  919:     */       {
/*  920:1064 */         Thread.currentThread().interrupt();
/*  921:     */       }
/*  922:     */       catch (IOException ie)
/*  923:     */       {
/*  924:1066 */         LOG.warn("Failure sending status update: " + StringUtils.stringifyException(ie));
/*  925:     */         
/*  926:1068 */         retries--;
/*  927:1068 */         if (retries == 0) {
/*  928:1069 */           throw ie;
/*  929:     */         }
/*  930:     */       }
/*  931:     */     }
/*  932:     */   }
/*  933:     */   
/*  934:     */   private void sendLastUpdate(TaskUmbilicalProtocol umbilical)
/*  935:     */     throws IOException
/*  936:     */   {
/*  937:1080 */     this.taskStatus.setOutputSize(calculateOutputSize());
/*  938:     */     
/*  939:1082 */     this.taskStatus.statusUpdate(this.taskProgress.get(), this.taskProgress.toString(), this.counters);
/*  940:     */     
/*  941:     */ 
/*  942:1085 */     statusUpdate(umbilical);
/*  943:     */   }
/*  944:     */   
/*  945:     */   private long calculateOutputSize()
/*  946:     */     throws IOException
/*  947:     */   {
/*  948:1094 */     if (!isMapOrReduce()) {
/*  949:1095 */       return -1L;
/*  950:     */     }
/*  951:1098 */     if ((isMapTask()) && (this.conf.getNumReduceTasks() > 0)) {
/*  952:     */       try
/*  953:     */       {
/*  954:1100 */         Path mapOutput = this.mapOutputFile.getOutputFile();
/*  955:1101 */         FileSystem localFS = FileSystem.getLocal(this.conf);
/*  956:1102 */         return localFS.getFileStatus(mapOutput).getLen();
/*  957:     */       }
/*  958:     */       catch (IOException e)
/*  959:     */       {
/*  960:1104 */         LOG.warn("Could not find output size ", e);
/*  961:     */       }
/*  962:     */     }
/*  963:1107 */     return -1L;
/*  964:     */   }
/*  965:     */   
/*  966:     */   private void sendDone(TaskUmbilicalProtocol umbilical)
/*  967:     */     throws IOException
/*  968:     */   {
/*  969:1111 */     int retries = 10;
/*  970:     */     for (;;)
/*  971:     */     {
/*  972:     */       try
/*  973:     */       {
/*  974:1114 */         umbilical.done(getTaskID());
/*  975:1115 */         LOG.info("Task '" + this.taskId + "' done.");
/*  976:1116 */         return;
/*  977:     */       }
/*  978:     */       catch (IOException ie)
/*  979:     */       {
/*  980:1118 */         LOG.warn("Failure signalling completion: " + StringUtils.stringifyException(ie));
/*  981:     */         
/*  982:1120 */         retries--;
/*  983:1120 */         if (retries == 0) {
/*  984:1121 */           throw ie;
/*  985:     */         }
/*  986:     */       }
/*  987:     */     }
/*  988:     */   }
/*  989:     */   
/*  990:     */   private void commit(TaskUmbilicalProtocol umbilical, TaskReporter reporter, org.apache.hadoop.mapreduce.OutputCommitter committer)
/*  991:     */     throws IOException
/*  992:     */   {
/*  993:1131 */     int retries = 10;
/*  994:     */     for (;;)
/*  995:     */     {
/*  996:     */       try
/*  997:     */       {
/*  998:1134 */         if (!umbilical.canCommit(this.taskId))
/*  999:     */         {
/* 1000:     */           try
/* 1001:     */           {
/* 1002:1136 */             Thread.sleep(1000L);
/* 1003:     */           }
/* 1004:     */           catch (InterruptedException ie) {}
/* 1005:1140 */           reporter.setProgressFlag();
/* 1006:     */         }
/* 1007:     */       }
/* 1008:     */       catch (IOException ie)
/* 1009:     */       {
/* 1010:1144 */         LOG.warn("Failure asking whether task can commit: " + StringUtils.stringifyException(ie));
/* 1011:     */         
/* 1012:1146 */         retries--;
/* 1013:1146 */         if (retries == 0)
/* 1014:     */         {
/* 1015:1148 */           discardOutput(this.taskContext);
/* 1016:1149 */           System.exit(68);
/* 1017:     */         }
/* 1018:     */       }
/* 1019:     */     }
/* 1020:     */     try
/* 1021:     */     {
/* 1022:1156 */       LOG.info("Task " + this.taskId + " is allowed to commit now");
/* 1023:1157 */       committer.commitTask(this.taskContext);
/* 1024:1158 */       return;
/* 1025:     */     }
/* 1026:     */     catch (IOException iee)
/* 1027:     */     {
/* 1028:1160 */       LOG.warn("Failure committing: " + StringUtils.stringifyException(iee));
/* 1029:     */       
/* 1030:     */ 
/* 1031:1163 */       discardOutput(this.taskContext);
/* 1032:1164 */       throw iee;
/* 1033:     */     }
/* 1034:     */   }
/* 1035:     */   
/* 1036:     */   private void discardOutput(TaskAttemptContext taskContext)
/* 1037:     */   {
/* 1038:     */     try
/* 1039:     */     {
/* 1040:1171 */       this.committer.abortTask(taskContext);
/* 1041:     */     }
/* 1042:     */     catch (IOException ioe)
/* 1043:     */     {
/* 1044:1173 */       LOG.warn("Failure cleaning up: " + StringUtils.stringifyException(ioe));
/* 1045:     */     }
/* 1046:     */   }
/* 1047:     */   
/* 1048:     */   protected void runTaskCleanupTask(TaskUmbilicalProtocol umbilical, TaskReporter reporter)
/* 1049:     */     throws IOException, InterruptedException
/* 1050:     */   {
/* 1051:1181 */     taskCleanup(umbilical);
/* 1052:1182 */     done(umbilical, reporter);
/* 1053:     */   }
/* 1054:     */   
/* 1055:     */   void taskCleanup(TaskUmbilicalProtocol umbilical)
/* 1056:     */     throws IOException
/* 1057:     */   {
/* 1058:1188 */     setPhase(TaskStatus.Phase.CLEANUP);
/* 1059:1189 */     getProgress().setStatus("cleanup");
/* 1060:1190 */     statusUpdate(umbilical);
/* 1061:1191 */     LOG.info("Runnning cleanup for the task");
/* 1062:     */     
/* 1063:1193 */     this.committer.abortTask(this.taskContext);
/* 1064:     */   }
/* 1065:     */   
/* 1066:     */   protected void runJobCleanupTask(TaskUmbilicalProtocol umbilical, TaskReporter reporter)
/* 1067:     */     throws IOException, InterruptedException
/* 1068:     */   {
/* 1069:1200 */     setPhase(TaskStatus.Phase.CLEANUP);
/* 1070:1201 */     getProgress().setStatus("cleanup");
/* 1071:1202 */     statusUpdate(umbilical);
/* 1072:     */     
/* 1073:1204 */     LOG.info("Cleaning up job");
/* 1074:1205 */     if ((this.jobRunStateForCleanup == JobStatus.State.FAILED) || (this.jobRunStateForCleanup == JobStatus.State.KILLED))
/* 1075:     */     {
/* 1076:1207 */       LOG.info("Aborting job with runstate : " + this.jobRunStateForCleanup.name());
/* 1077:1208 */       if (this.conf.getUseNewMapper())
/* 1078:     */       {
/* 1079:1209 */         this.committer.abortJob(this.jobContext, this.jobRunStateForCleanup);
/* 1080:     */       }
/* 1081:     */       else
/* 1082:     */       {
/* 1083:1211 */         OutputCommitter oldCommitter = (OutputCommitter)this.committer;
/* 1084:     */         
/* 1085:1213 */         oldCommitter.abortJob(this.jobContext, this.jobRunStateForCleanup);
/* 1086:     */       }
/* 1087:     */     }
/* 1088:1215 */     else if (this.jobRunStateForCleanup == JobStatus.State.SUCCEEDED)
/* 1089:     */     {
/* 1090:1216 */       LOG.info("Committing job");
/* 1091:1217 */       this.committer.commitJob(this.jobContext);
/* 1092:     */     }
/* 1093:     */     else
/* 1094:     */     {
/* 1095:1219 */       throw new IOException("Invalid state of the job for cleanup. State found " + this.jobRunStateForCleanup + " expecting " + JobStatus.State.SUCCEEDED + ", " + JobStatus.State.FAILED + " or " + JobStatus.State.KILLED);
/* 1096:     */     }
/* 1097:1227 */     JobConf conf = new JobConf(this.jobContext.getConfiguration());
/* 1098:1228 */     if (!keepTaskFiles(conf))
/* 1099:     */     {
/* 1100:1229 */       String jobTempDir = conf.get("mapreduce.job.dir");
/* 1101:1230 */       Path jobTempDirPath = new Path(jobTempDir);
/* 1102:1231 */       FileSystem fs = jobTempDirPath.getFileSystem(conf);
/* 1103:1232 */       fs.delete(jobTempDirPath, true);
/* 1104:     */     }
/* 1105:1234 */     done(umbilical, reporter);
/* 1106:     */   }
/* 1107:     */   
/* 1108:     */   protected boolean keepTaskFiles(JobConf conf)
/* 1109:     */   {
/* 1110:1238 */     return (conf.getKeepTaskFilesPattern() != null) || (conf.getKeepFailedTaskFiles());
/* 1111:     */   }
/* 1112:     */   
/* 1113:     */   protected void runJobSetupTask(TaskUmbilicalProtocol umbilical, TaskReporter reporter)
/* 1114:     */     throws IOException, InterruptedException
/* 1115:     */   {
/* 1116:1246 */     getProgress().setStatus("setup");
/* 1117:1247 */     this.committer.setupJob(this.jobContext);
/* 1118:1248 */     done(umbilical, reporter);
/* 1119:     */   }
/* 1120:     */   
/* 1121:     */   public void setConf(Configuration conf)
/* 1122:     */   {
/* 1123:1252 */     if ((conf instanceof JobConf)) {
/* 1124:1253 */       this.conf = ((JobConf)conf);
/* 1125:     */     } else {
/* 1126:1255 */       this.conf = new JobConf(conf);
/* 1127:     */     }
/* 1128:1257 */     this.mapOutputFile = ((MapOutputFile)ReflectionUtils.newInstance(conf.getClass("mapreduce.task.local.output.class", MROutputFiles.class, MapOutputFile.class), conf));
/* 1129:     */     
/* 1130:     */ 
/* 1131:1260 */     this.lDirAlloc = new LocalDirAllocator("mapreduce.cluster.local.dir");
/* 1132:     */     
/* 1133:     */ 
/* 1134:     */ 
/* 1135:1264 */     String[] hostToResolved = conf.getStrings("mapreduce.job.net.static.resolutions");
/* 1136:1265 */     if (hostToResolved != null) {
/* 1137:1266 */       for (String str : hostToResolved)
/* 1138:     */       {
/* 1139:1267 */         String name = str.substring(0, str.indexOf('='));
/* 1140:1268 */         String resolvedName = str.substring(str.indexOf('=') + 1);
/* 1141:1269 */         NetUtils.addStaticResolution(name, resolvedName);
/* 1142:     */       }
/* 1143:     */     }
/* 1144:     */   }
/* 1145:     */   
/* 1146:     */   public Configuration getConf()
/* 1147:     */   {
/* 1148:1275 */     return this.conf;
/* 1149:     */   }
/* 1150:     */   
/* 1151:     */   public MapOutputFile getMapOutputFile()
/* 1152:     */   {
/* 1153:1279 */     return this.mapOutputFile;
/* 1154:     */   }
/* 1155:     */   
/* 1156:     */   @InterfaceAudience.Private
/* 1157:     */   @InterfaceStability.Unstable
/* 1158:     */   public static class CombineOutputCollector<K, V>
/* 1159:     */     implements OutputCollector<K, V>
/* 1160:     */   {
/* 1161:     */     private IFile.Writer<K, V> writer;
/* 1162:     */     private Counters.Counter outCounter;
/* 1163:     */     private Progressable progressable;
/* 1164:     */     private long progressBar;
/* 1165:     */     
/* 1166:     */     public CombineOutputCollector(Counters.Counter outCounter, Progressable progressable, Configuration conf)
/* 1167:     */     {
/* 1168:1295 */       this.outCounter = outCounter;
/* 1169:1296 */       this.progressable = progressable;
/* 1170:1297 */       this.progressBar = conf.getLong("mapreduce.task.combine.progress.records", 10000L);
/* 1171:     */     }
/* 1172:     */     
/* 1173:     */     public synchronized void setWriter(IFile.Writer<K, V> writer)
/* 1174:     */     {
/* 1175:1301 */       this.writer = writer;
/* 1176:     */     }
/* 1177:     */     
/* 1178:     */     public synchronized void collect(K key, V value)
/* 1179:     */       throws IOException
/* 1180:     */     {
/* 1181:1306 */       this.outCounter.increment(1L);
/* 1182:1307 */       this.writer.append(key, value);
/* 1183:1308 */       if (this.outCounter.getValue() % this.progressBar == 0L) {
/* 1184:1309 */         this.progressable.progress();
/* 1185:     */       }
/* 1186:     */     }
/* 1187:     */   }
/* 1188:     */   
/* 1189:     */   static class ValuesIterator<KEY, VALUE>
/* 1190:     */     implements Iterator<VALUE>
/* 1191:     */   {
/* 1192:     */     protected RawKeyValueIterator in;
/* 1193:     */     private KEY key;
/* 1194:     */     private KEY nextKey;
/* 1195:     */     private VALUE value;
/* 1196:     */     private boolean hasNext;
/* 1197:     */     private boolean more;
/* 1198:     */     private RawComparator<KEY> comparator;
/* 1199:     */     protected Progressable reporter;
/* 1200:     */     private Deserializer<KEY> keyDeserializer;
/* 1201:     */     private Deserializer<VALUE> valDeserializer;
/* 1202:1326 */     private DataInputBuffer keyIn = new DataInputBuffer();
/* 1203:1327 */     private DataInputBuffer valueIn = new DataInputBuffer();
/* 1204:     */     
/* 1205:     */     public ValuesIterator(RawKeyValueIterator in, RawComparator<KEY> comparator, Class<KEY> keyClass, Class<VALUE> valClass, Configuration conf, Progressable reporter)
/* 1206:     */       throws IOException
/* 1207:     */     {
/* 1208:1335 */       this.in = in;
/* 1209:1336 */       this.comparator = comparator;
/* 1210:1337 */       this.reporter = reporter;
/* 1211:1338 */       SerializationFactory serializationFactory = new SerializationFactory(conf);
/* 1212:1339 */       this.keyDeserializer = serializationFactory.getDeserializer(keyClass);
/* 1213:1340 */       this.keyDeserializer.open(this.keyIn);
/* 1214:1341 */       this.valDeserializer = serializationFactory.getDeserializer(valClass);
/* 1215:1342 */       this.valDeserializer.open(this.valueIn);
/* 1216:1343 */       readNextKey();
/* 1217:1344 */       this.key = this.nextKey;
/* 1218:1345 */       this.nextKey = null;
/* 1219:1346 */       this.hasNext = this.more;
/* 1220:     */     }
/* 1221:     */     
/* 1222:     */     RawKeyValueIterator getRawIterator()
/* 1223:     */     {
/* 1224:1349 */       return this.in;
/* 1225:     */     }
/* 1226:     */     
/* 1227:     */     public boolean hasNext()
/* 1228:     */     {
/* 1229:1353 */       return this.hasNext;
/* 1230:     */     }
/* 1231:     */     
/* 1232:1355 */     private int ctr = 0;
/* 1233:     */     
/* 1234:     */     public VALUE next()
/* 1235:     */     {
/* 1236:1357 */       if (!this.hasNext) {
/* 1237:1358 */         throw new NoSuchElementException("iterate past last value");
/* 1238:     */       }
/* 1239:     */       try
/* 1240:     */       {
/* 1241:1361 */         readNextValue();
/* 1242:1362 */         readNextKey();
/* 1243:     */       }
/* 1244:     */       catch (IOException ie)
/* 1245:     */       {
/* 1246:1364 */         throw new RuntimeException("problem advancing post rec#" + this.ctr, ie);
/* 1247:     */       }
/* 1248:1366 */       this.reporter.progress();
/* 1249:1367 */       return this.value;
/* 1250:     */     }
/* 1251:     */     
/* 1252:     */     public void remove()
/* 1253:     */     {
/* 1254:1370 */       throw new RuntimeException("not implemented");
/* 1255:     */     }
/* 1256:     */     
/* 1257:     */     public void nextKey()
/* 1258:     */       throws IOException
/* 1259:     */     {
/* 1260:1377 */       while (this.hasNext) {
/* 1261:1378 */         readNextKey();
/* 1262:     */       }
/* 1263:1380 */       this.ctr += 1;
/* 1264:     */       
/* 1265:     */ 
/* 1266:1383 */       KEY tmpKey = this.key;
/* 1267:1384 */       this.key = this.nextKey;
/* 1268:1385 */       this.nextKey = tmpKey;
/* 1269:1386 */       this.hasNext = this.more;
/* 1270:     */     }
/* 1271:     */     
/* 1272:     */     public boolean more()
/* 1273:     */     {
/* 1274:1391 */       return this.more;
/* 1275:     */     }
/* 1276:     */     
/* 1277:     */     public KEY getKey()
/* 1278:     */     {
/* 1279:1396 */       return this.key;
/* 1280:     */     }
/* 1281:     */     
/* 1282:     */     private void readNextKey()
/* 1283:     */       throws IOException
/* 1284:     */     {
/* 1285:1403 */       this.more = this.in.next();
/* 1286:1404 */       if (this.more)
/* 1287:     */       {
/* 1288:1405 */         DataInputBuffer nextKeyBytes = this.in.getKey();
/* 1289:1406 */         this.keyIn.reset(nextKeyBytes.getData(), nextKeyBytes.getPosition(), nextKeyBytes.getLength());
/* 1290:1407 */         this.nextKey = this.keyDeserializer.deserialize(this.nextKey);
/* 1291:1408 */         this.hasNext = ((this.key != null) && (this.comparator.compare(this.key, this.nextKey) == 0));
/* 1292:     */       }
/* 1293:     */       else
/* 1294:     */       {
/* 1295:1410 */         this.hasNext = false;
/* 1296:     */       }
/* 1297:     */     }
/* 1298:     */     
/* 1299:     */     private void readNextValue()
/* 1300:     */       throws IOException
/* 1301:     */     {
/* 1302:1419 */       DataInputBuffer nextValueBytes = this.in.getValue();
/* 1303:1420 */       this.valueIn.reset(nextValueBytes.getData(), nextValueBytes.getPosition(), nextValueBytes.getLength());
/* 1304:1421 */       this.value = this.valDeserializer.deserialize(this.value);
/* 1305:     */     }
/* 1306:     */   }
/* 1307:     */   
/* 1308:     */   @InterfaceAudience.Private
/* 1309:     */   @InterfaceStability.Unstable
/* 1310:     */   public static class CombineValuesIterator<KEY, VALUE>
/* 1311:     */     extends Task.ValuesIterator<KEY, VALUE>
/* 1312:     */   {
/* 1313:     */     private final Counters.Counter combineInputCounter;
/* 1314:     */     
/* 1315:     */     public CombineValuesIterator(RawKeyValueIterator in, RawComparator<KEY> comparator, Class<KEY> keyClass, Class<VALUE> valClass, Configuration conf, Reporter reporter, Counters.Counter combineInputCounter)
/* 1316:     */       throws IOException
/* 1317:     */     {
/* 1318:1437 */       super(comparator, keyClass, valClass, conf, reporter);
/* 1319:1438 */       this.combineInputCounter = combineInputCounter;
/* 1320:     */     }
/* 1321:     */     
/* 1322:     */     public VALUE next()
/* 1323:     */     {
/* 1324:1442 */       this.combineInputCounter.increment(1L);
/* 1325:1443 */       return super.next();
/* 1326:     */     }
/* 1327:     */   }
/* 1328:     */   
/* 1329:     */   protected static <INKEY, INVALUE, OUTKEY, OUTVALUE> org.apache.hadoop.mapreduce.Reducer<INKEY, INVALUE, OUTKEY, OUTVALUE>.Context createReduceContext(org.apache.hadoop.mapreduce.Reducer<INKEY, INVALUE, OUTKEY, OUTVALUE> reducer, Configuration job, org.apache.hadoop.mapreduce.TaskAttemptID taskId, RawKeyValueIterator rIter, Counter inputKeyCounter, Counter inputValueCounter, RecordWriter<OUTKEY, OUTVALUE> output, org.apache.hadoop.mapreduce.OutputCommitter committer, StatusReporter reporter, RawComparator<INKEY> comparator, Class<INKEY> keyClass, Class<INVALUE> valueClass)
/* 1330:     */     throws IOException, InterruptedException
/* 1331:     */   {
/* 1332:1464 */     ReduceContext<INKEY, INVALUE, OUTKEY, OUTVALUE> reduceContext = new ReduceContextImpl(job, taskId, rIter, inputKeyCounter, inputValueCounter, output, committer, reporter, comparator, keyClass, valueClass);
/* 1333:     */     
/* 1334:     */ 
/* 1335:     */ 
/* 1336:     */ 
/* 1337:     */ 
/* 1338:     */ 
/* 1339:     */ 
/* 1340:     */ 
/* 1341:     */ 
/* 1342:     */ 
/* 1343:     */ 
/* 1344:     */ 
/* 1345:1477 */     org.apache.hadoop.mapreduce.Reducer<INKEY, INVALUE, OUTKEY, OUTVALUE>.Context reducerContext = new WrappedReducer().getReducerContext(reduceContext);
/* 1346:     */     
/* 1347:     */ 
/* 1348:     */ 
/* 1349:1481 */     return reducerContext;
/* 1350:     */   }
/* 1351:     */   
/* 1352:     */   @InterfaceAudience.LimitedPrivate({"MapReduce"})
/* 1353:     */   @InterfaceStability.Unstable
/* 1354:     */   public static abstract class CombinerRunner<K, V>
/* 1355:     */   {
/* 1356:     */     protected final Counters.Counter inputCounter;
/* 1357:     */     protected final JobConf job;
/* 1358:     */     protected final Task.TaskReporter reporter;
/* 1359:     */     
/* 1360:     */     CombinerRunner(Counters.Counter inputCounter, JobConf job, Task.TaskReporter reporter)
/* 1361:     */     {
/* 1362:1494 */       this.inputCounter = inputCounter;
/* 1363:1495 */       this.job = job;
/* 1364:1496 */       this.reporter = reporter;
/* 1365:     */     }
/* 1366:     */     
/* 1367:     */     public abstract void combine(RawKeyValueIterator paramRawKeyValueIterator, OutputCollector<K, V> paramOutputCollector)
/* 1368:     */       throws IOException, InterruptedException, ClassNotFoundException;
/* 1369:     */     
/* 1370:     */     public static <K, V> CombinerRunner<K, V> create(JobConf job, TaskAttemptID taskId, Counters.Counter inputCounter, Task.TaskReporter reporter, org.apache.hadoop.mapreduce.OutputCommitter committer)
/* 1371:     */       throws ClassNotFoundException
/* 1372:     */     {
/* 1373:1517 */       Class<? extends Reducer<K, V, K, V>> cls = job.getCombinerClass();
/* 1374:1520 */       if (cls != null) {
/* 1375:1521 */         return new Task.OldCombinerRunner(cls, job, inputCounter, reporter);
/* 1376:     */       }
/* 1377:1524 */       org.apache.hadoop.mapreduce.TaskAttemptContext taskContext = new org.apache.hadoop.mapreduce.task.TaskAttemptContextImpl(job, taskId, reporter);
/* 1378:     */       
/* 1379:     */ 
/* 1380:1527 */       Class<? extends org.apache.hadoop.mapreduce.Reducer<K, V, K, V>> newcls = taskContext.getCombinerClass();
/* 1381:1530 */       if (newcls != null) {
/* 1382:1531 */         return new Task.NewCombinerRunner(newcls, job, taskId, taskContext, inputCounter, reporter, committer);
/* 1383:     */       }
/* 1384:1535 */       return null;
/* 1385:     */     }
/* 1386:     */   }
/* 1387:     */   
/* 1388:     */   @InterfaceAudience.Private
/* 1389:     */   @InterfaceStability.Unstable
/* 1390:     */   protected static class OldCombinerRunner<K, V>
/* 1391:     */     extends Task.CombinerRunner<K, V>
/* 1392:     */   {
/* 1393:     */     private final Class<? extends Reducer<K, V, K, V>> combinerClass;
/* 1394:     */     private final Class<K> keyClass;
/* 1395:     */     private final Class<V> valueClass;
/* 1396:     */     private final RawComparator<K> comparator;
/* 1397:     */     
/* 1398:     */     protected OldCombinerRunner(Class<? extends Reducer<K, V, K, V>> cls, JobConf conf, Counters.Counter inputCounter, Task.TaskReporter reporter)
/* 1399:     */     {
/* 1400:1552 */       super(conf, reporter);
/* 1401:1553 */       this.combinerClass = cls;
/* 1402:1554 */       this.keyClass = this.job.getMapOutputKeyClass();
/* 1403:1555 */       this.valueClass = this.job.getMapOutputValueClass();
/* 1404:1556 */       this.comparator = this.job.getCombinerKeyGroupingComparator();
/* 1405:     */     }
/* 1406:     */     
/* 1407:     */     public void combine(RawKeyValueIterator kvIter, OutputCollector<K, V> combineCollector)
/* 1408:     */       throws IOException
/* 1409:     */     {
/* 1410:1564 */       Reducer<K, V, K, V> combiner = (Reducer)ReflectionUtils.newInstance(this.combinerClass, this.job);
/* 1411:     */       try
/* 1412:     */       {
/* 1413:1567 */         Task.CombineValuesIterator<K, V> values = new Task.CombineValuesIterator(kvIter, this.comparator, this.keyClass, this.valueClass, this.job, this.reporter, this.inputCounter);
/* 1414:1571 */         while (values.more())
/* 1415:     */         {
/* 1416:1572 */           combiner.reduce(values.getKey(), values, combineCollector, this.reporter);
/* 1417:     */           
/* 1418:1574 */           values.nextKey();
/* 1419:     */         }
/* 1420:     */       }
/* 1421:     */       finally
/* 1422:     */       {
/* 1423:1577 */         combiner.close();
/* 1424:     */       }
/* 1425:     */     }
/* 1426:     */   }
/* 1427:     */   
/* 1428:     */   @InterfaceAudience.Private
/* 1429:     */   @InterfaceStability.Unstable
/* 1430:     */   protected static class NewCombinerRunner<K, V>
/* 1431:     */     extends Task.CombinerRunner<K, V>
/* 1432:     */   {
/* 1433:     */     private final Class<? extends org.apache.hadoop.mapreduce.Reducer<K, V, K, V>> reducerClass;
/* 1434:     */     private final org.apache.hadoop.mapreduce.TaskAttemptID taskId;
/* 1435:     */     private final RawComparator<K> comparator;
/* 1436:     */     private final Class<K> keyClass;
/* 1437:     */     private final Class<V> valueClass;
/* 1438:     */     private final org.apache.hadoop.mapreduce.OutputCommitter committer;
/* 1439:     */     
/* 1440:     */     NewCombinerRunner(Class reducerClass, JobConf job, org.apache.hadoop.mapreduce.TaskAttemptID taskId, org.apache.hadoop.mapreduce.TaskAttemptContext context, Counters.Counter inputCounter, Task.TaskReporter reporter, org.apache.hadoop.mapreduce.OutputCommitter committer)
/* 1441:     */     {
/* 1442:1601 */       super(job, reporter);
/* 1443:1602 */       this.reducerClass = reducerClass;
/* 1444:1603 */       this.taskId = taskId;
/* 1445:1604 */       this.keyClass = context.getMapOutputKeyClass();
/* 1446:1605 */       this.valueClass = context.getMapOutputValueClass();
/* 1447:1606 */       this.comparator = context.getCombinerKeyGroupingComparator();
/* 1448:1607 */       this.committer = committer;
/* 1449:     */     }
/* 1450:     */     
/* 1451:     */     private static class OutputConverter<K, V>
/* 1452:     */       extends RecordWriter<K, V>
/* 1453:     */     {
/* 1454:     */       OutputCollector<K, V> output;
/* 1455:     */       
/* 1456:     */       OutputConverter(OutputCollector<K, V> output)
/* 1457:     */       {
/* 1458:1614 */         this.output = output;
/* 1459:     */       }
/* 1460:     */       
/* 1461:     */       public void close(org.apache.hadoop.mapreduce.TaskAttemptContext context) {}
/* 1462:     */       
/* 1463:     */       public void write(K key, V value)
/* 1464:     */         throws IOException, InterruptedException
/* 1465:     */       {
/* 1466:1624 */         this.output.collect(key, value);
/* 1467:     */       }
/* 1468:     */     }
/* 1469:     */     
/* 1470:     */     public void combine(RawKeyValueIterator iterator, OutputCollector<K, V> collector)
/* 1471:     */       throws IOException, InterruptedException, ClassNotFoundException
/* 1472:     */     {
/* 1473:1635 */       org.apache.hadoop.mapreduce.Reducer<K, V, K, V> reducer = (org.apache.hadoop.mapreduce.Reducer)ReflectionUtils.newInstance(this.reducerClass, this.job);
/* 1474:     */       
/* 1475:     */ 
/* 1476:     */ 
/* 1477:1639 */       Reducer.Context reducerContext = Task.createReduceContext(reducer, this.job, this.taskId, iterator, null, this.inputCounter, new OutputConverter(collector), this.committer, this.reporter, this.comparator, this.keyClass, this.valueClass);
/* 1478:     */       
/* 1479:     */ 
/* 1480:     */ 
/* 1481:     */ 
/* 1482:     */ 
/* 1483:1645 */       reducer.run(reducerContext);
/* 1484:     */     }
/* 1485:     */   }
/* 1486:     */   
/* 1487:     */   BytesWritable getExtraData()
/* 1488:     */   {
/* 1489:1650 */     return this.extraData;
/* 1490:     */   }
/* 1491:     */   
/* 1492:     */   void setExtraData(BytesWritable extraData)
/* 1493:     */   {
/* 1494:1654 */     this.extraData = extraData;
/* 1495:     */   }
/* 1496:     */   
/* 1497:     */   public abstract void run(JobConf paramJobConf, TaskUmbilicalProtocol paramTaskUmbilicalProtocol)
/* 1498:     */     throws IOException, ClassNotFoundException, InterruptedException;
/* 1499:     */   
/* 1500:     */   public abstract boolean isMapTask();
/* 1501:     */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.Task
 * JD-Core Version:    0.7.0.1
 */