package org.apache.hadoop.mapreduce.v2.api.records;

public enum TaskAttemptState
{
  NEW,  STARTING,  RUNNING,  COMMIT_PENDING,  SUCCEEDED,  FAILED,  KILLED;
  
  private TaskAttemptState() {}
}


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-common-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.v2.api.records.TaskAttemptState
 * JD-Core Version:    0.7.0.1
 */