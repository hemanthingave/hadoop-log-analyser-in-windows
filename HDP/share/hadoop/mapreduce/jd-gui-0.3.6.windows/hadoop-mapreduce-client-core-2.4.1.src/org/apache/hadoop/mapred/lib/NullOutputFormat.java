/*  1:   */ package org.apache.hadoop.mapred.lib;
/*  2:   */ 
/*  3:   */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*  4:   */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*  5:   */ import org.apache.hadoop.fs.FileSystem;
/*  6:   */ import org.apache.hadoop.mapred.JobConf;
/*  7:   */ import org.apache.hadoop.mapred.OutputFormat;
/*  8:   */ import org.apache.hadoop.mapred.RecordWriter;
/*  9:   */ import org.apache.hadoop.mapred.Reporter;
/* 10:   */ import org.apache.hadoop.util.Progressable;
/* 11:   */ 
/* 12:   */ @InterfaceAudience.Public
/* 13:   */ @InterfaceStability.Stable
/* 14:   */ public class NullOutputFormat<K, V>
/* 15:   */   implements OutputFormat<K, V>
/* 16:   */ {
/* 17:   */   public RecordWriter<K, V> getRecordWriter(FileSystem ignored, JobConf job, String name, Progressable progress)
/* 18:   */   {
/* 19:39 */     new RecordWriter()
/* 20:   */     {
/* 21:   */       public void write(K key, V value) {}
/* 22:   */       
/* 23:   */       public void close(Reporter reporter) {}
/* 24:   */     };
/* 25:   */   }
/* 26:   */   
/* 27:   */   public void checkOutputSpecs(FileSystem ignored, JobConf job) {}
/* 28:   */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.lib.NullOutputFormat
 * JD-Core Version:    0.7.0.1
 */