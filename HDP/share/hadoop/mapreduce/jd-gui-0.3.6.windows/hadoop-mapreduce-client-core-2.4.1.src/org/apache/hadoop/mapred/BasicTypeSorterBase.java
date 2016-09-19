/*   1:    */ package org.apache.hadoop.mapred;
/*   2:    */ 
/*   3:    */ import org.apache.hadoop.io.OutputBuffer;
/*   4:    */ import org.apache.hadoop.io.RawComparator;
/*   5:    */ import org.apache.hadoop.io.SequenceFile.Sorter.RawKeyValueIterator;
/*   6:    */ import org.apache.hadoop.util.Progressable;
/*   7:    */ 
/*   8:    */ abstract class BasicTypeSorterBase
/*   9:    */   implements BufferSorter
/*  10:    */ {
/*  11:    */   protected OutputBuffer keyValBuffer;
/*  12:    */   protected int[] startOffsets;
/*  13:    */   protected int[] keyLengths;
/*  14:    */   protected int[] valueLengths;
/*  15:    */   protected int[] pointers;
/*  16:    */   protected RawComparator comparator;
/*  17:    */   protected int count;
/*  18:    */   private static final int BUFFERED_KEY_VAL_OVERHEAD = 16;
/*  19:    */   private static final int INITIAL_ARRAY_SIZE = 5;
/*  20: 60 */   private int maxKeyLength = 0;
/*  21: 61 */   private int maxValLength = 0;
/*  22:    */   protected Progressable reporter;
/*  23:    */   
/*  24:    */   public void configure(JobConf conf)
/*  25:    */   {
/*  26: 69 */     this.comparator = conf.getOutputKeyComparator();
/*  27:    */   }
/*  28:    */   
/*  29:    */   public void setProgressable(Progressable reporter)
/*  30:    */   {
/*  31: 73 */     this.reporter = reporter;
/*  32:    */   }
/*  33:    */   
/*  34:    */   public void addKeyValue(int recordOffset, int keyLength, int valLength)
/*  35:    */   {
/*  36: 79 */     if ((this.startOffsets == null) || (this.count == this.startOffsets.length)) {
/*  37: 80 */       grow();
/*  38:    */     }
/*  39: 81 */     this.startOffsets[this.count] = recordOffset;
/*  40: 82 */     this.keyLengths[this.count] = keyLength;
/*  41: 83 */     if (keyLength > this.maxKeyLength) {
/*  42: 84 */       this.maxKeyLength = keyLength;
/*  43:    */     }
/*  44: 86 */     if (valLength > this.maxValLength) {
/*  45: 87 */       this.maxValLength = valLength;
/*  46:    */     }
/*  47: 89 */     this.valueLengths[this.count] = valLength;
/*  48: 90 */     this.pointers[this.count] = this.count;
/*  49: 91 */     this.count += 1;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public void setInputBuffer(OutputBuffer buffer)
/*  53:    */   {
/*  54: 96 */     this.keyValBuffer = buffer;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public long getMemoryUtilized()
/*  58:    */   {
/*  59:103 */     if (this.startOffsets != null) {
/*  60:104 */       return this.startOffsets.length * 16 + this.maxKeyLength + this.maxValLength;
/*  61:    */     }
/*  62:108 */     return 0L;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public abstract SequenceFile.Sorter.RawKeyValueIterator sort();
/*  66:    */   
/*  67:    */   public void close()
/*  68:    */   {
/*  69:117 */     this.count = 0;
/*  70:118 */     this.startOffsets = null;
/*  71:119 */     this.keyLengths = null;
/*  72:120 */     this.valueLengths = null;
/*  73:121 */     this.pointers = null;
/*  74:122 */     this.maxKeyLength = 0;
/*  75:123 */     this.maxValLength = 0;
/*  76:    */     
/*  77:    */ 
/*  78:    */ 
/*  79:127 */     this.keyValBuffer = null;
/*  80:    */   }
/*  81:    */   
/*  82:    */   private void grow()
/*  83:    */   {
/*  84:131 */     int currLength = 0;
/*  85:132 */     if (this.startOffsets != null) {
/*  86:133 */       currLength = this.startOffsets.length;
/*  87:    */     }
/*  88:135 */     int newLength = (int)(currLength * 1.1D) + 1;
/*  89:136 */     this.startOffsets = grow(this.startOffsets, newLength);
/*  90:137 */     this.keyLengths = grow(this.keyLengths, newLength);
/*  91:138 */     this.valueLengths = grow(this.valueLengths, newLength);
/*  92:139 */     this.pointers = grow(this.pointers, newLength);
/*  93:    */   }
/*  94:    */   
/*  95:    */   private int[] grow(int[] old, int newLength)
/*  96:    */   {
/*  97:143 */     int[] result = new int[newLength];
/*  98:144 */     if (old != null) {
/*  99:145 */       System.arraycopy(old, 0, result, 0, old.length);
/* 100:    */     }
/* 101:147 */     return result;
/* 102:    */   }
/* 103:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.BasicTypeSorterBase
 * JD-Core Version:    0.7.0.1
 */