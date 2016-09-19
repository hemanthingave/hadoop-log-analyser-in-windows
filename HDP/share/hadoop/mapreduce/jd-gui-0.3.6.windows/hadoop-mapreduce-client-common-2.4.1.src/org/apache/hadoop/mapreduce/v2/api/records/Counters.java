package org.apache.hadoop.mapreduce.v2.api.records;

import java.util.Map;

public abstract interface Counters
{
  public abstract Map<String, CounterGroup> getAllCounterGroups();
  
  public abstract CounterGroup getCounterGroup(String paramString);
  
  public abstract Counter getCounter(Enum<?> paramEnum);
  
  public abstract void addAllCounterGroups(Map<String, CounterGroup> paramMap);
  
  public abstract void setCounterGroup(String paramString, CounterGroup paramCounterGroup);
  
  public abstract void removeCounterGroup(String paramString);
  
  public abstract void clearCounterGroups();
  
  public abstract void incrCounter(Enum<?> paramEnum, long paramLong);
}


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-common-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.v2.api.records.Counters
 * JD-Core Version:    0.7.0.1
 */