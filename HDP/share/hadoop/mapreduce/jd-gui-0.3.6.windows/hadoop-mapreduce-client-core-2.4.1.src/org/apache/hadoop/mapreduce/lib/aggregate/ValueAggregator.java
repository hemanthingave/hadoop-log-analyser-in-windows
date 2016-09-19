package org.apache.hadoop.mapreduce.lib.aggregate;

import java.util.ArrayList;
import org.apache.hadoop.classification.InterfaceAudience.Public;
import org.apache.hadoop.classification.InterfaceStability.Stable;

@InterfaceAudience.Public
@InterfaceStability.Stable
public abstract interface ValueAggregator<E>
{
  public abstract void addNextValue(Object paramObject);
  
  public abstract void reset();
  
  public abstract String getReport();
  
  public abstract ArrayList<E> getCombinerOutput();
}


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.lib.aggregate.ValueAggregator
 * JD-Core Version:    0.7.0.1
 */