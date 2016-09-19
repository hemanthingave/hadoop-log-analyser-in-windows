/*  1:   */ package org.apache.hadoop.mapreduce.lib.db;
/*  2:   */ 
/*  3:   */ import java.sql.ResultSet;
/*  4:   */ import java.sql.SQLException;
/*  5:   */ import java.util.ArrayList;
/*  6:   */ import java.util.List;
/*  7:   */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*  8:   */ import org.apache.hadoop.classification.InterfaceStability.Evolving;
/*  9:   */ import org.apache.hadoop.conf.Configuration;
/* 10:   */ import org.apache.hadoop.mapreduce.InputSplit;
/* 11:   */ 
/* 12:   */ @InterfaceAudience.Public
/* 13:   */ @InterfaceStability.Evolving
/* 14:   */ public class BooleanSplitter
/* 15:   */   implements DBSplitter
/* 16:   */ {
/* 17:   */   public List<InputSplit> split(Configuration conf, ResultSet results, String colName)
/* 18:   */     throws SQLException
/* 19:   */   {
/* 20:40 */     List<InputSplit> splits = new ArrayList();
/* 21:42 */     if ((results.getString(1) == null) && (results.getString(2) == null))
/* 22:   */     {
/* 23:44 */       splits.add(new DataDrivenDBInputFormat.DataDrivenDBInputSplit(colName + " IS NULL", colName + " IS NULL"));
/* 24:   */       
/* 25:46 */       return splits;
/* 26:   */     }
/* 27:49 */     boolean minVal = results.getBoolean(1);
/* 28:50 */     boolean maxVal = results.getBoolean(2);
/* 29:53 */     if (!minVal) {
/* 30:54 */       splits.add(new DataDrivenDBInputFormat.DataDrivenDBInputSplit(colName + " = FALSE", colName + " = FALSE"));
/* 31:   */     }
/* 32:58 */     if (maxVal) {
/* 33:59 */       splits.add(new DataDrivenDBInputFormat.DataDrivenDBInputSplit(colName + " = TRUE", colName + " = TRUE"));
/* 34:   */     }
/* 35:63 */     if ((results.getString(1) == null) || (results.getString(2) == null)) {
/* 36:65 */       splits.add(new DataDrivenDBInputFormat.DataDrivenDBInputSplit(colName + " IS NULL", colName + " IS NULL"));
/* 37:   */     }
/* 38:69 */     return splits;
/* 39:   */   }
/* 40:   */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.lib.db.BooleanSplitter
 * JD-Core Version:    0.7.0.1
 */