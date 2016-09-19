/*  1:   */ package org.apache.hadoop.mapred;
/*  2:   */ 
/*  3:   */ import java.util.Comparator;
/*  4:   */ import org.apache.hadoop.io.IntWritable;
/*  5:   */ import org.apache.hadoop.io.OutputBuffer;
/*  6:   */ import org.apache.hadoop.io.RawComparator;
/*  7:   */ import org.apache.hadoop.io.SequenceFile.Sorter.RawKeyValueIterator;
/*  8:   */ import org.apache.hadoop.util.MergeSort;
/*  9:   */ import org.apache.hadoop.util.Progressable;
/* 10:   */ 
/* 11:   */ class MergeSorter
/* 12:   */   extends BasicTypeSorterBase
/* 13:   */   implements Comparator<IntWritable>
/* 14:   */ {
/* 15:37 */   private static int progressUpdateFrequency = 10000;
/* 16:38 */   private int progressCalls = 0;
/* 17:   */   
/* 18:   */   public SequenceFile.Sorter.RawKeyValueIterator sort()
/* 19:   */   {
/* 20:42 */     MergeSort m = new MergeSort(this);
/* 21:43 */     int count = this.count;
/* 22:44 */     if (count == 0) {
/* 23:44 */       return null;
/* 24:   */     }
/* 25:45 */     int[] pointers = this.pointers;
/* 26:46 */     int[] pointersCopy = new int[count];
/* 27:47 */     System.arraycopy(pointers, 0, pointersCopy, 0, count);
/* 28:48 */     m.mergeSort(pointers, pointersCopy, 0, count);
/* 29:49 */     return new MRSortResultIterator(this.keyValBuffer, pointersCopy, this.startOffsets, this.keyLengths, this.valueLengths);
/* 30:   */   }
/* 31:   */   
/* 32:   */   public int compare(IntWritable i, IntWritable j)
/* 33:   */   {
/* 34:61 */     if (this.progressCalls < progressUpdateFrequency)
/* 35:   */     {
/* 36:62 */       this.progressCalls += 1;
/* 37:   */     }
/* 38:   */     else
/* 39:   */     {
/* 40:64 */       this.progressCalls = 0;
/* 41:65 */       this.reporter.progress();
/* 42:   */     }
/* 43:67 */     return this.comparator.compare(this.keyValBuffer.getData(), this.startOffsets[i.get()], this.keyLengths[i.get()], this.keyValBuffer.getData(), this.startOffsets[j.get()], this.keyLengths[j.get()]);
/* 44:   */   }
/* 45:   */   
/* 46:   */   public long getMemoryUtilized()
/* 47:   */   {
/* 48:77 */     return super.getMemoryUtilized() + this.count * 4;
/* 49:   */   }
/* 50:   */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.MergeSorter
 * JD-Core Version:    0.7.0.1
 */