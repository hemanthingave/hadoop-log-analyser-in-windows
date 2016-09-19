/*   1:    */ package org.apache.hadoop.mapred.lib;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import java.util.Random;
/*   6:    */ import org.apache.commons.logging.Log;
/*   7:    */ import org.apache.commons.logging.LogFactory;
/*   8:    */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*   9:    */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*  10:    */ import org.apache.hadoop.mapred.InputFormat;
/*  11:    */ import org.apache.hadoop.mapred.InputSplit;
/*  12:    */ import org.apache.hadoop.mapred.JobConf;
/*  13:    */ import org.apache.hadoop.mapred.RecordReader;
/*  14:    */ import org.apache.hadoop.mapred.Reporter;
/*  15:    */ import org.apache.hadoop.mapreduce.Job;
/*  16:    */ 
/*  17:    */ @InterfaceAudience.Public
/*  18:    */ @InterfaceStability.Stable
/*  19:    */ public class InputSampler<K, V>
/*  20:    */   extends org.apache.hadoop.mapreduce.lib.partition.InputSampler<K, V>
/*  21:    */ {
/*  22: 41 */   private static final Log LOG = LogFactory.getLog(InputSampler.class);
/*  23:    */   
/*  24:    */   public InputSampler(JobConf conf)
/*  25:    */   {
/*  26: 44 */     super(conf);
/*  27:    */   }
/*  28:    */   
/*  29:    */   public static <K, V> void writePartitionFile(JobConf job, Sampler<K, V> sampler)
/*  30:    */     throws IOException, ClassNotFoundException, InterruptedException
/*  31:    */   {
/*  32: 49 */     writePartitionFile(new Job(job), sampler);
/*  33:    */   }
/*  34:    */   
/*  35:    */   public static abstract interface Sampler<K, V>
/*  36:    */     extends org.apache.hadoop.mapreduce.lib.partition.InputSampler.Sampler<K, V>
/*  37:    */   {
/*  38:    */     public abstract K[] getSample(InputFormat<K, V> paramInputFormat, JobConf paramJobConf)
/*  39:    */       throws IOException;
/*  40:    */   }
/*  41:    */   
/*  42:    */   public static class SplitSampler<K, V>
/*  43:    */     extends org.apache.hadoop.mapreduce.lib.partition.InputSampler.SplitSampler<K, V>
/*  44:    */     implements InputSampler.Sampler<K, V>
/*  45:    */   {
/*  46:    */     public SplitSampler(int numSamples)
/*  47:    */     {
/*  48: 78 */       this(numSamples, 2147483647);
/*  49:    */     }
/*  50:    */     
/*  51:    */     public SplitSampler(int numSamples, int maxSplitsSampled)
/*  52:    */     {
/*  53: 88 */       super(maxSplitsSampled);
/*  54:    */     }
/*  55:    */     
/*  56:    */     public K[] getSample(InputFormat<K, V> inf, JobConf job)
/*  57:    */       throws IOException
/*  58:    */     {
/*  59: 96 */       InputSplit[] splits = inf.getSplits(job, job.getNumMapTasks());
/*  60: 97 */       ArrayList<K> samples = new ArrayList(this.numSamples);
/*  61: 98 */       int splitsToSample = Math.min(this.maxSplitsSampled, splits.length);
/*  62: 99 */       int splitStep = splits.length / splitsToSample;
/*  63:100 */       int samplesPerSplit = this.numSamples / splitsToSample;
/*  64:101 */       long records = 0L;
/*  65:102 */       for (int i = 0; i < splitsToSample; i++)
/*  66:    */       {
/*  67:103 */         RecordReader<K, V> reader = inf.getRecordReader(splits[(i * splitStep)], job, Reporter.NULL);
/*  68:    */         
/*  69:105 */         K key = reader.createKey();
/*  70:106 */         V value = reader.createValue();
/*  71:107 */         while (reader.next(key, value))
/*  72:    */         {
/*  73:108 */           samples.add(key);
/*  74:109 */           key = reader.createKey();
/*  75:110 */           records += 1L;
/*  76:111 */           if ((i + 1) * samplesPerSplit <= records) {
/*  77:    */             break;
/*  78:    */           }
/*  79:    */         }
/*  80:115 */         reader.close();
/*  81:    */       }
/*  82:117 */       return (Object[])samples.toArray();
/*  83:    */     }
/*  84:    */   }
/*  85:    */   
/*  86:    */   public static class RandomSampler<K, V>
/*  87:    */     extends org.apache.hadoop.mapreduce.lib.partition.InputSampler.RandomSampler<K, V>
/*  88:    */     implements InputSampler.Sampler<K, V>
/*  89:    */   {
/*  90:    */     public RandomSampler(double freq, int numSamples)
/*  91:    */     {
/*  92:138 */       this(freq, numSamples, 2147483647);
/*  93:    */     }
/*  94:    */     
/*  95:    */     public RandomSampler(double freq, int numSamples, int maxSplitsSampled)
/*  96:    */     {
/*  97:149 */       super(numSamples, maxSplitsSampled);
/*  98:    */     }
/*  99:    */     
/* 100:    */     public K[] getSample(InputFormat<K, V> inf, JobConf job)
/* 101:    */       throws IOException
/* 102:    */     {
/* 103:160 */       InputSplit[] splits = inf.getSplits(job, job.getNumMapTasks());
/* 104:161 */       ArrayList<K> samples = new ArrayList(this.numSamples);
/* 105:162 */       int splitsToSample = Math.min(this.maxSplitsSampled, splits.length);
/* 106:    */       
/* 107:164 */       Random r = new Random();
/* 108:165 */       long seed = r.nextLong();
/* 109:166 */       r.setSeed(seed);
/* 110:167 */       InputSampler.LOG.debug("seed: " + seed);
/* 111:169 */       for (int i = 0; i < splits.length; i++)
/* 112:    */       {
/* 113:170 */         InputSplit tmp = splits[i];
/* 114:171 */         int j = r.nextInt(splits.length);
/* 115:172 */         splits[i] = splits[j];
/* 116:173 */         splits[j] = tmp;
/* 117:    */       }
/* 118:178 */       for (int i = 0; (i < splitsToSample) || ((i < splits.length) && (samples.size() < this.numSamples)); i++)
/* 119:    */       {
/* 120:180 */         RecordReader<K, V> reader = inf.getRecordReader(splits[i], job, Reporter.NULL);
/* 121:    */         
/* 122:182 */         K key = reader.createKey();
/* 123:183 */         V value = reader.createValue();
/* 124:184 */         while (reader.next(key, value)) {
/* 125:185 */           if (r.nextDouble() <= this.freq)
/* 126:    */           {
/* 127:186 */             if (samples.size() < this.numSamples)
/* 128:    */             {
/* 129:187 */               samples.add(key);
/* 130:    */             }
/* 131:    */             else
/* 132:    */             {
/* 133:193 */               int ind = r.nextInt(this.numSamples);
/* 134:194 */               if (ind != this.numSamples) {
/* 135:195 */                 samples.set(ind, key);
/* 136:    */               }
/* 137:197 */               this.freq *= (this.numSamples - 1) / this.numSamples;
/* 138:    */             }
/* 139:199 */             key = reader.createKey();
/* 140:    */           }
/* 141:    */         }
/* 142:202 */         reader.close();
/* 143:    */       }
/* 144:204 */       return (Object[])samples.toArray();
/* 145:    */     }
/* 146:    */   }
/* 147:    */   
/* 148:    */   public static class IntervalSampler<K, V>
/* 149:    */     extends org.apache.hadoop.mapreduce.lib.partition.InputSampler.IntervalSampler<K, V>
/* 150:    */     implements InputSampler.Sampler<K, V>
/* 151:    */   {
/* 152:    */     public IntervalSampler(double freq)
/* 153:    */     {
/* 154:221 */       this(freq, 2147483647);
/* 155:    */     }
/* 156:    */     
/* 157:    */     public IntervalSampler(double freq, int maxSplitsSampled)
/* 158:    */     {
/* 159:231 */       super(maxSplitsSampled);
/* 160:    */     }
/* 161:    */     
/* 162:    */     public K[] getSample(InputFormat<K, V> inf, JobConf job)
/* 163:    */       throws IOException
/* 164:    */     {
/* 165:241 */       InputSplit[] splits = inf.getSplits(job, job.getNumMapTasks());
/* 166:242 */       ArrayList<K> samples = new ArrayList();
/* 167:243 */       int splitsToSample = Math.min(this.maxSplitsSampled, splits.length);
/* 168:244 */       int splitStep = splits.length / splitsToSample;
/* 169:245 */       long records = 0L;
/* 170:246 */       long kept = 0L;
/* 171:247 */       for (int i = 0; i < splitsToSample; i++)
/* 172:    */       {
/* 173:248 */         RecordReader<K, V> reader = inf.getRecordReader(splits[(i * splitStep)], job, Reporter.NULL);
/* 174:    */         
/* 175:250 */         K key = reader.createKey();
/* 176:251 */         V value = reader.createValue();
/* 177:252 */         while (reader.next(key, value))
/* 178:    */         {
/* 179:253 */           records += 1L;
/* 180:254 */           if (kept / records < this.freq)
/* 181:    */           {
/* 182:255 */             kept += 1L;
/* 183:256 */             samples.add(key);
/* 184:257 */             key = reader.createKey();
/* 185:    */           }
/* 186:    */         }
/* 187:260 */         reader.close();
/* 188:    */       }
/* 189:262 */       return (Object[])samples.toArray();
/* 190:    */     }
/* 191:    */   }
/* 192:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.lib.InputSampler
 * JD-Core Version:    0.7.0.1
 */