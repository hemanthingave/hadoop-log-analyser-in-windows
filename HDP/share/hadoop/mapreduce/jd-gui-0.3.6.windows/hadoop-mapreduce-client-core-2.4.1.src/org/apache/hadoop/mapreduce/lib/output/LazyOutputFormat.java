/*   1:    */ package org.apache.hadoop.mapreduce.lib.output;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*   5:    */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*   6:    */ import org.apache.hadoop.conf.Configuration;
/*   7:    */ import org.apache.hadoop.mapreduce.Job;
/*   8:    */ import org.apache.hadoop.mapreduce.JobContext;
/*   9:    */ import org.apache.hadoop.mapreduce.OutputCommitter;
/*  10:    */ import org.apache.hadoop.mapreduce.OutputFormat;
/*  11:    */ import org.apache.hadoop.mapreduce.RecordWriter;
/*  12:    */ import org.apache.hadoop.mapreduce.TaskAttemptContext;
/*  13:    */ import org.apache.hadoop.util.ReflectionUtils;
/*  14:    */ 
/*  15:    */ @InterfaceAudience.Public
/*  16:    */ @InterfaceStability.Stable
/*  17:    */ public class LazyOutputFormat<K, V>
/*  18:    */   extends FilterOutputFormat<K, V>
/*  19:    */ {
/*  20: 43 */   public static String OUTPUT_FORMAT = "mapreduce.output.lazyoutputformat.outputformat";
/*  21:    */   
/*  22:    */   public static void setOutputFormatClass(Job job, Class<? extends OutputFormat> theClass)
/*  23:    */   {
/*  24: 53 */     job.setOutputFormatClass(LazyOutputFormat.class);
/*  25: 54 */     job.getConfiguration().setClass(OUTPUT_FORMAT, theClass, OutputFormat.class);
/*  26:    */   }
/*  27:    */   
/*  28:    */   private void getBaseOutputFormat(Configuration conf)
/*  29:    */     throws IOException
/*  30:    */   {
/*  31: 61 */     this.baseOut = ((OutputFormat)ReflectionUtils.newInstance(conf.getClass(OUTPUT_FORMAT, null), conf));
/*  32: 63 */     if (this.baseOut == null) {
/*  33: 64 */       throw new IOException("Output Format not set for LazyOutputFormat");
/*  34:    */     }
/*  35:    */   }
/*  36:    */   
/*  37:    */   public RecordWriter<K, V> getRecordWriter(TaskAttemptContext context)
/*  38:    */     throws IOException, InterruptedException
/*  39:    */   {
/*  40: 71 */     if (this.baseOut == null) {
/*  41: 72 */       getBaseOutputFormat(context.getConfiguration());
/*  42:    */     }
/*  43: 74 */     return new LazyRecordWriter(this.baseOut, context);
/*  44:    */   }
/*  45:    */   
/*  46:    */   public void checkOutputSpecs(JobContext context)
/*  47:    */     throws IOException, InterruptedException
/*  48:    */   {
/*  49: 80 */     if (this.baseOut == null) {
/*  50: 81 */       getBaseOutputFormat(context.getConfiguration());
/*  51:    */     }
/*  52: 83 */     super.checkOutputSpecs(context);
/*  53:    */   }
/*  54:    */   
/*  55:    */   public OutputCommitter getOutputCommitter(TaskAttemptContext context)
/*  56:    */     throws IOException, InterruptedException
/*  57:    */   {
/*  58: 89 */     if (this.baseOut == null) {
/*  59: 90 */       getBaseOutputFormat(context.getConfiguration());
/*  60:    */     }
/*  61: 92 */     return super.getOutputCommitter(context);
/*  62:    */   }
/*  63:    */   
/*  64:    */   private static class LazyRecordWriter<K, V>
/*  65:    */     extends FilterOutputFormat.FilterRecordWriter<K, V>
/*  66:    */   {
/*  67:    */     final OutputFormat<K, V> outputFormat;
/*  68:    */     final TaskAttemptContext taskContext;
/*  69:    */     
/*  70:    */     public LazyRecordWriter(OutputFormat<K, V> out, TaskAttemptContext taskContext)
/*  71:    */       throws IOException, InterruptedException
/*  72:    */     {
/*  73:106 */       this.outputFormat = out;
/*  74:107 */       this.taskContext = taskContext;
/*  75:    */     }
/*  76:    */     
/*  77:    */     public void write(K key, V value)
/*  78:    */       throws IOException, InterruptedException
/*  79:    */     {
/*  80:112 */       if (this.rawWriter == null) {
/*  81:113 */         this.rawWriter = this.outputFormat.getRecordWriter(this.taskContext);
/*  82:    */       }
/*  83:115 */       this.rawWriter.write(key, value);
/*  84:    */     }
/*  85:    */     
/*  86:    */     public void close(TaskAttemptContext context)
/*  87:    */       throws IOException, InterruptedException
/*  88:    */     {
/*  89:121 */       if (this.rawWriter != null) {
/*  90:122 */         this.rawWriter.close(context);
/*  91:    */       }
/*  92:    */     }
/*  93:    */   }
/*  94:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.lib.output.LazyOutputFormat
 * JD-Core Version:    0.7.0.1
 */