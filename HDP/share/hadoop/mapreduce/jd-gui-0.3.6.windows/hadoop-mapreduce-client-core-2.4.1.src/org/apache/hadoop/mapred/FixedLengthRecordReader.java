/*  1:   */ package org.apache.hadoop.mapred;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import org.apache.hadoop.classification.InterfaceAudience.Private;
/*  5:   */ import org.apache.hadoop.classification.InterfaceStability.Evolving;
/*  6:   */ import org.apache.hadoop.conf.Configuration;
/*  7:   */ import org.apache.hadoop.io.BytesWritable;
/*  8:   */ import org.apache.hadoop.io.LongWritable;
/*  9:   */ 
/* 10:   */ @InterfaceAudience.Private
/* 11:   */ @InterfaceStability.Evolving
/* 12:   */ public class FixedLengthRecordReader
/* 13:   */   implements RecordReader<LongWritable, BytesWritable>
/* 14:   */ {
/* 15:   */   private int recordLength;
/* 16:   */   private org.apache.hadoop.mapreduce.lib.input.FixedLengthRecordReader reader;
/* 17:   */   
/* 18:   */   public FixedLengthRecordReader(Configuration job, FileSplit split, int recordLength)
/* 19:   */     throws IOException
/* 20:   */   {
/* 21:44 */     this.recordLength = recordLength;
/* 22:45 */     this.reader = new org.apache.hadoop.mapreduce.lib.input.FixedLengthRecordReader(recordLength);
/* 23:   */     
/* 24:47 */     this.reader.initialize(job, split.getStart(), split.getLength(), split.getPath());
/* 25:   */   }
/* 26:   */   
/* 27:   */   public LongWritable createKey()
/* 28:   */   {
/* 29:53 */     return new LongWritable();
/* 30:   */   }
/* 31:   */   
/* 32:   */   public BytesWritable createValue()
/* 33:   */   {
/* 34:58 */     return new BytesWritable(new byte[this.recordLength]);
/* 35:   */   }
/* 36:   */   
/* 37:   */   public synchronized boolean next(LongWritable key, BytesWritable value)
/* 38:   */     throws IOException
/* 39:   */   {
/* 40:64 */     boolean dataRead = this.reader.nextKeyValue();
/* 41:65 */     if (dataRead)
/* 42:   */     {
/* 43:66 */       LongWritable newKey = this.reader.getCurrentKey();
/* 44:67 */       BytesWritable newValue = this.reader.getCurrentValue();
/* 45:68 */       key.set(newKey.get());
/* 46:69 */       value.set(newValue);
/* 47:   */     }
/* 48:71 */     return dataRead;
/* 49:   */   }
/* 50:   */   
/* 51:   */   public float getProgress()
/* 52:   */     throws IOException
/* 53:   */   {
/* 54:76 */     return this.reader.getProgress();
/* 55:   */   }
/* 56:   */   
/* 57:   */   public synchronized long getPos()
/* 58:   */     throws IOException
/* 59:   */   {
/* 60:81 */     return this.reader.getPos();
/* 61:   */   }
/* 62:   */   
/* 63:   */   public void close()
/* 64:   */     throws IOException
/* 65:   */   {
/* 66:86 */     this.reader.close();
/* 67:   */   }
/* 68:   */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.FixedLengthRecordReader
 * JD-Core Version:    0.7.0.1
 */