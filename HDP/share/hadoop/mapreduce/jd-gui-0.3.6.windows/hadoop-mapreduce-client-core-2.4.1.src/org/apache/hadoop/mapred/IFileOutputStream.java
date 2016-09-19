/*  1:   */ package org.apache.hadoop.mapred;
/*  2:   */ 
/*  3:   */ import java.io.FilterOutputStream;
/*  4:   */ import java.io.IOException;
/*  5:   */ import java.io.OutputStream;
/*  6:   */ import org.apache.hadoop.classification.InterfaceAudience.Private;
/*  7:   */ import org.apache.hadoop.classification.InterfaceStability.Unstable;
/*  8:   */ import org.apache.hadoop.util.DataChecksum;
/*  9:   */ import org.apache.hadoop.util.DataChecksum.Type;
/* 10:   */ 
/* 11:   */ @InterfaceAudience.Private
/* 12:   */ @InterfaceStability.Unstable
/* 13:   */ public class IFileOutputStream
/* 14:   */   extends FilterOutputStream
/* 15:   */ {
/* 16:   */   private final DataChecksum sum;
/* 17:   */   private byte[] barray;
/* 18:42 */   private boolean closed = false;
/* 19:43 */   private boolean finished = false;
/* 20:   */   
/* 21:   */   public IFileOutputStream(OutputStream out)
/* 22:   */   {
/* 23:51 */     super(out);
/* 24:52 */     this.sum = DataChecksum.newDataChecksum(DataChecksum.Type.CRC32, 2147483647);
/* 25:   */     
/* 26:54 */     this.barray = new byte[this.sum.getChecksumSize()];
/* 27:   */   }
/* 28:   */   
/* 29:   */   public void close()
/* 30:   */     throws IOException
/* 31:   */   {
/* 32:59 */     if (this.closed) {
/* 33:60 */       return;
/* 34:   */     }
/* 35:62 */     this.closed = true;
/* 36:63 */     finish();
/* 37:64 */     this.out.close();
/* 38:   */   }
/* 39:   */   
/* 40:   */   public void finish()
/* 41:   */     throws IOException
/* 42:   */   {
/* 43:73 */     if (this.finished) {
/* 44:74 */       return;
/* 45:   */     }
/* 46:76 */     this.finished = true;
/* 47:77 */     this.sum.writeValue(this.barray, 0, false);
/* 48:78 */     this.out.write(this.barray, 0, this.sum.getChecksumSize());
/* 49:79 */     this.out.flush();
/* 50:   */   }
/* 51:   */   
/* 52:   */   public void write(byte[] b, int off, int len)
/* 53:   */     throws IOException
/* 54:   */   {
/* 55:87 */     this.sum.update(b, off, len);
/* 56:88 */     this.out.write(b, off, len);
/* 57:   */   }
/* 58:   */   
/* 59:   */   public void write(int b)
/* 60:   */     throws IOException
/* 61:   */   {
/* 62:93 */     this.barray[0] = ((byte)(b & 0xFF));
/* 63:94 */     write(this.barray, 0, 1);
/* 64:   */   }
/* 65:   */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.IFileOutputStream
 * JD-Core Version:    0.7.0.1
 */