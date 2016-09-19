package org.apache.hadoop.mapreduce.lib.output;

import java.io.IOException;
import org.apache.hadoop.classification.InterfaceAudience.Public;
import org.apache.hadoop.classification.InterfaceStability.Evolving;
import org.apache.hadoop.mapreduce.TaskAttemptContext;

@InterfaceAudience.Public
@InterfaceStability.Evolving
public abstract interface PartialOutputCommitter
{
  public abstract void cleanUpPartialOutputForTask(TaskAttemptContext paramTaskAttemptContext)
    throws IOException;
}


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.lib.output.PartialOutputCommitter
 * JD-Core Version:    0.7.0.1
 */