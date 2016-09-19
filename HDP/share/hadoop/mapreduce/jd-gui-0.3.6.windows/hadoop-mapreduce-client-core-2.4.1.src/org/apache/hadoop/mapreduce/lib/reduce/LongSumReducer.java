/*  1:   */ package org.apache.hadoop.mapreduce.lib.reduce;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*  5:   */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*  6:   */ import org.apache.hadoop.io.LongWritable;
/*  7:   */ import org.apache.hadoop.mapreduce.Reducer;
/*  8:   */ import org.apache.hadoop.mapreduce.Reducer.Context;
/*  9:   */ 
/* 10:   */ @InterfaceAudience.Public
/* 11:   */ @InterfaceStability.Stable
/* 12:   */ public class LongSumReducer<KEY>
/* 13:   */   extends Reducer<KEY, LongWritable, KEY, LongWritable>
/* 14:   */ {
/* 15:33 */   private LongWritable result = new LongWritable();
/* 16:   */   
/* 17:   */   public void reduce(KEY key, Iterable<LongWritable> values, Reducer<KEY, LongWritable, KEY, LongWritable>.Context context)
/* 18:   */     throws IOException, InterruptedException
/* 19:   */   {
/* 20:37 */     long sum = 0L;
/* 21:38 */     for (LongWritable val : values) {
/* 22:39 */       sum += val.get();
/* 23:   */     }
/* 24:41 */     this.result.set(sum);
/* 25:42 */     context.write(key, this.result);
/* 26:   */   }
/* 27:   */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.lib.reduce.LongSumReducer
 * JD-Core Version:    0.7.0.1
 */