/*   1:    */ package org.apache.hadoop.mapreduce.lib.db;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.sql.Connection;
/*   5:    */ import java.sql.PreparedStatement;
/*   6:    */ import java.sql.ResultSet;
/*   7:    */ import java.sql.SQLException;
/*   8:    */ import org.apache.commons.logging.Log;
/*   9:    */ import org.apache.commons.logging.LogFactory;
/*  10:    */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*  11:    */ import org.apache.hadoop.classification.InterfaceStability.Evolving;
/*  12:    */ import org.apache.hadoop.conf.Configuration;
/*  13:    */ import org.apache.hadoop.io.LongWritable;
/*  14:    */ import org.apache.hadoop.mapreduce.InputSplit;
/*  15:    */ import org.apache.hadoop.mapreduce.RecordReader;
/*  16:    */ import org.apache.hadoop.mapreduce.TaskAttemptContext;
/*  17:    */ import org.apache.hadoop.util.ReflectionUtils;
/*  18:    */ 
/*  19:    */ @InterfaceAudience.Public
/*  20:    */ @InterfaceStability.Evolving
/*  21:    */ public class DBRecordReader<T extends DBWritable>
/*  22:    */   extends RecordReader<LongWritable, T>
/*  23:    */ {
/*  24: 59 */   private static final Log LOG = LogFactory.getLog(DBRecordReader.class);
/*  25: 61 */   private ResultSet results = null;
/*  26:    */   private Class<T> inputClass;
/*  27:    */   private Configuration conf;
/*  28:    */   private DBInputFormat.DBInputSplit split;
/*  29: 69 */   private long pos = 0L;
/*  30: 71 */   private LongWritable key = null;
/*  31: 73 */   private T value = null;
/*  32:    */   private Connection connection;
/*  33:    */   protected PreparedStatement statement;
/*  34:    */   private DBConfiguration dbConf;
/*  35:    */   private String conditions;
/*  36:    */   private String[] fieldNames;
/*  37:    */   private String tableName;
/*  38:    */   
/*  39:    */   public DBRecordReader(DBInputFormat.DBInputSplit split, Class<T> inputClass, Configuration conf, Connection conn, DBConfiguration dbConfig, String cond, String[] fields, String table)
/*  40:    */     throws SQLException
/*  41:    */   {
/*  42: 95 */     this.inputClass = inputClass;
/*  43: 96 */     this.split = split;
/*  44: 97 */     this.conf = conf;
/*  45: 98 */     this.connection = conn;
/*  46: 99 */     this.dbConf = dbConfig;
/*  47:100 */     this.conditions = cond;
/*  48:101 */     this.fieldNames = fields;
/*  49:102 */     this.tableName = table;
/*  50:    */   }
/*  51:    */   
/*  52:    */   protected ResultSet executeQuery(String query)
/*  53:    */     throws SQLException
/*  54:    */   {
/*  55:106 */     this.statement = this.connection.prepareStatement(query, 1003, 1007);
/*  56:    */     
/*  57:108 */     return this.statement.executeQuery();
/*  58:    */   }
/*  59:    */   
/*  60:    */   protected String getSelectQuery()
/*  61:    */   {
/*  62:114 */     StringBuilder query = new StringBuilder();
/*  63:117 */     if (this.dbConf.getInputQuery() == null)
/*  64:    */     {
/*  65:118 */       query.append("SELECT ");
/*  66:120 */       for (int i = 0; i < this.fieldNames.length; i++)
/*  67:    */       {
/*  68:121 */         query.append(this.fieldNames[i]);
/*  69:122 */         if (i != this.fieldNames.length - 1) {
/*  70:123 */           query.append(", ");
/*  71:    */         }
/*  72:    */       }
/*  73:127 */       query.append(" FROM ").append(this.tableName);
/*  74:128 */       query.append(" AS ").append(this.tableName);
/*  75:129 */       if ((this.conditions != null) && (this.conditions.length() > 0)) {
/*  76:130 */         query.append(" WHERE (").append(this.conditions).append(")");
/*  77:    */       }
/*  78:133 */       String orderBy = this.dbConf.getInputOrderBy();
/*  79:134 */       if ((orderBy != null) && (orderBy.length() > 0)) {
/*  80:135 */         query.append(" ORDER BY ").append(orderBy);
/*  81:    */       }
/*  82:    */     }
/*  83:    */     else
/*  84:    */     {
/*  85:139 */       query.append(this.dbConf.getInputQuery());
/*  86:    */     }
/*  87:    */     try
/*  88:    */     {
/*  89:143 */       query.append(" LIMIT ").append(this.split.getLength());
/*  90:144 */       query.append(" OFFSET ").append(this.split.getStart());
/*  91:    */     }
/*  92:    */     catch (IOException ex) {}
/*  93:149 */     return query.toString();
/*  94:    */   }
/*  95:    */   
/*  96:    */   public void close()
/*  97:    */     throws IOException
/*  98:    */   {
/*  99:    */     try
/* 100:    */     {
/* 101:155 */       if (null != this.results) {
/* 102:156 */         this.results.close();
/* 103:    */       }
/* 104:158 */       if (null != this.statement) {
/* 105:159 */         this.statement.close();
/* 106:    */       }
/* 107:161 */       if (null != this.connection)
/* 108:    */       {
/* 109:162 */         this.connection.commit();
/* 110:163 */         this.connection.close();
/* 111:    */       }
/* 112:    */     }
/* 113:    */     catch (SQLException e)
/* 114:    */     {
/* 115:166 */       throw new IOException(e.getMessage());
/* 116:    */     }
/* 117:    */   }
/* 118:    */   
/* 119:    */   public void initialize(InputSplit split, TaskAttemptContext context)
/* 120:    */     throws IOException, InterruptedException
/* 121:    */   {}
/* 122:    */   
/* 123:    */   public LongWritable getCurrentKey()
/* 124:    */   {
/* 125:177 */     return this.key;
/* 126:    */   }
/* 127:    */   
/* 128:    */   public T getCurrentValue()
/* 129:    */   {
/* 130:182 */     return this.value;
/* 131:    */   }
/* 132:    */   
/* 133:    */   @Deprecated
/* 134:    */   public T createValue()
/* 135:    */   {
/* 136:190 */     return (DBWritable)ReflectionUtils.newInstance(this.inputClass, this.conf);
/* 137:    */   }
/* 138:    */   
/* 139:    */   @Deprecated
/* 140:    */   public long getPos()
/* 141:    */     throws IOException
/* 142:    */   {
/* 143:198 */     return this.pos;
/* 144:    */   }
/* 145:    */   
/* 146:    */   @Deprecated
/* 147:    */   public boolean next(LongWritable key, T value)
/* 148:    */     throws IOException
/* 149:    */   {
/* 150:206 */     this.key = key;
/* 151:207 */     this.value = value;
/* 152:208 */     return nextKeyValue();
/* 153:    */   }
/* 154:    */   
/* 155:    */   public float getProgress()
/* 156:    */     throws IOException
/* 157:    */   {
/* 158:213 */     return (float)this.pos / (float)this.split.getLength();
/* 159:    */   }
/* 160:    */   
/* 161:    */   public boolean nextKeyValue()
/* 162:    */     throws IOException
/* 163:    */   {
/* 164:    */     try
/* 165:    */     {
/* 166:219 */       if (this.key == null) {
/* 167:220 */         this.key = new LongWritable();
/* 168:    */       }
/* 169:222 */       if (this.value == null) {
/* 170:223 */         this.value = createValue();
/* 171:    */       }
/* 172:225 */       if (null == this.results) {
/* 173:227 */         this.results = executeQuery(getSelectQuery());
/* 174:    */       }
/* 175:229 */       if (!this.results.next()) {
/* 176:230 */         return false;
/* 177:    */       }
/* 178:233 */       this.key.set(this.pos + this.split.getStart());
/* 179:    */       
/* 180:235 */       this.value.readFields(this.results);
/* 181:    */       
/* 182:237 */       this.pos += 1L;
/* 183:    */     }
/* 184:    */     catch (SQLException e)
/* 185:    */     {
/* 186:239 */       throw new IOException("SQLException in nextKeyValue", e);
/* 187:    */     }
/* 188:241 */     return true;
/* 189:    */   }
/* 190:    */   
/* 191:    */   protected DBInputFormat.DBInputSplit getSplit()
/* 192:    */   {
/* 193:245 */     return this.split;
/* 194:    */   }
/* 195:    */   
/* 196:    */   protected String[] getFieldNames()
/* 197:    */   {
/* 198:249 */     return this.fieldNames;
/* 199:    */   }
/* 200:    */   
/* 201:    */   protected String getTableName()
/* 202:    */   {
/* 203:253 */     return this.tableName;
/* 204:    */   }
/* 205:    */   
/* 206:    */   protected String getConditions()
/* 207:    */   {
/* 208:257 */     return this.conditions;
/* 209:    */   }
/* 210:    */   
/* 211:    */   protected DBConfiguration getDBConf()
/* 212:    */   {
/* 213:261 */     return this.dbConf;
/* 214:    */   }
/* 215:    */   
/* 216:    */   protected Connection getConnection()
/* 217:    */   {
/* 218:265 */     return this.connection;
/* 219:    */   }
/* 220:    */   
/* 221:    */   protected PreparedStatement getStatement()
/* 222:    */   {
/* 223:269 */     return this.statement;
/* 224:    */   }
/* 225:    */   
/* 226:    */   protected void setStatement(PreparedStatement stmt)
/* 227:    */   {
/* 228:273 */     this.statement = stmt;
/* 229:    */   }
/* 230:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.lib.db.DBRecordReader
 * JD-Core Version:    0.7.0.1
 */