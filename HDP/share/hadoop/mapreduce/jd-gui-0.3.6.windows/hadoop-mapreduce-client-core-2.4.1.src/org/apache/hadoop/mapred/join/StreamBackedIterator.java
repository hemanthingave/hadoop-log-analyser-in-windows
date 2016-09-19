package org.apache.hadoop.mapred.join;

import org.apache.hadoop.classification.InterfaceAudience.Public;
import org.apache.hadoop.classification.InterfaceStability.Stable;
import org.apache.hadoop.io.Writable;

@InterfaceAudience.Public
@InterfaceStability.Stable
public class StreamBackedIterator<X extends Writable>
  extends org.apache.hadoop.mapreduce.lib.join.StreamBackedIterator<X>
  implements ResetableIterator<X>
{}


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.join.StreamBackedIterator
 * JD-Core Version:    0.7.0.1
 */