/*  1:   */ package org.apache.hadoop.mapreduce.task.reduce;
/*  2:   */ 
/*  3:   */ import org.apache.hadoop.classification.InterfaceAudience.LimitedPrivate;
/*  4:   */ import org.apache.hadoop.classification.InterfaceStability.Unstable;
/*  5:   */ import org.apache.hadoop.mapred.JobConf;
/*  6:   */ import org.apache.hadoop.mapreduce.JobID;
/*  7:   */ import org.apache.hadoop.mapreduce.TaskAttemptID;
/*  8:   */ import org.apache.hadoop.metrics.MetricsContext;
/*  9:   */ import org.apache.hadoop.metrics.MetricsRecord;
/* 10:   */ import org.apache.hadoop.metrics.MetricsUtil;
/* 11:   */ import org.apache.hadoop.metrics.Updater;
/* 12:   */ 
/* 13:   */ @InterfaceAudience.LimitedPrivate({"MapReduce"})
/* 14:   */ @InterfaceStability.Unstable
/* 15:   */ public class ShuffleClientMetrics
/* 16:   */   implements Updater
/* 17:   */ {
/* 18:35 */   private MetricsRecord shuffleMetrics = null;
/* 19:36 */   private int numFailedFetches = 0;
/* 20:37 */   private int numSuccessFetches = 0;
/* 21:38 */   private long numBytes = 0L;
/* 22:39 */   private int numThreadsBusy = 0;
/* 23:   */   private final int numCopiers;
/* 24:   */   
/* 25:   */   ShuffleClientMetrics(TaskAttemptID reduceId, JobConf jobConf)
/* 26:   */   {
/* 27:43 */     this.numCopiers = jobConf.getInt("mapreduce.reduce.shuffle.parallelcopies", 5);
/* 28:   */     
/* 29:45 */     MetricsContext metricsContext = MetricsUtil.getContext("mapred");
/* 30:46 */     this.shuffleMetrics = MetricsUtil.createRecord(metricsContext, "shuffleInput");
/* 31:   */     
/* 32:48 */     this.shuffleMetrics.setTag("user", jobConf.getUser());
/* 33:49 */     this.shuffleMetrics.setTag("jobName", jobConf.getJobName());
/* 34:50 */     this.shuffleMetrics.setTag("jobId", reduceId.getJobID().toString());
/* 35:51 */     this.shuffleMetrics.setTag("taskId", reduceId.toString());
/* 36:52 */     this.shuffleMetrics.setTag("sessionId", jobConf.getSessionId());
/* 37:53 */     metricsContext.registerUpdater(this);
/* 38:   */   }
/* 39:   */   
/* 40:   */   public synchronized void inputBytes(long numBytes)
/* 41:   */   {
/* 42:56 */     this.numBytes += numBytes;
/* 43:   */   }
/* 44:   */   
/* 45:   */   public synchronized void failedFetch()
/* 46:   */   {
/* 47:59 */     this.numFailedFetches += 1;
/* 48:   */   }
/* 49:   */   
/* 50:   */   public synchronized void successFetch()
/* 51:   */   {
/* 52:62 */     this.numSuccessFetches += 1;
/* 53:   */   }
/* 54:   */   
/* 55:   */   public synchronized void threadBusy()
/* 56:   */   {
/* 57:65 */     this.numThreadsBusy += 1;
/* 58:   */   }
/* 59:   */   
/* 60:   */   public synchronized void threadFree()
/* 61:   */   {
/* 62:68 */     this.numThreadsBusy -= 1;
/* 63:   */   }
/* 64:   */   
/* 65:   */   public void doUpdates(MetricsContext unused)
/* 66:   */   {
/* 67:71 */     synchronized (this)
/* 68:   */     {
/* 69:72 */       this.shuffleMetrics.incrMetric("shuffle_input_bytes", this.numBytes);
/* 70:73 */       this.shuffleMetrics.incrMetric("shuffle_failed_fetches", this.numFailedFetches);
/* 71:   */       
/* 72:75 */       this.shuffleMetrics.incrMetric("shuffle_success_fetches", this.numSuccessFetches);
/* 73:77 */       if (this.numCopiers != 0) {
/* 74:78 */         this.shuffleMetrics.setMetric("shuffle_fetchers_busy_percent", 100.0F * (this.numThreadsBusy / this.numCopiers));
/* 75:   */       } else {
/* 76:81 */         this.shuffleMetrics.setMetric("shuffle_fetchers_busy_percent", 0);
/* 77:   */       }
/* 78:83 */       this.numBytes = 0L;
/* 79:84 */       this.numSuccessFetches = 0;
/* 80:85 */       this.numFailedFetches = 0;
/* 81:   */     }
/* 82:87 */     this.shuffleMetrics.update();
/* 83:   */   }
/* 84:   */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.task.reduce.ShuffleClientMetrics
 * JD-Core Version:    0.7.0.1
 */