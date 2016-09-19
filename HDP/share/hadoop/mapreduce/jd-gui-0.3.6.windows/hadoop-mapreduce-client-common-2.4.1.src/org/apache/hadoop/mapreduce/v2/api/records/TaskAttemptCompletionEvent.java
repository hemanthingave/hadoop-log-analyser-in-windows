package org.apache.hadoop.mapreduce.v2.api.records;

public abstract interface TaskAttemptCompletionEvent
{
  public abstract TaskAttemptId getAttemptId();
  
  public abstract TaskAttemptCompletionEventStatus getStatus();
  
  public abstract String getMapOutputServerAddress();
  
  public abstract int getAttemptRunTime();
  
  public abstract int getEventId();
  
  public abstract void setAttemptId(TaskAttemptId paramTaskAttemptId);
  
  public abstract void setStatus(TaskAttemptCompletionEventStatus paramTaskAttemptCompletionEventStatus);
  
  public abstract void setMapOutputServerAddress(String paramString);
  
  public abstract void setAttemptRunTime(int paramInt);
  
  public abstract void setEventId(int paramInt);
}


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-common-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.v2.api.records.TaskAttemptCompletionEvent
 * JD-Core Version:    0.7.0.1
 */