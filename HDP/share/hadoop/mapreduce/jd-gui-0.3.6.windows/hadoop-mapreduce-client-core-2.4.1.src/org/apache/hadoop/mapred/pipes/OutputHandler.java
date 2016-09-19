/*   1:    */ package org.apache.hadoop.mapred.pipes;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.util.HashMap;
/*   5:    */ import java.util.Map;
/*   6:    */ import org.apache.hadoop.io.FloatWritable;
/*   7:    */ import org.apache.hadoop.io.NullWritable;
/*   8:    */ import org.apache.hadoop.io.Writable;
/*   9:    */ import org.apache.hadoop.io.WritableComparable;
/*  10:    */ import org.apache.hadoop.mapred.Counters.Counter;
/*  11:    */ import org.apache.hadoop.mapred.OutputCollector;
/*  12:    */ import org.apache.hadoop.mapred.RecordReader;
/*  13:    */ import org.apache.hadoop.mapred.Reporter;
/*  14:    */ 
/*  15:    */ class OutputHandler<K extends WritableComparable, V extends Writable>
/*  16:    */   implements UpwardProtocol<K, V>
/*  17:    */ {
/*  18:    */   private Reporter reporter;
/*  19:    */   private OutputCollector<K, V> collector;
/*  20: 43 */   private float progressValue = 0.0F;
/*  21: 44 */   private boolean done = false;
/*  22: 46 */   private Throwable exception = null;
/*  23: 47 */   RecordReader<FloatWritable, NullWritable> recordReader = null;
/*  24: 48 */   private Map<Integer, Counters.Counter> registeredCounters = new HashMap();
/*  25: 51 */   private String expectedDigest = null;
/*  26: 52 */   private boolean digestReceived = false;
/*  27:    */   
/*  28:    */   public OutputHandler(OutputCollector<K, V> collector, Reporter reporter, RecordReader<FloatWritable, NullWritable> recordReader, String expectedDigest)
/*  29:    */   {
/*  30: 61 */     this.reporter = reporter;
/*  31: 62 */     this.collector = collector;
/*  32: 63 */     this.recordReader = recordReader;
/*  33: 64 */     this.expectedDigest = expectedDigest;
/*  34:    */   }
/*  35:    */   
/*  36:    */   public void output(K key, V value)
/*  37:    */     throws IOException
/*  38:    */   {
/*  39: 71 */     this.collector.collect(key, value);
/*  40:    */   }
/*  41:    */   
/*  42:    */   public void partitionedOutput(int reduce, K key, V value)
/*  43:    */     throws IOException
/*  44:    */   {
/*  45: 79 */     PipesPartitioner.setNextPartition(reduce);
/*  46: 80 */     this.collector.collect(key, value);
/*  47:    */   }
/*  48:    */   
/*  49:    */   public void status(String msg)
/*  50:    */   {
/*  51: 87 */     this.reporter.setStatus(msg);
/*  52:    */   }
/*  53:    */   
/*  54: 90 */   private FloatWritable progressKey = new FloatWritable(0.0F);
/*  55: 91 */   private NullWritable nullValue = NullWritable.get();
/*  56:    */   
/*  57:    */   public void progress(float progress)
/*  58:    */     throws IOException
/*  59:    */   {
/*  60: 96 */     this.progressValue = progress;
/*  61: 97 */     this.reporter.progress();
/*  62: 99 */     if (this.recordReader != null)
/*  63:    */     {
/*  64:100 */       this.progressKey.set(progress);
/*  65:101 */       this.recordReader.next(this.progressKey, this.nullValue);
/*  66:    */     }
/*  67:    */   }
/*  68:    */   
/*  69:    */   public void done()
/*  70:    */     throws IOException
/*  71:    */   {
/*  72:109 */     synchronized (this)
/*  73:    */     {
/*  74:110 */       this.done = true;
/*  75:111 */       notify();
/*  76:    */     }
/*  77:    */   }
/*  78:    */   
/*  79:    */   public float getProgress()
/*  80:    */   {
/*  81:120 */     return this.progressValue;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public void failed(Throwable e)
/*  85:    */   {
/*  86:127 */     synchronized (this)
/*  87:    */     {
/*  88:128 */       this.exception = e;
/*  89:129 */       notify();
/*  90:    */     }
/*  91:    */   }
/*  92:    */   
/*  93:    */   public synchronized boolean waitForFinish()
/*  94:    */     throws Throwable
/*  95:    */   {
/*  96:139 */     while ((!this.done) && (this.exception == null)) {
/*  97:140 */       wait();
/*  98:    */     }
/*  99:142 */     if (this.exception != null) {
/* 100:143 */       throw this.exception;
/* 101:    */     }
/* 102:145 */     return this.done;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public void registerCounter(int id, String group, String name)
/* 106:    */     throws IOException
/* 107:    */   {
/* 108:149 */     Counters.Counter counter = this.reporter.getCounter(group, name);
/* 109:150 */     this.registeredCounters.put(Integer.valueOf(id), counter);
/* 110:    */   }
/* 111:    */   
/* 112:    */   public void incrementCounter(int id, long amount)
/* 113:    */     throws IOException
/* 114:    */   {
/* 115:154 */     if (id < this.registeredCounters.size())
/* 116:    */     {
/* 117:155 */       Counters.Counter counter = (Counters.Counter)this.registeredCounters.get(Integer.valueOf(id));
/* 118:156 */       counter.increment(amount);
/* 119:    */     }
/* 120:    */     else
/* 121:    */     {
/* 122:158 */       throw new IOException("Invalid counter with id: " + id);
/* 123:    */     }
/* 124:    */   }
/* 125:    */   
/* 126:    */   public synchronized boolean authenticate(String digest)
/* 127:    */     throws IOException
/* 128:    */   {
/* 129:163 */     boolean success = true;
/* 130:164 */     if (!this.expectedDigest.equals(digest))
/* 131:    */     {
/* 132:165 */       this.exception = new IOException("Authentication Failed: Expected digest=" + this.expectedDigest + ", received=" + this.digestReceived);
/* 133:    */       
/* 134:167 */       success = false;
/* 135:    */     }
/* 136:169 */     this.digestReceived = true;
/* 137:170 */     notify();
/* 138:171 */     return success;
/* 139:    */   }
/* 140:    */   
/* 141:    */   synchronized void waitForAuthentication()
/* 142:    */     throws IOException, InterruptedException
/* 143:    */   {
/* 144:182 */     while ((!this.digestReceived) && (this.exception == null)) {
/* 145:183 */       wait();
/* 146:    */     }
/* 147:185 */     if (this.exception != null) {
/* 148:186 */       throw new IOException(this.exception.getMessage());
/* 149:    */     }
/* 150:    */   }
/* 151:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.pipes.OutputHandler
 * JD-Core Version:    0.7.0.1
 */