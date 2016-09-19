package org.apache.hadoop.mapreduce.jobhistory;

import org.apache.hadoop.classification.InterfaceAudience.Private;
import org.apache.hadoop.classification.InterfaceStability.Unstable;

@InterfaceAudience.Private
@InterfaceStability.Unstable
public abstract interface HistoryEvent
{
  public abstract EventType getEventType();
  
  public abstract Object getDatum();
  
  public abstract void setDatum(Object paramObject);
}


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.jobhistory.HistoryEvent
 * JD-Core Version:    0.7.0.1
 */