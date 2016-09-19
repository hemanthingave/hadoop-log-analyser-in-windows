/*   1:    */ package org.apache.hadoop.mapreduce.jobhistory;
/*   2:    */ 
/*   3:    */ import java.util.List;
/*   4:    */ import org.apache.avro.AvroRuntimeException;
/*   5:    */ import org.apache.avro.Schema;
/*   6:    */ import org.apache.avro.Schema.Field;
/*   7:    */ import org.apache.avro.Schema.Parser;
/*   8:    */ import org.apache.avro.data.RecordBuilder;
/*   9:    */ import org.apache.avro.generic.GenericData;
/*  10:    */ import org.apache.avro.specific.AvroGenerated;
/*  11:    */ import org.apache.avro.specific.SpecificRecord;
/*  12:    */ import org.apache.avro.specific.SpecificRecordBase;
/*  13:    */ import org.apache.avro.specific.SpecificRecordBuilderBase;
/*  14:    */ 
/*  15:    */ @AvroGenerated
/*  16:    */ public class JhCounterGroup
/*  17:    */   extends SpecificRecordBase
/*  18:    */   implements SpecificRecord
/*  19:    */ {
/*  20: 10 */   public static final Schema SCHEMA$ = new Schema.Parser().parse("{\"type\":\"record\",\"name\":\"JhCounterGroup\",\"namespace\":\"org.apache.hadoop.mapreduce.jobhistory\",\"fields\":[{\"name\":\"name\",\"type\":\"string\"},{\"name\":\"displayName\",\"type\":\"string\"},{\"name\":\"counts\",\"type\":{\"type\":\"array\",\"items\":{\"type\":\"record\",\"name\":\"JhCounter\",\"fields\":[{\"name\":\"name\",\"type\":\"string\"},{\"name\":\"displayName\",\"type\":\"string\"},{\"name\":\"value\",\"type\":\"long\"}]}}}]}");
/*  21:    */   @Deprecated
/*  22:    */   public CharSequence name;
/*  23:    */   @Deprecated
/*  24:    */   public CharSequence displayName;
/*  25:    */   @Deprecated
/*  26:    */   public List<JhCounter> counts;
/*  27:    */   
/*  28:    */   public static Schema getClassSchema()
/*  29:    */   {
/*  30: 11 */     return SCHEMA$;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public JhCounterGroup() {}
/*  34:    */   
/*  35:    */   public JhCounterGroup(CharSequence name, CharSequence displayName, List<JhCounter> counts)
/*  36:    */   {
/*  37: 25 */     this.name = name;
/*  38: 26 */     this.displayName = displayName;
/*  39: 27 */     this.counts = counts;
/*  40:    */   }
/*  41:    */   
/*  42:    */   public Schema getSchema()
/*  43:    */   {
/*  44: 30 */     return SCHEMA$;
/*  45:    */   }
/*  46:    */   
/*  47:    */   public Object get(int field$)
/*  48:    */   {
/*  49: 33 */     switch (field$)
/*  50:    */     {
/*  51:    */     case 0: 
/*  52: 34 */       return this.name;
/*  53:    */     case 1: 
/*  54: 35 */       return this.displayName;
/*  55:    */     case 2: 
/*  56: 36 */       return this.counts;
/*  57:    */     }
/*  58: 37 */     throw new AvroRuntimeException("Bad index");
/*  59:    */   }
/*  60:    */   
/*  61:    */   public void put(int field$, Object value$)
/*  62:    */   {
/*  63: 43 */     switch (field$)
/*  64:    */     {
/*  65:    */     case 0: 
/*  66: 44 */       this.name = ((CharSequence)value$); break;
/*  67:    */     case 1: 
/*  68: 45 */       this.displayName = ((CharSequence)value$); break;
/*  69:    */     case 2: 
/*  70: 46 */       this.counts = ((List)value$); break;
/*  71:    */     default: 
/*  72: 47 */       throw new AvroRuntimeException("Bad index");
/*  73:    */     }
/*  74:    */   }
/*  75:    */   
/*  76:    */   public CharSequence getName()
/*  77:    */   {
/*  78: 55 */     return this.name;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public void setName(CharSequence value)
/*  82:    */   {
/*  83: 63 */     this.name = value;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public CharSequence getDisplayName()
/*  87:    */   {
/*  88: 70 */     return this.displayName;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public void setDisplayName(CharSequence value)
/*  92:    */   {
/*  93: 78 */     this.displayName = value;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public List<JhCounter> getCounts()
/*  97:    */   {
/*  98: 85 */     return this.counts;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public void setCounts(List<JhCounter> value)
/* 102:    */   {
/* 103: 93 */     this.counts = value;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public static Builder newBuilder()
/* 107:    */   {
/* 108: 98 */     return new Builder(null);
/* 109:    */   }
/* 110:    */   
/* 111:    */   public static Builder newBuilder(Builder other)
/* 112:    */   {
/* 113:103 */     return new Builder(other, null);
/* 114:    */   }
/* 115:    */   
/* 116:    */   public static Builder newBuilder(JhCounterGroup other)
/* 117:    */   {
/* 118:108 */     return new Builder(other, null);
/* 119:    */   }
/* 120:    */   
/* 121:    */   public static class Builder
/* 122:    */     extends SpecificRecordBuilderBase<JhCounterGroup>
/* 123:    */     implements RecordBuilder<JhCounterGroup>
/* 124:    */   {
/* 125:    */     private CharSequence name;
/* 126:    */     private CharSequence displayName;
/* 127:    */     private List<JhCounter> counts;
/* 128:    */     
/* 129:    */     private Builder()
/* 130:    */     {
/* 131:123 */       super();
/* 132:    */     }
/* 133:    */     
/* 134:    */     private Builder(Builder other)
/* 135:    */     {
/* 136:128 */       super();
/* 137:    */     }
/* 138:    */     
/* 139:    */     private Builder(JhCounterGroup other)
/* 140:    */     {
/* 141:133 */       super();
/* 142:134 */       if (isValidValue(fields()[0], other.name))
/* 143:    */       {
/* 144:135 */         this.name = ((CharSequence)data().deepCopy(fields()[0].schema(), other.name));
/* 145:136 */         fieldSetFlags()[0] = 1;
/* 146:    */       }
/* 147:138 */       if (isValidValue(fields()[1], other.displayName))
/* 148:    */       {
/* 149:139 */         this.displayName = ((CharSequence)data().deepCopy(fields()[1].schema(), other.displayName));
/* 150:140 */         fieldSetFlags()[1] = 1;
/* 151:    */       }
/* 152:142 */       if (isValidValue(fields()[2], other.counts))
/* 153:    */       {
/* 154:143 */         this.counts = ((List)data().deepCopy(fields()[2].schema(), other.counts));
/* 155:144 */         fieldSetFlags()[2] = 1;
/* 156:    */       }
/* 157:    */     }
/* 158:    */     
/* 159:    */     public CharSequence getName()
/* 160:    */     {
/* 161:150 */       return this.name;
/* 162:    */     }
/* 163:    */     
/* 164:    */     public Builder setName(CharSequence value)
/* 165:    */     {
/* 166:155 */       validate(fields()[0], value);
/* 167:156 */       this.name = value;
/* 168:157 */       fieldSetFlags()[0] = 1;
/* 169:158 */       return this;
/* 170:    */     }
/* 171:    */     
/* 172:    */     public boolean hasName()
/* 173:    */     {
/* 174:163 */       return fieldSetFlags()[0];
/* 175:    */     }
/* 176:    */     
/* 177:    */     public Builder clearName()
/* 178:    */     {
/* 179:168 */       this.name = null;
/* 180:169 */       fieldSetFlags()[0] = 0;
/* 181:170 */       return this;
/* 182:    */     }
/* 183:    */     
/* 184:    */     public CharSequence getDisplayName()
/* 185:    */     {
/* 186:175 */       return this.displayName;
/* 187:    */     }
/* 188:    */     
/* 189:    */     public Builder setDisplayName(CharSequence value)
/* 190:    */     {
/* 191:180 */       validate(fields()[1], value);
/* 192:181 */       this.displayName = value;
/* 193:182 */       fieldSetFlags()[1] = 1;
/* 194:183 */       return this;
/* 195:    */     }
/* 196:    */     
/* 197:    */     public boolean hasDisplayName()
/* 198:    */     {
/* 199:188 */       return fieldSetFlags()[1];
/* 200:    */     }
/* 201:    */     
/* 202:    */     public Builder clearDisplayName()
/* 203:    */     {
/* 204:193 */       this.displayName = null;
/* 205:194 */       fieldSetFlags()[1] = 0;
/* 206:195 */       return this;
/* 207:    */     }
/* 208:    */     
/* 209:    */     public List<JhCounter> getCounts()
/* 210:    */     {
/* 211:200 */       return this.counts;
/* 212:    */     }
/* 213:    */     
/* 214:    */     public Builder setCounts(List<JhCounter> value)
/* 215:    */     {
/* 216:205 */       validate(fields()[2], value);
/* 217:206 */       this.counts = value;
/* 218:207 */       fieldSetFlags()[2] = 1;
/* 219:208 */       return this;
/* 220:    */     }
/* 221:    */     
/* 222:    */     public boolean hasCounts()
/* 223:    */     {
/* 224:213 */       return fieldSetFlags()[2];
/* 225:    */     }
/* 226:    */     
/* 227:    */     public Builder clearCounts()
/* 228:    */     {
/* 229:218 */       this.counts = null;
/* 230:219 */       fieldSetFlags()[2] = 0;
/* 231:220 */       return this;
/* 232:    */     }
/* 233:    */     
/* 234:    */     public JhCounterGroup build()
/* 235:    */     {
/* 236:    */       try
/* 237:    */       {
/* 238:226 */         JhCounterGroup record = new JhCounterGroup();
/* 239:227 */         record.name = (fieldSetFlags()[0] != 0 ? this.name : (CharSequence)defaultValue(fields()[0]));
/* 240:228 */         record.displayName = (fieldSetFlags()[1] != 0 ? this.displayName : (CharSequence)defaultValue(fields()[1]));
/* 241:229 */         record.counts = (fieldSetFlags()[2] != 0 ? this.counts : (List)defaultValue(fields()[2]));
/* 242:230 */         return record;
/* 243:    */       }
/* 244:    */       catch (Exception e)
/* 245:    */       {
/* 246:232 */         throw new AvroRuntimeException(e);
/* 247:    */       }
/* 248:    */     }
/* 249:    */   }
/* 250:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.jobhistory.JhCounterGroup
 * JD-Core Version:    0.7.0.1
 */