/*  1:   */ package org.apache.hadoop.mapreduce.v2.api.protocolrecords.impl.pb;
/*  2:   */ 
/*  3:   */ import org.apache.hadoop.mapreduce.v2.api.protocolrecords.KillJobResponse;
/*  4:   */ import org.apache.hadoop.mapreduce.v2.proto.MRServiceProtos.KillJobResponseProto;
/*  5:   */ import org.apache.hadoop.mapreduce.v2.proto.MRServiceProtos.KillJobResponseProto.Builder;
/*  6:   */ import org.apache.hadoop.yarn.api.records.impl.pb.ProtoBase;
/*  7:   */ 
/*  8:   */ public class KillJobResponsePBImpl
/*  9:   */   extends ProtoBase<MRServiceProtos.KillJobResponseProto>
/* 10:   */   implements KillJobResponse
/* 11:   */ {
/* 12:29 */   MRServiceProtos.KillJobResponseProto proto = MRServiceProtos.KillJobResponseProto.getDefaultInstance();
/* 13:30 */   MRServiceProtos.KillJobResponseProto.Builder builder = null;
/* 14:31 */   boolean viaProto = false;
/* 15:   */   
/* 16:   */   public KillJobResponsePBImpl()
/* 17:   */   {
/* 18:34 */     this.builder = MRServiceProtos.KillJobResponseProto.newBuilder();
/* 19:   */   }
/* 20:   */   
/* 21:   */   public KillJobResponsePBImpl(MRServiceProtos.KillJobResponseProto proto)
/* 22:   */   {
/* 23:38 */     this.proto = proto;
/* 24:39 */     this.viaProto = true;
/* 25:   */   }
/* 26:   */   
/* 27:   */   public MRServiceProtos.KillJobResponseProto getProto()
/* 28:   */   {
/* 29:43 */     this.proto = (this.viaProto ? this.proto : this.builder.build());
/* 30:44 */     this.viaProto = true;
/* 31:45 */     return this.proto;
/* 32:   */   }
/* 33:   */   
/* 34:   */   private void maybeInitBuilder()
/* 35:   */   {
/* 36:49 */     if ((this.viaProto) || (this.builder == null)) {
/* 37:50 */       this.builder = MRServiceProtos.KillJobResponseProto.newBuilder(this.proto);
/* 38:   */     }
/* 39:52 */     this.viaProto = false;
/* 40:   */   }
/* 41:   */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-common-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.v2.api.protocolrecords.impl.pb.KillJobResponsePBImpl
 * JD-Core Version:    0.7.0.1
 */