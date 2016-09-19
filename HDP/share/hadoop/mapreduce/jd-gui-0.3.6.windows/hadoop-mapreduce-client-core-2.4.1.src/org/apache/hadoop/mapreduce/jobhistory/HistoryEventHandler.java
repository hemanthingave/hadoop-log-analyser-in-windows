package org.apache.hadoop.mapreduce.jobhistory;

import java.io.IOException;

public abstract interface HistoryEventHandler
{
  public abstract void handleEvent(HistoryEvent paramHistoryEvent)
    throws IOException;
}


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.jobhistory.HistoryEventHandler
 * JD-Core Version:    0.7.0.1
 */