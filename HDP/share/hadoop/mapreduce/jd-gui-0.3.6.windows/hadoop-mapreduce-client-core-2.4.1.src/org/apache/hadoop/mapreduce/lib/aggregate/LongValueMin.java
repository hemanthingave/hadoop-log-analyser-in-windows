/*   1:    */ package org.apache.hadoop.mapreduce.lib.aggregate;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*   5:    */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*   6:    */ 
/*   7:    */ @InterfaceAudience.Public
/*   8:    */ @InterfaceStability.Stable
/*   9:    */ public class LongValueMin
/*  10:    */   implements ValueAggregator<String>
/*  11:    */ {
/*  12: 35 */   long minVal = 9223372036854775807L;
/*  13:    */   
/*  14:    */   public LongValueMin()
/*  15:    */   {
/*  16: 42 */     reset();
/*  17:    */   }
/*  18:    */   
/*  19:    */   public void addNextValue(Object val)
/*  20:    */   {
/*  21: 53 */     long newVal = Long.parseLong(val.toString());
/*  22: 54 */     if (this.minVal > newVal) {
/*  23: 55 */       this.minVal = newVal;
/*  24:    */     }
/*  25:    */   }
/*  26:    */   
/*  27:    */   public void addNextValue(long newVal)
/*  28:    */   {
/*  29: 67 */     if (this.minVal > newVal) {
/*  30: 68 */       this.minVal = newVal;
/*  31:    */     }
/*  32:    */   }
/*  33:    */   
/*  34:    */   public long getVal()
/*  35:    */   {
/*  36: 76 */     return this.minVal;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public String getReport()
/*  40:    */   {
/*  41: 83 */     return "" + this.minVal;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public void reset()
/*  45:    */   {
/*  46: 90 */     this.minVal = 9223372036854775807L;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public ArrayList<String> getCombinerOutput()
/*  50:    */   {
/*  51: 99 */     ArrayList<String> retv = new ArrayList(1);
/*  52:100 */     retv.add("" + this.minVal);
/*  53:101 */     return retv;
/*  54:    */   }
/*  55:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.lib.aggregate.LongValueMin
 * JD-Core Version:    0.7.0.1
 */