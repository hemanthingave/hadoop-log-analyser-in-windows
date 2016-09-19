package org.apache.hadoop.mapreduce.v2.api.records;

public abstract interface Counter
{
  public abstract String getName();
  
  public abstract String getDisplayName();
  
  public abstract long getValue();
  
  public abstract void setName(String paramString);
  
  public abstract void setDisplayName(String paramString);
  
  public abstract void setValue(long paramLong);
}


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-common-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.v2.api.records.Counter
 * JD-Core Version:    0.7.0.1
 */