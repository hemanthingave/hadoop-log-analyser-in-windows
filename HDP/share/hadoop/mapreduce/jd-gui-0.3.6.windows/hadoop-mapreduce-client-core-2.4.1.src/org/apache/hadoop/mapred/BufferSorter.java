package org.apache.hadoop.mapred;

import org.apache.hadoop.io.OutputBuffer;
import org.apache.hadoop.io.SequenceFile.Sorter.RawKeyValueIterator;
import org.apache.hadoop.util.Progressable;

abstract interface BufferSorter
  extends JobConfigurable
{
  public abstract void setProgressable(Progressable paramProgressable);
  
  public abstract void addKeyValue(int paramInt1, int paramInt2, int paramInt3);
  
  public abstract void setInputBuffer(OutputBuffer paramOutputBuffer);
  
  public abstract long getMemoryUtilized();
  
  public abstract SequenceFile.Sorter.RawKeyValueIterator sort();
  
  public abstract void close();
}


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.BufferSorter
 * JD-Core Version:    0.7.0.1
 */