/*  1:   */ package org.apache.hadoop.mapred;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*  5:   */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*  6:   */ 
/*  7:   */ @InterfaceAudience.Public
/*  8:   */ @InterfaceStability.Stable
/*  9:   */ public class FileAlreadyExistsException
/* 10:   */   extends IOException
/* 11:   */ {
/* 12:   */   private static final long serialVersionUID = 1L;
/* 13:   */   
/* 14:   */   public FileAlreadyExistsException() {}
/* 15:   */   
/* 16:   */   public FileAlreadyExistsException(String msg)
/* 17:   */   {
/* 18:42 */     super(msg);
/* 19:   */   }
/* 20:   */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.FileAlreadyExistsException
 * JD-Core Version:    0.7.0.1
 */