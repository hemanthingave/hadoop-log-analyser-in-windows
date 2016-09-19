/*   1:    */ package org.apache.hadoop.mapreduce.lib.db;
/*   2:    */ 
/*   3:    */ import java.io.DataInput;
/*   4:    */ import java.io.DataOutput;
/*   5:    */ import java.io.IOException;
/*   6:    */ import java.sql.Connection;
/*   7:    */ import java.sql.ResultSet;
/*   8:    */ import java.sql.ResultSetMetaData;
/*   9:    */ import java.sql.SQLException;
/*  10:    */ import java.sql.Statement;
/*  11:    */ import java.util.ArrayList;
/*  12:    */ import java.util.List;
/*  13:    */ import org.apache.commons.logging.Log;
/*  14:    */ import org.apache.commons.logging.LogFactory;
/*  15:    */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*  16:    */ import org.apache.hadoop.classification.InterfaceStability.Evolving;
/*  17:    */ import org.apache.hadoop.conf.Configurable;
/*  18:    */ import org.apache.hadoop.conf.Configuration;
/*  19:    */ import org.apache.hadoop.io.LongWritable;
/*  20:    */ import org.apache.hadoop.io.Text;
/*  21:    */ import org.apache.hadoop.mapreduce.InputSplit;
/*  22:    */ import org.apache.hadoop.mapreduce.Job;
/*  23:    */ import org.apache.hadoop.mapreduce.JobContext;
/*  24:    */ import org.apache.hadoop.mapreduce.RecordReader;
/*  25:    */ 
/*  26:    */ @InterfaceAudience.Public
/*  27:    */ @InterfaceStability.Evolving
/*  28:    */ public class DataDrivenDBInputFormat<T extends DBWritable>
/*  29:    */   extends DBInputFormat<T>
/*  30:    */   implements Configurable
/*  31:    */ {
/*  32: 64 */   private static final Log LOG = LogFactory.getLog(DataDrivenDBInputFormat.class);
/*  33:    */   public static final String SUBSTITUTE_TOKEN = "$CONDITIONS";
/*  34:    */   
/*  35:    */   @InterfaceStability.Evolving
/*  36:    */   public static class DataDrivenDBInputSplit
/*  37:    */     extends DBInputFormat.DBInputSplit
/*  38:    */   {
/*  39:    */     private String lowerBoundClause;
/*  40:    */     private String upperBoundClause;
/*  41:    */     
/*  42:    */     public DataDrivenDBInputSplit() {}
/*  43:    */     
/*  44:    */     public DataDrivenDBInputSplit(String lower, String upper)
/*  45:    */     {
/*  46: 92 */       this.lowerBoundClause = lower;
/*  47: 93 */       this.upperBoundClause = upper;
/*  48:    */     }
/*  49:    */     
/*  50:    */     public long getLength()
/*  51:    */       throws IOException
/*  52:    */     {
/*  53:101 */       return 0L;
/*  54:    */     }
/*  55:    */     
/*  56:    */     public void readFields(DataInput input)
/*  57:    */       throws IOException
/*  58:    */     {
/*  59:106 */       this.lowerBoundClause = Text.readString(input);
/*  60:107 */       this.upperBoundClause = Text.readString(input);
/*  61:    */     }
/*  62:    */     
/*  63:    */     public void write(DataOutput output)
/*  64:    */       throws IOException
/*  65:    */     {
/*  66:112 */       Text.writeString(output, this.lowerBoundClause);
/*  67:113 */       Text.writeString(output, this.upperBoundClause);
/*  68:    */     }
/*  69:    */     
/*  70:    */     public String getLowerClause()
/*  71:    */     {
/*  72:117 */       return this.lowerBoundClause;
/*  73:    */     }
/*  74:    */     
/*  75:    */     public String getUpperClause()
/*  76:    */     {
/*  77:121 */       return this.upperBoundClause;
/*  78:    */     }
/*  79:    */   }
/*  80:    */   
/*  81:    */   protected DBSplitter getSplitter(int sqlDataType)
/*  82:    */   {
/*  83:129 */     switch (sqlDataType)
/*  84:    */     {
/*  85:    */     case 2: 
/*  86:    */     case 3: 
/*  87:132 */       return new BigDecimalSplitter();
/*  88:    */     case -7: 
/*  89:    */     case 16: 
/*  90:136 */       return new BooleanSplitter();
/*  91:    */     case -6: 
/*  92:    */     case -5: 
/*  93:    */     case 4: 
/*  94:    */     case 5: 
/*  95:142 */       return new IntegerSplitter();
/*  96:    */     case 6: 
/*  97:    */     case 7: 
/*  98:    */     case 8: 
/*  99:147 */       return new FloatSplitter();
/* 100:    */     case -1: 
/* 101:    */     case 1: 
/* 102:    */     case 12: 
/* 103:152 */       return new TextSplitter();
/* 104:    */     case 91: 
/* 105:    */     case 92: 
/* 106:    */     case 93: 
/* 107:157 */       return new DateSplitter();
/* 108:    */     }
/* 109:162 */     return null;
/* 110:    */   }
/* 111:    */   
/* 112:    */   public List<InputSplit> getSplits(JobContext job)
/* 113:    */     throws IOException
/* 114:    */   {
/* 115:169 */     int targetNumTasks = job.getConfiguration().getInt("mapreduce.job.maps", 1);
/* 116:170 */     if (1 == targetNumTasks)
/* 117:    */     {
/* 118:174 */       List<InputSplit> singletonSplit = new ArrayList();
/* 119:175 */       singletonSplit.add(new DataDrivenDBInputSplit("1=1", "1=1"));
/* 120:176 */       return singletonSplit;
/* 121:    */     }
/* 122:179 */     ResultSet results = null;
/* 123:180 */     Statement statement = null;
/* 124:181 */     Connection connection = getConnection();
/* 125:    */     try
/* 126:    */     {
/* 127:183 */       statement = connection.createStatement();
/* 128:    */       
/* 129:185 */       results = statement.executeQuery(getBoundingValsQuery());
/* 130:186 */       results.next();
/* 131:    */       
/* 132:    */ 
/* 133:    */ 
/* 134:    */ 
/* 135:191 */       int sqlDataType = results.getMetaData().getColumnType(1);
/* 136:192 */       DBSplitter splitter = getSplitter(sqlDataType);
/* 137:193 */       if (null == splitter) {
/* 138:194 */         throw new IOException("Unknown SQL data type: " + sqlDataType);
/* 139:    */       }
/* 140:197 */       return splitter.split(job.getConfiguration(), results, getDBConf().getInputOrderBy());
/* 141:    */     }
/* 142:    */     catch (SQLException e)
/* 143:    */     {
/* 144:199 */       throw new IOException(e.getMessage());
/* 145:    */     }
/* 146:    */     finally
/* 147:    */     {
/* 148:    */       try
/* 149:    */       {
/* 150:203 */         if (null != results) {
/* 151:204 */           results.close();
/* 152:    */         }
/* 153:    */       }
/* 154:    */       catch (SQLException se)
/* 155:    */       {
/* 156:207 */         LOG.debug("SQLException closing resultset: " + se.toString());
/* 157:    */       }
/* 158:    */       try
/* 159:    */       {
/* 160:211 */         if (null != statement) {
/* 161:212 */           statement.close();
/* 162:    */         }
/* 163:    */       }
/* 164:    */       catch (SQLException se)
/* 165:    */       {
/* 166:215 */         LOG.debug("SQLException closing statement: " + se.toString());
/* 167:    */       }
/* 168:    */       try
/* 169:    */       {
/* 170:219 */         connection.commit();
/* 171:220 */         closeConnection();
/* 172:    */       }
/* 173:    */       catch (SQLException se)
/* 174:    */       {
/* 175:222 */         LOG.debug("SQLException committing split transaction: " + se.toString());
/* 176:    */       }
/* 177:    */     }
/* 178:    */   }
/* 179:    */   
/* 180:    */   protected String getBoundingValsQuery()
/* 181:    */   {
/* 182:236 */     String userQuery = getDBConf().getInputBoundingQuery();
/* 183:237 */     if (null != userQuery) {
/* 184:238 */       return userQuery;
/* 185:    */     }
/* 186:242 */     StringBuilder query = new StringBuilder();
/* 187:    */     
/* 188:244 */     String splitCol = getDBConf().getInputOrderBy();
/* 189:245 */     query.append("SELECT MIN(").append(splitCol).append("), ");
/* 190:246 */     query.append("MAX(").append(splitCol).append(") FROM ");
/* 191:247 */     query.append(getDBConf().getInputTableName());
/* 192:248 */     String conditions = getDBConf().getInputConditions();
/* 193:249 */     if (null != conditions) {
/* 194:250 */       query.append(" WHERE ( " + conditions + " )");
/* 195:    */     }
/* 196:253 */     return query.toString();
/* 197:    */   }
/* 198:    */   
/* 199:    */   public static void setBoundingQuery(Configuration conf, String query)
/* 200:    */   {
/* 201:266 */     if (null != query) {
/* 202:268 */       if (query.indexOf("$CONDITIONS") == -1) {
/* 203:269 */         LOG.warn("Could not find $CONDITIONS token in query: " + query + "; splits may not partition data.");
/* 204:    */       }
/* 205:    */     }
/* 206:274 */     conf.set("mapred.jdbc.input.bounding.query", query);
/* 207:    */   }
/* 208:    */   
/* 209:    */   protected RecordReader<LongWritable, T> createDBRecordReader(DBInputFormat.DBInputSplit split, Configuration conf)
/* 210:    */     throws IOException
/* 211:    */   {
/* 212:280 */     DBConfiguration dbConf = getDBConf();
/* 213:    */     
/* 214:282 */     Class<T> inputClass = dbConf.getInputClass();
/* 215:283 */     String dbProductName = getDBProductName();
/* 216:    */     
/* 217:285 */     LOG.debug("Creating db record reader for db product: " + dbProductName);
/* 218:    */     try
/* 219:    */     {
/* 220:289 */       if (dbProductName.startsWith("MYSQL")) {
/* 221:291 */         return new MySQLDataDrivenDBRecordReader(split, inputClass, conf, getConnection(), dbConf, dbConf.getInputConditions(), dbConf.getInputFieldNames(), dbConf.getInputTableName());
/* 222:    */       }
/* 223:296 */       return new DataDrivenDBRecordReader(split, inputClass, conf, getConnection(), dbConf, dbConf.getInputConditions(), dbConf.getInputFieldNames(), dbConf.getInputTableName(), dbProductName);
/* 224:    */     }
/* 225:    */     catch (SQLException ex)
/* 226:    */     {
/* 227:302 */       throw new IOException(ex.getMessage());
/* 228:    */     }
/* 229:    */   }
/* 230:    */   
/* 231:    */   public static void setInput(Job job, Class<? extends DBWritable> inputClass, String tableName, String conditions, String splitBy, String... fieldNames)
/* 232:    */   {
/* 233:317 */     DBInputFormat.setInput(job, inputClass, tableName, conditions, splitBy, fieldNames);
/* 234:318 */     job.setInputFormatClass(DataDrivenDBInputFormat.class);
/* 235:    */   }
/* 236:    */   
/* 237:    */   public static void setInput(Job job, Class<? extends DBWritable> inputClass, String inputQuery, String inputBoundingQuery)
/* 238:    */   {
/* 239:327 */     DBInputFormat.setInput(job, inputClass, inputQuery, "");
/* 240:328 */     job.getConfiguration().set("mapred.jdbc.input.bounding.query", inputBoundingQuery);
/* 241:329 */     job.setInputFormatClass(DataDrivenDBInputFormat.class);
/* 242:    */   }
/* 243:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.lib.db.DataDrivenDBInputFormat
 * JD-Core Version:    0.7.0.1
 */