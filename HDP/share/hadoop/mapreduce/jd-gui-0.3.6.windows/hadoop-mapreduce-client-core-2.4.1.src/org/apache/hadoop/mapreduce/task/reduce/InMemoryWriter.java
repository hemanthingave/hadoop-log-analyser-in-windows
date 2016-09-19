/*  1:   */ package org.apache.hadoop.mapreduce.task.reduce;
/*  2:   */ 
/*  3:   */ import java.io.DataOutputStream;
/*  4:   */ import java.io.IOException;
/*  5:   */ import org.apache.hadoop.classification.InterfaceAudience.Private;
/*  6:   */ import org.apache.hadoop.classification.InterfaceStability.Unstable;
/*  7:   */ import org.apache.hadoop.io.BoundedByteArrayOutputStream;
/*  8:   */ import org.apache.hadoop.io.DataInputBuffer;
/*  9:   */ import org.apache.hadoop.io.WritableUtils;
/* 10:   */ import org.apache.hadoop.mapred.IFile.Writer;
/* 11:   */ import org.apache.hadoop.mapred.IFileOutputStream;
/* 12:   */ 
/* 13:   */ @InterfaceAudience.Private
/* 14:   */ @InterfaceStability.Unstable
/* 15:   */ public class InMemoryWriter<K, V>
/* 16:   */   extends IFile.Writer<K, V>
/* 17:   */ {
/* 18:   */   private DataOutputStream out;
/* 19:   */   
/* 20:   */   public InMemoryWriter(BoundedByteArrayOutputStream arrayStream)
/* 21:   */   {
/* 22:38 */     super(null);
/* 23:39 */     this.out = new DataOutputStream(new IFileOutputStream(arrayStream));
/* 24:   */   }
/* 25:   */   
/* 26:   */   public void append(K key, V value)
/* 27:   */     throws IOException
/* 28:   */   {
/* 29:44 */     throw new UnsupportedOperationException("InMemoryWriter.append(K key, V value");
/* 30:   */   }
/* 31:   */   
/* 32:   */   public void append(DataInputBuffer key, DataInputBuffer value)
/* 33:   */     throws IOException
/* 34:   */   {
/* 35:50 */     int keyLength = key.getLength() - key.getPosition();
/* 36:51 */     if (keyLength < 0) {
/* 37:52 */       throw new IOException("Negative key-length not allowed: " + keyLength + " for " + key);
/* 38:   */     }
/* 39:56 */     int valueLength = value.getLength() - value.getPosition();
/* 40:57 */     if (valueLength < 0) {
/* 41:58 */       throw new IOException("Negative value-length not allowed: " + valueLength + " for " + value);
/* 42:   */     }
/* 43:62 */     WritableUtils.writeVInt(this.out, keyLength);
/* 44:63 */     WritableUtils.writeVInt(this.out, valueLength);
/* 45:64 */     this.out.write(key.getData(), key.getPosition(), keyLength);
/* 46:65 */     this.out.write(value.getData(), value.getPosition(), valueLength);
/* 47:   */   }
/* 48:   */   
/* 49:   */   public void close()
/* 50:   */     throws IOException
/* 51:   */   {
/* 52:70 */     WritableUtils.writeVInt(this.out, -1);
/* 53:71 */     WritableUtils.writeVInt(this.out, -1);
/* 54:   */     
/* 55:   */ 
/* 56:74 */     this.out.close();
/* 57:75 */     this.out = null;
/* 58:   */   }
/* 59:   */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.task.reduce.InMemoryWriter
 * JD-Core Version:    0.7.0.1
 */