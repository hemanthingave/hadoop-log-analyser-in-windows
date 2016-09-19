/*   1:    */ package org.apache.hadoop.mapreduce;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.util.Iterator;
/*   5:    */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*   6:    */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*   7:    */ import org.apache.hadoop.mapreduce.task.annotation.Checkpointable;
/*   8:    */ 
/*   9:    */ @Checkpointable
/*  10:    */ @InterfaceAudience.Public
/*  11:    */ @InterfaceStability.Stable
/*  12:    */ public class Reducer<KEYIN, VALUEIN, KEYOUT, VALUEOUT>
/*  13:    */ {
/*  14:    */   protected void setup(Reducer<KEYIN, VALUEIN, KEYOUT, VALUEOUT>.Context context)
/*  15:    */     throws IOException, InterruptedException
/*  16:    */   {}
/*  17:    */   
/*  18:    */   protected void reduce(KEYIN key, Iterable<VALUEIN> values, Reducer<KEYIN, VALUEIN, KEYOUT, VALUEOUT>.Context context)
/*  19:    */     throws IOException, InterruptedException
/*  20:    */   {
/*  21:149 */     for (VALUEIN value : values) {
/*  22:150 */       context.write(key, value);
/*  23:    */     }
/*  24:    */   }
/*  25:    */   
/*  26:    */   protected void cleanup(Reducer<KEYIN, VALUEIN, KEYOUT, VALUEOUT>.Context context)
/*  27:    */     throws IOException, InterruptedException
/*  28:    */   {}
/*  29:    */   
/*  30:    */   public void run(Reducer<KEYIN, VALUEIN, KEYOUT, VALUEOUT>.Context context)
/*  31:    */     throws IOException, InterruptedException
/*  32:    */   {
/*  33:168 */     setup(context);
/*  34:    */     try
/*  35:    */     {
/*  36:170 */       while (context.nextKey())
/*  37:    */       {
/*  38:171 */         reduce(context.getCurrentKey(), context.getValues(), context);
/*  39:    */         
/*  40:173 */         Iterator<VALUEIN> iter = context.getValues().iterator();
/*  41:174 */         if ((iter instanceof ReduceContext.ValueIterator)) {
/*  42:175 */           ((ReduceContext.ValueIterator)iter).resetBackupStore();
/*  43:    */         }
/*  44:    */       }
/*  45:    */     }
/*  46:    */     finally
/*  47:    */     {
/*  48:179 */       cleanup(context);
/*  49:    */     }
/*  50:    */   }
/*  51:    */   
/*  52:    */   public abstract class Context
/*  53:    */     implements ReduceContext<KEYIN, VALUEIN, KEYOUT, VALUEOUT>
/*  54:    */   {
/*  55:    */     public Context() {}
/*  56:    */   }
/*  57:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.Reducer
 * JD-Core Version:    0.7.0.1
 */