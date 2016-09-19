/*   1:    */ package org.apache.hadoop.mapred;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*   5:    */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*   6:    */ import org.apache.hadoop.fs.FileSystem;
/*   7:    */ import org.apache.hadoop.fs.Path;
/*   8:    */ import org.apache.hadoop.io.BytesWritable;
/*   9:    */ import org.apache.hadoop.io.SequenceFile;
/*  10:    */ import org.apache.hadoop.io.SequenceFile.CompressionType;
/*  11:    */ import org.apache.hadoop.io.SequenceFile.Writer;
/*  12:    */ import org.apache.hadoop.io.Writable;
/*  13:    */ import org.apache.hadoop.io.WritableComparable;
/*  14:    */ import org.apache.hadoop.io.compress.CompressionCodec;
/*  15:    */ import org.apache.hadoop.io.compress.DefaultCodec;
/*  16:    */ import org.apache.hadoop.util.Progressable;
/*  17:    */ import org.apache.hadoop.util.ReflectionUtils;
/*  18:    */ 
/*  19:    */ @InterfaceAudience.Public
/*  20:    */ @InterfaceStability.Stable
/*  21:    */ public class SequenceFileAsBinaryOutputFormat
/*  22:    */   extends SequenceFileOutputFormat<BytesWritable, BytesWritable>
/*  23:    */ {
/*  24:    */   protected static class WritableValueBytes
/*  25:    */     extends org.apache.hadoop.mapreduce.lib.output.SequenceFileAsBinaryOutputFormat.WritableValueBytes
/*  26:    */   {
/*  27:    */     public WritableValueBytes() {}
/*  28:    */     
/*  29:    */     public WritableValueBytes(BytesWritable value)
/*  30:    */     {
/*  31: 55 */       super();
/*  32:    */     }
/*  33:    */   }
/*  34:    */   
/*  35:    */   public static void setSequenceFileOutputKeyClass(JobConf conf, Class<?> theClass)
/*  36:    */   {
/*  37: 69 */     conf.setClass(org.apache.hadoop.mapreduce.lib.output.SequenceFileAsBinaryOutputFormat.KEY_CLASS, theClass, Object.class);
/*  38:    */   }
/*  39:    */   
/*  40:    */   public static void setSequenceFileOutputValueClass(JobConf conf, Class<?> theClass)
/*  41:    */   {
/*  42: 83 */     conf.setClass(org.apache.hadoop.mapreduce.lib.output.SequenceFileAsBinaryOutputFormat.VALUE_CLASS, theClass, Object.class);
/*  43:    */   }
/*  44:    */   
/*  45:    */   public static Class<? extends WritableComparable> getSequenceFileOutputKeyClass(JobConf conf)
/*  46:    */   {
/*  47: 93 */     return conf.getClass(org.apache.hadoop.mapreduce.lib.output.SequenceFileAsBinaryOutputFormat.KEY_CLASS, conf.getOutputKeyClass().asSubclass(WritableComparable.class), WritableComparable.class);
/*  48:    */   }
/*  49:    */   
/*  50:    */   public static Class<? extends Writable> getSequenceFileOutputValueClass(JobConf conf)
/*  51:    */   {
/*  52:105 */     return conf.getClass(org.apache.hadoop.mapreduce.lib.output.SequenceFileAsBinaryOutputFormat.VALUE_CLASS, conf.getOutputValueClass().asSubclass(Writable.class), Writable.class);
/*  53:    */   }
/*  54:    */   
/*  55:    */   public RecordWriter<BytesWritable, BytesWritable> getRecordWriter(FileSystem ignored, JobConf job, String name, Progressable progress)
/*  56:    */     throws IOException
/*  57:    */   {
/*  58:116 */     Path file = FileOutputFormat.getTaskOutputPath(job, name);
/*  59:    */     
/*  60:118 */     FileSystem fs = file.getFileSystem(job);
/*  61:119 */     CompressionCodec codec = null;
/*  62:120 */     SequenceFile.CompressionType compressionType = SequenceFile.CompressionType.NONE;
/*  63:121 */     if (getCompressOutput(job))
/*  64:    */     {
/*  65:123 */       compressionType = getOutputCompressionType(job);
/*  66:    */       
/*  67:    */ 
/*  68:126 */       Class<? extends CompressionCodec> codecClass = getOutputCompressorClass(job, DefaultCodec.class);
/*  69:    */       
/*  70:128 */       codec = (CompressionCodec)ReflectionUtils.newInstance(codecClass, job);
/*  71:    */     }
/*  72:130 */     final SequenceFile.Writer out = SequenceFile.createWriter(fs, job, file, getSequenceFileOutputKeyClass(job), getSequenceFileOutputValueClass(job), compressionType, codec, progress);
/*  73:    */     
/*  74:    */ 
/*  75:    */ 
/*  76:    */ 
/*  77:    */ 
/*  78:    */ 
/*  79:    */ 
/*  80:138 */     new RecordWriter()
/*  81:    */     {
/*  82:140 */       private SequenceFileAsBinaryOutputFormat.WritableValueBytes wvaluebytes = new SequenceFileAsBinaryOutputFormat.WritableValueBytes();
/*  83:    */       
/*  84:    */       public void write(BytesWritable bkey, BytesWritable bvalue)
/*  85:    */         throws IOException
/*  86:    */       {
/*  87:145 */         this.wvaluebytes.reset(bvalue);
/*  88:146 */         out.appendRaw(bkey.getBytes(), 0, bkey.getLength(), this.wvaluebytes);
/*  89:147 */         this.wvaluebytes.reset(null);
/*  90:    */       }
/*  91:    */       
/*  92:    */       public void close(Reporter reporter)
/*  93:    */         throws IOException
/*  94:    */       {
/*  95:151 */         out.close();
/*  96:    */       }
/*  97:    */     };
/*  98:    */   }
/*  99:    */   
/* 100:    */   public void checkOutputSpecs(FileSystem ignored, JobConf job)
/* 101:    */     throws IOException
/* 102:    */   {
/* 103:161 */     super.checkOutputSpecs(ignored, job);
/* 104:162 */     if ((getCompressOutput(job)) && (getOutputCompressionType(job) == SequenceFile.CompressionType.RECORD)) {
/* 105:164 */       throw new InvalidJobConfException("SequenceFileAsBinaryOutputFormat doesn't support Record Compression");
/* 106:    */     }
/* 107:    */   }
/* 108:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.SequenceFileAsBinaryOutputFormat
 * JD-Core Version:    0.7.0.1
 */