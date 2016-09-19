/*   1:    */ package org.apache.hadoop.mapreduce.lib.partition;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.PrintStream;
/*   5:    */ import java.util.ArrayList;
/*   6:    */ import java.util.Arrays;
/*   7:    */ import java.util.List;
/*   8:    */ import java.util.Random;
/*   9:    */ import org.apache.commons.logging.Log;
/*  10:    */ import org.apache.commons.logging.LogFactory;
/*  11:    */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*  12:    */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*  13:    */ import org.apache.hadoop.conf.Configuration;
/*  14:    */ import org.apache.hadoop.conf.Configured;
/*  15:    */ import org.apache.hadoop.fs.FileSystem;
/*  16:    */ import org.apache.hadoop.fs.Path;
/*  17:    */ import org.apache.hadoop.io.NullWritable;
/*  18:    */ import org.apache.hadoop.io.RawComparator;
/*  19:    */ import org.apache.hadoop.io.SequenceFile;
/*  20:    */ import org.apache.hadoop.io.SequenceFile.Writer;
/*  21:    */ import org.apache.hadoop.io.WritableComparable;
/*  22:    */ import org.apache.hadoop.mapreduce.InputFormat;
/*  23:    */ import org.apache.hadoop.mapreduce.InputSplit;
/*  24:    */ import org.apache.hadoop.mapreduce.Job;
/*  25:    */ import org.apache.hadoop.mapreduce.RecordReader;
/*  26:    */ import org.apache.hadoop.mapreduce.TaskAttemptContext;
/*  27:    */ import org.apache.hadoop.mapreduce.TaskAttemptID;
/*  28:    */ import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
/*  29:    */ import org.apache.hadoop.mapreduce.task.TaskAttemptContextImpl;
/*  30:    */ import org.apache.hadoop.util.ReflectionUtils;
/*  31:    */ import org.apache.hadoop.util.Tool;
/*  32:    */ import org.apache.hadoop.util.ToolRunner;
/*  33:    */ 
/*  34:    */ @InterfaceAudience.Public
/*  35:    */ @InterfaceStability.Stable
/*  36:    */ public class InputSampler<K, V>
/*  37:    */   extends Configured
/*  38:    */   implements Tool
/*  39:    */ {
/*  40: 59 */   private static final Log LOG = LogFactory.getLog(InputSampler.class);
/*  41:    */   
/*  42:    */   static int printUsage()
/*  43:    */   {
/*  44: 62 */     System.out.println("sampler -r <reduces>\n      [-inFormat <input format class>]\n      [-keyClass <map input & output key class>]\n      [-splitRandom <double pcnt> <numSamples> <maxsplits> |              // Sample from random splits at random (general)\n       -splitSample <numSamples> <maxsplits> |              // Sample from first records in splits (random data)\n       -splitInterval <double pcnt> <maxsplits>]             // Sample from splits at intervals (sorted data)");
/*  45:    */     
/*  46:    */ 
/*  47:    */ 
/*  48:    */ 
/*  49:    */ 
/*  50:    */ 
/*  51:    */ 
/*  52:    */ 
/*  53: 71 */     System.out.println("Default sampler: -splitRandom 0.1 10000 10");
/*  54: 72 */     ToolRunner.printGenericCommandUsage(System.out);
/*  55: 73 */     return -1;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public InputSampler(Configuration conf)
/*  59:    */   {
/*  60: 77 */     setConf(conf);
/*  61:    */   }
/*  62:    */   
/*  63:    */   public static abstract interface Sampler<K, V>
/*  64:    */   {
/*  65:    */     public abstract K[] getSample(InputFormat<K, V> paramInputFormat, Job paramJob)
/*  66:    */       throws IOException, InterruptedException;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public static class SplitSampler<K, V>
/*  70:    */     implements InputSampler.Sampler<K, V>
/*  71:    */   {
/*  72:    */     protected final int numSamples;
/*  73:    */     protected final int maxSplitsSampled;
/*  74:    */     
/*  75:    */     public SplitSampler(int numSamples)
/*  76:    */     {
/*  77:109 */       this(numSamples, 2147483647);
/*  78:    */     }
/*  79:    */     
/*  80:    */     public SplitSampler(int numSamples, int maxSplitsSampled)
/*  81:    */     {
/*  82:119 */       this.numSamples = numSamples;
/*  83:120 */       this.maxSplitsSampled = maxSplitsSampled;
/*  84:    */     }
/*  85:    */     
/*  86:    */     public K[] getSample(InputFormat<K, V> inf, Job job)
/*  87:    */       throws IOException, InterruptedException
/*  88:    */     {
/*  89:129 */       List<InputSplit> splits = inf.getSplits(job);
/*  90:130 */       ArrayList<K> samples = new ArrayList(this.numSamples);
/*  91:131 */       int splitsToSample = Math.min(this.maxSplitsSampled, splits.size());
/*  92:132 */       int samplesPerSplit = this.numSamples / splitsToSample;
/*  93:133 */       long records = 0L;
/*  94:134 */       for (int i = 0; i < splitsToSample; i++)
/*  95:    */       {
/*  96:135 */         TaskAttemptContext samplingContext = new TaskAttemptContextImpl(job.getConfiguration(), new TaskAttemptID());
/*  97:    */         
/*  98:137 */         RecordReader<K, V> reader = inf.createRecordReader((InputSplit)splits.get(i), samplingContext);
/*  99:    */         
/* 100:139 */         reader.initialize((InputSplit)splits.get(i), samplingContext);
/* 101:140 */         while (reader.nextKeyValue())
/* 102:    */         {
/* 103:141 */           samples.add(ReflectionUtils.copy(job.getConfiguration(), reader.getCurrentKey(), null));
/* 104:    */           
/* 105:143 */           records += 1L;
/* 106:144 */           if ((i + 1) * samplesPerSplit <= records) {
/* 107:    */             break;
/* 108:    */           }
/* 109:    */         }
/* 110:148 */         reader.close();
/* 111:    */       }
/* 112:150 */       return (Object[])samples.toArray();
/* 113:    */     }
/* 114:    */   }
/* 115:    */   
/* 116:    */   public static class RandomSampler<K, V>
/* 117:    */     implements InputSampler.Sampler<K, V>
/* 118:    */   {
/* 119:    */     protected double freq;
/* 120:    */     protected final int numSamples;
/* 121:    */     protected final int maxSplitsSampled;
/* 122:    */     
/* 123:    */     public RandomSampler(double freq, int numSamples)
/* 124:    */     {
/* 125:172 */       this(freq, numSamples, 2147483647);
/* 126:    */     }
/* 127:    */     
/* 128:    */     public RandomSampler(double freq, int numSamples, int maxSplitsSampled)
/* 129:    */     {
/* 130:183 */       this.freq = freq;
/* 131:184 */       this.numSamples = numSamples;
/* 132:185 */       this.maxSplitsSampled = maxSplitsSampled;
/* 133:    */     }
/* 134:    */     
/* 135:    */     public K[] getSample(InputFormat<K, V> inf, Job job)
/* 136:    */       throws IOException, InterruptedException
/* 137:    */     {
/* 138:197 */       List<InputSplit> splits = inf.getSplits(job);
/* 139:198 */       ArrayList<K> samples = new ArrayList(this.numSamples);
/* 140:199 */       int splitsToSample = Math.min(this.maxSplitsSampled, splits.size());
/* 141:    */       
/* 142:201 */       Random r = new Random();
/* 143:202 */       long seed = r.nextLong();
/* 144:203 */       r.setSeed(seed);
/* 145:204 */       InputSampler.LOG.debug("seed: " + seed);
/* 146:206 */       for (int i = 0; i < splits.size(); i++)
/* 147:    */       {
/* 148:207 */         InputSplit tmp = (InputSplit)splits.get(i);
/* 149:208 */         int j = r.nextInt(splits.size());
/* 150:209 */         splits.set(i, splits.get(j));
/* 151:210 */         splits.set(j, tmp);
/* 152:    */       }
/* 153:215 */       for (int i = 0; (i < splitsToSample) || ((i < splits.size()) && (samples.size() < this.numSamples)); i++)
/* 154:    */       {
/* 155:217 */         TaskAttemptContext samplingContext = new TaskAttemptContextImpl(job.getConfiguration(), new TaskAttemptID());
/* 156:    */         
/* 157:219 */         RecordReader<K, V> reader = inf.createRecordReader((InputSplit)splits.get(i), samplingContext);
/* 158:    */         
/* 159:221 */         reader.initialize((InputSplit)splits.get(i), samplingContext);
/* 160:222 */         while (reader.nextKeyValue()) {
/* 161:223 */           if (r.nextDouble() <= this.freq) {
/* 162:224 */             if (samples.size() < this.numSamples)
/* 163:    */             {
/* 164:225 */               samples.add(ReflectionUtils.copy(job.getConfiguration(), reader.getCurrentKey(), null));
/* 165:    */             }
/* 166:    */             else
/* 167:    */             {
/* 168:232 */               int ind = r.nextInt(this.numSamples);
/* 169:233 */               if (ind != this.numSamples) {
/* 170:234 */                 samples.set(ind, ReflectionUtils.copy(job.getConfiguration(), reader.getCurrentKey(), null));
/* 171:    */               }
/* 172:237 */               this.freq *= (this.numSamples - 1) / this.numSamples;
/* 173:    */             }
/* 174:    */           }
/* 175:    */         }
/* 176:241 */         reader.close();
/* 177:    */       }
/* 178:243 */       return (Object[])samples.toArray();
/* 179:    */     }
/* 180:    */   }
/* 181:    */   
/* 182:    */   public static class IntervalSampler<K, V>
/* 183:    */     implements InputSampler.Sampler<K, V>
/* 184:    */   {
/* 185:    */     protected final double freq;
/* 186:    */     protected final int maxSplitsSampled;
/* 187:    */     
/* 188:    */     public IntervalSampler(double freq)
/* 189:    */     {
/* 190:260 */       this(freq, 2147483647);
/* 191:    */     }
/* 192:    */     
/* 193:    */     public IntervalSampler(double freq, int maxSplitsSampled)
/* 194:    */     {
/* 195:270 */       this.freq = freq;
/* 196:271 */       this.maxSplitsSampled = maxSplitsSampled;
/* 197:    */     }
/* 198:    */     
/* 199:    */     public K[] getSample(InputFormat<K, V> inf, Job job)
/* 200:    */       throws IOException, InterruptedException
/* 201:    */     {
/* 202:282 */       List<InputSplit> splits = inf.getSplits(job);
/* 203:283 */       ArrayList<K> samples = new ArrayList();
/* 204:284 */       int splitsToSample = Math.min(this.maxSplitsSampled, splits.size());
/* 205:285 */       long records = 0L;
/* 206:286 */       long kept = 0L;
/* 207:287 */       for (int i = 0; i < splitsToSample; i++)
/* 208:    */       {
/* 209:288 */         TaskAttemptContext samplingContext = new TaskAttemptContextImpl(job.getConfiguration(), new TaskAttemptID());
/* 210:    */         
/* 211:290 */         RecordReader<K, V> reader = inf.createRecordReader((InputSplit)splits.get(i), samplingContext);
/* 212:    */         
/* 213:292 */         reader.initialize((InputSplit)splits.get(i), samplingContext);
/* 214:293 */         while (reader.nextKeyValue())
/* 215:    */         {
/* 216:294 */           records += 1L;
/* 217:295 */           if (kept / records < this.freq)
/* 218:    */           {
/* 219:296 */             samples.add(ReflectionUtils.copy(job.getConfiguration(), reader.getCurrentKey(), null));
/* 220:    */             
/* 221:298 */             kept += 1L;
/* 222:    */           }
/* 223:    */         }
/* 224:301 */         reader.close();
/* 225:    */       }
/* 226:303 */       return (Object[])samples.toArray();
/* 227:    */     }
/* 228:    */   }
/* 229:    */   
/* 230:    */   public static <K, V> void writePartitionFile(Job job, Sampler<K, V> sampler)
/* 231:    */     throws IOException, ClassNotFoundException, InterruptedException
/* 232:    */   {
/* 233:316 */     Configuration conf = job.getConfiguration();
/* 234:317 */     InputFormat inf = (InputFormat)ReflectionUtils.newInstance(job.getInputFormatClass(), conf);
/* 235:    */     
/* 236:319 */     int numPartitions = job.getNumReduceTasks();
/* 237:320 */     K[] samples = (Object[])sampler.getSample(inf, job);
/* 238:321 */     LOG.info("Using " + samples.length + " samples");
/* 239:322 */     RawComparator<K> comparator = job.getSortComparator();
/* 240:    */     
/* 241:324 */     Arrays.sort(samples, comparator);
/* 242:325 */     Path dst = new Path(TotalOrderPartitioner.getPartitionFile(conf));
/* 243:326 */     FileSystem fs = dst.getFileSystem(conf);
/* 244:327 */     if (fs.exists(dst)) {
/* 245:328 */       fs.delete(dst, false);
/* 246:    */     }
/* 247:330 */     SequenceFile.Writer writer = SequenceFile.createWriter(fs, conf, dst, job.getMapOutputKeyClass(), NullWritable.class);
/* 248:    */     
/* 249:332 */     NullWritable nullValue = NullWritable.get();
/* 250:333 */     float stepSize = samples.length / numPartitions;
/* 251:334 */     int last = -1;
/* 252:335 */     for (int i = 1; i < numPartitions; i++)
/* 253:    */     {
/* 254:336 */       int k = Math.round(stepSize * i);
/* 255:337 */       while ((last >= k) && (comparator.compare(samples[last], samples[k]) == 0)) {
/* 256:338 */         k++;
/* 257:    */       }
/* 258:340 */       writer.append(samples[k], nullValue);
/* 259:341 */       last = k;
/* 260:    */     }
/* 261:343 */     writer.close();
/* 262:    */   }
/* 263:    */   
/* 264:    */   public int run(String[] args)
/* 265:    */     throws Exception
/* 266:    */   {
/* 267:351 */     Job job = new Job(getConf());
/* 268:352 */     ArrayList<String> otherArgs = new ArrayList();
/* 269:353 */     Sampler<K, V> sampler = null;
/* 270:354 */     for (int i = 0; i < args.length; i++) {
/* 271:    */       try
/* 272:    */       {
/* 273:356 */         if ("-r".equals(args[i]))
/* 274:    */         {
/* 275:357 */           job.setNumReduceTasks(Integer.parseInt(args[(++i)]));
/* 276:    */         }
/* 277:358 */         else if ("-inFormat".equals(args[i]))
/* 278:    */         {
/* 279:359 */           job.setInputFormatClass(Class.forName(args[(++i)]).asSubclass(InputFormat.class));
/* 280:    */         }
/* 281:361 */         else if ("-keyClass".equals(args[i]))
/* 282:    */         {
/* 283:362 */           job.setMapOutputKeyClass(Class.forName(args[(++i)]).asSubclass(WritableComparable.class));
/* 284:    */         }
/* 285:364 */         else if ("-splitSample".equals(args[i]))
/* 286:    */         {
/* 287:365 */           int numSamples = Integer.parseInt(args[(++i)]);
/* 288:366 */           int maxSplits = Integer.parseInt(args[(++i)]);
/* 289:367 */           if (0 >= maxSplits) {
/* 290:367 */             maxSplits = 2147483647;
/* 291:    */           }
/* 292:368 */           sampler = new SplitSampler(numSamples, maxSplits);
/* 293:    */         }
/* 294:369 */         else if ("-splitRandom".equals(args[i]))
/* 295:    */         {
/* 296:370 */           double pcnt = Double.parseDouble(args[(++i)]);
/* 297:371 */           int numSamples = Integer.parseInt(args[(++i)]);
/* 298:372 */           int maxSplits = Integer.parseInt(args[(++i)]);
/* 299:373 */           if (0 >= maxSplits) {
/* 300:373 */             maxSplits = 2147483647;
/* 301:    */           }
/* 302:374 */           sampler = new RandomSampler(pcnt, numSamples, maxSplits);
/* 303:    */         }
/* 304:375 */         else if ("-splitInterval".equals(args[i]))
/* 305:    */         {
/* 306:376 */           double pcnt = Double.parseDouble(args[(++i)]);
/* 307:377 */           int maxSplits = Integer.parseInt(args[(++i)]);
/* 308:378 */           if (0 >= maxSplits) {
/* 309:378 */             maxSplits = 2147483647;
/* 310:    */           }
/* 311:379 */           sampler = new IntervalSampler(pcnt, maxSplits);
/* 312:    */         }
/* 313:    */         else
/* 314:    */         {
/* 315:381 */           otherArgs.add(args[i]);
/* 316:    */         }
/* 317:    */       }
/* 318:    */       catch (NumberFormatException except)
/* 319:    */       {
/* 320:384 */         System.out.println("ERROR: Integer expected instead of " + args[i]);
/* 321:385 */         return printUsage();
/* 322:    */       }
/* 323:    */       catch (ArrayIndexOutOfBoundsException except)
/* 324:    */       {
/* 325:387 */         System.out.println("ERROR: Required parameter missing from " + args[(i - 1)]);
/* 326:    */         
/* 327:389 */         return printUsage();
/* 328:    */       }
/* 329:    */     }
/* 330:392 */     if (job.getNumReduceTasks() <= 1)
/* 331:    */     {
/* 332:393 */       System.err.println("Sampler requires more than one reducer");
/* 333:394 */       return printUsage();
/* 334:    */     }
/* 335:396 */     if (otherArgs.size() < 2)
/* 336:    */     {
/* 337:397 */       System.out.println("ERROR: Wrong number of parameters: ");
/* 338:398 */       return printUsage();
/* 339:    */     }
/* 340:400 */     if (null == sampler) {
/* 341:401 */       sampler = new RandomSampler(0.1D, 10000, 10);
/* 342:    */     }
/* 343:404 */     Path outf = new Path((String)otherArgs.remove(otherArgs.size() - 1));
/* 344:405 */     TotalOrderPartitioner.setPartitionFile(getConf(), outf);
/* 345:406 */     for (String s : otherArgs) {
/* 346:407 */       FileInputFormat.addInputPath(job, new Path(s));
/* 347:    */     }
/* 348:409 */     writePartitionFile(job, sampler);
/* 349:    */     
/* 350:411 */     return 0;
/* 351:    */   }
/* 352:    */   
/* 353:    */   public static void main(String[] args)
/* 354:    */     throws Exception
/* 355:    */   {
/* 356:415 */     InputSampler<?, ?> sampler = new InputSampler(new Configuration());
/* 357:416 */     int res = ToolRunner.run(sampler, args);
/* 358:417 */     System.exit(res);
/* 359:    */   }
/* 360:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.lib.partition.InputSampler
 * JD-Core Version:    0.7.0.1
 */