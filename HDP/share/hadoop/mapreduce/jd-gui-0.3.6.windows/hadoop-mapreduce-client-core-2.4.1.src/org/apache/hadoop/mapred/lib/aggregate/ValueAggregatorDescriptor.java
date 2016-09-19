/*  1:   */ package org.apache.hadoop.mapred.lib.aggregate;
/*  2:   */ 
/*  3:   */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*  4:   */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*  5:   */ import org.apache.hadoop.io.Text;
/*  6:   */ import org.apache.hadoop.mapred.JobConf;
/*  7:   */ 
/*  8:   */ @InterfaceAudience.Public
/*  9:   */ @InterfaceStability.Stable
/* 10:   */ public abstract interface ValueAggregatorDescriptor
/* 11:   */   extends org.apache.hadoop.mapreduce.lib.aggregate.ValueAggregatorDescriptor
/* 12:   */ {
/* 13:   */   public static final String TYPE_SEPARATOR = ":";
/* 14:45 */   public static final Text ONE = org.apache.hadoop.mapreduce.lib.aggregate.ValueAggregatorDescriptor.ONE;
/* 15:   */   
/* 16:   */   public abstract void configure(JobConf paramJobConf);
/* 17:   */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.lib.aggregate.ValueAggregatorDescriptor
 * JD-Core Version:    0.7.0.1
 */