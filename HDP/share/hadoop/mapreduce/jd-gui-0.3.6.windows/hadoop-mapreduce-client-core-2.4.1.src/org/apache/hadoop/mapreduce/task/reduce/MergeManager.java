package org.apache.hadoop.mapreduce.task.reduce;

import java.io.IOException;
import org.apache.hadoop.classification.InterfaceAudience.Private;
import org.apache.hadoop.classification.InterfaceStability.Unstable;
import org.apache.hadoop.mapred.RawKeyValueIterator;
import org.apache.hadoop.mapreduce.TaskAttemptID;

@InterfaceAudience.Private
@InterfaceStability.Unstable
public abstract interface MergeManager<K, V>
{
  public abstract void waitForResource()
    throws InterruptedException;
  
  public abstract MapOutput<K, V> reserve(TaskAttemptID paramTaskAttemptID, long paramLong, int paramInt)
    throws IOException;
  
  public abstract RawKeyValueIterator close()
    throws Throwable;
}


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.task.reduce.MergeManager
 * JD-Core Version:    0.7.0.1
 */