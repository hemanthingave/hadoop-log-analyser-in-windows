/*   1:    */ package org.apache.hadoop.mapreduce.lib.reduce;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.net.URI;
/*   5:    */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*   6:    */ import org.apache.hadoop.classification.InterfaceStability.Evolving;
/*   7:    */ import org.apache.hadoop.conf.Configuration;
/*   8:    */ import org.apache.hadoop.conf.Configuration.IntegerRanges;
/*   9:    */ import org.apache.hadoop.fs.Path;
/*  10:    */ import org.apache.hadoop.io.RawComparator;
/*  11:    */ import org.apache.hadoop.mapreduce.Counter;
/*  12:    */ import org.apache.hadoop.mapreduce.InputFormat;
/*  13:    */ import org.apache.hadoop.mapreduce.JobID;
/*  14:    */ import org.apache.hadoop.mapreduce.Mapper;
/*  15:    */ import org.apache.hadoop.mapreduce.OutputCommitter;
/*  16:    */ import org.apache.hadoop.mapreduce.OutputFormat;
/*  17:    */ import org.apache.hadoop.mapreduce.Partitioner;
/*  18:    */ import org.apache.hadoop.mapreduce.ReduceContext;
/*  19:    */ import org.apache.hadoop.mapreduce.Reducer;
/*  20:    */ import org.apache.hadoop.mapreduce.Reducer.Context;
/*  21:    */ import org.apache.hadoop.mapreduce.TaskAttemptID;
/*  22:    */ import org.apache.hadoop.security.Credentials;
/*  23:    */ 
/*  24:    */ @InterfaceAudience.Public
/*  25:    */ @InterfaceStability.Evolving
/*  26:    */ public class WrappedReducer<KEYIN, VALUEIN, KEYOUT, VALUEOUT>
/*  27:    */   extends Reducer<KEYIN, VALUEIN, KEYOUT, VALUEOUT>
/*  28:    */ {
/*  29:    */   public Reducer<KEYIN, VALUEIN, KEYOUT, VALUEOUT>.Context getReducerContext(ReduceContext<KEYIN, VALUEIN, KEYOUT, VALUEOUT> reduceContext)
/*  30:    */   {
/*  31: 58 */     return new Context(reduceContext);
/*  32:    */   }
/*  33:    */   
/*  34:    */   @InterfaceStability.Evolving
/*  35:    */   public class Context
/*  36:    */     extends Reducer.Context
/*  37:    */   {
/*  38:    */     protected ReduceContext<KEYIN, VALUEIN, KEYOUT, VALUEOUT> reduceContext;
/*  39:    */     
/*  40:    */     public Context()
/*  41:    */     {
/*  42: 68 */       super();
/*  43: 69 */       this.reduceContext = reduceContext;
/*  44:    */     }
/*  45:    */     
/*  46:    */     public KEYIN getCurrentKey()
/*  47:    */       throws IOException, InterruptedException
/*  48:    */     {
/*  49: 74 */       return this.reduceContext.getCurrentKey();
/*  50:    */     }
/*  51:    */     
/*  52:    */     public VALUEIN getCurrentValue()
/*  53:    */       throws IOException, InterruptedException
/*  54:    */     {
/*  55: 79 */       return this.reduceContext.getCurrentValue();
/*  56:    */     }
/*  57:    */     
/*  58:    */     public boolean nextKeyValue()
/*  59:    */       throws IOException, InterruptedException
/*  60:    */     {
/*  61: 84 */       return this.reduceContext.nextKeyValue();
/*  62:    */     }
/*  63:    */     
/*  64:    */     public Counter getCounter(Enum counterName)
/*  65:    */     {
/*  66: 89 */       return this.reduceContext.getCounter(counterName);
/*  67:    */     }
/*  68:    */     
/*  69:    */     public Counter getCounter(String groupName, String counterName)
/*  70:    */     {
/*  71: 94 */       return this.reduceContext.getCounter(groupName, counterName);
/*  72:    */     }
/*  73:    */     
/*  74:    */     public OutputCommitter getOutputCommitter()
/*  75:    */     {
/*  76: 99 */       return this.reduceContext.getOutputCommitter();
/*  77:    */     }
/*  78:    */     
/*  79:    */     public void write(KEYOUT key, VALUEOUT value)
/*  80:    */       throws IOException, InterruptedException
/*  81:    */     {
/*  82:105 */       this.reduceContext.write(key, value);
/*  83:    */     }
/*  84:    */     
/*  85:    */     public String getStatus()
/*  86:    */     {
/*  87:110 */       return this.reduceContext.getStatus();
/*  88:    */     }
/*  89:    */     
/*  90:    */     public TaskAttemptID getTaskAttemptID()
/*  91:    */     {
/*  92:115 */       return this.reduceContext.getTaskAttemptID();
/*  93:    */     }
/*  94:    */     
/*  95:    */     public void setStatus(String msg)
/*  96:    */     {
/*  97:120 */       this.reduceContext.setStatus(msg);
/*  98:    */     }
/*  99:    */     
/* 100:    */     public Path[] getArchiveClassPaths()
/* 101:    */     {
/* 102:125 */       return this.reduceContext.getArchiveClassPaths();
/* 103:    */     }
/* 104:    */     
/* 105:    */     public String[] getArchiveTimestamps()
/* 106:    */     {
/* 107:130 */       return this.reduceContext.getArchiveTimestamps();
/* 108:    */     }
/* 109:    */     
/* 110:    */     public URI[] getCacheArchives()
/* 111:    */       throws IOException
/* 112:    */     {
/* 113:135 */       return this.reduceContext.getCacheArchives();
/* 114:    */     }
/* 115:    */     
/* 116:    */     public URI[] getCacheFiles()
/* 117:    */       throws IOException
/* 118:    */     {
/* 119:140 */       return this.reduceContext.getCacheFiles();
/* 120:    */     }
/* 121:    */     
/* 122:    */     public Class<? extends Reducer<?, ?, ?, ?>> getCombinerClass()
/* 123:    */       throws ClassNotFoundException
/* 124:    */     {
/* 125:146 */       return this.reduceContext.getCombinerClass();
/* 126:    */     }
/* 127:    */     
/* 128:    */     public Configuration getConfiguration()
/* 129:    */     {
/* 130:151 */       return this.reduceContext.getConfiguration();
/* 131:    */     }
/* 132:    */     
/* 133:    */     public Path[] getFileClassPaths()
/* 134:    */     {
/* 135:156 */       return this.reduceContext.getFileClassPaths();
/* 136:    */     }
/* 137:    */     
/* 138:    */     public String[] getFileTimestamps()
/* 139:    */     {
/* 140:161 */       return this.reduceContext.getFileTimestamps();
/* 141:    */     }
/* 142:    */     
/* 143:    */     public RawComparator<?> getCombinerKeyGroupingComparator()
/* 144:    */     {
/* 145:166 */       return this.reduceContext.getCombinerKeyGroupingComparator();
/* 146:    */     }
/* 147:    */     
/* 148:    */     public RawComparator<?> getGroupingComparator()
/* 149:    */     {
/* 150:171 */       return this.reduceContext.getGroupingComparator();
/* 151:    */     }
/* 152:    */     
/* 153:    */     public Class<? extends InputFormat<?, ?>> getInputFormatClass()
/* 154:    */       throws ClassNotFoundException
/* 155:    */     {
/* 156:177 */       return this.reduceContext.getInputFormatClass();
/* 157:    */     }
/* 158:    */     
/* 159:    */     public String getJar()
/* 160:    */     {
/* 161:182 */       return this.reduceContext.getJar();
/* 162:    */     }
/* 163:    */     
/* 164:    */     public JobID getJobID()
/* 165:    */     {
/* 166:187 */       return this.reduceContext.getJobID();
/* 167:    */     }
/* 168:    */     
/* 169:    */     public String getJobName()
/* 170:    */     {
/* 171:192 */       return this.reduceContext.getJobName();
/* 172:    */     }
/* 173:    */     
/* 174:    */     public boolean getJobSetupCleanupNeeded()
/* 175:    */     {
/* 176:197 */       return this.reduceContext.getJobSetupCleanupNeeded();
/* 177:    */     }
/* 178:    */     
/* 179:    */     public boolean getTaskCleanupNeeded()
/* 180:    */     {
/* 181:202 */       return this.reduceContext.getTaskCleanupNeeded();
/* 182:    */     }
/* 183:    */     
/* 184:    */     public Path[] getLocalCacheArchives()
/* 185:    */       throws IOException
/* 186:    */     {
/* 187:207 */       return this.reduceContext.getLocalCacheArchives();
/* 188:    */     }
/* 189:    */     
/* 190:    */     public Path[] getLocalCacheFiles()
/* 191:    */       throws IOException
/* 192:    */     {
/* 193:212 */       return this.reduceContext.getLocalCacheFiles();
/* 194:    */     }
/* 195:    */     
/* 196:    */     public Class<?> getMapOutputKeyClass()
/* 197:    */     {
/* 198:217 */       return this.reduceContext.getMapOutputKeyClass();
/* 199:    */     }
/* 200:    */     
/* 201:    */     public Class<?> getMapOutputValueClass()
/* 202:    */     {
/* 203:222 */       return this.reduceContext.getMapOutputValueClass();
/* 204:    */     }
/* 205:    */     
/* 206:    */     public Class<? extends Mapper<?, ?, ?, ?>> getMapperClass()
/* 207:    */       throws ClassNotFoundException
/* 208:    */     {
/* 209:228 */       return this.reduceContext.getMapperClass();
/* 210:    */     }
/* 211:    */     
/* 212:    */     public int getMaxMapAttempts()
/* 213:    */     {
/* 214:233 */       return this.reduceContext.getMaxMapAttempts();
/* 215:    */     }
/* 216:    */     
/* 217:    */     public int getMaxReduceAttempts()
/* 218:    */     {
/* 219:238 */       return this.reduceContext.getMaxReduceAttempts();
/* 220:    */     }
/* 221:    */     
/* 222:    */     public int getNumReduceTasks()
/* 223:    */     {
/* 224:243 */       return this.reduceContext.getNumReduceTasks();
/* 225:    */     }
/* 226:    */     
/* 227:    */     public Class<? extends OutputFormat<?, ?>> getOutputFormatClass()
/* 228:    */       throws ClassNotFoundException
/* 229:    */     {
/* 230:249 */       return this.reduceContext.getOutputFormatClass();
/* 231:    */     }
/* 232:    */     
/* 233:    */     public Class<?> getOutputKeyClass()
/* 234:    */     {
/* 235:254 */       return this.reduceContext.getOutputKeyClass();
/* 236:    */     }
/* 237:    */     
/* 238:    */     public Class<?> getOutputValueClass()
/* 239:    */     {
/* 240:259 */       return this.reduceContext.getOutputValueClass();
/* 241:    */     }
/* 242:    */     
/* 243:    */     public Class<? extends Partitioner<?, ?>> getPartitionerClass()
/* 244:    */       throws ClassNotFoundException
/* 245:    */     {
/* 246:265 */       return this.reduceContext.getPartitionerClass();
/* 247:    */     }
/* 248:    */     
/* 249:    */     public Class<? extends Reducer<?, ?, ?, ?>> getReducerClass()
/* 250:    */       throws ClassNotFoundException
/* 251:    */     {
/* 252:271 */       return this.reduceContext.getReducerClass();
/* 253:    */     }
/* 254:    */     
/* 255:    */     public RawComparator<?> getSortComparator()
/* 256:    */     {
/* 257:276 */       return this.reduceContext.getSortComparator();
/* 258:    */     }
/* 259:    */     
/* 260:    */     public boolean getSymlink()
/* 261:    */     {
/* 262:281 */       return this.reduceContext.getSymlink();
/* 263:    */     }
/* 264:    */     
/* 265:    */     public Path getWorkingDirectory()
/* 266:    */       throws IOException
/* 267:    */     {
/* 268:286 */       return this.reduceContext.getWorkingDirectory();
/* 269:    */     }
/* 270:    */     
/* 271:    */     public void progress()
/* 272:    */     {
/* 273:291 */       this.reduceContext.progress();
/* 274:    */     }
/* 275:    */     
/* 276:    */     public Iterable<VALUEIN> getValues()
/* 277:    */       throws IOException, InterruptedException
/* 278:    */     {
/* 279:297 */       return this.reduceContext.getValues();
/* 280:    */     }
/* 281:    */     
/* 282:    */     public boolean nextKey()
/* 283:    */       throws IOException, InterruptedException
/* 284:    */     {
/* 285:302 */       return this.reduceContext.nextKey();
/* 286:    */     }
/* 287:    */     
/* 288:    */     public boolean getProfileEnabled()
/* 289:    */     {
/* 290:307 */       return this.reduceContext.getProfileEnabled();
/* 291:    */     }
/* 292:    */     
/* 293:    */     public String getProfileParams()
/* 294:    */     {
/* 295:312 */       return this.reduceContext.getProfileParams();
/* 296:    */     }
/* 297:    */     
/* 298:    */     public Configuration.IntegerRanges getProfileTaskRange(boolean isMap)
/* 299:    */     {
/* 300:317 */       return this.reduceContext.getProfileTaskRange(isMap);
/* 301:    */     }
/* 302:    */     
/* 303:    */     public String getUser()
/* 304:    */     {
/* 305:322 */       return this.reduceContext.getUser();
/* 306:    */     }
/* 307:    */     
/* 308:    */     public Credentials getCredentials()
/* 309:    */     {
/* 310:327 */       return this.reduceContext.getCredentials();
/* 311:    */     }
/* 312:    */     
/* 313:    */     public float getProgress()
/* 314:    */     {
/* 315:332 */       return this.reduceContext.getProgress();
/* 316:    */     }
/* 317:    */   }
/* 318:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.lib.reduce.WrappedReducer
 * JD-Core Version:    0.7.0.1
 */