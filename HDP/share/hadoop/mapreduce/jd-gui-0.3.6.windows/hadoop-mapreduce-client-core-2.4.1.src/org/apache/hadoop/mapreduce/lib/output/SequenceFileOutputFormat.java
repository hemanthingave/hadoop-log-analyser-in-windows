/*   1:    */ package org.apache.hadoop.mapreduce.lib.output;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*   5:    */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*   6:    */ import org.apache.hadoop.conf.Configuration;
/*   7:    */ import org.apache.hadoop.fs.FileSystem;
/*   8:    */ import org.apache.hadoop.fs.Path;
/*   9:    */ import org.apache.hadoop.io.SequenceFile;
/*  10:    */ import org.apache.hadoop.io.SequenceFile.CompressionType;
/*  11:    */ import org.apache.hadoop.io.SequenceFile.Writer;
/*  12:    */ import org.apache.hadoop.io.compress.CompressionCodec;
/*  13:    */ import org.apache.hadoop.io.compress.DefaultCodec;
/*  14:    */ import org.apache.hadoop.mapreduce.Job;
/*  15:    */ import org.apache.hadoop.mapreduce.JobContext;
/*  16:    */ import org.apache.hadoop.mapreduce.RecordWriter;
/*  17:    */ import org.apache.hadoop.mapreduce.TaskAttemptContext;
/*  18:    */ import org.apache.hadoop.util.ReflectionUtils;
/*  19:    */ 
/*  20:    */ @InterfaceAudience.Public
/*  21:    */ @InterfaceStability.Stable
/*  22:    */ public class SequenceFileOutputFormat<K, V>
/*  23:    */   extends FileOutputFormat<K, V>
/*  24:    */ {
/*  25:    */   protected SequenceFile.Writer getSequenceWriter(TaskAttemptContext context, Class<?> keyClass, Class<?> valueClass)
/*  26:    */     throws IOException
/*  27:    */   {
/*  28: 48 */     Configuration conf = context.getConfiguration();
/*  29:    */     
/*  30: 50 */     CompressionCodec codec = null;
/*  31: 51 */     SequenceFile.CompressionType compressionType = SequenceFile.CompressionType.NONE;
/*  32: 52 */     if (getCompressOutput(context))
/*  33:    */     {
/*  34: 54 */       compressionType = getOutputCompressionType(context);
/*  35:    */       
/*  36: 56 */       Class<?> codecClass = getOutputCompressorClass(context, DefaultCodec.class);
/*  37:    */       
/*  38: 58 */       codec = (CompressionCodec)ReflectionUtils.newInstance(codecClass, conf);
/*  39:    */     }
/*  40: 62 */     Path file = getDefaultWorkFile(context, "");
/*  41: 63 */     FileSystem fs = file.getFileSystem(conf);
/*  42: 64 */     return SequenceFile.createWriter(fs, conf, file, keyClass, valueClass, compressionType, codec, context);
/*  43:    */   }
/*  44:    */   
/*  45:    */   public RecordWriter<K, V> getRecordWriter(TaskAttemptContext context)
/*  46:    */     throws IOException, InterruptedException
/*  47:    */   {
/*  48: 75 */     final SequenceFile.Writer out = getSequenceWriter(context, context.getOutputKeyClass(), context.getOutputValueClass());
/*  49:    */     
/*  50:    */ 
/*  51: 78 */     new RecordWriter()
/*  52:    */     {
/*  53:    */       public void write(K key, V value)
/*  54:    */         throws IOException
/*  55:    */       {
/*  56: 83 */         out.append(key, value);
/*  57:    */       }
/*  58:    */       
/*  59:    */       public void close(TaskAttemptContext context)
/*  60:    */         throws IOException
/*  61:    */       {
/*  62: 87 */         out.close();
/*  63:    */       }
/*  64:    */     };
/*  65:    */   }
/*  66:    */   
/*  67:    */   public static SequenceFile.CompressionType getOutputCompressionType(JobContext job)
/*  68:    */   {
/*  69: 99 */     String val = job.getConfiguration().get("mapreduce.output.fileoutputformat.compress.type", SequenceFile.CompressionType.RECORD.toString());
/*  70:    */     
/*  71:101 */     return SequenceFile.CompressionType.valueOf(val);
/*  72:    */   }
/*  73:    */   
/*  74:    */   public static void setOutputCompressionType(Job job, SequenceFile.CompressionType style)
/*  75:    */   {
/*  76:112 */     setCompressOutput(job, true);
/*  77:113 */     job.getConfiguration().set("mapreduce.output.fileoutputformat.compress.type", style.toString());
/*  78:    */   }
/*  79:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.lib.output.SequenceFileOutputFormat
 * JD-Core Version:    0.7.0.1
 */