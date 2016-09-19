package org.apache.hadoop.mapreduce.lib.join;

import java.io.IOException;
import org.apache.hadoop.classification.InterfaceAudience.Public;
import org.apache.hadoop.classification.InterfaceStability.Stable;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.mapreduce.InputFormat;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.TaskAttemptContext;

@InterfaceAudience.Public
@InterfaceStability.Stable
public abstract class ComposableInputFormat<K extends WritableComparable<?>, V extends Writable>
  extends InputFormat<K, V>
{
  public abstract ComposableRecordReader<K, V> createRecordReader(InputSplit paramInputSplit, TaskAttemptContext paramTaskAttemptContext)
    throws IOException, InterruptedException;
}


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.lib.join.ComposableInputFormat
 * JD-Core Version:    0.7.0.1
 */