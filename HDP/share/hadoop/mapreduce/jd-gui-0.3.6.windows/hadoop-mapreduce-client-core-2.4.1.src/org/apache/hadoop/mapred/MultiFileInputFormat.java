/*   1:    */ package org.apache.hadoop.mapred;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import java.util.List;
/*   6:    */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*   7:    */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*   8:    */ import org.apache.hadoop.fs.ContentSummary;
/*   9:    */ import org.apache.hadoop.fs.FileSystem;
/*  10:    */ import org.apache.hadoop.fs.FileUtil;
/*  11:    */ import org.apache.hadoop.fs.Path;
/*  12:    */ 
/*  13:    */ @InterfaceAudience.Public
/*  14:    */ @InterfaceStability.Stable
/*  15:    */ public abstract class MultiFileInputFormat<K, V>
/*  16:    */   extends FileInputFormat<K, V>
/*  17:    */ {
/*  18:    */   public InputSplit[] getSplits(JobConf job, int numSplits)
/*  19:    */     throws IOException
/*  20:    */   {
/*  21: 49 */     Path[] paths = FileUtil.stat2Paths(listStatus(job));
/*  22: 50 */     List<MultiFileSplit> splits = new ArrayList(Math.min(numSplits, paths.length));
/*  23: 51 */     if (paths.length != 0)
/*  24:    */     {
/*  25: 53 */       long[] lengths = new long[paths.length];
/*  26: 54 */       long totLength = 0L;
/*  27: 55 */       for (int i = 0; i < paths.length; i++)
/*  28:    */       {
/*  29: 56 */         FileSystem fs = paths[i].getFileSystem(job);
/*  30: 57 */         lengths[i] = fs.getContentSummary(paths[i]).getLength();
/*  31: 58 */         totLength += lengths[i];
/*  32:    */       }
/*  33: 60 */       double avgLengthPerSplit = totLength / numSplits;
/*  34: 61 */       long cumulativeLength = 0L;
/*  35:    */       
/*  36: 63 */       int startIndex = 0;
/*  37: 65 */       for (int i = 0; i < numSplits; i++)
/*  38:    */       {
/*  39: 66 */         int splitSize = findSize(i, avgLengthPerSplit, cumulativeLength, startIndex, lengths);
/*  40: 68 */         if (splitSize != 0)
/*  41:    */         {
/*  42: 70 */           Path[] splitPaths = new Path[splitSize];
/*  43: 71 */           long[] splitLengths = new long[splitSize];
/*  44: 72 */           System.arraycopy(paths, startIndex, splitPaths, 0, splitSize);
/*  45: 73 */           System.arraycopy(lengths, startIndex, splitLengths, 0, splitSize);
/*  46: 74 */           splits.add(new MultiFileSplit(job, splitPaths, splitLengths));
/*  47: 75 */           startIndex += splitSize;
/*  48: 76 */           for (long l : splitLengths) {
/*  49: 77 */             cumulativeLength += l;
/*  50:    */           }
/*  51:    */         }
/*  52:    */       }
/*  53:    */     }
/*  54: 82 */     return (InputSplit[])splits.toArray(new MultiFileSplit[splits.size()]);
/*  55:    */   }
/*  56:    */   
/*  57:    */   private int findSize(int splitIndex, double avgLengthPerSplit, long cumulativeLength, int startIndex, long[] lengths)
/*  58:    */   {
/*  59: 88 */     if (splitIndex == lengths.length - 1) {
/*  60: 89 */       return lengths.length - startIndex;
/*  61:    */     }
/*  62: 91 */     long goalLength = ((splitIndex + 1) * avgLengthPerSplit);
/*  63: 92 */     long partialLength = 0L;
/*  64: 94 */     for (int i = startIndex; i < lengths.length; i++)
/*  65:    */     {
/*  66: 95 */       partialLength += lengths[i];
/*  67: 96 */       if (partialLength + cumulativeLength >= goalLength) {
/*  68: 97 */         return i - startIndex + 1;
/*  69:    */       }
/*  70:    */     }
/*  71:100 */     return lengths.length - startIndex;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public abstract RecordReader<K, V> getRecordReader(InputSplit paramInputSplit, JobConf paramJobConf, Reporter paramReporter)
/*  75:    */     throws IOException;
/*  76:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.MultiFileInputFormat
 * JD-Core Version:    0.7.0.1
 */