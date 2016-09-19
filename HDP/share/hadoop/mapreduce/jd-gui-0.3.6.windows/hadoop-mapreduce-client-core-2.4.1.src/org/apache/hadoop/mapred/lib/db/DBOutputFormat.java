/*   1:    */ package org.apache.hadoop.mapred.lib.db;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.sql.Connection;
/*   5:    */ import java.sql.PreparedStatement;
/*   6:    */ import java.sql.SQLException;
/*   7:    */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*   8:    */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*   9:    */ import org.apache.hadoop.fs.FileSystem;
/*  10:    */ import org.apache.hadoop.mapred.JobConf;
/*  11:    */ import org.apache.hadoop.mapred.OutputFormat;
/*  12:    */ import org.apache.hadoop.mapred.Reporter;
/*  13:    */ import org.apache.hadoop.mapreduce.TaskAttemptID;
/*  14:    */ import org.apache.hadoop.mapreduce.task.TaskAttemptContextImpl;
/*  15:    */ import org.apache.hadoop.util.Progressable;
/*  16:    */ 
/*  17:    */ @InterfaceAudience.Public
/*  18:    */ @InterfaceStability.Stable
/*  19:    */ public class DBOutputFormat<K extends DBWritable, V>
/*  20:    */   extends org.apache.hadoop.mapreduce.lib.db.DBOutputFormat<K, V>
/*  21:    */   implements OutputFormat<K, V>
/*  22:    */ {
/*  23:    */   public void checkOutputSpecs(FileSystem filesystem, JobConf job)
/*  24:    */     throws IOException
/*  25:    */   {}
/*  26:    */   
/*  27:    */   protected class DBRecordWriter
/*  28:    */     extends org.apache.hadoop.mapreduce.lib.db.DBOutputFormat<K, V>.DBRecordWriter
/*  29:    */     implements org.apache.hadoop.mapred.RecordWriter<K, V>
/*  30:    */   {
/*  31:    */     protected DBRecordWriter(Connection connection, PreparedStatement statement)
/*  32:    */       throws SQLException
/*  33:    */     {
/*  34: 53 */       super(connection, statement);
/*  35:    */     }
/*  36:    */     
/*  37:    */     public void close(Reporter reporter)
/*  38:    */       throws IOException
/*  39:    */     {
/*  40: 58 */       super.close(null);
/*  41:    */     }
/*  42:    */   }
/*  43:    */   
/*  44:    */   public org.apache.hadoop.mapred.RecordWriter<K, V> getRecordWriter(FileSystem filesystem, JobConf job, String name, Progressable progress)
/*  45:    */     throws IOException
/*  46:    */   {
/*  47: 71 */     org.apache.hadoop.mapreduce.RecordWriter<K, V> w = super.getRecordWriter(new TaskAttemptContextImpl(job, TaskAttemptID.forName(job.get("mapreduce.task.attempt.id"))));
/*  48:    */     
/*  49:    */ 
/*  50: 74 */     org.apache.hadoop.mapreduce.lib.db.DBOutputFormat.DBRecordWriter writer = (org.apache.hadoop.mapreduce.lib.db.DBOutputFormat.DBRecordWriter)w;
/*  51:    */     try
/*  52:    */     {
/*  53: 77 */       return new DBRecordWriter(writer.getConnection(), writer.getStatement());
/*  54:    */     }
/*  55:    */     catch (SQLException se)
/*  56:    */     {
/*  57: 79 */       throw new IOException(se);
/*  58:    */     }
/*  59:    */   }
/*  60:    */   
/*  61:    */   public static void setOutput(JobConf job, String tableName, String... fieldNames)
/*  62:    */   {
/*  63: 91 */     if ((fieldNames.length > 0) && (fieldNames[0] != null))
/*  64:    */     {
/*  65: 92 */       DBConfiguration dbConf = setOutput(job, tableName);
/*  66: 93 */       dbConf.setOutputFieldNames(fieldNames);
/*  67:    */     }
/*  68: 95 */     else if (fieldNames.length > 0)
/*  69:    */     {
/*  70: 96 */       setOutput(job, tableName, fieldNames.length);
/*  71:    */     }
/*  72:    */     else
/*  73:    */     {
/*  74: 98 */       throw new IllegalArgumentException("Field names must be greater than 0");
/*  75:    */     }
/*  76:    */   }
/*  77:    */   
/*  78:    */   public static void setOutput(JobConf job, String tableName, int fieldCount)
/*  79:    */   {
/*  80:110 */     DBConfiguration dbConf = setOutput(job, tableName);
/*  81:111 */     dbConf.setOutputFieldCount(fieldCount);
/*  82:    */   }
/*  83:    */   
/*  84:    */   private static DBConfiguration setOutput(JobConf job, String tableName)
/*  85:    */   {
/*  86:115 */     job.setOutputFormat(DBOutputFormat.class);
/*  87:116 */     job.setReduceSpeculativeExecution(false);
/*  88:    */     
/*  89:118 */     DBConfiguration dbConf = new DBConfiguration(job);
/*  90:    */     
/*  91:120 */     dbConf.setOutputTableName(tableName);
/*  92:121 */     return dbConf;
/*  93:    */   }
/*  94:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.lib.db.DBOutputFormat
 * JD-Core Version:    0.7.0.1
 */