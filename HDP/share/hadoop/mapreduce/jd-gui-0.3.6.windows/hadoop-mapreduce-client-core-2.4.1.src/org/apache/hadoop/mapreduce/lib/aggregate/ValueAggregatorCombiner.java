/*  1:   */ package org.apache.hadoop.mapreduce.lib.aggregate;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import java.util.ArrayList;
/*  5:   */ import java.util.Iterator;
/*  6:   */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*  7:   */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*  8:   */ import org.apache.hadoop.conf.Configuration;
/*  9:   */ import org.apache.hadoop.io.Text;
/* 10:   */ import org.apache.hadoop.io.Writable;
/* 11:   */ import org.apache.hadoop.io.WritableComparable;
/* 12:   */ import org.apache.hadoop.mapreduce.Reducer;
/* 13:   */ import org.apache.hadoop.mapreduce.Reducer.Context;
/* 14:   */ 
/* 15:   */ @InterfaceAudience.Public
/* 16:   */ @InterfaceStability.Stable
/* 17:   */ public class ValueAggregatorCombiner<K1 extends WritableComparable<?>, V1 extends Writable>
/* 18:   */   extends Reducer<Text, Text, Text, Text>
/* 19:   */ {
/* 20:   */   public void reduce(Text key, Iterable<Text> values, Reducer<Text, Text, Text, Text>.Context context)
/* 21:   */     throws IOException, InterruptedException
/* 22:   */   {
/* 23:48 */     String keyStr = key.toString();
/* 24:49 */     int pos = keyStr.indexOf(":");
/* 25:50 */     String type = keyStr.substring(0, pos);
/* 26:51 */     long uniqCount = context.getConfiguration().getLong("mapreduce.aggregate.max.num.unique.values", 9223372036854775807L);
/* 27:   */     
/* 28:53 */     ValueAggregator aggregator = ValueAggregatorBaseDescriptor.generateValueAggregator(type, uniqCount);
/* 29:55 */     for (Text val : values) {
/* 30:56 */       aggregator.addNextValue(val);
/* 31:   */     }
/* 32:58 */     Iterator<?> outputs = aggregator.getCombinerOutput().iterator();
/* 33:60 */     while (outputs.hasNext())
/* 34:   */     {
/* 35:61 */       Object v = outputs.next();
/* 36:62 */       if ((v instanceof Text)) {
/* 37:63 */         context.write(key, (Text)v);
/* 38:   */       } else {
/* 39:65 */         context.write(key, new Text(v.toString()));
/* 40:   */       }
/* 41:   */     }
/* 42:   */   }
/* 43:   */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.lib.aggregate.ValueAggregatorCombiner
 * JD-Core Version:    0.7.0.1
 */