/*  1:   */ package org.apache.hadoop.mapreduce.v2.util;
/*  2:   */ 
/*  3:   */ import java.util.List;
/*  4:   */ import org.apache.hadoop.mapreduce.v2.api.records.AMInfo;
/*  5:   */ import org.apache.hadoop.mapreduce.v2.api.records.JobId;
/*  6:   */ import org.apache.hadoop.mapreduce.v2.api.records.JobReport;
/*  7:   */ import org.apache.hadoop.mapreduce.v2.api.records.JobState;
/*  8:   */ import org.apache.hadoop.mapreduce.v2.api.records.TaskAttemptId;
/*  9:   */ import org.apache.hadoop.mapreduce.v2.api.records.TaskId;
/* 10:   */ import org.apache.hadoop.mapreduce.v2.api.records.TaskType;
/* 11:   */ import org.apache.hadoop.yarn.api.records.ApplicationAttemptId;
/* 12:   */ import org.apache.hadoop.yarn.api.records.ApplicationId;
/* 13:   */ import org.apache.hadoop.yarn.api.records.ContainerId;
/* 14:   */ import org.apache.hadoop.yarn.util.Records;
/* 15:   */ 
/* 16:   */ public class MRBuilderUtils
/* 17:   */ {
/* 18:   */   public static JobId newJobId(ApplicationId appId, int id)
/* 19:   */   {
/* 20:38 */     JobId jobId = (JobId)Records.newRecord(JobId.class);
/* 21:39 */     jobId.setAppId(appId);
/* 22:40 */     jobId.setId(id);
/* 23:41 */     return jobId;
/* 24:   */   }
/* 25:   */   
/* 26:   */   public static JobId newJobId(long clusterTs, int appIdInt, int id)
/* 27:   */   {
/* 28:45 */     ApplicationId appId = ApplicationId.newInstance(clusterTs, appIdInt);
/* 29:46 */     return newJobId(appId, id);
/* 30:   */   }
/* 31:   */   
/* 32:   */   public static TaskId newTaskId(JobId jobId, int id, TaskType taskType)
/* 33:   */   {
/* 34:50 */     TaskId taskId = (TaskId)Records.newRecord(TaskId.class);
/* 35:51 */     taskId.setJobId(jobId);
/* 36:52 */     taskId.setId(id);
/* 37:53 */     taskId.setTaskType(taskType);
/* 38:54 */     return taskId;
/* 39:   */   }
/* 40:   */   
/* 41:   */   public static TaskAttemptId newTaskAttemptId(TaskId taskId, int attemptId)
/* 42:   */   {
/* 43:58 */     TaskAttemptId taskAttemptId = (TaskAttemptId)Records.newRecord(TaskAttemptId.class);
/* 44:   */     
/* 45:60 */     taskAttemptId.setTaskId(taskId);
/* 46:61 */     taskAttemptId.setId(attemptId);
/* 47:62 */     return taskAttemptId;
/* 48:   */   }
/* 49:   */   
/* 50:   */   public static JobReport newJobReport(JobId jobId, String jobName, String userName, JobState state, long submitTime, long startTime, long finishTime, float setupProgress, float mapProgress, float reduceProgress, float cleanupProgress, String jobFile, List<AMInfo> amInfos, boolean isUber, String diagnostics)
/* 51:   */   {
/* 52:70 */     JobReport report = (JobReport)Records.newRecord(JobReport.class);
/* 53:71 */     report.setJobId(jobId);
/* 54:72 */     report.setJobName(jobName);
/* 55:73 */     report.setUser(userName);
/* 56:74 */     report.setJobState(state);
/* 57:75 */     report.setSubmitTime(submitTime);
/* 58:76 */     report.setStartTime(startTime);
/* 59:77 */     report.setFinishTime(finishTime);
/* 60:78 */     report.setSetupProgress(setupProgress);
/* 61:79 */     report.setCleanupProgress(cleanupProgress);
/* 62:80 */     report.setMapProgress(mapProgress);
/* 63:81 */     report.setReduceProgress(reduceProgress);
/* 64:82 */     report.setJobFile(jobFile);
/* 65:83 */     report.setAMInfos(amInfos);
/* 66:84 */     report.setIsUber(isUber);
/* 67:85 */     report.setDiagnostics(diagnostics);
/* 68:86 */     return report;
/* 69:   */   }
/* 70:   */   
/* 71:   */   public static AMInfo newAMInfo(ApplicationAttemptId appAttemptId, long startTime, ContainerId containerId, String nmHost, int nmPort, int nmHttpPort)
/* 72:   */   {
/* 73:92 */     AMInfo amInfo = (AMInfo)Records.newRecord(AMInfo.class);
/* 74:93 */     amInfo.setAppAttemptId(appAttemptId);
/* 75:94 */     amInfo.setStartTime(startTime);
/* 76:95 */     amInfo.setContainerId(containerId);
/* 77:96 */     amInfo.setNodeManagerHost(nmHost);
/* 78:97 */     amInfo.setNodeManagerPort(nmPort);
/* 79:98 */     amInfo.setNodeManagerHttpPort(nmHttpPort);
/* 80:99 */     return amInfo;
/* 81:   */   }
/* 82:   */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-common-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.v2.util.MRBuilderUtils
 * JD-Core Version:    0.7.0.1
 */