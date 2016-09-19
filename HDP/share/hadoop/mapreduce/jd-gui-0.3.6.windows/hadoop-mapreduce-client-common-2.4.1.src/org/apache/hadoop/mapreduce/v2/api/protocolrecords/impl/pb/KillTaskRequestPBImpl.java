/*   1:    */ package org.apache.hadoop.mapreduce.v2.api.protocolrecords.impl.pb;
/*   2:    */ 
/*   3:    */ import org.apache.hadoop.mapreduce.v2.api.protocolrecords.KillTaskRequest;
/*   4:    */ import org.apache.hadoop.mapreduce.v2.api.records.TaskId;
/*   5:    */ import org.apache.hadoop.mapreduce.v2.api.records.impl.pb.TaskIdPBImpl;
/*   6:    */ import org.apache.hadoop.mapreduce.v2.proto.MRProtos.TaskIdProto;
/*   7:    */ import org.apache.hadoop.mapreduce.v2.proto.MRServiceProtos.KillTaskRequestProto;
/*   8:    */ import org.apache.hadoop.mapreduce.v2.proto.MRServiceProtos.KillTaskRequestProto.Builder;
/*   9:    */ import org.apache.hadoop.mapreduce.v2.proto.MRServiceProtos.KillTaskRequestProtoOrBuilder;
/*  10:    */ import org.apache.hadoop.yarn.api.records.impl.pb.ProtoBase;
/*  11:    */ 
/*  12:    */ public class KillTaskRequestPBImpl
/*  13:    */   extends ProtoBase<MRServiceProtos.KillTaskRequestProto>
/*  14:    */   implements KillTaskRequest
/*  15:    */ {
/*  16: 33 */   MRServiceProtos.KillTaskRequestProto proto = MRServiceProtos.KillTaskRequestProto.getDefaultInstance();
/*  17: 34 */   MRServiceProtos.KillTaskRequestProto.Builder builder = null;
/*  18: 35 */   boolean viaProto = false;
/*  19: 37 */   private TaskId taskId = null;
/*  20:    */   
/*  21:    */   public KillTaskRequestPBImpl()
/*  22:    */   {
/*  23: 41 */     this.builder = MRServiceProtos.KillTaskRequestProto.newBuilder();
/*  24:    */   }
/*  25:    */   
/*  26:    */   public KillTaskRequestPBImpl(MRServiceProtos.KillTaskRequestProto proto)
/*  27:    */   {
/*  28: 45 */     this.proto = proto;
/*  29: 46 */     this.viaProto = true;
/*  30:    */   }
/*  31:    */   
/*  32:    */   public MRServiceProtos.KillTaskRequestProto getProto()
/*  33:    */   {
/*  34: 50 */     mergeLocalToProto();
/*  35: 51 */     this.proto = (this.viaProto ? this.proto : this.builder.build());
/*  36: 52 */     this.viaProto = true;
/*  37: 53 */     return this.proto;
/*  38:    */   }
/*  39:    */   
/*  40:    */   private void mergeLocalToBuilder()
/*  41:    */   {
/*  42: 57 */     if (this.taskId != null) {
/*  43: 58 */       this.builder.setTaskId(convertToProtoFormat(this.taskId));
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
/*  60: 72 */       this.builder = MRServiceProtos.KillTaskRequestProto.newBuilder(this.proto);
/*  61:    */     }
/*  62: 74 */     this.viaProto = false;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public TaskId getTaskId()
/*  66:    */   {
/*  67: 80 */     MRServiceProtos.KillTaskRequestProtoOrBuilder p = this.viaProto ? this.proto : this.builder;
/*  68: 81 */     if (this.taskId != null) {
/*  69: 82 */       return this.taskId;
/*  70:    */     }
/*  71: 84 */     if (!p.hasTaskId()) {
/*  72: 85 */       return null;
/*  73:    */     }
/*  74: 87 */     this.taskId = convertFromProtoFormat(p.getTaskId());
/*  75: 88 */     return this.taskId;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public void setTaskId(TaskId taskId)
/*  79:    */   {
/*  80: 93 */     maybeInitBuilder();
/*  81: 94 */     if (taskId == null) {
/*  82: 95 */       this.builder.clearTaskId();
/*  83:    */     }
/*  84: 96 */     this.taskId = taskId;
/*  85:    */   }
/*  86:    */   
/*  87:    */   private TaskIdPBImpl convertFromProtoFormat(MRProtos.TaskIdProto p)
/*  88:    */   {
/*  89:100 */     return new TaskIdPBImpl(p);
/*  90:    */   }
/*  91:    */   
/*  92:    */   private MRProtos.TaskIdProto convertToProtoFormat(TaskId t)
/*  93:    */   {
/*  94:104 */     return ((TaskIdPBImpl)t).getProto();
/*  95:    */   }
/*  96:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-common-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.v2.api.protocolrecords.impl.pb.KillTaskRequestPBImpl
 * JD-Core Version:    0.7.0.1
 */