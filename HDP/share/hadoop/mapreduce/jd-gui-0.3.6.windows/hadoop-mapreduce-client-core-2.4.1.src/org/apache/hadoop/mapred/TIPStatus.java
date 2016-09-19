package org.apache.hadoop.mapred;

import org.apache.hadoop.classification.InterfaceAudience.Private;
import org.apache.hadoop.classification.InterfaceStability.Unstable;

@InterfaceAudience.Private
@InterfaceStability.Unstable
public enum TIPStatus
{
  PENDING,  RUNNING,  COMPLETE,  KILLED,  FAILED;
  
  private TIPStatus() {}
}


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.TIPStatus
 * JD-Core Version:    0.7.0.1
 */