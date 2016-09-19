/*  1:   */ package org.apache.hadoop.mapreduce.lib.aggregate;
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
/* 12:   */ import org.apache.hadoop.mapreduce.Mapper;
/* 13:   */ import org.apache.hadoop.mapreduce.Mapper.Context;
/* 14:   */ 
/* 15:   */ @InterfaceAudience.Public
/* 16:   */ @InterfaceStability.Stable
/* 17:   */ public class ValueAggregatorMapper<K1 extends WritableComparable<?>, V1 extends Writable>
/* 18:   */   extends Mapper<K1, V1, Text, Text>
/* 19:   */ {
/* 20:   */   public void setup(Mapper<K1, V1, Text, Text>.Context context)
/* 21:   */     throws IOException, InterruptedException
/* 22:   */   {
/* 23:43 */     ValueAggregatorJobBase.setup(context.getConfiguration());
/* 24:   */   }
/* 25:   */   
/* 26:   */   public void map(K1 key, V1 value, Mapper<K1, V1, Text, Text>.Context context)
/* 27:   */     throws IOException, InterruptedException
/* 28:   */   {
/* 29:53 */     Iterator<?> iter = ValueAggregatorJobBase.aggregatorDescriptorList.iterator();
/* 30:55 */     while (iter.hasNext())
/* 31:   */     {
/* 32:56 */       ValueAggregatorDescriptor ad = (ValueAggregatorDescriptor)iter.next();
/* 33:57 */       Iterator<Map.Entry<Text, Text>> ens = ad.generateKeyValPairs(key, value).iterator();
/* 34:59 */       while (ens.hasNext())
/* 35:   */       {
/* 36:60 */         Map.Entry<Text, Text> en = (Map.Entry)ens.next();
/* 37:61 */         context.write(en.getKey(), en.getValue());
/* 38:   */       }
/* 39:   */     }
/* 40:   */   }
/* 41:   */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.lib.aggregate.ValueAggregatorMapper
 * JD-Core Version:    0.7.0.1
 */