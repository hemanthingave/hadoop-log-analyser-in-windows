/*  1:   */ package org.apache.hadoop.mapred.lib;
/*  2:   */ 
/*  3:   */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*  4:   */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*  5:   */ import org.apache.hadoop.mapred.JobConf;
/*  6:   */ import org.apache.hadoop.mapred.Partitioner;
/*  7:   */ 
/*  8:   */ @InterfaceAudience.Public
/*  9:   */ @InterfaceStability.Stable
/* 10:   */ public class HashPartitioner<K2, V2>
/* 11:   */   implements Partitioner<K2, V2>
/* 12:   */ {
/* 13:   */   public void configure(JobConf job) {}
/* 14:   */   
/* 15:   */   public int getPartition(K2 key, V2 value, int numReduceTasks)
/* 16:   */   {
/* 17:38 */     return (key.hashCode() & 0x7FFFFFFF) % numReduceTasks;
/* 18:   */   }
/* 19:   */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.lib.HashPartitioner
 * JD-Core Version:    0.7.0.1
 */