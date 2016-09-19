/*   1:    */ package org.apache.hadoop.mapred.jobcontrol;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.Collection;
/*   5:    */ import java.util.List;
/*   6:    */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*   7:    */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*   8:    */ import org.apache.hadoop.mapreduce.lib.jobcontrol.ControlledJob;
/*   9:    */ import org.apache.hadoop.mapreduce.lib.jobcontrol.JobControl.ThreadState;
/*  10:    */ 
/*  11:    */ @InterfaceAudience.Public
/*  12:    */ @InterfaceStability.Stable
/*  13:    */ public class JobControl
/*  14:    */   extends org.apache.hadoop.mapreduce.lib.jobcontrol.JobControl
/*  15:    */ {
/*  16:    */   public JobControl(String groupName)
/*  17:    */   {
/*  18: 39 */     super(groupName);
/*  19:    */   }
/*  20:    */   
/*  21:    */   static ArrayList<Job> castToJobList(List<ControlledJob> cjobs)
/*  22:    */   {
/*  23: 43 */     ArrayList<Job> ret = new ArrayList();
/*  24: 44 */     for (ControlledJob job : cjobs) {
/*  25: 45 */       ret.add((Job)job);
/*  26:    */     }
/*  27: 47 */     return ret;
/*  28:    */   }
/*  29:    */   
/*  30:    */   public ArrayList<Job> getWaitingJobs()
/*  31:    */   {
/*  32: 54 */     return castToJobList(super.getWaitingJobList());
/*  33:    */   }
/*  34:    */   
/*  35:    */   public ArrayList<Job> getRunningJobs()
/*  36:    */   {
/*  37: 61 */     return castToJobList(super.getRunningJobList());
/*  38:    */   }
/*  39:    */   
/*  40:    */   public ArrayList<Job> getReadyJobs()
/*  41:    */   {
/*  42: 68 */     return castToJobList(super.getReadyJobsList());
/*  43:    */   }
/*  44:    */   
/*  45:    */   public ArrayList<Job> getSuccessfulJobs()
/*  46:    */   {
/*  47: 75 */     return castToJobList(super.getSuccessfulJobList());
/*  48:    */   }
/*  49:    */   
/*  50:    */   public ArrayList<Job> getFailedJobs()
/*  51:    */   {
/*  52: 79 */     return castToJobList(super.getFailedJobList());
/*  53:    */   }
/*  54:    */   
/*  55:    */   public void addJobs(Collection<Job> jobs)
/*  56:    */   {
/*  57: 88 */     for (Job job : jobs) {
/*  58: 89 */       addJob(job);
/*  59:    */     }
/*  60:    */   }
/*  61:    */   
/*  62:    */   public int getState()
/*  63:    */   {
/*  64: 97 */     JobControl.ThreadState state = super.getThreadState();
/*  65: 98 */     if (state == JobControl.ThreadState.RUNNING) {
/*  66: 99 */       return 0;
/*  67:    */     }
/*  68:101 */     if (state == JobControl.ThreadState.SUSPENDED) {
/*  69:102 */       return 1;
/*  70:    */     }
/*  71:104 */     if (state == JobControl.ThreadState.STOPPED) {
/*  72:105 */       return 2;
/*  73:    */     }
/*  74:107 */     if (state == JobControl.ThreadState.STOPPING) {
/*  75:108 */       return 3;
/*  76:    */     }
/*  77:110 */     if (state == JobControl.ThreadState.READY) {
/*  78:111 */       return 4;
/*  79:    */     }
/*  80:113 */     return -1;
/*  81:    */   }
/*  82:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.jobcontrol.JobControl
 * JD-Core Version:    0.7.0.1
 */