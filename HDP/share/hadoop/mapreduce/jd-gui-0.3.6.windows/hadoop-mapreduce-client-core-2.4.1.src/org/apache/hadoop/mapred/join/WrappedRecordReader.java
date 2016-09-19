/*   1:    */ package org.apache.hadoop.mapred.join;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*   5:    */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*   6:    */ import org.apache.hadoop.io.Writable;
/*   7:    */ import org.apache.hadoop.io.WritableComparable;
/*   8:    */ import org.apache.hadoop.io.WritableComparator;
/*   9:    */ import org.apache.hadoop.io.WritableUtils;
/*  10:    */ import org.apache.hadoop.mapred.RecordReader;
/*  11:    */ 
/*  12:    */ @InterfaceAudience.Public
/*  13:    */ @InterfaceStability.Stable
/*  14:    */ public class WrappedRecordReader<K extends WritableComparable, U extends Writable>
/*  15:    */   implements ComposableRecordReader<K, U>
/*  16:    */ {
/*  17: 43 */   private boolean empty = false;
/*  18:    */   private RecordReader<K, U> rr;
/*  19:    */   private int id;
/*  20:    */   private K khead;
/*  21:    */   private U vhead;
/*  22:    */   private WritableComparator cmp;
/*  23:    */   private ResetableIterator<U> vjoin;
/*  24:    */   
/*  25:    */   WrappedRecordReader(int id, RecordReader<K, U> rr, Class<? extends WritableComparator> cmpcl)
/*  26:    */     throws IOException
/*  27:    */   {
/*  28: 58 */     this.id = id;
/*  29: 59 */     this.rr = rr;
/*  30: 60 */     this.khead = ((WritableComparable)rr.createKey());
/*  31: 61 */     this.vhead = ((Writable)rr.createValue());
/*  32:    */     try
/*  33:    */     {
/*  34: 63 */       this.cmp = (null == cmpcl ? WritableComparator.get(this.khead.getClass()) : (WritableComparator)cmpcl.newInstance());
/*  35:    */     }
/*  36:    */     catch (InstantiationException e)
/*  37:    */     {
/*  38: 67 */       throw ((IOException)new IOException().initCause(e));
/*  39:    */     }
/*  40:    */     catch (IllegalAccessException e)
/*  41:    */     {
/*  42: 69 */       throw ((IOException)new IOException().initCause(e));
/*  43:    */     }
/*  44: 71 */     this.vjoin = new StreamBackedIterator();
/*  45: 72 */     next();
/*  46:    */   }
/*  47:    */   
/*  48:    */   public int id()
/*  49:    */   {
/*  50: 77 */     return this.id;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public K key()
/*  54:    */   {
/*  55: 84 */     return this.khead;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public void key(K qkey)
/*  59:    */     throws IOException
/*  60:    */   {
/*  61: 91 */     WritableUtils.cloneInto(qkey, this.khead);
/*  62:    */   }
/*  63:    */   
/*  64:    */   public boolean hasNext()
/*  65:    */   {
/*  66: 99 */     return !this.empty;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public void skip(K key)
/*  70:    */     throws IOException
/*  71:    */   {
/*  72:106 */     while ((hasNext()) && 
/*  73:107 */       (this.cmp.compare(this.khead, key) <= 0) && (next())) {}
/*  74:    */   }
/*  75:    */   
/*  76:    */   protected boolean next()
/*  77:    */     throws IOException
/*  78:    */   {
/*  79:116 */     this.empty = (!this.rr.next(this.khead, this.vhead));
/*  80:117 */     return hasNext();
/*  81:    */   }
/*  82:    */   
/*  83:    */   public void accept(CompositeRecordReader.JoinCollector i, K key)
/*  84:    */     throws IOException
/*  85:    */   {
/*  86:130 */     this.vjoin.clear();
/*  87:131 */     if (0 == this.cmp.compare(key, this.khead)) {
/*  88:    */       do
/*  89:    */       {
/*  90:133 */         this.vjoin.add(this.vhead);
/*  91:134 */       } while ((next()) && (0 == this.cmp.compare(key, this.khead)));
/*  92:    */     }
/*  93:136 */     i.add(this.id, this.vjoin);
/*  94:    */   }
/*  95:    */   
/*  96:    */   public boolean next(K key, U value)
/*  97:    */     throws IOException
/*  98:    */   {
/*  99:144 */     if (hasNext())
/* 100:    */     {
/* 101:145 */       WritableUtils.cloneInto(key, this.khead);
/* 102:146 */       WritableUtils.cloneInto(value, this.vhead);
/* 103:147 */       next();
/* 104:148 */       return true;
/* 105:    */     }
/* 106:150 */     return false;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public K createKey()
/* 110:    */   {
/* 111:157 */     return (WritableComparable)this.rr.createKey();
/* 112:    */   }
/* 113:    */   
/* 114:    */   public U createValue()
/* 115:    */   {
/* 116:164 */     return (Writable)this.rr.createValue();
/* 117:    */   }
/* 118:    */   
/* 119:    */   public float getProgress()
/* 120:    */     throws IOException
/* 121:    */   {
/* 122:171 */     return this.rr.getProgress();
/* 123:    */   }
/* 124:    */   
/* 125:    */   public long getPos()
/* 126:    */     throws IOException
/* 127:    */   {
/* 128:178 */     return this.rr.getPos();
/* 129:    */   }
/* 130:    */   
/* 131:    */   public void close()
/* 132:    */     throws IOException
/* 133:    */   {
/* 134:185 */     this.rr.close();
/* 135:    */   }
/* 136:    */   
/* 137:    */   public int compareTo(ComposableRecordReader<K, ?> other)
/* 138:    */   {
/* 139:193 */     return this.cmp.compare(key(), other.key());
/* 140:    */   }
/* 141:    */   
/* 142:    */   public boolean equals(Object other)
/* 143:    */   {
/* 144:201 */     return ((other instanceof ComposableRecordReader)) && (0 == compareTo((ComposableRecordReader)other));
/* 145:    */   }
/* 146:    */   
/* 147:    */   public int hashCode()
/* 148:    */   {
/* 149:206 */     if (!$assertionsDisabled) {
/* 150:206 */       throw new AssertionError("hashCode not designed");
/* 151:    */     }
/* 152:207 */     return 42;
/* 153:    */   }
/* 154:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.join.WrappedRecordReader
 * JD-Core Version:    0.7.0.1
 */