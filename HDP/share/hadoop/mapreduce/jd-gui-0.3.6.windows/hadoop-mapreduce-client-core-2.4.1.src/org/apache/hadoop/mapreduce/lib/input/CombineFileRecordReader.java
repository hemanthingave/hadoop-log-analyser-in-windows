/*   1:    */ package org.apache.hadoop.mapreduce.lib.input;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.lang.reflect.Constructor;
/*   5:    */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*   6:    */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*   7:    */ import org.apache.hadoop.conf.Configuration;
/*   8:    */ import org.apache.hadoop.fs.FileSystem;
/*   9:    */ import org.apache.hadoop.fs.Path;
/*  10:    */ import org.apache.hadoop.mapreduce.InputSplit;
/*  11:    */ import org.apache.hadoop.mapreduce.RecordReader;
/*  12:    */ import org.apache.hadoop.mapreduce.TaskAttemptContext;
/*  13:    */ 
/*  14:    */ @InterfaceAudience.Public
/*  15:    */ @InterfaceStability.Stable
/*  16:    */ public class CombineFileRecordReader<K, V>
/*  17:    */   extends RecordReader<K, V>
/*  18:    */ {
/*  19: 43 */   static final Class[] constructorSignature = { CombineFileSplit.class, TaskAttemptContext.class, Integer.class };
/*  20:    */   protected CombineFileSplit split;
/*  21:    */   protected Class<? extends RecordReader<K, V>> rrClass;
/*  22:    */   protected Constructor<? extends RecordReader<K, V>> rrConstructor;
/*  23:    */   protected FileSystem fs;
/*  24:    */   protected TaskAttemptContext context;
/*  25:    */   protected int idx;
/*  26:    */   protected long progress;
/*  27:    */   protected RecordReader<K, V> curReader;
/*  28:    */   
/*  29:    */   public void initialize(InputSplit split, TaskAttemptContext context)
/*  30:    */     throws IOException, InterruptedException
/*  31:    */   {
/*  32: 60 */     this.split = ((CombineFileSplit)split);
/*  33: 61 */     this.context = context;
/*  34: 62 */     if (null != this.curReader) {
/*  35: 63 */       this.curReader.initialize(split, context);
/*  36:    */     }
/*  37:    */   }
/*  38:    */   
/*  39:    */   public boolean nextKeyValue()
/*  40:    */     throws IOException, InterruptedException
/*  41:    */   {
/*  42: 69 */     while ((this.curReader == null) || (!this.curReader.nextKeyValue())) {
/*  43: 70 */       if (!initNextRecordReader()) {
/*  44: 71 */         return false;
/*  45:    */       }
/*  46:    */     }
/*  47: 74 */     return true;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public K getCurrentKey()
/*  51:    */     throws IOException, InterruptedException
/*  52:    */   {
/*  53: 78 */     return this.curReader.getCurrentKey();
/*  54:    */   }
/*  55:    */   
/*  56:    */   public V getCurrentValue()
/*  57:    */     throws IOException, InterruptedException
/*  58:    */   {
/*  59: 82 */     return this.curReader.getCurrentValue();
/*  60:    */   }
/*  61:    */   
/*  62:    */   public void close()
/*  63:    */     throws IOException
/*  64:    */   {
/*  65: 86 */     if (this.curReader != null)
/*  66:    */     {
/*  67: 87 */       this.curReader.close();
/*  68: 88 */       this.curReader = null;
/*  69:    */     }
/*  70:    */   }
/*  71:    */   
/*  72:    */   public float getProgress()
/*  73:    */     throws IOException, InterruptedException
/*  74:    */   {
/*  75: 96 */     long subprogress = 0L;
/*  76: 97 */     if (null != this.curReader) {
/*  77: 99 */       subprogress = (this.curReader.getProgress() * (float)this.split.getLength(this.idx - 1));
/*  78:    */     }
/*  79:101 */     return Math.min(1.0F, (float)(this.progress + subprogress) / (float)this.split.getLength());
/*  80:    */   }
/*  81:    */   
/*  82:    */   public CombineFileRecordReader(CombineFileSplit split, TaskAttemptContext context, Class<? extends RecordReader<K, V>> rrClass)
/*  83:    */     throws IOException
/*  84:    */   {
/*  85:112 */     this.split = split;
/*  86:113 */     this.context = context;
/*  87:114 */     this.rrClass = rrClass;
/*  88:115 */     this.idx = 0;
/*  89:116 */     this.curReader = null;
/*  90:117 */     this.progress = 0L;
/*  91:    */     try
/*  92:    */     {
/*  93:120 */       this.rrConstructor = rrClass.getDeclaredConstructor(constructorSignature);
/*  94:121 */       this.rrConstructor.setAccessible(true);
/*  95:    */     }
/*  96:    */     catch (Exception e)
/*  97:    */     {
/*  98:123 */       throw new RuntimeException(rrClass.getName() + " does not have valid constructor", e);
/*  99:    */     }
/* 100:126 */     initNextRecordReader();
/* 101:    */   }
/* 102:    */   
/* 103:    */   protected boolean initNextRecordReader()
/* 104:    */     throws IOException
/* 105:    */   {
/* 106:134 */     if (this.curReader != null)
/* 107:    */     {
/* 108:135 */       this.curReader.close();
/* 109:136 */       this.curReader = null;
/* 110:137 */       if (this.idx > 0) {
/* 111:138 */         this.progress += this.split.getLength(this.idx - 1);
/* 112:    */       }
/* 113:    */     }
/* 114:143 */     if (this.idx == this.split.getNumPaths()) {
/* 115:144 */       return false;
/* 116:    */     }
/* 117:147 */     this.context.progress();
/* 118:    */     try
/* 119:    */     {
/* 120:151 */       Configuration conf = this.context.getConfiguration();
/* 121:    */       
/* 122:153 */       conf.set("mapreduce.map.input.file", this.split.getPath(this.idx).toString());
/* 123:154 */       conf.setLong("mapreduce.map.input.start", this.split.getOffset(this.idx));
/* 124:155 */       conf.setLong("mapreduce.map.input.length", this.split.getLength(this.idx));
/* 125:    */       
/* 126:157 */       this.curReader = ((RecordReader)this.rrConstructor.newInstance(new Object[] { this.split, this.context, Integer.valueOf(this.idx) }));
/* 127:160 */       if (this.idx > 0) {
/* 128:163 */         this.curReader.initialize(this.split, this.context);
/* 129:    */       }
/* 130:    */     }
/* 131:    */     catch (Exception e)
/* 132:    */     {
/* 133:166 */       throw new RuntimeException(e);
/* 134:    */     }
/* 135:168 */     this.idx += 1;
/* 136:169 */     return true;
/* 137:    */   }
/* 138:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.lib.input.CombineFileRecordReader
 * JD-Core Version:    0.7.0.1
 */