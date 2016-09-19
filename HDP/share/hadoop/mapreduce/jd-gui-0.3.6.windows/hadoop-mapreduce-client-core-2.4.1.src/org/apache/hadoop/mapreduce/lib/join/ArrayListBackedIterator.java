/*  1:   */ package org.apache.hadoop.mapreduce.lib.join;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import java.util.ArrayList;
/*  5:   */ import java.util.Iterator;
/*  6:   */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*  7:   */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*  8:   */ import org.apache.hadoop.conf.Configuration;
/*  9:   */ import org.apache.hadoop.io.Writable;
/* 10:   */ import org.apache.hadoop.io.WritableUtils;
/* 11:   */ import org.apache.hadoop.util.ReflectionUtils;
/* 12:   */ 
/* 13:   */ @InterfaceAudience.Public
/* 14:   */ @InterfaceStability.Stable
/* 15:   */ public class ArrayListBackedIterator<X extends Writable>
/* 16:   */   implements ResetableIterator<X>
/* 17:   */ {
/* 18:   */   private Iterator<X> iter;
/* 19:   */   private ArrayList<X> data;
/* 20:44 */   private X hold = null;
/* 21:45 */   private Configuration conf = new Configuration();
/* 22:   */   
/* 23:   */   public ArrayListBackedIterator()
/* 24:   */   {
/* 25:48 */     this(new ArrayList());
/* 26:   */   }
/* 27:   */   
/* 28:   */   public ArrayListBackedIterator(ArrayList<X> data)
/* 29:   */   {
/* 30:52 */     this.data = data;
/* 31:53 */     this.iter = this.data.iterator();
/* 32:   */   }
/* 33:   */   
/* 34:   */   public boolean hasNext()
/* 35:   */   {
/* 36:57 */     return this.iter.hasNext();
/* 37:   */   }
/* 38:   */   
/* 39:   */   public boolean next(X val)
/* 40:   */     throws IOException
/* 41:   */   {
/* 42:61 */     if (this.iter.hasNext())
/* 43:   */     {
/* 44:62 */       ReflectionUtils.copy(this.conf, this.iter.next(), val);
/* 45:63 */       if (null == this.hold) {
/* 46:64 */         this.hold = WritableUtils.clone(val, null);
/* 47:   */       } else {
/* 48:66 */         ReflectionUtils.copy(this.conf, val, this.hold);
/* 49:   */       }
/* 50:68 */       return true;
/* 51:   */     }
/* 52:70 */     return false;
/* 53:   */   }
/* 54:   */   
/* 55:   */   public boolean replay(X val)
/* 56:   */     throws IOException
/* 57:   */   {
/* 58:74 */     ReflectionUtils.copy(this.conf, this.hold, val);
/* 59:75 */     return true;
/* 60:   */   }
/* 61:   */   
/* 62:   */   public void reset()
/* 63:   */   {
/* 64:79 */     this.iter = this.data.iterator();
/* 65:   */   }
/* 66:   */   
/* 67:   */   public void add(X item)
/* 68:   */     throws IOException
/* 69:   */   {
/* 70:83 */     this.data.add(WritableUtils.clone(item, null));
/* 71:   */   }
/* 72:   */   
/* 73:   */   public void close()
/* 74:   */     throws IOException
/* 75:   */   {
/* 76:87 */     this.iter = null;
/* 77:88 */     this.data = null;
/* 78:   */   }
/* 79:   */   
/* 80:   */   public void clear()
/* 81:   */   {
/* 82:92 */     this.data.clear();
/* 83:93 */     reset();
/* 84:   */   }
/* 85:   */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.lib.join.ArrayListBackedIterator
 * JD-Core Version:    0.7.0.1
 */