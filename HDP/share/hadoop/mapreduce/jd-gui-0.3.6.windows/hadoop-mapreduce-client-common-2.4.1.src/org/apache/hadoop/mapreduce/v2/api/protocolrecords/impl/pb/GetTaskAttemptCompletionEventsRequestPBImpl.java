/*   1:    */ package org.apache.hadoop.mapreduce.v2.api.protocolrecords.impl.pb;
/*   2:    */ 
/*   3:    */ import org.apache.hadoop.mapreduce.v2.api.protocolrecords.GetTaskAttemptCompletionEventsRequest;
/*   4:    */ import org.apache.hadoop.mapreduce.v2.api.records.JobId;
/*   5:    */ import org.apache.hadoop.mapreduce.v2.api.records.impl.pb.JobIdPBImpl;
/*   6:    */ import org.apache.hadoop.mapreduce.v2.proto.MRProtos.JobIdProto;
/*   7:    */ import org.apache.hadoop.mapreduce.v2.proto.MRServiceProtos.GetTaskAttemptCompletionEventsRequestProto;
/*   8:    */ import org.apache.hadoop.mapreduce.v2.proto.MRServiceProtos.GetTaskAttemptCompletionEventsRequestProto.Builder;
/*   9:    */ import org.apache.hadoop.mapreduce.v2.proto.MRServiceProtos.GetTaskAttemptCompletionEventsRequestProtoOrBuilder;
/*  10:    */ import org.apache.hadoop.yarn.api.records.impl.pb.ProtoBase;
/*  11:    */ 
/*  12:    */ public class GetTaskAttemptCompletionEventsRequestPBImpl
/*  13:    */   extends ProtoBase<MRServiceProtos.GetTaskAttemptCompletionEventsRequestProto>
/*  14:    */   implements GetTaskAttemptCompletionEventsRequest
/*  15:    */ {
/*  16: 33 */   MRServiceProtos.GetTaskAttemptCompletionEventsRequestProto proto = MRServiceProtos.GetTaskAttemptCompletionEventsRequestProto.getDefaultInstance();
/*  17: 34 */   MRServiceProtos.GetTaskAttemptCompletionEventsRequestProto.Builder builder = null;
/*  18: 35 */   boolean viaProto = false;
/*  19: 37 */   private JobId jobId = null;
/*  20:    */   
/*  21:    */   public GetTaskAttemptCompletionEventsRequestPBImpl()
/*  22:    */   {
/*  23: 41 */     this.builder = MRServiceProtos.GetTaskAttemptCompletionEventsRequestProto.newBuilder();
/*  24:    */   }
/*  25:    */   
/*  26:    */   public GetTaskAttemptCompletionEventsRequestPBImpl(MRServiceProtos.GetTaskAttemptCompletionEventsRequestProto proto)
/*  27:    */   {
/*  28: 45 */     this.proto = proto;
/*  29: 46 */     this.viaProto = true;
/*  30:    */   }
/*  31:    */   
/*  32:    */   public MRServiceProtos.GetTaskAttemptCompletionEventsRequestProto getProto()
/*  33:    */   {
/*  34: 50 */     mergeLocalToProto();
/*  35: 51 */     this.proto = (this.viaProto ? this.proto : this.builder.build());
/*  36: 52 */     this.viaProto = true;
/*  37: 53 */     return this.proto;
/*  38:    */   }
/*  39:    */   
/*  40:    */   private void mergeLocalToBuilder()
/*  41:    */   {
/*  42: 57 */     if (this.jobId != null) {
/*  43: 58 */       this.builder.setJobId(convertToProtoFormat(this.jobId));
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
/*  60: 72 */       this.builder = MRServiceProtos.GetTaskAttemptCompletionEventsRequestProto.newBuilder(this.proto);
/*  61:    */     }
/*  62: 74 */     this.viaProto = false;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public JobId getJobId()
/*  66:    */   {
/*  67: 80 */     MRServiceProtos.GetTaskAttemptCompletionEventsRequestProtoOrBuilder p = this.viaProto ? this.proto : this.builder;
/*  68: 81 */     if (this.jobId != null) {
/*  69: 82 */       return this.jobId;
/*  70:    */     }
/*  71: 84 */     if (!p.hasJobId()) {
/*  72: 85 */       return null;
/*  73:    */     }
/*  74: 87 */     this.jobId = convertFromProtoFormat(p.getJobId());
/*  75: 88 */     return this.jobId;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public void setJobId(JobId jobId)
/*  79:    */   {
/*  80: 93 */     maybeInitBuilder();
/*  81: 94 */     if (jobId == null) {
/*  82: 95 */       this.builder.clearJobId();
/*  83:    */     }
/*  84: 96 */     this.jobId = jobId;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public int getFromEventId()
/*  88:    */   {
/*  89:100 */     MRServiceProtos.GetTaskAttemptCompletionEventsRequestProtoOrBuilder p = this.viaProto ? this.proto : this.builder;
/*  90:101 */     return p.getFromEventId();
/*  91:    */   }
/*  92:    */   
/*  93:    */   public void setFromEventId(int fromEventId)
/*  94:    */   {
/*  95:106 */     maybeInitBuilder();
/*  96:107 */     this.builder.setFromEventId(fromEventId);
/*  97:    */   }
/*  98:    */   
/*  99:    */   public int getMaxEvents()
/* 100:    */   {
/* 101:111 */     MRServiceProtos.GetTaskAttemptCompletionEventsRequestProtoOrBuilder p = this.viaProto ? this.proto : this.builder;
/* 102:112 */     return p.getMaxEvents();
/* 103:    */   }
/* 104:    */   
/* 105:    */   public void setMaxEvents(int maxEvents)
/* 106:    */   {
/* 107:117 */     maybeInitBuilder();
/* 108:118 */     this.builder.setMaxEvents(maxEvents);
/* 109:    */   }
/* 110:    */   
/* 111:    */   private JobIdPBImpl convertFromProtoFormat(MRProtos.JobIdProto p)
/* 112:    */   {
/* 113:122 */     return new JobIdPBImpl(p);
/* 114:    */   }
/* 115:    */   
/* 116:    */   private MRProtos.JobIdProto convertToProtoFormat(JobId t)
/* 117:    */   {
/* 118:126 */     return ((JobIdPBImpl)t).getProto();
/* 119:    */   }
/* 120:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-common-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.v2.api.protocolrecords.impl.pb.GetTaskAttemptCompletionEventsRequestPBImpl
 * JD-Core Version:    0.7.0.1
 */