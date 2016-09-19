/*  1:   */ package org.apache.hadoop.mapreduce.lib.aggregate;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*  5:   */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*  6:   */ import org.apache.hadoop.conf.Configuration;
/*  7:   */ import org.apache.hadoop.io.Text;
/*  8:   */ import org.apache.hadoop.io.Writable;
/*  9:   */ import org.apache.hadoop.io.WritableComparable;
/* 10:   */ import org.apache.hadoop.mapreduce.Reducer;
/* 11:   */ import org.apache.hadoop.mapreduce.Reducer.Context;
/* 12:   */ 
/* 13:   */ @InterfaceAudience.Public
/* 14:   */ @InterfaceStability.Stable
/* 15:   */ public class ValueAggregatorReducer<K1 extends WritableComparable<?>, V1 extends Writable>
/* 16:   */   extends Reducer<Text, Text, Text, Text>
/* 17:   */ {
/* 18:   */   public void setup(Reducer<Text, Text, Text, Text>.Context context)
/* 19:   */     throws IOException, InterruptedException
/* 20:   */   {
/* 21:41 */     ValueAggregatorJobBase.setup(context.getConfiguration());
/* 22:   */   }
/* 23:   */   
/* 24:   */   public void reduce(Text key, Iterable<Text> values, Reducer<Text, Text, Text, Text>.Context context)
/* 25:   */     throws IOException, InterruptedException
/* 26:   */   {
/* 27:56 */     String keyStr = key.toString();
/* 28:57 */     int pos = keyStr.indexOf(":");
/* 29:58 */     String type = keyStr.substring(0, pos);
/* 30:59 */     keyStr = keyStr.substring(pos + ":".length());
/* 31:   */     
/* 32:61 */     long uniqCount = context.getConfiguration().getLong("mapreduce.aggregate.max.num.unique.values", 9223372036854775807L);
/* 33:   */     
/* 34:63 */     ValueAggregator aggregator = ValueAggregatorBaseDescriptor.generateValueAggregator(type, uniqCount);
/* 35:65 */     for (Text value : values) {
/* 36:66 */       aggregator.addNextValue(value);
/* 37:   */     }
/* 38:69 */     String val = aggregator.getReport();
/* 39:70 */     key = new Text(keyStr);
/* 40:71 */     context.write(key, new Text(val));
/* 41:   */   }
/* 42:   */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.lib.aggregate.ValueAggregatorReducer
 * JD-Core Version:    0.7.0.1
 */