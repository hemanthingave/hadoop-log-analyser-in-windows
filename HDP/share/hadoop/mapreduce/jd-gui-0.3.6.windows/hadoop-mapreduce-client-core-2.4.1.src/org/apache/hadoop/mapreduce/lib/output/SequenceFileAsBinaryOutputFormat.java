/*   1:    */ package org.apache.hadoop.mapreduce.lib.output;
/*   2:    */ 
/*   3:    */ import java.io.DataOutputStream;
/*   4:    */ import java.io.IOException;
/*   5:    */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*   6:    */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*   7:    */ import org.apache.hadoop.conf.Configuration;
/*   8:    */ import org.apache.hadoop.io.BytesWritable;
/*   9:    */ import org.apache.hadoop.io.SequenceFile.CompressionType;
/*  10:    */ import org.apache.hadoop.io.SequenceFile.ValueBytes;
/*  11:    */ import org.apache.hadoop.io.SequenceFile.Writer;
/*  12:    */ import org.apache.hadoop.io.Writable;
/*  13:    */ import org.apache.hadoop.io.WritableComparable;
/*  14:    */ import org.apache.hadoop.mapred.InvalidJobConfException;
/*  15:    */ import org.apache.hadoop.mapreduce.Job;
/*  16:    */ import org.apache.hadoop.mapreduce.JobContext;
/*  17:    */ import org.apache.hadoop.mapreduce.RecordWriter;
/*  18:    */ import org.apache.hadoop.mapreduce.TaskAttemptContext;
/*  19:    */ 
/*  20:    */ @InterfaceAudience.Public
/*  21:    */ @InterfaceStability.Stable
/*  22:    */ public class SequenceFileAsBinaryOutputFormat
/*  23:    */   extends SequenceFileOutputFormat<BytesWritable, BytesWritable>
/*  24:    */ {
/*  25: 45 */   public static String KEY_CLASS = "mapreduce.output.seqbinaryoutputformat.key.class";
/*  26: 46 */   public static String VALUE_CLASS = "mapreduce.output.seqbinaryoutputformat.value.class";
/*  27:    */   
/*  28:    */   public static class WritableValueBytes
/*  29:    */     implements SequenceFile.ValueBytes
/*  30:    */   {
/*  31:    */     private BytesWritable value;
/*  32:    */     
/*  33:    */     public WritableValueBytes()
/*  34:    */     {
/*  35: 55 */       this.value = null;
/*  36:    */     }
/*  37:    */     
/*  38:    */     public WritableValueBytes(BytesWritable value)
/*  39:    */     {
/*  40: 59 */       this.value = value;
/*  41:    */     }
/*  42:    */     
/*  43:    */     public void reset(BytesWritable value)
/*  44:    */     {
/*  45: 63 */       this.value = value;
/*  46:    */     }
/*  47:    */     
/*  48:    */     public void writeUncompressedBytes(DataOutputStream outStream)
/*  49:    */       throws IOException
/*  50:    */     {
/*  51: 68 */       outStream.write(this.value.getBytes(), 0, this.value.getLength());
/*  52:    */     }
/*  53:    */     
/*  54:    */     public void writeCompressedBytes(DataOutputStream outStream)
/*  55:    */       throws IllegalArgumentException, IOException
/*  56:    */     {
/*  57: 73 */       throw new UnsupportedOperationException("WritableValueBytes doesn't support RECORD compression");
/*  58:    */     }
/*  59:    */     
/*  60:    */     public int getSize()
/*  61:    */     {
/*  62: 78 */       return this.value.getLength();
/*  63:    */     }
/*  64:    */   }
/*  65:    */   
/*  66:    */   public static void setSequenceFileOutputKeyClass(Job job, Class<?> theClass)
/*  67:    */   {
/*  68: 92 */     job.getConfiguration().setClass(KEY_CLASS, theClass, Object.class);
/*  69:    */   }
/*  70:    */   
/*  71:    */   public static void setSequenceFileOutputValueClass(Job job, Class<?> theClass)
/*  72:    */   {
/*  73:106 */     job.getConfiguration().setClass(VALUE_CLASS, theClass, Object.class);
/*  74:    */   }
/*  75:    */   
/*  76:    */   public static Class<? extends WritableComparable> getSequenceFileOutputKeyClass(JobContext job)
/*  77:    */   {
/*  78:117 */     return job.getConfiguration().getClass(KEY_CLASS, job.getOutputKeyClass().asSubclass(WritableComparable.class), WritableComparable.class);
/*  79:    */   }
/*  80:    */   
/*  81:    */   public static Class<? extends Writable> getSequenceFileOutputValueClass(JobContext job)
/*  82:    */   {
/*  83:129 */     return job.getConfiguration().getClass(VALUE_CLASS, job.getOutputValueClass().asSubclass(Writable.class), Writable.class);
/*  84:    */   }
/*  85:    */   
/*  86:    */   public RecordWriter<BytesWritable, BytesWritable> getRecordWriter(TaskAttemptContext context)
/*  87:    */     throws IOException
/*  88:    */   {
/*  89:136 */     final SequenceFile.Writer out = getSequenceWriter(context, getSequenceFileOutputKeyClass(context), getSequenceFileOutputValueClass(context));
/*  90:    */     
/*  91:    */ 
/*  92:    */ 
/*  93:140 */     new RecordWriter()
/*  94:    */     {
/*  95:141 */       private SequenceFileAsBinaryOutputFormat.WritableValueBytes wvaluebytes = new SequenceFileAsBinaryOutputFormat.WritableValueBytes();
/*  96:    */       
/*  97:    */       public void write(BytesWritable bkey, BytesWritable bvalue)
/*  98:    */         throws IOException
/*  99:    */       {
/* 100:145 */         this.wvaluebytes.reset(bvalue);
/* 101:146 */         out.appendRaw(bkey.getBytes(), 0, bkey.getLength(), this.wvaluebytes);
/* 102:147 */         this.wvaluebytes.reset(null);
/* 103:    */       }
/* 104:    */       
/* 105:    */       public void close(TaskAttemptContext context)
/* 106:    */         throws IOException
/* 107:    */       {
/* 108:151 */         out.close();
/* 109:    */       }
/* 110:    */     };
/* 111:    */   }
/* 112:    */   
/* 113:    */   public void checkOutputSpecs(JobContext job)
/* 114:    */     throws IOException
/* 115:    */   {
/* 116:158 */     super.checkOutputSpecs(job);
/* 117:159 */     if ((getCompressOutput(job)) && (getOutputCompressionType(job) == SequenceFile.CompressionType.RECORD)) {
/* 118:161 */       throw new InvalidJobConfException("SequenceFileAsBinaryOutputFormat doesn't support Record Compression");
/* 119:    */     }
/* 120:    */   }
/* 121:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.lib.output.SequenceFileAsBinaryOutputFormat
 * JD-Core Version:    0.7.0.1
 */