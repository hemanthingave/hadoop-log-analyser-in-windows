/*   1:    */ package org.apache.hadoop.mapred.join;
/*   2:    */ 
/*   3:    */ import java.io.DataInput;
/*   4:    */ import java.io.DataOutput;
/*   5:    */ import java.io.IOException;
/*   6:    */ import java.util.HashSet;
/*   7:    */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*   8:    */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*   9:    */ import org.apache.hadoop.io.Text;
/*  10:    */ import org.apache.hadoop.io.WritableUtils;
/*  11:    */ import org.apache.hadoop.mapred.InputSplit;
/*  12:    */ import org.apache.hadoop.util.ReflectionUtils;
/*  13:    */ 
/*  14:    */ @InterfaceAudience.Public
/*  15:    */ @InterfaceStability.Stable
/*  16:    */ public class CompositeInputSplit
/*  17:    */   implements InputSplit
/*  18:    */ {
/*  19: 41 */   private int fill = 0;
/*  20: 42 */   private long totsize = 0L;
/*  21:    */   private InputSplit[] splits;
/*  22:    */   
/*  23:    */   public CompositeInputSplit() {}
/*  24:    */   
/*  25:    */   public CompositeInputSplit(int capacity)
/*  26:    */   {
/*  27: 48 */     this.splits = new InputSplit[capacity];
/*  28:    */   }
/*  29:    */   
/*  30:    */   public void add(InputSplit s)
/*  31:    */     throws IOException
/*  32:    */   {
/*  33: 57 */     if (null == this.splits) {
/*  34: 58 */       throw new IOException("Uninitialized InputSplit");
/*  35:    */     }
/*  36: 60 */     if (this.fill == this.splits.length) {
/*  37: 61 */       throw new IOException("Too many splits");
/*  38:    */     }
/*  39: 63 */     this.splits[(this.fill++)] = s;
/*  40: 64 */     this.totsize += s.getLength();
/*  41:    */   }
/*  42:    */   
/*  43:    */   public InputSplit get(int i)
/*  44:    */   {
/*  45: 71 */     return this.splits[i];
/*  46:    */   }
/*  47:    */   
/*  48:    */   public long getLength()
/*  49:    */     throws IOException
/*  50:    */   {
/*  51: 78 */     return this.totsize;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public long getLength(int i)
/*  55:    */     throws IOException
/*  56:    */   {
/*  57: 85 */     return this.splits[i].getLength();
/*  58:    */   }
/*  59:    */   
/*  60:    */   public String[] getLocations()
/*  61:    */     throws IOException
/*  62:    */   {
/*  63: 92 */     HashSet<String> hosts = new HashSet();
/*  64: 93 */     for (InputSplit s : this.splits)
/*  65:    */     {
/*  66: 94 */       String[] hints = s.getLocations();
/*  67: 95 */       if ((hints != null) && (hints.length > 0)) {
/*  68: 96 */         for (String host : hints) {
/*  69: 97 */           hosts.add(host);
/*  70:    */         }
/*  71:    */       }
/*  72:    */     }
/*  73:101 */     return (String[])hosts.toArray(new String[hosts.size()]);
/*  74:    */   }
/*  75:    */   
/*  76:    */   public String[] getLocation(int i)
/*  77:    */     throws IOException
/*  78:    */   {
/*  79:108 */     return this.splits[i].getLocations();
/*  80:    */   }
/*  81:    */   
/*  82:    */   public void write(DataOutput out)
/*  83:    */     throws IOException
/*  84:    */   {
/*  85:118 */     WritableUtils.writeVInt(out, this.splits.length);
/*  86:119 */     for (InputSplit s : this.splits) {
/*  87:120 */       Text.writeString(out, s.getClass().getName());
/*  88:    */     }
/*  89:122 */     for (InputSplit s : this.splits) {
/*  90:123 */       s.write(out);
/*  91:    */     }
/*  92:    */   }
/*  93:    */   
/*  94:    */   public void readFields(DataInput in)
/*  95:    */     throws IOException
/*  96:    */   {
/*  97:134 */     int card = WritableUtils.readVInt(in);
/*  98:135 */     if ((this.splits == null) || (this.splits.length != card)) {
/*  99:136 */       this.splits = new InputSplit[card];
/* 100:    */     }
/* 101:138 */     Class<? extends InputSplit>[] cls = new Class[card];
/* 102:    */     try
/* 103:    */     {
/* 104:140 */       for (int i = 0; i < card; i++) {
/* 105:141 */         cls[i] = Class.forName(Text.readString(in)).asSubclass(InputSplit.class);
/* 106:    */       }
/* 107:144 */       for (int i = 0; i < card; i++)
/* 108:    */       {
/* 109:145 */         this.splits[i] = ((InputSplit)ReflectionUtils.newInstance(cls[i], null));
/* 110:146 */         this.splits[i].readFields(in);
/* 111:    */       }
/* 112:    */     }
/* 113:    */     catch (ClassNotFoundException e)
/* 114:    */     {
/* 115:149 */       throw ((IOException)new IOException("Failed split init").initCause(e));
/* 116:    */     }
/* 117:    */   }
/* 118:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.join.CompositeInputSplit
 * JD-Core Version:    0.7.0.1
 */