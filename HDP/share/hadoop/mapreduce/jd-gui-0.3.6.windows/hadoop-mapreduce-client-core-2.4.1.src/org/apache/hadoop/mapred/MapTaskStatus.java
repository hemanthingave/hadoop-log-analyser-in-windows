/*   1:    */ package org.apache.hadoop.mapred;
/*   2:    */ 
/*   3:    */ import java.io.DataInput;
/*   4:    */ import java.io.DataOutput;
/*   5:    */ import java.io.IOException;
/*   6:    */ 
/*   7:    */ class MapTaskStatus
/*   8:    */   extends TaskStatus
/*   9:    */ {
/*  10: 28 */   private long mapFinishTime = 0L;
/*  11:    */   
/*  12:    */   public MapTaskStatus() {}
/*  13:    */   
/*  14:    */   public MapTaskStatus(TaskAttemptID taskid, float progress, int numSlots, TaskStatus.State runState, String diagnosticInfo, String stateString, String taskTracker, TaskStatus.Phase phase, Counters counters)
/*  15:    */   {
/*  16: 35 */     super(taskid, progress, numSlots, runState, diagnosticInfo, stateString, taskTracker, phase, counters);
/*  17:    */   }
/*  18:    */   
/*  19:    */   public boolean getIsMap()
/*  20:    */   {
/*  21: 41 */     return true;
/*  22:    */   }
/*  23:    */   
/*  24:    */   void setFinishTime(long finishTime)
/*  25:    */   {
/*  26: 50 */     super.setFinishTime(finishTime);
/*  27: 52 */     if (getMapFinishTime() == 0L) {
/*  28: 53 */       setMapFinishTime(finishTime);
/*  29:    */     }
/*  30:    */   }
/*  31:    */   
/*  32:    */   public long getShuffleFinishTime()
/*  33:    */   {
/*  34: 59 */     throw new UnsupportedOperationException("getShuffleFinishTime() not supported for MapTask");
/*  35:    */   }
/*  36:    */   
/*  37:    */   void setShuffleFinishTime(long shuffleFinishTime)
/*  38:    */   {
/*  39: 64 */     throw new UnsupportedOperationException("setShuffleFinishTime() not supported for MapTask");
/*  40:    */   }
/*  41:    */   
/*  42:    */   public long getMapFinishTime()
/*  43:    */   {
/*  44: 69 */     return this.mapFinishTime;
/*  45:    */   }
/*  46:    */   
/*  47:    */   void setMapFinishTime(long mapFinishTime)
/*  48:    */   {
/*  49: 74 */     this.mapFinishTime = mapFinishTime;
/*  50:    */   }
/*  51:    */   
/*  52:    */   synchronized void statusUpdate(TaskStatus status)
/*  53:    */   {
/*  54: 79 */     super.statusUpdate(status);
/*  55: 81 */     if (status.getMapFinishTime() != 0L) {
/*  56: 82 */       this.mapFinishTime = status.getMapFinishTime();
/*  57:    */     }
/*  58:    */   }
/*  59:    */   
/*  60:    */   public void readFields(DataInput in)
/*  61:    */     throws IOException
/*  62:    */   {
/*  63: 88 */     super.readFields(in);
/*  64: 89 */     this.mapFinishTime = in.readLong();
/*  65:    */   }
/*  66:    */   
/*  67:    */   public void write(DataOutput out)
/*  68:    */     throws IOException
/*  69:    */   {
/*  70: 94 */     super.write(out);
/*  71: 95 */     out.writeLong(this.mapFinishTime);
/*  72:    */   }
/*  73:    */   
/*  74:    */   public void addFetchFailedMap(TaskAttemptID mapTaskId)
/*  75:    */   {
/*  76:100 */     throw new UnsupportedOperationException("addFetchFailedMap() not supported for MapTask");
/*  77:    */   }
/*  78:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.MapTaskStatus
 * JD-Core Version:    0.7.0.1
 */