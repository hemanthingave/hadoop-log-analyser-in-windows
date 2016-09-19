/*  1:   */ package org.apache.hadoop.mapreduce.jobhistory;
/*  2:   */ 
/*  3:   */ import org.apache.avro.Schema;
/*  4:   */ import org.apache.avro.Schema.Parser;
/*  5:   */ import org.apache.avro.specific.AvroGenerated;
/*  6:   */ 
/*  7:   */ @AvroGenerated
/*  8:   */ public enum EventType
/*  9:   */ {
/* 10:10 */   JOB_SUBMITTED,  JOB_INITED,  JOB_FINISHED,  JOB_PRIORITY_CHANGED,  JOB_STATUS_CHANGED,  JOB_QUEUE_CHANGED,  JOB_FAILED,  JOB_KILLED,  JOB_ERROR,  JOB_INFO_CHANGED,  TASK_STARTED,  TASK_FINISHED,  TASK_FAILED,  TASK_UPDATED,  NORMALIZED_RESOURCE,  MAP_ATTEMPT_STARTED,  MAP_ATTEMPT_FINISHED,  MAP_ATTEMPT_FAILED,  MAP_ATTEMPT_KILLED,  REDUCE_ATTEMPT_STARTED,  REDUCE_ATTEMPT_FINISHED,  REDUCE_ATTEMPT_FAILED,  REDUCE_ATTEMPT_KILLED,  SETUP_ATTEMPT_STARTED,  SETUP_ATTEMPT_FINISHED,  SETUP_ATTEMPT_FAILED,  SETUP_ATTEMPT_KILLED,  CLEANUP_ATTEMPT_STARTED,  CLEANUP_ATTEMPT_FINISHED,  CLEANUP_ATTEMPT_FAILED,  CLEANUP_ATTEMPT_KILLED,  AM_STARTED;
/* 11:   */   
/* 12:11 */   public static final Schema SCHEMA$ = new Schema.Parser().parse("{\"type\":\"enum\",\"name\":\"EventType\",\"namespace\":\"org.apache.hadoop.mapreduce.jobhistory\",\"symbols\":[\"JOB_SUBMITTED\",\"JOB_INITED\",\"JOB_FINISHED\",\"JOB_PRIORITY_CHANGED\",\"JOB_STATUS_CHANGED\",\"JOB_QUEUE_CHANGED\",\"JOB_FAILED\",\"JOB_KILLED\",\"JOB_ERROR\",\"JOB_INFO_CHANGED\",\"TASK_STARTED\",\"TASK_FINISHED\",\"TASK_FAILED\",\"TASK_UPDATED\",\"NORMALIZED_RESOURCE\",\"MAP_ATTEMPT_STARTED\",\"MAP_ATTEMPT_FINISHED\",\"MAP_ATTEMPT_FAILED\",\"MAP_ATTEMPT_KILLED\",\"REDUCE_ATTEMPT_STARTED\",\"REDUCE_ATTEMPT_FINISHED\",\"REDUCE_ATTEMPT_FAILED\",\"REDUCE_ATTEMPT_KILLED\",\"SETUP_ATTEMPT_STARTED\",\"SETUP_ATTEMPT_FINISHED\",\"SETUP_ATTEMPT_FAILED\",\"SETUP_ATTEMPT_KILLED\",\"CLEANUP_ATTEMPT_STARTED\",\"CLEANUP_ATTEMPT_FINISHED\",\"CLEANUP_ATTEMPT_FAILED\",\"CLEANUP_ATTEMPT_KILLED\",\"AM_STARTED\"]}");
/* 13:   */   
/* 14:   */   private EventType() {}
/* 15:   */   
/* 16:   */   public static Schema getClassSchema()
/* 17:   */   {
/* 18:12 */     return SCHEMA$;
/* 19:   */   }
/* 20:   */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.jobhistory.EventType
 * JD-Core Version:    0.7.0.1
 */