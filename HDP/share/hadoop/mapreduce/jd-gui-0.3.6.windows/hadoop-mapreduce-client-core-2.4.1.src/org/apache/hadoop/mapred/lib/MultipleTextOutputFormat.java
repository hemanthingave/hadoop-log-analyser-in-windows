/*  1:   */ package org.apache.hadoop.mapred.lib;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*  5:   */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*  6:   */ import org.apache.hadoop.fs.FileSystem;
/*  7:   */ import org.apache.hadoop.mapred.JobConf;
/*  8:   */ import org.apache.hadoop.mapred.RecordWriter;
/*  9:   */ import org.apache.hadoop.mapred.TextOutputFormat;
/* 10:   */ import org.apache.hadoop.util.Progressable;
/* 11:   */ 
/* 12:   */ @InterfaceAudience.Public
/* 13:   */ @InterfaceStability.Stable
/* 14:   */ public class MultipleTextOutputFormat<K, V>
/* 15:   */   extends MultipleOutputFormat<K, V>
/* 16:   */ {
/* 17:40 */   private TextOutputFormat<K, V> theTextOutputFormat = null;
/* 18:   */   
/* 19:   */   protected RecordWriter<K, V> getBaseRecordWriter(FileSystem fs, JobConf job, String name, Progressable arg3)
/* 20:   */     throws IOException
/* 21:   */   {
/* 22:45 */     if (this.theTextOutputFormat == null) {
/* 23:46 */       this.theTextOutputFormat = new TextOutputFormat();
/* 24:   */     }
/* 25:48 */     return this.theTextOutputFormat.getRecordWriter(fs, job, name, arg3);
/* 26:   */   }
/* 27:   */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.lib.MultipleTextOutputFormat
 * JD-Core Version:    0.7.0.1
 */