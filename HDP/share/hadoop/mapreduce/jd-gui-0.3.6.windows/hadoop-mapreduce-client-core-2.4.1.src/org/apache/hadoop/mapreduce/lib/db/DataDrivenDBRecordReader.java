/*   1:    */ package org.apache.hadoop.mapreduce.lib.db;
/*   2:    */ 
/*   3:    */ import java.sql.Connection;
/*   4:    */ import java.sql.SQLException;
/*   5:    */ import org.apache.commons.logging.Log;
/*   6:    */ import org.apache.commons.logging.LogFactory;
/*   7:    */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*   8:    */ import org.apache.hadoop.classification.InterfaceStability.Evolving;
/*   9:    */ import org.apache.hadoop.conf.Configuration;
/*  10:    */ 
/*  11:    */ @InterfaceAudience.Public
/*  12:    */ @InterfaceStability.Evolving
/*  13:    */ public class DataDrivenDBRecordReader<T extends DBWritable>
/*  14:    */   extends DBRecordReader<T>
/*  15:    */ {
/*  16: 59 */   private static final Log LOG = LogFactory.getLog(DataDrivenDBRecordReader.class);
/*  17:    */   private String dbProductName;
/*  18:    */   
/*  19:    */   public DataDrivenDBRecordReader(DBInputFormat.DBInputSplit split, Class<T> inputClass, Configuration conf, Connection conn, DBConfiguration dbConfig, String cond, String[] fields, String table, String dbProduct)
/*  20:    */     throws SQLException
/*  21:    */   {
/*  22: 71 */     super(split, inputClass, conf, conn, dbConfig, cond, fields, table);
/*  23: 72 */     this.dbProductName = dbProduct;
/*  24:    */   }
/*  25:    */   
/*  26:    */   protected String getSelectQuery()
/*  27:    */   {
/*  28: 79 */     StringBuilder query = new StringBuilder();
/*  29: 80 */     DataDrivenDBInputFormat.DataDrivenDBInputSplit dataSplit = (DataDrivenDBInputFormat.DataDrivenDBInputSplit)getSplit();
/*  30:    */     
/*  31: 82 */     DBConfiguration dbConf = getDBConf();
/*  32: 83 */     String[] fieldNames = getFieldNames();
/*  33: 84 */     String tableName = getTableName();
/*  34: 85 */     String conditions = getConditions();
/*  35:    */     
/*  36:    */ 
/*  37:    */ 
/*  38: 89 */     StringBuilder conditionClauses = new StringBuilder();
/*  39: 90 */     conditionClauses.append("( ").append(dataSplit.getLowerClause());
/*  40: 91 */     conditionClauses.append(" ) AND ( ").append(dataSplit.getUpperClause());
/*  41: 92 */     conditionClauses.append(" )");
/*  42: 94 */     if (dbConf.getInputQuery() == null)
/*  43:    */     {
/*  44: 96 */       query.append("SELECT ");
/*  45: 98 */       for (int i = 0; i < fieldNames.length; i++)
/*  46:    */       {
/*  47: 99 */         query.append(fieldNames[i]);
/*  48:100 */         if (i != fieldNames.length - 1) {
/*  49:101 */           query.append(", ");
/*  50:    */         }
/*  51:    */       }
/*  52:105 */       query.append(" FROM ").append(tableName);
/*  53:106 */       if (!this.dbProductName.startsWith("ORACLE")) {
/*  54:109 */         query.append(" AS ").append(tableName);
/*  55:    */       }
/*  56:111 */       query.append(" WHERE ");
/*  57:112 */       if ((conditions != null) && (conditions.length() > 0)) {
/*  58:114 */         query.append("( ").append(conditions).append(" ) AND ");
/*  59:    */       }
/*  60:118 */       query.append(conditionClauses.toString());
/*  61:    */     }
/*  62:    */     else
/*  63:    */     {
/*  64:122 */       String inputQuery = dbConf.getInputQuery();
/*  65:123 */       if (inputQuery.indexOf("$CONDITIONS") == -1) {
/*  66:124 */         LOG.error("Could not find the clause substitution token $CONDITIONS in the query: [" + inputQuery + "]. Parallel splits may not work correctly.");
/*  67:    */       }
/*  68:129 */       query.append(inputQuery.replace("$CONDITIONS", conditionClauses.toString()));
/*  69:    */     }
/*  70:133 */     LOG.debug("Using query: " + query.toString());
/*  71:    */     
/*  72:135 */     return query.toString();
/*  73:    */   }
/*  74:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.lib.db.DataDrivenDBRecordReader
 * JD-Core Version:    0.7.0.1
 */