package org.apache.hadoop.mapred;

import java.io.IOException;
import org.apache.hadoop.classification.InterfaceAudience.Private;
import org.apache.hadoop.classification.InterfaceStability.Unstable;
import org.apache.hadoop.io.DataInputBuffer;
import org.apache.hadoop.util.Progress;

@InterfaceAudience.Private
@InterfaceStability.Unstable
public abstract interface RawKeyValueIterator
{
  public abstract DataInputBuffer getKey()
    throws IOException;
  
  public abstract DataInputBuffer getValue()
    throws IOException;
  
  public abstract boolean next()
    throws IOException;
  
  public abstract void close()
    throws IOException;
  
  public abstract Progress getProgress();
}


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.RawKeyValueIterator
 * JD-Core Version:    0.7.0.1
 */