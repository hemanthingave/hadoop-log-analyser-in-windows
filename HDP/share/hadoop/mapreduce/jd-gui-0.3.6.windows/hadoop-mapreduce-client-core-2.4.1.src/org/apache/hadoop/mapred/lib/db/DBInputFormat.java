/*   1:    */ package org.apache.hadoop.mapred.lib.db;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.sql.Connection;
/*   5:    */ import java.sql.SQLException;
/*   6:    */ import java.util.List;
/*   7:    */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*   8:    */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*   9:    */ import org.apache.hadoop.io.LongWritable;
/*  10:    */ import org.apache.hadoop.io.Writable;
/*  11:    */ import org.apache.hadoop.mapred.InputFormat;
/*  12:    */ import org.apache.hadoop.mapred.JobConf;
/*  13:    */ import org.apache.hadoop.mapred.JobConfigurable;
/*  14:    */ import org.apache.hadoop.mapred.RecordReader;
/*  15:    */ import org.apache.hadoop.mapred.Reporter;
/*  16:    */ import org.apache.hadoop.mapreduce.Job;
/*  17:    */ import org.apache.hadoop.mapreduce.lib.db.DBRecordReader;
/*  18:    */ 
/*  19:    */ @InterfaceAudience.Public
/*  20:    */ @InterfaceStability.Stable
/*  21:    */ public class DBInputFormat<T extends DBWritable>
/*  22:    */   extends org.apache.hadoop.mapreduce.lib.db.DBInputFormat<T>
/*  23:    */   implements InputFormat<LongWritable, T>, JobConfigurable
/*  24:    */ {
/*  25:    */   protected class DBRecordReader
/*  26:    */     extends DBRecordReader<T>
/*  27:    */     implements RecordReader<LongWritable, T>
/*  28:    */   {
/*  29:    */     protected DBRecordReader(Class<T> split, JobConf inputClass)
/*  30:    */       throws SQLException
/*  31:    */     {
/*  32: 60 */       super(inputClass, job, DBInputFormat.this.connection, DBInputFormat.this.dbConf, DBInputFormat.this.conditions, DBInputFormat.this.fieldNames, DBInputFormat.this.tableName);
/*  33:    */     }
/*  34:    */     
/*  35:    */     protected DBRecordReader(Class<T> split, JobConf inputClass, Connection job, DBConfiguration conn, String dbConfig, String[] cond, String fields)
/*  36:    */       throws SQLException
/*  37:    */     {
/*  38: 70 */       super(inputClass, job, conn, dbConfig, cond, fields, table);
/*  39:    */     }
/*  40:    */     
/*  41:    */     public LongWritable createKey()
/*  42:    */     {
/*  43: 75 */       return new LongWritable();
/*  44:    */     }
/*  45:    */     
/*  46:    */     public T createValue()
/*  47:    */     {
/*  48: 80 */       return (DBWritable)super.createValue();
/*  49:    */     }
/*  50:    */     
/*  51:    */     public long getPos()
/*  52:    */       throws IOException
/*  53:    */     {
/*  54: 84 */       return super.getPos();
/*  55:    */     }
/*  56:    */     
/*  57:    */     public boolean next(LongWritable key, T value)
/*  58:    */       throws IOException
/*  59:    */     {
/*  60: 89 */       return super.next(key, value);
/*  61:    */     }
/*  62:    */   }
/*  63:    */   
/*  64:    */   private static class DBRecordReaderWrapper<T extends DBWritable>
/*  65:    */     implements RecordReader<LongWritable, T>
/*  66:    */   {
/*  67:    */     private DBRecordReader<T> rr;
/*  68:    */     
/*  69:    */     public DBRecordReaderWrapper(DBRecordReader<T> inner)
/*  70:    */     {
/*  71:104 */       this.rr = inner;
/*  72:    */     }
/*  73:    */     
/*  74:    */     public void close()
/*  75:    */       throws IOException
/*  76:    */     {
/*  77:108 */       this.rr.close();
/*  78:    */     }
/*  79:    */     
/*  80:    */     public LongWritable createKey()
/*  81:    */     {
/*  82:112 */       return new LongWritable();
/*  83:    */     }
/*  84:    */     
/*  85:    */     public T createValue()
/*  86:    */     {
/*  87:116 */       return (DBWritable)this.rr.createValue();
/*  88:    */     }
/*  89:    */     
/*  90:    */     public float getProgress()
/*  91:    */       throws IOException
/*  92:    */     {
/*  93:120 */       return this.rr.getProgress();
/*  94:    */     }
/*  95:    */     
/*  96:    */     public long getPos()
/*  97:    */       throws IOException
/*  98:    */     {
/*  99:124 */       return this.rr.getPos();
/* 100:    */     }
/* 101:    */     
/* 102:    */     public boolean next(LongWritable key, T value)
/* 103:    */       throws IOException
/* 104:    */     {
/* 105:128 */       return this.rr.next(key, value);
/* 106:    */     }
/* 107:    */   }
/* 108:    */   
/* 109:    */   public static class NullDBWritable
/* 110:    */     extends org.apache.hadoop.mapreduce.lib.db.DBInputFormat.NullDBWritable
/* 111:    */     implements DBWritable, Writable
/* 112:    */   {}
/* 113:    */   
/* 114:    */   protected static class DBInputSplit
/* 115:    */     extends org.apache.hadoop.mapreduce.lib.db.DBInputFormat.DBInputSplit
/* 116:    */     implements org.apache.hadoop.mapred.InputSplit
/* 117:    */   {
/* 118:    */     public DBInputSplit() {}
/* 119:    */     
/* 120:    */     public DBInputSplit(long start, long end)
/* 121:    */     {
/* 122:157 */       super(end);
/* 123:    */     }
/* 124:    */   }
/* 125:    */   
/* 126:    */   public void configure(JobConf job)
/* 127:    */   {
/* 128:163 */     super.setConf(job);
/* 129:    */   }
/* 130:    */   
/* 131:    */   public RecordReader<LongWritable, T> getRecordReader(org.apache.hadoop.mapred.InputSplit split, JobConf job, Reporter reporter)
/* 132:    */     throws IOException
/* 133:    */   {
/* 134:171 */     return new DBRecordReaderWrapper((DBRecordReader)createDBRecordReader((org.apache.hadoop.mapreduce.lib.db.DBInputFormat.DBInputSplit)split, job));
/* 135:    */   }
/* 136:    */   
/* 137:    */   public org.apache.hadoop.mapred.InputSplit[] getSplits(JobConf job, int chunks)
/* 138:    */     throws IOException
/* 139:    */   {
/* 140:179 */     List<org.apache.hadoop.mapreduce.InputSplit> newSplits = super.getSplits(new Job(job));
/* 141:    */     
/* 142:181 */     org.apache.hadoop.mapred.InputSplit[] ret = new org.apache.hadoop.mapred.InputSplit[newSplits.size()];
/* 143:182 */     int i = 0;
/* 144:183 */     for (org.apache.hadoop.mapreduce.InputSplit s : newSplits)
/* 145:    */     {
/* 146:184 */       org.apache.hadoop.mapreduce.lib.db.DBInputFormat.DBInputSplit split = (org.apache.hadoop.mapreduce.lib.db.DBInputFormat.DBInputSplit)s;
/* 147:    */       
/* 148:186 */       ret[(i++)] = new DBInputSplit(split.getStart(), split.getEnd());
/* 149:    */     }
/* 150:188 */     return ret;
/* 151:    */   }
/* 152:    */   
/* 153:    */   public static void setInput(JobConf job, Class<? extends DBWritable> inputClass, String tableName, String conditions, String orderBy, String... fieldNames)
/* 154:    */   {
/* 155:206 */     job.setInputFormat(DBInputFormat.class);
/* 156:    */     
/* 157:208 */     DBConfiguration dbConf = new DBConfiguration(job);
/* 158:209 */     dbConf.setInputClass(inputClass);
/* 159:210 */     dbConf.setInputTableName(tableName);
/* 160:211 */     dbConf.setInputFieldNames(fieldNames);
/* 161:212 */     dbConf.setInputConditions(conditions);
/* 162:213 */     dbConf.setInputOrderBy(orderBy);
/* 163:    */   }
/* 164:    */   
/* 165:    */   public static void setInput(JobConf job, Class<? extends DBWritable> inputClass, String inputQuery, String inputCountQuery)
/* 166:    */   {
/* 167:231 */     job.setInputFormat(DBInputFormat.class);
/* 168:    */     
/* 169:233 */     DBConfiguration dbConf = new DBConfiguration(job);
/* 170:234 */     dbConf.setInputClass(inputClass);
/* 171:235 */     dbConf.setInputQuery(inputQuery);
/* 172:236 */     dbConf.setInputCountQuery(inputCountQuery);
/* 173:    */   }
/* 174:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.lib.db.DBInputFormat
 * JD-Core Version:    0.7.0.1
 */