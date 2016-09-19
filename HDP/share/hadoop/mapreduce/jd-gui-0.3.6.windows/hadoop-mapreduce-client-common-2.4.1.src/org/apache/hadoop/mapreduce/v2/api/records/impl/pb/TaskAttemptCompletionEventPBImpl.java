/*   1:    */ package org.apache.hadoop.mapreduce.v2.api.records.impl.pb;
/*   2:    */ 
/*   3:    */ import org.apache.hadoop.mapreduce.v2.api.records.TaskAttemptCompletionEvent;
/*   4:    */ import org.apache.hadoop.mapreduce.v2.api.records.TaskAttemptCompletionEventStatus;
/*   5:    */ import org.apache.hadoop.mapreduce.v2.api.records.TaskAttemptId;
/*   6:    */ import org.apache.hadoop.mapreduce.v2.proto.MRProtos.TaskAttemptCompletionEventProto;
/*   7:    */ import org.apache.hadoop.mapreduce.v2.proto.MRProtos.TaskAttemptCompletionEventProto.Builder;
/*   8:    */ import org.apache.hadoop.mapreduce.v2.proto.MRProtos.TaskAttemptCompletionEventProtoOrBuilder;
/*   9:    */ import org.apache.hadoop.mapreduce.v2.proto.MRProtos.TaskAttemptCompletionEventStatusProto;
/*  10:    */ import org.apache.hadoop.mapreduce.v2.proto.MRProtos.TaskAttemptIdProto;
/*  11:    */ import org.apache.hadoop.mapreduce.v2.util.MRProtoUtils;
/*  12:    */ import org.apache.hadoop.yarn.api.records.impl.pb.ProtoBase;
/*  13:    */ 
/*  14:    */ public class TaskAttemptCompletionEventPBImpl
/*  15:    */   extends ProtoBase<MRProtos.TaskAttemptCompletionEventProto>
/*  16:    */   implements TaskAttemptCompletionEvent
/*  17:    */ {
/*  18: 35 */   MRProtos.TaskAttemptCompletionEventProto proto = MRProtos.TaskAttemptCompletionEventProto.getDefaultInstance();
/*  19: 36 */   MRProtos.TaskAttemptCompletionEventProto.Builder builder = null;
/*  20: 37 */   boolean viaProto = false;
/*  21: 39 */   private TaskAttemptId taskAttemptId = null;
/*  22:    */   
/*  23:    */   public TaskAttemptCompletionEventPBImpl()
/*  24:    */   {
/*  25: 43 */     this.builder = MRProtos.TaskAttemptCompletionEventProto.newBuilder();
/*  26:    */   }
/*  27:    */   
/*  28:    */   public TaskAttemptCompletionEventPBImpl(MRProtos.TaskAttemptCompletionEventProto proto)
/*  29:    */   {
/*  30: 47 */     this.proto = proto;
/*  31: 48 */     this.viaProto = true;
/*  32:    */   }
/*  33:    */   
/*  34:    */   public MRProtos.TaskAttemptCompletionEventProto getProto()
/*  35:    */   {
/*  36: 52 */     mergeLocalToProto();
/*  37: 53 */     this.proto = (this.viaProto ? this.proto : this.builder.build());
/*  38: 54 */     this.viaProto = true;
/*  39: 55 */     return this.proto;
/*  40:    */   }
/*  41:    */   
/*  42:    */   private void mergeLocalToBuilder()
/*  43:    */   {
/*  44: 59 */     if (this.taskAttemptId != null) {
/*  45: 60 */       this.builder.setAttemptId(convertToProtoFormat(this.taskAttemptId));
/*  46:    */     }
/*  47:    */   }
/*  48:    */   
/*  49:    */   private void mergeLocalToProto()
/*  50:    */   {
/*  51: 65 */     if (this.viaProto) {
/*  52: 66 */       maybeInitBuilder();
/*  53:    */     }
/*  54: 67 */     mergeLocalToBuilder();
/*  55: 68 */     this.proto = this.builder.build();
/*  56: 69 */     this.viaProto = true;
/*  57:    */   }
/*  58:    */   
/*  59:    */   private void maybeInitBuilder()
/*  60:    */   {
/*  61: 73 */     if ((this.viaProto) || (this.builder == null)) {
/*  62: 74 */       this.builder = MRProtos.TaskAttemptCompletionEventProto.newBuilder(this.proto);
/*  63:    */     }
/*  64: 76 */     this.viaProto = false;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public TaskAttemptId getAttemptId()
/*  68:    */   {
/*  69: 82 */     MRProtos.TaskAttemptCompletionEventProtoOrBuilder p = this.viaProto ? this.proto : this.builder;
/*  70: 83 */     if (this.taskAttemptId != null) {
/*  71: 84 */       return this.taskAttemptId;
/*  72:    */     }
/*  73: 86 */     if (!p.hasAttemptId()) {
/*  74: 87 */       return null;
/*  75:    */     }
/*  76: 89 */     this.taskAttemptId = convertFromProtoFormat(p.getAttemptId());
/*  77: 90 */     return this.taskAttemptId;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public void setAttemptId(TaskAttemptId attemptId)
/*  81:    */   {
/*  82: 95 */     maybeInitBuilder();
/*  83: 96 */     if (attemptId == null) {
/*  84: 97 */       this.builder.clearAttemptId();
/*  85:    */     }
/*  86: 98 */     this.taskAttemptId = attemptId;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public TaskAttemptCompletionEventStatus getStatus()
/*  90:    */   {
/*  91:102 */     MRProtos.TaskAttemptCompletionEventProtoOrBuilder p = this.viaProto ? this.proto : this.builder;
/*  92:103 */     if (!p.hasStatus()) {
/*  93:104 */       return null;
/*  94:    */     }
/*  95:106 */     return convertFromProtoFormat(p.getStatus());
/*  96:    */   }
/*  97:    */   
/*  98:    */   public void setStatus(TaskAttemptCompletionEventStatus status)
/*  99:    */   {
/* 100:111 */     maybeInitBuilder();
/* 101:112 */     if (status == null)
/* 102:    */     {
/* 103:113 */       this.builder.clearStatus();
/* 104:114 */       return;
/* 105:    */     }
/* 106:116 */     this.builder.setStatus(convertToProtoFormat(status));
/* 107:    */   }
/* 108:    */   
/* 109:    */   public String getMapOutputServerAddress()
/* 110:    */   {
/* 111:120 */     MRProtos.TaskAttemptCompletionEventProtoOrBuilder p = this.viaProto ? this.proto : this.builder;
/* 112:121 */     if (!p.hasMapOutputServerAddress()) {
/* 113:122 */       return null;
/* 114:    */     }
/* 115:124 */     return p.getMapOutputServerAddress();
/* 116:    */   }
/* 117:    */   
/* 118:    */   public void setMapOutputServerAddress(String mapOutputServerAddress)
/* 119:    */   {
/* 120:129 */     maybeInitBuilder();
/* 121:130 */     if (mapOutputServerAddress == null)
/* 122:    */     {
/* 123:131 */       this.builder.clearMapOutputServerAddress();
/* 124:132 */       return;
/* 125:    */     }
/* 126:134 */     this.builder.setMapOutputServerAddress(mapOutputServerAddress);
/* 127:    */   }
/* 128:    */   
/* 129:    */   public int getAttemptRunTime()
/* 130:    */   {
/* 131:138 */     MRProtos.TaskAttemptCompletionEventProtoOrBuilder p = this.viaProto ? this.proto : this.builder;
/* 132:139 */     return p.getAttemptRunTime();
/* 133:    */   }
/* 134:    */   
/* 135:    */   public void setAttemptRunTime(int attemptRunTime)
/* 136:    */   {
/* 137:144 */     maybeInitBuilder();
/* 138:145 */     this.builder.setAttemptRunTime(attemptRunTime);
/* 139:    */   }
/* 140:    */   
/* 141:    */   public int getEventId()
/* 142:    */   {
/* 143:149 */     MRProtos.TaskAttemptCompletionEventProtoOrBuilder p = this.viaProto ? this.proto : this.builder;
/* 144:150 */     return p.getEventId();
/* 145:    */   }
/* 146:    */   
/* 147:    */   public void setEventId(int eventId)
/* 148:    */   {
/* 149:155 */     maybeInitBuilder();
/* 150:156 */     this.builder.setEventId(eventId);
/* 151:    */   }
/* 152:    */   
/* 153:    */   private TaskAttemptIdPBImpl convertFromProtoFormat(MRProtos.TaskAttemptIdProto p)
/* 154:    */   {
/* 155:160 */     return new TaskAttemptIdPBImpl(p);
/* 156:    */   }
/* 157:    */   
/* 158:    */   private MRProtos.TaskAttemptIdProto convertToProtoFormat(TaskAttemptId t)
/* 159:    */   {
/* 160:164 */     return ((TaskAttemptIdPBImpl)t).getProto();
/* 161:    */   }
/* 162:    */   
/* 163:    */   private MRProtos.TaskAttemptCompletionEventStatusProto convertToProtoFormat(TaskAttemptCompletionEventStatus e)
/* 164:    */   {
/* 165:168 */     return MRProtoUtils.convertToProtoFormat(e);
/* 166:    */   }
/* 167:    */   
/* 168:    */   private TaskAttemptCompletionEventStatus convertFromProtoFormat(MRProtos.TaskAttemptCompletionEventStatusProto e)
/* 169:    */   {
/* 170:172 */     return MRProtoUtils.convertFromProtoFormat(e);
/* 171:    */   }
/* 172:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-common-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.v2.api.records.impl.pb.TaskAttemptCompletionEventPBImpl
 * JD-Core Version:    0.7.0.1
 */