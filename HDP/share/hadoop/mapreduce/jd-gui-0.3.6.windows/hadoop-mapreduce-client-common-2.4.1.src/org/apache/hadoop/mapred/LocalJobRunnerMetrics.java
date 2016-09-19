/*  1:   */ package org.apache.hadoop.mapred;
/*  2:   */ 
/*  3:   */ import org.apache.hadoop.metrics.MetricsContext;
/*  4:   */ import org.apache.hadoop.metrics.MetricsRecord;
/*  5:   */ import org.apache.hadoop.metrics.MetricsUtil;
/*  6:   */ import org.apache.hadoop.metrics.Updater;
/*  7:   */ import org.apache.hadoop.metrics.jvm.JvmMetrics;
/*  8:   */ 
/*  9:   */ class LocalJobRunnerMetrics
/* 10:   */   implements Updater
/* 11:   */ {
/* 12:   */   private final MetricsRecord metricsRecord;
/* 13:30 */   private int numMapTasksLaunched = 0;
/* 14:31 */   private int numMapTasksCompleted = 0;
/* 15:32 */   private int numReduceTasksLaunched = 0;
/* 16:33 */   private int numReduceTasksCompleted = 0;
/* 17:34 */   private int numWaitingMaps = 0;
/* 18:35 */   private int numWaitingReduces = 0;
/* 19:   */   
/* 20:   */   public LocalJobRunnerMetrics(JobConf conf)
/* 21:   */   {
/* 22:38 */     String sessionId = conf.getSessionId();
/* 23:   */     
/* 24:40 */     JvmMetrics.init("JobTracker", sessionId);
/* 25:   */     
/* 26:42 */     MetricsContext context = MetricsUtil.getContext("mapred");
/* 27:   */     
/* 28:44 */     this.metricsRecord = MetricsUtil.createRecord(context, "jobtracker");
/* 29:45 */     this.metricsRecord.setTag("sessionId", sessionId);
/* 30:46 */     context.registerUpdater(this);
/* 31:   */   }
/* 32:   */   
/* 33:   */   public void doUpdates(MetricsContext unused)
/* 34:   */   {
/* 35:54 */     synchronized (this)
/* 36:   */     {
/* 37:55 */       this.metricsRecord.incrMetric("maps_launched", this.numMapTasksLaunched);
/* 38:56 */       this.metricsRecord.incrMetric("maps_completed", this.numMapTasksCompleted);
/* 39:57 */       this.metricsRecord.incrMetric("reduces_launched", this.numReduceTasksLaunched);
/* 40:58 */       this.metricsRecord.incrMetric("reduces_completed", this.numReduceTasksCompleted);
/* 41:59 */       this.metricsRecord.incrMetric("waiting_maps", this.numWaitingMaps);
/* 42:60 */       this.metricsRecord.incrMetric("waiting_reduces", this.numWaitingReduces);
/* 43:   */       
/* 44:62 */       this.numMapTasksLaunched = 0;
/* 45:63 */       this.numMapTasksCompleted = 0;
/* 46:64 */       this.numReduceTasksLaunched = 0;
/* 47:65 */       this.numReduceTasksCompleted = 0;
/* 48:66 */       this.numWaitingMaps = 0;
/* 49:67 */       this.numWaitingReduces = 0;
/* 50:   */     }
/* 51:69 */     this.metricsRecord.update();
/* 52:   */   }
/* 53:   */   
/* 54:   */   public synchronized void launchMap(TaskAttemptID taskAttemptID)
/* 55:   */   {
/* 56:73 */     this.numMapTasksLaunched += 1;
/* 57:74 */     decWaitingMaps(taskAttemptID.getJobID(), 1);
/* 58:   */   }
/* 59:   */   
/* 60:   */   public synchronized void completeMap(TaskAttemptID taskAttemptID)
/* 61:   */   {
/* 62:78 */     this.numMapTasksCompleted += 1;
/* 63:   */   }
/* 64:   */   
/* 65:   */   public synchronized void launchReduce(TaskAttemptID taskAttemptID)
/* 66:   */   {
/* 67:82 */     this.numReduceTasksLaunched += 1;
/* 68:83 */     decWaitingReduces(taskAttemptID.getJobID(), 1);
/* 69:   */   }
/* 70:   */   
/* 71:   */   public synchronized void completeReduce(TaskAttemptID taskAttemptID)
/* 72:   */   {
/* 73:87 */     this.numReduceTasksCompleted += 1;
/* 74:   */   }
/* 75:   */   
/* 76:   */   private synchronized void decWaitingMaps(JobID id, int task)
/* 77:   */   {
/* 78:91 */     this.numWaitingMaps -= task;
/* 79:   */   }
/* 80:   */   
/* 81:   */   private synchronized void decWaitingReduces(JobID id, int task)
/* 82:   */   {
/* 83:95 */     this.numWaitingReduces -= task;
/* 84:   */   }
/* 85:   */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-common-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.LocalJobRunnerMetrics
 * JD-Core Version:    0.7.0.1
 */