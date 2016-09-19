/*  1:   */ package org.apache.hadoop.mapred.lib;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*  5:   */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*  6:   */ import org.apache.hadoop.conf.Configuration;
/*  7:   */ import org.apache.hadoop.mapred.FileInputFormat;
/*  8:   */ import org.apache.hadoop.mapred.FileSplit;
/*  9:   */ import org.apache.hadoop.mapred.JobConf;
/* 10:   */ import org.apache.hadoop.mapred.RecordReader;
/* 11:   */ import org.apache.hadoop.mapred.Reporter;
/* 12:   */ 
/* 13:   */ @InterfaceAudience.Public
/* 14:   */ @InterfaceStability.Stable
/* 15:   */ public abstract class CombineFileRecordReaderWrapper<K, V>
/* 16:   */   implements RecordReader<K, V>
/* 17:   */ {
/* 18:   */   private final RecordReader<K, V> delegate;
/* 19:   */   
/* 20:   */   protected CombineFileRecordReaderWrapper(FileInputFormat<K, V> inputFormat, CombineFileSplit split, Configuration conf, Reporter reporter, Integer idx)
/* 21:   */     throws IOException
/* 22:   */   {
/* 23:55 */     FileSplit fileSplit = new FileSplit(split.getPath(idx.intValue()), split.getOffset(idx.intValue()), split.getLength(idx.intValue()), split.getLocations());
/* 24:   */     
/* 25:   */ 
/* 26:   */ 
/* 27:   */ 
/* 28:60 */     this.delegate = inputFormat.getRecordReader(fileSplit, (JobConf)conf, reporter);
/* 29:   */   }
/* 30:   */   
/* 31:   */   public boolean next(K key, V value)
/* 32:   */     throws IOException
/* 33:   */   {
/* 34:64 */     return this.delegate.next(key, value);
/* 35:   */   }
/* 36:   */   
/* 37:   */   public K createKey()
/* 38:   */   {
/* 39:68 */     return this.delegate.createKey();
/* 40:   */   }
/* 41:   */   
/* 42:   */   public V createValue()
/* 43:   */   {
/* 44:72 */     return this.delegate.createValue();
/* 45:   */   }
/* 46:   */   
/* 47:   */   public long getPos()
/* 48:   */     throws IOException
/* 49:   */   {
/* 50:76 */     return this.delegate.getPos();
/* 51:   */   }
/* 52:   */   
/* 53:   */   public void close()
/* 54:   */     throws IOException
/* 55:   */   {
/* 56:80 */     this.delegate.close();
/* 57:   */   }
/* 58:   */   
/* 59:   */   public float getProgress()
/* 60:   */     throws IOException
/* 61:   */   {
/* 62:84 */     return this.delegate.getProgress();
/* 63:   */   }
/* 64:   */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.lib.CombineFileRecordReaderWrapper
 * JD-Core Version:    0.7.0.1
 */