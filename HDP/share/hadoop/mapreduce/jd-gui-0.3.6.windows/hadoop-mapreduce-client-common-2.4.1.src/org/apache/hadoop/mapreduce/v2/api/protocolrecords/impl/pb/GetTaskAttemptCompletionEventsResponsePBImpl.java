/*   1:    */ package org.apache.hadoop.mapreduce.v2.api.protocolrecords.impl.pb;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.Iterator;
/*   5:    */ import java.util.List;
/*   6:    */ import org.apache.hadoop.mapreduce.v2.api.protocolrecords.GetTaskAttemptCompletionEventsResponse;
/*   7:    */ import org.apache.hadoop.mapreduce.v2.api.records.TaskAttemptCompletionEvent;
/*   8:    */ import org.apache.hadoop.mapreduce.v2.api.records.impl.pb.TaskAttemptCompletionEventPBImpl;
/*   9:    */ import org.apache.hadoop.mapreduce.v2.proto.MRProtos.TaskAttemptCompletionEventProto;
/*  10:    */ import org.apache.hadoop.mapreduce.v2.proto.MRServiceProtos.GetTaskAttemptCompletionEventsResponseProto;
/*  11:    */ import org.apache.hadoop.mapreduce.v2.proto.MRServiceProtos.GetTaskAttemptCompletionEventsResponseProto.Builder;
/*  12:    */ import org.apache.hadoop.mapreduce.v2.proto.MRServiceProtos.GetTaskAttemptCompletionEventsResponseProtoOrBuilder;
/*  13:    */ import org.apache.hadoop.yarn.api.records.impl.pb.ProtoBase;
/*  14:    */ 
/*  15:    */ public class GetTaskAttemptCompletionEventsResponsePBImpl
/*  16:    */   extends ProtoBase<MRServiceProtos.GetTaskAttemptCompletionEventsResponseProto>
/*  17:    */   implements GetTaskAttemptCompletionEventsResponse
/*  18:    */ {
/*  19: 37 */   MRServiceProtos.GetTaskAttemptCompletionEventsResponseProto proto = MRServiceProtos.GetTaskAttemptCompletionEventsResponseProto.getDefaultInstance();
/*  20: 38 */   MRServiceProtos.GetTaskAttemptCompletionEventsResponseProto.Builder builder = null;
/*  21: 39 */   boolean viaProto = false;
/*  22: 41 */   private List<TaskAttemptCompletionEvent> completionEvents = null;
/*  23:    */   
/*  24:    */   public GetTaskAttemptCompletionEventsResponsePBImpl()
/*  25:    */   {
/*  26: 45 */     this.builder = MRServiceProtos.GetTaskAttemptCompletionEventsResponseProto.newBuilder();
/*  27:    */   }
/*  28:    */   
/*  29:    */   public GetTaskAttemptCompletionEventsResponsePBImpl(MRServiceProtos.GetTaskAttemptCompletionEventsResponseProto proto)
/*  30:    */   {
/*  31: 49 */     this.proto = proto;
/*  32: 50 */     this.viaProto = true;
/*  33:    */   }
/*  34:    */   
/*  35:    */   public MRServiceProtos.GetTaskAttemptCompletionEventsResponseProto getProto()
/*  36:    */   {
/*  37: 54 */     mergeLocalToProto();
/*  38: 55 */     this.proto = (this.viaProto ? this.proto : this.builder.build());
/*  39: 56 */     this.viaProto = true;
/*  40: 57 */     return this.proto;
/*  41:    */   }
/*  42:    */   
/*  43:    */   private void mergeLocalToBuilder()
/*  44:    */   {
/*  45: 61 */     if (this.completionEvents != null) {
/*  46: 62 */       addCompletionEventsToProto();
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
/*  63: 76 */       this.builder = MRServiceProtos.GetTaskAttemptCompletionEventsResponseProto.newBuilder(this.proto);
/*  64:    */     }
/*  65: 78 */     this.viaProto = false;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public List<TaskAttemptCompletionEvent> getCompletionEventList()
/*  69:    */   {
/*  70: 84 */     initCompletionEvents();
/*  71: 85 */     return this.completionEvents;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public TaskAttemptCompletionEvent getCompletionEvent(int index)
/*  75:    */   {
/*  76: 89 */     initCompletionEvents();
/*  77: 90 */     return (TaskAttemptCompletionEvent)this.completionEvents.get(index);
/*  78:    */   }
/*  79:    */   
/*  80:    */   public int getCompletionEventCount()
/*  81:    */   {
/*  82: 94 */     initCompletionEvents();
/*  83: 95 */     return this.completionEvents.size();
/*  84:    */   }
/*  85:    */   
/*  86:    */   private void initCompletionEvents()
/*  87:    */   {
/*  88: 99 */     if (this.completionEvents != null) {
/*  89:100 */       return;
/*  90:    */     }
/*  91:102 */     MRServiceProtos.GetTaskAttemptCompletionEventsResponseProtoOrBuilder p = this.viaProto ? this.proto : this.builder;
/*  92:103 */     List<MRProtos.TaskAttemptCompletionEventProto> list = p.getCompletionEventsList();
/*  93:104 */     this.completionEvents = new ArrayList();
/*  94:106 */     for (MRProtos.TaskAttemptCompletionEventProto c : list) {
/*  95:107 */       this.completionEvents.add(convertFromProtoFormat(c));
/*  96:    */     }
/*  97:    */   }
/*  98:    */   
/*  99:    */   public void addAllCompletionEvents(List<TaskAttemptCompletionEvent> completionEvents)
/* 100:    */   {
/* 101:113 */     if (completionEvents == null) {
/* 102:114 */       return;
/* 103:    */     }
/* 104:115 */     initCompletionEvents();
/* 105:116 */     this.completionEvents.addAll(completionEvents);
/* 106:    */   }
/* 107:    */   
/* 108:    */   private void addCompletionEventsToProto()
/* 109:    */   {
/* 110:120 */     maybeInitBuilder();
/* 111:121 */     this.builder.clearCompletionEvents();
/* 112:122 */     if (this.completionEvents == null) {
/* 113:123 */       return;
/* 114:    */     }
/* 115:124 */     Iterable<MRProtos.TaskAttemptCompletionEventProto> iterable = new Iterable()
/* 116:    */     {
/* 117:    */       public Iterator<MRProtos.TaskAttemptCompletionEventProto> iterator()
/* 118:    */       {
/* 119:127 */         new Iterator()
/* 120:    */         {
/* 121:129 */           Iterator<TaskAttemptCompletionEvent> iter = GetTaskAttemptCompletionEventsResponsePBImpl.this.completionEvents.iterator();
/* 122:    */           
/* 123:    */           public boolean hasNext()
/* 124:    */           {
/* 125:133 */             return this.iter.hasNext();
/* 126:    */           }
/* 127:    */           
/* 128:    */           public MRProtos.TaskAttemptCompletionEventProto next()
/* 129:    */           {
/* 130:138 */             return GetTaskAttemptCompletionEventsResponsePBImpl.this.convertToProtoFormat((TaskAttemptCompletionEvent)this.iter.next());
/* 131:    */           }
/* 132:    */           
/* 133:    */           public void remove()
/* 134:    */           {
/* 135:143 */             throw new UnsupportedOperationException();
/* 136:    */           }
/* 137:    */         };
/* 138:    */       }
/* 139:149 */     };
/* 140:150 */     this.builder.addAllCompletionEvents(iterable);
/* 141:    */   }
/* 142:    */   
/* 143:    */   public void addCompletionEvent(TaskAttemptCompletionEvent completionEvents)
/* 144:    */   {
/* 145:154 */     initCompletionEvents();
/* 146:155 */     this.completionEvents.add(completionEvents);
/* 147:    */   }
/* 148:    */   
/* 149:    */   public void removeCompletionEvent(int index)
/* 150:    */   {
/* 151:159 */     initCompletionEvents();
/* 152:160 */     this.completionEvents.remove(index);
/* 153:    */   }
/* 154:    */   
/* 155:    */   public void clearCompletionEvents()
/* 156:    */   {
/* 157:164 */     initCompletionEvents();
/* 158:165 */     this.completionEvents.clear();
/* 159:    */   }
/* 160:    */   
/* 161:    */   private TaskAttemptCompletionEventPBImpl convertFromProtoFormat(MRProtos.TaskAttemptCompletionEventProto p)
/* 162:    */   {
/* 163:169 */     return new TaskAttemptCompletionEventPBImpl(p);
/* 164:    */   }
/* 165:    */   
/* 166:    */   private MRProtos.TaskAttemptCompletionEventProto convertToProtoFormat(TaskAttemptCompletionEvent t)
/* 167:    */   {
/* 168:173 */     return ((TaskAttemptCompletionEventPBImpl)t).getProto();
/* 169:    */   }
/* 170:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-common-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.v2.api.protocolrecords.impl.pb.GetTaskAttemptCompletionEventsResponsePBImpl
 * JD-Core Version:    0.7.0.1
 */