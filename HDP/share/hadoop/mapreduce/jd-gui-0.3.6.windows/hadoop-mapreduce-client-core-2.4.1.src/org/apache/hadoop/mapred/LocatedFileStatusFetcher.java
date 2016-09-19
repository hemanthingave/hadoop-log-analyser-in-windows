/*   1:    */ package org.apache.hadoop.mapred;
/*   2:    */ 
/*   3:    */ import com.google.common.collect.Iterables;
/*   4:    */ import com.google.common.util.concurrent.FutureCallback;
/*   5:    */ import com.google.common.util.concurrent.Futures;
/*   6:    */ import com.google.common.util.concurrent.ListenableFuture;
/*   7:    */ import com.google.common.util.concurrent.ListeningExecutorService;
/*   8:    */ import com.google.common.util.concurrent.MoreExecutors;
/*   9:    */ import com.google.common.util.concurrent.ThreadFactoryBuilder;
/*  10:    */ import java.io.IOException;
/*  11:    */ import java.util.LinkedList;
/*  12:    */ import java.util.List;
/*  13:    */ import java.util.concurrent.BlockingQueue;
/*  14:    */ import java.util.concurrent.Callable;
/*  15:    */ import java.util.concurrent.ExecutorService;
/*  16:    */ import java.util.concurrent.Executors;
/*  17:    */ import java.util.concurrent.LinkedBlockingQueue;
/*  18:    */ import java.util.concurrent.atomic.AtomicInteger;
/*  19:    */ import java.util.concurrent.locks.Condition;
/*  20:    */ import java.util.concurrent.locks.ReentrantLock;
/*  21:    */ import org.apache.hadoop.classification.InterfaceAudience.Private;
/*  22:    */ import org.apache.hadoop.conf.Configuration;
/*  23:    */ import org.apache.hadoop.fs.FileStatus;
/*  24:    */ import org.apache.hadoop.fs.FileSystem;
/*  25:    */ import org.apache.hadoop.fs.LocatedFileStatus;
/*  26:    */ import org.apache.hadoop.fs.Path;
/*  27:    */ import org.apache.hadoop.fs.PathFilter;
/*  28:    */ import org.apache.hadoop.fs.RemoteIterator;
/*  29:    */ 
/*  30:    */ @InterfaceAudience.Private
/*  31:    */ public class LocatedFileStatusFetcher
/*  32:    */ {
/*  33:    */   private final Path[] inputDirs;
/*  34:    */   private final PathFilter inputFilter;
/*  35:    */   private final Configuration conf;
/*  36:    */   private final boolean recursive;
/*  37:    */   private final boolean newApi;
/*  38:    */   private final ExecutorService rawExec;
/*  39:    */   private final ListeningExecutorService exec;
/*  40:    */   private final BlockingQueue<List<FileStatus>> resultQueue;
/*  41: 67 */   private final List<IOException> invalidInputErrors = new LinkedList();
/*  42: 69 */   private final ProcessInitialInputPathCallback processInitialInputPathCallback = new ProcessInitialInputPathCallback(null);
/*  43: 71 */   private final ProcessInputDirCallback processInputDirCallback = new ProcessInputDirCallback(null);
/*  44: 74 */   private final AtomicInteger runningTasks = new AtomicInteger(0);
/*  45: 76 */   private final ReentrantLock lock = new ReentrantLock();
/*  46: 77 */   private final Condition condition = this.lock.newCondition();
/*  47:    */   private volatile Throwable unknownError;
/*  48:    */   
/*  49:    */   public LocatedFileStatusFetcher(Configuration conf, Path[] dirs, boolean recursive, PathFilter inputFilter, boolean newApi)
/*  50:    */     throws InterruptedException, IOException
/*  51:    */   {
/*  52: 93 */     int numThreads = conf.getInt("mapreduce.input.fileinputformat.list-status.num-threads", 1);
/*  53:    */     
/*  54: 95 */     this.rawExec = Executors.newFixedThreadPool(numThreads, new ThreadFactoryBuilder().setDaemon(true).setNameFormat("GetFileInfo #%d").build());
/*  55:    */     
/*  56:    */ 
/*  57:    */ 
/*  58: 99 */     this.exec = MoreExecutors.listeningDecorator(this.rawExec);
/*  59:100 */     this.resultQueue = new LinkedBlockingQueue();
/*  60:101 */     this.conf = conf;
/*  61:102 */     this.inputDirs = dirs;
/*  62:103 */     this.recursive = recursive;
/*  63:104 */     this.inputFilter = inputFilter;
/*  64:105 */     this.newApi = newApi;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public Iterable<FileStatus> getFileStatuses()
/*  68:    */     throws InterruptedException, IOException
/*  69:    */   {
/*  70:118 */     this.runningTasks.incrementAndGet();
/*  71:119 */     for (Path p : this.inputDirs)
/*  72:    */     {
/*  73:120 */       this.runningTasks.incrementAndGet();
/*  74:121 */       ListenableFuture<LocatedFileStatusFetcher.ProcessInitialInputPathCallable.Result> future = this.exec.submit(new ProcessInitialInputPathCallable(p, this.conf, this.inputFilter));
/*  75:    */       
/*  76:123 */       Futures.addCallback(future, this.processInitialInputPathCallback);
/*  77:    */     }
/*  78:126 */     this.runningTasks.decrementAndGet();
/*  79:    */     
/*  80:128 */     this.lock.lock();
/*  81:    */     try
/*  82:    */     {
/*  83:130 */       while ((this.runningTasks.get() != 0) && (this.unknownError == null)) {
/*  84:131 */         this.condition.await();
/*  85:    */       }
/*  86:    */     }
/*  87:    */     finally
/*  88:    */     {
/*  89:134 */       this.lock.unlock();
/*  90:    */     }
/*  91:136 */     this.exec.shutdownNow();
/*  92:137 */     if (this.unknownError != null)
/*  93:    */     {
/*  94:138 */       if ((this.unknownError instanceof Error)) {
/*  95:139 */         throw ((Error)this.unknownError);
/*  96:    */       }
/*  97:140 */       if ((this.unknownError instanceof RuntimeException)) {
/*  98:141 */         throw ((RuntimeException)this.unknownError);
/*  99:    */       }
/* 100:142 */       if ((this.unknownError instanceof IOException)) {
/* 101:143 */         throw ((IOException)this.unknownError);
/* 102:    */       }
/* 103:144 */       if ((this.unknownError instanceof InterruptedException)) {
/* 104:145 */         throw ((InterruptedException)this.unknownError);
/* 105:    */       }
/* 106:147 */       throw new IOException(this.unknownError);
/* 107:    */     }
/* 108:150 */     if (this.invalidInputErrors.size() != 0)
/* 109:    */     {
/* 110:151 */       if (this.newApi) {
/* 111:152 */         throw new org.apache.hadoop.mapreduce.lib.input.InvalidInputException(this.invalidInputErrors);
/* 112:    */       }
/* 113:155 */       throw new InvalidInputException(this.invalidInputErrors);
/* 114:    */     }
/* 115:158 */     return Iterables.concat(this.resultQueue);
/* 116:    */   }
/* 117:    */   
/* 118:    */   private void registerInvalidInputError(List<IOException> errors)
/* 119:    */   {
/* 120:166 */     synchronized (this)
/* 121:    */     {
/* 122:167 */       this.invalidInputErrors.addAll(errors);
/* 123:    */     }
/* 124:    */   }
/* 125:    */   
/* 126:    */   private void registerError(Throwable t)
/* 127:    */   {
/* 128:176 */     this.lock.lock();
/* 129:    */     try
/* 130:    */     {
/* 131:178 */       if (this.unknownError != null)
/* 132:    */       {
/* 133:179 */         this.unknownError = t;
/* 134:180 */         this.condition.signal();
/* 135:    */       }
/* 136:    */     }
/* 137:    */     finally
/* 138:    */     {
/* 139:184 */       this.lock.unlock();
/* 140:    */     }
/* 141:    */   }
/* 142:    */   
/* 143:    */   private void decrementRunningAndCheckCompletion()
/* 144:    */   {
/* 145:189 */     this.lock.lock();
/* 146:    */     try
/* 147:    */     {
/* 148:191 */       if (this.runningTasks.decrementAndGet() == 0) {
/* 149:192 */         this.condition.signal();
/* 150:    */       }
/* 151:    */     }
/* 152:    */     finally
/* 153:    */     {
/* 154:195 */       this.lock.unlock();
/* 155:    */     }
/* 156:    */   }
/* 157:    */   
/* 158:    */   private static class ProcessInputDirCallable
/* 159:    */     implements Callable<Result>
/* 160:    */   {
/* 161:    */     private final FileSystem fs;
/* 162:    */     private final FileStatus fileStatus;
/* 163:    */     private final boolean recursive;
/* 164:    */     private final PathFilter inputFilter;
/* 165:    */     
/* 166:    */     ProcessInputDirCallable(FileSystem fs, FileStatus fileStatus, boolean recursive, PathFilter inputFilter)
/* 167:    */     {
/* 168:213 */       this.fs = fs;
/* 169:214 */       this.fileStatus = fileStatus;
/* 170:215 */       this.recursive = recursive;
/* 171:216 */       this.inputFilter = inputFilter;
/* 172:    */     }
/* 173:    */     
/* 174:    */     public Result call()
/* 175:    */       throws Exception
/* 176:    */     {
/* 177:221 */       Result result = new Result(null);
/* 178:222 */       result.fs = this.fs;
/* 179:224 */       if (this.fileStatus.isDirectory())
/* 180:    */       {
/* 181:225 */         RemoteIterator<LocatedFileStatus> iter = this.fs.listLocatedStatus(this.fileStatus.getPath());
/* 182:227 */         while (iter.hasNext())
/* 183:    */         {
/* 184:228 */           LocatedFileStatus stat = (LocatedFileStatus)iter.next();
/* 185:229 */           if (this.inputFilter.accept(stat.getPath())) {
/* 186:230 */             if ((this.recursive) && (stat.isDirectory())) {
/* 187:231 */               result.dirsNeedingRecursiveCalls.add(stat);
/* 188:    */             } else {
/* 189:233 */               result.locatedFileStatuses.add(stat);
/* 190:    */             }
/* 191:    */           }
/* 192:    */         }
/* 193:    */       }
/* 194:    */       else
/* 195:    */       {
/* 196:238 */         result.locatedFileStatuses.add(this.fileStatus);
/* 197:    */       }
/* 198:240 */       return result;
/* 199:    */     }
/* 200:    */     
/* 201:    */     private static class Result
/* 202:    */     {
/* 203:244 */       private List<FileStatus> locatedFileStatuses = new LinkedList();
/* 204:245 */       private List<FileStatus> dirsNeedingRecursiveCalls = new LinkedList();
/* 205:    */       private FileSystem fs;
/* 206:    */     }
/* 207:    */   }
/* 208:    */   
/* 209:    */   private class ProcessInputDirCallback
/* 210:    */     implements FutureCallback<LocatedFileStatusFetcher.ProcessInputDirCallable.Result>
/* 211:    */   {
/* 212:    */     private ProcessInputDirCallback() {}
/* 213:    */     
/* 214:    */     public void onSuccess(LocatedFileStatusFetcher.ProcessInputDirCallable.Result result)
/* 215:    */     {
/* 216:    */       try
/* 217:    */       {
/* 218:261 */         if (LocatedFileStatusFetcher.ProcessInputDirCallable.Result.access$500(result).size() != 0) {
/* 219:262 */           LocatedFileStatusFetcher.this.resultQueue.add(LocatedFileStatusFetcher.ProcessInputDirCallable.Result.access$500(result));
/* 220:    */         }
/* 221:264 */         if (LocatedFileStatusFetcher.ProcessInputDirCallable.Result.access$400(result).size() != 0) {
/* 222:265 */           for (FileStatus fileStatus : LocatedFileStatusFetcher.ProcessInputDirCallable.Result.access$400(result))
/* 223:    */           {
/* 224:266 */             LocatedFileStatusFetcher.this.runningTasks.incrementAndGet();
/* 225:267 */             ListenableFuture<LocatedFileStatusFetcher.ProcessInputDirCallable.Result> future = LocatedFileStatusFetcher.this.exec.submit(new LocatedFileStatusFetcher.ProcessInputDirCallable(LocatedFileStatusFetcher.ProcessInputDirCallable.Result.access$300(result), fileStatus, LocatedFileStatusFetcher.this.recursive, LocatedFileStatusFetcher.this.inputFilter));
/* 226:    */             
/* 227:    */ 
/* 228:270 */             Futures.addCallback(future, LocatedFileStatusFetcher.this.processInputDirCallback);
/* 229:    */           }
/* 230:    */         }
/* 231:273 */         LocatedFileStatusFetcher.this.decrementRunningAndCheckCompletion();
/* 232:    */       }
/* 233:    */       catch (Throwable t)
/* 234:    */       {
/* 235:275 */         LocatedFileStatusFetcher.this.registerError(t);
/* 236:    */       }
/* 237:    */     }
/* 238:    */     
/* 239:    */     public void onFailure(Throwable t)
/* 240:    */     {
/* 241:282 */       LocatedFileStatusFetcher.this.registerError(t);
/* 242:    */     }
/* 243:    */   }
/* 244:    */   
/* 245:    */   private static class ProcessInitialInputPathCallable
/* 246:    */     implements Callable<Result>
/* 247:    */   {
/* 248:    */     private final Path path;
/* 249:    */     private final Configuration conf;
/* 250:    */     private final PathFilter inputFilter;
/* 251:    */     
/* 252:    */     public ProcessInitialInputPathCallable(Path path, Configuration conf, PathFilter pathFilter)
/* 253:    */     {
/* 254:300 */       this.path = path;
/* 255:301 */       this.conf = conf;
/* 256:302 */       this.inputFilter = pathFilter;
/* 257:    */     }
/* 258:    */     
/* 259:    */     public Result call()
/* 260:    */       throws Exception
/* 261:    */     {
/* 262:307 */       Result result = new Result(null);
/* 263:308 */       FileSystem fs = this.path.getFileSystem(this.conf);
/* 264:309 */       result.fs = fs;
/* 265:310 */       FileStatus[] matches = fs.globStatus(this.path, this.inputFilter);
/* 266:311 */       if (matches == null) {
/* 267:312 */         result.addError(new IOException("Input path does not exist: " + this.path));
/* 268:313 */       } else if (matches.length == 0) {
/* 269:314 */         result.addError(new IOException("Input Pattern " + this.path + " matches 0 files"));
/* 270:    */       } else {
/* 271:317 */         result.matchedFileStatuses = matches;
/* 272:    */       }
/* 273:319 */       return result;
/* 274:    */     }
/* 275:    */     
/* 276:    */     private static class Result
/* 277:    */     {
/* 278:    */       private List<IOException> errors;
/* 279:    */       private FileStatus[] matchedFileStatuses;
/* 280:    */       private FileSystem fs;
/* 281:    */       
/* 282:    */       void addError(IOException ioe)
/* 283:    */       {
/* 284:328 */         if (this.errors == null) {
/* 285:329 */           this.errors = new LinkedList();
/* 286:    */         }
/* 287:331 */         this.errors.add(ioe);
/* 288:    */       }
/* 289:    */     }
/* 290:    */   }
/* 291:    */   
/* 292:    */   private class ProcessInitialInputPathCallback
/* 293:    */     implements FutureCallback<LocatedFileStatusFetcher.ProcessInitialInputPathCallable.Result>
/* 294:    */   {
/* 295:    */     private ProcessInitialInputPathCallback() {}
/* 296:    */     
/* 297:    */     public void onSuccess(LocatedFileStatusFetcher.ProcessInitialInputPathCallable.Result result)
/* 298:    */     {
/* 299:    */       try
/* 300:    */       {
/* 301:347 */         if (LocatedFileStatusFetcher.ProcessInitialInputPathCallable.Result.access$1700(result) != null) {
/* 302:348 */           LocatedFileStatusFetcher.this.registerInvalidInputError(LocatedFileStatusFetcher.ProcessInitialInputPathCallable.Result.access$1700(result));
/* 303:    */         }
/* 304:350 */         if (LocatedFileStatusFetcher.ProcessInitialInputPathCallable.Result.access$1600(result) != null) {
/* 305:351 */           for (FileStatus matched : LocatedFileStatusFetcher.ProcessInitialInputPathCallable.Result.access$1600(result))
/* 306:    */           {
/* 307:352 */             LocatedFileStatusFetcher.this.runningTasks.incrementAndGet();
/* 308:353 */             ListenableFuture<LocatedFileStatusFetcher.ProcessInputDirCallable.Result> future = LocatedFileStatusFetcher.this.exec.submit(new LocatedFileStatusFetcher.ProcessInputDirCallable(LocatedFileStatusFetcher.ProcessInitialInputPathCallable.Result.access$1500(result), matched, LocatedFileStatusFetcher.this.recursive, LocatedFileStatusFetcher.this.inputFilter));
/* 309:    */             
/* 310:    */ 
/* 311:356 */             Futures.addCallback(future, LocatedFileStatusFetcher.this.processInputDirCallback);
/* 312:    */           }
/* 313:    */         }
/* 314:359 */         LocatedFileStatusFetcher.this.decrementRunningAndCheckCompletion();
/* 315:    */       }
/* 316:    */       catch (Throwable t)
/* 317:    */       {
/* 318:361 */         LocatedFileStatusFetcher.this.registerError(t);
/* 319:    */       }
/* 320:    */     }
/* 321:    */     
/* 322:    */     public void onFailure(Throwable t)
/* 323:    */     {
/* 324:368 */       LocatedFileStatusFetcher.this.registerError(t);
/* 325:    */     }
/* 326:    */   }
/* 327:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.LocatedFileStatusFetcher
 * JD-Core Version:    0.7.0.1
 */