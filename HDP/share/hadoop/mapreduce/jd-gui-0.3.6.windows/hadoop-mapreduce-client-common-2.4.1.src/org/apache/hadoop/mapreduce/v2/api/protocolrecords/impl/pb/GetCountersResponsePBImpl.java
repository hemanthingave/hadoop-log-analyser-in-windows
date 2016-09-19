/*   1:    */ package org.apache.hadoop.mapreduce.v2.api.protocolrecords.impl.pb;
/*   2:    */ 
/*   3:    */ import org.apache.hadoop.mapreduce.v2.api.protocolrecords.GetCountersResponse;
/*   4:    */ import org.apache.hadoop.mapreduce.v2.api.records.Counters;
/*   5:    */ import org.apache.hadoop.mapreduce.v2.api.records.impl.pb.CountersPBImpl;
/*   6:    */ import org.apache.hadoop.mapreduce.v2.proto.MRProtos.CountersProto;
/*   7:    */ import org.apache.hadoop.mapreduce.v2.proto.MRServiceProtos.GetCountersResponseProto;
/*   8:    */ import org.apache.hadoop.mapreduce.v2.proto.MRServiceProtos.GetCountersResponseProto.Builder;
/*   9:    */ import org.apache.hadoop.mapreduce.v2.proto.MRServiceProtos.GetCountersResponseProtoOrBuilder;
/*  10:    */ import org.apache.hadoop.yarn.api.records.impl.pb.ProtoBase;
/*  11:    */ 
/*  12:    */ public class GetCountersResponsePBImpl
/*  13:    */   extends ProtoBase<MRServiceProtos.GetCountersResponseProto>
/*  14:    */   implements GetCountersResponse
/*  15:    */ {
/*  16: 33 */   MRServiceProtos.GetCountersResponseProto proto = MRServiceProtos.GetCountersResponseProto.getDefaultInstance();
/*  17: 34 */   MRServiceProtos.GetCountersResponseProto.Builder builder = null;
/*  18: 35 */   boolean viaProto = false;
/*  19: 37 */   private Counters counters = null;
/*  20:    */   
/*  21:    */   public GetCountersResponsePBImpl()
/*  22:    */   {
/*  23: 41 */     this.builder = MRServiceProtos.GetCountersResponseProto.newBuilder();
/*  24:    */   }
/*  25:    */   
/*  26:    */   public GetCountersResponsePBImpl(MRServiceProtos.GetCountersResponseProto proto)
/*  27:    */   {
/*  28: 45 */     this.proto = proto;
/*  29: 46 */     this.viaProto = true;
/*  30:    */   }
/*  31:    */   
/*  32:    */   public MRServiceProtos.GetCountersResponseProto getProto()
/*  33:    */   {
/*  34: 50 */     mergeLocalToProto();
/*  35: 51 */     this.proto = (this.viaProto ? this.proto : this.builder.build());
/*  36: 52 */     this.viaProto = true;
/*  37: 53 */     return this.proto;
/*  38:    */   }
/*  39:    */   
/*  40:    */   private void mergeLocalToBuilder()
/*  41:    */   {
/*  42: 57 */     if (this.counters != null) {
/*  43: 58 */       this.builder.setCounters(convertToProtoFormat(this.counters));
/*  44:    */     }
/*  45:    */   }
/*  46:    */   
/*  47:    */   private void mergeLocalToProto()
/*  48:    */   {
/*  49: 63 */     if (this.viaProto) {
/*  50: 64 */       maybeInitBuilder();
/*  51:    */     }
/*  52: 65 */     mergeLocalToBuilder();
/*  53: 66 */     this.proto = this.builder.build();
/*  54: 67 */     this.viaProto = true;
/*  55:    */   }
/*  56:    */   
/*  57:    */   private void maybeInitBuilder()
/*  58:    */   {
/*  59: 71 */     if ((this.viaProto) || (this.builder == null)) {
/*  60: 72 */       this.builder = MRServiceProtos.GetCountersResponseProto.newBuilder(this.proto);
/*  61:    */     }
/*  62: 74 */     this.viaProto = false;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public Counters getCounters()
/*  66:    */   {
/*  67: 80 */     MRServiceProtos.GetCountersResponseProtoOrBuilder p = this.viaProto ? this.proto : this.builder;
/*  68: 81 */     if (this.counters != null) {
/*  69: 82 */       return this.counters;
/*  70:    */     }
/*  71: 84 */     if (!p.hasCounters()) {
/*  72: 85 */       return null;
/*  73:    */     }
/*  74: 87 */     this.counters = convertFromProtoFormat(p.getCounters());
/*  75: 88 */     return this.counters;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public void setCounters(Counters counters)
/*  79:    */   {
/*  80: 93 */     maybeInitBuilder();
/*  81: 94 */     if (counters == null) {
/*  82: 95 */       this.builder.clearCounters();
/*  83:    */     }
/*  84: 96 */     this.counters = counters;
/*  85:    */   }
/*  86:    */   
/*  87:    */   private CountersPBImpl convertFromProtoFormat(MRProtos.CountersProto p)
/*  88:    */   {
/*  89:100 */     return new CountersPBImpl(p);
/*  90:    */   }
/*  91:    */   
/*  92:    */   private MRProtos.CountersProto convertToProtoFormat(Counters t)
/*  93:    */   {
/*  94:104 */     return ((CountersPBImpl)t).getProto();
/*  95:    */   }
/*  96:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-common-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.v2.api.protocolrecords.impl.pb.GetCountersResponsePBImpl
 * JD-Core Version:    0.7.0.1
 */