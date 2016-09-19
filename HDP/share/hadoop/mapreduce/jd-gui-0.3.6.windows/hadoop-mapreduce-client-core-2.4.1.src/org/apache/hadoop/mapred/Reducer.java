package org.apache.hadoop.mapred;

import java.io.IOException;
import java.util.Iterator;
import org.apache.hadoop.classification.InterfaceAudience.Public;
import org.apache.hadoop.classification.InterfaceStability.Stable;
import org.apache.hadoop.io.Closeable;

@InterfaceAudience.Public
@InterfaceStability.Stable
public abstract interface Reducer<K2, V2, K3, V3>
  extends JobConfigurable, Closeable
{
  public abstract void reduce(K2 paramK2, Iterator<V2> paramIterator, OutputCollector<K3, V3> paramOutputCollector, Reporter paramReporter)
    throws IOException;
}


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.Reducer
 * JD-Core Version:    0.7.0.1
 */