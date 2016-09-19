package org.apache.hadoop.mapreduce.v2.api.protocolrecords;

import org.apache.hadoop.mapreduce.v2.api.records.TaskAttemptId;

public abstract interface GetDiagnosticsRequest
{
  public abstract TaskAttemptId getTaskAttemptId();
  
  public abstract void setTaskAttemptId(TaskAttemptId paramTaskAttemptId);
}


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-common-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.v2.api.protocolrecords.GetDiagnosticsRequest
 * JD-Core Version:    0.7.0.1
 */