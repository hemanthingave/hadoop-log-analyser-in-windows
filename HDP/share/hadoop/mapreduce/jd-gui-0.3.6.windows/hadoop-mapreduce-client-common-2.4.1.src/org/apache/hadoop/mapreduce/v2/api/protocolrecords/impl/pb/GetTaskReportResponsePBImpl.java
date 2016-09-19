/*   1:    */ package org.apache.hadoop.mapreduce.v2.api.protocolrecords.impl.pb;
/*   2:    */ 
/*   3:    */ import org.apache.hadoop.mapreduce.v2.api.protocolrecords.GetTaskReportResponse;
/*   4:    */ import org.apache.hadoop.mapreduce.v2.api.records.TaskReport;
/*   5:    */ import org.apache.hadoop.mapreduce.v2.api.records.impl.pb.TaskReportPBImpl;
/*   6:    */ import org.apache.hadoop.mapreduce.v2.proto.MRProtos.TaskReportProto;
/*   7:    */ import org.apache.hadoop.mapreduce.v2.proto.MRServiceProtos.GetTaskReportResponseProto;
/*   8:    */ import org.apache.hadoop.mapreduce.v2.proto.MRServiceProtos.GetTaskReportResponseProto.Builder;
/*   9:    */ import org.apache.hadoop.mapreduce.v2.proto.MRServiceProtos.GetTaskReportResponseProtoOrBuilder;
/*  10:    */ import org.apache.hadoop.yarn.api.records.impl.pb.ProtoBase;
/*  11:    */ 
/*  12:    */ public class GetTaskReportResponsePBImpl
/*  13:    */   extends ProtoBase<MRServiceProtos.GetTaskReportResponseProto>
/*  14:    */   implements GetTaskReportResponse
/*  15:    */ {
/*  16: 33 */   MRServiceProtos.GetTaskReportResponseProto proto = MRServiceProtos.GetTaskReportResponseProto.getDefaultInstance();
/*  17: 34 */   MRServiceProtos.GetTaskReportResponseProto.Builder builder = null;
/*  18: 35 */   boolean viaProto = false;
/*  19: 37 */   private TaskReport taskReport = null;
/*  20:    */   
/*  21:    */   public GetTaskReportResponsePBImpl()
/*  22:    */   {
/*  23: 41 */     this.builder = MRServiceProtos.GetTaskReportResponseProto.newBuilder();
/*  24:    */   }
/*  25:    */   
/*  26:    */   public GetTaskReportResponsePBImpl(MRServiceProtos.GetTaskReportResponseProto proto)
/*  27:    */   {
/*  28: 45 */     this.proto = proto;
/*  29: 46 */     this.viaProto = true;
/*  30:    */   }
/*  31:    */   
/*  32:    */   public MRServiceProtos.GetTaskReportResponseProto getProto()
/*  33:    */   {
/*  34: 50 */     mergeLocalToProto();
/*  35: 51 */     this.proto = (this.viaProto ? this.proto : this.builder.build());
/*  36: 52 */     this.viaProto = true;
/*  37: 53 */     return this.proto;
/*  38:    */   }
/*  39:    */   
/*  40:    */   private void mergeLocalToBuilder()
/*  41:    */   {
/*  42: 57 */     if (this.taskReport != null) {
/*  43: 58 */       this.builder.setTaskReport(convertToProtoFormat(this.taskReport));
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
/*  60: 72 */       this.builder = MRServiceProtos.GetTaskReportResponseProto.newBuilder(this.proto);
/*  61:    */     }
/*  62: 74 */     this.viaProto = false;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public TaskReport getTaskReport()
/*  66:    */   {
/*  67: 80 */     MRServiceProtos.GetTaskReportResponseProtoOrBuilder p = this.viaProto ? this.proto : this.builder;
/*  68: 81 */     if (this.taskReport != null) {
/*  69: 82 */       return this.taskReport;
/*  70:    */     }
/*  71: 84 */     if (!p.hasTaskReport()) {
/*  72: 85 */       return null;
/*  73:    */     }
/*  74: 87 */     this.taskReport = convertFromProtoFormat(p.getTaskReport());
/*  75: 88 */     return this.taskReport;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public void setTaskReport(TaskReport taskReport)
/*  79:    */   {
/*  80: 93 */     maybeInitBuilder();
/*  81: 94 */     if (taskReport == null) {
/*  82: 95 */       this.builder.clearTaskReport();
/*  83:    */     }
/*  84: 96 */     this.taskReport = taskReport;
/*  85:    */   }
/*  86:    */   
/*  87:    */   private TaskReportPBImpl convertFromProtoFormat(MRProtos.TaskReportProto p)
/*  88:    */   {
/*  89:100 */     return new TaskReportPBImpl(p);
/*  90:    */   }
/*  91:    */   
/*  92:    */   private MRProtos.TaskReportProto convertToProtoFormat(TaskReport t)
/*  93:    */   {
/*  94:104 */     return ((TaskReportPBImpl)t).getProto();
/*  95:    */   }
/*  96:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-common-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.v2.api.protocolrecords.impl.pb.GetTaskReportResponsePBImpl
 * JD-Core Version:    0.7.0.1
 */