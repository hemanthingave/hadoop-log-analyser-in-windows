/*   1:    */ package org.apache.hadoop.mapreduce.v2.api.protocolrecords.impl.pb;
/*   2:    */ 
/*   3:    */ import org.apache.hadoop.mapreduce.v2.api.protocolrecords.GetJobReportResponse;
/*   4:    */ import org.apache.hadoop.mapreduce.v2.api.records.JobReport;
/*   5:    */ import org.apache.hadoop.mapreduce.v2.api.records.impl.pb.JobReportPBImpl;
/*   6:    */ import org.apache.hadoop.mapreduce.v2.proto.MRProtos.JobReportProto;
/*   7:    */ import org.apache.hadoop.mapreduce.v2.proto.MRServiceProtos.GetJobReportResponseProto;
/*   8:    */ import org.apache.hadoop.mapreduce.v2.proto.MRServiceProtos.GetJobReportResponseProto.Builder;
/*   9:    */ import org.apache.hadoop.mapreduce.v2.proto.MRServiceProtos.GetJobReportResponseProtoOrBuilder;
/*  10:    */ import org.apache.hadoop.yarn.api.records.impl.pb.ProtoBase;
/*  11:    */ 
/*  12:    */ public class GetJobReportResponsePBImpl
/*  13:    */   extends ProtoBase<MRServiceProtos.GetJobReportResponseProto>
/*  14:    */   implements GetJobReportResponse
/*  15:    */ {
/*  16: 33 */   MRServiceProtos.GetJobReportResponseProto proto = MRServiceProtos.GetJobReportResponseProto.getDefaultInstance();
/*  17: 34 */   MRServiceProtos.GetJobReportResponseProto.Builder builder = null;
/*  18: 35 */   boolean viaProto = false;
/*  19: 37 */   private JobReport jobReport = null;
/*  20:    */   
/*  21:    */   public GetJobReportResponsePBImpl()
/*  22:    */   {
/*  23: 41 */     this.builder = MRServiceProtos.GetJobReportResponseProto.newBuilder();
/*  24:    */   }
/*  25:    */   
/*  26:    */   public GetJobReportResponsePBImpl(MRServiceProtos.GetJobReportResponseProto proto)
/*  27:    */   {
/*  28: 45 */     this.proto = proto;
/*  29: 46 */     this.viaProto = true;
/*  30:    */   }
/*  31:    */   
/*  32:    */   public MRServiceProtos.GetJobReportResponseProto getProto()
/*  33:    */   {
/*  34: 50 */     mergeLocalToProto();
/*  35: 51 */     this.proto = (this.viaProto ? this.proto : this.builder.build());
/*  36: 52 */     this.viaProto = true;
/*  37: 53 */     return this.proto;
/*  38:    */   }
/*  39:    */   
/*  40:    */   private void mergeLocalToBuilder()
/*  41:    */   {
/*  42: 57 */     if (this.jobReport != null) {
/*  43: 58 */       this.builder.setJobReport(convertToProtoFormat(this.jobReport));
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
/*  60: 72 */       this.builder = MRServiceProtos.GetJobReportResponseProto.newBuilder(this.proto);
/*  61:    */     }
/*  62: 74 */     this.viaProto = false;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public JobReport getJobReport()
/*  66:    */   {
/*  67: 80 */     MRServiceProtos.GetJobReportResponseProtoOrBuilder p = this.viaProto ? this.proto : this.builder;
/*  68: 81 */     if (this.jobReport != null) {
/*  69: 82 */       return this.jobReport;
/*  70:    */     }
/*  71: 84 */     if (!p.hasJobReport()) {
/*  72: 85 */       return null;
/*  73:    */     }
/*  74: 87 */     this.jobReport = convertFromProtoFormat(p.getJobReport());
/*  75: 88 */     return this.jobReport;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public void setJobReport(JobReport jobReport)
/*  79:    */   {
/*  80: 93 */     maybeInitBuilder();
/*  81: 94 */     if (jobReport == null) {
/*  82: 95 */       this.builder.clearJobReport();
/*  83:    */     }
/*  84: 96 */     this.jobReport = jobReport;
/*  85:    */   }
/*  86:    */   
/*  87:    */   private JobReportPBImpl convertFromProtoFormat(MRProtos.JobReportProto p)
/*  88:    */   {
/*  89:100 */     return new JobReportPBImpl(p);
/*  90:    */   }
/*  91:    */   
/*  92:    */   private MRProtos.JobReportProto convertToProtoFormat(JobReport t)
/*  93:    */   {
/*  94:104 */     return ((JobReportPBImpl)t).getProto();
/*  95:    */   }
/*  96:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-common-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.v2.api.protocolrecords.impl.pb.GetJobReportResponsePBImpl
 * JD-Core Version:    0.7.0.1
 */