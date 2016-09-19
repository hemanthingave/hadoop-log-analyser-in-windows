/*   1:    */ package org.apache.hadoop.mapred;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.InputStream;
/*   5:    */ import org.apache.commons.logging.Log;
/*   6:    */ import org.apache.commons.logging.LogFactory;
/*   7:    */ import org.apache.hadoop.classification.InterfaceAudience.LimitedPrivate;
/*   8:    */ import org.apache.hadoop.classification.InterfaceStability.Unstable;
/*   9:    */ import org.apache.hadoop.conf.Configuration;
/*  10:    */ import org.apache.hadoop.fs.FSDataInputStream;
/*  11:    */ import org.apache.hadoop.fs.FileSystem;
/*  12:    */ import org.apache.hadoop.fs.Path;
/*  13:    */ import org.apache.hadoop.fs.Seekable;
/*  14:    */ import org.apache.hadoop.io.LongWritable;
/*  15:    */ import org.apache.hadoop.io.Text;
/*  16:    */ import org.apache.hadoop.io.compress.CodecPool;
/*  17:    */ import org.apache.hadoop.io.compress.CompressionCodec;
/*  18:    */ import org.apache.hadoop.io.compress.CompressionCodecFactory;
/*  19:    */ import org.apache.hadoop.io.compress.Decompressor;
/*  20:    */ import org.apache.hadoop.io.compress.SplitCompressionInputStream;
/*  21:    */ import org.apache.hadoop.io.compress.SplittableCompressionCodec;
/*  22:    */ import org.apache.hadoop.io.compress.SplittableCompressionCodec.READ_MODE;
/*  23:    */ import org.apache.hadoop.mapreduce.lib.input.CompressedSplitLineReader;
/*  24:    */ import org.apache.hadoop.mapreduce.lib.input.SplitLineReader;
/*  25:    */ import org.apache.hadoop.util.LineReader;
/*  26:    */ 
/*  27:    */ @InterfaceAudience.LimitedPrivate({"MapReduce", "Pig"})
/*  28:    */ @InterfaceStability.Unstable
/*  29:    */ public class LineRecordReader
/*  30:    */   implements RecordReader<LongWritable, Text>
/*  31:    */ {
/*  32: 50 */   private static final Log LOG = LogFactory.getLog(LineRecordReader.class.getName());
/*  33: 53 */   private CompressionCodecFactory compressionCodecs = null;
/*  34:    */   private long start;
/*  35:    */   private long pos;
/*  36:    */   private long end;
/*  37:    */   private SplitLineReader in;
/*  38:    */   private FSDataInputStream fileIn;
/*  39:    */   private final Seekable filePosition;
/*  40:    */   int maxLineLength;
/*  41:    */   private CompressionCodec codec;
/*  42:    */   private Decompressor decompressor;
/*  43:    */   
/*  44:    */   @Deprecated
/*  45:    */   public static class LineReader
/*  46:    */     extends LineReader
/*  47:    */   {
/*  48:    */     LineReader(InputStream in)
/*  49:    */     {
/*  50: 71 */       super();
/*  51:    */     }
/*  52:    */     
/*  53:    */     LineReader(InputStream in, int bufferSize)
/*  54:    */     {
/*  55: 74 */       super(bufferSize);
/*  56:    */     }
/*  57:    */     
/*  58:    */     public LineReader(InputStream in, Configuration conf)
/*  59:    */       throws IOException
/*  60:    */     {
/*  61: 77 */       super(conf);
/*  62:    */     }
/*  63:    */     
/*  64:    */     LineReader(InputStream in, byte[] recordDelimiter)
/*  65:    */     {
/*  66: 80 */       super(recordDelimiter);
/*  67:    */     }
/*  68:    */     
/*  69:    */     LineReader(InputStream in, int bufferSize, byte[] recordDelimiter)
/*  70:    */     {
/*  71: 83 */       super(bufferSize, recordDelimiter);
/*  72:    */     }
/*  73:    */     
/*  74:    */     public LineReader(InputStream in, Configuration conf, byte[] recordDelimiter)
/*  75:    */       throws IOException
/*  76:    */     {
/*  77: 87 */       super(conf, recordDelimiter);
/*  78:    */     }
/*  79:    */   }
/*  80:    */   
/*  81:    */   public LineRecordReader(Configuration job, FileSplit split)
/*  82:    */     throws IOException
/*  83:    */   {
/*  84: 93 */     this(job, split, null);
/*  85:    */   }
/*  86:    */   
/*  87:    */   public LineRecordReader(Configuration job, FileSplit split, byte[] recordDelimiter)
/*  88:    */     throws IOException
/*  89:    */   {
/*  90: 98 */     this.maxLineLength = job.getInt("mapreduce.input.linerecordreader.line.maxlength", 2147483647);
/*  91:    */     
/*  92:100 */     this.start = split.getStart();
/*  93:101 */     this.end = (this.start + split.getLength());
/*  94:102 */     Path file = split.getPath();
/*  95:103 */     this.compressionCodecs = new CompressionCodecFactory(job);
/*  96:104 */     this.codec = this.compressionCodecs.getCodec(file);
/*  97:    */     
/*  98:    */ 
/*  99:107 */     FileSystem fs = file.getFileSystem(job);
/* 100:108 */     this.fileIn = fs.open(file);
/* 101:109 */     if (isCompressedInput())
/* 102:    */     {
/* 103:110 */       this.decompressor = CodecPool.getDecompressor(this.codec);
/* 104:111 */       if ((this.codec instanceof SplittableCompressionCodec))
/* 105:    */       {
/* 106:112 */         SplitCompressionInputStream cIn = ((SplittableCompressionCodec)this.codec).createInputStream(this.fileIn, this.decompressor, this.start, this.end, SplittableCompressionCodec.READ_MODE.BYBLOCK);
/* 107:    */         
/* 108:    */ 
/* 109:    */ 
/* 110:116 */         this.in = new CompressedSplitLineReader(cIn, job, recordDelimiter);
/* 111:117 */         this.start = cIn.getAdjustedStart();
/* 112:118 */         this.end = cIn.getAdjustedEnd();
/* 113:119 */         this.filePosition = cIn;
/* 114:    */       }
/* 115:    */       else
/* 116:    */       {
/* 117:121 */         this.in = new SplitLineReader(this.codec.createInputStream(this.fileIn, this.decompressor), job, recordDelimiter);
/* 118:    */         
/* 119:123 */         this.filePosition = this.fileIn;
/* 120:    */       }
/* 121:    */     }
/* 122:    */     else
/* 123:    */     {
/* 124:126 */       this.fileIn.seek(this.start);
/* 125:127 */       this.in = new SplitLineReader(this.fileIn, job, recordDelimiter);
/* 126:128 */       this.filePosition = this.fileIn;
/* 127:    */     }
/* 128:133 */     if (this.start != 0L) {
/* 129:134 */       this.start += this.in.readLine(new Text(), 0, maxBytesToConsume(this.start));
/* 130:    */     }
/* 131:136 */     this.pos = this.start;
/* 132:    */   }
/* 133:    */   
/* 134:    */   public LineRecordReader(InputStream in, long offset, long endOffset, int maxLineLength)
/* 135:    */   {
/* 136:141 */     this(in, offset, endOffset, maxLineLength, null);
/* 137:    */   }
/* 138:    */   
/* 139:    */   public LineRecordReader(InputStream in, long offset, long endOffset, int maxLineLength, byte[] recordDelimiter)
/* 140:    */   {
/* 141:146 */     this.maxLineLength = maxLineLength;
/* 142:147 */     this.in = new SplitLineReader(in, recordDelimiter);
/* 143:148 */     this.start = offset;
/* 144:149 */     this.pos = offset;
/* 145:150 */     this.end = endOffset;
/* 146:151 */     this.filePosition = null;
/* 147:    */   }
/* 148:    */   
/* 149:    */   public LineRecordReader(InputStream in, long offset, long endOffset, Configuration job)
/* 150:    */     throws IOException
/* 151:    */   {
/* 152:157 */     this(in, offset, endOffset, job, null);
/* 153:    */   }
/* 154:    */   
/* 155:    */   public LineRecordReader(InputStream in, long offset, long endOffset, Configuration job, byte[] recordDelimiter)
/* 156:    */     throws IOException
/* 157:    */   {
/* 158:163 */     this.maxLineLength = job.getInt("mapreduce.input.linerecordreader.line.maxlength", 2147483647);
/* 159:    */     
/* 160:165 */     this.in = new SplitLineReader(in, job, recordDelimiter);
/* 161:166 */     this.start = offset;
/* 162:167 */     this.pos = offset;
/* 163:168 */     this.end = endOffset;
/* 164:169 */     this.filePosition = null;
/* 165:    */   }
/* 166:    */   
/* 167:    */   public LongWritable createKey()
/* 168:    */   {
/* 169:173 */     return new LongWritable();
/* 170:    */   }
/* 171:    */   
/* 172:    */   public Text createValue()
/* 173:    */   {
/* 174:177 */     return new Text();
/* 175:    */   }
/* 176:    */   
/* 177:    */   private boolean isCompressedInput()
/* 178:    */   {
/* 179:181 */     return this.codec != null;
/* 180:    */   }
/* 181:    */   
/* 182:    */   private int maxBytesToConsume(long pos)
/* 183:    */   {
/* 184:185 */     return isCompressedInput() ? 2147483647 : (int)Math.min(2147483647L, this.end - pos);
/* 185:    */   }
/* 186:    */   
/* 187:    */   private long getFilePosition()
/* 188:    */     throws IOException
/* 189:    */   {
/* 190:    */     long retVal;
/* 191:    */     long retVal;
/* 192:192 */     if ((isCompressedInput()) && (null != this.filePosition)) {
/* 193:193 */       retVal = this.filePosition.getPos();
/* 194:    */     } else {
/* 195:195 */       retVal = this.pos;
/* 196:    */     }
/* 197:197 */     return retVal;
/* 198:    */   }
/* 199:    */   
/* 200:    */   public synchronized boolean next(LongWritable key, Text value)
/* 201:    */     throws IOException
/* 202:    */   {
/* 203:206 */     while ((getFilePosition() <= this.end) || (this.in.needAdditionalRecordAfterSplit()))
/* 204:    */     {
/* 205:207 */       key.set(this.pos);
/* 206:    */       
/* 207:209 */       int newSize = this.in.readLine(value, this.maxLineLength, Math.max(maxBytesToConsume(this.pos), this.maxLineLength));
/* 208:211 */       if (newSize == 0) {
/* 209:212 */         return false;
/* 210:    */       }
/* 211:214 */       this.pos += newSize;
/* 212:215 */       if (newSize < this.maxLineLength) {
/* 213:216 */         return true;
/* 214:    */       }
/* 215:220 */       LOG.info("Skipped line of size " + newSize + " at pos " + (this.pos - newSize));
/* 216:    */     }
/* 217:223 */     return false;
/* 218:    */   }
/* 219:    */   
/* 220:    */   public synchronized float getProgress()
/* 221:    */     throws IOException
/* 222:    */   {
/* 223:230 */     if (this.start == this.end) {
/* 224:231 */       return 0.0F;
/* 225:    */     }
/* 226:233 */     return Math.min(1.0F, (float)(getFilePosition() - this.start) / (float)(this.end - this.start));
/* 227:    */   }
/* 228:    */   
/* 229:    */   public synchronized long getPos()
/* 230:    */     throws IOException
/* 231:    */   {
/* 232:238 */     return this.pos;
/* 233:    */   }
/* 234:    */   
/* 235:    */   public synchronized void close()
/* 236:    */     throws IOException
/* 237:    */   {
/* 238:    */     try
/* 239:    */     {
/* 240:243 */       if (this.in != null) {
/* 241:244 */         this.in.close();
/* 242:    */       }
/* 243:    */     }
/* 244:    */     finally
/* 245:    */     {
/* 246:247 */       if (this.decompressor != null) {
/* 247:248 */         CodecPool.returnDecompressor(this.decompressor);
/* 248:    */       }
/* 249:    */     }
/* 250:    */   }
/* 251:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.LineRecordReader
 * JD-Core Version:    0.7.0.1
 */