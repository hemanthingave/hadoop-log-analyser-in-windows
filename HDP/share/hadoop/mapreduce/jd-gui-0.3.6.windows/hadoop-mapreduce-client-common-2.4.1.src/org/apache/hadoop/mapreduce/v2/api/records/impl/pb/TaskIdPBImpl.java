/*   1:    */ package org.apache.hadoop.mapreduce.v2.api.records.impl.pb;
/*   2:    */ 
/*   3:    */ import org.apache.hadoop.mapreduce.v2.api.records.JobId;
/*   4:    */ import org.apache.hadoop.mapreduce.v2.api.records.TaskId;
/*   5:    */ import org.apache.hadoop.mapreduce.v2.api.records.TaskType;
/*   6:    */ import org.apache.hadoop.mapreduce.v2.proto.MRProtos.JobIdProto;
/*   7:    */ import org.apache.hadoop.mapreduce.v2.proto.MRProtos.TaskIdProto;
/*   8:    */ import org.apache.hadoop.mapreduce.v2.proto.MRProtos.TaskIdProto.Builder;
/*   9:    */ import org.apache.hadoop.mapreduce.v2.proto.MRProtos.TaskIdProtoOrBuilder;
/*  10:    */ import org.apache.hadoop.mapreduce.v2.proto.MRProtos.TaskTypeProto;
/*  11:    */ import org.apache.hadoop.mapreduce.v2.util.MRProtoUtils;
/*  12:    */ 
/*  13:    */ public class TaskIdPBImpl
/*  14:    */   extends TaskId
/*  15:    */ {
/*  16: 31 */   MRProtos.TaskIdProto proto = MRProtos.TaskIdProto.getDefaultInstance();
/*  17: 32 */   MRProtos.TaskIdProto.Builder builder = null;
/*  18: 33 */   boolean viaProto = false;
/*  19: 35 */   private JobId jobId = null;
/*  20:    */   
/*  21:    */   public TaskIdPBImpl()
/*  22:    */   {
/*  23: 38 */     this.builder = MRProtos.TaskIdProto.newBuilder(this.proto);
/*  24:    */   }
/*  25:    */   
/*  26:    */   public TaskIdPBImpl(MRProtos.TaskIdProto proto)
/*  27:    */   {
/*  28: 42 */     this.proto = proto;
/*  29: 43 */     this.viaProto = true;
/*  30:    */   }
/*  31:    */   
/*  32:    */   public synchronized MRProtos.TaskIdProto getProto()
/*  33:    */   {
/*  34: 47 */     mergeLocalToProto();
/*  35: 48 */     this.proto = (this.viaProto ? this.proto : this.builder.build());
/*  36: 49 */     this.viaProto = true;
/*  37: 50 */     return this.proto;
/*  38:    */   }
/*  39:    */   
/*  40:    */   private synchronized void mergeLocalToBuilder()
/*  41:    */   {
/*  42: 54 */     if ((this.jobId != null) && (!((JobIdPBImpl)this.jobId).getProto().equals(this.builder.getJobId()))) {
/*  43: 56 */       this.builder.setJobId(convertToProtoFormat(this.jobId));
/*  44:    */     }
/*  45:    */   }
/*  46:    */   
/*  47:    */   private synchronized void mergeLocalToProto()
/*  48:    */   {
/*  49: 61 */     if (this.viaProto) {
/*  50: 62 */       maybeInitBuilder();
/*  51:    */     }
/*  52: 63 */     mergeLocalToBuilder();
/*  53: 64 */     this.proto = this.builder.build();
/*  54: 65 */     this.viaProto = true;
/*  55:    */   }
/*  56:    */   
/*  57:    */   private synchronized void maybeInitBuilder()
/*  58:    */   {
/*  59: 69 */     if ((this.viaProto) || (this.builder == null)) {
/*  60: 70 */       this.builder = MRProtos.TaskIdProto.newBuilder(this.proto);
/*  61:    */     }
/*  62: 72 */     this.viaProto = false;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public synchronized int getId()
/*  66:    */   {
/*  67: 77 */     MRProtos.TaskIdProtoOrBuilder p = this.viaProto ? this.proto : this.builder;
/*  68: 78 */     return p.getId();
/*  69:    */   }
/*  70:    */   
/*  71:    */   public synchronized void setId(int id)
/*  72:    */   {
/*  73: 83 */     maybeInitBuilder();
/*  74: 84 */     this.builder.setId(id);
/*  75:    */   }
/*  76:    */   
/*  77:    */   public synchronized JobId getJobId()
/*  78:    */   {
/*  79: 89 */     MRProtos.TaskIdProtoOrBuilder p = this.viaProto ? this.proto : this.builder;
/*  80: 90 */     if (this.jobId != null) {
/*  81: 91 */       return this.jobId;
/*  82:    */     }
/*  83: 93 */     if (!p.hasJobId()) {
/*  84: 94 */       return null;
/*  85:    */     }
/*  86: 96 */     this.jobId = convertFromProtoFormat(p.getJobId());
/*  87: 97 */     return this.jobId;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public synchronized void setJobId(JobId jobId)
/*  91:    */   {
/*  92:102 */     maybeInitBuilder();
/*  93:103 */     if (jobId == null) {
/*  94:104 */       this.builder.clearJobId();
/*  95:    */     }
/*  96:105 */     this.jobId = jobId;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public synchronized TaskType getTaskType()
/* 100:    */   {
/* 101:110 */     MRProtos.TaskIdProtoOrBuilder p = this.viaProto ? this.proto : this.builder;
/* 102:111 */     if (!p.hasTaskType()) {
/* 103:112 */       return null;
/* 104:    */     }
/* 105:114 */     return convertFromProtoFormat(p.getTaskType());
/* 106:    */   }
/* 107:    */   
/* 108:    */   public synchronized void setTaskType(TaskType taskType)
/* 109:    */   {
/* 110:119 */     maybeInitBuilder();
/* 111:120 */     if (taskType == null)
/* 112:    */     {
/* 113:121 */       this.builder.clearTaskType();
/* 114:122 */       return;
/* 115:    */     }
/* 116:124 */     this.builder.setTaskType(convertToProtoFormat(taskType));
/* 117:    */   }
/* 118:    */   
/* 119:    */   private JobIdPBImpl convertFromProtoFormat(MRProtos.JobIdProto p)
/* 120:    */   {
/* 121:128 */     return new JobIdPBImpl(p);
/* 122:    */   }
/* 123:    */   
/* 124:    */   private MRProtos.JobIdProto convertToProtoFormat(JobId t)
/* 125:    */   {
/* 126:132 */     return ((JobIdPBImpl)t).getProto();
/* 127:    */   }
/* 128:    */   
/* 129:    */   private MRProtos.TaskTypeProto convertToProtoFormat(TaskType e)
/* 130:    */   {
/* 131:136 */     return MRProtoUtils.convertToProtoFormat(e);
/* 132:    */   }
/* 133:    */   
/* 134:    */   private TaskType convertFromProtoFormat(MRProtos.TaskTypeProto e)
/* 135:    */   {
/* 136:140 */     return MRProtoUtils.convertFromProtoFormat(e);
/* 137:    */   }
/* 138:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-common-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.v2.api.records.impl.pb.TaskIdPBImpl
 * JD-Core Version:    0.7.0.1
 */