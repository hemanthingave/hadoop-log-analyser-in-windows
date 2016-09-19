/*  1:   */ package org.apache.hadoop.mapred;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*  5:   */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*  6:   */ import org.apache.hadoop.conf.Configuration;
/*  7:   */ import org.apache.hadoop.io.Text;
/*  8:   */ import org.apache.hadoop.io.Writable;
/*  9:   */ import org.apache.hadoop.io.WritableComparable;
/* 10:   */ 
/* 11:   */ @InterfaceAudience.Public
/* 12:   */ @InterfaceStability.Stable
/* 13:   */ public class SequenceFileAsTextRecordReader
/* 14:   */   implements RecordReader<Text, Text>
/* 15:   */ {
/* 16:   */   private final SequenceFileRecordReader<WritableComparable, Writable> sequenceFileRecordReader;
/* 17:   */   private WritableComparable innerKey;
/* 18:   */   private Writable innerValue;
/* 19:   */   
/* 20:   */   public SequenceFileAsTextRecordReader(Configuration conf, FileSplit split)
/* 21:   */     throws IOException
/* 22:   */   {
/* 23:48 */     this.sequenceFileRecordReader = new SequenceFileRecordReader(conf, split);
/* 24:   */     
/* 25:50 */     this.innerKey = ((WritableComparable)this.sequenceFileRecordReader.createKey());
/* 26:51 */     this.innerValue = ((Writable)this.sequenceFileRecordReader.createValue());
/* 27:   */   }
/* 28:   */   
/* 29:   */   public Text createKey()
/* 30:   */   {
/* 31:55 */     return new Text();
/* 32:   */   }
/* 33:   */   
/* 34:   */   public Text createValue()
/* 35:   */   {
/* 36:59 */     return new Text();
/* 37:   */   }
/* 38:   */   
/* 39:   */   public synchronized boolean next(Text key, Text value)
/* 40:   */     throws IOException
/* 41:   */   {
/* 42:64 */     Text tKey = key;
/* 43:65 */     Text tValue = value;
/* 44:66 */     if (!this.sequenceFileRecordReader.next(this.innerKey, this.innerValue)) {
/* 45:67 */       return false;
/* 46:   */     }
/* 47:69 */     tKey.set(this.innerKey.toString());
/* 48:70 */     tValue.set(this.innerValue.toString());
/* 49:71 */     return true;
/* 50:   */   }
/* 51:   */   
/* 52:   */   public float getProgress()
/* 53:   */     throws IOException
/* 54:   */   {
/* 55:75 */     return this.sequenceFileRecordReader.getProgress();
/* 56:   */   }
/* 57:   */   
/* 58:   */   public synchronized long getPos()
/* 59:   */     throws IOException
/* 60:   */   {
/* 61:79 */     return this.sequenceFileRecordReader.getPos();
/* 62:   */   }
/* 63:   */   
/* 64:   */   public synchronized void close()
/* 65:   */     throws IOException
/* 66:   */   {
/* 67:83 */     this.sequenceFileRecordReader.close();
/* 68:   */   }
/* 69:   */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.SequenceFileAsTextRecordReader
 * JD-Core Version:    0.7.0.1
 */