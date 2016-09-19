/*   1:    */ package org.apache.hadoop.mapred;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*   5:    */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*   6:    */ import org.apache.hadoop.conf.Configuration;
/*   7:    */ import org.apache.hadoop.fs.FileSystem;
/*   8:    */ import org.apache.hadoop.fs.Path;
/*   9:    */ import org.apache.hadoop.io.SequenceFile.Reader;
/*  10:    */ import org.apache.hadoop.util.ReflectionUtils;
/*  11:    */ 
/*  12:    */ @InterfaceAudience.Public
/*  13:    */ @InterfaceStability.Stable
/*  14:    */ public class SequenceFileRecordReader<K, V>
/*  15:    */   implements RecordReader<K, V>
/*  16:    */ {
/*  17:    */   private SequenceFile.Reader in;
/*  18:    */   private long start;
/*  19:    */   private long end;
/*  20: 42 */   private boolean more = true;
/*  21:    */   protected Configuration conf;
/*  22:    */   
/*  23:    */   public SequenceFileRecordReader(Configuration conf, FileSplit split)
/*  24:    */     throws IOException
/*  25:    */   {
/*  26: 47 */     Path path = split.getPath();
/*  27: 48 */     FileSystem fs = path.getFileSystem(conf);
/*  28: 49 */     this.in = new SequenceFile.Reader(fs, path, conf);
/*  29: 50 */     this.end = (split.getStart() + split.getLength());
/*  30: 51 */     this.conf = conf;
/*  31: 53 */     if (split.getStart() > this.in.getPosition()) {
/*  32: 54 */       this.in.sync(split.getStart());
/*  33:    */     }
/*  34: 56 */     this.start = this.in.getPosition();
/*  35: 57 */     this.more = (this.start < this.end);
/*  36:    */   }
/*  37:    */   
/*  38:    */   public Class getKeyClass()
/*  39:    */   {
/*  40: 63 */     return this.in.getKeyClass();
/*  41:    */   }
/*  42:    */   
/*  43:    */   public Class getValueClass()
/*  44:    */   {
/*  45: 67 */     return this.in.getValueClass();
/*  46:    */   }
/*  47:    */   
/*  48:    */   public K createKey()
/*  49:    */   {
/*  50: 71 */     return ReflectionUtils.newInstance(getKeyClass(), this.conf);
/*  51:    */   }
/*  52:    */   
/*  53:    */   public V createValue()
/*  54:    */   {
/*  55: 76 */     return ReflectionUtils.newInstance(getValueClass(), this.conf);
/*  56:    */   }
/*  57:    */   
/*  58:    */   public synchronized boolean next(K key, V value)
/*  59:    */     throws IOException
/*  60:    */   {
/*  61: 80 */     if (!this.more) {
/*  62: 80 */       return false;
/*  63:    */     }
/*  64: 81 */     long pos = this.in.getPosition();
/*  65: 82 */     boolean remaining = this.in.next(key) != null;
/*  66: 83 */     if (remaining) {
/*  67: 84 */       getCurrentValue(value);
/*  68:    */     }
/*  69: 86 */     if ((pos >= this.end) && (this.in.syncSeen())) {
/*  70: 87 */       this.more = false;
/*  71:    */     } else {
/*  72: 89 */       this.more = remaining;
/*  73:    */     }
/*  74: 91 */     return this.more;
/*  75:    */   }
/*  76:    */   
/*  77:    */   protected synchronized boolean next(K key)
/*  78:    */     throws IOException
/*  79:    */   {
/*  80: 96 */     if (!this.more) {
/*  81: 96 */       return false;
/*  82:    */     }
/*  83: 97 */     long pos = this.in.getPosition();
/*  84: 98 */     boolean remaining = this.in.next(key) != null;
/*  85: 99 */     if ((pos >= this.end) && (this.in.syncSeen())) {
/*  86:100 */       this.more = false;
/*  87:    */     } else {
/*  88:102 */       this.more = remaining;
/*  89:    */     }
/*  90:104 */     return this.more;
/*  91:    */   }
/*  92:    */   
/*  93:    */   protected synchronized void getCurrentValue(V value)
/*  94:    */     throws IOException
/*  95:    */   {
/*  96:109 */     this.in.getCurrentValue(value);
/*  97:    */   }
/*  98:    */   
/*  99:    */   public float getProgress()
/* 100:    */     throws IOException
/* 101:    */   {
/* 102:117 */     if (this.end == this.start) {
/* 103:118 */       return 0.0F;
/* 104:    */     }
/* 105:120 */     return Math.min(1.0F, (float)(this.in.getPosition() - this.start) / (float)(this.end - this.start));
/* 106:    */   }
/* 107:    */   
/* 108:    */   public synchronized long getPos()
/* 109:    */     throws IOException
/* 110:    */   {
/* 111:125 */     return this.in.getPosition();
/* 112:    */   }
/* 113:    */   
/* 114:    */   protected synchronized void seek(long pos)
/* 115:    */     throws IOException
/* 116:    */   {
/* 117:129 */     this.in.seek(pos);
/* 118:    */   }
/* 119:    */   
/* 120:    */   public synchronized void close()
/* 121:    */     throws IOException
/* 122:    */   {
/* 123:131 */     this.in.close();
/* 124:    */   }
/* 125:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.SequenceFileRecordReader
 * JD-Core Version:    0.7.0.1
 */