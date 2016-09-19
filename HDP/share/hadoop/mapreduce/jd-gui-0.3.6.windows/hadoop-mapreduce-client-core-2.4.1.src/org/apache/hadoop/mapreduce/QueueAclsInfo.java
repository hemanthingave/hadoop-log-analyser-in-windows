/*  1:   */ package org.apache.hadoop.mapreduce;
/*  2:   */ 
/*  3:   */ import java.io.DataInput;
/*  4:   */ import java.io.DataOutput;
/*  5:   */ import java.io.IOException;
/*  6:   */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*  7:   */ import org.apache.hadoop.classification.InterfaceStability.Evolving;
/*  8:   */ import org.apache.hadoop.io.Text;
/*  9:   */ import org.apache.hadoop.io.Writable;
/* 10:   */ import org.apache.hadoop.io.WritableUtils;
/* 11:   */ import org.apache.hadoop.util.StringInterner;
/* 12:   */ 
/* 13:   */ @InterfaceAudience.Public
/* 14:   */ @InterfaceStability.Evolving
/* 15:   */ public class QueueAclsInfo
/* 16:   */   implements Writable
/* 17:   */ {
/* 18:   */   private String queueName;
/* 19:   */   private String[] operations;
/* 20:   */   
/* 21:   */   public QueueAclsInfo() {}
/* 22:   */   
/* 23:   */   public QueueAclsInfo(String queueName, String[] operations)
/* 24:   */   {
/* 25:58 */     this.queueName = queueName;
/* 26:59 */     this.operations = operations;
/* 27:   */   }
/* 28:   */   
/* 29:   */   public String getQueueName()
/* 30:   */   {
/* 31:68 */     return this.queueName;
/* 32:   */   }
/* 33:   */   
/* 34:   */   protected void setQueueName(String queueName)
/* 35:   */   {
/* 36:72 */     this.queueName = queueName;
/* 37:   */   }
/* 38:   */   
/* 39:   */   public String[] getOperations()
/* 40:   */   {
/* 41:81 */     return this.operations;
/* 42:   */   }
/* 43:   */   
/* 44:   */   public void readFields(DataInput in)
/* 45:   */     throws IOException
/* 46:   */   {
/* 47:86 */     this.queueName = StringInterner.weakIntern(Text.readString(in));
/* 48:87 */     this.operations = WritableUtils.readStringArray(in);
/* 49:   */   }
/* 50:   */   
/* 51:   */   public void write(DataOutput out)
/* 52:   */     throws IOException
/* 53:   */   {
/* 54:92 */     Text.writeString(out, this.queueName);
/* 55:93 */     WritableUtils.writeStringArray(out, this.operations);
/* 56:   */   }
/* 57:   */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.QueueAclsInfo
 * JD-Core Version:    0.7.0.1
 */