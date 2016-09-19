package org.apache.hadoop.mapreduce.v2.api.records;

import org.apache.hadoop.yarn.api.records.ApplicationAttemptId;
import org.apache.hadoop.yarn.api.records.ContainerId;

public abstract interface AMInfo
{
  public abstract ApplicationAttemptId getAppAttemptId();
  
  public abstract long getStartTime();
  
  public abstract ContainerId getContainerId();
  
  public abstract String getNodeManagerHost();
  
  public abstract int getNodeManagerPort();
  
  public abstract int getNodeManagerHttpPort();
  
  public abstract void setAppAttemptId(ApplicationAttemptId paramApplicationAttemptId);
  
  public abstract void setStartTime(long paramLong);
  
  public abstract void setContainerId(ContainerId paramContainerId);
  
  public abstract void setNodeManagerHost(String paramString);
  
  public abstract void setNodeManagerPort(int paramInt);
  
  public abstract void setNodeManagerHttpPort(int paramInt);
}


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-common-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.v2.api.records.AMInfo
 * JD-Core Version:    0.7.0.1
 */