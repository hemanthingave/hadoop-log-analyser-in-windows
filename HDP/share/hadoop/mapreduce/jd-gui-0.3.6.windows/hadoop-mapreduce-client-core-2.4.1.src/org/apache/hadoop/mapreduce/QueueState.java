/*  1:   */ package org.apache.hadoop.mapreduce;
/*  2:   */ 
/*  3:   */ import java.util.HashMap;
/*  4:   */ import java.util.Map;
/*  5:   */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*  6:   */ import org.apache.hadoop.classification.InterfaceStability.Evolving;
/*  7:   */ 
/*  8:   */ @InterfaceAudience.Public
/*  9:   */ @InterfaceStability.Evolving
/* 10:   */ public enum QueueState
/* 11:   */ {
/* 12:33 */   STOPPED("stopped"),  RUNNING("running"),  UNDEFINED("undefined");
/* 13:   */   
/* 14:   */   private final String stateName;
/* 15:   */   private static Map<String, QueueState> enumMap;
/* 16:   */   
/* 17:   */   static
/* 18:   */   {
/* 19:35 */     enumMap = new HashMap();
/* 20:39 */     for (QueueState state : values()) {
/* 21:40 */       enumMap.put(state.getStateName(), state);
/* 22:   */     }
/* 23:   */   }
/* 24:   */   
/* 25:   */   private QueueState(String stateName)
/* 26:   */   {
/* 27:45 */     this.stateName = stateName;
/* 28:   */   }
/* 29:   */   
/* 30:   */   public String getStateName()
/* 31:   */   {
/* 32:52 */     return this.stateName;
/* 33:   */   }
/* 34:   */   
/* 35:   */   public static QueueState getState(String state)
/* 36:   */   {
/* 37:56 */     QueueState qState = (QueueState)enumMap.get(state);
/* 38:57 */     if (qState == null) {
/* 39:58 */       return UNDEFINED;
/* 40:   */     }
/* 41:60 */     return qState;
/* 42:   */   }
/* 43:   */   
/* 44:   */   public String toString()
/* 45:   */   {
/* 46:65 */     return this.stateName;
/* 47:   */   }
/* 48:   */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.QueueState
 * JD-Core Version:    0.7.0.1
 */