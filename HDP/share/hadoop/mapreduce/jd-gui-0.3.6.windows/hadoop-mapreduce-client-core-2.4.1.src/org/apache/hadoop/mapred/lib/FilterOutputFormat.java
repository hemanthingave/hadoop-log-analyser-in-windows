/*  1:   */ package org.apache.hadoop.mapred.lib;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*  5:   */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*  6:   */ import org.apache.hadoop.fs.FileSystem;
/*  7:   */ import org.apache.hadoop.mapred.JobConf;
/*  8:   */ import org.apache.hadoop.mapred.OutputFormat;
/*  9:   */ import org.apache.hadoop.mapred.RecordWriter;
/* 10:   */ import org.apache.hadoop.mapred.Reporter;
/* 11:   */ import org.apache.hadoop.util.Progressable;
/* 12:   */ 
/* 13:   */ @InterfaceAudience.Public
/* 14:   */ @InterfaceStability.Stable
/* 15:   */ public class FilterOutputFormat<K, V>
/* 16:   */   implements OutputFormat<K, V>
/* 17:   */ {
/* 18:   */   protected OutputFormat<K, V> baseOut;
/* 19:   */   
/* 20:   */   public FilterOutputFormat()
/* 21:   */   {
/* 22:42 */     this.baseOut = null;
/* 23:   */   }
/* 24:   */   
/* 25:   */   public FilterOutputFormat(OutputFormat<K, V> out)
/* 26:   */   {
/* 27:50 */     this.baseOut = out;
/* 28:   */   }
/* 29:   */   
/* 30:   */   public RecordWriter<K, V> getRecordWriter(FileSystem ignored, JobConf job, String name, Progressable progress)
/* 31:   */     throws IOException
/* 32:   */   {
/* 33:55 */     return getBaseOut().getRecordWriter(ignored, job, name, progress);
/* 34:   */   }
/* 35:   */   
/* 36:   */   public void checkOutputSpecs(FileSystem ignored, JobConf job)
/* 37:   */     throws IOException
/* 38:   */   {
/* 39:60 */     getBaseOut().checkOutputSpecs(ignored, job);
/* 40:   */   }
/* 41:   */   
/* 42:   */   private OutputFormat<K, V> getBaseOut()
/* 43:   */     throws IOException
/* 44:   */   {
/* 45:64 */     if (this.baseOut == null) {
/* 46:65 */       throw new IOException("Outputformat not set for FilterOutputFormat");
/* 47:   */     }
/* 48:67 */     return this.baseOut;
/* 49:   */   }
/* 50:   */   
/* 51:   */   public static class FilterRecordWriter<K, V>
/* 52:   */     implements RecordWriter<K, V>
/* 53:   */   {
/* 54:77 */     protected RecordWriter<K, V> rawWriter = null;
/* 55:   */     
/* 56:   */     public FilterRecordWriter()
/* 57:   */       throws IOException
/* 58:   */     {
/* 59:80 */       this.rawWriter = null;
/* 60:   */     }
/* 61:   */     
/* 62:   */     public FilterRecordWriter(RecordWriter<K, V> rawWriter)
/* 63:   */       throws IOException
/* 64:   */     {
/* 65:84 */       this.rawWriter = rawWriter;
/* 66:   */     }
/* 67:   */     
/* 68:   */     public void close(Reporter reporter)
/* 69:   */       throws IOException
/* 70:   */     {
/* 71:88 */       getRawWriter().close(reporter);
/* 72:   */     }
/* 73:   */     
/* 74:   */     public void write(K key, V value)
/* 75:   */       throws IOException
/* 76:   */     {
/* 77:92 */       getRawWriter().write(key, value);
/* 78:   */     }
/* 79:   */     
/* 80:   */     private RecordWriter<K, V> getRawWriter()
/* 81:   */       throws IOException
/* 82:   */     {
/* 83:96 */       if (this.rawWriter == null) {
/* 84:97 */         throw new IOException("Record Writer not set for FilterRecordWriter");
/* 85:   */       }
/* 86:99 */       return this.rawWriter;
/* 87:   */     }
/* 88:   */   }
/* 89:   */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.lib.FilterOutputFormat
 * JD-Core Version:    0.7.0.1
 */