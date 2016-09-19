/*  1:   */ package org.apache.hadoop.mapreduce.lib.input;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*  5:   */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*  6:   */ import org.apache.hadoop.mapreduce.InputSplit;
/*  7:   */ import org.apache.hadoop.mapreduce.RecordReader;
/*  8:   */ import org.apache.hadoop.mapreduce.TaskAttemptContext;
/*  9:   */ 
/* 10:   */ @InterfaceAudience.Public
/* 11:   */ @InterfaceStability.Stable
/* 12:   */ public class CombineSequenceFileInputFormat<K, V>
/* 13:   */   extends CombineFileInputFormat<K, V>
/* 14:   */ {
/* 15:   */   public RecordReader<K, V> createRecordReader(InputSplit split, TaskAttemptContext context)
/* 16:   */     throws IOException
/* 17:   */   {
/* 18:42 */     return new CombineFileRecordReader((CombineFileSplit)split, context, SequenceFileRecordReaderWrapper.class);
/* 19:   */   }
/* 20:   */   
/* 21:   */   private static class SequenceFileRecordReaderWrapper<K, V>
/* 22:   */     extends CombineFileRecordReaderWrapper<K, V>
/* 23:   */   {
/* 24:   */     public SequenceFileRecordReaderWrapper(CombineFileSplit split, TaskAttemptContext context, Integer idx)
/* 25:   */       throws IOException, InterruptedException
/* 26:   */     {
/* 27:61 */       super(split, context, idx);
/* 28:   */     }
/* 29:   */   }
/* 30:   */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.lib.input.CombineSequenceFileInputFormat
 * JD-Core Version:    0.7.0.1
 */