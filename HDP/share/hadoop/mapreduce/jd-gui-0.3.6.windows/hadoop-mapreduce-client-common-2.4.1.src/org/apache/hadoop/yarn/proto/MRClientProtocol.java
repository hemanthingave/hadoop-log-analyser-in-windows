/*    1:     */ package org.apache.hadoop.yarn.proto;
/*    2:     */ 
/*    3:     */ import com.google.protobuf.BlockingRpcChannel;
/*    4:     */ import com.google.protobuf.BlockingService;
/*    5:     */ import com.google.protobuf.Descriptors.FileDescriptor;
/*    6:     */ import com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner;
/*    7:     */ import com.google.protobuf.Descriptors.MethodDescriptor;
/*    8:     */ import com.google.protobuf.Descriptors.ServiceDescriptor;
/*    9:     */ import com.google.protobuf.ExtensionRegistry;
/*   10:     */ import com.google.protobuf.Message;
/*   11:     */ import com.google.protobuf.RpcCallback;
/*   12:     */ import com.google.protobuf.RpcChannel;
/*   13:     */ import com.google.protobuf.RpcController;
/*   14:     */ import com.google.protobuf.RpcUtil;
/*   15:     */ import com.google.protobuf.Service;
/*   16:     */ import com.google.protobuf.ServiceException;
/*   17:     */ import java.util.List;
/*   18:     */ import org.apache.hadoop.mapreduce.v2.proto.MRServiceProtos;
/*   19:     */ import org.apache.hadoop.mapreduce.v2.proto.MRServiceProtos.FailTaskAttemptRequestProto;
/*   20:     */ import org.apache.hadoop.mapreduce.v2.proto.MRServiceProtos.FailTaskAttemptResponseProto;
/*   21:     */ import org.apache.hadoop.mapreduce.v2.proto.MRServiceProtos.GetCountersRequestProto;
/*   22:     */ import org.apache.hadoop.mapreduce.v2.proto.MRServiceProtos.GetCountersResponseProto;
/*   23:     */ import org.apache.hadoop.mapreduce.v2.proto.MRServiceProtos.GetDiagnosticsRequestProto;
/*   24:     */ import org.apache.hadoop.mapreduce.v2.proto.MRServiceProtos.GetDiagnosticsResponseProto;
/*   25:     */ import org.apache.hadoop.mapreduce.v2.proto.MRServiceProtos.GetJobReportRequestProto;
/*   26:     */ import org.apache.hadoop.mapreduce.v2.proto.MRServiceProtos.GetJobReportResponseProto;
/*   27:     */ import org.apache.hadoop.mapreduce.v2.proto.MRServiceProtos.GetTaskAttemptCompletionEventsRequestProto;
/*   28:     */ import org.apache.hadoop.mapreduce.v2.proto.MRServiceProtos.GetTaskAttemptCompletionEventsResponseProto;
/*   29:     */ import org.apache.hadoop.mapreduce.v2.proto.MRServiceProtos.GetTaskAttemptReportRequestProto;
/*   30:     */ import org.apache.hadoop.mapreduce.v2.proto.MRServiceProtos.GetTaskAttemptReportResponseProto;
/*   31:     */ import org.apache.hadoop.mapreduce.v2.proto.MRServiceProtos.GetTaskReportRequestProto;
/*   32:     */ import org.apache.hadoop.mapreduce.v2.proto.MRServiceProtos.GetTaskReportResponseProto;
/*   33:     */ import org.apache.hadoop.mapreduce.v2.proto.MRServiceProtos.GetTaskReportsRequestProto;
/*   34:     */ import org.apache.hadoop.mapreduce.v2.proto.MRServiceProtos.GetTaskReportsResponseProto;
/*   35:     */ import org.apache.hadoop.mapreduce.v2.proto.MRServiceProtos.KillJobRequestProto;
/*   36:     */ import org.apache.hadoop.mapreduce.v2.proto.MRServiceProtos.KillJobResponseProto;
/*   37:     */ import org.apache.hadoop.mapreduce.v2.proto.MRServiceProtos.KillTaskAttemptRequestProto;
/*   38:     */ import org.apache.hadoop.mapreduce.v2.proto.MRServiceProtos.KillTaskAttemptResponseProto;
/*   39:     */ import org.apache.hadoop.mapreduce.v2.proto.MRServiceProtos.KillTaskRequestProto;
/*   40:     */ import org.apache.hadoop.mapreduce.v2.proto.MRServiceProtos.KillTaskResponseProto;
/*   41:     */ import org.apache.hadoop.security.proto.SecurityProtos;
/*   42:     */ import org.apache.hadoop.security.proto.SecurityProtos.CancelDelegationTokenRequestProto;
/*   43:     */ import org.apache.hadoop.security.proto.SecurityProtos.CancelDelegationTokenResponseProto;
/*   44:     */ import org.apache.hadoop.security.proto.SecurityProtos.GetDelegationTokenRequestProto;
/*   45:     */ import org.apache.hadoop.security.proto.SecurityProtos.GetDelegationTokenResponseProto;
/*   46:     */ import org.apache.hadoop.security.proto.SecurityProtos.RenewDelegationTokenRequestProto;
/*   47:     */ import org.apache.hadoop.security.proto.SecurityProtos.RenewDelegationTokenResponseProto;
/*   48:     */ 
/*   49:     */ public final class MRClientProtocol
/*   50:     */ {
/*   51:     */   private static Descriptors.FileDescriptor descriptor;
/*   52:     */   public static void registerAllExtensions(ExtensionRegistry registry) {}
/*   53:     */   
/*   54:     */   public static abstract class MRClientProtocolService
/*   55:     */     implements Service
/*   56:     */   {
/*   57:     */     public static Service newReflectiveService(Interface impl)
/*   58:     */     {
/*   59: 139 */       new MRClientProtocolService()
/*   60:     */       {
/*   61:     */         public void getJobReport(RpcController controller, MRServiceProtos.GetJobReportRequestProto request, RpcCallback<MRServiceProtos.GetJobReportResponseProto> done)
/*   62:     */         {
/*   63: 145 */           this.val$impl.getJobReport(controller, request, done);
/*   64:     */         }
/*   65:     */         
/*   66:     */         public void getTaskReport(RpcController controller, MRServiceProtos.GetTaskReportRequestProto request, RpcCallback<MRServiceProtos.GetTaskReportResponseProto> done)
/*   67:     */         {
/*   68: 153 */           this.val$impl.getTaskReport(controller, request, done);
/*   69:     */         }
/*   70:     */         
/*   71:     */         public void getTaskAttemptReport(RpcController controller, MRServiceProtos.GetTaskAttemptReportRequestProto request, RpcCallback<MRServiceProtos.GetTaskAttemptReportResponseProto> done)
/*   72:     */         {
/*   73: 161 */           this.val$impl.getTaskAttemptReport(controller, request, done);
/*   74:     */         }
/*   75:     */         
/*   76:     */         public void getCounters(RpcController controller, MRServiceProtos.GetCountersRequestProto request, RpcCallback<MRServiceProtos.GetCountersResponseProto> done)
/*   77:     */         {
/*   78: 169 */           this.val$impl.getCounters(controller, request, done);
/*   79:     */         }
/*   80:     */         
/*   81:     */         public void getTaskAttemptCompletionEvents(RpcController controller, MRServiceProtos.GetTaskAttemptCompletionEventsRequestProto request, RpcCallback<MRServiceProtos.GetTaskAttemptCompletionEventsResponseProto> done)
/*   82:     */         {
/*   83: 177 */           this.val$impl.getTaskAttemptCompletionEvents(controller, request, done);
/*   84:     */         }
/*   85:     */         
/*   86:     */         public void getTaskReports(RpcController controller, MRServiceProtos.GetTaskReportsRequestProto request, RpcCallback<MRServiceProtos.GetTaskReportsResponseProto> done)
/*   87:     */         {
/*   88: 185 */           this.val$impl.getTaskReports(controller, request, done);
/*   89:     */         }
/*   90:     */         
/*   91:     */         public void getDiagnostics(RpcController controller, MRServiceProtos.GetDiagnosticsRequestProto request, RpcCallback<MRServiceProtos.GetDiagnosticsResponseProto> done)
/*   92:     */         {
/*   93: 193 */           this.val$impl.getDiagnostics(controller, request, done);
/*   94:     */         }
/*   95:     */         
/*   96:     */         public void getDelegationToken(RpcController controller, SecurityProtos.GetDelegationTokenRequestProto request, RpcCallback<SecurityProtos.GetDelegationTokenResponseProto> done)
/*   97:     */         {
/*   98: 201 */           this.val$impl.getDelegationToken(controller, request, done);
/*   99:     */         }
/*  100:     */         
/*  101:     */         public void killJob(RpcController controller, MRServiceProtos.KillJobRequestProto request, RpcCallback<MRServiceProtos.KillJobResponseProto> done)
/*  102:     */         {
/*  103: 209 */           this.val$impl.killJob(controller, request, done);
/*  104:     */         }
/*  105:     */         
/*  106:     */         public void killTask(RpcController controller, MRServiceProtos.KillTaskRequestProto request, RpcCallback<MRServiceProtos.KillTaskResponseProto> done)
/*  107:     */         {
/*  108: 217 */           this.val$impl.killTask(controller, request, done);
/*  109:     */         }
/*  110:     */         
/*  111:     */         public void killTaskAttempt(RpcController controller, MRServiceProtos.KillTaskAttemptRequestProto request, RpcCallback<MRServiceProtos.KillTaskAttemptResponseProto> done)
/*  112:     */         {
/*  113: 225 */           this.val$impl.killTaskAttempt(controller, request, done);
/*  114:     */         }
/*  115:     */         
/*  116:     */         public void failTaskAttempt(RpcController controller, MRServiceProtos.FailTaskAttemptRequestProto request, RpcCallback<MRServiceProtos.FailTaskAttemptResponseProto> done)
/*  117:     */         {
/*  118: 233 */           this.val$impl.failTaskAttempt(controller, request, done);
/*  119:     */         }
/*  120:     */         
/*  121:     */         public void renewDelegationToken(RpcController controller, SecurityProtos.RenewDelegationTokenRequestProto request, RpcCallback<SecurityProtos.RenewDelegationTokenResponseProto> done)
/*  122:     */         {
/*  123: 241 */           this.val$impl.renewDelegationToken(controller, request, done);
/*  124:     */         }
/*  125:     */         
/*  126:     */         public void cancelDelegationToken(RpcController controller, SecurityProtos.CancelDelegationTokenRequestProto request, RpcCallback<SecurityProtos.CancelDelegationTokenResponseProto> done)
/*  127:     */         {
/*  128: 249 */           this.val$impl.cancelDelegationToken(controller, request, done);
/*  129:     */         }
/*  130:     */       };
/*  131:     */     }
/*  132:     */     
/*  133:     */     public static BlockingService newReflectiveBlockingService(BlockingInterface impl)
/*  134:     */     {
/*  135: 257 */       new BlockingService()
/*  136:     */       {
/*  137:     */         public final Descriptors.ServiceDescriptor getDescriptorForType()
/*  138:     */         {
/*  139: 260 */           return MRClientProtocol.MRClientProtocolService.getDescriptor();
/*  140:     */         }
/*  141:     */         
/*  142:     */         public final Message callBlockingMethod(Descriptors.MethodDescriptor method, RpcController controller, Message request)
/*  143:     */           throws ServiceException
/*  144:     */         {
/*  145: 268 */           if (method.getService() != MRClientProtocol.MRClientProtocolService.getDescriptor()) {
/*  146: 269 */             throw new IllegalArgumentException("Service.callBlockingMethod() given method descriptor for wrong service type.");
/*  147:     */           }
/*  148: 273 */           switch (method.getIndex())
/*  149:     */           {
/*  150:     */           case 0: 
/*  151: 275 */             return this.val$impl.getJobReport(controller, (MRServiceProtos.GetJobReportRequestProto)request);
/*  152:     */           case 1: 
/*  153: 277 */             return this.val$impl.getTaskReport(controller, (MRServiceProtos.GetTaskReportRequestProto)request);
/*  154:     */           case 2: 
/*  155: 279 */             return this.val$impl.getTaskAttemptReport(controller, (MRServiceProtos.GetTaskAttemptReportRequestProto)request);
/*  156:     */           case 3: 
/*  157: 281 */             return this.val$impl.getCounters(controller, (MRServiceProtos.GetCountersRequestProto)request);
/*  158:     */           case 4: 
/*  159: 283 */             return this.val$impl.getTaskAttemptCompletionEvents(controller, (MRServiceProtos.GetTaskAttemptCompletionEventsRequestProto)request);
/*  160:     */           case 5: 
/*  161: 285 */             return this.val$impl.getTaskReports(controller, (MRServiceProtos.GetTaskReportsRequestProto)request);
/*  162:     */           case 6: 
/*  163: 287 */             return this.val$impl.getDiagnostics(controller, (MRServiceProtos.GetDiagnosticsRequestProto)request);
/*  164:     */           case 7: 
/*  165: 289 */             return this.val$impl.getDelegationToken(controller, (SecurityProtos.GetDelegationTokenRequestProto)request);
/*  166:     */           case 8: 
/*  167: 291 */             return this.val$impl.killJob(controller, (MRServiceProtos.KillJobRequestProto)request);
/*  168:     */           case 9: 
/*  169: 293 */             return this.val$impl.killTask(controller, (MRServiceProtos.KillTaskRequestProto)request);
/*  170:     */           case 10: 
/*  171: 295 */             return this.val$impl.killTaskAttempt(controller, (MRServiceProtos.KillTaskAttemptRequestProto)request);
/*  172:     */           case 11: 
/*  173: 297 */             return this.val$impl.failTaskAttempt(controller, (MRServiceProtos.FailTaskAttemptRequestProto)request);
/*  174:     */           case 12: 
/*  175: 299 */             return this.val$impl.renewDelegationToken(controller, (SecurityProtos.RenewDelegationTokenRequestProto)request);
/*  176:     */           case 13: 
/*  177: 301 */             return this.val$impl.cancelDelegationToken(controller, (SecurityProtos.CancelDelegationTokenRequestProto)request);
/*  178:     */           }
/*  179: 303 */           throw new AssertionError("Can't get here.");
/*  180:     */         }
/*  181:     */         
/*  182:     */         public final Message getRequestPrototype(Descriptors.MethodDescriptor method)
/*  183:     */         {
/*  184: 310 */           if (method.getService() != MRClientProtocol.MRClientProtocolService.getDescriptor()) {
/*  185: 311 */             throw new IllegalArgumentException("Service.getRequestPrototype() given method descriptor for wrong service type.");
/*  186:     */           }
/*  187: 315 */           switch (method.getIndex())
/*  188:     */           {
/*  189:     */           case 0: 
/*  190: 317 */             return MRServiceProtos.GetJobReportRequestProto.getDefaultInstance();
/*  191:     */           case 1: 
/*  192: 319 */             return MRServiceProtos.GetTaskReportRequestProto.getDefaultInstance();
/*  193:     */           case 2: 
/*  194: 321 */             return MRServiceProtos.GetTaskAttemptReportRequestProto.getDefaultInstance();
/*  195:     */           case 3: 
/*  196: 323 */             return MRServiceProtos.GetCountersRequestProto.getDefaultInstance();
/*  197:     */           case 4: 
/*  198: 325 */             return MRServiceProtos.GetTaskAttemptCompletionEventsRequestProto.getDefaultInstance();
/*  199:     */           case 5: 
/*  200: 327 */             return MRServiceProtos.GetTaskReportsRequestProto.getDefaultInstance();
/*  201:     */           case 6: 
/*  202: 329 */             return MRServiceProtos.GetDiagnosticsRequestProto.getDefaultInstance();
/*  203:     */           case 7: 
/*  204: 331 */             return SecurityProtos.GetDelegationTokenRequestProto.getDefaultInstance();
/*  205:     */           case 8: 
/*  206: 333 */             return MRServiceProtos.KillJobRequestProto.getDefaultInstance();
/*  207:     */           case 9: 
/*  208: 335 */             return MRServiceProtos.KillTaskRequestProto.getDefaultInstance();
/*  209:     */           case 10: 
/*  210: 337 */             return MRServiceProtos.KillTaskAttemptRequestProto.getDefaultInstance();
/*  211:     */           case 11: 
/*  212: 339 */             return MRServiceProtos.FailTaskAttemptRequestProto.getDefaultInstance();
/*  213:     */           case 12: 
/*  214: 341 */             return SecurityProtos.RenewDelegationTokenRequestProto.getDefaultInstance();
/*  215:     */           case 13: 
/*  216: 343 */             return SecurityProtos.CancelDelegationTokenRequestProto.getDefaultInstance();
/*  217:     */           }
/*  218: 345 */           throw new AssertionError("Can't get here.");
/*  219:     */         }
/*  220:     */         
/*  221:     */         public final Message getResponsePrototype(Descriptors.MethodDescriptor method)
/*  222:     */         {
/*  223: 352 */           if (method.getService() != MRClientProtocol.MRClientProtocolService.getDescriptor()) {
/*  224: 353 */             throw new IllegalArgumentException("Service.getResponsePrototype() given method descriptor for wrong service type.");
/*  225:     */           }
/*  226: 357 */           switch (method.getIndex())
/*  227:     */           {
/*  228:     */           case 0: 
/*  229: 359 */             return MRServiceProtos.GetJobReportResponseProto.getDefaultInstance();
/*  230:     */           case 1: 
/*  231: 361 */             return MRServiceProtos.GetTaskReportResponseProto.getDefaultInstance();
/*  232:     */           case 2: 
/*  233: 363 */             return MRServiceProtos.GetTaskAttemptReportResponseProto.getDefaultInstance();
/*  234:     */           case 3: 
/*  235: 365 */             return MRServiceProtos.GetCountersResponseProto.getDefaultInstance();
/*  236:     */           case 4: 
/*  237: 367 */             return MRServiceProtos.GetTaskAttemptCompletionEventsResponseProto.getDefaultInstance();
/*  238:     */           case 5: 
/*  239: 369 */             return MRServiceProtos.GetTaskReportsResponseProto.getDefaultInstance();
/*  240:     */           case 6: 
/*  241: 371 */             return MRServiceProtos.GetDiagnosticsResponseProto.getDefaultInstance();
/*  242:     */           case 7: 
/*  243: 373 */             return SecurityProtos.GetDelegationTokenResponseProto.getDefaultInstance();
/*  244:     */           case 8: 
/*  245: 375 */             return MRServiceProtos.KillJobResponseProto.getDefaultInstance();
/*  246:     */           case 9: 
/*  247: 377 */             return MRServiceProtos.KillTaskResponseProto.getDefaultInstance();
/*  248:     */           case 10: 
/*  249: 379 */             return MRServiceProtos.KillTaskAttemptResponseProto.getDefaultInstance();
/*  250:     */           case 11: 
/*  251: 381 */             return MRServiceProtos.FailTaskAttemptResponseProto.getDefaultInstance();
/*  252:     */           case 12: 
/*  253: 383 */             return SecurityProtos.RenewDelegationTokenResponseProto.getDefaultInstance();
/*  254:     */           case 13: 
/*  255: 385 */             return SecurityProtos.CancelDelegationTokenResponseProto.getDefaultInstance();
/*  256:     */           }
/*  257: 387 */           throw new AssertionError("Can't get here.");
/*  258:     */         }
/*  259:     */       };
/*  260:     */     }
/*  261:     */     
/*  262:     */     public abstract void getJobReport(RpcController paramRpcController, MRServiceProtos.GetJobReportRequestProto paramGetJobReportRequestProto, RpcCallback<MRServiceProtos.GetJobReportResponseProto> paramRpcCallback);
/*  263:     */     
/*  264:     */     public abstract void getTaskReport(RpcController paramRpcController, MRServiceProtos.GetTaskReportRequestProto paramGetTaskReportRequestProto, RpcCallback<MRServiceProtos.GetTaskReportResponseProto> paramRpcCallback);
/*  265:     */     
/*  266:     */     public abstract void getTaskAttemptReport(RpcController paramRpcController, MRServiceProtos.GetTaskAttemptReportRequestProto paramGetTaskAttemptReportRequestProto, RpcCallback<MRServiceProtos.GetTaskAttemptReportResponseProto> paramRpcCallback);
/*  267:     */     
/*  268:     */     public abstract void getCounters(RpcController paramRpcController, MRServiceProtos.GetCountersRequestProto paramGetCountersRequestProto, RpcCallback<MRServiceProtos.GetCountersResponseProto> paramRpcCallback);
/*  269:     */     
/*  270:     */     public abstract void getTaskAttemptCompletionEvents(RpcController paramRpcController, MRServiceProtos.GetTaskAttemptCompletionEventsRequestProto paramGetTaskAttemptCompletionEventsRequestProto, RpcCallback<MRServiceProtos.GetTaskAttemptCompletionEventsResponseProto> paramRpcCallback);
/*  271:     */     
/*  272:     */     public abstract void getTaskReports(RpcController paramRpcController, MRServiceProtos.GetTaskReportsRequestProto paramGetTaskReportsRequestProto, RpcCallback<MRServiceProtos.GetTaskReportsResponseProto> paramRpcCallback);
/*  273:     */     
/*  274:     */     public abstract void getDiagnostics(RpcController paramRpcController, MRServiceProtos.GetDiagnosticsRequestProto paramGetDiagnosticsRequestProto, RpcCallback<MRServiceProtos.GetDiagnosticsResponseProto> paramRpcCallback);
/*  275:     */     
/*  276:     */     public abstract void getDelegationToken(RpcController paramRpcController, SecurityProtos.GetDelegationTokenRequestProto paramGetDelegationTokenRequestProto, RpcCallback<SecurityProtos.GetDelegationTokenResponseProto> paramRpcCallback);
/*  277:     */     
/*  278:     */     public abstract void killJob(RpcController paramRpcController, MRServiceProtos.KillJobRequestProto paramKillJobRequestProto, RpcCallback<MRServiceProtos.KillJobResponseProto> paramRpcCallback);
/*  279:     */     
/*  280:     */     public abstract void killTask(RpcController paramRpcController, MRServiceProtos.KillTaskRequestProto paramKillTaskRequestProto, RpcCallback<MRServiceProtos.KillTaskResponseProto> paramRpcCallback);
/*  281:     */     
/*  282:     */     public abstract void killTaskAttempt(RpcController paramRpcController, MRServiceProtos.KillTaskAttemptRequestProto paramKillTaskAttemptRequestProto, RpcCallback<MRServiceProtos.KillTaskAttemptResponseProto> paramRpcCallback);
/*  283:     */     
/*  284:     */     public abstract void failTaskAttempt(RpcController paramRpcController, MRServiceProtos.FailTaskAttemptRequestProto paramFailTaskAttemptRequestProto, RpcCallback<MRServiceProtos.FailTaskAttemptResponseProto> paramRpcCallback);
/*  285:     */     
/*  286:     */     public abstract void renewDelegationToken(RpcController paramRpcController, SecurityProtos.RenewDelegationTokenRequestProto paramRenewDelegationTokenRequestProto, RpcCallback<SecurityProtos.RenewDelegationTokenResponseProto> paramRpcCallback);
/*  287:     */     
/*  288:     */     public abstract void cancelDelegationToken(RpcController paramRpcController, SecurityProtos.CancelDelegationTokenRequestProto paramCancelDelegationTokenRequestProto, RpcCallback<SecurityProtos.CancelDelegationTokenResponseProto> paramRpcCallback);
/*  289:     */     
/*  290:     */     public static final Descriptors.ServiceDescriptor getDescriptor()
/*  291:     */     {
/*  292: 509 */       return (Descriptors.ServiceDescriptor)MRClientProtocol.getDescriptor().getServices().get(0);
/*  293:     */     }
/*  294:     */     
/*  295:     */     public final Descriptors.ServiceDescriptor getDescriptorForType()
/*  296:     */     {
/*  297: 513 */       return getDescriptor();
/*  298:     */     }
/*  299:     */     
/*  300:     */     public final void callMethod(Descriptors.MethodDescriptor method, RpcController controller, Message request, RpcCallback<Message> done)
/*  301:     */     {
/*  302: 522 */       if (method.getService() != getDescriptor()) {
/*  303: 523 */         throw new IllegalArgumentException("Service.callMethod() given method descriptor for wrong service type.");
/*  304:     */       }
/*  305: 527 */       switch (method.getIndex())
/*  306:     */       {
/*  307:     */       case 0: 
/*  308: 529 */         getJobReport(controller, (MRServiceProtos.GetJobReportRequestProto)request, RpcUtil.specializeCallback(done));
/*  309:     */         
/*  310:     */ 
/*  311: 532 */         return;
/*  312:     */       case 1: 
/*  313: 534 */         getTaskReport(controller, (MRServiceProtos.GetTaskReportRequestProto)request, RpcUtil.specializeCallback(done));
/*  314:     */         
/*  315:     */ 
/*  316: 537 */         return;
/*  317:     */       case 2: 
/*  318: 539 */         getTaskAttemptReport(controller, (MRServiceProtos.GetTaskAttemptReportRequestProto)request, RpcUtil.specializeCallback(done));
/*  319:     */         
/*  320:     */ 
/*  321: 542 */         return;
/*  322:     */       case 3: 
/*  323: 544 */         getCounters(controller, (MRServiceProtos.GetCountersRequestProto)request, RpcUtil.specializeCallback(done));
/*  324:     */         
/*  325:     */ 
/*  326: 547 */         return;
/*  327:     */       case 4: 
/*  328: 549 */         getTaskAttemptCompletionEvents(controller, (MRServiceProtos.GetTaskAttemptCompletionEventsRequestProto)request, RpcUtil.specializeCallback(done));
/*  329:     */         
/*  330:     */ 
/*  331: 552 */         return;
/*  332:     */       case 5: 
/*  333: 554 */         getTaskReports(controller, (MRServiceProtos.GetTaskReportsRequestProto)request, RpcUtil.specializeCallback(done));
/*  334:     */         
/*  335:     */ 
/*  336: 557 */         return;
/*  337:     */       case 6: 
/*  338: 559 */         getDiagnostics(controller, (MRServiceProtos.GetDiagnosticsRequestProto)request, RpcUtil.specializeCallback(done));
/*  339:     */         
/*  340:     */ 
/*  341: 562 */         return;
/*  342:     */       case 7: 
/*  343: 564 */         getDelegationToken(controller, (SecurityProtos.GetDelegationTokenRequestProto)request, RpcUtil.specializeCallback(done));
/*  344:     */         
/*  345:     */ 
/*  346: 567 */         return;
/*  347:     */       case 8: 
/*  348: 569 */         killJob(controller, (MRServiceProtos.KillJobRequestProto)request, RpcUtil.specializeCallback(done));
/*  349:     */         
/*  350:     */ 
/*  351: 572 */         return;
/*  352:     */       case 9: 
/*  353: 574 */         killTask(controller, (MRServiceProtos.KillTaskRequestProto)request, RpcUtil.specializeCallback(done));
/*  354:     */         
/*  355:     */ 
/*  356: 577 */         return;
/*  357:     */       case 10: 
/*  358: 579 */         killTaskAttempt(controller, (MRServiceProtos.KillTaskAttemptRequestProto)request, RpcUtil.specializeCallback(done));
/*  359:     */         
/*  360:     */ 
/*  361: 582 */         return;
/*  362:     */       case 11: 
/*  363: 584 */         failTaskAttempt(controller, (MRServiceProtos.FailTaskAttemptRequestProto)request, RpcUtil.specializeCallback(done));
/*  364:     */         
/*  365:     */ 
/*  366: 587 */         return;
/*  367:     */       case 12: 
/*  368: 589 */         renewDelegationToken(controller, (SecurityProtos.RenewDelegationTokenRequestProto)request, RpcUtil.specializeCallback(done));
/*  369:     */         
/*  370:     */ 
/*  371: 592 */         return;
/*  372:     */       case 13: 
/*  373: 594 */         cancelDelegationToken(controller, (SecurityProtos.CancelDelegationTokenRequestProto)request, RpcUtil.specializeCallback(done));
/*  374:     */         
/*  375:     */ 
/*  376: 597 */         return;
/*  377:     */       }
/*  378: 599 */       throw new AssertionError("Can't get here.");
/*  379:     */     }
/*  380:     */     
/*  381:     */     public final Message getRequestPrototype(Descriptors.MethodDescriptor method)
/*  382:     */     {
/*  383: 606 */       if (method.getService() != getDescriptor()) {
/*  384: 607 */         throw new IllegalArgumentException("Service.getRequestPrototype() given method descriptor for wrong service type.");
/*  385:     */       }
/*  386: 611 */       switch (method.getIndex())
/*  387:     */       {
/*  388:     */       case 0: 
/*  389: 613 */         return MRServiceProtos.GetJobReportRequestProto.getDefaultInstance();
/*  390:     */       case 1: 
/*  391: 615 */         return MRServiceProtos.GetTaskReportRequestProto.getDefaultInstance();
/*  392:     */       case 2: 
/*  393: 617 */         return MRServiceProtos.GetTaskAttemptReportRequestProto.getDefaultInstance();
/*  394:     */       case 3: 
/*  395: 619 */         return MRServiceProtos.GetCountersRequestProto.getDefaultInstance();
/*  396:     */       case 4: 
/*  397: 621 */         return MRServiceProtos.GetTaskAttemptCompletionEventsRequestProto.getDefaultInstance();
/*  398:     */       case 5: 
/*  399: 623 */         return MRServiceProtos.GetTaskReportsRequestProto.getDefaultInstance();
/*  400:     */       case 6: 
/*  401: 625 */         return MRServiceProtos.GetDiagnosticsRequestProto.getDefaultInstance();
/*  402:     */       case 7: 
/*  403: 627 */         return SecurityProtos.GetDelegationTokenRequestProto.getDefaultInstance();
/*  404:     */       case 8: 
/*  405: 629 */         return MRServiceProtos.KillJobRequestProto.getDefaultInstance();
/*  406:     */       case 9: 
/*  407: 631 */         return MRServiceProtos.KillTaskRequestProto.getDefaultInstance();
/*  408:     */       case 10: 
/*  409: 633 */         return MRServiceProtos.KillTaskAttemptRequestProto.getDefaultInstance();
/*  410:     */       case 11: 
/*  411: 635 */         return MRServiceProtos.FailTaskAttemptRequestProto.getDefaultInstance();
/*  412:     */       case 12: 
/*  413: 637 */         return SecurityProtos.RenewDelegationTokenRequestProto.getDefaultInstance();
/*  414:     */       case 13: 
/*  415: 639 */         return SecurityProtos.CancelDelegationTokenRequestProto.getDefaultInstance();
/*  416:     */       }
/*  417: 641 */       throw new AssertionError("Can't get here.");
/*  418:     */     }
/*  419:     */     
/*  420:     */     public final Message getResponsePrototype(Descriptors.MethodDescriptor method)
/*  421:     */     {
/*  422: 648 */       if (method.getService() != getDescriptor()) {
/*  423: 649 */         throw new IllegalArgumentException("Service.getResponsePrototype() given method descriptor for wrong service type.");
/*  424:     */       }
/*  425: 653 */       switch (method.getIndex())
/*  426:     */       {
/*  427:     */       case 0: 
/*  428: 655 */         return MRServiceProtos.GetJobReportResponseProto.getDefaultInstance();
/*  429:     */       case 1: 
/*  430: 657 */         return MRServiceProtos.GetTaskReportResponseProto.getDefaultInstance();
/*  431:     */       case 2: 
/*  432: 659 */         return MRServiceProtos.GetTaskAttemptReportResponseProto.getDefaultInstance();
/*  433:     */       case 3: 
/*  434: 661 */         return MRServiceProtos.GetCountersResponseProto.getDefaultInstance();
/*  435:     */       case 4: 
/*  436: 663 */         return MRServiceProtos.GetTaskAttemptCompletionEventsResponseProto.getDefaultInstance();
/*  437:     */       case 5: 
/*  438: 665 */         return MRServiceProtos.GetTaskReportsResponseProto.getDefaultInstance();
/*  439:     */       case 6: 
/*  440: 667 */         return MRServiceProtos.GetDiagnosticsResponseProto.getDefaultInstance();
/*  441:     */       case 7: 
/*  442: 669 */         return SecurityProtos.GetDelegationTokenResponseProto.getDefaultInstance();
/*  443:     */       case 8: 
/*  444: 671 */         return MRServiceProtos.KillJobResponseProto.getDefaultInstance();
/*  445:     */       case 9: 
/*  446: 673 */         return MRServiceProtos.KillTaskResponseProto.getDefaultInstance();
/*  447:     */       case 10: 
/*  448: 675 */         return MRServiceProtos.KillTaskAttemptResponseProto.getDefaultInstance();
/*  449:     */       case 11: 
/*  450: 677 */         return MRServiceProtos.FailTaskAttemptResponseProto.getDefaultInstance();
/*  451:     */       case 12: 
/*  452: 679 */         return SecurityProtos.RenewDelegationTokenResponseProto.getDefaultInstance();
/*  453:     */       case 13: 
/*  454: 681 */         return SecurityProtos.CancelDelegationTokenResponseProto.getDefaultInstance();
/*  455:     */       }
/*  456: 683 */       throw new AssertionError("Can't get here.");
/*  457:     */     }
/*  458:     */     
/*  459:     */     public static Stub newStub(RpcChannel channel)
/*  460:     */     {
/*  461: 689 */       return new Stub(channel, null);
/*  462:     */     }
/*  463:     */     
/*  464:     */     public static abstract interface Interface
/*  465:     */     {
/*  466:     */       public abstract void getJobReport(RpcController paramRpcController, MRServiceProtos.GetJobReportRequestProto paramGetJobReportRequestProto, RpcCallback<MRServiceProtos.GetJobReportResponseProto> paramRpcCallback);
/*  467:     */       
/*  468:     */       public abstract void getTaskReport(RpcController paramRpcController, MRServiceProtos.GetTaskReportRequestProto paramGetTaskReportRequestProto, RpcCallback<MRServiceProtos.GetTaskReportResponseProto> paramRpcCallback);
/*  469:     */       
/*  470:     */       public abstract void getTaskAttemptReport(RpcController paramRpcController, MRServiceProtos.GetTaskAttemptReportRequestProto paramGetTaskAttemptReportRequestProto, RpcCallback<MRServiceProtos.GetTaskAttemptReportResponseProto> paramRpcCallback);
/*  471:     */       
/*  472:     */       public abstract void getCounters(RpcController paramRpcController, MRServiceProtos.GetCountersRequestProto paramGetCountersRequestProto, RpcCallback<MRServiceProtos.GetCountersResponseProto> paramRpcCallback);
/*  473:     */       
/*  474:     */       public abstract void getTaskAttemptCompletionEvents(RpcController paramRpcController, MRServiceProtos.GetTaskAttemptCompletionEventsRequestProto paramGetTaskAttemptCompletionEventsRequestProto, RpcCallback<MRServiceProtos.GetTaskAttemptCompletionEventsResponseProto> paramRpcCallback);
/*  475:     */       
/*  476:     */       public abstract void getTaskReports(RpcController paramRpcController, MRServiceProtos.GetTaskReportsRequestProto paramGetTaskReportsRequestProto, RpcCallback<MRServiceProtos.GetTaskReportsResponseProto> paramRpcCallback);
/*  477:     */       
/*  478:     */       public abstract void getDiagnostics(RpcController paramRpcController, MRServiceProtos.GetDiagnosticsRequestProto paramGetDiagnosticsRequestProto, RpcCallback<MRServiceProtos.GetDiagnosticsResponseProto> paramRpcCallback);
/*  479:     */       
/*  480:     */       public abstract void getDelegationToken(RpcController paramRpcController, SecurityProtos.GetDelegationTokenRequestProto paramGetDelegationTokenRequestProto, RpcCallback<SecurityProtos.GetDelegationTokenResponseProto> paramRpcCallback);
/*  481:     */       
/*  482:     */       public abstract void killJob(RpcController paramRpcController, MRServiceProtos.KillJobRequestProto paramKillJobRequestProto, RpcCallback<MRServiceProtos.KillJobResponseProto> paramRpcCallback);
/*  483:     */       
/*  484:     */       public abstract void killTask(RpcController paramRpcController, MRServiceProtos.KillTaskRequestProto paramKillTaskRequestProto, RpcCallback<MRServiceProtos.KillTaskResponseProto> paramRpcCallback);
/*  485:     */       
/*  486:     */       public abstract void killTaskAttempt(RpcController paramRpcController, MRServiceProtos.KillTaskAttemptRequestProto paramKillTaskAttemptRequestProto, RpcCallback<MRServiceProtos.KillTaskAttemptResponseProto> paramRpcCallback);
/*  487:     */       
/*  488:     */       public abstract void failTaskAttempt(RpcController paramRpcController, MRServiceProtos.FailTaskAttemptRequestProto paramFailTaskAttemptRequestProto, RpcCallback<MRServiceProtos.FailTaskAttemptResponseProto> paramRpcCallback);
/*  489:     */       
/*  490:     */       public abstract void renewDelegationToken(RpcController paramRpcController, SecurityProtos.RenewDelegationTokenRequestProto paramRenewDelegationTokenRequestProto, RpcCallback<SecurityProtos.RenewDelegationTokenResponseProto> paramRpcCallback);
/*  491:     */       
/*  492:     */       public abstract void cancelDelegationToken(RpcController paramRpcController, SecurityProtos.CancelDelegationTokenRequestProto paramCancelDelegationTokenRequestProto, RpcCallback<SecurityProtos.CancelDelegationTokenResponseProto> paramRpcCallback);
/*  493:     */     }
/*  494:     */     
/*  495:     */     public static final class Stub
/*  496:     */       extends MRClientProtocol.MRClientProtocolService
/*  497:     */       implements MRClientProtocol.MRClientProtocolService.Interface
/*  498:     */     {
/*  499:     */       private final RpcChannel channel;
/*  500:     */       
/*  501:     */       private Stub(RpcChannel channel)
/*  502:     */       {
/*  503: 694 */         this.channel = channel;
/*  504:     */       }
/*  505:     */       
/*  506:     */       public RpcChannel getChannel()
/*  507:     */       {
/*  508: 700 */         return this.channel;
/*  509:     */       }
/*  510:     */       
/*  511:     */       public void getJobReport(RpcController controller, MRServiceProtos.GetJobReportRequestProto request, RpcCallback<MRServiceProtos.GetJobReportResponseProto> done)
/*  512:     */       {
/*  513: 707 */         this.channel.callMethod((Descriptors.MethodDescriptor)getDescriptor().getMethods().get(0), controller, request, MRServiceProtos.GetJobReportResponseProto.getDefaultInstance(), RpcUtil.generalizeCallback(done, MRServiceProtos.GetJobReportResponseProto.class, MRServiceProtos.GetJobReportResponseProto.getDefaultInstance()));
/*  514:     */       }
/*  515:     */       
/*  516:     */       public void getTaskReport(RpcController controller, MRServiceProtos.GetTaskReportRequestProto request, RpcCallback<MRServiceProtos.GetTaskReportResponseProto> done)
/*  517:     */       {
/*  518: 722 */         this.channel.callMethod((Descriptors.MethodDescriptor)getDescriptor().getMethods().get(1), controller, request, MRServiceProtos.GetTaskReportResponseProto.getDefaultInstance(), RpcUtil.generalizeCallback(done, MRServiceProtos.GetTaskReportResponseProto.class, MRServiceProtos.GetTaskReportResponseProto.getDefaultInstance()));
/*  519:     */       }
/*  520:     */       
/*  521:     */       public void getTaskAttemptReport(RpcController controller, MRServiceProtos.GetTaskAttemptReportRequestProto request, RpcCallback<MRServiceProtos.GetTaskAttemptReportResponseProto> done)
/*  522:     */       {
/*  523: 737 */         this.channel.callMethod((Descriptors.MethodDescriptor)getDescriptor().getMethods().get(2), controller, request, MRServiceProtos.GetTaskAttemptReportResponseProto.getDefaultInstance(), RpcUtil.generalizeCallback(done, MRServiceProtos.GetTaskAttemptReportResponseProto.class, MRServiceProtos.GetTaskAttemptReportResponseProto.getDefaultInstance()));
/*  524:     */       }
/*  525:     */       
/*  526:     */       public void getCounters(RpcController controller, MRServiceProtos.GetCountersRequestProto request, RpcCallback<MRServiceProtos.GetCountersResponseProto> done)
/*  527:     */       {
/*  528: 752 */         this.channel.callMethod((Descriptors.MethodDescriptor)getDescriptor().getMethods().get(3), controller, request, MRServiceProtos.GetCountersResponseProto.getDefaultInstance(), RpcUtil.generalizeCallback(done, MRServiceProtos.GetCountersResponseProto.class, MRServiceProtos.GetCountersResponseProto.getDefaultInstance()));
/*  529:     */       }
/*  530:     */       
/*  531:     */       public void getTaskAttemptCompletionEvents(RpcController controller, MRServiceProtos.GetTaskAttemptCompletionEventsRequestProto request, RpcCallback<MRServiceProtos.GetTaskAttemptCompletionEventsResponseProto> done)
/*  532:     */       {
/*  533: 767 */         this.channel.callMethod((Descriptors.MethodDescriptor)getDescriptor().getMethods().get(4), controller, request, MRServiceProtos.GetTaskAttemptCompletionEventsResponseProto.getDefaultInstance(), RpcUtil.generalizeCallback(done, MRServiceProtos.GetTaskAttemptCompletionEventsResponseProto.class, MRServiceProtos.GetTaskAttemptCompletionEventsResponseProto.getDefaultInstance()));
/*  534:     */       }
/*  535:     */       
/*  536:     */       public void getTaskReports(RpcController controller, MRServiceProtos.GetTaskReportsRequestProto request, RpcCallback<MRServiceProtos.GetTaskReportsResponseProto> done)
/*  537:     */       {
/*  538: 782 */         this.channel.callMethod((Descriptors.MethodDescriptor)getDescriptor().getMethods().get(5), controller, request, MRServiceProtos.GetTaskReportsResponseProto.getDefaultInstance(), RpcUtil.generalizeCallback(done, MRServiceProtos.GetTaskReportsResponseProto.class, MRServiceProtos.GetTaskReportsResponseProto.getDefaultInstance()));
/*  539:     */       }
/*  540:     */       
/*  541:     */       public void getDiagnostics(RpcController controller, MRServiceProtos.GetDiagnosticsRequestProto request, RpcCallback<MRServiceProtos.GetDiagnosticsResponseProto> done)
/*  542:     */       {
/*  543: 797 */         this.channel.callMethod((Descriptors.MethodDescriptor)getDescriptor().getMethods().get(6), controller, request, MRServiceProtos.GetDiagnosticsResponseProto.getDefaultInstance(), RpcUtil.generalizeCallback(done, MRServiceProtos.GetDiagnosticsResponseProto.class, MRServiceProtos.GetDiagnosticsResponseProto.getDefaultInstance()));
/*  544:     */       }
/*  545:     */       
/*  546:     */       public void getDelegationToken(RpcController controller, SecurityProtos.GetDelegationTokenRequestProto request, RpcCallback<SecurityProtos.GetDelegationTokenResponseProto> done)
/*  547:     */       {
/*  548: 812 */         this.channel.callMethod((Descriptors.MethodDescriptor)getDescriptor().getMethods().get(7), controller, request, SecurityProtos.GetDelegationTokenResponseProto.getDefaultInstance(), RpcUtil.generalizeCallback(done, SecurityProtos.GetDelegationTokenResponseProto.class, SecurityProtos.GetDelegationTokenResponseProto.getDefaultInstance()));
/*  549:     */       }
/*  550:     */       
/*  551:     */       public void killJob(RpcController controller, MRServiceProtos.KillJobRequestProto request, RpcCallback<MRServiceProtos.KillJobResponseProto> done)
/*  552:     */       {
/*  553: 827 */         this.channel.callMethod((Descriptors.MethodDescriptor)getDescriptor().getMethods().get(8), controller, request, MRServiceProtos.KillJobResponseProto.getDefaultInstance(), RpcUtil.generalizeCallback(done, MRServiceProtos.KillJobResponseProto.class, MRServiceProtos.KillJobResponseProto.getDefaultInstance()));
/*  554:     */       }
/*  555:     */       
/*  556:     */       public void killTask(RpcController controller, MRServiceProtos.KillTaskRequestProto request, RpcCallback<MRServiceProtos.KillTaskResponseProto> done)
/*  557:     */       {
/*  558: 842 */         this.channel.callMethod((Descriptors.MethodDescriptor)getDescriptor().getMethods().get(9), controller, request, MRServiceProtos.KillTaskResponseProto.getDefaultInstance(), RpcUtil.generalizeCallback(done, MRServiceProtos.KillTaskResponseProto.class, MRServiceProtos.KillTaskResponseProto.getDefaultInstance()));
/*  559:     */       }
/*  560:     */       
/*  561:     */       public void killTaskAttempt(RpcController controller, MRServiceProtos.KillTaskAttemptRequestProto request, RpcCallback<MRServiceProtos.KillTaskAttemptResponseProto> done)
/*  562:     */       {
/*  563: 857 */         this.channel.callMethod((Descriptors.MethodDescriptor)getDescriptor().getMethods().get(10), controller, request, MRServiceProtos.KillTaskAttemptResponseProto.getDefaultInstance(), RpcUtil.generalizeCallback(done, MRServiceProtos.KillTaskAttemptResponseProto.class, MRServiceProtos.KillTaskAttemptResponseProto.getDefaultInstance()));
/*  564:     */       }
/*  565:     */       
/*  566:     */       public void failTaskAttempt(RpcController controller, MRServiceProtos.FailTaskAttemptRequestProto request, RpcCallback<MRServiceProtos.FailTaskAttemptResponseProto> done)
/*  567:     */       {
/*  568: 872 */         this.channel.callMethod((Descriptors.MethodDescriptor)getDescriptor().getMethods().get(11), controller, request, MRServiceProtos.FailTaskAttemptResponseProto.getDefaultInstance(), RpcUtil.generalizeCallback(done, MRServiceProtos.FailTaskAttemptResponseProto.class, MRServiceProtos.FailTaskAttemptResponseProto.getDefaultInstance()));
/*  569:     */       }
/*  570:     */       
/*  571:     */       public void renewDelegationToken(RpcController controller, SecurityProtos.RenewDelegationTokenRequestProto request, RpcCallback<SecurityProtos.RenewDelegationTokenResponseProto> done)
/*  572:     */       {
/*  573: 887 */         this.channel.callMethod((Descriptors.MethodDescriptor)getDescriptor().getMethods().get(12), controller, request, SecurityProtos.RenewDelegationTokenResponseProto.getDefaultInstance(), RpcUtil.generalizeCallback(done, SecurityProtos.RenewDelegationTokenResponseProto.class, SecurityProtos.RenewDelegationTokenResponseProto.getDefaultInstance()));
/*  574:     */       }
/*  575:     */       
/*  576:     */       public void cancelDelegationToken(RpcController controller, SecurityProtos.CancelDelegationTokenRequestProto request, RpcCallback<SecurityProtos.CancelDelegationTokenResponseProto> done)
/*  577:     */       {
/*  578: 902 */         this.channel.callMethod((Descriptors.MethodDescriptor)getDescriptor().getMethods().get(13), controller, request, SecurityProtos.CancelDelegationTokenResponseProto.getDefaultInstance(), RpcUtil.generalizeCallback(done, SecurityProtos.CancelDelegationTokenResponseProto.class, SecurityProtos.CancelDelegationTokenResponseProto.getDefaultInstance()));
/*  579:     */       }
/*  580:     */     }
/*  581:     */     
/*  582:     */     public static BlockingInterface newBlockingStub(BlockingRpcChannel channel)
/*  583:     */     {
/*  584: 916 */       return new BlockingStub(channel, null);
/*  585:     */     }
/*  586:     */     
/*  587:     */     public static abstract interface BlockingInterface
/*  588:     */     {
/*  589:     */       public abstract MRServiceProtos.GetJobReportResponseProto getJobReport(RpcController paramRpcController, MRServiceProtos.GetJobReportRequestProto paramGetJobReportRequestProto)
/*  590:     */         throws ServiceException;
/*  591:     */       
/*  592:     */       public abstract MRServiceProtos.GetTaskReportResponseProto getTaskReport(RpcController paramRpcController, MRServiceProtos.GetTaskReportRequestProto paramGetTaskReportRequestProto)
/*  593:     */         throws ServiceException;
/*  594:     */       
/*  595:     */       public abstract MRServiceProtos.GetTaskAttemptReportResponseProto getTaskAttemptReport(RpcController paramRpcController, MRServiceProtos.GetTaskAttemptReportRequestProto paramGetTaskAttemptReportRequestProto)
/*  596:     */         throws ServiceException;
/*  597:     */       
/*  598:     */       public abstract MRServiceProtos.GetCountersResponseProto getCounters(RpcController paramRpcController, MRServiceProtos.GetCountersRequestProto paramGetCountersRequestProto)
/*  599:     */         throws ServiceException;
/*  600:     */       
/*  601:     */       public abstract MRServiceProtos.GetTaskAttemptCompletionEventsResponseProto getTaskAttemptCompletionEvents(RpcController paramRpcController, MRServiceProtos.GetTaskAttemptCompletionEventsRequestProto paramGetTaskAttemptCompletionEventsRequestProto)
/*  602:     */         throws ServiceException;
/*  603:     */       
/*  604:     */       public abstract MRServiceProtos.GetTaskReportsResponseProto getTaskReports(RpcController paramRpcController, MRServiceProtos.GetTaskReportsRequestProto paramGetTaskReportsRequestProto)
/*  605:     */         throws ServiceException;
/*  606:     */       
/*  607:     */       public abstract MRServiceProtos.GetDiagnosticsResponseProto getDiagnostics(RpcController paramRpcController, MRServiceProtos.GetDiagnosticsRequestProto paramGetDiagnosticsRequestProto)
/*  608:     */         throws ServiceException;
/*  609:     */       
/*  610:     */       public abstract SecurityProtos.GetDelegationTokenResponseProto getDelegationToken(RpcController paramRpcController, SecurityProtos.GetDelegationTokenRequestProto paramGetDelegationTokenRequestProto)
/*  611:     */         throws ServiceException;
/*  612:     */       
/*  613:     */       public abstract MRServiceProtos.KillJobResponseProto killJob(RpcController paramRpcController, MRServiceProtos.KillJobRequestProto paramKillJobRequestProto)
/*  614:     */         throws ServiceException;
/*  615:     */       
/*  616:     */       public abstract MRServiceProtos.KillTaskResponseProto killTask(RpcController paramRpcController, MRServiceProtos.KillTaskRequestProto paramKillTaskRequestProto)
/*  617:     */         throws ServiceException;
/*  618:     */       
/*  619:     */       public abstract MRServiceProtos.KillTaskAttemptResponseProto killTaskAttempt(RpcController paramRpcController, MRServiceProtos.KillTaskAttemptRequestProto paramKillTaskAttemptRequestProto)
/*  620:     */         throws ServiceException;
/*  621:     */       
/*  622:     */       public abstract MRServiceProtos.FailTaskAttemptResponseProto failTaskAttempt(RpcController paramRpcController, MRServiceProtos.FailTaskAttemptRequestProto paramFailTaskAttemptRequestProto)
/*  623:     */         throws ServiceException;
/*  624:     */       
/*  625:     */       public abstract SecurityProtos.RenewDelegationTokenResponseProto renewDelegationToken(RpcController paramRpcController, SecurityProtos.RenewDelegationTokenRequestProto paramRenewDelegationTokenRequestProto)
/*  626:     */         throws ServiceException;
/*  627:     */       
/*  628:     */       public abstract SecurityProtos.CancelDelegationTokenResponseProto cancelDelegationToken(RpcController paramRpcController, SecurityProtos.CancelDelegationTokenRequestProto paramCancelDelegationTokenRequestProto)
/*  629:     */         throws ServiceException;
/*  630:     */     }
/*  631:     */     
/*  632:     */     private static final class BlockingStub
/*  633:     */       implements MRClientProtocol.MRClientProtocolService.BlockingInterface
/*  634:     */     {
/*  635:     */       private final BlockingRpcChannel channel;
/*  636:     */       
/*  637:     */       private BlockingStub(BlockingRpcChannel channel)
/*  638:     */       {
/*  639: 993 */         this.channel = channel;
/*  640:     */       }
/*  641:     */       
/*  642:     */       public MRServiceProtos.GetJobReportResponseProto getJobReport(RpcController controller, MRServiceProtos.GetJobReportRequestProto request)
/*  643:     */         throws ServiceException
/*  644:     */       {
/*  645:1002 */         return (MRServiceProtos.GetJobReportResponseProto)this.channel.callBlockingMethod((Descriptors.MethodDescriptor)MRClientProtocol.MRClientProtocolService.getDescriptor().getMethods().get(0), controller, request, MRServiceProtos.GetJobReportResponseProto.getDefaultInstance());
/*  646:     */       }
/*  647:     */       
/*  648:     */       public MRServiceProtos.GetTaskReportResponseProto getTaskReport(RpcController controller, MRServiceProtos.GetTaskReportRequestProto request)
/*  649:     */         throws ServiceException
/*  650:     */       {
/*  651:1014 */         return (MRServiceProtos.GetTaskReportResponseProto)this.channel.callBlockingMethod((Descriptors.MethodDescriptor)MRClientProtocol.MRClientProtocolService.getDescriptor().getMethods().get(1), controller, request, MRServiceProtos.GetTaskReportResponseProto.getDefaultInstance());
/*  652:     */       }
/*  653:     */       
/*  654:     */       public MRServiceProtos.GetTaskAttemptReportResponseProto getTaskAttemptReport(RpcController controller, MRServiceProtos.GetTaskAttemptReportRequestProto request)
/*  655:     */         throws ServiceException
/*  656:     */       {
/*  657:1026 */         return (MRServiceProtos.GetTaskAttemptReportResponseProto)this.channel.callBlockingMethod((Descriptors.MethodDescriptor)MRClientProtocol.MRClientProtocolService.getDescriptor().getMethods().get(2), controller, request, MRServiceProtos.GetTaskAttemptReportResponseProto.getDefaultInstance());
/*  658:     */       }
/*  659:     */       
/*  660:     */       public MRServiceProtos.GetCountersResponseProto getCounters(RpcController controller, MRServiceProtos.GetCountersRequestProto request)
/*  661:     */         throws ServiceException
/*  662:     */       {
/*  663:1038 */         return (MRServiceProtos.GetCountersResponseProto)this.channel.callBlockingMethod((Descriptors.MethodDescriptor)MRClientProtocol.MRClientProtocolService.getDescriptor().getMethods().get(3), controller, request, MRServiceProtos.GetCountersResponseProto.getDefaultInstance());
/*  664:     */       }
/*  665:     */       
/*  666:     */       public MRServiceProtos.GetTaskAttemptCompletionEventsResponseProto getTaskAttemptCompletionEvents(RpcController controller, MRServiceProtos.GetTaskAttemptCompletionEventsRequestProto request)
/*  667:     */         throws ServiceException
/*  668:     */       {
/*  669:1050 */         return (MRServiceProtos.GetTaskAttemptCompletionEventsResponseProto)this.channel.callBlockingMethod((Descriptors.MethodDescriptor)MRClientProtocol.MRClientProtocolService.getDescriptor().getMethods().get(4), controller, request, MRServiceProtos.GetTaskAttemptCompletionEventsResponseProto.getDefaultInstance());
/*  670:     */       }
/*  671:     */       
/*  672:     */       public MRServiceProtos.GetTaskReportsResponseProto getTaskReports(RpcController controller, MRServiceProtos.GetTaskReportsRequestProto request)
/*  673:     */         throws ServiceException
/*  674:     */       {
/*  675:1062 */         return (MRServiceProtos.GetTaskReportsResponseProto)this.channel.callBlockingMethod((Descriptors.MethodDescriptor)MRClientProtocol.MRClientProtocolService.getDescriptor().getMethods().get(5), controller, request, MRServiceProtos.GetTaskReportsResponseProto.getDefaultInstance());
/*  676:     */       }
/*  677:     */       
/*  678:     */       public MRServiceProtos.GetDiagnosticsResponseProto getDiagnostics(RpcController controller, MRServiceProtos.GetDiagnosticsRequestProto request)
/*  679:     */         throws ServiceException
/*  680:     */       {
/*  681:1074 */         return (MRServiceProtos.GetDiagnosticsResponseProto)this.channel.callBlockingMethod((Descriptors.MethodDescriptor)MRClientProtocol.MRClientProtocolService.getDescriptor().getMethods().get(6), controller, request, MRServiceProtos.GetDiagnosticsResponseProto.getDefaultInstance());
/*  682:     */       }
/*  683:     */       
/*  684:     */       public SecurityProtos.GetDelegationTokenResponseProto getDelegationToken(RpcController controller, SecurityProtos.GetDelegationTokenRequestProto request)
/*  685:     */         throws ServiceException
/*  686:     */       {
/*  687:1086 */         return (SecurityProtos.GetDelegationTokenResponseProto)this.channel.callBlockingMethod((Descriptors.MethodDescriptor)MRClientProtocol.MRClientProtocolService.getDescriptor().getMethods().get(7), controller, request, SecurityProtos.GetDelegationTokenResponseProto.getDefaultInstance());
/*  688:     */       }
/*  689:     */       
/*  690:     */       public MRServiceProtos.KillJobResponseProto killJob(RpcController controller, MRServiceProtos.KillJobRequestProto request)
/*  691:     */         throws ServiceException
/*  692:     */       {
/*  693:1098 */         return (MRServiceProtos.KillJobResponseProto)this.channel.callBlockingMethod((Descriptors.MethodDescriptor)MRClientProtocol.MRClientProtocolService.getDescriptor().getMethods().get(8), controller, request, MRServiceProtos.KillJobResponseProto.getDefaultInstance());
/*  694:     */       }
/*  695:     */       
/*  696:     */       public MRServiceProtos.KillTaskResponseProto killTask(RpcController controller, MRServiceProtos.KillTaskRequestProto request)
/*  697:     */         throws ServiceException
/*  698:     */       {
/*  699:1110 */         return (MRServiceProtos.KillTaskResponseProto)this.channel.callBlockingMethod((Descriptors.MethodDescriptor)MRClientProtocol.MRClientProtocolService.getDescriptor().getMethods().get(9), controller, request, MRServiceProtos.KillTaskResponseProto.getDefaultInstance());
/*  700:     */       }
/*  701:     */       
/*  702:     */       public MRServiceProtos.KillTaskAttemptResponseProto killTaskAttempt(RpcController controller, MRServiceProtos.KillTaskAttemptRequestProto request)
/*  703:     */         throws ServiceException
/*  704:     */       {
/*  705:1122 */         return (MRServiceProtos.KillTaskAttemptResponseProto)this.channel.callBlockingMethod((Descriptors.MethodDescriptor)MRClientProtocol.MRClientProtocolService.getDescriptor().getMethods().get(10), controller, request, MRServiceProtos.KillTaskAttemptResponseProto.getDefaultInstance());
/*  706:     */       }
/*  707:     */       
/*  708:     */       public MRServiceProtos.FailTaskAttemptResponseProto failTaskAttempt(RpcController controller, MRServiceProtos.FailTaskAttemptRequestProto request)
/*  709:     */         throws ServiceException
/*  710:     */       {
/*  711:1134 */         return (MRServiceProtos.FailTaskAttemptResponseProto)this.channel.callBlockingMethod((Descriptors.MethodDescriptor)MRClientProtocol.MRClientProtocolService.getDescriptor().getMethods().get(11), controller, request, MRServiceProtos.FailTaskAttemptResponseProto.getDefaultInstance());
/*  712:     */       }
/*  713:     */       
/*  714:     */       public SecurityProtos.RenewDelegationTokenResponseProto renewDelegationToken(RpcController controller, SecurityProtos.RenewDelegationTokenRequestProto request)
/*  715:     */         throws ServiceException
/*  716:     */       {
/*  717:1146 */         return (SecurityProtos.RenewDelegationTokenResponseProto)this.channel.callBlockingMethod((Descriptors.MethodDescriptor)MRClientProtocol.MRClientProtocolService.getDescriptor().getMethods().get(12), controller, request, SecurityProtos.RenewDelegationTokenResponseProto.getDefaultInstance());
/*  718:     */       }
/*  719:     */       
/*  720:     */       public SecurityProtos.CancelDelegationTokenResponseProto cancelDelegationToken(RpcController controller, SecurityProtos.CancelDelegationTokenRequestProto request)
/*  721:     */         throws ServiceException
/*  722:     */       {
/*  723:1158 */         return (SecurityProtos.CancelDelegationTokenResponseProto)this.channel.callBlockingMethod((Descriptors.MethodDescriptor)MRClientProtocol.MRClientProtocolService.getDescriptor().getMethods().get(13), controller, request, SecurityProtos.CancelDelegationTokenResponseProto.getDefaultInstance());
/*  724:     */       }
/*  725:     */     }
/*  726:     */   }
/*  727:     */   
/*  728:     */   public static Descriptors.FileDescriptor getDescriptor()
/*  729:     */   {
/*  730:1173 */     return descriptor;
/*  731:     */   }
/*  732:     */   
/*  733:     */   static
/*  734:     */   {
/*  735:1178 */     String[] descriptorData = { "\n\026MRClientProtocol.proto\022\020hadoop.mapreduce\032\016Security.proto\032\027mr_service_protos.proto2Ü\f\n\027MRClientProtocolService\022g\n\fgetJobReport\022*.hadoop.mapreduce.GetJobReportRequestProto\032+.hadoop.mapreduce.GetJobReportResponseProto\022j\n\rgetTaskReport\022+.hadoop.mapreduce.GetTaskReportRequestProto\032,.hadoop.mapreduce.GetTaskReportResponseProto\022\n\024getTaskAttemptReport\0222.hadoop.mapreduce.GetTaskAttemptReportRequestProto", "\0323.hadoop.mapreduce.GetTaskAttemptReportResponseProto\022d\n\013getCounters\022).hadoop.mapreduce.GetCountersRequestProto\032*.hadoop.mapreduce.GetCountersResponseProto\022\001\n\036getTaskAttemptCompletionEvents\022<.hadoop.mapreduce.GetTaskAttemptCompletionEventsRequestProto\032=.hadoop.mapreduce.GetTaskAttemptCompletionEventsResponseProto\022m\n\016getTaskReports\022,.hadoop.mapreduce.GetTaskReportsRequestProto\032-.hadoop.mapreduce.G", "etTaskReportsResponseProto\022m\n\016getDiagnostics\022,.hadoop.mapreduce.GetDiagnosticsRequestProto\032-.hadoop.mapreduce.GetDiagnosticsResponseProto\022s\n\022getDelegationToken\022-.hadoop.common.GetDelegationTokenRequestProto\032..hadoop.common.GetDelegationTokenResponseProto\022X\n\007killJob\022%.hadoop.mapreduce.KillJobRequestProto\032&.hadoop.mapreduce.KillJobResponseProto\022[\n\bkillTask\022&.hadoop.mapreduce.KillTaskRequestProto\032'.h", "adoop.mapreduce.KillTaskResponseProto\022p\n\017killTaskAttempt\022-.hadoop.mapreduce.KillTaskAttemptRequestProto\032..hadoop.mapreduce.KillTaskAttemptResponseProto\022p\n\017failTaskAttempt\022-.hadoop.mapreduce.FailTaskAttemptRequestProto\032..hadoop.mapreduce.FailTaskAttemptResponseProto\022y\n\024renewDelegationToken\022/.hadoop.common.RenewDelegationTokenRequestProto\0320.hadoop.common.RenewDelegationTokenResponseProto\022|\n\025cancelDe", "legationToken\0220.hadoop.common.CancelDelegationTokenRequestProto\0321.hadoop.common.CancelDelegationTokenResponseProtoB3\n\034org.apache.hadoop.yarn.protoB\020MRClientProtocol\001\001" };
/*  736:     */     
/*  737:     */ 
/*  738:     */ 
/*  739:     */ 
/*  740:     */ 
/*  741:     */ 
/*  742:     */ 
/*  743:     */ 
/*  744:     */ 
/*  745:     */ 
/*  746:     */ 
/*  747:     */ 
/*  748:     */ 
/*  749:     */ 
/*  750:     */ 
/*  751:     */ 
/*  752:     */ 
/*  753:     */ 
/*  754:     */ 
/*  755:     */ 
/*  756:     */ 
/*  757:     */ 
/*  758:     */ 
/*  759:     */ 
/*  760:     */ 
/*  761:     */ 
/*  762:     */ 
/*  763:     */ 
/*  764:     */ 
/*  765:     */ 
/*  766:     */ 
/*  767:     */ 
/*  768:     */ 
/*  769:     */ 
/*  770:     */ 
/*  771:     */ 
/*  772:     */ 
/*  773:     */ 
/*  774:     */ 
/*  775:     */ 
/*  776:     */ 
/*  777:     */ 
/*  778:     */ 
/*  779:     */ 
/*  780:     */ 
/*  781:     */ 
/*  782:1225 */     Descriptors.FileDescriptor.InternalDescriptorAssigner assigner = new Descriptors.FileDescriptor.InternalDescriptorAssigner()
/*  783:     */     {
/*  784:     */       public ExtensionRegistry assignDescriptors(Descriptors.FileDescriptor root)
/*  785:     */       {
/*  786:1229 */         MRClientProtocol.access$202(root);
/*  787:1230 */         return null;
/*  788:     */       }
/*  789:1232 */     };
/*  790:1233 */     Descriptors.FileDescriptor.internalBuildGeneratedFileFrom(descriptorData, new Descriptors.FileDescriptor[] { SecurityProtos.getDescriptor(), MRServiceProtos.getDescriptor() }, assigner);
/*  791:     */   }
/*  792:     */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-common-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.yarn.proto.MRClientProtocol
 * JD-Core Version:    0.7.0.1
 */