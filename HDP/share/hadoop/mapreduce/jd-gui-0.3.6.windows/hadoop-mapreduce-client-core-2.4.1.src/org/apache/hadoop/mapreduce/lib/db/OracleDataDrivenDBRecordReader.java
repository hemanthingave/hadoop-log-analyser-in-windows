/*  1:   */ package org.apache.hadoop.mapreduce.lib.db;
/*  2:   */ 
/*  3:   */ import java.sql.Connection;
/*  4:   */ import java.sql.SQLException;
/*  5:   */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*  6:   */ import org.apache.hadoop.classification.InterfaceStability.Evolving;
/*  7:   */ import org.apache.hadoop.conf.Configuration;
/*  8:   */ 
/*  9:   */ @InterfaceAudience.Public
/* 10:   */ @InterfaceStability.Evolving
/* 11:   */ public class OracleDataDrivenDBRecordReader<T extends DBWritable>
/* 12:   */   extends DataDrivenDBRecordReader<T>
/* 13:   */ {
/* 14:   */   public OracleDataDrivenDBRecordReader(DBInputFormat.DBInputSplit split, Class<T> inputClass, Configuration conf, Connection conn, DBConfiguration dbConfig, String cond, String[] fields, String table)
/* 15:   */     throws SQLException
/* 16:   */   {
/* 17:43 */     super(split, inputClass, conf, conn, dbConfig, cond, fields, table, "ORACLE");
/* 18:   */     
/* 19:   */ 
/* 20:   */ 
/* 21:47 */     OracleDBRecordReader.setSessionTimeZone(conf, conn);
/* 22:   */   }
/* 23:   */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.lib.db.OracleDataDrivenDBRecordReader
 * JD-Core Version:    0.7.0.1
 */