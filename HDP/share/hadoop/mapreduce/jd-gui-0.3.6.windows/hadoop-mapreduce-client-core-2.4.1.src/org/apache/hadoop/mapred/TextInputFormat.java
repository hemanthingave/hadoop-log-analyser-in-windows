/*  1:   */ package org.apache.hadoop.mapred;
/*  2:   */ 
/*  3:   */ import com.google.common.base.Charsets;
/*  4:   */ import java.io.IOException;
/*  5:   */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*  6:   */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*  7:   */ import org.apache.hadoop.fs.FileSystem;
/*  8:   */ import org.apache.hadoop.fs.Path;
/*  9:   */ import org.apache.hadoop.io.LongWritable;
/* 10:   */ import org.apache.hadoop.io.Text;
/* 11:   */ import org.apache.hadoop.io.compress.CompressionCodec;
/* 12:   */ import org.apache.hadoop.io.compress.CompressionCodecFactory;
/* 13:   */ import org.apache.hadoop.io.compress.SplittableCompressionCodec;
/* 14:   */ 
/* 15:   */ @InterfaceAudience.Public
/* 16:   */ @InterfaceStability.Stable
/* 17:   */ public class TextInputFormat
/* 18:   */   extends FileInputFormat<LongWritable, Text>
/* 19:   */   implements JobConfigurable
/* 20:   */ {
/* 21:42 */   private CompressionCodecFactory compressionCodecs = null;
/* 22:   */   
/* 23:   */   public void configure(JobConf conf)
/* 24:   */   {
/* 25:45 */     this.compressionCodecs = new CompressionCodecFactory(conf);
/* 26:   */   }
/* 27:   */   
/* 28:   */   protected boolean isSplitable(FileSystem fs, Path file)
/* 29:   */   {
/* 30:49 */     CompressionCodec codec = this.compressionCodecs.getCodec(file);
/* 31:50 */     if (null == codec) {
/* 32:51 */       return true;
/* 33:   */     }
/* 34:53 */     return codec instanceof SplittableCompressionCodec;
/* 35:   */   }
/* 36:   */   
/* 37:   */   public RecordReader<LongWritable, Text> getRecordReader(InputSplit genericSplit, JobConf job, Reporter reporter)
/* 38:   */     throws IOException
/* 39:   */   {
/* 40:61 */     reporter.setStatus(genericSplit.toString());
/* 41:62 */     String delimiter = job.get("textinputformat.record.delimiter");
/* 42:63 */     byte[] recordDelimiterBytes = null;
/* 43:64 */     if (null != delimiter) {
/* 44:65 */       recordDelimiterBytes = delimiter.getBytes(Charsets.UTF_8);
/* 45:   */     }
/* 46:67 */     return new LineRecordReader(job, (FileSplit)genericSplit, recordDelimiterBytes);
/* 47:   */   }
/* 48:   */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.TextInputFormat
 * JD-Core Version:    0.7.0.1
 */