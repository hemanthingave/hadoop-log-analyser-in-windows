/*   1:    */ package org.apache.hadoop.mapreduce.lib.chain;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.net.URI;
/*   5:    */ import org.apache.hadoop.conf.Configuration;
/*   6:    */ import org.apache.hadoop.conf.Configuration.IntegerRanges;
/*   7:    */ import org.apache.hadoop.fs.Path;
/*   8:    */ import org.apache.hadoop.io.RawComparator;
/*   9:    */ import org.apache.hadoop.mapreduce.Counter;
/*  10:    */ import org.apache.hadoop.mapreduce.InputFormat;
/*  11:    */ import org.apache.hadoop.mapreduce.InputSplit;
/*  12:    */ import org.apache.hadoop.mapreduce.JobID;
/*  13:    */ import org.apache.hadoop.mapreduce.MapContext;
/*  14:    */ import org.apache.hadoop.mapreduce.Mapper;
/*  15:    */ import org.apache.hadoop.mapreduce.OutputCommitter;
/*  16:    */ import org.apache.hadoop.mapreduce.OutputFormat;
/*  17:    */ import org.apache.hadoop.mapreduce.Partitioner;
/*  18:    */ import org.apache.hadoop.mapreduce.RecordReader;
/*  19:    */ import org.apache.hadoop.mapreduce.RecordWriter;
/*  20:    */ import org.apache.hadoop.mapreduce.Reducer;
/*  21:    */ import org.apache.hadoop.mapreduce.TaskAttemptID;
/*  22:    */ import org.apache.hadoop.mapreduce.TaskInputOutputContext;
/*  23:    */ import org.apache.hadoop.security.Credentials;
/*  24:    */ 
/*  25:    */ class ChainMapContextImpl<KEYIN, VALUEIN, KEYOUT, VALUEOUT>
/*  26:    */   implements MapContext<KEYIN, VALUEIN, KEYOUT, VALUEOUT>
/*  27:    */ {
/*  28:    */   private RecordReader<KEYIN, VALUEIN> reader;
/*  29:    */   private RecordWriter<KEYOUT, VALUEOUT> output;
/*  30:    */   private TaskInputOutputContext<KEYIN, VALUEIN, KEYOUT, VALUEOUT> base;
/*  31:    */   private Configuration conf;
/*  32:    */   
/*  33:    */   ChainMapContextImpl(TaskInputOutputContext<KEYIN, VALUEIN, KEYOUT, VALUEOUT> base, RecordReader<KEYIN, VALUEIN> rr, RecordWriter<KEYOUT, VALUEOUT> rw, Configuration conf)
/*  34:    */   {
/*  35: 60 */     this.reader = rr;
/*  36: 61 */     this.output = rw;
/*  37: 62 */     this.base = base;
/*  38: 63 */     this.conf = conf;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public KEYIN getCurrentKey()
/*  42:    */     throws IOException, InterruptedException
/*  43:    */   {
/*  44: 68 */     return this.reader.getCurrentKey();
/*  45:    */   }
/*  46:    */   
/*  47:    */   public VALUEIN getCurrentValue()
/*  48:    */     throws IOException, InterruptedException
/*  49:    */   {
/*  50: 73 */     return this.reader.getCurrentValue();
/*  51:    */   }
/*  52:    */   
/*  53:    */   public boolean nextKeyValue()
/*  54:    */     throws IOException, InterruptedException
/*  55:    */   {
/*  56: 78 */     return this.reader.nextKeyValue();
/*  57:    */   }
/*  58:    */   
/*  59:    */   public InputSplit getInputSplit()
/*  60:    */   {
/*  61: 83 */     if ((this.base instanceof MapContext))
/*  62:    */     {
/*  63: 84 */       MapContext<KEYIN, VALUEIN, KEYOUT, VALUEOUT> mc = (MapContext)this.base;
/*  64:    */       
/*  65: 86 */       return mc.getInputSplit();
/*  66:    */     }
/*  67: 88 */     return null;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public Counter getCounter(Enum<?> counterName)
/*  71:    */   {
/*  72: 94 */     return this.base.getCounter(counterName);
/*  73:    */   }
/*  74:    */   
/*  75:    */   public Counter getCounter(String groupName, String counterName)
/*  76:    */   {
/*  77: 99 */     return this.base.getCounter(groupName, counterName);
/*  78:    */   }
/*  79:    */   
/*  80:    */   public OutputCommitter getOutputCommitter()
/*  81:    */   {
/*  82:104 */     return this.base.getOutputCommitter();
/*  83:    */   }
/*  84:    */   
/*  85:    */   public void write(KEYOUT key, VALUEOUT value)
/*  86:    */     throws IOException, InterruptedException
/*  87:    */   {
/*  88:110 */     this.output.write(key, value);
/*  89:    */   }
/*  90:    */   
/*  91:    */   public String getStatus()
/*  92:    */   {
/*  93:115 */     return this.base.getStatus();
/*  94:    */   }
/*  95:    */   
/*  96:    */   public TaskAttemptID getTaskAttemptID()
/*  97:    */   {
/*  98:120 */     return this.base.getTaskAttemptID();
/*  99:    */   }
/* 100:    */   
/* 101:    */   public void setStatus(String msg)
/* 102:    */   {
/* 103:125 */     this.base.setStatus(msg);
/* 104:    */   }
/* 105:    */   
/* 106:    */   public Path[] getArchiveClassPaths()
/* 107:    */   {
/* 108:130 */     return this.base.getArchiveClassPaths();
/* 109:    */   }
/* 110:    */   
/* 111:    */   public String[] getArchiveTimestamps()
/* 112:    */   {
/* 113:135 */     return this.base.getArchiveTimestamps();
/* 114:    */   }
/* 115:    */   
/* 116:    */   public URI[] getCacheArchives()
/* 117:    */     throws IOException
/* 118:    */   {
/* 119:140 */     return this.base.getCacheArchives();
/* 120:    */   }
/* 121:    */   
/* 122:    */   public URI[] getCacheFiles()
/* 123:    */     throws IOException
/* 124:    */   {
/* 125:145 */     return this.base.getCacheFiles();
/* 126:    */   }
/* 127:    */   
/* 128:    */   public Class<? extends Reducer<?, ?, ?, ?>> getCombinerClass()
/* 129:    */     throws ClassNotFoundException
/* 130:    */   {
/* 131:151 */     return this.base.getCombinerClass();
/* 132:    */   }
/* 133:    */   
/* 134:    */   public Configuration getConfiguration()
/* 135:    */   {
/* 136:156 */     return this.conf;
/* 137:    */   }
/* 138:    */   
/* 139:    */   public Path[] getFileClassPaths()
/* 140:    */   {
/* 141:161 */     return this.base.getFileClassPaths();
/* 142:    */   }
/* 143:    */   
/* 144:    */   public String[] getFileTimestamps()
/* 145:    */   {
/* 146:166 */     return this.base.getFileTimestamps();
/* 147:    */   }
/* 148:    */   
/* 149:    */   public RawComparator<?> getCombinerKeyGroupingComparator()
/* 150:    */   {
/* 151:171 */     return this.base.getCombinerKeyGroupingComparator();
/* 152:    */   }
/* 153:    */   
/* 154:    */   public RawComparator<?> getGroupingComparator()
/* 155:    */   {
/* 156:176 */     return this.base.getGroupingComparator();
/* 157:    */   }
/* 158:    */   
/* 159:    */   public Class<? extends InputFormat<?, ?>> getInputFormatClass()
/* 160:    */     throws ClassNotFoundException
/* 161:    */   {
/* 162:182 */     return this.base.getInputFormatClass();
/* 163:    */   }
/* 164:    */   
/* 165:    */   public String getJar()
/* 166:    */   {
/* 167:187 */     return this.base.getJar();
/* 168:    */   }
/* 169:    */   
/* 170:    */   public JobID getJobID()
/* 171:    */   {
/* 172:192 */     return this.base.getJobID();
/* 173:    */   }
/* 174:    */   
/* 175:    */   public String getJobName()
/* 176:    */   {
/* 177:197 */     return this.base.getJobName();
/* 178:    */   }
/* 179:    */   
/* 180:    */   public boolean getJobSetupCleanupNeeded()
/* 181:    */   {
/* 182:202 */     return this.base.getJobSetupCleanupNeeded();
/* 183:    */   }
/* 184:    */   
/* 185:    */   public boolean getTaskCleanupNeeded()
/* 186:    */   {
/* 187:207 */     return this.base.getTaskCleanupNeeded();
/* 188:    */   }
/* 189:    */   
/* 190:    */   public Path[] getLocalCacheArchives()
/* 191:    */     throws IOException
/* 192:    */   {
/* 193:212 */     return this.base.getLocalCacheArchives();
/* 194:    */   }
/* 195:    */   
/* 196:    */   public Path[] getLocalCacheFiles()
/* 197:    */     throws IOException
/* 198:    */   {
/* 199:217 */     return this.base.getLocalCacheArchives();
/* 200:    */   }
/* 201:    */   
/* 202:    */   public Class<?> getMapOutputKeyClass()
/* 203:    */   {
/* 204:222 */     return this.base.getMapOutputKeyClass();
/* 205:    */   }
/* 206:    */   
/* 207:    */   public Class<?> getMapOutputValueClass()
/* 208:    */   {
/* 209:227 */     return this.base.getMapOutputValueClass();
/* 210:    */   }
/* 211:    */   
/* 212:    */   public Class<? extends Mapper<?, ?, ?, ?>> getMapperClass()
/* 213:    */     throws ClassNotFoundException
/* 214:    */   {
/* 215:233 */     return this.base.getMapperClass();
/* 216:    */   }
/* 217:    */   
/* 218:    */   public int getMaxMapAttempts()
/* 219:    */   {
/* 220:238 */     return this.base.getMaxMapAttempts();
/* 221:    */   }
/* 222:    */   
/* 223:    */   public int getMaxReduceAttempts()
/* 224:    */   {
/* 225:243 */     return this.base.getMaxReduceAttempts();
/* 226:    */   }
/* 227:    */   
/* 228:    */   public int getNumReduceTasks()
/* 229:    */   {
/* 230:248 */     return this.base.getNumReduceTasks();
/* 231:    */   }
/* 232:    */   
/* 233:    */   public Class<? extends OutputFormat<?, ?>> getOutputFormatClass()
/* 234:    */     throws ClassNotFoundException
/* 235:    */   {
/* 236:254 */     return this.base.getOutputFormatClass();
/* 237:    */   }
/* 238:    */   
/* 239:    */   public Class<?> getOutputKeyClass()
/* 240:    */   {
/* 241:259 */     return this.base.getMapOutputKeyClass();
/* 242:    */   }
/* 243:    */   
/* 244:    */   public Class<?> getOutputValueClass()
/* 245:    */   {
/* 246:264 */     return this.base.getOutputValueClass();
/* 247:    */   }
/* 248:    */   
/* 249:    */   public Class<? extends Partitioner<?, ?>> getPartitionerClass()
/* 250:    */     throws ClassNotFoundException
/* 251:    */   {
/* 252:270 */     return this.base.getPartitionerClass();
/* 253:    */   }
/* 254:    */   
/* 255:    */   public boolean getProfileEnabled()
/* 256:    */   {
/* 257:275 */     return this.base.getProfileEnabled();
/* 258:    */   }
/* 259:    */   
/* 260:    */   public String getProfileParams()
/* 261:    */   {
/* 262:280 */     return this.base.getProfileParams();
/* 263:    */   }
/* 264:    */   
/* 265:    */   public Configuration.IntegerRanges getProfileTaskRange(boolean isMap)
/* 266:    */   {
/* 267:285 */     return this.base.getProfileTaskRange(isMap);
/* 268:    */   }
/* 269:    */   
/* 270:    */   public Class<? extends Reducer<?, ?, ?, ?>> getReducerClass()
/* 271:    */     throws ClassNotFoundException
/* 272:    */   {
/* 273:291 */     return this.base.getReducerClass();
/* 274:    */   }
/* 275:    */   
/* 276:    */   public RawComparator<?> getSortComparator()
/* 277:    */   {
/* 278:296 */     return this.base.getSortComparator();
/* 279:    */   }
/* 280:    */   
/* 281:    */   public boolean getSymlink()
/* 282:    */   {
/* 283:301 */     return this.base.getSymlink();
/* 284:    */   }
/* 285:    */   
/* 286:    */   public String getUser()
/* 287:    */   {
/* 288:306 */     return this.base.getUser();
/* 289:    */   }
/* 290:    */   
/* 291:    */   public Path getWorkingDirectory()
/* 292:    */     throws IOException
/* 293:    */   {
/* 294:311 */     return this.base.getWorkingDirectory();
/* 295:    */   }
/* 296:    */   
/* 297:    */   public void progress()
/* 298:    */   {
/* 299:316 */     this.base.progress();
/* 300:    */   }
/* 301:    */   
/* 302:    */   public Credentials getCredentials()
/* 303:    */   {
/* 304:321 */     return this.base.getCredentials();
/* 305:    */   }
/* 306:    */   
/* 307:    */   public float getProgress()
/* 308:    */   {
/* 309:326 */     return this.base.getProgress();
/* 310:    */   }
/* 311:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.lib.chain.ChainMapContextImpl
 * JD-Core Version:    0.7.0.1
 */