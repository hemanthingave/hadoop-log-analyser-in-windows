/*  1:   */ package org.apache.hadoop.mapreduce.lib.reduce;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*  5:   */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*  6:   */ import org.apache.hadoop.io.IntWritable;
/*  7:   */ import org.apache.hadoop.mapreduce.Reducer;
/*  8:   */ import org.apache.hadoop.mapreduce.Reducer.Context;
/*  9:   */ 
/* 10:   */ @InterfaceAudience.Public
/* 11:   */ @InterfaceStability.Stable
/* 12:   */ public class IntSumReducer<Key>
/* 13:   */   extends Reducer<Key, IntWritable, Key, IntWritable>
/* 14:   */ {
/* 15:32 */   private IntWritable result = new IntWritable();
/* 16:   */   
/* 17:   */   public void reduce(Key key, Iterable<IntWritable> values, Reducer<Key, IntWritable, Key, IntWritable>.Context context)
/* 18:   */     throws IOException, InterruptedException
/* 19:   */   {
/* 20:36 */     int sum = 0;
/* 21:37 */     for (IntWritable val : values) {
/* 22:38 */       sum += val.get();
/* 23:   */     }
/* 24:40 */     this.result.set(sum);
/* 25:41 */     context.write(key, this.result);
/* 26:   */   }
/* 27:   */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.lib.reduce.IntSumReducer
 * JD-Core Version:    0.7.0.1
 */