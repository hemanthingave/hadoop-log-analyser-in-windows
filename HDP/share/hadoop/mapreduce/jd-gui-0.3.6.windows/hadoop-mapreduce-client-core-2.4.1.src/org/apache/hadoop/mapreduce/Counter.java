package org.apache.hadoop.mapreduce;

import org.apache.hadoop.classification.InterfaceAudience.Private;
import org.apache.hadoop.classification.InterfaceAudience.Public;
import org.apache.hadoop.classification.InterfaceStability.Stable;
import org.apache.hadoop.io.Writable;

@InterfaceAudience.Public
@InterfaceStability.Stable
public abstract interface Counter
  extends Writable
{
  @Deprecated
  public abstract void setDisplayName(String paramString);
  
  public abstract String getName();
  
  public abstract String getDisplayName();
  
  public abstract long getValue();
  
  public abstract void setValue(long paramLong);
  
  public abstract void increment(long paramLong);
  
  @InterfaceAudience.Private
  public abstract Counter getUnderlyingCounter();
}


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.Counter
 * JD-Core Version:    0.7.0.1
 */