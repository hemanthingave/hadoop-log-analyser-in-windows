/*  1:   */ package org.apache.hadoop.mapreduce.lib.input;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*  5:   */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*  6:   */ import org.apache.hadoop.io.LongWritable;
/*  7:   */ import org.apache.hadoop.io.Text;
/*  8:   */ import org.apache.hadoop.mapreduce.InputSplit;
/*  9:   */ import org.apache.hadoop.mapreduce.RecordReader;
/* 10:   */ import org.apache.hadoop.mapreduce.TaskAttemptContext;
/* 11:   */ 
/* 12:   */ @InterfaceAudience.Public
/* 13:   */ @InterfaceStability.Stable
/* 14:   */ public class CombineTextInputFormat
/* 15:   */   extends CombineFileInputFormat<LongWritable, Text>
/* 16:   */ {
/* 17:   */   public RecordReader<LongWritable, Text> createRecordReader(InputSplit split, TaskAttemptContext context)
/* 18:   */     throws IOException
/* 19:   */   {
/* 20:43 */     return new CombineFileRecordReader((CombineFileSplit)split, context, TextRecordReaderWrapper.class);
/* 21:   */   }
/* 22:   */   
/* 23:   */   private static class TextRecordReaderWrapper
/* 24:   */     extends CombineFileRecordReaderWrapper<LongWritable, Text>
/* 25:   */   {
/* 26:   */     public TextRecordReaderWrapper(CombineFileSplit split, TaskAttemptContext context, Integer idx)
/* 27:   */       throws IOException, InterruptedException
/* 28:   */     {
/* 29:62 */       super(split, context, idx);
/* 30:   */     }
/* 31:   */   }
/* 32:   */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.lib.input.CombineTextInputFormat
 * JD-Core Version:    0.7.0.1
 */