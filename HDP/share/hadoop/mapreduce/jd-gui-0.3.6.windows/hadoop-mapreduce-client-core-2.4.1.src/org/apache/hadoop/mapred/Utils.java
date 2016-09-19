/*  1:   */ package org.apache.hadoop.mapred;
/*  2:   */ 
/*  3:   */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*  4:   */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*  5:   */ import org.apache.hadoop.fs.Path;
/*  6:   */ import org.apache.hadoop.fs.PathFilter;
/*  7:   */ 
/*  8:   */ @InterfaceAudience.Public
/*  9:   */ @InterfaceStability.Stable
/* 10:   */ public class Utils
/* 11:   */ {
/* 12:   */   public static class OutputFileUtils
/* 13:   */   {
/* 14:   */     public static class OutputFilesFilter
/* 15:   */       extends Utils.OutputFileUtils.OutputLogFilter
/* 16:   */     {
/* 17:   */       public boolean accept(Path path)
/* 18:   */       {
/* 19:43 */         return (super.accept(path)) && (!"_SUCCESS".equals(path.getName()));
/* 20:   */       }
/* 21:   */     }
/* 22:   */     
/* 23:   */     public static class OutputLogFilter
/* 24:   */       implements PathFilter
/* 25:   */     {
/* 26:   */       public boolean accept(Path path)
/* 27:   */       {
/* 28:58 */         return !"_logs".equals(path.getName());
/* 29:   */       }
/* 30:   */     }
/* 31:   */   }
/* 32:   */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.Utils
 * JD-Core Version:    0.7.0.1
 */