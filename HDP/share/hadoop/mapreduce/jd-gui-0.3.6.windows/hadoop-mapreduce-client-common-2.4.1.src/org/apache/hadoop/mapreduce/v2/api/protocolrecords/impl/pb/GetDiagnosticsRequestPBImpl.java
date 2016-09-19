/*   1:    */ package org.apache.hadoop.mapreduce.v2.api.protocolrecords.impl.pb;
/*   2:    */ 
/*   3:    */ import org.apache.hadoop.mapreduce.v2.api.protocolrecords.GetDiagnosticsRequest;
/*   4:    */ import org.apache.hadoop.mapreduce.v2.api.records.TaskAttemptId;
/*   5:    */ import org.apache.hadoop.mapreduce.v2.api.records.impl.pb.TaskAttemptIdPBImpl;
/*   6:    */ import org.apache.hadoop.mapreduce.v2.proto.MRProtos.TaskAttemptIdProto;
/*   7:    */ import org.apache.hadoop.mapreduce.v2.proto.MRServiceProtos.GetDiagnosticsRequestProto;
/*   8:    */ import org.apache.hadoop.mapreduce.v2.proto.MRServiceProtos.GetDiagnosticsRequestProto.Builder;
/*   9:    */ import org.apache.hadoop.mapreduce.v2.proto.MRServiceProtos.GetDiagnosticsRequestProtoOrBuilder;
/*  10:    */ import org.apache.hadoop.yarn.api.records.impl.pb.ProtoBase;
/*  11:    */ 
/*  12:    */ public class GetDiagnosticsRequestPBImpl
/*  13:    */   extends ProtoBase<MRServiceProtos.GetDiagnosticsRequestProto>
/*  14:    */   implements GetDiagnosticsRequest
/*  15:    */ {
/*  16: 33 */   MRServiceProtos.GetDiagnosticsRequestProto proto = MRServiceProtos.GetDiagnosticsRequestProto.getDefaultInstance();
/*  17: 34 */   MRServiceProtos.GetDiagnosticsRequestProto.Builder builder = null;
/*  18: 35 */   boolean viaProto = false;
/*  19: 37 */   private TaskAttemptId taskAttemptId = null;
/*  20:    */   
/*  21:    */   public GetDiagnosticsRequestPBImpl()
/*  22:    */   {
/*  23: 41 */     this.builder = MRServiceProtos.GetDiagnosticsRequestProto.newBuilder();
/*  24:    */   }
/*  25:    */   
/*  26:    */   public GetDiagnosticsRequestPBImpl(MRServiceProtos.GetDiagnosticsRequestProto proto)
/*  27:    */   {
/*  28: 45 */     this.proto = proto;
/*  29: 46 */     this.viaProto = true;
/*  30:    */   }
/*  31:    */   
/*  32:    */   public MRServiceProtos.GetDiagnosticsRequestProto getProto()
/*  33:    */   {
/*  34: 50 */     mergeLocalToProto();
/*  35: 51 */     this.proto = (this.viaProto ? this.proto : this.builder.build());
/*  36: 52 */     this.viaProto = true;
/*  37: 53 */     return this.proto;
/*  38:    */   }
/*  39:    */   
/*  40:    */   private void mergeLocalToBuilder()
/*  41:    */   {
/*  42: 57 */     if (this.taskAttemptId != null) {
/*  43: 58 */       this.builder.setTaskAttemptId(convertToProtoFormat(this.taskAttemptId));
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
/*  60: 72 */       this.builder = MRServiceProtos.GetDiagnosticsRequestProto.newBuilder(this.proto);
/*  61:    */     }
/*  62: 74 */     this.viaProto = false;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public TaskAttemptId getTaskAttemptId()
/*  66:    */   {
/*  67: 80 */     MRServiceProtos.GetDiagnosticsRequestProtoOrBuilder p = this.viaProto ? this.proto : this.builder;
/*  68: 81 */     if (this.taskAttemptId != null) {
/*  69: 82 */       return this.taskAttemptId;
/*  70:    */     }
/*  71: 84 */     if (!p.hasTaskAttemptId()) {
/*  72: 85 */       return null;
/*  73:    */     }
/*  74: 87 */     this.taskAttemptId = convertFromProtoFormat(p.getTaskAttemptId());
/*  75: 88 */     return this.taskAttemptId;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public void setTaskAttemptId(TaskAttemptId taskAttemptId)
/*  79:    */   {
/*  80: 93 */     maybeInitBuilder();
/*  81: 94 */     if (taskAttemptId == null) {
/*  82: 95 */       this.builder.clearTaskAttemptId();
/*  83:    */     }
/*  84: 96 */     this.taskAttemptId = taskAttemptId;
/*  85:    */   }
/*  86:    */   
/*  87:    */   private TaskAttemptIdPBImpl convertFromProtoFormat(MRProtos.TaskAttemptIdProto p)
/*  88:    */   {
/*  89:100 */     return new TaskAttemptIdPBImpl(p);
/*  90:    */   }
/*  91:    */   
/*  92:    */   private MRProtos.TaskAttemptIdProto convertToProtoFormat(TaskAttemptId t)
/*  93:    */   {
/*  94:104 */     return ((TaskAttemptIdPBImpl)t).getProto();
/*  95:    */   }
/*  96:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-common-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.v2.api.protocolrecords.impl.pb.GetDiagnosticsRequestPBImpl
 * JD-Core Version:    0.7.0.1
 */