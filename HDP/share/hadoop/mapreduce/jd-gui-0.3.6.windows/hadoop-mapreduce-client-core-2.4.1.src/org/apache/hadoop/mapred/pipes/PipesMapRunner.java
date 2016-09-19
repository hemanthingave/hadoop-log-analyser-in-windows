/*   1:    */ package org.apache.hadoop.mapred.pipes;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import org.apache.hadoop.io.FloatWritable;
/*   5:    */ import org.apache.hadoop.io.NullWritable;
/*   6:    */ import org.apache.hadoop.io.Writable;
/*   7:    */ import org.apache.hadoop.io.WritableComparable;
/*   8:    */ import org.apache.hadoop.mapred.JobConf;
/*   9:    */ import org.apache.hadoop.mapred.MapRunner;
/*  10:    */ import org.apache.hadoop.mapred.OutputCollector;
/*  11:    */ import org.apache.hadoop.mapred.RecordReader;
/*  12:    */ import org.apache.hadoop.mapred.Reporter;
/*  13:    */ import org.apache.hadoop.mapred.SkipBadRecords;
/*  14:    */ 
/*  15:    */ class PipesMapRunner<K1 extends WritableComparable, V1 extends Writable, K2 extends WritableComparable, V2 extends Writable>
/*  16:    */   extends MapRunner<K1, V1, K2, V2>
/*  17:    */ {
/*  18:    */   private JobConf job;
/*  19:    */   
/*  20:    */   public void configure(JobConf job)
/*  21:    */   {
/*  22: 48 */     this.job = job;
/*  23:    */     
/*  24:    */ 
/*  25: 51 */     SkipBadRecords.setAutoIncrMapperProcCount(job, false);
/*  26:    */   }
/*  27:    */   
/*  28:    */   public void run(RecordReader<K1, V1> input, OutputCollector<K2, V2> output, Reporter reporter)
/*  29:    */     throws IOException
/*  30:    */   {
/*  31: 63 */     Application<K1, V1, K2, V2> application = null;
/*  32:    */     try
/*  33:    */     {
/*  34: 65 */       RecordReader<FloatWritable, NullWritable> fakeInput = (!Submitter.getIsJavaRecordReader(this.job)) && (!Submitter.getIsJavaMapper(this.job)) ? input : null;
/*  35:    */       
/*  36:    */ 
/*  37:    */ 
/*  38: 69 */       application = new Application(this.job, fakeInput, output, reporter, this.job.getOutputKeyClass(), this.job.getOutputValueClass());
/*  39:    */     }
/*  40:    */     catch (InterruptedException ie)
/*  41:    */     {
/*  42: 74 */       throw new RuntimeException("interrupted", ie);
/*  43:    */     }
/*  44: 76 */     DownwardProtocol<K1, V1> downlink = application.getDownlink();
/*  45: 77 */     boolean isJavaInput = Submitter.getIsJavaRecordReader(this.job);
/*  46: 78 */     downlink.runMap(reporter.getInputSplit(), this.job.getNumReduceTasks(), isJavaInput);
/*  47:    */     
/*  48: 80 */     boolean skipping = this.job.getBoolean("mapreduce.job.skiprecords", false);
/*  49:    */     try
/*  50:    */     {
/*  51: 82 */       if (isJavaInput)
/*  52:    */       {
/*  53: 84 */         K1 key = (WritableComparable)input.createKey();
/*  54: 85 */         V1 value = (Writable)input.createValue();
/*  55: 86 */         downlink.setInputTypes(key.getClass().getName(), value.getClass().getName());
/*  56: 89 */         while (input.next(key, value))
/*  57:    */         {
/*  58: 91 */           downlink.mapItem(key, value);
/*  59: 92 */           if (skipping) {
/*  60: 95 */             downlink.flush();
/*  61:    */           }
/*  62:    */         }
/*  63: 98 */         downlink.endOfInput();
/*  64:    */       }
/*  65:100 */       application.waitForFinish();
/*  66:    */     }
/*  67:    */     catch (Throwable t)
/*  68:    */     {
/*  69:102 */       application.abort(t);
/*  70:    */     }
/*  71:    */     finally
/*  72:    */     {
/*  73:104 */       application.cleanup();
/*  74:    */     }
/*  75:    */   }
/*  76:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.pipes.PipesMapRunner
 * JD-Core Version:    0.7.0.1
 */