/*   1:    */ package org.apache.hadoop.mapreduce.lib.db;
/*   2:    */ 
/*   3:    */ import java.sql.ResultSet;
/*   4:    */ import java.sql.SQLException;
/*   5:    */ import java.util.ArrayList;
/*   6:    */ import java.util.List;
/*   7:    */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*   8:    */ import org.apache.hadoop.classification.InterfaceStability.Evolving;
/*   9:    */ import org.apache.hadoop.conf.Configuration;
/*  10:    */ import org.apache.hadoop.mapreduce.InputSplit;
/*  11:    */ 
/*  12:    */ @InterfaceAudience.Public
/*  13:    */ @InterfaceStability.Evolving
/*  14:    */ public class IntegerSplitter
/*  15:    */   implements DBSplitter
/*  16:    */ {
/*  17:    */   public List<InputSplit> split(Configuration conf, ResultSet results, String colName)
/*  18:    */     throws SQLException
/*  19:    */   {
/*  20: 41 */     long minVal = results.getLong(1);
/*  21: 42 */     long maxVal = results.getLong(2);
/*  22:    */     
/*  23: 44 */     String lowClausePrefix = colName + " >= ";
/*  24: 45 */     String highClausePrefix = colName + " < ";
/*  25:    */     
/*  26: 47 */     int numSplits = conf.getInt("mapreduce.job.maps", 1);
/*  27: 48 */     if (numSplits < 1) {
/*  28: 49 */       numSplits = 1;
/*  29:    */     }
/*  30: 52 */     if ((results.getString(1) == null) && (results.getString(2) == null))
/*  31:    */     {
/*  32: 54 */       List<InputSplit> splits = new ArrayList();
/*  33: 55 */       splits.add(new DataDrivenDBInputFormat.DataDrivenDBInputSplit(colName + " IS NULL", colName + " IS NULL"));
/*  34:    */       
/*  35: 57 */       return splits;
/*  36:    */     }
/*  37: 61 */     List<Long> splitPoints = split(numSplits, minVal, maxVal);
/*  38: 62 */     List<InputSplit> splits = new ArrayList();
/*  39:    */     
/*  40:    */ 
/*  41: 65 */     long start = ((Long)splitPoints.get(0)).longValue();
/*  42: 66 */     for (int i = 1; i < splitPoints.size(); i++)
/*  43:    */     {
/*  44: 67 */       long end = ((Long)splitPoints.get(i)).longValue();
/*  45: 69 */       if (i == splitPoints.size() - 1) {
/*  46: 71 */         splits.add(new DataDrivenDBInputFormat.DataDrivenDBInputSplit(lowClausePrefix + Long.toString(start), colName + " <= " + Long.toString(end)));
/*  47:    */       } else {
/*  48: 76 */         splits.add(new DataDrivenDBInputFormat.DataDrivenDBInputSplit(lowClausePrefix + Long.toString(start), highClausePrefix + Long.toString(end)));
/*  49:    */       }
/*  50: 81 */       start = end;
/*  51:    */     }
/*  52: 84 */     if ((results.getString(1) == null) || (results.getString(2) == null)) {
/*  53: 86 */       splits.add(new DataDrivenDBInputFormat.DataDrivenDBInputSplit(colName + " IS NULL", colName + " IS NULL"));
/*  54:    */     }
/*  55: 90 */     return splits;
/*  56:    */   }
/*  57:    */   
/*  58:    */   List<Long> split(long numSplits, long minVal, long maxVal)
/*  59:    */     throws SQLException
/*  60:    */   {
/*  61:108 */     List<Long> splits = new ArrayList();
/*  62:    */     
/*  63:    */ 
/*  64:    */ 
/*  65:    */ 
/*  66:113 */     long splitSize = (maxVal - minVal) / numSplits;
/*  67:114 */     if (splitSize < 1L) {
/*  68:115 */       splitSize = 1L;
/*  69:    */     }
/*  70:118 */     long curVal = minVal;
/*  71:120 */     while (curVal <= maxVal)
/*  72:    */     {
/*  73:121 */       splits.add(Long.valueOf(curVal));
/*  74:122 */       curVal += splitSize;
/*  75:    */     }
/*  76:125 */     if ((((Long)splits.get(splits.size() - 1)).longValue() != maxVal) || (splits.size() == 1)) {
/*  77:127 */       splits.add(Long.valueOf(maxVal));
/*  78:    */     }
/*  79:130 */     return splits;
/*  80:    */   }
/*  81:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.lib.db.IntegerSplitter
 * JD-Core Version:    0.7.0.1
 */