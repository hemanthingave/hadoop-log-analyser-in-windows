package org.apache.hadoop.mapreduce.v2.api.protocolrecords;

import org.apache.hadoop.mapreduce.v2.api.records.JobId;
import org.apache.hadoop.mapreduce.v2.api.records.TaskType;

public abstract interface GetTaskReportsRequest
{
  public abstract JobId getJobId();
  
  public abstract TaskType getTaskType();
  
  public abstract void setJobId(JobId paramJobId);
  
  public abstract void setTaskType(TaskType paramTaskType);
}


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-common-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.v2.api.protocolrecords.GetTaskReportsRequest
 * JD-Core Version:    0.7.0.1
 */