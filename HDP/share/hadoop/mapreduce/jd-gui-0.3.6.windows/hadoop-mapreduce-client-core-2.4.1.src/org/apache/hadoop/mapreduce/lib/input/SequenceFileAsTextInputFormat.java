/*  1:   */ package org.apache.hadoop.mapreduce.lib.input;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*  5:   */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*  6:   */ import org.apache.hadoop.io.Text;
/*  7:   */ import org.apache.hadoop.mapreduce.InputSplit;
/*  8:   */ import org.apache.hadoop.mapreduce.RecordReader;
/*  9:   */ import org.apache.hadoop.mapreduce.TaskAttemptContext;
/* 10:   */ 
/* 11:   */ @InterfaceAudience.Public
/* 12:   */ @InterfaceStability.Stable
/* 13:   */ public class SequenceFileAsTextInputFormat
/* 14:   */   extends SequenceFileInputFormat<Text, Text>
/* 15:   */ {
/* 16:   */   public RecordReader<Text, Text> createRecordReader(InputSplit split, TaskAttemptContext context)
/* 17:   */     throws IOException
/* 18:   */   {
/* 19:46 */     context.setStatus(split.toString());
/* 20:47 */     return new SequenceFileAsTextRecordReader();
/* 21:   */   }
/* 22:   */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.lib.input.SequenceFileAsTextInputFormat
 * JD-Core Version:    0.7.0.1
 */