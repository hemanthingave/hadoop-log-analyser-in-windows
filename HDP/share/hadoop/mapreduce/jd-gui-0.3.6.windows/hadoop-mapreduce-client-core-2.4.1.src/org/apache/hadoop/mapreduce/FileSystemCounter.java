package org.apache.hadoop.mapreduce;

import org.apache.hadoop.classification.InterfaceAudience.Private;

@InterfaceAudience.Private
public enum FileSystemCounter
{
  BYTES_READ,  BYTES_WRITTEN,  READ_OPS,  LARGE_READ_OPS,  WRITE_OPS;
  
  private FileSystemCounter() {}
}


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.FileSystemCounter
 * JD-Core Version:    0.7.0.1
 */