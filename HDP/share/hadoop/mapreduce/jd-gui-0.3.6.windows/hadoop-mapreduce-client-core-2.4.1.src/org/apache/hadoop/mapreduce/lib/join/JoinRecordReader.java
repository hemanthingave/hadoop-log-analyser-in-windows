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
/*  11:    */ import org.apache.hadoop.util.ReflectionUtils;
/*  12:    */ 
/*  13:    */ @InterfaceAudience.Public
/*  14:    */ @InterfaceStability.Stable
/*  15:    */ public abstract class JoinRecordReader<K extends WritableComparable<?>>
/*  16:    */   extends CompositeRecordReader<K, Writable, TupleWritable>
/*  17:    */ {
/*  18:    */   public JoinRecordReader(int id, Configuration conf, int capacity, Class<? extends WritableComparator> cmpcl)
/*  19:    */     throws IOException
/*  20:    */   {
/*  21: 42 */     super(id, capacity, cmpcl);
/*  22: 43 */     setConf(conf);
/*  23:    */   }
/*  24:    */   
/*  25:    */   public boolean nextKeyValue()
/*  26:    */     throws IOException, InterruptedException
/*  27:    */   {
/*  28: 52 */     if (this.key == null) {
/*  29: 53 */       this.key = createKey();
/*  30:    */     }
/*  31: 55 */     if (this.jc.flush((TupleWritable)this.value))
/*  32:    */     {
/*  33: 56 */       ReflectionUtils.copy(this.conf, this.jc.key(), this.key);
/*  34: 57 */       return true;
/*  35:    */     }
/*  36: 59 */     this.jc.clear();
/*  37: 60 */     if (this.value == null) {
/*  38: 61 */       this.value = createValue();
/*  39:    */     }
/*  40: 63 */     PriorityQueue<ComposableRecordReader<K, ?>> q = getRecordReaderQueue();
/*  41:    */     
/*  42: 65 */     K iterkey = createKey();
/*  43: 66 */     while ((q != null) && (!q.isEmpty()))
/*  44:    */     {
/*  45: 67 */       fillJoinCollector(iterkey);
/*  46: 68 */       this.jc.reset(iterkey);
/*  47: 69 */       if (this.jc.flush((TupleWritable)this.value))
/*  48:    */       {
/*  49: 70 */         ReflectionUtils.copy(this.conf, this.jc.key(), this.key);
/*  50: 71 */         return true;
/*  51:    */       }
/*  52: 73 */       this.jc.clear();
/*  53:    */     }
/*  54: 75 */     return false;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public TupleWritable createValue()
/*  58:    */   {
/*  59: 79 */     return createTupleWritable();
/*  60:    */   }
/*  61:    */   
/*  62:    */   protected ResetableIterator<TupleWritable> getDelegate()
/*  63:    */   {
/*  64: 86 */     return new JoinDelegationIterator();
/*  65:    */   }
/*  66:    */   
/*  67:    */   protected class JoinDelegationIterator
/*  68:    */     implements ResetableIterator<TupleWritable>
/*  69:    */   {
/*  70:    */     protected JoinDelegationIterator() {}
/*  71:    */     
/*  72:    */     public boolean hasNext()
/*  73:    */     {
/*  74: 97 */       return JoinRecordReader.this.jc.hasNext();
/*  75:    */     }
/*  76:    */     
/*  77:    */     public boolean next(TupleWritable val)
/*  78:    */       throws IOException
/*  79:    */     {
/*  80:101 */       return JoinRecordReader.this.jc.flush(val);
/*  81:    */     }
/*  82:    */     
/*  83:    */     public boolean replay(TupleWritable val)
/*  84:    */       throws IOException
/*  85:    */     {
/*  86:105 */       return JoinRecordReader.this.jc.replay(val);
/*  87:    */     }
/*  88:    */     
/*  89:    */     public void reset()
/*  90:    */     {
/*  91:109 */       JoinRecordReader.this.jc.reset(JoinRecordReader.this.jc.key());
/*  92:    */     }
/*  93:    */     
/*  94:    */     public void add(TupleWritable item)
/*  95:    */       throws IOException
/*  96:    */     {
/*  97:113 */       throw new UnsupportedOperationException();
/*  98:    */     }
/*  99:    */     
/* 100:    */     public void close()
/* 101:    */       throws IOException
/* 102:    */     {
/* 103:117 */       JoinRecordReader.this.jc.close();
/* 104:    */     }
/* 105:    */     
/* 106:    */     public void clear()
/* 107:    */     {
/* 108:121 */       JoinRecordReader.this.jc.clear();
/* 109:    */     }
/* 110:    */   }
/* 111:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.lib.join.JoinRecordReader
 * JD-Core Version:    0.7.0.1
 */