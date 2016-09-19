/*   1:    */ package org.apache.hadoop.mapreduce.v2.api.records.impl.pb;
/*   2:    */ 
/*   3:    */ import org.apache.hadoop.mapreduce.v2.api.records.AMInfo;
/*   4:    */ import org.apache.hadoop.mapreduce.v2.proto.MRProtos.AMInfoProto;
/*   5:    */ import org.apache.hadoop.mapreduce.v2.proto.MRProtos.AMInfoProto.Builder;
/*   6:    */ import org.apache.hadoop.mapreduce.v2.proto.MRProtos.AMInfoProtoOrBuilder;
/*   7:    */ import org.apache.hadoop.yarn.api.records.ApplicationAttemptId;
/*   8:    */ import org.apache.hadoop.yarn.api.records.ContainerId;
/*   9:    */ import org.apache.hadoop.yarn.api.records.impl.pb.ApplicationAttemptIdPBImpl;
/*  10:    */ import org.apache.hadoop.yarn.api.records.impl.pb.ContainerIdPBImpl;
/*  11:    */ import org.apache.hadoop.yarn.api.records.impl.pb.ProtoBase;
/*  12:    */ import org.apache.hadoop.yarn.proto.YarnProtos.ApplicationAttemptIdProto;
/*  13:    */ import org.apache.hadoop.yarn.proto.YarnProtos.ContainerIdProto;
/*  14:    */ 
/*  15:    */ public class AMInfoPBImpl
/*  16:    */   extends ProtoBase<MRProtos.AMInfoProto>
/*  17:    */   implements AMInfo
/*  18:    */ {
/*  19: 34 */   MRProtos.AMInfoProto proto = MRProtos.AMInfoProto.getDefaultInstance();
/*  20: 35 */   MRProtos.AMInfoProto.Builder builder = null;
/*  21: 36 */   boolean viaProto = false;
/*  22:    */   private ApplicationAttemptId appAttemptId;
/*  23:    */   private ContainerId containerId;
/*  24:    */   
/*  25:    */   public AMInfoPBImpl()
/*  26:    */   {
/*  27: 42 */     this.builder = MRProtos.AMInfoProto.newBuilder();
/*  28:    */   }
/*  29:    */   
/*  30:    */   public AMInfoPBImpl(MRProtos.AMInfoProto proto)
/*  31:    */   {
/*  32: 46 */     this.proto = proto;
/*  33: 47 */     this.viaProto = true;
/*  34:    */   }
/*  35:    */   
/*  36:    */   public synchronized MRProtos.AMInfoProto getProto()
/*  37:    */   {
/*  38: 51 */     mergeLocalToProto();
/*  39: 52 */     this.proto = (this.viaProto ? this.proto : this.builder.build());
/*  40: 53 */     this.viaProto = true;
/*  41: 54 */     return this.proto;
/*  42:    */   }
/*  43:    */   
/*  44:    */   private synchronized void mergeLocalToBuilder()
/*  45:    */   {
/*  46: 58 */     if ((this.appAttemptId != null) && (!((ApplicationAttemptIdPBImpl)this.appAttemptId).getProto().equals(this.builder.getApplicationAttemptId()))) {
/*  47: 61 */       this.builder.setApplicationAttemptId(convertToProtoFormat(this.appAttemptId));
/*  48:    */     }
/*  49: 63 */     if ((getContainerId() != null) && (!((ContainerIdPBImpl)this.containerId).getProto().equals(this.builder.getContainerId()))) {
/*  50: 66 */       this.builder.setContainerId(convertToProtoFormat(this.containerId));
/*  51:    */     }
/*  52:    */   }
/*  53:    */   
/*  54:    */   private synchronized void mergeLocalToProto()
/*  55:    */   {
/*  56: 71 */     if (this.viaProto) {
/*  57: 72 */       maybeInitBuilder();
/*  58:    */     }
/*  59: 73 */     mergeLocalToBuilder();
/*  60: 74 */     this.proto = this.builder.build();
/*  61: 75 */     this.viaProto = true;
/*  62:    */   }
/*  63:    */   
/*  64:    */   private synchronized void maybeInitBuilder()
/*  65:    */   {
/*  66: 79 */     if ((this.viaProto) || (this.builder == null)) {
/*  67: 80 */       this.builder = MRProtos.AMInfoProto.newBuilder(this.proto);
/*  68:    */     }
/*  69: 82 */     this.viaProto = false;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public synchronized ApplicationAttemptId getAppAttemptId()
/*  73:    */   {
/*  74: 87 */     MRProtos.AMInfoProtoOrBuilder p = this.viaProto ? this.proto : this.builder;
/*  75: 88 */     if (this.appAttemptId != null) {
/*  76: 89 */       return this.appAttemptId;
/*  77:    */     }
/*  78: 91 */     if (!p.hasApplicationAttemptId()) {
/*  79: 92 */       return null;
/*  80:    */     }
/*  81: 94 */     this.appAttemptId = convertFromProtoFormat(p.getApplicationAttemptId());
/*  82: 95 */     return this.appAttemptId;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public synchronized void setAppAttemptId(ApplicationAttemptId appAttemptId)
/*  86:    */   {
/*  87:100 */     maybeInitBuilder();
/*  88:101 */     if (appAttemptId == null) {
/*  89:102 */       this.builder.clearApplicationAttemptId();
/*  90:    */     }
/*  91:104 */     this.appAttemptId = appAttemptId;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public synchronized long getStartTime()
/*  95:    */   {
/*  96:109 */     MRProtos.AMInfoProtoOrBuilder p = this.viaProto ? this.proto : this.builder;
/*  97:110 */     return p.getStartTime();
/*  98:    */   }
/*  99:    */   
/* 100:    */   public synchronized void setStartTime(long startTime)
/* 101:    */   {
/* 102:115 */     maybeInitBuilder();
/* 103:116 */     this.builder.setStartTime(startTime);
/* 104:    */   }
/* 105:    */   
/* 106:    */   public synchronized ContainerId getContainerId()
/* 107:    */   {
/* 108:121 */     MRProtos.AMInfoProtoOrBuilder p = this.viaProto ? this.proto : this.builder;
/* 109:122 */     if (this.containerId != null) {
/* 110:123 */       return this.containerId;
/* 111:    */     }
/* 112:125 */     if (!p.hasContainerId()) {
/* 113:126 */       return null;
/* 114:    */     }
/* 115:128 */     this.containerId = convertFromProtoFormat(p.getContainerId());
/* 116:129 */     return this.containerId;
/* 117:    */   }
/* 118:    */   
/* 119:    */   public synchronized void setContainerId(ContainerId containerId)
/* 120:    */   {
/* 121:134 */     maybeInitBuilder();
/* 122:135 */     if (containerId == null) {
/* 123:136 */       this.builder.clearContainerId();
/* 124:    */     }
/* 125:138 */     this.containerId = containerId;
/* 126:    */   }
/* 127:    */   
/* 128:    */   public synchronized String getNodeManagerHost()
/* 129:    */   {
/* 130:143 */     MRProtos.AMInfoProtoOrBuilder p = this.viaProto ? this.proto : this.builder;
/* 131:144 */     if (!p.hasNodeManagerHost()) {
/* 132:145 */       return null;
/* 133:    */     }
/* 134:147 */     return p.getNodeManagerHost();
/* 135:    */   }
/* 136:    */   
/* 137:    */   public synchronized void setNodeManagerHost(String nmHost)
/* 138:    */   {
/* 139:152 */     maybeInitBuilder();
/* 140:153 */     if (nmHost == null)
/* 141:    */     {
/* 142:154 */       this.builder.clearNodeManagerHost();
/* 143:155 */       return;
/* 144:    */     }
/* 145:157 */     this.builder.setNodeManagerHost(nmHost);
/* 146:    */   }
/* 147:    */   
/* 148:    */   public synchronized int getNodeManagerPort()
/* 149:    */   {
/* 150:162 */     MRProtos.AMInfoProtoOrBuilder p = this.viaProto ? this.proto : this.builder;
/* 151:163 */     return p.getNodeManagerPort();
/* 152:    */   }
/* 153:    */   
/* 154:    */   public synchronized void setNodeManagerPort(int nmPort)
/* 155:    */   {
/* 156:168 */     maybeInitBuilder();
/* 157:169 */     this.builder.setNodeManagerPort(nmPort);
/* 158:    */   }
/* 159:    */   
/* 160:    */   public synchronized int getNodeManagerHttpPort()
/* 161:    */   {
/* 162:174 */     MRProtos.AMInfoProtoOrBuilder p = this.viaProto ? this.proto : this.builder;
/* 163:175 */     return p.getNodeManagerHttpPort();
/* 164:    */   }
/* 165:    */   
/* 166:    */   public synchronized void setNodeManagerHttpPort(int httpPort)
/* 167:    */   {
/* 168:180 */     maybeInitBuilder();
/* 169:181 */     this.builder.setNodeManagerHttpPort(httpPort);
/* 170:    */   }
/* 171:    */   
/* 172:    */   private ApplicationAttemptIdPBImpl convertFromProtoFormat(YarnProtos.ApplicationAttemptIdProto p)
/* 173:    */   {
/* 174:186 */     return new ApplicationAttemptIdPBImpl(p);
/* 175:    */   }
/* 176:    */   
/* 177:    */   private ContainerIdPBImpl convertFromProtoFormat(YarnProtos.ContainerIdProto p)
/* 178:    */   {
/* 179:190 */     return new ContainerIdPBImpl(p);
/* 180:    */   }
/* 181:    */   
/* 182:    */   private YarnProtos.ApplicationAttemptIdProto convertToProtoFormat(ApplicationAttemptId t)
/* 183:    */   {
/* 184:195 */     return ((ApplicationAttemptIdPBImpl)t).getProto();
/* 185:    */   }
/* 186:    */   
/* 187:    */   private YarnProtos.ContainerIdProto convertToProtoFormat(ContainerId t)
/* 188:    */   {
/* 189:199 */     return ((ContainerIdPBImpl)t).getProto();
/* 190:    */   }
/* 191:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-common-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.v2.api.records.impl.pb.AMInfoPBImpl
 * JD-Core Version:    0.7.0.1
 */