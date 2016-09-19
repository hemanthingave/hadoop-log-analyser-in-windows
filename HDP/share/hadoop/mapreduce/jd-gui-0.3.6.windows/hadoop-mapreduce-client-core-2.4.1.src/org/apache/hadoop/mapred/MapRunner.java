/*  1:   */ package org.apache.hadoop.mapred;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*  5:   */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*  6:   */ import org.apache.hadoop.util.ReflectionUtils;
/*  7:   */ 
/*  8:   */ @InterfaceAudience.Public
/*  9:   */ @InterfaceStability.Stable
/* 10:   */ public class MapRunner<K1, V1, K2, V2>
/* 11:   */   implements MapRunnable<K1, V1, K2, V2>
/* 12:   */ {
/* 13:   */   private Mapper<K1, V1, K2, V2> mapper;
/* 14:   */   private boolean incrProcCount;
/* 15:   */   
/* 16:   */   public void configure(JobConf job)
/* 17:   */   {
/* 18:38 */     this.mapper = ((Mapper)ReflectionUtils.newInstance(job.getMapperClass(), job));
/* 19:   */     
/* 20:40 */     this.incrProcCount = ((SkipBadRecords.getMapperMaxSkipRecords(job) > 0L) && (SkipBadRecords.getAutoIncrMapperProcCount(job)));
/* 21:   */   }
/* 22:   */   
/* 23:   */   public void run(RecordReader<K1, V1> input, OutputCollector<K2, V2> output, Reporter reporter)
/* 24:   */     throws IOException
/* 25:   */   {
/* 26:   */     try
/* 27:   */     {
/* 28:49 */       K1 key = input.createKey();
/* 29:50 */       V1 value = input.createValue();
/* 30:52 */       while (input.next(key, value))
/* 31:   */       {
/* 32:54 */         this.mapper.map(key, value, output, reporter);
/* 33:55 */         if (this.incrProcCount) {
/* 34:56 */           reporter.incrCounter("SkippingTaskCounters", "MapProcessedRecords", 1L);
/* 35:   */         }
/* 36:   */       }
/* 37:   */     }
/* 38:   */     finally
/* 39:   */     {
/* 40:61 */       this.mapper.close();
/* 41:   */     }
/* 42:   */   }
/* 43:   */   
/* 44:   */   protected Mapper<K1, V1, K2, V2> getMapper()
/* 45:   */   {
/* 46:66 */     return this.mapper;
/* 47:   */   }
/* 48:   */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.MapRunner
 * JD-Core Version:    0.7.0.1
 */