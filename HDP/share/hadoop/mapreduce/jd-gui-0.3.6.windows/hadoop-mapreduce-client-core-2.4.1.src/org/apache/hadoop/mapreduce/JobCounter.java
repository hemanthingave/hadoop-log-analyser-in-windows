package org.apache.hadoop.mapreduce;

import org.apache.hadoop.classification.InterfaceAudience.Public;
import org.apache.hadoop.classification.InterfaceStability.Evolving;

@InterfaceAudience.Public
@InterfaceStability.Evolving
public enum JobCounter
{
  NUM_FAILED_MAPS,  NUM_FAILED_REDUCES,  NUM_KILLED_MAPS,  NUM_KILLED_REDUCES,  TOTAL_LAUNCHED_MAPS,  TOTAL_LAUNCHED_REDUCES,  OTHER_LOCAL_MAPS,  DATA_LOCAL_MAPS,  RACK_LOCAL_MAPS,  SLOTS_MILLIS_MAPS,  SLOTS_MILLIS_REDUCES,  FALLOW_SLOTS_MILLIS_MAPS,  FALLOW_SLOTS_MILLIS_REDUCES,  TOTAL_LAUNCHED_UBERTASKS,  NUM_UBER_SUBMAPS,  NUM_UBER_SUBREDUCES,  NUM_FAILED_UBERTASKS,  MILLIS_MAPS,  MILLIS_REDUCES,  VCORES_MILLIS_MAPS,  VCORES_MILLIS_REDUCES,  MB_MILLIS_MAPS,  MB_MILLIS_REDUCES;
  
  private JobCounter() {}
}


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.JobCounter
 * JD-Core Version:    0.7.0.1
 */