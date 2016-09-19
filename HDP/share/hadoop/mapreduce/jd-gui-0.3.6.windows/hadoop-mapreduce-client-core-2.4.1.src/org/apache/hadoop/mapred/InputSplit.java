package org.apache.hadoop.mapred;

import java.io.IOException;
import org.apache.hadoop.classification.InterfaceAudience.Public;
import org.apache.hadoop.classification.InterfaceStability.Stable;
import org.apache.hadoop.io.Writable;

@InterfaceAudience.Public
@InterfaceStability.Stable
public abstract interface InputSplit
  extends Writable
{
  public abstract long getLength()
    throws IOException;
  
  public abstract String[] getLocations()
    throws IOException;
}


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.InputSplit
 * JD-Core Version:    0.7.0.1
 */