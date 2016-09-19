/*   1:    */ package org.apache.hadoop.mapreduce.lib.db;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.lang.reflect.Method;
/*   5:    */ import java.sql.Connection;
/*   6:    */ import java.sql.SQLException;
/*   7:    */ import org.apache.commons.logging.Log;
/*   8:    */ import org.apache.commons.logging.LogFactory;
/*   9:    */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*  10:    */ import org.apache.hadoop.classification.InterfaceStability.Evolving;
/*  11:    */ import org.apache.hadoop.conf.Configuration;
/*  12:    */ 
/*  13:    */ @InterfaceAudience.Public
/*  14:    */ @InterfaceStability.Evolving
/*  15:    */ public class OracleDBRecordReader<T extends DBWritable>
/*  16:    */   extends DBRecordReader<T>
/*  17:    */ {
/*  18:    */   public static final String SESSION_TIMEZONE_KEY = "oracle.sessionTimeZone";
/*  19: 42 */   private static final Log LOG = LogFactory.getLog(OracleDBRecordReader.class);
/*  20:    */   
/*  21:    */   public OracleDBRecordReader(DBInputFormat.DBInputSplit split, Class<T> inputClass, Configuration conf, Connection conn, DBConfiguration dbConfig, String cond, String[] fields, String table)
/*  22:    */     throws SQLException
/*  23:    */   {
/*  24: 47 */     super(split, inputClass, conf, conn, dbConfig, cond, fields, table);
/*  25: 48 */     setSessionTimeZone(conf, conn);
/*  26:    */   }
/*  27:    */   
/*  28:    */   protected String getSelectQuery()
/*  29:    */   {
/*  30: 53 */     StringBuilder query = new StringBuilder();
/*  31: 54 */     DBConfiguration dbConf = getDBConf();
/*  32: 55 */     String conditions = getConditions();
/*  33: 56 */     String tableName = getTableName();
/*  34: 57 */     String[] fieldNames = getFieldNames();
/*  35: 60 */     if (dbConf.getInputQuery() == null)
/*  36:    */     {
/*  37: 61 */       query.append("SELECT ");
/*  38: 63 */       for (int i = 0; i < fieldNames.length; i++)
/*  39:    */       {
/*  40: 64 */         query.append(fieldNames[i]);
/*  41: 65 */         if (i != fieldNames.length - 1) {
/*  42: 66 */           query.append(", ");
/*  43:    */         }
/*  44:    */       }
/*  45: 70 */       query.append(" FROM ").append(tableName);
/*  46: 71 */       if ((conditions != null) && (conditions.length() > 0)) {
/*  47: 72 */         query.append(" WHERE ").append(conditions);
/*  48:    */       }
/*  49: 73 */       String orderBy = dbConf.getInputOrderBy();
/*  50: 74 */       if ((orderBy != null) && (orderBy.length() > 0)) {
/*  51: 75 */         query.append(" ORDER BY ").append(orderBy);
/*  52:    */       }
/*  53:    */     }
/*  54:    */     else
/*  55:    */     {
/*  56: 79 */       query.append(dbConf.getInputQuery());
/*  57:    */     }
/*  58:    */     try
/*  59:    */     {
/*  60: 83 */       DBInputFormat.DBInputSplit split = getSplit();
/*  61: 84 */       if ((split.getLength() > 0L) && (split.getStart() > 0L))
/*  62:    */       {
/*  63: 85 */         String querystring = query.toString();
/*  64:    */         
/*  65: 87 */         query = new StringBuilder();
/*  66: 88 */         query.append("SELECT * FROM (SELECT a.*,ROWNUM dbif_rno FROM ( ");
/*  67: 89 */         query.append(querystring);
/*  68: 90 */         query.append(" ) a WHERE rownum <= ").append(split.getStart());
/*  69: 91 */         query.append(" + ").append(split.getLength());
/*  70: 92 */         query.append(" ) WHERE dbif_rno >= ").append(split.getStart());
/*  71:    */       }
/*  72:    */     }
/*  73:    */     catch (IOException ex) {}
/*  74: 98 */     return query.toString();
/*  75:    */   }
/*  76:    */   
/*  77:    */   public static void setSessionTimeZone(Configuration conf, Connection conn)
/*  78:    */     throws SQLException
/*  79:    */   {
/*  80:    */     Method method;
/*  81:    */     try
/*  82:    */     {
/*  83:114 */       method = conn.getClass().getMethod("setSessionTimeZone", new Class[] { String.class });
/*  84:    */     }
/*  85:    */     catch (Exception ex)
/*  86:    */     {
/*  87:117 */       LOG.error("Could not find method setSessionTimeZone in " + conn.getClass().getName(), ex);
/*  88:    */       
/*  89:119 */       throw new SQLException(ex);
/*  90:    */     }
/*  91:126 */     String clientTimeZone = conf.get("oracle.sessionTimeZone", "GMT");
/*  92:    */     try
/*  93:    */     {
/*  94:128 */       method.setAccessible(true);
/*  95:129 */       method.invoke(conn, new Object[] { clientTimeZone });
/*  96:130 */       LOG.info("Time zone has been set to " + clientTimeZone);
/*  97:    */     }
/*  98:    */     catch (Exception ex)
/*  99:    */     {
/* 100:132 */       LOG.warn("Time zone " + clientTimeZone + " could not be set on Oracle database.");
/* 101:    */       
/* 102:134 */       LOG.warn("Setting default time zone: GMT");
/* 103:    */       try
/* 104:    */       {
/* 105:137 */         method.invoke(conn, new Object[] { "GMT" });
/* 106:    */       }
/* 107:    */       catch (Exception ex2)
/* 108:    */       {
/* 109:139 */         LOG.error("Could not set time zone for oracle connection", ex2);
/* 110:    */         
/* 111:141 */         throw new SQLException(ex);
/* 112:    */       }
/* 113:    */     }
/* 114:    */   }
/* 115:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.lib.db.OracleDBRecordReader
 * JD-Core Version:    0.7.0.1
 */