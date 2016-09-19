package org.apache.hadoop.mapreduce.v2.api.records;

public enum TaskAttemptCompletionEventStatus
{
  FAILED,  KILLED,  SUCCEEDED,  OBSOLETE,  TIPFAILED;
  
  private TaskAttemptCompletionEventStatus() {}
}


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-common-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.v2.api.records.TaskAttemptCompletionEventStatus
 * JD-Core Version:    0.7.0.1
 */