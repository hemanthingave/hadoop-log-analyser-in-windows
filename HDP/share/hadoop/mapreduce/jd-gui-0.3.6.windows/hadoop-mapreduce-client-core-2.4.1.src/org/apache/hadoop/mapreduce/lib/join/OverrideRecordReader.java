/*   1:    */ package org.apache.hadoop.mapreduce.lib.join;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import java.util.Iterator;
/*   6:    */ import java.util.PriorityQueue;
/*   7:    */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*   8:    */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*   9:    */ import org.apache.hadoop.conf.Configuration;
/*  10:    */ import org.apache.hadoop.io.NullWritable;
/*  11:    */ import org.apache.hadoop.io.Writable;
/*  12:    */ import org.apache.hadoop.io.WritableComparable;
/*  13:    */ import org.apache.hadoop.io.WritableComparator;
/*  14:    */ import org.apache.hadoop.util.ReflectionUtils;
/*  15:    */ 
/*  16:    */ @InterfaceAudience.Public
/*  17:    */ @InterfaceStability.Stable
/*  18:    */ public class OverrideRecordReader<K extends WritableComparable<?>, V extends Writable>
/*  19:    */   extends MultiFilterRecordReader<K, V>
/*  20:    */ {
/*  21:    */   OverrideRecordReader(int id, Configuration conf, int capacity, Class<? extends WritableComparator> cmpcl)
/*  22:    */     throws IOException
/*  23:    */   {
/*  24: 48 */     super(id, conf, capacity, cmpcl);
/*  25:    */   }
/*  26:    */   
/*  27: 50 */   private Class<? extends Writable> valueclass = null;
/*  28:    */   
/*  29:    */   protected V emit(TupleWritable dst)
/*  30:    */   {
/*  31: 57 */     return (Writable)dst.iterator().next();
/*  32:    */   }
/*  33:    */   
/*  34:    */   public V createValue()
/*  35:    */   {
/*  36: 62 */     if (null == this.valueclass)
/*  37:    */     {
/*  38: 63 */       Class<?> cls = this.kids[(this.kids.length - 1)].createValue().getClass();
/*  39: 64 */       for (int i = this.kids.length - 1; cls.equals(NullWritable.class); i--) {
/*  40: 65 */         cls = this.kids[i].createValue().getClass();
/*  41:    */       }
/*  42: 67 */       this.valueclass = cls.asSubclass(Writable.class);
/*  43:    */     }
/*  44: 69 */     if (this.valueclass.equals(NullWritable.class)) {
/*  45: 70 */       return NullWritable.get();
/*  46:    */     }
/*  47: 72 */     return (Writable)ReflectionUtils.newInstance(this.valueclass, null);
/*  48:    */   }
/*  49:    */   
/*  50:    */   protected void fillJoinCollector(K iterkey)
/*  51:    */     throws IOException, InterruptedException
/*  52:    */   {
/*  53: 86 */     PriorityQueue<ComposableRecordReader<K, ?>> q = getRecordReaderQueue();
/*  54: 88 */     if ((q != null) && (!q.isEmpty()))
/*  55:    */     {
/*  56: 89 */       int highpos = -1;
/*  57: 90 */       ArrayList<ComposableRecordReader<K, ?>> list = new ArrayList(this.kids.length);
/*  58:    */       
/*  59: 92 */       ((ComposableRecordReader)q.peek()).key(iterkey);
/*  60: 93 */       WritableComparator cmp = getComparator();
/*  61: 94 */       while (0 == cmp.compare(((ComposableRecordReader)q.peek()).key(), iterkey))
/*  62:    */       {
/*  63: 95 */         ComposableRecordReader<K, ?> t = (ComposableRecordReader)q.poll();
/*  64: 96 */         if ((-1 == highpos) || (((ComposableRecordReader)list.get(highpos)).id() < t.id())) {
/*  65: 97 */           highpos = list.size();
/*  66:    */         }
/*  67: 99 */         list.add(t);
/*  68:100 */         if (q.isEmpty()) {
/*  69:    */           break;
/*  70:    */         }
/*  71:    */       }
/*  72:103 */       ComposableRecordReader<K, ?> t = (ComposableRecordReader)list.remove(highpos);
/*  73:104 */       t.accept(this.jc, iterkey);
/*  74:105 */       for (ComposableRecordReader<K, ?> rr : list) {
/*  75:106 */         rr.skip(iterkey);
/*  76:    */       }
/*  77:108 */       list.add(t);
/*  78:109 */       for (ComposableRecordReader<K, ?> rr : list) {
/*  79:110 */         if (rr.hasNext()) {
/*  80:111 */           q.add(rr);
/*  81:    */         }
/*  82:    */       }
/*  83:    */     }
/*  84:    */   }
/*  85:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.lib.join.OverrideRecordReader
 * JD-Core Version:    0.7.0.1
 */