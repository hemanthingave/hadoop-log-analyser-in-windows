/*   1:    */ package org.apache.hadoop.mapred.lib.aggregate;
/*   2:    */ 
/*   3:    */ import java.util.Map.Entry;
/*   4:    */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*   5:    */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*   6:    */ import org.apache.hadoop.io.Text;
/*   7:    */ import org.apache.hadoop.mapred.JobConf;
/*   8:    */ 
/*   9:    */ @InterfaceAudience.Public
/*  10:    */ @InterfaceStability.Stable
/*  11:    */ public class ValueAggregatorBaseDescriptor
/*  12:    */   extends org.apache.hadoop.mapreduce.lib.aggregate.ValueAggregatorBaseDescriptor
/*  13:    */   implements ValueAggregatorDescriptor
/*  14:    */ {
/*  15:    */   public static final String UNIQ_VALUE_COUNT = "UniqValueCount";
/*  16:    */   public static final String LONG_VALUE_SUM = "LongValueSum";
/*  17:    */   public static final String DOUBLE_VALUE_SUM = "DoubleValueSum";
/*  18:    */   public static final String VALUE_HISTOGRAM = "ValueHistogram";
/*  19:    */   public static final String LONG_VALUE_MAX = "LongValueMax";
/*  20:    */   public static final String LONG_VALUE_MIN = "LongValueMin";
/*  21:    */   public static final String STRING_VALUE_MAX = "StringValueMax";
/*  22:    */   public static final String STRING_VALUE_MIN = "StringValueMin";
/*  23: 62 */   private static long maxNumItems = 9223372036854775807L;
/*  24:    */   
/*  25:    */   public static Map.Entry<Text, Text> generateEntry(String type, String id, Text val)
/*  26:    */   {
/*  27: 73 */     return org.apache.hadoop.mapreduce.lib.aggregate.ValueAggregatorBaseDescriptor.generateEntry(type, id, val);
/*  28:    */   }
/*  29:    */   
/*  30:    */   public static ValueAggregator generateValueAggregator(String type)
/*  31:    */   {
/*  32: 83 */     ValueAggregator retv = null;
/*  33: 84 */     if (type.compareToIgnoreCase("LongValueSum") == 0) {
/*  34: 85 */       retv = new LongValueSum();
/*  35:    */     }
/*  36: 86 */     if (type.compareToIgnoreCase("LongValueMax") == 0) {
/*  37: 87 */       retv = new LongValueMax();
/*  38: 88 */     } else if (type.compareToIgnoreCase("LongValueMin") == 0) {
/*  39: 89 */       retv = new LongValueMin();
/*  40: 90 */     } else if (type.compareToIgnoreCase("StringValueMax") == 0) {
/*  41: 91 */       retv = new StringValueMax();
/*  42: 92 */     } else if (type.compareToIgnoreCase("StringValueMin") == 0) {
/*  43: 93 */       retv = new StringValueMin();
/*  44: 94 */     } else if (type.compareToIgnoreCase("DoubleValueSum") == 0) {
/*  45: 95 */       retv = new DoubleValueSum();
/*  46: 96 */     } else if (type.compareToIgnoreCase("UniqValueCount") == 0) {
/*  47: 97 */       retv = new UniqValueCount(maxNumItems);
/*  48: 98 */     } else if (type.compareToIgnoreCase("ValueHistogram") == 0) {
/*  49: 99 */       retv = new ValueHistogram();
/*  50:    */     }
/*  51:101 */     return retv;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public void configure(JobConf job)
/*  55:    */   {
/*  56:110 */     super.configure(job);
/*  57:111 */     maxNumItems = job.getLong("aggregate.max.num.unique.values", 9223372036854775807L);
/*  58:    */   }
/*  59:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.lib.aggregate.ValueAggregatorBaseDescriptor
 * JD-Core Version:    0.7.0.1
 */