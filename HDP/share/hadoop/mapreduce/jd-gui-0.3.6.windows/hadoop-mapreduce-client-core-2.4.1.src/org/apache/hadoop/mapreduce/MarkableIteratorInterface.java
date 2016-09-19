package org.apache.hadoop.mapreduce;

import java.io.IOException;
import java.util.Iterator;
import org.apache.hadoop.classification.InterfaceAudience.Public;
import org.apache.hadoop.classification.InterfaceStability.Evolving;

@InterfaceAudience.Public
@InterfaceStability.Evolving
abstract interface MarkableIteratorInterface<VALUE>
  extends Iterator<VALUE>
{
  public abstract void mark()
    throws IOException;
  
  public abstract void reset()
    throws IOException;
  
  public abstract void clearMark()
    throws IOException;
}


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.MarkableIteratorInterface
 * JD-Core Version:    0.7.0.1
 */