/*   1:    */ package org.apache.hadoop.mapreduce.task.reduce;
/*   2:    */ 
/*   3:    */ import java.io.Closeable;
/*   4:    */ import java.io.IOException;
/*   5:    */ import java.io.InputStream;
/*   6:    */ import org.apache.commons.logging.Log;
/*   7:    */ import org.apache.commons.logging.LogFactory;
/*   8:    */ import org.apache.hadoop.classification.InterfaceAudience.Private;
/*   9:    */ import org.apache.hadoop.classification.InterfaceStability.Unstable;
/*  10:    */ import org.apache.hadoop.conf.Configuration;
/*  11:    */ import org.apache.hadoop.io.BoundedByteArrayOutputStream;
/*  12:    */ import org.apache.hadoop.io.IOUtils;
/*  13:    */ import org.apache.hadoop.io.compress.CodecPool;
/*  14:    */ import org.apache.hadoop.io.compress.CompressionCodec;
/*  15:    */ import org.apache.hadoop.io.compress.Decompressor;
/*  16:    */ import org.apache.hadoop.mapred.IFileInputStream;
/*  17:    */ import org.apache.hadoop.mapred.Reporter;
/*  18:    */ import org.apache.hadoop.mapreduce.TaskAttemptID;
/*  19:    */ 
/*  20:    */ @InterfaceAudience.Private
/*  21:    */ @InterfaceStability.Unstable
/*  22:    */ class InMemoryMapOutput<K, V>
/*  23:    */   extends MapOutput<K, V>
/*  24:    */ {
/*  25: 46 */   private static final Log LOG = LogFactory.getLog(InMemoryMapOutput.class);
/*  26:    */   private Configuration conf;
/*  27:    */   private final MergeManagerImpl<K, V> merger;
/*  28:    */   private final byte[] memory;
/*  29:    */   private BoundedByteArrayOutputStream byteStream;
/*  30:    */   private final CompressionCodec codec;
/*  31:    */   private final Decompressor decompressor;
/*  32:    */   
/*  33:    */   public InMemoryMapOutput(Configuration conf, TaskAttemptID mapId, MergeManagerImpl<K, V> merger, int size, CompressionCodec codec, boolean primaryMapOutput)
/*  34:    */   {
/*  35: 59 */     super(mapId, size, primaryMapOutput);
/*  36: 60 */     this.conf = conf;
/*  37: 61 */     this.merger = merger;
/*  38: 62 */     this.codec = codec;
/*  39: 63 */     this.byteStream = new BoundedByteArrayOutputStream(size);
/*  40: 64 */     this.memory = this.byteStream.getBuffer();
/*  41: 65 */     if (codec != null) {
/*  42: 66 */       this.decompressor = CodecPool.getDecompressor(codec);
/*  43:    */     } else {
/*  44: 68 */       this.decompressor = null;
/*  45:    */     }
/*  46:    */   }
/*  47:    */   
/*  48:    */   public byte[] getMemory()
/*  49:    */   {
/*  50: 73 */     return this.memory;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public BoundedByteArrayOutputStream getArrayStream()
/*  54:    */   {
/*  55: 77 */     return this.byteStream;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public void shuffle(MapHost host, InputStream input, long compressedLength, long decompressedLength, ShuffleClientMetrics metrics, Reporter reporter)
/*  59:    */     throws IOException
/*  60:    */   {
/*  61: 85 */     IFileInputStream checksumIn = new IFileInputStream(input, compressedLength, this.conf);
/*  62:    */     
/*  63:    */ 
/*  64: 88 */     input = checksumIn;
/*  65: 91 */     if (this.codec != null)
/*  66:    */     {
/*  67: 92 */       this.decompressor.reset();
/*  68: 93 */       input = this.codec.createInputStream(input, this.decompressor);
/*  69:    */     }
/*  70:    */     try
/*  71:    */     {
/*  72: 97 */       IOUtils.readFully(input, this.memory, 0, this.memory.length);
/*  73: 98 */       metrics.inputBytes(this.memory.length);
/*  74: 99 */       reporter.progress();
/*  75:100 */       LOG.info("Read " + this.memory.length + " bytes from map-output for " + getMapId());
/*  76:110 */       if (input.read() >= 0) {
/*  77:111 */         throw new IOException("Unexpected extra bytes from input stream for " + getMapId());
/*  78:    */       }
/*  79:    */     }
/*  80:    */     catch (IOException ioe)
/*  81:    */     {
/*  82:117 */       IOUtils.cleanup(LOG, new Closeable[] { input });
/*  83:    */       
/*  84:    */ 
/*  85:120 */       throw ioe;
/*  86:    */     }
/*  87:    */     finally
/*  88:    */     {
/*  89:122 */       CodecPool.returnDecompressor(this.decompressor);
/*  90:    */     }
/*  91:    */   }
/*  92:    */   
/*  93:    */   public void commit()
/*  94:    */     throws IOException
/*  95:    */   {
/*  96:128 */     this.merger.closeInMemoryFile(this);
/*  97:    */   }
/*  98:    */   
/*  99:    */   public void abort()
/* 100:    */   {
/* 101:133 */     this.merger.unreserve(this.memory.length);
/* 102:    */   }
/* 103:    */   
/* 104:    */   public String getDescription()
/* 105:    */   {
/* 106:138 */     return "MEMORY";
/* 107:    */   }
/* 108:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.task.reduce.InMemoryMapOutput
 * JD-Core Version:    0.7.0.1
 */