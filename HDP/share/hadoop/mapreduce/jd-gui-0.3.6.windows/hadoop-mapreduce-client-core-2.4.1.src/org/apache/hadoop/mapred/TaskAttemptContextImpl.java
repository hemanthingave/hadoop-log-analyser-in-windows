/*  1:   */ package org.apache.hadoop.mapred;
/*  2:   */ 
/*  3:   */ import org.apache.hadoop.classification.InterfaceAudience.Private;
/*  4:   */ import org.apache.hadoop.classification.InterfaceStability.Unstable;
/*  5:   */ import org.apache.hadoop.mapreduce.Counter;
/*  6:   */ import org.apache.hadoop.util.Progressable;
/*  7:   */ 
/*  8:   */ @InterfaceAudience.Private
/*  9:   */ @InterfaceStability.Unstable
/* 10:   */ public class TaskAttemptContextImpl
/* 11:   */   extends org.apache.hadoop.mapreduce.task.TaskAttemptContextImpl
/* 12:   */   implements TaskAttemptContext
/* 13:   */ {
/* 14:   */   private Reporter reporter;
/* 15:   */   
/* 16:   */   public TaskAttemptContextImpl(JobConf conf, TaskAttemptID taskid)
/* 17:   */   {
/* 18:33 */     this(conf, taskid, Reporter.NULL);
/* 19:   */   }
/* 20:   */   
/* 21:   */   TaskAttemptContextImpl(JobConf conf, TaskAttemptID taskid, Reporter reporter)
/* 22:   */   {
/* 23:38 */     super(conf, taskid);
/* 24:39 */     this.reporter = reporter;
/* 25:   */   }
/* 26:   */   
/* 27:   */   public TaskAttemptID getTaskAttemptID()
/* 28:   */   {
/* 29:48 */     return (TaskAttemptID)super.getTaskAttemptID();
/* 30:   */   }
/* 31:   */   
/* 32:   */   public Progressable getProgressible()
/* 33:   */   {
/* 34:52 */     return this.reporter;
/* 35:   */   }
/* 36:   */   
/* 37:   */   public JobConf getJobConf()
/* 38:   */   {
/* 39:56 */     return (JobConf)getConfiguration();
/* 40:   */   }
/* 41:   */   
/* 42:   */   public float getProgress()
/* 43:   */   {
/* 44:61 */     return this.reporter.getProgress();
/* 45:   */   }
/* 46:   */   
/* 47:   */   public Counter getCounter(Enum<?> counterName)
/* 48:   */   {
/* 49:66 */     return this.reporter.getCounter(counterName);
/* 50:   */   }
/* 51:   */   
/* 52:   */   public Counter getCounter(String groupName, String counterName)
/* 53:   */   {
/* 54:71 */     return this.reporter.getCounter(groupName, counterName);
/* 55:   */   }
/* 56:   */   
/* 57:   */   public void progress()
/* 58:   */   {
/* 59:79 */     this.reporter.progress();
/* 60:   */   }
/* 61:   */   
/* 62:   */   public void setStatus(String status)
/* 63:   */   {
/* 64:87 */     setStatusString(status);
/* 65:88 */     this.reporter.setStatus(status);
/* 66:   */   }
/* 67:   */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.TaskAttemptContextImpl
 * JD-Core Version:    0.7.0.1
 */