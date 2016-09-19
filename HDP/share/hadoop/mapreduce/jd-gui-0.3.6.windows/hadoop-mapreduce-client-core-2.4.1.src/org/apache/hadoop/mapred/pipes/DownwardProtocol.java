package org.apache.hadoop.mapred.pipes;

import java.io.IOException;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.mapred.InputSplit;
import org.apache.hadoop.mapred.JobConf;

abstract interface DownwardProtocol<K extends WritableComparable, V extends Writable>
{
  public abstract void authenticate(String paramString1, String paramString2)
    throws IOException;
  
  public abstract void start()
    throws IOException;
  
  public abstract void setJobConf(JobConf paramJobConf)
    throws IOException;
  
  public abstract void setInputTypes(String paramString1, String paramString2)
    throws IOException;
  
  public abstract void runMap(InputSplit paramInputSplit, int paramInt, boolean paramBoolean)
    throws IOException;
  
  public abstract void mapItem(K paramK, V paramV)
    throws IOException;
  
  public abstract void runReduce(int paramInt, boolean paramBoolean)
    throws IOException;
  
  public abstract void reduceKey(K paramK)
    throws IOException;
  
  public abstract void reduceValue(V paramV)
    throws IOException;
  
  public abstract void endOfInput()
    throws IOException;
  
  public abstract void abort()
    throws IOException;
  
  public abstract void flush()
    throws IOException;
  
  public abstract void close()
    throws IOException, InterruptedException;
}


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.pipes.DownwardProtocol
 * JD-Core Version:    0.7.0.1
 */