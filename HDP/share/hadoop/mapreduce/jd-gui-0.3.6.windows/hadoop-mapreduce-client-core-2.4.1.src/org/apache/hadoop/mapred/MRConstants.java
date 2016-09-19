package org.apache.hadoop.mapred;

import org.apache.hadoop.classification.InterfaceAudience.Private;
import org.apache.hadoop.classification.InterfaceStability.Unstable;

@InterfaceAudience.Private
@InterfaceStability.Unstable
public abstract interface MRConstants
{
  public static final long COUNTER_UPDATE_INTERVAL = 60000L;
  public static final int SUCCESS = 0;
  public static final int FILE_NOT_FOUND = -1;
  public static final String MAP_OUTPUT_LENGTH = "Map-Output-Length";
  public static final String RAW_MAP_OUTPUT_LENGTH = "Raw-Map-Output-Length";
  public static final String FROM_MAP_TASK = "from-map-task";
  public static final String FOR_REDUCE_TASK = "for-reduce-task";
  public static final String WORKDIR = "work";
  public static final String APPLICATION_ATTEMPT_ID = "mapreduce.job.application.attempt.id";
}


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.MRConstants
 * JD-Core Version:    0.7.0.1
 */