/*   1:    */ package org.apache.hadoop.mapred;
/*   2:    */ 
/*   3:    */ import java.io.DataInput;
/*   4:    */ import java.io.DataInputStream;
/*   5:    */ import java.io.DataOutputStream;
/*   6:    */ import java.io.EOFException;
/*   7:    */ import java.io.IOException;
/*   8:    */ import java.io.InputStream;
/*   9:    */ import org.apache.commons.logging.Log;
/*  10:    */ import org.apache.commons.logging.LogFactory;
/*  11:    */ import org.apache.hadoop.classification.InterfaceAudience.Private;
/*  12:    */ import org.apache.hadoop.classification.InterfaceStability.Unstable;
/*  13:    */ import org.apache.hadoop.conf.Configuration;
/*  14:    */ import org.apache.hadoop.fs.FSDataInputStream;
/*  15:    */ import org.apache.hadoop.fs.FSDataOutputStream;
/*  16:    */ import org.apache.hadoop.fs.FileStatus;
/*  17:    */ import org.apache.hadoop.fs.FileSystem;
/*  18:    */ import org.apache.hadoop.fs.Path;
/*  19:    */ import org.apache.hadoop.io.DataInputBuffer;
/*  20:    */ import org.apache.hadoop.io.DataOutputBuffer;
/*  21:    */ import org.apache.hadoop.io.IOUtils;
/*  22:    */ import org.apache.hadoop.io.WritableUtils;
/*  23:    */ import org.apache.hadoop.io.compress.CodecPool;
/*  24:    */ import org.apache.hadoop.io.compress.CompressionCodec;
/*  25:    */ import org.apache.hadoop.io.compress.CompressionOutputStream;
/*  26:    */ import org.apache.hadoop.io.compress.Compressor;
/*  27:    */ import org.apache.hadoop.io.compress.Decompressor;
/*  28:    */ import org.apache.hadoop.io.serializer.SerializationFactory;
/*  29:    */ import org.apache.hadoop.io.serializer.Serializer;
/*  30:    */ 
/*  31:    */ @InterfaceAudience.Private
/*  32:    */ @InterfaceStability.Unstable
/*  33:    */ public class IFile
/*  34:    */ {
/*  35: 59 */   private static final Log LOG = LogFactory.getLog(IFile.class);
/*  36:    */   public static final int EOF_MARKER = -1;
/*  37:    */   
/*  38:    */   @InterfaceAudience.Private
/*  39:    */   @InterfaceStability.Unstable
/*  40:    */   public static class Writer<K, V>
/*  41:    */   {
/*  42:    */     FSDataOutputStream out;
/*  43: 69 */     boolean ownOutputStream = false;
/*  44: 70 */     long start = 0L;
/*  45:    */     FSDataOutputStream rawOut;
/*  46:    */     CompressionOutputStream compressedOut;
/*  47:    */     Compressor compressor;
/*  48: 75 */     boolean compressOutput = false;
/*  49: 77 */     long decompressedBytesWritten = 0L;
/*  50: 78 */     long compressedBytesWritten = 0L;
/*  51: 81 */     private long numRecordsWritten = 0L;
/*  52:    */     private final Counters.Counter writtenRecordsCounter;
/*  53:    */     IFileOutputStream checksumOut;
/*  54:    */     Class<K> keyClass;
/*  55:    */     Class<V> valueClass;
/*  56:    */     Serializer<K> keySerializer;
/*  57:    */     Serializer<V> valueSerializer;
/*  58: 91 */     DataOutputBuffer buffer = new DataOutputBuffer();
/*  59:    */     
/*  60:    */     public Writer(Configuration conf, FileSystem fs, Path file, Class<K> keyClass, Class<V> valueClass, CompressionCodec codec, Counters.Counter writesCounter)
/*  61:    */       throws IOException
/*  62:    */     {
/*  63: 97 */       this(conf, fs.create(file), keyClass, valueClass, codec, writesCounter);
/*  64:    */       
/*  65: 99 */       this.ownOutputStream = true;
/*  66:    */     }
/*  67:    */     
/*  68:    */     protected Writer(Counters.Counter writesCounter)
/*  69:    */     {
/*  70:103 */       this.writtenRecordsCounter = writesCounter;
/*  71:    */     }
/*  72:    */     
/*  73:    */     public Writer(Configuration conf, FSDataOutputStream out, Class<K> keyClass, Class<V> valueClass, CompressionCodec codec, Counters.Counter writesCounter)
/*  74:    */       throws IOException
/*  75:    */     {
/*  76:110 */       this.writtenRecordsCounter = writesCounter;
/*  77:111 */       this.checksumOut = new IFileOutputStream(out);
/*  78:112 */       this.rawOut = out;
/*  79:113 */       this.start = this.rawOut.getPos();
/*  80:114 */       if (codec != null)
/*  81:    */       {
/*  82:115 */         this.compressor = CodecPool.getCompressor(codec);
/*  83:116 */         if (this.compressor != null)
/*  84:    */         {
/*  85:117 */           this.compressor.reset();
/*  86:118 */           this.compressedOut = codec.createOutputStream(this.checksumOut, this.compressor);
/*  87:119 */           this.out = new FSDataOutputStream(this.compressedOut, null);
/*  88:120 */           this.compressOutput = true;
/*  89:    */         }
/*  90:    */         else
/*  91:    */         {
/*  92:122 */           IFile.LOG.warn("Could not obtain compressor from CodecPool");
/*  93:123 */           this.out = new FSDataOutputStream(this.checksumOut, null);
/*  94:    */         }
/*  95:    */       }
/*  96:    */       else
/*  97:    */       {
/*  98:126 */         this.out = new FSDataOutputStream(this.checksumOut, null);
/*  99:    */       }
/* 100:129 */       this.keyClass = keyClass;
/* 101:130 */       this.valueClass = valueClass;
/* 102:132 */       if (keyClass != null)
/* 103:    */       {
/* 104:133 */         SerializationFactory serializationFactory = new SerializationFactory(conf);
/* 105:    */         
/* 106:135 */         this.keySerializer = serializationFactory.getSerializer(keyClass);
/* 107:136 */         this.keySerializer.open(this.buffer);
/* 108:137 */         this.valueSerializer = serializationFactory.getSerializer(valueClass);
/* 109:138 */         this.valueSerializer.open(this.buffer);
/* 110:    */       }
/* 111:    */     }
/* 112:    */     
/* 113:    */     public Writer(Configuration conf, FileSystem fs, Path file)
/* 114:    */       throws IOException
/* 115:    */     {
/* 116:144 */       this(conf, fs, file, null, null, null, null);
/* 117:    */     }
/* 118:    */     
/* 119:    */     public void close()
/* 120:    */       throws IOException
/* 121:    */     {
/* 122:152 */       if (this.keyClass != null)
/* 123:    */       {
/* 124:153 */         this.keySerializer.close();
/* 125:154 */         this.valueSerializer.close();
/* 126:    */       }
/* 127:158 */       WritableUtils.writeVInt(this.out, -1);
/* 128:159 */       WritableUtils.writeVInt(this.out, -1);
/* 129:160 */       this.decompressedBytesWritten += 2 * WritableUtils.getVIntSize(-1L);
/* 130:    */       
/* 131:    */ 
/* 132:163 */       this.out.flush();
/* 133:165 */       if (this.compressOutput)
/* 134:    */       {
/* 135:167 */         this.compressedOut.finish();
/* 136:168 */         this.compressedOut.resetState();
/* 137:    */       }
/* 138:172 */       if (this.ownOutputStream) {
/* 139:173 */         this.out.close();
/* 140:    */       } else {
/* 141:177 */         this.checksumOut.finish();
/* 142:    */       }
/* 143:180 */       this.compressedBytesWritten = (this.rawOut.getPos() - this.start);
/* 144:182 */       if (this.compressOutput)
/* 145:    */       {
/* 146:184 */         CodecPool.returnCompressor(this.compressor);
/* 147:185 */         this.compressor = null;
/* 148:    */       }
/* 149:188 */       this.out = null;
/* 150:189 */       if (this.writtenRecordsCounter != null) {
/* 151:190 */         this.writtenRecordsCounter.increment(this.numRecordsWritten);
/* 152:    */       }
/* 153:    */     }
/* 154:    */     
/* 155:    */     public void append(K key, V value)
/* 156:    */       throws IOException
/* 157:    */     {
/* 158:195 */       if (key.getClass() != this.keyClass) {
/* 159:196 */         throw new IOException("wrong key class: " + key.getClass() + " is not " + this.keyClass);
/* 160:    */       }
/* 161:198 */       if (value.getClass() != this.valueClass) {
/* 162:199 */         throw new IOException("wrong value class: " + value.getClass() + " is not " + this.valueClass);
/* 163:    */       }
/* 164:203 */       this.keySerializer.serialize(key);
/* 165:204 */       int keyLength = this.buffer.getLength();
/* 166:205 */       if (keyLength < 0) {
/* 167:206 */         throw new IOException("Negative key-length not allowed: " + keyLength + " for " + key);
/* 168:    */       }
/* 169:211 */       this.valueSerializer.serialize(value);
/* 170:212 */       int valueLength = this.buffer.getLength() - keyLength;
/* 171:213 */       if (valueLength < 0) {
/* 172:214 */         throw new IOException("Negative value-length not allowed: " + valueLength + " for " + value);
/* 173:    */       }
/* 174:219 */       WritableUtils.writeVInt(this.out, keyLength);
/* 175:220 */       WritableUtils.writeVInt(this.out, valueLength);
/* 176:221 */       this.out.write(this.buffer.getData(), 0, this.buffer.getLength());
/* 177:    */       
/* 178:    */ 
/* 179:224 */       this.buffer.reset();
/* 180:    */       
/* 181:    */ 
/* 182:227 */       this.decompressedBytesWritten += keyLength + valueLength + WritableUtils.getVIntSize(keyLength) + WritableUtils.getVIntSize(valueLength);
/* 183:    */       
/* 184:    */ 
/* 185:230 */       this.numRecordsWritten += 1L;
/* 186:    */     }
/* 187:    */     
/* 188:    */     public void append(DataInputBuffer key, DataInputBuffer value)
/* 189:    */       throws IOException
/* 190:    */     {
/* 191:235 */       int keyLength = key.getLength() - key.getPosition();
/* 192:236 */       if (keyLength < 0) {
/* 193:237 */         throw new IOException("Negative key-length not allowed: " + keyLength + " for " + key);
/* 194:    */       }
/* 195:241 */       int valueLength = value.getLength() - value.getPosition();
/* 196:242 */       if (valueLength < 0) {
/* 197:243 */         throw new IOException("Negative value-length not allowed: " + valueLength + " for " + value);
/* 198:    */       }
/* 199:247 */       WritableUtils.writeVInt(this.out, keyLength);
/* 200:248 */       WritableUtils.writeVInt(this.out, valueLength);
/* 201:249 */       this.out.write(key.getData(), key.getPosition(), keyLength);
/* 202:250 */       this.out.write(value.getData(), value.getPosition(), valueLength);
/* 203:    */       
/* 204:    */ 
/* 205:253 */       this.decompressedBytesWritten += keyLength + valueLength + WritableUtils.getVIntSize(keyLength) + WritableUtils.getVIntSize(valueLength);
/* 206:    */       
/* 207:    */ 
/* 208:256 */       this.numRecordsWritten += 1L;
/* 209:    */     }
/* 210:    */     
/* 211:    */     public DataOutputStream getOutputStream()
/* 212:    */     {
/* 213:261 */       return this.out;
/* 214:    */     }
/* 215:    */     
/* 216:    */     public void updateCountersForExternalAppend(long length)
/* 217:    */     {
/* 218:266 */       this.numRecordsWritten += 1L;
/* 219:267 */       this.decompressedBytesWritten += length;
/* 220:    */     }
/* 221:    */     
/* 222:    */     public long getRawLength()
/* 223:    */     {
/* 224:271 */       return this.decompressedBytesWritten;
/* 225:    */     }
/* 226:    */     
/* 227:    */     public long getCompressedLength()
/* 228:    */     {
/* 229:275 */       return this.compressedBytesWritten;
/* 230:    */     }
/* 231:    */   }
/* 232:    */   
/* 233:    */   @InterfaceAudience.Private
/* 234:    */   @InterfaceStability.Unstable
/* 235:    */   public static class Reader<K, V>
/* 236:    */   {
/* 237:    */     private static final int DEFAULT_BUFFER_SIZE = 131072;
/* 238:    */     private static final int MAX_VINT_SIZE = 9;
/* 239:289 */     private long numRecordsRead = 0L;
/* 240:    */     private final Counters.Counter readRecordsCounter;
/* 241:    */     final InputStream in;
/* 242:    */     Decompressor decompressor;
/* 243:294 */     public long bytesRead = 0L;
/* 244:    */     protected final long fileLength;
/* 245:296 */     protected boolean eof = false;
/* 246:    */     final IFileInputStream checksumIn;
/* 247:299 */     protected byte[] buffer = null;
/* 248:300 */     protected int bufferSize = 131072;
/* 249:    */     protected DataInputStream dataIn;
/* 250:303 */     protected int recNo = 1;
/* 251:    */     protected int currentKeyLength;
/* 252:    */     protected int currentValueLength;
/* 253:306 */     byte[] keyBytes = new byte[0];
/* 254:    */     
/* 255:    */     public Reader(Configuration conf, FileSystem fs, Path file, CompressionCodec codec, Counters.Counter readsCounter)
/* 256:    */       throws IOException
/* 257:    */     {
/* 258:323 */       this(conf, fs.open(file), fs.getFileStatus(file).getLen(), codec, readsCounter);
/* 259:    */     }
/* 260:    */     
/* 261:    */     public Reader(Configuration conf, FSDataInputStream in, long length, CompressionCodec codec, Counters.Counter readsCounter)
/* 262:    */       throws IOException
/* 263:    */     {
/* 264:342 */       this.readRecordsCounter = readsCounter;
/* 265:343 */       this.checksumIn = new IFileInputStream(in, length, conf);
/* 266:344 */       if (codec != null)
/* 267:    */       {
/* 268:345 */         this.decompressor = CodecPool.getDecompressor(codec);
/* 269:346 */         if (this.decompressor != null)
/* 270:    */         {
/* 271:347 */           this.in = codec.createInputStream(this.checksumIn, this.decompressor);
/* 272:    */         }
/* 273:    */         else
/* 274:    */         {
/* 275:349 */           IFile.LOG.warn("Could not obtain decompressor from CodecPool");
/* 276:350 */           this.in = this.checksumIn;
/* 277:    */         }
/* 278:    */       }
/* 279:    */       else
/* 280:    */       {
/* 281:353 */         this.in = this.checksumIn;
/* 282:    */       }
/* 283:355 */       this.dataIn = new DataInputStream(this.in);
/* 284:356 */       this.fileLength = length;
/* 285:358 */       if (conf != null) {
/* 286:359 */         this.bufferSize = conf.getInt("io.file.buffer.size", 131072);
/* 287:    */       }
/* 288:    */     }
/* 289:    */     
/* 290:    */     public long getLength()
/* 291:    */     {
/* 292:364 */       return this.fileLength - this.checksumIn.getSize();
/* 293:    */     }
/* 294:    */     
/* 295:    */     public long getPosition()
/* 296:    */       throws IOException
/* 297:    */     {
/* 298:368 */       return this.checksumIn.getPosition();
/* 299:    */     }
/* 300:    */     
/* 301:    */     private int readData(byte[] buf, int off, int len)
/* 302:    */       throws IOException
/* 303:    */     {
/* 304:381 */       int bytesRead = 0;
/* 305:382 */       while (bytesRead < len)
/* 306:    */       {
/* 307:383 */         int n = IOUtils.wrappedReadForCompressedData(this.in, buf, off + bytesRead, len - bytesRead);
/* 308:385 */         if (n < 0) {
/* 309:386 */           return bytesRead;
/* 310:    */         }
/* 311:388 */         bytesRead += n;
/* 312:    */       }
/* 313:390 */       return len;
/* 314:    */     }
/* 315:    */     
/* 316:    */     protected boolean positionToNextRecord(DataInput dIn)
/* 317:    */       throws IOException
/* 318:    */     {
/* 319:395 */       if (this.eof) {
/* 320:396 */         throw new EOFException("Completed reading " + this.bytesRead);
/* 321:    */       }
/* 322:400 */       this.currentKeyLength = WritableUtils.readVInt(dIn);
/* 323:401 */       this.currentValueLength = WritableUtils.readVInt(dIn);
/* 324:402 */       this.bytesRead += WritableUtils.getVIntSize(this.currentKeyLength) + WritableUtils.getVIntSize(this.currentValueLength);
/* 325:406 */       if ((this.currentKeyLength == -1) && (this.currentValueLength == -1))
/* 326:    */       {
/* 327:407 */         this.eof = true;
/* 328:408 */         return false;
/* 329:    */       }
/* 330:412 */       if (this.currentKeyLength < 0) {
/* 331:413 */         throw new IOException("Rec# " + this.recNo + ": Negative key-length: " + this.currentKeyLength);
/* 332:    */       }
/* 333:416 */       if (this.currentValueLength < 0) {
/* 334:417 */         throw new IOException("Rec# " + this.recNo + ": Negative value-length: " + this.currentValueLength);
/* 335:    */       }
/* 336:421 */       return true;
/* 337:    */     }
/* 338:    */     
/* 339:    */     public boolean nextRawKey(DataInputBuffer key)
/* 340:    */       throws IOException
/* 341:    */     {
/* 342:425 */       if (!positionToNextRecord(this.dataIn)) {
/* 343:426 */         return false;
/* 344:    */       }
/* 345:428 */       if (this.keyBytes.length < this.currentKeyLength) {
/* 346:429 */         this.keyBytes = new byte[this.currentKeyLength << 1];
/* 347:    */       }
/* 348:431 */       int i = readData(this.keyBytes, 0, this.currentKeyLength);
/* 349:432 */       if (i != this.currentKeyLength) {
/* 350:433 */         throw new IOException("Asked for " + this.currentKeyLength + " Got: " + i);
/* 351:    */       }
/* 352:435 */       key.reset(this.keyBytes, this.currentKeyLength);
/* 353:436 */       this.bytesRead += this.currentKeyLength;
/* 354:437 */       return true;
/* 355:    */     }
/* 356:    */     
/* 357:    */     public void nextRawValue(DataInputBuffer value)
/* 358:    */       throws IOException
/* 359:    */     {
/* 360:441 */       byte[] valBytes = value.getData().length < this.currentValueLength ? new byte[this.currentValueLength << 1] : value.getData();
/* 361:    */       
/* 362:    */ 
/* 363:444 */       int i = readData(valBytes, 0, this.currentValueLength);
/* 364:445 */       if (i != this.currentValueLength) {
/* 365:446 */         throw new IOException("Asked for " + this.currentValueLength + " Got: " + i);
/* 366:    */       }
/* 367:448 */       value.reset(valBytes, this.currentValueLength);
/* 368:    */       
/* 369:    */ 
/* 370:451 */       this.bytesRead += this.currentValueLength;
/* 371:    */       
/* 372:453 */       this.recNo += 1;
/* 373:454 */       this.numRecordsRead += 1L;
/* 374:    */     }
/* 375:    */     
/* 376:    */     public void close()
/* 377:    */       throws IOException
/* 378:    */     {
/* 379:459 */       this.in.close();
/* 380:    */       
/* 381:    */ 
/* 382:462 */       this.dataIn = null;
/* 383:463 */       this.buffer = null;
/* 384:464 */       if (this.readRecordsCounter != null) {
/* 385:465 */         this.readRecordsCounter.increment(this.numRecordsRead);
/* 386:    */       }
/* 387:469 */       if (this.decompressor != null)
/* 388:    */       {
/* 389:470 */         this.decompressor.reset();
/* 390:471 */         CodecPool.returnDecompressor(this.decompressor);
/* 391:472 */         this.decompressor = null;
/* 392:    */       }
/* 393:    */     }
/* 394:    */     
/* 395:    */     public void reset(int offset) {}
/* 396:    */     
/* 397:    */     public void disableChecksumValidation()
/* 398:    */     {
/* 399:481 */       this.checksumIn.disableChecksumValidation();
/* 400:    */     }
/* 401:    */   }
/* 402:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.IFile
 * JD-Core Version:    0.7.0.1
 */