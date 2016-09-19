/*   1:    */ package org.apache.hadoop.mapreduce.task.reduce;
/*   2:    */ 
/*   3:    */ import com.google.common.annotations.VisibleForTesting;
/*   4:    */ import java.io.Closeable;
/*   5:    */ import java.io.IOException;
/*   6:    */ import java.io.InputStream;
/*   7:    */ import java.io.OutputStream;
/*   8:    */ import org.apache.commons.logging.Log;
/*   9:    */ import org.apache.commons.logging.LogFactory;
/*  10:    */ import org.apache.hadoop.classification.InterfaceAudience.Private;
/*  11:    */ import org.apache.hadoop.classification.InterfaceStability.Unstable;
/*  12:    */ import org.apache.hadoop.fs.FileSystem;
/*  13:    */ import org.apache.hadoop.fs.Path;
/*  14:    */ import org.apache.hadoop.io.IOUtils;
/*  15:    */ import org.apache.hadoop.mapred.JobConf;
/*  16:    */ import org.apache.hadoop.mapred.MapOutputFile;
/*  17:    */ import org.apache.hadoop.mapred.Reporter;
/*  18:    */ import org.apache.hadoop.mapreduce.TaskAttemptID;
/*  19:    */ 
/*  20:    */ @InterfaceAudience.Private
/*  21:    */ @InterfaceStability.Unstable
/*  22:    */ class OnDiskMapOutput<K, V>
/*  23:    */   extends MapOutput<K, V>
/*  24:    */ {
/*  25: 47 */   private static final Log LOG = LogFactory.getLog(OnDiskMapOutput.class);
/*  26:    */   private final FileSystem fs;
/*  27:    */   private final Path tmpOutputPath;
/*  28:    */   private final Path outputPath;
/*  29:    */   private final MergeManagerImpl<K, V> merger;
/*  30:    */   private final OutputStream disk;
/*  31:    */   private long compressedSize;
/*  32:    */   
/*  33:    */   public OnDiskMapOutput(TaskAttemptID mapId, TaskAttemptID reduceId, MergeManagerImpl<K, V> merger, long size, JobConf conf, MapOutputFile mapOutputFile, int fetcher, boolean primaryMapOutput)
/*  34:    */     throws IOException
/*  35:    */   {
/*  36: 61 */     this(mapId, reduceId, merger, size, conf, mapOutputFile, fetcher, primaryMapOutput, FileSystem.getLocal(conf), mapOutputFile.getInputFileForWrite(mapId.getTaskID(), size));
/*  37:    */   }
/*  38:    */   
/*  39:    */   @VisibleForTesting
/*  40:    */   OnDiskMapOutput(TaskAttemptID mapId, TaskAttemptID reduceId, MergeManagerImpl<K, V> merger, long size, JobConf conf, MapOutputFile mapOutputFile, int fetcher, boolean primaryMapOutput, FileSystem fs, Path outputPath)
/*  41:    */     throws IOException
/*  42:    */   {
/*  43: 73 */     super(mapId, size, primaryMapOutput);
/*  44: 74 */     this.fs = fs;
/*  45: 75 */     this.merger = merger;
/*  46: 76 */     this.outputPath = outputPath;
/*  47: 77 */     this.tmpOutputPath = getTempPath(outputPath, fetcher);
/*  48: 78 */     this.disk = fs.create(this.tmpOutputPath);
/*  49:    */   }
/*  50:    */   
/*  51:    */   @VisibleForTesting
/*  52:    */   static Path getTempPath(Path outPath, int fetcher)
/*  53:    */   {
/*  54: 83 */     return outPath.suffix(String.valueOf(fetcher));
/*  55:    */   }
/*  56:    */   
/*  57:    */   public void shuffle(MapHost host, InputStream input, long compressedLength, long decompressedLength, ShuffleClientMetrics metrics, Reporter reporter)
/*  58:    */     throws IOException
/*  59:    */   {
/*  60: 92 */     long bytesLeft = compressedLength;
/*  61:    */     try
/*  62:    */     {
/*  63: 94 */       int BYTES_TO_READ = 65536;
/*  64: 95 */       byte[] buf = new byte[65536];
/*  65: 96 */       while (bytesLeft > 0L)
/*  66:    */       {
/*  67: 97 */         int n = input.read(buf, 0, (int)Math.min(bytesLeft, 65536L));
/*  68: 98 */         if (n < 0) {
/*  69: 99 */           throw new IOException("read past end of stream reading " + getMapId());
/*  70:    */         }
/*  71:102 */         this.disk.write(buf, 0, n);
/*  72:103 */         bytesLeft -= n;
/*  73:104 */         metrics.inputBytes(n);
/*  74:105 */         reporter.progress();
/*  75:    */       }
/*  76:108 */       LOG.info("Read " + (compressedLength - bytesLeft) + " bytes from map-output for " + getMapId());
/*  77:    */       
/*  78:    */ 
/*  79:111 */       this.disk.close();
/*  80:    */     }
/*  81:    */     catch (IOException ioe)
/*  82:    */     {
/*  83:114 */       IOUtils.cleanup(LOG, new Closeable[] { input, this.disk });
/*  84:    */       
/*  85:    */ 
/*  86:117 */       throw ioe;
/*  87:    */     }
/*  88:121 */     if (bytesLeft != 0L) {
/*  89:122 */       throw new IOException("Incomplete map output received for " + getMapId() + " from " + host.getHostName() + " (" + bytesLeft + " bytes missing of " + compressedLength + ")");
/*  90:    */     }
/*  91:128 */     this.compressedSize = compressedLength;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public void commit()
/*  95:    */     throws IOException
/*  96:    */   {
/*  97:133 */     this.fs.rename(this.tmpOutputPath, this.outputPath);
/*  98:134 */     MergeManagerImpl.CompressAwarePath compressAwarePath = new MergeManagerImpl.CompressAwarePath(this.outputPath, getSize(), this.compressedSize);
/*  99:    */     
/* 100:136 */     this.merger.closeOnDiskFile(compressAwarePath);
/* 101:    */   }
/* 102:    */   
/* 103:    */   public void abort()
/* 104:    */   {
/* 105:    */     try
/* 106:    */     {
/* 107:142 */       this.fs.delete(this.tmpOutputPath, false);
/* 108:    */     }
/* 109:    */     catch (IOException ie)
/* 110:    */     {
/* 111:144 */       LOG.info("failure to clean up " + this.tmpOutputPath, ie);
/* 112:    */     }
/* 113:    */   }
/* 114:    */   
/* 115:    */   public String getDescription()
/* 116:    */   {
/* 117:150 */     return "DISK";
/* 118:    */   }
/* 119:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.task.reduce.OnDiskMapOutput
 * JD-Core Version:    0.7.0.1
 */