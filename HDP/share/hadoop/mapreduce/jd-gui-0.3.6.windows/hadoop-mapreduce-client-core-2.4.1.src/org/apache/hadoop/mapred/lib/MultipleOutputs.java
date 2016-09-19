/*   1:    */ package org.apache.hadoop.mapred.lib;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import java.util.Collections;
/*   6:    */ import java.util.HashMap;
/*   7:    */ import java.util.HashSet;
/*   8:    */ import java.util.Iterator;
/*   9:    */ import java.util.List;
/*  10:    */ import java.util.Map;
/*  11:    */ import java.util.Set;
/*  12:    */ import java.util.StringTokenizer;
/*  13:    */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*  14:    */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*  15:    */ import org.apache.hadoop.fs.FileSystem;
/*  16:    */ import org.apache.hadoop.mapred.FileOutputFormat;
/*  17:    */ import org.apache.hadoop.mapred.JobConf;
/*  18:    */ import org.apache.hadoop.mapred.OutputCollector;
/*  19:    */ import org.apache.hadoop.mapred.OutputFormat;
/*  20:    */ import org.apache.hadoop.mapred.RecordWriter;
/*  21:    */ import org.apache.hadoop.mapred.Reporter;
/*  22:    */ import org.apache.hadoop.util.Progressable;
/*  23:    */ 
/*  24:    */ @InterfaceAudience.Public
/*  25:    */ @InterfaceStability.Stable
/*  26:    */ public class MultipleOutputs
/*  27:    */ {
/*  28:    */   private static final String NAMED_OUTPUTS = "mo.namedOutputs";
/*  29:    */   private static final String MO_PREFIX = "mo.namedOutput.";
/*  30:    */   private static final String FORMAT = ".format";
/*  31:    */   private static final String KEY = ".key";
/*  32:    */   private static final String VALUE = ".value";
/*  33:    */   private static final String MULTI = ".multi";
/*  34:    */   private static final String COUNTERS_ENABLED = "mo.counters";
/*  35:134 */   private static final String COUNTERS_GROUP = MultipleOutputs.class.getName();
/*  36:    */   private JobConf conf;
/*  37:    */   private OutputFormat outputFormat;
/*  38:    */   private Set<String> namedOutputs;
/*  39:    */   private Map<String, RecordWriter> recordWriters;
/*  40:    */   private boolean countersEnabled;
/*  41:    */   
/*  42:    */   private static void checkNamedOutput(JobConf conf, String namedOutput, boolean alreadyDefined)
/*  43:    */   {
/*  44:149 */     List<String> definedChannels = getNamedOutputsList(conf);
/*  45:150 */     if ((alreadyDefined) && (definedChannels.contains(namedOutput))) {
/*  46:151 */       throw new IllegalArgumentException("Named output '" + namedOutput + "' already alreadyDefined");
/*  47:    */     }
/*  48:153 */     if ((!alreadyDefined) && (!definedChannels.contains(namedOutput))) {
/*  49:154 */       throw new IllegalArgumentException("Named output '" + namedOutput + "' not defined");
/*  50:    */     }
/*  51:    */   }
/*  52:    */   
/*  53:    */   private static void checkTokenName(String namedOutput)
/*  54:    */   {
/*  55:166 */     if ((namedOutput == null) || (namedOutput.length() == 0)) {
/*  56:167 */       throw new IllegalArgumentException("Name cannot be NULL or emtpy");
/*  57:    */     }
/*  58:170 */     for (char ch : namedOutput.toCharArray()) {
/*  59:171 */       if ((ch < 'A') || (ch > 'Z')) {
/*  60:174 */         if ((ch < 'a') || (ch > 'z')) {
/*  61:177 */           if ((ch < '0') || (ch > '9')) {
/*  62:180 */             throw new IllegalArgumentException("Name cannot be have a '" + ch + "' char");
/*  63:    */           }
/*  64:    */         }
/*  65:    */       }
/*  66:    */     }
/*  67:    */   }
/*  68:    */   
/*  69:    */   private static void checkNamedOutputName(String namedOutput)
/*  70:    */   {
/*  71:192 */     checkTokenName(namedOutput);
/*  72:194 */     if (namedOutput.equals("part")) {
/*  73:195 */       throw new IllegalArgumentException("Named output name cannot be 'part'");
/*  74:    */     }
/*  75:    */   }
/*  76:    */   
/*  77:    */   public static List<String> getNamedOutputsList(JobConf conf)
/*  78:    */   {
/*  79:207 */     List<String> names = new ArrayList();
/*  80:208 */     StringTokenizer st = new StringTokenizer(conf.get("mo.namedOutputs", ""), " ");
/*  81:209 */     while (st.hasMoreTokens()) {
/*  82:210 */       names.add(st.nextToken());
/*  83:    */     }
/*  84:212 */     return names;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public static boolean isMultiNamedOutput(JobConf conf, String namedOutput)
/*  88:    */   {
/*  89:226 */     checkNamedOutput(conf, namedOutput, false);
/*  90:227 */     return conf.getBoolean("mo.namedOutput." + namedOutput + ".multi", false);
/*  91:    */   }
/*  92:    */   
/*  93:    */   public static Class<? extends OutputFormat> getNamedOutputFormatClass(JobConf conf, String namedOutput)
/*  94:    */   {
/*  95:239 */     checkNamedOutput(conf, namedOutput, false);
/*  96:240 */     return conf.getClass("mo.namedOutput." + namedOutput + ".format", null, OutputFormat.class);
/*  97:    */   }
/*  98:    */   
/*  99:    */   public static Class<?> getNamedOutputKeyClass(JobConf conf, String namedOutput)
/* 100:    */   {
/* 101:253 */     checkNamedOutput(conf, namedOutput, false);
/* 102:254 */     return conf.getClass("mo.namedOutput." + namedOutput + ".key", null, Object.class);
/* 103:    */   }
/* 104:    */   
/* 105:    */   public static Class<?> getNamedOutputValueClass(JobConf conf, String namedOutput)
/* 106:    */   {
/* 107:267 */     checkNamedOutput(conf, namedOutput, false);
/* 108:268 */     return conf.getClass("mo.namedOutput." + namedOutput + ".value", null, Object.class);
/* 109:    */   }
/* 110:    */   
/* 111:    */   public static void addNamedOutput(JobConf conf, String namedOutput, Class<? extends OutputFormat> outputFormatClass, Class<?> keyClass, Class<?> valueClass)
/* 112:    */   {
/* 113:288 */     addNamedOutput(conf, namedOutput, false, outputFormatClass, keyClass, valueClass);
/* 114:    */   }
/* 115:    */   
/* 116:    */   public static void addMultiNamedOutput(JobConf conf, String namedOutput, Class<? extends OutputFormat> outputFormatClass, Class<?> keyClass, Class<?> valueClass)
/* 117:    */   {
/* 118:308 */     addNamedOutput(conf, namedOutput, true, outputFormatClass, keyClass, valueClass);
/* 119:    */   }
/* 120:    */   
/* 121:    */   private static void addNamedOutput(JobConf conf, String namedOutput, boolean multi, Class<? extends OutputFormat> outputFormatClass, Class<?> keyClass, Class<?> valueClass)
/* 122:    */   {
/* 123:330 */     checkNamedOutputName(namedOutput);
/* 124:331 */     checkNamedOutput(conf, namedOutput, true);
/* 125:332 */     conf.set("mo.namedOutputs", conf.get("mo.namedOutputs", "") + " " + namedOutput);
/* 126:333 */     conf.setClass("mo.namedOutput." + namedOutput + ".format", outputFormatClass, OutputFormat.class);
/* 127:    */     
/* 128:335 */     conf.setClass("mo.namedOutput." + namedOutput + ".key", keyClass, Object.class);
/* 129:336 */     conf.setClass("mo.namedOutput." + namedOutput + ".value", valueClass, Object.class);
/* 130:337 */     conf.setBoolean("mo.namedOutput." + namedOutput + ".multi", multi);
/* 131:    */   }
/* 132:    */   
/* 133:    */   public static void setCountersEnabled(JobConf conf, boolean enabled)
/* 134:    */   {
/* 135:356 */     conf.setBoolean("mo.counters", enabled);
/* 136:    */   }
/* 137:    */   
/* 138:    */   public static boolean getCountersEnabled(JobConf conf)
/* 139:    */   {
/* 140:376 */     return conf.getBoolean("mo.counters", false);
/* 141:    */   }
/* 142:    */   
/* 143:    */   public MultipleOutputs(JobConf job)
/* 144:    */   {
/* 145:394 */     this.conf = job;
/* 146:395 */     this.outputFormat = new InternalFileOutputFormat(null);
/* 147:396 */     this.namedOutputs = Collections.unmodifiableSet(new HashSet(getNamedOutputsList(job)));
/* 148:    */     
/* 149:398 */     this.recordWriters = new HashMap();
/* 150:399 */     this.countersEnabled = getCountersEnabled(job);
/* 151:    */   }
/* 152:    */   
/* 153:    */   public Iterator<String> getNamedOutputs()
/* 154:    */   {
/* 155:408 */     return this.namedOutputs.iterator();
/* 156:    */   }
/* 157:    */   
/* 158:    */   private synchronized RecordWriter getRecordWriter(String namedOutput, String baseFileName, Reporter reporter)
/* 159:    */     throws IOException
/* 160:    */   {
/* 161:418 */     RecordWriter writer = (RecordWriter)this.recordWriters.get(baseFileName);
/* 162:419 */     if (writer == null)
/* 163:    */     {
/* 164:420 */       if ((this.countersEnabled) && (reporter == null)) {
/* 165:421 */         throw new IllegalArgumentException("Counters are enabled, Reporter cannot be NULL");
/* 166:    */       }
/* 167:424 */       JobConf jobConf = new JobConf(this.conf);
/* 168:425 */       jobConf.set("mo.config.namedOutput", namedOutput);
/* 169:426 */       FileSystem fs = FileSystem.get(this.conf);
/* 170:427 */       writer = this.outputFormat.getRecordWriter(fs, jobConf, baseFileName, reporter);
/* 171:430 */       if (this.countersEnabled)
/* 172:    */       {
/* 173:431 */         if (reporter == null) {
/* 174:432 */           throw new IllegalArgumentException("Counters are enabled, Reporter cannot be NULL");
/* 175:    */         }
/* 176:435 */         writer = new RecordWriterWithCounter(writer, baseFileName, reporter);
/* 177:    */       }
/* 178:438 */       this.recordWriters.put(baseFileName, writer);
/* 179:    */     }
/* 180:440 */     return writer;
/* 181:    */   }
/* 182:    */   
/* 183:    */   private static class RecordWriterWithCounter
/* 184:    */     implements RecordWriter
/* 185:    */   {
/* 186:    */     private RecordWriter writer;
/* 187:    */     private String counterName;
/* 188:    */     private Reporter reporter;
/* 189:    */     
/* 190:    */     public RecordWriterWithCounter(RecordWriter writer, String counterName, Reporter reporter)
/* 191:    */     {
/* 192:450 */       this.writer = writer;
/* 193:451 */       this.counterName = counterName;
/* 194:452 */       this.reporter = reporter;
/* 195:    */     }
/* 196:    */     
/* 197:    */     public void write(Object key, Object value)
/* 198:    */       throws IOException
/* 199:    */     {
/* 200:457 */       this.reporter.incrCounter(MultipleOutputs.COUNTERS_GROUP, this.counterName, 1L);
/* 201:458 */       this.writer.write(key, value);
/* 202:    */     }
/* 203:    */     
/* 204:    */     public void close(Reporter reporter)
/* 205:    */       throws IOException
/* 206:    */     {
/* 207:462 */       this.writer.close(reporter);
/* 208:    */     }
/* 209:    */   }
/* 210:    */   
/* 211:    */   public OutputCollector getCollector(String namedOutput, Reporter reporter)
/* 212:    */     throws IOException
/* 213:    */   {
/* 214:478 */     return getCollector(namedOutput, null, reporter);
/* 215:    */   }
/* 216:    */   
/* 217:    */   public OutputCollector getCollector(String namedOutput, String multiName, Reporter reporter)
/* 218:    */     throws IOException
/* 219:    */   {
/* 220:496 */     checkNamedOutputName(namedOutput);
/* 221:497 */     if (!this.namedOutputs.contains(namedOutput)) {
/* 222:498 */       throw new IllegalArgumentException("Undefined named output '" + namedOutput + "'");
/* 223:    */     }
/* 224:501 */     boolean multi = isMultiNamedOutput(this.conf, namedOutput);
/* 225:503 */     if ((!multi) && (multiName != null)) {
/* 226:504 */       throw new IllegalArgumentException("Name output '" + namedOutput + "' has not been defined as multi");
/* 227:    */     }
/* 228:507 */     if (multi) {
/* 229:508 */       checkTokenName(multiName);
/* 230:    */     }
/* 231:511 */     String baseFileName = multi ? namedOutput + "_" + multiName : namedOutput;
/* 232:    */     
/* 233:513 */     final RecordWriter writer = getRecordWriter(namedOutput, baseFileName, reporter);
/* 234:    */     
/* 235:    */ 
/* 236:516 */     new OutputCollector()
/* 237:    */     {
/* 238:    */       public void collect(Object key, Object value)
/* 239:    */         throws IOException
/* 240:    */       {
/* 241:520 */         writer.write(key, value);
/* 242:    */       }
/* 243:    */     };
/* 244:    */   }
/* 245:    */   
/* 246:    */   public void close()
/* 247:    */     throws IOException
/* 248:    */   {
/* 249:536 */     for (RecordWriter writer : this.recordWriters.values()) {
/* 250:537 */       writer.close(null);
/* 251:    */     }
/* 252:    */   }
/* 253:    */   
/* 254:    */   private static class InternalFileOutputFormat
/* 255:    */     extends FileOutputFormat<Object, Object>
/* 256:    */   {
/* 257:    */     public static final String CONFIG_NAMED_OUTPUT = "mo.config.namedOutput";
/* 258:    */     
/* 259:    */     public RecordWriter<Object, Object> getRecordWriter(FileSystem fs, JobConf job, String baseFileName, Progressable progress)
/* 260:    */       throws IOException
/* 261:    */     {
/* 262:551 */       String nameOutput = job.get("mo.config.namedOutput", null);
/* 263:552 */       String fileName = getUniqueName(job, baseFileName);
/* 264:    */       
/* 265:    */ 
/* 266:    */ 
/* 267:556 */       JobConf outputConf = new JobConf(job);
/* 268:557 */       outputConf.setOutputFormat(MultipleOutputs.getNamedOutputFormatClass(job, nameOutput));
/* 269:558 */       outputConf.setOutputKeyClass(MultipleOutputs.getNamedOutputKeyClass(job, nameOutput));
/* 270:559 */       outputConf.setOutputValueClass(MultipleOutputs.getNamedOutputValueClass(job, nameOutput));
/* 271:560 */       OutputFormat outputFormat = outputConf.getOutputFormat();
/* 272:561 */       return outputFormat.getRecordWriter(fs, outputConf, fileName, progress);
/* 273:    */     }
/* 274:    */   }
/* 275:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.lib.MultipleOutputs
 * JD-Core Version:    0.7.0.1
 */