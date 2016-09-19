/*  1:   */ package org.apache.hadoop.mapreduce.lib.db;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import java.sql.SQLException;
/*  5:   */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*  6:   */ import org.apache.hadoop.classification.InterfaceStability.Evolving;
/*  7:   */ import org.apache.hadoop.conf.Configurable;
/*  8:   */ import org.apache.hadoop.conf.Configuration;
/*  9:   */ import org.apache.hadoop.io.LongWritable;
/* 10:   */ import org.apache.hadoop.mapreduce.RecordReader;
/* 11:   */ 
/* 12:   */ @InterfaceAudience.Public
/* 13:   */ @InterfaceStability.Evolving
/* 14:   */ public class OracleDataDrivenDBInputFormat<T extends DBWritable>
/* 15:   */   extends DataDrivenDBInputFormat<T>
/* 16:   */   implements Configurable
/* 17:   */ {
/* 18:   */   protected DBSplitter getSplitter(int sqlDataType)
/* 19:   */   {
/* 20:65 */     switch (sqlDataType)
/* 21:   */     {
/* 22:   */     case 91: 
/* 23:   */     case 92: 
/* 24:   */     case 93: 
/* 25:69 */       return new OracleDateSplitter();
/* 26:   */     }
/* 27:72 */     return super.getSplitter(sqlDataType);
/* 28:   */   }
/* 29:   */   
/* 30:   */   protected RecordReader<LongWritable, T> createDBRecordReader(DBInputFormat.DBInputSplit split, Configuration conf)
/* 31:   */     throws IOException
/* 32:   */   {
/* 33:80 */     DBConfiguration dbConf = getDBConf();
/* 34:   */     
/* 35:82 */     Class<T> inputClass = dbConf.getInputClass();
/* 36:   */     try
/* 37:   */     {
/* 38:86 */       return new OracleDataDrivenDBRecordReader(split, inputClass, conf, getConnection(), dbConf, dbConf.getInputConditions(), dbConf.getInputFieldNames(), dbConf.getInputTableName());
/* 39:   */     }
/* 40:   */     catch (SQLException ex)
/* 41:   */     {
/* 42:90 */       throw new IOException(ex.getMessage());
/* 43:   */     }
/* 44:   */   }
/* 45:   */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.lib.db.OracleDataDrivenDBInputFormat
 * JD-Core Version:    0.7.0.1
 */