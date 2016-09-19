/*   1:    */ package org.apache.hadoop.mapreduce.lib.db;
/*   2:    */ 
/*   3:    */ import java.sql.ResultSet;
/*   4:    */ import java.sql.SQLException;
/*   5:    */ import java.util.ArrayList;
/*   6:    */ import java.util.List;
/*   7:    */ import org.apache.commons.logging.Log;
/*   8:    */ import org.apache.commons.logging.LogFactory;
/*   9:    */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*  10:    */ import org.apache.hadoop.classification.InterfaceStability.Evolving;
/*  11:    */ import org.apache.hadoop.conf.Configuration;
/*  12:    */ import org.apache.hadoop.mapreduce.InputSplit;
/*  13:    */ 
/*  14:    */ @InterfaceAudience.Public
/*  15:    */ @InterfaceStability.Evolving
/*  16:    */ public class FloatSplitter
/*  17:    */   implements DBSplitter
/*  18:    */ {
/*  19: 42 */   private static final Log LOG = LogFactory.getLog(FloatSplitter.class);
/*  20:    */   private static final double MIN_INCREMENT = 4.940656458412465E-320D;
/*  21:    */   
/*  22:    */   public List<InputSplit> split(Configuration conf, ResultSet results, String colName)
/*  23:    */     throws SQLException
/*  24:    */   {
/*  25: 49 */     LOG.warn("Generating splits for a floating-point index column. Due to the");
/*  26: 50 */     LOG.warn("imprecise representation of floating-point values in Java, this");
/*  27: 51 */     LOG.warn("may result in an incomplete import.");
/*  28: 52 */     LOG.warn("You are strongly encouraged to choose an integral split column.");
/*  29:    */     
/*  30: 54 */     List<InputSplit> splits = new ArrayList();
/*  31: 56 */     if ((results.getString(1) == null) && (results.getString(2) == null))
/*  32:    */     {
/*  33: 58 */       splits.add(new DataDrivenDBInputFormat.DataDrivenDBInputSplit(colName + " IS NULL", colName + " IS NULL"));
/*  34:    */       
/*  35: 60 */       return splits;
/*  36:    */     }
/*  37: 63 */     double minVal = results.getDouble(1);
/*  38: 64 */     double maxVal = results.getDouble(2);
/*  39:    */     
/*  40:    */ 
/*  41:    */ 
/*  42: 68 */     int numSplits = conf.getInt("mapreduce.job.maps", 1);
/*  43: 69 */     double splitSize = (maxVal - minVal) / numSplits;
/*  44: 71 */     if (splitSize < 4.940656458412465E-320D) {
/*  45: 72 */       splitSize = 4.940656458412465E-320D;
/*  46:    */     }
/*  47: 75 */     String lowClausePrefix = colName + " >= ";
/*  48: 76 */     String highClausePrefix = colName + " < ";
/*  49:    */     
/*  50: 78 */     double curLower = minVal;
/*  51: 79 */     double curUpper = curLower + splitSize;
/*  52: 81 */     while (curUpper < maxVal)
/*  53:    */     {
/*  54: 82 */       splits.add(new DataDrivenDBInputFormat.DataDrivenDBInputSplit(lowClausePrefix + Double.toString(curLower), highClausePrefix + Double.toString(curUpper)));
/*  55:    */       
/*  56:    */ 
/*  57:    */ 
/*  58: 86 */       curLower = curUpper;
/*  59: 87 */       curUpper += splitSize;
/*  60:    */     }
/*  61: 91 */     if ((curLower <= maxVal) || (splits.size() == 1)) {
/*  62: 92 */       splits.add(new DataDrivenDBInputFormat.DataDrivenDBInputSplit(lowClausePrefix + Double.toString(curLower), colName + " <= " + Double.toString(maxVal)));
/*  63:    */     }
/*  64: 97 */     if ((results.getString(1) == null) || (results.getString(2) == null)) {
/*  65: 99 */       splits.add(new DataDrivenDBInputFormat.DataDrivenDBInputSplit(colName + " IS NULL", colName + " IS NULL"));
/*  66:    */     }
/*  67:103 */     return splits;
/*  68:    */   }
/*  69:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.lib.db.FloatSplitter
 * JD-Core Version:    0.7.0.1
 */