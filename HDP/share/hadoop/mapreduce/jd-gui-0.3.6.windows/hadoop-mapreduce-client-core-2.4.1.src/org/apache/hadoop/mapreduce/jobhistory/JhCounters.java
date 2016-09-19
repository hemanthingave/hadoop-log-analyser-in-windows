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
/*  16:    */ public class JhCounters
/*  17:    */   extends SpecificRecordBase
/*  18:    */   implements SpecificRecord
/*  19:    */ {
/*  20: 10 */   public static final Schema SCHEMA$ = new Schema.Parser().parse("{\"type\":\"record\",\"name\":\"JhCounters\",\"namespace\":\"org.apache.hadoop.mapreduce.jobhistory\",\"fields\":[{\"name\":\"name\",\"type\":\"string\"},{\"name\":\"groups\",\"type\":{\"type\":\"array\",\"items\":{\"type\":\"record\",\"name\":\"JhCounterGroup\",\"fields\":[{\"name\":\"name\",\"type\":\"string\"},{\"name\":\"displayName\",\"type\":\"string\"},{\"name\":\"counts\",\"type\":{\"type\":\"array\",\"items\":{\"type\":\"record\",\"name\":\"JhCounter\",\"fields\":[{\"name\":\"name\",\"type\":\"string\"},{\"name\":\"displayName\",\"type\":\"string\"},{\"name\":\"value\",\"type\":\"long\"}]}}}]}}}]}");
/*  21:    */   @Deprecated
/*  22:    */   public CharSequence name;
/*  23:    */   @Deprecated
/*  24:    */   public List<JhCounterGroup> groups;
/*  25:    */   
/*  26:    */   public static Schema getClassSchema()
/*  27:    */   {
/*  28: 11 */     return SCHEMA$;
/*  29:    */   }
/*  30:    */   
/*  31:    */   public JhCounters() {}
/*  32:    */   
/*  33:    */   public JhCounters(CharSequence name, List<JhCounterGroup> groups)
/*  34:    */   {
/*  35: 24 */     this.name = name;
/*  36: 25 */     this.groups = groups;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public Schema getSchema()
/*  40:    */   {
/*  41: 28 */     return SCHEMA$;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public Object get(int field$)
/*  45:    */   {
/*  46: 31 */     switch (field$)
/*  47:    */     {
/*  48:    */     case 0: 
/*  49: 32 */       return this.name;
/*  50:    */     case 1: 
/*  51: 33 */       return this.groups;
/*  52:    */     }
/*  53: 34 */     throw new AvroRuntimeException("Bad index");
/*  54:    */   }
/*  55:    */   
/*  56:    */   public void put(int field$, Object value$)
/*  57:    */   {
/*  58: 40 */     switch (field$)
/*  59:    */     {
/*  60:    */     case 0: 
/*  61: 41 */       this.name = ((CharSequence)value$); break;
/*  62:    */     case 1: 
/*  63: 42 */       this.groups = ((List)value$); break;
/*  64:    */     default: 
/*  65: 43 */       throw new AvroRuntimeException("Bad index");
/*  66:    */     }
/*  67:    */   }
/*  68:    */   
/*  69:    */   public CharSequence getName()
/*  70:    */   {
/*  71: 51 */     return this.name;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public void setName(CharSequence value)
/*  75:    */   {
/*  76: 59 */     this.name = value;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public List<JhCounterGroup> getGroups()
/*  80:    */   {
/*  81: 66 */     return this.groups;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public void setGroups(List<JhCounterGroup> value)
/*  85:    */   {
/*  86: 74 */     this.groups = value;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public static Builder newBuilder()
/*  90:    */   {
/*  91: 79 */     return new Builder(null);
/*  92:    */   }
/*  93:    */   
/*  94:    */   public static Builder newBuilder(Builder other)
/*  95:    */   {
/*  96: 84 */     return new Builder(other, null);
/*  97:    */   }
/*  98:    */   
/*  99:    */   public static Builder newBuilder(JhCounters other)
/* 100:    */   {
/* 101: 89 */     return new Builder(other, null);
/* 102:    */   }
/* 103:    */   
/* 104:    */   public static class Builder
/* 105:    */     extends SpecificRecordBuilderBase<JhCounters>
/* 106:    */     implements RecordBuilder<JhCounters>
/* 107:    */   {
/* 108:    */     private CharSequence name;
/* 109:    */     private List<JhCounterGroup> groups;
/* 110:    */     
/* 111:    */     private Builder()
/* 112:    */     {
/* 113:103 */       super();
/* 114:    */     }
/* 115:    */     
/* 116:    */     private Builder(Builder other)
/* 117:    */     {
/* 118:108 */       super();
/* 119:    */     }
/* 120:    */     
/* 121:    */     private Builder(JhCounters other)
/* 122:    */     {
/* 123:113 */       super();
/* 124:114 */       if (isValidValue(fields()[0], other.name))
/* 125:    */       {
/* 126:115 */         this.name = ((CharSequence)data().deepCopy(fields()[0].schema(), other.name));
/* 127:116 */         fieldSetFlags()[0] = 1;
/* 128:    */       }
/* 129:118 */       if (isValidValue(fields()[1], other.groups))
/* 130:    */       {
/* 131:119 */         this.groups = ((List)data().deepCopy(fields()[1].schema(), other.groups));
/* 132:120 */         fieldSetFlags()[1] = 1;
/* 133:    */       }
/* 134:    */     }
/* 135:    */     
/* 136:    */     public CharSequence getName()
/* 137:    */     {
/* 138:126 */       return this.name;
/* 139:    */     }
/* 140:    */     
/* 141:    */     public Builder setName(CharSequence value)
/* 142:    */     {
/* 143:131 */       validate(fields()[0], value);
/* 144:132 */       this.name = value;
/* 145:133 */       fieldSetFlags()[0] = 1;
/* 146:134 */       return this;
/* 147:    */     }
/* 148:    */     
/* 149:    */     public boolean hasName()
/* 150:    */     {
/* 151:139 */       return fieldSetFlags()[0];
/* 152:    */     }
/* 153:    */     
/* 154:    */     public Builder clearName()
/* 155:    */     {
/* 156:144 */       this.name = null;
/* 157:145 */       fieldSetFlags()[0] = 0;
/* 158:146 */       return this;
/* 159:    */     }
/* 160:    */     
/* 161:    */     public List<JhCounterGroup> getGroups()
/* 162:    */     {
/* 163:151 */       return this.groups;
/* 164:    */     }
/* 165:    */     
/* 166:    */     public Builder setGroups(List<JhCounterGroup> value)
/* 167:    */     {
/* 168:156 */       validate(fields()[1], value);
/* 169:157 */       this.groups = value;
/* 170:158 */       fieldSetFlags()[1] = 1;
/* 171:159 */       return this;
/* 172:    */     }
/* 173:    */     
/* 174:    */     public boolean hasGroups()
/* 175:    */     {
/* 176:164 */       return fieldSetFlags()[1];
/* 177:    */     }
/* 178:    */     
/* 179:    */     public Builder clearGroups()
/* 180:    */     {
/* 181:169 */       this.groups = null;
/* 182:170 */       fieldSetFlags()[1] = 0;
/* 183:171 */       return this;
/* 184:    */     }
/* 185:    */     
/* 186:    */     public JhCounters build()
/* 187:    */     {
/* 188:    */       try
/* 189:    */       {
/* 190:177 */         JhCounters record = new JhCounters();
/* 191:178 */         record.name = (fieldSetFlags()[0] != 0 ? this.name : (CharSequence)defaultValue(fields()[0]));
/* 192:179 */         record.groups = (fieldSetFlags()[1] != 0 ? this.groups : (List)defaultValue(fields()[1]));
/* 193:180 */         return record;
/* 194:    */       }
/* 195:    */       catch (Exception e)
/* 196:    */       {
/* 197:182 */         throw new AvroRuntimeException(e);
/* 198:    */       }
/* 199:    */     }
/* 200:    */   }
/* 201:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.jobhistory.JhCounters
 * JD-Core Version:    0.7.0.1
 */