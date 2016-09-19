/*     1:      */ package org.apache.hadoop.mapreduce.v2.proto;
/*     2:      */ 
/*     3:      */ import com.google.protobuf.AbstractParser;
/*     4:      */ import com.google.protobuf.ByteString;
/*     5:      */ import com.google.protobuf.CodedInputStream;
/*     6:      */ import com.google.protobuf.CodedOutputStream;
/*     7:      */ import com.google.protobuf.Descriptors.Descriptor;
/*     8:      */ import com.google.protobuf.Descriptors.EnumDescriptor;
/*     9:      */ import com.google.protobuf.Descriptors.EnumValueDescriptor;
/*    10:      */ import com.google.protobuf.Descriptors.FileDescriptor;
/*    11:      */ import com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner;
/*    12:      */ import com.google.protobuf.ExtensionRegistry;
/*    13:      */ import com.google.protobuf.ExtensionRegistryLite;
/*    14:      */ import com.google.protobuf.GeneratedMessage;
/*    15:      */ import com.google.protobuf.GeneratedMessage.Builder;
/*    16:      */ import com.google.protobuf.GeneratedMessage.BuilderParent;
/*    17:      */ import com.google.protobuf.GeneratedMessage.FieldAccessorTable;
/*    18:      */ import com.google.protobuf.Internal.EnumLiteMap;
/*    19:      */ import com.google.protobuf.InvalidProtocolBufferException;
/*    20:      */ import com.google.protobuf.LazyStringArrayList;
/*    21:      */ import com.google.protobuf.LazyStringList;
/*    22:      */ import com.google.protobuf.Message;
/*    23:      */ import com.google.protobuf.MessageLite;
/*    24:      */ import com.google.protobuf.MessageOrBuilder;
/*    25:      */ import com.google.protobuf.Parser;
/*    26:      */ import com.google.protobuf.ProtocolMessageEnum;
/*    27:      */ import com.google.protobuf.RepeatedFieldBuilder;
/*    28:      */ import com.google.protobuf.SingleFieldBuilder;
/*    29:      */ import com.google.protobuf.UnknownFieldSet;
/*    30:      */ import com.google.protobuf.UnknownFieldSet.Builder;
/*    31:      */ import com.google.protobuf.UnmodifiableLazyStringList;
/*    32:      */ import java.io.IOException;
/*    33:      */ import java.io.InputStream;
/*    34:      */ import java.io.ObjectStreamException;
/*    35:      */ import java.util.ArrayList;
/*    36:      */ import java.util.Collections;
/*    37:      */ import java.util.List;
/*    38:      */ import org.apache.hadoop.yarn.proto.YarnProtos;
/*    39:      */ import org.apache.hadoop.yarn.proto.YarnProtos.ApplicationAttemptIdProto;
/*    40:      */ import org.apache.hadoop.yarn.proto.YarnProtos.ApplicationAttemptIdProto.Builder;
/*    41:      */ import org.apache.hadoop.yarn.proto.YarnProtos.ApplicationAttemptIdProtoOrBuilder;
/*    42:      */ import org.apache.hadoop.yarn.proto.YarnProtos.ApplicationIdProto;
/*    43:      */ import org.apache.hadoop.yarn.proto.YarnProtos.ApplicationIdProto.Builder;
/*    44:      */ import org.apache.hadoop.yarn.proto.YarnProtos.ApplicationIdProtoOrBuilder;
/*    45:      */ import org.apache.hadoop.yarn.proto.YarnProtos.ContainerIdProto;
/*    46:      */ import org.apache.hadoop.yarn.proto.YarnProtos.ContainerIdProto.Builder;
/*    47:      */ import org.apache.hadoop.yarn.proto.YarnProtos.ContainerIdProtoOrBuilder;
/*    48:      */ 
/*    49:      */ public final class MRProtos
/*    50:      */ {
/*    51:      */   private static Descriptors.Descriptor internal_static_hadoop_mapreduce_JobIdProto_descriptor;
/*    52:      */   private static GeneratedMessage.FieldAccessorTable internal_static_hadoop_mapreduce_JobIdProto_fieldAccessorTable;
/*    53:      */   private static Descriptors.Descriptor internal_static_hadoop_mapreduce_TaskIdProto_descriptor;
/*    54:      */   private static GeneratedMessage.FieldAccessorTable internal_static_hadoop_mapreduce_TaskIdProto_fieldAccessorTable;
/*    55:      */   private static Descriptors.Descriptor internal_static_hadoop_mapreduce_TaskAttemptIdProto_descriptor;
/*    56:      */   private static GeneratedMessage.FieldAccessorTable internal_static_hadoop_mapreduce_TaskAttemptIdProto_fieldAccessorTable;
/*    57:      */   private static Descriptors.Descriptor internal_static_hadoop_mapreduce_CounterProto_descriptor;
/*    58:      */   private static GeneratedMessage.FieldAccessorTable internal_static_hadoop_mapreduce_CounterProto_fieldAccessorTable;
/*    59:      */   private static Descriptors.Descriptor internal_static_hadoop_mapreduce_CounterGroupProto_descriptor;
/*    60:      */   private static GeneratedMessage.FieldAccessorTable internal_static_hadoop_mapreduce_CounterGroupProto_fieldAccessorTable;
/*    61:      */   private static Descriptors.Descriptor internal_static_hadoop_mapreduce_CountersProto_descriptor;
/*    62:      */   private static GeneratedMessage.FieldAccessorTable internal_static_hadoop_mapreduce_CountersProto_fieldAccessorTable;
/*    63:      */   private static Descriptors.Descriptor internal_static_hadoop_mapreduce_TaskReportProto_descriptor;
/*    64:      */   private static GeneratedMessage.FieldAccessorTable internal_static_hadoop_mapreduce_TaskReportProto_fieldAccessorTable;
/*    65:      */   private static Descriptors.Descriptor internal_static_hadoop_mapreduce_TaskAttemptReportProto_descriptor;
/*    66:      */   private static GeneratedMessage.FieldAccessorTable internal_static_hadoop_mapreduce_TaskAttemptReportProto_fieldAccessorTable;
/*    67:      */   private static Descriptors.Descriptor internal_static_hadoop_mapreduce_JobReportProto_descriptor;
/*    68:      */   private static GeneratedMessage.FieldAccessorTable internal_static_hadoop_mapreduce_JobReportProto_fieldAccessorTable;
/*    69:      */   private static Descriptors.Descriptor internal_static_hadoop_mapreduce_AMInfoProto_descriptor;
/*    70:      */   private static GeneratedMessage.FieldAccessorTable internal_static_hadoop_mapreduce_AMInfoProto_fieldAccessorTable;
/*    71:      */   private static Descriptors.Descriptor internal_static_hadoop_mapreduce_TaskAttemptCompletionEventProto_descriptor;
/*    72:      */   private static GeneratedMessage.FieldAccessorTable internal_static_hadoop_mapreduce_TaskAttemptCompletionEventProto_fieldAccessorTable;
/*    73:      */   private static Descriptors.Descriptor internal_static_hadoop_mapreduce_StringCounterMapProto_descriptor;
/*    74:      */   private static GeneratedMessage.FieldAccessorTable internal_static_hadoop_mapreduce_StringCounterMapProto_fieldAccessorTable;
/*    75:      */   private static Descriptors.Descriptor internal_static_hadoop_mapreduce_StringCounterGroupMapProto_descriptor;
/*    76:      */   private static GeneratedMessage.FieldAccessorTable internal_static_hadoop_mapreduce_StringCounterGroupMapProto_fieldAccessorTable;
/*    77:      */   private static Descriptors.FileDescriptor descriptor;
/*    78:      */   public static void registerAllExtensions(ExtensionRegistry registry) {}
/*    79:      */   
/*    80:      */   public static enum TaskTypeProto
/*    81:      */     implements ProtocolMessageEnum
/*    82:      */   {
/*    83:   19 */     MAP(0, 1),  REDUCE(1, 2);
/*    84:      */     
/*    85:      */     public static final int MAP_VALUE = 1;
/*    86:      */     public static final int REDUCE_VALUE = 2;
/*    87:      */     
/*    88:      */     public final int getNumber()
/*    89:      */     {
/*    90:   36 */       return this.value;
/*    91:      */     }
/*    92:      */     
/*    93:      */     public static TaskTypeProto valueOf(int value)
/*    94:      */     {
/*    95:   39 */       switch (value)
/*    96:      */       {
/*    97:      */       case 1: 
/*    98:   40 */         return MAP;
/*    99:      */       case 2: 
/*   100:   41 */         return REDUCE;
/*   101:      */       }
/*   102:   42 */       return null;
/*   103:      */     }
/*   104:      */     
/*   105:      */     public static Internal.EnumLiteMap<TaskTypeProto> internalGetValueMap()
/*   106:      */     {
/*   107:   48 */       return internalValueMap;
/*   108:      */     }
/*   109:      */     
/*   110:   51 */     private static Internal.EnumLiteMap<TaskTypeProto> internalValueMap = new Internal.EnumLiteMap()
/*   111:      */     {
/*   112:      */       public MRProtos.TaskTypeProto findValueByNumber(int number)
/*   113:      */       {
/*   114:   54 */         return MRProtos.TaskTypeProto.valueOf(number);
/*   115:      */       }
/*   116:      */     };
/*   117:      */     
/*   118:      */     public final Descriptors.EnumValueDescriptor getValueDescriptor()
/*   119:      */     {
/*   120:   60 */       return (Descriptors.EnumValueDescriptor)getDescriptor().getValues().get(this.index);
/*   121:      */     }
/*   122:      */     
/*   123:      */     public final Descriptors.EnumDescriptor getDescriptorForType()
/*   124:      */     {
/*   125:   64 */       return getDescriptor();
/*   126:      */     }
/*   127:      */     
/*   128:      */     public static final Descriptors.EnumDescriptor getDescriptor()
/*   129:      */     {
/*   130:   68 */       return (Descriptors.EnumDescriptor)MRProtos.getDescriptor().getEnumTypes().get(0);
/*   131:      */     }
/*   132:      */     
/*   133:   71 */     private static final TaskTypeProto[] VALUES = values();
/*   134:      */     private final int index;
/*   135:      */     private final int value;
/*   136:      */     
/*   137:      */     public static TaskTypeProto valueOf(Descriptors.EnumValueDescriptor desc)
/*   138:      */     {
/*   139:   75 */       if (desc.getType() != getDescriptor()) {
/*   140:   76 */         throw new IllegalArgumentException("EnumValueDescriptor is not for this type.");
/*   141:      */       }
/*   142:   79 */       return VALUES[desc.getIndex()];
/*   143:      */     }
/*   144:      */     
/*   145:      */     private TaskTypeProto(int index, int value)
/*   146:      */     {
/*   147:   86 */       this.index = index;
/*   148:   87 */       this.value = value;
/*   149:      */     }
/*   150:      */   }
/*   151:      */   
/*   152:      */   public static enum TaskStateProto
/*   153:      */     implements ProtocolMessageEnum
/*   154:      */   {
/*   155:  101 */     TS_NEW(0, 1),  TS_SCHEDULED(1, 2),  TS_RUNNING(2, 3),  TS_SUCCEEDED(3, 4),  TS_FAILED(4, 5),  TS_KILLED(5, 6);
/*   156:      */     
/*   157:      */     public static final int TS_NEW_VALUE = 1;
/*   158:      */     public static final int TS_SCHEDULED_VALUE = 2;
/*   159:      */     public static final int TS_RUNNING_VALUE = 3;
/*   160:      */     public static final int TS_SUCCEEDED_VALUE = 4;
/*   161:      */     public static final int TS_FAILED_VALUE = 5;
/*   162:      */     public static final int TS_KILLED_VALUE = 6;
/*   163:      */     
/*   164:      */     public final int getNumber()
/*   165:      */     {
/*   166:  150 */       return this.value;
/*   167:      */     }
/*   168:      */     
/*   169:      */     public static TaskStateProto valueOf(int value)
/*   170:      */     {
/*   171:  153 */       switch (value)
/*   172:      */       {
/*   173:      */       case 1: 
/*   174:  154 */         return TS_NEW;
/*   175:      */       case 2: 
/*   176:  155 */         return TS_SCHEDULED;
/*   177:      */       case 3: 
/*   178:  156 */         return TS_RUNNING;
/*   179:      */       case 4: 
/*   180:  157 */         return TS_SUCCEEDED;
/*   181:      */       case 5: 
/*   182:  158 */         return TS_FAILED;
/*   183:      */       case 6: 
/*   184:  159 */         return TS_KILLED;
/*   185:      */       }
/*   186:  160 */       return null;
/*   187:      */     }
/*   188:      */     
/*   189:      */     public static Internal.EnumLiteMap<TaskStateProto> internalGetValueMap()
/*   190:      */     {
/*   191:  166 */       return internalValueMap;
/*   192:      */     }
/*   193:      */     
/*   194:  169 */     private static Internal.EnumLiteMap<TaskStateProto> internalValueMap = new Internal.EnumLiteMap()
/*   195:      */     {
/*   196:      */       public MRProtos.TaskStateProto findValueByNumber(int number)
/*   197:      */       {
/*   198:  172 */         return MRProtos.TaskStateProto.valueOf(number);
/*   199:      */       }
/*   200:      */     };
/*   201:      */     
/*   202:      */     public final Descriptors.EnumValueDescriptor getValueDescriptor()
/*   203:      */     {
/*   204:  178 */       return (Descriptors.EnumValueDescriptor)getDescriptor().getValues().get(this.index);
/*   205:      */     }
/*   206:      */     
/*   207:      */     public final Descriptors.EnumDescriptor getDescriptorForType()
/*   208:      */     {
/*   209:  182 */       return getDescriptor();
/*   210:      */     }
/*   211:      */     
/*   212:      */     public static final Descriptors.EnumDescriptor getDescriptor()
/*   213:      */     {
/*   214:  186 */       return (Descriptors.EnumDescriptor)MRProtos.getDescriptor().getEnumTypes().get(1);
/*   215:      */     }
/*   216:      */     
/*   217:  189 */     private static final TaskStateProto[] VALUES = values();
/*   218:      */     private final int index;
/*   219:      */     private final int value;
/*   220:      */     
/*   221:      */     public static TaskStateProto valueOf(Descriptors.EnumValueDescriptor desc)
/*   222:      */     {
/*   223:  193 */       if (desc.getType() != getDescriptor()) {
/*   224:  194 */         throw new IllegalArgumentException("EnumValueDescriptor is not for this type.");
/*   225:      */       }
/*   226:  197 */       return VALUES[desc.getIndex()];
/*   227:      */     }
/*   228:      */     
/*   229:      */     private TaskStateProto(int index, int value)
/*   230:      */     {
/*   231:  204 */       this.index = index;
/*   232:  205 */       this.value = value;
/*   233:      */     }
/*   234:      */   }
/*   235:      */   
/*   236:      */   public static enum PhaseProto
/*   237:      */     implements ProtocolMessageEnum
/*   238:      */   {
/*   239:  219 */     P_STARTING(0, 1),  P_MAP(1, 2),  P_SHUFFLE(2, 3),  P_SORT(3, 4),  P_REDUCE(4, 5),  P_CLEANUP(5, 6);
/*   240:      */     
/*   241:      */     public static final int P_STARTING_VALUE = 1;
/*   242:      */     public static final int P_MAP_VALUE = 2;
/*   243:      */     public static final int P_SHUFFLE_VALUE = 3;
/*   244:      */     public static final int P_SORT_VALUE = 4;
/*   245:      */     public static final int P_REDUCE_VALUE = 5;
/*   246:      */     public static final int P_CLEANUP_VALUE = 6;
/*   247:      */     
/*   248:      */     public final int getNumber()
/*   249:      */     {
/*   250:  268 */       return this.value;
/*   251:      */     }
/*   252:      */     
/*   253:      */     public static PhaseProto valueOf(int value)
/*   254:      */     {
/*   255:  271 */       switch (value)
/*   256:      */       {
/*   257:      */       case 1: 
/*   258:  272 */         return P_STARTING;
/*   259:      */       case 2: 
/*   260:  273 */         return P_MAP;
/*   261:      */       case 3: 
/*   262:  274 */         return P_SHUFFLE;
/*   263:      */       case 4: 
/*   264:  275 */         return P_SORT;
/*   265:      */       case 5: 
/*   266:  276 */         return P_REDUCE;
/*   267:      */       case 6: 
/*   268:  277 */         return P_CLEANUP;
/*   269:      */       }
/*   270:  278 */       return null;
/*   271:      */     }
/*   272:      */     
/*   273:      */     public static Internal.EnumLiteMap<PhaseProto> internalGetValueMap()
/*   274:      */     {
/*   275:  284 */       return internalValueMap;
/*   276:      */     }
/*   277:      */     
/*   278:  287 */     private static Internal.EnumLiteMap<PhaseProto> internalValueMap = new Internal.EnumLiteMap()
/*   279:      */     {
/*   280:      */       public MRProtos.PhaseProto findValueByNumber(int number)
/*   281:      */       {
/*   282:  290 */         return MRProtos.PhaseProto.valueOf(number);
/*   283:      */       }
/*   284:      */     };
/*   285:      */     
/*   286:      */     public final Descriptors.EnumValueDescriptor getValueDescriptor()
/*   287:      */     {
/*   288:  296 */       return (Descriptors.EnumValueDescriptor)getDescriptor().getValues().get(this.index);
/*   289:      */     }
/*   290:      */     
/*   291:      */     public final Descriptors.EnumDescriptor getDescriptorForType()
/*   292:      */     {
/*   293:  300 */       return getDescriptor();
/*   294:      */     }
/*   295:      */     
/*   296:      */     public static final Descriptors.EnumDescriptor getDescriptor()
/*   297:      */     {
/*   298:  304 */       return (Descriptors.EnumDescriptor)MRProtos.getDescriptor().getEnumTypes().get(2);
/*   299:      */     }
/*   300:      */     
/*   301:  307 */     private static final PhaseProto[] VALUES = values();
/*   302:      */     private final int index;
/*   303:      */     private final int value;
/*   304:      */     
/*   305:      */     public static PhaseProto valueOf(Descriptors.EnumValueDescriptor desc)
/*   306:      */     {
/*   307:  311 */       if (desc.getType() != getDescriptor()) {
/*   308:  312 */         throw new IllegalArgumentException("EnumValueDescriptor is not for this type.");
/*   309:      */       }
/*   310:  315 */       return VALUES[desc.getIndex()];
/*   311:      */     }
/*   312:      */     
/*   313:      */     private PhaseProto(int index, int value)
/*   314:      */     {
/*   315:  322 */       this.index = index;
/*   316:  323 */       this.value = value;
/*   317:      */     }
/*   318:      */   }
/*   319:      */   
/*   320:      */   public static enum TaskAttemptStateProto
/*   321:      */     implements ProtocolMessageEnum
/*   322:      */   {
/*   323:  337 */     TA_NEW(0, 1),  TA_STARTING(1, 2),  TA_RUNNING(2, 3),  TA_COMMIT_PENDING(3, 4),  TA_SUCCEEDED(4, 5),  TA_FAILED(5, 6),  TA_KILLED(6, 7);
/*   324:      */     
/*   325:      */     public static final int TA_NEW_VALUE = 1;
/*   326:      */     public static final int TA_STARTING_VALUE = 2;
/*   327:      */     public static final int TA_RUNNING_VALUE = 3;
/*   328:      */     public static final int TA_COMMIT_PENDING_VALUE = 4;
/*   329:      */     public static final int TA_SUCCEEDED_VALUE = 5;
/*   330:      */     public static final int TA_FAILED_VALUE = 6;
/*   331:      */     public static final int TA_KILLED_VALUE = 7;
/*   332:      */     
/*   333:      */     public final int getNumber()
/*   334:      */     {
/*   335:  394 */       return this.value;
/*   336:      */     }
/*   337:      */     
/*   338:      */     public static TaskAttemptStateProto valueOf(int value)
/*   339:      */     {
/*   340:  397 */       switch (value)
/*   341:      */       {
/*   342:      */       case 1: 
/*   343:  398 */         return TA_NEW;
/*   344:      */       case 2: 
/*   345:  399 */         return TA_STARTING;
/*   346:      */       case 3: 
/*   347:  400 */         return TA_RUNNING;
/*   348:      */       case 4: 
/*   349:  401 */         return TA_COMMIT_PENDING;
/*   350:      */       case 5: 
/*   351:  402 */         return TA_SUCCEEDED;
/*   352:      */       case 6: 
/*   353:  403 */         return TA_FAILED;
/*   354:      */       case 7: 
/*   355:  404 */         return TA_KILLED;
/*   356:      */       }
/*   357:  405 */       return null;
/*   358:      */     }
/*   359:      */     
/*   360:      */     public static Internal.EnumLiteMap<TaskAttemptStateProto> internalGetValueMap()
/*   361:      */     {
/*   362:  411 */       return internalValueMap;
/*   363:      */     }
/*   364:      */     
/*   365:  414 */     private static Internal.EnumLiteMap<TaskAttemptStateProto> internalValueMap = new Internal.EnumLiteMap()
/*   366:      */     {
/*   367:      */       public MRProtos.TaskAttemptStateProto findValueByNumber(int number)
/*   368:      */       {
/*   369:  417 */         return MRProtos.TaskAttemptStateProto.valueOf(number);
/*   370:      */       }
/*   371:      */     };
/*   372:      */     
/*   373:      */     public final Descriptors.EnumValueDescriptor getValueDescriptor()
/*   374:      */     {
/*   375:  423 */       return (Descriptors.EnumValueDescriptor)getDescriptor().getValues().get(this.index);
/*   376:      */     }
/*   377:      */     
/*   378:      */     public final Descriptors.EnumDescriptor getDescriptorForType()
/*   379:      */     {
/*   380:  427 */       return getDescriptor();
/*   381:      */     }
/*   382:      */     
/*   383:      */     public static final Descriptors.EnumDescriptor getDescriptor()
/*   384:      */     {
/*   385:  431 */       return (Descriptors.EnumDescriptor)MRProtos.getDescriptor().getEnumTypes().get(3);
/*   386:      */     }
/*   387:      */     
/*   388:  434 */     private static final TaskAttemptStateProto[] VALUES = values();
/*   389:      */     private final int index;
/*   390:      */     private final int value;
/*   391:      */     
/*   392:      */     public static TaskAttemptStateProto valueOf(Descriptors.EnumValueDescriptor desc)
/*   393:      */     {
/*   394:  438 */       if (desc.getType() != getDescriptor()) {
/*   395:  439 */         throw new IllegalArgumentException("EnumValueDescriptor is not for this type.");
/*   396:      */       }
/*   397:  442 */       return VALUES[desc.getIndex()];
/*   398:      */     }
/*   399:      */     
/*   400:      */     private TaskAttemptStateProto(int index, int value)
/*   401:      */     {
/*   402:  449 */       this.index = index;
/*   403:  450 */       this.value = value;
/*   404:      */     }
/*   405:      */   }
/*   406:      */   
/*   407:      */   public static enum JobStateProto
/*   408:      */     implements ProtocolMessageEnum
/*   409:      */   {
/*   410:  464 */     J_NEW(0, 1),  J_INITED(1, 2),  J_RUNNING(2, 3),  J_SUCCEEDED(3, 4),  J_FAILED(4, 5),  J_KILLED(5, 6),  J_ERROR(6, 7);
/*   411:      */     
/*   412:      */     public static final int J_NEW_VALUE = 1;
/*   413:      */     public static final int J_INITED_VALUE = 2;
/*   414:      */     public static final int J_RUNNING_VALUE = 3;
/*   415:      */     public static final int J_SUCCEEDED_VALUE = 4;
/*   416:      */     public static final int J_FAILED_VALUE = 5;
/*   417:      */     public static final int J_KILLED_VALUE = 6;
/*   418:      */     public static final int J_ERROR_VALUE = 7;
/*   419:      */     
/*   420:      */     public final int getNumber()
/*   421:      */     {
/*   422:  521 */       return this.value;
/*   423:      */     }
/*   424:      */     
/*   425:      */     public static JobStateProto valueOf(int value)
/*   426:      */     {
/*   427:  524 */       switch (value)
/*   428:      */       {
/*   429:      */       case 1: 
/*   430:  525 */         return J_NEW;
/*   431:      */       case 2: 
/*   432:  526 */         return J_INITED;
/*   433:      */       case 3: 
/*   434:  527 */         return J_RUNNING;
/*   435:      */       case 4: 
/*   436:  528 */         return J_SUCCEEDED;
/*   437:      */       case 5: 
/*   438:  529 */         return J_FAILED;
/*   439:      */       case 6: 
/*   440:  530 */         return J_KILLED;
/*   441:      */       case 7: 
/*   442:  531 */         return J_ERROR;
/*   443:      */       }
/*   444:  532 */       return null;
/*   445:      */     }
/*   446:      */     
/*   447:      */     public static Internal.EnumLiteMap<JobStateProto> internalGetValueMap()
/*   448:      */     {
/*   449:  538 */       return internalValueMap;
/*   450:      */     }
/*   451:      */     
/*   452:  541 */     private static Internal.EnumLiteMap<JobStateProto> internalValueMap = new Internal.EnumLiteMap()
/*   453:      */     {
/*   454:      */       public MRProtos.JobStateProto findValueByNumber(int number)
/*   455:      */       {
/*   456:  544 */         return MRProtos.JobStateProto.valueOf(number);
/*   457:      */       }
/*   458:      */     };
/*   459:      */     
/*   460:      */     public final Descriptors.EnumValueDescriptor getValueDescriptor()
/*   461:      */     {
/*   462:  550 */       return (Descriptors.EnumValueDescriptor)getDescriptor().getValues().get(this.index);
/*   463:      */     }
/*   464:      */     
/*   465:      */     public final Descriptors.EnumDescriptor getDescriptorForType()
/*   466:      */     {
/*   467:  554 */       return getDescriptor();
/*   468:      */     }
/*   469:      */     
/*   470:      */     public static final Descriptors.EnumDescriptor getDescriptor()
/*   471:      */     {
/*   472:  558 */       return (Descriptors.EnumDescriptor)MRProtos.getDescriptor().getEnumTypes().get(4);
/*   473:      */     }
/*   474:      */     
/*   475:  561 */     private static final JobStateProto[] VALUES = values();
/*   476:      */     private final int index;
/*   477:      */     private final int value;
/*   478:      */     
/*   479:      */     public static JobStateProto valueOf(Descriptors.EnumValueDescriptor desc)
/*   480:      */     {
/*   481:  565 */       if (desc.getType() != getDescriptor()) {
/*   482:  566 */         throw new IllegalArgumentException("EnumValueDescriptor is not for this type.");
/*   483:      */       }
/*   484:  569 */       return VALUES[desc.getIndex()];
/*   485:      */     }
/*   486:      */     
/*   487:      */     private JobStateProto(int index, int value)
/*   488:      */     {
/*   489:  576 */       this.index = index;
/*   490:  577 */       this.value = value;
/*   491:      */     }
/*   492:      */   }
/*   493:      */   
/*   494:      */   public static enum TaskAttemptCompletionEventStatusProto
/*   495:      */     implements ProtocolMessageEnum
/*   496:      */   {
/*   497:  591 */     TACE_FAILED(0, 1),  TACE_KILLED(1, 2),  TACE_SUCCEEDED(2, 3),  TACE_OBSOLETE(3, 4),  TACE_TIPFAILED(4, 5);
/*   498:      */     
/*   499:      */     public static final int TACE_FAILED_VALUE = 1;
/*   500:      */     public static final int TACE_KILLED_VALUE = 2;
/*   501:      */     public static final int TACE_SUCCEEDED_VALUE = 3;
/*   502:      */     public static final int TACE_OBSOLETE_VALUE = 4;
/*   503:      */     public static final int TACE_TIPFAILED_VALUE = 5;
/*   504:      */     
/*   505:      */     public final int getNumber()
/*   506:      */     {
/*   507:  632 */       return this.value;
/*   508:      */     }
/*   509:      */     
/*   510:      */     public static TaskAttemptCompletionEventStatusProto valueOf(int value)
/*   511:      */     {
/*   512:  635 */       switch (value)
/*   513:      */       {
/*   514:      */       case 1: 
/*   515:  636 */         return TACE_FAILED;
/*   516:      */       case 2: 
/*   517:  637 */         return TACE_KILLED;
/*   518:      */       case 3: 
/*   519:  638 */         return TACE_SUCCEEDED;
/*   520:      */       case 4: 
/*   521:  639 */         return TACE_OBSOLETE;
/*   522:      */       case 5: 
/*   523:  640 */         return TACE_TIPFAILED;
/*   524:      */       }
/*   525:  641 */       return null;
/*   526:      */     }
/*   527:      */     
/*   528:      */     public static Internal.EnumLiteMap<TaskAttemptCompletionEventStatusProto> internalGetValueMap()
/*   529:      */     {
/*   530:  647 */       return internalValueMap;
/*   531:      */     }
/*   532:      */     
/*   533:  650 */     private static Internal.EnumLiteMap<TaskAttemptCompletionEventStatusProto> internalValueMap = new Internal.EnumLiteMap()
/*   534:      */     {
/*   535:      */       public MRProtos.TaskAttemptCompletionEventStatusProto findValueByNumber(int number)
/*   536:      */       {
/*   537:  653 */         return MRProtos.TaskAttemptCompletionEventStatusProto.valueOf(number);
/*   538:      */       }
/*   539:      */     };
/*   540:      */     
/*   541:      */     public final Descriptors.EnumValueDescriptor getValueDescriptor()
/*   542:      */     {
/*   543:  659 */       return (Descriptors.EnumValueDescriptor)getDescriptor().getValues().get(this.index);
/*   544:      */     }
/*   545:      */     
/*   546:      */     public final Descriptors.EnumDescriptor getDescriptorForType()
/*   547:      */     {
/*   548:  663 */       return getDescriptor();
/*   549:      */     }
/*   550:      */     
/*   551:      */     public static final Descriptors.EnumDescriptor getDescriptor()
/*   552:      */     {
/*   553:  667 */       return (Descriptors.EnumDescriptor)MRProtos.getDescriptor().getEnumTypes().get(5);
/*   554:      */     }
/*   555:      */     
/*   556:  670 */     private static final TaskAttemptCompletionEventStatusProto[] VALUES = values();
/*   557:      */     private final int index;
/*   558:      */     private final int value;
/*   559:      */     
/*   560:      */     public static TaskAttemptCompletionEventStatusProto valueOf(Descriptors.EnumValueDescriptor desc)
/*   561:      */     {
/*   562:  674 */       if (desc.getType() != getDescriptor()) {
/*   563:  675 */         throw new IllegalArgumentException("EnumValueDescriptor is not for this type.");
/*   564:      */       }
/*   565:  678 */       return VALUES[desc.getIndex()];
/*   566:      */     }
/*   567:      */     
/*   568:      */     private TaskAttemptCompletionEventStatusProto(int index, int value)
/*   569:      */     {
/*   570:  685 */       this.index = index;
/*   571:  686 */       this.value = value;
/*   572:      */     }
/*   573:      */   }
/*   574:      */   
/*   575:      */   public static abstract interface JobIdProtoOrBuilder
/*   576:      */     extends MessageOrBuilder
/*   577:      */   {
/*   578:      */     public abstract boolean hasAppId();
/*   579:      */     
/*   580:      */     public abstract YarnProtos.ApplicationIdProto getAppId();
/*   581:      */     
/*   582:      */     public abstract YarnProtos.ApplicationIdProtoOrBuilder getAppIdOrBuilder();
/*   583:      */     
/*   584:      */     public abstract boolean hasId();
/*   585:      */     
/*   586:      */     public abstract int getId();
/*   587:      */   }
/*   588:      */   
/*   589:      */   public static final class JobIdProto
/*   590:      */     extends GeneratedMessage
/*   591:      */     implements MRProtos.JobIdProtoOrBuilder
/*   592:      */   {
/*   593:      */     private static final JobIdProto defaultInstance;
/*   594:      */     private final UnknownFieldSet unknownFields;
/*   595:      */     
/*   596:      */     private JobIdProto(GeneratedMessage.Builder<?> builder)
/*   597:      */     {
/*   598:  727 */       super();
/*   599:  728 */       this.unknownFields = builder.getUnknownFields();
/*   600:      */     }
/*   601:      */     
/*   602:      */     private JobIdProto(boolean noInit)
/*   603:      */     {
/*   604:  730 */       this.unknownFields = UnknownFieldSet.getDefaultInstance();
/*   605:      */     }
/*   606:      */     
/*   607:      */     public static JobIdProto getDefaultInstance()
/*   608:      */     {
/*   609:  734 */       return defaultInstance;
/*   610:      */     }
/*   611:      */     
/*   612:      */     public JobIdProto getDefaultInstanceForType()
/*   613:      */     {
/*   614:  738 */       return defaultInstance;
/*   615:      */     }
/*   616:      */     
/*   617:      */     public final UnknownFieldSet getUnknownFields()
/*   618:      */     {
/*   619:  745 */       return this.unknownFields;
/*   620:      */     }
/*   621:      */     
/*   622:      */     private JobIdProto(CodedInputStream input, ExtensionRegistryLite extensionRegistry)
/*   623:      */       throws InvalidProtocolBufferException
/*   624:      */     {
/*   625:  751 */       initFields();
/*   626:  752 */       int mutable_bitField0_ = 0;
/*   627:  753 */       UnknownFieldSet.Builder unknownFields = UnknownFieldSet.newBuilder();
/*   628:      */       try
/*   629:      */       {
/*   630:  756 */         boolean done = false;
/*   631:  757 */         while (!done)
/*   632:      */         {
/*   633:  758 */           int tag = input.readTag();
/*   634:  759 */           switch (tag)
/*   635:      */           {
/*   636:      */           case 0: 
/*   637:  761 */             done = true;
/*   638:  762 */             break;
/*   639:      */           default: 
/*   640:  764 */             if (!parseUnknownField(input, unknownFields, extensionRegistry, tag)) {
/*   641:  766 */               done = true;
/*   642:      */             }
/*   643:      */             break;
/*   644:      */           case 10: 
/*   645:  771 */             YarnProtos.ApplicationIdProto.Builder subBuilder = null;
/*   646:  772 */             if ((this.bitField0_ & 0x1) == 1) {
/*   647:  773 */               subBuilder = this.appId_.toBuilder();
/*   648:      */             }
/*   649:  775 */             this.appId_ = ((YarnProtos.ApplicationIdProto)input.readMessage(YarnProtos.ApplicationIdProto.PARSER, extensionRegistry));
/*   650:  776 */             if (subBuilder != null)
/*   651:      */             {
/*   652:  777 */               subBuilder.mergeFrom(this.appId_);
/*   653:  778 */               this.appId_ = subBuilder.buildPartial();
/*   654:      */             }
/*   655:  780 */             this.bitField0_ |= 0x1;
/*   656:  781 */             break;
/*   657:      */           case 16: 
/*   658:  784 */             this.bitField0_ |= 0x2;
/*   659:  785 */             this.id_ = input.readInt32();
/*   660:      */           }
/*   661:      */         }
/*   662:      */       }
/*   663:      */       catch (InvalidProtocolBufferException e)
/*   664:      */       {
/*   665:  791 */         throw e.setUnfinishedMessage(this);
/*   666:      */       }
/*   667:      */       catch (IOException e)
/*   668:      */       {
/*   669:  793 */         throw new InvalidProtocolBufferException(e.getMessage()).setUnfinishedMessage(this);
/*   670:      */       }
/*   671:      */       finally
/*   672:      */       {
/*   673:  796 */         this.unknownFields = unknownFields.build();
/*   674:  797 */         makeExtensionsImmutable();
/*   675:      */       }
/*   676:      */     }
/*   677:      */     
/*   678:      */     public static final Descriptors.Descriptor getDescriptor()
/*   679:      */     {
/*   680:  802 */       return MRProtos.internal_static_hadoop_mapreduce_JobIdProto_descriptor;
/*   681:      */     }
/*   682:      */     
/*   683:      */     protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable()
/*   684:      */     {
/*   685:  807 */       return MRProtos.internal_static_hadoop_mapreduce_JobIdProto_fieldAccessorTable.ensureFieldAccessorsInitialized(JobIdProto.class, Builder.class);
/*   686:      */     }
/*   687:      */     
/*   688:  812 */     public static Parser<JobIdProto> PARSER = new AbstractParser()
/*   689:      */     {
/*   690:      */       public MRProtos.JobIdProto parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry)
/*   691:      */         throws InvalidProtocolBufferException
/*   692:      */       {
/*   693:  818 */         return new MRProtos.JobIdProto(input, extensionRegistry, null);
/*   694:      */       }
/*   695:      */     };
/*   696:      */     private int bitField0_;
/*   697:      */     public static final int APP_ID_FIELD_NUMBER = 1;
/*   698:      */     private YarnProtos.ApplicationIdProto appId_;
/*   699:      */     public static final int ID_FIELD_NUMBER = 2;
/*   700:      */     private int id_;
/*   701:      */     
/*   702:      */     public Parser<JobIdProto> getParserForType()
/*   703:      */     {
/*   704:  824 */       return PARSER;
/*   705:      */     }
/*   706:      */     
/*   707:      */     public boolean hasAppId()
/*   708:      */     {
/*   709:  835 */       return (this.bitField0_ & 0x1) == 1;
/*   710:      */     }
/*   711:      */     
/*   712:      */     public YarnProtos.ApplicationIdProto getAppId()
/*   713:      */     {
/*   714:  841 */       return this.appId_;
/*   715:      */     }
/*   716:      */     
/*   717:      */     public YarnProtos.ApplicationIdProtoOrBuilder getAppIdOrBuilder()
/*   718:      */     {
/*   719:  847 */       return this.appId_;
/*   720:      */     }
/*   721:      */     
/*   722:      */     public boolean hasId()
/*   723:      */     {
/*   724:  857 */       return (this.bitField0_ & 0x2) == 2;
/*   725:      */     }
/*   726:      */     
/*   727:      */     public int getId()
/*   728:      */     {
/*   729:  863 */       return this.id_;
/*   730:      */     }
/*   731:      */     
/*   732:      */     private void initFields()
/*   733:      */     {
/*   734:  867 */       this.appId_ = YarnProtos.ApplicationIdProto.getDefaultInstance();
/*   735:  868 */       this.id_ = 0;
/*   736:      */     }
/*   737:      */     
/*   738:  870 */     private byte memoizedIsInitialized = -1;
/*   739:      */     
/*   740:      */     public final boolean isInitialized()
/*   741:      */     {
/*   742:  872 */       byte isInitialized = this.memoizedIsInitialized;
/*   743:  873 */       if (isInitialized != -1) {
/*   744:  873 */         return isInitialized == 1;
/*   745:      */       }
/*   746:  875 */       this.memoizedIsInitialized = 1;
/*   747:  876 */       return true;
/*   748:      */     }
/*   749:      */     
/*   750:      */     public void writeTo(CodedOutputStream output)
/*   751:      */       throws IOException
/*   752:      */     {
/*   753:  881 */       getSerializedSize();
/*   754:  882 */       if ((this.bitField0_ & 0x1) == 1) {
/*   755:  883 */         output.writeMessage(1, this.appId_);
/*   756:      */       }
/*   757:  885 */       if ((this.bitField0_ & 0x2) == 2) {
/*   758:  886 */         output.writeInt32(2, this.id_);
/*   759:      */       }
/*   760:  888 */       getUnknownFields().writeTo(output);
/*   761:      */     }
/*   762:      */     
/*   763:  891 */     private int memoizedSerializedSize = -1;
/*   764:      */     private static final long serialVersionUID = 0L;
/*   765:      */     
/*   766:      */     public int getSerializedSize()
/*   767:      */     {
/*   768:  893 */       int size = this.memoizedSerializedSize;
/*   769:  894 */       if (size != -1) {
/*   770:  894 */         return size;
/*   771:      */       }
/*   772:  896 */       size = 0;
/*   773:  897 */       if ((this.bitField0_ & 0x1) == 1) {
/*   774:  898 */         size += CodedOutputStream.computeMessageSize(1, this.appId_);
/*   775:      */       }
/*   776:  901 */       if ((this.bitField0_ & 0x2) == 2) {
/*   777:  902 */         size += CodedOutputStream.computeInt32Size(2, this.id_);
/*   778:      */       }
/*   779:  905 */       size += getUnknownFields().getSerializedSize();
/*   780:  906 */       this.memoizedSerializedSize = size;
/*   781:  907 */       return size;
/*   782:      */     }
/*   783:      */     
/*   784:      */     protected Object writeReplace()
/*   785:      */       throws ObjectStreamException
/*   786:      */     {
/*   787:  914 */       return super.writeReplace();
/*   788:      */     }
/*   789:      */     
/*   790:      */     public boolean equals(Object obj)
/*   791:      */     {
/*   792:  919 */       if (obj == this) {
/*   793:  920 */         return true;
/*   794:      */       }
/*   795:  922 */       if (!(obj instanceof JobIdProto)) {
/*   796:  923 */         return super.equals(obj);
/*   797:      */       }
/*   798:  925 */       JobIdProto other = (JobIdProto)obj;
/*   799:      */       
/*   800:  927 */       boolean result = true;
/*   801:  928 */       result = (result) && (hasAppId() == other.hasAppId());
/*   802:  929 */       if (hasAppId()) {
/*   803:  930 */         result = (result) && (getAppId().equals(other.getAppId()));
/*   804:      */       }
/*   805:  933 */       result = (result) && (hasId() == other.hasId());
/*   806:  934 */       if (hasId()) {
/*   807:  935 */         result = (result) && (getId() == other.getId());
/*   808:      */       }
/*   809:  938 */       result = (result) && (getUnknownFields().equals(other.getUnknownFields()));
/*   810:      */       
/*   811:  940 */       return result;
/*   812:      */     }
/*   813:      */     
/*   814:  943 */     private int memoizedHashCode = 0;
/*   815:      */     
/*   816:      */     public int hashCode()
/*   817:      */     {
/*   818:  946 */       if (this.memoizedHashCode != 0) {
/*   819:  947 */         return this.memoizedHashCode;
/*   820:      */       }
/*   821:  949 */       int hash = 41;
/*   822:  950 */       hash = 19 * hash + getDescriptorForType().hashCode();
/*   823:  951 */       if (hasAppId())
/*   824:      */       {
/*   825:  952 */         hash = 37 * hash + 1;
/*   826:  953 */         hash = 53 * hash + getAppId().hashCode();
/*   827:      */       }
/*   828:  955 */       if (hasId())
/*   829:      */       {
/*   830:  956 */         hash = 37 * hash + 2;
/*   831:  957 */         hash = 53 * hash + getId();
/*   832:      */       }
/*   833:  959 */       hash = 29 * hash + getUnknownFields().hashCode();
/*   834:  960 */       this.memoizedHashCode = hash;
/*   835:  961 */       return hash;
/*   836:      */     }
/*   837:      */     
/*   838:      */     public static JobIdProto parseFrom(ByteString data)
/*   839:      */       throws InvalidProtocolBufferException
/*   840:      */     {
/*   841:  967 */       return (JobIdProto)PARSER.parseFrom(data);
/*   842:      */     }
/*   843:      */     
/*   844:      */     public static JobIdProto parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry)
/*   845:      */       throws InvalidProtocolBufferException
/*   846:      */     {
/*   847:  973 */       return (JobIdProto)PARSER.parseFrom(data, extensionRegistry);
/*   848:      */     }
/*   849:      */     
/*   850:      */     public static JobIdProto parseFrom(byte[] data)
/*   851:      */       throws InvalidProtocolBufferException
/*   852:      */     {
/*   853:  977 */       return (JobIdProto)PARSER.parseFrom(data);
/*   854:      */     }
/*   855:      */     
/*   856:      */     public static JobIdProto parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry)
/*   857:      */       throws InvalidProtocolBufferException
/*   858:      */     {
/*   859:  983 */       return (JobIdProto)PARSER.parseFrom(data, extensionRegistry);
/*   860:      */     }
/*   861:      */     
/*   862:      */     public static JobIdProto parseFrom(InputStream input)
/*   863:      */       throws IOException
/*   864:      */     {
/*   865:  987 */       return (JobIdProto)PARSER.parseFrom(input);
/*   866:      */     }
/*   867:      */     
/*   868:      */     public static JobIdProto parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry)
/*   869:      */       throws IOException
/*   870:      */     {
/*   871:  993 */       return (JobIdProto)PARSER.parseFrom(input, extensionRegistry);
/*   872:      */     }
/*   873:      */     
/*   874:      */     public static JobIdProto parseDelimitedFrom(InputStream input)
/*   875:      */       throws IOException
/*   876:      */     {
/*   877:  997 */       return (JobIdProto)PARSER.parseDelimitedFrom(input);
/*   878:      */     }
/*   879:      */     
/*   880:      */     public static JobIdProto parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry)
/*   881:      */       throws IOException
/*   882:      */     {
/*   883: 1003 */       return (JobIdProto)PARSER.parseDelimitedFrom(input, extensionRegistry);
/*   884:      */     }
/*   885:      */     
/*   886:      */     public static JobIdProto parseFrom(CodedInputStream input)
/*   887:      */       throws IOException
/*   888:      */     {
/*   889: 1008 */       return (JobIdProto)PARSER.parseFrom(input);
/*   890:      */     }
/*   891:      */     
/*   892:      */     public static JobIdProto parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry)
/*   893:      */       throws IOException
/*   894:      */     {
/*   895: 1014 */       return (JobIdProto)PARSER.parseFrom(input, extensionRegistry);
/*   896:      */     }
/*   897:      */     
/*   898:      */     public static Builder newBuilder()
/*   899:      */     {
/*   900: 1017 */       return Builder.access$300();
/*   901:      */     }
/*   902:      */     
/*   903:      */     public Builder newBuilderForType()
/*   904:      */     {
/*   905: 1018 */       return newBuilder();
/*   906:      */     }
/*   907:      */     
/*   908:      */     public static Builder newBuilder(JobIdProto prototype)
/*   909:      */     {
/*   910: 1020 */       return newBuilder().mergeFrom(prototype);
/*   911:      */     }
/*   912:      */     
/*   913:      */     public Builder toBuilder()
/*   914:      */     {
/*   915: 1022 */       return newBuilder(this);
/*   916:      */     }
/*   917:      */     
/*   918:      */     protected Builder newBuilderForType(GeneratedMessage.BuilderParent parent)
/*   919:      */     {
/*   920: 1027 */       Builder builder = new Builder(parent, null);
/*   921: 1028 */       return builder;
/*   922:      */     }
/*   923:      */     
/*   924:      */     public static final class Builder
/*   925:      */       extends GeneratedMessage.Builder<Builder>
/*   926:      */       implements MRProtos.JobIdProtoOrBuilder
/*   927:      */     {
/*   928:      */       private int bitField0_;
/*   929:      */       
/*   930:      */       public static final Descriptors.Descriptor getDescriptor()
/*   931:      */       {
/*   932: 1038 */         return MRProtos.internal_static_hadoop_mapreduce_JobIdProto_descriptor;
/*   933:      */       }
/*   934:      */       
/*   935:      */       protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable()
/*   936:      */       {
/*   937: 1043 */         return MRProtos.internal_static_hadoop_mapreduce_JobIdProto_fieldAccessorTable.ensureFieldAccessorsInitialized(MRProtos.JobIdProto.class, Builder.class);
/*   938:      */       }
/*   939:      */       
/*   940:      */       private Builder()
/*   941:      */       {
/*   942: 1050 */         maybeForceBuilderInitialization();
/*   943:      */       }
/*   944:      */       
/*   945:      */       private Builder(GeneratedMessage.BuilderParent parent)
/*   946:      */       {
/*   947: 1055 */         super();
/*   948: 1056 */         maybeForceBuilderInitialization();
/*   949:      */       }
/*   950:      */       
/*   951:      */       private void maybeForceBuilderInitialization()
/*   952:      */       {
/*   953: 1059 */         if (MRProtos.JobIdProto.alwaysUseFieldBuilders) {
/*   954: 1060 */           getAppIdFieldBuilder();
/*   955:      */         }
/*   956:      */       }
/*   957:      */       
/*   958:      */       private static Builder create()
/*   959:      */       {
/*   960: 1064 */         return new Builder();
/*   961:      */       }
/*   962:      */       
/*   963:      */       public Builder clear()
/*   964:      */       {
/*   965: 1068 */         super.clear();
/*   966: 1069 */         if (this.appIdBuilder_ == null) {
/*   967: 1070 */           this.appId_ = YarnProtos.ApplicationIdProto.getDefaultInstance();
/*   968:      */         } else {
/*   969: 1072 */           this.appIdBuilder_.clear();
/*   970:      */         }
/*   971: 1074 */         this.bitField0_ &= 0xFFFFFFFE;
/*   972: 1075 */         this.id_ = 0;
/*   973: 1076 */         this.bitField0_ &= 0xFFFFFFFD;
/*   974: 1077 */         return this;
/*   975:      */       }
/*   976:      */       
/*   977:      */       public Builder clone()
/*   978:      */       {
/*   979: 1081 */         return create().mergeFrom(buildPartial());
/*   980:      */       }
/*   981:      */       
/*   982:      */       public Descriptors.Descriptor getDescriptorForType()
/*   983:      */       {
/*   984: 1086 */         return MRProtos.internal_static_hadoop_mapreduce_JobIdProto_descriptor;
/*   985:      */       }
/*   986:      */       
/*   987:      */       public MRProtos.JobIdProto getDefaultInstanceForType()
/*   988:      */       {
/*   989: 1090 */         return MRProtos.JobIdProto.getDefaultInstance();
/*   990:      */       }
/*   991:      */       
/*   992:      */       public MRProtos.JobIdProto build()
/*   993:      */       {
/*   994: 1094 */         MRProtos.JobIdProto result = buildPartial();
/*   995: 1095 */         if (!result.isInitialized()) {
/*   996: 1096 */           throw newUninitializedMessageException(result);
/*   997:      */         }
/*   998: 1098 */         return result;
/*   999:      */       }
/*  1000:      */       
/*  1001:      */       public MRProtos.JobIdProto buildPartial()
/*  1002:      */       {
/*  1003: 1102 */         MRProtos.JobIdProto result = new MRProtos.JobIdProto(this, null);
/*  1004: 1103 */         int from_bitField0_ = this.bitField0_;
/*  1005: 1104 */         int to_bitField0_ = 0;
/*  1006: 1105 */         if ((from_bitField0_ & 0x1) == 1) {
/*  1007: 1106 */           to_bitField0_ |= 0x1;
/*  1008:      */         }
/*  1009: 1108 */         if (this.appIdBuilder_ == null) {
/*  1010: 1109 */           result.appId_ = this.appId_;
/*  1011:      */         } else {
/*  1012: 1111 */           result.appId_ = ((YarnProtos.ApplicationIdProto)this.appIdBuilder_.build());
/*  1013:      */         }
/*  1014: 1113 */         if ((from_bitField0_ & 0x2) == 2) {
/*  1015: 1114 */           to_bitField0_ |= 0x2;
/*  1016:      */         }
/*  1017: 1116 */         result.id_ = this.id_;
/*  1018: 1117 */         result.bitField0_ = to_bitField0_;
/*  1019: 1118 */         onBuilt();
/*  1020: 1119 */         return result;
/*  1021:      */       }
/*  1022:      */       
/*  1023:      */       public Builder mergeFrom(Message other)
/*  1024:      */       {
/*  1025: 1123 */         if ((other instanceof MRProtos.JobIdProto)) {
/*  1026: 1124 */           return mergeFrom((MRProtos.JobIdProto)other);
/*  1027:      */         }
/*  1028: 1126 */         super.mergeFrom(other);
/*  1029: 1127 */         return this;
/*  1030:      */       }
/*  1031:      */       
/*  1032:      */       public Builder mergeFrom(MRProtos.JobIdProto other)
/*  1033:      */       {
/*  1034: 1132 */         if (other == MRProtos.JobIdProto.getDefaultInstance()) {
/*  1035: 1132 */           return this;
/*  1036:      */         }
/*  1037: 1133 */         if (other.hasAppId()) {
/*  1038: 1134 */           mergeAppId(other.getAppId());
/*  1039:      */         }
/*  1040: 1136 */         if (other.hasId()) {
/*  1041: 1137 */           setId(other.getId());
/*  1042:      */         }
/*  1043: 1139 */         mergeUnknownFields(other.getUnknownFields());
/*  1044: 1140 */         return this;
/*  1045:      */       }
/*  1046:      */       
/*  1047:      */       public final boolean isInitialized()
/*  1048:      */       {
/*  1049: 1144 */         return true;
/*  1050:      */       }
/*  1051:      */       
/*  1052:      */       public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry)
/*  1053:      */         throws IOException
/*  1054:      */       {
/*  1055: 1151 */         MRProtos.JobIdProto parsedMessage = null;
/*  1056:      */         try
/*  1057:      */         {
/*  1058: 1153 */           parsedMessage = (MRProtos.JobIdProto)MRProtos.JobIdProto.PARSER.parsePartialFrom(input, extensionRegistry);
/*  1059:      */         }
/*  1060:      */         catch (InvalidProtocolBufferException e)
/*  1061:      */         {
/*  1062: 1155 */           parsedMessage = (MRProtos.JobIdProto)e.getUnfinishedMessage();
/*  1063: 1156 */           throw e;
/*  1064:      */         }
/*  1065:      */         finally
/*  1066:      */         {
/*  1067: 1158 */           if (parsedMessage != null) {
/*  1068: 1159 */             mergeFrom(parsedMessage);
/*  1069:      */           }
/*  1070:      */         }
/*  1071: 1162 */         return this;
/*  1072:      */       }
/*  1073:      */       
/*  1074: 1167 */       private YarnProtos.ApplicationIdProto appId_ = YarnProtos.ApplicationIdProto.getDefaultInstance();
/*  1075:      */       private SingleFieldBuilder<YarnProtos.ApplicationIdProto, YarnProtos.ApplicationIdProto.Builder, YarnProtos.ApplicationIdProtoOrBuilder> appIdBuilder_;
/*  1076:      */       private int id_;
/*  1077:      */       
/*  1078:      */       public boolean hasAppId()
/*  1079:      */       {
/*  1080: 1174 */         return (this.bitField0_ & 0x1) == 1;
/*  1081:      */       }
/*  1082:      */       
/*  1083:      */       public YarnProtos.ApplicationIdProto getAppId()
/*  1084:      */       {
/*  1085: 1180 */         if (this.appIdBuilder_ == null) {
/*  1086: 1181 */           return this.appId_;
/*  1087:      */         }
/*  1088: 1183 */         return (YarnProtos.ApplicationIdProto)this.appIdBuilder_.getMessage();
/*  1089:      */       }
/*  1090:      */       
/*  1091:      */       public Builder setAppId(YarnProtos.ApplicationIdProto value)
/*  1092:      */       {
/*  1093: 1190 */         if (this.appIdBuilder_ == null)
/*  1094:      */         {
/*  1095: 1191 */           if (value == null) {
/*  1096: 1192 */             throw new NullPointerException();
/*  1097:      */           }
/*  1098: 1194 */           this.appId_ = value;
/*  1099: 1195 */           onChanged();
/*  1100:      */         }
/*  1101:      */         else
/*  1102:      */         {
/*  1103: 1197 */           this.appIdBuilder_.setMessage(value);
/*  1104:      */         }
/*  1105: 1199 */         this.bitField0_ |= 0x1;
/*  1106: 1200 */         return this;
/*  1107:      */       }
/*  1108:      */       
/*  1109:      */       public Builder setAppId(YarnProtos.ApplicationIdProto.Builder builderForValue)
/*  1110:      */       {
/*  1111: 1207 */         if (this.appIdBuilder_ == null)
/*  1112:      */         {
/*  1113: 1208 */           this.appId_ = builderForValue.build();
/*  1114: 1209 */           onChanged();
/*  1115:      */         }
/*  1116:      */         else
/*  1117:      */         {
/*  1118: 1211 */           this.appIdBuilder_.setMessage(builderForValue.build());
/*  1119:      */         }
/*  1120: 1213 */         this.bitField0_ |= 0x1;
/*  1121: 1214 */         return this;
/*  1122:      */       }
/*  1123:      */       
/*  1124:      */       public Builder mergeAppId(YarnProtos.ApplicationIdProto value)
/*  1125:      */       {
/*  1126: 1220 */         if (this.appIdBuilder_ == null)
/*  1127:      */         {
/*  1128: 1221 */           if (((this.bitField0_ & 0x1) == 1) && (this.appId_ != YarnProtos.ApplicationIdProto.getDefaultInstance())) {
/*  1129: 1223 */             this.appId_ = YarnProtos.ApplicationIdProto.newBuilder(this.appId_).mergeFrom(value).buildPartial();
/*  1130:      */           } else {
/*  1131: 1226 */             this.appId_ = value;
/*  1132:      */           }
/*  1133: 1228 */           onChanged();
/*  1134:      */         }
/*  1135:      */         else
/*  1136:      */         {
/*  1137: 1230 */           this.appIdBuilder_.mergeFrom(value);
/*  1138:      */         }
/*  1139: 1232 */         this.bitField0_ |= 0x1;
/*  1140: 1233 */         return this;
/*  1141:      */       }
/*  1142:      */       
/*  1143:      */       public Builder clearAppId()
/*  1144:      */       {
/*  1145: 1239 */         if (this.appIdBuilder_ == null)
/*  1146:      */         {
/*  1147: 1240 */           this.appId_ = YarnProtos.ApplicationIdProto.getDefaultInstance();
/*  1148: 1241 */           onChanged();
/*  1149:      */         }
/*  1150:      */         else
/*  1151:      */         {
/*  1152: 1243 */           this.appIdBuilder_.clear();
/*  1153:      */         }
/*  1154: 1245 */         this.bitField0_ &= 0xFFFFFFFE;
/*  1155: 1246 */         return this;
/*  1156:      */       }
/*  1157:      */       
/*  1158:      */       public YarnProtos.ApplicationIdProto.Builder getAppIdBuilder()
/*  1159:      */       {
/*  1160: 1252 */         this.bitField0_ |= 0x1;
/*  1161: 1253 */         onChanged();
/*  1162: 1254 */         return (YarnProtos.ApplicationIdProto.Builder)getAppIdFieldBuilder().getBuilder();
/*  1163:      */       }
/*  1164:      */       
/*  1165:      */       public YarnProtos.ApplicationIdProtoOrBuilder getAppIdOrBuilder()
/*  1166:      */       {
/*  1167: 1260 */         if (this.appIdBuilder_ != null) {
/*  1168: 1261 */           return (YarnProtos.ApplicationIdProtoOrBuilder)this.appIdBuilder_.getMessageOrBuilder();
/*  1169:      */         }
/*  1170: 1263 */         return this.appId_;
/*  1171:      */       }
/*  1172:      */       
/*  1173:      */       private SingleFieldBuilder<YarnProtos.ApplicationIdProto, YarnProtos.ApplicationIdProto.Builder, YarnProtos.ApplicationIdProtoOrBuilder> getAppIdFieldBuilder()
/*  1174:      */       {
/*  1175: 1272 */         if (this.appIdBuilder_ == null)
/*  1176:      */         {
/*  1177: 1273 */           this.appIdBuilder_ = new SingleFieldBuilder(this.appId_, getParentForChildren(), isClean());
/*  1178:      */           
/*  1179:      */ 
/*  1180:      */ 
/*  1181:      */ 
/*  1182: 1278 */           this.appId_ = null;
/*  1183:      */         }
/*  1184: 1280 */         return this.appIdBuilder_;
/*  1185:      */       }
/*  1186:      */       
/*  1187:      */       public boolean hasId()
/*  1188:      */       {
/*  1189: 1289 */         return (this.bitField0_ & 0x2) == 2;
/*  1190:      */       }
/*  1191:      */       
/*  1192:      */       public int getId()
/*  1193:      */       {
/*  1194: 1295 */         return this.id_;
/*  1195:      */       }
/*  1196:      */       
/*  1197:      */       public Builder setId(int value)
/*  1198:      */       {
/*  1199: 1301 */         this.bitField0_ |= 0x2;
/*  1200: 1302 */         this.id_ = value;
/*  1201: 1303 */         onChanged();
/*  1202: 1304 */         return this;
/*  1203:      */       }
/*  1204:      */       
/*  1205:      */       public Builder clearId()
/*  1206:      */       {
/*  1207: 1310 */         this.bitField0_ &= 0xFFFFFFFD;
/*  1208: 1311 */         this.id_ = 0;
/*  1209: 1312 */         onChanged();
/*  1210: 1313 */         return this;
/*  1211:      */       }
/*  1212:      */     }
/*  1213:      */     
/*  1214:      */     static
/*  1215:      */     {
/*  1216: 1320 */       defaultInstance = new JobIdProto(true);
/*  1217: 1321 */       defaultInstance.initFields();
/*  1218:      */     }
/*  1219:      */   }
/*  1220:      */   
/*  1221:      */   public static abstract interface TaskIdProtoOrBuilder
/*  1222:      */     extends MessageOrBuilder
/*  1223:      */   {
/*  1224:      */     public abstract boolean hasJobId();
/*  1225:      */     
/*  1226:      */     public abstract MRProtos.JobIdProto getJobId();
/*  1227:      */     
/*  1228:      */     public abstract MRProtos.JobIdProtoOrBuilder getJobIdOrBuilder();
/*  1229:      */     
/*  1230:      */     public abstract boolean hasTaskType();
/*  1231:      */     
/*  1232:      */     public abstract MRProtos.TaskTypeProto getTaskType();
/*  1233:      */     
/*  1234:      */     public abstract boolean hasId();
/*  1235:      */     
/*  1236:      */     public abstract int getId();
/*  1237:      */   }
/*  1238:      */   
/*  1239:      */   public static final class TaskIdProto
/*  1240:      */     extends GeneratedMessage
/*  1241:      */     implements MRProtos.TaskIdProtoOrBuilder
/*  1242:      */   {
/*  1243:      */     private static final TaskIdProto defaultInstance;
/*  1244:      */     private final UnknownFieldSet unknownFields;
/*  1245:      */     
/*  1246:      */     private TaskIdProto(GeneratedMessage.Builder<?> builder)
/*  1247:      */     {
/*  1248: 1372 */       super();
/*  1249: 1373 */       this.unknownFields = builder.getUnknownFields();
/*  1250:      */     }
/*  1251:      */     
/*  1252:      */     private TaskIdProto(boolean noInit)
/*  1253:      */     {
/*  1254: 1375 */       this.unknownFields = UnknownFieldSet.getDefaultInstance();
/*  1255:      */     }
/*  1256:      */     
/*  1257:      */     public static TaskIdProto getDefaultInstance()
/*  1258:      */     {
/*  1259: 1379 */       return defaultInstance;
/*  1260:      */     }
/*  1261:      */     
/*  1262:      */     public TaskIdProto getDefaultInstanceForType()
/*  1263:      */     {
/*  1264: 1383 */       return defaultInstance;
/*  1265:      */     }
/*  1266:      */     
/*  1267:      */     public final UnknownFieldSet getUnknownFields()
/*  1268:      */     {
/*  1269: 1390 */       return this.unknownFields;
/*  1270:      */     }
/*  1271:      */     
/*  1272:      */     private TaskIdProto(CodedInputStream input, ExtensionRegistryLite extensionRegistry)
/*  1273:      */       throws InvalidProtocolBufferException
/*  1274:      */     {
/*  1275: 1396 */       initFields();
/*  1276: 1397 */       int mutable_bitField0_ = 0;
/*  1277: 1398 */       UnknownFieldSet.Builder unknownFields = UnknownFieldSet.newBuilder();
/*  1278:      */       try
/*  1279:      */       {
/*  1280: 1401 */         boolean done = false;
/*  1281: 1402 */         while (!done)
/*  1282:      */         {
/*  1283: 1403 */           int tag = input.readTag();
/*  1284: 1404 */           switch (tag)
/*  1285:      */           {
/*  1286:      */           case 0: 
/*  1287: 1406 */             done = true;
/*  1288: 1407 */             break;
/*  1289:      */           default: 
/*  1290: 1409 */             if (!parseUnknownField(input, unknownFields, extensionRegistry, tag)) {
/*  1291: 1411 */               done = true;
/*  1292:      */             }
/*  1293:      */             break;
/*  1294:      */           case 10: 
/*  1295: 1416 */             MRProtos.JobIdProto.Builder subBuilder = null;
/*  1296: 1417 */             if ((this.bitField0_ & 0x1) == 1) {
/*  1297: 1418 */               subBuilder = this.jobId_.toBuilder();
/*  1298:      */             }
/*  1299: 1420 */             this.jobId_ = ((MRProtos.JobIdProto)input.readMessage(MRProtos.JobIdProto.PARSER, extensionRegistry));
/*  1300: 1421 */             if (subBuilder != null)
/*  1301:      */             {
/*  1302: 1422 */               subBuilder.mergeFrom(this.jobId_);
/*  1303: 1423 */               this.jobId_ = subBuilder.buildPartial();
/*  1304:      */             }
/*  1305: 1425 */             this.bitField0_ |= 0x1;
/*  1306: 1426 */             break;
/*  1307:      */           case 16: 
/*  1308: 1429 */             int rawValue = input.readEnum();
/*  1309: 1430 */             MRProtos.TaskTypeProto value = MRProtos.TaskTypeProto.valueOf(rawValue);
/*  1310: 1431 */             if (value == null)
/*  1311:      */             {
/*  1312: 1432 */               unknownFields.mergeVarintField(2, rawValue);
/*  1313:      */             }
/*  1314:      */             else
/*  1315:      */             {
/*  1316: 1434 */               this.bitField0_ |= 0x2;
/*  1317: 1435 */               this.taskType_ = value;
/*  1318:      */             }
/*  1319: 1437 */             break;
/*  1320:      */           case 24: 
/*  1321: 1440 */             this.bitField0_ |= 0x4;
/*  1322: 1441 */             this.id_ = input.readInt32();
/*  1323:      */           }
/*  1324:      */         }
/*  1325:      */       }
/*  1326:      */       catch (InvalidProtocolBufferException e)
/*  1327:      */       {
/*  1328: 1447 */         throw e.setUnfinishedMessage(this);
/*  1329:      */       }
/*  1330:      */       catch (IOException e)
/*  1331:      */       {
/*  1332: 1449 */         throw new InvalidProtocolBufferException(e.getMessage()).setUnfinishedMessage(this);
/*  1333:      */       }
/*  1334:      */       finally
/*  1335:      */       {
/*  1336: 1452 */         this.unknownFields = unknownFields.build();
/*  1337: 1453 */         makeExtensionsImmutable();
/*  1338:      */       }
/*  1339:      */     }
/*  1340:      */     
/*  1341:      */     public static final Descriptors.Descriptor getDescriptor()
/*  1342:      */     {
/*  1343: 1458 */       return MRProtos.internal_static_hadoop_mapreduce_TaskIdProto_descriptor;
/*  1344:      */     }
/*  1345:      */     
/*  1346:      */     protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable()
/*  1347:      */     {
/*  1348: 1463 */       return MRProtos.internal_static_hadoop_mapreduce_TaskIdProto_fieldAccessorTable.ensureFieldAccessorsInitialized(TaskIdProto.class, Builder.class);
/*  1349:      */     }
/*  1350:      */     
/*  1351: 1468 */     public static Parser<TaskIdProto> PARSER = new AbstractParser()
/*  1352:      */     {
/*  1353:      */       public MRProtos.TaskIdProto parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry)
/*  1354:      */         throws InvalidProtocolBufferException
/*  1355:      */       {
/*  1356: 1474 */         return new MRProtos.TaskIdProto(input, extensionRegistry, null);
/*  1357:      */       }
/*  1358:      */     };
/*  1359:      */     private int bitField0_;
/*  1360:      */     public static final int JOB_ID_FIELD_NUMBER = 1;
/*  1361:      */     private MRProtos.JobIdProto jobId_;
/*  1362:      */     public static final int TASK_TYPE_FIELD_NUMBER = 2;
/*  1363:      */     private MRProtos.TaskTypeProto taskType_;
/*  1364:      */     public static final int ID_FIELD_NUMBER = 3;
/*  1365:      */     private int id_;
/*  1366:      */     
/*  1367:      */     public Parser<TaskIdProto> getParserForType()
/*  1368:      */     {
/*  1369: 1480 */       return PARSER;
/*  1370:      */     }
/*  1371:      */     
/*  1372:      */     public boolean hasJobId()
/*  1373:      */     {
/*  1374: 1491 */       return (this.bitField0_ & 0x1) == 1;
/*  1375:      */     }
/*  1376:      */     
/*  1377:      */     public MRProtos.JobIdProto getJobId()
/*  1378:      */     {
/*  1379: 1497 */       return this.jobId_;
/*  1380:      */     }
/*  1381:      */     
/*  1382:      */     public MRProtos.JobIdProtoOrBuilder getJobIdOrBuilder()
/*  1383:      */     {
/*  1384: 1503 */       return this.jobId_;
/*  1385:      */     }
/*  1386:      */     
/*  1387:      */     public boolean hasTaskType()
/*  1388:      */     {
/*  1389: 1513 */       return (this.bitField0_ & 0x2) == 2;
/*  1390:      */     }
/*  1391:      */     
/*  1392:      */     public MRProtos.TaskTypeProto getTaskType()
/*  1393:      */     {
/*  1394: 1519 */       return this.taskType_;
/*  1395:      */     }
/*  1396:      */     
/*  1397:      */     public boolean hasId()
/*  1398:      */     {
/*  1399: 1529 */       return (this.bitField0_ & 0x4) == 4;
/*  1400:      */     }
/*  1401:      */     
/*  1402:      */     public int getId()
/*  1403:      */     {
/*  1404: 1535 */       return this.id_;
/*  1405:      */     }
/*  1406:      */     
/*  1407:      */     private void initFields()
/*  1408:      */     {
/*  1409: 1539 */       this.jobId_ = MRProtos.JobIdProto.getDefaultInstance();
/*  1410: 1540 */       this.taskType_ = MRProtos.TaskTypeProto.MAP;
/*  1411: 1541 */       this.id_ = 0;
/*  1412:      */     }
/*  1413:      */     
/*  1414: 1543 */     private byte memoizedIsInitialized = -1;
/*  1415:      */     
/*  1416:      */     public final boolean isInitialized()
/*  1417:      */     {
/*  1418: 1545 */       byte isInitialized = this.memoizedIsInitialized;
/*  1419: 1546 */       if (isInitialized != -1) {
/*  1420: 1546 */         return isInitialized == 1;
/*  1421:      */       }
/*  1422: 1548 */       this.memoizedIsInitialized = 1;
/*  1423: 1549 */       return true;
/*  1424:      */     }
/*  1425:      */     
/*  1426:      */     public void writeTo(CodedOutputStream output)
/*  1427:      */       throws IOException
/*  1428:      */     {
/*  1429: 1554 */       getSerializedSize();
/*  1430: 1555 */       if ((this.bitField0_ & 0x1) == 1) {
/*  1431: 1556 */         output.writeMessage(1, this.jobId_);
/*  1432:      */       }
/*  1433: 1558 */       if ((this.bitField0_ & 0x2) == 2) {
/*  1434: 1559 */         output.writeEnum(2, this.taskType_.getNumber());
/*  1435:      */       }
/*  1436: 1561 */       if ((this.bitField0_ & 0x4) == 4) {
/*  1437: 1562 */         output.writeInt32(3, this.id_);
/*  1438:      */       }
/*  1439: 1564 */       getUnknownFields().writeTo(output);
/*  1440:      */     }
/*  1441:      */     
/*  1442: 1567 */     private int memoizedSerializedSize = -1;
/*  1443:      */     private static final long serialVersionUID = 0L;
/*  1444:      */     
/*  1445:      */     public int getSerializedSize()
/*  1446:      */     {
/*  1447: 1569 */       int size = this.memoizedSerializedSize;
/*  1448: 1570 */       if (size != -1) {
/*  1449: 1570 */         return size;
/*  1450:      */       }
/*  1451: 1572 */       size = 0;
/*  1452: 1573 */       if ((this.bitField0_ & 0x1) == 1) {
/*  1453: 1574 */         size += CodedOutputStream.computeMessageSize(1, this.jobId_);
/*  1454:      */       }
/*  1455: 1577 */       if ((this.bitField0_ & 0x2) == 2) {
/*  1456: 1578 */         size += CodedOutputStream.computeEnumSize(2, this.taskType_.getNumber());
/*  1457:      */       }
/*  1458: 1581 */       if ((this.bitField0_ & 0x4) == 4) {
/*  1459: 1582 */         size += CodedOutputStream.computeInt32Size(3, this.id_);
/*  1460:      */       }
/*  1461: 1585 */       size += getUnknownFields().getSerializedSize();
/*  1462: 1586 */       this.memoizedSerializedSize = size;
/*  1463: 1587 */       return size;
/*  1464:      */     }
/*  1465:      */     
/*  1466:      */     protected Object writeReplace()
/*  1467:      */       throws ObjectStreamException
/*  1468:      */     {
/*  1469: 1594 */       return super.writeReplace();
/*  1470:      */     }
/*  1471:      */     
/*  1472:      */     public boolean equals(Object obj)
/*  1473:      */     {
/*  1474: 1599 */       if (obj == this) {
/*  1475: 1600 */         return true;
/*  1476:      */       }
/*  1477: 1602 */       if (!(obj instanceof TaskIdProto)) {
/*  1478: 1603 */         return super.equals(obj);
/*  1479:      */       }
/*  1480: 1605 */       TaskIdProto other = (TaskIdProto)obj;
/*  1481:      */       
/*  1482: 1607 */       boolean result = true;
/*  1483: 1608 */       result = (result) && (hasJobId() == other.hasJobId());
/*  1484: 1609 */       if (hasJobId()) {
/*  1485: 1610 */         result = (result) && (getJobId().equals(other.getJobId()));
/*  1486:      */       }
/*  1487: 1613 */       result = (result) && (hasTaskType() == other.hasTaskType());
/*  1488: 1614 */       if (hasTaskType()) {
/*  1489: 1615 */         result = (result) && (getTaskType() == other.getTaskType());
/*  1490:      */       }
/*  1491: 1618 */       result = (result) && (hasId() == other.hasId());
/*  1492: 1619 */       if (hasId()) {
/*  1493: 1620 */         result = (result) && (getId() == other.getId());
/*  1494:      */       }
/*  1495: 1623 */       result = (result) && (getUnknownFields().equals(other.getUnknownFields()));
/*  1496:      */       
/*  1497: 1625 */       return result;
/*  1498:      */     }
/*  1499:      */     
/*  1500: 1628 */     private int memoizedHashCode = 0;
/*  1501:      */     
/*  1502:      */     public int hashCode()
/*  1503:      */     {
/*  1504: 1631 */       if (this.memoizedHashCode != 0) {
/*  1505: 1632 */         return this.memoizedHashCode;
/*  1506:      */       }
/*  1507: 1634 */       int hash = 41;
/*  1508: 1635 */       hash = 19 * hash + getDescriptorForType().hashCode();
/*  1509: 1636 */       if (hasJobId())
/*  1510:      */       {
/*  1511: 1637 */         hash = 37 * hash + 1;
/*  1512: 1638 */         hash = 53 * hash + getJobId().hashCode();
/*  1513:      */       }
/*  1514: 1640 */       if (hasTaskType())
/*  1515:      */       {
/*  1516: 1641 */         hash = 37 * hash + 2;
/*  1517: 1642 */         hash = 53 * hash + hashEnum(getTaskType());
/*  1518:      */       }
/*  1519: 1644 */       if (hasId())
/*  1520:      */       {
/*  1521: 1645 */         hash = 37 * hash + 3;
/*  1522: 1646 */         hash = 53 * hash + getId();
/*  1523:      */       }
/*  1524: 1648 */       hash = 29 * hash + getUnknownFields().hashCode();
/*  1525: 1649 */       this.memoizedHashCode = hash;
/*  1526: 1650 */       return hash;
/*  1527:      */     }
/*  1528:      */     
/*  1529:      */     public static TaskIdProto parseFrom(ByteString data)
/*  1530:      */       throws InvalidProtocolBufferException
/*  1531:      */     {
/*  1532: 1656 */       return (TaskIdProto)PARSER.parseFrom(data);
/*  1533:      */     }
/*  1534:      */     
/*  1535:      */     public static TaskIdProto parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry)
/*  1536:      */       throws InvalidProtocolBufferException
/*  1537:      */     {
/*  1538: 1662 */       return (TaskIdProto)PARSER.parseFrom(data, extensionRegistry);
/*  1539:      */     }
/*  1540:      */     
/*  1541:      */     public static TaskIdProto parseFrom(byte[] data)
/*  1542:      */       throws InvalidProtocolBufferException
/*  1543:      */     {
/*  1544: 1666 */       return (TaskIdProto)PARSER.parseFrom(data);
/*  1545:      */     }
/*  1546:      */     
/*  1547:      */     public static TaskIdProto parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry)
/*  1548:      */       throws InvalidProtocolBufferException
/*  1549:      */     {
/*  1550: 1672 */       return (TaskIdProto)PARSER.parseFrom(data, extensionRegistry);
/*  1551:      */     }
/*  1552:      */     
/*  1553:      */     public static TaskIdProto parseFrom(InputStream input)
/*  1554:      */       throws IOException
/*  1555:      */     {
/*  1556: 1676 */       return (TaskIdProto)PARSER.parseFrom(input);
/*  1557:      */     }
/*  1558:      */     
/*  1559:      */     public static TaskIdProto parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry)
/*  1560:      */       throws IOException
/*  1561:      */     {
/*  1562: 1682 */       return (TaskIdProto)PARSER.parseFrom(input, extensionRegistry);
/*  1563:      */     }
/*  1564:      */     
/*  1565:      */     public static TaskIdProto parseDelimitedFrom(InputStream input)
/*  1566:      */       throws IOException
/*  1567:      */     {
/*  1568: 1686 */       return (TaskIdProto)PARSER.parseDelimitedFrom(input);
/*  1569:      */     }
/*  1570:      */     
/*  1571:      */     public static TaskIdProto parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry)
/*  1572:      */       throws IOException
/*  1573:      */     {
/*  1574: 1692 */       return (TaskIdProto)PARSER.parseDelimitedFrom(input, extensionRegistry);
/*  1575:      */     }
/*  1576:      */     
/*  1577:      */     public static TaskIdProto parseFrom(CodedInputStream input)
/*  1578:      */       throws IOException
/*  1579:      */     {
/*  1580: 1697 */       return (TaskIdProto)PARSER.parseFrom(input);
/*  1581:      */     }
/*  1582:      */     
/*  1583:      */     public static TaskIdProto parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry)
/*  1584:      */       throws IOException
/*  1585:      */     {
/*  1586: 1703 */       return (TaskIdProto)PARSER.parseFrom(input, extensionRegistry);
/*  1587:      */     }
/*  1588:      */     
/*  1589:      */     public static Builder newBuilder()
/*  1590:      */     {
/*  1591: 1706 */       return Builder.access$1300();
/*  1592:      */     }
/*  1593:      */     
/*  1594:      */     public Builder newBuilderForType()
/*  1595:      */     {
/*  1596: 1707 */       return newBuilder();
/*  1597:      */     }
/*  1598:      */     
/*  1599:      */     public static Builder newBuilder(TaskIdProto prototype)
/*  1600:      */     {
/*  1601: 1709 */       return newBuilder().mergeFrom(prototype);
/*  1602:      */     }
/*  1603:      */     
/*  1604:      */     public Builder toBuilder()
/*  1605:      */     {
/*  1606: 1711 */       return newBuilder(this);
/*  1607:      */     }
/*  1608:      */     
/*  1609:      */     protected Builder newBuilderForType(GeneratedMessage.BuilderParent parent)
/*  1610:      */     {
/*  1611: 1716 */       Builder builder = new Builder(parent, null);
/*  1612: 1717 */       return builder;
/*  1613:      */     }
/*  1614:      */     
/*  1615:      */     public static final class Builder
/*  1616:      */       extends GeneratedMessage.Builder<Builder>
/*  1617:      */       implements MRProtos.TaskIdProtoOrBuilder
/*  1618:      */     {
/*  1619:      */       private int bitField0_;
/*  1620:      */       
/*  1621:      */       public static final Descriptors.Descriptor getDescriptor()
/*  1622:      */       {
/*  1623: 1727 */         return MRProtos.internal_static_hadoop_mapreduce_TaskIdProto_descriptor;
/*  1624:      */       }
/*  1625:      */       
/*  1626:      */       protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable()
/*  1627:      */       {
/*  1628: 1732 */         return MRProtos.internal_static_hadoop_mapreduce_TaskIdProto_fieldAccessorTable.ensureFieldAccessorsInitialized(MRProtos.TaskIdProto.class, Builder.class);
/*  1629:      */       }
/*  1630:      */       
/*  1631:      */       private Builder()
/*  1632:      */       {
/*  1633: 1739 */         maybeForceBuilderInitialization();
/*  1634:      */       }
/*  1635:      */       
/*  1636:      */       private Builder(GeneratedMessage.BuilderParent parent)
/*  1637:      */       {
/*  1638: 1744 */         super();
/*  1639: 1745 */         maybeForceBuilderInitialization();
/*  1640:      */       }
/*  1641:      */       
/*  1642:      */       private void maybeForceBuilderInitialization()
/*  1643:      */       {
/*  1644: 1748 */         if (MRProtos.TaskIdProto.alwaysUseFieldBuilders) {
/*  1645: 1749 */           getJobIdFieldBuilder();
/*  1646:      */         }
/*  1647:      */       }
/*  1648:      */       
/*  1649:      */       private static Builder create()
/*  1650:      */       {
/*  1651: 1753 */         return new Builder();
/*  1652:      */       }
/*  1653:      */       
/*  1654:      */       public Builder clear()
/*  1655:      */       {
/*  1656: 1757 */         super.clear();
/*  1657: 1758 */         if (this.jobIdBuilder_ == null) {
/*  1658: 1759 */           this.jobId_ = MRProtos.JobIdProto.getDefaultInstance();
/*  1659:      */         } else {
/*  1660: 1761 */           this.jobIdBuilder_.clear();
/*  1661:      */         }
/*  1662: 1763 */         this.bitField0_ &= 0xFFFFFFFE;
/*  1663: 1764 */         this.taskType_ = MRProtos.TaskTypeProto.MAP;
/*  1664: 1765 */         this.bitField0_ &= 0xFFFFFFFD;
/*  1665: 1766 */         this.id_ = 0;
/*  1666: 1767 */         this.bitField0_ &= 0xFFFFFFFB;
/*  1667: 1768 */         return this;
/*  1668:      */       }
/*  1669:      */       
/*  1670:      */       public Builder clone()
/*  1671:      */       {
/*  1672: 1772 */         return create().mergeFrom(buildPartial());
/*  1673:      */       }
/*  1674:      */       
/*  1675:      */       public Descriptors.Descriptor getDescriptorForType()
/*  1676:      */       {
/*  1677: 1777 */         return MRProtos.internal_static_hadoop_mapreduce_TaskIdProto_descriptor;
/*  1678:      */       }
/*  1679:      */       
/*  1680:      */       public MRProtos.TaskIdProto getDefaultInstanceForType()
/*  1681:      */       {
/*  1682: 1781 */         return MRProtos.TaskIdProto.getDefaultInstance();
/*  1683:      */       }
/*  1684:      */       
/*  1685:      */       public MRProtos.TaskIdProto build()
/*  1686:      */       {
/*  1687: 1785 */         MRProtos.TaskIdProto result = buildPartial();
/*  1688: 1786 */         if (!result.isInitialized()) {
/*  1689: 1787 */           throw newUninitializedMessageException(result);
/*  1690:      */         }
/*  1691: 1789 */         return result;
/*  1692:      */       }
/*  1693:      */       
/*  1694:      */       public MRProtos.TaskIdProto buildPartial()
/*  1695:      */       {
/*  1696: 1793 */         MRProtos.TaskIdProto result = new MRProtos.TaskIdProto(this, null);
/*  1697: 1794 */         int from_bitField0_ = this.bitField0_;
/*  1698: 1795 */         int to_bitField0_ = 0;
/*  1699: 1796 */         if ((from_bitField0_ & 0x1) == 1) {
/*  1700: 1797 */           to_bitField0_ |= 0x1;
/*  1701:      */         }
/*  1702: 1799 */         if (this.jobIdBuilder_ == null) {
/*  1703: 1800 */           result.jobId_ = this.jobId_;
/*  1704:      */         } else {
/*  1705: 1802 */           result.jobId_ = ((MRProtos.JobIdProto)this.jobIdBuilder_.build());
/*  1706:      */         }
/*  1707: 1804 */         if ((from_bitField0_ & 0x2) == 2) {
/*  1708: 1805 */           to_bitField0_ |= 0x2;
/*  1709:      */         }
/*  1710: 1807 */         result.taskType_ = this.taskType_;
/*  1711: 1808 */         if ((from_bitField0_ & 0x4) == 4) {
/*  1712: 1809 */           to_bitField0_ |= 0x4;
/*  1713:      */         }
/*  1714: 1811 */         result.id_ = this.id_;
/*  1715: 1812 */         result.bitField0_ = to_bitField0_;
/*  1716: 1813 */         onBuilt();
/*  1717: 1814 */         return result;
/*  1718:      */       }
/*  1719:      */       
/*  1720:      */       public Builder mergeFrom(Message other)
/*  1721:      */       {
/*  1722: 1818 */         if ((other instanceof MRProtos.TaskIdProto)) {
/*  1723: 1819 */           return mergeFrom((MRProtos.TaskIdProto)other);
/*  1724:      */         }
/*  1725: 1821 */         super.mergeFrom(other);
/*  1726: 1822 */         return this;
/*  1727:      */       }
/*  1728:      */       
/*  1729:      */       public Builder mergeFrom(MRProtos.TaskIdProto other)
/*  1730:      */       {
/*  1731: 1827 */         if (other == MRProtos.TaskIdProto.getDefaultInstance()) {
/*  1732: 1827 */           return this;
/*  1733:      */         }
/*  1734: 1828 */         if (other.hasJobId()) {
/*  1735: 1829 */           mergeJobId(other.getJobId());
/*  1736:      */         }
/*  1737: 1831 */         if (other.hasTaskType()) {
/*  1738: 1832 */           setTaskType(other.getTaskType());
/*  1739:      */         }
/*  1740: 1834 */         if (other.hasId()) {
/*  1741: 1835 */           setId(other.getId());
/*  1742:      */         }
/*  1743: 1837 */         mergeUnknownFields(other.getUnknownFields());
/*  1744: 1838 */         return this;
/*  1745:      */       }
/*  1746:      */       
/*  1747:      */       public final boolean isInitialized()
/*  1748:      */       {
/*  1749: 1842 */         return true;
/*  1750:      */       }
/*  1751:      */       
/*  1752:      */       public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry)
/*  1753:      */         throws IOException
/*  1754:      */       {
/*  1755: 1849 */         MRProtos.TaskIdProto parsedMessage = null;
/*  1756:      */         try
/*  1757:      */         {
/*  1758: 1851 */           parsedMessage = (MRProtos.TaskIdProto)MRProtos.TaskIdProto.PARSER.parsePartialFrom(input, extensionRegistry);
/*  1759:      */         }
/*  1760:      */         catch (InvalidProtocolBufferException e)
/*  1761:      */         {
/*  1762: 1853 */           parsedMessage = (MRProtos.TaskIdProto)e.getUnfinishedMessage();
/*  1763: 1854 */           throw e;
/*  1764:      */         }
/*  1765:      */         finally
/*  1766:      */         {
/*  1767: 1856 */           if (parsedMessage != null) {
/*  1768: 1857 */             mergeFrom(parsedMessage);
/*  1769:      */           }
/*  1770:      */         }
/*  1771: 1860 */         return this;
/*  1772:      */       }
/*  1773:      */       
/*  1774: 1865 */       private MRProtos.JobIdProto jobId_ = MRProtos.JobIdProto.getDefaultInstance();
/*  1775:      */       private SingleFieldBuilder<MRProtos.JobIdProto, MRProtos.JobIdProto.Builder, MRProtos.JobIdProtoOrBuilder> jobIdBuilder_;
/*  1776:      */       
/*  1777:      */       public boolean hasJobId()
/*  1778:      */       {
/*  1779: 1872 */         return (this.bitField0_ & 0x1) == 1;
/*  1780:      */       }
/*  1781:      */       
/*  1782:      */       public MRProtos.JobIdProto getJobId()
/*  1783:      */       {
/*  1784: 1878 */         if (this.jobIdBuilder_ == null) {
/*  1785: 1879 */           return this.jobId_;
/*  1786:      */         }
/*  1787: 1881 */         return (MRProtos.JobIdProto)this.jobIdBuilder_.getMessage();
/*  1788:      */       }
/*  1789:      */       
/*  1790:      */       public Builder setJobId(MRProtos.JobIdProto value)
/*  1791:      */       {
/*  1792: 1888 */         if (this.jobIdBuilder_ == null)
/*  1793:      */         {
/*  1794: 1889 */           if (value == null) {
/*  1795: 1890 */             throw new NullPointerException();
/*  1796:      */           }
/*  1797: 1892 */           this.jobId_ = value;
/*  1798: 1893 */           onChanged();
/*  1799:      */         }
/*  1800:      */         else
/*  1801:      */         {
/*  1802: 1895 */           this.jobIdBuilder_.setMessage(value);
/*  1803:      */         }
/*  1804: 1897 */         this.bitField0_ |= 0x1;
/*  1805: 1898 */         return this;
/*  1806:      */       }
/*  1807:      */       
/*  1808:      */       public Builder setJobId(MRProtos.JobIdProto.Builder builderForValue)
/*  1809:      */       {
/*  1810: 1905 */         if (this.jobIdBuilder_ == null)
/*  1811:      */         {
/*  1812: 1906 */           this.jobId_ = builderForValue.build();
/*  1813: 1907 */           onChanged();
/*  1814:      */         }
/*  1815:      */         else
/*  1816:      */         {
/*  1817: 1909 */           this.jobIdBuilder_.setMessage(builderForValue.build());
/*  1818:      */         }
/*  1819: 1911 */         this.bitField0_ |= 0x1;
/*  1820: 1912 */         return this;
/*  1821:      */       }
/*  1822:      */       
/*  1823:      */       public Builder mergeJobId(MRProtos.JobIdProto value)
/*  1824:      */       {
/*  1825: 1918 */         if (this.jobIdBuilder_ == null)
/*  1826:      */         {
/*  1827: 1919 */           if (((this.bitField0_ & 0x1) == 1) && (this.jobId_ != MRProtos.JobIdProto.getDefaultInstance())) {
/*  1828: 1921 */             this.jobId_ = MRProtos.JobIdProto.newBuilder(this.jobId_).mergeFrom(value).buildPartial();
/*  1829:      */           } else {
/*  1830: 1924 */             this.jobId_ = value;
/*  1831:      */           }
/*  1832: 1926 */           onChanged();
/*  1833:      */         }
/*  1834:      */         else
/*  1835:      */         {
/*  1836: 1928 */           this.jobIdBuilder_.mergeFrom(value);
/*  1837:      */         }
/*  1838: 1930 */         this.bitField0_ |= 0x1;
/*  1839: 1931 */         return this;
/*  1840:      */       }
/*  1841:      */       
/*  1842:      */       public Builder clearJobId()
/*  1843:      */       {
/*  1844: 1937 */         if (this.jobIdBuilder_ == null)
/*  1845:      */         {
/*  1846: 1938 */           this.jobId_ = MRProtos.JobIdProto.getDefaultInstance();
/*  1847: 1939 */           onChanged();
/*  1848:      */         }
/*  1849:      */         else
/*  1850:      */         {
/*  1851: 1941 */           this.jobIdBuilder_.clear();
/*  1852:      */         }
/*  1853: 1943 */         this.bitField0_ &= 0xFFFFFFFE;
/*  1854: 1944 */         return this;
/*  1855:      */       }
/*  1856:      */       
/*  1857:      */       public MRProtos.JobIdProto.Builder getJobIdBuilder()
/*  1858:      */       {
/*  1859: 1950 */         this.bitField0_ |= 0x1;
/*  1860: 1951 */         onChanged();
/*  1861: 1952 */         return (MRProtos.JobIdProto.Builder)getJobIdFieldBuilder().getBuilder();
/*  1862:      */       }
/*  1863:      */       
/*  1864:      */       public MRProtos.JobIdProtoOrBuilder getJobIdOrBuilder()
/*  1865:      */       {
/*  1866: 1958 */         if (this.jobIdBuilder_ != null) {
/*  1867: 1959 */           return (MRProtos.JobIdProtoOrBuilder)this.jobIdBuilder_.getMessageOrBuilder();
/*  1868:      */         }
/*  1869: 1961 */         return this.jobId_;
/*  1870:      */       }
/*  1871:      */       
/*  1872:      */       private SingleFieldBuilder<MRProtos.JobIdProto, MRProtos.JobIdProto.Builder, MRProtos.JobIdProtoOrBuilder> getJobIdFieldBuilder()
/*  1873:      */       {
/*  1874: 1970 */         if (this.jobIdBuilder_ == null)
/*  1875:      */         {
/*  1876: 1971 */           this.jobIdBuilder_ = new SingleFieldBuilder(this.jobId_, getParentForChildren(), isClean());
/*  1877:      */           
/*  1878:      */ 
/*  1879:      */ 
/*  1880:      */ 
/*  1881: 1976 */           this.jobId_ = null;
/*  1882:      */         }
/*  1883: 1978 */         return this.jobIdBuilder_;
/*  1884:      */       }
/*  1885:      */       
/*  1886: 1982 */       private MRProtos.TaskTypeProto taskType_ = MRProtos.TaskTypeProto.MAP;
/*  1887:      */       private int id_;
/*  1888:      */       
/*  1889:      */       public boolean hasTaskType()
/*  1890:      */       {
/*  1891: 1987 */         return (this.bitField0_ & 0x2) == 2;
/*  1892:      */       }
/*  1893:      */       
/*  1894:      */       public MRProtos.TaskTypeProto getTaskType()
/*  1895:      */       {
/*  1896: 1993 */         return this.taskType_;
/*  1897:      */       }
/*  1898:      */       
/*  1899:      */       public Builder setTaskType(MRProtos.TaskTypeProto value)
/*  1900:      */       {
/*  1901: 1999 */         if (value == null) {
/*  1902: 2000 */           throw new NullPointerException();
/*  1903:      */         }
/*  1904: 2002 */         this.bitField0_ |= 0x2;
/*  1905: 2003 */         this.taskType_ = value;
/*  1906: 2004 */         onChanged();
/*  1907: 2005 */         return this;
/*  1908:      */       }
/*  1909:      */       
/*  1910:      */       public Builder clearTaskType()
/*  1911:      */       {
/*  1912: 2011 */         this.bitField0_ &= 0xFFFFFFFD;
/*  1913: 2012 */         this.taskType_ = MRProtos.TaskTypeProto.MAP;
/*  1914: 2013 */         onChanged();
/*  1915: 2014 */         return this;
/*  1916:      */       }
/*  1917:      */       
/*  1918:      */       public boolean hasId()
/*  1919:      */       {
/*  1920: 2023 */         return (this.bitField0_ & 0x4) == 4;
/*  1921:      */       }
/*  1922:      */       
/*  1923:      */       public int getId()
/*  1924:      */       {
/*  1925: 2029 */         return this.id_;
/*  1926:      */       }
/*  1927:      */       
/*  1928:      */       public Builder setId(int value)
/*  1929:      */       {
/*  1930: 2035 */         this.bitField0_ |= 0x4;
/*  1931: 2036 */         this.id_ = value;
/*  1932: 2037 */         onChanged();
/*  1933: 2038 */         return this;
/*  1934:      */       }
/*  1935:      */       
/*  1936:      */       public Builder clearId()
/*  1937:      */       {
/*  1938: 2044 */         this.bitField0_ &= 0xFFFFFFFB;
/*  1939: 2045 */         this.id_ = 0;
/*  1940: 2046 */         onChanged();
/*  1941: 2047 */         return this;
/*  1942:      */       }
/*  1943:      */     }
/*  1944:      */     
/*  1945:      */     static
/*  1946:      */     {
/*  1947: 2054 */       defaultInstance = new TaskIdProto(true);
/*  1948: 2055 */       defaultInstance.initFields();
/*  1949:      */     }
/*  1950:      */   }
/*  1951:      */   
/*  1952:      */   public static abstract interface TaskAttemptIdProtoOrBuilder
/*  1953:      */     extends MessageOrBuilder
/*  1954:      */   {
/*  1955:      */     public abstract boolean hasTaskId();
/*  1956:      */     
/*  1957:      */     public abstract MRProtos.TaskIdProto getTaskId();
/*  1958:      */     
/*  1959:      */     public abstract MRProtos.TaskIdProtoOrBuilder getTaskIdOrBuilder();
/*  1960:      */     
/*  1961:      */     public abstract boolean hasId();
/*  1962:      */     
/*  1963:      */     public abstract int getId();
/*  1964:      */   }
/*  1965:      */   
/*  1966:      */   public static final class TaskAttemptIdProto
/*  1967:      */     extends GeneratedMessage
/*  1968:      */     implements MRProtos.TaskAttemptIdProtoOrBuilder
/*  1969:      */   {
/*  1970:      */     private static final TaskAttemptIdProto defaultInstance;
/*  1971:      */     private final UnknownFieldSet unknownFields;
/*  1972:      */     
/*  1973:      */     private TaskAttemptIdProto(GeneratedMessage.Builder<?> builder)
/*  1974:      */     {
/*  1975: 2096 */       super();
/*  1976: 2097 */       this.unknownFields = builder.getUnknownFields();
/*  1977:      */     }
/*  1978:      */     
/*  1979:      */     private TaskAttemptIdProto(boolean noInit)
/*  1980:      */     {
/*  1981: 2099 */       this.unknownFields = UnknownFieldSet.getDefaultInstance();
/*  1982:      */     }
/*  1983:      */     
/*  1984:      */     public static TaskAttemptIdProto getDefaultInstance()
/*  1985:      */     {
/*  1986: 2103 */       return defaultInstance;
/*  1987:      */     }
/*  1988:      */     
/*  1989:      */     public TaskAttemptIdProto getDefaultInstanceForType()
/*  1990:      */     {
/*  1991: 2107 */       return defaultInstance;
/*  1992:      */     }
/*  1993:      */     
/*  1994:      */     public final UnknownFieldSet getUnknownFields()
/*  1995:      */     {
/*  1996: 2114 */       return this.unknownFields;
/*  1997:      */     }
/*  1998:      */     
/*  1999:      */     private TaskAttemptIdProto(CodedInputStream input, ExtensionRegistryLite extensionRegistry)
/*  2000:      */       throws InvalidProtocolBufferException
/*  2001:      */     {
/*  2002: 2120 */       initFields();
/*  2003: 2121 */       int mutable_bitField0_ = 0;
/*  2004: 2122 */       UnknownFieldSet.Builder unknownFields = UnknownFieldSet.newBuilder();
/*  2005:      */       try
/*  2006:      */       {
/*  2007: 2125 */         boolean done = false;
/*  2008: 2126 */         while (!done)
/*  2009:      */         {
/*  2010: 2127 */           int tag = input.readTag();
/*  2011: 2128 */           switch (tag)
/*  2012:      */           {
/*  2013:      */           case 0: 
/*  2014: 2130 */             done = true;
/*  2015: 2131 */             break;
/*  2016:      */           default: 
/*  2017: 2133 */             if (!parseUnknownField(input, unknownFields, extensionRegistry, tag)) {
/*  2018: 2135 */               done = true;
/*  2019:      */             }
/*  2020:      */             break;
/*  2021:      */           case 10: 
/*  2022: 2140 */             MRProtos.TaskIdProto.Builder subBuilder = null;
/*  2023: 2141 */             if ((this.bitField0_ & 0x1) == 1) {
/*  2024: 2142 */               subBuilder = this.taskId_.toBuilder();
/*  2025:      */             }
/*  2026: 2144 */             this.taskId_ = ((MRProtos.TaskIdProto)input.readMessage(MRProtos.TaskIdProto.PARSER, extensionRegistry));
/*  2027: 2145 */             if (subBuilder != null)
/*  2028:      */             {
/*  2029: 2146 */               subBuilder.mergeFrom(this.taskId_);
/*  2030: 2147 */               this.taskId_ = subBuilder.buildPartial();
/*  2031:      */             }
/*  2032: 2149 */             this.bitField0_ |= 0x1;
/*  2033: 2150 */             break;
/*  2034:      */           case 16: 
/*  2035: 2153 */             this.bitField0_ |= 0x2;
/*  2036: 2154 */             this.id_ = input.readInt32();
/*  2037:      */           }
/*  2038:      */         }
/*  2039:      */       }
/*  2040:      */       catch (InvalidProtocolBufferException e)
/*  2041:      */       {
/*  2042: 2160 */         throw e.setUnfinishedMessage(this);
/*  2043:      */       }
/*  2044:      */       catch (IOException e)
/*  2045:      */       {
/*  2046: 2162 */         throw new InvalidProtocolBufferException(e.getMessage()).setUnfinishedMessage(this);
/*  2047:      */       }
/*  2048:      */       finally
/*  2049:      */       {
/*  2050: 2165 */         this.unknownFields = unknownFields.build();
/*  2051: 2166 */         makeExtensionsImmutable();
/*  2052:      */       }
/*  2053:      */     }
/*  2054:      */     
/*  2055:      */     public static final Descriptors.Descriptor getDescriptor()
/*  2056:      */     {
/*  2057: 2171 */       return MRProtos.internal_static_hadoop_mapreduce_TaskAttemptIdProto_descriptor;
/*  2058:      */     }
/*  2059:      */     
/*  2060:      */     protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable()
/*  2061:      */     {
/*  2062: 2176 */       return MRProtos.internal_static_hadoop_mapreduce_TaskAttemptIdProto_fieldAccessorTable.ensureFieldAccessorsInitialized(TaskAttemptIdProto.class, Builder.class);
/*  2063:      */     }
/*  2064:      */     
/*  2065: 2181 */     public static Parser<TaskAttemptIdProto> PARSER = new AbstractParser()
/*  2066:      */     {
/*  2067:      */       public MRProtos.TaskAttemptIdProto parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry)
/*  2068:      */         throws InvalidProtocolBufferException
/*  2069:      */       {
/*  2070: 2187 */         return new MRProtos.TaskAttemptIdProto(input, extensionRegistry, null);
/*  2071:      */       }
/*  2072:      */     };
/*  2073:      */     private int bitField0_;
/*  2074:      */     public static final int TASK_ID_FIELD_NUMBER = 1;
/*  2075:      */     private MRProtos.TaskIdProto taskId_;
/*  2076:      */     public static final int ID_FIELD_NUMBER = 2;
/*  2077:      */     private int id_;
/*  2078:      */     
/*  2079:      */     public Parser<TaskAttemptIdProto> getParserForType()
/*  2080:      */     {
/*  2081: 2193 */       return PARSER;
/*  2082:      */     }
/*  2083:      */     
/*  2084:      */     public boolean hasTaskId()
/*  2085:      */     {
/*  2086: 2204 */       return (this.bitField0_ & 0x1) == 1;
/*  2087:      */     }
/*  2088:      */     
/*  2089:      */     public MRProtos.TaskIdProto getTaskId()
/*  2090:      */     {
/*  2091: 2210 */       return this.taskId_;
/*  2092:      */     }
/*  2093:      */     
/*  2094:      */     public MRProtos.TaskIdProtoOrBuilder getTaskIdOrBuilder()
/*  2095:      */     {
/*  2096: 2216 */       return this.taskId_;
/*  2097:      */     }
/*  2098:      */     
/*  2099:      */     public boolean hasId()
/*  2100:      */     {
/*  2101: 2226 */       return (this.bitField0_ & 0x2) == 2;
/*  2102:      */     }
/*  2103:      */     
/*  2104:      */     public int getId()
/*  2105:      */     {
/*  2106: 2232 */       return this.id_;
/*  2107:      */     }
/*  2108:      */     
/*  2109:      */     private void initFields()
/*  2110:      */     {
/*  2111: 2236 */       this.taskId_ = MRProtos.TaskIdProto.getDefaultInstance();
/*  2112: 2237 */       this.id_ = 0;
/*  2113:      */     }
/*  2114:      */     
/*  2115: 2239 */     private byte memoizedIsInitialized = -1;
/*  2116:      */     
/*  2117:      */     public final boolean isInitialized()
/*  2118:      */     {
/*  2119: 2241 */       byte isInitialized = this.memoizedIsInitialized;
/*  2120: 2242 */       if (isInitialized != -1) {
/*  2121: 2242 */         return isInitialized == 1;
/*  2122:      */       }
/*  2123: 2244 */       this.memoizedIsInitialized = 1;
/*  2124: 2245 */       return true;
/*  2125:      */     }
/*  2126:      */     
/*  2127:      */     public void writeTo(CodedOutputStream output)
/*  2128:      */       throws IOException
/*  2129:      */     {
/*  2130: 2250 */       getSerializedSize();
/*  2131: 2251 */       if ((this.bitField0_ & 0x1) == 1) {
/*  2132: 2252 */         output.writeMessage(1, this.taskId_);
/*  2133:      */       }
/*  2134: 2254 */       if ((this.bitField0_ & 0x2) == 2) {
/*  2135: 2255 */         output.writeInt32(2, this.id_);
/*  2136:      */       }
/*  2137: 2257 */       getUnknownFields().writeTo(output);
/*  2138:      */     }
/*  2139:      */     
/*  2140: 2260 */     private int memoizedSerializedSize = -1;
/*  2141:      */     private static final long serialVersionUID = 0L;
/*  2142:      */     
/*  2143:      */     public int getSerializedSize()
/*  2144:      */     {
/*  2145: 2262 */       int size = this.memoizedSerializedSize;
/*  2146: 2263 */       if (size != -1) {
/*  2147: 2263 */         return size;
/*  2148:      */       }
/*  2149: 2265 */       size = 0;
/*  2150: 2266 */       if ((this.bitField0_ & 0x1) == 1) {
/*  2151: 2267 */         size += CodedOutputStream.computeMessageSize(1, this.taskId_);
/*  2152:      */       }
/*  2153: 2270 */       if ((this.bitField0_ & 0x2) == 2) {
/*  2154: 2271 */         size += CodedOutputStream.computeInt32Size(2, this.id_);
/*  2155:      */       }
/*  2156: 2274 */       size += getUnknownFields().getSerializedSize();
/*  2157: 2275 */       this.memoizedSerializedSize = size;
/*  2158: 2276 */       return size;
/*  2159:      */     }
/*  2160:      */     
/*  2161:      */     protected Object writeReplace()
/*  2162:      */       throws ObjectStreamException
/*  2163:      */     {
/*  2164: 2283 */       return super.writeReplace();
/*  2165:      */     }
/*  2166:      */     
/*  2167:      */     public boolean equals(Object obj)
/*  2168:      */     {
/*  2169: 2288 */       if (obj == this) {
/*  2170: 2289 */         return true;
/*  2171:      */       }
/*  2172: 2291 */       if (!(obj instanceof TaskAttemptIdProto)) {
/*  2173: 2292 */         return super.equals(obj);
/*  2174:      */       }
/*  2175: 2294 */       TaskAttemptIdProto other = (TaskAttemptIdProto)obj;
/*  2176:      */       
/*  2177: 2296 */       boolean result = true;
/*  2178: 2297 */       result = (result) && (hasTaskId() == other.hasTaskId());
/*  2179: 2298 */       if (hasTaskId()) {
/*  2180: 2299 */         result = (result) && (getTaskId().equals(other.getTaskId()));
/*  2181:      */       }
/*  2182: 2302 */       result = (result) && (hasId() == other.hasId());
/*  2183: 2303 */       if (hasId()) {
/*  2184: 2304 */         result = (result) && (getId() == other.getId());
/*  2185:      */       }
/*  2186: 2307 */       result = (result) && (getUnknownFields().equals(other.getUnknownFields()));
/*  2187:      */       
/*  2188: 2309 */       return result;
/*  2189:      */     }
/*  2190:      */     
/*  2191: 2312 */     private int memoizedHashCode = 0;
/*  2192:      */     
/*  2193:      */     public int hashCode()
/*  2194:      */     {
/*  2195: 2315 */       if (this.memoizedHashCode != 0) {
/*  2196: 2316 */         return this.memoizedHashCode;
/*  2197:      */       }
/*  2198: 2318 */       int hash = 41;
/*  2199: 2319 */       hash = 19 * hash + getDescriptorForType().hashCode();
/*  2200: 2320 */       if (hasTaskId())
/*  2201:      */       {
/*  2202: 2321 */         hash = 37 * hash + 1;
/*  2203: 2322 */         hash = 53 * hash + getTaskId().hashCode();
/*  2204:      */       }
/*  2205: 2324 */       if (hasId())
/*  2206:      */       {
/*  2207: 2325 */         hash = 37 * hash + 2;
/*  2208: 2326 */         hash = 53 * hash + getId();
/*  2209:      */       }
/*  2210: 2328 */       hash = 29 * hash + getUnknownFields().hashCode();
/*  2211: 2329 */       this.memoizedHashCode = hash;
/*  2212: 2330 */       return hash;
/*  2213:      */     }
/*  2214:      */     
/*  2215:      */     public static TaskAttemptIdProto parseFrom(ByteString data)
/*  2216:      */       throws InvalidProtocolBufferException
/*  2217:      */     {
/*  2218: 2336 */       return (TaskAttemptIdProto)PARSER.parseFrom(data);
/*  2219:      */     }
/*  2220:      */     
/*  2221:      */     public static TaskAttemptIdProto parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry)
/*  2222:      */       throws InvalidProtocolBufferException
/*  2223:      */     {
/*  2224: 2342 */       return (TaskAttemptIdProto)PARSER.parseFrom(data, extensionRegistry);
/*  2225:      */     }
/*  2226:      */     
/*  2227:      */     public static TaskAttemptIdProto parseFrom(byte[] data)
/*  2228:      */       throws InvalidProtocolBufferException
/*  2229:      */     {
/*  2230: 2346 */       return (TaskAttemptIdProto)PARSER.parseFrom(data);
/*  2231:      */     }
/*  2232:      */     
/*  2233:      */     public static TaskAttemptIdProto parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry)
/*  2234:      */       throws InvalidProtocolBufferException
/*  2235:      */     {
/*  2236: 2352 */       return (TaskAttemptIdProto)PARSER.parseFrom(data, extensionRegistry);
/*  2237:      */     }
/*  2238:      */     
/*  2239:      */     public static TaskAttemptIdProto parseFrom(InputStream input)
/*  2240:      */       throws IOException
/*  2241:      */     {
/*  2242: 2356 */       return (TaskAttemptIdProto)PARSER.parseFrom(input);
/*  2243:      */     }
/*  2244:      */     
/*  2245:      */     public static TaskAttemptIdProto parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry)
/*  2246:      */       throws IOException
/*  2247:      */     {
/*  2248: 2362 */       return (TaskAttemptIdProto)PARSER.parseFrom(input, extensionRegistry);
/*  2249:      */     }
/*  2250:      */     
/*  2251:      */     public static TaskAttemptIdProto parseDelimitedFrom(InputStream input)
/*  2252:      */       throws IOException
/*  2253:      */     {
/*  2254: 2366 */       return (TaskAttemptIdProto)PARSER.parseDelimitedFrom(input);
/*  2255:      */     }
/*  2256:      */     
/*  2257:      */     public static TaskAttemptIdProto parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry)
/*  2258:      */       throws IOException
/*  2259:      */     {
/*  2260: 2372 */       return (TaskAttemptIdProto)PARSER.parseDelimitedFrom(input, extensionRegistry);
/*  2261:      */     }
/*  2262:      */     
/*  2263:      */     public static TaskAttemptIdProto parseFrom(CodedInputStream input)
/*  2264:      */       throws IOException
/*  2265:      */     {
/*  2266: 2377 */       return (TaskAttemptIdProto)PARSER.parseFrom(input);
/*  2267:      */     }
/*  2268:      */     
/*  2269:      */     public static TaskAttemptIdProto parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry)
/*  2270:      */       throws IOException
/*  2271:      */     {
/*  2272: 2383 */       return (TaskAttemptIdProto)PARSER.parseFrom(input, extensionRegistry);
/*  2273:      */     }
/*  2274:      */     
/*  2275:      */     public static Builder newBuilder()
/*  2276:      */     {
/*  2277: 2386 */       return Builder.access$2400();
/*  2278:      */     }
/*  2279:      */     
/*  2280:      */     public Builder newBuilderForType()
/*  2281:      */     {
/*  2282: 2387 */       return newBuilder();
/*  2283:      */     }
/*  2284:      */     
/*  2285:      */     public static Builder newBuilder(TaskAttemptIdProto prototype)
/*  2286:      */     {
/*  2287: 2389 */       return newBuilder().mergeFrom(prototype);
/*  2288:      */     }
/*  2289:      */     
/*  2290:      */     public Builder toBuilder()
/*  2291:      */     {
/*  2292: 2391 */       return newBuilder(this);
/*  2293:      */     }
/*  2294:      */     
/*  2295:      */     protected Builder newBuilderForType(GeneratedMessage.BuilderParent parent)
/*  2296:      */     {
/*  2297: 2396 */       Builder builder = new Builder(parent, null);
/*  2298: 2397 */       return builder;
/*  2299:      */     }
/*  2300:      */     
/*  2301:      */     public static final class Builder
/*  2302:      */       extends GeneratedMessage.Builder<Builder>
/*  2303:      */       implements MRProtos.TaskAttemptIdProtoOrBuilder
/*  2304:      */     {
/*  2305:      */       private int bitField0_;
/*  2306:      */       
/*  2307:      */       public static final Descriptors.Descriptor getDescriptor()
/*  2308:      */       {
/*  2309: 2407 */         return MRProtos.internal_static_hadoop_mapreduce_TaskAttemptIdProto_descriptor;
/*  2310:      */       }
/*  2311:      */       
/*  2312:      */       protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable()
/*  2313:      */       {
/*  2314: 2412 */         return MRProtos.internal_static_hadoop_mapreduce_TaskAttemptIdProto_fieldAccessorTable.ensureFieldAccessorsInitialized(MRProtos.TaskAttemptIdProto.class, Builder.class);
/*  2315:      */       }
/*  2316:      */       
/*  2317:      */       private Builder()
/*  2318:      */       {
/*  2319: 2419 */         maybeForceBuilderInitialization();
/*  2320:      */       }
/*  2321:      */       
/*  2322:      */       private Builder(GeneratedMessage.BuilderParent parent)
/*  2323:      */       {
/*  2324: 2424 */         super();
/*  2325: 2425 */         maybeForceBuilderInitialization();
/*  2326:      */       }
/*  2327:      */       
/*  2328:      */       private void maybeForceBuilderInitialization()
/*  2329:      */       {
/*  2330: 2428 */         if (MRProtos.TaskAttemptIdProto.alwaysUseFieldBuilders) {
/*  2331: 2429 */           getTaskIdFieldBuilder();
/*  2332:      */         }
/*  2333:      */       }
/*  2334:      */       
/*  2335:      */       private static Builder create()
/*  2336:      */       {
/*  2337: 2433 */         return new Builder();
/*  2338:      */       }
/*  2339:      */       
/*  2340:      */       public Builder clear()
/*  2341:      */       {
/*  2342: 2437 */         super.clear();
/*  2343: 2438 */         if (this.taskIdBuilder_ == null) {
/*  2344: 2439 */           this.taskId_ = MRProtos.TaskIdProto.getDefaultInstance();
/*  2345:      */         } else {
/*  2346: 2441 */           this.taskIdBuilder_.clear();
/*  2347:      */         }
/*  2348: 2443 */         this.bitField0_ &= 0xFFFFFFFE;
/*  2349: 2444 */         this.id_ = 0;
/*  2350: 2445 */         this.bitField0_ &= 0xFFFFFFFD;
/*  2351: 2446 */         return this;
/*  2352:      */       }
/*  2353:      */       
/*  2354:      */       public Builder clone()
/*  2355:      */       {
/*  2356: 2450 */         return create().mergeFrom(buildPartial());
/*  2357:      */       }
/*  2358:      */       
/*  2359:      */       public Descriptors.Descriptor getDescriptorForType()
/*  2360:      */       {
/*  2361: 2455 */         return MRProtos.internal_static_hadoop_mapreduce_TaskAttemptIdProto_descriptor;
/*  2362:      */       }
/*  2363:      */       
/*  2364:      */       public MRProtos.TaskAttemptIdProto getDefaultInstanceForType()
/*  2365:      */       {
/*  2366: 2459 */         return MRProtos.TaskAttemptIdProto.getDefaultInstance();
/*  2367:      */       }
/*  2368:      */       
/*  2369:      */       public MRProtos.TaskAttemptIdProto build()
/*  2370:      */       {
/*  2371: 2463 */         MRProtos.TaskAttemptIdProto result = buildPartial();
/*  2372: 2464 */         if (!result.isInitialized()) {
/*  2373: 2465 */           throw newUninitializedMessageException(result);
/*  2374:      */         }
/*  2375: 2467 */         return result;
/*  2376:      */       }
/*  2377:      */       
/*  2378:      */       public MRProtos.TaskAttemptIdProto buildPartial()
/*  2379:      */       {
/*  2380: 2471 */         MRProtos.TaskAttemptIdProto result = new MRProtos.TaskAttemptIdProto(this, null);
/*  2381: 2472 */         int from_bitField0_ = this.bitField0_;
/*  2382: 2473 */         int to_bitField0_ = 0;
/*  2383: 2474 */         if ((from_bitField0_ & 0x1) == 1) {
/*  2384: 2475 */           to_bitField0_ |= 0x1;
/*  2385:      */         }
/*  2386: 2477 */         if (this.taskIdBuilder_ == null) {
/*  2387: 2478 */           result.taskId_ = this.taskId_;
/*  2388:      */         } else {
/*  2389: 2480 */           result.taskId_ = ((MRProtos.TaskIdProto)this.taskIdBuilder_.build());
/*  2390:      */         }
/*  2391: 2482 */         if ((from_bitField0_ & 0x2) == 2) {
/*  2392: 2483 */           to_bitField0_ |= 0x2;
/*  2393:      */         }
/*  2394: 2485 */         result.id_ = this.id_;
/*  2395: 2486 */         result.bitField0_ = to_bitField0_;
/*  2396: 2487 */         onBuilt();
/*  2397: 2488 */         return result;
/*  2398:      */       }
/*  2399:      */       
/*  2400:      */       public Builder mergeFrom(Message other)
/*  2401:      */       {
/*  2402: 2492 */         if ((other instanceof MRProtos.TaskAttemptIdProto)) {
/*  2403: 2493 */           return mergeFrom((MRProtos.TaskAttemptIdProto)other);
/*  2404:      */         }
/*  2405: 2495 */         super.mergeFrom(other);
/*  2406: 2496 */         return this;
/*  2407:      */       }
/*  2408:      */       
/*  2409:      */       public Builder mergeFrom(MRProtos.TaskAttemptIdProto other)
/*  2410:      */       {
/*  2411: 2501 */         if (other == MRProtos.TaskAttemptIdProto.getDefaultInstance()) {
/*  2412: 2501 */           return this;
/*  2413:      */         }
/*  2414: 2502 */         if (other.hasTaskId()) {
/*  2415: 2503 */           mergeTaskId(other.getTaskId());
/*  2416:      */         }
/*  2417: 2505 */         if (other.hasId()) {
/*  2418: 2506 */           setId(other.getId());
/*  2419:      */         }
/*  2420: 2508 */         mergeUnknownFields(other.getUnknownFields());
/*  2421: 2509 */         return this;
/*  2422:      */       }
/*  2423:      */       
/*  2424:      */       public final boolean isInitialized()
/*  2425:      */       {
/*  2426: 2513 */         return true;
/*  2427:      */       }
/*  2428:      */       
/*  2429:      */       public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry)
/*  2430:      */         throws IOException
/*  2431:      */       {
/*  2432: 2520 */         MRProtos.TaskAttemptIdProto parsedMessage = null;
/*  2433:      */         try
/*  2434:      */         {
/*  2435: 2522 */           parsedMessage = (MRProtos.TaskAttemptIdProto)MRProtos.TaskAttemptIdProto.PARSER.parsePartialFrom(input, extensionRegistry);
/*  2436:      */         }
/*  2437:      */         catch (InvalidProtocolBufferException e)
/*  2438:      */         {
/*  2439: 2524 */           parsedMessage = (MRProtos.TaskAttemptIdProto)e.getUnfinishedMessage();
/*  2440: 2525 */           throw e;
/*  2441:      */         }
/*  2442:      */         finally
/*  2443:      */         {
/*  2444: 2527 */           if (parsedMessage != null) {
/*  2445: 2528 */             mergeFrom(parsedMessage);
/*  2446:      */           }
/*  2447:      */         }
/*  2448: 2531 */         return this;
/*  2449:      */       }
/*  2450:      */       
/*  2451: 2536 */       private MRProtos.TaskIdProto taskId_ = MRProtos.TaskIdProto.getDefaultInstance();
/*  2452:      */       private SingleFieldBuilder<MRProtos.TaskIdProto, MRProtos.TaskIdProto.Builder, MRProtos.TaskIdProtoOrBuilder> taskIdBuilder_;
/*  2453:      */       private int id_;
/*  2454:      */       
/*  2455:      */       public boolean hasTaskId()
/*  2456:      */       {
/*  2457: 2543 */         return (this.bitField0_ & 0x1) == 1;
/*  2458:      */       }
/*  2459:      */       
/*  2460:      */       public MRProtos.TaskIdProto getTaskId()
/*  2461:      */       {
/*  2462: 2549 */         if (this.taskIdBuilder_ == null) {
/*  2463: 2550 */           return this.taskId_;
/*  2464:      */         }
/*  2465: 2552 */         return (MRProtos.TaskIdProto)this.taskIdBuilder_.getMessage();
/*  2466:      */       }
/*  2467:      */       
/*  2468:      */       public Builder setTaskId(MRProtos.TaskIdProto value)
/*  2469:      */       {
/*  2470: 2559 */         if (this.taskIdBuilder_ == null)
/*  2471:      */         {
/*  2472: 2560 */           if (value == null) {
/*  2473: 2561 */             throw new NullPointerException();
/*  2474:      */           }
/*  2475: 2563 */           this.taskId_ = value;
/*  2476: 2564 */           onChanged();
/*  2477:      */         }
/*  2478:      */         else
/*  2479:      */         {
/*  2480: 2566 */           this.taskIdBuilder_.setMessage(value);
/*  2481:      */         }
/*  2482: 2568 */         this.bitField0_ |= 0x1;
/*  2483: 2569 */         return this;
/*  2484:      */       }
/*  2485:      */       
/*  2486:      */       public Builder setTaskId(MRProtos.TaskIdProto.Builder builderForValue)
/*  2487:      */       {
/*  2488: 2576 */         if (this.taskIdBuilder_ == null)
/*  2489:      */         {
/*  2490: 2577 */           this.taskId_ = builderForValue.build();
/*  2491: 2578 */           onChanged();
/*  2492:      */         }
/*  2493:      */         else
/*  2494:      */         {
/*  2495: 2580 */           this.taskIdBuilder_.setMessage(builderForValue.build());
/*  2496:      */         }
/*  2497: 2582 */         this.bitField0_ |= 0x1;
/*  2498: 2583 */         return this;
/*  2499:      */       }
/*  2500:      */       
/*  2501:      */       public Builder mergeTaskId(MRProtos.TaskIdProto value)
/*  2502:      */       {
/*  2503: 2589 */         if (this.taskIdBuilder_ == null)
/*  2504:      */         {
/*  2505: 2590 */           if (((this.bitField0_ & 0x1) == 1) && (this.taskId_ != MRProtos.TaskIdProto.getDefaultInstance())) {
/*  2506: 2592 */             this.taskId_ = MRProtos.TaskIdProto.newBuilder(this.taskId_).mergeFrom(value).buildPartial();
/*  2507:      */           } else {
/*  2508: 2595 */             this.taskId_ = value;
/*  2509:      */           }
/*  2510: 2597 */           onChanged();
/*  2511:      */         }
/*  2512:      */         else
/*  2513:      */         {
/*  2514: 2599 */           this.taskIdBuilder_.mergeFrom(value);
/*  2515:      */         }
/*  2516: 2601 */         this.bitField0_ |= 0x1;
/*  2517: 2602 */         return this;
/*  2518:      */       }
/*  2519:      */       
/*  2520:      */       public Builder clearTaskId()
/*  2521:      */       {
/*  2522: 2608 */         if (this.taskIdBuilder_ == null)
/*  2523:      */         {
/*  2524: 2609 */           this.taskId_ = MRProtos.TaskIdProto.getDefaultInstance();
/*  2525: 2610 */           onChanged();
/*  2526:      */         }
/*  2527:      */         else
/*  2528:      */         {
/*  2529: 2612 */           this.taskIdBuilder_.clear();
/*  2530:      */         }
/*  2531: 2614 */         this.bitField0_ &= 0xFFFFFFFE;
/*  2532: 2615 */         return this;
/*  2533:      */       }
/*  2534:      */       
/*  2535:      */       public MRProtos.TaskIdProto.Builder getTaskIdBuilder()
/*  2536:      */       {
/*  2537: 2621 */         this.bitField0_ |= 0x1;
/*  2538: 2622 */         onChanged();
/*  2539: 2623 */         return (MRProtos.TaskIdProto.Builder)getTaskIdFieldBuilder().getBuilder();
/*  2540:      */       }
/*  2541:      */       
/*  2542:      */       public MRProtos.TaskIdProtoOrBuilder getTaskIdOrBuilder()
/*  2543:      */       {
/*  2544: 2629 */         if (this.taskIdBuilder_ != null) {
/*  2545: 2630 */           return (MRProtos.TaskIdProtoOrBuilder)this.taskIdBuilder_.getMessageOrBuilder();
/*  2546:      */         }
/*  2547: 2632 */         return this.taskId_;
/*  2548:      */       }
/*  2549:      */       
/*  2550:      */       private SingleFieldBuilder<MRProtos.TaskIdProto, MRProtos.TaskIdProto.Builder, MRProtos.TaskIdProtoOrBuilder> getTaskIdFieldBuilder()
/*  2551:      */       {
/*  2552: 2641 */         if (this.taskIdBuilder_ == null)
/*  2553:      */         {
/*  2554: 2642 */           this.taskIdBuilder_ = new SingleFieldBuilder(this.taskId_, getParentForChildren(), isClean());
/*  2555:      */           
/*  2556:      */ 
/*  2557:      */ 
/*  2558:      */ 
/*  2559: 2647 */           this.taskId_ = null;
/*  2560:      */         }
/*  2561: 2649 */         return this.taskIdBuilder_;
/*  2562:      */       }
/*  2563:      */       
/*  2564:      */       public boolean hasId()
/*  2565:      */       {
/*  2566: 2658 */         return (this.bitField0_ & 0x2) == 2;
/*  2567:      */       }
/*  2568:      */       
/*  2569:      */       public int getId()
/*  2570:      */       {
/*  2571: 2664 */         return this.id_;
/*  2572:      */       }
/*  2573:      */       
/*  2574:      */       public Builder setId(int value)
/*  2575:      */       {
/*  2576: 2670 */         this.bitField0_ |= 0x2;
/*  2577: 2671 */         this.id_ = value;
/*  2578: 2672 */         onChanged();
/*  2579: 2673 */         return this;
/*  2580:      */       }
/*  2581:      */       
/*  2582:      */       public Builder clearId()
/*  2583:      */       {
/*  2584: 2679 */         this.bitField0_ &= 0xFFFFFFFD;
/*  2585: 2680 */         this.id_ = 0;
/*  2586: 2681 */         onChanged();
/*  2587: 2682 */         return this;
/*  2588:      */       }
/*  2589:      */     }
/*  2590:      */     
/*  2591:      */     static
/*  2592:      */     {
/*  2593: 2689 */       defaultInstance = new TaskAttemptIdProto(true);
/*  2594: 2690 */       defaultInstance.initFields();
/*  2595:      */     }
/*  2596:      */   }
/*  2597:      */   
/*  2598:      */   public static abstract interface CounterProtoOrBuilder
/*  2599:      */     extends MessageOrBuilder
/*  2600:      */   {
/*  2601:      */     public abstract boolean hasName();
/*  2602:      */     
/*  2603:      */     public abstract String getName();
/*  2604:      */     
/*  2605:      */     public abstract ByteString getNameBytes();
/*  2606:      */     
/*  2607:      */     public abstract boolean hasDisplayName();
/*  2608:      */     
/*  2609:      */     public abstract String getDisplayName();
/*  2610:      */     
/*  2611:      */     public abstract ByteString getDisplayNameBytes();
/*  2612:      */     
/*  2613:      */     public abstract boolean hasValue();
/*  2614:      */     
/*  2615:      */     public abstract long getValue();
/*  2616:      */   }
/*  2617:      */   
/*  2618:      */   public static final class CounterProto
/*  2619:      */     extends GeneratedMessage
/*  2620:      */     implements MRProtos.CounterProtoOrBuilder
/*  2621:      */   {
/*  2622:      */     private static final CounterProto defaultInstance;
/*  2623:      */     private final UnknownFieldSet unknownFields;
/*  2624:      */     
/*  2625:      */     private CounterProto(GeneratedMessage.Builder<?> builder)
/*  2626:      */     {
/*  2627: 2747 */       super();
/*  2628: 2748 */       this.unknownFields = builder.getUnknownFields();
/*  2629:      */     }
/*  2630:      */     
/*  2631:      */     private CounterProto(boolean noInit)
/*  2632:      */     {
/*  2633: 2750 */       this.unknownFields = UnknownFieldSet.getDefaultInstance();
/*  2634:      */     }
/*  2635:      */     
/*  2636:      */     public static CounterProto getDefaultInstance()
/*  2637:      */     {
/*  2638: 2754 */       return defaultInstance;
/*  2639:      */     }
/*  2640:      */     
/*  2641:      */     public CounterProto getDefaultInstanceForType()
/*  2642:      */     {
/*  2643: 2758 */       return defaultInstance;
/*  2644:      */     }
/*  2645:      */     
/*  2646:      */     public final UnknownFieldSet getUnknownFields()
/*  2647:      */     {
/*  2648: 2765 */       return this.unknownFields;
/*  2649:      */     }
/*  2650:      */     
/*  2651:      */     private CounterProto(CodedInputStream input, ExtensionRegistryLite extensionRegistry)
/*  2652:      */       throws InvalidProtocolBufferException
/*  2653:      */     {
/*  2654: 2771 */       initFields();
/*  2655: 2772 */       int mutable_bitField0_ = 0;
/*  2656: 2773 */       UnknownFieldSet.Builder unknownFields = UnknownFieldSet.newBuilder();
/*  2657:      */       try
/*  2658:      */       {
/*  2659: 2776 */         boolean done = false;
/*  2660: 2777 */         while (!done)
/*  2661:      */         {
/*  2662: 2778 */           int tag = input.readTag();
/*  2663: 2779 */           switch (tag)
/*  2664:      */           {
/*  2665:      */           case 0: 
/*  2666: 2781 */             done = true;
/*  2667: 2782 */             break;
/*  2668:      */           default: 
/*  2669: 2784 */             if (!parseUnknownField(input, unknownFields, extensionRegistry, tag)) {
/*  2670: 2786 */               done = true;
/*  2671:      */             }
/*  2672:      */             break;
/*  2673:      */           case 10: 
/*  2674: 2791 */             this.bitField0_ |= 0x1;
/*  2675: 2792 */             this.name_ = input.readBytes();
/*  2676: 2793 */             break;
/*  2677:      */           case 18: 
/*  2678: 2796 */             this.bitField0_ |= 0x2;
/*  2679: 2797 */             this.displayName_ = input.readBytes();
/*  2680: 2798 */             break;
/*  2681:      */           case 24: 
/*  2682: 2801 */             this.bitField0_ |= 0x4;
/*  2683: 2802 */             this.value_ = input.readInt64();
/*  2684:      */           }
/*  2685:      */         }
/*  2686:      */       }
/*  2687:      */       catch (InvalidProtocolBufferException e)
/*  2688:      */       {
/*  2689: 2808 */         throw e.setUnfinishedMessage(this);
/*  2690:      */       }
/*  2691:      */       catch (IOException e)
/*  2692:      */       {
/*  2693: 2810 */         throw new InvalidProtocolBufferException(e.getMessage()).setUnfinishedMessage(this);
/*  2694:      */       }
/*  2695:      */       finally
/*  2696:      */       {
/*  2697: 2813 */         this.unknownFields = unknownFields.build();
/*  2698: 2814 */         makeExtensionsImmutable();
/*  2699:      */       }
/*  2700:      */     }
/*  2701:      */     
/*  2702:      */     public static final Descriptors.Descriptor getDescriptor()
/*  2703:      */     {
/*  2704: 2819 */       return MRProtos.internal_static_hadoop_mapreduce_CounterProto_descriptor;
/*  2705:      */     }
/*  2706:      */     
/*  2707:      */     protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable()
/*  2708:      */     {
/*  2709: 2824 */       return MRProtos.internal_static_hadoop_mapreduce_CounterProto_fieldAccessorTable.ensureFieldAccessorsInitialized(CounterProto.class, Builder.class);
/*  2710:      */     }
/*  2711:      */     
/*  2712: 2829 */     public static Parser<CounterProto> PARSER = new AbstractParser()
/*  2713:      */     {
/*  2714:      */       public MRProtos.CounterProto parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry)
/*  2715:      */         throws InvalidProtocolBufferException
/*  2716:      */       {
/*  2717: 2835 */         return new MRProtos.CounterProto(input, extensionRegistry, null);
/*  2718:      */       }
/*  2719:      */     };
/*  2720:      */     private int bitField0_;
/*  2721:      */     public static final int NAME_FIELD_NUMBER = 1;
/*  2722:      */     private Object name_;
/*  2723:      */     public static final int DISPLAY_NAME_FIELD_NUMBER = 2;
/*  2724:      */     private Object displayName_;
/*  2725:      */     public static final int VALUE_FIELD_NUMBER = 3;
/*  2726:      */     private long value_;
/*  2727:      */     
/*  2728:      */     public Parser<CounterProto> getParserForType()
/*  2729:      */     {
/*  2730: 2841 */       return PARSER;
/*  2731:      */     }
/*  2732:      */     
/*  2733:      */     public boolean hasName()
/*  2734:      */     {
/*  2735: 2852 */       return (this.bitField0_ & 0x1) == 1;
/*  2736:      */     }
/*  2737:      */     
/*  2738:      */     public String getName()
/*  2739:      */     {
/*  2740: 2858 */       Object ref = this.name_;
/*  2741: 2859 */       if ((ref instanceof String)) {
/*  2742: 2860 */         return (String)ref;
/*  2743:      */       }
/*  2744: 2862 */       ByteString bs = (ByteString)ref;
/*  2745:      */       
/*  2746: 2864 */       String s = bs.toStringUtf8();
/*  2747: 2865 */       if (bs.isValidUtf8()) {
/*  2748: 2866 */         this.name_ = s;
/*  2749:      */       }
/*  2750: 2868 */       return s;
/*  2751:      */     }
/*  2752:      */     
/*  2753:      */     public ByteString getNameBytes()
/*  2754:      */     {
/*  2755: 2876 */       Object ref = this.name_;
/*  2756: 2877 */       if ((ref instanceof String))
/*  2757:      */       {
/*  2758: 2878 */         ByteString b = ByteString.copyFromUtf8((String)ref);
/*  2759:      */         
/*  2760:      */ 
/*  2761: 2881 */         this.name_ = b;
/*  2762: 2882 */         return b;
/*  2763:      */       }
/*  2764: 2884 */       return (ByteString)ref;
/*  2765:      */     }
/*  2766:      */     
/*  2767:      */     public boolean hasDisplayName()
/*  2768:      */     {
/*  2769: 2895 */       return (this.bitField0_ & 0x2) == 2;
/*  2770:      */     }
/*  2771:      */     
/*  2772:      */     public String getDisplayName()
/*  2773:      */     {
/*  2774: 2901 */       Object ref = this.displayName_;
/*  2775: 2902 */       if ((ref instanceof String)) {
/*  2776: 2903 */         return (String)ref;
/*  2777:      */       }
/*  2778: 2905 */       ByteString bs = (ByteString)ref;
/*  2779:      */       
/*  2780: 2907 */       String s = bs.toStringUtf8();
/*  2781: 2908 */       if (bs.isValidUtf8()) {
/*  2782: 2909 */         this.displayName_ = s;
/*  2783:      */       }
/*  2784: 2911 */       return s;
/*  2785:      */     }
/*  2786:      */     
/*  2787:      */     public ByteString getDisplayNameBytes()
/*  2788:      */     {
/*  2789: 2919 */       Object ref = this.displayName_;
/*  2790: 2920 */       if ((ref instanceof String))
/*  2791:      */       {
/*  2792: 2921 */         ByteString b = ByteString.copyFromUtf8((String)ref);
/*  2793:      */         
/*  2794:      */ 
/*  2795: 2924 */         this.displayName_ = b;
/*  2796: 2925 */         return b;
/*  2797:      */       }
/*  2798: 2927 */       return (ByteString)ref;
/*  2799:      */     }
/*  2800:      */     
/*  2801:      */     public boolean hasValue()
/*  2802:      */     {
/*  2803: 2938 */       return (this.bitField0_ & 0x4) == 4;
/*  2804:      */     }
/*  2805:      */     
/*  2806:      */     public long getValue()
/*  2807:      */     {
/*  2808: 2944 */       return this.value_;
/*  2809:      */     }
/*  2810:      */     
/*  2811:      */     private void initFields()
/*  2812:      */     {
/*  2813: 2948 */       this.name_ = "";
/*  2814: 2949 */       this.displayName_ = "";
/*  2815: 2950 */       this.value_ = 0L;
/*  2816:      */     }
/*  2817:      */     
/*  2818: 2952 */     private byte memoizedIsInitialized = -1;
/*  2819:      */     
/*  2820:      */     public final boolean isInitialized()
/*  2821:      */     {
/*  2822: 2954 */       byte isInitialized = this.memoizedIsInitialized;
/*  2823: 2955 */       if (isInitialized != -1) {
/*  2824: 2955 */         return isInitialized == 1;
/*  2825:      */       }
/*  2826: 2957 */       this.memoizedIsInitialized = 1;
/*  2827: 2958 */       return true;
/*  2828:      */     }
/*  2829:      */     
/*  2830:      */     public void writeTo(CodedOutputStream output)
/*  2831:      */       throws IOException
/*  2832:      */     {
/*  2833: 2963 */       getSerializedSize();
/*  2834: 2964 */       if ((this.bitField0_ & 0x1) == 1) {
/*  2835: 2965 */         output.writeBytes(1, getNameBytes());
/*  2836:      */       }
/*  2837: 2967 */       if ((this.bitField0_ & 0x2) == 2) {
/*  2838: 2968 */         output.writeBytes(2, getDisplayNameBytes());
/*  2839:      */       }
/*  2840: 2970 */       if ((this.bitField0_ & 0x4) == 4) {
/*  2841: 2971 */         output.writeInt64(3, this.value_);
/*  2842:      */       }
/*  2843: 2973 */       getUnknownFields().writeTo(output);
/*  2844:      */     }
/*  2845:      */     
/*  2846: 2976 */     private int memoizedSerializedSize = -1;
/*  2847:      */     private static final long serialVersionUID = 0L;
/*  2848:      */     
/*  2849:      */     public int getSerializedSize()
/*  2850:      */     {
/*  2851: 2978 */       int size = this.memoizedSerializedSize;
/*  2852: 2979 */       if (size != -1) {
/*  2853: 2979 */         return size;
/*  2854:      */       }
/*  2855: 2981 */       size = 0;
/*  2856: 2982 */       if ((this.bitField0_ & 0x1) == 1) {
/*  2857: 2983 */         size += CodedOutputStream.computeBytesSize(1, getNameBytes());
/*  2858:      */       }
/*  2859: 2986 */       if ((this.bitField0_ & 0x2) == 2) {
/*  2860: 2987 */         size += CodedOutputStream.computeBytesSize(2, getDisplayNameBytes());
/*  2861:      */       }
/*  2862: 2990 */       if ((this.bitField0_ & 0x4) == 4) {
/*  2863: 2991 */         size += CodedOutputStream.computeInt64Size(3, this.value_);
/*  2864:      */       }
/*  2865: 2994 */       size += getUnknownFields().getSerializedSize();
/*  2866: 2995 */       this.memoizedSerializedSize = size;
/*  2867: 2996 */       return size;
/*  2868:      */     }
/*  2869:      */     
/*  2870:      */     protected Object writeReplace()
/*  2871:      */       throws ObjectStreamException
/*  2872:      */     {
/*  2873: 3003 */       return super.writeReplace();
/*  2874:      */     }
/*  2875:      */     
/*  2876:      */     public boolean equals(Object obj)
/*  2877:      */     {
/*  2878: 3008 */       if (obj == this) {
/*  2879: 3009 */         return true;
/*  2880:      */       }
/*  2881: 3011 */       if (!(obj instanceof CounterProto)) {
/*  2882: 3012 */         return super.equals(obj);
/*  2883:      */       }
/*  2884: 3014 */       CounterProto other = (CounterProto)obj;
/*  2885:      */       
/*  2886: 3016 */       boolean result = true;
/*  2887: 3017 */       result = (result) && (hasName() == other.hasName());
/*  2888: 3018 */       if (hasName()) {
/*  2889: 3019 */         result = (result) && (getName().equals(other.getName()));
/*  2890:      */       }
/*  2891: 3022 */       result = (result) && (hasDisplayName() == other.hasDisplayName());
/*  2892: 3023 */       if (hasDisplayName()) {
/*  2893: 3024 */         result = (result) && (getDisplayName().equals(other.getDisplayName()));
/*  2894:      */       }
/*  2895: 3027 */       result = (result) && (hasValue() == other.hasValue());
/*  2896: 3028 */       if (hasValue()) {
/*  2897: 3029 */         result = (result) && (getValue() == other.getValue());
/*  2898:      */       }
/*  2899: 3032 */       result = (result) && (getUnknownFields().equals(other.getUnknownFields()));
/*  2900:      */       
/*  2901: 3034 */       return result;
/*  2902:      */     }
/*  2903:      */     
/*  2904: 3037 */     private int memoizedHashCode = 0;
/*  2905:      */     
/*  2906:      */     public int hashCode()
/*  2907:      */     {
/*  2908: 3040 */       if (this.memoizedHashCode != 0) {
/*  2909: 3041 */         return this.memoizedHashCode;
/*  2910:      */       }
/*  2911: 3043 */       int hash = 41;
/*  2912: 3044 */       hash = 19 * hash + getDescriptorForType().hashCode();
/*  2913: 3045 */       if (hasName())
/*  2914:      */       {
/*  2915: 3046 */         hash = 37 * hash + 1;
/*  2916: 3047 */         hash = 53 * hash + getName().hashCode();
/*  2917:      */       }
/*  2918: 3049 */       if (hasDisplayName())
/*  2919:      */       {
/*  2920: 3050 */         hash = 37 * hash + 2;
/*  2921: 3051 */         hash = 53 * hash + getDisplayName().hashCode();
/*  2922:      */       }
/*  2923: 3053 */       if (hasValue())
/*  2924:      */       {
/*  2925: 3054 */         hash = 37 * hash + 3;
/*  2926: 3055 */         hash = 53 * hash + hashLong(getValue());
/*  2927:      */       }
/*  2928: 3057 */       hash = 29 * hash + getUnknownFields().hashCode();
/*  2929: 3058 */       this.memoizedHashCode = hash;
/*  2930: 3059 */       return hash;
/*  2931:      */     }
/*  2932:      */     
/*  2933:      */     public static CounterProto parseFrom(ByteString data)
/*  2934:      */       throws InvalidProtocolBufferException
/*  2935:      */     {
/*  2936: 3065 */       return (CounterProto)PARSER.parseFrom(data);
/*  2937:      */     }
/*  2938:      */     
/*  2939:      */     public static CounterProto parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry)
/*  2940:      */       throws InvalidProtocolBufferException
/*  2941:      */     {
/*  2942: 3071 */       return (CounterProto)PARSER.parseFrom(data, extensionRegistry);
/*  2943:      */     }
/*  2944:      */     
/*  2945:      */     public static CounterProto parseFrom(byte[] data)
/*  2946:      */       throws InvalidProtocolBufferException
/*  2947:      */     {
/*  2948: 3075 */       return (CounterProto)PARSER.parseFrom(data);
/*  2949:      */     }
/*  2950:      */     
/*  2951:      */     public static CounterProto parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry)
/*  2952:      */       throws InvalidProtocolBufferException
/*  2953:      */     {
/*  2954: 3081 */       return (CounterProto)PARSER.parseFrom(data, extensionRegistry);
/*  2955:      */     }
/*  2956:      */     
/*  2957:      */     public static CounterProto parseFrom(InputStream input)
/*  2958:      */       throws IOException
/*  2959:      */     {
/*  2960: 3085 */       return (CounterProto)PARSER.parseFrom(input);
/*  2961:      */     }
/*  2962:      */     
/*  2963:      */     public static CounterProto parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry)
/*  2964:      */       throws IOException
/*  2965:      */     {
/*  2966: 3091 */       return (CounterProto)PARSER.parseFrom(input, extensionRegistry);
/*  2967:      */     }
/*  2968:      */     
/*  2969:      */     public static CounterProto parseDelimitedFrom(InputStream input)
/*  2970:      */       throws IOException
/*  2971:      */     {
/*  2972: 3095 */       return (CounterProto)PARSER.parseDelimitedFrom(input);
/*  2973:      */     }
/*  2974:      */     
/*  2975:      */     public static CounterProto parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry)
/*  2976:      */       throws IOException
/*  2977:      */     {
/*  2978: 3101 */       return (CounterProto)PARSER.parseDelimitedFrom(input, extensionRegistry);
/*  2979:      */     }
/*  2980:      */     
/*  2981:      */     public static CounterProto parseFrom(CodedInputStream input)
/*  2982:      */       throws IOException
/*  2983:      */     {
/*  2984: 3106 */       return (CounterProto)PARSER.parseFrom(input);
/*  2985:      */     }
/*  2986:      */     
/*  2987:      */     public static CounterProto parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry)
/*  2988:      */       throws IOException
/*  2989:      */     {
/*  2990: 3112 */       return (CounterProto)PARSER.parseFrom(input, extensionRegistry);
/*  2991:      */     }
/*  2992:      */     
/*  2993:      */     public static Builder newBuilder()
/*  2994:      */     {
/*  2995: 3115 */       return Builder.access$3400();
/*  2996:      */     }
/*  2997:      */     
/*  2998:      */     public Builder newBuilderForType()
/*  2999:      */     {
/*  3000: 3116 */       return newBuilder();
/*  3001:      */     }
/*  3002:      */     
/*  3003:      */     public static Builder newBuilder(CounterProto prototype)
/*  3004:      */     {
/*  3005: 3118 */       return newBuilder().mergeFrom(prototype);
/*  3006:      */     }
/*  3007:      */     
/*  3008:      */     public Builder toBuilder()
/*  3009:      */     {
/*  3010: 3120 */       return newBuilder(this);
/*  3011:      */     }
/*  3012:      */     
/*  3013:      */     protected Builder newBuilderForType(GeneratedMessage.BuilderParent parent)
/*  3014:      */     {
/*  3015: 3125 */       Builder builder = new Builder(parent, null);
/*  3016: 3126 */       return builder;
/*  3017:      */     }
/*  3018:      */     
/*  3019:      */     public static final class Builder
/*  3020:      */       extends GeneratedMessage.Builder<Builder>
/*  3021:      */       implements MRProtos.CounterProtoOrBuilder
/*  3022:      */     {
/*  3023:      */       private int bitField0_;
/*  3024:      */       
/*  3025:      */       public static final Descriptors.Descriptor getDescriptor()
/*  3026:      */       {
/*  3027: 3136 */         return MRProtos.internal_static_hadoop_mapreduce_CounterProto_descriptor;
/*  3028:      */       }
/*  3029:      */       
/*  3030:      */       protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable()
/*  3031:      */       {
/*  3032: 3141 */         return MRProtos.internal_static_hadoop_mapreduce_CounterProto_fieldAccessorTable.ensureFieldAccessorsInitialized(MRProtos.CounterProto.class, Builder.class);
/*  3033:      */       }
/*  3034:      */       
/*  3035:      */       private Builder()
/*  3036:      */       {
/*  3037: 3148 */         maybeForceBuilderInitialization();
/*  3038:      */       }
/*  3039:      */       
/*  3040:      */       private Builder(GeneratedMessage.BuilderParent parent)
/*  3041:      */       {
/*  3042: 3153 */         super();
/*  3043: 3154 */         maybeForceBuilderInitialization();
/*  3044:      */       }
/*  3045:      */       
/*  3046:      */       private void maybeForceBuilderInitialization()
/*  3047:      */       {
/*  3048: 3157 */         if (MRProtos.CounterProto.alwaysUseFieldBuilders) {}
/*  3049:      */       }
/*  3050:      */       
/*  3051:      */       private static Builder create()
/*  3052:      */       {
/*  3053: 3161 */         return new Builder();
/*  3054:      */       }
/*  3055:      */       
/*  3056:      */       public Builder clear()
/*  3057:      */       {
/*  3058: 3165 */         super.clear();
/*  3059: 3166 */         this.name_ = "";
/*  3060: 3167 */         this.bitField0_ &= 0xFFFFFFFE;
/*  3061: 3168 */         this.displayName_ = "";
/*  3062: 3169 */         this.bitField0_ &= 0xFFFFFFFD;
/*  3063: 3170 */         this.value_ = 0L;
/*  3064: 3171 */         this.bitField0_ &= 0xFFFFFFFB;
/*  3065: 3172 */         return this;
/*  3066:      */       }
/*  3067:      */       
/*  3068:      */       public Builder clone()
/*  3069:      */       {
/*  3070: 3176 */         return create().mergeFrom(buildPartial());
/*  3071:      */       }
/*  3072:      */       
/*  3073:      */       public Descriptors.Descriptor getDescriptorForType()
/*  3074:      */       {
/*  3075: 3181 */         return MRProtos.internal_static_hadoop_mapreduce_CounterProto_descriptor;
/*  3076:      */       }
/*  3077:      */       
/*  3078:      */       public MRProtos.CounterProto getDefaultInstanceForType()
/*  3079:      */       {
/*  3080: 3185 */         return MRProtos.CounterProto.getDefaultInstance();
/*  3081:      */       }
/*  3082:      */       
/*  3083:      */       public MRProtos.CounterProto build()
/*  3084:      */       {
/*  3085: 3189 */         MRProtos.CounterProto result = buildPartial();
/*  3086: 3190 */         if (!result.isInitialized()) {
/*  3087: 3191 */           throw newUninitializedMessageException(result);
/*  3088:      */         }
/*  3089: 3193 */         return result;
/*  3090:      */       }
/*  3091:      */       
/*  3092:      */       public MRProtos.CounterProto buildPartial()
/*  3093:      */       {
/*  3094: 3197 */         MRProtos.CounterProto result = new MRProtos.CounterProto(this, null);
/*  3095: 3198 */         int from_bitField0_ = this.bitField0_;
/*  3096: 3199 */         int to_bitField0_ = 0;
/*  3097: 3200 */         if ((from_bitField0_ & 0x1) == 1) {
/*  3098: 3201 */           to_bitField0_ |= 0x1;
/*  3099:      */         }
/*  3100: 3203 */         result.name_ = this.name_;
/*  3101: 3204 */         if ((from_bitField0_ & 0x2) == 2) {
/*  3102: 3205 */           to_bitField0_ |= 0x2;
/*  3103:      */         }
/*  3104: 3207 */         result.displayName_ = this.displayName_;
/*  3105: 3208 */         if ((from_bitField0_ & 0x4) == 4) {
/*  3106: 3209 */           to_bitField0_ |= 0x4;
/*  3107:      */         }
/*  3108: 3211 */         result.value_ = this.value_;
/*  3109: 3212 */         result.bitField0_ = to_bitField0_;
/*  3110: 3213 */         onBuilt();
/*  3111: 3214 */         return result;
/*  3112:      */       }
/*  3113:      */       
/*  3114:      */       public Builder mergeFrom(Message other)
/*  3115:      */       {
/*  3116: 3218 */         if ((other instanceof MRProtos.CounterProto)) {
/*  3117: 3219 */           return mergeFrom((MRProtos.CounterProto)other);
/*  3118:      */         }
/*  3119: 3221 */         super.mergeFrom(other);
/*  3120: 3222 */         return this;
/*  3121:      */       }
/*  3122:      */       
/*  3123:      */       public Builder mergeFrom(MRProtos.CounterProto other)
/*  3124:      */       {
/*  3125: 3227 */         if (other == MRProtos.CounterProto.getDefaultInstance()) {
/*  3126: 3227 */           return this;
/*  3127:      */         }
/*  3128: 3228 */         if (other.hasName())
/*  3129:      */         {
/*  3130: 3229 */           this.bitField0_ |= 0x1;
/*  3131: 3230 */           this.name_ = other.name_;
/*  3132: 3231 */           onChanged();
/*  3133:      */         }
/*  3134: 3233 */         if (other.hasDisplayName())
/*  3135:      */         {
/*  3136: 3234 */           this.bitField0_ |= 0x2;
/*  3137: 3235 */           this.displayName_ = other.displayName_;
/*  3138: 3236 */           onChanged();
/*  3139:      */         }
/*  3140: 3238 */         if (other.hasValue()) {
/*  3141: 3239 */           setValue(other.getValue());
/*  3142:      */         }
/*  3143: 3241 */         mergeUnknownFields(other.getUnknownFields());
/*  3144: 3242 */         return this;
/*  3145:      */       }
/*  3146:      */       
/*  3147:      */       public final boolean isInitialized()
/*  3148:      */       {
/*  3149: 3246 */         return true;
/*  3150:      */       }
/*  3151:      */       
/*  3152:      */       public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry)
/*  3153:      */         throws IOException
/*  3154:      */       {
/*  3155: 3253 */         MRProtos.CounterProto parsedMessage = null;
/*  3156:      */         try
/*  3157:      */         {
/*  3158: 3255 */           parsedMessage = (MRProtos.CounterProto)MRProtos.CounterProto.PARSER.parsePartialFrom(input, extensionRegistry);
/*  3159:      */         }
/*  3160:      */         catch (InvalidProtocolBufferException e)
/*  3161:      */         {
/*  3162: 3257 */           parsedMessage = (MRProtos.CounterProto)e.getUnfinishedMessage();
/*  3163: 3258 */           throw e;
/*  3164:      */         }
/*  3165:      */         finally
/*  3166:      */         {
/*  3167: 3260 */           if (parsedMessage != null) {
/*  3168: 3261 */             mergeFrom(parsedMessage);
/*  3169:      */           }
/*  3170:      */         }
/*  3171: 3264 */         return this;
/*  3172:      */       }
/*  3173:      */       
/*  3174: 3269 */       private Object name_ = "";
/*  3175:      */       
/*  3176:      */       public boolean hasName()
/*  3177:      */       {
/*  3178: 3274 */         return (this.bitField0_ & 0x1) == 1;
/*  3179:      */       }
/*  3180:      */       
/*  3181:      */       public String getName()
/*  3182:      */       {
/*  3183: 3280 */         Object ref = this.name_;
/*  3184: 3281 */         if (!(ref instanceof String))
/*  3185:      */         {
/*  3186: 3282 */           String s = ((ByteString)ref).toStringUtf8();
/*  3187:      */           
/*  3188: 3284 */           this.name_ = s;
/*  3189: 3285 */           return s;
/*  3190:      */         }
/*  3191: 3287 */         return (String)ref;
/*  3192:      */       }
/*  3193:      */       
/*  3194:      */       public ByteString getNameBytes()
/*  3195:      */       {
/*  3196: 3295 */         Object ref = this.name_;
/*  3197: 3296 */         if ((ref instanceof String))
/*  3198:      */         {
/*  3199: 3297 */           ByteString b = ByteString.copyFromUtf8((String)ref);
/*  3200:      */           
/*  3201:      */ 
/*  3202: 3300 */           this.name_ = b;
/*  3203: 3301 */           return b;
/*  3204:      */         }
/*  3205: 3303 */         return (ByteString)ref;
/*  3206:      */       }
/*  3207:      */       
/*  3208:      */       public Builder setName(String value)
/*  3209:      */       {
/*  3210: 3311 */         if (value == null) {
/*  3211: 3312 */           throw new NullPointerException();
/*  3212:      */         }
/*  3213: 3314 */         this.bitField0_ |= 0x1;
/*  3214: 3315 */         this.name_ = value;
/*  3215: 3316 */         onChanged();
/*  3216: 3317 */         return this;
/*  3217:      */       }
/*  3218:      */       
/*  3219:      */       public Builder clearName()
/*  3220:      */       {
/*  3221: 3323 */         this.bitField0_ &= 0xFFFFFFFE;
/*  3222: 3324 */         this.name_ = MRProtos.CounterProto.getDefaultInstance().getName();
/*  3223: 3325 */         onChanged();
/*  3224: 3326 */         return this;
/*  3225:      */       }
/*  3226:      */       
/*  3227:      */       public Builder setNameBytes(ByteString value)
/*  3228:      */       {
/*  3229: 3333 */         if (value == null) {
/*  3230: 3334 */           throw new NullPointerException();
/*  3231:      */         }
/*  3232: 3336 */         this.bitField0_ |= 0x1;
/*  3233: 3337 */         this.name_ = value;
/*  3234: 3338 */         onChanged();
/*  3235: 3339 */         return this;
/*  3236:      */       }
/*  3237:      */       
/*  3238: 3343 */       private Object displayName_ = "";
/*  3239:      */       private long value_;
/*  3240:      */       
/*  3241:      */       public boolean hasDisplayName()
/*  3242:      */       {
/*  3243: 3348 */         return (this.bitField0_ & 0x2) == 2;
/*  3244:      */       }
/*  3245:      */       
/*  3246:      */       public String getDisplayName()
/*  3247:      */       {
/*  3248: 3354 */         Object ref = this.displayName_;
/*  3249: 3355 */         if (!(ref instanceof String))
/*  3250:      */         {
/*  3251: 3356 */           String s = ((ByteString)ref).toStringUtf8();
/*  3252:      */           
/*  3253: 3358 */           this.displayName_ = s;
/*  3254: 3359 */           return s;
/*  3255:      */         }
/*  3256: 3361 */         return (String)ref;
/*  3257:      */       }
/*  3258:      */       
/*  3259:      */       public ByteString getDisplayNameBytes()
/*  3260:      */       {
/*  3261: 3369 */         Object ref = this.displayName_;
/*  3262: 3370 */         if ((ref instanceof String))
/*  3263:      */         {
/*  3264: 3371 */           ByteString b = ByteString.copyFromUtf8((String)ref);
/*  3265:      */           
/*  3266:      */ 
/*  3267: 3374 */           this.displayName_ = b;
/*  3268: 3375 */           return b;
/*  3269:      */         }
/*  3270: 3377 */         return (ByteString)ref;
/*  3271:      */       }
/*  3272:      */       
/*  3273:      */       public Builder setDisplayName(String value)
/*  3274:      */       {
/*  3275: 3385 */         if (value == null) {
/*  3276: 3386 */           throw new NullPointerException();
/*  3277:      */         }
/*  3278: 3388 */         this.bitField0_ |= 0x2;
/*  3279: 3389 */         this.displayName_ = value;
/*  3280: 3390 */         onChanged();
/*  3281: 3391 */         return this;
/*  3282:      */       }
/*  3283:      */       
/*  3284:      */       public Builder clearDisplayName()
/*  3285:      */       {
/*  3286: 3397 */         this.bitField0_ &= 0xFFFFFFFD;
/*  3287: 3398 */         this.displayName_ = MRProtos.CounterProto.getDefaultInstance().getDisplayName();
/*  3288: 3399 */         onChanged();
/*  3289: 3400 */         return this;
/*  3290:      */       }
/*  3291:      */       
/*  3292:      */       public Builder setDisplayNameBytes(ByteString value)
/*  3293:      */       {
/*  3294: 3407 */         if (value == null) {
/*  3295: 3408 */           throw new NullPointerException();
/*  3296:      */         }
/*  3297: 3410 */         this.bitField0_ |= 0x2;
/*  3298: 3411 */         this.displayName_ = value;
/*  3299: 3412 */         onChanged();
/*  3300: 3413 */         return this;
/*  3301:      */       }
/*  3302:      */       
/*  3303:      */       public boolean hasValue()
/*  3304:      */       {
/*  3305: 3422 */         return (this.bitField0_ & 0x4) == 4;
/*  3306:      */       }
/*  3307:      */       
/*  3308:      */       public long getValue()
/*  3309:      */       {
/*  3310: 3428 */         return this.value_;
/*  3311:      */       }
/*  3312:      */       
/*  3313:      */       public Builder setValue(long value)
/*  3314:      */       {
/*  3315: 3434 */         this.bitField0_ |= 0x4;
/*  3316: 3435 */         this.value_ = value;
/*  3317: 3436 */         onChanged();
/*  3318: 3437 */         return this;
/*  3319:      */       }
/*  3320:      */       
/*  3321:      */       public Builder clearValue()
/*  3322:      */       {
/*  3323: 3443 */         this.bitField0_ &= 0xFFFFFFFB;
/*  3324: 3444 */         this.value_ = 0L;
/*  3325: 3445 */         onChanged();
/*  3326: 3446 */         return this;
/*  3327:      */       }
/*  3328:      */     }
/*  3329:      */     
/*  3330:      */     static
/*  3331:      */     {
/*  3332: 3453 */       defaultInstance = new CounterProto(true);
/*  3333: 3454 */       defaultInstance.initFields();
/*  3334:      */     }
/*  3335:      */   }
/*  3336:      */   
/*  3337:      */   public static abstract interface CounterGroupProtoOrBuilder
/*  3338:      */     extends MessageOrBuilder
/*  3339:      */   {
/*  3340:      */     public abstract boolean hasName();
/*  3341:      */     
/*  3342:      */     public abstract String getName();
/*  3343:      */     
/*  3344:      */     public abstract ByteString getNameBytes();
/*  3345:      */     
/*  3346:      */     public abstract boolean hasDisplayName();
/*  3347:      */     
/*  3348:      */     public abstract String getDisplayName();
/*  3349:      */     
/*  3350:      */     public abstract ByteString getDisplayNameBytes();
/*  3351:      */     
/*  3352:      */     public abstract List<MRProtos.StringCounterMapProto> getCountersList();
/*  3353:      */     
/*  3354:      */     public abstract MRProtos.StringCounterMapProto getCounters(int paramInt);
/*  3355:      */     
/*  3356:      */     public abstract int getCountersCount();
/*  3357:      */     
/*  3358:      */     public abstract List<? extends MRProtos.StringCounterMapProtoOrBuilder> getCountersOrBuilderList();
/*  3359:      */     
/*  3360:      */     public abstract MRProtos.StringCounterMapProtoOrBuilder getCountersOrBuilder(int paramInt);
/*  3361:      */   }
/*  3362:      */   
/*  3363:      */   public static final class CounterGroupProto
/*  3364:      */     extends GeneratedMessage
/*  3365:      */     implements MRProtos.CounterGroupProtoOrBuilder
/*  3366:      */   {
/*  3367:      */     private static final CounterGroupProto defaultInstance;
/*  3368:      */     private final UnknownFieldSet unknownFields;
/*  3369:      */     
/*  3370:      */     private CounterGroupProto(GeneratedMessage.Builder<?> builder)
/*  3371:      */     {
/*  3372: 3526 */       super();
/*  3373: 3527 */       this.unknownFields = builder.getUnknownFields();
/*  3374:      */     }
/*  3375:      */     
/*  3376:      */     private CounterGroupProto(boolean noInit)
/*  3377:      */     {
/*  3378: 3529 */       this.unknownFields = UnknownFieldSet.getDefaultInstance();
/*  3379:      */     }
/*  3380:      */     
/*  3381:      */     public static CounterGroupProto getDefaultInstance()
/*  3382:      */     {
/*  3383: 3533 */       return defaultInstance;
/*  3384:      */     }
/*  3385:      */     
/*  3386:      */     public CounterGroupProto getDefaultInstanceForType()
/*  3387:      */     {
/*  3388: 3537 */       return defaultInstance;
/*  3389:      */     }
/*  3390:      */     
/*  3391:      */     public final UnknownFieldSet getUnknownFields()
/*  3392:      */     {
/*  3393: 3544 */       return this.unknownFields;
/*  3394:      */     }
/*  3395:      */     
/*  3396:      */     private CounterGroupProto(CodedInputStream input, ExtensionRegistryLite extensionRegistry)
/*  3397:      */       throws InvalidProtocolBufferException
/*  3398:      */     {
/*  3399: 3550 */       initFields();
/*  3400: 3551 */       int mutable_bitField0_ = 0;
/*  3401: 3552 */       UnknownFieldSet.Builder unknownFields = UnknownFieldSet.newBuilder();
/*  3402:      */       try
/*  3403:      */       {
/*  3404: 3555 */         boolean done = false;
/*  3405: 3556 */         while (!done)
/*  3406:      */         {
/*  3407: 3557 */           int tag = input.readTag();
/*  3408: 3558 */           switch (tag)
/*  3409:      */           {
/*  3410:      */           case 0: 
/*  3411: 3560 */             done = true;
/*  3412: 3561 */             break;
/*  3413:      */           default: 
/*  3414: 3563 */             if (!parseUnknownField(input, unknownFields, extensionRegistry, tag)) {
/*  3415: 3565 */               done = true;
/*  3416:      */             }
/*  3417:      */             break;
/*  3418:      */           case 10: 
/*  3419: 3570 */             this.bitField0_ |= 0x1;
/*  3420: 3571 */             this.name_ = input.readBytes();
/*  3421: 3572 */             break;
/*  3422:      */           case 18: 
/*  3423: 3575 */             this.bitField0_ |= 0x2;
/*  3424: 3576 */             this.displayName_ = input.readBytes();
/*  3425: 3577 */             break;
/*  3426:      */           case 26: 
/*  3427: 3580 */             if ((mutable_bitField0_ & 0x4) != 4)
/*  3428:      */             {
/*  3429: 3581 */               this.counters_ = new ArrayList();
/*  3430: 3582 */               mutable_bitField0_ |= 0x4;
/*  3431:      */             }
/*  3432: 3584 */             this.counters_.add(input.readMessage(MRProtos.StringCounterMapProto.PARSER, extensionRegistry));
/*  3433:      */           }
/*  3434:      */         }
/*  3435:      */       }
/*  3436:      */       catch (InvalidProtocolBufferException e)
/*  3437:      */       {
/*  3438: 3590 */         throw e.setUnfinishedMessage(this);
/*  3439:      */       }
/*  3440:      */       catch (IOException e)
/*  3441:      */       {
/*  3442: 3592 */         throw new InvalidProtocolBufferException(e.getMessage()).setUnfinishedMessage(this);
/*  3443:      */       }
/*  3444:      */       finally
/*  3445:      */       {
/*  3446: 3595 */         if ((mutable_bitField0_ & 0x4) == 4) {
/*  3447: 3596 */           this.counters_ = Collections.unmodifiableList(this.counters_);
/*  3448:      */         }
/*  3449: 3598 */         this.unknownFields = unknownFields.build();
/*  3450: 3599 */         makeExtensionsImmutable();
/*  3451:      */       }
/*  3452:      */     }
/*  3453:      */     
/*  3454:      */     public static final Descriptors.Descriptor getDescriptor()
/*  3455:      */     {
/*  3456: 3604 */       return MRProtos.internal_static_hadoop_mapreduce_CounterGroupProto_descriptor;
/*  3457:      */     }
/*  3458:      */     
/*  3459:      */     protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable()
/*  3460:      */     {
/*  3461: 3609 */       return MRProtos.internal_static_hadoop_mapreduce_CounterGroupProto_fieldAccessorTable.ensureFieldAccessorsInitialized(CounterGroupProto.class, Builder.class);
/*  3462:      */     }
/*  3463:      */     
/*  3464: 3614 */     public static Parser<CounterGroupProto> PARSER = new AbstractParser()
/*  3465:      */     {
/*  3466:      */       public MRProtos.CounterGroupProto parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry)
/*  3467:      */         throws InvalidProtocolBufferException
/*  3468:      */       {
/*  3469: 3620 */         return new MRProtos.CounterGroupProto(input, extensionRegistry, null);
/*  3470:      */       }
/*  3471:      */     };
/*  3472:      */     private int bitField0_;
/*  3473:      */     public static final int NAME_FIELD_NUMBER = 1;
/*  3474:      */     private Object name_;
/*  3475:      */     public static final int DISPLAY_NAME_FIELD_NUMBER = 2;
/*  3476:      */     private Object displayName_;
/*  3477:      */     public static final int COUNTERS_FIELD_NUMBER = 3;
/*  3478:      */     private List<MRProtos.StringCounterMapProto> counters_;
/*  3479:      */     
/*  3480:      */     public Parser<CounterGroupProto> getParserForType()
/*  3481:      */     {
/*  3482: 3626 */       return PARSER;
/*  3483:      */     }
/*  3484:      */     
/*  3485:      */     public boolean hasName()
/*  3486:      */     {
/*  3487: 3637 */       return (this.bitField0_ & 0x1) == 1;
/*  3488:      */     }
/*  3489:      */     
/*  3490:      */     public String getName()
/*  3491:      */     {
/*  3492: 3643 */       Object ref = this.name_;
/*  3493: 3644 */       if ((ref instanceof String)) {
/*  3494: 3645 */         return (String)ref;
/*  3495:      */       }
/*  3496: 3647 */       ByteString bs = (ByteString)ref;
/*  3497:      */       
/*  3498: 3649 */       String s = bs.toStringUtf8();
/*  3499: 3650 */       if (bs.isValidUtf8()) {
/*  3500: 3651 */         this.name_ = s;
/*  3501:      */       }
/*  3502: 3653 */       return s;
/*  3503:      */     }
/*  3504:      */     
/*  3505:      */     public ByteString getNameBytes()
/*  3506:      */     {
/*  3507: 3661 */       Object ref = this.name_;
/*  3508: 3662 */       if ((ref instanceof String))
/*  3509:      */       {
/*  3510: 3663 */         ByteString b = ByteString.copyFromUtf8((String)ref);
/*  3511:      */         
/*  3512:      */ 
/*  3513: 3666 */         this.name_ = b;
/*  3514: 3667 */         return b;
/*  3515:      */       }
/*  3516: 3669 */       return (ByteString)ref;
/*  3517:      */     }
/*  3518:      */     
/*  3519:      */     public boolean hasDisplayName()
/*  3520:      */     {
/*  3521: 3680 */       return (this.bitField0_ & 0x2) == 2;
/*  3522:      */     }
/*  3523:      */     
/*  3524:      */     public String getDisplayName()
/*  3525:      */     {
/*  3526: 3686 */       Object ref = this.displayName_;
/*  3527: 3687 */       if ((ref instanceof String)) {
/*  3528: 3688 */         return (String)ref;
/*  3529:      */       }
/*  3530: 3690 */       ByteString bs = (ByteString)ref;
/*  3531:      */       
/*  3532: 3692 */       String s = bs.toStringUtf8();
/*  3533: 3693 */       if (bs.isValidUtf8()) {
/*  3534: 3694 */         this.displayName_ = s;
/*  3535:      */       }
/*  3536: 3696 */       return s;
/*  3537:      */     }
/*  3538:      */     
/*  3539:      */     public ByteString getDisplayNameBytes()
/*  3540:      */     {
/*  3541: 3704 */       Object ref = this.displayName_;
/*  3542: 3705 */       if ((ref instanceof String))
/*  3543:      */       {
/*  3544: 3706 */         ByteString b = ByteString.copyFromUtf8((String)ref);
/*  3545:      */         
/*  3546:      */ 
/*  3547: 3709 */         this.displayName_ = b;
/*  3548: 3710 */         return b;
/*  3549:      */       }
/*  3550: 3712 */       return (ByteString)ref;
/*  3551:      */     }
/*  3552:      */     
/*  3553:      */     public List<MRProtos.StringCounterMapProto> getCountersList()
/*  3554:      */     {
/*  3555: 3723 */       return this.counters_;
/*  3556:      */     }
/*  3557:      */     
/*  3558:      */     public List<? extends MRProtos.StringCounterMapProtoOrBuilder> getCountersOrBuilderList()
/*  3559:      */     {
/*  3560: 3730 */       return this.counters_;
/*  3561:      */     }
/*  3562:      */     
/*  3563:      */     public int getCountersCount()
/*  3564:      */     {
/*  3565: 3736 */       return this.counters_.size();
/*  3566:      */     }
/*  3567:      */     
/*  3568:      */     public MRProtos.StringCounterMapProto getCounters(int index)
/*  3569:      */     {
/*  3570: 3742 */       return (MRProtos.StringCounterMapProto)this.counters_.get(index);
/*  3571:      */     }
/*  3572:      */     
/*  3573:      */     public MRProtos.StringCounterMapProtoOrBuilder getCountersOrBuilder(int index)
/*  3574:      */     {
/*  3575: 3749 */       return (MRProtos.StringCounterMapProtoOrBuilder)this.counters_.get(index);
/*  3576:      */     }
/*  3577:      */     
/*  3578:      */     private void initFields()
/*  3579:      */     {
/*  3580: 3753 */       this.name_ = "";
/*  3581: 3754 */       this.displayName_ = "";
/*  3582: 3755 */       this.counters_ = Collections.emptyList();
/*  3583:      */     }
/*  3584:      */     
/*  3585: 3757 */     private byte memoizedIsInitialized = -1;
/*  3586:      */     
/*  3587:      */     public final boolean isInitialized()
/*  3588:      */     {
/*  3589: 3759 */       byte isInitialized = this.memoizedIsInitialized;
/*  3590: 3760 */       if (isInitialized != -1) {
/*  3591: 3760 */         return isInitialized == 1;
/*  3592:      */       }
/*  3593: 3762 */       this.memoizedIsInitialized = 1;
/*  3594: 3763 */       return true;
/*  3595:      */     }
/*  3596:      */     
/*  3597:      */     public void writeTo(CodedOutputStream output)
/*  3598:      */       throws IOException
/*  3599:      */     {
/*  3600: 3768 */       getSerializedSize();
/*  3601: 3769 */       if ((this.bitField0_ & 0x1) == 1) {
/*  3602: 3770 */         output.writeBytes(1, getNameBytes());
/*  3603:      */       }
/*  3604: 3772 */       if ((this.bitField0_ & 0x2) == 2) {
/*  3605: 3773 */         output.writeBytes(2, getDisplayNameBytes());
/*  3606:      */       }
/*  3607: 3775 */       for (int i = 0; i < this.counters_.size(); i++) {
/*  3608: 3776 */         output.writeMessage(3, (MessageLite)this.counters_.get(i));
/*  3609:      */       }
/*  3610: 3778 */       getUnknownFields().writeTo(output);
/*  3611:      */     }
/*  3612:      */     
/*  3613: 3781 */     private int memoizedSerializedSize = -1;
/*  3614:      */     private static final long serialVersionUID = 0L;
/*  3615:      */     
/*  3616:      */     public int getSerializedSize()
/*  3617:      */     {
/*  3618: 3783 */       int size = this.memoizedSerializedSize;
/*  3619: 3784 */       if (size != -1) {
/*  3620: 3784 */         return size;
/*  3621:      */       }
/*  3622: 3786 */       size = 0;
/*  3623: 3787 */       if ((this.bitField0_ & 0x1) == 1) {
/*  3624: 3788 */         size += CodedOutputStream.computeBytesSize(1, getNameBytes());
/*  3625:      */       }
/*  3626: 3791 */       if ((this.bitField0_ & 0x2) == 2) {
/*  3627: 3792 */         size += CodedOutputStream.computeBytesSize(2, getDisplayNameBytes());
/*  3628:      */       }
/*  3629: 3795 */       for (int i = 0; i < this.counters_.size(); i++) {
/*  3630: 3796 */         size += CodedOutputStream.computeMessageSize(3, (MessageLite)this.counters_.get(i));
/*  3631:      */       }
/*  3632: 3799 */       size += getUnknownFields().getSerializedSize();
/*  3633: 3800 */       this.memoizedSerializedSize = size;
/*  3634: 3801 */       return size;
/*  3635:      */     }
/*  3636:      */     
/*  3637:      */     protected Object writeReplace()
/*  3638:      */       throws ObjectStreamException
/*  3639:      */     {
/*  3640: 3808 */       return super.writeReplace();
/*  3641:      */     }
/*  3642:      */     
/*  3643:      */     public boolean equals(Object obj)
/*  3644:      */     {
/*  3645: 3813 */       if (obj == this) {
/*  3646: 3814 */         return true;
/*  3647:      */       }
/*  3648: 3816 */       if (!(obj instanceof CounterGroupProto)) {
/*  3649: 3817 */         return super.equals(obj);
/*  3650:      */       }
/*  3651: 3819 */       CounterGroupProto other = (CounterGroupProto)obj;
/*  3652:      */       
/*  3653: 3821 */       boolean result = true;
/*  3654: 3822 */       result = (result) && (hasName() == other.hasName());
/*  3655: 3823 */       if (hasName()) {
/*  3656: 3824 */         result = (result) && (getName().equals(other.getName()));
/*  3657:      */       }
/*  3658: 3827 */       result = (result) && (hasDisplayName() == other.hasDisplayName());
/*  3659: 3828 */       if (hasDisplayName()) {
/*  3660: 3829 */         result = (result) && (getDisplayName().equals(other.getDisplayName()));
/*  3661:      */       }
/*  3662: 3832 */       result = (result) && (getCountersList().equals(other.getCountersList()));
/*  3663:      */       
/*  3664: 3834 */       result = (result) && (getUnknownFields().equals(other.getUnknownFields()));
/*  3665:      */       
/*  3666: 3836 */       return result;
/*  3667:      */     }
/*  3668:      */     
/*  3669: 3839 */     private int memoizedHashCode = 0;
/*  3670:      */     
/*  3671:      */     public int hashCode()
/*  3672:      */     {
/*  3673: 3842 */       if (this.memoizedHashCode != 0) {
/*  3674: 3843 */         return this.memoizedHashCode;
/*  3675:      */       }
/*  3676: 3845 */       int hash = 41;
/*  3677: 3846 */       hash = 19 * hash + getDescriptorForType().hashCode();
/*  3678: 3847 */       if (hasName())
/*  3679:      */       {
/*  3680: 3848 */         hash = 37 * hash + 1;
/*  3681: 3849 */         hash = 53 * hash + getName().hashCode();
/*  3682:      */       }
/*  3683: 3851 */       if (hasDisplayName())
/*  3684:      */       {
/*  3685: 3852 */         hash = 37 * hash + 2;
/*  3686: 3853 */         hash = 53 * hash + getDisplayName().hashCode();
/*  3687:      */       }
/*  3688: 3855 */       if (getCountersCount() > 0)
/*  3689:      */       {
/*  3690: 3856 */         hash = 37 * hash + 3;
/*  3691: 3857 */         hash = 53 * hash + getCountersList().hashCode();
/*  3692:      */       }
/*  3693: 3859 */       hash = 29 * hash + getUnknownFields().hashCode();
/*  3694: 3860 */       this.memoizedHashCode = hash;
/*  3695: 3861 */       return hash;
/*  3696:      */     }
/*  3697:      */     
/*  3698:      */     public static CounterGroupProto parseFrom(ByteString data)
/*  3699:      */       throws InvalidProtocolBufferException
/*  3700:      */     {
/*  3701: 3867 */       return (CounterGroupProto)PARSER.parseFrom(data);
/*  3702:      */     }
/*  3703:      */     
/*  3704:      */     public static CounterGroupProto parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry)
/*  3705:      */       throws InvalidProtocolBufferException
/*  3706:      */     {
/*  3707: 3873 */       return (CounterGroupProto)PARSER.parseFrom(data, extensionRegistry);
/*  3708:      */     }
/*  3709:      */     
/*  3710:      */     public static CounterGroupProto parseFrom(byte[] data)
/*  3711:      */       throws InvalidProtocolBufferException
/*  3712:      */     {
/*  3713: 3877 */       return (CounterGroupProto)PARSER.parseFrom(data);
/*  3714:      */     }
/*  3715:      */     
/*  3716:      */     public static CounterGroupProto parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry)
/*  3717:      */       throws InvalidProtocolBufferException
/*  3718:      */     {
/*  3719: 3883 */       return (CounterGroupProto)PARSER.parseFrom(data, extensionRegistry);
/*  3720:      */     }
/*  3721:      */     
/*  3722:      */     public static CounterGroupProto parseFrom(InputStream input)
/*  3723:      */       throws IOException
/*  3724:      */     {
/*  3725: 3887 */       return (CounterGroupProto)PARSER.parseFrom(input);
/*  3726:      */     }
/*  3727:      */     
/*  3728:      */     public static CounterGroupProto parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry)
/*  3729:      */       throws IOException
/*  3730:      */     {
/*  3731: 3893 */       return (CounterGroupProto)PARSER.parseFrom(input, extensionRegistry);
/*  3732:      */     }
/*  3733:      */     
/*  3734:      */     public static CounterGroupProto parseDelimitedFrom(InputStream input)
/*  3735:      */       throws IOException
/*  3736:      */     {
/*  3737: 3897 */       return (CounterGroupProto)PARSER.parseDelimitedFrom(input);
/*  3738:      */     }
/*  3739:      */     
/*  3740:      */     public static CounterGroupProto parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry)
/*  3741:      */       throws IOException
/*  3742:      */     {
/*  3743: 3903 */       return (CounterGroupProto)PARSER.parseDelimitedFrom(input, extensionRegistry);
/*  3744:      */     }
/*  3745:      */     
/*  3746:      */     public static CounterGroupProto parseFrom(CodedInputStream input)
/*  3747:      */       throws IOException
/*  3748:      */     {
/*  3749: 3908 */       return (CounterGroupProto)PARSER.parseFrom(input);
/*  3750:      */     }
/*  3751:      */     
/*  3752:      */     public static CounterGroupProto parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry)
/*  3753:      */       throws IOException
/*  3754:      */     {
/*  3755: 3914 */       return (CounterGroupProto)PARSER.parseFrom(input, extensionRegistry);
/*  3756:      */     }
/*  3757:      */     
/*  3758:      */     public static Builder newBuilder()
/*  3759:      */     {
/*  3760: 3917 */       return Builder.access$4500();
/*  3761:      */     }
/*  3762:      */     
/*  3763:      */     public Builder newBuilderForType()
/*  3764:      */     {
/*  3765: 3918 */       return newBuilder();
/*  3766:      */     }
/*  3767:      */     
/*  3768:      */     public static Builder newBuilder(CounterGroupProto prototype)
/*  3769:      */     {
/*  3770: 3920 */       return newBuilder().mergeFrom(prototype);
/*  3771:      */     }
/*  3772:      */     
/*  3773:      */     public Builder toBuilder()
/*  3774:      */     {
/*  3775: 3922 */       return newBuilder(this);
/*  3776:      */     }
/*  3777:      */     
/*  3778:      */     protected Builder newBuilderForType(GeneratedMessage.BuilderParent parent)
/*  3779:      */     {
/*  3780: 3927 */       Builder builder = new Builder(parent, null);
/*  3781: 3928 */       return builder;
/*  3782:      */     }
/*  3783:      */     
/*  3784:      */     public static final class Builder
/*  3785:      */       extends GeneratedMessage.Builder<Builder>
/*  3786:      */       implements MRProtos.CounterGroupProtoOrBuilder
/*  3787:      */     {
/*  3788:      */       private int bitField0_;
/*  3789:      */       
/*  3790:      */       public static final Descriptors.Descriptor getDescriptor()
/*  3791:      */       {
/*  3792: 3938 */         return MRProtos.internal_static_hadoop_mapreduce_CounterGroupProto_descriptor;
/*  3793:      */       }
/*  3794:      */       
/*  3795:      */       protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable()
/*  3796:      */       {
/*  3797: 3943 */         return MRProtos.internal_static_hadoop_mapreduce_CounterGroupProto_fieldAccessorTable.ensureFieldAccessorsInitialized(MRProtos.CounterGroupProto.class, Builder.class);
/*  3798:      */       }
/*  3799:      */       
/*  3800:      */       private Builder()
/*  3801:      */       {
/*  3802: 3950 */         maybeForceBuilderInitialization();
/*  3803:      */       }
/*  3804:      */       
/*  3805:      */       private Builder(GeneratedMessage.BuilderParent parent)
/*  3806:      */       {
/*  3807: 3955 */         super();
/*  3808: 3956 */         maybeForceBuilderInitialization();
/*  3809:      */       }
/*  3810:      */       
/*  3811:      */       private void maybeForceBuilderInitialization()
/*  3812:      */       {
/*  3813: 3959 */         if (MRProtos.CounterGroupProto.alwaysUseFieldBuilders) {
/*  3814: 3960 */           getCountersFieldBuilder();
/*  3815:      */         }
/*  3816:      */       }
/*  3817:      */       
/*  3818:      */       private static Builder create()
/*  3819:      */       {
/*  3820: 3964 */         return new Builder();
/*  3821:      */       }
/*  3822:      */       
/*  3823:      */       public Builder clear()
/*  3824:      */       {
/*  3825: 3968 */         super.clear();
/*  3826: 3969 */         this.name_ = "";
/*  3827: 3970 */         this.bitField0_ &= 0xFFFFFFFE;
/*  3828: 3971 */         this.displayName_ = "";
/*  3829: 3972 */         this.bitField0_ &= 0xFFFFFFFD;
/*  3830: 3973 */         if (this.countersBuilder_ == null)
/*  3831:      */         {
/*  3832: 3974 */           this.counters_ = Collections.emptyList();
/*  3833: 3975 */           this.bitField0_ &= 0xFFFFFFFB;
/*  3834:      */         }
/*  3835:      */         else
/*  3836:      */         {
/*  3837: 3977 */           this.countersBuilder_.clear();
/*  3838:      */         }
/*  3839: 3979 */         return this;
/*  3840:      */       }
/*  3841:      */       
/*  3842:      */       public Builder clone()
/*  3843:      */       {
/*  3844: 3983 */         return create().mergeFrom(buildPartial());
/*  3845:      */       }
/*  3846:      */       
/*  3847:      */       public Descriptors.Descriptor getDescriptorForType()
/*  3848:      */       {
/*  3849: 3988 */         return MRProtos.internal_static_hadoop_mapreduce_CounterGroupProto_descriptor;
/*  3850:      */       }
/*  3851:      */       
/*  3852:      */       public MRProtos.CounterGroupProto getDefaultInstanceForType()
/*  3853:      */       {
/*  3854: 3992 */         return MRProtos.CounterGroupProto.getDefaultInstance();
/*  3855:      */       }
/*  3856:      */       
/*  3857:      */       public MRProtos.CounterGroupProto build()
/*  3858:      */       {
/*  3859: 3996 */         MRProtos.CounterGroupProto result = buildPartial();
/*  3860: 3997 */         if (!result.isInitialized()) {
/*  3861: 3998 */           throw newUninitializedMessageException(result);
/*  3862:      */         }
/*  3863: 4000 */         return result;
/*  3864:      */       }
/*  3865:      */       
/*  3866:      */       public MRProtos.CounterGroupProto buildPartial()
/*  3867:      */       {
/*  3868: 4004 */         MRProtos.CounterGroupProto result = new MRProtos.CounterGroupProto(this, null);
/*  3869: 4005 */         int from_bitField0_ = this.bitField0_;
/*  3870: 4006 */         int to_bitField0_ = 0;
/*  3871: 4007 */         if ((from_bitField0_ & 0x1) == 1) {
/*  3872: 4008 */           to_bitField0_ |= 0x1;
/*  3873:      */         }
/*  3874: 4010 */         result.name_ = this.name_;
/*  3875: 4011 */         if ((from_bitField0_ & 0x2) == 2) {
/*  3876: 4012 */           to_bitField0_ |= 0x2;
/*  3877:      */         }
/*  3878: 4014 */         result.displayName_ = this.displayName_;
/*  3879: 4015 */         if (this.countersBuilder_ == null)
/*  3880:      */         {
/*  3881: 4016 */           if ((this.bitField0_ & 0x4) == 4)
/*  3882:      */           {
/*  3883: 4017 */             this.counters_ = Collections.unmodifiableList(this.counters_);
/*  3884: 4018 */             this.bitField0_ &= 0xFFFFFFFB;
/*  3885:      */           }
/*  3886: 4020 */           result.counters_ = this.counters_;
/*  3887:      */         }
/*  3888:      */         else
/*  3889:      */         {
/*  3890: 4022 */           result.counters_ = this.countersBuilder_.build();
/*  3891:      */         }
/*  3892: 4024 */         result.bitField0_ = to_bitField0_;
/*  3893: 4025 */         onBuilt();
/*  3894: 4026 */         return result;
/*  3895:      */       }
/*  3896:      */       
/*  3897:      */       public Builder mergeFrom(Message other)
/*  3898:      */       {
/*  3899: 4030 */         if ((other instanceof MRProtos.CounterGroupProto)) {
/*  3900: 4031 */           return mergeFrom((MRProtos.CounterGroupProto)other);
/*  3901:      */         }
/*  3902: 4033 */         super.mergeFrom(other);
/*  3903: 4034 */         return this;
/*  3904:      */       }
/*  3905:      */       
/*  3906:      */       public Builder mergeFrom(MRProtos.CounterGroupProto other)
/*  3907:      */       {
/*  3908: 4039 */         if (other == MRProtos.CounterGroupProto.getDefaultInstance()) {
/*  3909: 4039 */           return this;
/*  3910:      */         }
/*  3911: 4040 */         if (other.hasName())
/*  3912:      */         {
/*  3913: 4041 */           this.bitField0_ |= 0x1;
/*  3914: 4042 */           this.name_ = other.name_;
/*  3915: 4043 */           onChanged();
/*  3916:      */         }
/*  3917: 4045 */         if (other.hasDisplayName())
/*  3918:      */         {
/*  3919: 4046 */           this.bitField0_ |= 0x2;
/*  3920: 4047 */           this.displayName_ = other.displayName_;
/*  3921: 4048 */           onChanged();
/*  3922:      */         }
/*  3923: 4050 */         if (this.countersBuilder_ == null)
/*  3924:      */         {
/*  3925: 4051 */           if (!other.counters_.isEmpty())
/*  3926:      */           {
/*  3927: 4052 */             if (this.counters_.isEmpty())
/*  3928:      */             {
/*  3929: 4053 */               this.counters_ = other.counters_;
/*  3930: 4054 */               this.bitField0_ &= 0xFFFFFFFB;
/*  3931:      */             }
/*  3932:      */             else
/*  3933:      */             {
/*  3934: 4056 */               ensureCountersIsMutable();
/*  3935: 4057 */               this.counters_.addAll(other.counters_);
/*  3936:      */             }
/*  3937: 4059 */             onChanged();
/*  3938:      */           }
/*  3939:      */         }
/*  3940: 4062 */         else if (!other.counters_.isEmpty()) {
/*  3941: 4063 */           if (this.countersBuilder_.isEmpty())
/*  3942:      */           {
/*  3943: 4064 */             this.countersBuilder_.dispose();
/*  3944: 4065 */             this.countersBuilder_ = null;
/*  3945: 4066 */             this.counters_ = other.counters_;
/*  3946: 4067 */             this.bitField0_ &= 0xFFFFFFFB;
/*  3947: 4068 */             this.countersBuilder_ = (MRProtos.CounterGroupProto.alwaysUseFieldBuilders ? getCountersFieldBuilder() : null);
/*  3948:      */           }
/*  3949:      */           else
/*  3950:      */           {
/*  3951: 4072 */             this.countersBuilder_.addAllMessages(other.counters_);
/*  3952:      */           }
/*  3953:      */         }
/*  3954: 4076 */         mergeUnknownFields(other.getUnknownFields());
/*  3955: 4077 */         return this;
/*  3956:      */       }
/*  3957:      */       
/*  3958:      */       public final boolean isInitialized()
/*  3959:      */       {
/*  3960: 4081 */         return true;
/*  3961:      */       }
/*  3962:      */       
/*  3963:      */       public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry)
/*  3964:      */         throws IOException
/*  3965:      */       {
/*  3966: 4088 */         MRProtos.CounterGroupProto parsedMessage = null;
/*  3967:      */         try
/*  3968:      */         {
/*  3969: 4090 */           parsedMessage = (MRProtos.CounterGroupProto)MRProtos.CounterGroupProto.PARSER.parsePartialFrom(input, extensionRegistry);
/*  3970:      */         }
/*  3971:      */         catch (InvalidProtocolBufferException e)
/*  3972:      */         {
/*  3973: 4092 */           parsedMessage = (MRProtos.CounterGroupProto)e.getUnfinishedMessage();
/*  3974: 4093 */           throw e;
/*  3975:      */         }
/*  3976:      */         finally
/*  3977:      */         {
/*  3978: 4095 */           if (parsedMessage != null) {
/*  3979: 4096 */             mergeFrom(parsedMessage);
/*  3980:      */           }
/*  3981:      */         }
/*  3982: 4099 */         return this;
/*  3983:      */       }
/*  3984:      */       
/*  3985: 4104 */       private Object name_ = "";
/*  3986:      */       
/*  3987:      */       public boolean hasName()
/*  3988:      */       {
/*  3989: 4109 */         return (this.bitField0_ & 0x1) == 1;
/*  3990:      */       }
/*  3991:      */       
/*  3992:      */       public String getName()
/*  3993:      */       {
/*  3994: 4115 */         Object ref = this.name_;
/*  3995: 4116 */         if (!(ref instanceof String))
/*  3996:      */         {
/*  3997: 4117 */           String s = ((ByteString)ref).toStringUtf8();
/*  3998:      */           
/*  3999: 4119 */           this.name_ = s;
/*  4000: 4120 */           return s;
/*  4001:      */         }
/*  4002: 4122 */         return (String)ref;
/*  4003:      */       }
/*  4004:      */       
/*  4005:      */       public ByteString getNameBytes()
/*  4006:      */       {
/*  4007: 4130 */         Object ref = this.name_;
/*  4008: 4131 */         if ((ref instanceof String))
/*  4009:      */         {
/*  4010: 4132 */           ByteString b = ByteString.copyFromUtf8((String)ref);
/*  4011:      */           
/*  4012:      */ 
/*  4013: 4135 */           this.name_ = b;
/*  4014: 4136 */           return b;
/*  4015:      */         }
/*  4016: 4138 */         return (ByteString)ref;
/*  4017:      */       }
/*  4018:      */       
/*  4019:      */       public Builder setName(String value)
/*  4020:      */       {
/*  4021: 4146 */         if (value == null) {
/*  4022: 4147 */           throw new NullPointerException();
/*  4023:      */         }
/*  4024: 4149 */         this.bitField0_ |= 0x1;
/*  4025: 4150 */         this.name_ = value;
/*  4026: 4151 */         onChanged();
/*  4027: 4152 */         return this;
/*  4028:      */       }
/*  4029:      */       
/*  4030:      */       public Builder clearName()
/*  4031:      */       {
/*  4032: 4158 */         this.bitField0_ &= 0xFFFFFFFE;
/*  4033: 4159 */         this.name_ = MRProtos.CounterGroupProto.getDefaultInstance().getName();
/*  4034: 4160 */         onChanged();
/*  4035: 4161 */         return this;
/*  4036:      */       }
/*  4037:      */       
/*  4038:      */       public Builder setNameBytes(ByteString value)
/*  4039:      */       {
/*  4040: 4168 */         if (value == null) {
/*  4041: 4169 */           throw new NullPointerException();
/*  4042:      */         }
/*  4043: 4171 */         this.bitField0_ |= 0x1;
/*  4044: 4172 */         this.name_ = value;
/*  4045: 4173 */         onChanged();
/*  4046: 4174 */         return this;
/*  4047:      */       }
/*  4048:      */       
/*  4049: 4178 */       private Object displayName_ = "";
/*  4050:      */       
/*  4051:      */       public boolean hasDisplayName()
/*  4052:      */       {
/*  4053: 4183 */         return (this.bitField0_ & 0x2) == 2;
/*  4054:      */       }
/*  4055:      */       
/*  4056:      */       public String getDisplayName()
/*  4057:      */       {
/*  4058: 4189 */         Object ref = this.displayName_;
/*  4059: 4190 */         if (!(ref instanceof String))
/*  4060:      */         {
/*  4061: 4191 */           String s = ((ByteString)ref).toStringUtf8();
/*  4062:      */           
/*  4063: 4193 */           this.displayName_ = s;
/*  4064: 4194 */           return s;
/*  4065:      */         }
/*  4066: 4196 */         return (String)ref;
/*  4067:      */       }
/*  4068:      */       
/*  4069:      */       public ByteString getDisplayNameBytes()
/*  4070:      */       {
/*  4071: 4204 */         Object ref = this.displayName_;
/*  4072: 4205 */         if ((ref instanceof String))
/*  4073:      */         {
/*  4074: 4206 */           ByteString b = ByteString.copyFromUtf8((String)ref);
/*  4075:      */           
/*  4076:      */ 
/*  4077: 4209 */           this.displayName_ = b;
/*  4078: 4210 */           return b;
/*  4079:      */         }
/*  4080: 4212 */         return (ByteString)ref;
/*  4081:      */       }
/*  4082:      */       
/*  4083:      */       public Builder setDisplayName(String value)
/*  4084:      */       {
/*  4085: 4220 */         if (value == null) {
/*  4086: 4221 */           throw new NullPointerException();
/*  4087:      */         }
/*  4088: 4223 */         this.bitField0_ |= 0x2;
/*  4089: 4224 */         this.displayName_ = value;
/*  4090: 4225 */         onChanged();
/*  4091: 4226 */         return this;
/*  4092:      */       }
/*  4093:      */       
/*  4094:      */       public Builder clearDisplayName()
/*  4095:      */       {
/*  4096: 4232 */         this.bitField0_ &= 0xFFFFFFFD;
/*  4097: 4233 */         this.displayName_ = MRProtos.CounterGroupProto.getDefaultInstance().getDisplayName();
/*  4098: 4234 */         onChanged();
/*  4099: 4235 */         return this;
/*  4100:      */       }
/*  4101:      */       
/*  4102:      */       public Builder setDisplayNameBytes(ByteString value)
/*  4103:      */       {
/*  4104: 4242 */         if (value == null) {
/*  4105: 4243 */           throw new NullPointerException();
/*  4106:      */         }
/*  4107: 4245 */         this.bitField0_ |= 0x2;
/*  4108: 4246 */         this.displayName_ = value;
/*  4109: 4247 */         onChanged();
/*  4110: 4248 */         return this;
/*  4111:      */       }
/*  4112:      */       
/*  4113: 4252 */       private List<MRProtos.StringCounterMapProto> counters_ = Collections.emptyList();
/*  4114:      */       private RepeatedFieldBuilder<MRProtos.StringCounterMapProto, MRProtos.StringCounterMapProto.Builder, MRProtos.StringCounterMapProtoOrBuilder> countersBuilder_;
/*  4115:      */       
/*  4116:      */       private void ensureCountersIsMutable()
/*  4117:      */       {
/*  4118: 4255 */         if ((this.bitField0_ & 0x4) != 4)
/*  4119:      */         {
/*  4120: 4256 */           this.counters_ = new ArrayList(this.counters_);
/*  4121: 4257 */           this.bitField0_ |= 0x4;
/*  4122:      */         }
/*  4123:      */       }
/*  4124:      */       
/*  4125:      */       public List<MRProtos.StringCounterMapProto> getCountersList()
/*  4126:      */       {
/*  4127: 4268 */         if (this.countersBuilder_ == null) {
/*  4128: 4269 */           return Collections.unmodifiableList(this.counters_);
/*  4129:      */         }
/*  4130: 4271 */         return this.countersBuilder_.getMessageList();
/*  4131:      */       }
/*  4132:      */       
/*  4133:      */       public int getCountersCount()
/*  4134:      */       {
/*  4135: 4278 */         if (this.countersBuilder_ == null) {
/*  4136: 4279 */           return this.counters_.size();
/*  4137:      */         }
/*  4138: 4281 */         return this.countersBuilder_.getCount();
/*  4139:      */       }
/*  4140:      */       
/*  4141:      */       public MRProtos.StringCounterMapProto getCounters(int index)
/*  4142:      */       {
/*  4143: 4288 */         if (this.countersBuilder_ == null) {
/*  4144: 4289 */           return (MRProtos.StringCounterMapProto)this.counters_.get(index);
/*  4145:      */         }
/*  4146: 4291 */         return (MRProtos.StringCounterMapProto)this.countersBuilder_.getMessage(index);
/*  4147:      */       }
/*  4148:      */       
/*  4149:      */       public Builder setCounters(int index, MRProtos.StringCounterMapProto value)
/*  4150:      */       {
/*  4151: 4299 */         if (this.countersBuilder_ == null)
/*  4152:      */         {
/*  4153: 4300 */           if (value == null) {
/*  4154: 4301 */             throw new NullPointerException();
/*  4155:      */           }
/*  4156: 4303 */           ensureCountersIsMutable();
/*  4157: 4304 */           this.counters_.set(index, value);
/*  4158: 4305 */           onChanged();
/*  4159:      */         }
/*  4160:      */         else
/*  4161:      */         {
/*  4162: 4307 */           this.countersBuilder_.setMessage(index, value);
/*  4163:      */         }
/*  4164: 4309 */         return this;
/*  4165:      */       }
/*  4166:      */       
/*  4167:      */       public Builder setCounters(int index, MRProtos.StringCounterMapProto.Builder builderForValue)
/*  4168:      */       {
/*  4169: 4316 */         if (this.countersBuilder_ == null)
/*  4170:      */         {
/*  4171: 4317 */           ensureCountersIsMutable();
/*  4172: 4318 */           this.counters_.set(index, builderForValue.build());
/*  4173: 4319 */           onChanged();
/*  4174:      */         }
/*  4175:      */         else
/*  4176:      */         {
/*  4177: 4321 */           this.countersBuilder_.setMessage(index, builderForValue.build());
/*  4178:      */         }
/*  4179: 4323 */         return this;
/*  4180:      */       }
/*  4181:      */       
/*  4182:      */       public Builder addCounters(MRProtos.StringCounterMapProto value)
/*  4183:      */       {
/*  4184: 4329 */         if (this.countersBuilder_ == null)
/*  4185:      */         {
/*  4186: 4330 */           if (value == null) {
/*  4187: 4331 */             throw new NullPointerException();
/*  4188:      */           }
/*  4189: 4333 */           ensureCountersIsMutable();
/*  4190: 4334 */           this.counters_.add(value);
/*  4191: 4335 */           onChanged();
/*  4192:      */         }
/*  4193:      */         else
/*  4194:      */         {
/*  4195: 4337 */           this.countersBuilder_.addMessage(value);
/*  4196:      */         }
/*  4197: 4339 */         return this;
/*  4198:      */       }
/*  4199:      */       
/*  4200:      */       public Builder addCounters(int index, MRProtos.StringCounterMapProto value)
/*  4201:      */       {
/*  4202: 4346 */         if (this.countersBuilder_ == null)
/*  4203:      */         {
/*  4204: 4347 */           if (value == null) {
/*  4205: 4348 */             throw new NullPointerException();
/*  4206:      */           }
/*  4207: 4350 */           ensureCountersIsMutable();
/*  4208: 4351 */           this.counters_.add(index, value);
/*  4209: 4352 */           onChanged();
/*  4210:      */         }
/*  4211:      */         else
/*  4212:      */         {
/*  4213: 4354 */           this.countersBuilder_.addMessage(index, value);
/*  4214:      */         }
/*  4215: 4356 */         return this;
/*  4216:      */       }
/*  4217:      */       
/*  4218:      */       public Builder addCounters(MRProtos.StringCounterMapProto.Builder builderForValue)
/*  4219:      */       {
/*  4220: 4363 */         if (this.countersBuilder_ == null)
/*  4221:      */         {
/*  4222: 4364 */           ensureCountersIsMutable();
/*  4223: 4365 */           this.counters_.add(builderForValue.build());
/*  4224: 4366 */           onChanged();
/*  4225:      */         }
/*  4226:      */         else
/*  4227:      */         {
/*  4228: 4368 */           this.countersBuilder_.addMessage(builderForValue.build());
/*  4229:      */         }
/*  4230: 4370 */         return this;
/*  4231:      */       }
/*  4232:      */       
/*  4233:      */       public Builder addCounters(int index, MRProtos.StringCounterMapProto.Builder builderForValue)
/*  4234:      */       {
/*  4235: 4377 */         if (this.countersBuilder_ == null)
/*  4236:      */         {
/*  4237: 4378 */           ensureCountersIsMutable();
/*  4238: 4379 */           this.counters_.add(index, builderForValue.build());
/*  4239: 4380 */           onChanged();
/*  4240:      */         }
/*  4241:      */         else
/*  4242:      */         {
/*  4243: 4382 */           this.countersBuilder_.addMessage(index, builderForValue.build());
/*  4244:      */         }
/*  4245: 4384 */         return this;
/*  4246:      */       }
/*  4247:      */       
/*  4248:      */       public Builder addAllCounters(Iterable<? extends MRProtos.StringCounterMapProto> values)
/*  4249:      */       {
/*  4250: 4391 */         if (this.countersBuilder_ == null)
/*  4251:      */         {
/*  4252: 4392 */           ensureCountersIsMutable();
/*  4253: 4393 */           GeneratedMessage.Builder.addAll(values, this.counters_);
/*  4254: 4394 */           onChanged();
/*  4255:      */         }
/*  4256:      */         else
/*  4257:      */         {
/*  4258: 4396 */           this.countersBuilder_.addAllMessages(values);
/*  4259:      */         }
/*  4260: 4398 */         return this;
/*  4261:      */       }
/*  4262:      */       
/*  4263:      */       public Builder clearCounters()
/*  4264:      */       {
/*  4265: 4404 */         if (this.countersBuilder_ == null)
/*  4266:      */         {
/*  4267: 4405 */           this.counters_ = Collections.emptyList();
/*  4268: 4406 */           this.bitField0_ &= 0xFFFFFFFB;
/*  4269: 4407 */           onChanged();
/*  4270:      */         }
/*  4271:      */         else
/*  4272:      */         {
/*  4273: 4409 */           this.countersBuilder_.clear();
/*  4274:      */         }
/*  4275: 4411 */         return this;
/*  4276:      */       }
/*  4277:      */       
/*  4278:      */       public Builder removeCounters(int index)
/*  4279:      */       {
/*  4280: 4417 */         if (this.countersBuilder_ == null)
/*  4281:      */         {
/*  4282: 4418 */           ensureCountersIsMutable();
/*  4283: 4419 */           this.counters_.remove(index);
/*  4284: 4420 */           onChanged();
/*  4285:      */         }
/*  4286:      */         else
/*  4287:      */         {
/*  4288: 4422 */           this.countersBuilder_.remove(index);
/*  4289:      */         }
/*  4290: 4424 */         return this;
/*  4291:      */       }
/*  4292:      */       
/*  4293:      */       public MRProtos.StringCounterMapProto.Builder getCountersBuilder(int index)
/*  4294:      */       {
/*  4295: 4431 */         return (MRProtos.StringCounterMapProto.Builder)getCountersFieldBuilder().getBuilder(index);
/*  4296:      */       }
/*  4297:      */       
/*  4298:      */       public MRProtos.StringCounterMapProtoOrBuilder getCountersOrBuilder(int index)
/*  4299:      */       {
/*  4300: 4438 */         if (this.countersBuilder_ == null) {
/*  4301: 4439 */           return (MRProtos.StringCounterMapProtoOrBuilder)this.counters_.get(index);
/*  4302:      */         }
/*  4303: 4440 */         return (MRProtos.StringCounterMapProtoOrBuilder)this.countersBuilder_.getMessageOrBuilder(index);
/*  4304:      */       }
/*  4305:      */       
/*  4306:      */       public List<? extends MRProtos.StringCounterMapProtoOrBuilder> getCountersOrBuilderList()
/*  4307:      */       {
/*  4308: 4448 */         if (this.countersBuilder_ != null) {
/*  4309: 4449 */           return this.countersBuilder_.getMessageOrBuilderList();
/*  4310:      */         }
/*  4311: 4451 */         return Collections.unmodifiableList(this.counters_);
/*  4312:      */       }
/*  4313:      */       
/*  4314:      */       public MRProtos.StringCounterMapProto.Builder addCountersBuilder()
/*  4315:      */       {
/*  4316: 4458 */         return (MRProtos.StringCounterMapProto.Builder)getCountersFieldBuilder().addBuilder(MRProtos.StringCounterMapProto.getDefaultInstance());
/*  4317:      */       }
/*  4318:      */       
/*  4319:      */       public MRProtos.StringCounterMapProto.Builder addCountersBuilder(int index)
/*  4320:      */       {
/*  4321: 4466 */         return (MRProtos.StringCounterMapProto.Builder)getCountersFieldBuilder().addBuilder(index, MRProtos.StringCounterMapProto.getDefaultInstance());
/*  4322:      */       }
/*  4323:      */       
/*  4324:      */       public List<MRProtos.StringCounterMapProto.Builder> getCountersBuilderList()
/*  4325:      */       {
/*  4326: 4474 */         return getCountersFieldBuilder().getBuilderList();
/*  4327:      */       }
/*  4328:      */       
/*  4329:      */       private RepeatedFieldBuilder<MRProtos.StringCounterMapProto, MRProtos.StringCounterMapProto.Builder, MRProtos.StringCounterMapProtoOrBuilder> getCountersFieldBuilder()
/*  4330:      */       {
/*  4331: 4479 */         if (this.countersBuilder_ == null)
/*  4332:      */         {
/*  4333: 4480 */           this.countersBuilder_ = new RepeatedFieldBuilder(this.counters_, (this.bitField0_ & 0x4) == 4, getParentForChildren(), isClean());
/*  4334:      */           
/*  4335:      */ 
/*  4336:      */ 
/*  4337:      */ 
/*  4338:      */ 
/*  4339: 4486 */           this.counters_ = null;
/*  4340:      */         }
/*  4341: 4488 */         return this.countersBuilder_;
/*  4342:      */       }
/*  4343:      */     }
/*  4344:      */     
/*  4345:      */     static
/*  4346:      */     {
/*  4347: 4495 */       defaultInstance = new CounterGroupProto(true);
/*  4348: 4496 */       defaultInstance.initFields();
/*  4349:      */     }
/*  4350:      */   }
/*  4351:      */   
/*  4352:      */   public static abstract interface CountersProtoOrBuilder
/*  4353:      */     extends MessageOrBuilder
/*  4354:      */   {
/*  4355:      */     public abstract List<MRProtos.StringCounterGroupMapProto> getCounterGroupsList();
/*  4356:      */     
/*  4357:      */     public abstract MRProtos.StringCounterGroupMapProto getCounterGroups(int paramInt);
/*  4358:      */     
/*  4359:      */     public abstract int getCounterGroupsCount();
/*  4360:      */     
/*  4361:      */     public abstract List<? extends MRProtos.StringCounterGroupMapProtoOrBuilder> getCounterGroupsOrBuilderList();
/*  4362:      */     
/*  4363:      */     public abstract MRProtos.StringCounterGroupMapProtoOrBuilder getCounterGroupsOrBuilder(int paramInt);
/*  4364:      */   }
/*  4365:      */   
/*  4366:      */   public static final class CountersProto
/*  4367:      */     extends GeneratedMessage
/*  4368:      */     implements MRProtos.CountersProtoOrBuilder
/*  4369:      */   {
/*  4370:      */     private static final CountersProto defaultInstance;
/*  4371:      */     private final UnknownFieldSet unknownFields;
/*  4372:      */     
/*  4373:      */     private CountersProto(GeneratedMessage.Builder<?> builder)
/*  4374:      */     {
/*  4375: 4538 */       super();
/*  4376: 4539 */       this.unknownFields = builder.getUnknownFields();
/*  4377:      */     }
/*  4378:      */     
/*  4379:      */     private CountersProto(boolean noInit)
/*  4380:      */     {
/*  4381: 4541 */       this.unknownFields = UnknownFieldSet.getDefaultInstance();
/*  4382:      */     }
/*  4383:      */     
/*  4384:      */     public static CountersProto getDefaultInstance()
/*  4385:      */     {
/*  4386: 4545 */       return defaultInstance;
/*  4387:      */     }
/*  4388:      */     
/*  4389:      */     public CountersProto getDefaultInstanceForType()
/*  4390:      */     {
/*  4391: 4549 */       return defaultInstance;
/*  4392:      */     }
/*  4393:      */     
/*  4394:      */     public final UnknownFieldSet getUnknownFields()
/*  4395:      */     {
/*  4396: 4556 */       return this.unknownFields;
/*  4397:      */     }
/*  4398:      */     
/*  4399:      */     private CountersProto(CodedInputStream input, ExtensionRegistryLite extensionRegistry)
/*  4400:      */       throws InvalidProtocolBufferException
/*  4401:      */     {
/*  4402: 4562 */       initFields();
/*  4403: 4563 */       int mutable_bitField0_ = 0;
/*  4404: 4564 */       UnknownFieldSet.Builder unknownFields = UnknownFieldSet.newBuilder();
/*  4405:      */       try
/*  4406:      */       {
/*  4407: 4567 */         boolean done = false;
/*  4408: 4568 */         while (!done)
/*  4409:      */         {
/*  4410: 4569 */           int tag = input.readTag();
/*  4411: 4570 */           switch (tag)
/*  4412:      */           {
/*  4413:      */           case 0: 
/*  4414: 4572 */             done = true;
/*  4415: 4573 */             break;
/*  4416:      */           default: 
/*  4417: 4575 */             if (!parseUnknownField(input, unknownFields, extensionRegistry, tag)) {
/*  4418: 4577 */               done = true;
/*  4419:      */             }
/*  4420:      */             break;
/*  4421:      */           case 10: 
/*  4422: 4582 */             if ((mutable_bitField0_ & 0x1) != 1)
/*  4423:      */             {
/*  4424: 4583 */               this.counterGroups_ = new ArrayList();
/*  4425: 4584 */               mutable_bitField0_ |= 0x1;
/*  4426:      */             }
/*  4427: 4586 */             this.counterGroups_.add(input.readMessage(MRProtos.StringCounterGroupMapProto.PARSER, extensionRegistry));
/*  4428:      */           }
/*  4429:      */         }
/*  4430:      */       }
/*  4431:      */       catch (InvalidProtocolBufferException e)
/*  4432:      */       {
/*  4433: 4592 */         throw e.setUnfinishedMessage(this);
/*  4434:      */       }
/*  4435:      */       catch (IOException e)
/*  4436:      */       {
/*  4437: 4594 */         throw new InvalidProtocolBufferException(e.getMessage()).setUnfinishedMessage(this);
/*  4438:      */       }
/*  4439:      */       finally
/*  4440:      */       {
/*  4441: 4597 */         if ((mutable_bitField0_ & 0x1) == 1) {
/*  4442: 4598 */           this.counterGroups_ = Collections.unmodifiableList(this.counterGroups_);
/*  4443:      */         }
/*  4444: 4600 */         this.unknownFields = unknownFields.build();
/*  4445: 4601 */         makeExtensionsImmutable();
/*  4446:      */       }
/*  4447:      */     }
/*  4448:      */     
/*  4449:      */     public static final Descriptors.Descriptor getDescriptor()
/*  4450:      */     {
/*  4451: 4606 */       return MRProtos.internal_static_hadoop_mapreduce_CountersProto_descriptor;
/*  4452:      */     }
/*  4453:      */     
/*  4454:      */     protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable()
/*  4455:      */     {
/*  4456: 4611 */       return MRProtos.internal_static_hadoop_mapreduce_CountersProto_fieldAccessorTable.ensureFieldAccessorsInitialized(CountersProto.class, Builder.class);
/*  4457:      */     }
/*  4458:      */     
/*  4459: 4616 */     public static Parser<CountersProto> PARSER = new AbstractParser()
/*  4460:      */     {
/*  4461:      */       public MRProtos.CountersProto parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry)
/*  4462:      */         throws InvalidProtocolBufferException
/*  4463:      */       {
/*  4464: 4622 */         return new MRProtos.CountersProto(input, extensionRegistry, null);
/*  4465:      */       }
/*  4466:      */     };
/*  4467:      */     public static final int COUNTER_GROUPS_FIELD_NUMBER = 1;
/*  4468:      */     private List<MRProtos.StringCounterGroupMapProto> counterGroups_;
/*  4469:      */     
/*  4470:      */     public Parser<CountersProto> getParserForType()
/*  4471:      */     {
/*  4472: 4628 */       return PARSER;
/*  4473:      */     }
/*  4474:      */     
/*  4475:      */     public List<MRProtos.StringCounterGroupMapProto> getCounterGroupsList()
/*  4476:      */     {
/*  4477: 4638 */       return this.counterGroups_;
/*  4478:      */     }
/*  4479:      */     
/*  4480:      */     public List<? extends MRProtos.StringCounterGroupMapProtoOrBuilder> getCounterGroupsOrBuilderList()
/*  4481:      */     {
/*  4482: 4645 */       return this.counterGroups_;
/*  4483:      */     }
/*  4484:      */     
/*  4485:      */     public int getCounterGroupsCount()
/*  4486:      */     {
/*  4487: 4651 */       return this.counterGroups_.size();
/*  4488:      */     }
/*  4489:      */     
/*  4490:      */     public MRProtos.StringCounterGroupMapProto getCounterGroups(int index)
/*  4491:      */     {
/*  4492: 4657 */       return (MRProtos.StringCounterGroupMapProto)this.counterGroups_.get(index);
/*  4493:      */     }
/*  4494:      */     
/*  4495:      */     public MRProtos.StringCounterGroupMapProtoOrBuilder getCounterGroupsOrBuilder(int index)
/*  4496:      */     {
/*  4497: 4664 */       return (MRProtos.StringCounterGroupMapProtoOrBuilder)this.counterGroups_.get(index);
/*  4498:      */     }
/*  4499:      */     
/*  4500:      */     private void initFields()
/*  4501:      */     {
/*  4502: 4668 */       this.counterGroups_ = Collections.emptyList();
/*  4503:      */     }
/*  4504:      */     
/*  4505: 4670 */     private byte memoizedIsInitialized = -1;
/*  4506:      */     
/*  4507:      */     public final boolean isInitialized()
/*  4508:      */     {
/*  4509: 4672 */       byte isInitialized = this.memoizedIsInitialized;
/*  4510: 4673 */       if (isInitialized != -1) {
/*  4511: 4673 */         return isInitialized == 1;
/*  4512:      */       }
/*  4513: 4675 */       this.memoizedIsInitialized = 1;
/*  4514: 4676 */       return true;
/*  4515:      */     }
/*  4516:      */     
/*  4517:      */     public void writeTo(CodedOutputStream output)
/*  4518:      */       throws IOException
/*  4519:      */     {
/*  4520: 4681 */       getSerializedSize();
/*  4521: 4682 */       for (int i = 0; i < this.counterGroups_.size(); i++) {
/*  4522: 4683 */         output.writeMessage(1, (MessageLite)this.counterGroups_.get(i));
/*  4523:      */       }
/*  4524: 4685 */       getUnknownFields().writeTo(output);
/*  4525:      */     }
/*  4526:      */     
/*  4527: 4688 */     private int memoizedSerializedSize = -1;
/*  4528:      */     private static final long serialVersionUID = 0L;
/*  4529:      */     
/*  4530:      */     public int getSerializedSize()
/*  4531:      */     {
/*  4532: 4690 */       int size = this.memoizedSerializedSize;
/*  4533: 4691 */       if (size != -1) {
/*  4534: 4691 */         return size;
/*  4535:      */       }
/*  4536: 4693 */       size = 0;
/*  4537: 4694 */       for (int i = 0; i < this.counterGroups_.size(); i++) {
/*  4538: 4695 */         size += CodedOutputStream.computeMessageSize(1, (MessageLite)this.counterGroups_.get(i));
/*  4539:      */       }
/*  4540: 4698 */       size += getUnknownFields().getSerializedSize();
/*  4541: 4699 */       this.memoizedSerializedSize = size;
/*  4542: 4700 */       return size;
/*  4543:      */     }
/*  4544:      */     
/*  4545:      */     protected Object writeReplace()
/*  4546:      */       throws ObjectStreamException
/*  4547:      */     {
/*  4548: 4707 */       return super.writeReplace();
/*  4549:      */     }
/*  4550:      */     
/*  4551:      */     public boolean equals(Object obj)
/*  4552:      */     {
/*  4553: 4712 */       if (obj == this) {
/*  4554: 4713 */         return true;
/*  4555:      */       }
/*  4556: 4715 */       if (!(obj instanceof CountersProto)) {
/*  4557: 4716 */         return super.equals(obj);
/*  4558:      */       }
/*  4559: 4718 */       CountersProto other = (CountersProto)obj;
/*  4560:      */       
/*  4561: 4720 */       boolean result = true;
/*  4562: 4721 */       result = (result) && (getCounterGroupsList().equals(other.getCounterGroupsList()));
/*  4563:      */       
/*  4564: 4723 */       result = (result) && (getUnknownFields().equals(other.getUnknownFields()));
/*  4565:      */       
/*  4566: 4725 */       return result;
/*  4567:      */     }
/*  4568:      */     
/*  4569: 4728 */     private int memoizedHashCode = 0;
/*  4570:      */     
/*  4571:      */     public int hashCode()
/*  4572:      */     {
/*  4573: 4731 */       if (this.memoizedHashCode != 0) {
/*  4574: 4732 */         return this.memoizedHashCode;
/*  4575:      */       }
/*  4576: 4734 */       int hash = 41;
/*  4577: 4735 */       hash = 19 * hash + getDescriptorForType().hashCode();
/*  4578: 4736 */       if (getCounterGroupsCount() > 0)
/*  4579:      */       {
/*  4580: 4737 */         hash = 37 * hash + 1;
/*  4581: 4738 */         hash = 53 * hash + getCounterGroupsList().hashCode();
/*  4582:      */       }
/*  4583: 4740 */       hash = 29 * hash + getUnknownFields().hashCode();
/*  4584: 4741 */       this.memoizedHashCode = hash;
/*  4585: 4742 */       return hash;
/*  4586:      */     }
/*  4587:      */     
/*  4588:      */     public static CountersProto parseFrom(ByteString data)
/*  4589:      */       throws InvalidProtocolBufferException
/*  4590:      */     {
/*  4591: 4748 */       return (CountersProto)PARSER.parseFrom(data);
/*  4592:      */     }
/*  4593:      */     
/*  4594:      */     public static CountersProto parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry)
/*  4595:      */       throws InvalidProtocolBufferException
/*  4596:      */     {
/*  4597: 4754 */       return (CountersProto)PARSER.parseFrom(data, extensionRegistry);
/*  4598:      */     }
/*  4599:      */     
/*  4600:      */     public static CountersProto parseFrom(byte[] data)
/*  4601:      */       throws InvalidProtocolBufferException
/*  4602:      */     {
/*  4603: 4758 */       return (CountersProto)PARSER.parseFrom(data);
/*  4604:      */     }
/*  4605:      */     
/*  4606:      */     public static CountersProto parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry)
/*  4607:      */       throws InvalidProtocolBufferException
/*  4608:      */     {
/*  4609: 4764 */       return (CountersProto)PARSER.parseFrom(data, extensionRegistry);
/*  4610:      */     }
/*  4611:      */     
/*  4612:      */     public static CountersProto parseFrom(InputStream input)
/*  4613:      */       throws IOException
/*  4614:      */     {
/*  4615: 4768 */       return (CountersProto)PARSER.parseFrom(input);
/*  4616:      */     }
/*  4617:      */     
/*  4618:      */     public static CountersProto parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry)
/*  4619:      */       throws IOException
/*  4620:      */     {
/*  4621: 4774 */       return (CountersProto)PARSER.parseFrom(input, extensionRegistry);
/*  4622:      */     }
/*  4623:      */     
/*  4624:      */     public static CountersProto parseDelimitedFrom(InputStream input)
/*  4625:      */       throws IOException
/*  4626:      */     {
/*  4627: 4778 */       return (CountersProto)PARSER.parseDelimitedFrom(input);
/*  4628:      */     }
/*  4629:      */     
/*  4630:      */     public static CountersProto parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry)
/*  4631:      */       throws IOException
/*  4632:      */     {
/*  4633: 4784 */       return (CountersProto)PARSER.parseDelimitedFrom(input, extensionRegistry);
/*  4634:      */     }
/*  4635:      */     
/*  4636:      */     public static CountersProto parseFrom(CodedInputStream input)
/*  4637:      */       throws IOException
/*  4638:      */     {
/*  4639: 4789 */       return (CountersProto)PARSER.parseFrom(input);
/*  4640:      */     }
/*  4641:      */     
/*  4642:      */     public static CountersProto parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry)
/*  4643:      */       throws IOException
/*  4644:      */     {
/*  4645: 4795 */       return (CountersProto)PARSER.parseFrom(input, extensionRegistry);
/*  4646:      */     }
/*  4647:      */     
/*  4648:      */     public static Builder newBuilder()
/*  4649:      */     {
/*  4650: 4798 */       return Builder.access$5700();
/*  4651:      */     }
/*  4652:      */     
/*  4653:      */     public Builder newBuilderForType()
/*  4654:      */     {
/*  4655: 4799 */       return newBuilder();
/*  4656:      */     }
/*  4657:      */     
/*  4658:      */     public static Builder newBuilder(CountersProto prototype)
/*  4659:      */     {
/*  4660: 4801 */       return newBuilder().mergeFrom(prototype);
/*  4661:      */     }
/*  4662:      */     
/*  4663:      */     public Builder toBuilder()
/*  4664:      */     {
/*  4665: 4803 */       return newBuilder(this);
/*  4666:      */     }
/*  4667:      */     
/*  4668:      */     protected Builder newBuilderForType(GeneratedMessage.BuilderParent parent)
/*  4669:      */     {
/*  4670: 4808 */       Builder builder = new Builder(parent, null);
/*  4671: 4809 */       return builder;
/*  4672:      */     }
/*  4673:      */     
/*  4674:      */     public static final class Builder
/*  4675:      */       extends GeneratedMessage.Builder<Builder>
/*  4676:      */       implements MRProtos.CountersProtoOrBuilder
/*  4677:      */     {
/*  4678:      */       private int bitField0_;
/*  4679:      */       
/*  4680:      */       public static final Descriptors.Descriptor getDescriptor()
/*  4681:      */       {
/*  4682: 4819 */         return MRProtos.internal_static_hadoop_mapreduce_CountersProto_descriptor;
/*  4683:      */       }
/*  4684:      */       
/*  4685:      */       protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable()
/*  4686:      */       {
/*  4687: 4824 */         return MRProtos.internal_static_hadoop_mapreduce_CountersProto_fieldAccessorTable.ensureFieldAccessorsInitialized(MRProtos.CountersProto.class, Builder.class);
/*  4688:      */       }
/*  4689:      */       
/*  4690:      */       private Builder()
/*  4691:      */       {
/*  4692: 4831 */         maybeForceBuilderInitialization();
/*  4693:      */       }
/*  4694:      */       
/*  4695:      */       private Builder(GeneratedMessage.BuilderParent parent)
/*  4696:      */       {
/*  4697: 4836 */         super();
/*  4698: 4837 */         maybeForceBuilderInitialization();
/*  4699:      */       }
/*  4700:      */       
/*  4701:      */       private void maybeForceBuilderInitialization()
/*  4702:      */       {
/*  4703: 4840 */         if (MRProtos.CountersProto.alwaysUseFieldBuilders) {
/*  4704: 4841 */           getCounterGroupsFieldBuilder();
/*  4705:      */         }
/*  4706:      */       }
/*  4707:      */       
/*  4708:      */       private static Builder create()
/*  4709:      */       {
/*  4710: 4845 */         return new Builder();
/*  4711:      */       }
/*  4712:      */       
/*  4713:      */       public Builder clear()
/*  4714:      */       {
/*  4715: 4849 */         super.clear();
/*  4716: 4850 */         if (this.counterGroupsBuilder_ == null)
/*  4717:      */         {
/*  4718: 4851 */           this.counterGroups_ = Collections.emptyList();
/*  4719: 4852 */           this.bitField0_ &= 0xFFFFFFFE;
/*  4720:      */         }
/*  4721:      */         else
/*  4722:      */         {
/*  4723: 4854 */           this.counterGroupsBuilder_.clear();
/*  4724:      */         }
/*  4725: 4856 */         return this;
/*  4726:      */       }
/*  4727:      */       
/*  4728:      */       public Builder clone()
/*  4729:      */       {
/*  4730: 4860 */         return create().mergeFrom(buildPartial());
/*  4731:      */       }
/*  4732:      */       
/*  4733:      */       public Descriptors.Descriptor getDescriptorForType()
/*  4734:      */       {
/*  4735: 4865 */         return MRProtos.internal_static_hadoop_mapreduce_CountersProto_descriptor;
/*  4736:      */       }
/*  4737:      */       
/*  4738:      */       public MRProtos.CountersProto getDefaultInstanceForType()
/*  4739:      */       {
/*  4740: 4869 */         return MRProtos.CountersProto.getDefaultInstance();
/*  4741:      */       }
/*  4742:      */       
/*  4743:      */       public MRProtos.CountersProto build()
/*  4744:      */       {
/*  4745: 4873 */         MRProtos.CountersProto result = buildPartial();
/*  4746: 4874 */         if (!result.isInitialized()) {
/*  4747: 4875 */           throw newUninitializedMessageException(result);
/*  4748:      */         }
/*  4749: 4877 */         return result;
/*  4750:      */       }
/*  4751:      */       
/*  4752:      */       public MRProtos.CountersProto buildPartial()
/*  4753:      */       {
/*  4754: 4881 */         MRProtos.CountersProto result = new MRProtos.CountersProto(this, null);
/*  4755: 4882 */         int from_bitField0_ = this.bitField0_;
/*  4756: 4883 */         if (this.counterGroupsBuilder_ == null)
/*  4757:      */         {
/*  4758: 4884 */           if ((this.bitField0_ & 0x1) == 1)
/*  4759:      */           {
/*  4760: 4885 */             this.counterGroups_ = Collections.unmodifiableList(this.counterGroups_);
/*  4761: 4886 */             this.bitField0_ &= 0xFFFFFFFE;
/*  4762:      */           }
/*  4763: 4888 */           result.counterGroups_ = this.counterGroups_;
/*  4764:      */         }
/*  4765:      */         else
/*  4766:      */         {
/*  4767: 4890 */           result.counterGroups_ = this.counterGroupsBuilder_.build();
/*  4768:      */         }
/*  4769: 4892 */         onBuilt();
/*  4770: 4893 */         return result;
/*  4771:      */       }
/*  4772:      */       
/*  4773:      */       public Builder mergeFrom(Message other)
/*  4774:      */       {
/*  4775: 4897 */         if ((other instanceof MRProtos.CountersProto)) {
/*  4776: 4898 */           return mergeFrom((MRProtos.CountersProto)other);
/*  4777:      */         }
/*  4778: 4900 */         super.mergeFrom(other);
/*  4779: 4901 */         return this;
/*  4780:      */       }
/*  4781:      */       
/*  4782:      */       public Builder mergeFrom(MRProtos.CountersProto other)
/*  4783:      */       {
/*  4784: 4906 */         if (other == MRProtos.CountersProto.getDefaultInstance()) {
/*  4785: 4906 */           return this;
/*  4786:      */         }
/*  4787: 4907 */         if (this.counterGroupsBuilder_ == null)
/*  4788:      */         {
/*  4789: 4908 */           if (!other.counterGroups_.isEmpty())
/*  4790:      */           {
/*  4791: 4909 */             if (this.counterGroups_.isEmpty())
/*  4792:      */             {
/*  4793: 4910 */               this.counterGroups_ = other.counterGroups_;
/*  4794: 4911 */               this.bitField0_ &= 0xFFFFFFFE;
/*  4795:      */             }
/*  4796:      */             else
/*  4797:      */             {
/*  4798: 4913 */               ensureCounterGroupsIsMutable();
/*  4799: 4914 */               this.counterGroups_.addAll(other.counterGroups_);
/*  4800:      */             }
/*  4801: 4916 */             onChanged();
/*  4802:      */           }
/*  4803:      */         }
/*  4804: 4919 */         else if (!other.counterGroups_.isEmpty()) {
/*  4805: 4920 */           if (this.counterGroupsBuilder_.isEmpty())
/*  4806:      */           {
/*  4807: 4921 */             this.counterGroupsBuilder_.dispose();
/*  4808: 4922 */             this.counterGroupsBuilder_ = null;
/*  4809: 4923 */             this.counterGroups_ = other.counterGroups_;
/*  4810: 4924 */             this.bitField0_ &= 0xFFFFFFFE;
/*  4811: 4925 */             this.counterGroupsBuilder_ = (MRProtos.CountersProto.alwaysUseFieldBuilders ? getCounterGroupsFieldBuilder() : null);
/*  4812:      */           }
/*  4813:      */           else
/*  4814:      */           {
/*  4815: 4929 */             this.counterGroupsBuilder_.addAllMessages(other.counterGroups_);
/*  4816:      */           }
/*  4817:      */         }
/*  4818: 4933 */         mergeUnknownFields(other.getUnknownFields());
/*  4819: 4934 */         return this;
/*  4820:      */       }
/*  4821:      */       
/*  4822:      */       public final boolean isInitialized()
/*  4823:      */       {
/*  4824: 4938 */         return true;
/*  4825:      */       }
/*  4826:      */       
/*  4827:      */       public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry)
/*  4828:      */         throws IOException
/*  4829:      */       {
/*  4830: 4945 */         MRProtos.CountersProto parsedMessage = null;
/*  4831:      */         try
/*  4832:      */         {
/*  4833: 4947 */           parsedMessage = (MRProtos.CountersProto)MRProtos.CountersProto.PARSER.parsePartialFrom(input, extensionRegistry);
/*  4834:      */         }
/*  4835:      */         catch (InvalidProtocolBufferException e)
/*  4836:      */         {
/*  4837: 4949 */           parsedMessage = (MRProtos.CountersProto)e.getUnfinishedMessage();
/*  4838: 4950 */           throw e;
/*  4839:      */         }
/*  4840:      */         finally
/*  4841:      */         {
/*  4842: 4952 */           if (parsedMessage != null) {
/*  4843: 4953 */             mergeFrom(parsedMessage);
/*  4844:      */           }
/*  4845:      */         }
/*  4846: 4956 */         return this;
/*  4847:      */       }
/*  4848:      */       
/*  4849: 4961 */       private List<MRProtos.StringCounterGroupMapProto> counterGroups_ = Collections.emptyList();
/*  4850:      */       private RepeatedFieldBuilder<MRProtos.StringCounterGroupMapProto, MRProtos.StringCounterGroupMapProto.Builder, MRProtos.StringCounterGroupMapProtoOrBuilder> counterGroupsBuilder_;
/*  4851:      */       
/*  4852:      */       private void ensureCounterGroupsIsMutable()
/*  4853:      */       {
/*  4854: 4964 */         if ((this.bitField0_ & 0x1) != 1)
/*  4855:      */         {
/*  4856: 4965 */           this.counterGroups_ = new ArrayList(this.counterGroups_);
/*  4857: 4966 */           this.bitField0_ |= 0x1;
/*  4858:      */         }
/*  4859:      */       }
/*  4860:      */       
/*  4861:      */       public List<MRProtos.StringCounterGroupMapProto> getCounterGroupsList()
/*  4862:      */       {
/*  4863: 4977 */         if (this.counterGroupsBuilder_ == null) {
/*  4864: 4978 */           return Collections.unmodifiableList(this.counterGroups_);
/*  4865:      */         }
/*  4866: 4980 */         return this.counterGroupsBuilder_.getMessageList();
/*  4867:      */       }
/*  4868:      */       
/*  4869:      */       public int getCounterGroupsCount()
/*  4870:      */       {
/*  4871: 4987 */         if (this.counterGroupsBuilder_ == null) {
/*  4872: 4988 */           return this.counterGroups_.size();
/*  4873:      */         }
/*  4874: 4990 */         return this.counterGroupsBuilder_.getCount();
/*  4875:      */       }
/*  4876:      */       
/*  4877:      */       public MRProtos.StringCounterGroupMapProto getCounterGroups(int index)
/*  4878:      */       {
/*  4879: 4997 */         if (this.counterGroupsBuilder_ == null) {
/*  4880: 4998 */           return (MRProtos.StringCounterGroupMapProto)this.counterGroups_.get(index);
/*  4881:      */         }
/*  4882: 5000 */         return (MRProtos.StringCounterGroupMapProto)this.counterGroupsBuilder_.getMessage(index);
/*  4883:      */       }
/*  4884:      */       
/*  4885:      */       public Builder setCounterGroups(int index, MRProtos.StringCounterGroupMapProto value)
/*  4886:      */       {
/*  4887: 5008 */         if (this.counterGroupsBuilder_ == null)
/*  4888:      */         {
/*  4889: 5009 */           if (value == null) {
/*  4890: 5010 */             throw new NullPointerException();
/*  4891:      */           }
/*  4892: 5012 */           ensureCounterGroupsIsMutable();
/*  4893: 5013 */           this.counterGroups_.set(index, value);
/*  4894: 5014 */           onChanged();
/*  4895:      */         }
/*  4896:      */         else
/*  4897:      */         {
/*  4898: 5016 */           this.counterGroupsBuilder_.setMessage(index, value);
/*  4899:      */         }
/*  4900: 5018 */         return this;
/*  4901:      */       }
/*  4902:      */       
/*  4903:      */       public Builder setCounterGroups(int index, MRProtos.StringCounterGroupMapProto.Builder builderForValue)
/*  4904:      */       {
/*  4905: 5025 */         if (this.counterGroupsBuilder_ == null)
/*  4906:      */         {
/*  4907: 5026 */           ensureCounterGroupsIsMutable();
/*  4908: 5027 */           this.counterGroups_.set(index, builderForValue.build());
/*  4909: 5028 */           onChanged();
/*  4910:      */         }
/*  4911:      */         else
/*  4912:      */         {
/*  4913: 5030 */           this.counterGroupsBuilder_.setMessage(index, builderForValue.build());
/*  4914:      */         }
/*  4915: 5032 */         return this;
/*  4916:      */       }
/*  4917:      */       
/*  4918:      */       public Builder addCounterGroups(MRProtos.StringCounterGroupMapProto value)
/*  4919:      */       {
/*  4920: 5038 */         if (this.counterGroupsBuilder_ == null)
/*  4921:      */         {
/*  4922: 5039 */           if (value == null) {
/*  4923: 5040 */             throw new NullPointerException();
/*  4924:      */           }
/*  4925: 5042 */           ensureCounterGroupsIsMutable();
/*  4926: 5043 */           this.counterGroups_.add(value);
/*  4927: 5044 */           onChanged();
/*  4928:      */         }
/*  4929:      */         else
/*  4930:      */         {
/*  4931: 5046 */           this.counterGroupsBuilder_.addMessage(value);
/*  4932:      */         }
/*  4933: 5048 */         return this;
/*  4934:      */       }
/*  4935:      */       
/*  4936:      */       public Builder addCounterGroups(int index, MRProtos.StringCounterGroupMapProto value)
/*  4937:      */       {
/*  4938: 5055 */         if (this.counterGroupsBuilder_ == null)
/*  4939:      */         {
/*  4940: 5056 */           if (value == null) {
/*  4941: 5057 */             throw new NullPointerException();
/*  4942:      */           }
/*  4943: 5059 */           ensureCounterGroupsIsMutable();
/*  4944: 5060 */           this.counterGroups_.add(index, value);
/*  4945: 5061 */           onChanged();
/*  4946:      */         }
/*  4947:      */         else
/*  4948:      */         {
/*  4949: 5063 */           this.counterGroupsBuilder_.addMessage(index, value);
/*  4950:      */         }
/*  4951: 5065 */         return this;
/*  4952:      */       }
/*  4953:      */       
/*  4954:      */       public Builder addCounterGroups(MRProtos.StringCounterGroupMapProto.Builder builderForValue)
/*  4955:      */       {
/*  4956: 5072 */         if (this.counterGroupsBuilder_ == null)
/*  4957:      */         {
/*  4958: 5073 */           ensureCounterGroupsIsMutable();
/*  4959: 5074 */           this.counterGroups_.add(builderForValue.build());
/*  4960: 5075 */           onChanged();
/*  4961:      */         }
/*  4962:      */         else
/*  4963:      */         {
/*  4964: 5077 */           this.counterGroupsBuilder_.addMessage(builderForValue.build());
/*  4965:      */         }
/*  4966: 5079 */         return this;
/*  4967:      */       }
/*  4968:      */       
/*  4969:      */       public Builder addCounterGroups(int index, MRProtos.StringCounterGroupMapProto.Builder builderForValue)
/*  4970:      */       {
/*  4971: 5086 */         if (this.counterGroupsBuilder_ == null)
/*  4972:      */         {
/*  4973: 5087 */           ensureCounterGroupsIsMutable();
/*  4974: 5088 */           this.counterGroups_.add(index, builderForValue.build());
/*  4975: 5089 */           onChanged();
/*  4976:      */         }
/*  4977:      */         else
/*  4978:      */         {
/*  4979: 5091 */           this.counterGroupsBuilder_.addMessage(index, builderForValue.build());
/*  4980:      */         }
/*  4981: 5093 */         return this;
/*  4982:      */       }
/*  4983:      */       
/*  4984:      */       public Builder addAllCounterGroups(Iterable<? extends MRProtos.StringCounterGroupMapProto> values)
/*  4985:      */       {
/*  4986: 5100 */         if (this.counterGroupsBuilder_ == null)
/*  4987:      */         {
/*  4988: 5101 */           ensureCounterGroupsIsMutable();
/*  4989: 5102 */           GeneratedMessage.Builder.addAll(values, this.counterGroups_);
/*  4990: 5103 */           onChanged();
/*  4991:      */         }
/*  4992:      */         else
/*  4993:      */         {
/*  4994: 5105 */           this.counterGroupsBuilder_.addAllMessages(values);
/*  4995:      */         }
/*  4996: 5107 */         return this;
/*  4997:      */       }
/*  4998:      */       
/*  4999:      */       public Builder clearCounterGroups()
/*  5000:      */       {
/*  5001: 5113 */         if (this.counterGroupsBuilder_ == null)
/*  5002:      */         {
/*  5003: 5114 */           this.counterGroups_ = Collections.emptyList();
/*  5004: 5115 */           this.bitField0_ &= 0xFFFFFFFE;
/*  5005: 5116 */           onChanged();
/*  5006:      */         }
/*  5007:      */         else
/*  5008:      */         {
/*  5009: 5118 */           this.counterGroupsBuilder_.clear();
/*  5010:      */         }
/*  5011: 5120 */         return this;
/*  5012:      */       }
/*  5013:      */       
/*  5014:      */       public Builder removeCounterGroups(int index)
/*  5015:      */       {
/*  5016: 5126 */         if (this.counterGroupsBuilder_ == null)
/*  5017:      */         {
/*  5018: 5127 */           ensureCounterGroupsIsMutable();
/*  5019: 5128 */           this.counterGroups_.remove(index);
/*  5020: 5129 */           onChanged();
/*  5021:      */         }
/*  5022:      */         else
/*  5023:      */         {
/*  5024: 5131 */           this.counterGroupsBuilder_.remove(index);
/*  5025:      */         }
/*  5026: 5133 */         return this;
/*  5027:      */       }
/*  5028:      */       
/*  5029:      */       public MRProtos.StringCounterGroupMapProto.Builder getCounterGroupsBuilder(int index)
/*  5030:      */       {
/*  5031: 5140 */         return (MRProtos.StringCounterGroupMapProto.Builder)getCounterGroupsFieldBuilder().getBuilder(index);
/*  5032:      */       }
/*  5033:      */       
/*  5034:      */       public MRProtos.StringCounterGroupMapProtoOrBuilder getCounterGroupsOrBuilder(int index)
/*  5035:      */       {
/*  5036: 5147 */         if (this.counterGroupsBuilder_ == null) {
/*  5037: 5148 */           return (MRProtos.StringCounterGroupMapProtoOrBuilder)this.counterGroups_.get(index);
/*  5038:      */         }
/*  5039: 5149 */         return (MRProtos.StringCounterGroupMapProtoOrBuilder)this.counterGroupsBuilder_.getMessageOrBuilder(index);
/*  5040:      */       }
/*  5041:      */       
/*  5042:      */       public List<? extends MRProtos.StringCounterGroupMapProtoOrBuilder> getCounterGroupsOrBuilderList()
/*  5043:      */       {
/*  5044: 5157 */         if (this.counterGroupsBuilder_ != null) {
/*  5045: 5158 */           return this.counterGroupsBuilder_.getMessageOrBuilderList();
/*  5046:      */         }
/*  5047: 5160 */         return Collections.unmodifiableList(this.counterGroups_);
/*  5048:      */       }
/*  5049:      */       
/*  5050:      */       public MRProtos.StringCounterGroupMapProto.Builder addCounterGroupsBuilder()
/*  5051:      */       {
/*  5052: 5167 */         return (MRProtos.StringCounterGroupMapProto.Builder)getCounterGroupsFieldBuilder().addBuilder(MRProtos.StringCounterGroupMapProto.getDefaultInstance());
/*  5053:      */       }
/*  5054:      */       
/*  5055:      */       public MRProtos.StringCounterGroupMapProto.Builder addCounterGroupsBuilder(int index)
/*  5056:      */       {
/*  5057: 5175 */         return (MRProtos.StringCounterGroupMapProto.Builder)getCounterGroupsFieldBuilder().addBuilder(index, MRProtos.StringCounterGroupMapProto.getDefaultInstance());
/*  5058:      */       }
/*  5059:      */       
/*  5060:      */       public List<MRProtos.StringCounterGroupMapProto.Builder> getCounterGroupsBuilderList()
/*  5061:      */       {
/*  5062: 5183 */         return getCounterGroupsFieldBuilder().getBuilderList();
/*  5063:      */       }
/*  5064:      */       
/*  5065:      */       private RepeatedFieldBuilder<MRProtos.StringCounterGroupMapProto, MRProtos.StringCounterGroupMapProto.Builder, MRProtos.StringCounterGroupMapProtoOrBuilder> getCounterGroupsFieldBuilder()
/*  5066:      */       {
/*  5067: 5188 */         if (this.counterGroupsBuilder_ == null)
/*  5068:      */         {
/*  5069: 5189 */           this.counterGroupsBuilder_ = new RepeatedFieldBuilder(this.counterGroups_, (this.bitField0_ & 0x1) == 1, getParentForChildren(), isClean());
/*  5070:      */           
/*  5071:      */ 
/*  5072:      */ 
/*  5073:      */ 
/*  5074:      */ 
/*  5075: 5195 */           this.counterGroups_ = null;
/*  5076:      */         }
/*  5077: 5197 */         return this.counterGroupsBuilder_;
/*  5078:      */       }
/*  5079:      */     }
/*  5080:      */     
/*  5081:      */     static
/*  5082:      */     {
/*  5083: 5204 */       defaultInstance = new CountersProto(true);
/*  5084: 5205 */       defaultInstance.initFields();
/*  5085:      */     }
/*  5086:      */   }
/*  5087:      */   
/*  5088:      */   public static abstract interface TaskReportProtoOrBuilder
/*  5089:      */     extends MessageOrBuilder
/*  5090:      */   {
/*  5091:      */     public abstract boolean hasTaskId();
/*  5092:      */     
/*  5093:      */     public abstract MRProtos.TaskIdProto getTaskId();
/*  5094:      */     
/*  5095:      */     public abstract MRProtos.TaskIdProtoOrBuilder getTaskIdOrBuilder();
/*  5096:      */     
/*  5097:      */     public abstract boolean hasTaskState();
/*  5098:      */     
/*  5099:      */     public abstract MRProtos.TaskStateProto getTaskState();
/*  5100:      */     
/*  5101:      */     public abstract boolean hasProgress();
/*  5102:      */     
/*  5103:      */     public abstract float getProgress();
/*  5104:      */     
/*  5105:      */     public abstract boolean hasStartTime();
/*  5106:      */     
/*  5107:      */     public abstract long getStartTime();
/*  5108:      */     
/*  5109:      */     public abstract boolean hasFinishTime();
/*  5110:      */     
/*  5111:      */     public abstract long getFinishTime();
/*  5112:      */     
/*  5113:      */     public abstract boolean hasCounters();
/*  5114:      */     
/*  5115:      */     public abstract MRProtos.CountersProto getCounters();
/*  5116:      */     
/*  5117:      */     public abstract MRProtos.CountersProtoOrBuilder getCountersOrBuilder();
/*  5118:      */     
/*  5119:      */     public abstract List<MRProtos.TaskAttemptIdProto> getRunningAttemptsList();
/*  5120:      */     
/*  5121:      */     public abstract MRProtos.TaskAttemptIdProto getRunningAttempts(int paramInt);
/*  5122:      */     
/*  5123:      */     public abstract int getRunningAttemptsCount();
/*  5124:      */     
/*  5125:      */     public abstract List<? extends MRProtos.TaskAttemptIdProtoOrBuilder> getRunningAttemptsOrBuilderList();
/*  5126:      */     
/*  5127:      */     public abstract MRProtos.TaskAttemptIdProtoOrBuilder getRunningAttemptsOrBuilder(int paramInt);
/*  5128:      */     
/*  5129:      */     public abstract boolean hasSuccessfulAttempt();
/*  5130:      */     
/*  5131:      */     public abstract MRProtos.TaskAttemptIdProto getSuccessfulAttempt();
/*  5132:      */     
/*  5133:      */     public abstract MRProtos.TaskAttemptIdProtoOrBuilder getSuccessfulAttemptOrBuilder();
/*  5134:      */     
/*  5135:      */     public abstract List<String> getDiagnosticsList();
/*  5136:      */     
/*  5137:      */     public abstract int getDiagnosticsCount();
/*  5138:      */     
/*  5139:      */     public abstract String getDiagnostics(int paramInt);
/*  5140:      */     
/*  5141:      */     public abstract ByteString getDiagnosticsBytes(int paramInt);
/*  5142:      */   }
/*  5143:      */   
/*  5144:      */   public static final class TaskReportProto
/*  5145:      */     extends GeneratedMessage
/*  5146:      */     implements MRProtos.TaskReportProtoOrBuilder
/*  5147:      */   {
/*  5148:      */     private static final TaskReportProto defaultInstance;
/*  5149:      */     private final UnknownFieldSet unknownFields;
/*  5150:      */     
/*  5151:      */     private TaskReportProto(GeneratedMessage.Builder<?> builder)
/*  5152:      */     {
/*  5153: 5349 */       super();
/*  5154: 5350 */       this.unknownFields = builder.getUnknownFields();
/*  5155:      */     }
/*  5156:      */     
/*  5157:      */     private TaskReportProto(boolean noInit)
/*  5158:      */     {
/*  5159: 5352 */       this.unknownFields = UnknownFieldSet.getDefaultInstance();
/*  5160:      */     }
/*  5161:      */     
/*  5162:      */     public static TaskReportProto getDefaultInstance()
/*  5163:      */     {
/*  5164: 5356 */       return defaultInstance;
/*  5165:      */     }
/*  5166:      */     
/*  5167:      */     public TaskReportProto getDefaultInstanceForType()
/*  5168:      */     {
/*  5169: 5360 */       return defaultInstance;
/*  5170:      */     }
/*  5171:      */     
/*  5172:      */     public final UnknownFieldSet getUnknownFields()
/*  5173:      */     {
/*  5174: 5367 */       return this.unknownFields;
/*  5175:      */     }
/*  5176:      */     
/*  5177:      */     private TaskReportProto(CodedInputStream input, ExtensionRegistryLite extensionRegistry)
/*  5178:      */       throws InvalidProtocolBufferException
/*  5179:      */     {
/*  5180: 5373 */       initFields();
/*  5181: 5374 */       int mutable_bitField0_ = 0;
/*  5182: 5375 */       UnknownFieldSet.Builder unknownFields = UnknownFieldSet.newBuilder();
/*  5183:      */       try
/*  5184:      */       {
/*  5185: 5378 */         boolean done = false;
/*  5186: 5379 */         while (!done)
/*  5187:      */         {
/*  5188: 5380 */           int tag = input.readTag();
/*  5189: 5381 */           switch (tag)
/*  5190:      */           {
/*  5191:      */           case 0: 
/*  5192: 5383 */             done = true;
/*  5193: 5384 */             break;
/*  5194:      */           default: 
/*  5195: 5386 */             if (!parseUnknownField(input, unknownFields, extensionRegistry, tag)) {
/*  5196: 5388 */               done = true;
/*  5197:      */             }
/*  5198:      */             break;
/*  5199:      */           case 10: 
/*  5200: 5393 */             MRProtos.TaskIdProto.Builder subBuilder = null;
/*  5201: 5394 */             if ((this.bitField0_ & 0x1) == 1) {
/*  5202: 5395 */               subBuilder = this.taskId_.toBuilder();
/*  5203:      */             }
/*  5204: 5397 */             this.taskId_ = ((MRProtos.TaskIdProto)input.readMessage(MRProtos.TaskIdProto.PARSER, extensionRegistry));
/*  5205: 5398 */             if (subBuilder != null)
/*  5206:      */             {
/*  5207: 5399 */               subBuilder.mergeFrom(this.taskId_);
/*  5208: 5400 */               this.taskId_ = subBuilder.buildPartial();
/*  5209:      */             }
/*  5210: 5402 */             this.bitField0_ |= 0x1;
/*  5211: 5403 */             break;
/*  5212:      */           case 16: 
/*  5213: 5406 */             int rawValue = input.readEnum();
/*  5214: 5407 */             MRProtos.TaskStateProto value = MRProtos.TaskStateProto.valueOf(rawValue);
/*  5215: 5408 */             if (value == null)
/*  5216:      */             {
/*  5217: 5409 */               unknownFields.mergeVarintField(2, rawValue);
/*  5218:      */             }
/*  5219:      */             else
/*  5220:      */             {
/*  5221: 5411 */               this.bitField0_ |= 0x2;
/*  5222: 5412 */               this.taskState_ = value;
/*  5223:      */             }
/*  5224: 5414 */             break;
/*  5225:      */           case 29: 
/*  5226: 5417 */             this.bitField0_ |= 0x4;
/*  5227: 5418 */             this.progress_ = input.readFloat();
/*  5228: 5419 */             break;
/*  5229:      */           case 32: 
/*  5230: 5422 */             this.bitField0_ |= 0x8;
/*  5231: 5423 */             this.startTime_ = input.readInt64();
/*  5232: 5424 */             break;
/*  5233:      */           case 40: 
/*  5234: 5427 */             this.bitField0_ |= 0x10;
/*  5235: 5428 */             this.finishTime_ = input.readInt64();
/*  5236: 5429 */             break;
/*  5237:      */           case 50: 
/*  5238: 5432 */             MRProtos.CountersProto.Builder subBuilder = null;
/*  5239: 5433 */             if ((this.bitField0_ & 0x20) == 32) {
/*  5240: 5434 */               subBuilder = this.counters_.toBuilder();
/*  5241:      */             }
/*  5242: 5436 */             this.counters_ = ((MRProtos.CountersProto)input.readMessage(MRProtos.CountersProto.PARSER, extensionRegistry));
/*  5243: 5437 */             if (subBuilder != null)
/*  5244:      */             {
/*  5245: 5438 */               subBuilder.mergeFrom(this.counters_);
/*  5246: 5439 */               this.counters_ = subBuilder.buildPartial();
/*  5247:      */             }
/*  5248: 5441 */             this.bitField0_ |= 0x20;
/*  5249: 5442 */             break;
/*  5250:      */           case 58: 
/*  5251: 5445 */             if ((mutable_bitField0_ & 0x40) != 64)
/*  5252:      */             {
/*  5253: 5446 */               this.runningAttempts_ = new ArrayList();
/*  5254: 5447 */               mutable_bitField0_ |= 0x40;
/*  5255:      */             }
/*  5256: 5449 */             this.runningAttempts_.add(input.readMessage(MRProtos.TaskAttemptIdProto.PARSER, extensionRegistry));
/*  5257: 5450 */             break;
/*  5258:      */           case 66: 
/*  5259: 5453 */             MRProtos.TaskAttemptIdProto.Builder subBuilder = null;
/*  5260: 5454 */             if ((this.bitField0_ & 0x40) == 64) {
/*  5261: 5455 */               subBuilder = this.successfulAttempt_.toBuilder();
/*  5262:      */             }
/*  5263: 5457 */             this.successfulAttempt_ = ((MRProtos.TaskAttemptIdProto)input.readMessage(MRProtos.TaskAttemptIdProto.PARSER, extensionRegistry));
/*  5264: 5458 */             if (subBuilder != null)
/*  5265:      */             {
/*  5266: 5459 */               subBuilder.mergeFrom(this.successfulAttempt_);
/*  5267: 5460 */               this.successfulAttempt_ = subBuilder.buildPartial();
/*  5268:      */             }
/*  5269: 5462 */             this.bitField0_ |= 0x40;
/*  5270: 5463 */             break;
/*  5271:      */           case 74: 
/*  5272: 5466 */             if ((mutable_bitField0_ & 0x100) != 256)
/*  5273:      */             {
/*  5274: 5467 */               this.diagnostics_ = new LazyStringArrayList();
/*  5275: 5468 */               mutable_bitField0_ |= 0x100;
/*  5276:      */             }
/*  5277: 5470 */             this.diagnostics_.add(input.readBytes());
/*  5278:      */           }
/*  5279:      */         }
/*  5280:      */       }
/*  5281:      */       catch (InvalidProtocolBufferException e)
/*  5282:      */       {
/*  5283: 5476 */         throw e.setUnfinishedMessage(this);
/*  5284:      */       }
/*  5285:      */       catch (IOException e)
/*  5286:      */       {
/*  5287: 5478 */         throw new InvalidProtocolBufferException(e.getMessage()).setUnfinishedMessage(this);
/*  5288:      */       }
/*  5289:      */       finally
/*  5290:      */       {
/*  5291: 5481 */         if ((mutable_bitField0_ & 0x40) == 64) {
/*  5292: 5482 */           this.runningAttempts_ = Collections.unmodifiableList(this.runningAttempts_);
/*  5293:      */         }
/*  5294: 5484 */         if ((mutable_bitField0_ & 0x100) == 256) {
/*  5295: 5485 */           this.diagnostics_ = new UnmodifiableLazyStringList(this.diagnostics_);
/*  5296:      */         }
/*  5297: 5487 */         this.unknownFields = unknownFields.build();
/*  5298: 5488 */         makeExtensionsImmutable();
/*  5299:      */       }
/*  5300:      */     }
/*  5301:      */     
/*  5302:      */     public static final Descriptors.Descriptor getDescriptor()
/*  5303:      */     {
/*  5304: 5493 */       return MRProtos.internal_static_hadoop_mapreduce_TaskReportProto_descriptor;
/*  5305:      */     }
/*  5306:      */     
/*  5307:      */     protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable()
/*  5308:      */     {
/*  5309: 5498 */       return MRProtos.internal_static_hadoop_mapreduce_TaskReportProto_fieldAccessorTable.ensureFieldAccessorsInitialized(TaskReportProto.class, Builder.class);
/*  5310:      */     }
/*  5311:      */     
/*  5312: 5503 */     public static Parser<TaskReportProto> PARSER = new AbstractParser()
/*  5313:      */     {
/*  5314:      */       public MRProtos.TaskReportProto parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry)
/*  5315:      */         throws InvalidProtocolBufferException
/*  5316:      */       {
/*  5317: 5509 */         return new MRProtos.TaskReportProto(input, extensionRegistry, null);
/*  5318:      */       }
/*  5319:      */     };
/*  5320:      */     private int bitField0_;
/*  5321:      */     public static final int TASK_ID_FIELD_NUMBER = 1;
/*  5322:      */     private MRProtos.TaskIdProto taskId_;
/*  5323:      */     public static final int TASK_STATE_FIELD_NUMBER = 2;
/*  5324:      */     private MRProtos.TaskStateProto taskState_;
/*  5325:      */     public static final int PROGRESS_FIELD_NUMBER = 3;
/*  5326:      */     private float progress_;
/*  5327:      */     public static final int START_TIME_FIELD_NUMBER = 4;
/*  5328:      */     private long startTime_;
/*  5329:      */     public static final int FINISH_TIME_FIELD_NUMBER = 5;
/*  5330:      */     private long finishTime_;
/*  5331:      */     public static final int COUNTERS_FIELD_NUMBER = 6;
/*  5332:      */     private MRProtos.CountersProto counters_;
/*  5333:      */     public static final int RUNNING_ATTEMPTS_FIELD_NUMBER = 7;
/*  5334:      */     private List<MRProtos.TaskAttemptIdProto> runningAttempts_;
/*  5335:      */     public static final int SUCCESSFUL_ATTEMPT_FIELD_NUMBER = 8;
/*  5336:      */     private MRProtos.TaskAttemptIdProto successfulAttempt_;
/*  5337:      */     public static final int DIAGNOSTICS_FIELD_NUMBER = 9;
/*  5338:      */     private LazyStringList diagnostics_;
/*  5339:      */     
/*  5340:      */     public Parser<TaskReportProto> getParserForType()
/*  5341:      */     {
/*  5342: 5515 */       return PARSER;
/*  5343:      */     }
/*  5344:      */     
/*  5345:      */     public boolean hasTaskId()
/*  5346:      */     {
/*  5347: 5526 */       return (this.bitField0_ & 0x1) == 1;
/*  5348:      */     }
/*  5349:      */     
/*  5350:      */     public MRProtos.TaskIdProto getTaskId()
/*  5351:      */     {
/*  5352: 5532 */       return this.taskId_;
/*  5353:      */     }
/*  5354:      */     
/*  5355:      */     public MRProtos.TaskIdProtoOrBuilder getTaskIdOrBuilder()
/*  5356:      */     {
/*  5357: 5538 */       return this.taskId_;
/*  5358:      */     }
/*  5359:      */     
/*  5360:      */     public boolean hasTaskState()
/*  5361:      */     {
/*  5362: 5548 */       return (this.bitField0_ & 0x2) == 2;
/*  5363:      */     }
/*  5364:      */     
/*  5365:      */     public MRProtos.TaskStateProto getTaskState()
/*  5366:      */     {
/*  5367: 5554 */       return this.taskState_;
/*  5368:      */     }
/*  5369:      */     
/*  5370:      */     public boolean hasProgress()
/*  5371:      */     {
/*  5372: 5564 */       return (this.bitField0_ & 0x4) == 4;
/*  5373:      */     }
/*  5374:      */     
/*  5375:      */     public float getProgress()
/*  5376:      */     {
/*  5377: 5570 */       return this.progress_;
/*  5378:      */     }
/*  5379:      */     
/*  5380:      */     public boolean hasStartTime()
/*  5381:      */     {
/*  5382: 5580 */       return (this.bitField0_ & 0x8) == 8;
/*  5383:      */     }
/*  5384:      */     
/*  5385:      */     public long getStartTime()
/*  5386:      */     {
/*  5387: 5586 */       return this.startTime_;
/*  5388:      */     }
/*  5389:      */     
/*  5390:      */     public boolean hasFinishTime()
/*  5391:      */     {
/*  5392: 5596 */       return (this.bitField0_ & 0x10) == 16;
/*  5393:      */     }
/*  5394:      */     
/*  5395:      */     public long getFinishTime()
/*  5396:      */     {
/*  5397: 5602 */       return this.finishTime_;
/*  5398:      */     }
/*  5399:      */     
/*  5400:      */     public boolean hasCounters()
/*  5401:      */     {
/*  5402: 5612 */       return (this.bitField0_ & 0x20) == 32;
/*  5403:      */     }
/*  5404:      */     
/*  5405:      */     public MRProtos.CountersProto getCounters()
/*  5406:      */     {
/*  5407: 5618 */       return this.counters_;
/*  5408:      */     }
/*  5409:      */     
/*  5410:      */     public MRProtos.CountersProtoOrBuilder getCountersOrBuilder()
/*  5411:      */     {
/*  5412: 5624 */       return this.counters_;
/*  5413:      */     }
/*  5414:      */     
/*  5415:      */     public List<MRProtos.TaskAttemptIdProto> getRunningAttemptsList()
/*  5416:      */     {
/*  5417: 5634 */       return this.runningAttempts_;
/*  5418:      */     }
/*  5419:      */     
/*  5420:      */     public List<? extends MRProtos.TaskAttemptIdProtoOrBuilder> getRunningAttemptsOrBuilderList()
/*  5421:      */     {
/*  5422: 5641 */       return this.runningAttempts_;
/*  5423:      */     }
/*  5424:      */     
/*  5425:      */     public int getRunningAttemptsCount()
/*  5426:      */     {
/*  5427: 5647 */       return this.runningAttempts_.size();
/*  5428:      */     }
/*  5429:      */     
/*  5430:      */     public MRProtos.TaskAttemptIdProto getRunningAttempts(int index)
/*  5431:      */     {
/*  5432: 5653 */       return (MRProtos.TaskAttemptIdProto)this.runningAttempts_.get(index);
/*  5433:      */     }
/*  5434:      */     
/*  5435:      */     public MRProtos.TaskAttemptIdProtoOrBuilder getRunningAttemptsOrBuilder(int index)
/*  5436:      */     {
/*  5437: 5660 */       return (MRProtos.TaskAttemptIdProtoOrBuilder)this.runningAttempts_.get(index);
/*  5438:      */     }
/*  5439:      */     
/*  5440:      */     public boolean hasSuccessfulAttempt()
/*  5441:      */     {
/*  5442: 5670 */       return (this.bitField0_ & 0x40) == 64;
/*  5443:      */     }
/*  5444:      */     
/*  5445:      */     public MRProtos.TaskAttemptIdProto getSuccessfulAttempt()
/*  5446:      */     {
/*  5447: 5676 */       return this.successfulAttempt_;
/*  5448:      */     }
/*  5449:      */     
/*  5450:      */     public MRProtos.TaskAttemptIdProtoOrBuilder getSuccessfulAttemptOrBuilder()
/*  5451:      */     {
/*  5452: 5682 */       return this.successfulAttempt_;
/*  5453:      */     }
/*  5454:      */     
/*  5455:      */     public List<String> getDiagnosticsList()
/*  5456:      */     {
/*  5457: 5693 */       return this.diagnostics_;
/*  5458:      */     }
/*  5459:      */     
/*  5460:      */     public int getDiagnosticsCount()
/*  5461:      */     {
/*  5462: 5699 */       return this.diagnostics_.size();
/*  5463:      */     }
/*  5464:      */     
/*  5465:      */     public String getDiagnostics(int index)
/*  5466:      */     {
/*  5467: 5705 */       return (String)this.diagnostics_.get(index);
/*  5468:      */     }
/*  5469:      */     
/*  5470:      */     public ByteString getDiagnosticsBytes(int index)
/*  5471:      */     {
/*  5472: 5712 */       return this.diagnostics_.getByteString(index);
/*  5473:      */     }
/*  5474:      */     
/*  5475:      */     private void initFields()
/*  5476:      */     {
/*  5477: 5716 */       this.taskId_ = MRProtos.TaskIdProto.getDefaultInstance();
/*  5478: 5717 */       this.taskState_ = MRProtos.TaskStateProto.TS_NEW;
/*  5479: 5718 */       this.progress_ = 0.0F;
/*  5480: 5719 */       this.startTime_ = 0L;
/*  5481: 5720 */       this.finishTime_ = 0L;
/*  5482: 5721 */       this.counters_ = MRProtos.CountersProto.getDefaultInstance();
/*  5483: 5722 */       this.runningAttempts_ = Collections.emptyList();
/*  5484: 5723 */       this.successfulAttempt_ = MRProtos.TaskAttemptIdProto.getDefaultInstance();
/*  5485: 5724 */       this.diagnostics_ = LazyStringArrayList.EMPTY;
/*  5486:      */     }
/*  5487:      */     
/*  5488: 5726 */     private byte memoizedIsInitialized = -1;
/*  5489:      */     
/*  5490:      */     public final boolean isInitialized()
/*  5491:      */     {
/*  5492: 5728 */       byte isInitialized = this.memoizedIsInitialized;
/*  5493: 5729 */       if (isInitialized != -1) {
/*  5494: 5729 */         return isInitialized == 1;
/*  5495:      */       }
/*  5496: 5731 */       this.memoizedIsInitialized = 1;
/*  5497: 5732 */       return true;
/*  5498:      */     }
/*  5499:      */     
/*  5500:      */     public void writeTo(CodedOutputStream output)
/*  5501:      */       throws IOException
/*  5502:      */     {
/*  5503: 5737 */       getSerializedSize();
/*  5504: 5738 */       if ((this.bitField0_ & 0x1) == 1) {
/*  5505: 5739 */         output.writeMessage(1, this.taskId_);
/*  5506:      */       }
/*  5507: 5741 */       if ((this.bitField0_ & 0x2) == 2) {
/*  5508: 5742 */         output.writeEnum(2, this.taskState_.getNumber());
/*  5509:      */       }
/*  5510: 5744 */       if ((this.bitField0_ & 0x4) == 4) {
/*  5511: 5745 */         output.writeFloat(3, this.progress_);
/*  5512:      */       }
/*  5513: 5747 */       if ((this.bitField0_ & 0x8) == 8) {
/*  5514: 5748 */         output.writeInt64(4, this.startTime_);
/*  5515:      */       }
/*  5516: 5750 */       if ((this.bitField0_ & 0x10) == 16) {
/*  5517: 5751 */         output.writeInt64(5, this.finishTime_);
/*  5518:      */       }
/*  5519: 5753 */       if ((this.bitField0_ & 0x20) == 32) {
/*  5520: 5754 */         output.writeMessage(6, this.counters_);
/*  5521:      */       }
/*  5522: 5756 */       for (int i = 0; i < this.runningAttempts_.size(); i++) {
/*  5523: 5757 */         output.writeMessage(7, (MessageLite)this.runningAttempts_.get(i));
/*  5524:      */       }
/*  5525: 5759 */       if ((this.bitField0_ & 0x40) == 64) {
/*  5526: 5760 */         output.writeMessage(8, this.successfulAttempt_);
/*  5527:      */       }
/*  5528: 5762 */       for (int i = 0; i < this.diagnostics_.size(); i++) {
/*  5529: 5763 */         output.writeBytes(9, this.diagnostics_.getByteString(i));
/*  5530:      */       }
/*  5531: 5765 */       getUnknownFields().writeTo(output);
/*  5532:      */     }
/*  5533:      */     
/*  5534: 5768 */     private int memoizedSerializedSize = -1;
/*  5535:      */     private static final long serialVersionUID = 0L;
/*  5536:      */     
/*  5537:      */     public int getSerializedSize()
/*  5538:      */     {
/*  5539: 5770 */       int size = this.memoizedSerializedSize;
/*  5540: 5771 */       if (size != -1) {
/*  5541: 5771 */         return size;
/*  5542:      */       }
/*  5543: 5773 */       size = 0;
/*  5544: 5774 */       if ((this.bitField0_ & 0x1) == 1) {
/*  5545: 5775 */         size += CodedOutputStream.computeMessageSize(1, this.taskId_);
/*  5546:      */       }
/*  5547: 5778 */       if ((this.bitField0_ & 0x2) == 2) {
/*  5548: 5779 */         size += CodedOutputStream.computeEnumSize(2, this.taskState_.getNumber());
/*  5549:      */       }
/*  5550: 5782 */       if ((this.bitField0_ & 0x4) == 4) {
/*  5551: 5783 */         size += CodedOutputStream.computeFloatSize(3, this.progress_);
/*  5552:      */       }
/*  5553: 5786 */       if ((this.bitField0_ & 0x8) == 8) {
/*  5554: 5787 */         size += CodedOutputStream.computeInt64Size(4, this.startTime_);
/*  5555:      */       }
/*  5556: 5790 */       if ((this.bitField0_ & 0x10) == 16) {
/*  5557: 5791 */         size += CodedOutputStream.computeInt64Size(5, this.finishTime_);
/*  5558:      */       }
/*  5559: 5794 */       if ((this.bitField0_ & 0x20) == 32) {
/*  5560: 5795 */         size += CodedOutputStream.computeMessageSize(6, this.counters_);
/*  5561:      */       }
/*  5562: 5798 */       for (int i = 0; i < this.runningAttempts_.size(); i++) {
/*  5563: 5799 */         size += CodedOutputStream.computeMessageSize(7, (MessageLite)this.runningAttempts_.get(i));
/*  5564:      */       }
/*  5565: 5802 */       if ((this.bitField0_ & 0x40) == 64) {
/*  5566: 5803 */         size += CodedOutputStream.computeMessageSize(8, this.successfulAttempt_);
/*  5567:      */       }
/*  5568: 5807 */       int dataSize = 0;
/*  5569: 5808 */       for (int i = 0; i < this.diagnostics_.size(); i++) {
/*  5570: 5809 */         dataSize += CodedOutputStream.computeBytesSizeNoTag(this.diagnostics_.getByteString(i));
/*  5571:      */       }
/*  5572: 5812 */       size += dataSize;
/*  5573: 5813 */       size += 1 * getDiagnosticsList().size();
/*  5574:      */       
/*  5575: 5815 */       size += getUnknownFields().getSerializedSize();
/*  5576: 5816 */       this.memoizedSerializedSize = size;
/*  5577: 5817 */       return size;
/*  5578:      */     }
/*  5579:      */     
/*  5580:      */     protected Object writeReplace()
/*  5581:      */       throws ObjectStreamException
/*  5582:      */     {
/*  5583: 5824 */       return super.writeReplace();
/*  5584:      */     }
/*  5585:      */     
/*  5586:      */     public boolean equals(Object obj)
/*  5587:      */     {
/*  5588: 5829 */       if (obj == this) {
/*  5589: 5830 */         return true;
/*  5590:      */       }
/*  5591: 5832 */       if (!(obj instanceof TaskReportProto)) {
/*  5592: 5833 */         return super.equals(obj);
/*  5593:      */       }
/*  5594: 5835 */       TaskReportProto other = (TaskReportProto)obj;
/*  5595:      */       
/*  5596: 5837 */       boolean result = true;
/*  5597: 5838 */       result = (result) && (hasTaskId() == other.hasTaskId());
/*  5598: 5839 */       if (hasTaskId()) {
/*  5599: 5840 */         result = (result) && (getTaskId().equals(other.getTaskId()));
/*  5600:      */       }
/*  5601: 5843 */       result = (result) && (hasTaskState() == other.hasTaskState());
/*  5602: 5844 */       if (hasTaskState()) {
/*  5603: 5845 */         result = (result) && (getTaskState() == other.getTaskState());
/*  5604:      */       }
/*  5605: 5848 */       result = (result) && (hasProgress() == other.hasProgress());
/*  5606: 5849 */       if (hasProgress()) {
/*  5607: 5850 */         result = (result) && (Float.floatToIntBits(getProgress()) == Float.floatToIntBits(other.getProgress()));
/*  5608:      */       }
/*  5609: 5852 */       result = (result) && (hasStartTime() == other.hasStartTime());
/*  5610: 5853 */       if (hasStartTime()) {
/*  5611: 5854 */         result = (result) && (getStartTime() == other.getStartTime());
/*  5612:      */       }
/*  5613: 5857 */       result = (result) && (hasFinishTime() == other.hasFinishTime());
/*  5614: 5858 */       if (hasFinishTime()) {
/*  5615: 5859 */         result = (result) && (getFinishTime() == other.getFinishTime());
/*  5616:      */       }
/*  5617: 5862 */       result = (result) && (hasCounters() == other.hasCounters());
/*  5618: 5863 */       if (hasCounters()) {
/*  5619: 5864 */         result = (result) && (getCounters().equals(other.getCounters()));
/*  5620:      */       }
/*  5621: 5867 */       result = (result) && (getRunningAttemptsList().equals(other.getRunningAttemptsList()));
/*  5622:      */       
/*  5623: 5869 */       result = (result) && (hasSuccessfulAttempt() == other.hasSuccessfulAttempt());
/*  5624: 5870 */       if (hasSuccessfulAttempt()) {
/*  5625: 5871 */         result = (result) && (getSuccessfulAttempt().equals(other.getSuccessfulAttempt()));
/*  5626:      */       }
/*  5627: 5874 */       result = (result) && (getDiagnosticsList().equals(other.getDiagnosticsList()));
/*  5628:      */       
/*  5629: 5876 */       result = (result) && (getUnknownFields().equals(other.getUnknownFields()));
/*  5630:      */       
/*  5631: 5878 */       return result;
/*  5632:      */     }
/*  5633:      */     
/*  5634: 5881 */     private int memoizedHashCode = 0;
/*  5635:      */     
/*  5636:      */     public int hashCode()
/*  5637:      */     {
/*  5638: 5884 */       if (this.memoizedHashCode != 0) {
/*  5639: 5885 */         return this.memoizedHashCode;
/*  5640:      */       }
/*  5641: 5887 */       int hash = 41;
/*  5642: 5888 */       hash = 19 * hash + getDescriptorForType().hashCode();
/*  5643: 5889 */       if (hasTaskId())
/*  5644:      */       {
/*  5645: 5890 */         hash = 37 * hash + 1;
/*  5646: 5891 */         hash = 53 * hash + getTaskId().hashCode();
/*  5647:      */       }
/*  5648: 5893 */       if (hasTaskState())
/*  5649:      */       {
/*  5650: 5894 */         hash = 37 * hash + 2;
/*  5651: 5895 */         hash = 53 * hash + hashEnum(getTaskState());
/*  5652:      */       }
/*  5653: 5897 */       if (hasProgress())
/*  5654:      */       {
/*  5655: 5898 */         hash = 37 * hash + 3;
/*  5656: 5899 */         hash = 53 * hash + Float.floatToIntBits(getProgress());
/*  5657:      */       }
/*  5658: 5902 */       if (hasStartTime())
/*  5659:      */       {
/*  5660: 5903 */         hash = 37 * hash + 4;
/*  5661: 5904 */         hash = 53 * hash + hashLong(getStartTime());
/*  5662:      */       }
/*  5663: 5906 */       if (hasFinishTime())
/*  5664:      */       {
/*  5665: 5907 */         hash = 37 * hash + 5;
/*  5666: 5908 */         hash = 53 * hash + hashLong(getFinishTime());
/*  5667:      */       }
/*  5668: 5910 */       if (hasCounters())
/*  5669:      */       {
/*  5670: 5911 */         hash = 37 * hash + 6;
/*  5671: 5912 */         hash = 53 * hash + getCounters().hashCode();
/*  5672:      */       }
/*  5673: 5914 */       if (getRunningAttemptsCount() > 0)
/*  5674:      */       {
/*  5675: 5915 */         hash = 37 * hash + 7;
/*  5676: 5916 */         hash = 53 * hash + getRunningAttemptsList().hashCode();
/*  5677:      */       }
/*  5678: 5918 */       if (hasSuccessfulAttempt())
/*  5679:      */       {
/*  5680: 5919 */         hash = 37 * hash + 8;
/*  5681: 5920 */         hash = 53 * hash + getSuccessfulAttempt().hashCode();
/*  5682:      */       }
/*  5683: 5922 */       if (getDiagnosticsCount() > 0)
/*  5684:      */       {
/*  5685: 5923 */         hash = 37 * hash + 9;
/*  5686: 5924 */         hash = 53 * hash + getDiagnosticsList().hashCode();
/*  5687:      */       }
/*  5688: 5926 */       hash = 29 * hash + getUnknownFields().hashCode();
/*  5689: 5927 */       this.memoizedHashCode = hash;
/*  5690: 5928 */       return hash;
/*  5691:      */     }
/*  5692:      */     
/*  5693:      */     public static TaskReportProto parseFrom(ByteString data)
/*  5694:      */       throws InvalidProtocolBufferException
/*  5695:      */     {
/*  5696: 5934 */       return (TaskReportProto)PARSER.parseFrom(data);
/*  5697:      */     }
/*  5698:      */     
/*  5699:      */     public static TaskReportProto parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry)
/*  5700:      */       throws InvalidProtocolBufferException
/*  5701:      */     {
/*  5702: 5940 */       return (TaskReportProto)PARSER.parseFrom(data, extensionRegistry);
/*  5703:      */     }
/*  5704:      */     
/*  5705:      */     public static TaskReportProto parseFrom(byte[] data)
/*  5706:      */       throws InvalidProtocolBufferException
/*  5707:      */     {
/*  5708: 5944 */       return (TaskReportProto)PARSER.parseFrom(data);
/*  5709:      */     }
/*  5710:      */     
/*  5711:      */     public static TaskReportProto parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry)
/*  5712:      */       throws InvalidProtocolBufferException
/*  5713:      */     {
/*  5714: 5950 */       return (TaskReportProto)PARSER.parseFrom(data, extensionRegistry);
/*  5715:      */     }
/*  5716:      */     
/*  5717:      */     public static TaskReportProto parseFrom(InputStream input)
/*  5718:      */       throws IOException
/*  5719:      */     {
/*  5720: 5954 */       return (TaskReportProto)PARSER.parseFrom(input);
/*  5721:      */     }
/*  5722:      */     
/*  5723:      */     public static TaskReportProto parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry)
/*  5724:      */       throws IOException
/*  5725:      */     {
/*  5726: 5960 */       return (TaskReportProto)PARSER.parseFrom(input, extensionRegistry);
/*  5727:      */     }
/*  5728:      */     
/*  5729:      */     public static TaskReportProto parseDelimitedFrom(InputStream input)
/*  5730:      */       throws IOException
/*  5731:      */     {
/*  5732: 5964 */       return (TaskReportProto)PARSER.parseDelimitedFrom(input);
/*  5733:      */     }
/*  5734:      */     
/*  5735:      */     public static TaskReportProto parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry)
/*  5736:      */       throws IOException
/*  5737:      */     {
/*  5738: 5970 */       return (TaskReportProto)PARSER.parseDelimitedFrom(input, extensionRegistry);
/*  5739:      */     }
/*  5740:      */     
/*  5741:      */     public static TaskReportProto parseFrom(CodedInputStream input)
/*  5742:      */       throws IOException
/*  5743:      */     {
/*  5744: 5975 */       return (TaskReportProto)PARSER.parseFrom(input);
/*  5745:      */     }
/*  5746:      */     
/*  5747:      */     public static TaskReportProto parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry)
/*  5748:      */       throws IOException
/*  5749:      */     {
/*  5750: 5981 */       return (TaskReportProto)PARSER.parseFrom(input, extensionRegistry);
/*  5751:      */     }
/*  5752:      */     
/*  5753:      */     public static Builder newBuilder()
/*  5754:      */     {
/*  5755: 5984 */       return Builder.access$6600();
/*  5756:      */     }
/*  5757:      */     
/*  5758:      */     public Builder newBuilderForType()
/*  5759:      */     {
/*  5760: 5985 */       return newBuilder();
/*  5761:      */     }
/*  5762:      */     
/*  5763:      */     public static Builder newBuilder(TaskReportProto prototype)
/*  5764:      */     {
/*  5765: 5987 */       return newBuilder().mergeFrom(prototype);
/*  5766:      */     }
/*  5767:      */     
/*  5768:      */     public Builder toBuilder()
/*  5769:      */     {
/*  5770: 5989 */       return newBuilder(this);
/*  5771:      */     }
/*  5772:      */     
/*  5773:      */     protected Builder newBuilderForType(GeneratedMessage.BuilderParent parent)
/*  5774:      */     {
/*  5775: 5994 */       Builder builder = new Builder(parent, null);
/*  5776: 5995 */       return builder;
/*  5777:      */     }
/*  5778:      */     
/*  5779:      */     public static final class Builder
/*  5780:      */       extends GeneratedMessage.Builder<Builder>
/*  5781:      */       implements MRProtos.TaskReportProtoOrBuilder
/*  5782:      */     {
/*  5783:      */       private int bitField0_;
/*  5784:      */       
/*  5785:      */       public static final Descriptors.Descriptor getDescriptor()
/*  5786:      */       {
/*  5787: 6005 */         return MRProtos.internal_static_hadoop_mapreduce_TaskReportProto_descriptor;
/*  5788:      */       }
/*  5789:      */       
/*  5790:      */       protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable()
/*  5791:      */       {
/*  5792: 6010 */         return MRProtos.internal_static_hadoop_mapreduce_TaskReportProto_fieldAccessorTable.ensureFieldAccessorsInitialized(MRProtos.TaskReportProto.class, Builder.class);
/*  5793:      */       }
/*  5794:      */       
/*  5795:      */       private Builder()
/*  5796:      */       {
/*  5797: 6017 */         maybeForceBuilderInitialization();
/*  5798:      */       }
/*  5799:      */       
/*  5800:      */       private Builder(GeneratedMessage.BuilderParent parent)
/*  5801:      */       {
/*  5802: 6022 */         super();
/*  5803: 6023 */         maybeForceBuilderInitialization();
/*  5804:      */       }
/*  5805:      */       
/*  5806:      */       private void maybeForceBuilderInitialization()
/*  5807:      */       {
/*  5808: 6026 */         if (MRProtos.TaskReportProto.alwaysUseFieldBuilders)
/*  5809:      */         {
/*  5810: 6027 */           getTaskIdFieldBuilder();
/*  5811: 6028 */           getCountersFieldBuilder();
/*  5812: 6029 */           getRunningAttemptsFieldBuilder();
/*  5813: 6030 */           getSuccessfulAttemptFieldBuilder();
/*  5814:      */         }
/*  5815:      */       }
/*  5816:      */       
/*  5817:      */       private static Builder create()
/*  5818:      */       {
/*  5819: 6034 */         return new Builder();
/*  5820:      */       }
/*  5821:      */       
/*  5822:      */       public Builder clear()
/*  5823:      */       {
/*  5824: 6038 */         super.clear();
/*  5825: 6039 */         if (this.taskIdBuilder_ == null) {
/*  5826: 6040 */           this.taskId_ = MRProtos.TaskIdProto.getDefaultInstance();
/*  5827:      */         } else {
/*  5828: 6042 */           this.taskIdBuilder_.clear();
/*  5829:      */         }
/*  5830: 6044 */         this.bitField0_ &= 0xFFFFFFFE;
/*  5831: 6045 */         this.taskState_ = MRProtos.TaskStateProto.TS_NEW;
/*  5832: 6046 */         this.bitField0_ &= 0xFFFFFFFD;
/*  5833: 6047 */         this.progress_ = 0.0F;
/*  5834: 6048 */         this.bitField0_ &= 0xFFFFFFFB;
/*  5835: 6049 */         this.startTime_ = 0L;
/*  5836: 6050 */         this.bitField0_ &= 0xFFFFFFF7;
/*  5837: 6051 */         this.finishTime_ = 0L;
/*  5838: 6052 */         this.bitField0_ &= 0xFFFFFFEF;
/*  5839: 6053 */         if (this.countersBuilder_ == null) {
/*  5840: 6054 */           this.counters_ = MRProtos.CountersProto.getDefaultInstance();
/*  5841:      */         } else {
/*  5842: 6056 */           this.countersBuilder_.clear();
/*  5843:      */         }
/*  5844: 6058 */         this.bitField0_ &= 0xFFFFFFDF;
/*  5845: 6059 */         if (this.runningAttemptsBuilder_ == null)
/*  5846:      */         {
/*  5847: 6060 */           this.runningAttempts_ = Collections.emptyList();
/*  5848: 6061 */           this.bitField0_ &= 0xFFFFFFBF;
/*  5849:      */         }
/*  5850:      */         else
/*  5851:      */         {
/*  5852: 6063 */           this.runningAttemptsBuilder_.clear();
/*  5853:      */         }
/*  5854: 6065 */         if (this.successfulAttemptBuilder_ == null) {
/*  5855: 6066 */           this.successfulAttempt_ = MRProtos.TaskAttemptIdProto.getDefaultInstance();
/*  5856:      */         } else {
/*  5857: 6068 */           this.successfulAttemptBuilder_.clear();
/*  5858:      */         }
/*  5859: 6070 */         this.bitField0_ &= 0xFFFFFF7F;
/*  5860: 6071 */         this.diagnostics_ = LazyStringArrayList.EMPTY;
/*  5861: 6072 */         this.bitField0_ &= 0xFFFFFEFF;
/*  5862: 6073 */         return this;
/*  5863:      */       }
/*  5864:      */       
/*  5865:      */       public Builder clone()
/*  5866:      */       {
/*  5867: 6077 */         return create().mergeFrom(buildPartial());
/*  5868:      */       }
/*  5869:      */       
/*  5870:      */       public Descriptors.Descriptor getDescriptorForType()
/*  5871:      */       {
/*  5872: 6082 */         return MRProtos.internal_static_hadoop_mapreduce_TaskReportProto_descriptor;
/*  5873:      */       }
/*  5874:      */       
/*  5875:      */       public MRProtos.TaskReportProto getDefaultInstanceForType()
/*  5876:      */       {
/*  5877: 6086 */         return MRProtos.TaskReportProto.getDefaultInstance();
/*  5878:      */       }
/*  5879:      */       
/*  5880:      */       public MRProtos.TaskReportProto build()
/*  5881:      */       {
/*  5882: 6090 */         MRProtos.TaskReportProto result = buildPartial();
/*  5883: 6091 */         if (!result.isInitialized()) {
/*  5884: 6092 */           throw newUninitializedMessageException(result);
/*  5885:      */         }
/*  5886: 6094 */         return result;
/*  5887:      */       }
/*  5888:      */       
/*  5889:      */       public MRProtos.TaskReportProto buildPartial()
/*  5890:      */       {
/*  5891: 6098 */         MRProtos.TaskReportProto result = new MRProtos.TaskReportProto(this, null);
/*  5892: 6099 */         int from_bitField0_ = this.bitField0_;
/*  5893: 6100 */         int to_bitField0_ = 0;
/*  5894: 6101 */         if ((from_bitField0_ & 0x1) == 1) {
/*  5895: 6102 */           to_bitField0_ |= 0x1;
/*  5896:      */         }
/*  5897: 6104 */         if (this.taskIdBuilder_ == null) {
/*  5898: 6105 */           result.taskId_ = this.taskId_;
/*  5899:      */         } else {
/*  5900: 6107 */           result.taskId_ = ((MRProtos.TaskIdProto)this.taskIdBuilder_.build());
/*  5901:      */         }
/*  5902: 6109 */         if ((from_bitField0_ & 0x2) == 2) {
/*  5903: 6110 */           to_bitField0_ |= 0x2;
/*  5904:      */         }
/*  5905: 6112 */         result.taskState_ = this.taskState_;
/*  5906: 6113 */         if ((from_bitField0_ & 0x4) == 4) {
/*  5907: 6114 */           to_bitField0_ |= 0x4;
/*  5908:      */         }
/*  5909: 6116 */         result.progress_ = this.progress_;
/*  5910: 6117 */         if ((from_bitField0_ & 0x8) == 8) {
/*  5911: 6118 */           to_bitField0_ |= 0x8;
/*  5912:      */         }
/*  5913: 6120 */         result.startTime_ = this.startTime_;
/*  5914: 6121 */         if ((from_bitField0_ & 0x10) == 16) {
/*  5915: 6122 */           to_bitField0_ |= 0x10;
/*  5916:      */         }
/*  5917: 6124 */         result.finishTime_ = this.finishTime_;
/*  5918: 6125 */         if ((from_bitField0_ & 0x20) == 32) {
/*  5919: 6126 */           to_bitField0_ |= 0x20;
/*  5920:      */         }
/*  5921: 6128 */         if (this.countersBuilder_ == null) {
/*  5922: 6129 */           result.counters_ = this.counters_;
/*  5923:      */         } else {
/*  5924: 6131 */           result.counters_ = ((MRProtos.CountersProto)this.countersBuilder_.build());
/*  5925:      */         }
/*  5926: 6133 */         if (this.runningAttemptsBuilder_ == null)
/*  5927:      */         {
/*  5928: 6134 */           if ((this.bitField0_ & 0x40) == 64)
/*  5929:      */           {
/*  5930: 6135 */             this.runningAttempts_ = Collections.unmodifiableList(this.runningAttempts_);
/*  5931: 6136 */             this.bitField0_ &= 0xFFFFFFBF;
/*  5932:      */           }
/*  5933: 6138 */           result.runningAttempts_ = this.runningAttempts_;
/*  5934:      */         }
/*  5935:      */         else
/*  5936:      */         {
/*  5937: 6140 */           result.runningAttempts_ = this.runningAttemptsBuilder_.build();
/*  5938:      */         }
/*  5939: 6142 */         if ((from_bitField0_ & 0x80) == 128) {
/*  5940: 6143 */           to_bitField0_ |= 0x40;
/*  5941:      */         }
/*  5942: 6145 */         if (this.successfulAttemptBuilder_ == null) {
/*  5943: 6146 */           result.successfulAttempt_ = this.successfulAttempt_;
/*  5944:      */         } else {
/*  5945: 6148 */           result.successfulAttempt_ = ((MRProtos.TaskAttemptIdProto)this.successfulAttemptBuilder_.build());
/*  5946:      */         }
/*  5947: 6150 */         if ((this.bitField0_ & 0x100) == 256)
/*  5948:      */         {
/*  5949: 6151 */           this.diagnostics_ = new UnmodifiableLazyStringList(this.diagnostics_);
/*  5950:      */           
/*  5951: 6153 */           this.bitField0_ &= 0xFFFFFEFF;
/*  5952:      */         }
/*  5953: 6155 */         result.diagnostics_ = this.diagnostics_;
/*  5954: 6156 */         result.bitField0_ = to_bitField0_;
/*  5955: 6157 */         onBuilt();
/*  5956: 6158 */         return result;
/*  5957:      */       }
/*  5958:      */       
/*  5959:      */       public Builder mergeFrom(Message other)
/*  5960:      */       {
/*  5961: 6162 */         if ((other instanceof MRProtos.TaskReportProto)) {
/*  5962: 6163 */           return mergeFrom((MRProtos.TaskReportProto)other);
/*  5963:      */         }
/*  5964: 6165 */         super.mergeFrom(other);
/*  5965: 6166 */         return this;
/*  5966:      */       }
/*  5967:      */       
/*  5968:      */       public Builder mergeFrom(MRProtos.TaskReportProto other)
/*  5969:      */       {
/*  5970: 6171 */         if (other == MRProtos.TaskReportProto.getDefaultInstance()) {
/*  5971: 6171 */           return this;
/*  5972:      */         }
/*  5973: 6172 */         if (other.hasTaskId()) {
/*  5974: 6173 */           mergeTaskId(other.getTaskId());
/*  5975:      */         }
/*  5976: 6175 */         if (other.hasTaskState()) {
/*  5977: 6176 */           setTaskState(other.getTaskState());
/*  5978:      */         }
/*  5979: 6178 */         if (other.hasProgress()) {
/*  5980: 6179 */           setProgress(other.getProgress());
/*  5981:      */         }
/*  5982: 6181 */         if (other.hasStartTime()) {
/*  5983: 6182 */           setStartTime(other.getStartTime());
/*  5984:      */         }
/*  5985: 6184 */         if (other.hasFinishTime()) {
/*  5986: 6185 */           setFinishTime(other.getFinishTime());
/*  5987:      */         }
/*  5988: 6187 */         if (other.hasCounters()) {
/*  5989: 6188 */           mergeCounters(other.getCounters());
/*  5990:      */         }
/*  5991: 6190 */         if (this.runningAttemptsBuilder_ == null)
/*  5992:      */         {
/*  5993: 6191 */           if (!other.runningAttempts_.isEmpty())
/*  5994:      */           {
/*  5995: 6192 */             if (this.runningAttempts_.isEmpty())
/*  5996:      */             {
/*  5997: 6193 */               this.runningAttempts_ = other.runningAttempts_;
/*  5998: 6194 */               this.bitField0_ &= 0xFFFFFFBF;
/*  5999:      */             }
/*  6000:      */             else
/*  6001:      */             {
/*  6002: 6196 */               ensureRunningAttemptsIsMutable();
/*  6003: 6197 */               this.runningAttempts_.addAll(other.runningAttempts_);
/*  6004:      */             }
/*  6005: 6199 */             onChanged();
/*  6006:      */           }
/*  6007:      */         }
/*  6008: 6202 */         else if (!other.runningAttempts_.isEmpty()) {
/*  6009: 6203 */           if (this.runningAttemptsBuilder_.isEmpty())
/*  6010:      */           {
/*  6011: 6204 */             this.runningAttemptsBuilder_.dispose();
/*  6012: 6205 */             this.runningAttemptsBuilder_ = null;
/*  6013: 6206 */             this.runningAttempts_ = other.runningAttempts_;
/*  6014: 6207 */             this.bitField0_ &= 0xFFFFFFBF;
/*  6015: 6208 */             this.runningAttemptsBuilder_ = (MRProtos.TaskReportProto.alwaysUseFieldBuilders ? getRunningAttemptsFieldBuilder() : null);
/*  6016:      */           }
/*  6017:      */           else
/*  6018:      */           {
/*  6019: 6212 */             this.runningAttemptsBuilder_.addAllMessages(other.runningAttempts_);
/*  6020:      */           }
/*  6021:      */         }
/*  6022: 6216 */         if (other.hasSuccessfulAttempt()) {
/*  6023: 6217 */           mergeSuccessfulAttempt(other.getSuccessfulAttempt());
/*  6024:      */         }
/*  6025: 6219 */         if (!other.diagnostics_.isEmpty())
/*  6026:      */         {
/*  6027: 6220 */           if (this.diagnostics_.isEmpty())
/*  6028:      */           {
/*  6029: 6221 */             this.diagnostics_ = other.diagnostics_;
/*  6030: 6222 */             this.bitField0_ &= 0xFFFFFEFF;
/*  6031:      */           }
/*  6032:      */           else
/*  6033:      */           {
/*  6034: 6224 */             ensureDiagnosticsIsMutable();
/*  6035: 6225 */             this.diagnostics_.addAll(other.diagnostics_);
/*  6036:      */           }
/*  6037: 6227 */           onChanged();
/*  6038:      */         }
/*  6039: 6229 */         mergeUnknownFields(other.getUnknownFields());
/*  6040: 6230 */         return this;
/*  6041:      */       }
/*  6042:      */       
/*  6043:      */       public final boolean isInitialized()
/*  6044:      */       {
/*  6045: 6234 */         return true;
/*  6046:      */       }
/*  6047:      */       
/*  6048:      */       public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry)
/*  6049:      */         throws IOException
/*  6050:      */       {
/*  6051: 6241 */         MRProtos.TaskReportProto parsedMessage = null;
/*  6052:      */         try
/*  6053:      */         {
/*  6054: 6243 */           parsedMessage = (MRProtos.TaskReportProto)MRProtos.TaskReportProto.PARSER.parsePartialFrom(input, extensionRegistry);
/*  6055:      */         }
/*  6056:      */         catch (InvalidProtocolBufferException e)
/*  6057:      */         {
/*  6058: 6245 */           parsedMessage = (MRProtos.TaskReportProto)e.getUnfinishedMessage();
/*  6059: 6246 */           throw e;
/*  6060:      */         }
/*  6061:      */         finally
/*  6062:      */         {
/*  6063: 6248 */           if (parsedMessage != null) {
/*  6064: 6249 */             mergeFrom(parsedMessage);
/*  6065:      */           }
/*  6066:      */         }
/*  6067: 6252 */         return this;
/*  6068:      */       }
/*  6069:      */       
/*  6070: 6257 */       private MRProtos.TaskIdProto taskId_ = MRProtos.TaskIdProto.getDefaultInstance();
/*  6071:      */       private SingleFieldBuilder<MRProtos.TaskIdProto, MRProtos.TaskIdProto.Builder, MRProtos.TaskIdProtoOrBuilder> taskIdBuilder_;
/*  6072:      */       
/*  6073:      */       public boolean hasTaskId()
/*  6074:      */       {
/*  6075: 6264 */         return (this.bitField0_ & 0x1) == 1;
/*  6076:      */       }
/*  6077:      */       
/*  6078:      */       public MRProtos.TaskIdProto getTaskId()
/*  6079:      */       {
/*  6080: 6270 */         if (this.taskIdBuilder_ == null) {
/*  6081: 6271 */           return this.taskId_;
/*  6082:      */         }
/*  6083: 6273 */         return (MRProtos.TaskIdProto)this.taskIdBuilder_.getMessage();
/*  6084:      */       }
/*  6085:      */       
/*  6086:      */       public Builder setTaskId(MRProtos.TaskIdProto value)
/*  6087:      */       {
/*  6088: 6280 */         if (this.taskIdBuilder_ == null)
/*  6089:      */         {
/*  6090: 6281 */           if (value == null) {
/*  6091: 6282 */             throw new NullPointerException();
/*  6092:      */           }
/*  6093: 6284 */           this.taskId_ = value;
/*  6094: 6285 */           onChanged();
/*  6095:      */         }
/*  6096:      */         else
/*  6097:      */         {
/*  6098: 6287 */           this.taskIdBuilder_.setMessage(value);
/*  6099:      */         }
/*  6100: 6289 */         this.bitField0_ |= 0x1;
/*  6101: 6290 */         return this;
/*  6102:      */       }
/*  6103:      */       
/*  6104:      */       public Builder setTaskId(MRProtos.TaskIdProto.Builder builderForValue)
/*  6105:      */       {
/*  6106: 6297 */         if (this.taskIdBuilder_ == null)
/*  6107:      */         {
/*  6108: 6298 */           this.taskId_ = builderForValue.build();
/*  6109: 6299 */           onChanged();
/*  6110:      */         }
/*  6111:      */         else
/*  6112:      */         {
/*  6113: 6301 */           this.taskIdBuilder_.setMessage(builderForValue.build());
/*  6114:      */         }
/*  6115: 6303 */         this.bitField0_ |= 0x1;
/*  6116: 6304 */         return this;
/*  6117:      */       }
/*  6118:      */       
/*  6119:      */       public Builder mergeTaskId(MRProtos.TaskIdProto value)
/*  6120:      */       {
/*  6121: 6310 */         if (this.taskIdBuilder_ == null)
/*  6122:      */         {
/*  6123: 6311 */           if (((this.bitField0_ & 0x1) == 1) && (this.taskId_ != MRProtos.TaskIdProto.getDefaultInstance())) {
/*  6124: 6313 */             this.taskId_ = MRProtos.TaskIdProto.newBuilder(this.taskId_).mergeFrom(value).buildPartial();
/*  6125:      */           } else {
/*  6126: 6316 */             this.taskId_ = value;
/*  6127:      */           }
/*  6128: 6318 */           onChanged();
/*  6129:      */         }
/*  6130:      */         else
/*  6131:      */         {
/*  6132: 6320 */           this.taskIdBuilder_.mergeFrom(value);
/*  6133:      */         }
/*  6134: 6322 */         this.bitField0_ |= 0x1;
/*  6135: 6323 */         return this;
/*  6136:      */       }
/*  6137:      */       
/*  6138:      */       public Builder clearTaskId()
/*  6139:      */       {
/*  6140: 6329 */         if (this.taskIdBuilder_ == null)
/*  6141:      */         {
/*  6142: 6330 */           this.taskId_ = MRProtos.TaskIdProto.getDefaultInstance();
/*  6143: 6331 */           onChanged();
/*  6144:      */         }
/*  6145:      */         else
/*  6146:      */         {
/*  6147: 6333 */           this.taskIdBuilder_.clear();
/*  6148:      */         }
/*  6149: 6335 */         this.bitField0_ &= 0xFFFFFFFE;
/*  6150: 6336 */         return this;
/*  6151:      */       }
/*  6152:      */       
/*  6153:      */       public MRProtos.TaskIdProto.Builder getTaskIdBuilder()
/*  6154:      */       {
/*  6155: 6342 */         this.bitField0_ |= 0x1;
/*  6156: 6343 */         onChanged();
/*  6157: 6344 */         return (MRProtos.TaskIdProto.Builder)getTaskIdFieldBuilder().getBuilder();
/*  6158:      */       }
/*  6159:      */       
/*  6160:      */       public MRProtos.TaskIdProtoOrBuilder getTaskIdOrBuilder()
/*  6161:      */       {
/*  6162: 6350 */         if (this.taskIdBuilder_ != null) {
/*  6163: 6351 */           return (MRProtos.TaskIdProtoOrBuilder)this.taskIdBuilder_.getMessageOrBuilder();
/*  6164:      */         }
/*  6165: 6353 */         return this.taskId_;
/*  6166:      */       }
/*  6167:      */       
/*  6168:      */       private SingleFieldBuilder<MRProtos.TaskIdProto, MRProtos.TaskIdProto.Builder, MRProtos.TaskIdProtoOrBuilder> getTaskIdFieldBuilder()
/*  6169:      */       {
/*  6170: 6362 */         if (this.taskIdBuilder_ == null)
/*  6171:      */         {
/*  6172: 6363 */           this.taskIdBuilder_ = new SingleFieldBuilder(this.taskId_, getParentForChildren(), isClean());
/*  6173:      */           
/*  6174:      */ 
/*  6175:      */ 
/*  6176:      */ 
/*  6177: 6368 */           this.taskId_ = null;
/*  6178:      */         }
/*  6179: 6370 */         return this.taskIdBuilder_;
/*  6180:      */       }
/*  6181:      */       
/*  6182: 6374 */       private MRProtos.TaskStateProto taskState_ = MRProtos.TaskStateProto.TS_NEW;
/*  6183:      */       private float progress_;
/*  6184:      */       private long startTime_;
/*  6185:      */       private long finishTime_;
/*  6186:      */       
/*  6187:      */       public boolean hasTaskState()
/*  6188:      */       {
/*  6189: 6379 */         return (this.bitField0_ & 0x2) == 2;
/*  6190:      */       }
/*  6191:      */       
/*  6192:      */       public MRProtos.TaskStateProto getTaskState()
/*  6193:      */       {
/*  6194: 6385 */         return this.taskState_;
/*  6195:      */       }
/*  6196:      */       
/*  6197:      */       public Builder setTaskState(MRProtos.TaskStateProto value)
/*  6198:      */       {
/*  6199: 6391 */         if (value == null) {
/*  6200: 6392 */           throw new NullPointerException();
/*  6201:      */         }
/*  6202: 6394 */         this.bitField0_ |= 0x2;
/*  6203: 6395 */         this.taskState_ = value;
/*  6204: 6396 */         onChanged();
/*  6205: 6397 */         return this;
/*  6206:      */       }
/*  6207:      */       
/*  6208:      */       public Builder clearTaskState()
/*  6209:      */       {
/*  6210: 6403 */         this.bitField0_ &= 0xFFFFFFFD;
/*  6211: 6404 */         this.taskState_ = MRProtos.TaskStateProto.TS_NEW;
/*  6212: 6405 */         onChanged();
/*  6213: 6406 */         return this;
/*  6214:      */       }
/*  6215:      */       
/*  6216:      */       public boolean hasProgress()
/*  6217:      */       {
/*  6218: 6415 */         return (this.bitField0_ & 0x4) == 4;
/*  6219:      */       }
/*  6220:      */       
/*  6221:      */       public float getProgress()
/*  6222:      */       {
/*  6223: 6421 */         return this.progress_;
/*  6224:      */       }
/*  6225:      */       
/*  6226:      */       public Builder setProgress(float value)
/*  6227:      */       {
/*  6228: 6427 */         this.bitField0_ |= 0x4;
/*  6229: 6428 */         this.progress_ = value;
/*  6230: 6429 */         onChanged();
/*  6231: 6430 */         return this;
/*  6232:      */       }
/*  6233:      */       
/*  6234:      */       public Builder clearProgress()
/*  6235:      */       {
/*  6236: 6436 */         this.bitField0_ &= 0xFFFFFFFB;
/*  6237: 6437 */         this.progress_ = 0.0F;
/*  6238: 6438 */         onChanged();
/*  6239: 6439 */         return this;
/*  6240:      */       }
/*  6241:      */       
/*  6242:      */       public boolean hasStartTime()
/*  6243:      */       {
/*  6244: 6448 */         return (this.bitField0_ & 0x8) == 8;
/*  6245:      */       }
/*  6246:      */       
/*  6247:      */       public long getStartTime()
/*  6248:      */       {
/*  6249: 6454 */         return this.startTime_;
/*  6250:      */       }
/*  6251:      */       
/*  6252:      */       public Builder setStartTime(long value)
/*  6253:      */       {
/*  6254: 6460 */         this.bitField0_ |= 0x8;
/*  6255: 6461 */         this.startTime_ = value;
/*  6256: 6462 */         onChanged();
/*  6257: 6463 */         return this;
/*  6258:      */       }
/*  6259:      */       
/*  6260:      */       public Builder clearStartTime()
/*  6261:      */       {
/*  6262: 6469 */         this.bitField0_ &= 0xFFFFFFF7;
/*  6263: 6470 */         this.startTime_ = 0L;
/*  6264: 6471 */         onChanged();
/*  6265: 6472 */         return this;
/*  6266:      */       }
/*  6267:      */       
/*  6268:      */       public boolean hasFinishTime()
/*  6269:      */       {
/*  6270: 6481 */         return (this.bitField0_ & 0x10) == 16;
/*  6271:      */       }
/*  6272:      */       
/*  6273:      */       public long getFinishTime()
/*  6274:      */       {
/*  6275: 6487 */         return this.finishTime_;
/*  6276:      */       }
/*  6277:      */       
/*  6278:      */       public Builder setFinishTime(long value)
/*  6279:      */       {
/*  6280: 6493 */         this.bitField0_ |= 0x10;
/*  6281: 6494 */         this.finishTime_ = value;
/*  6282: 6495 */         onChanged();
/*  6283: 6496 */         return this;
/*  6284:      */       }
/*  6285:      */       
/*  6286:      */       public Builder clearFinishTime()
/*  6287:      */       {
/*  6288: 6502 */         this.bitField0_ &= 0xFFFFFFEF;
/*  6289: 6503 */         this.finishTime_ = 0L;
/*  6290: 6504 */         onChanged();
/*  6291: 6505 */         return this;
/*  6292:      */       }
/*  6293:      */       
/*  6294: 6509 */       private MRProtos.CountersProto counters_ = MRProtos.CountersProto.getDefaultInstance();
/*  6295:      */       private SingleFieldBuilder<MRProtos.CountersProto, MRProtos.CountersProto.Builder, MRProtos.CountersProtoOrBuilder> countersBuilder_;
/*  6296:      */       
/*  6297:      */       public boolean hasCounters()
/*  6298:      */       {
/*  6299: 6516 */         return (this.bitField0_ & 0x20) == 32;
/*  6300:      */       }
/*  6301:      */       
/*  6302:      */       public MRProtos.CountersProto getCounters()
/*  6303:      */       {
/*  6304: 6522 */         if (this.countersBuilder_ == null) {
/*  6305: 6523 */           return this.counters_;
/*  6306:      */         }
/*  6307: 6525 */         return (MRProtos.CountersProto)this.countersBuilder_.getMessage();
/*  6308:      */       }
/*  6309:      */       
/*  6310:      */       public Builder setCounters(MRProtos.CountersProto value)
/*  6311:      */       {
/*  6312: 6532 */         if (this.countersBuilder_ == null)
/*  6313:      */         {
/*  6314: 6533 */           if (value == null) {
/*  6315: 6534 */             throw new NullPointerException();
/*  6316:      */           }
/*  6317: 6536 */           this.counters_ = value;
/*  6318: 6537 */           onChanged();
/*  6319:      */         }
/*  6320:      */         else
/*  6321:      */         {
/*  6322: 6539 */           this.countersBuilder_.setMessage(value);
/*  6323:      */         }
/*  6324: 6541 */         this.bitField0_ |= 0x20;
/*  6325: 6542 */         return this;
/*  6326:      */       }
/*  6327:      */       
/*  6328:      */       public Builder setCounters(MRProtos.CountersProto.Builder builderForValue)
/*  6329:      */       {
/*  6330: 6549 */         if (this.countersBuilder_ == null)
/*  6331:      */         {
/*  6332: 6550 */           this.counters_ = builderForValue.build();
/*  6333: 6551 */           onChanged();
/*  6334:      */         }
/*  6335:      */         else
/*  6336:      */         {
/*  6337: 6553 */           this.countersBuilder_.setMessage(builderForValue.build());
/*  6338:      */         }
/*  6339: 6555 */         this.bitField0_ |= 0x20;
/*  6340: 6556 */         return this;
/*  6341:      */       }
/*  6342:      */       
/*  6343:      */       public Builder mergeCounters(MRProtos.CountersProto value)
/*  6344:      */       {
/*  6345: 6562 */         if (this.countersBuilder_ == null)
/*  6346:      */         {
/*  6347: 6563 */           if (((this.bitField0_ & 0x20) == 32) && (this.counters_ != MRProtos.CountersProto.getDefaultInstance())) {
/*  6348: 6565 */             this.counters_ = MRProtos.CountersProto.newBuilder(this.counters_).mergeFrom(value).buildPartial();
/*  6349:      */           } else {
/*  6350: 6568 */             this.counters_ = value;
/*  6351:      */           }
/*  6352: 6570 */           onChanged();
/*  6353:      */         }
/*  6354:      */         else
/*  6355:      */         {
/*  6356: 6572 */           this.countersBuilder_.mergeFrom(value);
/*  6357:      */         }
/*  6358: 6574 */         this.bitField0_ |= 0x20;
/*  6359: 6575 */         return this;
/*  6360:      */       }
/*  6361:      */       
/*  6362:      */       public Builder clearCounters()
/*  6363:      */       {
/*  6364: 6581 */         if (this.countersBuilder_ == null)
/*  6365:      */         {
/*  6366: 6582 */           this.counters_ = MRProtos.CountersProto.getDefaultInstance();
/*  6367: 6583 */           onChanged();
/*  6368:      */         }
/*  6369:      */         else
/*  6370:      */         {
/*  6371: 6585 */           this.countersBuilder_.clear();
/*  6372:      */         }
/*  6373: 6587 */         this.bitField0_ &= 0xFFFFFFDF;
/*  6374: 6588 */         return this;
/*  6375:      */       }
/*  6376:      */       
/*  6377:      */       public MRProtos.CountersProto.Builder getCountersBuilder()
/*  6378:      */       {
/*  6379: 6594 */         this.bitField0_ |= 0x20;
/*  6380: 6595 */         onChanged();
/*  6381: 6596 */         return (MRProtos.CountersProto.Builder)getCountersFieldBuilder().getBuilder();
/*  6382:      */       }
/*  6383:      */       
/*  6384:      */       public MRProtos.CountersProtoOrBuilder getCountersOrBuilder()
/*  6385:      */       {
/*  6386: 6602 */         if (this.countersBuilder_ != null) {
/*  6387: 6603 */           return (MRProtos.CountersProtoOrBuilder)this.countersBuilder_.getMessageOrBuilder();
/*  6388:      */         }
/*  6389: 6605 */         return this.counters_;
/*  6390:      */       }
/*  6391:      */       
/*  6392:      */       private SingleFieldBuilder<MRProtos.CountersProto, MRProtos.CountersProto.Builder, MRProtos.CountersProtoOrBuilder> getCountersFieldBuilder()
/*  6393:      */       {
/*  6394: 6614 */         if (this.countersBuilder_ == null)
/*  6395:      */         {
/*  6396: 6615 */           this.countersBuilder_ = new SingleFieldBuilder(this.counters_, getParentForChildren(), isClean());
/*  6397:      */           
/*  6398:      */ 
/*  6399:      */ 
/*  6400:      */ 
/*  6401: 6620 */           this.counters_ = null;
/*  6402:      */         }
/*  6403: 6622 */         return this.countersBuilder_;
/*  6404:      */       }
/*  6405:      */       
/*  6406: 6626 */       private List<MRProtos.TaskAttemptIdProto> runningAttempts_ = Collections.emptyList();
/*  6407:      */       private RepeatedFieldBuilder<MRProtos.TaskAttemptIdProto, MRProtos.TaskAttemptIdProto.Builder, MRProtos.TaskAttemptIdProtoOrBuilder> runningAttemptsBuilder_;
/*  6408:      */       
/*  6409:      */       private void ensureRunningAttemptsIsMutable()
/*  6410:      */       {
/*  6411: 6629 */         if ((this.bitField0_ & 0x40) != 64)
/*  6412:      */         {
/*  6413: 6630 */           this.runningAttempts_ = new ArrayList(this.runningAttempts_);
/*  6414: 6631 */           this.bitField0_ |= 0x40;
/*  6415:      */         }
/*  6416:      */       }
/*  6417:      */       
/*  6418:      */       public List<MRProtos.TaskAttemptIdProto> getRunningAttemptsList()
/*  6419:      */       {
/*  6420: 6642 */         if (this.runningAttemptsBuilder_ == null) {
/*  6421: 6643 */           return Collections.unmodifiableList(this.runningAttempts_);
/*  6422:      */         }
/*  6423: 6645 */         return this.runningAttemptsBuilder_.getMessageList();
/*  6424:      */       }
/*  6425:      */       
/*  6426:      */       public int getRunningAttemptsCount()
/*  6427:      */       {
/*  6428: 6652 */         if (this.runningAttemptsBuilder_ == null) {
/*  6429: 6653 */           return this.runningAttempts_.size();
/*  6430:      */         }
/*  6431: 6655 */         return this.runningAttemptsBuilder_.getCount();
/*  6432:      */       }
/*  6433:      */       
/*  6434:      */       public MRProtos.TaskAttemptIdProto getRunningAttempts(int index)
/*  6435:      */       {
/*  6436: 6662 */         if (this.runningAttemptsBuilder_ == null) {
/*  6437: 6663 */           return (MRProtos.TaskAttemptIdProto)this.runningAttempts_.get(index);
/*  6438:      */         }
/*  6439: 6665 */         return (MRProtos.TaskAttemptIdProto)this.runningAttemptsBuilder_.getMessage(index);
/*  6440:      */       }
/*  6441:      */       
/*  6442:      */       public Builder setRunningAttempts(int index, MRProtos.TaskAttemptIdProto value)
/*  6443:      */       {
/*  6444: 6673 */         if (this.runningAttemptsBuilder_ == null)
/*  6445:      */         {
/*  6446: 6674 */           if (value == null) {
/*  6447: 6675 */             throw new NullPointerException();
/*  6448:      */           }
/*  6449: 6677 */           ensureRunningAttemptsIsMutable();
/*  6450: 6678 */           this.runningAttempts_.set(index, value);
/*  6451: 6679 */           onChanged();
/*  6452:      */         }
/*  6453:      */         else
/*  6454:      */         {
/*  6455: 6681 */           this.runningAttemptsBuilder_.setMessage(index, value);
/*  6456:      */         }
/*  6457: 6683 */         return this;
/*  6458:      */       }
/*  6459:      */       
/*  6460:      */       public Builder setRunningAttempts(int index, MRProtos.TaskAttemptIdProto.Builder builderForValue)
/*  6461:      */       {
/*  6462: 6690 */         if (this.runningAttemptsBuilder_ == null)
/*  6463:      */         {
/*  6464: 6691 */           ensureRunningAttemptsIsMutable();
/*  6465: 6692 */           this.runningAttempts_.set(index, builderForValue.build());
/*  6466: 6693 */           onChanged();
/*  6467:      */         }
/*  6468:      */         else
/*  6469:      */         {
/*  6470: 6695 */           this.runningAttemptsBuilder_.setMessage(index, builderForValue.build());
/*  6471:      */         }
/*  6472: 6697 */         return this;
/*  6473:      */       }
/*  6474:      */       
/*  6475:      */       public Builder addRunningAttempts(MRProtos.TaskAttemptIdProto value)
/*  6476:      */       {
/*  6477: 6703 */         if (this.runningAttemptsBuilder_ == null)
/*  6478:      */         {
/*  6479: 6704 */           if (value == null) {
/*  6480: 6705 */             throw new NullPointerException();
/*  6481:      */           }
/*  6482: 6707 */           ensureRunningAttemptsIsMutable();
/*  6483: 6708 */           this.runningAttempts_.add(value);
/*  6484: 6709 */           onChanged();
/*  6485:      */         }
/*  6486:      */         else
/*  6487:      */         {
/*  6488: 6711 */           this.runningAttemptsBuilder_.addMessage(value);
/*  6489:      */         }
/*  6490: 6713 */         return this;
/*  6491:      */       }
/*  6492:      */       
/*  6493:      */       public Builder addRunningAttempts(int index, MRProtos.TaskAttemptIdProto value)
/*  6494:      */       {
/*  6495: 6720 */         if (this.runningAttemptsBuilder_ == null)
/*  6496:      */         {
/*  6497: 6721 */           if (value == null) {
/*  6498: 6722 */             throw new NullPointerException();
/*  6499:      */           }
/*  6500: 6724 */           ensureRunningAttemptsIsMutable();
/*  6501: 6725 */           this.runningAttempts_.add(index, value);
/*  6502: 6726 */           onChanged();
/*  6503:      */         }
/*  6504:      */         else
/*  6505:      */         {
/*  6506: 6728 */           this.runningAttemptsBuilder_.addMessage(index, value);
/*  6507:      */         }
/*  6508: 6730 */         return this;
/*  6509:      */       }
/*  6510:      */       
/*  6511:      */       public Builder addRunningAttempts(MRProtos.TaskAttemptIdProto.Builder builderForValue)
/*  6512:      */       {
/*  6513: 6737 */         if (this.runningAttemptsBuilder_ == null)
/*  6514:      */         {
/*  6515: 6738 */           ensureRunningAttemptsIsMutable();
/*  6516: 6739 */           this.runningAttempts_.add(builderForValue.build());
/*  6517: 6740 */           onChanged();
/*  6518:      */         }
/*  6519:      */         else
/*  6520:      */         {
/*  6521: 6742 */           this.runningAttemptsBuilder_.addMessage(builderForValue.build());
/*  6522:      */         }
/*  6523: 6744 */         return this;
/*  6524:      */       }
/*  6525:      */       
/*  6526:      */       public Builder addRunningAttempts(int index, MRProtos.TaskAttemptIdProto.Builder builderForValue)
/*  6527:      */       {
/*  6528: 6751 */         if (this.runningAttemptsBuilder_ == null)
/*  6529:      */         {
/*  6530: 6752 */           ensureRunningAttemptsIsMutable();
/*  6531: 6753 */           this.runningAttempts_.add(index, builderForValue.build());
/*  6532: 6754 */           onChanged();
/*  6533:      */         }
/*  6534:      */         else
/*  6535:      */         {
/*  6536: 6756 */           this.runningAttemptsBuilder_.addMessage(index, builderForValue.build());
/*  6537:      */         }
/*  6538: 6758 */         return this;
/*  6539:      */       }
/*  6540:      */       
/*  6541:      */       public Builder addAllRunningAttempts(Iterable<? extends MRProtos.TaskAttemptIdProto> values)
/*  6542:      */       {
/*  6543: 6765 */         if (this.runningAttemptsBuilder_ == null)
/*  6544:      */         {
/*  6545: 6766 */           ensureRunningAttemptsIsMutable();
/*  6546: 6767 */           GeneratedMessage.Builder.addAll(values, this.runningAttempts_);
/*  6547: 6768 */           onChanged();
/*  6548:      */         }
/*  6549:      */         else
/*  6550:      */         {
/*  6551: 6770 */           this.runningAttemptsBuilder_.addAllMessages(values);
/*  6552:      */         }
/*  6553: 6772 */         return this;
/*  6554:      */       }
/*  6555:      */       
/*  6556:      */       public Builder clearRunningAttempts()
/*  6557:      */       {
/*  6558: 6778 */         if (this.runningAttemptsBuilder_ == null)
/*  6559:      */         {
/*  6560: 6779 */           this.runningAttempts_ = Collections.emptyList();
/*  6561: 6780 */           this.bitField0_ &= 0xFFFFFFBF;
/*  6562: 6781 */           onChanged();
/*  6563:      */         }
/*  6564:      */         else
/*  6565:      */         {
/*  6566: 6783 */           this.runningAttemptsBuilder_.clear();
/*  6567:      */         }
/*  6568: 6785 */         return this;
/*  6569:      */       }
/*  6570:      */       
/*  6571:      */       public Builder removeRunningAttempts(int index)
/*  6572:      */       {
/*  6573: 6791 */         if (this.runningAttemptsBuilder_ == null)
/*  6574:      */         {
/*  6575: 6792 */           ensureRunningAttemptsIsMutable();
/*  6576: 6793 */           this.runningAttempts_.remove(index);
/*  6577: 6794 */           onChanged();
/*  6578:      */         }
/*  6579:      */         else
/*  6580:      */         {
/*  6581: 6796 */           this.runningAttemptsBuilder_.remove(index);
/*  6582:      */         }
/*  6583: 6798 */         return this;
/*  6584:      */       }
/*  6585:      */       
/*  6586:      */       public MRProtos.TaskAttemptIdProto.Builder getRunningAttemptsBuilder(int index)
/*  6587:      */       {
/*  6588: 6805 */         return (MRProtos.TaskAttemptIdProto.Builder)getRunningAttemptsFieldBuilder().getBuilder(index);
/*  6589:      */       }
/*  6590:      */       
/*  6591:      */       public MRProtos.TaskAttemptIdProtoOrBuilder getRunningAttemptsOrBuilder(int index)
/*  6592:      */       {
/*  6593: 6812 */         if (this.runningAttemptsBuilder_ == null) {
/*  6594: 6813 */           return (MRProtos.TaskAttemptIdProtoOrBuilder)this.runningAttempts_.get(index);
/*  6595:      */         }
/*  6596: 6814 */         return (MRProtos.TaskAttemptIdProtoOrBuilder)this.runningAttemptsBuilder_.getMessageOrBuilder(index);
/*  6597:      */       }
/*  6598:      */       
/*  6599:      */       public List<? extends MRProtos.TaskAttemptIdProtoOrBuilder> getRunningAttemptsOrBuilderList()
/*  6600:      */       {
/*  6601: 6822 */         if (this.runningAttemptsBuilder_ != null) {
/*  6602: 6823 */           return this.runningAttemptsBuilder_.getMessageOrBuilderList();
/*  6603:      */         }
/*  6604: 6825 */         return Collections.unmodifiableList(this.runningAttempts_);
/*  6605:      */       }
/*  6606:      */       
/*  6607:      */       public MRProtos.TaskAttemptIdProto.Builder addRunningAttemptsBuilder()
/*  6608:      */       {
/*  6609: 6832 */         return (MRProtos.TaskAttemptIdProto.Builder)getRunningAttemptsFieldBuilder().addBuilder(MRProtos.TaskAttemptIdProto.getDefaultInstance());
/*  6610:      */       }
/*  6611:      */       
/*  6612:      */       public MRProtos.TaskAttemptIdProto.Builder addRunningAttemptsBuilder(int index)
/*  6613:      */       {
/*  6614: 6840 */         return (MRProtos.TaskAttemptIdProto.Builder)getRunningAttemptsFieldBuilder().addBuilder(index, MRProtos.TaskAttemptIdProto.getDefaultInstance());
/*  6615:      */       }
/*  6616:      */       
/*  6617:      */       public List<MRProtos.TaskAttemptIdProto.Builder> getRunningAttemptsBuilderList()
/*  6618:      */       {
/*  6619: 6848 */         return getRunningAttemptsFieldBuilder().getBuilderList();
/*  6620:      */       }
/*  6621:      */       
/*  6622:      */       private RepeatedFieldBuilder<MRProtos.TaskAttemptIdProto, MRProtos.TaskAttemptIdProto.Builder, MRProtos.TaskAttemptIdProtoOrBuilder> getRunningAttemptsFieldBuilder()
/*  6623:      */       {
/*  6624: 6853 */         if (this.runningAttemptsBuilder_ == null)
/*  6625:      */         {
/*  6626: 6854 */           this.runningAttemptsBuilder_ = new RepeatedFieldBuilder(this.runningAttempts_, (this.bitField0_ & 0x40) == 64, getParentForChildren(), isClean());
/*  6627:      */           
/*  6628:      */ 
/*  6629:      */ 
/*  6630:      */ 
/*  6631:      */ 
/*  6632: 6860 */           this.runningAttempts_ = null;
/*  6633:      */         }
/*  6634: 6862 */         return this.runningAttemptsBuilder_;
/*  6635:      */       }
/*  6636:      */       
/*  6637: 6866 */       private MRProtos.TaskAttemptIdProto successfulAttempt_ = MRProtos.TaskAttemptIdProto.getDefaultInstance();
/*  6638:      */       private SingleFieldBuilder<MRProtos.TaskAttemptIdProto, MRProtos.TaskAttemptIdProto.Builder, MRProtos.TaskAttemptIdProtoOrBuilder> successfulAttemptBuilder_;
/*  6639:      */       
/*  6640:      */       public boolean hasSuccessfulAttempt()
/*  6641:      */       {
/*  6642: 6873 */         return (this.bitField0_ & 0x80) == 128;
/*  6643:      */       }
/*  6644:      */       
/*  6645:      */       public MRProtos.TaskAttemptIdProto getSuccessfulAttempt()
/*  6646:      */       {
/*  6647: 6879 */         if (this.successfulAttemptBuilder_ == null) {
/*  6648: 6880 */           return this.successfulAttempt_;
/*  6649:      */         }
/*  6650: 6882 */         return (MRProtos.TaskAttemptIdProto)this.successfulAttemptBuilder_.getMessage();
/*  6651:      */       }
/*  6652:      */       
/*  6653:      */       public Builder setSuccessfulAttempt(MRProtos.TaskAttemptIdProto value)
/*  6654:      */       {
/*  6655: 6889 */         if (this.successfulAttemptBuilder_ == null)
/*  6656:      */         {
/*  6657: 6890 */           if (value == null) {
/*  6658: 6891 */             throw new NullPointerException();
/*  6659:      */           }
/*  6660: 6893 */           this.successfulAttempt_ = value;
/*  6661: 6894 */           onChanged();
/*  6662:      */         }
/*  6663:      */         else
/*  6664:      */         {
/*  6665: 6896 */           this.successfulAttemptBuilder_.setMessage(value);
/*  6666:      */         }
/*  6667: 6898 */         this.bitField0_ |= 0x80;
/*  6668: 6899 */         return this;
/*  6669:      */       }
/*  6670:      */       
/*  6671:      */       public Builder setSuccessfulAttempt(MRProtos.TaskAttemptIdProto.Builder builderForValue)
/*  6672:      */       {
/*  6673: 6906 */         if (this.successfulAttemptBuilder_ == null)
/*  6674:      */         {
/*  6675: 6907 */           this.successfulAttempt_ = builderForValue.build();
/*  6676: 6908 */           onChanged();
/*  6677:      */         }
/*  6678:      */         else
/*  6679:      */         {
/*  6680: 6910 */           this.successfulAttemptBuilder_.setMessage(builderForValue.build());
/*  6681:      */         }
/*  6682: 6912 */         this.bitField0_ |= 0x80;
/*  6683: 6913 */         return this;
/*  6684:      */       }
/*  6685:      */       
/*  6686:      */       public Builder mergeSuccessfulAttempt(MRProtos.TaskAttemptIdProto value)
/*  6687:      */       {
/*  6688: 6919 */         if (this.successfulAttemptBuilder_ == null)
/*  6689:      */         {
/*  6690: 6920 */           if (((this.bitField0_ & 0x80) == 128) && (this.successfulAttempt_ != MRProtos.TaskAttemptIdProto.getDefaultInstance())) {
/*  6691: 6922 */             this.successfulAttempt_ = MRProtos.TaskAttemptIdProto.newBuilder(this.successfulAttempt_).mergeFrom(value).buildPartial();
/*  6692:      */           } else {
/*  6693: 6925 */             this.successfulAttempt_ = value;
/*  6694:      */           }
/*  6695: 6927 */           onChanged();
/*  6696:      */         }
/*  6697:      */         else
/*  6698:      */         {
/*  6699: 6929 */           this.successfulAttemptBuilder_.mergeFrom(value);
/*  6700:      */         }
/*  6701: 6931 */         this.bitField0_ |= 0x80;
/*  6702: 6932 */         return this;
/*  6703:      */       }
/*  6704:      */       
/*  6705:      */       public Builder clearSuccessfulAttempt()
/*  6706:      */       {
/*  6707: 6938 */         if (this.successfulAttemptBuilder_ == null)
/*  6708:      */         {
/*  6709: 6939 */           this.successfulAttempt_ = MRProtos.TaskAttemptIdProto.getDefaultInstance();
/*  6710: 6940 */           onChanged();
/*  6711:      */         }
/*  6712:      */         else
/*  6713:      */         {
/*  6714: 6942 */           this.successfulAttemptBuilder_.clear();
/*  6715:      */         }
/*  6716: 6944 */         this.bitField0_ &= 0xFFFFFF7F;
/*  6717: 6945 */         return this;
/*  6718:      */       }
/*  6719:      */       
/*  6720:      */       public MRProtos.TaskAttemptIdProto.Builder getSuccessfulAttemptBuilder()
/*  6721:      */       {
/*  6722: 6951 */         this.bitField0_ |= 0x80;
/*  6723: 6952 */         onChanged();
/*  6724: 6953 */         return (MRProtos.TaskAttemptIdProto.Builder)getSuccessfulAttemptFieldBuilder().getBuilder();
/*  6725:      */       }
/*  6726:      */       
/*  6727:      */       public MRProtos.TaskAttemptIdProtoOrBuilder getSuccessfulAttemptOrBuilder()
/*  6728:      */       {
/*  6729: 6959 */         if (this.successfulAttemptBuilder_ != null) {
/*  6730: 6960 */           return (MRProtos.TaskAttemptIdProtoOrBuilder)this.successfulAttemptBuilder_.getMessageOrBuilder();
/*  6731:      */         }
/*  6732: 6962 */         return this.successfulAttempt_;
/*  6733:      */       }
/*  6734:      */       
/*  6735:      */       private SingleFieldBuilder<MRProtos.TaskAttemptIdProto, MRProtos.TaskAttemptIdProto.Builder, MRProtos.TaskAttemptIdProtoOrBuilder> getSuccessfulAttemptFieldBuilder()
/*  6736:      */       {
/*  6737: 6971 */         if (this.successfulAttemptBuilder_ == null)
/*  6738:      */         {
/*  6739: 6972 */           this.successfulAttemptBuilder_ = new SingleFieldBuilder(this.successfulAttempt_, getParentForChildren(), isClean());
/*  6740:      */           
/*  6741:      */ 
/*  6742:      */ 
/*  6743:      */ 
/*  6744: 6977 */           this.successfulAttempt_ = null;
/*  6745:      */         }
/*  6746: 6979 */         return this.successfulAttemptBuilder_;
/*  6747:      */       }
/*  6748:      */       
/*  6749: 6983 */       private LazyStringList diagnostics_ = LazyStringArrayList.EMPTY;
/*  6750:      */       
/*  6751:      */       private void ensureDiagnosticsIsMutable()
/*  6752:      */       {
/*  6753: 6985 */         if ((this.bitField0_ & 0x100) != 256)
/*  6754:      */         {
/*  6755: 6986 */           this.diagnostics_ = new LazyStringArrayList(this.diagnostics_);
/*  6756: 6987 */           this.bitField0_ |= 0x100;
/*  6757:      */         }
/*  6758:      */       }
/*  6759:      */       
/*  6760:      */       public List<String> getDiagnosticsList()
/*  6761:      */       {
/*  6762: 6995 */         return Collections.unmodifiableList(this.diagnostics_);
/*  6763:      */       }
/*  6764:      */       
/*  6765:      */       public int getDiagnosticsCount()
/*  6766:      */       {
/*  6767: 7001 */         return this.diagnostics_.size();
/*  6768:      */       }
/*  6769:      */       
/*  6770:      */       public String getDiagnostics(int index)
/*  6771:      */       {
/*  6772: 7007 */         return (String)this.diagnostics_.get(index);
/*  6773:      */       }
/*  6774:      */       
/*  6775:      */       public ByteString getDiagnosticsBytes(int index)
/*  6776:      */       {
/*  6777: 7014 */         return this.diagnostics_.getByteString(index);
/*  6778:      */       }
/*  6779:      */       
/*  6780:      */       public Builder setDiagnostics(int index, String value)
/*  6781:      */       {
/*  6782: 7021 */         if (value == null) {
/*  6783: 7022 */           throw new NullPointerException();
/*  6784:      */         }
/*  6785: 7024 */         ensureDiagnosticsIsMutable();
/*  6786: 7025 */         this.diagnostics_.set(index, value);
/*  6787: 7026 */         onChanged();
/*  6788: 7027 */         return this;
/*  6789:      */       }
/*  6790:      */       
/*  6791:      */       public Builder addDiagnostics(String value)
/*  6792:      */       {
/*  6793: 7034 */         if (value == null) {
/*  6794: 7035 */           throw new NullPointerException();
/*  6795:      */         }
/*  6796: 7037 */         ensureDiagnosticsIsMutable();
/*  6797: 7038 */         this.diagnostics_.add(value);
/*  6798: 7039 */         onChanged();
/*  6799: 7040 */         return this;
/*  6800:      */       }
/*  6801:      */       
/*  6802:      */       public Builder addAllDiagnostics(Iterable<String> values)
/*  6803:      */       {
/*  6804: 7047 */         ensureDiagnosticsIsMutable();
/*  6805: 7048 */         GeneratedMessage.Builder.addAll(values, this.diagnostics_);
/*  6806: 7049 */         onChanged();
/*  6807: 7050 */         return this;
/*  6808:      */       }
/*  6809:      */       
/*  6810:      */       public Builder clearDiagnostics()
/*  6811:      */       {
/*  6812: 7056 */         this.diagnostics_ = LazyStringArrayList.EMPTY;
/*  6813: 7057 */         this.bitField0_ &= 0xFFFFFEFF;
/*  6814: 7058 */         onChanged();
/*  6815: 7059 */         return this;
/*  6816:      */       }
/*  6817:      */       
/*  6818:      */       public Builder addDiagnosticsBytes(ByteString value)
/*  6819:      */       {
/*  6820: 7066 */         if (value == null) {
/*  6821: 7067 */           throw new NullPointerException();
/*  6822:      */         }
/*  6823: 7069 */         ensureDiagnosticsIsMutable();
/*  6824: 7070 */         this.diagnostics_.add(value);
/*  6825: 7071 */         onChanged();
/*  6826: 7072 */         return this;
/*  6827:      */       }
/*  6828:      */     }
/*  6829:      */     
/*  6830:      */     static
/*  6831:      */     {
/*  6832: 7079 */       defaultInstance = new TaskReportProto(true);
/*  6833: 7080 */       defaultInstance.initFields();
/*  6834:      */     }
/*  6835:      */   }
/*  6836:      */   
/*  6837:      */   public static abstract interface TaskAttemptReportProtoOrBuilder
/*  6838:      */     extends MessageOrBuilder
/*  6839:      */   {
/*  6840:      */     public abstract boolean hasTaskAttemptId();
/*  6841:      */     
/*  6842:      */     public abstract MRProtos.TaskAttemptIdProto getTaskAttemptId();
/*  6843:      */     
/*  6844:      */     public abstract MRProtos.TaskAttemptIdProtoOrBuilder getTaskAttemptIdOrBuilder();
/*  6845:      */     
/*  6846:      */     public abstract boolean hasTaskAttemptState();
/*  6847:      */     
/*  6848:      */     public abstract MRProtos.TaskAttemptStateProto getTaskAttemptState();
/*  6849:      */     
/*  6850:      */     public abstract boolean hasProgress();
/*  6851:      */     
/*  6852:      */     public abstract float getProgress();
/*  6853:      */     
/*  6854:      */     public abstract boolean hasStartTime();
/*  6855:      */     
/*  6856:      */     public abstract long getStartTime();
/*  6857:      */     
/*  6858:      */     public abstract boolean hasFinishTime();
/*  6859:      */     
/*  6860:      */     public abstract long getFinishTime();
/*  6861:      */     
/*  6862:      */     public abstract boolean hasCounters();
/*  6863:      */     
/*  6864:      */     public abstract MRProtos.CountersProto getCounters();
/*  6865:      */     
/*  6866:      */     public abstract MRProtos.CountersProtoOrBuilder getCountersOrBuilder();
/*  6867:      */     
/*  6868:      */     public abstract boolean hasDiagnosticInfo();
/*  6869:      */     
/*  6870:      */     public abstract String getDiagnosticInfo();
/*  6871:      */     
/*  6872:      */     public abstract ByteString getDiagnosticInfoBytes();
/*  6873:      */     
/*  6874:      */     public abstract boolean hasStateString();
/*  6875:      */     
/*  6876:      */     public abstract String getStateString();
/*  6877:      */     
/*  6878:      */     public abstract ByteString getStateStringBytes();
/*  6879:      */     
/*  6880:      */     public abstract boolean hasPhase();
/*  6881:      */     
/*  6882:      */     public abstract MRProtos.PhaseProto getPhase();
/*  6883:      */     
/*  6884:      */     public abstract boolean hasShuffleFinishTime();
/*  6885:      */     
/*  6886:      */     public abstract long getShuffleFinishTime();
/*  6887:      */     
/*  6888:      */     public abstract boolean hasSortFinishTime();
/*  6889:      */     
/*  6890:      */     public abstract long getSortFinishTime();
/*  6891:      */     
/*  6892:      */     public abstract boolean hasNodeManagerHost();
/*  6893:      */     
/*  6894:      */     public abstract String getNodeManagerHost();
/*  6895:      */     
/*  6896:      */     public abstract ByteString getNodeManagerHostBytes();
/*  6897:      */     
/*  6898:      */     public abstract boolean hasNodeManagerPort();
/*  6899:      */     
/*  6900:      */     public abstract int getNodeManagerPort();
/*  6901:      */     
/*  6902:      */     public abstract boolean hasNodeManagerHttpPort();
/*  6903:      */     
/*  6904:      */     public abstract int getNodeManagerHttpPort();
/*  6905:      */     
/*  6906:      */     public abstract boolean hasContainerId();
/*  6907:      */     
/*  6908:      */     public abstract YarnProtos.ContainerIdProto getContainerId();
/*  6909:      */     
/*  6910:      */     public abstract YarnProtos.ContainerIdProtoOrBuilder getContainerIdOrBuilder();
/*  6911:      */   }
/*  6912:      */   
/*  6913:      */   public static final class TaskAttemptReportProto
/*  6914:      */     extends GeneratedMessage
/*  6915:      */     implements MRProtos.TaskAttemptReportProtoOrBuilder
/*  6916:      */   {
/*  6917:      */     private static final TaskAttemptReportProto defaultInstance;
/*  6918:      */     private final UnknownFieldSet unknownFields;
/*  6919:      */     
/*  6920:      */     private TaskAttemptReportProto(GeneratedMessage.Builder<?> builder)
/*  6921:      */     {
/*  6922: 7274 */       super();
/*  6923: 7275 */       this.unknownFields = builder.getUnknownFields();
/*  6924:      */     }
/*  6925:      */     
/*  6926:      */     private TaskAttemptReportProto(boolean noInit)
/*  6927:      */     {
/*  6928: 7277 */       this.unknownFields = UnknownFieldSet.getDefaultInstance();
/*  6929:      */     }
/*  6930:      */     
/*  6931:      */     public static TaskAttemptReportProto getDefaultInstance()
/*  6932:      */     {
/*  6933: 7281 */       return defaultInstance;
/*  6934:      */     }
/*  6935:      */     
/*  6936:      */     public TaskAttemptReportProto getDefaultInstanceForType()
/*  6937:      */     {
/*  6938: 7285 */       return defaultInstance;
/*  6939:      */     }
/*  6940:      */     
/*  6941:      */     public final UnknownFieldSet getUnknownFields()
/*  6942:      */     {
/*  6943: 7292 */       return this.unknownFields;
/*  6944:      */     }
/*  6945:      */     
/*  6946:      */     private TaskAttemptReportProto(CodedInputStream input, ExtensionRegistryLite extensionRegistry)
/*  6947:      */       throws InvalidProtocolBufferException
/*  6948:      */     {
/*  6949: 7298 */       initFields();
/*  6950: 7299 */       int mutable_bitField0_ = 0;
/*  6951: 7300 */       UnknownFieldSet.Builder unknownFields = UnknownFieldSet.newBuilder();
/*  6952:      */       try
/*  6953:      */       {
/*  6954: 7303 */         boolean done = false;
/*  6955: 7304 */         while (!done)
/*  6956:      */         {
/*  6957: 7305 */           int tag = input.readTag();
/*  6958: 7306 */           switch (tag)
/*  6959:      */           {
/*  6960:      */           case 0: 
/*  6961: 7308 */             done = true;
/*  6962: 7309 */             break;
/*  6963:      */           default: 
/*  6964: 7311 */             if (!parseUnknownField(input, unknownFields, extensionRegistry, tag)) {
/*  6965: 7313 */               done = true;
/*  6966:      */             }
/*  6967:      */             break;
/*  6968:      */           case 10: 
/*  6969: 7318 */             MRProtos.TaskAttemptIdProto.Builder subBuilder = null;
/*  6970: 7319 */             if ((this.bitField0_ & 0x1) == 1) {
/*  6971: 7320 */               subBuilder = this.taskAttemptId_.toBuilder();
/*  6972:      */             }
/*  6973: 7322 */             this.taskAttemptId_ = ((MRProtos.TaskAttemptIdProto)input.readMessage(MRProtos.TaskAttemptIdProto.PARSER, extensionRegistry));
/*  6974: 7323 */             if (subBuilder != null)
/*  6975:      */             {
/*  6976: 7324 */               subBuilder.mergeFrom(this.taskAttemptId_);
/*  6977: 7325 */               this.taskAttemptId_ = subBuilder.buildPartial();
/*  6978:      */             }
/*  6979: 7327 */             this.bitField0_ |= 0x1;
/*  6980: 7328 */             break;
/*  6981:      */           case 16: 
/*  6982: 7331 */             int rawValue = input.readEnum();
/*  6983: 7332 */             MRProtos.TaskAttemptStateProto value = MRProtos.TaskAttemptStateProto.valueOf(rawValue);
/*  6984: 7333 */             if (value == null)
/*  6985:      */             {
/*  6986: 7334 */               unknownFields.mergeVarintField(2, rawValue);
/*  6987:      */             }
/*  6988:      */             else
/*  6989:      */             {
/*  6990: 7336 */               this.bitField0_ |= 0x2;
/*  6991: 7337 */               this.taskAttemptState_ = value;
/*  6992:      */             }
/*  6993: 7339 */             break;
/*  6994:      */           case 29: 
/*  6995: 7342 */             this.bitField0_ |= 0x4;
/*  6996: 7343 */             this.progress_ = input.readFloat();
/*  6997: 7344 */             break;
/*  6998:      */           case 32: 
/*  6999: 7347 */             this.bitField0_ |= 0x8;
/*  7000: 7348 */             this.startTime_ = input.readInt64();
/*  7001: 7349 */             break;
/*  7002:      */           case 40: 
/*  7003: 7352 */             this.bitField0_ |= 0x10;
/*  7004: 7353 */             this.finishTime_ = input.readInt64();
/*  7005: 7354 */             break;
/*  7006:      */           case 50: 
/*  7007: 7357 */             MRProtos.CountersProto.Builder subBuilder = null;
/*  7008: 7358 */             if ((this.bitField0_ & 0x20) == 32) {
/*  7009: 7359 */               subBuilder = this.counters_.toBuilder();
/*  7010:      */             }
/*  7011: 7361 */             this.counters_ = ((MRProtos.CountersProto)input.readMessage(MRProtos.CountersProto.PARSER, extensionRegistry));
/*  7012: 7362 */             if (subBuilder != null)
/*  7013:      */             {
/*  7014: 7363 */               subBuilder.mergeFrom(this.counters_);
/*  7015: 7364 */               this.counters_ = subBuilder.buildPartial();
/*  7016:      */             }
/*  7017: 7366 */             this.bitField0_ |= 0x20;
/*  7018: 7367 */             break;
/*  7019:      */           case 58: 
/*  7020: 7370 */             this.bitField0_ |= 0x40;
/*  7021: 7371 */             this.diagnosticInfo_ = input.readBytes();
/*  7022: 7372 */             break;
/*  7023:      */           case 66: 
/*  7024: 7375 */             this.bitField0_ |= 0x80;
/*  7025: 7376 */             this.stateString_ = input.readBytes();
/*  7026: 7377 */             break;
/*  7027:      */           case 72: 
/*  7028: 7380 */             int rawValue = input.readEnum();
/*  7029: 7381 */             MRProtos.PhaseProto value = MRProtos.PhaseProto.valueOf(rawValue);
/*  7030: 7382 */             if (value == null)
/*  7031:      */             {
/*  7032: 7383 */               unknownFields.mergeVarintField(9, rawValue);
/*  7033:      */             }
/*  7034:      */             else
/*  7035:      */             {
/*  7036: 7385 */               this.bitField0_ |= 0x100;
/*  7037: 7386 */               this.phase_ = value;
/*  7038:      */             }
/*  7039: 7388 */             break;
/*  7040:      */           case 80: 
/*  7041: 7391 */             this.bitField0_ |= 0x200;
/*  7042: 7392 */             this.shuffleFinishTime_ = input.readInt64();
/*  7043: 7393 */             break;
/*  7044:      */           case 88: 
/*  7045: 7396 */             this.bitField0_ |= 0x400;
/*  7046: 7397 */             this.sortFinishTime_ = input.readInt64();
/*  7047: 7398 */             break;
/*  7048:      */           case 98: 
/*  7049: 7401 */             this.bitField0_ |= 0x800;
/*  7050: 7402 */             this.nodeManagerHost_ = input.readBytes();
/*  7051: 7403 */             break;
/*  7052:      */           case 104: 
/*  7053: 7406 */             this.bitField0_ |= 0x1000;
/*  7054: 7407 */             this.nodeManagerPort_ = input.readInt32();
/*  7055: 7408 */             break;
/*  7056:      */           case 112: 
/*  7057: 7411 */             this.bitField0_ |= 0x2000;
/*  7058: 7412 */             this.nodeManagerHttpPort_ = input.readInt32();
/*  7059: 7413 */             break;
/*  7060:      */           case 122: 
/*  7061: 7416 */             YarnProtos.ContainerIdProto.Builder subBuilder = null;
/*  7062: 7417 */             if ((this.bitField0_ & 0x4000) == 16384) {
/*  7063: 7418 */               subBuilder = this.containerId_.toBuilder();
/*  7064:      */             }
/*  7065: 7420 */             this.containerId_ = ((YarnProtos.ContainerIdProto)input.readMessage(YarnProtos.ContainerIdProto.PARSER, extensionRegistry));
/*  7066: 7421 */             if (subBuilder != null)
/*  7067:      */             {
/*  7068: 7422 */               subBuilder.mergeFrom(this.containerId_);
/*  7069: 7423 */               this.containerId_ = subBuilder.buildPartial();
/*  7070:      */             }
/*  7071: 7425 */             this.bitField0_ |= 0x4000;
/*  7072:      */           }
/*  7073:      */         }
/*  7074:      */       }
/*  7075:      */       catch (InvalidProtocolBufferException e)
/*  7076:      */       {
/*  7077: 7431 */         throw e.setUnfinishedMessage(this);
/*  7078:      */       }
/*  7079:      */       catch (IOException e)
/*  7080:      */       {
/*  7081: 7433 */         throw new InvalidProtocolBufferException(e.getMessage()).setUnfinishedMessage(this);
/*  7082:      */       }
/*  7083:      */       finally
/*  7084:      */       {
/*  7085: 7436 */         this.unknownFields = unknownFields.build();
/*  7086: 7437 */         makeExtensionsImmutable();
/*  7087:      */       }
/*  7088:      */     }
/*  7089:      */     
/*  7090:      */     public static final Descriptors.Descriptor getDescriptor()
/*  7091:      */     {
/*  7092: 7442 */       return MRProtos.internal_static_hadoop_mapreduce_TaskAttemptReportProto_descriptor;
/*  7093:      */     }
/*  7094:      */     
/*  7095:      */     protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable()
/*  7096:      */     {
/*  7097: 7447 */       return MRProtos.internal_static_hadoop_mapreduce_TaskAttemptReportProto_fieldAccessorTable.ensureFieldAccessorsInitialized(TaskAttemptReportProto.class, Builder.class);
/*  7098:      */     }
/*  7099:      */     
/*  7100: 7452 */     public static Parser<TaskAttemptReportProto> PARSER = new AbstractParser()
/*  7101:      */     {
/*  7102:      */       public MRProtos.TaskAttemptReportProto parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry)
/*  7103:      */         throws InvalidProtocolBufferException
/*  7104:      */       {
/*  7105: 7458 */         return new MRProtos.TaskAttemptReportProto(input, extensionRegistry, null);
/*  7106:      */       }
/*  7107:      */     };
/*  7108:      */     private int bitField0_;
/*  7109:      */     public static final int TASK_ATTEMPT_ID_FIELD_NUMBER = 1;
/*  7110:      */     private MRProtos.TaskAttemptIdProto taskAttemptId_;
/*  7111:      */     public static final int TASK_ATTEMPT_STATE_FIELD_NUMBER = 2;
/*  7112:      */     private MRProtos.TaskAttemptStateProto taskAttemptState_;
/*  7113:      */     public static final int PROGRESS_FIELD_NUMBER = 3;
/*  7114:      */     private float progress_;
/*  7115:      */     public static final int START_TIME_FIELD_NUMBER = 4;
/*  7116:      */     private long startTime_;
/*  7117:      */     public static final int FINISH_TIME_FIELD_NUMBER = 5;
/*  7118:      */     private long finishTime_;
/*  7119:      */     public static final int COUNTERS_FIELD_NUMBER = 6;
/*  7120:      */     private MRProtos.CountersProto counters_;
/*  7121:      */     public static final int DIAGNOSTIC_INFO_FIELD_NUMBER = 7;
/*  7122:      */     private Object diagnosticInfo_;
/*  7123:      */     public static final int STATE_STRING_FIELD_NUMBER = 8;
/*  7124:      */     private Object stateString_;
/*  7125:      */     public static final int PHASE_FIELD_NUMBER = 9;
/*  7126:      */     private MRProtos.PhaseProto phase_;
/*  7127:      */     public static final int SHUFFLE_FINISH_TIME_FIELD_NUMBER = 10;
/*  7128:      */     private long shuffleFinishTime_;
/*  7129:      */     public static final int SORT_FINISH_TIME_FIELD_NUMBER = 11;
/*  7130:      */     private long sortFinishTime_;
/*  7131:      */     public static final int NODE_MANAGER_HOST_FIELD_NUMBER = 12;
/*  7132:      */     private Object nodeManagerHost_;
/*  7133:      */     public static final int NODE_MANAGER_PORT_FIELD_NUMBER = 13;
/*  7134:      */     private int nodeManagerPort_;
/*  7135:      */     public static final int NODE_MANAGER_HTTP_PORT_FIELD_NUMBER = 14;
/*  7136:      */     private int nodeManagerHttpPort_;
/*  7137:      */     public static final int CONTAINER_ID_FIELD_NUMBER = 15;
/*  7138:      */     private YarnProtos.ContainerIdProto containerId_;
/*  7139:      */     
/*  7140:      */     public Parser<TaskAttemptReportProto> getParserForType()
/*  7141:      */     {
/*  7142: 7464 */       return PARSER;
/*  7143:      */     }
/*  7144:      */     
/*  7145:      */     public boolean hasTaskAttemptId()
/*  7146:      */     {
/*  7147: 7475 */       return (this.bitField0_ & 0x1) == 1;
/*  7148:      */     }
/*  7149:      */     
/*  7150:      */     public MRProtos.TaskAttemptIdProto getTaskAttemptId()
/*  7151:      */     {
/*  7152: 7481 */       return this.taskAttemptId_;
/*  7153:      */     }
/*  7154:      */     
/*  7155:      */     public MRProtos.TaskAttemptIdProtoOrBuilder getTaskAttemptIdOrBuilder()
/*  7156:      */     {
/*  7157: 7487 */       return this.taskAttemptId_;
/*  7158:      */     }
/*  7159:      */     
/*  7160:      */     public boolean hasTaskAttemptState()
/*  7161:      */     {
/*  7162: 7497 */       return (this.bitField0_ & 0x2) == 2;
/*  7163:      */     }
/*  7164:      */     
/*  7165:      */     public MRProtos.TaskAttemptStateProto getTaskAttemptState()
/*  7166:      */     {
/*  7167: 7503 */       return this.taskAttemptState_;
/*  7168:      */     }
/*  7169:      */     
/*  7170:      */     public boolean hasProgress()
/*  7171:      */     {
/*  7172: 7513 */       return (this.bitField0_ & 0x4) == 4;
/*  7173:      */     }
/*  7174:      */     
/*  7175:      */     public float getProgress()
/*  7176:      */     {
/*  7177: 7519 */       return this.progress_;
/*  7178:      */     }
/*  7179:      */     
/*  7180:      */     public boolean hasStartTime()
/*  7181:      */     {
/*  7182: 7529 */       return (this.bitField0_ & 0x8) == 8;
/*  7183:      */     }
/*  7184:      */     
/*  7185:      */     public long getStartTime()
/*  7186:      */     {
/*  7187: 7535 */       return this.startTime_;
/*  7188:      */     }
/*  7189:      */     
/*  7190:      */     public boolean hasFinishTime()
/*  7191:      */     {
/*  7192: 7545 */       return (this.bitField0_ & 0x10) == 16;
/*  7193:      */     }
/*  7194:      */     
/*  7195:      */     public long getFinishTime()
/*  7196:      */     {
/*  7197: 7551 */       return this.finishTime_;
/*  7198:      */     }
/*  7199:      */     
/*  7200:      */     public boolean hasCounters()
/*  7201:      */     {
/*  7202: 7561 */       return (this.bitField0_ & 0x20) == 32;
/*  7203:      */     }
/*  7204:      */     
/*  7205:      */     public MRProtos.CountersProto getCounters()
/*  7206:      */     {
/*  7207: 7567 */       return this.counters_;
/*  7208:      */     }
/*  7209:      */     
/*  7210:      */     public MRProtos.CountersProtoOrBuilder getCountersOrBuilder()
/*  7211:      */     {
/*  7212: 7573 */       return this.counters_;
/*  7213:      */     }
/*  7214:      */     
/*  7215:      */     public boolean hasDiagnosticInfo()
/*  7216:      */     {
/*  7217: 7583 */       return (this.bitField0_ & 0x40) == 64;
/*  7218:      */     }
/*  7219:      */     
/*  7220:      */     public String getDiagnosticInfo()
/*  7221:      */     {
/*  7222: 7589 */       Object ref = this.diagnosticInfo_;
/*  7223: 7590 */       if ((ref instanceof String)) {
/*  7224: 7591 */         return (String)ref;
/*  7225:      */       }
/*  7226: 7593 */       ByteString bs = (ByteString)ref;
/*  7227:      */       
/*  7228: 7595 */       String s = bs.toStringUtf8();
/*  7229: 7596 */       if (bs.isValidUtf8()) {
/*  7230: 7597 */         this.diagnosticInfo_ = s;
/*  7231:      */       }
/*  7232: 7599 */       return s;
/*  7233:      */     }
/*  7234:      */     
/*  7235:      */     public ByteString getDiagnosticInfoBytes()
/*  7236:      */     {
/*  7237: 7607 */       Object ref = this.diagnosticInfo_;
/*  7238: 7608 */       if ((ref instanceof String))
/*  7239:      */       {
/*  7240: 7609 */         ByteString b = ByteString.copyFromUtf8((String)ref);
/*  7241:      */         
/*  7242:      */ 
/*  7243: 7612 */         this.diagnosticInfo_ = b;
/*  7244: 7613 */         return b;
/*  7245:      */       }
/*  7246: 7615 */       return (ByteString)ref;
/*  7247:      */     }
/*  7248:      */     
/*  7249:      */     public boolean hasStateString()
/*  7250:      */     {
/*  7251: 7626 */       return (this.bitField0_ & 0x80) == 128;
/*  7252:      */     }
/*  7253:      */     
/*  7254:      */     public String getStateString()
/*  7255:      */     {
/*  7256: 7632 */       Object ref = this.stateString_;
/*  7257: 7633 */       if ((ref instanceof String)) {
/*  7258: 7634 */         return (String)ref;
/*  7259:      */       }
/*  7260: 7636 */       ByteString bs = (ByteString)ref;
/*  7261:      */       
/*  7262: 7638 */       String s = bs.toStringUtf8();
/*  7263: 7639 */       if (bs.isValidUtf8()) {
/*  7264: 7640 */         this.stateString_ = s;
/*  7265:      */       }
/*  7266: 7642 */       return s;
/*  7267:      */     }
/*  7268:      */     
/*  7269:      */     public ByteString getStateStringBytes()
/*  7270:      */     {
/*  7271: 7650 */       Object ref = this.stateString_;
/*  7272: 7651 */       if ((ref instanceof String))
/*  7273:      */       {
/*  7274: 7652 */         ByteString b = ByteString.copyFromUtf8((String)ref);
/*  7275:      */         
/*  7276:      */ 
/*  7277: 7655 */         this.stateString_ = b;
/*  7278: 7656 */         return b;
/*  7279:      */       }
/*  7280: 7658 */       return (ByteString)ref;
/*  7281:      */     }
/*  7282:      */     
/*  7283:      */     public boolean hasPhase()
/*  7284:      */     {
/*  7285: 7669 */       return (this.bitField0_ & 0x100) == 256;
/*  7286:      */     }
/*  7287:      */     
/*  7288:      */     public MRProtos.PhaseProto getPhase()
/*  7289:      */     {
/*  7290: 7675 */       return this.phase_;
/*  7291:      */     }
/*  7292:      */     
/*  7293:      */     public boolean hasShuffleFinishTime()
/*  7294:      */     {
/*  7295: 7685 */       return (this.bitField0_ & 0x200) == 512;
/*  7296:      */     }
/*  7297:      */     
/*  7298:      */     public long getShuffleFinishTime()
/*  7299:      */     {
/*  7300: 7691 */       return this.shuffleFinishTime_;
/*  7301:      */     }
/*  7302:      */     
/*  7303:      */     public boolean hasSortFinishTime()
/*  7304:      */     {
/*  7305: 7701 */       return (this.bitField0_ & 0x400) == 1024;
/*  7306:      */     }
/*  7307:      */     
/*  7308:      */     public long getSortFinishTime()
/*  7309:      */     {
/*  7310: 7707 */       return this.sortFinishTime_;
/*  7311:      */     }
/*  7312:      */     
/*  7313:      */     public boolean hasNodeManagerHost()
/*  7314:      */     {
/*  7315: 7717 */       return (this.bitField0_ & 0x800) == 2048;
/*  7316:      */     }
/*  7317:      */     
/*  7318:      */     public String getNodeManagerHost()
/*  7319:      */     {
/*  7320: 7723 */       Object ref = this.nodeManagerHost_;
/*  7321: 7724 */       if ((ref instanceof String)) {
/*  7322: 7725 */         return (String)ref;
/*  7323:      */       }
/*  7324: 7727 */       ByteString bs = (ByteString)ref;
/*  7325:      */       
/*  7326: 7729 */       String s = bs.toStringUtf8();
/*  7327: 7730 */       if (bs.isValidUtf8()) {
/*  7328: 7731 */         this.nodeManagerHost_ = s;
/*  7329:      */       }
/*  7330: 7733 */       return s;
/*  7331:      */     }
/*  7332:      */     
/*  7333:      */     public ByteString getNodeManagerHostBytes()
/*  7334:      */     {
/*  7335: 7741 */       Object ref = this.nodeManagerHost_;
/*  7336: 7742 */       if ((ref instanceof String))
/*  7337:      */       {
/*  7338: 7743 */         ByteString b = ByteString.copyFromUtf8((String)ref);
/*  7339:      */         
/*  7340:      */ 
/*  7341: 7746 */         this.nodeManagerHost_ = b;
/*  7342: 7747 */         return b;
/*  7343:      */       }
/*  7344: 7749 */       return (ByteString)ref;
/*  7345:      */     }
/*  7346:      */     
/*  7347:      */     public boolean hasNodeManagerPort()
/*  7348:      */     {
/*  7349: 7760 */       return (this.bitField0_ & 0x1000) == 4096;
/*  7350:      */     }
/*  7351:      */     
/*  7352:      */     public int getNodeManagerPort()
/*  7353:      */     {
/*  7354: 7766 */       return this.nodeManagerPort_;
/*  7355:      */     }
/*  7356:      */     
/*  7357:      */     public boolean hasNodeManagerHttpPort()
/*  7358:      */     {
/*  7359: 7776 */       return (this.bitField0_ & 0x2000) == 8192;
/*  7360:      */     }
/*  7361:      */     
/*  7362:      */     public int getNodeManagerHttpPort()
/*  7363:      */     {
/*  7364: 7782 */       return this.nodeManagerHttpPort_;
/*  7365:      */     }
/*  7366:      */     
/*  7367:      */     public boolean hasContainerId()
/*  7368:      */     {
/*  7369: 7792 */       return (this.bitField0_ & 0x4000) == 16384;
/*  7370:      */     }
/*  7371:      */     
/*  7372:      */     public YarnProtos.ContainerIdProto getContainerId()
/*  7373:      */     {
/*  7374: 7798 */       return this.containerId_;
/*  7375:      */     }
/*  7376:      */     
/*  7377:      */     public YarnProtos.ContainerIdProtoOrBuilder getContainerIdOrBuilder()
/*  7378:      */     {
/*  7379: 7804 */       return this.containerId_;
/*  7380:      */     }
/*  7381:      */     
/*  7382:      */     private void initFields()
/*  7383:      */     {
/*  7384: 7808 */       this.taskAttemptId_ = MRProtos.TaskAttemptIdProto.getDefaultInstance();
/*  7385: 7809 */       this.taskAttemptState_ = MRProtos.TaskAttemptStateProto.TA_NEW;
/*  7386: 7810 */       this.progress_ = 0.0F;
/*  7387: 7811 */       this.startTime_ = 0L;
/*  7388: 7812 */       this.finishTime_ = 0L;
/*  7389: 7813 */       this.counters_ = MRProtos.CountersProto.getDefaultInstance();
/*  7390: 7814 */       this.diagnosticInfo_ = "";
/*  7391: 7815 */       this.stateString_ = "";
/*  7392: 7816 */       this.phase_ = MRProtos.PhaseProto.P_STARTING;
/*  7393: 7817 */       this.shuffleFinishTime_ = 0L;
/*  7394: 7818 */       this.sortFinishTime_ = 0L;
/*  7395: 7819 */       this.nodeManagerHost_ = "";
/*  7396: 7820 */       this.nodeManagerPort_ = 0;
/*  7397: 7821 */       this.nodeManagerHttpPort_ = 0;
/*  7398: 7822 */       this.containerId_ = YarnProtos.ContainerIdProto.getDefaultInstance();
/*  7399:      */     }
/*  7400:      */     
/*  7401: 7824 */     private byte memoizedIsInitialized = -1;
/*  7402:      */     
/*  7403:      */     public final boolean isInitialized()
/*  7404:      */     {
/*  7405: 7826 */       byte isInitialized = this.memoizedIsInitialized;
/*  7406: 7827 */       if (isInitialized != -1) {
/*  7407: 7827 */         return isInitialized == 1;
/*  7408:      */       }
/*  7409: 7829 */       this.memoizedIsInitialized = 1;
/*  7410: 7830 */       return true;
/*  7411:      */     }
/*  7412:      */     
/*  7413:      */     public void writeTo(CodedOutputStream output)
/*  7414:      */       throws IOException
/*  7415:      */     {
/*  7416: 7835 */       getSerializedSize();
/*  7417: 7836 */       if ((this.bitField0_ & 0x1) == 1) {
/*  7418: 7837 */         output.writeMessage(1, this.taskAttemptId_);
/*  7419:      */       }
/*  7420: 7839 */       if ((this.bitField0_ & 0x2) == 2) {
/*  7421: 7840 */         output.writeEnum(2, this.taskAttemptState_.getNumber());
/*  7422:      */       }
/*  7423: 7842 */       if ((this.bitField0_ & 0x4) == 4) {
/*  7424: 7843 */         output.writeFloat(3, this.progress_);
/*  7425:      */       }
/*  7426: 7845 */       if ((this.bitField0_ & 0x8) == 8) {
/*  7427: 7846 */         output.writeInt64(4, this.startTime_);
/*  7428:      */       }
/*  7429: 7848 */       if ((this.bitField0_ & 0x10) == 16) {
/*  7430: 7849 */         output.writeInt64(5, this.finishTime_);
/*  7431:      */       }
/*  7432: 7851 */       if ((this.bitField0_ & 0x20) == 32) {
/*  7433: 7852 */         output.writeMessage(6, this.counters_);
/*  7434:      */       }
/*  7435: 7854 */       if ((this.bitField0_ & 0x40) == 64) {
/*  7436: 7855 */         output.writeBytes(7, getDiagnosticInfoBytes());
/*  7437:      */       }
/*  7438: 7857 */       if ((this.bitField0_ & 0x80) == 128) {
/*  7439: 7858 */         output.writeBytes(8, getStateStringBytes());
/*  7440:      */       }
/*  7441: 7860 */       if ((this.bitField0_ & 0x100) == 256) {
/*  7442: 7861 */         output.writeEnum(9, this.phase_.getNumber());
/*  7443:      */       }
/*  7444: 7863 */       if ((this.bitField0_ & 0x200) == 512) {
/*  7445: 7864 */         output.writeInt64(10, this.shuffleFinishTime_);
/*  7446:      */       }
/*  7447: 7866 */       if ((this.bitField0_ & 0x400) == 1024) {
/*  7448: 7867 */         output.writeInt64(11, this.sortFinishTime_);
/*  7449:      */       }
/*  7450: 7869 */       if ((this.bitField0_ & 0x800) == 2048) {
/*  7451: 7870 */         output.writeBytes(12, getNodeManagerHostBytes());
/*  7452:      */       }
/*  7453: 7872 */       if ((this.bitField0_ & 0x1000) == 4096) {
/*  7454: 7873 */         output.writeInt32(13, this.nodeManagerPort_);
/*  7455:      */       }
/*  7456: 7875 */       if ((this.bitField0_ & 0x2000) == 8192) {
/*  7457: 7876 */         output.writeInt32(14, this.nodeManagerHttpPort_);
/*  7458:      */       }
/*  7459: 7878 */       if ((this.bitField0_ & 0x4000) == 16384) {
/*  7460: 7879 */         output.writeMessage(15, this.containerId_);
/*  7461:      */       }
/*  7462: 7881 */       getUnknownFields().writeTo(output);
/*  7463:      */     }
/*  7464:      */     
/*  7465: 7884 */     private int memoizedSerializedSize = -1;
/*  7466:      */     private static final long serialVersionUID = 0L;
/*  7467:      */     
/*  7468:      */     public int getSerializedSize()
/*  7469:      */     {
/*  7470: 7886 */       int size = this.memoizedSerializedSize;
/*  7471: 7887 */       if (size != -1) {
/*  7472: 7887 */         return size;
/*  7473:      */       }
/*  7474: 7889 */       size = 0;
/*  7475: 7890 */       if ((this.bitField0_ & 0x1) == 1) {
/*  7476: 7891 */         size += CodedOutputStream.computeMessageSize(1, this.taskAttemptId_);
/*  7477:      */       }
/*  7478: 7894 */       if ((this.bitField0_ & 0x2) == 2) {
/*  7479: 7895 */         size += CodedOutputStream.computeEnumSize(2, this.taskAttemptState_.getNumber());
/*  7480:      */       }
/*  7481: 7898 */       if ((this.bitField0_ & 0x4) == 4) {
/*  7482: 7899 */         size += CodedOutputStream.computeFloatSize(3, this.progress_);
/*  7483:      */       }
/*  7484: 7902 */       if ((this.bitField0_ & 0x8) == 8) {
/*  7485: 7903 */         size += CodedOutputStream.computeInt64Size(4, this.startTime_);
/*  7486:      */       }
/*  7487: 7906 */       if ((this.bitField0_ & 0x10) == 16) {
/*  7488: 7907 */         size += CodedOutputStream.computeInt64Size(5, this.finishTime_);
/*  7489:      */       }
/*  7490: 7910 */       if ((this.bitField0_ & 0x20) == 32) {
/*  7491: 7911 */         size += CodedOutputStream.computeMessageSize(6, this.counters_);
/*  7492:      */       }
/*  7493: 7914 */       if ((this.bitField0_ & 0x40) == 64) {
/*  7494: 7915 */         size += CodedOutputStream.computeBytesSize(7, getDiagnosticInfoBytes());
/*  7495:      */       }
/*  7496: 7918 */       if ((this.bitField0_ & 0x80) == 128) {
/*  7497: 7919 */         size += CodedOutputStream.computeBytesSize(8, getStateStringBytes());
/*  7498:      */       }
/*  7499: 7922 */       if ((this.bitField0_ & 0x100) == 256) {
/*  7500: 7923 */         size += CodedOutputStream.computeEnumSize(9, this.phase_.getNumber());
/*  7501:      */       }
/*  7502: 7926 */       if ((this.bitField0_ & 0x200) == 512) {
/*  7503: 7927 */         size += CodedOutputStream.computeInt64Size(10, this.shuffleFinishTime_);
/*  7504:      */       }
/*  7505: 7930 */       if ((this.bitField0_ & 0x400) == 1024) {
/*  7506: 7931 */         size += CodedOutputStream.computeInt64Size(11, this.sortFinishTime_);
/*  7507:      */       }
/*  7508: 7934 */       if ((this.bitField0_ & 0x800) == 2048) {
/*  7509: 7935 */         size += CodedOutputStream.computeBytesSize(12, getNodeManagerHostBytes());
/*  7510:      */       }
/*  7511: 7938 */       if ((this.bitField0_ & 0x1000) == 4096) {
/*  7512: 7939 */         size += CodedOutputStream.computeInt32Size(13, this.nodeManagerPort_);
/*  7513:      */       }
/*  7514: 7942 */       if ((this.bitField0_ & 0x2000) == 8192) {
/*  7515: 7943 */         size += CodedOutputStream.computeInt32Size(14, this.nodeManagerHttpPort_);
/*  7516:      */       }
/*  7517: 7946 */       if ((this.bitField0_ & 0x4000) == 16384) {
/*  7518: 7947 */         size += CodedOutputStream.computeMessageSize(15, this.containerId_);
/*  7519:      */       }
/*  7520: 7950 */       size += getUnknownFields().getSerializedSize();
/*  7521: 7951 */       this.memoizedSerializedSize = size;
/*  7522: 7952 */       return size;
/*  7523:      */     }
/*  7524:      */     
/*  7525:      */     protected Object writeReplace()
/*  7526:      */       throws ObjectStreamException
/*  7527:      */     {
/*  7528: 7959 */       return super.writeReplace();
/*  7529:      */     }
/*  7530:      */     
/*  7531:      */     public boolean equals(Object obj)
/*  7532:      */     {
/*  7533: 7964 */       if (obj == this) {
/*  7534: 7965 */         return true;
/*  7535:      */       }
/*  7536: 7967 */       if (!(obj instanceof TaskAttemptReportProto)) {
/*  7537: 7968 */         return super.equals(obj);
/*  7538:      */       }
/*  7539: 7970 */       TaskAttemptReportProto other = (TaskAttemptReportProto)obj;
/*  7540:      */       
/*  7541: 7972 */       boolean result = true;
/*  7542: 7973 */       result = (result) && (hasTaskAttemptId() == other.hasTaskAttemptId());
/*  7543: 7974 */       if (hasTaskAttemptId()) {
/*  7544: 7975 */         result = (result) && (getTaskAttemptId().equals(other.getTaskAttemptId()));
/*  7545:      */       }
/*  7546: 7978 */       result = (result) && (hasTaskAttemptState() == other.hasTaskAttemptState());
/*  7547: 7979 */       if (hasTaskAttemptState()) {
/*  7548: 7980 */         result = (result) && (getTaskAttemptState() == other.getTaskAttemptState());
/*  7549:      */       }
/*  7550: 7983 */       result = (result) && (hasProgress() == other.hasProgress());
/*  7551: 7984 */       if (hasProgress()) {
/*  7552: 7985 */         result = (result) && (Float.floatToIntBits(getProgress()) == Float.floatToIntBits(other.getProgress()));
/*  7553:      */       }
/*  7554: 7987 */       result = (result) && (hasStartTime() == other.hasStartTime());
/*  7555: 7988 */       if (hasStartTime()) {
/*  7556: 7989 */         result = (result) && (getStartTime() == other.getStartTime());
/*  7557:      */       }
/*  7558: 7992 */       result = (result) && (hasFinishTime() == other.hasFinishTime());
/*  7559: 7993 */       if (hasFinishTime()) {
/*  7560: 7994 */         result = (result) && (getFinishTime() == other.getFinishTime());
/*  7561:      */       }
/*  7562: 7997 */       result = (result) && (hasCounters() == other.hasCounters());
/*  7563: 7998 */       if (hasCounters()) {
/*  7564: 7999 */         result = (result) && (getCounters().equals(other.getCounters()));
/*  7565:      */       }
/*  7566: 8002 */       result = (result) && (hasDiagnosticInfo() == other.hasDiagnosticInfo());
/*  7567: 8003 */       if (hasDiagnosticInfo()) {
/*  7568: 8004 */         result = (result) && (getDiagnosticInfo().equals(other.getDiagnosticInfo()));
/*  7569:      */       }
/*  7570: 8007 */       result = (result) && (hasStateString() == other.hasStateString());
/*  7571: 8008 */       if (hasStateString()) {
/*  7572: 8009 */         result = (result) && (getStateString().equals(other.getStateString()));
/*  7573:      */       }
/*  7574: 8012 */       result = (result) && (hasPhase() == other.hasPhase());
/*  7575: 8013 */       if (hasPhase()) {
/*  7576: 8014 */         result = (result) && (getPhase() == other.getPhase());
/*  7577:      */       }
/*  7578: 8017 */       result = (result) && (hasShuffleFinishTime() == other.hasShuffleFinishTime());
/*  7579: 8018 */       if (hasShuffleFinishTime()) {
/*  7580: 8019 */         result = (result) && (getShuffleFinishTime() == other.getShuffleFinishTime());
/*  7581:      */       }
/*  7582: 8022 */       result = (result) && (hasSortFinishTime() == other.hasSortFinishTime());
/*  7583: 8023 */       if (hasSortFinishTime()) {
/*  7584: 8024 */         result = (result) && (getSortFinishTime() == other.getSortFinishTime());
/*  7585:      */       }
/*  7586: 8027 */       result = (result) && (hasNodeManagerHost() == other.hasNodeManagerHost());
/*  7587: 8028 */       if (hasNodeManagerHost()) {
/*  7588: 8029 */         result = (result) && (getNodeManagerHost().equals(other.getNodeManagerHost()));
/*  7589:      */       }
/*  7590: 8032 */       result = (result) && (hasNodeManagerPort() == other.hasNodeManagerPort());
/*  7591: 8033 */       if (hasNodeManagerPort()) {
/*  7592: 8034 */         result = (result) && (getNodeManagerPort() == other.getNodeManagerPort());
/*  7593:      */       }
/*  7594: 8037 */       result = (result) && (hasNodeManagerHttpPort() == other.hasNodeManagerHttpPort());
/*  7595: 8038 */       if (hasNodeManagerHttpPort()) {
/*  7596: 8039 */         result = (result) && (getNodeManagerHttpPort() == other.getNodeManagerHttpPort());
/*  7597:      */       }
/*  7598: 8042 */       result = (result) && (hasContainerId() == other.hasContainerId());
/*  7599: 8043 */       if (hasContainerId()) {
/*  7600: 8044 */         result = (result) && (getContainerId().equals(other.getContainerId()));
/*  7601:      */       }
/*  7602: 8047 */       result = (result) && (getUnknownFields().equals(other.getUnknownFields()));
/*  7603:      */       
/*  7604: 8049 */       return result;
/*  7605:      */     }
/*  7606:      */     
/*  7607: 8052 */     private int memoizedHashCode = 0;
/*  7608:      */     
/*  7609:      */     public int hashCode()
/*  7610:      */     {
/*  7611: 8055 */       if (this.memoizedHashCode != 0) {
/*  7612: 8056 */         return this.memoizedHashCode;
/*  7613:      */       }
/*  7614: 8058 */       int hash = 41;
/*  7615: 8059 */       hash = 19 * hash + getDescriptorForType().hashCode();
/*  7616: 8060 */       if (hasTaskAttemptId())
/*  7617:      */       {
/*  7618: 8061 */         hash = 37 * hash + 1;
/*  7619: 8062 */         hash = 53 * hash + getTaskAttemptId().hashCode();
/*  7620:      */       }
/*  7621: 8064 */       if (hasTaskAttemptState())
/*  7622:      */       {
/*  7623: 8065 */         hash = 37 * hash + 2;
/*  7624: 8066 */         hash = 53 * hash + hashEnum(getTaskAttemptState());
/*  7625:      */       }
/*  7626: 8068 */       if (hasProgress())
/*  7627:      */       {
/*  7628: 8069 */         hash = 37 * hash + 3;
/*  7629: 8070 */         hash = 53 * hash + Float.floatToIntBits(getProgress());
/*  7630:      */       }
/*  7631: 8073 */       if (hasStartTime())
/*  7632:      */       {
/*  7633: 8074 */         hash = 37 * hash + 4;
/*  7634: 8075 */         hash = 53 * hash + hashLong(getStartTime());
/*  7635:      */       }
/*  7636: 8077 */       if (hasFinishTime())
/*  7637:      */       {
/*  7638: 8078 */         hash = 37 * hash + 5;
/*  7639: 8079 */         hash = 53 * hash + hashLong(getFinishTime());
/*  7640:      */       }
/*  7641: 8081 */       if (hasCounters())
/*  7642:      */       {
/*  7643: 8082 */         hash = 37 * hash + 6;
/*  7644: 8083 */         hash = 53 * hash + getCounters().hashCode();
/*  7645:      */       }
/*  7646: 8085 */       if (hasDiagnosticInfo())
/*  7647:      */       {
/*  7648: 8086 */         hash = 37 * hash + 7;
/*  7649: 8087 */         hash = 53 * hash + getDiagnosticInfo().hashCode();
/*  7650:      */       }
/*  7651: 8089 */       if (hasStateString())
/*  7652:      */       {
/*  7653: 8090 */         hash = 37 * hash + 8;
/*  7654: 8091 */         hash = 53 * hash + getStateString().hashCode();
/*  7655:      */       }
/*  7656: 8093 */       if (hasPhase())
/*  7657:      */       {
/*  7658: 8094 */         hash = 37 * hash + 9;
/*  7659: 8095 */         hash = 53 * hash + hashEnum(getPhase());
/*  7660:      */       }
/*  7661: 8097 */       if (hasShuffleFinishTime())
/*  7662:      */       {
/*  7663: 8098 */         hash = 37 * hash + 10;
/*  7664: 8099 */         hash = 53 * hash + hashLong(getShuffleFinishTime());
/*  7665:      */       }
/*  7666: 8101 */       if (hasSortFinishTime())
/*  7667:      */       {
/*  7668: 8102 */         hash = 37 * hash + 11;
/*  7669: 8103 */         hash = 53 * hash + hashLong(getSortFinishTime());
/*  7670:      */       }
/*  7671: 8105 */       if (hasNodeManagerHost())
/*  7672:      */       {
/*  7673: 8106 */         hash = 37 * hash + 12;
/*  7674: 8107 */         hash = 53 * hash + getNodeManagerHost().hashCode();
/*  7675:      */       }
/*  7676: 8109 */       if (hasNodeManagerPort())
/*  7677:      */       {
/*  7678: 8110 */         hash = 37 * hash + 13;
/*  7679: 8111 */         hash = 53 * hash + getNodeManagerPort();
/*  7680:      */       }
/*  7681: 8113 */       if (hasNodeManagerHttpPort())
/*  7682:      */       {
/*  7683: 8114 */         hash = 37 * hash + 14;
/*  7684: 8115 */         hash = 53 * hash + getNodeManagerHttpPort();
/*  7685:      */       }
/*  7686: 8117 */       if (hasContainerId())
/*  7687:      */       {
/*  7688: 8118 */         hash = 37 * hash + 15;
/*  7689: 8119 */         hash = 53 * hash + getContainerId().hashCode();
/*  7690:      */       }
/*  7691: 8121 */       hash = 29 * hash + getUnknownFields().hashCode();
/*  7692: 8122 */       this.memoizedHashCode = hash;
/*  7693: 8123 */       return hash;
/*  7694:      */     }
/*  7695:      */     
/*  7696:      */     public static TaskAttemptReportProto parseFrom(ByteString data)
/*  7697:      */       throws InvalidProtocolBufferException
/*  7698:      */     {
/*  7699: 8129 */       return (TaskAttemptReportProto)PARSER.parseFrom(data);
/*  7700:      */     }
/*  7701:      */     
/*  7702:      */     public static TaskAttemptReportProto parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry)
/*  7703:      */       throws InvalidProtocolBufferException
/*  7704:      */     {
/*  7705: 8135 */       return (TaskAttemptReportProto)PARSER.parseFrom(data, extensionRegistry);
/*  7706:      */     }
/*  7707:      */     
/*  7708:      */     public static TaskAttemptReportProto parseFrom(byte[] data)
/*  7709:      */       throws InvalidProtocolBufferException
/*  7710:      */     {
/*  7711: 8139 */       return (TaskAttemptReportProto)PARSER.parseFrom(data);
/*  7712:      */     }
/*  7713:      */     
/*  7714:      */     public static TaskAttemptReportProto parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry)
/*  7715:      */       throws InvalidProtocolBufferException
/*  7716:      */     {
/*  7717: 8145 */       return (TaskAttemptReportProto)PARSER.parseFrom(data, extensionRegistry);
/*  7718:      */     }
/*  7719:      */     
/*  7720:      */     public static TaskAttemptReportProto parseFrom(InputStream input)
/*  7721:      */       throws IOException
/*  7722:      */     {
/*  7723: 8149 */       return (TaskAttemptReportProto)PARSER.parseFrom(input);
/*  7724:      */     }
/*  7725:      */     
/*  7726:      */     public static TaskAttemptReportProto parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry)
/*  7727:      */       throws IOException
/*  7728:      */     {
/*  7729: 8155 */       return (TaskAttemptReportProto)PARSER.parseFrom(input, extensionRegistry);
/*  7730:      */     }
/*  7731:      */     
/*  7732:      */     public static TaskAttemptReportProto parseDelimitedFrom(InputStream input)
/*  7733:      */       throws IOException
/*  7734:      */     {
/*  7735: 8159 */       return (TaskAttemptReportProto)PARSER.parseDelimitedFrom(input);
/*  7736:      */     }
/*  7737:      */     
/*  7738:      */     public static TaskAttemptReportProto parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry)
/*  7739:      */       throws IOException
/*  7740:      */     {
/*  7741: 8165 */       return (TaskAttemptReportProto)PARSER.parseDelimitedFrom(input, extensionRegistry);
/*  7742:      */     }
/*  7743:      */     
/*  7744:      */     public static TaskAttemptReportProto parseFrom(CodedInputStream input)
/*  7745:      */       throws IOException
/*  7746:      */     {
/*  7747: 8170 */       return (TaskAttemptReportProto)PARSER.parseFrom(input);
/*  7748:      */     }
/*  7749:      */     
/*  7750:      */     public static TaskAttemptReportProto parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry)
/*  7751:      */       throws IOException
/*  7752:      */     {
/*  7753: 8176 */       return (TaskAttemptReportProto)PARSER.parseFrom(input, extensionRegistry);
/*  7754:      */     }
/*  7755:      */     
/*  7756:      */     public static Builder newBuilder()
/*  7757:      */     {
/*  7758: 8179 */       return Builder.access$8400();
/*  7759:      */     }
/*  7760:      */     
/*  7761:      */     public Builder newBuilderForType()
/*  7762:      */     {
/*  7763: 8180 */       return newBuilder();
/*  7764:      */     }
/*  7765:      */     
/*  7766:      */     public static Builder newBuilder(TaskAttemptReportProto prototype)
/*  7767:      */     {
/*  7768: 8182 */       return newBuilder().mergeFrom(prototype);
/*  7769:      */     }
/*  7770:      */     
/*  7771:      */     public Builder toBuilder()
/*  7772:      */     {
/*  7773: 8184 */       return newBuilder(this);
/*  7774:      */     }
/*  7775:      */     
/*  7776:      */     protected Builder newBuilderForType(GeneratedMessage.BuilderParent parent)
/*  7777:      */     {
/*  7778: 8189 */       Builder builder = new Builder(parent, null);
/*  7779: 8190 */       return builder;
/*  7780:      */     }
/*  7781:      */     
/*  7782:      */     public static final class Builder
/*  7783:      */       extends GeneratedMessage.Builder<Builder>
/*  7784:      */       implements MRProtos.TaskAttemptReportProtoOrBuilder
/*  7785:      */     {
/*  7786:      */       private int bitField0_;
/*  7787:      */       
/*  7788:      */       public static final Descriptors.Descriptor getDescriptor()
/*  7789:      */       {
/*  7790: 8200 */         return MRProtos.internal_static_hadoop_mapreduce_TaskAttemptReportProto_descriptor;
/*  7791:      */       }
/*  7792:      */       
/*  7793:      */       protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable()
/*  7794:      */       {
/*  7795: 8205 */         return MRProtos.internal_static_hadoop_mapreduce_TaskAttemptReportProto_fieldAccessorTable.ensureFieldAccessorsInitialized(MRProtos.TaskAttemptReportProto.class, Builder.class);
/*  7796:      */       }
/*  7797:      */       
/*  7798:      */       private Builder()
/*  7799:      */       {
/*  7800: 8212 */         maybeForceBuilderInitialization();
/*  7801:      */       }
/*  7802:      */       
/*  7803:      */       private Builder(GeneratedMessage.BuilderParent parent)
/*  7804:      */       {
/*  7805: 8217 */         super();
/*  7806: 8218 */         maybeForceBuilderInitialization();
/*  7807:      */       }
/*  7808:      */       
/*  7809:      */       private void maybeForceBuilderInitialization()
/*  7810:      */       {
/*  7811: 8221 */         if (MRProtos.TaskAttemptReportProto.alwaysUseFieldBuilders)
/*  7812:      */         {
/*  7813: 8222 */           getTaskAttemptIdFieldBuilder();
/*  7814: 8223 */           getCountersFieldBuilder();
/*  7815: 8224 */           getContainerIdFieldBuilder();
/*  7816:      */         }
/*  7817:      */       }
/*  7818:      */       
/*  7819:      */       private static Builder create()
/*  7820:      */       {
/*  7821: 8228 */         return new Builder();
/*  7822:      */       }
/*  7823:      */       
/*  7824:      */       public Builder clear()
/*  7825:      */       {
/*  7826: 8232 */         super.clear();
/*  7827: 8233 */         if (this.taskAttemptIdBuilder_ == null) {
/*  7828: 8234 */           this.taskAttemptId_ = MRProtos.TaskAttemptIdProto.getDefaultInstance();
/*  7829:      */         } else {
/*  7830: 8236 */           this.taskAttemptIdBuilder_.clear();
/*  7831:      */         }
/*  7832: 8238 */         this.bitField0_ &= 0xFFFFFFFE;
/*  7833: 8239 */         this.taskAttemptState_ = MRProtos.TaskAttemptStateProto.TA_NEW;
/*  7834: 8240 */         this.bitField0_ &= 0xFFFFFFFD;
/*  7835: 8241 */         this.progress_ = 0.0F;
/*  7836: 8242 */         this.bitField0_ &= 0xFFFFFFFB;
/*  7837: 8243 */         this.startTime_ = 0L;
/*  7838: 8244 */         this.bitField0_ &= 0xFFFFFFF7;
/*  7839: 8245 */         this.finishTime_ = 0L;
/*  7840: 8246 */         this.bitField0_ &= 0xFFFFFFEF;
/*  7841: 8247 */         if (this.countersBuilder_ == null) {
/*  7842: 8248 */           this.counters_ = MRProtos.CountersProto.getDefaultInstance();
/*  7843:      */         } else {
/*  7844: 8250 */           this.countersBuilder_.clear();
/*  7845:      */         }
/*  7846: 8252 */         this.bitField0_ &= 0xFFFFFFDF;
/*  7847: 8253 */         this.diagnosticInfo_ = "";
/*  7848: 8254 */         this.bitField0_ &= 0xFFFFFFBF;
/*  7849: 8255 */         this.stateString_ = "";
/*  7850: 8256 */         this.bitField0_ &= 0xFFFFFF7F;
/*  7851: 8257 */         this.phase_ = MRProtos.PhaseProto.P_STARTING;
/*  7852: 8258 */         this.bitField0_ &= 0xFFFFFEFF;
/*  7853: 8259 */         this.shuffleFinishTime_ = 0L;
/*  7854: 8260 */         this.bitField0_ &= 0xFFFFFDFF;
/*  7855: 8261 */         this.sortFinishTime_ = 0L;
/*  7856: 8262 */         this.bitField0_ &= 0xFFFFFBFF;
/*  7857: 8263 */         this.nodeManagerHost_ = "";
/*  7858: 8264 */         this.bitField0_ &= 0xFFFFF7FF;
/*  7859: 8265 */         this.nodeManagerPort_ = 0;
/*  7860: 8266 */         this.bitField0_ &= 0xFFFFEFFF;
/*  7861: 8267 */         this.nodeManagerHttpPort_ = 0;
/*  7862: 8268 */         this.bitField0_ &= 0xFFFFDFFF;
/*  7863: 8269 */         if (this.containerIdBuilder_ == null) {
/*  7864: 8270 */           this.containerId_ = YarnProtos.ContainerIdProto.getDefaultInstance();
/*  7865:      */         } else {
/*  7866: 8272 */           this.containerIdBuilder_.clear();
/*  7867:      */         }
/*  7868: 8274 */         this.bitField0_ &= 0xFFFFBFFF;
/*  7869: 8275 */         return this;
/*  7870:      */       }
/*  7871:      */       
/*  7872:      */       public Builder clone()
/*  7873:      */       {
/*  7874: 8279 */         return create().mergeFrom(buildPartial());
/*  7875:      */       }
/*  7876:      */       
/*  7877:      */       public Descriptors.Descriptor getDescriptorForType()
/*  7878:      */       {
/*  7879: 8284 */         return MRProtos.internal_static_hadoop_mapreduce_TaskAttemptReportProto_descriptor;
/*  7880:      */       }
/*  7881:      */       
/*  7882:      */       public MRProtos.TaskAttemptReportProto getDefaultInstanceForType()
/*  7883:      */       {
/*  7884: 8288 */         return MRProtos.TaskAttemptReportProto.getDefaultInstance();
/*  7885:      */       }
/*  7886:      */       
/*  7887:      */       public MRProtos.TaskAttemptReportProto build()
/*  7888:      */       {
/*  7889: 8292 */         MRProtos.TaskAttemptReportProto result = buildPartial();
/*  7890: 8293 */         if (!result.isInitialized()) {
/*  7891: 8294 */           throw newUninitializedMessageException(result);
/*  7892:      */         }
/*  7893: 8296 */         return result;
/*  7894:      */       }
/*  7895:      */       
/*  7896:      */       public MRProtos.TaskAttemptReportProto buildPartial()
/*  7897:      */       {
/*  7898: 8300 */         MRProtos.TaskAttemptReportProto result = new MRProtos.TaskAttemptReportProto(this, null);
/*  7899: 8301 */         int from_bitField0_ = this.bitField0_;
/*  7900: 8302 */         int to_bitField0_ = 0;
/*  7901: 8303 */         if ((from_bitField0_ & 0x1) == 1) {
/*  7902: 8304 */           to_bitField0_ |= 0x1;
/*  7903:      */         }
/*  7904: 8306 */         if (this.taskAttemptIdBuilder_ == null) {
/*  7905: 8307 */           result.taskAttemptId_ = this.taskAttemptId_;
/*  7906:      */         } else {
/*  7907: 8309 */           result.taskAttemptId_ = ((MRProtos.TaskAttemptIdProto)this.taskAttemptIdBuilder_.build());
/*  7908:      */         }
/*  7909: 8311 */         if ((from_bitField0_ & 0x2) == 2) {
/*  7910: 8312 */           to_bitField0_ |= 0x2;
/*  7911:      */         }
/*  7912: 8314 */         result.taskAttemptState_ = this.taskAttemptState_;
/*  7913: 8315 */         if ((from_bitField0_ & 0x4) == 4) {
/*  7914: 8316 */           to_bitField0_ |= 0x4;
/*  7915:      */         }
/*  7916: 8318 */         result.progress_ = this.progress_;
/*  7917: 8319 */         if ((from_bitField0_ & 0x8) == 8) {
/*  7918: 8320 */           to_bitField0_ |= 0x8;
/*  7919:      */         }
/*  7920: 8322 */         result.startTime_ = this.startTime_;
/*  7921: 8323 */         if ((from_bitField0_ & 0x10) == 16) {
/*  7922: 8324 */           to_bitField0_ |= 0x10;
/*  7923:      */         }
/*  7924: 8326 */         result.finishTime_ = this.finishTime_;
/*  7925: 8327 */         if ((from_bitField0_ & 0x20) == 32) {
/*  7926: 8328 */           to_bitField0_ |= 0x20;
/*  7927:      */         }
/*  7928: 8330 */         if (this.countersBuilder_ == null) {
/*  7929: 8331 */           result.counters_ = this.counters_;
/*  7930:      */         } else {
/*  7931: 8333 */           result.counters_ = ((MRProtos.CountersProto)this.countersBuilder_.build());
/*  7932:      */         }
/*  7933: 8335 */         if ((from_bitField0_ & 0x40) == 64) {
/*  7934: 8336 */           to_bitField0_ |= 0x40;
/*  7935:      */         }
/*  7936: 8338 */         result.diagnosticInfo_ = this.diagnosticInfo_;
/*  7937: 8339 */         if ((from_bitField0_ & 0x80) == 128) {
/*  7938: 8340 */           to_bitField0_ |= 0x80;
/*  7939:      */         }
/*  7940: 8342 */         result.stateString_ = this.stateString_;
/*  7941: 8343 */         if ((from_bitField0_ & 0x100) == 256) {
/*  7942: 8344 */           to_bitField0_ |= 0x100;
/*  7943:      */         }
/*  7944: 8346 */         result.phase_ = this.phase_;
/*  7945: 8347 */         if ((from_bitField0_ & 0x200) == 512) {
/*  7946: 8348 */           to_bitField0_ |= 0x200;
/*  7947:      */         }
/*  7948: 8350 */         result.shuffleFinishTime_ = this.shuffleFinishTime_;
/*  7949: 8351 */         if ((from_bitField0_ & 0x400) == 1024) {
/*  7950: 8352 */           to_bitField0_ |= 0x400;
/*  7951:      */         }
/*  7952: 8354 */         result.sortFinishTime_ = this.sortFinishTime_;
/*  7953: 8355 */         if ((from_bitField0_ & 0x800) == 2048) {
/*  7954: 8356 */           to_bitField0_ |= 0x800;
/*  7955:      */         }
/*  7956: 8358 */         result.nodeManagerHost_ = this.nodeManagerHost_;
/*  7957: 8359 */         if ((from_bitField0_ & 0x1000) == 4096) {
/*  7958: 8360 */           to_bitField0_ |= 0x1000;
/*  7959:      */         }
/*  7960: 8362 */         result.nodeManagerPort_ = this.nodeManagerPort_;
/*  7961: 8363 */         if ((from_bitField0_ & 0x2000) == 8192) {
/*  7962: 8364 */           to_bitField0_ |= 0x2000;
/*  7963:      */         }
/*  7964: 8366 */         result.nodeManagerHttpPort_ = this.nodeManagerHttpPort_;
/*  7965: 8367 */         if ((from_bitField0_ & 0x4000) == 16384) {
/*  7966: 8368 */           to_bitField0_ |= 0x4000;
/*  7967:      */         }
/*  7968: 8370 */         if (this.containerIdBuilder_ == null) {
/*  7969: 8371 */           result.containerId_ = this.containerId_;
/*  7970:      */         } else {
/*  7971: 8373 */           result.containerId_ = ((YarnProtos.ContainerIdProto)this.containerIdBuilder_.build());
/*  7972:      */         }
/*  7973: 8375 */         result.bitField0_ = to_bitField0_;
/*  7974: 8376 */         onBuilt();
/*  7975: 8377 */         return result;
/*  7976:      */       }
/*  7977:      */       
/*  7978:      */       public Builder mergeFrom(Message other)
/*  7979:      */       {
/*  7980: 8381 */         if ((other instanceof MRProtos.TaskAttemptReportProto)) {
/*  7981: 8382 */           return mergeFrom((MRProtos.TaskAttemptReportProto)other);
/*  7982:      */         }
/*  7983: 8384 */         super.mergeFrom(other);
/*  7984: 8385 */         return this;
/*  7985:      */       }
/*  7986:      */       
/*  7987:      */       public Builder mergeFrom(MRProtos.TaskAttemptReportProto other)
/*  7988:      */       {
/*  7989: 8390 */         if (other == MRProtos.TaskAttemptReportProto.getDefaultInstance()) {
/*  7990: 8390 */           return this;
/*  7991:      */         }
/*  7992: 8391 */         if (other.hasTaskAttemptId()) {
/*  7993: 8392 */           mergeTaskAttemptId(other.getTaskAttemptId());
/*  7994:      */         }
/*  7995: 8394 */         if (other.hasTaskAttemptState()) {
/*  7996: 8395 */           setTaskAttemptState(other.getTaskAttemptState());
/*  7997:      */         }
/*  7998: 8397 */         if (other.hasProgress()) {
/*  7999: 8398 */           setProgress(other.getProgress());
/*  8000:      */         }
/*  8001: 8400 */         if (other.hasStartTime()) {
/*  8002: 8401 */           setStartTime(other.getStartTime());
/*  8003:      */         }
/*  8004: 8403 */         if (other.hasFinishTime()) {
/*  8005: 8404 */           setFinishTime(other.getFinishTime());
/*  8006:      */         }
/*  8007: 8406 */         if (other.hasCounters()) {
/*  8008: 8407 */           mergeCounters(other.getCounters());
/*  8009:      */         }
/*  8010: 8409 */         if (other.hasDiagnosticInfo())
/*  8011:      */         {
/*  8012: 8410 */           this.bitField0_ |= 0x40;
/*  8013: 8411 */           this.diagnosticInfo_ = other.diagnosticInfo_;
/*  8014: 8412 */           onChanged();
/*  8015:      */         }
/*  8016: 8414 */         if (other.hasStateString())
/*  8017:      */         {
/*  8018: 8415 */           this.bitField0_ |= 0x80;
/*  8019: 8416 */           this.stateString_ = other.stateString_;
/*  8020: 8417 */           onChanged();
/*  8021:      */         }
/*  8022: 8419 */         if (other.hasPhase()) {
/*  8023: 8420 */           setPhase(other.getPhase());
/*  8024:      */         }
/*  8025: 8422 */         if (other.hasShuffleFinishTime()) {
/*  8026: 8423 */           setShuffleFinishTime(other.getShuffleFinishTime());
/*  8027:      */         }
/*  8028: 8425 */         if (other.hasSortFinishTime()) {
/*  8029: 8426 */           setSortFinishTime(other.getSortFinishTime());
/*  8030:      */         }
/*  8031: 8428 */         if (other.hasNodeManagerHost())
/*  8032:      */         {
/*  8033: 8429 */           this.bitField0_ |= 0x800;
/*  8034: 8430 */           this.nodeManagerHost_ = other.nodeManagerHost_;
/*  8035: 8431 */           onChanged();
/*  8036:      */         }
/*  8037: 8433 */         if (other.hasNodeManagerPort()) {
/*  8038: 8434 */           setNodeManagerPort(other.getNodeManagerPort());
/*  8039:      */         }
/*  8040: 8436 */         if (other.hasNodeManagerHttpPort()) {
/*  8041: 8437 */           setNodeManagerHttpPort(other.getNodeManagerHttpPort());
/*  8042:      */         }
/*  8043: 8439 */         if (other.hasContainerId()) {
/*  8044: 8440 */           mergeContainerId(other.getContainerId());
/*  8045:      */         }
/*  8046: 8442 */         mergeUnknownFields(other.getUnknownFields());
/*  8047: 8443 */         return this;
/*  8048:      */       }
/*  8049:      */       
/*  8050:      */       public final boolean isInitialized()
/*  8051:      */       {
/*  8052: 8447 */         return true;
/*  8053:      */       }
/*  8054:      */       
/*  8055:      */       public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry)
/*  8056:      */         throws IOException
/*  8057:      */       {
/*  8058: 8454 */         MRProtos.TaskAttemptReportProto parsedMessage = null;
/*  8059:      */         try
/*  8060:      */         {
/*  8061: 8456 */           parsedMessage = (MRProtos.TaskAttemptReportProto)MRProtos.TaskAttemptReportProto.PARSER.parsePartialFrom(input, extensionRegistry);
/*  8062:      */         }
/*  8063:      */         catch (InvalidProtocolBufferException e)
/*  8064:      */         {
/*  8065: 8458 */           parsedMessage = (MRProtos.TaskAttemptReportProto)e.getUnfinishedMessage();
/*  8066: 8459 */           throw e;
/*  8067:      */         }
/*  8068:      */         finally
/*  8069:      */         {
/*  8070: 8461 */           if (parsedMessage != null) {
/*  8071: 8462 */             mergeFrom(parsedMessage);
/*  8072:      */           }
/*  8073:      */         }
/*  8074: 8465 */         return this;
/*  8075:      */       }
/*  8076:      */       
/*  8077: 8470 */       private MRProtos.TaskAttemptIdProto taskAttemptId_ = MRProtos.TaskAttemptIdProto.getDefaultInstance();
/*  8078:      */       private SingleFieldBuilder<MRProtos.TaskAttemptIdProto, MRProtos.TaskAttemptIdProto.Builder, MRProtos.TaskAttemptIdProtoOrBuilder> taskAttemptIdBuilder_;
/*  8079:      */       
/*  8080:      */       public boolean hasTaskAttemptId()
/*  8081:      */       {
/*  8082: 8477 */         return (this.bitField0_ & 0x1) == 1;
/*  8083:      */       }
/*  8084:      */       
/*  8085:      */       public MRProtos.TaskAttemptIdProto getTaskAttemptId()
/*  8086:      */       {
/*  8087: 8483 */         if (this.taskAttemptIdBuilder_ == null) {
/*  8088: 8484 */           return this.taskAttemptId_;
/*  8089:      */         }
/*  8090: 8486 */         return (MRProtos.TaskAttemptIdProto)this.taskAttemptIdBuilder_.getMessage();
/*  8091:      */       }
/*  8092:      */       
/*  8093:      */       public Builder setTaskAttemptId(MRProtos.TaskAttemptIdProto value)
/*  8094:      */       {
/*  8095: 8493 */         if (this.taskAttemptIdBuilder_ == null)
/*  8096:      */         {
/*  8097: 8494 */           if (value == null) {
/*  8098: 8495 */             throw new NullPointerException();
/*  8099:      */           }
/*  8100: 8497 */           this.taskAttemptId_ = value;
/*  8101: 8498 */           onChanged();
/*  8102:      */         }
/*  8103:      */         else
/*  8104:      */         {
/*  8105: 8500 */           this.taskAttemptIdBuilder_.setMessage(value);
/*  8106:      */         }
/*  8107: 8502 */         this.bitField0_ |= 0x1;
/*  8108: 8503 */         return this;
/*  8109:      */       }
/*  8110:      */       
/*  8111:      */       public Builder setTaskAttemptId(MRProtos.TaskAttemptIdProto.Builder builderForValue)
/*  8112:      */       {
/*  8113: 8510 */         if (this.taskAttemptIdBuilder_ == null)
/*  8114:      */         {
/*  8115: 8511 */           this.taskAttemptId_ = builderForValue.build();
/*  8116: 8512 */           onChanged();
/*  8117:      */         }
/*  8118:      */         else
/*  8119:      */         {
/*  8120: 8514 */           this.taskAttemptIdBuilder_.setMessage(builderForValue.build());
/*  8121:      */         }
/*  8122: 8516 */         this.bitField0_ |= 0x1;
/*  8123: 8517 */         return this;
/*  8124:      */       }
/*  8125:      */       
/*  8126:      */       public Builder mergeTaskAttemptId(MRProtos.TaskAttemptIdProto value)
/*  8127:      */       {
/*  8128: 8523 */         if (this.taskAttemptIdBuilder_ == null)
/*  8129:      */         {
/*  8130: 8524 */           if (((this.bitField0_ & 0x1) == 1) && (this.taskAttemptId_ != MRProtos.TaskAttemptIdProto.getDefaultInstance())) {
/*  8131: 8526 */             this.taskAttemptId_ = MRProtos.TaskAttemptIdProto.newBuilder(this.taskAttemptId_).mergeFrom(value).buildPartial();
/*  8132:      */           } else {
/*  8133: 8529 */             this.taskAttemptId_ = value;
/*  8134:      */           }
/*  8135: 8531 */           onChanged();
/*  8136:      */         }
/*  8137:      */         else
/*  8138:      */         {
/*  8139: 8533 */           this.taskAttemptIdBuilder_.mergeFrom(value);
/*  8140:      */         }
/*  8141: 8535 */         this.bitField0_ |= 0x1;
/*  8142: 8536 */         return this;
/*  8143:      */       }
/*  8144:      */       
/*  8145:      */       public Builder clearTaskAttemptId()
/*  8146:      */       {
/*  8147: 8542 */         if (this.taskAttemptIdBuilder_ == null)
/*  8148:      */         {
/*  8149: 8543 */           this.taskAttemptId_ = MRProtos.TaskAttemptIdProto.getDefaultInstance();
/*  8150: 8544 */           onChanged();
/*  8151:      */         }
/*  8152:      */         else
/*  8153:      */         {
/*  8154: 8546 */           this.taskAttemptIdBuilder_.clear();
/*  8155:      */         }
/*  8156: 8548 */         this.bitField0_ &= 0xFFFFFFFE;
/*  8157: 8549 */         return this;
/*  8158:      */       }
/*  8159:      */       
/*  8160:      */       public MRProtos.TaskAttemptIdProto.Builder getTaskAttemptIdBuilder()
/*  8161:      */       {
/*  8162: 8555 */         this.bitField0_ |= 0x1;
/*  8163: 8556 */         onChanged();
/*  8164: 8557 */         return (MRProtos.TaskAttemptIdProto.Builder)getTaskAttemptIdFieldBuilder().getBuilder();
/*  8165:      */       }
/*  8166:      */       
/*  8167:      */       public MRProtos.TaskAttemptIdProtoOrBuilder getTaskAttemptIdOrBuilder()
/*  8168:      */       {
/*  8169: 8563 */         if (this.taskAttemptIdBuilder_ != null) {
/*  8170: 8564 */           return (MRProtos.TaskAttemptIdProtoOrBuilder)this.taskAttemptIdBuilder_.getMessageOrBuilder();
/*  8171:      */         }
/*  8172: 8566 */         return this.taskAttemptId_;
/*  8173:      */       }
/*  8174:      */       
/*  8175:      */       private SingleFieldBuilder<MRProtos.TaskAttemptIdProto, MRProtos.TaskAttemptIdProto.Builder, MRProtos.TaskAttemptIdProtoOrBuilder> getTaskAttemptIdFieldBuilder()
/*  8176:      */       {
/*  8177: 8575 */         if (this.taskAttemptIdBuilder_ == null)
/*  8178:      */         {
/*  8179: 8576 */           this.taskAttemptIdBuilder_ = new SingleFieldBuilder(this.taskAttemptId_, getParentForChildren(), isClean());
/*  8180:      */           
/*  8181:      */ 
/*  8182:      */ 
/*  8183:      */ 
/*  8184: 8581 */           this.taskAttemptId_ = null;
/*  8185:      */         }
/*  8186: 8583 */         return this.taskAttemptIdBuilder_;
/*  8187:      */       }
/*  8188:      */       
/*  8189: 8587 */       private MRProtos.TaskAttemptStateProto taskAttemptState_ = MRProtos.TaskAttemptStateProto.TA_NEW;
/*  8190:      */       private float progress_;
/*  8191:      */       private long startTime_;
/*  8192:      */       private long finishTime_;
/*  8193:      */       
/*  8194:      */       public boolean hasTaskAttemptState()
/*  8195:      */       {
/*  8196: 8592 */         return (this.bitField0_ & 0x2) == 2;
/*  8197:      */       }
/*  8198:      */       
/*  8199:      */       public MRProtos.TaskAttemptStateProto getTaskAttemptState()
/*  8200:      */       {
/*  8201: 8598 */         return this.taskAttemptState_;
/*  8202:      */       }
/*  8203:      */       
/*  8204:      */       public Builder setTaskAttemptState(MRProtos.TaskAttemptStateProto value)
/*  8205:      */       {
/*  8206: 8604 */         if (value == null) {
/*  8207: 8605 */           throw new NullPointerException();
/*  8208:      */         }
/*  8209: 8607 */         this.bitField0_ |= 0x2;
/*  8210: 8608 */         this.taskAttemptState_ = value;
/*  8211: 8609 */         onChanged();
/*  8212: 8610 */         return this;
/*  8213:      */       }
/*  8214:      */       
/*  8215:      */       public Builder clearTaskAttemptState()
/*  8216:      */       {
/*  8217: 8616 */         this.bitField0_ &= 0xFFFFFFFD;
/*  8218: 8617 */         this.taskAttemptState_ = MRProtos.TaskAttemptStateProto.TA_NEW;
/*  8219: 8618 */         onChanged();
/*  8220: 8619 */         return this;
/*  8221:      */       }
/*  8222:      */       
/*  8223:      */       public boolean hasProgress()
/*  8224:      */       {
/*  8225: 8628 */         return (this.bitField0_ & 0x4) == 4;
/*  8226:      */       }
/*  8227:      */       
/*  8228:      */       public float getProgress()
/*  8229:      */       {
/*  8230: 8634 */         return this.progress_;
/*  8231:      */       }
/*  8232:      */       
/*  8233:      */       public Builder setProgress(float value)
/*  8234:      */       {
/*  8235: 8640 */         this.bitField0_ |= 0x4;
/*  8236: 8641 */         this.progress_ = value;
/*  8237: 8642 */         onChanged();
/*  8238: 8643 */         return this;
/*  8239:      */       }
/*  8240:      */       
/*  8241:      */       public Builder clearProgress()
/*  8242:      */       {
/*  8243: 8649 */         this.bitField0_ &= 0xFFFFFFFB;
/*  8244: 8650 */         this.progress_ = 0.0F;
/*  8245: 8651 */         onChanged();
/*  8246: 8652 */         return this;
/*  8247:      */       }
/*  8248:      */       
/*  8249:      */       public boolean hasStartTime()
/*  8250:      */       {
/*  8251: 8661 */         return (this.bitField0_ & 0x8) == 8;
/*  8252:      */       }
/*  8253:      */       
/*  8254:      */       public long getStartTime()
/*  8255:      */       {
/*  8256: 8667 */         return this.startTime_;
/*  8257:      */       }
/*  8258:      */       
/*  8259:      */       public Builder setStartTime(long value)
/*  8260:      */       {
/*  8261: 8673 */         this.bitField0_ |= 0x8;
/*  8262: 8674 */         this.startTime_ = value;
/*  8263: 8675 */         onChanged();
/*  8264: 8676 */         return this;
/*  8265:      */       }
/*  8266:      */       
/*  8267:      */       public Builder clearStartTime()
/*  8268:      */       {
/*  8269: 8682 */         this.bitField0_ &= 0xFFFFFFF7;
/*  8270: 8683 */         this.startTime_ = 0L;
/*  8271: 8684 */         onChanged();
/*  8272: 8685 */         return this;
/*  8273:      */       }
/*  8274:      */       
/*  8275:      */       public boolean hasFinishTime()
/*  8276:      */       {
/*  8277: 8694 */         return (this.bitField0_ & 0x10) == 16;
/*  8278:      */       }
/*  8279:      */       
/*  8280:      */       public long getFinishTime()
/*  8281:      */       {
/*  8282: 8700 */         return this.finishTime_;
/*  8283:      */       }
/*  8284:      */       
/*  8285:      */       public Builder setFinishTime(long value)
/*  8286:      */       {
/*  8287: 8706 */         this.bitField0_ |= 0x10;
/*  8288: 8707 */         this.finishTime_ = value;
/*  8289: 8708 */         onChanged();
/*  8290: 8709 */         return this;
/*  8291:      */       }
/*  8292:      */       
/*  8293:      */       public Builder clearFinishTime()
/*  8294:      */       {
/*  8295: 8715 */         this.bitField0_ &= 0xFFFFFFEF;
/*  8296: 8716 */         this.finishTime_ = 0L;
/*  8297: 8717 */         onChanged();
/*  8298: 8718 */         return this;
/*  8299:      */       }
/*  8300:      */       
/*  8301: 8722 */       private MRProtos.CountersProto counters_ = MRProtos.CountersProto.getDefaultInstance();
/*  8302:      */       private SingleFieldBuilder<MRProtos.CountersProto, MRProtos.CountersProto.Builder, MRProtos.CountersProtoOrBuilder> countersBuilder_;
/*  8303:      */       
/*  8304:      */       public boolean hasCounters()
/*  8305:      */       {
/*  8306: 8729 */         return (this.bitField0_ & 0x20) == 32;
/*  8307:      */       }
/*  8308:      */       
/*  8309:      */       public MRProtos.CountersProto getCounters()
/*  8310:      */       {
/*  8311: 8735 */         if (this.countersBuilder_ == null) {
/*  8312: 8736 */           return this.counters_;
/*  8313:      */         }
/*  8314: 8738 */         return (MRProtos.CountersProto)this.countersBuilder_.getMessage();
/*  8315:      */       }
/*  8316:      */       
/*  8317:      */       public Builder setCounters(MRProtos.CountersProto value)
/*  8318:      */       {
/*  8319: 8745 */         if (this.countersBuilder_ == null)
/*  8320:      */         {
/*  8321: 8746 */           if (value == null) {
/*  8322: 8747 */             throw new NullPointerException();
/*  8323:      */           }
/*  8324: 8749 */           this.counters_ = value;
/*  8325: 8750 */           onChanged();
/*  8326:      */         }
/*  8327:      */         else
/*  8328:      */         {
/*  8329: 8752 */           this.countersBuilder_.setMessage(value);
/*  8330:      */         }
/*  8331: 8754 */         this.bitField0_ |= 0x20;
/*  8332: 8755 */         return this;
/*  8333:      */       }
/*  8334:      */       
/*  8335:      */       public Builder setCounters(MRProtos.CountersProto.Builder builderForValue)
/*  8336:      */       {
/*  8337: 8762 */         if (this.countersBuilder_ == null)
/*  8338:      */         {
/*  8339: 8763 */           this.counters_ = builderForValue.build();
/*  8340: 8764 */           onChanged();
/*  8341:      */         }
/*  8342:      */         else
/*  8343:      */         {
/*  8344: 8766 */           this.countersBuilder_.setMessage(builderForValue.build());
/*  8345:      */         }
/*  8346: 8768 */         this.bitField0_ |= 0x20;
/*  8347: 8769 */         return this;
/*  8348:      */       }
/*  8349:      */       
/*  8350:      */       public Builder mergeCounters(MRProtos.CountersProto value)
/*  8351:      */       {
/*  8352: 8775 */         if (this.countersBuilder_ == null)
/*  8353:      */         {
/*  8354: 8776 */           if (((this.bitField0_ & 0x20) == 32) && (this.counters_ != MRProtos.CountersProto.getDefaultInstance())) {
/*  8355: 8778 */             this.counters_ = MRProtos.CountersProto.newBuilder(this.counters_).mergeFrom(value).buildPartial();
/*  8356:      */           } else {
/*  8357: 8781 */             this.counters_ = value;
/*  8358:      */           }
/*  8359: 8783 */           onChanged();
/*  8360:      */         }
/*  8361:      */         else
/*  8362:      */         {
/*  8363: 8785 */           this.countersBuilder_.mergeFrom(value);
/*  8364:      */         }
/*  8365: 8787 */         this.bitField0_ |= 0x20;
/*  8366: 8788 */         return this;
/*  8367:      */       }
/*  8368:      */       
/*  8369:      */       public Builder clearCounters()
/*  8370:      */       {
/*  8371: 8794 */         if (this.countersBuilder_ == null)
/*  8372:      */         {
/*  8373: 8795 */           this.counters_ = MRProtos.CountersProto.getDefaultInstance();
/*  8374: 8796 */           onChanged();
/*  8375:      */         }
/*  8376:      */         else
/*  8377:      */         {
/*  8378: 8798 */           this.countersBuilder_.clear();
/*  8379:      */         }
/*  8380: 8800 */         this.bitField0_ &= 0xFFFFFFDF;
/*  8381: 8801 */         return this;
/*  8382:      */       }
/*  8383:      */       
/*  8384:      */       public MRProtos.CountersProto.Builder getCountersBuilder()
/*  8385:      */       {
/*  8386: 8807 */         this.bitField0_ |= 0x20;
/*  8387: 8808 */         onChanged();
/*  8388: 8809 */         return (MRProtos.CountersProto.Builder)getCountersFieldBuilder().getBuilder();
/*  8389:      */       }
/*  8390:      */       
/*  8391:      */       public MRProtos.CountersProtoOrBuilder getCountersOrBuilder()
/*  8392:      */       {
/*  8393: 8815 */         if (this.countersBuilder_ != null) {
/*  8394: 8816 */           return (MRProtos.CountersProtoOrBuilder)this.countersBuilder_.getMessageOrBuilder();
/*  8395:      */         }
/*  8396: 8818 */         return this.counters_;
/*  8397:      */       }
/*  8398:      */       
/*  8399:      */       private SingleFieldBuilder<MRProtos.CountersProto, MRProtos.CountersProto.Builder, MRProtos.CountersProtoOrBuilder> getCountersFieldBuilder()
/*  8400:      */       {
/*  8401: 8827 */         if (this.countersBuilder_ == null)
/*  8402:      */         {
/*  8403: 8828 */           this.countersBuilder_ = new SingleFieldBuilder(this.counters_, getParentForChildren(), isClean());
/*  8404:      */           
/*  8405:      */ 
/*  8406:      */ 
/*  8407:      */ 
/*  8408: 8833 */           this.counters_ = null;
/*  8409:      */         }
/*  8410: 8835 */         return this.countersBuilder_;
/*  8411:      */       }
/*  8412:      */       
/*  8413: 8839 */       private Object diagnosticInfo_ = "";
/*  8414:      */       
/*  8415:      */       public boolean hasDiagnosticInfo()
/*  8416:      */       {
/*  8417: 8844 */         return (this.bitField0_ & 0x40) == 64;
/*  8418:      */       }
/*  8419:      */       
/*  8420:      */       public String getDiagnosticInfo()
/*  8421:      */       {
/*  8422: 8850 */         Object ref = this.diagnosticInfo_;
/*  8423: 8851 */         if (!(ref instanceof String))
/*  8424:      */         {
/*  8425: 8852 */           String s = ((ByteString)ref).toStringUtf8();
/*  8426:      */           
/*  8427: 8854 */           this.diagnosticInfo_ = s;
/*  8428: 8855 */           return s;
/*  8429:      */         }
/*  8430: 8857 */         return (String)ref;
/*  8431:      */       }
/*  8432:      */       
/*  8433:      */       public ByteString getDiagnosticInfoBytes()
/*  8434:      */       {
/*  8435: 8865 */         Object ref = this.diagnosticInfo_;
/*  8436: 8866 */         if ((ref instanceof String))
/*  8437:      */         {
/*  8438: 8867 */           ByteString b = ByteString.copyFromUtf8((String)ref);
/*  8439:      */           
/*  8440:      */ 
/*  8441: 8870 */           this.diagnosticInfo_ = b;
/*  8442: 8871 */           return b;
/*  8443:      */         }
/*  8444: 8873 */         return (ByteString)ref;
/*  8445:      */       }
/*  8446:      */       
/*  8447:      */       public Builder setDiagnosticInfo(String value)
/*  8448:      */       {
/*  8449: 8881 */         if (value == null) {
/*  8450: 8882 */           throw new NullPointerException();
/*  8451:      */         }
/*  8452: 8884 */         this.bitField0_ |= 0x40;
/*  8453: 8885 */         this.diagnosticInfo_ = value;
/*  8454: 8886 */         onChanged();
/*  8455: 8887 */         return this;
/*  8456:      */       }
/*  8457:      */       
/*  8458:      */       public Builder clearDiagnosticInfo()
/*  8459:      */       {
/*  8460: 8893 */         this.bitField0_ &= 0xFFFFFFBF;
/*  8461: 8894 */         this.diagnosticInfo_ = MRProtos.TaskAttemptReportProto.getDefaultInstance().getDiagnosticInfo();
/*  8462: 8895 */         onChanged();
/*  8463: 8896 */         return this;
/*  8464:      */       }
/*  8465:      */       
/*  8466:      */       public Builder setDiagnosticInfoBytes(ByteString value)
/*  8467:      */       {
/*  8468: 8903 */         if (value == null) {
/*  8469: 8904 */           throw new NullPointerException();
/*  8470:      */         }
/*  8471: 8906 */         this.bitField0_ |= 0x40;
/*  8472: 8907 */         this.diagnosticInfo_ = value;
/*  8473: 8908 */         onChanged();
/*  8474: 8909 */         return this;
/*  8475:      */       }
/*  8476:      */       
/*  8477: 8913 */       private Object stateString_ = "";
/*  8478:      */       
/*  8479:      */       public boolean hasStateString()
/*  8480:      */       {
/*  8481: 8918 */         return (this.bitField0_ & 0x80) == 128;
/*  8482:      */       }
/*  8483:      */       
/*  8484:      */       public String getStateString()
/*  8485:      */       {
/*  8486: 8924 */         Object ref = this.stateString_;
/*  8487: 8925 */         if (!(ref instanceof String))
/*  8488:      */         {
/*  8489: 8926 */           String s = ((ByteString)ref).toStringUtf8();
/*  8490:      */           
/*  8491: 8928 */           this.stateString_ = s;
/*  8492: 8929 */           return s;
/*  8493:      */         }
/*  8494: 8931 */         return (String)ref;
/*  8495:      */       }
/*  8496:      */       
/*  8497:      */       public ByteString getStateStringBytes()
/*  8498:      */       {
/*  8499: 8939 */         Object ref = this.stateString_;
/*  8500: 8940 */         if ((ref instanceof String))
/*  8501:      */         {
/*  8502: 8941 */           ByteString b = ByteString.copyFromUtf8((String)ref);
/*  8503:      */           
/*  8504:      */ 
/*  8505: 8944 */           this.stateString_ = b;
/*  8506: 8945 */           return b;
/*  8507:      */         }
/*  8508: 8947 */         return (ByteString)ref;
/*  8509:      */       }
/*  8510:      */       
/*  8511:      */       public Builder setStateString(String value)
/*  8512:      */       {
/*  8513: 8955 */         if (value == null) {
/*  8514: 8956 */           throw new NullPointerException();
/*  8515:      */         }
/*  8516: 8958 */         this.bitField0_ |= 0x80;
/*  8517: 8959 */         this.stateString_ = value;
/*  8518: 8960 */         onChanged();
/*  8519: 8961 */         return this;
/*  8520:      */       }
/*  8521:      */       
/*  8522:      */       public Builder clearStateString()
/*  8523:      */       {
/*  8524: 8967 */         this.bitField0_ &= 0xFFFFFF7F;
/*  8525: 8968 */         this.stateString_ = MRProtos.TaskAttemptReportProto.getDefaultInstance().getStateString();
/*  8526: 8969 */         onChanged();
/*  8527: 8970 */         return this;
/*  8528:      */       }
/*  8529:      */       
/*  8530:      */       public Builder setStateStringBytes(ByteString value)
/*  8531:      */       {
/*  8532: 8977 */         if (value == null) {
/*  8533: 8978 */           throw new NullPointerException();
/*  8534:      */         }
/*  8535: 8980 */         this.bitField0_ |= 0x80;
/*  8536: 8981 */         this.stateString_ = value;
/*  8537: 8982 */         onChanged();
/*  8538: 8983 */         return this;
/*  8539:      */       }
/*  8540:      */       
/*  8541: 8987 */       private MRProtos.PhaseProto phase_ = MRProtos.PhaseProto.P_STARTING;
/*  8542:      */       private long shuffleFinishTime_;
/*  8543:      */       private long sortFinishTime_;
/*  8544:      */       
/*  8545:      */       public boolean hasPhase()
/*  8546:      */       {
/*  8547: 8992 */         return (this.bitField0_ & 0x100) == 256;
/*  8548:      */       }
/*  8549:      */       
/*  8550:      */       public MRProtos.PhaseProto getPhase()
/*  8551:      */       {
/*  8552: 8998 */         return this.phase_;
/*  8553:      */       }
/*  8554:      */       
/*  8555:      */       public Builder setPhase(MRProtos.PhaseProto value)
/*  8556:      */       {
/*  8557: 9004 */         if (value == null) {
/*  8558: 9005 */           throw new NullPointerException();
/*  8559:      */         }
/*  8560: 9007 */         this.bitField0_ |= 0x100;
/*  8561: 9008 */         this.phase_ = value;
/*  8562: 9009 */         onChanged();
/*  8563: 9010 */         return this;
/*  8564:      */       }
/*  8565:      */       
/*  8566:      */       public Builder clearPhase()
/*  8567:      */       {
/*  8568: 9016 */         this.bitField0_ &= 0xFFFFFEFF;
/*  8569: 9017 */         this.phase_ = MRProtos.PhaseProto.P_STARTING;
/*  8570: 9018 */         onChanged();
/*  8571: 9019 */         return this;
/*  8572:      */       }
/*  8573:      */       
/*  8574:      */       public boolean hasShuffleFinishTime()
/*  8575:      */       {
/*  8576: 9028 */         return (this.bitField0_ & 0x200) == 512;
/*  8577:      */       }
/*  8578:      */       
/*  8579:      */       public long getShuffleFinishTime()
/*  8580:      */       {
/*  8581: 9034 */         return this.shuffleFinishTime_;
/*  8582:      */       }
/*  8583:      */       
/*  8584:      */       public Builder setShuffleFinishTime(long value)
/*  8585:      */       {
/*  8586: 9040 */         this.bitField0_ |= 0x200;
/*  8587: 9041 */         this.shuffleFinishTime_ = value;
/*  8588: 9042 */         onChanged();
/*  8589: 9043 */         return this;
/*  8590:      */       }
/*  8591:      */       
/*  8592:      */       public Builder clearShuffleFinishTime()
/*  8593:      */       {
/*  8594: 9049 */         this.bitField0_ &= 0xFFFFFDFF;
/*  8595: 9050 */         this.shuffleFinishTime_ = 0L;
/*  8596: 9051 */         onChanged();
/*  8597: 9052 */         return this;
/*  8598:      */       }
/*  8599:      */       
/*  8600:      */       public boolean hasSortFinishTime()
/*  8601:      */       {
/*  8602: 9061 */         return (this.bitField0_ & 0x400) == 1024;
/*  8603:      */       }
/*  8604:      */       
/*  8605:      */       public long getSortFinishTime()
/*  8606:      */       {
/*  8607: 9067 */         return this.sortFinishTime_;
/*  8608:      */       }
/*  8609:      */       
/*  8610:      */       public Builder setSortFinishTime(long value)
/*  8611:      */       {
/*  8612: 9073 */         this.bitField0_ |= 0x400;
/*  8613: 9074 */         this.sortFinishTime_ = value;
/*  8614: 9075 */         onChanged();
/*  8615: 9076 */         return this;
/*  8616:      */       }
/*  8617:      */       
/*  8618:      */       public Builder clearSortFinishTime()
/*  8619:      */       {
/*  8620: 9082 */         this.bitField0_ &= 0xFFFFFBFF;
/*  8621: 9083 */         this.sortFinishTime_ = 0L;
/*  8622: 9084 */         onChanged();
/*  8623: 9085 */         return this;
/*  8624:      */       }
/*  8625:      */       
/*  8626: 9089 */       private Object nodeManagerHost_ = "";
/*  8627:      */       private int nodeManagerPort_;
/*  8628:      */       private int nodeManagerHttpPort_;
/*  8629:      */       
/*  8630:      */       public boolean hasNodeManagerHost()
/*  8631:      */       {
/*  8632: 9094 */         return (this.bitField0_ & 0x800) == 2048;
/*  8633:      */       }
/*  8634:      */       
/*  8635:      */       public String getNodeManagerHost()
/*  8636:      */       {
/*  8637: 9100 */         Object ref = this.nodeManagerHost_;
/*  8638: 9101 */         if (!(ref instanceof String))
/*  8639:      */         {
/*  8640: 9102 */           String s = ((ByteString)ref).toStringUtf8();
/*  8641:      */           
/*  8642: 9104 */           this.nodeManagerHost_ = s;
/*  8643: 9105 */           return s;
/*  8644:      */         }
/*  8645: 9107 */         return (String)ref;
/*  8646:      */       }
/*  8647:      */       
/*  8648:      */       public ByteString getNodeManagerHostBytes()
/*  8649:      */       {
/*  8650: 9115 */         Object ref = this.nodeManagerHost_;
/*  8651: 9116 */         if ((ref instanceof String))
/*  8652:      */         {
/*  8653: 9117 */           ByteString b = ByteString.copyFromUtf8((String)ref);
/*  8654:      */           
/*  8655:      */ 
/*  8656: 9120 */           this.nodeManagerHost_ = b;
/*  8657: 9121 */           return b;
/*  8658:      */         }
/*  8659: 9123 */         return (ByteString)ref;
/*  8660:      */       }
/*  8661:      */       
/*  8662:      */       public Builder setNodeManagerHost(String value)
/*  8663:      */       {
/*  8664: 9131 */         if (value == null) {
/*  8665: 9132 */           throw new NullPointerException();
/*  8666:      */         }
/*  8667: 9134 */         this.bitField0_ |= 0x800;
/*  8668: 9135 */         this.nodeManagerHost_ = value;
/*  8669: 9136 */         onChanged();
/*  8670: 9137 */         return this;
/*  8671:      */       }
/*  8672:      */       
/*  8673:      */       public Builder clearNodeManagerHost()
/*  8674:      */       {
/*  8675: 9143 */         this.bitField0_ &= 0xFFFFF7FF;
/*  8676: 9144 */         this.nodeManagerHost_ = MRProtos.TaskAttemptReportProto.getDefaultInstance().getNodeManagerHost();
/*  8677: 9145 */         onChanged();
/*  8678: 9146 */         return this;
/*  8679:      */       }
/*  8680:      */       
/*  8681:      */       public Builder setNodeManagerHostBytes(ByteString value)
/*  8682:      */       {
/*  8683: 9153 */         if (value == null) {
/*  8684: 9154 */           throw new NullPointerException();
/*  8685:      */         }
/*  8686: 9156 */         this.bitField0_ |= 0x800;
/*  8687: 9157 */         this.nodeManagerHost_ = value;
/*  8688: 9158 */         onChanged();
/*  8689: 9159 */         return this;
/*  8690:      */       }
/*  8691:      */       
/*  8692:      */       public boolean hasNodeManagerPort()
/*  8693:      */       {
/*  8694: 9168 */         return (this.bitField0_ & 0x1000) == 4096;
/*  8695:      */       }
/*  8696:      */       
/*  8697:      */       public int getNodeManagerPort()
/*  8698:      */       {
/*  8699: 9174 */         return this.nodeManagerPort_;
/*  8700:      */       }
/*  8701:      */       
/*  8702:      */       public Builder setNodeManagerPort(int value)
/*  8703:      */       {
/*  8704: 9180 */         this.bitField0_ |= 0x1000;
/*  8705: 9181 */         this.nodeManagerPort_ = value;
/*  8706: 9182 */         onChanged();
/*  8707: 9183 */         return this;
/*  8708:      */       }
/*  8709:      */       
/*  8710:      */       public Builder clearNodeManagerPort()
/*  8711:      */       {
/*  8712: 9189 */         this.bitField0_ &= 0xFFFFEFFF;
/*  8713: 9190 */         this.nodeManagerPort_ = 0;
/*  8714: 9191 */         onChanged();
/*  8715: 9192 */         return this;
/*  8716:      */       }
/*  8717:      */       
/*  8718:      */       public boolean hasNodeManagerHttpPort()
/*  8719:      */       {
/*  8720: 9201 */         return (this.bitField0_ & 0x2000) == 8192;
/*  8721:      */       }
/*  8722:      */       
/*  8723:      */       public int getNodeManagerHttpPort()
/*  8724:      */       {
/*  8725: 9207 */         return this.nodeManagerHttpPort_;
/*  8726:      */       }
/*  8727:      */       
/*  8728:      */       public Builder setNodeManagerHttpPort(int value)
/*  8729:      */       {
/*  8730: 9213 */         this.bitField0_ |= 0x2000;
/*  8731: 9214 */         this.nodeManagerHttpPort_ = value;
/*  8732: 9215 */         onChanged();
/*  8733: 9216 */         return this;
/*  8734:      */       }
/*  8735:      */       
/*  8736:      */       public Builder clearNodeManagerHttpPort()
/*  8737:      */       {
/*  8738: 9222 */         this.bitField0_ &= 0xFFFFDFFF;
/*  8739: 9223 */         this.nodeManagerHttpPort_ = 0;
/*  8740: 9224 */         onChanged();
/*  8741: 9225 */         return this;
/*  8742:      */       }
/*  8743:      */       
/*  8744: 9229 */       private YarnProtos.ContainerIdProto containerId_ = YarnProtos.ContainerIdProto.getDefaultInstance();
/*  8745:      */       private SingleFieldBuilder<YarnProtos.ContainerIdProto, YarnProtos.ContainerIdProto.Builder, YarnProtos.ContainerIdProtoOrBuilder> containerIdBuilder_;
/*  8746:      */       
/*  8747:      */       public boolean hasContainerId()
/*  8748:      */       {
/*  8749: 9236 */         return (this.bitField0_ & 0x4000) == 16384;
/*  8750:      */       }
/*  8751:      */       
/*  8752:      */       public YarnProtos.ContainerIdProto getContainerId()
/*  8753:      */       {
/*  8754: 9242 */         if (this.containerIdBuilder_ == null) {
/*  8755: 9243 */           return this.containerId_;
/*  8756:      */         }
/*  8757: 9245 */         return (YarnProtos.ContainerIdProto)this.containerIdBuilder_.getMessage();
/*  8758:      */       }
/*  8759:      */       
/*  8760:      */       public Builder setContainerId(YarnProtos.ContainerIdProto value)
/*  8761:      */       {
/*  8762: 9252 */         if (this.containerIdBuilder_ == null)
/*  8763:      */         {
/*  8764: 9253 */           if (value == null) {
/*  8765: 9254 */             throw new NullPointerException();
/*  8766:      */           }
/*  8767: 9256 */           this.containerId_ = value;
/*  8768: 9257 */           onChanged();
/*  8769:      */         }
/*  8770:      */         else
/*  8771:      */         {
/*  8772: 9259 */           this.containerIdBuilder_.setMessage(value);
/*  8773:      */         }
/*  8774: 9261 */         this.bitField0_ |= 0x4000;
/*  8775: 9262 */         return this;
/*  8776:      */       }
/*  8777:      */       
/*  8778:      */       public Builder setContainerId(YarnProtos.ContainerIdProto.Builder builderForValue)
/*  8779:      */       {
/*  8780: 9269 */         if (this.containerIdBuilder_ == null)
/*  8781:      */         {
/*  8782: 9270 */           this.containerId_ = builderForValue.build();
/*  8783: 9271 */           onChanged();
/*  8784:      */         }
/*  8785:      */         else
/*  8786:      */         {
/*  8787: 9273 */           this.containerIdBuilder_.setMessage(builderForValue.build());
/*  8788:      */         }
/*  8789: 9275 */         this.bitField0_ |= 0x4000;
/*  8790: 9276 */         return this;
/*  8791:      */       }
/*  8792:      */       
/*  8793:      */       public Builder mergeContainerId(YarnProtos.ContainerIdProto value)
/*  8794:      */       {
/*  8795: 9282 */         if (this.containerIdBuilder_ == null)
/*  8796:      */         {
/*  8797: 9283 */           if (((this.bitField0_ & 0x4000) == 16384) && (this.containerId_ != YarnProtos.ContainerIdProto.getDefaultInstance())) {
/*  8798: 9285 */             this.containerId_ = YarnProtos.ContainerIdProto.newBuilder(this.containerId_).mergeFrom(value).buildPartial();
/*  8799:      */           } else {
/*  8800: 9288 */             this.containerId_ = value;
/*  8801:      */           }
/*  8802: 9290 */           onChanged();
/*  8803:      */         }
/*  8804:      */         else
/*  8805:      */         {
/*  8806: 9292 */           this.containerIdBuilder_.mergeFrom(value);
/*  8807:      */         }
/*  8808: 9294 */         this.bitField0_ |= 0x4000;
/*  8809: 9295 */         return this;
/*  8810:      */       }
/*  8811:      */       
/*  8812:      */       public Builder clearContainerId()
/*  8813:      */       {
/*  8814: 9301 */         if (this.containerIdBuilder_ == null)
/*  8815:      */         {
/*  8816: 9302 */           this.containerId_ = YarnProtos.ContainerIdProto.getDefaultInstance();
/*  8817: 9303 */           onChanged();
/*  8818:      */         }
/*  8819:      */         else
/*  8820:      */         {
/*  8821: 9305 */           this.containerIdBuilder_.clear();
/*  8822:      */         }
/*  8823: 9307 */         this.bitField0_ &= 0xFFFFBFFF;
/*  8824: 9308 */         return this;
/*  8825:      */       }
/*  8826:      */       
/*  8827:      */       public YarnProtos.ContainerIdProto.Builder getContainerIdBuilder()
/*  8828:      */       {
/*  8829: 9314 */         this.bitField0_ |= 0x4000;
/*  8830: 9315 */         onChanged();
/*  8831: 9316 */         return (YarnProtos.ContainerIdProto.Builder)getContainerIdFieldBuilder().getBuilder();
/*  8832:      */       }
/*  8833:      */       
/*  8834:      */       public YarnProtos.ContainerIdProtoOrBuilder getContainerIdOrBuilder()
/*  8835:      */       {
/*  8836: 9322 */         if (this.containerIdBuilder_ != null) {
/*  8837: 9323 */           return (YarnProtos.ContainerIdProtoOrBuilder)this.containerIdBuilder_.getMessageOrBuilder();
/*  8838:      */         }
/*  8839: 9325 */         return this.containerId_;
/*  8840:      */       }
/*  8841:      */       
/*  8842:      */       private SingleFieldBuilder<YarnProtos.ContainerIdProto, YarnProtos.ContainerIdProto.Builder, YarnProtos.ContainerIdProtoOrBuilder> getContainerIdFieldBuilder()
/*  8843:      */       {
/*  8844: 9334 */         if (this.containerIdBuilder_ == null)
/*  8845:      */         {
/*  8846: 9335 */           this.containerIdBuilder_ = new SingleFieldBuilder(this.containerId_, getParentForChildren(), isClean());
/*  8847:      */           
/*  8848:      */ 
/*  8849:      */ 
/*  8850:      */ 
/*  8851: 9340 */           this.containerId_ = null;
/*  8852:      */         }
/*  8853: 9342 */         return this.containerIdBuilder_;
/*  8854:      */       }
/*  8855:      */     }
/*  8856:      */     
/*  8857:      */     static
/*  8858:      */     {
/*  8859: 9349 */       defaultInstance = new TaskAttemptReportProto(true);
/*  8860: 9350 */       defaultInstance.initFields();
/*  8861:      */     }
/*  8862:      */   }
/*  8863:      */   
/*  8864:      */   public static abstract interface JobReportProtoOrBuilder
/*  8865:      */     extends MessageOrBuilder
/*  8866:      */   {
/*  8867:      */     public abstract boolean hasJobId();
/*  8868:      */     
/*  8869:      */     public abstract MRProtos.JobIdProto getJobId();
/*  8870:      */     
/*  8871:      */     public abstract MRProtos.JobIdProtoOrBuilder getJobIdOrBuilder();
/*  8872:      */     
/*  8873:      */     public abstract boolean hasJobState();
/*  8874:      */     
/*  8875:      */     public abstract MRProtos.JobStateProto getJobState();
/*  8876:      */     
/*  8877:      */     public abstract boolean hasMapProgress();
/*  8878:      */     
/*  8879:      */     public abstract float getMapProgress();
/*  8880:      */     
/*  8881:      */     public abstract boolean hasReduceProgress();
/*  8882:      */     
/*  8883:      */     public abstract float getReduceProgress();
/*  8884:      */     
/*  8885:      */     public abstract boolean hasCleanupProgress();
/*  8886:      */     
/*  8887:      */     public abstract float getCleanupProgress();
/*  8888:      */     
/*  8889:      */     public abstract boolean hasSetupProgress();
/*  8890:      */     
/*  8891:      */     public abstract float getSetupProgress();
/*  8892:      */     
/*  8893:      */     public abstract boolean hasStartTime();
/*  8894:      */     
/*  8895:      */     public abstract long getStartTime();
/*  8896:      */     
/*  8897:      */     public abstract boolean hasFinishTime();
/*  8898:      */     
/*  8899:      */     public abstract long getFinishTime();
/*  8900:      */     
/*  8901:      */     public abstract boolean hasUser();
/*  8902:      */     
/*  8903:      */     public abstract String getUser();
/*  8904:      */     
/*  8905:      */     public abstract ByteString getUserBytes();
/*  8906:      */     
/*  8907:      */     public abstract boolean hasJobName();
/*  8908:      */     
/*  8909:      */     public abstract String getJobName();
/*  8910:      */     
/*  8911:      */     public abstract ByteString getJobNameBytes();
/*  8912:      */     
/*  8913:      */     public abstract boolean hasTrackingUrl();
/*  8914:      */     
/*  8915:      */     public abstract String getTrackingUrl();
/*  8916:      */     
/*  8917:      */     public abstract ByteString getTrackingUrlBytes();
/*  8918:      */     
/*  8919:      */     public abstract boolean hasDiagnostics();
/*  8920:      */     
/*  8921:      */     public abstract String getDiagnostics();
/*  8922:      */     
/*  8923:      */     public abstract ByteString getDiagnosticsBytes();
/*  8924:      */     
/*  8925:      */     public abstract boolean hasJobFile();
/*  8926:      */     
/*  8927:      */     public abstract String getJobFile();
/*  8928:      */     
/*  8929:      */     public abstract ByteString getJobFileBytes();
/*  8930:      */     
/*  8931:      */     public abstract List<MRProtos.AMInfoProto> getAmInfosList();
/*  8932:      */     
/*  8933:      */     public abstract MRProtos.AMInfoProto getAmInfos(int paramInt);
/*  8934:      */     
/*  8935:      */     public abstract int getAmInfosCount();
/*  8936:      */     
/*  8937:      */     public abstract List<? extends MRProtos.AMInfoProtoOrBuilder> getAmInfosOrBuilderList();
/*  8938:      */     
/*  8939:      */     public abstract MRProtos.AMInfoProtoOrBuilder getAmInfosOrBuilder(int paramInt);
/*  8940:      */     
/*  8941:      */     public abstract boolean hasSubmitTime();
/*  8942:      */     
/*  8943:      */     public abstract long getSubmitTime();
/*  8944:      */     
/*  8945:      */     public abstract boolean hasIsUber();
/*  8946:      */     
/*  8947:      */     public abstract boolean getIsUber();
/*  8948:      */   }
/*  8949:      */   
/*  8950:      */   public static final class JobReportProto
/*  8951:      */     extends GeneratedMessage
/*  8952:      */     implements MRProtos.JobReportProtoOrBuilder
/*  8953:      */   {
/*  8954:      */     private static final JobReportProto defaultInstance;
/*  8955:      */     private final UnknownFieldSet unknownFields;
/*  8956:      */     
/*  8957:      */     private JobReportProto(GeneratedMessage.Builder<?> builder)
/*  8958:      */     {
/*  8959: 9571 */       super();
/*  8960: 9572 */       this.unknownFields = builder.getUnknownFields();
/*  8961:      */     }
/*  8962:      */     
/*  8963:      */     private JobReportProto(boolean noInit)
/*  8964:      */     {
/*  8965: 9574 */       this.unknownFields = UnknownFieldSet.getDefaultInstance();
/*  8966:      */     }
/*  8967:      */     
/*  8968:      */     public static JobReportProto getDefaultInstance()
/*  8969:      */     {
/*  8970: 9578 */       return defaultInstance;
/*  8971:      */     }
/*  8972:      */     
/*  8973:      */     public JobReportProto getDefaultInstanceForType()
/*  8974:      */     {
/*  8975: 9582 */       return defaultInstance;
/*  8976:      */     }
/*  8977:      */     
/*  8978:      */     public final UnknownFieldSet getUnknownFields()
/*  8979:      */     {
/*  8980: 9589 */       return this.unknownFields;
/*  8981:      */     }
/*  8982:      */     
/*  8983:      */     private JobReportProto(CodedInputStream input, ExtensionRegistryLite extensionRegistry)
/*  8984:      */       throws InvalidProtocolBufferException
/*  8985:      */     {
/*  8986: 9595 */       initFields();
/*  8987: 9596 */       int mutable_bitField0_ = 0;
/*  8988: 9597 */       UnknownFieldSet.Builder unknownFields = UnknownFieldSet.newBuilder();
/*  8989:      */       try
/*  8990:      */       {
/*  8991: 9600 */         boolean done = false;
/*  8992: 9601 */         while (!done)
/*  8993:      */         {
/*  8994: 9602 */           int tag = input.readTag();
/*  8995: 9603 */           switch (tag)
/*  8996:      */           {
/*  8997:      */           case 0: 
/*  8998: 9605 */             done = true;
/*  8999: 9606 */             break;
/*  9000:      */           default: 
/*  9001: 9608 */             if (!parseUnknownField(input, unknownFields, extensionRegistry, tag)) {
/*  9002: 9610 */               done = true;
/*  9003:      */             }
/*  9004:      */             break;
/*  9005:      */           case 10: 
/*  9006: 9615 */             MRProtos.JobIdProto.Builder subBuilder = null;
/*  9007: 9616 */             if ((this.bitField0_ & 0x1) == 1) {
/*  9008: 9617 */               subBuilder = this.jobId_.toBuilder();
/*  9009:      */             }
/*  9010: 9619 */             this.jobId_ = ((MRProtos.JobIdProto)input.readMessage(MRProtos.JobIdProto.PARSER, extensionRegistry));
/*  9011: 9620 */             if (subBuilder != null)
/*  9012:      */             {
/*  9013: 9621 */               subBuilder.mergeFrom(this.jobId_);
/*  9014: 9622 */               this.jobId_ = subBuilder.buildPartial();
/*  9015:      */             }
/*  9016: 9624 */             this.bitField0_ |= 0x1;
/*  9017: 9625 */             break;
/*  9018:      */           case 16: 
/*  9019: 9628 */             int rawValue = input.readEnum();
/*  9020: 9629 */             MRProtos.JobStateProto value = MRProtos.JobStateProto.valueOf(rawValue);
/*  9021: 9630 */             if (value == null)
/*  9022:      */             {
/*  9023: 9631 */               unknownFields.mergeVarintField(2, rawValue);
/*  9024:      */             }
/*  9025:      */             else
/*  9026:      */             {
/*  9027: 9633 */               this.bitField0_ |= 0x2;
/*  9028: 9634 */               this.jobState_ = value;
/*  9029:      */             }
/*  9030: 9636 */             break;
/*  9031:      */           case 29: 
/*  9032: 9639 */             this.bitField0_ |= 0x4;
/*  9033: 9640 */             this.mapProgress_ = input.readFloat();
/*  9034: 9641 */             break;
/*  9035:      */           case 37: 
/*  9036: 9644 */             this.bitField0_ |= 0x8;
/*  9037: 9645 */             this.reduceProgress_ = input.readFloat();
/*  9038: 9646 */             break;
/*  9039:      */           case 45: 
/*  9040: 9649 */             this.bitField0_ |= 0x10;
/*  9041: 9650 */             this.cleanupProgress_ = input.readFloat();
/*  9042: 9651 */             break;
/*  9043:      */           case 53: 
/*  9044: 9654 */             this.bitField0_ |= 0x20;
/*  9045: 9655 */             this.setupProgress_ = input.readFloat();
/*  9046: 9656 */             break;
/*  9047:      */           case 56: 
/*  9048: 9659 */             this.bitField0_ |= 0x40;
/*  9049: 9660 */             this.startTime_ = input.readInt64();
/*  9050: 9661 */             break;
/*  9051:      */           case 64: 
/*  9052: 9664 */             this.bitField0_ |= 0x80;
/*  9053: 9665 */             this.finishTime_ = input.readInt64();
/*  9054: 9666 */             break;
/*  9055:      */           case 74: 
/*  9056: 9669 */             this.bitField0_ |= 0x100;
/*  9057: 9670 */             this.user_ = input.readBytes();
/*  9058: 9671 */             break;
/*  9059:      */           case 82: 
/*  9060: 9674 */             this.bitField0_ |= 0x200;
/*  9061: 9675 */             this.jobName_ = input.readBytes();
/*  9062: 9676 */             break;
/*  9063:      */           case 90: 
/*  9064: 9679 */             this.bitField0_ |= 0x400;
/*  9065: 9680 */             this.trackingUrl_ = input.readBytes();
/*  9066: 9681 */             break;
/*  9067:      */           case 98: 
/*  9068: 9684 */             this.bitField0_ |= 0x800;
/*  9069: 9685 */             this.diagnostics_ = input.readBytes();
/*  9070: 9686 */             break;
/*  9071:      */           case 106: 
/*  9072: 9689 */             this.bitField0_ |= 0x1000;
/*  9073: 9690 */             this.jobFile_ = input.readBytes();
/*  9074: 9691 */             break;
/*  9075:      */           case 114: 
/*  9076: 9694 */             if ((mutable_bitField0_ & 0x2000) != 8192)
/*  9077:      */             {
/*  9078: 9695 */               this.amInfos_ = new ArrayList();
/*  9079: 9696 */               mutable_bitField0_ |= 0x2000;
/*  9080:      */             }
/*  9081: 9698 */             this.amInfos_.add(input.readMessage(MRProtos.AMInfoProto.PARSER, extensionRegistry));
/*  9082: 9699 */             break;
/*  9083:      */           case 120: 
/*  9084: 9702 */             this.bitField0_ |= 0x2000;
/*  9085: 9703 */             this.submitTime_ = input.readInt64();
/*  9086: 9704 */             break;
/*  9087:      */           case 128: 
/*  9088: 9707 */             this.bitField0_ |= 0x4000;
/*  9089: 9708 */             this.isUber_ = input.readBool();
/*  9090:      */           }
/*  9091:      */         }
/*  9092:      */       }
/*  9093:      */       catch (InvalidProtocolBufferException e)
/*  9094:      */       {
/*  9095: 9714 */         throw e.setUnfinishedMessage(this);
/*  9096:      */       }
/*  9097:      */       catch (IOException e)
/*  9098:      */       {
/*  9099: 9716 */         throw new InvalidProtocolBufferException(e.getMessage()).setUnfinishedMessage(this);
/*  9100:      */       }
/*  9101:      */       finally
/*  9102:      */       {
/*  9103: 9719 */         if ((mutable_bitField0_ & 0x2000) == 8192) {
/*  9104: 9720 */           this.amInfos_ = Collections.unmodifiableList(this.amInfos_);
/*  9105:      */         }
/*  9106: 9722 */         this.unknownFields = unknownFields.build();
/*  9107: 9723 */         makeExtensionsImmutable();
/*  9108:      */       }
/*  9109:      */     }
/*  9110:      */     
/*  9111:      */     public static final Descriptors.Descriptor getDescriptor()
/*  9112:      */     {
/*  9113: 9728 */       return MRProtos.internal_static_hadoop_mapreduce_JobReportProto_descriptor;
/*  9114:      */     }
/*  9115:      */     
/*  9116:      */     protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable()
/*  9117:      */     {
/*  9118: 9733 */       return MRProtos.internal_static_hadoop_mapreduce_JobReportProto_fieldAccessorTable.ensureFieldAccessorsInitialized(JobReportProto.class, Builder.class);
/*  9119:      */     }
/*  9120:      */     
/*  9121: 9738 */     public static Parser<JobReportProto> PARSER = new AbstractParser()
/*  9122:      */     {
/*  9123:      */       public MRProtos.JobReportProto parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry)
/*  9124:      */         throws InvalidProtocolBufferException
/*  9125:      */       {
/*  9126: 9744 */         return new MRProtos.JobReportProto(input, extensionRegistry, null);
/*  9127:      */       }
/*  9128:      */     };
/*  9129:      */     private int bitField0_;
/*  9130:      */     public static final int JOB_ID_FIELD_NUMBER = 1;
/*  9131:      */     private MRProtos.JobIdProto jobId_;
/*  9132:      */     public static final int JOB_STATE_FIELD_NUMBER = 2;
/*  9133:      */     private MRProtos.JobStateProto jobState_;
/*  9134:      */     public static final int MAP_PROGRESS_FIELD_NUMBER = 3;
/*  9135:      */     private float mapProgress_;
/*  9136:      */     public static final int REDUCE_PROGRESS_FIELD_NUMBER = 4;
/*  9137:      */     private float reduceProgress_;
/*  9138:      */     public static final int CLEANUP_PROGRESS_FIELD_NUMBER = 5;
/*  9139:      */     private float cleanupProgress_;
/*  9140:      */     public static final int SETUP_PROGRESS_FIELD_NUMBER = 6;
/*  9141:      */     private float setupProgress_;
/*  9142:      */     public static final int START_TIME_FIELD_NUMBER = 7;
/*  9143:      */     private long startTime_;
/*  9144:      */     public static final int FINISH_TIME_FIELD_NUMBER = 8;
/*  9145:      */     private long finishTime_;
/*  9146:      */     public static final int USER_FIELD_NUMBER = 9;
/*  9147:      */     private Object user_;
/*  9148:      */     public static final int JOBNAME_FIELD_NUMBER = 10;
/*  9149:      */     private Object jobName_;
/*  9150:      */     public static final int TRACKINGURL_FIELD_NUMBER = 11;
/*  9151:      */     private Object trackingUrl_;
/*  9152:      */     public static final int DIAGNOSTICS_FIELD_NUMBER = 12;
/*  9153:      */     private Object diagnostics_;
/*  9154:      */     public static final int JOBFILE_FIELD_NUMBER = 13;
/*  9155:      */     private Object jobFile_;
/*  9156:      */     public static final int AM_INFOS_FIELD_NUMBER = 14;
/*  9157:      */     private List<MRProtos.AMInfoProto> amInfos_;
/*  9158:      */     public static final int SUBMIT_TIME_FIELD_NUMBER = 15;
/*  9159:      */     private long submitTime_;
/*  9160:      */     public static final int IS_UBER_FIELD_NUMBER = 16;
/*  9161:      */     private boolean isUber_;
/*  9162:      */     
/*  9163:      */     public Parser<JobReportProto> getParserForType()
/*  9164:      */     {
/*  9165: 9750 */       return PARSER;
/*  9166:      */     }
/*  9167:      */     
/*  9168:      */     public boolean hasJobId()
/*  9169:      */     {
/*  9170: 9761 */       return (this.bitField0_ & 0x1) == 1;
/*  9171:      */     }
/*  9172:      */     
/*  9173:      */     public MRProtos.JobIdProto getJobId()
/*  9174:      */     {
/*  9175: 9767 */       return this.jobId_;
/*  9176:      */     }
/*  9177:      */     
/*  9178:      */     public MRProtos.JobIdProtoOrBuilder getJobIdOrBuilder()
/*  9179:      */     {
/*  9180: 9773 */       return this.jobId_;
/*  9181:      */     }
/*  9182:      */     
/*  9183:      */     public boolean hasJobState()
/*  9184:      */     {
/*  9185: 9783 */       return (this.bitField0_ & 0x2) == 2;
/*  9186:      */     }
/*  9187:      */     
/*  9188:      */     public MRProtos.JobStateProto getJobState()
/*  9189:      */     {
/*  9190: 9789 */       return this.jobState_;
/*  9191:      */     }
/*  9192:      */     
/*  9193:      */     public boolean hasMapProgress()
/*  9194:      */     {
/*  9195: 9799 */       return (this.bitField0_ & 0x4) == 4;
/*  9196:      */     }
/*  9197:      */     
/*  9198:      */     public float getMapProgress()
/*  9199:      */     {
/*  9200: 9805 */       return this.mapProgress_;
/*  9201:      */     }
/*  9202:      */     
/*  9203:      */     public boolean hasReduceProgress()
/*  9204:      */     {
/*  9205: 9815 */       return (this.bitField0_ & 0x8) == 8;
/*  9206:      */     }
/*  9207:      */     
/*  9208:      */     public float getReduceProgress()
/*  9209:      */     {
/*  9210: 9821 */       return this.reduceProgress_;
/*  9211:      */     }
/*  9212:      */     
/*  9213:      */     public boolean hasCleanupProgress()
/*  9214:      */     {
/*  9215: 9831 */       return (this.bitField0_ & 0x10) == 16;
/*  9216:      */     }
/*  9217:      */     
/*  9218:      */     public float getCleanupProgress()
/*  9219:      */     {
/*  9220: 9837 */       return this.cleanupProgress_;
/*  9221:      */     }
/*  9222:      */     
/*  9223:      */     public boolean hasSetupProgress()
/*  9224:      */     {
/*  9225: 9847 */       return (this.bitField0_ & 0x20) == 32;
/*  9226:      */     }
/*  9227:      */     
/*  9228:      */     public float getSetupProgress()
/*  9229:      */     {
/*  9230: 9853 */       return this.setupProgress_;
/*  9231:      */     }
/*  9232:      */     
/*  9233:      */     public boolean hasStartTime()
/*  9234:      */     {
/*  9235: 9863 */       return (this.bitField0_ & 0x40) == 64;
/*  9236:      */     }
/*  9237:      */     
/*  9238:      */     public long getStartTime()
/*  9239:      */     {
/*  9240: 9869 */       return this.startTime_;
/*  9241:      */     }
/*  9242:      */     
/*  9243:      */     public boolean hasFinishTime()
/*  9244:      */     {
/*  9245: 9879 */       return (this.bitField0_ & 0x80) == 128;
/*  9246:      */     }
/*  9247:      */     
/*  9248:      */     public long getFinishTime()
/*  9249:      */     {
/*  9250: 9885 */       return this.finishTime_;
/*  9251:      */     }
/*  9252:      */     
/*  9253:      */     public boolean hasUser()
/*  9254:      */     {
/*  9255: 9895 */       return (this.bitField0_ & 0x100) == 256;
/*  9256:      */     }
/*  9257:      */     
/*  9258:      */     public String getUser()
/*  9259:      */     {
/*  9260: 9901 */       Object ref = this.user_;
/*  9261: 9902 */       if ((ref instanceof String)) {
/*  9262: 9903 */         return (String)ref;
/*  9263:      */       }
/*  9264: 9905 */       ByteString bs = (ByteString)ref;
/*  9265:      */       
/*  9266: 9907 */       String s = bs.toStringUtf8();
/*  9267: 9908 */       if (bs.isValidUtf8()) {
/*  9268: 9909 */         this.user_ = s;
/*  9269:      */       }
/*  9270: 9911 */       return s;
/*  9271:      */     }
/*  9272:      */     
/*  9273:      */     public ByteString getUserBytes()
/*  9274:      */     {
/*  9275: 9919 */       Object ref = this.user_;
/*  9276: 9920 */       if ((ref instanceof String))
/*  9277:      */       {
/*  9278: 9921 */         ByteString b = ByteString.copyFromUtf8((String)ref);
/*  9279:      */         
/*  9280:      */ 
/*  9281: 9924 */         this.user_ = b;
/*  9282: 9925 */         return b;
/*  9283:      */       }
/*  9284: 9927 */       return (ByteString)ref;
/*  9285:      */     }
/*  9286:      */     
/*  9287:      */     public boolean hasJobName()
/*  9288:      */     {
/*  9289: 9938 */       return (this.bitField0_ & 0x200) == 512;
/*  9290:      */     }
/*  9291:      */     
/*  9292:      */     public String getJobName()
/*  9293:      */     {
/*  9294: 9944 */       Object ref = this.jobName_;
/*  9295: 9945 */       if ((ref instanceof String)) {
/*  9296: 9946 */         return (String)ref;
/*  9297:      */       }
/*  9298: 9948 */       ByteString bs = (ByteString)ref;
/*  9299:      */       
/*  9300: 9950 */       String s = bs.toStringUtf8();
/*  9301: 9951 */       if (bs.isValidUtf8()) {
/*  9302: 9952 */         this.jobName_ = s;
/*  9303:      */       }
/*  9304: 9954 */       return s;
/*  9305:      */     }
/*  9306:      */     
/*  9307:      */     public ByteString getJobNameBytes()
/*  9308:      */     {
/*  9309: 9962 */       Object ref = this.jobName_;
/*  9310: 9963 */       if ((ref instanceof String))
/*  9311:      */       {
/*  9312: 9964 */         ByteString b = ByteString.copyFromUtf8((String)ref);
/*  9313:      */         
/*  9314:      */ 
/*  9315: 9967 */         this.jobName_ = b;
/*  9316: 9968 */         return b;
/*  9317:      */       }
/*  9318: 9970 */       return (ByteString)ref;
/*  9319:      */     }
/*  9320:      */     
/*  9321:      */     public boolean hasTrackingUrl()
/*  9322:      */     {
/*  9323: 9981 */       return (this.bitField0_ & 0x400) == 1024;
/*  9324:      */     }
/*  9325:      */     
/*  9326:      */     public String getTrackingUrl()
/*  9327:      */     {
/*  9328: 9987 */       Object ref = this.trackingUrl_;
/*  9329: 9988 */       if ((ref instanceof String)) {
/*  9330: 9989 */         return (String)ref;
/*  9331:      */       }
/*  9332: 9991 */       ByteString bs = (ByteString)ref;
/*  9333:      */       
/*  9334: 9993 */       String s = bs.toStringUtf8();
/*  9335: 9994 */       if (bs.isValidUtf8()) {
/*  9336: 9995 */         this.trackingUrl_ = s;
/*  9337:      */       }
/*  9338: 9997 */       return s;
/*  9339:      */     }
/*  9340:      */     
/*  9341:      */     public ByteString getTrackingUrlBytes()
/*  9342:      */     {
/*  9343:10005 */       Object ref = this.trackingUrl_;
/*  9344:10006 */       if ((ref instanceof String))
/*  9345:      */       {
/*  9346:10007 */         ByteString b = ByteString.copyFromUtf8((String)ref);
/*  9347:      */         
/*  9348:      */ 
/*  9349:10010 */         this.trackingUrl_ = b;
/*  9350:10011 */         return b;
/*  9351:      */       }
/*  9352:10013 */       return (ByteString)ref;
/*  9353:      */     }
/*  9354:      */     
/*  9355:      */     public boolean hasDiagnostics()
/*  9356:      */     {
/*  9357:10024 */       return (this.bitField0_ & 0x800) == 2048;
/*  9358:      */     }
/*  9359:      */     
/*  9360:      */     public String getDiagnostics()
/*  9361:      */     {
/*  9362:10030 */       Object ref = this.diagnostics_;
/*  9363:10031 */       if ((ref instanceof String)) {
/*  9364:10032 */         return (String)ref;
/*  9365:      */       }
/*  9366:10034 */       ByteString bs = (ByteString)ref;
/*  9367:      */       
/*  9368:10036 */       String s = bs.toStringUtf8();
/*  9369:10037 */       if (bs.isValidUtf8()) {
/*  9370:10038 */         this.diagnostics_ = s;
/*  9371:      */       }
/*  9372:10040 */       return s;
/*  9373:      */     }
/*  9374:      */     
/*  9375:      */     public ByteString getDiagnosticsBytes()
/*  9376:      */     {
/*  9377:10048 */       Object ref = this.diagnostics_;
/*  9378:10049 */       if ((ref instanceof String))
/*  9379:      */       {
/*  9380:10050 */         ByteString b = ByteString.copyFromUtf8((String)ref);
/*  9381:      */         
/*  9382:      */ 
/*  9383:10053 */         this.diagnostics_ = b;
/*  9384:10054 */         return b;
/*  9385:      */       }
/*  9386:10056 */       return (ByteString)ref;
/*  9387:      */     }
/*  9388:      */     
/*  9389:      */     public boolean hasJobFile()
/*  9390:      */     {
/*  9391:10067 */       return (this.bitField0_ & 0x1000) == 4096;
/*  9392:      */     }
/*  9393:      */     
/*  9394:      */     public String getJobFile()
/*  9395:      */     {
/*  9396:10073 */       Object ref = this.jobFile_;
/*  9397:10074 */       if ((ref instanceof String)) {
/*  9398:10075 */         return (String)ref;
/*  9399:      */       }
/*  9400:10077 */       ByteString bs = (ByteString)ref;
/*  9401:      */       
/*  9402:10079 */       String s = bs.toStringUtf8();
/*  9403:10080 */       if (bs.isValidUtf8()) {
/*  9404:10081 */         this.jobFile_ = s;
/*  9405:      */       }
/*  9406:10083 */       return s;
/*  9407:      */     }
/*  9408:      */     
/*  9409:      */     public ByteString getJobFileBytes()
/*  9410:      */     {
/*  9411:10091 */       Object ref = this.jobFile_;
/*  9412:10092 */       if ((ref instanceof String))
/*  9413:      */       {
/*  9414:10093 */         ByteString b = ByteString.copyFromUtf8((String)ref);
/*  9415:      */         
/*  9416:      */ 
/*  9417:10096 */         this.jobFile_ = b;
/*  9418:10097 */         return b;
/*  9419:      */       }
/*  9420:10099 */       return (ByteString)ref;
/*  9421:      */     }
/*  9422:      */     
/*  9423:      */     public List<MRProtos.AMInfoProto> getAmInfosList()
/*  9424:      */     {
/*  9425:10110 */       return this.amInfos_;
/*  9426:      */     }
/*  9427:      */     
/*  9428:      */     public List<? extends MRProtos.AMInfoProtoOrBuilder> getAmInfosOrBuilderList()
/*  9429:      */     {
/*  9430:10117 */       return this.amInfos_;
/*  9431:      */     }
/*  9432:      */     
/*  9433:      */     public int getAmInfosCount()
/*  9434:      */     {
/*  9435:10123 */       return this.amInfos_.size();
/*  9436:      */     }
/*  9437:      */     
/*  9438:      */     public MRProtos.AMInfoProto getAmInfos(int index)
/*  9439:      */     {
/*  9440:10129 */       return (MRProtos.AMInfoProto)this.amInfos_.get(index);
/*  9441:      */     }
/*  9442:      */     
/*  9443:      */     public MRProtos.AMInfoProtoOrBuilder getAmInfosOrBuilder(int index)
/*  9444:      */     {
/*  9445:10136 */       return (MRProtos.AMInfoProtoOrBuilder)this.amInfos_.get(index);
/*  9446:      */     }
/*  9447:      */     
/*  9448:      */     public boolean hasSubmitTime()
/*  9449:      */     {
/*  9450:10146 */       return (this.bitField0_ & 0x2000) == 8192;
/*  9451:      */     }
/*  9452:      */     
/*  9453:      */     public long getSubmitTime()
/*  9454:      */     {
/*  9455:10152 */       return this.submitTime_;
/*  9456:      */     }
/*  9457:      */     
/*  9458:      */     public boolean hasIsUber()
/*  9459:      */     {
/*  9460:10162 */       return (this.bitField0_ & 0x4000) == 16384;
/*  9461:      */     }
/*  9462:      */     
/*  9463:      */     public boolean getIsUber()
/*  9464:      */     {
/*  9465:10168 */       return this.isUber_;
/*  9466:      */     }
/*  9467:      */     
/*  9468:      */     private void initFields()
/*  9469:      */     {
/*  9470:10172 */       this.jobId_ = MRProtos.JobIdProto.getDefaultInstance();
/*  9471:10173 */       this.jobState_ = MRProtos.JobStateProto.J_NEW;
/*  9472:10174 */       this.mapProgress_ = 0.0F;
/*  9473:10175 */       this.reduceProgress_ = 0.0F;
/*  9474:10176 */       this.cleanupProgress_ = 0.0F;
/*  9475:10177 */       this.setupProgress_ = 0.0F;
/*  9476:10178 */       this.startTime_ = 0L;
/*  9477:10179 */       this.finishTime_ = 0L;
/*  9478:10180 */       this.user_ = "";
/*  9479:10181 */       this.jobName_ = "";
/*  9480:10182 */       this.trackingUrl_ = "";
/*  9481:10183 */       this.diagnostics_ = "";
/*  9482:10184 */       this.jobFile_ = "";
/*  9483:10185 */       this.amInfos_ = Collections.emptyList();
/*  9484:10186 */       this.submitTime_ = 0L;
/*  9485:10187 */       this.isUber_ = false;
/*  9486:      */     }
/*  9487:      */     
/*  9488:10189 */     private byte memoizedIsInitialized = -1;
/*  9489:      */     
/*  9490:      */     public final boolean isInitialized()
/*  9491:      */     {
/*  9492:10191 */       byte isInitialized = this.memoizedIsInitialized;
/*  9493:10192 */       if (isInitialized != -1) {
/*  9494:10192 */         return isInitialized == 1;
/*  9495:      */       }
/*  9496:10194 */       this.memoizedIsInitialized = 1;
/*  9497:10195 */       return true;
/*  9498:      */     }
/*  9499:      */     
/*  9500:      */     public void writeTo(CodedOutputStream output)
/*  9501:      */       throws IOException
/*  9502:      */     {
/*  9503:10200 */       getSerializedSize();
/*  9504:10201 */       if ((this.bitField0_ & 0x1) == 1) {
/*  9505:10202 */         output.writeMessage(1, this.jobId_);
/*  9506:      */       }
/*  9507:10204 */       if ((this.bitField0_ & 0x2) == 2) {
/*  9508:10205 */         output.writeEnum(2, this.jobState_.getNumber());
/*  9509:      */       }
/*  9510:10207 */       if ((this.bitField0_ & 0x4) == 4) {
/*  9511:10208 */         output.writeFloat(3, this.mapProgress_);
/*  9512:      */       }
/*  9513:10210 */       if ((this.bitField0_ & 0x8) == 8) {
/*  9514:10211 */         output.writeFloat(4, this.reduceProgress_);
/*  9515:      */       }
/*  9516:10213 */       if ((this.bitField0_ & 0x10) == 16) {
/*  9517:10214 */         output.writeFloat(5, this.cleanupProgress_);
/*  9518:      */       }
/*  9519:10216 */       if ((this.bitField0_ & 0x20) == 32) {
/*  9520:10217 */         output.writeFloat(6, this.setupProgress_);
/*  9521:      */       }
/*  9522:10219 */       if ((this.bitField0_ & 0x40) == 64) {
/*  9523:10220 */         output.writeInt64(7, this.startTime_);
/*  9524:      */       }
/*  9525:10222 */       if ((this.bitField0_ & 0x80) == 128) {
/*  9526:10223 */         output.writeInt64(8, this.finishTime_);
/*  9527:      */       }
/*  9528:10225 */       if ((this.bitField0_ & 0x100) == 256) {
/*  9529:10226 */         output.writeBytes(9, getUserBytes());
/*  9530:      */       }
/*  9531:10228 */       if ((this.bitField0_ & 0x200) == 512) {
/*  9532:10229 */         output.writeBytes(10, getJobNameBytes());
/*  9533:      */       }
/*  9534:10231 */       if ((this.bitField0_ & 0x400) == 1024) {
/*  9535:10232 */         output.writeBytes(11, getTrackingUrlBytes());
/*  9536:      */       }
/*  9537:10234 */       if ((this.bitField0_ & 0x800) == 2048) {
/*  9538:10235 */         output.writeBytes(12, getDiagnosticsBytes());
/*  9539:      */       }
/*  9540:10237 */       if ((this.bitField0_ & 0x1000) == 4096) {
/*  9541:10238 */         output.writeBytes(13, getJobFileBytes());
/*  9542:      */       }
/*  9543:10240 */       for (int i = 0; i < this.amInfos_.size(); i++) {
/*  9544:10241 */         output.writeMessage(14, (MessageLite)this.amInfos_.get(i));
/*  9545:      */       }
/*  9546:10243 */       if ((this.bitField0_ & 0x2000) == 8192) {
/*  9547:10244 */         output.writeInt64(15, this.submitTime_);
/*  9548:      */       }
/*  9549:10246 */       if ((this.bitField0_ & 0x4000) == 16384) {
/*  9550:10247 */         output.writeBool(16, this.isUber_);
/*  9551:      */       }
/*  9552:10249 */       getUnknownFields().writeTo(output);
/*  9553:      */     }
/*  9554:      */     
/*  9555:10252 */     private int memoizedSerializedSize = -1;
/*  9556:      */     private static final long serialVersionUID = 0L;
/*  9557:      */     
/*  9558:      */     public int getSerializedSize()
/*  9559:      */     {
/*  9560:10254 */       int size = this.memoizedSerializedSize;
/*  9561:10255 */       if (size != -1) {
/*  9562:10255 */         return size;
/*  9563:      */       }
/*  9564:10257 */       size = 0;
/*  9565:10258 */       if ((this.bitField0_ & 0x1) == 1) {
/*  9566:10259 */         size += CodedOutputStream.computeMessageSize(1, this.jobId_);
/*  9567:      */       }
/*  9568:10262 */       if ((this.bitField0_ & 0x2) == 2) {
/*  9569:10263 */         size += CodedOutputStream.computeEnumSize(2, this.jobState_.getNumber());
/*  9570:      */       }
/*  9571:10266 */       if ((this.bitField0_ & 0x4) == 4) {
/*  9572:10267 */         size += CodedOutputStream.computeFloatSize(3, this.mapProgress_);
/*  9573:      */       }
/*  9574:10270 */       if ((this.bitField0_ & 0x8) == 8) {
/*  9575:10271 */         size += CodedOutputStream.computeFloatSize(4, this.reduceProgress_);
/*  9576:      */       }
/*  9577:10274 */       if ((this.bitField0_ & 0x10) == 16) {
/*  9578:10275 */         size += CodedOutputStream.computeFloatSize(5, this.cleanupProgress_);
/*  9579:      */       }
/*  9580:10278 */       if ((this.bitField0_ & 0x20) == 32) {
/*  9581:10279 */         size += CodedOutputStream.computeFloatSize(6, this.setupProgress_);
/*  9582:      */       }
/*  9583:10282 */       if ((this.bitField0_ & 0x40) == 64) {
/*  9584:10283 */         size += CodedOutputStream.computeInt64Size(7, this.startTime_);
/*  9585:      */       }
/*  9586:10286 */       if ((this.bitField0_ & 0x80) == 128) {
/*  9587:10287 */         size += CodedOutputStream.computeInt64Size(8, this.finishTime_);
/*  9588:      */       }
/*  9589:10290 */       if ((this.bitField0_ & 0x100) == 256) {
/*  9590:10291 */         size += CodedOutputStream.computeBytesSize(9, getUserBytes());
/*  9591:      */       }
/*  9592:10294 */       if ((this.bitField0_ & 0x200) == 512) {
/*  9593:10295 */         size += CodedOutputStream.computeBytesSize(10, getJobNameBytes());
/*  9594:      */       }
/*  9595:10298 */       if ((this.bitField0_ & 0x400) == 1024) {
/*  9596:10299 */         size += CodedOutputStream.computeBytesSize(11, getTrackingUrlBytes());
/*  9597:      */       }
/*  9598:10302 */       if ((this.bitField0_ & 0x800) == 2048) {
/*  9599:10303 */         size += CodedOutputStream.computeBytesSize(12, getDiagnosticsBytes());
/*  9600:      */       }
/*  9601:10306 */       if ((this.bitField0_ & 0x1000) == 4096) {
/*  9602:10307 */         size += CodedOutputStream.computeBytesSize(13, getJobFileBytes());
/*  9603:      */       }
/*  9604:10310 */       for (int i = 0; i < this.amInfos_.size(); i++) {
/*  9605:10311 */         size += CodedOutputStream.computeMessageSize(14, (MessageLite)this.amInfos_.get(i));
/*  9606:      */       }
/*  9607:10314 */       if ((this.bitField0_ & 0x2000) == 8192) {
/*  9608:10315 */         size += CodedOutputStream.computeInt64Size(15, this.submitTime_);
/*  9609:      */       }
/*  9610:10318 */       if ((this.bitField0_ & 0x4000) == 16384) {
/*  9611:10319 */         size += CodedOutputStream.computeBoolSize(16, this.isUber_);
/*  9612:      */       }
/*  9613:10322 */       size += getUnknownFields().getSerializedSize();
/*  9614:10323 */       this.memoizedSerializedSize = size;
/*  9615:10324 */       return size;
/*  9616:      */     }
/*  9617:      */     
/*  9618:      */     protected Object writeReplace()
/*  9619:      */       throws ObjectStreamException
/*  9620:      */     {
/*  9621:10331 */       return super.writeReplace();
/*  9622:      */     }
/*  9623:      */     
/*  9624:      */     public boolean equals(Object obj)
/*  9625:      */     {
/*  9626:10336 */       if (obj == this) {
/*  9627:10337 */         return true;
/*  9628:      */       }
/*  9629:10339 */       if (!(obj instanceof JobReportProto)) {
/*  9630:10340 */         return super.equals(obj);
/*  9631:      */       }
/*  9632:10342 */       JobReportProto other = (JobReportProto)obj;
/*  9633:      */       
/*  9634:10344 */       boolean result = true;
/*  9635:10345 */       result = (result) && (hasJobId() == other.hasJobId());
/*  9636:10346 */       if (hasJobId()) {
/*  9637:10347 */         result = (result) && (getJobId().equals(other.getJobId()));
/*  9638:      */       }
/*  9639:10350 */       result = (result) && (hasJobState() == other.hasJobState());
/*  9640:10351 */       if (hasJobState()) {
/*  9641:10352 */         result = (result) && (getJobState() == other.getJobState());
/*  9642:      */       }
/*  9643:10355 */       result = (result) && (hasMapProgress() == other.hasMapProgress());
/*  9644:10356 */       if (hasMapProgress()) {
/*  9645:10357 */         result = (result) && (Float.floatToIntBits(getMapProgress()) == Float.floatToIntBits(other.getMapProgress()));
/*  9646:      */       }
/*  9647:10359 */       result = (result) && (hasReduceProgress() == other.hasReduceProgress());
/*  9648:10360 */       if (hasReduceProgress()) {
/*  9649:10361 */         result = (result) && (Float.floatToIntBits(getReduceProgress()) == Float.floatToIntBits(other.getReduceProgress()));
/*  9650:      */       }
/*  9651:10363 */       result = (result) && (hasCleanupProgress() == other.hasCleanupProgress());
/*  9652:10364 */       if (hasCleanupProgress()) {
/*  9653:10365 */         result = (result) && (Float.floatToIntBits(getCleanupProgress()) == Float.floatToIntBits(other.getCleanupProgress()));
/*  9654:      */       }
/*  9655:10367 */       result = (result) && (hasSetupProgress() == other.hasSetupProgress());
/*  9656:10368 */       if (hasSetupProgress()) {
/*  9657:10369 */         result = (result) && (Float.floatToIntBits(getSetupProgress()) == Float.floatToIntBits(other.getSetupProgress()));
/*  9658:      */       }
/*  9659:10371 */       result = (result) && (hasStartTime() == other.hasStartTime());
/*  9660:10372 */       if (hasStartTime()) {
/*  9661:10373 */         result = (result) && (getStartTime() == other.getStartTime());
/*  9662:      */       }
/*  9663:10376 */       result = (result) && (hasFinishTime() == other.hasFinishTime());
/*  9664:10377 */       if (hasFinishTime()) {
/*  9665:10378 */         result = (result) && (getFinishTime() == other.getFinishTime());
/*  9666:      */       }
/*  9667:10381 */       result = (result) && (hasUser() == other.hasUser());
/*  9668:10382 */       if (hasUser()) {
/*  9669:10383 */         result = (result) && (getUser().equals(other.getUser()));
/*  9670:      */       }
/*  9671:10386 */       result = (result) && (hasJobName() == other.hasJobName());
/*  9672:10387 */       if (hasJobName()) {
/*  9673:10388 */         result = (result) && (getJobName().equals(other.getJobName()));
/*  9674:      */       }
/*  9675:10391 */       result = (result) && (hasTrackingUrl() == other.hasTrackingUrl());
/*  9676:10392 */       if (hasTrackingUrl()) {
/*  9677:10393 */         result = (result) && (getTrackingUrl().equals(other.getTrackingUrl()));
/*  9678:      */       }
/*  9679:10396 */       result = (result) && (hasDiagnostics() == other.hasDiagnostics());
/*  9680:10397 */       if (hasDiagnostics()) {
/*  9681:10398 */         result = (result) && (getDiagnostics().equals(other.getDiagnostics()));
/*  9682:      */       }
/*  9683:10401 */       result = (result) && (hasJobFile() == other.hasJobFile());
/*  9684:10402 */       if (hasJobFile()) {
/*  9685:10403 */         result = (result) && (getJobFile().equals(other.getJobFile()));
/*  9686:      */       }
/*  9687:10406 */       result = (result) && (getAmInfosList().equals(other.getAmInfosList()));
/*  9688:      */       
/*  9689:10408 */       result = (result) && (hasSubmitTime() == other.hasSubmitTime());
/*  9690:10409 */       if (hasSubmitTime()) {
/*  9691:10410 */         result = (result) && (getSubmitTime() == other.getSubmitTime());
/*  9692:      */       }
/*  9693:10413 */       result = (result) && (hasIsUber() == other.hasIsUber());
/*  9694:10414 */       if (hasIsUber()) {
/*  9695:10415 */         result = (result) && (getIsUber() == other.getIsUber());
/*  9696:      */       }
/*  9697:10418 */       result = (result) && (getUnknownFields().equals(other.getUnknownFields()));
/*  9698:      */       
/*  9699:10420 */       return result;
/*  9700:      */     }
/*  9701:      */     
/*  9702:10423 */     private int memoizedHashCode = 0;
/*  9703:      */     
/*  9704:      */     public int hashCode()
/*  9705:      */     {
/*  9706:10426 */       if (this.memoizedHashCode != 0) {
/*  9707:10427 */         return this.memoizedHashCode;
/*  9708:      */       }
/*  9709:10429 */       int hash = 41;
/*  9710:10430 */       hash = 19 * hash + getDescriptorForType().hashCode();
/*  9711:10431 */       if (hasJobId())
/*  9712:      */       {
/*  9713:10432 */         hash = 37 * hash + 1;
/*  9714:10433 */         hash = 53 * hash + getJobId().hashCode();
/*  9715:      */       }
/*  9716:10435 */       if (hasJobState())
/*  9717:      */       {
/*  9718:10436 */         hash = 37 * hash + 2;
/*  9719:10437 */         hash = 53 * hash + hashEnum(getJobState());
/*  9720:      */       }
/*  9721:10439 */       if (hasMapProgress())
/*  9722:      */       {
/*  9723:10440 */         hash = 37 * hash + 3;
/*  9724:10441 */         hash = 53 * hash + Float.floatToIntBits(getMapProgress());
/*  9725:      */       }
/*  9726:10444 */       if (hasReduceProgress())
/*  9727:      */       {
/*  9728:10445 */         hash = 37 * hash + 4;
/*  9729:10446 */         hash = 53 * hash + Float.floatToIntBits(getReduceProgress());
/*  9730:      */       }
/*  9731:10449 */       if (hasCleanupProgress())
/*  9732:      */       {
/*  9733:10450 */         hash = 37 * hash + 5;
/*  9734:10451 */         hash = 53 * hash + Float.floatToIntBits(getCleanupProgress());
/*  9735:      */       }
/*  9736:10454 */       if (hasSetupProgress())
/*  9737:      */       {
/*  9738:10455 */         hash = 37 * hash + 6;
/*  9739:10456 */         hash = 53 * hash + Float.floatToIntBits(getSetupProgress());
/*  9740:      */       }
/*  9741:10459 */       if (hasStartTime())
/*  9742:      */       {
/*  9743:10460 */         hash = 37 * hash + 7;
/*  9744:10461 */         hash = 53 * hash + hashLong(getStartTime());
/*  9745:      */       }
/*  9746:10463 */       if (hasFinishTime())
/*  9747:      */       {
/*  9748:10464 */         hash = 37 * hash + 8;
/*  9749:10465 */         hash = 53 * hash + hashLong(getFinishTime());
/*  9750:      */       }
/*  9751:10467 */       if (hasUser())
/*  9752:      */       {
/*  9753:10468 */         hash = 37 * hash + 9;
/*  9754:10469 */         hash = 53 * hash + getUser().hashCode();
/*  9755:      */       }
/*  9756:10471 */       if (hasJobName())
/*  9757:      */       {
/*  9758:10472 */         hash = 37 * hash + 10;
/*  9759:10473 */         hash = 53 * hash + getJobName().hashCode();
/*  9760:      */       }
/*  9761:10475 */       if (hasTrackingUrl())
/*  9762:      */       {
/*  9763:10476 */         hash = 37 * hash + 11;
/*  9764:10477 */         hash = 53 * hash + getTrackingUrl().hashCode();
/*  9765:      */       }
/*  9766:10479 */       if (hasDiagnostics())
/*  9767:      */       {
/*  9768:10480 */         hash = 37 * hash + 12;
/*  9769:10481 */         hash = 53 * hash + getDiagnostics().hashCode();
/*  9770:      */       }
/*  9771:10483 */       if (hasJobFile())
/*  9772:      */       {
/*  9773:10484 */         hash = 37 * hash + 13;
/*  9774:10485 */         hash = 53 * hash + getJobFile().hashCode();
/*  9775:      */       }
/*  9776:10487 */       if (getAmInfosCount() > 0)
/*  9777:      */       {
/*  9778:10488 */         hash = 37 * hash + 14;
/*  9779:10489 */         hash = 53 * hash + getAmInfosList().hashCode();
/*  9780:      */       }
/*  9781:10491 */       if (hasSubmitTime())
/*  9782:      */       {
/*  9783:10492 */         hash = 37 * hash + 15;
/*  9784:10493 */         hash = 53 * hash + hashLong(getSubmitTime());
/*  9785:      */       }
/*  9786:10495 */       if (hasIsUber())
/*  9787:      */       {
/*  9788:10496 */         hash = 37 * hash + 16;
/*  9789:10497 */         hash = 53 * hash + hashBoolean(getIsUber());
/*  9790:      */       }
/*  9791:10499 */       hash = 29 * hash + getUnknownFields().hashCode();
/*  9792:10500 */       this.memoizedHashCode = hash;
/*  9793:10501 */       return hash;
/*  9794:      */     }
/*  9795:      */     
/*  9796:      */     public static JobReportProto parseFrom(ByteString data)
/*  9797:      */       throws InvalidProtocolBufferException
/*  9798:      */     {
/*  9799:10507 */       return (JobReportProto)PARSER.parseFrom(data);
/*  9800:      */     }
/*  9801:      */     
/*  9802:      */     public static JobReportProto parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry)
/*  9803:      */       throws InvalidProtocolBufferException
/*  9804:      */     {
/*  9805:10513 */       return (JobReportProto)PARSER.parseFrom(data, extensionRegistry);
/*  9806:      */     }
/*  9807:      */     
/*  9808:      */     public static JobReportProto parseFrom(byte[] data)
/*  9809:      */       throws InvalidProtocolBufferException
/*  9810:      */     {
/*  9811:10517 */       return (JobReportProto)PARSER.parseFrom(data);
/*  9812:      */     }
/*  9813:      */     
/*  9814:      */     public static JobReportProto parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry)
/*  9815:      */       throws InvalidProtocolBufferException
/*  9816:      */     {
/*  9817:10523 */       return (JobReportProto)PARSER.parseFrom(data, extensionRegistry);
/*  9818:      */     }
/*  9819:      */     
/*  9820:      */     public static JobReportProto parseFrom(InputStream input)
/*  9821:      */       throws IOException
/*  9822:      */     {
/*  9823:10527 */       return (JobReportProto)PARSER.parseFrom(input);
/*  9824:      */     }
/*  9825:      */     
/*  9826:      */     public static JobReportProto parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry)
/*  9827:      */       throws IOException
/*  9828:      */     {
/*  9829:10533 */       return (JobReportProto)PARSER.parseFrom(input, extensionRegistry);
/*  9830:      */     }
/*  9831:      */     
/*  9832:      */     public static JobReportProto parseDelimitedFrom(InputStream input)
/*  9833:      */       throws IOException
/*  9834:      */     {
/*  9835:10537 */       return (JobReportProto)PARSER.parseDelimitedFrom(input);
/*  9836:      */     }
/*  9837:      */     
/*  9838:      */     public static JobReportProto parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry)
/*  9839:      */       throws IOException
/*  9840:      */     {
/*  9841:10543 */       return (JobReportProto)PARSER.parseDelimitedFrom(input, extensionRegistry);
/*  9842:      */     }
/*  9843:      */     
/*  9844:      */     public static JobReportProto parseFrom(CodedInputStream input)
/*  9845:      */       throws IOException
/*  9846:      */     {
/*  9847:10548 */       return (JobReportProto)PARSER.parseFrom(input);
/*  9848:      */     }
/*  9849:      */     
/*  9850:      */     public static JobReportProto parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry)
/*  9851:      */       throws IOException
/*  9852:      */     {
/*  9853:10554 */       return (JobReportProto)PARSER.parseFrom(input, extensionRegistry);
/*  9854:      */     }
/*  9855:      */     
/*  9856:      */     public static Builder newBuilder()
/*  9857:      */     {
/*  9858:10557 */       return Builder.access$10700();
/*  9859:      */     }
/*  9860:      */     
/*  9861:      */     public Builder newBuilderForType()
/*  9862:      */     {
/*  9863:10558 */       return newBuilder();
/*  9864:      */     }
/*  9865:      */     
/*  9866:      */     public static Builder newBuilder(JobReportProto prototype)
/*  9867:      */     {
/*  9868:10560 */       return newBuilder().mergeFrom(prototype);
/*  9869:      */     }
/*  9870:      */     
/*  9871:      */     public Builder toBuilder()
/*  9872:      */     {
/*  9873:10562 */       return newBuilder(this);
/*  9874:      */     }
/*  9875:      */     
/*  9876:      */     protected Builder newBuilderForType(GeneratedMessage.BuilderParent parent)
/*  9877:      */     {
/*  9878:10567 */       Builder builder = new Builder(parent, null);
/*  9879:10568 */       return builder;
/*  9880:      */     }
/*  9881:      */     
/*  9882:      */     public static final class Builder
/*  9883:      */       extends GeneratedMessage.Builder<Builder>
/*  9884:      */       implements MRProtos.JobReportProtoOrBuilder
/*  9885:      */     {
/*  9886:      */       private int bitField0_;
/*  9887:      */       
/*  9888:      */       public static final Descriptors.Descriptor getDescriptor()
/*  9889:      */       {
/*  9890:10578 */         return MRProtos.internal_static_hadoop_mapreduce_JobReportProto_descriptor;
/*  9891:      */       }
/*  9892:      */       
/*  9893:      */       protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable()
/*  9894:      */       {
/*  9895:10583 */         return MRProtos.internal_static_hadoop_mapreduce_JobReportProto_fieldAccessorTable.ensureFieldAccessorsInitialized(MRProtos.JobReportProto.class, Builder.class);
/*  9896:      */       }
/*  9897:      */       
/*  9898:      */       private Builder()
/*  9899:      */       {
/*  9900:10590 */         maybeForceBuilderInitialization();
/*  9901:      */       }
/*  9902:      */       
/*  9903:      */       private Builder(GeneratedMessage.BuilderParent parent)
/*  9904:      */       {
/*  9905:10595 */         super();
/*  9906:10596 */         maybeForceBuilderInitialization();
/*  9907:      */       }
/*  9908:      */       
/*  9909:      */       private void maybeForceBuilderInitialization()
/*  9910:      */       {
/*  9911:10599 */         if (MRProtos.JobReportProto.alwaysUseFieldBuilders)
/*  9912:      */         {
/*  9913:10600 */           getJobIdFieldBuilder();
/*  9914:10601 */           getAmInfosFieldBuilder();
/*  9915:      */         }
/*  9916:      */       }
/*  9917:      */       
/*  9918:      */       private static Builder create()
/*  9919:      */       {
/*  9920:10605 */         return new Builder();
/*  9921:      */       }
/*  9922:      */       
/*  9923:      */       public Builder clear()
/*  9924:      */       {
/*  9925:10609 */         super.clear();
/*  9926:10610 */         if (this.jobIdBuilder_ == null) {
/*  9927:10611 */           this.jobId_ = MRProtos.JobIdProto.getDefaultInstance();
/*  9928:      */         } else {
/*  9929:10613 */           this.jobIdBuilder_.clear();
/*  9930:      */         }
/*  9931:10615 */         this.bitField0_ &= 0xFFFFFFFE;
/*  9932:10616 */         this.jobState_ = MRProtos.JobStateProto.J_NEW;
/*  9933:10617 */         this.bitField0_ &= 0xFFFFFFFD;
/*  9934:10618 */         this.mapProgress_ = 0.0F;
/*  9935:10619 */         this.bitField0_ &= 0xFFFFFFFB;
/*  9936:10620 */         this.reduceProgress_ = 0.0F;
/*  9937:10621 */         this.bitField0_ &= 0xFFFFFFF7;
/*  9938:10622 */         this.cleanupProgress_ = 0.0F;
/*  9939:10623 */         this.bitField0_ &= 0xFFFFFFEF;
/*  9940:10624 */         this.setupProgress_ = 0.0F;
/*  9941:10625 */         this.bitField0_ &= 0xFFFFFFDF;
/*  9942:10626 */         this.startTime_ = 0L;
/*  9943:10627 */         this.bitField0_ &= 0xFFFFFFBF;
/*  9944:10628 */         this.finishTime_ = 0L;
/*  9945:10629 */         this.bitField0_ &= 0xFFFFFF7F;
/*  9946:10630 */         this.user_ = "";
/*  9947:10631 */         this.bitField0_ &= 0xFFFFFEFF;
/*  9948:10632 */         this.jobName_ = "";
/*  9949:10633 */         this.bitField0_ &= 0xFFFFFDFF;
/*  9950:10634 */         this.trackingUrl_ = "";
/*  9951:10635 */         this.bitField0_ &= 0xFFFFFBFF;
/*  9952:10636 */         this.diagnostics_ = "";
/*  9953:10637 */         this.bitField0_ &= 0xFFFFF7FF;
/*  9954:10638 */         this.jobFile_ = "";
/*  9955:10639 */         this.bitField0_ &= 0xFFFFEFFF;
/*  9956:10640 */         if (this.amInfosBuilder_ == null)
/*  9957:      */         {
/*  9958:10641 */           this.amInfos_ = Collections.emptyList();
/*  9959:10642 */           this.bitField0_ &= 0xFFFFDFFF;
/*  9960:      */         }
/*  9961:      */         else
/*  9962:      */         {
/*  9963:10644 */           this.amInfosBuilder_.clear();
/*  9964:      */         }
/*  9965:10646 */         this.submitTime_ = 0L;
/*  9966:10647 */         this.bitField0_ &= 0xFFFFBFFF;
/*  9967:10648 */         this.isUber_ = false;
/*  9968:10649 */         this.bitField0_ &= 0xFFFF7FFF;
/*  9969:10650 */         return this;
/*  9970:      */       }
/*  9971:      */       
/*  9972:      */       public Builder clone()
/*  9973:      */       {
/*  9974:10654 */         return create().mergeFrom(buildPartial());
/*  9975:      */       }
/*  9976:      */       
/*  9977:      */       public Descriptors.Descriptor getDescriptorForType()
/*  9978:      */       {
/*  9979:10659 */         return MRProtos.internal_static_hadoop_mapreduce_JobReportProto_descriptor;
/*  9980:      */       }
/*  9981:      */       
/*  9982:      */       public MRProtos.JobReportProto getDefaultInstanceForType()
/*  9983:      */       {
/*  9984:10663 */         return MRProtos.JobReportProto.getDefaultInstance();
/*  9985:      */       }
/*  9986:      */       
/*  9987:      */       public MRProtos.JobReportProto build()
/*  9988:      */       {
/*  9989:10667 */         MRProtos.JobReportProto result = buildPartial();
/*  9990:10668 */         if (!result.isInitialized()) {
/*  9991:10669 */           throw newUninitializedMessageException(result);
/*  9992:      */         }
/*  9993:10671 */         return result;
/*  9994:      */       }
/*  9995:      */       
/*  9996:      */       public MRProtos.JobReportProto buildPartial()
/*  9997:      */       {
/*  9998:10675 */         MRProtos.JobReportProto result = new MRProtos.JobReportProto(this, null);
/*  9999:10676 */         int from_bitField0_ = this.bitField0_;
/* 10000:10677 */         int to_bitField0_ = 0;
/* 10001:10678 */         if ((from_bitField0_ & 0x1) == 1) {
/* 10002:10679 */           to_bitField0_ |= 0x1;
/* 10003:      */         }
/* 10004:10681 */         if (this.jobIdBuilder_ == null) {
/* 10005:10682 */           result.jobId_ = this.jobId_;
/* 10006:      */         } else {
/* 10007:10684 */           result.jobId_ = ((MRProtos.JobIdProto)this.jobIdBuilder_.build());
/* 10008:      */         }
/* 10009:10686 */         if ((from_bitField0_ & 0x2) == 2) {
/* 10010:10687 */           to_bitField0_ |= 0x2;
/* 10011:      */         }
/* 10012:10689 */         result.jobState_ = this.jobState_;
/* 10013:10690 */         if ((from_bitField0_ & 0x4) == 4) {
/* 10014:10691 */           to_bitField0_ |= 0x4;
/* 10015:      */         }
/* 10016:10693 */         result.mapProgress_ = this.mapProgress_;
/* 10017:10694 */         if ((from_bitField0_ & 0x8) == 8) {
/* 10018:10695 */           to_bitField0_ |= 0x8;
/* 10019:      */         }
/* 10020:10697 */         result.reduceProgress_ = this.reduceProgress_;
/* 10021:10698 */         if ((from_bitField0_ & 0x10) == 16) {
/* 10022:10699 */           to_bitField0_ |= 0x10;
/* 10023:      */         }
/* 10024:10701 */         result.cleanupProgress_ = this.cleanupProgress_;
/* 10025:10702 */         if ((from_bitField0_ & 0x20) == 32) {
/* 10026:10703 */           to_bitField0_ |= 0x20;
/* 10027:      */         }
/* 10028:10705 */         result.setupProgress_ = this.setupProgress_;
/* 10029:10706 */         if ((from_bitField0_ & 0x40) == 64) {
/* 10030:10707 */           to_bitField0_ |= 0x40;
/* 10031:      */         }
/* 10032:10709 */         result.startTime_ = this.startTime_;
/* 10033:10710 */         if ((from_bitField0_ & 0x80) == 128) {
/* 10034:10711 */           to_bitField0_ |= 0x80;
/* 10035:      */         }
/* 10036:10713 */         result.finishTime_ = this.finishTime_;
/* 10037:10714 */         if ((from_bitField0_ & 0x100) == 256) {
/* 10038:10715 */           to_bitField0_ |= 0x100;
/* 10039:      */         }
/* 10040:10717 */         result.user_ = this.user_;
/* 10041:10718 */         if ((from_bitField0_ & 0x200) == 512) {
/* 10042:10719 */           to_bitField0_ |= 0x200;
/* 10043:      */         }
/* 10044:10721 */         result.jobName_ = this.jobName_;
/* 10045:10722 */         if ((from_bitField0_ & 0x400) == 1024) {
/* 10046:10723 */           to_bitField0_ |= 0x400;
/* 10047:      */         }
/* 10048:10725 */         result.trackingUrl_ = this.trackingUrl_;
/* 10049:10726 */         if ((from_bitField0_ & 0x800) == 2048) {
/* 10050:10727 */           to_bitField0_ |= 0x800;
/* 10051:      */         }
/* 10052:10729 */         result.diagnostics_ = this.diagnostics_;
/* 10053:10730 */         if ((from_bitField0_ & 0x1000) == 4096) {
/* 10054:10731 */           to_bitField0_ |= 0x1000;
/* 10055:      */         }
/* 10056:10733 */         result.jobFile_ = this.jobFile_;
/* 10057:10734 */         if (this.amInfosBuilder_ == null)
/* 10058:      */         {
/* 10059:10735 */           if ((this.bitField0_ & 0x2000) == 8192)
/* 10060:      */           {
/* 10061:10736 */             this.amInfos_ = Collections.unmodifiableList(this.amInfos_);
/* 10062:10737 */             this.bitField0_ &= 0xFFFFDFFF;
/* 10063:      */           }
/* 10064:10739 */           result.amInfos_ = this.amInfos_;
/* 10065:      */         }
/* 10066:      */         else
/* 10067:      */         {
/* 10068:10741 */           result.amInfos_ = this.amInfosBuilder_.build();
/* 10069:      */         }
/* 10070:10743 */         if ((from_bitField0_ & 0x4000) == 16384) {
/* 10071:10744 */           to_bitField0_ |= 0x2000;
/* 10072:      */         }
/* 10073:10746 */         result.submitTime_ = this.submitTime_;
/* 10074:10747 */         if ((from_bitField0_ & 0x8000) == 32768) {
/* 10075:10748 */           to_bitField0_ |= 0x4000;
/* 10076:      */         }
/* 10077:10750 */         result.isUber_ = this.isUber_;
/* 10078:10751 */         result.bitField0_ = to_bitField0_;
/* 10079:10752 */         onBuilt();
/* 10080:10753 */         return result;
/* 10081:      */       }
/* 10082:      */       
/* 10083:      */       public Builder mergeFrom(Message other)
/* 10084:      */       {
/* 10085:10757 */         if ((other instanceof MRProtos.JobReportProto)) {
/* 10086:10758 */           return mergeFrom((MRProtos.JobReportProto)other);
/* 10087:      */         }
/* 10088:10760 */         super.mergeFrom(other);
/* 10089:10761 */         return this;
/* 10090:      */       }
/* 10091:      */       
/* 10092:      */       public Builder mergeFrom(MRProtos.JobReportProto other)
/* 10093:      */       {
/* 10094:10766 */         if (other == MRProtos.JobReportProto.getDefaultInstance()) {
/* 10095:10766 */           return this;
/* 10096:      */         }
/* 10097:10767 */         if (other.hasJobId()) {
/* 10098:10768 */           mergeJobId(other.getJobId());
/* 10099:      */         }
/* 10100:10770 */         if (other.hasJobState()) {
/* 10101:10771 */           setJobState(other.getJobState());
/* 10102:      */         }
/* 10103:10773 */         if (other.hasMapProgress()) {
/* 10104:10774 */           setMapProgress(other.getMapProgress());
/* 10105:      */         }
/* 10106:10776 */         if (other.hasReduceProgress()) {
/* 10107:10777 */           setReduceProgress(other.getReduceProgress());
/* 10108:      */         }
/* 10109:10779 */         if (other.hasCleanupProgress()) {
/* 10110:10780 */           setCleanupProgress(other.getCleanupProgress());
/* 10111:      */         }
/* 10112:10782 */         if (other.hasSetupProgress()) {
/* 10113:10783 */           setSetupProgress(other.getSetupProgress());
/* 10114:      */         }
/* 10115:10785 */         if (other.hasStartTime()) {
/* 10116:10786 */           setStartTime(other.getStartTime());
/* 10117:      */         }
/* 10118:10788 */         if (other.hasFinishTime()) {
/* 10119:10789 */           setFinishTime(other.getFinishTime());
/* 10120:      */         }
/* 10121:10791 */         if (other.hasUser())
/* 10122:      */         {
/* 10123:10792 */           this.bitField0_ |= 0x100;
/* 10124:10793 */           this.user_ = other.user_;
/* 10125:10794 */           onChanged();
/* 10126:      */         }
/* 10127:10796 */         if (other.hasJobName())
/* 10128:      */         {
/* 10129:10797 */           this.bitField0_ |= 0x200;
/* 10130:10798 */           this.jobName_ = other.jobName_;
/* 10131:10799 */           onChanged();
/* 10132:      */         }
/* 10133:10801 */         if (other.hasTrackingUrl())
/* 10134:      */         {
/* 10135:10802 */           this.bitField0_ |= 0x400;
/* 10136:10803 */           this.trackingUrl_ = other.trackingUrl_;
/* 10137:10804 */           onChanged();
/* 10138:      */         }
/* 10139:10806 */         if (other.hasDiagnostics())
/* 10140:      */         {
/* 10141:10807 */           this.bitField0_ |= 0x800;
/* 10142:10808 */           this.diagnostics_ = other.diagnostics_;
/* 10143:10809 */           onChanged();
/* 10144:      */         }
/* 10145:10811 */         if (other.hasJobFile())
/* 10146:      */         {
/* 10147:10812 */           this.bitField0_ |= 0x1000;
/* 10148:10813 */           this.jobFile_ = other.jobFile_;
/* 10149:10814 */           onChanged();
/* 10150:      */         }
/* 10151:10816 */         if (this.amInfosBuilder_ == null)
/* 10152:      */         {
/* 10153:10817 */           if (!other.amInfos_.isEmpty())
/* 10154:      */           {
/* 10155:10818 */             if (this.amInfos_.isEmpty())
/* 10156:      */             {
/* 10157:10819 */               this.amInfos_ = other.amInfos_;
/* 10158:10820 */               this.bitField0_ &= 0xFFFFDFFF;
/* 10159:      */             }
/* 10160:      */             else
/* 10161:      */             {
/* 10162:10822 */               ensureAmInfosIsMutable();
/* 10163:10823 */               this.amInfos_.addAll(other.amInfos_);
/* 10164:      */             }
/* 10165:10825 */             onChanged();
/* 10166:      */           }
/* 10167:      */         }
/* 10168:10828 */         else if (!other.amInfos_.isEmpty()) {
/* 10169:10829 */           if (this.amInfosBuilder_.isEmpty())
/* 10170:      */           {
/* 10171:10830 */             this.amInfosBuilder_.dispose();
/* 10172:10831 */             this.amInfosBuilder_ = null;
/* 10173:10832 */             this.amInfos_ = other.amInfos_;
/* 10174:10833 */             this.bitField0_ &= 0xFFFFDFFF;
/* 10175:10834 */             this.amInfosBuilder_ = (MRProtos.JobReportProto.alwaysUseFieldBuilders ? getAmInfosFieldBuilder() : null);
/* 10176:      */           }
/* 10177:      */           else
/* 10178:      */           {
/* 10179:10838 */             this.amInfosBuilder_.addAllMessages(other.amInfos_);
/* 10180:      */           }
/* 10181:      */         }
/* 10182:10842 */         if (other.hasSubmitTime()) {
/* 10183:10843 */           setSubmitTime(other.getSubmitTime());
/* 10184:      */         }
/* 10185:10845 */         if (other.hasIsUber()) {
/* 10186:10846 */           setIsUber(other.getIsUber());
/* 10187:      */         }
/* 10188:10848 */         mergeUnknownFields(other.getUnknownFields());
/* 10189:10849 */         return this;
/* 10190:      */       }
/* 10191:      */       
/* 10192:      */       public final boolean isInitialized()
/* 10193:      */       {
/* 10194:10853 */         return true;
/* 10195:      */       }
/* 10196:      */       
/* 10197:      */       public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry)
/* 10198:      */         throws IOException
/* 10199:      */       {
/* 10200:10860 */         MRProtos.JobReportProto parsedMessage = null;
/* 10201:      */         try
/* 10202:      */         {
/* 10203:10862 */           parsedMessage = (MRProtos.JobReportProto)MRProtos.JobReportProto.PARSER.parsePartialFrom(input, extensionRegistry);
/* 10204:      */         }
/* 10205:      */         catch (InvalidProtocolBufferException e)
/* 10206:      */         {
/* 10207:10864 */           parsedMessage = (MRProtos.JobReportProto)e.getUnfinishedMessage();
/* 10208:10865 */           throw e;
/* 10209:      */         }
/* 10210:      */         finally
/* 10211:      */         {
/* 10212:10867 */           if (parsedMessage != null) {
/* 10213:10868 */             mergeFrom(parsedMessage);
/* 10214:      */           }
/* 10215:      */         }
/* 10216:10871 */         return this;
/* 10217:      */       }
/* 10218:      */       
/* 10219:10876 */       private MRProtos.JobIdProto jobId_ = MRProtos.JobIdProto.getDefaultInstance();
/* 10220:      */       private SingleFieldBuilder<MRProtos.JobIdProto, MRProtos.JobIdProto.Builder, MRProtos.JobIdProtoOrBuilder> jobIdBuilder_;
/* 10221:      */       
/* 10222:      */       public boolean hasJobId()
/* 10223:      */       {
/* 10224:10883 */         return (this.bitField0_ & 0x1) == 1;
/* 10225:      */       }
/* 10226:      */       
/* 10227:      */       public MRProtos.JobIdProto getJobId()
/* 10228:      */       {
/* 10229:10889 */         if (this.jobIdBuilder_ == null) {
/* 10230:10890 */           return this.jobId_;
/* 10231:      */         }
/* 10232:10892 */         return (MRProtos.JobIdProto)this.jobIdBuilder_.getMessage();
/* 10233:      */       }
/* 10234:      */       
/* 10235:      */       public Builder setJobId(MRProtos.JobIdProto value)
/* 10236:      */       {
/* 10237:10899 */         if (this.jobIdBuilder_ == null)
/* 10238:      */         {
/* 10239:10900 */           if (value == null) {
/* 10240:10901 */             throw new NullPointerException();
/* 10241:      */           }
/* 10242:10903 */           this.jobId_ = value;
/* 10243:10904 */           onChanged();
/* 10244:      */         }
/* 10245:      */         else
/* 10246:      */         {
/* 10247:10906 */           this.jobIdBuilder_.setMessage(value);
/* 10248:      */         }
/* 10249:10908 */         this.bitField0_ |= 0x1;
/* 10250:10909 */         return this;
/* 10251:      */       }
/* 10252:      */       
/* 10253:      */       public Builder setJobId(MRProtos.JobIdProto.Builder builderForValue)
/* 10254:      */       {
/* 10255:10916 */         if (this.jobIdBuilder_ == null)
/* 10256:      */         {
/* 10257:10917 */           this.jobId_ = builderForValue.build();
/* 10258:10918 */           onChanged();
/* 10259:      */         }
/* 10260:      */         else
/* 10261:      */         {
/* 10262:10920 */           this.jobIdBuilder_.setMessage(builderForValue.build());
/* 10263:      */         }
/* 10264:10922 */         this.bitField0_ |= 0x1;
/* 10265:10923 */         return this;
/* 10266:      */       }
/* 10267:      */       
/* 10268:      */       public Builder mergeJobId(MRProtos.JobIdProto value)
/* 10269:      */       {
/* 10270:10929 */         if (this.jobIdBuilder_ == null)
/* 10271:      */         {
/* 10272:10930 */           if (((this.bitField0_ & 0x1) == 1) && (this.jobId_ != MRProtos.JobIdProto.getDefaultInstance())) {
/* 10273:10932 */             this.jobId_ = MRProtos.JobIdProto.newBuilder(this.jobId_).mergeFrom(value).buildPartial();
/* 10274:      */           } else {
/* 10275:10935 */             this.jobId_ = value;
/* 10276:      */           }
/* 10277:10937 */           onChanged();
/* 10278:      */         }
/* 10279:      */         else
/* 10280:      */         {
/* 10281:10939 */           this.jobIdBuilder_.mergeFrom(value);
/* 10282:      */         }
/* 10283:10941 */         this.bitField0_ |= 0x1;
/* 10284:10942 */         return this;
/* 10285:      */       }
/* 10286:      */       
/* 10287:      */       public Builder clearJobId()
/* 10288:      */       {
/* 10289:10948 */         if (this.jobIdBuilder_ == null)
/* 10290:      */         {
/* 10291:10949 */           this.jobId_ = MRProtos.JobIdProto.getDefaultInstance();
/* 10292:10950 */           onChanged();
/* 10293:      */         }
/* 10294:      */         else
/* 10295:      */         {
/* 10296:10952 */           this.jobIdBuilder_.clear();
/* 10297:      */         }
/* 10298:10954 */         this.bitField0_ &= 0xFFFFFFFE;
/* 10299:10955 */         return this;
/* 10300:      */       }
/* 10301:      */       
/* 10302:      */       public MRProtos.JobIdProto.Builder getJobIdBuilder()
/* 10303:      */       {
/* 10304:10961 */         this.bitField0_ |= 0x1;
/* 10305:10962 */         onChanged();
/* 10306:10963 */         return (MRProtos.JobIdProto.Builder)getJobIdFieldBuilder().getBuilder();
/* 10307:      */       }
/* 10308:      */       
/* 10309:      */       public MRProtos.JobIdProtoOrBuilder getJobIdOrBuilder()
/* 10310:      */       {
/* 10311:10969 */         if (this.jobIdBuilder_ != null) {
/* 10312:10970 */           return (MRProtos.JobIdProtoOrBuilder)this.jobIdBuilder_.getMessageOrBuilder();
/* 10313:      */         }
/* 10314:10972 */         return this.jobId_;
/* 10315:      */       }
/* 10316:      */       
/* 10317:      */       private SingleFieldBuilder<MRProtos.JobIdProto, MRProtos.JobIdProto.Builder, MRProtos.JobIdProtoOrBuilder> getJobIdFieldBuilder()
/* 10318:      */       {
/* 10319:10981 */         if (this.jobIdBuilder_ == null)
/* 10320:      */         {
/* 10321:10982 */           this.jobIdBuilder_ = new SingleFieldBuilder(this.jobId_, getParentForChildren(), isClean());
/* 10322:      */           
/* 10323:      */ 
/* 10324:      */ 
/* 10325:      */ 
/* 10326:10987 */           this.jobId_ = null;
/* 10327:      */         }
/* 10328:10989 */         return this.jobIdBuilder_;
/* 10329:      */       }
/* 10330:      */       
/* 10331:10993 */       private MRProtos.JobStateProto jobState_ = MRProtos.JobStateProto.J_NEW;
/* 10332:      */       private float mapProgress_;
/* 10333:      */       private float reduceProgress_;
/* 10334:      */       private float cleanupProgress_;
/* 10335:      */       private float setupProgress_;
/* 10336:      */       private long startTime_;
/* 10337:      */       private long finishTime_;
/* 10338:      */       
/* 10339:      */       public boolean hasJobState()
/* 10340:      */       {
/* 10341:10998 */         return (this.bitField0_ & 0x2) == 2;
/* 10342:      */       }
/* 10343:      */       
/* 10344:      */       public MRProtos.JobStateProto getJobState()
/* 10345:      */       {
/* 10346:11004 */         return this.jobState_;
/* 10347:      */       }
/* 10348:      */       
/* 10349:      */       public Builder setJobState(MRProtos.JobStateProto value)
/* 10350:      */       {
/* 10351:11010 */         if (value == null) {
/* 10352:11011 */           throw new NullPointerException();
/* 10353:      */         }
/* 10354:11013 */         this.bitField0_ |= 0x2;
/* 10355:11014 */         this.jobState_ = value;
/* 10356:11015 */         onChanged();
/* 10357:11016 */         return this;
/* 10358:      */       }
/* 10359:      */       
/* 10360:      */       public Builder clearJobState()
/* 10361:      */       {
/* 10362:11022 */         this.bitField0_ &= 0xFFFFFFFD;
/* 10363:11023 */         this.jobState_ = MRProtos.JobStateProto.J_NEW;
/* 10364:11024 */         onChanged();
/* 10365:11025 */         return this;
/* 10366:      */       }
/* 10367:      */       
/* 10368:      */       public boolean hasMapProgress()
/* 10369:      */       {
/* 10370:11034 */         return (this.bitField0_ & 0x4) == 4;
/* 10371:      */       }
/* 10372:      */       
/* 10373:      */       public float getMapProgress()
/* 10374:      */       {
/* 10375:11040 */         return this.mapProgress_;
/* 10376:      */       }
/* 10377:      */       
/* 10378:      */       public Builder setMapProgress(float value)
/* 10379:      */       {
/* 10380:11046 */         this.bitField0_ |= 0x4;
/* 10381:11047 */         this.mapProgress_ = value;
/* 10382:11048 */         onChanged();
/* 10383:11049 */         return this;
/* 10384:      */       }
/* 10385:      */       
/* 10386:      */       public Builder clearMapProgress()
/* 10387:      */       {
/* 10388:11055 */         this.bitField0_ &= 0xFFFFFFFB;
/* 10389:11056 */         this.mapProgress_ = 0.0F;
/* 10390:11057 */         onChanged();
/* 10391:11058 */         return this;
/* 10392:      */       }
/* 10393:      */       
/* 10394:      */       public boolean hasReduceProgress()
/* 10395:      */       {
/* 10396:11067 */         return (this.bitField0_ & 0x8) == 8;
/* 10397:      */       }
/* 10398:      */       
/* 10399:      */       public float getReduceProgress()
/* 10400:      */       {
/* 10401:11073 */         return this.reduceProgress_;
/* 10402:      */       }
/* 10403:      */       
/* 10404:      */       public Builder setReduceProgress(float value)
/* 10405:      */       {
/* 10406:11079 */         this.bitField0_ |= 0x8;
/* 10407:11080 */         this.reduceProgress_ = value;
/* 10408:11081 */         onChanged();
/* 10409:11082 */         return this;
/* 10410:      */       }
/* 10411:      */       
/* 10412:      */       public Builder clearReduceProgress()
/* 10413:      */       {
/* 10414:11088 */         this.bitField0_ &= 0xFFFFFFF7;
/* 10415:11089 */         this.reduceProgress_ = 0.0F;
/* 10416:11090 */         onChanged();
/* 10417:11091 */         return this;
/* 10418:      */       }
/* 10419:      */       
/* 10420:      */       public boolean hasCleanupProgress()
/* 10421:      */       {
/* 10422:11100 */         return (this.bitField0_ & 0x10) == 16;
/* 10423:      */       }
/* 10424:      */       
/* 10425:      */       public float getCleanupProgress()
/* 10426:      */       {
/* 10427:11106 */         return this.cleanupProgress_;
/* 10428:      */       }
/* 10429:      */       
/* 10430:      */       public Builder setCleanupProgress(float value)
/* 10431:      */       {
/* 10432:11112 */         this.bitField0_ |= 0x10;
/* 10433:11113 */         this.cleanupProgress_ = value;
/* 10434:11114 */         onChanged();
/* 10435:11115 */         return this;
/* 10436:      */       }
/* 10437:      */       
/* 10438:      */       public Builder clearCleanupProgress()
/* 10439:      */       {
/* 10440:11121 */         this.bitField0_ &= 0xFFFFFFEF;
/* 10441:11122 */         this.cleanupProgress_ = 0.0F;
/* 10442:11123 */         onChanged();
/* 10443:11124 */         return this;
/* 10444:      */       }
/* 10445:      */       
/* 10446:      */       public boolean hasSetupProgress()
/* 10447:      */       {
/* 10448:11133 */         return (this.bitField0_ & 0x20) == 32;
/* 10449:      */       }
/* 10450:      */       
/* 10451:      */       public float getSetupProgress()
/* 10452:      */       {
/* 10453:11139 */         return this.setupProgress_;
/* 10454:      */       }
/* 10455:      */       
/* 10456:      */       public Builder setSetupProgress(float value)
/* 10457:      */       {
/* 10458:11145 */         this.bitField0_ |= 0x20;
/* 10459:11146 */         this.setupProgress_ = value;
/* 10460:11147 */         onChanged();
/* 10461:11148 */         return this;
/* 10462:      */       }
/* 10463:      */       
/* 10464:      */       public Builder clearSetupProgress()
/* 10465:      */       {
/* 10466:11154 */         this.bitField0_ &= 0xFFFFFFDF;
/* 10467:11155 */         this.setupProgress_ = 0.0F;
/* 10468:11156 */         onChanged();
/* 10469:11157 */         return this;
/* 10470:      */       }
/* 10471:      */       
/* 10472:      */       public boolean hasStartTime()
/* 10473:      */       {
/* 10474:11166 */         return (this.bitField0_ & 0x40) == 64;
/* 10475:      */       }
/* 10476:      */       
/* 10477:      */       public long getStartTime()
/* 10478:      */       {
/* 10479:11172 */         return this.startTime_;
/* 10480:      */       }
/* 10481:      */       
/* 10482:      */       public Builder setStartTime(long value)
/* 10483:      */       {
/* 10484:11178 */         this.bitField0_ |= 0x40;
/* 10485:11179 */         this.startTime_ = value;
/* 10486:11180 */         onChanged();
/* 10487:11181 */         return this;
/* 10488:      */       }
/* 10489:      */       
/* 10490:      */       public Builder clearStartTime()
/* 10491:      */       {
/* 10492:11187 */         this.bitField0_ &= 0xFFFFFFBF;
/* 10493:11188 */         this.startTime_ = 0L;
/* 10494:11189 */         onChanged();
/* 10495:11190 */         return this;
/* 10496:      */       }
/* 10497:      */       
/* 10498:      */       public boolean hasFinishTime()
/* 10499:      */       {
/* 10500:11199 */         return (this.bitField0_ & 0x80) == 128;
/* 10501:      */       }
/* 10502:      */       
/* 10503:      */       public long getFinishTime()
/* 10504:      */       {
/* 10505:11205 */         return this.finishTime_;
/* 10506:      */       }
/* 10507:      */       
/* 10508:      */       public Builder setFinishTime(long value)
/* 10509:      */       {
/* 10510:11211 */         this.bitField0_ |= 0x80;
/* 10511:11212 */         this.finishTime_ = value;
/* 10512:11213 */         onChanged();
/* 10513:11214 */         return this;
/* 10514:      */       }
/* 10515:      */       
/* 10516:      */       public Builder clearFinishTime()
/* 10517:      */       {
/* 10518:11220 */         this.bitField0_ &= 0xFFFFFF7F;
/* 10519:11221 */         this.finishTime_ = 0L;
/* 10520:11222 */         onChanged();
/* 10521:11223 */         return this;
/* 10522:      */       }
/* 10523:      */       
/* 10524:11227 */       private Object user_ = "";
/* 10525:      */       
/* 10526:      */       public boolean hasUser()
/* 10527:      */       {
/* 10528:11232 */         return (this.bitField0_ & 0x100) == 256;
/* 10529:      */       }
/* 10530:      */       
/* 10531:      */       public String getUser()
/* 10532:      */       {
/* 10533:11238 */         Object ref = this.user_;
/* 10534:11239 */         if (!(ref instanceof String))
/* 10535:      */         {
/* 10536:11240 */           String s = ((ByteString)ref).toStringUtf8();
/* 10537:      */           
/* 10538:11242 */           this.user_ = s;
/* 10539:11243 */           return s;
/* 10540:      */         }
/* 10541:11245 */         return (String)ref;
/* 10542:      */       }
/* 10543:      */       
/* 10544:      */       public ByteString getUserBytes()
/* 10545:      */       {
/* 10546:11253 */         Object ref = this.user_;
/* 10547:11254 */         if ((ref instanceof String))
/* 10548:      */         {
/* 10549:11255 */           ByteString b = ByteString.copyFromUtf8((String)ref);
/* 10550:      */           
/* 10551:      */ 
/* 10552:11258 */           this.user_ = b;
/* 10553:11259 */           return b;
/* 10554:      */         }
/* 10555:11261 */         return (ByteString)ref;
/* 10556:      */       }
/* 10557:      */       
/* 10558:      */       public Builder setUser(String value)
/* 10559:      */       {
/* 10560:11269 */         if (value == null) {
/* 10561:11270 */           throw new NullPointerException();
/* 10562:      */         }
/* 10563:11272 */         this.bitField0_ |= 0x100;
/* 10564:11273 */         this.user_ = value;
/* 10565:11274 */         onChanged();
/* 10566:11275 */         return this;
/* 10567:      */       }
/* 10568:      */       
/* 10569:      */       public Builder clearUser()
/* 10570:      */       {
/* 10571:11281 */         this.bitField0_ &= 0xFFFFFEFF;
/* 10572:11282 */         this.user_ = MRProtos.JobReportProto.getDefaultInstance().getUser();
/* 10573:11283 */         onChanged();
/* 10574:11284 */         return this;
/* 10575:      */       }
/* 10576:      */       
/* 10577:      */       public Builder setUserBytes(ByteString value)
/* 10578:      */       {
/* 10579:11291 */         if (value == null) {
/* 10580:11292 */           throw new NullPointerException();
/* 10581:      */         }
/* 10582:11294 */         this.bitField0_ |= 0x100;
/* 10583:11295 */         this.user_ = value;
/* 10584:11296 */         onChanged();
/* 10585:11297 */         return this;
/* 10586:      */       }
/* 10587:      */       
/* 10588:11301 */       private Object jobName_ = "";
/* 10589:      */       
/* 10590:      */       public boolean hasJobName()
/* 10591:      */       {
/* 10592:11306 */         return (this.bitField0_ & 0x200) == 512;
/* 10593:      */       }
/* 10594:      */       
/* 10595:      */       public String getJobName()
/* 10596:      */       {
/* 10597:11312 */         Object ref = this.jobName_;
/* 10598:11313 */         if (!(ref instanceof String))
/* 10599:      */         {
/* 10600:11314 */           String s = ((ByteString)ref).toStringUtf8();
/* 10601:      */           
/* 10602:11316 */           this.jobName_ = s;
/* 10603:11317 */           return s;
/* 10604:      */         }
/* 10605:11319 */         return (String)ref;
/* 10606:      */       }
/* 10607:      */       
/* 10608:      */       public ByteString getJobNameBytes()
/* 10609:      */       {
/* 10610:11327 */         Object ref = this.jobName_;
/* 10611:11328 */         if ((ref instanceof String))
/* 10612:      */         {
/* 10613:11329 */           ByteString b = ByteString.copyFromUtf8((String)ref);
/* 10614:      */           
/* 10615:      */ 
/* 10616:11332 */           this.jobName_ = b;
/* 10617:11333 */           return b;
/* 10618:      */         }
/* 10619:11335 */         return (ByteString)ref;
/* 10620:      */       }
/* 10621:      */       
/* 10622:      */       public Builder setJobName(String value)
/* 10623:      */       {
/* 10624:11343 */         if (value == null) {
/* 10625:11344 */           throw new NullPointerException();
/* 10626:      */         }
/* 10627:11346 */         this.bitField0_ |= 0x200;
/* 10628:11347 */         this.jobName_ = value;
/* 10629:11348 */         onChanged();
/* 10630:11349 */         return this;
/* 10631:      */       }
/* 10632:      */       
/* 10633:      */       public Builder clearJobName()
/* 10634:      */       {
/* 10635:11355 */         this.bitField0_ &= 0xFFFFFDFF;
/* 10636:11356 */         this.jobName_ = MRProtos.JobReportProto.getDefaultInstance().getJobName();
/* 10637:11357 */         onChanged();
/* 10638:11358 */         return this;
/* 10639:      */       }
/* 10640:      */       
/* 10641:      */       public Builder setJobNameBytes(ByteString value)
/* 10642:      */       {
/* 10643:11365 */         if (value == null) {
/* 10644:11366 */           throw new NullPointerException();
/* 10645:      */         }
/* 10646:11368 */         this.bitField0_ |= 0x200;
/* 10647:11369 */         this.jobName_ = value;
/* 10648:11370 */         onChanged();
/* 10649:11371 */         return this;
/* 10650:      */       }
/* 10651:      */       
/* 10652:11375 */       private Object trackingUrl_ = "";
/* 10653:      */       
/* 10654:      */       public boolean hasTrackingUrl()
/* 10655:      */       {
/* 10656:11380 */         return (this.bitField0_ & 0x400) == 1024;
/* 10657:      */       }
/* 10658:      */       
/* 10659:      */       public String getTrackingUrl()
/* 10660:      */       {
/* 10661:11386 */         Object ref = this.trackingUrl_;
/* 10662:11387 */         if (!(ref instanceof String))
/* 10663:      */         {
/* 10664:11388 */           String s = ((ByteString)ref).toStringUtf8();
/* 10665:      */           
/* 10666:11390 */           this.trackingUrl_ = s;
/* 10667:11391 */           return s;
/* 10668:      */         }
/* 10669:11393 */         return (String)ref;
/* 10670:      */       }
/* 10671:      */       
/* 10672:      */       public ByteString getTrackingUrlBytes()
/* 10673:      */       {
/* 10674:11401 */         Object ref = this.trackingUrl_;
/* 10675:11402 */         if ((ref instanceof String))
/* 10676:      */         {
/* 10677:11403 */           ByteString b = ByteString.copyFromUtf8((String)ref);
/* 10678:      */           
/* 10679:      */ 
/* 10680:11406 */           this.trackingUrl_ = b;
/* 10681:11407 */           return b;
/* 10682:      */         }
/* 10683:11409 */         return (ByteString)ref;
/* 10684:      */       }
/* 10685:      */       
/* 10686:      */       public Builder setTrackingUrl(String value)
/* 10687:      */       {
/* 10688:11417 */         if (value == null) {
/* 10689:11418 */           throw new NullPointerException();
/* 10690:      */         }
/* 10691:11420 */         this.bitField0_ |= 0x400;
/* 10692:11421 */         this.trackingUrl_ = value;
/* 10693:11422 */         onChanged();
/* 10694:11423 */         return this;
/* 10695:      */       }
/* 10696:      */       
/* 10697:      */       public Builder clearTrackingUrl()
/* 10698:      */       {
/* 10699:11429 */         this.bitField0_ &= 0xFFFFFBFF;
/* 10700:11430 */         this.trackingUrl_ = MRProtos.JobReportProto.getDefaultInstance().getTrackingUrl();
/* 10701:11431 */         onChanged();
/* 10702:11432 */         return this;
/* 10703:      */       }
/* 10704:      */       
/* 10705:      */       public Builder setTrackingUrlBytes(ByteString value)
/* 10706:      */       {
/* 10707:11439 */         if (value == null) {
/* 10708:11440 */           throw new NullPointerException();
/* 10709:      */         }
/* 10710:11442 */         this.bitField0_ |= 0x400;
/* 10711:11443 */         this.trackingUrl_ = value;
/* 10712:11444 */         onChanged();
/* 10713:11445 */         return this;
/* 10714:      */       }
/* 10715:      */       
/* 10716:11449 */       private Object diagnostics_ = "";
/* 10717:      */       
/* 10718:      */       public boolean hasDiagnostics()
/* 10719:      */       {
/* 10720:11454 */         return (this.bitField0_ & 0x800) == 2048;
/* 10721:      */       }
/* 10722:      */       
/* 10723:      */       public String getDiagnostics()
/* 10724:      */       {
/* 10725:11460 */         Object ref = this.diagnostics_;
/* 10726:11461 */         if (!(ref instanceof String))
/* 10727:      */         {
/* 10728:11462 */           String s = ((ByteString)ref).toStringUtf8();
/* 10729:      */           
/* 10730:11464 */           this.diagnostics_ = s;
/* 10731:11465 */           return s;
/* 10732:      */         }
/* 10733:11467 */         return (String)ref;
/* 10734:      */       }
/* 10735:      */       
/* 10736:      */       public ByteString getDiagnosticsBytes()
/* 10737:      */       {
/* 10738:11475 */         Object ref = this.diagnostics_;
/* 10739:11476 */         if ((ref instanceof String))
/* 10740:      */         {
/* 10741:11477 */           ByteString b = ByteString.copyFromUtf8((String)ref);
/* 10742:      */           
/* 10743:      */ 
/* 10744:11480 */           this.diagnostics_ = b;
/* 10745:11481 */           return b;
/* 10746:      */         }
/* 10747:11483 */         return (ByteString)ref;
/* 10748:      */       }
/* 10749:      */       
/* 10750:      */       public Builder setDiagnostics(String value)
/* 10751:      */       {
/* 10752:11491 */         if (value == null) {
/* 10753:11492 */           throw new NullPointerException();
/* 10754:      */         }
/* 10755:11494 */         this.bitField0_ |= 0x800;
/* 10756:11495 */         this.diagnostics_ = value;
/* 10757:11496 */         onChanged();
/* 10758:11497 */         return this;
/* 10759:      */       }
/* 10760:      */       
/* 10761:      */       public Builder clearDiagnostics()
/* 10762:      */       {
/* 10763:11503 */         this.bitField0_ &= 0xFFFFF7FF;
/* 10764:11504 */         this.diagnostics_ = MRProtos.JobReportProto.getDefaultInstance().getDiagnostics();
/* 10765:11505 */         onChanged();
/* 10766:11506 */         return this;
/* 10767:      */       }
/* 10768:      */       
/* 10769:      */       public Builder setDiagnosticsBytes(ByteString value)
/* 10770:      */       {
/* 10771:11513 */         if (value == null) {
/* 10772:11514 */           throw new NullPointerException();
/* 10773:      */         }
/* 10774:11516 */         this.bitField0_ |= 0x800;
/* 10775:11517 */         this.diagnostics_ = value;
/* 10776:11518 */         onChanged();
/* 10777:11519 */         return this;
/* 10778:      */       }
/* 10779:      */       
/* 10780:11523 */       private Object jobFile_ = "";
/* 10781:      */       
/* 10782:      */       public boolean hasJobFile()
/* 10783:      */       {
/* 10784:11528 */         return (this.bitField0_ & 0x1000) == 4096;
/* 10785:      */       }
/* 10786:      */       
/* 10787:      */       public String getJobFile()
/* 10788:      */       {
/* 10789:11534 */         Object ref = this.jobFile_;
/* 10790:11535 */         if (!(ref instanceof String))
/* 10791:      */         {
/* 10792:11536 */           String s = ((ByteString)ref).toStringUtf8();
/* 10793:      */           
/* 10794:11538 */           this.jobFile_ = s;
/* 10795:11539 */           return s;
/* 10796:      */         }
/* 10797:11541 */         return (String)ref;
/* 10798:      */       }
/* 10799:      */       
/* 10800:      */       public ByteString getJobFileBytes()
/* 10801:      */       {
/* 10802:11549 */         Object ref = this.jobFile_;
/* 10803:11550 */         if ((ref instanceof String))
/* 10804:      */         {
/* 10805:11551 */           ByteString b = ByteString.copyFromUtf8((String)ref);
/* 10806:      */           
/* 10807:      */ 
/* 10808:11554 */           this.jobFile_ = b;
/* 10809:11555 */           return b;
/* 10810:      */         }
/* 10811:11557 */         return (ByteString)ref;
/* 10812:      */       }
/* 10813:      */       
/* 10814:      */       public Builder setJobFile(String value)
/* 10815:      */       {
/* 10816:11565 */         if (value == null) {
/* 10817:11566 */           throw new NullPointerException();
/* 10818:      */         }
/* 10819:11568 */         this.bitField0_ |= 0x1000;
/* 10820:11569 */         this.jobFile_ = value;
/* 10821:11570 */         onChanged();
/* 10822:11571 */         return this;
/* 10823:      */       }
/* 10824:      */       
/* 10825:      */       public Builder clearJobFile()
/* 10826:      */       {
/* 10827:11577 */         this.bitField0_ &= 0xFFFFEFFF;
/* 10828:11578 */         this.jobFile_ = MRProtos.JobReportProto.getDefaultInstance().getJobFile();
/* 10829:11579 */         onChanged();
/* 10830:11580 */         return this;
/* 10831:      */       }
/* 10832:      */       
/* 10833:      */       public Builder setJobFileBytes(ByteString value)
/* 10834:      */       {
/* 10835:11587 */         if (value == null) {
/* 10836:11588 */           throw new NullPointerException();
/* 10837:      */         }
/* 10838:11590 */         this.bitField0_ |= 0x1000;
/* 10839:11591 */         this.jobFile_ = value;
/* 10840:11592 */         onChanged();
/* 10841:11593 */         return this;
/* 10842:      */       }
/* 10843:      */       
/* 10844:11597 */       private List<MRProtos.AMInfoProto> amInfos_ = Collections.emptyList();
/* 10845:      */       private RepeatedFieldBuilder<MRProtos.AMInfoProto, MRProtos.AMInfoProto.Builder, MRProtos.AMInfoProtoOrBuilder> amInfosBuilder_;
/* 10846:      */       private long submitTime_;
/* 10847:      */       private boolean isUber_;
/* 10848:      */       
/* 10849:      */       private void ensureAmInfosIsMutable()
/* 10850:      */       {
/* 10851:11600 */         if ((this.bitField0_ & 0x2000) != 8192)
/* 10852:      */         {
/* 10853:11601 */           this.amInfos_ = new ArrayList(this.amInfos_);
/* 10854:11602 */           this.bitField0_ |= 0x2000;
/* 10855:      */         }
/* 10856:      */       }
/* 10857:      */       
/* 10858:      */       public List<MRProtos.AMInfoProto> getAmInfosList()
/* 10859:      */       {
/* 10860:11613 */         if (this.amInfosBuilder_ == null) {
/* 10861:11614 */           return Collections.unmodifiableList(this.amInfos_);
/* 10862:      */         }
/* 10863:11616 */         return this.amInfosBuilder_.getMessageList();
/* 10864:      */       }
/* 10865:      */       
/* 10866:      */       public int getAmInfosCount()
/* 10867:      */       {
/* 10868:11623 */         if (this.amInfosBuilder_ == null) {
/* 10869:11624 */           return this.amInfos_.size();
/* 10870:      */         }
/* 10871:11626 */         return this.amInfosBuilder_.getCount();
/* 10872:      */       }
/* 10873:      */       
/* 10874:      */       public MRProtos.AMInfoProto getAmInfos(int index)
/* 10875:      */       {
/* 10876:11633 */         if (this.amInfosBuilder_ == null) {
/* 10877:11634 */           return (MRProtos.AMInfoProto)this.amInfos_.get(index);
/* 10878:      */         }
/* 10879:11636 */         return (MRProtos.AMInfoProto)this.amInfosBuilder_.getMessage(index);
/* 10880:      */       }
/* 10881:      */       
/* 10882:      */       public Builder setAmInfos(int index, MRProtos.AMInfoProto value)
/* 10883:      */       {
/* 10884:11644 */         if (this.amInfosBuilder_ == null)
/* 10885:      */         {
/* 10886:11645 */           if (value == null) {
/* 10887:11646 */             throw new NullPointerException();
/* 10888:      */           }
/* 10889:11648 */           ensureAmInfosIsMutable();
/* 10890:11649 */           this.amInfos_.set(index, value);
/* 10891:11650 */           onChanged();
/* 10892:      */         }
/* 10893:      */         else
/* 10894:      */         {
/* 10895:11652 */           this.amInfosBuilder_.setMessage(index, value);
/* 10896:      */         }
/* 10897:11654 */         return this;
/* 10898:      */       }
/* 10899:      */       
/* 10900:      */       public Builder setAmInfos(int index, MRProtos.AMInfoProto.Builder builderForValue)
/* 10901:      */       {
/* 10902:11661 */         if (this.amInfosBuilder_ == null)
/* 10903:      */         {
/* 10904:11662 */           ensureAmInfosIsMutable();
/* 10905:11663 */           this.amInfos_.set(index, builderForValue.build());
/* 10906:11664 */           onChanged();
/* 10907:      */         }
/* 10908:      */         else
/* 10909:      */         {
/* 10910:11666 */           this.amInfosBuilder_.setMessage(index, builderForValue.build());
/* 10911:      */         }
/* 10912:11668 */         return this;
/* 10913:      */       }
/* 10914:      */       
/* 10915:      */       public Builder addAmInfos(MRProtos.AMInfoProto value)
/* 10916:      */       {
/* 10917:11674 */         if (this.amInfosBuilder_ == null)
/* 10918:      */         {
/* 10919:11675 */           if (value == null) {
/* 10920:11676 */             throw new NullPointerException();
/* 10921:      */           }
/* 10922:11678 */           ensureAmInfosIsMutable();
/* 10923:11679 */           this.amInfos_.add(value);
/* 10924:11680 */           onChanged();
/* 10925:      */         }
/* 10926:      */         else
/* 10927:      */         {
/* 10928:11682 */           this.amInfosBuilder_.addMessage(value);
/* 10929:      */         }
/* 10930:11684 */         return this;
/* 10931:      */       }
/* 10932:      */       
/* 10933:      */       public Builder addAmInfos(int index, MRProtos.AMInfoProto value)
/* 10934:      */       {
/* 10935:11691 */         if (this.amInfosBuilder_ == null)
/* 10936:      */         {
/* 10937:11692 */           if (value == null) {
/* 10938:11693 */             throw new NullPointerException();
/* 10939:      */           }
/* 10940:11695 */           ensureAmInfosIsMutable();
/* 10941:11696 */           this.amInfos_.add(index, value);
/* 10942:11697 */           onChanged();
/* 10943:      */         }
/* 10944:      */         else
/* 10945:      */         {
/* 10946:11699 */           this.amInfosBuilder_.addMessage(index, value);
/* 10947:      */         }
/* 10948:11701 */         return this;
/* 10949:      */       }
/* 10950:      */       
/* 10951:      */       public Builder addAmInfos(MRProtos.AMInfoProto.Builder builderForValue)
/* 10952:      */       {
/* 10953:11708 */         if (this.amInfosBuilder_ == null)
/* 10954:      */         {
/* 10955:11709 */           ensureAmInfosIsMutable();
/* 10956:11710 */           this.amInfos_.add(builderForValue.build());
/* 10957:11711 */           onChanged();
/* 10958:      */         }
/* 10959:      */         else
/* 10960:      */         {
/* 10961:11713 */           this.amInfosBuilder_.addMessage(builderForValue.build());
/* 10962:      */         }
/* 10963:11715 */         return this;
/* 10964:      */       }
/* 10965:      */       
/* 10966:      */       public Builder addAmInfos(int index, MRProtos.AMInfoProto.Builder builderForValue)
/* 10967:      */       {
/* 10968:11722 */         if (this.amInfosBuilder_ == null)
/* 10969:      */         {
/* 10970:11723 */           ensureAmInfosIsMutable();
/* 10971:11724 */           this.amInfos_.add(index, builderForValue.build());
/* 10972:11725 */           onChanged();
/* 10973:      */         }
/* 10974:      */         else
/* 10975:      */         {
/* 10976:11727 */           this.amInfosBuilder_.addMessage(index, builderForValue.build());
/* 10977:      */         }
/* 10978:11729 */         return this;
/* 10979:      */       }
/* 10980:      */       
/* 10981:      */       public Builder addAllAmInfos(Iterable<? extends MRProtos.AMInfoProto> values)
/* 10982:      */       {
/* 10983:11736 */         if (this.amInfosBuilder_ == null)
/* 10984:      */         {
/* 10985:11737 */           ensureAmInfosIsMutable();
/* 10986:11738 */           GeneratedMessage.Builder.addAll(values, this.amInfos_);
/* 10987:11739 */           onChanged();
/* 10988:      */         }
/* 10989:      */         else
/* 10990:      */         {
/* 10991:11741 */           this.amInfosBuilder_.addAllMessages(values);
/* 10992:      */         }
/* 10993:11743 */         return this;
/* 10994:      */       }
/* 10995:      */       
/* 10996:      */       public Builder clearAmInfos()
/* 10997:      */       {
/* 10998:11749 */         if (this.amInfosBuilder_ == null)
/* 10999:      */         {
/* 11000:11750 */           this.amInfos_ = Collections.emptyList();
/* 11001:11751 */           this.bitField0_ &= 0xFFFFDFFF;
/* 11002:11752 */           onChanged();
/* 11003:      */         }
/* 11004:      */         else
/* 11005:      */         {
/* 11006:11754 */           this.amInfosBuilder_.clear();
/* 11007:      */         }
/* 11008:11756 */         return this;
/* 11009:      */       }
/* 11010:      */       
/* 11011:      */       public Builder removeAmInfos(int index)
/* 11012:      */       {
/* 11013:11762 */         if (this.amInfosBuilder_ == null)
/* 11014:      */         {
/* 11015:11763 */           ensureAmInfosIsMutable();
/* 11016:11764 */           this.amInfos_.remove(index);
/* 11017:11765 */           onChanged();
/* 11018:      */         }
/* 11019:      */         else
/* 11020:      */         {
/* 11021:11767 */           this.amInfosBuilder_.remove(index);
/* 11022:      */         }
/* 11023:11769 */         return this;
/* 11024:      */       }
/* 11025:      */       
/* 11026:      */       public MRProtos.AMInfoProto.Builder getAmInfosBuilder(int index)
/* 11027:      */       {
/* 11028:11776 */         return (MRProtos.AMInfoProto.Builder)getAmInfosFieldBuilder().getBuilder(index);
/* 11029:      */       }
/* 11030:      */       
/* 11031:      */       public MRProtos.AMInfoProtoOrBuilder getAmInfosOrBuilder(int index)
/* 11032:      */       {
/* 11033:11783 */         if (this.amInfosBuilder_ == null) {
/* 11034:11784 */           return (MRProtos.AMInfoProtoOrBuilder)this.amInfos_.get(index);
/* 11035:      */         }
/* 11036:11785 */         return (MRProtos.AMInfoProtoOrBuilder)this.amInfosBuilder_.getMessageOrBuilder(index);
/* 11037:      */       }
/* 11038:      */       
/* 11039:      */       public List<? extends MRProtos.AMInfoProtoOrBuilder> getAmInfosOrBuilderList()
/* 11040:      */       {
/* 11041:11793 */         if (this.amInfosBuilder_ != null) {
/* 11042:11794 */           return this.amInfosBuilder_.getMessageOrBuilderList();
/* 11043:      */         }
/* 11044:11796 */         return Collections.unmodifiableList(this.amInfos_);
/* 11045:      */       }
/* 11046:      */       
/* 11047:      */       public MRProtos.AMInfoProto.Builder addAmInfosBuilder()
/* 11048:      */       {
/* 11049:11803 */         return (MRProtos.AMInfoProto.Builder)getAmInfosFieldBuilder().addBuilder(MRProtos.AMInfoProto.getDefaultInstance());
/* 11050:      */       }
/* 11051:      */       
/* 11052:      */       public MRProtos.AMInfoProto.Builder addAmInfosBuilder(int index)
/* 11053:      */       {
/* 11054:11811 */         return (MRProtos.AMInfoProto.Builder)getAmInfosFieldBuilder().addBuilder(index, MRProtos.AMInfoProto.getDefaultInstance());
/* 11055:      */       }
/* 11056:      */       
/* 11057:      */       public List<MRProtos.AMInfoProto.Builder> getAmInfosBuilderList()
/* 11058:      */       {
/* 11059:11819 */         return getAmInfosFieldBuilder().getBuilderList();
/* 11060:      */       }
/* 11061:      */       
/* 11062:      */       private RepeatedFieldBuilder<MRProtos.AMInfoProto, MRProtos.AMInfoProto.Builder, MRProtos.AMInfoProtoOrBuilder> getAmInfosFieldBuilder()
/* 11063:      */       {
/* 11064:11824 */         if (this.amInfosBuilder_ == null)
/* 11065:      */         {
/* 11066:11825 */           this.amInfosBuilder_ = new RepeatedFieldBuilder(this.amInfos_, (this.bitField0_ & 0x2000) == 8192, getParentForChildren(), isClean());
/* 11067:      */           
/* 11068:      */ 
/* 11069:      */ 
/* 11070:      */ 
/* 11071:      */ 
/* 11072:11831 */           this.amInfos_ = null;
/* 11073:      */         }
/* 11074:11833 */         return this.amInfosBuilder_;
/* 11075:      */       }
/* 11076:      */       
/* 11077:      */       public boolean hasSubmitTime()
/* 11078:      */       {
/* 11079:11842 */         return (this.bitField0_ & 0x4000) == 16384;
/* 11080:      */       }
/* 11081:      */       
/* 11082:      */       public long getSubmitTime()
/* 11083:      */       {
/* 11084:11848 */         return this.submitTime_;
/* 11085:      */       }
/* 11086:      */       
/* 11087:      */       public Builder setSubmitTime(long value)
/* 11088:      */       {
/* 11089:11854 */         this.bitField0_ |= 0x4000;
/* 11090:11855 */         this.submitTime_ = value;
/* 11091:11856 */         onChanged();
/* 11092:11857 */         return this;
/* 11093:      */       }
/* 11094:      */       
/* 11095:      */       public Builder clearSubmitTime()
/* 11096:      */       {
/* 11097:11863 */         this.bitField0_ &= 0xFFFFBFFF;
/* 11098:11864 */         this.submitTime_ = 0L;
/* 11099:11865 */         onChanged();
/* 11100:11866 */         return this;
/* 11101:      */       }
/* 11102:      */       
/* 11103:      */       public boolean hasIsUber()
/* 11104:      */       {
/* 11105:11875 */         return (this.bitField0_ & 0x8000) == 32768;
/* 11106:      */       }
/* 11107:      */       
/* 11108:      */       public boolean getIsUber()
/* 11109:      */       {
/* 11110:11881 */         return this.isUber_;
/* 11111:      */       }
/* 11112:      */       
/* 11113:      */       public Builder setIsUber(boolean value)
/* 11114:      */       {
/* 11115:11887 */         this.bitField0_ |= 0x8000;
/* 11116:11888 */         this.isUber_ = value;
/* 11117:11889 */         onChanged();
/* 11118:11890 */         return this;
/* 11119:      */       }
/* 11120:      */       
/* 11121:      */       public Builder clearIsUber()
/* 11122:      */       {
/* 11123:11896 */         this.bitField0_ &= 0xFFFF7FFF;
/* 11124:11897 */         this.isUber_ = false;
/* 11125:11898 */         onChanged();
/* 11126:11899 */         return this;
/* 11127:      */       }
/* 11128:      */     }
/* 11129:      */     
/* 11130:      */     static
/* 11131:      */     {
/* 11132:11906 */       defaultInstance = new JobReportProto(true);
/* 11133:11907 */       defaultInstance.initFields();
/* 11134:      */     }
/* 11135:      */   }
/* 11136:      */   
/* 11137:      */   public static abstract interface AMInfoProtoOrBuilder
/* 11138:      */     extends MessageOrBuilder
/* 11139:      */   {
/* 11140:      */     public abstract boolean hasApplicationAttemptId();
/* 11141:      */     
/* 11142:      */     public abstract YarnProtos.ApplicationAttemptIdProto getApplicationAttemptId();
/* 11143:      */     
/* 11144:      */     public abstract YarnProtos.ApplicationAttemptIdProtoOrBuilder getApplicationAttemptIdOrBuilder();
/* 11145:      */     
/* 11146:      */     public abstract boolean hasStartTime();
/* 11147:      */     
/* 11148:      */     public abstract long getStartTime();
/* 11149:      */     
/* 11150:      */     public abstract boolean hasContainerId();
/* 11151:      */     
/* 11152:      */     public abstract YarnProtos.ContainerIdProto getContainerId();
/* 11153:      */     
/* 11154:      */     public abstract YarnProtos.ContainerIdProtoOrBuilder getContainerIdOrBuilder();
/* 11155:      */     
/* 11156:      */     public abstract boolean hasNodeManagerHost();
/* 11157:      */     
/* 11158:      */     public abstract String getNodeManagerHost();
/* 11159:      */     
/* 11160:      */     public abstract ByteString getNodeManagerHostBytes();
/* 11161:      */     
/* 11162:      */     public abstract boolean hasNodeManagerPort();
/* 11163:      */     
/* 11164:      */     public abstract int getNodeManagerPort();
/* 11165:      */     
/* 11166:      */     public abstract boolean hasNodeManagerHttpPort();
/* 11167:      */     
/* 11168:      */     public abstract int getNodeManagerHttpPort();
/* 11169:      */   }
/* 11170:      */   
/* 11171:      */   public static final class AMInfoProto
/* 11172:      */     extends GeneratedMessage
/* 11173:      */     implements MRProtos.AMInfoProtoOrBuilder
/* 11174:      */   {
/* 11175:      */     private static final AMInfoProto defaultInstance;
/* 11176:      */     private final UnknownFieldSet unknownFields;
/* 11177:      */     
/* 11178:      */     private AMInfoProto(GeneratedMessage.Builder<?> builder)
/* 11179:      */     {
/* 11180:11997 */       super();
/* 11181:11998 */       this.unknownFields = builder.getUnknownFields();
/* 11182:      */     }
/* 11183:      */     
/* 11184:      */     private AMInfoProto(boolean noInit)
/* 11185:      */     {
/* 11186:12000 */       this.unknownFields = UnknownFieldSet.getDefaultInstance();
/* 11187:      */     }
/* 11188:      */     
/* 11189:      */     public static AMInfoProto getDefaultInstance()
/* 11190:      */     {
/* 11191:12004 */       return defaultInstance;
/* 11192:      */     }
/* 11193:      */     
/* 11194:      */     public AMInfoProto getDefaultInstanceForType()
/* 11195:      */     {
/* 11196:12008 */       return defaultInstance;
/* 11197:      */     }
/* 11198:      */     
/* 11199:      */     public final UnknownFieldSet getUnknownFields()
/* 11200:      */     {
/* 11201:12015 */       return this.unknownFields;
/* 11202:      */     }
/* 11203:      */     
/* 11204:      */     private AMInfoProto(CodedInputStream input, ExtensionRegistryLite extensionRegistry)
/* 11205:      */       throws InvalidProtocolBufferException
/* 11206:      */     {
/* 11207:12021 */       initFields();
/* 11208:12022 */       int mutable_bitField0_ = 0;
/* 11209:12023 */       UnknownFieldSet.Builder unknownFields = UnknownFieldSet.newBuilder();
/* 11210:      */       try
/* 11211:      */       {
/* 11212:12026 */         boolean done = false;
/* 11213:12027 */         while (!done)
/* 11214:      */         {
/* 11215:12028 */           int tag = input.readTag();
/* 11216:12029 */           switch (tag)
/* 11217:      */           {
/* 11218:      */           case 0: 
/* 11219:12031 */             done = true;
/* 11220:12032 */             break;
/* 11221:      */           default: 
/* 11222:12034 */             if (!parseUnknownField(input, unknownFields, extensionRegistry, tag)) {
/* 11223:12036 */               done = true;
/* 11224:      */             }
/* 11225:      */             break;
/* 11226:      */           case 10: 
/* 11227:12041 */             YarnProtos.ApplicationAttemptIdProto.Builder subBuilder = null;
/* 11228:12042 */             if ((this.bitField0_ & 0x1) == 1) {
/* 11229:12043 */               subBuilder = this.applicationAttemptId_.toBuilder();
/* 11230:      */             }
/* 11231:12045 */             this.applicationAttemptId_ = ((YarnProtos.ApplicationAttemptIdProto)input.readMessage(YarnProtos.ApplicationAttemptIdProto.PARSER, extensionRegistry));
/* 11232:12046 */             if (subBuilder != null)
/* 11233:      */             {
/* 11234:12047 */               subBuilder.mergeFrom(this.applicationAttemptId_);
/* 11235:12048 */               this.applicationAttemptId_ = subBuilder.buildPartial();
/* 11236:      */             }
/* 11237:12050 */             this.bitField0_ |= 0x1;
/* 11238:12051 */             break;
/* 11239:      */           case 16: 
/* 11240:12054 */             this.bitField0_ |= 0x2;
/* 11241:12055 */             this.startTime_ = input.readInt64();
/* 11242:12056 */             break;
/* 11243:      */           case 26: 
/* 11244:12059 */             YarnProtos.ContainerIdProto.Builder subBuilder = null;
/* 11245:12060 */             if ((this.bitField0_ & 0x4) == 4) {
/* 11246:12061 */               subBuilder = this.containerId_.toBuilder();
/* 11247:      */             }
/* 11248:12063 */             this.containerId_ = ((YarnProtos.ContainerIdProto)input.readMessage(YarnProtos.ContainerIdProto.PARSER, extensionRegistry));
/* 11249:12064 */             if (subBuilder != null)
/* 11250:      */             {
/* 11251:12065 */               subBuilder.mergeFrom(this.containerId_);
/* 11252:12066 */               this.containerId_ = subBuilder.buildPartial();
/* 11253:      */             }
/* 11254:12068 */             this.bitField0_ |= 0x4;
/* 11255:12069 */             break;
/* 11256:      */           case 34: 
/* 11257:12072 */             this.bitField0_ |= 0x8;
/* 11258:12073 */             this.nodeManagerHost_ = input.readBytes();
/* 11259:12074 */             break;
/* 11260:      */           case 40: 
/* 11261:12077 */             this.bitField0_ |= 0x10;
/* 11262:12078 */             this.nodeManagerPort_ = input.readInt32();
/* 11263:12079 */             break;
/* 11264:      */           case 48: 
/* 11265:12082 */             this.bitField0_ |= 0x20;
/* 11266:12083 */             this.nodeManagerHttpPort_ = input.readInt32();
/* 11267:      */           }
/* 11268:      */         }
/* 11269:      */       }
/* 11270:      */       catch (InvalidProtocolBufferException e)
/* 11271:      */       {
/* 11272:12089 */         throw e.setUnfinishedMessage(this);
/* 11273:      */       }
/* 11274:      */       catch (IOException e)
/* 11275:      */       {
/* 11276:12091 */         throw new InvalidProtocolBufferException(e.getMessage()).setUnfinishedMessage(this);
/* 11277:      */       }
/* 11278:      */       finally
/* 11279:      */       {
/* 11280:12094 */         this.unknownFields = unknownFields.build();
/* 11281:12095 */         makeExtensionsImmutable();
/* 11282:      */       }
/* 11283:      */     }
/* 11284:      */     
/* 11285:      */     public static final Descriptors.Descriptor getDescriptor()
/* 11286:      */     {
/* 11287:12100 */       return MRProtos.internal_static_hadoop_mapreduce_AMInfoProto_descriptor;
/* 11288:      */     }
/* 11289:      */     
/* 11290:      */     protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable()
/* 11291:      */     {
/* 11292:12105 */       return MRProtos.internal_static_hadoop_mapreduce_AMInfoProto_fieldAccessorTable.ensureFieldAccessorsInitialized(AMInfoProto.class, Builder.class);
/* 11293:      */     }
/* 11294:      */     
/* 11295:12110 */     public static Parser<AMInfoProto> PARSER = new AbstractParser()
/* 11296:      */     {
/* 11297:      */       public MRProtos.AMInfoProto parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry)
/* 11298:      */         throws InvalidProtocolBufferException
/* 11299:      */       {
/* 11300:12116 */         return new MRProtos.AMInfoProto(input, extensionRegistry, null);
/* 11301:      */       }
/* 11302:      */     };
/* 11303:      */     private int bitField0_;
/* 11304:      */     public static final int APPLICATION_ATTEMPT_ID_FIELD_NUMBER = 1;
/* 11305:      */     private YarnProtos.ApplicationAttemptIdProto applicationAttemptId_;
/* 11306:      */     public static final int START_TIME_FIELD_NUMBER = 2;
/* 11307:      */     private long startTime_;
/* 11308:      */     public static final int CONTAINER_ID_FIELD_NUMBER = 3;
/* 11309:      */     private YarnProtos.ContainerIdProto containerId_;
/* 11310:      */     public static final int NODE_MANAGER_HOST_FIELD_NUMBER = 4;
/* 11311:      */     private Object nodeManagerHost_;
/* 11312:      */     public static final int NODE_MANAGER_PORT_FIELD_NUMBER = 5;
/* 11313:      */     private int nodeManagerPort_;
/* 11314:      */     public static final int NODE_MANAGER_HTTP_PORT_FIELD_NUMBER = 6;
/* 11315:      */     private int nodeManagerHttpPort_;
/* 11316:      */     
/* 11317:      */     public Parser<AMInfoProto> getParserForType()
/* 11318:      */     {
/* 11319:12122 */       return PARSER;
/* 11320:      */     }
/* 11321:      */     
/* 11322:      */     public boolean hasApplicationAttemptId()
/* 11323:      */     {
/* 11324:12133 */       return (this.bitField0_ & 0x1) == 1;
/* 11325:      */     }
/* 11326:      */     
/* 11327:      */     public YarnProtos.ApplicationAttemptIdProto getApplicationAttemptId()
/* 11328:      */     {
/* 11329:12139 */       return this.applicationAttemptId_;
/* 11330:      */     }
/* 11331:      */     
/* 11332:      */     public YarnProtos.ApplicationAttemptIdProtoOrBuilder getApplicationAttemptIdOrBuilder()
/* 11333:      */     {
/* 11334:12145 */       return this.applicationAttemptId_;
/* 11335:      */     }
/* 11336:      */     
/* 11337:      */     public boolean hasStartTime()
/* 11338:      */     {
/* 11339:12155 */       return (this.bitField0_ & 0x2) == 2;
/* 11340:      */     }
/* 11341:      */     
/* 11342:      */     public long getStartTime()
/* 11343:      */     {
/* 11344:12161 */       return this.startTime_;
/* 11345:      */     }
/* 11346:      */     
/* 11347:      */     public boolean hasContainerId()
/* 11348:      */     {
/* 11349:12171 */       return (this.bitField0_ & 0x4) == 4;
/* 11350:      */     }
/* 11351:      */     
/* 11352:      */     public YarnProtos.ContainerIdProto getContainerId()
/* 11353:      */     {
/* 11354:12177 */       return this.containerId_;
/* 11355:      */     }
/* 11356:      */     
/* 11357:      */     public YarnProtos.ContainerIdProtoOrBuilder getContainerIdOrBuilder()
/* 11358:      */     {
/* 11359:12183 */       return this.containerId_;
/* 11360:      */     }
/* 11361:      */     
/* 11362:      */     public boolean hasNodeManagerHost()
/* 11363:      */     {
/* 11364:12193 */       return (this.bitField0_ & 0x8) == 8;
/* 11365:      */     }
/* 11366:      */     
/* 11367:      */     public String getNodeManagerHost()
/* 11368:      */     {
/* 11369:12199 */       Object ref = this.nodeManagerHost_;
/* 11370:12200 */       if ((ref instanceof String)) {
/* 11371:12201 */         return (String)ref;
/* 11372:      */       }
/* 11373:12203 */       ByteString bs = (ByteString)ref;
/* 11374:      */       
/* 11375:12205 */       String s = bs.toStringUtf8();
/* 11376:12206 */       if (bs.isValidUtf8()) {
/* 11377:12207 */         this.nodeManagerHost_ = s;
/* 11378:      */       }
/* 11379:12209 */       return s;
/* 11380:      */     }
/* 11381:      */     
/* 11382:      */     public ByteString getNodeManagerHostBytes()
/* 11383:      */     {
/* 11384:12217 */       Object ref = this.nodeManagerHost_;
/* 11385:12218 */       if ((ref instanceof String))
/* 11386:      */       {
/* 11387:12219 */         ByteString b = ByteString.copyFromUtf8((String)ref);
/* 11388:      */         
/* 11389:      */ 
/* 11390:12222 */         this.nodeManagerHost_ = b;
/* 11391:12223 */         return b;
/* 11392:      */       }
/* 11393:12225 */       return (ByteString)ref;
/* 11394:      */     }
/* 11395:      */     
/* 11396:      */     public boolean hasNodeManagerPort()
/* 11397:      */     {
/* 11398:12236 */       return (this.bitField0_ & 0x10) == 16;
/* 11399:      */     }
/* 11400:      */     
/* 11401:      */     public int getNodeManagerPort()
/* 11402:      */     {
/* 11403:12242 */       return this.nodeManagerPort_;
/* 11404:      */     }
/* 11405:      */     
/* 11406:      */     public boolean hasNodeManagerHttpPort()
/* 11407:      */     {
/* 11408:12252 */       return (this.bitField0_ & 0x20) == 32;
/* 11409:      */     }
/* 11410:      */     
/* 11411:      */     public int getNodeManagerHttpPort()
/* 11412:      */     {
/* 11413:12258 */       return this.nodeManagerHttpPort_;
/* 11414:      */     }
/* 11415:      */     
/* 11416:      */     private void initFields()
/* 11417:      */     {
/* 11418:12262 */       this.applicationAttemptId_ = YarnProtos.ApplicationAttemptIdProto.getDefaultInstance();
/* 11419:12263 */       this.startTime_ = 0L;
/* 11420:12264 */       this.containerId_ = YarnProtos.ContainerIdProto.getDefaultInstance();
/* 11421:12265 */       this.nodeManagerHost_ = "";
/* 11422:12266 */       this.nodeManagerPort_ = 0;
/* 11423:12267 */       this.nodeManagerHttpPort_ = 0;
/* 11424:      */     }
/* 11425:      */     
/* 11426:12269 */     private byte memoizedIsInitialized = -1;
/* 11427:      */     
/* 11428:      */     public final boolean isInitialized()
/* 11429:      */     {
/* 11430:12271 */       byte isInitialized = this.memoizedIsInitialized;
/* 11431:12272 */       if (isInitialized != -1) {
/* 11432:12272 */         return isInitialized == 1;
/* 11433:      */       }
/* 11434:12274 */       this.memoizedIsInitialized = 1;
/* 11435:12275 */       return true;
/* 11436:      */     }
/* 11437:      */     
/* 11438:      */     public void writeTo(CodedOutputStream output)
/* 11439:      */       throws IOException
/* 11440:      */     {
/* 11441:12280 */       getSerializedSize();
/* 11442:12281 */       if ((this.bitField0_ & 0x1) == 1) {
/* 11443:12282 */         output.writeMessage(1, this.applicationAttemptId_);
/* 11444:      */       }
/* 11445:12284 */       if ((this.bitField0_ & 0x2) == 2) {
/* 11446:12285 */         output.writeInt64(2, this.startTime_);
/* 11447:      */       }
/* 11448:12287 */       if ((this.bitField0_ & 0x4) == 4) {
/* 11449:12288 */         output.writeMessage(3, this.containerId_);
/* 11450:      */       }
/* 11451:12290 */       if ((this.bitField0_ & 0x8) == 8) {
/* 11452:12291 */         output.writeBytes(4, getNodeManagerHostBytes());
/* 11453:      */       }
/* 11454:12293 */       if ((this.bitField0_ & 0x10) == 16) {
/* 11455:12294 */         output.writeInt32(5, this.nodeManagerPort_);
/* 11456:      */       }
/* 11457:12296 */       if ((this.bitField0_ & 0x20) == 32) {
/* 11458:12297 */         output.writeInt32(6, this.nodeManagerHttpPort_);
/* 11459:      */       }
/* 11460:12299 */       getUnknownFields().writeTo(output);
/* 11461:      */     }
/* 11462:      */     
/* 11463:12302 */     private int memoizedSerializedSize = -1;
/* 11464:      */     private static final long serialVersionUID = 0L;
/* 11465:      */     
/* 11466:      */     public int getSerializedSize()
/* 11467:      */     {
/* 11468:12304 */       int size = this.memoizedSerializedSize;
/* 11469:12305 */       if (size != -1) {
/* 11470:12305 */         return size;
/* 11471:      */       }
/* 11472:12307 */       size = 0;
/* 11473:12308 */       if ((this.bitField0_ & 0x1) == 1) {
/* 11474:12309 */         size += CodedOutputStream.computeMessageSize(1, this.applicationAttemptId_);
/* 11475:      */       }
/* 11476:12312 */       if ((this.bitField0_ & 0x2) == 2) {
/* 11477:12313 */         size += CodedOutputStream.computeInt64Size(2, this.startTime_);
/* 11478:      */       }
/* 11479:12316 */       if ((this.bitField0_ & 0x4) == 4) {
/* 11480:12317 */         size += CodedOutputStream.computeMessageSize(3, this.containerId_);
/* 11481:      */       }
/* 11482:12320 */       if ((this.bitField0_ & 0x8) == 8) {
/* 11483:12321 */         size += CodedOutputStream.computeBytesSize(4, getNodeManagerHostBytes());
/* 11484:      */       }
/* 11485:12324 */       if ((this.bitField0_ & 0x10) == 16) {
/* 11486:12325 */         size += CodedOutputStream.computeInt32Size(5, this.nodeManagerPort_);
/* 11487:      */       }
/* 11488:12328 */       if ((this.bitField0_ & 0x20) == 32) {
/* 11489:12329 */         size += CodedOutputStream.computeInt32Size(6, this.nodeManagerHttpPort_);
/* 11490:      */       }
/* 11491:12332 */       size += getUnknownFields().getSerializedSize();
/* 11492:12333 */       this.memoizedSerializedSize = size;
/* 11493:12334 */       return size;
/* 11494:      */     }
/* 11495:      */     
/* 11496:      */     protected Object writeReplace()
/* 11497:      */       throws ObjectStreamException
/* 11498:      */     {
/* 11499:12341 */       return super.writeReplace();
/* 11500:      */     }
/* 11501:      */     
/* 11502:      */     public boolean equals(Object obj)
/* 11503:      */     {
/* 11504:12346 */       if (obj == this) {
/* 11505:12347 */         return true;
/* 11506:      */       }
/* 11507:12349 */       if (!(obj instanceof AMInfoProto)) {
/* 11508:12350 */         return super.equals(obj);
/* 11509:      */       }
/* 11510:12352 */       AMInfoProto other = (AMInfoProto)obj;
/* 11511:      */       
/* 11512:12354 */       boolean result = true;
/* 11513:12355 */       result = (result) && (hasApplicationAttemptId() == other.hasApplicationAttemptId());
/* 11514:12356 */       if (hasApplicationAttemptId()) {
/* 11515:12357 */         result = (result) && (getApplicationAttemptId().equals(other.getApplicationAttemptId()));
/* 11516:      */       }
/* 11517:12360 */       result = (result) && (hasStartTime() == other.hasStartTime());
/* 11518:12361 */       if (hasStartTime()) {
/* 11519:12362 */         result = (result) && (getStartTime() == other.getStartTime());
/* 11520:      */       }
/* 11521:12365 */       result = (result) && (hasContainerId() == other.hasContainerId());
/* 11522:12366 */       if (hasContainerId()) {
/* 11523:12367 */         result = (result) && (getContainerId().equals(other.getContainerId()));
/* 11524:      */       }
/* 11525:12370 */       result = (result) && (hasNodeManagerHost() == other.hasNodeManagerHost());
/* 11526:12371 */       if (hasNodeManagerHost()) {
/* 11527:12372 */         result = (result) && (getNodeManagerHost().equals(other.getNodeManagerHost()));
/* 11528:      */       }
/* 11529:12375 */       result = (result) && (hasNodeManagerPort() == other.hasNodeManagerPort());
/* 11530:12376 */       if (hasNodeManagerPort()) {
/* 11531:12377 */         result = (result) && (getNodeManagerPort() == other.getNodeManagerPort());
/* 11532:      */       }
/* 11533:12380 */       result = (result) && (hasNodeManagerHttpPort() == other.hasNodeManagerHttpPort());
/* 11534:12381 */       if (hasNodeManagerHttpPort()) {
/* 11535:12382 */         result = (result) && (getNodeManagerHttpPort() == other.getNodeManagerHttpPort());
/* 11536:      */       }
/* 11537:12385 */       result = (result) && (getUnknownFields().equals(other.getUnknownFields()));
/* 11538:      */       
/* 11539:12387 */       return result;
/* 11540:      */     }
/* 11541:      */     
/* 11542:12390 */     private int memoizedHashCode = 0;
/* 11543:      */     
/* 11544:      */     public int hashCode()
/* 11545:      */     {
/* 11546:12393 */       if (this.memoizedHashCode != 0) {
/* 11547:12394 */         return this.memoizedHashCode;
/* 11548:      */       }
/* 11549:12396 */       int hash = 41;
/* 11550:12397 */       hash = 19 * hash + getDescriptorForType().hashCode();
/* 11551:12398 */       if (hasApplicationAttemptId())
/* 11552:      */       {
/* 11553:12399 */         hash = 37 * hash + 1;
/* 11554:12400 */         hash = 53 * hash + getApplicationAttemptId().hashCode();
/* 11555:      */       }
/* 11556:12402 */       if (hasStartTime())
/* 11557:      */       {
/* 11558:12403 */         hash = 37 * hash + 2;
/* 11559:12404 */         hash = 53 * hash + hashLong(getStartTime());
/* 11560:      */       }
/* 11561:12406 */       if (hasContainerId())
/* 11562:      */       {
/* 11563:12407 */         hash = 37 * hash + 3;
/* 11564:12408 */         hash = 53 * hash + getContainerId().hashCode();
/* 11565:      */       }
/* 11566:12410 */       if (hasNodeManagerHost())
/* 11567:      */       {
/* 11568:12411 */         hash = 37 * hash + 4;
/* 11569:12412 */         hash = 53 * hash + getNodeManagerHost().hashCode();
/* 11570:      */       }
/* 11571:12414 */       if (hasNodeManagerPort())
/* 11572:      */       {
/* 11573:12415 */         hash = 37 * hash + 5;
/* 11574:12416 */         hash = 53 * hash + getNodeManagerPort();
/* 11575:      */       }
/* 11576:12418 */       if (hasNodeManagerHttpPort())
/* 11577:      */       {
/* 11578:12419 */         hash = 37 * hash + 6;
/* 11579:12420 */         hash = 53 * hash + getNodeManagerHttpPort();
/* 11580:      */       }
/* 11581:12422 */       hash = 29 * hash + getUnknownFields().hashCode();
/* 11582:12423 */       this.memoizedHashCode = hash;
/* 11583:12424 */       return hash;
/* 11584:      */     }
/* 11585:      */     
/* 11586:      */     public static AMInfoProto parseFrom(ByteString data)
/* 11587:      */       throws InvalidProtocolBufferException
/* 11588:      */     {
/* 11589:12430 */       return (AMInfoProto)PARSER.parseFrom(data);
/* 11590:      */     }
/* 11591:      */     
/* 11592:      */     public static AMInfoProto parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry)
/* 11593:      */       throws InvalidProtocolBufferException
/* 11594:      */     {
/* 11595:12436 */       return (AMInfoProto)PARSER.parseFrom(data, extensionRegistry);
/* 11596:      */     }
/* 11597:      */     
/* 11598:      */     public static AMInfoProto parseFrom(byte[] data)
/* 11599:      */       throws InvalidProtocolBufferException
/* 11600:      */     {
/* 11601:12440 */       return (AMInfoProto)PARSER.parseFrom(data);
/* 11602:      */     }
/* 11603:      */     
/* 11604:      */     public static AMInfoProto parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry)
/* 11605:      */       throws InvalidProtocolBufferException
/* 11606:      */     {
/* 11607:12446 */       return (AMInfoProto)PARSER.parseFrom(data, extensionRegistry);
/* 11608:      */     }
/* 11609:      */     
/* 11610:      */     public static AMInfoProto parseFrom(InputStream input)
/* 11611:      */       throws IOException
/* 11612:      */     {
/* 11613:12450 */       return (AMInfoProto)PARSER.parseFrom(input);
/* 11614:      */     }
/* 11615:      */     
/* 11616:      */     public static AMInfoProto parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry)
/* 11617:      */       throws IOException
/* 11618:      */     {
/* 11619:12456 */       return (AMInfoProto)PARSER.parseFrom(input, extensionRegistry);
/* 11620:      */     }
/* 11621:      */     
/* 11622:      */     public static AMInfoProto parseDelimitedFrom(InputStream input)
/* 11623:      */       throws IOException
/* 11624:      */     {
/* 11625:12460 */       return (AMInfoProto)PARSER.parseDelimitedFrom(input);
/* 11626:      */     }
/* 11627:      */     
/* 11628:      */     public static AMInfoProto parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry)
/* 11629:      */       throws IOException
/* 11630:      */     {
/* 11631:12466 */       return (AMInfoProto)PARSER.parseDelimitedFrom(input, extensionRegistry);
/* 11632:      */     }
/* 11633:      */     
/* 11634:      */     public static AMInfoProto parseFrom(CodedInputStream input)
/* 11635:      */       throws IOException
/* 11636:      */     {
/* 11637:12471 */       return (AMInfoProto)PARSER.parseFrom(input);
/* 11638:      */     }
/* 11639:      */     
/* 11640:      */     public static AMInfoProto parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry)
/* 11641:      */       throws IOException
/* 11642:      */     {
/* 11643:12477 */       return (AMInfoProto)PARSER.parseFrom(input, extensionRegistry);
/* 11644:      */     }
/* 11645:      */     
/* 11646:      */     public static Builder newBuilder()
/* 11647:      */     {
/* 11648:12480 */       return Builder.access$13200();
/* 11649:      */     }
/* 11650:      */     
/* 11651:      */     public Builder newBuilderForType()
/* 11652:      */     {
/* 11653:12481 */       return newBuilder();
/* 11654:      */     }
/* 11655:      */     
/* 11656:      */     public static Builder newBuilder(AMInfoProto prototype)
/* 11657:      */     {
/* 11658:12483 */       return newBuilder().mergeFrom(prototype);
/* 11659:      */     }
/* 11660:      */     
/* 11661:      */     public Builder toBuilder()
/* 11662:      */     {
/* 11663:12485 */       return newBuilder(this);
/* 11664:      */     }
/* 11665:      */     
/* 11666:      */     protected Builder newBuilderForType(GeneratedMessage.BuilderParent parent)
/* 11667:      */     {
/* 11668:12490 */       Builder builder = new Builder(parent, null);
/* 11669:12491 */       return builder;
/* 11670:      */     }
/* 11671:      */     
/* 11672:      */     public static final class Builder
/* 11673:      */       extends GeneratedMessage.Builder<Builder>
/* 11674:      */       implements MRProtos.AMInfoProtoOrBuilder
/* 11675:      */     {
/* 11676:      */       private int bitField0_;
/* 11677:      */       
/* 11678:      */       public static final Descriptors.Descriptor getDescriptor()
/* 11679:      */       {
/* 11680:12501 */         return MRProtos.internal_static_hadoop_mapreduce_AMInfoProto_descriptor;
/* 11681:      */       }
/* 11682:      */       
/* 11683:      */       protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable()
/* 11684:      */       {
/* 11685:12506 */         return MRProtos.internal_static_hadoop_mapreduce_AMInfoProto_fieldAccessorTable.ensureFieldAccessorsInitialized(MRProtos.AMInfoProto.class, Builder.class);
/* 11686:      */       }
/* 11687:      */       
/* 11688:      */       private Builder()
/* 11689:      */       {
/* 11690:12513 */         maybeForceBuilderInitialization();
/* 11691:      */       }
/* 11692:      */       
/* 11693:      */       private Builder(GeneratedMessage.BuilderParent parent)
/* 11694:      */       {
/* 11695:12518 */         super();
/* 11696:12519 */         maybeForceBuilderInitialization();
/* 11697:      */       }
/* 11698:      */       
/* 11699:      */       private void maybeForceBuilderInitialization()
/* 11700:      */       {
/* 11701:12522 */         if (MRProtos.AMInfoProto.alwaysUseFieldBuilders)
/* 11702:      */         {
/* 11703:12523 */           getApplicationAttemptIdFieldBuilder();
/* 11704:12524 */           getContainerIdFieldBuilder();
/* 11705:      */         }
/* 11706:      */       }
/* 11707:      */       
/* 11708:      */       private static Builder create()
/* 11709:      */       {
/* 11710:12528 */         return new Builder();
/* 11711:      */       }
/* 11712:      */       
/* 11713:      */       public Builder clear()
/* 11714:      */       {
/* 11715:12532 */         super.clear();
/* 11716:12533 */         if (this.applicationAttemptIdBuilder_ == null) {
/* 11717:12534 */           this.applicationAttemptId_ = YarnProtos.ApplicationAttemptIdProto.getDefaultInstance();
/* 11718:      */         } else {
/* 11719:12536 */           this.applicationAttemptIdBuilder_.clear();
/* 11720:      */         }
/* 11721:12538 */         this.bitField0_ &= 0xFFFFFFFE;
/* 11722:12539 */         this.startTime_ = 0L;
/* 11723:12540 */         this.bitField0_ &= 0xFFFFFFFD;
/* 11724:12541 */         if (this.containerIdBuilder_ == null) {
/* 11725:12542 */           this.containerId_ = YarnProtos.ContainerIdProto.getDefaultInstance();
/* 11726:      */         } else {
/* 11727:12544 */           this.containerIdBuilder_.clear();
/* 11728:      */         }
/* 11729:12546 */         this.bitField0_ &= 0xFFFFFFFB;
/* 11730:12547 */         this.nodeManagerHost_ = "";
/* 11731:12548 */         this.bitField0_ &= 0xFFFFFFF7;
/* 11732:12549 */         this.nodeManagerPort_ = 0;
/* 11733:12550 */         this.bitField0_ &= 0xFFFFFFEF;
/* 11734:12551 */         this.nodeManagerHttpPort_ = 0;
/* 11735:12552 */         this.bitField0_ &= 0xFFFFFFDF;
/* 11736:12553 */         return this;
/* 11737:      */       }
/* 11738:      */       
/* 11739:      */       public Builder clone()
/* 11740:      */       {
/* 11741:12557 */         return create().mergeFrom(buildPartial());
/* 11742:      */       }
/* 11743:      */       
/* 11744:      */       public Descriptors.Descriptor getDescriptorForType()
/* 11745:      */       {
/* 11746:12562 */         return MRProtos.internal_static_hadoop_mapreduce_AMInfoProto_descriptor;
/* 11747:      */       }
/* 11748:      */       
/* 11749:      */       public MRProtos.AMInfoProto getDefaultInstanceForType()
/* 11750:      */       {
/* 11751:12566 */         return MRProtos.AMInfoProto.getDefaultInstance();
/* 11752:      */       }
/* 11753:      */       
/* 11754:      */       public MRProtos.AMInfoProto build()
/* 11755:      */       {
/* 11756:12570 */         MRProtos.AMInfoProto result = buildPartial();
/* 11757:12571 */         if (!result.isInitialized()) {
/* 11758:12572 */           throw newUninitializedMessageException(result);
/* 11759:      */         }
/* 11760:12574 */         return result;
/* 11761:      */       }
/* 11762:      */       
/* 11763:      */       public MRProtos.AMInfoProto buildPartial()
/* 11764:      */       {
/* 11765:12578 */         MRProtos.AMInfoProto result = new MRProtos.AMInfoProto(this, null);
/* 11766:12579 */         int from_bitField0_ = this.bitField0_;
/* 11767:12580 */         int to_bitField0_ = 0;
/* 11768:12581 */         if ((from_bitField0_ & 0x1) == 1) {
/* 11769:12582 */           to_bitField0_ |= 0x1;
/* 11770:      */         }
/* 11771:12584 */         if (this.applicationAttemptIdBuilder_ == null) {
/* 11772:12585 */           result.applicationAttemptId_ = this.applicationAttemptId_;
/* 11773:      */         } else {
/* 11774:12587 */           result.applicationAttemptId_ = ((YarnProtos.ApplicationAttemptIdProto)this.applicationAttemptIdBuilder_.build());
/* 11775:      */         }
/* 11776:12589 */         if ((from_bitField0_ & 0x2) == 2) {
/* 11777:12590 */           to_bitField0_ |= 0x2;
/* 11778:      */         }
/* 11779:12592 */         result.startTime_ = this.startTime_;
/* 11780:12593 */         if ((from_bitField0_ & 0x4) == 4) {
/* 11781:12594 */           to_bitField0_ |= 0x4;
/* 11782:      */         }
/* 11783:12596 */         if (this.containerIdBuilder_ == null) {
/* 11784:12597 */           result.containerId_ = this.containerId_;
/* 11785:      */         } else {
/* 11786:12599 */           result.containerId_ = ((YarnProtos.ContainerIdProto)this.containerIdBuilder_.build());
/* 11787:      */         }
/* 11788:12601 */         if ((from_bitField0_ & 0x8) == 8) {
/* 11789:12602 */           to_bitField0_ |= 0x8;
/* 11790:      */         }
/* 11791:12604 */         result.nodeManagerHost_ = this.nodeManagerHost_;
/* 11792:12605 */         if ((from_bitField0_ & 0x10) == 16) {
/* 11793:12606 */           to_bitField0_ |= 0x10;
/* 11794:      */         }
/* 11795:12608 */         result.nodeManagerPort_ = this.nodeManagerPort_;
/* 11796:12609 */         if ((from_bitField0_ & 0x20) == 32) {
/* 11797:12610 */           to_bitField0_ |= 0x20;
/* 11798:      */         }
/* 11799:12612 */         result.nodeManagerHttpPort_ = this.nodeManagerHttpPort_;
/* 11800:12613 */         result.bitField0_ = to_bitField0_;
/* 11801:12614 */         onBuilt();
/* 11802:12615 */         return result;
/* 11803:      */       }
/* 11804:      */       
/* 11805:      */       public Builder mergeFrom(Message other)
/* 11806:      */       {
/* 11807:12619 */         if ((other instanceof MRProtos.AMInfoProto)) {
/* 11808:12620 */           return mergeFrom((MRProtos.AMInfoProto)other);
/* 11809:      */         }
/* 11810:12622 */         super.mergeFrom(other);
/* 11811:12623 */         return this;
/* 11812:      */       }
/* 11813:      */       
/* 11814:      */       public Builder mergeFrom(MRProtos.AMInfoProto other)
/* 11815:      */       {
/* 11816:12628 */         if (other == MRProtos.AMInfoProto.getDefaultInstance()) {
/* 11817:12628 */           return this;
/* 11818:      */         }
/* 11819:12629 */         if (other.hasApplicationAttemptId()) {
/* 11820:12630 */           mergeApplicationAttemptId(other.getApplicationAttemptId());
/* 11821:      */         }
/* 11822:12632 */         if (other.hasStartTime()) {
/* 11823:12633 */           setStartTime(other.getStartTime());
/* 11824:      */         }
/* 11825:12635 */         if (other.hasContainerId()) {
/* 11826:12636 */           mergeContainerId(other.getContainerId());
/* 11827:      */         }
/* 11828:12638 */         if (other.hasNodeManagerHost())
/* 11829:      */         {
/* 11830:12639 */           this.bitField0_ |= 0x8;
/* 11831:12640 */           this.nodeManagerHost_ = other.nodeManagerHost_;
/* 11832:12641 */           onChanged();
/* 11833:      */         }
/* 11834:12643 */         if (other.hasNodeManagerPort()) {
/* 11835:12644 */           setNodeManagerPort(other.getNodeManagerPort());
/* 11836:      */         }
/* 11837:12646 */         if (other.hasNodeManagerHttpPort()) {
/* 11838:12647 */           setNodeManagerHttpPort(other.getNodeManagerHttpPort());
/* 11839:      */         }
/* 11840:12649 */         mergeUnknownFields(other.getUnknownFields());
/* 11841:12650 */         return this;
/* 11842:      */       }
/* 11843:      */       
/* 11844:      */       public final boolean isInitialized()
/* 11845:      */       {
/* 11846:12654 */         return true;
/* 11847:      */       }
/* 11848:      */       
/* 11849:      */       public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry)
/* 11850:      */         throws IOException
/* 11851:      */       {
/* 11852:12661 */         MRProtos.AMInfoProto parsedMessage = null;
/* 11853:      */         try
/* 11854:      */         {
/* 11855:12663 */           parsedMessage = (MRProtos.AMInfoProto)MRProtos.AMInfoProto.PARSER.parsePartialFrom(input, extensionRegistry);
/* 11856:      */         }
/* 11857:      */         catch (InvalidProtocolBufferException e)
/* 11858:      */         {
/* 11859:12665 */           parsedMessage = (MRProtos.AMInfoProto)e.getUnfinishedMessage();
/* 11860:12666 */           throw e;
/* 11861:      */         }
/* 11862:      */         finally
/* 11863:      */         {
/* 11864:12668 */           if (parsedMessage != null) {
/* 11865:12669 */             mergeFrom(parsedMessage);
/* 11866:      */           }
/* 11867:      */         }
/* 11868:12672 */         return this;
/* 11869:      */       }
/* 11870:      */       
/* 11871:12677 */       private YarnProtos.ApplicationAttemptIdProto applicationAttemptId_ = YarnProtos.ApplicationAttemptIdProto.getDefaultInstance();
/* 11872:      */       private SingleFieldBuilder<YarnProtos.ApplicationAttemptIdProto, YarnProtos.ApplicationAttemptIdProto.Builder, YarnProtos.ApplicationAttemptIdProtoOrBuilder> applicationAttemptIdBuilder_;
/* 11873:      */       private long startTime_;
/* 11874:      */       
/* 11875:      */       public boolean hasApplicationAttemptId()
/* 11876:      */       {
/* 11877:12684 */         return (this.bitField0_ & 0x1) == 1;
/* 11878:      */       }
/* 11879:      */       
/* 11880:      */       public YarnProtos.ApplicationAttemptIdProto getApplicationAttemptId()
/* 11881:      */       {
/* 11882:12690 */         if (this.applicationAttemptIdBuilder_ == null) {
/* 11883:12691 */           return this.applicationAttemptId_;
/* 11884:      */         }
/* 11885:12693 */         return (YarnProtos.ApplicationAttemptIdProto)this.applicationAttemptIdBuilder_.getMessage();
/* 11886:      */       }
/* 11887:      */       
/* 11888:      */       public Builder setApplicationAttemptId(YarnProtos.ApplicationAttemptIdProto value)
/* 11889:      */       {
/* 11890:12700 */         if (this.applicationAttemptIdBuilder_ == null)
/* 11891:      */         {
/* 11892:12701 */           if (value == null) {
/* 11893:12702 */             throw new NullPointerException();
/* 11894:      */           }
/* 11895:12704 */           this.applicationAttemptId_ = value;
/* 11896:12705 */           onChanged();
/* 11897:      */         }
/* 11898:      */         else
/* 11899:      */         {
/* 11900:12707 */           this.applicationAttemptIdBuilder_.setMessage(value);
/* 11901:      */         }
/* 11902:12709 */         this.bitField0_ |= 0x1;
/* 11903:12710 */         return this;
/* 11904:      */       }
/* 11905:      */       
/* 11906:      */       public Builder setApplicationAttemptId(YarnProtos.ApplicationAttemptIdProto.Builder builderForValue)
/* 11907:      */       {
/* 11908:12717 */         if (this.applicationAttemptIdBuilder_ == null)
/* 11909:      */         {
/* 11910:12718 */           this.applicationAttemptId_ = builderForValue.build();
/* 11911:12719 */           onChanged();
/* 11912:      */         }
/* 11913:      */         else
/* 11914:      */         {
/* 11915:12721 */           this.applicationAttemptIdBuilder_.setMessage(builderForValue.build());
/* 11916:      */         }
/* 11917:12723 */         this.bitField0_ |= 0x1;
/* 11918:12724 */         return this;
/* 11919:      */       }
/* 11920:      */       
/* 11921:      */       public Builder mergeApplicationAttemptId(YarnProtos.ApplicationAttemptIdProto value)
/* 11922:      */       {
/* 11923:12730 */         if (this.applicationAttemptIdBuilder_ == null)
/* 11924:      */         {
/* 11925:12731 */           if (((this.bitField0_ & 0x1) == 1) && (this.applicationAttemptId_ != YarnProtos.ApplicationAttemptIdProto.getDefaultInstance())) {
/* 11926:12733 */             this.applicationAttemptId_ = YarnProtos.ApplicationAttemptIdProto.newBuilder(this.applicationAttemptId_).mergeFrom(value).buildPartial();
/* 11927:      */           } else {
/* 11928:12736 */             this.applicationAttemptId_ = value;
/* 11929:      */           }
/* 11930:12738 */           onChanged();
/* 11931:      */         }
/* 11932:      */         else
/* 11933:      */         {
/* 11934:12740 */           this.applicationAttemptIdBuilder_.mergeFrom(value);
/* 11935:      */         }
/* 11936:12742 */         this.bitField0_ |= 0x1;
/* 11937:12743 */         return this;
/* 11938:      */       }
/* 11939:      */       
/* 11940:      */       public Builder clearApplicationAttemptId()
/* 11941:      */       {
/* 11942:12749 */         if (this.applicationAttemptIdBuilder_ == null)
/* 11943:      */         {
/* 11944:12750 */           this.applicationAttemptId_ = YarnProtos.ApplicationAttemptIdProto.getDefaultInstance();
/* 11945:12751 */           onChanged();
/* 11946:      */         }
/* 11947:      */         else
/* 11948:      */         {
/* 11949:12753 */           this.applicationAttemptIdBuilder_.clear();
/* 11950:      */         }
/* 11951:12755 */         this.bitField0_ &= 0xFFFFFFFE;
/* 11952:12756 */         return this;
/* 11953:      */       }
/* 11954:      */       
/* 11955:      */       public YarnProtos.ApplicationAttemptIdProto.Builder getApplicationAttemptIdBuilder()
/* 11956:      */       {
/* 11957:12762 */         this.bitField0_ |= 0x1;
/* 11958:12763 */         onChanged();
/* 11959:12764 */         return (YarnProtos.ApplicationAttemptIdProto.Builder)getApplicationAttemptIdFieldBuilder().getBuilder();
/* 11960:      */       }
/* 11961:      */       
/* 11962:      */       public YarnProtos.ApplicationAttemptIdProtoOrBuilder getApplicationAttemptIdOrBuilder()
/* 11963:      */       {
/* 11964:12770 */         if (this.applicationAttemptIdBuilder_ != null) {
/* 11965:12771 */           return (YarnProtos.ApplicationAttemptIdProtoOrBuilder)this.applicationAttemptIdBuilder_.getMessageOrBuilder();
/* 11966:      */         }
/* 11967:12773 */         return this.applicationAttemptId_;
/* 11968:      */       }
/* 11969:      */       
/* 11970:      */       private SingleFieldBuilder<YarnProtos.ApplicationAttemptIdProto, YarnProtos.ApplicationAttemptIdProto.Builder, YarnProtos.ApplicationAttemptIdProtoOrBuilder> getApplicationAttemptIdFieldBuilder()
/* 11971:      */       {
/* 11972:12782 */         if (this.applicationAttemptIdBuilder_ == null)
/* 11973:      */         {
/* 11974:12783 */           this.applicationAttemptIdBuilder_ = new SingleFieldBuilder(this.applicationAttemptId_, getParentForChildren(), isClean());
/* 11975:      */           
/* 11976:      */ 
/* 11977:      */ 
/* 11978:      */ 
/* 11979:12788 */           this.applicationAttemptId_ = null;
/* 11980:      */         }
/* 11981:12790 */         return this.applicationAttemptIdBuilder_;
/* 11982:      */       }
/* 11983:      */       
/* 11984:      */       public boolean hasStartTime()
/* 11985:      */       {
/* 11986:12799 */         return (this.bitField0_ & 0x2) == 2;
/* 11987:      */       }
/* 11988:      */       
/* 11989:      */       public long getStartTime()
/* 11990:      */       {
/* 11991:12805 */         return this.startTime_;
/* 11992:      */       }
/* 11993:      */       
/* 11994:      */       public Builder setStartTime(long value)
/* 11995:      */       {
/* 11996:12811 */         this.bitField0_ |= 0x2;
/* 11997:12812 */         this.startTime_ = value;
/* 11998:12813 */         onChanged();
/* 11999:12814 */         return this;
/* 12000:      */       }
/* 12001:      */       
/* 12002:      */       public Builder clearStartTime()
/* 12003:      */       {
/* 12004:12820 */         this.bitField0_ &= 0xFFFFFFFD;
/* 12005:12821 */         this.startTime_ = 0L;
/* 12006:12822 */         onChanged();
/* 12007:12823 */         return this;
/* 12008:      */       }
/* 12009:      */       
/* 12010:12827 */       private YarnProtos.ContainerIdProto containerId_ = YarnProtos.ContainerIdProto.getDefaultInstance();
/* 12011:      */       private SingleFieldBuilder<YarnProtos.ContainerIdProto, YarnProtos.ContainerIdProto.Builder, YarnProtos.ContainerIdProtoOrBuilder> containerIdBuilder_;
/* 12012:      */       
/* 12013:      */       public boolean hasContainerId()
/* 12014:      */       {
/* 12015:12834 */         return (this.bitField0_ & 0x4) == 4;
/* 12016:      */       }
/* 12017:      */       
/* 12018:      */       public YarnProtos.ContainerIdProto getContainerId()
/* 12019:      */       {
/* 12020:12840 */         if (this.containerIdBuilder_ == null) {
/* 12021:12841 */           return this.containerId_;
/* 12022:      */         }
/* 12023:12843 */         return (YarnProtos.ContainerIdProto)this.containerIdBuilder_.getMessage();
/* 12024:      */       }
/* 12025:      */       
/* 12026:      */       public Builder setContainerId(YarnProtos.ContainerIdProto value)
/* 12027:      */       {
/* 12028:12850 */         if (this.containerIdBuilder_ == null)
/* 12029:      */         {
/* 12030:12851 */           if (value == null) {
/* 12031:12852 */             throw new NullPointerException();
/* 12032:      */           }
/* 12033:12854 */           this.containerId_ = value;
/* 12034:12855 */           onChanged();
/* 12035:      */         }
/* 12036:      */         else
/* 12037:      */         {
/* 12038:12857 */           this.containerIdBuilder_.setMessage(value);
/* 12039:      */         }
/* 12040:12859 */         this.bitField0_ |= 0x4;
/* 12041:12860 */         return this;
/* 12042:      */       }
/* 12043:      */       
/* 12044:      */       public Builder setContainerId(YarnProtos.ContainerIdProto.Builder builderForValue)
/* 12045:      */       {
/* 12046:12867 */         if (this.containerIdBuilder_ == null)
/* 12047:      */         {
/* 12048:12868 */           this.containerId_ = builderForValue.build();
/* 12049:12869 */           onChanged();
/* 12050:      */         }
/* 12051:      */         else
/* 12052:      */         {
/* 12053:12871 */           this.containerIdBuilder_.setMessage(builderForValue.build());
/* 12054:      */         }
/* 12055:12873 */         this.bitField0_ |= 0x4;
/* 12056:12874 */         return this;
/* 12057:      */       }
/* 12058:      */       
/* 12059:      */       public Builder mergeContainerId(YarnProtos.ContainerIdProto value)
/* 12060:      */       {
/* 12061:12880 */         if (this.containerIdBuilder_ == null)
/* 12062:      */         {
/* 12063:12881 */           if (((this.bitField0_ & 0x4) == 4) && (this.containerId_ != YarnProtos.ContainerIdProto.getDefaultInstance())) {
/* 12064:12883 */             this.containerId_ = YarnProtos.ContainerIdProto.newBuilder(this.containerId_).mergeFrom(value).buildPartial();
/* 12065:      */           } else {
/* 12066:12886 */             this.containerId_ = value;
/* 12067:      */           }
/* 12068:12888 */           onChanged();
/* 12069:      */         }
/* 12070:      */         else
/* 12071:      */         {
/* 12072:12890 */           this.containerIdBuilder_.mergeFrom(value);
/* 12073:      */         }
/* 12074:12892 */         this.bitField0_ |= 0x4;
/* 12075:12893 */         return this;
/* 12076:      */       }
/* 12077:      */       
/* 12078:      */       public Builder clearContainerId()
/* 12079:      */       {
/* 12080:12899 */         if (this.containerIdBuilder_ == null)
/* 12081:      */         {
/* 12082:12900 */           this.containerId_ = YarnProtos.ContainerIdProto.getDefaultInstance();
/* 12083:12901 */           onChanged();
/* 12084:      */         }
/* 12085:      */         else
/* 12086:      */         {
/* 12087:12903 */           this.containerIdBuilder_.clear();
/* 12088:      */         }
/* 12089:12905 */         this.bitField0_ &= 0xFFFFFFFB;
/* 12090:12906 */         return this;
/* 12091:      */       }
/* 12092:      */       
/* 12093:      */       public YarnProtos.ContainerIdProto.Builder getContainerIdBuilder()
/* 12094:      */       {
/* 12095:12912 */         this.bitField0_ |= 0x4;
/* 12096:12913 */         onChanged();
/* 12097:12914 */         return (YarnProtos.ContainerIdProto.Builder)getContainerIdFieldBuilder().getBuilder();
/* 12098:      */       }
/* 12099:      */       
/* 12100:      */       public YarnProtos.ContainerIdProtoOrBuilder getContainerIdOrBuilder()
/* 12101:      */       {
/* 12102:12920 */         if (this.containerIdBuilder_ != null) {
/* 12103:12921 */           return (YarnProtos.ContainerIdProtoOrBuilder)this.containerIdBuilder_.getMessageOrBuilder();
/* 12104:      */         }
/* 12105:12923 */         return this.containerId_;
/* 12106:      */       }
/* 12107:      */       
/* 12108:      */       private SingleFieldBuilder<YarnProtos.ContainerIdProto, YarnProtos.ContainerIdProto.Builder, YarnProtos.ContainerIdProtoOrBuilder> getContainerIdFieldBuilder()
/* 12109:      */       {
/* 12110:12932 */         if (this.containerIdBuilder_ == null)
/* 12111:      */         {
/* 12112:12933 */           this.containerIdBuilder_ = new SingleFieldBuilder(this.containerId_, getParentForChildren(), isClean());
/* 12113:      */           
/* 12114:      */ 
/* 12115:      */ 
/* 12116:      */ 
/* 12117:12938 */           this.containerId_ = null;
/* 12118:      */         }
/* 12119:12940 */         return this.containerIdBuilder_;
/* 12120:      */       }
/* 12121:      */       
/* 12122:12944 */       private Object nodeManagerHost_ = "";
/* 12123:      */       private int nodeManagerPort_;
/* 12124:      */       private int nodeManagerHttpPort_;
/* 12125:      */       
/* 12126:      */       public boolean hasNodeManagerHost()
/* 12127:      */       {
/* 12128:12949 */         return (this.bitField0_ & 0x8) == 8;
/* 12129:      */       }
/* 12130:      */       
/* 12131:      */       public String getNodeManagerHost()
/* 12132:      */       {
/* 12133:12955 */         Object ref = this.nodeManagerHost_;
/* 12134:12956 */         if (!(ref instanceof String))
/* 12135:      */         {
/* 12136:12957 */           String s = ((ByteString)ref).toStringUtf8();
/* 12137:      */           
/* 12138:12959 */           this.nodeManagerHost_ = s;
/* 12139:12960 */           return s;
/* 12140:      */         }
/* 12141:12962 */         return (String)ref;
/* 12142:      */       }
/* 12143:      */       
/* 12144:      */       public ByteString getNodeManagerHostBytes()
/* 12145:      */       {
/* 12146:12970 */         Object ref = this.nodeManagerHost_;
/* 12147:12971 */         if ((ref instanceof String))
/* 12148:      */         {
/* 12149:12972 */           ByteString b = ByteString.copyFromUtf8((String)ref);
/* 12150:      */           
/* 12151:      */ 
/* 12152:12975 */           this.nodeManagerHost_ = b;
/* 12153:12976 */           return b;
/* 12154:      */         }
/* 12155:12978 */         return (ByteString)ref;
/* 12156:      */       }
/* 12157:      */       
/* 12158:      */       public Builder setNodeManagerHost(String value)
/* 12159:      */       {
/* 12160:12986 */         if (value == null) {
/* 12161:12987 */           throw new NullPointerException();
/* 12162:      */         }
/* 12163:12989 */         this.bitField0_ |= 0x8;
/* 12164:12990 */         this.nodeManagerHost_ = value;
/* 12165:12991 */         onChanged();
/* 12166:12992 */         return this;
/* 12167:      */       }
/* 12168:      */       
/* 12169:      */       public Builder clearNodeManagerHost()
/* 12170:      */       {
/* 12171:12998 */         this.bitField0_ &= 0xFFFFFFF7;
/* 12172:12999 */         this.nodeManagerHost_ = MRProtos.AMInfoProto.getDefaultInstance().getNodeManagerHost();
/* 12173:13000 */         onChanged();
/* 12174:13001 */         return this;
/* 12175:      */       }
/* 12176:      */       
/* 12177:      */       public Builder setNodeManagerHostBytes(ByteString value)
/* 12178:      */       {
/* 12179:13008 */         if (value == null) {
/* 12180:13009 */           throw new NullPointerException();
/* 12181:      */         }
/* 12182:13011 */         this.bitField0_ |= 0x8;
/* 12183:13012 */         this.nodeManagerHost_ = value;
/* 12184:13013 */         onChanged();
/* 12185:13014 */         return this;
/* 12186:      */       }
/* 12187:      */       
/* 12188:      */       public boolean hasNodeManagerPort()
/* 12189:      */       {
/* 12190:13023 */         return (this.bitField0_ & 0x10) == 16;
/* 12191:      */       }
/* 12192:      */       
/* 12193:      */       public int getNodeManagerPort()
/* 12194:      */       {
/* 12195:13029 */         return this.nodeManagerPort_;
/* 12196:      */       }
/* 12197:      */       
/* 12198:      */       public Builder setNodeManagerPort(int value)
/* 12199:      */       {
/* 12200:13035 */         this.bitField0_ |= 0x10;
/* 12201:13036 */         this.nodeManagerPort_ = value;
/* 12202:13037 */         onChanged();
/* 12203:13038 */         return this;
/* 12204:      */       }
/* 12205:      */       
/* 12206:      */       public Builder clearNodeManagerPort()
/* 12207:      */       {
/* 12208:13044 */         this.bitField0_ &= 0xFFFFFFEF;
/* 12209:13045 */         this.nodeManagerPort_ = 0;
/* 12210:13046 */         onChanged();
/* 12211:13047 */         return this;
/* 12212:      */       }
/* 12213:      */       
/* 12214:      */       public boolean hasNodeManagerHttpPort()
/* 12215:      */       {
/* 12216:13056 */         return (this.bitField0_ & 0x20) == 32;
/* 12217:      */       }
/* 12218:      */       
/* 12219:      */       public int getNodeManagerHttpPort()
/* 12220:      */       {
/* 12221:13062 */         return this.nodeManagerHttpPort_;
/* 12222:      */       }
/* 12223:      */       
/* 12224:      */       public Builder setNodeManagerHttpPort(int value)
/* 12225:      */       {
/* 12226:13068 */         this.bitField0_ |= 0x20;
/* 12227:13069 */         this.nodeManagerHttpPort_ = value;
/* 12228:13070 */         onChanged();
/* 12229:13071 */         return this;
/* 12230:      */       }
/* 12231:      */       
/* 12232:      */       public Builder clearNodeManagerHttpPort()
/* 12233:      */       {
/* 12234:13077 */         this.bitField0_ &= 0xFFFFFFDF;
/* 12235:13078 */         this.nodeManagerHttpPort_ = 0;
/* 12236:13079 */         onChanged();
/* 12237:13080 */         return this;
/* 12238:      */       }
/* 12239:      */     }
/* 12240:      */     
/* 12241:      */     static
/* 12242:      */     {
/* 12243:13087 */       defaultInstance = new AMInfoProto(true);
/* 12244:13088 */       defaultInstance.initFields();
/* 12245:      */     }
/* 12246:      */   }
/* 12247:      */   
/* 12248:      */   public static abstract interface TaskAttemptCompletionEventProtoOrBuilder
/* 12249:      */     extends MessageOrBuilder
/* 12250:      */   {
/* 12251:      */     public abstract boolean hasAttemptId();
/* 12252:      */     
/* 12253:      */     public abstract MRProtos.TaskAttemptIdProto getAttemptId();
/* 12254:      */     
/* 12255:      */     public abstract MRProtos.TaskAttemptIdProtoOrBuilder getAttemptIdOrBuilder();
/* 12256:      */     
/* 12257:      */     public abstract boolean hasStatus();
/* 12258:      */     
/* 12259:      */     public abstract MRProtos.TaskAttemptCompletionEventStatusProto getStatus();
/* 12260:      */     
/* 12261:      */     public abstract boolean hasMapOutputServerAddress();
/* 12262:      */     
/* 12263:      */     public abstract String getMapOutputServerAddress();
/* 12264:      */     
/* 12265:      */     public abstract ByteString getMapOutputServerAddressBytes();
/* 12266:      */     
/* 12267:      */     public abstract boolean hasAttemptRunTime();
/* 12268:      */     
/* 12269:      */     public abstract int getAttemptRunTime();
/* 12270:      */     
/* 12271:      */     public abstract boolean hasEventId();
/* 12272:      */     
/* 12273:      */     public abstract int getEventId();
/* 12274:      */   }
/* 12275:      */   
/* 12276:      */   public static final class TaskAttemptCompletionEventProto
/* 12277:      */     extends GeneratedMessage
/* 12278:      */     implements MRProtos.TaskAttemptCompletionEventProtoOrBuilder
/* 12279:      */   {
/* 12280:      */     private static final TaskAttemptCompletionEventProto defaultInstance;
/* 12281:      */     private final UnknownFieldSet unknownFields;
/* 12282:      */     
/* 12283:      */     private TaskAttemptCompletionEventProto(GeneratedMessage.Builder<?> builder)
/* 12284:      */     {
/* 12285:13164 */       super();
/* 12286:13165 */       this.unknownFields = builder.getUnknownFields();
/* 12287:      */     }
/* 12288:      */     
/* 12289:      */     private TaskAttemptCompletionEventProto(boolean noInit)
/* 12290:      */     {
/* 12291:13167 */       this.unknownFields = UnknownFieldSet.getDefaultInstance();
/* 12292:      */     }
/* 12293:      */     
/* 12294:      */     public static TaskAttemptCompletionEventProto getDefaultInstance()
/* 12295:      */     {
/* 12296:13171 */       return defaultInstance;
/* 12297:      */     }
/* 12298:      */     
/* 12299:      */     public TaskAttemptCompletionEventProto getDefaultInstanceForType()
/* 12300:      */     {
/* 12301:13175 */       return defaultInstance;
/* 12302:      */     }
/* 12303:      */     
/* 12304:      */     public final UnknownFieldSet getUnknownFields()
/* 12305:      */     {
/* 12306:13182 */       return this.unknownFields;
/* 12307:      */     }
/* 12308:      */     
/* 12309:      */     private TaskAttemptCompletionEventProto(CodedInputStream input, ExtensionRegistryLite extensionRegistry)
/* 12310:      */       throws InvalidProtocolBufferException
/* 12311:      */     {
/* 12312:13188 */       initFields();
/* 12313:13189 */       int mutable_bitField0_ = 0;
/* 12314:13190 */       UnknownFieldSet.Builder unknownFields = UnknownFieldSet.newBuilder();
/* 12315:      */       try
/* 12316:      */       {
/* 12317:13193 */         boolean done = false;
/* 12318:13194 */         while (!done)
/* 12319:      */         {
/* 12320:13195 */           int tag = input.readTag();
/* 12321:13196 */           switch (tag)
/* 12322:      */           {
/* 12323:      */           case 0: 
/* 12324:13198 */             done = true;
/* 12325:13199 */             break;
/* 12326:      */           default: 
/* 12327:13201 */             if (!parseUnknownField(input, unknownFields, extensionRegistry, tag)) {
/* 12328:13203 */               done = true;
/* 12329:      */             }
/* 12330:      */             break;
/* 12331:      */           case 10: 
/* 12332:13208 */             MRProtos.TaskAttemptIdProto.Builder subBuilder = null;
/* 12333:13209 */             if ((this.bitField0_ & 0x1) == 1) {
/* 12334:13210 */               subBuilder = this.attemptId_.toBuilder();
/* 12335:      */             }
/* 12336:13212 */             this.attemptId_ = ((MRProtos.TaskAttemptIdProto)input.readMessage(MRProtos.TaskAttemptIdProto.PARSER, extensionRegistry));
/* 12337:13213 */             if (subBuilder != null)
/* 12338:      */             {
/* 12339:13214 */               subBuilder.mergeFrom(this.attemptId_);
/* 12340:13215 */               this.attemptId_ = subBuilder.buildPartial();
/* 12341:      */             }
/* 12342:13217 */             this.bitField0_ |= 0x1;
/* 12343:13218 */             break;
/* 12344:      */           case 16: 
/* 12345:13221 */             int rawValue = input.readEnum();
/* 12346:13222 */             MRProtos.TaskAttemptCompletionEventStatusProto value = MRProtos.TaskAttemptCompletionEventStatusProto.valueOf(rawValue);
/* 12347:13223 */             if (value == null)
/* 12348:      */             {
/* 12349:13224 */               unknownFields.mergeVarintField(2, rawValue);
/* 12350:      */             }
/* 12351:      */             else
/* 12352:      */             {
/* 12353:13226 */               this.bitField0_ |= 0x2;
/* 12354:13227 */               this.status_ = value;
/* 12355:      */             }
/* 12356:13229 */             break;
/* 12357:      */           case 26: 
/* 12358:13232 */             this.bitField0_ |= 0x4;
/* 12359:13233 */             this.mapOutputServerAddress_ = input.readBytes();
/* 12360:13234 */             break;
/* 12361:      */           case 32: 
/* 12362:13237 */             this.bitField0_ |= 0x8;
/* 12363:13238 */             this.attemptRunTime_ = input.readInt32();
/* 12364:13239 */             break;
/* 12365:      */           case 40: 
/* 12366:13242 */             this.bitField0_ |= 0x10;
/* 12367:13243 */             this.eventId_ = input.readInt32();
/* 12368:      */           }
/* 12369:      */         }
/* 12370:      */       }
/* 12371:      */       catch (InvalidProtocolBufferException e)
/* 12372:      */       {
/* 12373:13249 */         throw e.setUnfinishedMessage(this);
/* 12374:      */       }
/* 12375:      */       catch (IOException e)
/* 12376:      */       {
/* 12377:13251 */         throw new InvalidProtocolBufferException(e.getMessage()).setUnfinishedMessage(this);
/* 12378:      */       }
/* 12379:      */       finally
/* 12380:      */       {
/* 12381:13254 */         this.unknownFields = unknownFields.build();
/* 12382:13255 */         makeExtensionsImmutable();
/* 12383:      */       }
/* 12384:      */     }
/* 12385:      */     
/* 12386:      */     public static final Descriptors.Descriptor getDescriptor()
/* 12387:      */     {
/* 12388:13260 */       return MRProtos.internal_static_hadoop_mapreduce_TaskAttemptCompletionEventProto_descriptor;
/* 12389:      */     }
/* 12390:      */     
/* 12391:      */     protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable()
/* 12392:      */     {
/* 12393:13265 */       return MRProtos.internal_static_hadoop_mapreduce_TaskAttemptCompletionEventProto_fieldAccessorTable.ensureFieldAccessorsInitialized(TaskAttemptCompletionEventProto.class, Builder.class);
/* 12394:      */     }
/* 12395:      */     
/* 12396:13270 */     public static Parser<TaskAttemptCompletionEventProto> PARSER = new AbstractParser()
/* 12397:      */     {
/* 12398:      */       public MRProtos.TaskAttemptCompletionEventProto parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry)
/* 12399:      */         throws InvalidProtocolBufferException
/* 12400:      */       {
/* 12401:13276 */         return new MRProtos.TaskAttemptCompletionEventProto(input, extensionRegistry, null);
/* 12402:      */       }
/* 12403:      */     };
/* 12404:      */     private int bitField0_;
/* 12405:      */     public static final int ATTEMPT_ID_FIELD_NUMBER = 1;
/* 12406:      */     private MRProtos.TaskAttemptIdProto attemptId_;
/* 12407:      */     public static final int STATUS_FIELD_NUMBER = 2;
/* 12408:      */     private MRProtos.TaskAttemptCompletionEventStatusProto status_;
/* 12409:      */     public static final int MAP_OUTPUT_SERVER_ADDRESS_FIELD_NUMBER = 3;
/* 12410:      */     private Object mapOutputServerAddress_;
/* 12411:      */     public static final int ATTEMPT_RUN_TIME_FIELD_NUMBER = 4;
/* 12412:      */     private int attemptRunTime_;
/* 12413:      */     public static final int EVENT_ID_FIELD_NUMBER = 5;
/* 12414:      */     private int eventId_;
/* 12415:      */     
/* 12416:      */     public Parser<TaskAttemptCompletionEventProto> getParserForType()
/* 12417:      */     {
/* 12418:13282 */       return PARSER;
/* 12419:      */     }
/* 12420:      */     
/* 12421:      */     public boolean hasAttemptId()
/* 12422:      */     {
/* 12423:13293 */       return (this.bitField0_ & 0x1) == 1;
/* 12424:      */     }
/* 12425:      */     
/* 12426:      */     public MRProtos.TaskAttemptIdProto getAttemptId()
/* 12427:      */     {
/* 12428:13299 */       return this.attemptId_;
/* 12429:      */     }
/* 12430:      */     
/* 12431:      */     public MRProtos.TaskAttemptIdProtoOrBuilder getAttemptIdOrBuilder()
/* 12432:      */     {
/* 12433:13305 */       return this.attemptId_;
/* 12434:      */     }
/* 12435:      */     
/* 12436:      */     public boolean hasStatus()
/* 12437:      */     {
/* 12438:13315 */       return (this.bitField0_ & 0x2) == 2;
/* 12439:      */     }
/* 12440:      */     
/* 12441:      */     public MRProtos.TaskAttemptCompletionEventStatusProto getStatus()
/* 12442:      */     {
/* 12443:13321 */       return this.status_;
/* 12444:      */     }
/* 12445:      */     
/* 12446:      */     public boolean hasMapOutputServerAddress()
/* 12447:      */     {
/* 12448:13331 */       return (this.bitField0_ & 0x4) == 4;
/* 12449:      */     }
/* 12450:      */     
/* 12451:      */     public String getMapOutputServerAddress()
/* 12452:      */     {
/* 12453:13337 */       Object ref = this.mapOutputServerAddress_;
/* 12454:13338 */       if ((ref instanceof String)) {
/* 12455:13339 */         return (String)ref;
/* 12456:      */       }
/* 12457:13341 */       ByteString bs = (ByteString)ref;
/* 12458:      */       
/* 12459:13343 */       String s = bs.toStringUtf8();
/* 12460:13344 */       if (bs.isValidUtf8()) {
/* 12461:13345 */         this.mapOutputServerAddress_ = s;
/* 12462:      */       }
/* 12463:13347 */       return s;
/* 12464:      */     }
/* 12465:      */     
/* 12466:      */     public ByteString getMapOutputServerAddressBytes()
/* 12467:      */     {
/* 12468:13355 */       Object ref = this.mapOutputServerAddress_;
/* 12469:13356 */       if ((ref instanceof String))
/* 12470:      */       {
/* 12471:13357 */         ByteString b = ByteString.copyFromUtf8((String)ref);
/* 12472:      */         
/* 12473:      */ 
/* 12474:13360 */         this.mapOutputServerAddress_ = b;
/* 12475:13361 */         return b;
/* 12476:      */       }
/* 12477:13363 */       return (ByteString)ref;
/* 12478:      */     }
/* 12479:      */     
/* 12480:      */     public boolean hasAttemptRunTime()
/* 12481:      */     {
/* 12482:13374 */       return (this.bitField0_ & 0x8) == 8;
/* 12483:      */     }
/* 12484:      */     
/* 12485:      */     public int getAttemptRunTime()
/* 12486:      */     {
/* 12487:13380 */       return this.attemptRunTime_;
/* 12488:      */     }
/* 12489:      */     
/* 12490:      */     public boolean hasEventId()
/* 12491:      */     {
/* 12492:13390 */       return (this.bitField0_ & 0x10) == 16;
/* 12493:      */     }
/* 12494:      */     
/* 12495:      */     public int getEventId()
/* 12496:      */     {
/* 12497:13396 */       return this.eventId_;
/* 12498:      */     }
/* 12499:      */     
/* 12500:      */     private void initFields()
/* 12501:      */     {
/* 12502:13400 */       this.attemptId_ = MRProtos.TaskAttemptIdProto.getDefaultInstance();
/* 12503:13401 */       this.status_ = MRProtos.TaskAttemptCompletionEventStatusProto.TACE_FAILED;
/* 12504:13402 */       this.mapOutputServerAddress_ = "";
/* 12505:13403 */       this.attemptRunTime_ = 0;
/* 12506:13404 */       this.eventId_ = 0;
/* 12507:      */     }
/* 12508:      */     
/* 12509:13406 */     private byte memoizedIsInitialized = -1;
/* 12510:      */     
/* 12511:      */     public final boolean isInitialized()
/* 12512:      */     {
/* 12513:13408 */       byte isInitialized = this.memoizedIsInitialized;
/* 12514:13409 */       if (isInitialized != -1) {
/* 12515:13409 */         return isInitialized == 1;
/* 12516:      */       }
/* 12517:13411 */       this.memoizedIsInitialized = 1;
/* 12518:13412 */       return true;
/* 12519:      */     }
/* 12520:      */     
/* 12521:      */     public void writeTo(CodedOutputStream output)
/* 12522:      */       throws IOException
/* 12523:      */     {
/* 12524:13417 */       getSerializedSize();
/* 12525:13418 */       if ((this.bitField0_ & 0x1) == 1) {
/* 12526:13419 */         output.writeMessage(1, this.attemptId_);
/* 12527:      */       }
/* 12528:13421 */       if ((this.bitField0_ & 0x2) == 2) {
/* 12529:13422 */         output.writeEnum(2, this.status_.getNumber());
/* 12530:      */       }
/* 12531:13424 */       if ((this.bitField0_ & 0x4) == 4) {
/* 12532:13425 */         output.writeBytes(3, getMapOutputServerAddressBytes());
/* 12533:      */       }
/* 12534:13427 */       if ((this.bitField0_ & 0x8) == 8) {
/* 12535:13428 */         output.writeInt32(4, this.attemptRunTime_);
/* 12536:      */       }
/* 12537:13430 */       if ((this.bitField0_ & 0x10) == 16) {
/* 12538:13431 */         output.writeInt32(5, this.eventId_);
/* 12539:      */       }
/* 12540:13433 */       getUnknownFields().writeTo(output);
/* 12541:      */     }
/* 12542:      */     
/* 12543:13436 */     private int memoizedSerializedSize = -1;
/* 12544:      */     private static final long serialVersionUID = 0L;
/* 12545:      */     
/* 12546:      */     public int getSerializedSize()
/* 12547:      */     {
/* 12548:13438 */       int size = this.memoizedSerializedSize;
/* 12549:13439 */       if (size != -1) {
/* 12550:13439 */         return size;
/* 12551:      */       }
/* 12552:13441 */       size = 0;
/* 12553:13442 */       if ((this.bitField0_ & 0x1) == 1) {
/* 12554:13443 */         size += CodedOutputStream.computeMessageSize(1, this.attemptId_);
/* 12555:      */       }
/* 12556:13446 */       if ((this.bitField0_ & 0x2) == 2) {
/* 12557:13447 */         size += CodedOutputStream.computeEnumSize(2, this.status_.getNumber());
/* 12558:      */       }
/* 12559:13450 */       if ((this.bitField0_ & 0x4) == 4) {
/* 12560:13451 */         size += CodedOutputStream.computeBytesSize(3, getMapOutputServerAddressBytes());
/* 12561:      */       }
/* 12562:13454 */       if ((this.bitField0_ & 0x8) == 8) {
/* 12563:13455 */         size += CodedOutputStream.computeInt32Size(4, this.attemptRunTime_);
/* 12564:      */       }
/* 12565:13458 */       if ((this.bitField0_ & 0x10) == 16) {
/* 12566:13459 */         size += CodedOutputStream.computeInt32Size(5, this.eventId_);
/* 12567:      */       }
/* 12568:13462 */       size += getUnknownFields().getSerializedSize();
/* 12569:13463 */       this.memoizedSerializedSize = size;
/* 12570:13464 */       return size;
/* 12571:      */     }
/* 12572:      */     
/* 12573:      */     protected Object writeReplace()
/* 12574:      */       throws ObjectStreamException
/* 12575:      */     {
/* 12576:13471 */       return super.writeReplace();
/* 12577:      */     }
/* 12578:      */     
/* 12579:      */     public boolean equals(Object obj)
/* 12580:      */     {
/* 12581:13476 */       if (obj == this) {
/* 12582:13477 */         return true;
/* 12583:      */       }
/* 12584:13479 */       if (!(obj instanceof TaskAttemptCompletionEventProto)) {
/* 12585:13480 */         return super.equals(obj);
/* 12586:      */       }
/* 12587:13482 */       TaskAttemptCompletionEventProto other = (TaskAttemptCompletionEventProto)obj;
/* 12588:      */       
/* 12589:13484 */       boolean result = true;
/* 12590:13485 */       result = (result) && (hasAttemptId() == other.hasAttemptId());
/* 12591:13486 */       if (hasAttemptId()) {
/* 12592:13487 */         result = (result) && (getAttemptId().equals(other.getAttemptId()));
/* 12593:      */       }
/* 12594:13490 */       result = (result) && (hasStatus() == other.hasStatus());
/* 12595:13491 */       if (hasStatus()) {
/* 12596:13492 */         result = (result) && (getStatus() == other.getStatus());
/* 12597:      */       }
/* 12598:13495 */       result = (result) && (hasMapOutputServerAddress() == other.hasMapOutputServerAddress());
/* 12599:13496 */       if (hasMapOutputServerAddress()) {
/* 12600:13497 */         result = (result) && (getMapOutputServerAddress().equals(other.getMapOutputServerAddress()));
/* 12601:      */       }
/* 12602:13500 */       result = (result) && (hasAttemptRunTime() == other.hasAttemptRunTime());
/* 12603:13501 */       if (hasAttemptRunTime()) {
/* 12604:13502 */         result = (result) && (getAttemptRunTime() == other.getAttemptRunTime());
/* 12605:      */       }
/* 12606:13505 */       result = (result) && (hasEventId() == other.hasEventId());
/* 12607:13506 */       if (hasEventId()) {
/* 12608:13507 */         result = (result) && (getEventId() == other.getEventId());
/* 12609:      */       }
/* 12610:13510 */       result = (result) && (getUnknownFields().equals(other.getUnknownFields()));
/* 12611:      */       
/* 12612:13512 */       return result;
/* 12613:      */     }
/* 12614:      */     
/* 12615:13515 */     private int memoizedHashCode = 0;
/* 12616:      */     
/* 12617:      */     public int hashCode()
/* 12618:      */     {
/* 12619:13518 */       if (this.memoizedHashCode != 0) {
/* 12620:13519 */         return this.memoizedHashCode;
/* 12621:      */       }
/* 12622:13521 */       int hash = 41;
/* 12623:13522 */       hash = 19 * hash + getDescriptorForType().hashCode();
/* 12624:13523 */       if (hasAttemptId())
/* 12625:      */       {
/* 12626:13524 */         hash = 37 * hash + 1;
/* 12627:13525 */         hash = 53 * hash + getAttemptId().hashCode();
/* 12628:      */       }
/* 12629:13527 */       if (hasStatus())
/* 12630:      */       {
/* 12631:13528 */         hash = 37 * hash + 2;
/* 12632:13529 */         hash = 53 * hash + hashEnum(getStatus());
/* 12633:      */       }
/* 12634:13531 */       if (hasMapOutputServerAddress())
/* 12635:      */       {
/* 12636:13532 */         hash = 37 * hash + 3;
/* 12637:13533 */         hash = 53 * hash + getMapOutputServerAddress().hashCode();
/* 12638:      */       }
/* 12639:13535 */       if (hasAttemptRunTime())
/* 12640:      */       {
/* 12641:13536 */         hash = 37 * hash + 4;
/* 12642:13537 */         hash = 53 * hash + getAttemptRunTime();
/* 12643:      */       }
/* 12644:13539 */       if (hasEventId())
/* 12645:      */       {
/* 12646:13540 */         hash = 37 * hash + 5;
/* 12647:13541 */         hash = 53 * hash + getEventId();
/* 12648:      */       }
/* 12649:13543 */       hash = 29 * hash + getUnknownFields().hashCode();
/* 12650:13544 */       this.memoizedHashCode = hash;
/* 12651:13545 */       return hash;
/* 12652:      */     }
/* 12653:      */     
/* 12654:      */     public static TaskAttemptCompletionEventProto parseFrom(ByteString data)
/* 12655:      */       throws InvalidProtocolBufferException
/* 12656:      */     {
/* 12657:13551 */       return (TaskAttemptCompletionEventProto)PARSER.parseFrom(data);
/* 12658:      */     }
/* 12659:      */     
/* 12660:      */     public static TaskAttemptCompletionEventProto parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry)
/* 12661:      */       throws InvalidProtocolBufferException
/* 12662:      */     {
/* 12663:13557 */       return (TaskAttemptCompletionEventProto)PARSER.parseFrom(data, extensionRegistry);
/* 12664:      */     }
/* 12665:      */     
/* 12666:      */     public static TaskAttemptCompletionEventProto parseFrom(byte[] data)
/* 12667:      */       throws InvalidProtocolBufferException
/* 12668:      */     {
/* 12669:13561 */       return (TaskAttemptCompletionEventProto)PARSER.parseFrom(data);
/* 12670:      */     }
/* 12671:      */     
/* 12672:      */     public static TaskAttemptCompletionEventProto parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry)
/* 12673:      */       throws InvalidProtocolBufferException
/* 12674:      */     {
/* 12675:13567 */       return (TaskAttemptCompletionEventProto)PARSER.parseFrom(data, extensionRegistry);
/* 12676:      */     }
/* 12677:      */     
/* 12678:      */     public static TaskAttemptCompletionEventProto parseFrom(InputStream input)
/* 12679:      */       throws IOException
/* 12680:      */     {
/* 12681:13571 */       return (TaskAttemptCompletionEventProto)PARSER.parseFrom(input);
/* 12682:      */     }
/* 12683:      */     
/* 12684:      */     public static TaskAttemptCompletionEventProto parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry)
/* 12685:      */       throws IOException
/* 12686:      */     {
/* 12687:13577 */       return (TaskAttemptCompletionEventProto)PARSER.parseFrom(input, extensionRegistry);
/* 12688:      */     }
/* 12689:      */     
/* 12690:      */     public static TaskAttemptCompletionEventProto parseDelimitedFrom(InputStream input)
/* 12691:      */       throws IOException
/* 12692:      */     {
/* 12693:13581 */       return (TaskAttemptCompletionEventProto)PARSER.parseDelimitedFrom(input);
/* 12694:      */     }
/* 12695:      */     
/* 12696:      */     public static TaskAttemptCompletionEventProto parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry)
/* 12697:      */       throws IOException
/* 12698:      */     {
/* 12699:13587 */       return (TaskAttemptCompletionEventProto)PARSER.parseDelimitedFrom(input, extensionRegistry);
/* 12700:      */     }
/* 12701:      */     
/* 12702:      */     public static TaskAttemptCompletionEventProto parseFrom(CodedInputStream input)
/* 12703:      */       throws IOException
/* 12704:      */     {
/* 12705:13592 */       return (TaskAttemptCompletionEventProto)PARSER.parseFrom(input);
/* 12706:      */     }
/* 12707:      */     
/* 12708:      */     public static TaskAttemptCompletionEventProto parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry)
/* 12709:      */       throws IOException
/* 12710:      */     {
/* 12711:13598 */       return (TaskAttemptCompletionEventProto)PARSER.parseFrom(input, extensionRegistry);
/* 12712:      */     }
/* 12713:      */     
/* 12714:      */     public static Builder newBuilder()
/* 12715:      */     {
/* 12716:13601 */       return Builder.access$14600();
/* 12717:      */     }
/* 12718:      */     
/* 12719:      */     public Builder newBuilderForType()
/* 12720:      */     {
/* 12721:13602 */       return newBuilder();
/* 12722:      */     }
/* 12723:      */     
/* 12724:      */     public static Builder newBuilder(TaskAttemptCompletionEventProto prototype)
/* 12725:      */     {
/* 12726:13604 */       return newBuilder().mergeFrom(prototype);
/* 12727:      */     }
/* 12728:      */     
/* 12729:      */     public Builder toBuilder()
/* 12730:      */     {
/* 12731:13606 */       return newBuilder(this);
/* 12732:      */     }
/* 12733:      */     
/* 12734:      */     protected Builder newBuilderForType(GeneratedMessage.BuilderParent parent)
/* 12735:      */     {
/* 12736:13611 */       Builder builder = new Builder(parent, null);
/* 12737:13612 */       return builder;
/* 12738:      */     }
/* 12739:      */     
/* 12740:      */     public static final class Builder
/* 12741:      */       extends GeneratedMessage.Builder<Builder>
/* 12742:      */       implements MRProtos.TaskAttemptCompletionEventProtoOrBuilder
/* 12743:      */     {
/* 12744:      */       private int bitField0_;
/* 12745:      */       
/* 12746:      */       public static final Descriptors.Descriptor getDescriptor()
/* 12747:      */       {
/* 12748:13622 */         return MRProtos.internal_static_hadoop_mapreduce_TaskAttemptCompletionEventProto_descriptor;
/* 12749:      */       }
/* 12750:      */       
/* 12751:      */       protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable()
/* 12752:      */       {
/* 12753:13627 */         return MRProtos.internal_static_hadoop_mapreduce_TaskAttemptCompletionEventProto_fieldAccessorTable.ensureFieldAccessorsInitialized(MRProtos.TaskAttemptCompletionEventProto.class, Builder.class);
/* 12754:      */       }
/* 12755:      */       
/* 12756:      */       private Builder()
/* 12757:      */       {
/* 12758:13634 */         maybeForceBuilderInitialization();
/* 12759:      */       }
/* 12760:      */       
/* 12761:      */       private Builder(GeneratedMessage.BuilderParent parent)
/* 12762:      */       {
/* 12763:13639 */         super();
/* 12764:13640 */         maybeForceBuilderInitialization();
/* 12765:      */       }
/* 12766:      */       
/* 12767:      */       private void maybeForceBuilderInitialization()
/* 12768:      */       {
/* 12769:13643 */         if (MRProtos.TaskAttemptCompletionEventProto.alwaysUseFieldBuilders) {
/* 12770:13644 */           getAttemptIdFieldBuilder();
/* 12771:      */         }
/* 12772:      */       }
/* 12773:      */       
/* 12774:      */       private static Builder create()
/* 12775:      */       {
/* 12776:13648 */         return new Builder();
/* 12777:      */       }
/* 12778:      */       
/* 12779:      */       public Builder clear()
/* 12780:      */       {
/* 12781:13652 */         super.clear();
/* 12782:13653 */         if (this.attemptIdBuilder_ == null) {
/* 12783:13654 */           this.attemptId_ = MRProtos.TaskAttemptIdProto.getDefaultInstance();
/* 12784:      */         } else {
/* 12785:13656 */           this.attemptIdBuilder_.clear();
/* 12786:      */         }
/* 12787:13658 */         this.bitField0_ &= 0xFFFFFFFE;
/* 12788:13659 */         this.status_ = MRProtos.TaskAttemptCompletionEventStatusProto.TACE_FAILED;
/* 12789:13660 */         this.bitField0_ &= 0xFFFFFFFD;
/* 12790:13661 */         this.mapOutputServerAddress_ = "";
/* 12791:13662 */         this.bitField0_ &= 0xFFFFFFFB;
/* 12792:13663 */         this.attemptRunTime_ = 0;
/* 12793:13664 */         this.bitField0_ &= 0xFFFFFFF7;
/* 12794:13665 */         this.eventId_ = 0;
/* 12795:13666 */         this.bitField0_ &= 0xFFFFFFEF;
/* 12796:13667 */         return this;
/* 12797:      */       }
/* 12798:      */       
/* 12799:      */       public Builder clone()
/* 12800:      */       {
/* 12801:13671 */         return create().mergeFrom(buildPartial());
/* 12802:      */       }
/* 12803:      */       
/* 12804:      */       public Descriptors.Descriptor getDescriptorForType()
/* 12805:      */       {
/* 12806:13676 */         return MRProtos.internal_static_hadoop_mapreduce_TaskAttemptCompletionEventProto_descriptor;
/* 12807:      */       }
/* 12808:      */       
/* 12809:      */       public MRProtos.TaskAttemptCompletionEventProto getDefaultInstanceForType()
/* 12810:      */       {
/* 12811:13680 */         return MRProtos.TaskAttemptCompletionEventProto.getDefaultInstance();
/* 12812:      */       }
/* 12813:      */       
/* 12814:      */       public MRProtos.TaskAttemptCompletionEventProto build()
/* 12815:      */       {
/* 12816:13684 */         MRProtos.TaskAttemptCompletionEventProto result = buildPartial();
/* 12817:13685 */         if (!result.isInitialized()) {
/* 12818:13686 */           throw newUninitializedMessageException(result);
/* 12819:      */         }
/* 12820:13688 */         return result;
/* 12821:      */       }
/* 12822:      */       
/* 12823:      */       public MRProtos.TaskAttemptCompletionEventProto buildPartial()
/* 12824:      */       {
/* 12825:13692 */         MRProtos.TaskAttemptCompletionEventProto result = new MRProtos.TaskAttemptCompletionEventProto(this, null);
/* 12826:13693 */         int from_bitField0_ = this.bitField0_;
/* 12827:13694 */         int to_bitField0_ = 0;
/* 12828:13695 */         if ((from_bitField0_ & 0x1) == 1) {
/* 12829:13696 */           to_bitField0_ |= 0x1;
/* 12830:      */         }
/* 12831:13698 */         if (this.attemptIdBuilder_ == null) {
/* 12832:13699 */           result.attemptId_ = this.attemptId_;
/* 12833:      */         } else {
/* 12834:13701 */           result.attemptId_ = ((MRProtos.TaskAttemptIdProto)this.attemptIdBuilder_.build());
/* 12835:      */         }
/* 12836:13703 */         if ((from_bitField0_ & 0x2) == 2) {
/* 12837:13704 */           to_bitField0_ |= 0x2;
/* 12838:      */         }
/* 12839:13706 */         result.status_ = this.status_;
/* 12840:13707 */         if ((from_bitField0_ & 0x4) == 4) {
/* 12841:13708 */           to_bitField0_ |= 0x4;
/* 12842:      */         }
/* 12843:13710 */         result.mapOutputServerAddress_ = this.mapOutputServerAddress_;
/* 12844:13711 */         if ((from_bitField0_ & 0x8) == 8) {
/* 12845:13712 */           to_bitField0_ |= 0x8;
/* 12846:      */         }
/* 12847:13714 */         result.attemptRunTime_ = this.attemptRunTime_;
/* 12848:13715 */         if ((from_bitField0_ & 0x10) == 16) {
/* 12849:13716 */           to_bitField0_ |= 0x10;
/* 12850:      */         }
/* 12851:13718 */         result.eventId_ = this.eventId_;
/* 12852:13719 */         result.bitField0_ = to_bitField0_;
/* 12853:13720 */         onBuilt();
/* 12854:13721 */         return result;
/* 12855:      */       }
/* 12856:      */       
/* 12857:      */       public Builder mergeFrom(Message other)
/* 12858:      */       {
/* 12859:13725 */         if ((other instanceof MRProtos.TaskAttemptCompletionEventProto)) {
/* 12860:13726 */           return mergeFrom((MRProtos.TaskAttemptCompletionEventProto)other);
/* 12861:      */         }
/* 12862:13728 */         super.mergeFrom(other);
/* 12863:13729 */         return this;
/* 12864:      */       }
/* 12865:      */       
/* 12866:      */       public Builder mergeFrom(MRProtos.TaskAttemptCompletionEventProto other)
/* 12867:      */       {
/* 12868:13734 */         if (other == MRProtos.TaskAttemptCompletionEventProto.getDefaultInstance()) {
/* 12869:13734 */           return this;
/* 12870:      */         }
/* 12871:13735 */         if (other.hasAttemptId()) {
/* 12872:13736 */           mergeAttemptId(other.getAttemptId());
/* 12873:      */         }
/* 12874:13738 */         if (other.hasStatus()) {
/* 12875:13739 */           setStatus(other.getStatus());
/* 12876:      */         }
/* 12877:13741 */         if (other.hasMapOutputServerAddress())
/* 12878:      */         {
/* 12879:13742 */           this.bitField0_ |= 0x4;
/* 12880:13743 */           this.mapOutputServerAddress_ = other.mapOutputServerAddress_;
/* 12881:13744 */           onChanged();
/* 12882:      */         }
/* 12883:13746 */         if (other.hasAttemptRunTime()) {
/* 12884:13747 */           setAttemptRunTime(other.getAttemptRunTime());
/* 12885:      */         }
/* 12886:13749 */         if (other.hasEventId()) {
/* 12887:13750 */           setEventId(other.getEventId());
/* 12888:      */         }
/* 12889:13752 */         mergeUnknownFields(other.getUnknownFields());
/* 12890:13753 */         return this;
/* 12891:      */       }
/* 12892:      */       
/* 12893:      */       public final boolean isInitialized()
/* 12894:      */       {
/* 12895:13757 */         return true;
/* 12896:      */       }
/* 12897:      */       
/* 12898:      */       public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry)
/* 12899:      */         throws IOException
/* 12900:      */       {
/* 12901:13764 */         MRProtos.TaskAttemptCompletionEventProto parsedMessage = null;
/* 12902:      */         try
/* 12903:      */         {
/* 12904:13766 */           parsedMessage = (MRProtos.TaskAttemptCompletionEventProto)MRProtos.TaskAttemptCompletionEventProto.PARSER.parsePartialFrom(input, extensionRegistry);
/* 12905:      */         }
/* 12906:      */         catch (InvalidProtocolBufferException e)
/* 12907:      */         {
/* 12908:13768 */           parsedMessage = (MRProtos.TaskAttemptCompletionEventProto)e.getUnfinishedMessage();
/* 12909:13769 */           throw e;
/* 12910:      */         }
/* 12911:      */         finally
/* 12912:      */         {
/* 12913:13771 */           if (parsedMessage != null) {
/* 12914:13772 */             mergeFrom(parsedMessage);
/* 12915:      */           }
/* 12916:      */         }
/* 12917:13775 */         return this;
/* 12918:      */       }
/* 12919:      */       
/* 12920:13780 */       private MRProtos.TaskAttemptIdProto attemptId_ = MRProtos.TaskAttemptIdProto.getDefaultInstance();
/* 12921:      */       private SingleFieldBuilder<MRProtos.TaskAttemptIdProto, MRProtos.TaskAttemptIdProto.Builder, MRProtos.TaskAttemptIdProtoOrBuilder> attemptIdBuilder_;
/* 12922:      */       
/* 12923:      */       public boolean hasAttemptId()
/* 12924:      */       {
/* 12925:13787 */         return (this.bitField0_ & 0x1) == 1;
/* 12926:      */       }
/* 12927:      */       
/* 12928:      */       public MRProtos.TaskAttemptIdProto getAttemptId()
/* 12929:      */       {
/* 12930:13793 */         if (this.attemptIdBuilder_ == null) {
/* 12931:13794 */           return this.attemptId_;
/* 12932:      */         }
/* 12933:13796 */         return (MRProtos.TaskAttemptIdProto)this.attemptIdBuilder_.getMessage();
/* 12934:      */       }
/* 12935:      */       
/* 12936:      */       public Builder setAttemptId(MRProtos.TaskAttemptIdProto value)
/* 12937:      */       {
/* 12938:13803 */         if (this.attemptIdBuilder_ == null)
/* 12939:      */         {
/* 12940:13804 */           if (value == null) {
/* 12941:13805 */             throw new NullPointerException();
/* 12942:      */           }
/* 12943:13807 */           this.attemptId_ = value;
/* 12944:13808 */           onChanged();
/* 12945:      */         }
/* 12946:      */         else
/* 12947:      */         {
/* 12948:13810 */           this.attemptIdBuilder_.setMessage(value);
/* 12949:      */         }
/* 12950:13812 */         this.bitField0_ |= 0x1;
/* 12951:13813 */         return this;
/* 12952:      */       }
/* 12953:      */       
/* 12954:      */       public Builder setAttemptId(MRProtos.TaskAttemptIdProto.Builder builderForValue)
/* 12955:      */       {
/* 12956:13820 */         if (this.attemptIdBuilder_ == null)
/* 12957:      */         {
/* 12958:13821 */           this.attemptId_ = builderForValue.build();
/* 12959:13822 */           onChanged();
/* 12960:      */         }
/* 12961:      */         else
/* 12962:      */         {
/* 12963:13824 */           this.attemptIdBuilder_.setMessage(builderForValue.build());
/* 12964:      */         }
/* 12965:13826 */         this.bitField0_ |= 0x1;
/* 12966:13827 */         return this;
/* 12967:      */       }
/* 12968:      */       
/* 12969:      */       public Builder mergeAttemptId(MRProtos.TaskAttemptIdProto value)
/* 12970:      */       {
/* 12971:13833 */         if (this.attemptIdBuilder_ == null)
/* 12972:      */         {
/* 12973:13834 */           if (((this.bitField0_ & 0x1) == 1) && (this.attemptId_ != MRProtos.TaskAttemptIdProto.getDefaultInstance())) {
/* 12974:13836 */             this.attemptId_ = MRProtos.TaskAttemptIdProto.newBuilder(this.attemptId_).mergeFrom(value).buildPartial();
/* 12975:      */           } else {
/* 12976:13839 */             this.attemptId_ = value;
/* 12977:      */           }
/* 12978:13841 */           onChanged();
/* 12979:      */         }
/* 12980:      */         else
/* 12981:      */         {
/* 12982:13843 */           this.attemptIdBuilder_.mergeFrom(value);
/* 12983:      */         }
/* 12984:13845 */         this.bitField0_ |= 0x1;
/* 12985:13846 */         return this;
/* 12986:      */       }
/* 12987:      */       
/* 12988:      */       public Builder clearAttemptId()
/* 12989:      */       {
/* 12990:13852 */         if (this.attemptIdBuilder_ == null)
/* 12991:      */         {
/* 12992:13853 */           this.attemptId_ = MRProtos.TaskAttemptIdProto.getDefaultInstance();
/* 12993:13854 */           onChanged();
/* 12994:      */         }
/* 12995:      */         else
/* 12996:      */         {
/* 12997:13856 */           this.attemptIdBuilder_.clear();
/* 12998:      */         }
/* 12999:13858 */         this.bitField0_ &= 0xFFFFFFFE;
/* 13000:13859 */         return this;
/* 13001:      */       }
/* 13002:      */       
/* 13003:      */       public MRProtos.TaskAttemptIdProto.Builder getAttemptIdBuilder()
/* 13004:      */       {
/* 13005:13865 */         this.bitField0_ |= 0x1;
/* 13006:13866 */         onChanged();
/* 13007:13867 */         return (MRProtos.TaskAttemptIdProto.Builder)getAttemptIdFieldBuilder().getBuilder();
/* 13008:      */       }
/* 13009:      */       
/* 13010:      */       public MRProtos.TaskAttemptIdProtoOrBuilder getAttemptIdOrBuilder()
/* 13011:      */       {
/* 13012:13873 */         if (this.attemptIdBuilder_ != null) {
/* 13013:13874 */           return (MRProtos.TaskAttemptIdProtoOrBuilder)this.attemptIdBuilder_.getMessageOrBuilder();
/* 13014:      */         }
/* 13015:13876 */         return this.attemptId_;
/* 13016:      */       }
/* 13017:      */       
/* 13018:      */       private SingleFieldBuilder<MRProtos.TaskAttemptIdProto, MRProtos.TaskAttemptIdProto.Builder, MRProtos.TaskAttemptIdProtoOrBuilder> getAttemptIdFieldBuilder()
/* 13019:      */       {
/* 13020:13885 */         if (this.attemptIdBuilder_ == null)
/* 13021:      */         {
/* 13022:13886 */           this.attemptIdBuilder_ = new SingleFieldBuilder(this.attemptId_, getParentForChildren(), isClean());
/* 13023:      */           
/* 13024:      */ 
/* 13025:      */ 
/* 13026:      */ 
/* 13027:13891 */           this.attemptId_ = null;
/* 13028:      */         }
/* 13029:13893 */         return this.attemptIdBuilder_;
/* 13030:      */       }
/* 13031:      */       
/* 13032:13897 */       private MRProtos.TaskAttemptCompletionEventStatusProto status_ = MRProtos.TaskAttemptCompletionEventStatusProto.TACE_FAILED;
/* 13033:      */       
/* 13034:      */       public boolean hasStatus()
/* 13035:      */       {
/* 13036:13902 */         return (this.bitField0_ & 0x2) == 2;
/* 13037:      */       }
/* 13038:      */       
/* 13039:      */       public MRProtos.TaskAttemptCompletionEventStatusProto getStatus()
/* 13040:      */       {
/* 13041:13908 */         return this.status_;
/* 13042:      */       }
/* 13043:      */       
/* 13044:      */       public Builder setStatus(MRProtos.TaskAttemptCompletionEventStatusProto value)
/* 13045:      */       {
/* 13046:13914 */         if (value == null) {
/* 13047:13915 */           throw new NullPointerException();
/* 13048:      */         }
/* 13049:13917 */         this.bitField0_ |= 0x2;
/* 13050:13918 */         this.status_ = value;
/* 13051:13919 */         onChanged();
/* 13052:13920 */         return this;
/* 13053:      */       }
/* 13054:      */       
/* 13055:      */       public Builder clearStatus()
/* 13056:      */       {
/* 13057:13926 */         this.bitField0_ &= 0xFFFFFFFD;
/* 13058:13927 */         this.status_ = MRProtos.TaskAttemptCompletionEventStatusProto.TACE_FAILED;
/* 13059:13928 */         onChanged();
/* 13060:13929 */         return this;
/* 13061:      */       }
/* 13062:      */       
/* 13063:13933 */       private Object mapOutputServerAddress_ = "";
/* 13064:      */       private int attemptRunTime_;
/* 13065:      */       private int eventId_;
/* 13066:      */       
/* 13067:      */       public boolean hasMapOutputServerAddress()
/* 13068:      */       {
/* 13069:13938 */         return (this.bitField0_ & 0x4) == 4;
/* 13070:      */       }
/* 13071:      */       
/* 13072:      */       public String getMapOutputServerAddress()
/* 13073:      */       {
/* 13074:13944 */         Object ref = this.mapOutputServerAddress_;
/* 13075:13945 */         if (!(ref instanceof String))
/* 13076:      */         {
/* 13077:13946 */           String s = ((ByteString)ref).toStringUtf8();
/* 13078:      */           
/* 13079:13948 */           this.mapOutputServerAddress_ = s;
/* 13080:13949 */           return s;
/* 13081:      */         }
/* 13082:13951 */         return (String)ref;
/* 13083:      */       }
/* 13084:      */       
/* 13085:      */       public ByteString getMapOutputServerAddressBytes()
/* 13086:      */       {
/* 13087:13959 */         Object ref = this.mapOutputServerAddress_;
/* 13088:13960 */         if ((ref instanceof String))
/* 13089:      */         {
/* 13090:13961 */           ByteString b = ByteString.copyFromUtf8((String)ref);
/* 13091:      */           
/* 13092:      */ 
/* 13093:13964 */           this.mapOutputServerAddress_ = b;
/* 13094:13965 */           return b;
/* 13095:      */         }
/* 13096:13967 */         return (ByteString)ref;
/* 13097:      */       }
/* 13098:      */       
/* 13099:      */       public Builder setMapOutputServerAddress(String value)
/* 13100:      */       {
/* 13101:13975 */         if (value == null) {
/* 13102:13976 */           throw new NullPointerException();
/* 13103:      */         }
/* 13104:13978 */         this.bitField0_ |= 0x4;
/* 13105:13979 */         this.mapOutputServerAddress_ = value;
/* 13106:13980 */         onChanged();
/* 13107:13981 */         return this;
/* 13108:      */       }
/* 13109:      */       
/* 13110:      */       public Builder clearMapOutputServerAddress()
/* 13111:      */       {
/* 13112:13987 */         this.bitField0_ &= 0xFFFFFFFB;
/* 13113:13988 */         this.mapOutputServerAddress_ = MRProtos.TaskAttemptCompletionEventProto.getDefaultInstance().getMapOutputServerAddress();
/* 13114:13989 */         onChanged();
/* 13115:13990 */         return this;
/* 13116:      */       }
/* 13117:      */       
/* 13118:      */       public Builder setMapOutputServerAddressBytes(ByteString value)
/* 13119:      */       {
/* 13120:13997 */         if (value == null) {
/* 13121:13998 */           throw new NullPointerException();
/* 13122:      */         }
/* 13123:14000 */         this.bitField0_ |= 0x4;
/* 13124:14001 */         this.mapOutputServerAddress_ = value;
/* 13125:14002 */         onChanged();
/* 13126:14003 */         return this;
/* 13127:      */       }
/* 13128:      */       
/* 13129:      */       public boolean hasAttemptRunTime()
/* 13130:      */       {
/* 13131:14012 */         return (this.bitField0_ & 0x8) == 8;
/* 13132:      */       }
/* 13133:      */       
/* 13134:      */       public int getAttemptRunTime()
/* 13135:      */       {
/* 13136:14018 */         return this.attemptRunTime_;
/* 13137:      */       }
/* 13138:      */       
/* 13139:      */       public Builder setAttemptRunTime(int value)
/* 13140:      */       {
/* 13141:14024 */         this.bitField0_ |= 0x8;
/* 13142:14025 */         this.attemptRunTime_ = value;
/* 13143:14026 */         onChanged();
/* 13144:14027 */         return this;
/* 13145:      */       }
/* 13146:      */       
/* 13147:      */       public Builder clearAttemptRunTime()
/* 13148:      */       {
/* 13149:14033 */         this.bitField0_ &= 0xFFFFFFF7;
/* 13150:14034 */         this.attemptRunTime_ = 0;
/* 13151:14035 */         onChanged();
/* 13152:14036 */         return this;
/* 13153:      */       }
/* 13154:      */       
/* 13155:      */       public boolean hasEventId()
/* 13156:      */       {
/* 13157:14045 */         return (this.bitField0_ & 0x10) == 16;
/* 13158:      */       }
/* 13159:      */       
/* 13160:      */       public int getEventId()
/* 13161:      */       {
/* 13162:14051 */         return this.eventId_;
/* 13163:      */       }
/* 13164:      */       
/* 13165:      */       public Builder setEventId(int value)
/* 13166:      */       {
/* 13167:14057 */         this.bitField0_ |= 0x10;
/* 13168:14058 */         this.eventId_ = value;
/* 13169:14059 */         onChanged();
/* 13170:14060 */         return this;
/* 13171:      */       }
/* 13172:      */       
/* 13173:      */       public Builder clearEventId()
/* 13174:      */       {
/* 13175:14066 */         this.bitField0_ &= 0xFFFFFFEF;
/* 13176:14067 */         this.eventId_ = 0;
/* 13177:14068 */         onChanged();
/* 13178:14069 */         return this;
/* 13179:      */       }
/* 13180:      */     }
/* 13181:      */     
/* 13182:      */     static
/* 13183:      */     {
/* 13184:14076 */       defaultInstance = new TaskAttemptCompletionEventProto(true);
/* 13185:14077 */       defaultInstance.initFields();
/* 13186:      */     }
/* 13187:      */   }
/* 13188:      */   
/* 13189:      */   public static abstract interface StringCounterMapProtoOrBuilder
/* 13190:      */     extends MessageOrBuilder
/* 13191:      */   {
/* 13192:      */     public abstract boolean hasKey();
/* 13193:      */     
/* 13194:      */     public abstract String getKey();
/* 13195:      */     
/* 13196:      */     public abstract ByteString getKeyBytes();
/* 13197:      */     
/* 13198:      */     public abstract boolean hasValue();
/* 13199:      */     
/* 13200:      */     public abstract MRProtos.CounterProto getValue();
/* 13201:      */     
/* 13202:      */     public abstract MRProtos.CounterProtoOrBuilder getValueOrBuilder();
/* 13203:      */   }
/* 13204:      */   
/* 13205:      */   public static final class StringCounterMapProto
/* 13206:      */     extends GeneratedMessage
/* 13207:      */     implements MRProtos.StringCounterMapProtoOrBuilder
/* 13208:      */   {
/* 13209:      */     private static final StringCounterMapProto defaultInstance;
/* 13210:      */     private final UnknownFieldSet unknownFields;
/* 13211:      */     
/* 13212:      */     private StringCounterMapProto(GeneratedMessage.Builder<?> builder)
/* 13213:      */     {
/* 13214:14123 */       super();
/* 13215:14124 */       this.unknownFields = builder.getUnknownFields();
/* 13216:      */     }
/* 13217:      */     
/* 13218:      */     private StringCounterMapProto(boolean noInit)
/* 13219:      */     {
/* 13220:14126 */       this.unknownFields = UnknownFieldSet.getDefaultInstance();
/* 13221:      */     }
/* 13222:      */     
/* 13223:      */     public static StringCounterMapProto getDefaultInstance()
/* 13224:      */     {
/* 13225:14130 */       return defaultInstance;
/* 13226:      */     }
/* 13227:      */     
/* 13228:      */     public StringCounterMapProto getDefaultInstanceForType()
/* 13229:      */     {
/* 13230:14134 */       return defaultInstance;
/* 13231:      */     }
/* 13232:      */     
/* 13233:      */     public final UnknownFieldSet getUnknownFields()
/* 13234:      */     {
/* 13235:14141 */       return this.unknownFields;
/* 13236:      */     }
/* 13237:      */     
/* 13238:      */     private StringCounterMapProto(CodedInputStream input, ExtensionRegistryLite extensionRegistry)
/* 13239:      */       throws InvalidProtocolBufferException
/* 13240:      */     {
/* 13241:14147 */       initFields();
/* 13242:14148 */       int mutable_bitField0_ = 0;
/* 13243:14149 */       UnknownFieldSet.Builder unknownFields = UnknownFieldSet.newBuilder();
/* 13244:      */       try
/* 13245:      */       {
/* 13246:14152 */         boolean done = false;
/* 13247:14153 */         while (!done)
/* 13248:      */         {
/* 13249:14154 */           int tag = input.readTag();
/* 13250:14155 */           switch (tag)
/* 13251:      */           {
/* 13252:      */           case 0: 
/* 13253:14157 */             done = true;
/* 13254:14158 */             break;
/* 13255:      */           default: 
/* 13256:14160 */             if (!parseUnknownField(input, unknownFields, extensionRegistry, tag)) {
/* 13257:14162 */               done = true;
/* 13258:      */             }
/* 13259:      */             break;
/* 13260:      */           case 10: 
/* 13261:14167 */             this.bitField0_ |= 0x1;
/* 13262:14168 */             this.key_ = input.readBytes();
/* 13263:14169 */             break;
/* 13264:      */           case 18: 
/* 13265:14172 */             MRProtos.CounterProto.Builder subBuilder = null;
/* 13266:14173 */             if ((this.bitField0_ & 0x2) == 2) {
/* 13267:14174 */               subBuilder = this.value_.toBuilder();
/* 13268:      */             }
/* 13269:14176 */             this.value_ = ((MRProtos.CounterProto)input.readMessage(MRProtos.CounterProto.PARSER, extensionRegistry));
/* 13270:14177 */             if (subBuilder != null)
/* 13271:      */             {
/* 13272:14178 */               subBuilder.mergeFrom(this.value_);
/* 13273:14179 */               this.value_ = subBuilder.buildPartial();
/* 13274:      */             }
/* 13275:14181 */             this.bitField0_ |= 0x2;
/* 13276:      */           }
/* 13277:      */         }
/* 13278:      */       }
/* 13279:      */       catch (InvalidProtocolBufferException e)
/* 13280:      */       {
/* 13281:14187 */         throw e.setUnfinishedMessage(this);
/* 13282:      */       }
/* 13283:      */       catch (IOException e)
/* 13284:      */       {
/* 13285:14189 */         throw new InvalidProtocolBufferException(e.getMessage()).setUnfinishedMessage(this);
/* 13286:      */       }
/* 13287:      */       finally
/* 13288:      */       {
/* 13289:14192 */         this.unknownFields = unknownFields.build();
/* 13290:14193 */         makeExtensionsImmutable();
/* 13291:      */       }
/* 13292:      */     }
/* 13293:      */     
/* 13294:      */     public static final Descriptors.Descriptor getDescriptor()
/* 13295:      */     {
/* 13296:14198 */       return MRProtos.internal_static_hadoop_mapreduce_StringCounterMapProto_descriptor;
/* 13297:      */     }
/* 13298:      */     
/* 13299:      */     protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable()
/* 13300:      */     {
/* 13301:14203 */       return MRProtos.internal_static_hadoop_mapreduce_StringCounterMapProto_fieldAccessorTable.ensureFieldAccessorsInitialized(StringCounterMapProto.class, Builder.class);
/* 13302:      */     }
/* 13303:      */     
/* 13304:14208 */     public static Parser<StringCounterMapProto> PARSER = new AbstractParser()
/* 13305:      */     {
/* 13306:      */       public MRProtos.StringCounterMapProto parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry)
/* 13307:      */         throws InvalidProtocolBufferException
/* 13308:      */       {
/* 13309:14214 */         return new MRProtos.StringCounterMapProto(input, extensionRegistry, null);
/* 13310:      */       }
/* 13311:      */     };
/* 13312:      */     private int bitField0_;
/* 13313:      */     public static final int KEY_FIELD_NUMBER = 1;
/* 13314:      */     private Object key_;
/* 13315:      */     public static final int VALUE_FIELD_NUMBER = 2;
/* 13316:      */     private MRProtos.CounterProto value_;
/* 13317:      */     
/* 13318:      */     public Parser<StringCounterMapProto> getParserForType()
/* 13319:      */     {
/* 13320:14220 */       return PARSER;
/* 13321:      */     }
/* 13322:      */     
/* 13323:      */     public boolean hasKey()
/* 13324:      */     {
/* 13325:14231 */       return (this.bitField0_ & 0x1) == 1;
/* 13326:      */     }
/* 13327:      */     
/* 13328:      */     public String getKey()
/* 13329:      */     {
/* 13330:14237 */       Object ref = this.key_;
/* 13331:14238 */       if ((ref instanceof String)) {
/* 13332:14239 */         return (String)ref;
/* 13333:      */       }
/* 13334:14241 */       ByteString bs = (ByteString)ref;
/* 13335:      */       
/* 13336:14243 */       String s = bs.toStringUtf8();
/* 13337:14244 */       if (bs.isValidUtf8()) {
/* 13338:14245 */         this.key_ = s;
/* 13339:      */       }
/* 13340:14247 */       return s;
/* 13341:      */     }
/* 13342:      */     
/* 13343:      */     public ByteString getKeyBytes()
/* 13344:      */     {
/* 13345:14255 */       Object ref = this.key_;
/* 13346:14256 */       if ((ref instanceof String))
/* 13347:      */       {
/* 13348:14257 */         ByteString b = ByteString.copyFromUtf8((String)ref);
/* 13349:      */         
/* 13350:      */ 
/* 13351:14260 */         this.key_ = b;
/* 13352:14261 */         return b;
/* 13353:      */       }
/* 13354:14263 */       return (ByteString)ref;
/* 13355:      */     }
/* 13356:      */     
/* 13357:      */     public boolean hasValue()
/* 13358:      */     {
/* 13359:14274 */       return (this.bitField0_ & 0x2) == 2;
/* 13360:      */     }
/* 13361:      */     
/* 13362:      */     public MRProtos.CounterProto getValue()
/* 13363:      */     {
/* 13364:14280 */       return this.value_;
/* 13365:      */     }
/* 13366:      */     
/* 13367:      */     public MRProtos.CounterProtoOrBuilder getValueOrBuilder()
/* 13368:      */     {
/* 13369:14286 */       return this.value_;
/* 13370:      */     }
/* 13371:      */     
/* 13372:      */     private void initFields()
/* 13373:      */     {
/* 13374:14290 */       this.key_ = "";
/* 13375:14291 */       this.value_ = MRProtos.CounterProto.getDefaultInstance();
/* 13376:      */     }
/* 13377:      */     
/* 13378:14293 */     private byte memoizedIsInitialized = -1;
/* 13379:      */     
/* 13380:      */     public final boolean isInitialized()
/* 13381:      */     {
/* 13382:14295 */       byte isInitialized = this.memoizedIsInitialized;
/* 13383:14296 */       if (isInitialized != -1) {
/* 13384:14296 */         return isInitialized == 1;
/* 13385:      */       }
/* 13386:14298 */       this.memoizedIsInitialized = 1;
/* 13387:14299 */       return true;
/* 13388:      */     }
/* 13389:      */     
/* 13390:      */     public void writeTo(CodedOutputStream output)
/* 13391:      */       throws IOException
/* 13392:      */     {
/* 13393:14304 */       getSerializedSize();
/* 13394:14305 */       if ((this.bitField0_ & 0x1) == 1) {
/* 13395:14306 */         output.writeBytes(1, getKeyBytes());
/* 13396:      */       }
/* 13397:14308 */       if ((this.bitField0_ & 0x2) == 2) {
/* 13398:14309 */         output.writeMessage(2, this.value_);
/* 13399:      */       }
/* 13400:14311 */       getUnknownFields().writeTo(output);
/* 13401:      */     }
/* 13402:      */     
/* 13403:14314 */     private int memoizedSerializedSize = -1;
/* 13404:      */     private static final long serialVersionUID = 0L;
/* 13405:      */     
/* 13406:      */     public int getSerializedSize()
/* 13407:      */     {
/* 13408:14316 */       int size = this.memoizedSerializedSize;
/* 13409:14317 */       if (size != -1) {
/* 13410:14317 */         return size;
/* 13411:      */       }
/* 13412:14319 */       size = 0;
/* 13413:14320 */       if ((this.bitField0_ & 0x1) == 1) {
/* 13414:14321 */         size += CodedOutputStream.computeBytesSize(1, getKeyBytes());
/* 13415:      */       }
/* 13416:14324 */       if ((this.bitField0_ & 0x2) == 2) {
/* 13417:14325 */         size += CodedOutputStream.computeMessageSize(2, this.value_);
/* 13418:      */       }
/* 13419:14328 */       size += getUnknownFields().getSerializedSize();
/* 13420:14329 */       this.memoizedSerializedSize = size;
/* 13421:14330 */       return size;
/* 13422:      */     }
/* 13423:      */     
/* 13424:      */     protected Object writeReplace()
/* 13425:      */       throws ObjectStreamException
/* 13426:      */     {
/* 13427:14337 */       return super.writeReplace();
/* 13428:      */     }
/* 13429:      */     
/* 13430:      */     public boolean equals(Object obj)
/* 13431:      */     {
/* 13432:14342 */       if (obj == this) {
/* 13433:14343 */         return true;
/* 13434:      */       }
/* 13435:14345 */       if (!(obj instanceof StringCounterMapProto)) {
/* 13436:14346 */         return super.equals(obj);
/* 13437:      */       }
/* 13438:14348 */       StringCounterMapProto other = (StringCounterMapProto)obj;
/* 13439:      */       
/* 13440:14350 */       boolean result = true;
/* 13441:14351 */       result = (result) && (hasKey() == other.hasKey());
/* 13442:14352 */       if (hasKey()) {
/* 13443:14353 */         result = (result) && (getKey().equals(other.getKey()));
/* 13444:      */       }
/* 13445:14356 */       result = (result) && (hasValue() == other.hasValue());
/* 13446:14357 */       if (hasValue()) {
/* 13447:14358 */         result = (result) && (getValue().equals(other.getValue()));
/* 13448:      */       }
/* 13449:14361 */       result = (result) && (getUnknownFields().equals(other.getUnknownFields()));
/* 13450:      */       
/* 13451:14363 */       return result;
/* 13452:      */     }
/* 13453:      */     
/* 13454:14366 */     private int memoizedHashCode = 0;
/* 13455:      */     
/* 13456:      */     public int hashCode()
/* 13457:      */     {
/* 13458:14369 */       if (this.memoizedHashCode != 0) {
/* 13459:14370 */         return this.memoizedHashCode;
/* 13460:      */       }
/* 13461:14372 */       int hash = 41;
/* 13462:14373 */       hash = 19 * hash + getDescriptorForType().hashCode();
/* 13463:14374 */       if (hasKey())
/* 13464:      */       {
/* 13465:14375 */         hash = 37 * hash + 1;
/* 13466:14376 */         hash = 53 * hash + getKey().hashCode();
/* 13467:      */       }
/* 13468:14378 */       if (hasValue())
/* 13469:      */       {
/* 13470:14379 */         hash = 37 * hash + 2;
/* 13471:14380 */         hash = 53 * hash + getValue().hashCode();
/* 13472:      */       }
/* 13473:14382 */       hash = 29 * hash + getUnknownFields().hashCode();
/* 13474:14383 */       this.memoizedHashCode = hash;
/* 13475:14384 */       return hash;
/* 13476:      */     }
/* 13477:      */     
/* 13478:      */     public static StringCounterMapProto parseFrom(ByteString data)
/* 13479:      */       throws InvalidProtocolBufferException
/* 13480:      */     {
/* 13481:14390 */       return (StringCounterMapProto)PARSER.parseFrom(data);
/* 13482:      */     }
/* 13483:      */     
/* 13484:      */     public static StringCounterMapProto parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry)
/* 13485:      */       throws InvalidProtocolBufferException
/* 13486:      */     {
/* 13487:14396 */       return (StringCounterMapProto)PARSER.parseFrom(data, extensionRegistry);
/* 13488:      */     }
/* 13489:      */     
/* 13490:      */     public static StringCounterMapProto parseFrom(byte[] data)
/* 13491:      */       throws InvalidProtocolBufferException
/* 13492:      */     {
/* 13493:14400 */       return (StringCounterMapProto)PARSER.parseFrom(data);
/* 13494:      */     }
/* 13495:      */     
/* 13496:      */     public static StringCounterMapProto parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry)
/* 13497:      */       throws InvalidProtocolBufferException
/* 13498:      */     {
/* 13499:14406 */       return (StringCounterMapProto)PARSER.parseFrom(data, extensionRegistry);
/* 13500:      */     }
/* 13501:      */     
/* 13502:      */     public static StringCounterMapProto parseFrom(InputStream input)
/* 13503:      */       throws IOException
/* 13504:      */     {
/* 13505:14410 */       return (StringCounterMapProto)PARSER.parseFrom(input);
/* 13506:      */     }
/* 13507:      */     
/* 13508:      */     public static StringCounterMapProto parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry)
/* 13509:      */       throws IOException
/* 13510:      */     {
/* 13511:14416 */       return (StringCounterMapProto)PARSER.parseFrom(input, extensionRegistry);
/* 13512:      */     }
/* 13513:      */     
/* 13514:      */     public static StringCounterMapProto parseDelimitedFrom(InputStream input)
/* 13515:      */       throws IOException
/* 13516:      */     {
/* 13517:14420 */       return (StringCounterMapProto)PARSER.parseDelimitedFrom(input);
/* 13518:      */     }
/* 13519:      */     
/* 13520:      */     public static StringCounterMapProto parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry)
/* 13521:      */       throws IOException
/* 13522:      */     {
/* 13523:14426 */       return (StringCounterMapProto)PARSER.parseDelimitedFrom(input, extensionRegistry);
/* 13524:      */     }
/* 13525:      */     
/* 13526:      */     public static StringCounterMapProto parseFrom(CodedInputStream input)
/* 13527:      */       throws IOException
/* 13528:      */     {
/* 13529:14431 */       return (StringCounterMapProto)PARSER.parseFrom(input);
/* 13530:      */     }
/* 13531:      */     
/* 13532:      */     public static StringCounterMapProto parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry)
/* 13533:      */       throws IOException
/* 13534:      */     {
/* 13535:14437 */       return (StringCounterMapProto)PARSER.parseFrom(input, extensionRegistry);
/* 13536:      */     }
/* 13537:      */     
/* 13538:      */     public static Builder newBuilder()
/* 13539:      */     {
/* 13540:14440 */       return Builder.access$15900();
/* 13541:      */     }
/* 13542:      */     
/* 13543:      */     public Builder newBuilderForType()
/* 13544:      */     {
/* 13545:14441 */       return newBuilder();
/* 13546:      */     }
/* 13547:      */     
/* 13548:      */     public static Builder newBuilder(StringCounterMapProto prototype)
/* 13549:      */     {
/* 13550:14443 */       return newBuilder().mergeFrom(prototype);
/* 13551:      */     }
/* 13552:      */     
/* 13553:      */     public Builder toBuilder()
/* 13554:      */     {
/* 13555:14445 */       return newBuilder(this);
/* 13556:      */     }
/* 13557:      */     
/* 13558:      */     protected Builder newBuilderForType(GeneratedMessage.BuilderParent parent)
/* 13559:      */     {
/* 13560:14450 */       Builder builder = new Builder(parent, null);
/* 13561:14451 */       return builder;
/* 13562:      */     }
/* 13563:      */     
/* 13564:      */     public static final class Builder
/* 13565:      */       extends GeneratedMessage.Builder<Builder>
/* 13566:      */       implements MRProtos.StringCounterMapProtoOrBuilder
/* 13567:      */     {
/* 13568:      */       private int bitField0_;
/* 13569:      */       
/* 13570:      */       public static final Descriptors.Descriptor getDescriptor()
/* 13571:      */       {
/* 13572:14461 */         return MRProtos.internal_static_hadoop_mapreduce_StringCounterMapProto_descriptor;
/* 13573:      */       }
/* 13574:      */       
/* 13575:      */       protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable()
/* 13576:      */       {
/* 13577:14466 */         return MRProtos.internal_static_hadoop_mapreduce_StringCounterMapProto_fieldAccessorTable.ensureFieldAccessorsInitialized(MRProtos.StringCounterMapProto.class, Builder.class);
/* 13578:      */       }
/* 13579:      */       
/* 13580:      */       private Builder()
/* 13581:      */       {
/* 13582:14473 */         maybeForceBuilderInitialization();
/* 13583:      */       }
/* 13584:      */       
/* 13585:      */       private Builder(GeneratedMessage.BuilderParent parent)
/* 13586:      */       {
/* 13587:14478 */         super();
/* 13588:14479 */         maybeForceBuilderInitialization();
/* 13589:      */       }
/* 13590:      */       
/* 13591:      */       private void maybeForceBuilderInitialization()
/* 13592:      */       {
/* 13593:14482 */         if (MRProtos.StringCounterMapProto.alwaysUseFieldBuilders) {
/* 13594:14483 */           getValueFieldBuilder();
/* 13595:      */         }
/* 13596:      */       }
/* 13597:      */       
/* 13598:      */       private static Builder create()
/* 13599:      */       {
/* 13600:14487 */         return new Builder();
/* 13601:      */       }
/* 13602:      */       
/* 13603:      */       public Builder clear()
/* 13604:      */       {
/* 13605:14491 */         super.clear();
/* 13606:14492 */         this.key_ = "";
/* 13607:14493 */         this.bitField0_ &= 0xFFFFFFFE;
/* 13608:14494 */         if (this.valueBuilder_ == null) {
/* 13609:14495 */           this.value_ = MRProtos.CounterProto.getDefaultInstance();
/* 13610:      */         } else {
/* 13611:14497 */           this.valueBuilder_.clear();
/* 13612:      */         }
/* 13613:14499 */         this.bitField0_ &= 0xFFFFFFFD;
/* 13614:14500 */         return this;
/* 13615:      */       }
/* 13616:      */       
/* 13617:      */       public Builder clone()
/* 13618:      */       {
/* 13619:14504 */         return create().mergeFrom(buildPartial());
/* 13620:      */       }
/* 13621:      */       
/* 13622:      */       public Descriptors.Descriptor getDescriptorForType()
/* 13623:      */       {
/* 13624:14509 */         return MRProtos.internal_static_hadoop_mapreduce_StringCounterMapProto_descriptor;
/* 13625:      */       }
/* 13626:      */       
/* 13627:      */       public MRProtos.StringCounterMapProto getDefaultInstanceForType()
/* 13628:      */       {
/* 13629:14513 */         return MRProtos.StringCounterMapProto.getDefaultInstance();
/* 13630:      */       }
/* 13631:      */       
/* 13632:      */       public MRProtos.StringCounterMapProto build()
/* 13633:      */       {
/* 13634:14517 */         MRProtos.StringCounterMapProto result = buildPartial();
/* 13635:14518 */         if (!result.isInitialized()) {
/* 13636:14519 */           throw newUninitializedMessageException(result);
/* 13637:      */         }
/* 13638:14521 */         return result;
/* 13639:      */       }
/* 13640:      */       
/* 13641:      */       public MRProtos.StringCounterMapProto buildPartial()
/* 13642:      */       {
/* 13643:14525 */         MRProtos.StringCounterMapProto result = new MRProtos.StringCounterMapProto(this, null);
/* 13644:14526 */         int from_bitField0_ = this.bitField0_;
/* 13645:14527 */         int to_bitField0_ = 0;
/* 13646:14528 */         if ((from_bitField0_ & 0x1) == 1) {
/* 13647:14529 */           to_bitField0_ |= 0x1;
/* 13648:      */         }
/* 13649:14531 */         result.key_ = this.key_;
/* 13650:14532 */         if ((from_bitField0_ & 0x2) == 2) {
/* 13651:14533 */           to_bitField0_ |= 0x2;
/* 13652:      */         }
/* 13653:14535 */         if (this.valueBuilder_ == null) {
/* 13654:14536 */           result.value_ = this.value_;
/* 13655:      */         } else {
/* 13656:14538 */           result.value_ = ((MRProtos.CounterProto)this.valueBuilder_.build());
/* 13657:      */         }
/* 13658:14540 */         result.bitField0_ = to_bitField0_;
/* 13659:14541 */         onBuilt();
/* 13660:14542 */         return result;
/* 13661:      */       }
/* 13662:      */       
/* 13663:      */       public Builder mergeFrom(Message other)
/* 13664:      */       {
/* 13665:14546 */         if ((other instanceof MRProtos.StringCounterMapProto)) {
/* 13666:14547 */           return mergeFrom((MRProtos.StringCounterMapProto)other);
/* 13667:      */         }
/* 13668:14549 */         super.mergeFrom(other);
/* 13669:14550 */         return this;
/* 13670:      */       }
/* 13671:      */       
/* 13672:      */       public Builder mergeFrom(MRProtos.StringCounterMapProto other)
/* 13673:      */       {
/* 13674:14555 */         if (other == MRProtos.StringCounterMapProto.getDefaultInstance()) {
/* 13675:14555 */           return this;
/* 13676:      */         }
/* 13677:14556 */         if (other.hasKey())
/* 13678:      */         {
/* 13679:14557 */           this.bitField0_ |= 0x1;
/* 13680:14558 */           this.key_ = other.key_;
/* 13681:14559 */           onChanged();
/* 13682:      */         }
/* 13683:14561 */         if (other.hasValue()) {
/* 13684:14562 */           mergeValue(other.getValue());
/* 13685:      */         }
/* 13686:14564 */         mergeUnknownFields(other.getUnknownFields());
/* 13687:14565 */         return this;
/* 13688:      */       }
/* 13689:      */       
/* 13690:      */       public final boolean isInitialized()
/* 13691:      */       {
/* 13692:14569 */         return true;
/* 13693:      */       }
/* 13694:      */       
/* 13695:      */       public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry)
/* 13696:      */         throws IOException
/* 13697:      */       {
/* 13698:14576 */         MRProtos.StringCounterMapProto parsedMessage = null;
/* 13699:      */         try
/* 13700:      */         {
/* 13701:14578 */           parsedMessage = (MRProtos.StringCounterMapProto)MRProtos.StringCounterMapProto.PARSER.parsePartialFrom(input, extensionRegistry);
/* 13702:      */         }
/* 13703:      */         catch (InvalidProtocolBufferException e)
/* 13704:      */         {
/* 13705:14580 */           parsedMessage = (MRProtos.StringCounterMapProto)e.getUnfinishedMessage();
/* 13706:14581 */           throw e;
/* 13707:      */         }
/* 13708:      */         finally
/* 13709:      */         {
/* 13710:14583 */           if (parsedMessage != null) {
/* 13711:14584 */             mergeFrom(parsedMessage);
/* 13712:      */           }
/* 13713:      */         }
/* 13714:14587 */         return this;
/* 13715:      */       }
/* 13716:      */       
/* 13717:14592 */       private Object key_ = "";
/* 13718:      */       
/* 13719:      */       public boolean hasKey()
/* 13720:      */       {
/* 13721:14597 */         return (this.bitField0_ & 0x1) == 1;
/* 13722:      */       }
/* 13723:      */       
/* 13724:      */       public String getKey()
/* 13725:      */       {
/* 13726:14603 */         Object ref = this.key_;
/* 13727:14604 */         if (!(ref instanceof String))
/* 13728:      */         {
/* 13729:14605 */           String s = ((ByteString)ref).toStringUtf8();
/* 13730:      */           
/* 13731:14607 */           this.key_ = s;
/* 13732:14608 */           return s;
/* 13733:      */         }
/* 13734:14610 */         return (String)ref;
/* 13735:      */       }
/* 13736:      */       
/* 13737:      */       public ByteString getKeyBytes()
/* 13738:      */       {
/* 13739:14618 */         Object ref = this.key_;
/* 13740:14619 */         if ((ref instanceof String))
/* 13741:      */         {
/* 13742:14620 */           ByteString b = ByteString.copyFromUtf8((String)ref);
/* 13743:      */           
/* 13744:      */ 
/* 13745:14623 */           this.key_ = b;
/* 13746:14624 */           return b;
/* 13747:      */         }
/* 13748:14626 */         return (ByteString)ref;
/* 13749:      */       }
/* 13750:      */       
/* 13751:      */       public Builder setKey(String value)
/* 13752:      */       {
/* 13753:14634 */         if (value == null) {
/* 13754:14635 */           throw new NullPointerException();
/* 13755:      */         }
/* 13756:14637 */         this.bitField0_ |= 0x1;
/* 13757:14638 */         this.key_ = value;
/* 13758:14639 */         onChanged();
/* 13759:14640 */         return this;
/* 13760:      */       }
/* 13761:      */       
/* 13762:      */       public Builder clearKey()
/* 13763:      */       {
/* 13764:14646 */         this.bitField0_ &= 0xFFFFFFFE;
/* 13765:14647 */         this.key_ = MRProtos.StringCounterMapProto.getDefaultInstance().getKey();
/* 13766:14648 */         onChanged();
/* 13767:14649 */         return this;
/* 13768:      */       }
/* 13769:      */       
/* 13770:      */       public Builder setKeyBytes(ByteString value)
/* 13771:      */       {
/* 13772:14656 */         if (value == null) {
/* 13773:14657 */           throw new NullPointerException();
/* 13774:      */         }
/* 13775:14659 */         this.bitField0_ |= 0x1;
/* 13776:14660 */         this.key_ = value;
/* 13777:14661 */         onChanged();
/* 13778:14662 */         return this;
/* 13779:      */       }
/* 13780:      */       
/* 13781:14666 */       private MRProtos.CounterProto value_ = MRProtos.CounterProto.getDefaultInstance();
/* 13782:      */       private SingleFieldBuilder<MRProtos.CounterProto, MRProtos.CounterProto.Builder, MRProtos.CounterProtoOrBuilder> valueBuilder_;
/* 13783:      */       
/* 13784:      */       public boolean hasValue()
/* 13785:      */       {
/* 13786:14673 */         return (this.bitField0_ & 0x2) == 2;
/* 13787:      */       }
/* 13788:      */       
/* 13789:      */       public MRProtos.CounterProto getValue()
/* 13790:      */       {
/* 13791:14679 */         if (this.valueBuilder_ == null) {
/* 13792:14680 */           return this.value_;
/* 13793:      */         }
/* 13794:14682 */         return (MRProtos.CounterProto)this.valueBuilder_.getMessage();
/* 13795:      */       }
/* 13796:      */       
/* 13797:      */       public Builder setValue(MRProtos.CounterProto value)
/* 13798:      */       {
/* 13799:14689 */         if (this.valueBuilder_ == null)
/* 13800:      */         {
/* 13801:14690 */           if (value == null) {
/* 13802:14691 */             throw new NullPointerException();
/* 13803:      */           }
/* 13804:14693 */           this.value_ = value;
/* 13805:14694 */           onChanged();
/* 13806:      */         }
/* 13807:      */         else
/* 13808:      */         {
/* 13809:14696 */           this.valueBuilder_.setMessage(value);
/* 13810:      */         }
/* 13811:14698 */         this.bitField0_ |= 0x2;
/* 13812:14699 */         return this;
/* 13813:      */       }
/* 13814:      */       
/* 13815:      */       public Builder setValue(MRProtos.CounterProto.Builder builderForValue)
/* 13816:      */       {
/* 13817:14706 */         if (this.valueBuilder_ == null)
/* 13818:      */         {
/* 13819:14707 */           this.value_ = builderForValue.build();
/* 13820:14708 */           onChanged();
/* 13821:      */         }
/* 13822:      */         else
/* 13823:      */         {
/* 13824:14710 */           this.valueBuilder_.setMessage(builderForValue.build());
/* 13825:      */         }
/* 13826:14712 */         this.bitField0_ |= 0x2;
/* 13827:14713 */         return this;
/* 13828:      */       }
/* 13829:      */       
/* 13830:      */       public Builder mergeValue(MRProtos.CounterProto value)
/* 13831:      */       {
/* 13832:14719 */         if (this.valueBuilder_ == null)
/* 13833:      */         {
/* 13834:14720 */           if (((this.bitField0_ & 0x2) == 2) && (this.value_ != MRProtos.CounterProto.getDefaultInstance())) {
/* 13835:14722 */             this.value_ = MRProtos.CounterProto.newBuilder(this.value_).mergeFrom(value).buildPartial();
/* 13836:      */           } else {
/* 13837:14725 */             this.value_ = value;
/* 13838:      */           }
/* 13839:14727 */           onChanged();
/* 13840:      */         }
/* 13841:      */         else
/* 13842:      */         {
/* 13843:14729 */           this.valueBuilder_.mergeFrom(value);
/* 13844:      */         }
/* 13845:14731 */         this.bitField0_ |= 0x2;
/* 13846:14732 */         return this;
/* 13847:      */       }
/* 13848:      */       
/* 13849:      */       public Builder clearValue()
/* 13850:      */       {
/* 13851:14738 */         if (this.valueBuilder_ == null)
/* 13852:      */         {
/* 13853:14739 */           this.value_ = MRProtos.CounterProto.getDefaultInstance();
/* 13854:14740 */           onChanged();
/* 13855:      */         }
/* 13856:      */         else
/* 13857:      */         {
/* 13858:14742 */           this.valueBuilder_.clear();
/* 13859:      */         }
/* 13860:14744 */         this.bitField0_ &= 0xFFFFFFFD;
/* 13861:14745 */         return this;
/* 13862:      */       }
/* 13863:      */       
/* 13864:      */       public MRProtos.CounterProto.Builder getValueBuilder()
/* 13865:      */       {
/* 13866:14751 */         this.bitField0_ |= 0x2;
/* 13867:14752 */         onChanged();
/* 13868:14753 */         return (MRProtos.CounterProto.Builder)getValueFieldBuilder().getBuilder();
/* 13869:      */       }
/* 13870:      */       
/* 13871:      */       public MRProtos.CounterProtoOrBuilder getValueOrBuilder()
/* 13872:      */       {
/* 13873:14759 */         if (this.valueBuilder_ != null) {
/* 13874:14760 */           return (MRProtos.CounterProtoOrBuilder)this.valueBuilder_.getMessageOrBuilder();
/* 13875:      */         }
/* 13876:14762 */         return this.value_;
/* 13877:      */       }
/* 13878:      */       
/* 13879:      */       private SingleFieldBuilder<MRProtos.CounterProto, MRProtos.CounterProto.Builder, MRProtos.CounterProtoOrBuilder> getValueFieldBuilder()
/* 13880:      */       {
/* 13881:14771 */         if (this.valueBuilder_ == null)
/* 13882:      */         {
/* 13883:14772 */           this.valueBuilder_ = new SingleFieldBuilder(this.value_, getParentForChildren(), isClean());
/* 13884:      */           
/* 13885:      */ 
/* 13886:      */ 
/* 13887:      */ 
/* 13888:14777 */           this.value_ = null;
/* 13889:      */         }
/* 13890:14779 */         return this.valueBuilder_;
/* 13891:      */       }
/* 13892:      */     }
/* 13893:      */     
/* 13894:      */     static
/* 13895:      */     {
/* 13896:14786 */       defaultInstance = new StringCounterMapProto(true);
/* 13897:14787 */       defaultInstance.initFields();
/* 13898:      */     }
/* 13899:      */   }
/* 13900:      */   
/* 13901:      */   public static abstract interface StringCounterGroupMapProtoOrBuilder
/* 13902:      */     extends MessageOrBuilder
/* 13903:      */   {
/* 13904:      */     public abstract boolean hasKey();
/* 13905:      */     
/* 13906:      */     public abstract String getKey();
/* 13907:      */     
/* 13908:      */     public abstract ByteString getKeyBytes();
/* 13909:      */     
/* 13910:      */     public abstract boolean hasValue();
/* 13911:      */     
/* 13912:      */     public abstract MRProtos.CounterGroupProto getValue();
/* 13913:      */     
/* 13914:      */     public abstract MRProtos.CounterGroupProtoOrBuilder getValueOrBuilder();
/* 13915:      */   }
/* 13916:      */   
/* 13917:      */   public static final class StringCounterGroupMapProto
/* 13918:      */     extends GeneratedMessage
/* 13919:      */     implements MRProtos.StringCounterGroupMapProtoOrBuilder
/* 13920:      */   {
/* 13921:      */     private static final StringCounterGroupMapProto defaultInstance;
/* 13922:      */     private final UnknownFieldSet unknownFields;
/* 13923:      */     
/* 13924:      */     private StringCounterGroupMapProto(GeneratedMessage.Builder<?> builder)
/* 13925:      */     {
/* 13926:14833 */       super();
/* 13927:14834 */       this.unknownFields = builder.getUnknownFields();
/* 13928:      */     }
/* 13929:      */     
/* 13930:      */     private StringCounterGroupMapProto(boolean noInit)
/* 13931:      */     {
/* 13932:14836 */       this.unknownFields = UnknownFieldSet.getDefaultInstance();
/* 13933:      */     }
/* 13934:      */     
/* 13935:      */     public static StringCounterGroupMapProto getDefaultInstance()
/* 13936:      */     {
/* 13937:14840 */       return defaultInstance;
/* 13938:      */     }
/* 13939:      */     
/* 13940:      */     public StringCounterGroupMapProto getDefaultInstanceForType()
/* 13941:      */     {
/* 13942:14844 */       return defaultInstance;
/* 13943:      */     }
/* 13944:      */     
/* 13945:      */     public final UnknownFieldSet getUnknownFields()
/* 13946:      */     {
/* 13947:14851 */       return this.unknownFields;
/* 13948:      */     }
/* 13949:      */     
/* 13950:      */     private StringCounterGroupMapProto(CodedInputStream input, ExtensionRegistryLite extensionRegistry)
/* 13951:      */       throws InvalidProtocolBufferException
/* 13952:      */     {
/* 13953:14857 */       initFields();
/* 13954:14858 */       int mutable_bitField0_ = 0;
/* 13955:14859 */       UnknownFieldSet.Builder unknownFields = UnknownFieldSet.newBuilder();
/* 13956:      */       try
/* 13957:      */       {
/* 13958:14862 */         boolean done = false;
/* 13959:14863 */         while (!done)
/* 13960:      */         {
/* 13961:14864 */           int tag = input.readTag();
/* 13962:14865 */           switch (tag)
/* 13963:      */           {
/* 13964:      */           case 0: 
/* 13965:14867 */             done = true;
/* 13966:14868 */             break;
/* 13967:      */           default: 
/* 13968:14870 */             if (!parseUnknownField(input, unknownFields, extensionRegistry, tag)) {
/* 13969:14872 */               done = true;
/* 13970:      */             }
/* 13971:      */             break;
/* 13972:      */           case 10: 
/* 13973:14877 */             this.bitField0_ |= 0x1;
/* 13974:14878 */             this.key_ = input.readBytes();
/* 13975:14879 */             break;
/* 13976:      */           case 18: 
/* 13977:14882 */             MRProtos.CounterGroupProto.Builder subBuilder = null;
/* 13978:14883 */             if ((this.bitField0_ & 0x2) == 2) {
/* 13979:14884 */               subBuilder = this.value_.toBuilder();
/* 13980:      */             }
/* 13981:14886 */             this.value_ = ((MRProtos.CounterGroupProto)input.readMessage(MRProtos.CounterGroupProto.PARSER, extensionRegistry));
/* 13982:14887 */             if (subBuilder != null)
/* 13983:      */             {
/* 13984:14888 */               subBuilder.mergeFrom(this.value_);
/* 13985:14889 */               this.value_ = subBuilder.buildPartial();
/* 13986:      */             }
/* 13987:14891 */             this.bitField0_ |= 0x2;
/* 13988:      */           }
/* 13989:      */         }
/* 13990:      */       }
/* 13991:      */       catch (InvalidProtocolBufferException e)
/* 13992:      */       {
/* 13993:14897 */         throw e.setUnfinishedMessage(this);
/* 13994:      */       }
/* 13995:      */       catch (IOException e)
/* 13996:      */       {
/* 13997:14899 */         throw new InvalidProtocolBufferException(e.getMessage()).setUnfinishedMessage(this);
/* 13998:      */       }
/* 13999:      */       finally
/* 14000:      */       {
/* 14001:14902 */         this.unknownFields = unknownFields.build();
/* 14002:14903 */         makeExtensionsImmutable();
/* 14003:      */       }
/* 14004:      */     }
/* 14005:      */     
/* 14006:      */     public static final Descriptors.Descriptor getDescriptor()
/* 14007:      */     {
/* 14008:14908 */       return MRProtos.internal_static_hadoop_mapreduce_StringCounterGroupMapProto_descriptor;
/* 14009:      */     }
/* 14010:      */     
/* 14011:      */     protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable()
/* 14012:      */     {
/* 14013:14913 */       return MRProtos.internal_static_hadoop_mapreduce_StringCounterGroupMapProto_fieldAccessorTable.ensureFieldAccessorsInitialized(StringCounterGroupMapProto.class, Builder.class);
/* 14014:      */     }
/* 14015:      */     
/* 14016:14918 */     public static Parser<StringCounterGroupMapProto> PARSER = new AbstractParser()
/* 14017:      */     {
/* 14018:      */       public MRProtos.StringCounterGroupMapProto parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry)
/* 14019:      */         throws InvalidProtocolBufferException
/* 14020:      */       {
/* 14021:14924 */         return new MRProtos.StringCounterGroupMapProto(input, extensionRegistry, null);
/* 14022:      */       }
/* 14023:      */     };
/* 14024:      */     private int bitField0_;
/* 14025:      */     public static final int KEY_FIELD_NUMBER = 1;
/* 14026:      */     private Object key_;
/* 14027:      */     public static final int VALUE_FIELD_NUMBER = 2;
/* 14028:      */     private MRProtos.CounterGroupProto value_;
/* 14029:      */     
/* 14030:      */     public Parser<StringCounterGroupMapProto> getParserForType()
/* 14031:      */     {
/* 14032:14930 */       return PARSER;
/* 14033:      */     }
/* 14034:      */     
/* 14035:      */     public boolean hasKey()
/* 14036:      */     {
/* 14037:14941 */       return (this.bitField0_ & 0x1) == 1;
/* 14038:      */     }
/* 14039:      */     
/* 14040:      */     public String getKey()
/* 14041:      */     {
/* 14042:14947 */       Object ref = this.key_;
/* 14043:14948 */       if ((ref instanceof String)) {
/* 14044:14949 */         return (String)ref;
/* 14045:      */       }
/* 14046:14951 */       ByteString bs = (ByteString)ref;
/* 14047:      */       
/* 14048:14953 */       String s = bs.toStringUtf8();
/* 14049:14954 */       if (bs.isValidUtf8()) {
/* 14050:14955 */         this.key_ = s;
/* 14051:      */       }
/* 14052:14957 */       return s;
/* 14053:      */     }
/* 14054:      */     
/* 14055:      */     public ByteString getKeyBytes()
/* 14056:      */     {
/* 14057:14965 */       Object ref = this.key_;
/* 14058:14966 */       if ((ref instanceof String))
/* 14059:      */       {
/* 14060:14967 */         ByteString b = ByteString.copyFromUtf8((String)ref);
/* 14061:      */         
/* 14062:      */ 
/* 14063:14970 */         this.key_ = b;
/* 14064:14971 */         return b;
/* 14065:      */       }
/* 14066:14973 */       return (ByteString)ref;
/* 14067:      */     }
/* 14068:      */     
/* 14069:      */     public boolean hasValue()
/* 14070:      */     {
/* 14071:14984 */       return (this.bitField0_ & 0x2) == 2;
/* 14072:      */     }
/* 14073:      */     
/* 14074:      */     public MRProtos.CounterGroupProto getValue()
/* 14075:      */     {
/* 14076:14990 */       return this.value_;
/* 14077:      */     }
/* 14078:      */     
/* 14079:      */     public MRProtos.CounterGroupProtoOrBuilder getValueOrBuilder()
/* 14080:      */     {
/* 14081:14996 */       return this.value_;
/* 14082:      */     }
/* 14083:      */     
/* 14084:      */     private void initFields()
/* 14085:      */     {
/* 14086:15000 */       this.key_ = "";
/* 14087:15001 */       this.value_ = MRProtos.CounterGroupProto.getDefaultInstance();
/* 14088:      */     }
/* 14089:      */     
/* 14090:15003 */     private byte memoizedIsInitialized = -1;
/* 14091:      */     
/* 14092:      */     public final boolean isInitialized()
/* 14093:      */     {
/* 14094:15005 */       byte isInitialized = this.memoizedIsInitialized;
/* 14095:15006 */       if (isInitialized != -1) {
/* 14096:15006 */         return isInitialized == 1;
/* 14097:      */       }
/* 14098:15008 */       this.memoizedIsInitialized = 1;
/* 14099:15009 */       return true;
/* 14100:      */     }
/* 14101:      */     
/* 14102:      */     public void writeTo(CodedOutputStream output)
/* 14103:      */       throws IOException
/* 14104:      */     {
/* 14105:15014 */       getSerializedSize();
/* 14106:15015 */       if ((this.bitField0_ & 0x1) == 1) {
/* 14107:15016 */         output.writeBytes(1, getKeyBytes());
/* 14108:      */       }
/* 14109:15018 */       if ((this.bitField0_ & 0x2) == 2) {
/* 14110:15019 */         output.writeMessage(2, this.value_);
/* 14111:      */       }
/* 14112:15021 */       getUnknownFields().writeTo(output);
/* 14113:      */     }
/* 14114:      */     
/* 14115:15024 */     private int memoizedSerializedSize = -1;
/* 14116:      */     private static final long serialVersionUID = 0L;
/* 14117:      */     
/* 14118:      */     public int getSerializedSize()
/* 14119:      */     {
/* 14120:15026 */       int size = this.memoizedSerializedSize;
/* 14121:15027 */       if (size != -1) {
/* 14122:15027 */         return size;
/* 14123:      */       }
/* 14124:15029 */       size = 0;
/* 14125:15030 */       if ((this.bitField0_ & 0x1) == 1) {
/* 14126:15031 */         size += CodedOutputStream.computeBytesSize(1, getKeyBytes());
/* 14127:      */       }
/* 14128:15034 */       if ((this.bitField0_ & 0x2) == 2) {
/* 14129:15035 */         size += CodedOutputStream.computeMessageSize(2, this.value_);
/* 14130:      */       }
/* 14131:15038 */       size += getUnknownFields().getSerializedSize();
/* 14132:15039 */       this.memoizedSerializedSize = size;
/* 14133:15040 */       return size;
/* 14134:      */     }
/* 14135:      */     
/* 14136:      */     protected Object writeReplace()
/* 14137:      */       throws ObjectStreamException
/* 14138:      */     {
/* 14139:15047 */       return super.writeReplace();
/* 14140:      */     }
/* 14141:      */     
/* 14142:      */     public boolean equals(Object obj)
/* 14143:      */     {
/* 14144:15052 */       if (obj == this) {
/* 14145:15053 */         return true;
/* 14146:      */       }
/* 14147:15055 */       if (!(obj instanceof StringCounterGroupMapProto)) {
/* 14148:15056 */         return super.equals(obj);
/* 14149:      */       }
/* 14150:15058 */       StringCounterGroupMapProto other = (StringCounterGroupMapProto)obj;
/* 14151:      */       
/* 14152:15060 */       boolean result = true;
/* 14153:15061 */       result = (result) && (hasKey() == other.hasKey());
/* 14154:15062 */       if (hasKey()) {
/* 14155:15063 */         result = (result) && (getKey().equals(other.getKey()));
/* 14156:      */       }
/* 14157:15066 */       result = (result) && (hasValue() == other.hasValue());
/* 14158:15067 */       if (hasValue()) {
/* 14159:15068 */         result = (result) && (getValue().equals(other.getValue()));
/* 14160:      */       }
/* 14161:15071 */       result = (result) && (getUnknownFields().equals(other.getUnknownFields()));
/* 14162:      */       
/* 14163:15073 */       return result;
/* 14164:      */     }
/* 14165:      */     
/* 14166:15076 */     private int memoizedHashCode = 0;
/* 14167:      */     
/* 14168:      */     public int hashCode()
/* 14169:      */     {
/* 14170:15079 */       if (this.memoizedHashCode != 0) {
/* 14171:15080 */         return this.memoizedHashCode;
/* 14172:      */       }
/* 14173:15082 */       int hash = 41;
/* 14174:15083 */       hash = 19 * hash + getDescriptorForType().hashCode();
/* 14175:15084 */       if (hasKey())
/* 14176:      */       {
/* 14177:15085 */         hash = 37 * hash + 1;
/* 14178:15086 */         hash = 53 * hash + getKey().hashCode();
/* 14179:      */       }
/* 14180:15088 */       if (hasValue())
/* 14181:      */       {
/* 14182:15089 */         hash = 37 * hash + 2;
/* 14183:15090 */         hash = 53 * hash + getValue().hashCode();
/* 14184:      */       }
/* 14185:15092 */       hash = 29 * hash + getUnknownFields().hashCode();
/* 14186:15093 */       this.memoizedHashCode = hash;
/* 14187:15094 */       return hash;
/* 14188:      */     }
/* 14189:      */     
/* 14190:      */     public static StringCounterGroupMapProto parseFrom(ByteString data)
/* 14191:      */       throws InvalidProtocolBufferException
/* 14192:      */     {
/* 14193:15100 */       return (StringCounterGroupMapProto)PARSER.parseFrom(data);
/* 14194:      */     }
/* 14195:      */     
/* 14196:      */     public static StringCounterGroupMapProto parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry)
/* 14197:      */       throws InvalidProtocolBufferException
/* 14198:      */     {
/* 14199:15106 */       return (StringCounterGroupMapProto)PARSER.parseFrom(data, extensionRegistry);
/* 14200:      */     }
/* 14201:      */     
/* 14202:      */     public static StringCounterGroupMapProto parseFrom(byte[] data)
/* 14203:      */       throws InvalidProtocolBufferException
/* 14204:      */     {
/* 14205:15110 */       return (StringCounterGroupMapProto)PARSER.parseFrom(data);
/* 14206:      */     }
/* 14207:      */     
/* 14208:      */     public static StringCounterGroupMapProto parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry)
/* 14209:      */       throws InvalidProtocolBufferException
/* 14210:      */     {
/* 14211:15116 */       return (StringCounterGroupMapProto)PARSER.parseFrom(data, extensionRegistry);
/* 14212:      */     }
/* 14213:      */     
/* 14214:      */     public static StringCounterGroupMapProto parseFrom(InputStream input)
/* 14215:      */       throws IOException
/* 14216:      */     {
/* 14217:15120 */       return (StringCounterGroupMapProto)PARSER.parseFrom(input);
/* 14218:      */     }
/* 14219:      */     
/* 14220:      */     public static StringCounterGroupMapProto parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry)
/* 14221:      */       throws IOException
/* 14222:      */     {
/* 14223:15126 */       return (StringCounterGroupMapProto)PARSER.parseFrom(input, extensionRegistry);
/* 14224:      */     }
/* 14225:      */     
/* 14226:      */     public static StringCounterGroupMapProto parseDelimitedFrom(InputStream input)
/* 14227:      */       throws IOException
/* 14228:      */     {
/* 14229:15130 */       return (StringCounterGroupMapProto)PARSER.parseDelimitedFrom(input);
/* 14230:      */     }
/* 14231:      */     
/* 14232:      */     public static StringCounterGroupMapProto parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry)
/* 14233:      */       throws IOException
/* 14234:      */     {
/* 14235:15136 */       return (StringCounterGroupMapProto)PARSER.parseDelimitedFrom(input, extensionRegistry);
/* 14236:      */     }
/* 14237:      */     
/* 14238:      */     public static StringCounterGroupMapProto parseFrom(CodedInputStream input)
/* 14239:      */       throws IOException
/* 14240:      */     {
/* 14241:15141 */       return (StringCounterGroupMapProto)PARSER.parseFrom(input);
/* 14242:      */     }
/* 14243:      */     
/* 14244:      */     public static StringCounterGroupMapProto parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry)
/* 14245:      */       throws IOException
/* 14246:      */     {
/* 14247:15147 */       return (StringCounterGroupMapProto)PARSER.parseFrom(input, extensionRegistry);
/* 14248:      */     }
/* 14249:      */     
/* 14250:      */     public static Builder newBuilder()
/* 14251:      */     {
/* 14252:15150 */       return Builder.access$16900();
/* 14253:      */     }
/* 14254:      */     
/* 14255:      */     public Builder newBuilderForType()
/* 14256:      */     {
/* 14257:15151 */       return newBuilder();
/* 14258:      */     }
/* 14259:      */     
/* 14260:      */     public static Builder newBuilder(StringCounterGroupMapProto prototype)
/* 14261:      */     {
/* 14262:15153 */       return newBuilder().mergeFrom(prototype);
/* 14263:      */     }
/* 14264:      */     
/* 14265:      */     public Builder toBuilder()
/* 14266:      */     {
/* 14267:15155 */       return newBuilder(this);
/* 14268:      */     }
/* 14269:      */     
/* 14270:      */     protected Builder newBuilderForType(GeneratedMessage.BuilderParent parent)
/* 14271:      */     {
/* 14272:15160 */       Builder builder = new Builder(parent, null);
/* 14273:15161 */       return builder;
/* 14274:      */     }
/* 14275:      */     
/* 14276:      */     public static final class Builder
/* 14277:      */       extends GeneratedMessage.Builder<Builder>
/* 14278:      */       implements MRProtos.StringCounterGroupMapProtoOrBuilder
/* 14279:      */     {
/* 14280:      */       private int bitField0_;
/* 14281:      */       
/* 14282:      */       public static final Descriptors.Descriptor getDescriptor()
/* 14283:      */       {
/* 14284:15171 */         return MRProtos.internal_static_hadoop_mapreduce_StringCounterGroupMapProto_descriptor;
/* 14285:      */       }
/* 14286:      */       
/* 14287:      */       protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable()
/* 14288:      */       {
/* 14289:15176 */         return MRProtos.internal_static_hadoop_mapreduce_StringCounterGroupMapProto_fieldAccessorTable.ensureFieldAccessorsInitialized(MRProtos.StringCounterGroupMapProto.class, Builder.class);
/* 14290:      */       }
/* 14291:      */       
/* 14292:      */       private Builder()
/* 14293:      */       {
/* 14294:15183 */         maybeForceBuilderInitialization();
/* 14295:      */       }
/* 14296:      */       
/* 14297:      */       private Builder(GeneratedMessage.BuilderParent parent)
/* 14298:      */       {
/* 14299:15188 */         super();
/* 14300:15189 */         maybeForceBuilderInitialization();
/* 14301:      */       }
/* 14302:      */       
/* 14303:      */       private void maybeForceBuilderInitialization()
/* 14304:      */       {
/* 14305:15192 */         if (MRProtos.StringCounterGroupMapProto.alwaysUseFieldBuilders) {
/* 14306:15193 */           getValueFieldBuilder();
/* 14307:      */         }
/* 14308:      */       }
/* 14309:      */       
/* 14310:      */       private static Builder create()
/* 14311:      */       {
/* 14312:15197 */         return new Builder();
/* 14313:      */       }
/* 14314:      */       
/* 14315:      */       public Builder clear()
/* 14316:      */       {
/* 14317:15201 */         super.clear();
/* 14318:15202 */         this.key_ = "";
/* 14319:15203 */         this.bitField0_ &= 0xFFFFFFFE;
/* 14320:15204 */         if (this.valueBuilder_ == null) {
/* 14321:15205 */           this.value_ = MRProtos.CounterGroupProto.getDefaultInstance();
/* 14322:      */         } else {
/* 14323:15207 */           this.valueBuilder_.clear();
/* 14324:      */         }
/* 14325:15209 */         this.bitField0_ &= 0xFFFFFFFD;
/* 14326:15210 */         return this;
/* 14327:      */       }
/* 14328:      */       
/* 14329:      */       public Builder clone()
/* 14330:      */       {
/* 14331:15214 */         return create().mergeFrom(buildPartial());
/* 14332:      */       }
/* 14333:      */       
/* 14334:      */       public Descriptors.Descriptor getDescriptorForType()
/* 14335:      */       {
/* 14336:15219 */         return MRProtos.internal_static_hadoop_mapreduce_StringCounterGroupMapProto_descriptor;
/* 14337:      */       }
/* 14338:      */       
/* 14339:      */       public MRProtos.StringCounterGroupMapProto getDefaultInstanceForType()
/* 14340:      */       {
/* 14341:15223 */         return MRProtos.StringCounterGroupMapProto.getDefaultInstance();
/* 14342:      */       }
/* 14343:      */       
/* 14344:      */       public MRProtos.StringCounterGroupMapProto build()
/* 14345:      */       {
/* 14346:15227 */         MRProtos.StringCounterGroupMapProto result = buildPartial();
/* 14347:15228 */         if (!result.isInitialized()) {
/* 14348:15229 */           throw newUninitializedMessageException(result);
/* 14349:      */         }
/* 14350:15231 */         return result;
/* 14351:      */       }
/* 14352:      */       
/* 14353:      */       public MRProtos.StringCounterGroupMapProto buildPartial()
/* 14354:      */       {
/* 14355:15235 */         MRProtos.StringCounterGroupMapProto result = new MRProtos.StringCounterGroupMapProto(this, null);
/* 14356:15236 */         int from_bitField0_ = this.bitField0_;
/* 14357:15237 */         int to_bitField0_ = 0;
/* 14358:15238 */         if ((from_bitField0_ & 0x1) == 1) {
/* 14359:15239 */           to_bitField0_ |= 0x1;
/* 14360:      */         }
/* 14361:15241 */         result.key_ = this.key_;
/* 14362:15242 */         if ((from_bitField0_ & 0x2) == 2) {
/* 14363:15243 */           to_bitField0_ |= 0x2;
/* 14364:      */         }
/* 14365:15245 */         if (this.valueBuilder_ == null) {
/* 14366:15246 */           result.value_ = this.value_;
/* 14367:      */         } else {
/* 14368:15248 */           result.value_ = ((MRProtos.CounterGroupProto)this.valueBuilder_.build());
/* 14369:      */         }
/* 14370:15250 */         result.bitField0_ = to_bitField0_;
/* 14371:15251 */         onBuilt();
/* 14372:15252 */         return result;
/* 14373:      */       }
/* 14374:      */       
/* 14375:      */       public Builder mergeFrom(Message other)
/* 14376:      */       {
/* 14377:15256 */         if ((other instanceof MRProtos.StringCounterGroupMapProto)) {
/* 14378:15257 */           return mergeFrom((MRProtos.StringCounterGroupMapProto)other);
/* 14379:      */         }
/* 14380:15259 */         super.mergeFrom(other);
/* 14381:15260 */         return this;
/* 14382:      */       }
/* 14383:      */       
/* 14384:      */       public Builder mergeFrom(MRProtos.StringCounterGroupMapProto other)
/* 14385:      */       {
/* 14386:15265 */         if (other == MRProtos.StringCounterGroupMapProto.getDefaultInstance()) {
/* 14387:15265 */           return this;
/* 14388:      */         }
/* 14389:15266 */         if (other.hasKey())
/* 14390:      */         {
/* 14391:15267 */           this.bitField0_ |= 0x1;
/* 14392:15268 */           this.key_ = other.key_;
/* 14393:15269 */           onChanged();
/* 14394:      */         }
/* 14395:15271 */         if (other.hasValue()) {
/* 14396:15272 */           mergeValue(other.getValue());
/* 14397:      */         }
/* 14398:15274 */         mergeUnknownFields(other.getUnknownFields());
/* 14399:15275 */         return this;
/* 14400:      */       }
/* 14401:      */       
/* 14402:      */       public final boolean isInitialized()
/* 14403:      */       {
/* 14404:15279 */         return true;
/* 14405:      */       }
/* 14406:      */       
/* 14407:      */       public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry)
/* 14408:      */         throws IOException
/* 14409:      */       {
/* 14410:15286 */         MRProtos.StringCounterGroupMapProto parsedMessage = null;
/* 14411:      */         try
/* 14412:      */         {
/* 14413:15288 */           parsedMessage = (MRProtos.StringCounterGroupMapProto)MRProtos.StringCounterGroupMapProto.PARSER.parsePartialFrom(input, extensionRegistry);
/* 14414:      */         }
/* 14415:      */         catch (InvalidProtocolBufferException e)
/* 14416:      */         {
/* 14417:15290 */           parsedMessage = (MRProtos.StringCounterGroupMapProto)e.getUnfinishedMessage();
/* 14418:15291 */           throw e;
/* 14419:      */         }
/* 14420:      */         finally
/* 14421:      */         {
/* 14422:15293 */           if (parsedMessage != null) {
/* 14423:15294 */             mergeFrom(parsedMessage);
/* 14424:      */           }
/* 14425:      */         }
/* 14426:15297 */         return this;
/* 14427:      */       }
/* 14428:      */       
/* 14429:15302 */       private Object key_ = "";
/* 14430:      */       
/* 14431:      */       public boolean hasKey()
/* 14432:      */       {
/* 14433:15307 */         return (this.bitField0_ & 0x1) == 1;
/* 14434:      */       }
/* 14435:      */       
/* 14436:      */       public String getKey()
/* 14437:      */       {
/* 14438:15313 */         Object ref = this.key_;
/* 14439:15314 */         if (!(ref instanceof String))
/* 14440:      */         {
/* 14441:15315 */           String s = ((ByteString)ref).toStringUtf8();
/* 14442:      */           
/* 14443:15317 */           this.key_ = s;
/* 14444:15318 */           return s;
/* 14445:      */         }
/* 14446:15320 */         return (String)ref;
/* 14447:      */       }
/* 14448:      */       
/* 14449:      */       public ByteString getKeyBytes()
/* 14450:      */       {
/* 14451:15328 */         Object ref = this.key_;
/* 14452:15329 */         if ((ref instanceof String))
/* 14453:      */         {
/* 14454:15330 */           ByteString b = ByteString.copyFromUtf8((String)ref);
/* 14455:      */           
/* 14456:      */ 
/* 14457:15333 */           this.key_ = b;
/* 14458:15334 */           return b;
/* 14459:      */         }
/* 14460:15336 */         return (ByteString)ref;
/* 14461:      */       }
/* 14462:      */       
/* 14463:      */       public Builder setKey(String value)
/* 14464:      */       {
/* 14465:15344 */         if (value == null) {
/* 14466:15345 */           throw new NullPointerException();
/* 14467:      */         }
/* 14468:15347 */         this.bitField0_ |= 0x1;
/* 14469:15348 */         this.key_ = value;
/* 14470:15349 */         onChanged();
/* 14471:15350 */         return this;
/* 14472:      */       }
/* 14473:      */       
/* 14474:      */       public Builder clearKey()
/* 14475:      */       {
/* 14476:15356 */         this.bitField0_ &= 0xFFFFFFFE;
/* 14477:15357 */         this.key_ = MRProtos.StringCounterGroupMapProto.getDefaultInstance().getKey();
/* 14478:15358 */         onChanged();
/* 14479:15359 */         return this;
/* 14480:      */       }
/* 14481:      */       
/* 14482:      */       public Builder setKeyBytes(ByteString value)
/* 14483:      */       {
/* 14484:15366 */         if (value == null) {
/* 14485:15367 */           throw new NullPointerException();
/* 14486:      */         }
/* 14487:15369 */         this.bitField0_ |= 0x1;
/* 14488:15370 */         this.key_ = value;
/* 14489:15371 */         onChanged();
/* 14490:15372 */         return this;
/* 14491:      */       }
/* 14492:      */       
/* 14493:15376 */       private MRProtos.CounterGroupProto value_ = MRProtos.CounterGroupProto.getDefaultInstance();
/* 14494:      */       private SingleFieldBuilder<MRProtos.CounterGroupProto, MRProtos.CounterGroupProto.Builder, MRProtos.CounterGroupProtoOrBuilder> valueBuilder_;
/* 14495:      */       
/* 14496:      */       public boolean hasValue()
/* 14497:      */       {
/* 14498:15383 */         return (this.bitField0_ & 0x2) == 2;
/* 14499:      */       }
/* 14500:      */       
/* 14501:      */       public MRProtos.CounterGroupProto getValue()
/* 14502:      */       {
/* 14503:15389 */         if (this.valueBuilder_ == null) {
/* 14504:15390 */           return this.value_;
/* 14505:      */         }
/* 14506:15392 */         return (MRProtos.CounterGroupProto)this.valueBuilder_.getMessage();
/* 14507:      */       }
/* 14508:      */       
/* 14509:      */       public Builder setValue(MRProtos.CounterGroupProto value)
/* 14510:      */       {
/* 14511:15399 */         if (this.valueBuilder_ == null)
/* 14512:      */         {
/* 14513:15400 */           if (value == null) {
/* 14514:15401 */             throw new NullPointerException();
/* 14515:      */           }
/* 14516:15403 */           this.value_ = value;
/* 14517:15404 */           onChanged();
/* 14518:      */         }
/* 14519:      */         else
/* 14520:      */         {
/* 14521:15406 */           this.valueBuilder_.setMessage(value);
/* 14522:      */         }
/* 14523:15408 */         this.bitField0_ |= 0x2;
/* 14524:15409 */         return this;
/* 14525:      */       }
/* 14526:      */       
/* 14527:      */       public Builder setValue(MRProtos.CounterGroupProto.Builder builderForValue)
/* 14528:      */       {
/* 14529:15416 */         if (this.valueBuilder_ == null)
/* 14530:      */         {
/* 14531:15417 */           this.value_ = builderForValue.build();
/* 14532:15418 */           onChanged();
/* 14533:      */         }
/* 14534:      */         else
/* 14535:      */         {
/* 14536:15420 */           this.valueBuilder_.setMessage(builderForValue.build());
/* 14537:      */         }
/* 14538:15422 */         this.bitField0_ |= 0x2;
/* 14539:15423 */         return this;
/* 14540:      */       }
/* 14541:      */       
/* 14542:      */       public Builder mergeValue(MRProtos.CounterGroupProto value)
/* 14543:      */       {
/* 14544:15429 */         if (this.valueBuilder_ == null)
/* 14545:      */         {
/* 14546:15430 */           if (((this.bitField0_ & 0x2) == 2) && (this.value_ != MRProtos.CounterGroupProto.getDefaultInstance())) {
/* 14547:15432 */             this.value_ = MRProtos.CounterGroupProto.newBuilder(this.value_).mergeFrom(value).buildPartial();
/* 14548:      */           } else {
/* 14549:15435 */             this.value_ = value;
/* 14550:      */           }
/* 14551:15437 */           onChanged();
/* 14552:      */         }
/* 14553:      */         else
/* 14554:      */         {
/* 14555:15439 */           this.valueBuilder_.mergeFrom(value);
/* 14556:      */         }
/* 14557:15441 */         this.bitField0_ |= 0x2;
/* 14558:15442 */         return this;
/* 14559:      */       }
/* 14560:      */       
/* 14561:      */       public Builder clearValue()
/* 14562:      */       {
/* 14563:15448 */         if (this.valueBuilder_ == null)
/* 14564:      */         {
/* 14565:15449 */           this.value_ = MRProtos.CounterGroupProto.getDefaultInstance();
/* 14566:15450 */           onChanged();
/* 14567:      */         }
/* 14568:      */         else
/* 14569:      */         {
/* 14570:15452 */           this.valueBuilder_.clear();
/* 14571:      */         }
/* 14572:15454 */         this.bitField0_ &= 0xFFFFFFFD;
/* 14573:15455 */         return this;
/* 14574:      */       }
/* 14575:      */       
/* 14576:      */       public MRProtos.CounterGroupProto.Builder getValueBuilder()
/* 14577:      */       {
/* 14578:15461 */         this.bitField0_ |= 0x2;
/* 14579:15462 */         onChanged();
/* 14580:15463 */         return (MRProtos.CounterGroupProto.Builder)getValueFieldBuilder().getBuilder();
/* 14581:      */       }
/* 14582:      */       
/* 14583:      */       public MRProtos.CounterGroupProtoOrBuilder getValueOrBuilder()
/* 14584:      */       {
/* 14585:15469 */         if (this.valueBuilder_ != null) {
/* 14586:15470 */           return (MRProtos.CounterGroupProtoOrBuilder)this.valueBuilder_.getMessageOrBuilder();
/* 14587:      */         }
/* 14588:15472 */         return this.value_;
/* 14589:      */       }
/* 14590:      */       
/* 14591:      */       private SingleFieldBuilder<MRProtos.CounterGroupProto, MRProtos.CounterGroupProto.Builder, MRProtos.CounterGroupProtoOrBuilder> getValueFieldBuilder()
/* 14592:      */       {
/* 14593:15481 */         if (this.valueBuilder_ == null)
/* 14594:      */         {
/* 14595:15482 */           this.valueBuilder_ = new SingleFieldBuilder(this.value_, getParentForChildren(), isClean());
/* 14596:      */           
/* 14597:      */ 
/* 14598:      */ 
/* 14599:      */ 
/* 14600:15487 */           this.value_ = null;
/* 14601:      */         }
/* 14602:15489 */         return this.valueBuilder_;
/* 14603:      */       }
/* 14604:      */     }
/* 14605:      */     
/* 14606:      */     static
/* 14607:      */     {
/* 14608:15496 */       defaultInstance = new StringCounterGroupMapProto(true);
/* 14609:15497 */       defaultInstance.initFields();
/* 14610:      */     }
/* 14611:      */   }
/* 14612:      */   
/* 14613:      */   public static Descriptors.FileDescriptor getDescriptor()
/* 14614:      */   {
/* 14615:15571 */     return descriptor;
/* 14616:      */   }
/* 14617:      */   
/* 14618:      */   static
/* 14619:      */   {
/* 14620:15576 */     String[] descriptorData = { "\n\017mr_protos.proto\022\020hadoop.mapreduce\032\021yarn_protos.proto\"I\n\nJobIdProto\022/\n\006app_id\030\001 \001(\0132\037.hadoop.yarn.ApplicationIdProto\022\n\n\002id\030\002 \001(\005\"{\n\013TaskIdProto\022,\n\006job_id\030\001 \001(\0132\034.hadoop.mapreduce.JobIdProto\0222\n\ttask_type\030\002 \001(\0162\037.hadoop.mapreduce.TaskTypeProto\022\n\n\002id\030\003 \001(\005\"P\n\022TaskAttemptIdProto\022.\n\007task_id\030\001 \001(\0132\035.hadoop.mapreduce.TaskIdProto\022\n\n\002id\030\002 \001(\005\"A\n\fCounterProto\022\f\n\004name\030\001 \001(\t\022\024\n\fdisplay_name\030\002 \001(\t\022\r\n\005value\030\003 ", "\001(\003\"r\n\021CounterGroupProto\022\f\n\004name\030\001 \001(\t\022\024\n\fdisplay_name\030\002 \001(\t\0229\n\bcounters\030\003 \003(\0132'.hadoop.mapreduce.StringCounterMapProto\"U\n\rCountersProto\022D\n\016counter_groups\030\001 \003(\0132,.hadoop.mapreduce.StringCounterGroupMapProto\"ü\002\n\017TaskReportProto\022.\n\007task_id\030\001 \001(\0132\035.hadoop.mapreduce.TaskIdProto\0224\n\ntask_state\030\002 \001(\0162 .hadoop.mapreduce.TaskStateProto\022\020\n\bprogress\030\003 \001(\002\022\022\n\nstart_time\030\004 \001(\003\022\023\n\013finish_time\030\005 \001(\003\0221\n\bcounters\030", "\006 \001(\0132\037.hadoop.mapreduce.CountersProto\022>\n\020running_attempts\030\007 \003(\0132$.hadoop.mapreduce.TaskAttemptIdProto\022@\n\022successful_attempt\030\b \001(\0132$.hadoop.mapreduce.TaskAttemptIdProto\022\023\n\013diagnostics\030\t \003(\t\"¨\004\n\026TaskAttemptReportProto\022=\n\017task_attempt_id\030\001 \001(\0132$.hadoop.mapreduce.TaskAttemptIdProto\022C\n\022task_attempt_state\030\002 \001(\0162'.hadoop.mapreduce.TaskAttemptStateProto\022\020\n\bprogress\030\003 \001(\002\022\022\n\nstart_time\030\004 \001(\003\022\023\n\013finish_tim", "e\030\005 \001(\003\0221\n\bcounters\030\006 \001(\0132\037.hadoop.mapreduce.CountersProto\022\027\n\017diagnostic_info\030\007 \001(\t\022\024\n\fstate_string\030\b \001(\t\022+\n\005phase\030\t \001(\0162\034.hadoop.mapreduce.PhaseProto\022\033\n\023shuffle_finish_time\030\n \001(\003\022\030\n\020sort_finish_time\030\013 \001(\003\022\031\n\021node_manager_host\030\f \001(\t\022\031\n\021node_manager_port\030\r \001(\005\022\036\n\026node_manager_http_port\030\016 \001(\005\0223\n\fcontainer_id\030\017 \001(\0132\035.hadoop.yarn.ContainerIdProto\"´\003\n\016JobReportProto\022,\n\006job_id\030\001 \001(\0132\034.hadoop.mapreduce.J", "obIdProto\0222\n\tjob_state\030\002 \001(\0162\037.hadoop.mapreduce.JobStateProto\022\024\n\fmap_progress\030\003 \001(\002\022\027\n\017reduce_progress\030\004 \001(\002\022\030\n\020cleanup_progress\030\005 \001(\002\022\026\n\016setup_progress\030\006 \001(\002\022\022\n\nstart_time\030\007 \001(\003\022\023\n\013finish_time\030\b \001(\003\022\f\n\004user\030\t \001(\t\022\017\n\007jobName\030\n \001(\t\022\023\n\013trackingUrl\030\013 \001(\t\022\023\n\013diagnostics\030\f \001(\t\022\017\n\007jobFile\030\r \001(\t\022/\n\bam_infos\030\016 \003(\0132\035.hadoop.mapreduce.AMInfoProto\022\023\n\013submit_time\030\017 \001(\003\022\026\n\007is_uber\030\020 \001(\b:\005false\"ô\001\n\013AMInfoProto\022", "F\n\026application_attempt_id\030\001 \001(\0132&.hadoop.yarn.ApplicationAttemptIdProto\022\022\n\nstart_time\030\002 \001(\003\0223\n\fcontainer_id\030\003 \001(\0132\035.hadoop.yarn.ContainerIdProto\022\031\n\021node_manager_host\030\004 \001(\t\022\031\n\021node_manager_port\030\005 \001(\005\022\036\n\026node_manager_http_port\030\006 \001(\005\"ó\001\n\037TaskAttemptCompletionEventProto\0228\n\nattempt_id\030\001 \001(\0132$.hadoop.mapreduce.TaskAttemptIdProto\022G\n\006status\030\002 \001(\01627.hadoop.mapreduce.TaskAttemptCompletionEventStatusProto\022!\n", "\031map_output_server_address\030\003 \001(\t\022\030\n\020attempt_run_time\030\004 \001(\005\022\020\n\bevent_id\030\005 \001(\005\"S\n\025StringCounterMapProto\022\013\n\003key\030\001 \001(\t\022-\n\005value\030\002 \001(\0132\036.hadoop.mapreduce.CounterProto\"]\n\032StringCounterGroupMapProto\022\013\n\003key\030\001 \001(\t\0222\n\005value\030\002 \001(\0132#.hadoop.mapreduce.CounterGroupProto*$\n\rTaskTypeProto\022\007\n\003MAP\020\001\022\n\n\006REDUCE\020\002*n\n\016TaskStateProto\022\n\n\006TS_NEW\020\001\022\020\n\fTS_SCHEDULED\020\002\022\016\n\nTS_RUNNING\020\003\022\020\n\fTS_SUCCEEDED\020\004\022\r\n\tTS_FAILED\020\005\022\r\n\tTS_KI", "LLED\020\006*_\n\nPhaseProto\022\016\n\nP_STARTING\020\001\022\t\n\005P_MAP\020\002\022\r\n\tP_SHUFFLE\020\003\022\n\n\006P_SORT\020\004\022\f\n\bP_REDUCE\020\005\022\r\n\tP_CLEANUP\020\006*\001\n\025TaskAttemptStateProto\022\n\n\006TA_NEW\020\001\022\017\n\013TA_STARTING\020\002\022\016\n\nTA_RUNNING\020\003\022\025\n\021TA_COMMIT_PENDING\020\004\022\020\n\fTA_SUCCEEDED\020\005\022\r\n\tTA_FAILED\020\006\022\r\n\tTA_KILLED\020\007*q\n\rJobStateProto\022\t\n\005J_NEW\020\001\022\f\n\bJ_INITED\020\002\022\r\n\tJ_RUNNING\020\003\022\017\n\013J_SUCCEEDED\020\004\022\f\n\bJ_FAILED\020\005\022\f\n\bJ_KILLED\020\006\022\013\n\007J_ERROR\020\007*\001\n%TaskAttemptCompletionEventStatusPro", "to\022\017\n\013TACE_FAILED\020\001\022\017\n\013TACE_KILLED\020\002\022\022\n\016TACE_SUCCEEDED\020\003\022\021\n\rTACE_OBSOLETE\020\004\022\022\n\016TACE_TIPFAILED\020\005B6\n$org.apache.hadoop.mapreduce.v2.protoB\bMRProtos\001\001 \001\001" };
/* 14621:      */     
/* 14622:      */ 
/* 14623:      */ 
/* 14624:      */ 
/* 14625:      */ 
/* 14626:      */ 
/* 14627:      */ 
/* 14628:      */ 
/* 14629:      */ 
/* 14630:      */ 
/* 14631:      */ 
/* 14632:      */ 
/* 14633:      */ 
/* 14634:      */ 
/* 14635:      */ 
/* 14636:      */ 
/* 14637:      */ 
/* 14638:      */ 
/* 14639:      */ 
/* 14640:      */ 
/* 14641:      */ 
/* 14642:      */ 
/* 14643:      */ 
/* 14644:      */ 
/* 14645:      */ 
/* 14646:      */ 
/* 14647:      */ 
/* 14648:      */ 
/* 14649:      */ 
/* 14650:      */ 
/* 14651:      */ 
/* 14652:      */ 
/* 14653:      */ 
/* 14654:      */ 
/* 14655:      */ 
/* 14656:      */ 
/* 14657:      */ 
/* 14658:      */ 
/* 14659:      */ 
/* 14660:      */ 
/* 14661:      */ 
/* 14662:      */ 
/* 14663:      */ 
/* 14664:      */ 
/* 14665:      */ 
/* 14666:      */ 
/* 14667:      */ 
/* 14668:      */ 
/* 14669:      */ 
/* 14670:      */ 
/* 14671:      */ 
/* 14672:      */ 
/* 14673:      */ 
/* 14674:      */ 
/* 14675:      */ 
/* 14676:      */ 
/* 14677:      */ 
/* 14678:      */ 
/* 14679:      */ 
/* 14680:      */ 
/* 14681:      */ 
/* 14682:      */ 
/* 14683:      */ 
/* 14684:      */ 
/* 14685:      */ 
/* 14686:      */ 
/* 14687:      */ 
/* 14688:      */ 
/* 14689:      */ 
/* 14690:      */ 
/* 14691:      */ 
/* 14692:      */ 
/* 14693:      */ 
/* 14694:      */ 
/* 14695:      */ 
/* 14696:      */ 
/* 14697:      */ 
/* 14698:      */ 
/* 14699:      */ 
/* 14700:      */ 
/* 14701:      */ 
/* 14702:      */ 
/* 14703:      */ 
/* 14704:      */ 
/* 14705:      */ 
/* 14706:15662 */     Descriptors.FileDescriptor.InternalDescriptorAssigner assigner = new Descriptors.FileDescriptor.InternalDescriptorAssigner()
/* 14707:      */     {
/* 14708:      */       public ExtensionRegistry assignDescriptors(Descriptors.FileDescriptor root)
/* 14709:      */       {
/* 14710:15666 */         MRProtos.access$17602(root);
/* 14711:15667 */         MRProtos.access$002((Descriptors.Descriptor)MRProtos.getDescriptor().getMessageTypes().get(0));
/* 14712:      */         
/* 14713:15669 */         MRProtos.access$102(new GeneratedMessage.FieldAccessorTable(MRProtos.internal_static_hadoop_mapreduce_JobIdProto_descriptor, new String[] { "AppId", "Id" }));
/* 14714:      */         
/* 14715:      */ 
/* 14716:      */ 
/* 14717:15673 */         MRProtos.access$1002((Descriptors.Descriptor)MRProtos.getDescriptor().getMessageTypes().get(1));
/* 14718:      */         
/* 14719:15675 */         MRProtos.access$1102(new GeneratedMessage.FieldAccessorTable(MRProtos.internal_static_hadoop_mapreduce_TaskIdProto_descriptor, new String[] { "JobId", "TaskType", "Id" }));
/* 14720:      */         
/* 14721:      */ 
/* 14722:      */ 
/* 14723:15679 */         MRProtos.access$2102((Descriptors.Descriptor)MRProtos.getDescriptor().getMessageTypes().get(2));
/* 14724:      */         
/* 14725:15681 */         MRProtos.access$2202(new GeneratedMessage.FieldAccessorTable(MRProtos.internal_static_hadoop_mapreduce_TaskAttemptIdProto_descriptor, new String[] { "TaskId", "Id" }));
/* 14726:      */         
/* 14727:      */ 
/* 14728:      */ 
/* 14729:15685 */         MRProtos.access$3102((Descriptors.Descriptor)MRProtos.getDescriptor().getMessageTypes().get(3));
/* 14730:      */         
/* 14731:15687 */         MRProtos.access$3202(new GeneratedMessage.FieldAccessorTable(MRProtos.internal_static_hadoop_mapreduce_CounterProto_descriptor, new String[] { "Name", "DisplayName", "Value" }));
/* 14732:      */         
/* 14733:      */ 
/* 14734:      */ 
/* 14735:15691 */         MRProtos.access$4202((Descriptors.Descriptor)MRProtos.getDescriptor().getMessageTypes().get(4));
/* 14736:      */         
/* 14737:15693 */         MRProtos.access$4302(new GeneratedMessage.FieldAccessorTable(MRProtos.internal_static_hadoop_mapreduce_CounterGroupProto_descriptor, new String[] { "Name", "DisplayName", "Counters" }));
/* 14738:      */         
/* 14739:      */ 
/* 14740:      */ 
/* 14741:15697 */         MRProtos.access$5402((Descriptors.Descriptor)MRProtos.getDescriptor().getMessageTypes().get(5));
/* 14742:      */         
/* 14743:15699 */         MRProtos.access$5502(new GeneratedMessage.FieldAccessorTable(MRProtos.internal_static_hadoop_mapreduce_CountersProto_descriptor, new String[] { "CounterGroups" }));
/* 14744:      */         
/* 14745:      */ 
/* 14746:      */ 
/* 14747:15703 */         MRProtos.access$6302((Descriptors.Descriptor)MRProtos.getDescriptor().getMessageTypes().get(6));
/* 14748:      */         
/* 14749:15705 */         MRProtos.access$6402(new GeneratedMessage.FieldAccessorTable(MRProtos.internal_static_hadoop_mapreduce_TaskReportProto_descriptor, new String[] { "TaskId", "TaskState", "Progress", "StartTime", "FinishTime", "Counters", "RunningAttempts", "SuccessfulAttempt", "Diagnostics" }));
/* 14750:      */         
/* 14751:      */ 
/* 14752:      */ 
/* 14753:15709 */         MRProtos.access$8102((Descriptors.Descriptor)MRProtos.getDescriptor().getMessageTypes().get(7));
/* 14754:      */         
/* 14755:15711 */         MRProtos.access$8202(new GeneratedMessage.FieldAccessorTable(MRProtos.internal_static_hadoop_mapreduce_TaskAttemptReportProto_descriptor, new String[] { "TaskAttemptId", "TaskAttemptState", "Progress", "StartTime", "FinishTime", "Counters", "DiagnosticInfo", "StateString", "Phase", "ShuffleFinishTime", "SortFinishTime", "NodeManagerHost", "NodeManagerPort", "NodeManagerHttpPort", "ContainerId" }));
/* 14756:      */         
/* 14757:      */ 
/* 14758:      */ 
/* 14759:15715 */         MRProtos.access$10402((Descriptors.Descriptor)MRProtos.getDescriptor().getMessageTypes().get(8));
/* 14760:      */         
/* 14761:15717 */         MRProtos.access$10502(new GeneratedMessage.FieldAccessorTable(MRProtos.internal_static_hadoop_mapreduce_JobReportProto_descriptor, new String[] { "JobId", "JobState", "MapProgress", "ReduceProgress", "CleanupProgress", "SetupProgress", "StartTime", "FinishTime", "User", "JobName", "TrackingUrl", "Diagnostics", "JobFile", "AmInfos", "SubmitTime", "IsUber" }));
/* 14762:      */         
/* 14763:      */ 
/* 14764:      */ 
/* 14765:15721 */         MRProtos.access$12902((Descriptors.Descriptor)MRProtos.getDescriptor().getMessageTypes().get(9));
/* 14766:      */         
/* 14767:15723 */         MRProtos.access$13002(new GeneratedMessage.FieldAccessorTable(MRProtos.internal_static_hadoop_mapreduce_AMInfoProto_descriptor, new String[] { "ApplicationAttemptId", "StartTime", "ContainerId", "NodeManagerHost", "NodeManagerPort", "NodeManagerHttpPort" }));
/* 14768:      */         
/* 14769:      */ 
/* 14770:      */ 
/* 14771:15727 */         MRProtos.access$14302((Descriptors.Descriptor)MRProtos.getDescriptor().getMessageTypes().get(10));
/* 14772:      */         
/* 14773:15729 */         MRProtos.access$14402(new GeneratedMessage.FieldAccessorTable(MRProtos.internal_static_hadoop_mapreduce_TaskAttemptCompletionEventProto_descriptor, new String[] { "AttemptId", "Status", "MapOutputServerAddress", "AttemptRunTime", "EventId" }));
/* 14774:      */         
/* 14775:      */ 
/* 14776:      */ 
/* 14777:15733 */         MRProtos.access$15602((Descriptors.Descriptor)MRProtos.getDescriptor().getMessageTypes().get(11));
/* 14778:      */         
/* 14779:15735 */         MRProtos.access$15702(new GeneratedMessage.FieldAccessorTable(MRProtos.internal_static_hadoop_mapreduce_StringCounterMapProto_descriptor, new String[] { "Key", "Value" }));
/* 14780:      */         
/* 14781:      */ 
/* 14782:      */ 
/* 14783:15739 */         MRProtos.access$16602((Descriptors.Descriptor)MRProtos.getDescriptor().getMessageTypes().get(12));
/* 14784:      */         
/* 14785:15741 */         MRProtos.access$16702(new GeneratedMessage.FieldAccessorTable(MRProtos.internal_static_hadoop_mapreduce_StringCounterGroupMapProto_descriptor, new String[] { "Key", "Value" }));
/* 14786:      */         
/* 14787:      */ 
/* 14788:      */ 
/* 14789:15745 */         return null;
/* 14790:      */       }
/* 14791:15747 */     };
/* 14792:15748 */     Descriptors.FileDescriptor.internalBuildGeneratedFileFrom(descriptorData, new Descriptors.FileDescriptor[] { YarnProtos.getDescriptor() }, assigner);
/* 14793:      */   }
/* 14794:      */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-common-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.v2.proto.MRProtos
 * JD-Core Version:    0.7.0.1
 */