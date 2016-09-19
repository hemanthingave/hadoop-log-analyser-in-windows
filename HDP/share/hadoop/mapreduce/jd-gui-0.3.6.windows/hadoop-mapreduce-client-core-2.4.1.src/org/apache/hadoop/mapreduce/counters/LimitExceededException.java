/*  1:   */ package org.apache.hadoop.mapreduce.counters;
/*  2:   */ 
/*  3:   */ import org.apache.hadoop.classification.InterfaceAudience.Private;
/*  4:   */ import org.apache.hadoop.mapred.Counters.CountersExceededException;
/*  5:   */ 
/*  6:   */ @InterfaceAudience.Private
/*  7:   */ public class LimitExceededException
/*  8:   */   extends Counters.CountersExceededException
/*  9:   */ {
/* 10:   */   private static final long serialVersionUID = 1L;
/* 11:   */   
/* 12:   */   public LimitExceededException(String msg)
/* 13:   */   {
/* 14:30 */     super(msg);
/* 15:   */   }
/* 16:   */   
/* 17:   */   public LimitExceededException(LimitExceededException cause)
/* 18:   */   {
/* 19:35 */     super(cause);
/* 20:   */   }
/* 21:   */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.counters.LimitExceededException
 * JD-Core Version:    0.7.0.1
 */