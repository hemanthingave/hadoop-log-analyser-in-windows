/*  1:   */ package org.apache.hadoop.mapred.lib;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import java.util.StringTokenizer;
/*  5:   */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*  6:   */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*  7:   */ import org.apache.hadoop.io.LongWritable;
/*  8:   */ import org.apache.hadoop.io.Text;
/*  9:   */ import org.apache.hadoop.mapred.MapReduceBase;
/* 10:   */ import org.apache.hadoop.mapred.Mapper;
/* 11:   */ import org.apache.hadoop.mapred.OutputCollector;
/* 12:   */ import org.apache.hadoop.mapred.Reporter;
/* 13:   */ 
/* 14:   */ @InterfaceAudience.Public
/* 15:   */ @InterfaceStability.Stable
/* 16:   */ public class TokenCountMapper<K>
/* 17:   */   extends MapReduceBase
/* 18:   */   implements Mapper<K, Text, Text, LongWritable>
/* 19:   */ {
/* 20:   */   public void map(K key, Text value, OutputCollector<Text, LongWritable> output, Reporter reporter)
/* 21:   */     throws IOException
/* 22:   */   {
/* 23:48 */     String text = value.toString();
/* 24:   */     
/* 25:   */ 
/* 26:51 */     StringTokenizer st = new StringTokenizer(text);
/* 27:52 */     while (st.hasMoreTokens()) {
/* 28:54 */       output.collect(new Text(st.nextToken()), new LongWritable(1L));
/* 29:   */     }
/* 30:   */   }
/* 31:   */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.lib.TokenCountMapper
 * JD-Core Version:    0.7.0.1
 */