package org.apache.hadoop.mapred;

import java.io.IOException;
import org.apache.hadoop.classification.InterfaceAudience.Public;
import org.apache.hadoop.classification.InterfaceStability.Stable;
import org.apache.hadoop.conf.Configuration;

@InterfaceAudience.Public
@InterfaceStability.Stable
public abstract interface RunningJob
{
  public abstract Configuration getConfiguration();
  
  public abstract JobID getID();
  
  @Deprecated
  public abstract String getJobID();
  
  public abstract String getJobName();
  
  public abstract String getJobFile();
  
  public abstract String getTrackingURL();
  
  public abstract float mapProgress()
    throws IOException;
  
  public abstract float reduceProgress()
    throws IOException;
  
  public abstract float cleanupProgress()
    throws IOException;
  
  public abstract float setupProgress()
    throws IOException;
  
  public abstract boolean isComplete()
    throws IOException;
  
  public abstract boolean isSuccessful()
    throws IOException;
  
  public abstract void waitForCompletion()
    throws IOException;
  
  public abstract int getJobState()
    throws IOException;
  
  public abstract JobStatus getJobStatus()
    throws IOException;
  
  public abstract void killJob()
    throws IOException;
  
  public abstract void setJobPriority(String paramString)
    throws IOException;
  
  public abstract TaskCompletionEvent[] getTaskCompletionEvents(int paramInt)
    throws IOException;
  
  public abstract void killTask(TaskAttemptID paramTaskAttemptID, boolean paramBoolean)
    throws IOException;
  
  @Deprecated
  public abstract void killTask(String paramString, boolean paramBoolean)
    throws IOException;
  
  public abstract Counters getCounters()
    throws IOException;
  
  public abstract String[] getTaskDiagnostics(TaskAttemptID paramTaskAttemptID)
    throws IOException;
  
  public abstract String getHistoryUrl()
    throws IOException;
  
  public abstract boolean isRetired()
    throws IOException;
  
  public abstract String getFailureInfo()
    throws IOException;
}


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.RunningJob
 * JD-Core Version:    0.7.0.1
 */