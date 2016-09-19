/*   1:    */ package org.apache.hadoop.mapreduce;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*   5:    */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*   6:    */ 
/*   7:    */ @InterfaceAudience.Public
/*   8:    */ @InterfaceStability.Stable
/*   9:    */ public abstract class OutputCommitter
/*  10:    */ {
/*  11:    */   public abstract void setupJob(JobContext paramJobContext)
/*  12:    */     throws IOException;
/*  13:    */   
/*  14:    */   @Deprecated
/*  15:    */   public void cleanupJob(JobContext jobContext)
/*  16:    */     throws IOException
/*  17:    */   {}
/*  18:    */   
/*  19:    */   public void commitJob(JobContext jobContext)
/*  20:    */     throws IOException
/*  21:    */   {
/*  22:104 */     cleanupJob(jobContext);
/*  23:    */   }
/*  24:    */   
/*  25:    */   public void abortJob(JobContext jobContext, JobStatus.State state)
/*  26:    */     throws IOException
/*  27:    */   {
/*  28:120 */     cleanupJob(jobContext);
/*  29:    */   }
/*  30:    */   
/*  31:    */   public abstract void setupTask(TaskAttemptContext paramTaskAttemptContext)
/*  32:    */     throws IOException;
/*  33:    */   
/*  34:    */   public abstract boolean needsTaskCommit(TaskAttemptContext paramTaskAttemptContext)
/*  35:    */     throws IOException;
/*  36:    */   
/*  37:    */   public abstract void commitTask(TaskAttemptContext paramTaskAttemptContext)
/*  38:    */     throws IOException;
/*  39:    */   
/*  40:    */   public abstract void abortTask(TaskAttemptContext paramTaskAttemptContext)
/*  41:    */     throws IOException;
/*  42:    */   
/*  43:    */   public boolean isRecoverySupported()
/*  44:    */   {
/*  45:187 */     return false;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public void recoverTask(TaskAttemptContext taskContext)
/*  49:    */     throws IOException
/*  50:    */   {}
/*  51:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.OutputCommitter
 * JD-Core Version:    0.7.0.1
 */