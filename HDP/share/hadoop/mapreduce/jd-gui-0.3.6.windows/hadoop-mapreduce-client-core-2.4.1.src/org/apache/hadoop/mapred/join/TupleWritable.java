/*  1:   */ package org.apache.hadoop.mapred.join;
/*  2:   */ 
/*  3:   */ import java.util.BitSet;
/*  4:   */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*  5:   */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*  6:   */ import org.apache.hadoop.io.Writable;
/*  7:   */ 
/*  8:   */ @InterfaceAudience.Public
/*  9:   */ @InterfaceStability.Stable
/* 10:   */ public class TupleWritable
/* 11:   */   extends org.apache.hadoop.mapreduce.lib.join.TupleWritable
/* 12:   */ {
/* 13:   */   public TupleWritable() {}
/* 14:   */   
/* 15:   */   public TupleWritable(Writable[] vals)
/* 16:   */   {
/* 17:54 */     super(vals);
/* 18:   */   }
/* 19:   */   
/* 20:   */   void setWritten(int i)
/* 21:   */   {
/* 22:61 */     this.written.set(i);
/* 23:   */   }
/* 24:   */   
/* 25:   */   void clearWritten(int i)
/* 26:   */   {
/* 27:69 */     this.written.clear(i);
/* 28:   */   }
/* 29:   */   
/* 30:   */   void clearWritten()
/* 31:   */   {
/* 32:77 */     this.written.clear();
/* 33:   */   }
/* 34:   */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.join.TupleWritable
 * JD-Core Version:    0.7.0.1
 */