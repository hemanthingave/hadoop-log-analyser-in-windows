/*   1:    */ package org.apache.hadoop.mapreduce.v2.api.protocolrecords.impl.pb;
/*   2:    */ 
/*   3:    */ import org.apache.hadoop.mapreduce.v2.api.protocolrecords.GetTaskAttemptReportResponse;
/*   4:    */ import org.apache.hadoop.mapreduce.v2.api.records.TaskAttemptReport;
/*   5:    */ import org.apache.hadoop.mapreduce.v2.api.records.impl.pb.TaskAttemptReportPBImpl;
/*   6:    */ import org.apache.hadoop.mapreduce.v2.proto.MRProtos.TaskAttemptReportProto;
/*   7:    */ import org.apache.hadoop.mapreduce.v2.proto.MRServiceProtos.GetTaskAttemptReportResponseProto;
/*   8:    */ import org.apache.hadoop.mapreduce.v2.proto.MRServiceProtos.GetTaskAttemptReportResponseProto.Builder;
/*   9:    */ import org.apache.hadoop.mapreduce.v2.proto.MRServiceProtos.GetTaskAttemptReportResponseProtoOrBuilder;
/*  10:    */ import org.apache.hadoop.yarn.api.records.impl.pb.ProtoBase;
/*  11:    */ 
/*  12:    */ public class GetTaskAttemptReportResponsePBImpl
/*  13:    */   extends ProtoBase<MRServiceProtos.GetTaskAttemptReportResponseProto>
/*  14:    */   implements GetTaskAttemptReportResponse
/*  15:    */ {
/*  16: 33 */   MRServiceProtos.GetTaskAttemptReportResponseProto proto = MRServiceProtos.GetTaskAttemptReportResponseProto.getDefaultInstance();
/*  17: 34 */   MRServiceProtos.GetTaskAttemptReportResponseProto.Builder builder = null;
/*  18: 35 */   boolean viaProto = false;
/*  19: 37 */   private TaskAttemptReport taskAttemptReport = null;
/*  20:    */   
/*  21:    */   public GetTaskAttemptReportResponsePBImpl()
/*  22:    */   {
/*  23: 41 */     this.builder = MRServiceProtos.GetTaskAttemptReportResponseProto.newBuilder();
/*  24:    */   }
/*  25:    */   
/*  26:    */   public GetTaskAttemptReportResponsePBImpl(MRServiceProtos.GetTaskAttemptReportResponseProto proto)
/*  27:    */   {
/*  28: 45 */     this.proto = proto;
/*  29: 46 */     this.viaProto = true;
/*  30:    */   }
/*  31:    */   
/*  32:    */   public MRServiceProtos.GetTaskAttemptReportResponseProto getProto()
/*  33:    */   {
/*  34: 50 */     mergeLocalToProto();
/*  35: 51 */     this.proto = (this.viaProto ? this.proto : this.builder.build());
/*  36: 52 */     this.viaProto = true;
/*  37: 53 */     return this.proto;
/*  38:    */   }
/*  39:    */   
/*  40:    */   private void mergeLocalToBuilder()
/*  41:    */   {
/*  42: 57 */     if (this.taskAttemptReport != null) {
/*  43: 58 */       this.builder.setTaskAttemptReport(convertToProtoFormat(this.taskAttemptReport));
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
/*  60: 72 */       this.builder = MRServiceProtos.GetTaskAttemptReportResponseProto.newBuilder(this.proto);
/*  61:    */     }
/*  62: 74 */     this.viaProto = false;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public TaskAttemptReport getTaskAttemptReport()
/*  66:    */   {
/*  67: 80 */     MRServiceProtos.GetTaskAttemptReportResponseProtoOrBuilder p = this.viaProto ? this.proto : this.builder;
/*  68: 81 */     if (this.taskAttemptReport != null) {
/*  69: 82 */       return this.taskAttemptReport;
/*  70:    */     }
/*  71: 84 */     if (!p.hasTaskAttemptReport()) {
/*  72: 85 */       return null;
/*  73:    */     }
/*  74: 87 */     this.taskAttemptReport = convertFromProtoFormat(p.getTaskAttemptReport());
/*  75: 88 */     return this.taskAttemptReport;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public void setTaskAttemptReport(TaskAttemptReport taskAttemptReport)
/*  79:    */   {
/*  80: 93 */     maybeInitBuilder();
/*  81: 94 */     if (taskAttemptReport == null) {
/*  82: 95 */       this.builder.clearTaskAttemptReport();
/*  83:    */     }
/*  84: 96 */     this.taskAttemptReport = taskAttemptReport;
/*  85:    */   }
/*  86:    */   
/*  87:    */   private TaskAttemptReportPBImpl convertFromProtoFormat(MRProtos.TaskAttemptReportProto p)
/*  88:    */   {
/*  89:100 */     return new TaskAttemptReportPBImpl(p);
/*  90:    */   }
/*  91:    */   
/*  92:    */   private MRProtos.TaskAttemptReportProto convertToProtoFormat(TaskAttemptReport t)
/*  93:    */   {
/*  94:104 */     return ((TaskAttemptReportPBImpl)t).getProto();
/*  95:    */   }
/*  96:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-common-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.v2.api.protocolrecords.impl.pb.GetTaskAttemptReportResponsePBImpl
 * JD-Core Version:    0.7.0.1
 */