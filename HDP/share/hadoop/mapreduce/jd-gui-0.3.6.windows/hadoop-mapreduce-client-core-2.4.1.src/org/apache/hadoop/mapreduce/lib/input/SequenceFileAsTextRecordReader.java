/*  1:   */ package org.apache.hadoop.mapreduce.lib.input;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*  5:   */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*  6:   */ import org.apache.hadoop.io.Text;
/*  7:   */ import org.apache.hadoop.io.Writable;
/*  8:   */ import org.apache.hadoop.io.WritableComparable;
/*  9:   */ import org.apache.hadoop.mapreduce.InputSplit;
/* 10:   */ import org.apache.hadoop.mapreduce.RecordReader;
/* 11:   */ import org.apache.hadoop.mapreduce.TaskAttemptContext;
/* 12:   */ 
/* 13:   */ @InterfaceAudience.Public
/* 14:   */ @InterfaceStability.Stable
/* 15:   */ public class SequenceFileAsTextRecordReader
/* 16:   */   extends RecordReader<Text, Text>
/* 17:   */ {
/* 18:   */   private final SequenceFileRecordReader<WritableComparable<?>, Writable> sequenceFileRecordReader;
/* 19:   */   private Text key;
/* 20:   */   private Text value;
/* 21:   */   
/* 22:   */   public SequenceFileAsTextRecordReader()
/* 23:   */     throws IOException
/* 24:   */   {
/* 25:50 */     this.sequenceFileRecordReader = new SequenceFileRecordReader();
/* 26:   */   }
/* 27:   */   
/* 28:   */   public void initialize(InputSplit split, TaskAttemptContext context)
/* 29:   */     throws IOException, InterruptedException
/* 30:   */   {
/* 31:56 */     this.sequenceFileRecordReader.initialize(split, context);
/* 32:   */   }
/* 33:   */   
/* 34:   */   public Text getCurrentKey()
/* 35:   */     throws IOException, InterruptedException
/* 36:   */   {
/* 37:62 */     return this.key;
/* 38:   */   }
/* 39:   */   
/* 40:   */   public Text getCurrentValue()
/* 41:   */     throws IOException, InterruptedException
/* 42:   */   {
/* 43:68 */     return this.value;
/* 44:   */   }
/* 45:   */   
/* 46:   */   public synchronized boolean nextKeyValue()
/* 47:   */     throws IOException, InterruptedException
/* 48:   */   {
/* 49:74 */     if (!this.sequenceFileRecordReader.nextKeyValue()) {
/* 50:75 */       return false;
/* 51:   */     }
/* 52:77 */     if (this.key == null) {
/* 53:78 */       this.key = new Text();
/* 54:   */     }
/* 55:80 */     if (this.value == null) {
/* 56:81 */       this.value = new Text();
/* 57:   */     }
/* 58:83 */     this.key.set(((WritableComparable)this.sequenceFileRecordReader.getCurrentKey()).toString());
/* 59:84 */     this.value.set(((Writable)this.sequenceFileRecordReader.getCurrentValue()).toString());
/* 60:85 */     return true;
/* 61:   */   }
/* 62:   */   
/* 63:   */   public float getProgress()
/* 64:   */     throws IOException, InterruptedException
/* 65:   */   {
/* 66:89 */     return this.sequenceFileRecordReader.getProgress();
/* 67:   */   }
/* 68:   */   
/* 69:   */   public synchronized void close()
/* 70:   */     throws IOException
/* 71:   */   {
/* 72:93 */     this.sequenceFileRecordReader.close();
/* 73:   */   }
/* 74:   */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.lib.input.SequenceFileAsTextRecordReader
 * JD-Core Version:    0.7.0.1
 */