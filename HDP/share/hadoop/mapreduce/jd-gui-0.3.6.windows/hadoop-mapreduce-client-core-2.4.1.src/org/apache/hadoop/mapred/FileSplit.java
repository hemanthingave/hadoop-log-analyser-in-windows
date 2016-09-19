/*  1:   */ package org.apache.hadoop.mapred;
/*  2:   */ 
/*  3:   */ import java.io.DataInput;
/*  4:   */ import java.io.DataOutput;
/*  5:   */ import java.io.IOException;
/*  6:   */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*  7:   */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*  8:   */ import org.apache.hadoop.fs.Path;
/*  9:   */ 
/* 10:   */ @InterfaceAudience.Public
/* 11:   */ @InterfaceStability.Stable
/* 12:   */ public class FileSplit
/* 13:   */   extends org.apache.hadoop.mapreduce.InputSplit
/* 14:   */   implements InputSplit
/* 15:   */ {
/* 16:   */   org.apache.hadoop.mapreduce.lib.input.FileSplit fs;
/* 17:   */   
/* 18:   */   protected FileSplit()
/* 19:   */   {
/* 20:39 */     this.fs = new org.apache.hadoop.mapreduce.lib.input.FileSplit();
/* 21:   */   }
/* 22:   */   
/* 23:   */   @Deprecated
/* 24:   */   public FileSplit(Path file, long start, long length, JobConf conf)
/* 25:   */   {
/* 26:50 */     this(file, start, length, (String[])null);
/* 27:   */   }
/* 28:   */   
/* 29:   */   public FileSplit(Path file, long start, long length, String[] hosts)
/* 30:   */   {
/* 31:61 */     this.fs = new org.apache.hadoop.mapreduce.lib.input.FileSplit(file, start, length, hosts);
/* 32:   */   }
/* 33:   */   
/* 34:   */   public FileSplit(org.apache.hadoop.mapreduce.lib.input.FileSplit fs)
/* 35:   */   {
/* 36:66 */     this.fs = fs;
/* 37:   */   }
/* 38:   */   
/* 39:   */   public Path getPath()
/* 40:   */   {
/* 41:70 */     return this.fs.getPath();
/* 42:   */   }
/* 43:   */   
/* 44:   */   public long getStart()
/* 45:   */   {
/* 46:73 */     return this.fs.getStart();
/* 47:   */   }
/* 48:   */   
/* 49:   */   public long getLength()
/* 50:   */   {
/* 51:76 */     return this.fs.getLength();
/* 52:   */   }
/* 53:   */   
/* 54:   */   public String toString()
/* 55:   */   {
/* 56:78 */     return this.fs.toString();
/* 57:   */   }
/* 58:   */   
/* 59:   */   public void write(DataOutput out)
/* 60:   */     throws IOException
/* 61:   */   {
/* 62:85 */     this.fs.write(out);
/* 63:   */   }
/* 64:   */   
/* 65:   */   public void readFields(DataInput in)
/* 66:   */     throws IOException
/* 67:   */   {
/* 68:88 */     this.fs.readFields(in);
/* 69:   */   }
/* 70:   */   
/* 71:   */   public String[] getLocations()
/* 72:   */     throws IOException
/* 73:   */   {
/* 74:92 */     return this.fs.getLocations();
/* 75:   */   }
/* 76:   */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.FileSplit
 * JD-Core Version:    0.7.0.1
 */