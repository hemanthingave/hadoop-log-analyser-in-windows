/*   1:    */ package org.apache.hadoop.mapreduce.lib.output;
/*   2:    */ 
/*   3:    */ import java.io.DataOutputStream;
/*   4:    */ import java.io.IOException;
/*   5:    */ import java.io.UnsupportedEncodingException;
/*   6:    */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*   7:    */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*   8:    */ import org.apache.hadoop.conf.Configuration;
/*   9:    */ import org.apache.hadoop.fs.FSDataOutputStream;
/*  10:    */ import org.apache.hadoop.fs.FileSystem;
/*  11:    */ import org.apache.hadoop.fs.Path;
/*  12:    */ import org.apache.hadoop.io.NullWritable;
/*  13:    */ import org.apache.hadoop.io.Text;
/*  14:    */ import org.apache.hadoop.io.compress.CompressionCodec;
/*  15:    */ import org.apache.hadoop.io.compress.GzipCodec;
/*  16:    */ import org.apache.hadoop.mapreduce.RecordWriter;
/*  17:    */ import org.apache.hadoop.mapreduce.TaskAttemptContext;
/*  18:    */ import org.apache.hadoop.util.ReflectionUtils;
/*  19:    */ 
/*  20:    */ @InterfaceAudience.Public
/*  21:    */ @InterfaceStability.Stable
/*  22:    */ public class TextOutputFormat<K, V>
/*  23:    */   extends FileOutputFormat<K, V>
/*  24:    */ {
/*  25: 45 */   public static String SEPERATOR = "mapreduce.output.textoutputformat.separator";
/*  26:    */   
/*  27:    */   protected static class LineRecordWriter<K, V>
/*  28:    */     extends RecordWriter<K, V>
/*  29:    */   {
/*  30:    */     private static final String utf8 = "UTF-8";
/*  31:    */     private static final byte[] newline;
/*  32:    */     protected DataOutputStream out;
/*  33:    */     private final byte[] keyValueSeparator;
/*  34:    */     
/*  35:    */     static
/*  36:    */     {
/*  37:    */       try
/*  38:    */       {
/*  39: 52 */         newline = "\n".getBytes("UTF-8");
/*  40:    */       }
/*  41:    */       catch (UnsupportedEncodingException uee)
/*  42:    */       {
/*  43: 54 */         throw new IllegalArgumentException("can't find UTF-8 encoding");
/*  44:    */       }
/*  45:    */     }
/*  46:    */     
/*  47:    */     public LineRecordWriter(DataOutputStream out, String keyValueSeparator)
/*  48:    */     {
/*  49: 62 */       this.out = out;
/*  50:    */       try
/*  51:    */       {
/*  52: 64 */         this.keyValueSeparator = keyValueSeparator.getBytes("UTF-8");
/*  53:    */       }
/*  54:    */       catch (UnsupportedEncodingException uee)
/*  55:    */       {
/*  56: 66 */         throw new IllegalArgumentException("can't find UTF-8 encoding");
/*  57:    */       }
/*  58:    */     }
/*  59:    */     
/*  60:    */     public LineRecordWriter(DataOutputStream out)
/*  61:    */     {
/*  62: 71 */       this(out, "\t");
/*  63:    */     }
/*  64:    */     
/*  65:    */     private void writeObject(Object o)
/*  66:    */       throws IOException
/*  67:    */     {
/*  68: 81 */       if ((o instanceof Text))
/*  69:    */       {
/*  70: 82 */         Text to = (Text)o;
/*  71: 83 */         this.out.write(to.getBytes(), 0, to.getLength());
/*  72:    */       }
/*  73:    */       else
/*  74:    */       {
/*  75: 85 */         this.out.write(o.toString().getBytes("UTF-8"));
/*  76:    */       }
/*  77:    */     }
/*  78:    */     
/*  79:    */     public synchronized void write(K key, V value)
/*  80:    */       throws IOException
/*  81:    */     {
/*  82: 92 */       boolean nullKey = (key == null) || ((key instanceof NullWritable));
/*  83: 93 */       boolean nullValue = (value == null) || ((value instanceof NullWritable));
/*  84: 94 */       if ((nullKey) && (nullValue)) {
/*  85: 95 */         return;
/*  86:    */       }
/*  87: 97 */       if (!nullKey) {
/*  88: 98 */         writeObject(key);
/*  89:    */       }
/*  90:100 */       if ((!nullKey) && (!nullValue)) {
/*  91:101 */         this.out.write(this.keyValueSeparator);
/*  92:    */       }
/*  93:103 */       if (!nullValue) {
/*  94:104 */         writeObject(value);
/*  95:    */       }
/*  96:106 */       this.out.write(newline);
/*  97:    */     }
/*  98:    */     
/*  99:    */     public synchronized void close(TaskAttemptContext context)
/* 100:    */       throws IOException
/* 101:    */     {
/* 102:111 */       this.out.close();
/* 103:    */     }
/* 104:    */   }
/* 105:    */   
/* 106:    */   public RecordWriter<K, V> getRecordWriter(TaskAttemptContext job)
/* 107:    */     throws IOException, InterruptedException
/* 108:    */   {
/* 109:118 */     Configuration conf = job.getConfiguration();
/* 110:119 */     boolean isCompressed = getCompressOutput(job);
/* 111:120 */     String keyValueSeparator = conf.get(SEPERATOR, "\t");
/* 112:121 */     CompressionCodec codec = null;
/* 113:122 */     String extension = "";
/* 114:123 */     if (isCompressed)
/* 115:    */     {
/* 116:124 */       Class<? extends CompressionCodec> codecClass = getOutputCompressorClass(job, GzipCodec.class);
/* 117:    */       
/* 118:126 */       codec = (CompressionCodec)ReflectionUtils.newInstance(codecClass, conf);
/* 119:127 */       extension = codec.getDefaultExtension();
/* 120:    */     }
/* 121:129 */     Path file = getDefaultWorkFile(job, extension);
/* 122:130 */     FileSystem fs = file.getFileSystem(conf);
/* 123:131 */     if (!isCompressed)
/* 124:    */     {
/* 125:132 */       FSDataOutputStream fileOut = fs.create(file, false);
/* 126:133 */       return new LineRecordWriter(fileOut, keyValueSeparator);
/* 127:    */     }
/* 128:135 */     FSDataOutputStream fileOut = fs.create(file, false);
/* 129:136 */     return new LineRecordWriter(new DataOutputStream(codec.createOutputStream(fileOut)), keyValueSeparator);
/* 130:    */   }
/* 131:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.lib.output.TextOutputFormat
 * JD-Core Version:    0.7.0.1
 */