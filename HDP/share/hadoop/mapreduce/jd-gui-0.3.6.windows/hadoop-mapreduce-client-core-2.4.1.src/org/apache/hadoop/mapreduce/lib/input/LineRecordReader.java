/*   1:    */ package org.apache.hadoop.mapreduce.lib.input;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import org.apache.commons.logging.Log;
/*   5:    */ import org.apache.commons.logging.LogFactory;
/*   6:    */ import org.apache.hadoop.classification.InterfaceAudience.LimitedPrivate;
/*   7:    */ import org.apache.hadoop.classification.InterfaceStability.Evolving;
/*   8:    */ import org.apache.hadoop.conf.Configuration;
/*   9:    */ import org.apache.hadoop.fs.FSDataInputStream;
/*  10:    */ import org.apache.hadoop.fs.FileSystem;
/*  11:    */ import org.apache.hadoop.fs.Path;
/*  12:    */ import org.apache.hadoop.fs.Seekable;
/*  13:    */ import org.apache.hadoop.io.LongWritable;
/*  14:    */ import org.apache.hadoop.io.Text;
/*  15:    */ import org.apache.hadoop.io.compress.CodecPool;
/*  16:    */ import org.apache.hadoop.io.compress.CompressionCodec;
/*  17:    */ import org.apache.hadoop.io.compress.CompressionCodecFactory;
/*  18:    */ import org.apache.hadoop.io.compress.Decompressor;
/*  19:    */ import org.apache.hadoop.io.compress.SplitCompressionInputStream;
/*  20:    */ import org.apache.hadoop.io.compress.SplittableCompressionCodec;
/*  21:    */ import org.apache.hadoop.io.compress.SplittableCompressionCodec.READ_MODE;
/*  22:    */ import org.apache.hadoop.mapreduce.InputSplit;
/*  23:    */ import org.apache.hadoop.mapreduce.RecordReader;
/*  24:    */ import org.apache.hadoop.mapreduce.TaskAttemptContext;
/*  25:    */ 
/*  26:    */ @InterfaceAudience.LimitedPrivate({"MapReduce", "Pig"})
/*  27:    */ @InterfaceStability.Evolving
/*  28:    */ public class LineRecordReader
/*  29:    */   extends RecordReader<LongWritable, Text>
/*  30:    */ {
/*  31: 50 */   private static final Log LOG = LogFactory.getLog(LineRecordReader.class);
/*  32:    */   public static final String MAX_LINE_LENGTH = "mapreduce.input.linerecordreader.line.maxlength";
/*  33:    */   private long start;
/*  34:    */   private long pos;
/*  35:    */   private long end;
/*  36:    */   private SplitLineReader in;
/*  37:    */   private FSDataInputStream fileIn;
/*  38:    */   private Seekable filePosition;
/*  39:    */   private int maxLineLength;
/*  40:    */   private LongWritable key;
/*  41:    */   private Text value;
/*  42:    */   private boolean isCompressedInput;
/*  43:    */   private Decompressor decompressor;
/*  44:    */   private byte[] recordDelimiterBytes;
/*  45:    */   
/*  46:    */   public LineRecordReader() {}
/*  47:    */   
/*  48:    */   public LineRecordReader(byte[] recordDelimiter)
/*  49:    */   {
/*  50: 71 */     this.recordDelimiterBytes = recordDelimiter;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public void initialize(InputSplit genericSplit, TaskAttemptContext context)
/*  54:    */     throws IOException
/*  55:    */   {
/*  56: 76 */     FileSplit split = (FileSplit)genericSplit;
/*  57: 77 */     Configuration job = context.getConfiguration();
/*  58: 78 */     this.maxLineLength = job.getInt("mapreduce.input.linerecordreader.line.maxlength", 2147483647);
/*  59: 79 */     this.start = split.getStart();
/*  60: 80 */     this.end = (this.start + split.getLength());
/*  61: 81 */     Path file = split.getPath();
/*  62:    */     
/*  63:    */ 
/*  64: 84 */     FileSystem fs = file.getFileSystem(job);
/*  65: 85 */     this.fileIn = fs.open(file);
/*  66:    */     
/*  67: 87 */     CompressionCodec codec = new CompressionCodecFactory(job).getCodec(file);
/*  68: 88 */     if (null != codec)
/*  69:    */     {
/*  70: 89 */       this.isCompressedInput = true;
/*  71: 90 */       this.decompressor = CodecPool.getDecompressor(codec);
/*  72: 91 */       if ((codec instanceof SplittableCompressionCodec))
/*  73:    */       {
/*  74: 92 */         SplitCompressionInputStream cIn = ((SplittableCompressionCodec)codec).createInputStream(this.fileIn, this.decompressor, this.start, this.end, SplittableCompressionCodec.READ_MODE.BYBLOCK);
/*  75:    */         
/*  76:    */ 
/*  77:    */ 
/*  78: 96 */         this.in = new CompressedSplitLineReader(cIn, job, this.recordDelimiterBytes);
/*  79:    */         
/*  80: 98 */         this.start = cIn.getAdjustedStart();
/*  81: 99 */         this.end = cIn.getAdjustedEnd();
/*  82:100 */         this.filePosition = cIn;
/*  83:    */       }
/*  84:    */       else
/*  85:    */       {
/*  86:102 */         this.in = new SplitLineReader(codec.createInputStream(this.fileIn, this.decompressor), job, this.recordDelimiterBytes);
/*  87:    */         
/*  88:104 */         this.filePosition = this.fileIn;
/*  89:    */       }
/*  90:    */     }
/*  91:    */     else
/*  92:    */     {
/*  93:107 */       this.fileIn.seek(this.start);
/*  94:108 */       this.in = new SplitLineReader(this.fileIn, job, this.recordDelimiterBytes);
/*  95:109 */       this.filePosition = this.fileIn;
/*  96:    */     }
/*  97:114 */     if (this.start != 0L) {
/*  98:115 */       this.start += this.in.readLine(new Text(), 0, maxBytesToConsume(this.start));
/*  99:    */     }
/* 100:117 */     this.pos = this.start;
/* 101:    */   }
/* 102:    */   
/* 103:    */   private int maxBytesToConsume(long pos)
/* 104:    */   {
/* 105:122 */     return this.isCompressedInput ? 2147483647 : (int)Math.min(2147483647L, this.end - pos);
/* 106:    */   }
/* 107:    */   
/* 108:    */   private long getFilePosition()
/* 109:    */     throws IOException
/* 110:    */   {
/* 111:    */     long retVal;
/* 112:    */     long retVal;
/* 113:129 */     if ((this.isCompressedInput) && (null != this.filePosition)) {
/* 114:130 */       retVal = this.filePosition.getPos();
/* 115:    */     } else {
/* 116:132 */       retVal = this.pos;
/* 117:    */     }
/* 118:134 */     return retVal;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public boolean nextKeyValue()
/* 122:    */     throws IOException
/* 123:    */   {
/* 124:138 */     if (this.key == null) {
/* 125:139 */       this.key = new LongWritable();
/* 126:    */     }
/* 127:141 */     this.key.set(this.pos);
/* 128:142 */     if (this.value == null) {
/* 129:143 */       this.value = new Text();
/* 130:    */     }
/* 131:145 */     int newSize = 0;
/* 132:148 */     while ((getFilePosition() <= this.end) || (this.in.needAdditionalRecordAfterSplit()))
/* 133:    */     {
/* 134:149 */       newSize = this.in.readLine(this.value, this.maxLineLength, Math.max(maxBytesToConsume(this.pos), this.maxLineLength));
/* 135:    */       
/* 136:151 */       this.pos += newSize;
/* 137:152 */       if (newSize < this.maxLineLength) {
/* 138:    */         break;
/* 139:    */       }
/* 140:157 */       LOG.info("Skipped line of size " + newSize + " at pos " + (this.pos - newSize));
/* 141:    */     }
/* 142:160 */     if (newSize == 0)
/* 143:    */     {
/* 144:161 */       this.key = null;
/* 145:162 */       this.value = null;
/* 146:163 */       return false;
/* 147:    */     }
/* 148:165 */     return true;
/* 149:    */   }
/* 150:    */   
/* 151:    */   public LongWritable getCurrentKey()
/* 152:    */   {
/* 153:171 */     return this.key;
/* 154:    */   }
/* 155:    */   
/* 156:    */   public Text getCurrentValue()
/* 157:    */   {
/* 158:176 */     return this.value;
/* 159:    */   }
/* 160:    */   
/* 161:    */   public float getProgress()
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
/* 175:192 */       if (this.in != null) {
/* 176:193 */         this.in.close();
/* 177:    */       }
/* 178:    */     }
/* 179:    */     finally
/* 180:    */     {
/* 181:196 */       if (this.decompressor != null) {
/* 182:197 */         CodecPool.returnDecompressor(this.decompressor);
/* 183:    */       }
/* 184:    */     }
/* 185:    */   }
/* 186:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.lib.input.LineRecordReader
 * JD-Core Version:    0.7.0.1
 */