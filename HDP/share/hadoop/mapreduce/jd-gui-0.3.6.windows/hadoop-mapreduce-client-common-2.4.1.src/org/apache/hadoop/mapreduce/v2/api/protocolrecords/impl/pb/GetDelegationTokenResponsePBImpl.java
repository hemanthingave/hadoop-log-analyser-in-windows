/*   1:    */ package org.apache.hadoop.mapreduce.v2.api.protocolrecords.impl.pb;
/*   2:    */ 
/*   3:    */ import org.apache.hadoop.mapreduce.v2.api.protocolrecords.GetDelegationTokenResponse;
/*   4:    */ import org.apache.hadoop.security.proto.SecurityProtos.GetDelegationTokenResponseProto;
/*   5:    */ import org.apache.hadoop.security.proto.SecurityProtos.GetDelegationTokenResponseProto.Builder;
/*   6:    */ import org.apache.hadoop.security.proto.SecurityProtos.GetDelegationTokenResponseProtoOrBuilder;
/*   7:    */ import org.apache.hadoop.security.proto.SecurityProtos.TokenProto;
/*   8:    */ import org.apache.hadoop.yarn.api.records.Token;
/*   9:    */ import org.apache.hadoop.yarn.api.records.impl.pb.ProtoBase;
/*  10:    */ import org.apache.hadoop.yarn.api.records.impl.pb.TokenPBImpl;
/*  11:    */ 
/*  12:    */ public class GetDelegationTokenResponsePBImpl
/*  13:    */   extends ProtoBase<SecurityProtos.GetDelegationTokenResponseProto>
/*  14:    */   implements GetDelegationTokenResponse
/*  15:    */ {
/*  16:    */   Token mrToken;
/*  17: 34 */   SecurityProtos.GetDelegationTokenResponseProto proto = SecurityProtos.GetDelegationTokenResponseProto.getDefaultInstance();
/*  18: 36 */   SecurityProtos.GetDelegationTokenResponseProto.Builder builder = null;
/*  19: 37 */   boolean viaProto = false;
/*  20:    */   
/*  21:    */   public GetDelegationTokenResponsePBImpl()
/*  22:    */   {
/*  23: 40 */     this.builder = SecurityProtos.GetDelegationTokenResponseProto.newBuilder();
/*  24:    */   }
/*  25:    */   
/*  26:    */   public GetDelegationTokenResponsePBImpl(SecurityProtos.GetDelegationTokenResponseProto proto)
/*  27:    */   {
/*  28: 45 */     this.proto = proto;
/*  29: 46 */     this.viaProto = true;
/*  30:    */   }
/*  31:    */   
/*  32:    */   public Token getDelegationToken()
/*  33:    */   {
/*  34: 51 */     SecurityProtos.GetDelegationTokenResponseProtoOrBuilder p = this.viaProto ? this.proto : this.builder;
/*  35: 52 */     if (this.mrToken != null) {
/*  36: 53 */       return this.mrToken;
/*  37:    */     }
/*  38: 55 */     if (!p.hasToken()) {
/*  39: 56 */       return null;
/*  40:    */     }
/*  41: 58 */     this.mrToken = convertFromProtoFormat(p.getToken());
/*  42: 59 */     return this.mrToken;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public void setDelegationToken(Token mrToken)
/*  46:    */   {
/*  47: 64 */     maybeInitBuilder();
/*  48: 65 */     if (mrToken == null) {
/*  49: 66 */       this.builder.getToken();
/*  50:    */     }
/*  51: 67 */     this.mrToken = mrToken;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public SecurityProtos.GetDelegationTokenResponseProto getProto()
/*  55:    */   {
/*  56: 72 */     mergeLocalToProto();
/*  57: 73 */     this.proto = (this.viaProto ? this.proto : this.builder.build());
/*  58: 74 */     this.viaProto = true;
/*  59: 75 */     return this.proto;
/*  60:    */   }
/*  61:    */   
/*  62:    */   private void mergeLocalToBuilder()
/*  63:    */   {
/*  64: 80 */     if (this.mrToken != null) {
/*  65: 81 */       this.builder.setToken(convertToProtoFormat(this.mrToken));
/*  66:    */     }
/*  67:    */   }
/*  68:    */   
/*  69:    */   private void mergeLocalToProto()
/*  70:    */   {
/*  71: 86 */     if (this.viaProto) {
/*  72: 87 */       maybeInitBuilder();
/*  73:    */     }
/*  74: 88 */     mergeLocalToBuilder();
/*  75: 89 */     this.proto = this.builder.build();
/*  76: 90 */     this.viaProto = true;
/*  77:    */   }
/*  78:    */   
/*  79:    */   private void maybeInitBuilder()
/*  80:    */   {
/*  81: 94 */     if ((this.viaProto) || (this.builder == null)) {
/*  82: 95 */       this.builder = SecurityProtos.GetDelegationTokenResponseProto.newBuilder(this.proto);
/*  83:    */     }
/*  84: 97 */     this.viaProto = false;
/*  85:    */   }
/*  86:    */   
/*  87:    */   private TokenPBImpl convertFromProtoFormat(SecurityProtos.TokenProto p)
/*  88:    */   {
/*  89:101 */     return new TokenPBImpl(p);
/*  90:    */   }
/*  91:    */   
/*  92:    */   private SecurityProtos.TokenProto convertToProtoFormat(Token t)
/*  93:    */   {
/*  94:105 */     return ((TokenPBImpl)t).getProto();
/*  95:    */   }
/*  96:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-common-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.v2.api.protocolrecords.impl.pb.GetDelegationTokenResponsePBImpl
 * JD-Core Version:    0.7.0.1
 */