/*   1:    */ package org.apache.hadoop.mapred.lib;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*   5:    */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*   6:    */ import org.apache.hadoop.mapred.JobConf;
/*   7:    */ import org.apache.hadoop.mapred.Mapper;
/*   8:    */ import org.apache.hadoop.mapred.OutputCollector;
/*   9:    */ import org.apache.hadoop.mapred.Reporter;
/*  10:    */ 
/*  11:    */ @InterfaceAudience.Public
/*  12:    */ @InterfaceStability.Stable
/*  13:    */ public class ChainMapper
/*  14:    */   implements Mapper
/*  15:    */ {
/*  16:    */   private Chain chain;
/*  17:    */   
/*  18:    */   public static <K1, V1, K2, V2> void addMapper(JobConf job, Class<? extends Mapper<K1, V1, K2, V2>> klass, Class<? extends K1> inputKeyClass, Class<? extends V1> inputValueClass, Class<? extends K2> outputKeyClass, Class<? extends V2> outputValueClass, boolean byValue, JobConf mapperConf)
/*  19:    */   {
/*  20:133 */     job.setMapperClass(ChainMapper.class);
/*  21:134 */     job.setMapOutputKeyClass(outputKeyClass);
/*  22:135 */     job.setMapOutputValueClass(outputValueClass);
/*  23:136 */     Chain.addMapper(true, job, klass, inputKeyClass, inputValueClass, outputKeyClass, outputValueClass, byValue, mapperConf);
/*  24:    */   }
/*  25:    */   
/*  26:    */   public ChainMapper()
/*  27:    */   {
/*  28:146 */     this.chain = new Chain(true);
/*  29:    */   }
/*  30:    */   
/*  31:    */   public void configure(JobConf job)
/*  32:    */   {
/*  33:156 */     this.chain.configure(job);
/*  34:    */   }
/*  35:    */   
/*  36:    */   public void map(Object key, Object value, OutputCollector output, Reporter reporter)
/*  37:    */     throws IOException
/*  38:    */   {
/*  39:165 */     Mapper mapper = this.chain.getFirstMap();
/*  40:166 */     if (mapper != null) {
/*  41:167 */       mapper.map(key, value, this.chain.getMapperCollector(0, output, reporter), reporter);
/*  42:    */     }
/*  43:    */   }
/*  44:    */   
/*  45:    */   public void close()
/*  46:    */     throws IOException
/*  47:    */   {
/*  48:179 */     this.chain.close();
/*  49:    */   }
/*  50:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.lib.ChainMapper
 * JD-Core Version:    0.7.0.1
 */