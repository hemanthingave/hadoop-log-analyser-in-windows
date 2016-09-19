package org.apache.hadoop.mapreduce;

import org.apache.hadoop.classification.InterfaceAudience.Public;
import org.apache.hadoop.classification.InterfaceStability.Evolving;
import org.apache.hadoop.util.Progressable;

@InterfaceAudience.Public
@InterfaceStability.Evolving
public abstract interface TaskAttemptContext
  extends JobContext, Progressable
{
  public abstract TaskAttemptID getTaskAttemptID();
  
  public abstract void setStatus(String paramString);
  
  public abstract String getStatus();
  
  public abstract float getProgress();
  
  public abstract Counter getCounter(Enum<?> paramEnum);
  
  public abstract Counter getCounter(String paramString1, String paramString2);
}


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.TaskAttemptContext
 * JD-Core Version:    0.7.0.1
 */