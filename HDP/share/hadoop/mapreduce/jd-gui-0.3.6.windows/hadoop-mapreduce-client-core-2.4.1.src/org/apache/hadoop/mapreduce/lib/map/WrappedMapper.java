/*   1:    */ package org.apache.hadoop.mapreduce.lib.map;
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
/*  13:    */ import org.apache.hadoop.mapreduce.InputSplit;
/*  14:    */ import org.apache.hadoop.mapreduce.JobID;
/*  15:    */ import org.apache.hadoop.mapreduce.MapContext;
/*  16:    */ import org.apache.hadoop.mapreduce.Mapper;
/*  17:    */ import org.apache.hadoop.mapreduce.Mapper.Context;
/*  18:    */ import org.apache.hadoop.mapreduce.OutputCommitter;
/*  19:    */ import org.apache.hadoop.mapreduce.OutputFormat;
/*  20:    */ import org.apache.hadoop.mapreduce.Partitioner;
/*  21:    */ import org.apache.hadoop.mapreduce.Reducer;
/*  22:    */ import org.apache.hadoop.mapreduce.TaskAttemptID;
/*  23:    */ import org.apache.hadoop.security.Credentials;
/*  24:    */ 
/*  25:    */ @InterfaceAudience.Public
/*  26:    */ @InterfaceStability.Evolving
/*  27:    */ public class WrappedMapper<KEYIN, VALUEIN, KEYOUT, VALUEOUT>
/*  28:    */   extends Mapper<KEYIN, VALUEIN, KEYOUT, VALUEOUT>
/*  29:    */ {
/*  30:    */   public Mapper<KEYIN, VALUEIN, KEYOUT, VALUEOUT>.Context getMapContext(MapContext<KEYIN, VALUEIN, KEYOUT, VALUEOUT> mapContext)
/*  31:    */   {
/*  32: 59 */     return new Context(mapContext);
/*  33:    */   }
/*  34:    */   
/*  35:    */   @InterfaceStability.Evolving
/*  36:    */   public class Context
/*  37:    */     extends Mapper.Context
/*  38:    */   {
/*  39:    */     protected MapContext<KEYIN, VALUEIN, KEYOUT, VALUEOUT> mapContext;
/*  40:    */     
/*  41:    */     public Context()
/*  42:    */     {
/*  43: 68 */       super();
/*  44: 69 */       this.mapContext = mapContext;
/*  45:    */     }
/*  46:    */     
/*  47:    */     public InputSplit getInputSplit()
/*  48:    */     {
/*  49: 76 */       return this.mapContext.getInputSplit();
/*  50:    */     }
/*  51:    */     
/*  52:    */     public KEYIN getCurrentKey()
/*  53:    */       throws IOException, InterruptedException
/*  54:    */     {
/*  55: 81 */       return this.mapContext.getCurrentKey();
/*  56:    */     }
/*  57:    */     
/*  58:    */     public VALUEIN getCurrentValue()
/*  59:    */       throws IOException, InterruptedException
/*  60:    */     {
/*  61: 86 */       return this.mapContext.getCurrentValue();
/*  62:    */     }
/*  63:    */     
/*  64:    */     public boolean nextKeyValue()
/*  65:    */       throws IOException, InterruptedException
/*  66:    */     {
/*  67: 91 */       return this.mapContext.nextKeyValue();
/*  68:    */     }
/*  69:    */     
/*  70:    */     public Counter getCounter(Enum<?> counterName)
/*  71:    */     {
/*  72: 96 */       return this.mapContext.getCounter(counterName);
/*  73:    */     }
/*  74:    */     
/*  75:    */     public Counter getCounter(String groupName, String counterName)
/*  76:    */     {
/*  77:101 */       return this.mapContext.getCounter(groupName, counterName);
/*  78:    */     }
/*  79:    */     
/*  80:    */     public OutputCommitter getOutputCommitter()
/*  81:    */     {
/*  82:106 */       return this.mapContext.getOutputCommitter();
/*  83:    */     }
/*  84:    */     
/*  85:    */     public void write(KEYOUT key, VALUEOUT value)
/*  86:    */       throws IOException, InterruptedException
/*  87:    */     {
/*  88:112 */       this.mapContext.write(key, value);
/*  89:    */     }
/*  90:    */     
/*  91:    */     public String getStatus()
/*  92:    */     {
/*  93:117 */       return this.mapContext.getStatus();
/*  94:    */     }
/*  95:    */     
/*  96:    */     public TaskAttemptID getTaskAttemptID()
/*  97:    */     {
/*  98:122 */       return this.mapContext.getTaskAttemptID();
/*  99:    */     }
/* 100:    */     
/* 101:    */     public void setStatus(String msg)
/* 102:    */     {
/* 103:127 */       this.mapContext.setStatus(msg);
/* 104:    */     }
/* 105:    */     
/* 106:    */     public Path[] getArchiveClassPaths()
/* 107:    */     {
/* 108:132 */       return this.mapContext.getArchiveClassPaths();
/* 109:    */     }
/* 110:    */     
/* 111:    */     public String[] getArchiveTimestamps()
/* 112:    */     {
/* 113:137 */       return this.mapContext.getArchiveTimestamps();
/* 114:    */     }
/* 115:    */     
/* 116:    */     public URI[] getCacheArchives()
/* 117:    */       throws IOException
/* 118:    */     {
/* 119:142 */       return this.mapContext.getCacheArchives();
/* 120:    */     }
/* 121:    */     
/* 122:    */     public URI[] getCacheFiles()
/* 123:    */       throws IOException
/* 124:    */     {
/* 125:147 */       return this.mapContext.getCacheFiles();
/* 126:    */     }
/* 127:    */     
/* 128:    */     public Class<? extends Reducer<?, ?, ?, ?>> getCombinerClass()
/* 129:    */       throws ClassNotFoundException
/* 130:    */     {
/* 131:153 */       return this.mapContext.getCombinerClass();
/* 132:    */     }
/* 133:    */     
/* 134:    */     public Configuration getConfiguration()
/* 135:    */     {
/* 136:158 */       return this.mapContext.getConfiguration();
/* 137:    */     }
/* 138:    */     
/* 139:    */     public Path[] getFileClassPaths()
/* 140:    */     {
/* 141:163 */       return this.mapContext.getFileClassPaths();
/* 142:    */     }
/* 143:    */     
/* 144:    */     public String[] getFileTimestamps()
/* 145:    */     {
/* 146:168 */       return this.mapContext.getFileTimestamps();
/* 147:    */     }
/* 148:    */     
/* 149:    */     public RawComparator<?> getCombinerKeyGroupingComparator()
/* 150:    */     {
/* 151:173 */       return this.mapContext.getCombinerKeyGroupingComparator();
/* 152:    */     }
/* 153:    */     
/* 154:    */     public RawComparator<?> getGroupingComparator()
/* 155:    */     {
/* 156:178 */       return this.mapContext.getGroupingComparator();
/* 157:    */     }
/* 158:    */     
/* 159:    */     public Class<? extends InputFormat<?, ?>> getInputFormatClass()
/* 160:    */       throws ClassNotFoundException
/* 161:    */     {
/* 162:184 */       return this.mapContext.getInputFormatClass();
/* 163:    */     }
/* 164:    */     
/* 165:    */     public String getJar()
/* 166:    */     {
/* 167:189 */       return this.mapContext.getJar();
/* 168:    */     }
/* 169:    */     
/* 170:    */     public JobID getJobID()
/* 171:    */     {
/* 172:194 */       return this.mapContext.getJobID();
/* 173:    */     }
/* 174:    */     
/* 175:    */     public String getJobName()
/* 176:    */     {
/* 177:199 */       return this.mapContext.getJobName();
/* 178:    */     }
/* 179:    */     
/* 180:    */     public boolean getJobSetupCleanupNeeded()
/* 181:    */     {
/* 182:204 */       return this.mapContext.getJobSetupCleanupNeeded();
/* 183:    */     }
/* 184:    */     
/* 185:    */     public boolean getTaskCleanupNeeded()
/* 186:    */     {
/* 187:209 */       return this.mapContext.getTaskCleanupNeeded();
/* 188:    */     }
/* 189:    */     
/* 190:    */     public Path[] getLocalCacheArchives()
/* 191:    */       throws IOException
/* 192:    */     {
/* 193:214 */       return this.mapContext.getLocalCacheArchives();
/* 194:    */     }
/* 195:    */     
/* 196:    */     public Path[] getLocalCacheFiles()
/* 197:    */       throws IOException
/* 198:    */     {
/* 199:219 */       return this.mapContext.getLocalCacheFiles();
/* 200:    */     }
/* 201:    */     
/* 202:    */     public Class<?> getMapOutputKeyClass()
/* 203:    */     {
/* 204:224 */       return this.mapContext.getMapOutputKeyClass();
/* 205:    */     }
/* 206:    */     
/* 207:    */     public Class<?> getMapOutputValueClass()
/* 208:    */     {
/* 209:229 */       return this.mapContext.getMapOutputValueClass();
/* 210:    */     }
/* 211:    */     
/* 212:    */     public Class<? extends Mapper<?, ?, ?, ?>> getMapperClass()
/* 213:    */       throws ClassNotFoundException
/* 214:    */     {
/* 215:235 */       return this.mapContext.getMapperClass();
/* 216:    */     }
/* 217:    */     
/* 218:    */     public int getMaxMapAttempts()
/* 219:    */     {
/* 220:240 */       return this.mapContext.getMaxMapAttempts();
/* 221:    */     }
/* 222:    */     
/* 223:    */     public int getMaxReduceAttempts()
/* 224:    */     {
/* 225:245 */       return this.mapContext.getMaxReduceAttempts();
/* 226:    */     }
/* 227:    */     
/* 228:    */     public int getNumReduceTasks()
/* 229:    */     {
/* 230:250 */       return this.mapContext.getNumReduceTasks();
/* 231:    */     }
/* 232:    */     
/* 233:    */     public Class<? extends OutputFormat<?, ?>> getOutputFormatClass()
/* 234:    */       throws ClassNotFoundException
/* 235:    */     {
/* 236:256 */       return this.mapContext.getOutputFormatClass();
/* 237:    */     }
/* 238:    */     
/* 239:    */     public Class<?> getOutputKeyClass()
/* 240:    */     {
/* 241:261 */       return this.mapContext.getOutputKeyClass();
/* 242:    */     }
/* 243:    */     
/* 244:    */     public Class<?> getOutputValueClass()
/* 245:    */     {
/* 246:266 */       return this.mapContext.getOutputValueClass();
/* 247:    */     }
/* 248:    */     
/* 249:    */     public Class<? extends Partitioner<?, ?>> getPartitionerClass()
/* 250:    */       throws ClassNotFoundException
/* 251:    */     {
/* 252:272 */       return this.mapContext.getPartitionerClass();
/* 253:    */     }
/* 254:    */     
/* 255:    */     public Class<? extends Reducer<?, ?, ?, ?>> getReducerClass()
/* 256:    */       throws ClassNotFoundException
/* 257:    */     {
/* 258:278 */       return this.mapContext.getReducerClass();
/* 259:    */     }
/* 260:    */     
/* 261:    */     public RawComparator<?> getSortComparator()
/* 262:    */     {
/* 263:283 */       return this.mapContext.getSortComparator();
/* 264:    */     }
/* 265:    */     
/* 266:    */     public boolean getSymlink()
/* 267:    */     {
/* 268:288 */       return this.mapContext.getSymlink();
/* 269:    */     }
/* 270:    */     
/* 271:    */     public Path getWorkingDirectory()
/* 272:    */       throws IOException
/* 273:    */     {
/* 274:293 */       return this.mapContext.getWorkingDirectory();
/* 275:    */     }
/* 276:    */     
/* 277:    */     public void progress()
/* 278:    */     {
/* 279:298 */       this.mapContext.progress();
/* 280:    */     }
/* 281:    */     
/* 282:    */     public boolean getProfileEnabled()
/* 283:    */     {
/* 284:303 */       return this.mapContext.getProfileEnabled();
/* 285:    */     }
/* 286:    */     
/* 287:    */     public String getProfileParams()
/* 288:    */     {
/* 289:308 */       return this.mapContext.getProfileParams();
/* 290:    */     }
/* 291:    */     
/* 292:    */     public Configuration.IntegerRanges getProfileTaskRange(boolean isMap)
/* 293:    */     {
/* 294:313 */       return this.mapContext.getProfileTaskRange(isMap);
/* 295:    */     }
/* 296:    */     
/* 297:    */     public String getUser()
/* 298:    */     {
/* 299:318 */       return this.mapContext.getUser();
/* 300:    */     }
/* 301:    */     
/* 302:    */     public Credentials getCredentials()
/* 303:    */     {
/* 304:323 */       return this.mapContext.getCredentials();
/* 305:    */     }
/* 306:    */     
/* 307:    */     public float getProgress()
/* 308:    */     {
/* 309:328 */       return this.mapContext.getProgress();
/* 310:    */     }
/* 311:    */   }
/* 312:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.lib.map.WrappedMapper
 * JD-Core Version:    0.7.0.1
 */