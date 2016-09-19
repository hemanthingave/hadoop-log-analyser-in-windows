package org.apache.hadoop.mapred;

import org.apache.hadoop.classification.InterfaceAudience.Public;
import org.apache.hadoop.classification.InterfaceStability.Stable;

@InterfaceAudience.Public
@InterfaceStability.Stable
public enum JobPriority
{
  VERY_HIGH,  HIGH,  NORMAL,  LOW,  VERY_LOW;
  
  private JobPriority() {}
}


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.JobPriority
 * JD-Core Version:    0.7.0.1
 */