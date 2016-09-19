/*   1:    */ package org.apache.hadoop.mapred.lib;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.util.concurrent.ArrayBlockingQueue;
/*   5:    */ import java.util.concurrent.ExecutorService;
/*   6:    */ import java.util.concurrent.ThreadPoolExecutor;
/*   7:    */ import java.util.concurrent.TimeUnit;
/*   8:    */ import org.apache.commons.logging.Log;
/*   9:    */ import org.apache.commons.logging.LogFactory;
/*  10:    */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*  11:    */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*  12:    */ import org.apache.hadoop.mapred.JobConf;
/*  13:    */ import org.apache.hadoop.mapred.MapRunnable;
/*  14:    */ import org.apache.hadoop.mapred.Mapper;
/*  15:    */ import org.apache.hadoop.mapred.OutputCollector;
/*  16:    */ import org.apache.hadoop.mapred.RecordReader;
/*  17:    */ import org.apache.hadoop.mapred.Reporter;
/*  18:    */ import org.apache.hadoop.mapred.SkipBadRecords;
/*  19:    */ import org.apache.hadoop.mapreduce.lib.map.MultithreadedMapper;
/*  20:    */ import org.apache.hadoop.util.ReflectionUtils;
/*  21:    */ 
/*  22:    */ @InterfaceAudience.Public
/*  23:    */ @InterfaceStability.Stable
/*  24:    */ public class MultithreadedMapRunner<K1, V1, K2, V2>
/*  25:    */   implements MapRunnable<K1, V1, K2, V2>
/*  26:    */ {
/*  27: 59 */   private static final Log LOG = LogFactory.getLog(MultithreadedMapRunner.class.getName());
/*  28:    */   private JobConf job;
/*  29:    */   private Mapper<K1, V1, K2, V2> mapper;
/*  30:    */   private ExecutorService executorService;
/*  31:    */   private volatile IOException ioException;
/*  32:    */   private volatile RuntimeException runtimeException;
/*  33:    */   private boolean incrProcCount;
/*  34:    */   
/*  35:    */   public void configure(JobConf jobConf)
/*  36:    */   {
/*  37: 71 */     int numberOfThreads = jobConf.getInt(MultithreadedMapper.NUM_THREADS, 10);
/*  38: 73 */     if (LOG.isDebugEnabled()) {
/*  39: 74 */       LOG.debug("Configuring jobConf " + jobConf.getJobName() + " to use " + numberOfThreads + " threads");
/*  40:    */     }
/*  41: 78 */     this.job = jobConf;
/*  42:    */     
/*  43: 80 */     this.incrProcCount = ((SkipBadRecords.getMapperMaxSkipRecords(this.job) > 0L) && (SkipBadRecords.getAutoIncrMapperProcCount(this.job)));
/*  44:    */     
/*  45: 82 */     this.mapper = ((Mapper)ReflectionUtils.newInstance(jobConf.getMapperClass(), jobConf));
/*  46:    */     
/*  47:    */ 
/*  48:    */ 
/*  49:    */ 
/*  50: 87 */     this.executorService = new ThreadPoolExecutor(numberOfThreads, numberOfThreads, 0L, TimeUnit.MILLISECONDS, new BlockingArrayQueue(numberOfThreads));
/*  51:    */   }
/*  52:    */   
/*  53:    */   private static class BlockingArrayQueue
/*  54:    */     extends ArrayBlockingQueue<Runnable>
/*  55:    */   {
/*  56:    */     private static final long serialVersionUID = 1L;
/*  57:    */     
/*  58:    */     public BlockingArrayQueue(int capacity)
/*  59:    */     {
/*  60:101 */       super();
/*  61:    */     }
/*  62:    */     
/*  63:    */     public boolean offer(Runnable r)
/*  64:    */     {
/*  65:104 */       return add(r);
/*  66:    */     }
/*  67:    */     
/*  68:    */     public boolean add(Runnable r)
/*  69:    */     {
/*  70:    */       try
/*  71:    */       {
/*  72:108 */         put(r);
/*  73:    */       }
/*  74:    */       catch (InterruptedException ie)
/*  75:    */       {
/*  76:110 */         Thread.currentThread().interrupt();
/*  77:    */       }
/*  78:112 */       return true;
/*  79:    */     }
/*  80:    */   }
/*  81:    */   
/*  82:    */   private void checkForExceptionsFromProcessingThreads()
/*  83:    */     throws IOException, RuntimeException
/*  84:    */   {
/*  85:122 */     if (this.ioException != null) {
/*  86:123 */       throw this.ioException;
/*  87:    */     }
/*  88:130 */     if (this.runtimeException != null) {
/*  89:131 */       throw this.runtimeException;
/*  90:    */     }
/*  91:    */   }
/*  92:    */   
/*  93:    */   public void run(RecordReader<K1, V1> input, OutputCollector<K2, V2> output, Reporter reporter)
/*  94:    */     throws IOException
/*  95:    */   {
/*  96:    */     try
/*  97:    */     {
/*  98:141 */       K1 key = input.createKey();
/*  99:142 */       V1 value = input.createValue();
/* 100:144 */       while (input.next(key, value))
/* 101:    */       {
/* 102:146 */         this.executorService.execute(new MapperInvokeRunable(key, value, output, reporter));
/* 103:    */         
/* 104:    */ 
/* 105:149 */         checkForExceptionsFromProcessingThreads();
/* 106:    */         
/* 107:    */ 
/* 108:152 */         key = input.createKey();
/* 109:153 */         value = input.createValue();
/* 110:    */       }
/* 111:156 */       if (LOG.isDebugEnabled()) {
/* 112:157 */         LOG.debug("Finished dispatching all Mappper.map calls, job " + this.job.getJobName());
/* 113:    */       }
/* 114:163 */       this.executorService.shutdown();
/* 115:    */       try
/* 116:    */       {
/* 117:168 */         while (!this.executorService.awaitTermination(100L, TimeUnit.MILLISECONDS))
/* 118:    */         {
/* 119:169 */           if (LOG.isDebugEnabled()) {
/* 120:170 */             LOG.debug("Awaiting all running Mappper.map calls to finish, job " + this.job.getJobName());
/* 121:    */           }
/* 122:176 */           checkForExceptionsFromProcessingThreads();
/* 123:    */         }
/* 124:183 */         checkForExceptionsFromProcessingThreads();
/* 125:    */       }
/* 126:    */       catch (IOException ioEx)
/* 127:    */       {
/* 128:188 */         this.executorService.shutdownNow();
/* 129:189 */         throw ioEx;
/* 130:    */       }
/* 131:    */       catch (InterruptedException iEx)
/* 132:    */       {
/* 133:191 */         throw new RuntimeException(iEx);
/* 134:    */       }
/* 135:    */     }
/* 136:    */     finally
/* 137:    */     {
/* 138:195 */       this.mapper.close();
/* 139:    */     }
/* 140:    */   }
/* 141:    */   
/* 142:    */   private class MapperInvokeRunable
/* 143:    */     implements Runnable
/* 144:    */   {
/* 145:    */     private K1 key;
/* 146:    */     private V1 value;
/* 147:    */     private OutputCollector<K2, V2> output;
/* 148:    */     private Reporter reporter;
/* 149:    */     
/* 150:    */     public MapperInvokeRunable(V1 key, OutputCollector<K2, V2> value, Reporter output)
/* 151:    */     {
/* 152:221 */       this.key = key;
/* 153:222 */       this.value = value;
/* 154:223 */       this.output = output;
/* 155:224 */       this.reporter = reporter;
/* 156:    */     }
/* 157:    */     
/* 158:    */     public void run()
/* 159:    */     {
/* 160:    */       try
/* 161:    */       {
/* 162:236 */         MultithreadedMapRunner.this.mapper.map(this.key, this.value, this.output, this.reporter);
/* 163:237 */         if (MultithreadedMapRunner.this.incrProcCount) {
/* 164:238 */           this.reporter.incrCounter("SkippingTaskCounters", "MapProcessedRecords", 1L);
/* 165:    */         }
/* 166:    */       }
/* 167:    */       catch (IOException ex)
/* 168:    */       {
/* 169:245 */         synchronized (MultithreadedMapRunner.this)
/* 170:    */         {
/* 171:246 */           if (MultithreadedMapRunner.this.ioException == null) {
/* 172:247 */             MultithreadedMapRunner.this.ioException = ex;
/* 173:    */           }
/* 174:    */         }
/* 175:    */       }
/* 176:    */       catch (RuntimeException ex)
/* 177:    */       {
/* 178:254 */         synchronized (MultithreadedMapRunner.this)
/* 179:    */         {
/* 180:255 */           if (MultithreadedMapRunner.this.runtimeException == null) {
/* 181:256 */             MultithreadedMapRunner.this.runtimeException = ex;
/* 182:    */           }
/* 183:    */         }
/* 184:    */       }
/* 185:    */     }
/* 186:    */   }
/* 187:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.lib.MultithreadedMapRunner
 * JD-Core Version:    0.7.0.1
 */