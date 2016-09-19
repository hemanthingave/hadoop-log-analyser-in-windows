/*   1:    */ package org.apache.hadoop.mapreduce.lib.input;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*   5:    */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*   6:    */ import org.apache.hadoop.conf.Configuration;
/*   7:    */ import org.apache.hadoop.fs.FileSystem;
/*   8:    */ import org.apache.hadoop.fs.Path;
/*   9:    */ import org.apache.hadoop.io.BytesWritable;
/*  10:    */ import org.apache.hadoop.io.DataOutputBuffer;
/*  11:    */ import org.apache.hadoop.io.SequenceFile.Reader;
/*  12:    */ import org.apache.hadoop.io.SequenceFile.ValueBytes;
/*  13:    */ import org.apache.hadoop.mapreduce.InputSplit;
/*  14:    */ import org.apache.hadoop.mapreduce.RecordReader;
/*  15:    */ import org.apache.hadoop.mapreduce.TaskAttemptContext;
/*  16:    */ 
/*  17:    */ @InterfaceAudience.Public
/*  18:    */ @InterfaceStability.Stable
/*  19:    */ public class SequenceFileAsBinaryInputFormat
/*  20:    */   extends SequenceFileInputFormat<BytesWritable, BytesWritable>
/*  21:    */ {
/*  22:    */   public RecordReader<BytesWritable, BytesWritable> createRecordReader(InputSplit split, TaskAttemptContext context)
/*  23:    */     throws IOException
/*  24:    */   {
/*  25: 50 */     return new SequenceFileAsBinaryRecordReader();
/*  26:    */   }
/*  27:    */   
/*  28:    */   public static class SequenceFileAsBinaryRecordReader
/*  29:    */     extends RecordReader<BytesWritable, BytesWritable>
/*  30:    */   {
/*  31:    */     private SequenceFile.Reader in;
/*  32:    */     private long start;
/*  33:    */     private long end;
/*  34: 61 */     private boolean done = false;
/*  35: 62 */     private DataOutputBuffer buffer = new DataOutputBuffer();
/*  36:    */     private SequenceFile.ValueBytes vbytes;
/*  37: 64 */     private BytesWritable key = null;
/*  38: 65 */     private BytesWritable value = null;
/*  39:    */     
/*  40:    */     public void initialize(InputSplit split, TaskAttemptContext context)
/*  41:    */       throws IOException, InterruptedException
/*  42:    */     {
/*  43: 69 */       Path path = ((FileSplit)split).getPath();
/*  44: 70 */       Configuration conf = context.getConfiguration();
/*  45: 71 */       FileSystem fs = path.getFileSystem(conf);
/*  46: 72 */       this.in = new SequenceFile.Reader(fs, path, conf);
/*  47: 73 */       this.end = (((FileSplit)split).getStart() + split.getLength());
/*  48: 74 */       if (((FileSplit)split).getStart() > this.in.getPosition()) {
/*  49: 75 */         this.in.sync(((FileSplit)split).getStart());
/*  50:    */       }
/*  51: 77 */       this.start = this.in.getPosition();
/*  52: 78 */       this.vbytes = this.in.createValueBytes();
/*  53: 79 */       this.done = (this.start >= this.end);
/*  54:    */     }
/*  55:    */     
/*  56:    */     public BytesWritable getCurrentKey()
/*  57:    */       throws IOException, InterruptedException
/*  58:    */     {
/*  59: 85 */       return this.key;
/*  60:    */     }
/*  61:    */     
/*  62:    */     public BytesWritable getCurrentValue()
/*  63:    */       throws IOException, InterruptedException
/*  64:    */     {
/*  65: 91 */       return this.value;
/*  66:    */     }
/*  67:    */     
/*  68:    */     public String getKeyClassName()
/*  69:    */     {
/*  70: 99 */       return this.in.getKeyClassName();
/*  71:    */     }
/*  72:    */     
/*  73:    */     public String getValueClassName()
/*  74:    */     {
/*  75:107 */       return this.in.getValueClassName();
/*  76:    */     }
/*  77:    */     
/*  78:    */     public synchronized boolean nextKeyValue()
/*  79:    */       throws IOException, InterruptedException
/*  80:    */     {
/*  81:115 */       if (this.done) {
/*  82:116 */         return false;
/*  83:    */       }
/*  84:118 */       long pos = this.in.getPosition();
/*  85:119 */       boolean eof = -1 == this.in.nextRawKey(this.buffer);
/*  86:120 */       if (!eof)
/*  87:    */       {
/*  88:121 */         if (this.key == null) {
/*  89:122 */           this.key = new BytesWritable();
/*  90:    */         }
/*  91:124 */         if (this.value == null) {
/*  92:125 */           this.value = new BytesWritable();
/*  93:    */         }
/*  94:127 */         this.key.set(this.buffer.getData(), 0, this.buffer.getLength());
/*  95:128 */         this.buffer.reset();
/*  96:129 */         this.in.nextRawValue(this.vbytes);
/*  97:130 */         this.vbytes.writeUncompressedBytes(this.buffer);
/*  98:131 */         this.value.set(this.buffer.getData(), 0, this.buffer.getLength());
/*  99:132 */         this.buffer.reset();
/* 100:    */       }
/* 101:134 */       return (this.done = (eof) || ((pos >= this.end) && (this.in.syncSeen())) ? 1 : 0) == 0;
/* 102:    */     }
/* 103:    */     
/* 104:    */     public void close()
/* 105:    */       throws IOException
/* 106:    */     {
/* 107:138 */       this.in.close();
/* 108:    */     }
/* 109:    */     
/* 110:    */     public float getProgress()
/* 111:    */       throws IOException, InterruptedException
/* 112:    */     {
/* 113:146 */       if (this.end == this.start) {
/* 114:147 */         return 0.0F;
/* 115:    */       }
/* 116:149 */       return Math.min(1.0F, (float)((this.in.getPosition() - this.start) / (this.end - this.start)));
/* 117:    */     }
/* 118:    */   }
/* 119:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.lib.input.SequenceFileAsBinaryInputFormat
 * JD-Core Version:    0.7.0.1
 */