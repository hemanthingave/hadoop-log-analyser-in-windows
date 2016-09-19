/*   1:    */ package org.apache.hadoop.mapreduce.lib.db;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.sql.Connection;
/*   5:    */ import java.sql.PreparedStatement;
/*   6:    */ import java.sql.SQLException;
/*   7:    */ import org.apache.commons.logging.Log;
/*   8:    */ import org.apache.commons.logging.LogFactory;
/*   9:    */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*  10:    */ import org.apache.hadoop.classification.InterfaceStability.Evolving;
/*  11:    */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*  12:    */ import org.apache.hadoop.mapreduce.Job;
/*  13:    */ import org.apache.hadoop.mapreduce.JobContext;
/*  14:    */ import org.apache.hadoop.mapreduce.OutputCommitter;
/*  15:    */ import org.apache.hadoop.mapreduce.OutputFormat;
/*  16:    */ import org.apache.hadoop.mapreduce.RecordWriter;
/*  17:    */ import org.apache.hadoop.mapreduce.TaskAttemptContext;
/*  18:    */ import org.apache.hadoop.mapreduce.lib.output.FileOutputCommitter;
/*  19:    */ import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
/*  20:    */ import org.apache.hadoop.util.StringUtils;
/*  21:    */ 
/*  22:    */ @InterfaceAudience.Public
/*  23:    */ @InterfaceStability.Stable
/*  24:    */ public class DBOutputFormat<K extends DBWritable, V>
/*  25:    */   extends OutputFormat<K, V>
/*  26:    */ {
/*  27: 53 */   private static final Log LOG = LogFactory.getLog(DBOutputFormat.class);
/*  28:    */   
/*  29:    */   public void checkOutputSpecs(JobContext context)
/*  30:    */     throws IOException, InterruptedException
/*  31:    */   {}
/*  32:    */   
/*  33:    */   public OutputCommitter getOutputCommitter(TaskAttemptContext context)
/*  34:    */     throws IOException, InterruptedException
/*  35:    */   {
/*  36: 59 */     return new FileOutputCommitter(FileOutputFormat.getOutputPath(context), context);
/*  37:    */   }
/*  38:    */   
/*  39:    */   @InterfaceStability.Evolving
/*  40:    */   public class DBRecordWriter
/*  41:    */     extends RecordWriter<K, V>
/*  42:    */   {
/*  43:    */     private Connection connection;
/*  44:    */     private PreparedStatement statement;
/*  45:    */     
/*  46:    */     public DBRecordWriter()
/*  47:    */       throws SQLException
/*  48:    */     {}
/*  49:    */     
/*  50:    */     public DBRecordWriter(Connection connection, PreparedStatement statement)
/*  51:    */       throws SQLException
/*  52:    */     {
/*  53: 78 */       this.connection = connection;
/*  54: 79 */       this.statement = statement;
/*  55: 80 */       this.connection.setAutoCommit(false);
/*  56:    */     }
/*  57:    */     
/*  58:    */     public Connection getConnection()
/*  59:    */     {
/*  60: 84 */       return this.connection;
/*  61:    */     }
/*  62:    */     
/*  63:    */     public PreparedStatement getStatement()
/*  64:    */     {
/*  65: 88 */       return this.statement;
/*  66:    */     }
/*  67:    */     
/*  68:    */     public void close(TaskAttemptContext context)
/*  69:    */       throws IOException
/*  70:    */     {
/*  71:    */       try
/*  72:    */       {
/*  73: 94 */         this.statement.executeBatch();
/*  74: 95 */         this.connection.commit(); return;
/*  75:    */       }
/*  76:    */       catch (SQLException e)
/*  77:    */       {
/*  78:    */         try
/*  79:    */         {
/*  80: 98 */           this.connection.rollback();
/*  81:    */         }
/*  82:    */         catch (SQLException ex)
/*  83:    */         {
/*  84:101 */           DBOutputFormat.LOG.warn(StringUtils.stringifyException(ex));
/*  85:    */         }
/*  86:103 */         throw new IOException(e.getMessage());
/*  87:    */       }
/*  88:    */       finally
/*  89:    */       {
/*  90:    */         try
/*  91:    */         {
/*  92:106 */           this.statement.close();
/*  93:107 */           this.connection.close();
/*  94:    */         }
/*  95:    */         catch (SQLException ex)
/*  96:    */         {
/*  97:110 */           throw new IOException(ex.getMessage());
/*  98:    */         }
/*  99:    */       }
/* 100:    */     }
/* 101:    */     
/* 102:    */     public void write(K key, V value)
/* 103:    */       throws IOException
/* 104:    */     {
/* 105:    */       try
/* 106:    */       {
/* 107:118 */         key.write(this.statement);
/* 108:119 */         this.statement.addBatch();
/* 109:    */       }
/* 110:    */       catch (SQLException e)
/* 111:    */       {
/* 112:121 */         e.printStackTrace();
/* 113:    */       }
/* 114:    */     }
/* 115:    */   }
/* 116:    */   
/* 117:    */   public String constructQuery(String table, String[] fieldNames)
/* 118:    */   {
/* 119:136 */     if (fieldNames == null) {
/* 120:137 */       throw new IllegalArgumentException("Field names may not be null");
/* 121:    */     }
/* 122:140 */     StringBuilder query = new StringBuilder();
/* 123:141 */     query.append("INSERT INTO ").append(table);
/* 124:143 */     if ((fieldNames.length > 0) && (fieldNames[0] != null))
/* 125:    */     {
/* 126:144 */       query.append(" (");
/* 127:145 */       for (int i = 0; i < fieldNames.length; i++)
/* 128:    */       {
/* 129:146 */         query.append(fieldNames[i]);
/* 130:147 */         if (i != fieldNames.length - 1) {
/* 131:148 */           query.append(",");
/* 132:    */         }
/* 133:    */       }
/* 134:151 */       query.append(")");
/* 135:    */     }
/* 136:153 */     query.append(" VALUES (");
/* 137:155 */     for (int i = 0; i < fieldNames.length; i++)
/* 138:    */     {
/* 139:156 */       query.append("?");
/* 140:157 */       if (i != fieldNames.length - 1) {
/* 141:158 */         query.append(",");
/* 142:    */       }
/* 143:    */     }
/* 144:161 */     query.append(");");
/* 145:    */     
/* 146:163 */     return query.toString();
/* 147:    */   }
/* 148:    */   
/* 149:    */   public RecordWriter<K, V> getRecordWriter(TaskAttemptContext context)
/* 150:    */     throws IOException
/* 151:    */   {
/* 152:169 */     DBConfiguration dbConf = new DBConfiguration(context.getConfiguration());
/* 153:170 */     String tableName = dbConf.getOutputTableName();
/* 154:171 */     String[] fieldNames = dbConf.getOutputFieldNames();
/* 155:173 */     if (fieldNames == null) {
/* 156:174 */       fieldNames = new String[dbConf.getOutputFieldCount()];
/* 157:    */     }
/* 158:    */     try
/* 159:    */     {
/* 160:178 */       Connection connection = dbConf.getConnection();
/* 161:179 */       PreparedStatement statement = null;
/* 162:    */       
/* 163:181 */       statement = connection.prepareStatement(constructQuery(tableName, fieldNames));
/* 164:    */       
/* 165:183 */       return new DBRecordWriter(connection, statement);
/* 166:    */     }
/* 167:    */     catch (Exception ex)
/* 168:    */     {
/* 169:185 */       throw new IOException(ex.getMessage());
/* 170:    */     }
/* 171:    */   }
/* 172:    */   
/* 173:    */   public static void setOutput(Job job, String tableName, String... fieldNames)
/* 174:    */     throws IOException
/* 175:    */   {
/* 176:199 */     if ((fieldNames.length > 0) && (fieldNames[0] != null))
/* 177:    */     {
/* 178:200 */       DBConfiguration dbConf = setOutput(job, tableName);
/* 179:201 */       dbConf.setOutputFieldNames(fieldNames);
/* 180:    */     }
/* 181:203 */     else if (fieldNames.length > 0)
/* 182:    */     {
/* 183:204 */       setOutput(job, tableName, fieldNames.length);
/* 184:    */     }
/* 185:    */     else
/* 186:    */     {
/* 187:207 */       throw new IllegalArgumentException("Field names must be greater than 0");
/* 188:    */     }
/* 189:    */   }
/* 190:    */   
/* 191:    */   public static void setOutput(Job job, String tableName, int fieldCount)
/* 192:    */     throws IOException
/* 193:    */   {
/* 194:223 */     DBConfiguration dbConf = setOutput(job, tableName);
/* 195:224 */     dbConf.setOutputFieldCount(fieldCount);
/* 196:    */   }
/* 197:    */   
/* 198:    */   private static DBConfiguration setOutput(Job job, String tableName)
/* 199:    */     throws IOException
/* 200:    */   {
/* 201:229 */     job.setOutputFormatClass(DBOutputFormat.class);
/* 202:230 */     job.setReduceSpeculativeExecution(false);
/* 203:    */     
/* 204:232 */     DBConfiguration dbConf = new DBConfiguration(job.getConfiguration());
/* 205:    */     
/* 206:234 */     dbConf.setOutputTableName(tableName);
/* 207:235 */     return dbConf;
/* 208:    */   }
/* 209:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.lib.db.DBOutputFormat
 * JD-Core Version:    0.7.0.1
 */