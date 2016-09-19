/*  1:   */ package org.apache.hadoop.mapred;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*  5:   */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*  6:   */ import org.apache.hadoop.fs.FileStatus;
/*  7:   */ import org.apache.hadoop.fs.FileSystem;
/*  8:   */ import org.apache.hadoop.fs.Path;
/*  9:   */ 
/* 10:   */ @InterfaceAudience.Public
/* 11:   */ @InterfaceStability.Stable
/* 12:   */ public class SequenceFileInputFormat<K, V>
/* 13:   */   extends FileInputFormat<K, V>
/* 14:   */ {
/* 15:   */   public SequenceFileInputFormat()
/* 16:   */   {
/* 17:40 */     setMinSplitSize(2000L);
/* 18:   */   }
/* 19:   */   
/* 20:   */   protected FileStatus[] listStatus(JobConf job)
/* 21:   */     throws IOException
/* 22:   */   {
/* 23:45 */     FileStatus[] files = super.listStatus(job);
/* 24:46 */     for (int i = 0; i < files.length; i++)
/* 25:   */     {
/* 26:47 */       FileStatus file = files[i];
/* 27:48 */       if (file.isDirectory())
/* 28:   */       {
/* 29:49 */         Path dataFile = new Path(file.getPath(), "data");
/* 30:50 */         FileSystem fs = file.getPath().getFileSystem(job);
/* 31:   */         
/* 32:52 */         files[i] = fs.getFileStatus(dataFile);
/* 33:   */       }
/* 34:   */     }
/* 35:55 */     return files;
/* 36:   */   }
/* 37:   */   
/* 38:   */   public RecordReader<K, V> getRecordReader(InputSplit split, JobConf job, Reporter reporter)
/* 39:   */     throws IOException
/* 40:   */   {
/* 41:62 */     reporter.setStatus(split.toString());
/* 42:   */     
/* 43:64 */     return new SequenceFileRecordReader(job, (FileSplit)split);
/* 44:   */   }
/* 45:   */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.SequenceFileInputFormat
 * JD-Core Version:    0.7.0.1
 */