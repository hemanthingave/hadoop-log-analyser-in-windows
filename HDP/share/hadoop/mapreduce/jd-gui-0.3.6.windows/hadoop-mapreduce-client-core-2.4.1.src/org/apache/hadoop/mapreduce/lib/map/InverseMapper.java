/*  1:   */ package org.apache.hadoop.mapreduce.lib.map;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*  5:   */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*  6:   */ import org.apache.hadoop.mapreduce.Mapper;
/*  7:   */ import org.apache.hadoop.mapreduce.Mapper.Context;
/*  8:   */ 
/*  9:   */ @InterfaceAudience.Public
/* 10:   */ @InterfaceStability.Stable
/* 11:   */ public class InverseMapper<K, V>
/* 12:   */   extends Mapper<K, V, V, K>
/* 13:   */ {
/* 14:   */   public void map(K key, V value, Mapper<K, V, V, K>.Context context)
/* 15:   */     throws IOException, InterruptedException
/* 16:   */   {
/* 17:36 */     context.write(value, key);
/* 18:   */   }
/* 19:   */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.lib.map.InverseMapper
 * JD-Core Version:    0.7.0.1
 */