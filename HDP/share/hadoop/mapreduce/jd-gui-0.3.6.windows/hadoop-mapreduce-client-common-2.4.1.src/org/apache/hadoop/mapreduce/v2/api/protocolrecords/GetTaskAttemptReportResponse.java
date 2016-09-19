package org.apache.hadoop.mapreduce.v2.api.protocolrecords;

import org.apache.hadoop.mapreduce.v2.api.records.TaskAttemptReport;

public abstract interface GetTaskAttemptReportResponse
{
  public abstract TaskAttemptReport getTaskAttemptReport();
  
  public abstract void setTaskAttemptReport(TaskAttemptReport paramTaskAttemptReport);
}


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-common-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.v2.api.protocolrecords.GetTaskAttemptReportResponse
 * JD-Core Version:    0.7.0.1
 */