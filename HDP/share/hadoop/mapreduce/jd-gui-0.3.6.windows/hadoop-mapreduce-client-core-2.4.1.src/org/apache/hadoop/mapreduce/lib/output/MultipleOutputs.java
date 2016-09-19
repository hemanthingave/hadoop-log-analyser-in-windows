/*   1:    */ package org.apache.hadoop.mapreduce.lib.output;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import java.util.Collections;
/*   6:    */ import java.util.HashMap;
/*   7:    */ import java.util.HashSet;
/*   8:    */ import java.util.List;
/*   9:    */ import java.util.Map;
/*  10:    */ import java.util.Set;
/*  11:    */ import java.util.StringTokenizer;
/*  12:    */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*  13:    */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*  14:    */ import org.apache.hadoop.conf.Configuration;
/*  15:    */ import org.apache.hadoop.mapreduce.Counter;
/*  16:    */ import org.apache.hadoop.mapreduce.Job;
/*  17:    */ import org.apache.hadoop.mapreduce.JobContext;
/*  18:    */ import org.apache.hadoop.mapreduce.OutputFormat;
/*  19:    */ import org.apache.hadoop.mapreduce.RecordWriter;
/*  20:    */ import org.apache.hadoop.mapreduce.StatusReporter;
/*  21:    */ import org.apache.hadoop.mapreduce.TaskAttemptContext;
/*  22:    */ import org.apache.hadoop.mapreduce.TaskInputOutputContext;
/*  23:    */ import org.apache.hadoop.mapreduce.task.TaskAttemptContextImpl;
/*  24:    */ import org.apache.hadoop.util.ReflectionUtils;
/*  25:    */ 
/*  26:    */ @InterfaceAudience.Public
/*  27:    */ @InterfaceStability.Stable
/*  28:    */ public class MultipleOutputs<KEYOUT, VALUEOUT>
/*  29:    */ {
/*  30:    */   private static final String MULTIPLE_OUTPUTS = "mapreduce.multipleoutputs";
/*  31:    */   private static final String MO_PREFIX = "mapreduce.multipleoutputs.namedOutput.";
/*  32:    */   private static final String FORMAT = ".format";
/*  33:    */   private static final String KEY = ".key";
/*  34:    */   private static final String VALUE = ".value";
/*  35:    */   private static final String COUNTERS_ENABLED = "mapreduce.multipleoutputs.counters";
/*  36:191 */   private static final String COUNTERS_GROUP = MultipleOutputs.class.getName();
/*  37:196 */   private Map<String, TaskAttemptContext> taskContexts = new HashMap();
/*  38:    */   private TaskAttemptContext jobOutputFormatContext;
/*  39:    */   private TaskInputOutputContext<?, ?, KEYOUT, VALUEOUT> context;
/*  40:    */   private Set<String> namedOutputs;
/*  41:    */   private Map<String, RecordWriter<?, ?>> recordWriters;
/*  42:    */   private boolean countersEnabled;
/*  43:    */   
/*  44:    */   private static void checkTokenName(String namedOutput)
/*  45:    */   {
/*  46:209 */     if ((namedOutput == null) || (namedOutput.length() == 0)) {
/*  47:210 */       throw new IllegalArgumentException("Name cannot be NULL or emtpy");
/*  48:    */     }
/*  49:213 */     for (char ch : namedOutput.toCharArray()) {
/*  50:214 */       if ((ch < 'A') || (ch > 'Z')) {
/*  51:217 */         if ((ch < 'a') || (ch > 'z')) {
/*  52:220 */           if ((ch < '0') || (ch > '9')) {
/*  53:223 */             throw new IllegalArgumentException("Name cannot be have a '" + ch + "' char");
/*  54:    */           }
/*  55:    */         }
/*  56:    */       }
/*  57:    */     }
/*  58:    */   }
/*  59:    */   
/*  60:    */   private static void checkBaseOutputPath(String outputPath)
/*  61:    */   {
/*  62:236 */     if (outputPath.equals("part")) {
/*  63:237 */       throw new IllegalArgumentException("output name cannot be 'part'");
/*  64:    */     }
/*  65:    */   }
/*  66:    */   
/*  67:    */   private static void checkNamedOutputName(JobContext job, String namedOutput, boolean alreadyDefined)
/*  68:    */   {
/*  69:249 */     checkTokenName(namedOutput);
/*  70:250 */     checkBaseOutputPath(namedOutput);
/*  71:251 */     List<String> definedChannels = getNamedOutputsList(job);
/*  72:252 */     if ((alreadyDefined) && (definedChannels.contains(namedOutput))) {
/*  73:253 */       throw new IllegalArgumentException("Named output '" + namedOutput + "' already alreadyDefined");
/*  74:    */     }
/*  75:255 */     if ((!alreadyDefined) && (!definedChannels.contains(namedOutput))) {
/*  76:256 */       throw new IllegalArgumentException("Named output '" + namedOutput + "' not defined");
/*  77:    */     }
/*  78:    */   }
/*  79:    */   
/*  80:    */   private static List<String> getNamedOutputsList(JobContext job)
/*  81:    */   {
/*  82:263 */     List<String> names = new ArrayList();
/*  83:264 */     StringTokenizer st = new StringTokenizer(job.getConfiguration().get("mapreduce.multipleoutputs", ""), " ");
/*  84:266 */     while (st.hasMoreTokens()) {
/*  85:267 */       names.add(st.nextToken());
/*  86:    */     }
/*  87:269 */     return names;
/*  88:    */   }
/*  89:    */   
/*  90:    */   private static Class<? extends OutputFormat<?, ?>> getNamedOutputFormatClass(JobContext job, String namedOutput)
/*  91:    */   {
/*  92:276 */     return job.getConfiguration().getClass("mapreduce.multipleoutputs.namedOutput." + namedOutput + ".format", null, OutputFormat.class);
/*  93:    */   }
/*  94:    */   
/*  95:    */   private static Class<?> getNamedOutputKeyClass(JobContext job, String namedOutput)
/*  96:    */   {
/*  97:284 */     return job.getConfiguration().getClass("mapreduce.multipleoutputs.namedOutput." + namedOutput + ".key", null, Object.class);
/*  98:    */   }
/*  99:    */   
/* 100:    */   private static Class<?> getNamedOutputValueClass(JobContext job, String namedOutput)
/* 101:    */   {
/* 102:291 */     return job.getConfiguration().getClass("mapreduce.multipleoutputs.namedOutput." + namedOutput + ".value", null, Object.class);
/* 103:    */   }
/* 104:    */   
/* 105:    */   public static void addNamedOutput(Job job, String namedOutput, Class<? extends OutputFormat> outputFormatClass, Class<?> keyClass, Class<?> valueClass)
/* 106:    */   {
/* 107:311 */     checkNamedOutputName(job, namedOutput, true);
/* 108:312 */     Configuration conf = job.getConfiguration();
/* 109:313 */     conf.set("mapreduce.multipleoutputs", conf.get("mapreduce.multipleoutputs", "") + " " + namedOutput);
/* 110:    */     
/* 111:315 */     conf.setClass("mapreduce.multipleoutputs.namedOutput." + namedOutput + ".format", outputFormatClass, OutputFormat.class);
/* 112:    */     
/* 113:317 */     conf.setClass("mapreduce.multipleoutputs.namedOutput." + namedOutput + ".key", keyClass, Object.class);
/* 114:318 */     conf.setClass("mapreduce.multipleoutputs.namedOutput." + namedOutput + ".value", valueClass, Object.class);
/* 115:    */   }
/* 116:    */   
/* 117:    */   public static void setCountersEnabled(Job job, boolean enabled)
/* 118:    */   {
/* 119:333 */     job.getConfiguration().setBoolean("mapreduce.multipleoutputs.counters", enabled);
/* 120:    */   }
/* 121:    */   
/* 122:    */   public static boolean getCountersEnabled(JobContext job)
/* 123:    */   {
/* 124:344 */     return job.getConfiguration().getBoolean("mapreduce.multipleoutputs.counters", false);
/* 125:    */   }
/* 126:    */   
/* 127:    */   private static class RecordWriterWithCounter
/* 128:    */     extends RecordWriter
/* 129:    */   {
/* 130:    */     private RecordWriter writer;
/* 131:    */     private String counterName;
/* 132:    */     private TaskInputOutputContext context;
/* 133:    */     
/* 134:    */     public RecordWriterWithCounter(RecordWriter writer, String counterName, TaskInputOutputContext context)
/* 135:    */     {
/* 136:358 */       this.writer = writer;
/* 137:359 */       this.counterName = counterName;
/* 138:360 */       this.context = context;
/* 139:    */     }
/* 140:    */     
/* 141:    */     public void write(Object key, Object value)
/* 142:    */       throws IOException, InterruptedException
/* 143:    */     {
/* 144:366 */       this.context.getCounter(MultipleOutputs.COUNTERS_GROUP, this.counterName).increment(1L);
/* 145:367 */       this.writer.write(key, value);
/* 146:    */     }
/* 147:    */     
/* 148:    */     public void close(TaskAttemptContext context)
/* 149:    */       throws IOException, InterruptedException
/* 150:    */     {
/* 151:372 */       this.writer.close(context);
/* 152:    */     }
/* 153:    */   }
/* 154:    */   
/* 155:    */   public MultipleOutputs(TaskInputOutputContext<?, ?, KEYOUT, VALUEOUT> context)
/* 156:    */   {
/* 157:391 */     this.context = context;
/* 158:392 */     this.namedOutputs = Collections.unmodifiableSet(new HashSet(getNamedOutputsList(context)));
/* 159:    */     
/* 160:394 */     this.recordWriters = new HashMap();
/* 161:395 */     this.countersEnabled = getCountersEnabled(context);
/* 162:    */   }
/* 163:    */   
/* 164:    */   public <K, V> void write(String namedOutput, K key, V value)
/* 165:    */     throws IOException, InterruptedException
/* 166:    */   {
/* 167:411 */     write(namedOutput, key, value, namedOutput);
/* 168:    */   }
/* 169:    */   
/* 170:    */   public <K, V> void write(String namedOutput, K key, V value, String baseOutputPath)
/* 171:    */     throws IOException, InterruptedException
/* 172:    */   {
/* 173:426 */     checkNamedOutputName(this.context, namedOutput, false);
/* 174:427 */     checkBaseOutputPath(baseOutputPath);
/* 175:428 */     if (!this.namedOutputs.contains(namedOutput)) {
/* 176:429 */       throw new IllegalArgumentException("Undefined named output '" + namedOutput + "'");
/* 177:    */     }
/* 178:432 */     TaskAttemptContext taskContext = getContext(namedOutput);
/* 179:433 */     getRecordWriter(taskContext, baseOutputPath).write(key, value);
/* 180:    */   }
/* 181:    */   
/* 182:    */   public void write(KEYOUT key, VALUEOUT value, String baseOutputPath)
/* 183:    */     throws IOException, InterruptedException
/* 184:    */   {
/* 185:450 */     checkBaseOutputPath(baseOutputPath);
/* 186:451 */     if (this.jobOutputFormatContext == null) {
/* 187:452 */       this.jobOutputFormatContext = new TaskAttemptContextImpl(this.context.getConfiguration(), this.context.getTaskAttemptID(), new WrappedStatusReporter(this.context));
/* 188:    */     }
/* 189:457 */     getRecordWriter(this.jobOutputFormatContext, baseOutputPath).write(key, value);
/* 190:    */   }
/* 191:    */   
/* 192:    */   private synchronized RecordWriter getRecordWriter(TaskAttemptContext taskContext, String baseFileName)
/* 193:    */     throws IOException, InterruptedException
/* 194:    */   {
/* 195:468 */     RecordWriter writer = (RecordWriter)this.recordWriters.get(baseFileName);
/* 196:471 */     if (writer == null)
/* 197:    */     {
/* 198:473 */       FileOutputFormat.setOutputName(taskContext, baseFileName);
/* 199:    */       try
/* 200:    */       {
/* 201:475 */         writer = ((OutputFormat)ReflectionUtils.newInstance(taskContext.getOutputFormatClass(), taskContext.getConfiguration())).getRecordWriter(taskContext);
/* 202:    */       }
/* 203:    */       catch (ClassNotFoundException e)
/* 204:    */       {
/* 205:479 */         throw new IOException(e);
/* 206:    */       }
/* 207:484 */       if (this.countersEnabled) {
/* 208:485 */         writer = new RecordWriterWithCounter(writer, baseFileName, this.context);
/* 209:    */       }
/* 210:489 */       this.recordWriters.put(baseFileName, writer);
/* 211:    */     }
/* 212:491 */     return writer;
/* 213:    */   }
/* 214:    */   
/* 215:    */   private TaskAttemptContext getContext(String nameOutput)
/* 216:    */     throws IOException
/* 217:    */   {
/* 218:498 */     TaskAttemptContext taskContext = (TaskAttemptContext)this.taskContexts.get(nameOutput);
/* 219:500 */     if (taskContext != null) {
/* 220:501 */       return taskContext;
/* 221:    */     }
/* 222:506 */     Job job = new Job(this.context.getConfiguration());
/* 223:507 */     job.setOutputFormatClass(getNamedOutputFormatClass(this.context, nameOutput));
/* 224:508 */     job.setOutputKeyClass(getNamedOutputKeyClass(this.context, nameOutput));
/* 225:509 */     job.setOutputValueClass(getNamedOutputValueClass(this.context, nameOutput));
/* 226:510 */     taskContext = new TaskAttemptContextImpl(job.getConfiguration(), this.context.getTaskAttemptID(), new WrappedStatusReporter(this.context));
/* 227:    */     
/* 228:    */ 
/* 229:513 */     this.taskContexts.put(nameOutput, taskContext);
/* 230:    */     
/* 231:515 */     return taskContext;
/* 232:    */   }
/* 233:    */   
/* 234:    */   private static class WrappedStatusReporter
/* 235:    */     extends StatusReporter
/* 236:    */   {
/* 237:    */     TaskAttemptContext context;
/* 238:    */     
/* 239:    */     public WrappedStatusReporter(TaskAttemptContext context)
/* 240:    */     {
/* 241:523 */       this.context = context;
/* 242:    */     }
/* 243:    */     
/* 244:    */     public Counter getCounter(Enum<?> name)
/* 245:    */     {
/* 246:528 */       return this.context.getCounter(name);
/* 247:    */     }
/* 248:    */     
/* 249:    */     public Counter getCounter(String group, String name)
/* 250:    */     {
/* 251:533 */       return this.context.getCounter(group, name);
/* 252:    */     }
/* 253:    */     
/* 254:    */     public void progress()
/* 255:    */     {
/* 256:538 */       this.context.progress();
/* 257:    */     }
/* 258:    */     
/* 259:    */     public float getProgress()
/* 260:    */     {
/* 261:543 */       return this.context.getProgress();
/* 262:    */     }
/* 263:    */     
/* 264:    */     public void setStatus(String status)
/* 265:    */     {
/* 266:548 */       this.context.setStatus(status);
/* 267:    */     }
/* 268:    */   }
/* 269:    */   
/* 270:    */   public void close()
/* 271:    */     throws IOException, InterruptedException
/* 272:    */   {
/* 273:562 */     for (RecordWriter writer : this.recordWriters.values()) {
/* 274:563 */       writer.close(this.context);
/* 275:    */     }
/* 276:    */   }
/* 277:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.lib.output.MultipleOutputs
 * JD-Core Version:    0.7.0.1
 */