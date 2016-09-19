package org.apache.hadoop.mapreduce.lib.join;

import java.io.IOException;
import org.apache.hadoop.classification.InterfaceAudience.Public;
import org.apache.hadoop.classification.InterfaceStability.Stable;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.mapreduce.RecordReader;

@InterfaceAudience.Public
@InterfaceStability.Stable
public abstract class ComposableRecordReader<K extends WritableComparable<?>, V extends Writable>
  extends RecordReader<K, V>
  implements Comparable<ComposableRecordReader<K, ?>>
{
  abstract int id();
  
  abstract K key();
  
  abstract void key(K paramK)
    throws IOException;
  
  abstract K createKey();
  
  abstract V createValue();
  
  abstract boolean hasNext();
  
  abstract void skip(K paramK)
    throws IOException, InterruptedException;
  
  abstract void accept(CompositeRecordReader.JoinCollector paramJoinCollector, K paramK)
    throws IOException, InterruptedException;
}


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.lib.join.ComposableRecordReader
 * JD-Core Version:    0.7.0.1
 */