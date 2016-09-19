package org.apache.hadoop.mapred.join;

import java.io.IOException;
import org.apache.hadoop.classification.InterfaceAudience.Public;
import org.apache.hadoop.classification.InterfaceStability.Stable;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.mapred.RecordReader;

@InterfaceAudience.Public
@InterfaceStability.Stable
public abstract interface ComposableRecordReader<K extends WritableComparable, V extends Writable>
  extends RecordReader<K, V>, Comparable<ComposableRecordReader<K, ?>>
{
  public abstract int id();
  
  public abstract K key();
  
  public abstract void key(K paramK)
    throws IOException;
  
  public abstract boolean hasNext();
  
  public abstract void skip(K paramK)
    throws IOException;
  
  public abstract void accept(CompositeRecordReader.JoinCollector paramJoinCollector, K paramK)
    throws IOException;
}


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.join.ComposableRecordReader
 * JD-Core Version:    0.7.0.1
 */