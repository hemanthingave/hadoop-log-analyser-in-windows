/*  1:   */ package org.apache.hadoop.mapred;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*  5:   */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*  6:   */ import org.apache.hadoop.conf.Configuration;
/*  7:   */ import org.apache.hadoop.fs.FileSystem;
/*  8:   */ import org.apache.hadoop.fs.Path;
/*  9:   */ import org.apache.hadoop.io.MapFile.Reader;
/* 10:   */ import org.apache.hadoop.io.MapFile.Writer;
/* 11:   */ import org.apache.hadoop.io.SequenceFile.CompressionType;
/* 12:   */ import org.apache.hadoop.io.Writable;
/* 13:   */ import org.apache.hadoop.io.WritableComparable;
/* 14:   */ import org.apache.hadoop.io.compress.CompressionCodec;
/* 15:   */ import org.apache.hadoop.io.compress.DefaultCodec;
/* 16:   */ import org.apache.hadoop.util.Progressable;
/* 17:   */ import org.apache.hadoop.util.ReflectionUtils;
/* 18:   */ 
/* 19:   */ @InterfaceAudience.Public
/* 20:   */ @InterfaceStability.Stable
/* 21:   */ public class MapFileOutputFormat
/* 22:   */   extends FileOutputFormat<WritableComparable, Writable>
/* 23:   */ {
/* 24:   */   public RecordWriter<WritableComparable, Writable> getRecordWriter(FileSystem ignored, JobConf job, String name, Progressable progress)
/* 25:   */     throws IOException
/* 26:   */   {
/* 27:49 */     Path file = FileOutputFormat.getTaskOutputPath(job, name);
/* 28:   */     
/* 29:51 */     FileSystem fs = file.getFileSystem(job);
/* 30:52 */     CompressionCodec codec = null;
/* 31:53 */     SequenceFile.CompressionType compressionType = SequenceFile.CompressionType.NONE;
/* 32:54 */     if (getCompressOutput(job))
/* 33:   */     {
/* 34:56 */       compressionType = SequenceFileOutputFormat.getOutputCompressionType(job);
/* 35:   */       
/* 36:   */ 
/* 37:59 */       Class<? extends CompressionCodec> codecClass = getOutputCompressorClass(job, DefaultCodec.class);
/* 38:   */       
/* 39:61 */       codec = (CompressionCodec)ReflectionUtils.newInstance(codecClass, job);
/* 40:   */     }
/* 41:65 */     final MapFile.Writer out = new MapFile.Writer(job, fs, file.toString(), job.getOutputKeyClass().asSubclass(WritableComparable.class), job.getOutputValueClass().asSubclass(Writable.class), compressionType, codec, progress);
/* 42:   */     
/* 43:   */ 
/* 44:   */ 
/* 45:   */ 
/* 46:   */ 
/* 47:   */ 
/* 48:72 */     new RecordWriter()
/* 49:   */     {
/* 50:   */       public void write(WritableComparable key, Writable value)
/* 51:   */         throws IOException
/* 52:   */       {
/* 53:77 */         out.append(key, value);
/* 54:   */       }
/* 55:   */       
/* 56:   */       public void close(Reporter reporter)
/* 57:   */         throws IOException
/* 58:   */       {
/* 59:80 */         out.close();
/* 60:   */       }
/* 61:   */     };
/* 62:   */   }
/* 63:   */   
/* 64:   */   public static MapFile.Reader[] getReaders(FileSystem ignored, Path dir, Configuration conf)
/* 65:   */     throws IOException
/* 66:   */   {
/* 67:88 */     return org.apache.hadoop.mapreduce.lib.output.MapFileOutputFormat.getReaders(dir, conf);
/* 68:   */   }
/* 69:   */   
/* 70:   */   public static <K extends WritableComparable, V extends Writable> Writable getEntry(MapFile.Reader[] readers, Partitioner<K, V> partitioner, K key, V value)
/* 71:   */     throws IOException
/* 72:   */   {
/* 73:98 */     int part = partitioner.getPartition(key, value, readers.length);
/* 74:99 */     return readers[part].get(key, value);
/* 75:   */   }
/* 76:   */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.MapFileOutputFormat
 * JD-Core Version:    0.7.0.1
 */