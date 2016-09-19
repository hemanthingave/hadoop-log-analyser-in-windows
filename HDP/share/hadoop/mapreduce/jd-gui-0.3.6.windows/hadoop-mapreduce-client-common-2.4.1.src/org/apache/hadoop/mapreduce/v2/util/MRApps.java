/*   1:    */ package org.apache.hadoop.mapreduce.v2.util;
/*   2:    */ 
/*   3:    */ import java.io.File;
/*   4:    */ import java.io.IOException;
/*   5:    */ import java.net.MalformedURLException;
/*   6:    */ import java.net.URI;
/*   7:    */ import java.net.URISyntaxException;
/*   8:    */ import java.security.AccessController;
/*   9:    */ import java.security.PrivilegedActionException;
/*  10:    */ import java.security.PrivilegedExceptionAction;
/*  11:    */ import java.util.Arrays;
/*  12:    */ import java.util.HashMap;
/*  13:    */ import java.util.List;
/*  14:    */ import java.util.Map;
/*  15:    */ import org.apache.commons.logging.Log;
/*  16:    */ import org.apache.commons.logging.LogFactory;
/*  17:    */ import org.apache.hadoop.classification.InterfaceAudience.Private;
/*  18:    */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*  19:    */ import org.apache.hadoop.classification.InterfaceStability.Unstable;
/*  20:    */ import org.apache.hadoop.conf.Configuration;
/*  21:    */ import org.apache.hadoop.fs.FileSystem;
/*  22:    */ import org.apache.hadoop.fs.Path;
/*  23:    */ import org.apache.hadoop.mapreduce.JobID;
/*  24:    */ import org.apache.hadoop.mapreduce.MRJobConfig;
/*  25:    */ import org.apache.hadoop.mapreduce.TaskAttemptID;
/*  26:    */ import org.apache.hadoop.mapreduce.TaskID;
/*  27:    */ import org.apache.hadoop.mapreduce.TypeConverter;
/*  28:    */ import org.apache.hadoop.mapreduce.filecache.DistributedCache;
/*  29:    */ import org.apache.hadoop.mapreduce.v2.api.records.JobId;
/*  30:    */ import org.apache.hadoop.mapreduce.v2.api.records.TaskAttemptId;
/*  31:    */ import org.apache.hadoop.mapreduce.v2.api.records.TaskAttemptState;
/*  32:    */ import org.apache.hadoop.mapreduce.v2.api.records.TaskId;
/*  33:    */ import org.apache.hadoop.mapreduce.v2.api.records.TaskState;
/*  34:    */ import org.apache.hadoop.mapreduce.v2.api.records.TaskType;
/*  35:    */ import org.apache.hadoop.util.StringUtils;
/*  36:    */ import org.apache.hadoop.yarn.api.ApplicationConstants.Environment;
/*  37:    */ import org.apache.hadoop.yarn.api.records.LocalResource;
/*  38:    */ import org.apache.hadoop.yarn.api.records.LocalResourceType;
/*  39:    */ import org.apache.hadoop.yarn.api.records.LocalResourceVisibility;
/*  40:    */ import org.apache.hadoop.yarn.api.records.URL;
/*  41:    */ import org.apache.hadoop.yarn.conf.YarnConfiguration;
/*  42:    */ import org.apache.hadoop.yarn.exceptions.YarnRuntimeException;
/*  43:    */ import org.apache.hadoop.yarn.util.ApplicationClassLoader;
/*  44:    */ import org.apache.hadoop.yarn.util.Apps;
/*  45:    */ import org.apache.hadoop.yarn.util.ConverterUtils;
/*  46:    */ 
/*  47:    */ @InterfaceAudience.Private
/*  48:    */ @InterfaceStability.Unstable
/*  49:    */ public class MRApps
/*  50:    */   extends Apps
/*  51:    */ {
/*  52: 80 */   public static final Log LOG = LogFactory.getLog(MRApps.class);
/*  53:    */   private static final String STAGING_CONSTANT = ".staging";
/*  54:    */   
/*  55:    */   public static String toString(JobId jid)
/*  56:    */   {
/*  57: 83 */     return jid.toString();
/*  58:    */   }
/*  59:    */   
/*  60:    */   public static JobId toJobID(String jid)
/*  61:    */   {
/*  62: 87 */     return TypeConverter.toYarn(JobID.forName(jid));
/*  63:    */   }
/*  64:    */   
/*  65:    */   public static String toString(TaskId tid)
/*  66:    */   {
/*  67: 91 */     return tid.toString();
/*  68:    */   }
/*  69:    */   
/*  70:    */   public static TaskId toTaskID(String tid)
/*  71:    */   {
/*  72: 95 */     return TypeConverter.toYarn(TaskID.forName(tid));
/*  73:    */   }
/*  74:    */   
/*  75:    */   public static String toString(TaskAttemptId taid)
/*  76:    */   {
/*  77: 99 */     return taid.toString();
/*  78:    */   }
/*  79:    */   
/*  80:    */   public static TaskAttemptId toTaskAttemptID(String taid)
/*  81:    */   {
/*  82:103 */     return TypeConverter.toYarn(TaskAttemptID.forName(taid));
/*  83:    */   }
/*  84:    */   
/*  85:    */   public static String taskSymbol(TaskType type)
/*  86:    */   {
/*  87:107 */     switch (2.$SwitchMap$org$apache$hadoop$mapreduce$v2$api$records$TaskType[type.ordinal()])
/*  88:    */     {
/*  89:    */     case 1: 
/*  90:108 */       return "m";
/*  91:    */     case 2: 
/*  92:109 */       return "r";
/*  93:    */     }
/*  94:111 */     throw new YarnRuntimeException("Unknown task type: " + type.toString());
/*  95:    */   }
/*  96:    */   
/*  97:    */   public static enum TaskAttemptStateUI
/*  98:    */   {
/*  99:115 */     NEW(new TaskAttemptState[] { TaskAttemptState.NEW, TaskAttemptState.STARTING }),  RUNNING(new TaskAttemptState[] { TaskAttemptState.RUNNING, TaskAttemptState.COMMIT_PENDING }),  SUCCESSFUL(new TaskAttemptState[] { TaskAttemptState.SUCCEEDED }),  FAILED(new TaskAttemptState[] { TaskAttemptState.FAILED }),  KILLED(new TaskAttemptState[] { TaskAttemptState.KILLED });
/* 100:    */     
/* 101:    */     private final List<TaskAttemptState> correspondingStates;
/* 102:    */     
/* 103:    */     private TaskAttemptStateUI(TaskAttemptState[] correspondingStates)
/* 104:    */     {
/* 105:128 */       this.correspondingStates = Arrays.asList(correspondingStates);
/* 106:    */     }
/* 107:    */     
/* 108:    */     public boolean correspondsTo(TaskAttemptState state)
/* 109:    */     {
/* 110:132 */       return this.correspondingStates.contains(state);
/* 111:    */     }
/* 112:    */   }
/* 113:    */   
/* 114:    */   public static enum TaskStateUI
/* 115:    */   {
/* 116:137 */     RUNNING(new TaskState[] { TaskState.RUNNING }),  PENDING(new TaskState[] { TaskState.SCHEDULED }),  COMPLETED(new TaskState[] { TaskState.SUCCEEDED, TaskState.FAILED, TaskState.KILLED });
/* 117:    */     
/* 118:    */     private final List<TaskState> correspondingStates;
/* 119:    */     
/* 120:    */     private TaskStateUI(TaskState[] correspondingStates)
/* 121:    */     {
/* 122:145 */       this.correspondingStates = Arrays.asList(correspondingStates);
/* 123:    */     }
/* 124:    */     
/* 125:    */     public boolean correspondsTo(TaskState state)
/* 126:    */     {
/* 127:149 */       return this.correspondingStates.contains(state);
/* 128:    */     }
/* 129:    */   }
/* 130:    */   
/* 131:    */   public static TaskType taskType(String symbol)
/* 132:    */   {
/* 133:155 */     if (symbol.equals("m")) {
/* 134:155 */       return TaskType.MAP;
/* 135:    */     }
/* 136:156 */     if (symbol.equals("r")) {
/* 137:156 */       return TaskType.REDUCE;
/* 138:    */     }
/* 139:157 */     throw new YarnRuntimeException("Unknown task symbol: " + symbol);
/* 140:    */   }
/* 141:    */   
/* 142:    */   public static TaskAttemptStateUI taskAttemptState(String attemptStateStr)
/* 143:    */   {
/* 144:161 */     return TaskAttemptStateUI.valueOf(attemptStateStr);
/* 145:    */   }
/* 146:    */   
/* 147:    */   public static TaskStateUI taskState(String taskStateStr)
/* 148:    */   {
/* 149:165 */     return TaskStateUI.valueOf(taskStateStr);
/* 150:    */   }
/* 151:    */   
/* 152:    */   private static String getMRFrameworkName(Configuration conf)
/* 153:    */   {
/* 154:171 */     String frameworkName = null;
/* 155:172 */     String framework = conf.get("mapreduce.application.framework.path", "");
/* 156:174 */     if (!framework.isEmpty())
/* 157:    */     {
/* 158:    */       URI uri;
/* 159:    */       try
/* 160:    */       {
/* 161:177 */         uri = new URI(framework);
/* 162:    */       }
/* 163:    */       catch (URISyntaxException e)
/* 164:    */       {
/* 165:179 */         throw new IllegalArgumentException("Unable to parse '" + framework + "' as a URI, check the setting for " + "mapreduce.application.framework.path", e);
/* 166:    */       }
/* 167:184 */       frameworkName = uri.getFragment();
/* 168:185 */       if (frameworkName == null) {
/* 169:186 */         frameworkName = new Path(uri).getName();
/* 170:    */       }
/* 171:    */     }
/* 172:189 */     return frameworkName;
/* 173:    */   }
/* 174:    */   
/* 175:    */   private static void setMRFrameworkClasspath(Map<String, String> environment, Configuration conf)
/* 176:    */     throws IOException
/* 177:    */   {
/* 178:195 */     if (conf.getBoolean("yarn.is.minicluster", false)) {
/* 179:196 */       addToEnvironment(environment, ApplicationConstants.Environment.CLASSPATH.name(), System.getProperty("java.class.path"), conf);
/* 180:    */     }
/* 181:199 */     boolean crossPlatform = conf.getBoolean("mapreduce.app-submission.cross-platform", false);
/* 182:    */     
/* 183:    */ 
/* 184:    */ 
/* 185:    */ 
/* 186:204 */     String frameworkName = getMRFrameworkName(conf);
/* 187:205 */     if (frameworkName == null) {
/* 188:207 */       for (String c : conf.getStrings("yarn.application.classpath", crossPlatform ? YarnConfiguration.DEFAULT_YARN_CROSS_PLATFORM_APPLICATION_CLASSPATH : YarnConfiguration.DEFAULT_YARN_APPLICATION_CLASSPATH)) {
/* 189:211 */         addToEnvironment(environment, ApplicationConstants.Environment.CLASSPATH.name(), c.trim(), conf);
/* 190:    */       }
/* 191:    */     }
/* 192:216 */     boolean foundFrameworkInClasspath = frameworkName == null;
/* 193:217 */     for (String c : conf.getStrings("mapreduce.application.classpath", crossPlatform ? StringUtils.getStrings(MRJobConfig.DEFAULT_MAPREDUCE_CROSS_PLATFORM_APPLICATION_CLASSPATH) : StringUtils.getStrings(MRJobConfig.DEFAULT_MAPREDUCE_APPLICATION_CLASSPATH)))
/* 194:    */     {
/* 195:221 */       addToEnvironment(environment, ApplicationConstants.Environment.CLASSPATH.name(), c.trim(), conf);
/* 196:223 */       if (!foundFrameworkInClasspath) {
/* 197:224 */         foundFrameworkInClasspath = c.contains(frameworkName);
/* 198:    */       }
/* 199:    */     }
/* 200:228 */     if (!foundFrameworkInClasspath) {
/* 201:229 */       throw new IllegalArgumentException("Could not locate MapReduce framework name '" + frameworkName + "' in " + "mapreduce.application.classpath");
/* 202:    */     }
/* 203:    */   }
/* 204:    */   
/* 205:    */   public static void setClasspath(Map<String, String> environment, Configuration conf)
/* 206:    */     throws IOException
/* 207:    */   {
/* 208:239 */     boolean userClassesTakesPrecedence = conf.getBoolean("mapreduce.job.user.classpath.first", false);
/* 209:    */     
/* 210:    */ 
/* 211:242 */     String classpathEnvVar = conf.getBoolean("mapreduce.job.classloader", false) ? ApplicationConstants.Environment.APP_CLASSPATH.name() : ApplicationConstants.Environment.CLASSPATH.name();
/* 212:    */     
/* 213:    */ 
/* 214:    */ 
/* 215:246 */     addToEnvironment(environment, classpathEnvVar, crossPlatformifyMREnv(conf, ApplicationConstants.Environment.PWD), conf);
/* 216:248 */     if (!userClassesTakesPrecedence) {
/* 217:249 */       setMRFrameworkClasspath(environment, conf);
/* 218:    */     }
/* 219:251 */     addToEnvironment(environment, classpathEnvVar, "job.jar/job.jar", conf);
/* 220:    */     
/* 221:    */ 
/* 222:    */ 
/* 223:255 */     addToEnvironment(environment, classpathEnvVar, "job.jar/classes/", conf);
/* 224:    */     
/* 225:    */ 
/* 226:    */ 
/* 227:259 */     addToEnvironment(environment, classpathEnvVar, "job.jar/lib/*", conf);
/* 228:    */     
/* 229:    */ 
/* 230:    */ 
/* 231:263 */     addToEnvironment(environment, classpathEnvVar, crossPlatformifyMREnv(conf, ApplicationConstants.Environment.PWD) + "/" + "*", conf);
/* 232:    */     
/* 233:    */ 
/* 234:    */ 
/* 235:    */ 
/* 236:    */ 
/* 237:269 */     addToClasspathIfNotJar(DistributedCache.getFileClassPaths(conf), DistributedCache.getCacheFiles(conf), conf, environment, classpathEnvVar);
/* 238:    */     
/* 239:    */ 
/* 240:    */ 
/* 241:273 */     addToClasspathIfNotJar(DistributedCache.getArchiveClassPaths(conf), DistributedCache.getCacheArchives(conf), conf, environment, classpathEnvVar);
/* 242:277 */     if (userClassesTakesPrecedence) {
/* 243:278 */       setMRFrameworkClasspath(environment, conf);
/* 244:    */     }
/* 245:    */   }
/* 246:    */   
/* 247:    */   private static void addToClasspathIfNotJar(Path[] paths, URI[] withLinks, Configuration conf, Map<String, String> environment, String classpathEnvVar)
/* 248:    */     throws IOException
/* 249:    */   {
/* 250:294 */     if (paths != null)
/* 251:    */     {
/* 252:295 */       HashMap<Path, String> linkLookup = new HashMap();
/* 253:296 */       if (withLinks != null) {
/* 254:297 */         for (URI u : withLinks)
/* 255:    */         {
/* 256:298 */           Path p = new Path(u);
/* 257:299 */           FileSystem remoteFS = p.getFileSystem(conf);
/* 258:300 */           p = remoteFS.resolvePath(p.makeQualified(remoteFS.getUri(), remoteFS.getWorkingDirectory()));
/* 259:    */           
/* 260:302 */           String name = null == u.getFragment() ? p.getName() : u.getFragment();
/* 261:304 */           if (!name.toLowerCase().endsWith(".jar")) {
/* 262:305 */             linkLookup.put(p, name);
/* 263:    */           }
/* 264:    */         }
/* 265:    */       }
/* 266:310 */       for (Path p : paths)
/* 267:    */       {
/* 268:311 */         FileSystem remoteFS = p.getFileSystem(conf);
/* 269:312 */         p = remoteFS.resolvePath(p.makeQualified(remoteFS.getUri(), remoteFS.getWorkingDirectory()));
/* 270:    */         
/* 271:314 */         String name = (String)linkLookup.get(p);
/* 272:315 */         if (name == null) {
/* 273:316 */           name = p.getName();
/* 274:    */         }
/* 275:318 */         if (!name.toLowerCase().endsWith(".jar")) {
/* 276:319 */           addToEnvironment(environment, classpathEnvVar, crossPlatformifyMREnv(conf, ApplicationConstants.Environment.PWD) + "/" + name, conf);
/* 277:    */         }
/* 278:    */       }
/* 279:    */     }
/* 280:    */   }
/* 281:    */   
/* 282:    */   public static void setJobClassLoader(Configuration conf)
/* 283:    */     throws IOException
/* 284:    */   {
/* 285:338 */     if (conf.getBoolean("mapreduce.job.classloader", false))
/* 286:    */     {
/* 287:339 */       String appClasspath = System.getenv(ApplicationConstants.Environment.APP_CLASSPATH.key());
/* 288:340 */       if (appClasspath == null)
/* 289:    */       {
/* 290:341 */         LOG.warn("Not using job classloader since APP_CLASSPATH is not set.");
/* 291:    */       }
/* 292:    */       else
/* 293:    */       {
/* 294:343 */         LOG.info("Using job classloader");
/* 295:344 */         if (LOG.isDebugEnabled()) {
/* 296:345 */           LOG.debug("APP_CLASSPATH=" + appClasspath);
/* 297:    */         }
/* 298:347 */         String[] systemClasses = conf.getStrings("mapreduce.job.classloader.system.classes");
/* 299:    */         
/* 300:349 */         ClassLoader jobClassLoader = createJobClassLoader(appClasspath, systemClasses);
/* 301:351 */         if (jobClassLoader != null)
/* 302:    */         {
/* 303:352 */           conf.setClassLoader(jobClassLoader);
/* 304:353 */           Thread.currentThread().setContextClassLoader(jobClassLoader);
/* 305:    */         }
/* 306:    */       }
/* 307:    */     }
/* 308:    */   }
/* 309:    */   
/* 310:    */   private static ClassLoader createJobClassLoader(String appClasspath, final String[] systemClasses)
/* 311:    */     throws IOException
/* 312:    */   {
/* 313:    */     try
/* 314:    */     {
/* 315:362 */       (ClassLoader)AccessController.doPrivileged(new PrivilegedExceptionAction()
/* 316:    */       {
/* 317:    */         public ClassLoader run()
/* 318:    */           throws MalformedURLException
/* 319:    */         {
/* 320:366 */           return new ApplicationClassLoader(this.val$appClasspath, MRApps.class.getClassLoader(), Arrays.asList(systemClasses));
/* 321:    */         }
/* 322:    */       });
/* 323:    */     }
/* 324:    */     catch (PrivilegedActionException e)
/* 325:    */     {
/* 326:371 */       Throwable t = e.getCause();
/* 327:372 */       if ((t instanceof MalformedURLException)) {
/* 328:373 */         throw ((MalformedURLException)t);
/* 329:    */       }
/* 330:375 */       throw new IOException(e);
/* 331:    */     }
/* 332:    */   }
/* 333:    */   
/* 334:    */   public static Path getStagingAreaDir(Configuration conf, String user)
/* 335:    */   {
/* 336:381 */     return new Path(conf.get("yarn.app.mapreduce.am.staging-dir", "/tmp/hadoop-yarn/staging") + "/" + user + "/" + ".staging");
/* 337:    */   }
/* 338:    */   
/* 339:    */   public static String getJobFile(Configuration conf, String user, JobID jobId)
/* 340:    */   {
/* 341:388 */     Path jobFile = new Path(getStagingAreaDir(conf, user), jobId.toString() + "/" + "job.xml");
/* 342:    */     
/* 343:390 */     return jobFile.toString();
/* 344:    */   }
/* 345:    */   
/* 346:    */   public static Path getEndJobCommitSuccessFile(Configuration conf, String user, JobId jobId)
/* 347:    */   {
/* 348:395 */     Path endCommitFile = new Path(getStagingAreaDir(conf, user), jobId.toString() + "/" + "COMMIT_SUCCESS");
/* 349:    */     
/* 350:397 */     return endCommitFile;
/* 351:    */   }
/* 352:    */   
/* 353:    */   public static Path getEndJobCommitFailureFile(Configuration conf, String user, JobId jobId)
/* 354:    */   {
/* 355:402 */     Path endCommitFile = new Path(getStagingAreaDir(conf, user), jobId.toString() + "/" + "COMMIT_FAIL");
/* 356:    */     
/* 357:404 */     return endCommitFile;
/* 358:    */   }
/* 359:    */   
/* 360:    */   public static Path getStartJobCommitFile(Configuration conf, String user, JobId jobId)
/* 361:    */   {
/* 362:409 */     Path startCommitFile = new Path(getStagingAreaDir(conf, user), jobId.toString() + "/" + "COMMIT_STARTED");
/* 363:    */     
/* 364:411 */     return startCommitFile;
/* 365:    */   }
/* 366:    */   
/* 367:    */   public static void setupDistributedCache(Configuration conf, Map<String, LocalResource> localResources)
/* 368:    */     throws IOException
/* 369:    */   {
/* 370:420 */     parseDistributedCacheArtifacts(conf, localResources, LocalResourceType.ARCHIVE, DistributedCache.getCacheArchives(conf), DistributedCache.getArchiveTimestamps(conf), getFileSizes(conf, "mapreduce.job.cache.archives.filesizes"), DistributedCache.getArchiveVisibilities(conf));
/* 371:    */     
/* 372:    */ 
/* 373:    */ 
/* 374:    */ 
/* 375:    */ 
/* 376:    */ 
/* 377:    */ 
/* 378:428 */     parseDistributedCacheArtifacts(conf, localResources, LocalResourceType.FILE, DistributedCache.getCacheFiles(conf), DistributedCache.getFileTimestamps(conf), getFileSizes(conf, "mapreduce.job.cache.files.filesizes"), DistributedCache.getFileVisibilities(conf));
/* 379:    */   }
/* 380:    */   
/* 381:    */   private static String getResourceDescription(LocalResourceType type)
/* 382:    */   {
/* 383:438 */     if ((type == LocalResourceType.ARCHIVE) || (type == LocalResourceType.PATTERN)) {
/* 384:439 */       return "cache archive (mapreduce.job.cache.archives) ";
/* 385:    */     }
/* 386:441 */     return "cache file (mapreduce.job.cache.files) ";
/* 387:    */   }
/* 388:    */   
/* 389:    */   private static String toString(URL url)
/* 390:    */   {
/* 391:445 */     StringBuffer b = new StringBuffer();
/* 392:446 */     b.append(url.getScheme()).append("://").append(url.getHost());
/* 393:447 */     if (url.getPort() >= 0) {
/* 394:448 */       b.append(":").append(url.getPort());
/* 395:    */     }
/* 396:450 */     b.append(url.getFile());
/* 397:451 */     return b.toString();
/* 398:    */   }
/* 399:    */   
/* 400:    */   private static void parseDistributedCacheArtifacts(Configuration conf, Map<String, LocalResource> localResources, LocalResourceType type, URI[] uris, long[] timestamps, long[] sizes, boolean[] visibilities)
/* 401:    */     throws IOException
/* 402:    */   {
/* 403:464 */     if (uris != null)
/* 404:    */     {
/* 405:466 */       if ((uris.length != timestamps.length) || (uris.length != sizes.length) || (uris.length != visibilities.length)) {
/* 406:468 */         throw new IllegalArgumentException("Invalid specification for distributed-cache artifacts of type " + type + " :" + " #uris=" + uris.length + " #timestamps=" + timestamps.length + " #visibilities=" + visibilities.length);
/* 407:    */       }
/* 408:476 */       for (int i = 0; i < uris.length; i++)
/* 409:    */       {
/* 410:477 */         URI u = uris[i];
/* 411:478 */         Path p = new Path(u);
/* 412:479 */         FileSystem remoteFS = p.getFileSystem(conf);
/* 413:480 */         p = remoteFS.resolvePath(p.makeQualified(remoteFS.getUri(), remoteFS.getWorkingDirectory()));
/* 414:    */         
/* 415:    */ 
/* 416:483 */         Path name = new Path(null == u.getFragment() ? p.getName() : u.getFragment());
/* 417:486 */         if (name.isAbsolute()) {
/* 418:487 */           throw new IllegalArgumentException("Resource name must be relative");
/* 419:    */         }
/* 420:489 */         String linkName = name.toUri().getPath();
/* 421:490 */         LocalResource orig = (LocalResource)localResources.get(linkName);
/* 422:491 */         URL url = ConverterUtils.getYarnUrlFromURI(p.toUri());
/* 423:493 */         if ((orig != null) && (!orig.getResource().equals(url))) {
/* 424:494 */           LOG.warn(getResourceDescription(orig.getType()) + toString(orig.getResource()) + " conflicts with " + getResourceDescription(type) + toString(url) + " This will be an error in Hadoop 2.0");
/* 425:    */         } else {
/* 426:501 */           localResources.put(linkName, LocalResource.newInstance(ConverterUtils.getYarnUrlFromURI(p.toUri()), type, visibilities[i] != 0 ? LocalResourceVisibility.PUBLIC : LocalResourceVisibility.PRIVATE, sizes[i], timestamps[i]));
/* 427:    */         }
/* 428:    */       }
/* 429:    */     }
/* 430:    */   }
/* 431:    */   
/* 432:    */   private static long[] getFileSizes(Configuration conf, String key)
/* 433:    */   {
/* 434:511 */     String[] strs = conf.getStrings(key);
/* 435:512 */     if (strs == null) {
/* 436:513 */       return null;
/* 437:    */     }
/* 438:515 */     long[] result = new long[strs.length];
/* 439:516 */     for (int i = 0; i < strs.length; i++) {
/* 440:517 */       result[i] = Long.parseLong(strs[i]);
/* 441:    */     }
/* 442:519 */     return result;
/* 443:    */   }
/* 444:    */   
/* 445:    */   public static void addLog4jSystemProperties(String logLevel, long logSize, int numBackups, List<String> vargs)
/* 446:    */   {
/* 447:531 */     vargs.add("-Dlog4j.configuration=container-log4j.properties");
/* 448:532 */     vargs.add("-Dyarn.app.container.log.dir=<LOG_DIR>");
/* 449:    */     
/* 450:534 */     vargs.add("-Dyarn.app.container.log.filesize=" + logSize);
/* 451:536 */     if ((logSize > 0L) && (numBackups > 0))
/* 452:    */     {
/* 453:538 */       vargs.add("-Dyarn.app.container.log.backups=" + numBackups);
/* 454:    */       
/* 455:540 */       vargs.add("-Dhadoop.root.logger=" + logLevel + ",CRLA");
/* 456:    */     }
/* 457:    */     else
/* 458:    */     {
/* 459:542 */       vargs.add("-Dhadoop.root.logger=" + logLevel + ",CLA");
/* 460:    */     }
/* 461:    */   }
/* 462:    */   
/* 463:    */   public static void setEnvFromInputString(Map<String, String> env, String envString, Configuration conf)
/* 464:    */   {
/* 465:548 */     String classPathSeparator = conf.getBoolean("mapreduce.app-submission.cross-platform", false) ? "<CPS>" : File.pathSeparator;
/* 466:    */     
/* 467:    */ 
/* 468:    */ 
/* 469:552 */     Apps.setEnvFromInputString(env, envString, classPathSeparator);
/* 470:    */   }
/* 471:    */   
/* 472:    */   @InterfaceAudience.Public
/* 473:    */   @InterfaceStability.Unstable
/* 474:    */   public static void addToEnvironment(Map<String, String> environment, String variable, String value, Configuration conf)
/* 475:    */   {
/* 476:559 */     String classPathSeparator = conf.getBoolean("mapreduce.app-submission.cross-platform", false) ? "<CPS>" : File.pathSeparator;
/* 477:    */     
/* 478:    */ 
/* 479:    */ 
/* 480:563 */     Apps.addToEnvironment(environment, variable, value, classPathSeparator);
/* 481:    */   }
/* 482:    */   
/* 483:    */   public static String crossPlatformifyMREnv(Configuration conf, ApplicationConstants.Environment env)
/* 484:    */   {
/* 485:567 */     boolean crossPlatform = conf.getBoolean("mapreduce.app-submission.cross-platform", false);
/* 486:    */     
/* 487:    */ 
/* 488:570 */     return crossPlatform ? env.$$() : env.$();
/* 489:    */   }
/* 490:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-common-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.v2.util.MRApps
 * JD-Core Version:    0.7.0.1
 */