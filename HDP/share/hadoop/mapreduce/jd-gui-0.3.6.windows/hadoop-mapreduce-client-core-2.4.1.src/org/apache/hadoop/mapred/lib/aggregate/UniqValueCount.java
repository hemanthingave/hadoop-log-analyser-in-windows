/*  1:   */ package org.apache.hadoop.mapred.lib.aggregate;
/*  2:   */ 
/*  3:   */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*  4:   */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*  5:   */ 
/*  6:   */ @InterfaceAudience.Public
/*  7:   */ @InterfaceStability.Stable
/*  8:   */ public class UniqValueCount
/*  9:   */   extends org.apache.hadoop.mapreduce.lib.aggregate.UniqValueCount
/* 10:   */   implements ValueAggregator<Object>
/* 11:   */ {
/* 12:   */   public UniqValueCount() {}
/* 13:   */   
/* 14:   */   public UniqValueCount(long maxNum)
/* 15:   */   {
/* 16:46 */     super(maxNum);
/* 17:   */   }
/* 18:   */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.lib.aggregate.UniqValueCount
 * JD-Core Version:    0.7.0.1
 */