package org.apache.hadoop.mapreduce.task.reduce;

import org.apache.hadoop.classification.InterfaceAudience.LimitedPrivate;
import org.apache.hadoop.classification.InterfaceStability.Unstable;

@InterfaceAudience.LimitedPrivate({"MapReduce"})
@InterfaceStability.Unstable
public abstract interface ExceptionReporter
{
  public abstract void reportException(Throwable paramThrowable);
}


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.task.reduce.ExceptionReporter
 * JD-Core Version:    0.7.0.1
 */