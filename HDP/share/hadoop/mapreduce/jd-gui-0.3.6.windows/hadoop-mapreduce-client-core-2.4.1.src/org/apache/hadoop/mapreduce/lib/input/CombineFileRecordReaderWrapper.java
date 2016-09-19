/*   1:    */ package org.apache.hadoop.mapreduce.lib.input;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*   5:    */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*   6:    */ import org.apache.hadoop.conf.Configuration;
/*   7:    */ import org.apache.hadoop.fs.Path;
/*   8:    */ import org.apache.hadoop.mapreduce.InputSplit;
/*   9:    */ import org.apache.hadoop.mapreduce.RecordReader;
/*  10:    */ import org.apache.hadoop.mapreduce.TaskAttemptContext;
/*  11:    */ 
/*  12:    */ @InterfaceAudience.Public
/*  13:    */ @InterfaceStability.Stable
/*  14:    */ public abstract class CombineFileRecordReaderWrapper<K, V>
/*  15:    */   extends RecordReader<K, V>
/*  16:    */ {
/*  17:    */   private final FileSplit fileSplit;
/*  18:    */   private final RecordReader<K, V> delegate;
/*  19:    */   
/*  20:    */   protected CombineFileRecordReaderWrapper(FileInputFormat<K, V> inputFormat, CombineFileSplit split, TaskAttemptContext context, Integer idx)
/*  21:    */     throws IOException, InterruptedException
/*  22:    */   {
/*  23: 55 */     this.fileSplit = new FileSplit(split.getPath(idx.intValue()), split.getOffset(idx.intValue()), split.getLength(idx.intValue()), split.getLocations());
/*  24:    */     
/*  25:    */ 
/*  26:    */ 
/*  27:    */ 
/*  28: 60 */     this.delegate = inputFormat.createRecordReader(this.fileSplit, context);
/*  29:    */   }
/*  30:    */   
/*  31:    */   public void initialize(InputSplit split, TaskAttemptContext context)
/*  32:    */     throws IOException, InterruptedException
/*  33:    */   {
/*  34: 67 */     assert (fileSplitIsValid(context));
/*  35:    */     
/*  36: 69 */     this.delegate.initialize(this.fileSplit, context);
/*  37:    */   }
/*  38:    */   
/*  39:    */   private boolean fileSplitIsValid(TaskAttemptContext context)
/*  40:    */   {
/*  41: 73 */     Configuration conf = context.getConfiguration();
/*  42: 74 */     long offset = conf.getLong("mapreduce.map.input.start", 0L);
/*  43: 75 */     if (this.fileSplit.getStart() != offset) {
/*  44: 76 */       return false;
/*  45:    */     }
/*  46: 78 */     long length = conf.getLong("mapreduce.map.input.length", 0L);
/*  47: 79 */     if (this.fileSplit.getLength() != length) {
/*  48: 80 */       return false;
/*  49:    */     }
/*  50: 82 */     String path = conf.get("mapreduce.map.input.file");
/*  51: 83 */     if (!this.fileSplit.getPath().toString().equals(path)) {
/*  52: 84 */       return false;
/*  53:    */     }
/*  54: 86 */     return true;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public boolean nextKeyValue()
/*  58:    */     throws IOException, InterruptedException
/*  59:    */   {
/*  60: 90 */     return this.delegate.nextKeyValue();
/*  61:    */   }
/*  62:    */   
/*  63:    */   public K getCurrentKey()
/*  64:    */     throws IOException, InterruptedException
/*  65:    */   {
/*  66: 94 */     return this.delegate.getCurrentKey();
/*  67:    */   }
/*  68:    */   
/*  69:    */   public V getCurrentValue()
/*  70:    */     throws IOException, InterruptedException
/*  71:    */   {
/*  72: 98 */     return this.delegate.getCurrentValue();
/*  73:    */   }
/*  74:    */   
/*  75:    */   public float getProgress()
/*  76:    */     throws IOException, InterruptedException
/*  77:    */   {
/*  78:102 */     return this.delegate.getProgress();
/*  79:    */   }
/*  80:    */   
/*  81:    */   public void close()
/*  82:    */     throws IOException
/*  83:    */   {
/*  84:106 */     this.delegate.close();
/*  85:    */   }
/*  86:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.lib.input.CombineFileRecordReaderWrapper
 * JD-Core Version:    0.7.0.1
 */