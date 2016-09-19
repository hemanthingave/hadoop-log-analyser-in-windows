/*   1:    */ package org.apache.hadoop.mapreduce.lib.aggregate;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.Iterator;
/*   5:    */ import java.util.Set;
/*   6:    */ import java.util.TreeMap;
/*   7:    */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*   8:    */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*   9:    */ 
/*  10:    */ @InterfaceAudience.Public
/*  11:    */ @InterfaceStability.Stable
/*  12:    */ public class UniqValueCount
/*  13:    */   implements ValueAggregator<Object>
/*  14:    */ {
/*  15:    */   public static final String MAX_NUM_UNIQUE_VALUES = "mapreduce.aggregate.max.num.unique.values";
/*  16: 39 */   private TreeMap<Object, Object> uniqItems = null;
/*  17: 41 */   private long numItems = 0L;
/*  18: 43 */   private long maxNumItems = 9223372036854775807L;
/*  19:    */   
/*  20:    */   public UniqValueCount()
/*  21:    */   {
/*  22: 50 */     this(9223372036854775807L);
/*  23:    */   }
/*  24:    */   
/*  25:    */   public UniqValueCount(long maxNum)
/*  26:    */   {
/*  27: 59 */     this.uniqItems = new TreeMap();
/*  28: 60 */     this.numItems = 0L;
/*  29: 61 */     this.maxNumItems = 9223372036854775807L;
/*  30: 62 */     if (maxNum > 0L) {
/*  31: 63 */       this.maxNumItems = maxNum;
/*  32:    */     }
/*  33:    */   }
/*  34:    */   
/*  35:    */   public long setMaxItems(long n)
/*  36:    */   {
/*  37: 73 */     if (n >= this.numItems) {
/*  38: 74 */       this.maxNumItems = n;
/*  39: 75 */     } else if (this.maxNumItems >= this.numItems) {
/*  40: 76 */       this.maxNumItems = this.numItems;
/*  41:    */     }
/*  42: 78 */     return this.maxNumItems;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public void addNextValue(Object val)
/*  46:    */   {
/*  47: 89 */     if (this.numItems <= this.maxNumItems)
/*  48:    */     {
/*  49: 90 */       this.uniqItems.put(val.toString(), "1");
/*  50: 91 */       this.numItems = this.uniqItems.size();
/*  51:    */     }
/*  52:    */   }
/*  53:    */   
/*  54:    */   public String getReport()
/*  55:    */   {
/*  56: 99 */     return "" + this.uniqItems.size();
/*  57:    */   }
/*  58:    */   
/*  59:    */   public Set<Object> getUniqueItems()
/*  60:    */   {
/*  61:107 */     return this.uniqItems.keySet();
/*  62:    */   }
/*  63:    */   
/*  64:    */   public void reset()
/*  65:    */   {
/*  66:114 */     this.uniqItems = new TreeMap();
/*  67:    */   }
/*  68:    */   
/*  69:    */   public ArrayList<Object> getCombinerOutput()
/*  70:    */   {
/*  71:122 */     Object key = null;
/*  72:123 */     Iterator<Object> iter = this.uniqItems.keySet().iterator();
/*  73:124 */     ArrayList<Object> retv = new ArrayList();
/*  74:126 */     while (iter.hasNext())
/*  75:    */     {
/*  76:127 */       key = iter.next();
/*  77:128 */       retv.add(key);
/*  78:    */     }
/*  79:130 */     return retv;
/*  80:    */   }
/*  81:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.lib.aggregate.UniqValueCount
 * JD-Core Version:    0.7.0.1
 */