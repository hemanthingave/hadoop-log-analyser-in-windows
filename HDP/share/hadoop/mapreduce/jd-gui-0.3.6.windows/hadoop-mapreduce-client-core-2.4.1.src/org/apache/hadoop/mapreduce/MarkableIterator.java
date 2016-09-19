/*  1:   */ package org.apache.hadoop.mapreduce;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import java.util.Iterator;
/*  5:   */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*  6:   */ import org.apache.hadoop.classification.InterfaceStability.Evolving;
/*  7:   */ 
/*  8:   */ @InterfaceAudience.Public
/*  9:   */ @InterfaceStability.Evolving
/* 10:   */ public class MarkableIterator<VALUE>
/* 11:   */   implements MarkableIteratorInterface<VALUE>
/* 12:   */ {
/* 13:   */   MarkableIteratorInterface<VALUE> baseIterator;
/* 14:   */   
/* 15:   */   public MarkableIterator(Iterator<VALUE> itr)
/* 16:   */   {
/* 17:44 */     if (!(itr instanceof MarkableIteratorInterface)) {
/* 18:45 */       throw new IllegalArgumentException("Input Iterator not markable");
/* 19:   */     }
/* 20:47 */     this.baseIterator = ((MarkableIteratorInterface)itr);
/* 21:   */   }
/* 22:   */   
/* 23:   */   public void mark()
/* 24:   */     throws IOException
/* 25:   */   {
/* 26:52 */     this.baseIterator.mark();
/* 27:   */   }
/* 28:   */   
/* 29:   */   public void reset()
/* 30:   */     throws IOException
/* 31:   */   {
/* 32:57 */     this.baseIterator.reset();
/* 33:   */   }
/* 34:   */   
/* 35:   */   public void clearMark()
/* 36:   */     throws IOException
/* 37:   */   {
/* 38:62 */     this.baseIterator.clearMark();
/* 39:   */   }
/* 40:   */   
/* 41:   */   public boolean hasNext()
/* 42:   */   {
/* 43:67 */     return this.baseIterator.hasNext();
/* 44:   */   }
/* 45:   */   
/* 46:   */   public VALUE next()
/* 47:   */   {
/* 48:72 */     return this.baseIterator.next();
/* 49:   */   }
/* 50:   */   
/* 51:   */   public void remove()
/* 52:   */   {
/* 53:77 */     throw new UnsupportedOperationException("Remove Not Implemented");
/* 54:   */   }
/* 55:   */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.MarkableIterator
 * JD-Core Version:    0.7.0.1
 */