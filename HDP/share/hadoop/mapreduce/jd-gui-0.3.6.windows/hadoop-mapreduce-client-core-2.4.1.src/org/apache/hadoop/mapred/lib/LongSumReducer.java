/*  1:   */ package org.apache.hadoop.mapred.lib;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import java.util.Iterator;
/*  5:   */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*  6:   */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*  7:   */ import org.apache.hadoop.io.LongWritable;
/*  8:   */ import org.apache.hadoop.mapred.MapReduceBase;
/*  9:   */ import org.apache.hadoop.mapred.OutputCollector;
/* 10:   */ import org.apache.hadoop.mapred.Reducer;
/* 11:   */ import org.apache.hadoop.mapred.Reporter;
/* 12:   */ 
/* 13:   */ @InterfaceAudience.Public
/* 14:   */ @InterfaceStability.Stable
/* 15:   */ public class LongSumReducer<K>
/* 16:   */   extends MapReduceBase
/* 17:   */   implements Reducer<K, LongWritable, K, LongWritable>
/* 18:   */ {
/* 19:   */   public void reduce(K key, Iterator<LongWritable> values, OutputCollector<K, LongWritable> output, Reporter reporter)
/* 20:   */     throws IOException
/* 21:   */   {
/* 22:47 */     long sum = 0L;
/* 23:48 */     while (values.hasNext()) {
/* 24:49 */       sum += ((LongWritable)values.next()).get();
/* 25:   */     }
/* 26:53 */     output.collect(key, new LongWritable(sum));
/* 27:   */   }
/* 28:   */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.lib.LongSumReducer
 * JD-Core Version:    0.7.0.1
 */