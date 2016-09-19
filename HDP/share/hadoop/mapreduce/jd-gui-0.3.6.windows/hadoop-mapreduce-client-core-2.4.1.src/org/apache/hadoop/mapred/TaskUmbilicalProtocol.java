package org.apache.hadoop.mapred;

import java.io.IOException;
import org.apache.hadoop.classification.InterfaceAudience.Private;
import org.apache.hadoop.classification.InterfaceStability.Stable;
import org.apache.hadoop.ipc.VersionedProtocol;
import org.apache.hadoop.mapreduce.security.token.JobTokenSelector;
import org.apache.hadoop.security.token.TokenInfo;

@TokenInfo(JobTokenSelector.class)
@InterfaceAudience.Private
@InterfaceStability.Stable
public abstract interface TaskUmbilicalProtocol
  extends VersionedProtocol
{
  public static final long versionID = 19L;
  
  public abstract JvmTask getTask(JvmContext paramJvmContext)
    throws IOException;
  
  public abstract boolean statusUpdate(TaskAttemptID paramTaskAttemptID, TaskStatus paramTaskStatus)
    throws IOException, InterruptedException;
  
  public abstract void reportDiagnosticInfo(TaskAttemptID paramTaskAttemptID, String paramString)
    throws IOException;
  
  public abstract void reportNextRecordRange(TaskAttemptID paramTaskAttemptID, SortedRanges.Range paramRange)
    throws IOException;
  
  public abstract boolean ping(TaskAttemptID paramTaskAttemptID)
    throws IOException;
  
  public abstract void done(TaskAttemptID paramTaskAttemptID)
    throws IOException;
  
  public abstract void commitPending(TaskAttemptID paramTaskAttemptID, TaskStatus paramTaskStatus)
    throws IOException, InterruptedException;
  
  public abstract boolean canCommit(TaskAttemptID paramTaskAttemptID)
    throws IOException;
  
  public abstract void shuffleError(TaskAttemptID paramTaskAttemptID, String paramString)
    throws IOException;
  
  public abstract void fsError(TaskAttemptID paramTaskAttemptID, String paramString)
    throws IOException;
  
  public abstract void fatalError(TaskAttemptID paramTaskAttemptID, String paramString)
    throws IOException;
  
  public abstract MapTaskCompletionEventsUpdate getMapCompletionEvents(JobID paramJobID, int paramInt1, int paramInt2, TaskAttemptID paramTaskAttemptID)
    throws IOException;
}


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.TaskUmbilicalProtocol
 * JD-Core Version:    0.7.0.1
 */