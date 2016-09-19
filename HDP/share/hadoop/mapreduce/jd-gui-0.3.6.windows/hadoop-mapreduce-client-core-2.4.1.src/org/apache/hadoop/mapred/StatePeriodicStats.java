/*  1:   */ package org.apache.hadoop.mapred;
/*  2:   */ 
/*  3:   */ class StatePeriodicStats
/*  4:   */   extends PeriodicStatsAccumulator
/*  5:   */ {
/*  6:   */   StatePeriodicStats(int count)
/*  7:   */   {
/*  8:36 */     super(count);
/*  9:   */   }
/* 10:   */   
/* 11:   */   protected void extendInternal(double newProgress, int newValue)
/* 12:   */   {
/* 13:47 */     if (this.state == null) {
/* 14:48 */       return;
/* 15:   */     }
/* 16:52 */     double mean = (newValue + this.state.oldValue) / 2.0D;
/* 17:   */     
/* 18:   */ 
/* 19:55 */     this.state.currentAccumulation += mean * (newProgress - this.state.oldProgress) * this.count;
/* 20:   */   }
/* 21:   */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.StatePeriodicStats
 * JD-Core Version:    0.7.0.1
 */