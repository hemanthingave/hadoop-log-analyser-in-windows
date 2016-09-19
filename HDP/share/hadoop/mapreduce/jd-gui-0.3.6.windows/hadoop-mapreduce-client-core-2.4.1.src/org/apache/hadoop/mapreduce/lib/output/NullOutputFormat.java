/*  1:   */ package org.apache.hadoop.mapreduce.lib.output;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*  5:   */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*  6:   */ import org.apache.hadoop.mapreduce.JobContext;
/*  7:   */ import org.apache.hadoop.mapreduce.OutputCommitter;
/*  8:   */ import org.apache.hadoop.mapreduce.OutputFormat;
/*  9:   */ import org.apache.hadoop.mapreduce.RecordWriter;
/* 10:   */ import org.apache.hadoop.mapreduce.TaskAttemptContext;
/* 11:   */ 
/* 12:   */ @InterfaceAudience.Public
/* 13:   */ @InterfaceStability.Stable
/* 14:   */ public class NullOutputFormat<K, V>
/* 15:   */   extends OutputFormat<K, V>
/* 16:   */ {
/* 17:   */   public RecordWriter<K, V> getRecordWriter(TaskAttemptContext context)
/* 18:   */   {
/* 19:41 */     new RecordWriter()
/* 20:   */     {
/* 21:   */       public void write(K key, V value) {}
/* 22:   */       
/* 23:   */       public void close(TaskAttemptContext context) {}
/* 24:   */     };
/* 25:   */   }
/* 26:   */   
/* 27:   */   public void checkOutputSpecs(JobContext context) {}
/* 28:   */   
/* 29:   */   public OutputCommitter getOutputCommitter(TaskAttemptContext context)
/* 30:   */   {
/* 31:52 */     new OutputCommitter()
/* 32:   */     {
/* 33:   */       public void abortTask(TaskAttemptContext taskContext) {}
/* 34:   */       
/* 35:   */       public void cleanupJob(JobContext jobContext) {}
/* 36:   */       
/* 37:   */       public void commitTask(TaskAttemptContext taskContext) {}
/* 38:   */       
/* 39:   */       public boolean needsTaskCommit(TaskAttemptContext taskContext)
/* 40:   */       {
/* 41:57 */         return false;
/* 42:   */       }
/* 43:   */       
/* 44:   */       public void setupJob(JobContext jobContext) {}
/* 45:   */       
/* 46:   */       public void setupTask(TaskAttemptContext taskContext) {}
/* 47:   */       
/* 48:   */       public boolean isRecoverySupported()
/* 49:   */       {
/* 50:64 */         return true;
/* 51:   */       }
/* 52:   */       
/* 53:   */       public void recoverTask(TaskAttemptContext taskContext)
/* 54:   */         throws IOException
/* 55:   */       {}
/* 56:   */     };
/* 57:   */   }
/* 58:   */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.lib.output.NullOutputFormat
 * JD-Core Version:    0.7.0.1
 */