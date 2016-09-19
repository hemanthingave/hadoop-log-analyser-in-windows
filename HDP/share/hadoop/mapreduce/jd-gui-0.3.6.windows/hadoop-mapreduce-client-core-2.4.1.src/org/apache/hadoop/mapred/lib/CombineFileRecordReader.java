/*   1:    */ package org.apache.hadoop.mapred.lib;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.lang.reflect.Constructor;
/*   5:    */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*   6:    */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*   7:    */ import org.apache.hadoop.conf.Configuration;
/*   8:    */ import org.apache.hadoop.fs.FileSystem;
/*   9:    */ import org.apache.hadoop.fs.Path;
/*  10:    */ import org.apache.hadoop.mapred.JobConf;
/*  11:    */ import org.apache.hadoop.mapred.RecordReader;
/*  12:    */ import org.apache.hadoop.mapred.Reporter;
/*  13:    */ 
/*  14:    */ @InterfaceAudience.Public
/*  15:    */ @InterfaceStability.Stable
/*  16:    */ public class CombineFileRecordReader<K, V>
/*  17:    */   implements RecordReader<K, V>
/*  18:    */ {
/*  19: 43 */   static final Class[] constructorSignature = { CombineFileSplit.class, Configuration.class, Reporter.class, Integer.class };
/*  20:    */   protected CombineFileSplit split;
/*  21:    */   protected JobConf jc;
/*  22:    */   protected Reporter reporter;
/*  23:    */   protected Class<RecordReader<K, V>> rrClass;
/*  24:    */   protected Constructor<RecordReader<K, V>> rrConstructor;
/*  25:    */   protected FileSystem fs;
/*  26:    */   protected int idx;
/*  27:    */   protected long progress;
/*  28:    */   protected RecordReader<K, V> curReader;
/*  29:    */   
/*  30:    */   public boolean next(K key, V value)
/*  31:    */     throws IOException
/*  32:    */   {
/*  33: 62 */     while ((this.curReader == null) || (!this.curReader.next(key, value))) {
/*  34: 63 */       if (!initNextRecordReader()) {
/*  35: 64 */         return false;
/*  36:    */       }
/*  37:    */     }
/*  38: 67 */     return true;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public K createKey()
/*  42:    */   {
/*  43: 71 */     return this.curReader.createKey();
/*  44:    */   }
/*  45:    */   
/*  46:    */   public V createValue()
/*  47:    */   {
/*  48: 75 */     return this.curReader.createValue();
/*  49:    */   }
/*  50:    */   
/*  51:    */   public long getPos()
/*  52:    */     throws IOException
/*  53:    */   {
/*  54: 82 */     return this.progress;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public void close()
/*  58:    */     throws IOException
/*  59:    */   {
/*  60: 86 */     if (this.curReader != null)
/*  61:    */     {
/*  62: 87 */       this.curReader.close();
/*  63: 88 */       this.curReader = null;
/*  64:    */     }
/*  65:    */   }
/*  66:    */   
/*  67:    */   public float getProgress()
/*  68:    */     throws IOException
/*  69:    */   {
/*  70: 96 */     return Math.min(1.0F, (float)this.progress / (float)this.split.getLength());
/*  71:    */   }
/*  72:    */   
/*  73:    */   public CombineFileRecordReader(JobConf job, CombineFileSplit split, Reporter reporter, Class<RecordReader<K, V>> rrClass)
/*  74:    */     throws IOException
/*  75:    */   {
/*  76:107 */     this.split = split;
/*  77:108 */     this.jc = job;
/*  78:109 */     this.rrClass = rrClass;
/*  79:110 */     this.reporter = reporter;
/*  80:111 */     this.idx = 0;
/*  81:112 */     this.curReader = null;
/*  82:113 */     this.progress = 0L;
/*  83:    */     try
/*  84:    */     {
/*  85:116 */       this.rrConstructor = rrClass.getDeclaredConstructor(constructorSignature);
/*  86:117 */       this.rrConstructor.setAccessible(true);
/*  87:    */     }
/*  88:    */     catch (Exception e)
/*  89:    */     {
/*  90:119 */       throw new RuntimeException(rrClass.getName() + " does not have valid constructor", e);
/*  91:    */     }
/*  92:122 */     initNextRecordReader();
/*  93:    */   }
/*  94:    */   
/*  95:    */   protected boolean initNextRecordReader()
/*  96:    */     throws IOException
/*  97:    */   {
/*  98:130 */     if (this.curReader != null)
/*  99:    */     {
/* 100:131 */       this.curReader.close();
/* 101:132 */       this.curReader = null;
/* 102:133 */       if (this.idx > 0) {
/* 103:134 */         this.progress += this.split.getLength(this.idx - 1);
/* 104:    */       }
/* 105:    */     }
/* 106:139 */     if (this.idx == this.split.getNumPaths()) {
/* 107:140 */       return false;
/* 108:    */     }
/* 109:143 */     this.reporter.progress();
/* 110:    */     try
/* 111:    */     {
/* 112:147 */       this.curReader = ((RecordReader)this.rrConstructor.newInstance(new Object[] { this.split, this.jc, this.reporter, Integer.valueOf(this.idx) }));
/* 113:    */       
/* 114:    */ 
/* 115:    */ 
/* 116:151 */       this.jc.set("mapreduce.map.input.file", this.split.getPath(this.idx).toString());
/* 117:152 */       this.jc.setLong("mapreduce.map.input.start", this.split.getOffset(this.idx));
/* 118:153 */       this.jc.setLong("mapreduce.map.input.length", this.split.getLength(this.idx));
/* 119:    */     }
/* 120:    */     catch (Exception e)
/* 121:    */     {
/* 122:155 */       throw new RuntimeException(e);
/* 123:    */     }
/* 124:157 */     this.idx += 1;
/* 125:158 */     return true;
/* 126:    */   }
/* 127:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.lib.CombineFileRecordReader
 * JD-Core Version:    0.7.0.1
 */