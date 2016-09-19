package org.apache.hadoop.mapred;

import java.io.IOException;
import org.apache.hadoop.classification.InterfaceAudience.Public;
import org.apache.hadoop.classification.InterfaceStability.Stable;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.util.Progressable;

@InterfaceAudience.Public
@InterfaceStability.Stable
public abstract interface OutputFormat<K, V>
{
  public abstract RecordWriter<K, V> getRecordWriter(FileSystem paramFileSystem, JobConf paramJobConf, String paramString, Progressable paramProgressable)
    throws IOException;
  
  public abstract void checkOutputSpecs(FileSystem paramFileSystem, JobConf paramJobConf)
    throws IOException;
}


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.OutputFormat
 * JD-Core Version:    0.7.0.1
 */