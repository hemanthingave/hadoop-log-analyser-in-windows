/*   1:    */ package org.apache.hadoop.mapreduce.v2.api.protocolrecords.impl.pb;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.Iterator;
/*   5:    */ import java.util.List;
/*   6:    */ import org.apache.hadoop.mapreduce.v2.api.protocolrecords.GetTaskReportsResponse;
/*   7:    */ import org.apache.hadoop.mapreduce.v2.api.records.TaskReport;
/*   8:    */ import org.apache.hadoop.mapreduce.v2.api.records.impl.pb.TaskReportPBImpl;
/*   9:    */ import org.apache.hadoop.mapreduce.v2.proto.MRProtos.TaskReportProto;
/*  10:    */ import org.apache.hadoop.mapreduce.v2.proto.MRServiceProtos.GetTaskReportsResponseProto;
/*  11:    */ import org.apache.hadoop.mapreduce.v2.proto.MRServiceProtos.GetTaskReportsResponseProto.Builder;
/*  12:    */ import org.apache.hadoop.mapreduce.v2.proto.MRServiceProtos.GetTaskReportsResponseProtoOrBuilder;
/*  13:    */ import org.apache.hadoop.yarn.api.records.impl.pb.ProtoBase;
/*  14:    */ 
/*  15:    */ public class GetTaskReportsResponsePBImpl
/*  16:    */   extends ProtoBase<MRServiceProtos.GetTaskReportsResponseProto>
/*  17:    */   implements GetTaskReportsResponse
/*  18:    */ {
/*  19: 37 */   MRServiceProtos.GetTaskReportsResponseProto proto = MRServiceProtos.GetTaskReportsResponseProto.getDefaultInstance();
/*  20: 38 */   MRServiceProtos.GetTaskReportsResponseProto.Builder builder = null;
/*  21: 39 */   boolean viaProto = false;
/*  22: 41 */   private List<TaskReport> taskReports = null;
/*  23:    */   
/*  24:    */   public GetTaskReportsResponsePBImpl()
/*  25:    */   {
/*  26: 45 */     this.builder = MRServiceProtos.GetTaskReportsResponseProto.newBuilder();
/*  27:    */   }
/*  28:    */   
/*  29:    */   public GetTaskReportsResponsePBImpl(MRServiceProtos.GetTaskReportsResponseProto proto)
/*  30:    */   {
/*  31: 49 */     this.proto = proto;
/*  32: 50 */     this.viaProto = true;
/*  33:    */   }
/*  34:    */   
/*  35:    */   public MRServiceProtos.GetTaskReportsResponseProto getProto()
/*  36:    */   {
/*  37: 54 */     mergeLocalToProto();
/*  38: 55 */     this.proto = (this.viaProto ? this.proto : this.builder.build());
/*  39: 56 */     this.viaProto = true;
/*  40: 57 */     return this.proto;
/*  41:    */   }
/*  42:    */   
/*  43:    */   private void mergeLocalToBuilder()
/*  44:    */   {
/*  45: 61 */     if (this.taskReports != null) {
/*  46: 62 */       addTaskReportsToProto();
/*  47:    */     }
/*  48:    */   }
/*  49:    */   
/*  50:    */   private void mergeLocalToProto()
/*  51:    */   {
/*  52: 67 */     if (this.viaProto) {
/*  53: 68 */       maybeInitBuilder();
/*  54:    */     }
/*  55: 69 */     mergeLocalToBuilder();
/*  56: 70 */     this.proto = this.builder.build();
/*  57: 71 */     this.viaProto = true;
/*  58:    */   }
/*  59:    */   
/*  60:    */   private void maybeInitBuilder()
/*  61:    */   {
/*  62: 75 */     if ((this.viaProto) || (this.builder == null)) {
/*  63: 76 */       this.builder = MRServiceProtos.GetTaskReportsResponseProto.newBuilder(this.proto);
/*  64:    */     }
/*  65: 78 */     this.viaProto = false;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public List<TaskReport> getTaskReportList()
/*  69:    */   {
/*  70: 84 */     initTaskReports();
/*  71: 85 */     return this.taskReports;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public TaskReport getTaskReport(int index)
/*  75:    */   {
/*  76: 89 */     initTaskReports();
/*  77: 90 */     return (TaskReport)this.taskReports.get(index);
/*  78:    */   }
/*  79:    */   
/*  80:    */   public int getTaskReportCount()
/*  81:    */   {
/*  82: 94 */     initTaskReports();
/*  83: 95 */     return this.taskReports.size();
/*  84:    */   }
/*  85:    */   
/*  86:    */   private void initTaskReports()
/*  87:    */   {
/*  88: 99 */     if (this.taskReports != null) {
/*  89:100 */       return;
/*  90:    */     }
/*  91:102 */     MRServiceProtos.GetTaskReportsResponseProtoOrBuilder p = this.viaProto ? this.proto : this.builder;
/*  92:103 */     List<MRProtos.TaskReportProto> list = p.getTaskReportsList();
/*  93:104 */     this.taskReports = new ArrayList();
/*  94:106 */     for (MRProtos.TaskReportProto c : list) {
/*  95:107 */       this.taskReports.add(convertFromProtoFormat(c));
/*  96:    */     }
/*  97:    */   }
/*  98:    */   
/*  99:    */   public void addAllTaskReports(List<TaskReport> taskReports)
/* 100:    */   {
/* 101:113 */     if (taskReports == null) {
/* 102:114 */       return;
/* 103:    */     }
/* 104:115 */     initTaskReports();
/* 105:116 */     this.taskReports.addAll(taskReports);
/* 106:    */   }
/* 107:    */   
/* 108:    */   private void addTaskReportsToProto()
/* 109:    */   {
/* 110:120 */     maybeInitBuilder();
/* 111:121 */     this.builder.clearTaskReports();
/* 112:122 */     if (this.taskReports == null) {
/* 113:123 */       return;
/* 114:    */     }
/* 115:124 */     Iterable<MRProtos.TaskReportProto> iterable = new Iterable()
/* 116:    */     {
/* 117:    */       public Iterator<MRProtos.TaskReportProto> iterator()
/* 118:    */       {
/* 119:127 */         new Iterator()
/* 120:    */         {
/* 121:129 */           Iterator<TaskReport> iter = GetTaskReportsResponsePBImpl.this.taskReports.iterator();
/* 122:    */           
/* 123:    */           public boolean hasNext()
/* 124:    */           {
/* 125:133 */             return this.iter.hasNext();
/* 126:    */           }
/* 127:    */           
/* 128:    */           public MRProtos.TaskReportProto next()
/* 129:    */           {
/* 130:138 */             return GetTaskReportsResponsePBImpl.this.convertToProtoFormat((TaskReport)this.iter.next());
/* 131:    */           }
/* 132:    */           
/* 133:    */           public void remove()
/* 134:    */           {
/* 135:143 */             throw new UnsupportedOperationException();
/* 136:    */           }
/* 137:    */         };
/* 138:    */       }
/* 139:149 */     };
/* 140:150 */     this.builder.addAllTaskReports(iterable);
/* 141:    */   }
/* 142:    */   
/* 143:    */   public void addTaskReport(TaskReport taskReports)
/* 144:    */   {
/* 145:154 */     initTaskReports();
/* 146:155 */     this.taskReports.add(taskReports);
/* 147:    */   }
/* 148:    */   
/* 149:    */   public void removeTaskReport(int index)
/* 150:    */   {
/* 151:159 */     initTaskReports();
/* 152:160 */     this.taskReports.remove(index);
/* 153:    */   }
/* 154:    */   
/* 155:    */   public void clearTaskReports()
/* 156:    */   {
/* 157:164 */     initTaskReports();
/* 158:165 */     this.taskReports.clear();
/* 159:    */   }
/* 160:    */   
/* 161:    */   private TaskReportPBImpl convertFromProtoFormat(MRProtos.TaskReportProto p)
/* 162:    */   {
/* 163:169 */     return new TaskReportPBImpl(p);
/* 164:    */   }
/* 165:    */   
/* 166:    */   private MRProtos.TaskReportProto convertToProtoFormat(TaskReport t)
/* 167:    */   {
/* 168:173 */     return ((TaskReportPBImpl)t).getProto();
/* 169:    */   }
/* 170:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-common-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.v2.api.protocolrecords.impl.pb.GetTaskReportsResponsePBImpl
 * JD-Core Version:    0.7.0.1
 */