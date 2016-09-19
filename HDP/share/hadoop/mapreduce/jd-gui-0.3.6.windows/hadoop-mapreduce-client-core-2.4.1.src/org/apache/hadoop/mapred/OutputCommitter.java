/*   1:    */ package org.apache.hadoop.mapred;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*   5:    */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*   6:    */ import org.apache.hadoop.mapreduce.JobStatus.State;
/*   7:    */ 
/*   8:    */ @InterfaceAudience.Public
/*   9:    */ @InterfaceStability.Stable
/*  10:    */ public abstract class OutputCommitter
/*  11:    */   extends org.apache.hadoop.mapreduce.OutputCommitter
/*  12:    */ {
/*  13:    */   public abstract void setupJob(JobContext paramJobContext)
/*  14:    */     throws IOException;
/*  15:    */   
/*  16:    */   @Deprecated
/*  17:    */   public void cleanupJob(JobContext jobContext)
/*  18:    */     throws IOException
/*  19:    */   {}
/*  20:    */   
/*  21:    */   public void commitJob(JobContext jobContext)
/*  22:    */     throws IOException
/*  23:    */   {
/*  24:106 */     cleanupJob(jobContext);
/*  25:    */   }
/*  26:    */   
/*  27:    */   public void abortJob(JobContext jobContext, int status)
/*  28:    */     throws IOException
/*  29:    */   {
/*  30:121 */     cleanupJob(jobContext);
/*  31:    */   }
/*  32:    */   
/*  33:    */   public abstract void setupTask(TaskAttemptContext paramTaskAttemptContext)
/*  34:    */     throws IOException;
/*  35:    */   
/*  36:    */   public abstract boolean needsTaskCommit(TaskAttemptContext paramTaskAttemptContext)
/*  37:    */     throws IOException;
/*  38:    */   
/*  39:    */   public abstract void commitTask(TaskAttemptContext paramTaskAttemptContext)
/*  40:    */     throws IOException;
/*  41:    */   
/*  42:    */   public abstract void abortTask(TaskAttemptContext paramTaskAttemptContext)
/*  43:    */     throws IOException;
/*  44:    */   
/*  45:    */   public boolean isRecoverySupported()
/*  46:    */   {
/*  47:184 */     return false;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public void recoverTask(TaskAttemptContext taskContext)
/*  51:    */     throws IOException
/*  52:    */   {}
/*  53:    */   
/*  54:    */   public final void setupJob(org.apache.hadoop.mapreduce.JobContext jobContext)
/*  55:    */     throws IOException
/*  56:    */   {
/*  57:213 */     setupJob((JobContext)jobContext);
/*  58:    */   }
/*  59:    */   
/*  60:    */   @Deprecated
/*  61:    */   public final void cleanupJob(org.apache.hadoop.mapreduce.JobContext context)
/*  62:    */     throws IOException
/*  63:    */   {
/*  64:228 */     cleanupJob((JobContext)context);
/*  65:    */   }
/*  66:    */   
/*  67:    */   public final void commitJob(org.apache.hadoop.mapreduce.JobContext context)
/*  68:    */     throws IOException
/*  69:    */   {
/*  70:239 */     commitJob((JobContext)context);
/*  71:    */   }
/*  72:    */   
/*  73:    */   public final void abortJob(org.apache.hadoop.mapreduce.JobContext context, JobStatus.State runState)
/*  74:    */     throws IOException
/*  75:    */   {
/*  76:251 */     int state = JobStatus.getOldNewJobRunState(runState);
/*  77:252 */     if ((state != JobStatus.FAILED) && (state != JobStatus.KILLED)) {
/*  78:253 */       throw new IOException("Invalid job run state : " + runState.name());
/*  79:    */     }
/*  80:255 */     abortJob((JobContext)context, state);
/*  81:    */   }
/*  82:    */   
/*  83:    */   public final void setupTask(org.apache.hadoop.mapreduce.TaskAttemptContext taskContext)
/*  84:    */     throws IOException
/*  85:    */   {
/*  86:267 */     setupTask((TaskAttemptContext)taskContext);
/*  87:    */   }
/*  88:    */   
/*  89:    */   public final boolean needsTaskCommit(org.apache.hadoop.mapreduce.TaskAttemptContext taskContext)
/*  90:    */     throws IOException
/*  91:    */   {
/*  92:279 */     return needsTaskCommit((TaskAttemptContext)taskContext);
/*  93:    */   }
/*  94:    */   
/*  95:    */   public final void commitTask(org.apache.hadoop.mapreduce.TaskAttemptContext taskContext)
/*  96:    */     throws IOException
/*  97:    */   {
/*  98:291 */     commitTask((TaskAttemptContext)taskContext);
/*  99:    */   }
/* 100:    */   
/* 101:    */   public final void abortTask(org.apache.hadoop.mapreduce.TaskAttemptContext taskContext)
/* 102:    */     throws IOException
/* 103:    */   {
/* 104:303 */     abortTask((TaskAttemptContext)taskContext);
/* 105:    */   }
/* 106:    */   
/* 107:    */   public final void recoverTask(org.apache.hadoop.mapreduce.TaskAttemptContext taskContext)
/* 108:    */     throws IOException
/* 109:    */   {
/* 110:315 */     recoverTask((TaskAttemptContext)taskContext);
/* 111:    */   }
/* 112:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.OutputCommitter
 * JD-Core Version:    0.7.0.1
 */