/*  1:   */ package org.apache.hadoop.mapreduce.util;
/*  2:   */ 
/*  3:   */ import org.apache.hadoop.classification.InterfaceAudience.Private;
/*  4:   */ import org.apache.hadoop.classification.InterfaceStability.Unstable;
/*  5:   */ 
/*  6:   */ @InterfaceAudience.Private
/*  7:   */ @InterfaceStability.Unstable
/*  8:   */ public class HostUtil
/*  9:   */ {
/* 10:   */   public static String getTaskLogUrl(String scheme, String taskTrackerHostName, String httpPort, String taskAttemptID)
/* 11:   */   {
/* 12:37 */     return scheme + taskTrackerHostName + ":" + httpPort + "/tasklog?attemptid=" + taskAttemptID;
/* 13:   */   }
/* 14:   */   
/* 15:   */   @Deprecated
/* 16:   */   public static String getTaskLogUrl(String taskTrackerHostName, String httpPort, String taskAttemptID)
/* 17:   */   {
/* 18:51 */     throw new RuntimeException("This method is not supposed to be called at runtime. Use HostUtil.getTaskLogUrl(String, String, String, String) instead.");
/* 19:   */   }
/* 20:   */   
/* 21:   */   public static String convertTrackerNameToHostName(String trackerName)
/* 22:   */   {
/* 23:59 */     int indexOfColon = trackerName.indexOf(":");
/* 24:60 */     String trackerHostName = indexOfColon == -1 ? trackerName : trackerName.substring(0, indexOfColon);
/* 25:   */     
/* 26:   */ 
/* 27:63 */     return trackerHostName.substring("tracker_".length());
/* 28:   */   }
/* 29:   */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.util.HostUtil
 * JD-Core Version:    0.7.0.1
 */