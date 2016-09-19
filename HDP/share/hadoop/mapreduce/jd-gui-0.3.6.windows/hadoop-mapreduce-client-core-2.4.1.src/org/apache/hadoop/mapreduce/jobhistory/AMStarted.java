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
/*  15:    */ public class AMStarted
/*  16:    */   extends SpecificRecordBase
/*  17:    */   implements SpecificRecord
/*  18:    */ {
/*  19: 10 */   public static final Schema SCHEMA$ = new Schema.Parser().parse("{\"type\":\"record\",\"name\":\"AMStarted\",\"namespace\":\"org.apache.hadoop.mapreduce.jobhistory\",\"fields\":[{\"name\":\"applicationAttemptId\",\"type\":\"string\"},{\"name\":\"startTime\",\"type\":\"long\"},{\"name\":\"containerId\",\"type\":\"string\"},{\"name\":\"nodeManagerHost\",\"type\":\"string\"},{\"name\":\"nodeManagerPort\",\"type\":\"int\"},{\"name\":\"nodeManagerHttpPort\",\"type\":\"int\"}]}");
/*  20:    */   @Deprecated
/*  21:    */   public CharSequence applicationAttemptId;
/*  22:    */   @Deprecated
/*  23:    */   public long startTime;
/*  24:    */   @Deprecated
/*  25:    */   public CharSequence containerId;
/*  26:    */   @Deprecated
/*  27:    */   public CharSequence nodeManagerHost;
/*  28:    */   @Deprecated
/*  29:    */   public int nodeManagerPort;
/*  30:    */   @Deprecated
/*  31:    */   public int nodeManagerHttpPort;
/*  32:    */   
/*  33:    */   public static Schema getClassSchema()
/*  34:    */   {
/*  35: 11 */     return SCHEMA$;
/*  36:    */   }
/*  37:    */   
/*  38:    */   public AMStarted() {}
/*  39:    */   
/*  40:    */   public AMStarted(CharSequence applicationAttemptId, Long startTime, CharSequence containerId, CharSequence nodeManagerHost, Integer nodeManagerPort, Integer nodeManagerHttpPort)
/*  41:    */   {
/*  42: 28 */     this.applicationAttemptId = applicationAttemptId;
/*  43: 29 */     this.startTime = startTime.longValue();
/*  44: 30 */     this.containerId = containerId;
/*  45: 31 */     this.nodeManagerHost = nodeManagerHost;
/*  46: 32 */     this.nodeManagerPort = nodeManagerPort.intValue();
/*  47: 33 */     this.nodeManagerHttpPort = nodeManagerHttpPort.intValue();
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
/*  60: 40 */       return this.applicationAttemptId;
/*  61:    */     case 1: 
/*  62: 41 */       return Long.valueOf(this.startTime);
/*  63:    */     case 2: 
/*  64: 42 */       return this.containerId;
/*  65:    */     case 3: 
/*  66: 43 */       return this.nodeManagerHost;
/*  67:    */     case 4: 
/*  68: 44 */       return Integer.valueOf(this.nodeManagerPort);
/*  69:    */     case 5: 
/*  70: 45 */       return Integer.valueOf(this.nodeManagerHttpPort);
/*  71:    */     }
/*  72: 46 */     throw new AvroRuntimeException("Bad index");
/*  73:    */   }
/*  74:    */   
/*  75:    */   public void put(int field$, Object value$)
/*  76:    */   {
/*  77: 52 */     switch (field$)
/*  78:    */     {
/*  79:    */     case 0: 
/*  80: 53 */       this.applicationAttemptId = ((CharSequence)value$); break;
/*  81:    */     case 1: 
/*  82: 54 */       this.startTime = ((Long)value$).longValue(); break;
/*  83:    */     case 2: 
/*  84: 55 */       this.containerId = ((CharSequence)value$); break;
/*  85:    */     case 3: 
/*  86: 56 */       this.nodeManagerHost = ((CharSequence)value$); break;
/*  87:    */     case 4: 
/*  88: 57 */       this.nodeManagerPort = ((Integer)value$).intValue(); break;
/*  89:    */     case 5: 
/*  90: 58 */       this.nodeManagerHttpPort = ((Integer)value$).intValue(); break;
/*  91:    */     default: 
/*  92: 59 */       throw new AvroRuntimeException("Bad index");
/*  93:    */     }
/*  94:    */   }
/*  95:    */   
/*  96:    */   public CharSequence getApplicationAttemptId()
/*  97:    */   {
/*  98: 67 */     return this.applicationAttemptId;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public void setApplicationAttemptId(CharSequence value)
/* 102:    */   {
/* 103: 75 */     this.applicationAttemptId = value;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public Long getStartTime()
/* 107:    */   {
/* 108: 82 */     return Long.valueOf(this.startTime);
/* 109:    */   }
/* 110:    */   
/* 111:    */   public void setStartTime(Long value)
/* 112:    */   {
/* 113: 90 */     this.startTime = value.longValue();
/* 114:    */   }
/* 115:    */   
/* 116:    */   public CharSequence getContainerId()
/* 117:    */   {
/* 118: 97 */     return this.containerId;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public void setContainerId(CharSequence value)
/* 122:    */   {
/* 123:105 */     this.containerId = value;
/* 124:    */   }
/* 125:    */   
/* 126:    */   public CharSequence getNodeManagerHost()
/* 127:    */   {
/* 128:112 */     return this.nodeManagerHost;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public void setNodeManagerHost(CharSequence value)
/* 132:    */   {
/* 133:120 */     this.nodeManagerHost = value;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public Integer getNodeManagerPort()
/* 137:    */   {
/* 138:127 */     return Integer.valueOf(this.nodeManagerPort);
/* 139:    */   }
/* 140:    */   
/* 141:    */   public void setNodeManagerPort(Integer value)
/* 142:    */   {
/* 143:135 */     this.nodeManagerPort = value.intValue();
/* 144:    */   }
/* 145:    */   
/* 146:    */   public Integer getNodeManagerHttpPort()
/* 147:    */   {
/* 148:142 */     return Integer.valueOf(this.nodeManagerHttpPort);
/* 149:    */   }
/* 150:    */   
/* 151:    */   public void setNodeManagerHttpPort(Integer value)
/* 152:    */   {
/* 153:150 */     this.nodeManagerHttpPort = value.intValue();
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
/* 166:    */   public static Builder newBuilder(AMStarted other)
/* 167:    */   {
/* 168:165 */     return new Builder(other, null);
/* 169:    */   }
/* 170:    */   
/* 171:    */   public static class Builder
/* 172:    */     extends SpecificRecordBuilderBase<AMStarted>
/* 173:    */     implements RecordBuilder<AMStarted>
/* 174:    */   {
/* 175:    */     private CharSequence applicationAttemptId;
/* 176:    */     private long startTime;
/* 177:    */     private CharSequence containerId;
/* 178:    */     private CharSequence nodeManagerHost;
/* 179:    */     private int nodeManagerPort;
/* 180:    */     private int nodeManagerHttpPort;
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
/* 192:    */     private Builder(AMStarted other)
/* 193:    */     {
/* 194:193 */       super();
/* 195:194 */       if (isValidValue(fields()[0], other.applicationAttemptId))
/* 196:    */       {
/* 197:195 */         this.applicationAttemptId = ((CharSequence)data().deepCopy(fields()[0].schema(), other.applicationAttemptId));
/* 198:196 */         fieldSetFlags()[0] = 1;
/* 199:    */       }
/* 200:198 */       if (isValidValue(fields()[1], Long.valueOf(other.startTime)))
/* 201:    */       {
/* 202:199 */         this.startTime = ((Long)data().deepCopy(fields()[1].schema(), Long.valueOf(other.startTime))).longValue();
/* 203:200 */         fieldSetFlags()[1] = 1;
/* 204:    */       }
/* 205:202 */       if (isValidValue(fields()[2], other.containerId))
/* 206:    */       {
/* 207:203 */         this.containerId = ((CharSequence)data().deepCopy(fields()[2].schema(), other.containerId));
/* 208:204 */         fieldSetFlags()[2] = 1;
/* 209:    */       }
/* 210:206 */       if (isValidValue(fields()[3], other.nodeManagerHost))
/* 211:    */       {
/* 212:207 */         this.nodeManagerHost = ((CharSequence)data().deepCopy(fields()[3].schema(), other.nodeManagerHost));
/* 213:208 */         fieldSetFlags()[3] = 1;
/* 214:    */       }
/* 215:210 */       if (isValidValue(fields()[4], Integer.valueOf(other.nodeManagerPort)))
/* 216:    */       {
/* 217:211 */         this.nodeManagerPort = ((Integer)data().deepCopy(fields()[4].schema(), Integer.valueOf(other.nodeManagerPort))).intValue();
/* 218:212 */         fieldSetFlags()[4] = 1;
/* 219:    */       }
/* 220:214 */       if (isValidValue(fields()[5], Integer.valueOf(other.nodeManagerHttpPort)))
/* 221:    */       {
/* 222:215 */         this.nodeManagerHttpPort = ((Integer)data().deepCopy(fields()[5].schema(), Integer.valueOf(other.nodeManagerHttpPort))).intValue();
/* 223:216 */         fieldSetFlags()[5] = 1;
/* 224:    */       }
/* 225:    */     }
/* 226:    */     
/* 227:    */     public CharSequence getApplicationAttemptId()
/* 228:    */     {
/* 229:222 */       return this.applicationAttemptId;
/* 230:    */     }
/* 231:    */     
/* 232:    */     public Builder setApplicationAttemptId(CharSequence value)
/* 233:    */     {
/* 234:227 */       validate(fields()[0], value);
/* 235:228 */       this.applicationAttemptId = value;
/* 236:229 */       fieldSetFlags()[0] = 1;
/* 237:230 */       return this;
/* 238:    */     }
/* 239:    */     
/* 240:    */     public boolean hasApplicationAttemptId()
/* 241:    */     {
/* 242:235 */       return fieldSetFlags()[0];
/* 243:    */     }
/* 244:    */     
/* 245:    */     public Builder clearApplicationAttemptId()
/* 246:    */     {
/* 247:240 */       this.applicationAttemptId = null;
/* 248:241 */       fieldSetFlags()[0] = 0;
/* 249:242 */       return this;
/* 250:    */     }
/* 251:    */     
/* 252:    */     public Long getStartTime()
/* 253:    */     {
/* 254:247 */       return Long.valueOf(this.startTime);
/* 255:    */     }
/* 256:    */     
/* 257:    */     public Builder setStartTime(long value)
/* 258:    */     {
/* 259:252 */       validate(fields()[1], Long.valueOf(value));
/* 260:253 */       this.startTime = value;
/* 261:254 */       fieldSetFlags()[1] = 1;
/* 262:255 */       return this;
/* 263:    */     }
/* 264:    */     
/* 265:    */     public boolean hasStartTime()
/* 266:    */     {
/* 267:260 */       return fieldSetFlags()[1];
/* 268:    */     }
/* 269:    */     
/* 270:    */     public Builder clearStartTime()
/* 271:    */     {
/* 272:265 */       fieldSetFlags()[1] = 0;
/* 273:266 */       return this;
/* 274:    */     }
/* 275:    */     
/* 276:    */     public CharSequence getContainerId()
/* 277:    */     {
/* 278:271 */       return this.containerId;
/* 279:    */     }
/* 280:    */     
/* 281:    */     public Builder setContainerId(CharSequence value)
/* 282:    */     {
/* 283:276 */       validate(fields()[2], value);
/* 284:277 */       this.containerId = value;
/* 285:278 */       fieldSetFlags()[2] = 1;
/* 286:279 */       return this;
/* 287:    */     }
/* 288:    */     
/* 289:    */     public boolean hasContainerId()
/* 290:    */     {
/* 291:284 */       return fieldSetFlags()[2];
/* 292:    */     }
/* 293:    */     
/* 294:    */     public Builder clearContainerId()
/* 295:    */     {
/* 296:289 */       this.containerId = null;
/* 297:290 */       fieldSetFlags()[2] = 0;
/* 298:291 */       return this;
/* 299:    */     }
/* 300:    */     
/* 301:    */     public CharSequence getNodeManagerHost()
/* 302:    */     {
/* 303:296 */       return this.nodeManagerHost;
/* 304:    */     }
/* 305:    */     
/* 306:    */     public Builder setNodeManagerHost(CharSequence value)
/* 307:    */     {
/* 308:301 */       validate(fields()[3], value);
/* 309:302 */       this.nodeManagerHost = value;
/* 310:303 */       fieldSetFlags()[3] = 1;
/* 311:304 */       return this;
/* 312:    */     }
/* 313:    */     
/* 314:    */     public boolean hasNodeManagerHost()
/* 315:    */     {
/* 316:309 */       return fieldSetFlags()[3];
/* 317:    */     }
/* 318:    */     
/* 319:    */     public Builder clearNodeManagerHost()
/* 320:    */     {
/* 321:314 */       this.nodeManagerHost = null;
/* 322:315 */       fieldSetFlags()[3] = 0;
/* 323:316 */       return this;
/* 324:    */     }
/* 325:    */     
/* 326:    */     public Integer getNodeManagerPort()
/* 327:    */     {
/* 328:321 */       return Integer.valueOf(this.nodeManagerPort);
/* 329:    */     }
/* 330:    */     
/* 331:    */     public Builder setNodeManagerPort(int value)
/* 332:    */     {
/* 333:326 */       validate(fields()[4], Integer.valueOf(value));
/* 334:327 */       this.nodeManagerPort = value;
/* 335:328 */       fieldSetFlags()[4] = 1;
/* 336:329 */       return this;
/* 337:    */     }
/* 338:    */     
/* 339:    */     public boolean hasNodeManagerPort()
/* 340:    */     {
/* 341:334 */       return fieldSetFlags()[4];
/* 342:    */     }
/* 343:    */     
/* 344:    */     public Builder clearNodeManagerPort()
/* 345:    */     {
/* 346:339 */       fieldSetFlags()[4] = 0;
/* 347:340 */       return this;
/* 348:    */     }
/* 349:    */     
/* 350:    */     public Integer getNodeManagerHttpPort()
/* 351:    */     {
/* 352:345 */       return Integer.valueOf(this.nodeManagerHttpPort);
/* 353:    */     }
/* 354:    */     
/* 355:    */     public Builder setNodeManagerHttpPort(int value)
/* 356:    */     {
/* 357:350 */       validate(fields()[5], Integer.valueOf(value));
/* 358:351 */       this.nodeManagerHttpPort = value;
/* 359:352 */       fieldSetFlags()[5] = 1;
/* 360:353 */       return this;
/* 361:    */     }
/* 362:    */     
/* 363:    */     public boolean hasNodeManagerHttpPort()
/* 364:    */     {
/* 365:358 */       return fieldSetFlags()[5];
/* 366:    */     }
/* 367:    */     
/* 368:    */     public Builder clearNodeManagerHttpPort()
/* 369:    */     {
/* 370:363 */       fieldSetFlags()[5] = 0;
/* 371:364 */       return this;
/* 372:    */     }
/* 373:    */     
/* 374:    */     public AMStarted build()
/* 375:    */     {
/* 376:    */       try
/* 377:    */       {
/* 378:370 */         AMStarted record = new AMStarted();
/* 379:371 */         record.applicationAttemptId = (fieldSetFlags()[0] != 0 ? this.applicationAttemptId : (CharSequence)defaultValue(fields()[0]));
/* 380:372 */         record.startTime = (fieldSetFlags()[1] != 0 ? this.startTime : ((Long)defaultValue(fields()[1])).longValue());
/* 381:373 */         record.containerId = (fieldSetFlags()[2] != 0 ? this.containerId : (CharSequence)defaultValue(fields()[2]));
/* 382:374 */         record.nodeManagerHost = (fieldSetFlags()[3] != 0 ? this.nodeManagerHost : (CharSequence)defaultValue(fields()[3]));
/* 383:375 */         record.nodeManagerPort = (fieldSetFlags()[4] != 0 ? this.nodeManagerPort : ((Integer)defaultValue(fields()[4])).intValue());
/* 384:376 */         record.nodeManagerHttpPort = (fieldSetFlags()[5] != 0 ? this.nodeManagerHttpPort : ((Integer)defaultValue(fields()[5])).intValue());
/* 385:377 */         return record;
/* 386:    */       }
/* 387:    */       catch (Exception e)
/* 388:    */       {
/* 389:379 */         throw new AvroRuntimeException(e);
/* 390:    */       }
/* 391:    */     }
/* 392:    */   }
/* 393:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.jobhistory.AMStarted
 * JD-Core Version:    0.7.0.1
 */