/*   1:    */ package org.apache.hadoop.mapreduce.lib.input;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*   5:    */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*   6:    */ import org.apache.hadoop.conf.Configuration;
/*   7:    */ import org.apache.hadoop.fs.FileSystem;
/*   8:    */ import org.apache.hadoop.fs.Path;
/*   9:    */ import org.apache.hadoop.io.SequenceFile.Reader;
/*  10:    */ import org.apache.hadoop.mapreduce.InputSplit;
/*  11:    */ import org.apache.hadoop.mapreduce.RecordReader;
/*  12:    */ import org.apache.hadoop.mapreduce.TaskAttemptContext;
/*  13:    */ 
/*  14:    */ @InterfaceAudience.Public
/*  15:    */ @InterfaceStability.Stable
/*  16:    */ public class SequenceFileRecordReader<K, V>
/*  17:    */   extends RecordReader<K, V>
/*  18:    */ {
/*  19:    */   private SequenceFile.Reader in;
/*  20:    */   private long start;
/*  21:    */   private long end;
/*  22: 41 */   private boolean more = true;
/*  23: 42 */   private K key = null;
/*  24: 43 */   private V value = null;
/*  25:    */   protected Configuration conf;
/*  26:    */   
/*  27:    */   public void initialize(InputSplit split, TaskAttemptContext context)
/*  28:    */     throws IOException, InterruptedException
/*  29:    */   {
/*  30: 50 */     FileSplit fileSplit = (FileSplit)split;
/*  31: 51 */     this.conf = context.getConfiguration();
/*  32: 52 */     Path path = fileSplit.getPath();
/*  33: 53 */     FileSystem fs = path.getFileSystem(this.conf);
/*  34: 54 */     this.in = new SequenceFile.Reader(fs, path, this.conf);
/*  35: 55 */     this.end = (fileSplit.getStart() + fileSplit.getLength());
/*  36: 57 */     if (fileSplit.getStart() > this.in.getPosition()) {
/*  37: 58 */       this.in.sync(fileSplit.getStart());
/*  38:    */     }
/*  39: 61 */     this.start = this.in.getPosition();
/*  40: 62 */     this.more = (this.start < this.end);
/*  41:    */   }
/*  42:    */   
/*  43:    */   public boolean nextKeyValue()
/*  44:    */     throws IOException, InterruptedException
/*  45:    */   {
/*  46: 68 */     if (!this.more) {
/*  47: 69 */       return false;
/*  48:    */     }
/*  49: 71 */     long pos = this.in.getPosition();
/*  50: 72 */     this.key = this.in.next(this.key);
/*  51: 73 */     if ((this.key == null) || ((pos >= this.end) && (this.in.syncSeen())))
/*  52:    */     {
/*  53: 74 */       this.more = false;
/*  54: 75 */       this.key = null;
/*  55: 76 */       this.value = null;
/*  56:    */     }
/*  57:    */     else
/*  58:    */     {
/*  59: 78 */       this.value = this.in.getCurrentValue(this.value);
/*  60:    */     }
/*  61: 80 */     return this.more;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public K getCurrentKey()
/*  65:    */   {
/*  66: 85 */     return this.key;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public V getCurrentValue()
/*  70:    */   {
/*  71: 90 */     return this.value;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public float getProgress()
/*  75:    */     throws IOException
/*  76:    */   {
/*  77: 98 */     if (this.end == this.start) {
/*  78: 99 */       return 0.0F;
/*  79:    */     }
/*  80:101 */     return Math.min(1.0F, (float)(this.in.getPosition() - this.start) / (float)(this.end - this.start));
/*  81:    */   }
/*  82:    */   
/*  83:    */   public synchronized void close()
/*  84:    */     throws IOException
/*  85:    */   {
/*  86:105 */     this.in.close();
/*  87:    */   }
/*  88:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.lib.input.SequenceFileRecordReader
 * JD-Core Version:    0.7.0.1
 */