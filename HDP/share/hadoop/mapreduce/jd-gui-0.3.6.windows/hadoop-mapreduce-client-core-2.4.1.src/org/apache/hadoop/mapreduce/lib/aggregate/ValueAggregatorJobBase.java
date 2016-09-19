/*  1:   */ package org.apache.hadoop.mapreduce.lib.aggregate;
/*  2:   */ 
/*  3:   */ import java.util.ArrayList;
/*  4:   */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*  5:   */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*  6:   */ import org.apache.hadoop.conf.Configuration;
/*  7:   */ import org.apache.hadoop.io.Writable;
/*  8:   */ import org.apache.hadoop.io.WritableComparable;
/*  9:   */ 
/* 10:   */ @InterfaceAudience.Public
/* 11:   */ @InterfaceStability.Stable
/* 12:   */ public class ValueAggregatorJobBase<K1 extends WritableComparable<?>, V1 extends Writable>
/* 13:   */ {
/* 14:   */   public static final String DESCRIPTOR = "mapreduce.aggregate.descriptor";
/* 15:   */   public static final String DESCRIPTOR_NUM = "mapreduce.aggregate.descriptor.num";
/* 16:   */   public static final String USER_JAR = "mapreduce.aggregate.user.jar.file";
/* 17:43 */   protected static ArrayList<ValueAggregatorDescriptor> aggregatorDescriptorList = null;
/* 18:   */   
/* 19:   */   public static void setup(Configuration job)
/* 20:   */   {
/* 21:46 */     initializeMySpec(job);
/* 22:47 */     logSpec();
/* 23:   */   }
/* 24:   */   
/* 25:   */   protected static ValueAggregatorDescriptor getValueAggregatorDescriptor(String spec, Configuration conf)
/* 26:   */   {
/* 27:52 */     if (spec == null) {
/* 28:53 */       return null;
/* 29:   */     }
/* 30:54 */     String[] segments = spec.split(",", -1);
/* 31:55 */     String type = segments[0];
/* 32:56 */     if (type.compareToIgnoreCase("UserDefined") == 0)
/* 33:   */     {
/* 34:57 */       String className = segments[1];
/* 35:58 */       return new UserDefinedValueAggregatorDescriptor(className, conf);
/* 36:   */     }
/* 37:60 */     return null;
/* 38:   */   }
/* 39:   */   
/* 40:   */   protected static ArrayList<ValueAggregatorDescriptor> getAggregatorDescriptors(Configuration conf)
/* 41:   */   {
/* 42:65 */     int num = conf.getInt("mapreduce.aggregate.descriptor.num", 0);
/* 43:66 */     ArrayList<ValueAggregatorDescriptor> retv = new ArrayList(num);
/* 44:68 */     for (int i = 0; i < num; i++)
/* 45:   */     {
/* 46:69 */       String spec = conf.get("mapreduce.aggregate.descriptor." + i);
/* 47:70 */       ValueAggregatorDescriptor ad = getValueAggregatorDescriptor(spec, conf);
/* 48:71 */       if (ad != null) {
/* 49:72 */         retv.add(ad);
/* 50:   */       }
/* 51:   */     }
/* 52:75 */     return retv;
/* 53:   */   }
/* 54:   */   
/* 55:   */   private static void initializeMySpec(Configuration conf)
/* 56:   */   {
/* 57:79 */     aggregatorDescriptorList = getAggregatorDescriptors(conf);
/* 58:80 */     if (aggregatorDescriptorList.size() == 0) {
/* 59:81 */       aggregatorDescriptorList.add(new UserDefinedValueAggregatorDescriptor(ValueAggregatorBaseDescriptor.class.getCanonicalName(), conf));
/* 60:   */     }
/* 61:   */   }
/* 62:   */   
/* 63:   */   protected static void logSpec() {}
/* 64:   */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.lib.aggregate.ValueAggregatorJobBase
 * JD-Core Version:    0.7.0.1
 */