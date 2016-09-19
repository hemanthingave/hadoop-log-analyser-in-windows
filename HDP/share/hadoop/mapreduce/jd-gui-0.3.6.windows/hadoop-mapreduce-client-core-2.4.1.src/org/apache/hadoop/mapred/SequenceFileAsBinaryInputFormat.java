/*   1:    */ package org.apache.hadoop.mapred;
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
/*  13:    */ 
/*  14:    */ @InterfaceAudience.Public
/*  15:    */ @InterfaceStability.Stable
/*  16:    */ public class SequenceFileAsBinaryInputFormat
/*  17:    */   extends SequenceFileInputFormat<BytesWritable, BytesWritable>
/*  18:    */ {
/*  19:    */   public RecordReader<BytesWritable, BytesWritable> getRecordReader(InputSplit split, JobConf job, Reporter reporter)
/*  20:    */     throws IOException
/*  21:    */   {
/*  22: 47 */     return new SequenceFileAsBinaryRecordReader(job, (FileSplit)split);
/*  23:    */   }
/*  24:    */   
/*  25:    */   public static class SequenceFileAsBinaryRecordReader
/*  26:    */     implements RecordReader<BytesWritable, BytesWritable>
/*  27:    */   {
/*  28:    */     private SequenceFile.Reader in;
/*  29:    */     private long start;
/*  30:    */     private long end;
/*  31: 58 */     private boolean done = false;
/*  32: 59 */     private DataOutputBuffer buffer = new DataOutputBuffer();
/*  33:    */     private SequenceFile.ValueBytes vbytes;
/*  34:    */     
/*  35:    */     public SequenceFileAsBinaryRecordReader(Configuration conf, FileSplit split)
/*  36:    */       throws IOException
/*  37:    */     {
/*  38: 64 */       Path path = split.getPath();
/*  39: 65 */       FileSystem fs = path.getFileSystem(conf);
/*  40: 66 */       this.in = new SequenceFile.Reader(fs, path, conf);
/*  41: 67 */       this.end = (split.getStart() + split.getLength());
/*  42: 68 */       if (split.getStart() > this.in.getPosition()) {
/*  43: 69 */         this.in.sync(split.getStart());
/*  44:    */       }
/*  45: 70 */       this.start = this.in.getPosition();
/*  46: 71 */       this.vbytes = this.in.createValueBytes();
/*  47: 72 */       this.done = (this.start >= this.end);
/*  48:    */     }
/*  49:    */     
/*  50:    */     public BytesWritable createKey()
/*  51:    */     {
/*  52: 76 */       return new BytesWritable();
/*  53:    */     }
/*  54:    */     
/*  55:    */     public BytesWritable createValue()
/*  56:    */     {
/*  57: 80 */       return new BytesWritable();
/*  58:    */     }
/*  59:    */     
/*  60:    */     public String getKeyClassName()
/*  61:    */     {
/*  62: 88 */       return this.in.getKeyClassName();
/*  63:    */     }
/*  64:    */     
/*  65:    */     public String getValueClassName()
/*  66:    */     {
/*  67: 96 */       return this.in.getValueClassName();
/*  68:    */     }
/*  69:    */     
/*  70:    */     public synchronized boolean next(BytesWritable key, BytesWritable val)
/*  71:    */       throws IOException
/*  72:    */     {
/*  73:104 */       if (this.done) {
/*  74:104 */         return false;
/*  75:    */       }
/*  76:105 */       long pos = this.in.getPosition();
/*  77:106 */       boolean eof = -1 == this.in.nextRawKey(this.buffer);
/*  78:107 */       if (!eof)
/*  79:    */       {
/*  80:108 */         key.set(this.buffer.getData(), 0, this.buffer.getLength());
/*  81:109 */         this.buffer.reset();
/*  82:110 */         this.in.nextRawValue(this.vbytes);
/*  83:111 */         this.vbytes.writeUncompressedBytes(this.buffer);
/*  84:112 */         val.set(this.buffer.getData(), 0, this.buffer.getLength());
/*  85:113 */         this.buffer.reset();
/*  86:    */       }
/*  87:115 */       return (this.done = (eof) || ((pos >= this.end) && (this.in.syncSeen())) ? 1 : 0) == 0;
/*  88:    */     }
/*  89:    */     
/*  90:    */     public long getPos()
/*  91:    */       throws IOException
/*  92:    */     {
/*  93:119 */       return this.in.getPosition();
/*  94:    */     }
/*  95:    */     
/*  96:    */     public void close()
/*  97:    */       throws IOException
/*  98:    */     {
/*  99:123 */       this.in.close();
/* 100:    */     }
/* 101:    */     
/* 102:    */     public float getProgress()
/* 103:    */       throws IOException
/* 104:    */     {
/* 105:131 */       if (this.end == this.start) {
/* 106:132 */         return 0.0F;
/* 107:    */       }
/* 108:134 */       return Math.min(1.0F, (float)((this.in.getPosition() - this.start) / (this.end - this.start)));
/* 109:    */     }
/* 110:    */   }
/* 111:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.SequenceFileAsBinaryInputFormat
 * JD-Core Version:    0.7.0.1
 */