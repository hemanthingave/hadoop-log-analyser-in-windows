/*   1:    */ package org.apache.hadoop.mapreduce.lib.db;
/*   2:    */ 
/*   3:    */ import java.math.BigDecimal;
/*   4:    */ import java.sql.ResultSet;
/*   5:    */ import java.sql.SQLException;
/*   6:    */ import java.util.ArrayList;
/*   7:    */ import java.util.List;
/*   8:    */ import org.apache.commons.logging.Log;
/*   9:    */ import org.apache.commons.logging.LogFactory;
/*  10:    */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*  11:    */ import org.apache.hadoop.classification.InterfaceStability.Evolving;
/*  12:    */ import org.apache.hadoop.conf.Configuration;
/*  13:    */ import org.apache.hadoop.mapreduce.InputSplit;
/*  14:    */ 
/*  15:    */ @InterfaceAudience.Public
/*  16:    */ @InterfaceStability.Evolving
/*  17:    */ public class BigDecimalSplitter
/*  18:    */   implements DBSplitter
/*  19:    */ {
/*  20: 42 */   private static final Log LOG = LogFactory.getLog(BigDecimalSplitter.class);
/*  21:    */   
/*  22:    */   public List<InputSplit> split(Configuration conf, ResultSet results, String colName)
/*  23:    */     throws SQLException
/*  24:    */   {
/*  25: 47 */     BigDecimal minVal = results.getBigDecimal(1);
/*  26: 48 */     BigDecimal maxVal = results.getBigDecimal(2);
/*  27:    */     
/*  28: 50 */     String lowClausePrefix = colName + " >= ";
/*  29: 51 */     String highClausePrefix = colName + " < ";
/*  30:    */     
/*  31: 53 */     BigDecimal numSplits = new BigDecimal(conf.getInt("mapreduce.job.maps", 1));
/*  32: 55 */     if ((minVal == null) && (maxVal == null))
/*  33:    */     {
/*  34: 57 */       List<InputSplit> splits = new ArrayList();
/*  35: 58 */       splits.add(new DataDrivenDBInputFormat.DataDrivenDBInputSplit(colName + " IS NULL", colName + " IS NULL"));
/*  36:    */       
/*  37: 60 */       return splits;
/*  38:    */     }
/*  39: 63 */     if ((minVal == null) || (maxVal == null))
/*  40:    */     {
/*  41: 65 */       LOG.error("Cannot find a range for NUMERIC or DECIMAL fields with one end NULL.");
/*  42: 66 */       return null;
/*  43:    */     }
/*  44: 70 */     List<BigDecimal> splitPoints = split(numSplits, minVal, maxVal);
/*  45: 71 */     List<InputSplit> splits = new ArrayList();
/*  46:    */     
/*  47:    */ 
/*  48: 74 */     BigDecimal start = (BigDecimal)splitPoints.get(0);
/*  49: 75 */     for (int i = 1; i < splitPoints.size(); i++)
/*  50:    */     {
/*  51: 76 */       BigDecimal end = (BigDecimal)splitPoints.get(i);
/*  52: 78 */       if (i == splitPoints.size() - 1) {
/*  53: 80 */         splits.add(new DataDrivenDBInputFormat.DataDrivenDBInputSplit(lowClausePrefix + start.toString(), colName + " <= " + end.toString()));
/*  54:    */       } else {
/*  55: 85 */         splits.add(new DataDrivenDBInputFormat.DataDrivenDBInputSplit(lowClausePrefix + start.toString(), highClausePrefix + end.toString()));
/*  56:    */       }
/*  57: 90 */       start = end;
/*  58:    */     }
/*  59: 93 */     return splits;
/*  60:    */   }
/*  61:    */   
/*  62: 96 */   private static final BigDecimal MIN_INCREMENT = new BigDecimal(4.940656458412465E-320D);
/*  63:    */   
/*  64:    */   protected BigDecimal tryDivide(BigDecimal numerator, BigDecimal denominator)
/*  65:    */   {
/*  66:    */     try
/*  67:    */     {
/*  68:103 */       return numerator.divide(denominator);
/*  69:    */     }
/*  70:    */     catch (ArithmeticException ae) {}
/*  71:105 */     return numerator.divide(denominator, 4);
/*  72:    */   }
/*  73:    */   
/*  74:    */   List<BigDecimal> split(BigDecimal numSplits, BigDecimal minVal, BigDecimal maxVal)
/*  75:    */     throws SQLException
/*  76:    */   {
/*  77:124 */     List<BigDecimal> splits = new ArrayList();
/*  78:    */     
/*  79:    */ 
/*  80:    */ 
/*  81:    */ 
/*  82:129 */     BigDecimal splitSize = tryDivide(maxVal.subtract(minVal), numSplits);
/*  83:130 */     if (splitSize.compareTo(MIN_INCREMENT) < 0)
/*  84:    */     {
/*  85:131 */       splitSize = MIN_INCREMENT;
/*  86:132 */       LOG.warn("Set BigDecimal splitSize to MIN_INCREMENT");
/*  87:    */     }
/*  88:135 */     BigDecimal curVal = minVal;
/*  89:137 */     while (curVal.compareTo(maxVal) <= 0)
/*  90:    */     {
/*  91:138 */       splits.add(curVal);
/*  92:139 */       curVal = curVal.add(splitSize);
/*  93:    */     }
/*  94:142 */     if ((((BigDecimal)splits.get(splits.size() - 1)).compareTo(maxVal) != 0) || (splits.size() == 1)) {
/*  95:144 */       splits.add(maxVal);
/*  96:    */     }
/*  97:147 */     return splits;
/*  98:    */   }
/*  99:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.lib.db.BigDecimalSplitter
 * JD-Core Version:    0.7.0.1
 */