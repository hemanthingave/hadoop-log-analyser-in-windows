/*   1:    */ package org.apache.hadoop.mapreduce.util;
/*   2:    */ 
/*   3:    */ import com.google.common.collect.Lists;
/*   4:    */ import java.text.ParseException;
/*   5:    */ import java.util.List;
/*   6:    */ import org.apache.hadoop.classification.InterfaceAudience.Private;
/*   7:    */ import org.apache.hadoop.io.IntWritable;
/*   8:    */ import org.apache.hadoop.mapreduce.Counter;
/*   9:    */ import org.apache.hadoop.mapreduce.counters.AbstractCounters;
/*  10:    */ import org.apache.hadoop.mapreduce.counters.CounterGroupBase;
/*  11:    */ import org.apache.hadoop.util.StringInterner;
/*  12:    */ import org.apache.hadoop.util.StringUtils;
/*  13:    */ 
/*  14:    */ @InterfaceAudience.Private
/*  15:    */ public class CountersStrings
/*  16:    */ {
/*  17:    */   private static final char GROUP_OPEN = '{';
/*  18:    */   private static final char GROUP_CLOSE = '}';
/*  19:    */   private static final char COUNTER_OPEN = '[';
/*  20:    */   private static final char COUNTER_CLOSE = ']';
/*  21:    */   private static final char UNIT_OPEN = '(';
/*  22:    */   private static final char UNIT_CLOSE = ')';
/*  23: 46 */   private static char[] charsToEscape = { '{', '}', '[', ']', '(', ')' };
/*  24:    */   
/*  25:    */   public static String toEscapedCompactString(Counter counter)
/*  26:    */   {
/*  27:    */     String escapedName;
/*  28:    */     String escapedDispName;
/*  29:    */     long currentValue;
/*  30: 61 */     synchronized (counter)
/*  31:    */     {
/*  32: 62 */       escapedName = escape(counter.getName());
/*  33: 63 */       escapedDispName = escape(counter.getDisplayName());
/*  34: 64 */       currentValue = counter.getValue();
/*  35:    */     }
/*  36: 66 */     int length = escapedName.length() + escapedDispName.length() + 4;
/*  37:    */     
/*  38:    */ 
/*  39: 69 */     length += 8;
/*  40: 70 */     StringBuilder builder = new StringBuilder(length);
/*  41: 71 */     builder.append('[');
/*  42:    */     
/*  43:    */ 
/*  44: 74 */     builder.append('(');
/*  45: 75 */     builder.append(escapedName);
/*  46: 76 */     builder.append(')');
/*  47:    */     
/*  48:    */ 
/*  49: 79 */     builder.append('(');
/*  50: 80 */     builder.append(escapedDispName);
/*  51: 81 */     builder.append(')');
/*  52:    */     
/*  53:    */ 
/*  54: 84 */     builder.append('(');
/*  55: 85 */     builder.append(currentValue);
/*  56: 86 */     builder.append(')');
/*  57:    */     
/*  58: 88 */     builder.append(']');
/*  59:    */     
/*  60: 90 */     return builder.toString();
/*  61:    */   }
/*  62:    */   
/*  63:    */   public static <G extends CounterGroupBase<?>> String toEscapedCompactString(G group)
/*  64:    */   {
/*  65:103 */     List<String> escapedStrs = Lists.newArrayList();
/*  66:    */     String escapedName;
/*  67:    */     String escapedDispName;
/*  68:    */     int length;
/*  69:106 */     synchronized (group)
/*  70:    */     {
/*  71:109 */       escapedName = escape(group.getName());
/*  72:110 */       escapedDispName = escape(group.getDisplayName());
/*  73:111 */       int i = 0;
/*  74:112 */       length = escapedName.length() + escapedDispName.length();
/*  75:113 */       for (Counter counter : group)
/*  76:    */       {
/*  77:114 */         String escapedStr = toEscapedCompactString(counter);
/*  78:115 */         escapedStrs.add(escapedStr);
/*  79:116 */         length += escapedStr.length();
/*  80:    */       }
/*  81:    */     }
/*  82:119 */     length += 6;
/*  83:120 */     StringBuilder builder = new StringBuilder(length);
/*  84:121 */     builder.append('{');
/*  85:    */     
/*  86:    */ 
/*  87:124 */     builder.append('(');
/*  88:125 */     builder.append(escapedName);
/*  89:126 */     builder.append(')');
/*  90:    */     
/*  91:    */ 
/*  92:129 */     builder.append('(');
/*  93:130 */     builder.append(escapedDispName);
/*  94:131 */     builder.append(')');
/*  95:134 */     for (String escaped : escapedStrs) {
/*  96:135 */       builder.append(escaped);
/*  97:    */     }
/*  98:138 */     builder.append('}');
/*  99:139 */     return builder.toString();
/* 100:    */   }
/* 101:    */   
/* 102:    */   public static <C extends Counter, G extends CounterGroupBase<C>, T extends AbstractCounters<C, G>> String toEscapedCompactString(T counters)
/* 103:    */   {
/* 104:155 */     int length = 0;
/* 105:    */     String[] groupsArray;
/* 106:    */     int i;
/* 107:156 */     synchronized (counters)
/* 108:    */     {
/* 109:157 */       groupsArray = new String[counters.countCounters()];
/* 110:158 */       i = 0;
/* 111:161 */       for (G group : counters)
/* 112:    */       {
/* 113:162 */         String escapedString = toEscapedCompactString(group);
/* 114:163 */         groupsArray[(i++)] = escapedString;
/* 115:164 */         length += escapedString.length();
/* 116:    */       }
/* 117:    */     }
/* 118:169 */     StringBuilder builder = new StringBuilder(length);
/* 119:170 */     for (String group : groupsArray) {
/* 120:171 */       builder.append(group);
/* 121:    */     }
/* 122:173 */     return builder.toString();
/* 123:    */   }
/* 124:    */   
/* 125:    */   private static String escape(String string)
/* 126:    */   {
/* 127:178 */     return StringUtils.escapeString(string, '\\', charsToEscape);
/* 128:    */   }
/* 129:    */   
/* 130:    */   private static String unescape(String string)
/* 131:    */   {
/* 132:184 */     return StringUtils.unEscapeString(string, '\\', charsToEscape);
/* 133:    */   }
/* 134:    */   
/* 135:    */   private static String getBlock(String str, char open, char close, IntWritable index)
/* 136:    */     throws ParseException
/* 137:    */   {
/* 138:193 */     StringBuilder split = new StringBuilder();
/* 139:194 */     int next = StringUtils.findNext(str, open, '\\', index.get(), split);
/* 140:    */     
/* 141:196 */     split.setLength(0);
/* 142:197 */     if (next >= 0)
/* 143:    */     {
/* 144:198 */       next++;
/* 145:    */       
/* 146:200 */       next = StringUtils.findNext(str, close, '\\', next, split);
/* 147:202 */       if (next >= 0)
/* 148:    */       {
/* 149:203 */         next++;
/* 150:204 */         index.set(next);
/* 151:205 */         return split.toString();
/* 152:    */       }
/* 153:207 */       throw new ParseException("Unexpected end of block", next);
/* 154:    */     }
/* 155:210 */     return null;
/* 156:    */   }
/* 157:    */   
/* 158:    */   public static <C extends Counter, G extends CounterGroupBase<C>, T extends AbstractCounters<C, G>> T parseEscapedCompactString(String compactString, T counters)
/* 159:    */     throws ParseException
/* 160:    */   {
/* 161:228 */     IntWritable index = new IntWritable(0);
/* 162:    */     
/* 163:    */ 
/* 164:231 */     String groupString = getBlock(compactString, '{', '}', index);
/* 165:234 */     while (groupString != null)
/* 166:    */     {
/* 167:235 */       IntWritable groupIndex = new IntWritable(0);
/* 168:    */       
/* 169:    */ 
/* 170:238 */       String groupName = StringInterner.weakIntern(getBlock(groupString, '(', ')', groupIndex));
/* 171:    */       
/* 172:240 */       groupName = StringInterner.weakIntern(unescape(groupName));
/* 173:    */       
/* 174:    */ 
/* 175:243 */       String groupDisplayName = StringInterner.weakIntern(getBlock(groupString, '(', ')', groupIndex));
/* 176:    */       
/* 177:245 */       groupDisplayName = StringInterner.weakIntern(unescape(groupDisplayName));
/* 178:    */       
/* 179:    */ 
/* 180:248 */       G group = counters.getGroup(groupName);
/* 181:249 */       group.setDisplayName(groupDisplayName);
/* 182:    */       
/* 183:251 */       String counterString = getBlock(groupString, '[', ']', groupIndex);
/* 184:254 */       while (counterString != null)
/* 185:    */       {
/* 186:255 */         IntWritable counterIndex = new IntWritable(0);
/* 187:    */         
/* 188:    */ 
/* 189:258 */         String counterName = StringInterner.weakIntern(getBlock(counterString, '(', ')', counterIndex));
/* 190:    */         
/* 191:260 */         counterName = StringInterner.weakIntern(unescape(counterName));
/* 192:    */         
/* 193:    */ 
/* 194:263 */         String counterDisplayName = StringInterner.weakIntern(getBlock(counterString, '(', ')', counterIndex));
/* 195:    */         
/* 196:265 */         counterDisplayName = StringInterner.weakIntern(unescape(counterDisplayName));
/* 197:    */         
/* 198:    */ 
/* 199:268 */         long value = Long.parseLong(getBlock(counterString, '(', ')', counterIndex));
/* 200:    */         
/* 201:    */ 
/* 202:    */ 
/* 203:    */ 
/* 204:273 */         Counter counter = group.findCounter(counterName);
/* 205:274 */         counter.setDisplayName(counterDisplayName);
/* 206:275 */         counter.increment(value);
/* 207:    */         
/* 208:    */ 
/* 209:278 */         counterString = getBlock(groupString, '[', ']', groupIndex);
/* 210:    */       }
/* 211:282 */       groupString = getBlock(compactString, '{', '}', index);
/* 212:    */     }
/* 213:284 */     return counters;
/* 214:    */   }
/* 215:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.util.CountersStrings
 * JD-Core Version:    0.7.0.1
 */