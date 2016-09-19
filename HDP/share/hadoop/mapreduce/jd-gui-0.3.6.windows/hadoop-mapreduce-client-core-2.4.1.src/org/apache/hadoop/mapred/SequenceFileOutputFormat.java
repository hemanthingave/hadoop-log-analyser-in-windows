/*   1:    */ package org.apache.hadoop.mapred;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.util.Arrays;
/*   5:    */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*   6:    */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*   7:    */ import org.apache.hadoop.conf.Configuration;
/*   8:    */ import org.apache.hadoop.fs.FileSystem;
/*   9:    */ import org.apache.hadoop.fs.FileUtil;
/*  10:    */ import org.apache.hadoop.fs.Path;
/*  11:    */ import org.apache.hadoop.io.SequenceFile;
/*  12:    */ import org.apache.hadoop.io.SequenceFile.CompressionType;
/*  13:    */ import org.apache.hadoop.io.SequenceFile.Reader;
/*  14:    */ import org.apache.hadoop.io.SequenceFile.Writer;
/*  15:    */ import org.apache.hadoop.io.compress.CompressionCodec;
/*  16:    */ import org.apache.hadoop.io.compress.DefaultCodec;
/*  17:    */ import org.apache.hadoop.util.Progressable;
/*  18:    */ import org.apache.hadoop.util.ReflectionUtils;
/*  19:    */ 
/*  20:    */ @InterfaceAudience.Public
/*  21:    */ @InterfaceStability.Stable
/*  22:    */ public class SequenceFileOutputFormat<K, V>
/*  23:    */   extends FileOutputFormat<K, V>
/*  24:    */ {
/*  25:    */   public RecordWriter<K, V> getRecordWriter(FileSystem ignored, JobConf job, String name, Progressable progress)
/*  26:    */     throws IOException
/*  27:    */   {
/*  28: 49 */     Path file = FileOutputFormat.getTaskOutputPath(job, name);
/*  29:    */     
/*  30: 51 */     FileSystem fs = file.getFileSystem(job);
/*  31: 52 */     CompressionCodec codec = null;
/*  32: 53 */     SequenceFile.CompressionType compressionType = SequenceFile.CompressionType.NONE;
/*  33: 54 */     if (getCompressOutput(job))
/*  34:    */     {
/*  35: 56 */       compressionType = getOutputCompressionType(job);
/*  36:    */       
/*  37:    */ 
/*  38: 59 */       Class<? extends CompressionCodec> codecClass = getOutputCompressorClass(job, DefaultCodec.class);
/*  39:    */       
/*  40: 61 */       codec = (CompressionCodec)ReflectionUtils.newInstance(codecClass, job);
/*  41:    */     }
/*  42: 63 */     final SequenceFile.Writer out = SequenceFile.createWriter(fs, job, file, job.getOutputKeyClass(), job.getOutputValueClass(), compressionType, codec, progress);
/*  43:    */     
/*  44:    */ 
/*  45:    */ 
/*  46:    */ 
/*  47:    */ 
/*  48:    */ 
/*  49:    */ 
/*  50: 71 */     new RecordWriter()
/*  51:    */     {
/*  52:    */       public void write(K key, V value)
/*  53:    */         throws IOException
/*  54:    */       {
/*  55: 76 */         out.append(key, value);
/*  56:    */       }
/*  57:    */       
/*  58:    */       public void close(Reporter reporter)
/*  59:    */         throws IOException
/*  60:    */       {
/*  61: 79 */         out.close();
/*  62:    */       }
/*  63:    */     };
/*  64:    */   }
/*  65:    */   
/*  66:    */   public static SequenceFile.Reader[] getReaders(Configuration conf, Path dir)
/*  67:    */     throws IOException
/*  68:    */   {
/*  69: 86 */     FileSystem fs = dir.getFileSystem(conf);
/*  70: 87 */     Path[] names = FileUtil.stat2Paths(fs.listStatus(dir));
/*  71:    */     
/*  72:    */ 
/*  73: 90 */     Arrays.sort(names);
/*  74:    */     
/*  75: 92 */     SequenceFile.Reader[] parts = new SequenceFile.Reader[names.length];
/*  76: 93 */     for (int i = 0; i < names.length; i++) {
/*  77: 94 */       parts[i] = new SequenceFile.Reader(fs, names[i], conf);
/*  78:    */     }
/*  79: 96 */     return parts;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public static SequenceFile.CompressionType getOutputCompressionType(JobConf conf)
/*  83:    */   {
/*  84:106 */     String val = conf.get("mapreduce.output.fileoutputformat.compress.type", SequenceFile.CompressionType.RECORD.toString());
/*  85:    */     
/*  86:108 */     return SequenceFile.CompressionType.valueOf(val);
/*  87:    */   }
/*  88:    */   
/*  89:    */   public static void setOutputCompressionType(JobConf conf, SequenceFile.CompressionType style)
/*  90:    */   {
/*  91:119 */     setCompressOutput(conf, true);
/*  92:120 */     conf.set("mapreduce.output.fileoutputformat.compress.type", style.toString());
/*  93:    */   }
/*  94:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.SequenceFileOutputFormat
 * JD-Core Version:    0.7.0.1
 */