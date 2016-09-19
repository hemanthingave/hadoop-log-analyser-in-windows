/*  1:   */ package org.apache.hadoop.mapreduce.lib.input;
/*  2:   */ 
/*  3:   */ import java.io.DataInput;
/*  4:   */ import java.io.DataOutput;
/*  5:   */ import java.io.IOException;
/*  6:   */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*  7:   */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*  8:   */ import org.apache.hadoop.fs.Path;
/*  9:   */ import org.apache.hadoop.io.Text;
/* 10:   */ import org.apache.hadoop.io.Writable;
/* 11:   */ import org.apache.hadoop.mapreduce.InputSplit;
/* 12:   */ 
/* 13:   */ @InterfaceAudience.Public
/* 14:   */ @InterfaceStability.Stable
/* 15:   */ public class FileSplit
/* 16:   */   extends InputSplit
/* 17:   */   implements Writable
/* 18:   */ {
/* 19:   */   private Path file;
/* 20:   */   private long start;
/* 21:   */   private long length;
/* 22:   */   private String[] hosts;
/* 23:   */   
/* 24:   */   public FileSplit() {}
/* 25:   */   
/* 26:   */   public FileSplit(Path file, long start, long length, String[] hosts)
/* 27:   */   {
/* 28:55 */     this.file = file;
/* 29:56 */     this.start = start;
/* 30:57 */     this.length = length;
/* 31:58 */     this.hosts = hosts;
/* 32:   */   }
/* 33:   */   
/* 34:   */   public Path getPath()
/* 35:   */   {
/* 36:62 */     return this.file;
/* 37:   */   }
/* 38:   */   
/* 39:   */   public long getStart()
/* 40:   */   {
/* 41:65 */     return this.start;
/* 42:   */   }
/* 43:   */   
/* 44:   */   public long getLength()
/* 45:   */   {
/* 46:69 */     return this.length;
/* 47:   */   }
/* 48:   */   
/* 49:   */   public String toString()
/* 50:   */   {
/* 51:72 */     return this.file + ":" + this.start + "+" + this.length;
/* 52:   */   }
/* 53:   */   
/* 54:   */   public void write(DataOutput out)
/* 55:   */     throws IOException
/* 56:   */   {
/* 57:80 */     Text.writeString(out, this.file.toString());
/* 58:81 */     out.writeLong(this.start);
/* 59:82 */     out.writeLong(this.length);
/* 60:   */   }
/* 61:   */   
/* 62:   */   public void readFields(DataInput in)
/* 63:   */     throws IOException
/* 64:   */   {
/* 65:87 */     this.file = new Path(Text.readString(in));
/* 66:88 */     this.start = in.readLong();
/* 67:89 */     this.length = in.readLong();
/* 68:90 */     this.hosts = null;
/* 69:   */   }
/* 70:   */   
/* 71:   */   public String[] getLocations()
/* 72:   */     throws IOException
/* 73:   */   {
/* 74:95 */     if (this.hosts == null) {
/* 75:96 */       return new String[0];
/* 76:   */     }
/* 77:98 */     return this.hosts;
/* 78:   */   }
/* 79:   */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.lib.input.FileSplit
 * JD-Core Version:    0.7.0.1
 */