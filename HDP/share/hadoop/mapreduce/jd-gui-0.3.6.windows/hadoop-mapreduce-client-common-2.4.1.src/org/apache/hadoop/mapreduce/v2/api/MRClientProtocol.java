package org.apache.hadoop.mapreduce.v2.api;

import java.io.IOException;
import java.net.InetSocketAddress;
import org.apache.hadoop.mapreduce.v2.api.protocolrecords.CancelDelegationTokenRequest;
import org.apache.hadoop.mapreduce.v2.api.protocolrecords.CancelDelegationTokenResponse;
import org.apache.hadoop.mapreduce.v2.api.protocolrecords.FailTaskAttemptRequest;
import org.apache.hadoop.mapreduce.v2.api.protocolrecords.FailTaskAttemptResponse;
import org.apache.hadoop.mapreduce.v2.api.protocolrecords.GetCountersRequest;
import org.apache.hadoop.mapreduce.v2.api.protocolrecords.GetCountersResponse;
import org.apache.hadoop.mapreduce.v2.api.protocolrecords.GetDelegationTokenRequest;
import org.apache.hadoop.mapreduce.v2.api.protocolrecords.GetDelegationTokenResponse;
import org.apache.hadoop.mapreduce.v2.api.protocolrecords.GetDiagnosticsRequest;
import org.apache.hadoop.mapreduce.v2.api.protocolrecords.GetDiagnosticsResponse;
import org.apache.hadoop.mapreduce.v2.api.protocolrecords.GetJobReportRequest;
import org.apache.hadoop.mapreduce.v2.api.protocolrecords.GetJobReportResponse;
import org.apache.hadoop.mapreduce.v2.api.protocolrecords.GetTaskAttemptCompletionEventsRequest;
import org.apache.hadoop.mapreduce.v2.api.protocolrecords.GetTaskAttemptCompletionEventsResponse;
import org.apache.hadoop.mapreduce.v2.api.protocolrecords.GetTaskAttemptReportRequest;
import org.apache.hadoop.mapreduce.v2.api.protocolrecords.GetTaskAttemptReportResponse;
import org.apache.hadoop.mapreduce.v2.api.protocolrecords.GetTaskReportRequest;
import org.apache.hadoop.mapreduce.v2.api.protocolrecords.GetTaskReportResponse;
import org.apache.hadoop.mapreduce.v2.api.protocolrecords.GetTaskReportsRequest;
import org.apache.hadoop.mapreduce.v2.api.protocolrecords.GetTaskReportsResponse;
import org.apache.hadoop.mapreduce.v2.api.protocolrecords.KillJobRequest;
import org.apache.hadoop.mapreduce.v2.api.protocolrecords.KillJobResponse;
import org.apache.hadoop.mapreduce.v2.api.protocolrecords.KillTaskAttemptRequest;
import org.apache.hadoop.mapreduce.v2.api.protocolrecords.KillTaskAttemptResponse;
import org.apache.hadoop.mapreduce.v2.api.protocolrecords.KillTaskRequest;
import org.apache.hadoop.mapreduce.v2.api.protocolrecords.KillTaskResponse;
import org.apache.hadoop.mapreduce.v2.api.protocolrecords.RenewDelegationTokenRequest;
import org.apache.hadoop.mapreduce.v2.api.protocolrecords.RenewDelegationTokenResponse;

public abstract interface MRClientProtocol
{
  public abstract InetSocketAddress getConnectAddress();
  
  public abstract GetJobReportResponse getJobReport(GetJobReportRequest paramGetJobReportRequest)
    throws IOException;
  
  public abstract GetTaskReportResponse getTaskReport(GetTaskReportRequest paramGetTaskReportRequest)
    throws IOException;
  
  public abstract GetTaskAttemptReportResponse getTaskAttemptReport(GetTaskAttemptReportRequest paramGetTaskAttemptReportRequest)
    throws IOException;
  
  public abstract GetCountersResponse getCounters(GetCountersRequest paramGetCountersRequest)
    throws IOException;
  
  public abstract GetTaskAttemptCompletionEventsResponse getTaskAttemptCompletionEvents(GetTaskAttemptCompletionEventsRequest paramGetTaskAttemptCompletionEventsRequest)
    throws IOException;
  
  public abstract GetTaskReportsResponse getTaskReports(GetTaskReportsRequest paramGetTaskReportsRequest)
    throws IOException;
  
  public abstract GetDiagnosticsResponse getDiagnostics(GetDiagnosticsRequest paramGetDiagnosticsRequest)
    throws IOException;
  
  public abstract KillJobResponse killJob(KillJobRequest paramKillJobRequest)
    throws IOException;
  
  public abstract KillTaskResponse killTask(KillTaskRequest paramKillTaskRequest)
    throws IOException;
  
  public abstract KillTaskAttemptResponse killTaskAttempt(KillTaskAttemptRequest paramKillTaskAttemptRequest)
    throws IOException;
  
  public abstract FailTaskAttemptResponse failTaskAttempt(FailTaskAttemptRequest paramFailTaskAttemptRequest)
    throws IOException;
  
  public abstract GetDelegationTokenResponse getDelegationToken(GetDelegationTokenRequest paramGetDelegationTokenRequest)
    throws IOException;
  
  public abstract RenewDelegationTokenResponse renewDelegationToken(RenewDelegationTokenRequest paramRenewDelegationTokenRequest)
    throws IOException;
  
  public abstract CancelDelegationTokenResponse cancelDelegationToken(CancelDelegationTokenRequest paramCancelDelegationTokenRequest)
    throws IOException;
}


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-common-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.v2.api.MRClientProtocol
 * JD-Core Version:    0.7.0.1
 */