/*   1:    */ package org.apache.hadoop.mapred;
/*   2:    */ 
/*   3:    */ import java.io.DataOutputStream;
/*   4:    */ import java.io.IOException;
/*   5:    */ import java.io.UnsupportedEncodingException;
/*   6:    */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*   7:    */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*   8:    */ import org.apache.hadoop.fs.FSDataOutputStream;
/*   9:    */ import org.apache.hadoop.fs.FileSystem;
/*  10:    */ import org.apache.hadoop.fs.Path;
/*  11:    */ import org.apache.hadoop.io.NullWritable;
/*  12:    */ import org.apache.hadoop.io.Text;
/*  13:    */ import org.apache.hadoop.io.compress.CompressionCodec;
/*  14:    */ import org.apache.hadoop.io.compress.GzipCodec;
/*  15:    */ import org.apache.hadoop.util.Progressable;
/*  16:    */ import org.apache.hadoop.util.ReflectionUtils;
/*  17:    */ 
/*  18:    */ @InterfaceAudience.Public
/*  19:    */ @InterfaceStability.Stable
/*  20:    */ public class TextOutputFormat<K, V>
/*  21:    */   extends FileOutputFormat<K, V>
/*  22:    */ {
/*  23:    */   protected static class LineRecordWriter<K, V>
/*  24:    */     implements RecordWriter<K, V>
/*  25:    */   {
/*  26:    */     private static final String utf8 = "UTF-8";
/*  27:    */     private static final byte[] newline;
/*  28:    */     protected DataOutputStream out;
/*  29:    */     private final byte[] keyValueSeparator;
/*  30:    */     
/*  31:    */     static
/*  32:    */     {
/*  33:    */       try
/*  34:    */       {
/*  35: 50 */         newline = "\n".getBytes("UTF-8");
/*  36:    */       }
/*  37:    */       catch (UnsupportedEncodingException uee)
/*  38:    */       {
/*  39: 52 */         throw new IllegalArgumentException("can't find UTF-8 encoding");
/*  40:    */       }
/*  41:    */     }
/*  42:    */     
/*  43:    */     public LineRecordWriter(DataOutputStream out, String keyValueSeparator)
/*  44:    */     {
/*  45: 60 */       this.out = out;
/*  46:    */       try
/*  47:    */       {
/*  48: 62 */         this.keyValueSeparator = keyValueSeparator.getBytes("UTF-8");
/*  49:    */       }
/*  50:    */       catch (UnsupportedEncodingException uee)
/*  51:    */       {
/*  52: 64 */         throw new IllegalArgumentException("can't find UTF-8 encoding");
/*  53:    */       }
/*  54:    */     }
/*  55:    */     
/*  56:    */     public LineRecordWriter(DataOutputStream out)
/*  57:    */     {
/*  58: 69 */       this(out, "\t");
/*  59:    */     }
/*  60:    */     
/*  61:    */     private void writeObject(Object o)
/*  62:    */       throws IOException
/*  63:    */     {
/*  64: 79 */       if ((o instanceof Text))
/*  65:    */       {
/*  66: 80 */         Text to = (Text)o;
/*  67: 81 */         this.out.write(to.getBytes(), 0, to.getLength());
/*  68:    */       }
/*  69:    */       else
/*  70:    */       {
/*  71: 83 */         this.out.write(o.toString().getBytes("UTF-8"));
/*  72:    */       }
/*  73:    */     }
/*  74:    */     
/*  75:    */     public synchronized void write(K key, V value)
/*  76:    */       throws IOException
/*  77:    */     {
/*  78: 90 */       boolean nullKey = (key == null) || ((key instanceof NullWritable));
/*  79: 91 */       boolean nullValue = (value == null) || ((value instanceof NullWritable));
/*  80: 92 */       if ((nullKey) && (nullValue)) {
/*  81: 93 */         return;
/*  82:    */       }
/*  83: 95 */       if (!nullKey) {
/*  84: 96 */         writeObject(key);
/*  85:    */       }
/*  86: 98 */       if ((!nullKey) && (!nullValue)) {
/*  87: 99 */         this.out.write(this.keyValueSeparator);
/*  88:    */       }
/*  89:101 */       if (!nullValue) {
/*  90:102 */         writeObject(value);
/*  91:    */       }
/*  92:104 */       this.out.write(newline);
/*  93:    */     }
/*  94:    */     
/*  95:    */     public synchronized void close(Reporter reporter)
/*  96:    */       throws IOException
/*  97:    */     {
/*  98:108 */       this.out.close();
/*  99:    */     }
/* 100:    */   }
/* 101:    */   
/* 102:    */   public RecordWriter<K, V> getRecordWriter(FileSystem ignored, JobConf job, String name, Progressable progress)
/* 103:    */     throws IOException
/* 104:    */   {
/* 105:117 */     boolean isCompressed = getCompressOutput(job);
/* 106:118 */     String keyValueSeparator = job.get("mapreduce.output.textoutputformat.separator", "\t");
/* 107:120 */     if (!isCompressed)
/* 108:    */     {
/* 109:121 */       Path file = FileOutputFormat.getTaskOutputPath(job, name);
/* 110:122 */       FileSystem fs = file.getFileSystem(job);
/* 111:123 */       FSDataOutputStream fileOut = fs.create(file, progress);
/* 112:124 */       return new LineRecordWriter(fileOut, keyValueSeparator);
/* 113:    */     }
/* 114:126 */     Class<? extends CompressionCodec> codecClass = getOutputCompressorClass(job, GzipCodec.class);
/* 115:    */     
/* 116:    */ 
/* 117:129 */     CompressionCodec codec = (CompressionCodec)ReflectionUtils.newInstance(codecClass, job);
/* 118:    */     
/* 119:131 */     Path file = FileOutputFormat.getTaskOutputPath(job, name + codec.getDefaultExtension());
/* 120:    */     
/* 121:    */ 
/* 122:134 */     FileSystem fs = file.getFileSystem(job);
/* 123:135 */     FSDataOutputStream fileOut = fs.create(file, progress);
/* 124:136 */     return new LineRecordWriter(new DataOutputStream(codec.createOutputStream(fileOut)), keyValueSeparator);
/* 125:    */   }
/* 126:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.TextOutputFormat
 * JD-Core Version:    0.7.0.1
 */