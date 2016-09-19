/*  1:   */ package org.apache.hadoop.mapred.pipes;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import org.apache.hadoop.conf.Configuration;
/*  5:   */ import org.apache.hadoop.io.FloatWritable;
/*  6:   */ import org.apache.hadoop.io.NullWritable;
/*  7:   */ import org.apache.hadoop.mapred.InputFormat;
/*  8:   */ import org.apache.hadoop.mapred.InputSplit;
/*  9:   */ import org.apache.hadoop.mapred.JobConf;
/* 10:   */ import org.apache.hadoop.mapred.RecordReader;
/* 11:   */ import org.apache.hadoop.mapred.Reporter;
/* 12:   */ import org.apache.hadoop.mapred.TextInputFormat;
/* 13:   */ import org.apache.hadoop.util.ReflectionUtils;
/* 14:   */ 
/* 15:   */ class PipesNonJavaInputFormat
/* 16:   */   implements InputFormat<FloatWritable, NullWritable>
/* 17:   */ {
/* 18:   */   public RecordReader<FloatWritable, NullWritable> getRecordReader(InputSplit genericSplit, JobConf job, Reporter reporter)
/* 19:   */     throws IOException
/* 20:   */   {
/* 21:48 */     return new PipesDummyRecordReader(job, genericSplit);
/* 22:   */   }
/* 23:   */   
/* 24:   */   public InputSplit[] getSplits(JobConf job, int numSplits)
/* 25:   */     throws IOException
/* 26:   */   {
/* 27:53 */     return ((InputFormat)ReflectionUtils.newInstance(job.getClass("mapreduce.pipes.inputformat", TextInputFormat.class, InputFormat.class), job)).getSplits(job, numSplits);
/* 28:   */   }
/* 29:   */   
/* 30:   */   static class PipesDummyRecordReader
/* 31:   */     implements RecordReader<FloatWritable, NullWritable>
/* 32:   */   {
/* 33:70 */     float progress = 0.0F;
/* 34:   */     
/* 35:   */     public PipesDummyRecordReader(Configuration job, InputSplit split)
/* 36:   */       throws IOException
/* 37:   */     {}
/* 38:   */     
/* 39:   */     public FloatWritable createKey()
/* 40:   */     {
/* 41:78 */       return null;
/* 42:   */     }
/* 43:   */     
/* 44:   */     public NullWritable createValue()
/* 45:   */     {
/* 46:82 */       return null;
/* 47:   */     }
/* 48:   */     
/* 49:   */     public synchronized void close()
/* 50:   */       throws IOException
/* 51:   */     {}
/* 52:   */     
/* 53:   */     public synchronized long getPos()
/* 54:   */       throws IOException
/* 55:   */     {
/* 56:88 */       return 0L;
/* 57:   */     }
/* 58:   */     
/* 59:   */     public float getProgress()
/* 60:   */     {
/* 61:92 */       return this.progress;
/* 62:   */     }
/* 63:   */     
/* 64:   */     public synchronized boolean next(FloatWritable key, NullWritable value)
/* 65:   */       throws IOException
/* 66:   */     {
/* 67:97 */       this.progress = key.get();
/* 68:98 */       return true;
/* 69:   */     }
/* 70:   */   }
/* 71:   */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.pipes.PipesNonJavaInputFormat
 * JD-Core Version:    0.7.0.1
 */