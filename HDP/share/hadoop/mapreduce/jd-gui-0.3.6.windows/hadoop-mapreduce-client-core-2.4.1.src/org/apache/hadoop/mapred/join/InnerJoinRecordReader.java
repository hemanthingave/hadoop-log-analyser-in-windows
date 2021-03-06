/*  1:   */ package org.apache.hadoop.mapred.join;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*  5:   */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*  6:   */ import org.apache.hadoop.io.WritableComparable;
/*  7:   */ import org.apache.hadoop.io.WritableComparator;
/*  8:   */ import org.apache.hadoop.mapred.JobConf;
/*  9:   */ 
/* 10:   */ @InterfaceAudience.Public
/* 11:   */ @InterfaceStability.Stable
/* 12:   */ public class InnerJoinRecordReader<K extends WritableComparable>
/* 13:   */   extends JoinRecordReader<K>
/* 14:   */ {
/* 15:   */   InnerJoinRecordReader(int id, JobConf conf, int capacity, Class<? extends WritableComparator> cmpcl)
/* 16:   */     throws IOException
/* 17:   */   {
/* 18:39 */     super(id, conf, capacity, cmpcl);
/* 19:   */   }
/* 20:   */   
/* 21:   */   protected boolean combine(Object[] srcs, TupleWritable dst)
/* 22:   */   {
/* 23:46 */     assert (srcs.length == dst.size());
/* 24:47 */     for (int i = 0; i < srcs.length; i++) {
/* 25:48 */       if (!dst.has(i)) {
/* 26:49 */         return false;
/* 27:   */       }
/* 28:   */     }
/* 29:52 */     return true;
/* 30:   */   }
/* 31:   */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.join.InnerJoinRecordReader
 * JD-Core Version:    0.7.0.1
 */