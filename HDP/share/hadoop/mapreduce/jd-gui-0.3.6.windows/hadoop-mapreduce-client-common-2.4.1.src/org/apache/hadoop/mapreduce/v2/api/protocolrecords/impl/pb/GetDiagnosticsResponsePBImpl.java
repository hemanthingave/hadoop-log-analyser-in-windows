/*   1:    */ package org.apache.hadoop.mapreduce.v2.api.protocolrecords.impl.pb;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.List;
/*   5:    */ import org.apache.hadoop.mapreduce.v2.api.protocolrecords.GetDiagnosticsResponse;
/*   6:    */ import org.apache.hadoop.mapreduce.v2.proto.MRServiceProtos.GetDiagnosticsResponseProto;
/*   7:    */ import org.apache.hadoop.mapreduce.v2.proto.MRServiceProtos.GetDiagnosticsResponseProto.Builder;
/*   8:    */ import org.apache.hadoop.mapreduce.v2.proto.MRServiceProtos.GetDiagnosticsResponseProtoOrBuilder;
/*   9:    */ import org.apache.hadoop.yarn.api.records.impl.pb.ProtoBase;
/*  10:    */ 
/*  11:    */ public class GetDiagnosticsResponsePBImpl
/*  12:    */   extends ProtoBase<MRServiceProtos.GetDiagnosticsResponseProto>
/*  13:    */   implements GetDiagnosticsResponse
/*  14:    */ {
/*  15: 33 */   MRServiceProtos.GetDiagnosticsResponseProto proto = MRServiceProtos.GetDiagnosticsResponseProto.getDefaultInstance();
/*  16: 34 */   MRServiceProtos.GetDiagnosticsResponseProto.Builder builder = null;
/*  17: 35 */   boolean viaProto = false;
/*  18: 37 */   private List<String> diagnostics = null;
/*  19:    */   
/*  20:    */   public GetDiagnosticsResponsePBImpl()
/*  21:    */   {
/*  22: 41 */     this.builder = MRServiceProtos.GetDiagnosticsResponseProto.newBuilder();
/*  23:    */   }
/*  24:    */   
/*  25:    */   public GetDiagnosticsResponsePBImpl(MRServiceProtos.GetDiagnosticsResponseProto proto)
/*  26:    */   {
/*  27: 45 */     this.proto = proto;
/*  28: 46 */     this.viaProto = true;
/*  29:    */   }
/*  30:    */   
/*  31:    */   public MRServiceProtos.GetDiagnosticsResponseProto getProto()
/*  32:    */   {
/*  33: 50 */     mergeLocalToProto();
/*  34: 51 */     this.proto = (this.viaProto ? this.proto : this.builder.build());
/*  35: 52 */     this.viaProto = true;
/*  36: 53 */     return this.proto;
/*  37:    */   }
/*  38:    */   
/*  39:    */   private void mergeLocalToBuilder()
/*  40:    */   {
/*  41: 57 */     if (this.diagnostics != null) {
/*  42: 58 */       addDiagnosticsToProto();
/*  43:    */     }
/*  44:    */   }
/*  45:    */   
/*  46:    */   private void mergeLocalToProto()
/*  47:    */   {
/*  48: 63 */     if (this.viaProto) {
/*  49: 64 */       maybeInitBuilder();
/*  50:    */     }
/*  51: 65 */     mergeLocalToBuilder();
/*  52: 66 */     this.proto = this.builder.build();
/*  53: 67 */     this.viaProto = true;
/*  54:    */   }
/*  55:    */   
/*  56:    */   private void maybeInitBuilder()
/*  57:    */   {
/*  58: 71 */     if ((this.viaProto) || (this.builder == null)) {
/*  59: 72 */       this.builder = MRServiceProtos.GetDiagnosticsResponseProto.newBuilder(this.proto);
/*  60:    */     }
/*  61: 74 */     this.viaProto = false;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public List<String> getDiagnosticsList()
/*  65:    */   {
/*  66: 80 */     initDiagnostics();
/*  67: 81 */     return this.diagnostics;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public String getDiagnostics(int index)
/*  71:    */   {
/*  72: 85 */     initDiagnostics();
/*  73: 86 */     return (String)this.diagnostics.get(index);
/*  74:    */   }
/*  75:    */   
/*  76:    */   public int getDiagnosticsCount()
/*  77:    */   {
/*  78: 90 */     initDiagnostics();
/*  79: 91 */     return this.diagnostics.size();
/*  80:    */   }
/*  81:    */   
/*  82:    */   private void initDiagnostics()
/*  83:    */   {
/*  84: 95 */     if (this.diagnostics != null) {
/*  85: 96 */       return;
/*  86:    */     }
/*  87: 98 */     MRServiceProtos.GetDiagnosticsResponseProtoOrBuilder p = this.viaProto ? this.proto : this.builder;
/*  88: 99 */     List<String> list = p.getDiagnosticsList();
/*  89:100 */     this.diagnostics = new ArrayList();
/*  90:102 */     for (String c : list) {
/*  91:103 */       this.diagnostics.add(c);
/*  92:    */     }
/*  93:    */   }
/*  94:    */   
/*  95:    */   public void addAllDiagnostics(List<String> diagnostics)
/*  96:    */   {
/*  97:109 */     if (diagnostics == null) {
/*  98:110 */       return;
/*  99:    */     }
/* 100:111 */     initDiagnostics();
/* 101:112 */     this.diagnostics.addAll(diagnostics);
/* 102:    */   }
/* 103:    */   
/* 104:    */   private void addDiagnosticsToProto()
/* 105:    */   {
/* 106:116 */     maybeInitBuilder();
/* 107:117 */     this.builder.clearDiagnostics();
/* 108:118 */     if (this.diagnostics == null) {
/* 109:119 */       return;
/* 110:    */     }
/* 111:120 */     this.builder.addAllDiagnostics(this.diagnostics);
/* 112:    */   }
/* 113:    */   
/* 114:    */   public void addDiagnostics(String diagnostics)
/* 115:    */   {
/* 116:124 */     initDiagnostics();
/* 117:125 */     this.diagnostics.add(diagnostics);
/* 118:    */   }
/* 119:    */   
/* 120:    */   public void removeDiagnostics(int index)
/* 121:    */   {
/* 122:129 */     initDiagnostics();
/* 123:130 */     this.diagnostics.remove(index);
/* 124:    */   }
/* 125:    */   
/* 126:    */   public void clearDiagnostics()
/* 127:    */   {
/* 128:134 */     initDiagnostics();
/* 129:135 */     this.diagnostics.clear();
/* 130:    */   }
/* 131:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-common-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.v2.api.protocolrecords.impl.pb.GetDiagnosticsResponsePBImpl
 * JD-Core Version:    0.7.0.1
 */