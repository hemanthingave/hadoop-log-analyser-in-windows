/*   1:    */ package org.apache.hadoop.mapreduce.lib.join;
/*   2:    */ 
/*   3:    */ import java.io.ByteArrayInputStream;
/*   4:    */ import java.io.ByteArrayOutputStream;
/*   5:    */ import java.io.DataInputStream;
/*   6:    */ import java.io.DataOutputStream;
/*   7:    */ import java.io.IOException;
/*   8:    */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*   9:    */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*  10:    */ import org.apache.hadoop.io.Writable;
/*  11:    */ 
/*  12:    */ @InterfaceAudience.Public
/*  13:    */ @InterfaceStability.Stable
/*  14:    */ public class StreamBackedIterator<X extends Writable>
/*  15:    */   implements ResetableIterator<X>
/*  16:    */ {
/*  17:    */   private static class ReplayableByteInputStream
/*  18:    */     extends ByteArrayInputStream
/*  19:    */   {
/*  20:    */     public ReplayableByteInputStream(byte[] arr)
/*  21:    */     {
/*  22: 41 */       super();
/*  23:    */     }
/*  24:    */     
/*  25:    */     public void resetStream()
/*  26:    */     {
/*  27: 44 */       this.mark = 0;
/*  28: 45 */       reset();
/*  29:    */     }
/*  30:    */   }
/*  31:    */   
/*  32: 49 */   private ByteArrayOutputStream outbuf = new ByteArrayOutputStream();
/*  33: 50 */   private DataOutputStream outfbuf = new DataOutputStream(this.outbuf);
/*  34:    */   private ReplayableByteInputStream inbuf;
/*  35:    */   private DataInputStream infbuf;
/*  36:    */   
/*  37:    */   public boolean hasNext()
/*  38:    */   {
/*  39: 57 */     return (this.infbuf != null) && (this.inbuf.available() > 0);
/*  40:    */   }
/*  41:    */   
/*  42:    */   public boolean next(X val)
/*  43:    */     throws IOException
/*  44:    */   {
/*  45: 61 */     if (hasNext())
/*  46:    */     {
/*  47: 62 */       this.inbuf.mark(0);
/*  48: 63 */       val.readFields(this.infbuf);
/*  49: 64 */       return true;
/*  50:    */     }
/*  51: 66 */     return false;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public boolean replay(X val)
/*  55:    */     throws IOException
/*  56:    */   {
/*  57: 70 */     this.inbuf.reset();
/*  58: 71 */     if (0 == this.inbuf.available()) {
/*  59: 72 */       return false;
/*  60:    */     }
/*  61: 73 */     val.readFields(this.infbuf);
/*  62: 74 */     return true;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public void reset()
/*  66:    */   {
/*  67: 78 */     if (null != this.outfbuf)
/*  68:    */     {
/*  69: 79 */       this.inbuf = new ReplayableByteInputStream(this.outbuf.toByteArray());
/*  70: 80 */       this.infbuf = new DataInputStream(this.inbuf);
/*  71: 81 */       this.outfbuf = null;
/*  72:    */     }
/*  73: 83 */     this.inbuf.resetStream();
/*  74:    */   }
/*  75:    */   
/*  76:    */   public void add(X item)
/*  77:    */     throws IOException
/*  78:    */   {
/*  79: 87 */     item.write(this.outfbuf);
/*  80:    */   }
/*  81:    */   
/*  82:    */   public void close()
/*  83:    */     throws IOException
/*  84:    */   {
/*  85: 91 */     if (null != this.infbuf) {
/*  86: 92 */       this.infbuf.close();
/*  87:    */     }
/*  88: 93 */     if (null != this.outfbuf) {
/*  89: 94 */       this.outfbuf.close();
/*  90:    */     }
/*  91:    */   }
/*  92:    */   
/*  93:    */   public void clear()
/*  94:    */   {
/*  95: 98 */     if (null != this.inbuf) {
/*  96: 99 */       this.inbuf.resetStream();
/*  97:    */     }
/*  98:100 */     this.outbuf.reset();
/*  99:101 */     this.outfbuf = new DataOutputStream(this.outbuf);
/* 100:    */   }
/* 101:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.lib.join.StreamBackedIterator
 * JD-Core Version:    0.7.0.1
 */