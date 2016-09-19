/*  1:   */ package org.apache.hadoop.mapred.lib.aggregate;
/*  2:   */ 
/*  3:   */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*  4:   */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*  5:   */ import org.apache.hadoop.mapred.JobConf;
/*  6:   */ 
/*  7:   */ @InterfaceAudience.Public
/*  8:   */ @InterfaceStability.Stable
/*  9:   */ public class UserDefinedValueAggregatorDescriptor
/* 10:   */   extends org.apache.hadoop.mapreduce.lib.aggregate.UserDefinedValueAggregatorDescriptor
/* 11:   */   implements ValueAggregatorDescriptor
/* 12:   */ {
/* 13:   */   public static Object createInstance(String className)
/* 14:   */   {
/* 15:45 */     return org.apache.hadoop.mapreduce.lib.aggregate.UserDefinedValueAggregatorDescriptor.createInstance(className);
/* 16:   */   }
/* 17:   */   
/* 18:   */   public UserDefinedValueAggregatorDescriptor(String className, JobConf job)
/* 19:   */   {
/* 20:55 */     super(className, job);
/* 21:56 */     ((ValueAggregatorDescriptor)this.theAggregatorDescriptor).configure(job);
/* 22:   */   }
/* 23:   */   
/* 24:   */   public void configure(JobConf job) {}
/* 25:   */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.lib.aggregate.UserDefinedValueAggregatorDescriptor
 * JD-Core Version:    0.7.0.1
 */