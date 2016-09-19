/*  1:   */ package org.apache.hadoop.mapreduce.v2.api.protocolrecords.impl.pb;
/*  2:   */ 
/*  3:   */ import org.apache.hadoop.mapreduce.v2.api.protocolrecords.CancelDelegationTokenResponse;
/*  4:   */ import org.apache.hadoop.security.proto.SecurityProtos.CancelDelegationTokenResponseProto;
/*  5:   */ import org.apache.hadoop.yarn.api.records.impl.pb.ProtoBase;
/*  6:   */ 
/*  7:   */ public class CancelDelegationTokenResponsePBImpl
/*  8:   */   extends ProtoBase<SecurityProtos.CancelDelegationTokenResponseProto>
/*  9:   */   implements CancelDelegationTokenResponse
/* 10:   */ {
/* 11:28 */   SecurityProtos.CancelDelegationTokenResponseProto proto = SecurityProtos.CancelDelegationTokenResponseProto.getDefaultInstance();
/* 12:   */   
/* 13:   */   public CancelDelegationTokenResponsePBImpl() {}
/* 14:   */   
/* 15:   */   public CancelDelegationTokenResponsePBImpl(SecurityProtos.CancelDelegationTokenResponseProto proto)
/* 16:   */   {
/* 17:36 */     this.proto = proto;
/* 18:   */   }
/* 19:   */   
/* 20:   */   public SecurityProtos.CancelDelegationTokenResponseProto getProto()
/* 21:   */   {
/* 22:41 */     return this.proto;
/* 23:   */   }
/* 24:   */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-common-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.v2.api.protocolrecords.impl.pb.CancelDelegationTokenResponsePBImpl
 * JD-Core Version:    0.7.0.1
 */