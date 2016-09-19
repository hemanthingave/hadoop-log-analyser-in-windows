/*  1:   */ package org.apache.hadoop.mapred;
/*  2:   */ 
/*  3:   */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*  4:   */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*  5:   */ 
/*  6:   */ @InterfaceAudience.Public
/*  7:   */ @InterfaceStability.Stable
/*  8:   */ public abstract class ID
/*  9:   */   extends org.apache.hadoop.mapreduce.ID
/* 10:   */ {
/* 11:   */   public ID(int id)
/* 12:   */   {
/* 13:39 */     super(id);
/* 14:   */   }
/* 15:   */   
/* 16:   */   protected ID() {}
/* 17:   */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.ID
 * JD-Core Version:    0.7.0.1
 */