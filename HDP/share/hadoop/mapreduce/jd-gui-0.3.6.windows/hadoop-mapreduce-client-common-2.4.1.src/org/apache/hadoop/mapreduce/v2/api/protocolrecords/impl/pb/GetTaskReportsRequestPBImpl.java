/*   1:    */ package org.apache.hadoop.mapreduce.v2.api.protocolrecords.impl.pb;
/*   2:    */ 
/*   3:    */ import org.apache.hadoop.mapreduce.v2.api.protocolrecords.GetTaskReportsRequest;
/*   4:    */ import org.apache.hadoop.mapreduce.v2.api.records.JobId;
/*   5:    */ import org.apache.hadoop.mapreduce.v2.api.records.TaskType;
/*   6:    */ import org.apache.hadoop.mapreduce.v2.api.records.impl.pb.JobIdPBImpl;
/*   7:    */ import org.apache.hadoop.mapreduce.v2.proto.MRProtos.JobIdProto;
/*   8:    */ import org.apache.hadoop.mapreduce.v2.proto.MRProtos.TaskTypeProto;
/*   9:    */ import org.apache.hadoop.mapreduce.v2.proto.MRServiceProtos.GetTaskReportsRequestProto;
/*  10:    */ import org.apache.hadoop.mapreduce.v2.proto.MRServiceProtos.GetTaskReportsRequestProto.Builder;
/*  11:    */ import org.apache.hadoop.mapreduce.v2.proto.MRServiceProtos.GetTaskReportsRequestProtoOrBuilder;
/*  12:    */ import org.apache.hadoop.mapreduce.v2.util.MRProtoUtils;
/*  13:    */ import org.apache.hadoop.yarn.api.records.impl.pb.ProtoBase;
/*  14:    */ 
/*  15:    */ public class GetTaskReportsRequestPBImpl
/*  16:    */   extends ProtoBase<MRServiceProtos.GetTaskReportsRequestProto>
/*  17:    */   implements GetTaskReportsRequest
/*  18:    */ {
/*  19: 36 */   MRServiceProtos.GetTaskReportsRequestProto proto = MRServiceProtos.GetTaskReportsRequestProto.getDefaultInstance();
/*  20: 37 */   MRServiceProtos.GetTaskReportsRequestProto.Builder builder = null;
/*  21: 38 */   boolean viaProto = false;
/*  22: 40 */   private JobId jobId = null;
/*  23:    */   
/*  24:    */   public GetTaskReportsRequestPBImpl()
/*  25:    */   {
/*  26: 44 */     this.builder = MRServiceProtos.GetTaskReportsRequestProto.newBuilder();
/*  27:    */   }
/*  28:    */   
/*  29:    */   public GetTaskReportsRequestPBImpl(MRServiceProtos.GetTaskReportsRequestProto proto)
/*  30:    */   {
/*  31: 48 */     this.proto = proto;
/*  32: 49 */     this.viaProto = true;
/*  33:    */   }
/*  34:    */   
/*  35:    */   public MRServiceProtos.GetTaskReportsRequestProto getProto()
/*  36:    */   {
/*  37: 53 */     mergeLocalToProto();
/*  38: 54 */     this.proto = (this.viaProto ? this.proto : this.builder.build());
/*  39: 55 */     this.viaProto = true;
/*  40: 56 */     return this.proto;
/*  41:    */   }
/*  42:    */   
/*  43:    */   private void mergeLocalToBuilder()
/*  44:    */   {
/*  45: 60 */     if (this.jobId != null) {
/*  46: 61 */       this.builder.setJobId(convertToProtoFormat(this.jobId));
/*  47:    */     }
/*  48:    */   }
/*  49:    */   
/*  50:    */   private void mergeLocalToProto()
/*  51:    */   {
/*  52: 66 */     if (this.viaProto) {
/*  53: 67 */       maybeInitBuilder();
/*  54:    */     }
/*  55: 68 */     mergeLocalToBuilder();
/*  56: 69 */     this.proto = this.builder.build();
/*  57: 70 */     this.viaProto = true;
/*  58:    */   }
/*  59:    */   
/*  60:    */   private void maybeInitBuilder()
/*  61:    */   {
/*  62: 74 */     if ((this.viaProto) || (this.builder == null)) {
/*  63: 75 */       this.builder = MRServiceProtos.GetTaskReportsRequestProto.newBuilder(this.proto);
/*  64:    */     }
/*  65: 77 */     this.viaProto = false;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public JobId getJobId()
/*  69:    */   {
/*  70: 83 */     MRServiceProtos.GetTaskReportsRequestProtoOrBuilder p = this.viaProto ? this.proto : this.builder;
/*  71: 84 */     if (this.jobId != null) {
/*  72: 85 */       return this.jobId;
/*  73:    */     }
/*  74: 87 */     if (!p.hasJobId()) {
/*  75: 88 */       return null;
/*  76:    */     }
/*  77: 90 */     this.jobId = convertFromProtoFormat(p.getJobId());
/*  78: 91 */     return this.jobId;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public void setJobId(JobId jobId)
/*  82:    */   {
/*  83: 96 */     maybeInitBuilder();
/*  84: 97 */     if (jobId == null) {
/*  85: 98 */       this.builder.clearJobId();
/*  86:    */     }
/*  87: 99 */     this.jobId = jobId;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public TaskType getTaskType()
/*  91:    */   {
/*  92:103 */     MRServiceProtos.GetTaskReportsRequestProtoOrBuilder p = this.viaProto ? this.proto : this.builder;
/*  93:104 */     if (!p.hasTaskType()) {
/*  94:105 */       return null;
/*  95:    */     }
/*  96:107 */     return convertFromProtoFormat(p.getTaskType());
/*  97:    */   }
/*  98:    */   
/*  99:    */   public void setTaskType(TaskType taskType)
/* 100:    */   {
/* 101:112 */     maybeInitBuilder();
/* 102:113 */     if (taskType == null)
/* 103:    */     {
/* 104:114 */       this.builder.clearTaskType();
/* 105:115 */       return;
/* 106:    */     }
/* 107:117 */     this.builder.setTaskType(convertToProtoFormat(taskType));
/* 108:    */   }
/* 109:    */   
/* 110:    */   private JobIdPBImpl convertFromProtoFormat(MRProtos.JobIdProto p)
/* 111:    */   {
/* 112:121 */     return new JobIdPBImpl(p);
/* 113:    */   }
/* 114:    */   
/* 115:    */   private MRProtos.JobIdProto convertToProtoFormat(JobId t)
/* 116:    */   {
/* 117:125 */     return ((JobIdPBImpl)t).getProto();
/* 118:    */   }
/* 119:    */   
/* 120:    */   private MRProtos.TaskTypeProto convertToProtoFormat(TaskType e)
/* 121:    */   {
/* 122:129 */     return MRProtoUtils.convertToProtoFormat(e);
/* 123:    */   }
/* 124:    */   
/* 125:    */   private TaskType convertFromProtoFormat(MRProtos.TaskTypeProto e)
/* 126:    */   {
/* 127:133 */     return MRProtoUtils.convertFromProtoFormat(e);
/* 128:    */   }
/* 129:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-common-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.v2.api.protocolrecords.impl.pb.GetTaskReportsRequestPBImpl
 * JD-Core Version:    0.7.0.1
 */