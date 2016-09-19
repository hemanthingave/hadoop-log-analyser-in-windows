/*  1:   */ package org.apache.hadoop.mapred.lib;
/*  2:   */ 
/*  3:   */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*  4:   */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*  5:   */ import org.apache.hadoop.fs.Path;
/*  6:   */ import org.apache.hadoop.io.WritableComparable;
/*  7:   */ import org.apache.hadoop.mapred.JobConf;
/*  8:   */ import org.apache.hadoop.mapred.Partitioner;
/*  9:   */ 
/* 10:   */ @InterfaceAudience.Public
/* 11:   */ @InterfaceStability.Stable
/* 12:   */ public class TotalOrderPartitioner<K extends WritableComparable<?>, V>
/* 13:   */   extends org.apache.hadoop.mapreduce.lib.partition.TotalOrderPartitioner<K, V>
/* 14:   */   implements Partitioner<K, V>
/* 15:   */ {
/* 16:   */   public void configure(JobConf job)
/* 17:   */   {
/* 18:42 */     super.setConf(job);
/* 19:   */   }
/* 20:   */   
/* 21:   */   @Deprecated
/* 22:   */   public static void setPartitionFile(JobConf job, Path p)
/* 23:   */   {
/* 24:55 */     org.apache.hadoop.mapreduce.lib.partition.TotalOrderPartitioner.setPartitionFile(job, p);
/* 25:   */   }
/* 26:   */   
/* 27:   */   @Deprecated
/* 28:   */   public static String getPartitionFile(JobConf job)
/* 29:   */   {
/* 30:68 */     return org.apache.hadoop.mapreduce.lib.partition.TotalOrderPartitioner.getPartitionFile(job);
/* 31:   */   }
/* 32:   */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.lib.TotalOrderPartitioner
 * JD-Core Version:    0.7.0.1
 */