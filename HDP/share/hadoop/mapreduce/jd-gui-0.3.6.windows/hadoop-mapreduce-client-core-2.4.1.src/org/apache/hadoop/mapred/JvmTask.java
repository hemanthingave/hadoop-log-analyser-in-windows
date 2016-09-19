/*  1:   */ package org.apache.hadoop.mapred;
/*  2:   */ 
/*  3:   */ import java.io.DataInput;
/*  4:   */ import java.io.DataOutput;
/*  5:   */ import java.io.IOException;
/*  6:   */ import org.apache.hadoop.classification.InterfaceAudience.Private;
/*  7:   */ import org.apache.hadoop.classification.InterfaceStability.Unstable;
/*  8:   */ import org.apache.hadoop.io.Writable;
/*  9:   */ 
/* 10:   */ @InterfaceAudience.Private
/* 11:   */ @InterfaceStability.Unstable
/* 12:   */ public class JvmTask
/* 13:   */   implements Writable
/* 14:   */ {
/* 15:   */   Task t;
/* 16:   */   boolean shouldDie;
/* 17:   */   
/* 18:   */   public JvmTask(Task t, boolean shouldDie)
/* 19:   */   {
/* 20:38 */     this.t = t;
/* 21:39 */     this.shouldDie = shouldDie;
/* 22:   */   }
/* 23:   */   
/* 24:   */   public JvmTask() {}
/* 25:   */   
/* 26:   */   public Task getTask()
/* 27:   */   {
/* 28:43 */     return this.t;
/* 29:   */   }
/* 30:   */   
/* 31:   */   public boolean shouldDie()
/* 32:   */   {
/* 33:46 */     return this.shouldDie;
/* 34:   */   }
/* 35:   */   
/* 36:   */   public void write(DataOutput out)
/* 37:   */     throws IOException
/* 38:   */   {
/* 39:49 */     out.writeBoolean(this.shouldDie);
/* 40:50 */     if (this.t != null)
/* 41:   */     {
/* 42:51 */       out.writeBoolean(true);
/* 43:52 */       out.writeBoolean(this.t.isMapTask());
/* 44:53 */       this.t.write(out);
/* 45:   */     }
/* 46:   */     else
/* 47:   */     {
/* 48:55 */       out.writeBoolean(false);
/* 49:   */     }
/* 50:   */   }
/* 51:   */   
/* 52:   */   public void readFields(DataInput in)
/* 53:   */     throws IOException
/* 54:   */   {
/* 55:59 */     this.shouldDie = in.readBoolean();
/* 56:60 */     boolean taskComing = in.readBoolean();
/* 57:61 */     if (taskComing)
/* 58:   */     {
/* 59:62 */       boolean isMap = in.readBoolean();
/* 60:63 */       if (isMap) {
/* 61:64 */         this.t = new MapTask();
/* 62:   */       } else {
/* 63:66 */         this.t = new ReduceTask();
/* 64:   */       }
/* 65:68 */       this.t.readFields(in);
/* 66:   */     }
/* 67:   */   }
/* 68:   */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.JvmTask
 * JD-Core Version:    0.7.0.1
 */