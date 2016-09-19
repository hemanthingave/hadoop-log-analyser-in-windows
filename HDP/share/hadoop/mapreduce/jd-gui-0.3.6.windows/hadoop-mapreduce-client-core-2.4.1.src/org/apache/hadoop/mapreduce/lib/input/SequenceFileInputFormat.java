/*  1:   */ package org.apache.hadoop.mapreduce.lib.input;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import java.util.List;
/*  5:   */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*  6:   */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*  7:   */ import org.apache.hadoop.fs.FileStatus;
/*  8:   */ import org.apache.hadoop.fs.FileSystem;
/*  9:   */ import org.apache.hadoop.fs.Path;
/* 10:   */ import org.apache.hadoop.mapreduce.InputSplit;
/* 11:   */ import org.apache.hadoop.mapreduce.JobContext;
/* 12:   */ import org.apache.hadoop.mapreduce.RecordReader;
/* 13:   */ import org.apache.hadoop.mapreduce.TaskAttemptContext;
/* 14:   */ 
/* 15:   */ @InterfaceAudience.Public
/* 16:   */ @InterfaceStability.Stable
/* 17:   */ public class SequenceFileInputFormat<K, V>
/* 18:   */   extends FileInputFormat<K, V>
/* 19:   */ {
/* 20:   */   public RecordReader<K, V> createRecordReader(InputSplit split, TaskAttemptContext context)
/* 21:   */     throws IOException
/* 22:   */   {
/* 23:47 */     return new SequenceFileRecordReader();
/* 24:   */   }
/* 25:   */   
/* 26:   */   protected long getFormatMinSplitSize()
/* 27:   */   {
/* 28:52 */     return 2000L;
/* 29:   */   }
/* 30:   */   
/* 31:   */   protected List<FileStatus> listStatus(JobContext job)
/* 32:   */     throws IOException
/* 33:   */   {
/* 34:59 */     List<FileStatus> files = super.listStatus(job);
/* 35:60 */     int len = files.size();
/* 36:61 */     for (int i = 0; i < len; i++)
/* 37:   */     {
/* 38:62 */       FileStatus file = (FileStatus)files.get(i);
/* 39:63 */       if (file.isDirectory())
/* 40:   */       {
/* 41:64 */         Path p = file.getPath();
/* 42:65 */         FileSystem fs = p.getFileSystem(job.getConfiguration());
/* 43:   */         
/* 44:67 */         files.set(i, fs.getFileStatus(new Path(p, "data")));
/* 45:   */       }
/* 46:   */     }
/* 47:70 */     return files;
/* 48:   */   }
/* 49:   */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.lib.input.SequenceFileInputFormat
 * JD-Core Version:    0.7.0.1
 */