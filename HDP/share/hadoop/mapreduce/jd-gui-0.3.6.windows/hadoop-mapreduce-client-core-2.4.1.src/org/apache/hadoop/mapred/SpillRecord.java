/*   1:    */ package org.apache.hadoop.mapred;
/*   2:    */ 
/*   3:    */ import java.io.File;
/*   4:    */ import java.io.IOException;
/*   5:    */ import java.net.URI;
/*   6:    */ import java.nio.ByteBuffer;
/*   7:    */ import java.nio.LongBuffer;
/*   8:    */ import java.util.zip.CheckedInputStream;
/*   9:    */ import java.util.zip.CheckedOutputStream;
/*  10:    */ import java.util.zip.Checksum;
/*  11:    */ import org.apache.hadoop.classification.InterfaceAudience.LimitedPrivate;
/*  12:    */ import org.apache.hadoop.classification.InterfaceStability.Unstable;
/*  13:    */ import org.apache.hadoop.fs.ChecksumException;
/*  14:    */ import org.apache.hadoop.fs.FSDataInputStream;
/*  15:    */ import org.apache.hadoop.fs.FSDataOutputStream;
/*  16:    */ import org.apache.hadoop.fs.FileStatus;
/*  17:    */ import org.apache.hadoop.fs.FileSystem;
/*  18:    */ import org.apache.hadoop.fs.LocalFileSystem;
/*  19:    */ import org.apache.hadoop.fs.Path;
/*  20:    */ import org.apache.hadoop.io.IOUtils;
/*  21:    */ import org.apache.hadoop.io.SecureIOUtils;
/*  22:    */ import org.apache.hadoop.util.PureJavaCrc32;
/*  23:    */ 
/*  24:    */ @InterfaceAudience.LimitedPrivate({"MapReduce"})
/*  25:    */ @InterfaceStability.Unstable
/*  26:    */ public class SpillRecord
/*  27:    */ {
/*  28:    */   private final ByteBuffer buf;
/*  29:    */   private final LongBuffer entries;
/*  30:    */   
/*  31:    */   public SpillRecord(int numPartitions)
/*  32:    */   {
/*  33: 51 */     this.buf = ByteBuffer.allocate(numPartitions * 24);
/*  34:    */     
/*  35: 53 */     this.entries = this.buf.asLongBuffer();
/*  36:    */   }
/*  37:    */   
/*  38:    */   public SpillRecord(Path indexFileName, JobConf job)
/*  39:    */     throws IOException
/*  40:    */   {
/*  41: 57 */     this(indexFileName, job, null);
/*  42:    */   }
/*  43:    */   
/*  44:    */   public SpillRecord(Path indexFileName, JobConf job, String expectedIndexOwner)
/*  45:    */     throws IOException
/*  46:    */   {
/*  47: 62 */     this(indexFileName, job, new PureJavaCrc32(), expectedIndexOwner);
/*  48:    */   }
/*  49:    */   
/*  50:    */   public SpillRecord(Path indexFileName, JobConf job, Checksum crc, String expectedIndexOwner)
/*  51:    */     throws IOException
/*  52:    */   {
/*  53: 69 */     FileSystem rfs = FileSystem.getLocal(job).getRaw();
/*  54: 70 */     FSDataInputStream in = SecureIOUtils.openFSDataInputStream(new File(indexFileName.toUri().getRawPath()), expectedIndexOwner, null);
/*  55:    */     try
/*  56:    */     {
/*  57: 74 */       long length = rfs.getFileStatus(indexFileName).getLen();
/*  58: 75 */       int partitions = (int)length / 24;
/*  59: 76 */       int size = partitions * 24;
/*  60: 77 */       this.buf = ByteBuffer.allocate(size);
/*  61: 78 */       if (crc != null)
/*  62:    */       {
/*  63: 79 */         crc.reset();
/*  64: 80 */         CheckedInputStream chk = new CheckedInputStream(in, crc);
/*  65: 81 */         IOUtils.readFully(chk, this.buf.array(), 0, size);
/*  66: 83 */         if (chk.getChecksum().getValue() != in.readLong()) {
/*  67: 84 */           throw new ChecksumException("Checksum error reading spill index: " + indexFileName, -1L);
/*  68:    */         }
/*  69:    */       }
/*  70:    */       else
/*  71:    */       {
/*  72: 88 */         IOUtils.readFully(in, this.buf.array(), 0, size);
/*  73:    */       }
/*  74: 90 */       this.entries = this.buf.asLongBuffer();
/*  75:    */     }
/*  76:    */     finally
/*  77:    */     {
/*  78: 92 */       in.close();
/*  79:    */     }
/*  80:    */   }
/*  81:    */   
/*  82:    */   public int size()
/*  83:    */   {
/*  84:100 */     return this.entries.capacity() / 3;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public IndexRecord getIndex(int partition)
/*  88:    */   {
/*  89:107 */     int pos = partition * 24 / 8;
/*  90:108 */     return new IndexRecord(this.entries.get(pos), this.entries.get(pos + 1), this.entries.get(pos + 2));
/*  91:    */   }
/*  92:    */   
/*  93:    */   public void putIndex(IndexRecord rec, int partition)
/*  94:    */   {
/*  95:116 */     int pos = partition * 24 / 8;
/*  96:117 */     this.entries.put(pos, rec.startOffset);
/*  97:118 */     this.entries.put(pos + 1, rec.rawLength);
/*  98:119 */     this.entries.put(pos + 2, rec.partLength);
/*  99:    */   }
/* 100:    */   
/* 101:    */   public void writeToFile(Path loc, JobConf job)
/* 102:    */     throws IOException
/* 103:    */   {
/* 104:127 */     writeToFile(loc, job, new PureJavaCrc32());
/* 105:    */   }
/* 106:    */   
/* 107:    */   public void writeToFile(Path loc, JobConf job, Checksum crc)
/* 108:    */     throws IOException
/* 109:    */   {
/* 110:132 */     FileSystem rfs = FileSystem.getLocal(job).getRaw();
/* 111:133 */     CheckedOutputStream chk = null;
/* 112:134 */     FSDataOutputStream out = rfs.create(loc);
/* 113:    */     try
/* 114:    */     {
/* 115:136 */       if (crc != null)
/* 116:    */       {
/* 117:137 */         crc.reset();
/* 118:138 */         chk = new CheckedOutputStream(out, crc);
/* 119:139 */         chk.write(this.buf.array());
/* 120:140 */         out.writeLong(chk.getChecksum().getValue());
/* 121:    */       }
/* 122:    */       else
/* 123:    */       {
/* 124:142 */         out.write(this.buf.array());
/* 125:    */       }
/* 126:    */     }
/* 127:    */     finally
/* 128:    */     {
/* 129:145 */       if (chk != null) {
/* 130:146 */         chk.close();
/* 131:    */       } else {
/* 132:148 */         out.close();
/* 133:    */       }
/* 134:    */     }
/* 135:    */   }
/* 136:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.SpillRecord
 * JD-Core Version:    0.7.0.1
 */