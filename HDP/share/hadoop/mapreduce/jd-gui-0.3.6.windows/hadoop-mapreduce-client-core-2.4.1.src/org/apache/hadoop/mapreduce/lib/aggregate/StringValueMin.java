/*  1:   */ package org.apache.hadoop.mapreduce.lib.aggregate;
/*  2:   */ 
/*  3:   */ import java.util.ArrayList;
/*  4:   */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*  5:   */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*  6:   */ 
/*  7:   */ @InterfaceAudience.Public
/*  8:   */ @InterfaceStability.Stable
/*  9:   */ public class StringValueMin
/* 10:   */   implements ValueAggregator<String>
/* 11:   */ {
/* 12:35 */   String minVal = null;
/* 13:   */   
/* 14:   */   public StringValueMin()
/* 15:   */   {
/* 16:42 */     reset();
/* 17:   */   }
/* 18:   */   
/* 19:   */   public void addNextValue(Object val)
/* 20:   */   {
/* 21:53 */     String newVal = val.toString();
/* 22:54 */     if ((this.minVal == null) || (this.minVal.compareTo(newVal) > 0)) {
/* 23:55 */       this.minVal = newVal;
/* 24:   */     }
/* 25:   */   }
/* 26:   */   
/* 27:   */   public String getVal()
/* 28:   */   {
/* 29:64 */     return this.minVal;
/* 30:   */   }
/* 31:   */   
/* 32:   */   public String getReport()
/* 33:   */   {
/* 34:71 */     return this.minVal;
/* 35:   */   }
/* 36:   */   
/* 37:   */   public void reset()
/* 38:   */   {
/* 39:78 */     this.minVal = null;
/* 40:   */   }
/* 41:   */   
/* 42:   */   public ArrayList<String> getCombinerOutput()
/* 43:   */   {
/* 44:87 */     ArrayList<String> retv = new ArrayList(1);
/* 45:88 */     retv.add(this.minVal);
/* 46:89 */     return retv;
/* 47:   */   }
/* 48:   */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.lib.aggregate.StringValueMin
 * JD-Core Version:    0.7.0.1
 */