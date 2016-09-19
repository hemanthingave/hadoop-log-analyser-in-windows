/*  1:   */ package org.apache.hadoop.mapred.lib.aggregate;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import java.util.ArrayList;
/*  5:   */ import java.util.Iterator;
/*  6:   */ import java.util.Map.Entry;
/*  7:   */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*  8:   */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*  9:   */ import org.apache.hadoop.io.Text;
/* 10:   */ import org.apache.hadoop.io.Writable;
/* 11:   */ import org.apache.hadoop.io.WritableComparable;
/* 12:   */ import org.apache.hadoop.mapred.OutputCollector;
/* 13:   */ import org.apache.hadoop.mapred.Reporter;
/* 14:   */ 
/* 15:   */ @InterfaceAudience.Public
/* 16:   */ @InterfaceStability.Stable
/* 17:   */ public class ValueAggregatorMapper<K1 extends WritableComparable, V1 extends Writable>
/* 18:   */   extends ValueAggregatorJobBase<K1, V1>
/* 19:   */ {
/* 20:   */   public void map(K1 key, V1 value, OutputCollector<Text, Text> output, Reporter reporter)
/* 21:   */     throws IOException
/* 22:   */   {
/* 23:49 */     Iterator iter = this.aggregatorDescriptorList.iterator();
/* 24:50 */     while (iter.hasNext())
/* 25:   */     {
/* 26:51 */       ValueAggregatorDescriptor ad = (ValueAggregatorDescriptor)iter.next();
/* 27:52 */       Iterator<Map.Entry<Text, Text>> ens = ad.generateKeyValPairs(key, value).iterator();
/* 28:54 */       while (ens.hasNext())
/* 29:   */       {
/* 30:55 */         Map.Entry<Text, Text> en = (Map.Entry)ens.next();
/* 31:56 */         output.collect(en.getKey(), en.getValue());
/* 32:   */       }
/* 33:   */     }
/* 34:   */   }
/* 35:   */   
/* 36:   */   public void reduce(Text arg0, Iterator<Text> arg1, OutputCollector<Text, Text> arg2, Reporter arg3)
/* 37:   */     throws IOException
/* 38:   */   {
/* 39:67 */     throw new IOException("should not be called\n");
/* 40:   */   }
/* 41:   */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.lib.aggregate.ValueAggregatorMapper
 * JD-Core Version:    0.7.0.1
 */