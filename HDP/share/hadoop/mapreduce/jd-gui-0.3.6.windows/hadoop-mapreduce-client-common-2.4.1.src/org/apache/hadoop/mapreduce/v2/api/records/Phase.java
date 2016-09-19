package org.apache.hadoop.mapreduce.v2.api.records;

public enum Phase
{
  STARTING,  MAP,  SHUFFLE,  SORT,  REDUCE,  CLEANUP;
  
  private Phase() {}
}


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-common-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.v2.api.records.Phase
 * JD-Core Version:    0.7.0.1
 */