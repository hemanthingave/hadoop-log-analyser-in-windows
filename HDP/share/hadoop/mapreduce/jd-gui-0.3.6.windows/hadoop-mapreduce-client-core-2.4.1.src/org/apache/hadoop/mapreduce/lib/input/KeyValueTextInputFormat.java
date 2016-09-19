/*  1:   */ package org.apache.hadoop.mapreduce.lib.input;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*  5:   */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*  6:   */ import org.apache.hadoop.fs.Path;
/*  7:   */ import org.apache.hadoop.io.Text;
/*  8:   */ import org.apache.hadoop.io.compress.CompressionCodec;
/*  9:   */ import org.apache.hadoop.io.compress.CompressionCodecFactory;
/* 10:   */ import org.apache.hadoop.io.compress.SplittableCompressionCodec;
/* 11:   */ import org.apache.hadoop.mapreduce.InputSplit;
/* 12:   */ import org.apache.hadoop.mapreduce.JobContext;
/* 13:   */ import org.apache.hadoop.mapreduce.RecordReader;
/* 14:   */ import org.apache.hadoop.mapreduce.TaskAttemptContext;
/* 15:   */ 
/* 16:   */ @InterfaceAudience.Public
/* 17:   */ @InterfaceStability.Stable
/* 18:   */ public class KeyValueTextInputFormat
/* 19:   */   extends FileInputFormat<Text, Text>
/* 20:   */ {
/* 21:   */   protected boolean isSplitable(JobContext context, Path file)
/* 22:   */   {
/* 23:48 */     CompressionCodec codec = new CompressionCodecFactory(context.getConfiguration()).getCodec(file);
/* 24:50 */     if (null == codec) {
/* 25:51 */       return true;
/* 26:   */     }
/* 27:53 */     return codec instanceof SplittableCompressionCodec;
/* 28:   */   }
/* 29:   */   
/* 30:   */   public RecordReader<Text, Text> createRecordReader(InputSplit genericSplit, TaskAttemptContext context)
/* 31:   */     throws IOException
/* 32:   */   {
/* 33:59 */     context.setStatus(genericSplit.toString());
/* 34:60 */     return new KeyValueLineRecordReader(context.getConfiguration());
/* 35:   */   }
/* 36:   */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.lib.input.KeyValueTextInputFormat
 * JD-Core Version:    0.7.0.1
 */