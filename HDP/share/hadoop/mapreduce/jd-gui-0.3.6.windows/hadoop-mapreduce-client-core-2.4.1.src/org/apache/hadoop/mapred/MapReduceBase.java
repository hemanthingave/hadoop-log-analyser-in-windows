package org.apache.hadoop.mapred;

import java.io.IOException;
import org.apache.hadoop.classification.InterfaceAudience.Public;
import org.apache.hadoop.classification.InterfaceStability.Stable;
import org.apache.hadoop.io.Closeable;

@InterfaceAudience.Public
@InterfaceStability.Stable
public class MapReduceBase
  implements Closeable, JobConfigurable
{
  public void close()
    throws IOException
  {}
  
  public void configure(JobConf job) {}
}


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.MapReduceBase
 * JD-Core Version:    0.7.0.1
 */