package org.apache.hadoop.mapreduce.v2.api.records;

import java.util.List;

public abstract interface JobReport
{
  public abstract JobId getJobId();
  
  public abstract JobState getJobState();
  
  public abstract float getMapProgress();
  
  public abstract float getReduceProgress();
  
  public abstract float getCleanupProgress();
  
  public abstract float getSetupProgress();
  
  public abstract long getSubmitTime();
  
  public abstract long getStartTime();
  
  public abstract long getFinishTime();
  
  public abstract String getUser();
  
  public abstract String getJobName();
  
  public abstract String getTrackingUrl();
  
  public abstract String getDiagnostics();
  
  public abstract String getJobFile();
  
  public abstract List<AMInfo> getAMInfos();
  
  public abstract boolean isUber();
  
  public abstract void setJobId(JobId paramJobId);
  
  public abstract void setJobState(JobState paramJobState);
  
  public abstract void setMapProgress(float paramFloat);
  
  public abstract void setReduceProgress(float paramFloat);
  
  public abstract void setCleanupProgress(float paramFloat);
  
  public abstract void setSetupProgress(float paramFloat);
  
  public abstract void setSubmitTime(long paramLong);
  
  public abstract void setStartTime(long paramLong);
  
  public abstract void setFinishTime(long paramLong);
  
  public abstract void setUser(String paramString);
  
  public abstract void setJobName(String paramString);
  
  public abstract void setTrackingUrl(String paramString);
  
  public abstract void setDiagnostics(String paramString);
  
  public abstract void setJobFile(String paramString);
  
  public abstract void setAMInfos(List<AMInfo> paramList);
  
  public abstract void setIsUber(boolean paramBoolean);
}


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-common-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.v2.api.records.JobReport
 * JD-Core Version:    0.7.0.1
 */