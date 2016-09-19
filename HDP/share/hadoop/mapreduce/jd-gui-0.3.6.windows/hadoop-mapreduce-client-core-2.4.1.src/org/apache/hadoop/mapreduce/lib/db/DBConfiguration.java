/*   1:    */ package org.apache.hadoop.mapreduce.lib.db;
/*   2:    */ 
/*   3:    */ import java.sql.Connection;
/*   4:    */ import java.sql.DriverManager;
/*   5:    */ import java.sql.SQLException;
/*   6:    */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*   7:    */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*   8:    */ import org.apache.hadoop.conf.Configuration;
/*   9:    */ 
/*  10:    */ @InterfaceAudience.Public
/*  11:    */ @InterfaceStability.Stable
/*  12:    */ public class DBConfiguration
/*  13:    */ {
/*  14:    */   public static final String DRIVER_CLASS_PROPERTY = "mapreduce.jdbc.driver.class";
/*  15:    */   public static final String URL_PROPERTY = "mapreduce.jdbc.url";
/*  16:    */   public static final String USERNAME_PROPERTY = "mapreduce.jdbc.username";
/*  17:    */   public static final String PASSWORD_PROPERTY = "mapreduce.jdbc.password";
/*  18:    */   public static final String INPUT_TABLE_NAME_PROPERTY = "mapreduce.jdbc.input.table.name";
/*  19:    */   public static final String INPUT_FIELD_NAMES_PROPERTY = "mapreduce.jdbc.input.field.names";
/*  20:    */   public static final String INPUT_CONDITIONS_PROPERTY = "mapreduce.jdbc.input.conditions";
/*  21:    */   public static final String INPUT_ORDER_BY_PROPERTY = "mapreduce.jdbc.input.orderby";
/*  22:    */   public static final String INPUT_QUERY = "mapreduce.jdbc.input.query";
/*  23:    */   public static final String INPUT_COUNT_QUERY = "mapreduce.jdbc.input.count.query";
/*  24:    */   public static final String INPUT_BOUNDING_QUERY = "mapred.jdbc.input.bounding.query";
/*  25:    */   public static final String INPUT_CLASS_PROPERTY = "mapreduce.jdbc.input.class";
/*  26:    */   public static final String OUTPUT_TABLE_NAME_PROPERTY = "mapreduce.jdbc.output.table.name";
/*  27:    */   public static final String OUTPUT_FIELD_NAMES_PROPERTY = "mapreduce.jdbc.output.field.names";
/*  28:    */   public static final String OUTPUT_FIELD_COUNT_PROPERTY = "mapreduce.jdbc.output.field.count";
/*  29:    */   private Configuration conf;
/*  30:    */   
/*  31:    */   public static void configureDB(Configuration conf, String driverClass, String dbUrl, String userName, String passwd)
/*  32:    */   {
/*  33:115 */     conf.set("mapreduce.jdbc.driver.class", driverClass);
/*  34:116 */     conf.set("mapreduce.jdbc.url", dbUrl);
/*  35:117 */     if (userName != null) {
/*  36:118 */       conf.set("mapreduce.jdbc.username", userName);
/*  37:    */     }
/*  38:120 */     if (passwd != null) {
/*  39:121 */       conf.set("mapreduce.jdbc.password", passwd);
/*  40:    */     }
/*  41:    */   }
/*  42:    */   
/*  43:    */   public static void configureDB(Configuration job, String driverClass, String dbUrl)
/*  44:    */   {
/*  45:133 */     configureDB(job, driverClass, dbUrl, null, null);
/*  46:    */   }
/*  47:    */   
/*  48:    */   public DBConfiguration(Configuration job)
/*  49:    */   {
/*  50:139 */     this.conf = job;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public Connection getConnection()
/*  54:    */     throws ClassNotFoundException, SQLException
/*  55:    */   {
/*  56:148 */     Class.forName(this.conf.get("mapreduce.jdbc.driver.class"));
/*  57:150 */     if (this.conf.get("mapreduce.jdbc.username") == null) {
/*  58:151 */       return DriverManager.getConnection(this.conf.get("mapreduce.jdbc.url"));
/*  59:    */     }
/*  60:154 */     return DriverManager.getConnection(this.conf.get("mapreduce.jdbc.url"), this.conf.get("mapreduce.jdbc.username"), this.conf.get("mapreduce.jdbc.password"));
/*  61:    */   }
/*  62:    */   
/*  63:    */   public Configuration getConf()
/*  64:    */   {
/*  65:162 */     return this.conf;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public String getInputTableName()
/*  69:    */   {
/*  70:166 */     return this.conf.get("mapreduce.jdbc.input.table.name");
/*  71:    */   }
/*  72:    */   
/*  73:    */   public void setInputTableName(String tableName)
/*  74:    */   {
/*  75:170 */     this.conf.set("mapreduce.jdbc.input.table.name", tableName);
/*  76:    */   }
/*  77:    */   
/*  78:    */   public String[] getInputFieldNames()
/*  79:    */   {
/*  80:174 */     return this.conf.getStrings("mapreduce.jdbc.input.field.names");
/*  81:    */   }
/*  82:    */   
/*  83:    */   public void setInputFieldNames(String... fieldNames)
/*  84:    */   {
/*  85:178 */     this.conf.setStrings("mapreduce.jdbc.input.field.names", fieldNames);
/*  86:    */   }
/*  87:    */   
/*  88:    */   public String getInputConditions()
/*  89:    */   {
/*  90:182 */     return this.conf.get("mapreduce.jdbc.input.conditions");
/*  91:    */   }
/*  92:    */   
/*  93:    */   public void setInputConditions(String conditions)
/*  94:    */   {
/*  95:186 */     if ((conditions != null) && (conditions.length() > 0)) {
/*  96:187 */       this.conf.set("mapreduce.jdbc.input.conditions", conditions);
/*  97:    */     }
/*  98:    */   }
/*  99:    */   
/* 100:    */   public String getInputOrderBy()
/* 101:    */   {
/* 102:191 */     return this.conf.get("mapreduce.jdbc.input.orderby");
/* 103:    */   }
/* 104:    */   
/* 105:    */   public void setInputOrderBy(String orderby)
/* 106:    */   {
/* 107:195 */     if ((orderby != null) && (orderby.length() > 0)) {
/* 108:196 */       this.conf.set("mapreduce.jdbc.input.orderby", orderby);
/* 109:    */     }
/* 110:    */   }
/* 111:    */   
/* 112:    */   public String getInputQuery()
/* 113:    */   {
/* 114:201 */     return this.conf.get("mapreduce.jdbc.input.query");
/* 115:    */   }
/* 116:    */   
/* 117:    */   public void setInputQuery(String query)
/* 118:    */   {
/* 119:205 */     if ((query != null) && (query.length() > 0)) {
/* 120:206 */       this.conf.set("mapreduce.jdbc.input.query", query);
/* 121:    */     }
/* 122:    */   }
/* 123:    */   
/* 124:    */   public String getInputCountQuery()
/* 125:    */   {
/* 126:211 */     return this.conf.get("mapreduce.jdbc.input.count.query");
/* 127:    */   }
/* 128:    */   
/* 129:    */   public void setInputCountQuery(String query)
/* 130:    */   {
/* 131:215 */     if ((query != null) && (query.length() > 0)) {
/* 132:216 */       this.conf.set("mapreduce.jdbc.input.count.query", query);
/* 133:    */     }
/* 134:    */   }
/* 135:    */   
/* 136:    */   public void setInputBoundingQuery(String query)
/* 137:    */   {
/* 138:221 */     if ((query != null) && (query.length() > 0)) {
/* 139:222 */       this.conf.set("mapred.jdbc.input.bounding.query", query);
/* 140:    */     }
/* 141:    */   }
/* 142:    */   
/* 143:    */   public String getInputBoundingQuery()
/* 144:    */   {
/* 145:227 */     return this.conf.get("mapred.jdbc.input.bounding.query");
/* 146:    */   }
/* 147:    */   
/* 148:    */   public Class<?> getInputClass()
/* 149:    */   {
/* 150:231 */     return this.conf.getClass("mapreduce.jdbc.input.class", DBInputFormat.NullDBWritable.class);
/* 151:    */   }
/* 152:    */   
/* 153:    */   public void setInputClass(Class<? extends DBWritable> inputClass)
/* 154:    */   {
/* 155:236 */     this.conf.setClass("mapreduce.jdbc.input.class", inputClass, DBWritable.class);
/* 156:    */   }
/* 157:    */   
/* 158:    */   public String getOutputTableName()
/* 159:    */   {
/* 160:241 */     return this.conf.get("mapreduce.jdbc.output.table.name");
/* 161:    */   }
/* 162:    */   
/* 163:    */   public void setOutputTableName(String tableName)
/* 164:    */   {
/* 165:245 */     this.conf.set("mapreduce.jdbc.output.table.name", tableName);
/* 166:    */   }
/* 167:    */   
/* 168:    */   public String[] getOutputFieldNames()
/* 169:    */   {
/* 170:249 */     return this.conf.getStrings("mapreduce.jdbc.output.field.names");
/* 171:    */   }
/* 172:    */   
/* 173:    */   public void setOutputFieldNames(String... fieldNames)
/* 174:    */   {
/* 175:253 */     this.conf.setStrings("mapreduce.jdbc.output.field.names", fieldNames);
/* 176:    */   }
/* 177:    */   
/* 178:    */   public void setOutputFieldCount(int fieldCount)
/* 179:    */   {
/* 180:257 */     this.conf.setInt("mapreduce.jdbc.output.field.count", fieldCount);
/* 181:    */   }
/* 182:    */   
/* 183:    */   public int getOutputFieldCount()
/* 184:    */   {
/* 185:261 */     return this.conf.getInt("mapreduce.jdbc.output.field.count", 0);
/* 186:    */   }
/* 187:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.lib.db.DBConfiguration
 * JD-Core Version:    0.7.0.1
 */