package org.apache.hadoop.mapreduce.task.reduce;

import java.io.IOException;
import org.apache.hadoop.classification.InterfaceAudience.Private;
import org.apache.hadoop.classification.InterfaceStability.Unstable;
import org.apache.hadoop.mapred.TaskCompletionEvent;

@InterfaceAudience.Private
@InterfaceStability.Unstable
public abstract interface ShuffleScheduler<K, V>
{
  public abstract boolean waitUntilDone(int paramInt)
    throws InterruptedException;
  
  public abstract void resolve(TaskCompletionEvent paramTaskCompletionEvent)
    throws IOException, InterruptedException;
  
  public abstract void close()
    throws InterruptedException;
}


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.task.reduce.ShuffleScheduler
 * JD-Core Version:    0.7.0.1
 */