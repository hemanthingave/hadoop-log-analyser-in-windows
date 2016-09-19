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
/*  11:    */ import org.apache.hadoop.mapreduce.JobID;
/*  12:    */ import org.apache.hadoop.mapreduce.Mapper;
/*  13:    */ import org.apache.hadoop.mapreduce.OutputCommitter;
/*  14:    */ import org.apache.hadoop.mapreduce.OutputFormat;
/*  15:    */ import org.apache.hadoop.mapreduce.Partitioner;
/*  16:    */ import org.apache.hadoop.mapreduce.RecordWriter;
/*  17:    */ import org.apache.hadoop.mapreduce.ReduceContext;
/*  18:    */ import org.apache.hadoop.mapreduce.Reducer;
/*  19:    */ import org.apache.hadoop.mapreduce.TaskAttemptID;
/*  20:    */ import org.apache.hadoop.security.Credentials;
/*  21:    */ 
/*  22:    */ class ChainReduceContextImpl<KEYIN, VALUEIN, KEYOUT, VALUEOUT>
/*  23:    */   implements ReduceContext<KEYIN, VALUEIN, KEYOUT, VALUEOUT>
/*  24:    */ {
/*  25:    */   private final ReduceContext<KEYIN, VALUEIN, KEYOUT, VALUEOUT> base;
/*  26:    */   private final RecordWriter<KEYOUT, VALUEOUT> rw;
/*  27:    */   private final Configuration conf;
/*  28:    */   
/*  29:    */   public ChainReduceContextImpl(ReduceContext<KEYIN, VALUEIN, KEYOUT, VALUEOUT> base, RecordWriter<KEYOUT, VALUEOUT> output, Configuration conf)
/*  30:    */   {
/*  31: 55 */     this.base = base;
/*  32: 56 */     this.rw = output;
/*  33: 57 */     this.conf = conf;
/*  34:    */   }
/*  35:    */   
/*  36:    */   public Iterable<VALUEIN> getValues()
/*  37:    */     throws IOException, InterruptedException
/*  38:    */   {
/*  39: 62 */     return this.base.getValues();
/*  40:    */   }
/*  41:    */   
/*  42:    */   public boolean nextKey()
/*  43:    */     throws IOException, InterruptedException
/*  44:    */   {
/*  45: 67 */     return this.base.nextKey();
/*  46:    */   }
/*  47:    */   
/*  48:    */   public Counter getCounter(Enum<?> counterName)
/*  49:    */   {
/*  50: 72 */     return this.base.getCounter(counterName);
/*  51:    */   }
/*  52:    */   
/*  53:    */   public Counter getCounter(String groupName, String counterName)
/*  54:    */   {
/*  55: 77 */     return this.base.getCounter(groupName, counterName);
/*  56:    */   }
/*  57:    */   
/*  58:    */   public KEYIN getCurrentKey()
/*  59:    */     throws IOException, InterruptedException
/*  60:    */   {
/*  61: 82 */     return this.base.getCurrentKey();
/*  62:    */   }
/*  63:    */   
/*  64:    */   public VALUEIN getCurrentValue()
/*  65:    */     throws IOException, InterruptedException
/*  66:    */   {
/*  67: 87 */     return this.base.getCurrentValue();
/*  68:    */   }
/*  69:    */   
/*  70:    */   public OutputCommitter getOutputCommitter()
/*  71:    */   {
/*  72: 92 */     return this.base.getOutputCommitter();
/*  73:    */   }
/*  74:    */   
/*  75:    */   public boolean nextKeyValue()
/*  76:    */     throws IOException, InterruptedException
/*  77:    */   {
/*  78: 97 */     return this.base.nextKeyValue();
/*  79:    */   }
/*  80:    */   
/*  81:    */   public void write(KEYOUT key, VALUEOUT value)
/*  82:    */     throws IOException, InterruptedException
/*  83:    */   {
/*  84:103 */     this.rw.write(key, value);
/*  85:    */   }
/*  86:    */   
/*  87:    */   public String getStatus()
/*  88:    */   {
/*  89:108 */     return this.base.getStatus();
/*  90:    */   }
/*  91:    */   
/*  92:    */   public TaskAttemptID getTaskAttemptID()
/*  93:    */   {
/*  94:113 */     return this.base.getTaskAttemptID();
/*  95:    */   }
/*  96:    */   
/*  97:    */   public void setStatus(String msg)
/*  98:    */   {
/*  99:118 */     this.base.setStatus(msg);
/* 100:    */   }
/* 101:    */   
/* 102:    */   public Path[] getArchiveClassPaths()
/* 103:    */   {
/* 104:123 */     return this.base.getArchiveClassPaths();
/* 105:    */   }
/* 106:    */   
/* 107:    */   public String[] getArchiveTimestamps()
/* 108:    */   {
/* 109:128 */     return this.base.getArchiveTimestamps();
/* 110:    */   }
/* 111:    */   
/* 112:    */   public URI[] getCacheArchives()
/* 113:    */     throws IOException
/* 114:    */   {
/* 115:133 */     return this.base.getCacheArchives();
/* 116:    */   }
/* 117:    */   
/* 118:    */   public URI[] getCacheFiles()
/* 119:    */     throws IOException
/* 120:    */   {
/* 121:138 */     return this.base.getCacheFiles();
/* 122:    */   }
/* 123:    */   
/* 124:    */   public Class<? extends Reducer<?, ?, ?, ?>> getCombinerClass()
/* 125:    */     throws ClassNotFoundException
/* 126:    */   {
/* 127:144 */     return this.base.getCombinerClass();
/* 128:    */   }
/* 129:    */   
/* 130:    */   public Configuration getConfiguration()
/* 131:    */   {
/* 132:149 */     return this.conf;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public Path[] getFileClassPaths()
/* 136:    */   {
/* 137:154 */     return this.base.getFileClassPaths();
/* 138:    */   }
/* 139:    */   
/* 140:    */   public String[] getFileTimestamps()
/* 141:    */   {
/* 142:159 */     return this.base.getFileTimestamps();
/* 143:    */   }
/* 144:    */   
/* 145:    */   public RawComparator<?> getCombinerKeyGroupingComparator()
/* 146:    */   {
/* 147:164 */     return this.base.getCombinerKeyGroupingComparator();
/* 148:    */   }
/* 149:    */   
/* 150:    */   public RawComparator<?> getGroupingComparator()
/* 151:    */   {
/* 152:169 */     return this.base.getGroupingComparator();
/* 153:    */   }
/* 154:    */   
/* 155:    */   public Class<? extends InputFormat<?, ?>> getInputFormatClass()
/* 156:    */     throws ClassNotFoundException
/* 157:    */   {
/* 158:175 */     return this.base.getInputFormatClass();
/* 159:    */   }
/* 160:    */   
/* 161:    */   public String getJar()
/* 162:    */   {
/* 163:180 */     return this.base.getJar();
/* 164:    */   }
/* 165:    */   
/* 166:    */   public JobID getJobID()
/* 167:    */   {
/* 168:185 */     return this.base.getJobID();
/* 169:    */   }
/* 170:    */   
/* 171:    */   public String getJobName()
/* 172:    */   {
/* 173:190 */     return this.base.getJobName();
/* 174:    */   }
/* 175:    */   
/* 176:    */   public boolean getJobSetupCleanupNeeded()
/* 177:    */   {
/* 178:195 */     return this.base.getJobSetupCleanupNeeded();
/* 179:    */   }
/* 180:    */   
/* 181:    */   public boolean getTaskCleanupNeeded()
/* 182:    */   {
/* 183:200 */     return this.base.getTaskCleanupNeeded();
/* 184:    */   }
/* 185:    */   
/* 186:    */   public Path[] getLocalCacheArchives()
/* 187:    */     throws IOException
/* 188:    */   {
/* 189:205 */     return this.base.getLocalCacheArchives();
/* 190:    */   }
/* 191:    */   
/* 192:    */   public Path[] getLocalCacheFiles()
/* 193:    */     throws IOException
/* 194:    */   {
/* 195:210 */     return this.base.getLocalCacheFiles();
/* 196:    */   }
/* 197:    */   
/* 198:    */   public Class<?> getMapOutputKeyClass()
/* 199:    */   {
/* 200:215 */     return this.base.getMapOutputKeyClass();
/* 201:    */   }
/* 202:    */   
/* 203:    */   public Class<?> getMapOutputValueClass()
/* 204:    */   {
/* 205:220 */     return this.base.getMapOutputValueClass();
/* 206:    */   }
/* 207:    */   
/* 208:    */   public Class<? extends Mapper<?, ?, ?, ?>> getMapperClass()
/* 209:    */     throws ClassNotFoundException
/* 210:    */   {
/* 211:226 */     return this.base.getMapperClass();
/* 212:    */   }
/* 213:    */   
/* 214:    */   public int getMaxMapAttempts()
/* 215:    */   {
/* 216:231 */     return this.base.getMaxMapAttempts();
/* 217:    */   }
/* 218:    */   
/* 219:    */   public int getMaxReduceAttempts()
/* 220:    */   {
/* 221:236 */     return this.base.getMaxMapAttempts();
/* 222:    */   }
/* 223:    */   
/* 224:    */   public int getNumReduceTasks()
/* 225:    */   {
/* 226:241 */     return this.base.getNumReduceTasks();
/* 227:    */   }
/* 228:    */   
/* 229:    */   public Class<? extends OutputFormat<?, ?>> getOutputFormatClass()
/* 230:    */     throws ClassNotFoundException
/* 231:    */   {
/* 232:247 */     return this.base.getOutputFormatClass();
/* 233:    */   }
/* 234:    */   
/* 235:    */   public Class<?> getOutputKeyClass()
/* 236:    */   {
/* 237:252 */     return this.base.getOutputKeyClass();
/* 238:    */   }
/* 239:    */   
/* 240:    */   public Class<?> getOutputValueClass()
/* 241:    */   {
/* 242:257 */     return this.base.getOutputValueClass();
/* 243:    */   }
/* 244:    */   
/* 245:    */   public Class<? extends Partitioner<?, ?>> getPartitionerClass()
/* 246:    */     throws ClassNotFoundException
/* 247:    */   {
/* 248:263 */     return this.base.getPartitionerClass();
/* 249:    */   }
/* 250:    */   
/* 251:    */   public boolean getProfileEnabled()
/* 252:    */   {
/* 253:268 */     return this.base.getProfileEnabled();
/* 254:    */   }
/* 255:    */   
/* 256:    */   public String getProfileParams()
/* 257:    */   {
/* 258:273 */     return this.base.getProfileParams();
/* 259:    */   }
/* 260:    */   
/* 261:    */   public Configuration.IntegerRanges getProfileTaskRange(boolean isMap)
/* 262:    */   {
/* 263:278 */     return this.base.getProfileTaskRange(isMap);
/* 264:    */   }
/* 265:    */   
/* 266:    */   public Class<? extends Reducer<?, ?, ?, ?>> getReducerClass()
/* 267:    */     throws ClassNotFoundException
/* 268:    */   {
/* 269:284 */     return this.base.getReducerClass();
/* 270:    */   }
/* 271:    */   
/* 272:    */   public RawComparator<?> getSortComparator()
/* 273:    */   {
/* 274:289 */     return this.base.getSortComparator();
/* 275:    */   }
/* 276:    */   
/* 277:    */   public boolean getSymlink()
/* 278:    */   {
/* 279:294 */     return this.base.getSymlink();
/* 280:    */   }
/* 281:    */   
/* 282:    */   public String getUser()
/* 283:    */   {
/* 284:299 */     return this.base.getUser();
/* 285:    */   }
/* 286:    */   
/* 287:    */   public Path getWorkingDirectory()
/* 288:    */     throws IOException
/* 289:    */   {
/* 290:304 */     return this.base.getWorkingDirectory();
/* 291:    */   }
/* 292:    */   
/* 293:    */   public void progress()
/* 294:    */   {
/* 295:309 */     this.base.progress();
/* 296:    */   }
/* 297:    */   
/* 298:    */   public Credentials getCredentials()
/* 299:    */   {
/* 300:314 */     return this.base.getCredentials();
/* 301:    */   }
/* 302:    */   
/* 303:    */   public float getProgress()
/* 304:    */   {
/* 305:319 */     return this.base.getProgress();
/* 306:    */   }
/* 307:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.lib.chain.ChainReduceContextImpl
 * JD-Core Version:    0.7.0.1
 */