/*   1:    */ package org.apache.hadoop.mapreduce.lib.chain;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.util.List;
/*   5:    */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*   6:    */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*   7:    */ import org.apache.hadoop.conf.Configuration;
/*   8:    */ import org.apache.hadoop.mapreduce.Job;
/*   9:    */ import org.apache.hadoop.mapreduce.Mapper;
/*  10:    */ import org.apache.hadoop.mapreduce.Mapper.Context;
/*  11:    */ 
/*  12:    */ @InterfaceAudience.Public
/*  13:    */ @InterfaceStability.Stable
/*  14:    */ public class ChainMapper<KEYIN, VALUEIN, KEYOUT, VALUEOUT>
/*  15:    */   extends Mapper<KEYIN, VALUEIN, KEYOUT, VALUEOUT>
/*  16:    */ {
/*  17:    */   private Chain chain;
/*  18:    */   
/*  19:    */   public static void addMapper(Job job, Class<? extends Mapper> klass, Class<?> inputKeyClass, Class<?> inputValueClass, Class<?> outputKeyClass, Class<?> outputValueClass, Configuration mapperConf)
/*  20:    */     throws IOException
/*  21:    */   {
/*  22:123 */     job.setMapperClass(ChainMapper.class);
/*  23:124 */     job.setMapOutputKeyClass(outputKeyClass);
/*  24:125 */     job.setMapOutputValueClass(outputValueClass);
/*  25:126 */     Chain.addMapper(true, job, klass, inputKeyClass, inputValueClass, outputKeyClass, outputValueClass, mapperConf);
/*  26:    */   }
/*  27:    */   
/*  28:    */   protected void setup(Mapper<KEYIN, VALUEIN, KEYOUT, VALUEOUT>.Context context)
/*  29:    */   {
/*  30:133 */     this.chain = new Chain(true);
/*  31:134 */     this.chain.setup(context.getConfiguration());
/*  32:    */   }
/*  33:    */   
/*  34:    */   public void run(Mapper<KEYIN, VALUEIN, KEYOUT, VALUEOUT>.Context context)
/*  35:    */     throws IOException, InterruptedException
/*  36:    */   {
/*  37:139 */     setup(context);
/*  38:    */     
/*  39:141 */     int numMappers = this.chain.getAllMappers().size();
/*  40:142 */     if (numMappers == 0) {
/*  41:143 */       return;
/*  42:    */     }
/*  43:148 */     if (numMappers == 1)
/*  44:    */     {
/*  45:149 */       this.chain.runMapper(context, 0);
/*  46:    */     }
/*  47:    */     else
/*  48:    */     {
/*  49:153 */       Chain.ChainBlockingQueue<Chain.KeyValuePair<?, ?>> outputqueue = this.chain.createBlockingQueue();
/*  50:154 */       this.chain.addMapper(context, outputqueue, 0);
/*  51:156 */       for (int i = 1; i < numMappers - 1; i++)
/*  52:    */       {
/*  53:157 */         Chain.ChainBlockingQueue<Chain.KeyValuePair<?, ?>> inputqueue = outputqueue;
/*  54:158 */         outputqueue = this.chain.createBlockingQueue();
/*  55:159 */         this.chain.addMapper(inputqueue, outputqueue, context, i);
/*  56:    */       }
/*  57:162 */       this.chain.addMapper(outputqueue, context, numMappers - 1);
/*  58:    */     }
/*  59:166 */     this.chain.startAllThreads();
/*  60:    */     
/*  61:    */ 
/*  62:169 */     this.chain.joinAllThreads();
/*  63:    */   }
/*  64:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.lib.chain.ChainMapper
 * JD-Core Version:    0.7.0.1
 */