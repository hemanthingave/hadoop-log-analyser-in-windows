/*   1:    */ package org.apache.hadoop.mapreduce.v2.api.records.impl.pb;
/*   2:    */ 
/*   3:    */ import org.apache.hadoop.mapreduce.v2.api.records.JobId;
/*   4:    */ import org.apache.hadoop.mapreduce.v2.proto.MRProtos.JobIdProto;
/*   5:    */ import org.apache.hadoop.mapreduce.v2.proto.MRProtos.JobIdProto.Builder;
/*   6:    */ import org.apache.hadoop.mapreduce.v2.proto.MRProtos.JobIdProtoOrBuilder;
/*   7:    */ import org.apache.hadoop.yarn.api.records.ApplicationId;
/*   8:    */ import org.apache.hadoop.yarn.api.records.impl.pb.ApplicationIdPBImpl;
/*   9:    */ import org.apache.hadoop.yarn.proto.YarnProtos.ApplicationIdProto;
/*  10:    */ 
/*  11:    */ public class JobIdPBImpl
/*  12:    */   extends JobId
/*  13:    */ {
/*  14: 30 */   MRProtos.JobIdProto proto = MRProtos.JobIdProto.getDefaultInstance();
/*  15: 31 */   MRProtos.JobIdProto.Builder builder = null;
/*  16: 32 */   boolean viaProto = false;
/*  17: 34 */   private ApplicationId applicationId = null;
/*  18:    */   
/*  19:    */   public JobIdPBImpl()
/*  20:    */   {
/*  21: 37 */     this.builder = MRProtos.JobIdProto.newBuilder();
/*  22:    */   }
/*  23:    */   
/*  24:    */   public JobIdPBImpl(MRProtos.JobIdProto proto)
/*  25:    */   {
/*  26: 41 */     this.proto = proto;
/*  27: 42 */     this.viaProto = true;
/*  28:    */   }
/*  29:    */   
/*  30:    */   public synchronized MRProtos.JobIdProto getProto()
/*  31:    */   {
/*  32: 46 */     mergeLocalToProto();
/*  33: 47 */     this.proto = (this.viaProto ? this.proto : this.builder.build());
/*  34: 48 */     this.viaProto = true;
/*  35: 49 */     return this.proto;
/*  36:    */   }
/*  37:    */   
/*  38:    */   private synchronized void mergeLocalToBuilder()
/*  39:    */   {
/*  40: 53 */     if ((this.applicationId != null) && (!((ApplicationIdPBImpl)this.applicationId).getProto().equals(this.builder.getAppId()))) {
/*  41: 56 */       this.builder.setAppId(convertToProtoFormat(this.applicationId));
/*  42:    */     }
/*  43:    */   }
/*  44:    */   
/*  45:    */   private synchronized void mergeLocalToProto()
/*  46:    */   {
/*  47: 61 */     if (this.viaProto) {
/*  48: 62 */       maybeInitBuilder();
/*  49:    */     }
/*  50: 63 */     mergeLocalToBuilder();
/*  51: 64 */     this.proto = this.builder.build();
/*  52: 65 */     this.viaProto = true;
/*  53:    */   }
/*  54:    */   
/*  55:    */   private synchronized void maybeInitBuilder()
/*  56:    */   {
/*  57: 69 */     if ((this.viaProto) || (this.builder == null)) {
/*  58: 70 */       this.builder = MRProtos.JobIdProto.newBuilder(this.proto);
/*  59:    */     }
/*  60: 72 */     this.viaProto = false;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public synchronized ApplicationId getAppId()
/*  64:    */   {
/*  65: 78 */     MRProtos.JobIdProtoOrBuilder p = this.viaProto ? this.proto : this.builder;
/*  66: 79 */     if (this.applicationId != null) {
/*  67: 80 */       return this.applicationId;
/*  68:    */     }
/*  69: 82 */     if (!p.hasAppId()) {
/*  70: 83 */       return null;
/*  71:    */     }
/*  72: 85 */     this.applicationId = convertFromProtoFormat(p.getAppId());
/*  73: 86 */     return this.applicationId;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public synchronized void setAppId(ApplicationId appId)
/*  77:    */   {
/*  78: 91 */     maybeInitBuilder();
/*  79: 92 */     if (appId == null) {
/*  80: 93 */       this.builder.clearAppId();
/*  81:    */     }
/*  82: 95 */     this.applicationId = appId;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public synchronized int getId()
/*  86:    */   {
/*  87: 99 */     MRProtos.JobIdProtoOrBuilder p = this.viaProto ? this.proto : this.builder;
/*  88:100 */     return p.getId();
/*  89:    */   }
/*  90:    */   
/*  91:    */   public synchronized void setId(int id)
/*  92:    */   {
/*  93:105 */     maybeInitBuilder();
/*  94:106 */     this.builder.setId(id);
/*  95:    */   }
/*  96:    */   
/*  97:    */   private ApplicationIdPBImpl convertFromProtoFormat(YarnProtos.ApplicationIdProto p)
/*  98:    */   {
/*  99:111 */     return new ApplicationIdPBImpl(p);
/* 100:    */   }
/* 101:    */   
/* 102:    */   private YarnProtos.ApplicationIdProto convertToProtoFormat(ApplicationId t)
/* 103:    */   {
/* 104:115 */     return ((ApplicationIdPBImpl)t).getProto();
/* 105:    */   }
/* 106:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-common-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.v2.api.records.impl.pb.JobIdPBImpl
 * JD-Core Version:    0.7.0.1
 */