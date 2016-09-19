/*   1:    */ package org.apache.hadoop.mapred.join;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.util.PriorityQueue;
/*   5:    */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*   6:    */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*   7:    */ import org.apache.hadoop.io.Writable;
/*   8:    */ import org.apache.hadoop.io.WritableComparable;
/*   9:    */ import org.apache.hadoop.io.WritableComparator;
/*  10:    */ import org.apache.hadoop.io.WritableUtils;
/*  11:    */ import org.apache.hadoop.mapred.JobConf;
/*  12:    */ import org.apache.hadoop.mapred.RecordReader;
/*  13:    */ import org.apache.hadoop.util.ReflectionUtils;
/*  14:    */ 
/*  15:    */ @InterfaceAudience.Public
/*  16:    */ @InterfaceStability.Stable
/*  17:    */ public abstract class MultiFilterRecordReader<K extends WritableComparable, V extends Writable>
/*  18:    */   extends CompositeRecordReader<K, V, V>
/*  19:    */   implements ComposableRecordReader<K, V>
/*  20:    */ {
/*  21:    */   private Class<? extends Writable> valueclass;
/*  22:    */   private TupleWritable ivalue;
/*  23:    */   
/*  24:    */   public MultiFilterRecordReader(int id, JobConf conf, int capacity, Class<? extends WritableComparator> cmpcl)
/*  25:    */     throws IOException
/*  26:    */   {
/*  27: 50 */     super(id, capacity, cmpcl);
/*  28: 51 */     setConf(conf);
/*  29:    */   }
/*  30:    */   
/*  31:    */   protected abstract V emit(TupleWritable paramTupleWritable)
/*  32:    */     throws IOException;
/*  33:    */   
/*  34:    */   protected boolean combine(Object[] srcs, TupleWritable dst)
/*  35:    */   {
/*  36: 68 */     return true;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public boolean next(K key, V value)
/*  40:    */     throws IOException
/*  41:    */   {
/*  42: 73 */     if (this.jc.flush(this.ivalue))
/*  43:    */     {
/*  44: 74 */       WritableUtils.cloneInto(key, this.jc.key());
/*  45: 75 */       WritableUtils.cloneInto(value, emit(this.ivalue));
/*  46: 76 */       return true;
/*  47:    */     }
/*  48: 78 */     this.jc.clear();
/*  49: 79 */     K iterkey = createKey();
/*  50: 80 */     PriorityQueue<ComposableRecordReader<K, ?>> q = getRecordReaderQueue();
/*  51: 81 */     while (!q.isEmpty())
/*  52:    */     {
/*  53: 82 */       fillJoinCollector(iterkey);
/*  54: 83 */       this.jc.reset(iterkey);
/*  55: 84 */       if (this.jc.flush(this.ivalue))
/*  56:    */       {
/*  57: 85 */         WritableUtils.cloneInto(key, this.jc.key());
/*  58: 86 */         WritableUtils.cloneInto(value, emit(this.ivalue));
/*  59: 87 */         return true;
/*  60:    */       }
/*  61: 89 */       this.jc.clear();
/*  62:    */     }
/*  63: 91 */     return false;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public V createValue()
/*  67:    */   {
/*  68: 97 */     if (null == this.valueclass)
/*  69:    */     {
/*  70: 98 */       Class<?> cls = ((Writable)this.kids[0].createValue()).getClass();
/*  71: 99 */       for (RecordReader<K, ? extends V> rr : this.kids) {
/*  72:100 */         if (!cls.equals(((Writable)rr.createValue()).getClass())) {
/*  73:101 */           throw new ClassCastException("Child value classes fail to agree");
/*  74:    */         }
/*  75:    */       }
/*  76:104 */       this.valueclass = cls.asSubclass(Writable.class);
/*  77:105 */       this.ivalue = createInternalValue();
/*  78:    */     }
/*  79:107 */     return (Writable)ReflectionUtils.newInstance(this.valueclass, null);
/*  80:    */   }
/*  81:    */   
/*  82:    */   protected ResetableIterator<V> getDelegate()
/*  83:    */   {
/*  84:115 */     return new MultiFilterDelegationIterator();
/*  85:    */   }
/*  86:    */   
/*  87:    */   protected class MultiFilterDelegationIterator
/*  88:    */     implements ResetableIterator<V>
/*  89:    */   {
/*  90:    */     protected MultiFilterDelegationIterator() {}
/*  91:    */     
/*  92:    */     public boolean hasNext()
/*  93:    */     {
/*  94:125 */       return MultiFilterRecordReader.this.jc.hasNext();
/*  95:    */     }
/*  96:    */     
/*  97:    */     public boolean next(V val)
/*  98:    */       throws IOException
/*  99:    */     {
/* 100:    */       boolean ret;
/* 101:130 */       if ((ret = MultiFilterRecordReader.this.jc.flush(MultiFilterRecordReader.this.ivalue))) {
/* 102:131 */         WritableUtils.cloneInto(val, MultiFilterRecordReader.this.emit(MultiFilterRecordReader.this.ivalue));
/* 103:    */       }
/* 104:133 */       return ret;
/* 105:    */     }
/* 106:    */     
/* 107:    */     public boolean replay(V val)
/* 108:    */       throws IOException
/* 109:    */     {
/* 110:137 */       WritableUtils.cloneInto(val, MultiFilterRecordReader.this.emit(MultiFilterRecordReader.this.ivalue));
/* 111:138 */       return true;
/* 112:    */     }
/* 113:    */     
/* 114:    */     public void reset()
/* 115:    */     {
/* 116:142 */       MultiFilterRecordReader.this.jc.reset(MultiFilterRecordReader.this.jc.key());
/* 117:    */     }
/* 118:    */     
/* 119:    */     public void add(V item)
/* 120:    */       throws IOException
/* 121:    */     {
/* 122:146 */       throw new UnsupportedOperationException();
/* 123:    */     }
/* 124:    */     
/* 125:    */     public void close()
/* 126:    */       throws IOException
/* 127:    */     {
/* 128:150 */       MultiFilterRecordReader.this.jc.close();
/* 129:    */     }
/* 130:    */     
/* 131:    */     public void clear()
/* 132:    */     {
/* 133:154 */       MultiFilterRecordReader.this.jc.clear();
/* 134:    */     }
/* 135:    */   }
/* 136:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.join.MultiFilterRecordReader
 * JD-Core Version:    0.7.0.1
 */