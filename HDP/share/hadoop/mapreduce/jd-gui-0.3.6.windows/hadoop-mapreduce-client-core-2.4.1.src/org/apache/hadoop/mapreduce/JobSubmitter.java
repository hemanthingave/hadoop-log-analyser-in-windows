/*   1:    */ package org.apache.hadoop.mapreduce;
/*   2:    */ 
/*   3:    */ import com.google.common.base.Charsets;
/*   4:    */ import java.io.File;
/*   5:    */ import java.io.IOException;
/*   6:    */ import java.net.InetAddress;
/*   7:    */ import java.net.URI;
/*   8:    */ import java.net.URISyntaxException;
/*   9:    */ import java.net.UnknownHostException;
/*  10:    */ import java.security.NoSuchAlgorithmException;
/*  11:    */ import java.util.ArrayList;
/*  12:    */ import java.util.Arrays;
/*  13:    */ import java.util.Comparator;
/*  14:    */ import java.util.List;
/*  15:    */ import java.util.Map;
/*  16:    */ import java.util.Map.Entry;
/*  17:    */ import javax.crypto.KeyGenerator;
/*  18:    */ import javax.crypto.SecretKey;
/*  19:    */ import org.apache.commons.logging.Log;
/*  20:    */ import org.apache.commons.logging.LogFactory;
/*  21:    */ import org.apache.hadoop.classification.InterfaceAudience.Private;
/*  22:    */ import org.apache.hadoop.classification.InterfaceStability.Unstable;
/*  23:    */ import org.apache.hadoop.conf.Configuration;
/*  24:    */ import org.apache.hadoop.fs.FSDataOutputStream;
/*  25:    */ import org.apache.hadoop.fs.FileContext;
/*  26:    */ import org.apache.hadoop.fs.FileSystem;
/*  27:    */ import org.apache.hadoop.fs.FileUtil;
/*  28:    */ import org.apache.hadoop.fs.Path;
/*  29:    */ import org.apache.hadoop.fs.permission.FsPermission;
/*  30:    */ import org.apache.hadoop.io.Text;
/*  31:    */ import org.apache.hadoop.mapred.JobConf;
/*  32:    */ import org.apache.hadoop.mapred.QueueACL;
/*  33:    */ import org.apache.hadoop.mapred.QueueManager;
/*  34:    */ import org.apache.hadoop.mapreduce.filecache.ClientDistributedCacheManager;
/*  35:    */ import org.apache.hadoop.mapreduce.filecache.DistributedCache;
/*  36:    */ import org.apache.hadoop.mapreduce.protocol.ClientProtocol;
/*  37:    */ import org.apache.hadoop.mapreduce.security.TokenCache;
/*  38:    */ import org.apache.hadoop.mapreduce.split.JobSplitWriter;
/*  39:    */ import org.apache.hadoop.security.Credentials;
/*  40:    */ import org.apache.hadoop.security.UserGroupInformation;
/*  41:    */ import org.apache.hadoop.security.authorize.AccessControlList;
/*  42:    */ import org.apache.hadoop.security.token.Token;
/*  43:    */ import org.apache.hadoop.security.token.TokenIdentifier;
/*  44:    */ import org.apache.hadoop.util.ReflectionUtils;
/*  45:    */ import org.codehaus.jackson.JsonParseException;
/*  46:    */ import org.codehaus.jackson.map.JsonMappingException;
/*  47:    */ import org.codehaus.jackson.map.ObjectMapper;
/*  48:    */ 
/*  49:    */ @InterfaceAudience.Private
/*  50:    */ @InterfaceStability.Unstable
/*  51:    */ class JobSubmitter
/*  52:    */ {
/*  53: 72 */   protected static final Log LOG = LogFactory.getLog(JobSubmitter.class);
/*  54:    */   private static final String SHUFFLE_KEYGEN_ALGORITHM = "HmacSHA1";
/*  55:    */   private static final int SHUFFLE_KEY_LENGTH = 64;
/*  56:    */   private FileSystem jtFs;
/*  57:    */   private ClientProtocol submitClient;
/*  58:    */   private String submitHostName;
/*  59:    */   private String submitHostAddress;
/*  60:    */   
/*  61:    */   JobSubmitter(FileSystem submitFs, ClientProtocol submitClient)
/*  62:    */     throws IOException
/*  63:    */   {
/*  64: 82 */     this.submitClient = submitClient;
/*  65: 83 */     this.jtFs = submitFs;
/*  66:    */   }
/*  67:    */   
/*  68:    */   private boolean compareFs(FileSystem srcFs, FileSystem destFs)
/*  69:    */   {
/*  70: 89 */     URI srcUri = srcFs.getUri();
/*  71: 90 */     URI dstUri = destFs.getUri();
/*  72: 91 */     if (srcUri.getScheme() == null) {
/*  73: 92 */       return false;
/*  74:    */     }
/*  75: 94 */     if (!srcUri.getScheme().equals(dstUri.getScheme())) {
/*  76: 95 */       return false;
/*  77:    */     }
/*  78: 97 */     String srcHost = srcUri.getHost();
/*  79: 98 */     String dstHost = dstUri.getHost();
/*  80: 99 */     if ((srcHost != null) && (dstHost != null))
/*  81:    */     {
/*  82:    */       try
/*  83:    */       {
/*  84:101 */         srcHost = InetAddress.getByName(srcHost).getCanonicalHostName();
/*  85:102 */         dstHost = InetAddress.getByName(dstHost).getCanonicalHostName();
/*  86:    */       }
/*  87:    */       catch (UnknownHostException ue)
/*  88:    */       {
/*  89:104 */         return false;
/*  90:    */       }
/*  91:106 */       if (!srcHost.equals(dstHost)) {
/*  92:107 */         return false;
/*  93:    */       }
/*  94:    */     }
/*  95:    */     else
/*  96:    */     {
/*  97:109 */       if ((srcHost == null) && (dstHost != null)) {
/*  98:110 */         return false;
/*  99:    */       }
/* 100:111 */       if ((srcHost != null) && (dstHost == null)) {
/* 101:112 */         return false;
/* 102:    */       }
/* 103:    */     }
/* 104:115 */     if (srcUri.getPort() != dstUri.getPort()) {
/* 105:116 */       return false;
/* 106:    */     }
/* 107:118 */     return true;
/* 108:    */   }
/* 109:    */   
/* 110:    */   private Path copyRemoteFiles(Path parentDir, Path originalPath, Configuration conf, short replication)
/* 111:    */     throws IOException
/* 112:    */   {
/* 113:132 */     FileSystem remoteFs = null;
/* 114:133 */     remoteFs = originalPath.getFileSystem(conf);
/* 115:134 */     if (compareFs(remoteFs, this.jtFs)) {
/* 116:135 */       return originalPath;
/* 117:    */     }
/* 118:139 */     Path newPath = new Path(parentDir, originalPath.getName());
/* 119:140 */     FileUtil.copy(remoteFs, originalPath, this.jtFs, newPath, false, conf);
/* 120:141 */     this.jtFs.setReplication(newPath, replication);
/* 121:142 */     return newPath;
/* 122:    */   }
/* 123:    */   
/* 124:    */   private void copyAndConfigureFiles(Job job, Path submitJobDir, short replication)
/* 125:    */     throws IOException
/* 126:    */   {
/* 127:148 */     Configuration conf = job.getConfiguration();
/* 128:149 */     if (!conf.getBoolean("mapreduce.client.genericoptionsparser.used", false)) {
/* 129:150 */       LOG.warn("Hadoop command-line option parsing not performed. Implement the Tool interface and execute your application with ToolRunner to remedy this.");
/* 130:    */     }
/* 131:156 */     String files = conf.get("tmpfiles");
/* 132:157 */     String libjars = conf.get("tmpjars");
/* 133:158 */     String archives = conf.get("tmparchives");
/* 134:159 */     String jobJar = job.getJar();
/* 135:    */     
/* 136:    */ 
/* 137:    */ 
/* 138:    */ 
/* 139:    */ 
/* 140:    */ 
/* 141:    */ 
/* 142:    */ 
/* 143:    */ 
/* 144:    */ 
/* 145:170 */     LOG.debug("default FileSystem: " + this.jtFs.getUri());
/* 146:171 */     if (this.jtFs.exists(submitJobDir)) {
/* 147:172 */       throw new IOException("Not submitting job. Job directory " + submitJobDir + " already exists!! This is unexpected.Please check what's there in" + " that directory");
/* 148:    */     }
/* 149:176 */     submitJobDir = this.jtFs.makeQualified(submitJobDir);
/* 150:177 */     submitJobDir = new Path(submitJobDir.toUri().getPath());
/* 151:178 */     FsPermission mapredSysPerms = new FsPermission(JobSubmissionFiles.JOB_DIR_PERMISSION);
/* 152:179 */     FileSystem.mkdirs(this.jtFs, submitJobDir, mapredSysPerms);
/* 153:180 */     Path filesDir = JobSubmissionFiles.getJobDistCacheFiles(submitJobDir);
/* 154:181 */     Path archivesDir = JobSubmissionFiles.getJobDistCacheArchives(submitJobDir);
/* 155:182 */     Path libjarsDir = JobSubmissionFiles.getJobDistCacheLibjars(submitJobDir);
/* 156:186 */     if (files != null)
/* 157:    */     {
/* 158:187 */       FileSystem.mkdirs(this.jtFs, filesDir, mapredSysPerms);
/* 159:188 */       String[] fileArr = files.split(",");
/* 160:189 */       for (String tmpFile : fileArr)
/* 161:    */       {
/* 162:190 */         URI tmpURI = null;
/* 163:    */         try
/* 164:    */         {
/* 165:192 */           tmpURI = new URI(tmpFile);
/* 166:    */         }
/* 167:    */         catch (URISyntaxException e)
/* 168:    */         {
/* 169:194 */           throw new IllegalArgumentException(e);
/* 170:    */         }
/* 171:196 */         Path tmp = new Path(tmpURI);
/* 172:197 */         Path newPath = copyRemoteFiles(filesDir, tmp, conf, replication);
/* 173:    */         try
/* 174:    */         {
/* 175:199 */           URI pathURI = getPathURI(newPath, tmpURI.getFragment());
/* 176:200 */           DistributedCache.addCacheFile(pathURI, conf);
/* 177:    */         }
/* 178:    */         catch (URISyntaxException ue)
/* 179:    */         {
/* 180:203 */           throw new IOException("Failed to create uri for " + tmpFile, ue);
/* 181:    */         }
/* 182:    */       }
/* 183:    */     }
/* 184:208 */     if (libjars != null)
/* 185:    */     {
/* 186:209 */       FileSystem.mkdirs(this.jtFs, libjarsDir, mapredSysPerms);
/* 187:210 */       String[] libjarsArr = libjars.split(",");
/* 188:211 */       for (String tmpjars : libjarsArr)
/* 189:    */       {
/* 190:212 */         Path tmp = new Path(tmpjars);
/* 191:213 */         Path newPath = copyRemoteFiles(libjarsDir, tmp, conf, replication);
/* 192:214 */         DistributedCache.addFileToClassPath(new Path(newPath.toUri().getPath()), conf);
/* 193:    */       }
/* 194:    */     }
/* 195:219 */     if (archives != null)
/* 196:    */     {
/* 197:220 */       FileSystem.mkdirs(this.jtFs, archivesDir, mapredSysPerms);
/* 198:221 */       String[] archivesArr = archives.split(",");
/* 199:222 */       for (String tmpArchives : archivesArr)
/* 200:    */       {
/* 201:    */         URI tmpURI;
/* 202:    */         try
/* 203:    */         {
/* 204:225 */           tmpURI = new URI(tmpArchives);
/* 205:    */         }
/* 206:    */         catch (URISyntaxException e)
/* 207:    */         {
/* 208:227 */           throw new IllegalArgumentException(e);
/* 209:    */         }
/* 210:229 */         Path tmp = new Path(tmpURI);
/* 211:230 */         Path newPath = copyRemoteFiles(archivesDir, tmp, conf, replication);
/* 212:    */         try
/* 213:    */         {
/* 214:233 */           URI pathURI = getPathURI(newPath, tmpURI.getFragment());
/* 215:234 */           DistributedCache.addCacheArchive(pathURI, conf);
/* 216:    */         }
/* 217:    */         catch (URISyntaxException ue)
/* 218:    */         {
/* 219:237 */           throw new IOException("Failed to create uri for " + tmpArchives, ue);
/* 220:    */         }
/* 221:    */       }
/* 222:    */     }
/* 223:242 */     if (jobJar != null)
/* 224:    */     {
/* 225:244 */       if ("".equals(job.getJobName())) {
/* 226:245 */         job.setJobName(new Path(jobJar).getName());
/* 227:    */       }
/* 228:247 */       Path jobJarPath = new Path(jobJar);
/* 229:248 */       URI jobJarURI = jobJarPath.toUri();
/* 230:250 */       if ((jobJarURI.getScheme() == null) || (jobJarURI.getAuthority() == null) || (!jobJarURI.getScheme().equals(this.jtFs.getUri().getScheme())) || (!jobJarURI.getAuthority().equals(this.jtFs.getUri().getAuthority())))
/* 231:    */       {
/* 232:254 */         copyJar(jobJarPath, JobSubmissionFiles.getJobJar(submitJobDir), replication);
/* 233:    */         
/* 234:256 */         job.setJar(JobSubmissionFiles.getJobJar(submitJobDir).toString());
/* 235:    */       }
/* 236:    */     }
/* 237:    */     else
/* 238:    */     {
/* 239:259 */       LOG.warn("No job jar file set.  User classes may not be found. See Job or Job#setJar(String).");
/* 240:    */     }
/* 241:265 */     ClientDistributedCacheManager.determineTimestampsAndCacheVisibilities(conf);
/* 242:    */     
/* 243:267 */     ClientDistributedCacheManager.getDelegationTokens(conf, job.getCredentials());
/* 244:    */   }
/* 245:    */   
/* 246:    */   private URI getPathURI(Path destPath, String fragment)
/* 247:    */     throws URISyntaxException
/* 248:    */   {
/* 249:273 */     URI pathURI = destPath.toUri();
/* 250:274 */     if (pathURI.getFragment() == null) {
/* 251:275 */       if (fragment == null) {
/* 252:276 */         pathURI = new URI(pathURI.toString() + "#" + destPath.getName());
/* 253:    */       } else {
/* 254:278 */         pathURI = new URI(pathURI.toString() + "#" + fragment);
/* 255:    */       }
/* 256:    */     }
/* 257:281 */     return pathURI;
/* 258:    */   }
/* 259:    */   
/* 260:    */   private void copyJar(Path originalJarPath, Path submitJarFile, short replication)
/* 261:    */     throws IOException
/* 262:    */   {
/* 263:286 */     this.jtFs.copyFromLocalFile(originalJarPath, submitJarFile);
/* 264:287 */     this.jtFs.setReplication(submitJarFile, replication);
/* 265:288 */     this.jtFs.setPermission(submitJarFile, new FsPermission(JobSubmissionFiles.JOB_FILE_PERMISSION));
/* 266:    */   }
/* 267:    */   
/* 268:    */   private void copyAndConfigureFiles(Job job, Path jobSubmitDir)
/* 269:    */     throws IOException
/* 270:    */   {
/* 271:299 */     Configuration conf = job.getConfiguration();
/* 272:300 */     short replication = (short)conf.getInt("mapreduce.client.submit.file.replication", 10);
/* 273:301 */     copyAndConfigureFiles(job, jobSubmitDir, replication);
/* 274:304 */     if (job.getWorkingDirectory() == null) {
/* 275:305 */       job.setWorkingDirectory(this.jtFs.getWorkingDirectory());
/* 276:    */     }
/* 277:    */   }
/* 278:    */   
/* 279:    */   JobStatus submitJobInternal(Job job, Cluster cluster)
/* 280:    */     throws ClassNotFoundException, InterruptedException, IOException
/* 281:    */   {
/* 282:343 */     checkSpecs(job);
/* 283:    */     
/* 284:345 */     Configuration conf = job.getConfiguration();
/* 285:346 */     addMRFrameworkToDistributedCache(conf);
/* 286:    */     
/* 287:348 */     Path jobStagingArea = JobSubmissionFiles.getStagingDir(cluster, conf);
/* 288:    */     
/* 289:350 */     InetAddress ip = InetAddress.getLocalHost();
/* 290:351 */     if (ip != null)
/* 291:    */     {
/* 292:352 */       this.submitHostAddress = ip.getHostAddress();
/* 293:353 */       this.submitHostName = ip.getHostName();
/* 294:354 */       conf.set("mapreduce.job.submithostname", this.submitHostName);
/* 295:355 */       conf.set("mapreduce.job.submithostaddress", this.submitHostAddress);
/* 296:    */     }
/* 297:357 */     JobID jobId = this.submitClient.getNewJobID();
/* 298:358 */     job.setJobID(jobId);
/* 299:359 */     Path submitJobDir = new Path(jobStagingArea, jobId.toString());
/* 300:360 */     JobStatus status = null;
/* 301:    */     try
/* 302:    */     {
/* 303:362 */       conf.set("mapreduce.job.user.name", UserGroupInformation.getCurrentUser().getShortUserName());
/* 304:    */       
/* 305:364 */       conf.set("hadoop.http.filter.initializers", "org.apache.hadoop.yarn.server.webproxy.amfilter.AmFilterInitializer");
/* 306:    */       
/* 307:366 */       conf.set("mapreduce.job.dir", submitJobDir.toString());
/* 308:367 */       LOG.debug("Configuring job " + jobId + " with " + submitJobDir + " as the submit dir");
/* 309:    */       
/* 310:    */ 
/* 311:370 */       TokenCache.obtainTokensForNamenodes(job.getCredentials(), new Path[] { submitJobDir }, conf);
/* 312:    */       
/* 313:    */ 
/* 314:373 */       populateTokenCache(conf, job.getCredentials());
/* 315:376 */       if (TokenCache.getShuffleSecretKey(job.getCredentials()) == null)
/* 316:    */       {
/* 317:    */         KeyGenerator keyGen;
/* 318:    */         try
/* 319:    */         {
/* 320:379 */           keyGen = KeyGenerator.getInstance("HmacSHA1");
/* 321:380 */           keyGen.init(64);
/* 322:    */         }
/* 323:    */         catch (NoSuchAlgorithmException e)
/* 324:    */         {
/* 325:382 */           throw new IOException("Error generating shuffle secret key", e);
/* 326:    */         }
/* 327:384 */         SecretKey shuffleKey = keyGen.generateKey();
/* 328:385 */         TokenCache.setShuffleSecretKey(shuffleKey.getEncoded(), job.getCredentials());
/* 329:    */       }
/* 330:389 */       copyAndConfigureFiles(job, submitJobDir);
/* 331:390 */       Path submitJobFile = JobSubmissionFiles.getJobConfPath(submitJobDir);
/* 332:    */       
/* 333:    */ 
/* 334:393 */       LOG.debug("Creating splits at " + this.jtFs.makeQualified(submitJobDir));
/* 335:394 */       int maps = writeSplits(job, submitJobDir);
/* 336:395 */       conf.setInt("mapreduce.job.maps", maps);
/* 337:396 */       LOG.info("number of splits:" + maps);
/* 338:    */       
/* 339:    */ 
/* 340:    */ 
/* 341:400 */       String queue = conf.get("mapreduce.job.queuename", "default");
/* 342:    */       
/* 343:402 */       AccessControlList acl = this.submitClient.getQueueAdmins(queue);
/* 344:403 */       conf.set(QueueManager.toFullPropertyName(queue, QueueACL.ADMINISTER_JOBS.getAclName()), acl.getAclString());
/* 345:    */       
/* 346:    */ 
/* 347:    */ 
/* 348:    */ 
/* 349:    */ 
/* 350:    */ 
/* 351:410 */       TokenCache.cleanUpTokenReferral(conf);
/* 352:    */       ArrayList<String> trackingIds;
/* 353:412 */       if (conf.getBoolean("mapreduce.job.token.tracking.ids.enabled", false))
/* 354:    */       {
/* 355:416 */         trackingIds = new ArrayList();
/* 356:418 */         for (Token<? extends TokenIdentifier> t : job.getCredentials().getAllTokens()) {
/* 357:419 */           trackingIds.add(t.decodeIdentifier().getTrackingId());
/* 358:    */         }
/* 359:421 */         conf.setStrings("mapreduce.job.token.tracking.ids", (String[])trackingIds.toArray(new String[trackingIds.size()]));
/* 360:    */       }
/* 361:426 */       writeConf(conf, submitJobFile);
/* 362:    */       
/* 363:    */ 
/* 364:    */ 
/* 365:    */ 
/* 366:431 */       printTokens(jobId, job.getCredentials());
/* 367:432 */       status = this.submitClient.submitJob(jobId, submitJobDir.toString(), job.getCredentials());
/* 368:434 */       if (status != null) {
/* 369:435 */         return status;
/* 370:    */       }
/* 371:437 */       throw new IOException("Could not launch job");
/* 372:    */     }
/* 373:    */     finally
/* 374:    */     {
/* 375:440 */       if (status == null)
/* 376:    */       {
/* 377:441 */         LOG.info("Cleaning up the staging area " + submitJobDir);
/* 378:442 */         if ((this.jtFs != null) && (submitJobDir != null)) {
/* 379:443 */           this.jtFs.delete(submitJobDir, true);
/* 380:    */         }
/* 381:    */       }
/* 382:    */     }
/* 383:    */   }
/* 384:    */   
/* 385:    */   private void checkSpecs(Job job)
/* 386:    */     throws ClassNotFoundException, InterruptedException, IOException
/* 387:    */   {
/* 388:451 */     JobConf jConf = (JobConf)job.getConfiguration();
/* 389:453 */     if (jConf.getNumReduceTasks() == 0 ? jConf.getUseNewMapper() : jConf.getUseNewReducer())
/* 390:    */     {
/* 391:455 */       OutputFormat<?, ?> output = (OutputFormat)ReflectionUtils.newInstance(job.getOutputFormatClass(), job.getConfiguration());
/* 392:    */       
/* 393:    */ 
/* 394:458 */       output.checkOutputSpecs(job);
/* 395:    */     }
/* 396:    */     else
/* 397:    */     {
/* 398:460 */       jConf.getOutputFormat().checkOutputSpecs(this.jtFs, jConf);
/* 399:    */     }
/* 400:    */   }
/* 401:    */   
/* 402:    */   private void writeConf(Configuration conf, Path jobFile)
/* 403:    */     throws IOException
/* 404:    */   {
/* 405:467 */     FSDataOutputStream out = FileSystem.create(this.jtFs, jobFile, new FsPermission(JobSubmissionFiles.JOB_FILE_PERMISSION));
/* 406:    */     try
/* 407:    */     {
/* 408:471 */       conf.writeXml(out);
/* 409:    */     }
/* 410:    */     finally
/* 411:    */     {
/* 412:473 */       out.close();
/* 413:    */     }
/* 414:    */   }
/* 415:    */   
/* 416:    */   private void printTokens(JobID jobId, Credentials credentials)
/* 417:    */     throws IOException
/* 418:    */   {
/* 419:479 */     LOG.info("Submitting tokens for job: " + jobId);
/* 420:480 */     for (Token<?> token : credentials.getAllTokens()) {
/* 421:481 */       LOG.info(token);
/* 422:    */     }
/* 423:    */   }
/* 424:    */   
/* 425:    */   private <T extends InputSplit> int writeNewSplits(JobContext job, Path jobSubmitDir)
/* 426:    */     throws IOException, InterruptedException, ClassNotFoundException
/* 427:    */   {
/* 428:489 */     Configuration conf = job.getConfiguration();
/* 429:490 */     InputFormat<?, ?> input = (InputFormat)ReflectionUtils.newInstance(job.getInputFormatClass(), conf);
/* 430:    */     
/* 431:    */ 
/* 432:493 */     List<InputSplit> splits = input.getSplits(job);
/* 433:494 */     T[] array = (InputSplit[])splits.toArray(new InputSplit[splits.size()]);
/* 434:    */     
/* 435:    */ 
/* 436:    */ 
/* 437:498 */     Arrays.sort(array, new SplitComparator(null));
/* 438:499 */     JobSplitWriter.createSplitFiles(jobSubmitDir, conf, jobSubmitDir.getFileSystem(conf), array);
/* 439:    */     
/* 440:501 */     return array.length;
/* 441:    */   }
/* 442:    */   
/* 443:    */   private int writeSplits(JobContext job, Path jobSubmitDir)
/* 444:    */     throws IOException, InterruptedException, ClassNotFoundException
/* 445:    */   {
/* 446:507 */     JobConf jConf = (JobConf)job.getConfiguration();
/* 447:    */     int maps;
/* 448:    */     int maps;
/* 449:509 */     if (jConf.getUseNewMapper()) {
/* 450:510 */       maps = writeNewSplits(job, jobSubmitDir);
/* 451:    */     } else {
/* 452:512 */       maps = writeOldSplits(jConf, jobSubmitDir);
/* 453:    */     }
/* 454:514 */     return maps;
/* 455:    */   }
/* 456:    */   
/* 457:    */   private int writeOldSplits(JobConf job, Path jobSubmitDir)
/* 458:    */     throws IOException
/* 459:    */   {
/* 460:520 */     org.apache.hadoop.mapred.InputSplit[] splits = job.getInputFormat().getSplits(job, job.getNumMapTasks());
/* 461:    */     
/* 462:    */ 
/* 463:    */ 
/* 464:524 */     Arrays.sort(splits, new Comparator()
/* 465:    */     {
/* 466:    */       public int compare(org.apache.hadoop.mapred.InputSplit a, org.apache.hadoop.mapred.InputSplit b)
/* 467:    */       {
/* 468:    */         try
/* 469:    */         {
/* 470:528 */           long left = a.getLength();
/* 471:529 */           long right = b.getLength();
/* 472:530 */           if (left == right) {
/* 473:531 */             return 0;
/* 474:    */           }
/* 475:532 */           if (left < right) {
/* 476:533 */             return 1;
/* 477:    */           }
/* 478:535 */           return -1;
/* 479:    */         }
/* 480:    */         catch (IOException ie)
/* 481:    */         {
/* 482:538 */           throw new RuntimeException("Problem getting input split size", ie);
/* 483:    */         }
/* 484:    */       }
/* 485:541 */     });
/* 486:542 */     JobSplitWriter.createSplitFiles(jobSubmitDir, job, jobSubmitDir.getFileSystem(job), splits);
/* 487:    */     
/* 488:544 */     return splits.length;
/* 489:    */   }
/* 490:    */   
/* 491:    */   private static class SplitComparator
/* 492:    */     implements Comparator<InputSplit>
/* 493:    */   {
/* 494:    */     public int compare(InputSplit o1, InputSplit o2)
/* 495:    */     {
/* 496:    */       try
/* 497:    */       {
/* 498:551 */         long len1 = o1.getLength();
/* 499:552 */         long len2 = o2.getLength();
/* 500:553 */         if (len1 < len2) {
/* 501:554 */           return 1;
/* 502:    */         }
/* 503:555 */         if (len1 == len2) {
/* 504:556 */           return 0;
/* 505:    */         }
/* 506:558 */         return -1;
/* 507:    */       }
/* 508:    */       catch (IOException ie)
/* 509:    */       {
/* 510:561 */         throw new RuntimeException("exception in compare", ie);
/* 511:    */       }
/* 512:    */       catch (InterruptedException ie)
/* 513:    */       {
/* 514:563 */         throw new RuntimeException("exception in compare", ie);
/* 515:    */       }
/* 516:    */     }
/* 517:    */   }
/* 518:    */   
/* 519:    */   private void readTokensFromFiles(Configuration conf, Credentials credentials)
/* 520:    */     throws IOException
/* 521:    */   {
/* 522:572 */     String binaryTokenFilename = conf.get("mapreduce.job.credentials.binary");
/* 523:574 */     if (binaryTokenFilename != null)
/* 524:    */     {
/* 525:575 */       Credentials binary = Credentials.readTokenStorageFile(new Path("file:///" + binaryTokenFilename), conf);
/* 526:    */       
/* 527:577 */       credentials.addAll(binary);
/* 528:    */     }
/* 529:580 */     String tokensFileName = conf.get("mapreduce.job.credentials.json");
/* 530:581 */     if (tokensFileName != null)
/* 531:    */     {
/* 532:582 */       LOG.info("loading user's secret keys from " + tokensFileName);
/* 533:583 */       String localFileName = new Path(tokensFileName).toUri().getPath();
/* 534:    */       
/* 535:585 */       boolean json_error = false;
/* 536:    */       try
/* 537:    */       {
/* 538:588 */         ObjectMapper mapper = new ObjectMapper();
/* 539:589 */         Map<String, String> nm = (Map)mapper.readValue(new File(localFileName), Map.class);
/* 540:592 */         for (Map.Entry<String, String> ent : nm.entrySet()) {
/* 541:593 */           credentials.addSecretKey(new Text((String)ent.getKey()), ((String)ent.getValue()).getBytes(Charsets.UTF_8));
/* 542:    */         }
/* 543:    */       }
/* 544:    */       catch (JsonMappingException e)
/* 545:    */       {
/* 546:597 */         json_error = true;
/* 547:    */       }
/* 548:    */       catch (JsonParseException e)
/* 549:    */       {
/* 550:599 */         json_error = true;
/* 551:    */       }
/* 552:601 */       if (json_error) {
/* 553:602 */         LOG.warn("couldn't parse Token Cache JSON file with user secret keys");
/* 554:    */       }
/* 555:    */     }
/* 556:    */   }
/* 557:    */   
/* 558:    */   private void populateTokenCache(Configuration conf, Credentials credentials)
/* 559:    */     throws IOException
/* 560:    */   {
/* 561:609 */     readTokensFromFiles(conf, credentials);
/* 562:    */     
/* 563:611 */     String[] nameNodes = conf.getStrings("mapreduce.job.hdfs-servers");
/* 564:612 */     LOG.debug("adding the following namenodes' delegation tokens:" + Arrays.toString(nameNodes));
/* 565:614 */     if (nameNodes != null)
/* 566:    */     {
/* 567:615 */       Path[] ps = new Path[nameNodes.length];
/* 568:616 */       for (int i = 0; i < nameNodes.length; i++) {
/* 569:617 */         ps[i] = new Path(nameNodes[i]);
/* 570:    */       }
/* 571:619 */       TokenCache.obtainTokensForNamenodes(credentials, ps, conf);
/* 572:    */     }
/* 573:    */   }
/* 574:    */   
/* 575:    */   private static void addMRFrameworkToDistributedCache(Configuration conf)
/* 576:    */     throws IOException
/* 577:    */   {
/* 578:626 */     String framework = conf.get("mapreduce.application.framework.path", "");
/* 579:628 */     if (!framework.isEmpty())
/* 580:    */     {
/* 581:    */       try
/* 582:    */       {
/* 583:631 */         uri = new URI(framework);
/* 584:    */       }
/* 585:    */       catch (URISyntaxException e)
/* 586:    */       {
/* 587:633 */         throw new IllegalArgumentException("Unable to parse '" + framework + "' as a URI, check the setting for " + "mapreduce.application.framework.path", e);
/* 588:    */       }
/* 589:638 */       String linkedName = uri.getFragment();
/* 590:    */       
/* 591:    */ 
/* 592:    */ 
/* 593:    */ 
/* 594:643 */       FileSystem fs = FileSystem.get(conf);
/* 595:644 */       Path frameworkPath = fs.makeQualified(new Path(uri.getScheme(), uri.getAuthority(), uri.getPath()));
/* 596:    */       
/* 597:646 */       FileContext fc = FileContext.getFileContext(frameworkPath.toUri(), conf);
/* 598:647 */       frameworkPath = fc.resolvePath(frameworkPath);
/* 599:648 */       URI uri = frameworkPath.toUri();
/* 600:    */       try
/* 601:    */       {
/* 602:650 */         uri = new URI(uri.getScheme(), uri.getAuthority(), uri.getPath(), null, linkedName);
/* 603:    */       }
/* 604:    */       catch (URISyntaxException e)
/* 605:    */       {
/* 606:653 */         throw new IllegalArgumentException(e);
/* 607:    */       }
/* 608:656 */       DistributedCache.addCacheArchive(uri, conf);
/* 609:    */     }
/* 610:    */   }
/* 611:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.JobSubmitter
 * JD-Core Version:    0.7.0.1
 */