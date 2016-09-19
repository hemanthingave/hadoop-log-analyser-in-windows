package org.apache.hadoop.mapred;

import java.io.IOException;
import org.apache.hadoop.classification.InterfaceAudience.Public;
import org.apache.hadoop.classification.InterfaceStability.Stable;

@InterfaceAudience.Public
@InterfaceStability.Stable
public abstract interface MapRunnable<K1, V1, K2, V2>
  extends JobConfigurable
{
  public abstract void run(RecordReader<K1, V1> paramRecordReader, OutputCollector<K2, V2> paramOutputCollector, Reporter paramReporter)
    throws IOException;
}


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.MapRunnable
 * JD-Core Version:    0.7.0.1
 */