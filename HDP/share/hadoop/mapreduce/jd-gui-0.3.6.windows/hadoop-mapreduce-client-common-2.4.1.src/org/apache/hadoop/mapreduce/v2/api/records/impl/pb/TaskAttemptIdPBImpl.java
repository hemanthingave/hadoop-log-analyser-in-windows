/*   1:    */ package org.apache.hadoop.mapreduce.v2.api.records.impl.pb;
/*   2:    */ 
/*   3:    */ import org.apache.hadoop.mapreduce.v2.api.records.TaskAttemptId;
/*   4:    */ import org.apache.hadoop.mapreduce.v2.api.records.TaskId;
/*   5:    */ import org.apache.hadoop.mapreduce.v2.proto.MRProtos.TaskAttemptIdProto;
/*   6:    */ import org.apache.hadoop.mapreduce.v2.proto.MRProtos.TaskAttemptIdProto.Builder;
/*   7:    */ import org.apache.hadoop.mapreduce.v2.proto.MRProtos.TaskAttemptIdProtoOrBuilder;
/*   8:    */ import org.apache.hadoop.mapreduce.v2.proto.MRProtos.TaskIdProto;
/*   9:    */ 
/*  10:    */ public class TaskAttemptIdPBImpl
/*  11:    */   extends TaskAttemptId
/*  12:    */ {
/*  13: 28 */   MRProtos.TaskAttemptIdProto proto = MRProtos.TaskAttemptIdProto.getDefaultInstance();
/*  14: 29 */   MRProtos.TaskAttemptIdProto.Builder builder = null;
/*  15: 30 */   boolean viaProto = false;
/*  16: 32 */   private TaskId taskId = null;
/*  17:    */   
/*  18:    */   public TaskAttemptIdPBImpl()
/*  19:    */   {
/*  20: 37 */     this.builder = MRProtos.TaskAttemptIdProto.newBuilder();
/*  21:    */   }
/*  22:    */   
/*  23:    */   public TaskAttemptIdPBImpl(MRProtos.TaskAttemptIdProto proto)
/*  24:    */   {
/*  25: 41 */     this.proto = proto;
/*  26: 42 */     this.viaProto = true;
/*  27:    */   }
/*  28:    */   
/*  29:    */   public synchronized MRProtos.TaskAttemptIdProto getProto()
/*  30:    */   {
/*  31: 46 */     mergeLocalToProto();
/*  32: 47 */     this.proto = (this.viaProto ? this.proto : this.builder.build());
/*  33: 48 */     this.viaProto = true;
/*  34: 49 */     return this.proto;
/*  35:    */   }
/*  36:    */   
/*  37:    */   private synchronized void mergeLocalToBuilder()
/*  38:    */   {
/*  39: 53 */     if ((this.taskId != null) && (!((TaskIdPBImpl)this.taskId).getProto().equals(this.builder.getTaskId()))) {
/*  40: 55 */       this.builder.setTaskId(convertToProtoFormat(this.taskId));
/*  41:    */     }
/*  42:    */   }
/*  43:    */   
/*  44:    */   private synchronized void mergeLocalToProto()
/*  45:    */   {
/*  46: 60 */     if (this.viaProto) {
/*  47: 61 */       maybeInitBuilder();
/*  48:    */     }
/*  49: 62 */     mergeLocalToBuilder();
/*  50: 63 */     this.proto = this.builder.build();
/*  51: 64 */     this.viaProto = true;
/*  52:    */   }
/*  53:    */   
/*  54:    */   private synchronized void maybeInitBuilder()
/*  55:    */   {
/*  56: 68 */     if ((this.viaProto) || (this.builder == null)) {
/*  57: 69 */       this.builder = MRProtos.TaskAttemptIdProto.newBuilder(this.proto);
/*  58:    */     }
/*  59: 71 */     this.viaProto = false;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public synchronized int getId()
/*  63:    */   {
/*  64: 77 */     MRProtos.TaskAttemptIdProtoOrBuilder p = this.viaProto ? this.proto : this.builder;
/*  65: 78 */     return p.getId();
/*  66:    */   }
/*  67:    */   
/*  68:    */   public synchronized void setId(int id)
/*  69:    */   {
/*  70: 83 */     maybeInitBuilder();
/*  71: 84 */     this.builder.setId(id);
/*  72:    */   }
/*  73:    */   
/*  74:    */   public synchronized TaskId getTaskId()
/*  75:    */   {
/*  76: 88 */     MRProtos.TaskAttemptIdProtoOrBuilder p = this.viaProto ? this.proto : this.builder;
/*  77: 89 */     if (this.taskId != null) {
/*  78: 90 */       return this.taskId;
/*  79:    */     }
/*  80: 92 */     if (!p.hasTaskId()) {
/*  81: 93 */       return null;
/*  82:    */     }
/*  83: 95 */     this.taskId = convertFromProtoFormat(p.getTaskId());
/*  84: 96 */     return this.taskId;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public synchronized void setTaskId(TaskId taskId)
/*  88:    */   {
/*  89:101 */     maybeInitBuilder();
/*  90:102 */     if (taskId == null) {
/*  91:103 */       this.builder.clearTaskId();
/*  92:    */     }
/*  93:104 */     this.taskId = taskId;
/*  94:    */   }
/*  95:    */   
/*  96:    */   private TaskIdPBImpl convertFromProtoFormat(MRProtos.TaskIdProto p)
/*  97:    */   {
/*  98:108 */     return new TaskIdPBImpl(p);
/*  99:    */   }
/* 100:    */   
/* 101:    */   private MRProtos.TaskIdProto convertToProtoFormat(TaskId t)
/* 102:    */   {
/* 103:112 */     return ((TaskIdPBImpl)t).getProto();
/* 104:    */   }
/* 105:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-common-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.v2.api.records.impl.pb.TaskAttemptIdPBImpl
 * JD-Core Version:    0.7.0.1
 */