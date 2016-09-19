/*  1:   */ package org.apache.hadoop.mapreduce.lib.join;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*  5:   */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*  6:   */ import org.apache.hadoop.io.Writable;
/*  7:   */ 
/*  8:   */ @InterfaceAudience.Public
/*  9:   */ @InterfaceStability.Stable
/* 10:   */ public abstract interface ResetableIterator<T extends Writable>
/* 11:   */ {
/* 12:   */   public abstract boolean hasNext();
/* 13:   */   
/* 14:   */   public abstract boolean next(T paramT)
/* 15:   */     throws IOException;
/* 16:   */   
/* 17:   */   public abstract boolean replay(T paramT)
/* 18:   */     throws IOException;
/* 19:   */   
/* 20:   */   public abstract void reset();
/* 21:   */   
/* 22:   */   public abstract void add(T paramT)
/* 23:   */     throws IOException;
/* 24:   */   
/* 25:   */   public abstract void close()
/* 26:   */     throws IOException;
/* 27:   */   
/* 28:   */   public abstract void clear();
/* 29:   */   
/* 30:   */   public static class EMPTY<U extends Writable>
/* 31:   */     implements ResetableIterator<U>
/* 32:   */   {
/* 33:   */     public boolean hasNext()
/* 34:   */     {
/* 35:37 */       return false;
/* 36:   */     }
/* 37:   */     
/* 38:   */     public void reset() {}
/* 39:   */     
/* 40:   */     public void close()
/* 41:   */       throws IOException
/* 42:   */     {}
/* 43:   */     
/* 44:   */     public void clear() {}
/* 45:   */     
/* 46:   */     public boolean next(U val)
/* 47:   */       throws IOException
/* 48:   */     {
/* 49:42 */       return false;
/* 50:   */     }
/* 51:   */     
/* 52:   */     public boolean replay(U val)
/* 53:   */       throws IOException
/* 54:   */     {
/* 55:45 */       return false;
/* 56:   */     }
/* 57:   */     
/* 58:   */     public void add(U item)
/* 59:   */       throws IOException
/* 60:   */     {
/* 61:48 */       throw new UnsupportedOperationException();
/* 62:   */     }
/* 63:   */   }
/* 64:   */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.lib.join.ResetableIterator
 * JD-Core Version:    0.7.0.1
 */