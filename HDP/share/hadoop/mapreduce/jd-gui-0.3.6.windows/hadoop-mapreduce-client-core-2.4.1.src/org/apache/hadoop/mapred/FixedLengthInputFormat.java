/*  1:   */ package org.apache.hadoop.mapred;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*  5:   */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*  6:   */ import org.apache.hadoop.conf.Configuration;
/*  7:   */ import org.apache.hadoop.fs.FileSystem;
/*  8:   */ import org.apache.hadoop.fs.Path;
/*  9:   */ import org.apache.hadoop.io.BytesWritable;
/* 10:   */ import org.apache.hadoop.io.LongWritable;
/* 11:   */ import org.apache.hadoop.io.compress.CompressionCodec;
/* 12:   */ import org.apache.hadoop.io.compress.CompressionCodecFactory;
/* 13:   */ 
/* 14:   */ @InterfaceAudience.Public
/* 15:   */ @InterfaceStability.Stable
/* 16:   */ public class FixedLengthInputFormat
/* 17:   */   extends FileInputFormat<LongWritable, BytesWritable>
/* 18:   */   implements JobConfigurable
/* 19:   */ {
/* 20:49 */   private CompressionCodecFactory compressionCodecs = null;
/* 21:   */   public static final String FIXED_RECORD_LENGTH = "fixedlengthinputformat.record.length";
/* 22:   */   
/* 23:   */   public static void setRecordLength(Configuration conf, int recordLength)
/* 24:   */   {
/* 25:60 */     conf.setInt("fixedlengthinputformat.record.length", recordLength);
/* 26:   */   }
/* 27:   */   
/* 28:   */   public static int getRecordLength(Configuration conf)
/* 29:   */   {
/* 30:69 */     return conf.getInt("fixedlengthinputformat.record.length", 0);
/* 31:   */   }
/* 32:   */   
/* 33:   */   public void configure(JobConf conf)
/* 34:   */   {
/* 35:74 */     this.compressionCodecs = new CompressionCodecFactory(conf);
/* 36:   */   }
/* 37:   */   
/* 38:   */   public RecordReader<LongWritable, BytesWritable> getRecordReader(InputSplit genericSplit, JobConf job, Reporter reporter)
/* 39:   */     throws IOException
/* 40:   */   {
/* 41:81 */     reporter.setStatus(genericSplit.toString());
/* 42:82 */     int recordLength = getRecordLength(job);
/* 43:83 */     if (recordLength <= 0) {
/* 44:84 */       throw new IOException("Fixed record length " + recordLength + " is invalid.  It should be set to a value greater than zero");
/* 45:   */     }
/* 46:87 */     return new FixedLengthRecordReader(job, (FileSplit)genericSplit, recordLength);
/* 47:   */   }
/* 48:   */   
/* 49:   */   protected boolean isSplitable(FileSystem fs, Path file)
/* 50:   */   {
/* 51:93 */     CompressionCodec codec = this.compressionCodecs.getCodec(file);
/* 52:94 */     return null == codec;
/* 53:   */   }
/* 54:   */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.FixedLengthInputFormat
 * JD-Core Version:    0.7.0.1
 */