package org.apache.hadoop.mapred.join;

import java.io.IOException;
import org.apache.hadoop.classification.InterfaceAudience.Public;
import org.apache.hadoop.classification.InterfaceStability.Stable;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.mapred.InputFormat;
import org.apache.hadoop.mapred.InputSplit;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.Reporter;

@InterfaceAudience.Public
@InterfaceStability.Stable
public abstract interface ComposableInputFormat<K extends WritableComparable, V extends Writable>
  extends InputFormat<K, V>
{
  public abstract ComposableRecordReader<K, V> getRecordReader(InputSplit paramInputSplit, JobConf paramJobConf, Reporter paramReporter)
    throws IOException;
}


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.join.ComposableInputFormat
 * JD-Core Version:    0.7.0.1
 */