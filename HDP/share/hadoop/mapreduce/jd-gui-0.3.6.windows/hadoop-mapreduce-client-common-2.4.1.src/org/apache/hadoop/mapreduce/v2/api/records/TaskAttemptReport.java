package org.apache.hadoop.mapreduce.v2.api.records;

import org.apache.hadoop.yarn.api.records.ContainerId;

public abstract interface TaskAttemptReport
{
  public abstract TaskAttemptId getTaskAttemptId();
  
  public abstract TaskAttemptState getTaskAttemptState();
  
  public abstract float getProgress();
  
  public abstract long getStartTime();
  
  public abstract long getFinishTime();
  
  public abstract long getShuffleFinishTime();
  
  public abstract long getSortFinishTime();
  
  public abstract Counters getCounters();
  
  public abstract String getDiagnosticInfo();
  
  public abstract String getStateString();
  
  public abstract Phase getPhase();
  
  public abstract String getNodeManagerHost();
  
  public abstract int getNodeManagerPort();
  
  public abstract int getNodeManagerHttpPort();
  
  public abstract ContainerId getContainerId();
  
  public abstract void setTaskAttemptId(TaskAttemptId paramTaskAttemptId);
  
  public abstract void setTaskAttemptState(TaskAttemptState paramTaskAttemptState);
  
  public abstract void setProgress(float paramFloat);
  
  public abstract void setStartTime(long paramLong);
  
  public abstract void setFinishTime(long paramLong);
  
  public abstract void setCounters(Counters paramCounters);
  
  public abstract void setDiagnosticInfo(String paramString);
  
  public abstract void setStateString(String paramString);
  
  public abstract void setPhase(Phase paramPhase);
  
  public abstract void setNodeManagerHost(String paramString);
  
  public abstract void setNodeManagerPort(int paramInt);
  
  public abstract void setNodeManagerHttpPort(int paramInt);
  
  public abstract void setContainerId(ContainerId paramContainerId);
  
  public abstract void setShuffleFinishTime(long paramLong);
  
  public abstract void setSortFinishTime(long paramLong);
}


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-common-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.v2.api.records.TaskAttemptReport
 * JD-Core Version:    0.7.0.1
 */