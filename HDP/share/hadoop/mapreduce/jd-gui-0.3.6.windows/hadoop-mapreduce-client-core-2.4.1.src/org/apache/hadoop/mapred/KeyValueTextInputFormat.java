/*  1:   */ package org.apache.hadoop.mapred;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*  5:   */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*  6:   */ import org.apache.hadoop.fs.FileSystem;
/*  7:   */ import org.apache.hadoop.fs.Path;
/*  8:   */ import org.apache.hadoop.io.Text;
/*  9:   */ import org.apache.hadoop.io.compress.CompressionCodec;
/* 10:   */ import org.apache.hadoop.io.compress.CompressionCodecFactory;
/* 11:   */ import org.apache.hadoop.io.compress.SplittableCompressionCodec;
/* 12:   */ 
/* 13:   */ @InterfaceAudience.Public
/* 14:   */ @InterfaceStability.Stable
/* 15:   */ public class KeyValueTextInputFormat
/* 16:   */   extends FileInputFormat<Text, Text>
/* 17:   */   implements JobConfigurable
/* 18:   */ {
/* 19:43 */   private CompressionCodecFactory compressionCodecs = null;
/* 20:   */   
/* 21:   */   public void configure(JobConf conf)
/* 22:   */   {
/* 23:46 */     this.compressionCodecs = new CompressionCodecFactory(conf);
/* 24:   */   }
/* 25:   */   
/* 26:   */   protected boolean isSplitable(FileSystem fs, Path file)
/* 27:   */   {
/* 28:50 */     CompressionCodec codec = this.compressionCodecs.getCodec(file);
/* 29:51 */     if (null == codec) {
/* 30:52 */       return true;
/* 31:   */     }
/* 32:54 */     return codec instanceof SplittableCompressionCodec;
/* 33:   */   }
/* 34:   */   
/* 35:   */   public RecordReader<Text, Text> getRecordReader(InputSplit genericSplit, JobConf job, Reporter reporter)
/* 36:   */     throws IOException
/* 37:   */   {
/* 38:62 */     reporter.setStatus(genericSplit.toString());
/* 39:63 */     return new KeyValueLineRecordReader(job, (FileSplit)genericSplit);
/* 40:   */   }
/* 41:   */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.KeyValueTextInputFormat
 * JD-Core Version:    0.7.0.1
 */