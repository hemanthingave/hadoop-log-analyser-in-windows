/*   1:    */ package org.apache.hadoop.mapred.lib;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.util.Iterator;
/*   5:    */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*   6:    */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*   7:    */ import org.apache.hadoop.mapred.JobConf;
/*   8:    */ import org.apache.hadoop.mapred.Mapper;
/*   9:    */ import org.apache.hadoop.mapred.OutputCollector;
/*  10:    */ import org.apache.hadoop.mapred.Reducer;
/*  11:    */ import org.apache.hadoop.mapred.Reporter;
/*  12:    */ 
/*  13:    */ @InterfaceAudience.Public
/*  14:    */ @InterfaceStability.Stable
/*  15:    */ public class ChainReducer
/*  16:    */   implements Reducer
/*  17:    */ {
/*  18:    */   private Chain chain;
/*  19:    */   
/*  20:    */   public static <K1, V1, K2, V2> void setReducer(JobConf job, Class<? extends Reducer<K1, V1, K2, V2>> klass, Class<? extends K1> inputKeyClass, Class<? extends V1> inputValueClass, Class<? extends K2> outputKeyClass, Class<? extends V2> outputValueClass, boolean byValue, JobConf reducerConf)
/*  21:    */   {
/*  22:133 */     job.setReducerClass(ChainReducer.class);
/*  23:134 */     job.setOutputKeyClass(outputKeyClass);
/*  24:135 */     job.setOutputValueClass(outputValueClass);
/*  25:136 */     Chain.setReducer(job, klass, inputKeyClass, inputValueClass, outputKeyClass, outputValueClass, byValue, reducerConf);
/*  26:    */   }
/*  27:    */   
/*  28:    */   public static <K1, V1, K2, V2> void addMapper(JobConf job, Class<? extends Mapper<K1, V1, K2, V2>> klass, Class<? extends K1> inputKeyClass, Class<? extends V1> inputValueClass, Class<? extends K2> outputKeyClass, Class<? extends V2> outputValueClass, boolean byValue, JobConf mapperConf)
/*  29:    */   {
/*  30:177 */     job.setOutputKeyClass(outputKeyClass);
/*  31:178 */     job.setOutputValueClass(outputValueClass);
/*  32:179 */     Chain.addMapper(false, job, klass, inputKeyClass, inputValueClass, outputKeyClass, outputValueClass, byValue, mapperConf);
/*  33:    */   }
/*  34:    */   
/*  35:    */   public ChainReducer()
/*  36:    */   {
/*  37:189 */     this.chain = new Chain(false);
/*  38:    */   }
/*  39:    */   
/*  40:    */   public void configure(JobConf job)
/*  41:    */   {
/*  42:199 */     this.chain.configure(job);
/*  43:    */   }
/*  44:    */   
/*  45:    */   public void reduce(Object key, Iterator values, OutputCollector output, Reporter reporter)
/*  46:    */     throws IOException
/*  47:    */   {
/*  48:209 */     Reducer reducer = this.chain.getReducer();
/*  49:210 */     if (reducer != null) {
/*  50:211 */       reducer.reduce(key, values, this.chain.getReducerCollector(output, reporter), reporter);
/*  51:    */     }
/*  52:    */   }
/*  53:    */   
/*  54:    */   public void close()
/*  55:    */     throws IOException
/*  56:    */   {
/*  57:223 */     this.chain.close();
/*  58:    */   }
/*  59:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.lib.ChainReducer
 * JD-Core Version:    0.7.0.1
 */