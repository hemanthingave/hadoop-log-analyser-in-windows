/*   1:    */ package org.apache.hadoop.mapreduce.lib.db;
/*   2:    */ 
/*   3:    */ import java.io.DataInput;
/*   4:    */ import java.io.DataOutput;
/*   5:    */ import java.io.IOException;
/*   6:    */ import java.sql.Connection;
/*   7:    */ import java.sql.DatabaseMetaData;
/*   8:    */ import java.sql.PreparedStatement;
/*   9:    */ import java.sql.ResultSet;
/*  10:    */ import java.sql.SQLException;
/*  11:    */ import java.sql.Statement;
/*  12:    */ import java.util.ArrayList;
/*  13:    */ import java.util.List;
/*  14:    */ import org.apache.commons.logging.Log;
/*  15:    */ import org.apache.commons.logging.LogFactory;
/*  16:    */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*  17:    */ import org.apache.hadoop.classification.InterfaceStability.Evolving;
/*  18:    */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*  19:    */ import org.apache.hadoop.conf.Configurable;
/*  20:    */ import org.apache.hadoop.conf.Configuration;
/*  21:    */ import org.apache.hadoop.io.LongWritable;
/*  22:    */ import org.apache.hadoop.io.Writable;
/*  23:    */ import org.apache.hadoop.mapreduce.InputFormat;
/*  24:    */ import org.apache.hadoop.mapreduce.InputSplit;
/*  25:    */ import org.apache.hadoop.mapreduce.Job;
/*  26:    */ import org.apache.hadoop.mapreduce.JobContext;
/*  27:    */ import org.apache.hadoop.mapreduce.RecordReader;
/*  28:    */ import org.apache.hadoop.mapreduce.TaskAttemptContext;
/*  29:    */ 
/*  30:    */ @InterfaceAudience.Public
/*  31:    */ @InterfaceStability.Stable
/*  32:    */ public class DBInputFormat<T extends DBWritable>
/*  33:    */   extends InputFormat<LongWritable, T>
/*  34:    */   implements Configurable
/*  35:    */ {
/*  36: 62 */   private static final Log LOG = LogFactory.getLog(DBInputFormat.class);
/*  37:    */   protected String dbProductName;
/*  38:    */   protected String conditions;
/*  39:    */   protected Connection connection;
/*  40:    */   protected String tableName;
/*  41:    */   protected String[] fieldNames;
/*  42:    */   protected DBConfiguration dbConf;
/*  43:    */   
/*  44:    */   public DBInputFormat()
/*  45:    */   {
/*  46: 64 */     this.dbProductName = "DEFAULT";
/*  47:    */   }
/*  48:    */   
/*  49:    */   @InterfaceStability.Evolving
/*  50:    */   public static class DBInputSplit
/*  51:    */     extends InputSplit
/*  52:    */     implements Writable
/*  53:    */   {
/*  54: 87 */     private long end = 0L;
/*  55: 88 */     private long start = 0L;
/*  56:    */     
/*  57:    */     public DBInputSplit() {}
/*  58:    */     
/*  59:    */     public DBInputSplit(long start, long end)
/*  60:    */     {
/*  61:102 */       this.start = start;
/*  62:103 */       this.end = end;
/*  63:    */     }
/*  64:    */     
/*  65:    */     public String[] getLocations()
/*  66:    */       throws IOException
/*  67:    */     {
/*  68:109 */       return new String[0];
/*  69:    */     }
/*  70:    */     
/*  71:    */     public long getStart()
/*  72:    */     {
/*  73:116 */       return this.start;
/*  74:    */     }
/*  75:    */     
/*  76:    */     public long getEnd()
/*  77:    */     {
/*  78:123 */       return this.end;
/*  79:    */     }
/*  80:    */     
/*  81:    */     public long getLength()
/*  82:    */       throws IOException
/*  83:    */     {
/*  84:130 */       return this.end - this.start;
/*  85:    */     }
/*  86:    */     
/*  87:    */     public void readFields(DataInput input)
/*  88:    */       throws IOException
/*  89:    */     {
/*  90:135 */       this.start = input.readLong();
/*  91:136 */       this.end = input.readLong();
/*  92:    */     }
/*  93:    */     
/*  94:    */     public void write(DataOutput output)
/*  95:    */       throws IOException
/*  96:    */     {
/*  97:141 */       output.writeLong(this.start);
/*  98:142 */       output.writeLong(this.end);
/*  99:    */     }
/* 100:    */   }
/* 101:    */   
/* 102:    */   public void setConf(Configuration conf)
/* 103:    */   {
/* 104:159 */     this.dbConf = new DBConfiguration(conf);
/* 105:    */     try
/* 106:    */     {
/* 107:162 */       getConnection();
/* 108:    */       
/* 109:164 */       DatabaseMetaData dbMeta = this.connection.getMetaData();
/* 110:165 */       this.dbProductName = dbMeta.getDatabaseProductName().toUpperCase();
/* 111:    */     }
/* 112:    */     catch (Exception ex)
/* 113:    */     {
/* 114:168 */       throw new RuntimeException(ex);
/* 115:    */     }
/* 116:171 */     this.tableName = this.dbConf.getInputTableName();
/* 117:172 */     this.fieldNames = this.dbConf.getInputFieldNames();
/* 118:173 */     this.conditions = this.dbConf.getInputConditions();
/* 119:    */   }
/* 120:    */   
/* 121:    */   public Configuration getConf()
/* 122:    */   {
/* 123:177 */     return this.dbConf.getConf();
/* 124:    */   }
/* 125:    */   
/* 126:    */   public DBConfiguration getDBConf()
/* 127:    */   {
/* 128:181 */     return this.dbConf;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public Connection getConnection()
/* 132:    */   {
/* 133:    */     try
/* 134:    */     {
/* 135:186 */       if (null == this.connection)
/* 136:    */       {
/* 137:188 */         this.connection = this.dbConf.getConnection();
/* 138:189 */         this.connection.setAutoCommit(false);
/* 139:190 */         this.connection.setTransactionIsolation(8);
/* 140:    */       }
/* 141:    */     }
/* 142:    */     catch (Exception e)
/* 143:    */     {
/* 144:194 */       throw new RuntimeException(e);
/* 145:    */     }
/* 146:196 */     return this.connection;
/* 147:    */   }
/* 148:    */   
/* 149:    */   public String getDBProductName()
/* 150:    */   {
/* 151:200 */     return this.dbProductName;
/* 152:    */   }
/* 153:    */   
/* 154:    */   protected RecordReader<LongWritable, T> createDBRecordReader(DBInputSplit split, Configuration conf)
/* 155:    */     throws IOException
/* 156:    */   {
/* 157:207 */     Class<T> inputClass = this.dbConf.getInputClass();
/* 158:    */     try
/* 159:    */     {
/* 160:210 */       if (this.dbProductName.startsWith("ORACLE")) {
/* 161:212 */         return new OracleDBRecordReader(split, inputClass, conf, getConnection(), getDBConf(), this.conditions, this.fieldNames, this.tableName);
/* 162:    */       }
/* 163:215 */       if (this.dbProductName.startsWith("MYSQL")) {
/* 164:217 */         return new MySQLDBRecordReader(split, inputClass, conf, getConnection(), getDBConf(), this.conditions, this.fieldNames, this.tableName);
/* 165:    */       }
/* 166:222 */       return new DBRecordReader(split, inputClass, conf, getConnection(), getDBConf(), this.conditions, this.fieldNames, this.tableName);
/* 167:    */     }
/* 168:    */     catch (SQLException ex)
/* 169:    */     {
/* 170:227 */       throw new IOException(ex.getMessage());
/* 171:    */     }
/* 172:    */   }
/* 173:    */   
/* 174:    */   public RecordReader<LongWritable, T> createRecordReader(InputSplit split, TaskAttemptContext context)
/* 175:    */     throws IOException, InterruptedException
/* 176:    */   {
/* 177:235 */     return createDBRecordReader((DBInputSplit)split, context.getConfiguration());
/* 178:    */   }
/* 179:    */   
/* 180:    */   public List<InputSplit> getSplits(JobContext job)
/* 181:    */     throws IOException
/* 182:    */   {
/* 183:241 */     ResultSet results = null;
/* 184:242 */     Statement statement = null;
/* 185:    */     try
/* 186:    */     {
/* 187:244 */       statement = this.connection.createStatement();
/* 188:    */       
/* 189:246 */       results = statement.executeQuery(getCountQuery());
/* 190:247 */       results.next();
/* 191:    */       
/* 192:249 */       long count = results.getLong(1);
/* 193:250 */       int chunks = job.getConfiguration().getInt("mapreduce.job.maps", 1);
/* 194:251 */       long chunkSize = count / chunks;
/* 195:    */       
/* 196:253 */       results.close();
/* 197:254 */       statement.close();
/* 198:    */       
/* 199:256 */       List<InputSplit> splits = new ArrayList();
/* 200:260 */       for (int i = 0; i < chunks; i++)
/* 201:    */       {
/* 202:    */         DBInputSplit split;
/* 203:    */         DBInputSplit split;
/* 204:263 */         if (i + 1 == chunks) {
/* 205:264 */           split = new DBInputSplit(i * chunkSize, count);
/* 206:    */         } else {
/* 207:266 */           split = new DBInputSplit(i * chunkSize, i * chunkSize + chunkSize);
/* 208:    */         }
/* 209:269 */         splits.add(split);
/* 210:    */       }
/* 211:272 */       this.connection.commit();
/* 212:273 */       return splits;
/* 213:    */     }
/* 214:    */     catch (SQLException e)
/* 215:    */     {
/* 216:275 */       throw new IOException("Got SQLException", e);
/* 217:    */     }
/* 218:    */     finally
/* 219:    */     {
/* 220:    */       try
/* 221:    */       {
/* 222:278 */         if (results != null) {
/* 223:278 */           results.close();
/* 224:    */         }
/* 225:    */       }
/* 226:    */       catch (SQLException e1) {}
/* 227:    */       try
/* 228:    */       {
/* 229:281 */         if (statement != null) {
/* 230:281 */           statement.close();
/* 231:    */         }
/* 232:    */       }
/* 233:    */       catch (SQLException e1) {}
/* 234:284 */       closeConnection();
/* 235:    */     }
/* 236:    */   }
/* 237:    */   
/* 238:    */   protected String getCountQuery()
/* 239:    */   {
/* 240:292 */     if (this.dbConf.getInputCountQuery() != null) {
/* 241:293 */       return this.dbConf.getInputCountQuery();
/* 242:    */     }
/* 243:296 */     StringBuilder query = new StringBuilder();
/* 244:297 */     query.append("SELECT COUNT(*) FROM " + this.tableName);
/* 245:299 */     if ((this.conditions != null) && (this.conditions.length() > 0)) {
/* 246:300 */       query.append(" WHERE " + this.conditions);
/* 247:    */     }
/* 248:301 */     return query.toString();
/* 249:    */   }
/* 250:    */   
/* 251:    */   public static void setInput(Job job, Class<? extends DBWritable> inputClass, String tableName, String conditions, String orderBy, String... fieldNames)
/* 252:    */   {
/* 253:321 */     job.setInputFormatClass(DBInputFormat.class);
/* 254:322 */     DBConfiguration dbConf = new DBConfiguration(job.getConfiguration());
/* 255:323 */     dbConf.setInputClass(inputClass);
/* 256:324 */     dbConf.setInputTableName(tableName);
/* 257:325 */     dbConf.setInputFieldNames(fieldNames);
/* 258:326 */     dbConf.setInputConditions(conditions);
/* 259:327 */     dbConf.setInputOrderBy(orderBy);
/* 260:    */   }
/* 261:    */   
/* 262:    */   public static void setInput(Job job, Class<? extends DBWritable> inputClass, String inputQuery, String inputCountQuery)
/* 263:    */   {
/* 264:346 */     job.setInputFormatClass(DBInputFormat.class);
/* 265:347 */     DBConfiguration dbConf = new DBConfiguration(job.getConfiguration());
/* 266:348 */     dbConf.setInputClass(inputClass);
/* 267:349 */     dbConf.setInputQuery(inputQuery);
/* 268:350 */     dbConf.setInputCountQuery(inputCountQuery);
/* 269:    */   }
/* 270:    */   
/* 271:    */   protected void closeConnection()
/* 272:    */   {
/* 273:    */     try
/* 274:    */     {
/* 275:355 */       if (null != this.connection)
/* 276:    */       {
/* 277:356 */         this.connection.close();
/* 278:357 */         this.connection = null;
/* 279:    */       }
/* 280:    */     }
/* 281:    */     catch (SQLException sqlE)
/* 282:    */     {
/* 283:360 */       LOG.debug("Exception on close", sqlE);
/* 284:    */     }
/* 285:    */   }
/* 286:    */   
/* 287:    */   @InterfaceStability.Evolving
/* 288:    */   public static class NullDBWritable
/* 289:    */     implements DBWritable, Writable
/* 290:    */   {
/* 291:    */     public void readFields(DataInput in)
/* 292:    */       throws IOException
/* 293:    */     {}
/* 294:    */     
/* 295:    */     public void readFields(ResultSet arg0)
/* 296:    */       throws SQLException
/* 297:    */     {}
/* 298:    */     
/* 299:    */     public void write(DataOutput out)
/* 300:    */       throws IOException
/* 301:    */     {}
/* 302:    */     
/* 303:    */     public void write(PreparedStatement arg0)
/* 304:    */       throws SQLException
/* 305:    */     {}
/* 306:    */   }
/* 307:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.lib.db.DBInputFormat
 * JD-Core Version:    0.7.0.1
 */