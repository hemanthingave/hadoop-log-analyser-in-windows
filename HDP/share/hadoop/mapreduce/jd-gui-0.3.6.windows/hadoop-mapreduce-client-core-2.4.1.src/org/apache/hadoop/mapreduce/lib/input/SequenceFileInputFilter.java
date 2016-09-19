/*   1:    */ package org.apache.hadoop.mapreduce.lib.input;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.nio.ByteBuffer;
/*   5:    */ import java.security.DigestException;
/*   6:    */ import java.security.MessageDigest;
/*   7:    */ import java.security.NoSuchAlgorithmException;
/*   8:    */ import java.util.regex.Matcher;
/*   9:    */ import java.util.regex.Pattern;
/*  10:    */ import java.util.regex.PatternSyntaxException;
/*  11:    */ import org.apache.commons.logging.Log;
/*  12:    */ import org.apache.commons.logging.LogFactory;
/*  13:    */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*  14:    */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*  15:    */ import org.apache.hadoop.conf.Configurable;
/*  16:    */ import org.apache.hadoop.conf.Configuration;
/*  17:    */ import org.apache.hadoop.io.BytesWritable;
/*  18:    */ import org.apache.hadoop.io.Text;
/*  19:    */ import org.apache.hadoop.mapreduce.InputSplit;
/*  20:    */ import org.apache.hadoop.mapreduce.Job;
/*  21:    */ import org.apache.hadoop.mapreduce.RecordReader;
/*  22:    */ import org.apache.hadoop.mapreduce.TaskAttemptContext;
/*  23:    */ import org.apache.hadoop.util.ReflectionUtils;
/*  24:    */ 
/*  25:    */ @InterfaceAudience.Public
/*  26:    */ @InterfaceStability.Stable
/*  27:    */ public class SequenceFileInputFilter<K, V>
/*  28:    */   extends SequenceFileInputFormat<K, V>
/*  29:    */ {
/*  30: 51 */   public static final Log LOG = LogFactory.getLog(FileInputFormat.class);
/*  31:    */   public static final String FILTER_CLASS = "mapreduce.input.sequencefileinputfilter.class";
/*  32:    */   public static final String FILTER_FREQUENCY = "mapreduce.input.sequencefileinputfilter.frequency";
/*  33:    */   public static final String FILTER_REGEX = "mapreduce.input.sequencefileinputfilter.regex";
/*  34:    */   
/*  35:    */   public RecordReader<K, V> createRecordReader(InputSplit split, TaskAttemptContext context)
/*  36:    */     throws IOException
/*  37:    */   {
/*  38: 70 */     context.setStatus(split.toString());
/*  39: 71 */     return new FilterRecordReader(context.getConfiguration());
/*  40:    */   }
/*  41:    */   
/*  42:    */   public static void setFilterClass(Job job, Class<?> filterClass)
/*  43:    */   {
/*  44: 81 */     job.getConfiguration().set("mapreduce.input.sequencefileinputfilter.class", filterClass.getName());
/*  45:    */   }
/*  46:    */   
/*  47:    */   public static abstract interface Filter
/*  48:    */     extends Configurable
/*  49:    */   {
/*  50:    */     public abstract boolean accept(Object paramObject);
/*  51:    */   }
/*  52:    */   
/*  53:    */   public static abstract class FilterBase
/*  54:    */     implements SequenceFileInputFilter.Filter
/*  55:    */   {
/*  56:    */     Configuration conf;
/*  57:    */     
/*  58:    */     public Configuration getConf()
/*  59:    */     {
/*  60:104 */       return this.conf;
/*  61:    */     }
/*  62:    */   }
/*  63:    */   
/*  64:    */   public static class RegexFilter
/*  65:    */     extends SequenceFileInputFilter.FilterBase
/*  66:    */   {
/*  67:    */     private Pattern p;
/*  68:    */     
/*  69:    */     public static void setPattern(Configuration conf, String regex)
/*  70:    */       throws PatternSyntaxException
/*  71:    */     {
/*  72:    */       try
/*  73:    */       {
/*  74:119 */         Pattern.compile(regex);
/*  75:    */       }
/*  76:    */       catch (PatternSyntaxException e)
/*  77:    */       {
/*  78:121 */         throw new IllegalArgumentException("Invalid pattern: " + regex);
/*  79:    */       }
/*  80:123 */       conf.set("mapreduce.input.sequencefileinputfilter.regex", regex);
/*  81:    */     }
/*  82:    */     
/*  83:    */     public void setConf(Configuration conf)
/*  84:    */     {
/*  85:131 */       String regex = conf.get("mapreduce.input.sequencefileinputfilter.regex");
/*  86:132 */       if (regex == null) {
/*  87:133 */         throw new RuntimeException("mapreduce.input.sequencefileinputfilter.regexnot set");
/*  88:    */       }
/*  89:134 */       this.p = Pattern.compile(regex);
/*  90:135 */       this.conf = conf;
/*  91:    */     }
/*  92:    */     
/*  93:    */     public boolean accept(Object key)
/*  94:    */     {
/*  95:144 */       return this.p.matcher(key.toString()).matches();
/*  96:    */     }
/*  97:    */   }
/*  98:    */   
/*  99:    */   public static class PercentFilter
/* 100:    */     extends SequenceFileInputFilter.FilterBase
/* 101:    */   {
/* 102:    */     private int frequency;
/* 103:    */     private int count;
/* 104:    */     
/* 105:    */     public static void setFrequency(Configuration conf, int frequency)
/* 106:    */     {
/* 107:162 */       if (frequency <= 0) {
/* 108:163 */         throw new IllegalArgumentException("Negative mapreduce.input.sequencefileinputfilter.frequency: " + frequency);
/* 109:    */       }
/* 110:165 */       conf.setInt("mapreduce.input.sequencefileinputfilter.frequency", frequency);
/* 111:    */     }
/* 112:    */     
/* 113:    */     public void setConf(Configuration conf)
/* 114:    */     {
/* 115:175 */       this.frequency = conf.getInt("mapreduce.input.sequencefileinputfilter.frequency", 10);
/* 116:176 */       if (this.frequency <= 0) {
/* 117:177 */         throw new RuntimeException("Negative mapreduce.input.sequencefileinputfilter.frequency: " + this.frequency);
/* 118:    */       }
/* 119:180 */       this.conf = conf;
/* 120:    */     }
/* 121:    */     
/* 122:    */     public boolean accept(Object key)
/* 123:    */     {
/* 124:188 */       boolean accepted = false;
/* 125:189 */       if (this.count == 0) {
/* 126:190 */         accepted = true;
/* 127:    */       }
/* 128:191 */       if (++this.count == this.frequency) {
/* 129:192 */         this.count = 0;
/* 130:    */       }
/* 131:194 */       return accepted;
/* 132:    */     }
/* 133:    */   }
/* 134:    */   
/* 135:    */   public static class MD5Filter
/* 136:    */     extends SequenceFileInputFilter.FilterBase
/* 137:    */   {
/* 138:    */     private int frequency;
/* 139:    */     private static final MessageDigest DIGESTER;
/* 140:    */     public static final int MD5_LEN = 16;
/* 141:206 */     private byte[] digest = new byte[16];
/* 142:    */     
/* 143:    */     static
/* 144:    */     {
/* 145:    */       try
/* 146:    */       {
/* 147:210 */         DIGESTER = MessageDigest.getInstance("MD5");
/* 148:    */       }
/* 149:    */       catch (NoSuchAlgorithmException e)
/* 150:    */       {
/* 151:212 */         throw new RuntimeException(e);
/* 152:    */       }
/* 153:    */     }
/* 154:    */     
/* 155:    */     public static void setFrequency(Configuration conf, int frequency)
/* 156:    */     {
/* 157:223 */       if (frequency <= 0) {
/* 158:224 */         throw new IllegalArgumentException("Negative mapreduce.input.sequencefileinputfilter.frequency: " + frequency);
/* 159:    */       }
/* 160:226 */       conf.setInt("mapreduce.input.sequencefileinputfilter.frequency", frequency);
/* 161:    */     }
/* 162:    */     
/* 163:    */     public void setConf(Configuration conf)
/* 164:    */     {
/* 165:236 */       this.frequency = conf.getInt("mapreduce.input.sequencefileinputfilter.frequency", 10);
/* 166:237 */       if (this.frequency <= 0) {
/* 167:238 */         throw new RuntimeException("Negative mapreduce.input.sequencefileinputfilter.frequency: " + this.frequency);
/* 168:    */       }
/* 169:241 */       this.conf = conf;
/* 170:    */     }
/* 171:    */     
/* 172:    */     public boolean accept(Object key)
/* 173:    */     {
/* 174:    */       try
/* 175:    */       {
/* 176:    */         long hashcode;
/* 177:    */         long hashcode;
/* 178:251 */         if ((key instanceof Text))
/* 179:    */         {
/* 180:252 */           hashcode = MD5Hashcode((Text)key);
/* 181:    */         }
/* 182:    */         else
/* 183:    */         {
/* 184:    */           long hashcode;
/* 185:253 */           if ((key instanceof BytesWritable))
/* 186:    */           {
/* 187:254 */             hashcode = MD5Hashcode((BytesWritable)key);
/* 188:    */           }
/* 189:    */           else
/* 190:    */           {
/* 191:257 */             ByteBuffer bb = Text.encode(key.toString());
/* 192:258 */             hashcode = MD5Hashcode(bb.array(), 0, bb.limit());
/* 193:    */           }
/* 194:    */         }
/* 195:260 */         if (hashcode / this.frequency * this.frequency == hashcode) {
/* 196:261 */           return true;
/* 197:    */         }
/* 198:    */       }
/* 199:    */       catch (Exception e)
/* 200:    */       {
/* 201:263 */         SequenceFileInputFilter.LOG.warn(e);
/* 202:264 */         throw new RuntimeException(e);
/* 203:    */       }
/* 204:266 */       return false;
/* 205:    */     }
/* 206:    */     
/* 207:    */     private long MD5Hashcode(Text key)
/* 208:    */       throws DigestException
/* 209:    */     {
/* 210:270 */       return MD5Hashcode(key.getBytes(), 0, key.getLength());
/* 211:    */     }
/* 212:    */     
/* 213:    */     private long MD5Hashcode(BytesWritable key)
/* 214:    */       throws DigestException
/* 215:    */     {
/* 216:274 */       return MD5Hashcode(key.getBytes(), 0, key.getLength());
/* 217:    */     }
/* 218:    */     
/* 219:    */     private synchronized long MD5Hashcode(byte[] bytes, int start, int length)
/* 220:    */       throws DigestException
/* 221:    */     {
/* 222:279 */       DIGESTER.update(bytes, 0, length);
/* 223:280 */       DIGESTER.digest(this.digest, 0, 16);
/* 224:281 */       long hashcode = 0L;
/* 225:282 */       for (int i = 0; i < 8; i++) {
/* 226:283 */         hashcode |= (this.digest[i] & 0xFF) << 8 * (7 - i);
/* 227:    */       }
/* 228:284 */       return hashcode;
/* 229:    */     }
/* 230:    */   }
/* 231:    */   
/* 232:    */   private static class FilterRecordReader<K, V>
/* 233:    */     extends SequenceFileRecordReader<K, V>
/* 234:    */   {
/* 235:    */     private SequenceFileInputFilter.Filter filter;
/* 236:    */     private K key;
/* 237:    */     private V value;
/* 238:    */     
/* 239:    */     public FilterRecordReader(Configuration conf)
/* 240:    */       throws IOException
/* 241:    */     {
/* 242:299 */       this.filter = ((SequenceFileInputFilter.Filter)ReflectionUtils.newInstance(conf.getClass("mapreduce.input.sequencefileinputfilter.class", SequenceFileInputFilter.PercentFilter.class), conf));
/* 243:    */     }
/* 244:    */     
/* 245:    */     public synchronized boolean nextKeyValue()
/* 246:    */       throws IOException, InterruptedException
/* 247:    */     {
/* 248:305 */       while (super.nextKeyValue())
/* 249:    */       {
/* 250:306 */         this.key = super.getCurrentKey();
/* 251:307 */         if (this.filter.accept(this.key))
/* 252:    */         {
/* 253:308 */           this.value = super.getCurrentValue();
/* 254:309 */           return true;
/* 255:    */         }
/* 256:    */       }
/* 257:312 */       return false;
/* 258:    */     }
/* 259:    */     
/* 260:    */     public K getCurrentKey()
/* 261:    */     {
/* 262:317 */       return this.key;
/* 263:    */     }
/* 264:    */     
/* 265:    */     public V getCurrentValue()
/* 266:    */     {
/* 267:322 */       return this.value;
/* 268:    */     }
/* 269:    */   }
/* 270:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.lib.input.SequenceFileInputFilter
 * JD-Core Version:    0.7.0.1
 */