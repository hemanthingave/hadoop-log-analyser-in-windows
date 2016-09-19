/*   1:    */ package org.apache.hadoop.mapreduce.lib.join;
/*   2:    */ 
/*   3:    */ import java.io.DataInput;
/*   4:    */ import java.io.DataInputStream;
/*   5:    */ import java.io.DataOutput;
/*   6:    */ import java.io.DataOutputStream;
/*   7:    */ import java.io.IOException;
/*   8:    */ import java.util.HashSet;
/*   9:    */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*  10:    */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*  11:    */ import org.apache.hadoop.conf.Configuration;
/*  12:    */ import org.apache.hadoop.io.Text;
/*  13:    */ import org.apache.hadoop.io.Writable;
/*  14:    */ import org.apache.hadoop.io.WritableUtils;
/*  15:    */ import org.apache.hadoop.io.serializer.Deserializer;
/*  16:    */ import org.apache.hadoop.io.serializer.SerializationFactory;
/*  17:    */ import org.apache.hadoop.io.serializer.Serializer;
/*  18:    */ import org.apache.hadoop.mapreduce.InputSplit;
/*  19:    */ import org.apache.hadoop.util.ReflectionUtils;
/*  20:    */ 
/*  21:    */ @InterfaceAudience.Public
/*  22:    */ @InterfaceStability.Stable
/*  23:    */ public class CompositeInputSplit
/*  24:    */   extends InputSplit
/*  25:    */   implements Writable
/*  26:    */ {
/*  27: 46 */   private int fill = 0;
/*  28: 47 */   private long totsize = 0L;
/*  29:    */   private InputSplit[] splits;
/*  30: 49 */   private Configuration conf = new Configuration();
/*  31:    */   
/*  32:    */   public CompositeInputSplit() {}
/*  33:    */   
/*  34:    */   public CompositeInputSplit(int capacity)
/*  35:    */   {
/*  36: 54 */     this.splits = new InputSplit[capacity];
/*  37:    */   }
/*  38:    */   
/*  39:    */   public void add(InputSplit s)
/*  40:    */     throws IOException, InterruptedException
/*  41:    */   {
/*  42: 63 */     if (null == this.splits) {
/*  43: 64 */       throw new IOException("Uninitialized InputSplit");
/*  44:    */     }
/*  45: 66 */     if (this.fill == this.splits.length) {
/*  46: 67 */       throw new IOException("Too many splits");
/*  47:    */     }
/*  48: 69 */     this.splits[(this.fill++)] = s;
/*  49: 70 */     this.totsize += s.getLength();
/*  50:    */   }
/*  51:    */   
/*  52:    */   public InputSplit get(int i)
/*  53:    */   {
/*  54: 77 */     return this.splits[i];
/*  55:    */   }
/*  56:    */   
/*  57:    */   public long getLength()
/*  58:    */     throws IOException
/*  59:    */   {
/*  60: 84 */     return this.totsize;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public long getLength(int i)
/*  64:    */     throws IOException, InterruptedException
/*  65:    */   {
/*  66: 91 */     return this.splits[i].getLength();
/*  67:    */   }
/*  68:    */   
/*  69:    */   public String[] getLocations()
/*  70:    */     throws IOException, InterruptedException
/*  71:    */   {
/*  72: 98 */     HashSet<String> hosts = new HashSet();
/*  73: 99 */     for (InputSplit s : this.splits)
/*  74:    */     {
/*  75:100 */       String[] hints = s.getLocations();
/*  76:101 */       if ((hints != null) && (hints.length > 0)) {
/*  77:102 */         for (String host : hints) {
/*  78:103 */           hosts.add(host);
/*  79:    */         }
/*  80:    */       }
/*  81:    */     }
/*  82:107 */     return (String[])hosts.toArray(new String[hosts.size()]);
/*  83:    */   }
/*  84:    */   
/*  85:    */   public String[] getLocation(int i)
/*  86:    */     throws IOException, InterruptedException
/*  87:    */   {
/*  88:114 */     return this.splits[i].getLocations();
/*  89:    */   }
/*  90:    */   
/*  91:    */   public void write(DataOutput out)
/*  92:    */     throws IOException
/*  93:    */   {
/*  94:125 */     WritableUtils.writeVInt(out, this.splits.length);
/*  95:126 */     for (InputSplit s : this.splits) {
/*  96:127 */       Text.writeString(out, s.getClass().getName());
/*  97:    */     }
/*  98:129 */     for (InputSplit s : this.splits)
/*  99:    */     {
/* 100:130 */       SerializationFactory factory = new SerializationFactory(this.conf);
/* 101:131 */       Serializer serializer = factory.getSerializer(s.getClass());
/* 102:    */       
/* 103:133 */       serializer.open((DataOutputStream)out);
/* 104:134 */       serializer.serialize(s);
/* 105:    */     }
/* 106:    */   }
/* 107:    */   
/* 108:    */   public void readFields(DataInput in)
/* 109:    */     throws IOException
/* 110:    */   {
/* 111:145 */     int card = WritableUtils.readVInt(in);
/* 112:146 */     if ((this.splits == null) || (this.splits.length != card)) {
/* 113:147 */       this.splits = new InputSplit[card];
/* 114:    */     }
/* 115:149 */     Class<? extends InputSplit>[] cls = new Class[card];
/* 116:    */     try
/* 117:    */     {
/* 118:151 */       for (int i = 0; i < card; i++) {
/* 119:152 */         cls[i] = Class.forName(Text.readString(in)).asSubclass(InputSplit.class);
/* 120:    */       }
/* 121:155 */       for (int i = 0; i < card; i++)
/* 122:    */       {
/* 123:156 */         this.splits[i] = ((InputSplit)ReflectionUtils.newInstance(cls[i], null));
/* 124:157 */         SerializationFactory factory = new SerializationFactory(this.conf);
/* 125:158 */         Deserializer deserializer = factory.getDeserializer(cls[i]);
/* 126:159 */         deserializer.open((DataInputStream)in);
/* 127:160 */         this.splits[i] = ((InputSplit)deserializer.deserialize(this.splits[i]));
/* 128:    */       }
/* 129:    */     }
/* 130:    */     catch (ClassNotFoundException e)
/* 131:    */     {
/* 132:163 */       throw new IOException("Failed split init", e);
/* 133:    */     }
/* 134:    */   }
/* 135:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.lib.join.CompositeInputSplit
 * JD-Core Version:    0.7.0.1
 */