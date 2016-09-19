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
/*  15:    */ public class JobPriorityChange
/*  16:    */   extends SpecificRecordBase
/*  17:    */   implements SpecificRecord
/*  18:    */ {
/*  19: 10 */   public static final Schema SCHEMA$ = new Schema.Parser().parse("{\"type\":\"record\",\"name\":\"JobPriorityChange\",\"namespace\":\"org.apache.hadoop.mapreduce.jobhistory\",\"fields\":[{\"name\":\"jobid\",\"type\":\"string\"},{\"name\":\"priority\",\"type\":\"string\"}]}");
/*  20:    */   @Deprecated
/*  21:    */   public CharSequence jobid;
/*  22:    */   @Deprecated
/*  23:    */   public CharSequence priority;
/*  24:    */   
/*  25:    */   public static Schema getClassSchema()
/*  26:    */   {
/*  27: 11 */     return SCHEMA$;
/*  28:    */   }
/*  29:    */   
/*  30:    */   public JobPriorityChange() {}
/*  31:    */   
/*  32:    */   public JobPriorityChange(CharSequence jobid, CharSequence priority)
/*  33:    */   {
/*  34: 24 */     this.jobid = jobid;
/*  35: 25 */     this.priority = priority;
/*  36:    */   }
/*  37:    */   
/*  38:    */   public Schema getSchema()
/*  39:    */   {
/*  40: 28 */     return SCHEMA$;
/*  41:    */   }
/*  42:    */   
/*  43:    */   public Object get(int field$)
/*  44:    */   {
/*  45: 31 */     switch (field$)
/*  46:    */     {
/*  47:    */     case 0: 
/*  48: 32 */       return this.jobid;
/*  49:    */     case 1: 
/*  50: 33 */       return this.priority;
/*  51:    */     }
/*  52: 34 */     throw new AvroRuntimeException("Bad index");
/*  53:    */   }
/*  54:    */   
/*  55:    */   public void put(int field$, Object value$)
/*  56:    */   {
/*  57: 40 */     switch (field$)
/*  58:    */     {
/*  59:    */     case 0: 
/*  60: 41 */       this.jobid = ((CharSequence)value$); break;
/*  61:    */     case 1: 
/*  62: 42 */       this.priority = ((CharSequence)value$); break;
/*  63:    */     default: 
/*  64: 43 */       throw new AvroRuntimeException("Bad index");
/*  65:    */     }
/*  66:    */   }
/*  67:    */   
/*  68:    */   public CharSequence getJobid()
/*  69:    */   {
/*  70: 51 */     return this.jobid;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public void setJobid(CharSequence value)
/*  74:    */   {
/*  75: 59 */     this.jobid = value;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public CharSequence getPriority()
/*  79:    */   {
/*  80: 66 */     return this.priority;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public void setPriority(CharSequence value)
/*  84:    */   {
/*  85: 74 */     this.priority = value;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public static Builder newBuilder()
/*  89:    */   {
/*  90: 79 */     return new Builder(null);
/*  91:    */   }
/*  92:    */   
/*  93:    */   public static Builder newBuilder(Builder other)
/*  94:    */   {
/*  95: 84 */     return new Builder(other, null);
/*  96:    */   }
/*  97:    */   
/*  98:    */   public static Builder newBuilder(JobPriorityChange other)
/*  99:    */   {
/* 100: 89 */     return new Builder(other, null);
/* 101:    */   }
/* 102:    */   
/* 103:    */   public static class Builder
/* 104:    */     extends SpecificRecordBuilderBase<JobPriorityChange>
/* 105:    */     implements RecordBuilder<JobPriorityChange>
/* 106:    */   {
/* 107:    */     private CharSequence jobid;
/* 108:    */     private CharSequence priority;
/* 109:    */     
/* 110:    */     private Builder()
/* 111:    */     {
/* 112:103 */       super();
/* 113:    */     }
/* 114:    */     
/* 115:    */     private Builder(Builder other)
/* 116:    */     {
/* 117:108 */       super();
/* 118:    */     }
/* 119:    */     
/* 120:    */     private Builder(JobPriorityChange other)
/* 121:    */     {
/* 122:113 */       super();
/* 123:114 */       if (isValidValue(fields()[0], other.jobid))
/* 124:    */       {
/* 125:115 */         this.jobid = ((CharSequence)data().deepCopy(fields()[0].schema(), other.jobid));
/* 126:116 */         fieldSetFlags()[0] = 1;
/* 127:    */       }
/* 128:118 */       if (isValidValue(fields()[1], other.priority))
/* 129:    */       {
/* 130:119 */         this.priority = ((CharSequence)data().deepCopy(fields()[1].schema(), other.priority));
/* 131:120 */         fieldSetFlags()[1] = 1;
/* 132:    */       }
/* 133:    */     }
/* 134:    */     
/* 135:    */     public CharSequence getJobid()
/* 136:    */     {
/* 137:126 */       return this.jobid;
/* 138:    */     }
/* 139:    */     
/* 140:    */     public Builder setJobid(CharSequence value)
/* 141:    */     {
/* 142:131 */       validate(fields()[0], value);
/* 143:132 */       this.jobid = value;
/* 144:133 */       fieldSetFlags()[0] = 1;
/* 145:134 */       return this;
/* 146:    */     }
/* 147:    */     
/* 148:    */     public boolean hasJobid()
/* 149:    */     {
/* 150:139 */       return fieldSetFlags()[0];
/* 151:    */     }
/* 152:    */     
/* 153:    */     public Builder clearJobid()
/* 154:    */     {
/* 155:144 */       this.jobid = null;
/* 156:145 */       fieldSetFlags()[0] = 0;
/* 157:146 */       return this;
/* 158:    */     }
/* 159:    */     
/* 160:    */     public CharSequence getPriority()
/* 161:    */     {
/* 162:151 */       return this.priority;
/* 163:    */     }
/* 164:    */     
/* 165:    */     public Builder setPriority(CharSequence value)
/* 166:    */     {
/* 167:156 */       validate(fields()[1], value);
/* 168:157 */       this.priority = value;
/* 169:158 */       fieldSetFlags()[1] = 1;
/* 170:159 */       return this;
/* 171:    */     }
/* 172:    */     
/* 173:    */     public boolean hasPriority()
/* 174:    */     {
/* 175:164 */       return fieldSetFlags()[1];
/* 176:    */     }
/* 177:    */     
/* 178:    */     public Builder clearPriority()
/* 179:    */     {
/* 180:169 */       this.priority = null;
/* 181:170 */       fieldSetFlags()[1] = 0;
/* 182:171 */       return this;
/* 183:    */     }
/* 184:    */     
/* 185:    */     public JobPriorityChange build()
/* 186:    */     {
/* 187:    */       try
/* 188:    */       {
/* 189:177 */         JobPriorityChange record = new JobPriorityChange();
/* 190:178 */         record.jobid = (fieldSetFlags()[0] != 0 ? this.jobid : (CharSequence)defaultValue(fields()[0]));
/* 191:179 */         record.priority = (fieldSetFlags()[1] != 0 ? this.priority : (CharSequence)defaultValue(fields()[1]));
/* 192:180 */         return record;
/* 193:    */       }
/* 194:    */       catch (Exception e)
/* 195:    */       {
/* 196:182 */         throw new AvroRuntimeException(e);
/* 197:    */       }
/* 198:    */     }
/* 199:    */   }
/* 200:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.jobhistory.JobPriorityChange
 * JD-Core Version:    0.7.0.1
 */