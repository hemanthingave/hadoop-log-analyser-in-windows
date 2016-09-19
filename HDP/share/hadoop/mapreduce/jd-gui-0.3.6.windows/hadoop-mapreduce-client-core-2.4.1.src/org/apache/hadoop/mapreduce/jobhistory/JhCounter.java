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
/*  15:    */ public class JhCounter
/*  16:    */   extends SpecificRecordBase
/*  17:    */   implements SpecificRecord
/*  18:    */ {
/*  19: 10 */   public static final Schema SCHEMA$ = new Schema.Parser().parse("{\"type\":\"record\",\"name\":\"JhCounter\",\"namespace\":\"org.apache.hadoop.mapreduce.jobhistory\",\"fields\":[{\"name\":\"name\",\"type\":\"string\"},{\"name\":\"displayName\",\"type\":\"string\"},{\"name\":\"value\",\"type\":\"long\"}]}");
/*  20:    */   @Deprecated
/*  21:    */   public CharSequence name;
/*  22:    */   @Deprecated
/*  23:    */   public CharSequence displayName;
/*  24:    */   @Deprecated
/*  25:    */   public long value;
/*  26:    */   
/*  27:    */   public static Schema getClassSchema()
/*  28:    */   {
/*  29: 11 */     return SCHEMA$;
/*  30:    */   }
/*  31:    */   
/*  32:    */   public JhCounter() {}
/*  33:    */   
/*  34:    */   public JhCounter(CharSequence name, CharSequence displayName, Long value)
/*  35:    */   {
/*  36: 25 */     this.name = name;
/*  37: 26 */     this.displayName = displayName;
/*  38: 27 */     this.value = value.longValue();
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
/*  51: 34 */       return this.name;
/*  52:    */     case 1: 
/*  53: 35 */       return this.displayName;
/*  54:    */     case 2: 
/*  55: 36 */       return Long.valueOf(this.value);
/*  56:    */     }
/*  57: 37 */     throw new AvroRuntimeException("Bad index");
/*  58:    */   }
/*  59:    */   
/*  60:    */   public void put(int field$, Object value$)
/*  61:    */   {
/*  62: 43 */     switch (field$)
/*  63:    */     {
/*  64:    */     case 0: 
/*  65: 44 */       this.name = ((CharSequence)value$); break;
/*  66:    */     case 1: 
/*  67: 45 */       this.displayName = ((CharSequence)value$); break;
/*  68:    */     case 2: 
/*  69: 46 */       this.value = ((Long)value$).longValue(); break;
/*  70:    */     default: 
/*  71: 47 */       throw new AvroRuntimeException("Bad index");
/*  72:    */     }
/*  73:    */   }
/*  74:    */   
/*  75:    */   public CharSequence getName()
/*  76:    */   {
/*  77: 55 */     return this.name;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public void setName(CharSequence value)
/*  81:    */   {
/*  82: 63 */     this.name = value;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public CharSequence getDisplayName()
/*  86:    */   {
/*  87: 70 */     return this.displayName;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public void setDisplayName(CharSequence value)
/*  91:    */   {
/*  92: 78 */     this.displayName = value;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public Long getValue()
/*  96:    */   {
/*  97: 85 */     return Long.valueOf(this.value);
/*  98:    */   }
/*  99:    */   
/* 100:    */   public void setValue(Long value)
/* 101:    */   {
/* 102: 93 */     this.value = value.longValue();
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
/* 115:    */   public static Builder newBuilder(JhCounter other)
/* 116:    */   {
/* 117:108 */     return new Builder(other, null);
/* 118:    */   }
/* 119:    */   
/* 120:    */   public static class Builder
/* 121:    */     extends SpecificRecordBuilderBase<JhCounter>
/* 122:    */     implements RecordBuilder<JhCounter>
/* 123:    */   {
/* 124:    */     private CharSequence name;
/* 125:    */     private CharSequence displayName;
/* 126:    */     private long value;
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
/* 138:    */     private Builder(JhCounter other)
/* 139:    */     {
/* 140:133 */       super();
/* 141:134 */       if (isValidValue(fields()[0], other.name))
/* 142:    */       {
/* 143:135 */         this.name = ((CharSequence)data().deepCopy(fields()[0].schema(), other.name));
/* 144:136 */         fieldSetFlags()[0] = 1;
/* 145:    */       }
/* 146:138 */       if (isValidValue(fields()[1], other.displayName))
/* 147:    */       {
/* 148:139 */         this.displayName = ((CharSequence)data().deepCopy(fields()[1].schema(), other.displayName));
/* 149:140 */         fieldSetFlags()[1] = 1;
/* 150:    */       }
/* 151:142 */       if (isValidValue(fields()[2], Long.valueOf(other.value)))
/* 152:    */       {
/* 153:143 */         this.value = ((Long)data().deepCopy(fields()[2].schema(), Long.valueOf(other.value))).longValue();
/* 154:144 */         fieldSetFlags()[2] = 1;
/* 155:    */       }
/* 156:    */     }
/* 157:    */     
/* 158:    */     public CharSequence getName()
/* 159:    */     {
/* 160:150 */       return this.name;
/* 161:    */     }
/* 162:    */     
/* 163:    */     public Builder setName(CharSequence value)
/* 164:    */     {
/* 165:155 */       validate(fields()[0], value);
/* 166:156 */       this.name = value;
/* 167:157 */       fieldSetFlags()[0] = 1;
/* 168:158 */       return this;
/* 169:    */     }
/* 170:    */     
/* 171:    */     public boolean hasName()
/* 172:    */     {
/* 173:163 */       return fieldSetFlags()[0];
/* 174:    */     }
/* 175:    */     
/* 176:    */     public Builder clearName()
/* 177:    */     {
/* 178:168 */       this.name = null;
/* 179:169 */       fieldSetFlags()[0] = 0;
/* 180:170 */       return this;
/* 181:    */     }
/* 182:    */     
/* 183:    */     public CharSequence getDisplayName()
/* 184:    */     {
/* 185:175 */       return this.displayName;
/* 186:    */     }
/* 187:    */     
/* 188:    */     public Builder setDisplayName(CharSequence value)
/* 189:    */     {
/* 190:180 */       validate(fields()[1], value);
/* 191:181 */       this.displayName = value;
/* 192:182 */       fieldSetFlags()[1] = 1;
/* 193:183 */       return this;
/* 194:    */     }
/* 195:    */     
/* 196:    */     public boolean hasDisplayName()
/* 197:    */     {
/* 198:188 */       return fieldSetFlags()[1];
/* 199:    */     }
/* 200:    */     
/* 201:    */     public Builder clearDisplayName()
/* 202:    */     {
/* 203:193 */       this.displayName = null;
/* 204:194 */       fieldSetFlags()[1] = 0;
/* 205:195 */       return this;
/* 206:    */     }
/* 207:    */     
/* 208:    */     public Long getValue()
/* 209:    */     {
/* 210:200 */       return Long.valueOf(this.value);
/* 211:    */     }
/* 212:    */     
/* 213:    */     public Builder setValue(long value)
/* 214:    */     {
/* 215:205 */       validate(fields()[2], Long.valueOf(value));
/* 216:206 */       this.value = value;
/* 217:207 */       fieldSetFlags()[2] = 1;
/* 218:208 */       return this;
/* 219:    */     }
/* 220:    */     
/* 221:    */     public boolean hasValue()
/* 222:    */     {
/* 223:213 */       return fieldSetFlags()[2];
/* 224:    */     }
/* 225:    */     
/* 226:    */     public Builder clearValue()
/* 227:    */     {
/* 228:218 */       fieldSetFlags()[2] = 0;
/* 229:219 */       return this;
/* 230:    */     }
/* 231:    */     
/* 232:    */     public JhCounter build()
/* 233:    */     {
/* 234:    */       try
/* 235:    */       {
/* 236:225 */         JhCounter record = new JhCounter();
/* 237:226 */         record.name = (fieldSetFlags()[0] != 0 ? this.name : (CharSequence)defaultValue(fields()[0]));
/* 238:227 */         record.displayName = (fieldSetFlags()[1] != 0 ? this.displayName : (CharSequence)defaultValue(fields()[1]));
/* 239:228 */         record.value = (fieldSetFlags()[2] != 0 ? this.value : ((Long)defaultValue(fields()[2])).longValue());
/* 240:229 */         return record;
/* 241:    */       }
/* 242:    */       catch (Exception e)
/* 243:    */       {
/* 244:231 */         throw new AvroRuntimeException(e);
/* 245:    */       }
/* 246:    */     }
/* 247:    */   }
/* 248:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.jobhistory.JhCounter
 * JD-Core Version:    0.7.0.1
 */