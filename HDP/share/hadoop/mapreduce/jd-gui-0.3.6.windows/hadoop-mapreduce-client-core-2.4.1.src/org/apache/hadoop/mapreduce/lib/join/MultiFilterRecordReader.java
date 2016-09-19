/*   1:    */ package org.apache.hadoop.mapreduce.lib.join;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.util.PriorityQueue;
/*   5:    */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*   6:    */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*   7:    */ import org.apache.hadoop.conf.Configuration;
/*   8:    */ import org.apache.hadoop.io.Writable;
/*   9:    */ import org.apache.hadoop.io.WritableComparable;
/*  10:    */ import org.apache.hadoop.io.WritableComparator;
/*  11:    */ import org.apache.hadoop.mapreduce.InputSplit;
/*  12:    */ import org.apache.hadoop.mapreduce.TaskAttemptContext;
/*  13:    */ import org.apache.hadoop.util.ReflectionUtils;
/*  14:    */ 
/*  15:    */ @InterfaceAudience.Public
/*  16:    */ @InterfaceStability.Stable
/*  17:    */ public abstract class MultiFilterRecordReader<K extends WritableComparable<?>, V extends Writable>
/*  18:    */   extends CompositeRecordReader<K, V, V>
/*  19:    */ {
/*  20: 44 */   private TupleWritable ivalue = null;
/*  21:    */   
/*  22:    */   public MultiFilterRecordReader(int id, Configuration conf, int capacity, Class<? extends WritableComparator> cmpcl)
/*  23:    */     throws IOException
/*  24:    */   {
/*  25: 48 */     super(id, capacity, cmpcl);
/*  26: 49 */     setConf(conf);
/*  27:    */   }
/*  28:    */   
/*  29:    */   protected abstract V emit(TupleWritable paramTupleWritable)
/*  30:    */     throws IOException;
/*  31:    */   
/*  32:    */   protected boolean combine(Object[] srcs, TupleWritable dst)
/*  33:    */   {
/*  34: 66 */     return true;
/*  35:    */   }
/*  36:    */   
/*  37:    */   public boolean nextKeyValue()
/*  38:    */     throws IOException, InterruptedException
/*  39:    */   {
/*  40: 71 */     if (this.key == null) {
/*  41: 72 */       this.key = createKey();
/*  42:    */     }
/*  43: 74 */     if (this.value == null) {
/*  44: 75 */       this.value = createValue();
/*  45:    */     }
/*  46: 77 */     if (this.jc.flush(this.ivalue))
/*  47:    */     {
/*  48: 78 */       ReflectionUtils.copy(this.conf, this.jc.key(), this.key);
/*  49: 79 */       ReflectionUtils.copy(this.conf, emit(this.ivalue), this.value);
/*  50: 80 */       return true;
/*  51:    */     }
/*  52: 82 */     if (this.ivalue == null) {
/*  53: 83 */       this.ivalue = createTupleWritable();
/*  54:    */     }
/*  55: 85 */     this.jc.clear();
/*  56: 86 */     PriorityQueue<ComposableRecordReader<K, ?>> q = getRecordReaderQueue();
/*  57:    */     
/*  58: 88 */     K iterkey = createKey();
/*  59: 89 */     while ((q != null) && (!q.isEmpty()))
/*  60:    */     {
/*  61: 90 */       fillJoinCollector(iterkey);
/*  62: 91 */       this.jc.reset(iterkey);
/*  63: 92 */       if (this.jc.flush(this.ivalue))
/*  64:    */       {
/*  65: 93 */         ReflectionUtils.copy(this.conf, this.jc.key(), this.key);
/*  66: 94 */         ReflectionUtils.copy(this.conf, emit(this.ivalue), this.value);
/*  67: 95 */         return true;
/*  68:    */       }
/*  69: 97 */       this.jc.clear();
/*  70:    */     }
/*  71: 99 */     return false;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public void initialize(InputSplit split, TaskAttemptContext context)
/*  75:    */     throws IOException, InterruptedException
/*  76:    */   {
/*  77:105 */     super.initialize(split, context);
/*  78:    */   }
/*  79:    */   
/*  80:    */   protected ResetableIterator<V> getDelegate()
/*  81:    */   {
/*  82:113 */     return new MultiFilterDelegationIterator();
/*  83:    */   }
/*  84:    */   
/*  85:    */   protected class MultiFilterDelegationIterator
/*  86:    */     implements ResetableIterator<V>
/*  87:    */   {
/*  88:    */     protected MultiFilterDelegationIterator() {}
/*  89:    */     
/*  90:    */     public boolean hasNext()
/*  91:    */     {
/*  92:123 */       return MultiFilterRecordReader.this.jc.hasNext();
/*  93:    */     }
/*  94:    */     
/*  95:    */     public boolean next(V val)
/*  96:    */       throws IOException
/*  97:    */     {
/*  98:    */       boolean ret;
/*  99:128 */       if ((ret = MultiFilterRecordReader.this.jc.flush(MultiFilterRecordReader.this.ivalue))) {
/* 100:129 */         ReflectionUtils.copy(MultiFilterRecordReader.this.getConf(), MultiFilterRecordReader.this.emit(MultiFilterRecordReader.this.ivalue), val);
/* 101:    */       }
/* 102:131 */       return ret;
/* 103:    */     }
/* 104:    */     
/* 105:    */     public boolean replay(V val)
/* 106:    */       throws IOException
/* 107:    */     {
/* 108:135 */       ReflectionUtils.copy(MultiFilterRecordReader.this.getConf(), MultiFilterRecordReader.this.emit(MultiFilterRecordReader.this.ivalue), val);
/* 109:136 */       return true;
/* 110:    */     }
/* 111:    */     
/* 112:    */     public void reset()
/* 113:    */     {
/* 114:140 */       MultiFilterRecordReader.this.jc.reset(MultiFilterRecordReader.this.jc.key());
/* 115:    */     }
/* 116:    */     
/* 117:    */     public void add(V item)
/* 118:    */       throws IOException
/* 119:    */     {
/* 120:144 */       throw new UnsupportedOperationException();
/* 121:    */     }
/* 122:    */     
/* 123:    */     public void close()
/* 124:    */       throws IOException
/* 125:    */     {
/* 126:148 */       MultiFilterRecordReader.this.jc.close();
/* 127:    */     }
/* 128:    */     
/* 129:    */     public void clear()
/* 130:    */     {
/* 131:152 */       MultiFilterRecordReader.this.jc.clear();
/* 132:    */     }
/* 133:    */   }
/* 134:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.lib.join.MultiFilterRecordReader
 * JD-Core Version:    0.7.0.1
 */