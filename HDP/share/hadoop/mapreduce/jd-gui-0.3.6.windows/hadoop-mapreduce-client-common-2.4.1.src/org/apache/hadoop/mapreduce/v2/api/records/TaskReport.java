package org.apache.hadoop.mapreduce.v2.api.records;

import java.util.List;

public abstract interface TaskReport
{
  public abstract TaskId getTaskId();
  
  public abstract TaskState getTaskState();
  
  public abstract float getProgress();
  
  public abstract String getStatus();
  
  public abstract long getStartTime();
  
  public abstract long getFinishTime();
  
  public abstract Counters getCounters();
  
  public abstract List<TaskAttemptId> getRunningAttemptsList();
  
  public abstract TaskAttemptId getRunningAttempt(int paramInt);
  
  public abstract int getRunningAttemptsCount();
  
  public abstract TaskAttemptId getSuccessfulAttempt();
  
  public abstract List<String> getDiagnosticsList();
  
  public abstract String getDiagnostics(int paramInt);
  
  public abstract int getDiagnosticsCount();
  
  public abstract void setTaskId(TaskId paramTaskId);
  
  public abstract void setTaskState(TaskState paramTaskState);
  
  public abstract void setProgress(float paramFloat);
  
  public abstract void setStatus(String paramString);
  
  public abstract void setStartTime(long paramLong);
  
  public abstract void setFinishTime(long paramLong);
  
  public abstract void setCounters(Counters paramCounters);
  
  public abstract void addAllRunningAttempts(List<TaskAttemptId> paramList);
  
  public abstract void addRunningAttempt(TaskAttemptId paramTaskAttemptId);
  
  public abstract void removeRunningAttempt(int paramInt);
  
  public abstract void clearRunningAttempts();
  
  public abstract void setSuccessfulAttempt(TaskAttemptId paramTaskAttemptId);
  
  public abstract void addAllDiagnostics(List<String> paramList);
  
  public abstract void addDiagnostics(String paramString);
  
  public abstract void removeDiagnostics(int paramInt);
  
  public abstract void clearDiagnostics();
}


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-common-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.v2.api.records.TaskReport
 * JD-Core Version:    0.7.0.1
 */