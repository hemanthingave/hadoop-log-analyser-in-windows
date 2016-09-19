/*   1:    */ package org.apache.hadoop.mapreduce.lib.input;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.InputStream;
/*   5:    */ import org.apache.commons.logging.Log;
/*   6:    */ import org.apache.commons.logging.LogFactory;
/*   7:    */ import org.apache.hadoop.classification.InterfaceAudience.Private;
/*   8:    */ import org.apache.hadoop.classification.InterfaceStability.Evolving;
/*   9:    */ import org.apache.hadoop.conf.Configuration;
/*  10:    */ import org.apache.hadoop.fs.FSDataInputStream;
/*  11:    */ import org.apache.hadoop.fs.FileSystem;
/*  12:    */ import org.apache.hadoop.fs.Path;
/*  13:    */ import org.apache.hadoop.fs.Seekable;
/*  14:    */ import org.apache.hadoop.io.BytesWritable;
/*  15:    */ import org.apache.hadoop.io.LongWritable;
/*  16:    */ import org.apache.hadoop.io.compress.CodecPool;
/*  17:    */ import org.apache.hadoop.io.compress.CompressionCodec;
/*  18:    */ import org.apache.hadoop.io.compress.CompressionCodecFactory;
/*  19:    */ import org.apache.hadoop.io.compress.CompressionInputStream;
/*  20:    */ import org.apache.hadoop.io.compress.Decompressor;
/*  21:    */ import org.apache.hadoop.mapreduce.InputSplit;
/*  22:    */ import org.apache.hadoop.mapreduce.RecordReader;
/*  23:    */ import org.apache.hadoop.mapreduce.TaskAttemptContext;
/*  24:    */ 
/*  25:    */ @InterfaceAudience.Private
/*  26:    */ @InterfaceStability.Evolving
/*  27:    */ public class FixedLengthRecordReader
/*  28:    */   extends RecordReader<LongWritable, BytesWritable>
/*  29:    */ {
/*  30: 52 */   private static final Log LOG = LogFactory.getLog(FixedLengthRecordReader.class);
/*  31:    */   private int recordLength;
/*  32:    */   private long start;
/*  33:    */   private long pos;
/*  34:    */   private long end;
/*  35:    */   private long numRecordsRemainingInSplit;
/*  36:    */   private FSDataInputStream fileIn;
/*  37:    */   private Seekable filePosition;
/*  38:    */   private LongWritable key;
/*  39:    */   private BytesWritable value;
/*  40:    */   private boolean isCompressedInput;
/*  41:    */   private Decompressor decompressor;
/*  42:    */   private InputStream inputStream;
/*  43:    */   
/*  44:    */   public FixedLengthRecordReader(int recordLength)
/*  45:    */   {
/*  46: 69 */     this.recordLength = recordLength;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public void initialize(InputSplit genericSplit, TaskAttemptContext context)
/*  50:    */     throws IOException
/*  51:    */   {
/*  52: 75 */     FileSplit split = (FileSplit)genericSplit;
/*  53: 76 */     Configuration job = context.getConfiguration();
/*  54: 77 */     Path file = split.getPath();
/*  55: 78 */     initialize(job, split.getStart(), split.getLength(), file);
/*  56:    */   }
/*  57:    */   
/*  58:    */   public void initialize(Configuration job, long splitStart, long splitLength, Path file)
/*  59:    */     throws IOException
/*  60:    */   {
/*  61: 84 */     this.start = splitStart;
/*  62: 85 */     this.end = (this.start + splitLength);
/*  63: 86 */     long partialRecordLength = this.start % this.recordLength;
/*  64: 87 */     long numBytesToSkip = 0L;
/*  65: 88 */     if (partialRecordLength != 0L) {
/*  66: 89 */       numBytesToSkip = this.recordLength - partialRecordLength;
/*  67:    */     }
/*  68: 93 */     FileSystem fs = file.getFileSystem(job);
/*  69: 94 */     this.fileIn = fs.open(file);
/*  70:    */     
/*  71: 96 */     CompressionCodec codec = new CompressionCodecFactory(job).getCodec(file);
/*  72: 97 */     if (null != codec)
/*  73:    */     {
/*  74: 98 */       this.isCompressedInput = true;
/*  75: 99 */       this.decompressor = CodecPool.getDecompressor(codec);
/*  76:100 */       CompressionInputStream cIn = codec.createInputStream(this.fileIn, this.decompressor);
/*  77:    */       
/*  78:102 */       this.filePosition = cIn;
/*  79:103 */       this.inputStream = cIn;
/*  80:104 */       this.numRecordsRemainingInSplit = 9223372036854775807L;
/*  81:105 */       LOG.info("Compressed input; cannot compute number of records in the split");
/*  82:    */     }
/*  83:    */     else
/*  84:    */     {
/*  85:108 */       this.fileIn.seek(this.start);
/*  86:109 */       this.filePosition = this.fileIn;
/*  87:110 */       this.inputStream = this.fileIn;
/*  88:111 */       long splitSize = this.end - this.start - numBytesToSkip;
/*  89:112 */       this.numRecordsRemainingInSplit = ((splitSize + this.recordLength - 1L) / this.recordLength);
/*  90:113 */       if (this.numRecordsRemainingInSplit < 0L) {
/*  91:114 */         this.numRecordsRemainingInSplit = 0L;
/*  92:    */       }
/*  93:116 */       LOG.info("Expecting " + this.numRecordsRemainingInSplit + " records each with a length of " + this.recordLength + " bytes in the split with an effective size of " + splitSize + " bytes");
/*  94:    */     }
/*  95:121 */     if (numBytesToSkip != 0L) {
/*  96:122 */       this.start += this.inputStream.skip(numBytesToSkip);
/*  97:    */     }
/*  98:124 */     this.pos = this.start;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public synchronized boolean nextKeyValue()
/* 102:    */     throws IOException
/* 103:    */   {
/* 104:129 */     if (this.key == null) {
/* 105:130 */       this.key = new LongWritable();
/* 106:    */     }
/* 107:132 */     if (this.value == null) {
/* 108:133 */       this.value = new BytesWritable(new byte[this.recordLength]);
/* 109:    */     }
/* 110:135 */     boolean dataRead = false;
/* 111:136 */     this.value.setSize(this.recordLength);
/* 112:137 */     byte[] record = this.value.getBytes();
/* 113:138 */     if (this.numRecordsRemainingInSplit > 0L)
/* 114:    */     {
/* 115:139 */       this.key.set(this.pos);
/* 116:140 */       int offset = 0;
/* 117:141 */       int numBytesToRead = this.recordLength;
/* 118:142 */       int numBytesRead = 0;
/* 119:143 */       while (numBytesToRead > 0)
/* 120:    */       {
/* 121:144 */         numBytesRead = this.inputStream.read(record, offset, numBytesToRead);
/* 122:145 */         if (numBytesRead == -1) {
/* 123:    */           break;
/* 124:    */         }
/* 125:149 */         offset += numBytesRead;
/* 126:150 */         numBytesToRead -= numBytesRead;
/* 127:    */       }
/* 128:152 */       numBytesRead = this.recordLength - numBytesToRead;
/* 129:153 */       this.pos += numBytesRead;
/* 130:154 */       if (numBytesRead > 0)
/* 131:    */       {
/* 132:155 */         dataRead = true;
/* 133:156 */         if (numBytesRead >= this.recordLength)
/* 134:    */         {
/* 135:157 */           if (!this.isCompressedInput) {
/* 136:158 */             this.numRecordsRemainingInSplit -= 1L;
/* 137:    */           }
/* 138:    */         }
/* 139:    */         else {
/* 140:161 */           throw new IOException("Partial record(length = " + numBytesRead + ") found at the end of split.");
/* 141:    */         }
/* 142:    */       }
/* 143:    */       else
/* 144:    */       {
/* 145:165 */         this.numRecordsRemainingInSplit = 0L;
/* 146:    */       }
/* 147:    */     }
/* 148:168 */     return dataRead;
/* 149:    */   }
/* 150:    */   
/* 151:    */   public LongWritable getCurrentKey()
/* 152:    */   {
/* 153:173 */     return this.key;
/* 154:    */   }
/* 155:    */   
/* 156:    */   public BytesWritable getCurrentValue()
/* 157:    */   {
/* 158:178 */     return this.value;
/* 159:    */   }
/* 160:    */   
/* 161:    */   public synchronized float getProgress()
/* 162:    */     throws IOException
/* 163:    */   {
/* 164:183 */     if (this.start == this.end) {
/* 165:184 */       return 0.0F;
/* 166:    */     }
/* 167:186 */     return Math.min(1.0F, (float)(getFilePosition() - this.start) / (float)(this.end - this.start));
/* 168:    */   }
/* 169:    */   
/* 170:    */   public synchronized void close()
/* 171:    */     throws IOException
/* 172:    */   {
/* 173:    */     try
/* 174:    */     {
/* 175:193 */       if (this.inputStream != null)
/* 176:    */       {
/* 177:194 */         this.inputStream.close();
/* 178:195 */         this.inputStream = null;
/* 179:    */       }
/* 180:    */     }
/* 181:    */     finally
/* 182:    */     {
/* 183:198 */       if (this.decompressor != null)
/* 184:    */       {
/* 185:199 */         CodecPool.returnDecompressor(this.decompressor);
/* 186:200 */         this.decompressor = null;
/* 187:    */       }
/* 188:    */     }
/* 189:    */   }
/* 190:    */   
/* 191:    */   public long getPos()
/* 192:    */   {
/* 193:207 */     return this.pos;
/* 194:    */   }
/* 195:    */   
/* 196:    */   private long getFilePosition()
/* 197:    */     throws IOException
/* 198:    */   {
/* 199:    */     long retVal;
/* 200:    */     long retVal;
/* 201:212 */     if ((this.isCompressedInput) && (null != this.filePosition)) {
/* 202:213 */       retVal = this.filePosition.getPos();
/* 203:    */     } else {
/* 204:215 */       retVal = this.pos;
/* 205:    */     }
/* 206:217 */     return retVal;
/* 207:    */   }
/* 208:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.lib.input.FixedLengthRecordReader
 * JD-Core Version:    0.7.0.1
 */