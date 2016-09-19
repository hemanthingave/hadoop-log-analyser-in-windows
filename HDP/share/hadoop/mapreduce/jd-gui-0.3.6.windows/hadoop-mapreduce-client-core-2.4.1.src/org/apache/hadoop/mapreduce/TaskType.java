package org.apache.hadoop.mapreduce;

import org.apache.hadoop.classification.InterfaceAudience.Public;
import org.apache.hadoop.classification.InterfaceStability.Stable;

@InterfaceAudience.Public
@InterfaceStability.Stable
public enum TaskType
{
  MAP,  REDUCE,  JOB_SETUP,  JOB_CLEANUP,  TASK_CLEANUP;
  
  private TaskType() {}
}


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.TaskType
 * JD-Core Version:    0.7.0.1
 */