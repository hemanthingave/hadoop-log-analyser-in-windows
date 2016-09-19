/*   1:    */ package org.apache.hadoop.mapreduce.task;
/*   2:    */ 
/*   3:    */ import org.apache.hadoop.classification.InterfaceAudience.Private;
/*   4:    */ import org.apache.hadoop.classification.InterfaceStability.Unstable;
/*   5:    */ import org.apache.hadoop.conf.Configuration;
/*   6:    */ import org.apache.hadoop.mapred.Task;
/*   7:    */ import org.apache.hadoop.mapreduce.Counter;
/*   8:    */ import org.apache.hadoop.mapreduce.Counters;
/*   9:    */ import org.apache.hadoop.mapreduce.StatusReporter;
/*  10:    */ import org.apache.hadoop.mapreduce.TaskAttemptContext;
/*  11:    */ import org.apache.hadoop.mapreduce.TaskAttemptID;
/*  12:    */ 
/*  13:    */ @InterfaceAudience.Private
/*  14:    */ @InterfaceStability.Unstable
/*  15:    */ public class TaskAttemptContextImpl
/*  16:    */   extends JobContextImpl
/*  17:    */   implements TaskAttemptContext
/*  18:    */ {
/*  19:    */   private final TaskAttemptID taskId;
/*  20: 39 */   private String status = "";
/*  21:    */   private StatusReporter reporter;
/*  22:    */   
/*  23:    */   public TaskAttemptContextImpl(Configuration conf, TaskAttemptID taskId)
/*  24:    */   {
/*  25: 44 */     this(conf, taskId, new DummyReporter());
/*  26:    */   }
/*  27:    */   
/*  28:    */   public TaskAttemptContextImpl(Configuration conf, TaskAttemptID taskId, StatusReporter reporter)
/*  29:    */   {
/*  30: 49 */     super(conf, taskId.getJobID());
/*  31: 50 */     this.taskId = taskId;
/*  32: 51 */     this.reporter = reporter;
/*  33:    */   }
/*  34:    */   
/*  35:    */   public TaskAttemptID getTaskAttemptID()
/*  36:    */   {
/*  37: 58 */     return this.taskId;
/*  38:    */   }
/*  39:    */   
/*  40:    */   public String getStatus()
/*  41:    */   {
/*  42: 66 */     return this.status;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public Counter getCounter(Enum<?> counterName)
/*  46:    */   {
/*  47: 71 */     return this.reporter.getCounter(counterName);
/*  48:    */   }
/*  49:    */   
/*  50:    */   public Counter getCounter(String groupName, String counterName)
/*  51:    */   {
/*  52: 76 */     return this.reporter.getCounter(groupName, counterName);
/*  53:    */   }
/*  54:    */   
/*  55:    */   public void progress()
/*  56:    */   {
/*  57: 84 */     this.reporter.progress();
/*  58:    */   }
/*  59:    */   
/*  60:    */   protected void setStatusString(String status)
/*  61:    */   {
/*  62: 88 */     this.status = status;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public void setStatus(String status)
/*  66:    */   {
/*  67: 96 */     String normalizedStatus = Task.normalizeStatus(status, this.conf);
/*  68: 97 */     setStatusString(normalizedStatus);
/*  69: 98 */     this.reporter.setStatus(normalizedStatus);
/*  70:    */   }
/*  71:    */   
/*  72:    */   public static class DummyReporter
/*  73:    */     extends StatusReporter
/*  74:    */   {
/*  75:    */     public void setStatus(String s) {}
/*  76:    */     
/*  77:    */     public void progress() {}
/*  78:    */     
/*  79:    */     public Counter getCounter(Enum<?> name)
/*  80:    */     {
/*  81:107 */       return new Counters().findCounter(name);
/*  82:    */     }
/*  83:    */     
/*  84:    */     public Counter getCounter(String group, String name)
/*  85:    */     {
/*  86:110 */       return new Counters().findCounter(group, name);
/*  87:    */     }
/*  88:    */     
/*  89:    */     public float getProgress()
/*  90:    */     {
/*  91:113 */       return 0.0F;
/*  92:    */     }
/*  93:    */   }
/*  94:    */   
/*  95:    */   public float getProgress()
/*  96:    */   {
/*  97:119 */     return this.reporter.getProgress();
/*  98:    */   }
/*  99:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.task.TaskAttemptContextImpl
 * JD-Core Version:    0.7.0.1
 */