/*  1:   */ package org.apache.hadoop.mapred;
/*  2:   */ 
/*  3:   */ import org.apache.hadoop.classification.InterfaceAudience.Private;
/*  4:   */ import org.apache.hadoop.classification.InterfaceStability.Unstable;
/*  5:   */ 
/*  6:   */ @InterfaceAudience.Private
/*  7:   */ @InterfaceStability.Unstable
/*  8:   */ public class ProgressSplitsBlock
/*  9:   */ {
/* 10:   */   final PeriodicStatsAccumulator progressWallclockTime;
/* 11:   */   final PeriodicStatsAccumulator progressCPUTime;
/* 12:   */   final PeriodicStatsAccumulator progressVirtualMemoryKbytes;
/* 13:   */   final PeriodicStatsAccumulator progressPhysicalMemoryKbytes;
/* 14:37 */   static final int[] NULL_ARRAY = new int[0];
/* 15:   */   static final int WALLCLOCK_TIME_INDEX = 0;
/* 16:   */   static final int CPU_TIME_INDEX = 1;
/* 17:   */   static final int VIRTUAL_MEMORY_KBYTES_INDEX = 2;
/* 18:   */   static final int PHYSICAL_MEMORY_KBYTES_INDEX = 3;
/* 19:   */   static final int DEFAULT_NUMBER_PROGRESS_SPLITS = 12;
/* 20:   */   
/* 21:   */   ProgressSplitsBlock(int numberSplits)
/* 22:   */   {
/* 23:47 */     this.progressWallclockTime = new CumulativePeriodicStats(numberSplits);
/* 24:   */     
/* 25:49 */     this.progressCPUTime = new CumulativePeriodicStats(numberSplits);
/* 26:   */     
/* 27:51 */     this.progressVirtualMemoryKbytes = new StatePeriodicStats(numberSplits);
/* 28:   */     
/* 29:53 */     this.progressPhysicalMemoryKbytes = new StatePeriodicStats(numberSplits);
/* 30:   */   }
/* 31:   */   
/* 32:   */   int[][] burst()
/* 33:   */   {
/* 34:59 */     int[][] result = new int[4][];
/* 35:   */     
/* 36:61 */     result[0] = this.progressWallclockTime.getValues();
/* 37:62 */     result[1] = this.progressCPUTime.getValues();
/* 38:63 */     result[2] = this.progressVirtualMemoryKbytes.getValues();
/* 39:64 */     result[3] = this.progressPhysicalMemoryKbytes.getValues();
/* 40:   */     
/* 41:66 */     return result;
/* 42:   */   }
/* 43:   */   
/* 44:   */   public static int[] arrayGet(int[][] burstedBlock, int index)
/* 45:   */   {
/* 46:70 */     return burstedBlock == null ? NULL_ARRAY : burstedBlock[index];
/* 47:   */   }
/* 48:   */   
/* 49:   */   public static int[] arrayGetWallclockTime(int[][] burstedBlock)
/* 50:   */   {
/* 51:74 */     return arrayGet(burstedBlock, 0);
/* 52:   */   }
/* 53:   */   
/* 54:   */   public static int[] arrayGetCPUTime(int[][] burstedBlock)
/* 55:   */   {
/* 56:78 */     return arrayGet(burstedBlock, 1);
/* 57:   */   }
/* 58:   */   
/* 59:   */   public static int[] arrayGetVMemKbytes(int[][] burstedBlock)
/* 60:   */   {
/* 61:82 */     return arrayGet(burstedBlock, 2);
/* 62:   */   }
/* 63:   */   
/* 64:   */   public static int[] arrayGetPhysMemKbytes(int[][] burstedBlock)
/* 65:   */   {
/* 66:86 */     return arrayGet(burstedBlock, 3);
/* 67:   */   }
/* 68:   */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.ProgressSplitsBlock
 * JD-Core Version:    0.7.0.1
 */