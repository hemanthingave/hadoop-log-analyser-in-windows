/*   1:    */ package org.apache.hadoop.mapreduce.task.reduce;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.InputStream;
/*   5:    */ import java.util.Comparator;
/*   6:    */ import java.util.concurrent.atomic.AtomicInteger;
/*   7:    */ import org.apache.hadoop.classification.InterfaceAudience.LimitedPrivate;
/*   8:    */ import org.apache.hadoop.classification.InterfaceStability.Unstable;
/*   9:    */ import org.apache.hadoop.mapred.Reporter;
/*  10:    */ import org.apache.hadoop.mapreduce.TaskAttemptID;
/*  11:    */ 
/*  12:    */ @InterfaceAudience.LimitedPrivate({"MapReduce"})
/*  13:    */ @InterfaceStability.Unstable
/*  14:    */ public abstract class MapOutput<K, V>
/*  15:    */ {
/*  16: 36 */   private static AtomicInteger ID = new AtomicInteger(0);
/*  17:    */   private final int id;
/*  18:    */   private final TaskAttemptID mapId;
/*  19:    */   private final long size;
/*  20:    */   private final boolean primaryMapOutput;
/*  21:    */   
/*  22:    */   public MapOutput(TaskAttemptID mapId, long size, boolean primaryMapOutput)
/*  23:    */   {
/*  24: 44 */     this.id = ID.incrementAndGet();
/*  25: 45 */     this.mapId = mapId;
/*  26: 46 */     this.size = size;
/*  27: 47 */     this.primaryMapOutput = primaryMapOutput;
/*  28:    */   }
/*  29:    */   
/*  30:    */   public boolean isPrimaryMapOutput()
/*  31:    */   {
/*  32: 51 */     return this.primaryMapOutput;
/*  33:    */   }
/*  34:    */   
/*  35:    */   public boolean equals(Object obj)
/*  36:    */   {
/*  37: 56 */     if ((obj instanceof MapOutput)) {
/*  38: 57 */       return this.id == ((MapOutput)obj).id;
/*  39:    */     }
/*  40: 59 */     return false;
/*  41:    */   }
/*  42:    */   
/*  43:    */   public int hashCode()
/*  44:    */   {
/*  45: 64 */     return this.id;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public TaskAttemptID getMapId()
/*  49:    */   {
/*  50: 68 */     return this.mapId;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public long getSize()
/*  54:    */   {
/*  55: 72 */     return this.size;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public abstract void shuffle(MapHost paramMapHost, InputStream paramInputStream, long paramLong1, long paramLong2, ShuffleClientMetrics paramShuffleClientMetrics, Reporter paramReporter)
/*  59:    */     throws IOException;
/*  60:    */   
/*  61:    */   public abstract void commit()
/*  62:    */     throws IOException;
/*  63:    */   
/*  64:    */   public abstract void abort();
/*  65:    */   
/*  66:    */   public abstract String getDescription();
/*  67:    */   
/*  68:    */   public String toString()
/*  69:    */   {
/*  70: 88 */     return "MapOutput(" + this.mapId + ", " + getDescription() + ")";
/*  71:    */   }
/*  72:    */   
/*  73:    */   public static class MapOutputComparator<K, V>
/*  74:    */     implements Comparator<MapOutput<K, V>>
/*  75:    */   {
/*  76:    */     public int compare(MapOutput<K, V> o1, MapOutput<K, V> o2)
/*  77:    */     {
/*  78: 94 */       if (o1.id == o2.id) {
/*  79: 95 */         return 0;
/*  80:    */       }
/*  81: 98 */       if (o1.size < o2.size) {
/*  82: 99 */         return -1;
/*  83:    */       }
/*  84:100 */       if (o1.size > o2.size) {
/*  85:101 */         return 1;
/*  86:    */       }
/*  87:104 */       if (o1.id < o2.id) {
/*  88:105 */         return -1;
/*  89:    */       }
/*  90:107 */       return 1;
/*  91:    */     }
/*  92:    */   }
/*  93:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.task.reduce.MapOutput
 * JD-Core Version:    0.7.0.1
 */