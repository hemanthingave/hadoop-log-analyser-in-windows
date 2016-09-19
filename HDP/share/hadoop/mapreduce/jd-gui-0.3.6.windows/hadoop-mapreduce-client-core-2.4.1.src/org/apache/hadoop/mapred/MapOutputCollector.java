/*  1:   */ package org.apache.hadoop.mapred;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import org.apache.hadoop.classification.InterfaceAudience.LimitedPrivate;
/*  5:   */ import org.apache.hadoop.classification.InterfaceStability.Unstable;
/*  6:   */ 
/*  7:   */ @InterfaceAudience.LimitedPrivate({"MapReduce"})
/*  8:   */ @InterfaceStability.Unstable
/*  9:   */ public abstract interface MapOutputCollector<K, V>
/* 10:   */ {
/* 11:   */   public abstract void init(Context paramContext)
/* 12:   */     throws IOException, ClassNotFoundException;
/* 13:   */   
/* 14:   */   public abstract void collect(K paramK, V paramV, int paramInt)
/* 15:   */     throws IOException, InterruptedException;
/* 16:   */   
/* 17:   */   public abstract void close()
/* 18:   */     throws IOException, InterruptedException;
/* 19:   */   
/* 20:   */   public abstract void flush()
/* 21:   */     throws IOException, InterruptedException, ClassNotFoundException;
/* 22:   */   
/* 23:   */   @InterfaceAudience.LimitedPrivate({"MapReduce"})
/* 24:   */   @InterfaceStability.Unstable
/* 25:   */   public static class Context
/* 26:   */   {
/* 27:   */     private final MapTask mapTask;
/* 28:   */     private final JobConf jobConf;
/* 29:   */     private final Task.TaskReporter reporter;
/* 30:   */     
/* 31:   */     public Context(MapTask mapTask, JobConf jobConf, Task.TaskReporter reporter)
/* 32:   */     {
/* 33:48 */       this.mapTask = mapTask;
/* 34:49 */       this.jobConf = jobConf;
/* 35:50 */       this.reporter = reporter;
/* 36:   */     }
/* 37:   */     
/* 38:   */     public MapTask getMapTask()
/* 39:   */     {
/* 40:54 */       return this.mapTask;
/* 41:   */     }
/* 42:   */     
/* 43:   */     public JobConf getJobConf()
/* 44:   */     {
/* 45:58 */       return this.jobConf;
/* 46:   */     }
/* 47:   */     
/* 48:   */     public Task.TaskReporter getReporter()
/* 49:   */     {
/* 50:62 */       return this.reporter;
/* 51:   */     }
/* 52:   */   }
/* 53:   */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.MapOutputCollector
 * JD-Core Version:    0.7.0.1
 */