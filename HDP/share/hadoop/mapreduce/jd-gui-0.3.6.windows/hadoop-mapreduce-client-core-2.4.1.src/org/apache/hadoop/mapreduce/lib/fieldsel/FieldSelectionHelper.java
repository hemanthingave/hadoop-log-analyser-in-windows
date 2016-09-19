/*   1:    */ package org.apache.hadoop.mapreduce.lib.fieldsel;
/*   2:    */ 
/*   3:    */ import java.util.List;
/*   4:    */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*   5:    */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*   6:    */ import org.apache.hadoop.io.Text;
/*   7:    */ 
/*   8:    */ @InterfaceAudience.Public
/*   9:    */ @InterfaceStability.Stable
/*  10:    */ public class FieldSelectionHelper
/*  11:    */ {
/*  12: 62 */   public static Text emptyText = new Text("");
/*  13:    */   public static final String DATA_FIELD_SEPERATOR = "mapreduce.fieldsel.data.field.separator";
/*  14:    */   public static final String MAP_OUTPUT_KEY_VALUE_SPEC = "mapreduce.fieldsel.map.output.key.value.fields.spec";
/*  15:    */   public static final String REDUCE_OUTPUT_KEY_VALUE_SPEC = "mapreduce.fieldsel.reduce.output.key.value.fields.spec";
/*  16:    */   
/*  17:    */   private static int extractFields(String[] fieldListSpec, List<Integer> fieldList)
/*  18:    */   {
/*  19: 81 */     int allFieldsFrom = -1;
/*  20: 82 */     int i = 0;
/*  21: 83 */     int j = 0;
/*  22: 84 */     int pos = -1;
/*  23: 85 */     String fieldSpec = null;
/*  24: 86 */     for (i = 0; i < fieldListSpec.length; i++)
/*  25:    */     {
/*  26: 87 */       fieldSpec = fieldListSpec[i];
/*  27: 88 */       if (fieldSpec.length() != 0)
/*  28:    */       {
/*  29: 91 */         pos = fieldSpec.indexOf('-');
/*  30: 92 */         if (pos < 0)
/*  31:    */         {
/*  32: 93 */           Integer fn = new Integer(fieldSpec);
/*  33: 94 */           fieldList.add(fn);
/*  34:    */         }
/*  35:    */         else
/*  36:    */         {
/*  37: 96 */           String start = fieldSpec.substring(0, pos);
/*  38: 97 */           String end = fieldSpec.substring(pos + 1);
/*  39: 98 */           if (start.length() == 0) {
/*  40: 99 */             start = "0";
/*  41:    */           }
/*  42:101 */           if (end.length() == 0)
/*  43:    */           {
/*  44:102 */             allFieldsFrom = Integer.parseInt(start);
/*  45:    */           }
/*  46:    */           else
/*  47:    */           {
/*  48:105 */             int startPos = Integer.parseInt(start);
/*  49:106 */             int endPos = Integer.parseInt(end);
/*  50:107 */             for (j = startPos; j <= endPos; j++) {
/*  51:108 */               fieldList.add(Integer.valueOf(j));
/*  52:    */             }
/*  53:    */           }
/*  54:    */         }
/*  55:    */       }
/*  56:    */     }
/*  57:112 */     return allFieldsFrom;
/*  58:    */   }
/*  59:    */   
/*  60:    */   private static String selectFields(String[] fields, List<Integer> fieldList, int allFieldsFrom, String separator)
/*  61:    */   {
/*  62:117 */     String retv = null;
/*  63:118 */     int i = 0;
/*  64:119 */     StringBuffer sb = null;
/*  65:120 */     if ((fieldList != null) && (fieldList.size() > 0))
/*  66:    */     {
/*  67:121 */       if (sb == null) {
/*  68:122 */         sb = new StringBuffer();
/*  69:    */       }
/*  70:124 */       for (Integer index : fieldList)
/*  71:    */       {
/*  72:125 */         if (index.intValue() < fields.length) {
/*  73:126 */           sb.append(fields[index.intValue()]);
/*  74:    */         }
/*  75:128 */         sb.append(separator);
/*  76:    */       }
/*  77:    */     }
/*  78:131 */     if (allFieldsFrom >= 0)
/*  79:    */     {
/*  80:132 */       if (sb == null) {
/*  81:133 */         sb = new StringBuffer();
/*  82:    */       }
/*  83:135 */       for (i = allFieldsFrom; i < fields.length; i++) {
/*  84:136 */         sb.append(fields[i]).append(separator);
/*  85:    */       }
/*  86:    */     }
/*  87:139 */     if (sb != null)
/*  88:    */     {
/*  89:140 */       retv = sb.toString();
/*  90:141 */       if (retv.length() > 0) {
/*  91:142 */         retv = retv.substring(0, retv.length() - 1);
/*  92:    */       }
/*  93:    */     }
/*  94:145 */     return retv;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public static int parseOutputKeyValueSpec(String keyValueSpec, List<Integer> keyFieldList, List<Integer> valueFieldList)
/*  98:    */   {
/*  99:150 */     String[] keyValSpecs = keyValueSpec.split(":", -1);
/* 100:    */     
/* 101:152 */     String[] keySpec = keyValSpecs[0].split(",");
/* 102:    */     
/* 103:154 */     String[] valSpec = new String[0];
/* 104:155 */     if (keyValSpecs.length > 1) {
/* 105:156 */       valSpec = keyValSpecs[1].split(",");
/* 106:    */     }
/* 107:159 */     extractFields(keySpec, keyFieldList);
/* 108:160 */     return extractFields(valSpec, valueFieldList);
/* 109:    */   }
/* 110:    */   
/* 111:    */   public static String specToString(String fieldSeparator, String keyValueSpec, int allValueFieldsFrom, List<Integer> keyFieldList, List<Integer> valueFieldList)
/* 112:    */   {
/* 113:166 */     StringBuffer sb = new StringBuffer();
/* 114:167 */     sb.append("fieldSeparator: ").append(fieldSeparator).append("\n");
/* 115:    */     
/* 116:169 */     sb.append("keyValueSpec: ").append(keyValueSpec).append("\n");
/* 117:170 */     sb.append("allValueFieldsFrom: ").append(allValueFieldsFrom);
/* 118:171 */     sb.append("\n");
/* 119:172 */     sb.append("keyFieldList.length: ").append(keyFieldList.size());
/* 120:173 */     sb.append("\n");
/* 121:174 */     for (Integer field : keyFieldList) {
/* 122:175 */       sb.append("\t").append(field).append("\n");
/* 123:    */     }
/* 124:177 */     sb.append("valueFieldList.length: ").append(valueFieldList.size());
/* 125:178 */     sb.append("\n");
/* 126:179 */     for (Integer field : valueFieldList) {
/* 127:180 */       sb.append("\t").append(field).append("\n");
/* 128:    */     }
/* 129:182 */     return sb.toString();
/* 130:    */   }
/* 131:    */   
/* 132:185 */   private Text key = null;
/* 133:186 */   private Text value = null;
/* 134:    */   
/* 135:    */   public FieldSelectionHelper() {}
/* 136:    */   
/* 137:    */   public FieldSelectionHelper(Text key, Text val)
/* 138:    */   {
/* 139:192 */     this.key = key;
/* 140:193 */     this.value = val;
/* 141:    */   }
/* 142:    */   
/* 143:    */   public Text getKey()
/* 144:    */   {
/* 145:197 */     return this.key;
/* 146:    */   }
/* 147:    */   
/* 148:    */   public Text getValue()
/* 149:    */   {
/* 150:201 */     return this.value;
/* 151:    */   }
/* 152:    */   
/* 153:    */   public void extractOutputKeyValue(String key, String val, String fieldSep, List<Integer> keyFieldList, List<Integer> valFieldList, int allValueFieldsFrom, boolean ignoreKey, boolean isMap)
/* 154:    */   {
/* 155:207 */     if (!ignoreKey) {
/* 156:208 */       val = key + val;
/* 157:    */     }
/* 158:210 */     String[] fields = val.split(fieldSep);
/* 159:    */     
/* 160:212 */     String newKey = selectFields(fields, keyFieldList, -1, fieldSep);
/* 161:213 */     String newVal = selectFields(fields, valFieldList, allValueFieldsFrom, fieldSep);
/* 162:215 */     if ((isMap) && (newKey == null))
/* 163:    */     {
/* 164:216 */       newKey = newVal;
/* 165:217 */       newVal = null;
/* 166:    */     }
/* 167:220 */     if (newKey != null) {
/* 168:221 */       this.key = new Text(newKey);
/* 169:    */     }
/* 170:223 */     if (newVal != null) {
/* 171:224 */       this.value = new Text(newVal);
/* 172:    */     }
/* 173:    */   }
/* 174:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.lib.fieldsel.FieldSelectionHelper
 * JD-Core Version:    0.7.0.1
 */