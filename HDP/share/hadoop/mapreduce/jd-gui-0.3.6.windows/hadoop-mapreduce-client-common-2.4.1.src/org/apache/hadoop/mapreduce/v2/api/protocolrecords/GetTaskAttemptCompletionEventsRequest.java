package org.apache.hadoop.mapreduce.v2.api.protocolrecords;

import org.apache.hadoop.mapreduce.v2.api.records.JobId;

public abstract interface GetTaskAttemptCompletionEventsRequest
{
  public abstract JobId getJobId();
  
  public abstract int getFromEventId();
  
  public abstract int getMaxEvents();
  
  public abstract void setJobId(JobId paramJobId);
  
  public abstract void setFromEventId(int paramInt);
  
  public abstract void setMaxEvents(int paramInt);
}


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-common-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.v2.api.protocolrecords.GetTaskAttemptCompletionEventsRequest
 * JD-Core Version:    0.7.0.1
 */