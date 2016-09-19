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
/*  12:    */ 
/*  13:    */ @InterfaceAudience.Public
/*  14:    */ @InterfaceStability.Stable
/*  15:    */ public abstract class JoinRecordReader<K extends WritableComparable>
/*  16:    */   extends CompositeRecordReader<K, Writable, TupleWritable>
/*  17:    */   implements ComposableRecordReader<K, TupleWritable>
/*  18:    */ {
/*  19:    */   public JoinRecordReader(int id, JobConf conf, int capacity, Class<? extends WritableComparator> cmpcl)
/*  20:    */     throws IOException
/*  21:    */   {
/*  22: 43 */     super(id, capacity, cmpcl);
/*  23: 44 */     setConf(conf);
/*  24:    */   }
/*  25:    */   
/*  26:    */   public boolean next(K key, TupleWritable value)
/*  27:    */     throws IOException
/*  28:    */   {
/*  29: 52 */     if (this.jc.flush(value))
/*  30:    */     {
/*  31: 53 */       WritableUtils.cloneInto(key, this.jc.key());
/*  32: 54 */       return true;
/*  33:    */     }
/*  34: 56 */     this.jc.clear();
/*  35: 57 */     K iterkey = createKey();
/*  36: 58 */     PriorityQueue<ComposableRecordReader<K, ?>> q = getRecordReaderQueue();
/*  37: 59 */     while (!q.isEmpty())
/*  38:    */     {
/*  39: 60 */       fillJoinCollector(iterkey);
/*  40: 61 */       this.jc.reset(iterkey);
/*  41: 62 */       if (this.jc.flush(value))
/*  42:    */       {
/*  43: 63 */         WritableUtils.cloneInto(key, this.jc.key());
/*  44: 64 */         return true;
/*  45:    */       }
/*  46: 66 */       this.jc.clear();
/*  47:    */     }
/*  48: 68 */     return false;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public TupleWritable createValue()
/*  52:    */   {
/*  53: 73 */     return createInternalValue();
/*  54:    */   }
/*  55:    */   
/*  56:    */   protected ResetableIterator<TupleWritable> getDelegate()
/*  57:    */   {
/*  58: 80 */     return new JoinDelegationIterator();
/*  59:    */   }
/*  60:    */   
/*  61:    */   protected class JoinDelegationIterator
/*  62:    */     implements ResetableIterator<TupleWritable>
/*  63:    */   {
/*  64:    */     protected JoinDelegationIterator() {}
/*  65:    */     
/*  66:    */     public boolean hasNext()
/*  67:    */     {
/*  68: 91 */       return JoinRecordReader.this.jc.hasNext();
/*  69:    */     }
/*  70:    */     
/*  71:    */     public boolean next(TupleWritable val)
/*  72:    */       throws IOException
/*  73:    */     {
/*  74: 95 */       return JoinRecordReader.this.jc.flush(val);
/*  75:    */     }
/*  76:    */     
/*  77:    */     public boolean replay(TupleWritable val)
/*  78:    */       throws IOException
/*  79:    */     {
/*  80: 99 */       return JoinRecordReader.this.jc.replay(val);
/*  81:    */     }
/*  82:    */     
/*  83:    */     public void reset()
/*  84:    */     {
/*  85:103 */       JoinRecordReader.this.jc.reset(JoinRecordReader.this.jc.key());
/*  86:    */     }
/*  87:    */     
/*  88:    */     public void add(TupleWritable item)
/*  89:    */       throws IOException
/*  90:    */     {
/*  91:107 */       throw new UnsupportedOperationException();
/*  92:    */     }
/*  93:    */     
/*  94:    */     public void close()
/*  95:    */       throws IOException
/*  96:    */     {
/*  97:111 */       JoinRecordReader.this.jc.close();
/*  98:    */     }
/*  99:    */     
/* 100:    */     public void clear()
/* 101:    */     {
/* 102:115 */       JoinRecordReader.this.jc.clear();
/* 103:    */     }
/* 104:    */   }
/* 105:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.join.JoinRecordReader
 * JD-Core Version:    0.7.0.1
 */