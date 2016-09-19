/*   1:    */ package org.apache.hadoop.mapreduce.v2.api.records.impl.pb;
/*   2:    */ 
/*   3:    */ import org.apache.hadoop.mapreduce.v2.api.records.Counter;
/*   4:    */ import org.apache.hadoop.mapreduce.v2.proto.MRProtos.CounterProto;
/*   5:    */ import org.apache.hadoop.mapreduce.v2.proto.MRProtos.CounterProto.Builder;
/*   6:    */ import org.apache.hadoop.mapreduce.v2.proto.MRProtos.CounterProtoOrBuilder;
/*   7:    */ import org.apache.hadoop.yarn.api.records.impl.pb.ProtoBase;
/*   8:    */ 
/*   9:    */ public class CounterPBImpl
/*  10:    */   extends ProtoBase<MRProtos.CounterProto>
/*  11:    */   implements Counter
/*  12:    */ {
/*  13: 30 */   MRProtos.CounterProto proto = MRProtos.CounterProto.getDefaultInstance();
/*  14: 31 */   MRProtos.CounterProto.Builder builder = null;
/*  15: 32 */   boolean viaProto = false;
/*  16:    */   
/*  17:    */   public CounterPBImpl()
/*  18:    */   {
/*  19: 35 */     this.builder = MRProtos.CounterProto.newBuilder();
/*  20:    */   }
/*  21:    */   
/*  22:    */   public CounterPBImpl(MRProtos.CounterProto proto)
/*  23:    */   {
/*  24: 39 */     this.proto = proto;
/*  25: 40 */     this.viaProto = true;
/*  26:    */   }
/*  27:    */   
/*  28:    */   public MRProtos.CounterProto getProto()
/*  29:    */   {
/*  30: 44 */     this.proto = (this.viaProto ? this.proto : this.builder.build());
/*  31: 45 */     this.viaProto = true;
/*  32: 46 */     return this.proto;
/*  33:    */   }
/*  34:    */   
/*  35:    */   private void maybeInitBuilder()
/*  36:    */   {
/*  37: 50 */     if ((this.viaProto) || (this.builder == null)) {
/*  38: 51 */       this.builder = MRProtos.CounterProto.newBuilder(this.proto);
/*  39:    */     }
/*  40: 53 */     this.viaProto = false;
/*  41:    */   }
/*  42:    */   
/*  43:    */   public String getName()
/*  44:    */   {
/*  45: 59 */     MRProtos.CounterProtoOrBuilder p = this.viaProto ? this.proto : this.builder;
/*  46: 60 */     if (!p.hasName()) {
/*  47: 61 */       return null;
/*  48:    */     }
/*  49: 63 */     return p.getName();
/*  50:    */   }
/*  51:    */   
/*  52:    */   public void setName(String name)
/*  53:    */   {
/*  54: 68 */     maybeInitBuilder();
/*  55: 69 */     if (name == null)
/*  56:    */     {
/*  57: 70 */       this.builder.clearName();
/*  58: 71 */       return;
/*  59:    */     }
/*  60: 73 */     this.builder.setName(name);
/*  61:    */   }
/*  62:    */   
/*  63:    */   public long getValue()
/*  64:    */   {
/*  65: 77 */     MRProtos.CounterProtoOrBuilder p = this.viaProto ? this.proto : this.builder;
/*  66: 78 */     return p.getValue();
/*  67:    */   }
/*  68:    */   
/*  69:    */   public void setValue(long value)
/*  70:    */   {
/*  71: 83 */     maybeInitBuilder();
/*  72: 84 */     this.builder.setValue(value);
/*  73:    */   }
/*  74:    */   
/*  75:    */   public String getDisplayName()
/*  76:    */   {
/*  77: 88 */     MRProtos.CounterProtoOrBuilder p = this.viaProto ? this.proto : this.builder;
/*  78: 89 */     if (!p.hasDisplayName()) {
/*  79: 90 */       return null;
/*  80:    */     }
/*  81: 92 */     return p.getDisplayName();
/*  82:    */   }
/*  83:    */   
/*  84:    */   public void setDisplayName(String displayName)
/*  85:    */   {
/*  86: 97 */     maybeInitBuilder();
/*  87: 98 */     if (displayName == null)
/*  88:    */     {
/*  89: 99 */       this.builder.clearDisplayName();
/*  90:100 */       return;
/*  91:    */     }
/*  92:102 */     this.builder.setDisplayName(displayName);
/*  93:    */   }
/*  94:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-common-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.v2.api.records.impl.pb.CounterPBImpl
 * JD-Core Version:    0.7.0.1
 */