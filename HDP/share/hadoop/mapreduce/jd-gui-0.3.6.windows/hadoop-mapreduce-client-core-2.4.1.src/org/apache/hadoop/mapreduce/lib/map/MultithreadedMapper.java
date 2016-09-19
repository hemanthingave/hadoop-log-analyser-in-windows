/*   1:    */ package org.apache.hadoop.mapreduce.lib.map;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import java.util.List;
/*   6:    */ import org.apache.commons.logging.Log;
/*   7:    */ import org.apache.commons.logging.LogFactory;
/*   8:    */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*   9:    */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*  10:    */ import org.apache.hadoop.conf.Configuration;
/*  11:    */ import org.apache.hadoop.mapreduce.Counter;
/*  12:    */ import org.apache.hadoop.mapreduce.InputSplit;
/*  13:    */ import org.apache.hadoop.mapreduce.Job;
/*  14:    */ import org.apache.hadoop.mapreduce.JobContext;
/*  15:    */ import org.apache.hadoop.mapreduce.MapContext;
/*  16:    */ import org.apache.hadoop.mapreduce.Mapper;
/*  17:    */ import org.apache.hadoop.mapreduce.Mapper.Context;
/*  18:    */ import org.apache.hadoop.mapreduce.RecordReader;
/*  19:    */ import org.apache.hadoop.mapreduce.RecordWriter;
/*  20:    */ import org.apache.hadoop.mapreduce.StatusReporter;
/*  21:    */ import org.apache.hadoop.mapreduce.TaskAttemptContext;
/*  22:    */ import org.apache.hadoop.mapreduce.task.MapContextImpl;
/*  23:    */ import org.apache.hadoop.util.ReflectionUtils;
/*  24:    */ 
/*  25:    */ @InterfaceAudience.Public
/*  26:    */ @InterfaceStability.Stable
/*  27:    */ public class MultithreadedMapper<K1, V1, K2, V2>
/*  28:    */   extends Mapper<K1, V1, K2, V2>
/*  29:    */ {
/*  30: 64 */   private static final Log LOG = LogFactory.getLog(MultithreadedMapper.class);
/*  31: 65 */   public static String NUM_THREADS = "mapreduce.mapper.multithreadedmapper.threads";
/*  32: 66 */   public static String MAP_CLASS = "mapreduce.mapper.multithreadedmapper.mapclass";
/*  33:    */   private Class<? extends Mapper<K1, V1, K2, V2>> mapClass;
/*  34:    */   private Mapper<K1, V1, K2, V2>.Context outer;
/*  35:    */   private List<MultithreadedMapper<K1, V1, K2, V2>.MapRunner> runners;
/*  36:    */   
/*  37:    */   public static int getNumberOfThreads(JobContext job)
/*  38:    */   {
/*  39: 78 */     return job.getConfiguration().getInt(NUM_THREADS, 10);
/*  40:    */   }
/*  41:    */   
/*  42:    */   public static void setNumberOfThreads(Job job, int threads)
/*  43:    */   {
/*  44: 87 */     job.getConfiguration().setInt(NUM_THREADS, threads);
/*  45:    */   }
/*  46:    */   
/*  47:    */   public static <K1, V1, K2, V2> Class<Mapper<K1, V1, K2, V2>> getMapperClass(JobContext job)
/*  48:    */   {
/*  49:102 */     return job.getConfiguration().getClass(MAP_CLASS, Mapper.class);
/*  50:    */   }
/*  51:    */   
/*  52:    */   public static <K1, V1, K2, V2> void setMapperClass(Job job, Class<? extends Mapper<K1, V1, K2, V2>> cls)
/*  53:    */   {
/*  54:118 */     if (MultithreadedMapper.class.isAssignableFrom(cls)) {
/*  55:119 */       throw new IllegalArgumentException("Can't have recursive MultithreadedMapper instances.");
/*  56:    */     }
/*  57:122 */     job.getConfiguration().setClass(MAP_CLASS, cls, Mapper.class);
/*  58:    */   }
/*  59:    */   
/*  60:    */   public void run(Mapper<K1, V1, K2, V2>.Context context)
/*  61:    */     throws IOException, InterruptedException
/*  62:    */   {
/*  63:130 */     this.outer = context;
/*  64:131 */     int numberOfThreads = getNumberOfThreads(context);
/*  65:132 */     this.mapClass = getMapperClass(context);
/*  66:133 */     if (LOG.isDebugEnabled()) {
/*  67:134 */       LOG.debug("Configuring multithread runner to use " + numberOfThreads + " threads");
/*  68:    */     }
/*  69:138 */     this.runners = new ArrayList(numberOfThreads);
/*  70:139 */     for (int i = 0; i < numberOfThreads; i++)
/*  71:    */     {
/*  72:140 */       MultithreadedMapper<K1, V1, K2, V2>.MapRunner thread = new MapRunner(context);
/*  73:141 */       thread.start();
/*  74:142 */       this.runners.add(i, thread);
/*  75:    */     }
/*  76:144 */     for (int i = 0; i < numberOfThreads; i++)
/*  77:    */     {
/*  78:145 */       MultithreadedMapper<K1, V1, K2, V2>.MapRunner thread = (MapRunner)this.runners.get(i);
/*  79:146 */       thread.join();
/*  80:147 */       Throwable th = thread.throwable;
/*  81:148 */       if (th != null)
/*  82:    */       {
/*  83:149 */         if ((th instanceof IOException)) {
/*  84:150 */           throw ((IOException)th);
/*  85:    */         }
/*  86:151 */         if ((th instanceof InterruptedException)) {
/*  87:152 */           throw ((InterruptedException)th);
/*  88:    */         }
/*  89:154 */         throw new RuntimeException(th);
/*  90:    */       }
/*  91:    */     }
/*  92:    */   }
/*  93:    */   
/*  94:    */   private class SubMapRecordReader
/*  95:    */     extends RecordReader<K1, V1>
/*  96:    */   {
/*  97:    */     private K1 key;
/*  98:    */     private V1 value;
/*  99:    */     private Configuration conf;
/* 100:    */     
/* 101:    */     private SubMapRecordReader() {}
/* 102:    */     
/* 103:    */     public void close()
/* 104:    */       throws IOException
/* 105:    */     {}
/* 106:    */     
/* 107:    */     public float getProgress()
/* 108:    */       throws IOException, InterruptedException
/* 109:    */     {
/* 110:171 */       return 0.0F;
/* 111:    */     }
/* 112:    */     
/* 113:    */     public void initialize(InputSplit split, TaskAttemptContext context)
/* 114:    */       throws IOException, InterruptedException
/* 115:    */     {
/* 116:178 */       this.conf = context.getConfiguration();
/* 117:    */     }
/* 118:    */     
/* 119:    */     public boolean nextKeyValue()
/* 120:    */       throws IOException, InterruptedException
/* 121:    */     {
/* 122:184 */       synchronized (MultithreadedMapper.this.outer)
/* 123:    */       {
/* 124:185 */         if (!MultithreadedMapper.this.outer.nextKeyValue()) {
/* 125:186 */           return false;
/* 126:    */         }
/* 127:188 */         this.key = ReflectionUtils.copy(MultithreadedMapper.this.outer.getConfiguration(), MultithreadedMapper.this.outer.getCurrentKey(), this.key);
/* 128:    */         
/* 129:190 */         this.value = ReflectionUtils.copy(this.conf, MultithreadedMapper.this.outer.getCurrentValue(), this.value);
/* 130:191 */         return true;
/* 131:    */       }
/* 132:    */     }
/* 133:    */     
/* 134:    */     public K1 getCurrentKey()
/* 135:    */     {
/* 136:196 */       return this.key;
/* 137:    */     }
/* 138:    */     
/* 139:    */     public V1 getCurrentValue()
/* 140:    */     {
/* 141:201 */       return this.value;
/* 142:    */     }
/* 143:    */   }
/* 144:    */   
/* 145:    */   private class SubMapRecordWriter
/* 146:    */     extends RecordWriter<K2, V2>
/* 147:    */   {
/* 148:    */     private SubMapRecordWriter() {}
/* 149:    */     
/* 150:    */     public void close(TaskAttemptContext context)
/* 151:    */       throws IOException, InterruptedException
/* 152:    */     {}
/* 153:    */     
/* 154:    */     public void write(K2 key, V2 value)
/* 155:    */       throws IOException, InterruptedException
/* 156:    */     {
/* 157:215 */       synchronized (MultithreadedMapper.this.outer)
/* 158:    */       {
/* 159:216 */         MultithreadedMapper.this.outer.write(key, value);
/* 160:    */       }
/* 161:    */     }
/* 162:    */   }
/* 163:    */   
/* 164:    */   private class SubMapStatusReporter
/* 165:    */     extends StatusReporter
/* 166:    */   {
/* 167:    */     private SubMapStatusReporter() {}
/* 168:    */     
/* 169:    */     public Counter getCounter(Enum<?> name)
/* 170:    */     {
/* 171:225 */       return MultithreadedMapper.this.outer.getCounter(name);
/* 172:    */     }
/* 173:    */     
/* 174:    */     public Counter getCounter(String group, String name)
/* 175:    */     {
/* 176:230 */       return MultithreadedMapper.this.outer.getCounter(group, name);
/* 177:    */     }
/* 178:    */     
/* 179:    */     public void progress()
/* 180:    */     {
/* 181:235 */       MultithreadedMapper.this.outer.progress();
/* 182:    */     }
/* 183:    */     
/* 184:    */     public void setStatus(String status)
/* 185:    */     {
/* 186:240 */       MultithreadedMapper.this.outer.setStatus(status);
/* 187:    */     }
/* 188:    */     
/* 189:    */     public float getProgress()
/* 190:    */     {
/* 191:245 */       return MultithreadedMapper.this.outer.getProgress();
/* 192:    */     }
/* 193:    */   }
/* 194:    */   
/* 195:    */   private class MapRunner
/* 196:    */     extends Thread
/* 197:    */   {
/* 198:    */     private Mapper<K1, V1, K2, V2> mapper;
/* 199:    */     private Mapper<K1, V1, K2, V2>.Context subcontext;
/* 200:    */     private Throwable throwable;
/* 201:253 */     private RecordReader<K1, V1> reader = new MultithreadedMapper.SubMapRecordReader(MultithreadedMapper.this, null);
/* 202:    */     
/* 203:    */     MapRunner()
/* 204:    */       throws IOException, InterruptedException
/* 205:    */     {
/* 206:256 */       this.mapper = ((Mapper)ReflectionUtils.newInstance(MultithreadedMapper.this.mapClass, context.getConfiguration()));
/* 207:    */       
/* 208:258 */       MapContext<K1, V1, K2, V2> mapContext = new MapContextImpl(MultithreadedMapper.this.outer.getConfiguration(), MultithreadedMapper.this.outer.getTaskAttemptID(), this.reader, new MultithreadedMapper.SubMapRecordWriter(MultithreadedMapper.this, null), context.getOutputCommitter(), new MultithreadedMapper.SubMapStatusReporter(MultithreadedMapper.this, null), MultithreadedMapper.this.outer.getInputSplit());
/* 209:    */       
/* 210:    */ 
/* 211:    */ 
/* 212:    */ 
/* 213:    */ 
/* 214:    */ 
/* 215:    */ 
/* 216:266 */       this.subcontext = new WrappedMapper().getMapContext(mapContext);
/* 217:267 */       this.reader.initialize(context.getInputSplit(), context);
/* 218:    */     }
/* 219:    */     
/* 220:    */     public void run()
/* 221:    */     {
/* 222:    */       try
/* 223:    */       {
/* 224:273 */         this.mapper.run(this.subcontext);
/* 225:274 */         this.reader.close();
/* 226:    */       }
/* 227:    */       catch (Throwable ie)
/* 228:    */       {
/* 229:276 */         this.throwable = ie;
/* 230:    */       }
/* 231:    */     }
/* 232:    */   }
/* 233:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.lib.map.MultithreadedMapper
 * JD-Core Version:    0.7.0.1
 */