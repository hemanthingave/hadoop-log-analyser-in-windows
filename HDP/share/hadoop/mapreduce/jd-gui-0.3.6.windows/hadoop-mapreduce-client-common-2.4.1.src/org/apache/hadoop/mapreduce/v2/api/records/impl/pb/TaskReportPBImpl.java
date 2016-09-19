/*   1:    */ package org.apache.hadoop.mapreduce.v2.api.records.impl.pb;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.Iterator;
/*   5:    */ import java.util.List;
/*   6:    */ import org.apache.hadoop.mapreduce.v2.api.records.Counters;
/*   7:    */ import org.apache.hadoop.mapreduce.v2.api.records.TaskAttemptId;
/*   8:    */ import org.apache.hadoop.mapreduce.v2.api.records.TaskId;
/*   9:    */ import org.apache.hadoop.mapreduce.v2.api.records.TaskReport;
/*  10:    */ import org.apache.hadoop.mapreduce.v2.api.records.TaskState;
/*  11:    */ import org.apache.hadoop.mapreduce.v2.proto.MRProtos.CountersProto;
/*  12:    */ import org.apache.hadoop.mapreduce.v2.proto.MRProtos.TaskAttemptIdProto;
/*  13:    */ import org.apache.hadoop.mapreduce.v2.proto.MRProtos.TaskIdProto;
/*  14:    */ import org.apache.hadoop.mapreduce.v2.proto.MRProtos.TaskReportProto;
/*  15:    */ import org.apache.hadoop.mapreduce.v2.proto.MRProtos.TaskReportProto.Builder;
/*  16:    */ import org.apache.hadoop.mapreduce.v2.proto.MRProtos.TaskReportProtoOrBuilder;
/*  17:    */ import org.apache.hadoop.mapreduce.v2.proto.MRProtos.TaskStateProto;
/*  18:    */ import org.apache.hadoop.mapreduce.v2.util.MRProtoUtils;
/*  19:    */ import org.apache.hadoop.yarn.api.records.impl.pb.ProtoBase;
/*  20:    */ 
/*  21:    */ public class TaskReportPBImpl
/*  22:    */   extends ProtoBase<MRProtos.TaskReportProto>
/*  23:    */   implements TaskReport
/*  24:    */ {
/*  25: 43 */   MRProtos.TaskReportProto proto = MRProtos.TaskReportProto.getDefaultInstance();
/*  26: 44 */   MRProtos.TaskReportProto.Builder builder = null;
/*  27: 45 */   boolean viaProto = false;
/*  28: 47 */   private TaskId taskId = null;
/*  29: 48 */   private Counters counters = null;
/*  30: 49 */   private List<TaskAttemptId> runningAttempts = null;
/*  31: 50 */   private TaskAttemptId successfulAttemptId = null;
/*  32: 51 */   private List<String> diagnostics = null;
/*  33:    */   private String status;
/*  34:    */   
/*  35:    */   public TaskReportPBImpl()
/*  36:    */   {
/*  37: 56 */     this.builder = MRProtos.TaskReportProto.newBuilder();
/*  38:    */   }
/*  39:    */   
/*  40:    */   public TaskReportPBImpl(MRProtos.TaskReportProto proto)
/*  41:    */   {
/*  42: 60 */     this.proto = proto;
/*  43: 61 */     this.viaProto = true;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public MRProtos.TaskReportProto getProto()
/*  47:    */   {
/*  48: 65 */     mergeLocalToProto();
/*  49: 66 */     this.proto = (this.viaProto ? this.proto : this.builder.build());
/*  50: 67 */     this.viaProto = true;
/*  51: 68 */     return this.proto;
/*  52:    */   }
/*  53:    */   
/*  54:    */   private void mergeLocalToBuilder()
/*  55:    */   {
/*  56: 72 */     if (this.taskId != null) {
/*  57: 73 */       this.builder.setTaskId(convertToProtoFormat(this.taskId));
/*  58:    */     }
/*  59: 75 */     if (this.counters != null) {
/*  60: 76 */       this.builder.setCounters(convertToProtoFormat(this.counters));
/*  61:    */     }
/*  62: 78 */     if (this.runningAttempts != null) {
/*  63: 79 */       addRunningAttemptsToProto();
/*  64:    */     }
/*  65: 81 */     if (this.successfulAttemptId != null) {
/*  66: 82 */       this.builder.setSuccessfulAttempt(convertToProtoFormat(this.successfulAttemptId));
/*  67:    */     }
/*  68: 84 */     if (this.diagnostics != null) {
/*  69: 85 */       addDiagnosticsToProto();
/*  70:    */     }
/*  71:    */   }
/*  72:    */   
/*  73:    */   private void mergeLocalToProto()
/*  74:    */   {
/*  75: 90 */     if (this.viaProto) {
/*  76: 91 */       maybeInitBuilder();
/*  77:    */     }
/*  78: 92 */     mergeLocalToBuilder();
/*  79: 93 */     this.proto = this.builder.build();
/*  80: 94 */     this.viaProto = true;
/*  81:    */   }
/*  82:    */   
/*  83:    */   private void maybeInitBuilder()
/*  84:    */   {
/*  85: 98 */     if ((this.viaProto) || (this.builder == null)) {
/*  86: 99 */       this.builder = MRProtos.TaskReportProto.newBuilder(this.proto);
/*  87:    */     }
/*  88:101 */     this.viaProto = false;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public Counters getCounters()
/*  92:    */   {
/*  93:107 */     MRProtos.TaskReportProtoOrBuilder p = this.viaProto ? this.proto : this.builder;
/*  94:108 */     if (this.counters != null) {
/*  95:109 */       return this.counters;
/*  96:    */     }
/*  97:111 */     if (!p.hasCounters()) {
/*  98:112 */       return null;
/*  99:    */     }
/* 100:114 */     this.counters = convertFromProtoFormat(p.getCounters());
/* 101:115 */     return this.counters;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public void setCounters(Counters counters)
/* 105:    */   {
/* 106:120 */     maybeInitBuilder();
/* 107:121 */     if (counters == null) {
/* 108:122 */       this.builder.clearCounters();
/* 109:    */     }
/* 110:123 */     this.counters = counters;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public long getStartTime()
/* 114:    */   {
/* 115:127 */     MRProtos.TaskReportProtoOrBuilder p = this.viaProto ? this.proto : this.builder;
/* 116:128 */     return p.getStartTime();
/* 117:    */   }
/* 118:    */   
/* 119:    */   public void setStartTime(long startTime)
/* 120:    */   {
/* 121:133 */     maybeInitBuilder();
/* 122:134 */     this.builder.setStartTime(startTime);
/* 123:    */   }
/* 124:    */   
/* 125:    */   public long getFinishTime()
/* 126:    */   {
/* 127:139 */     MRProtos.TaskReportProtoOrBuilder p = this.viaProto ? this.proto : this.builder;
/* 128:140 */     return p.getFinishTime();
/* 129:    */   }
/* 130:    */   
/* 131:    */   public void setFinishTime(long finishTime)
/* 132:    */   {
/* 133:145 */     maybeInitBuilder();
/* 134:146 */     this.builder.setFinishTime(finishTime);
/* 135:    */   }
/* 136:    */   
/* 137:    */   public TaskId getTaskId()
/* 138:    */   {
/* 139:151 */     MRProtos.TaskReportProtoOrBuilder p = this.viaProto ? this.proto : this.builder;
/* 140:152 */     if (this.taskId != null) {
/* 141:153 */       return this.taskId;
/* 142:    */     }
/* 143:155 */     if (!p.hasTaskId()) {
/* 144:156 */       return null;
/* 145:    */     }
/* 146:158 */     this.taskId = convertFromProtoFormat(p.getTaskId());
/* 147:159 */     return this.taskId;
/* 148:    */   }
/* 149:    */   
/* 150:    */   public void setTaskId(TaskId taskId)
/* 151:    */   {
/* 152:164 */     maybeInitBuilder();
/* 153:165 */     if (taskId == null) {
/* 154:166 */       this.builder.clearTaskId();
/* 155:    */     }
/* 156:167 */     this.taskId = taskId;
/* 157:    */   }
/* 158:    */   
/* 159:    */   public float getProgress()
/* 160:    */   {
/* 161:171 */     MRProtos.TaskReportProtoOrBuilder p = this.viaProto ? this.proto : this.builder;
/* 162:172 */     return p.getProgress();
/* 163:    */   }
/* 164:    */   
/* 165:    */   public String getStatus()
/* 166:    */   {
/* 167:177 */     return this.status;
/* 168:    */   }
/* 169:    */   
/* 170:    */   public void setProgress(float progress)
/* 171:    */   {
/* 172:182 */     maybeInitBuilder();
/* 173:183 */     this.builder.setProgress(progress);
/* 174:    */   }
/* 175:    */   
/* 176:    */   public void setStatus(String status)
/* 177:    */   {
/* 178:188 */     this.status = status;
/* 179:    */   }
/* 180:    */   
/* 181:    */   public TaskState getTaskState()
/* 182:    */   {
/* 183:193 */     MRProtos.TaskReportProtoOrBuilder p = this.viaProto ? this.proto : this.builder;
/* 184:194 */     if (!p.hasTaskState()) {
/* 185:195 */       return null;
/* 186:    */     }
/* 187:197 */     return convertFromProtoFormat(p.getTaskState());
/* 188:    */   }
/* 189:    */   
/* 190:    */   public void setTaskState(TaskState taskState)
/* 191:    */   {
/* 192:202 */     maybeInitBuilder();
/* 193:203 */     if (taskState == null)
/* 194:    */     {
/* 195:204 */       this.builder.clearTaskState();
/* 196:205 */       return;
/* 197:    */     }
/* 198:207 */     this.builder.setTaskState(convertToProtoFormat(taskState));
/* 199:    */   }
/* 200:    */   
/* 201:    */   public List<TaskAttemptId> getRunningAttemptsList()
/* 202:    */   {
/* 203:211 */     initRunningAttempts();
/* 204:212 */     return this.runningAttempts;
/* 205:    */   }
/* 206:    */   
/* 207:    */   public TaskAttemptId getRunningAttempt(int index)
/* 208:    */   {
/* 209:216 */     initRunningAttempts();
/* 210:217 */     return (TaskAttemptId)this.runningAttempts.get(index);
/* 211:    */   }
/* 212:    */   
/* 213:    */   public int getRunningAttemptsCount()
/* 214:    */   {
/* 215:221 */     initRunningAttempts();
/* 216:222 */     return this.runningAttempts.size();
/* 217:    */   }
/* 218:    */   
/* 219:    */   private void initRunningAttempts()
/* 220:    */   {
/* 221:226 */     if (this.runningAttempts != null) {
/* 222:227 */       return;
/* 223:    */     }
/* 224:229 */     MRProtos.TaskReportProtoOrBuilder p = this.viaProto ? this.proto : this.builder;
/* 225:230 */     List<MRProtos.TaskAttemptIdProto> list = p.getRunningAttemptsList();
/* 226:231 */     this.runningAttempts = new ArrayList();
/* 227:233 */     for (MRProtos.TaskAttemptIdProto c : list) {
/* 228:234 */       this.runningAttempts.add(convertFromProtoFormat(c));
/* 229:    */     }
/* 230:    */   }
/* 231:    */   
/* 232:    */   public void addAllRunningAttempts(List<TaskAttemptId> runningAttempts)
/* 233:    */   {
/* 234:240 */     if (runningAttempts == null) {
/* 235:241 */       return;
/* 236:    */     }
/* 237:242 */     initRunningAttempts();
/* 238:243 */     this.runningAttempts.addAll(runningAttempts);
/* 239:    */   }
/* 240:    */   
/* 241:    */   private void addRunningAttemptsToProto()
/* 242:    */   {
/* 243:247 */     maybeInitBuilder();
/* 244:248 */     this.builder.clearRunningAttempts();
/* 245:249 */     if (this.runningAttempts == null) {
/* 246:250 */       return;
/* 247:    */     }
/* 248:251 */     Iterable<MRProtos.TaskAttemptIdProto> iterable = new Iterable()
/* 249:    */     {
/* 250:    */       public Iterator<MRProtos.TaskAttemptIdProto> iterator()
/* 251:    */       {
/* 252:254 */         new Iterator()
/* 253:    */         {
/* 254:256 */           Iterator<TaskAttemptId> iter = TaskReportPBImpl.this.runningAttempts.iterator();
/* 255:    */           
/* 256:    */           public boolean hasNext()
/* 257:    */           {
/* 258:260 */             return this.iter.hasNext();
/* 259:    */           }
/* 260:    */           
/* 261:    */           public MRProtos.TaskAttemptIdProto next()
/* 262:    */           {
/* 263:265 */             return TaskReportPBImpl.this.convertToProtoFormat((TaskAttemptId)this.iter.next());
/* 264:    */           }
/* 265:    */           
/* 266:    */           public void remove()
/* 267:    */           {
/* 268:270 */             throw new UnsupportedOperationException();
/* 269:    */           }
/* 270:    */         };
/* 271:    */       }
/* 272:276 */     };
/* 273:277 */     this.builder.addAllRunningAttempts(iterable);
/* 274:    */   }
/* 275:    */   
/* 276:    */   public void addRunningAttempt(TaskAttemptId runningAttempts)
/* 277:    */   {
/* 278:281 */     initRunningAttempts();
/* 279:282 */     this.runningAttempts.add(runningAttempts);
/* 280:    */   }
/* 281:    */   
/* 282:    */   public void removeRunningAttempt(int index)
/* 283:    */   {
/* 284:286 */     initRunningAttempts();
/* 285:287 */     this.runningAttempts.remove(index);
/* 286:    */   }
/* 287:    */   
/* 288:    */   public void clearRunningAttempts()
/* 289:    */   {
/* 290:291 */     initRunningAttempts();
/* 291:292 */     this.runningAttempts.clear();
/* 292:    */   }
/* 293:    */   
/* 294:    */   public TaskAttemptId getSuccessfulAttempt()
/* 295:    */   {
/* 296:296 */     MRProtos.TaskReportProtoOrBuilder p = this.viaProto ? this.proto : this.builder;
/* 297:297 */     if (this.successfulAttemptId != null) {
/* 298:298 */       return this.successfulAttemptId;
/* 299:    */     }
/* 300:300 */     if (!p.hasSuccessfulAttempt()) {
/* 301:301 */       return null;
/* 302:    */     }
/* 303:303 */     this.successfulAttemptId = convertFromProtoFormat(p.getSuccessfulAttempt());
/* 304:304 */     return this.successfulAttemptId;
/* 305:    */   }
/* 306:    */   
/* 307:    */   public void setSuccessfulAttempt(TaskAttemptId successfulAttempt)
/* 308:    */   {
/* 309:309 */     maybeInitBuilder();
/* 310:310 */     if (successfulAttempt == null) {
/* 311:311 */       this.builder.clearSuccessfulAttempt();
/* 312:    */     }
/* 313:312 */     this.successfulAttemptId = successfulAttempt;
/* 314:    */   }
/* 315:    */   
/* 316:    */   public List<String> getDiagnosticsList()
/* 317:    */   {
/* 318:316 */     initDiagnostics();
/* 319:317 */     return this.diagnostics;
/* 320:    */   }
/* 321:    */   
/* 322:    */   public String getDiagnostics(int index)
/* 323:    */   {
/* 324:321 */     initDiagnostics();
/* 325:322 */     return (String)this.diagnostics.get(index);
/* 326:    */   }
/* 327:    */   
/* 328:    */   public int getDiagnosticsCount()
/* 329:    */   {
/* 330:326 */     initDiagnostics();
/* 331:327 */     return this.diagnostics.size();
/* 332:    */   }
/* 333:    */   
/* 334:    */   private void initDiagnostics()
/* 335:    */   {
/* 336:331 */     if (this.diagnostics != null) {
/* 337:332 */       return;
/* 338:    */     }
/* 339:334 */     MRProtos.TaskReportProtoOrBuilder p = this.viaProto ? this.proto : this.builder;
/* 340:335 */     List<String> list = p.getDiagnosticsList();
/* 341:336 */     this.diagnostics = new ArrayList();
/* 342:338 */     for (String c : list) {
/* 343:339 */       this.diagnostics.add(c);
/* 344:    */     }
/* 345:    */   }
/* 346:    */   
/* 347:    */   public void addAllDiagnostics(List<String> diagnostics)
/* 348:    */   {
/* 349:345 */     if (diagnostics == null) {
/* 350:346 */       return;
/* 351:    */     }
/* 352:347 */     initDiagnostics();
/* 353:348 */     this.diagnostics.addAll(diagnostics);
/* 354:    */   }
/* 355:    */   
/* 356:    */   private void addDiagnosticsToProto()
/* 357:    */   {
/* 358:352 */     maybeInitBuilder();
/* 359:353 */     this.builder.clearDiagnostics();
/* 360:354 */     if (this.diagnostics == null) {
/* 361:355 */       return;
/* 362:    */     }
/* 363:356 */     this.builder.addAllDiagnostics(this.diagnostics);
/* 364:    */   }
/* 365:    */   
/* 366:    */   public void addDiagnostics(String diagnostics)
/* 367:    */   {
/* 368:360 */     initDiagnostics();
/* 369:361 */     this.diagnostics.add(diagnostics);
/* 370:    */   }
/* 371:    */   
/* 372:    */   public void removeDiagnostics(int index)
/* 373:    */   {
/* 374:365 */     initDiagnostics();
/* 375:366 */     this.diagnostics.remove(index);
/* 376:    */   }
/* 377:    */   
/* 378:    */   public void clearDiagnostics()
/* 379:    */   {
/* 380:370 */     initDiagnostics();
/* 381:371 */     this.diagnostics.clear();
/* 382:    */   }
/* 383:    */   
/* 384:    */   private CountersPBImpl convertFromProtoFormat(MRProtos.CountersProto p)
/* 385:    */   {
/* 386:375 */     return new CountersPBImpl(p);
/* 387:    */   }
/* 388:    */   
/* 389:    */   private MRProtos.CountersProto convertToProtoFormat(Counters t)
/* 390:    */   {
/* 391:379 */     return ((CountersPBImpl)t).getProto();
/* 392:    */   }
/* 393:    */   
/* 394:    */   private TaskIdPBImpl convertFromProtoFormat(MRProtos.TaskIdProto p)
/* 395:    */   {
/* 396:383 */     return new TaskIdPBImpl(p);
/* 397:    */   }
/* 398:    */   
/* 399:    */   private MRProtos.TaskIdProto convertToProtoFormat(TaskId t)
/* 400:    */   {
/* 401:387 */     return ((TaskIdPBImpl)t).getProto();
/* 402:    */   }
/* 403:    */   
/* 404:    */   private MRProtos.TaskStateProto convertToProtoFormat(TaskState e)
/* 405:    */   {
/* 406:391 */     return MRProtoUtils.convertToProtoFormat(e);
/* 407:    */   }
/* 408:    */   
/* 409:    */   private TaskState convertFromProtoFormat(MRProtos.TaskStateProto e)
/* 410:    */   {
/* 411:395 */     return MRProtoUtils.convertFromProtoFormat(e);
/* 412:    */   }
/* 413:    */   
/* 414:    */   private TaskAttemptIdPBImpl convertFromProtoFormat(MRProtos.TaskAttemptIdProto p)
/* 415:    */   {
/* 416:399 */     return new TaskAttemptIdPBImpl(p);
/* 417:    */   }
/* 418:    */   
/* 419:    */   private MRProtos.TaskAttemptIdProto convertToProtoFormat(TaskAttemptId t)
/* 420:    */   {
/* 421:403 */     return ((TaskAttemptIdPBImpl)t).getProto();
/* 422:    */   }
/* 423:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-common-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.v2.api.records.impl.pb.TaskReportPBImpl
 * JD-Core Version:    0.7.0.1
 */