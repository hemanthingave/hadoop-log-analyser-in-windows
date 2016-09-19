/*  1:   */ package org.apache.hadoop.mapreduce.lib.db;
/*  2:   */ 
/*  3:   */ import java.util.Date;
/*  4:   */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*  5:   */ import org.apache.hadoop.classification.InterfaceStability.Evolving;
/*  6:   */ 
/*  7:   */ @InterfaceAudience.Public
/*  8:   */ @InterfaceStability.Evolving
/*  9:   */ public class OracleDateSplitter
/* 10:   */   extends DateSplitter
/* 11:   */ {
/* 12:   */   protected String dateToString(Date d)
/* 13:   */   {
/* 14:40 */     return "TO_TIMESTAMP('" + d.toString() + "', 'YYYY-MM-DD HH24:MI:SS.FF')";
/* 15:   */   }
/* 16:   */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.lib.db.OracleDateSplitter
 * JD-Core Version:    0.7.0.1
 */