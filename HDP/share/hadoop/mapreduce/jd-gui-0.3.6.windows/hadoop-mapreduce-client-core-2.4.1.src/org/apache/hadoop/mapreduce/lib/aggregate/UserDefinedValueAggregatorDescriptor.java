/*   1:    */ package org.apache.hadoop.mapreduce.lib.aggregate;
/*   2:    */ 
/*   3:    */ import java.lang.reflect.Constructor;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import java.util.Map.Entry;
/*   6:    */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*   7:    */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*   8:    */ import org.apache.hadoop.conf.Configuration;
/*   9:    */ import org.apache.hadoop.io.Text;
/*  10:    */ 
/*  11:    */ @InterfaceAudience.Public
/*  12:    */ @InterfaceStability.Stable
/*  13:    */ public class UserDefinedValueAggregatorDescriptor
/*  14:    */   implements ValueAggregatorDescriptor
/*  15:    */ {
/*  16:    */   private String className;
/*  17: 45 */   protected ValueAggregatorDescriptor theAggregatorDescriptor = null;
/*  18: 47 */   private static final Class<?>[] argArray = new Class[0];
/*  19:    */   
/*  20:    */   public static Object createInstance(String className)
/*  21:    */   {
/*  22: 55 */     Object retv = null;
/*  23:    */     try
/*  24:    */     {
/*  25: 57 */       ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
/*  26: 58 */       Class<?> theFilterClass = Class.forName(className, true, classLoader);
/*  27: 59 */       Constructor<?> meth = theFilterClass.getDeclaredConstructor(argArray);
/*  28: 60 */       meth.setAccessible(true);
/*  29: 61 */       retv = meth.newInstance(new Object[0]);
/*  30:    */     }
/*  31:    */     catch (Exception e)
/*  32:    */     {
/*  33: 63 */       throw new RuntimeException(e);
/*  34:    */     }
/*  35: 65 */     return retv;
/*  36:    */   }
/*  37:    */   
/*  38:    */   private void createAggregator(Configuration conf)
/*  39:    */   {
/*  40: 69 */     if (this.theAggregatorDescriptor == null)
/*  41:    */     {
/*  42: 70 */       this.theAggregatorDescriptor = ((ValueAggregatorDescriptor)createInstance(this.className));
/*  43:    */       
/*  44: 72 */       this.theAggregatorDescriptor.configure(conf);
/*  45:    */     }
/*  46:    */   }
/*  47:    */   
/*  48:    */   public UserDefinedValueAggregatorDescriptor(String className, Configuration conf)
/*  49:    */   {
/*  50: 83 */     this.className = className;
/*  51: 84 */     createAggregator(conf);
/*  52:    */   }
/*  53:    */   
/*  54:    */   public ArrayList<Map.Entry<Text, Text>> generateKeyValPairs(Object key, Object val)
/*  55:    */   {
/*  56:101 */     ArrayList<Map.Entry<Text, Text>> retv = new ArrayList();
/*  57:102 */     if (this.theAggregatorDescriptor != null) {
/*  58:103 */       retv = this.theAggregatorDescriptor.generateKeyValPairs(key, val);
/*  59:    */     }
/*  60:105 */     return retv;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public String toString()
/*  64:    */   {
/*  65:112 */     return "UserDefinedValueAggregatorDescriptor with class name:\t" + this.className;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public void configure(Configuration conf) {}
/*  69:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.lib.aggregate.UserDefinedValueAggregatorDescriptor
 * JD-Core Version:    0.7.0.1
 */