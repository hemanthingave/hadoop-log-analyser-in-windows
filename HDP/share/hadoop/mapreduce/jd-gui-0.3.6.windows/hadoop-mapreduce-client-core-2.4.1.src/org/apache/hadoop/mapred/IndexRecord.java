/*  1:   */ package org.apache.hadoop.mapred;
/*  2:   */ 
/*  3:   */ import org.apache.hadoop.classification.InterfaceAudience.LimitedPrivate;
/*  4:   */ import org.apache.hadoop.classification.InterfaceStability.Unstable;
/*  5:   */ 
/*  6:   */ @InterfaceAudience.LimitedPrivate({"MapReduce"})
/*  7:   */ @InterfaceStability.Unstable
/*  8:   */ public class IndexRecord
/*  9:   */ {
/* 10:   */   public long startOffset;
/* 11:   */   public long rawLength;
/* 12:   */   public long partLength;
/* 13:   */   
/* 14:   */   public IndexRecord() {}
/* 15:   */   
/* 16:   */   public IndexRecord(long startOffset, long rawLength, long partLength)
/* 17:   */   {
/* 18:33 */     this.startOffset = startOffset;
/* 19:34 */     this.rawLength = rawLength;
/* 20:35 */     this.partLength = partLength;
/* 21:   */   }
/* 22:   */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.IndexRecord
 * JD-Core Version:    0.7.0.1
 */