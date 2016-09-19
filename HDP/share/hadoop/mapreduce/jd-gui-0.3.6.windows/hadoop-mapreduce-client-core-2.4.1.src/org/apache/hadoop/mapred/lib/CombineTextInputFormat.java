/*  1:   */ package org.apache.hadoop.mapred.lib;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*  5:   */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*  6:   */ import org.apache.hadoop.conf.Configuration;
/*  7:   */ import org.apache.hadoop.io.LongWritable;
/*  8:   */ import org.apache.hadoop.io.Text;
/*  9:   */ import org.apache.hadoop.mapred.InputSplit;
/* 10:   */ import org.apache.hadoop.mapred.JobConf;
/* 11:   */ import org.apache.hadoop.mapred.RecordReader;
/* 12:   */ import org.apache.hadoop.mapred.Reporter;
/* 13:   */ import org.apache.hadoop.mapred.TextInputFormat;
/* 14:   */ 
/* 15:   */ @InterfaceAudience.Public
/* 16:   */ @InterfaceStability.Stable
/* 17:   */ public class CombineTextInputFormat
/* 18:   */   extends CombineFileInputFormat<LongWritable, Text>
/* 19:   */ {
/* 20:   */   public RecordReader<LongWritable, Text> getRecordReader(InputSplit split, JobConf conf, Reporter reporter)
/* 21:   */     throws IOException
/* 22:   */   {
/* 23:47 */     return new CombineFileRecordReader(conf, (CombineFileSplit)split, reporter, TextRecordReaderWrapper.class);
/* 24:   */   }
/* 25:   */   
/* 26:   */   private static class TextRecordReaderWrapper
/* 27:   */     extends CombineFileRecordReaderWrapper<LongWritable, Text>
/* 28:   */   {
/* 29:   */     public TextRecordReaderWrapper(CombineFileSplit split, Configuration conf, Reporter reporter, Integer idx)
/* 30:   */       throws IOException
/* 31:   */     {
/* 32:65 */       super(split, conf, reporter, idx);
/* 33:   */     }
/* 34:   */   }
/* 35:   */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.lib.CombineTextInputFormat
 * JD-Core Version:    0.7.0.1
 */