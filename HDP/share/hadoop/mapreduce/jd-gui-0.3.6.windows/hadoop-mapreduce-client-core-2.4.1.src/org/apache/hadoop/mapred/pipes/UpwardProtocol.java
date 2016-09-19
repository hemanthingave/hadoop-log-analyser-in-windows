package org.apache.hadoop.mapred.pipes;

import java.io.IOException;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.WritableComparable;

abstract interface UpwardProtocol<K extends WritableComparable, V extends Writable>
{
  public abstract void output(K paramK, V paramV)
    throws IOException;
  
  public abstract void partitionedOutput(int paramInt, K paramK, V paramV)
    throws IOException;
  
  public abstract void status(String paramString)
    throws IOException;
  
  public abstract void progress(float paramFloat)
    throws IOException;
  
  public abstract void done()
    throws IOException;
  
  public abstract void failed(Throwable paramThrowable);
  
  public abstract void registerCounter(int paramInt, String paramString1, String paramString2)
    throws IOException;
  
  public abstract void incrementCounter(int paramInt, long paramLong)
    throws IOException;
  
  public abstract boolean authenticate(String paramString)
    throws IOException;
}


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.pipes.UpwardProtocol
 * JD-Core Version:    0.7.0.1
 */