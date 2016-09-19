/*  1:   */ package org.apache.hadoop.mapreduce.jobhistory;
/*  2:   */ 
/*  3:   */ import java.util.ArrayList;
/*  4:   */ import java.util.Iterator;
/*  5:   */ import java.util.List;
/*  6:   */ import org.apache.avro.Schema;
/*  7:   */ import org.apache.avro.Schema.Type;
/*  8:   */ import org.apache.avro.generic.GenericData.Array;
/*  9:   */ 
/* 10:   */ public class AvroArrayUtils
/* 11:   */ {
/* 12:30 */   private static final Schema ARRAY_INT = Schema.createArray(Schema.create(Schema.Type.INT));
/* 13:33 */   public static List<Integer> NULL_PROGRESS_SPLITS_ARRAY = new GenericData.Array(0, ARRAY_INT);
/* 14:   */   
/* 15:   */   public static List<Integer> toAvro(int[] values)
/* 16:   */   {
/* 17:38 */     List<Integer> result = new ArrayList(values.length);
/* 18:40 */     for (int i = 0; i < values.length; i++) {
/* 19:41 */       result.add(Integer.valueOf(values[i]));
/* 20:   */     }
/* 21:44 */     return result;
/* 22:   */   }
/* 23:   */   
/* 24:   */   public static int[] fromAvro(List<Integer> avro)
/* 25:   */   {
/* 26:48 */     int[] result = new int[avro.size()];
/* 27:   */     
/* 28:50 */     int i = 0;
/* 29:52 */     for (Iterator<Integer> iter = avro.iterator(); iter.hasNext(); i++) {
/* 30:53 */       result[i] = ((Integer)iter.next()).intValue();
/* 31:   */     }
/* 32:56 */     return result;
/* 33:   */   }
/* 34:   */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.jobhistory.AvroArrayUtils
 * JD-Core Version:    0.7.0.1
 */