/*  1:   */ package org.apache.hadoop.mapred;
/*  2:   */ 
/*  3:   */ import org.apache.hadoop.classification.InterfaceAudience.Private;
/*  4:   */ import org.apache.hadoop.classification.InterfaceStability.Unstable;
/*  5:   */ 
/*  6:   */ @InterfaceAudience.Private
/*  7:   */ @InterfaceStability.Unstable
/*  8:   */ public class JobInProgress
/*  9:   */ {
/* 10:   */   @Deprecated
/* 11:   */   public static enum Counter
/* 12:   */   {
/* 13:34 */     NUM_FAILED_MAPS,  NUM_FAILED_REDUCES,  TOTAL_LAUNCHED_MAPS,  TOTAL_LAUNCHED_REDUCES,  OTHER_LOCAL_MAPS,  DATA_LOCAL_MAPS,  RACK_LOCAL_MAPS,  SLOTS_MILLIS_MAPS,  SLOTS_MILLIS_REDUCES,  FALLOW_SLOTS_MILLIS_MAPS,  FALLOW_SLOTS_MILLIS_REDUCES;
/* 14:   */     
/* 15:   */     private Counter() {}
/* 16:   */   }
/* 17:   */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.JobInProgress
 * JD-Core Version:    0.7.0.1
 */