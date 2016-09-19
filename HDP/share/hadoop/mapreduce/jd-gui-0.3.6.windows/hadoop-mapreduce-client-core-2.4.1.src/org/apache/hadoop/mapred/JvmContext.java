/*  1:   */ package org.apache.hadoop.mapred;
/*  2:   */ 
/*  3:   */ import java.io.DataInput;
/*  4:   */ import java.io.DataOutput;
/*  5:   */ import java.io.IOException;
/*  6:   */ import org.apache.commons.logging.Log;
/*  7:   */ import org.apache.commons.logging.LogFactory;
/*  8:   */ import org.apache.hadoop.io.Text;
/*  9:   */ import org.apache.hadoop.io.Writable;
/* 10:   */ 
/* 11:   */ class JvmContext
/* 12:   */   implements Writable
/* 13:   */ {
/* 14:32 */   public static final Log LOG = LogFactory.getLog(JvmContext.class);
/* 15:   */   JVMId jvmId;
/* 16:   */   String pid;
/* 17:   */   
/* 18:   */   JvmContext()
/* 19:   */   {
/* 20:39 */     this.jvmId = new JVMId();
/* 21:40 */     this.pid = "";
/* 22:   */   }
/* 23:   */   
/* 24:   */   JvmContext(JVMId id, String pid)
/* 25:   */   {
/* 26:44 */     this.jvmId = id;
/* 27:45 */     this.pid = pid;
/* 28:   */   }
/* 29:   */   
/* 30:   */   public void readFields(DataInput in)
/* 31:   */     throws IOException
/* 32:   */   {
/* 33:49 */     this.jvmId.readFields(in);
/* 34:50 */     this.pid = Text.readString(in);
/* 35:   */   }
/* 36:   */   
/* 37:   */   public void write(DataOutput out)
/* 38:   */     throws IOException
/* 39:   */   {
/* 40:54 */     this.jvmId.write(out);
/* 41:55 */     Text.writeString(out, this.pid);
/* 42:   */   }
/* 43:   */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.JvmContext
 * JD-Core Version:    0.7.0.1
 */