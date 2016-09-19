/*  1:   */ package org.apache.hadoop.mapreduce.util;
/*  2:   */ 
/*  3:   */ import java.util.Locale;
/*  4:   */ import java.util.ResourceBundle;
/*  5:   */ 
/*  6:   */ public class ResourceBundles
/*  7:   */ {
/*  8:   */   public static ResourceBundle getBundle(String bundleName)
/*  9:   */   {
/* 10:37 */     return ResourceBundle.getBundle(bundleName.replace('$', '_'), Locale.getDefault(), Thread.currentThread().getContextClassLoader());
/* 11:   */   }
/* 12:   */   
/* 13:   */   public static synchronized <T> T getValue(String bundleName, String key, String suffix, T defaultValue)
/* 14:   */   {
/* 15:   */     T value;
/* 16:   */     try
/* 17:   */     {
/* 18:56 */       ResourceBundle bundle = getBundle(bundleName);
/* 19:57 */       value = bundle.getObject(getLookupKey(key, suffix));
/* 20:   */     }
/* 21:   */     catch (Exception e)
/* 22:   */     {
/* 23:60 */       return defaultValue;
/* 24:   */     }
/* 25:62 */     return value == null ? defaultValue : value;
/* 26:   */   }
/* 27:   */   
/* 28:   */   private static String getLookupKey(String key, String suffix)
/* 29:   */   {
/* 30:66 */     if ((suffix == null) || (suffix.isEmpty())) {
/* 31:66 */       return key;
/* 32:   */     }
/* 33:67 */     return key + suffix;
/* 34:   */   }
/* 35:   */   
/* 36:   */   public static String getCounterGroupName(String group, String defaultValue)
/* 37:   */   {
/* 38:77 */     return (String)getValue(group, "CounterGroupName", "", defaultValue);
/* 39:   */   }
/* 40:   */   
/* 41:   */   public static String getCounterName(String group, String counter, String defaultValue)
/* 42:   */   {
/* 43:89 */     return (String)getValue(group, counter, ".name", defaultValue);
/* 44:   */   }
/* 45:   */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.util.ResourceBundles
 * JD-Core Version:    0.7.0.1
 */