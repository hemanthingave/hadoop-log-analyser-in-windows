/*   1:    */ package org.apache.hadoop.mapreduce.v2.api.impl.pb.service;
/*   2:    */ 
/*   3:    */ import com.google.protobuf.RpcController;
/*   4:    */ import com.google.protobuf.ServiceException;
/*   5:    */ import java.io.IOException;
/*   6:    */ import org.apache.hadoop.mapreduce.v2.api.MRClientProtocol;
/*   7:    */ import org.apache.hadoop.mapreduce.v2.api.MRClientProtocolPB;
/*   8:    */ import org.apache.hadoop.mapreduce.v2.api.protocolrecords.CancelDelegationTokenResponse;
/*   9:    */ import org.apache.hadoop.mapreduce.v2.api.protocolrecords.FailTaskAttemptRequest;
/*  10:    */ import org.apache.hadoop.mapreduce.v2.api.protocolrecords.FailTaskAttemptResponse;
/*  11:    */ import org.apache.hadoop.mapreduce.v2.api.protocolrecords.GetCountersRequest;
/*  12:    */ import org.apache.hadoop.mapreduce.v2.api.protocolrecords.GetCountersResponse;
/*  13:    */ import org.apache.hadoop.mapreduce.v2.api.protocolrecords.GetDelegationTokenRequest;
/*  14:    */ import org.apache.hadoop.mapreduce.v2.api.protocolrecords.GetDelegationTokenResponse;
/*  15:    */ import org.apache.hadoop.mapreduce.v2.api.protocolrecords.GetDiagnosticsRequest;
/*  16:    */ import org.apache.hadoop.mapreduce.v2.api.protocolrecords.GetDiagnosticsResponse;
/*  17:    */ import org.apache.hadoop.mapreduce.v2.api.protocolrecords.GetJobReportResponse;
/*  18:    */ import org.apache.hadoop.mapreduce.v2.api.protocolrecords.GetTaskAttemptCompletionEventsRequest;
/*  19:    */ import org.apache.hadoop.mapreduce.v2.api.protocolrecords.GetTaskAttemptCompletionEventsResponse;
/*  20:    */ import org.apache.hadoop.mapreduce.v2.api.protocolrecords.GetTaskAttemptReportRequest;
/*  21:    */ import org.apache.hadoop.mapreduce.v2.api.protocolrecords.GetTaskAttemptReportResponse;
/*  22:    */ import org.apache.hadoop.mapreduce.v2.api.protocolrecords.GetTaskReportRequest;
/*  23:    */ import org.apache.hadoop.mapreduce.v2.api.protocolrecords.GetTaskReportResponse;
/*  24:    */ import org.apache.hadoop.mapreduce.v2.api.protocolrecords.GetTaskReportsRequest;
/*  25:    */ import org.apache.hadoop.mapreduce.v2.api.protocolrecords.GetTaskReportsResponse;
/*  26:    */ import org.apache.hadoop.mapreduce.v2.api.protocolrecords.KillJobRequest;
/*  27:    */ import org.apache.hadoop.mapreduce.v2.api.protocolrecords.KillJobResponse;
/*  28:    */ import org.apache.hadoop.mapreduce.v2.api.protocolrecords.KillTaskAttemptRequest;
/*  29:    */ import org.apache.hadoop.mapreduce.v2.api.protocolrecords.KillTaskAttemptResponse;
/*  30:    */ import org.apache.hadoop.mapreduce.v2.api.protocolrecords.KillTaskRequest;
/*  31:    */ import org.apache.hadoop.mapreduce.v2.api.protocolrecords.KillTaskResponse;
/*  32:    */ import org.apache.hadoop.mapreduce.v2.api.protocolrecords.RenewDelegationTokenResponse;
/*  33:    */ import org.apache.hadoop.mapreduce.v2.api.protocolrecords.impl.pb.CancelDelegationTokenRequestPBImpl;
/*  34:    */ import org.apache.hadoop.mapreduce.v2.api.protocolrecords.impl.pb.CancelDelegationTokenResponsePBImpl;
/*  35:    */ import org.apache.hadoop.mapreduce.v2.api.protocolrecords.impl.pb.FailTaskAttemptRequestPBImpl;
/*  36:    */ import org.apache.hadoop.mapreduce.v2.api.protocolrecords.impl.pb.FailTaskAttemptResponsePBImpl;
/*  37:    */ import org.apache.hadoop.mapreduce.v2.api.protocolrecords.impl.pb.GetCountersRequestPBImpl;
/*  38:    */ import org.apache.hadoop.mapreduce.v2.api.protocolrecords.impl.pb.GetCountersResponsePBImpl;
/*  39:    */ import org.apache.hadoop.mapreduce.v2.api.protocolrecords.impl.pb.GetDelegationTokenRequestPBImpl;
/*  40:    */ import org.apache.hadoop.mapreduce.v2.api.protocolrecords.impl.pb.GetDelegationTokenResponsePBImpl;
/*  41:    */ import org.apache.hadoop.mapreduce.v2.api.protocolrecords.impl.pb.GetDiagnosticsRequestPBImpl;
/*  42:    */ import org.apache.hadoop.mapreduce.v2.api.protocolrecords.impl.pb.GetDiagnosticsResponsePBImpl;
/*  43:    */ import org.apache.hadoop.mapreduce.v2.api.protocolrecords.impl.pb.GetJobReportRequestPBImpl;
/*  44:    */ import org.apache.hadoop.mapreduce.v2.api.protocolrecords.impl.pb.GetJobReportResponsePBImpl;
/*  45:    */ import org.apache.hadoop.mapreduce.v2.api.protocolrecords.impl.pb.GetTaskAttemptCompletionEventsRequestPBImpl;
/*  46:    */ import org.apache.hadoop.mapreduce.v2.api.protocolrecords.impl.pb.GetTaskAttemptCompletionEventsResponsePBImpl;
/*  47:    */ import org.apache.hadoop.mapreduce.v2.api.protocolrecords.impl.pb.GetTaskAttemptReportRequestPBImpl;
/*  48:    */ import org.apache.hadoop.mapreduce.v2.api.protocolrecords.impl.pb.GetTaskAttemptReportResponsePBImpl;
/*  49:    */ import org.apache.hadoop.mapreduce.v2.api.protocolrecords.impl.pb.GetTaskReportRequestPBImpl;
/*  50:    */ import org.apache.hadoop.mapreduce.v2.api.protocolrecords.impl.pb.GetTaskReportResponsePBImpl;
/*  51:    */ import org.apache.hadoop.mapreduce.v2.api.protocolrecords.impl.pb.GetTaskReportsRequestPBImpl;
/*  52:    */ import org.apache.hadoop.mapreduce.v2.api.protocolrecords.impl.pb.GetTaskReportsResponsePBImpl;
/*  53:    */ import org.apache.hadoop.mapreduce.v2.api.protocolrecords.impl.pb.KillJobRequestPBImpl;
/*  54:    */ import org.apache.hadoop.mapreduce.v2.api.protocolrecords.impl.pb.KillJobResponsePBImpl;
/*  55:    */ import org.apache.hadoop.mapreduce.v2.api.protocolrecords.impl.pb.KillTaskAttemptRequestPBImpl;
/*  56:    */ import org.apache.hadoop.mapreduce.v2.api.protocolrecords.impl.pb.KillTaskAttemptResponsePBImpl;
/*  57:    */ import org.apache.hadoop.mapreduce.v2.api.protocolrecords.impl.pb.KillTaskRequestPBImpl;
/*  58:    */ import org.apache.hadoop.mapreduce.v2.api.protocolrecords.impl.pb.KillTaskResponsePBImpl;
/*  59:    */ import org.apache.hadoop.mapreduce.v2.api.protocolrecords.impl.pb.RenewDelegationTokenRequestPBImpl;
/*  60:    */ import org.apache.hadoop.mapreduce.v2.api.protocolrecords.impl.pb.RenewDelegationTokenResponsePBImpl;
/*  61:    */ import org.apache.hadoop.mapreduce.v2.proto.MRServiceProtos.FailTaskAttemptRequestProto;
/*  62:    */ import org.apache.hadoop.mapreduce.v2.proto.MRServiceProtos.FailTaskAttemptResponseProto;
/*  63:    */ import org.apache.hadoop.mapreduce.v2.proto.MRServiceProtos.GetCountersRequestProto;
/*  64:    */ import org.apache.hadoop.mapreduce.v2.proto.MRServiceProtos.GetCountersResponseProto;
/*  65:    */ import org.apache.hadoop.mapreduce.v2.proto.MRServiceProtos.GetDiagnosticsRequestProto;
/*  66:    */ import org.apache.hadoop.mapreduce.v2.proto.MRServiceProtos.GetDiagnosticsResponseProto;
/*  67:    */ import org.apache.hadoop.mapreduce.v2.proto.MRServiceProtos.GetJobReportRequestProto;
/*  68:    */ import org.apache.hadoop.mapreduce.v2.proto.MRServiceProtos.GetJobReportResponseProto;
/*  69:    */ import org.apache.hadoop.mapreduce.v2.proto.MRServiceProtos.GetTaskAttemptCompletionEventsRequestProto;
/*  70:    */ import org.apache.hadoop.mapreduce.v2.proto.MRServiceProtos.GetTaskAttemptCompletionEventsResponseProto;
/*  71:    */ import org.apache.hadoop.mapreduce.v2.proto.MRServiceProtos.GetTaskAttemptReportRequestProto;
/*  72:    */ import org.apache.hadoop.mapreduce.v2.proto.MRServiceProtos.GetTaskAttemptReportResponseProto;
/*  73:    */ import org.apache.hadoop.mapreduce.v2.proto.MRServiceProtos.GetTaskReportRequestProto;
/*  74:    */ import org.apache.hadoop.mapreduce.v2.proto.MRServiceProtos.GetTaskReportResponseProto;
/*  75:    */ import org.apache.hadoop.mapreduce.v2.proto.MRServiceProtos.GetTaskReportsRequestProto;
/*  76:    */ import org.apache.hadoop.mapreduce.v2.proto.MRServiceProtos.GetTaskReportsResponseProto;
/*  77:    */ import org.apache.hadoop.mapreduce.v2.proto.MRServiceProtos.KillJobRequestProto;
/*  78:    */ import org.apache.hadoop.mapreduce.v2.proto.MRServiceProtos.KillJobResponseProto;
/*  79:    */ import org.apache.hadoop.mapreduce.v2.proto.MRServiceProtos.KillTaskAttemptRequestProto;
/*  80:    */ import org.apache.hadoop.mapreduce.v2.proto.MRServiceProtos.KillTaskAttemptResponseProto;
/*  81:    */ import org.apache.hadoop.mapreduce.v2.proto.MRServiceProtos.KillTaskRequestProto;
/*  82:    */ import org.apache.hadoop.mapreduce.v2.proto.MRServiceProtos.KillTaskResponseProto;
/*  83:    */ import org.apache.hadoop.security.proto.SecurityProtos.CancelDelegationTokenRequestProto;
/*  84:    */ import org.apache.hadoop.security.proto.SecurityProtos.CancelDelegationTokenResponseProto;
/*  85:    */ import org.apache.hadoop.security.proto.SecurityProtos.GetDelegationTokenRequestProto;
/*  86:    */ import org.apache.hadoop.security.proto.SecurityProtos.GetDelegationTokenResponseProto;
/*  87:    */ import org.apache.hadoop.security.proto.SecurityProtos.RenewDelegationTokenRequestProto;
/*  88:    */ import org.apache.hadoop.security.proto.SecurityProtos.RenewDelegationTokenResponseProto;
/*  89:    */ 
/*  90:    */ public class MRClientProtocolPBServiceImpl
/*  91:    */   implements MRClientProtocolPB
/*  92:    */ {
/*  93:    */   private MRClientProtocol real;
/*  94:    */   
/*  95:    */   public MRClientProtocolPBServiceImpl(MRClientProtocol impl)
/*  96:    */   {
/*  97:114 */     this.real = impl;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public MRServiceProtos.GetJobReportResponseProto getJobReport(RpcController controller, MRServiceProtos.GetJobReportRequestProto proto)
/* 101:    */     throws ServiceException
/* 102:    */   {
/* 103:120 */     GetJobReportRequestPBImpl request = new GetJobReportRequestPBImpl(proto);
/* 104:    */     try
/* 105:    */     {
/* 106:122 */       GetJobReportResponse response = this.real.getJobReport(request);
/* 107:123 */       return ((GetJobReportResponsePBImpl)response).getProto();
/* 108:    */     }
/* 109:    */     catch (IOException e)
/* 110:    */     {
/* 111:125 */       throw new ServiceException(e);
/* 112:    */     }
/* 113:    */   }
/* 114:    */   
/* 115:    */   public MRServiceProtos.GetTaskReportResponseProto getTaskReport(RpcController controller, MRServiceProtos.GetTaskReportRequestProto proto)
/* 116:    */     throws ServiceException
/* 117:    */   {
/* 118:132 */     GetTaskReportRequest request = new GetTaskReportRequestPBImpl(proto);
/* 119:    */     try
/* 120:    */     {
/* 121:134 */       GetTaskReportResponse response = this.real.getTaskReport(request);
/* 122:135 */       return ((GetTaskReportResponsePBImpl)response).getProto();
/* 123:    */     }
/* 124:    */     catch (IOException e)
/* 125:    */     {
/* 126:137 */       throw new ServiceException(e);
/* 127:    */     }
/* 128:    */   }
/* 129:    */   
/* 130:    */   public MRServiceProtos.GetTaskAttemptReportResponseProto getTaskAttemptReport(RpcController controller, MRServiceProtos.GetTaskAttemptReportRequestProto proto)
/* 131:    */     throws ServiceException
/* 132:    */   {
/* 133:145 */     GetTaskAttemptReportRequest request = new GetTaskAttemptReportRequestPBImpl(proto);
/* 134:    */     try
/* 135:    */     {
/* 136:147 */       GetTaskAttemptReportResponse response = this.real.getTaskAttemptReport(request);
/* 137:148 */       return ((GetTaskAttemptReportResponsePBImpl)response).getProto();
/* 138:    */     }
/* 139:    */     catch (IOException e)
/* 140:    */     {
/* 141:150 */       throw new ServiceException(e);
/* 142:    */     }
/* 143:    */   }
/* 144:    */   
/* 145:    */   public MRServiceProtos.GetCountersResponseProto getCounters(RpcController controller, MRServiceProtos.GetCountersRequestProto proto)
/* 146:    */     throws ServiceException
/* 147:    */   {
/* 148:157 */     GetCountersRequest request = new GetCountersRequestPBImpl(proto);
/* 149:    */     try
/* 150:    */     {
/* 151:159 */       GetCountersResponse response = this.real.getCounters(request);
/* 152:160 */       return ((GetCountersResponsePBImpl)response).getProto();
/* 153:    */     }
/* 154:    */     catch (IOException e)
/* 155:    */     {
/* 156:162 */       throw new ServiceException(e);
/* 157:    */     }
/* 158:    */   }
/* 159:    */   
/* 160:    */   public MRServiceProtos.GetTaskAttemptCompletionEventsResponseProto getTaskAttemptCompletionEvents(RpcController controller, MRServiceProtos.GetTaskAttemptCompletionEventsRequestProto proto)
/* 161:    */     throws ServiceException
/* 162:    */   {
/* 163:171 */     GetTaskAttemptCompletionEventsRequest request = new GetTaskAttemptCompletionEventsRequestPBImpl(proto);
/* 164:    */     try
/* 165:    */     {
/* 166:173 */       GetTaskAttemptCompletionEventsResponse response = this.real.getTaskAttemptCompletionEvents(request);
/* 167:174 */       return ((GetTaskAttemptCompletionEventsResponsePBImpl)response).getProto();
/* 168:    */     }
/* 169:    */     catch (IOException e)
/* 170:    */     {
/* 171:176 */       throw new ServiceException(e);
/* 172:    */     }
/* 173:    */   }
/* 174:    */   
/* 175:    */   public MRServiceProtos.GetTaskReportsResponseProto getTaskReports(RpcController controller, MRServiceProtos.GetTaskReportsRequestProto proto)
/* 176:    */     throws ServiceException
/* 177:    */   {
/* 178:183 */     GetTaskReportsRequest request = new GetTaskReportsRequestPBImpl(proto);
/* 179:    */     try
/* 180:    */     {
/* 181:185 */       GetTaskReportsResponse response = this.real.getTaskReports(request);
/* 182:186 */       return ((GetTaskReportsResponsePBImpl)response).getProto();
/* 183:    */     }
/* 184:    */     catch (IOException e)
/* 185:    */     {
/* 186:188 */       throw new ServiceException(e);
/* 187:    */     }
/* 188:    */   }
/* 189:    */   
/* 190:    */   public MRServiceProtos.GetDiagnosticsResponseProto getDiagnostics(RpcController controller, MRServiceProtos.GetDiagnosticsRequestProto proto)
/* 191:    */     throws ServiceException
/* 192:    */   {
/* 193:195 */     GetDiagnosticsRequest request = new GetDiagnosticsRequestPBImpl(proto);
/* 194:    */     try
/* 195:    */     {
/* 196:197 */       GetDiagnosticsResponse response = this.real.getDiagnostics(request);
/* 197:198 */       return ((GetDiagnosticsResponsePBImpl)response).getProto();
/* 198:    */     }
/* 199:    */     catch (IOException e)
/* 200:    */     {
/* 201:200 */       throw new ServiceException(e);
/* 202:    */     }
/* 203:    */   }
/* 204:    */   
/* 205:    */   public SecurityProtos.GetDelegationTokenResponseProto getDelegationToken(RpcController controller, SecurityProtos.GetDelegationTokenRequestProto proto)
/* 206:    */     throws ServiceException
/* 207:    */   {
/* 208:208 */     GetDelegationTokenRequest request = new GetDelegationTokenRequestPBImpl(proto);
/* 209:    */     try
/* 210:    */     {
/* 211:210 */       GetDelegationTokenResponse response = this.real.getDelegationToken(request);
/* 212:211 */       return ((GetDelegationTokenResponsePBImpl)response).getProto();
/* 213:    */     }
/* 214:    */     catch (IOException e)
/* 215:    */     {
/* 216:213 */       throw new ServiceException(e);
/* 217:    */     }
/* 218:    */   }
/* 219:    */   
/* 220:    */   public MRServiceProtos.KillJobResponseProto killJob(RpcController controller, MRServiceProtos.KillJobRequestProto proto)
/* 221:    */     throws ServiceException
/* 222:    */   {
/* 223:220 */     KillJobRequest request = new KillJobRequestPBImpl(proto);
/* 224:    */     try
/* 225:    */     {
/* 226:222 */       KillJobResponse response = this.real.killJob(request);
/* 227:223 */       return ((KillJobResponsePBImpl)response).getProto();
/* 228:    */     }
/* 229:    */     catch (IOException e)
/* 230:    */     {
/* 231:225 */       throw new ServiceException(e);
/* 232:    */     }
/* 233:    */   }
/* 234:    */   
/* 235:    */   public MRServiceProtos.KillTaskResponseProto killTask(RpcController controller, MRServiceProtos.KillTaskRequestProto proto)
/* 236:    */     throws ServiceException
/* 237:    */   {
/* 238:232 */     KillTaskRequest request = new KillTaskRequestPBImpl(proto);
/* 239:    */     try
/* 240:    */     {
/* 241:234 */       KillTaskResponse response = this.real.killTask(request);
/* 242:235 */       return ((KillTaskResponsePBImpl)response).getProto();
/* 243:    */     }
/* 244:    */     catch (IOException e)
/* 245:    */     {
/* 246:237 */       throw new ServiceException(e);
/* 247:    */     }
/* 248:    */   }
/* 249:    */   
/* 250:    */   public MRServiceProtos.KillTaskAttemptResponseProto killTaskAttempt(RpcController controller, MRServiceProtos.KillTaskAttemptRequestProto proto)
/* 251:    */     throws ServiceException
/* 252:    */   {
/* 253:244 */     KillTaskAttemptRequest request = new KillTaskAttemptRequestPBImpl(proto);
/* 254:    */     try
/* 255:    */     {
/* 256:246 */       KillTaskAttemptResponse response = this.real.killTaskAttempt(request);
/* 257:247 */       return ((KillTaskAttemptResponsePBImpl)response).getProto();
/* 258:    */     }
/* 259:    */     catch (IOException e)
/* 260:    */     {
/* 261:249 */       throw new ServiceException(e);
/* 262:    */     }
/* 263:    */   }
/* 264:    */   
/* 265:    */   public MRServiceProtos.FailTaskAttemptResponseProto failTaskAttempt(RpcController controller, MRServiceProtos.FailTaskAttemptRequestProto proto)
/* 266:    */     throws ServiceException
/* 267:    */   {
/* 268:256 */     FailTaskAttemptRequest request = new FailTaskAttemptRequestPBImpl(proto);
/* 269:    */     try
/* 270:    */     {
/* 271:258 */       FailTaskAttemptResponse response = this.real.failTaskAttempt(request);
/* 272:259 */       return ((FailTaskAttemptResponsePBImpl)response).getProto();
/* 273:    */     }
/* 274:    */     catch (IOException e)
/* 275:    */     {
/* 276:261 */       throw new ServiceException(e);
/* 277:    */     }
/* 278:    */   }
/* 279:    */   
/* 280:    */   public SecurityProtos.RenewDelegationTokenResponseProto renewDelegationToken(RpcController controller, SecurityProtos.RenewDelegationTokenRequestProto proto)
/* 281:    */     throws ServiceException
/* 282:    */   {
/* 283:269 */     RenewDelegationTokenRequestPBImpl request = new RenewDelegationTokenRequestPBImpl(proto);
/* 284:    */     try
/* 285:    */     {
/* 286:272 */       RenewDelegationTokenResponse response = this.real.renewDelegationToken(request);
/* 287:273 */       return ((RenewDelegationTokenResponsePBImpl)response).getProto();
/* 288:    */     }
/* 289:    */     catch (IOException e)
/* 290:    */     {
/* 291:275 */       throw new ServiceException(e);
/* 292:    */     }
/* 293:    */   }
/* 294:    */   
/* 295:    */   public SecurityProtos.CancelDelegationTokenResponseProto cancelDelegationToken(RpcController controller, SecurityProtos.CancelDelegationTokenRequestProto proto)
/* 296:    */     throws ServiceException
/* 297:    */   {
/* 298:283 */     CancelDelegationTokenRequestPBImpl request = new CancelDelegationTokenRequestPBImpl(proto);
/* 299:    */     try
/* 300:    */     {
/* 301:286 */       CancelDelegationTokenResponse response = this.real.cancelDelegationToken(request);
/* 302:287 */       return ((CancelDelegationTokenResponsePBImpl)response).getProto();
/* 303:    */     }
/* 304:    */     catch (IOException e)
/* 305:    */     {
/* 306:289 */       throw new ServiceException(e);
/* 307:    */     }
/* 308:    */   }
/* 309:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-common-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.v2.api.impl.pb.service.MRClientProtocolPBServiceImpl
 * JD-Core Version:    0.7.0.1
 */