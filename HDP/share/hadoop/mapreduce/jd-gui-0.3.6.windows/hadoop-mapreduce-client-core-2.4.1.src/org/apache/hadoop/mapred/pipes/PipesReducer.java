/*   1:    */ package org.apache.hadoop.mapred.pipes;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.util.Iterator;
/*   5:    */ import org.apache.commons.logging.Log;
/*   6:    */ import org.apache.commons.logging.LogFactory;
/*   7:    */ import org.apache.hadoop.io.Writable;
/*   8:    */ import org.apache.hadoop.io.WritableComparable;
/*   9:    */ import org.apache.hadoop.mapred.JobConf;
/*  10:    */ import org.apache.hadoop.mapred.OutputCollector;
/*  11:    */ import org.apache.hadoop.mapred.Reducer;
/*  12:    */ import org.apache.hadoop.mapred.Reporter;
/*  13:    */ import org.apache.hadoop.mapred.SkipBadRecords;
/*  14:    */ 
/*  15:    */ class PipesReducer<K2 extends WritableComparable, V2 extends Writable, K3 extends WritableComparable, V3 extends Writable>
/*  16:    */   implements Reducer<K2, V2, K3, V3>
/*  17:    */ {
/*  18: 41 */   private static final Log LOG = LogFactory.getLog(PipesReducer.class.getName());
/*  19:    */   private JobConf job;
/*  20: 43 */   private Application<K2, V2, K3, V3> application = null;
/*  21: 44 */   private DownwardProtocol<K2, V2> downlink = null;
/*  22: 45 */   private boolean isOk = true;
/*  23: 46 */   private boolean skipping = false;
/*  24:    */   
/*  25:    */   public void configure(JobConf job)
/*  26:    */   {
/*  27: 49 */     this.job = job;
/*  28:    */     
/*  29:    */ 
/*  30: 52 */     SkipBadRecords.setAutoIncrReducerProcCount(job, false);
/*  31: 53 */     this.skipping = job.getBoolean("mapreduce.job.skiprecords", false);
/*  32:    */   }
/*  33:    */   
/*  34:    */   public void reduce(K2 key, Iterator<V2> values, OutputCollector<K3, V3> output, Reporter reporter)
/*  35:    */     throws IOException
/*  36:    */   {
/*  37: 63 */     this.isOk = false;
/*  38: 64 */     startApplication(output, reporter);
/*  39: 65 */     this.downlink.reduceKey(key);
/*  40: 66 */     while (values.hasNext()) {
/*  41: 67 */       this.downlink.reduceValue((Writable)values.next());
/*  42:    */     }
/*  43: 69 */     if (this.skipping) {
/*  44: 72 */       this.downlink.flush();
/*  45:    */     }
/*  46: 74 */     this.isOk = true;
/*  47:    */   }
/*  48:    */   
/*  49:    */   private void startApplication(OutputCollector<K3, V3> output, Reporter reporter)
/*  50:    */     throws IOException
/*  51:    */   {
/*  52: 79 */     if (this.application == null)
/*  53:    */     {
/*  54:    */       try
/*  55:    */       {
/*  56: 81 */         LOG.info("starting application");
/*  57: 82 */         this.application = new Application(this.job, null, output, reporter, this.job.getOutputKeyClass(), this.job.getOutputValueClass());
/*  58:    */         
/*  59:    */ 
/*  60:    */ 
/*  61:    */ 
/*  62: 87 */         this.downlink = this.application.getDownlink();
/*  63:    */       }
/*  64:    */       catch (InterruptedException ie)
/*  65:    */       {
/*  66: 89 */         throw new RuntimeException("interrupted", ie);
/*  67:    */       }
/*  68: 91 */       int reduce = 0;
/*  69: 92 */       this.downlink.runReduce(reduce, Submitter.getIsJavaRecordWriter(this.job));
/*  70:    */     }
/*  71:    */   }
/*  72:    */   
/*  73:    */   public void close()
/*  74:    */     throws IOException
/*  75:    */   {
/*  76:101 */     if (this.isOk)
/*  77:    */     {
/*  78:102 */       OutputCollector<K3, V3> nullCollector = new OutputCollector()
/*  79:    */       {
/*  80:    */         public void collect(K3 key, V3 value)
/*  81:    */           throws IOException
/*  82:    */         {}
/*  83:107 */       };
/*  84:108 */       startApplication(nullCollector, Reporter.NULL);
/*  85:    */     }
/*  86:    */     try
/*  87:    */     {
/*  88:111 */       if (this.isOk) {
/*  89:112 */         this.application.getDownlink().endOfInput();
/*  90:    */       } else {
/*  91:115 */         this.application.getDownlink().abort();
/*  92:    */       }
/*  93:117 */       LOG.info("waiting for finish");
/*  94:118 */       this.application.waitForFinish();
/*  95:119 */       LOG.info("got done");
/*  96:    */     }
/*  97:    */     catch (Throwable t)
/*  98:    */     {
/*  99:121 */       this.application.abort(t);
/* 100:    */     }
/* 101:    */     finally
/* 102:    */     {
/* 103:123 */       this.application.cleanup();
/* 104:    */     }
/* 105:    */   }
/* 106:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.pipes.PipesReducer
 * JD-Core Version:    0.7.0.1
 */