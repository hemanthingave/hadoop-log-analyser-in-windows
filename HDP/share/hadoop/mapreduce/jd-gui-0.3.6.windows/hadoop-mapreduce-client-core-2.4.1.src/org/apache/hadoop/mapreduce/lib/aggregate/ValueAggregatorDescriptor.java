/*  1:   */ package org.apache.hadoop.mapreduce.lib.aggregate;
/*  2:   */ 
/*  3:   */ import java.util.ArrayList;
/*  4:   */ import java.util.Map.Entry;
/*  5:   */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*  6:   */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*  7:   */ import org.apache.hadoop.conf.Configuration;
/*  8:   */ import org.apache.hadoop.io.Text;
/*  9:   */ 
/* 10:   */ @InterfaceAudience.Public
/* 11:   */ @InterfaceStability.Stable
/* 12:   */ public abstract interface ValueAggregatorDescriptor
/* 13:   */ {
/* 14:   */   public static final String TYPE_SEPARATOR = ":";
/* 15:48 */   public static final Text ONE = new Text("1");
/* 16:   */   
/* 17:   */   public abstract ArrayList<Map.Entry<Text, Text>> generateKeyValPairs(Object paramObject1, Object paramObject2);
/* 18:   */   
/* 19:   */   public abstract void configure(Configuration paramConfiguration);
/* 20:   */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.lib.aggregate.ValueAggregatorDescriptor
 * JD-Core Version:    0.7.0.1
 */