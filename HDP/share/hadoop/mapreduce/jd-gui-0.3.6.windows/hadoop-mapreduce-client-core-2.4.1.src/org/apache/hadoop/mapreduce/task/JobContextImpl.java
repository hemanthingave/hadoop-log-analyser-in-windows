/*   1:    */ package org.apache.hadoop.mapreduce.task;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.net.URI;
/*   5:    */ import org.apache.hadoop.classification.InterfaceAudience.Private;
/*   6:    */ import org.apache.hadoop.classification.InterfaceStability.Unstable;
/*   7:    */ import org.apache.hadoop.conf.Configuration;
/*   8:    */ import org.apache.hadoop.conf.Configuration.IntegerRanges;
/*   9:    */ import org.apache.hadoop.fs.Path;
/*  10:    */ import org.apache.hadoop.io.RawComparator;
/*  11:    */ import org.apache.hadoop.mapred.JobConf;
/*  12:    */ import org.apache.hadoop.mapreduce.InputFormat;
/*  13:    */ import org.apache.hadoop.mapreduce.JobContext;
/*  14:    */ import org.apache.hadoop.mapreduce.JobID;
/*  15:    */ import org.apache.hadoop.mapreduce.Mapper;
/*  16:    */ import org.apache.hadoop.mapreduce.OutputFormat;
/*  17:    */ import org.apache.hadoop.mapreduce.Partitioner;
/*  18:    */ import org.apache.hadoop.mapreduce.Reducer;
/*  19:    */ import org.apache.hadoop.mapreduce.filecache.DistributedCache;
/*  20:    */ import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
/*  21:    */ import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
/*  22:    */ import org.apache.hadoop.mapreduce.lib.partition.HashPartitioner;
/*  23:    */ import org.apache.hadoop.security.Credentials;
/*  24:    */ import org.apache.hadoop.security.UserGroupInformation;
/*  25:    */ 
/*  26:    */ @InterfaceAudience.Private
/*  27:    */ @InterfaceStability.Unstable
/*  28:    */ public class JobContextImpl
/*  29:    */   implements JobContext
/*  30:    */ {
/*  31:    */   protected final JobConf conf;
/*  32:    */   private JobID jobId;
/*  33:    */   protected UserGroupInformation ugi;
/*  34:    */   protected final Credentials credentials;
/*  35:    */   
/*  36:    */   public JobContextImpl(Configuration conf, JobID jobId)
/*  37:    */   {
/*  38: 64 */     if ((conf instanceof JobConf)) {
/*  39: 65 */       this.conf = ((JobConf)conf);
/*  40:    */     } else {
/*  41: 67 */       this.conf = new JobConf(conf);
/*  42:    */     }
/*  43: 69 */     this.jobId = jobId;
/*  44: 70 */     this.credentials = this.conf.getCredentials();
/*  45:    */     try
/*  46:    */     {
/*  47: 72 */       this.ugi = UserGroupInformation.getCurrentUser();
/*  48:    */     }
/*  49:    */     catch (IOException e)
/*  50:    */     {
/*  51: 74 */       throw new RuntimeException(e);
/*  52:    */     }
/*  53:    */   }
/*  54:    */   
/*  55:    */   public Configuration getConfiguration()
/*  56:    */   {
/*  57: 83 */     return this.conf;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public JobID getJobID()
/*  61:    */   {
/*  62: 91 */     return this.jobId;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public void setJobID(JobID jobId)
/*  66:    */   {
/*  67: 98 */     this.jobId = jobId;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public int getNumReduceTasks()
/*  71:    */   {
/*  72:107 */     return this.conf.getNumReduceTasks();
/*  73:    */   }
/*  74:    */   
/*  75:    */   public Path getWorkingDirectory()
/*  76:    */     throws IOException
/*  77:    */   {
/*  78:116 */     return this.conf.getWorkingDirectory();
/*  79:    */   }
/*  80:    */   
/*  81:    */   public Class<?> getOutputKeyClass()
/*  82:    */   {
/*  83:124 */     return this.conf.getOutputKeyClass();
/*  84:    */   }
/*  85:    */   
/*  86:    */   public Class<?> getOutputValueClass()
/*  87:    */   {
/*  88:132 */     return this.conf.getOutputValueClass();
/*  89:    */   }
/*  90:    */   
/*  91:    */   public Class<?> getMapOutputKeyClass()
/*  92:    */   {
/*  93:142 */     return this.conf.getMapOutputKeyClass();
/*  94:    */   }
/*  95:    */   
/*  96:    */   public Class<?> getMapOutputValueClass()
/*  97:    */   {
/*  98:153 */     return this.conf.getMapOutputValueClass();
/*  99:    */   }
/* 100:    */   
/* 101:    */   public String getJobName()
/* 102:    */   {
/* 103:163 */     return this.conf.getJobName();
/* 104:    */   }
/* 105:    */   
/* 106:    */   public Class<? extends InputFormat<?, ?>> getInputFormatClass()
/* 107:    */     throws ClassNotFoundException
/* 108:    */   {
/* 109:174 */     return this.conf.getClass("mapreduce.job.inputformat.class", TextInputFormat.class);
/* 110:    */   }
/* 111:    */   
/* 112:    */   public Class<? extends Mapper<?, ?, ?, ?>> getMapperClass()
/* 113:    */     throws ClassNotFoundException
/* 114:    */   {
/* 115:186 */     return this.conf.getClass("mapreduce.job.map.class", Mapper.class);
/* 116:    */   }
/* 117:    */   
/* 118:    */   public Class<? extends Reducer<?, ?, ?, ?>> getCombinerClass()
/* 119:    */     throws ClassNotFoundException
/* 120:    */   {
/* 121:198 */     return this.conf.getClass("mapreduce.job.combine.class", null);
/* 122:    */   }
/* 123:    */   
/* 124:    */   public Class<? extends Reducer<?, ?, ?, ?>> getReducerClass()
/* 125:    */     throws ClassNotFoundException
/* 126:    */   {
/* 127:210 */     return this.conf.getClass("mapreduce.job.reduce.class", Reducer.class);
/* 128:    */   }
/* 129:    */   
/* 130:    */   public Class<? extends OutputFormat<?, ?>> getOutputFormatClass()
/* 131:    */     throws ClassNotFoundException
/* 132:    */   {
/* 133:222 */     return this.conf.getClass("mapreduce.job.outputformat.class", TextOutputFormat.class);
/* 134:    */   }
/* 135:    */   
/* 136:    */   public Class<? extends Partitioner<?, ?>> getPartitionerClass()
/* 137:    */     throws ClassNotFoundException
/* 138:    */   {
/* 139:234 */     return this.conf.getClass("mapreduce.job.partitioner.class", HashPartitioner.class);
/* 140:    */   }
/* 141:    */   
/* 142:    */   public RawComparator<?> getSortComparator()
/* 143:    */   {
/* 144:244 */     return this.conf.getOutputKeyComparator();
/* 145:    */   }
/* 146:    */   
/* 147:    */   public String getJar()
/* 148:    */   {
/* 149:252 */     return this.conf.getJar();
/* 150:    */   }
/* 151:    */   
/* 152:    */   public RawComparator<?> getCombinerKeyGroupingComparator()
/* 153:    */   {
/* 154:263 */     return this.conf.getCombinerKeyGroupingComparator();
/* 155:    */   }
/* 156:    */   
/* 157:    */   public RawComparator<?> getGroupingComparator()
/* 158:    */   {
/* 159:274 */     return this.conf.getOutputValueGroupingComparator();
/* 160:    */   }
/* 161:    */   
/* 162:    */   public boolean getJobSetupCleanupNeeded()
/* 163:    */   {
/* 164:283 */     return this.conf.getBoolean("mapreduce.job.committer.setup.cleanup.needed", true);
/* 165:    */   }
/* 166:    */   
/* 167:    */   public boolean getTaskCleanupNeeded()
/* 168:    */   {
/* 169:292 */     return this.conf.getBoolean("mapreduce.job.committer.task.cleanup.needed", true);
/* 170:    */   }
/* 171:    */   
/* 172:    */   public boolean getSymlink()
/* 173:    */   {
/* 174:301 */     return DistributedCache.getSymlink(this.conf);
/* 175:    */   }
/* 176:    */   
/* 177:    */   public Path[] getArchiveClassPaths()
/* 178:    */   {
/* 179:308 */     return DistributedCache.getArchiveClassPaths(this.conf);
/* 180:    */   }
/* 181:    */   
/* 182:    */   public URI[] getCacheArchives()
/* 183:    */     throws IOException
/* 184:    */   {
/* 185:317 */     return DistributedCache.getCacheArchives(this.conf);
/* 186:    */   }
/* 187:    */   
/* 188:    */   public URI[] getCacheFiles()
/* 189:    */     throws IOException
/* 190:    */   {
/* 191:327 */     return DistributedCache.getCacheFiles(this.conf);
/* 192:    */   }
/* 193:    */   
/* 194:    */   public Path[] getLocalCacheArchives()
/* 195:    */     throws IOException
/* 196:    */   {
/* 197:337 */     return DistributedCache.getLocalCacheArchives(this.conf);
/* 198:    */   }
/* 199:    */   
/* 200:    */   public Path[] getLocalCacheFiles()
/* 201:    */     throws IOException
/* 202:    */   {
/* 203:347 */     return DistributedCache.getLocalCacheFiles(this.conf);
/* 204:    */   }
/* 205:    */   
/* 206:    */   public Path[] getFileClassPaths()
/* 207:    */   {
/* 208:354 */     return DistributedCache.getFileClassPaths(this.conf);
/* 209:    */   }
/* 210:    */   
/* 211:    */   private static String[] toTimestampStrs(long[] timestamps)
/* 212:    */   {
/* 213:363 */     if (timestamps == null) {
/* 214:364 */       return null;
/* 215:    */     }
/* 216:366 */     String[] result = new String[timestamps.length];
/* 217:367 */     for (int i = 0; i < timestamps.length; i++) {
/* 218:368 */       result[i] = Long.toString(timestamps[i]);
/* 219:    */     }
/* 220:370 */     return result;
/* 221:    */   }
/* 222:    */   
/* 223:    */   public String[] getArchiveTimestamps()
/* 224:    */   {
/* 225:380 */     return toTimestampStrs(DistributedCache.getArchiveTimestamps(this.conf));
/* 226:    */   }
/* 227:    */   
/* 228:    */   public String[] getFileTimestamps()
/* 229:    */   {
/* 230:390 */     return toTimestampStrs(DistributedCache.getFileTimestamps(this.conf));
/* 231:    */   }
/* 232:    */   
/* 233:    */   public int getMaxMapAttempts()
/* 234:    */   {
/* 235:401 */     return this.conf.getMaxMapAttempts();
/* 236:    */   }
/* 237:    */   
/* 238:    */   public int getMaxReduceAttempts()
/* 239:    */   {
/* 240:412 */     return this.conf.getMaxReduceAttempts();
/* 241:    */   }
/* 242:    */   
/* 243:    */   public boolean getProfileEnabled()
/* 244:    */   {
/* 245:420 */     return this.conf.getProfileEnabled();
/* 246:    */   }
/* 247:    */   
/* 248:    */   public String getProfileParams()
/* 249:    */   {
/* 250:432 */     return this.conf.getProfileParams();
/* 251:    */   }
/* 252:    */   
/* 253:    */   public Configuration.IntegerRanges getProfileTaskRange(boolean isMap)
/* 254:    */   {
/* 255:441 */     return this.conf.getProfileTaskRange(isMap);
/* 256:    */   }
/* 257:    */   
/* 258:    */   public String getUser()
/* 259:    */   {
/* 260:450 */     return this.conf.getUser();
/* 261:    */   }
/* 262:    */   
/* 263:    */   public Credentials getCredentials()
/* 264:    */   {
/* 265:454 */     return this.credentials;
/* 266:    */   }
/* 267:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.task.JobContextImpl
 * JD-Core Version:    0.7.0.1
 */