/*   1:    */ package org.apache.hadoop.mapreduce.counters;
/*   2:    */ 
/*   3:    */ import java.io.DataInput;
/*   4:    */ import java.io.DataOutput;
/*   5:    */ import java.io.IOException;
/*   6:    */ import org.apache.hadoop.classification.InterfaceAudience.Private;
/*   7:    */ import org.apache.hadoop.io.Text;
/*   8:    */ import org.apache.hadoop.io.WritableUtils;
/*   9:    */ import org.apache.hadoop.mapreduce.Counter;
/*  10:    */ import org.apache.hadoop.util.StringInterner;
/*  11:    */ 
/*  12:    */ @InterfaceAudience.Private
/*  13:    */ public class GenericCounter
/*  14:    */   extends AbstractCounter
/*  15:    */ {
/*  16:    */   private String name;
/*  17:    */   private String displayName;
/*  18: 39 */   private long value = 0L;
/*  19:    */   
/*  20:    */   public GenericCounter() {}
/*  21:    */   
/*  22:    */   public GenericCounter(String name, String displayName)
/*  23:    */   {
/*  24: 46 */     this.name = name;
/*  25: 47 */     this.displayName = displayName;
/*  26:    */   }
/*  27:    */   
/*  28:    */   public GenericCounter(String name, String displayName, long value)
/*  29:    */   {
/*  30: 51 */     this.name = name;
/*  31: 52 */     this.displayName = displayName;
/*  32: 53 */     this.value = value;
/*  33:    */   }
/*  34:    */   
/*  35:    */   @Deprecated
/*  36:    */   public synchronized void setDisplayName(String displayName)
/*  37:    */   {
/*  38: 58 */     this.displayName = displayName;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public synchronized void readFields(DataInput in)
/*  42:    */     throws IOException
/*  43:    */   {
/*  44: 63 */     this.name = StringInterner.weakIntern(Text.readString(in));
/*  45: 64 */     this.displayName = (in.readBoolean() ? StringInterner.weakIntern(Text.readString(in)) : this.name);
/*  46:    */     
/*  47: 66 */     this.value = WritableUtils.readVLong(in);
/*  48:    */   }
/*  49:    */   
/*  50:    */   public synchronized void write(DataOutput out)
/*  51:    */     throws IOException
/*  52:    */   {
/*  53: 74 */     Text.writeString(out, this.name);
/*  54: 75 */     boolean distinctDisplayName = !this.name.equals(this.displayName);
/*  55: 76 */     out.writeBoolean(distinctDisplayName);
/*  56: 77 */     if (distinctDisplayName) {
/*  57: 78 */       Text.writeString(out, this.displayName);
/*  58:    */     }
/*  59: 80 */     WritableUtils.writeVLong(out, this.value);
/*  60:    */   }
/*  61:    */   
/*  62:    */   public synchronized String getName()
/*  63:    */   {
/*  64: 85 */     return this.name;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public synchronized String getDisplayName()
/*  68:    */   {
/*  69: 90 */     return this.displayName;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public synchronized long getValue()
/*  73:    */   {
/*  74: 95 */     return this.value;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public synchronized void setValue(long value)
/*  78:    */   {
/*  79:100 */     this.value = value;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public synchronized void increment(long incr)
/*  83:    */   {
/*  84:105 */     this.value += incr;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public Counter getUnderlyingCounter()
/*  88:    */   {
/*  89:110 */     return this;
/*  90:    */   }
/*  91:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.counters.GenericCounter
 * JD-Core Version:    0.7.0.1
 */