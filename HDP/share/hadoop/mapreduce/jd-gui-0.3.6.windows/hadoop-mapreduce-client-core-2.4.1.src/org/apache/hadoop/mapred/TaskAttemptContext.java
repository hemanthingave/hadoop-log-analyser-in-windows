package org.apache.hadoop.mapred;

import org.apache.hadoop.classification.InterfaceAudience.Public;
import org.apache.hadoop.classification.InterfaceStability.Stable;
import org.apache.hadoop.util.Progressable;

@InterfaceAudience.Public
@InterfaceStability.Stable
public abstract interface TaskAttemptContext
  extends org.apache.hadoop.mapreduce.TaskAttemptContext
{
  public abstract TaskAttemptID getTaskAttemptID();
  
  public abstract Progressable getProgressible();
  
  public abstract JobConf getJobConf();
}


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.TaskAttemptContext
 * JD-Core Version:    0.7.0.1
 */