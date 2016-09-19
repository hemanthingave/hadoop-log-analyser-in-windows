/*   1:    */ package org.apache.hadoop.mapreduce.task.reduce;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.net.InetAddress;
/*   5:    */ import java.net.URI;
/*   6:    */ import java.net.UnknownHostException;
/*   7:    */ import java.text.DecimalFormat;
/*   8:    */ import java.util.ArrayList;
/*   9:    */ import java.util.HashMap;
/*  10:    */ import java.util.HashSet;
/*  11:    */ import java.util.Iterator;
/*  12:    */ import java.util.List;
/*  13:    */ import java.util.Map;
/*  14:    */ import java.util.Random;
/*  15:    */ import java.util.Set;
/*  16:    */ import java.util.concurrent.DelayQueue;
/*  17:    */ import java.util.concurrent.Delayed;
/*  18:    */ import java.util.concurrent.TimeUnit;
/*  19:    */ import org.apache.commons.logging.Log;
/*  20:    */ import org.apache.commons.logging.LogFactory;
/*  21:    */ import org.apache.hadoop.classification.InterfaceAudience.Private;
/*  22:    */ import org.apache.hadoop.classification.InterfaceStability.Unstable;
/*  23:    */ import org.apache.hadoop.io.IntWritable;
/*  24:    */ import org.apache.hadoop.mapred.Counters.Counter;
/*  25:    */ import org.apache.hadoop.mapred.JobConf;
/*  26:    */ import org.apache.hadoop.mapred.TaskCompletionEvent;
/*  27:    */ import org.apache.hadoop.mapred.TaskStatus;
/*  28:    */ import org.apache.hadoop.mapreduce.TaskID;
/*  29:    */ import org.apache.hadoop.util.Progress;
/*  30:    */ 
/*  31:    */ @InterfaceAudience.Private
/*  32:    */ @InterfaceStability.Unstable
/*  33:    */ public class ShuffleSchedulerImpl<K, V>
/*  34:    */   implements ShuffleScheduler<K, V>
/*  35:    */ {
/*  36: 56 */   static ThreadLocal<Long> shuffleStart = new ThreadLocal()
/*  37:    */   {
/*  38:    */     protected Long initialValue()
/*  39:    */     {
/*  40: 58 */       return Long.valueOf(0L);
/*  41:    */     }
/*  42:    */   };
/*  43: 62 */   private static final Log LOG = LogFactory.getLog(ShuffleSchedulerImpl.class);
/*  44:    */   private static final int MAX_MAPS_AT_ONCE = 20;
/*  45:    */   private static final long INITIAL_PENALTY = 10000L;
/*  46:    */   private static final float PENALTY_GROWTH_RATE = 1.3F;
/*  47:    */   private static final int REPORT_FAILURE_LIMIT = 10;
/*  48:    */   private final boolean[] finishedMaps;
/*  49:    */   private final int totalMaps;
/*  50:    */   private int remainingMaps;
/*  51: 72 */   private Map<String, MapHost> mapLocations = new HashMap();
/*  52: 73 */   private Set<MapHost> pendingHosts = new HashSet();
/*  53: 74 */   private Set<org.apache.hadoop.mapreduce.TaskAttemptID> obsoleteMaps = new HashSet();
/*  54:    */   private final org.apache.hadoop.mapreduce.TaskAttemptID reduceId;
/*  55: 77 */   private final Random random = new Random();
/*  56: 78 */   private final DelayQueue<Penalty> penalties = new DelayQueue();
/*  57: 79 */   private final ShuffleSchedulerImpl<K, V>.Referee referee = new Referee();
/*  58: 80 */   private final Map<org.apache.hadoop.mapreduce.TaskAttemptID, IntWritable> failureCounts = new HashMap();
/*  59: 82 */   private final Map<String, IntWritable> hostFailures = new HashMap();
/*  60:    */   private final TaskStatus status;
/*  61:    */   private final ExceptionReporter reporter;
/*  62:    */   private final int abortFailureLimit;
/*  63:    */   private final Progress progress;
/*  64:    */   private final Counters.Counter shuffledMapsCounter;
/*  65:    */   private final Counters.Counter reduceShuffleBytes;
/*  66:    */   private final Counters.Counter failedShuffleCounter;
/*  67:    */   private final long startTime;
/*  68:    */   private long lastProgressTime;
/*  69: 95 */   private volatile int maxMapRuntime = 0;
/*  70:    */   private final int maxFailedUniqueFetches;
/*  71:    */   private final int maxFetchFailuresBeforeReporting;
/*  72: 99 */   private long totalBytesShuffledTillNow = 0L;
/*  73:100 */   private final DecimalFormat mbpsFormat = new DecimalFormat("0.00");
/*  74:    */   private final boolean reportReadErrorImmediately;
/*  75:103 */   private long maxDelay = 60000L;
/*  76:    */   
/*  77:    */   public ShuffleSchedulerImpl(JobConf job, TaskStatus status, org.apache.hadoop.mapreduce.TaskAttemptID reduceId, ExceptionReporter reporter, Progress progress, Counters.Counter shuffledMapsCounter, Counters.Counter reduceShuffleBytes, Counters.Counter failedShuffleCounter)
/*  78:    */   {
/*  79:112 */     this.totalMaps = job.getNumMapTasks();
/*  80:113 */     this.abortFailureLimit = Math.max(30, this.totalMaps / 10);
/*  81:    */     
/*  82:115 */     this.remainingMaps = this.totalMaps;
/*  83:116 */     this.finishedMaps = new boolean[this.remainingMaps];
/*  84:117 */     this.reporter = reporter;
/*  85:118 */     this.status = status;
/*  86:119 */     this.reduceId = reduceId;
/*  87:120 */     this.progress = progress;
/*  88:121 */     this.shuffledMapsCounter = shuffledMapsCounter;
/*  89:122 */     this.reduceShuffleBytes = reduceShuffleBytes;
/*  90:123 */     this.failedShuffleCounter = failedShuffleCounter;
/*  91:124 */     this.startTime = System.currentTimeMillis();
/*  92:125 */     this.lastProgressTime = this.startTime;
/*  93:126 */     this.referee.start();
/*  94:127 */     this.maxFailedUniqueFetches = Math.min(this.totalMaps, 5);
/*  95:128 */     this.maxFetchFailuresBeforeReporting = job.getInt("mapreduce.reduce.shuffle.maxfetchfailures", 10);
/*  96:    */     
/*  97:130 */     this.reportReadErrorImmediately = job.getBoolean("mapreduce.reduce.shuffle.notify.readerror", true);
/*  98:    */     
/*  99:    */ 
/* 100:133 */     this.maxDelay = job.getLong("mapreduce.reduce.shuffle.retry-delay.max.ms", 60000L);
/* 101:    */   }
/* 102:    */   
/* 103:    */   public void resolve(TaskCompletionEvent event)
/* 104:    */   {
/* 105:139 */     switch (2.$SwitchMap$org$apache$hadoop$mapred$TaskCompletionEvent$Status[event.getTaskStatus().ordinal()])
/* 106:    */     {
/* 107:    */     case 1: 
/* 108:141 */       URI u = getBaseURI(this.reduceId, event.getTaskTrackerHttp());
/* 109:142 */       addKnownMapOutput(u.getHost() + ":" + u.getPort(), u.toString(), event.getTaskAttemptId());
/* 110:    */       
/* 111:    */ 
/* 112:145 */       this.maxMapRuntime = Math.max(this.maxMapRuntime, event.getTaskRunTime());
/* 113:146 */       break;
/* 114:    */     case 2: 
/* 115:    */     case 3: 
/* 116:    */     case 4: 
/* 117:150 */       obsoleteMapOutput(event.getTaskAttemptId());
/* 118:151 */       LOG.info("Ignoring obsolete output of " + event.getTaskStatus() + " map-task: '" + event.getTaskAttemptId() + "'");
/* 119:    */       
/* 120:153 */       break;
/* 121:    */     case 5: 
/* 122:155 */       tipFailed(event.getTaskAttemptId().getTaskID());
/* 123:156 */       LOG.info("Ignoring output of failed map TIP: '" + event.getTaskAttemptId() + "'");
/* 124:    */     }
/* 125:    */   }
/* 126:    */   
/* 127:    */   static URI getBaseURI(org.apache.hadoop.mapreduce.TaskAttemptID reduceId, String url)
/* 128:    */   {
/* 129:163 */     StringBuffer baseUrl = new StringBuffer(url);
/* 130:164 */     if (!url.endsWith("/")) {
/* 131:165 */       baseUrl.append("/");
/* 132:    */     }
/* 133:167 */     baseUrl.append("mapOutput?job=");
/* 134:168 */     baseUrl.append(reduceId.getJobID());
/* 135:169 */     baseUrl.append("&reduce=");
/* 136:170 */     baseUrl.append(reduceId.getTaskID().getId());
/* 137:171 */     baseUrl.append("&map=");
/* 138:172 */     URI u = URI.create(baseUrl.toString());
/* 139:173 */     return u;
/* 140:    */   }
/* 141:    */   
/* 142:    */   public synchronized void copySucceeded(org.apache.hadoop.mapreduce.TaskAttemptID mapId, MapHost host, long bytes, long millis, MapOutput<K, V> output)
/* 143:    */     throws IOException
/* 144:    */   {
/* 145:182 */     this.failureCounts.remove(mapId);
/* 146:183 */     this.hostFailures.remove(host.getHostName());
/* 147:184 */     int mapIndex = mapId.getTaskID().getId();
/* 148:186 */     if (this.finishedMaps[mapIndex] == 0)
/* 149:    */     {
/* 150:187 */       output.commit();
/* 151:188 */       this.finishedMaps[mapIndex] = true;
/* 152:189 */       this.shuffledMapsCounter.increment(1L);
/* 153:190 */       if (--this.remainingMaps == 0) {
/* 154:191 */         notifyAll();
/* 155:    */       }
/* 156:195 */       this.totalBytesShuffledTillNow += bytes;
/* 157:196 */       updateStatus();
/* 158:197 */       this.reduceShuffleBytes.increment(bytes);
/* 159:198 */       this.lastProgressTime = System.currentTimeMillis();
/* 160:199 */       LOG.debug("map " + mapId + " done " + this.status.getStateString());
/* 161:    */     }
/* 162:    */   }
/* 163:    */   
/* 164:    */   private void updateStatus()
/* 165:    */   {
/* 166:204 */     float mbs = (float)this.totalBytesShuffledTillNow / 1048576.0F;
/* 167:205 */     int mapsDone = this.totalMaps - this.remainingMaps;
/* 168:206 */     long secsSinceStart = (System.currentTimeMillis() - this.startTime) / 1000L + 1L;
/* 169:    */     
/* 170:208 */     float transferRate = mbs / (float)secsSinceStart;
/* 171:209 */     this.progress.set(mapsDone / this.totalMaps);
/* 172:210 */     String statusString = mapsDone + " / " + this.totalMaps + " copied.";
/* 173:211 */     this.status.setStateString(statusString);
/* 174:    */     
/* 175:213 */     this.progress.setStatus("copy(" + mapsDone + " of " + this.totalMaps + " at " + this.mbpsFormat.format(transferRate) + " MB/s)");
/* 176:    */   }
/* 177:    */   
/* 178:    */   public synchronized void copyFailed(org.apache.hadoop.mapreduce.TaskAttemptID mapId, MapHost host, boolean readError, boolean connectExcpt)
/* 179:    */   {
/* 180:219 */     host.penalize();
/* 181:220 */     int failures = 1;
/* 182:221 */     if (this.failureCounts.containsKey(mapId))
/* 183:    */     {
/* 184:222 */       IntWritable x = (IntWritable)this.failureCounts.get(mapId);
/* 185:223 */       x.set(x.get() + 1);
/* 186:224 */       failures = x.get();
/* 187:    */     }
/* 188:    */     else
/* 189:    */     {
/* 190:226 */       this.failureCounts.put(mapId, new IntWritable(1));
/* 191:    */     }
/* 192:228 */     String hostname = host.getHostName();
/* 193:229 */     if (this.hostFailures.containsKey(hostname))
/* 194:    */     {
/* 195:230 */       IntWritable x = (IntWritable)this.hostFailures.get(hostname);
/* 196:231 */       x.set(x.get() + 1);
/* 197:    */     }
/* 198:    */     else
/* 199:    */     {
/* 200:233 */       this.hostFailures.put(hostname, new IntWritable(1));
/* 201:    */     }
/* 202:235 */     if (failures >= this.abortFailureLimit) {
/* 203:    */       try
/* 204:    */       {
/* 205:237 */         throw new IOException(failures + " failures downloading " + mapId);
/* 206:    */       }
/* 207:    */       catch (IOException ie)
/* 208:    */       {
/* 209:239 */         this.reporter.reportException(ie);
/* 210:    */       }
/* 211:    */     }
/* 212:243 */     checkAndInformJobTracker(failures, mapId, readError, connectExcpt);
/* 213:    */     
/* 214:245 */     checkReducerHealth();
/* 215:    */     
/* 216:247 */     long delay = (10000.0D * Math.pow(1.299999952316284D, failures));
/* 217:249 */     if (delay > this.maxDelay) {
/* 218:250 */       delay = this.maxDelay;
/* 219:    */     }
/* 220:253 */     this.penalties.add(new Penalty(host, delay));
/* 221:    */     
/* 222:255 */     this.failedShuffleCounter.increment(1L);
/* 223:    */   }
/* 224:    */   
/* 225:    */   public void reportLocalError(IOException ioe)
/* 226:    */   {
/* 227:    */     try
/* 228:    */     {
/* 229:260 */       LOG.error("Shuffle failed : local error on this node: " + InetAddress.getLocalHost());
/* 230:    */     }
/* 231:    */     catch (UnknownHostException e)
/* 232:    */     {
/* 233:263 */       LOG.error("Shuffle failed : local error on this node");
/* 234:    */     }
/* 235:265 */     this.reporter.reportException(ioe);
/* 236:    */   }
/* 237:    */   
/* 238:    */   private void checkAndInformJobTracker(int failures, org.apache.hadoop.mapreduce.TaskAttemptID mapId, boolean readError, boolean connectExcpt)
/* 239:    */   {
/* 240:274 */     if ((connectExcpt) || ((this.reportReadErrorImmediately) && (readError)) || (failures % this.maxFetchFailuresBeforeReporting == 0))
/* 241:    */     {
/* 242:276 */       LOG.info("Reporting fetch failure for " + mapId + " to jobtracker.");
/* 243:277 */       this.status.addFetchFailedMap((org.apache.hadoop.mapred.TaskAttemptID)mapId);
/* 244:    */     }
/* 245:    */   }
/* 246:    */   
/* 247:    */   private void checkReducerHealth()
/* 248:    */   {
/* 249:282 */     float MAX_ALLOWED_FAILED_FETCH_ATTEMPT_PERCENT = 0.5F;
/* 250:283 */     float MIN_REQUIRED_PROGRESS_PERCENT = 0.5F;
/* 251:284 */     float MAX_ALLOWED_STALL_TIME_PERCENT = 0.5F;
/* 252:    */     
/* 253:286 */     long totalFailures = this.failedShuffleCounter.getValue();
/* 254:287 */     int doneMaps = this.totalMaps - this.remainingMaps;
/* 255:    */     
/* 256:289 */     boolean reducerHealthy = (float)totalFailures / (float)(totalFailures + doneMaps) < 0.5F;
/* 257:    */     
/* 258:    */ 
/* 259:    */ 
/* 260:    */ 
/* 261:294 */     boolean reducerProgressedEnough = doneMaps / this.totalMaps >= 0.5F;
/* 262:    */     
/* 263:    */ 
/* 264:    */ 
/* 265:    */ 
/* 266:    */ 
/* 267:300 */     int stallDuration = (int)(System.currentTimeMillis() - this.lastProgressTime);
/* 268:    */     
/* 269:    */ 
/* 270:    */ 
/* 271:304 */     int shuffleProgressDuration = (int)(this.lastProgressTime - this.startTime);
/* 272:    */     
/* 273:    */ 
/* 274:    */ 
/* 275:308 */     int minShuffleRunDuration = Math.max(shuffleProgressDuration, this.maxMapRuntime);
/* 276:    */     
/* 277:    */ 
/* 278:311 */     boolean reducerStalled = stallDuration / minShuffleRunDuration >= 0.5F;
/* 279:316 */     if (((this.failureCounts.size() >= this.maxFailedUniqueFetches) || (this.failureCounts.size() == this.totalMaps - doneMaps)) && (!reducerHealthy) && ((!reducerProgressedEnough) || (reducerStalled)))
/* 280:    */     {
/* 281:320 */       LOG.fatal("Shuffle failed with too many fetch failures and insufficient progress!");
/* 282:    */       
/* 283:322 */       String errorMsg = "Exceeded MAX_FAILED_UNIQUE_FETCHES; bailing-out.";
/* 284:323 */       this.reporter.reportException(new IOException(errorMsg));
/* 285:    */     }
/* 286:    */   }
/* 287:    */   
/* 288:    */   public synchronized void tipFailed(TaskID taskId)
/* 289:    */   {
/* 290:329 */     if (this.finishedMaps[taskId.getId()] == 0)
/* 291:    */     {
/* 292:330 */       this.finishedMaps[taskId.getId()] = true;
/* 293:331 */       if (--this.remainingMaps == 0) {
/* 294:332 */         notifyAll();
/* 295:    */       }
/* 296:334 */       updateStatus();
/* 297:    */     }
/* 298:    */   }
/* 299:    */   
/* 300:    */   public synchronized void addKnownMapOutput(String hostName, String hostUrl, org.apache.hadoop.mapreduce.TaskAttemptID mapId)
/* 301:    */   {
/* 302:341 */     MapHost host = (MapHost)this.mapLocations.get(hostName);
/* 303:342 */     if (host == null)
/* 304:    */     {
/* 305:343 */       host = new MapHost(hostName, hostUrl);
/* 306:344 */       this.mapLocations.put(hostName, host);
/* 307:    */     }
/* 308:346 */     host.addKnownMap(mapId);
/* 309:349 */     if (host.getState() == MapHost.State.PENDING)
/* 310:    */     {
/* 311:350 */       this.pendingHosts.add(host);
/* 312:351 */       notifyAll();
/* 313:    */     }
/* 314:    */   }
/* 315:    */   
/* 316:    */   public synchronized void obsoleteMapOutput(org.apache.hadoop.mapreduce.TaskAttemptID mapId)
/* 317:    */   {
/* 318:357 */     this.obsoleteMaps.add(mapId);
/* 319:    */   }
/* 320:    */   
/* 321:    */   public synchronized void putBackKnownMapOutput(MapHost host, org.apache.hadoop.mapreduce.TaskAttemptID mapId)
/* 322:    */   {
/* 323:362 */     host.addKnownMap(mapId);
/* 324:    */   }
/* 325:    */   
/* 326:    */   public synchronized MapHost getHost()
/* 327:    */     throws InterruptedException
/* 328:    */   {
/* 329:367 */     while (this.pendingHosts.isEmpty()) {
/* 330:368 */       wait();
/* 331:    */     }
/* 332:371 */     MapHost host = null;
/* 333:372 */     Iterator<MapHost> iter = this.pendingHosts.iterator();
/* 334:373 */     int numToPick = this.random.nextInt(this.pendingHosts.size());
/* 335:374 */     for (int i = 0; i <= numToPick; i++) {
/* 336:375 */       host = (MapHost)iter.next();
/* 337:    */     }
/* 338:378 */     this.pendingHosts.remove(host);
/* 339:379 */     host.markBusy();
/* 340:    */     
/* 341:381 */     LOG.info("Assigning " + host + " with " + host.getNumKnownMapOutputs() + " to " + Thread.currentThread().getName());
/* 342:    */     
/* 343:383 */     shuffleStart.set(Long.valueOf(System.currentTimeMillis()));
/* 344:    */     
/* 345:385 */     return host;
/* 346:    */   }
/* 347:    */   
/* 348:    */   public synchronized List<org.apache.hadoop.mapreduce.TaskAttemptID> getMapsForHost(MapHost host)
/* 349:    */   {
/* 350:389 */     List<org.apache.hadoop.mapreduce.TaskAttemptID> list = host.getAndClearKnownMaps();
/* 351:390 */     Iterator<org.apache.hadoop.mapreduce.TaskAttemptID> itr = list.iterator();
/* 352:391 */     List<org.apache.hadoop.mapreduce.TaskAttemptID> result = new ArrayList();
/* 353:392 */     int includedMaps = 0;
/* 354:393 */     int totalSize = list.size();
/* 355:395 */     for (; itr.hasNext(); includedMaps >= 20)
/* 356:    */     {
/* 357:396 */       org.apache.hadoop.mapreduce.TaskAttemptID id = (org.apache.hadoop.mapreduce.TaskAttemptID)itr.next();
/* 358:397 */       if ((!this.obsoleteMaps.contains(id)) && (this.finishedMaps[id.getTaskID().getId()] == 0))
/* 359:    */       {
/* 360:398 */         result.add(id);
/* 361:399 */         includedMaps++;
/* 362:    */       }
/* 363:    */     }
/* 364:405 */     while (itr.hasNext())
/* 365:    */     {
/* 366:406 */       org.apache.hadoop.mapreduce.TaskAttemptID id = (org.apache.hadoop.mapreduce.TaskAttemptID)itr.next();
/* 367:407 */       if ((!this.obsoleteMaps.contains(id)) && (this.finishedMaps[id.getTaskID().getId()] == 0)) {
/* 368:408 */         host.addKnownMap(id);
/* 369:    */       }
/* 370:    */     }
/* 371:411 */     LOG.info("assigned " + includedMaps + " of " + totalSize + " to " + host + " to " + Thread.currentThread().getName());
/* 372:    */     
/* 373:413 */     return result;
/* 374:    */   }
/* 375:    */   
/* 376:    */   public synchronized void freeHost(MapHost host)
/* 377:    */   {
/* 378:417 */     if ((host.getState() != MapHost.State.PENALIZED) && 
/* 379:418 */       (host.markAvailable() == MapHost.State.PENDING))
/* 380:    */     {
/* 381:419 */       this.pendingHosts.add(host);
/* 382:420 */       notifyAll();
/* 383:    */     }
/* 384:423 */     LOG.info(host + " freed by " + Thread.currentThread().getName() + " in " + (System.currentTimeMillis() - ((Long)shuffleStart.get()).longValue()) + "ms");
/* 385:    */   }
/* 386:    */   
/* 387:    */   public synchronized void resetKnownMaps()
/* 388:    */   {
/* 389:428 */     this.mapLocations.clear();
/* 390:429 */     this.obsoleteMaps.clear();
/* 391:430 */     this.pendingHosts.clear();
/* 392:    */   }
/* 393:    */   
/* 394:    */   public synchronized boolean waitUntilDone(int millis)
/* 395:    */     throws InterruptedException
/* 396:    */   {
/* 397:442 */     if (this.remainingMaps > 0)
/* 398:    */     {
/* 399:443 */       wait(millis);
/* 400:444 */       return this.remainingMaps == 0;
/* 401:    */     }
/* 402:446 */     return true;
/* 403:    */   }
/* 404:    */   
/* 405:    */   private static class Penalty
/* 406:    */     implements Delayed
/* 407:    */   {
/* 408:    */     MapHost host;
/* 409:    */     private long endTime;
/* 410:    */     
/* 411:    */     Penalty(MapHost host, long delay)
/* 412:    */     {
/* 413:457 */       this.host = host;
/* 414:458 */       this.endTime = (System.currentTimeMillis() + delay);
/* 415:    */     }
/* 416:    */     
/* 417:    */     public long getDelay(TimeUnit unit)
/* 418:    */     {
/* 419:463 */       long remainingTime = this.endTime - System.currentTimeMillis();
/* 420:464 */       return unit.convert(remainingTime, TimeUnit.MILLISECONDS);
/* 421:    */     }
/* 422:    */     
/* 423:    */     public int compareTo(Delayed o)
/* 424:    */     {
/* 425:469 */       long other = ((Penalty)o).endTime;
/* 426:470 */       return this.endTime < other ? -1 : this.endTime == other ? 0 : 1;
/* 427:    */     }
/* 428:    */   }
/* 429:    */   
/* 430:    */   private class Referee
/* 431:    */     extends Thread
/* 432:    */   {
/* 433:    */     public Referee()
/* 434:    */     {
/* 435:480 */       setName("ShufflePenaltyReferee");
/* 436:481 */       setDaemon(true);
/* 437:    */     }
/* 438:    */     
/* 439:    */     public void run()
/* 440:    */     {
/* 441:    */       try
/* 442:    */       {
/* 443:    */         for (;;)
/* 444:    */         {
/* 445:488 */           MapHost host = ((ShuffleSchedulerImpl.Penalty)ShuffleSchedulerImpl.this.penalties.take()).host;
/* 446:489 */           synchronized (ShuffleSchedulerImpl.this)
/* 447:    */           {
/* 448:490 */             if (host.markAvailable() == MapHost.State.PENDING)
/* 449:    */             {
/* 450:491 */               ShuffleSchedulerImpl.this.pendingHosts.add(host);
/* 451:492 */               ShuffleSchedulerImpl.this.notifyAll();
/* 452:    */             }
/* 453:    */           }
/* 454:    */         }
/* 455:    */       }
/* 456:    */       catch (InterruptedException ie) {}catch (Throwable t)
/* 457:    */       {
/* 458:499 */         ShuffleSchedulerImpl.this.reporter.reportException(t);
/* 459:    */       }
/* 460:    */     }
/* 461:    */   }
/* 462:    */   
/* 463:    */   public void close()
/* 464:    */     throws InterruptedException
/* 465:    */   {
/* 466:506 */     this.referee.interrupt();
/* 467:507 */     this.referee.join();
/* 468:    */   }
/* 469:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.task.reduce.ShuffleSchedulerImpl
 * JD-Core Version:    0.7.0.1
 */