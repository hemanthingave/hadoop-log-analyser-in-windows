/*     1:      */ package org.apache.hadoop.mapreduce.v2.proto;
/*     2:      */ 
/*     3:      */ import com.google.protobuf.AbstractParser;
/*     4:      */ import com.google.protobuf.ByteString;
/*     5:      */ import com.google.protobuf.CodedInputStream;
/*     6:      */ import com.google.protobuf.CodedOutputStream;
/*     7:      */ import com.google.protobuf.Descriptors.Descriptor;
/*     8:      */ import com.google.protobuf.Descriptors.FileDescriptor;
/*     9:      */ import com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner;
/*    10:      */ import com.google.protobuf.ExtensionRegistry;
/*    11:      */ import com.google.protobuf.ExtensionRegistryLite;
/*    12:      */ import com.google.protobuf.GeneratedMessage;
/*    13:      */ import com.google.protobuf.GeneratedMessage.Builder;
/*    14:      */ import com.google.protobuf.GeneratedMessage.BuilderParent;
/*    15:      */ import com.google.protobuf.GeneratedMessage.FieldAccessorTable;
/*    16:      */ import com.google.protobuf.InvalidProtocolBufferException;
/*    17:      */ import com.google.protobuf.LazyStringArrayList;
/*    18:      */ import com.google.protobuf.LazyStringList;
/*    19:      */ import com.google.protobuf.Message;
/*    20:      */ import com.google.protobuf.MessageLite;
/*    21:      */ import com.google.protobuf.MessageOrBuilder;
/*    22:      */ import com.google.protobuf.Parser;
/*    23:      */ import com.google.protobuf.RepeatedFieldBuilder;
/*    24:      */ import com.google.protobuf.SingleFieldBuilder;
/*    25:      */ import com.google.protobuf.UnknownFieldSet;
/*    26:      */ import com.google.protobuf.UnknownFieldSet.Builder;
/*    27:      */ import com.google.protobuf.UnmodifiableLazyStringList;
/*    28:      */ import java.io.IOException;
/*    29:      */ import java.io.InputStream;
/*    30:      */ import java.io.ObjectStreamException;
/*    31:      */ import java.util.ArrayList;
/*    32:      */ import java.util.Collections;
/*    33:      */ import java.util.List;
/*    34:      */ import org.apache.hadoop.security.proto.SecurityProtos;
/*    35:      */ import org.apache.hadoop.yarn.proto.YarnProtos;
/*    36:      */ 
/*    37:      */ public final class MRServiceProtos
/*    38:      */ {
/*    39:      */   private static Descriptors.Descriptor internal_static_hadoop_mapreduce_GetJobReportRequestProto_descriptor;
/*    40:      */   private static GeneratedMessage.FieldAccessorTable internal_static_hadoop_mapreduce_GetJobReportRequestProto_fieldAccessorTable;
/*    41:      */   private static Descriptors.Descriptor internal_static_hadoop_mapreduce_GetJobReportResponseProto_descriptor;
/*    42:      */   private static GeneratedMessage.FieldAccessorTable internal_static_hadoop_mapreduce_GetJobReportResponseProto_fieldAccessorTable;
/*    43:      */   private static Descriptors.Descriptor internal_static_hadoop_mapreduce_GetTaskReportRequestProto_descriptor;
/*    44:      */   private static GeneratedMessage.FieldAccessorTable internal_static_hadoop_mapreduce_GetTaskReportRequestProto_fieldAccessorTable;
/*    45:      */   private static Descriptors.Descriptor internal_static_hadoop_mapreduce_GetTaskReportResponseProto_descriptor;
/*    46:      */   private static GeneratedMessage.FieldAccessorTable internal_static_hadoop_mapreduce_GetTaskReportResponseProto_fieldAccessorTable;
/*    47:      */   private static Descriptors.Descriptor internal_static_hadoop_mapreduce_GetTaskAttemptReportRequestProto_descriptor;
/*    48:      */   private static GeneratedMessage.FieldAccessorTable internal_static_hadoop_mapreduce_GetTaskAttemptReportRequestProto_fieldAccessorTable;
/*    49:      */   private static Descriptors.Descriptor internal_static_hadoop_mapreduce_GetTaskAttemptReportResponseProto_descriptor;
/*    50:      */   private static GeneratedMessage.FieldAccessorTable internal_static_hadoop_mapreduce_GetTaskAttemptReportResponseProto_fieldAccessorTable;
/*    51:      */   private static Descriptors.Descriptor internal_static_hadoop_mapreduce_GetCountersRequestProto_descriptor;
/*    52:      */   private static GeneratedMessage.FieldAccessorTable internal_static_hadoop_mapreduce_GetCountersRequestProto_fieldAccessorTable;
/*    53:      */   private static Descriptors.Descriptor internal_static_hadoop_mapreduce_GetCountersResponseProto_descriptor;
/*    54:      */   private static GeneratedMessage.FieldAccessorTable internal_static_hadoop_mapreduce_GetCountersResponseProto_fieldAccessorTable;
/*    55:      */   private static Descriptors.Descriptor internal_static_hadoop_mapreduce_GetTaskAttemptCompletionEventsRequestProto_descriptor;
/*    56:      */   private static GeneratedMessage.FieldAccessorTable internal_static_hadoop_mapreduce_GetTaskAttemptCompletionEventsRequestProto_fieldAccessorTable;
/*    57:      */   private static Descriptors.Descriptor internal_static_hadoop_mapreduce_GetTaskAttemptCompletionEventsResponseProto_descriptor;
/*    58:      */   private static GeneratedMessage.FieldAccessorTable internal_static_hadoop_mapreduce_GetTaskAttemptCompletionEventsResponseProto_fieldAccessorTable;
/*    59:      */   private static Descriptors.Descriptor internal_static_hadoop_mapreduce_GetTaskReportsRequestProto_descriptor;
/*    60:      */   private static GeneratedMessage.FieldAccessorTable internal_static_hadoop_mapreduce_GetTaskReportsRequestProto_fieldAccessorTable;
/*    61:      */   private static Descriptors.Descriptor internal_static_hadoop_mapreduce_GetTaskReportsResponseProto_descriptor;
/*    62:      */   private static GeneratedMessage.FieldAccessorTable internal_static_hadoop_mapreduce_GetTaskReportsResponseProto_fieldAccessorTable;
/*    63:      */   private static Descriptors.Descriptor internal_static_hadoop_mapreduce_GetDiagnosticsRequestProto_descriptor;
/*    64:      */   private static GeneratedMessage.FieldAccessorTable internal_static_hadoop_mapreduce_GetDiagnosticsRequestProto_fieldAccessorTable;
/*    65:      */   private static Descriptors.Descriptor internal_static_hadoop_mapreduce_GetDiagnosticsResponseProto_descriptor;
/*    66:      */   private static GeneratedMessage.FieldAccessorTable internal_static_hadoop_mapreduce_GetDiagnosticsResponseProto_fieldAccessorTable;
/*    67:      */   private static Descriptors.Descriptor internal_static_hadoop_mapreduce_KillJobRequestProto_descriptor;
/*    68:      */   private static GeneratedMessage.FieldAccessorTable internal_static_hadoop_mapreduce_KillJobRequestProto_fieldAccessorTable;
/*    69:      */   private static Descriptors.Descriptor internal_static_hadoop_mapreduce_KillJobResponseProto_descriptor;
/*    70:      */   private static GeneratedMessage.FieldAccessorTable internal_static_hadoop_mapreduce_KillJobResponseProto_fieldAccessorTable;
/*    71:      */   private static Descriptors.Descriptor internal_static_hadoop_mapreduce_KillTaskRequestProto_descriptor;
/*    72:      */   private static GeneratedMessage.FieldAccessorTable internal_static_hadoop_mapreduce_KillTaskRequestProto_fieldAccessorTable;
/*    73:      */   private static Descriptors.Descriptor internal_static_hadoop_mapreduce_KillTaskResponseProto_descriptor;
/*    74:      */   private static GeneratedMessage.FieldAccessorTable internal_static_hadoop_mapreduce_KillTaskResponseProto_fieldAccessorTable;
/*    75:      */   private static Descriptors.Descriptor internal_static_hadoop_mapreduce_KillTaskAttemptRequestProto_descriptor;
/*    76:      */   private static GeneratedMessage.FieldAccessorTable internal_static_hadoop_mapreduce_KillTaskAttemptRequestProto_fieldAccessorTable;
/*    77:      */   private static Descriptors.Descriptor internal_static_hadoop_mapreduce_KillTaskAttemptResponseProto_descriptor;
/*    78:      */   private static GeneratedMessage.FieldAccessorTable internal_static_hadoop_mapreduce_KillTaskAttemptResponseProto_fieldAccessorTable;
/*    79:      */   private static Descriptors.Descriptor internal_static_hadoop_mapreduce_FailTaskAttemptRequestProto_descriptor;
/*    80:      */   private static GeneratedMessage.FieldAccessorTable internal_static_hadoop_mapreduce_FailTaskAttemptRequestProto_fieldAccessorTable;
/*    81:      */   private static Descriptors.Descriptor internal_static_hadoop_mapreduce_FailTaskAttemptResponseProto_descriptor;
/*    82:      */   private static GeneratedMessage.FieldAccessorTable internal_static_hadoop_mapreduce_FailTaskAttemptResponseProto_fieldAccessorTable;
/*    83:      */   private static Descriptors.FileDescriptor descriptor;
/*    84:      */   public static void registerAllExtensions(ExtensionRegistry registry) {}
/*    85:      */   
/*    86:      */   public static abstract interface GetJobReportRequestProtoOrBuilder
/*    87:      */     extends MessageOrBuilder
/*    88:      */   {
/*    89:      */     public abstract boolean hasJobId();
/*    90:      */     
/*    91:      */     public abstract MRProtos.JobIdProto getJobId();
/*    92:      */     
/*    93:      */     public abstract MRProtos.JobIdProtoOrBuilder getJobIdOrBuilder();
/*    94:      */   }
/*    95:      */   
/*    96:      */   public static final class GetJobReportRequestProto
/*    97:      */     extends GeneratedMessage
/*    98:      */     implements MRServiceProtos.GetJobReportRequestProtoOrBuilder
/*    99:      */   {
/*   100:      */     private static final GetJobReportRequestProto defaultInstance;
/*   101:      */     private final UnknownFieldSet unknownFields;
/*   102:      */     
/*   103:      */     private GetJobReportRequestProto(GeneratedMessage.Builder<?> builder)
/*   104:      */     {
/*   105:   36 */       super();
/*   106:   37 */       this.unknownFields = builder.getUnknownFields();
/*   107:      */     }
/*   108:      */     
/*   109:      */     private GetJobReportRequestProto(boolean noInit)
/*   110:      */     {
/*   111:   39 */       this.unknownFields = UnknownFieldSet.getDefaultInstance();
/*   112:      */     }
/*   113:      */     
/*   114:      */     public static GetJobReportRequestProto getDefaultInstance()
/*   115:      */     {
/*   116:   43 */       return defaultInstance;
/*   117:      */     }
/*   118:      */     
/*   119:      */     public GetJobReportRequestProto getDefaultInstanceForType()
/*   120:      */     {
/*   121:   47 */       return defaultInstance;
/*   122:      */     }
/*   123:      */     
/*   124:      */     public final UnknownFieldSet getUnknownFields()
/*   125:      */     {
/*   126:   54 */       return this.unknownFields;
/*   127:      */     }
/*   128:      */     
/*   129:      */     private GetJobReportRequestProto(CodedInputStream input, ExtensionRegistryLite extensionRegistry)
/*   130:      */       throws InvalidProtocolBufferException
/*   131:      */     {
/*   132:   60 */       initFields();
/*   133:   61 */       int mutable_bitField0_ = 0;
/*   134:   62 */       UnknownFieldSet.Builder unknownFields = UnknownFieldSet.newBuilder();
/*   135:      */       try
/*   136:      */       {
/*   137:   65 */         boolean done = false;
/*   138:   66 */         while (!done)
/*   139:      */         {
/*   140:   67 */           int tag = input.readTag();
/*   141:   68 */           switch (tag)
/*   142:      */           {
/*   143:      */           case 0: 
/*   144:   70 */             done = true;
/*   145:   71 */             break;
/*   146:      */           default: 
/*   147:   73 */             if (!parseUnknownField(input, unknownFields, extensionRegistry, tag)) {
/*   148:   75 */               done = true;
/*   149:      */             }
/*   150:      */             break;
/*   151:      */           case 10: 
/*   152:   80 */             MRProtos.JobIdProto.Builder subBuilder = null;
/*   153:   81 */             if ((this.bitField0_ & 0x1) == 1) {
/*   154:   82 */               subBuilder = this.jobId_.toBuilder();
/*   155:      */             }
/*   156:   84 */             this.jobId_ = ((MRProtos.JobIdProto)input.readMessage(MRProtos.JobIdProto.PARSER, extensionRegistry));
/*   157:   85 */             if (subBuilder != null)
/*   158:      */             {
/*   159:   86 */               subBuilder.mergeFrom(this.jobId_);
/*   160:   87 */               this.jobId_ = subBuilder.buildPartial();
/*   161:      */             }
/*   162:   89 */             this.bitField0_ |= 0x1;
/*   163:      */           }
/*   164:      */         }
/*   165:      */       }
/*   166:      */       catch (InvalidProtocolBufferException e)
/*   167:      */       {
/*   168:   95 */         throw e.setUnfinishedMessage(this);
/*   169:      */       }
/*   170:      */       catch (IOException e)
/*   171:      */       {
/*   172:   97 */         throw new InvalidProtocolBufferException(e.getMessage()).setUnfinishedMessage(this);
/*   173:      */       }
/*   174:      */       finally
/*   175:      */       {
/*   176:  100 */         this.unknownFields = unknownFields.build();
/*   177:  101 */         makeExtensionsImmutable();
/*   178:      */       }
/*   179:      */     }
/*   180:      */     
/*   181:      */     public static final Descriptors.Descriptor getDescriptor()
/*   182:      */     {
/*   183:  106 */       return MRServiceProtos.internal_static_hadoop_mapreduce_GetJobReportRequestProto_descriptor;
/*   184:      */     }
/*   185:      */     
/*   186:      */     protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable()
/*   187:      */     {
/*   188:  111 */       return MRServiceProtos.internal_static_hadoop_mapreduce_GetJobReportRequestProto_fieldAccessorTable.ensureFieldAccessorsInitialized(GetJobReportRequestProto.class, Builder.class);
/*   189:      */     }
/*   190:      */     
/*   191:  116 */     public static Parser<GetJobReportRequestProto> PARSER = new AbstractParser()
/*   192:      */     {
/*   193:      */       public MRServiceProtos.GetJobReportRequestProto parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry)
/*   194:      */         throws InvalidProtocolBufferException
/*   195:      */       {
/*   196:  122 */         return new MRServiceProtos.GetJobReportRequestProto(input, extensionRegistry, null);
/*   197:      */       }
/*   198:      */     };
/*   199:      */     private int bitField0_;
/*   200:      */     public static final int JOB_ID_FIELD_NUMBER = 1;
/*   201:      */     private MRProtos.JobIdProto jobId_;
/*   202:      */     
/*   203:      */     public Parser<GetJobReportRequestProto> getParserForType()
/*   204:      */     {
/*   205:  128 */       return PARSER;
/*   206:      */     }
/*   207:      */     
/*   208:      */     public boolean hasJobId()
/*   209:      */     {
/*   210:  139 */       return (this.bitField0_ & 0x1) == 1;
/*   211:      */     }
/*   212:      */     
/*   213:      */     public MRProtos.JobIdProto getJobId()
/*   214:      */     {
/*   215:  145 */       return this.jobId_;
/*   216:      */     }
/*   217:      */     
/*   218:      */     public MRProtos.JobIdProtoOrBuilder getJobIdOrBuilder()
/*   219:      */     {
/*   220:  151 */       return this.jobId_;
/*   221:      */     }
/*   222:      */     
/*   223:      */     private void initFields()
/*   224:      */     {
/*   225:  155 */       this.jobId_ = MRProtos.JobIdProto.getDefaultInstance();
/*   226:      */     }
/*   227:      */     
/*   228:  157 */     private byte memoizedIsInitialized = -1;
/*   229:      */     
/*   230:      */     public final boolean isInitialized()
/*   231:      */     {
/*   232:  159 */       byte isInitialized = this.memoizedIsInitialized;
/*   233:  160 */       if (isInitialized != -1) {
/*   234:  160 */         return isInitialized == 1;
/*   235:      */       }
/*   236:  162 */       this.memoizedIsInitialized = 1;
/*   237:  163 */       return true;
/*   238:      */     }
/*   239:      */     
/*   240:      */     public void writeTo(CodedOutputStream output)
/*   241:      */       throws IOException
/*   242:      */     {
/*   243:  168 */       getSerializedSize();
/*   244:  169 */       if ((this.bitField0_ & 0x1) == 1) {
/*   245:  170 */         output.writeMessage(1, this.jobId_);
/*   246:      */       }
/*   247:  172 */       getUnknownFields().writeTo(output);
/*   248:      */     }
/*   249:      */     
/*   250:  175 */     private int memoizedSerializedSize = -1;
/*   251:      */     private static final long serialVersionUID = 0L;
/*   252:      */     
/*   253:      */     public int getSerializedSize()
/*   254:      */     {
/*   255:  177 */       int size = this.memoizedSerializedSize;
/*   256:  178 */       if (size != -1) {
/*   257:  178 */         return size;
/*   258:      */       }
/*   259:  180 */       size = 0;
/*   260:  181 */       if ((this.bitField0_ & 0x1) == 1) {
/*   261:  182 */         size += CodedOutputStream.computeMessageSize(1, this.jobId_);
/*   262:      */       }
/*   263:  185 */       size += getUnknownFields().getSerializedSize();
/*   264:  186 */       this.memoizedSerializedSize = size;
/*   265:  187 */       return size;
/*   266:      */     }
/*   267:      */     
/*   268:      */     protected Object writeReplace()
/*   269:      */       throws ObjectStreamException
/*   270:      */     {
/*   271:  194 */       return super.writeReplace();
/*   272:      */     }
/*   273:      */     
/*   274:      */     public boolean equals(Object obj)
/*   275:      */     {
/*   276:  199 */       if (obj == this) {
/*   277:  200 */         return true;
/*   278:      */       }
/*   279:  202 */       if (!(obj instanceof GetJobReportRequestProto)) {
/*   280:  203 */         return super.equals(obj);
/*   281:      */       }
/*   282:  205 */       GetJobReportRequestProto other = (GetJobReportRequestProto)obj;
/*   283:      */       
/*   284:  207 */       boolean result = true;
/*   285:  208 */       result = (result) && (hasJobId() == other.hasJobId());
/*   286:  209 */       if (hasJobId()) {
/*   287:  210 */         result = (result) && (getJobId().equals(other.getJobId()));
/*   288:      */       }
/*   289:  213 */       result = (result) && (getUnknownFields().equals(other.getUnknownFields()));
/*   290:      */       
/*   291:  215 */       return result;
/*   292:      */     }
/*   293:      */     
/*   294:  218 */     private int memoizedHashCode = 0;
/*   295:      */     
/*   296:      */     public int hashCode()
/*   297:      */     {
/*   298:  221 */       if (this.memoizedHashCode != 0) {
/*   299:  222 */         return this.memoizedHashCode;
/*   300:      */       }
/*   301:  224 */       int hash = 41;
/*   302:  225 */       hash = 19 * hash + getDescriptorForType().hashCode();
/*   303:  226 */       if (hasJobId())
/*   304:      */       {
/*   305:  227 */         hash = 37 * hash + 1;
/*   306:  228 */         hash = 53 * hash + getJobId().hashCode();
/*   307:      */       }
/*   308:  230 */       hash = 29 * hash + getUnknownFields().hashCode();
/*   309:  231 */       this.memoizedHashCode = hash;
/*   310:  232 */       return hash;
/*   311:      */     }
/*   312:      */     
/*   313:      */     public static GetJobReportRequestProto parseFrom(ByteString data)
/*   314:      */       throws InvalidProtocolBufferException
/*   315:      */     {
/*   316:  238 */       return (GetJobReportRequestProto)PARSER.parseFrom(data);
/*   317:      */     }
/*   318:      */     
/*   319:      */     public static GetJobReportRequestProto parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry)
/*   320:      */       throws InvalidProtocolBufferException
/*   321:      */     {
/*   322:  244 */       return (GetJobReportRequestProto)PARSER.parseFrom(data, extensionRegistry);
/*   323:      */     }
/*   324:      */     
/*   325:      */     public static GetJobReportRequestProto parseFrom(byte[] data)
/*   326:      */       throws InvalidProtocolBufferException
/*   327:      */     {
/*   328:  248 */       return (GetJobReportRequestProto)PARSER.parseFrom(data);
/*   329:      */     }
/*   330:      */     
/*   331:      */     public static GetJobReportRequestProto parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry)
/*   332:      */       throws InvalidProtocolBufferException
/*   333:      */     {
/*   334:  254 */       return (GetJobReportRequestProto)PARSER.parseFrom(data, extensionRegistry);
/*   335:      */     }
/*   336:      */     
/*   337:      */     public static GetJobReportRequestProto parseFrom(InputStream input)
/*   338:      */       throws IOException
/*   339:      */     {
/*   340:  258 */       return (GetJobReportRequestProto)PARSER.parseFrom(input);
/*   341:      */     }
/*   342:      */     
/*   343:      */     public static GetJobReportRequestProto parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry)
/*   344:      */       throws IOException
/*   345:      */     {
/*   346:  264 */       return (GetJobReportRequestProto)PARSER.parseFrom(input, extensionRegistry);
/*   347:      */     }
/*   348:      */     
/*   349:      */     public static GetJobReportRequestProto parseDelimitedFrom(InputStream input)
/*   350:      */       throws IOException
/*   351:      */     {
/*   352:  268 */       return (GetJobReportRequestProto)PARSER.parseDelimitedFrom(input);
/*   353:      */     }
/*   354:      */     
/*   355:      */     public static GetJobReportRequestProto parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry)
/*   356:      */       throws IOException
/*   357:      */     {
/*   358:  274 */       return (GetJobReportRequestProto)PARSER.parseDelimitedFrom(input, extensionRegistry);
/*   359:      */     }
/*   360:      */     
/*   361:      */     public static GetJobReportRequestProto parseFrom(CodedInputStream input)
/*   362:      */       throws IOException
/*   363:      */     {
/*   364:  279 */       return (GetJobReportRequestProto)PARSER.parseFrom(input);
/*   365:      */     }
/*   366:      */     
/*   367:      */     public static GetJobReportRequestProto parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry)
/*   368:      */       throws IOException
/*   369:      */     {
/*   370:  285 */       return (GetJobReportRequestProto)PARSER.parseFrom(input, extensionRegistry);
/*   371:      */     }
/*   372:      */     
/*   373:      */     public static Builder newBuilder()
/*   374:      */     {
/*   375:  288 */       return Builder.access$300();
/*   376:      */     }
/*   377:      */     
/*   378:      */     public Builder newBuilderForType()
/*   379:      */     {
/*   380:  289 */       return newBuilder();
/*   381:      */     }
/*   382:      */     
/*   383:      */     public static Builder newBuilder(GetJobReportRequestProto prototype)
/*   384:      */     {
/*   385:  291 */       return newBuilder().mergeFrom(prototype);
/*   386:      */     }
/*   387:      */     
/*   388:      */     public Builder toBuilder()
/*   389:      */     {
/*   390:  293 */       return newBuilder(this);
/*   391:      */     }
/*   392:      */     
/*   393:      */     protected Builder newBuilderForType(GeneratedMessage.BuilderParent parent)
/*   394:      */     {
/*   395:  298 */       Builder builder = new Builder(parent, null);
/*   396:  299 */       return builder;
/*   397:      */     }
/*   398:      */     
/*   399:      */     public static final class Builder
/*   400:      */       extends GeneratedMessage.Builder<Builder>
/*   401:      */       implements MRServiceProtos.GetJobReportRequestProtoOrBuilder
/*   402:      */     {
/*   403:      */       private int bitField0_;
/*   404:      */       
/*   405:      */       public static final Descriptors.Descriptor getDescriptor()
/*   406:      */       {
/*   407:  309 */         return MRServiceProtos.internal_static_hadoop_mapreduce_GetJobReportRequestProto_descriptor;
/*   408:      */       }
/*   409:      */       
/*   410:      */       protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable()
/*   411:      */       {
/*   412:  314 */         return MRServiceProtos.internal_static_hadoop_mapreduce_GetJobReportRequestProto_fieldAccessorTable.ensureFieldAccessorsInitialized(MRServiceProtos.GetJobReportRequestProto.class, Builder.class);
/*   413:      */       }
/*   414:      */       
/*   415:      */       private Builder()
/*   416:      */       {
/*   417:  321 */         maybeForceBuilderInitialization();
/*   418:      */       }
/*   419:      */       
/*   420:      */       private Builder(GeneratedMessage.BuilderParent parent)
/*   421:      */       {
/*   422:  326 */         super();
/*   423:  327 */         maybeForceBuilderInitialization();
/*   424:      */       }
/*   425:      */       
/*   426:      */       private void maybeForceBuilderInitialization()
/*   427:      */       {
/*   428:  330 */         if (MRServiceProtos.GetJobReportRequestProto.alwaysUseFieldBuilders) {
/*   429:  331 */           getJobIdFieldBuilder();
/*   430:      */         }
/*   431:      */       }
/*   432:      */       
/*   433:      */       private static Builder create()
/*   434:      */       {
/*   435:  335 */         return new Builder();
/*   436:      */       }
/*   437:      */       
/*   438:      */       public Builder clear()
/*   439:      */       {
/*   440:  339 */         super.clear();
/*   441:  340 */         if (this.jobIdBuilder_ == null) {
/*   442:  341 */           this.jobId_ = MRProtos.JobIdProto.getDefaultInstance();
/*   443:      */         } else {
/*   444:  343 */           this.jobIdBuilder_.clear();
/*   445:      */         }
/*   446:  345 */         this.bitField0_ &= 0xFFFFFFFE;
/*   447:  346 */         return this;
/*   448:      */       }
/*   449:      */       
/*   450:      */       public Builder clone()
/*   451:      */       {
/*   452:  350 */         return create().mergeFrom(buildPartial());
/*   453:      */       }
/*   454:      */       
/*   455:      */       public Descriptors.Descriptor getDescriptorForType()
/*   456:      */       {
/*   457:  355 */         return MRServiceProtos.internal_static_hadoop_mapreduce_GetJobReportRequestProto_descriptor;
/*   458:      */       }
/*   459:      */       
/*   460:      */       public MRServiceProtos.GetJobReportRequestProto getDefaultInstanceForType()
/*   461:      */       {
/*   462:  359 */         return MRServiceProtos.GetJobReportRequestProto.getDefaultInstance();
/*   463:      */       }
/*   464:      */       
/*   465:      */       public MRServiceProtos.GetJobReportRequestProto build()
/*   466:      */       {
/*   467:  363 */         MRServiceProtos.GetJobReportRequestProto result = buildPartial();
/*   468:  364 */         if (!result.isInitialized()) {
/*   469:  365 */           throw newUninitializedMessageException(result);
/*   470:      */         }
/*   471:  367 */         return result;
/*   472:      */       }
/*   473:      */       
/*   474:      */       public MRServiceProtos.GetJobReportRequestProto buildPartial()
/*   475:      */       {
/*   476:  371 */         MRServiceProtos.GetJobReportRequestProto result = new MRServiceProtos.GetJobReportRequestProto(this, null);
/*   477:  372 */         int from_bitField0_ = this.bitField0_;
/*   478:  373 */         int to_bitField0_ = 0;
/*   479:  374 */         if ((from_bitField0_ & 0x1) == 1) {
/*   480:  375 */           to_bitField0_ |= 0x1;
/*   481:      */         }
/*   482:  377 */         if (this.jobIdBuilder_ == null) {
/*   483:  378 */           result.jobId_ = this.jobId_;
/*   484:      */         } else {
/*   485:  380 */           result.jobId_ = ((MRProtos.JobIdProto)this.jobIdBuilder_.build());
/*   486:      */         }
/*   487:  382 */         result.bitField0_ = to_bitField0_;
/*   488:  383 */         onBuilt();
/*   489:  384 */         return result;
/*   490:      */       }
/*   491:      */       
/*   492:      */       public Builder mergeFrom(Message other)
/*   493:      */       {
/*   494:  388 */         if ((other instanceof MRServiceProtos.GetJobReportRequestProto)) {
/*   495:  389 */           return mergeFrom((MRServiceProtos.GetJobReportRequestProto)other);
/*   496:      */         }
/*   497:  391 */         super.mergeFrom(other);
/*   498:  392 */         return this;
/*   499:      */       }
/*   500:      */       
/*   501:      */       public Builder mergeFrom(MRServiceProtos.GetJobReportRequestProto other)
/*   502:      */       {
/*   503:  397 */         if (other == MRServiceProtos.GetJobReportRequestProto.getDefaultInstance()) {
/*   504:  397 */           return this;
/*   505:      */         }
/*   506:  398 */         if (other.hasJobId()) {
/*   507:  399 */           mergeJobId(other.getJobId());
/*   508:      */         }
/*   509:  401 */         mergeUnknownFields(other.getUnknownFields());
/*   510:  402 */         return this;
/*   511:      */       }
/*   512:      */       
/*   513:      */       public final boolean isInitialized()
/*   514:      */       {
/*   515:  406 */         return true;
/*   516:      */       }
/*   517:      */       
/*   518:      */       public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry)
/*   519:      */         throws IOException
/*   520:      */       {
/*   521:  413 */         MRServiceProtos.GetJobReportRequestProto parsedMessage = null;
/*   522:      */         try
/*   523:      */         {
/*   524:  415 */           parsedMessage = (MRServiceProtos.GetJobReportRequestProto)MRServiceProtos.GetJobReportRequestProto.PARSER.parsePartialFrom(input, extensionRegistry);
/*   525:      */         }
/*   526:      */         catch (InvalidProtocolBufferException e)
/*   527:      */         {
/*   528:  417 */           parsedMessage = (MRServiceProtos.GetJobReportRequestProto)e.getUnfinishedMessage();
/*   529:  418 */           throw e;
/*   530:      */         }
/*   531:      */         finally
/*   532:      */         {
/*   533:  420 */           if (parsedMessage != null) {
/*   534:  421 */             mergeFrom(parsedMessage);
/*   535:      */           }
/*   536:      */         }
/*   537:  424 */         return this;
/*   538:      */       }
/*   539:      */       
/*   540:  429 */       private MRProtos.JobIdProto jobId_ = MRProtos.JobIdProto.getDefaultInstance();
/*   541:      */       private SingleFieldBuilder<MRProtos.JobIdProto, MRProtos.JobIdProto.Builder, MRProtos.JobIdProtoOrBuilder> jobIdBuilder_;
/*   542:      */       
/*   543:      */       public boolean hasJobId()
/*   544:      */       {
/*   545:  436 */         return (this.bitField0_ & 0x1) == 1;
/*   546:      */       }
/*   547:      */       
/*   548:      */       public MRProtos.JobIdProto getJobId()
/*   549:      */       {
/*   550:  442 */         if (this.jobIdBuilder_ == null) {
/*   551:  443 */           return this.jobId_;
/*   552:      */         }
/*   553:  445 */         return (MRProtos.JobIdProto)this.jobIdBuilder_.getMessage();
/*   554:      */       }
/*   555:      */       
/*   556:      */       public Builder setJobId(MRProtos.JobIdProto value)
/*   557:      */       {
/*   558:  452 */         if (this.jobIdBuilder_ == null)
/*   559:      */         {
/*   560:  453 */           if (value == null) {
/*   561:  454 */             throw new NullPointerException();
/*   562:      */           }
/*   563:  456 */           this.jobId_ = value;
/*   564:  457 */           onChanged();
/*   565:      */         }
/*   566:      */         else
/*   567:      */         {
/*   568:  459 */           this.jobIdBuilder_.setMessage(value);
/*   569:      */         }
/*   570:  461 */         this.bitField0_ |= 0x1;
/*   571:  462 */         return this;
/*   572:      */       }
/*   573:      */       
/*   574:      */       public Builder setJobId(MRProtos.JobIdProto.Builder builderForValue)
/*   575:      */       {
/*   576:  469 */         if (this.jobIdBuilder_ == null)
/*   577:      */         {
/*   578:  470 */           this.jobId_ = builderForValue.build();
/*   579:  471 */           onChanged();
/*   580:      */         }
/*   581:      */         else
/*   582:      */         {
/*   583:  473 */           this.jobIdBuilder_.setMessage(builderForValue.build());
/*   584:      */         }
/*   585:  475 */         this.bitField0_ |= 0x1;
/*   586:  476 */         return this;
/*   587:      */       }
/*   588:      */       
/*   589:      */       public Builder mergeJobId(MRProtos.JobIdProto value)
/*   590:      */       {
/*   591:  482 */         if (this.jobIdBuilder_ == null)
/*   592:      */         {
/*   593:  483 */           if (((this.bitField0_ & 0x1) == 1) && (this.jobId_ != MRProtos.JobIdProto.getDefaultInstance())) {
/*   594:  485 */             this.jobId_ = MRProtos.JobIdProto.newBuilder(this.jobId_).mergeFrom(value).buildPartial();
/*   595:      */           } else {
/*   596:  488 */             this.jobId_ = value;
/*   597:      */           }
/*   598:  490 */           onChanged();
/*   599:      */         }
/*   600:      */         else
/*   601:      */         {
/*   602:  492 */           this.jobIdBuilder_.mergeFrom(value);
/*   603:      */         }
/*   604:  494 */         this.bitField0_ |= 0x1;
/*   605:  495 */         return this;
/*   606:      */       }
/*   607:      */       
/*   608:      */       public Builder clearJobId()
/*   609:      */       {
/*   610:  501 */         if (this.jobIdBuilder_ == null)
/*   611:      */         {
/*   612:  502 */           this.jobId_ = MRProtos.JobIdProto.getDefaultInstance();
/*   613:  503 */           onChanged();
/*   614:      */         }
/*   615:      */         else
/*   616:      */         {
/*   617:  505 */           this.jobIdBuilder_.clear();
/*   618:      */         }
/*   619:  507 */         this.bitField0_ &= 0xFFFFFFFE;
/*   620:  508 */         return this;
/*   621:      */       }
/*   622:      */       
/*   623:      */       public MRProtos.JobIdProto.Builder getJobIdBuilder()
/*   624:      */       {
/*   625:  514 */         this.bitField0_ |= 0x1;
/*   626:  515 */         onChanged();
/*   627:  516 */         return (MRProtos.JobIdProto.Builder)getJobIdFieldBuilder().getBuilder();
/*   628:      */       }
/*   629:      */       
/*   630:      */       public MRProtos.JobIdProtoOrBuilder getJobIdOrBuilder()
/*   631:      */       {
/*   632:  522 */         if (this.jobIdBuilder_ != null) {
/*   633:  523 */           return (MRProtos.JobIdProtoOrBuilder)this.jobIdBuilder_.getMessageOrBuilder();
/*   634:      */         }
/*   635:  525 */         return this.jobId_;
/*   636:      */       }
/*   637:      */       
/*   638:      */       private SingleFieldBuilder<MRProtos.JobIdProto, MRProtos.JobIdProto.Builder, MRProtos.JobIdProtoOrBuilder> getJobIdFieldBuilder()
/*   639:      */       {
/*   640:  534 */         if (this.jobIdBuilder_ == null)
/*   641:      */         {
/*   642:  535 */           this.jobIdBuilder_ = new SingleFieldBuilder(this.jobId_, getParentForChildren(), isClean());
/*   643:      */           
/*   644:      */ 
/*   645:      */ 
/*   646:      */ 
/*   647:  540 */           this.jobId_ = null;
/*   648:      */         }
/*   649:  542 */         return this.jobIdBuilder_;
/*   650:      */       }
/*   651:      */     }
/*   652:      */     
/*   653:      */     static
/*   654:      */     {
/*   655:  549 */       defaultInstance = new GetJobReportRequestProto(true);
/*   656:  550 */       defaultInstance.initFields();
/*   657:      */     }
/*   658:      */   }
/*   659:      */   
/*   660:      */   public static abstract interface GetJobReportResponseProtoOrBuilder
/*   661:      */     extends MessageOrBuilder
/*   662:      */   {
/*   663:      */     public abstract boolean hasJobReport();
/*   664:      */     
/*   665:      */     public abstract MRProtos.JobReportProto getJobReport();
/*   666:      */     
/*   667:      */     public abstract MRProtos.JobReportProtoOrBuilder getJobReportOrBuilder();
/*   668:      */   }
/*   669:      */   
/*   670:      */   public static final class GetJobReportResponseProto
/*   671:      */     extends GeneratedMessage
/*   672:      */     implements MRServiceProtos.GetJobReportResponseProtoOrBuilder
/*   673:      */   {
/*   674:      */     private static final GetJobReportResponseProto defaultInstance;
/*   675:      */     private final UnknownFieldSet unknownFields;
/*   676:      */     
/*   677:      */     private GetJobReportResponseProto(GeneratedMessage.Builder<?> builder)
/*   678:      */     {
/*   679:  581 */       super();
/*   680:  582 */       this.unknownFields = builder.getUnknownFields();
/*   681:      */     }
/*   682:      */     
/*   683:      */     private GetJobReportResponseProto(boolean noInit)
/*   684:      */     {
/*   685:  584 */       this.unknownFields = UnknownFieldSet.getDefaultInstance();
/*   686:      */     }
/*   687:      */     
/*   688:      */     public static GetJobReportResponseProto getDefaultInstance()
/*   689:      */     {
/*   690:  588 */       return defaultInstance;
/*   691:      */     }
/*   692:      */     
/*   693:      */     public GetJobReportResponseProto getDefaultInstanceForType()
/*   694:      */     {
/*   695:  592 */       return defaultInstance;
/*   696:      */     }
/*   697:      */     
/*   698:      */     public final UnknownFieldSet getUnknownFields()
/*   699:      */     {
/*   700:  599 */       return this.unknownFields;
/*   701:      */     }
/*   702:      */     
/*   703:      */     private GetJobReportResponseProto(CodedInputStream input, ExtensionRegistryLite extensionRegistry)
/*   704:      */       throws InvalidProtocolBufferException
/*   705:      */     {
/*   706:  605 */       initFields();
/*   707:  606 */       int mutable_bitField0_ = 0;
/*   708:  607 */       UnknownFieldSet.Builder unknownFields = UnknownFieldSet.newBuilder();
/*   709:      */       try
/*   710:      */       {
/*   711:  610 */         boolean done = false;
/*   712:  611 */         while (!done)
/*   713:      */         {
/*   714:  612 */           int tag = input.readTag();
/*   715:  613 */           switch (tag)
/*   716:      */           {
/*   717:      */           case 0: 
/*   718:  615 */             done = true;
/*   719:  616 */             break;
/*   720:      */           default: 
/*   721:  618 */             if (!parseUnknownField(input, unknownFields, extensionRegistry, tag)) {
/*   722:  620 */               done = true;
/*   723:      */             }
/*   724:      */             break;
/*   725:      */           case 10: 
/*   726:  625 */             MRProtos.JobReportProto.Builder subBuilder = null;
/*   727:  626 */             if ((this.bitField0_ & 0x1) == 1) {
/*   728:  627 */               subBuilder = this.jobReport_.toBuilder();
/*   729:      */             }
/*   730:  629 */             this.jobReport_ = ((MRProtos.JobReportProto)input.readMessage(MRProtos.JobReportProto.PARSER, extensionRegistry));
/*   731:  630 */             if (subBuilder != null)
/*   732:      */             {
/*   733:  631 */               subBuilder.mergeFrom(this.jobReport_);
/*   734:  632 */               this.jobReport_ = subBuilder.buildPartial();
/*   735:      */             }
/*   736:  634 */             this.bitField0_ |= 0x1;
/*   737:      */           }
/*   738:      */         }
/*   739:      */       }
/*   740:      */       catch (InvalidProtocolBufferException e)
/*   741:      */       {
/*   742:  640 */         throw e.setUnfinishedMessage(this);
/*   743:      */       }
/*   744:      */       catch (IOException e)
/*   745:      */       {
/*   746:  642 */         throw new InvalidProtocolBufferException(e.getMessage()).setUnfinishedMessage(this);
/*   747:      */       }
/*   748:      */       finally
/*   749:      */       {
/*   750:  645 */         this.unknownFields = unknownFields.build();
/*   751:  646 */         makeExtensionsImmutable();
/*   752:      */       }
/*   753:      */     }
/*   754:      */     
/*   755:      */     public static final Descriptors.Descriptor getDescriptor()
/*   756:      */     {
/*   757:  651 */       return MRServiceProtos.internal_static_hadoop_mapreduce_GetJobReportResponseProto_descriptor;
/*   758:      */     }
/*   759:      */     
/*   760:      */     protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable()
/*   761:      */     {
/*   762:  656 */       return MRServiceProtos.internal_static_hadoop_mapreduce_GetJobReportResponseProto_fieldAccessorTable.ensureFieldAccessorsInitialized(GetJobReportResponseProto.class, Builder.class);
/*   763:      */     }
/*   764:      */     
/*   765:  661 */     public static Parser<GetJobReportResponseProto> PARSER = new AbstractParser()
/*   766:      */     {
/*   767:      */       public MRServiceProtos.GetJobReportResponseProto parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry)
/*   768:      */         throws InvalidProtocolBufferException
/*   769:      */       {
/*   770:  667 */         return new MRServiceProtos.GetJobReportResponseProto(input, extensionRegistry, null);
/*   771:      */       }
/*   772:      */     };
/*   773:      */     private int bitField0_;
/*   774:      */     public static final int JOB_REPORT_FIELD_NUMBER = 1;
/*   775:      */     private MRProtos.JobReportProto jobReport_;
/*   776:      */     
/*   777:      */     public Parser<GetJobReportResponseProto> getParserForType()
/*   778:      */     {
/*   779:  673 */       return PARSER;
/*   780:      */     }
/*   781:      */     
/*   782:      */     public boolean hasJobReport()
/*   783:      */     {
/*   784:  684 */       return (this.bitField0_ & 0x1) == 1;
/*   785:      */     }
/*   786:      */     
/*   787:      */     public MRProtos.JobReportProto getJobReport()
/*   788:      */     {
/*   789:  690 */       return this.jobReport_;
/*   790:      */     }
/*   791:      */     
/*   792:      */     public MRProtos.JobReportProtoOrBuilder getJobReportOrBuilder()
/*   793:      */     {
/*   794:  696 */       return this.jobReport_;
/*   795:      */     }
/*   796:      */     
/*   797:      */     private void initFields()
/*   798:      */     {
/*   799:  700 */       this.jobReport_ = MRProtos.JobReportProto.getDefaultInstance();
/*   800:      */     }
/*   801:      */     
/*   802:  702 */     private byte memoizedIsInitialized = -1;
/*   803:      */     
/*   804:      */     public final boolean isInitialized()
/*   805:      */     {
/*   806:  704 */       byte isInitialized = this.memoizedIsInitialized;
/*   807:  705 */       if (isInitialized != -1) {
/*   808:  705 */         return isInitialized == 1;
/*   809:      */       }
/*   810:  707 */       this.memoizedIsInitialized = 1;
/*   811:  708 */       return true;
/*   812:      */     }
/*   813:      */     
/*   814:      */     public void writeTo(CodedOutputStream output)
/*   815:      */       throws IOException
/*   816:      */     {
/*   817:  713 */       getSerializedSize();
/*   818:  714 */       if ((this.bitField0_ & 0x1) == 1) {
/*   819:  715 */         output.writeMessage(1, this.jobReport_);
/*   820:      */       }
/*   821:  717 */       getUnknownFields().writeTo(output);
/*   822:      */     }
/*   823:      */     
/*   824:  720 */     private int memoizedSerializedSize = -1;
/*   825:      */     private static final long serialVersionUID = 0L;
/*   826:      */     
/*   827:      */     public int getSerializedSize()
/*   828:      */     {
/*   829:  722 */       int size = this.memoizedSerializedSize;
/*   830:  723 */       if (size != -1) {
/*   831:  723 */         return size;
/*   832:      */       }
/*   833:  725 */       size = 0;
/*   834:  726 */       if ((this.bitField0_ & 0x1) == 1) {
/*   835:  727 */         size += CodedOutputStream.computeMessageSize(1, this.jobReport_);
/*   836:      */       }
/*   837:  730 */       size += getUnknownFields().getSerializedSize();
/*   838:  731 */       this.memoizedSerializedSize = size;
/*   839:  732 */       return size;
/*   840:      */     }
/*   841:      */     
/*   842:      */     protected Object writeReplace()
/*   843:      */       throws ObjectStreamException
/*   844:      */     {
/*   845:  739 */       return super.writeReplace();
/*   846:      */     }
/*   847:      */     
/*   848:      */     public boolean equals(Object obj)
/*   849:      */     {
/*   850:  744 */       if (obj == this) {
/*   851:  745 */         return true;
/*   852:      */       }
/*   853:  747 */       if (!(obj instanceof GetJobReportResponseProto)) {
/*   854:  748 */         return super.equals(obj);
/*   855:      */       }
/*   856:  750 */       GetJobReportResponseProto other = (GetJobReportResponseProto)obj;
/*   857:      */       
/*   858:  752 */       boolean result = true;
/*   859:  753 */       result = (result) && (hasJobReport() == other.hasJobReport());
/*   860:  754 */       if (hasJobReport()) {
/*   861:  755 */         result = (result) && (getJobReport().equals(other.getJobReport()));
/*   862:      */       }
/*   863:  758 */       result = (result) && (getUnknownFields().equals(other.getUnknownFields()));
/*   864:      */       
/*   865:  760 */       return result;
/*   866:      */     }
/*   867:      */     
/*   868:  763 */     private int memoizedHashCode = 0;
/*   869:      */     
/*   870:      */     public int hashCode()
/*   871:      */     {
/*   872:  766 */       if (this.memoizedHashCode != 0) {
/*   873:  767 */         return this.memoizedHashCode;
/*   874:      */       }
/*   875:  769 */       int hash = 41;
/*   876:  770 */       hash = 19 * hash + getDescriptorForType().hashCode();
/*   877:  771 */       if (hasJobReport())
/*   878:      */       {
/*   879:  772 */         hash = 37 * hash + 1;
/*   880:  773 */         hash = 53 * hash + getJobReport().hashCode();
/*   881:      */       }
/*   882:  775 */       hash = 29 * hash + getUnknownFields().hashCode();
/*   883:  776 */       this.memoizedHashCode = hash;
/*   884:  777 */       return hash;
/*   885:      */     }
/*   886:      */     
/*   887:      */     public static GetJobReportResponseProto parseFrom(ByteString data)
/*   888:      */       throws InvalidProtocolBufferException
/*   889:      */     {
/*   890:  783 */       return (GetJobReportResponseProto)PARSER.parseFrom(data);
/*   891:      */     }
/*   892:      */     
/*   893:      */     public static GetJobReportResponseProto parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry)
/*   894:      */       throws InvalidProtocolBufferException
/*   895:      */     {
/*   896:  789 */       return (GetJobReportResponseProto)PARSER.parseFrom(data, extensionRegistry);
/*   897:      */     }
/*   898:      */     
/*   899:      */     public static GetJobReportResponseProto parseFrom(byte[] data)
/*   900:      */       throws InvalidProtocolBufferException
/*   901:      */     {
/*   902:  793 */       return (GetJobReportResponseProto)PARSER.parseFrom(data);
/*   903:      */     }
/*   904:      */     
/*   905:      */     public static GetJobReportResponseProto parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry)
/*   906:      */       throws InvalidProtocolBufferException
/*   907:      */     {
/*   908:  799 */       return (GetJobReportResponseProto)PARSER.parseFrom(data, extensionRegistry);
/*   909:      */     }
/*   910:      */     
/*   911:      */     public static GetJobReportResponseProto parseFrom(InputStream input)
/*   912:      */       throws IOException
/*   913:      */     {
/*   914:  803 */       return (GetJobReportResponseProto)PARSER.parseFrom(input);
/*   915:      */     }
/*   916:      */     
/*   917:      */     public static GetJobReportResponseProto parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry)
/*   918:      */       throws IOException
/*   919:      */     {
/*   920:  809 */       return (GetJobReportResponseProto)PARSER.parseFrom(input, extensionRegistry);
/*   921:      */     }
/*   922:      */     
/*   923:      */     public static GetJobReportResponseProto parseDelimitedFrom(InputStream input)
/*   924:      */       throws IOException
/*   925:      */     {
/*   926:  813 */       return (GetJobReportResponseProto)PARSER.parseDelimitedFrom(input);
/*   927:      */     }
/*   928:      */     
/*   929:      */     public static GetJobReportResponseProto parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry)
/*   930:      */       throws IOException
/*   931:      */     {
/*   932:  819 */       return (GetJobReportResponseProto)PARSER.parseDelimitedFrom(input, extensionRegistry);
/*   933:      */     }
/*   934:      */     
/*   935:      */     public static GetJobReportResponseProto parseFrom(CodedInputStream input)
/*   936:      */       throws IOException
/*   937:      */     {
/*   938:  824 */       return (GetJobReportResponseProto)PARSER.parseFrom(input);
/*   939:      */     }
/*   940:      */     
/*   941:      */     public static GetJobReportResponseProto parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry)
/*   942:      */       throws IOException
/*   943:      */     {
/*   944:  830 */       return (GetJobReportResponseProto)PARSER.parseFrom(input, extensionRegistry);
/*   945:      */     }
/*   946:      */     
/*   947:      */     public static Builder newBuilder()
/*   948:      */     {
/*   949:  833 */       return Builder.access$1200();
/*   950:      */     }
/*   951:      */     
/*   952:      */     public Builder newBuilderForType()
/*   953:      */     {
/*   954:  834 */       return newBuilder();
/*   955:      */     }
/*   956:      */     
/*   957:      */     public static Builder newBuilder(GetJobReportResponseProto prototype)
/*   958:      */     {
/*   959:  836 */       return newBuilder().mergeFrom(prototype);
/*   960:      */     }
/*   961:      */     
/*   962:      */     public Builder toBuilder()
/*   963:      */     {
/*   964:  838 */       return newBuilder(this);
/*   965:      */     }
/*   966:      */     
/*   967:      */     protected Builder newBuilderForType(GeneratedMessage.BuilderParent parent)
/*   968:      */     {
/*   969:  843 */       Builder builder = new Builder(parent, null);
/*   970:  844 */       return builder;
/*   971:      */     }
/*   972:      */     
/*   973:      */     public static final class Builder
/*   974:      */       extends GeneratedMessage.Builder<Builder>
/*   975:      */       implements MRServiceProtos.GetJobReportResponseProtoOrBuilder
/*   976:      */     {
/*   977:      */       private int bitField0_;
/*   978:      */       
/*   979:      */       public static final Descriptors.Descriptor getDescriptor()
/*   980:      */       {
/*   981:  854 */         return MRServiceProtos.internal_static_hadoop_mapreduce_GetJobReportResponseProto_descriptor;
/*   982:      */       }
/*   983:      */       
/*   984:      */       protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable()
/*   985:      */       {
/*   986:  859 */         return MRServiceProtos.internal_static_hadoop_mapreduce_GetJobReportResponseProto_fieldAccessorTable.ensureFieldAccessorsInitialized(MRServiceProtos.GetJobReportResponseProto.class, Builder.class);
/*   987:      */       }
/*   988:      */       
/*   989:      */       private Builder()
/*   990:      */       {
/*   991:  866 */         maybeForceBuilderInitialization();
/*   992:      */       }
/*   993:      */       
/*   994:      */       private Builder(GeneratedMessage.BuilderParent parent)
/*   995:      */       {
/*   996:  871 */         super();
/*   997:  872 */         maybeForceBuilderInitialization();
/*   998:      */       }
/*   999:      */       
/*  1000:      */       private void maybeForceBuilderInitialization()
/*  1001:      */       {
/*  1002:  875 */         if (MRServiceProtos.GetJobReportResponseProto.alwaysUseFieldBuilders) {
/*  1003:  876 */           getJobReportFieldBuilder();
/*  1004:      */         }
/*  1005:      */       }
/*  1006:      */       
/*  1007:      */       private static Builder create()
/*  1008:      */       {
/*  1009:  880 */         return new Builder();
/*  1010:      */       }
/*  1011:      */       
/*  1012:      */       public Builder clear()
/*  1013:      */       {
/*  1014:  884 */         super.clear();
/*  1015:  885 */         if (this.jobReportBuilder_ == null) {
/*  1016:  886 */           this.jobReport_ = MRProtos.JobReportProto.getDefaultInstance();
/*  1017:      */         } else {
/*  1018:  888 */           this.jobReportBuilder_.clear();
/*  1019:      */         }
/*  1020:  890 */         this.bitField0_ &= 0xFFFFFFFE;
/*  1021:  891 */         return this;
/*  1022:      */       }
/*  1023:      */       
/*  1024:      */       public Builder clone()
/*  1025:      */       {
/*  1026:  895 */         return create().mergeFrom(buildPartial());
/*  1027:      */       }
/*  1028:      */       
/*  1029:      */       public Descriptors.Descriptor getDescriptorForType()
/*  1030:      */       {
/*  1031:  900 */         return MRServiceProtos.internal_static_hadoop_mapreduce_GetJobReportResponseProto_descriptor;
/*  1032:      */       }
/*  1033:      */       
/*  1034:      */       public MRServiceProtos.GetJobReportResponseProto getDefaultInstanceForType()
/*  1035:      */       {
/*  1036:  904 */         return MRServiceProtos.GetJobReportResponseProto.getDefaultInstance();
/*  1037:      */       }
/*  1038:      */       
/*  1039:      */       public MRServiceProtos.GetJobReportResponseProto build()
/*  1040:      */       {
/*  1041:  908 */         MRServiceProtos.GetJobReportResponseProto result = buildPartial();
/*  1042:  909 */         if (!result.isInitialized()) {
/*  1043:  910 */           throw newUninitializedMessageException(result);
/*  1044:      */         }
/*  1045:  912 */         return result;
/*  1046:      */       }
/*  1047:      */       
/*  1048:      */       public MRServiceProtos.GetJobReportResponseProto buildPartial()
/*  1049:      */       {
/*  1050:  916 */         MRServiceProtos.GetJobReportResponseProto result = new MRServiceProtos.GetJobReportResponseProto(this, null);
/*  1051:  917 */         int from_bitField0_ = this.bitField0_;
/*  1052:  918 */         int to_bitField0_ = 0;
/*  1053:  919 */         if ((from_bitField0_ & 0x1) == 1) {
/*  1054:  920 */           to_bitField0_ |= 0x1;
/*  1055:      */         }
/*  1056:  922 */         if (this.jobReportBuilder_ == null) {
/*  1057:  923 */           result.jobReport_ = this.jobReport_;
/*  1058:      */         } else {
/*  1059:  925 */           result.jobReport_ = ((MRProtos.JobReportProto)this.jobReportBuilder_.build());
/*  1060:      */         }
/*  1061:  927 */         result.bitField0_ = to_bitField0_;
/*  1062:  928 */         onBuilt();
/*  1063:  929 */         return result;
/*  1064:      */       }
/*  1065:      */       
/*  1066:      */       public Builder mergeFrom(Message other)
/*  1067:      */       {
/*  1068:  933 */         if ((other instanceof MRServiceProtos.GetJobReportResponseProto)) {
/*  1069:  934 */           return mergeFrom((MRServiceProtos.GetJobReportResponseProto)other);
/*  1070:      */         }
/*  1071:  936 */         super.mergeFrom(other);
/*  1072:  937 */         return this;
/*  1073:      */       }
/*  1074:      */       
/*  1075:      */       public Builder mergeFrom(MRServiceProtos.GetJobReportResponseProto other)
/*  1076:      */       {
/*  1077:  942 */         if (other == MRServiceProtos.GetJobReportResponseProto.getDefaultInstance()) {
/*  1078:  942 */           return this;
/*  1079:      */         }
/*  1080:  943 */         if (other.hasJobReport()) {
/*  1081:  944 */           mergeJobReport(other.getJobReport());
/*  1082:      */         }
/*  1083:  946 */         mergeUnknownFields(other.getUnknownFields());
/*  1084:  947 */         return this;
/*  1085:      */       }
/*  1086:      */       
/*  1087:      */       public final boolean isInitialized()
/*  1088:      */       {
/*  1089:  951 */         return true;
/*  1090:      */       }
/*  1091:      */       
/*  1092:      */       public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry)
/*  1093:      */         throws IOException
/*  1094:      */       {
/*  1095:  958 */         MRServiceProtos.GetJobReportResponseProto parsedMessage = null;
/*  1096:      */         try
/*  1097:      */         {
/*  1098:  960 */           parsedMessage = (MRServiceProtos.GetJobReportResponseProto)MRServiceProtos.GetJobReportResponseProto.PARSER.parsePartialFrom(input, extensionRegistry);
/*  1099:      */         }
/*  1100:      */         catch (InvalidProtocolBufferException e)
/*  1101:      */         {
/*  1102:  962 */           parsedMessage = (MRServiceProtos.GetJobReportResponseProto)e.getUnfinishedMessage();
/*  1103:  963 */           throw e;
/*  1104:      */         }
/*  1105:      */         finally
/*  1106:      */         {
/*  1107:  965 */           if (parsedMessage != null) {
/*  1108:  966 */             mergeFrom(parsedMessage);
/*  1109:      */           }
/*  1110:      */         }
/*  1111:  969 */         return this;
/*  1112:      */       }
/*  1113:      */       
/*  1114:  974 */       private MRProtos.JobReportProto jobReport_ = MRProtos.JobReportProto.getDefaultInstance();
/*  1115:      */       private SingleFieldBuilder<MRProtos.JobReportProto, MRProtos.JobReportProto.Builder, MRProtos.JobReportProtoOrBuilder> jobReportBuilder_;
/*  1116:      */       
/*  1117:      */       public boolean hasJobReport()
/*  1118:      */       {
/*  1119:  981 */         return (this.bitField0_ & 0x1) == 1;
/*  1120:      */       }
/*  1121:      */       
/*  1122:      */       public MRProtos.JobReportProto getJobReport()
/*  1123:      */       {
/*  1124:  987 */         if (this.jobReportBuilder_ == null) {
/*  1125:  988 */           return this.jobReport_;
/*  1126:      */         }
/*  1127:  990 */         return (MRProtos.JobReportProto)this.jobReportBuilder_.getMessage();
/*  1128:      */       }
/*  1129:      */       
/*  1130:      */       public Builder setJobReport(MRProtos.JobReportProto value)
/*  1131:      */       {
/*  1132:  997 */         if (this.jobReportBuilder_ == null)
/*  1133:      */         {
/*  1134:  998 */           if (value == null) {
/*  1135:  999 */             throw new NullPointerException();
/*  1136:      */           }
/*  1137: 1001 */           this.jobReport_ = value;
/*  1138: 1002 */           onChanged();
/*  1139:      */         }
/*  1140:      */         else
/*  1141:      */         {
/*  1142: 1004 */           this.jobReportBuilder_.setMessage(value);
/*  1143:      */         }
/*  1144: 1006 */         this.bitField0_ |= 0x1;
/*  1145: 1007 */         return this;
/*  1146:      */       }
/*  1147:      */       
/*  1148:      */       public Builder setJobReport(MRProtos.JobReportProto.Builder builderForValue)
/*  1149:      */       {
/*  1150: 1014 */         if (this.jobReportBuilder_ == null)
/*  1151:      */         {
/*  1152: 1015 */           this.jobReport_ = builderForValue.build();
/*  1153: 1016 */           onChanged();
/*  1154:      */         }
/*  1155:      */         else
/*  1156:      */         {
/*  1157: 1018 */           this.jobReportBuilder_.setMessage(builderForValue.build());
/*  1158:      */         }
/*  1159: 1020 */         this.bitField0_ |= 0x1;
/*  1160: 1021 */         return this;
/*  1161:      */       }
/*  1162:      */       
/*  1163:      */       public Builder mergeJobReport(MRProtos.JobReportProto value)
/*  1164:      */       {
/*  1165: 1027 */         if (this.jobReportBuilder_ == null)
/*  1166:      */         {
/*  1167: 1028 */           if (((this.bitField0_ & 0x1) == 1) && (this.jobReport_ != MRProtos.JobReportProto.getDefaultInstance())) {
/*  1168: 1030 */             this.jobReport_ = MRProtos.JobReportProto.newBuilder(this.jobReport_).mergeFrom(value).buildPartial();
/*  1169:      */           } else {
/*  1170: 1033 */             this.jobReport_ = value;
/*  1171:      */           }
/*  1172: 1035 */           onChanged();
/*  1173:      */         }
/*  1174:      */         else
/*  1175:      */         {
/*  1176: 1037 */           this.jobReportBuilder_.mergeFrom(value);
/*  1177:      */         }
/*  1178: 1039 */         this.bitField0_ |= 0x1;
/*  1179: 1040 */         return this;
/*  1180:      */       }
/*  1181:      */       
/*  1182:      */       public Builder clearJobReport()
/*  1183:      */       {
/*  1184: 1046 */         if (this.jobReportBuilder_ == null)
/*  1185:      */         {
/*  1186: 1047 */           this.jobReport_ = MRProtos.JobReportProto.getDefaultInstance();
/*  1187: 1048 */           onChanged();
/*  1188:      */         }
/*  1189:      */         else
/*  1190:      */         {
/*  1191: 1050 */           this.jobReportBuilder_.clear();
/*  1192:      */         }
/*  1193: 1052 */         this.bitField0_ &= 0xFFFFFFFE;
/*  1194: 1053 */         return this;
/*  1195:      */       }
/*  1196:      */       
/*  1197:      */       public MRProtos.JobReportProto.Builder getJobReportBuilder()
/*  1198:      */       {
/*  1199: 1059 */         this.bitField0_ |= 0x1;
/*  1200: 1060 */         onChanged();
/*  1201: 1061 */         return (MRProtos.JobReportProto.Builder)getJobReportFieldBuilder().getBuilder();
/*  1202:      */       }
/*  1203:      */       
/*  1204:      */       public MRProtos.JobReportProtoOrBuilder getJobReportOrBuilder()
/*  1205:      */       {
/*  1206: 1067 */         if (this.jobReportBuilder_ != null) {
/*  1207: 1068 */           return (MRProtos.JobReportProtoOrBuilder)this.jobReportBuilder_.getMessageOrBuilder();
/*  1208:      */         }
/*  1209: 1070 */         return this.jobReport_;
/*  1210:      */       }
/*  1211:      */       
/*  1212:      */       private SingleFieldBuilder<MRProtos.JobReportProto, MRProtos.JobReportProto.Builder, MRProtos.JobReportProtoOrBuilder> getJobReportFieldBuilder()
/*  1213:      */       {
/*  1214: 1079 */         if (this.jobReportBuilder_ == null)
/*  1215:      */         {
/*  1216: 1080 */           this.jobReportBuilder_ = new SingleFieldBuilder(this.jobReport_, getParentForChildren(), isClean());
/*  1217:      */           
/*  1218:      */ 
/*  1219:      */ 
/*  1220:      */ 
/*  1221: 1085 */           this.jobReport_ = null;
/*  1222:      */         }
/*  1223: 1087 */         return this.jobReportBuilder_;
/*  1224:      */       }
/*  1225:      */     }
/*  1226:      */     
/*  1227:      */     static
/*  1228:      */     {
/*  1229: 1094 */       defaultInstance = new GetJobReportResponseProto(true);
/*  1230: 1095 */       defaultInstance.initFields();
/*  1231:      */     }
/*  1232:      */   }
/*  1233:      */   
/*  1234:      */   public static abstract interface GetTaskReportRequestProtoOrBuilder
/*  1235:      */     extends MessageOrBuilder
/*  1236:      */   {
/*  1237:      */     public abstract boolean hasTaskId();
/*  1238:      */     
/*  1239:      */     public abstract MRProtos.TaskIdProto getTaskId();
/*  1240:      */     
/*  1241:      */     public abstract MRProtos.TaskIdProtoOrBuilder getTaskIdOrBuilder();
/*  1242:      */   }
/*  1243:      */   
/*  1244:      */   public static final class GetTaskReportRequestProto
/*  1245:      */     extends GeneratedMessage
/*  1246:      */     implements MRServiceProtos.GetTaskReportRequestProtoOrBuilder
/*  1247:      */   {
/*  1248:      */     private static final GetTaskReportRequestProto defaultInstance;
/*  1249:      */     private final UnknownFieldSet unknownFields;
/*  1250:      */     
/*  1251:      */     private GetTaskReportRequestProto(GeneratedMessage.Builder<?> builder)
/*  1252:      */     {
/*  1253: 1126 */       super();
/*  1254: 1127 */       this.unknownFields = builder.getUnknownFields();
/*  1255:      */     }
/*  1256:      */     
/*  1257:      */     private GetTaskReportRequestProto(boolean noInit)
/*  1258:      */     {
/*  1259: 1129 */       this.unknownFields = UnknownFieldSet.getDefaultInstance();
/*  1260:      */     }
/*  1261:      */     
/*  1262:      */     public static GetTaskReportRequestProto getDefaultInstance()
/*  1263:      */     {
/*  1264: 1133 */       return defaultInstance;
/*  1265:      */     }
/*  1266:      */     
/*  1267:      */     public GetTaskReportRequestProto getDefaultInstanceForType()
/*  1268:      */     {
/*  1269: 1137 */       return defaultInstance;
/*  1270:      */     }
/*  1271:      */     
/*  1272:      */     public final UnknownFieldSet getUnknownFields()
/*  1273:      */     {
/*  1274: 1144 */       return this.unknownFields;
/*  1275:      */     }
/*  1276:      */     
/*  1277:      */     private GetTaskReportRequestProto(CodedInputStream input, ExtensionRegistryLite extensionRegistry)
/*  1278:      */       throws InvalidProtocolBufferException
/*  1279:      */     {
/*  1280: 1150 */       initFields();
/*  1281: 1151 */       int mutable_bitField0_ = 0;
/*  1282: 1152 */       UnknownFieldSet.Builder unknownFields = UnknownFieldSet.newBuilder();
/*  1283:      */       try
/*  1284:      */       {
/*  1285: 1155 */         boolean done = false;
/*  1286: 1156 */         while (!done)
/*  1287:      */         {
/*  1288: 1157 */           int tag = input.readTag();
/*  1289: 1158 */           switch (tag)
/*  1290:      */           {
/*  1291:      */           case 0: 
/*  1292: 1160 */             done = true;
/*  1293: 1161 */             break;
/*  1294:      */           default: 
/*  1295: 1163 */             if (!parseUnknownField(input, unknownFields, extensionRegistry, tag)) {
/*  1296: 1165 */               done = true;
/*  1297:      */             }
/*  1298:      */             break;
/*  1299:      */           case 10: 
/*  1300: 1170 */             MRProtos.TaskIdProto.Builder subBuilder = null;
/*  1301: 1171 */             if ((this.bitField0_ & 0x1) == 1) {
/*  1302: 1172 */               subBuilder = this.taskId_.toBuilder();
/*  1303:      */             }
/*  1304: 1174 */             this.taskId_ = ((MRProtos.TaskIdProto)input.readMessage(MRProtos.TaskIdProto.PARSER, extensionRegistry));
/*  1305: 1175 */             if (subBuilder != null)
/*  1306:      */             {
/*  1307: 1176 */               subBuilder.mergeFrom(this.taskId_);
/*  1308: 1177 */               this.taskId_ = subBuilder.buildPartial();
/*  1309:      */             }
/*  1310: 1179 */             this.bitField0_ |= 0x1;
/*  1311:      */           }
/*  1312:      */         }
/*  1313:      */       }
/*  1314:      */       catch (InvalidProtocolBufferException e)
/*  1315:      */       {
/*  1316: 1185 */         throw e.setUnfinishedMessage(this);
/*  1317:      */       }
/*  1318:      */       catch (IOException e)
/*  1319:      */       {
/*  1320: 1187 */         throw new InvalidProtocolBufferException(e.getMessage()).setUnfinishedMessage(this);
/*  1321:      */       }
/*  1322:      */       finally
/*  1323:      */       {
/*  1324: 1190 */         this.unknownFields = unknownFields.build();
/*  1325: 1191 */         makeExtensionsImmutable();
/*  1326:      */       }
/*  1327:      */     }
/*  1328:      */     
/*  1329:      */     public static final Descriptors.Descriptor getDescriptor()
/*  1330:      */     {
/*  1331: 1196 */       return MRServiceProtos.internal_static_hadoop_mapreduce_GetTaskReportRequestProto_descriptor;
/*  1332:      */     }
/*  1333:      */     
/*  1334:      */     protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable()
/*  1335:      */     {
/*  1336: 1201 */       return MRServiceProtos.internal_static_hadoop_mapreduce_GetTaskReportRequestProto_fieldAccessorTable.ensureFieldAccessorsInitialized(GetTaskReportRequestProto.class, Builder.class);
/*  1337:      */     }
/*  1338:      */     
/*  1339: 1206 */     public static Parser<GetTaskReportRequestProto> PARSER = new AbstractParser()
/*  1340:      */     {
/*  1341:      */       public MRServiceProtos.GetTaskReportRequestProto parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry)
/*  1342:      */         throws InvalidProtocolBufferException
/*  1343:      */       {
/*  1344: 1212 */         return new MRServiceProtos.GetTaskReportRequestProto(input, extensionRegistry, null);
/*  1345:      */       }
/*  1346:      */     };
/*  1347:      */     private int bitField0_;
/*  1348:      */     public static final int TASK_ID_FIELD_NUMBER = 1;
/*  1349:      */     private MRProtos.TaskIdProto taskId_;
/*  1350:      */     
/*  1351:      */     public Parser<GetTaskReportRequestProto> getParserForType()
/*  1352:      */     {
/*  1353: 1218 */       return PARSER;
/*  1354:      */     }
/*  1355:      */     
/*  1356:      */     public boolean hasTaskId()
/*  1357:      */     {
/*  1358: 1229 */       return (this.bitField0_ & 0x1) == 1;
/*  1359:      */     }
/*  1360:      */     
/*  1361:      */     public MRProtos.TaskIdProto getTaskId()
/*  1362:      */     {
/*  1363: 1235 */       return this.taskId_;
/*  1364:      */     }
/*  1365:      */     
/*  1366:      */     public MRProtos.TaskIdProtoOrBuilder getTaskIdOrBuilder()
/*  1367:      */     {
/*  1368: 1241 */       return this.taskId_;
/*  1369:      */     }
/*  1370:      */     
/*  1371:      */     private void initFields()
/*  1372:      */     {
/*  1373: 1245 */       this.taskId_ = MRProtos.TaskIdProto.getDefaultInstance();
/*  1374:      */     }
/*  1375:      */     
/*  1376: 1247 */     private byte memoizedIsInitialized = -1;
/*  1377:      */     
/*  1378:      */     public final boolean isInitialized()
/*  1379:      */     {
/*  1380: 1249 */       byte isInitialized = this.memoizedIsInitialized;
/*  1381: 1250 */       if (isInitialized != -1) {
/*  1382: 1250 */         return isInitialized == 1;
/*  1383:      */       }
/*  1384: 1252 */       this.memoizedIsInitialized = 1;
/*  1385: 1253 */       return true;
/*  1386:      */     }
/*  1387:      */     
/*  1388:      */     public void writeTo(CodedOutputStream output)
/*  1389:      */       throws IOException
/*  1390:      */     {
/*  1391: 1258 */       getSerializedSize();
/*  1392: 1259 */       if ((this.bitField0_ & 0x1) == 1) {
/*  1393: 1260 */         output.writeMessage(1, this.taskId_);
/*  1394:      */       }
/*  1395: 1262 */       getUnknownFields().writeTo(output);
/*  1396:      */     }
/*  1397:      */     
/*  1398: 1265 */     private int memoizedSerializedSize = -1;
/*  1399:      */     private static final long serialVersionUID = 0L;
/*  1400:      */     
/*  1401:      */     public int getSerializedSize()
/*  1402:      */     {
/*  1403: 1267 */       int size = this.memoizedSerializedSize;
/*  1404: 1268 */       if (size != -1) {
/*  1405: 1268 */         return size;
/*  1406:      */       }
/*  1407: 1270 */       size = 0;
/*  1408: 1271 */       if ((this.bitField0_ & 0x1) == 1) {
/*  1409: 1272 */         size += CodedOutputStream.computeMessageSize(1, this.taskId_);
/*  1410:      */       }
/*  1411: 1275 */       size += getUnknownFields().getSerializedSize();
/*  1412: 1276 */       this.memoizedSerializedSize = size;
/*  1413: 1277 */       return size;
/*  1414:      */     }
/*  1415:      */     
/*  1416:      */     protected Object writeReplace()
/*  1417:      */       throws ObjectStreamException
/*  1418:      */     {
/*  1419: 1284 */       return super.writeReplace();
/*  1420:      */     }
/*  1421:      */     
/*  1422:      */     public boolean equals(Object obj)
/*  1423:      */     {
/*  1424: 1289 */       if (obj == this) {
/*  1425: 1290 */         return true;
/*  1426:      */       }
/*  1427: 1292 */       if (!(obj instanceof GetTaskReportRequestProto)) {
/*  1428: 1293 */         return super.equals(obj);
/*  1429:      */       }
/*  1430: 1295 */       GetTaskReportRequestProto other = (GetTaskReportRequestProto)obj;
/*  1431:      */       
/*  1432: 1297 */       boolean result = true;
/*  1433: 1298 */       result = (result) && (hasTaskId() == other.hasTaskId());
/*  1434: 1299 */       if (hasTaskId()) {
/*  1435: 1300 */         result = (result) && (getTaskId().equals(other.getTaskId()));
/*  1436:      */       }
/*  1437: 1303 */       result = (result) && (getUnknownFields().equals(other.getUnknownFields()));
/*  1438:      */       
/*  1439: 1305 */       return result;
/*  1440:      */     }
/*  1441:      */     
/*  1442: 1308 */     private int memoizedHashCode = 0;
/*  1443:      */     
/*  1444:      */     public int hashCode()
/*  1445:      */     {
/*  1446: 1311 */       if (this.memoizedHashCode != 0) {
/*  1447: 1312 */         return this.memoizedHashCode;
/*  1448:      */       }
/*  1449: 1314 */       int hash = 41;
/*  1450: 1315 */       hash = 19 * hash + getDescriptorForType().hashCode();
/*  1451: 1316 */       if (hasTaskId())
/*  1452:      */       {
/*  1453: 1317 */         hash = 37 * hash + 1;
/*  1454: 1318 */         hash = 53 * hash + getTaskId().hashCode();
/*  1455:      */       }
/*  1456: 1320 */       hash = 29 * hash + getUnknownFields().hashCode();
/*  1457: 1321 */       this.memoizedHashCode = hash;
/*  1458: 1322 */       return hash;
/*  1459:      */     }
/*  1460:      */     
/*  1461:      */     public static GetTaskReportRequestProto parseFrom(ByteString data)
/*  1462:      */       throws InvalidProtocolBufferException
/*  1463:      */     {
/*  1464: 1328 */       return (GetTaskReportRequestProto)PARSER.parseFrom(data);
/*  1465:      */     }
/*  1466:      */     
/*  1467:      */     public static GetTaskReportRequestProto parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry)
/*  1468:      */       throws InvalidProtocolBufferException
/*  1469:      */     {
/*  1470: 1334 */       return (GetTaskReportRequestProto)PARSER.parseFrom(data, extensionRegistry);
/*  1471:      */     }
/*  1472:      */     
/*  1473:      */     public static GetTaskReportRequestProto parseFrom(byte[] data)
/*  1474:      */       throws InvalidProtocolBufferException
/*  1475:      */     {
/*  1476: 1338 */       return (GetTaskReportRequestProto)PARSER.parseFrom(data);
/*  1477:      */     }
/*  1478:      */     
/*  1479:      */     public static GetTaskReportRequestProto parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry)
/*  1480:      */       throws InvalidProtocolBufferException
/*  1481:      */     {
/*  1482: 1344 */       return (GetTaskReportRequestProto)PARSER.parseFrom(data, extensionRegistry);
/*  1483:      */     }
/*  1484:      */     
/*  1485:      */     public static GetTaskReportRequestProto parseFrom(InputStream input)
/*  1486:      */       throws IOException
/*  1487:      */     {
/*  1488: 1348 */       return (GetTaskReportRequestProto)PARSER.parseFrom(input);
/*  1489:      */     }
/*  1490:      */     
/*  1491:      */     public static GetTaskReportRequestProto parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry)
/*  1492:      */       throws IOException
/*  1493:      */     {
/*  1494: 1354 */       return (GetTaskReportRequestProto)PARSER.parseFrom(input, extensionRegistry);
/*  1495:      */     }
/*  1496:      */     
/*  1497:      */     public static GetTaskReportRequestProto parseDelimitedFrom(InputStream input)
/*  1498:      */       throws IOException
/*  1499:      */     {
/*  1500: 1358 */       return (GetTaskReportRequestProto)PARSER.parseDelimitedFrom(input);
/*  1501:      */     }
/*  1502:      */     
/*  1503:      */     public static GetTaskReportRequestProto parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry)
/*  1504:      */       throws IOException
/*  1505:      */     {
/*  1506: 1364 */       return (GetTaskReportRequestProto)PARSER.parseDelimitedFrom(input, extensionRegistry);
/*  1507:      */     }
/*  1508:      */     
/*  1509:      */     public static GetTaskReportRequestProto parseFrom(CodedInputStream input)
/*  1510:      */       throws IOException
/*  1511:      */     {
/*  1512: 1369 */       return (GetTaskReportRequestProto)PARSER.parseFrom(input);
/*  1513:      */     }
/*  1514:      */     
/*  1515:      */     public static GetTaskReportRequestProto parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry)
/*  1516:      */       throws IOException
/*  1517:      */     {
/*  1518: 1375 */       return (GetTaskReportRequestProto)PARSER.parseFrom(input, extensionRegistry);
/*  1519:      */     }
/*  1520:      */     
/*  1521:      */     public static Builder newBuilder()
/*  1522:      */     {
/*  1523: 1378 */       return Builder.access$2100();
/*  1524:      */     }
/*  1525:      */     
/*  1526:      */     public Builder newBuilderForType()
/*  1527:      */     {
/*  1528: 1379 */       return newBuilder();
/*  1529:      */     }
/*  1530:      */     
/*  1531:      */     public static Builder newBuilder(GetTaskReportRequestProto prototype)
/*  1532:      */     {
/*  1533: 1381 */       return newBuilder().mergeFrom(prototype);
/*  1534:      */     }
/*  1535:      */     
/*  1536:      */     public Builder toBuilder()
/*  1537:      */     {
/*  1538: 1383 */       return newBuilder(this);
/*  1539:      */     }
/*  1540:      */     
/*  1541:      */     protected Builder newBuilderForType(GeneratedMessage.BuilderParent parent)
/*  1542:      */     {
/*  1543: 1388 */       Builder builder = new Builder(parent, null);
/*  1544: 1389 */       return builder;
/*  1545:      */     }
/*  1546:      */     
/*  1547:      */     public static final class Builder
/*  1548:      */       extends GeneratedMessage.Builder<Builder>
/*  1549:      */       implements MRServiceProtos.GetTaskReportRequestProtoOrBuilder
/*  1550:      */     {
/*  1551:      */       private int bitField0_;
/*  1552:      */       
/*  1553:      */       public static final Descriptors.Descriptor getDescriptor()
/*  1554:      */       {
/*  1555: 1399 */         return MRServiceProtos.internal_static_hadoop_mapreduce_GetTaskReportRequestProto_descriptor;
/*  1556:      */       }
/*  1557:      */       
/*  1558:      */       protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable()
/*  1559:      */       {
/*  1560: 1404 */         return MRServiceProtos.internal_static_hadoop_mapreduce_GetTaskReportRequestProto_fieldAccessorTable.ensureFieldAccessorsInitialized(MRServiceProtos.GetTaskReportRequestProto.class, Builder.class);
/*  1561:      */       }
/*  1562:      */       
/*  1563:      */       private Builder()
/*  1564:      */       {
/*  1565: 1411 */         maybeForceBuilderInitialization();
/*  1566:      */       }
/*  1567:      */       
/*  1568:      */       private Builder(GeneratedMessage.BuilderParent parent)
/*  1569:      */       {
/*  1570: 1416 */         super();
/*  1571: 1417 */         maybeForceBuilderInitialization();
/*  1572:      */       }
/*  1573:      */       
/*  1574:      */       private void maybeForceBuilderInitialization()
/*  1575:      */       {
/*  1576: 1420 */         if (MRServiceProtos.GetTaskReportRequestProto.alwaysUseFieldBuilders) {
/*  1577: 1421 */           getTaskIdFieldBuilder();
/*  1578:      */         }
/*  1579:      */       }
/*  1580:      */       
/*  1581:      */       private static Builder create()
/*  1582:      */       {
/*  1583: 1425 */         return new Builder();
/*  1584:      */       }
/*  1585:      */       
/*  1586:      */       public Builder clear()
/*  1587:      */       {
/*  1588: 1429 */         super.clear();
/*  1589: 1430 */         if (this.taskIdBuilder_ == null) {
/*  1590: 1431 */           this.taskId_ = MRProtos.TaskIdProto.getDefaultInstance();
/*  1591:      */         } else {
/*  1592: 1433 */           this.taskIdBuilder_.clear();
/*  1593:      */         }
/*  1594: 1435 */         this.bitField0_ &= 0xFFFFFFFE;
/*  1595: 1436 */         return this;
/*  1596:      */       }
/*  1597:      */       
/*  1598:      */       public Builder clone()
/*  1599:      */       {
/*  1600: 1440 */         return create().mergeFrom(buildPartial());
/*  1601:      */       }
/*  1602:      */       
/*  1603:      */       public Descriptors.Descriptor getDescriptorForType()
/*  1604:      */       {
/*  1605: 1445 */         return MRServiceProtos.internal_static_hadoop_mapreduce_GetTaskReportRequestProto_descriptor;
/*  1606:      */       }
/*  1607:      */       
/*  1608:      */       public MRServiceProtos.GetTaskReportRequestProto getDefaultInstanceForType()
/*  1609:      */       {
/*  1610: 1449 */         return MRServiceProtos.GetTaskReportRequestProto.getDefaultInstance();
/*  1611:      */       }
/*  1612:      */       
/*  1613:      */       public MRServiceProtos.GetTaskReportRequestProto build()
/*  1614:      */       {
/*  1615: 1453 */         MRServiceProtos.GetTaskReportRequestProto result = buildPartial();
/*  1616: 1454 */         if (!result.isInitialized()) {
/*  1617: 1455 */           throw newUninitializedMessageException(result);
/*  1618:      */         }
/*  1619: 1457 */         return result;
/*  1620:      */       }
/*  1621:      */       
/*  1622:      */       public MRServiceProtos.GetTaskReportRequestProto buildPartial()
/*  1623:      */       {
/*  1624: 1461 */         MRServiceProtos.GetTaskReportRequestProto result = new MRServiceProtos.GetTaskReportRequestProto(this, null);
/*  1625: 1462 */         int from_bitField0_ = this.bitField0_;
/*  1626: 1463 */         int to_bitField0_ = 0;
/*  1627: 1464 */         if ((from_bitField0_ & 0x1) == 1) {
/*  1628: 1465 */           to_bitField0_ |= 0x1;
/*  1629:      */         }
/*  1630: 1467 */         if (this.taskIdBuilder_ == null) {
/*  1631: 1468 */           result.taskId_ = this.taskId_;
/*  1632:      */         } else {
/*  1633: 1470 */           result.taskId_ = ((MRProtos.TaskIdProto)this.taskIdBuilder_.build());
/*  1634:      */         }
/*  1635: 1472 */         result.bitField0_ = to_bitField0_;
/*  1636: 1473 */         onBuilt();
/*  1637: 1474 */         return result;
/*  1638:      */       }
/*  1639:      */       
/*  1640:      */       public Builder mergeFrom(Message other)
/*  1641:      */       {
/*  1642: 1478 */         if ((other instanceof MRServiceProtos.GetTaskReportRequestProto)) {
/*  1643: 1479 */           return mergeFrom((MRServiceProtos.GetTaskReportRequestProto)other);
/*  1644:      */         }
/*  1645: 1481 */         super.mergeFrom(other);
/*  1646: 1482 */         return this;
/*  1647:      */       }
/*  1648:      */       
/*  1649:      */       public Builder mergeFrom(MRServiceProtos.GetTaskReportRequestProto other)
/*  1650:      */       {
/*  1651: 1487 */         if (other == MRServiceProtos.GetTaskReportRequestProto.getDefaultInstance()) {
/*  1652: 1487 */           return this;
/*  1653:      */         }
/*  1654: 1488 */         if (other.hasTaskId()) {
/*  1655: 1489 */           mergeTaskId(other.getTaskId());
/*  1656:      */         }
/*  1657: 1491 */         mergeUnknownFields(other.getUnknownFields());
/*  1658: 1492 */         return this;
/*  1659:      */       }
/*  1660:      */       
/*  1661:      */       public final boolean isInitialized()
/*  1662:      */       {
/*  1663: 1496 */         return true;
/*  1664:      */       }
/*  1665:      */       
/*  1666:      */       public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry)
/*  1667:      */         throws IOException
/*  1668:      */       {
/*  1669: 1503 */         MRServiceProtos.GetTaskReportRequestProto parsedMessage = null;
/*  1670:      */         try
/*  1671:      */         {
/*  1672: 1505 */           parsedMessage = (MRServiceProtos.GetTaskReportRequestProto)MRServiceProtos.GetTaskReportRequestProto.PARSER.parsePartialFrom(input, extensionRegistry);
/*  1673:      */         }
/*  1674:      */         catch (InvalidProtocolBufferException e)
/*  1675:      */         {
/*  1676: 1507 */           parsedMessage = (MRServiceProtos.GetTaskReportRequestProto)e.getUnfinishedMessage();
/*  1677: 1508 */           throw e;
/*  1678:      */         }
/*  1679:      */         finally
/*  1680:      */         {
/*  1681: 1510 */           if (parsedMessage != null) {
/*  1682: 1511 */             mergeFrom(parsedMessage);
/*  1683:      */           }
/*  1684:      */         }
/*  1685: 1514 */         return this;
/*  1686:      */       }
/*  1687:      */       
/*  1688: 1519 */       private MRProtos.TaskIdProto taskId_ = MRProtos.TaskIdProto.getDefaultInstance();
/*  1689:      */       private SingleFieldBuilder<MRProtos.TaskIdProto, MRProtos.TaskIdProto.Builder, MRProtos.TaskIdProtoOrBuilder> taskIdBuilder_;
/*  1690:      */       
/*  1691:      */       public boolean hasTaskId()
/*  1692:      */       {
/*  1693: 1526 */         return (this.bitField0_ & 0x1) == 1;
/*  1694:      */       }
/*  1695:      */       
/*  1696:      */       public MRProtos.TaskIdProto getTaskId()
/*  1697:      */       {
/*  1698: 1532 */         if (this.taskIdBuilder_ == null) {
/*  1699: 1533 */           return this.taskId_;
/*  1700:      */         }
/*  1701: 1535 */         return (MRProtos.TaskIdProto)this.taskIdBuilder_.getMessage();
/*  1702:      */       }
/*  1703:      */       
/*  1704:      */       public Builder setTaskId(MRProtos.TaskIdProto value)
/*  1705:      */       {
/*  1706: 1542 */         if (this.taskIdBuilder_ == null)
/*  1707:      */         {
/*  1708: 1543 */           if (value == null) {
/*  1709: 1544 */             throw new NullPointerException();
/*  1710:      */           }
/*  1711: 1546 */           this.taskId_ = value;
/*  1712: 1547 */           onChanged();
/*  1713:      */         }
/*  1714:      */         else
/*  1715:      */         {
/*  1716: 1549 */           this.taskIdBuilder_.setMessage(value);
/*  1717:      */         }
/*  1718: 1551 */         this.bitField0_ |= 0x1;
/*  1719: 1552 */         return this;
/*  1720:      */       }
/*  1721:      */       
/*  1722:      */       public Builder setTaskId(MRProtos.TaskIdProto.Builder builderForValue)
/*  1723:      */       {
/*  1724: 1559 */         if (this.taskIdBuilder_ == null)
/*  1725:      */         {
/*  1726: 1560 */           this.taskId_ = builderForValue.build();
/*  1727: 1561 */           onChanged();
/*  1728:      */         }
/*  1729:      */         else
/*  1730:      */         {
/*  1731: 1563 */           this.taskIdBuilder_.setMessage(builderForValue.build());
/*  1732:      */         }
/*  1733: 1565 */         this.bitField0_ |= 0x1;
/*  1734: 1566 */         return this;
/*  1735:      */       }
/*  1736:      */       
/*  1737:      */       public Builder mergeTaskId(MRProtos.TaskIdProto value)
/*  1738:      */       {
/*  1739: 1572 */         if (this.taskIdBuilder_ == null)
/*  1740:      */         {
/*  1741: 1573 */           if (((this.bitField0_ & 0x1) == 1) && (this.taskId_ != MRProtos.TaskIdProto.getDefaultInstance())) {
/*  1742: 1575 */             this.taskId_ = MRProtos.TaskIdProto.newBuilder(this.taskId_).mergeFrom(value).buildPartial();
/*  1743:      */           } else {
/*  1744: 1578 */             this.taskId_ = value;
/*  1745:      */           }
/*  1746: 1580 */           onChanged();
/*  1747:      */         }
/*  1748:      */         else
/*  1749:      */         {
/*  1750: 1582 */           this.taskIdBuilder_.mergeFrom(value);
/*  1751:      */         }
/*  1752: 1584 */         this.bitField0_ |= 0x1;
/*  1753: 1585 */         return this;
/*  1754:      */       }
/*  1755:      */       
/*  1756:      */       public Builder clearTaskId()
/*  1757:      */       {
/*  1758: 1591 */         if (this.taskIdBuilder_ == null)
/*  1759:      */         {
/*  1760: 1592 */           this.taskId_ = MRProtos.TaskIdProto.getDefaultInstance();
/*  1761: 1593 */           onChanged();
/*  1762:      */         }
/*  1763:      */         else
/*  1764:      */         {
/*  1765: 1595 */           this.taskIdBuilder_.clear();
/*  1766:      */         }
/*  1767: 1597 */         this.bitField0_ &= 0xFFFFFFFE;
/*  1768: 1598 */         return this;
/*  1769:      */       }
/*  1770:      */       
/*  1771:      */       public MRProtos.TaskIdProto.Builder getTaskIdBuilder()
/*  1772:      */       {
/*  1773: 1604 */         this.bitField0_ |= 0x1;
/*  1774: 1605 */         onChanged();
/*  1775: 1606 */         return (MRProtos.TaskIdProto.Builder)getTaskIdFieldBuilder().getBuilder();
/*  1776:      */       }
/*  1777:      */       
/*  1778:      */       public MRProtos.TaskIdProtoOrBuilder getTaskIdOrBuilder()
/*  1779:      */       {
/*  1780: 1612 */         if (this.taskIdBuilder_ != null) {
/*  1781: 1613 */           return (MRProtos.TaskIdProtoOrBuilder)this.taskIdBuilder_.getMessageOrBuilder();
/*  1782:      */         }
/*  1783: 1615 */         return this.taskId_;
/*  1784:      */       }
/*  1785:      */       
/*  1786:      */       private SingleFieldBuilder<MRProtos.TaskIdProto, MRProtos.TaskIdProto.Builder, MRProtos.TaskIdProtoOrBuilder> getTaskIdFieldBuilder()
/*  1787:      */       {
/*  1788: 1624 */         if (this.taskIdBuilder_ == null)
/*  1789:      */         {
/*  1790: 1625 */           this.taskIdBuilder_ = new SingleFieldBuilder(this.taskId_, getParentForChildren(), isClean());
/*  1791:      */           
/*  1792:      */ 
/*  1793:      */ 
/*  1794:      */ 
/*  1795: 1630 */           this.taskId_ = null;
/*  1796:      */         }
/*  1797: 1632 */         return this.taskIdBuilder_;
/*  1798:      */       }
/*  1799:      */     }
/*  1800:      */     
/*  1801:      */     static
/*  1802:      */     {
/*  1803: 1639 */       defaultInstance = new GetTaskReportRequestProto(true);
/*  1804: 1640 */       defaultInstance.initFields();
/*  1805:      */     }
/*  1806:      */   }
/*  1807:      */   
/*  1808:      */   public static abstract interface GetTaskReportResponseProtoOrBuilder
/*  1809:      */     extends MessageOrBuilder
/*  1810:      */   {
/*  1811:      */     public abstract boolean hasTaskReport();
/*  1812:      */     
/*  1813:      */     public abstract MRProtos.TaskReportProto getTaskReport();
/*  1814:      */     
/*  1815:      */     public abstract MRProtos.TaskReportProtoOrBuilder getTaskReportOrBuilder();
/*  1816:      */   }
/*  1817:      */   
/*  1818:      */   public static final class GetTaskReportResponseProto
/*  1819:      */     extends GeneratedMessage
/*  1820:      */     implements MRServiceProtos.GetTaskReportResponseProtoOrBuilder
/*  1821:      */   {
/*  1822:      */     private static final GetTaskReportResponseProto defaultInstance;
/*  1823:      */     private final UnknownFieldSet unknownFields;
/*  1824:      */     
/*  1825:      */     private GetTaskReportResponseProto(GeneratedMessage.Builder<?> builder)
/*  1826:      */     {
/*  1827: 1671 */       super();
/*  1828: 1672 */       this.unknownFields = builder.getUnknownFields();
/*  1829:      */     }
/*  1830:      */     
/*  1831:      */     private GetTaskReportResponseProto(boolean noInit)
/*  1832:      */     {
/*  1833: 1674 */       this.unknownFields = UnknownFieldSet.getDefaultInstance();
/*  1834:      */     }
/*  1835:      */     
/*  1836:      */     public static GetTaskReportResponseProto getDefaultInstance()
/*  1837:      */     {
/*  1838: 1678 */       return defaultInstance;
/*  1839:      */     }
/*  1840:      */     
/*  1841:      */     public GetTaskReportResponseProto getDefaultInstanceForType()
/*  1842:      */     {
/*  1843: 1682 */       return defaultInstance;
/*  1844:      */     }
/*  1845:      */     
/*  1846:      */     public final UnknownFieldSet getUnknownFields()
/*  1847:      */     {
/*  1848: 1689 */       return this.unknownFields;
/*  1849:      */     }
/*  1850:      */     
/*  1851:      */     private GetTaskReportResponseProto(CodedInputStream input, ExtensionRegistryLite extensionRegistry)
/*  1852:      */       throws InvalidProtocolBufferException
/*  1853:      */     {
/*  1854: 1695 */       initFields();
/*  1855: 1696 */       int mutable_bitField0_ = 0;
/*  1856: 1697 */       UnknownFieldSet.Builder unknownFields = UnknownFieldSet.newBuilder();
/*  1857:      */       try
/*  1858:      */       {
/*  1859: 1700 */         boolean done = false;
/*  1860: 1701 */         while (!done)
/*  1861:      */         {
/*  1862: 1702 */           int tag = input.readTag();
/*  1863: 1703 */           switch (tag)
/*  1864:      */           {
/*  1865:      */           case 0: 
/*  1866: 1705 */             done = true;
/*  1867: 1706 */             break;
/*  1868:      */           default: 
/*  1869: 1708 */             if (!parseUnknownField(input, unknownFields, extensionRegistry, tag)) {
/*  1870: 1710 */               done = true;
/*  1871:      */             }
/*  1872:      */             break;
/*  1873:      */           case 10: 
/*  1874: 1715 */             MRProtos.TaskReportProto.Builder subBuilder = null;
/*  1875: 1716 */             if ((this.bitField0_ & 0x1) == 1) {
/*  1876: 1717 */               subBuilder = this.taskReport_.toBuilder();
/*  1877:      */             }
/*  1878: 1719 */             this.taskReport_ = ((MRProtos.TaskReportProto)input.readMessage(MRProtos.TaskReportProto.PARSER, extensionRegistry));
/*  1879: 1720 */             if (subBuilder != null)
/*  1880:      */             {
/*  1881: 1721 */               subBuilder.mergeFrom(this.taskReport_);
/*  1882: 1722 */               this.taskReport_ = subBuilder.buildPartial();
/*  1883:      */             }
/*  1884: 1724 */             this.bitField0_ |= 0x1;
/*  1885:      */           }
/*  1886:      */         }
/*  1887:      */       }
/*  1888:      */       catch (InvalidProtocolBufferException e)
/*  1889:      */       {
/*  1890: 1730 */         throw e.setUnfinishedMessage(this);
/*  1891:      */       }
/*  1892:      */       catch (IOException e)
/*  1893:      */       {
/*  1894: 1732 */         throw new InvalidProtocolBufferException(e.getMessage()).setUnfinishedMessage(this);
/*  1895:      */       }
/*  1896:      */       finally
/*  1897:      */       {
/*  1898: 1735 */         this.unknownFields = unknownFields.build();
/*  1899: 1736 */         makeExtensionsImmutable();
/*  1900:      */       }
/*  1901:      */     }
/*  1902:      */     
/*  1903:      */     public static final Descriptors.Descriptor getDescriptor()
/*  1904:      */     {
/*  1905: 1741 */       return MRServiceProtos.internal_static_hadoop_mapreduce_GetTaskReportResponseProto_descriptor;
/*  1906:      */     }
/*  1907:      */     
/*  1908:      */     protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable()
/*  1909:      */     {
/*  1910: 1746 */       return MRServiceProtos.internal_static_hadoop_mapreduce_GetTaskReportResponseProto_fieldAccessorTable.ensureFieldAccessorsInitialized(GetTaskReportResponseProto.class, Builder.class);
/*  1911:      */     }
/*  1912:      */     
/*  1913: 1751 */     public static Parser<GetTaskReportResponseProto> PARSER = new AbstractParser()
/*  1914:      */     {
/*  1915:      */       public MRServiceProtos.GetTaskReportResponseProto parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry)
/*  1916:      */         throws InvalidProtocolBufferException
/*  1917:      */       {
/*  1918: 1757 */         return new MRServiceProtos.GetTaskReportResponseProto(input, extensionRegistry, null);
/*  1919:      */       }
/*  1920:      */     };
/*  1921:      */     private int bitField0_;
/*  1922:      */     public static final int TASK_REPORT_FIELD_NUMBER = 1;
/*  1923:      */     private MRProtos.TaskReportProto taskReport_;
/*  1924:      */     
/*  1925:      */     public Parser<GetTaskReportResponseProto> getParserForType()
/*  1926:      */     {
/*  1927: 1763 */       return PARSER;
/*  1928:      */     }
/*  1929:      */     
/*  1930:      */     public boolean hasTaskReport()
/*  1931:      */     {
/*  1932: 1774 */       return (this.bitField0_ & 0x1) == 1;
/*  1933:      */     }
/*  1934:      */     
/*  1935:      */     public MRProtos.TaskReportProto getTaskReport()
/*  1936:      */     {
/*  1937: 1780 */       return this.taskReport_;
/*  1938:      */     }
/*  1939:      */     
/*  1940:      */     public MRProtos.TaskReportProtoOrBuilder getTaskReportOrBuilder()
/*  1941:      */     {
/*  1942: 1786 */       return this.taskReport_;
/*  1943:      */     }
/*  1944:      */     
/*  1945:      */     private void initFields()
/*  1946:      */     {
/*  1947: 1790 */       this.taskReport_ = MRProtos.TaskReportProto.getDefaultInstance();
/*  1948:      */     }
/*  1949:      */     
/*  1950: 1792 */     private byte memoizedIsInitialized = -1;
/*  1951:      */     
/*  1952:      */     public final boolean isInitialized()
/*  1953:      */     {
/*  1954: 1794 */       byte isInitialized = this.memoizedIsInitialized;
/*  1955: 1795 */       if (isInitialized != -1) {
/*  1956: 1795 */         return isInitialized == 1;
/*  1957:      */       }
/*  1958: 1797 */       this.memoizedIsInitialized = 1;
/*  1959: 1798 */       return true;
/*  1960:      */     }
/*  1961:      */     
/*  1962:      */     public void writeTo(CodedOutputStream output)
/*  1963:      */       throws IOException
/*  1964:      */     {
/*  1965: 1803 */       getSerializedSize();
/*  1966: 1804 */       if ((this.bitField0_ & 0x1) == 1) {
/*  1967: 1805 */         output.writeMessage(1, this.taskReport_);
/*  1968:      */       }
/*  1969: 1807 */       getUnknownFields().writeTo(output);
/*  1970:      */     }
/*  1971:      */     
/*  1972: 1810 */     private int memoizedSerializedSize = -1;
/*  1973:      */     private static final long serialVersionUID = 0L;
/*  1974:      */     
/*  1975:      */     public int getSerializedSize()
/*  1976:      */     {
/*  1977: 1812 */       int size = this.memoizedSerializedSize;
/*  1978: 1813 */       if (size != -1) {
/*  1979: 1813 */         return size;
/*  1980:      */       }
/*  1981: 1815 */       size = 0;
/*  1982: 1816 */       if ((this.bitField0_ & 0x1) == 1) {
/*  1983: 1817 */         size += CodedOutputStream.computeMessageSize(1, this.taskReport_);
/*  1984:      */       }
/*  1985: 1820 */       size += getUnknownFields().getSerializedSize();
/*  1986: 1821 */       this.memoizedSerializedSize = size;
/*  1987: 1822 */       return size;
/*  1988:      */     }
/*  1989:      */     
/*  1990:      */     protected Object writeReplace()
/*  1991:      */       throws ObjectStreamException
/*  1992:      */     {
/*  1993: 1829 */       return super.writeReplace();
/*  1994:      */     }
/*  1995:      */     
/*  1996:      */     public boolean equals(Object obj)
/*  1997:      */     {
/*  1998: 1834 */       if (obj == this) {
/*  1999: 1835 */         return true;
/*  2000:      */       }
/*  2001: 1837 */       if (!(obj instanceof GetTaskReportResponseProto)) {
/*  2002: 1838 */         return super.equals(obj);
/*  2003:      */       }
/*  2004: 1840 */       GetTaskReportResponseProto other = (GetTaskReportResponseProto)obj;
/*  2005:      */       
/*  2006: 1842 */       boolean result = true;
/*  2007: 1843 */       result = (result) && (hasTaskReport() == other.hasTaskReport());
/*  2008: 1844 */       if (hasTaskReport()) {
/*  2009: 1845 */         result = (result) && (getTaskReport().equals(other.getTaskReport()));
/*  2010:      */       }
/*  2011: 1848 */       result = (result) && (getUnknownFields().equals(other.getUnknownFields()));
/*  2012:      */       
/*  2013: 1850 */       return result;
/*  2014:      */     }
/*  2015:      */     
/*  2016: 1853 */     private int memoizedHashCode = 0;
/*  2017:      */     
/*  2018:      */     public int hashCode()
/*  2019:      */     {
/*  2020: 1856 */       if (this.memoizedHashCode != 0) {
/*  2021: 1857 */         return this.memoizedHashCode;
/*  2022:      */       }
/*  2023: 1859 */       int hash = 41;
/*  2024: 1860 */       hash = 19 * hash + getDescriptorForType().hashCode();
/*  2025: 1861 */       if (hasTaskReport())
/*  2026:      */       {
/*  2027: 1862 */         hash = 37 * hash + 1;
/*  2028: 1863 */         hash = 53 * hash + getTaskReport().hashCode();
/*  2029:      */       }
/*  2030: 1865 */       hash = 29 * hash + getUnknownFields().hashCode();
/*  2031: 1866 */       this.memoizedHashCode = hash;
/*  2032: 1867 */       return hash;
/*  2033:      */     }
/*  2034:      */     
/*  2035:      */     public static GetTaskReportResponseProto parseFrom(ByteString data)
/*  2036:      */       throws InvalidProtocolBufferException
/*  2037:      */     {
/*  2038: 1873 */       return (GetTaskReportResponseProto)PARSER.parseFrom(data);
/*  2039:      */     }
/*  2040:      */     
/*  2041:      */     public static GetTaskReportResponseProto parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry)
/*  2042:      */       throws InvalidProtocolBufferException
/*  2043:      */     {
/*  2044: 1879 */       return (GetTaskReportResponseProto)PARSER.parseFrom(data, extensionRegistry);
/*  2045:      */     }
/*  2046:      */     
/*  2047:      */     public static GetTaskReportResponseProto parseFrom(byte[] data)
/*  2048:      */       throws InvalidProtocolBufferException
/*  2049:      */     {
/*  2050: 1883 */       return (GetTaskReportResponseProto)PARSER.parseFrom(data);
/*  2051:      */     }
/*  2052:      */     
/*  2053:      */     public static GetTaskReportResponseProto parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry)
/*  2054:      */       throws InvalidProtocolBufferException
/*  2055:      */     {
/*  2056: 1889 */       return (GetTaskReportResponseProto)PARSER.parseFrom(data, extensionRegistry);
/*  2057:      */     }
/*  2058:      */     
/*  2059:      */     public static GetTaskReportResponseProto parseFrom(InputStream input)
/*  2060:      */       throws IOException
/*  2061:      */     {
/*  2062: 1893 */       return (GetTaskReportResponseProto)PARSER.parseFrom(input);
/*  2063:      */     }
/*  2064:      */     
/*  2065:      */     public static GetTaskReportResponseProto parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry)
/*  2066:      */       throws IOException
/*  2067:      */     {
/*  2068: 1899 */       return (GetTaskReportResponseProto)PARSER.parseFrom(input, extensionRegistry);
/*  2069:      */     }
/*  2070:      */     
/*  2071:      */     public static GetTaskReportResponseProto parseDelimitedFrom(InputStream input)
/*  2072:      */       throws IOException
/*  2073:      */     {
/*  2074: 1903 */       return (GetTaskReportResponseProto)PARSER.parseDelimitedFrom(input);
/*  2075:      */     }
/*  2076:      */     
/*  2077:      */     public static GetTaskReportResponseProto parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry)
/*  2078:      */       throws IOException
/*  2079:      */     {
/*  2080: 1909 */       return (GetTaskReportResponseProto)PARSER.parseDelimitedFrom(input, extensionRegistry);
/*  2081:      */     }
/*  2082:      */     
/*  2083:      */     public static GetTaskReportResponseProto parseFrom(CodedInputStream input)
/*  2084:      */       throws IOException
/*  2085:      */     {
/*  2086: 1914 */       return (GetTaskReportResponseProto)PARSER.parseFrom(input);
/*  2087:      */     }
/*  2088:      */     
/*  2089:      */     public static GetTaskReportResponseProto parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry)
/*  2090:      */       throws IOException
/*  2091:      */     {
/*  2092: 1920 */       return (GetTaskReportResponseProto)PARSER.parseFrom(input, extensionRegistry);
/*  2093:      */     }
/*  2094:      */     
/*  2095:      */     public static Builder newBuilder()
/*  2096:      */     {
/*  2097: 1923 */       return Builder.access$3000();
/*  2098:      */     }
/*  2099:      */     
/*  2100:      */     public Builder newBuilderForType()
/*  2101:      */     {
/*  2102: 1924 */       return newBuilder();
/*  2103:      */     }
/*  2104:      */     
/*  2105:      */     public static Builder newBuilder(GetTaskReportResponseProto prototype)
/*  2106:      */     {
/*  2107: 1926 */       return newBuilder().mergeFrom(prototype);
/*  2108:      */     }
/*  2109:      */     
/*  2110:      */     public Builder toBuilder()
/*  2111:      */     {
/*  2112: 1928 */       return newBuilder(this);
/*  2113:      */     }
/*  2114:      */     
/*  2115:      */     protected Builder newBuilderForType(GeneratedMessage.BuilderParent parent)
/*  2116:      */     {
/*  2117: 1933 */       Builder builder = new Builder(parent, null);
/*  2118: 1934 */       return builder;
/*  2119:      */     }
/*  2120:      */     
/*  2121:      */     public static final class Builder
/*  2122:      */       extends GeneratedMessage.Builder<Builder>
/*  2123:      */       implements MRServiceProtos.GetTaskReportResponseProtoOrBuilder
/*  2124:      */     {
/*  2125:      */       private int bitField0_;
/*  2126:      */       
/*  2127:      */       public static final Descriptors.Descriptor getDescriptor()
/*  2128:      */       {
/*  2129: 1944 */         return MRServiceProtos.internal_static_hadoop_mapreduce_GetTaskReportResponseProto_descriptor;
/*  2130:      */       }
/*  2131:      */       
/*  2132:      */       protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable()
/*  2133:      */       {
/*  2134: 1949 */         return MRServiceProtos.internal_static_hadoop_mapreduce_GetTaskReportResponseProto_fieldAccessorTable.ensureFieldAccessorsInitialized(MRServiceProtos.GetTaskReportResponseProto.class, Builder.class);
/*  2135:      */       }
/*  2136:      */       
/*  2137:      */       private Builder()
/*  2138:      */       {
/*  2139: 1956 */         maybeForceBuilderInitialization();
/*  2140:      */       }
/*  2141:      */       
/*  2142:      */       private Builder(GeneratedMessage.BuilderParent parent)
/*  2143:      */       {
/*  2144: 1961 */         super();
/*  2145: 1962 */         maybeForceBuilderInitialization();
/*  2146:      */       }
/*  2147:      */       
/*  2148:      */       private void maybeForceBuilderInitialization()
/*  2149:      */       {
/*  2150: 1965 */         if (MRServiceProtos.GetTaskReportResponseProto.alwaysUseFieldBuilders) {
/*  2151: 1966 */           getTaskReportFieldBuilder();
/*  2152:      */         }
/*  2153:      */       }
/*  2154:      */       
/*  2155:      */       private static Builder create()
/*  2156:      */       {
/*  2157: 1970 */         return new Builder();
/*  2158:      */       }
/*  2159:      */       
/*  2160:      */       public Builder clear()
/*  2161:      */       {
/*  2162: 1974 */         super.clear();
/*  2163: 1975 */         if (this.taskReportBuilder_ == null) {
/*  2164: 1976 */           this.taskReport_ = MRProtos.TaskReportProto.getDefaultInstance();
/*  2165:      */         } else {
/*  2166: 1978 */           this.taskReportBuilder_.clear();
/*  2167:      */         }
/*  2168: 1980 */         this.bitField0_ &= 0xFFFFFFFE;
/*  2169: 1981 */         return this;
/*  2170:      */       }
/*  2171:      */       
/*  2172:      */       public Builder clone()
/*  2173:      */       {
/*  2174: 1985 */         return create().mergeFrom(buildPartial());
/*  2175:      */       }
/*  2176:      */       
/*  2177:      */       public Descriptors.Descriptor getDescriptorForType()
/*  2178:      */       {
/*  2179: 1990 */         return MRServiceProtos.internal_static_hadoop_mapreduce_GetTaskReportResponseProto_descriptor;
/*  2180:      */       }
/*  2181:      */       
/*  2182:      */       public MRServiceProtos.GetTaskReportResponseProto getDefaultInstanceForType()
/*  2183:      */       {
/*  2184: 1994 */         return MRServiceProtos.GetTaskReportResponseProto.getDefaultInstance();
/*  2185:      */       }
/*  2186:      */       
/*  2187:      */       public MRServiceProtos.GetTaskReportResponseProto build()
/*  2188:      */       {
/*  2189: 1998 */         MRServiceProtos.GetTaskReportResponseProto result = buildPartial();
/*  2190: 1999 */         if (!result.isInitialized()) {
/*  2191: 2000 */           throw newUninitializedMessageException(result);
/*  2192:      */         }
/*  2193: 2002 */         return result;
/*  2194:      */       }
/*  2195:      */       
/*  2196:      */       public MRServiceProtos.GetTaskReportResponseProto buildPartial()
/*  2197:      */       {
/*  2198: 2006 */         MRServiceProtos.GetTaskReportResponseProto result = new MRServiceProtos.GetTaskReportResponseProto(this, null);
/*  2199: 2007 */         int from_bitField0_ = this.bitField0_;
/*  2200: 2008 */         int to_bitField0_ = 0;
/*  2201: 2009 */         if ((from_bitField0_ & 0x1) == 1) {
/*  2202: 2010 */           to_bitField0_ |= 0x1;
/*  2203:      */         }
/*  2204: 2012 */         if (this.taskReportBuilder_ == null) {
/*  2205: 2013 */           result.taskReport_ = this.taskReport_;
/*  2206:      */         } else {
/*  2207: 2015 */           result.taskReport_ = ((MRProtos.TaskReportProto)this.taskReportBuilder_.build());
/*  2208:      */         }
/*  2209: 2017 */         result.bitField0_ = to_bitField0_;
/*  2210: 2018 */         onBuilt();
/*  2211: 2019 */         return result;
/*  2212:      */       }
/*  2213:      */       
/*  2214:      */       public Builder mergeFrom(Message other)
/*  2215:      */       {
/*  2216: 2023 */         if ((other instanceof MRServiceProtos.GetTaskReportResponseProto)) {
/*  2217: 2024 */           return mergeFrom((MRServiceProtos.GetTaskReportResponseProto)other);
/*  2218:      */         }
/*  2219: 2026 */         super.mergeFrom(other);
/*  2220: 2027 */         return this;
/*  2221:      */       }
/*  2222:      */       
/*  2223:      */       public Builder mergeFrom(MRServiceProtos.GetTaskReportResponseProto other)
/*  2224:      */       {
/*  2225: 2032 */         if (other == MRServiceProtos.GetTaskReportResponseProto.getDefaultInstance()) {
/*  2226: 2032 */           return this;
/*  2227:      */         }
/*  2228: 2033 */         if (other.hasTaskReport()) {
/*  2229: 2034 */           mergeTaskReport(other.getTaskReport());
/*  2230:      */         }
/*  2231: 2036 */         mergeUnknownFields(other.getUnknownFields());
/*  2232: 2037 */         return this;
/*  2233:      */       }
/*  2234:      */       
/*  2235:      */       public final boolean isInitialized()
/*  2236:      */       {
/*  2237: 2041 */         return true;
/*  2238:      */       }
/*  2239:      */       
/*  2240:      */       public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry)
/*  2241:      */         throws IOException
/*  2242:      */       {
/*  2243: 2048 */         MRServiceProtos.GetTaskReportResponseProto parsedMessage = null;
/*  2244:      */         try
/*  2245:      */         {
/*  2246: 2050 */           parsedMessage = (MRServiceProtos.GetTaskReportResponseProto)MRServiceProtos.GetTaskReportResponseProto.PARSER.parsePartialFrom(input, extensionRegistry);
/*  2247:      */         }
/*  2248:      */         catch (InvalidProtocolBufferException e)
/*  2249:      */         {
/*  2250: 2052 */           parsedMessage = (MRServiceProtos.GetTaskReportResponseProto)e.getUnfinishedMessage();
/*  2251: 2053 */           throw e;
/*  2252:      */         }
/*  2253:      */         finally
/*  2254:      */         {
/*  2255: 2055 */           if (parsedMessage != null) {
/*  2256: 2056 */             mergeFrom(parsedMessage);
/*  2257:      */           }
/*  2258:      */         }
/*  2259: 2059 */         return this;
/*  2260:      */       }
/*  2261:      */       
/*  2262: 2064 */       private MRProtos.TaskReportProto taskReport_ = MRProtos.TaskReportProto.getDefaultInstance();
/*  2263:      */       private SingleFieldBuilder<MRProtos.TaskReportProto, MRProtos.TaskReportProto.Builder, MRProtos.TaskReportProtoOrBuilder> taskReportBuilder_;
/*  2264:      */       
/*  2265:      */       public boolean hasTaskReport()
/*  2266:      */       {
/*  2267: 2071 */         return (this.bitField0_ & 0x1) == 1;
/*  2268:      */       }
/*  2269:      */       
/*  2270:      */       public MRProtos.TaskReportProto getTaskReport()
/*  2271:      */       {
/*  2272: 2077 */         if (this.taskReportBuilder_ == null) {
/*  2273: 2078 */           return this.taskReport_;
/*  2274:      */         }
/*  2275: 2080 */         return (MRProtos.TaskReportProto)this.taskReportBuilder_.getMessage();
/*  2276:      */       }
/*  2277:      */       
/*  2278:      */       public Builder setTaskReport(MRProtos.TaskReportProto value)
/*  2279:      */       {
/*  2280: 2087 */         if (this.taskReportBuilder_ == null)
/*  2281:      */         {
/*  2282: 2088 */           if (value == null) {
/*  2283: 2089 */             throw new NullPointerException();
/*  2284:      */           }
/*  2285: 2091 */           this.taskReport_ = value;
/*  2286: 2092 */           onChanged();
/*  2287:      */         }
/*  2288:      */         else
/*  2289:      */         {
/*  2290: 2094 */           this.taskReportBuilder_.setMessage(value);
/*  2291:      */         }
/*  2292: 2096 */         this.bitField0_ |= 0x1;
/*  2293: 2097 */         return this;
/*  2294:      */       }
/*  2295:      */       
/*  2296:      */       public Builder setTaskReport(MRProtos.TaskReportProto.Builder builderForValue)
/*  2297:      */       {
/*  2298: 2104 */         if (this.taskReportBuilder_ == null)
/*  2299:      */         {
/*  2300: 2105 */           this.taskReport_ = builderForValue.build();
/*  2301: 2106 */           onChanged();
/*  2302:      */         }
/*  2303:      */         else
/*  2304:      */         {
/*  2305: 2108 */           this.taskReportBuilder_.setMessage(builderForValue.build());
/*  2306:      */         }
/*  2307: 2110 */         this.bitField0_ |= 0x1;
/*  2308: 2111 */         return this;
/*  2309:      */       }
/*  2310:      */       
/*  2311:      */       public Builder mergeTaskReport(MRProtos.TaskReportProto value)
/*  2312:      */       {
/*  2313: 2117 */         if (this.taskReportBuilder_ == null)
/*  2314:      */         {
/*  2315: 2118 */           if (((this.bitField0_ & 0x1) == 1) && (this.taskReport_ != MRProtos.TaskReportProto.getDefaultInstance())) {
/*  2316: 2120 */             this.taskReport_ = MRProtos.TaskReportProto.newBuilder(this.taskReport_).mergeFrom(value).buildPartial();
/*  2317:      */           } else {
/*  2318: 2123 */             this.taskReport_ = value;
/*  2319:      */           }
/*  2320: 2125 */           onChanged();
/*  2321:      */         }
/*  2322:      */         else
/*  2323:      */         {
/*  2324: 2127 */           this.taskReportBuilder_.mergeFrom(value);
/*  2325:      */         }
/*  2326: 2129 */         this.bitField0_ |= 0x1;
/*  2327: 2130 */         return this;
/*  2328:      */       }
/*  2329:      */       
/*  2330:      */       public Builder clearTaskReport()
/*  2331:      */       {
/*  2332: 2136 */         if (this.taskReportBuilder_ == null)
/*  2333:      */         {
/*  2334: 2137 */           this.taskReport_ = MRProtos.TaskReportProto.getDefaultInstance();
/*  2335: 2138 */           onChanged();
/*  2336:      */         }
/*  2337:      */         else
/*  2338:      */         {
/*  2339: 2140 */           this.taskReportBuilder_.clear();
/*  2340:      */         }
/*  2341: 2142 */         this.bitField0_ &= 0xFFFFFFFE;
/*  2342: 2143 */         return this;
/*  2343:      */       }
/*  2344:      */       
/*  2345:      */       public MRProtos.TaskReportProto.Builder getTaskReportBuilder()
/*  2346:      */       {
/*  2347: 2149 */         this.bitField0_ |= 0x1;
/*  2348: 2150 */         onChanged();
/*  2349: 2151 */         return (MRProtos.TaskReportProto.Builder)getTaskReportFieldBuilder().getBuilder();
/*  2350:      */       }
/*  2351:      */       
/*  2352:      */       public MRProtos.TaskReportProtoOrBuilder getTaskReportOrBuilder()
/*  2353:      */       {
/*  2354: 2157 */         if (this.taskReportBuilder_ != null) {
/*  2355: 2158 */           return (MRProtos.TaskReportProtoOrBuilder)this.taskReportBuilder_.getMessageOrBuilder();
/*  2356:      */         }
/*  2357: 2160 */         return this.taskReport_;
/*  2358:      */       }
/*  2359:      */       
/*  2360:      */       private SingleFieldBuilder<MRProtos.TaskReportProto, MRProtos.TaskReportProto.Builder, MRProtos.TaskReportProtoOrBuilder> getTaskReportFieldBuilder()
/*  2361:      */       {
/*  2362: 2169 */         if (this.taskReportBuilder_ == null)
/*  2363:      */         {
/*  2364: 2170 */           this.taskReportBuilder_ = new SingleFieldBuilder(this.taskReport_, getParentForChildren(), isClean());
/*  2365:      */           
/*  2366:      */ 
/*  2367:      */ 
/*  2368:      */ 
/*  2369: 2175 */           this.taskReport_ = null;
/*  2370:      */         }
/*  2371: 2177 */         return this.taskReportBuilder_;
/*  2372:      */       }
/*  2373:      */     }
/*  2374:      */     
/*  2375:      */     static
/*  2376:      */     {
/*  2377: 2184 */       defaultInstance = new GetTaskReportResponseProto(true);
/*  2378: 2185 */       defaultInstance.initFields();
/*  2379:      */     }
/*  2380:      */   }
/*  2381:      */   
/*  2382:      */   public static abstract interface GetTaskAttemptReportRequestProtoOrBuilder
/*  2383:      */     extends MessageOrBuilder
/*  2384:      */   {
/*  2385:      */     public abstract boolean hasTaskAttemptId();
/*  2386:      */     
/*  2387:      */     public abstract MRProtos.TaskAttemptIdProto getTaskAttemptId();
/*  2388:      */     
/*  2389:      */     public abstract MRProtos.TaskAttemptIdProtoOrBuilder getTaskAttemptIdOrBuilder();
/*  2390:      */   }
/*  2391:      */   
/*  2392:      */   public static final class GetTaskAttemptReportRequestProto
/*  2393:      */     extends GeneratedMessage
/*  2394:      */     implements MRServiceProtos.GetTaskAttemptReportRequestProtoOrBuilder
/*  2395:      */   {
/*  2396:      */     private static final GetTaskAttemptReportRequestProto defaultInstance;
/*  2397:      */     private final UnknownFieldSet unknownFields;
/*  2398:      */     
/*  2399:      */     private GetTaskAttemptReportRequestProto(GeneratedMessage.Builder<?> builder)
/*  2400:      */     {
/*  2401: 2216 */       super();
/*  2402: 2217 */       this.unknownFields = builder.getUnknownFields();
/*  2403:      */     }
/*  2404:      */     
/*  2405:      */     private GetTaskAttemptReportRequestProto(boolean noInit)
/*  2406:      */     {
/*  2407: 2219 */       this.unknownFields = UnknownFieldSet.getDefaultInstance();
/*  2408:      */     }
/*  2409:      */     
/*  2410:      */     public static GetTaskAttemptReportRequestProto getDefaultInstance()
/*  2411:      */     {
/*  2412: 2223 */       return defaultInstance;
/*  2413:      */     }
/*  2414:      */     
/*  2415:      */     public GetTaskAttemptReportRequestProto getDefaultInstanceForType()
/*  2416:      */     {
/*  2417: 2227 */       return defaultInstance;
/*  2418:      */     }
/*  2419:      */     
/*  2420:      */     public final UnknownFieldSet getUnknownFields()
/*  2421:      */     {
/*  2422: 2234 */       return this.unknownFields;
/*  2423:      */     }
/*  2424:      */     
/*  2425:      */     private GetTaskAttemptReportRequestProto(CodedInputStream input, ExtensionRegistryLite extensionRegistry)
/*  2426:      */       throws InvalidProtocolBufferException
/*  2427:      */     {
/*  2428: 2240 */       initFields();
/*  2429: 2241 */       int mutable_bitField0_ = 0;
/*  2430: 2242 */       UnknownFieldSet.Builder unknownFields = UnknownFieldSet.newBuilder();
/*  2431:      */       try
/*  2432:      */       {
/*  2433: 2245 */         boolean done = false;
/*  2434: 2246 */         while (!done)
/*  2435:      */         {
/*  2436: 2247 */           int tag = input.readTag();
/*  2437: 2248 */           switch (tag)
/*  2438:      */           {
/*  2439:      */           case 0: 
/*  2440: 2250 */             done = true;
/*  2441: 2251 */             break;
/*  2442:      */           default: 
/*  2443: 2253 */             if (!parseUnknownField(input, unknownFields, extensionRegistry, tag)) {
/*  2444: 2255 */               done = true;
/*  2445:      */             }
/*  2446:      */             break;
/*  2447:      */           case 10: 
/*  2448: 2260 */             MRProtos.TaskAttemptIdProto.Builder subBuilder = null;
/*  2449: 2261 */             if ((this.bitField0_ & 0x1) == 1) {
/*  2450: 2262 */               subBuilder = this.taskAttemptId_.toBuilder();
/*  2451:      */             }
/*  2452: 2264 */             this.taskAttemptId_ = ((MRProtos.TaskAttemptIdProto)input.readMessage(MRProtos.TaskAttemptIdProto.PARSER, extensionRegistry));
/*  2453: 2265 */             if (subBuilder != null)
/*  2454:      */             {
/*  2455: 2266 */               subBuilder.mergeFrom(this.taskAttemptId_);
/*  2456: 2267 */               this.taskAttemptId_ = subBuilder.buildPartial();
/*  2457:      */             }
/*  2458: 2269 */             this.bitField0_ |= 0x1;
/*  2459:      */           }
/*  2460:      */         }
/*  2461:      */       }
/*  2462:      */       catch (InvalidProtocolBufferException e)
/*  2463:      */       {
/*  2464: 2275 */         throw e.setUnfinishedMessage(this);
/*  2465:      */       }
/*  2466:      */       catch (IOException e)
/*  2467:      */       {
/*  2468: 2277 */         throw new InvalidProtocolBufferException(e.getMessage()).setUnfinishedMessage(this);
/*  2469:      */       }
/*  2470:      */       finally
/*  2471:      */       {
/*  2472: 2280 */         this.unknownFields = unknownFields.build();
/*  2473: 2281 */         makeExtensionsImmutable();
/*  2474:      */       }
/*  2475:      */     }
/*  2476:      */     
/*  2477:      */     public static final Descriptors.Descriptor getDescriptor()
/*  2478:      */     {
/*  2479: 2286 */       return MRServiceProtos.internal_static_hadoop_mapreduce_GetTaskAttemptReportRequestProto_descriptor;
/*  2480:      */     }
/*  2481:      */     
/*  2482:      */     protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable()
/*  2483:      */     {
/*  2484: 2291 */       return MRServiceProtos.internal_static_hadoop_mapreduce_GetTaskAttemptReportRequestProto_fieldAccessorTable.ensureFieldAccessorsInitialized(GetTaskAttemptReportRequestProto.class, Builder.class);
/*  2485:      */     }
/*  2486:      */     
/*  2487: 2296 */     public static Parser<GetTaskAttemptReportRequestProto> PARSER = new AbstractParser()
/*  2488:      */     {
/*  2489:      */       public MRServiceProtos.GetTaskAttemptReportRequestProto parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry)
/*  2490:      */         throws InvalidProtocolBufferException
/*  2491:      */       {
/*  2492: 2302 */         return new MRServiceProtos.GetTaskAttemptReportRequestProto(input, extensionRegistry, null);
/*  2493:      */       }
/*  2494:      */     };
/*  2495:      */     private int bitField0_;
/*  2496:      */     public static final int TASK_ATTEMPT_ID_FIELD_NUMBER = 1;
/*  2497:      */     private MRProtos.TaskAttemptIdProto taskAttemptId_;
/*  2498:      */     
/*  2499:      */     public Parser<GetTaskAttemptReportRequestProto> getParserForType()
/*  2500:      */     {
/*  2501: 2308 */       return PARSER;
/*  2502:      */     }
/*  2503:      */     
/*  2504:      */     public boolean hasTaskAttemptId()
/*  2505:      */     {
/*  2506: 2319 */       return (this.bitField0_ & 0x1) == 1;
/*  2507:      */     }
/*  2508:      */     
/*  2509:      */     public MRProtos.TaskAttemptIdProto getTaskAttemptId()
/*  2510:      */     {
/*  2511: 2325 */       return this.taskAttemptId_;
/*  2512:      */     }
/*  2513:      */     
/*  2514:      */     public MRProtos.TaskAttemptIdProtoOrBuilder getTaskAttemptIdOrBuilder()
/*  2515:      */     {
/*  2516: 2331 */       return this.taskAttemptId_;
/*  2517:      */     }
/*  2518:      */     
/*  2519:      */     private void initFields()
/*  2520:      */     {
/*  2521: 2335 */       this.taskAttemptId_ = MRProtos.TaskAttemptIdProto.getDefaultInstance();
/*  2522:      */     }
/*  2523:      */     
/*  2524: 2337 */     private byte memoizedIsInitialized = -1;
/*  2525:      */     
/*  2526:      */     public final boolean isInitialized()
/*  2527:      */     {
/*  2528: 2339 */       byte isInitialized = this.memoizedIsInitialized;
/*  2529: 2340 */       if (isInitialized != -1) {
/*  2530: 2340 */         return isInitialized == 1;
/*  2531:      */       }
/*  2532: 2342 */       this.memoizedIsInitialized = 1;
/*  2533: 2343 */       return true;
/*  2534:      */     }
/*  2535:      */     
/*  2536:      */     public void writeTo(CodedOutputStream output)
/*  2537:      */       throws IOException
/*  2538:      */     {
/*  2539: 2348 */       getSerializedSize();
/*  2540: 2349 */       if ((this.bitField0_ & 0x1) == 1) {
/*  2541: 2350 */         output.writeMessage(1, this.taskAttemptId_);
/*  2542:      */       }
/*  2543: 2352 */       getUnknownFields().writeTo(output);
/*  2544:      */     }
/*  2545:      */     
/*  2546: 2355 */     private int memoizedSerializedSize = -1;
/*  2547:      */     private static final long serialVersionUID = 0L;
/*  2548:      */     
/*  2549:      */     public int getSerializedSize()
/*  2550:      */     {
/*  2551: 2357 */       int size = this.memoizedSerializedSize;
/*  2552: 2358 */       if (size != -1) {
/*  2553: 2358 */         return size;
/*  2554:      */       }
/*  2555: 2360 */       size = 0;
/*  2556: 2361 */       if ((this.bitField0_ & 0x1) == 1) {
/*  2557: 2362 */         size += CodedOutputStream.computeMessageSize(1, this.taskAttemptId_);
/*  2558:      */       }
/*  2559: 2365 */       size += getUnknownFields().getSerializedSize();
/*  2560: 2366 */       this.memoizedSerializedSize = size;
/*  2561: 2367 */       return size;
/*  2562:      */     }
/*  2563:      */     
/*  2564:      */     protected Object writeReplace()
/*  2565:      */       throws ObjectStreamException
/*  2566:      */     {
/*  2567: 2374 */       return super.writeReplace();
/*  2568:      */     }
/*  2569:      */     
/*  2570:      */     public boolean equals(Object obj)
/*  2571:      */     {
/*  2572: 2379 */       if (obj == this) {
/*  2573: 2380 */         return true;
/*  2574:      */       }
/*  2575: 2382 */       if (!(obj instanceof GetTaskAttemptReportRequestProto)) {
/*  2576: 2383 */         return super.equals(obj);
/*  2577:      */       }
/*  2578: 2385 */       GetTaskAttemptReportRequestProto other = (GetTaskAttemptReportRequestProto)obj;
/*  2579:      */       
/*  2580: 2387 */       boolean result = true;
/*  2581: 2388 */       result = (result) && (hasTaskAttemptId() == other.hasTaskAttemptId());
/*  2582: 2389 */       if (hasTaskAttemptId()) {
/*  2583: 2390 */         result = (result) && (getTaskAttemptId().equals(other.getTaskAttemptId()));
/*  2584:      */       }
/*  2585: 2393 */       result = (result) && (getUnknownFields().equals(other.getUnknownFields()));
/*  2586:      */       
/*  2587: 2395 */       return result;
/*  2588:      */     }
/*  2589:      */     
/*  2590: 2398 */     private int memoizedHashCode = 0;
/*  2591:      */     
/*  2592:      */     public int hashCode()
/*  2593:      */     {
/*  2594: 2401 */       if (this.memoizedHashCode != 0) {
/*  2595: 2402 */         return this.memoizedHashCode;
/*  2596:      */       }
/*  2597: 2404 */       int hash = 41;
/*  2598: 2405 */       hash = 19 * hash + getDescriptorForType().hashCode();
/*  2599: 2406 */       if (hasTaskAttemptId())
/*  2600:      */       {
/*  2601: 2407 */         hash = 37 * hash + 1;
/*  2602: 2408 */         hash = 53 * hash + getTaskAttemptId().hashCode();
/*  2603:      */       }
/*  2604: 2410 */       hash = 29 * hash + getUnknownFields().hashCode();
/*  2605: 2411 */       this.memoizedHashCode = hash;
/*  2606: 2412 */       return hash;
/*  2607:      */     }
/*  2608:      */     
/*  2609:      */     public static GetTaskAttemptReportRequestProto parseFrom(ByteString data)
/*  2610:      */       throws InvalidProtocolBufferException
/*  2611:      */     {
/*  2612: 2418 */       return (GetTaskAttemptReportRequestProto)PARSER.parseFrom(data);
/*  2613:      */     }
/*  2614:      */     
/*  2615:      */     public static GetTaskAttemptReportRequestProto parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry)
/*  2616:      */       throws InvalidProtocolBufferException
/*  2617:      */     {
/*  2618: 2424 */       return (GetTaskAttemptReportRequestProto)PARSER.parseFrom(data, extensionRegistry);
/*  2619:      */     }
/*  2620:      */     
/*  2621:      */     public static GetTaskAttemptReportRequestProto parseFrom(byte[] data)
/*  2622:      */       throws InvalidProtocolBufferException
/*  2623:      */     {
/*  2624: 2428 */       return (GetTaskAttemptReportRequestProto)PARSER.parseFrom(data);
/*  2625:      */     }
/*  2626:      */     
/*  2627:      */     public static GetTaskAttemptReportRequestProto parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry)
/*  2628:      */       throws InvalidProtocolBufferException
/*  2629:      */     {
/*  2630: 2434 */       return (GetTaskAttemptReportRequestProto)PARSER.parseFrom(data, extensionRegistry);
/*  2631:      */     }
/*  2632:      */     
/*  2633:      */     public static GetTaskAttemptReportRequestProto parseFrom(InputStream input)
/*  2634:      */       throws IOException
/*  2635:      */     {
/*  2636: 2438 */       return (GetTaskAttemptReportRequestProto)PARSER.parseFrom(input);
/*  2637:      */     }
/*  2638:      */     
/*  2639:      */     public static GetTaskAttemptReportRequestProto parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry)
/*  2640:      */       throws IOException
/*  2641:      */     {
/*  2642: 2444 */       return (GetTaskAttemptReportRequestProto)PARSER.parseFrom(input, extensionRegistry);
/*  2643:      */     }
/*  2644:      */     
/*  2645:      */     public static GetTaskAttemptReportRequestProto parseDelimitedFrom(InputStream input)
/*  2646:      */       throws IOException
/*  2647:      */     {
/*  2648: 2448 */       return (GetTaskAttemptReportRequestProto)PARSER.parseDelimitedFrom(input);
/*  2649:      */     }
/*  2650:      */     
/*  2651:      */     public static GetTaskAttemptReportRequestProto parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry)
/*  2652:      */       throws IOException
/*  2653:      */     {
/*  2654: 2454 */       return (GetTaskAttemptReportRequestProto)PARSER.parseDelimitedFrom(input, extensionRegistry);
/*  2655:      */     }
/*  2656:      */     
/*  2657:      */     public static GetTaskAttemptReportRequestProto parseFrom(CodedInputStream input)
/*  2658:      */       throws IOException
/*  2659:      */     {
/*  2660: 2459 */       return (GetTaskAttemptReportRequestProto)PARSER.parseFrom(input);
/*  2661:      */     }
/*  2662:      */     
/*  2663:      */     public static GetTaskAttemptReportRequestProto parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry)
/*  2664:      */       throws IOException
/*  2665:      */     {
/*  2666: 2465 */       return (GetTaskAttemptReportRequestProto)PARSER.parseFrom(input, extensionRegistry);
/*  2667:      */     }
/*  2668:      */     
/*  2669:      */     public static Builder newBuilder()
/*  2670:      */     {
/*  2671: 2468 */       return Builder.access$3900();
/*  2672:      */     }
/*  2673:      */     
/*  2674:      */     public Builder newBuilderForType()
/*  2675:      */     {
/*  2676: 2469 */       return newBuilder();
/*  2677:      */     }
/*  2678:      */     
/*  2679:      */     public static Builder newBuilder(GetTaskAttemptReportRequestProto prototype)
/*  2680:      */     {
/*  2681: 2471 */       return newBuilder().mergeFrom(prototype);
/*  2682:      */     }
/*  2683:      */     
/*  2684:      */     public Builder toBuilder()
/*  2685:      */     {
/*  2686: 2473 */       return newBuilder(this);
/*  2687:      */     }
/*  2688:      */     
/*  2689:      */     protected Builder newBuilderForType(GeneratedMessage.BuilderParent parent)
/*  2690:      */     {
/*  2691: 2478 */       Builder builder = new Builder(parent, null);
/*  2692: 2479 */       return builder;
/*  2693:      */     }
/*  2694:      */     
/*  2695:      */     public static final class Builder
/*  2696:      */       extends GeneratedMessage.Builder<Builder>
/*  2697:      */       implements MRServiceProtos.GetTaskAttemptReportRequestProtoOrBuilder
/*  2698:      */     {
/*  2699:      */       private int bitField0_;
/*  2700:      */       
/*  2701:      */       public static final Descriptors.Descriptor getDescriptor()
/*  2702:      */       {
/*  2703: 2489 */         return MRServiceProtos.internal_static_hadoop_mapreduce_GetTaskAttemptReportRequestProto_descriptor;
/*  2704:      */       }
/*  2705:      */       
/*  2706:      */       protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable()
/*  2707:      */       {
/*  2708: 2494 */         return MRServiceProtos.internal_static_hadoop_mapreduce_GetTaskAttemptReportRequestProto_fieldAccessorTable.ensureFieldAccessorsInitialized(MRServiceProtos.GetTaskAttemptReportRequestProto.class, Builder.class);
/*  2709:      */       }
/*  2710:      */       
/*  2711:      */       private Builder()
/*  2712:      */       {
/*  2713: 2501 */         maybeForceBuilderInitialization();
/*  2714:      */       }
/*  2715:      */       
/*  2716:      */       private Builder(GeneratedMessage.BuilderParent parent)
/*  2717:      */       {
/*  2718: 2506 */         super();
/*  2719: 2507 */         maybeForceBuilderInitialization();
/*  2720:      */       }
/*  2721:      */       
/*  2722:      */       private void maybeForceBuilderInitialization()
/*  2723:      */       {
/*  2724: 2510 */         if (MRServiceProtos.GetTaskAttemptReportRequestProto.alwaysUseFieldBuilders) {
/*  2725: 2511 */           getTaskAttemptIdFieldBuilder();
/*  2726:      */         }
/*  2727:      */       }
/*  2728:      */       
/*  2729:      */       private static Builder create()
/*  2730:      */       {
/*  2731: 2515 */         return new Builder();
/*  2732:      */       }
/*  2733:      */       
/*  2734:      */       public Builder clear()
/*  2735:      */       {
/*  2736: 2519 */         super.clear();
/*  2737: 2520 */         if (this.taskAttemptIdBuilder_ == null) {
/*  2738: 2521 */           this.taskAttemptId_ = MRProtos.TaskAttemptIdProto.getDefaultInstance();
/*  2739:      */         } else {
/*  2740: 2523 */           this.taskAttemptIdBuilder_.clear();
/*  2741:      */         }
/*  2742: 2525 */         this.bitField0_ &= 0xFFFFFFFE;
/*  2743: 2526 */         return this;
/*  2744:      */       }
/*  2745:      */       
/*  2746:      */       public Builder clone()
/*  2747:      */       {
/*  2748: 2530 */         return create().mergeFrom(buildPartial());
/*  2749:      */       }
/*  2750:      */       
/*  2751:      */       public Descriptors.Descriptor getDescriptorForType()
/*  2752:      */       {
/*  2753: 2535 */         return MRServiceProtos.internal_static_hadoop_mapreduce_GetTaskAttemptReportRequestProto_descriptor;
/*  2754:      */       }
/*  2755:      */       
/*  2756:      */       public MRServiceProtos.GetTaskAttemptReportRequestProto getDefaultInstanceForType()
/*  2757:      */       {
/*  2758: 2539 */         return MRServiceProtos.GetTaskAttemptReportRequestProto.getDefaultInstance();
/*  2759:      */       }
/*  2760:      */       
/*  2761:      */       public MRServiceProtos.GetTaskAttemptReportRequestProto build()
/*  2762:      */       {
/*  2763: 2543 */         MRServiceProtos.GetTaskAttemptReportRequestProto result = buildPartial();
/*  2764: 2544 */         if (!result.isInitialized()) {
/*  2765: 2545 */           throw newUninitializedMessageException(result);
/*  2766:      */         }
/*  2767: 2547 */         return result;
/*  2768:      */       }
/*  2769:      */       
/*  2770:      */       public MRServiceProtos.GetTaskAttemptReportRequestProto buildPartial()
/*  2771:      */       {
/*  2772: 2551 */         MRServiceProtos.GetTaskAttemptReportRequestProto result = new MRServiceProtos.GetTaskAttemptReportRequestProto(this, null);
/*  2773: 2552 */         int from_bitField0_ = this.bitField0_;
/*  2774: 2553 */         int to_bitField0_ = 0;
/*  2775: 2554 */         if ((from_bitField0_ & 0x1) == 1) {
/*  2776: 2555 */           to_bitField0_ |= 0x1;
/*  2777:      */         }
/*  2778: 2557 */         if (this.taskAttemptIdBuilder_ == null) {
/*  2779: 2558 */           result.taskAttemptId_ = this.taskAttemptId_;
/*  2780:      */         } else {
/*  2781: 2560 */           result.taskAttemptId_ = ((MRProtos.TaskAttemptIdProto)this.taskAttemptIdBuilder_.build());
/*  2782:      */         }
/*  2783: 2562 */         result.bitField0_ = to_bitField0_;
/*  2784: 2563 */         onBuilt();
/*  2785: 2564 */         return result;
/*  2786:      */       }
/*  2787:      */       
/*  2788:      */       public Builder mergeFrom(Message other)
/*  2789:      */       {
/*  2790: 2568 */         if ((other instanceof MRServiceProtos.GetTaskAttemptReportRequestProto)) {
/*  2791: 2569 */           return mergeFrom((MRServiceProtos.GetTaskAttemptReportRequestProto)other);
/*  2792:      */         }
/*  2793: 2571 */         super.mergeFrom(other);
/*  2794: 2572 */         return this;
/*  2795:      */       }
/*  2796:      */       
/*  2797:      */       public Builder mergeFrom(MRServiceProtos.GetTaskAttemptReportRequestProto other)
/*  2798:      */       {
/*  2799: 2577 */         if (other == MRServiceProtos.GetTaskAttemptReportRequestProto.getDefaultInstance()) {
/*  2800: 2577 */           return this;
/*  2801:      */         }
/*  2802: 2578 */         if (other.hasTaskAttemptId()) {
/*  2803: 2579 */           mergeTaskAttemptId(other.getTaskAttemptId());
/*  2804:      */         }
/*  2805: 2581 */         mergeUnknownFields(other.getUnknownFields());
/*  2806: 2582 */         return this;
/*  2807:      */       }
/*  2808:      */       
/*  2809:      */       public final boolean isInitialized()
/*  2810:      */       {
/*  2811: 2586 */         return true;
/*  2812:      */       }
/*  2813:      */       
/*  2814:      */       public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry)
/*  2815:      */         throws IOException
/*  2816:      */       {
/*  2817: 2593 */         MRServiceProtos.GetTaskAttemptReportRequestProto parsedMessage = null;
/*  2818:      */         try
/*  2819:      */         {
/*  2820: 2595 */           parsedMessage = (MRServiceProtos.GetTaskAttemptReportRequestProto)MRServiceProtos.GetTaskAttemptReportRequestProto.PARSER.parsePartialFrom(input, extensionRegistry);
/*  2821:      */         }
/*  2822:      */         catch (InvalidProtocolBufferException e)
/*  2823:      */         {
/*  2824: 2597 */           parsedMessage = (MRServiceProtos.GetTaskAttemptReportRequestProto)e.getUnfinishedMessage();
/*  2825: 2598 */           throw e;
/*  2826:      */         }
/*  2827:      */         finally
/*  2828:      */         {
/*  2829: 2600 */           if (parsedMessage != null) {
/*  2830: 2601 */             mergeFrom(parsedMessage);
/*  2831:      */           }
/*  2832:      */         }
/*  2833: 2604 */         return this;
/*  2834:      */       }
/*  2835:      */       
/*  2836: 2609 */       private MRProtos.TaskAttemptIdProto taskAttemptId_ = MRProtos.TaskAttemptIdProto.getDefaultInstance();
/*  2837:      */       private SingleFieldBuilder<MRProtos.TaskAttemptIdProto, MRProtos.TaskAttemptIdProto.Builder, MRProtos.TaskAttemptIdProtoOrBuilder> taskAttemptIdBuilder_;
/*  2838:      */       
/*  2839:      */       public boolean hasTaskAttemptId()
/*  2840:      */       {
/*  2841: 2616 */         return (this.bitField0_ & 0x1) == 1;
/*  2842:      */       }
/*  2843:      */       
/*  2844:      */       public MRProtos.TaskAttemptIdProto getTaskAttemptId()
/*  2845:      */       {
/*  2846: 2622 */         if (this.taskAttemptIdBuilder_ == null) {
/*  2847: 2623 */           return this.taskAttemptId_;
/*  2848:      */         }
/*  2849: 2625 */         return (MRProtos.TaskAttemptIdProto)this.taskAttemptIdBuilder_.getMessage();
/*  2850:      */       }
/*  2851:      */       
/*  2852:      */       public Builder setTaskAttemptId(MRProtos.TaskAttemptIdProto value)
/*  2853:      */       {
/*  2854: 2632 */         if (this.taskAttemptIdBuilder_ == null)
/*  2855:      */         {
/*  2856: 2633 */           if (value == null) {
/*  2857: 2634 */             throw new NullPointerException();
/*  2858:      */           }
/*  2859: 2636 */           this.taskAttemptId_ = value;
/*  2860: 2637 */           onChanged();
/*  2861:      */         }
/*  2862:      */         else
/*  2863:      */         {
/*  2864: 2639 */           this.taskAttemptIdBuilder_.setMessage(value);
/*  2865:      */         }
/*  2866: 2641 */         this.bitField0_ |= 0x1;
/*  2867: 2642 */         return this;
/*  2868:      */       }
/*  2869:      */       
/*  2870:      */       public Builder setTaskAttemptId(MRProtos.TaskAttemptIdProto.Builder builderForValue)
/*  2871:      */       {
/*  2872: 2649 */         if (this.taskAttemptIdBuilder_ == null)
/*  2873:      */         {
/*  2874: 2650 */           this.taskAttemptId_ = builderForValue.build();
/*  2875: 2651 */           onChanged();
/*  2876:      */         }
/*  2877:      */         else
/*  2878:      */         {
/*  2879: 2653 */           this.taskAttemptIdBuilder_.setMessage(builderForValue.build());
/*  2880:      */         }
/*  2881: 2655 */         this.bitField0_ |= 0x1;
/*  2882: 2656 */         return this;
/*  2883:      */       }
/*  2884:      */       
/*  2885:      */       public Builder mergeTaskAttemptId(MRProtos.TaskAttemptIdProto value)
/*  2886:      */       {
/*  2887: 2662 */         if (this.taskAttemptIdBuilder_ == null)
/*  2888:      */         {
/*  2889: 2663 */           if (((this.bitField0_ & 0x1) == 1) && (this.taskAttemptId_ != MRProtos.TaskAttemptIdProto.getDefaultInstance())) {
/*  2890: 2665 */             this.taskAttemptId_ = MRProtos.TaskAttemptIdProto.newBuilder(this.taskAttemptId_).mergeFrom(value).buildPartial();
/*  2891:      */           } else {
/*  2892: 2668 */             this.taskAttemptId_ = value;
/*  2893:      */           }
/*  2894: 2670 */           onChanged();
/*  2895:      */         }
/*  2896:      */         else
/*  2897:      */         {
/*  2898: 2672 */           this.taskAttemptIdBuilder_.mergeFrom(value);
/*  2899:      */         }
/*  2900: 2674 */         this.bitField0_ |= 0x1;
/*  2901: 2675 */         return this;
/*  2902:      */       }
/*  2903:      */       
/*  2904:      */       public Builder clearTaskAttemptId()
/*  2905:      */       {
/*  2906: 2681 */         if (this.taskAttemptIdBuilder_ == null)
/*  2907:      */         {
/*  2908: 2682 */           this.taskAttemptId_ = MRProtos.TaskAttemptIdProto.getDefaultInstance();
/*  2909: 2683 */           onChanged();
/*  2910:      */         }
/*  2911:      */         else
/*  2912:      */         {
/*  2913: 2685 */           this.taskAttemptIdBuilder_.clear();
/*  2914:      */         }
/*  2915: 2687 */         this.bitField0_ &= 0xFFFFFFFE;
/*  2916: 2688 */         return this;
/*  2917:      */       }
/*  2918:      */       
/*  2919:      */       public MRProtos.TaskAttemptIdProto.Builder getTaskAttemptIdBuilder()
/*  2920:      */       {
/*  2921: 2694 */         this.bitField0_ |= 0x1;
/*  2922: 2695 */         onChanged();
/*  2923: 2696 */         return (MRProtos.TaskAttemptIdProto.Builder)getTaskAttemptIdFieldBuilder().getBuilder();
/*  2924:      */       }
/*  2925:      */       
/*  2926:      */       public MRProtos.TaskAttemptIdProtoOrBuilder getTaskAttemptIdOrBuilder()
/*  2927:      */       {
/*  2928: 2702 */         if (this.taskAttemptIdBuilder_ != null) {
/*  2929: 2703 */           return (MRProtos.TaskAttemptIdProtoOrBuilder)this.taskAttemptIdBuilder_.getMessageOrBuilder();
/*  2930:      */         }
/*  2931: 2705 */         return this.taskAttemptId_;
/*  2932:      */       }
/*  2933:      */       
/*  2934:      */       private SingleFieldBuilder<MRProtos.TaskAttemptIdProto, MRProtos.TaskAttemptIdProto.Builder, MRProtos.TaskAttemptIdProtoOrBuilder> getTaskAttemptIdFieldBuilder()
/*  2935:      */       {
/*  2936: 2714 */         if (this.taskAttemptIdBuilder_ == null)
/*  2937:      */         {
/*  2938: 2715 */           this.taskAttemptIdBuilder_ = new SingleFieldBuilder(this.taskAttemptId_, getParentForChildren(), isClean());
/*  2939:      */           
/*  2940:      */ 
/*  2941:      */ 
/*  2942:      */ 
/*  2943: 2720 */           this.taskAttemptId_ = null;
/*  2944:      */         }
/*  2945: 2722 */         return this.taskAttemptIdBuilder_;
/*  2946:      */       }
/*  2947:      */     }
/*  2948:      */     
/*  2949:      */     static
/*  2950:      */     {
/*  2951: 2729 */       defaultInstance = new GetTaskAttemptReportRequestProto(true);
/*  2952: 2730 */       defaultInstance.initFields();
/*  2953:      */     }
/*  2954:      */   }
/*  2955:      */   
/*  2956:      */   public static abstract interface GetTaskAttemptReportResponseProtoOrBuilder
/*  2957:      */     extends MessageOrBuilder
/*  2958:      */   {
/*  2959:      */     public abstract boolean hasTaskAttemptReport();
/*  2960:      */     
/*  2961:      */     public abstract MRProtos.TaskAttemptReportProto getTaskAttemptReport();
/*  2962:      */     
/*  2963:      */     public abstract MRProtos.TaskAttemptReportProtoOrBuilder getTaskAttemptReportOrBuilder();
/*  2964:      */   }
/*  2965:      */   
/*  2966:      */   public static final class GetTaskAttemptReportResponseProto
/*  2967:      */     extends GeneratedMessage
/*  2968:      */     implements MRServiceProtos.GetTaskAttemptReportResponseProtoOrBuilder
/*  2969:      */   {
/*  2970:      */     private static final GetTaskAttemptReportResponseProto defaultInstance;
/*  2971:      */     private final UnknownFieldSet unknownFields;
/*  2972:      */     
/*  2973:      */     private GetTaskAttemptReportResponseProto(GeneratedMessage.Builder<?> builder)
/*  2974:      */     {
/*  2975: 2761 */       super();
/*  2976: 2762 */       this.unknownFields = builder.getUnknownFields();
/*  2977:      */     }
/*  2978:      */     
/*  2979:      */     private GetTaskAttemptReportResponseProto(boolean noInit)
/*  2980:      */     {
/*  2981: 2764 */       this.unknownFields = UnknownFieldSet.getDefaultInstance();
/*  2982:      */     }
/*  2983:      */     
/*  2984:      */     public static GetTaskAttemptReportResponseProto getDefaultInstance()
/*  2985:      */     {
/*  2986: 2768 */       return defaultInstance;
/*  2987:      */     }
/*  2988:      */     
/*  2989:      */     public GetTaskAttemptReportResponseProto getDefaultInstanceForType()
/*  2990:      */     {
/*  2991: 2772 */       return defaultInstance;
/*  2992:      */     }
/*  2993:      */     
/*  2994:      */     public final UnknownFieldSet getUnknownFields()
/*  2995:      */     {
/*  2996: 2779 */       return this.unknownFields;
/*  2997:      */     }
/*  2998:      */     
/*  2999:      */     private GetTaskAttemptReportResponseProto(CodedInputStream input, ExtensionRegistryLite extensionRegistry)
/*  3000:      */       throws InvalidProtocolBufferException
/*  3001:      */     {
/*  3002: 2785 */       initFields();
/*  3003: 2786 */       int mutable_bitField0_ = 0;
/*  3004: 2787 */       UnknownFieldSet.Builder unknownFields = UnknownFieldSet.newBuilder();
/*  3005:      */       try
/*  3006:      */       {
/*  3007: 2790 */         boolean done = false;
/*  3008: 2791 */         while (!done)
/*  3009:      */         {
/*  3010: 2792 */           int tag = input.readTag();
/*  3011: 2793 */           switch (tag)
/*  3012:      */           {
/*  3013:      */           case 0: 
/*  3014: 2795 */             done = true;
/*  3015: 2796 */             break;
/*  3016:      */           default: 
/*  3017: 2798 */             if (!parseUnknownField(input, unknownFields, extensionRegistry, tag)) {
/*  3018: 2800 */               done = true;
/*  3019:      */             }
/*  3020:      */             break;
/*  3021:      */           case 10: 
/*  3022: 2805 */             MRProtos.TaskAttemptReportProto.Builder subBuilder = null;
/*  3023: 2806 */             if ((this.bitField0_ & 0x1) == 1) {
/*  3024: 2807 */               subBuilder = this.taskAttemptReport_.toBuilder();
/*  3025:      */             }
/*  3026: 2809 */             this.taskAttemptReport_ = ((MRProtos.TaskAttemptReportProto)input.readMessage(MRProtos.TaskAttemptReportProto.PARSER, extensionRegistry));
/*  3027: 2810 */             if (subBuilder != null)
/*  3028:      */             {
/*  3029: 2811 */               subBuilder.mergeFrom(this.taskAttemptReport_);
/*  3030: 2812 */               this.taskAttemptReport_ = subBuilder.buildPartial();
/*  3031:      */             }
/*  3032: 2814 */             this.bitField0_ |= 0x1;
/*  3033:      */           }
/*  3034:      */         }
/*  3035:      */       }
/*  3036:      */       catch (InvalidProtocolBufferException e)
/*  3037:      */       {
/*  3038: 2820 */         throw e.setUnfinishedMessage(this);
/*  3039:      */       }
/*  3040:      */       catch (IOException e)
/*  3041:      */       {
/*  3042: 2822 */         throw new InvalidProtocolBufferException(e.getMessage()).setUnfinishedMessage(this);
/*  3043:      */       }
/*  3044:      */       finally
/*  3045:      */       {
/*  3046: 2825 */         this.unknownFields = unknownFields.build();
/*  3047: 2826 */         makeExtensionsImmutable();
/*  3048:      */       }
/*  3049:      */     }
/*  3050:      */     
/*  3051:      */     public static final Descriptors.Descriptor getDescriptor()
/*  3052:      */     {
/*  3053: 2831 */       return MRServiceProtos.internal_static_hadoop_mapreduce_GetTaskAttemptReportResponseProto_descriptor;
/*  3054:      */     }
/*  3055:      */     
/*  3056:      */     protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable()
/*  3057:      */     {
/*  3058: 2836 */       return MRServiceProtos.internal_static_hadoop_mapreduce_GetTaskAttemptReportResponseProto_fieldAccessorTable.ensureFieldAccessorsInitialized(GetTaskAttemptReportResponseProto.class, Builder.class);
/*  3059:      */     }
/*  3060:      */     
/*  3061: 2841 */     public static Parser<GetTaskAttemptReportResponseProto> PARSER = new AbstractParser()
/*  3062:      */     {
/*  3063:      */       public MRServiceProtos.GetTaskAttemptReportResponseProto parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry)
/*  3064:      */         throws InvalidProtocolBufferException
/*  3065:      */       {
/*  3066: 2847 */         return new MRServiceProtos.GetTaskAttemptReportResponseProto(input, extensionRegistry, null);
/*  3067:      */       }
/*  3068:      */     };
/*  3069:      */     private int bitField0_;
/*  3070:      */     public static final int TASK_ATTEMPT_REPORT_FIELD_NUMBER = 1;
/*  3071:      */     private MRProtos.TaskAttemptReportProto taskAttemptReport_;
/*  3072:      */     
/*  3073:      */     public Parser<GetTaskAttemptReportResponseProto> getParserForType()
/*  3074:      */     {
/*  3075: 2853 */       return PARSER;
/*  3076:      */     }
/*  3077:      */     
/*  3078:      */     public boolean hasTaskAttemptReport()
/*  3079:      */     {
/*  3080: 2864 */       return (this.bitField0_ & 0x1) == 1;
/*  3081:      */     }
/*  3082:      */     
/*  3083:      */     public MRProtos.TaskAttemptReportProto getTaskAttemptReport()
/*  3084:      */     {
/*  3085: 2870 */       return this.taskAttemptReport_;
/*  3086:      */     }
/*  3087:      */     
/*  3088:      */     public MRProtos.TaskAttemptReportProtoOrBuilder getTaskAttemptReportOrBuilder()
/*  3089:      */     {
/*  3090: 2876 */       return this.taskAttemptReport_;
/*  3091:      */     }
/*  3092:      */     
/*  3093:      */     private void initFields()
/*  3094:      */     {
/*  3095: 2880 */       this.taskAttemptReport_ = MRProtos.TaskAttemptReportProto.getDefaultInstance();
/*  3096:      */     }
/*  3097:      */     
/*  3098: 2882 */     private byte memoizedIsInitialized = -1;
/*  3099:      */     
/*  3100:      */     public final boolean isInitialized()
/*  3101:      */     {
/*  3102: 2884 */       byte isInitialized = this.memoizedIsInitialized;
/*  3103: 2885 */       if (isInitialized != -1) {
/*  3104: 2885 */         return isInitialized == 1;
/*  3105:      */       }
/*  3106: 2887 */       this.memoizedIsInitialized = 1;
/*  3107: 2888 */       return true;
/*  3108:      */     }
/*  3109:      */     
/*  3110:      */     public void writeTo(CodedOutputStream output)
/*  3111:      */       throws IOException
/*  3112:      */     {
/*  3113: 2893 */       getSerializedSize();
/*  3114: 2894 */       if ((this.bitField0_ & 0x1) == 1) {
/*  3115: 2895 */         output.writeMessage(1, this.taskAttemptReport_);
/*  3116:      */       }
/*  3117: 2897 */       getUnknownFields().writeTo(output);
/*  3118:      */     }
/*  3119:      */     
/*  3120: 2900 */     private int memoizedSerializedSize = -1;
/*  3121:      */     private static final long serialVersionUID = 0L;
/*  3122:      */     
/*  3123:      */     public int getSerializedSize()
/*  3124:      */     {
/*  3125: 2902 */       int size = this.memoizedSerializedSize;
/*  3126: 2903 */       if (size != -1) {
/*  3127: 2903 */         return size;
/*  3128:      */       }
/*  3129: 2905 */       size = 0;
/*  3130: 2906 */       if ((this.bitField0_ & 0x1) == 1) {
/*  3131: 2907 */         size += CodedOutputStream.computeMessageSize(1, this.taskAttemptReport_);
/*  3132:      */       }
/*  3133: 2910 */       size += getUnknownFields().getSerializedSize();
/*  3134: 2911 */       this.memoizedSerializedSize = size;
/*  3135: 2912 */       return size;
/*  3136:      */     }
/*  3137:      */     
/*  3138:      */     protected Object writeReplace()
/*  3139:      */       throws ObjectStreamException
/*  3140:      */     {
/*  3141: 2919 */       return super.writeReplace();
/*  3142:      */     }
/*  3143:      */     
/*  3144:      */     public boolean equals(Object obj)
/*  3145:      */     {
/*  3146: 2924 */       if (obj == this) {
/*  3147: 2925 */         return true;
/*  3148:      */       }
/*  3149: 2927 */       if (!(obj instanceof GetTaskAttemptReportResponseProto)) {
/*  3150: 2928 */         return super.equals(obj);
/*  3151:      */       }
/*  3152: 2930 */       GetTaskAttemptReportResponseProto other = (GetTaskAttemptReportResponseProto)obj;
/*  3153:      */       
/*  3154: 2932 */       boolean result = true;
/*  3155: 2933 */       result = (result) && (hasTaskAttemptReport() == other.hasTaskAttemptReport());
/*  3156: 2934 */       if (hasTaskAttemptReport()) {
/*  3157: 2935 */         result = (result) && (getTaskAttemptReport().equals(other.getTaskAttemptReport()));
/*  3158:      */       }
/*  3159: 2938 */       result = (result) && (getUnknownFields().equals(other.getUnknownFields()));
/*  3160:      */       
/*  3161: 2940 */       return result;
/*  3162:      */     }
/*  3163:      */     
/*  3164: 2943 */     private int memoizedHashCode = 0;
/*  3165:      */     
/*  3166:      */     public int hashCode()
/*  3167:      */     {
/*  3168: 2946 */       if (this.memoizedHashCode != 0) {
/*  3169: 2947 */         return this.memoizedHashCode;
/*  3170:      */       }
/*  3171: 2949 */       int hash = 41;
/*  3172: 2950 */       hash = 19 * hash + getDescriptorForType().hashCode();
/*  3173: 2951 */       if (hasTaskAttemptReport())
/*  3174:      */       {
/*  3175: 2952 */         hash = 37 * hash + 1;
/*  3176: 2953 */         hash = 53 * hash + getTaskAttemptReport().hashCode();
/*  3177:      */       }
/*  3178: 2955 */       hash = 29 * hash + getUnknownFields().hashCode();
/*  3179: 2956 */       this.memoizedHashCode = hash;
/*  3180: 2957 */       return hash;
/*  3181:      */     }
/*  3182:      */     
/*  3183:      */     public static GetTaskAttemptReportResponseProto parseFrom(ByteString data)
/*  3184:      */       throws InvalidProtocolBufferException
/*  3185:      */     {
/*  3186: 2963 */       return (GetTaskAttemptReportResponseProto)PARSER.parseFrom(data);
/*  3187:      */     }
/*  3188:      */     
/*  3189:      */     public static GetTaskAttemptReportResponseProto parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry)
/*  3190:      */       throws InvalidProtocolBufferException
/*  3191:      */     {
/*  3192: 2969 */       return (GetTaskAttemptReportResponseProto)PARSER.parseFrom(data, extensionRegistry);
/*  3193:      */     }
/*  3194:      */     
/*  3195:      */     public static GetTaskAttemptReportResponseProto parseFrom(byte[] data)
/*  3196:      */       throws InvalidProtocolBufferException
/*  3197:      */     {
/*  3198: 2973 */       return (GetTaskAttemptReportResponseProto)PARSER.parseFrom(data);
/*  3199:      */     }
/*  3200:      */     
/*  3201:      */     public static GetTaskAttemptReportResponseProto parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry)
/*  3202:      */       throws InvalidProtocolBufferException
/*  3203:      */     {
/*  3204: 2979 */       return (GetTaskAttemptReportResponseProto)PARSER.parseFrom(data, extensionRegistry);
/*  3205:      */     }
/*  3206:      */     
/*  3207:      */     public static GetTaskAttemptReportResponseProto parseFrom(InputStream input)
/*  3208:      */       throws IOException
/*  3209:      */     {
/*  3210: 2983 */       return (GetTaskAttemptReportResponseProto)PARSER.parseFrom(input);
/*  3211:      */     }
/*  3212:      */     
/*  3213:      */     public static GetTaskAttemptReportResponseProto parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry)
/*  3214:      */       throws IOException
/*  3215:      */     {
/*  3216: 2989 */       return (GetTaskAttemptReportResponseProto)PARSER.parseFrom(input, extensionRegistry);
/*  3217:      */     }
/*  3218:      */     
/*  3219:      */     public static GetTaskAttemptReportResponseProto parseDelimitedFrom(InputStream input)
/*  3220:      */       throws IOException
/*  3221:      */     {
/*  3222: 2993 */       return (GetTaskAttemptReportResponseProto)PARSER.parseDelimitedFrom(input);
/*  3223:      */     }
/*  3224:      */     
/*  3225:      */     public static GetTaskAttemptReportResponseProto parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry)
/*  3226:      */       throws IOException
/*  3227:      */     {
/*  3228: 2999 */       return (GetTaskAttemptReportResponseProto)PARSER.parseDelimitedFrom(input, extensionRegistry);
/*  3229:      */     }
/*  3230:      */     
/*  3231:      */     public static GetTaskAttemptReportResponseProto parseFrom(CodedInputStream input)
/*  3232:      */       throws IOException
/*  3233:      */     {
/*  3234: 3004 */       return (GetTaskAttemptReportResponseProto)PARSER.parseFrom(input);
/*  3235:      */     }
/*  3236:      */     
/*  3237:      */     public static GetTaskAttemptReportResponseProto parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry)
/*  3238:      */       throws IOException
/*  3239:      */     {
/*  3240: 3010 */       return (GetTaskAttemptReportResponseProto)PARSER.parseFrom(input, extensionRegistry);
/*  3241:      */     }
/*  3242:      */     
/*  3243:      */     public static Builder newBuilder()
/*  3244:      */     {
/*  3245: 3013 */       return Builder.access$4800();
/*  3246:      */     }
/*  3247:      */     
/*  3248:      */     public Builder newBuilderForType()
/*  3249:      */     {
/*  3250: 3014 */       return newBuilder();
/*  3251:      */     }
/*  3252:      */     
/*  3253:      */     public static Builder newBuilder(GetTaskAttemptReportResponseProto prototype)
/*  3254:      */     {
/*  3255: 3016 */       return newBuilder().mergeFrom(prototype);
/*  3256:      */     }
/*  3257:      */     
/*  3258:      */     public Builder toBuilder()
/*  3259:      */     {
/*  3260: 3018 */       return newBuilder(this);
/*  3261:      */     }
/*  3262:      */     
/*  3263:      */     protected Builder newBuilderForType(GeneratedMessage.BuilderParent parent)
/*  3264:      */     {
/*  3265: 3023 */       Builder builder = new Builder(parent, null);
/*  3266: 3024 */       return builder;
/*  3267:      */     }
/*  3268:      */     
/*  3269:      */     public static final class Builder
/*  3270:      */       extends GeneratedMessage.Builder<Builder>
/*  3271:      */       implements MRServiceProtos.GetTaskAttemptReportResponseProtoOrBuilder
/*  3272:      */     {
/*  3273:      */       private int bitField0_;
/*  3274:      */       
/*  3275:      */       public static final Descriptors.Descriptor getDescriptor()
/*  3276:      */       {
/*  3277: 3034 */         return MRServiceProtos.internal_static_hadoop_mapreduce_GetTaskAttemptReportResponseProto_descriptor;
/*  3278:      */       }
/*  3279:      */       
/*  3280:      */       protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable()
/*  3281:      */       {
/*  3282: 3039 */         return MRServiceProtos.internal_static_hadoop_mapreduce_GetTaskAttemptReportResponseProto_fieldAccessorTable.ensureFieldAccessorsInitialized(MRServiceProtos.GetTaskAttemptReportResponseProto.class, Builder.class);
/*  3283:      */       }
/*  3284:      */       
/*  3285:      */       private Builder()
/*  3286:      */       {
/*  3287: 3046 */         maybeForceBuilderInitialization();
/*  3288:      */       }
/*  3289:      */       
/*  3290:      */       private Builder(GeneratedMessage.BuilderParent parent)
/*  3291:      */       {
/*  3292: 3051 */         super();
/*  3293: 3052 */         maybeForceBuilderInitialization();
/*  3294:      */       }
/*  3295:      */       
/*  3296:      */       private void maybeForceBuilderInitialization()
/*  3297:      */       {
/*  3298: 3055 */         if (MRServiceProtos.GetTaskAttemptReportResponseProto.alwaysUseFieldBuilders) {
/*  3299: 3056 */           getTaskAttemptReportFieldBuilder();
/*  3300:      */         }
/*  3301:      */       }
/*  3302:      */       
/*  3303:      */       private static Builder create()
/*  3304:      */       {
/*  3305: 3060 */         return new Builder();
/*  3306:      */       }
/*  3307:      */       
/*  3308:      */       public Builder clear()
/*  3309:      */       {
/*  3310: 3064 */         super.clear();
/*  3311: 3065 */         if (this.taskAttemptReportBuilder_ == null) {
/*  3312: 3066 */           this.taskAttemptReport_ = MRProtos.TaskAttemptReportProto.getDefaultInstance();
/*  3313:      */         } else {
/*  3314: 3068 */           this.taskAttemptReportBuilder_.clear();
/*  3315:      */         }
/*  3316: 3070 */         this.bitField0_ &= 0xFFFFFFFE;
/*  3317: 3071 */         return this;
/*  3318:      */       }
/*  3319:      */       
/*  3320:      */       public Builder clone()
/*  3321:      */       {
/*  3322: 3075 */         return create().mergeFrom(buildPartial());
/*  3323:      */       }
/*  3324:      */       
/*  3325:      */       public Descriptors.Descriptor getDescriptorForType()
/*  3326:      */       {
/*  3327: 3080 */         return MRServiceProtos.internal_static_hadoop_mapreduce_GetTaskAttemptReportResponseProto_descriptor;
/*  3328:      */       }
/*  3329:      */       
/*  3330:      */       public MRServiceProtos.GetTaskAttemptReportResponseProto getDefaultInstanceForType()
/*  3331:      */       {
/*  3332: 3084 */         return MRServiceProtos.GetTaskAttemptReportResponseProto.getDefaultInstance();
/*  3333:      */       }
/*  3334:      */       
/*  3335:      */       public MRServiceProtos.GetTaskAttemptReportResponseProto build()
/*  3336:      */       {
/*  3337: 3088 */         MRServiceProtos.GetTaskAttemptReportResponseProto result = buildPartial();
/*  3338: 3089 */         if (!result.isInitialized()) {
/*  3339: 3090 */           throw newUninitializedMessageException(result);
/*  3340:      */         }
/*  3341: 3092 */         return result;
/*  3342:      */       }
/*  3343:      */       
/*  3344:      */       public MRServiceProtos.GetTaskAttemptReportResponseProto buildPartial()
/*  3345:      */       {
/*  3346: 3096 */         MRServiceProtos.GetTaskAttemptReportResponseProto result = new MRServiceProtos.GetTaskAttemptReportResponseProto(this, null);
/*  3347: 3097 */         int from_bitField0_ = this.bitField0_;
/*  3348: 3098 */         int to_bitField0_ = 0;
/*  3349: 3099 */         if ((from_bitField0_ & 0x1) == 1) {
/*  3350: 3100 */           to_bitField0_ |= 0x1;
/*  3351:      */         }
/*  3352: 3102 */         if (this.taskAttemptReportBuilder_ == null) {
/*  3353: 3103 */           result.taskAttemptReport_ = this.taskAttemptReport_;
/*  3354:      */         } else {
/*  3355: 3105 */           result.taskAttemptReport_ = ((MRProtos.TaskAttemptReportProto)this.taskAttemptReportBuilder_.build());
/*  3356:      */         }
/*  3357: 3107 */         result.bitField0_ = to_bitField0_;
/*  3358: 3108 */         onBuilt();
/*  3359: 3109 */         return result;
/*  3360:      */       }
/*  3361:      */       
/*  3362:      */       public Builder mergeFrom(Message other)
/*  3363:      */       {
/*  3364: 3113 */         if ((other instanceof MRServiceProtos.GetTaskAttemptReportResponseProto)) {
/*  3365: 3114 */           return mergeFrom((MRServiceProtos.GetTaskAttemptReportResponseProto)other);
/*  3366:      */         }
/*  3367: 3116 */         super.mergeFrom(other);
/*  3368: 3117 */         return this;
/*  3369:      */       }
/*  3370:      */       
/*  3371:      */       public Builder mergeFrom(MRServiceProtos.GetTaskAttemptReportResponseProto other)
/*  3372:      */       {
/*  3373: 3122 */         if (other == MRServiceProtos.GetTaskAttemptReportResponseProto.getDefaultInstance()) {
/*  3374: 3122 */           return this;
/*  3375:      */         }
/*  3376: 3123 */         if (other.hasTaskAttemptReport()) {
/*  3377: 3124 */           mergeTaskAttemptReport(other.getTaskAttemptReport());
/*  3378:      */         }
/*  3379: 3126 */         mergeUnknownFields(other.getUnknownFields());
/*  3380: 3127 */         return this;
/*  3381:      */       }
/*  3382:      */       
/*  3383:      */       public final boolean isInitialized()
/*  3384:      */       {
/*  3385: 3131 */         return true;
/*  3386:      */       }
/*  3387:      */       
/*  3388:      */       public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry)
/*  3389:      */         throws IOException
/*  3390:      */       {
/*  3391: 3138 */         MRServiceProtos.GetTaskAttemptReportResponseProto parsedMessage = null;
/*  3392:      */         try
/*  3393:      */         {
/*  3394: 3140 */           parsedMessage = (MRServiceProtos.GetTaskAttemptReportResponseProto)MRServiceProtos.GetTaskAttemptReportResponseProto.PARSER.parsePartialFrom(input, extensionRegistry);
/*  3395:      */         }
/*  3396:      */         catch (InvalidProtocolBufferException e)
/*  3397:      */         {
/*  3398: 3142 */           parsedMessage = (MRServiceProtos.GetTaskAttemptReportResponseProto)e.getUnfinishedMessage();
/*  3399: 3143 */           throw e;
/*  3400:      */         }
/*  3401:      */         finally
/*  3402:      */         {
/*  3403: 3145 */           if (parsedMessage != null) {
/*  3404: 3146 */             mergeFrom(parsedMessage);
/*  3405:      */           }
/*  3406:      */         }
/*  3407: 3149 */         return this;
/*  3408:      */       }
/*  3409:      */       
/*  3410: 3154 */       private MRProtos.TaskAttemptReportProto taskAttemptReport_ = MRProtos.TaskAttemptReportProto.getDefaultInstance();
/*  3411:      */       private SingleFieldBuilder<MRProtos.TaskAttemptReportProto, MRProtos.TaskAttemptReportProto.Builder, MRProtos.TaskAttemptReportProtoOrBuilder> taskAttemptReportBuilder_;
/*  3412:      */       
/*  3413:      */       public boolean hasTaskAttemptReport()
/*  3414:      */       {
/*  3415: 3161 */         return (this.bitField0_ & 0x1) == 1;
/*  3416:      */       }
/*  3417:      */       
/*  3418:      */       public MRProtos.TaskAttemptReportProto getTaskAttemptReport()
/*  3419:      */       {
/*  3420: 3167 */         if (this.taskAttemptReportBuilder_ == null) {
/*  3421: 3168 */           return this.taskAttemptReport_;
/*  3422:      */         }
/*  3423: 3170 */         return (MRProtos.TaskAttemptReportProto)this.taskAttemptReportBuilder_.getMessage();
/*  3424:      */       }
/*  3425:      */       
/*  3426:      */       public Builder setTaskAttemptReport(MRProtos.TaskAttemptReportProto value)
/*  3427:      */       {
/*  3428: 3177 */         if (this.taskAttemptReportBuilder_ == null)
/*  3429:      */         {
/*  3430: 3178 */           if (value == null) {
/*  3431: 3179 */             throw new NullPointerException();
/*  3432:      */           }
/*  3433: 3181 */           this.taskAttemptReport_ = value;
/*  3434: 3182 */           onChanged();
/*  3435:      */         }
/*  3436:      */         else
/*  3437:      */         {
/*  3438: 3184 */           this.taskAttemptReportBuilder_.setMessage(value);
/*  3439:      */         }
/*  3440: 3186 */         this.bitField0_ |= 0x1;
/*  3441: 3187 */         return this;
/*  3442:      */       }
/*  3443:      */       
/*  3444:      */       public Builder setTaskAttemptReport(MRProtos.TaskAttemptReportProto.Builder builderForValue)
/*  3445:      */       {
/*  3446: 3194 */         if (this.taskAttemptReportBuilder_ == null)
/*  3447:      */         {
/*  3448: 3195 */           this.taskAttemptReport_ = builderForValue.build();
/*  3449: 3196 */           onChanged();
/*  3450:      */         }
/*  3451:      */         else
/*  3452:      */         {
/*  3453: 3198 */           this.taskAttemptReportBuilder_.setMessage(builderForValue.build());
/*  3454:      */         }
/*  3455: 3200 */         this.bitField0_ |= 0x1;
/*  3456: 3201 */         return this;
/*  3457:      */       }
/*  3458:      */       
/*  3459:      */       public Builder mergeTaskAttemptReport(MRProtos.TaskAttemptReportProto value)
/*  3460:      */       {
/*  3461: 3207 */         if (this.taskAttemptReportBuilder_ == null)
/*  3462:      */         {
/*  3463: 3208 */           if (((this.bitField0_ & 0x1) == 1) && (this.taskAttemptReport_ != MRProtos.TaskAttemptReportProto.getDefaultInstance())) {
/*  3464: 3210 */             this.taskAttemptReport_ = MRProtos.TaskAttemptReportProto.newBuilder(this.taskAttemptReport_).mergeFrom(value).buildPartial();
/*  3465:      */           } else {
/*  3466: 3213 */             this.taskAttemptReport_ = value;
/*  3467:      */           }
/*  3468: 3215 */           onChanged();
/*  3469:      */         }
/*  3470:      */         else
/*  3471:      */         {
/*  3472: 3217 */           this.taskAttemptReportBuilder_.mergeFrom(value);
/*  3473:      */         }
/*  3474: 3219 */         this.bitField0_ |= 0x1;
/*  3475: 3220 */         return this;
/*  3476:      */       }
/*  3477:      */       
/*  3478:      */       public Builder clearTaskAttemptReport()
/*  3479:      */       {
/*  3480: 3226 */         if (this.taskAttemptReportBuilder_ == null)
/*  3481:      */         {
/*  3482: 3227 */           this.taskAttemptReport_ = MRProtos.TaskAttemptReportProto.getDefaultInstance();
/*  3483: 3228 */           onChanged();
/*  3484:      */         }
/*  3485:      */         else
/*  3486:      */         {
/*  3487: 3230 */           this.taskAttemptReportBuilder_.clear();
/*  3488:      */         }
/*  3489: 3232 */         this.bitField0_ &= 0xFFFFFFFE;
/*  3490: 3233 */         return this;
/*  3491:      */       }
/*  3492:      */       
/*  3493:      */       public MRProtos.TaskAttemptReportProto.Builder getTaskAttemptReportBuilder()
/*  3494:      */       {
/*  3495: 3239 */         this.bitField0_ |= 0x1;
/*  3496: 3240 */         onChanged();
/*  3497: 3241 */         return (MRProtos.TaskAttemptReportProto.Builder)getTaskAttemptReportFieldBuilder().getBuilder();
/*  3498:      */       }
/*  3499:      */       
/*  3500:      */       public MRProtos.TaskAttemptReportProtoOrBuilder getTaskAttemptReportOrBuilder()
/*  3501:      */       {
/*  3502: 3247 */         if (this.taskAttemptReportBuilder_ != null) {
/*  3503: 3248 */           return (MRProtos.TaskAttemptReportProtoOrBuilder)this.taskAttemptReportBuilder_.getMessageOrBuilder();
/*  3504:      */         }
/*  3505: 3250 */         return this.taskAttemptReport_;
/*  3506:      */       }
/*  3507:      */       
/*  3508:      */       private SingleFieldBuilder<MRProtos.TaskAttemptReportProto, MRProtos.TaskAttemptReportProto.Builder, MRProtos.TaskAttemptReportProtoOrBuilder> getTaskAttemptReportFieldBuilder()
/*  3509:      */       {
/*  3510: 3259 */         if (this.taskAttemptReportBuilder_ == null)
/*  3511:      */         {
/*  3512: 3260 */           this.taskAttemptReportBuilder_ = new SingleFieldBuilder(this.taskAttemptReport_, getParentForChildren(), isClean());
/*  3513:      */           
/*  3514:      */ 
/*  3515:      */ 
/*  3516:      */ 
/*  3517: 3265 */           this.taskAttemptReport_ = null;
/*  3518:      */         }
/*  3519: 3267 */         return this.taskAttemptReportBuilder_;
/*  3520:      */       }
/*  3521:      */     }
/*  3522:      */     
/*  3523:      */     static
/*  3524:      */     {
/*  3525: 3274 */       defaultInstance = new GetTaskAttemptReportResponseProto(true);
/*  3526: 3275 */       defaultInstance.initFields();
/*  3527:      */     }
/*  3528:      */   }
/*  3529:      */   
/*  3530:      */   public static abstract interface GetCountersRequestProtoOrBuilder
/*  3531:      */     extends MessageOrBuilder
/*  3532:      */   {
/*  3533:      */     public abstract boolean hasJobId();
/*  3534:      */     
/*  3535:      */     public abstract MRProtos.JobIdProto getJobId();
/*  3536:      */     
/*  3537:      */     public abstract MRProtos.JobIdProtoOrBuilder getJobIdOrBuilder();
/*  3538:      */   }
/*  3539:      */   
/*  3540:      */   public static final class GetCountersRequestProto
/*  3541:      */     extends GeneratedMessage
/*  3542:      */     implements MRServiceProtos.GetCountersRequestProtoOrBuilder
/*  3543:      */   {
/*  3544:      */     private static final GetCountersRequestProto defaultInstance;
/*  3545:      */     private final UnknownFieldSet unknownFields;
/*  3546:      */     
/*  3547:      */     private GetCountersRequestProto(GeneratedMessage.Builder<?> builder)
/*  3548:      */     {
/*  3549: 3306 */       super();
/*  3550: 3307 */       this.unknownFields = builder.getUnknownFields();
/*  3551:      */     }
/*  3552:      */     
/*  3553:      */     private GetCountersRequestProto(boolean noInit)
/*  3554:      */     {
/*  3555: 3309 */       this.unknownFields = UnknownFieldSet.getDefaultInstance();
/*  3556:      */     }
/*  3557:      */     
/*  3558:      */     public static GetCountersRequestProto getDefaultInstance()
/*  3559:      */     {
/*  3560: 3313 */       return defaultInstance;
/*  3561:      */     }
/*  3562:      */     
/*  3563:      */     public GetCountersRequestProto getDefaultInstanceForType()
/*  3564:      */     {
/*  3565: 3317 */       return defaultInstance;
/*  3566:      */     }
/*  3567:      */     
/*  3568:      */     public final UnknownFieldSet getUnknownFields()
/*  3569:      */     {
/*  3570: 3324 */       return this.unknownFields;
/*  3571:      */     }
/*  3572:      */     
/*  3573:      */     private GetCountersRequestProto(CodedInputStream input, ExtensionRegistryLite extensionRegistry)
/*  3574:      */       throws InvalidProtocolBufferException
/*  3575:      */     {
/*  3576: 3330 */       initFields();
/*  3577: 3331 */       int mutable_bitField0_ = 0;
/*  3578: 3332 */       UnknownFieldSet.Builder unknownFields = UnknownFieldSet.newBuilder();
/*  3579:      */       try
/*  3580:      */       {
/*  3581: 3335 */         boolean done = false;
/*  3582: 3336 */         while (!done)
/*  3583:      */         {
/*  3584: 3337 */           int tag = input.readTag();
/*  3585: 3338 */           switch (tag)
/*  3586:      */           {
/*  3587:      */           case 0: 
/*  3588: 3340 */             done = true;
/*  3589: 3341 */             break;
/*  3590:      */           default: 
/*  3591: 3343 */             if (!parseUnknownField(input, unknownFields, extensionRegistry, tag)) {
/*  3592: 3345 */               done = true;
/*  3593:      */             }
/*  3594:      */             break;
/*  3595:      */           case 10: 
/*  3596: 3350 */             MRProtos.JobIdProto.Builder subBuilder = null;
/*  3597: 3351 */             if ((this.bitField0_ & 0x1) == 1) {
/*  3598: 3352 */               subBuilder = this.jobId_.toBuilder();
/*  3599:      */             }
/*  3600: 3354 */             this.jobId_ = ((MRProtos.JobIdProto)input.readMessage(MRProtos.JobIdProto.PARSER, extensionRegistry));
/*  3601: 3355 */             if (subBuilder != null)
/*  3602:      */             {
/*  3603: 3356 */               subBuilder.mergeFrom(this.jobId_);
/*  3604: 3357 */               this.jobId_ = subBuilder.buildPartial();
/*  3605:      */             }
/*  3606: 3359 */             this.bitField0_ |= 0x1;
/*  3607:      */           }
/*  3608:      */         }
/*  3609:      */       }
/*  3610:      */       catch (InvalidProtocolBufferException e)
/*  3611:      */       {
/*  3612: 3365 */         throw e.setUnfinishedMessage(this);
/*  3613:      */       }
/*  3614:      */       catch (IOException e)
/*  3615:      */       {
/*  3616: 3367 */         throw new InvalidProtocolBufferException(e.getMessage()).setUnfinishedMessage(this);
/*  3617:      */       }
/*  3618:      */       finally
/*  3619:      */       {
/*  3620: 3370 */         this.unknownFields = unknownFields.build();
/*  3621: 3371 */         makeExtensionsImmutable();
/*  3622:      */       }
/*  3623:      */     }
/*  3624:      */     
/*  3625:      */     public static final Descriptors.Descriptor getDescriptor()
/*  3626:      */     {
/*  3627: 3376 */       return MRServiceProtos.internal_static_hadoop_mapreduce_GetCountersRequestProto_descriptor;
/*  3628:      */     }
/*  3629:      */     
/*  3630:      */     protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable()
/*  3631:      */     {
/*  3632: 3381 */       return MRServiceProtos.internal_static_hadoop_mapreduce_GetCountersRequestProto_fieldAccessorTable.ensureFieldAccessorsInitialized(GetCountersRequestProto.class, Builder.class);
/*  3633:      */     }
/*  3634:      */     
/*  3635: 3386 */     public static Parser<GetCountersRequestProto> PARSER = new AbstractParser()
/*  3636:      */     {
/*  3637:      */       public MRServiceProtos.GetCountersRequestProto parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry)
/*  3638:      */         throws InvalidProtocolBufferException
/*  3639:      */       {
/*  3640: 3392 */         return new MRServiceProtos.GetCountersRequestProto(input, extensionRegistry, null);
/*  3641:      */       }
/*  3642:      */     };
/*  3643:      */     private int bitField0_;
/*  3644:      */     public static final int JOB_ID_FIELD_NUMBER = 1;
/*  3645:      */     private MRProtos.JobIdProto jobId_;
/*  3646:      */     
/*  3647:      */     public Parser<GetCountersRequestProto> getParserForType()
/*  3648:      */     {
/*  3649: 3398 */       return PARSER;
/*  3650:      */     }
/*  3651:      */     
/*  3652:      */     public boolean hasJobId()
/*  3653:      */     {
/*  3654: 3409 */       return (this.bitField0_ & 0x1) == 1;
/*  3655:      */     }
/*  3656:      */     
/*  3657:      */     public MRProtos.JobIdProto getJobId()
/*  3658:      */     {
/*  3659: 3415 */       return this.jobId_;
/*  3660:      */     }
/*  3661:      */     
/*  3662:      */     public MRProtos.JobIdProtoOrBuilder getJobIdOrBuilder()
/*  3663:      */     {
/*  3664: 3421 */       return this.jobId_;
/*  3665:      */     }
/*  3666:      */     
/*  3667:      */     private void initFields()
/*  3668:      */     {
/*  3669: 3425 */       this.jobId_ = MRProtos.JobIdProto.getDefaultInstance();
/*  3670:      */     }
/*  3671:      */     
/*  3672: 3427 */     private byte memoizedIsInitialized = -1;
/*  3673:      */     
/*  3674:      */     public final boolean isInitialized()
/*  3675:      */     {
/*  3676: 3429 */       byte isInitialized = this.memoizedIsInitialized;
/*  3677: 3430 */       if (isInitialized != -1) {
/*  3678: 3430 */         return isInitialized == 1;
/*  3679:      */       }
/*  3680: 3432 */       this.memoizedIsInitialized = 1;
/*  3681: 3433 */       return true;
/*  3682:      */     }
/*  3683:      */     
/*  3684:      */     public void writeTo(CodedOutputStream output)
/*  3685:      */       throws IOException
/*  3686:      */     {
/*  3687: 3438 */       getSerializedSize();
/*  3688: 3439 */       if ((this.bitField0_ & 0x1) == 1) {
/*  3689: 3440 */         output.writeMessage(1, this.jobId_);
/*  3690:      */       }
/*  3691: 3442 */       getUnknownFields().writeTo(output);
/*  3692:      */     }
/*  3693:      */     
/*  3694: 3445 */     private int memoizedSerializedSize = -1;
/*  3695:      */     private static final long serialVersionUID = 0L;
/*  3696:      */     
/*  3697:      */     public int getSerializedSize()
/*  3698:      */     {
/*  3699: 3447 */       int size = this.memoizedSerializedSize;
/*  3700: 3448 */       if (size != -1) {
/*  3701: 3448 */         return size;
/*  3702:      */       }
/*  3703: 3450 */       size = 0;
/*  3704: 3451 */       if ((this.bitField0_ & 0x1) == 1) {
/*  3705: 3452 */         size += CodedOutputStream.computeMessageSize(1, this.jobId_);
/*  3706:      */       }
/*  3707: 3455 */       size += getUnknownFields().getSerializedSize();
/*  3708: 3456 */       this.memoizedSerializedSize = size;
/*  3709: 3457 */       return size;
/*  3710:      */     }
/*  3711:      */     
/*  3712:      */     protected Object writeReplace()
/*  3713:      */       throws ObjectStreamException
/*  3714:      */     {
/*  3715: 3464 */       return super.writeReplace();
/*  3716:      */     }
/*  3717:      */     
/*  3718:      */     public boolean equals(Object obj)
/*  3719:      */     {
/*  3720: 3469 */       if (obj == this) {
/*  3721: 3470 */         return true;
/*  3722:      */       }
/*  3723: 3472 */       if (!(obj instanceof GetCountersRequestProto)) {
/*  3724: 3473 */         return super.equals(obj);
/*  3725:      */       }
/*  3726: 3475 */       GetCountersRequestProto other = (GetCountersRequestProto)obj;
/*  3727:      */       
/*  3728: 3477 */       boolean result = true;
/*  3729: 3478 */       result = (result) && (hasJobId() == other.hasJobId());
/*  3730: 3479 */       if (hasJobId()) {
/*  3731: 3480 */         result = (result) && (getJobId().equals(other.getJobId()));
/*  3732:      */       }
/*  3733: 3483 */       result = (result) && (getUnknownFields().equals(other.getUnknownFields()));
/*  3734:      */       
/*  3735: 3485 */       return result;
/*  3736:      */     }
/*  3737:      */     
/*  3738: 3488 */     private int memoizedHashCode = 0;
/*  3739:      */     
/*  3740:      */     public int hashCode()
/*  3741:      */     {
/*  3742: 3491 */       if (this.memoizedHashCode != 0) {
/*  3743: 3492 */         return this.memoizedHashCode;
/*  3744:      */       }
/*  3745: 3494 */       int hash = 41;
/*  3746: 3495 */       hash = 19 * hash + getDescriptorForType().hashCode();
/*  3747: 3496 */       if (hasJobId())
/*  3748:      */       {
/*  3749: 3497 */         hash = 37 * hash + 1;
/*  3750: 3498 */         hash = 53 * hash + getJobId().hashCode();
/*  3751:      */       }
/*  3752: 3500 */       hash = 29 * hash + getUnknownFields().hashCode();
/*  3753: 3501 */       this.memoizedHashCode = hash;
/*  3754: 3502 */       return hash;
/*  3755:      */     }
/*  3756:      */     
/*  3757:      */     public static GetCountersRequestProto parseFrom(ByteString data)
/*  3758:      */       throws InvalidProtocolBufferException
/*  3759:      */     {
/*  3760: 3508 */       return (GetCountersRequestProto)PARSER.parseFrom(data);
/*  3761:      */     }
/*  3762:      */     
/*  3763:      */     public static GetCountersRequestProto parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry)
/*  3764:      */       throws InvalidProtocolBufferException
/*  3765:      */     {
/*  3766: 3514 */       return (GetCountersRequestProto)PARSER.parseFrom(data, extensionRegistry);
/*  3767:      */     }
/*  3768:      */     
/*  3769:      */     public static GetCountersRequestProto parseFrom(byte[] data)
/*  3770:      */       throws InvalidProtocolBufferException
/*  3771:      */     {
/*  3772: 3518 */       return (GetCountersRequestProto)PARSER.parseFrom(data);
/*  3773:      */     }
/*  3774:      */     
/*  3775:      */     public static GetCountersRequestProto parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry)
/*  3776:      */       throws InvalidProtocolBufferException
/*  3777:      */     {
/*  3778: 3524 */       return (GetCountersRequestProto)PARSER.parseFrom(data, extensionRegistry);
/*  3779:      */     }
/*  3780:      */     
/*  3781:      */     public static GetCountersRequestProto parseFrom(InputStream input)
/*  3782:      */       throws IOException
/*  3783:      */     {
/*  3784: 3528 */       return (GetCountersRequestProto)PARSER.parseFrom(input);
/*  3785:      */     }
/*  3786:      */     
/*  3787:      */     public static GetCountersRequestProto parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry)
/*  3788:      */       throws IOException
/*  3789:      */     {
/*  3790: 3534 */       return (GetCountersRequestProto)PARSER.parseFrom(input, extensionRegistry);
/*  3791:      */     }
/*  3792:      */     
/*  3793:      */     public static GetCountersRequestProto parseDelimitedFrom(InputStream input)
/*  3794:      */       throws IOException
/*  3795:      */     {
/*  3796: 3538 */       return (GetCountersRequestProto)PARSER.parseDelimitedFrom(input);
/*  3797:      */     }
/*  3798:      */     
/*  3799:      */     public static GetCountersRequestProto parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry)
/*  3800:      */       throws IOException
/*  3801:      */     {
/*  3802: 3544 */       return (GetCountersRequestProto)PARSER.parseDelimitedFrom(input, extensionRegistry);
/*  3803:      */     }
/*  3804:      */     
/*  3805:      */     public static GetCountersRequestProto parseFrom(CodedInputStream input)
/*  3806:      */       throws IOException
/*  3807:      */     {
/*  3808: 3549 */       return (GetCountersRequestProto)PARSER.parseFrom(input);
/*  3809:      */     }
/*  3810:      */     
/*  3811:      */     public static GetCountersRequestProto parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry)
/*  3812:      */       throws IOException
/*  3813:      */     {
/*  3814: 3555 */       return (GetCountersRequestProto)PARSER.parseFrom(input, extensionRegistry);
/*  3815:      */     }
/*  3816:      */     
/*  3817:      */     public static Builder newBuilder()
/*  3818:      */     {
/*  3819: 3558 */       return Builder.access$5700();
/*  3820:      */     }
/*  3821:      */     
/*  3822:      */     public Builder newBuilderForType()
/*  3823:      */     {
/*  3824: 3559 */       return newBuilder();
/*  3825:      */     }
/*  3826:      */     
/*  3827:      */     public static Builder newBuilder(GetCountersRequestProto prototype)
/*  3828:      */     {
/*  3829: 3561 */       return newBuilder().mergeFrom(prototype);
/*  3830:      */     }
/*  3831:      */     
/*  3832:      */     public Builder toBuilder()
/*  3833:      */     {
/*  3834: 3563 */       return newBuilder(this);
/*  3835:      */     }
/*  3836:      */     
/*  3837:      */     protected Builder newBuilderForType(GeneratedMessage.BuilderParent parent)
/*  3838:      */     {
/*  3839: 3568 */       Builder builder = new Builder(parent, null);
/*  3840: 3569 */       return builder;
/*  3841:      */     }
/*  3842:      */     
/*  3843:      */     public static final class Builder
/*  3844:      */       extends GeneratedMessage.Builder<Builder>
/*  3845:      */       implements MRServiceProtos.GetCountersRequestProtoOrBuilder
/*  3846:      */     {
/*  3847:      */       private int bitField0_;
/*  3848:      */       
/*  3849:      */       public static final Descriptors.Descriptor getDescriptor()
/*  3850:      */       {
/*  3851: 3579 */         return MRServiceProtos.internal_static_hadoop_mapreduce_GetCountersRequestProto_descriptor;
/*  3852:      */       }
/*  3853:      */       
/*  3854:      */       protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable()
/*  3855:      */       {
/*  3856: 3584 */         return MRServiceProtos.internal_static_hadoop_mapreduce_GetCountersRequestProto_fieldAccessorTable.ensureFieldAccessorsInitialized(MRServiceProtos.GetCountersRequestProto.class, Builder.class);
/*  3857:      */       }
/*  3858:      */       
/*  3859:      */       private Builder()
/*  3860:      */       {
/*  3861: 3591 */         maybeForceBuilderInitialization();
/*  3862:      */       }
/*  3863:      */       
/*  3864:      */       private Builder(GeneratedMessage.BuilderParent parent)
/*  3865:      */       {
/*  3866: 3596 */         super();
/*  3867: 3597 */         maybeForceBuilderInitialization();
/*  3868:      */       }
/*  3869:      */       
/*  3870:      */       private void maybeForceBuilderInitialization()
/*  3871:      */       {
/*  3872: 3600 */         if (MRServiceProtos.GetCountersRequestProto.alwaysUseFieldBuilders) {
/*  3873: 3601 */           getJobIdFieldBuilder();
/*  3874:      */         }
/*  3875:      */       }
/*  3876:      */       
/*  3877:      */       private static Builder create()
/*  3878:      */       {
/*  3879: 3605 */         return new Builder();
/*  3880:      */       }
/*  3881:      */       
/*  3882:      */       public Builder clear()
/*  3883:      */       {
/*  3884: 3609 */         super.clear();
/*  3885: 3610 */         if (this.jobIdBuilder_ == null) {
/*  3886: 3611 */           this.jobId_ = MRProtos.JobIdProto.getDefaultInstance();
/*  3887:      */         } else {
/*  3888: 3613 */           this.jobIdBuilder_.clear();
/*  3889:      */         }
/*  3890: 3615 */         this.bitField0_ &= 0xFFFFFFFE;
/*  3891: 3616 */         return this;
/*  3892:      */       }
/*  3893:      */       
/*  3894:      */       public Builder clone()
/*  3895:      */       {
/*  3896: 3620 */         return create().mergeFrom(buildPartial());
/*  3897:      */       }
/*  3898:      */       
/*  3899:      */       public Descriptors.Descriptor getDescriptorForType()
/*  3900:      */       {
/*  3901: 3625 */         return MRServiceProtos.internal_static_hadoop_mapreduce_GetCountersRequestProto_descriptor;
/*  3902:      */       }
/*  3903:      */       
/*  3904:      */       public MRServiceProtos.GetCountersRequestProto getDefaultInstanceForType()
/*  3905:      */       {
/*  3906: 3629 */         return MRServiceProtos.GetCountersRequestProto.getDefaultInstance();
/*  3907:      */       }
/*  3908:      */       
/*  3909:      */       public MRServiceProtos.GetCountersRequestProto build()
/*  3910:      */       {
/*  3911: 3633 */         MRServiceProtos.GetCountersRequestProto result = buildPartial();
/*  3912: 3634 */         if (!result.isInitialized()) {
/*  3913: 3635 */           throw newUninitializedMessageException(result);
/*  3914:      */         }
/*  3915: 3637 */         return result;
/*  3916:      */       }
/*  3917:      */       
/*  3918:      */       public MRServiceProtos.GetCountersRequestProto buildPartial()
/*  3919:      */       {
/*  3920: 3641 */         MRServiceProtos.GetCountersRequestProto result = new MRServiceProtos.GetCountersRequestProto(this, null);
/*  3921: 3642 */         int from_bitField0_ = this.bitField0_;
/*  3922: 3643 */         int to_bitField0_ = 0;
/*  3923: 3644 */         if ((from_bitField0_ & 0x1) == 1) {
/*  3924: 3645 */           to_bitField0_ |= 0x1;
/*  3925:      */         }
/*  3926: 3647 */         if (this.jobIdBuilder_ == null) {
/*  3927: 3648 */           result.jobId_ = this.jobId_;
/*  3928:      */         } else {
/*  3929: 3650 */           result.jobId_ = ((MRProtos.JobIdProto)this.jobIdBuilder_.build());
/*  3930:      */         }
/*  3931: 3652 */         result.bitField0_ = to_bitField0_;
/*  3932: 3653 */         onBuilt();
/*  3933: 3654 */         return result;
/*  3934:      */       }
/*  3935:      */       
/*  3936:      */       public Builder mergeFrom(Message other)
/*  3937:      */       {
/*  3938: 3658 */         if ((other instanceof MRServiceProtos.GetCountersRequestProto)) {
/*  3939: 3659 */           return mergeFrom((MRServiceProtos.GetCountersRequestProto)other);
/*  3940:      */         }
/*  3941: 3661 */         super.mergeFrom(other);
/*  3942: 3662 */         return this;
/*  3943:      */       }
/*  3944:      */       
/*  3945:      */       public Builder mergeFrom(MRServiceProtos.GetCountersRequestProto other)
/*  3946:      */       {
/*  3947: 3667 */         if (other == MRServiceProtos.GetCountersRequestProto.getDefaultInstance()) {
/*  3948: 3667 */           return this;
/*  3949:      */         }
/*  3950: 3668 */         if (other.hasJobId()) {
/*  3951: 3669 */           mergeJobId(other.getJobId());
/*  3952:      */         }
/*  3953: 3671 */         mergeUnknownFields(other.getUnknownFields());
/*  3954: 3672 */         return this;
/*  3955:      */       }
/*  3956:      */       
/*  3957:      */       public final boolean isInitialized()
/*  3958:      */       {
/*  3959: 3676 */         return true;
/*  3960:      */       }
/*  3961:      */       
/*  3962:      */       public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry)
/*  3963:      */         throws IOException
/*  3964:      */       {
/*  3965: 3683 */         MRServiceProtos.GetCountersRequestProto parsedMessage = null;
/*  3966:      */         try
/*  3967:      */         {
/*  3968: 3685 */           parsedMessage = (MRServiceProtos.GetCountersRequestProto)MRServiceProtos.GetCountersRequestProto.PARSER.parsePartialFrom(input, extensionRegistry);
/*  3969:      */         }
/*  3970:      */         catch (InvalidProtocolBufferException e)
/*  3971:      */         {
/*  3972: 3687 */           parsedMessage = (MRServiceProtos.GetCountersRequestProto)e.getUnfinishedMessage();
/*  3973: 3688 */           throw e;
/*  3974:      */         }
/*  3975:      */         finally
/*  3976:      */         {
/*  3977: 3690 */           if (parsedMessage != null) {
/*  3978: 3691 */             mergeFrom(parsedMessage);
/*  3979:      */           }
/*  3980:      */         }
/*  3981: 3694 */         return this;
/*  3982:      */       }
/*  3983:      */       
/*  3984: 3699 */       private MRProtos.JobIdProto jobId_ = MRProtos.JobIdProto.getDefaultInstance();
/*  3985:      */       private SingleFieldBuilder<MRProtos.JobIdProto, MRProtos.JobIdProto.Builder, MRProtos.JobIdProtoOrBuilder> jobIdBuilder_;
/*  3986:      */       
/*  3987:      */       public boolean hasJobId()
/*  3988:      */       {
/*  3989: 3706 */         return (this.bitField0_ & 0x1) == 1;
/*  3990:      */       }
/*  3991:      */       
/*  3992:      */       public MRProtos.JobIdProto getJobId()
/*  3993:      */       {
/*  3994: 3712 */         if (this.jobIdBuilder_ == null) {
/*  3995: 3713 */           return this.jobId_;
/*  3996:      */         }
/*  3997: 3715 */         return (MRProtos.JobIdProto)this.jobIdBuilder_.getMessage();
/*  3998:      */       }
/*  3999:      */       
/*  4000:      */       public Builder setJobId(MRProtos.JobIdProto value)
/*  4001:      */       {
/*  4002: 3722 */         if (this.jobIdBuilder_ == null)
/*  4003:      */         {
/*  4004: 3723 */           if (value == null) {
/*  4005: 3724 */             throw new NullPointerException();
/*  4006:      */           }
/*  4007: 3726 */           this.jobId_ = value;
/*  4008: 3727 */           onChanged();
/*  4009:      */         }
/*  4010:      */         else
/*  4011:      */         {
/*  4012: 3729 */           this.jobIdBuilder_.setMessage(value);
/*  4013:      */         }
/*  4014: 3731 */         this.bitField0_ |= 0x1;
/*  4015: 3732 */         return this;
/*  4016:      */       }
/*  4017:      */       
/*  4018:      */       public Builder setJobId(MRProtos.JobIdProto.Builder builderForValue)
/*  4019:      */       {
/*  4020: 3739 */         if (this.jobIdBuilder_ == null)
/*  4021:      */         {
/*  4022: 3740 */           this.jobId_ = builderForValue.build();
/*  4023: 3741 */           onChanged();
/*  4024:      */         }
/*  4025:      */         else
/*  4026:      */         {
/*  4027: 3743 */           this.jobIdBuilder_.setMessage(builderForValue.build());
/*  4028:      */         }
/*  4029: 3745 */         this.bitField0_ |= 0x1;
/*  4030: 3746 */         return this;
/*  4031:      */       }
/*  4032:      */       
/*  4033:      */       public Builder mergeJobId(MRProtos.JobIdProto value)
/*  4034:      */       {
/*  4035: 3752 */         if (this.jobIdBuilder_ == null)
/*  4036:      */         {
/*  4037: 3753 */           if (((this.bitField0_ & 0x1) == 1) && (this.jobId_ != MRProtos.JobIdProto.getDefaultInstance())) {
/*  4038: 3755 */             this.jobId_ = MRProtos.JobIdProto.newBuilder(this.jobId_).mergeFrom(value).buildPartial();
/*  4039:      */           } else {
/*  4040: 3758 */             this.jobId_ = value;
/*  4041:      */           }
/*  4042: 3760 */           onChanged();
/*  4043:      */         }
/*  4044:      */         else
/*  4045:      */         {
/*  4046: 3762 */           this.jobIdBuilder_.mergeFrom(value);
/*  4047:      */         }
/*  4048: 3764 */         this.bitField0_ |= 0x1;
/*  4049: 3765 */         return this;
/*  4050:      */       }
/*  4051:      */       
/*  4052:      */       public Builder clearJobId()
/*  4053:      */       {
/*  4054: 3771 */         if (this.jobIdBuilder_ == null)
/*  4055:      */         {
/*  4056: 3772 */           this.jobId_ = MRProtos.JobIdProto.getDefaultInstance();
/*  4057: 3773 */           onChanged();
/*  4058:      */         }
/*  4059:      */         else
/*  4060:      */         {
/*  4061: 3775 */           this.jobIdBuilder_.clear();
/*  4062:      */         }
/*  4063: 3777 */         this.bitField0_ &= 0xFFFFFFFE;
/*  4064: 3778 */         return this;
/*  4065:      */       }
/*  4066:      */       
/*  4067:      */       public MRProtos.JobIdProto.Builder getJobIdBuilder()
/*  4068:      */       {
/*  4069: 3784 */         this.bitField0_ |= 0x1;
/*  4070: 3785 */         onChanged();
/*  4071: 3786 */         return (MRProtos.JobIdProto.Builder)getJobIdFieldBuilder().getBuilder();
/*  4072:      */       }
/*  4073:      */       
/*  4074:      */       public MRProtos.JobIdProtoOrBuilder getJobIdOrBuilder()
/*  4075:      */       {
/*  4076: 3792 */         if (this.jobIdBuilder_ != null) {
/*  4077: 3793 */           return (MRProtos.JobIdProtoOrBuilder)this.jobIdBuilder_.getMessageOrBuilder();
/*  4078:      */         }
/*  4079: 3795 */         return this.jobId_;
/*  4080:      */       }
/*  4081:      */       
/*  4082:      */       private SingleFieldBuilder<MRProtos.JobIdProto, MRProtos.JobIdProto.Builder, MRProtos.JobIdProtoOrBuilder> getJobIdFieldBuilder()
/*  4083:      */       {
/*  4084: 3804 */         if (this.jobIdBuilder_ == null)
/*  4085:      */         {
/*  4086: 3805 */           this.jobIdBuilder_ = new SingleFieldBuilder(this.jobId_, getParentForChildren(), isClean());
/*  4087:      */           
/*  4088:      */ 
/*  4089:      */ 
/*  4090:      */ 
/*  4091: 3810 */           this.jobId_ = null;
/*  4092:      */         }
/*  4093: 3812 */         return this.jobIdBuilder_;
/*  4094:      */       }
/*  4095:      */     }
/*  4096:      */     
/*  4097:      */     static
/*  4098:      */     {
/*  4099: 3819 */       defaultInstance = new GetCountersRequestProto(true);
/*  4100: 3820 */       defaultInstance.initFields();
/*  4101:      */     }
/*  4102:      */   }
/*  4103:      */   
/*  4104:      */   public static abstract interface GetCountersResponseProtoOrBuilder
/*  4105:      */     extends MessageOrBuilder
/*  4106:      */   {
/*  4107:      */     public abstract boolean hasCounters();
/*  4108:      */     
/*  4109:      */     public abstract MRProtos.CountersProto getCounters();
/*  4110:      */     
/*  4111:      */     public abstract MRProtos.CountersProtoOrBuilder getCountersOrBuilder();
/*  4112:      */   }
/*  4113:      */   
/*  4114:      */   public static final class GetCountersResponseProto
/*  4115:      */     extends GeneratedMessage
/*  4116:      */     implements MRServiceProtos.GetCountersResponseProtoOrBuilder
/*  4117:      */   {
/*  4118:      */     private static final GetCountersResponseProto defaultInstance;
/*  4119:      */     private final UnknownFieldSet unknownFields;
/*  4120:      */     
/*  4121:      */     private GetCountersResponseProto(GeneratedMessage.Builder<?> builder)
/*  4122:      */     {
/*  4123: 3851 */       super();
/*  4124: 3852 */       this.unknownFields = builder.getUnknownFields();
/*  4125:      */     }
/*  4126:      */     
/*  4127:      */     private GetCountersResponseProto(boolean noInit)
/*  4128:      */     {
/*  4129: 3854 */       this.unknownFields = UnknownFieldSet.getDefaultInstance();
/*  4130:      */     }
/*  4131:      */     
/*  4132:      */     public static GetCountersResponseProto getDefaultInstance()
/*  4133:      */     {
/*  4134: 3858 */       return defaultInstance;
/*  4135:      */     }
/*  4136:      */     
/*  4137:      */     public GetCountersResponseProto getDefaultInstanceForType()
/*  4138:      */     {
/*  4139: 3862 */       return defaultInstance;
/*  4140:      */     }
/*  4141:      */     
/*  4142:      */     public final UnknownFieldSet getUnknownFields()
/*  4143:      */     {
/*  4144: 3869 */       return this.unknownFields;
/*  4145:      */     }
/*  4146:      */     
/*  4147:      */     private GetCountersResponseProto(CodedInputStream input, ExtensionRegistryLite extensionRegistry)
/*  4148:      */       throws InvalidProtocolBufferException
/*  4149:      */     {
/*  4150: 3875 */       initFields();
/*  4151: 3876 */       int mutable_bitField0_ = 0;
/*  4152: 3877 */       UnknownFieldSet.Builder unknownFields = UnknownFieldSet.newBuilder();
/*  4153:      */       try
/*  4154:      */       {
/*  4155: 3880 */         boolean done = false;
/*  4156: 3881 */         while (!done)
/*  4157:      */         {
/*  4158: 3882 */           int tag = input.readTag();
/*  4159: 3883 */           switch (tag)
/*  4160:      */           {
/*  4161:      */           case 0: 
/*  4162: 3885 */             done = true;
/*  4163: 3886 */             break;
/*  4164:      */           default: 
/*  4165: 3888 */             if (!parseUnknownField(input, unknownFields, extensionRegistry, tag)) {
/*  4166: 3890 */               done = true;
/*  4167:      */             }
/*  4168:      */             break;
/*  4169:      */           case 10: 
/*  4170: 3895 */             MRProtos.CountersProto.Builder subBuilder = null;
/*  4171: 3896 */             if ((this.bitField0_ & 0x1) == 1) {
/*  4172: 3897 */               subBuilder = this.counters_.toBuilder();
/*  4173:      */             }
/*  4174: 3899 */             this.counters_ = ((MRProtos.CountersProto)input.readMessage(MRProtos.CountersProto.PARSER, extensionRegistry));
/*  4175: 3900 */             if (subBuilder != null)
/*  4176:      */             {
/*  4177: 3901 */               subBuilder.mergeFrom(this.counters_);
/*  4178: 3902 */               this.counters_ = subBuilder.buildPartial();
/*  4179:      */             }
/*  4180: 3904 */             this.bitField0_ |= 0x1;
/*  4181:      */           }
/*  4182:      */         }
/*  4183:      */       }
/*  4184:      */       catch (InvalidProtocolBufferException e)
/*  4185:      */       {
/*  4186: 3910 */         throw e.setUnfinishedMessage(this);
/*  4187:      */       }
/*  4188:      */       catch (IOException e)
/*  4189:      */       {
/*  4190: 3912 */         throw new InvalidProtocolBufferException(e.getMessage()).setUnfinishedMessage(this);
/*  4191:      */       }
/*  4192:      */       finally
/*  4193:      */       {
/*  4194: 3915 */         this.unknownFields = unknownFields.build();
/*  4195: 3916 */         makeExtensionsImmutable();
/*  4196:      */       }
/*  4197:      */     }
/*  4198:      */     
/*  4199:      */     public static final Descriptors.Descriptor getDescriptor()
/*  4200:      */     {
/*  4201: 3921 */       return MRServiceProtos.internal_static_hadoop_mapreduce_GetCountersResponseProto_descriptor;
/*  4202:      */     }
/*  4203:      */     
/*  4204:      */     protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable()
/*  4205:      */     {
/*  4206: 3926 */       return MRServiceProtos.internal_static_hadoop_mapreduce_GetCountersResponseProto_fieldAccessorTable.ensureFieldAccessorsInitialized(GetCountersResponseProto.class, Builder.class);
/*  4207:      */     }
/*  4208:      */     
/*  4209: 3931 */     public static Parser<GetCountersResponseProto> PARSER = new AbstractParser()
/*  4210:      */     {
/*  4211:      */       public MRServiceProtos.GetCountersResponseProto parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry)
/*  4212:      */         throws InvalidProtocolBufferException
/*  4213:      */       {
/*  4214: 3937 */         return new MRServiceProtos.GetCountersResponseProto(input, extensionRegistry, null);
/*  4215:      */       }
/*  4216:      */     };
/*  4217:      */     private int bitField0_;
/*  4218:      */     public static final int COUNTERS_FIELD_NUMBER = 1;
/*  4219:      */     private MRProtos.CountersProto counters_;
/*  4220:      */     
/*  4221:      */     public Parser<GetCountersResponseProto> getParserForType()
/*  4222:      */     {
/*  4223: 3943 */       return PARSER;
/*  4224:      */     }
/*  4225:      */     
/*  4226:      */     public boolean hasCounters()
/*  4227:      */     {
/*  4228: 3954 */       return (this.bitField0_ & 0x1) == 1;
/*  4229:      */     }
/*  4230:      */     
/*  4231:      */     public MRProtos.CountersProto getCounters()
/*  4232:      */     {
/*  4233: 3960 */       return this.counters_;
/*  4234:      */     }
/*  4235:      */     
/*  4236:      */     public MRProtos.CountersProtoOrBuilder getCountersOrBuilder()
/*  4237:      */     {
/*  4238: 3966 */       return this.counters_;
/*  4239:      */     }
/*  4240:      */     
/*  4241:      */     private void initFields()
/*  4242:      */     {
/*  4243: 3970 */       this.counters_ = MRProtos.CountersProto.getDefaultInstance();
/*  4244:      */     }
/*  4245:      */     
/*  4246: 3972 */     private byte memoizedIsInitialized = -1;
/*  4247:      */     
/*  4248:      */     public final boolean isInitialized()
/*  4249:      */     {
/*  4250: 3974 */       byte isInitialized = this.memoizedIsInitialized;
/*  4251: 3975 */       if (isInitialized != -1) {
/*  4252: 3975 */         return isInitialized == 1;
/*  4253:      */       }
/*  4254: 3977 */       this.memoizedIsInitialized = 1;
/*  4255: 3978 */       return true;
/*  4256:      */     }
/*  4257:      */     
/*  4258:      */     public void writeTo(CodedOutputStream output)
/*  4259:      */       throws IOException
/*  4260:      */     {
/*  4261: 3983 */       getSerializedSize();
/*  4262: 3984 */       if ((this.bitField0_ & 0x1) == 1) {
/*  4263: 3985 */         output.writeMessage(1, this.counters_);
/*  4264:      */       }
/*  4265: 3987 */       getUnknownFields().writeTo(output);
/*  4266:      */     }
/*  4267:      */     
/*  4268: 3990 */     private int memoizedSerializedSize = -1;
/*  4269:      */     private static final long serialVersionUID = 0L;
/*  4270:      */     
/*  4271:      */     public int getSerializedSize()
/*  4272:      */     {
/*  4273: 3992 */       int size = this.memoizedSerializedSize;
/*  4274: 3993 */       if (size != -1) {
/*  4275: 3993 */         return size;
/*  4276:      */       }
/*  4277: 3995 */       size = 0;
/*  4278: 3996 */       if ((this.bitField0_ & 0x1) == 1) {
/*  4279: 3997 */         size += CodedOutputStream.computeMessageSize(1, this.counters_);
/*  4280:      */       }
/*  4281: 4000 */       size += getUnknownFields().getSerializedSize();
/*  4282: 4001 */       this.memoizedSerializedSize = size;
/*  4283: 4002 */       return size;
/*  4284:      */     }
/*  4285:      */     
/*  4286:      */     protected Object writeReplace()
/*  4287:      */       throws ObjectStreamException
/*  4288:      */     {
/*  4289: 4009 */       return super.writeReplace();
/*  4290:      */     }
/*  4291:      */     
/*  4292:      */     public boolean equals(Object obj)
/*  4293:      */     {
/*  4294: 4014 */       if (obj == this) {
/*  4295: 4015 */         return true;
/*  4296:      */       }
/*  4297: 4017 */       if (!(obj instanceof GetCountersResponseProto)) {
/*  4298: 4018 */         return super.equals(obj);
/*  4299:      */       }
/*  4300: 4020 */       GetCountersResponseProto other = (GetCountersResponseProto)obj;
/*  4301:      */       
/*  4302: 4022 */       boolean result = true;
/*  4303: 4023 */       result = (result) && (hasCounters() == other.hasCounters());
/*  4304: 4024 */       if (hasCounters()) {
/*  4305: 4025 */         result = (result) && (getCounters().equals(other.getCounters()));
/*  4306:      */       }
/*  4307: 4028 */       result = (result) && (getUnknownFields().equals(other.getUnknownFields()));
/*  4308:      */       
/*  4309: 4030 */       return result;
/*  4310:      */     }
/*  4311:      */     
/*  4312: 4033 */     private int memoizedHashCode = 0;
/*  4313:      */     
/*  4314:      */     public int hashCode()
/*  4315:      */     {
/*  4316: 4036 */       if (this.memoizedHashCode != 0) {
/*  4317: 4037 */         return this.memoizedHashCode;
/*  4318:      */       }
/*  4319: 4039 */       int hash = 41;
/*  4320: 4040 */       hash = 19 * hash + getDescriptorForType().hashCode();
/*  4321: 4041 */       if (hasCounters())
/*  4322:      */       {
/*  4323: 4042 */         hash = 37 * hash + 1;
/*  4324: 4043 */         hash = 53 * hash + getCounters().hashCode();
/*  4325:      */       }
/*  4326: 4045 */       hash = 29 * hash + getUnknownFields().hashCode();
/*  4327: 4046 */       this.memoizedHashCode = hash;
/*  4328: 4047 */       return hash;
/*  4329:      */     }
/*  4330:      */     
/*  4331:      */     public static GetCountersResponseProto parseFrom(ByteString data)
/*  4332:      */       throws InvalidProtocolBufferException
/*  4333:      */     {
/*  4334: 4053 */       return (GetCountersResponseProto)PARSER.parseFrom(data);
/*  4335:      */     }
/*  4336:      */     
/*  4337:      */     public static GetCountersResponseProto parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry)
/*  4338:      */       throws InvalidProtocolBufferException
/*  4339:      */     {
/*  4340: 4059 */       return (GetCountersResponseProto)PARSER.parseFrom(data, extensionRegistry);
/*  4341:      */     }
/*  4342:      */     
/*  4343:      */     public static GetCountersResponseProto parseFrom(byte[] data)
/*  4344:      */       throws InvalidProtocolBufferException
/*  4345:      */     {
/*  4346: 4063 */       return (GetCountersResponseProto)PARSER.parseFrom(data);
/*  4347:      */     }
/*  4348:      */     
/*  4349:      */     public static GetCountersResponseProto parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry)
/*  4350:      */       throws InvalidProtocolBufferException
/*  4351:      */     {
/*  4352: 4069 */       return (GetCountersResponseProto)PARSER.parseFrom(data, extensionRegistry);
/*  4353:      */     }
/*  4354:      */     
/*  4355:      */     public static GetCountersResponseProto parseFrom(InputStream input)
/*  4356:      */       throws IOException
/*  4357:      */     {
/*  4358: 4073 */       return (GetCountersResponseProto)PARSER.parseFrom(input);
/*  4359:      */     }
/*  4360:      */     
/*  4361:      */     public static GetCountersResponseProto parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry)
/*  4362:      */       throws IOException
/*  4363:      */     {
/*  4364: 4079 */       return (GetCountersResponseProto)PARSER.parseFrom(input, extensionRegistry);
/*  4365:      */     }
/*  4366:      */     
/*  4367:      */     public static GetCountersResponseProto parseDelimitedFrom(InputStream input)
/*  4368:      */       throws IOException
/*  4369:      */     {
/*  4370: 4083 */       return (GetCountersResponseProto)PARSER.parseDelimitedFrom(input);
/*  4371:      */     }
/*  4372:      */     
/*  4373:      */     public static GetCountersResponseProto parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry)
/*  4374:      */       throws IOException
/*  4375:      */     {
/*  4376: 4089 */       return (GetCountersResponseProto)PARSER.parseDelimitedFrom(input, extensionRegistry);
/*  4377:      */     }
/*  4378:      */     
/*  4379:      */     public static GetCountersResponseProto parseFrom(CodedInputStream input)
/*  4380:      */       throws IOException
/*  4381:      */     {
/*  4382: 4094 */       return (GetCountersResponseProto)PARSER.parseFrom(input);
/*  4383:      */     }
/*  4384:      */     
/*  4385:      */     public static GetCountersResponseProto parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry)
/*  4386:      */       throws IOException
/*  4387:      */     {
/*  4388: 4100 */       return (GetCountersResponseProto)PARSER.parseFrom(input, extensionRegistry);
/*  4389:      */     }
/*  4390:      */     
/*  4391:      */     public static Builder newBuilder()
/*  4392:      */     {
/*  4393: 4103 */       return Builder.access$6600();
/*  4394:      */     }
/*  4395:      */     
/*  4396:      */     public Builder newBuilderForType()
/*  4397:      */     {
/*  4398: 4104 */       return newBuilder();
/*  4399:      */     }
/*  4400:      */     
/*  4401:      */     public static Builder newBuilder(GetCountersResponseProto prototype)
/*  4402:      */     {
/*  4403: 4106 */       return newBuilder().mergeFrom(prototype);
/*  4404:      */     }
/*  4405:      */     
/*  4406:      */     public Builder toBuilder()
/*  4407:      */     {
/*  4408: 4108 */       return newBuilder(this);
/*  4409:      */     }
/*  4410:      */     
/*  4411:      */     protected Builder newBuilderForType(GeneratedMessage.BuilderParent parent)
/*  4412:      */     {
/*  4413: 4113 */       Builder builder = new Builder(parent, null);
/*  4414: 4114 */       return builder;
/*  4415:      */     }
/*  4416:      */     
/*  4417:      */     public static final class Builder
/*  4418:      */       extends GeneratedMessage.Builder<Builder>
/*  4419:      */       implements MRServiceProtos.GetCountersResponseProtoOrBuilder
/*  4420:      */     {
/*  4421:      */       private int bitField0_;
/*  4422:      */       
/*  4423:      */       public static final Descriptors.Descriptor getDescriptor()
/*  4424:      */       {
/*  4425: 4124 */         return MRServiceProtos.internal_static_hadoop_mapreduce_GetCountersResponseProto_descriptor;
/*  4426:      */       }
/*  4427:      */       
/*  4428:      */       protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable()
/*  4429:      */       {
/*  4430: 4129 */         return MRServiceProtos.internal_static_hadoop_mapreduce_GetCountersResponseProto_fieldAccessorTable.ensureFieldAccessorsInitialized(MRServiceProtos.GetCountersResponseProto.class, Builder.class);
/*  4431:      */       }
/*  4432:      */       
/*  4433:      */       private Builder()
/*  4434:      */       {
/*  4435: 4136 */         maybeForceBuilderInitialization();
/*  4436:      */       }
/*  4437:      */       
/*  4438:      */       private Builder(GeneratedMessage.BuilderParent parent)
/*  4439:      */       {
/*  4440: 4141 */         super();
/*  4441: 4142 */         maybeForceBuilderInitialization();
/*  4442:      */       }
/*  4443:      */       
/*  4444:      */       private void maybeForceBuilderInitialization()
/*  4445:      */       {
/*  4446: 4145 */         if (MRServiceProtos.GetCountersResponseProto.alwaysUseFieldBuilders) {
/*  4447: 4146 */           getCountersFieldBuilder();
/*  4448:      */         }
/*  4449:      */       }
/*  4450:      */       
/*  4451:      */       private static Builder create()
/*  4452:      */       {
/*  4453: 4150 */         return new Builder();
/*  4454:      */       }
/*  4455:      */       
/*  4456:      */       public Builder clear()
/*  4457:      */       {
/*  4458: 4154 */         super.clear();
/*  4459: 4155 */         if (this.countersBuilder_ == null) {
/*  4460: 4156 */           this.counters_ = MRProtos.CountersProto.getDefaultInstance();
/*  4461:      */         } else {
/*  4462: 4158 */           this.countersBuilder_.clear();
/*  4463:      */         }
/*  4464: 4160 */         this.bitField0_ &= 0xFFFFFFFE;
/*  4465: 4161 */         return this;
/*  4466:      */       }
/*  4467:      */       
/*  4468:      */       public Builder clone()
/*  4469:      */       {
/*  4470: 4165 */         return create().mergeFrom(buildPartial());
/*  4471:      */       }
/*  4472:      */       
/*  4473:      */       public Descriptors.Descriptor getDescriptorForType()
/*  4474:      */       {
/*  4475: 4170 */         return MRServiceProtos.internal_static_hadoop_mapreduce_GetCountersResponseProto_descriptor;
/*  4476:      */       }
/*  4477:      */       
/*  4478:      */       public MRServiceProtos.GetCountersResponseProto getDefaultInstanceForType()
/*  4479:      */       {
/*  4480: 4174 */         return MRServiceProtos.GetCountersResponseProto.getDefaultInstance();
/*  4481:      */       }
/*  4482:      */       
/*  4483:      */       public MRServiceProtos.GetCountersResponseProto build()
/*  4484:      */       {
/*  4485: 4178 */         MRServiceProtos.GetCountersResponseProto result = buildPartial();
/*  4486: 4179 */         if (!result.isInitialized()) {
/*  4487: 4180 */           throw newUninitializedMessageException(result);
/*  4488:      */         }
/*  4489: 4182 */         return result;
/*  4490:      */       }
/*  4491:      */       
/*  4492:      */       public MRServiceProtos.GetCountersResponseProto buildPartial()
/*  4493:      */       {
/*  4494: 4186 */         MRServiceProtos.GetCountersResponseProto result = new MRServiceProtos.GetCountersResponseProto(this, null);
/*  4495: 4187 */         int from_bitField0_ = this.bitField0_;
/*  4496: 4188 */         int to_bitField0_ = 0;
/*  4497: 4189 */         if ((from_bitField0_ & 0x1) == 1) {
/*  4498: 4190 */           to_bitField0_ |= 0x1;
/*  4499:      */         }
/*  4500: 4192 */         if (this.countersBuilder_ == null) {
/*  4501: 4193 */           result.counters_ = this.counters_;
/*  4502:      */         } else {
/*  4503: 4195 */           result.counters_ = ((MRProtos.CountersProto)this.countersBuilder_.build());
/*  4504:      */         }
/*  4505: 4197 */         result.bitField0_ = to_bitField0_;
/*  4506: 4198 */         onBuilt();
/*  4507: 4199 */         return result;
/*  4508:      */       }
/*  4509:      */       
/*  4510:      */       public Builder mergeFrom(Message other)
/*  4511:      */       {
/*  4512: 4203 */         if ((other instanceof MRServiceProtos.GetCountersResponseProto)) {
/*  4513: 4204 */           return mergeFrom((MRServiceProtos.GetCountersResponseProto)other);
/*  4514:      */         }
/*  4515: 4206 */         super.mergeFrom(other);
/*  4516: 4207 */         return this;
/*  4517:      */       }
/*  4518:      */       
/*  4519:      */       public Builder mergeFrom(MRServiceProtos.GetCountersResponseProto other)
/*  4520:      */       {
/*  4521: 4212 */         if (other == MRServiceProtos.GetCountersResponseProto.getDefaultInstance()) {
/*  4522: 4212 */           return this;
/*  4523:      */         }
/*  4524: 4213 */         if (other.hasCounters()) {
/*  4525: 4214 */           mergeCounters(other.getCounters());
/*  4526:      */         }
/*  4527: 4216 */         mergeUnknownFields(other.getUnknownFields());
/*  4528: 4217 */         return this;
/*  4529:      */       }
/*  4530:      */       
/*  4531:      */       public final boolean isInitialized()
/*  4532:      */       {
/*  4533: 4221 */         return true;
/*  4534:      */       }
/*  4535:      */       
/*  4536:      */       public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry)
/*  4537:      */         throws IOException
/*  4538:      */       {
/*  4539: 4228 */         MRServiceProtos.GetCountersResponseProto parsedMessage = null;
/*  4540:      */         try
/*  4541:      */         {
/*  4542: 4230 */           parsedMessage = (MRServiceProtos.GetCountersResponseProto)MRServiceProtos.GetCountersResponseProto.PARSER.parsePartialFrom(input, extensionRegistry);
/*  4543:      */         }
/*  4544:      */         catch (InvalidProtocolBufferException e)
/*  4545:      */         {
/*  4546: 4232 */           parsedMessage = (MRServiceProtos.GetCountersResponseProto)e.getUnfinishedMessage();
/*  4547: 4233 */           throw e;
/*  4548:      */         }
/*  4549:      */         finally
/*  4550:      */         {
/*  4551: 4235 */           if (parsedMessage != null) {
/*  4552: 4236 */             mergeFrom(parsedMessage);
/*  4553:      */           }
/*  4554:      */         }
/*  4555: 4239 */         return this;
/*  4556:      */       }
/*  4557:      */       
/*  4558: 4244 */       private MRProtos.CountersProto counters_ = MRProtos.CountersProto.getDefaultInstance();
/*  4559:      */       private SingleFieldBuilder<MRProtos.CountersProto, MRProtos.CountersProto.Builder, MRProtos.CountersProtoOrBuilder> countersBuilder_;
/*  4560:      */       
/*  4561:      */       public boolean hasCounters()
/*  4562:      */       {
/*  4563: 4251 */         return (this.bitField0_ & 0x1) == 1;
/*  4564:      */       }
/*  4565:      */       
/*  4566:      */       public MRProtos.CountersProto getCounters()
/*  4567:      */       {
/*  4568: 4257 */         if (this.countersBuilder_ == null) {
/*  4569: 4258 */           return this.counters_;
/*  4570:      */         }
/*  4571: 4260 */         return (MRProtos.CountersProto)this.countersBuilder_.getMessage();
/*  4572:      */       }
/*  4573:      */       
/*  4574:      */       public Builder setCounters(MRProtos.CountersProto value)
/*  4575:      */       {
/*  4576: 4267 */         if (this.countersBuilder_ == null)
/*  4577:      */         {
/*  4578: 4268 */           if (value == null) {
/*  4579: 4269 */             throw new NullPointerException();
/*  4580:      */           }
/*  4581: 4271 */           this.counters_ = value;
/*  4582: 4272 */           onChanged();
/*  4583:      */         }
/*  4584:      */         else
/*  4585:      */         {
/*  4586: 4274 */           this.countersBuilder_.setMessage(value);
/*  4587:      */         }
/*  4588: 4276 */         this.bitField0_ |= 0x1;
/*  4589: 4277 */         return this;
/*  4590:      */       }
/*  4591:      */       
/*  4592:      */       public Builder setCounters(MRProtos.CountersProto.Builder builderForValue)
/*  4593:      */       {
/*  4594: 4284 */         if (this.countersBuilder_ == null)
/*  4595:      */         {
/*  4596: 4285 */           this.counters_ = builderForValue.build();
/*  4597: 4286 */           onChanged();
/*  4598:      */         }
/*  4599:      */         else
/*  4600:      */         {
/*  4601: 4288 */           this.countersBuilder_.setMessage(builderForValue.build());
/*  4602:      */         }
/*  4603: 4290 */         this.bitField0_ |= 0x1;
/*  4604: 4291 */         return this;
/*  4605:      */       }
/*  4606:      */       
/*  4607:      */       public Builder mergeCounters(MRProtos.CountersProto value)
/*  4608:      */       {
/*  4609: 4297 */         if (this.countersBuilder_ == null)
/*  4610:      */         {
/*  4611: 4298 */           if (((this.bitField0_ & 0x1) == 1) && (this.counters_ != MRProtos.CountersProto.getDefaultInstance())) {
/*  4612: 4300 */             this.counters_ = MRProtos.CountersProto.newBuilder(this.counters_).mergeFrom(value).buildPartial();
/*  4613:      */           } else {
/*  4614: 4303 */             this.counters_ = value;
/*  4615:      */           }
/*  4616: 4305 */           onChanged();
/*  4617:      */         }
/*  4618:      */         else
/*  4619:      */         {
/*  4620: 4307 */           this.countersBuilder_.mergeFrom(value);
/*  4621:      */         }
/*  4622: 4309 */         this.bitField0_ |= 0x1;
/*  4623: 4310 */         return this;
/*  4624:      */       }
/*  4625:      */       
/*  4626:      */       public Builder clearCounters()
/*  4627:      */       {
/*  4628: 4316 */         if (this.countersBuilder_ == null)
/*  4629:      */         {
/*  4630: 4317 */           this.counters_ = MRProtos.CountersProto.getDefaultInstance();
/*  4631: 4318 */           onChanged();
/*  4632:      */         }
/*  4633:      */         else
/*  4634:      */         {
/*  4635: 4320 */           this.countersBuilder_.clear();
/*  4636:      */         }
/*  4637: 4322 */         this.bitField0_ &= 0xFFFFFFFE;
/*  4638: 4323 */         return this;
/*  4639:      */       }
/*  4640:      */       
/*  4641:      */       public MRProtos.CountersProto.Builder getCountersBuilder()
/*  4642:      */       {
/*  4643: 4329 */         this.bitField0_ |= 0x1;
/*  4644: 4330 */         onChanged();
/*  4645: 4331 */         return (MRProtos.CountersProto.Builder)getCountersFieldBuilder().getBuilder();
/*  4646:      */       }
/*  4647:      */       
/*  4648:      */       public MRProtos.CountersProtoOrBuilder getCountersOrBuilder()
/*  4649:      */       {
/*  4650: 4337 */         if (this.countersBuilder_ != null) {
/*  4651: 4338 */           return (MRProtos.CountersProtoOrBuilder)this.countersBuilder_.getMessageOrBuilder();
/*  4652:      */         }
/*  4653: 4340 */         return this.counters_;
/*  4654:      */       }
/*  4655:      */       
/*  4656:      */       private SingleFieldBuilder<MRProtos.CountersProto, MRProtos.CountersProto.Builder, MRProtos.CountersProtoOrBuilder> getCountersFieldBuilder()
/*  4657:      */       {
/*  4658: 4349 */         if (this.countersBuilder_ == null)
/*  4659:      */         {
/*  4660: 4350 */           this.countersBuilder_ = new SingleFieldBuilder(this.counters_, getParentForChildren(), isClean());
/*  4661:      */           
/*  4662:      */ 
/*  4663:      */ 
/*  4664:      */ 
/*  4665: 4355 */           this.counters_ = null;
/*  4666:      */         }
/*  4667: 4357 */         return this.countersBuilder_;
/*  4668:      */       }
/*  4669:      */     }
/*  4670:      */     
/*  4671:      */     static
/*  4672:      */     {
/*  4673: 4364 */       defaultInstance = new GetCountersResponseProto(true);
/*  4674: 4365 */       defaultInstance.initFields();
/*  4675:      */     }
/*  4676:      */   }
/*  4677:      */   
/*  4678:      */   public static abstract interface GetTaskAttemptCompletionEventsRequestProtoOrBuilder
/*  4679:      */     extends MessageOrBuilder
/*  4680:      */   {
/*  4681:      */     public abstract boolean hasJobId();
/*  4682:      */     
/*  4683:      */     public abstract MRProtos.JobIdProto getJobId();
/*  4684:      */     
/*  4685:      */     public abstract MRProtos.JobIdProtoOrBuilder getJobIdOrBuilder();
/*  4686:      */     
/*  4687:      */     public abstract boolean hasFromEventId();
/*  4688:      */     
/*  4689:      */     public abstract int getFromEventId();
/*  4690:      */     
/*  4691:      */     public abstract boolean hasMaxEvents();
/*  4692:      */     
/*  4693:      */     public abstract int getMaxEvents();
/*  4694:      */   }
/*  4695:      */   
/*  4696:      */   public static final class GetTaskAttemptCompletionEventsRequestProto
/*  4697:      */     extends GeneratedMessage
/*  4698:      */     implements MRServiceProtos.GetTaskAttemptCompletionEventsRequestProtoOrBuilder
/*  4699:      */   {
/*  4700:      */     private static final GetTaskAttemptCompletionEventsRequestProto defaultInstance;
/*  4701:      */     private final UnknownFieldSet unknownFields;
/*  4702:      */     
/*  4703:      */     private GetTaskAttemptCompletionEventsRequestProto(GeneratedMessage.Builder<?> builder)
/*  4704:      */     {
/*  4705: 4416 */       super();
/*  4706: 4417 */       this.unknownFields = builder.getUnknownFields();
/*  4707:      */     }
/*  4708:      */     
/*  4709:      */     private GetTaskAttemptCompletionEventsRequestProto(boolean noInit)
/*  4710:      */     {
/*  4711: 4419 */       this.unknownFields = UnknownFieldSet.getDefaultInstance();
/*  4712:      */     }
/*  4713:      */     
/*  4714:      */     public static GetTaskAttemptCompletionEventsRequestProto getDefaultInstance()
/*  4715:      */     {
/*  4716: 4423 */       return defaultInstance;
/*  4717:      */     }
/*  4718:      */     
/*  4719:      */     public GetTaskAttemptCompletionEventsRequestProto getDefaultInstanceForType()
/*  4720:      */     {
/*  4721: 4427 */       return defaultInstance;
/*  4722:      */     }
/*  4723:      */     
/*  4724:      */     public final UnknownFieldSet getUnknownFields()
/*  4725:      */     {
/*  4726: 4434 */       return this.unknownFields;
/*  4727:      */     }
/*  4728:      */     
/*  4729:      */     private GetTaskAttemptCompletionEventsRequestProto(CodedInputStream input, ExtensionRegistryLite extensionRegistry)
/*  4730:      */       throws InvalidProtocolBufferException
/*  4731:      */     {
/*  4732: 4440 */       initFields();
/*  4733: 4441 */       int mutable_bitField0_ = 0;
/*  4734: 4442 */       UnknownFieldSet.Builder unknownFields = UnknownFieldSet.newBuilder();
/*  4735:      */       try
/*  4736:      */       {
/*  4737: 4445 */         boolean done = false;
/*  4738: 4446 */         while (!done)
/*  4739:      */         {
/*  4740: 4447 */           int tag = input.readTag();
/*  4741: 4448 */           switch (tag)
/*  4742:      */           {
/*  4743:      */           case 0: 
/*  4744: 4450 */             done = true;
/*  4745: 4451 */             break;
/*  4746:      */           default: 
/*  4747: 4453 */             if (!parseUnknownField(input, unknownFields, extensionRegistry, tag)) {
/*  4748: 4455 */               done = true;
/*  4749:      */             }
/*  4750:      */             break;
/*  4751:      */           case 10: 
/*  4752: 4460 */             MRProtos.JobIdProto.Builder subBuilder = null;
/*  4753: 4461 */             if ((this.bitField0_ & 0x1) == 1) {
/*  4754: 4462 */               subBuilder = this.jobId_.toBuilder();
/*  4755:      */             }
/*  4756: 4464 */             this.jobId_ = ((MRProtos.JobIdProto)input.readMessage(MRProtos.JobIdProto.PARSER, extensionRegistry));
/*  4757: 4465 */             if (subBuilder != null)
/*  4758:      */             {
/*  4759: 4466 */               subBuilder.mergeFrom(this.jobId_);
/*  4760: 4467 */               this.jobId_ = subBuilder.buildPartial();
/*  4761:      */             }
/*  4762: 4469 */             this.bitField0_ |= 0x1;
/*  4763: 4470 */             break;
/*  4764:      */           case 16: 
/*  4765: 4473 */             this.bitField0_ |= 0x2;
/*  4766: 4474 */             this.fromEventId_ = input.readInt32();
/*  4767: 4475 */             break;
/*  4768:      */           case 24: 
/*  4769: 4478 */             this.bitField0_ |= 0x4;
/*  4770: 4479 */             this.maxEvents_ = input.readInt32();
/*  4771:      */           }
/*  4772:      */         }
/*  4773:      */       }
/*  4774:      */       catch (InvalidProtocolBufferException e)
/*  4775:      */       {
/*  4776: 4485 */         throw e.setUnfinishedMessage(this);
/*  4777:      */       }
/*  4778:      */       catch (IOException e)
/*  4779:      */       {
/*  4780: 4487 */         throw new InvalidProtocolBufferException(e.getMessage()).setUnfinishedMessage(this);
/*  4781:      */       }
/*  4782:      */       finally
/*  4783:      */       {
/*  4784: 4490 */         this.unknownFields = unknownFields.build();
/*  4785: 4491 */         makeExtensionsImmutable();
/*  4786:      */       }
/*  4787:      */     }
/*  4788:      */     
/*  4789:      */     public static final Descriptors.Descriptor getDescriptor()
/*  4790:      */     {
/*  4791: 4496 */       return MRServiceProtos.internal_static_hadoop_mapreduce_GetTaskAttemptCompletionEventsRequestProto_descriptor;
/*  4792:      */     }
/*  4793:      */     
/*  4794:      */     protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable()
/*  4795:      */     {
/*  4796: 4501 */       return MRServiceProtos.internal_static_hadoop_mapreduce_GetTaskAttemptCompletionEventsRequestProto_fieldAccessorTable.ensureFieldAccessorsInitialized(GetTaskAttemptCompletionEventsRequestProto.class, Builder.class);
/*  4797:      */     }
/*  4798:      */     
/*  4799: 4506 */     public static Parser<GetTaskAttemptCompletionEventsRequestProto> PARSER = new AbstractParser()
/*  4800:      */     {
/*  4801:      */       public MRServiceProtos.GetTaskAttemptCompletionEventsRequestProto parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry)
/*  4802:      */         throws InvalidProtocolBufferException
/*  4803:      */       {
/*  4804: 4512 */         return new MRServiceProtos.GetTaskAttemptCompletionEventsRequestProto(input, extensionRegistry, null);
/*  4805:      */       }
/*  4806:      */     };
/*  4807:      */     private int bitField0_;
/*  4808:      */     public static final int JOB_ID_FIELD_NUMBER = 1;
/*  4809:      */     private MRProtos.JobIdProto jobId_;
/*  4810:      */     public static final int FROM_EVENT_ID_FIELD_NUMBER = 2;
/*  4811:      */     private int fromEventId_;
/*  4812:      */     public static final int MAX_EVENTS_FIELD_NUMBER = 3;
/*  4813:      */     private int maxEvents_;
/*  4814:      */     
/*  4815:      */     public Parser<GetTaskAttemptCompletionEventsRequestProto> getParserForType()
/*  4816:      */     {
/*  4817: 4518 */       return PARSER;
/*  4818:      */     }
/*  4819:      */     
/*  4820:      */     public boolean hasJobId()
/*  4821:      */     {
/*  4822: 4529 */       return (this.bitField0_ & 0x1) == 1;
/*  4823:      */     }
/*  4824:      */     
/*  4825:      */     public MRProtos.JobIdProto getJobId()
/*  4826:      */     {
/*  4827: 4535 */       return this.jobId_;
/*  4828:      */     }
/*  4829:      */     
/*  4830:      */     public MRProtos.JobIdProtoOrBuilder getJobIdOrBuilder()
/*  4831:      */     {
/*  4832: 4541 */       return this.jobId_;
/*  4833:      */     }
/*  4834:      */     
/*  4835:      */     public boolean hasFromEventId()
/*  4836:      */     {
/*  4837: 4551 */       return (this.bitField0_ & 0x2) == 2;
/*  4838:      */     }
/*  4839:      */     
/*  4840:      */     public int getFromEventId()
/*  4841:      */     {
/*  4842: 4557 */       return this.fromEventId_;
/*  4843:      */     }
/*  4844:      */     
/*  4845:      */     public boolean hasMaxEvents()
/*  4846:      */     {
/*  4847: 4567 */       return (this.bitField0_ & 0x4) == 4;
/*  4848:      */     }
/*  4849:      */     
/*  4850:      */     public int getMaxEvents()
/*  4851:      */     {
/*  4852: 4573 */       return this.maxEvents_;
/*  4853:      */     }
/*  4854:      */     
/*  4855:      */     private void initFields()
/*  4856:      */     {
/*  4857: 4577 */       this.jobId_ = MRProtos.JobIdProto.getDefaultInstance();
/*  4858: 4578 */       this.fromEventId_ = 0;
/*  4859: 4579 */       this.maxEvents_ = 0;
/*  4860:      */     }
/*  4861:      */     
/*  4862: 4581 */     private byte memoizedIsInitialized = -1;
/*  4863:      */     
/*  4864:      */     public final boolean isInitialized()
/*  4865:      */     {
/*  4866: 4583 */       byte isInitialized = this.memoizedIsInitialized;
/*  4867: 4584 */       if (isInitialized != -1) {
/*  4868: 4584 */         return isInitialized == 1;
/*  4869:      */       }
/*  4870: 4586 */       this.memoizedIsInitialized = 1;
/*  4871: 4587 */       return true;
/*  4872:      */     }
/*  4873:      */     
/*  4874:      */     public void writeTo(CodedOutputStream output)
/*  4875:      */       throws IOException
/*  4876:      */     {
/*  4877: 4592 */       getSerializedSize();
/*  4878: 4593 */       if ((this.bitField0_ & 0x1) == 1) {
/*  4879: 4594 */         output.writeMessage(1, this.jobId_);
/*  4880:      */       }
/*  4881: 4596 */       if ((this.bitField0_ & 0x2) == 2) {
/*  4882: 4597 */         output.writeInt32(2, this.fromEventId_);
/*  4883:      */       }
/*  4884: 4599 */       if ((this.bitField0_ & 0x4) == 4) {
/*  4885: 4600 */         output.writeInt32(3, this.maxEvents_);
/*  4886:      */       }
/*  4887: 4602 */       getUnknownFields().writeTo(output);
/*  4888:      */     }
/*  4889:      */     
/*  4890: 4605 */     private int memoizedSerializedSize = -1;
/*  4891:      */     private static final long serialVersionUID = 0L;
/*  4892:      */     
/*  4893:      */     public int getSerializedSize()
/*  4894:      */     {
/*  4895: 4607 */       int size = this.memoizedSerializedSize;
/*  4896: 4608 */       if (size != -1) {
/*  4897: 4608 */         return size;
/*  4898:      */       }
/*  4899: 4610 */       size = 0;
/*  4900: 4611 */       if ((this.bitField0_ & 0x1) == 1) {
/*  4901: 4612 */         size += CodedOutputStream.computeMessageSize(1, this.jobId_);
/*  4902:      */       }
/*  4903: 4615 */       if ((this.bitField0_ & 0x2) == 2) {
/*  4904: 4616 */         size += CodedOutputStream.computeInt32Size(2, this.fromEventId_);
/*  4905:      */       }
/*  4906: 4619 */       if ((this.bitField0_ & 0x4) == 4) {
/*  4907: 4620 */         size += CodedOutputStream.computeInt32Size(3, this.maxEvents_);
/*  4908:      */       }
/*  4909: 4623 */       size += getUnknownFields().getSerializedSize();
/*  4910: 4624 */       this.memoizedSerializedSize = size;
/*  4911: 4625 */       return size;
/*  4912:      */     }
/*  4913:      */     
/*  4914:      */     protected Object writeReplace()
/*  4915:      */       throws ObjectStreamException
/*  4916:      */     {
/*  4917: 4632 */       return super.writeReplace();
/*  4918:      */     }
/*  4919:      */     
/*  4920:      */     public boolean equals(Object obj)
/*  4921:      */     {
/*  4922: 4637 */       if (obj == this) {
/*  4923: 4638 */         return true;
/*  4924:      */       }
/*  4925: 4640 */       if (!(obj instanceof GetTaskAttemptCompletionEventsRequestProto)) {
/*  4926: 4641 */         return super.equals(obj);
/*  4927:      */       }
/*  4928: 4643 */       GetTaskAttemptCompletionEventsRequestProto other = (GetTaskAttemptCompletionEventsRequestProto)obj;
/*  4929:      */       
/*  4930: 4645 */       boolean result = true;
/*  4931: 4646 */       result = (result) && (hasJobId() == other.hasJobId());
/*  4932: 4647 */       if (hasJobId()) {
/*  4933: 4648 */         result = (result) && (getJobId().equals(other.getJobId()));
/*  4934:      */       }
/*  4935: 4651 */       result = (result) && (hasFromEventId() == other.hasFromEventId());
/*  4936: 4652 */       if (hasFromEventId()) {
/*  4937: 4653 */         result = (result) && (getFromEventId() == other.getFromEventId());
/*  4938:      */       }
/*  4939: 4656 */       result = (result) && (hasMaxEvents() == other.hasMaxEvents());
/*  4940: 4657 */       if (hasMaxEvents()) {
/*  4941: 4658 */         result = (result) && (getMaxEvents() == other.getMaxEvents());
/*  4942:      */       }
/*  4943: 4661 */       result = (result) && (getUnknownFields().equals(other.getUnknownFields()));
/*  4944:      */       
/*  4945: 4663 */       return result;
/*  4946:      */     }
/*  4947:      */     
/*  4948: 4666 */     private int memoizedHashCode = 0;
/*  4949:      */     
/*  4950:      */     public int hashCode()
/*  4951:      */     {
/*  4952: 4669 */       if (this.memoizedHashCode != 0) {
/*  4953: 4670 */         return this.memoizedHashCode;
/*  4954:      */       }
/*  4955: 4672 */       int hash = 41;
/*  4956: 4673 */       hash = 19 * hash + getDescriptorForType().hashCode();
/*  4957: 4674 */       if (hasJobId())
/*  4958:      */       {
/*  4959: 4675 */         hash = 37 * hash + 1;
/*  4960: 4676 */         hash = 53 * hash + getJobId().hashCode();
/*  4961:      */       }
/*  4962: 4678 */       if (hasFromEventId())
/*  4963:      */       {
/*  4964: 4679 */         hash = 37 * hash + 2;
/*  4965: 4680 */         hash = 53 * hash + getFromEventId();
/*  4966:      */       }
/*  4967: 4682 */       if (hasMaxEvents())
/*  4968:      */       {
/*  4969: 4683 */         hash = 37 * hash + 3;
/*  4970: 4684 */         hash = 53 * hash + getMaxEvents();
/*  4971:      */       }
/*  4972: 4686 */       hash = 29 * hash + getUnknownFields().hashCode();
/*  4973: 4687 */       this.memoizedHashCode = hash;
/*  4974: 4688 */       return hash;
/*  4975:      */     }
/*  4976:      */     
/*  4977:      */     public static GetTaskAttemptCompletionEventsRequestProto parseFrom(ByteString data)
/*  4978:      */       throws InvalidProtocolBufferException
/*  4979:      */     {
/*  4980: 4694 */       return (GetTaskAttemptCompletionEventsRequestProto)PARSER.parseFrom(data);
/*  4981:      */     }
/*  4982:      */     
/*  4983:      */     public static GetTaskAttemptCompletionEventsRequestProto parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry)
/*  4984:      */       throws InvalidProtocolBufferException
/*  4985:      */     {
/*  4986: 4700 */       return (GetTaskAttemptCompletionEventsRequestProto)PARSER.parseFrom(data, extensionRegistry);
/*  4987:      */     }
/*  4988:      */     
/*  4989:      */     public static GetTaskAttemptCompletionEventsRequestProto parseFrom(byte[] data)
/*  4990:      */       throws InvalidProtocolBufferException
/*  4991:      */     {
/*  4992: 4704 */       return (GetTaskAttemptCompletionEventsRequestProto)PARSER.parseFrom(data);
/*  4993:      */     }
/*  4994:      */     
/*  4995:      */     public static GetTaskAttemptCompletionEventsRequestProto parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry)
/*  4996:      */       throws InvalidProtocolBufferException
/*  4997:      */     {
/*  4998: 4710 */       return (GetTaskAttemptCompletionEventsRequestProto)PARSER.parseFrom(data, extensionRegistry);
/*  4999:      */     }
/*  5000:      */     
/*  5001:      */     public static GetTaskAttemptCompletionEventsRequestProto parseFrom(InputStream input)
/*  5002:      */       throws IOException
/*  5003:      */     {
/*  5004: 4714 */       return (GetTaskAttemptCompletionEventsRequestProto)PARSER.parseFrom(input);
/*  5005:      */     }
/*  5006:      */     
/*  5007:      */     public static GetTaskAttemptCompletionEventsRequestProto parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry)
/*  5008:      */       throws IOException
/*  5009:      */     {
/*  5010: 4720 */       return (GetTaskAttemptCompletionEventsRequestProto)PARSER.parseFrom(input, extensionRegistry);
/*  5011:      */     }
/*  5012:      */     
/*  5013:      */     public static GetTaskAttemptCompletionEventsRequestProto parseDelimitedFrom(InputStream input)
/*  5014:      */       throws IOException
/*  5015:      */     {
/*  5016: 4724 */       return (GetTaskAttemptCompletionEventsRequestProto)PARSER.parseDelimitedFrom(input);
/*  5017:      */     }
/*  5018:      */     
/*  5019:      */     public static GetTaskAttemptCompletionEventsRequestProto parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry)
/*  5020:      */       throws IOException
/*  5021:      */     {
/*  5022: 4730 */       return (GetTaskAttemptCompletionEventsRequestProto)PARSER.parseDelimitedFrom(input, extensionRegistry);
/*  5023:      */     }
/*  5024:      */     
/*  5025:      */     public static GetTaskAttemptCompletionEventsRequestProto parseFrom(CodedInputStream input)
/*  5026:      */       throws IOException
/*  5027:      */     {
/*  5028: 4735 */       return (GetTaskAttemptCompletionEventsRequestProto)PARSER.parseFrom(input);
/*  5029:      */     }
/*  5030:      */     
/*  5031:      */     public static GetTaskAttemptCompletionEventsRequestProto parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry)
/*  5032:      */       throws IOException
/*  5033:      */     {
/*  5034: 4741 */       return (GetTaskAttemptCompletionEventsRequestProto)PARSER.parseFrom(input, extensionRegistry);
/*  5035:      */     }
/*  5036:      */     
/*  5037:      */     public static Builder newBuilder()
/*  5038:      */     {
/*  5039: 4744 */       return Builder.access$7500();
/*  5040:      */     }
/*  5041:      */     
/*  5042:      */     public Builder newBuilderForType()
/*  5043:      */     {
/*  5044: 4745 */       return newBuilder();
/*  5045:      */     }
/*  5046:      */     
/*  5047:      */     public static Builder newBuilder(GetTaskAttemptCompletionEventsRequestProto prototype)
/*  5048:      */     {
/*  5049: 4747 */       return newBuilder().mergeFrom(prototype);
/*  5050:      */     }
/*  5051:      */     
/*  5052:      */     public Builder toBuilder()
/*  5053:      */     {
/*  5054: 4749 */       return newBuilder(this);
/*  5055:      */     }
/*  5056:      */     
/*  5057:      */     protected Builder newBuilderForType(GeneratedMessage.BuilderParent parent)
/*  5058:      */     {
/*  5059: 4754 */       Builder builder = new Builder(parent, null);
/*  5060: 4755 */       return builder;
/*  5061:      */     }
/*  5062:      */     
/*  5063:      */     public static final class Builder
/*  5064:      */       extends GeneratedMessage.Builder<Builder>
/*  5065:      */       implements MRServiceProtos.GetTaskAttemptCompletionEventsRequestProtoOrBuilder
/*  5066:      */     {
/*  5067:      */       private int bitField0_;
/*  5068:      */       
/*  5069:      */       public static final Descriptors.Descriptor getDescriptor()
/*  5070:      */       {
/*  5071: 4765 */         return MRServiceProtos.internal_static_hadoop_mapreduce_GetTaskAttemptCompletionEventsRequestProto_descriptor;
/*  5072:      */       }
/*  5073:      */       
/*  5074:      */       protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable()
/*  5075:      */       {
/*  5076: 4770 */         return MRServiceProtos.internal_static_hadoop_mapreduce_GetTaskAttemptCompletionEventsRequestProto_fieldAccessorTable.ensureFieldAccessorsInitialized(MRServiceProtos.GetTaskAttemptCompletionEventsRequestProto.class, Builder.class);
/*  5077:      */       }
/*  5078:      */       
/*  5079:      */       private Builder()
/*  5080:      */       {
/*  5081: 4777 */         maybeForceBuilderInitialization();
/*  5082:      */       }
/*  5083:      */       
/*  5084:      */       private Builder(GeneratedMessage.BuilderParent parent)
/*  5085:      */       {
/*  5086: 4782 */         super();
/*  5087: 4783 */         maybeForceBuilderInitialization();
/*  5088:      */       }
/*  5089:      */       
/*  5090:      */       private void maybeForceBuilderInitialization()
/*  5091:      */       {
/*  5092: 4786 */         if (MRServiceProtos.GetTaskAttemptCompletionEventsRequestProto.alwaysUseFieldBuilders) {
/*  5093: 4787 */           getJobIdFieldBuilder();
/*  5094:      */         }
/*  5095:      */       }
/*  5096:      */       
/*  5097:      */       private static Builder create()
/*  5098:      */       {
/*  5099: 4791 */         return new Builder();
/*  5100:      */       }
/*  5101:      */       
/*  5102:      */       public Builder clear()
/*  5103:      */       {
/*  5104: 4795 */         super.clear();
/*  5105: 4796 */         if (this.jobIdBuilder_ == null) {
/*  5106: 4797 */           this.jobId_ = MRProtos.JobIdProto.getDefaultInstance();
/*  5107:      */         } else {
/*  5108: 4799 */           this.jobIdBuilder_.clear();
/*  5109:      */         }
/*  5110: 4801 */         this.bitField0_ &= 0xFFFFFFFE;
/*  5111: 4802 */         this.fromEventId_ = 0;
/*  5112: 4803 */         this.bitField0_ &= 0xFFFFFFFD;
/*  5113: 4804 */         this.maxEvents_ = 0;
/*  5114: 4805 */         this.bitField0_ &= 0xFFFFFFFB;
/*  5115: 4806 */         return this;
/*  5116:      */       }
/*  5117:      */       
/*  5118:      */       public Builder clone()
/*  5119:      */       {
/*  5120: 4810 */         return create().mergeFrom(buildPartial());
/*  5121:      */       }
/*  5122:      */       
/*  5123:      */       public Descriptors.Descriptor getDescriptorForType()
/*  5124:      */       {
/*  5125: 4815 */         return MRServiceProtos.internal_static_hadoop_mapreduce_GetTaskAttemptCompletionEventsRequestProto_descriptor;
/*  5126:      */       }
/*  5127:      */       
/*  5128:      */       public MRServiceProtos.GetTaskAttemptCompletionEventsRequestProto getDefaultInstanceForType()
/*  5129:      */       {
/*  5130: 4819 */         return MRServiceProtos.GetTaskAttemptCompletionEventsRequestProto.getDefaultInstance();
/*  5131:      */       }
/*  5132:      */       
/*  5133:      */       public MRServiceProtos.GetTaskAttemptCompletionEventsRequestProto build()
/*  5134:      */       {
/*  5135: 4823 */         MRServiceProtos.GetTaskAttemptCompletionEventsRequestProto result = buildPartial();
/*  5136: 4824 */         if (!result.isInitialized()) {
/*  5137: 4825 */           throw newUninitializedMessageException(result);
/*  5138:      */         }
/*  5139: 4827 */         return result;
/*  5140:      */       }
/*  5141:      */       
/*  5142:      */       public MRServiceProtos.GetTaskAttemptCompletionEventsRequestProto buildPartial()
/*  5143:      */       {
/*  5144: 4831 */         MRServiceProtos.GetTaskAttemptCompletionEventsRequestProto result = new MRServiceProtos.GetTaskAttemptCompletionEventsRequestProto(this, null);
/*  5145: 4832 */         int from_bitField0_ = this.bitField0_;
/*  5146: 4833 */         int to_bitField0_ = 0;
/*  5147: 4834 */         if ((from_bitField0_ & 0x1) == 1) {
/*  5148: 4835 */           to_bitField0_ |= 0x1;
/*  5149:      */         }
/*  5150: 4837 */         if (this.jobIdBuilder_ == null) {
/*  5151: 4838 */           result.jobId_ = this.jobId_;
/*  5152:      */         } else {
/*  5153: 4840 */           result.jobId_ = ((MRProtos.JobIdProto)this.jobIdBuilder_.build());
/*  5154:      */         }
/*  5155: 4842 */         if ((from_bitField0_ & 0x2) == 2) {
/*  5156: 4843 */           to_bitField0_ |= 0x2;
/*  5157:      */         }
/*  5158: 4845 */         result.fromEventId_ = this.fromEventId_;
/*  5159: 4846 */         if ((from_bitField0_ & 0x4) == 4) {
/*  5160: 4847 */           to_bitField0_ |= 0x4;
/*  5161:      */         }
/*  5162: 4849 */         result.maxEvents_ = this.maxEvents_;
/*  5163: 4850 */         result.bitField0_ = to_bitField0_;
/*  5164: 4851 */         onBuilt();
/*  5165: 4852 */         return result;
/*  5166:      */       }
/*  5167:      */       
/*  5168:      */       public Builder mergeFrom(Message other)
/*  5169:      */       {
/*  5170: 4856 */         if ((other instanceof MRServiceProtos.GetTaskAttemptCompletionEventsRequestProto)) {
/*  5171: 4857 */           return mergeFrom((MRServiceProtos.GetTaskAttemptCompletionEventsRequestProto)other);
/*  5172:      */         }
/*  5173: 4859 */         super.mergeFrom(other);
/*  5174: 4860 */         return this;
/*  5175:      */       }
/*  5176:      */       
/*  5177:      */       public Builder mergeFrom(MRServiceProtos.GetTaskAttemptCompletionEventsRequestProto other)
/*  5178:      */       {
/*  5179: 4865 */         if (other == MRServiceProtos.GetTaskAttemptCompletionEventsRequestProto.getDefaultInstance()) {
/*  5180: 4865 */           return this;
/*  5181:      */         }
/*  5182: 4866 */         if (other.hasJobId()) {
/*  5183: 4867 */           mergeJobId(other.getJobId());
/*  5184:      */         }
/*  5185: 4869 */         if (other.hasFromEventId()) {
/*  5186: 4870 */           setFromEventId(other.getFromEventId());
/*  5187:      */         }
/*  5188: 4872 */         if (other.hasMaxEvents()) {
/*  5189: 4873 */           setMaxEvents(other.getMaxEvents());
/*  5190:      */         }
/*  5191: 4875 */         mergeUnknownFields(other.getUnknownFields());
/*  5192: 4876 */         return this;
/*  5193:      */       }
/*  5194:      */       
/*  5195:      */       public final boolean isInitialized()
/*  5196:      */       {
/*  5197: 4880 */         return true;
/*  5198:      */       }
/*  5199:      */       
/*  5200:      */       public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry)
/*  5201:      */         throws IOException
/*  5202:      */       {
/*  5203: 4887 */         MRServiceProtos.GetTaskAttemptCompletionEventsRequestProto parsedMessage = null;
/*  5204:      */         try
/*  5205:      */         {
/*  5206: 4889 */           parsedMessage = (MRServiceProtos.GetTaskAttemptCompletionEventsRequestProto)MRServiceProtos.GetTaskAttemptCompletionEventsRequestProto.PARSER.parsePartialFrom(input, extensionRegistry);
/*  5207:      */         }
/*  5208:      */         catch (InvalidProtocolBufferException e)
/*  5209:      */         {
/*  5210: 4891 */           parsedMessage = (MRServiceProtos.GetTaskAttemptCompletionEventsRequestProto)e.getUnfinishedMessage();
/*  5211: 4892 */           throw e;
/*  5212:      */         }
/*  5213:      */         finally
/*  5214:      */         {
/*  5215: 4894 */           if (parsedMessage != null) {
/*  5216: 4895 */             mergeFrom(parsedMessage);
/*  5217:      */           }
/*  5218:      */         }
/*  5219: 4898 */         return this;
/*  5220:      */       }
/*  5221:      */       
/*  5222: 4903 */       private MRProtos.JobIdProto jobId_ = MRProtos.JobIdProto.getDefaultInstance();
/*  5223:      */       private SingleFieldBuilder<MRProtos.JobIdProto, MRProtos.JobIdProto.Builder, MRProtos.JobIdProtoOrBuilder> jobIdBuilder_;
/*  5224:      */       private int fromEventId_;
/*  5225:      */       private int maxEvents_;
/*  5226:      */       
/*  5227:      */       public boolean hasJobId()
/*  5228:      */       {
/*  5229: 4910 */         return (this.bitField0_ & 0x1) == 1;
/*  5230:      */       }
/*  5231:      */       
/*  5232:      */       public MRProtos.JobIdProto getJobId()
/*  5233:      */       {
/*  5234: 4916 */         if (this.jobIdBuilder_ == null) {
/*  5235: 4917 */           return this.jobId_;
/*  5236:      */         }
/*  5237: 4919 */         return (MRProtos.JobIdProto)this.jobIdBuilder_.getMessage();
/*  5238:      */       }
/*  5239:      */       
/*  5240:      */       public Builder setJobId(MRProtos.JobIdProto value)
/*  5241:      */       {
/*  5242: 4926 */         if (this.jobIdBuilder_ == null)
/*  5243:      */         {
/*  5244: 4927 */           if (value == null) {
/*  5245: 4928 */             throw new NullPointerException();
/*  5246:      */           }
/*  5247: 4930 */           this.jobId_ = value;
/*  5248: 4931 */           onChanged();
/*  5249:      */         }
/*  5250:      */         else
/*  5251:      */         {
/*  5252: 4933 */           this.jobIdBuilder_.setMessage(value);
/*  5253:      */         }
/*  5254: 4935 */         this.bitField0_ |= 0x1;
/*  5255: 4936 */         return this;
/*  5256:      */       }
/*  5257:      */       
/*  5258:      */       public Builder setJobId(MRProtos.JobIdProto.Builder builderForValue)
/*  5259:      */       {
/*  5260: 4943 */         if (this.jobIdBuilder_ == null)
/*  5261:      */         {
/*  5262: 4944 */           this.jobId_ = builderForValue.build();
/*  5263: 4945 */           onChanged();
/*  5264:      */         }
/*  5265:      */         else
/*  5266:      */         {
/*  5267: 4947 */           this.jobIdBuilder_.setMessage(builderForValue.build());
/*  5268:      */         }
/*  5269: 4949 */         this.bitField0_ |= 0x1;
/*  5270: 4950 */         return this;
/*  5271:      */       }
/*  5272:      */       
/*  5273:      */       public Builder mergeJobId(MRProtos.JobIdProto value)
/*  5274:      */       {
/*  5275: 4956 */         if (this.jobIdBuilder_ == null)
/*  5276:      */         {
/*  5277: 4957 */           if (((this.bitField0_ & 0x1) == 1) && (this.jobId_ != MRProtos.JobIdProto.getDefaultInstance())) {
/*  5278: 4959 */             this.jobId_ = MRProtos.JobIdProto.newBuilder(this.jobId_).mergeFrom(value).buildPartial();
/*  5279:      */           } else {
/*  5280: 4962 */             this.jobId_ = value;
/*  5281:      */           }
/*  5282: 4964 */           onChanged();
/*  5283:      */         }
/*  5284:      */         else
/*  5285:      */         {
/*  5286: 4966 */           this.jobIdBuilder_.mergeFrom(value);
/*  5287:      */         }
/*  5288: 4968 */         this.bitField0_ |= 0x1;
/*  5289: 4969 */         return this;
/*  5290:      */       }
/*  5291:      */       
/*  5292:      */       public Builder clearJobId()
/*  5293:      */       {
/*  5294: 4975 */         if (this.jobIdBuilder_ == null)
/*  5295:      */         {
/*  5296: 4976 */           this.jobId_ = MRProtos.JobIdProto.getDefaultInstance();
/*  5297: 4977 */           onChanged();
/*  5298:      */         }
/*  5299:      */         else
/*  5300:      */         {
/*  5301: 4979 */           this.jobIdBuilder_.clear();
/*  5302:      */         }
/*  5303: 4981 */         this.bitField0_ &= 0xFFFFFFFE;
/*  5304: 4982 */         return this;
/*  5305:      */       }
/*  5306:      */       
/*  5307:      */       public MRProtos.JobIdProto.Builder getJobIdBuilder()
/*  5308:      */       {
/*  5309: 4988 */         this.bitField0_ |= 0x1;
/*  5310: 4989 */         onChanged();
/*  5311: 4990 */         return (MRProtos.JobIdProto.Builder)getJobIdFieldBuilder().getBuilder();
/*  5312:      */       }
/*  5313:      */       
/*  5314:      */       public MRProtos.JobIdProtoOrBuilder getJobIdOrBuilder()
/*  5315:      */       {
/*  5316: 4996 */         if (this.jobIdBuilder_ != null) {
/*  5317: 4997 */           return (MRProtos.JobIdProtoOrBuilder)this.jobIdBuilder_.getMessageOrBuilder();
/*  5318:      */         }
/*  5319: 4999 */         return this.jobId_;
/*  5320:      */       }
/*  5321:      */       
/*  5322:      */       private SingleFieldBuilder<MRProtos.JobIdProto, MRProtos.JobIdProto.Builder, MRProtos.JobIdProtoOrBuilder> getJobIdFieldBuilder()
/*  5323:      */       {
/*  5324: 5008 */         if (this.jobIdBuilder_ == null)
/*  5325:      */         {
/*  5326: 5009 */           this.jobIdBuilder_ = new SingleFieldBuilder(this.jobId_, getParentForChildren(), isClean());
/*  5327:      */           
/*  5328:      */ 
/*  5329:      */ 
/*  5330:      */ 
/*  5331: 5014 */           this.jobId_ = null;
/*  5332:      */         }
/*  5333: 5016 */         return this.jobIdBuilder_;
/*  5334:      */       }
/*  5335:      */       
/*  5336:      */       public boolean hasFromEventId()
/*  5337:      */       {
/*  5338: 5025 */         return (this.bitField0_ & 0x2) == 2;
/*  5339:      */       }
/*  5340:      */       
/*  5341:      */       public int getFromEventId()
/*  5342:      */       {
/*  5343: 5031 */         return this.fromEventId_;
/*  5344:      */       }
/*  5345:      */       
/*  5346:      */       public Builder setFromEventId(int value)
/*  5347:      */       {
/*  5348: 5037 */         this.bitField0_ |= 0x2;
/*  5349: 5038 */         this.fromEventId_ = value;
/*  5350: 5039 */         onChanged();
/*  5351: 5040 */         return this;
/*  5352:      */       }
/*  5353:      */       
/*  5354:      */       public Builder clearFromEventId()
/*  5355:      */       {
/*  5356: 5046 */         this.bitField0_ &= 0xFFFFFFFD;
/*  5357: 5047 */         this.fromEventId_ = 0;
/*  5358: 5048 */         onChanged();
/*  5359: 5049 */         return this;
/*  5360:      */       }
/*  5361:      */       
/*  5362:      */       public boolean hasMaxEvents()
/*  5363:      */       {
/*  5364: 5058 */         return (this.bitField0_ & 0x4) == 4;
/*  5365:      */       }
/*  5366:      */       
/*  5367:      */       public int getMaxEvents()
/*  5368:      */       {
/*  5369: 5064 */         return this.maxEvents_;
/*  5370:      */       }
/*  5371:      */       
/*  5372:      */       public Builder setMaxEvents(int value)
/*  5373:      */       {
/*  5374: 5070 */         this.bitField0_ |= 0x4;
/*  5375: 5071 */         this.maxEvents_ = value;
/*  5376: 5072 */         onChanged();
/*  5377: 5073 */         return this;
/*  5378:      */       }
/*  5379:      */       
/*  5380:      */       public Builder clearMaxEvents()
/*  5381:      */       {
/*  5382: 5079 */         this.bitField0_ &= 0xFFFFFFFB;
/*  5383: 5080 */         this.maxEvents_ = 0;
/*  5384: 5081 */         onChanged();
/*  5385: 5082 */         return this;
/*  5386:      */       }
/*  5387:      */     }
/*  5388:      */     
/*  5389:      */     static
/*  5390:      */     {
/*  5391: 5089 */       defaultInstance = new GetTaskAttemptCompletionEventsRequestProto(true);
/*  5392: 5090 */       defaultInstance.initFields();
/*  5393:      */     }
/*  5394:      */   }
/*  5395:      */   
/*  5396:      */   public static abstract interface GetTaskAttemptCompletionEventsResponseProtoOrBuilder
/*  5397:      */     extends MessageOrBuilder
/*  5398:      */   {
/*  5399:      */     public abstract List<MRProtos.TaskAttemptCompletionEventProto> getCompletionEventsList();
/*  5400:      */     
/*  5401:      */     public abstract MRProtos.TaskAttemptCompletionEventProto getCompletionEvents(int paramInt);
/*  5402:      */     
/*  5403:      */     public abstract int getCompletionEventsCount();
/*  5404:      */     
/*  5405:      */     public abstract List<? extends MRProtos.TaskAttemptCompletionEventProtoOrBuilder> getCompletionEventsOrBuilderList();
/*  5406:      */     
/*  5407:      */     public abstract MRProtos.TaskAttemptCompletionEventProtoOrBuilder getCompletionEventsOrBuilder(int paramInt);
/*  5408:      */   }
/*  5409:      */   
/*  5410:      */   public static final class GetTaskAttemptCompletionEventsResponseProto
/*  5411:      */     extends GeneratedMessage
/*  5412:      */     implements MRServiceProtos.GetTaskAttemptCompletionEventsResponseProtoOrBuilder
/*  5413:      */   {
/*  5414:      */     private static final GetTaskAttemptCompletionEventsResponseProto defaultInstance;
/*  5415:      */     private final UnknownFieldSet unknownFields;
/*  5416:      */     
/*  5417:      */     private GetTaskAttemptCompletionEventsResponseProto(GeneratedMessage.Builder<?> builder)
/*  5418:      */     {
/*  5419: 5132 */       super();
/*  5420: 5133 */       this.unknownFields = builder.getUnknownFields();
/*  5421:      */     }
/*  5422:      */     
/*  5423:      */     private GetTaskAttemptCompletionEventsResponseProto(boolean noInit)
/*  5424:      */     {
/*  5425: 5135 */       this.unknownFields = UnknownFieldSet.getDefaultInstance();
/*  5426:      */     }
/*  5427:      */     
/*  5428:      */     public static GetTaskAttemptCompletionEventsResponseProto getDefaultInstance()
/*  5429:      */     {
/*  5430: 5139 */       return defaultInstance;
/*  5431:      */     }
/*  5432:      */     
/*  5433:      */     public GetTaskAttemptCompletionEventsResponseProto getDefaultInstanceForType()
/*  5434:      */     {
/*  5435: 5143 */       return defaultInstance;
/*  5436:      */     }
/*  5437:      */     
/*  5438:      */     public final UnknownFieldSet getUnknownFields()
/*  5439:      */     {
/*  5440: 5150 */       return this.unknownFields;
/*  5441:      */     }
/*  5442:      */     
/*  5443:      */     private GetTaskAttemptCompletionEventsResponseProto(CodedInputStream input, ExtensionRegistryLite extensionRegistry)
/*  5444:      */       throws InvalidProtocolBufferException
/*  5445:      */     {
/*  5446: 5156 */       initFields();
/*  5447: 5157 */       int mutable_bitField0_ = 0;
/*  5448: 5158 */       UnknownFieldSet.Builder unknownFields = UnknownFieldSet.newBuilder();
/*  5449:      */       try
/*  5450:      */       {
/*  5451: 5161 */         boolean done = false;
/*  5452: 5162 */         while (!done)
/*  5453:      */         {
/*  5454: 5163 */           int tag = input.readTag();
/*  5455: 5164 */           switch (tag)
/*  5456:      */           {
/*  5457:      */           case 0: 
/*  5458: 5166 */             done = true;
/*  5459: 5167 */             break;
/*  5460:      */           default: 
/*  5461: 5169 */             if (!parseUnknownField(input, unknownFields, extensionRegistry, tag)) {
/*  5462: 5171 */               done = true;
/*  5463:      */             }
/*  5464:      */             break;
/*  5465:      */           case 10: 
/*  5466: 5176 */             if ((mutable_bitField0_ & 0x1) != 1)
/*  5467:      */             {
/*  5468: 5177 */               this.completionEvents_ = new ArrayList();
/*  5469: 5178 */               mutable_bitField0_ |= 0x1;
/*  5470:      */             }
/*  5471: 5180 */             this.completionEvents_.add(input.readMessage(MRProtos.TaskAttemptCompletionEventProto.PARSER, extensionRegistry));
/*  5472:      */           }
/*  5473:      */         }
/*  5474:      */       }
/*  5475:      */       catch (InvalidProtocolBufferException e)
/*  5476:      */       {
/*  5477: 5186 */         throw e.setUnfinishedMessage(this);
/*  5478:      */       }
/*  5479:      */       catch (IOException e)
/*  5480:      */       {
/*  5481: 5188 */         throw new InvalidProtocolBufferException(e.getMessage()).setUnfinishedMessage(this);
/*  5482:      */       }
/*  5483:      */       finally
/*  5484:      */       {
/*  5485: 5191 */         if ((mutable_bitField0_ & 0x1) == 1) {
/*  5486: 5192 */           this.completionEvents_ = Collections.unmodifiableList(this.completionEvents_);
/*  5487:      */         }
/*  5488: 5194 */         this.unknownFields = unknownFields.build();
/*  5489: 5195 */         makeExtensionsImmutable();
/*  5490:      */       }
/*  5491:      */     }
/*  5492:      */     
/*  5493:      */     public static final Descriptors.Descriptor getDescriptor()
/*  5494:      */     {
/*  5495: 5200 */       return MRServiceProtos.internal_static_hadoop_mapreduce_GetTaskAttemptCompletionEventsResponseProto_descriptor;
/*  5496:      */     }
/*  5497:      */     
/*  5498:      */     protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable()
/*  5499:      */     {
/*  5500: 5205 */       return MRServiceProtos.internal_static_hadoop_mapreduce_GetTaskAttemptCompletionEventsResponseProto_fieldAccessorTable.ensureFieldAccessorsInitialized(GetTaskAttemptCompletionEventsResponseProto.class, Builder.class);
/*  5501:      */     }
/*  5502:      */     
/*  5503: 5210 */     public static Parser<GetTaskAttemptCompletionEventsResponseProto> PARSER = new AbstractParser()
/*  5504:      */     {
/*  5505:      */       public MRServiceProtos.GetTaskAttemptCompletionEventsResponseProto parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry)
/*  5506:      */         throws InvalidProtocolBufferException
/*  5507:      */       {
/*  5508: 5216 */         return new MRServiceProtos.GetTaskAttemptCompletionEventsResponseProto(input, extensionRegistry, null);
/*  5509:      */       }
/*  5510:      */     };
/*  5511:      */     public static final int COMPLETION_EVENTS_FIELD_NUMBER = 1;
/*  5512:      */     private List<MRProtos.TaskAttemptCompletionEventProto> completionEvents_;
/*  5513:      */     
/*  5514:      */     public Parser<GetTaskAttemptCompletionEventsResponseProto> getParserForType()
/*  5515:      */     {
/*  5516: 5222 */       return PARSER;
/*  5517:      */     }
/*  5518:      */     
/*  5519:      */     public List<MRProtos.TaskAttemptCompletionEventProto> getCompletionEventsList()
/*  5520:      */     {
/*  5521: 5232 */       return this.completionEvents_;
/*  5522:      */     }
/*  5523:      */     
/*  5524:      */     public List<? extends MRProtos.TaskAttemptCompletionEventProtoOrBuilder> getCompletionEventsOrBuilderList()
/*  5525:      */     {
/*  5526: 5239 */       return this.completionEvents_;
/*  5527:      */     }
/*  5528:      */     
/*  5529:      */     public int getCompletionEventsCount()
/*  5530:      */     {
/*  5531: 5245 */       return this.completionEvents_.size();
/*  5532:      */     }
/*  5533:      */     
/*  5534:      */     public MRProtos.TaskAttemptCompletionEventProto getCompletionEvents(int index)
/*  5535:      */     {
/*  5536: 5251 */       return (MRProtos.TaskAttemptCompletionEventProto)this.completionEvents_.get(index);
/*  5537:      */     }
/*  5538:      */     
/*  5539:      */     public MRProtos.TaskAttemptCompletionEventProtoOrBuilder getCompletionEventsOrBuilder(int index)
/*  5540:      */     {
/*  5541: 5258 */       return (MRProtos.TaskAttemptCompletionEventProtoOrBuilder)this.completionEvents_.get(index);
/*  5542:      */     }
/*  5543:      */     
/*  5544:      */     private void initFields()
/*  5545:      */     {
/*  5546: 5262 */       this.completionEvents_ = Collections.emptyList();
/*  5547:      */     }
/*  5548:      */     
/*  5549: 5264 */     private byte memoizedIsInitialized = -1;
/*  5550:      */     
/*  5551:      */     public final boolean isInitialized()
/*  5552:      */     {
/*  5553: 5266 */       byte isInitialized = this.memoizedIsInitialized;
/*  5554: 5267 */       if (isInitialized != -1) {
/*  5555: 5267 */         return isInitialized == 1;
/*  5556:      */       }
/*  5557: 5269 */       this.memoizedIsInitialized = 1;
/*  5558: 5270 */       return true;
/*  5559:      */     }
/*  5560:      */     
/*  5561:      */     public void writeTo(CodedOutputStream output)
/*  5562:      */       throws IOException
/*  5563:      */     {
/*  5564: 5275 */       getSerializedSize();
/*  5565: 5276 */       for (int i = 0; i < this.completionEvents_.size(); i++) {
/*  5566: 5277 */         output.writeMessage(1, (MessageLite)this.completionEvents_.get(i));
/*  5567:      */       }
/*  5568: 5279 */       getUnknownFields().writeTo(output);
/*  5569:      */     }
/*  5570:      */     
/*  5571: 5282 */     private int memoizedSerializedSize = -1;
/*  5572:      */     private static final long serialVersionUID = 0L;
/*  5573:      */     
/*  5574:      */     public int getSerializedSize()
/*  5575:      */     {
/*  5576: 5284 */       int size = this.memoizedSerializedSize;
/*  5577: 5285 */       if (size != -1) {
/*  5578: 5285 */         return size;
/*  5579:      */       }
/*  5580: 5287 */       size = 0;
/*  5581: 5288 */       for (int i = 0; i < this.completionEvents_.size(); i++) {
/*  5582: 5289 */         size += CodedOutputStream.computeMessageSize(1, (MessageLite)this.completionEvents_.get(i));
/*  5583:      */       }
/*  5584: 5292 */       size += getUnknownFields().getSerializedSize();
/*  5585: 5293 */       this.memoizedSerializedSize = size;
/*  5586: 5294 */       return size;
/*  5587:      */     }
/*  5588:      */     
/*  5589:      */     protected Object writeReplace()
/*  5590:      */       throws ObjectStreamException
/*  5591:      */     {
/*  5592: 5301 */       return super.writeReplace();
/*  5593:      */     }
/*  5594:      */     
/*  5595:      */     public boolean equals(Object obj)
/*  5596:      */     {
/*  5597: 5306 */       if (obj == this) {
/*  5598: 5307 */         return true;
/*  5599:      */       }
/*  5600: 5309 */       if (!(obj instanceof GetTaskAttemptCompletionEventsResponseProto)) {
/*  5601: 5310 */         return super.equals(obj);
/*  5602:      */       }
/*  5603: 5312 */       GetTaskAttemptCompletionEventsResponseProto other = (GetTaskAttemptCompletionEventsResponseProto)obj;
/*  5604:      */       
/*  5605: 5314 */       boolean result = true;
/*  5606: 5315 */       result = (result) && (getCompletionEventsList().equals(other.getCompletionEventsList()));
/*  5607:      */       
/*  5608: 5317 */       result = (result) && (getUnknownFields().equals(other.getUnknownFields()));
/*  5609:      */       
/*  5610: 5319 */       return result;
/*  5611:      */     }
/*  5612:      */     
/*  5613: 5322 */     private int memoizedHashCode = 0;
/*  5614:      */     
/*  5615:      */     public int hashCode()
/*  5616:      */     {
/*  5617: 5325 */       if (this.memoizedHashCode != 0) {
/*  5618: 5326 */         return this.memoizedHashCode;
/*  5619:      */       }
/*  5620: 5328 */       int hash = 41;
/*  5621: 5329 */       hash = 19 * hash + getDescriptorForType().hashCode();
/*  5622: 5330 */       if (getCompletionEventsCount() > 0)
/*  5623:      */       {
/*  5624: 5331 */         hash = 37 * hash + 1;
/*  5625: 5332 */         hash = 53 * hash + getCompletionEventsList().hashCode();
/*  5626:      */       }
/*  5627: 5334 */       hash = 29 * hash + getUnknownFields().hashCode();
/*  5628: 5335 */       this.memoizedHashCode = hash;
/*  5629: 5336 */       return hash;
/*  5630:      */     }
/*  5631:      */     
/*  5632:      */     public static GetTaskAttemptCompletionEventsResponseProto parseFrom(ByteString data)
/*  5633:      */       throws InvalidProtocolBufferException
/*  5634:      */     {
/*  5635: 5342 */       return (GetTaskAttemptCompletionEventsResponseProto)PARSER.parseFrom(data);
/*  5636:      */     }
/*  5637:      */     
/*  5638:      */     public static GetTaskAttemptCompletionEventsResponseProto parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry)
/*  5639:      */       throws InvalidProtocolBufferException
/*  5640:      */     {
/*  5641: 5348 */       return (GetTaskAttemptCompletionEventsResponseProto)PARSER.parseFrom(data, extensionRegistry);
/*  5642:      */     }
/*  5643:      */     
/*  5644:      */     public static GetTaskAttemptCompletionEventsResponseProto parseFrom(byte[] data)
/*  5645:      */       throws InvalidProtocolBufferException
/*  5646:      */     {
/*  5647: 5352 */       return (GetTaskAttemptCompletionEventsResponseProto)PARSER.parseFrom(data);
/*  5648:      */     }
/*  5649:      */     
/*  5650:      */     public static GetTaskAttemptCompletionEventsResponseProto parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry)
/*  5651:      */       throws InvalidProtocolBufferException
/*  5652:      */     {
/*  5653: 5358 */       return (GetTaskAttemptCompletionEventsResponseProto)PARSER.parseFrom(data, extensionRegistry);
/*  5654:      */     }
/*  5655:      */     
/*  5656:      */     public static GetTaskAttemptCompletionEventsResponseProto parseFrom(InputStream input)
/*  5657:      */       throws IOException
/*  5658:      */     {
/*  5659: 5362 */       return (GetTaskAttemptCompletionEventsResponseProto)PARSER.parseFrom(input);
/*  5660:      */     }
/*  5661:      */     
/*  5662:      */     public static GetTaskAttemptCompletionEventsResponseProto parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry)
/*  5663:      */       throws IOException
/*  5664:      */     {
/*  5665: 5368 */       return (GetTaskAttemptCompletionEventsResponseProto)PARSER.parseFrom(input, extensionRegistry);
/*  5666:      */     }
/*  5667:      */     
/*  5668:      */     public static GetTaskAttemptCompletionEventsResponseProto parseDelimitedFrom(InputStream input)
/*  5669:      */       throws IOException
/*  5670:      */     {
/*  5671: 5372 */       return (GetTaskAttemptCompletionEventsResponseProto)PARSER.parseDelimitedFrom(input);
/*  5672:      */     }
/*  5673:      */     
/*  5674:      */     public static GetTaskAttemptCompletionEventsResponseProto parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry)
/*  5675:      */       throws IOException
/*  5676:      */     {
/*  5677: 5378 */       return (GetTaskAttemptCompletionEventsResponseProto)PARSER.parseDelimitedFrom(input, extensionRegistry);
/*  5678:      */     }
/*  5679:      */     
/*  5680:      */     public static GetTaskAttemptCompletionEventsResponseProto parseFrom(CodedInputStream input)
/*  5681:      */       throws IOException
/*  5682:      */     {
/*  5683: 5383 */       return (GetTaskAttemptCompletionEventsResponseProto)PARSER.parseFrom(input);
/*  5684:      */     }
/*  5685:      */     
/*  5686:      */     public static GetTaskAttemptCompletionEventsResponseProto parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry)
/*  5687:      */       throws IOException
/*  5688:      */     {
/*  5689: 5389 */       return (GetTaskAttemptCompletionEventsResponseProto)PARSER.parseFrom(input, extensionRegistry);
/*  5690:      */     }
/*  5691:      */     
/*  5692:      */     public static Builder newBuilder()
/*  5693:      */     {
/*  5694: 5392 */       return Builder.access$8600();
/*  5695:      */     }
/*  5696:      */     
/*  5697:      */     public Builder newBuilderForType()
/*  5698:      */     {
/*  5699: 5393 */       return newBuilder();
/*  5700:      */     }
/*  5701:      */     
/*  5702:      */     public static Builder newBuilder(GetTaskAttemptCompletionEventsResponseProto prototype)
/*  5703:      */     {
/*  5704: 5395 */       return newBuilder().mergeFrom(prototype);
/*  5705:      */     }
/*  5706:      */     
/*  5707:      */     public Builder toBuilder()
/*  5708:      */     {
/*  5709: 5397 */       return newBuilder(this);
/*  5710:      */     }
/*  5711:      */     
/*  5712:      */     protected Builder newBuilderForType(GeneratedMessage.BuilderParent parent)
/*  5713:      */     {
/*  5714: 5402 */       Builder builder = new Builder(parent, null);
/*  5715: 5403 */       return builder;
/*  5716:      */     }
/*  5717:      */     
/*  5718:      */     public static final class Builder
/*  5719:      */       extends GeneratedMessage.Builder<Builder>
/*  5720:      */       implements MRServiceProtos.GetTaskAttemptCompletionEventsResponseProtoOrBuilder
/*  5721:      */     {
/*  5722:      */       private int bitField0_;
/*  5723:      */       
/*  5724:      */       public static final Descriptors.Descriptor getDescriptor()
/*  5725:      */       {
/*  5726: 5413 */         return MRServiceProtos.internal_static_hadoop_mapreduce_GetTaskAttemptCompletionEventsResponseProto_descriptor;
/*  5727:      */       }
/*  5728:      */       
/*  5729:      */       protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable()
/*  5730:      */       {
/*  5731: 5418 */         return MRServiceProtos.internal_static_hadoop_mapreduce_GetTaskAttemptCompletionEventsResponseProto_fieldAccessorTable.ensureFieldAccessorsInitialized(MRServiceProtos.GetTaskAttemptCompletionEventsResponseProto.class, Builder.class);
/*  5732:      */       }
/*  5733:      */       
/*  5734:      */       private Builder()
/*  5735:      */       {
/*  5736: 5425 */         maybeForceBuilderInitialization();
/*  5737:      */       }
/*  5738:      */       
/*  5739:      */       private Builder(GeneratedMessage.BuilderParent parent)
/*  5740:      */       {
/*  5741: 5430 */         super();
/*  5742: 5431 */         maybeForceBuilderInitialization();
/*  5743:      */       }
/*  5744:      */       
/*  5745:      */       private void maybeForceBuilderInitialization()
/*  5746:      */       {
/*  5747: 5434 */         if (MRServiceProtos.GetTaskAttemptCompletionEventsResponseProto.alwaysUseFieldBuilders) {
/*  5748: 5435 */           getCompletionEventsFieldBuilder();
/*  5749:      */         }
/*  5750:      */       }
/*  5751:      */       
/*  5752:      */       private static Builder create()
/*  5753:      */       {
/*  5754: 5439 */         return new Builder();
/*  5755:      */       }
/*  5756:      */       
/*  5757:      */       public Builder clear()
/*  5758:      */       {
/*  5759: 5443 */         super.clear();
/*  5760: 5444 */         if (this.completionEventsBuilder_ == null)
/*  5761:      */         {
/*  5762: 5445 */           this.completionEvents_ = Collections.emptyList();
/*  5763: 5446 */           this.bitField0_ &= 0xFFFFFFFE;
/*  5764:      */         }
/*  5765:      */         else
/*  5766:      */         {
/*  5767: 5448 */           this.completionEventsBuilder_.clear();
/*  5768:      */         }
/*  5769: 5450 */         return this;
/*  5770:      */       }
/*  5771:      */       
/*  5772:      */       public Builder clone()
/*  5773:      */       {
/*  5774: 5454 */         return create().mergeFrom(buildPartial());
/*  5775:      */       }
/*  5776:      */       
/*  5777:      */       public Descriptors.Descriptor getDescriptorForType()
/*  5778:      */       {
/*  5779: 5459 */         return MRServiceProtos.internal_static_hadoop_mapreduce_GetTaskAttemptCompletionEventsResponseProto_descriptor;
/*  5780:      */       }
/*  5781:      */       
/*  5782:      */       public MRServiceProtos.GetTaskAttemptCompletionEventsResponseProto getDefaultInstanceForType()
/*  5783:      */       {
/*  5784: 5463 */         return MRServiceProtos.GetTaskAttemptCompletionEventsResponseProto.getDefaultInstance();
/*  5785:      */       }
/*  5786:      */       
/*  5787:      */       public MRServiceProtos.GetTaskAttemptCompletionEventsResponseProto build()
/*  5788:      */       {
/*  5789: 5467 */         MRServiceProtos.GetTaskAttemptCompletionEventsResponseProto result = buildPartial();
/*  5790: 5468 */         if (!result.isInitialized()) {
/*  5791: 5469 */           throw newUninitializedMessageException(result);
/*  5792:      */         }
/*  5793: 5471 */         return result;
/*  5794:      */       }
/*  5795:      */       
/*  5796:      */       public MRServiceProtos.GetTaskAttemptCompletionEventsResponseProto buildPartial()
/*  5797:      */       {
/*  5798: 5475 */         MRServiceProtos.GetTaskAttemptCompletionEventsResponseProto result = new MRServiceProtos.GetTaskAttemptCompletionEventsResponseProto(this, null);
/*  5799: 5476 */         int from_bitField0_ = this.bitField0_;
/*  5800: 5477 */         if (this.completionEventsBuilder_ == null)
/*  5801:      */         {
/*  5802: 5478 */           if ((this.bitField0_ & 0x1) == 1)
/*  5803:      */           {
/*  5804: 5479 */             this.completionEvents_ = Collections.unmodifiableList(this.completionEvents_);
/*  5805: 5480 */             this.bitField0_ &= 0xFFFFFFFE;
/*  5806:      */           }
/*  5807: 5482 */           result.completionEvents_ = this.completionEvents_;
/*  5808:      */         }
/*  5809:      */         else
/*  5810:      */         {
/*  5811: 5484 */           result.completionEvents_ = this.completionEventsBuilder_.build();
/*  5812:      */         }
/*  5813: 5486 */         onBuilt();
/*  5814: 5487 */         return result;
/*  5815:      */       }
/*  5816:      */       
/*  5817:      */       public Builder mergeFrom(Message other)
/*  5818:      */       {
/*  5819: 5491 */         if ((other instanceof MRServiceProtos.GetTaskAttemptCompletionEventsResponseProto)) {
/*  5820: 5492 */           return mergeFrom((MRServiceProtos.GetTaskAttemptCompletionEventsResponseProto)other);
/*  5821:      */         }
/*  5822: 5494 */         super.mergeFrom(other);
/*  5823: 5495 */         return this;
/*  5824:      */       }
/*  5825:      */       
/*  5826:      */       public Builder mergeFrom(MRServiceProtos.GetTaskAttemptCompletionEventsResponseProto other)
/*  5827:      */       {
/*  5828: 5500 */         if (other == MRServiceProtos.GetTaskAttemptCompletionEventsResponseProto.getDefaultInstance()) {
/*  5829: 5500 */           return this;
/*  5830:      */         }
/*  5831: 5501 */         if (this.completionEventsBuilder_ == null)
/*  5832:      */         {
/*  5833: 5502 */           if (!other.completionEvents_.isEmpty())
/*  5834:      */           {
/*  5835: 5503 */             if (this.completionEvents_.isEmpty())
/*  5836:      */             {
/*  5837: 5504 */               this.completionEvents_ = other.completionEvents_;
/*  5838: 5505 */               this.bitField0_ &= 0xFFFFFFFE;
/*  5839:      */             }
/*  5840:      */             else
/*  5841:      */             {
/*  5842: 5507 */               ensureCompletionEventsIsMutable();
/*  5843: 5508 */               this.completionEvents_.addAll(other.completionEvents_);
/*  5844:      */             }
/*  5845: 5510 */             onChanged();
/*  5846:      */           }
/*  5847:      */         }
/*  5848: 5513 */         else if (!other.completionEvents_.isEmpty()) {
/*  5849: 5514 */           if (this.completionEventsBuilder_.isEmpty())
/*  5850:      */           {
/*  5851: 5515 */             this.completionEventsBuilder_.dispose();
/*  5852: 5516 */             this.completionEventsBuilder_ = null;
/*  5853: 5517 */             this.completionEvents_ = other.completionEvents_;
/*  5854: 5518 */             this.bitField0_ &= 0xFFFFFFFE;
/*  5855: 5519 */             this.completionEventsBuilder_ = (MRServiceProtos.GetTaskAttemptCompletionEventsResponseProto.alwaysUseFieldBuilders ? getCompletionEventsFieldBuilder() : null);
/*  5856:      */           }
/*  5857:      */           else
/*  5858:      */           {
/*  5859: 5523 */             this.completionEventsBuilder_.addAllMessages(other.completionEvents_);
/*  5860:      */           }
/*  5861:      */         }
/*  5862: 5527 */         mergeUnknownFields(other.getUnknownFields());
/*  5863: 5528 */         return this;
/*  5864:      */       }
/*  5865:      */       
/*  5866:      */       public final boolean isInitialized()
/*  5867:      */       {
/*  5868: 5532 */         return true;
/*  5869:      */       }
/*  5870:      */       
/*  5871:      */       public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry)
/*  5872:      */         throws IOException
/*  5873:      */       {
/*  5874: 5539 */         MRServiceProtos.GetTaskAttemptCompletionEventsResponseProto parsedMessage = null;
/*  5875:      */         try
/*  5876:      */         {
/*  5877: 5541 */           parsedMessage = (MRServiceProtos.GetTaskAttemptCompletionEventsResponseProto)MRServiceProtos.GetTaskAttemptCompletionEventsResponseProto.PARSER.parsePartialFrom(input, extensionRegistry);
/*  5878:      */         }
/*  5879:      */         catch (InvalidProtocolBufferException e)
/*  5880:      */         {
/*  5881: 5543 */           parsedMessage = (MRServiceProtos.GetTaskAttemptCompletionEventsResponseProto)e.getUnfinishedMessage();
/*  5882: 5544 */           throw e;
/*  5883:      */         }
/*  5884:      */         finally
/*  5885:      */         {
/*  5886: 5546 */           if (parsedMessage != null) {
/*  5887: 5547 */             mergeFrom(parsedMessage);
/*  5888:      */           }
/*  5889:      */         }
/*  5890: 5550 */         return this;
/*  5891:      */       }
/*  5892:      */       
/*  5893: 5555 */       private List<MRProtos.TaskAttemptCompletionEventProto> completionEvents_ = Collections.emptyList();
/*  5894:      */       private RepeatedFieldBuilder<MRProtos.TaskAttemptCompletionEventProto, MRProtos.TaskAttemptCompletionEventProto.Builder, MRProtos.TaskAttemptCompletionEventProtoOrBuilder> completionEventsBuilder_;
/*  5895:      */       
/*  5896:      */       private void ensureCompletionEventsIsMutable()
/*  5897:      */       {
/*  5898: 5558 */         if ((this.bitField0_ & 0x1) != 1)
/*  5899:      */         {
/*  5900: 5559 */           this.completionEvents_ = new ArrayList(this.completionEvents_);
/*  5901: 5560 */           this.bitField0_ |= 0x1;
/*  5902:      */         }
/*  5903:      */       }
/*  5904:      */       
/*  5905:      */       public List<MRProtos.TaskAttemptCompletionEventProto> getCompletionEventsList()
/*  5906:      */       {
/*  5907: 5571 */         if (this.completionEventsBuilder_ == null) {
/*  5908: 5572 */           return Collections.unmodifiableList(this.completionEvents_);
/*  5909:      */         }
/*  5910: 5574 */         return this.completionEventsBuilder_.getMessageList();
/*  5911:      */       }
/*  5912:      */       
/*  5913:      */       public int getCompletionEventsCount()
/*  5914:      */       {
/*  5915: 5581 */         if (this.completionEventsBuilder_ == null) {
/*  5916: 5582 */           return this.completionEvents_.size();
/*  5917:      */         }
/*  5918: 5584 */         return this.completionEventsBuilder_.getCount();
/*  5919:      */       }
/*  5920:      */       
/*  5921:      */       public MRProtos.TaskAttemptCompletionEventProto getCompletionEvents(int index)
/*  5922:      */       {
/*  5923: 5591 */         if (this.completionEventsBuilder_ == null) {
/*  5924: 5592 */           return (MRProtos.TaskAttemptCompletionEventProto)this.completionEvents_.get(index);
/*  5925:      */         }
/*  5926: 5594 */         return (MRProtos.TaskAttemptCompletionEventProto)this.completionEventsBuilder_.getMessage(index);
/*  5927:      */       }
/*  5928:      */       
/*  5929:      */       public Builder setCompletionEvents(int index, MRProtos.TaskAttemptCompletionEventProto value)
/*  5930:      */       {
/*  5931: 5602 */         if (this.completionEventsBuilder_ == null)
/*  5932:      */         {
/*  5933: 5603 */           if (value == null) {
/*  5934: 5604 */             throw new NullPointerException();
/*  5935:      */           }
/*  5936: 5606 */           ensureCompletionEventsIsMutable();
/*  5937: 5607 */           this.completionEvents_.set(index, value);
/*  5938: 5608 */           onChanged();
/*  5939:      */         }
/*  5940:      */         else
/*  5941:      */         {
/*  5942: 5610 */           this.completionEventsBuilder_.setMessage(index, value);
/*  5943:      */         }
/*  5944: 5612 */         return this;
/*  5945:      */       }
/*  5946:      */       
/*  5947:      */       public Builder setCompletionEvents(int index, MRProtos.TaskAttemptCompletionEventProto.Builder builderForValue)
/*  5948:      */       {
/*  5949: 5619 */         if (this.completionEventsBuilder_ == null)
/*  5950:      */         {
/*  5951: 5620 */           ensureCompletionEventsIsMutable();
/*  5952: 5621 */           this.completionEvents_.set(index, builderForValue.build());
/*  5953: 5622 */           onChanged();
/*  5954:      */         }
/*  5955:      */         else
/*  5956:      */         {
/*  5957: 5624 */           this.completionEventsBuilder_.setMessage(index, builderForValue.build());
/*  5958:      */         }
/*  5959: 5626 */         return this;
/*  5960:      */       }
/*  5961:      */       
/*  5962:      */       public Builder addCompletionEvents(MRProtos.TaskAttemptCompletionEventProto value)
/*  5963:      */       {
/*  5964: 5632 */         if (this.completionEventsBuilder_ == null)
/*  5965:      */         {
/*  5966: 5633 */           if (value == null) {
/*  5967: 5634 */             throw new NullPointerException();
/*  5968:      */           }
/*  5969: 5636 */           ensureCompletionEventsIsMutable();
/*  5970: 5637 */           this.completionEvents_.add(value);
/*  5971: 5638 */           onChanged();
/*  5972:      */         }
/*  5973:      */         else
/*  5974:      */         {
/*  5975: 5640 */           this.completionEventsBuilder_.addMessage(value);
/*  5976:      */         }
/*  5977: 5642 */         return this;
/*  5978:      */       }
/*  5979:      */       
/*  5980:      */       public Builder addCompletionEvents(int index, MRProtos.TaskAttemptCompletionEventProto value)
/*  5981:      */       {
/*  5982: 5649 */         if (this.completionEventsBuilder_ == null)
/*  5983:      */         {
/*  5984: 5650 */           if (value == null) {
/*  5985: 5651 */             throw new NullPointerException();
/*  5986:      */           }
/*  5987: 5653 */           ensureCompletionEventsIsMutable();
/*  5988: 5654 */           this.completionEvents_.add(index, value);
/*  5989: 5655 */           onChanged();
/*  5990:      */         }
/*  5991:      */         else
/*  5992:      */         {
/*  5993: 5657 */           this.completionEventsBuilder_.addMessage(index, value);
/*  5994:      */         }
/*  5995: 5659 */         return this;
/*  5996:      */       }
/*  5997:      */       
/*  5998:      */       public Builder addCompletionEvents(MRProtos.TaskAttemptCompletionEventProto.Builder builderForValue)
/*  5999:      */       {
/*  6000: 5666 */         if (this.completionEventsBuilder_ == null)
/*  6001:      */         {
/*  6002: 5667 */           ensureCompletionEventsIsMutable();
/*  6003: 5668 */           this.completionEvents_.add(builderForValue.build());
/*  6004: 5669 */           onChanged();
/*  6005:      */         }
/*  6006:      */         else
/*  6007:      */         {
/*  6008: 5671 */           this.completionEventsBuilder_.addMessage(builderForValue.build());
/*  6009:      */         }
/*  6010: 5673 */         return this;
/*  6011:      */       }
/*  6012:      */       
/*  6013:      */       public Builder addCompletionEvents(int index, MRProtos.TaskAttemptCompletionEventProto.Builder builderForValue)
/*  6014:      */       {
/*  6015: 5680 */         if (this.completionEventsBuilder_ == null)
/*  6016:      */         {
/*  6017: 5681 */           ensureCompletionEventsIsMutable();
/*  6018: 5682 */           this.completionEvents_.add(index, builderForValue.build());
/*  6019: 5683 */           onChanged();
/*  6020:      */         }
/*  6021:      */         else
/*  6022:      */         {
/*  6023: 5685 */           this.completionEventsBuilder_.addMessage(index, builderForValue.build());
/*  6024:      */         }
/*  6025: 5687 */         return this;
/*  6026:      */       }
/*  6027:      */       
/*  6028:      */       public Builder addAllCompletionEvents(Iterable<? extends MRProtos.TaskAttemptCompletionEventProto> values)
/*  6029:      */       {
/*  6030: 5694 */         if (this.completionEventsBuilder_ == null)
/*  6031:      */         {
/*  6032: 5695 */           ensureCompletionEventsIsMutable();
/*  6033: 5696 */           GeneratedMessage.Builder.addAll(values, this.completionEvents_);
/*  6034: 5697 */           onChanged();
/*  6035:      */         }
/*  6036:      */         else
/*  6037:      */         {
/*  6038: 5699 */           this.completionEventsBuilder_.addAllMessages(values);
/*  6039:      */         }
/*  6040: 5701 */         return this;
/*  6041:      */       }
/*  6042:      */       
/*  6043:      */       public Builder clearCompletionEvents()
/*  6044:      */       {
/*  6045: 5707 */         if (this.completionEventsBuilder_ == null)
/*  6046:      */         {
/*  6047: 5708 */           this.completionEvents_ = Collections.emptyList();
/*  6048: 5709 */           this.bitField0_ &= 0xFFFFFFFE;
/*  6049: 5710 */           onChanged();
/*  6050:      */         }
/*  6051:      */         else
/*  6052:      */         {
/*  6053: 5712 */           this.completionEventsBuilder_.clear();
/*  6054:      */         }
/*  6055: 5714 */         return this;
/*  6056:      */       }
/*  6057:      */       
/*  6058:      */       public Builder removeCompletionEvents(int index)
/*  6059:      */       {
/*  6060: 5720 */         if (this.completionEventsBuilder_ == null)
/*  6061:      */         {
/*  6062: 5721 */           ensureCompletionEventsIsMutable();
/*  6063: 5722 */           this.completionEvents_.remove(index);
/*  6064: 5723 */           onChanged();
/*  6065:      */         }
/*  6066:      */         else
/*  6067:      */         {
/*  6068: 5725 */           this.completionEventsBuilder_.remove(index);
/*  6069:      */         }
/*  6070: 5727 */         return this;
/*  6071:      */       }
/*  6072:      */       
/*  6073:      */       public MRProtos.TaskAttemptCompletionEventProto.Builder getCompletionEventsBuilder(int index)
/*  6074:      */       {
/*  6075: 5734 */         return (MRProtos.TaskAttemptCompletionEventProto.Builder)getCompletionEventsFieldBuilder().getBuilder(index);
/*  6076:      */       }
/*  6077:      */       
/*  6078:      */       public MRProtos.TaskAttemptCompletionEventProtoOrBuilder getCompletionEventsOrBuilder(int index)
/*  6079:      */       {
/*  6080: 5741 */         if (this.completionEventsBuilder_ == null) {
/*  6081: 5742 */           return (MRProtos.TaskAttemptCompletionEventProtoOrBuilder)this.completionEvents_.get(index);
/*  6082:      */         }
/*  6083: 5743 */         return (MRProtos.TaskAttemptCompletionEventProtoOrBuilder)this.completionEventsBuilder_.getMessageOrBuilder(index);
/*  6084:      */       }
/*  6085:      */       
/*  6086:      */       public List<? extends MRProtos.TaskAttemptCompletionEventProtoOrBuilder> getCompletionEventsOrBuilderList()
/*  6087:      */       {
/*  6088: 5751 */         if (this.completionEventsBuilder_ != null) {
/*  6089: 5752 */           return this.completionEventsBuilder_.getMessageOrBuilderList();
/*  6090:      */         }
/*  6091: 5754 */         return Collections.unmodifiableList(this.completionEvents_);
/*  6092:      */       }
/*  6093:      */       
/*  6094:      */       public MRProtos.TaskAttemptCompletionEventProto.Builder addCompletionEventsBuilder()
/*  6095:      */       {
/*  6096: 5761 */         return (MRProtos.TaskAttemptCompletionEventProto.Builder)getCompletionEventsFieldBuilder().addBuilder(MRProtos.TaskAttemptCompletionEventProto.getDefaultInstance());
/*  6097:      */       }
/*  6098:      */       
/*  6099:      */       public MRProtos.TaskAttemptCompletionEventProto.Builder addCompletionEventsBuilder(int index)
/*  6100:      */       {
/*  6101: 5769 */         return (MRProtos.TaskAttemptCompletionEventProto.Builder)getCompletionEventsFieldBuilder().addBuilder(index, MRProtos.TaskAttemptCompletionEventProto.getDefaultInstance());
/*  6102:      */       }
/*  6103:      */       
/*  6104:      */       public List<MRProtos.TaskAttemptCompletionEventProto.Builder> getCompletionEventsBuilderList()
/*  6105:      */       {
/*  6106: 5777 */         return getCompletionEventsFieldBuilder().getBuilderList();
/*  6107:      */       }
/*  6108:      */       
/*  6109:      */       private RepeatedFieldBuilder<MRProtos.TaskAttemptCompletionEventProto, MRProtos.TaskAttemptCompletionEventProto.Builder, MRProtos.TaskAttemptCompletionEventProtoOrBuilder> getCompletionEventsFieldBuilder()
/*  6110:      */       {
/*  6111: 5782 */         if (this.completionEventsBuilder_ == null)
/*  6112:      */         {
/*  6113: 5783 */           this.completionEventsBuilder_ = new RepeatedFieldBuilder(this.completionEvents_, (this.bitField0_ & 0x1) == 1, getParentForChildren(), isClean());
/*  6114:      */           
/*  6115:      */ 
/*  6116:      */ 
/*  6117:      */ 
/*  6118:      */ 
/*  6119: 5789 */           this.completionEvents_ = null;
/*  6120:      */         }
/*  6121: 5791 */         return this.completionEventsBuilder_;
/*  6122:      */       }
/*  6123:      */     }
/*  6124:      */     
/*  6125:      */     static
/*  6126:      */     {
/*  6127: 5798 */       defaultInstance = new GetTaskAttemptCompletionEventsResponseProto(true);
/*  6128: 5799 */       defaultInstance.initFields();
/*  6129:      */     }
/*  6130:      */   }
/*  6131:      */   
/*  6132:      */   public static abstract interface GetTaskReportsRequestProtoOrBuilder
/*  6133:      */     extends MessageOrBuilder
/*  6134:      */   {
/*  6135:      */     public abstract boolean hasJobId();
/*  6136:      */     
/*  6137:      */     public abstract MRProtos.JobIdProto getJobId();
/*  6138:      */     
/*  6139:      */     public abstract MRProtos.JobIdProtoOrBuilder getJobIdOrBuilder();
/*  6140:      */     
/*  6141:      */     public abstract boolean hasTaskType();
/*  6142:      */     
/*  6143:      */     public abstract MRProtos.TaskTypeProto getTaskType();
/*  6144:      */   }
/*  6145:      */   
/*  6146:      */   public static final class GetTaskReportsRequestProto
/*  6147:      */     extends GeneratedMessage
/*  6148:      */     implements MRServiceProtos.GetTaskReportsRequestProtoOrBuilder
/*  6149:      */   {
/*  6150:      */     private static final GetTaskReportsRequestProto defaultInstance;
/*  6151:      */     private final UnknownFieldSet unknownFields;
/*  6152:      */     
/*  6153:      */     private GetTaskReportsRequestProto(GeneratedMessage.Builder<?> builder)
/*  6154:      */     {
/*  6155: 5840 */       super();
/*  6156: 5841 */       this.unknownFields = builder.getUnknownFields();
/*  6157:      */     }
/*  6158:      */     
/*  6159:      */     private GetTaskReportsRequestProto(boolean noInit)
/*  6160:      */     {
/*  6161: 5843 */       this.unknownFields = UnknownFieldSet.getDefaultInstance();
/*  6162:      */     }
/*  6163:      */     
/*  6164:      */     public static GetTaskReportsRequestProto getDefaultInstance()
/*  6165:      */     {
/*  6166: 5847 */       return defaultInstance;
/*  6167:      */     }
/*  6168:      */     
/*  6169:      */     public GetTaskReportsRequestProto getDefaultInstanceForType()
/*  6170:      */     {
/*  6171: 5851 */       return defaultInstance;
/*  6172:      */     }
/*  6173:      */     
/*  6174:      */     public final UnknownFieldSet getUnknownFields()
/*  6175:      */     {
/*  6176: 5858 */       return this.unknownFields;
/*  6177:      */     }
/*  6178:      */     
/*  6179:      */     private GetTaskReportsRequestProto(CodedInputStream input, ExtensionRegistryLite extensionRegistry)
/*  6180:      */       throws InvalidProtocolBufferException
/*  6181:      */     {
/*  6182: 5864 */       initFields();
/*  6183: 5865 */       int mutable_bitField0_ = 0;
/*  6184: 5866 */       UnknownFieldSet.Builder unknownFields = UnknownFieldSet.newBuilder();
/*  6185:      */       try
/*  6186:      */       {
/*  6187: 5869 */         boolean done = false;
/*  6188: 5870 */         while (!done)
/*  6189:      */         {
/*  6190: 5871 */           int tag = input.readTag();
/*  6191: 5872 */           switch (tag)
/*  6192:      */           {
/*  6193:      */           case 0: 
/*  6194: 5874 */             done = true;
/*  6195: 5875 */             break;
/*  6196:      */           default: 
/*  6197: 5877 */             if (!parseUnknownField(input, unknownFields, extensionRegistry, tag)) {
/*  6198: 5879 */               done = true;
/*  6199:      */             }
/*  6200:      */             break;
/*  6201:      */           case 10: 
/*  6202: 5884 */             MRProtos.JobIdProto.Builder subBuilder = null;
/*  6203: 5885 */             if ((this.bitField0_ & 0x1) == 1) {
/*  6204: 5886 */               subBuilder = this.jobId_.toBuilder();
/*  6205:      */             }
/*  6206: 5888 */             this.jobId_ = ((MRProtos.JobIdProto)input.readMessage(MRProtos.JobIdProto.PARSER, extensionRegistry));
/*  6207: 5889 */             if (subBuilder != null)
/*  6208:      */             {
/*  6209: 5890 */               subBuilder.mergeFrom(this.jobId_);
/*  6210: 5891 */               this.jobId_ = subBuilder.buildPartial();
/*  6211:      */             }
/*  6212: 5893 */             this.bitField0_ |= 0x1;
/*  6213: 5894 */             break;
/*  6214:      */           case 16: 
/*  6215: 5897 */             int rawValue = input.readEnum();
/*  6216: 5898 */             MRProtos.TaskTypeProto value = MRProtos.TaskTypeProto.valueOf(rawValue);
/*  6217: 5899 */             if (value == null)
/*  6218:      */             {
/*  6219: 5900 */               unknownFields.mergeVarintField(2, rawValue);
/*  6220:      */             }
/*  6221:      */             else
/*  6222:      */             {
/*  6223: 5902 */               this.bitField0_ |= 0x2;
/*  6224: 5903 */               this.taskType_ = value;
/*  6225:      */             }
/*  6226:      */             break;
/*  6227:      */           }
/*  6228:      */         }
/*  6229:      */       }
/*  6230:      */       catch (InvalidProtocolBufferException e)
/*  6231:      */       {
/*  6232: 5910 */         throw e.setUnfinishedMessage(this);
/*  6233:      */       }
/*  6234:      */       catch (IOException e)
/*  6235:      */       {
/*  6236: 5912 */         throw new InvalidProtocolBufferException(e.getMessage()).setUnfinishedMessage(this);
/*  6237:      */       }
/*  6238:      */       finally
/*  6239:      */       {
/*  6240: 5915 */         this.unknownFields = unknownFields.build();
/*  6241: 5916 */         makeExtensionsImmutable();
/*  6242:      */       }
/*  6243:      */     }
/*  6244:      */     
/*  6245:      */     public static final Descriptors.Descriptor getDescriptor()
/*  6246:      */     {
/*  6247: 5921 */       return MRServiceProtos.internal_static_hadoop_mapreduce_GetTaskReportsRequestProto_descriptor;
/*  6248:      */     }
/*  6249:      */     
/*  6250:      */     protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable()
/*  6251:      */     {
/*  6252: 5926 */       return MRServiceProtos.internal_static_hadoop_mapreduce_GetTaskReportsRequestProto_fieldAccessorTable.ensureFieldAccessorsInitialized(GetTaskReportsRequestProto.class, Builder.class);
/*  6253:      */     }
/*  6254:      */     
/*  6255: 5931 */     public static Parser<GetTaskReportsRequestProto> PARSER = new AbstractParser()
/*  6256:      */     {
/*  6257:      */       public MRServiceProtos.GetTaskReportsRequestProto parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry)
/*  6258:      */         throws InvalidProtocolBufferException
/*  6259:      */       {
/*  6260: 5937 */         return new MRServiceProtos.GetTaskReportsRequestProto(input, extensionRegistry, null);
/*  6261:      */       }
/*  6262:      */     };
/*  6263:      */     private int bitField0_;
/*  6264:      */     public static final int JOB_ID_FIELD_NUMBER = 1;
/*  6265:      */     private MRProtos.JobIdProto jobId_;
/*  6266:      */     public static final int TASK_TYPE_FIELD_NUMBER = 2;
/*  6267:      */     private MRProtos.TaskTypeProto taskType_;
/*  6268:      */     
/*  6269:      */     public Parser<GetTaskReportsRequestProto> getParserForType()
/*  6270:      */     {
/*  6271: 5943 */       return PARSER;
/*  6272:      */     }
/*  6273:      */     
/*  6274:      */     public boolean hasJobId()
/*  6275:      */     {
/*  6276: 5954 */       return (this.bitField0_ & 0x1) == 1;
/*  6277:      */     }
/*  6278:      */     
/*  6279:      */     public MRProtos.JobIdProto getJobId()
/*  6280:      */     {
/*  6281: 5960 */       return this.jobId_;
/*  6282:      */     }
/*  6283:      */     
/*  6284:      */     public MRProtos.JobIdProtoOrBuilder getJobIdOrBuilder()
/*  6285:      */     {
/*  6286: 5966 */       return this.jobId_;
/*  6287:      */     }
/*  6288:      */     
/*  6289:      */     public boolean hasTaskType()
/*  6290:      */     {
/*  6291: 5976 */       return (this.bitField0_ & 0x2) == 2;
/*  6292:      */     }
/*  6293:      */     
/*  6294:      */     public MRProtos.TaskTypeProto getTaskType()
/*  6295:      */     {
/*  6296: 5982 */       return this.taskType_;
/*  6297:      */     }
/*  6298:      */     
/*  6299:      */     private void initFields()
/*  6300:      */     {
/*  6301: 5986 */       this.jobId_ = MRProtos.JobIdProto.getDefaultInstance();
/*  6302: 5987 */       this.taskType_ = MRProtos.TaskTypeProto.MAP;
/*  6303:      */     }
/*  6304:      */     
/*  6305: 5989 */     private byte memoizedIsInitialized = -1;
/*  6306:      */     
/*  6307:      */     public final boolean isInitialized()
/*  6308:      */     {
/*  6309: 5991 */       byte isInitialized = this.memoizedIsInitialized;
/*  6310: 5992 */       if (isInitialized != -1) {
/*  6311: 5992 */         return isInitialized == 1;
/*  6312:      */       }
/*  6313: 5994 */       this.memoizedIsInitialized = 1;
/*  6314: 5995 */       return true;
/*  6315:      */     }
/*  6316:      */     
/*  6317:      */     public void writeTo(CodedOutputStream output)
/*  6318:      */       throws IOException
/*  6319:      */     {
/*  6320: 6000 */       getSerializedSize();
/*  6321: 6001 */       if ((this.bitField0_ & 0x1) == 1) {
/*  6322: 6002 */         output.writeMessage(1, this.jobId_);
/*  6323:      */       }
/*  6324: 6004 */       if ((this.bitField0_ & 0x2) == 2) {
/*  6325: 6005 */         output.writeEnum(2, this.taskType_.getNumber());
/*  6326:      */       }
/*  6327: 6007 */       getUnknownFields().writeTo(output);
/*  6328:      */     }
/*  6329:      */     
/*  6330: 6010 */     private int memoizedSerializedSize = -1;
/*  6331:      */     private static final long serialVersionUID = 0L;
/*  6332:      */     
/*  6333:      */     public int getSerializedSize()
/*  6334:      */     {
/*  6335: 6012 */       int size = this.memoizedSerializedSize;
/*  6336: 6013 */       if (size != -1) {
/*  6337: 6013 */         return size;
/*  6338:      */       }
/*  6339: 6015 */       size = 0;
/*  6340: 6016 */       if ((this.bitField0_ & 0x1) == 1) {
/*  6341: 6017 */         size += CodedOutputStream.computeMessageSize(1, this.jobId_);
/*  6342:      */       }
/*  6343: 6020 */       if ((this.bitField0_ & 0x2) == 2) {
/*  6344: 6021 */         size += CodedOutputStream.computeEnumSize(2, this.taskType_.getNumber());
/*  6345:      */       }
/*  6346: 6024 */       size += getUnknownFields().getSerializedSize();
/*  6347: 6025 */       this.memoizedSerializedSize = size;
/*  6348: 6026 */       return size;
/*  6349:      */     }
/*  6350:      */     
/*  6351:      */     protected Object writeReplace()
/*  6352:      */       throws ObjectStreamException
/*  6353:      */     {
/*  6354: 6033 */       return super.writeReplace();
/*  6355:      */     }
/*  6356:      */     
/*  6357:      */     public boolean equals(Object obj)
/*  6358:      */     {
/*  6359: 6038 */       if (obj == this) {
/*  6360: 6039 */         return true;
/*  6361:      */       }
/*  6362: 6041 */       if (!(obj instanceof GetTaskReportsRequestProto)) {
/*  6363: 6042 */         return super.equals(obj);
/*  6364:      */       }
/*  6365: 6044 */       GetTaskReportsRequestProto other = (GetTaskReportsRequestProto)obj;
/*  6366:      */       
/*  6367: 6046 */       boolean result = true;
/*  6368: 6047 */       result = (result) && (hasJobId() == other.hasJobId());
/*  6369: 6048 */       if (hasJobId()) {
/*  6370: 6049 */         result = (result) && (getJobId().equals(other.getJobId()));
/*  6371:      */       }
/*  6372: 6052 */       result = (result) && (hasTaskType() == other.hasTaskType());
/*  6373: 6053 */       if (hasTaskType()) {
/*  6374: 6054 */         result = (result) && (getTaskType() == other.getTaskType());
/*  6375:      */       }
/*  6376: 6057 */       result = (result) && (getUnknownFields().equals(other.getUnknownFields()));
/*  6377:      */       
/*  6378: 6059 */       return result;
/*  6379:      */     }
/*  6380:      */     
/*  6381: 6062 */     private int memoizedHashCode = 0;
/*  6382:      */     
/*  6383:      */     public int hashCode()
/*  6384:      */     {
/*  6385: 6065 */       if (this.memoizedHashCode != 0) {
/*  6386: 6066 */         return this.memoizedHashCode;
/*  6387:      */       }
/*  6388: 6068 */       int hash = 41;
/*  6389: 6069 */       hash = 19 * hash + getDescriptorForType().hashCode();
/*  6390: 6070 */       if (hasJobId())
/*  6391:      */       {
/*  6392: 6071 */         hash = 37 * hash + 1;
/*  6393: 6072 */         hash = 53 * hash + getJobId().hashCode();
/*  6394:      */       }
/*  6395: 6074 */       if (hasTaskType())
/*  6396:      */       {
/*  6397: 6075 */         hash = 37 * hash + 2;
/*  6398: 6076 */         hash = 53 * hash + hashEnum(getTaskType());
/*  6399:      */       }
/*  6400: 6078 */       hash = 29 * hash + getUnknownFields().hashCode();
/*  6401: 6079 */       this.memoizedHashCode = hash;
/*  6402: 6080 */       return hash;
/*  6403:      */     }
/*  6404:      */     
/*  6405:      */     public static GetTaskReportsRequestProto parseFrom(ByteString data)
/*  6406:      */       throws InvalidProtocolBufferException
/*  6407:      */     {
/*  6408: 6086 */       return (GetTaskReportsRequestProto)PARSER.parseFrom(data);
/*  6409:      */     }
/*  6410:      */     
/*  6411:      */     public static GetTaskReportsRequestProto parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry)
/*  6412:      */       throws InvalidProtocolBufferException
/*  6413:      */     {
/*  6414: 6092 */       return (GetTaskReportsRequestProto)PARSER.parseFrom(data, extensionRegistry);
/*  6415:      */     }
/*  6416:      */     
/*  6417:      */     public static GetTaskReportsRequestProto parseFrom(byte[] data)
/*  6418:      */       throws InvalidProtocolBufferException
/*  6419:      */     {
/*  6420: 6096 */       return (GetTaskReportsRequestProto)PARSER.parseFrom(data);
/*  6421:      */     }
/*  6422:      */     
/*  6423:      */     public static GetTaskReportsRequestProto parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry)
/*  6424:      */       throws InvalidProtocolBufferException
/*  6425:      */     {
/*  6426: 6102 */       return (GetTaskReportsRequestProto)PARSER.parseFrom(data, extensionRegistry);
/*  6427:      */     }
/*  6428:      */     
/*  6429:      */     public static GetTaskReportsRequestProto parseFrom(InputStream input)
/*  6430:      */       throws IOException
/*  6431:      */     {
/*  6432: 6106 */       return (GetTaskReportsRequestProto)PARSER.parseFrom(input);
/*  6433:      */     }
/*  6434:      */     
/*  6435:      */     public static GetTaskReportsRequestProto parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry)
/*  6436:      */       throws IOException
/*  6437:      */     {
/*  6438: 6112 */       return (GetTaskReportsRequestProto)PARSER.parseFrom(input, extensionRegistry);
/*  6439:      */     }
/*  6440:      */     
/*  6441:      */     public static GetTaskReportsRequestProto parseDelimitedFrom(InputStream input)
/*  6442:      */       throws IOException
/*  6443:      */     {
/*  6444: 6116 */       return (GetTaskReportsRequestProto)PARSER.parseDelimitedFrom(input);
/*  6445:      */     }
/*  6446:      */     
/*  6447:      */     public static GetTaskReportsRequestProto parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry)
/*  6448:      */       throws IOException
/*  6449:      */     {
/*  6450: 6122 */       return (GetTaskReportsRequestProto)PARSER.parseDelimitedFrom(input, extensionRegistry);
/*  6451:      */     }
/*  6452:      */     
/*  6453:      */     public static GetTaskReportsRequestProto parseFrom(CodedInputStream input)
/*  6454:      */       throws IOException
/*  6455:      */     {
/*  6456: 6127 */       return (GetTaskReportsRequestProto)PARSER.parseFrom(input);
/*  6457:      */     }
/*  6458:      */     
/*  6459:      */     public static GetTaskReportsRequestProto parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry)
/*  6460:      */       throws IOException
/*  6461:      */     {
/*  6462: 6133 */       return (GetTaskReportsRequestProto)PARSER.parseFrom(input, extensionRegistry);
/*  6463:      */     }
/*  6464:      */     
/*  6465:      */     public static Builder newBuilder()
/*  6466:      */     {
/*  6467: 6136 */       return Builder.access$9500();
/*  6468:      */     }
/*  6469:      */     
/*  6470:      */     public Builder newBuilderForType()
/*  6471:      */     {
/*  6472: 6137 */       return newBuilder();
/*  6473:      */     }
/*  6474:      */     
/*  6475:      */     public static Builder newBuilder(GetTaskReportsRequestProto prototype)
/*  6476:      */     {
/*  6477: 6139 */       return newBuilder().mergeFrom(prototype);
/*  6478:      */     }
/*  6479:      */     
/*  6480:      */     public Builder toBuilder()
/*  6481:      */     {
/*  6482: 6141 */       return newBuilder(this);
/*  6483:      */     }
/*  6484:      */     
/*  6485:      */     protected Builder newBuilderForType(GeneratedMessage.BuilderParent parent)
/*  6486:      */     {
/*  6487: 6146 */       Builder builder = new Builder(parent, null);
/*  6488: 6147 */       return builder;
/*  6489:      */     }
/*  6490:      */     
/*  6491:      */     public static final class Builder
/*  6492:      */       extends GeneratedMessage.Builder<Builder>
/*  6493:      */       implements MRServiceProtos.GetTaskReportsRequestProtoOrBuilder
/*  6494:      */     {
/*  6495:      */       private int bitField0_;
/*  6496:      */       
/*  6497:      */       public static final Descriptors.Descriptor getDescriptor()
/*  6498:      */       {
/*  6499: 6157 */         return MRServiceProtos.internal_static_hadoop_mapreduce_GetTaskReportsRequestProto_descriptor;
/*  6500:      */       }
/*  6501:      */       
/*  6502:      */       protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable()
/*  6503:      */       {
/*  6504: 6162 */         return MRServiceProtos.internal_static_hadoop_mapreduce_GetTaskReportsRequestProto_fieldAccessorTable.ensureFieldAccessorsInitialized(MRServiceProtos.GetTaskReportsRequestProto.class, Builder.class);
/*  6505:      */       }
/*  6506:      */       
/*  6507:      */       private Builder()
/*  6508:      */       {
/*  6509: 6169 */         maybeForceBuilderInitialization();
/*  6510:      */       }
/*  6511:      */       
/*  6512:      */       private Builder(GeneratedMessage.BuilderParent parent)
/*  6513:      */       {
/*  6514: 6174 */         super();
/*  6515: 6175 */         maybeForceBuilderInitialization();
/*  6516:      */       }
/*  6517:      */       
/*  6518:      */       private void maybeForceBuilderInitialization()
/*  6519:      */       {
/*  6520: 6178 */         if (MRServiceProtos.GetTaskReportsRequestProto.alwaysUseFieldBuilders) {
/*  6521: 6179 */           getJobIdFieldBuilder();
/*  6522:      */         }
/*  6523:      */       }
/*  6524:      */       
/*  6525:      */       private static Builder create()
/*  6526:      */       {
/*  6527: 6183 */         return new Builder();
/*  6528:      */       }
/*  6529:      */       
/*  6530:      */       public Builder clear()
/*  6531:      */       {
/*  6532: 6187 */         super.clear();
/*  6533: 6188 */         if (this.jobIdBuilder_ == null) {
/*  6534: 6189 */           this.jobId_ = MRProtos.JobIdProto.getDefaultInstance();
/*  6535:      */         } else {
/*  6536: 6191 */           this.jobIdBuilder_.clear();
/*  6537:      */         }
/*  6538: 6193 */         this.bitField0_ &= 0xFFFFFFFE;
/*  6539: 6194 */         this.taskType_ = MRProtos.TaskTypeProto.MAP;
/*  6540: 6195 */         this.bitField0_ &= 0xFFFFFFFD;
/*  6541: 6196 */         return this;
/*  6542:      */       }
/*  6543:      */       
/*  6544:      */       public Builder clone()
/*  6545:      */       {
/*  6546: 6200 */         return create().mergeFrom(buildPartial());
/*  6547:      */       }
/*  6548:      */       
/*  6549:      */       public Descriptors.Descriptor getDescriptorForType()
/*  6550:      */       {
/*  6551: 6205 */         return MRServiceProtos.internal_static_hadoop_mapreduce_GetTaskReportsRequestProto_descriptor;
/*  6552:      */       }
/*  6553:      */       
/*  6554:      */       public MRServiceProtos.GetTaskReportsRequestProto getDefaultInstanceForType()
/*  6555:      */       {
/*  6556: 6209 */         return MRServiceProtos.GetTaskReportsRequestProto.getDefaultInstance();
/*  6557:      */       }
/*  6558:      */       
/*  6559:      */       public MRServiceProtos.GetTaskReportsRequestProto build()
/*  6560:      */       {
/*  6561: 6213 */         MRServiceProtos.GetTaskReportsRequestProto result = buildPartial();
/*  6562: 6214 */         if (!result.isInitialized()) {
/*  6563: 6215 */           throw newUninitializedMessageException(result);
/*  6564:      */         }
/*  6565: 6217 */         return result;
/*  6566:      */       }
/*  6567:      */       
/*  6568:      */       public MRServiceProtos.GetTaskReportsRequestProto buildPartial()
/*  6569:      */       {
/*  6570: 6221 */         MRServiceProtos.GetTaskReportsRequestProto result = new MRServiceProtos.GetTaskReportsRequestProto(this, null);
/*  6571: 6222 */         int from_bitField0_ = this.bitField0_;
/*  6572: 6223 */         int to_bitField0_ = 0;
/*  6573: 6224 */         if ((from_bitField0_ & 0x1) == 1) {
/*  6574: 6225 */           to_bitField0_ |= 0x1;
/*  6575:      */         }
/*  6576: 6227 */         if (this.jobIdBuilder_ == null) {
/*  6577: 6228 */           result.jobId_ = this.jobId_;
/*  6578:      */         } else {
/*  6579: 6230 */           result.jobId_ = ((MRProtos.JobIdProto)this.jobIdBuilder_.build());
/*  6580:      */         }
/*  6581: 6232 */         if ((from_bitField0_ & 0x2) == 2) {
/*  6582: 6233 */           to_bitField0_ |= 0x2;
/*  6583:      */         }
/*  6584: 6235 */         result.taskType_ = this.taskType_;
/*  6585: 6236 */         result.bitField0_ = to_bitField0_;
/*  6586: 6237 */         onBuilt();
/*  6587: 6238 */         return result;
/*  6588:      */       }
/*  6589:      */       
/*  6590:      */       public Builder mergeFrom(Message other)
/*  6591:      */       {
/*  6592: 6242 */         if ((other instanceof MRServiceProtos.GetTaskReportsRequestProto)) {
/*  6593: 6243 */           return mergeFrom((MRServiceProtos.GetTaskReportsRequestProto)other);
/*  6594:      */         }
/*  6595: 6245 */         super.mergeFrom(other);
/*  6596: 6246 */         return this;
/*  6597:      */       }
/*  6598:      */       
/*  6599:      */       public Builder mergeFrom(MRServiceProtos.GetTaskReportsRequestProto other)
/*  6600:      */       {
/*  6601: 6251 */         if (other == MRServiceProtos.GetTaskReportsRequestProto.getDefaultInstance()) {
/*  6602: 6251 */           return this;
/*  6603:      */         }
/*  6604: 6252 */         if (other.hasJobId()) {
/*  6605: 6253 */           mergeJobId(other.getJobId());
/*  6606:      */         }
/*  6607: 6255 */         if (other.hasTaskType()) {
/*  6608: 6256 */           setTaskType(other.getTaskType());
/*  6609:      */         }
/*  6610: 6258 */         mergeUnknownFields(other.getUnknownFields());
/*  6611: 6259 */         return this;
/*  6612:      */       }
/*  6613:      */       
/*  6614:      */       public final boolean isInitialized()
/*  6615:      */       {
/*  6616: 6263 */         return true;
/*  6617:      */       }
/*  6618:      */       
/*  6619:      */       public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry)
/*  6620:      */         throws IOException
/*  6621:      */       {
/*  6622: 6270 */         MRServiceProtos.GetTaskReportsRequestProto parsedMessage = null;
/*  6623:      */         try
/*  6624:      */         {
/*  6625: 6272 */           parsedMessage = (MRServiceProtos.GetTaskReportsRequestProto)MRServiceProtos.GetTaskReportsRequestProto.PARSER.parsePartialFrom(input, extensionRegistry);
/*  6626:      */         }
/*  6627:      */         catch (InvalidProtocolBufferException e)
/*  6628:      */         {
/*  6629: 6274 */           parsedMessage = (MRServiceProtos.GetTaskReportsRequestProto)e.getUnfinishedMessage();
/*  6630: 6275 */           throw e;
/*  6631:      */         }
/*  6632:      */         finally
/*  6633:      */         {
/*  6634: 6277 */           if (parsedMessage != null) {
/*  6635: 6278 */             mergeFrom(parsedMessage);
/*  6636:      */           }
/*  6637:      */         }
/*  6638: 6281 */         return this;
/*  6639:      */       }
/*  6640:      */       
/*  6641: 6286 */       private MRProtos.JobIdProto jobId_ = MRProtos.JobIdProto.getDefaultInstance();
/*  6642:      */       private SingleFieldBuilder<MRProtos.JobIdProto, MRProtos.JobIdProto.Builder, MRProtos.JobIdProtoOrBuilder> jobIdBuilder_;
/*  6643:      */       
/*  6644:      */       public boolean hasJobId()
/*  6645:      */       {
/*  6646: 6293 */         return (this.bitField0_ & 0x1) == 1;
/*  6647:      */       }
/*  6648:      */       
/*  6649:      */       public MRProtos.JobIdProto getJobId()
/*  6650:      */       {
/*  6651: 6299 */         if (this.jobIdBuilder_ == null) {
/*  6652: 6300 */           return this.jobId_;
/*  6653:      */         }
/*  6654: 6302 */         return (MRProtos.JobIdProto)this.jobIdBuilder_.getMessage();
/*  6655:      */       }
/*  6656:      */       
/*  6657:      */       public Builder setJobId(MRProtos.JobIdProto value)
/*  6658:      */       {
/*  6659: 6309 */         if (this.jobIdBuilder_ == null)
/*  6660:      */         {
/*  6661: 6310 */           if (value == null) {
/*  6662: 6311 */             throw new NullPointerException();
/*  6663:      */           }
/*  6664: 6313 */           this.jobId_ = value;
/*  6665: 6314 */           onChanged();
/*  6666:      */         }
/*  6667:      */         else
/*  6668:      */         {
/*  6669: 6316 */           this.jobIdBuilder_.setMessage(value);
/*  6670:      */         }
/*  6671: 6318 */         this.bitField0_ |= 0x1;
/*  6672: 6319 */         return this;
/*  6673:      */       }
/*  6674:      */       
/*  6675:      */       public Builder setJobId(MRProtos.JobIdProto.Builder builderForValue)
/*  6676:      */       {
/*  6677: 6326 */         if (this.jobIdBuilder_ == null)
/*  6678:      */         {
/*  6679: 6327 */           this.jobId_ = builderForValue.build();
/*  6680: 6328 */           onChanged();
/*  6681:      */         }
/*  6682:      */         else
/*  6683:      */         {
/*  6684: 6330 */           this.jobIdBuilder_.setMessage(builderForValue.build());
/*  6685:      */         }
/*  6686: 6332 */         this.bitField0_ |= 0x1;
/*  6687: 6333 */         return this;
/*  6688:      */       }
/*  6689:      */       
/*  6690:      */       public Builder mergeJobId(MRProtos.JobIdProto value)
/*  6691:      */       {
/*  6692: 6339 */         if (this.jobIdBuilder_ == null)
/*  6693:      */         {
/*  6694: 6340 */           if (((this.bitField0_ & 0x1) == 1) && (this.jobId_ != MRProtos.JobIdProto.getDefaultInstance())) {
/*  6695: 6342 */             this.jobId_ = MRProtos.JobIdProto.newBuilder(this.jobId_).mergeFrom(value).buildPartial();
/*  6696:      */           } else {
/*  6697: 6345 */             this.jobId_ = value;
/*  6698:      */           }
/*  6699: 6347 */           onChanged();
/*  6700:      */         }
/*  6701:      */         else
/*  6702:      */         {
/*  6703: 6349 */           this.jobIdBuilder_.mergeFrom(value);
/*  6704:      */         }
/*  6705: 6351 */         this.bitField0_ |= 0x1;
/*  6706: 6352 */         return this;
/*  6707:      */       }
/*  6708:      */       
/*  6709:      */       public Builder clearJobId()
/*  6710:      */       {
/*  6711: 6358 */         if (this.jobIdBuilder_ == null)
/*  6712:      */         {
/*  6713: 6359 */           this.jobId_ = MRProtos.JobIdProto.getDefaultInstance();
/*  6714: 6360 */           onChanged();
/*  6715:      */         }
/*  6716:      */         else
/*  6717:      */         {
/*  6718: 6362 */           this.jobIdBuilder_.clear();
/*  6719:      */         }
/*  6720: 6364 */         this.bitField0_ &= 0xFFFFFFFE;
/*  6721: 6365 */         return this;
/*  6722:      */       }
/*  6723:      */       
/*  6724:      */       public MRProtos.JobIdProto.Builder getJobIdBuilder()
/*  6725:      */       {
/*  6726: 6371 */         this.bitField0_ |= 0x1;
/*  6727: 6372 */         onChanged();
/*  6728: 6373 */         return (MRProtos.JobIdProto.Builder)getJobIdFieldBuilder().getBuilder();
/*  6729:      */       }
/*  6730:      */       
/*  6731:      */       public MRProtos.JobIdProtoOrBuilder getJobIdOrBuilder()
/*  6732:      */       {
/*  6733: 6379 */         if (this.jobIdBuilder_ != null) {
/*  6734: 6380 */           return (MRProtos.JobIdProtoOrBuilder)this.jobIdBuilder_.getMessageOrBuilder();
/*  6735:      */         }
/*  6736: 6382 */         return this.jobId_;
/*  6737:      */       }
/*  6738:      */       
/*  6739:      */       private SingleFieldBuilder<MRProtos.JobIdProto, MRProtos.JobIdProto.Builder, MRProtos.JobIdProtoOrBuilder> getJobIdFieldBuilder()
/*  6740:      */       {
/*  6741: 6391 */         if (this.jobIdBuilder_ == null)
/*  6742:      */         {
/*  6743: 6392 */           this.jobIdBuilder_ = new SingleFieldBuilder(this.jobId_, getParentForChildren(), isClean());
/*  6744:      */           
/*  6745:      */ 
/*  6746:      */ 
/*  6747:      */ 
/*  6748: 6397 */           this.jobId_ = null;
/*  6749:      */         }
/*  6750: 6399 */         return this.jobIdBuilder_;
/*  6751:      */       }
/*  6752:      */       
/*  6753: 6403 */       private MRProtos.TaskTypeProto taskType_ = MRProtos.TaskTypeProto.MAP;
/*  6754:      */       
/*  6755:      */       public boolean hasTaskType()
/*  6756:      */       {
/*  6757: 6408 */         return (this.bitField0_ & 0x2) == 2;
/*  6758:      */       }
/*  6759:      */       
/*  6760:      */       public MRProtos.TaskTypeProto getTaskType()
/*  6761:      */       {
/*  6762: 6414 */         return this.taskType_;
/*  6763:      */       }
/*  6764:      */       
/*  6765:      */       public Builder setTaskType(MRProtos.TaskTypeProto value)
/*  6766:      */       {
/*  6767: 6420 */         if (value == null) {
/*  6768: 6421 */           throw new NullPointerException();
/*  6769:      */         }
/*  6770: 6423 */         this.bitField0_ |= 0x2;
/*  6771: 6424 */         this.taskType_ = value;
/*  6772: 6425 */         onChanged();
/*  6773: 6426 */         return this;
/*  6774:      */       }
/*  6775:      */       
/*  6776:      */       public Builder clearTaskType()
/*  6777:      */       {
/*  6778: 6432 */         this.bitField0_ &= 0xFFFFFFFD;
/*  6779: 6433 */         this.taskType_ = MRProtos.TaskTypeProto.MAP;
/*  6780: 6434 */         onChanged();
/*  6781: 6435 */         return this;
/*  6782:      */       }
/*  6783:      */     }
/*  6784:      */     
/*  6785:      */     static
/*  6786:      */     {
/*  6787: 6442 */       defaultInstance = new GetTaskReportsRequestProto(true);
/*  6788: 6443 */       defaultInstance.initFields();
/*  6789:      */     }
/*  6790:      */   }
/*  6791:      */   
/*  6792:      */   public static abstract interface GetTaskReportsResponseProtoOrBuilder
/*  6793:      */     extends MessageOrBuilder
/*  6794:      */   {
/*  6795:      */     public abstract List<MRProtos.TaskReportProto> getTaskReportsList();
/*  6796:      */     
/*  6797:      */     public abstract MRProtos.TaskReportProto getTaskReports(int paramInt);
/*  6798:      */     
/*  6799:      */     public abstract int getTaskReportsCount();
/*  6800:      */     
/*  6801:      */     public abstract List<? extends MRProtos.TaskReportProtoOrBuilder> getTaskReportsOrBuilderList();
/*  6802:      */     
/*  6803:      */     public abstract MRProtos.TaskReportProtoOrBuilder getTaskReportsOrBuilder(int paramInt);
/*  6804:      */   }
/*  6805:      */   
/*  6806:      */   public static final class GetTaskReportsResponseProto
/*  6807:      */     extends GeneratedMessage
/*  6808:      */     implements MRServiceProtos.GetTaskReportsResponseProtoOrBuilder
/*  6809:      */   {
/*  6810:      */     private static final GetTaskReportsResponseProto defaultInstance;
/*  6811:      */     private final UnknownFieldSet unknownFields;
/*  6812:      */     
/*  6813:      */     private GetTaskReportsResponseProto(GeneratedMessage.Builder<?> builder)
/*  6814:      */     {
/*  6815: 6485 */       super();
/*  6816: 6486 */       this.unknownFields = builder.getUnknownFields();
/*  6817:      */     }
/*  6818:      */     
/*  6819:      */     private GetTaskReportsResponseProto(boolean noInit)
/*  6820:      */     {
/*  6821: 6488 */       this.unknownFields = UnknownFieldSet.getDefaultInstance();
/*  6822:      */     }
/*  6823:      */     
/*  6824:      */     public static GetTaskReportsResponseProto getDefaultInstance()
/*  6825:      */     {
/*  6826: 6492 */       return defaultInstance;
/*  6827:      */     }
/*  6828:      */     
/*  6829:      */     public GetTaskReportsResponseProto getDefaultInstanceForType()
/*  6830:      */     {
/*  6831: 6496 */       return defaultInstance;
/*  6832:      */     }
/*  6833:      */     
/*  6834:      */     public final UnknownFieldSet getUnknownFields()
/*  6835:      */     {
/*  6836: 6503 */       return this.unknownFields;
/*  6837:      */     }
/*  6838:      */     
/*  6839:      */     private GetTaskReportsResponseProto(CodedInputStream input, ExtensionRegistryLite extensionRegistry)
/*  6840:      */       throws InvalidProtocolBufferException
/*  6841:      */     {
/*  6842: 6509 */       initFields();
/*  6843: 6510 */       int mutable_bitField0_ = 0;
/*  6844: 6511 */       UnknownFieldSet.Builder unknownFields = UnknownFieldSet.newBuilder();
/*  6845:      */       try
/*  6846:      */       {
/*  6847: 6514 */         boolean done = false;
/*  6848: 6515 */         while (!done)
/*  6849:      */         {
/*  6850: 6516 */           int tag = input.readTag();
/*  6851: 6517 */           switch (tag)
/*  6852:      */           {
/*  6853:      */           case 0: 
/*  6854: 6519 */             done = true;
/*  6855: 6520 */             break;
/*  6856:      */           default: 
/*  6857: 6522 */             if (!parseUnknownField(input, unknownFields, extensionRegistry, tag)) {
/*  6858: 6524 */               done = true;
/*  6859:      */             }
/*  6860:      */             break;
/*  6861:      */           case 10: 
/*  6862: 6529 */             if ((mutable_bitField0_ & 0x1) != 1)
/*  6863:      */             {
/*  6864: 6530 */               this.taskReports_ = new ArrayList();
/*  6865: 6531 */               mutable_bitField0_ |= 0x1;
/*  6866:      */             }
/*  6867: 6533 */             this.taskReports_.add(input.readMessage(MRProtos.TaskReportProto.PARSER, extensionRegistry));
/*  6868:      */           }
/*  6869:      */         }
/*  6870:      */       }
/*  6871:      */       catch (InvalidProtocolBufferException e)
/*  6872:      */       {
/*  6873: 6539 */         throw e.setUnfinishedMessage(this);
/*  6874:      */       }
/*  6875:      */       catch (IOException e)
/*  6876:      */       {
/*  6877: 6541 */         throw new InvalidProtocolBufferException(e.getMessage()).setUnfinishedMessage(this);
/*  6878:      */       }
/*  6879:      */       finally
/*  6880:      */       {
/*  6881: 6544 */         if ((mutable_bitField0_ & 0x1) == 1) {
/*  6882: 6545 */           this.taskReports_ = Collections.unmodifiableList(this.taskReports_);
/*  6883:      */         }
/*  6884: 6547 */         this.unknownFields = unknownFields.build();
/*  6885: 6548 */         makeExtensionsImmutable();
/*  6886:      */       }
/*  6887:      */     }
/*  6888:      */     
/*  6889:      */     public static final Descriptors.Descriptor getDescriptor()
/*  6890:      */     {
/*  6891: 6553 */       return MRServiceProtos.internal_static_hadoop_mapreduce_GetTaskReportsResponseProto_descriptor;
/*  6892:      */     }
/*  6893:      */     
/*  6894:      */     protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable()
/*  6895:      */     {
/*  6896: 6558 */       return MRServiceProtos.internal_static_hadoop_mapreduce_GetTaskReportsResponseProto_fieldAccessorTable.ensureFieldAccessorsInitialized(GetTaskReportsResponseProto.class, Builder.class);
/*  6897:      */     }
/*  6898:      */     
/*  6899: 6563 */     public static Parser<GetTaskReportsResponseProto> PARSER = new AbstractParser()
/*  6900:      */     {
/*  6901:      */       public MRServiceProtos.GetTaskReportsResponseProto parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry)
/*  6902:      */         throws InvalidProtocolBufferException
/*  6903:      */       {
/*  6904: 6569 */         return new MRServiceProtos.GetTaskReportsResponseProto(input, extensionRegistry, null);
/*  6905:      */       }
/*  6906:      */     };
/*  6907:      */     public static final int TASK_REPORTS_FIELD_NUMBER = 1;
/*  6908:      */     private List<MRProtos.TaskReportProto> taskReports_;
/*  6909:      */     
/*  6910:      */     public Parser<GetTaskReportsResponseProto> getParserForType()
/*  6911:      */     {
/*  6912: 6575 */       return PARSER;
/*  6913:      */     }
/*  6914:      */     
/*  6915:      */     public List<MRProtos.TaskReportProto> getTaskReportsList()
/*  6916:      */     {
/*  6917: 6585 */       return this.taskReports_;
/*  6918:      */     }
/*  6919:      */     
/*  6920:      */     public List<? extends MRProtos.TaskReportProtoOrBuilder> getTaskReportsOrBuilderList()
/*  6921:      */     {
/*  6922: 6592 */       return this.taskReports_;
/*  6923:      */     }
/*  6924:      */     
/*  6925:      */     public int getTaskReportsCount()
/*  6926:      */     {
/*  6927: 6598 */       return this.taskReports_.size();
/*  6928:      */     }
/*  6929:      */     
/*  6930:      */     public MRProtos.TaskReportProto getTaskReports(int index)
/*  6931:      */     {
/*  6932: 6604 */       return (MRProtos.TaskReportProto)this.taskReports_.get(index);
/*  6933:      */     }
/*  6934:      */     
/*  6935:      */     public MRProtos.TaskReportProtoOrBuilder getTaskReportsOrBuilder(int index)
/*  6936:      */     {
/*  6937: 6611 */       return (MRProtos.TaskReportProtoOrBuilder)this.taskReports_.get(index);
/*  6938:      */     }
/*  6939:      */     
/*  6940:      */     private void initFields()
/*  6941:      */     {
/*  6942: 6615 */       this.taskReports_ = Collections.emptyList();
/*  6943:      */     }
/*  6944:      */     
/*  6945: 6617 */     private byte memoizedIsInitialized = -1;
/*  6946:      */     
/*  6947:      */     public final boolean isInitialized()
/*  6948:      */     {
/*  6949: 6619 */       byte isInitialized = this.memoizedIsInitialized;
/*  6950: 6620 */       if (isInitialized != -1) {
/*  6951: 6620 */         return isInitialized == 1;
/*  6952:      */       }
/*  6953: 6622 */       this.memoizedIsInitialized = 1;
/*  6954: 6623 */       return true;
/*  6955:      */     }
/*  6956:      */     
/*  6957:      */     public void writeTo(CodedOutputStream output)
/*  6958:      */       throws IOException
/*  6959:      */     {
/*  6960: 6628 */       getSerializedSize();
/*  6961: 6629 */       for (int i = 0; i < this.taskReports_.size(); i++) {
/*  6962: 6630 */         output.writeMessage(1, (MessageLite)this.taskReports_.get(i));
/*  6963:      */       }
/*  6964: 6632 */       getUnknownFields().writeTo(output);
/*  6965:      */     }
/*  6966:      */     
/*  6967: 6635 */     private int memoizedSerializedSize = -1;
/*  6968:      */     private static final long serialVersionUID = 0L;
/*  6969:      */     
/*  6970:      */     public int getSerializedSize()
/*  6971:      */     {
/*  6972: 6637 */       int size = this.memoizedSerializedSize;
/*  6973: 6638 */       if (size != -1) {
/*  6974: 6638 */         return size;
/*  6975:      */       }
/*  6976: 6640 */       size = 0;
/*  6977: 6641 */       for (int i = 0; i < this.taskReports_.size(); i++) {
/*  6978: 6642 */         size += CodedOutputStream.computeMessageSize(1, (MessageLite)this.taskReports_.get(i));
/*  6979:      */       }
/*  6980: 6645 */       size += getUnknownFields().getSerializedSize();
/*  6981: 6646 */       this.memoizedSerializedSize = size;
/*  6982: 6647 */       return size;
/*  6983:      */     }
/*  6984:      */     
/*  6985:      */     protected Object writeReplace()
/*  6986:      */       throws ObjectStreamException
/*  6987:      */     {
/*  6988: 6654 */       return super.writeReplace();
/*  6989:      */     }
/*  6990:      */     
/*  6991:      */     public boolean equals(Object obj)
/*  6992:      */     {
/*  6993: 6659 */       if (obj == this) {
/*  6994: 6660 */         return true;
/*  6995:      */       }
/*  6996: 6662 */       if (!(obj instanceof GetTaskReportsResponseProto)) {
/*  6997: 6663 */         return super.equals(obj);
/*  6998:      */       }
/*  6999: 6665 */       GetTaskReportsResponseProto other = (GetTaskReportsResponseProto)obj;
/*  7000:      */       
/*  7001: 6667 */       boolean result = true;
/*  7002: 6668 */       result = (result) && (getTaskReportsList().equals(other.getTaskReportsList()));
/*  7003:      */       
/*  7004: 6670 */       result = (result) && (getUnknownFields().equals(other.getUnknownFields()));
/*  7005:      */       
/*  7006: 6672 */       return result;
/*  7007:      */     }
/*  7008:      */     
/*  7009: 6675 */     private int memoizedHashCode = 0;
/*  7010:      */     
/*  7011:      */     public int hashCode()
/*  7012:      */     {
/*  7013: 6678 */       if (this.memoizedHashCode != 0) {
/*  7014: 6679 */         return this.memoizedHashCode;
/*  7015:      */       }
/*  7016: 6681 */       int hash = 41;
/*  7017: 6682 */       hash = 19 * hash + getDescriptorForType().hashCode();
/*  7018: 6683 */       if (getTaskReportsCount() > 0)
/*  7019:      */       {
/*  7020: 6684 */         hash = 37 * hash + 1;
/*  7021: 6685 */         hash = 53 * hash + getTaskReportsList().hashCode();
/*  7022:      */       }
/*  7023: 6687 */       hash = 29 * hash + getUnknownFields().hashCode();
/*  7024: 6688 */       this.memoizedHashCode = hash;
/*  7025: 6689 */       return hash;
/*  7026:      */     }
/*  7027:      */     
/*  7028:      */     public static GetTaskReportsResponseProto parseFrom(ByteString data)
/*  7029:      */       throws InvalidProtocolBufferException
/*  7030:      */     {
/*  7031: 6695 */       return (GetTaskReportsResponseProto)PARSER.parseFrom(data);
/*  7032:      */     }
/*  7033:      */     
/*  7034:      */     public static GetTaskReportsResponseProto parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry)
/*  7035:      */       throws InvalidProtocolBufferException
/*  7036:      */     {
/*  7037: 6701 */       return (GetTaskReportsResponseProto)PARSER.parseFrom(data, extensionRegistry);
/*  7038:      */     }
/*  7039:      */     
/*  7040:      */     public static GetTaskReportsResponseProto parseFrom(byte[] data)
/*  7041:      */       throws InvalidProtocolBufferException
/*  7042:      */     {
/*  7043: 6705 */       return (GetTaskReportsResponseProto)PARSER.parseFrom(data);
/*  7044:      */     }
/*  7045:      */     
/*  7046:      */     public static GetTaskReportsResponseProto parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry)
/*  7047:      */       throws InvalidProtocolBufferException
/*  7048:      */     {
/*  7049: 6711 */       return (GetTaskReportsResponseProto)PARSER.parseFrom(data, extensionRegistry);
/*  7050:      */     }
/*  7051:      */     
/*  7052:      */     public static GetTaskReportsResponseProto parseFrom(InputStream input)
/*  7053:      */       throws IOException
/*  7054:      */     {
/*  7055: 6715 */       return (GetTaskReportsResponseProto)PARSER.parseFrom(input);
/*  7056:      */     }
/*  7057:      */     
/*  7058:      */     public static GetTaskReportsResponseProto parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry)
/*  7059:      */       throws IOException
/*  7060:      */     {
/*  7061: 6721 */       return (GetTaskReportsResponseProto)PARSER.parseFrom(input, extensionRegistry);
/*  7062:      */     }
/*  7063:      */     
/*  7064:      */     public static GetTaskReportsResponseProto parseDelimitedFrom(InputStream input)
/*  7065:      */       throws IOException
/*  7066:      */     {
/*  7067: 6725 */       return (GetTaskReportsResponseProto)PARSER.parseDelimitedFrom(input);
/*  7068:      */     }
/*  7069:      */     
/*  7070:      */     public static GetTaskReportsResponseProto parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry)
/*  7071:      */       throws IOException
/*  7072:      */     {
/*  7073: 6731 */       return (GetTaskReportsResponseProto)PARSER.parseDelimitedFrom(input, extensionRegistry);
/*  7074:      */     }
/*  7075:      */     
/*  7076:      */     public static GetTaskReportsResponseProto parseFrom(CodedInputStream input)
/*  7077:      */       throws IOException
/*  7078:      */     {
/*  7079: 6736 */       return (GetTaskReportsResponseProto)PARSER.parseFrom(input);
/*  7080:      */     }
/*  7081:      */     
/*  7082:      */     public static GetTaskReportsResponseProto parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry)
/*  7083:      */       throws IOException
/*  7084:      */     {
/*  7085: 6742 */       return (GetTaskReportsResponseProto)PARSER.parseFrom(input, extensionRegistry);
/*  7086:      */     }
/*  7087:      */     
/*  7088:      */     public static Builder newBuilder()
/*  7089:      */     {
/*  7090: 6745 */       return Builder.access$10500();
/*  7091:      */     }
/*  7092:      */     
/*  7093:      */     public Builder newBuilderForType()
/*  7094:      */     {
/*  7095: 6746 */       return newBuilder();
/*  7096:      */     }
/*  7097:      */     
/*  7098:      */     public static Builder newBuilder(GetTaskReportsResponseProto prototype)
/*  7099:      */     {
/*  7100: 6748 */       return newBuilder().mergeFrom(prototype);
/*  7101:      */     }
/*  7102:      */     
/*  7103:      */     public Builder toBuilder()
/*  7104:      */     {
/*  7105: 6750 */       return newBuilder(this);
/*  7106:      */     }
/*  7107:      */     
/*  7108:      */     protected Builder newBuilderForType(GeneratedMessage.BuilderParent parent)
/*  7109:      */     {
/*  7110: 6755 */       Builder builder = new Builder(parent, null);
/*  7111: 6756 */       return builder;
/*  7112:      */     }
/*  7113:      */     
/*  7114:      */     public static final class Builder
/*  7115:      */       extends GeneratedMessage.Builder<Builder>
/*  7116:      */       implements MRServiceProtos.GetTaskReportsResponseProtoOrBuilder
/*  7117:      */     {
/*  7118:      */       private int bitField0_;
/*  7119:      */       
/*  7120:      */       public static final Descriptors.Descriptor getDescriptor()
/*  7121:      */       {
/*  7122: 6766 */         return MRServiceProtos.internal_static_hadoop_mapreduce_GetTaskReportsResponseProto_descriptor;
/*  7123:      */       }
/*  7124:      */       
/*  7125:      */       protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable()
/*  7126:      */       {
/*  7127: 6771 */         return MRServiceProtos.internal_static_hadoop_mapreduce_GetTaskReportsResponseProto_fieldAccessorTable.ensureFieldAccessorsInitialized(MRServiceProtos.GetTaskReportsResponseProto.class, Builder.class);
/*  7128:      */       }
/*  7129:      */       
/*  7130:      */       private Builder()
/*  7131:      */       {
/*  7132: 6778 */         maybeForceBuilderInitialization();
/*  7133:      */       }
/*  7134:      */       
/*  7135:      */       private Builder(GeneratedMessage.BuilderParent parent)
/*  7136:      */       {
/*  7137: 6783 */         super();
/*  7138: 6784 */         maybeForceBuilderInitialization();
/*  7139:      */       }
/*  7140:      */       
/*  7141:      */       private void maybeForceBuilderInitialization()
/*  7142:      */       {
/*  7143: 6787 */         if (MRServiceProtos.GetTaskReportsResponseProto.alwaysUseFieldBuilders) {
/*  7144: 6788 */           getTaskReportsFieldBuilder();
/*  7145:      */         }
/*  7146:      */       }
/*  7147:      */       
/*  7148:      */       private static Builder create()
/*  7149:      */       {
/*  7150: 6792 */         return new Builder();
/*  7151:      */       }
/*  7152:      */       
/*  7153:      */       public Builder clear()
/*  7154:      */       {
/*  7155: 6796 */         super.clear();
/*  7156: 6797 */         if (this.taskReportsBuilder_ == null)
/*  7157:      */         {
/*  7158: 6798 */           this.taskReports_ = Collections.emptyList();
/*  7159: 6799 */           this.bitField0_ &= 0xFFFFFFFE;
/*  7160:      */         }
/*  7161:      */         else
/*  7162:      */         {
/*  7163: 6801 */           this.taskReportsBuilder_.clear();
/*  7164:      */         }
/*  7165: 6803 */         return this;
/*  7166:      */       }
/*  7167:      */       
/*  7168:      */       public Builder clone()
/*  7169:      */       {
/*  7170: 6807 */         return create().mergeFrom(buildPartial());
/*  7171:      */       }
/*  7172:      */       
/*  7173:      */       public Descriptors.Descriptor getDescriptorForType()
/*  7174:      */       {
/*  7175: 6812 */         return MRServiceProtos.internal_static_hadoop_mapreduce_GetTaskReportsResponseProto_descriptor;
/*  7176:      */       }
/*  7177:      */       
/*  7178:      */       public MRServiceProtos.GetTaskReportsResponseProto getDefaultInstanceForType()
/*  7179:      */       {
/*  7180: 6816 */         return MRServiceProtos.GetTaskReportsResponseProto.getDefaultInstance();
/*  7181:      */       }
/*  7182:      */       
/*  7183:      */       public MRServiceProtos.GetTaskReportsResponseProto build()
/*  7184:      */       {
/*  7185: 6820 */         MRServiceProtos.GetTaskReportsResponseProto result = buildPartial();
/*  7186: 6821 */         if (!result.isInitialized()) {
/*  7187: 6822 */           throw newUninitializedMessageException(result);
/*  7188:      */         }
/*  7189: 6824 */         return result;
/*  7190:      */       }
/*  7191:      */       
/*  7192:      */       public MRServiceProtos.GetTaskReportsResponseProto buildPartial()
/*  7193:      */       {
/*  7194: 6828 */         MRServiceProtos.GetTaskReportsResponseProto result = new MRServiceProtos.GetTaskReportsResponseProto(this, null);
/*  7195: 6829 */         int from_bitField0_ = this.bitField0_;
/*  7196: 6830 */         if (this.taskReportsBuilder_ == null)
/*  7197:      */         {
/*  7198: 6831 */           if ((this.bitField0_ & 0x1) == 1)
/*  7199:      */           {
/*  7200: 6832 */             this.taskReports_ = Collections.unmodifiableList(this.taskReports_);
/*  7201: 6833 */             this.bitField0_ &= 0xFFFFFFFE;
/*  7202:      */           }
/*  7203: 6835 */           result.taskReports_ = this.taskReports_;
/*  7204:      */         }
/*  7205:      */         else
/*  7206:      */         {
/*  7207: 6837 */           result.taskReports_ = this.taskReportsBuilder_.build();
/*  7208:      */         }
/*  7209: 6839 */         onBuilt();
/*  7210: 6840 */         return result;
/*  7211:      */       }
/*  7212:      */       
/*  7213:      */       public Builder mergeFrom(Message other)
/*  7214:      */       {
/*  7215: 6844 */         if ((other instanceof MRServiceProtos.GetTaskReportsResponseProto)) {
/*  7216: 6845 */           return mergeFrom((MRServiceProtos.GetTaskReportsResponseProto)other);
/*  7217:      */         }
/*  7218: 6847 */         super.mergeFrom(other);
/*  7219: 6848 */         return this;
/*  7220:      */       }
/*  7221:      */       
/*  7222:      */       public Builder mergeFrom(MRServiceProtos.GetTaskReportsResponseProto other)
/*  7223:      */       {
/*  7224: 6853 */         if (other == MRServiceProtos.GetTaskReportsResponseProto.getDefaultInstance()) {
/*  7225: 6853 */           return this;
/*  7226:      */         }
/*  7227: 6854 */         if (this.taskReportsBuilder_ == null)
/*  7228:      */         {
/*  7229: 6855 */           if (!other.taskReports_.isEmpty())
/*  7230:      */           {
/*  7231: 6856 */             if (this.taskReports_.isEmpty())
/*  7232:      */             {
/*  7233: 6857 */               this.taskReports_ = other.taskReports_;
/*  7234: 6858 */               this.bitField0_ &= 0xFFFFFFFE;
/*  7235:      */             }
/*  7236:      */             else
/*  7237:      */             {
/*  7238: 6860 */               ensureTaskReportsIsMutable();
/*  7239: 6861 */               this.taskReports_.addAll(other.taskReports_);
/*  7240:      */             }
/*  7241: 6863 */             onChanged();
/*  7242:      */           }
/*  7243:      */         }
/*  7244: 6866 */         else if (!other.taskReports_.isEmpty()) {
/*  7245: 6867 */           if (this.taskReportsBuilder_.isEmpty())
/*  7246:      */           {
/*  7247: 6868 */             this.taskReportsBuilder_.dispose();
/*  7248: 6869 */             this.taskReportsBuilder_ = null;
/*  7249: 6870 */             this.taskReports_ = other.taskReports_;
/*  7250: 6871 */             this.bitField0_ &= 0xFFFFFFFE;
/*  7251: 6872 */             this.taskReportsBuilder_ = (MRServiceProtos.GetTaskReportsResponseProto.alwaysUseFieldBuilders ? getTaskReportsFieldBuilder() : null);
/*  7252:      */           }
/*  7253:      */           else
/*  7254:      */           {
/*  7255: 6876 */             this.taskReportsBuilder_.addAllMessages(other.taskReports_);
/*  7256:      */           }
/*  7257:      */         }
/*  7258: 6880 */         mergeUnknownFields(other.getUnknownFields());
/*  7259: 6881 */         return this;
/*  7260:      */       }
/*  7261:      */       
/*  7262:      */       public final boolean isInitialized()
/*  7263:      */       {
/*  7264: 6885 */         return true;
/*  7265:      */       }
/*  7266:      */       
/*  7267:      */       public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry)
/*  7268:      */         throws IOException
/*  7269:      */       {
/*  7270: 6892 */         MRServiceProtos.GetTaskReportsResponseProto parsedMessage = null;
/*  7271:      */         try
/*  7272:      */         {
/*  7273: 6894 */           parsedMessage = (MRServiceProtos.GetTaskReportsResponseProto)MRServiceProtos.GetTaskReportsResponseProto.PARSER.parsePartialFrom(input, extensionRegistry);
/*  7274:      */         }
/*  7275:      */         catch (InvalidProtocolBufferException e)
/*  7276:      */         {
/*  7277: 6896 */           parsedMessage = (MRServiceProtos.GetTaskReportsResponseProto)e.getUnfinishedMessage();
/*  7278: 6897 */           throw e;
/*  7279:      */         }
/*  7280:      */         finally
/*  7281:      */         {
/*  7282: 6899 */           if (parsedMessage != null) {
/*  7283: 6900 */             mergeFrom(parsedMessage);
/*  7284:      */           }
/*  7285:      */         }
/*  7286: 6903 */         return this;
/*  7287:      */       }
/*  7288:      */       
/*  7289: 6908 */       private List<MRProtos.TaskReportProto> taskReports_ = Collections.emptyList();
/*  7290:      */       private RepeatedFieldBuilder<MRProtos.TaskReportProto, MRProtos.TaskReportProto.Builder, MRProtos.TaskReportProtoOrBuilder> taskReportsBuilder_;
/*  7291:      */       
/*  7292:      */       private void ensureTaskReportsIsMutable()
/*  7293:      */       {
/*  7294: 6911 */         if ((this.bitField0_ & 0x1) != 1)
/*  7295:      */         {
/*  7296: 6912 */           this.taskReports_ = new ArrayList(this.taskReports_);
/*  7297: 6913 */           this.bitField0_ |= 0x1;
/*  7298:      */         }
/*  7299:      */       }
/*  7300:      */       
/*  7301:      */       public List<MRProtos.TaskReportProto> getTaskReportsList()
/*  7302:      */       {
/*  7303: 6924 */         if (this.taskReportsBuilder_ == null) {
/*  7304: 6925 */           return Collections.unmodifiableList(this.taskReports_);
/*  7305:      */         }
/*  7306: 6927 */         return this.taskReportsBuilder_.getMessageList();
/*  7307:      */       }
/*  7308:      */       
/*  7309:      */       public int getTaskReportsCount()
/*  7310:      */       {
/*  7311: 6934 */         if (this.taskReportsBuilder_ == null) {
/*  7312: 6935 */           return this.taskReports_.size();
/*  7313:      */         }
/*  7314: 6937 */         return this.taskReportsBuilder_.getCount();
/*  7315:      */       }
/*  7316:      */       
/*  7317:      */       public MRProtos.TaskReportProto getTaskReports(int index)
/*  7318:      */       {
/*  7319: 6944 */         if (this.taskReportsBuilder_ == null) {
/*  7320: 6945 */           return (MRProtos.TaskReportProto)this.taskReports_.get(index);
/*  7321:      */         }
/*  7322: 6947 */         return (MRProtos.TaskReportProto)this.taskReportsBuilder_.getMessage(index);
/*  7323:      */       }
/*  7324:      */       
/*  7325:      */       public Builder setTaskReports(int index, MRProtos.TaskReportProto value)
/*  7326:      */       {
/*  7327: 6955 */         if (this.taskReportsBuilder_ == null)
/*  7328:      */         {
/*  7329: 6956 */           if (value == null) {
/*  7330: 6957 */             throw new NullPointerException();
/*  7331:      */           }
/*  7332: 6959 */           ensureTaskReportsIsMutable();
/*  7333: 6960 */           this.taskReports_.set(index, value);
/*  7334: 6961 */           onChanged();
/*  7335:      */         }
/*  7336:      */         else
/*  7337:      */         {
/*  7338: 6963 */           this.taskReportsBuilder_.setMessage(index, value);
/*  7339:      */         }
/*  7340: 6965 */         return this;
/*  7341:      */       }
/*  7342:      */       
/*  7343:      */       public Builder setTaskReports(int index, MRProtos.TaskReportProto.Builder builderForValue)
/*  7344:      */       {
/*  7345: 6972 */         if (this.taskReportsBuilder_ == null)
/*  7346:      */         {
/*  7347: 6973 */           ensureTaskReportsIsMutable();
/*  7348: 6974 */           this.taskReports_.set(index, builderForValue.build());
/*  7349: 6975 */           onChanged();
/*  7350:      */         }
/*  7351:      */         else
/*  7352:      */         {
/*  7353: 6977 */           this.taskReportsBuilder_.setMessage(index, builderForValue.build());
/*  7354:      */         }
/*  7355: 6979 */         return this;
/*  7356:      */       }
/*  7357:      */       
/*  7358:      */       public Builder addTaskReports(MRProtos.TaskReportProto value)
/*  7359:      */       {
/*  7360: 6985 */         if (this.taskReportsBuilder_ == null)
/*  7361:      */         {
/*  7362: 6986 */           if (value == null) {
/*  7363: 6987 */             throw new NullPointerException();
/*  7364:      */           }
/*  7365: 6989 */           ensureTaskReportsIsMutable();
/*  7366: 6990 */           this.taskReports_.add(value);
/*  7367: 6991 */           onChanged();
/*  7368:      */         }
/*  7369:      */         else
/*  7370:      */         {
/*  7371: 6993 */           this.taskReportsBuilder_.addMessage(value);
/*  7372:      */         }
/*  7373: 6995 */         return this;
/*  7374:      */       }
/*  7375:      */       
/*  7376:      */       public Builder addTaskReports(int index, MRProtos.TaskReportProto value)
/*  7377:      */       {
/*  7378: 7002 */         if (this.taskReportsBuilder_ == null)
/*  7379:      */         {
/*  7380: 7003 */           if (value == null) {
/*  7381: 7004 */             throw new NullPointerException();
/*  7382:      */           }
/*  7383: 7006 */           ensureTaskReportsIsMutable();
/*  7384: 7007 */           this.taskReports_.add(index, value);
/*  7385: 7008 */           onChanged();
/*  7386:      */         }
/*  7387:      */         else
/*  7388:      */         {
/*  7389: 7010 */           this.taskReportsBuilder_.addMessage(index, value);
/*  7390:      */         }
/*  7391: 7012 */         return this;
/*  7392:      */       }
/*  7393:      */       
/*  7394:      */       public Builder addTaskReports(MRProtos.TaskReportProto.Builder builderForValue)
/*  7395:      */       {
/*  7396: 7019 */         if (this.taskReportsBuilder_ == null)
/*  7397:      */         {
/*  7398: 7020 */           ensureTaskReportsIsMutable();
/*  7399: 7021 */           this.taskReports_.add(builderForValue.build());
/*  7400: 7022 */           onChanged();
/*  7401:      */         }
/*  7402:      */         else
/*  7403:      */         {
/*  7404: 7024 */           this.taskReportsBuilder_.addMessage(builderForValue.build());
/*  7405:      */         }
/*  7406: 7026 */         return this;
/*  7407:      */       }
/*  7408:      */       
/*  7409:      */       public Builder addTaskReports(int index, MRProtos.TaskReportProto.Builder builderForValue)
/*  7410:      */       {
/*  7411: 7033 */         if (this.taskReportsBuilder_ == null)
/*  7412:      */         {
/*  7413: 7034 */           ensureTaskReportsIsMutable();
/*  7414: 7035 */           this.taskReports_.add(index, builderForValue.build());
/*  7415: 7036 */           onChanged();
/*  7416:      */         }
/*  7417:      */         else
/*  7418:      */         {
/*  7419: 7038 */           this.taskReportsBuilder_.addMessage(index, builderForValue.build());
/*  7420:      */         }
/*  7421: 7040 */         return this;
/*  7422:      */       }
/*  7423:      */       
/*  7424:      */       public Builder addAllTaskReports(Iterable<? extends MRProtos.TaskReportProto> values)
/*  7425:      */       {
/*  7426: 7047 */         if (this.taskReportsBuilder_ == null)
/*  7427:      */         {
/*  7428: 7048 */           ensureTaskReportsIsMutable();
/*  7429: 7049 */           GeneratedMessage.Builder.addAll(values, this.taskReports_);
/*  7430: 7050 */           onChanged();
/*  7431:      */         }
/*  7432:      */         else
/*  7433:      */         {
/*  7434: 7052 */           this.taskReportsBuilder_.addAllMessages(values);
/*  7435:      */         }
/*  7436: 7054 */         return this;
/*  7437:      */       }
/*  7438:      */       
/*  7439:      */       public Builder clearTaskReports()
/*  7440:      */       {
/*  7441: 7060 */         if (this.taskReportsBuilder_ == null)
/*  7442:      */         {
/*  7443: 7061 */           this.taskReports_ = Collections.emptyList();
/*  7444: 7062 */           this.bitField0_ &= 0xFFFFFFFE;
/*  7445: 7063 */           onChanged();
/*  7446:      */         }
/*  7447:      */         else
/*  7448:      */         {
/*  7449: 7065 */           this.taskReportsBuilder_.clear();
/*  7450:      */         }
/*  7451: 7067 */         return this;
/*  7452:      */       }
/*  7453:      */       
/*  7454:      */       public Builder removeTaskReports(int index)
/*  7455:      */       {
/*  7456: 7073 */         if (this.taskReportsBuilder_ == null)
/*  7457:      */         {
/*  7458: 7074 */           ensureTaskReportsIsMutable();
/*  7459: 7075 */           this.taskReports_.remove(index);
/*  7460: 7076 */           onChanged();
/*  7461:      */         }
/*  7462:      */         else
/*  7463:      */         {
/*  7464: 7078 */           this.taskReportsBuilder_.remove(index);
/*  7465:      */         }
/*  7466: 7080 */         return this;
/*  7467:      */       }
/*  7468:      */       
/*  7469:      */       public MRProtos.TaskReportProto.Builder getTaskReportsBuilder(int index)
/*  7470:      */       {
/*  7471: 7087 */         return (MRProtos.TaskReportProto.Builder)getTaskReportsFieldBuilder().getBuilder(index);
/*  7472:      */       }
/*  7473:      */       
/*  7474:      */       public MRProtos.TaskReportProtoOrBuilder getTaskReportsOrBuilder(int index)
/*  7475:      */       {
/*  7476: 7094 */         if (this.taskReportsBuilder_ == null) {
/*  7477: 7095 */           return (MRProtos.TaskReportProtoOrBuilder)this.taskReports_.get(index);
/*  7478:      */         }
/*  7479: 7096 */         return (MRProtos.TaskReportProtoOrBuilder)this.taskReportsBuilder_.getMessageOrBuilder(index);
/*  7480:      */       }
/*  7481:      */       
/*  7482:      */       public List<? extends MRProtos.TaskReportProtoOrBuilder> getTaskReportsOrBuilderList()
/*  7483:      */       {
/*  7484: 7104 */         if (this.taskReportsBuilder_ != null) {
/*  7485: 7105 */           return this.taskReportsBuilder_.getMessageOrBuilderList();
/*  7486:      */         }
/*  7487: 7107 */         return Collections.unmodifiableList(this.taskReports_);
/*  7488:      */       }
/*  7489:      */       
/*  7490:      */       public MRProtos.TaskReportProto.Builder addTaskReportsBuilder()
/*  7491:      */       {
/*  7492: 7114 */         return (MRProtos.TaskReportProto.Builder)getTaskReportsFieldBuilder().addBuilder(MRProtos.TaskReportProto.getDefaultInstance());
/*  7493:      */       }
/*  7494:      */       
/*  7495:      */       public MRProtos.TaskReportProto.Builder addTaskReportsBuilder(int index)
/*  7496:      */       {
/*  7497: 7122 */         return (MRProtos.TaskReportProto.Builder)getTaskReportsFieldBuilder().addBuilder(index, MRProtos.TaskReportProto.getDefaultInstance());
/*  7498:      */       }
/*  7499:      */       
/*  7500:      */       public List<MRProtos.TaskReportProto.Builder> getTaskReportsBuilderList()
/*  7501:      */       {
/*  7502: 7130 */         return getTaskReportsFieldBuilder().getBuilderList();
/*  7503:      */       }
/*  7504:      */       
/*  7505:      */       private RepeatedFieldBuilder<MRProtos.TaskReportProto, MRProtos.TaskReportProto.Builder, MRProtos.TaskReportProtoOrBuilder> getTaskReportsFieldBuilder()
/*  7506:      */       {
/*  7507: 7135 */         if (this.taskReportsBuilder_ == null)
/*  7508:      */         {
/*  7509: 7136 */           this.taskReportsBuilder_ = new RepeatedFieldBuilder(this.taskReports_, (this.bitField0_ & 0x1) == 1, getParentForChildren(), isClean());
/*  7510:      */           
/*  7511:      */ 
/*  7512:      */ 
/*  7513:      */ 
/*  7514:      */ 
/*  7515: 7142 */           this.taskReports_ = null;
/*  7516:      */         }
/*  7517: 7144 */         return this.taskReportsBuilder_;
/*  7518:      */       }
/*  7519:      */     }
/*  7520:      */     
/*  7521:      */     static
/*  7522:      */     {
/*  7523: 7151 */       defaultInstance = new GetTaskReportsResponseProto(true);
/*  7524: 7152 */       defaultInstance.initFields();
/*  7525:      */     }
/*  7526:      */   }
/*  7527:      */   
/*  7528:      */   public static abstract interface GetDiagnosticsRequestProtoOrBuilder
/*  7529:      */     extends MessageOrBuilder
/*  7530:      */   {
/*  7531:      */     public abstract boolean hasTaskAttemptId();
/*  7532:      */     
/*  7533:      */     public abstract MRProtos.TaskAttemptIdProto getTaskAttemptId();
/*  7534:      */     
/*  7535:      */     public abstract MRProtos.TaskAttemptIdProtoOrBuilder getTaskAttemptIdOrBuilder();
/*  7536:      */   }
/*  7537:      */   
/*  7538:      */   public static final class GetDiagnosticsRequestProto
/*  7539:      */     extends GeneratedMessage
/*  7540:      */     implements MRServiceProtos.GetDiagnosticsRequestProtoOrBuilder
/*  7541:      */   {
/*  7542:      */     private static final GetDiagnosticsRequestProto defaultInstance;
/*  7543:      */     private final UnknownFieldSet unknownFields;
/*  7544:      */     
/*  7545:      */     private GetDiagnosticsRequestProto(GeneratedMessage.Builder<?> builder)
/*  7546:      */     {
/*  7547: 7183 */       super();
/*  7548: 7184 */       this.unknownFields = builder.getUnknownFields();
/*  7549:      */     }
/*  7550:      */     
/*  7551:      */     private GetDiagnosticsRequestProto(boolean noInit)
/*  7552:      */     {
/*  7553: 7186 */       this.unknownFields = UnknownFieldSet.getDefaultInstance();
/*  7554:      */     }
/*  7555:      */     
/*  7556:      */     public static GetDiagnosticsRequestProto getDefaultInstance()
/*  7557:      */     {
/*  7558: 7190 */       return defaultInstance;
/*  7559:      */     }
/*  7560:      */     
/*  7561:      */     public GetDiagnosticsRequestProto getDefaultInstanceForType()
/*  7562:      */     {
/*  7563: 7194 */       return defaultInstance;
/*  7564:      */     }
/*  7565:      */     
/*  7566:      */     public final UnknownFieldSet getUnknownFields()
/*  7567:      */     {
/*  7568: 7201 */       return this.unknownFields;
/*  7569:      */     }
/*  7570:      */     
/*  7571:      */     private GetDiagnosticsRequestProto(CodedInputStream input, ExtensionRegistryLite extensionRegistry)
/*  7572:      */       throws InvalidProtocolBufferException
/*  7573:      */     {
/*  7574: 7207 */       initFields();
/*  7575: 7208 */       int mutable_bitField0_ = 0;
/*  7576: 7209 */       UnknownFieldSet.Builder unknownFields = UnknownFieldSet.newBuilder();
/*  7577:      */       try
/*  7578:      */       {
/*  7579: 7212 */         boolean done = false;
/*  7580: 7213 */         while (!done)
/*  7581:      */         {
/*  7582: 7214 */           int tag = input.readTag();
/*  7583: 7215 */           switch (tag)
/*  7584:      */           {
/*  7585:      */           case 0: 
/*  7586: 7217 */             done = true;
/*  7587: 7218 */             break;
/*  7588:      */           default: 
/*  7589: 7220 */             if (!parseUnknownField(input, unknownFields, extensionRegistry, tag)) {
/*  7590: 7222 */               done = true;
/*  7591:      */             }
/*  7592:      */             break;
/*  7593:      */           case 10: 
/*  7594: 7227 */             MRProtos.TaskAttemptIdProto.Builder subBuilder = null;
/*  7595: 7228 */             if ((this.bitField0_ & 0x1) == 1) {
/*  7596: 7229 */               subBuilder = this.taskAttemptId_.toBuilder();
/*  7597:      */             }
/*  7598: 7231 */             this.taskAttemptId_ = ((MRProtos.TaskAttemptIdProto)input.readMessage(MRProtos.TaskAttemptIdProto.PARSER, extensionRegistry));
/*  7599: 7232 */             if (subBuilder != null)
/*  7600:      */             {
/*  7601: 7233 */               subBuilder.mergeFrom(this.taskAttemptId_);
/*  7602: 7234 */               this.taskAttemptId_ = subBuilder.buildPartial();
/*  7603:      */             }
/*  7604: 7236 */             this.bitField0_ |= 0x1;
/*  7605:      */           }
/*  7606:      */         }
/*  7607:      */       }
/*  7608:      */       catch (InvalidProtocolBufferException e)
/*  7609:      */       {
/*  7610: 7242 */         throw e.setUnfinishedMessage(this);
/*  7611:      */       }
/*  7612:      */       catch (IOException e)
/*  7613:      */       {
/*  7614: 7244 */         throw new InvalidProtocolBufferException(e.getMessage()).setUnfinishedMessage(this);
/*  7615:      */       }
/*  7616:      */       finally
/*  7617:      */       {
/*  7618: 7247 */         this.unknownFields = unknownFields.build();
/*  7619: 7248 */         makeExtensionsImmutable();
/*  7620:      */       }
/*  7621:      */     }
/*  7622:      */     
/*  7623:      */     public static final Descriptors.Descriptor getDescriptor()
/*  7624:      */     {
/*  7625: 7253 */       return MRServiceProtos.internal_static_hadoop_mapreduce_GetDiagnosticsRequestProto_descriptor;
/*  7626:      */     }
/*  7627:      */     
/*  7628:      */     protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable()
/*  7629:      */     {
/*  7630: 7258 */       return MRServiceProtos.internal_static_hadoop_mapreduce_GetDiagnosticsRequestProto_fieldAccessorTable.ensureFieldAccessorsInitialized(GetDiagnosticsRequestProto.class, Builder.class);
/*  7631:      */     }
/*  7632:      */     
/*  7633: 7263 */     public static Parser<GetDiagnosticsRequestProto> PARSER = new AbstractParser()
/*  7634:      */     {
/*  7635:      */       public MRServiceProtos.GetDiagnosticsRequestProto parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry)
/*  7636:      */         throws InvalidProtocolBufferException
/*  7637:      */       {
/*  7638: 7269 */         return new MRServiceProtos.GetDiagnosticsRequestProto(input, extensionRegistry, null);
/*  7639:      */       }
/*  7640:      */     };
/*  7641:      */     private int bitField0_;
/*  7642:      */     public static final int TASK_ATTEMPT_ID_FIELD_NUMBER = 1;
/*  7643:      */     private MRProtos.TaskAttemptIdProto taskAttemptId_;
/*  7644:      */     
/*  7645:      */     public Parser<GetDiagnosticsRequestProto> getParserForType()
/*  7646:      */     {
/*  7647: 7275 */       return PARSER;
/*  7648:      */     }
/*  7649:      */     
/*  7650:      */     public boolean hasTaskAttemptId()
/*  7651:      */     {
/*  7652: 7286 */       return (this.bitField0_ & 0x1) == 1;
/*  7653:      */     }
/*  7654:      */     
/*  7655:      */     public MRProtos.TaskAttemptIdProto getTaskAttemptId()
/*  7656:      */     {
/*  7657: 7292 */       return this.taskAttemptId_;
/*  7658:      */     }
/*  7659:      */     
/*  7660:      */     public MRProtos.TaskAttemptIdProtoOrBuilder getTaskAttemptIdOrBuilder()
/*  7661:      */     {
/*  7662: 7298 */       return this.taskAttemptId_;
/*  7663:      */     }
/*  7664:      */     
/*  7665:      */     private void initFields()
/*  7666:      */     {
/*  7667: 7302 */       this.taskAttemptId_ = MRProtos.TaskAttemptIdProto.getDefaultInstance();
/*  7668:      */     }
/*  7669:      */     
/*  7670: 7304 */     private byte memoizedIsInitialized = -1;
/*  7671:      */     
/*  7672:      */     public final boolean isInitialized()
/*  7673:      */     {
/*  7674: 7306 */       byte isInitialized = this.memoizedIsInitialized;
/*  7675: 7307 */       if (isInitialized != -1) {
/*  7676: 7307 */         return isInitialized == 1;
/*  7677:      */       }
/*  7678: 7309 */       this.memoizedIsInitialized = 1;
/*  7679: 7310 */       return true;
/*  7680:      */     }
/*  7681:      */     
/*  7682:      */     public void writeTo(CodedOutputStream output)
/*  7683:      */       throws IOException
/*  7684:      */     {
/*  7685: 7315 */       getSerializedSize();
/*  7686: 7316 */       if ((this.bitField0_ & 0x1) == 1) {
/*  7687: 7317 */         output.writeMessage(1, this.taskAttemptId_);
/*  7688:      */       }
/*  7689: 7319 */       getUnknownFields().writeTo(output);
/*  7690:      */     }
/*  7691:      */     
/*  7692: 7322 */     private int memoizedSerializedSize = -1;
/*  7693:      */     private static final long serialVersionUID = 0L;
/*  7694:      */     
/*  7695:      */     public int getSerializedSize()
/*  7696:      */     {
/*  7697: 7324 */       int size = this.memoizedSerializedSize;
/*  7698: 7325 */       if (size != -1) {
/*  7699: 7325 */         return size;
/*  7700:      */       }
/*  7701: 7327 */       size = 0;
/*  7702: 7328 */       if ((this.bitField0_ & 0x1) == 1) {
/*  7703: 7329 */         size += CodedOutputStream.computeMessageSize(1, this.taskAttemptId_);
/*  7704:      */       }
/*  7705: 7332 */       size += getUnknownFields().getSerializedSize();
/*  7706: 7333 */       this.memoizedSerializedSize = size;
/*  7707: 7334 */       return size;
/*  7708:      */     }
/*  7709:      */     
/*  7710:      */     protected Object writeReplace()
/*  7711:      */       throws ObjectStreamException
/*  7712:      */     {
/*  7713: 7341 */       return super.writeReplace();
/*  7714:      */     }
/*  7715:      */     
/*  7716:      */     public boolean equals(Object obj)
/*  7717:      */     {
/*  7718: 7346 */       if (obj == this) {
/*  7719: 7347 */         return true;
/*  7720:      */       }
/*  7721: 7349 */       if (!(obj instanceof GetDiagnosticsRequestProto)) {
/*  7722: 7350 */         return super.equals(obj);
/*  7723:      */       }
/*  7724: 7352 */       GetDiagnosticsRequestProto other = (GetDiagnosticsRequestProto)obj;
/*  7725:      */       
/*  7726: 7354 */       boolean result = true;
/*  7727: 7355 */       result = (result) && (hasTaskAttemptId() == other.hasTaskAttemptId());
/*  7728: 7356 */       if (hasTaskAttemptId()) {
/*  7729: 7357 */         result = (result) && (getTaskAttemptId().equals(other.getTaskAttemptId()));
/*  7730:      */       }
/*  7731: 7360 */       result = (result) && (getUnknownFields().equals(other.getUnknownFields()));
/*  7732:      */       
/*  7733: 7362 */       return result;
/*  7734:      */     }
/*  7735:      */     
/*  7736: 7365 */     private int memoizedHashCode = 0;
/*  7737:      */     
/*  7738:      */     public int hashCode()
/*  7739:      */     {
/*  7740: 7368 */       if (this.memoizedHashCode != 0) {
/*  7741: 7369 */         return this.memoizedHashCode;
/*  7742:      */       }
/*  7743: 7371 */       int hash = 41;
/*  7744: 7372 */       hash = 19 * hash + getDescriptorForType().hashCode();
/*  7745: 7373 */       if (hasTaskAttemptId())
/*  7746:      */       {
/*  7747: 7374 */         hash = 37 * hash + 1;
/*  7748: 7375 */         hash = 53 * hash + getTaskAttemptId().hashCode();
/*  7749:      */       }
/*  7750: 7377 */       hash = 29 * hash + getUnknownFields().hashCode();
/*  7751: 7378 */       this.memoizedHashCode = hash;
/*  7752: 7379 */       return hash;
/*  7753:      */     }
/*  7754:      */     
/*  7755:      */     public static GetDiagnosticsRequestProto parseFrom(ByteString data)
/*  7756:      */       throws InvalidProtocolBufferException
/*  7757:      */     {
/*  7758: 7385 */       return (GetDiagnosticsRequestProto)PARSER.parseFrom(data);
/*  7759:      */     }
/*  7760:      */     
/*  7761:      */     public static GetDiagnosticsRequestProto parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry)
/*  7762:      */       throws InvalidProtocolBufferException
/*  7763:      */     {
/*  7764: 7391 */       return (GetDiagnosticsRequestProto)PARSER.parseFrom(data, extensionRegistry);
/*  7765:      */     }
/*  7766:      */     
/*  7767:      */     public static GetDiagnosticsRequestProto parseFrom(byte[] data)
/*  7768:      */       throws InvalidProtocolBufferException
/*  7769:      */     {
/*  7770: 7395 */       return (GetDiagnosticsRequestProto)PARSER.parseFrom(data);
/*  7771:      */     }
/*  7772:      */     
/*  7773:      */     public static GetDiagnosticsRequestProto parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry)
/*  7774:      */       throws InvalidProtocolBufferException
/*  7775:      */     {
/*  7776: 7401 */       return (GetDiagnosticsRequestProto)PARSER.parseFrom(data, extensionRegistry);
/*  7777:      */     }
/*  7778:      */     
/*  7779:      */     public static GetDiagnosticsRequestProto parseFrom(InputStream input)
/*  7780:      */       throws IOException
/*  7781:      */     {
/*  7782: 7405 */       return (GetDiagnosticsRequestProto)PARSER.parseFrom(input);
/*  7783:      */     }
/*  7784:      */     
/*  7785:      */     public static GetDiagnosticsRequestProto parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry)
/*  7786:      */       throws IOException
/*  7787:      */     {
/*  7788: 7411 */       return (GetDiagnosticsRequestProto)PARSER.parseFrom(input, extensionRegistry);
/*  7789:      */     }
/*  7790:      */     
/*  7791:      */     public static GetDiagnosticsRequestProto parseDelimitedFrom(InputStream input)
/*  7792:      */       throws IOException
/*  7793:      */     {
/*  7794: 7415 */       return (GetDiagnosticsRequestProto)PARSER.parseDelimitedFrom(input);
/*  7795:      */     }
/*  7796:      */     
/*  7797:      */     public static GetDiagnosticsRequestProto parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry)
/*  7798:      */       throws IOException
/*  7799:      */     {
/*  7800: 7421 */       return (GetDiagnosticsRequestProto)PARSER.parseDelimitedFrom(input, extensionRegistry);
/*  7801:      */     }
/*  7802:      */     
/*  7803:      */     public static GetDiagnosticsRequestProto parseFrom(CodedInputStream input)
/*  7804:      */       throws IOException
/*  7805:      */     {
/*  7806: 7426 */       return (GetDiagnosticsRequestProto)PARSER.parseFrom(input);
/*  7807:      */     }
/*  7808:      */     
/*  7809:      */     public static GetDiagnosticsRequestProto parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry)
/*  7810:      */       throws IOException
/*  7811:      */     {
/*  7812: 7432 */       return (GetDiagnosticsRequestProto)PARSER.parseFrom(input, extensionRegistry);
/*  7813:      */     }
/*  7814:      */     
/*  7815:      */     public static Builder newBuilder()
/*  7816:      */     {
/*  7817: 7435 */       return Builder.access$11400();
/*  7818:      */     }
/*  7819:      */     
/*  7820:      */     public Builder newBuilderForType()
/*  7821:      */     {
/*  7822: 7436 */       return newBuilder();
/*  7823:      */     }
/*  7824:      */     
/*  7825:      */     public static Builder newBuilder(GetDiagnosticsRequestProto prototype)
/*  7826:      */     {
/*  7827: 7438 */       return newBuilder().mergeFrom(prototype);
/*  7828:      */     }
/*  7829:      */     
/*  7830:      */     public Builder toBuilder()
/*  7831:      */     {
/*  7832: 7440 */       return newBuilder(this);
/*  7833:      */     }
/*  7834:      */     
/*  7835:      */     protected Builder newBuilderForType(GeneratedMessage.BuilderParent parent)
/*  7836:      */     {
/*  7837: 7445 */       Builder builder = new Builder(parent, null);
/*  7838: 7446 */       return builder;
/*  7839:      */     }
/*  7840:      */     
/*  7841:      */     public static final class Builder
/*  7842:      */       extends GeneratedMessage.Builder<Builder>
/*  7843:      */       implements MRServiceProtos.GetDiagnosticsRequestProtoOrBuilder
/*  7844:      */     {
/*  7845:      */       private int bitField0_;
/*  7846:      */       
/*  7847:      */       public static final Descriptors.Descriptor getDescriptor()
/*  7848:      */       {
/*  7849: 7456 */         return MRServiceProtos.internal_static_hadoop_mapreduce_GetDiagnosticsRequestProto_descriptor;
/*  7850:      */       }
/*  7851:      */       
/*  7852:      */       protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable()
/*  7853:      */       {
/*  7854: 7461 */         return MRServiceProtos.internal_static_hadoop_mapreduce_GetDiagnosticsRequestProto_fieldAccessorTable.ensureFieldAccessorsInitialized(MRServiceProtos.GetDiagnosticsRequestProto.class, Builder.class);
/*  7855:      */       }
/*  7856:      */       
/*  7857:      */       private Builder()
/*  7858:      */       {
/*  7859: 7468 */         maybeForceBuilderInitialization();
/*  7860:      */       }
/*  7861:      */       
/*  7862:      */       private Builder(GeneratedMessage.BuilderParent parent)
/*  7863:      */       {
/*  7864: 7473 */         super();
/*  7865: 7474 */         maybeForceBuilderInitialization();
/*  7866:      */       }
/*  7867:      */       
/*  7868:      */       private void maybeForceBuilderInitialization()
/*  7869:      */       {
/*  7870: 7477 */         if (MRServiceProtos.GetDiagnosticsRequestProto.alwaysUseFieldBuilders) {
/*  7871: 7478 */           getTaskAttemptIdFieldBuilder();
/*  7872:      */         }
/*  7873:      */       }
/*  7874:      */       
/*  7875:      */       private static Builder create()
/*  7876:      */       {
/*  7877: 7482 */         return new Builder();
/*  7878:      */       }
/*  7879:      */       
/*  7880:      */       public Builder clear()
/*  7881:      */       {
/*  7882: 7486 */         super.clear();
/*  7883: 7487 */         if (this.taskAttemptIdBuilder_ == null) {
/*  7884: 7488 */           this.taskAttemptId_ = MRProtos.TaskAttemptIdProto.getDefaultInstance();
/*  7885:      */         } else {
/*  7886: 7490 */           this.taskAttemptIdBuilder_.clear();
/*  7887:      */         }
/*  7888: 7492 */         this.bitField0_ &= 0xFFFFFFFE;
/*  7889: 7493 */         return this;
/*  7890:      */       }
/*  7891:      */       
/*  7892:      */       public Builder clone()
/*  7893:      */       {
/*  7894: 7497 */         return create().mergeFrom(buildPartial());
/*  7895:      */       }
/*  7896:      */       
/*  7897:      */       public Descriptors.Descriptor getDescriptorForType()
/*  7898:      */       {
/*  7899: 7502 */         return MRServiceProtos.internal_static_hadoop_mapreduce_GetDiagnosticsRequestProto_descriptor;
/*  7900:      */       }
/*  7901:      */       
/*  7902:      */       public MRServiceProtos.GetDiagnosticsRequestProto getDefaultInstanceForType()
/*  7903:      */       {
/*  7904: 7506 */         return MRServiceProtos.GetDiagnosticsRequestProto.getDefaultInstance();
/*  7905:      */       }
/*  7906:      */       
/*  7907:      */       public MRServiceProtos.GetDiagnosticsRequestProto build()
/*  7908:      */       {
/*  7909: 7510 */         MRServiceProtos.GetDiagnosticsRequestProto result = buildPartial();
/*  7910: 7511 */         if (!result.isInitialized()) {
/*  7911: 7512 */           throw newUninitializedMessageException(result);
/*  7912:      */         }
/*  7913: 7514 */         return result;
/*  7914:      */       }
/*  7915:      */       
/*  7916:      */       public MRServiceProtos.GetDiagnosticsRequestProto buildPartial()
/*  7917:      */       {
/*  7918: 7518 */         MRServiceProtos.GetDiagnosticsRequestProto result = new MRServiceProtos.GetDiagnosticsRequestProto(this, null);
/*  7919: 7519 */         int from_bitField0_ = this.bitField0_;
/*  7920: 7520 */         int to_bitField0_ = 0;
/*  7921: 7521 */         if ((from_bitField0_ & 0x1) == 1) {
/*  7922: 7522 */           to_bitField0_ |= 0x1;
/*  7923:      */         }
/*  7924: 7524 */         if (this.taskAttemptIdBuilder_ == null) {
/*  7925: 7525 */           result.taskAttemptId_ = this.taskAttemptId_;
/*  7926:      */         } else {
/*  7927: 7527 */           result.taskAttemptId_ = ((MRProtos.TaskAttemptIdProto)this.taskAttemptIdBuilder_.build());
/*  7928:      */         }
/*  7929: 7529 */         result.bitField0_ = to_bitField0_;
/*  7930: 7530 */         onBuilt();
/*  7931: 7531 */         return result;
/*  7932:      */       }
/*  7933:      */       
/*  7934:      */       public Builder mergeFrom(Message other)
/*  7935:      */       {
/*  7936: 7535 */         if ((other instanceof MRServiceProtos.GetDiagnosticsRequestProto)) {
/*  7937: 7536 */           return mergeFrom((MRServiceProtos.GetDiagnosticsRequestProto)other);
/*  7938:      */         }
/*  7939: 7538 */         super.mergeFrom(other);
/*  7940: 7539 */         return this;
/*  7941:      */       }
/*  7942:      */       
/*  7943:      */       public Builder mergeFrom(MRServiceProtos.GetDiagnosticsRequestProto other)
/*  7944:      */       {
/*  7945: 7544 */         if (other == MRServiceProtos.GetDiagnosticsRequestProto.getDefaultInstance()) {
/*  7946: 7544 */           return this;
/*  7947:      */         }
/*  7948: 7545 */         if (other.hasTaskAttemptId()) {
/*  7949: 7546 */           mergeTaskAttemptId(other.getTaskAttemptId());
/*  7950:      */         }
/*  7951: 7548 */         mergeUnknownFields(other.getUnknownFields());
/*  7952: 7549 */         return this;
/*  7953:      */       }
/*  7954:      */       
/*  7955:      */       public final boolean isInitialized()
/*  7956:      */       {
/*  7957: 7553 */         return true;
/*  7958:      */       }
/*  7959:      */       
/*  7960:      */       public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry)
/*  7961:      */         throws IOException
/*  7962:      */       {
/*  7963: 7560 */         MRServiceProtos.GetDiagnosticsRequestProto parsedMessage = null;
/*  7964:      */         try
/*  7965:      */         {
/*  7966: 7562 */           parsedMessage = (MRServiceProtos.GetDiagnosticsRequestProto)MRServiceProtos.GetDiagnosticsRequestProto.PARSER.parsePartialFrom(input, extensionRegistry);
/*  7967:      */         }
/*  7968:      */         catch (InvalidProtocolBufferException e)
/*  7969:      */         {
/*  7970: 7564 */           parsedMessage = (MRServiceProtos.GetDiagnosticsRequestProto)e.getUnfinishedMessage();
/*  7971: 7565 */           throw e;
/*  7972:      */         }
/*  7973:      */         finally
/*  7974:      */         {
/*  7975: 7567 */           if (parsedMessage != null) {
/*  7976: 7568 */             mergeFrom(parsedMessage);
/*  7977:      */           }
/*  7978:      */         }
/*  7979: 7571 */         return this;
/*  7980:      */       }
/*  7981:      */       
/*  7982: 7576 */       private MRProtos.TaskAttemptIdProto taskAttemptId_ = MRProtos.TaskAttemptIdProto.getDefaultInstance();
/*  7983:      */       private SingleFieldBuilder<MRProtos.TaskAttemptIdProto, MRProtos.TaskAttemptIdProto.Builder, MRProtos.TaskAttemptIdProtoOrBuilder> taskAttemptIdBuilder_;
/*  7984:      */       
/*  7985:      */       public boolean hasTaskAttemptId()
/*  7986:      */       {
/*  7987: 7583 */         return (this.bitField0_ & 0x1) == 1;
/*  7988:      */       }
/*  7989:      */       
/*  7990:      */       public MRProtos.TaskAttemptIdProto getTaskAttemptId()
/*  7991:      */       {
/*  7992: 7589 */         if (this.taskAttemptIdBuilder_ == null) {
/*  7993: 7590 */           return this.taskAttemptId_;
/*  7994:      */         }
/*  7995: 7592 */         return (MRProtos.TaskAttemptIdProto)this.taskAttemptIdBuilder_.getMessage();
/*  7996:      */       }
/*  7997:      */       
/*  7998:      */       public Builder setTaskAttemptId(MRProtos.TaskAttemptIdProto value)
/*  7999:      */       {
/*  8000: 7599 */         if (this.taskAttemptIdBuilder_ == null)
/*  8001:      */         {
/*  8002: 7600 */           if (value == null) {
/*  8003: 7601 */             throw new NullPointerException();
/*  8004:      */           }
/*  8005: 7603 */           this.taskAttemptId_ = value;
/*  8006: 7604 */           onChanged();
/*  8007:      */         }
/*  8008:      */         else
/*  8009:      */         {
/*  8010: 7606 */           this.taskAttemptIdBuilder_.setMessage(value);
/*  8011:      */         }
/*  8012: 7608 */         this.bitField0_ |= 0x1;
/*  8013: 7609 */         return this;
/*  8014:      */       }
/*  8015:      */       
/*  8016:      */       public Builder setTaskAttemptId(MRProtos.TaskAttemptIdProto.Builder builderForValue)
/*  8017:      */       {
/*  8018: 7616 */         if (this.taskAttemptIdBuilder_ == null)
/*  8019:      */         {
/*  8020: 7617 */           this.taskAttemptId_ = builderForValue.build();
/*  8021: 7618 */           onChanged();
/*  8022:      */         }
/*  8023:      */         else
/*  8024:      */         {
/*  8025: 7620 */           this.taskAttemptIdBuilder_.setMessage(builderForValue.build());
/*  8026:      */         }
/*  8027: 7622 */         this.bitField0_ |= 0x1;
/*  8028: 7623 */         return this;
/*  8029:      */       }
/*  8030:      */       
/*  8031:      */       public Builder mergeTaskAttemptId(MRProtos.TaskAttemptIdProto value)
/*  8032:      */       {
/*  8033: 7629 */         if (this.taskAttemptIdBuilder_ == null)
/*  8034:      */         {
/*  8035: 7630 */           if (((this.bitField0_ & 0x1) == 1) && (this.taskAttemptId_ != MRProtos.TaskAttemptIdProto.getDefaultInstance())) {
/*  8036: 7632 */             this.taskAttemptId_ = MRProtos.TaskAttemptIdProto.newBuilder(this.taskAttemptId_).mergeFrom(value).buildPartial();
/*  8037:      */           } else {
/*  8038: 7635 */             this.taskAttemptId_ = value;
/*  8039:      */           }
/*  8040: 7637 */           onChanged();
/*  8041:      */         }
/*  8042:      */         else
/*  8043:      */         {
/*  8044: 7639 */           this.taskAttemptIdBuilder_.mergeFrom(value);
/*  8045:      */         }
/*  8046: 7641 */         this.bitField0_ |= 0x1;
/*  8047: 7642 */         return this;
/*  8048:      */       }
/*  8049:      */       
/*  8050:      */       public Builder clearTaskAttemptId()
/*  8051:      */       {
/*  8052: 7648 */         if (this.taskAttemptIdBuilder_ == null)
/*  8053:      */         {
/*  8054: 7649 */           this.taskAttemptId_ = MRProtos.TaskAttemptIdProto.getDefaultInstance();
/*  8055: 7650 */           onChanged();
/*  8056:      */         }
/*  8057:      */         else
/*  8058:      */         {
/*  8059: 7652 */           this.taskAttemptIdBuilder_.clear();
/*  8060:      */         }
/*  8061: 7654 */         this.bitField0_ &= 0xFFFFFFFE;
/*  8062: 7655 */         return this;
/*  8063:      */       }
/*  8064:      */       
/*  8065:      */       public MRProtos.TaskAttemptIdProto.Builder getTaskAttemptIdBuilder()
/*  8066:      */       {
/*  8067: 7661 */         this.bitField0_ |= 0x1;
/*  8068: 7662 */         onChanged();
/*  8069: 7663 */         return (MRProtos.TaskAttemptIdProto.Builder)getTaskAttemptIdFieldBuilder().getBuilder();
/*  8070:      */       }
/*  8071:      */       
/*  8072:      */       public MRProtos.TaskAttemptIdProtoOrBuilder getTaskAttemptIdOrBuilder()
/*  8073:      */       {
/*  8074: 7669 */         if (this.taskAttemptIdBuilder_ != null) {
/*  8075: 7670 */           return (MRProtos.TaskAttemptIdProtoOrBuilder)this.taskAttemptIdBuilder_.getMessageOrBuilder();
/*  8076:      */         }
/*  8077: 7672 */         return this.taskAttemptId_;
/*  8078:      */       }
/*  8079:      */       
/*  8080:      */       private SingleFieldBuilder<MRProtos.TaskAttemptIdProto, MRProtos.TaskAttemptIdProto.Builder, MRProtos.TaskAttemptIdProtoOrBuilder> getTaskAttemptIdFieldBuilder()
/*  8081:      */       {
/*  8082: 7681 */         if (this.taskAttemptIdBuilder_ == null)
/*  8083:      */         {
/*  8084: 7682 */           this.taskAttemptIdBuilder_ = new SingleFieldBuilder(this.taskAttemptId_, getParentForChildren(), isClean());
/*  8085:      */           
/*  8086:      */ 
/*  8087:      */ 
/*  8088:      */ 
/*  8089: 7687 */           this.taskAttemptId_ = null;
/*  8090:      */         }
/*  8091: 7689 */         return this.taskAttemptIdBuilder_;
/*  8092:      */       }
/*  8093:      */     }
/*  8094:      */     
/*  8095:      */     static
/*  8096:      */     {
/*  8097: 7696 */       defaultInstance = new GetDiagnosticsRequestProto(true);
/*  8098: 7697 */       defaultInstance.initFields();
/*  8099:      */     }
/*  8100:      */   }
/*  8101:      */   
/*  8102:      */   public static abstract interface GetDiagnosticsResponseProtoOrBuilder
/*  8103:      */     extends MessageOrBuilder
/*  8104:      */   {
/*  8105:      */     public abstract List<String> getDiagnosticsList();
/*  8106:      */     
/*  8107:      */     public abstract int getDiagnosticsCount();
/*  8108:      */     
/*  8109:      */     public abstract String getDiagnostics(int paramInt);
/*  8110:      */     
/*  8111:      */     public abstract ByteString getDiagnosticsBytes(int paramInt);
/*  8112:      */   }
/*  8113:      */   
/*  8114:      */   public static final class GetDiagnosticsResponseProto
/*  8115:      */     extends GeneratedMessage
/*  8116:      */     implements MRServiceProtos.GetDiagnosticsResponseProtoOrBuilder
/*  8117:      */   {
/*  8118:      */     private static final GetDiagnosticsResponseProto defaultInstance;
/*  8119:      */     private final UnknownFieldSet unknownFields;
/*  8120:      */     
/*  8121:      */     private GetDiagnosticsResponseProto(GeneratedMessage.Builder<?> builder)
/*  8122:      */     {
/*  8123: 7734 */       super();
/*  8124: 7735 */       this.unknownFields = builder.getUnknownFields();
/*  8125:      */     }
/*  8126:      */     
/*  8127:      */     private GetDiagnosticsResponseProto(boolean noInit)
/*  8128:      */     {
/*  8129: 7737 */       this.unknownFields = UnknownFieldSet.getDefaultInstance();
/*  8130:      */     }
/*  8131:      */     
/*  8132:      */     public static GetDiagnosticsResponseProto getDefaultInstance()
/*  8133:      */     {
/*  8134: 7741 */       return defaultInstance;
/*  8135:      */     }
/*  8136:      */     
/*  8137:      */     public GetDiagnosticsResponseProto getDefaultInstanceForType()
/*  8138:      */     {
/*  8139: 7745 */       return defaultInstance;
/*  8140:      */     }
/*  8141:      */     
/*  8142:      */     public final UnknownFieldSet getUnknownFields()
/*  8143:      */     {
/*  8144: 7752 */       return this.unknownFields;
/*  8145:      */     }
/*  8146:      */     
/*  8147:      */     private GetDiagnosticsResponseProto(CodedInputStream input, ExtensionRegistryLite extensionRegistry)
/*  8148:      */       throws InvalidProtocolBufferException
/*  8149:      */     {
/*  8150: 7758 */       initFields();
/*  8151: 7759 */       int mutable_bitField0_ = 0;
/*  8152: 7760 */       UnknownFieldSet.Builder unknownFields = UnknownFieldSet.newBuilder();
/*  8153:      */       try
/*  8154:      */       {
/*  8155: 7763 */         boolean done = false;
/*  8156: 7764 */         while (!done)
/*  8157:      */         {
/*  8158: 7765 */           int tag = input.readTag();
/*  8159: 7766 */           switch (tag)
/*  8160:      */           {
/*  8161:      */           case 0: 
/*  8162: 7768 */             done = true;
/*  8163: 7769 */             break;
/*  8164:      */           default: 
/*  8165: 7771 */             if (!parseUnknownField(input, unknownFields, extensionRegistry, tag)) {
/*  8166: 7773 */               done = true;
/*  8167:      */             }
/*  8168:      */             break;
/*  8169:      */           case 10: 
/*  8170: 7778 */             if ((mutable_bitField0_ & 0x1) != 1)
/*  8171:      */             {
/*  8172: 7779 */               this.diagnostics_ = new LazyStringArrayList();
/*  8173: 7780 */               mutable_bitField0_ |= 0x1;
/*  8174:      */             }
/*  8175: 7782 */             this.diagnostics_.add(input.readBytes());
/*  8176:      */           }
/*  8177:      */         }
/*  8178:      */       }
/*  8179:      */       catch (InvalidProtocolBufferException e)
/*  8180:      */       {
/*  8181: 7788 */         throw e.setUnfinishedMessage(this);
/*  8182:      */       }
/*  8183:      */       catch (IOException e)
/*  8184:      */       {
/*  8185: 7790 */         throw new InvalidProtocolBufferException(e.getMessage()).setUnfinishedMessage(this);
/*  8186:      */       }
/*  8187:      */       finally
/*  8188:      */       {
/*  8189: 7793 */         if ((mutable_bitField0_ & 0x1) == 1) {
/*  8190: 7794 */           this.diagnostics_ = new UnmodifiableLazyStringList(this.diagnostics_);
/*  8191:      */         }
/*  8192: 7796 */         this.unknownFields = unknownFields.build();
/*  8193: 7797 */         makeExtensionsImmutable();
/*  8194:      */       }
/*  8195:      */     }
/*  8196:      */     
/*  8197:      */     public static final Descriptors.Descriptor getDescriptor()
/*  8198:      */     {
/*  8199: 7802 */       return MRServiceProtos.internal_static_hadoop_mapreduce_GetDiagnosticsResponseProto_descriptor;
/*  8200:      */     }
/*  8201:      */     
/*  8202:      */     protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable()
/*  8203:      */     {
/*  8204: 7807 */       return MRServiceProtos.internal_static_hadoop_mapreduce_GetDiagnosticsResponseProto_fieldAccessorTable.ensureFieldAccessorsInitialized(GetDiagnosticsResponseProto.class, Builder.class);
/*  8205:      */     }
/*  8206:      */     
/*  8207: 7812 */     public static Parser<GetDiagnosticsResponseProto> PARSER = new AbstractParser()
/*  8208:      */     {
/*  8209:      */       public MRServiceProtos.GetDiagnosticsResponseProto parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry)
/*  8210:      */         throws InvalidProtocolBufferException
/*  8211:      */       {
/*  8212: 7818 */         return new MRServiceProtos.GetDiagnosticsResponseProto(input, extensionRegistry, null);
/*  8213:      */       }
/*  8214:      */     };
/*  8215:      */     public static final int DIAGNOSTICS_FIELD_NUMBER = 1;
/*  8216:      */     private LazyStringList diagnostics_;
/*  8217:      */     
/*  8218:      */     public Parser<GetDiagnosticsResponseProto> getParserForType()
/*  8219:      */     {
/*  8220: 7824 */       return PARSER;
/*  8221:      */     }
/*  8222:      */     
/*  8223:      */     public List<String> getDiagnosticsList()
/*  8224:      */     {
/*  8225: 7835 */       return this.diagnostics_;
/*  8226:      */     }
/*  8227:      */     
/*  8228:      */     public int getDiagnosticsCount()
/*  8229:      */     {
/*  8230: 7841 */       return this.diagnostics_.size();
/*  8231:      */     }
/*  8232:      */     
/*  8233:      */     public String getDiagnostics(int index)
/*  8234:      */     {
/*  8235: 7847 */       return (String)this.diagnostics_.get(index);
/*  8236:      */     }
/*  8237:      */     
/*  8238:      */     public ByteString getDiagnosticsBytes(int index)
/*  8239:      */     {
/*  8240: 7854 */       return this.diagnostics_.getByteString(index);
/*  8241:      */     }
/*  8242:      */     
/*  8243:      */     private void initFields()
/*  8244:      */     {
/*  8245: 7858 */       this.diagnostics_ = LazyStringArrayList.EMPTY;
/*  8246:      */     }
/*  8247:      */     
/*  8248: 7860 */     private byte memoizedIsInitialized = -1;
/*  8249:      */     
/*  8250:      */     public final boolean isInitialized()
/*  8251:      */     {
/*  8252: 7862 */       byte isInitialized = this.memoizedIsInitialized;
/*  8253: 7863 */       if (isInitialized != -1) {
/*  8254: 7863 */         return isInitialized == 1;
/*  8255:      */       }
/*  8256: 7865 */       this.memoizedIsInitialized = 1;
/*  8257: 7866 */       return true;
/*  8258:      */     }
/*  8259:      */     
/*  8260:      */     public void writeTo(CodedOutputStream output)
/*  8261:      */       throws IOException
/*  8262:      */     {
/*  8263: 7871 */       getSerializedSize();
/*  8264: 7872 */       for (int i = 0; i < this.diagnostics_.size(); i++) {
/*  8265: 7873 */         output.writeBytes(1, this.diagnostics_.getByteString(i));
/*  8266:      */       }
/*  8267: 7875 */       getUnknownFields().writeTo(output);
/*  8268:      */     }
/*  8269:      */     
/*  8270: 7878 */     private int memoizedSerializedSize = -1;
/*  8271:      */     private static final long serialVersionUID = 0L;
/*  8272:      */     
/*  8273:      */     public int getSerializedSize()
/*  8274:      */     {
/*  8275: 7880 */       int size = this.memoizedSerializedSize;
/*  8276: 7881 */       if (size != -1) {
/*  8277: 7881 */         return size;
/*  8278:      */       }
/*  8279: 7883 */       size = 0;
/*  8280:      */       
/*  8281: 7885 */       int dataSize = 0;
/*  8282: 7886 */       for (int i = 0; i < this.diagnostics_.size(); i++) {
/*  8283: 7887 */         dataSize += CodedOutputStream.computeBytesSizeNoTag(this.diagnostics_.getByteString(i));
/*  8284:      */       }
/*  8285: 7890 */       size += dataSize;
/*  8286: 7891 */       size += 1 * getDiagnosticsList().size();
/*  8287:      */       
/*  8288: 7893 */       size += getUnknownFields().getSerializedSize();
/*  8289: 7894 */       this.memoizedSerializedSize = size;
/*  8290: 7895 */       return size;
/*  8291:      */     }
/*  8292:      */     
/*  8293:      */     protected Object writeReplace()
/*  8294:      */       throws ObjectStreamException
/*  8295:      */     {
/*  8296: 7902 */       return super.writeReplace();
/*  8297:      */     }
/*  8298:      */     
/*  8299:      */     public boolean equals(Object obj)
/*  8300:      */     {
/*  8301: 7907 */       if (obj == this) {
/*  8302: 7908 */         return true;
/*  8303:      */       }
/*  8304: 7910 */       if (!(obj instanceof GetDiagnosticsResponseProto)) {
/*  8305: 7911 */         return super.equals(obj);
/*  8306:      */       }
/*  8307: 7913 */       GetDiagnosticsResponseProto other = (GetDiagnosticsResponseProto)obj;
/*  8308:      */       
/*  8309: 7915 */       boolean result = true;
/*  8310: 7916 */       result = (result) && (getDiagnosticsList().equals(other.getDiagnosticsList()));
/*  8311:      */       
/*  8312: 7918 */       result = (result) && (getUnknownFields().equals(other.getUnknownFields()));
/*  8313:      */       
/*  8314: 7920 */       return result;
/*  8315:      */     }
/*  8316:      */     
/*  8317: 7923 */     private int memoizedHashCode = 0;
/*  8318:      */     
/*  8319:      */     public int hashCode()
/*  8320:      */     {
/*  8321: 7926 */       if (this.memoizedHashCode != 0) {
/*  8322: 7927 */         return this.memoizedHashCode;
/*  8323:      */       }
/*  8324: 7929 */       int hash = 41;
/*  8325: 7930 */       hash = 19 * hash + getDescriptorForType().hashCode();
/*  8326: 7931 */       if (getDiagnosticsCount() > 0)
/*  8327:      */       {
/*  8328: 7932 */         hash = 37 * hash + 1;
/*  8329: 7933 */         hash = 53 * hash + getDiagnosticsList().hashCode();
/*  8330:      */       }
/*  8331: 7935 */       hash = 29 * hash + getUnknownFields().hashCode();
/*  8332: 7936 */       this.memoizedHashCode = hash;
/*  8333: 7937 */       return hash;
/*  8334:      */     }
/*  8335:      */     
/*  8336:      */     public static GetDiagnosticsResponseProto parseFrom(ByteString data)
/*  8337:      */       throws InvalidProtocolBufferException
/*  8338:      */     {
/*  8339: 7943 */       return (GetDiagnosticsResponseProto)PARSER.parseFrom(data);
/*  8340:      */     }
/*  8341:      */     
/*  8342:      */     public static GetDiagnosticsResponseProto parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry)
/*  8343:      */       throws InvalidProtocolBufferException
/*  8344:      */     {
/*  8345: 7949 */       return (GetDiagnosticsResponseProto)PARSER.parseFrom(data, extensionRegistry);
/*  8346:      */     }
/*  8347:      */     
/*  8348:      */     public static GetDiagnosticsResponseProto parseFrom(byte[] data)
/*  8349:      */       throws InvalidProtocolBufferException
/*  8350:      */     {
/*  8351: 7953 */       return (GetDiagnosticsResponseProto)PARSER.parseFrom(data);
/*  8352:      */     }
/*  8353:      */     
/*  8354:      */     public static GetDiagnosticsResponseProto parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry)
/*  8355:      */       throws InvalidProtocolBufferException
/*  8356:      */     {
/*  8357: 7959 */       return (GetDiagnosticsResponseProto)PARSER.parseFrom(data, extensionRegistry);
/*  8358:      */     }
/*  8359:      */     
/*  8360:      */     public static GetDiagnosticsResponseProto parseFrom(InputStream input)
/*  8361:      */       throws IOException
/*  8362:      */     {
/*  8363: 7963 */       return (GetDiagnosticsResponseProto)PARSER.parseFrom(input);
/*  8364:      */     }
/*  8365:      */     
/*  8366:      */     public static GetDiagnosticsResponseProto parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry)
/*  8367:      */       throws IOException
/*  8368:      */     {
/*  8369: 7969 */       return (GetDiagnosticsResponseProto)PARSER.parseFrom(input, extensionRegistry);
/*  8370:      */     }
/*  8371:      */     
/*  8372:      */     public static GetDiagnosticsResponseProto parseDelimitedFrom(InputStream input)
/*  8373:      */       throws IOException
/*  8374:      */     {
/*  8375: 7973 */       return (GetDiagnosticsResponseProto)PARSER.parseDelimitedFrom(input);
/*  8376:      */     }
/*  8377:      */     
/*  8378:      */     public static GetDiagnosticsResponseProto parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry)
/*  8379:      */       throws IOException
/*  8380:      */     {
/*  8381: 7979 */       return (GetDiagnosticsResponseProto)PARSER.parseDelimitedFrom(input, extensionRegistry);
/*  8382:      */     }
/*  8383:      */     
/*  8384:      */     public static GetDiagnosticsResponseProto parseFrom(CodedInputStream input)
/*  8385:      */       throws IOException
/*  8386:      */     {
/*  8387: 7984 */       return (GetDiagnosticsResponseProto)PARSER.parseFrom(input);
/*  8388:      */     }
/*  8389:      */     
/*  8390:      */     public static GetDiagnosticsResponseProto parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry)
/*  8391:      */       throws IOException
/*  8392:      */     {
/*  8393: 7990 */       return (GetDiagnosticsResponseProto)PARSER.parseFrom(input, extensionRegistry);
/*  8394:      */     }
/*  8395:      */     
/*  8396:      */     public static Builder newBuilder()
/*  8397:      */     {
/*  8398: 7993 */       return Builder.access$12300();
/*  8399:      */     }
/*  8400:      */     
/*  8401:      */     public Builder newBuilderForType()
/*  8402:      */     {
/*  8403: 7994 */       return newBuilder();
/*  8404:      */     }
/*  8405:      */     
/*  8406:      */     public static Builder newBuilder(GetDiagnosticsResponseProto prototype)
/*  8407:      */     {
/*  8408: 7996 */       return newBuilder().mergeFrom(prototype);
/*  8409:      */     }
/*  8410:      */     
/*  8411:      */     public Builder toBuilder()
/*  8412:      */     {
/*  8413: 7998 */       return newBuilder(this);
/*  8414:      */     }
/*  8415:      */     
/*  8416:      */     protected Builder newBuilderForType(GeneratedMessage.BuilderParent parent)
/*  8417:      */     {
/*  8418: 8003 */       Builder builder = new Builder(parent, null);
/*  8419: 8004 */       return builder;
/*  8420:      */     }
/*  8421:      */     
/*  8422:      */     public static final class Builder
/*  8423:      */       extends GeneratedMessage.Builder<Builder>
/*  8424:      */       implements MRServiceProtos.GetDiagnosticsResponseProtoOrBuilder
/*  8425:      */     {
/*  8426:      */       private int bitField0_;
/*  8427:      */       
/*  8428:      */       public static final Descriptors.Descriptor getDescriptor()
/*  8429:      */       {
/*  8430: 8014 */         return MRServiceProtos.internal_static_hadoop_mapreduce_GetDiagnosticsResponseProto_descriptor;
/*  8431:      */       }
/*  8432:      */       
/*  8433:      */       protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable()
/*  8434:      */       {
/*  8435: 8019 */         return MRServiceProtos.internal_static_hadoop_mapreduce_GetDiagnosticsResponseProto_fieldAccessorTable.ensureFieldAccessorsInitialized(MRServiceProtos.GetDiagnosticsResponseProto.class, Builder.class);
/*  8436:      */       }
/*  8437:      */       
/*  8438:      */       private Builder()
/*  8439:      */       {
/*  8440: 8026 */         maybeForceBuilderInitialization();
/*  8441:      */       }
/*  8442:      */       
/*  8443:      */       private Builder(GeneratedMessage.BuilderParent parent)
/*  8444:      */       {
/*  8445: 8031 */         super();
/*  8446: 8032 */         maybeForceBuilderInitialization();
/*  8447:      */       }
/*  8448:      */       
/*  8449:      */       private void maybeForceBuilderInitialization()
/*  8450:      */       {
/*  8451: 8035 */         if (MRServiceProtos.GetDiagnosticsResponseProto.alwaysUseFieldBuilders) {}
/*  8452:      */       }
/*  8453:      */       
/*  8454:      */       private static Builder create()
/*  8455:      */       {
/*  8456: 8039 */         return new Builder();
/*  8457:      */       }
/*  8458:      */       
/*  8459:      */       public Builder clear()
/*  8460:      */       {
/*  8461: 8043 */         super.clear();
/*  8462: 8044 */         this.diagnostics_ = LazyStringArrayList.EMPTY;
/*  8463: 8045 */         this.bitField0_ &= 0xFFFFFFFE;
/*  8464: 8046 */         return this;
/*  8465:      */       }
/*  8466:      */       
/*  8467:      */       public Builder clone()
/*  8468:      */       {
/*  8469: 8050 */         return create().mergeFrom(buildPartial());
/*  8470:      */       }
/*  8471:      */       
/*  8472:      */       public Descriptors.Descriptor getDescriptorForType()
/*  8473:      */       {
/*  8474: 8055 */         return MRServiceProtos.internal_static_hadoop_mapreduce_GetDiagnosticsResponseProto_descriptor;
/*  8475:      */       }
/*  8476:      */       
/*  8477:      */       public MRServiceProtos.GetDiagnosticsResponseProto getDefaultInstanceForType()
/*  8478:      */       {
/*  8479: 8059 */         return MRServiceProtos.GetDiagnosticsResponseProto.getDefaultInstance();
/*  8480:      */       }
/*  8481:      */       
/*  8482:      */       public MRServiceProtos.GetDiagnosticsResponseProto build()
/*  8483:      */       {
/*  8484: 8063 */         MRServiceProtos.GetDiagnosticsResponseProto result = buildPartial();
/*  8485: 8064 */         if (!result.isInitialized()) {
/*  8486: 8065 */           throw newUninitializedMessageException(result);
/*  8487:      */         }
/*  8488: 8067 */         return result;
/*  8489:      */       }
/*  8490:      */       
/*  8491:      */       public MRServiceProtos.GetDiagnosticsResponseProto buildPartial()
/*  8492:      */       {
/*  8493: 8071 */         MRServiceProtos.GetDiagnosticsResponseProto result = new MRServiceProtos.GetDiagnosticsResponseProto(this, null);
/*  8494: 8072 */         int from_bitField0_ = this.bitField0_;
/*  8495: 8073 */         if ((this.bitField0_ & 0x1) == 1)
/*  8496:      */         {
/*  8497: 8074 */           this.diagnostics_ = new UnmodifiableLazyStringList(this.diagnostics_);
/*  8498:      */           
/*  8499: 8076 */           this.bitField0_ &= 0xFFFFFFFE;
/*  8500:      */         }
/*  8501: 8078 */         result.diagnostics_ = this.diagnostics_;
/*  8502: 8079 */         onBuilt();
/*  8503: 8080 */         return result;
/*  8504:      */       }
/*  8505:      */       
/*  8506:      */       public Builder mergeFrom(Message other)
/*  8507:      */       {
/*  8508: 8084 */         if ((other instanceof MRServiceProtos.GetDiagnosticsResponseProto)) {
/*  8509: 8085 */           return mergeFrom((MRServiceProtos.GetDiagnosticsResponseProto)other);
/*  8510:      */         }
/*  8511: 8087 */         super.mergeFrom(other);
/*  8512: 8088 */         return this;
/*  8513:      */       }
/*  8514:      */       
/*  8515:      */       public Builder mergeFrom(MRServiceProtos.GetDiagnosticsResponseProto other)
/*  8516:      */       {
/*  8517: 8093 */         if (other == MRServiceProtos.GetDiagnosticsResponseProto.getDefaultInstance()) {
/*  8518: 8093 */           return this;
/*  8519:      */         }
/*  8520: 8094 */         if (!other.diagnostics_.isEmpty())
/*  8521:      */         {
/*  8522: 8095 */           if (this.diagnostics_.isEmpty())
/*  8523:      */           {
/*  8524: 8096 */             this.diagnostics_ = other.diagnostics_;
/*  8525: 8097 */             this.bitField0_ &= 0xFFFFFFFE;
/*  8526:      */           }
/*  8527:      */           else
/*  8528:      */           {
/*  8529: 8099 */             ensureDiagnosticsIsMutable();
/*  8530: 8100 */             this.diagnostics_.addAll(other.diagnostics_);
/*  8531:      */           }
/*  8532: 8102 */           onChanged();
/*  8533:      */         }
/*  8534: 8104 */         mergeUnknownFields(other.getUnknownFields());
/*  8535: 8105 */         return this;
/*  8536:      */       }
/*  8537:      */       
/*  8538:      */       public final boolean isInitialized()
/*  8539:      */       {
/*  8540: 8109 */         return true;
/*  8541:      */       }
/*  8542:      */       
/*  8543:      */       public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry)
/*  8544:      */         throws IOException
/*  8545:      */       {
/*  8546: 8116 */         MRServiceProtos.GetDiagnosticsResponseProto parsedMessage = null;
/*  8547:      */         try
/*  8548:      */         {
/*  8549: 8118 */           parsedMessage = (MRServiceProtos.GetDiagnosticsResponseProto)MRServiceProtos.GetDiagnosticsResponseProto.PARSER.parsePartialFrom(input, extensionRegistry);
/*  8550:      */         }
/*  8551:      */         catch (InvalidProtocolBufferException e)
/*  8552:      */         {
/*  8553: 8120 */           parsedMessage = (MRServiceProtos.GetDiagnosticsResponseProto)e.getUnfinishedMessage();
/*  8554: 8121 */           throw e;
/*  8555:      */         }
/*  8556:      */         finally
/*  8557:      */         {
/*  8558: 8123 */           if (parsedMessage != null) {
/*  8559: 8124 */             mergeFrom(parsedMessage);
/*  8560:      */           }
/*  8561:      */         }
/*  8562: 8127 */         return this;
/*  8563:      */       }
/*  8564:      */       
/*  8565: 8132 */       private LazyStringList diagnostics_ = LazyStringArrayList.EMPTY;
/*  8566:      */       
/*  8567:      */       private void ensureDiagnosticsIsMutable()
/*  8568:      */       {
/*  8569: 8134 */         if ((this.bitField0_ & 0x1) != 1)
/*  8570:      */         {
/*  8571: 8135 */           this.diagnostics_ = new LazyStringArrayList(this.diagnostics_);
/*  8572: 8136 */           this.bitField0_ |= 0x1;
/*  8573:      */         }
/*  8574:      */       }
/*  8575:      */       
/*  8576:      */       public List<String> getDiagnosticsList()
/*  8577:      */       {
/*  8578: 8144 */         return Collections.unmodifiableList(this.diagnostics_);
/*  8579:      */       }
/*  8580:      */       
/*  8581:      */       public int getDiagnosticsCount()
/*  8582:      */       {
/*  8583: 8150 */         return this.diagnostics_.size();
/*  8584:      */       }
/*  8585:      */       
/*  8586:      */       public String getDiagnostics(int index)
/*  8587:      */       {
/*  8588: 8156 */         return (String)this.diagnostics_.get(index);
/*  8589:      */       }
/*  8590:      */       
/*  8591:      */       public ByteString getDiagnosticsBytes(int index)
/*  8592:      */       {
/*  8593: 8163 */         return this.diagnostics_.getByteString(index);
/*  8594:      */       }
/*  8595:      */       
/*  8596:      */       public Builder setDiagnostics(int index, String value)
/*  8597:      */       {
/*  8598: 8170 */         if (value == null) {
/*  8599: 8171 */           throw new NullPointerException();
/*  8600:      */         }
/*  8601: 8173 */         ensureDiagnosticsIsMutable();
/*  8602: 8174 */         this.diagnostics_.set(index, value);
/*  8603: 8175 */         onChanged();
/*  8604: 8176 */         return this;
/*  8605:      */       }
/*  8606:      */       
/*  8607:      */       public Builder addDiagnostics(String value)
/*  8608:      */       {
/*  8609: 8183 */         if (value == null) {
/*  8610: 8184 */           throw new NullPointerException();
/*  8611:      */         }
/*  8612: 8186 */         ensureDiagnosticsIsMutable();
/*  8613: 8187 */         this.diagnostics_.add(value);
/*  8614: 8188 */         onChanged();
/*  8615: 8189 */         return this;
/*  8616:      */       }
/*  8617:      */       
/*  8618:      */       public Builder addAllDiagnostics(Iterable<String> values)
/*  8619:      */       {
/*  8620: 8196 */         ensureDiagnosticsIsMutable();
/*  8621: 8197 */         GeneratedMessage.Builder.addAll(values, this.diagnostics_);
/*  8622: 8198 */         onChanged();
/*  8623: 8199 */         return this;
/*  8624:      */       }
/*  8625:      */       
/*  8626:      */       public Builder clearDiagnostics()
/*  8627:      */       {
/*  8628: 8205 */         this.diagnostics_ = LazyStringArrayList.EMPTY;
/*  8629: 8206 */         this.bitField0_ &= 0xFFFFFFFE;
/*  8630: 8207 */         onChanged();
/*  8631: 8208 */         return this;
/*  8632:      */       }
/*  8633:      */       
/*  8634:      */       public Builder addDiagnosticsBytes(ByteString value)
/*  8635:      */       {
/*  8636: 8215 */         if (value == null) {
/*  8637: 8216 */           throw new NullPointerException();
/*  8638:      */         }
/*  8639: 8218 */         ensureDiagnosticsIsMutable();
/*  8640: 8219 */         this.diagnostics_.add(value);
/*  8641: 8220 */         onChanged();
/*  8642: 8221 */         return this;
/*  8643:      */       }
/*  8644:      */     }
/*  8645:      */     
/*  8646:      */     static
/*  8647:      */     {
/*  8648: 8228 */       defaultInstance = new GetDiagnosticsResponseProto(true);
/*  8649: 8229 */       defaultInstance.initFields();
/*  8650:      */     }
/*  8651:      */   }
/*  8652:      */   
/*  8653:      */   public static abstract interface KillJobRequestProtoOrBuilder
/*  8654:      */     extends MessageOrBuilder
/*  8655:      */   {
/*  8656:      */     public abstract boolean hasJobId();
/*  8657:      */     
/*  8658:      */     public abstract MRProtos.JobIdProto getJobId();
/*  8659:      */     
/*  8660:      */     public abstract MRProtos.JobIdProtoOrBuilder getJobIdOrBuilder();
/*  8661:      */   }
/*  8662:      */   
/*  8663:      */   public static final class KillJobRequestProto
/*  8664:      */     extends GeneratedMessage
/*  8665:      */     implements MRServiceProtos.KillJobRequestProtoOrBuilder
/*  8666:      */   {
/*  8667:      */     private static final KillJobRequestProto defaultInstance;
/*  8668:      */     private final UnknownFieldSet unknownFields;
/*  8669:      */     
/*  8670:      */     private KillJobRequestProto(GeneratedMessage.Builder<?> builder)
/*  8671:      */     {
/*  8672: 8260 */       super();
/*  8673: 8261 */       this.unknownFields = builder.getUnknownFields();
/*  8674:      */     }
/*  8675:      */     
/*  8676:      */     private KillJobRequestProto(boolean noInit)
/*  8677:      */     {
/*  8678: 8263 */       this.unknownFields = UnknownFieldSet.getDefaultInstance();
/*  8679:      */     }
/*  8680:      */     
/*  8681:      */     public static KillJobRequestProto getDefaultInstance()
/*  8682:      */     {
/*  8683: 8267 */       return defaultInstance;
/*  8684:      */     }
/*  8685:      */     
/*  8686:      */     public KillJobRequestProto getDefaultInstanceForType()
/*  8687:      */     {
/*  8688: 8271 */       return defaultInstance;
/*  8689:      */     }
/*  8690:      */     
/*  8691:      */     public final UnknownFieldSet getUnknownFields()
/*  8692:      */     {
/*  8693: 8278 */       return this.unknownFields;
/*  8694:      */     }
/*  8695:      */     
/*  8696:      */     private KillJobRequestProto(CodedInputStream input, ExtensionRegistryLite extensionRegistry)
/*  8697:      */       throws InvalidProtocolBufferException
/*  8698:      */     {
/*  8699: 8284 */       initFields();
/*  8700: 8285 */       int mutable_bitField0_ = 0;
/*  8701: 8286 */       UnknownFieldSet.Builder unknownFields = UnknownFieldSet.newBuilder();
/*  8702:      */       try
/*  8703:      */       {
/*  8704: 8289 */         boolean done = false;
/*  8705: 8290 */         while (!done)
/*  8706:      */         {
/*  8707: 8291 */           int tag = input.readTag();
/*  8708: 8292 */           switch (tag)
/*  8709:      */           {
/*  8710:      */           case 0: 
/*  8711: 8294 */             done = true;
/*  8712: 8295 */             break;
/*  8713:      */           default: 
/*  8714: 8297 */             if (!parseUnknownField(input, unknownFields, extensionRegistry, tag)) {
/*  8715: 8299 */               done = true;
/*  8716:      */             }
/*  8717:      */             break;
/*  8718:      */           case 10: 
/*  8719: 8304 */             MRProtos.JobIdProto.Builder subBuilder = null;
/*  8720: 8305 */             if ((this.bitField0_ & 0x1) == 1) {
/*  8721: 8306 */               subBuilder = this.jobId_.toBuilder();
/*  8722:      */             }
/*  8723: 8308 */             this.jobId_ = ((MRProtos.JobIdProto)input.readMessage(MRProtos.JobIdProto.PARSER, extensionRegistry));
/*  8724: 8309 */             if (subBuilder != null)
/*  8725:      */             {
/*  8726: 8310 */               subBuilder.mergeFrom(this.jobId_);
/*  8727: 8311 */               this.jobId_ = subBuilder.buildPartial();
/*  8728:      */             }
/*  8729: 8313 */             this.bitField0_ |= 0x1;
/*  8730:      */           }
/*  8731:      */         }
/*  8732:      */       }
/*  8733:      */       catch (InvalidProtocolBufferException e)
/*  8734:      */       {
/*  8735: 8319 */         throw e.setUnfinishedMessage(this);
/*  8736:      */       }
/*  8737:      */       catch (IOException e)
/*  8738:      */       {
/*  8739: 8321 */         throw new InvalidProtocolBufferException(e.getMessage()).setUnfinishedMessage(this);
/*  8740:      */       }
/*  8741:      */       finally
/*  8742:      */       {
/*  8743: 8324 */         this.unknownFields = unknownFields.build();
/*  8744: 8325 */         makeExtensionsImmutable();
/*  8745:      */       }
/*  8746:      */     }
/*  8747:      */     
/*  8748:      */     public static final Descriptors.Descriptor getDescriptor()
/*  8749:      */     {
/*  8750: 8330 */       return MRServiceProtos.internal_static_hadoop_mapreduce_KillJobRequestProto_descriptor;
/*  8751:      */     }
/*  8752:      */     
/*  8753:      */     protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable()
/*  8754:      */     {
/*  8755: 8335 */       return MRServiceProtos.internal_static_hadoop_mapreduce_KillJobRequestProto_fieldAccessorTable.ensureFieldAccessorsInitialized(KillJobRequestProto.class, Builder.class);
/*  8756:      */     }
/*  8757:      */     
/*  8758: 8340 */     public static Parser<KillJobRequestProto> PARSER = new AbstractParser()
/*  8759:      */     {
/*  8760:      */       public MRServiceProtos.KillJobRequestProto parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry)
/*  8761:      */         throws InvalidProtocolBufferException
/*  8762:      */       {
/*  8763: 8346 */         return new MRServiceProtos.KillJobRequestProto(input, extensionRegistry, null);
/*  8764:      */       }
/*  8765:      */     };
/*  8766:      */     private int bitField0_;
/*  8767:      */     public static final int JOB_ID_FIELD_NUMBER = 1;
/*  8768:      */     private MRProtos.JobIdProto jobId_;
/*  8769:      */     
/*  8770:      */     public Parser<KillJobRequestProto> getParserForType()
/*  8771:      */     {
/*  8772: 8352 */       return PARSER;
/*  8773:      */     }
/*  8774:      */     
/*  8775:      */     public boolean hasJobId()
/*  8776:      */     {
/*  8777: 8363 */       return (this.bitField0_ & 0x1) == 1;
/*  8778:      */     }
/*  8779:      */     
/*  8780:      */     public MRProtos.JobIdProto getJobId()
/*  8781:      */     {
/*  8782: 8369 */       return this.jobId_;
/*  8783:      */     }
/*  8784:      */     
/*  8785:      */     public MRProtos.JobIdProtoOrBuilder getJobIdOrBuilder()
/*  8786:      */     {
/*  8787: 8375 */       return this.jobId_;
/*  8788:      */     }
/*  8789:      */     
/*  8790:      */     private void initFields()
/*  8791:      */     {
/*  8792: 8379 */       this.jobId_ = MRProtos.JobIdProto.getDefaultInstance();
/*  8793:      */     }
/*  8794:      */     
/*  8795: 8381 */     private byte memoizedIsInitialized = -1;
/*  8796:      */     
/*  8797:      */     public final boolean isInitialized()
/*  8798:      */     {
/*  8799: 8383 */       byte isInitialized = this.memoizedIsInitialized;
/*  8800: 8384 */       if (isInitialized != -1) {
/*  8801: 8384 */         return isInitialized == 1;
/*  8802:      */       }
/*  8803: 8386 */       this.memoizedIsInitialized = 1;
/*  8804: 8387 */       return true;
/*  8805:      */     }
/*  8806:      */     
/*  8807:      */     public void writeTo(CodedOutputStream output)
/*  8808:      */       throws IOException
/*  8809:      */     {
/*  8810: 8392 */       getSerializedSize();
/*  8811: 8393 */       if ((this.bitField0_ & 0x1) == 1) {
/*  8812: 8394 */         output.writeMessage(1, this.jobId_);
/*  8813:      */       }
/*  8814: 8396 */       getUnknownFields().writeTo(output);
/*  8815:      */     }
/*  8816:      */     
/*  8817: 8399 */     private int memoizedSerializedSize = -1;
/*  8818:      */     private static final long serialVersionUID = 0L;
/*  8819:      */     
/*  8820:      */     public int getSerializedSize()
/*  8821:      */     {
/*  8822: 8401 */       int size = this.memoizedSerializedSize;
/*  8823: 8402 */       if (size != -1) {
/*  8824: 8402 */         return size;
/*  8825:      */       }
/*  8826: 8404 */       size = 0;
/*  8827: 8405 */       if ((this.bitField0_ & 0x1) == 1) {
/*  8828: 8406 */         size += CodedOutputStream.computeMessageSize(1, this.jobId_);
/*  8829:      */       }
/*  8830: 8409 */       size += getUnknownFields().getSerializedSize();
/*  8831: 8410 */       this.memoizedSerializedSize = size;
/*  8832: 8411 */       return size;
/*  8833:      */     }
/*  8834:      */     
/*  8835:      */     protected Object writeReplace()
/*  8836:      */       throws ObjectStreamException
/*  8837:      */     {
/*  8838: 8418 */       return super.writeReplace();
/*  8839:      */     }
/*  8840:      */     
/*  8841:      */     public boolean equals(Object obj)
/*  8842:      */     {
/*  8843: 8423 */       if (obj == this) {
/*  8844: 8424 */         return true;
/*  8845:      */       }
/*  8846: 8426 */       if (!(obj instanceof KillJobRequestProto)) {
/*  8847: 8427 */         return super.equals(obj);
/*  8848:      */       }
/*  8849: 8429 */       KillJobRequestProto other = (KillJobRequestProto)obj;
/*  8850:      */       
/*  8851: 8431 */       boolean result = true;
/*  8852: 8432 */       result = (result) && (hasJobId() == other.hasJobId());
/*  8853: 8433 */       if (hasJobId()) {
/*  8854: 8434 */         result = (result) && (getJobId().equals(other.getJobId()));
/*  8855:      */       }
/*  8856: 8437 */       result = (result) && (getUnknownFields().equals(other.getUnknownFields()));
/*  8857:      */       
/*  8858: 8439 */       return result;
/*  8859:      */     }
/*  8860:      */     
/*  8861: 8442 */     private int memoizedHashCode = 0;
/*  8862:      */     
/*  8863:      */     public int hashCode()
/*  8864:      */     {
/*  8865: 8445 */       if (this.memoizedHashCode != 0) {
/*  8866: 8446 */         return this.memoizedHashCode;
/*  8867:      */       }
/*  8868: 8448 */       int hash = 41;
/*  8869: 8449 */       hash = 19 * hash + getDescriptorForType().hashCode();
/*  8870: 8450 */       if (hasJobId())
/*  8871:      */       {
/*  8872: 8451 */         hash = 37 * hash + 1;
/*  8873: 8452 */         hash = 53 * hash + getJobId().hashCode();
/*  8874:      */       }
/*  8875: 8454 */       hash = 29 * hash + getUnknownFields().hashCode();
/*  8876: 8455 */       this.memoizedHashCode = hash;
/*  8877: 8456 */       return hash;
/*  8878:      */     }
/*  8879:      */     
/*  8880:      */     public static KillJobRequestProto parseFrom(ByteString data)
/*  8881:      */       throws InvalidProtocolBufferException
/*  8882:      */     {
/*  8883: 8462 */       return (KillJobRequestProto)PARSER.parseFrom(data);
/*  8884:      */     }
/*  8885:      */     
/*  8886:      */     public static KillJobRequestProto parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry)
/*  8887:      */       throws InvalidProtocolBufferException
/*  8888:      */     {
/*  8889: 8468 */       return (KillJobRequestProto)PARSER.parseFrom(data, extensionRegistry);
/*  8890:      */     }
/*  8891:      */     
/*  8892:      */     public static KillJobRequestProto parseFrom(byte[] data)
/*  8893:      */       throws InvalidProtocolBufferException
/*  8894:      */     {
/*  8895: 8472 */       return (KillJobRequestProto)PARSER.parseFrom(data);
/*  8896:      */     }
/*  8897:      */     
/*  8898:      */     public static KillJobRequestProto parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry)
/*  8899:      */       throws InvalidProtocolBufferException
/*  8900:      */     {
/*  8901: 8478 */       return (KillJobRequestProto)PARSER.parseFrom(data, extensionRegistry);
/*  8902:      */     }
/*  8903:      */     
/*  8904:      */     public static KillJobRequestProto parseFrom(InputStream input)
/*  8905:      */       throws IOException
/*  8906:      */     {
/*  8907: 8482 */       return (KillJobRequestProto)PARSER.parseFrom(input);
/*  8908:      */     }
/*  8909:      */     
/*  8910:      */     public static KillJobRequestProto parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry)
/*  8911:      */       throws IOException
/*  8912:      */     {
/*  8913: 8488 */       return (KillJobRequestProto)PARSER.parseFrom(input, extensionRegistry);
/*  8914:      */     }
/*  8915:      */     
/*  8916:      */     public static KillJobRequestProto parseDelimitedFrom(InputStream input)
/*  8917:      */       throws IOException
/*  8918:      */     {
/*  8919: 8492 */       return (KillJobRequestProto)PARSER.parseDelimitedFrom(input);
/*  8920:      */     }
/*  8921:      */     
/*  8922:      */     public static KillJobRequestProto parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry)
/*  8923:      */       throws IOException
/*  8924:      */     {
/*  8925: 8498 */       return (KillJobRequestProto)PARSER.parseDelimitedFrom(input, extensionRegistry);
/*  8926:      */     }
/*  8927:      */     
/*  8928:      */     public static KillJobRequestProto parseFrom(CodedInputStream input)
/*  8929:      */       throws IOException
/*  8930:      */     {
/*  8931: 8503 */       return (KillJobRequestProto)PARSER.parseFrom(input);
/*  8932:      */     }
/*  8933:      */     
/*  8934:      */     public static KillJobRequestProto parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry)
/*  8935:      */       throws IOException
/*  8936:      */     {
/*  8937: 8509 */       return (KillJobRequestProto)PARSER.parseFrom(input, extensionRegistry);
/*  8938:      */     }
/*  8939:      */     
/*  8940:      */     public static Builder newBuilder()
/*  8941:      */     {
/*  8942: 8512 */       return Builder.access$13100();
/*  8943:      */     }
/*  8944:      */     
/*  8945:      */     public Builder newBuilderForType()
/*  8946:      */     {
/*  8947: 8513 */       return newBuilder();
/*  8948:      */     }
/*  8949:      */     
/*  8950:      */     public static Builder newBuilder(KillJobRequestProto prototype)
/*  8951:      */     {
/*  8952: 8515 */       return newBuilder().mergeFrom(prototype);
/*  8953:      */     }
/*  8954:      */     
/*  8955:      */     public Builder toBuilder()
/*  8956:      */     {
/*  8957: 8517 */       return newBuilder(this);
/*  8958:      */     }
/*  8959:      */     
/*  8960:      */     protected Builder newBuilderForType(GeneratedMessage.BuilderParent parent)
/*  8961:      */     {
/*  8962: 8522 */       Builder builder = new Builder(parent, null);
/*  8963: 8523 */       return builder;
/*  8964:      */     }
/*  8965:      */     
/*  8966:      */     public static final class Builder
/*  8967:      */       extends GeneratedMessage.Builder<Builder>
/*  8968:      */       implements MRServiceProtos.KillJobRequestProtoOrBuilder
/*  8969:      */     {
/*  8970:      */       private int bitField0_;
/*  8971:      */       
/*  8972:      */       public static final Descriptors.Descriptor getDescriptor()
/*  8973:      */       {
/*  8974: 8533 */         return MRServiceProtos.internal_static_hadoop_mapreduce_KillJobRequestProto_descriptor;
/*  8975:      */       }
/*  8976:      */       
/*  8977:      */       protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable()
/*  8978:      */       {
/*  8979: 8538 */         return MRServiceProtos.internal_static_hadoop_mapreduce_KillJobRequestProto_fieldAccessorTable.ensureFieldAccessorsInitialized(MRServiceProtos.KillJobRequestProto.class, Builder.class);
/*  8980:      */       }
/*  8981:      */       
/*  8982:      */       private Builder()
/*  8983:      */       {
/*  8984: 8545 */         maybeForceBuilderInitialization();
/*  8985:      */       }
/*  8986:      */       
/*  8987:      */       private Builder(GeneratedMessage.BuilderParent parent)
/*  8988:      */       {
/*  8989: 8550 */         super();
/*  8990: 8551 */         maybeForceBuilderInitialization();
/*  8991:      */       }
/*  8992:      */       
/*  8993:      */       private void maybeForceBuilderInitialization()
/*  8994:      */       {
/*  8995: 8554 */         if (MRServiceProtos.KillJobRequestProto.alwaysUseFieldBuilders) {
/*  8996: 8555 */           getJobIdFieldBuilder();
/*  8997:      */         }
/*  8998:      */       }
/*  8999:      */       
/*  9000:      */       private static Builder create()
/*  9001:      */       {
/*  9002: 8559 */         return new Builder();
/*  9003:      */       }
/*  9004:      */       
/*  9005:      */       public Builder clear()
/*  9006:      */       {
/*  9007: 8563 */         super.clear();
/*  9008: 8564 */         if (this.jobIdBuilder_ == null) {
/*  9009: 8565 */           this.jobId_ = MRProtos.JobIdProto.getDefaultInstance();
/*  9010:      */         } else {
/*  9011: 8567 */           this.jobIdBuilder_.clear();
/*  9012:      */         }
/*  9013: 8569 */         this.bitField0_ &= 0xFFFFFFFE;
/*  9014: 8570 */         return this;
/*  9015:      */       }
/*  9016:      */       
/*  9017:      */       public Builder clone()
/*  9018:      */       {
/*  9019: 8574 */         return create().mergeFrom(buildPartial());
/*  9020:      */       }
/*  9021:      */       
/*  9022:      */       public Descriptors.Descriptor getDescriptorForType()
/*  9023:      */       {
/*  9024: 8579 */         return MRServiceProtos.internal_static_hadoop_mapreduce_KillJobRequestProto_descriptor;
/*  9025:      */       }
/*  9026:      */       
/*  9027:      */       public MRServiceProtos.KillJobRequestProto getDefaultInstanceForType()
/*  9028:      */       {
/*  9029: 8583 */         return MRServiceProtos.KillJobRequestProto.getDefaultInstance();
/*  9030:      */       }
/*  9031:      */       
/*  9032:      */       public MRServiceProtos.KillJobRequestProto build()
/*  9033:      */       {
/*  9034: 8587 */         MRServiceProtos.KillJobRequestProto result = buildPartial();
/*  9035: 8588 */         if (!result.isInitialized()) {
/*  9036: 8589 */           throw newUninitializedMessageException(result);
/*  9037:      */         }
/*  9038: 8591 */         return result;
/*  9039:      */       }
/*  9040:      */       
/*  9041:      */       public MRServiceProtos.KillJobRequestProto buildPartial()
/*  9042:      */       {
/*  9043: 8595 */         MRServiceProtos.KillJobRequestProto result = new MRServiceProtos.KillJobRequestProto(this, null);
/*  9044: 8596 */         int from_bitField0_ = this.bitField0_;
/*  9045: 8597 */         int to_bitField0_ = 0;
/*  9046: 8598 */         if ((from_bitField0_ & 0x1) == 1) {
/*  9047: 8599 */           to_bitField0_ |= 0x1;
/*  9048:      */         }
/*  9049: 8601 */         if (this.jobIdBuilder_ == null) {
/*  9050: 8602 */           result.jobId_ = this.jobId_;
/*  9051:      */         } else {
/*  9052: 8604 */           result.jobId_ = ((MRProtos.JobIdProto)this.jobIdBuilder_.build());
/*  9053:      */         }
/*  9054: 8606 */         result.bitField0_ = to_bitField0_;
/*  9055: 8607 */         onBuilt();
/*  9056: 8608 */         return result;
/*  9057:      */       }
/*  9058:      */       
/*  9059:      */       public Builder mergeFrom(Message other)
/*  9060:      */       {
/*  9061: 8612 */         if ((other instanceof MRServiceProtos.KillJobRequestProto)) {
/*  9062: 8613 */           return mergeFrom((MRServiceProtos.KillJobRequestProto)other);
/*  9063:      */         }
/*  9064: 8615 */         super.mergeFrom(other);
/*  9065: 8616 */         return this;
/*  9066:      */       }
/*  9067:      */       
/*  9068:      */       public Builder mergeFrom(MRServiceProtos.KillJobRequestProto other)
/*  9069:      */       {
/*  9070: 8621 */         if (other == MRServiceProtos.KillJobRequestProto.getDefaultInstance()) {
/*  9071: 8621 */           return this;
/*  9072:      */         }
/*  9073: 8622 */         if (other.hasJobId()) {
/*  9074: 8623 */           mergeJobId(other.getJobId());
/*  9075:      */         }
/*  9076: 8625 */         mergeUnknownFields(other.getUnknownFields());
/*  9077: 8626 */         return this;
/*  9078:      */       }
/*  9079:      */       
/*  9080:      */       public final boolean isInitialized()
/*  9081:      */       {
/*  9082: 8630 */         return true;
/*  9083:      */       }
/*  9084:      */       
/*  9085:      */       public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry)
/*  9086:      */         throws IOException
/*  9087:      */       {
/*  9088: 8637 */         MRServiceProtos.KillJobRequestProto parsedMessage = null;
/*  9089:      */         try
/*  9090:      */         {
/*  9091: 8639 */           parsedMessage = (MRServiceProtos.KillJobRequestProto)MRServiceProtos.KillJobRequestProto.PARSER.parsePartialFrom(input, extensionRegistry);
/*  9092:      */         }
/*  9093:      */         catch (InvalidProtocolBufferException e)
/*  9094:      */         {
/*  9095: 8641 */           parsedMessage = (MRServiceProtos.KillJobRequestProto)e.getUnfinishedMessage();
/*  9096: 8642 */           throw e;
/*  9097:      */         }
/*  9098:      */         finally
/*  9099:      */         {
/*  9100: 8644 */           if (parsedMessage != null) {
/*  9101: 8645 */             mergeFrom(parsedMessage);
/*  9102:      */           }
/*  9103:      */         }
/*  9104: 8648 */         return this;
/*  9105:      */       }
/*  9106:      */       
/*  9107: 8653 */       private MRProtos.JobIdProto jobId_ = MRProtos.JobIdProto.getDefaultInstance();
/*  9108:      */       private SingleFieldBuilder<MRProtos.JobIdProto, MRProtos.JobIdProto.Builder, MRProtos.JobIdProtoOrBuilder> jobIdBuilder_;
/*  9109:      */       
/*  9110:      */       public boolean hasJobId()
/*  9111:      */       {
/*  9112: 8660 */         return (this.bitField0_ & 0x1) == 1;
/*  9113:      */       }
/*  9114:      */       
/*  9115:      */       public MRProtos.JobIdProto getJobId()
/*  9116:      */       {
/*  9117: 8666 */         if (this.jobIdBuilder_ == null) {
/*  9118: 8667 */           return this.jobId_;
/*  9119:      */         }
/*  9120: 8669 */         return (MRProtos.JobIdProto)this.jobIdBuilder_.getMessage();
/*  9121:      */       }
/*  9122:      */       
/*  9123:      */       public Builder setJobId(MRProtos.JobIdProto value)
/*  9124:      */       {
/*  9125: 8676 */         if (this.jobIdBuilder_ == null)
/*  9126:      */         {
/*  9127: 8677 */           if (value == null) {
/*  9128: 8678 */             throw new NullPointerException();
/*  9129:      */           }
/*  9130: 8680 */           this.jobId_ = value;
/*  9131: 8681 */           onChanged();
/*  9132:      */         }
/*  9133:      */         else
/*  9134:      */         {
/*  9135: 8683 */           this.jobIdBuilder_.setMessage(value);
/*  9136:      */         }
/*  9137: 8685 */         this.bitField0_ |= 0x1;
/*  9138: 8686 */         return this;
/*  9139:      */       }
/*  9140:      */       
/*  9141:      */       public Builder setJobId(MRProtos.JobIdProto.Builder builderForValue)
/*  9142:      */       {
/*  9143: 8693 */         if (this.jobIdBuilder_ == null)
/*  9144:      */         {
/*  9145: 8694 */           this.jobId_ = builderForValue.build();
/*  9146: 8695 */           onChanged();
/*  9147:      */         }
/*  9148:      */         else
/*  9149:      */         {
/*  9150: 8697 */           this.jobIdBuilder_.setMessage(builderForValue.build());
/*  9151:      */         }
/*  9152: 8699 */         this.bitField0_ |= 0x1;
/*  9153: 8700 */         return this;
/*  9154:      */       }
/*  9155:      */       
/*  9156:      */       public Builder mergeJobId(MRProtos.JobIdProto value)
/*  9157:      */       {
/*  9158: 8706 */         if (this.jobIdBuilder_ == null)
/*  9159:      */         {
/*  9160: 8707 */           if (((this.bitField0_ & 0x1) == 1) && (this.jobId_ != MRProtos.JobIdProto.getDefaultInstance())) {
/*  9161: 8709 */             this.jobId_ = MRProtos.JobIdProto.newBuilder(this.jobId_).mergeFrom(value).buildPartial();
/*  9162:      */           } else {
/*  9163: 8712 */             this.jobId_ = value;
/*  9164:      */           }
/*  9165: 8714 */           onChanged();
/*  9166:      */         }
/*  9167:      */         else
/*  9168:      */         {
/*  9169: 8716 */           this.jobIdBuilder_.mergeFrom(value);
/*  9170:      */         }
/*  9171: 8718 */         this.bitField0_ |= 0x1;
/*  9172: 8719 */         return this;
/*  9173:      */       }
/*  9174:      */       
/*  9175:      */       public Builder clearJobId()
/*  9176:      */       {
/*  9177: 8725 */         if (this.jobIdBuilder_ == null)
/*  9178:      */         {
/*  9179: 8726 */           this.jobId_ = MRProtos.JobIdProto.getDefaultInstance();
/*  9180: 8727 */           onChanged();
/*  9181:      */         }
/*  9182:      */         else
/*  9183:      */         {
/*  9184: 8729 */           this.jobIdBuilder_.clear();
/*  9185:      */         }
/*  9186: 8731 */         this.bitField0_ &= 0xFFFFFFFE;
/*  9187: 8732 */         return this;
/*  9188:      */       }
/*  9189:      */       
/*  9190:      */       public MRProtos.JobIdProto.Builder getJobIdBuilder()
/*  9191:      */       {
/*  9192: 8738 */         this.bitField0_ |= 0x1;
/*  9193: 8739 */         onChanged();
/*  9194: 8740 */         return (MRProtos.JobIdProto.Builder)getJobIdFieldBuilder().getBuilder();
/*  9195:      */       }
/*  9196:      */       
/*  9197:      */       public MRProtos.JobIdProtoOrBuilder getJobIdOrBuilder()
/*  9198:      */       {
/*  9199: 8746 */         if (this.jobIdBuilder_ != null) {
/*  9200: 8747 */           return (MRProtos.JobIdProtoOrBuilder)this.jobIdBuilder_.getMessageOrBuilder();
/*  9201:      */         }
/*  9202: 8749 */         return this.jobId_;
/*  9203:      */       }
/*  9204:      */       
/*  9205:      */       private SingleFieldBuilder<MRProtos.JobIdProto, MRProtos.JobIdProto.Builder, MRProtos.JobIdProtoOrBuilder> getJobIdFieldBuilder()
/*  9206:      */       {
/*  9207: 8758 */         if (this.jobIdBuilder_ == null)
/*  9208:      */         {
/*  9209: 8759 */           this.jobIdBuilder_ = new SingleFieldBuilder(this.jobId_, getParentForChildren(), isClean());
/*  9210:      */           
/*  9211:      */ 
/*  9212:      */ 
/*  9213:      */ 
/*  9214: 8764 */           this.jobId_ = null;
/*  9215:      */         }
/*  9216: 8766 */         return this.jobIdBuilder_;
/*  9217:      */       }
/*  9218:      */     }
/*  9219:      */     
/*  9220:      */     static
/*  9221:      */     {
/*  9222: 8773 */       defaultInstance = new KillJobRequestProto(true);
/*  9223: 8774 */       defaultInstance.initFields();
/*  9224:      */     }
/*  9225:      */   }
/*  9226:      */   
/*  9227:      */   public static abstract interface KillJobResponseProtoOrBuilder
/*  9228:      */     extends MessageOrBuilder
/*  9229:      */   {}
/*  9230:      */   
/*  9231:      */   public static final class KillJobResponseProto
/*  9232:      */     extends GeneratedMessage
/*  9233:      */     implements MRServiceProtos.KillJobResponseProtoOrBuilder
/*  9234:      */   {
/*  9235:      */     private static final KillJobResponseProto defaultInstance;
/*  9236:      */     private final UnknownFieldSet unknownFields;
/*  9237:      */     
/*  9238:      */     private KillJobResponseProto(GeneratedMessage.Builder<?> builder)
/*  9239:      */     {
/*  9240: 8791 */       super();
/*  9241: 8792 */       this.unknownFields = builder.getUnknownFields();
/*  9242:      */     }
/*  9243:      */     
/*  9244:      */     private KillJobResponseProto(boolean noInit)
/*  9245:      */     {
/*  9246: 8794 */       this.unknownFields = UnknownFieldSet.getDefaultInstance();
/*  9247:      */     }
/*  9248:      */     
/*  9249:      */     public static KillJobResponseProto getDefaultInstance()
/*  9250:      */     {
/*  9251: 8798 */       return defaultInstance;
/*  9252:      */     }
/*  9253:      */     
/*  9254:      */     public KillJobResponseProto getDefaultInstanceForType()
/*  9255:      */     {
/*  9256: 8802 */       return defaultInstance;
/*  9257:      */     }
/*  9258:      */     
/*  9259:      */     public final UnknownFieldSet getUnknownFields()
/*  9260:      */     {
/*  9261: 8809 */       return this.unknownFields;
/*  9262:      */     }
/*  9263:      */     
/*  9264:      */     private KillJobResponseProto(CodedInputStream input, ExtensionRegistryLite extensionRegistry)
/*  9265:      */       throws InvalidProtocolBufferException
/*  9266:      */     {
/*  9267: 8815 */       initFields();
/*  9268: 8816 */       UnknownFieldSet.Builder unknownFields = UnknownFieldSet.newBuilder();
/*  9269:      */       try
/*  9270:      */       {
/*  9271: 8819 */         boolean done = false;
/*  9272: 8820 */         while (!done)
/*  9273:      */         {
/*  9274: 8821 */           int tag = input.readTag();
/*  9275: 8822 */           switch (tag)
/*  9276:      */           {
/*  9277:      */           case 0: 
/*  9278: 8824 */             done = true;
/*  9279: 8825 */             break;
/*  9280:      */           default: 
/*  9281: 8827 */             if (!parseUnknownField(input, unknownFields, extensionRegistry, tag)) {
/*  9282: 8829 */               done = true;
/*  9283:      */             }
/*  9284:      */             break;
/*  9285:      */           }
/*  9286:      */         }
/*  9287:      */       }
/*  9288:      */       catch (InvalidProtocolBufferException e)
/*  9289:      */       {
/*  9290: 8836 */         throw e.setUnfinishedMessage(this);
/*  9291:      */       }
/*  9292:      */       catch (IOException e)
/*  9293:      */       {
/*  9294: 8838 */         throw new InvalidProtocolBufferException(e.getMessage()).setUnfinishedMessage(this);
/*  9295:      */       }
/*  9296:      */       finally
/*  9297:      */       {
/*  9298: 8841 */         this.unknownFields = unknownFields.build();
/*  9299: 8842 */         makeExtensionsImmutable();
/*  9300:      */       }
/*  9301:      */     }
/*  9302:      */     
/*  9303:      */     public static final Descriptors.Descriptor getDescriptor()
/*  9304:      */     {
/*  9305: 8847 */       return MRServiceProtos.internal_static_hadoop_mapreduce_KillJobResponseProto_descriptor;
/*  9306:      */     }
/*  9307:      */     
/*  9308:      */     protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable()
/*  9309:      */     {
/*  9310: 8852 */       return MRServiceProtos.internal_static_hadoop_mapreduce_KillJobResponseProto_fieldAccessorTable.ensureFieldAccessorsInitialized(KillJobResponseProto.class, Builder.class);
/*  9311:      */     }
/*  9312:      */     
/*  9313: 8857 */     public static Parser<KillJobResponseProto> PARSER = new AbstractParser()
/*  9314:      */     {
/*  9315:      */       public MRServiceProtos.KillJobResponseProto parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry)
/*  9316:      */         throws InvalidProtocolBufferException
/*  9317:      */       {
/*  9318: 8863 */         return new MRServiceProtos.KillJobResponseProto(input, extensionRegistry, null);
/*  9319:      */       }
/*  9320:      */     };
/*  9321:      */     
/*  9322:      */     public Parser<KillJobResponseProto> getParserForType()
/*  9323:      */     {
/*  9324: 8869 */       return PARSER;
/*  9325:      */     }
/*  9326:      */     
/*  9327: 8874 */     private byte memoizedIsInitialized = -1;
/*  9328:      */     
/*  9329:      */     private void initFields() {}
/*  9330:      */     
/*  9331:      */     public final boolean isInitialized()
/*  9332:      */     {
/*  9333: 8876 */       byte isInitialized = this.memoizedIsInitialized;
/*  9334: 8877 */       if (isInitialized != -1) {
/*  9335: 8877 */         return isInitialized == 1;
/*  9336:      */       }
/*  9337: 8879 */       this.memoizedIsInitialized = 1;
/*  9338: 8880 */       return true;
/*  9339:      */     }
/*  9340:      */     
/*  9341:      */     public void writeTo(CodedOutputStream output)
/*  9342:      */       throws IOException
/*  9343:      */     {
/*  9344: 8885 */       getSerializedSize();
/*  9345: 8886 */       getUnknownFields().writeTo(output);
/*  9346:      */     }
/*  9347:      */     
/*  9348: 8889 */     private int memoizedSerializedSize = -1;
/*  9349:      */     private static final long serialVersionUID = 0L;
/*  9350:      */     
/*  9351:      */     public int getSerializedSize()
/*  9352:      */     {
/*  9353: 8891 */       int size = this.memoizedSerializedSize;
/*  9354: 8892 */       if (size != -1) {
/*  9355: 8892 */         return size;
/*  9356:      */       }
/*  9357: 8894 */       size = 0;
/*  9358: 8895 */       size += getUnknownFields().getSerializedSize();
/*  9359: 8896 */       this.memoizedSerializedSize = size;
/*  9360: 8897 */       return size;
/*  9361:      */     }
/*  9362:      */     
/*  9363:      */     protected Object writeReplace()
/*  9364:      */       throws ObjectStreamException
/*  9365:      */     {
/*  9366: 8904 */       return super.writeReplace();
/*  9367:      */     }
/*  9368:      */     
/*  9369:      */     public boolean equals(Object obj)
/*  9370:      */     {
/*  9371: 8909 */       if (obj == this) {
/*  9372: 8910 */         return true;
/*  9373:      */       }
/*  9374: 8912 */       if (!(obj instanceof KillJobResponseProto)) {
/*  9375: 8913 */         return super.equals(obj);
/*  9376:      */       }
/*  9377: 8915 */       KillJobResponseProto other = (KillJobResponseProto)obj;
/*  9378:      */       
/*  9379: 8917 */       boolean result = true;
/*  9380: 8918 */       result = (result) && (getUnknownFields().equals(other.getUnknownFields()));
/*  9381:      */       
/*  9382: 8920 */       return result;
/*  9383:      */     }
/*  9384:      */     
/*  9385: 8923 */     private int memoizedHashCode = 0;
/*  9386:      */     
/*  9387:      */     public int hashCode()
/*  9388:      */     {
/*  9389: 8926 */       if (this.memoizedHashCode != 0) {
/*  9390: 8927 */         return this.memoizedHashCode;
/*  9391:      */       }
/*  9392: 8929 */       int hash = 41;
/*  9393: 8930 */       hash = 19 * hash + getDescriptorForType().hashCode();
/*  9394: 8931 */       hash = 29 * hash + getUnknownFields().hashCode();
/*  9395: 8932 */       this.memoizedHashCode = hash;
/*  9396: 8933 */       return hash;
/*  9397:      */     }
/*  9398:      */     
/*  9399:      */     public static KillJobResponseProto parseFrom(ByteString data)
/*  9400:      */       throws InvalidProtocolBufferException
/*  9401:      */     {
/*  9402: 8939 */       return (KillJobResponseProto)PARSER.parseFrom(data);
/*  9403:      */     }
/*  9404:      */     
/*  9405:      */     public static KillJobResponseProto parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry)
/*  9406:      */       throws InvalidProtocolBufferException
/*  9407:      */     {
/*  9408: 8945 */       return (KillJobResponseProto)PARSER.parseFrom(data, extensionRegistry);
/*  9409:      */     }
/*  9410:      */     
/*  9411:      */     public static KillJobResponseProto parseFrom(byte[] data)
/*  9412:      */       throws InvalidProtocolBufferException
/*  9413:      */     {
/*  9414: 8949 */       return (KillJobResponseProto)PARSER.parseFrom(data);
/*  9415:      */     }
/*  9416:      */     
/*  9417:      */     public static KillJobResponseProto parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry)
/*  9418:      */       throws InvalidProtocolBufferException
/*  9419:      */     {
/*  9420: 8955 */       return (KillJobResponseProto)PARSER.parseFrom(data, extensionRegistry);
/*  9421:      */     }
/*  9422:      */     
/*  9423:      */     public static KillJobResponseProto parseFrom(InputStream input)
/*  9424:      */       throws IOException
/*  9425:      */     {
/*  9426: 8959 */       return (KillJobResponseProto)PARSER.parseFrom(input);
/*  9427:      */     }
/*  9428:      */     
/*  9429:      */     public static KillJobResponseProto parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry)
/*  9430:      */       throws IOException
/*  9431:      */     {
/*  9432: 8965 */       return (KillJobResponseProto)PARSER.parseFrom(input, extensionRegistry);
/*  9433:      */     }
/*  9434:      */     
/*  9435:      */     public static KillJobResponseProto parseDelimitedFrom(InputStream input)
/*  9436:      */       throws IOException
/*  9437:      */     {
/*  9438: 8969 */       return (KillJobResponseProto)PARSER.parseDelimitedFrom(input);
/*  9439:      */     }
/*  9440:      */     
/*  9441:      */     public static KillJobResponseProto parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry)
/*  9442:      */       throws IOException
/*  9443:      */     {
/*  9444: 8975 */       return (KillJobResponseProto)PARSER.parseDelimitedFrom(input, extensionRegistry);
/*  9445:      */     }
/*  9446:      */     
/*  9447:      */     public static KillJobResponseProto parseFrom(CodedInputStream input)
/*  9448:      */       throws IOException
/*  9449:      */     {
/*  9450: 8980 */       return (KillJobResponseProto)PARSER.parseFrom(input);
/*  9451:      */     }
/*  9452:      */     
/*  9453:      */     public static KillJobResponseProto parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry)
/*  9454:      */       throws IOException
/*  9455:      */     {
/*  9456: 8986 */       return (KillJobResponseProto)PARSER.parseFrom(input, extensionRegistry);
/*  9457:      */     }
/*  9458:      */     
/*  9459:      */     public static Builder newBuilder()
/*  9460:      */     {
/*  9461: 8989 */       return Builder.access$14000();
/*  9462:      */     }
/*  9463:      */     
/*  9464:      */     public Builder newBuilderForType()
/*  9465:      */     {
/*  9466: 8990 */       return newBuilder();
/*  9467:      */     }
/*  9468:      */     
/*  9469:      */     public static Builder newBuilder(KillJobResponseProto prototype)
/*  9470:      */     {
/*  9471: 8992 */       return newBuilder().mergeFrom(prototype);
/*  9472:      */     }
/*  9473:      */     
/*  9474:      */     public Builder toBuilder()
/*  9475:      */     {
/*  9476: 8994 */       return newBuilder(this);
/*  9477:      */     }
/*  9478:      */     
/*  9479:      */     protected Builder newBuilderForType(GeneratedMessage.BuilderParent parent)
/*  9480:      */     {
/*  9481: 8999 */       Builder builder = new Builder(parent, null);
/*  9482: 9000 */       return builder;
/*  9483:      */     }
/*  9484:      */     
/*  9485:      */     public static final class Builder
/*  9486:      */       extends GeneratedMessage.Builder<Builder>
/*  9487:      */       implements MRServiceProtos.KillJobResponseProtoOrBuilder
/*  9488:      */     {
/*  9489:      */       public static final Descriptors.Descriptor getDescriptor()
/*  9490:      */       {
/*  9491: 9010 */         return MRServiceProtos.internal_static_hadoop_mapreduce_KillJobResponseProto_descriptor;
/*  9492:      */       }
/*  9493:      */       
/*  9494:      */       protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable()
/*  9495:      */       {
/*  9496: 9015 */         return MRServiceProtos.internal_static_hadoop_mapreduce_KillJobResponseProto_fieldAccessorTable.ensureFieldAccessorsInitialized(MRServiceProtos.KillJobResponseProto.class, Builder.class);
/*  9497:      */       }
/*  9498:      */       
/*  9499:      */       private Builder()
/*  9500:      */       {
/*  9501: 9022 */         maybeForceBuilderInitialization();
/*  9502:      */       }
/*  9503:      */       
/*  9504:      */       private Builder(GeneratedMessage.BuilderParent parent)
/*  9505:      */       {
/*  9506: 9027 */         super();
/*  9507: 9028 */         maybeForceBuilderInitialization();
/*  9508:      */       }
/*  9509:      */       
/*  9510:      */       private void maybeForceBuilderInitialization()
/*  9511:      */       {
/*  9512: 9031 */         if (MRServiceProtos.KillJobResponseProto.alwaysUseFieldBuilders) {}
/*  9513:      */       }
/*  9514:      */       
/*  9515:      */       private static Builder create()
/*  9516:      */       {
/*  9517: 9035 */         return new Builder();
/*  9518:      */       }
/*  9519:      */       
/*  9520:      */       public Builder clear()
/*  9521:      */       {
/*  9522: 9039 */         super.clear();
/*  9523: 9040 */         return this;
/*  9524:      */       }
/*  9525:      */       
/*  9526:      */       public Builder clone()
/*  9527:      */       {
/*  9528: 9044 */         return create().mergeFrom(buildPartial());
/*  9529:      */       }
/*  9530:      */       
/*  9531:      */       public Descriptors.Descriptor getDescriptorForType()
/*  9532:      */       {
/*  9533: 9049 */         return MRServiceProtos.internal_static_hadoop_mapreduce_KillJobResponseProto_descriptor;
/*  9534:      */       }
/*  9535:      */       
/*  9536:      */       public MRServiceProtos.KillJobResponseProto getDefaultInstanceForType()
/*  9537:      */       {
/*  9538: 9053 */         return MRServiceProtos.KillJobResponseProto.getDefaultInstance();
/*  9539:      */       }
/*  9540:      */       
/*  9541:      */       public MRServiceProtos.KillJobResponseProto build()
/*  9542:      */       {
/*  9543: 9057 */         MRServiceProtos.KillJobResponseProto result = buildPartial();
/*  9544: 9058 */         if (!result.isInitialized()) {
/*  9545: 9059 */           throw newUninitializedMessageException(result);
/*  9546:      */         }
/*  9547: 9061 */         return result;
/*  9548:      */       }
/*  9549:      */       
/*  9550:      */       public MRServiceProtos.KillJobResponseProto buildPartial()
/*  9551:      */       {
/*  9552: 9065 */         MRServiceProtos.KillJobResponseProto result = new MRServiceProtos.KillJobResponseProto(this, null);
/*  9553: 9066 */         onBuilt();
/*  9554: 9067 */         return result;
/*  9555:      */       }
/*  9556:      */       
/*  9557:      */       public Builder mergeFrom(Message other)
/*  9558:      */       {
/*  9559: 9071 */         if ((other instanceof MRServiceProtos.KillJobResponseProto)) {
/*  9560: 9072 */           return mergeFrom((MRServiceProtos.KillJobResponseProto)other);
/*  9561:      */         }
/*  9562: 9074 */         super.mergeFrom(other);
/*  9563: 9075 */         return this;
/*  9564:      */       }
/*  9565:      */       
/*  9566:      */       public Builder mergeFrom(MRServiceProtos.KillJobResponseProto other)
/*  9567:      */       {
/*  9568: 9080 */         if (other == MRServiceProtos.KillJobResponseProto.getDefaultInstance()) {
/*  9569: 9080 */           return this;
/*  9570:      */         }
/*  9571: 9081 */         mergeUnknownFields(other.getUnknownFields());
/*  9572: 9082 */         return this;
/*  9573:      */       }
/*  9574:      */       
/*  9575:      */       public final boolean isInitialized()
/*  9576:      */       {
/*  9577: 9086 */         return true;
/*  9578:      */       }
/*  9579:      */       
/*  9580:      */       public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry)
/*  9581:      */         throws IOException
/*  9582:      */       {
/*  9583: 9093 */         MRServiceProtos.KillJobResponseProto parsedMessage = null;
/*  9584:      */         try
/*  9585:      */         {
/*  9586: 9095 */           parsedMessage = (MRServiceProtos.KillJobResponseProto)MRServiceProtos.KillJobResponseProto.PARSER.parsePartialFrom(input, extensionRegistry);
/*  9587:      */         }
/*  9588:      */         catch (InvalidProtocolBufferException e)
/*  9589:      */         {
/*  9590: 9097 */           parsedMessage = (MRServiceProtos.KillJobResponseProto)e.getUnfinishedMessage();
/*  9591: 9098 */           throw e;
/*  9592:      */         }
/*  9593:      */         finally
/*  9594:      */         {
/*  9595: 9100 */           if (parsedMessage != null) {
/*  9596: 9101 */             mergeFrom(parsedMessage);
/*  9597:      */           }
/*  9598:      */         }
/*  9599: 9104 */         return this;
/*  9600:      */       }
/*  9601:      */     }
/*  9602:      */     
/*  9603:      */     static
/*  9604:      */     {
/*  9605: 9111 */       defaultInstance = new KillJobResponseProto(true);
/*  9606: 9112 */       defaultInstance.initFields();
/*  9607:      */     }
/*  9608:      */   }
/*  9609:      */   
/*  9610:      */   public static abstract interface KillTaskRequestProtoOrBuilder
/*  9611:      */     extends MessageOrBuilder
/*  9612:      */   {
/*  9613:      */     public abstract boolean hasTaskId();
/*  9614:      */     
/*  9615:      */     public abstract MRProtos.TaskIdProto getTaskId();
/*  9616:      */     
/*  9617:      */     public abstract MRProtos.TaskIdProtoOrBuilder getTaskIdOrBuilder();
/*  9618:      */   }
/*  9619:      */   
/*  9620:      */   public static final class KillTaskRequestProto
/*  9621:      */     extends GeneratedMessage
/*  9622:      */     implements MRServiceProtos.KillTaskRequestProtoOrBuilder
/*  9623:      */   {
/*  9624:      */     private static final KillTaskRequestProto defaultInstance;
/*  9625:      */     private final UnknownFieldSet unknownFields;
/*  9626:      */     
/*  9627:      */     private KillTaskRequestProto(GeneratedMessage.Builder<?> builder)
/*  9628:      */     {
/*  9629: 9143 */       super();
/*  9630: 9144 */       this.unknownFields = builder.getUnknownFields();
/*  9631:      */     }
/*  9632:      */     
/*  9633:      */     private KillTaskRequestProto(boolean noInit)
/*  9634:      */     {
/*  9635: 9146 */       this.unknownFields = UnknownFieldSet.getDefaultInstance();
/*  9636:      */     }
/*  9637:      */     
/*  9638:      */     public static KillTaskRequestProto getDefaultInstance()
/*  9639:      */     {
/*  9640: 9150 */       return defaultInstance;
/*  9641:      */     }
/*  9642:      */     
/*  9643:      */     public KillTaskRequestProto getDefaultInstanceForType()
/*  9644:      */     {
/*  9645: 9154 */       return defaultInstance;
/*  9646:      */     }
/*  9647:      */     
/*  9648:      */     public final UnknownFieldSet getUnknownFields()
/*  9649:      */     {
/*  9650: 9161 */       return this.unknownFields;
/*  9651:      */     }
/*  9652:      */     
/*  9653:      */     private KillTaskRequestProto(CodedInputStream input, ExtensionRegistryLite extensionRegistry)
/*  9654:      */       throws InvalidProtocolBufferException
/*  9655:      */     {
/*  9656: 9167 */       initFields();
/*  9657: 9168 */       int mutable_bitField0_ = 0;
/*  9658: 9169 */       UnknownFieldSet.Builder unknownFields = UnknownFieldSet.newBuilder();
/*  9659:      */       try
/*  9660:      */       {
/*  9661: 9172 */         boolean done = false;
/*  9662: 9173 */         while (!done)
/*  9663:      */         {
/*  9664: 9174 */           int tag = input.readTag();
/*  9665: 9175 */           switch (tag)
/*  9666:      */           {
/*  9667:      */           case 0: 
/*  9668: 9177 */             done = true;
/*  9669: 9178 */             break;
/*  9670:      */           default: 
/*  9671: 9180 */             if (!parseUnknownField(input, unknownFields, extensionRegistry, tag)) {
/*  9672: 9182 */               done = true;
/*  9673:      */             }
/*  9674:      */             break;
/*  9675:      */           case 10: 
/*  9676: 9187 */             MRProtos.TaskIdProto.Builder subBuilder = null;
/*  9677: 9188 */             if ((this.bitField0_ & 0x1) == 1) {
/*  9678: 9189 */               subBuilder = this.taskId_.toBuilder();
/*  9679:      */             }
/*  9680: 9191 */             this.taskId_ = ((MRProtos.TaskIdProto)input.readMessage(MRProtos.TaskIdProto.PARSER, extensionRegistry));
/*  9681: 9192 */             if (subBuilder != null)
/*  9682:      */             {
/*  9683: 9193 */               subBuilder.mergeFrom(this.taskId_);
/*  9684: 9194 */               this.taskId_ = subBuilder.buildPartial();
/*  9685:      */             }
/*  9686: 9196 */             this.bitField0_ |= 0x1;
/*  9687:      */           }
/*  9688:      */         }
/*  9689:      */       }
/*  9690:      */       catch (InvalidProtocolBufferException e)
/*  9691:      */       {
/*  9692: 9202 */         throw e.setUnfinishedMessage(this);
/*  9693:      */       }
/*  9694:      */       catch (IOException e)
/*  9695:      */       {
/*  9696: 9204 */         throw new InvalidProtocolBufferException(e.getMessage()).setUnfinishedMessage(this);
/*  9697:      */       }
/*  9698:      */       finally
/*  9699:      */       {
/*  9700: 9207 */         this.unknownFields = unknownFields.build();
/*  9701: 9208 */         makeExtensionsImmutable();
/*  9702:      */       }
/*  9703:      */     }
/*  9704:      */     
/*  9705:      */     public static final Descriptors.Descriptor getDescriptor()
/*  9706:      */     {
/*  9707: 9213 */       return MRServiceProtos.internal_static_hadoop_mapreduce_KillTaskRequestProto_descriptor;
/*  9708:      */     }
/*  9709:      */     
/*  9710:      */     protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable()
/*  9711:      */     {
/*  9712: 9218 */       return MRServiceProtos.internal_static_hadoop_mapreduce_KillTaskRequestProto_fieldAccessorTable.ensureFieldAccessorsInitialized(KillTaskRequestProto.class, Builder.class);
/*  9713:      */     }
/*  9714:      */     
/*  9715: 9223 */     public static Parser<KillTaskRequestProto> PARSER = new AbstractParser()
/*  9716:      */     {
/*  9717:      */       public MRServiceProtos.KillTaskRequestProto parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry)
/*  9718:      */         throws InvalidProtocolBufferException
/*  9719:      */       {
/*  9720: 9229 */         return new MRServiceProtos.KillTaskRequestProto(input, extensionRegistry, null);
/*  9721:      */       }
/*  9722:      */     };
/*  9723:      */     private int bitField0_;
/*  9724:      */     public static final int TASK_ID_FIELD_NUMBER = 1;
/*  9725:      */     private MRProtos.TaskIdProto taskId_;
/*  9726:      */     
/*  9727:      */     public Parser<KillTaskRequestProto> getParserForType()
/*  9728:      */     {
/*  9729: 9235 */       return PARSER;
/*  9730:      */     }
/*  9731:      */     
/*  9732:      */     public boolean hasTaskId()
/*  9733:      */     {
/*  9734: 9246 */       return (this.bitField0_ & 0x1) == 1;
/*  9735:      */     }
/*  9736:      */     
/*  9737:      */     public MRProtos.TaskIdProto getTaskId()
/*  9738:      */     {
/*  9739: 9252 */       return this.taskId_;
/*  9740:      */     }
/*  9741:      */     
/*  9742:      */     public MRProtos.TaskIdProtoOrBuilder getTaskIdOrBuilder()
/*  9743:      */     {
/*  9744: 9258 */       return this.taskId_;
/*  9745:      */     }
/*  9746:      */     
/*  9747:      */     private void initFields()
/*  9748:      */     {
/*  9749: 9262 */       this.taskId_ = MRProtos.TaskIdProto.getDefaultInstance();
/*  9750:      */     }
/*  9751:      */     
/*  9752: 9264 */     private byte memoizedIsInitialized = -1;
/*  9753:      */     
/*  9754:      */     public final boolean isInitialized()
/*  9755:      */     {
/*  9756: 9266 */       byte isInitialized = this.memoizedIsInitialized;
/*  9757: 9267 */       if (isInitialized != -1) {
/*  9758: 9267 */         return isInitialized == 1;
/*  9759:      */       }
/*  9760: 9269 */       this.memoizedIsInitialized = 1;
/*  9761: 9270 */       return true;
/*  9762:      */     }
/*  9763:      */     
/*  9764:      */     public void writeTo(CodedOutputStream output)
/*  9765:      */       throws IOException
/*  9766:      */     {
/*  9767: 9275 */       getSerializedSize();
/*  9768: 9276 */       if ((this.bitField0_ & 0x1) == 1) {
/*  9769: 9277 */         output.writeMessage(1, this.taskId_);
/*  9770:      */       }
/*  9771: 9279 */       getUnknownFields().writeTo(output);
/*  9772:      */     }
/*  9773:      */     
/*  9774: 9282 */     private int memoizedSerializedSize = -1;
/*  9775:      */     private static final long serialVersionUID = 0L;
/*  9776:      */     
/*  9777:      */     public int getSerializedSize()
/*  9778:      */     {
/*  9779: 9284 */       int size = this.memoizedSerializedSize;
/*  9780: 9285 */       if (size != -1) {
/*  9781: 9285 */         return size;
/*  9782:      */       }
/*  9783: 9287 */       size = 0;
/*  9784: 9288 */       if ((this.bitField0_ & 0x1) == 1) {
/*  9785: 9289 */         size += CodedOutputStream.computeMessageSize(1, this.taskId_);
/*  9786:      */       }
/*  9787: 9292 */       size += getUnknownFields().getSerializedSize();
/*  9788: 9293 */       this.memoizedSerializedSize = size;
/*  9789: 9294 */       return size;
/*  9790:      */     }
/*  9791:      */     
/*  9792:      */     protected Object writeReplace()
/*  9793:      */       throws ObjectStreamException
/*  9794:      */     {
/*  9795: 9301 */       return super.writeReplace();
/*  9796:      */     }
/*  9797:      */     
/*  9798:      */     public boolean equals(Object obj)
/*  9799:      */     {
/*  9800: 9306 */       if (obj == this) {
/*  9801: 9307 */         return true;
/*  9802:      */       }
/*  9803: 9309 */       if (!(obj instanceof KillTaskRequestProto)) {
/*  9804: 9310 */         return super.equals(obj);
/*  9805:      */       }
/*  9806: 9312 */       KillTaskRequestProto other = (KillTaskRequestProto)obj;
/*  9807:      */       
/*  9808: 9314 */       boolean result = true;
/*  9809: 9315 */       result = (result) && (hasTaskId() == other.hasTaskId());
/*  9810: 9316 */       if (hasTaskId()) {
/*  9811: 9317 */         result = (result) && (getTaskId().equals(other.getTaskId()));
/*  9812:      */       }
/*  9813: 9320 */       result = (result) && (getUnknownFields().equals(other.getUnknownFields()));
/*  9814:      */       
/*  9815: 9322 */       return result;
/*  9816:      */     }
/*  9817:      */     
/*  9818: 9325 */     private int memoizedHashCode = 0;
/*  9819:      */     
/*  9820:      */     public int hashCode()
/*  9821:      */     {
/*  9822: 9328 */       if (this.memoizedHashCode != 0) {
/*  9823: 9329 */         return this.memoizedHashCode;
/*  9824:      */       }
/*  9825: 9331 */       int hash = 41;
/*  9826: 9332 */       hash = 19 * hash + getDescriptorForType().hashCode();
/*  9827: 9333 */       if (hasTaskId())
/*  9828:      */       {
/*  9829: 9334 */         hash = 37 * hash + 1;
/*  9830: 9335 */         hash = 53 * hash + getTaskId().hashCode();
/*  9831:      */       }
/*  9832: 9337 */       hash = 29 * hash + getUnknownFields().hashCode();
/*  9833: 9338 */       this.memoizedHashCode = hash;
/*  9834: 9339 */       return hash;
/*  9835:      */     }
/*  9836:      */     
/*  9837:      */     public static KillTaskRequestProto parseFrom(ByteString data)
/*  9838:      */       throws InvalidProtocolBufferException
/*  9839:      */     {
/*  9840: 9345 */       return (KillTaskRequestProto)PARSER.parseFrom(data);
/*  9841:      */     }
/*  9842:      */     
/*  9843:      */     public static KillTaskRequestProto parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry)
/*  9844:      */       throws InvalidProtocolBufferException
/*  9845:      */     {
/*  9846: 9351 */       return (KillTaskRequestProto)PARSER.parseFrom(data, extensionRegistry);
/*  9847:      */     }
/*  9848:      */     
/*  9849:      */     public static KillTaskRequestProto parseFrom(byte[] data)
/*  9850:      */       throws InvalidProtocolBufferException
/*  9851:      */     {
/*  9852: 9355 */       return (KillTaskRequestProto)PARSER.parseFrom(data);
/*  9853:      */     }
/*  9854:      */     
/*  9855:      */     public static KillTaskRequestProto parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry)
/*  9856:      */       throws InvalidProtocolBufferException
/*  9857:      */     {
/*  9858: 9361 */       return (KillTaskRequestProto)PARSER.parseFrom(data, extensionRegistry);
/*  9859:      */     }
/*  9860:      */     
/*  9861:      */     public static KillTaskRequestProto parseFrom(InputStream input)
/*  9862:      */       throws IOException
/*  9863:      */     {
/*  9864: 9365 */       return (KillTaskRequestProto)PARSER.parseFrom(input);
/*  9865:      */     }
/*  9866:      */     
/*  9867:      */     public static KillTaskRequestProto parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry)
/*  9868:      */       throws IOException
/*  9869:      */     {
/*  9870: 9371 */       return (KillTaskRequestProto)PARSER.parseFrom(input, extensionRegistry);
/*  9871:      */     }
/*  9872:      */     
/*  9873:      */     public static KillTaskRequestProto parseDelimitedFrom(InputStream input)
/*  9874:      */       throws IOException
/*  9875:      */     {
/*  9876: 9375 */       return (KillTaskRequestProto)PARSER.parseDelimitedFrom(input);
/*  9877:      */     }
/*  9878:      */     
/*  9879:      */     public static KillTaskRequestProto parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry)
/*  9880:      */       throws IOException
/*  9881:      */     {
/*  9882: 9381 */       return (KillTaskRequestProto)PARSER.parseDelimitedFrom(input, extensionRegistry);
/*  9883:      */     }
/*  9884:      */     
/*  9885:      */     public static KillTaskRequestProto parseFrom(CodedInputStream input)
/*  9886:      */       throws IOException
/*  9887:      */     {
/*  9888: 9386 */       return (KillTaskRequestProto)PARSER.parseFrom(input);
/*  9889:      */     }
/*  9890:      */     
/*  9891:      */     public static KillTaskRequestProto parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry)
/*  9892:      */       throws IOException
/*  9893:      */     {
/*  9894: 9392 */       return (KillTaskRequestProto)PARSER.parseFrom(input, extensionRegistry);
/*  9895:      */     }
/*  9896:      */     
/*  9897:      */     public static Builder newBuilder()
/*  9898:      */     {
/*  9899: 9395 */       return Builder.access$14700();
/*  9900:      */     }
/*  9901:      */     
/*  9902:      */     public Builder newBuilderForType()
/*  9903:      */     {
/*  9904: 9396 */       return newBuilder();
/*  9905:      */     }
/*  9906:      */     
/*  9907:      */     public static Builder newBuilder(KillTaskRequestProto prototype)
/*  9908:      */     {
/*  9909: 9398 */       return newBuilder().mergeFrom(prototype);
/*  9910:      */     }
/*  9911:      */     
/*  9912:      */     public Builder toBuilder()
/*  9913:      */     {
/*  9914: 9400 */       return newBuilder(this);
/*  9915:      */     }
/*  9916:      */     
/*  9917:      */     protected Builder newBuilderForType(GeneratedMessage.BuilderParent parent)
/*  9918:      */     {
/*  9919: 9405 */       Builder builder = new Builder(parent, null);
/*  9920: 9406 */       return builder;
/*  9921:      */     }
/*  9922:      */     
/*  9923:      */     public static final class Builder
/*  9924:      */       extends GeneratedMessage.Builder<Builder>
/*  9925:      */       implements MRServiceProtos.KillTaskRequestProtoOrBuilder
/*  9926:      */     {
/*  9927:      */       private int bitField0_;
/*  9928:      */       
/*  9929:      */       public static final Descriptors.Descriptor getDescriptor()
/*  9930:      */       {
/*  9931: 9416 */         return MRServiceProtos.internal_static_hadoop_mapreduce_KillTaskRequestProto_descriptor;
/*  9932:      */       }
/*  9933:      */       
/*  9934:      */       protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable()
/*  9935:      */       {
/*  9936: 9421 */         return MRServiceProtos.internal_static_hadoop_mapreduce_KillTaskRequestProto_fieldAccessorTable.ensureFieldAccessorsInitialized(MRServiceProtos.KillTaskRequestProto.class, Builder.class);
/*  9937:      */       }
/*  9938:      */       
/*  9939:      */       private Builder()
/*  9940:      */       {
/*  9941: 9428 */         maybeForceBuilderInitialization();
/*  9942:      */       }
/*  9943:      */       
/*  9944:      */       private Builder(GeneratedMessage.BuilderParent parent)
/*  9945:      */       {
/*  9946: 9433 */         super();
/*  9947: 9434 */         maybeForceBuilderInitialization();
/*  9948:      */       }
/*  9949:      */       
/*  9950:      */       private void maybeForceBuilderInitialization()
/*  9951:      */       {
/*  9952: 9437 */         if (MRServiceProtos.KillTaskRequestProto.alwaysUseFieldBuilders) {
/*  9953: 9438 */           getTaskIdFieldBuilder();
/*  9954:      */         }
/*  9955:      */       }
/*  9956:      */       
/*  9957:      */       private static Builder create()
/*  9958:      */       {
/*  9959: 9442 */         return new Builder();
/*  9960:      */       }
/*  9961:      */       
/*  9962:      */       public Builder clear()
/*  9963:      */       {
/*  9964: 9446 */         super.clear();
/*  9965: 9447 */         if (this.taskIdBuilder_ == null) {
/*  9966: 9448 */           this.taskId_ = MRProtos.TaskIdProto.getDefaultInstance();
/*  9967:      */         } else {
/*  9968: 9450 */           this.taskIdBuilder_.clear();
/*  9969:      */         }
/*  9970: 9452 */         this.bitField0_ &= 0xFFFFFFFE;
/*  9971: 9453 */         return this;
/*  9972:      */       }
/*  9973:      */       
/*  9974:      */       public Builder clone()
/*  9975:      */       {
/*  9976: 9457 */         return create().mergeFrom(buildPartial());
/*  9977:      */       }
/*  9978:      */       
/*  9979:      */       public Descriptors.Descriptor getDescriptorForType()
/*  9980:      */       {
/*  9981: 9462 */         return MRServiceProtos.internal_static_hadoop_mapreduce_KillTaskRequestProto_descriptor;
/*  9982:      */       }
/*  9983:      */       
/*  9984:      */       public MRServiceProtos.KillTaskRequestProto getDefaultInstanceForType()
/*  9985:      */       {
/*  9986: 9466 */         return MRServiceProtos.KillTaskRequestProto.getDefaultInstance();
/*  9987:      */       }
/*  9988:      */       
/*  9989:      */       public MRServiceProtos.KillTaskRequestProto build()
/*  9990:      */       {
/*  9991: 9470 */         MRServiceProtos.KillTaskRequestProto result = buildPartial();
/*  9992: 9471 */         if (!result.isInitialized()) {
/*  9993: 9472 */           throw newUninitializedMessageException(result);
/*  9994:      */         }
/*  9995: 9474 */         return result;
/*  9996:      */       }
/*  9997:      */       
/*  9998:      */       public MRServiceProtos.KillTaskRequestProto buildPartial()
/*  9999:      */       {
/* 10000: 9478 */         MRServiceProtos.KillTaskRequestProto result = new MRServiceProtos.KillTaskRequestProto(this, null);
/* 10001: 9479 */         int from_bitField0_ = this.bitField0_;
/* 10002: 9480 */         int to_bitField0_ = 0;
/* 10003: 9481 */         if ((from_bitField0_ & 0x1) == 1) {
/* 10004: 9482 */           to_bitField0_ |= 0x1;
/* 10005:      */         }
/* 10006: 9484 */         if (this.taskIdBuilder_ == null) {
/* 10007: 9485 */           result.taskId_ = this.taskId_;
/* 10008:      */         } else {
/* 10009: 9487 */           result.taskId_ = ((MRProtos.TaskIdProto)this.taskIdBuilder_.build());
/* 10010:      */         }
/* 10011: 9489 */         result.bitField0_ = to_bitField0_;
/* 10012: 9490 */         onBuilt();
/* 10013: 9491 */         return result;
/* 10014:      */       }
/* 10015:      */       
/* 10016:      */       public Builder mergeFrom(Message other)
/* 10017:      */       {
/* 10018: 9495 */         if ((other instanceof MRServiceProtos.KillTaskRequestProto)) {
/* 10019: 9496 */           return mergeFrom((MRServiceProtos.KillTaskRequestProto)other);
/* 10020:      */         }
/* 10021: 9498 */         super.mergeFrom(other);
/* 10022: 9499 */         return this;
/* 10023:      */       }
/* 10024:      */       
/* 10025:      */       public Builder mergeFrom(MRServiceProtos.KillTaskRequestProto other)
/* 10026:      */       {
/* 10027: 9504 */         if (other == MRServiceProtos.KillTaskRequestProto.getDefaultInstance()) {
/* 10028: 9504 */           return this;
/* 10029:      */         }
/* 10030: 9505 */         if (other.hasTaskId()) {
/* 10031: 9506 */           mergeTaskId(other.getTaskId());
/* 10032:      */         }
/* 10033: 9508 */         mergeUnknownFields(other.getUnknownFields());
/* 10034: 9509 */         return this;
/* 10035:      */       }
/* 10036:      */       
/* 10037:      */       public final boolean isInitialized()
/* 10038:      */       {
/* 10039: 9513 */         return true;
/* 10040:      */       }
/* 10041:      */       
/* 10042:      */       public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry)
/* 10043:      */         throws IOException
/* 10044:      */       {
/* 10045: 9520 */         MRServiceProtos.KillTaskRequestProto parsedMessage = null;
/* 10046:      */         try
/* 10047:      */         {
/* 10048: 9522 */           parsedMessage = (MRServiceProtos.KillTaskRequestProto)MRServiceProtos.KillTaskRequestProto.PARSER.parsePartialFrom(input, extensionRegistry);
/* 10049:      */         }
/* 10050:      */         catch (InvalidProtocolBufferException e)
/* 10051:      */         {
/* 10052: 9524 */           parsedMessage = (MRServiceProtos.KillTaskRequestProto)e.getUnfinishedMessage();
/* 10053: 9525 */           throw e;
/* 10054:      */         }
/* 10055:      */         finally
/* 10056:      */         {
/* 10057: 9527 */           if (parsedMessage != null) {
/* 10058: 9528 */             mergeFrom(parsedMessage);
/* 10059:      */           }
/* 10060:      */         }
/* 10061: 9531 */         return this;
/* 10062:      */       }
/* 10063:      */       
/* 10064: 9536 */       private MRProtos.TaskIdProto taskId_ = MRProtos.TaskIdProto.getDefaultInstance();
/* 10065:      */       private SingleFieldBuilder<MRProtos.TaskIdProto, MRProtos.TaskIdProto.Builder, MRProtos.TaskIdProtoOrBuilder> taskIdBuilder_;
/* 10066:      */       
/* 10067:      */       public boolean hasTaskId()
/* 10068:      */       {
/* 10069: 9543 */         return (this.bitField0_ & 0x1) == 1;
/* 10070:      */       }
/* 10071:      */       
/* 10072:      */       public MRProtos.TaskIdProto getTaskId()
/* 10073:      */       {
/* 10074: 9549 */         if (this.taskIdBuilder_ == null) {
/* 10075: 9550 */           return this.taskId_;
/* 10076:      */         }
/* 10077: 9552 */         return (MRProtos.TaskIdProto)this.taskIdBuilder_.getMessage();
/* 10078:      */       }
/* 10079:      */       
/* 10080:      */       public Builder setTaskId(MRProtos.TaskIdProto value)
/* 10081:      */       {
/* 10082: 9559 */         if (this.taskIdBuilder_ == null)
/* 10083:      */         {
/* 10084: 9560 */           if (value == null) {
/* 10085: 9561 */             throw new NullPointerException();
/* 10086:      */           }
/* 10087: 9563 */           this.taskId_ = value;
/* 10088: 9564 */           onChanged();
/* 10089:      */         }
/* 10090:      */         else
/* 10091:      */         {
/* 10092: 9566 */           this.taskIdBuilder_.setMessage(value);
/* 10093:      */         }
/* 10094: 9568 */         this.bitField0_ |= 0x1;
/* 10095: 9569 */         return this;
/* 10096:      */       }
/* 10097:      */       
/* 10098:      */       public Builder setTaskId(MRProtos.TaskIdProto.Builder builderForValue)
/* 10099:      */       {
/* 10100: 9576 */         if (this.taskIdBuilder_ == null)
/* 10101:      */         {
/* 10102: 9577 */           this.taskId_ = builderForValue.build();
/* 10103: 9578 */           onChanged();
/* 10104:      */         }
/* 10105:      */         else
/* 10106:      */         {
/* 10107: 9580 */           this.taskIdBuilder_.setMessage(builderForValue.build());
/* 10108:      */         }
/* 10109: 9582 */         this.bitField0_ |= 0x1;
/* 10110: 9583 */         return this;
/* 10111:      */       }
/* 10112:      */       
/* 10113:      */       public Builder mergeTaskId(MRProtos.TaskIdProto value)
/* 10114:      */       {
/* 10115: 9589 */         if (this.taskIdBuilder_ == null)
/* 10116:      */         {
/* 10117: 9590 */           if (((this.bitField0_ & 0x1) == 1) && (this.taskId_ != MRProtos.TaskIdProto.getDefaultInstance())) {
/* 10118: 9592 */             this.taskId_ = MRProtos.TaskIdProto.newBuilder(this.taskId_).mergeFrom(value).buildPartial();
/* 10119:      */           } else {
/* 10120: 9595 */             this.taskId_ = value;
/* 10121:      */           }
/* 10122: 9597 */           onChanged();
/* 10123:      */         }
/* 10124:      */         else
/* 10125:      */         {
/* 10126: 9599 */           this.taskIdBuilder_.mergeFrom(value);
/* 10127:      */         }
/* 10128: 9601 */         this.bitField0_ |= 0x1;
/* 10129: 9602 */         return this;
/* 10130:      */       }
/* 10131:      */       
/* 10132:      */       public Builder clearTaskId()
/* 10133:      */       {
/* 10134: 9608 */         if (this.taskIdBuilder_ == null)
/* 10135:      */         {
/* 10136: 9609 */           this.taskId_ = MRProtos.TaskIdProto.getDefaultInstance();
/* 10137: 9610 */           onChanged();
/* 10138:      */         }
/* 10139:      */         else
/* 10140:      */         {
/* 10141: 9612 */           this.taskIdBuilder_.clear();
/* 10142:      */         }
/* 10143: 9614 */         this.bitField0_ &= 0xFFFFFFFE;
/* 10144: 9615 */         return this;
/* 10145:      */       }
/* 10146:      */       
/* 10147:      */       public MRProtos.TaskIdProto.Builder getTaskIdBuilder()
/* 10148:      */       {
/* 10149: 9621 */         this.bitField0_ |= 0x1;
/* 10150: 9622 */         onChanged();
/* 10151: 9623 */         return (MRProtos.TaskIdProto.Builder)getTaskIdFieldBuilder().getBuilder();
/* 10152:      */       }
/* 10153:      */       
/* 10154:      */       public MRProtos.TaskIdProtoOrBuilder getTaskIdOrBuilder()
/* 10155:      */       {
/* 10156: 9629 */         if (this.taskIdBuilder_ != null) {
/* 10157: 9630 */           return (MRProtos.TaskIdProtoOrBuilder)this.taskIdBuilder_.getMessageOrBuilder();
/* 10158:      */         }
/* 10159: 9632 */         return this.taskId_;
/* 10160:      */       }
/* 10161:      */       
/* 10162:      */       private SingleFieldBuilder<MRProtos.TaskIdProto, MRProtos.TaskIdProto.Builder, MRProtos.TaskIdProtoOrBuilder> getTaskIdFieldBuilder()
/* 10163:      */       {
/* 10164: 9641 */         if (this.taskIdBuilder_ == null)
/* 10165:      */         {
/* 10166: 9642 */           this.taskIdBuilder_ = new SingleFieldBuilder(this.taskId_, getParentForChildren(), isClean());
/* 10167:      */           
/* 10168:      */ 
/* 10169:      */ 
/* 10170:      */ 
/* 10171: 9647 */           this.taskId_ = null;
/* 10172:      */         }
/* 10173: 9649 */         return this.taskIdBuilder_;
/* 10174:      */       }
/* 10175:      */     }
/* 10176:      */     
/* 10177:      */     static
/* 10178:      */     {
/* 10179: 9656 */       defaultInstance = new KillTaskRequestProto(true);
/* 10180: 9657 */       defaultInstance.initFields();
/* 10181:      */     }
/* 10182:      */   }
/* 10183:      */   
/* 10184:      */   public static abstract interface KillTaskResponseProtoOrBuilder
/* 10185:      */     extends MessageOrBuilder
/* 10186:      */   {}
/* 10187:      */   
/* 10188:      */   public static final class KillTaskResponseProto
/* 10189:      */     extends GeneratedMessage
/* 10190:      */     implements MRServiceProtos.KillTaskResponseProtoOrBuilder
/* 10191:      */   {
/* 10192:      */     private static final KillTaskResponseProto defaultInstance;
/* 10193:      */     private final UnknownFieldSet unknownFields;
/* 10194:      */     
/* 10195:      */     private KillTaskResponseProto(GeneratedMessage.Builder<?> builder)
/* 10196:      */     {
/* 10197: 9674 */       super();
/* 10198: 9675 */       this.unknownFields = builder.getUnknownFields();
/* 10199:      */     }
/* 10200:      */     
/* 10201:      */     private KillTaskResponseProto(boolean noInit)
/* 10202:      */     {
/* 10203: 9677 */       this.unknownFields = UnknownFieldSet.getDefaultInstance();
/* 10204:      */     }
/* 10205:      */     
/* 10206:      */     public static KillTaskResponseProto getDefaultInstance()
/* 10207:      */     {
/* 10208: 9681 */       return defaultInstance;
/* 10209:      */     }
/* 10210:      */     
/* 10211:      */     public KillTaskResponseProto getDefaultInstanceForType()
/* 10212:      */     {
/* 10213: 9685 */       return defaultInstance;
/* 10214:      */     }
/* 10215:      */     
/* 10216:      */     public final UnknownFieldSet getUnknownFields()
/* 10217:      */     {
/* 10218: 9692 */       return this.unknownFields;
/* 10219:      */     }
/* 10220:      */     
/* 10221:      */     private KillTaskResponseProto(CodedInputStream input, ExtensionRegistryLite extensionRegistry)
/* 10222:      */       throws InvalidProtocolBufferException
/* 10223:      */     {
/* 10224: 9698 */       initFields();
/* 10225: 9699 */       UnknownFieldSet.Builder unknownFields = UnknownFieldSet.newBuilder();
/* 10226:      */       try
/* 10227:      */       {
/* 10228: 9702 */         boolean done = false;
/* 10229: 9703 */         while (!done)
/* 10230:      */         {
/* 10231: 9704 */           int tag = input.readTag();
/* 10232: 9705 */           switch (tag)
/* 10233:      */           {
/* 10234:      */           case 0: 
/* 10235: 9707 */             done = true;
/* 10236: 9708 */             break;
/* 10237:      */           default: 
/* 10238: 9710 */             if (!parseUnknownField(input, unknownFields, extensionRegistry, tag)) {
/* 10239: 9712 */               done = true;
/* 10240:      */             }
/* 10241:      */             break;
/* 10242:      */           }
/* 10243:      */         }
/* 10244:      */       }
/* 10245:      */       catch (InvalidProtocolBufferException e)
/* 10246:      */       {
/* 10247: 9719 */         throw e.setUnfinishedMessage(this);
/* 10248:      */       }
/* 10249:      */       catch (IOException e)
/* 10250:      */       {
/* 10251: 9721 */         throw new InvalidProtocolBufferException(e.getMessage()).setUnfinishedMessage(this);
/* 10252:      */       }
/* 10253:      */       finally
/* 10254:      */       {
/* 10255: 9724 */         this.unknownFields = unknownFields.build();
/* 10256: 9725 */         makeExtensionsImmutable();
/* 10257:      */       }
/* 10258:      */     }
/* 10259:      */     
/* 10260:      */     public static final Descriptors.Descriptor getDescriptor()
/* 10261:      */     {
/* 10262: 9730 */       return MRServiceProtos.internal_static_hadoop_mapreduce_KillTaskResponseProto_descriptor;
/* 10263:      */     }
/* 10264:      */     
/* 10265:      */     protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable()
/* 10266:      */     {
/* 10267: 9735 */       return MRServiceProtos.internal_static_hadoop_mapreduce_KillTaskResponseProto_fieldAccessorTable.ensureFieldAccessorsInitialized(KillTaskResponseProto.class, Builder.class);
/* 10268:      */     }
/* 10269:      */     
/* 10270: 9740 */     public static Parser<KillTaskResponseProto> PARSER = new AbstractParser()
/* 10271:      */     {
/* 10272:      */       public MRServiceProtos.KillTaskResponseProto parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry)
/* 10273:      */         throws InvalidProtocolBufferException
/* 10274:      */       {
/* 10275: 9746 */         return new MRServiceProtos.KillTaskResponseProto(input, extensionRegistry, null);
/* 10276:      */       }
/* 10277:      */     };
/* 10278:      */     
/* 10279:      */     public Parser<KillTaskResponseProto> getParserForType()
/* 10280:      */     {
/* 10281: 9752 */       return PARSER;
/* 10282:      */     }
/* 10283:      */     
/* 10284: 9757 */     private byte memoizedIsInitialized = -1;
/* 10285:      */     
/* 10286:      */     private void initFields() {}
/* 10287:      */     
/* 10288:      */     public final boolean isInitialized()
/* 10289:      */     {
/* 10290: 9759 */       byte isInitialized = this.memoizedIsInitialized;
/* 10291: 9760 */       if (isInitialized != -1) {
/* 10292: 9760 */         return isInitialized == 1;
/* 10293:      */       }
/* 10294: 9762 */       this.memoizedIsInitialized = 1;
/* 10295: 9763 */       return true;
/* 10296:      */     }
/* 10297:      */     
/* 10298:      */     public void writeTo(CodedOutputStream output)
/* 10299:      */       throws IOException
/* 10300:      */     {
/* 10301: 9768 */       getSerializedSize();
/* 10302: 9769 */       getUnknownFields().writeTo(output);
/* 10303:      */     }
/* 10304:      */     
/* 10305: 9772 */     private int memoizedSerializedSize = -1;
/* 10306:      */     private static final long serialVersionUID = 0L;
/* 10307:      */     
/* 10308:      */     public int getSerializedSize()
/* 10309:      */     {
/* 10310: 9774 */       int size = this.memoizedSerializedSize;
/* 10311: 9775 */       if (size != -1) {
/* 10312: 9775 */         return size;
/* 10313:      */       }
/* 10314: 9777 */       size = 0;
/* 10315: 9778 */       size += getUnknownFields().getSerializedSize();
/* 10316: 9779 */       this.memoizedSerializedSize = size;
/* 10317: 9780 */       return size;
/* 10318:      */     }
/* 10319:      */     
/* 10320:      */     protected Object writeReplace()
/* 10321:      */       throws ObjectStreamException
/* 10322:      */     {
/* 10323: 9787 */       return super.writeReplace();
/* 10324:      */     }
/* 10325:      */     
/* 10326:      */     public boolean equals(Object obj)
/* 10327:      */     {
/* 10328: 9792 */       if (obj == this) {
/* 10329: 9793 */         return true;
/* 10330:      */       }
/* 10331: 9795 */       if (!(obj instanceof KillTaskResponseProto)) {
/* 10332: 9796 */         return super.equals(obj);
/* 10333:      */       }
/* 10334: 9798 */       KillTaskResponseProto other = (KillTaskResponseProto)obj;
/* 10335:      */       
/* 10336: 9800 */       boolean result = true;
/* 10337: 9801 */       result = (result) && (getUnknownFields().equals(other.getUnknownFields()));
/* 10338:      */       
/* 10339: 9803 */       return result;
/* 10340:      */     }
/* 10341:      */     
/* 10342: 9806 */     private int memoizedHashCode = 0;
/* 10343:      */     
/* 10344:      */     public int hashCode()
/* 10345:      */     {
/* 10346: 9809 */       if (this.memoizedHashCode != 0) {
/* 10347: 9810 */         return this.memoizedHashCode;
/* 10348:      */       }
/* 10349: 9812 */       int hash = 41;
/* 10350: 9813 */       hash = 19 * hash + getDescriptorForType().hashCode();
/* 10351: 9814 */       hash = 29 * hash + getUnknownFields().hashCode();
/* 10352: 9815 */       this.memoizedHashCode = hash;
/* 10353: 9816 */       return hash;
/* 10354:      */     }
/* 10355:      */     
/* 10356:      */     public static KillTaskResponseProto parseFrom(ByteString data)
/* 10357:      */       throws InvalidProtocolBufferException
/* 10358:      */     {
/* 10359: 9822 */       return (KillTaskResponseProto)PARSER.parseFrom(data);
/* 10360:      */     }
/* 10361:      */     
/* 10362:      */     public static KillTaskResponseProto parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry)
/* 10363:      */       throws InvalidProtocolBufferException
/* 10364:      */     {
/* 10365: 9828 */       return (KillTaskResponseProto)PARSER.parseFrom(data, extensionRegistry);
/* 10366:      */     }
/* 10367:      */     
/* 10368:      */     public static KillTaskResponseProto parseFrom(byte[] data)
/* 10369:      */       throws InvalidProtocolBufferException
/* 10370:      */     {
/* 10371: 9832 */       return (KillTaskResponseProto)PARSER.parseFrom(data);
/* 10372:      */     }
/* 10373:      */     
/* 10374:      */     public static KillTaskResponseProto parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry)
/* 10375:      */       throws InvalidProtocolBufferException
/* 10376:      */     {
/* 10377: 9838 */       return (KillTaskResponseProto)PARSER.parseFrom(data, extensionRegistry);
/* 10378:      */     }
/* 10379:      */     
/* 10380:      */     public static KillTaskResponseProto parseFrom(InputStream input)
/* 10381:      */       throws IOException
/* 10382:      */     {
/* 10383: 9842 */       return (KillTaskResponseProto)PARSER.parseFrom(input);
/* 10384:      */     }
/* 10385:      */     
/* 10386:      */     public static KillTaskResponseProto parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry)
/* 10387:      */       throws IOException
/* 10388:      */     {
/* 10389: 9848 */       return (KillTaskResponseProto)PARSER.parseFrom(input, extensionRegistry);
/* 10390:      */     }
/* 10391:      */     
/* 10392:      */     public static KillTaskResponseProto parseDelimitedFrom(InputStream input)
/* 10393:      */       throws IOException
/* 10394:      */     {
/* 10395: 9852 */       return (KillTaskResponseProto)PARSER.parseDelimitedFrom(input);
/* 10396:      */     }
/* 10397:      */     
/* 10398:      */     public static KillTaskResponseProto parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry)
/* 10399:      */       throws IOException
/* 10400:      */     {
/* 10401: 9858 */       return (KillTaskResponseProto)PARSER.parseDelimitedFrom(input, extensionRegistry);
/* 10402:      */     }
/* 10403:      */     
/* 10404:      */     public static KillTaskResponseProto parseFrom(CodedInputStream input)
/* 10405:      */       throws IOException
/* 10406:      */     {
/* 10407: 9863 */       return (KillTaskResponseProto)PARSER.parseFrom(input);
/* 10408:      */     }
/* 10409:      */     
/* 10410:      */     public static KillTaskResponseProto parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry)
/* 10411:      */       throws IOException
/* 10412:      */     {
/* 10413: 9869 */       return (KillTaskResponseProto)PARSER.parseFrom(input, extensionRegistry);
/* 10414:      */     }
/* 10415:      */     
/* 10416:      */     public static Builder newBuilder()
/* 10417:      */     {
/* 10418: 9872 */       return Builder.access$15600();
/* 10419:      */     }
/* 10420:      */     
/* 10421:      */     public Builder newBuilderForType()
/* 10422:      */     {
/* 10423: 9873 */       return newBuilder();
/* 10424:      */     }
/* 10425:      */     
/* 10426:      */     public static Builder newBuilder(KillTaskResponseProto prototype)
/* 10427:      */     {
/* 10428: 9875 */       return newBuilder().mergeFrom(prototype);
/* 10429:      */     }
/* 10430:      */     
/* 10431:      */     public Builder toBuilder()
/* 10432:      */     {
/* 10433: 9877 */       return newBuilder(this);
/* 10434:      */     }
/* 10435:      */     
/* 10436:      */     protected Builder newBuilderForType(GeneratedMessage.BuilderParent parent)
/* 10437:      */     {
/* 10438: 9882 */       Builder builder = new Builder(parent, null);
/* 10439: 9883 */       return builder;
/* 10440:      */     }
/* 10441:      */     
/* 10442:      */     public static final class Builder
/* 10443:      */       extends GeneratedMessage.Builder<Builder>
/* 10444:      */       implements MRServiceProtos.KillTaskResponseProtoOrBuilder
/* 10445:      */     {
/* 10446:      */       public static final Descriptors.Descriptor getDescriptor()
/* 10447:      */       {
/* 10448: 9893 */         return MRServiceProtos.internal_static_hadoop_mapreduce_KillTaskResponseProto_descriptor;
/* 10449:      */       }
/* 10450:      */       
/* 10451:      */       protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable()
/* 10452:      */       {
/* 10453: 9898 */         return MRServiceProtos.internal_static_hadoop_mapreduce_KillTaskResponseProto_fieldAccessorTable.ensureFieldAccessorsInitialized(MRServiceProtos.KillTaskResponseProto.class, Builder.class);
/* 10454:      */       }
/* 10455:      */       
/* 10456:      */       private Builder()
/* 10457:      */       {
/* 10458: 9905 */         maybeForceBuilderInitialization();
/* 10459:      */       }
/* 10460:      */       
/* 10461:      */       private Builder(GeneratedMessage.BuilderParent parent)
/* 10462:      */       {
/* 10463: 9910 */         super();
/* 10464: 9911 */         maybeForceBuilderInitialization();
/* 10465:      */       }
/* 10466:      */       
/* 10467:      */       private void maybeForceBuilderInitialization()
/* 10468:      */       {
/* 10469: 9914 */         if (MRServiceProtos.KillTaskResponseProto.alwaysUseFieldBuilders) {}
/* 10470:      */       }
/* 10471:      */       
/* 10472:      */       private static Builder create()
/* 10473:      */       {
/* 10474: 9918 */         return new Builder();
/* 10475:      */       }
/* 10476:      */       
/* 10477:      */       public Builder clear()
/* 10478:      */       {
/* 10479: 9922 */         super.clear();
/* 10480: 9923 */         return this;
/* 10481:      */       }
/* 10482:      */       
/* 10483:      */       public Builder clone()
/* 10484:      */       {
/* 10485: 9927 */         return create().mergeFrom(buildPartial());
/* 10486:      */       }
/* 10487:      */       
/* 10488:      */       public Descriptors.Descriptor getDescriptorForType()
/* 10489:      */       {
/* 10490: 9932 */         return MRServiceProtos.internal_static_hadoop_mapreduce_KillTaskResponseProto_descriptor;
/* 10491:      */       }
/* 10492:      */       
/* 10493:      */       public MRServiceProtos.KillTaskResponseProto getDefaultInstanceForType()
/* 10494:      */       {
/* 10495: 9936 */         return MRServiceProtos.KillTaskResponseProto.getDefaultInstance();
/* 10496:      */       }
/* 10497:      */       
/* 10498:      */       public MRServiceProtos.KillTaskResponseProto build()
/* 10499:      */       {
/* 10500: 9940 */         MRServiceProtos.KillTaskResponseProto result = buildPartial();
/* 10501: 9941 */         if (!result.isInitialized()) {
/* 10502: 9942 */           throw newUninitializedMessageException(result);
/* 10503:      */         }
/* 10504: 9944 */         return result;
/* 10505:      */       }
/* 10506:      */       
/* 10507:      */       public MRServiceProtos.KillTaskResponseProto buildPartial()
/* 10508:      */       {
/* 10509: 9948 */         MRServiceProtos.KillTaskResponseProto result = new MRServiceProtos.KillTaskResponseProto(this, null);
/* 10510: 9949 */         onBuilt();
/* 10511: 9950 */         return result;
/* 10512:      */       }
/* 10513:      */       
/* 10514:      */       public Builder mergeFrom(Message other)
/* 10515:      */       {
/* 10516: 9954 */         if ((other instanceof MRServiceProtos.KillTaskResponseProto)) {
/* 10517: 9955 */           return mergeFrom((MRServiceProtos.KillTaskResponseProto)other);
/* 10518:      */         }
/* 10519: 9957 */         super.mergeFrom(other);
/* 10520: 9958 */         return this;
/* 10521:      */       }
/* 10522:      */       
/* 10523:      */       public Builder mergeFrom(MRServiceProtos.KillTaskResponseProto other)
/* 10524:      */       {
/* 10525: 9963 */         if (other == MRServiceProtos.KillTaskResponseProto.getDefaultInstance()) {
/* 10526: 9963 */           return this;
/* 10527:      */         }
/* 10528: 9964 */         mergeUnknownFields(other.getUnknownFields());
/* 10529: 9965 */         return this;
/* 10530:      */       }
/* 10531:      */       
/* 10532:      */       public final boolean isInitialized()
/* 10533:      */       {
/* 10534: 9969 */         return true;
/* 10535:      */       }
/* 10536:      */       
/* 10537:      */       public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry)
/* 10538:      */         throws IOException
/* 10539:      */       {
/* 10540: 9976 */         MRServiceProtos.KillTaskResponseProto parsedMessage = null;
/* 10541:      */         try
/* 10542:      */         {
/* 10543: 9978 */           parsedMessage = (MRServiceProtos.KillTaskResponseProto)MRServiceProtos.KillTaskResponseProto.PARSER.parsePartialFrom(input, extensionRegistry);
/* 10544:      */         }
/* 10545:      */         catch (InvalidProtocolBufferException e)
/* 10546:      */         {
/* 10547: 9980 */           parsedMessage = (MRServiceProtos.KillTaskResponseProto)e.getUnfinishedMessage();
/* 10548: 9981 */           throw e;
/* 10549:      */         }
/* 10550:      */         finally
/* 10551:      */         {
/* 10552: 9983 */           if (parsedMessage != null) {
/* 10553: 9984 */             mergeFrom(parsedMessage);
/* 10554:      */           }
/* 10555:      */         }
/* 10556: 9987 */         return this;
/* 10557:      */       }
/* 10558:      */     }
/* 10559:      */     
/* 10560:      */     static
/* 10561:      */     {
/* 10562: 9994 */       defaultInstance = new KillTaskResponseProto(true);
/* 10563: 9995 */       defaultInstance.initFields();
/* 10564:      */     }
/* 10565:      */   }
/* 10566:      */   
/* 10567:      */   public static abstract interface KillTaskAttemptRequestProtoOrBuilder
/* 10568:      */     extends MessageOrBuilder
/* 10569:      */   {
/* 10570:      */     public abstract boolean hasTaskAttemptId();
/* 10571:      */     
/* 10572:      */     public abstract MRProtos.TaskAttemptIdProto getTaskAttemptId();
/* 10573:      */     
/* 10574:      */     public abstract MRProtos.TaskAttemptIdProtoOrBuilder getTaskAttemptIdOrBuilder();
/* 10575:      */   }
/* 10576:      */   
/* 10577:      */   public static final class KillTaskAttemptRequestProto
/* 10578:      */     extends GeneratedMessage
/* 10579:      */     implements MRServiceProtos.KillTaskAttemptRequestProtoOrBuilder
/* 10580:      */   {
/* 10581:      */     private static final KillTaskAttemptRequestProto defaultInstance;
/* 10582:      */     private final UnknownFieldSet unknownFields;
/* 10583:      */     
/* 10584:      */     private KillTaskAttemptRequestProto(GeneratedMessage.Builder<?> builder)
/* 10585:      */     {
/* 10586:10026 */       super();
/* 10587:10027 */       this.unknownFields = builder.getUnknownFields();
/* 10588:      */     }
/* 10589:      */     
/* 10590:      */     private KillTaskAttemptRequestProto(boolean noInit)
/* 10591:      */     {
/* 10592:10029 */       this.unknownFields = UnknownFieldSet.getDefaultInstance();
/* 10593:      */     }
/* 10594:      */     
/* 10595:      */     public static KillTaskAttemptRequestProto getDefaultInstance()
/* 10596:      */     {
/* 10597:10033 */       return defaultInstance;
/* 10598:      */     }
/* 10599:      */     
/* 10600:      */     public KillTaskAttemptRequestProto getDefaultInstanceForType()
/* 10601:      */     {
/* 10602:10037 */       return defaultInstance;
/* 10603:      */     }
/* 10604:      */     
/* 10605:      */     public final UnknownFieldSet getUnknownFields()
/* 10606:      */     {
/* 10607:10044 */       return this.unknownFields;
/* 10608:      */     }
/* 10609:      */     
/* 10610:      */     private KillTaskAttemptRequestProto(CodedInputStream input, ExtensionRegistryLite extensionRegistry)
/* 10611:      */       throws InvalidProtocolBufferException
/* 10612:      */     {
/* 10613:10050 */       initFields();
/* 10614:10051 */       int mutable_bitField0_ = 0;
/* 10615:10052 */       UnknownFieldSet.Builder unknownFields = UnknownFieldSet.newBuilder();
/* 10616:      */       try
/* 10617:      */       {
/* 10618:10055 */         boolean done = false;
/* 10619:10056 */         while (!done)
/* 10620:      */         {
/* 10621:10057 */           int tag = input.readTag();
/* 10622:10058 */           switch (tag)
/* 10623:      */           {
/* 10624:      */           case 0: 
/* 10625:10060 */             done = true;
/* 10626:10061 */             break;
/* 10627:      */           default: 
/* 10628:10063 */             if (!parseUnknownField(input, unknownFields, extensionRegistry, tag)) {
/* 10629:10065 */               done = true;
/* 10630:      */             }
/* 10631:      */             break;
/* 10632:      */           case 10: 
/* 10633:10070 */             MRProtos.TaskAttemptIdProto.Builder subBuilder = null;
/* 10634:10071 */             if ((this.bitField0_ & 0x1) == 1) {
/* 10635:10072 */               subBuilder = this.taskAttemptId_.toBuilder();
/* 10636:      */             }
/* 10637:10074 */             this.taskAttemptId_ = ((MRProtos.TaskAttemptIdProto)input.readMessage(MRProtos.TaskAttemptIdProto.PARSER, extensionRegistry));
/* 10638:10075 */             if (subBuilder != null)
/* 10639:      */             {
/* 10640:10076 */               subBuilder.mergeFrom(this.taskAttemptId_);
/* 10641:10077 */               this.taskAttemptId_ = subBuilder.buildPartial();
/* 10642:      */             }
/* 10643:10079 */             this.bitField0_ |= 0x1;
/* 10644:      */           }
/* 10645:      */         }
/* 10646:      */       }
/* 10647:      */       catch (InvalidProtocolBufferException e)
/* 10648:      */       {
/* 10649:10085 */         throw e.setUnfinishedMessage(this);
/* 10650:      */       }
/* 10651:      */       catch (IOException e)
/* 10652:      */       {
/* 10653:10087 */         throw new InvalidProtocolBufferException(e.getMessage()).setUnfinishedMessage(this);
/* 10654:      */       }
/* 10655:      */       finally
/* 10656:      */       {
/* 10657:10090 */         this.unknownFields = unknownFields.build();
/* 10658:10091 */         makeExtensionsImmutable();
/* 10659:      */       }
/* 10660:      */     }
/* 10661:      */     
/* 10662:      */     public static final Descriptors.Descriptor getDescriptor()
/* 10663:      */     {
/* 10664:10096 */       return MRServiceProtos.internal_static_hadoop_mapreduce_KillTaskAttemptRequestProto_descriptor;
/* 10665:      */     }
/* 10666:      */     
/* 10667:      */     protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable()
/* 10668:      */     {
/* 10669:10101 */       return MRServiceProtos.internal_static_hadoop_mapreduce_KillTaskAttemptRequestProto_fieldAccessorTable.ensureFieldAccessorsInitialized(KillTaskAttemptRequestProto.class, Builder.class);
/* 10670:      */     }
/* 10671:      */     
/* 10672:10106 */     public static Parser<KillTaskAttemptRequestProto> PARSER = new AbstractParser()
/* 10673:      */     {
/* 10674:      */       public MRServiceProtos.KillTaskAttemptRequestProto parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry)
/* 10675:      */         throws InvalidProtocolBufferException
/* 10676:      */       {
/* 10677:10112 */         return new MRServiceProtos.KillTaskAttemptRequestProto(input, extensionRegistry, null);
/* 10678:      */       }
/* 10679:      */     };
/* 10680:      */     private int bitField0_;
/* 10681:      */     public static final int TASK_ATTEMPT_ID_FIELD_NUMBER = 1;
/* 10682:      */     private MRProtos.TaskAttemptIdProto taskAttemptId_;
/* 10683:      */     
/* 10684:      */     public Parser<KillTaskAttemptRequestProto> getParserForType()
/* 10685:      */     {
/* 10686:10118 */       return PARSER;
/* 10687:      */     }
/* 10688:      */     
/* 10689:      */     public boolean hasTaskAttemptId()
/* 10690:      */     {
/* 10691:10129 */       return (this.bitField0_ & 0x1) == 1;
/* 10692:      */     }
/* 10693:      */     
/* 10694:      */     public MRProtos.TaskAttemptIdProto getTaskAttemptId()
/* 10695:      */     {
/* 10696:10135 */       return this.taskAttemptId_;
/* 10697:      */     }
/* 10698:      */     
/* 10699:      */     public MRProtos.TaskAttemptIdProtoOrBuilder getTaskAttemptIdOrBuilder()
/* 10700:      */     {
/* 10701:10141 */       return this.taskAttemptId_;
/* 10702:      */     }
/* 10703:      */     
/* 10704:      */     private void initFields()
/* 10705:      */     {
/* 10706:10145 */       this.taskAttemptId_ = MRProtos.TaskAttemptIdProto.getDefaultInstance();
/* 10707:      */     }
/* 10708:      */     
/* 10709:10147 */     private byte memoizedIsInitialized = -1;
/* 10710:      */     
/* 10711:      */     public final boolean isInitialized()
/* 10712:      */     {
/* 10713:10149 */       byte isInitialized = this.memoizedIsInitialized;
/* 10714:10150 */       if (isInitialized != -1) {
/* 10715:10150 */         return isInitialized == 1;
/* 10716:      */       }
/* 10717:10152 */       this.memoizedIsInitialized = 1;
/* 10718:10153 */       return true;
/* 10719:      */     }
/* 10720:      */     
/* 10721:      */     public void writeTo(CodedOutputStream output)
/* 10722:      */       throws IOException
/* 10723:      */     {
/* 10724:10158 */       getSerializedSize();
/* 10725:10159 */       if ((this.bitField0_ & 0x1) == 1) {
/* 10726:10160 */         output.writeMessage(1, this.taskAttemptId_);
/* 10727:      */       }
/* 10728:10162 */       getUnknownFields().writeTo(output);
/* 10729:      */     }
/* 10730:      */     
/* 10731:10165 */     private int memoizedSerializedSize = -1;
/* 10732:      */     private static final long serialVersionUID = 0L;
/* 10733:      */     
/* 10734:      */     public int getSerializedSize()
/* 10735:      */     {
/* 10736:10167 */       int size = this.memoizedSerializedSize;
/* 10737:10168 */       if (size != -1) {
/* 10738:10168 */         return size;
/* 10739:      */       }
/* 10740:10170 */       size = 0;
/* 10741:10171 */       if ((this.bitField0_ & 0x1) == 1) {
/* 10742:10172 */         size += CodedOutputStream.computeMessageSize(1, this.taskAttemptId_);
/* 10743:      */       }
/* 10744:10175 */       size += getUnknownFields().getSerializedSize();
/* 10745:10176 */       this.memoizedSerializedSize = size;
/* 10746:10177 */       return size;
/* 10747:      */     }
/* 10748:      */     
/* 10749:      */     protected Object writeReplace()
/* 10750:      */       throws ObjectStreamException
/* 10751:      */     {
/* 10752:10184 */       return super.writeReplace();
/* 10753:      */     }
/* 10754:      */     
/* 10755:      */     public boolean equals(Object obj)
/* 10756:      */     {
/* 10757:10189 */       if (obj == this) {
/* 10758:10190 */         return true;
/* 10759:      */       }
/* 10760:10192 */       if (!(obj instanceof KillTaskAttemptRequestProto)) {
/* 10761:10193 */         return super.equals(obj);
/* 10762:      */       }
/* 10763:10195 */       KillTaskAttemptRequestProto other = (KillTaskAttemptRequestProto)obj;
/* 10764:      */       
/* 10765:10197 */       boolean result = true;
/* 10766:10198 */       result = (result) && (hasTaskAttemptId() == other.hasTaskAttemptId());
/* 10767:10199 */       if (hasTaskAttemptId()) {
/* 10768:10200 */         result = (result) && (getTaskAttemptId().equals(other.getTaskAttemptId()));
/* 10769:      */       }
/* 10770:10203 */       result = (result) && (getUnknownFields().equals(other.getUnknownFields()));
/* 10771:      */       
/* 10772:10205 */       return result;
/* 10773:      */     }
/* 10774:      */     
/* 10775:10208 */     private int memoizedHashCode = 0;
/* 10776:      */     
/* 10777:      */     public int hashCode()
/* 10778:      */     {
/* 10779:10211 */       if (this.memoizedHashCode != 0) {
/* 10780:10212 */         return this.memoizedHashCode;
/* 10781:      */       }
/* 10782:10214 */       int hash = 41;
/* 10783:10215 */       hash = 19 * hash + getDescriptorForType().hashCode();
/* 10784:10216 */       if (hasTaskAttemptId())
/* 10785:      */       {
/* 10786:10217 */         hash = 37 * hash + 1;
/* 10787:10218 */         hash = 53 * hash + getTaskAttemptId().hashCode();
/* 10788:      */       }
/* 10789:10220 */       hash = 29 * hash + getUnknownFields().hashCode();
/* 10790:10221 */       this.memoizedHashCode = hash;
/* 10791:10222 */       return hash;
/* 10792:      */     }
/* 10793:      */     
/* 10794:      */     public static KillTaskAttemptRequestProto parseFrom(ByteString data)
/* 10795:      */       throws InvalidProtocolBufferException
/* 10796:      */     {
/* 10797:10228 */       return (KillTaskAttemptRequestProto)PARSER.parseFrom(data);
/* 10798:      */     }
/* 10799:      */     
/* 10800:      */     public static KillTaskAttemptRequestProto parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry)
/* 10801:      */       throws InvalidProtocolBufferException
/* 10802:      */     {
/* 10803:10234 */       return (KillTaskAttemptRequestProto)PARSER.parseFrom(data, extensionRegistry);
/* 10804:      */     }
/* 10805:      */     
/* 10806:      */     public static KillTaskAttemptRequestProto parseFrom(byte[] data)
/* 10807:      */       throws InvalidProtocolBufferException
/* 10808:      */     {
/* 10809:10238 */       return (KillTaskAttemptRequestProto)PARSER.parseFrom(data);
/* 10810:      */     }
/* 10811:      */     
/* 10812:      */     public static KillTaskAttemptRequestProto parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry)
/* 10813:      */       throws InvalidProtocolBufferException
/* 10814:      */     {
/* 10815:10244 */       return (KillTaskAttemptRequestProto)PARSER.parseFrom(data, extensionRegistry);
/* 10816:      */     }
/* 10817:      */     
/* 10818:      */     public static KillTaskAttemptRequestProto parseFrom(InputStream input)
/* 10819:      */       throws IOException
/* 10820:      */     {
/* 10821:10248 */       return (KillTaskAttemptRequestProto)PARSER.parseFrom(input);
/* 10822:      */     }
/* 10823:      */     
/* 10824:      */     public static KillTaskAttemptRequestProto parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry)
/* 10825:      */       throws IOException
/* 10826:      */     {
/* 10827:10254 */       return (KillTaskAttemptRequestProto)PARSER.parseFrom(input, extensionRegistry);
/* 10828:      */     }
/* 10829:      */     
/* 10830:      */     public static KillTaskAttemptRequestProto parseDelimitedFrom(InputStream input)
/* 10831:      */       throws IOException
/* 10832:      */     {
/* 10833:10258 */       return (KillTaskAttemptRequestProto)PARSER.parseDelimitedFrom(input);
/* 10834:      */     }
/* 10835:      */     
/* 10836:      */     public static KillTaskAttemptRequestProto parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry)
/* 10837:      */       throws IOException
/* 10838:      */     {
/* 10839:10264 */       return (KillTaskAttemptRequestProto)PARSER.parseDelimitedFrom(input, extensionRegistry);
/* 10840:      */     }
/* 10841:      */     
/* 10842:      */     public static KillTaskAttemptRequestProto parseFrom(CodedInputStream input)
/* 10843:      */       throws IOException
/* 10844:      */     {
/* 10845:10269 */       return (KillTaskAttemptRequestProto)PARSER.parseFrom(input);
/* 10846:      */     }
/* 10847:      */     
/* 10848:      */     public static KillTaskAttemptRequestProto parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry)
/* 10849:      */       throws IOException
/* 10850:      */     {
/* 10851:10275 */       return (KillTaskAttemptRequestProto)PARSER.parseFrom(input, extensionRegistry);
/* 10852:      */     }
/* 10853:      */     
/* 10854:      */     public static Builder newBuilder()
/* 10855:      */     {
/* 10856:10278 */       return Builder.access$16300();
/* 10857:      */     }
/* 10858:      */     
/* 10859:      */     public Builder newBuilderForType()
/* 10860:      */     {
/* 10861:10279 */       return newBuilder();
/* 10862:      */     }
/* 10863:      */     
/* 10864:      */     public static Builder newBuilder(KillTaskAttemptRequestProto prototype)
/* 10865:      */     {
/* 10866:10281 */       return newBuilder().mergeFrom(prototype);
/* 10867:      */     }
/* 10868:      */     
/* 10869:      */     public Builder toBuilder()
/* 10870:      */     {
/* 10871:10283 */       return newBuilder(this);
/* 10872:      */     }
/* 10873:      */     
/* 10874:      */     protected Builder newBuilderForType(GeneratedMessage.BuilderParent parent)
/* 10875:      */     {
/* 10876:10288 */       Builder builder = new Builder(parent, null);
/* 10877:10289 */       return builder;
/* 10878:      */     }
/* 10879:      */     
/* 10880:      */     public static final class Builder
/* 10881:      */       extends GeneratedMessage.Builder<Builder>
/* 10882:      */       implements MRServiceProtos.KillTaskAttemptRequestProtoOrBuilder
/* 10883:      */     {
/* 10884:      */       private int bitField0_;
/* 10885:      */       
/* 10886:      */       public static final Descriptors.Descriptor getDescriptor()
/* 10887:      */       {
/* 10888:10299 */         return MRServiceProtos.internal_static_hadoop_mapreduce_KillTaskAttemptRequestProto_descriptor;
/* 10889:      */       }
/* 10890:      */       
/* 10891:      */       protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable()
/* 10892:      */       {
/* 10893:10304 */         return MRServiceProtos.internal_static_hadoop_mapreduce_KillTaskAttemptRequestProto_fieldAccessorTable.ensureFieldAccessorsInitialized(MRServiceProtos.KillTaskAttemptRequestProto.class, Builder.class);
/* 10894:      */       }
/* 10895:      */       
/* 10896:      */       private Builder()
/* 10897:      */       {
/* 10898:10311 */         maybeForceBuilderInitialization();
/* 10899:      */       }
/* 10900:      */       
/* 10901:      */       private Builder(GeneratedMessage.BuilderParent parent)
/* 10902:      */       {
/* 10903:10316 */         super();
/* 10904:10317 */         maybeForceBuilderInitialization();
/* 10905:      */       }
/* 10906:      */       
/* 10907:      */       private void maybeForceBuilderInitialization()
/* 10908:      */       {
/* 10909:10320 */         if (MRServiceProtos.KillTaskAttemptRequestProto.alwaysUseFieldBuilders) {
/* 10910:10321 */           getTaskAttemptIdFieldBuilder();
/* 10911:      */         }
/* 10912:      */       }
/* 10913:      */       
/* 10914:      */       private static Builder create()
/* 10915:      */       {
/* 10916:10325 */         return new Builder();
/* 10917:      */       }
/* 10918:      */       
/* 10919:      */       public Builder clear()
/* 10920:      */       {
/* 10921:10329 */         super.clear();
/* 10922:10330 */         if (this.taskAttemptIdBuilder_ == null) {
/* 10923:10331 */           this.taskAttemptId_ = MRProtos.TaskAttemptIdProto.getDefaultInstance();
/* 10924:      */         } else {
/* 10925:10333 */           this.taskAttemptIdBuilder_.clear();
/* 10926:      */         }
/* 10927:10335 */         this.bitField0_ &= 0xFFFFFFFE;
/* 10928:10336 */         return this;
/* 10929:      */       }
/* 10930:      */       
/* 10931:      */       public Builder clone()
/* 10932:      */       {
/* 10933:10340 */         return create().mergeFrom(buildPartial());
/* 10934:      */       }
/* 10935:      */       
/* 10936:      */       public Descriptors.Descriptor getDescriptorForType()
/* 10937:      */       {
/* 10938:10345 */         return MRServiceProtos.internal_static_hadoop_mapreduce_KillTaskAttemptRequestProto_descriptor;
/* 10939:      */       }
/* 10940:      */       
/* 10941:      */       public MRServiceProtos.KillTaskAttemptRequestProto getDefaultInstanceForType()
/* 10942:      */       {
/* 10943:10349 */         return MRServiceProtos.KillTaskAttemptRequestProto.getDefaultInstance();
/* 10944:      */       }
/* 10945:      */       
/* 10946:      */       public MRServiceProtos.KillTaskAttemptRequestProto build()
/* 10947:      */       {
/* 10948:10353 */         MRServiceProtos.KillTaskAttemptRequestProto result = buildPartial();
/* 10949:10354 */         if (!result.isInitialized()) {
/* 10950:10355 */           throw newUninitializedMessageException(result);
/* 10951:      */         }
/* 10952:10357 */         return result;
/* 10953:      */       }
/* 10954:      */       
/* 10955:      */       public MRServiceProtos.KillTaskAttemptRequestProto buildPartial()
/* 10956:      */       {
/* 10957:10361 */         MRServiceProtos.KillTaskAttemptRequestProto result = new MRServiceProtos.KillTaskAttemptRequestProto(this, null);
/* 10958:10362 */         int from_bitField0_ = this.bitField0_;
/* 10959:10363 */         int to_bitField0_ = 0;
/* 10960:10364 */         if ((from_bitField0_ & 0x1) == 1) {
/* 10961:10365 */           to_bitField0_ |= 0x1;
/* 10962:      */         }
/* 10963:10367 */         if (this.taskAttemptIdBuilder_ == null) {
/* 10964:10368 */           result.taskAttemptId_ = this.taskAttemptId_;
/* 10965:      */         } else {
/* 10966:10370 */           result.taskAttemptId_ = ((MRProtos.TaskAttemptIdProto)this.taskAttemptIdBuilder_.build());
/* 10967:      */         }
/* 10968:10372 */         result.bitField0_ = to_bitField0_;
/* 10969:10373 */         onBuilt();
/* 10970:10374 */         return result;
/* 10971:      */       }
/* 10972:      */       
/* 10973:      */       public Builder mergeFrom(Message other)
/* 10974:      */       {
/* 10975:10378 */         if ((other instanceof MRServiceProtos.KillTaskAttemptRequestProto)) {
/* 10976:10379 */           return mergeFrom((MRServiceProtos.KillTaskAttemptRequestProto)other);
/* 10977:      */         }
/* 10978:10381 */         super.mergeFrom(other);
/* 10979:10382 */         return this;
/* 10980:      */       }
/* 10981:      */       
/* 10982:      */       public Builder mergeFrom(MRServiceProtos.KillTaskAttemptRequestProto other)
/* 10983:      */       {
/* 10984:10387 */         if (other == MRServiceProtos.KillTaskAttemptRequestProto.getDefaultInstance()) {
/* 10985:10387 */           return this;
/* 10986:      */         }
/* 10987:10388 */         if (other.hasTaskAttemptId()) {
/* 10988:10389 */           mergeTaskAttemptId(other.getTaskAttemptId());
/* 10989:      */         }
/* 10990:10391 */         mergeUnknownFields(other.getUnknownFields());
/* 10991:10392 */         return this;
/* 10992:      */       }
/* 10993:      */       
/* 10994:      */       public final boolean isInitialized()
/* 10995:      */       {
/* 10996:10396 */         return true;
/* 10997:      */       }
/* 10998:      */       
/* 10999:      */       public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry)
/* 11000:      */         throws IOException
/* 11001:      */       {
/* 11002:10403 */         MRServiceProtos.KillTaskAttemptRequestProto parsedMessage = null;
/* 11003:      */         try
/* 11004:      */         {
/* 11005:10405 */           parsedMessage = (MRServiceProtos.KillTaskAttemptRequestProto)MRServiceProtos.KillTaskAttemptRequestProto.PARSER.parsePartialFrom(input, extensionRegistry);
/* 11006:      */         }
/* 11007:      */         catch (InvalidProtocolBufferException e)
/* 11008:      */         {
/* 11009:10407 */           parsedMessage = (MRServiceProtos.KillTaskAttemptRequestProto)e.getUnfinishedMessage();
/* 11010:10408 */           throw e;
/* 11011:      */         }
/* 11012:      */         finally
/* 11013:      */         {
/* 11014:10410 */           if (parsedMessage != null) {
/* 11015:10411 */             mergeFrom(parsedMessage);
/* 11016:      */           }
/* 11017:      */         }
/* 11018:10414 */         return this;
/* 11019:      */       }
/* 11020:      */       
/* 11021:10419 */       private MRProtos.TaskAttemptIdProto taskAttemptId_ = MRProtos.TaskAttemptIdProto.getDefaultInstance();
/* 11022:      */       private SingleFieldBuilder<MRProtos.TaskAttemptIdProto, MRProtos.TaskAttemptIdProto.Builder, MRProtos.TaskAttemptIdProtoOrBuilder> taskAttemptIdBuilder_;
/* 11023:      */       
/* 11024:      */       public boolean hasTaskAttemptId()
/* 11025:      */       {
/* 11026:10426 */         return (this.bitField0_ & 0x1) == 1;
/* 11027:      */       }
/* 11028:      */       
/* 11029:      */       public MRProtos.TaskAttemptIdProto getTaskAttemptId()
/* 11030:      */       {
/* 11031:10432 */         if (this.taskAttemptIdBuilder_ == null) {
/* 11032:10433 */           return this.taskAttemptId_;
/* 11033:      */         }
/* 11034:10435 */         return (MRProtos.TaskAttemptIdProto)this.taskAttemptIdBuilder_.getMessage();
/* 11035:      */       }
/* 11036:      */       
/* 11037:      */       public Builder setTaskAttemptId(MRProtos.TaskAttemptIdProto value)
/* 11038:      */       {
/* 11039:10442 */         if (this.taskAttemptIdBuilder_ == null)
/* 11040:      */         {
/* 11041:10443 */           if (value == null) {
/* 11042:10444 */             throw new NullPointerException();
/* 11043:      */           }
/* 11044:10446 */           this.taskAttemptId_ = value;
/* 11045:10447 */           onChanged();
/* 11046:      */         }
/* 11047:      */         else
/* 11048:      */         {
/* 11049:10449 */           this.taskAttemptIdBuilder_.setMessage(value);
/* 11050:      */         }
/* 11051:10451 */         this.bitField0_ |= 0x1;
/* 11052:10452 */         return this;
/* 11053:      */       }
/* 11054:      */       
/* 11055:      */       public Builder setTaskAttemptId(MRProtos.TaskAttemptIdProto.Builder builderForValue)
/* 11056:      */       {
/* 11057:10459 */         if (this.taskAttemptIdBuilder_ == null)
/* 11058:      */         {
/* 11059:10460 */           this.taskAttemptId_ = builderForValue.build();
/* 11060:10461 */           onChanged();
/* 11061:      */         }
/* 11062:      */         else
/* 11063:      */         {
/* 11064:10463 */           this.taskAttemptIdBuilder_.setMessage(builderForValue.build());
/* 11065:      */         }
/* 11066:10465 */         this.bitField0_ |= 0x1;
/* 11067:10466 */         return this;
/* 11068:      */       }
/* 11069:      */       
/* 11070:      */       public Builder mergeTaskAttemptId(MRProtos.TaskAttemptIdProto value)
/* 11071:      */       {
/* 11072:10472 */         if (this.taskAttemptIdBuilder_ == null)
/* 11073:      */         {
/* 11074:10473 */           if (((this.bitField0_ & 0x1) == 1) && (this.taskAttemptId_ != MRProtos.TaskAttemptIdProto.getDefaultInstance())) {
/* 11075:10475 */             this.taskAttemptId_ = MRProtos.TaskAttemptIdProto.newBuilder(this.taskAttemptId_).mergeFrom(value).buildPartial();
/* 11076:      */           } else {
/* 11077:10478 */             this.taskAttemptId_ = value;
/* 11078:      */           }
/* 11079:10480 */           onChanged();
/* 11080:      */         }
/* 11081:      */         else
/* 11082:      */         {
/* 11083:10482 */           this.taskAttemptIdBuilder_.mergeFrom(value);
/* 11084:      */         }
/* 11085:10484 */         this.bitField0_ |= 0x1;
/* 11086:10485 */         return this;
/* 11087:      */       }
/* 11088:      */       
/* 11089:      */       public Builder clearTaskAttemptId()
/* 11090:      */       {
/* 11091:10491 */         if (this.taskAttemptIdBuilder_ == null)
/* 11092:      */         {
/* 11093:10492 */           this.taskAttemptId_ = MRProtos.TaskAttemptIdProto.getDefaultInstance();
/* 11094:10493 */           onChanged();
/* 11095:      */         }
/* 11096:      */         else
/* 11097:      */         {
/* 11098:10495 */           this.taskAttemptIdBuilder_.clear();
/* 11099:      */         }
/* 11100:10497 */         this.bitField0_ &= 0xFFFFFFFE;
/* 11101:10498 */         return this;
/* 11102:      */       }
/* 11103:      */       
/* 11104:      */       public MRProtos.TaskAttemptIdProto.Builder getTaskAttemptIdBuilder()
/* 11105:      */       {
/* 11106:10504 */         this.bitField0_ |= 0x1;
/* 11107:10505 */         onChanged();
/* 11108:10506 */         return (MRProtos.TaskAttemptIdProto.Builder)getTaskAttemptIdFieldBuilder().getBuilder();
/* 11109:      */       }
/* 11110:      */       
/* 11111:      */       public MRProtos.TaskAttemptIdProtoOrBuilder getTaskAttemptIdOrBuilder()
/* 11112:      */       {
/* 11113:10512 */         if (this.taskAttemptIdBuilder_ != null) {
/* 11114:10513 */           return (MRProtos.TaskAttemptIdProtoOrBuilder)this.taskAttemptIdBuilder_.getMessageOrBuilder();
/* 11115:      */         }
/* 11116:10515 */         return this.taskAttemptId_;
/* 11117:      */       }
/* 11118:      */       
/* 11119:      */       private SingleFieldBuilder<MRProtos.TaskAttemptIdProto, MRProtos.TaskAttemptIdProto.Builder, MRProtos.TaskAttemptIdProtoOrBuilder> getTaskAttemptIdFieldBuilder()
/* 11120:      */       {
/* 11121:10524 */         if (this.taskAttemptIdBuilder_ == null)
/* 11122:      */         {
/* 11123:10525 */           this.taskAttemptIdBuilder_ = new SingleFieldBuilder(this.taskAttemptId_, getParentForChildren(), isClean());
/* 11124:      */           
/* 11125:      */ 
/* 11126:      */ 
/* 11127:      */ 
/* 11128:10530 */           this.taskAttemptId_ = null;
/* 11129:      */         }
/* 11130:10532 */         return this.taskAttemptIdBuilder_;
/* 11131:      */       }
/* 11132:      */     }
/* 11133:      */     
/* 11134:      */     static
/* 11135:      */     {
/* 11136:10539 */       defaultInstance = new KillTaskAttemptRequestProto(true);
/* 11137:10540 */       defaultInstance.initFields();
/* 11138:      */     }
/* 11139:      */   }
/* 11140:      */   
/* 11141:      */   public static abstract interface KillTaskAttemptResponseProtoOrBuilder
/* 11142:      */     extends MessageOrBuilder
/* 11143:      */   {}
/* 11144:      */   
/* 11145:      */   public static final class KillTaskAttemptResponseProto
/* 11146:      */     extends GeneratedMessage
/* 11147:      */     implements MRServiceProtos.KillTaskAttemptResponseProtoOrBuilder
/* 11148:      */   {
/* 11149:      */     private static final KillTaskAttemptResponseProto defaultInstance;
/* 11150:      */     private final UnknownFieldSet unknownFields;
/* 11151:      */     
/* 11152:      */     private KillTaskAttemptResponseProto(GeneratedMessage.Builder<?> builder)
/* 11153:      */     {
/* 11154:10557 */       super();
/* 11155:10558 */       this.unknownFields = builder.getUnknownFields();
/* 11156:      */     }
/* 11157:      */     
/* 11158:      */     private KillTaskAttemptResponseProto(boolean noInit)
/* 11159:      */     {
/* 11160:10560 */       this.unknownFields = UnknownFieldSet.getDefaultInstance();
/* 11161:      */     }
/* 11162:      */     
/* 11163:      */     public static KillTaskAttemptResponseProto getDefaultInstance()
/* 11164:      */     {
/* 11165:10564 */       return defaultInstance;
/* 11166:      */     }
/* 11167:      */     
/* 11168:      */     public KillTaskAttemptResponseProto getDefaultInstanceForType()
/* 11169:      */     {
/* 11170:10568 */       return defaultInstance;
/* 11171:      */     }
/* 11172:      */     
/* 11173:      */     public final UnknownFieldSet getUnknownFields()
/* 11174:      */     {
/* 11175:10575 */       return this.unknownFields;
/* 11176:      */     }
/* 11177:      */     
/* 11178:      */     private KillTaskAttemptResponseProto(CodedInputStream input, ExtensionRegistryLite extensionRegistry)
/* 11179:      */       throws InvalidProtocolBufferException
/* 11180:      */     {
/* 11181:10581 */       initFields();
/* 11182:10582 */       UnknownFieldSet.Builder unknownFields = UnknownFieldSet.newBuilder();
/* 11183:      */       try
/* 11184:      */       {
/* 11185:10585 */         boolean done = false;
/* 11186:10586 */         while (!done)
/* 11187:      */         {
/* 11188:10587 */           int tag = input.readTag();
/* 11189:10588 */           switch (tag)
/* 11190:      */           {
/* 11191:      */           case 0: 
/* 11192:10590 */             done = true;
/* 11193:10591 */             break;
/* 11194:      */           default: 
/* 11195:10593 */             if (!parseUnknownField(input, unknownFields, extensionRegistry, tag)) {
/* 11196:10595 */               done = true;
/* 11197:      */             }
/* 11198:      */             break;
/* 11199:      */           }
/* 11200:      */         }
/* 11201:      */       }
/* 11202:      */       catch (InvalidProtocolBufferException e)
/* 11203:      */       {
/* 11204:10602 */         throw e.setUnfinishedMessage(this);
/* 11205:      */       }
/* 11206:      */       catch (IOException e)
/* 11207:      */       {
/* 11208:10604 */         throw new InvalidProtocolBufferException(e.getMessage()).setUnfinishedMessage(this);
/* 11209:      */       }
/* 11210:      */       finally
/* 11211:      */       {
/* 11212:10607 */         this.unknownFields = unknownFields.build();
/* 11213:10608 */         makeExtensionsImmutable();
/* 11214:      */       }
/* 11215:      */     }
/* 11216:      */     
/* 11217:      */     public static final Descriptors.Descriptor getDescriptor()
/* 11218:      */     {
/* 11219:10613 */       return MRServiceProtos.internal_static_hadoop_mapreduce_KillTaskAttemptResponseProto_descriptor;
/* 11220:      */     }
/* 11221:      */     
/* 11222:      */     protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable()
/* 11223:      */     {
/* 11224:10618 */       return MRServiceProtos.internal_static_hadoop_mapreduce_KillTaskAttemptResponseProto_fieldAccessorTable.ensureFieldAccessorsInitialized(KillTaskAttemptResponseProto.class, Builder.class);
/* 11225:      */     }
/* 11226:      */     
/* 11227:10623 */     public static Parser<KillTaskAttemptResponseProto> PARSER = new AbstractParser()
/* 11228:      */     {
/* 11229:      */       public MRServiceProtos.KillTaskAttemptResponseProto parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry)
/* 11230:      */         throws InvalidProtocolBufferException
/* 11231:      */       {
/* 11232:10629 */         return new MRServiceProtos.KillTaskAttemptResponseProto(input, extensionRegistry, null);
/* 11233:      */       }
/* 11234:      */     };
/* 11235:      */     
/* 11236:      */     public Parser<KillTaskAttemptResponseProto> getParserForType()
/* 11237:      */     {
/* 11238:10635 */       return PARSER;
/* 11239:      */     }
/* 11240:      */     
/* 11241:10640 */     private byte memoizedIsInitialized = -1;
/* 11242:      */     
/* 11243:      */     private void initFields() {}
/* 11244:      */     
/* 11245:      */     public final boolean isInitialized()
/* 11246:      */     {
/* 11247:10642 */       byte isInitialized = this.memoizedIsInitialized;
/* 11248:10643 */       if (isInitialized != -1) {
/* 11249:10643 */         return isInitialized == 1;
/* 11250:      */       }
/* 11251:10645 */       this.memoizedIsInitialized = 1;
/* 11252:10646 */       return true;
/* 11253:      */     }
/* 11254:      */     
/* 11255:      */     public void writeTo(CodedOutputStream output)
/* 11256:      */       throws IOException
/* 11257:      */     {
/* 11258:10651 */       getSerializedSize();
/* 11259:10652 */       getUnknownFields().writeTo(output);
/* 11260:      */     }
/* 11261:      */     
/* 11262:10655 */     private int memoizedSerializedSize = -1;
/* 11263:      */     private static final long serialVersionUID = 0L;
/* 11264:      */     
/* 11265:      */     public int getSerializedSize()
/* 11266:      */     {
/* 11267:10657 */       int size = this.memoizedSerializedSize;
/* 11268:10658 */       if (size != -1) {
/* 11269:10658 */         return size;
/* 11270:      */       }
/* 11271:10660 */       size = 0;
/* 11272:10661 */       size += getUnknownFields().getSerializedSize();
/* 11273:10662 */       this.memoizedSerializedSize = size;
/* 11274:10663 */       return size;
/* 11275:      */     }
/* 11276:      */     
/* 11277:      */     protected Object writeReplace()
/* 11278:      */       throws ObjectStreamException
/* 11279:      */     {
/* 11280:10670 */       return super.writeReplace();
/* 11281:      */     }
/* 11282:      */     
/* 11283:      */     public boolean equals(Object obj)
/* 11284:      */     {
/* 11285:10675 */       if (obj == this) {
/* 11286:10676 */         return true;
/* 11287:      */       }
/* 11288:10678 */       if (!(obj instanceof KillTaskAttemptResponseProto)) {
/* 11289:10679 */         return super.equals(obj);
/* 11290:      */       }
/* 11291:10681 */       KillTaskAttemptResponseProto other = (KillTaskAttemptResponseProto)obj;
/* 11292:      */       
/* 11293:10683 */       boolean result = true;
/* 11294:10684 */       result = (result) && (getUnknownFields().equals(other.getUnknownFields()));
/* 11295:      */       
/* 11296:10686 */       return result;
/* 11297:      */     }
/* 11298:      */     
/* 11299:10689 */     private int memoizedHashCode = 0;
/* 11300:      */     
/* 11301:      */     public int hashCode()
/* 11302:      */     {
/* 11303:10692 */       if (this.memoizedHashCode != 0) {
/* 11304:10693 */         return this.memoizedHashCode;
/* 11305:      */       }
/* 11306:10695 */       int hash = 41;
/* 11307:10696 */       hash = 19 * hash + getDescriptorForType().hashCode();
/* 11308:10697 */       hash = 29 * hash + getUnknownFields().hashCode();
/* 11309:10698 */       this.memoizedHashCode = hash;
/* 11310:10699 */       return hash;
/* 11311:      */     }
/* 11312:      */     
/* 11313:      */     public static KillTaskAttemptResponseProto parseFrom(ByteString data)
/* 11314:      */       throws InvalidProtocolBufferException
/* 11315:      */     {
/* 11316:10705 */       return (KillTaskAttemptResponseProto)PARSER.parseFrom(data);
/* 11317:      */     }
/* 11318:      */     
/* 11319:      */     public static KillTaskAttemptResponseProto parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry)
/* 11320:      */       throws InvalidProtocolBufferException
/* 11321:      */     {
/* 11322:10711 */       return (KillTaskAttemptResponseProto)PARSER.parseFrom(data, extensionRegistry);
/* 11323:      */     }
/* 11324:      */     
/* 11325:      */     public static KillTaskAttemptResponseProto parseFrom(byte[] data)
/* 11326:      */       throws InvalidProtocolBufferException
/* 11327:      */     {
/* 11328:10715 */       return (KillTaskAttemptResponseProto)PARSER.parseFrom(data);
/* 11329:      */     }
/* 11330:      */     
/* 11331:      */     public static KillTaskAttemptResponseProto parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry)
/* 11332:      */       throws InvalidProtocolBufferException
/* 11333:      */     {
/* 11334:10721 */       return (KillTaskAttemptResponseProto)PARSER.parseFrom(data, extensionRegistry);
/* 11335:      */     }
/* 11336:      */     
/* 11337:      */     public static KillTaskAttemptResponseProto parseFrom(InputStream input)
/* 11338:      */       throws IOException
/* 11339:      */     {
/* 11340:10725 */       return (KillTaskAttemptResponseProto)PARSER.parseFrom(input);
/* 11341:      */     }
/* 11342:      */     
/* 11343:      */     public static KillTaskAttemptResponseProto parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry)
/* 11344:      */       throws IOException
/* 11345:      */     {
/* 11346:10731 */       return (KillTaskAttemptResponseProto)PARSER.parseFrom(input, extensionRegistry);
/* 11347:      */     }
/* 11348:      */     
/* 11349:      */     public static KillTaskAttemptResponseProto parseDelimitedFrom(InputStream input)
/* 11350:      */       throws IOException
/* 11351:      */     {
/* 11352:10735 */       return (KillTaskAttemptResponseProto)PARSER.parseDelimitedFrom(input);
/* 11353:      */     }
/* 11354:      */     
/* 11355:      */     public static KillTaskAttemptResponseProto parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry)
/* 11356:      */       throws IOException
/* 11357:      */     {
/* 11358:10741 */       return (KillTaskAttemptResponseProto)PARSER.parseDelimitedFrom(input, extensionRegistry);
/* 11359:      */     }
/* 11360:      */     
/* 11361:      */     public static KillTaskAttemptResponseProto parseFrom(CodedInputStream input)
/* 11362:      */       throws IOException
/* 11363:      */     {
/* 11364:10746 */       return (KillTaskAttemptResponseProto)PARSER.parseFrom(input);
/* 11365:      */     }
/* 11366:      */     
/* 11367:      */     public static KillTaskAttemptResponseProto parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry)
/* 11368:      */       throws IOException
/* 11369:      */     {
/* 11370:10752 */       return (KillTaskAttemptResponseProto)PARSER.parseFrom(input, extensionRegistry);
/* 11371:      */     }
/* 11372:      */     
/* 11373:      */     public static Builder newBuilder()
/* 11374:      */     {
/* 11375:10755 */       return Builder.access$17200();
/* 11376:      */     }
/* 11377:      */     
/* 11378:      */     public Builder newBuilderForType()
/* 11379:      */     {
/* 11380:10756 */       return newBuilder();
/* 11381:      */     }
/* 11382:      */     
/* 11383:      */     public static Builder newBuilder(KillTaskAttemptResponseProto prototype)
/* 11384:      */     {
/* 11385:10758 */       return newBuilder().mergeFrom(prototype);
/* 11386:      */     }
/* 11387:      */     
/* 11388:      */     public Builder toBuilder()
/* 11389:      */     {
/* 11390:10760 */       return newBuilder(this);
/* 11391:      */     }
/* 11392:      */     
/* 11393:      */     protected Builder newBuilderForType(GeneratedMessage.BuilderParent parent)
/* 11394:      */     {
/* 11395:10765 */       Builder builder = new Builder(parent, null);
/* 11396:10766 */       return builder;
/* 11397:      */     }
/* 11398:      */     
/* 11399:      */     public static final class Builder
/* 11400:      */       extends GeneratedMessage.Builder<Builder>
/* 11401:      */       implements MRServiceProtos.KillTaskAttemptResponseProtoOrBuilder
/* 11402:      */     {
/* 11403:      */       public static final Descriptors.Descriptor getDescriptor()
/* 11404:      */       {
/* 11405:10776 */         return MRServiceProtos.internal_static_hadoop_mapreduce_KillTaskAttemptResponseProto_descriptor;
/* 11406:      */       }
/* 11407:      */       
/* 11408:      */       protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable()
/* 11409:      */       {
/* 11410:10781 */         return MRServiceProtos.internal_static_hadoop_mapreduce_KillTaskAttemptResponseProto_fieldAccessorTable.ensureFieldAccessorsInitialized(MRServiceProtos.KillTaskAttemptResponseProto.class, Builder.class);
/* 11411:      */       }
/* 11412:      */       
/* 11413:      */       private Builder()
/* 11414:      */       {
/* 11415:10788 */         maybeForceBuilderInitialization();
/* 11416:      */       }
/* 11417:      */       
/* 11418:      */       private Builder(GeneratedMessage.BuilderParent parent)
/* 11419:      */       {
/* 11420:10793 */         super();
/* 11421:10794 */         maybeForceBuilderInitialization();
/* 11422:      */       }
/* 11423:      */       
/* 11424:      */       private void maybeForceBuilderInitialization()
/* 11425:      */       {
/* 11426:10797 */         if (MRServiceProtos.KillTaskAttemptResponseProto.alwaysUseFieldBuilders) {}
/* 11427:      */       }
/* 11428:      */       
/* 11429:      */       private static Builder create()
/* 11430:      */       {
/* 11431:10801 */         return new Builder();
/* 11432:      */       }
/* 11433:      */       
/* 11434:      */       public Builder clear()
/* 11435:      */       {
/* 11436:10805 */         super.clear();
/* 11437:10806 */         return this;
/* 11438:      */       }
/* 11439:      */       
/* 11440:      */       public Builder clone()
/* 11441:      */       {
/* 11442:10810 */         return create().mergeFrom(buildPartial());
/* 11443:      */       }
/* 11444:      */       
/* 11445:      */       public Descriptors.Descriptor getDescriptorForType()
/* 11446:      */       {
/* 11447:10815 */         return MRServiceProtos.internal_static_hadoop_mapreduce_KillTaskAttemptResponseProto_descriptor;
/* 11448:      */       }
/* 11449:      */       
/* 11450:      */       public MRServiceProtos.KillTaskAttemptResponseProto getDefaultInstanceForType()
/* 11451:      */       {
/* 11452:10819 */         return MRServiceProtos.KillTaskAttemptResponseProto.getDefaultInstance();
/* 11453:      */       }
/* 11454:      */       
/* 11455:      */       public MRServiceProtos.KillTaskAttemptResponseProto build()
/* 11456:      */       {
/* 11457:10823 */         MRServiceProtos.KillTaskAttemptResponseProto result = buildPartial();
/* 11458:10824 */         if (!result.isInitialized()) {
/* 11459:10825 */           throw newUninitializedMessageException(result);
/* 11460:      */         }
/* 11461:10827 */         return result;
/* 11462:      */       }
/* 11463:      */       
/* 11464:      */       public MRServiceProtos.KillTaskAttemptResponseProto buildPartial()
/* 11465:      */       {
/* 11466:10831 */         MRServiceProtos.KillTaskAttemptResponseProto result = new MRServiceProtos.KillTaskAttemptResponseProto(this, null);
/* 11467:10832 */         onBuilt();
/* 11468:10833 */         return result;
/* 11469:      */       }
/* 11470:      */       
/* 11471:      */       public Builder mergeFrom(Message other)
/* 11472:      */       {
/* 11473:10837 */         if ((other instanceof MRServiceProtos.KillTaskAttemptResponseProto)) {
/* 11474:10838 */           return mergeFrom((MRServiceProtos.KillTaskAttemptResponseProto)other);
/* 11475:      */         }
/* 11476:10840 */         super.mergeFrom(other);
/* 11477:10841 */         return this;
/* 11478:      */       }
/* 11479:      */       
/* 11480:      */       public Builder mergeFrom(MRServiceProtos.KillTaskAttemptResponseProto other)
/* 11481:      */       {
/* 11482:10846 */         if (other == MRServiceProtos.KillTaskAttemptResponseProto.getDefaultInstance()) {
/* 11483:10846 */           return this;
/* 11484:      */         }
/* 11485:10847 */         mergeUnknownFields(other.getUnknownFields());
/* 11486:10848 */         return this;
/* 11487:      */       }
/* 11488:      */       
/* 11489:      */       public final boolean isInitialized()
/* 11490:      */       {
/* 11491:10852 */         return true;
/* 11492:      */       }
/* 11493:      */       
/* 11494:      */       public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry)
/* 11495:      */         throws IOException
/* 11496:      */       {
/* 11497:10859 */         MRServiceProtos.KillTaskAttemptResponseProto parsedMessage = null;
/* 11498:      */         try
/* 11499:      */         {
/* 11500:10861 */           parsedMessage = (MRServiceProtos.KillTaskAttemptResponseProto)MRServiceProtos.KillTaskAttemptResponseProto.PARSER.parsePartialFrom(input, extensionRegistry);
/* 11501:      */         }
/* 11502:      */         catch (InvalidProtocolBufferException e)
/* 11503:      */         {
/* 11504:10863 */           parsedMessage = (MRServiceProtos.KillTaskAttemptResponseProto)e.getUnfinishedMessage();
/* 11505:10864 */           throw e;
/* 11506:      */         }
/* 11507:      */         finally
/* 11508:      */         {
/* 11509:10866 */           if (parsedMessage != null) {
/* 11510:10867 */             mergeFrom(parsedMessage);
/* 11511:      */           }
/* 11512:      */         }
/* 11513:10870 */         return this;
/* 11514:      */       }
/* 11515:      */     }
/* 11516:      */     
/* 11517:      */     static
/* 11518:      */     {
/* 11519:10877 */       defaultInstance = new KillTaskAttemptResponseProto(true);
/* 11520:10878 */       defaultInstance.initFields();
/* 11521:      */     }
/* 11522:      */   }
/* 11523:      */   
/* 11524:      */   public static abstract interface FailTaskAttemptRequestProtoOrBuilder
/* 11525:      */     extends MessageOrBuilder
/* 11526:      */   {
/* 11527:      */     public abstract boolean hasTaskAttemptId();
/* 11528:      */     
/* 11529:      */     public abstract MRProtos.TaskAttemptIdProto getTaskAttemptId();
/* 11530:      */     
/* 11531:      */     public abstract MRProtos.TaskAttemptIdProtoOrBuilder getTaskAttemptIdOrBuilder();
/* 11532:      */   }
/* 11533:      */   
/* 11534:      */   public static final class FailTaskAttemptRequestProto
/* 11535:      */     extends GeneratedMessage
/* 11536:      */     implements MRServiceProtos.FailTaskAttemptRequestProtoOrBuilder
/* 11537:      */   {
/* 11538:      */     private static final FailTaskAttemptRequestProto defaultInstance;
/* 11539:      */     private final UnknownFieldSet unknownFields;
/* 11540:      */     
/* 11541:      */     private FailTaskAttemptRequestProto(GeneratedMessage.Builder<?> builder)
/* 11542:      */     {
/* 11543:10909 */       super();
/* 11544:10910 */       this.unknownFields = builder.getUnknownFields();
/* 11545:      */     }
/* 11546:      */     
/* 11547:      */     private FailTaskAttemptRequestProto(boolean noInit)
/* 11548:      */     {
/* 11549:10912 */       this.unknownFields = UnknownFieldSet.getDefaultInstance();
/* 11550:      */     }
/* 11551:      */     
/* 11552:      */     public static FailTaskAttemptRequestProto getDefaultInstance()
/* 11553:      */     {
/* 11554:10916 */       return defaultInstance;
/* 11555:      */     }
/* 11556:      */     
/* 11557:      */     public FailTaskAttemptRequestProto getDefaultInstanceForType()
/* 11558:      */     {
/* 11559:10920 */       return defaultInstance;
/* 11560:      */     }
/* 11561:      */     
/* 11562:      */     public final UnknownFieldSet getUnknownFields()
/* 11563:      */     {
/* 11564:10927 */       return this.unknownFields;
/* 11565:      */     }
/* 11566:      */     
/* 11567:      */     private FailTaskAttemptRequestProto(CodedInputStream input, ExtensionRegistryLite extensionRegistry)
/* 11568:      */       throws InvalidProtocolBufferException
/* 11569:      */     {
/* 11570:10933 */       initFields();
/* 11571:10934 */       int mutable_bitField0_ = 0;
/* 11572:10935 */       UnknownFieldSet.Builder unknownFields = UnknownFieldSet.newBuilder();
/* 11573:      */       try
/* 11574:      */       {
/* 11575:10938 */         boolean done = false;
/* 11576:10939 */         while (!done)
/* 11577:      */         {
/* 11578:10940 */           int tag = input.readTag();
/* 11579:10941 */           switch (tag)
/* 11580:      */           {
/* 11581:      */           case 0: 
/* 11582:10943 */             done = true;
/* 11583:10944 */             break;
/* 11584:      */           default: 
/* 11585:10946 */             if (!parseUnknownField(input, unknownFields, extensionRegistry, tag)) {
/* 11586:10948 */               done = true;
/* 11587:      */             }
/* 11588:      */             break;
/* 11589:      */           case 10: 
/* 11590:10953 */             MRProtos.TaskAttemptIdProto.Builder subBuilder = null;
/* 11591:10954 */             if ((this.bitField0_ & 0x1) == 1) {
/* 11592:10955 */               subBuilder = this.taskAttemptId_.toBuilder();
/* 11593:      */             }
/* 11594:10957 */             this.taskAttemptId_ = ((MRProtos.TaskAttemptIdProto)input.readMessage(MRProtos.TaskAttemptIdProto.PARSER, extensionRegistry));
/* 11595:10958 */             if (subBuilder != null)
/* 11596:      */             {
/* 11597:10959 */               subBuilder.mergeFrom(this.taskAttemptId_);
/* 11598:10960 */               this.taskAttemptId_ = subBuilder.buildPartial();
/* 11599:      */             }
/* 11600:10962 */             this.bitField0_ |= 0x1;
/* 11601:      */           }
/* 11602:      */         }
/* 11603:      */       }
/* 11604:      */       catch (InvalidProtocolBufferException e)
/* 11605:      */       {
/* 11606:10968 */         throw e.setUnfinishedMessage(this);
/* 11607:      */       }
/* 11608:      */       catch (IOException e)
/* 11609:      */       {
/* 11610:10970 */         throw new InvalidProtocolBufferException(e.getMessage()).setUnfinishedMessage(this);
/* 11611:      */       }
/* 11612:      */       finally
/* 11613:      */       {
/* 11614:10973 */         this.unknownFields = unknownFields.build();
/* 11615:10974 */         makeExtensionsImmutable();
/* 11616:      */       }
/* 11617:      */     }
/* 11618:      */     
/* 11619:      */     public static final Descriptors.Descriptor getDescriptor()
/* 11620:      */     {
/* 11621:10979 */       return MRServiceProtos.internal_static_hadoop_mapreduce_FailTaskAttemptRequestProto_descriptor;
/* 11622:      */     }
/* 11623:      */     
/* 11624:      */     protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable()
/* 11625:      */     {
/* 11626:10984 */       return MRServiceProtos.internal_static_hadoop_mapreduce_FailTaskAttemptRequestProto_fieldAccessorTable.ensureFieldAccessorsInitialized(FailTaskAttemptRequestProto.class, Builder.class);
/* 11627:      */     }
/* 11628:      */     
/* 11629:10989 */     public static Parser<FailTaskAttemptRequestProto> PARSER = new AbstractParser()
/* 11630:      */     {
/* 11631:      */       public MRServiceProtos.FailTaskAttemptRequestProto parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry)
/* 11632:      */         throws InvalidProtocolBufferException
/* 11633:      */       {
/* 11634:10995 */         return new MRServiceProtos.FailTaskAttemptRequestProto(input, extensionRegistry, null);
/* 11635:      */       }
/* 11636:      */     };
/* 11637:      */     private int bitField0_;
/* 11638:      */     public static final int TASK_ATTEMPT_ID_FIELD_NUMBER = 1;
/* 11639:      */     private MRProtos.TaskAttemptIdProto taskAttemptId_;
/* 11640:      */     
/* 11641:      */     public Parser<FailTaskAttemptRequestProto> getParserForType()
/* 11642:      */     {
/* 11643:11001 */       return PARSER;
/* 11644:      */     }
/* 11645:      */     
/* 11646:      */     public boolean hasTaskAttemptId()
/* 11647:      */     {
/* 11648:11012 */       return (this.bitField0_ & 0x1) == 1;
/* 11649:      */     }
/* 11650:      */     
/* 11651:      */     public MRProtos.TaskAttemptIdProto getTaskAttemptId()
/* 11652:      */     {
/* 11653:11018 */       return this.taskAttemptId_;
/* 11654:      */     }
/* 11655:      */     
/* 11656:      */     public MRProtos.TaskAttemptIdProtoOrBuilder getTaskAttemptIdOrBuilder()
/* 11657:      */     {
/* 11658:11024 */       return this.taskAttemptId_;
/* 11659:      */     }
/* 11660:      */     
/* 11661:      */     private void initFields()
/* 11662:      */     {
/* 11663:11028 */       this.taskAttemptId_ = MRProtos.TaskAttemptIdProto.getDefaultInstance();
/* 11664:      */     }
/* 11665:      */     
/* 11666:11030 */     private byte memoizedIsInitialized = -1;
/* 11667:      */     
/* 11668:      */     public final boolean isInitialized()
/* 11669:      */     {
/* 11670:11032 */       byte isInitialized = this.memoizedIsInitialized;
/* 11671:11033 */       if (isInitialized != -1) {
/* 11672:11033 */         return isInitialized == 1;
/* 11673:      */       }
/* 11674:11035 */       this.memoizedIsInitialized = 1;
/* 11675:11036 */       return true;
/* 11676:      */     }
/* 11677:      */     
/* 11678:      */     public void writeTo(CodedOutputStream output)
/* 11679:      */       throws IOException
/* 11680:      */     {
/* 11681:11041 */       getSerializedSize();
/* 11682:11042 */       if ((this.bitField0_ & 0x1) == 1) {
/* 11683:11043 */         output.writeMessage(1, this.taskAttemptId_);
/* 11684:      */       }
/* 11685:11045 */       getUnknownFields().writeTo(output);
/* 11686:      */     }
/* 11687:      */     
/* 11688:11048 */     private int memoizedSerializedSize = -1;
/* 11689:      */     private static final long serialVersionUID = 0L;
/* 11690:      */     
/* 11691:      */     public int getSerializedSize()
/* 11692:      */     {
/* 11693:11050 */       int size = this.memoizedSerializedSize;
/* 11694:11051 */       if (size != -1) {
/* 11695:11051 */         return size;
/* 11696:      */       }
/* 11697:11053 */       size = 0;
/* 11698:11054 */       if ((this.bitField0_ & 0x1) == 1) {
/* 11699:11055 */         size += CodedOutputStream.computeMessageSize(1, this.taskAttemptId_);
/* 11700:      */       }
/* 11701:11058 */       size += getUnknownFields().getSerializedSize();
/* 11702:11059 */       this.memoizedSerializedSize = size;
/* 11703:11060 */       return size;
/* 11704:      */     }
/* 11705:      */     
/* 11706:      */     protected Object writeReplace()
/* 11707:      */       throws ObjectStreamException
/* 11708:      */     {
/* 11709:11067 */       return super.writeReplace();
/* 11710:      */     }
/* 11711:      */     
/* 11712:      */     public boolean equals(Object obj)
/* 11713:      */     {
/* 11714:11072 */       if (obj == this) {
/* 11715:11073 */         return true;
/* 11716:      */       }
/* 11717:11075 */       if (!(obj instanceof FailTaskAttemptRequestProto)) {
/* 11718:11076 */         return super.equals(obj);
/* 11719:      */       }
/* 11720:11078 */       FailTaskAttemptRequestProto other = (FailTaskAttemptRequestProto)obj;
/* 11721:      */       
/* 11722:11080 */       boolean result = true;
/* 11723:11081 */       result = (result) && (hasTaskAttemptId() == other.hasTaskAttemptId());
/* 11724:11082 */       if (hasTaskAttemptId()) {
/* 11725:11083 */         result = (result) && (getTaskAttemptId().equals(other.getTaskAttemptId()));
/* 11726:      */       }
/* 11727:11086 */       result = (result) && (getUnknownFields().equals(other.getUnknownFields()));
/* 11728:      */       
/* 11729:11088 */       return result;
/* 11730:      */     }
/* 11731:      */     
/* 11732:11091 */     private int memoizedHashCode = 0;
/* 11733:      */     
/* 11734:      */     public int hashCode()
/* 11735:      */     {
/* 11736:11094 */       if (this.memoizedHashCode != 0) {
/* 11737:11095 */         return this.memoizedHashCode;
/* 11738:      */       }
/* 11739:11097 */       int hash = 41;
/* 11740:11098 */       hash = 19 * hash + getDescriptorForType().hashCode();
/* 11741:11099 */       if (hasTaskAttemptId())
/* 11742:      */       {
/* 11743:11100 */         hash = 37 * hash + 1;
/* 11744:11101 */         hash = 53 * hash + getTaskAttemptId().hashCode();
/* 11745:      */       }
/* 11746:11103 */       hash = 29 * hash + getUnknownFields().hashCode();
/* 11747:11104 */       this.memoizedHashCode = hash;
/* 11748:11105 */       return hash;
/* 11749:      */     }
/* 11750:      */     
/* 11751:      */     public static FailTaskAttemptRequestProto parseFrom(ByteString data)
/* 11752:      */       throws InvalidProtocolBufferException
/* 11753:      */     {
/* 11754:11111 */       return (FailTaskAttemptRequestProto)PARSER.parseFrom(data);
/* 11755:      */     }
/* 11756:      */     
/* 11757:      */     public static FailTaskAttemptRequestProto parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry)
/* 11758:      */       throws InvalidProtocolBufferException
/* 11759:      */     {
/* 11760:11117 */       return (FailTaskAttemptRequestProto)PARSER.parseFrom(data, extensionRegistry);
/* 11761:      */     }
/* 11762:      */     
/* 11763:      */     public static FailTaskAttemptRequestProto parseFrom(byte[] data)
/* 11764:      */       throws InvalidProtocolBufferException
/* 11765:      */     {
/* 11766:11121 */       return (FailTaskAttemptRequestProto)PARSER.parseFrom(data);
/* 11767:      */     }
/* 11768:      */     
/* 11769:      */     public static FailTaskAttemptRequestProto parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry)
/* 11770:      */       throws InvalidProtocolBufferException
/* 11771:      */     {
/* 11772:11127 */       return (FailTaskAttemptRequestProto)PARSER.parseFrom(data, extensionRegistry);
/* 11773:      */     }
/* 11774:      */     
/* 11775:      */     public static FailTaskAttemptRequestProto parseFrom(InputStream input)
/* 11776:      */       throws IOException
/* 11777:      */     {
/* 11778:11131 */       return (FailTaskAttemptRequestProto)PARSER.parseFrom(input);
/* 11779:      */     }
/* 11780:      */     
/* 11781:      */     public static FailTaskAttemptRequestProto parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry)
/* 11782:      */       throws IOException
/* 11783:      */     {
/* 11784:11137 */       return (FailTaskAttemptRequestProto)PARSER.parseFrom(input, extensionRegistry);
/* 11785:      */     }
/* 11786:      */     
/* 11787:      */     public static FailTaskAttemptRequestProto parseDelimitedFrom(InputStream input)
/* 11788:      */       throws IOException
/* 11789:      */     {
/* 11790:11141 */       return (FailTaskAttemptRequestProto)PARSER.parseDelimitedFrom(input);
/* 11791:      */     }
/* 11792:      */     
/* 11793:      */     public static FailTaskAttemptRequestProto parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry)
/* 11794:      */       throws IOException
/* 11795:      */     {
/* 11796:11147 */       return (FailTaskAttemptRequestProto)PARSER.parseDelimitedFrom(input, extensionRegistry);
/* 11797:      */     }
/* 11798:      */     
/* 11799:      */     public static FailTaskAttemptRequestProto parseFrom(CodedInputStream input)
/* 11800:      */       throws IOException
/* 11801:      */     {
/* 11802:11152 */       return (FailTaskAttemptRequestProto)PARSER.parseFrom(input);
/* 11803:      */     }
/* 11804:      */     
/* 11805:      */     public static FailTaskAttemptRequestProto parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry)
/* 11806:      */       throws IOException
/* 11807:      */     {
/* 11808:11158 */       return (FailTaskAttemptRequestProto)PARSER.parseFrom(input, extensionRegistry);
/* 11809:      */     }
/* 11810:      */     
/* 11811:      */     public static Builder newBuilder()
/* 11812:      */     {
/* 11813:11161 */       return Builder.access$17900();
/* 11814:      */     }
/* 11815:      */     
/* 11816:      */     public Builder newBuilderForType()
/* 11817:      */     {
/* 11818:11162 */       return newBuilder();
/* 11819:      */     }
/* 11820:      */     
/* 11821:      */     public static Builder newBuilder(FailTaskAttemptRequestProto prototype)
/* 11822:      */     {
/* 11823:11164 */       return newBuilder().mergeFrom(prototype);
/* 11824:      */     }
/* 11825:      */     
/* 11826:      */     public Builder toBuilder()
/* 11827:      */     {
/* 11828:11166 */       return newBuilder(this);
/* 11829:      */     }
/* 11830:      */     
/* 11831:      */     protected Builder newBuilderForType(GeneratedMessage.BuilderParent parent)
/* 11832:      */     {
/* 11833:11171 */       Builder builder = new Builder(parent, null);
/* 11834:11172 */       return builder;
/* 11835:      */     }
/* 11836:      */     
/* 11837:      */     public static final class Builder
/* 11838:      */       extends GeneratedMessage.Builder<Builder>
/* 11839:      */       implements MRServiceProtos.FailTaskAttemptRequestProtoOrBuilder
/* 11840:      */     {
/* 11841:      */       private int bitField0_;
/* 11842:      */       
/* 11843:      */       public static final Descriptors.Descriptor getDescriptor()
/* 11844:      */       {
/* 11845:11182 */         return MRServiceProtos.internal_static_hadoop_mapreduce_FailTaskAttemptRequestProto_descriptor;
/* 11846:      */       }
/* 11847:      */       
/* 11848:      */       protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable()
/* 11849:      */       {
/* 11850:11187 */         return MRServiceProtos.internal_static_hadoop_mapreduce_FailTaskAttemptRequestProto_fieldAccessorTable.ensureFieldAccessorsInitialized(MRServiceProtos.FailTaskAttemptRequestProto.class, Builder.class);
/* 11851:      */       }
/* 11852:      */       
/* 11853:      */       private Builder()
/* 11854:      */       {
/* 11855:11194 */         maybeForceBuilderInitialization();
/* 11856:      */       }
/* 11857:      */       
/* 11858:      */       private Builder(GeneratedMessage.BuilderParent parent)
/* 11859:      */       {
/* 11860:11199 */         super();
/* 11861:11200 */         maybeForceBuilderInitialization();
/* 11862:      */       }
/* 11863:      */       
/* 11864:      */       private void maybeForceBuilderInitialization()
/* 11865:      */       {
/* 11866:11203 */         if (MRServiceProtos.FailTaskAttemptRequestProto.alwaysUseFieldBuilders) {
/* 11867:11204 */           getTaskAttemptIdFieldBuilder();
/* 11868:      */         }
/* 11869:      */       }
/* 11870:      */       
/* 11871:      */       private static Builder create()
/* 11872:      */       {
/* 11873:11208 */         return new Builder();
/* 11874:      */       }
/* 11875:      */       
/* 11876:      */       public Builder clear()
/* 11877:      */       {
/* 11878:11212 */         super.clear();
/* 11879:11213 */         if (this.taskAttemptIdBuilder_ == null) {
/* 11880:11214 */           this.taskAttemptId_ = MRProtos.TaskAttemptIdProto.getDefaultInstance();
/* 11881:      */         } else {
/* 11882:11216 */           this.taskAttemptIdBuilder_.clear();
/* 11883:      */         }
/* 11884:11218 */         this.bitField0_ &= 0xFFFFFFFE;
/* 11885:11219 */         return this;
/* 11886:      */       }
/* 11887:      */       
/* 11888:      */       public Builder clone()
/* 11889:      */       {
/* 11890:11223 */         return create().mergeFrom(buildPartial());
/* 11891:      */       }
/* 11892:      */       
/* 11893:      */       public Descriptors.Descriptor getDescriptorForType()
/* 11894:      */       {
/* 11895:11228 */         return MRServiceProtos.internal_static_hadoop_mapreduce_FailTaskAttemptRequestProto_descriptor;
/* 11896:      */       }
/* 11897:      */       
/* 11898:      */       public MRServiceProtos.FailTaskAttemptRequestProto getDefaultInstanceForType()
/* 11899:      */       {
/* 11900:11232 */         return MRServiceProtos.FailTaskAttemptRequestProto.getDefaultInstance();
/* 11901:      */       }
/* 11902:      */       
/* 11903:      */       public MRServiceProtos.FailTaskAttemptRequestProto build()
/* 11904:      */       {
/* 11905:11236 */         MRServiceProtos.FailTaskAttemptRequestProto result = buildPartial();
/* 11906:11237 */         if (!result.isInitialized()) {
/* 11907:11238 */           throw newUninitializedMessageException(result);
/* 11908:      */         }
/* 11909:11240 */         return result;
/* 11910:      */       }
/* 11911:      */       
/* 11912:      */       public MRServiceProtos.FailTaskAttemptRequestProto buildPartial()
/* 11913:      */       {
/* 11914:11244 */         MRServiceProtos.FailTaskAttemptRequestProto result = new MRServiceProtos.FailTaskAttemptRequestProto(this, null);
/* 11915:11245 */         int from_bitField0_ = this.bitField0_;
/* 11916:11246 */         int to_bitField0_ = 0;
/* 11917:11247 */         if ((from_bitField0_ & 0x1) == 1) {
/* 11918:11248 */           to_bitField0_ |= 0x1;
/* 11919:      */         }
/* 11920:11250 */         if (this.taskAttemptIdBuilder_ == null) {
/* 11921:11251 */           result.taskAttemptId_ = this.taskAttemptId_;
/* 11922:      */         } else {
/* 11923:11253 */           result.taskAttemptId_ = ((MRProtos.TaskAttemptIdProto)this.taskAttemptIdBuilder_.build());
/* 11924:      */         }
/* 11925:11255 */         result.bitField0_ = to_bitField0_;
/* 11926:11256 */         onBuilt();
/* 11927:11257 */         return result;
/* 11928:      */       }
/* 11929:      */       
/* 11930:      */       public Builder mergeFrom(Message other)
/* 11931:      */       {
/* 11932:11261 */         if ((other instanceof MRServiceProtos.FailTaskAttemptRequestProto)) {
/* 11933:11262 */           return mergeFrom((MRServiceProtos.FailTaskAttemptRequestProto)other);
/* 11934:      */         }
/* 11935:11264 */         super.mergeFrom(other);
/* 11936:11265 */         return this;
/* 11937:      */       }
/* 11938:      */       
/* 11939:      */       public Builder mergeFrom(MRServiceProtos.FailTaskAttemptRequestProto other)
/* 11940:      */       {
/* 11941:11270 */         if (other == MRServiceProtos.FailTaskAttemptRequestProto.getDefaultInstance()) {
/* 11942:11270 */           return this;
/* 11943:      */         }
/* 11944:11271 */         if (other.hasTaskAttemptId()) {
/* 11945:11272 */           mergeTaskAttemptId(other.getTaskAttemptId());
/* 11946:      */         }
/* 11947:11274 */         mergeUnknownFields(other.getUnknownFields());
/* 11948:11275 */         return this;
/* 11949:      */       }
/* 11950:      */       
/* 11951:      */       public final boolean isInitialized()
/* 11952:      */       {
/* 11953:11279 */         return true;
/* 11954:      */       }
/* 11955:      */       
/* 11956:      */       public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry)
/* 11957:      */         throws IOException
/* 11958:      */       {
/* 11959:11286 */         MRServiceProtos.FailTaskAttemptRequestProto parsedMessage = null;
/* 11960:      */         try
/* 11961:      */         {
/* 11962:11288 */           parsedMessage = (MRServiceProtos.FailTaskAttemptRequestProto)MRServiceProtos.FailTaskAttemptRequestProto.PARSER.parsePartialFrom(input, extensionRegistry);
/* 11963:      */         }
/* 11964:      */         catch (InvalidProtocolBufferException e)
/* 11965:      */         {
/* 11966:11290 */           parsedMessage = (MRServiceProtos.FailTaskAttemptRequestProto)e.getUnfinishedMessage();
/* 11967:11291 */           throw e;
/* 11968:      */         }
/* 11969:      */         finally
/* 11970:      */         {
/* 11971:11293 */           if (parsedMessage != null) {
/* 11972:11294 */             mergeFrom(parsedMessage);
/* 11973:      */           }
/* 11974:      */         }
/* 11975:11297 */         return this;
/* 11976:      */       }
/* 11977:      */       
/* 11978:11302 */       private MRProtos.TaskAttemptIdProto taskAttemptId_ = MRProtos.TaskAttemptIdProto.getDefaultInstance();
/* 11979:      */       private SingleFieldBuilder<MRProtos.TaskAttemptIdProto, MRProtos.TaskAttemptIdProto.Builder, MRProtos.TaskAttemptIdProtoOrBuilder> taskAttemptIdBuilder_;
/* 11980:      */       
/* 11981:      */       public boolean hasTaskAttemptId()
/* 11982:      */       {
/* 11983:11309 */         return (this.bitField0_ & 0x1) == 1;
/* 11984:      */       }
/* 11985:      */       
/* 11986:      */       public MRProtos.TaskAttemptIdProto getTaskAttemptId()
/* 11987:      */       {
/* 11988:11315 */         if (this.taskAttemptIdBuilder_ == null) {
/* 11989:11316 */           return this.taskAttemptId_;
/* 11990:      */         }
/* 11991:11318 */         return (MRProtos.TaskAttemptIdProto)this.taskAttemptIdBuilder_.getMessage();
/* 11992:      */       }
/* 11993:      */       
/* 11994:      */       public Builder setTaskAttemptId(MRProtos.TaskAttemptIdProto value)
/* 11995:      */       {
/* 11996:11325 */         if (this.taskAttemptIdBuilder_ == null)
/* 11997:      */         {
/* 11998:11326 */           if (value == null) {
/* 11999:11327 */             throw new NullPointerException();
/* 12000:      */           }
/* 12001:11329 */           this.taskAttemptId_ = value;
/* 12002:11330 */           onChanged();
/* 12003:      */         }
/* 12004:      */         else
/* 12005:      */         {
/* 12006:11332 */           this.taskAttemptIdBuilder_.setMessage(value);
/* 12007:      */         }
/* 12008:11334 */         this.bitField0_ |= 0x1;
/* 12009:11335 */         return this;
/* 12010:      */       }
/* 12011:      */       
/* 12012:      */       public Builder setTaskAttemptId(MRProtos.TaskAttemptIdProto.Builder builderForValue)
/* 12013:      */       {
/* 12014:11342 */         if (this.taskAttemptIdBuilder_ == null)
/* 12015:      */         {
/* 12016:11343 */           this.taskAttemptId_ = builderForValue.build();
/* 12017:11344 */           onChanged();
/* 12018:      */         }
/* 12019:      */         else
/* 12020:      */         {
/* 12021:11346 */           this.taskAttemptIdBuilder_.setMessage(builderForValue.build());
/* 12022:      */         }
/* 12023:11348 */         this.bitField0_ |= 0x1;
/* 12024:11349 */         return this;
/* 12025:      */       }
/* 12026:      */       
/* 12027:      */       public Builder mergeTaskAttemptId(MRProtos.TaskAttemptIdProto value)
/* 12028:      */       {
/* 12029:11355 */         if (this.taskAttemptIdBuilder_ == null)
/* 12030:      */         {
/* 12031:11356 */           if (((this.bitField0_ & 0x1) == 1) && (this.taskAttemptId_ != MRProtos.TaskAttemptIdProto.getDefaultInstance())) {
/* 12032:11358 */             this.taskAttemptId_ = MRProtos.TaskAttemptIdProto.newBuilder(this.taskAttemptId_).mergeFrom(value).buildPartial();
/* 12033:      */           } else {
/* 12034:11361 */             this.taskAttemptId_ = value;
/* 12035:      */           }
/* 12036:11363 */           onChanged();
/* 12037:      */         }
/* 12038:      */         else
/* 12039:      */         {
/* 12040:11365 */           this.taskAttemptIdBuilder_.mergeFrom(value);
/* 12041:      */         }
/* 12042:11367 */         this.bitField0_ |= 0x1;
/* 12043:11368 */         return this;
/* 12044:      */       }
/* 12045:      */       
/* 12046:      */       public Builder clearTaskAttemptId()
/* 12047:      */       {
/* 12048:11374 */         if (this.taskAttemptIdBuilder_ == null)
/* 12049:      */         {
/* 12050:11375 */           this.taskAttemptId_ = MRProtos.TaskAttemptIdProto.getDefaultInstance();
/* 12051:11376 */           onChanged();
/* 12052:      */         }
/* 12053:      */         else
/* 12054:      */         {
/* 12055:11378 */           this.taskAttemptIdBuilder_.clear();
/* 12056:      */         }
/* 12057:11380 */         this.bitField0_ &= 0xFFFFFFFE;
/* 12058:11381 */         return this;
/* 12059:      */       }
/* 12060:      */       
/* 12061:      */       public MRProtos.TaskAttemptIdProto.Builder getTaskAttemptIdBuilder()
/* 12062:      */       {
/* 12063:11387 */         this.bitField0_ |= 0x1;
/* 12064:11388 */         onChanged();
/* 12065:11389 */         return (MRProtos.TaskAttemptIdProto.Builder)getTaskAttemptIdFieldBuilder().getBuilder();
/* 12066:      */       }
/* 12067:      */       
/* 12068:      */       public MRProtos.TaskAttemptIdProtoOrBuilder getTaskAttemptIdOrBuilder()
/* 12069:      */       {
/* 12070:11395 */         if (this.taskAttemptIdBuilder_ != null) {
/* 12071:11396 */           return (MRProtos.TaskAttemptIdProtoOrBuilder)this.taskAttemptIdBuilder_.getMessageOrBuilder();
/* 12072:      */         }
/* 12073:11398 */         return this.taskAttemptId_;
/* 12074:      */       }
/* 12075:      */       
/* 12076:      */       private SingleFieldBuilder<MRProtos.TaskAttemptIdProto, MRProtos.TaskAttemptIdProto.Builder, MRProtos.TaskAttemptIdProtoOrBuilder> getTaskAttemptIdFieldBuilder()
/* 12077:      */       {
/* 12078:11407 */         if (this.taskAttemptIdBuilder_ == null)
/* 12079:      */         {
/* 12080:11408 */           this.taskAttemptIdBuilder_ = new SingleFieldBuilder(this.taskAttemptId_, getParentForChildren(), isClean());
/* 12081:      */           
/* 12082:      */ 
/* 12083:      */ 
/* 12084:      */ 
/* 12085:11413 */           this.taskAttemptId_ = null;
/* 12086:      */         }
/* 12087:11415 */         return this.taskAttemptIdBuilder_;
/* 12088:      */       }
/* 12089:      */     }
/* 12090:      */     
/* 12091:      */     static
/* 12092:      */     {
/* 12093:11422 */       defaultInstance = new FailTaskAttemptRequestProto(true);
/* 12094:11423 */       defaultInstance.initFields();
/* 12095:      */     }
/* 12096:      */   }
/* 12097:      */   
/* 12098:      */   public static abstract interface FailTaskAttemptResponseProtoOrBuilder
/* 12099:      */     extends MessageOrBuilder
/* 12100:      */   {}
/* 12101:      */   
/* 12102:      */   public static final class FailTaskAttemptResponseProto
/* 12103:      */     extends GeneratedMessage
/* 12104:      */     implements MRServiceProtos.FailTaskAttemptResponseProtoOrBuilder
/* 12105:      */   {
/* 12106:      */     private static final FailTaskAttemptResponseProto defaultInstance;
/* 12107:      */     private final UnknownFieldSet unknownFields;
/* 12108:      */     
/* 12109:      */     private FailTaskAttemptResponseProto(GeneratedMessage.Builder<?> builder)
/* 12110:      */     {
/* 12111:11440 */       super();
/* 12112:11441 */       this.unknownFields = builder.getUnknownFields();
/* 12113:      */     }
/* 12114:      */     
/* 12115:      */     private FailTaskAttemptResponseProto(boolean noInit)
/* 12116:      */     {
/* 12117:11443 */       this.unknownFields = UnknownFieldSet.getDefaultInstance();
/* 12118:      */     }
/* 12119:      */     
/* 12120:      */     public static FailTaskAttemptResponseProto getDefaultInstance()
/* 12121:      */     {
/* 12122:11447 */       return defaultInstance;
/* 12123:      */     }
/* 12124:      */     
/* 12125:      */     public FailTaskAttemptResponseProto getDefaultInstanceForType()
/* 12126:      */     {
/* 12127:11451 */       return defaultInstance;
/* 12128:      */     }
/* 12129:      */     
/* 12130:      */     public final UnknownFieldSet getUnknownFields()
/* 12131:      */     {
/* 12132:11458 */       return this.unknownFields;
/* 12133:      */     }
/* 12134:      */     
/* 12135:      */     private FailTaskAttemptResponseProto(CodedInputStream input, ExtensionRegistryLite extensionRegistry)
/* 12136:      */       throws InvalidProtocolBufferException
/* 12137:      */     {
/* 12138:11464 */       initFields();
/* 12139:11465 */       UnknownFieldSet.Builder unknownFields = UnknownFieldSet.newBuilder();
/* 12140:      */       try
/* 12141:      */       {
/* 12142:11468 */         boolean done = false;
/* 12143:11469 */         while (!done)
/* 12144:      */         {
/* 12145:11470 */           int tag = input.readTag();
/* 12146:11471 */           switch (tag)
/* 12147:      */           {
/* 12148:      */           case 0: 
/* 12149:11473 */             done = true;
/* 12150:11474 */             break;
/* 12151:      */           default: 
/* 12152:11476 */             if (!parseUnknownField(input, unknownFields, extensionRegistry, tag)) {
/* 12153:11478 */               done = true;
/* 12154:      */             }
/* 12155:      */             break;
/* 12156:      */           }
/* 12157:      */         }
/* 12158:      */       }
/* 12159:      */       catch (InvalidProtocolBufferException e)
/* 12160:      */       {
/* 12161:11485 */         throw e.setUnfinishedMessage(this);
/* 12162:      */       }
/* 12163:      */       catch (IOException e)
/* 12164:      */       {
/* 12165:11487 */         throw new InvalidProtocolBufferException(e.getMessage()).setUnfinishedMessage(this);
/* 12166:      */       }
/* 12167:      */       finally
/* 12168:      */       {
/* 12169:11490 */         this.unknownFields = unknownFields.build();
/* 12170:11491 */         makeExtensionsImmutable();
/* 12171:      */       }
/* 12172:      */     }
/* 12173:      */     
/* 12174:      */     public static final Descriptors.Descriptor getDescriptor()
/* 12175:      */     {
/* 12176:11496 */       return MRServiceProtos.internal_static_hadoop_mapreduce_FailTaskAttemptResponseProto_descriptor;
/* 12177:      */     }
/* 12178:      */     
/* 12179:      */     protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable()
/* 12180:      */     {
/* 12181:11501 */       return MRServiceProtos.internal_static_hadoop_mapreduce_FailTaskAttemptResponseProto_fieldAccessorTable.ensureFieldAccessorsInitialized(FailTaskAttemptResponseProto.class, Builder.class);
/* 12182:      */     }
/* 12183:      */     
/* 12184:11506 */     public static Parser<FailTaskAttemptResponseProto> PARSER = new AbstractParser()
/* 12185:      */     {
/* 12186:      */       public MRServiceProtos.FailTaskAttemptResponseProto parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry)
/* 12187:      */         throws InvalidProtocolBufferException
/* 12188:      */       {
/* 12189:11512 */         return new MRServiceProtos.FailTaskAttemptResponseProto(input, extensionRegistry, null);
/* 12190:      */       }
/* 12191:      */     };
/* 12192:      */     
/* 12193:      */     public Parser<FailTaskAttemptResponseProto> getParserForType()
/* 12194:      */     {
/* 12195:11518 */       return PARSER;
/* 12196:      */     }
/* 12197:      */     
/* 12198:11523 */     private byte memoizedIsInitialized = -1;
/* 12199:      */     
/* 12200:      */     private void initFields() {}
/* 12201:      */     
/* 12202:      */     public final boolean isInitialized()
/* 12203:      */     {
/* 12204:11525 */       byte isInitialized = this.memoizedIsInitialized;
/* 12205:11526 */       if (isInitialized != -1) {
/* 12206:11526 */         return isInitialized == 1;
/* 12207:      */       }
/* 12208:11528 */       this.memoizedIsInitialized = 1;
/* 12209:11529 */       return true;
/* 12210:      */     }
/* 12211:      */     
/* 12212:      */     public void writeTo(CodedOutputStream output)
/* 12213:      */       throws IOException
/* 12214:      */     {
/* 12215:11534 */       getSerializedSize();
/* 12216:11535 */       getUnknownFields().writeTo(output);
/* 12217:      */     }
/* 12218:      */     
/* 12219:11538 */     private int memoizedSerializedSize = -1;
/* 12220:      */     private static final long serialVersionUID = 0L;
/* 12221:      */     
/* 12222:      */     public int getSerializedSize()
/* 12223:      */     {
/* 12224:11540 */       int size = this.memoizedSerializedSize;
/* 12225:11541 */       if (size != -1) {
/* 12226:11541 */         return size;
/* 12227:      */       }
/* 12228:11543 */       size = 0;
/* 12229:11544 */       size += getUnknownFields().getSerializedSize();
/* 12230:11545 */       this.memoizedSerializedSize = size;
/* 12231:11546 */       return size;
/* 12232:      */     }
/* 12233:      */     
/* 12234:      */     protected Object writeReplace()
/* 12235:      */       throws ObjectStreamException
/* 12236:      */     {
/* 12237:11553 */       return super.writeReplace();
/* 12238:      */     }
/* 12239:      */     
/* 12240:      */     public boolean equals(Object obj)
/* 12241:      */     {
/* 12242:11558 */       if (obj == this) {
/* 12243:11559 */         return true;
/* 12244:      */       }
/* 12245:11561 */       if (!(obj instanceof FailTaskAttemptResponseProto)) {
/* 12246:11562 */         return super.equals(obj);
/* 12247:      */       }
/* 12248:11564 */       FailTaskAttemptResponseProto other = (FailTaskAttemptResponseProto)obj;
/* 12249:      */       
/* 12250:11566 */       boolean result = true;
/* 12251:11567 */       result = (result) && (getUnknownFields().equals(other.getUnknownFields()));
/* 12252:      */       
/* 12253:11569 */       return result;
/* 12254:      */     }
/* 12255:      */     
/* 12256:11572 */     private int memoizedHashCode = 0;
/* 12257:      */     
/* 12258:      */     public int hashCode()
/* 12259:      */     {
/* 12260:11575 */       if (this.memoizedHashCode != 0) {
/* 12261:11576 */         return this.memoizedHashCode;
/* 12262:      */       }
/* 12263:11578 */       int hash = 41;
/* 12264:11579 */       hash = 19 * hash + getDescriptorForType().hashCode();
/* 12265:11580 */       hash = 29 * hash + getUnknownFields().hashCode();
/* 12266:11581 */       this.memoizedHashCode = hash;
/* 12267:11582 */       return hash;
/* 12268:      */     }
/* 12269:      */     
/* 12270:      */     public static FailTaskAttemptResponseProto parseFrom(ByteString data)
/* 12271:      */       throws InvalidProtocolBufferException
/* 12272:      */     {
/* 12273:11588 */       return (FailTaskAttemptResponseProto)PARSER.parseFrom(data);
/* 12274:      */     }
/* 12275:      */     
/* 12276:      */     public static FailTaskAttemptResponseProto parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry)
/* 12277:      */       throws InvalidProtocolBufferException
/* 12278:      */     {
/* 12279:11594 */       return (FailTaskAttemptResponseProto)PARSER.parseFrom(data, extensionRegistry);
/* 12280:      */     }
/* 12281:      */     
/* 12282:      */     public static FailTaskAttemptResponseProto parseFrom(byte[] data)
/* 12283:      */       throws InvalidProtocolBufferException
/* 12284:      */     {
/* 12285:11598 */       return (FailTaskAttemptResponseProto)PARSER.parseFrom(data);
/* 12286:      */     }
/* 12287:      */     
/* 12288:      */     public static FailTaskAttemptResponseProto parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry)
/* 12289:      */       throws InvalidProtocolBufferException
/* 12290:      */     {
/* 12291:11604 */       return (FailTaskAttemptResponseProto)PARSER.parseFrom(data, extensionRegistry);
/* 12292:      */     }
/* 12293:      */     
/* 12294:      */     public static FailTaskAttemptResponseProto parseFrom(InputStream input)
/* 12295:      */       throws IOException
/* 12296:      */     {
/* 12297:11608 */       return (FailTaskAttemptResponseProto)PARSER.parseFrom(input);
/* 12298:      */     }
/* 12299:      */     
/* 12300:      */     public static FailTaskAttemptResponseProto parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry)
/* 12301:      */       throws IOException
/* 12302:      */     {
/* 12303:11614 */       return (FailTaskAttemptResponseProto)PARSER.parseFrom(input, extensionRegistry);
/* 12304:      */     }
/* 12305:      */     
/* 12306:      */     public static FailTaskAttemptResponseProto parseDelimitedFrom(InputStream input)
/* 12307:      */       throws IOException
/* 12308:      */     {
/* 12309:11618 */       return (FailTaskAttemptResponseProto)PARSER.parseDelimitedFrom(input);
/* 12310:      */     }
/* 12311:      */     
/* 12312:      */     public static FailTaskAttemptResponseProto parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry)
/* 12313:      */       throws IOException
/* 12314:      */     {
/* 12315:11624 */       return (FailTaskAttemptResponseProto)PARSER.parseDelimitedFrom(input, extensionRegistry);
/* 12316:      */     }
/* 12317:      */     
/* 12318:      */     public static FailTaskAttemptResponseProto parseFrom(CodedInputStream input)
/* 12319:      */       throws IOException
/* 12320:      */     {
/* 12321:11629 */       return (FailTaskAttemptResponseProto)PARSER.parseFrom(input);
/* 12322:      */     }
/* 12323:      */     
/* 12324:      */     public static FailTaskAttemptResponseProto parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry)
/* 12325:      */       throws IOException
/* 12326:      */     {
/* 12327:11635 */       return (FailTaskAttemptResponseProto)PARSER.parseFrom(input, extensionRegistry);
/* 12328:      */     }
/* 12329:      */     
/* 12330:      */     public static Builder newBuilder()
/* 12331:      */     {
/* 12332:11638 */       return Builder.access$18800();
/* 12333:      */     }
/* 12334:      */     
/* 12335:      */     public Builder newBuilderForType()
/* 12336:      */     {
/* 12337:11639 */       return newBuilder();
/* 12338:      */     }
/* 12339:      */     
/* 12340:      */     public static Builder newBuilder(FailTaskAttemptResponseProto prototype)
/* 12341:      */     {
/* 12342:11641 */       return newBuilder().mergeFrom(prototype);
/* 12343:      */     }
/* 12344:      */     
/* 12345:      */     public Builder toBuilder()
/* 12346:      */     {
/* 12347:11643 */       return newBuilder(this);
/* 12348:      */     }
/* 12349:      */     
/* 12350:      */     protected Builder newBuilderForType(GeneratedMessage.BuilderParent parent)
/* 12351:      */     {
/* 12352:11648 */       Builder builder = new Builder(parent, null);
/* 12353:11649 */       return builder;
/* 12354:      */     }
/* 12355:      */     
/* 12356:      */     public static final class Builder
/* 12357:      */       extends GeneratedMessage.Builder<Builder>
/* 12358:      */       implements MRServiceProtos.FailTaskAttemptResponseProtoOrBuilder
/* 12359:      */     {
/* 12360:      */       public static final Descriptors.Descriptor getDescriptor()
/* 12361:      */       {
/* 12362:11659 */         return MRServiceProtos.internal_static_hadoop_mapreduce_FailTaskAttemptResponseProto_descriptor;
/* 12363:      */       }
/* 12364:      */       
/* 12365:      */       protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable()
/* 12366:      */       {
/* 12367:11664 */         return MRServiceProtos.internal_static_hadoop_mapreduce_FailTaskAttemptResponseProto_fieldAccessorTable.ensureFieldAccessorsInitialized(MRServiceProtos.FailTaskAttemptResponseProto.class, Builder.class);
/* 12368:      */       }
/* 12369:      */       
/* 12370:      */       private Builder()
/* 12371:      */       {
/* 12372:11671 */         maybeForceBuilderInitialization();
/* 12373:      */       }
/* 12374:      */       
/* 12375:      */       private Builder(GeneratedMessage.BuilderParent parent)
/* 12376:      */       {
/* 12377:11676 */         super();
/* 12378:11677 */         maybeForceBuilderInitialization();
/* 12379:      */       }
/* 12380:      */       
/* 12381:      */       private void maybeForceBuilderInitialization()
/* 12382:      */       {
/* 12383:11680 */         if (MRServiceProtos.FailTaskAttemptResponseProto.alwaysUseFieldBuilders) {}
/* 12384:      */       }
/* 12385:      */       
/* 12386:      */       private static Builder create()
/* 12387:      */       {
/* 12388:11684 */         return new Builder();
/* 12389:      */       }
/* 12390:      */       
/* 12391:      */       public Builder clear()
/* 12392:      */       {
/* 12393:11688 */         super.clear();
/* 12394:11689 */         return this;
/* 12395:      */       }
/* 12396:      */       
/* 12397:      */       public Builder clone()
/* 12398:      */       {
/* 12399:11693 */         return create().mergeFrom(buildPartial());
/* 12400:      */       }
/* 12401:      */       
/* 12402:      */       public Descriptors.Descriptor getDescriptorForType()
/* 12403:      */       {
/* 12404:11698 */         return MRServiceProtos.internal_static_hadoop_mapreduce_FailTaskAttemptResponseProto_descriptor;
/* 12405:      */       }
/* 12406:      */       
/* 12407:      */       public MRServiceProtos.FailTaskAttemptResponseProto getDefaultInstanceForType()
/* 12408:      */       {
/* 12409:11702 */         return MRServiceProtos.FailTaskAttemptResponseProto.getDefaultInstance();
/* 12410:      */       }
/* 12411:      */       
/* 12412:      */       public MRServiceProtos.FailTaskAttemptResponseProto build()
/* 12413:      */       {
/* 12414:11706 */         MRServiceProtos.FailTaskAttemptResponseProto result = buildPartial();
/* 12415:11707 */         if (!result.isInitialized()) {
/* 12416:11708 */           throw newUninitializedMessageException(result);
/* 12417:      */         }
/* 12418:11710 */         return result;
/* 12419:      */       }
/* 12420:      */       
/* 12421:      */       public MRServiceProtos.FailTaskAttemptResponseProto buildPartial()
/* 12422:      */       {
/* 12423:11714 */         MRServiceProtos.FailTaskAttemptResponseProto result = new MRServiceProtos.FailTaskAttemptResponseProto(this, null);
/* 12424:11715 */         onBuilt();
/* 12425:11716 */         return result;
/* 12426:      */       }
/* 12427:      */       
/* 12428:      */       public Builder mergeFrom(Message other)
/* 12429:      */       {
/* 12430:11720 */         if ((other instanceof MRServiceProtos.FailTaskAttemptResponseProto)) {
/* 12431:11721 */           return mergeFrom((MRServiceProtos.FailTaskAttemptResponseProto)other);
/* 12432:      */         }
/* 12433:11723 */         super.mergeFrom(other);
/* 12434:11724 */         return this;
/* 12435:      */       }
/* 12436:      */       
/* 12437:      */       public Builder mergeFrom(MRServiceProtos.FailTaskAttemptResponseProto other)
/* 12438:      */       {
/* 12439:11729 */         if (other == MRServiceProtos.FailTaskAttemptResponseProto.getDefaultInstance()) {
/* 12440:11729 */           return this;
/* 12441:      */         }
/* 12442:11730 */         mergeUnknownFields(other.getUnknownFields());
/* 12443:11731 */         return this;
/* 12444:      */       }
/* 12445:      */       
/* 12446:      */       public final boolean isInitialized()
/* 12447:      */       {
/* 12448:11735 */         return true;
/* 12449:      */       }
/* 12450:      */       
/* 12451:      */       public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry)
/* 12452:      */         throws IOException
/* 12453:      */       {
/* 12454:11742 */         MRServiceProtos.FailTaskAttemptResponseProto parsedMessage = null;
/* 12455:      */         try
/* 12456:      */         {
/* 12457:11744 */           parsedMessage = (MRServiceProtos.FailTaskAttemptResponseProto)MRServiceProtos.FailTaskAttemptResponseProto.PARSER.parsePartialFrom(input, extensionRegistry);
/* 12458:      */         }
/* 12459:      */         catch (InvalidProtocolBufferException e)
/* 12460:      */         {
/* 12461:11746 */           parsedMessage = (MRServiceProtos.FailTaskAttemptResponseProto)e.getUnfinishedMessage();
/* 12462:11747 */           throw e;
/* 12463:      */         }
/* 12464:      */         finally
/* 12465:      */         {
/* 12466:11749 */           if (parsedMessage != null) {
/* 12467:11750 */             mergeFrom(parsedMessage);
/* 12468:      */           }
/* 12469:      */         }
/* 12470:11753 */         return this;
/* 12471:      */       }
/* 12472:      */     }
/* 12473:      */     
/* 12474:      */     static
/* 12475:      */     {
/* 12476:11760 */       defaultInstance = new FailTaskAttemptResponseProto(true);
/* 12477:11761 */       defaultInstance.initFields();
/* 12478:      */     }
/* 12479:      */   }
/* 12480:      */   
/* 12481:      */   public static Descriptors.FileDescriptor getDescriptor()
/* 12482:      */   {
/* 12483:11880 */     return descriptor;
/* 12484:      */   }
/* 12485:      */   
/* 12486:      */   static
/* 12487:      */   {
/* 12488:11885 */     String[] descriptorData = { "\n\027mr_service_protos.proto\022\020hadoop.mapreduce\032\016Security.proto\032\017mr_protos.proto\032\021yarn_protos.proto\"H\n\030GetJobReportRequestProto\022,\n\006job_id\030\001 \001(\0132\034.hadoop.mapreduce.JobIdProto\"Q\n\031GetJobReportResponseProto\0224\n\njob_report\030\001 \001(\0132 .hadoop.mapreduce.JobReportProto\"K\n\031GetTaskReportRequestProto\022.\n\007task_id\030\001 \001(\0132\035.hadoop.mapreduce.TaskIdProto\"T\n\032GetTaskReportResponseProto\0226\n\013task_report\030\001 \001(\0132!.hadoop.mapreduce.", "TaskReportProto\"a\n GetTaskAttemptReportRequestProto\022=\n\017task_attempt_id\030\001 \001(\0132$.hadoop.mapreduce.TaskAttemptIdProto\"j\n!GetTaskAttemptReportResponseProto\022E\n\023task_attempt_report\030\001 \001(\0132(.hadoop.mapreduce.TaskAttemptReportProto\"G\n\027GetCountersRequestProto\022,\n\006job_id\030\001 \001(\0132\034.hadoop.mapreduce.JobIdProto\"M\n\030GetCountersResponseProto\0221\n\bcounters\030\001 \001(\0132\037.hadoop.mapreduce.CountersProto\"\001\n*GetTaskAttemptComplet", "ionEventsRequestProto\022,\n\006job_id\030\001 \001(\0132\034.hadoop.mapreduce.JobIdProto\022\025\n\rfrom_event_id\030\002 \001(\005\022\022\n\nmax_events\030\003 \001(\005\"{\n+GetTaskAttemptCompletionEventsResponseProto\022L\n\021completion_events\030\001 \003(\01321.hadoop.mapreduce.TaskAttemptCompletionEventProto\"~\n\032GetTaskReportsRequestProto\022,\n\006job_id\030\001 \001(\0132\034.hadoop.mapreduce.JobIdProto\0222\n\ttask_type\030\002 \001(\0162\037.hadoop.mapreduce.TaskTypeProto\"V\n\033GetTaskReportsResponseProto\0227\n\fta", "sk_reports\030\001 \003(\0132!.hadoop.mapreduce.TaskReportProto\"[\n\032GetDiagnosticsRequestProto\022=\n\017task_attempt_id\030\001 \001(\0132$.hadoop.mapreduce.TaskAttemptIdProto\"2\n\033GetDiagnosticsResponseProto\022\023\n\013diagnostics\030\001 \003(\t\"C\n\023KillJobRequestProto\022,\n\006job_id\030\001 \001(\0132\034.hadoop.mapreduce.JobIdProto\"\026\n\024KillJobResponseProto\"F\n\024KillTaskRequestProto\022.\n\007task_id\030\001 \001(\0132\035.hadoop.mapreduce.TaskIdProto\"\027\n\025KillTaskResponseProto\"\\\n\033KillTaskAt", "temptRequestProto\022=\n\017task_attempt_id\030\001 \001(\0132$.hadoop.mapreduce.TaskAttemptIdProto\"\036\n\034KillTaskAttemptResponseProto\"\\\n\033FailTaskAttemptRequestProto\022=\n\017task_attempt_id\030\001 \001(\0132$.hadoop.mapreduce.TaskAttemptIdProto\"\036\n\034FailTaskAttemptResponseProtoB=\n$org.apache.hadoop.mapreduce.v2.protoB\017MRServiceProtos\001\001 \001\001" };
/* 12489:      */     
/* 12490:      */ 
/* 12491:      */ 
/* 12492:      */ 
/* 12493:      */ 
/* 12494:      */ 
/* 12495:      */ 
/* 12496:      */ 
/* 12497:      */ 
/* 12498:      */ 
/* 12499:      */ 
/* 12500:      */ 
/* 12501:      */ 
/* 12502:      */ 
/* 12503:      */ 
/* 12504:      */ 
/* 12505:      */ 
/* 12506:      */ 
/* 12507:      */ 
/* 12508:      */ 
/* 12509:      */ 
/* 12510:      */ 
/* 12511:      */ 
/* 12512:      */ 
/* 12513:      */ 
/* 12514:      */ 
/* 12515:      */ 
/* 12516:      */ 
/* 12517:      */ 
/* 12518:      */ 
/* 12519:      */ 
/* 12520:      */ 
/* 12521:      */ 
/* 12522:      */ 
/* 12523:      */ 
/* 12524:      */ 
/* 12525:      */ 
/* 12526:      */ 
/* 12527:      */ 
/* 12528:      */ 
/* 12529:      */ 
/* 12530:      */ 
/* 12531:      */ 
/* 12532:      */ 
/* 12533:      */ 
/* 12534:      */ 
/* 12535:      */ 
/* 12536:      */ 
/* 12537:      */ 
/* 12538:11935 */     Descriptors.FileDescriptor.InternalDescriptorAssigner assigner = new Descriptors.FileDescriptor.InternalDescriptorAssigner()
/* 12539:      */     {
/* 12540:      */       public ExtensionRegistry assignDescriptors(Descriptors.FileDescriptor root)
/* 12541:      */       {
/* 12542:11939 */         MRServiceProtos.access$19202(root);
/* 12543:11940 */         MRServiceProtos.access$002((Descriptors.Descriptor)MRServiceProtos.getDescriptor().getMessageTypes().get(0));
/* 12544:      */         
/* 12545:11942 */         MRServiceProtos.access$102(new GeneratedMessage.FieldAccessorTable(MRServiceProtos.internal_static_hadoop_mapreduce_GetJobReportRequestProto_descriptor, new String[] { "JobId" }));
/* 12546:      */         
/* 12547:      */ 
/* 12548:      */ 
/* 12549:11946 */         MRServiceProtos.access$902((Descriptors.Descriptor)MRServiceProtos.getDescriptor().getMessageTypes().get(1));
/* 12550:      */         
/* 12551:11948 */         MRServiceProtos.access$1002(new GeneratedMessage.FieldAccessorTable(MRServiceProtos.internal_static_hadoop_mapreduce_GetJobReportResponseProto_descriptor, new String[] { "JobReport" }));
/* 12552:      */         
/* 12553:      */ 
/* 12554:      */ 
/* 12555:11952 */         MRServiceProtos.access$1802((Descriptors.Descriptor)MRServiceProtos.getDescriptor().getMessageTypes().get(2));
/* 12556:      */         
/* 12557:11954 */         MRServiceProtos.access$1902(new GeneratedMessage.FieldAccessorTable(MRServiceProtos.internal_static_hadoop_mapreduce_GetTaskReportRequestProto_descriptor, new String[] { "TaskId" }));
/* 12558:      */         
/* 12559:      */ 
/* 12560:      */ 
/* 12561:11958 */         MRServiceProtos.access$2702((Descriptors.Descriptor)MRServiceProtos.getDescriptor().getMessageTypes().get(3));
/* 12562:      */         
/* 12563:11960 */         MRServiceProtos.access$2802(new GeneratedMessage.FieldAccessorTable(MRServiceProtos.internal_static_hadoop_mapreduce_GetTaskReportResponseProto_descriptor, new String[] { "TaskReport" }));
/* 12564:      */         
/* 12565:      */ 
/* 12566:      */ 
/* 12567:11964 */         MRServiceProtos.access$3602((Descriptors.Descriptor)MRServiceProtos.getDescriptor().getMessageTypes().get(4));
/* 12568:      */         
/* 12569:11966 */         MRServiceProtos.access$3702(new GeneratedMessage.FieldAccessorTable(MRServiceProtos.internal_static_hadoop_mapreduce_GetTaskAttemptReportRequestProto_descriptor, new String[] { "TaskAttemptId" }));
/* 12570:      */         
/* 12571:      */ 
/* 12572:      */ 
/* 12573:11970 */         MRServiceProtos.access$4502((Descriptors.Descriptor)MRServiceProtos.getDescriptor().getMessageTypes().get(5));
/* 12574:      */         
/* 12575:11972 */         MRServiceProtos.access$4602(new GeneratedMessage.FieldAccessorTable(MRServiceProtos.internal_static_hadoop_mapreduce_GetTaskAttemptReportResponseProto_descriptor, new String[] { "TaskAttemptReport" }));
/* 12576:      */         
/* 12577:      */ 
/* 12578:      */ 
/* 12579:11976 */         MRServiceProtos.access$5402((Descriptors.Descriptor)MRServiceProtos.getDescriptor().getMessageTypes().get(6));
/* 12580:      */         
/* 12581:11978 */         MRServiceProtos.access$5502(new GeneratedMessage.FieldAccessorTable(MRServiceProtos.internal_static_hadoop_mapreduce_GetCountersRequestProto_descriptor, new String[] { "JobId" }));
/* 12582:      */         
/* 12583:      */ 
/* 12584:      */ 
/* 12585:11982 */         MRServiceProtos.access$6302((Descriptors.Descriptor)MRServiceProtos.getDescriptor().getMessageTypes().get(7));
/* 12586:      */         
/* 12587:11984 */         MRServiceProtos.access$6402(new GeneratedMessage.FieldAccessorTable(MRServiceProtos.internal_static_hadoop_mapreduce_GetCountersResponseProto_descriptor, new String[] { "Counters" }));
/* 12588:      */         
/* 12589:      */ 
/* 12590:      */ 
/* 12591:11988 */         MRServiceProtos.access$7202((Descriptors.Descriptor)MRServiceProtos.getDescriptor().getMessageTypes().get(8));
/* 12592:      */         
/* 12593:11990 */         MRServiceProtos.access$7302(new GeneratedMessage.FieldAccessorTable(MRServiceProtos.internal_static_hadoop_mapreduce_GetTaskAttemptCompletionEventsRequestProto_descriptor, new String[] { "JobId", "FromEventId", "MaxEvents" }));
/* 12594:      */         
/* 12595:      */ 
/* 12596:      */ 
/* 12597:11994 */         MRServiceProtos.access$8302((Descriptors.Descriptor)MRServiceProtos.getDescriptor().getMessageTypes().get(9));
/* 12598:      */         
/* 12599:11996 */         MRServiceProtos.access$8402(new GeneratedMessage.FieldAccessorTable(MRServiceProtos.internal_static_hadoop_mapreduce_GetTaskAttemptCompletionEventsResponseProto_descriptor, new String[] { "CompletionEvents" }));
/* 12600:      */         
/* 12601:      */ 
/* 12602:      */ 
/* 12603:12000 */         MRServiceProtos.access$9202((Descriptors.Descriptor)MRServiceProtos.getDescriptor().getMessageTypes().get(10));
/* 12604:      */         
/* 12605:12002 */         MRServiceProtos.access$9302(new GeneratedMessage.FieldAccessorTable(MRServiceProtos.internal_static_hadoop_mapreduce_GetTaskReportsRequestProto_descriptor, new String[] { "JobId", "TaskType" }));
/* 12606:      */         
/* 12607:      */ 
/* 12608:      */ 
/* 12609:12006 */         MRServiceProtos.access$10202((Descriptors.Descriptor)MRServiceProtos.getDescriptor().getMessageTypes().get(11));
/* 12610:      */         
/* 12611:12008 */         MRServiceProtos.access$10302(new GeneratedMessage.FieldAccessorTable(MRServiceProtos.internal_static_hadoop_mapreduce_GetTaskReportsResponseProto_descriptor, new String[] { "TaskReports" }));
/* 12612:      */         
/* 12613:      */ 
/* 12614:      */ 
/* 12615:12012 */         MRServiceProtos.access$11102((Descriptors.Descriptor)MRServiceProtos.getDescriptor().getMessageTypes().get(12));
/* 12616:      */         
/* 12617:12014 */         MRServiceProtos.access$11202(new GeneratedMessage.FieldAccessorTable(MRServiceProtos.internal_static_hadoop_mapreduce_GetDiagnosticsRequestProto_descriptor, new String[] { "TaskAttemptId" }));
/* 12618:      */         
/* 12619:      */ 
/* 12620:      */ 
/* 12621:12018 */         MRServiceProtos.access$12002((Descriptors.Descriptor)MRServiceProtos.getDescriptor().getMessageTypes().get(13));
/* 12622:      */         
/* 12623:12020 */         MRServiceProtos.access$12102(new GeneratedMessage.FieldAccessorTable(MRServiceProtos.internal_static_hadoop_mapreduce_GetDiagnosticsResponseProto_descriptor, new String[] { "Diagnostics" }));
/* 12624:      */         
/* 12625:      */ 
/* 12626:      */ 
/* 12627:12024 */         MRServiceProtos.access$12802((Descriptors.Descriptor)MRServiceProtos.getDescriptor().getMessageTypes().get(14));
/* 12628:      */         
/* 12629:12026 */         MRServiceProtos.access$12902(new GeneratedMessage.FieldAccessorTable(MRServiceProtos.internal_static_hadoop_mapreduce_KillJobRequestProto_descriptor, new String[] { "JobId" }));
/* 12630:      */         
/* 12631:      */ 
/* 12632:      */ 
/* 12633:12030 */         MRServiceProtos.access$13702((Descriptors.Descriptor)MRServiceProtos.getDescriptor().getMessageTypes().get(15));
/* 12634:      */         
/* 12635:12032 */         MRServiceProtos.access$13802(new GeneratedMessage.FieldAccessorTable(MRServiceProtos.internal_static_hadoop_mapreduce_KillJobResponseProto_descriptor, new String[0]));
/* 12636:      */         
/* 12637:      */ 
/* 12638:      */ 
/* 12639:12036 */         MRServiceProtos.access$14402((Descriptors.Descriptor)MRServiceProtos.getDescriptor().getMessageTypes().get(16));
/* 12640:      */         
/* 12641:12038 */         MRServiceProtos.access$14502(new GeneratedMessage.FieldAccessorTable(MRServiceProtos.internal_static_hadoop_mapreduce_KillTaskRequestProto_descriptor, new String[] { "TaskId" }));
/* 12642:      */         
/* 12643:      */ 
/* 12644:      */ 
/* 12645:12042 */         MRServiceProtos.access$15302((Descriptors.Descriptor)MRServiceProtos.getDescriptor().getMessageTypes().get(17));
/* 12646:      */         
/* 12647:12044 */         MRServiceProtos.access$15402(new GeneratedMessage.FieldAccessorTable(MRServiceProtos.internal_static_hadoop_mapreduce_KillTaskResponseProto_descriptor, new String[0]));
/* 12648:      */         
/* 12649:      */ 
/* 12650:      */ 
/* 12651:12048 */         MRServiceProtos.access$16002((Descriptors.Descriptor)MRServiceProtos.getDescriptor().getMessageTypes().get(18));
/* 12652:      */         
/* 12653:12050 */         MRServiceProtos.access$16102(new GeneratedMessage.FieldAccessorTable(MRServiceProtos.internal_static_hadoop_mapreduce_KillTaskAttemptRequestProto_descriptor, new String[] { "TaskAttemptId" }));
/* 12654:      */         
/* 12655:      */ 
/* 12656:      */ 
/* 12657:12054 */         MRServiceProtos.access$16902((Descriptors.Descriptor)MRServiceProtos.getDescriptor().getMessageTypes().get(19));
/* 12658:      */         
/* 12659:12056 */         MRServiceProtos.access$17002(new GeneratedMessage.FieldAccessorTable(MRServiceProtos.internal_static_hadoop_mapreduce_KillTaskAttemptResponseProto_descriptor, new String[0]));
/* 12660:      */         
/* 12661:      */ 
/* 12662:      */ 
/* 12663:12060 */         MRServiceProtos.access$17602((Descriptors.Descriptor)MRServiceProtos.getDescriptor().getMessageTypes().get(20));
/* 12664:      */         
/* 12665:12062 */         MRServiceProtos.access$17702(new GeneratedMessage.FieldAccessorTable(MRServiceProtos.internal_static_hadoop_mapreduce_FailTaskAttemptRequestProto_descriptor, new String[] { "TaskAttemptId" }));
/* 12666:      */         
/* 12667:      */ 
/* 12668:      */ 
/* 12669:12066 */         MRServiceProtos.access$18502((Descriptors.Descriptor)MRServiceProtos.getDescriptor().getMessageTypes().get(21));
/* 12670:      */         
/* 12671:12068 */         MRServiceProtos.access$18602(new GeneratedMessage.FieldAccessorTable(MRServiceProtos.internal_static_hadoop_mapreduce_FailTaskAttemptResponseProto_descriptor, new String[0]));
/* 12672:      */         
/* 12673:      */ 
/* 12674:      */ 
/* 12675:12072 */         return null;
/* 12676:      */       }
/* 12677:12074 */     };
/* 12678:12075 */     Descriptors.FileDescriptor.internalBuildGeneratedFileFrom(descriptorData, new Descriptors.FileDescriptor[] { SecurityProtos.getDescriptor(), MRProtos.getDescriptor(), YarnProtos.getDescriptor() }, assigner);
/* 12679:      */   }
/* 12680:      */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-common-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.v2.proto.MRServiceProtos
 * JD-Core Version:    0.7.0.1
 */