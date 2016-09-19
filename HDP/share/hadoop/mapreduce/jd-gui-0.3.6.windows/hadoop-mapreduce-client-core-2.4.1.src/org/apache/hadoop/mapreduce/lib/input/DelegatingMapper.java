/*  1:   */ package org.apache.hadoop.mapreduce.lib.input;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import org.apache.hadoop.classification.InterfaceAudience.Private;
/*  5:   */ import org.apache.hadoop.classification.InterfaceStability.Unstable;
/*  6:   */ import org.apache.hadoop.mapreduce.Mapper;
/*  7:   */ import org.apache.hadoop.mapreduce.Mapper.Context;
/*  8:   */ import org.apache.hadoop.util.ReflectionUtils;
/*  9:   */ 
/* 10:   */ @InterfaceAudience.Private
/* 11:   */ @InterfaceStability.Unstable
/* 12:   */ public class DelegatingMapper<K1, V1, K2, V2>
/* 13:   */   extends Mapper<K1, V1, K2, V2>
/* 14:   */ {
/* 15:   */   private Mapper<K1, V1, K2, V2> mapper;
/* 16:   */   
/* 17:   */   protected void setup(Mapper<K1, V1, K2, V2>.Context context)
/* 18:   */     throws IOException, InterruptedException
/* 19:   */   {
/* 20:45 */     TaggedInputSplit inputSplit = (TaggedInputSplit)context.getInputSplit();
/* 21:46 */     this.mapper = ((Mapper)ReflectionUtils.newInstance(inputSplit.getMapperClass(), context.getConfiguration()));
/* 22:   */   }
/* 23:   */   
/* 24:   */   public void run(Mapper<K1, V1, K2, V2>.Context context)
/* 25:   */     throws IOException, InterruptedException
/* 26:   */   {
/* 27:54 */     setup(context);
/* 28:55 */     this.mapper.run(context);
/* 29:56 */     cleanup(context);
/* 30:   */   }
/* 31:   */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.lib.input.DelegatingMapper
 * JD-Core Version:    0.7.0.1
 */