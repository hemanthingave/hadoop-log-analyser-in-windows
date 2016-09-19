package org.apache.hadoop.mapreduce.counters;

import org.apache.hadoop.classification.InterfaceAudience.Private;
import org.apache.hadoop.classification.InterfaceAudience.Public;
import org.apache.hadoop.classification.InterfaceStability.Evolving;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapreduce.Counter;

@InterfaceAudience.Public
@InterfaceStability.Evolving
public abstract interface CounterGroupBase<T extends Counter>
  extends Writable, Iterable<T>
{
  public abstract String getName();
  
  public abstract String getDisplayName();
  
  public abstract void setDisplayName(String paramString);
  
  public abstract void addCounter(T paramT);
  
  public abstract T addCounter(String paramString1, String paramString2, long paramLong);
  
  public abstract T findCounter(String paramString1, String paramString2);
  
  public abstract T findCounter(String paramString, boolean paramBoolean);
  
  public abstract T findCounter(String paramString);
  
  public abstract int size();
  
  public abstract void incrAllCounters(CounterGroupBase<T> paramCounterGroupBase);
  
  @InterfaceAudience.Private
  public abstract CounterGroupBase<T> getUnderlyingGroup();
}


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.counters.CounterGroupBase
 * JD-Core Version:    0.7.0.1
 */