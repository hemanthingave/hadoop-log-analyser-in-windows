package org.apache.hadoop.mapreduce.v2.api.records;

import java.util.Map;

public abstract interface CounterGroup
{
  public abstract String getName();
  
  public abstract String getDisplayName();
  
  public abstract Map<String, Counter> getAllCounters();
  
  public abstract Counter getCounter(String paramString);
  
  public abstract void setName(String paramString);
  
  public abstract void setDisplayName(String paramString);
  
  public abstract void addAllCounters(Map<String, Counter> paramMap);
  
  public abstract void setCounter(String paramString, Counter paramCounter);
  
  public abstract void removeCounter(String paramString);
  
  public abstract void clearCounters();
}


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-common-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.v2.api.records.CounterGroup
 * JD-Core Version:    0.7.0.1
 */