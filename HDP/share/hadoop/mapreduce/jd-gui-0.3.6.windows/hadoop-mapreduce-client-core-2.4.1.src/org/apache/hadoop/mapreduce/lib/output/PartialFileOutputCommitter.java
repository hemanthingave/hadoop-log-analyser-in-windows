/*   1:    */ package org.apache.hadoop.mapreduce.lib.output;
/*   2:    */ 
/*   3:    */ import com.google.common.annotations.VisibleForTesting;
/*   4:    */ import java.io.IOException;
/*   5:    */ import org.apache.commons.logging.Log;
/*   6:    */ import org.apache.commons.logging.LogFactory;
/*   7:    */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*   8:    */ import org.apache.hadoop.classification.InterfaceStability.Evolving;
/*   9:    */ import org.apache.hadoop.conf.Configuration;
/*  10:    */ import org.apache.hadoop.fs.FileSystem;
/*  11:    */ import org.apache.hadoop.fs.Path;
/*  12:    */ import org.apache.hadoop.mapreduce.JobContext;
/*  13:    */ import org.apache.hadoop.mapreduce.TaskAttemptContext;
/*  14:    */ import org.apache.hadoop.mapreduce.TaskAttemptID;
/*  15:    */ import org.apache.hadoop.mapreduce.TaskID;
/*  16:    */ import org.apache.hadoop.mapreduce.task.annotation.Checkpointable;
/*  17:    */ 
/*  18:    */ @Checkpointable
/*  19:    */ @InterfaceAudience.Public
/*  20:    */ @InterfaceStability.Evolving
/*  21:    */ public class PartialFileOutputCommitter
/*  22:    */   extends FileOutputCommitter
/*  23:    */   implements PartialOutputCommitter
/*  24:    */ {
/*  25: 48 */   private static final Log LOG = LogFactory.getLog(PartialFileOutputCommitter.class);
/*  26:    */   
/*  27:    */   public PartialFileOutputCommitter(Path outputPath, TaskAttemptContext context)
/*  28:    */     throws IOException
/*  29:    */   {
/*  30: 54 */     super(outputPath, context);
/*  31:    */   }
/*  32:    */   
/*  33:    */   public PartialFileOutputCommitter(Path outputPath, JobContext context)
/*  34:    */     throws IOException
/*  35:    */   {
/*  36: 59 */     super(outputPath, context);
/*  37:    */   }
/*  38:    */   
/*  39:    */   public Path getCommittedTaskPath(int appAttemptId, TaskAttemptContext context)
/*  40:    */   {
/*  41: 64 */     return new Path(getJobAttemptPath(appAttemptId), String.valueOf(context.getTaskAttemptID()));
/*  42:    */   }
/*  43:    */   
/*  44:    */   @VisibleForTesting
/*  45:    */   FileSystem fsFor(Path p, Configuration conf)
/*  46:    */     throws IOException
/*  47:    */   {
/*  48: 70 */     return p.getFileSystem(conf);
/*  49:    */   }
/*  50:    */   
/*  51:    */   public void cleanUpPartialOutputForTask(TaskAttemptContext context)
/*  52:    */     throws IOException
/*  53:    */   {
/*  54: 82 */     if (!getClass().isAnnotationPresent(Checkpointable.class)) {
/*  55: 83 */       throw new IllegalStateException("Invoking cleanUpPartialOutputForTask() from non @Preemptable class");
/*  56:    */     }
/*  57: 86 */     FileSystem fs = fsFor(getTaskAttemptPath(context), context.getConfiguration());
/*  58:    */     
/*  59:    */ 
/*  60: 89 */     LOG.info("cleanUpPartialOutputForTask: removing everything belonging to " + context.getTaskAttemptID().getTaskID() + " in: " + getCommittedTaskPath(context).getParent());
/*  61:    */     
/*  62:    */ 
/*  63:    */ 
/*  64: 93 */     TaskAttemptID taid = context.getTaskAttemptID();
/*  65: 94 */     TaskID tid = taid.getTaskID();
/*  66: 95 */     Path pCommit = getCommittedTaskPath(context).getParent();
/*  67: 97 */     for (int i = 0; i < taid.getId(); i++)
/*  68:    */     {
/*  69: 98 */       TaskAttemptID oldId = new TaskAttemptID(tid, i);
/*  70: 99 */       Path pTask = new Path(pCommit, oldId.toString());
/*  71:100 */       if ((fs.exists(pTask)) && (!fs.delete(pTask, true))) {
/*  72:101 */         throw new IOException("Failed to delete " + pTask);
/*  73:    */       }
/*  74:    */     }
/*  75:    */   }
/*  76:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.lib.output.PartialFileOutputCommitter
 * JD-Core Version:    0.7.0.1
 */