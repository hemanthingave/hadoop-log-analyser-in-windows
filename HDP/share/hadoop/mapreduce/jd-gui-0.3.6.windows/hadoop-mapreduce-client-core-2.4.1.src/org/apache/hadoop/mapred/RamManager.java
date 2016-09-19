package org.apache.hadoop.mapred;

import java.io.InputStream;

abstract interface RamManager
{
  public abstract boolean reserve(int paramInt, InputStream paramInputStream)
    throws InterruptedException;
  
  public abstract void unreserve(int paramInt);
}


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.RamManager
 * JD-Core Version:    0.7.0.1
 */