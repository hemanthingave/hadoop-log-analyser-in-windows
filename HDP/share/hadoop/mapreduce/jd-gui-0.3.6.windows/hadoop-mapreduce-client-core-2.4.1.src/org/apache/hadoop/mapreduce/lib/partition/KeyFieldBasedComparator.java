/*   1:    */ package org.apache.hadoop.mapreduce.lib.partition;
/*   2:    */ 
/*   3:    */ import java.util.List;
/*   4:    */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*   5:    */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*   6:    */ import org.apache.hadoop.conf.Configurable;
/*   7:    */ import org.apache.hadoop.conf.Configuration;
/*   8:    */ import org.apache.hadoop.io.Text;
/*   9:    */ import org.apache.hadoop.io.WritableComparator;
/*  10:    */ import org.apache.hadoop.io.WritableUtils;
/*  11:    */ import org.apache.hadoop.mapreduce.Job;
/*  12:    */ import org.apache.hadoop.mapreduce.JobContext;
/*  13:    */ 
/*  14:    */ @InterfaceAudience.Public
/*  15:    */ @InterfaceStability.Stable
/*  16:    */ public class KeyFieldBasedComparator<K, V>
/*  17:    */   extends WritableComparator
/*  18:    */   implements Configurable
/*  19:    */ {
/*  20: 55 */   private KeyFieldHelper keyFieldHelper = new KeyFieldHelper();
/*  21: 56 */   public static String COMPARATOR_OPTIONS = "mapreduce.partition.keycomparator.options";
/*  22:    */   private static final byte NEGATIVE = 45;
/*  23:    */   private static final byte ZERO = 48;
/*  24:    */   private static final byte DECIMAL = 46;
/*  25:    */   private Configuration conf;
/*  26:    */   
/*  27:    */   public void setConf(Configuration conf)
/*  28:    */   {
/*  29: 63 */     this.conf = conf;
/*  30: 64 */     String option = conf.get(COMPARATOR_OPTIONS);
/*  31: 65 */     String keyFieldSeparator = conf.get("mapreduce.map.output.key.field.separator", "\t");
/*  32: 66 */     this.keyFieldHelper.setKeyFieldSeparator(keyFieldSeparator);
/*  33: 67 */     this.keyFieldHelper.parseOption(option);
/*  34:    */   }
/*  35:    */   
/*  36:    */   public Configuration getConf()
/*  37:    */   {
/*  38: 71 */     return this.conf;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public KeyFieldBasedComparator()
/*  42:    */   {
/*  43: 75 */     super(Text.class);
/*  44:    */   }
/*  45:    */   
/*  46:    */   public int compare(byte[] b1, int s1, int l1, byte[] b2, int s2, int l2)
/*  47:    */   {
/*  48: 80 */     int n1 = WritableUtils.decodeVIntSize(b1[s1]);
/*  49: 81 */     int n2 = WritableUtils.decodeVIntSize(b2[s2]);
/*  50: 82 */     List<KeyFieldHelper.KeyDescription> allKeySpecs = this.keyFieldHelper.keySpecs();
/*  51: 84 */     if (allKeySpecs.size() == 0) {
/*  52: 85 */       return compareBytes(b1, s1 + n1, l1 - n1, b2, s2 + n2, l2 - n2);
/*  53:    */     }
/*  54: 88 */     int[] lengthIndicesFirst = this.keyFieldHelper.getWordLengths(b1, s1 + n1, s1 + l1);
/*  55:    */     
/*  56: 90 */     int[] lengthIndicesSecond = this.keyFieldHelper.getWordLengths(b2, s2 + n2, s2 + l2);
/*  57: 93 */     for (KeyFieldHelper.KeyDescription keySpec : allKeySpecs)
/*  58:    */     {
/*  59: 94 */       int startCharFirst = this.keyFieldHelper.getStartOffset(b1, s1 + n1, s1 + l1, lengthIndicesFirst, keySpec);
/*  60:    */       
/*  61: 96 */       int endCharFirst = this.keyFieldHelper.getEndOffset(b1, s1 + n1, s1 + l1, lengthIndicesFirst, keySpec);
/*  62:    */       
/*  63: 98 */       int startCharSecond = this.keyFieldHelper.getStartOffset(b2, s2 + n2, s2 + l2, lengthIndicesSecond, keySpec);
/*  64:    */       
/*  65:100 */       int endCharSecond = this.keyFieldHelper.getEndOffset(b2, s2 + n2, s2 + l2, lengthIndicesSecond, keySpec);
/*  66:    */       int result;
/*  67:103 */       if ((result = compareByteSequence(b1, startCharFirst, endCharFirst, b2, startCharSecond, endCharSecond, keySpec)) != 0) {
/*  68:105 */         return result;
/*  69:    */       }
/*  70:    */     }
/*  71:108 */     return 0;
/*  72:    */   }
/*  73:    */   
/*  74:    */   private int compareByteSequence(byte[] first, int start1, int end1, byte[] second, int start2, int end2, KeyFieldHelper.KeyDescription key)
/*  75:    */   {
/*  76:113 */     if (start1 == -1)
/*  77:    */     {
/*  78:114 */       if (key.reverse) {
/*  79:115 */         return 1;
/*  80:    */       }
/*  81:117 */       return -1;
/*  82:    */     }
/*  83:119 */     if (start2 == -1)
/*  84:    */     {
/*  85:120 */       if (key.reverse) {
/*  86:121 */         return -1;
/*  87:    */       }
/*  88:123 */       return 1;
/*  89:    */     }
/*  90:125 */     int compareResult = 0;
/*  91:126 */     if (!key.numeric) {
/*  92:127 */       compareResult = compareBytes(first, start1, end1 - start1 + 1, second, start2, end2 - start2 + 1);
/*  93:    */     }
/*  94:130 */     if (key.numeric) {
/*  95:131 */       compareResult = numericalCompare(first, start1, end1, second, start2, end2);
/*  96:    */     }
/*  97:134 */     if (key.reverse) {
/*  98:135 */       return -compareResult;
/*  99:    */     }
/* 100:137 */     return compareResult;
/* 101:    */   }
/* 102:    */   
/* 103:    */   private int numericalCompare(byte[] a, int start1, int end1, byte[] b, int start2, int end2)
/* 104:    */   {
/* 105:142 */     int i = start1;
/* 106:143 */     int j = start2;
/* 107:144 */     int mul = 1;
/* 108:145 */     byte first_a = a[i];
/* 109:146 */     byte first_b = b[j];
/* 110:147 */     if (first_a == 45)
/* 111:    */     {
/* 112:148 */       if (first_b != 45) {
/* 113:150 */         return oneNegativeCompare(a, start1 + 1, end1, b, start2, end2);
/* 114:    */       }
/* 115:152 */       i++;
/* 116:    */     }
/* 117:154 */     if (first_b == 45)
/* 118:    */     {
/* 119:155 */       if (first_a != 45) {
/* 120:157 */         return -oneNegativeCompare(b, start2 + 1, end2, a, start1, end1);
/* 121:    */       }
/* 122:159 */       j++;
/* 123:    */     }
/* 124:161 */     if ((first_b == 45) && (first_a == 45)) {
/* 125:162 */       mul = -1;
/* 126:    */     }
/* 127:166 */     while ((i <= end1) && 
/* 128:167 */       (a[i] == 48)) {
/* 129:170 */       i++;
/* 130:    */     }
/* 131:172 */     while ((j <= end2) && 
/* 132:173 */       (b[j] == 48)) {
/* 133:176 */       j++;
/* 134:    */     }
/* 135:181 */     for (; (i <= end1) && (j <= end2) && 
/* 136:182 */           (isdigit(a[i])) && (a[i] == b[j]); j++) {
/* 137:185 */       i++;
/* 138:    */     }
/* 139:187 */     if (i <= end1) {
/* 140:188 */       first_a = a[i];
/* 141:    */     }
/* 142:190 */     if (j <= end2) {
/* 143:191 */       first_b = b[j];
/* 144:    */     }
/* 145:195 */     int firstResult = first_a - first_b;
/* 146:198 */     if (((first_a == 46) && ((!isdigit(first_b)) || (j > end2))) || ((first_b == 46) && ((!isdigit(first_a)) || (i > end1)))) {
/* 147:200 */       return mul < 0 ? -decimalCompare(a, i, end1, b, j, end2) : decimalCompare(a, i, end1, b, j, end2);
/* 148:    */     }
/* 149:204 */     int numRemainDigits_a = 0;
/* 150:205 */     int numRemainDigits_b = 0;
/* 151:206 */     while (i <= end1)
/* 152:    */     {
/* 153:209 */       if (!isdigit(a[(i++)])) {
/* 154:    */         break;
/* 155:    */       }
/* 156:210 */       numRemainDigits_a++;
/* 157:    */     }
/* 158:213 */     while (j <= end2)
/* 159:    */     {
/* 160:216 */       if (!isdigit(b[(j++)])) {
/* 161:    */         break;
/* 162:    */       }
/* 163:217 */       numRemainDigits_b++;
/* 164:    */     }
/* 165:220 */     int ret = numRemainDigits_a - numRemainDigits_b;
/* 166:221 */     if (ret == 0) {
/* 167:222 */       return mul < 0 ? -firstResult : firstResult;
/* 168:    */     }
/* 169:224 */     return mul < 0 ? -ret : ret;
/* 170:    */   }
/* 171:    */   
/* 172:    */   private boolean isdigit(byte b)
/* 173:    */   {
/* 174:228 */     if ((48 <= b) && (b <= 57)) {
/* 175:229 */       return true;
/* 176:    */     }
/* 177:231 */     return false;
/* 178:    */   }
/* 179:    */   
/* 180:    */   private int decimalCompare(byte[] a, int i, int end1, byte[] b, int j, int end2)
/* 181:    */   {
/* 182:235 */     if (i > end1) {
/* 183:237 */       return -decimalCompare1(b, ++j, end2);
/* 184:    */     }
/* 185:239 */     if (j > end2) {
/* 186:241 */       return decimalCompare1(a, ++i, end1);
/* 187:    */     }
/* 188:243 */     if ((a[i] == 46) && (b[j] == 46))
/* 189:    */     {
/* 190:244 */       for (; (i <= end1) && (j <= end2); j++)
/* 191:    */       {
/* 192:245 */         if (a[i] != b[j])
/* 193:    */         {
/* 194:246 */           if ((isdigit(a[i])) && (isdigit(b[j]))) {
/* 195:247 */             return a[i] - b[j];
/* 196:    */           }
/* 197:249 */           if (isdigit(a[i])) {
/* 198:250 */             return 1;
/* 199:    */           }
/* 200:252 */           if (isdigit(b[j])) {
/* 201:253 */             return -1;
/* 202:    */           }
/* 203:255 */           return 0;
/* 204:    */         }
/* 205:257 */         i++;
/* 206:    */       }
/* 207:259 */       if ((i > end1) && (j > end2)) {
/* 208:260 */         return 0;
/* 209:    */       }
/* 210:263 */       if (i > end1) {
/* 211:266 */         return -decimalCompare1(b, j, end2);
/* 212:    */       }
/* 213:268 */       if (j > end2) {
/* 214:271 */         return decimalCompare1(a, i, end1);
/* 215:    */       }
/* 216:    */     }
/* 217:    */     else
/* 218:    */     {
/* 219:274 */       if (a[i] == 46) {
/* 220:275 */         return decimalCompare1(a, ++i, end1);
/* 221:    */       }
/* 222:277 */       if (b[j] == 46) {
/* 223:278 */         return -decimalCompare1(b, ++j, end2);
/* 224:    */       }
/* 225:    */     }
/* 226:280 */     return 0;
/* 227:    */   }
/* 228:    */   
/* 229:    */   private int decimalCompare1(byte[] a, int i, int end)
/* 230:    */   {
/* 231:284 */     while (i <= end) {
/* 232:285 */       if (a[i] == 48)
/* 233:    */       {
/* 234:286 */         i++;
/* 235:    */       }
/* 236:    */       else
/* 237:    */       {
/* 238:289 */         if (isdigit(a[i])) {
/* 239:290 */           return 1;
/* 240:    */         }
/* 241:292 */         return 0;
/* 242:    */       }
/* 243:    */     }
/* 244:295 */     return 0;
/* 245:    */   }
/* 246:    */   
/* 247:    */   private int oneNegativeCompare(byte[] a, int start1, int end1, byte[] b, int start2, int end2)
/* 248:    */   {
/* 249:307 */     if (!isZero(a, start1, end1)) {
/* 250:308 */       return -1;
/* 251:    */     }
/* 252:311 */     if (!isZero(b, start2, end2)) {
/* 253:312 */       return -1;
/* 254:    */     }
/* 255:316 */     return 0;
/* 256:    */   }
/* 257:    */   
/* 258:    */   private boolean isZero(byte[] a, int start, int end)
/* 259:    */   {
/* 260:322 */     int i = start;
/* 261:324 */     while (i <= end)
/* 262:    */     {
/* 263:325 */       if (a[i] != 48)
/* 264:    */       {
/* 265:326 */         if ((a[i] == 46) || (!isdigit(a[i]))) {
/* 266:    */           break;
/* 267:    */         }
/* 268:327 */         return false;
/* 269:    */       }
/* 270:331 */       i++;
/* 271:    */     }
/* 272:334 */     if ((i != end + 1) && (a[(i++)] == 46)) {
/* 273:336 */       while (i <= end)
/* 274:    */       {
/* 275:337 */         if (a[i] != 48)
/* 276:    */         {
/* 277:338 */           if (!isdigit(a[i])) {
/* 278:    */             break;
/* 279:    */           }
/* 280:339 */           return false;
/* 281:    */         }
/* 282:343 */         i++;
/* 283:    */       }
/* 284:    */     }
/* 285:346 */     return true;
/* 286:    */   }
/* 287:    */   
/* 288:    */   public static void setKeyFieldComparatorOptions(Job job, String keySpec)
/* 289:    */   {
/* 290:364 */     job.getConfiguration().set(COMPARATOR_OPTIONS, keySpec);
/* 291:    */   }
/* 292:    */   
/* 293:    */   public static String getKeyFieldComparatorOption(JobContext job)
/* 294:    */   {
/* 295:371 */     return job.getConfiguration().get(COMPARATOR_OPTIONS);
/* 296:    */   }
/* 297:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.lib.partition.KeyFieldBasedComparator
 * JD-Core Version:    0.7.0.1
 */