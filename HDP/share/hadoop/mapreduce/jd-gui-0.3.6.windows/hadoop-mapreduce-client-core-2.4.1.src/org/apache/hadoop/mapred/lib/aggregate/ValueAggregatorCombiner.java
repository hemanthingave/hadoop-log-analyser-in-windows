/*  1:   */ package org.apache.hadoop.mapred.lib.aggregate;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import java.util.ArrayList;
/*  5:   */ import java.util.Iterator;
/*  6:   */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*  7:   */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*  8:   */ import org.apache.hadoop.io.Text;
/*  9:   */ import org.apache.hadoop.io.Writable;
/* 10:   */ import org.apache.hadoop.io.WritableComparable;
/* 11:   */ import org.apache.hadoop.mapred.JobConf;
/* 12:   */ import org.apache.hadoop.mapred.OutputCollector;
/* 13:   */ import org.apache.hadoop.mapred.Reporter;
/* 14:   */ 
/* 15:   */ @InterfaceAudience.Public
/* 16:   */ @InterfaceStability.Stable
/* 17:   */ public class ValueAggregatorCombiner<K1 extends WritableComparable, V1 extends Writable>
/* 18:   */   extends ValueAggregatorJobBase<K1, V1>
/* 19:   */ {
/* 20:   */   public void configure(JobConf job) {}
/* 21:   */   
/* 22:   */   public void reduce(Text key, Iterator<Text> values, OutputCollector<Text, Text> output, Reporter reporter)
/* 23:   */     throws IOException
/* 24:   */   {
/* 25:57 */     String keyStr = key.toString();
/* 26:58 */     int pos = keyStr.indexOf(":");
/* 27:59 */     String type = keyStr.substring(0, pos);
/* 28:60 */     ValueAggregator aggregator = ValueAggregatorBaseDescriptor.generateValueAggregator(type);
/* 29:62 */     while (values.hasNext()) {
/* 30:63 */       aggregator.addNextValue(values.next());
/* 31:   */     }
/* 32:65 */     Iterator outputs = aggregator.getCombinerOutput().iterator();
/* 33:67 */     while (outputs.hasNext())
/* 34:   */     {
/* 35:68 */       Object v = outputs.next();
/* 36:69 */       if ((v instanceof Text)) {
/* 37:70 */         output.collect(key, (Text)v);
/* 38:   */       } else {
/* 39:72 */         output.collect(key, new Text(v.toString()));
/* 40:   */       }
/* 41:   */     }
/* 42:   */   }
/* 43:   */   
/* 44:   */   public void close()
/* 45:   */     throws IOException
/* 46:   */   {}
/* 47:   */   
/* 48:   */   public void map(K1 arg0, V1 arg1, OutputCollector<Text, Text> arg2, Reporter arg3)
/* 49:   */     throws IOException
/* 50:   */   {
/* 51:91 */     throw new IOException("should not be called\n");
/* 52:   */   }
/* 53:   */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.lib.aggregate.ValueAggregatorCombiner
 * JD-Core Version:    0.7.0.1
 */