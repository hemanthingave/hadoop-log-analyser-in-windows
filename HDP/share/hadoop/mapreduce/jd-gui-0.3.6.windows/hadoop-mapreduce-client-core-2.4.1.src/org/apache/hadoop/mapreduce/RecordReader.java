package org.apache.hadoop.mapreduce;

import java.io.Closeable;
import java.io.IOException;
import org.apache.hadoop.classification.InterfaceAudience.Public;
import org.apache.hadoop.classification.InterfaceStability.Stable;

@InterfaceAudience.Public
@InterfaceStability.Stable
public abstract class RecordReader<KEYIN, VALUEIN>
  implements Closeable
{
  public abstract void initialize(InputSplit paramInputSplit, TaskAttemptContext paramTaskAttemptContext)
    throws IOException, InterruptedException;
  
  public abstract boolean nextKeyValue()
    throws IOException, InterruptedException;
  
  public abstract KEYIN getCurrentKey()
    throws IOException, InterruptedException;
  
  public abstract VALUEIN getCurrentValue()
    throws IOException, InterruptedException;
  
  public abstract float getProgress()
    throws IOException, InterruptedException;
  
  public abstract void close()
    throws IOException;
}


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.RecordReader
 * JD-Core Version:    0.7.0.1
 */