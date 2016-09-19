/*  1:   */ package org.apache.hadoop.mapreduce.task.reduce;
/*  2:   */ 
/*  3:   */ import java.io.DataInput;
/*  4:   */ import java.io.DataOutput;
/*  5:   */ import java.io.IOException;
/*  6:   */ import org.apache.hadoop.classification.InterfaceAudience.Private;
/*  7:   */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*  8:   */ import org.apache.hadoop.io.Text;
/*  9:   */ import org.apache.hadoop.io.Writable;
/* 10:   */ import org.apache.hadoop.io.WritableUtils;
/* 11:   */ 
/* 12:   */ @InterfaceAudience.Private
/* 13:   */ @InterfaceStability.Stable
/* 14:   */ public class ShuffleHeader
/* 15:   */   implements Writable
/* 16:   */ {
/* 17:   */   public static final String HTTP_HEADER_NAME = "name";
/* 18:   */   public static final String DEFAULT_HTTP_HEADER_NAME = "mapreduce";
/* 19:   */   public static final String HTTP_HEADER_VERSION = "version";
/* 20:   */   public static final String DEFAULT_HTTP_HEADER_VERSION = "1.0.0";
/* 21:   */   private static final int MAX_ID_LENGTH = 1000;
/* 22:   */   String mapId;
/* 23:   */   long uncompressedLength;
/* 24:   */   long compressedLength;
/* 25:   */   int forReduce;
/* 26:   */   
/* 27:   */   public ShuffleHeader() {}
/* 28:   */   
/* 29:   */   public ShuffleHeader(String mapId, long compressedLength, long uncompressedLength, int forReduce)
/* 30:   */   {
/* 31:59 */     this.mapId = mapId;
/* 32:60 */     this.compressedLength = compressedLength;
/* 33:61 */     this.uncompressedLength = uncompressedLength;
/* 34:62 */     this.forReduce = forReduce;
/* 35:   */   }
/* 36:   */   
/* 37:   */   public void readFields(DataInput in)
/* 38:   */     throws IOException
/* 39:   */   {
/* 40:66 */     this.mapId = WritableUtils.readStringSafely(in, 1000);
/* 41:67 */     this.compressedLength = WritableUtils.readVLong(in);
/* 42:68 */     this.uncompressedLength = WritableUtils.readVLong(in);
/* 43:69 */     this.forReduce = WritableUtils.readVInt(in);
/* 44:   */   }
/* 45:   */   
/* 46:   */   public void write(DataOutput out)
/* 47:   */     throws IOException
/* 48:   */   {
/* 49:73 */     Text.writeString(out, this.mapId);
/* 50:74 */     WritableUtils.writeVLong(out, this.compressedLength);
/* 51:75 */     WritableUtils.writeVLong(out, this.uncompressedLength);
/* 52:76 */     WritableUtils.writeVInt(out, this.forReduce);
/* 53:   */   }
/* 54:   */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.task.reduce.ShuffleHeader
 * JD-Core Version:    0.7.0.1
 */