/*  1:   */ package org.apache.hadoop.mapreduce.lib.input;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import org.apache.hadoop.classification.InterfaceAudience.Private;
/*  5:   */ import org.apache.hadoop.classification.InterfaceStability.Unstable;
/*  6:   */ import org.apache.hadoop.mapreduce.InputFormat;
/*  7:   */ import org.apache.hadoop.mapreduce.InputSplit;
/*  8:   */ import org.apache.hadoop.mapreduce.RecordReader;
/*  9:   */ import org.apache.hadoop.mapreduce.TaskAttemptContext;
/* 10:   */ import org.apache.hadoop.util.ReflectionUtils;
/* 11:   */ 
/* 12:   */ @InterfaceAudience.Private
/* 13:   */ @InterfaceStability.Unstable
/* 14:   */ public class DelegatingRecordReader<K, V>
/* 15:   */   extends RecordReader<K, V>
/* 16:   */ {
/* 17:   */   RecordReader<K, V> originalRR;
/* 18:   */   
/* 19:   */   public DelegatingRecordReader(InputSplit split, TaskAttemptContext context)
/* 20:   */     throws IOException, InterruptedException
/* 21:   */   {
/* 22:53 */     TaggedInputSplit taggedInputSplit = (TaggedInputSplit)split;
/* 23:54 */     InputFormat<K, V> inputFormat = (InputFormat)ReflectionUtils.newInstance(taggedInputSplit.getInputFormatClass(), context.getConfiguration());
/* 24:   */     
/* 25:   */ 
/* 26:57 */     this.originalRR = inputFormat.createRecordReader(taggedInputSplit.getInputSplit(), context);
/* 27:   */   }
/* 28:   */   
/* 29:   */   public void close()
/* 30:   */     throws IOException
/* 31:   */   {
/* 32:63 */     this.originalRR.close();
/* 33:   */   }
/* 34:   */   
/* 35:   */   public K getCurrentKey()
/* 36:   */     throws IOException, InterruptedException
/* 37:   */   {
/* 38:68 */     return this.originalRR.getCurrentKey();
/* 39:   */   }
/* 40:   */   
/* 41:   */   public V getCurrentValue()
/* 42:   */     throws IOException, InterruptedException
/* 43:   */   {
/* 44:73 */     return this.originalRR.getCurrentValue();
/* 45:   */   }
/* 46:   */   
/* 47:   */   public float getProgress()
/* 48:   */     throws IOException, InterruptedException
/* 49:   */   {
/* 50:78 */     return this.originalRR.getProgress();
/* 51:   */   }
/* 52:   */   
/* 53:   */   public void initialize(InputSplit split, TaskAttemptContext context)
/* 54:   */     throws IOException, InterruptedException
/* 55:   */   {
/* 56:84 */     this.originalRR.initialize(((TaggedInputSplit)split).getInputSplit(), context);
/* 57:   */   }
/* 58:   */   
/* 59:   */   public boolean nextKeyValue()
/* 60:   */     throws IOException, InterruptedException
/* 61:   */   {
/* 62:89 */     return this.originalRR.nextKeyValue();
/* 63:   */   }
/* 64:   */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.lib.input.DelegatingRecordReader
 * JD-Core Version:    0.7.0.1
 */