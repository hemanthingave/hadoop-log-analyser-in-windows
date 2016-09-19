/*   1:    */ package org.apache.hadoop.mapreduce.v2.api.records.impl.pb;
/*   2:    */ 
/*   3:    */ import org.apache.hadoop.mapreduce.v2.api.records.Counters;
/*   4:    */ import org.apache.hadoop.mapreduce.v2.api.records.Phase;
/*   5:    */ import org.apache.hadoop.mapreduce.v2.api.records.TaskAttemptId;
/*   6:    */ import org.apache.hadoop.mapreduce.v2.api.records.TaskAttemptReport;
/*   7:    */ import org.apache.hadoop.mapreduce.v2.api.records.TaskAttemptState;
/*   8:    */ import org.apache.hadoop.mapreduce.v2.proto.MRProtos.CountersProto;
/*   9:    */ import org.apache.hadoop.mapreduce.v2.proto.MRProtos.PhaseProto;
/*  10:    */ import org.apache.hadoop.mapreduce.v2.proto.MRProtos.TaskAttemptIdProto;
/*  11:    */ import org.apache.hadoop.mapreduce.v2.proto.MRProtos.TaskAttemptReportProto;
/*  12:    */ import org.apache.hadoop.mapreduce.v2.proto.MRProtos.TaskAttemptReportProto.Builder;
/*  13:    */ import org.apache.hadoop.mapreduce.v2.proto.MRProtos.TaskAttemptReportProtoOrBuilder;
/*  14:    */ import org.apache.hadoop.mapreduce.v2.proto.MRProtos.TaskAttemptStateProto;
/*  15:    */ import org.apache.hadoop.mapreduce.v2.util.MRProtoUtils;
/*  16:    */ import org.apache.hadoop.yarn.api.records.ContainerId;
/*  17:    */ import org.apache.hadoop.yarn.api.records.impl.pb.ContainerIdPBImpl;
/*  18:    */ import org.apache.hadoop.yarn.api.records.impl.pb.ProtoBase;
/*  19:    */ import org.apache.hadoop.yarn.proto.YarnProtos.ContainerIdProto;
/*  20:    */ 
/*  21:    */ public class TaskAttemptReportPBImpl
/*  22:    */   extends ProtoBase<MRProtos.TaskAttemptReportProto>
/*  23:    */   implements TaskAttemptReport
/*  24:    */ {
/*  25: 42 */   MRProtos.TaskAttemptReportProto proto = MRProtos.TaskAttemptReportProto.getDefaultInstance();
/*  26: 43 */   MRProtos.TaskAttemptReportProto.Builder builder = null;
/*  27: 44 */   boolean viaProto = false;
/*  28: 46 */   private TaskAttemptId taskAttemptId = null;
/*  29: 47 */   private Counters counters = null;
/*  30: 48 */   private ContainerId containerId = null;
/*  31:    */   
/*  32:    */   public TaskAttemptReportPBImpl()
/*  33:    */   {
/*  34: 52 */     this.builder = MRProtos.TaskAttemptReportProto.newBuilder();
/*  35:    */   }
/*  36:    */   
/*  37:    */   public TaskAttemptReportPBImpl(MRProtos.TaskAttemptReportProto proto)
/*  38:    */   {
/*  39: 56 */     this.proto = proto;
/*  40: 57 */     this.viaProto = true;
/*  41:    */   }
/*  42:    */   
/*  43:    */   public MRProtos.TaskAttemptReportProto getProto()
/*  44:    */   {
/*  45: 61 */     mergeLocalToProto();
/*  46: 62 */     this.proto = (this.viaProto ? this.proto : this.builder.build());
/*  47: 63 */     this.viaProto = true;
/*  48: 64 */     return this.proto;
/*  49:    */   }
/*  50:    */   
/*  51:    */   private void mergeLocalToBuilder()
/*  52:    */   {
/*  53: 68 */     if (this.taskAttemptId != null) {
/*  54: 69 */       this.builder.setTaskAttemptId(convertToProtoFormat(this.taskAttemptId));
/*  55:    */     }
/*  56: 71 */     if (this.counters != null) {
/*  57: 72 */       this.builder.setCounters(convertToProtoFormat(this.counters));
/*  58:    */     }
/*  59: 74 */     if (this.containerId != null) {
/*  60: 75 */       this.builder.setContainerId(convertToProtoFormat(this.containerId));
/*  61:    */     }
/*  62:    */   }
/*  63:    */   
/*  64:    */   private void mergeLocalToProto()
/*  65:    */   {
/*  66: 80 */     if (this.viaProto) {
/*  67: 81 */       maybeInitBuilder();
/*  68:    */     }
/*  69: 82 */     mergeLocalToBuilder();
/*  70: 83 */     this.proto = this.builder.build();
/*  71: 84 */     this.viaProto = true;
/*  72:    */   }
/*  73:    */   
/*  74:    */   private void maybeInitBuilder()
/*  75:    */   {
/*  76: 88 */     if ((this.viaProto) || (this.builder == null)) {
/*  77: 89 */       this.builder = MRProtos.TaskAttemptReportProto.newBuilder(this.proto);
/*  78:    */     }
/*  79: 91 */     this.viaProto = false;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public Counters getCounters()
/*  83:    */   {
/*  84: 97 */     MRProtos.TaskAttemptReportProtoOrBuilder p = this.viaProto ? this.proto : this.builder;
/*  85: 98 */     if (this.counters != null) {
/*  86: 99 */       return this.counters;
/*  87:    */     }
/*  88:101 */     if (!p.hasCounters()) {
/*  89:102 */       return null;
/*  90:    */     }
/*  91:104 */     this.counters = convertFromProtoFormat(p.getCounters());
/*  92:105 */     return this.counters;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public void setCounters(Counters counters)
/*  96:    */   {
/*  97:110 */     maybeInitBuilder();
/*  98:111 */     if (counters == null) {
/*  99:112 */       this.builder.clearCounters();
/* 100:    */     }
/* 101:113 */     this.counters = counters;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public long getStartTime()
/* 105:    */   {
/* 106:117 */     MRProtos.TaskAttemptReportProtoOrBuilder p = this.viaProto ? this.proto : this.builder;
/* 107:118 */     return p.getStartTime();
/* 108:    */   }
/* 109:    */   
/* 110:    */   public void setStartTime(long startTime)
/* 111:    */   {
/* 112:123 */     maybeInitBuilder();
/* 113:124 */     this.builder.setStartTime(startTime);
/* 114:    */   }
/* 115:    */   
/* 116:    */   public long getFinishTime()
/* 117:    */   {
/* 118:128 */     MRProtos.TaskAttemptReportProtoOrBuilder p = this.viaProto ? this.proto : this.builder;
/* 119:129 */     return p.getFinishTime();
/* 120:    */   }
/* 121:    */   
/* 122:    */   public void setFinishTime(long finishTime)
/* 123:    */   {
/* 124:134 */     maybeInitBuilder();
/* 125:135 */     this.builder.setFinishTime(finishTime);
/* 126:    */   }
/* 127:    */   
/* 128:    */   public long getShuffleFinishTime()
/* 129:    */   {
/* 130:140 */     MRProtos.TaskAttemptReportProtoOrBuilder p = this.viaProto ? this.proto : this.builder;
/* 131:141 */     return p.getShuffleFinishTime();
/* 132:    */   }
/* 133:    */   
/* 134:    */   public void setShuffleFinishTime(long time)
/* 135:    */   {
/* 136:146 */     maybeInitBuilder();
/* 137:147 */     this.builder.setShuffleFinishTime(time);
/* 138:    */   }
/* 139:    */   
/* 140:    */   public long getSortFinishTime()
/* 141:    */   {
/* 142:152 */     MRProtos.TaskAttemptReportProtoOrBuilder p = this.viaProto ? this.proto : this.builder;
/* 143:153 */     return p.getSortFinishTime();
/* 144:    */   }
/* 145:    */   
/* 146:    */   public void setSortFinishTime(long time)
/* 147:    */   {
/* 148:158 */     maybeInitBuilder();
/* 149:159 */     this.builder.setSortFinishTime(time);
/* 150:    */   }
/* 151:    */   
/* 152:    */   public TaskAttemptId getTaskAttemptId()
/* 153:    */   {
/* 154:164 */     MRProtos.TaskAttemptReportProtoOrBuilder p = this.viaProto ? this.proto : this.builder;
/* 155:165 */     if (this.taskAttemptId != null) {
/* 156:166 */       return this.taskAttemptId;
/* 157:    */     }
/* 158:168 */     if (!p.hasTaskAttemptId()) {
/* 159:169 */       return null;
/* 160:    */     }
/* 161:171 */     this.taskAttemptId = convertFromProtoFormat(p.getTaskAttemptId());
/* 162:172 */     return this.taskAttemptId;
/* 163:    */   }
/* 164:    */   
/* 165:    */   public void setTaskAttemptId(TaskAttemptId taskAttemptId)
/* 166:    */   {
/* 167:177 */     maybeInitBuilder();
/* 168:178 */     if (taskAttemptId == null) {
/* 169:179 */       this.builder.clearTaskAttemptId();
/* 170:    */     }
/* 171:180 */     this.taskAttemptId = taskAttemptId;
/* 172:    */   }
/* 173:    */   
/* 174:    */   public TaskAttemptState getTaskAttemptState()
/* 175:    */   {
/* 176:184 */     MRProtos.TaskAttemptReportProtoOrBuilder p = this.viaProto ? this.proto : this.builder;
/* 177:185 */     if (!p.hasTaskAttemptState()) {
/* 178:186 */       return null;
/* 179:    */     }
/* 180:188 */     return convertFromProtoFormat(p.getTaskAttemptState());
/* 181:    */   }
/* 182:    */   
/* 183:    */   public void setTaskAttemptState(TaskAttemptState taskAttemptState)
/* 184:    */   {
/* 185:193 */     maybeInitBuilder();
/* 186:194 */     if (taskAttemptState == null)
/* 187:    */     {
/* 188:195 */       this.builder.clearTaskAttemptState();
/* 189:196 */       return;
/* 190:    */     }
/* 191:198 */     this.builder.setTaskAttemptState(convertToProtoFormat(taskAttemptState));
/* 192:    */   }
/* 193:    */   
/* 194:    */   public float getProgress()
/* 195:    */   {
/* 196:202 */     MRProtos.TaskAttemptReportProtoOrBuilder p = this.viaProto ? this.proto : this.builder;
/* 197:203 */     return p.getProgress();
/* 198:    */   }
/* 199:    */   
/* 200:    */   public void setProgress(float progress)
/* 201:    */   {
/* 202:208 */     maybeInitBuilder();
/* 203:209 */     this.builder.setProgress(progress);
/* 204:    */   }
/* 205:    */   
/* 206:    */   public String getDiagnosticInfo()
/* 207:    */   {
/* 208:213 */     MRProtos.TaskAttemptReportProtoOrBuilder p = this.viaProto ? this.proto : this.builder;
/* 209:214 */     if (!p.hasDiagnosticInfo()) {
/* 210:215 */       return null;
/* 211:    */     }
/* 212:217 */     return p.getDiagnosticInfo();
/* 213:    */   }
/* 214:    */   
/* 215:    */   public void setDiagnosticInfo(String diagnosticInfo)
/* 216:    */   {
/* 217:222 */     maybeInitBuilder();
/* 218:223 */     if (diagnosticInfo == null)
/* 219:    */     {
/* 220:224 */       this.builder.clearDiagnosticInfo();
/* 221:225 */       return;
/* 222:    */     }
/* 223:227 */     this.builder.setDiagnosticInfo(diagnosticInfo);
/* 224:    */   }
/* 225:    */   
/* 226:    */   public String getStateString()
/* 227:    */   {
/* 228:231 */     MRProtos.TaskAttemptReportProtoOrBuilder p = this.viaProto ? this.proto : this.builder;
/* 229:232 */     if (!p.hasStateString()) {
/* 230:233 */       return null;
/* 231:    */     }
/* 232:235 */     return p.getStateString();
/* 233:    */   }
/* 234:    */   
/* 235:    */   public void setStateString(String stateString)
/* 236:    */   {
/* 237:240 */     maybeInitBuilder();
/* 238:241 */     if (stateString == null)
/* 239:    */     {
/* 240:242 */       this.builder.clearStateString();
/* 241:243 */       return;
/* 242:    */     }
/* 243:245 */     this.builder.setStateString(stateString);
/* 244:    */   }
/* 245:    */   
/* 246:    */   public Phase getPhase()
/* 247:    */   {
/* 248:249 */     MRProtos.TaskAttemptReportProtoOrBuilder p = this.viaProto ? this.proto : this.builder;
/* 249:250 */     if (!p.hasPhase()) {
/* 250:251 */       return null;
/* 251:    */     }
/* 252:253 */     return convertFromProtoFormat(p.getPhase());
/* 253:    */   }
/* 254:    */   
/* 255:    */   public void setPhase(Phase phase)
/* 256:    */   {
/* 257:258 */     maybeInitBuilder();
/* 258:259 */     if (phase == null)
/* 259:    */     {
/* 260:260 */       this.builder.clearPhase();
/* 261:261 */       return;
/* 262:    */     }
/* 263:263 */     this.builder.setPhase(convertToProtoFormat(phase));
/* 264:    */   }
/* 265:    */   
/* 266:    */   public String getNodeManagerHost()
/* 267:    */   {
/* 268:268 */     MRProtos.TaskAttemptReportProtoOrBuilder p = this.viaProto ? this.proto : this.builder;
/* 269:269 */     if (!p.hasNodeManagerHost()) {
/* 270:270 */       return null;
/* 271:    */     }
/* 272:272 */     return p.getNodeManagerHost();
/* 273:    */   }
/* 274:    */   
/* 275:    */   public void setNodeManagerHost(String nmHost)
/* 276:    */   {
/* 277:277 */     maybeInitBuilder();
/* 278:278 */     if (nmHost == null)
/* 279:    */     {
/* 280:279 */       this.builder.clearNodeManagerHost();
/* 281:280 */       return;
/* 282:    */     }
/* 283:282 */     this.builder.setNodeManagerHost(nmHost);
/* 284:    */   }
/* 285:    */   
/* 286:    */   public int getNodeManagerPort()
/* 287:    */   {
/* 288:287 */     MRProtos.TaskAttemptReportProtoOrBuilder p = this.viaProto ? this.proto : this.builder;
/* 289:288 */     return p.getNodeManagerPort();
/* 290:    */   }
/* 291:    */   
/* 292:    */   public void setNodeManagerPort(int nmPort)
/* 293:    */   {
/* 294:293 */     maybeInitBuilder();
/* 295:294 */     this.builder.setNodeManagerPort(nmPort);
/* 296:    */   }
/* 297:    */   
/* 298:    */   public int getNodeManagerHttpPort()
/* 299:    */   {
/* 300:299 */     MRProtos.TaskAttemptReportProtoOrBuilder p = this.viaProto ? this.proto : this.builder;
/* 301:300 */     return p.getNodeManagerHttpPort();
/* 302:    */   }
/* 303:    */   
/* 304:    */   public void setNodeManagerHttpPort(int nmHttpPort)
/* 305:    */   {
/* 306:305 */     maybeInitBuilder();
/* 307:306 */     this.builder.setNodeManagerHttpPort(nmHttpPort);
/* 308:    */   }
/* 309:    */   
/* 310:    */   public ContainerId getContainerId()
/* 311:    */   {
/* 312:311 */     MRProtos.TaskAttemptReportProtoOrBuilder p = this.viaProto ? this.proto : this.builder;
/* 313:312 */     if (this.containerId != null) {
/* 314:313 */       return this.containerId;
/* 315:    */     }
/* 316:315 */     if (!p.hasContainerId()) {
/* 317:316 */       return null;
/* 318:    */     }
/* 319:318 */     this.containerId = convertFromProtoFormat(p.getContainerId());
/* 320:319 */     return this.containerId;
/* 321:    */   }
/* 322:    */   
/* 323:    */   public void setContainerId(ContainerId containerId)
/* 324:    */   {
/* 325:324 */     maybeInitBuilder();
/* 326:325 */     if (containerId == null) {
/* 327:326 */       this.builder.clearContainerId();
/* 328:    */     }
/* 329:328 */     this.containerId = containerId;
/* 330:    */   }
/* 331:    */   
/* 332:    */   private YarnProtos.ContainerIdProto convertToProtoFormat(ContainerId t)
/* 333:    */   {
/* 334:332 */     return ((ContainerIdPBImpl)t).getProto();
/* 335:    */   }
/* 336:    */   
/* 337:    */   private ContainerIdPBImpl convertFromProtoFormat(YarnProtos.ContainerIdProto p)
/* 338:    */   {
/* 339:336 */     return new ContainerIdPBImpl(p);
/* 340:    */   }
/* 341:    */   
/* 342:    */   private CountersPBImpl convertFromProtoFormat(MRProtos.CountersProto p)
/* 343:    */   {
/* 344:340 */     return new CountersPBImpl(p);
/* 345:    */   }
/* 346:    */   
/* 347:    */   private MRProtos.CountersProto convertToProtoFormat(Counters t)
/* 348:    */   {
/* 349:344 */     return ((CountersPBImpl)t).getProto();
/* 350:    */   }
/* 351:    */   
/* 352:    */   private TaskAttemptIdPBImpl convertFromProtoFormat(MRProtos.TaskAttemptIdProto p)
/* 353:    */   {
/* 354:348 */     return new TaskAttemptIdPBImpl(p);
/* 355:    */   }
/* 356:    */   
/* 357:    */   private MRProtos.TaskAttemptIdProto convertToProtoFormat(TaskAttemptId t)
/* 358:    */   {
/* 359:352 */     return ((TaskAttemptIdPBImpl)t).getProto();
/* 360:    */   }
/* 361:    */   
/* 362:    */   private MRProtos.TaskAttemptStateProto convertToProtoFormat(TaskAttemptState e)
/* 363:    */   {
/* 364:356 */     return MRProtoUtils.convertToProtoFormat(e);
/* 365:    */   }
/* 366:    */   
/* 367:    */   private TaskAttemptState convertFromProtoFormat(MRProtos.TaskAttemptStateProto e)
/* 368:    */   {
/* 369:360 */     return MRProtoUtils.convertFromProtoFormat(e);
/* 370:    */   }
/* 371:    */   
/* 372:    */   private MRProtos.PhaseProto convertToProtoFormat(Phase e)
/* 373:    */   {
/* 374:364 */     return MRProtoUtils.convertToProtoFormat(e);
/* 375:    */   }
/* 376:    */   
/* 377:    */   private Phase convertFromProtoFormat(MRProtos.PhaseProto e)
/* 378:    */   {
/* 379:368 */     return MRProtoUtils.convertFromProtoFormat(e);
/* 380:    */   }
/* 381:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-common-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.v2.api.records.impl.pb.TaskAttemptReportPBImpl
 * JD-Core Version:    0.7.0.1
 */