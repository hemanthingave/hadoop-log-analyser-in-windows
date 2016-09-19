/*  1:   */ package org.apache.hadoop.mapred;
/*  2:   */ 
/*  3:   */ class CumulativePeriodicStats
/*  4:   */   extends PeriodicStatsAccumulator
/*  5:   */ {
/*  6:38 */   int previousValue = 0;
/*  7:   */   
/*  8:   */   CumulativePeriodicStats(int count)
/*  9:   */   {
/* 10:41 */     super(count);
/* 11:   */   }
/* 12:   */   
/* 13:   */   protected void extendInternal(double newProgress, int newValue)
/* 14:   */   {
/* 15:52 */     if (this.state == null) {
/* 16:53 */       return;
/* 17:   */     }
/* 18:56 */     this.state.currentAccumulation += newValue - this.previousValue;
/* 19:57 */     this.previousValue = newValue;
/* 20:   */   }
/* 21:   */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.CumulativePeriodicStats
 * JD-Core Version:    0.7.0.1
 */