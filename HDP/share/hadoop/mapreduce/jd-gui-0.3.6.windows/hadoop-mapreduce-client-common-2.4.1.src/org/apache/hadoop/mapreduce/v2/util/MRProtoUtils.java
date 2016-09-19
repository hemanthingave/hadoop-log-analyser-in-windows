/*  1:   */ package org.apache.hadoop.mapreduce.v2.util;
/*  2:   */ 
/*  3:   */ import org.apache.hadoop.mapreduce.v2.api.records.JobState;
/*  4:   */ import org.apache.hadoop.mapreduce.v2.api.records.Phase;
/*  5:   */ import org.apache.hadoop.mapreduce.v2.api.records.TaskAttemptCompletionEventStatus;
/*  6:   */ import org.apache.hadoop.mapreduce.v2.api.records.TaskAttemptState;
/*  7:   */ import org.apache.hadoop.mapreduce.v2.api.records.TaskState;
/*  8:   */ import org.apache.hadoop.mapreduce.v2.api.records.TaskType;
/*  9:   */ import org.apache.hadoop.mapreduce.v2.proto.MRProtos.JobStateProto;
/* 10:   */ import org.apache.hadoop.mapreduce.v2.proto.MRProtos.PhaseProto;
/* 11:   */ import org.apache.hadoop.mapreduce.v2.proto.MRProtos.TaskAttemptCompletionEventStatusProto;
/* 12:   */ import org.apache.hadoop.mapreduce.v2.proto.MRProtos.TaskAttemptStateProto;
/* 13:   */ import org.apache.hadoop.mapreduce.v2.proto.MRProtos.TaskStateProto;
/* 14:   */ import org.apache.hadoop.mapreduce.v2.proto.MRProtos.TaskTypeProto;
/* 15:   */ 
/* 16:   */ public class MRProtoUtils
/* 17:   */ {
/* 18:39 */   private static String JOB_STATE_PREFIX = "J_";
/* 19:   */   
/* 20:   */   public static MRProtos.JobStateProto convertToProtoFormat(JobState e)
/* 21:   */   {
/* 22:41 */     return MRProtos.JobStateProto.valueOf(JOB_STATE_PREFIX + e.name());
/* 23:   */   }
/* 24:   */   
/* 25:   */   public static JobState convertFromProtoFormat(MRProtos.JobStateProto e)
/* 26:   */   {
/* 27:44 */     return JobState.valueOf(e.name().replace(JOB_STATE_PREFIX, ""));
/* 28:   */   }
/* 29:   */   
/* 30:50 */   private static String PHASE_PREFIX = "P_";
/* 31:   */   
/* 32:   */   public static MRProtos.PhaseProto convertToProtoFormat(Phase e)
/* 33:   */   {
/* 34:52 */     return MRProtos.PhaseProto.valueOf(PHASE_PREFIX + e.name());
/* 35:   */   }
/* 36:   */   
/* 37:   */   public static Phase convertFromProtoFormat(MRProtos.PhaseProto e)
/* 38:   */   {
/* 39:55 */     return Phase.valueOf(e.name().replace(PHASE_PREFIX, ""));
/* 40:   */   }
/* 41:   */   
/* 42:61 */   private static String TACE_PREFIX = "TACE_";
/* 43:   */   
/* 44:   */   public static MRProtos.TaskAttemptCompletionEventStatusProto convertToProtoFormat(TaskAttemptCompletionEventStatus e)
/* 45:   */   {
/* 46:63 */     return MRProtos.TaskAttemptCompletionEventStatusProto.valueOf(TACE_PREFIX + e.name());
/* 47:   */   }
/* 48:   */   
/* 49:   */   public static TaskAttemptCompletionEventStatus convertFromProtoFormat(MRProtos.TaskAttemptCompletionEventStatusProto e)
/* 50:   */   {
/* 51:66 */     return TaskAttemptCompletionEventStatus.valueOf(e.name().replace(TACE_PREFIX, ""));
/* 52:   */   }
/* 53:   */   
/* 54:72 */   private static String TASK_ATTEMPT_STATE_PREFIX = "TA_";
/* 55:   */   
/* 56:   */   public static MRProtos.TaskAttemptStateProto convertToProtoFormat(TaskAttemptState e)
/* 57:   */   {
/* 58:74 */     return MRProtos.TaskAttemptStateProto.valueOf(TASK_ATTEMPT_STATE_PREFIX + e.name());
/* 59:   */   }
/* 60:   */   
/* 61:   */   public static TaskAttemptState convertFromProtoFormat(MRProtos.TaskAttemptStateProto e)
/* 62:   */   {
/* 63:77 */     return TaskAttemptState.valueOf(e.name().replace(TASK_ATTEMPT_STATE_PREFIX, ""));
/* 64:   */   }
/* 65:   */   
/* 66:83 */   private static String TASK_STATE_PREFIX = "TS_";
/* 67:   */   
/* 68:   */   public static MRProtos.TaskStateProto convertToProtoFormat(TaskState e)
/* 69:   */   {
/* 70:85 */     return MRProtos.TaskStateProto.valueOf(TASK_STATE_PREFIX + e.name());
/* 71:   */   }
/* 72:   */   
/* 73:   */   public static TaskState convertFromProtoFormat(MRProtos.TaskStateProto e)
/* 74:   */   {
/* 75:88 */     return TaskState.valueOf(e.name().replace(TASK_STATE_PREFIX, ""));
/* 76:   */   }
/* 77:   */   
/* 78:   */   public static MRProtos.TaskTypeProto convertToProtoFormat(TaskType e)
/* 79:   */   {
/* 80:95 */     return MRProtos.TaskTypeProto.valueOf(e.name());
/* 81:   */   }
/* 82:   */   
/* 83:   */   public static TaskType convertFromProtoFormat(MRProtos.TaskTypeProto e)
/* 84:   */   {
/* 85:98 */     return TaskType.valueOf(e.name());
/* 86:   */   }
/* 87:   */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-common-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.v2.util.MRProtoUtils
 * JD-Core Version:    0.7.0.1
 */