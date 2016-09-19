/*  1:   */ package org.apache.hadoop.mapred;
/*  2:   */ 
/*  3:   */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*  4:   */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*  5:   */ import org.apache.hadoop.util.Progressable;
/*  6:   */ 
/*  7:   */ @InterfaceAudience.Public
/*  8:   */ @InterfaceStability.Stable
/*  9:   */ public abstract interface Reporter
/* 10:   */   extends Progressable
/* 11:   */ {
/* 12:49 */   public static final Reporter NULL = new Reporter()
/* 13:   */   {
/* 14:   */     public void setStatus(String s) {}
/* 15:   */     
/* 16:   */     public void progress() {}
/* 17:   */     
/* 18:   */     public Counters.Counter getCounter(Enum<?> name)
/* 19:   */     {
/* 20:55 */       return null;
/* 21:   */     }
/* 22:   */     
/* 23:   */     public Counters.Counter getCounter(String group, String name)
/* 24:   */     {
/* 25:58 */       return null;
/* 26:   */     }
/* 27:   */     
/* 28:   */     public void incrCounter(Enum<?> key, long amount) {}
/* 29:   */     
/* 30:   */     public void incrCounter(String group, String counter, long amount) {}
/* 31:   */     
/* 32:   */     public InputSplit getInputSplit()
/* 33:   */       throws UnsupportedOperationException
/* 34:   */     {
/* 35:65 */       throw new UnsupportedOperationException("NULL reporter has no input");
/* 36:   */     }
/* 37:   */     
/* 38:   */     public float getProgress()
/* 39:   */     {
/* 40:69 */       return 0.0F;
/* 41:   */     }
/* 42:   */   };
/* 43:   */   
/* 44:   */   public abstract void setStatus(String paramString);
/* 45:   */   
/* 46:   */   public abstract Counters.Counter getCounter(Enum<?> paramEnum);
/* 47:   */   
/* 48:   */   public abstract Counters.Counter getCounter(String paramString1, String paramString2);
/* 49:   */   
/* 50:   */   public abstract void incrCounter(Enum<?> paramEnum, long paramLong);
/* 51:   */   
/* 52:   */   public abstract void incrCounter(String paramString1, String paramString2, long paramLong);
/* 53:   */   
/* 54:   */   public abstract InputSplit getInputSplit()
/* 55:   */     throws UnsupportedOperationException;
/* 56:   */   
/* 57:   */   public abstract float getProgress();
/* 58:   */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.Reporter
 * JD-Core Version:    0.7.0.1
 */