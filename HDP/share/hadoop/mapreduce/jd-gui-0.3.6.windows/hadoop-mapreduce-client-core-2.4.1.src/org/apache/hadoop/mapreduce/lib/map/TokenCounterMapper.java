/*  1:   */ package org.apache.hadoop.mapreduce.lib.map;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import java.util.StringTokenizer;
/*  5:   */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*  6:   */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*  7:   */ import org.apache.hadoop.io.IntWritable;
/*  8:   */ import org.apache.hadoop.io.Text;
/*  9:   */ import org.apache.hadoop.mapreduce.Mapper;
/* 10:   */ import org.apache.hadoop.mapreduce.Mapper.Context;
/* 11:   */ 
/* 12:   */ @InterfaceAudience.Public
/* 13:   */ @InterfaceStability.Stable
/* 14:   */ public class TokenCounterMapper
/* 15:   */   extends Mapper<Object, Text, Text, IntWritable>
/* 16:   */ {
/* 17:37 */   private static final IntWritable one = new IntWritable(1);
/* 18:38 */   private Text word = new Text();
/* 19:   */   
/* 20:   */   public void map(Object key, Text value, Mapper<Object, Text, Text, IntWritable>.Context context)
/* 21:   */     throws IOException, InterruptedException
/* 22:   */   {
/* 23:43 */     StringTokenizer itr = new StringTokenizer(value.toString());
/* 24:44 */     while (itr.hasMoreTokens())
/* 25:   */     {
/* 26:45 */       this.word.set(itr.nextToken());
/* 27:46 */       context.write(this.word, one);
/* 28:   */     }
/* 29:   */   }
/* 30:   */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.lib.map.TokenCounterMapper
 * JD-Core Version:    0.7.0.1
 */