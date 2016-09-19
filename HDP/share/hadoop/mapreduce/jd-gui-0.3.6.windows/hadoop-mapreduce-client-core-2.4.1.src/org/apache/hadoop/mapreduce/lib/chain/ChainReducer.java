/*   1:    */ package org.apache.hadoop.mapreduce.lib.chain;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.util.List;
/*   5:    */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*   6:    */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*   7:    */ import org.apache.hadoop.conf.Configuration;
/*   8:    */ import org.apache.hadoop.mapreduce.Job;
/*   9:    */ import org.apache.hadoop.mapreduce.Mapper;
/*  10:    */ import org.apache.hadoop.mapreduce.Reducer;
/*  11:    */ import org.apache.hadoop.mapreduce.Reducer.Context;
/*  12:    */ 
/*  13:    */ @InterfaceAudience.Public
/*  14:    */ @InterfaceStability.Stable
/*  15:    */ public class ChainReducer<KEYIN, VALUEIN, KEYOUT, VALUEOUT>
/*  16:    */   extends Reducer<KEYIN, VALUEIN, KEYOUT, VALUEOUT>
/*  17:    */ {
/*  18:    */   private Chain chain;
/*  19:    */   
/*  20:    */   public static void setReducer(Job job, Class<? extends Reducer> klass, Class<?> inputKeyClass, Class<?> inputValueClass, Class<?> outputKeyClass, Class<?> outputValueClass, Configuration reducerConf)
/*  21:    */   {
/*  22:128 */     job.setReducerClass(ChainReducer.class);
/*  23:129 */     job.setOutputKeyClass(outputKeyClass);
/*  24:130 */     job.setOutputValueClass(outputValueClass);
/*  25:131 */     Chain.setReducer(job, klass, inputKeyClass, inputValueClass, outputKeyClass, outputValueClass, reducerConf);
/*  26:    */   }
/*  27:    */   
/*  28:    */   public static void addMapper(Job job, Class<? extends Mapper> klass, Class<?> inputKeyClass, Class<?> inputValueClass, Class<?> outputKeyClass, Class<?> outputValueClass, Configuration mapperConf)
/*  29:    */     throws IOException
/*  30:    */   {
/*  31:172 */     job.setOutputKeyClass(outputKeyClass);
/*  32:173 */     job.setOutputValueClass(outputValueClass);
/*  33:174 */     Chain.addMapper(false, job, klass, inputKeyClass, inputValueClass, outputKeyClass, outputValueClass, mapperConf);
/*  34:    */   }
/*  35:    */   
/*  36:    */   protected void setup(Reducer<KEYIN, VALUEIN, KEYOUT, VALUEOUT>.Context context)
/*  37:    */   {
/*  38:181 */     this.chain = new Chain(false);
/*  39:182 */     this.chain.setup(context.getConfiguration());
/*  40:    */   }
/*  41:    */   
/*  42:    */   public void run(Reducer<KEYIN, VALUEIN, KEYOUT, VALUEOUT>.Context context)
/*  43:    */     throws IOException, InterruptedException
/*  44:    */   {
/*  45:186 */     setup(context);
/*  46:189 */     if (this.chain.getReducer() == null) {
/*  47:190 */       return;
/*  48:    */     }
/*  49:192 */     int numMappers = this.chain.getAllMappers().size();
/*  50:194 */     if (numMappers == 0)
/*  51:    */     {
/*  52:195 */       this.chain.runReducer(context);
/*  53:196 */       return;
/*  54:    */     }
/*  55:203 */     Chain.ChainBlockingQueue<Chain.KeyValuePair<?, ?>> outputqueue = this.chain.createBlockingQueue();
/*  56:204 */     this.chain.addReducer(context, outputqueue);
/*  57:206 */     for (int i = 0; i < numMappers - 1; i++)
/*  58:    */     {
/*  59:207 */       Chain.ChainBlockingQueue<Chain.KeyValuePair<?, ?>> inputqueue = outputqueue;
/*  60:208 */       outputqueue = this.chain.createBlockingQueue();
/*  61:209 */       this.chain.addMapper(inputqueue, outputqueue, context, i);
/*  62:    */     }
/*  63:212 */     this.chain.addMapper(outputqueue, context, numMappers - 1);
/*  64:    */     
/*  65:    */ 
/*  66:215 */     this.chain.startAllThreads();
/*  67:    */     
/*  68:    */ 
/*  69:218 */     this.chain.joinAllThreads();
/*  70:    */   }
/*  71:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.lib.chain.ChainReducer
 * JD-Core Version:    0.7.0.1
 */