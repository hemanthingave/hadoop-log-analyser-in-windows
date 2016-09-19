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
/*  15:    */ public class JobInfoChange
/*  16:    */   extends SpecificRecordBase
/*  17:    */   implements SpecificRecord
/*  18:    */ {
/*  19: 10 */   public static final Schema SCHEMA$ = new Schema.Parser().parse("{\"type\":\"record\",\"name\":\"JobInfoChange\",\"namespace\":\"org.apache.hadoop.mapreduce.jobhistory\",\"fields\":[{\"name\":\"jobid\",\"type\":\"string\"},{\"name\":\"submitTime\",\"type\":\"long\"},{\"name\":\"launchTime\",\"type\":\"long\"}]}");
/*  20:    */   @Deprecated
/*  21:    */   public CharSequence jobid;
/*  22:    */   @Deprecated
/*  23:    */   public long submitTime;
/*  24:    */   @Deprecated
/*  25:    */   public long launchTime;
/*  26:    */   
/*  27:    */   public static Schema getClassSchema()
/*  28:    */   {
/*  29: 11 */     return SCHEMA$;
/*  30:    */   }
/*  31:    */   
/*  32:    */   public JobInfoChange() {}
/*  33:    */   
/*  34:    */   public JobInfoChange(CharSequence jobid, Long submitTime, Long launchTime)
/*  35:    */   {
/*  36: 25 */     this.jobid = jobid;
/*  37: 26 */     this.submitTime = submitTime.longValue();
/*  38: 27 */     this.launchTime = launchTime.longValue();
/*  39:    */   }
/*  40:    */   
/*  41:    */   public Schema getSchema()
/*  42:    */   {
/*  43: 30 */     return SCHEMA$;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public Object get(int field$)
/*  47:    */   {
/*  48: 33 */     switch (field$)
/*  49:    */     {
/*  50:    */     case 0: 
/*  51: 34 */       return this.jobid;
/*  52:    */     case 1: 
/*  53: 35 */       return Long.valueOf(this.submitTime);
/*  54:    */     case 2: 
/*  55: 36 */       return Long.valueOf(this.launchTime);
/*  56:    */     }
/*  57: 37 */     throw new AvroRuntimeException("Bad index");
/*  58:    */   }
/*  59:    */   
/*  60:    */   public void put(int field$, Object value$)
/*  61:    */   {
/*  62: 43 */     switch (field$)
/*  63:    */     {
/*  64:    */     case 0: 
/*  65: 44 */       this.jobid = ((CharSequence)value$); break;
/*  66:    */     case 1: 
/*  67: 45 */       this.submitTime = ((Long)value$).longValue(); break;
/*  68:    */     case 2: 
/*  69: 46 */       this.launchTime = ((Long)value$).longValue(); break;
/*  70:    */     default: 
/*  71: 47 */       throw new AvroRuntimeException("Bad index");
/*  72:    */     }
/*  73:    */   }
/*  74:    */   
/*  75:    */   public CharSequence getJobid()
/*  76:    */   {
/*  77: 55 */     return this.jobid;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public void setJobid(CharSequence value)
/*  81:    */   {
/*  82: 63 */     this.jobid = value;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public Long getSubmitTime()
/*  86:    */   {
/*  87: 70 */     return Long.valueOf(this.submitTime);
/*  88:    */   }
/*  89:    */   
/*  90:    */   public void setSubmitTime(Long value)
/*  91:    */   {
/*  92: 78 */     this.submitTime = value.longValue();
/*  93:    */   }
/*  94:    */   
/*  95:    */   public Long getLaunchTime()
/*  96:    */   {
/*  97: 85 */     return Long.valueOf(this.launchTime);
/*  98:    */   }
/*  99:    */   
/* 100:    */   public void setLaunchTime(Long value)
/* 101:    */   {
/* 102: 93 */     this.launchTime = value.longValue();
/* 103:    */   }
/* 104:    */   
/* 105:    */   public static Builder newBuilder()
/* 106:    */   {
/* 107: 98 */     return new Builder(null);
/* 108:    */   }
/* 109:    */   
/* 110:    */   public static Builder newBuilder(Builder other)
/* 111:    */   {
/* 112:103 */     return new Builder(other, null);
/* 113:    */   }
/* 114:    */   
/* 115:    */   public static Builder newBuilder(JobInfoChange other)
/* 116:    */   {
/* 117:108 */     return new Builder(other, null);
/* 118:    */   }
/* 119:    */   
/* 120:    */   public static class Builder
/* 121:    */     extends SpecificRecordBuilderBase<JobInfoChange>
/* 122:    */     implements RecordBuilder<JobInfoChange>
/* 123:    */   {
/* 124:    */     private CharSequence jobid;
/* 125:    */     private long submitTime;
/* 126:    */     private long launchTime;
/* 127:    */     
/* 128:    */     private Builder()
/* 129:    */     {
/* 130:123 */       super();
/* 131:    */     }
/* 132:    */     
/* 133:    */     private Builder(Builder other)
/* 134:    */     {
/* 135:128 */       super();
/* 136:    */     }
/* 137:    */     
/* 138:    */     private Builder(JobInfoChange other)
/* 139:    */     {
/* 140:133 */       super();
/* 141:134 */       if (isValidValue(fields()[0], other.jobid))
/* 142:    */       {
/* 143:135 */         this.jobid = ((CharSequence)data().deepCopy(fields()[0].schema(), other.jobid));
/* 144:136 */         fieldSetFlags()[0] = 1;
/* 145:    */       }
/* 146:138 */       if (isValidValue(fields()[1], Long.valueOf(other.submitTime)))
/* 147:    */       {
/* 148:139 */         this.submitTime = ((Long)data().deepCopy(fields()[1].schema(), Long.valueOf(other.submitTime))).longValue();
/* 149:140 */         fieldSetFlags()[1] = 1;
/* 150:    */       }
/* 151:142 */       if (isValidValue(fields()[2], Long.valueOf(other.launchTime)))
/* 152:    */       {
/* 153:143 */         this.launchTime = ((Long)data().deepCopy(fields()[2].schema(), Long.valueOf(other.launchTime))).longValue();
/* 154:144 */         fieldSetFlags()[2] = 1;
/* 155:    */       }
/* 156:    */     }
/* 157:    */     
/* 158:    */     public CharSequence getJobid()
/* 159:    */     {
/* 160:150 */       return this.jobid;
/* 161:    */     }
/* 162:    */     
/* 163:    */     public Builder setJobid(CharSequence value)
/* 164:    */     {
/* 165:155 */       validate(fields()[0], value);
/* 166:156 */       this.jobid = value;
/* 167:157 */       fieldSetFlags()[0] = 1;
/* 168:158 */       return this;
/* 169:    */     }
/* 170:    */     
/* 171:    */     public boolean hasJobid()
/* 172:    */     {
/* 173:163 */       return fieldSetFlags()[0];
/* 174:    */     }
/* 175:    */     
/* 176:    */     public Builder clearJobid()
/* 177:    */     {
/* 178:168 */       this.jobid = null;
/* 179:169 */       fieldSetFlags()[0] = 0;
/* 180:170 */       return this;
/* 181:    */     }
/* 182:    */     
/* 183:    */     public Long getSubmitTime()
/* 184:    */     {
/* 185:175 */       return Long.valueOf(this.submitTime);
/* 186:    */     }
/* 187:    */     
/* 188:    */     public Builder setSubmitTime(long value)
/* 189:    */     {
/* 190:180 */       validate(fields()[1], Long.valueOf(value));
/* 191:181 */       this.submitTime = value;
/* 192:182 */       fieldSetFlags()[1] = 1;
/* 193:183 */       return this;
/* 194:    */     }
/* 195:    */     
/* 196:    */     public boolean hasSubmitTime()
/* 197:    */     {
/* 198:188 */       return fieldSetFlags()[1];
/* 199:    */     }
/* 200:    */     
/* 201:    */     public Builder clearSubmitTime()
/* 202:    */     {
/* 203:193 */       fieldSetFlags()[1] = 0;
/* 204:194 */       return this;
/* 205:    */     }
/* 206:    */     
/* 207:    */     public Long getLaunchTime()
/* 208:    */     {
/* 209:199 */       return Long.valueOf(this.launchTime);
/* 210:    */     }
/* 211:    */     
/* 212:    */     public Builder setLaunchTime(long value)
/* 213:    */     {
/* 214:204 */       validate(fields()[2], Long.valueOf(value));
/* 215:205 */       this.launchTime = value;
/* 216:206 */       fieldSetFlags()[2] = 1;
/* 217:207 */       return this;
/* 218:    */     }
/* 219:    */     
/* 220:    */     public boolean hasLaunchTime()
/* 221:    */     {
/* 222:212 */       return fieldSetFlags()[2];
/* 223:    */     }
/* 224:    */     
/* 225:    */     public Builder clearLaunchTime()
/* 226:    */     {
/* 227:217 */       fieldSetFlags()[2] = 0;
/* 228:218 */       return this;
/* 229:    */     }
/* 230:    */     
/* 231:    */     public JobInfoChange build()
/* 232:    */     {
/* 233:    */       try
/* 234:    */       {
/* 235:224 */         JobInfoChange record = new JobInfoChange();
/* 236:225 */         record.jobid = (fieldSetFlags()[0] != 0 ? this.jobid : (CharSequence)defaultValue(fields()[0]));
/* 237:226 */         record.submitTime = (fieldSetFlags()[1] != 0 ? this.submitTime : ((Long)defaultValue(fields()[1])).longValue());
/* 238:227 */         record.launchTime = (fieldSetFlags()[2] != 0 ? this.launchTime : ((Long)defaultValue(fields()[2])).longValue());
/* 239:228 */         return record;
/* 240:    */       }
/* 241:    */       catch (Exception e)
/* 242:    */       {
/* 243:230 */         throw new AvroRuntimeException(e);
/* 244:    */       }
/* 245:    */     }
/* 246:    */   }
/* 247:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.jobhistory.JobInfoChange
 * JD-Core Version:    0.7.0.1
 */