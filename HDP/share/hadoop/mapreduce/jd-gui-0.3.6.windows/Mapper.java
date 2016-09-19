/*   1:    */ package org.apache.hadoop.mapreduce;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*   5:    */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*   6:    */ 
/*   7:    */ @InterfaceAudience.Public
/*   8:    */ @InterfaceStability.Stable
/*   9:    */ public class Mapper<KEYIN, VALUEIN, KEYOUT, VALUEOUT>
/*  10:    */ {
/*  11:    */   protected void setup(Mapper<KEYIN, VALUEIN, KEYOUT, VALUEOUT>.Context context)
/*  12:    */     throws IOException, InterruptedException
/*  13:    */   {}
/*  14:    */   
/*  15:    */   protected void map(KEYIN key, VALUEIN value, Mapper<KEYIN, VALUEIN, KEYOUT, VALUEOUT>.Context context)
/*  16:    */     throws IOException, InterruptedException
/*  17:    */   {
/*  18:124 */     context.write(key, value);
/*  19:    */   }
/*  20:    */   
/*  21:    */   protected void cleanup(Mapper<KEYIN, VALUEIN, KEYOUT, VALUEOUT>.Context context)
/*  22:    */     throws IOException, InterruptedException
/*  23:    */   {}
/*  24:    */   
/*  25:    */   public void run(Mapper<KEYIN, VALUEIN, KEYOUT, VALUEOUT>.Context context)
/*  26:    */     throws IOException, InterruptedException
/*  27:    */   {
/*  28:142 */     setup(context);
/*  29:    */     try
/*  30:    */     {
/*  31:144 */       while (context.nextKeyValue()) {
/*  32:145 */         map(context.getCurrentKey(), context.getCurrentValue(), context);
/*  33:    */       }
/*  34:    */     }
/*  35:    */     finally
/*  36:    */     {
/*  37:148 */       cleanup(context);
/*  38:    */     }
/*  39:    */   }
/*  40:    */   
/*  41:    */   public abstract class Context
/*  42:    */     implements MapContext<KEYIN, VALUEIN, KEYOUT, VALUEOUT>
/*  43:    */   {
/*  44:    */     public Context() {}
/*  45:    */   }
/*  46:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.Mapper
 * JD-Core Version:    0.7.0.1
 */