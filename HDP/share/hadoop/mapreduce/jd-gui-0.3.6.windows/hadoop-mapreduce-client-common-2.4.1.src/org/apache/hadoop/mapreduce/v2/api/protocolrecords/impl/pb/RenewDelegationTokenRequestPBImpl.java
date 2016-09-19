/*   1:    */ package org.apache.hadoop.mapreduce.v2.api.protocolrecords.impl.pb;
/*   2:    */ 
/*   3:    */ import org.apache.hadoop.mapreduce.v2.api.protocolrecords.RenewDelegationTokenRequest;
/*   4:    */ import org.apache.hadoop.security.proto.SecurityProtos.RenewDelegationTokenRequestProto;
/*   5:    */ import org.apache.hadoop.security.proto.SecurityProtos.RenewDelegationTokenRequestProto.Builder;
/*   6:    */ import org.apache.hadoop.security.proto.SecurityProtos.RenewDelegationTokenRequestProtoOrBuilder;
/*   7:    */ import org.apache.hadoop.security.proto.SecurityProtos.TokenProto;
/*   8:    */ import org.apache.hadoop.yarn.api.records.Token;
/*   9:    */ import org.apache.hadoop.yarn.api.records.impl.pb.ProtoBase;
/*  10:    */ import org.apache.hadoop.yarn.api.records.impl.pb.TokenPBImpl;
/*  11:    */ 
/*  12:    */ public class RenewDelegationTokenRequestPBImpl
/*  13:    */   extends ProtoBase<SecurityProtos.RenewDelegationTokenRequestProto>
/*  14:    */   implements RenewDelegationTokenRequest
/*  15:    */ {
/*  16: 32 */   SecurityProtos.RenewDelegationTokenRequestProto proto = SecurityProtos.RenewDelegationTokenRequestProto.getDefaultInstance();
/*  17: 34 */   SecurityProtos.RenewDelegationTokenRequestProto.Builder builder = null;
/*  18: 35 */   boolean viaProto = false;
/*  19:    */   Token token;
/*  20:    */   
/*  21:    */   public RenewDelegationTokenRequestPBImpl()
/*  22:    */   {
/*  23: 38 */     this.builder = SecurityProtos.RenewDelegationTokenRequestProto.newBuilder();
/*  24:    */   }
/*  25:    */   
/*  26:    */   public RenewDelegationTokenRequestPBImpl(SecurityProtos.RenewDelegationTokenRequestProto proto)
/*  27:    */   {
/*  28: 43 */     this.proto = proto;
/*  29: 44 */     this.viaProto = true;
/*  30:    */   }
/*  31:    */   
/*  32:    */   public Token getDelegationToken()
/*  33:    */   {
/*  34: 51 */     SecurityProtos.RenewDelegationTokenRequestProtoOrBuilder p = this.viaProto ? this.proto : this.builder;
/*  35: 52 */     if (this.token != null) {
/*  36: 53 */       return this.token;
/*  37:    */     }
/*  38: 55 */     this.token = convertFromProtoFormat(p.getToken());
/*  39: 56 */     return this.token;
/*  40:    */   }
/*  41:    */   
/*  42:    */   public void setDelegationToken(Token token)
/*  43:    */   {
/*  44: 61 */     maybeInitBuilder();
/*  45: 62 */     if (token == null) {
/*  46: 63 */       this.builder.clearToken();
/*  47:    */     }
/*  48: 64 */     this.token = token;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public SecurityProtos.RenewDelegationTokenRequestProto getProto()
/*  52:    */   {
/*  53: 69 */     mergeLocalToProto();
/*  54: 70 */     this.proto = (this.viaProto ? this.proto : this.builder.build());
/*  55: 71 */     this.viaProto = true;
/*  56: 72 */     return this.proto;
/*  57:    */   }
/*  58:    */   
/*  59:    */   private void mergeLocalToBuilder()
/*  60:    */   {
/*  61: 76 */     if (this.token != null) {
/*  62: 77 */       this.builder.setToken(convertToProtoFormat(this.token));
/*  63:    */     }
/*  64:    */   }
/*  65:    */   
/*  66:    */   private void mergeLocalToProto()
/*  67:    */   {
/*  68: 82 */     if (this.viaProto) {
/*  69: 83 */       maybeInitBuilder();
/*  70:    */     }
/*  71: 84 */     mergeLocalToBuilder();
/*  72: 85 */     this.proto = this.builder.build();
/*  73: 86 */     this.viaProto = true;
/*  74:    */   }
/*  75:    */   
/*  76:    */   private void maybeInitBuilder()
/*  77:    */   {
/*  78: 90 */     if ((this.viaProto) || (this.builder == null)) {
/*  79: 91 */       this.builder = SecurityProtos.RenewDelegationTokenRequestProto.newBuilder(this.proto);
/*  80:    */     }
/*  81: 93 */     this.viaProto = false;
/*  82:    */   }
/*  83:    */   
/*  84:    */   private TokenPBImpl convertFromProtoFormat(SecurityProtos.TokenProto p)
/*  85:    */   {
/*  86: 97 */     return new TokenPBImpl(p);
/*  87:    */   }
/*  88:    */   
/*  89:    */   private SecurityProtos.TokenProto convertToProtoFormat(Token t)
/*  90:    */   {
/*  91:101 */     return ((TokenPBImpl)t).getProto();
/*  92:    */   }
/*  93:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-common-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.v2.api.protocolrecords.impl.pb.RenewDelegationTokenRequestPBImpl
 * JD-Core Version:    0.7.0.1
 */