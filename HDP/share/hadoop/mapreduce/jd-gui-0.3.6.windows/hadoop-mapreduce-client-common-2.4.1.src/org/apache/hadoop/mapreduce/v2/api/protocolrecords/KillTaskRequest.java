package org.apache.hadoop.mapreduce.v2.api.protocolrecords;

import org.apache.hadoop.mapreduce.v2.api.records.TaskId;

public abstract interface KillTaskRequest
{
  public abstract TaskId getTaskId();
  
  public abstract void setTaskId(TaskId paramTaskId);
}


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-common-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.v2.api.protocolrecords.KillTaskRequest
 * JD-Core Version:    0.7.0.1
 */