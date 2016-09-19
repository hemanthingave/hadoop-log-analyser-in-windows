package org.apache.hadoop.mapreduce.protocol;

import java.io.IOException;
import org.apache.hadoop.classification.InterfaceAudience.Private;
import org.apache.hadoop.classification.InterfaceStability.Stable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.ipc.VersionedProtocol;
import org.apache.hadoop.mapreduce.Cluster.JobTrackerStatus;
import org.apache.hadoop.mapreduce.ClusterMetrics;
import org.apache.hadoop.mapreduce.Counters;
import org.apache.hadoop.mapreduce.JobID;
import org.apache.hadoop.mapreduce.JobStatus;
import org.apache.hadoop.mapreduce.QueueAclsInfo;
import org.apache.hadoop.mapreduce.QueueInfo;
import org.apache.hadoop.mapreduce.TaskAttemptID;
import org.apache.hadoop.mapreduce.TaskCompletionEvent;
import org.apache.hadoop.mapreduce.TaskReport;
import org.apache.hadoop.mapreduce.TaskTrackerInfo;
import org.apache.hadoop.mapreduce.TaskType;
import org.apache.hadoop.mapreduce.security.token.delegation.DelegationTokenIdentifier;
import org.apache.hadoop.mapreduce.security.token.delegation.DelegationTokenSelector;
import org.apache.hadoop.mapreduce.v2.LogParams;
import org.apache.hadoop.security.Credentials;
import org.apache.hadoop.security.KerberosInfo;
import org.apache.hadoop.security.authorize.AccessControlList;
import org.apache.hadoop.security.token.Token;
import org.apache.hadoop.security.token.TokenInfo;

@KerberosInfo(serverPrincipal="mapreduce.jobtracker.kerberos.principal")
@TokenInfo(DelegationTokenSelector.class)
@InterfaceAudience.Private
@InterfaceStability.Stable
public abstract interface ClientProtocol
  extends VersionedProtocol
{
  public static final long versionID = 37L;
  
  public abstract JobID getNewJobID()
    throws IOException, InterruptedException;
  
  public abstract JobStatus submitJob(JobID paramJobID, String paramString, Credentials paramCredentials)
    throws IOException, InterruptedException;
  
  public abstract ClusterMetrics getClusterMetrics()
    throws IOException, InterruptedException;
  
  public abstract Cluster.JobTrackerStatus getJobTrackerStatus()
    throws IOException, InterruptedException;
  
  public abstract long getTaskTrackerExpiryInterval()
    throws IOException, InterruptedException;
  
  public abstract AccessControlList getQueueAdmins(String paramString)
    throws IOException;
  
  public abstract void killJob(JobID paramJobID)
    throws IOException, InterruptedException;
  
  public abstract void setJobPriority(JobID paramJobID, String paramString)
    throws IOException, InterruptedException;
  
  public abstract boolean killTask(TaskAttemptID paramTaskAttemptID, boolean paramBoolean)
    throws IOException, InterruptedException;
  
  public abstract JobStatus getJobStatus(JobID paramJobID)
    throws IOException, InterruptedException;
  
  public abstract Counters getJobCounters(JobID paramJobID)
    throws IOException, InterruptedException;
  
  public abstract TaskReport[] getTaskReports(JobID paramJobID, TaskType paramTaskType)
    throws IOException, InterruptedException;
  
  public abstract String getFilesystemName()
    throws IOException, InterruptedException;
  
  public abstract JobStatus[] getAllJobs()
    throws IOException, InterruptedException;
  
  public abstract TaskCompletionEvent[] getTaskCompletionEvents(JobID paramJobID, int paramInt1, int paramInt2)
    throws IOException, InterruptedException;
  
  public abstract String[] getTaskDiagnostics(TaskAttemptID paramTaskAttemptID)
    throws IOException, InterruptedException;
  
  public abstract TaskTrackerInfo[] getActiveTrackers()
    throws IOException, InterruptedException;
  
  public abstract TaskTrackerInfo[] getBlacklistedTrackers()
    throws IOException, InterruptedException;
  
  public abstract String getSystemDir()
    throws IOException, InterruptedException;
  
  public abstract String getStagingAreaDir()
    throws IOException, InterruptedException;
  
  public abstract String getJobHistoryDir()
    throws IOException, InterruptedException;
  
  public abstract QueueInfo[] getQueues()
    throws IOException, InterruptedException;
  
  public abstract QueueInfo getQueue(String paramString)
    throws IOException, InterruptedException;
  
  public abstract QueueAclsInfo[] getQueueAclsForCurrentUser()
    throws IOException, InterruptedException;
  
  public abstract QueueInfo[] getRootQueues()
    throws IOException, InterruptedException;
  
  public abstract QueueInfo[] getChildQueues(String paramString)
    throws IOException, InterruptedException;
  
  public abstract Token<DelegationTokenIdentifier> getDelegationToken(Text paramText)
    throws IOException, InterruptedException;
  
  public abstract long renewDelegationToken(Token<DelegationTokenIdentifier> paramToken)
    throws IOException, InterruptedException;
  
  public abstract void cancelDelegationToken(Token<DelegationTokenIdentifier> paramToken)
    throws IOException, InterruptedException;
  
  public abstract LogParams getLogFileParams(JobID paramJobID, TaskAttemptID paramTaskAttemptID)
    throws IOException, InterruptedException;
}


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.protocol.ClientProtocol
 * JD-Core Version:    0.7.0.1
 */