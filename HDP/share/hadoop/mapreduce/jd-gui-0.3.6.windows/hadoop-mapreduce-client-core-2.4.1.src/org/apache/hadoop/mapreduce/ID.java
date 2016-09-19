/*  1:   */ package org.apache.hadoop.mapreduce;
/*  2:   */ 
/*  3:   */ import java.io.DataInput;
/*  4:   */ import java.io.DataOutput;
/*  5:   */ import java.io.IOException;
/*  6:   */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*  7:   */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*  8:   */ import org.apache.hadoop.io.WritableComparable;
/*  9:   */ 
/* 10:   */ @InterfaceAudience.Public
/* 11:   */ @InterfaceStability.Stable
/* 12:   */ public abstract class ID
/* 13:   */   implements WritableComparable<ID>
/* 14:   */ {
/* 15:   */   protected static final char SEPARATOR = '_';
/* 16:   */   protected int id;
/* 17:   */   
/* 18:   */   public ID(int id)
/* 19:   */   {
/* 20:46 */     this.id = id;
/* 21:   */   }
/* 22:   */   
/* 23:   */   protected ID() {}
/* 24:   */   
/* 25:   */   public int getId()
/* 26:   */   {
/* 27:54 */     return this.id;
/* 28:   */   }
/* 29:   */   
/* 30:   */   public String toString()
/* 31:   */   {
/* 32:59 */     return String.valueOf(this.id);
/* 33:   */   }
/* 34:   */   
/* 35:   */   public int hashCode()
/* 36:   */   {
/* 37:64 */     return this.id;
/* 38:   */   }
/* 39:   */   
/* 40:   */   public boolean equals(Object o)
/* 41:   */   {
/* 42:69 */     if (this == o) {
/* 43:70 */       return true;
/* 44:   */     }
/* 45:71 */     if (o == null) {
/* 46:72 */       return false;
/* 47:   */     }
/* 48:73 */     if (o.getClass() == getClass())
/* 49:   */     {
/* 50:74 */       ID that = (ID)o;
/* 51:75 */       return this.id == that.id;
/* 52:   */     }
/* 53:78 */     return false;
/* 54:   */   }
/* 55:   */   
/* 56:   */   public int compareTo(ID that)
/* 57:   */   {
/* 58:83 */     return this.id - that.id;
/* 59:   */   }
/* 60:   */   
/* 61:   */   public void readFields(DataInput in)
/* 62:   */     throws IOException
/* 63:   */   {
/* 64:87 */     this.id = in.readInt();
/* 65:   */   }
/* 66:   */   
/* 67:   */   public void write(DataOutput out)
/* 68:   */     throws IOException
/* 69:   */   {
/* 70:91 */     out.writeInt(this.id);
/* 71:   */   }
/* 72:   */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.ID
 * JD-Core Version:    0.7.0.1
 */