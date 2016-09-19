/*   1:    */ package org.apache.hadoop.mapreduce.lib.output;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.util.Arrays;
/*   5:    */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*   6:    */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*   7:    */ import org.apache.hadoop.conf.Configuration;
/*   8:    */ import org.apache.hadoop.fs.FileSystem;
/*   9:    */ import org.apache.hadoop.fs.FileUtil;
/*  10:    */ import org.apache.hadoop.fs.Path;
/*  11:    */ import org.apache.hadoop.io.MapFile.Reader;
/*  12:    */ import org.apache.hadoop.io.MapFile.Writer;
/*  13:    */ import org.apache.hadoop.io.SequenceFile.CompressionType;
/*  14:    */ import org.apache.hadoop.io.Writable;
/*  15:    */ import org.apache.hadoop.io.WritableComparable;
/*  16:    */ import org.apache.hadoop.io.compress.CompressionCodec;
/*  17:    */ import org.apache.hadoop.io.compress.DefaultCodec;
/*  18:    */ import org.apache.hadoop.mapreduce.Partitioner;
/*  19:    */ import org.apache.hadoop.mapreduce.RecordWriter;
/*  20:    */ import org.apache.hadoop.mapreduce.TaskAttemptContext;
/*  21:    */ import org.apache.hadoop.util.ReflectionUtils;
/*  22:    */ 
/*  23:    */ @InterfaceAudience.Public
/*  24:    */ @InterfaceStability.Stable
/*  25:    */ public class MapFileOutputFormat
/*  26:    */   extends FileOutputFormat<WritableComparable<?>, Writable>
/*  27:    */ {
/*  28:    */   public RecordWriter<WritableComparable<?>, Writable> getRecordWriter(TaskAttemptContext context)
/*  29:    */     throws IOException
/*  30:    */   {
/*  31: 53 */     Configuration conf = context.getConfiguration();
/*  32: 54 */     CompressionCodec codec = null;
/*  33: 55 */     SequenceFile.CompressionType compressionType = SequenceFile.CompressionType.NONE;
/*  34: 56 */     if (getCompressOutput(context))
/*  35:    */     {
/*  36: 58 */       compressionType = SequenceFileOutputFormat.getOutputCompressionType(context);
/*  37:    */       
/*  38:    */ 
/*  39: 61 */       Class<?> codecClass = getOutputCompressorClass(context, DefaultCodec.class);
/*  40:    */       
/*  41: 63 */       codec = (CompressionCodec)ReflectionUtils.newInstance(codecClass, conf);
/*  42:    */     }
/*  43: 66 */     Path file = getDefaultWorkFile(context, "");
/*  44: 67 */     FileSystem fs = file.getFileSystem(conf);
/*  45:    */     
/*  46: 69 */     final MapFile.Writer out = new MapFile.Writer(conf, fs, file.toString(), context.getOutputKeyClass().asSubclass(WritableComparable.class), context.getOutputValueClass().asSubclass(Writable.class), compressionType, codec, context);
/*  47:    */     
/*  48:    */ 
/*  49:    */ 
/*  50:    */ 
/*  51:    */ 
/*  52: 75 */     new RecordWriter()
/*  53:    */     {
/*  54:    */       public void write(WritableComparable<?> key, Writable value)
/*  55:    */         throws IOException
/*  56:    */       {
/*  57: 78 */         out.append(key, value);
/*  58:    */       }
/*  59:    */       
/*  60:    */       public void close(TaskAttemptContext context)
/*  61:    */         throws IOException
/*  62:    */       {
/*  63: 82 */         out.close();
/*  64:    */       }
/*  65:    */     };
/*  66:    */   }
/*  67:    */   
/*  68:    */   public static MapFile.Reader[] getReaders(Path dir, Configuration conf)
/*  69:    */     throws IOException
/*  70:    */   {
/*  71: 90 */     FileSystem fs = dir.getFileSystem(conf);
/*  72: 91 */     Path[] names = FileUtil.stat2Paths(fs.listStatus(dir));
/*  73:    */     
/*  74:    */ 
/*  75: 94 */     Arrays.sort(names);
/*  76:    */     
/*  77: 96 */     MapFile.Reader[] parts = new MapFile.Reader[names.length];
/*  78: 97 */     for (int i = 0; i < names.length; i++) {
/*  79: 98 */       parts[i] = new MapFile.Reader(fs, names[i].toString(), conf);
/*  80:    */     }
/*  81:100 */     return parts;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public static <K extends WritableComparable<?>, V extends Writable> Writable getEntry(MapFile.Reader[] readers, Partitioner<K, V> partitioner, K key, V value)
/*  85:    */     throws IOException
/*  86:    */   {
/*  87:107 */     int part = partitioner.getPartition(key, value, readers.length);
/*  88:108 */     return readers[part].get(key, value);
/*  89:    */   }
/*  90:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.lib.output.MapFileOutputFormat
 * JD-Core Version:    0.7.0.1
 */