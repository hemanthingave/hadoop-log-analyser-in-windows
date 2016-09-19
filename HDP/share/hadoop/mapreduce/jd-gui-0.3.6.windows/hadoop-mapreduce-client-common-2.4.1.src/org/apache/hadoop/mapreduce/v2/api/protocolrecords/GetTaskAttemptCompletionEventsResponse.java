package org.apache.hadoop.mapreduce.v2.api.protocolrecords;

import java.util.List;
import org.apache.hadoop.mapreduce.v2.api.records.TaskAttemptCompletionEvent;

public abstract interface GetTaskAttemptCompletionEventsResponse
{
  public abstract List<TaskAttemptCompletionEvent> getCompletionEventList();
  
  public abstract TaskAttemptCompletionEvent getCompletionEvent(int paramInt);
  
  public abstract int getCompletionEventCount();
  
  public abstract void addAllCompletionEvents(List<TaskAttemptCompletionEvent> paramList);
  
  public abstract void addCompletionEvent(TaskAttemptCompletionEvent paramTaskAttemptCompletionEvent);
  
  public abstract void removeCompletionEvent(int paramInt);
  
  public abstract void clearCompletionEvents();
}


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-common-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.v2.api.protocolrecords.GetTaskAttemptCompletionEventsResponse
 * JD-Core Version:    0.7.0.1
 */