/*  1:   */ package org.apache.hadoop.mapred.join;
/*  2:   */ 
/*  3:   */ import java.util.ArrayList;
/*  4:   */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*  5:   */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*  6:   */ import org.apache.hadoop.io.Writable;
/*  7:   */ 
/*  8:   */ @InterfaceAudience.Public
/*  9:   */ @InterfaceStability.Stable
/* 10:   */ public class ArrayListBackedIterator<X extends Writable>
/* 11:   */   extends org.apache.hadoop.mapreduce.lib.join.ArrayListBackedIterator<X>
/* 12:   */   implements ResetableIterator<X>
/* 13:   */ {
/* 14:   */   public ArrayListBackedIterator() {}
/* 15:   */   
/* 16:   */   public ArrayListBackedIterator(ArrayList<X> data)
/* 17:   */   {
/* 18:43 */     super(data);
/* 19:   */   }
/* 20:   */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.join.ArrayListBackedIterator
 * JD-Core Version:    0.7.0.1
 */