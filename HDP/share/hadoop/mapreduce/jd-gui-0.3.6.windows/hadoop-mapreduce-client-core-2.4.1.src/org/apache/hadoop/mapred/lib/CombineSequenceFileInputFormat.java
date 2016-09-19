/*  1:   */ package org.apache.hadoop.mapred.lib;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*  5:   */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*  6:   */ import org.apache.hadoop.conf.Configuration;
/*  7:   */ import org.apache.hadoop.mapred.InputSplit;
/*  8:   */ import org.apache.hadoop.mapred.JobConf;
/*  9:   */ import org.apache.hadoop.mapred.RecordReader;
/* 10:   */ import org.apache.hadoop.mapred.Reporter;
/* 11:   */ import org.apache.hadoop.mapred.SequenceFileInputFormat;
/* 12:   */ 
/* 13:   */ @InterfaceAudience.Public
/* 14:   */ @InterfaceStability.Stable
/* 15:   */ public class CombineSequenceFileInputFormat<K, V>
/* 16:   */   extends CombineFileInputFormat<K, V>
/* 17:   */ {
/* 18:   */   public RecordReader<K, V> getRecordReader(InputSplit split, JobConf conf, Reporter reporter)
/* 19:   */     throws IOException
/* 20:   */   {
/* 21:45 */     return new CombineFileRecordReader(conf, (CombineFileSplit)split, reporter, SequenceFileRecordReaderWrapper.class);
/* 22:   */   }
/* 23:   */   
/* 24:   */   private static class SequenceFileRecordReaderWrapper<K, V>
/* 25:   */     extends CombineFileRecordReaderWrapper<K, V>
/* 26:   */   {
/* 27:   */     public SequenceFileRecordReaderWrapper(CombineFileSplit split, Configuration conf, Reporter reporter, Integer idx)
/* 28:   */       throws IOException
/* 29:   */     {
/* 30:63 */       super(split, conf, reporter, idx);
/* 31:   */     }
/* 32:   */   }
/* 33:   */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.lib.CombineSequenceFileInputFormat
 * JD-Core Version:    0.7.0.1
 */