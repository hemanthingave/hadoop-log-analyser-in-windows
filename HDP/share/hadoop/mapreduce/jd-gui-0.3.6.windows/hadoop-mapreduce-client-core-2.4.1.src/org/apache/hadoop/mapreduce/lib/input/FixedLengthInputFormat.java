/*  1:   */ package org.apache.hadoop.mapreduce.lib.input;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*  5:   */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*  6:   */ import org.apache.hadoop.conf.Configuration;
/*  7:   */ import org.apache.hadoop.fs.Path;
/*  8:   */ import org.apache.hadoop.io.BytesWritable;
/*  9:   */ import org.apache.hadoop.io.LongWritable;
/* 10:   */ import org.apache.hadoop.io.compress.CompressionCodec;
/* 11:   */ import org.apache.hadoop.io.compress.CompressionCodecFactory;
/* 12:   */ import org.apache.hadoop.mapreduce.InputSplit;
/* 13:   */ import org.apache.hadoop.mapreduce.JobContext;
/* 14:   */ import org.apache.hadoop.mapreduce.RecordReader;
/* 15:   */ import org.apache.hadoop.mapreduce.TaskAttemptContext;
/* 16:   */ 
/* 17:   */ @InterfaceAudience.Public
/* 18:   */ @InterfaceStability.Stable
/* 19:   */ public class FixedLengthInputFormat
/* 20:   */   extends FileInputFormat<LongWritable, BytesWritable>
/* 21:   */ {
/* 22:   */   public static final String FIXED_RECORD_LENGTH = "fixedlengthinputformat.record.length";
/* 23:   */   
/* 24:   */   public static void setRecordLength(Configuration conf, int recordLength)
/* 25:   */   {
/* 26:59 */     conf.setInt("fixedlengthinputformat.record.length", recordLength);
/* 27:   */   }
/* 28:   */   
/* 29:   */   public static int getRecordLength(Configuration conf)
/* 30:   */   {
/* 31:68 */     return conf.getInt("fixedlengthinputformat.record.length", 0);
/* 32:   */   }
/* 33:   */   
/* 34:   */   public RecordReader<LongWritable, BytesWritable> createRecordReader(InputSplit split, TaskAttemptContext context)
/* 35:   */     throws IOException, InterruptedException
/* 36:   */   {
/* 37:75 */     int recordLength = getRecordLength(context.getConfiguration());
/* 38:76 */     if (recordLength <= 0) {
/* 39:77 */       throw new IOException("Fixed record length " + recordLength + " is invalid.  It should be set to a value greater than zero");
/* 40:   */     }
/* 41:80 */     return new FixedLengthRecordReader(recordLength);
/* 42:   */   }
/* 43:   */   
/* 44:   */   protected boolean isSplitable(JobContext context, Path file)
/* 45:   */   {
/* 46:85 */     CompressionCodec codec = new CompressionCodecFactory(context.getConfiguration()).getCodec(file);
/* 47:   */     
/* 48:87 */     return null == codec;
/* 49:   */   }
/* 50:   */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.lib.input.FixedLengthInputFormat
 * JD-Core Version:    0.7.0.1
 */