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
/*  17:    */ public class TextSplitter
/*  18:    */   extends BigDecimalSplitter
/*  19:    */ {
/*  20: 44 */   private static final Log LOG = LogFactory.getLog(TextSplitter.class);
/*  21:    */   
/*  22:    */   public List<InputSplit> split(Configuration conf, ResultSet results, String colName)
/*  23:    */     throws SQLException
/*  24:    */   {
/*  25: 66 */     LOG.warn("Generating splits for a textual index column.");
/*  26: 67 */     LOG.warn("If your database sorts in a case-insensitive order, this may result in a partial import or duplicate records.");
/*  27:    */     
/*  28: 69 */     LOG.warn("You are strongly encouraged to choose an integral split column.");
/*  29:    */     
/*  30: 71 */     String minString = results.getString(1);
/*  31: 72 */     String maxString = results.getString(2);
/*  32:    */     
/*  33: 74 */     boolean minIsNull = false;
/*  34: 78 */     if (null == minString)
/*  35:    */     {
/*  36: 79 */       minString = "";
/*  37: 80 */       minIsNull = true;
/*  38:    */     }
/*  39: 83 */     if (null == maxString)
/*  40:    */     {
/*  41: 86 */       List<InputSplit> splits = new ArrayList();
/*  42: 87 */       splits.add(new DataDrivenDBInputFormat.DataDrivenDBInputSplit(colName + " IS NULL", colName + " IS NULL"));
/*  43:    */       
/*  44: 89 */       return splits;
/*  45:    */     }
/*  46: 94 */     int numSplits = conf.getInt("mapreduce.job.maps", 1);
/*  47:    */     
/*  48: 96 */     String lowClausePrefix = colName + " >= '";
/*  49: 97 */     String highClausePrefix = colName + " < '";
/*  50:    */     
/*  51:    */ 
/*  52:    */ 
/*  53:101 */     int maxPrefixLen = Math.min(minString.length(), maxString.length());
/*  54:103 */     for (int sharedLen = 0; sharedLen < maxPrefixLen; sharedLen++)
/*  55:    */     {
/*  56:104 */       char c1 = minString.charAt(sharedLen);
/*  57:105 */       char c2 = maxString.charAt(sharedLen);
/*  58:106 */       if (c1 != c2) {
/*  59:    */         break;
/*  60:    */       }
/*  61:    */     }
/*  62:112 */     String commonPrefix = minString.substring(0, sharedLen);
/*  63:113 */     minString = minString.substring(sharedLen);
/*  64:114 */     maxString = maxString.substring(sharedLen);
/*  65:    */     
/*  66:116 */     List<String> splitStrings = split(numSplits, minString, maxString, commonPrefix);
/*  67:117 */     List<InputSplit> splits = new ArrayList();
/*  68:    */     
/*  69:    */ 
/*  70:120 */     String start = (String)splitStrings.get(0);
/*  71:121 */     for (int i = 1; i < splitStrings.size(); i++)
/*  72:    */     {
/*  73:122 */       String end = (String)splitStrings.get(i);
/*  74:124 */       if (i == splitStrings.size() - 1) {
/*  75:126 */         splits.add(new DataDrivenDBInputFormat.DataDrivenDBInputSplit(lowClausePrefix + start + "'", colName + " <= '" + end + "'"));
/*  76:    */       } else {
/*  77:130 */         splits.add(new DataDrivenDBInputFormat.DataDrivenDBInputSplit(lowClausePrefix + start + "'", highClausePrefix + end + "'"));
/*  78:    */       }
/*  79:    */     }
/*  80:135 */     if (minIsNull) {
/*  81:137 */       splits.add(new DataDrivenDBInputFormat.DataDrivenDBInputSplit(colName + " IS NULL", colName + " IS NULL"));
/*  82:    */     }
/*  83:141 */     return splits;
/*  84:    */   }
/*  85:    */   
/*  86:    */   List<String> split(int numSplits, String minString, String maxString, String commonPrefix)
/*  87:    */     throws SQLException
/*  88:    */   {
/*  89:147 */     BigDecimal minVal = stringToBigDecimal(minString);
/*  90:148 */     BigDecimal maxVal = stringToBigDecimal(maxString);
/*  91:    */     
/*  92:150 */     List<BigDecimal> splitPoints = split(new BigDecimal(numSplits), minVal, maxVal);
/*  93:151 */     List<String> splitStrings = new ArrayList();
/*  94:154 */     for (BigDecimal bd : splitPoints) {
/*  95:155 */       splitStrings.add(commonPrefix + bigDecimalToString(bd));
/*  96:    */     }
/*  97:160 */     if ((splitStrings.size() == 0) || (!((String)splitStrings.get(0)).equals(commonPrefix + minString))) {
/*  98:161 */       splitStrings.add(0, commonPrefix + minString);
/*  99:    */     }
/* 100:163 */     if ((splitStrings.size() == 1) || (!((String)splitStrings.get(splitStrings.size() - 1)).equals(commonPrefix + maxString))) {
/* 101:165 */       splitStrings.add(commonPrefix + maxString);
/* 102:    */     }
/* 103:168 */     return splitStrings;
/* 104:    */   }
/* 105:    */   
/* 106:171 */   private static final BigDecimal ONE_PLACE = new BigDecimal(65536);
/* 107:    */   private static final int MAX_CHARS = 8;
/* 108:    */   
/* 109:    */   BigDecimal stringToBigDecimal(String str)
/* 110:    */   {
/* 111:183 */     BigDecimal result = BigDecimal.ZERO;
/* 112:184 */     BigDecimal curPlace = ONE_PLACE;
/* 113:    */     
/* 114:186 */     int len = Math.min(str.length(), 8);
/* 115:188 */     for (int i = 0; i < len; i++)
/* 116:    */     {
/* 117:189 */       int codePoint = str.codePointAt(i);
/* 118:190 */       result = result.add(tryDivide(new BigDecimal(codePoint), curPlace));
/* 119:    */       
/* 120:192 */       curPlace = curPlace.multiply(ONE_PLACE);
/* 121:    */     }
/* 122:195 */     return result;
/* 123:    */   }
/* 124:    */   
/* 125:    */   String bigDecimalToString(BigDecimal bd)
/* 126:    */   {
/* 127:205 */     BigDecimal cur = bd.stripTrailingZeros();
/* 128:206 */     StringBuilder sb = new StringBuilder();
/* 129:208 */     for (int numConverted = 0; numConverted < 8; numConverted++)
/* 130:    */     {
/* 131:209 */       cur = cur.multiply(ONE_PLACE);
/* 132:210 */       int curCodePoint = cur.intValue();
/* 133:211 */       if (0 == curCodePoint) {
/* 134:    */         break;
/* 135:    */       }
/* 136:215 */       cur = cur.subtract(new BigDecimal(curCodePoint));
/* 137:216 */       sb.append(Character.toChars(curCodePoint));
/* 138:    */     }
/* 139:219 */     return sb.toString();
/* 140:    */   }
/* 141:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.lib.db.TextSplitter
 * JD-Core Version:    0.7.0.1
 */