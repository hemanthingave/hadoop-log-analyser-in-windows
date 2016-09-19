/*   1:    */ package org.apache.hadoop.mapreduce.lib.output;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*   5:    */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*   6:    */ import org.apache.hadoop.mapreduce.JobContext;
/*   7:    */ import org.apache.hadoop.mapreduce.OutputCommitter;
/*   8:    */ import org.apache.hadoop.mapreduce.OutputFormat;
/*   9:    */ import org.apache.hadoop.mapreduce.RecordWriter;
/*  10:    */ import org.apache.hadoop.mapreduce.TaskAttemptContext;
/*  11:    */ 
/*  12:    */ @InterfaceAudience.Public
/*  13:    */ @InterfaceStability.Stable
/*  14:    */ public class FilterOutputFormat<K, V>
/*  15:    */   extends OutputFormat<K, V>
/*  16:    */ {
/*  17:    */   protected OutputFormat<K, V> baseOut;
/*  18:    */   
/*  19:    */   public FilterOutputFormat()
/*  20:    */   {
/*  21: 41 */     this.baseOut = null;
/*  22:    */   }
/*  23:    */   
/*  24:    */   public FilterOutputFormat(OutputFormat<K, V> baseOut)
/*  25:    */   {
/*  26: 49 */     this.baseOut = baseOut;
/*  27:    */   }
/*  28:    */   
/*  29:    */   public RecordWriter<K, V> getRecordWriter(TaskAttemptContext context)
/*  30:    */     throws IOException, InterruptedException
/*  31:    */   {
/*  32: 55 */     return getBaseOut().getRecordWriter(context);
/*  33:    */   }
/*  34:    */   
/*  35:    */   public void checkOutputSpecs(JobContext context)
/*  36:    */     throws IOException, InterruptedException
/*  37:    */   {
/*  38: 61 */     getBaseOut().checkOutputSpecs(context);
/*  39:    */   }
/*  40:    */   
/*  41:    */   public OutputCommitter getOutputCommitter(TaskAttemptContext context)
/*  42:    */     throws IOException, InterruptedException
/*  43:    */   {
/*  44: 67 */     return getBaseOut().getOutputCommitter(context);
/*  45:    */   }
/*  46:    */   
/*  47:    */   private OutputFormat<K, V> getBaseOut()
/*  48:    */     throws IOException
/*  49:    */   {
/*  50: 71 */     if (this.baseOut == null) {
/*  51: 72 */       throw new IOException("OutputFormat not set for FilterOutputFormat");
/*  52:    */     }
/*  53: 74 */     return this.baseOut;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public static class FilterRecordWriter<K, V>
/*  57:    */     extends RecordWriter<K, V>
/*  58:    */   {
/*  59: 83 */     protected RecordWriter<K, V> rawWriter = null;
/*  60:    */     
/*  61:    */     public FilterRecordWriter()
/*  62:    */     {
/*  63: 86 */       this.rawWriter = null;
/*  64:    */     }
/*  65:    */     
/*  66:    */     public FilterRecordWriter(RecordWriter<K, V> rwriter)
/*  67:    */     {
/*  68: 90 */       this.rawWriter = rwriter;
/*  69:    */     }
/*  70:    */     
/*  71:    */     public void write(K key, V value)
/*  72:    */       throws IOException, InterruptedException
/*  73:    */     {
/*  74: 95 */       getRawWriter().write(key, value);
/*  75:    */     }
/*  76:    */     
/*  77:    */     public void close(TaskAttemptContext context)
/*  78:    */       throws IOException, InterruptedException
/*  79:    */     {
/*  80:101 */       getRawWriter().close(context);
/*  81:    */     }
/*  82:    */     
/*  83:    */     private RecordWriter<K, V> getRawWriter()
/*  84:    */       throws IOException
/*  85:    */     {
/*  86:105 */       if (this.rawWriter == null) {
/*  87:106 */         throw new IOException("Record Writer not set for FilterRecordWriter");
/*  88:    */       }
/*  89:108 */       return this.rawWriter;
/*  90:    */     }
/*  91:    */   }
/*  92:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.lib.output.FilterOutputFormat
 * JD-Core Version:    0.7.0.1
 */