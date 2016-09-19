/*   1:    */ package org.apache.hadoop.mapred.lib.db;
/*   2:    */ 
/*   3:    */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*   4:    */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*   5:    */ import org.apache.hadoop.mapred.JobConf;
/*   6:    */ 
/*   7:    */ @InterfaceAudience.Public
/*   8:    */ @InterfaceStability.Stable
/*   9:    */ public class DBConfiguration
/*  10:    */   extends org.apache.hadoop.mapreduce.lib.db.DBConfiguration
/*  11:    */ {
/*  12:    */   public static final String DRIVER_CLASS_PROPERTY = "mapreduce.jdbc.driver.class";
/*  13:    */   public static final String URL_PROPERTY = "mapreduce.jdbc.url";
/*  14:    */   public static final String USERNAME_PROPERTY = "mapreduce.jdbc.username";
/*  15:    */   public static final String PASSWORD_PROPERTY = "mapreduce.jdbc.password";
/*  16:    */   public static final String INPUT_TABLE_NAME_PROPERTY = "mapreduce.jdbc.input.table.name";
/*  17:    */   public static final String INPUT_FIELD_NAMES_PROPERTY = "mapreduce.jdbc.input.field.names";
/*  18:    */   public static final String INPUT_CONDITIONS_PROPERTY = "mapreduce.jdbc.input.conditions";
/*  19:    */   public static final String INPUT_ORDER_BY_PROPERTY = "mapreduce.jdbc.input.orderby";
/*  20:    */   public static final String INPUT_QUERY = "mapreduce.jdbc.input.query";
/*  21:    */   public static final String INPUT_COUNT_QUERY = "mapreduce.jdbc.input.count.query";
/*  22:    */   public static final String INPUT_CLASS_PROPERTY = "mapreduce.jdbc.input.class";
/*  23:    */   public static final String OUTPUT_TABLE_NAME_PROPERTY = "mapreduce.jdbc.output.table.name";
/*  24:    */   public static final String OUTPUT_FIELD_NAMES_PROPERTY = "mapreduce.jdbc.output.field.names";
/*  25:    */   public static final String OUTPUT_FIELD_COUNT_PROPERTY = "mapreduce.jdbc.output.field.count";
/*  26:    */   
/*  27:    */   public static void configureDB(JobConf job, String driverClass, String dbUrl, String userName, String passwd)
/*  28:    */   {
/*  29: 97 */     job.set("mapreduce.jdbc.driver.class", driverClass);
/*  30: 98 */     job.set("mapreduce.jdbc.url", dbUrl);
/*  31: 99 */     if (userName != null) {
/*  32:100 */       job.set("mapreduce.jdbc.username", userName);
/*  33:    */     }
/*  34:101 */     if (passwd != null) {
/*  35:102 */       job.set("mapreduce.jdbc.password", passwd);
/*  36:    */     }
/*  37:    */   }
/*  38:    */   
/*  39:    */   public static void configureDB(JobConf job, String driverClass, String dbUrl)
/*  40:    */   {
/*  41:112 */     configureDB(job, driverClass, dbUrl, null, null);
/*  42:    */   }
/*  43:    */   
/*  44:    */   DBConfiguration(JobConf job)
/*  45:    */   {
/*  46:116 */     super(job);
/*  47:    */   }
/*  48:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.lib.db.DBConfiguration
 * JD-Core Version:    0.7.0.1
 */