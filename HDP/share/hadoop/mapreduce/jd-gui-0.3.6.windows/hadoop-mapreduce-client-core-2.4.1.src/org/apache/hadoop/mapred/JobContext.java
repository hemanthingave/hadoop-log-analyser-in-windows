package org.apache.hadoop.mapred;

import org.apache.hadoop.classification.InterfaceAudience.Public;
import org.apache.hadoop.classification.InterfaceStability.Stable;
import org.apache.hadoop.util.Progressable;

@InterfaceAudience.Public
@InterfaceStability.Stable
public abstract interface JobContext
  extends org.apache.hadoop.mapreduce.JobContext
{
  public abstract JobConf getJobConf();
  
  public abstract Progressable getProgressible();
}


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.JobContext
 * JD-Core Version:    0.7.0.1
 */