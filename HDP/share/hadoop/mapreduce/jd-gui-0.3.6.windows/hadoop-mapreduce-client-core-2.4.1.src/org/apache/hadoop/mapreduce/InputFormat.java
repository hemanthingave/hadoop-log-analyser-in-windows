package org.apache.hadoop.mapreduce;

import java.io.IOException;
import java.util.List;
import org.apache.hadoop.classification.InterfaceAudience.Public;
import org.apache.hadoop.classification.InterfaceStability.Stable;

@InterfaceAudience.Public
@InterfaceStability.Stable
public abstract class InputFormat<K, V>
{
  public abstract List<InputSplit> getSplits(JobContext paramJobContext)
    throws IOException, InterruptedException;
  
  public abstract RecordReader<K, V> createRecordReader(InputSplit paramInputSplit, TaskAttemptContext paramTaskAttemptContext)
    throws IOException, InterruptedException;
}


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.InputFormat
 * JD-Core Version:    0.7.0.1
 */