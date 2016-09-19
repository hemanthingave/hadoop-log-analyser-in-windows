/*  1:   */ package org.apache.hadoop.mapreduce.v2.api.protocolrecords.impl.pb;
/*  2:   */ 
/*  3:   */ import org.apache.hadoop.mapreduce.v2.api.protocolrecords.GetDelegationTokenRequest;
/*  4:   */ import org.apache.hadoop.security.proto.SecurityProtos.GetDelegationTokenRequestProto;
/*  5:   */ import org.apache.hadoop.security.proto.SecurityProtos.GetDelegationTokenRequestProto.Builder;
/*  6:   */ import org.apache.hadoop.security.proto.SecurityProtos.GetDelegationTokenRequestProtoOrBuilder;
/*  7:   */ import org.apache.hadoop.yarn.api.records.impl.pb.ProtoBase;
/*  8:   */ 
/*  9:   */ public class GetDelegationTokenRequestPBImpl
/* 10:   */   extends ProtoBase<SecurityProtos.GetDelegationTokenRequestProto>
/* 11:   */   implements GetDelegationTokenRequest
/* 12:   */ {
/* 13:   */   String renewer;
/* 14:32 */   SecurityProtos.GetDelegationTokenRequestProto proto = SecurityProtos.GetDelegationTokenRequestProto.getDefaultInstance();
/* 15:34 */   SecurityProtos.GetDelegationTokenRequestProto.Builder builder = null;
/* 16:35 */   boolean viaProto = false;
/* 17:   */   
/* 18:   */   public GetDelegationTokenRequestPBImpl()
/* 19:   */   {
/* 20:38 */     this.builder = SecurityProtos.GetDelegationTokenRequestProto.newBuilder();
/* 21:   */   }
/* 22:   */   
/* 23:   */   public GetDelegationTokenRequestPBImpl(SecurityProtos.GetDelegationTokenRequestProto proto)
/* 24:   */   {
/* 25:43 */     this.proto = proto;
/* 26:44 */     this.viaProto = true;
/* 27:   */   }
/* 28:   */   
/* 29:   */   public String getRenewer()
/* 30:   */   {
/* 31:49 */     SecurityProtos.GetDelegationTokenRequestProtoOrBuilder p = this.viaProto ? this.proto : this.builder;
/* 32:50 */     if (this.renewer != null) {
/* 33:51 */       return this.renewer;
/* 34:   */     }
/* 35:53 */     this.renewer = p.getRenewer();
/* 36:54 */     return this.renewer;
/* 37:   */   }
/* 38:   */   
/* 39:   */   public void setRenewer(String renewer)
/* 40:   */   {
/* 41:59 */     maybeInitBuilder();
/* 42:60 */     if (renewer == null) {
/* 43:61 */       this.builder.clearRenewer();
/* 44:   */     }
/* 45:62 */     this.renewer = renewer;
/* 46:   */   }
/* 47:   */   
/* 48:   */   public SecurityProtos.GetDelegationTokenRequestProto getProto()
/* 49:   */   {
/* 50:67 */     mergeLocalToProto();
/* 51:68 */     this.proto = (this.viaProto ? this.proto : this.builder.build());
/* 52:69 */     this.viaProto = true;
/* 53:70 */     return this.proto;
/* 54:   */   }
/* 55:   */   
/* 56:   */   private void mergeLocalToBuilder()
/* 57:   */   {
/* 58:75 */     if (this.renewer != null) {
/* 59:76 */       this.builder.setRenewer(this.renewer);
/* 60:   */     }
/* 61:   */   }
/* 62:   */   
/* 63:   */   private void mergeLocalToProto()
/* 64:   */   {
/* 65:81 */     if (this.viaProto) {
/* 66:82 */       maybeInitBuilder();
/* 67:   */     }
/* 68:83 */     mergeLocalToBuilder();
/* 69:84 */     this.proto = this.builder.build();
/* 70:85 */     this.viaProto = true;
/* 71:   */   }
/* 72:   */   
/* 73:   */   private void maybeInitBuilder()
/* 74:   */   {
/* 75:89 */     if ((this.viaProto) || (this.builder == null)) {
/* 76:90 */       this.builder = SecurityProtos.GetDelegationTokenRequestProto.newBuilder(this.proto);
/* 77:   */     }
/* 78:92 */     this.viaProto = false;
/* 79:   */   }
/* 80:   */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-common-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.v2.api.protocolrecords.impl.pb.GetDelegationTokenRequestPBImpl
 * JD-Core Version:    0.7.0.1
 */