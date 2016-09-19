/*   1:    */ package org.apache.hadoop.mapreduce.v2.api.impl.pb.client;
/*   2:    */ 
/*   3:    */ import com.google.protobuf.ServiceException;
/*   4:    */ import java.io.Closeable;
/*   5:    */ import java.io.IOException;
/*   6:    */ import java.lang.reflect.UndeclaredThrowableException;
/*   7:    */ import java.net.InetSocketAddress;
/*   8:    */ import org.apache.hadoop.conf.Configuration;
/*   9:    */ import org.apache.hadoop.ipc.ProtobufRpcEngine;
/*  10:    */ import org.apache.hadoop.ipc.RPC;
/*  11:    */ import org.apache.hadoop.ipc.RemoteException;
/*  12:    */ import org.apache.hadoop.mapreduce.v2.api.MRClientProtocol;
/*  13:    */ import org.apache.hadoop.mapreduce.v2.api.MRClientProtocolPB;
/*  14:    */ import org.apache.hadoop.mapreduce.v2.api.protocolrecords.CancelDelegationTokenRequest;
/*  15:    */ import org.apache.hadoop.mapreduce.v2.api.protocolrecords.CancelDelegationTokenResponse;
/*  16:    */ import org.apache.hadoop.mapreduce.v2.api.protocolrecords.FailTaskAttemptRequest;
/*  17:    */ import org.apache.hadoop.mapreduce.v2.api.protocolrecords.FailTaskAttemptResponse;
/*  18:    */ import org.apache.hadoop.mapreduce.v2.api.protocolrecords.GetCountersRequest;
/*  19:    */ import org.apache.hadoop.mapreduce.v2.api.protocolrecords.GetCountersResponse;
/*  20:    */ import org.apache.hadoop.mapreduce.v2.api.protocolrecords.GetDelegationTokenRequest;
/*  21:    */ import org.apache.hadoop.mapreduce.v2.api.protocolrecords.GetDelegationTokenResponse;
/*  22:    */ import org.apache.hadoop.mapreduce.v2.api.protocolrecords.GetDiagnosticsRequest;
/*  23:    */ import org.apache.hadoop.mapreduce.v2.api.protocolrecords.GetDiagnosticsResponse;
/*  24:    */ import org.apache.hadoop.mapreduce.v2.api.protocolrecords.GetJobReportRequest;
/*  25:    */ import org.apache.hadoop.mapreduce.v2.api.protocolrecords.GetJobReportResponse;
/*  26:    */ import org.apache.hadoop.mapreduce.v2.api.protocolrecords.GetTaskAttemptCompletionEventsRequest;
/*  27:    */ import org.apache.hadoop.mapreduce.v2.api.protocolrecords.GetTaskAttemptCompletionEventsResponse;
/*  28:    */ import org.apache.hadoop.mapreduce.v2.api.protocolrecords.GetTaskAttemptReportRequest;
/*  29:    */ import org.apache.hadoop.mapreduce.v2.api.protocolrecords.GetTaskAttemptReportResponse;
/*  30:    */ import org.apache.hadoop.mapreduce.v2.api.protocolrecords.GetTaskReportRequest;
/*  31:    */ import org.apache.hadoop.mapreduce.v2.api.protocolrecords.GetTaskReportResponse;
/*  32:    */ import org.apache.hadoop.mapreduce.v2.api.protocolrecords.GetTaskReportsRequest;
/*  33:    */ import org.apache.hadoop.mapreduce.v2.api.protocolrecords.GetTaskReportsResponse;
/*  34:    */ import org.apache.hadoop.mapreduce.v2.api.protocolrecords.KillJobRequest;
/*  35:    */ import org.apache.hadoop.mapreduce.v2.api.protocolrecords.KillJobResponse;
/*  36:    */ import org.apache.hadoop.mapreduce.v2.api.protocolrecords.KillTaskAttemptRequest;
/*  37:    */ import org.apache.hadoop.mapreduce.v2.api.protocolrecords.KillTaskAttemptResponse;
/*  38:    */ import org.apache.hadoop.mapreduce.v2.api.protocolrecords.KillTaskRequest;
/*  39:    */ import org.apache.hadoop.mapreduce.v2.api.protocolrecords.KillTaskResponse;
/*  40:    */ import org.apache.hadoop.mapreduce.v2.api.protocolrecords.RenewDelegationTokenRequest;
/*  41:    */ import org.apache.hadoop.mapreduce.v2.api.protocolrecords.RenewDelegationTokenResponse;
/*  42:    */ import org.apache.hadoop.mapreduce.v2.api.protocolrecords.impl.pb.CancelDelegationTokenRequestPBImpl;
/*  43:    */ import org.apache.hadoop.mapreduce.v2.api.protocolrecords.impl.pb.CancelDelegationTokenResponsePBImpl;
/*  44:    */ import org.apache.hadoop.mapreduce.v2.api.protocolrecords.impl.pb.FailTaskAttemptRequestPBImpl;
/*  45:    */ import org.apache.hadoop.mapreduce.v2.api.protocolrecords.impl.pb.FailTaskAttemptResponsePBImpl;
/*  46:    */ import org.apache.hadoop.mapreduce.v2.api.protocolrecords.impl.pb.GetCountersRequestPBImpl;
/*  47:    */ import org.apache.hadoop.mapreduce.v2.api.protocolrecords.impl.pb.GetCountersResponsePBImpl;
/*  48:    */ import org.apache.hadoop.mapreduce.v2.api.protocolrecords.impl.pb.GetDelegationTokenRequestPBImpl;
/*  49:    */ import org.apache.hadoop.mapreduce.v2.api.protocolrecords.impl.pb.GetDelegationTokenResponsePBImpl;
/*  50:    */ import org.apache.hadoop.mapreduce.v2.api.protocolrecords.impl.pb.GetDiagnosticsRequestPBImpl;
/*  51:    */ import org.apache.hadoop.mapreduce.v2.api.protocolrecords.impl.pb.GetDiagnosticsResponsePBImpl;
/*  52:    */ import org.apache.hadoop.mapreduce.v2.api.protocolrecords.impl.pb.GetJobReportRequestPBImpl;
/*  53:    */ import org.apache.hadoop.mapreduce.v2.api.protocolrecords.impl.pb.GetJobReportResponsePBImpl;
/*  54:    */ import org.apache.hadoop.mapreduce.v2.api.protocolrecords.impl.pb.GetTaskAttemptCompletionEventsRequestPBImpl;
/*  55:    */ import org.apache.hadoop.mapreduce.v2.api.protocolrecords.impl.pb.GetTaskAttemptCompletionEventsResponsePBImpl;
/*  56:    */ import org.apache.hadoop.mapreduce.v2.api.protocolrecords.impl.pb.GetTaskAttemptReportRequestPBImpl;
/*  57:    */ import org.apache.hadoop.mapreduce.v2.api.protocolrecords.impl.pb.GetTaskAttemptReportResponsePBImpl;
/*  58:    */ import org.apache.hadoop.mapreduce.v2.api.protocolrecords.impl.pb.GetTaskReportRequestPBImpl;
/*  59:    */ import org.apache.hadoop.mapreduce.v2.api.protocolrecords.impl.pb.GetTaskReportResponsePBImpl;
/*  60:    */ import org.apache.hadoop.mapreduce.v2.api.protocolrecords.impl.pb.GetTaskReportsRequestPBImpl;
/*  61:    */ import org.apache.hadoop.mapreduce.v2.api.protocolrecords.impl.pb.GetTaskReportsResponsePBImpl;
/*  62:    */ import org.apache.hadoop.mapreduce.v2.api.protocolrecords.impl.pb.KillJobRequestPBImpl;
/*  63:    */ import org.apache.hadoop.mapreduce.v2.api.protocolrecords.impl.pb.KillJobResponsePBImpl;
/*  64:    */ import org.apache.hadoop.mapreduce.v2.api.protocolrecords.impl.pb.KillTaskAttemptRequestPBImpl;
/*  65:    */ import org.apache.hadoop.mapreduce.v2.api.protocolrecords.impl.pb.KillTaskAttemptResponsePBImpl;
/*  66:    */ import org.apache.hadoop.mapreduce.v2.api.protocolrecords.impl.pb.KillTaskRequestPBImpl;
/*  67:    */ import org.apache.hadoop.mapreduce.v2.api.protocolrecords.impl.pb.KillTaskResponsePBImpl;
/*  68:    */ import org.apache.hadoop.mapreduce.v2.api.protocolrecords.impl.pb.RenewDelegationTokenRequestPBImpl;
/*  69:    */ import org.apache.hadoop.mapreduce.v2.api.protocolrecords.impl.pb.RenewDelegationTokenResponsePBImpl;
/*  70:    */ import org.apache.hadoop.mapreduce.v2.proto.MRServiceProtos.FailTaskAttemptRequestProto;
/*  71:    */ import org.apache.hadoop.mapreduce.v2.proto.MRServiceProtos.GetCountersRequestProto;
/*  72:    */ import org.apache.hadoop.mapreduce.v2.proto.MRServiceProtos.GetDiagnosticsRequestProto;
/*  73:    */ import org.apache.hadoop.mapreduce.v2.proto.MRServiceProtos.GetJobReportRequestProto;
/*  74:    */ import org.apache.hadoop.mapreduce.v2.proto.MRServiceProtos.GetTaskAttemptCompletionEventsRequestProto;
/*  75:    */ import org.apache.hadoop.mapreduce.v2.proto.MRServiceProtos.GetTaskAttemptReportRequestProto;
/*  76:    */ import org.apache.hadoop.mapreduce.v2.proto.MRServiceProtos.GetTaskReportRequestProto;
/*  77:    */ import org.apache.hadoop.mapreduce.v2.proto.MRServiceProtos.GetTaskReportsRequestProto;
/*  78:    */ import org.apache.hadoop.mapreduce.v2.proto.MRServiceProtos.KillJobRequestProto;
/*  79:    */ import org.apache.hadoop.mapreduce.v2.proto.MRServiceProtos.KillTaskAttemptRequestProto;
/*  80:    */ import org.apache.hadoop.mapreduce.v2.proto.MRServiceProtos.KillTaskRequestProto;
/*  81:    */ import org.apache.hadoop.security.proto.SecurityProtos.CancelDelegationTokenRequestProto;
/*  82:    */ import org.apache.hadoop.security.proto.SecurityProtos.GetDelegationTokenRequestProto;
/*  83:    */ import org.apache.hadoop.security.proto.SecurityProtos.RenewDelegationTokenRequestProto;
/*  84:    */ 
/*  85:    */ public class MRClientProtocolPBClientImpl
/*  86:    */   implements MRClientProtocol, Closeable
/*  87:    */ {
/*  88:    */   protected MRClientProtocolPB proxy;
/*  89:    */   
/*  90:    */   public MRClientProtocolPBClientImpl() {}
/*  91:    */   
/*  92:    */   public MRClientProtocolPBClientImpl(long clientVersion, InetSocketAddress addr, Configuration conf)
/*  93:    */     throws IOException
/*  94:    */   {
/*  95:112 */     RPC.setProtocolEngine(conf, MRClientProtocolPB.class, ProtobufRpcEngine.class);
/*  96:113 */     this.proxy = ((MRClientProtocolPB)RPC.getProxy(MRClientProtocolPB.class, clientVersion, addr, conf));
/*  97:    */   }
/*  98:    */   
/*  99:    */   public InetSocketAddress getConnectAddress()
/* 100:    */   {
/* 101:118 */     return RPC.getServerAddress(this.proxy);
/* 102:    */   }
/* 103:    */   
/* 104:    */   public void close()
/* 105:    */   {
/* 106:123 */     if (this.proxy != null) {
/* 107:124 */       RPC.stopProxy(this.proxy);
/* 108:    */     }
/* 109:    */   }
/* 110:    */   
/* 111:    */   public GetJobReportResponse getJobReport(GetJobReportRequest request)
/* 112:    */     throws IOException
/* 113:    */   {
/* 114:131 */     MRServiceProtos.GetJobReportRequestProto requestProto = ((GetJobReportRequestPBImpl)request).getProto();
/* 115:    */     try
/* 116:    */     {
/* 117:133 */       return new GetJobReportResponsePBImpl(this.proxy.getJobReport(null, requestProto));
/* 118:    */     }
/* 119:    */     catch (ServiceException e)
/* 120:    */     {
/* 121:135 */       throw unwrapAndThrowException(e);
/* 122:    */     }
/* 123:    */   }
/* 124:    */   
/* 125:    */   public GetTaskReportResponse getTaskReport(GetTaskReportRequest request)
/* 126:    */     throws IOException
/* 127:    */   {
/* 128:142 */     MRServiceProtos.GetTaskReportRequestProto requestProto = ((GetTaskReportRequestPBImpl)request).getProto();
/* 129:    */     try
/* 130:    */     {
/* 131:144 */       return new GetTaskReportResponsePBImpl(this.proxy.getTaskReport(null, requestProto));
/* 132:    */     }
/* 133:    */     catch (ServiceException e)
/* 134:    */     {
/* 135:146 */       throw unwrapAndThrowException(e);
/* 136:    */     }
/* 137:    */   }
/* 138:    */   
/* 139:    */   public GetTaskAttemptReportResponse getTaskAttemptReport(GetTaskAttemptReportRequest request)
/* 140:    */     throws IOException
/* 141:    */   {
/* 142:153 */     MRServiceProtos.GetTaskAttemptReportRequestProto requestProto = ((GetTaskAttemptReportRequestPBImpl)request).getProto();
/* 143:    */     try
/* 144:    */     {
/* 145:155 */       return new GetTaskAttemptReportResponsePBImpl(this.proxy.getTaskAttemptReport(null, requestProto));
/* 146:    */     }
/* 147:    */     catch (ServiceException e)
/* 148:    */     {
/* 149:157 */       throw unwrapAndThrowException(e);
/* 150:    */     }
/* 151:    */   }
/* 152:    */   
/* 153:    */   public GetCountersResponse getCounters(GetCountersRequest request)
/* 154:    */     throws IOException
/* 155:    */   {
/* 156:164 */     MRServiceProtos.GetCountersRequestProto requestProto = ((GetCountersRequestPBImpl)request).getProto();
/* 157:    */     try
/* 158:    */     {
/* 159:166 */       return new GetCountersResponsePBImpl(this.proxy.getCounters(null, requestProto));
/* 160:    */     }
/* 161:    */     catch (ServiceException e)
/* 162:    */     {
/* 163:168 */       throw unwrapAndThrowException(e);
/* 164:    */     }
/* 165:    */   }
/* 166:    */   
/* 167:    */   public GetTaskAttemptCompletionEventsResponse getTaskAttemptCompletionEvents(GetTaskAttemptCompletionEventsRequest request)
/* 168:    */     throws IOException
/* 169:    */   {
/* 170:175 */     MRServiceProtos.GetTaskAttemptCompletionEventsRequestProto requestProto = ((GetTaskAttemptCompletionEventsRequestPBImpl)request).getProto();
/* 171:    */     try
/* 172:    */     {
/* 173:177 */       return new GetTaskAttemptCompletionEventsResponsePBImpl(this.proxy.getTaskAttemptCompletionEvents(null, requestProto));
/* 174:    */     }
/* 175:    */     catch (ServiceException e)
/* 176:    */     {
/* 177:179 */       throw unwrapAndThrowException(e);
/* 178:    */     }
/* 179:    */   }
/* 180:    */   
/* 181:    */   public GetTaskReportsResponse getTaskReports(GetTaskReportsRequest request)
/* 182:    */     throws IOException
/* 183:    */   {
/* 184:186 */     MRServiceProtos.GetTaskReportsRequestProto requestProto = ((GetTaskReportsRequestPBImpl)request).getProto();
/* 185:    */     try
/* 186:    */     {
/* 187:188 */       return new GetTaskReportsResponsePBImpl(this.proxy.getTaskReports(null, requestProto));
/* 188:    */     }
/* 189:    */     catch (ServiceException e)
/* 190:    */     {
/* 191:190 */       throw unwrapAndThrowException(e);
/* 192:    */     }
/* 193:    */   }
/* 194:    */   
/* 195:    */   public GetDiagnosticsResponse getDiagnostics(GetDiagnosticsRequest request)
/* 196:    */     throws IOException
/* 197:    */   {
/* 198:197 */     MRServiceProtos.GetDiagnosticsRequestProto requestProto = ((GetDiagnosticsRequestPBImpl)request).getProto();
/* 199:    */     try
/* 200:    */     {
/* 201:199 */       return new GetDiagnosticsResponsePBImpl(this.proxy.getDiagnostics(null, requestProto));
/* 202:    */     }
/* 203:    */     catch (ServiceException e)
/* 204:    */     {
/* 205:201 */       throw unwrapAndThrowException(e);
/* 206:    */     }
/* 207:    */   }
/* 208:    */   
/* 209:    */   public GetDelegationTokenResponse getDelegationToken(GetDelegationTokenRequest request)
/* 210:    */     throws IOException
/* 211:    */   {
/* 212:208 */     SecurityProtos.GetDelegationTokenRequestProto requestProto = ((GetDelegationTokenRequestPBImpl)request).getProto();
/* 213:    */     try
/* 214:    */     {
/* 215:211 */       return new GetDelegationTokenResponsePBImpl(this.proxy.getDelegationToken(null, requestProto));
/* 216:    */     }
/* 217:    */     catch (ServiceException e)
/* 218:    */     {
/* 219:214 */       throw unwrapAndThrowException(e);
/* 220:    */     }
/* 221:    */   }
/* 222:    */   
/* 223:    */   public KillJobResponse killJob(KillJobRequest request)
/* 224:    */     throws IOException
/* 225:    */   {
/* 226:221 */     MRServiceProtos.KillJobRequestProto requestProto = ((KillJobRequestPBImpl)request).getProto();
/* 227:    */     try
/* 228:    */     {
/* 229:223 */       return new KillJobResponsePBImpl(this.proxy.killJob(null, requestProto));
/* 230:    */     }
/* 231:    */     catch (ServiceException e)
/* 232:    */     {
/* 233:225 */       throw unwrapAndThrowException(e);
/* 234:    */     }
/* 235:    */   }
/* 236:    */   
/* 237:    */   public KillTaskResponse killTask(KillTaskRequest request)
/* 238:    */     throws IOException
/* 239:    */   {
/* 240:232 */     MRServiceProtos.KillTaskRequestProto requestProto = ((KillTaskRequestPBImpl)request).getProto();
/* 241:    */     try
/* 242:    */     {
/* 243:234 */       return new KillTaskResponsePBImpl(this.proxy.killTask(null, requestProto));
/* 244:    */     }
/* 245:    */     catch (ServiceException e)
/* 246:    */     {
/* 247:236 */       throw unwrapAndThrowException(e);
/* 248:    */     }
/* 249:    */   }
/* 250:    */   
/* 251:    */   public KillTaskAttemptResponse killTaskAttempt(KillTaskAttemptRequest request)
/* 252:    */     throws IOException
/* 253:    */   {
/* 254:243 */     MRServiceProtos.KillTaskAttemptRequestProto requestProto = ((KillTaskAttemptRequestPBImpl)request).getProto();
/* 255:    */     try
/* 256:    */     {
/* 257:245 */       return new KillTaskAttemptResponsePBImpl(this.proxy.killTaskAttempt(null, requestProto));
/* 258:    */     }
/* 259:    */     catch (ServiceException e)
/* 260:    */     {
/* 261:247 */       throw unwrapAndThrowException(e);
/* 262:    */     }
/* 263:    */   }
/* 264:    */   
/* 265:    */   public FailTaskAttemptResponse failTaskAttempt(FailTaskAttemptRequest request)
/* 266:    */     throws IOException
/* 267:    */   {
/* 268:254 */     MRServiceProtos.FailTaskAttemptRequestProto requestProto = ((FailTaskAttemptRequestPBImpl)request).getProto();
/* 269:    */     try
/* 270:    */     {
/* 271:256 */       return new FailTaskAttemptResponsePBImpl(this.proxy.failTaskAttempt(null, requestProto));
/* 272:    */     }
/* 273:    */     catch (ServiceException e)
/* 274:    */     {
/* 275:258 */       throw unwrapAndThrowException(e);
/* 276:    */     }
/* 277:    */   }
/* 278:    */   
/* 279:    */   public RenewDelegationTokenResponse renewDelegationToken(RenewDelegationTokenRequest request)
/* 280:    */     throws IOException
/* 281:    */   {
/* 282:265 */     SecurityProtos.RenewDelegationTokenRequestProto requestProto = ((RenewDelegationTokenRequestPBImpl)request).getProto();
/* 283:    */     try
/* 284:    */     {
/* 285:268 */       return new RenewDelegationTokenResponsePBImpl(this.proxy.renewDelegationToken(null, requestProto));
/* 286:    */     }
/* 287:    */     catch (ServiceException e)
/* 288:    */     {
/* 289:271 */       throw unwrapAndThrowException(e);
/* 290:    */     }
/* 291:    */   }
/* 292:    */   
/* 293:    */   public CancelDelegationTokenResponse cancelDelegationToken(CancelDelegationTokenRequest request)
/* 294:    */     throws IOException
/* 295:    */   {
/* 296:278 */     SecurityProtos.CancelDelegationTokenRequestProto requestProto = ((CancelDelegationTokenRequestPBImpl)request).getProto();
/* 297:    */     try
/* 298:    */     {
/* 299:281 */       return new CancelDelegationTokenResponsePBImpl(this.proxy.cancelDelegationToken(null, requestProto));
/* 300:    */     }
/* 301:    */     catch (ServiceException e)
/* 302:    */     {
/* 303:285 */       throw unwrapAndThrowException(e);
/* 304:    */     }
/* 305:    */   }
/* 306:    */   
/* 307:    */   private IOException unwrapAndThrowException(ServiceException se)
/* 308:    */   {
/* 309:290 */     if ((se.getCause() instanceof RemoteException)) {
/* 310:291 */       return ((RemoteException)se.getCause()).unwrapRemoteException();
/* 311:    */     }
/* 312:292 */     if ((se.getCause() instanceof IOException)) {
/* 313:293 */       return (IOException)se.getCause();
/* 314:    */     }
/* 315:295 */     throw new UndeclaredThrowableException(se.getCause());
/* 316:    */   }
/* 317:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-common-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.v2.api.impl.pb.client.MRClientProtocolPBClientImpl
 * JD-Core Version:    0.7.0.1
 */