/*  1:   */ package org.apache.hadoop.mapreduce.lib.aggregate;
/*  2:   */ 
/*  3:   */ import java.util.ArrayList;
/*  4:   */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*  5:   */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*  6:   */ 
/*  7:   */ @InterfaceAudience.Public
/*  8:   */ @InterfaceStability.Stable
/*  9:   */ public class DoubleValueSum
/* 10:   */   implements ValueAggregator<String>
/* 11:   */ {
/* 12:36 */   double sum = 0.0D;
/* 13:   */   
/* 14:   */   public DoubleValueSum()
/* 15:   */   {
/* 16:43 */     reset();
/* 17:   */   }
/* 18:   */   
/* 19:   */   public void addNextValue(Object val)
/* 20:   */   {
/* 21:54 */     this.sum += Double.parseDouble(val.toString());
/* 22:   */   }
/* 23:   */   
/* 24:   */   public void addNextValue(double val)
/* 25:   */   {
/* 26:65 */     this.sum += val;
/* 27:   */   }
/* 28:   */   
/* 29:   */   public String getReport()
/* 30:   */   {
/* 31:72 */     return "" + this.sum;
/* 32:   */   }
/* 33:   */   
/* 34:   */   public double getSum()
/* 35:   */   {
/* 36:79 */     return this.sum;
/* 37:   */   }
/* 38:   */   
/* 39:   */   public void reset()
/* 40:   */   {
/* 41:86 */     this.sum = 0.0D;
/* 42:   */   }
/* 43:   */   
/* 44:   */   public ArrayList<String> getCombinerOutput()
/* 45:   */   {
/* 46:95 */     ArrayList<String> retv = new ArrayList(1);
/* 47:96 */     retv.add("" + this.sum);
/* 48:97 */     return retv;
/* 49:   */   }
/* 50:   */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.lib.aggregate.DoubleValueSum
 * JD-Core Version:    0.7.0.1
 */