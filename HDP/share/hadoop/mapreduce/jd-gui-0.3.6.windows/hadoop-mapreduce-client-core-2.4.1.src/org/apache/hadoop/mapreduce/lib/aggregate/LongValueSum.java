/*  1:   */ package org.apache.hadoop.mapreduce.lib.aggregate;
/*  2:   */ 
/*  3:   */ import java.util.ArrayList;
/*  4:   */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*  5:   */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*  6:   */ 
/*  7:   */ @InterfaceAudience.Public
/*  8:   */ @InterfaceStability.Stable
/*  9:   */ public class LongValueSum
/* 10:   */   implements ValueAggregator<String>
/* 11:   */ {
/* 12:35 */   long sum = 0L;
/* 13:   */   
/* 14:   */   public LongValueSum()
/* 15:   */   {
/* 16:42 */     reset();
/* 17:   */   }
/* 18:   */   
/* 19:   */   public void addNextValue(Object val)
/* 20:   */   {
/* 21:53 */     this.sum += Long.parseLong(val.toString());
/* 22:   */   }
/* 23:   */   
/* 24:   */   public void addNextValue(long val)
/* 25:   */   {
/* 26:64 */     this.sum += val;
/* 27:   */   }
/* 28:   */   
/* 29:   */   public long getSum()
/* 30:   */   {
/* 31:71 */     return this.sum;
/* 32:   */   }
/* 33:   */   
/* 34:   */   public String getReport()
/* 35:   */   {
/* 36:78 */     return "" + this.sum;
/* 37:   */   }
/* 38:   */   
/* 39:   */   public void reset()
/* 40:   */   {
/* 41:85 */     this.sum = 0L;
/* 42:   */   }
/* 43:   */   
/* 44:   */   public ArrayList<String> getCombinerOutput()
/* 45:   */   {
/* 46:94 */     ArrayList<String> retv = new ArrayList(1);
/* 47:95 */     retv.add("" + this.sum);
/* 48:96 */     return retv;
/* 49:   */   }
/* 50:   */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.lib.aggregate.LongValueSum
 * JD-Core Version:    0.7.0.1
 */