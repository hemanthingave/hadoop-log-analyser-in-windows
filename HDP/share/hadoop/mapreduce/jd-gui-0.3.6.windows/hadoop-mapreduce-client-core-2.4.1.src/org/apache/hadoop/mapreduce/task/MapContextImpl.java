/*  1:   */ package org.apache.hadoop.mapreduce.task;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import org.apache.hadoop.classification.InterfaceAudience.Private;
/*  5:   */ import org.apache.hadoop.classification.InterfaceStability.Unstable;
/*  6:   */ import org.apache.hadoop.conf.Configuration;
/*  7:   */ import org.apache.hadoop.mapreduce.InputSplit;
/*  8:   */ import org.apache.hadoop.mapreduce.MapContext;
/*  9:   */ import org.apache.hadoop.mapreduce.OutputCommitter;
/* 10:   */ import org.apache.hadoop.mapreduce.RecordReader;
/* 11:   */ import org.apache.hadoop.mapreduce.RecordWriter;
/* 12:   */ import org.apache.hadoop.mapreduce.StatusReporter;
/* 13:   */ import org.apache.hadoop.mapreduce.TaskAttemptID;
/* 14:   */ 
/* 15:   */ @InterfaceAudience.Private
/* 16:   */ @InterfaceStability.Unstable
/* 17:   */ public class MapContextImpl<KEYIN, VALUEIN, KEYOUT, VALUEOUT>
/* 18:   */   extends TaskInputOutputContextImpl<KEYIN, VALUEIN, KEYOUT, VALUEOUT>
/* 19:   */   implements MapContext<KEYIN, VALUEIN, KEYOUT, VALUEOUT>
/* 20:   */ {
/* 21:   */   private RecordReader<KEYIN, VALUEIN> reader;
/* 22:   */   private InputSplit split;
/* 23:   */   
/* 24:   */   public MapContextImpl(Configuration conf, TaskAttemptID taskid, RecordReader<KEYIN, VALUEIN> reader, RecordWriter<KEYOUT, VALUEOUT> writer, OutputCommitter committer, StatusReporter reporter, InputSplit split)
/* 25:   */   {
/* 26:56 */     super(conf, taskid, writer, committer, reporter);
/* 27:57 */     this.reader = reader;
/* 28:58 */     this.split = split;
/* 29:   */   }
/* 30:   */   
/* 31:   */   public InputSplit getInputSplit()
/* 32:   */   {
/* 33:65 */     return this.split;
/* 34:   */   }
/* 35:   */   
/* 36:   */   public KEYIN getCurrentKey()
/* 37:   */     throws IOException, InterruptedException
/* 38:   */   {
/* 39:70 */     return this.reader.getCurrentKey();
/* 40:   */   }
/* 41:   */   
/* 42:   */   public VALUEIN getCurrentValue()
/* 43:   */     throws IOException, InterruptedException
/* 44:   */   {
/* 45:75 */     return this.reader.getCurrentValue();
/* 46:   */   }
/* 47:   */   
/* 48:   */   public boolean nextKeyValue()
/* 49:   */     throws IOException, InterruptedException
/* 50:   */   {
/* 51:80 */     return this.reader.nextKeyValue();
/* 52:   */   }
/* 53:   */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.task.MapContextImpl
 * JD-Core Version:    0.7.0.1
 */