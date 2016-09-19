/*   1:    */ package org.apache.hadoop.mapreduce.jobhistory;
/*   2:    */ 
/*   3:    */ import org.apache.avro.AvroRuntimeException;
/*   4:    */ import org.apache.avro.Schema;
/*   5:    */ import org.apache.avro.Schema.Field;
/*   6:    */ import org.apache.avro.Schema.Parser;
/*   7:    */ import org.apache.avro.data.RecordBuilder;
/*   8:    */ import org.apache.avro.generic.GenericData;
/*   9:    */ import org.apache.avro.specific.AvroGenerated;
/*  10:    */ import org.apache.avro.specific.SpecificRecord;
/*  11:    */ import org.apache.avro.specific.SpecificRecordBase;
/*  12:    */ import org.apache.avro.specific.SpecificRecordBuilderBase;
/*  13:    */ 
/*  14:    */ @AvroGenerated
/*  15:    */ public class JobInited
/*  16:    */   extends SpecificRecordBase
/*  17:    */   implements SpecificRecord
/*  18:    */ {
/*  19: 10 */   public static final Schema SCHEMA$ = new Schema.Parser().parse("{\"type\":\"record\",\"name\":\"JobInited\",\"namespace\":\"org.apache.hadoop.mapreduce.jobhistory\",\"fields\":[{\"name\":\"jobid\",\"type\":\"string\"},{\"name\":\"launchTime\",\"type\":\"long\"},{\"name\":\"totalMaps\",\"type\":\"int\"},{\"name\":\"totalReduces\",\"type\":\"int\"},{\"name\":\"jobStatus\",\"type\":\"string\"},{\"name\":\"uberized\",\"type\":\"boolean\"}]}");
/*  20:    */   @Deprecated
/*  21:    */   public CharSequence jobid;
/*  22:    */   @Deprecated
/*  23:    */   public long launchTime;
/*  24:    */   @Deprecated
/*  25:    */   public int totalMaps;
/*  26:    */   @Deprecated
/*  27:    */   public int totalReduces;
/*  28:    */   @Deprecated
/*  29:    */   public CharSequence jobStatus;
/*  30:    */   @Deprecated
/*  31:    */   public boolean uberized;
/*  32:    */   
/*  33:    */   public static Schema getClassSchema()
/*  34:    */   {
/*  35: 11 */     return SCHEMA$;
/*  36:    */   }
/*  37:    */   
/*  38:    */   public JobInited() {}
/*  39:    */   
/*  40:    */   public JobInited(CharSequence jobid, Long launchTime, Integer totalMaps, Integer totalReduces, CharSequence jobStatus, Boolean uberized)
/*  41:    */   {
/*  42: 28 */     this.jobid = jobid;
/*  43: 29 */     this.launchTime = launchTime.longValue();
/*  44: 30 */     this.totalMaps = totalMaps.intValue();
/*  45: 31 */     this.totalReduces = totalReduces.intValue();
/*  46: 32 */     this.jobStatus = jobStatus;
/*  47: 33 */     this.uberized = uberized.booleanValue();
/*  48:    */   }
/*  49:    */   
/*  50:    */   public Schema getSchema()
/*  51:    */   {
/*  52: 36 */     return SCHEMA$;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public Object get(int field$)
/*  56:    */   {
/*  57: 39 */     switch (field$)
/*  58:    */     {
/*  59:    */     case 0: 
/*  60: 40 */       return this.jobid;
/*  61:    */     case 1: 
/*  62: 41 */       return Long.valueOf(this.launchTime);
/*  63:    */     case 2: 
/*  64: 42 */       return Integer.valueOf(this.totalMaps);
/*  65:    */     case 3: 
/*  66: 43 */       return Integer.valueOf(this.totalReduces);
/*  67:    */     case 4: 
/*  68: 44 */       return this.jobStatus;
/*  69:    */     case 5: 
/*  70: 45 */       return Boolean.valueOf(this.uberized);
/*  71:    */     }
/*  72: 46 */     throw new AvroRuntimeException("Bad index");
/*  73:    */   }
/*  74:    */   
/*  75:    */   public void put(int field$, Object value$)
/*  76:    */   {
/*  77: 52 */     switch (field$)
/*  78:    */     {
/*  79:    */     case 0: 
/*  80: 53 */       this.jobid = ((CharSequence)value$); break;
/*  81:    */     case 1: 
/*  82: 54 */       this.launchTime = ((Long)value$).longValue(); break;
/*  83:    */     case 2: 
/*  84: 55 */       this.totalMaps = ((Integer)value$).intValue(); break;
/*  85:    */     case 3: 
/*  86: 56 */       this.totalReduces = ((Integer)value$).intValue(); break;
/*  87:    */     case 4: 
/*  88: 57 */       this.jobStatus = ((CharSequence)value$); break;
/*  89:    */     case 5: 
/*  90: 58 */       this.uberized = ((Boolean)value$).booleanValue(); break;
/*  91:    */     default: 
/*  92: 59 */       throw new AvroRuntimeException("Bad index");
/*  93:    */     }
/*  94:    */   }
/*  95:    */   
/*  96:    */   public CharSequence getJobid()
/*  97:    */   {
/*  98: 67 */     return this.jobid;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public void setJobid(CharSequence value)
/* 102:    */   {
/* 103: 75 */     this.jobid = value;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public Long getLaunchTime()
/* 107:    */   {
/* 108: 82 */     return Long.valueOf(this.launchTime);
/* 109:    */   }
/* 110:    */   
/* 111:    */   public void setLaunchTime(Long value)
/* 112:    */   {
/* 113: 90 */     this.launchTime = value.longValue();
/* 114:    */   }
/* 115:    */   
/* 116:    */   public Integer getTotalMaps()
/* 117:    */   {
/* 118: 97 */     return Integer.valueOf(this.totalMaps);
/* 119:    */   }
/* 120:    */   
/* 121:    */   public void setTotalMaps(Integer value)
/* 122:    */   {
/* 123:105 */     this.totalMaps = value.intValue();
/* 124:    */   }
/* 125:    */   
/* 126:    */   public Integer getTotalReduces()
/* 127:    */   {
/* 128:112 */     return Integer.valueOf(this.totalReduces);
/* 129:    */   }
/* 130:    */   
/* 131:    */   public void setTotalReduces(Integer value)
/* 132:    */   {
/* 133:120 */     this.totalReduces = value.intValue();
/* 134:    */   }
/* 135:    */   
/* 136:    */   public CharSequence getJobStatus()
/* 137:    */   {
/* 138:127 */     return this.jobStatus;
/* 139:    */   }
/* 140:    */   
/* 141:    */   public void setJobStatus(CharSequence value)
/* 142:    */   {
/* 143:135 */     this.jobStatus = value;
/* 144:    */   }
/* 145:    */   
/* 146:    */   public Boolean getUberized()
/* 147:    */   {
/* 148:142 */     return Boolean.valueOf(this.uberized);
/* 149:    */   }
/* 150:    */   
/* 151:    */   public void setUberized(Boolean value)
/* 152:    */   {
/* 153:150 */     this.uberized = value.booleanValue();
/* 154:    */   }
/* 155:    */   
/* 156:    */   public static Builder newBuilder()
/* 157:    */   {
/* 158:155 */     return new Builder(null);
/* 159:    */   }
/* 160:    */   
/* 161:    */   public static Builder newBuilder(Builder other)
/* 162:    */   {
/* 163:160 */     return new Builder(other, null);
/* 164:    */   }
/* 165:    */   
/* 166:    */   public static Builder newBuilder(JobInited other)
/* 167:    */   {
/* 168:165 */     return new Builder(other, null);
/* 169:    */   }
/* 170:    */   
/* 171:    */   public static class Builder
/* 172:    */     extends SpecificRecordBuilderBase<JobInited>
/* 173:    */     implements RecordBuilder<JobInited>
/* 174:    */   {
/* 175:    */     private CharSequence jobid;
/* 176:    */     private long launchTime;
/* 177:    */     private int totalMaps;
/* 178:    */     private int totalReduces;
/* 179:    */     private CharSequence jobStatus;
/* 180:    */     private boolean uberized;
/* 181:    */     
/* 182:    */     private Builder()
/* 183:    */     {
/* 184:183 */       super();
/* 185:    */     }
/* 186:    */     
/* 187:    */     private Builder(Builder other)
/* 188:    */     {
/* 189:188 */       super();
/* 190:    */     }
/* 191:    */     
/* 192:    */     private Builder(JobInited other)
/* 193:    */     {
/* 194:193 */       super();
/* 195:194 */       if (isValidValue(fields()[0], other.jobid))
/* 196:    */       {
/* 197:195 */         this.jobid = ((CharSequence)data().deepCopy(fields()[0].schema(), other.jobid));
/* 198:196 */         fieldSetFlags()[0] = 1;
/* 199:    */       }
/* 200:198 */       if (isValidValue(fields()[1], Long.valueOf(other.launchTime)))
/* 201:    */       {
/* 202:199 */         this.launchTime = ((Long)data().deepCopy(fields()[1].schema(), Long.valueOf(other.launchTime))).longValue();
/* 203:200 */         fieldSetFlags()[1] = 1;
/* 204:    */       }
/* 205:202 */       if (isValidValue(fields()[2], Integer.valueOf(other.totalMaps)))
/* 206:    */       {
/* 207:203 */         this.totalMaps = ((Integer)data().deepCopy(fields()[2].schema(), Integer.valueOf(other.totalMaps))).intValue();
/* 208:204 */         fieldSetFlags()[2] = 1;
/* 209:    */       }
/* 210:206 */       if (isValidValue(fields()[3], Integer.valueOf(other.totalReduces)))
/* 211:    */       {
/* 212:207 */         this.totalReduces = ((Integer)data().deepCopy(fields()[3].schema(), Integer.valueOf(other.totalReduces))).intValue();
/* 213:208 */         fieldSetFlags()[3] = 1;
/* 214:    */       }
/* 215:210 */       if (isValidValue(fields()[4], other.jobStatus))
/* 216:    */       {
/* 217:211 */         this.jobStatus = ((CharSequence)data().deepCopy(fields()[4].schema(), other.jobStatus));
/* 218:212 */         fieldSetFlags()[4] = 1;
/* 219:    */       }
/* 220:214 */       if (isValidValue(fields()[5], Boolean.valueOf(other.uberized)))
/* 221:    */       {
/* 222:215 */         this.uberized = ((Boolean)data().deepCopy(fields()[5].schema(), Boolean.valueOf(other.uberized))).booleanValue();
/* 223:216 */         fieldSetFlags()[5] = 1;
/* 224:    */       }
/* 225:    */     }
/* 226:    */     
/* 227:    */     public CharSequence getJobid()
/* 228:    */     {
/* 229:222 */       return this.jobid;
/* 230:    */     }
/* 231:    */     
/* 232:    */     public Builder setJobid(CharSequence value)
/* 233:    */     {
/* 234:227 */       validate(fields()[0], value);
/* 235:228 */       this.jobid = value;
/* 236:229 */       fieldSetFlags()[0] = 1;
/* 237:230 */       return this;
/* 238:    */     }
/* 239:    */     
/* 240:    */     public boolean hasJobid()
/* 241:    */     {
/* 242:235 */       return fieldSetFlags()[0];
/* 243:    */     }
/* 244:    */     
/* 245:    */     public Builder clearJobid()
/* 246:    */     {
/* 247:240 */       this.jobid = null;
/* 248:241 */       fieldSetFlags()[0] = 0;
/* 249:242 */       return this;
/* 250:    */     }
/* 251:    */     
/* 252:    */     public Long getLaunchTime()
/* 253:    */     {
/* 254:247 */       return Long.valueOf(this.launchTime);
/* 255:    */     }
/* 256:    */     
/* 257:    */     public Builder setLaunchTime(long value)
/* 258:    */     {
/* 259:252 */       validate(fields()[1], Long.valueOf(value));
/* 260:253 */       this.launchTime = value;
/* 261:254 */       fieldSetFlags()[1] = 1;
/* 262:255 */       return this;
/* 263:    */     }
/* 264:    */     
/* 265:    */     public boolean hasLaunchTime()
/* 266:    */     {
/* 267:260 */       return fieldSetFlags()[1];
/* 268:    */     }
/* 269:    */     
/* 270:    */     public Builder clearLaunchTime()
/* 271:    */     {
/* 272:265 */       fieldSetFlags()[1] = 0;
/* 273:266 */       return this;
/* 274:    */     }
/* 275:    */     
/* 276:    */     public Integer getTotalMaps()
/* 277:    */     {
/* 278:271 */       return Integer.valueOf(this.totalMaps);
/* 279:    */     }
/* 280:    */     
/* 281:    */     public Builder setTotalMaps(int value)
/* 282:    */     {
/* 283:276 */       validate(fields()[2], Integer.valueOf(value));
/* 284:277 */       this.totalMaps = value;
/* 285:278 */       fieldSetFlags()[2] = 1;
/* 286:279 */       return this;
/* 287:    */     }
/* 288:    */     
/* 289:    */     public boolean hasTotalMaps()
/* 290:    */     {
/* 291:284 */       return fieldSetFlags()[2];
/* 292:    */     }
/* 293:    */     
/* 294:    */     public Builder clearTotalMaps()
/* 295:    */     {
/* 296:289 */       fieldSetFlags()[2] = 0;
/* 297:290 */       return this;
/* 298:    */     }
/* 299:    */     
/* 300:    */     public Integer getTotalReduces()
/* 301:    */     {
/* 302:295 */       return Integer.valueOf(this.totalReduces);
/* 303:    */     }
/* 304:    */     
/* 305:    */     public Builder setTotalReduces(int value)
/* 306:    */     {
/* 307:300 */       validate(fields()[3], Integer.valueOf(value));
/* 308:301 */       this.totalReduces = value;
/* 309:302 */       fieldSetFlags()[3] = 1;
/* 310:303 */       return this;
/* 311:    */     }
/* 312:    */     
/* 313:    */     public boolean hasTotalReduces()
/* 314:    */     {
/* 315:308 */       return fieldSetFlags()[3];
/* 316:    */     }
/* 317:    */     
/* 318:    */     public Builder clearTotalReduces()
/* 319:    */     {
/* 320:313 */       fieldSetFlags()[3] = 0;
/* 321:314 */       return this;
/* 322:    */     }
/* 323:    */     
/* 324:    */     public CharSequence getJobStatus()
/* 325:    */     {
/* 326:319 */       return this.jobStatus;
/* 327:    */     }
/* 328:    */     
/* 329:    */     public Builder setJobStatus(CharSequence value)
/* 330:    */     {
/* 331:324 */       validate(fields()[4], value);
/* 332:325 */       this.jobStatus = value;
/* 333:326 */       fieldSetFlags()[4] = 1;
/* 334:327 */       return this;
/* 335:    */     }
/* 336:    */     
/* 337:    */     public boolean hasJobStatus()
/* 338:    */     {
/* 339:332 */       return fieldSetFlags()[4];
/* 340:    */     }
/* 341:    */     
/* 342:    */     public Builder clearJobStatus()
/* 343:    */     {
/* 344:337 */       this.jobStatus = null;
/* 345:338 */       fieldSetFlags()[4] = 0;
/* 346:339 */       return this;
/* 347:    */     }
/* 348:    */     
/* 349:    */     public Boolean getUberized()
/* 350:    */     {
/* 351:344 */       return Boolean.valueOf(this.uberized);
/* 352:    */     }
/* 353:    */     
/* 354:    */     public Builder setUberized(boolean value)
/* 355:    */     {
/* 356:349 */       validate(fields()[5], Boolean.valueOf(value));
/* 357:350 */       this.uberized = value;
/* 358:351 */       fieldSetFlags()[5] = 1;
/* 359:352 */       return this;
/* 360:    */     }
/* 361:    */     
/* 362:    */     public boolean hasUberized()
/* 363:    */     {
/* 364:357 */       return fieldSetFlags()[5];
/* 365:    */     }
/* 366:    */     
/* 367:    */     public Builder clearUberized()
/* 368:    */     {
/* 369:362 */       fieldSetFlags()[5] = 0;
/* 370:363 */       return this;
/* 371:    */     }
/* 372:    */     
/* 373:    */     public JobInited build()
/* 374:    */     {
/* 375:    */       try
/* 376:    */       {
/* 377:369 */         JobInited record = new JobInited();
/* 378:370 */         record.jobid = (fieldSetFlags()[0] != 0 ? this.jobid : (CharSequence)defaultValue(fields()[0]));
/* 379:371 */         record.launchTime = (fieldSetFlags()[1] != 0 ? this.launchTime : ((Long)defaultValue(fields()[1])).longValue());
/* 380:372 */         record.totalMaps = (fieldSetFlags()[2] != 0 ? this.totalMaps : ((Integer)defaultValue(fields()[2])).intValue());
/* 381:373 */         record.totalReduces = (fieldSetFlags()[3] != 0 ? this.totalReduces : ((Integer)defaultValue(fields()[3])).intValue());
/* 382:374 */         record.jobStatus = (fieldSetFlags()[4] != 0 ? this.jobStatus : (CharSequence)defaultValue(fields()[4]));
/* 383:375 */         record.uberized = (fieldSetFlags()[5] != 0 ? this.uberized : ((Boolean)defaultValue(fields()[5])).booleanValue());
/* 384:376 */         return record;
/* 385:    */       }
/* 386:    */       catch (Exception e)
/* 387:    */       {
/* 388:378 */         throw new AvroRuntimeException(e);
/* 389:    */       }
/* 390:    */     }
/* 391:    */   }
/* 392:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.jobhistory.JobInited
 * JD-Core Version:    0.7.0.1
 */