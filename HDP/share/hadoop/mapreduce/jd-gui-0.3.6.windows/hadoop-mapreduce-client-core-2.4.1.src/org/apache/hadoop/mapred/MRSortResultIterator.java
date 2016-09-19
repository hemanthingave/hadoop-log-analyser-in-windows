/*   1:    */ package org.apache.hadoop.mapred;
/*   2:    */ 
/*   3:    */ import java.io.DataOutputStream;
/*   4:    */ import java.io.IOException;
/*   5:    */ import org.apache.hadoop.io.DataOutputBuffer;
/*   6:    */ import org.apache.hadoop.io.OutputBuffer;
/*   7:    */ import org.apache.hadoop.io.SequenceFile.Sorter.RawKeyValueIterator;
/*   8:    */ import org.apache.hadoop.io.SequenceFile.ValueBytes;
/*   9:    */ import org.apache.hadoop.util.Progress;
/*  10:    */ 
/*  11:    */ class MRSortResultIterator
/*  12:    */   implements SequenceFile.Sorter.RawKeyValueIterator
/*  13:    */ {
/*  14:    */   private int count;
/*  15:    */   private int[] pointers;
/*  16:    */   private int[] startOffsets;
/*  17:    */   private int[] keyLengths;
/*  18:    */   private int[] valLengths;
/*  19:    */   private int currStartOffsetIndex;
/*  20:    */   private int currIndexInPointers;
/*  21:    */   private OutputBuffer keyValBuffer;
/*  22:164 */   private DataOutputBuffer key = new DataOutputBuffer();
/*  23:165 */   private InMemUncompressedBytes value = new InMemUncompressedBytes(null);
/*  24:    */   
/*  25:    */   public MRSortResultIterator(OutputBuffer keyValBuffer, int[] pointers, int[] startOffsets, int[] keyLengths, int[] valLengths)
/*  26:    */   {
/*  27:170 */     this.count = pointers.length;
/*  28:171 */     this.pointers = pointers;
/*  29:172 */     this.startOffsets = startOffsets;
/*  30:173 */     this.keyLengths = keyLengths;
/*  31:174 */     this.valLengths = valLengths;
/*  32:175 */     this.keyValBuffer = keyValBuffer;
/*  33:    */   }
/*  34:    */   
/*  35:    */   public Progress getProgress()
/*  36:    */   {
/*  37:179 */     return null;
/*  38:    */   }
/*  39:    */   
/*  40:    */   public DataOutputBuffer getKey()
/*  41:    */     throws IOException
/*  42:    */   {
/*  43:183 */     int currKeyOffset = this.startOffsets[this.currStartOffsetIndex];
/*  44:184 */     int currKeyLength = this.keyLengths[this.currStartOffsetIndex];
/*  45:    */     
/*  46:186 */     this.key.reset();
/*  47:187 */     this.key.write(this.keyValBuffer.getData(), currKeyOffset, currKeyLength);
/*  48:188 */     return this.key;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public SequenceFile.ValueBytes getValue()
/*  52:    */     throws IOException
/*  53:    */   {
/*  54:194 */     this.value.reset(this.keyValBuffer, this.startOffsets[this.currStartOffsetIndex] + this.keyLengths[this.currStartOffsetIndex], this.valLengths[this.currStartOffsetIndex]);
/*  55:    */     
/*  56:    */ 
/*  57:197 */     return this.value;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public boolean next()
/*  61:    */     throws IOException
/*  62:    */   {
/*  63:201 */     if (this.count == this.currIndexInPointers) {
/*  64:202 */       return false;
/*  65:    */     }
/*  66:203 */     this.currStartOffsetIndex = this.pointers[this.currIndexInPointers];
/*  67:204 */     this.currIndexInPointers += 1;
/*  68:205 */     return true;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public void close() {}
/*  72:    */   
/*  73:    */   private static class InMemUncompressedBytes
/*  74:    */     implements SequenceFile.ValueBytes
/*  75:    */   {
/*  76:    */     private byte[] data;
/*  77:    */     int start;
/*  78:    */     int dataSize;
/*  79:    */     
/*  80:    */     private void reset(OutputBuffer d, int start, int length)
/*  81:    */       throws IOException
/*  82:    */     {
/*  83:220 */       this.data = d.getData();
/*  84:221 */       this.start = start;
/*  85:222 */       this.dataSize = length;
/*  86:    */     }
/*  87:    */     
/*  88:    */     public int getSize()
/*  89:    */     {
/*  90:226 */       return this.dataSize;
/*  91:    */     }
/*  92:    */     
/*  93:    */     public void writeUncompressedBytes(DataOutputStream outStream)
/*  94:    */       throws IOException
/*  95:    */     {
/*  96:231 */       outStream.write(this.data, this.start, this.dataSize);
/*  97:    */     }
/*  98:    */     
/*  99:    */     public void writeCompressedBytes(DataOutputStream outStream)
/* 100:    */       throws IllegalArgumentException, IOException
/* 101:    */     {
/* 102:236 */       throw new IllegalArgumentException("UncompressedBytes cannot be compressed!");
/* 103:    */     }
/* 104:    */   }
/* 105:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.MRSortResultIterator
 * JD-Core Version:    0.7.0.1
 */