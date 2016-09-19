/*  1:   */ package org.apache.hadoop.mapred.lib;
/*  2:   */ 
/*  3:   */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*  4:   */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*  5:   */ import org.apache.hadoop.mapred.JobConf;
/*  6:   */ import org.apache.hadoop.mapred.Partitioner;
/*  7:   */ 
/*  8:   */ @InterfaceAudience.Public
/*  9:   */ @InterfaceStability.Stable
/* 10:   */ public class KeyFieldBasedPartitioner<K2, V2>
/* 11:   */   extends org.apache.hadoop.mapreduce.lib.partition.KeyFieldBasedPartitioner<K2, V2>
/* 12:   */   implements Partitioner<K2, V2>
/* 13:   */ {
/* 14:   */   public void configure(JobConf job)
/* 15:   */   {
/* 16:45 */     super.setConf(job);
/* 17:   */   }
/* 18:   */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.lib.KeyFieldBasedPartitioner
 * JD-Core Version:    0.7.0.1
 */