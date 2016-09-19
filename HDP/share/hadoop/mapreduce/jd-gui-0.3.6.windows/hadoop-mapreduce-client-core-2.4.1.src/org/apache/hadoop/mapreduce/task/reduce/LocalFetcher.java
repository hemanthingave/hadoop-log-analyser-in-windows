/*   1:    */ package org.apache.hadoop.mapreduce.task.reduce;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.util.HashSet;
/*   5:    */ import java.util.Iterator;
/*   6:    */ import java.util.Map;
/*   7:    */ import java.util.Set;
/*   8:    */ import javax.crypto.SecretKey;
/*   9:    */ import org.apache.commons.logging.Log;
/*  10:    */ import org.apache.commons.logging.LogFactory;
/*  11:    */ import org.apache.hadoop.fs.FSDataInputStream;
/*  12:    */ import org.apache.hadoop.fs.FileSystem;
/*  13:    */ import org.apache.hadoop.fs.LocalFileSystem;
/*  14:    */ import org.apache.hadoop.fs.Path;
/*  15:    */ import org.apache.hadoop.mapred.IndexRecord;
/*  16:    */ import org.apache.hadoop.mapred.JobConf;
/*  17:    */ import org.apache.hadoop.mapred.MapOutputFile;
/*  18:    */ import org.apache.hadoop.mapred.Reporter;
/*  19:    */ import org.apache.hadoop.mapred.SpillRecord;
/*  20:    */ import org.apache.hadoop.mapreduce.TaskAttemptID;
/*  21:    */ 
/*  22:    */ class LocalFetcher<K, V>
/*  23:    */   extends Fetcher<K, V>
/*  24:    */ {
/*  25: 46 */   private static final Log LOG = LogFactory.getLog(LocalFetcher.class);
/*  26: 48 */   private static final MapHost LOCALHOST = new MapHost("local", "local");
/*  27:    */   private JobConf job;
/*  28:    */   private Map<TaskAttemptID, MapOutputFile> localMapFiles;
/*  29:    */   
/*  30:    */   public LocalFetcher(JobConf job, TaskAttemptID reduceId, ShuffleSchedulerImpl<K, V> scheduler, MergeManager<K, V> merger, Reporter reporter, ShuffleClientMetrics metrics, ExceptionReporter exceptionReporter, SecretKey shuffleKey, Map<TaskAttemptID, MapOutputFile> localMapFiles)
/*  31:    */   {
/*  32: 60 */     super(job, reduceId, scheduler, merger, reporter, metrics, exceptionReporter, shuffleKey);
/*  33:    */     
/*  34:    */ 
/*  35: 63 */     this.job = job;
/*  36: 64 */     this.localMapFiles = localMapFiles;
/*  37:    */     
/*  38: 66 */     setName("localfetcher#" + this.id);
/*  39: 67 */     setDaemon(true);
/*  40:    */   }
/*  41:    */   
/*  42:    */   public void run()
/*  43:    */   {
/*  44: 72 */     Set<TaskAttemptID> maps = new HashSet();
/*  45: 73 */     for (TaskAttemptID map : this.localMapFiles.keySet()) {
/*  46: 74 */       maps.add(map);
/*  47:    */     }
/*  48: 77 */     while (maps.size() > 0) {
/*  49:    */       try
/*  50:    */       {
/*  51: 80 */         this.merger.waitForResource();
/*  52: 81 */         this.metrics.threadBusy();
/*  53:    */         
/*  54:    */ 
/*  55: 84 */         doCopy(maps);
/*  56: 85 */         this.metrics.threadFree();
/*  57:    */       }
/*  58:    */       catch (InterruptedException ie) {}catch (Throwable t)
/*  59:    */       {
/*  60: 88 */         this.exceptionReporter.reportException(t);
/*  61:    */       }
/*  62:    */     }
/*  63:    */   }
/*  64:    */   
/*  65:    */   private void doCopy(Set<TaskAttemptID> maps)
/*  66:    */     throws IOException
/*  67:    */   {
/*  68: 97 */     Iterator<TaskAttemptID> iter = maps.iterator();
/*  69: 98 */     while (iter.hasNext())
/*  70:    */     {
/*  71: 99 */       TaskAttemptID map = (TaskAttemptID)iter.next();
/*  72:100 */       LOG.debug("LocalFetcher " + this.id + " going to fetch: " + map);
/*  73:101 */       if (!copyMapOutput(map)) {
/*  74:    */         break;
/*  75:    */       }
/*  76:103 */       iter.remove();
/*  77:    */     }
/*  78:    */   }
/*  79:    */   
/*  80:    */   private boolean copyMapOutput(TaskAttemptID mapTaskId)
/*  81:    */     throws IOException
/*  82:    */   {
/*  83:118 */     Path mapOutputFileName = ((MapOutputFile)this.localMapFiles.get(mapTaskId)).getOutputFile();
/*  84:119 */     Path indexFileName = mapOutputFileName.suffix(".index");
/*  85:    */     
/*  86:    */ 
/*  87:    */ 
/*  88:123 */     SpillRecord sr = new SpillRecord(indexFileName, this.job);
/*  89:124 */     IndexRecord ir = sr.getIndex(this.reduce);
/*  90:    */     
/*  91:126 */     long compressedLength = ir.partLength;
/*  92:127 */     long decompressedLength = ir.rawLength;
/*  93:    */     
/*  94:    */ 
/*  95:130 */     MapOutput<K, V> mapOutput = this.merger.reserve(mapTaskId, decompressedLength, this.id);
/*  96:134 */     if (mapOutput == null)
/*  97:    */     {
/*  98:135 */       LOG.info("fetcher#" + this.id + " - MergeManager returned Status.WAIT ...");
/*  99:136 */       return false;
/* 100:    */     }
/* 101:140 */     LOG.info("localfetcher#" + this.id + " about to shuffle output of map " + mapOutput.getMapId() + " decomp: " + decompressedLength + " len: " + compressedLength + " to " + mapOutput.getDescription());
/* 102:    */     
/* 103:    */ 
/* 104:    */ 
/* 105:    */ 
/* 106:    */ 
/* 107:146 */     FileSystem localFs = FileSystem.getLocal(this.job).getRaw();
/* 108:147 */     FSDataInputStream inStream = localFs.open(mapOutputFileName);
/* 109:    */     try
/* 110:    */     {
/* 111:149 */       inStream.seek(ir.startOffset);
/* 112:    */       
/* 113:151 */       mapOutput.shuffle(LOCALHOST, inStream, compressedLength, decompressedLength, this.metrics, this.reporter);
/* 114:    */       try
/* 115:    */       {
/* 116:154 */         inStream.close();
/* 117:    */       }
/* 118:    */       catch (IOException ioe)
/* 119:    */       {
/* 120:156 */         LOG.warn("IOException closing inputstream from map output: " + ioe.toString());
/* 121:    */       }
/* 122:161 */       this.scheduler.copySucceeded(mapTaskId, LOCALHOST, compressedLength, 0L, mapOutput);
/* 123:    */     }
/* 124:    */     finally
/* 125:    */     {
/* 126:    */       try
/* 127:    */       {
/* 128:154 */         inStream.close();
/* 129:    */       }
/* 130:    */       catch (IOException ioe)
/* 131:    */       {
/* 132:156 */         LOG.warn("IOException closing inputstream from map output: " + ioe.toString());
/* 133:    */       }
/* 134:    */     }
/* 135:163 */     return true;
/* 136:    */   }
/* 137:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.task.reduce.LocalFetcher
 * JD-Core Version:    0.7.0.1
 */