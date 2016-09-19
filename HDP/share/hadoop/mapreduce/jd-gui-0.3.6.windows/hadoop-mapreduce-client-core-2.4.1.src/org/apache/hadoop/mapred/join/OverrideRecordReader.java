/*  1:   */ package org.apache.hadoop.mapred.join;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import java.util.ArrayList;
/*  5:   */ import java.util.Iterator;
/*  6:   */ import java.util.PriorityQueue;
/*  7:   */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*  8:   */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*  9:   */ import org.apache.hadoop.io.Writable;
/* 10:   */ import org.apache.hadoop.io.WritableComparable;
/* 11:   */ import org.apache.hadoop.io.WritableComparator;
/* 12:   */ import org.apache.hadoop.mapred.JobConf;
/* 13:   */ 
/* 14:   */ @InterfaceAudience.Public
/* 15:   */ @InterfaceStability.Stable
/* 16:   */ public class OverrideRecordReader<K extends WritableComparable, V extends Writable>
/* 17:   */   extends MultiFilterRecordReader<K, V>
/* 18:   */ {
/* 19:   */   OverrideRecordReader(int id, JobConf conf, int capacity, Class<? extends WritableComparator> cmpcl)
/* 20:   */     throws IOException
/* 21:   */   {
/* 22:46 */     super(id, conf, capacity, cmpcl);
/* 23:   */   }
/* 24:   */   
/* 25:   */   protected V emit(TupleWritable dst)
/* 26:   */   {
/* 27:54 */     return (Writable)dst.iterator().next();
/* 28:   */   }
/* 29:   */   
/* 30:   */   protected void fillJoinCollector(K iterkey)
/* 31:   */     throws IOException
/* 32:   */   {
/* 33:67 */     PriorityQueue<ComposableRecordReader<K, ?>> q = getRecordReaderQueue();
/* 34:68 */     if (!q.isEmpty())
/* 35:   */     {
/* 36:69 */       int highpos = -1;
/* 37:70 */       ArrayList<ComposableRecordReader<K, ?>> list = new ArrayList(this.kids.length);
/* 38:   */       
/* 39:72 */       ((ComposableRecordReader)q.peek()).key(iterkey);
/* 40:73 */       WritableComparator cmp = getComparator();
/* 41:74 */       while (0 == cmp.compare(((ComposableRecordReader)q.peek()).key(), iterkey))
/* 42:   */       {
/* 43:75 */         ComposableRecordReader<K, ?> t = (ComposableRecordReader)q.poll();
/* 44:76 */         if ((-1 == highpos) || (((ComposableRecordReader)list.get(highpos)).id() < t.id())) {
/* 45:77 */           highpos = list.size();
/* 46:   */         }
/* 47:79 */         list.add(t);
/* 48:80 */         if (q.isEmpty()) {
/* 49:   */           break;
/* 50:   */         }
/* 51:   */       }
/* 52:83 */       ComposableRecordReader<K, ?> t = (ComposableRecordReader)list.remove(highpos);
/* 53:84 */       t.accept(this.jc, iterkey);
/* 54:85 */       for (ComposableRecordReader<K, ?> rr : list) {
/* 55:86 */         rr.skip(iterkey);
/* 56:   */       }
/* 57:88 */       list.add(t);
/* 58:89 */       for (ComposableRecordReader<K, ?> rr : list) {
/* 59:90 */         if (rr.hasNext()) {
/* 60:91 */           q.add(rr);
/* 61:   */         }
/* 62:   */       }
/* 63:   */     }
/* 64:   */   }
/* 65:   */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.join.OverrideRecordReader
 * JD-Core Version:    0.7.0.1
 */