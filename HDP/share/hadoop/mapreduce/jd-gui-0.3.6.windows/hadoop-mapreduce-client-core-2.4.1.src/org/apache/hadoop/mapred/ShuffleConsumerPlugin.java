/*   1:    */ package org.apache.hadoop.mapred;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.util.Map;
/*   5:    */ import org.apache.hadoop.classification.InterfaceAudience.LimitedPrivate;
/*   6:    */ import org.apache.hadoop.classification.InterfaceStability.Unstable;
/*   7:    */ import org.apache.hadoop.fs.FileSystem;
/*   8:    */ import org.apache.hadoop.fs.LocalDirAllocator;
/*   9:    */ import org.apache.hadoop.io.compress.CompressionCodec;
/*  10:    */ import org.apache.hadoop.util.Progress;
/*  11:    */ 
/*  12:    */ @InterfaceAudience.LimitedPrivate({"mapreduce"})
/*  13:    */ @InterfaceStability.Unstable
/*  14:    */ public abstract interface ShuffleConsumerPlugin<K, V>
/*  15:    */ {
/*  16:    */   public abstract void init(Context<K, V> paramContext);
/*  17:    */   
/*  18:    */   public abstract RawKeyValueIterator run()
/*  19:    */     throws IOException, InterruptedException;
/*  20:    */   
/*  21:    */   public abstract void close();
/*  22:    */   
/*  23:    */   @InterfaceAudience.LimitedPrivate({"mapreduce"})
/*  24:    */   @InterfaceStability.Unstable
/*  25:    */   public static class Context<K, V>
/*  26:    */   {
/*  27:    */     private final org.apache.hadoop.mapreduce.TaskAttemptID reduceId;
/*  28:    */     private final JobConf jobConf;
/*  29:    */     private final FileSystem localFS;
/*  30:    */     private final TaskUmbilicalProtocol umbilical;
/*  31:    */     private final LocalDirAllocator localDirAllocator;
/*  32:    */     private final Reporter reporter;
/*  33:    */     private final CompressionCodec codec;
/*  34:    */     private final Class<? extends Reducer> combinerClass;
/*  35:    */     private final Task.CombineOutputCollector<K, V> combineCollector;
/*  36:    */     private final Counters.Counter spilledRecordsCounter;
/*  37:    */     private final Counters.Counter reduceCombineInputCounter;
/*  38:    */     private final Counters.Counter shuffledMapsCounter;
/*  39:    */     private final Counters.Counter reduceShuffleBytes;
/*  40:    */     private final Counters.Counter failedShuffleCounter;
/*  41:    */     private final Counters.Counter mergedMapOutputsCounter;
/*  42:    */     private final TaskStatus status;
/*  43:    */     private final Progress copyPhase;
/*  44:    */     private final Progress mergePhase;
/*  45:    */     private final Task reduceTask;
/*  46:    */     private final MapOutputFile mapOutputFile;
/*  47:    */     private final Map<TaskAttemptID, MapOutputFile> localMapFiles;
/*  48:    */     
/*  49:    */     public Context(org.apache.hadoop.mapreduce.TaskAttemptID reduceId, JobConf jobConf, FileSystem localFS, TaskUmbilicalProtocol umbilical, LocalDirAllocator localDirAllocator, Reporter reporter, CompressionCodec codec, Class<? extends Reducer> combinerClass, Task.CombineOutputCollector<K, V> combineCollector, Counters.Counter spilledRecordsCounter, Counters.Counter reduceCombineInputCounter, Counters.Counter shuffledMapsCounter, Counters.Counter reduceShuffleBytes, Counters.Counter failedShuffleCounter, Counters.Counter mergedMapOutputsCounter, TaskStatus status, Progress copyPhase, Progress mergePhase, Task reduceTask, MapOutputFile mapOutputFile, Map<TaskAttemptID, MapOutputFile> localMapFiles)
/*  50:    */     {
/*  51: 88 */       this.reduceId = reduceId;
/*  52: 89 */       this.jobConf = jobConf;
/*  53: 90 */       this.localFS = localFS;
/*  54: 91 */       this.umbilical = umbilical;
/*  55: 92 */       this.localDirAllocator = localDirAllocator;
/*  56: 93 */       this.reporter = reporter;
/*  57: 94 */       this.codec = codec;
/*  58: 95 */       this.combinerClass = combinerClass;
/*  59: 96 */       this.combineCollector = combineCollector;
/*  60: 97 */       this.spilledRecordsCounter = spilledRecordsCounter;
/*  61: 98 */       this.reduceCombineInputCounter = reduceCombineInputCounter;
/*  62: 99 */       this.shuffledMapsCounter = shuffledMapsCounter;
/*  63:100 */       this.reduceShuffleBytes = reduceShuffleBytes;
/*  64:101 */       this.failedShuffleCounter = failedShuffleCounter;
/*  65:102 */       this.mergedMapOutputsCounter = mergedMapOutputsCounter;
/*  66:103 */       this.status = status;
/*  67:104 */       this.copyPhase = copyPhase;
/*  68:105 */       this.mergePhase = mergePhase;
/*  69:106 */       this.reduceTask = reduceTask;
/*  70:107 */       this.mapOutputFile = mapOutputFile;
/*  71:108 */       this.localMapFiles = localMapFiles;
/*  72:    */     }
/*  73:    */     
/*  74:    */     public org.apache.hadoop.mapreduce.TaskAttemptID getReduceId()
/*  75:    */     {
/*  76:112 */       return this.reduceId;
/*  77:    */     }
/*  78:    */     
/*  79:    */     public JobConf getJobConf()
/*  80:    */     {
/*  81:115 */       return this.jobConf;
/*  82:    */     }
/*  83:    */     
/*  84:    */     public FileSystem getLocalFS()
/*  85:    */     {
/*  86:118 */       return this.localFS;
/*  87:    */     }
/*  88:    */     
/*  89:    */     public TaskUmbilicalProtocol getUmbilical()
/*  90:    */     {
/*  91:121 */       return this.umbilical;
/*  92:    */     }
/*  93:    */     
/*  94:    */     public LocalDirAllocator getLocalDirAllocator()
/*  95:    */     {
/*  96:124 */       return this.localDirAllocator;
/*  97:    */     }
/*  98:    */     
/*  99:    */     public Reporter getReporter()
/* 100:    */     {
/* 101:127 */       return this.reporter;
/* 102:    */     }
/* 103:    */     
/* 104:    */     public CompressionCodec getCodec()
/* 105:    */     {
/* 106:130 */       return this.codec;
/* 107:    */     }
/* 108:    */     
/* 109:    */     public Class<? extends Reducer> getCombinerClass()
/* 110:    */     {
/* 111:133 */       return this.combinerClass;
/* 112:    */     }
/* 113:    */     
/* 114:    */     public Task.CombineOutputCollector<K, V> getCombineCollector()
/* 115:    */     {
/* 116:136 */       return this.combineCollector;
/* 117:    */     }
/* 118:    */     
/* 119:    */     public Counters.Counter getSpilledRecordsCounter()
/* 120:    */     {
/* 121:139 */       return this.spilledRecordsCounter;
/* 122:    */     }
/* 123:    */     
/* 124:    */     public Counters.Counter getReduceCombineInputCounter()
/* 125:    */     {
/* 126:142 */       return this.reduceCombineInputCounter;
/* 127:    */     }
/* 128:    */     
/* 129:    */     public Counters.Counter getShuffledMapsCounter()
/* 130:    */     {
/* 131:145 */       return this.shuffledMapsCounter;
/* 132:    */     }
/* 133:    */     
/* 134:    */     public Counters.Counter getReduceShuffleBytes()
/* 135:    */     {
/* 136:148 */       return this.reduceShuffleBytes;
/* 137:    */     }
/* 138:    */     
/* 139:    */     public Counters.Counter getFailedShuffleCounter()
/* 140:    */     {
/* 141:151 */       return this.failedShuffleCounter;
/* 142:    */     }
/* 143:    */     
/* 144:    */     public Counters.Counter getMergedMapOutputsCounter()
/* 145:    */     {
/* 146:154 */       return this.mergedMapOutputsCounter;
/* 147:    */     }
/* 148:    */     
/* 149:    */     public TaskStatus getStatus()
/* 150:    */     {
/* 151:157 */       return this.status;
/* 152:    */     }
/* 153:    */     
/* 154:    */     public Progress getCopyPhase()
/* 155:    */     {
/* 156:160 */       return this.copyPhase;
/* 157:    */     }
/* 158:    */     
/* 159:    */     public Progress getMergePhase()
/* 160:    */     {
/* 161:163 */       return this.mergePhase;
/* 162:    */     }
/* 163:    */     
/* 164:    */     public Task getReduceTask()
/* 165:    */     {
/* 166:166 */       return this.reduceTask;
/* 167:    */     }
/* 168:    */     
/* 169:    */     public MapOutputFile getMapOutputFile()
/* 170:    */     {
/* 171:169 */       return this.mapOutputFile;
/* 172:    */     }
/* 173:    */     
/* 174:    */     public Map<TaskAttemptID, MapOutputFile> getLocalMapFiles()
/* 175:    */     {
/* 176:172 */       return this.localMapFiles;
/* 177:    */     }
/* 178:    */   }
/* 179:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.ShuffleConsumerPlugin
 * JD-Core Version:    0.7.0.1
 */