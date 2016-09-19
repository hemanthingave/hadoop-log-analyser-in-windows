/*   1:    */ package org.apache.hadoop.mapred.lib;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*   5:    */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*   6:    */ import org.apache.hadoop.fs.FileSystem;
/*   7:    */ import org.apache.hadoop.mapred.JobConf;
/*   8:    */ import org.apache.hadoop.mapred.OutputFormat;
/*   9:    */ import org.apache.hadoop.mapred.RecordWriter;
/*  10:    */ import org.apache.hadoop.mapred.Reporter;
/*  11:    */ import org.apache.hadoop.util.Progressable;
/*  12:    */ import org.apache.hadoop.util.ReflectionUtils;
/*  13:    */ 
/*  14:    */ @InterfaceAudience.Public
/*  15:    */ @InterfaceStability.Stable
/*  16:    */ public class LazyOutputFormat<K, V>
/*  17:    */   extends FilterOutputFormat<K, V>
/*  18:    */ {
/*  19:    */   public static void setOutputFormatClass(JobConf job, Class<? extends OutputFormat> theClass)
/*  20:    */   {
/*  21: 47 */     job.setOutputFormat(LazyOutputFormat.class);
/*  22: 48 */     job.setClass("mapreduce.output.lazyoutputformat.outputformat", theClass, OutputFormat.class);
/*  23:    */   }
/*  24:    */   
/*  25:    */   public RecordWriter<K, V> getRecordWriter(FileSystem ignored, JobConf job, String name, Progressable progress)
/*  26:    */     throws IOException
/*  27:    */   {
/*  28: 54 */     if (this.baseOut == null) {
/*  29: 55 */       getBaseOutputFormat(job);
/*  30:    */     }
/*  31: 57 */     return new LazyRecordWriter(job, this.baseOut, name, progress);
/*  32:    */   }
/*  33:    */   
/*  34:    */   public void checkOutputSpecs(FileSystem ignored, JobConf job)
/*  35:    */     throws IOException
/*  36:    */   {
/*  37: 63 */     if (this.baseOut == null) {
/*  38: 64 */       getBaseOutputFormat(job);
/*  39:    */     }
/*  40: 66 */     super.checkOutputSpecs(ignored, job);
/*  41:    */   }
/*  42:    */   
/*  43:    */   private void getBaseOutputFormat(JobConf job)
/*  44:    */     throws IOException
/*  45:    */   {
/*  46: 71 */     this.baseOut = ((OutputFormat)ReflectionUtils.newInstance(job.getClass("mapreduce.output.lazyoutputformat.outputformat", null, OutputFormat.class), job));
/*  47: 74 */     if (this.baseOut == null) {
/*  48: 75 */       throw new IOException("Ouput format not set for LazyOutputFormat");
/*  49:    */     }
/*  50:    */   }
/*  51:    */   
/*  52:    */   private static class LazyRecordWriter<K, V>
/*  53:    */     extends FilterOutputFormat.FilterRecordWriter<K, V>
/*  54:    */   {
/*  55:    */     final OutputFormat of;
/*  56:    */     final String name;
/*  57:    */     final Progressable progress;
/*  58:    */     final JobConf job;
/*  59:    */     
/*  60:    */     public LazyRecordWriter(JobConf job, OutputFormat of, String name, Progressable progress)
/*  61:    */       throws IOException
/*  62:    */     {
/*  63: 93 */       this.of = of;
/*  64: 94 */       this.job = job;
/*  65: 95 */       this.name = name;
/*  66: 96 */       this.progress = progress;
/*  67:    */     }
/*  68:    */     
/*  69:    */     public void close(Reporter reporter)
/*  70:    */       throws IOException
/*  71:    */     {
/*  72:101 */       if (this.rawWriter != null) {
/*  73:102 */         this.rawWriter.close(reporter);
/*  74:    */       }
/*  75:    */     }
/*  76:    */     
/*  77:    */     public void write(K key, V value)
/*  78:    */       throws IOException
/*  79:    */     {
/*  80:108 */       if (this.rawWriter == null) {
/*  81:109 */         createRecordWriter();
/*  82:    */       }
/*  83:111 */       super.write(key, value);
/*  84:    */     }
/*  85:    */     
/*  86:    */     private void createRecordWriter()
/*  87:    */       throws IOException
/*  88:    */     {
/*  89:116 */       FileSystem fs = FileSystem.get(this.job);
/*  90:117 */       this.rawWriter = this.of.getRecordWriter(fs, this.job, this.name, this.progress);
/*  91:    */     }
/*  92:    */   }
/*  93:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.lib.LazyOutputFormat
 * JD-Core Version:    0.7.0.1
 */