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
/* 12:   */ public class MapTaskCompletionEventsUpdate
/* 13:   */   implements Writable
/* 14:   */ {
/* 15:   */   TaskCompletionEvent[] events;
/* 16:   */   boolean reset;
/* 17:   */   
/* 18:   */   public MapTaskCompletionEventsUpdate() {}
/* 19:   */   
/* 20:   */   public MapTaskCompletionEventsUpdate(TaskCompletionEvent[] events, boolean reset)
/* 21:   */   {
/* 22:43 */     this.events = events;
/* 23:44 */     this.reset = reset;
/* 24:   */   }
/* 25:   */   
/* 26:   */   public boolean shouldReset()
/* 27:   */   {
/* 28:48 */     return this.reset;
/* 29:   */   }
/* 30:   */   
/* 31:   */   public TaskCompletionEvent[] getMapTaskCompletionEvents()
/* 32:   */   {
/* 33:52 */     return this.events;
/* 34:   */   }
/* 35:   */   
/* 36:   */   public void write(DataOutput out)
/* 37:   */     throws IOException
/* 38:   */   {
/* 39:56 */     out.writeBoolean(this.reset);
/* 40:57 */     out.writeInt(this.events.length);
/* 41:58 */     for (TaskCompletionEvent event : this.events) {
/* 42:59 */       event.write(out);
/* 43:   */     }
/* 44:   */   }
/* 45:   */   
/* 46:   */   public void readFields(DataInput in)
/* 47:   */     throws IOException
/* 48:   */   {
/* 49:64 */     this.reset = in.readBoolean();
/* 50:65 */     this.events = new TaskCompletionEvent[in.readInt()];
/* 51:66 */     for (int i = 0; i < this.events.length; i++)
/* 52:   */     {
/* 53:67 */       this.events[i] = new TaskCompletionEvent();
/* 54:68 */       this.events[i].readFields(in);
/* 55:   */     }
/* 56:   */   }
/* 57:   */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.MapTaskCompletionEventsUpdate
 * JD-Core Version:    0.7.0.1
 */