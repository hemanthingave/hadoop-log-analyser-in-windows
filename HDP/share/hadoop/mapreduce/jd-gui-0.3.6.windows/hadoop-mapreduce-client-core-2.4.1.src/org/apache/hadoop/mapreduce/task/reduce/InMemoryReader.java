/*   1:    */ package org.apache.hadoop.mapreduce.task.reduce;
/*   2:    */ 
/*   3:    */ import java.io.File;
/*   4:    */ import java.io.FileOutputStream;
/*   5:    */ import java.io.IOException;
/*   6:    */ import java.io.PrintStream;
/*   7:    */ import org.apache.hadoop.classification.InterfaceAudience.Private;
/*   8:    */ import org.apache.hadoop.classification.InterfaceStability.Unstable;
/*   9:    */ import org.apache.hadoop.conf.Configuration;
/*  10:    */ import org.apache.hadoop.io.DataInputBuffer;
/*  11:    */ import org.apache.hadoop.mapred.IFile.Reader;
/*  12:    */ import org.apache.hadoop.mapreduce.TaskAttemptID;
/*  13:    */ 
/*  14:    */ @InterfaceAudience.Private
/*  15:    */ @InterfaceStability.Unstable
/*  16:    */ public class InMemoryReader<K, V>
/*  17:    */   extends IFile.Reader<K, V>
/*  18:    */ {
/*  19:    */   private final TaskAttemptID taskAttemptId;
/*  20:    */   private final MergeManagerImpl<K, V> merger;
/*  21: 40 */   private final DataInputBuffer memDataIn = new DataInputBuffer();
/*  22:    */   private final int start;
/*  23:    */   private final int length;
/*  24:    */   
/*  25:    */   public InMemoryReader(MergeManagerImpl<K, V> merger, TaskAttemptID taskAttemptId, byte[] data, int start, int length, Configuration conf)
/*  26:    */     throws IOException
/*  27:    */   {
/*  28: 47 */     super(conf, null, length - start, null, null);
/*  29: 48 */     this.merger = merger;
/*  30: 49 */     this.taskAttemptId = taskAttemptId;
/*  31:    */     
/*  32: 51 */     this.buffer = data;
/*  33: 52 */     this.bufferSize = ((int)this.fileLength);
/*  34: 53 */     this.memDataIn.reset(this.buffer, start, length - start);
/*  35: 54 */     this.start = start;
/*  36: 55 */     this.length = length;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public void reset(int offset)
/*  40:    */   {
/*  41: 60 */     this.memDataIn.reset(this.buffer, this.start + offset, this.length - this.start - offset);
/*  42: 61 */     this.bytesRead = offset;
/*  43: 62 */     this.eof = false;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public long getPosition()
/*  47:    */     throws IOException
/*  48:    */   {
/*  49: 70 */     return this.bytesRead;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public long getLength()
/*  53:    */   {
/*  54: 75 */     return this.fileLength;
/*  55:    */   }
/*  56:    */   
/*  57:    */   private void dumpOnError()
/*  58:    */   {
/*  59: 79 */     File dumpFile = new File("../output/" + this.taskAttemptId + ".dump");
/*  60: 80 */     System.err.println("Dumping corrupt map-output of " + this.taskAttemptId + " to " + dumpFile.getAbsolutePath());
/*  61:    */     try
/*  62:    */     {
/*  63: 83 */       FileOutputStream fos = new FileOutputStream(dumpFile);
/*  64: 84 */       fos.write(this.buffer, 0, this.bufferSize);
/*  65: 85 */       fos.close();
/*  66:    */     }
/*  67:    */     catch (IOException ioe)
/*  68:    */     {
/*  69: 87 */       System.err.println("Failed to dump map-output of " + this.taskAttemptId);
/*  70:    */     }
/*  71:    */   }
/*  72:    */   
/*  73:    */   public boolean nextRawKey(DataInputBuffer key)
/*  74:    */     throws IOException
/*  75:    */   {
/*  76:    */     try
/*  77:    */     {
/*  78: 93 */       if (!positionToNextRecord(this.memDataIn)) {
/*  79: 94 */         return false;
/*  80:    */       }
/*  81: 97 */       int pos = this.memDataIn.getPosition();
/*  82: 98 */       byte[] data = this.memDataIn.getData();
/*  83: 99 */       key.reset(data, pos, this.currentKeyLength);
/*  84:    */       
/*  85:101 */       long skipped = this.memDataIn.skip(this.currentKeyLength);
/*  86:102 */       if (skipped != this.currentKeyLength) {
/*  87:103 */         throw new IOException("Rec# " + this.recNo + ": Failed to skip past key of length: " + this.currentKeyLength);
/*  88:    */       }
/*  89:109 */       this.bytesRead += this.currentKeyLength;
/*  90:110 */       return true;
/*  91:    */     }
/*  92:    */     catch (IOException ioe)
/*  93:    */     {
/*  94:112 */       dumpOnError();
/*  95:113 */       throw ioe;
/*  96:    */     }
/*  97:    */   }
/*  98:    */   
/*  99:    */   public void nextRawValue(DataInputBuffer value)
/* 100:    */     throws IOException
/* 101:    */   {
/* 102:    */     try
/* 103:    */     {
/* 104:119 */       int pos = this.memDataIn.getPosition();
/* 105:120 */       byte[] data = this.memDataIn.getData();
/* 106:121 */       value.reset(data, pos, this.currentValueLength);
/* 107:    */       
/* 108:    */ 
/* 109:124 */       long skipped = this.memDataIn.skip(this.currentValueLength);
/* 110:125 */       if (skipped != this.currentValueLength) {
/* 111:126 */         throw new IOException("Rec# " + this.recNo + ": Failed to skip past value of length: " + this.currentValueLength);
/* 112:    */       }
/* 113:131 */       this.bytesRead += this.currentValueLength;
/* 114:    */       
/* 115:133 */       this.recNo += 1;
/* 116:    */     }
/* 117:    */     catch (IOException ioe)
/* 118:    */     {
/* 119:135 */       dumpOnError();
/* 120:136 */       throw ioe;
/* 121:    */     }
/* 122:    */   }
/* 123:    */   
/* 124:    */   public void close()
/* 125:    */   {
/* 126:142 */     this.dataIn = null;
/* 127:143 */     this.buffer = null;
/* 128:145 */     if (this.merger != null) {
/* 129:146 */       this.merger.unreserve(this.bufferSize);
/* 130:    */     }
/* 131:    */   }
/* 132:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.task.reduce.InMemoryReader
 * JD-Core Version:    0.7.0.1
 */