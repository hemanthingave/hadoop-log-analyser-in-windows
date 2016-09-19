/*  1:   */ package org.apache.hadoop.mapreduce.counters;
/*  2:   */ 
/*  3:   */ import com.google.common.base.Objects;
/*  4:   */ import org.apache.hadoop.classification.InterfaceAudience.Private;
/*  5:   */ import org.apache.hadoop.mapreduce.Counter;
/*  6:   */ 
/*  7:   */ @InterfaceAudience.Private
/*  8:   */ public abstract class AbstractCounter
/*  9:   */   implements Counter
/* 10:   */ {
/* 11:   */   @Deprecated
/* 12:   */   public void setDisplayName(String name) {}
/* 13:   */   
/* 14:   */   public synchronized boolean equals(Object genericRight)
/* 15:   */   {
/* 16:37 */     if ((genericRight instanceof Counter)) {
/* 17:38 */       synchronized (genericRight)
/* 18:   */       {
/* 19:39 */         Counter right = (Counter)genericRight;
/* 20:40 */         return (getName().equals(right.getName())) && (getDisplayName().equals(right.getDisplayName())) && (getValue() == right.getValue());
/* 21:   */       }
/* 22:   */     }
/* 23:45 */     return false;
/* 24:   */   }
/* 25:   */   
/* 26:   */   public synchronized int hashCode()
/* 27:   */   {
/* 28:50 */     return Objects.hashCode(new Object[] { getName(), getDisplayName(), Long.valueOf(getValue()) });
/* 29:   */   }
/* 30:   */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.counters.AbstractCounter
 * JD-Core Version:    0.7.0.1
 */