/*  1:   */ package org.apache.hadoop.mapreduce.v2.api.protocolrecords.impl.pb;
/*  2:   */ 
/*  3:   */ import org.apache.hadoop.mapreduce.v2.api.protocolrecords.RenewDelegationTokenResponse;
/*  4:   */ import org.apache.hadoop.security.proto.SecurityProtos.RenewDelegationTokenResponseProto;
/*  5:   */ import org.apache.hadoop.security.proto.SecurityProtos.RenewDelegationTokenResponseProto.Builder;
/*  6:   */ import org.apache.hadoop.security.proto.SecurityProtos.RenewDelegationTokenResponseProtoOrBuilder;
/*  7:   */ import org.apache.hadoop.yarn.api.records.impl.pb.ProtoBase;
/*  8:   */ 
/*  9:   */ public class RenewDelegationTokenResponsePBImpl
/* 10:   */   extends ProtoBase<SecurityProtos.RenewDelegationTokenResponseProto>
/* 11:   */   implements RenewDelegationTokenResponse
/* 12:   */ {
/* 13:30 */   SecurityProtos.RenewDelegationTokenResponseProto proto = SecurityProtos.RenewDelegationTokenResponseProto.getDefaultInstance();
/* 14:32 */   SecurityProtos.RenewDelegationTokenResponseProto.Builder builder = null;
/* 15:33 */   boolean viaProto = false;
/* 16:   */   
/* 17:   */   public RenewDelegationTokenResponsePBImpl()
/* 18:   */   {
/* 19:36 */     this.builder = SecurityProtos.RenewDelegationTokenResponseProto.newBuilder();
/* 20:   */   }
/* 21:   */   
/* 22:   */   public RenewDelegationTokenResponsePBImpl(SecurityProtos.RenewDelegationTokenResponseProto proto)
/* 23:   */   {
/* 24:41 */     this.proto = proto;
/* 25:42 */     this.viaProto = true;
/* 26:   */   }
/* 27:   */   
/* 28:   */   public SecurityProtos.RenewDelegationTokenResponseProto getProto()
/* 29:   */   {
/* 30:47 */     this.proto = (this.viaProto ? this.proto : this.builder.build());
/* 31:48 */     this.viaProto = true;
/* 32:49 */     return this.proto;
/* 33:   */   }
/* 34:   */   
/* 35:   */   private void maybeInitBuilder()
/* 36:   */   {
/* 37:53 */     if ((this.viaProto) || (this.builder == null)) {
/* 38:54 */       this.builder = SecurityProtos.RenewDelegationTokenResponseProto.newBuilder(this.proto);
/* 39:   */     }
/* 40:56 */     this.viaProto = false;
/* 41:   */   }
/* 42:   */   
/* 43:   */   public long getNextExpirationTime()
/* 44:   */   {
/* 45:61 */     SecurityProtos.RenewDelegationTokenResponseProtoOrBuilder p = this.viaProto ? this.proto : this.builder;
/* 46:62 */     return p.getNewExpiryTime();
/* 47:   */   }
/* 48:   */   
/* 49:   */   public void setNextExpirationTime(long expTime)
/* 50:   */   {
/* 51:67 */     maybeInitBuilder();
/* 52:68 */     this.builder.setNewExpiryTime(expTime);
/* 53:   */   }
/* 54:   */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-common-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.v2.api.protocolrecords.impl.pb.RenewDelegationTokenResponsePBImpl
 * JD-Core Version:    0.7.0.1
 */