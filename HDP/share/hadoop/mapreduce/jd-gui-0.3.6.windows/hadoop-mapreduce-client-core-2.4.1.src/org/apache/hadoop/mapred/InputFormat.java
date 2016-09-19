package org.apache.hadoop.mapred;

import java.io.IOException;
import org.apache.hadoop.classification.InterfaceAudience.Public;
import org.apache.hadoop.classification.InterfaceStability.Stable;

@InterfaceAudience.Public
@InterfaceStability.Stable
public abstract interface InputFormat<K, V>
{
  public abstract InputSplit[] getSplits(JobConf paramJobConf, int paramInt)
    throws IOException;
  
  public abstract RecordReader<K, V> getRecordReader(InputSplit paramInputSplit, JobConf paramJobConf, Reporter paramReporter)
    throws IOException;
}


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.InputFormat
 * JD-Core Version:    0.7.0.1
 */