/*   1:    */ package org.apache.hadoop.mapred;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.List;
/*   5:    */ import java.util.Properties;
/*   6:    */ import org.apache.hadoop.classification.InterfaceAudience.Private;
/*   7:    */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*   8:    */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*   9:    */ import org.apache.hadoop.mapreduce.JobStatus;
/*  10:    */ import org.apache.hadoop.mapreduce.QueueInfo;
/*  11:    */ import org.apache.hadoop.mapreduce.QueueState;
/*  12:    */ 
/*  13:    */ @InterfaceAudience.Public
/*  14:    */ @InterfaceStability.Stable
/*  15:    */ public class JobQueueInfo
/*  16:    */   extends QueueInfo
/*  17:    */ {
/*  18:    */   public JobQueueInfo() {}
/*  19:    */   
/*  20:    */   public JobQueueInfo(String queueName, String schedulingInfo)
/*  21:    */   {
/*  22: 54 */     super(queueName, schedulingInfo);
/*  23:    */   }
/*  24:    */   
/*  25:    */   JobQueueInfo(QueueInfo queue)
/*  26:    */   {
/*  27: 58 */     this(queue.getQueueName(), queue.getSchedulingInfo());
/*  28: 59 */     setQueueState(queue.getState().getStateName());
/*  29: 60 */     setQueueChildren(queue.getQueueChildren());
/*  30: 61 */     setProperties(queue.getProperties());
/*  31: 62 */     setJobStatuses(queue.getJobStatuses());
/*  32:    */   }
/*  33:    */   
/*  34:    */   @InterfaceAudience.Private
/*  35:    */   public void setQueueName(String queueName)
/*  36:    */   {
/*  37: 72 */     super.setQueueName(queueName);
/*  38:    */   }
/*  39:    */   
/*  40:    */   @InterfaceAudience.Private
/*  41:    */   public void setSchedulingInfo(String schedulingInfo)
/*  42:    */   {
/*  43: 82 */     super.setSchedulingInfo(schedulingInfo);
/*  44:    */   }
/*  45:    */   
/*  46:    */   @InterfaceAudience.Private
/*  47:    */   public void setQueueState(String state)
/*  48:    */   {
/*  49: 91 */     super.setState(QueueState.getState(state));
/*  50:    */   }
/*  51:    */   
/*  52:    */   @Deprecated
/*  53:    */   public String getQueueState()
/*  54:    */   {
/*  55: 99 */     return super.getState().toString();
/*  56:    */   }
/*  57:    */   
/*  58:    */   @InterfaceAudience.Private
/*  59:    */   public void setChildren(List<JobQueueInfo> children)
/*  60:    */   {
/*  61:104 */     List<QueueInfo> list = new ArrayList();
/*  62:105 */     for (JobQueueInfo q : children) {
/*  63:106 */       list.add(q);
/*  64:    */     }
/*  65:108 */     super.setQueueChildren(list);
/*  66:    */   }
/*  67:    */   
/*  68:    */   public List<JobQueueInfo> getChildren()
/*  69:    */   {
/*  70:112 */     List<JobQueueInfo> list = new ArrayList();
/*  71:113 */     for (QueueInfo q : super.getQueueChildren()) {
/*  72:114 */       list.add((JobQueueInfo)q);
/*  73:    */     }
/*  74:116 */     return list;
/*  75:    */   }
/*  76:    */   
/*  77:    */   @InterfaceAudience.Private
/*  78:    */   public void setProperties(Properties props)
/*  79:    */   {
/*  80:121 */     super.setProperties(props);
/*  81:    */   }
/*  82:    */   
/*  83:    */   void addChild(JobQueueInfo child)
/*  84:    */   {
/*  85:134 */     List<JobQueueInfo> children = getChildren();
/*  86:135 */     children.add(child);
/*  87:136 */     setChildren(children);
/*  88:    */   }
/*  89:    */   
/*  90:    */   void removeChild(JobQueueInfo child)
/*  91:    */   {
/*  92:148 */     List<JobQueueInfo> children = getChildren();
/*  93:149 */     children.remove(child);
/*  94:150 */     setChildren(children);
/*  95:    */   }
/*  96:    */   
/*  97:    */   @InterfaceAudience.Private
/*  98:    */   public void setJobStatuses(JobStatus[] stats)
/*  99:    */   {
/* 100:155 */     super.setJobStatuses(stats);
/* 101:    */   }
/* 102:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.JobQueueInfo
 * JD-Core Version:    0.7.0.1
 */