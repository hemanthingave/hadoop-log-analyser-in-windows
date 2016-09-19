/*  1:   */ package org.apache.hadoop.mapred;
/*  2:   */ 
/*  3:   */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*  4:   */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*  5:   */ import org.apache.hadoop.fs.Path;
/*  6:   */ import org.apache.hadoop.fs.PathFilter;
/*  7:   */ 
/*  8:   */ @InterfaceAudience.Public
/*  9:   */ @InterfaceStability.Stable
/* 10:   */ public class OutputLogFilter
/* 11:   */   implements PathFilter
/* 12:   */ {
/* 13:36 */   private static final PathFilter LOG_FILTER = new Utils.OutputFileUtils.OutputLogFilter();
/* 14:   */   
/* 15:   */   public boolean accept(Path path)
/* 16:   */   {
/* 17:39 */     return LOG_FILTER.accept(path);
/* 18:   */   }
/* 19:   */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.OutputLogFilter
 * JD-Core Version:    0.7.0.1
 */