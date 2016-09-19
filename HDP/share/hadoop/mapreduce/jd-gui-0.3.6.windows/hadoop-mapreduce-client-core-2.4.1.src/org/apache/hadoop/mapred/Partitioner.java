package org.apache.hadoop.mapred;

import org.apache.hadoop.classification.InterfaceAudience.Public;
import org.apache.hadoop.classification.InterfaceStability.Stable;

@InterfaceAudience.Public
@InterfaceStability.Stable
public abstract interface Partitioner<K2, V2>
  extends JobConfigurable
{
  public abstract int getPartition(K2 paramK2, V2 paramV2, int paramInt);
}


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.Partitioner
 * JD-Core Version:    0.7.0.1
 */