/*  1:   */ package org.apache.hadoop.mapred.lib;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import org.apache.hadoop.classification.InterfaceAudience.Private;
/*  5:   */ import org.apache.hadoop.classification.InterfaceStability.Unstable;
/*  6:   */ import org.apache.hadoop.mapred.JobConf;
/*  7:   */ import org.apache.hadoop.mapred.Mapper;
/*  8:   */ import org.apache.hadoop.mapred.OutputCollector;
/*  9:   */ import org.apache.hadoop.mapred.Reporter;
/* 10:   */ import org.apache.hadoop.util.ReflectionUtils;
/* 11:   */ 
/* 12:   */ @InterfaceAudience.Private
/* 13:   */ @InterfaceStability.Unstable
/* 14:   */ public class DelegatingMapper<K1, V1, K2, V2>
/* 15:   */   implements Mapper<K1, V1, K2, V2>
/* 16:   */ {
/* 17:   */   private JobConf conf;
/* 18:   */   private Mapper<K1, V1, K2, V2> mapper;
/* 19:   */   
/* 20:   */   public void map(K1 key, V1 value, OutputCollector<K2, V2> outputCollector, Reporter reporter)
/* 21:   */     throws IOException
/* 22:   */   {
/* 23:50 */     if (this.mapper == null)
/* 24:   */     {
/* 25:52 */       TaggedInputSplit inputSplit = (TaggedInputSplit)reporter.getInputSplit();
/* 26:53 */       this.mapper = ((Mapper)ReflectionUtils.newInstance(inputSplit.getMapperClass(), this.conf));
/* 27:   */     }
/* 28:56 */     this.mapper.map(key, value, outputCollector, reporter);
/* 29:   */   }
/* 30:   */   
/* 31:   */   public void configure(JobConf conf)
/* 32:   */   {
/* 33:60 */     this.conf = conf;
/* 34:   */   }
/* 35:   */   
/* 36:   */   public void close()
/* 37:   */     throws IOException
/* 38:   */   {
/* 39:64 */     if (this.mapper != null) {
/* 40:65 */       this.mapper.close();
/* 41:   */     }
/* 42:   */   }
/* 43:   */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.lib.DelegatingMapper
 * JD-Core Version:    0.7.0.1
 */