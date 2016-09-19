/*  1:   */ package org.apache.hadoop.mapreduce.v2.api.protocolrecords.impl.pb;
/*  2:   */ 
/*  3:   */ import org.apache.hadoop.mapreduce.v2.api.protocolrecords.KillTaskResponse;
/*  4:   */ import org.apache.hadoop.mapreduce.v2.proto.MRServiceProtos.KillTaskResponseProto;
/*  5:   */ import org.apache.hadoop.mapreduce.v2.proto.MRServiceProtos.KillTaskResponseProto.Builder;
/*  6:   */ import org.apache.hadoop.yarn.api.records.impl.pb.ProtoBase;
/*  7:   */ 
/*  8:   */ public class KillTaskResponsePBImpl
/*  9:   */   extends ProtoBase<MRServiceProtos.KillTaskResponseProto>
/* 10:   */   implements KillTaskResponse
/* 11:   */ {
/* 12:29 */   MRServiceProtos.KillTaskResponseProto proto = MRServiceProtos.KillTaskResponseProto.getDefaultInstance();
/* 13:30 */   MRServiceProtos.KillTaskResponseProto.Builder builder = null;
/* 14:31 */   boolean viaProto = false;
/* 15:   */   
/* 16:   */   public KillTaskResponsePBImpl()
/* 17:   */   {
/* 18:34 */     this.builder = MRServiceProtos.KillTaskResponseProto.newBuilder();
/* 19:   */   }
/* 20:   */   
/* 21:   */   public KillTaskResponsePBImpl(MRServiceProtos.KillTaskResponseProto proto)
/* 22:   */   {
/* 23:38 */     this.proto = proto;
/* 24:39 */     this.viaProto = true;
/* 25:   */   }
/* 26:   */   
/* 27:   */   public MRServiceProtos.KillTaskResponseProto getProto()
/* 28:   */   {
/* 29:43 */     this.proto = (this.viaProto ? this.proto : this.builder.build());
/* 30:44 */     this.viaProto = true;
/* 31:45 */     return this.proto;
/* 32:   */   }
/* 33:   */   
/* 34:   */   private void maybeInitBuilder()
/* 35:   */   {
/* 36:49 */     if ((this.viaProto) || (this.builder == null)) {
/* 37:50 */       this.builder = MRServiceProtos.KillTaskResponseProto.newBuilder(this.proto);
/* 38:   */     }
/* 39:52 */     this.viaProto = false;
/* 40:   */   }
/* 41:   */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-common-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.v2.api.protocolrecords.impl.pb.KillTaskResponsePBImpl
 * JD-Core Version:    0.7.0.1
 */