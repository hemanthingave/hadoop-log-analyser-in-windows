/*   1:    */ package org.apache.hadoop.mapreduce.lib.input;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.InputStream;
/*   5:    */ import org.apache.hadoop.classification.InterfaceAudience.Private;
/*   6:    */ import org.apache.hadoop.classification.InterfaceStability.Unstable;
/*   7:    */ import org.apache.hadoop.conf.Configuration;
/*   8:    */ import org.apache.hadoop.io.Text;
/*   9:    */ import org.apache.hadoop.io.compress.SplitCompressionInputStream;
/*  10:    */ 
/*  11:    */ @InterfaceAudience.Private
/*  12:    */ @InterfaceStability.Unstable
/*  13:    */ public class CompressedSplitLineReader
/*  14:    */   extends SplitLineReader
/*  15:    */ {
/*  16:    */   SplitCompressionInputStream scin;
/*  17:    */   private boolean usingCRLF;
/*  18:115 */   private boolean needAdditionalRecord = false;
/*  19:116 */   private boolean finished = false;
/*  20:    */   
/*  21:    */   public CompressedSplitLineReader(SplitCompressionInputStream in, Configuration conf, byte[] recordDelimiterBytes)
/*  22:    */     throws IOException
/*  23:    */   {
/*  24:122 */     super(in, conf, recordDelimiterBytes);
/*  25:123 */     this.scin = in;
/*  26:124 */     this.usingCRLF = (recordDelimiterBytes == null);
/*  27:    */   }
/*  28:    */   
/*  29:    */   protected int fillBuffer(InputStream in, byte[] buffer, boolean inDelimiter)
/*  30:    */     throws IOException
/*  31:    */   {
/*  32:130 */     int bytesRead = in.read(buffer);
/*  33:138 */     if ((inDelimiter) && (bytesRead > 0)) {
/*  34:139 */       if (this.usingCRLF) {
/*  35:140 */         this.needAdditionalRecord = (buffer[0] != 10);
/*  36:    */       } else {
/*  37:142 */         this.needAdditionalRecord = true;
/*  38:    */       }
/*  39:    */     }
/*  40:145 */     return bytesRead;
/*  41:    */   }
/*  42:    */   
/*  43:    */   public int readLine(Text str, int maxLineLength, int maxBytesToConsume)
/*  44:    */     throws IOException
/*  45:    */   {
/*  46:151 */     int bytesRead = 0;
/*  47:152 */     if (!this.finished)
/*  48:    */     {
/*  49:155 */       if (this.scin.getPos() > this.scin.getAdjustedEnd()) {
/*  50:156 */         this.finished = true;
/*  51:    */       }
/*  52:159 */       bytesRead = super.readLine(str, maxLineLength, maxBytesToConsume);
/*  53:    */     }
/*  54:161 */     return bytesRead;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public boolean needAdditionalRecordAfterSplit()
/*  58:    */   {
/*  59:166 */     return (!this.finished) && (this.needAdditionalRecord);
/*  60:    */   }
/*  61:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.lib.input.CompressedSplitLineReader
 * JD-Core Version:    0.7.0.1
 */