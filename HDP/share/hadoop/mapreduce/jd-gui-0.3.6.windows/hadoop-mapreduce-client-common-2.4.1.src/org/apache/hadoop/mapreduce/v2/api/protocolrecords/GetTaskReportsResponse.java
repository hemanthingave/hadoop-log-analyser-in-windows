package org.apache.hadoop.mapreduce.v2.api.protocolrecords;

import java.util.List;
import org.apache.hadoop.mapreduce.v2.api.records.TaskReport;

public abstract interface GetTaskReportsResponse
{
  public abstract List<TaskReport> getTaskReportList();
  
  public abstract TaskReport getTaskReport(int paramInt);
  
  public abstract int getTaskReportCount();
  
  public abstract void addAllTaskReports(List<TaskReport> paramList);
  
  public abstract void addTaskReport(TaskReport paramTaskReport);
  
  public abstract void removeTaskReport(int paramInt);
  
  public abstract void clearTaskReports();
}


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-common-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.v2.api.protocolrecords.GetTaskReportsResponse
 * JD-Core Version:    0.7.0.1
 */