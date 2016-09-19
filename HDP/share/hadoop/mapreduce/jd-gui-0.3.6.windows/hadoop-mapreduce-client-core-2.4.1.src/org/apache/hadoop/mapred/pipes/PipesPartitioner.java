/*  1:   */ package org.apache.hadoop.mapred.pipes;
/*  2:   */ 
/*  3:   */ import org.apache.hadoop.io.Writable;
/*  4:   */ import org.apache.hadoop.io.WritableComparable;
/*  5:   */ import org.apache.hadoop.mapred.JobConf;
/*  6:   */ import org.apache.hadoop.mapred.Partitioner;
/*  7:   */ import org.apache.hadoop.util.ReflectionUtils;
/*  8:   */ 
/*  9:   */ class PipesPartitioner<K extends WritableComparable, V extends Writable>
/* 10:   */   implements Partitioner<K, V>
/* 11:   */ {
/* 12:35 */   private static ThreadLocal<Integer> cache = new ThreadLocal();
/* 13:36 */   private Partitioner<K, V> part = null;
/* 14:   */   
/* 15:   */   public void configure(JobConf conf)
/* 16:   */   {
/* 17:40 */     this.part = ((Partitioner)ReflectionUtils.newInstance(Submitter.getJavaPartitioner(conf), conf));
/* 18:   */   }
/* 19:   */   
/* 20:   */   static void setNextPartition(int newValue)
/* 21:   */   {
/* 22:49 */     cache.set(Integer.valueOf(newValue));
/* 23:   */   }
/* 24:   */   
/* 25:   */   public int getPartition(K key, V value, int numPartitions)
/* 26:   */   {
/* 27:61 */     Integer result = (Integer)cache.get();
/* 28:62 */     if (result == null) {
/* 29:63 */       return this.part.getPartition(key, value, numPartitions);
/* 30:   */     }
/* 31:65 */     return result.intValue();
/* 32:   */   }
/* 33:   */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.pipes.PipesPartitioner
 * JD-Core Version:    0.7.0.1
 */