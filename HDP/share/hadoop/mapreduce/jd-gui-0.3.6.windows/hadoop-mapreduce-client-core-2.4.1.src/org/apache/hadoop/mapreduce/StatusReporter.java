package org.apache.hadoop.mapreduce;

import org.apache.hadoop.classification.InterfaceAudience.Private;

@InterfaceAudience.Private
public abstract class StatusReporter
{
  public abstract Counter getCounter(Enum<?> paramEnum);
  
  public abstract Counter getCounter(String paramString1, String paramString2);
  
  public abstract void progress();
  
  public abstract float getProgress();
  
  public abstract void setStatus(String paramString);
}


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.StatusReporter
 * JD-Core Version:    0.7.0.1
 */