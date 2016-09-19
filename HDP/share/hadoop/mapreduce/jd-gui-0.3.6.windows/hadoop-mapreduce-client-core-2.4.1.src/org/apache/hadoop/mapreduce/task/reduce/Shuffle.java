/*   1:    */ package org.apache.hadoop.mapreduce.task.reduce;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.util.Map;
/*   5:    */ import org.apache.hadoop.classification.InterfaceAudience.LimitedPrivate;
/*   6:    */ import org.apache.hadoop.classification.InterfaceStability.Unstable;
/*   7:    */ import org.apache.hadoop.mapred.JobConf;
/*   8:    */ import org.apache.hadoop.mapred.MapOutputFile;
/*   9:    */ import org.apache.hadoop.mapred.RawKeyValueIterator;
/*  10:    */ import org.apache.hadoop.mapred.Reporter;
/*  11:    */ import org.apache.hadoop.mapred.ShuffleConsumerPlugin;
/*  12:    */ import org.apache.hadoop.mapred.ShuffleConsumerPlugin.Context;
/*  13:    */ import org.apache.hadoop.mapred.Task;
/*  14:    */ import org.apache.hadoop.mapred.TaskStatus;
/*  15:    */ import org.apache.hadoop.mapred.TaskStatus.Phase;
/*  16:    */ import org.apache.hadoop.mapred.TaskUmbilicalProtocol;
/*  17:    */ import org.apache.hadoop.mapreduce.TaskAttemptID;
/*  18:    */ import org.apache.hadoop.util.Progress;
/*  19:    */ 
/*  20:    */ @InterfaceAudience.LimitedPrivate({"MapReduce"})
/*  21:    */ @InterfaceStability.Unstable
/*  22:    */ public class Shuffle<K, V>
/*  23:    */   implements ShuffleConsumerPlugin<K, V>, ExceptionReporter
/*  24:    */ {
/*  25:    */   private static final int PROGRESS_FREQUENCY = 2000;
/*  26:    */   private static final int MAX_EVENTS_TO_FETCH = 10000;
/*  27:    */   private static final int MIN_EVENTS_TO_FETCH = 100;
/*  28:    */   private static final int MAX_RPC_OUTSTANDING_EVENTS = 3000000;
/*  29:    */   private ShuffleConsumerPlugin.Context context;
/*  30:    */   private TaskAttemptID reduceId;
/*  31:    */   private JobConf jobConf;
/*  32:    */   private Reporter reporter;
/*  33:    */   private ShuffleClientMetrics metrics;
/*  34:    */   private TaskUmbilicalProtocol umbilical;
/*  35:    */   private ShuffleSchedulerImpl<K, V> scheduler;
/*  36:    */   private MergeManager<K, V> merger;
/*  37:    */   private Throwable throwable;
/*  38:    */   private String throwingThreadName;
/*  39:    */   private Progress copyPhase;
/*  40:    */   private TaskStatus taskStatus;
/*  41:    */   private Task reduceTask;
/*  42:    */   private Map<TaskAttemptID, MapOutputFile> localMapFiles;
/*  43:    */   
/*  44:    */   public Shuffle()
/*  45:    */   {
/*  46: 56 */     this.throwable = null;
/*  47: 57 */     this.throwingThreadName = null;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public void init(ShuffleConsumerPlugin.Context context)
/*  51:    */   {
/*  52: 65 */     this.context = context;
/*  53:    */     
/*  54: 67 */     this.reduceId = context.getReduceId();
/*  55: 68 */     this.jobConf = context.getJobConf();
/*  56: 69 */     this.umbilical = context.getUmbilical();
/*  57: 70 */     this.reporter = context.getReporter();
/*  58: 71 */     this.metrics = new ShuffleClientMetrics(this.reduceId, this.jobConf);
/*  59: 72 */     this.copyPhase = context.getCopyPhase();
/*  60: 73 */     this.taskStatus = context.getStatus();
/*  61: 74 */     this.reduceTask = context.getReduceTask();
/*  62: 75 */     this.localMapFiles = context.getLocalMapFiles();
/*  63:    */     
/*  64: 77 */     this.scheduler = new ShuffleSchedulerImpl(this.jobConf, this.taskStatus, this.reduceId, this, this.copyPhase, context.getShuffledMapsCounter(), context.getReduceShuffleBytes(), context.getFailedShuffleCounter());
/*  65:    */     
/*  66:    */ 
/*  67: 80 */     this.merger = createMergeManager(context);
/*  68:    */   }
/*  69:    */   
/*  70:    */   protected MergeManager<K, V> createMergeManager(ShuffleConsumerPlugin.Context context)
/*  71:    */   {
/*  72: 85 */     return new MergeManagerImpl(this.reduceId, this.jobConf, context.getLocalFS(), context.getLocalDirAllocator(), this.reporter, context.getCodec(), context.getCombinerClass(), context.getCombineCollector(), context.getSpilledRecordsCounter(), context.getReduceCombineInputCounter(), context.getMergedMapOutputsCounter(), this, context.getMergePhase(), context.getMapOutputFile());
/*  73:    */   }
/*  74:    */   
/*  75:    */   public RawKeyValueIterator run()
/*  76:    */     throws IOException, InterruptedException
/*  77:    */   {
/*  78: 99 */     int eventsPerReducer = Math.max(100, 3000000 / this.jobConf.getNumReduceTasks());
/*  79:    */     
/*  80:101 */     int maxEventsToFetch = Math.min(10000, eventsPerReducer);
/*  81:    */     
/*  82:    */ 
/*  83:104 */     EventFetcher<K, V> eventFetcher = new EventFetcher(this.reduceId, this.umbilical, this.scheduler, this, maxEventsToFetch);
/*  84:    */     
/*  85:    */ 
/*  86:107 */     eventFetcher.start();
/*  87:    */     
/*  88:    */ 
/*  89:110 */     boolean isLocal = this.localMapFiles != null;
/*  90:111 */     int numFetchers = isLocal ? 1 : this.jobConf.getInt("mapreduce.reduce.shuffle.parallelcopies", 5);
/*  91:    */     
/*  92:113 */     Fetcher<K, V>[] fetchers = new Fetcher[numFetchers];
/*  93:114 */     if (isLocal)
/*  94:    */     {
/*  95:115 */       fetchers[0] = new LocalFetcher(this.jobConf, this.reduceId, this.scheduler, this.merger, this.reporter, this.metrics, this, this.reduceTask.getShuffleSecret(), this.localMapFiles);
/*  96:    */       
/*  97:    */ 
/*  98:118 */       fetchers[0].start();
/*  99:    */     }
/* 100:    */     else
/* 101:    */     {
/* 102:120 */       for (int i = 0; i < numFetchers; i++)
/* 103:    */       {
/* 104:121 */         fetchers[i] = new Fetcher(this.jobConf, this.reduceId, this.scheduler, this.merger, this.reporter, this.metrics, this, this.reduceTask.getShuffleSecret());
/* 105:    */         
/* 106:    */ 
/* 107:124 */         fetchers[i].start();
/* 108:    */       }
/* 109:    */     }
/* 110:129 */     while (!this.scheduler.waitUntilDone(2000))
/* 111:    */     {
/* 112:130 */       this.reporter.progress();
/* 113:132 */       synchronized (this)
/* 114:    */       {
/* 115:133 */         if (this.throwable != null) {
/* 116:134 */           throw new ShuffleError("error in shuffle in " + this.throwingThreadName, this.throwable);
/* 117:    */         }
/* 118:    */       }
/* 119:    */     }
/* 120:141 */     eventFetcher.shutDown();
/* 121:144 */     for (Fetcher<K, V> fetcher : fetchers) {
/* 122:145 */       fetcher.shutDown();
/* 123:    */     }
/* 124:149 */     this.scheduler.close();
/* 125:    */     
/* 126:151 */     this.copyPhase.complete();
/* 127:152 */     this.taskStatus.setPhase(TaskStatus.Phase.SORT);
/* 128:153 */     this.reduceTask.statusUpdate(this.umbilical);
/* 129:    */     
/* 130:    */ 
/* 131:156 */     RawKeyValueIterator kvIter = null;
/* 132:    */     try
/* 133:    */     {
/* 134:158 */       kvIter = this.merger.close();
/* 135:    */     }
/* 136:    */     catch (Throwable e)
/* 137:    */     {
/* 138:160 */       throw new ShuffleError("Error while doing final merge ", e);
/* 139:    */     }
/* 140:164 */     synchronized (this)
/* 141:    */     {
/* 142:165 */       if (this.throwable != null) {
/* 143:166 */         throw new ShuffleError("error in shuffle in " + this.throwingThreadName, this.throwable);
/* 144:    */       }
/* 145:    */     }
/* 146:171 */     return kvIter;
/* 147:    */   }
/* 148:    */   
/* 149:    */   public void close() {}
/* 150:    */   
/* 151:    */   public synchronized void reportException(Throwable t)
/* 152:    */   {
/* 153:179 */     if (this.throwable == null)
/* 154:    */     {
/* 155:180 */       this.throwable = t;
/* 156:181 */       this.throwingThreadName = Thread.currentThread().getName();
/* 157:184 */       synchronized (this.scheduler)
/* 158:    */       {
/* 159:185 */         this.scheduler.notifyAll();
/* 160:    */       }
/* 161:    */     }
/* 162:    */   }
/* 163:    */   
/* 164:    */   public static class ShuffleError
/* 165:    */     extends IOException
/* 166:    */   {
/* 167:    */     private static final long serialVersionUID = 5753909320586607881L;
/* 168:    */     
/* 169:    */     ShuffleError(String msg, Throwable t)
/* 170:    */     {
/* 171:194 */       super(t);
/* 172:    */     }
/* 173:    */   }
/* 174:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.task.reduce.Shuffle
 * JD-Core Version:    0.7.0.1
 */