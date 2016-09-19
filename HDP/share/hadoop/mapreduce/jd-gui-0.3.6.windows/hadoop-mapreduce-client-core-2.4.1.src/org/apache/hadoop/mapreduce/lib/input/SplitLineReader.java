/*  1:   */ package org.apache.hadoop.mapreduce.lib.input;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import java.io.InputStream;
/*  5:   */ import org.apache.hadoop.classification.InterfaceAudience.Private;
/*  6:   */ import org.apache.hadoop.classification.InterfaceStability.Unstable;
/*  7:   */ import org.apache.hadoop.conf.Configuration;
/*  8:   */ import org.apache.hadoop.util.LineReader;
/*  9:   */ 
/* 10:   */ @InterfaceAudience.Private
/* 11:   */ @InterfaceStability.Unstable
/* 12:   */ public class SplitLineReader
/* 13:   */   extends LineReader
/* 14:   */ {
/* 15:   */   public SplitLineReader(InputStream in, byte[] recordDelimiterBytes)
/* 16:   */   {
/* 17:32 */     super(in, recordDelimiterBytes);
/* 18:   */   }
/* 19:   */   
/* 20:   */   public SplitLineReader(InputStream in, Configuration conf, byte[] recordDelimiterBytes)
/* 21:   */     throws IOException
/* 22:   */   {
/* 23:37 */     super(in, conf, recordDelimiterBytes);
/* 24:   */   }
/* 25:   */   
/* 26:   */   public boolean needAdditionalRecordAfterSplit()
/* 27:   */   {
/* 28:41 */     return false;
/* 29:   */   }
/* 30:   */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.lib.input.SplitLineReader
 * JD-Core Version:    0.7.0.1
 */