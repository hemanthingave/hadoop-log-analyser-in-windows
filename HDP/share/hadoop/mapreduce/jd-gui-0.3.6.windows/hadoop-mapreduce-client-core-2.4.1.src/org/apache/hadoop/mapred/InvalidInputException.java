/*  1:   */ package org.apache.hadoop.mapred;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import java.util.Iterator;
/*  5:   */ import java.util.List;
/*  6:   */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*  7:   */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*  8:   */ 
/*  9:   */ @InterfaceAudience.Public
/* 10:   */ @InterfaceStability.Stable
/* 11:   */ public class InvalidInputException
/* 12:   */   extends IOException
/* 13:   */ {
/* 14:   */   private static final long serialVersionUID = 1L;
/* 15:   */   private List<IOException> problems;
/* 16:   */   
/* 17:   */   public InvalidInputException(List<IOException> probs)
/* 18:   */   {
/* 19:44 */     this.problems = probs;
/* 20:   */   }
/* 21:   */   
/* 22:   */   public List<IOException> getProblems()
/* 23:   */   {
/* 24:52 */     return this.problems;
/* 25:   */   }
/* 26:   */   
/* 27:   */   public String getMessage()
/* 28:   */   {
/* 29:60 */     StringBuffer result = new StringBuffer();
/* 30:61 */     Iterator<IOException> itr = this.problems.iterator();
/* 31:62 */     while (itr.hasNext())
/* 32:   */     {
/* 33:63 */       result.append(((IOException)itr.next()).getMessage());
/* 34:64 */       if (itr.hasNext()) {
/* 35:65 */         result.append("\n");
/* 36:   */       }
/* 37:   */     }
/* 38:68 */     return result.toString();
/* 39:   */   }
/* 40:   */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.InvalidInputException
 * JD-Core Version:    0.7.0.1
 */