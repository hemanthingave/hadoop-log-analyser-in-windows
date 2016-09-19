/*   1:    */ package org.apache.hadoop.mapreduce.v2.api.records.impl.pb;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.List;
/*   5:    */ import org.apache.hadoop.mapreduce.v2.api.records.AMInfo;
/*   6:    */ import org.apache.hadoop.mapreduce.v2.api.records.JobId;
/*   7:    */ import org.apache.hadoop.mapreduce.v2.api.records.JobReport;
/*   8:    */ import org.apache.hadoop.mapreduce.v2.api.records.JobState;
/*   9:    */ import org.apache.hadoop.mapreduce.v2.proto.MRProtos.AMInfoProto;
/*  10:    */ import org.apache.hadoop.mapreduce.v2.proto.MRProtos.JobIdProto;
/*  11:    */ import org.apache.hadoop.mapreduce.v2.proto.MRProtos.JobReportProto;
/*  12:    */ import org.apache.hadoop.mapreduce.v2.proto.MRProtos.JobReportProto.Builder;
/*  13:    */ import org.apache.hadoop.mapreduce.v2.proto.MRProtos.JobReportProtoOrBuilder;
/*  14:    */ import org.apache.hadoop.mapreduce.v2.proto.MRProtos.JobStateProto;
/*  15:    */ import org.apache.hadoop.mapreduce.v2.util.MRProtoUtils;
/*  16:    */ import org.apache.hadoop.yarn.api.records.impl.pb.ProtoBase;
/*  17:    */ 
/*  18:    */ public class JobReportPBImpl
/*  19:    */   extends ProtoBase<MRProtos.JobReportProto>
/*  20:    */   implements JobReport
/*  21:    */ {
/*  22: 41 */   MRProtos.JobReportProto proto = MRProtos.JobReportProto.getDefaultInstance();
/*  23: 42 */   MRProtos.JobReportProto.Builder builder = null;
/*  24: 43 */   boolean viaProto = false;
/*  25: 45 */   private JobId jobId = null;
/*  26: 46 */   private List<AMInfo> amInfos = null;
/*  27:    */   
/*  28:    */   public JobReportPBImpl()
/*  29:    */   {
/*  30: 50 */     this.builder = MRProtos.JobReportProto.newBuilder();
/*  31:    */   }
/*  32:    */   
/*  33:    */   public JobReportPBImpl(MRProtos.JobReportProto proto)
/*  34:    */   {
/*  35: 54 */     this.proto = proto;
/*  36: 55 */     this.viaProto = true;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public synchronized MRProtos.JobReportProto getProto()
/*  40:    */   {
/*  41: 59 */     mergeLocalToProto();
/*  42: 60 */     this.proto = (this.viaProto ? this.proto : this.builder.build());
/*  43: 61 */     this.viaProto = true;
/*  44: 62 */     return this.proto;
/*  45:    */   }
/*  46:    */   
/*  47:    */   private synchronized void mergeLocalToBuilder()
/*  48:    */   {
/*  49: 66 */     if (this.jobId != null) {
/*  50: 67 */       this.builder.setJobId(convertToProtoFormat(this.jobId));
/*  51:    */     }
/*  52: 69 */     if (this.amInfos != null) {
/*  53: 70 */       addAMInfosToProto();
/*  54:    */     }
/*  55:    */   }
/*  56:    */   
/*  57:    */   private synchronized void mergeLocalToProto()
/*  58:    */   {
/*  59: 75 */     if (this.viaProto) {
/*  60: 76 */       maybeInitBuilder();
/*  61:    */     }
/*  62: 77 */     mergeLocalToBuilder();
/*  63: 78 */     this.proto = this.builder.build();
/*  64: 79 */     this.viaProto = true;
/*  65:    */   }
/*  66:    */   
/*  67:    */   private synchronized void maybeInitBuilder()
/*  68:    */   {
/*  69: 83 */     if ((this.viaProto) || (this.builder == null)) {
/*  70: 84 */       this.builder = MRProtos.JobReportProto.newBuilder(this.proto);
/*  71:    */     }
/*  72: 86 */     this.viaProto = false;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public synchronized JobId getJobId()
/*  76:    */   {
/*  77: 92 */     MRProtos.JobReportProtoOrBuilder p = this.viaProto ? this.proto : this.builder;
/*  78: 93 */     if (this.jobId != null) {
/*  79: 94 */       return this.jobId;
/*  80:    */     }
/*  81: 96 */     if (!p.hasJobId()) {
/*  82: 97 */       return null;
/*  83:    */     }
/*  84: 99 */     this.jobId = convertFromProtoFormat(p.getJobId());
/*  85:100 */     return this.jobId;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public synchronized void setJobId(JobId jobId)
/*  89:    */   {
/*  90:105 */     maybeInitBuilder();
/*  91:106 */     if (jobId == null) {
/*  92:107 */       this.builder.clearJobId();
/*  93:    */     }
/*  94:108 */     this.jobId = jobId;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public synchronized JobState getJobState()
/*  98:    */   {
/*  99:112 */     MRProtos.JobReportProtoOrBuilder p = this.viaProto ? this.proto : this.builder;
/* 100:113 */     if (!p.hasJobState()) {
/* 101:114 */       return null;
/* 102:    */     }
/* 103:116 */     return convertFromProtoFormat(p.getJobState());
/* 104:    */   }
/* 105:    */   
/* 106:    */   public synchronized void setJobState(JobState jobState)
/* 107:    */   {
/* 108:121 */     maybeInitBuilder();
/* 109:122 */     if (jobState == null)
/* 110:    */     {
/* 111:123 */       this.builder.clearJobState();
/* 112:124 */       return;
/* 113:    */     }
/* 114:126 */     this.builder.setJobState(convertToProtoFormat(jobState));
/* 115:    */   }
/* 116:    */   
/* 117:    */   public synchronized float getMapProgress()
/* 118:    */   {
/* 119:130 */     MRProtos.JobReportProtoOrBuilder p = this.viaProto ? this.proto : this.builder;
/* 120:131 */     return p.getMapProgress();
/* 121:    */   }
/* 122:    */   
/* 123:    */   public synchronized void setMapProgress(float mapProgress)
/* 124:    */   {
/* 125:136 */     maybeInitBuilder();
/* 126:137 */     this.builder.setMapProgress(mapProgress);
/* 127:    */   }
/* 128:    */   
/* 129:    */   public synchronized float getReduceProgress()
/* 130:    */   {
/* 131:141 */     MRProtos.JobReportProtoOrBuilder p = this.viaProto ? this.proto : this.builder;
/* 132:142 */     return p.getReduceProgress();
/* 133:    */   }
/* 134:    */   
/* 135:    */   public synchronized void setReduceProgress(float reduceProgress)
/* 136:    */   {
/* 137:147 */     maybeInitBuilder();
/* 138:148 */     this.builder.setReduceProgress(reduceProgress);
/* 139:    */   }
/* 140:    */   
/* 141:    */   public synchronized float getCleanupProgress()
/* 142:    */   {
/* 143:152 */     MRProtos.JobReportProtoOrBuilder p = this.viaProto ? this.proto : this.builder;
/* 144:153 */     return p.getCleanupProgress();
/* 145:    */   }
/* 146:    */   
/* 147:    */   public synchronized void setCleanupProgress(float cleanupProgress)
/* 148:    */   {
/* 149:158 */     maybeInitBuilder();
/* 150:159 */     this.builder.setCleanupProgress(cleanupProgress);
/* 151:    */   }
/* 152:    */   
/* 153:    */   public synchronized float getSetupProgress()
/* 154:    */   {
/* 155:163 */     MRProtos.JobReportProtoOrBuilder p = this.viaProto ? this.proto : this.builder;
/* 156:164 */     return p.getSetupProgress();
/* 157:    */   }
/* 158:    */   
/* 159:    */   public synchronized void setSetupProgress(float setupProgress)
/* 160:    */   {
/* 161:169 */     maybeInitBuilder();
/* 162:170 */     this.builder.setSetupProgress(setupProgress);
/* 163:    */   }
/* 164:    */   
/* 165:    */   public synchronized long getSubmitTime()
/* 166:    */   {
/* 167:175 */     MRProtos.JobReportProtoOrBuilder p = this.viaProto ? this.proto : this.builder;
/* 168:176 */     return p.getSubmitTime();
/* 169:    */   }
/* 170:    */   
/* 171:    */   public synchronized void setSubmitTime(long submitTime)
/* 172:    */   {
/* 173:181 */     maybeInitBuilder();
/* 174:182 */     this.builder.setSubmitTime(submitTime);
/* 175:    */   }
/* 176:    */   
/* 177:    */   public synchronized long getStartTime()
/* 178:    */   {
/* 179:187 */     MRProtos.JobReportProtoOrBuilder p = this.viaProto ? this.proto : this.builder;
/* 180:188 */     return p.getStartTime();
/* 181:    */   }
/* 182:    */   
/* 183:    */   public synchronized void setStartTime(long startTime)
/* 184:    */   {
/* 185:193 */     maybeInitBuilder();
/* 186:194 */     this.builder.setStartTime(startTime);
/* 187:    */   }
/* 188:    */   
/* 189:    */   public synchronized long getFinishTime()
/* 190:    */   {
/* 191:198 */     MRProtos.JobReportProtoOrBuilder p = this.viaProto ? this.proto : this.builder;
/* 192:199 */     return p.getFinishTime();
/* 193:    */   }
/* 194:    */   
/* 195:    */   public synchronized void setFinishTime(long finishTime)
/* 196:    */   {
/* 197:204 */     maybeInitBuilder();
/* 198:205 */     this.builder.setFinishTime(finishTime);
/* 199:    */   }
/* 200:    */   
/* 201:    */   public synchronized String getUser()
/* 202:    */   {
/* 203:210 */     MRProtos.JobReportProtoOrBuilder p = this.viaProto ? this.proto : this.builder;
/* 204:211 */     return p.getUser();
/* 205:    */   }
/* 206:    */   
/* 207:    */   public synchronized void setUser(String user)
/* 208:    */   {
/* 209:216 */     maybeInitBuilder();
/* 210:217 */     this.builder.setUser(user);
/* 211:    */   }
/* 212:    */   
/* 213:    */   public synchronized String getJobName()
/* 214:    */   {
/* 215:222 */     MRProtos.JobReportProtoOrBuilder p = this.viaProto ? this.proto : this.builder;
/* 216:223 */     return p.getJobName();
/* 217:    */   }
/* 218:    */   
/* 219:    */   public synchronized void setJobName(String jobName)
/* 220:    */   {
/* 221:228 */     maybeInitBuilder();
/* 222:229 */     this.builder.setJobName(jobName);
/* 223:    */   }
/* 224:    */   
/* 225:    */   public synchronized String getTrackingUrl()
/* 226:    */   {
/* 227:234 */     MRProtos.JobReportProtoOrBuilder p = this.viaProto ? this.proto : this.builder;
/* 228:235 */     return p.getTrackingUrl();
/* 229:    */   }
/* 230:    */   
/* 231:    */   public synchronized void setTrackingUrl(String trackingUrl)
/* 232:    */   {
/* 233:240 */     maybeInitBuilder();
/* 234:241 */     this.builder.setTrackingUrl(trackingUrl);
/* 235:    */   }
/* 236:    */   
/* 237:    */   public synchronized String getDiagnostics()
/* 238:    */   {
/* 239:246 */     MRProtos.JobReportProtoOrBuilder p = this.viaProto ? this.proto : this.builder;
/* 240:247 */     return p.getDiagnostics();
/* 241:    */   }
/* 242:    */   
/* 243:    */   public synchronized void setDiagnostics(String diagnostics)
/* 244:    */   {
/* 245:252 */     maybeInitBuilder();
/* 246:253 */     this.builder.setDiagnostics(diagnostics);
/* 247:    */   }
/* 248:    */   
/* 249:    */   public synchronized String getJobFile()
/* 250:    */   {
/* 251:258 */     MRProtos.JobReportProtoOrBuilder p = this.viaProto ? this.proto : this.builder;
/* 252:259 */     return p.getJobFile();
/* 253:    */   }
/* 254:    */   
/* 255:    */   public synchronized void setJobFile(String jobFile)
/* 256:    */   {
/* 257:264 */     maybeInitBuilder();
/* 258:265 */     this.builder.setJobFile(jobFile);
/* 259:    */   }
/* 260:    */   
/* 261:    */   public synchronized List<AMInfo> getAMInfos()
/* 262:    */   {
/* 263:270 */     initAMInfos();
/* 264:271 */     return this.amInfos;
/* 265:    */   }
/* 266:    */   
/* 267:    */   public synchronized void setAMInfos(List<AMInfo> amInfos)
/* 268:    */   {
/* 269:276 */     maybeInitBuilder();
/* 270:277 */     if (amInfos == null)
/* 271:    */     {
/* 272:278 */       this.builder.clearAmInfos();
/* 273:279 */       this.amInfos = null;
/* 274:280 */       return;
/* 275:    */     }
/* 276:282 */     initAMInfos();
/* 277:283 */     this.amInfos.clear();
/* 278:284 */     this.amInfos.addAll(amInfos);
/* 279:    */   }
/* 280:    */   
/* 281:    */   private synchronized void initAMInfos()
/* 282:    */   {
/* 283:289 */     if (this.amInfos != null) {
/* 284:290 */       return;
/* 285:    */     }
/* 286:292 */     MRProtos.JobReportProtoOrBuilder p = this.viaProto ? this.proto : this.builder;
/* 287:293 */     List<MRProtos.AMInfoProto> list = p.getAmInfosList();
/* 288:    */     
/* 289:295 */     this.amInfos = new ArrayList();
/* 290:297 */     for (MRProtos.AMInfoProto amInfoProto : list) {
/* 291:298 */       this.amInfos.add(convertFromProtoFormat(amInfoProto));
/* 292:    */     }
/* 293:    */   }
/* 294:    */   
/* 295:    */   private synchronized void addAMInfosToProto()
/* 296:    */   {
/* 297:303 */     maybeInitBuilder();
/* 298:304 */     this.builder.clearAmInfos();
/* 299:305 */     if (this.amInfos == null) {
/* 300:306 */       return;
/* 301:    */     }
/* 302:307 */     for (AMInfo amInfo : this.amInfos) {
/* 303:308 */       this.builder.addAmInfos(convertToProtoFormat(amInfo));
/* 304:    */     }
/* 305:    */   }
/* 306:    */   
/* 307:    */   private AMInfoPBImpl convertFromProtoFormat(MRProtos.AMInfoProto p)
/* 308:    */   {
/* 309:313 */     return new AMInfoPBImpl(p);
/* 310:    */   }
/* 311:    */   
/* 312:    */   private MRProtos.AMInfoProto convertToProtoFormat(AMInfo t)
/* 313:    */   {
/* 314:317 */     return ((AMInfoPBImpl)t).getProto();
/* 315:    */   }
/* 316:    */   
/* 317:    */   private JobIdPBImpl convertFromProtoFormat(MRProtos.JobIdProto p)
/* 318:    */   {
/* 319:321 */     return new JobIdPBImpl(p);
/* 320:    */   }
/* 321:    */   
/* 322:    */   private MRProtos.JobIdProto convertToProtoFormat(JobId t)
/* 323:    */   {
/* 324:325 */     return ((JobIdPBImpl)t).getProto();
/* 325:    */   }
/* 326:    */   
/* 327:    */   private MRProtos.JobStateProto convertToProtoFormat(JobState e)
/* 328:    */   {
/* 329:329 */     return MRProtoUtils.convertToProtoFormat(e);
/* 330:    */   }
/* 331:    */   
/* 332:    */   private JobState convertFromProtoFormat(MRProtos.JobStateProto e)
/* 333:    */   {
/* 334:333 */     return MRProtoUtils.convertFromProtoFormat(e);
/* 335:    */   }
/* 336:    */   
/* 337:    */   public synchronized boolean isUber()
/* 338:    */   {
/* 339:338 */     MRProtos.JobReportProtoOrBuilder p = this.viaProto ? this.proto : this.builder;
/* 340:339 */     return p.getIsUber();
/* 341:    */   }
/* 342:    */   
/* 343:    */   public synchronized void setIsUber(boolean isUber)
/* 344:    */   {
/* 345:344 */     maybeInitBuilder();
/* 346:345 */     this.builder.setIsUber(isUber);
/* 347:    */   }
/* 348:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-common-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.v2.api.records.impl.pb.JobReportPBImpl
 * JD-Core Version:    0.7.0.1
 */