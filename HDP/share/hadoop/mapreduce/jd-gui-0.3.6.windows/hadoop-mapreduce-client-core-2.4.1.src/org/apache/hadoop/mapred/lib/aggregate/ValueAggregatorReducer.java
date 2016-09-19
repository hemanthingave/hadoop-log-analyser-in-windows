/*  1:   */ package org.apache.hadoop.mapred.lib.aggregate;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import java.util.Iterator;
/*  5:   */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*  6:   */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*  7:   */ import org.apache.hadoop.io.Text;
/*  8:   */ import org.apache.hadoop.io.Writable;
/*  9:   */ import org.apache.hadoop.io.WritableComparable;
/* 10:   */ import org.apache.hadoop.mapred.OutputCollector;
/* 11:   */ import org.apache.hadoop.mapred.Reporter;
/* 12:   */ 
/* 13:   */ @InterfaceAudience.Public
/* 14:   */ @InterfaceStability.Stable
/* 15:   */ public class ValueAggregatorReducer<K1 extends WritableComparable, V1 extends Writable>
/* 16:   */   extends ValueAggregatorJobBase<K1, V1>
/* 17:   */ {
/* 18:   */   public void reduce(Text key, Iterator<Text> values, OutputCollector<Text, Text> output, Reporter reporter)
/* 19:   */     throws IOException
/* 20:   */   {
/* 21:52 */     String keyStr = key.toString();
/* 22:53 */     int pos = keyStr.indexOf(":");
/* 23:54 */     String type = keyStr.substring(0, pos);
/* 24:55 */     keyStr = keyStr.substring(pos + ":".length());
/* 25:   */     
/* 26:   */ 
/* 27:58 */     ValueAggregator aggregator = ValueAggregatorBaseDescriptor.generateValueAggregator(type);
/* 28:60 */     while (values.hasNext()) {
/* 29:61 */       aggregator.addNextValue(values.next());
/* 30:   */     }
/* 31:64 */     String val = aggregator.getReport();
/* 32:65 */     key = new Text(keyStr);
/* 33:66 */     output.collect(key, new Text(val));
/* 34:   */   }
/* 35:   */   
/* 36:   */   public void map(K1 arg0, V1 arg1, OutputCollector<Text, Text> arg2, Reporter arg3)
/* 37:   */     throws IOException
/* 38:   */   {
/* 39:74 */     throw new IOException("should not be called\n");
/* 40:   */   }
/* 41:   */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.lib.aggregate.ValueAggregatorReducer
 * JD-Core Version:    0.7.0.1
 */