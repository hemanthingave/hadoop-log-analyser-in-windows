/*  1:   */ package org.apache.hadoop.mapred.lib;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*  5:   */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*  6:   */ import org.apache.hadoop.mapred.MapReduceBase;
/*  7:   */ import org.apache.hadoop.mapred.Mapper;
/*  8:   */ import org.apache.hadoop.mapred.OutputCollector;
/*  9:   */ import org.apache.hadoop.mapred.Reporter;
/* 10:   */ 
/* 11:   */ @InterfaceAudience.Public
/* 12:   */ @InterfaceStability.Stable
/* 13:   */ public class IdentityMapper<K, V>
/* 14:   */   extends MapReduceBase
/* 15:   */   implements Mapper<K, V, K, V>
/* 16:   */ {
/* 17:   */   public void map(K key, V val, OutputCollector<K, V> output, Reporter reporter)
/* 18:   */     throws IOException
/* 19:   */   {
/* 20:43 */     output.collect(key, val);
/* 21:   */   }
/* 22:   */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.lib.IdentityMapper
 * JD-Core Version:    0.7.0.1
 */