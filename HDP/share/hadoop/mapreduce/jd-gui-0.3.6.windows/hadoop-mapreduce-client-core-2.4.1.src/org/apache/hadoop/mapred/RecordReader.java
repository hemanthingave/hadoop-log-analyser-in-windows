package org.apache.hadoop.mapred;

import java.io.IOException;
import org.apache.hadoop.classification.InterfaceAudience.Public;
import org.apache.hadoop.classification.InterfaceStability.Stable;

@InterfaceAudience.Public
@InterfaceStability.Stable
public abstract interface RecordReader<K, V>
{
  public abstract boolean next(K paramK, V paramV)
    throws IOException;
  
  public abstract K createKey();
  
  public abstract V createValue();
  
  public abstract long getPos()
    throws IOException;
  
  public abstract void close()
    throws IOException;
  
  public abstract float getProgress()
    throws IOException;
}


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.RecordReader
 * JD-Core Version:    0.7.0.1
 */