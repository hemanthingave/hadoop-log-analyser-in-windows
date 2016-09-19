/*  1:   */ package org.apache.hadoop.mapreduce.lib.db;
/*  2:   */ 
/*  3:   */ import java.sql.Connection;
/*  4:   */ import java.sql.PreparedStatement;
/*  5:   */ import java.sql.ResultSet;
/*  6:   */ import java.sql.SQLException;
/*  7:   */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*  8:   */ import org.apache.hadoop.classification.InterfaceStability.Evolving;
/*  9:   */ import org.apache.hadoop.conf.Configuration;
/* 10:   */ 
/* 11:   */ @InterfaceAudience.Public
/* 12:   */ @InterfaceStability.Evolving
/* 13:   */ public class MySQLDataDrivenDBRecordReader<T extends DBWritable>
/* 14:   */   extends DataDrivenDBRecordReader<T>
/* 15:   */ {
/* 16:   */   public MySQLDataDrivenDBRecordReader(DBInputFormat.DBInputSplit split, Class<T> inputClass, Configuration conf, Connection conn, DBConfiguration dbConfig, String cond, String[] fields, String table)
/* 17:   */     throws SQLException
/* 18:   */   {
/* 19:41 */     super(split, inputClass, conf, conn, dbConfig, cond, fields, table, "MYSQL");
/* 20:   */   }
/* 21:   */   
/* 22:   */   protected ResultSet executeQuery(String query)
/* 23:   */     throws SQLException
/* 24:   */   {
/* 25:46 */     this.statement = getConnection().prepareStatement(query, 1003, 1007);
/* 26:   */     
/* 27:48 */     this.statement.setFetchSize(-2147483648);
/* 28:49 */     return this.statement.executeQuery();
/* 29:   */   }
/* 30:   */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.lib.db.MySQLDataDrivenDBRecordReader
 * JD-Core Version:    0.7.0.1
 */