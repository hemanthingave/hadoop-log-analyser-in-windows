package org.apache.hadoop.mapreduce.v2.api.protocolrecords;

import org.apache.hadoop.mapreduce.v2.api.records.Counters;

public abstract interface GetCountersResponse
{
  public abstract Counters getCounters();
  
  public abstract void setCounters(Counters paramCounters);
}


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-common-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.v2.api.protocolrecords.GetCountersResponse
 * JD-Core Version:    0.7.0.1
 */