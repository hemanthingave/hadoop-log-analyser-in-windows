/*  1:   */ package org.apache.hadoop.mapreduce.lib.input;
/*  2:   */ 
/*  3:   */ import com.google.common.base.Charsets;
/*  4:   */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*  5:   */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*  6:   */ import org.apache.hadoop.conf.Configuration;
/*  7:   */ import org.apache.hadoop.fs.Path;
/*  8:   */ import org.apache.hadoop.io.LongWritable;
/*  9:   */ import org.apache.hadoop.io.Text;
/* 10:   */ import org.apache.hadoop.io.compress.CompressionCodec;
/* 11:   */ import org.apache.hadoop.io.compress.CompressionCodecFactory;
/* 12:   */ import org.apache.hadoop.io.compress.SplittableCompressionCodec;
/* 13:   */ import org.apache.hadoop.mapreduce.InputSplit;
/* 14:   */ import org.apache.hadoop.mapreduce.JobContext;
/* 15:   */ import org.apache.hadoop.mapreduce.RecordReader;
/* 16:   */ import org.apache.hadoop.mapreduce.TaskAttemptContext;
/* 17:   */ 
/* 18:   */ @InterfaceAudience.Public
/* 19:   */ @InterfaceStability.Stable
/* 20:   */ public class TextInputFormat
/* 21:   */   extends FileInputFormat<LongWritable, Text>
/* 22:   */ {
/* 23:   */   public RecordReader<LongWritable, Text> createRecordReader(InputSplit split, TaskAttemptContext context)
/* 24:   */   {
/* 25:48 */     String delimiter = context.getConfiguration().get("textinputformat.record.delimiter");
/* 26:   */     
/* 27:50 */     byte[] recordDelimiterBytes = null;
/* 28:51 */     if (null != delimiter) {
/* 29:52 */       recordDelimiterBytes = delimiter.getBytes(Charsets.UTF_8);
/* 30:   */     }
/* 31:53 */     return new LineRecordReader(recordDelimiterBytes);
/* 32:   */   }
/* 33:   */   
/* 34:   */   protected boolean isSplitable(JobContext context, Path file)
/* 35:   */   {
/* 36:58 */     CompressionCodec codec = new CompressionCodecFactory(context.getConfiguration()).getCodec(file);
/* 37:60 */     if (null == codec) {
/* 38:61 */       return true;
/* 39:   */     }
/* 40:63 */     return codec instanceof SplittableCompressionCodec;
/* 41:   */   }
/* 42:   */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.lib.input.TextInputFormat
 * JD-Core Version:    0.7.0.1
 */