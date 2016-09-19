package org.apache.hadoop.mapreduce;

import org.apache.hadoop.classification.InterfaceAudience.Public;
import org.apache.hadoop.classification.InterfaceStability.Evolving;

@InterfaceAudience.Public
@InterfaceStability.Evolving
public abstract interface MapContext<KEYIN, VALUEIN, KEYOUT, VALUEOUT>
  extends TaskInputOutputContext<KEYIN, VALUEIN, KEYOUT, VALUEOUT>
{
  public abstract InputSplit getInputSplit();
}


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.MapContext
 * JD-Core Version:    0.7.0.1
 */