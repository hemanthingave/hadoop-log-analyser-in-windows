/*   1:    */ package org.apache.hadoop.mapreduce.jobhistory;
/*   2:    */ 
/*   3:    */ import java.util.HashMap;
/*   4:    */ import java.util.Map;
/*   5:    */ import java.util.Map.Entry;
/*   6:    */ import org.apache.avro.util.Utf8;
/*   7:    */ import org.apache.hadoop.classification.InterfaceAudience.Private;
/*   8:    */ import org.apache.hadoop.classification.InterfaceStability.Unstable;
/*   9:    */ import org.apache.hadoop.mapreduce.JobACL;
/*  10:    */ import org.apache.hadoop.mapreduce.JobID;
/*  11:    */ import org.apache.hadoop.security.authorize.AccessControlList;
/*  12:    */ 
/*  13:    */ @InterfaceAudience.Private
/*  14:    */ @InterfaceStability.Unstable
/*  15:    */ public class JobSubmittedEvent
/*  16:    */   implements HistoryEvent
/*  17:    */ {
/*  18: 40 */   private JobSubmitted datum = new JobSubmitted();
/*  19:    */   
/*  20:    */   public JobSubmittedEvent(JobID id, String jobName, String userName, long submitTime, String jobConfPath, Map<JobACL, AccessControlList> jobACLs, String jobQueueName)
/*  21:    */   {
/*  22: 55 */     this(id, jobName, userName, submitTime, jobConfPath, jobACLs, jobQueueName, "", "", "", "");
/*  23:    */   }
/*  24:    */   
/*  25:    */   public JobSubmittedEvent(JobID id, String jobName, String userName, long submitTime, String jobConfPath, Map<JobACL, AccessControlList> jobACLs, String jobQueueName, String workflowId, String workflowName, String workflowNodeName, String workflowAdjacencies)
/*  26:    */   {
/*  27: 78 */     this(id, jobName, userName, submitTime, jobConfPath, jobACLs, jobQueueName, workflowId, workflowName, workflowNodeName, workflowAdjacencies, "");
/*  28:    */   }
/*  29:    */   
/*  30:    */   public JobSubmittedEvent(JobID id, String jobName, String userName, long submitTime, String jobConfPath, Map<JobACL, AccessControlList> jobACLs, String jobQueueName, String workflowId, String workflowName, String workflowNodeName, String workflowAdjacencies, String workflowTags)
/*  31:    */   {
/*  32:103 */     this.datum.jobid = new Utf8(id.toString());
/*  33:104 */     this.datum.jobName = new Utf8(jobName);
/*  34:105 */     this.datum.userName = new Utf8(userName);
/*  35:106 */     this.datum.submitTime = submitTime;
/*  36:107 */     this.datum.jobConfPath = new Utf8(jobConfPath);
/*  37:108 */     Map<CharSequence, CharSequence> jobAcls = new HashMap();
/*  38:109 */     for (Map.Entry<JobACL, AccessControlList> entry : jobACLs.entrySet()) {
/*  39:110 */       jobAcls.put(new Utf8(((JobACL)entry.getKey()).getAclName()), new Utf8(((AccessControlList)entry.getValue()).getAclString()));
/*  40:    */     }
/*  41:113 */     this.datum.acls = jobAcls;
/*  42:114 */     if (jobQueueName != null) {
/*  43:115 */       this.datum.jobQueueName = new Utf8(jobQueueName);
/*  44:    */     }
/*  45:117 */     if (workflowId != null) {
/*  46:118 */       this.datum.workflowId = new Utf8(workflowId);
/*  47:    */     }
/*  48:120 */     if (workflowName != null) {
/*  49:121 */       this.datum.workflowName = new Utf8(workflowName);
/*  50:    */     }
/*  51:123 */     if (workflowNodeName != null) {
/*  52:124 */       this.datum.workflowNodeName = new Utf8(workflowNodeName);
/*  53:    */     }
/*  54:126 */     if (workflowAdjacencies != null) {
/*  55:127 */       this.datum.workflowAdjacencies = new Utf8(workflowAdjacencies);
/*  56:    */     }
/*  57:129 */     if (workflowTags != null) {
/*  58:130 */       this.datum.workflowTags = new Utf8(workflowTags);
/*  59:    */     }
/*  60:    */   }
/*  61:    */   
/*  62:    */   JobSubmittedEvent() {}
/*  63:    */   
/*  64:    */   public Object getDatum()
/*  65:    */   {
/*  66:136 */     return this.datum;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public void setDatum(Object datum)
/*  70:    */   {
/*  71:138 */     this.datum = ((JobSubmitted)datum);
/*  72:    */   }
/*  73:    */   
/*  74:    */   public JobID getJobId()
/*  75:    */   {
/*  76:142 */     return JobID.forName(this.datum.jobid.toString());
/*  77:    */   }
/*  78:    */   
/*  79:    */   public String getJobName()
/*  80:    */   {
/*  81:144 */     return this.datum.jobName.toString();
/*  82:    */   }
/*  83:    */   
/*  84:    */   public String getJobQueueName()
/*  85:    */   {
/*  86:147 */     if (this.datum.jobQueueName != null) {
/*  87:148 */       return this.datum.jobQueueName.toString();
/*  88:    */     }
/*  89:150 */     return null;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public String getUserName()
/*  93:    */   {
/*  94:153 */     return this.datum.userName.toString();
/*  95:    */   }
/*  96:    */   
/*  97:    */   public long getSubmitTime()
/*  98:    */   {
/*  99:155 */     return this.datum.submitTime;
/* 100:    */   }
/* 101:    */   
/* 102:    */   public String getJobConfPath()
/* 103:    */   {
/* 104:157 */     return this.datum.jobConfPath.toString();
/* 105:    */   }
/* 106:    */   
/* 107:    */   public Map<JobACL, AccessControlList> getJobAcls()
/* 108:    */   {
/* 109:160 */     Map<JobACL, AccessControlList> jobAcls = new HashMap();
/* 110:162 */     for (JobACL jobACL : JobACL.values())
/* 111:    */     {
/* 112:163 */       Utf8 jobACLsUtf8 = new Utf8(jobACL.getAclName());
/* 113:164 */       if (this.datum.acls.containsKey(jobACLsUtf8)) {
/* 114:165 */         jobAcls.put(jobACL, new AccessControlList(((CharSequence)this.datum.acls.get(jobACLsUtf8)).toString()));
/* 115:    */       }
/* 116:    */     }
/* 117:169 */     return jobAcls;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public String getWorkflowId()
/* 121:    */   {
/* 122:173 */     if (this.datum.workflowId != null) {
/* 123:174 */       return this.datum.workflowId.toString();
/* 124:    */     }
/* 125:176 */     return null;
/* 126:    */   }
/* 127:    */   
/* 128:    */   public String getWorkflowName()
/* 129:    */   {
/* 130:180 */     if (this.datum.workflowName != null) {
/* 131:181 */       return this.datum.workflowName.toString();
/* 132:    */     }
/* 133:183 */     return null;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public String getWorkflowNodeName()
/* 137:    */   {
/* 138:187 */     if (this.datum.workflowNodeName != null) {
/* 139:188 */       return this.datum.workflowNodeName.toString();
/* 140:    */     }
/* 141:190 */     return null;
/* 142:    */   }
/* 143:    */   
/* 144:    */   public String getWorkflowAdjacencies()
/* 145:    */   {
/* 146:194 */     if (this.datum.workflowAdjacencies != null) {
/* 147:195 */       return this.datum.workflowAdjacencies.toString();
/* 148:    */     }
/* 149:197 */     return null;
/* 150:    */   }
/* 151:    */   
/* 152:    */   public String getWorkflowTags()
/* 153:    */   {
/* 154:201 */     if (this.datum.workflowTags != null) {
/* 155:202 */       return this.datum.workflowTags.toString();
/* 156:    */     }
/* 157:204 */     return null;
/* 158:    */   }
/* 159:    */   
/* 160:    */   public EventType getEventType()
/* 161:    */   {
/* 162:207 */     return EventType.JOB_SUBMITTED;
/* 163:    */   }
/* 164:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.jobhistory.JobSubmittedEvent
 * JD-Core Version:    0.7.0.1
 */