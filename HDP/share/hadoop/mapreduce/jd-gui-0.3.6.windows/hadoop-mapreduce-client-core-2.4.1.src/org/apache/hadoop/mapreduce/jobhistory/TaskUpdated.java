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
/*  15:    */ public class TaskUpdated
/*  16:    */   extends SpecificRecordBase
/*  17:    */   implements SpecificRecord
/*  18:    */ {
/*  19: 10 */   public static final Schema SCHEMA$ = new Schema.Parser().parse("{\"type\":\"record\",\"name\":\"TaskUpdated\",\"namespace\":\"org.apache.hadoop.mapreduce.jobhistory\",\"fields\":[{\"name\":\"taskid\",\"type\":\"string\"},{\"name\":\"finishTime\",\"type\":\"long\"}]}");
/*  20:    */   @Deprecated
/*  21:    */   public CharSequence taskid;
/*  22:    */   @Deprecated
/*  23:    */   public long finishTime;
/*  24:    */   
/*  25:    */   public static Schema getClassSchema()
/*  26:    */   {
/*  27: 11 */     return SCHEMA$;
/*  28:    */   }
/*  29:    */   
/*  30:    */   public TaskUpdated() {}
/*  31:    */   
/*  32:    */   public TaskUpdated(CharSequence taskid, Long finishTime)
/*  33:    */   {
/*  34: 24 */     this.taskid = taskid;
/*  35: 25 */     this.finishTime = finishTime.longValue();
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
/*  48: 32 */       return this.taskid;
/*  49:    */     case 1: 
/*  50: 33 */       return Long.valueOf(this.finishTime);
/*  51:    */     }
/*  52: 34 */     throw new AvroRuntimeException("Bad index");
/*  53:    */   }
/*  54:    */   
/*  55:    */   public void put(int field$, Object value$)
/*  56:    */   {
/*  57: 40 */     switch (field$)
/*  58:    */     {
/*  59:    */     case 0: 
/*  60: 41 */       this.taskid = ((CharSequence)value$); break;
/*  61:    */     case 1: 
/*  62: 42 */       this.finishTime = ((Long)value$).longValue(); break;
/*  63:    */     default: 
/*  64: 43 */       throw new AvroRuntimeException("Bad index");
/*  65:    */     }
/*  66:    */   }
/*  67:    */   
/*  68:    */   public CharSequence getTaskid()
/*  69:    */   {
/*  70: 51 */     return this.taskid;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public void setTaskid(CharSequence value)
/*  74:    */   {
/*  75: 59 */     this.taskid = value;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public Long getFinishTime()
/*  79:    */   {
/*  80: 66 */     return Long.valueOf(this.finishTime);
/*  81:    */   }
/*  82:    */   
/*  83:    */   public void setFinishTime(Long value)
/*  84:    */   {
/*  85: 74 */     this.finishTime = value.longValue();
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
/*  98:    */   public static Builder newBuilder(TaskUpdated other)
/*  99:    */   {
/* 100: 89 */     return new Builder(other, null);
/* 101:    */   }
/* 102:    */   
/* 103:    */   public static class Builder
/* 104:    */     extends SpecificRecordBuilderBase<TaskUpdated>
/* 105:    */     implements RecordBuilder<TaskUpdated>
/* 106:    */   {
/* 107:    */     private CharSequence taskid;
/* 108:    */     private long finishTime;
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
/* 120:    */     private Builder(TaskUpdated other)
/* 121:    */     {
/* 122:113 */       super();
/* 123:114 */       if (isValidValue(fields()[0], other.taskid))
/* 124:    */       {
/* 125:115 */         this.taskid = ((CharSequence)data().deepCopy(fields()[0].schema(), other.taskid));
/* 126:116 */         fieldSetFlags()[0] = 1;
/* 127:    */       }
/* 128:118 */       if (isValidValue(fields()[1], Long.valueOf(other.finishTime)))
/* 129:    */       {
/* 130:119 */         this.finishTime = ((Long)data().deepCopy(fields()[1].schema(), Long.valueOf(other.finishTime))).longValue();
/* 131:120 */         fieldSetFlags()[1] = 1;
/* 132:    */       }
/* 133:    */     }
/* 134:    */     
/* 135:    */     public CharSequence getTaskid()
/* 136:    */     {
/* 137:126 */       return this.taskid;
/* 138:    */     }
/* 139:    */     
/* 140:    */     public Builder setTaskid(CharSequence value)
/* 141:    */     {
/* 142:131 */       validate(fields()[0], value);
/* 143:132 */       this.taskid = value;
/* 144:133 */       fieldSetFlags()[0] = 1;
/* 145:134 */       return this;
/* 146:    */     }
/* 147:    */     
/* 148:    */     public boolean hasTaskid()
/* 149:    */     {
/* 150:139 */       return fieldSetFlags()[0];
/* 151:    */     }
/* 152:    */     
/* 153:    */     public Builder clearTaskid()
/* 154:    */     {
/* 155:144 */       this.taskid = null;
/* 156:145 */       fieldSetFlags()[0] = 0;
/* 157:146 */       return this;
/* 158:    */     }
/* 159:    */     
/* 160:    */     public Long getFinishTime()
/* 161:    */     {
/* 162:151 */       return Long.valueOf(this.finishTime);
/* 163:    */     }
/* 164:    */     
/* 165:    */     public Builder setFinishTime(long value)
/* 166:    */     {
/* 167:156 */       validate(fields()[1], Long.valueOf(value));
/* 168:157 */       this.finishTime = value;
/* 169:158 */       fieldSetFlags()[1] = 1;
/* 170:159 */       return this;
/* 171:    */     }
/* 172:    */     
/* 173:    */     public boolean hasFinishTime()
/* 174:    */     {
/* 175:164 */       return fieldSetFlags()[1];
/* 176:    */     }
/* 177:    */     
/* 178:    */     public Builder clearFinishTime()
/* 179:    */     {
/* 180:169 */       fieldSetFlags()[1] = 0;
/* 181:170 */       return this;
/* 182:    */     }
/* 183:    */     
/* 184:    */     public TaskUpdated build()
/* 185:    */     {
/* 186:    */       try
/* 187:    */       {
/* 188:176 */         TaskUpdated record = new TaskUpdated();
/* 189:177 */         record.taskid = (fieldSetFlags()[0] != 0 ? this.taskid : (CharSequence)defaultValue(fields()[0]));
/* 190:178 */         record.finishTime = (fieldSetFlags()[1] != 0 ? this.finishTime : ((Long)defaultValue(fields()[1])).longValue());
/* 191:179 */         return record;
/* 192:    */       }
/* 193:    */       catch (Exception e)
/* 194:    */       {
/* 195:181 */         throw new AvroRuntimeException(e);
/* 196:    */       }
/* 197:    */     }
/* 198:    */   }
/* 199:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.jobhistory.TaskUpdated
 * JD-Core Version:    0.7.0.1
 */