/*  1:   */ package org.apache.hadoop.mapreduce.task;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import org.apache.hadoop.classification.InterfaceAudience.Private;
/*  5:   */ import org.apache.hadoop.classification.InterfaceStability.Unstable;
/*  6:   */ import org.apache.hadoop.conf.Configuration;
/*  7:   */ import org.apache.hadoop.mapreduce.OutputCommitter;
/*  8:   */ import org.apache.hadoop.mapreduce.RecordWriter;
/*  9:   */ import org.apache.hadoop.mapreduce.StatusReporter;
/* 10:   */ import org.apache.hadoop.mapreduce.TaskAttemptID;
/* 11:   */ import org.apache.hadoop.mapreduce.TaskInputOutputContext;
/* 12:   */ 
/* 13:   */ @InterfaceAudience.Private
/* 14:   */ @InterfaceStability.Unstable
/* 15:   */ public abstract class TaskInputOutputContextImpl<KEYIN, VALUEIN, KEYOUT, VALUEOUT>
/* 16:   */   extends TaskAttemptContextImpl
/* 17:   */   implements TaskInputOutputContext<KEYIN, VALUEIN, KEYOUT, VALUEOUT>
/* 18:   */ {
/* 19:   */   private RecordWriter<KEYOUT, VALUEOUT> output;
/* 20:   */   private OutputCommitter committer;
/* 21:   */   
/* 22:   */   public TaskInputOutputContextImpl(Configuration conf, TaskAttemptID taskid, RecordWriter<KEYOUT, VALUEOUT> output, OutputCommitter committer, StatusReporter reporter)
/* 23:   */   {
/* 24:54 */     super(conf, taskid, reporter);
/* 25:55 */     this.output = output;
/* 26:56 */     this.committer = committer;
/* 27:   */   }
/* 28:   */   
/* 29:   */   public abstract boolean nextKeyValue()
/* 30:   */     throws IOException, InterruptedException;
/* 31:   */   
/* 32:   */   public abstract KEYIN getCurrentKey()
/* 33:   */     throws IOException, InterruptedException;
/* 34:   */   
/* 35:   */   public abstract VALUEIN getCurrentValue()
/* 36:   */     throws IOException, InterruptedException;
/* 37:   */   
/* 38:   */   public void write(KEYOUT key, VALUEOUT value)
/* 39:   */     throws IOException, InterruptedException
/* 40:   */   {
/* 41:89 */     this.output.write(key, value);
/* 42:   */   }
/* 43:   */   
/* 44:   */   public OutputCommitter getOutputCommitter()
/* 45:   */   {
/* 46:93 */     return this.committer;
/* 47:   */   }
/* 48:   */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.task.TaskInputOutputContextImpl
 * JD-Core Version:    0.7.0.1
 */