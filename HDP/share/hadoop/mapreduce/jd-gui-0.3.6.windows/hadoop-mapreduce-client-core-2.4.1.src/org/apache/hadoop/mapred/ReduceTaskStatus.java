/*   1:    */ package org.apache.hadoop.mapred;
/*   2:    */ 
/*   3:    */ import java.io.DataInput;
/*   4:    */ import java.io.DataOutput;
/*   5:    */ import java.io.IOException;
/*   6:    */ import java.util.ArrayList;
/*   7:    */ import java.util.List;
/*   8:    */ 
/*   9:    */ class ReduceTaskStatus
/*  10:    */   extends TaskStatus
/*  11:    */ {
/*  12:    */   private long shuffleFinishTime;
/*  13:    */   private long sortFinishTime;
/*  14: 33 */   private List<TaskAttemptID> failedFetchTasks = new ArrayList(1);
/*  15:    */   
/*  16:    */   public ReduceTaskStatus() {}
/*  17:    */   
/*  18:    */   public ReduceTaskStatus(TaskAttemptID taskid, float progress, int numSlots, TaskStatus.State runState, String diagnosticInfo, String stateString, String taskTracker, TaskStatus.Phase phase, Counters counters)
/*  19:    */   {
/*  20: 40 */     super(taskid, progress, numSlots, runState, diagnosticInfo, stateString, taskTracker, phase, counters);
/*  21:    */   }
/*  22:    */   
/*  23:    */   public Object clone()
/*  24:    */   {
/*  25: 46 */     ReduceTaskStatus myClone = (ReduceTaskStatus)super.clone();
/*  26: 47 */     myClone.failedFetchTasks = new ArrayList(this.failedFetchTasks);
/*  27: 48 */     return myClone;
/*  28:    */   }
/*  29:    */   
/*  30:    */   public boolean getIsMap()
/*  31:    */   {
/*  32: 53 */     return false;
/*  33:    */   }
/*  34:    */   
/*  35:    */   void setFinishTime(long finishTime)
/*  36:    */   {
/*  37: 58 */     if (this.shuffleFinishTime == 0L) {
/*  38: 59 */       this.shuffleFinishTime = finishTime;
/*  39:    */     }
/*  40: 61 */     if (this.sortFinishTime == 0L) {
/*  41: 62 */       this.sortFinishTime = finishTime;
/*  42:    */     }
/*  43: 64 */     super.setFinishTime(finishTime);
/*  44:    */   }
/*  45:    */   
/*  46:    */   public long getShuffleFinishTime()
/*  47:    */   {
/*  48: 69 */     return this.shuffleFinishTime;
/*  49:    */   }
/*  50:    */   
/*  51:    */   void setShuffleFinishTime(long shuffleFinishTime)
/*  52:    */   {
/*  53: 74 */     this.shuffleFinishTime = shuffleFinishTime;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public long getSortFinishTime()
/*  57:    */   {
/*  58: 79 */     return this.sortFinishTime;
/*  59:    */   }
/*  60:    */   
/*  61:    */   void setSortFinishTime(long sortFinishTime)
/*  62:    */   {
/*  63: 84 */     this.sortFinishTime = sortFinishTime;
/*  64: 85 */     if (0L == this.shuffleFinishTime) {
/*  65: 86 */       this.shuffleFinishTime = sortFinishTime;
/*  66:    */     }
/*  67:    */   }
/*  68:    */   
/*  69:    */   public long getMapFinishTime()
/*  70:    */   {
/*  71: 92 */     throw new UnsupportedOperationException("getMapFinishTime() not supported for ReduceTask");
/*  72:    */   }
/*  73:    */   
/*  74:    */   void setMapFinishTime(long shuffleFinishTime)
/*  75:    */   {
/*  76: 98 */     throw new UnsupportedOperationException("setMapFinishTime() not supported for ReduceTask");
/*  77:    */   }
/*  78:    */   
/*  79:    */   public List<TaskAttemptID> getFetchFailedMaps()
/*  80:    */   {
/*  81:104 */     return this.failedFetchTasks;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public void addFetchFailedMap(TaskAttemptID mapTaskId)
/*  85:    */   {
/*  86:109 */     this.failedFetchTasks.add(mapTaskId);
/*  87:    */   }
/*  88:    */   
/*  89:    */   synchronized void statusUpdate(TaskStatus status)
/*  90:    */   {
/*  91:114 */     super.statusUpdate(status);
/*  92:116 */     if (status.getShuffleFinishTime() != 0L) {
/*  93:117 */       this.shuffleFinishTime = status.getShuffleFinishTime();
/*  94:    */     }
/*  95:120 */     if (status.getSortFinishTime() != 0L) {
/*  96:121 */       this.sortFinishTime = status.getSortFinishTime();
/*  97:    */     }
/*  98:124 */     List<TaskAttemptID> newFetchFailedMaps = status.getFetchFailedMaps();
/*  99:125 */     if (this.failedFetchTasks == null) {
/* 100:126 */       this.failedFetchTasks = newFetchFailedMaps;
/* 101:127 */     } else if (newFetchFailedMaps != null) {
/* 102:128 */       this.failedFetchTasks.addAll(newFetchFailedMaps);
/* 103:    */     }
/* 104:    */   }
/* 105:    */   
/* 106:    */   synchronized void clearStatus()
/* 107:    */   {
/* 108:134 */     super.clearStatus();
/* 109:135 */     this.failedFetchTasks.clear();
/* 110:    */   }
/* 111:    */   
/* 112:    */   public void readFields(DataInput in)
/* 113:    */     throws IOException
/* 114:    */   {
/* 115:140 */     super.readFields(in);
/* 116:141 */     this.shuffleFinishTime = in.readLong();
/* 117:142 */     this.sortFinishTime = in.readLong();
/* 118:143 */     int noFailedFetchTasks = in.readInt();
/* 119:144 */     this.failedFetchTasks = new ArrayList(noFailedFetchTasks);
/* 120:145 */     for (int i = 0; i < noFailedFetchTasks; i++)
/* 121:    */     {
/* 122:146 */       TaskAttemptID id = new TaskAttemptID();
/* 123:147 */       id.readFields(in);
/* 124:148 */       this.failedFetchTasks.add(id);
/* 125:    */     }
/* 126:    */   }
/* 127:    */   
/* 128:    */   public void write(DataOutput out)
/* 129:    */     throws IOException
/* 130:    */   {
/* 131:154 */     super.write(out);
/* 132:155 */     out.writeLong(this.shuffleFinishTime);
/* 133:156 */     out.writeLong(this.sortFinishTime);
/* 134:157 */     out.writeInt(this.failedFetchTasks.size());
/* 135:158 */     for (TaskAttemptID taskId : this.failedFetchTasks) {
/* 136:159 */       taskId.write(out);
/* 137:    */     }
/* 138:    */   }
/* 139:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.ReduceTaskStatus
 * JD-Core Version:    0.7.0.1
 */