/*  1:   */ package org.apache.hadoop.mapred;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*  5:   */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*  6:   */ import org.apache.hadoop.io.Text;
/*  7:   */ 
/*  8:   */ @InterfaceAudience.Public
/*  9:   */ @InterfaceStability.Stable
/* 10:   */ public class SequenceFileAsTextInputFormat
/* 11:   */   extends SequenceFileInputFormat<Text, Text>
/* 12:   */ {
/* 13:   */   public RecordReader<Text, Text> getRecordReader(InputSplit split, JobConf job, Reporter reporter)
/* 14:   */     throws IOException
/* 15:   */   {
/* 16:47 */     reporter.setStatus(split.toString());
/* 17:   */     
/* 18:49 */     return new SequenceFileAsTextRecordReader(job, (FileSplit)split);
/* 19:   */   }
/* 20:   */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.SequenceFileAsTextInputFormat
 * JD-Core Version:    0.7.0.1
 */