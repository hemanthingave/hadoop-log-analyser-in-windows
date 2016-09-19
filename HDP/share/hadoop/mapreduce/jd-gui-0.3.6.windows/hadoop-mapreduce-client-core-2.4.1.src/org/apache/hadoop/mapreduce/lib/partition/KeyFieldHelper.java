/*   1:    */ package org.apache.hadoop.mapreduce.lib.partition;
/*   2:    */ 
/*   3:    */ import java.io.PrintStream;
/*   4:    */ import java.io.UnsupportedEncodingException;
/*   5:    */ import java.util.ArrayList;
/*   6:    */ import java.util.List;
/*   7:    */ import java.util.StringTokenizer;
/*   8:    */ import org.apache.hadoop.util.UTF8ByteArrayUtils;
/*   9:    */ 
/*  10:    */ class KeyFieldHelper
/*  11:    */ {
/*  12:    */   protected static class KeyDescription
/*  13:    */   {
/*  14: 44 */     int beginFieldIdx = 1;
/*  15: 45 */     int beginChar = 1;
/*  16: 46 */     int endFieldIdx = 0;
/*  17: 47 */     int endChar = 0;
/*  18:    */     boolean numeric;
/*  19:    */     boolean reverse;
/*  20:    */     
/*  21:    */     public String toString()
/*  22:    */     {
/*  23: 52 */       return "-k" + this.beginFieldIdx + "." + this.beginChar + "," + this.endFieldIdx + "." + this.endChar + (this.numeric ? "n" : "") + (this.reverse ? "r" : "");
/*  24:    */     }
/*  25:    */   }
/*  26:    */   
/*  27: 59 */   private List<KeyDescription> allKeySpecs = new ArrayList();
/*  28:    */   private byte[] keyFieldSeparator;
/*  29: 61 */   private boolean keySpecSeen = false;
/*  30:    */   
/*  31:    */   public void setKeyFieldSeparator(String keyFieldSeparator)
/*  32:    */   {
/*  33:    */     try
/*  34:    */     {
/*  35: 65 */       this.keyFieldSeparator = keyFieldSeparator.getBytes("UTF-8");
/*  36:    */     }
/*  37:    */     catch (UnsupportedEncodingException e)
/*  38:    */     {
/*  39: 68 */       throw new RuntimeException("The current system does not support UTF-8 encoding!", e);
/*  40:    */     }
/*  41:    */   }
/*  42:    */   
/*  43:    */   public void setKeyFieldSpec(int start, int end)
/*  44:    */   {
/*  45: 76 */     if (end >= start)
/*  46:    */     {
/*  47: 77 */       KeyDescription k = new KeyDescription();
/*  48: 78 */       k.beginFieldIdx = start;
/*  49: 79 */       k.endFieldIdx = end;
/*  50: 80 */       this.keySpecSeen = true;
/*  51: 81 */       this.allKeySpecs.add(k);
/*  52:    */     }
/*  53:    */   }
/*  54:    */   
/*  55:    */   public List<KeyDescription> keySpecs()
/*  56:    */   {
/*  57: 86 */     return this.allKeySpecs;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public int[] getWordLengths(byte[] b, int start, int end)
/*  61:    */   {
/*  62: 93 */     if (!this.keySpecSeen) {
/*  63: 95 */       return new int[] { 1 };
/*  64:    */     }
/*  65: 97 */     int[] lengths = new int[10];
/*  66: 98 */     int currLenLengths = lengths.length;
/*  67: 99 */     int idx = 1;
/*  68:    */     int pos;
/*  69:102 */     while ((pos = UTF8ByteArrayUtils.findBytes(b, start, end, this.keyFieldSeparator)) != -1)
/*  70:    */     {
/*  71:103 */       idx++;
/*  72:103 */       if (idx == currLenLengths)
/*  73:    */       {
/*  74:104 */         int[] temp = lengths;
/*  75:105 */         lengths = new int[currLenLengths *= 2];
/*  76:106 */         System.arraycopy(temp, 0, lengths, 0, temp.length);
/*  77:    */       }
/*  78:108 */       lengths[(idx - 1)] = (pos - start);
/*  79:109 */       start = pos + 1;
/*  80:    */     }
/*  81:112 */     if (start != end) {
/*  82:113 */       lengths[idx] = (end - start);
/*  83:    */     }
/*  84:115 */     lengths[0] = idx;
/*  85:116 */     return lengths;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public int getStartOffset(byte[] b, int start, int end, int[] lengthIndices, KeyDescription k)
/*  89:    */   {
/*  90:122 */     if (lengthIndices[0] >= k.beginFieldIdx)
/*  91:    */     {
/*  92:123 */       int position = 0;
/*  93:124 */       for (int i = 1; i < k.beginFieldIdx; i++) {
/*  94:125 */         position += lengthIndices[i] + this.keyFieldSeparator.length;
/*  95:    */       }
/*  96:127 */       if (position + k.beginChar <= end - start) {
/*  97:128 */         return start + position + k.beginChar - 1;
/*  98:    */       }
/*  99:    */     }
/* 100:131 */     return -1;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public int getEndOffset(byte[] b, int start, int end, int[] lengthIndices, KeyDescription k)
/* 104:    */   {
/* 105:137 */     if (k.endFieldIdx == 0) {
/* 106:140 */       return end - 1;
/* 107:    */     }
/* 108:142 */     if (lengthIndices[0] >= k.endFieldIdx)
/* 109:    */     {
/* 110:143 */       int position = 0;
/* 111:145 */       for (int i = 1; i < k.endFieldIdx; i++) {
/* 112:146 */         position += lengthIndices[i] + this.keyFieldSeparator.length;
/* 113:    */       }
/* 114:148 */       if (k.endChar == 0) {
/* 115:149 */         position += lengthIndices[i];
/* 116:    */       }
/* 117:151 */       if (position + k.endChar <= end - start) {
/* 118:152 */         return start + position + k.endChar - 1;
/* 119:    */       }
/* 120:154 */       return end - 1;
/* 121:    */     }
/* 122:156 */     return end - 1;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public void parseOption(String option)
/* 126:    */   {
/* 127:159 */     if ((option == null) || (option.equals(""))) {
/* 128:161 */       return;
/* 129:    */     }
/* 130:163 */     StringTokenizer args = new StringTokenizer(option);
/* 131:164 */     KeyDescription global = new KeyDescription();
/* 132:165 */     while (args.hasMoreTokens())
/* 133:    */     {
/* 134:166 */       String arg = args.nextToken();
/* 135:167 */       if (arg.equals("-n")) {
/* 136:168 */         global.numeric = true;
/* 137:    */       }
/* 138:170 */       if (arg.equals("-r")) {
/* 139:171 */         global.reverse = true;
/* 140:    */       }
/* 141:173 */       if (arg.equals("-nr"))
/* 142:    */       {
/* 143:174 */         global.numeric = true;
/* 144:175 */         global.reverse = true;
/* 145:    */       }
/* 146:177 */       if (arg.startsWith("-k"))
/* 147:    */       {
/* 148:178 */         KeyDescription k = parseKey(arg, args);
/* 149:179 */         if (k != null)
/* 150:    */         {
/* 151:180 */           this.allKeySpecs.add(k);
/* 152:181 */           this.keySpecSeen = true;
/* 153:    */         }
/* 154:    */       }
/* 155:    */     }
/* 156:185 */     for (KeyDescription key : this.allKeySpecs) {
/* 157:186 */       if (!(key.reverse | key.numeric))
/* 158:    */       {
/* 159:187 */         key.reverse = global.reverse;
/* 160:188 */         key.numeric = global.numeric;
/* 161:    */       }
/* 162:    */     }
/* 163:191 */     if (this.allKeySpecs.size() == 0) {
/* 164:192 */       this.allKeySpecs.add(global);
/* 165:    */     }
/* 166:    */   }
/* 167:    */   
/* 168:    */   private KeyDescription parseKey(String arg, StringTokenizer args)
/* 169:    */   {
/* 170:198 */     String keyArgs = null;
/* 171:199 */     if (arg.length() == 2)
/* 172:    */     {
/* 173:200 */       if (args.hasMoreTokens()) {
/* 174:201 */         keyArgs = args.nextToken();
/* 175:    */       }
/* 176:    */     }
/* 177:    */     else {
/* 178:204 */       keyArgs = arg.substring(2);
/* 179:    */     }
/* 180:206 */     if ((keyArgs == null) || (keyArgs.length() == 0)) {
/* 181:207 */       return null;
/* 182:    */     }
/* 183:209 */     StringTokenizer st = new StringTokenizer(keyArgs, "nr.,", true);
/* 184:    */     
/* 185:211 */     KeyDescription key = new KeyDescription();
/* 186:215 */     if (st.hasMoreTokens())
/* 187:    */     {
/* 188:216 */       String token = st.nextToken();
/* 189:    */       
/* 190:218 */       key.beginFieldIdx = Integer.parseInt(token);
/* 191:    */     }
/* 192:220 */     if (st.hasMoreTokens())
/* 193:    */     {
/* 194:221 */       String token = st.nextToken();
/* 195:222 */       if (token.equals("."))
/* 196:    */       {
/* 197:223 */         token = st.nextToken();
/* 198:224 */         key.beginChar = Integer.parseInt(token);
/* 199:225 */         if (st.hasMoreTokens()) {
/* 200:226 */           token = st.nextToken();
/* 201:    */         } else {
/* 202:228 */           return key;
/* 203:    */         }
/* 204:    */       }
/* 205:    */       for (;;)
/* 206:    */       {
/* 207:232 */         if (token.equals("n"))
/* 208:    */         {
/* 209:233 */           key.numeric = true;
/* 210:    */         }
/* 211:    */         else
/* 212:    */         {
/* 213:235 */           if (!token.equals("r")) {
/* 214:    */             break label210;
/* 215:    */           }
/* 216:236 */           key.reverse = true;
/* 217:    */         }
/* 218:239 */         if (!st.hasMoreTokens()) {
/* 219:    */           break;
/* 220:    */         }
/* 221:240 */         token = st.nextToken();
/* 222:    */       }
/* 223:242 */       return key;
/* 224:    */       label210:
/* 225:245 */       if (token.equals(","))
/* 226:    */       {
/* 227:246 */         token = st.nextToken();
/* 228:    */         
/* 229:248 */         key.endFieldIdx = Integer.parseInt(token);
/* 230:249 */         if (st.hasMoreTokens())
/* 231:    */         {
/* 232:250 */           token = st.nextToken();
/* 233:251 */           if (token.equals("."))
/* 234:    */           {
/* 235:252 */             token = st.nextToken();
/* 236:253 */             key.endChar = Integer.parseInt(token);
/* 237:254 */             if (st.hasMoreTokens()) {
/* 238:255 */               token = st.nextToken();
/* 239:    */             } else {
/* 240:257 */               return key;
/* 241:    */             }
/* 242:    */           }
/* 243:    */           for (;;)
/* 244:    */           {
/* 245:261 */             if (token.equals("n")) {
/* 246:262 */               key.numeric = true;
/* 247:264 */             } else if (token.equals("r")) {
/* 248:265 */               key.reverse = true;
/* 249:    */             } else {
/* 250:268 */               throw new IllegalArgumentException("Invalid -k argument. Must be of the form -k pos1,[pos2], where pos is of the form f[.c]nr");
/* 251:    */             }
/* 252:272 */             if (!st.hasMoreTokens()) {
/* 253:    */               break;
/* 254:    */             }
/* 255:273 */             token = st.nextToken();
/* 256:    */           }
/* 257:    */         }
/* 258:279 */         return key;
/* 259:    */       }
/* 260:281 */       throw new IllegalArgumentException("Invalid -k argument. Must be of the form -k pos1,[pos2], where pos is of the form f[.c]nr");
/* 261:    */     }
/* 262:285 */     return key;
/* 263:    */   }
/* 264:    */   
/* 265:    */   private void printKey(KeyDescription key)
/* 266:    */   {
/* 267:288 */     System.out.println("key.beginFieldIdx: " + key.beginFieldIdx);
/* 268:289 */     System.out.println("key.beginChar: " + key.beginChar);
/* 269:290 */     System.out.println("key.endFieldIdx: " + key.endFieldIdx);
/* 270:291 */     System.out.println("key.endChar: " + key.endChar);
/* 271:292 */     System.out.println("key.numeric: " + key.numeric);
/* 272:293 */     System.out.println("key.reverse: " + key.reverse);
/* 273:294 */     System.out.println("parseKey over");
/* 274:    */   }
/* 275:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.lib.partition.KeyFieldHelper
 * JD-Core Version:    0.7.0.1
 */