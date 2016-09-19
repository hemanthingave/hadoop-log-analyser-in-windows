package org.apache.hadoop.mapreduce;

import java.io.IOException;
import org.apache.hadoop.classification.InterfaceAudience.Public;
import org.apache.hadoop.classification.InterfaceStability.Stable;

@InterfaceAudience.Public
@InterfaceStability.Stable
public abstract class RecordWriter<K, V>
{
  public abstract void write(K paramK, V paramV)
    throws IOException, InterruptedException;
  
  public abstract void close(TaskAttemptContext paramTaskAttemptContext)
    throws IOException, InterruptedException;
}


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.RecordWriter
 * JD-Core Version:    0.7.0.1
 */