/*   1:    */ package org.apache.hadoop.mapreduce.lib.partition;
/*   2:    */ 
/*   3:    */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*   4:    */ import org.apache.hadoop.classification.InterfaceStability.Evolving;
/*   5:    */ import org.apache.hadoop.conf.Configurable;
/*   6:    */ import org.apache.hadoop.conf.Configuration;
/*   7:    */ import org.apache.hadoop.io.BinaryComparable;
/*   8:    */ import org.apache.hadoop.io.WritableComparator;
/*   9:    */ import org.apache.hadoop.mapreduce.Partitioner;
/*  10:    */ 
/*  11:    */ @InterfaceAudience.Public
/*  12:    */ @InterfaceStability.Evolving
/*  13:    */ public class BinaryPartitioner<V>
/*  14:    */   extends Partitioner<BinaryComparable, V>
/*  15:    */   implements Configurable
/*  16:    */ {
/*  17:    */   public static final String LEFT_OFFSET_PROPERTY_NAME = "mapreduce.partition.binarypartitioner.left.offset";
/*  18:    */   public static final String RIGHT_OFFSET_PROPERTY_NAME = "mapreduce.partition.binarypartitioner.right.offset";
/*  19:    */   private Configuration conf;
/*  20:    */   private int leftOffset;
/*  21:    */   private int rightOffset;
/*  22:    */   
/*  23:    */   public static void setOffsets(Configuration conf, int left, int right)
/*  24:    */   {
/*  25: 88 */     conf.setInt("mapreduce.partition.binarypartitioner.left.offset", left);
/*  26: 89 */     conf.setInt("mapreduce.partition.binarypartitioner.right.offset", right);
/*  27:    */   }
/*  28:    */   
/*  29:    */   public static void setLeftOffset(Configuration conf, int offset)
/*  30:    */   {
/*  31:100 */     conf.setInt("mapreduce.partition.binarypartitioner.left.offset", offset);
/*  32:    */   }
/*  33:    */   
/*  34:    */   public static void setRightOffset(Configuration conf, int offset)
/*  35:    */   {
/*  36:111 */     conf.setInt("mapreduce.partition.binarypartitioner.right.offset", offset);
/*  37:    */   }
/*  38:    */   
/*  39:    */   public void setConf(Configuration conf)
/*  40:    */   {
/*  41:119 */     this.conf = conf;
/*  42:120 */     this.leftOffset = conf.getInt("mapreduce.partition.binarypartitioner.left.offset", 0);
/*  43:121 */     this.rightOffset = conf.getInt("mapreduce.partition.binarypartitioner.right.offset", -1);
/*  44:    */   }
/*  45:    */   
/*  46:    */   public Configuration getConf()
/*  47:    */   {
/*  48:125 */     return this.conf;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public int getPartition(BinaryComparable key, V value, int numPartitions)
/*  52:    */   {
/*  53:134 */     int length = key.getLength();
/*  54:135 */     int leftIndex = (this.leftOffset + length) % length;
/*  55:136 */     int rightIndex = (this.rightOffset + length) % length;
/*  56:137 */     int hash = WritableComparator.hashBytes(key.getBytes(), leftIndex, rightIndex - leftIndex + 1);
/*  57:    */     
/*  58:139 */     return (hash & 0x7FFFFFFF) % numPartitions;
/*  59:    */   }
/*  60:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.lib.partition.BinaryPartitioner
 * JD-Core Version:    0.7.0.1
 */