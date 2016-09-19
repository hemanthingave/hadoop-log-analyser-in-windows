/*  1:   */ package org.apache.hadoop.mapred;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*  5:   */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*  6:   */ 
/*  7:   */ @InterfaceAudience.Public
/*  8:   */ @InterfaceStability.Stable
/*  9:   */ public class InvalidJobConfException
/* 10:   */   extends IOException
/* 11:   */ {
/* 12:   */   private static final long serialVersionUID = 1L;
/* 13:   */   
/* 14:   */   public InvalidJobConfException() {}
/* 15:   */   
/* 16:   */   public InvalidJobConfException(String msg)
/* 17:   */   {
/* 18:42 */     super(msg);
/* 19:   */   }
/* 20:   */   
/* 21:   */   public InvalidJobConfException(String msg, Throwable t)
/* 22:   */   {
/* 23:46 */     super(msg, t);
/* 24:   */   }
/* 25:   */   
/* 26:   */   public InvalidJobConfException(Throwable t)
/* 27:   */   {
/* 28:50 */     super(t);
/* 29:   */   }
/* 30:   */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.InvalidJobConfException
 * JD-Core Version:    0.7.0.1
 */