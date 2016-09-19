/*  1:   */ package org.apache.hadoop.mapreduce.lib.partition;
/*  2:   */ 
/*  3:   */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*  4:   */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*  5:   */ import org.apache.hadoop.mapreduce.Partitioner;
/*  6:   */ 
/*  7:   */ @InterfaceAudience.Public
/*  8:   */ @InterfaceStability.Stable
/*  9:   */ public class HashPartitioner<K, V>
/* 10:   */   extends Partitioner<K, V>
/* 11:   */ {
/* 12:   */   public int getPartition(K key, V value, int numReduceTasks)
/* 13:   */   {
/* 14:33 */     return (key.hashCode() & 0x7FFFFFFF) % numReduceTasks;
/* 15:   */   }
/* 16:   */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.lib.partition.HashPartitioner
 * JD-Core Version:    0.7.0.1
 */