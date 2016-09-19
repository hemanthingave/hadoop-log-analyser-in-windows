/*   1:    */ package org.apache.hadoop.mapreduce.lib.db;
/*   2:    */ 
/*   3:    */ import java.sql.ResultSet;
/*   4:    */ import java.sql.ResultSetMetaData;
/*   5:    */ import java.sql.SQLException;
/*   6:    */ import java.sql.Time;
/*   7:    */ import java.sql.Timestamp;
/*   8:    */ import java.util.ArrayList;
/*   9:    */ import java.util.List;
/*  10:    */ import org.apache.commons.logging.Log;
/*  11:    */ import org.apache.commons.logging.LogFactory;
/*  12:    */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*  13:    */ import org.apache.hadoop.classification.InterfaceStability.Evolving;
/*  14:    */ import org.apache.hadoop.conf.Configuration;
/*  15:    */ import org.apache.hadoop.mapreduce.InputSplit;
/*  16:    */ 
/*  17:    */ @InterfaceAudience.Public
/*  18:    */ @InterfaceStability.Evolving
/*  19:    */ public class DateSplitter
/*  20:    */   extends IntegerSplitter
/*  21:    */ {
/*  22: 48 */   private static final Log LOG = LogFactory.getLog(DateSplitter.class);
/*  23:    */   
/*  24:    */   public List<InputSplit> split(Configuration conf, ResultSet results, String colName)
/*  25:    */     throws SQLException
/*  26:    */   {
/*  27: 56 */     int sqlDataType = results.getMetaData().getColumnType(1);
/*  28: 57 */     long minVal = resultSetColToLong(results, 1, sqlDataType);
/*  29: 58 */     long maxVal = resultSetColToLong(results, 2, sqlDataType);
/*  30:    */     
/*  31: 60 */     String lowClausePrefix = colName + " >= ";
/*  32: 61 */     String highClausePrefix = colName + " < ";
/*  33:    */     
/*  34: 63 */     int numSplits = conf.getInt("mapreduce.job.maps", 1);
/*  35: 64 */     if (numSplits < 1) {
/*  36: 65 */       numSplits = 1;
/*  37:    */     }
/*  38: 68 */     if ((minVal == -9223372036854775808L) && (maxVal == -9223372036854775808L))
/*  39:    */     {
/*  40: 70 */       List<InputSplit> splits = new ArrayList();
/*  41: 71 */       splits.add(new DataDrivenDBInputFormat.DataDrivenDBInputSplit(colName + " IS NULL", colName + " IS NULL"));
/*  42:    */       
/*  43: 73 */       return splits;
/*  44:    */     }
/*  45: 77 */     List<Long> splitPoints = split(numSplits, minVal, maxVal);
/*  46: 78 */     List<InputSplit> splits = new ArrayList();
/*  47:    */     
/*  48:    */ 
/*  49: 81 */     long start = ((Long)splitPoints.get(0)).longValue();
/*  50: 82 */     java.util.Date startDate = longToDate(start, sqlDataType);
/*  51: 83 */     if (sqlDataType == 93) {
/*  52:    */       try
/*  53:    */       {
/*  54: 86 */         ((Timestamp)startDate).setNanos(results.getTimestamp(1).getNanos());
/*  55:    */       }
/*  56:    */       catch (NullPointerException npe) {}
/*  57:    */     }
/*  58: 92 */     for (int i = 1; i < splitPoints.size(); i++)
/*  59:    */     {
/*  60: 93 */       long end = ((Long)splitPoints.get(i)).longValue();
/*  61: 94 */       java.util.Date endDate = longToDate(end, sqlDataType);
/*  62: 96 */       if (i == splitPoints.size() - 1)
/*  63:    */       {
/*  64: 97 */         if (sqlDataType == 93) {
/*  65:    */           try
/*  66:    */           {
/*  67:100 */             ((Timestamp)endDate).setNanos(results.getTimestamp(2).getNanos());
/*  68:    */           }
/*  69:    */           catch (NullPointerException npe) {}
/*  70:    */         }
/*  71:106 */         splits.add(new DataDrivenDBInputFormat.DataDrivenDBInputSplit(lowClausePrefix + dateToString(startDate), colName + " <= " + dateToString(endDate)));
/*  72:    */       }
/*  73:    */       else
/*  74:    */       {
/*  75:111 */         splits.add(new DataDrivenDBInputFormat.DataDrivenDBInputSplit(lowClausePrefix + dateToString(startDate), highClausePrefix + dateToString(endDate)));
/*  76:    */       }
/*  77:116 */       start = end;
/*  78:117 */       startDate = endDate;
/*  79:    */     }
/*  80:120 */     if ((minVal == -9223372036854775808L) || (maxVal == -9223372036854775808L)) {
/*  81:122 */       splits.add(new DataDrivenDBInputFormat.DataDrivenDBInputSplit(colName + " IS NULL", colName + " IS NULL"));
/*  82:    */     }
/*  83:126 */     return splits;
/*  84:    */   }
/*  85:    */   
/*  86:    */   private long resultSetColToLong(ResultSet rs, int colNum, int sqlDataType)
/*  87:    */     throws SQLException
/*  88:    */   {
/*  89:    */     try
/*  90:    */     {
/*  91:137 */       switch (sqlDataType)
/*  92:    */       {
/*  93:    */       case 91: 
/*  94:139 */         return rs.getDate(colNum).getTime();
/*  95:    */       case 92: 
/*  96:141 */         return rs.getTime(colNum).getTime();
/*  97:    */       case 93: 
/*  98:143 */         return rs.getTimestamp(colNum).getTime();
/*  99:    */       }
/* 100:145 */       throw new SQLException("Not a date-type field");
/* 101:    */     }
/* 102:    */     catch (NullPointerException npe)
/* 103:    */     {
/* 104:149 */       LOG.warn("Encountered a NULL date in the split column. Splits may be poorly balanced.");
/* 105:    */     }
/* 106:150 */     return -9223372036854775808L;
/* 107:    */   }
/* 108:    */   
/* 109:    */   private java.util.Date longToDate(long val, int sqlDataType)
/* 110:    */   {
/* 111:156 */     switch (sqlDataType)
/* 112:    */     {
/* 113:    */     case 91: 
/* 114:158 */       return new java.sql.Date(val);
/* 115:    */     case 92: 
/* 116:160 */       return new Time(val);
/* 117:    */     case 93: 
/* 118:162 */       return new Timestamp(val);
/* 119:    */     }
/* 120:164 */     return null;
/* 121:    */   }
/* 122:    */   
/* 123:    */   protected String dateToString(java.util.Date d)
/* 124:    */   {
/* 125:176 */     return "'" + d.toString() + "'";
/* 126:    */   }
/* 127:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.lib.db.DateSplitter
 * JD-Core Version:    0.7.0.1
 */