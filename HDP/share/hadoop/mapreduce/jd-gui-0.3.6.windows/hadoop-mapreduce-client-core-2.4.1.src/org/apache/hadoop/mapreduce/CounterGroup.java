package org.apache.hadoop.mapreduce;

import org.apache.hadoop.classification.InterfaceAudience.Public;
import org.apache.hadoop.classification.InterfaceStability.Stable;
import org.apache.hadoop.mapreduce.counters.CounterGroupBase;

@InterfaceAudience.Public
@InterfaceStability.Stable
public abstract interface CounterGroup
  extends CounterGroupBase<Counter>
{}


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.CounterGroup
 * JD-Core Version:    0.7.0.1
 */