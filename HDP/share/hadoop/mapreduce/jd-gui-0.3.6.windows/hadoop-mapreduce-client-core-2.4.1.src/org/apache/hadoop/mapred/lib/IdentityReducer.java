/*  1:   */ package org.apache.hadoop.mapred.lib;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import java.util.Iterator;
/*  5:   */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*  6:   */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*  7:   */ import org.apache.hadoop.mapred.MapReduceBase;
/*  8:   */ import org.apache.hadoop.mapred.OutputCollector;
/*  9:   */ import org.apache.hadoop.mapred.Reducer;
/* 10:   */ import org.apache.hadoop.mapred.Reporter;
/* 11:   */ 
/* 12:   */ @InterfaceAudience.Public
/* 13:   */ @InterfaceStability.Stable
/* 14:   */ public class IdentityReducer<K, V>
/* 15:   */   extends MapReduceBase
/* 16:   */   implements Reducer<K, V, K, V>
/* 17:   */ {
/* 18:   */   public void reduce(K key, Iterator<V> values, OutputCollector<K, V> output, Reporter reporter)
/* 19:   */     throws IOException
/* 20:   */   {
/* 21:44 */     while (values.hasNext()) {
/* 22:45 */       output.collect(key, values.next());
/* 23:   */     }
/* 24:   */   }
/* 25:   */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.lib.IdentityReducer
 * JD-Core Version:    0.7.0.1
 */