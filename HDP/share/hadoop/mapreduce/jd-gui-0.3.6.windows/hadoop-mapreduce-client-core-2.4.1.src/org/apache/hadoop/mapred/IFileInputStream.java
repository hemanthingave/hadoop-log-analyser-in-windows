/*   1:    */ package org.apache.hadoop.mapred;
/*   2:    */ 
/*   3:    */ import java.io.EOFException;
/*   4:    */ import java.io.FileDescriptor;
/*   5:    */ import java.io.FileInputStream;
/*   6:    */ import java.io.IOException;
/*   7:    */ import java.io.InputStream;
/*   8:    */ import org.apache.commons.logging.Log;
/*   9:    */ import org.apache.commons.logging.LogFactory;
/*  10:    */ import org.apache.hadoop.classification.InterfaceAudience.Private;
/*  11:    */ import org.apache.hadoop.classification.InterfaceStability.Unstable;
/*  12:    */ import org.apache.hadoop.conf.Configuration;
/*  13:    */ import org.apache.hadoop.fs.ChecksumException;
/*  14:    */ import org.apache.hadoop.fs.HasFileDescriptor;
/*  15:    */ import org.apache.hadoop.io.IOUtils;
/*  16:    */ import org.apache.hadoop.io.ReadaheadPool;
/*  17:    */ import org.apache.hadoop.io.ReadaheadPool.ReadaheadRequest;
/*  18:    */ import org.apache.hadoop.util.DataChecksum;
/*  19:    */ import org.apache.hadoop.util.DataChecksum.Type;
/*  20:    */ 
/*  21:    */ @InterfaceAudience.Private
/*  22:    */ @InterfaceStability.Unstable
/*  23:    */ public class IFileInputStream
/*  24:    */   extends InputStream
/*  25:    */ {
/*  26:    */   private final InputStream in;
/*  27:    */   private final FileDescriptor inFd;
/*  28:    */   private final long length;
/*  29:    */   private final long dataLength;
/*  30:    */   private DataChecksum sum;
/*  31: 52 */   private long currentOffset = 0L;
/*  32: 53 */   private final byte[] b = new byte[1];
/*  33: 54 */   private byte[] csum = null;
/*  34:    */   private int checksumSize;
/*  35: 57 */   private ReadaheadPool.ReadaheadRequest curReadahead = null;
/*  36: 58 */   private ReadaheadPool raPool = ReadaheadPool.getInstance();
/*  37:    */   private boolean readahead;
/*  38:    */   private int readaheadLength;
/*  39: 62 */   public static final Log LOG = LogFactory.getLog(IFileInputStream.class);
/*  40: 64 */   private boolean disableChecksumValidation = false;
/*  41:    */   
/*  42:    */   public IFileInputStream(InputStream in, long len, Configuration conf)
/*  43:    */   {
/*  44: 72 */     this.in = in;
/*  45: 73 */     this.inFd = getFileDescriptorIfAvail(in);
/*  46: 74 */     this.sum = DataChecksum.newDataChecksum(DataChecksum.Type.CRC32, 2147483647);
/*  47:    */     
/*  48: 76 */     this.checksumSize = this.sum.getChecksumSize();
/*  49: 77 */     this.length = len;
/*  50: 78 */     this.dataLength = (this.length - this.checksumSize);
/*  51:    */     
/*  52: 80 */     conf = conf != null ? conf : new Configuration();
/*  53: 81 */     this.readahead = conf.getBoolean("mapreduce.ifile.readahead", true);
/*  54:    */     
/*  55: 83 */     this.readaheadLength = conf.getInt("mapreduce.ifile.readahead.bytes", 4194304);
/*  56:    */     
/*  57:    */ 
/*  58: 86 */     doReadahead();
/*  59:    */   }
/*  60:    */   
/*  61:    */   private static FileDescriptor getFileDescriptorIfAvail(InputStream in)
/*  62:    */   {
/*  63: 90 */     FileDescriptor fd = null;
/*  64:    */     try
/*  65:    */     {
/*  66: 92 */       if ((in instanceof HasFileDescriptor)) {
/*  67: 93 */         fd = ((HasFileDescriptor)in).getFileDescriptor();
/*  68: 94 */       } else if ((in instanceof FileInputStream)) {
/*  69: 95 */         fd = ((FileInputStream)in).getFD();
/*  70:    */       }
/*  71:    */     }
/*  72:    */     catch (IOException e)
/*  73:    */     {
/*  74: 98 */       LOG.info("Unable to determine FileDescriptor", e);
/*  75:    */     }
/*  76:100 */     return fd;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public void close()
/*  80:    */     throws IOException
/*  81:    */   {
/*  82:110 */     if (this.curReadahead != null) {
/*  83:111 */       this.curReadahead.cancel();
/*  84:    */     }
/*  85:113 */     if (this.currentOffset < this.dataLength)
/*  86:    */     {
/*  87:114 */       byte[] t = new byte[Math.min((int)(0x7FFFFFFF & this.dataLength - this.currentOffset), 32768)];
/*  88:116 */       while (this.currentOffset < this.dataLength)
/*  89:    */       {
/*  90:117 */         int n = read(t, 0, t.length);
/*  91:118 */         if (0 == n) {
/*  92:119 */           throw new EOFException("Could not validate checksum");
/*  93:    */         }
/*  94:    */       }
/*  95:    */     }
/*  96:123 */     this.in.close();
/*  97:    */   }
/*  98:    */   
/*  99:    */   public long skip(long n)
/* 100:    */     throws IOException
/* 101:    */   {
/* 102:128 */     throw new IOException("Skip not supported for IFileInputStream");
/* 103:    */   }
/* 104:    */   
/* 105:    */   public long getPosition()
/* 106:    */   {
/* 107:132 */     return this.currentOffset >= this.dataLength ? this.dataLength : this.currentOffset;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public long getSize()
/* 111:    */   {
/* 112:136 */     return this.checksumSize;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public int read(byte[] b, int off, int len)
/* 116:    */     throws IOException
/* 117:    */   {
/* 118:146 */     if (this.currentOffset >= this.dataLength) {
/* 119:147 */       return -1;
/* 120:    */     }
/* 121:150 */     doReadahead();
/* 122:    */     
/* 123:152 */     return doRead(b, off, len);
/* 124:    */   }
/* 125:    */   
/* 126:    */   private void doReadahead()
/* 127:    */   {
/* 128:156 */     if ((this.raPool != null) && (this.inFd != null) && (this.readahead)) {
/* 129:157 */       this.curReadahead = this.raPool.readaheadStream("ifile", this.inFd, this.currentOffset, this.readaheadLength, this.dataLength, this.curReadahead);
/* 130:    */     }
/* 131:    */   }
/* 132:    */   
/* 133:    */   public int readWithChecksum(byte[] b, int off, int len)
/* 134:    */     throws IOException
/* 135:    */   {
/* 136:172 */     if (this.currentOffset == this.length) {
/* 137:173 */       return -1;
/* 138:    */     }
/* 139:175 */     if (this.currentOffset >= this.dataLength)
/* 140:    */     {
/* 141:179 */       int lenToCopy = (int)(this.checksumSize - (this.currentOffset - this.dataLength));
/* 142:180 */       if (len < lenToCopy) {
/* 143:181 */         lenToCopy = len;
/* 144:    */       }
/* 145:183 */       System.arraycopy(this.csum, (int)(this.currentOffset - this.dataLength), b, off, lenToCopy);
/* 146:    */       
/* 147:185 */       this.currentOffset += lenToCopy;
/* 148:186 */       return lenToCopy;
/* 149:    */     }
/* 150:189 */     int bytesRead = doRead(b, off, len);
/* 151:191 */     if ((this.currentOffset == this.dataLength) && 
/* 152:192 */       (len >= bytesRead + this.checksumSize))
/* 153:    */     {
/* 154:193 */       System.arraycopy(this.csum, 0, b, off + bytesRead, this.checksumSize);
/* 155:194 */       bytesRead += this.checksumSize;
/* 156:195 */       this.currentOffset += this.checksumSize;
/* 157:    */     }
/* 158:198 */     return bytesRead;
/* 159:    */   }
/* 160:    */   
/* 161:    */   private int doRead(byte[] b, int off, int len)
/* 162:    */     throws IOException
/* 163:    */   {
/* 164:205 */     if (this.currentOffset + len > this.dataLength) {
/* 165:206 */       len = (int)this.dataLength - (int)this.currentOffset;
/* 166:    */     }
/* 167:209 */     int bytesRead = this.in.read(b, off, len);
/* 168:211 */     if (bytesRead < 0) {
/* 169:212 */       throw new ChecksumException("Checksum Error", 0L);
/* 170:    */     }
/* 171:215 */     this.sum.update(b, off, bytesRead);
/* 172:    */     
/* 173:217 */     this.currentOffset += bytesRead;
/* 174:219 */     if (this.disableChecksumValidation) {
/* 175:220 */       return bytesRead;
/* 176:    */     }
/* 177:223 */     if (this.currentOffset == this.dataLength)
/* 178:    */     {
/* 179:225 */       this.csum = new byte[this.checksumSize];
/* 180:226 */       IOUtils.readFully(this.in, this.csum, 0, this.checksumSize);
/* 181:227 */       if (!this.sum.compare(this.csum, 0)) {
/* 182:228 */         throw new ChecksumException("Checksum Error", 0L);
/* 183:    */       }
/* 184:    */     }
/* 185:231 */     return bytesRead;
/* 186:    */   }
/* 187:    */   
/* 188:    */   public int read()
/* 189:    */     throws IOException
/* 190:    */   {
/* 191:237 */     this.b[0] = 0;
/* 192:238 */     int l = read(this.b, 0, 1);
/* 193:239 */     if (l < 0) {
/* 194:239 */       return l;
/* 195:    */     }
/* 196:243 */     int result = 0xFF & this.b[0];
/* 197:244 */     return result;
/* 198:    */   }
/* 199:    */   
/* 200:    */   public byte[] getChecksum()
/* 201:    */   {
/* 202:248 */     return this.csum;
/* 203:    */   }
/* 204:    */   
/* 205:    */   void disableChecksumValidation()
/* 206:    */   {
/* 207:252 */     this.disableChecksumValidation = true;
/* 208:    */   }
/* 209:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.IFileInputStream
 * JD-Core Version:    0.7.0.1
 */