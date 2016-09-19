/*  1:   */ package org.apache.hadoop.mapred.lib;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*  5:   */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*  6:   */ import org.apache.hadoop.fs.Path;
/*  7:   */ import org.apache.hadoop.mapred.InputSplit;
/*  8:   */ import org.apache.hadoop.mapred.JobConf;
/*  9:   */ 
/* 10:   */ @InterfaceAudience.Public
/* 11:   */ @InterfaceStability.Stable
/* 12:   */ public class CombineFileSplit
/* 13:   */   extends org.apache.hadoop.mapreduce.lib.input.CombineFileSplit
/* 14:   */   implements InputSplit
/* 15:   */ {
/* 16:   */   private JobConf job;
/* 17:   */   
/* 18:   */   public CombineFileSplit() {}
/* 19:   */   
/* 20:   */   public CombineFileSplit(JobConf job, Path[] files, long[] start, long[] lengths, String[] locations)
/* 21:   */   {
/* 22:42 */     super(files, start, lengths, locations);
/* 23:43 */     this.job = job;
/* 24:   */   }
/* 25:   */   
/* 26:   */   public CombineFileSplit(JobConf job, Path[] files, long[] lengths)
/* 27:   */   {
/* 28:47 */     super(files, lengths);
/* 29:48 */     this.job = job;
/* 30:   */   }
/* 31:   */   
/* 32:   */   public CombineFileSplit(CombineFileSplit old)
/* 33:   */     throws IOException
/* 34:   */   {
/* 35:55 */     super(old);
/* 36:   */   }
/* 37:   */   
/* 38:   */   public JobConf getJob()
/* 39:   */   {
/* 40:59 */     return this.job;
/* 41:   */   }
/* 42:   */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.lib.CombineFileSplit
 * JD-Core Version:    0.7.0.1
 */