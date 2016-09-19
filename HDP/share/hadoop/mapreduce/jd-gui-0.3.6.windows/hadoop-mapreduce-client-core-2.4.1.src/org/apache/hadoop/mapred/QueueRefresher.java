package org.apache.hadoop.mapred;

import java.util.List;

abstract class QueueRefresher
{
  abstract void refreshQueues(List<JobQueueInfo> paramList)
    throws Throwable;
}


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.QueueRefresher
 * JD-Core Version:    0.7.0.1
 */