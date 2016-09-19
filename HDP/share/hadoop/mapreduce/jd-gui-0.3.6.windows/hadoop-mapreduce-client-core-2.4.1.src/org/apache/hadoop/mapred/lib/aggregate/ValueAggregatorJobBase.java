/*  1:   */ package org.apache.hadoop.mapred.lib.aggregate;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import java.util.ArrayList;
/*  5:   */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*  6:   */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*  7:   */ import org.apache.hadoop.io.Text;
/*  8:   */ import org.apache.hadoop.io.Writable;
/*  9:   */ import org.apache.hadoop.io.WritableComparable;
/* 10:   */ import org.apache.hadoop.mapred.JobConf;
/* 11:   */ import org.apache.hadoop.mapred.Mapper;
/* 12:   */ import org.apache.hadoop.mapred.Reducer;
/* 13:   */ 
/* 14:   */ @InterfaceAudience.Public
/* 15:   */ @InterfaceStability.Stable
/* 16:   */ public abstract class ValueAggregatorJobBase<K1 extends WritableComparable, V1 extends Writable>
/* 17:   */   implements Mapper<K1, V1, Text, Text>, Reducer<Text, Text, Text, Text>
/* 18:   */ {
/* 19:43 */   protected ArrayList<ValueAggregatorDescriptor> aggregatorDescriptorList = null;
/* 20:   */   
/* 21:   */   public void configure(JobConf job)
/* 22:   */   {
/* 23:46 */     initializeMySpec(job);
/* 24:47 */     logSpec();
/* 25:   */   }
/* 26:   */   
/* 27:   */   private static ValueAggregatorDescriptor getValueAggregatorDescriptor(String spec, JobConf job)
/* 28:   */   {
/* 29:52 */     if (spec == null) {
/* 30:53 */       return null;
/* 31:   */     }
/* 32:54 */     String[] segments = spec.split(",", -1);
/* 33:55 */     String type = segments[0];
/* 34:56 */     if (type.compareToIgnoreCase("UserDefined") == 0)
/* 35:   */     {
/* 36:57 */       String className = segments[1];
/* 37:58 */       return new UserDefinedValueAggregatorDescriptor(className, job);
/* 38:   */     }
/* 39:60 */     return null;
/* 40:   */   }
/* 41:   */   
/* 42:   */   private static ArrayList<ValueAggregatorDescriptor> getAggregatorDescriptors(JobConf job)
/* 43:   */   {
/* 44:64 */     String advn = "aggregator.descriptor";
/* 45:65 */     int num = job.getInt(advn + ".num", 0);
/* 46:66 */     ArrayList<ValueAggregatorDescriptor> retv = new ArrayList(num);
/* 47:67 */     for (int i = 0; i < num; i++)
/* 48:   */     {
/* 49:68 */       String spec = job.get(advn + "." + i);
/* 50:69 */       ValueAggregatorDescriptor ad = getValueAggregatorDescriptor(spec, job);
/* 51:70 */       if (ad != null) {
/* 52:71 */         retv.add(ad);
/* 53:   */       }
/* 54:   */     }
/* 55:74 */     return retv;
/* 56:   */   }
/* 57:   */   
/* 58:   */   private void initializeMySpec(JobConf job)
/* 59:   */   {
/* 60:78 */     this.aggregatorDescriptorList = getAggregatorDescriptors(job);
/* 61:79 */     if (this.aggregatorDescriptorList.size() == 0) {
/* 62:80 */       this.aggregatorDescriptorList.add(new UserDefinedValueAggregatorDescriptor(ValueAggregatorBaseDescriptor.class.getCanonicalName(), job));
/* 63:   */     }
/* 64:   */   }
/* 65:   */   
/* 66:   */   protected void logSpec() {}
/* 67:   */   
/* 68:   */   public void close()
/* 69:   */     throws IOException
/* 70:   */   {}
/* 71:   */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.lib.aggregate.ValueAggregatorJobBase
 * JD-Core Version:    0.7.0.1
 */