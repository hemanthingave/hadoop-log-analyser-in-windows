/*  1:   */ package org.apache.hadoop.mapred.lib;
/*  2:   */ 
/*  3:   */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*  4:   */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*  5:   */ import org.apache.hadoop.io.BinaryComparable;
/*  6:   */ import org.apache.hadoop.mapred.JobConf;
/*  7:   */ import org.apache.hadoop.mapred.Partitioner;
/*  8:   */ 
/*  9:   */ @InterfaceAudience.Public
/* 10:   */ @InterfaceStability.Stable
/* 11:   */ public class BinaryPartitioner<V>
/* 12:   */   extends org.apache.hadoop.mapreduce.lib.partition.BinaryPartitioner<V>
/* 13:   */   implements Partitioner<BinaryComparable, V>
/* 14:   */ {
/* 15:   */   public void configure(JobConf job)
/* 16:   */   {
/* 17:40 */     super.setConf(job);
/* 18:   */   }
/* 19:   */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.lib.BinaryPartitioner
 * JD-Core Version:    0.7.0.1
 */