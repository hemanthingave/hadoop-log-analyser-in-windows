/*   1:    */ package org.apache.hadoop.mapreduce;
/*   2:    */ 
/*   3:    */ import java.io.DataInput;
/*   4:    */ import java.io.DataOutput;
/*   5:    */ import java.io.IOException;
/*   6:    */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*   7:    */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*   8:    */ 
/*   9:    */ @InterfaceAudience.Public
/*  10:    */ @InterfaceStability.Stable
/*  11:    */ public class TaskAttemptID
/*  12:    */   extends org.apache.hadoop.mapred.ID
/*  13:    */ {
/*  14:    */   protected static final String ATTEMPT = "attempt";
/*  15:    */   private TaskID taskId;
/*  16:    */   
/*  17:    */   public TaskAttemptID(TaskID taskId, int id)
/*  18:    */   {
/*  19: 60 */     super(id);
/*  20: 61 */     if (taskId == null) {
/*  21: 62 */       throw new IllegalArgumentException("taskId cannot be null");
/*  22:    */     }
/*  23: 64 */     this.taskId = taskId;
/*  24:    */   }
/*  25:    */   
/*  26:    */   public TaskAttemptID(String jtIdentifier, int jobId, TaskType type, int taskId, int id)
/*  27:    */   {
/*  28: 77 */     this(new TaskID(jtIdentifier, jobId, type, taskId), id);
/*  29:    */   }
/*  30:    */   
/*  31:    */   @Deprecated
/*  32:    */   public TaskAttemptID(String jtIdentifier, int jobId, boolean isMap, int taskId, int id)
/*  33:    */   {
/*  34: 91 */     this(new TaskID(jtIdentifier, jobId, isMap, taskId), id);
/*  35:    */   }
/*  36:    */   
/*  37:    */   public TaskAttemptID()
/*  38:    */   {
/*  39: 95 */     this.taskId = new TaskID();
/*  40:    */   }
/*  41:    */   
/*  42:    */   public JobID getJobID()
/*  43:    */   {
/*  44:100 */     return this.taskId.getJobID();
/*  45:    */   }
/*  46:    */   
/*  47:    */   public TaskID getTaskID()
/*  48:    */   {
/*  49:105 */     return this.taskId;
/*  50:    */   }
/*  51:    */   
/*  52:    */   @Deprecated
/*  53:    */   public boolean isMap()
/*  54:    */   {
/*  55:111 */     return this.taskId.isMap();
/*  56:    */   }
/*  57:    */   
/*  58:    */   public TaskType getTaskType()
/*  59:    */   {
/*  60:116 */     return this.taskId.getTaskType();
/*  61:    */   }
/*  62:    */   
/*  63:    */   public boolean equals(Object o)
/*  64:    */   {
/*  65:120 */     if (!super.equals(o)) {
/*  66:121 */       return false;
/*  67:    */     }
/*  68:123 */     TaskAttemptID that = (TaskAttemptID)o;
/*  69:124 */     return this.taskId.equals(that.taskId);
/*  70:    */   }
/*  71:    */   
/*  72:    */   protected StringBuilder appendTo(StringBuilder builder)
/*  73:    */   {
/*  74:133 */     return this.taskId.appendTo(builder).append('_').append(this.id);
/*  75:    */   }
/*  76:    */   
/*  77:    */   public void readFields(DataInput in)
/*  78:    */     throws IOException
/*  79:    */   {
/*  80:138 */     super.readFields(in);
/*  81:139 */     this.taskId.readFields(in);
/*  82:    */   }
/*  83:    */   
/*  84:    */   public void write(DataOutput out)
/*  85:    */     throws IOException
/*  86:    */   {
/*  87:144 */     super.write(out);
/*  88:145 */     this.taskId.write(out);
/*  89:    */   }
/*  90:    */   
/*  91:    */   public int hashCode()
/*  92:    */   {
/*  93:150 */     return this.taskId.hashCode() * 5 + this.id;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public int compareTo(ID o)
/*  97:    */   {
/*  98:156 */     TaskAttemptID that = (TaskAttemptID)o;
/*  99:157 */     int tipComp = this.taskId.compareTo(that.taskId);
/* 100:158 */     if (tipComp == 0) {
/* 101:159 */       return this.id - that.id;
/* 102:    */     }
/* 103:161 */     return tipComp;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public String toString()
/* 107:    */   {
/* 108:165 */     return appendTo(new StringBuilder("attempt")).toString();
/* 109:    */   }
/* 110:    */   
/* 111:    */   public static TaskAttemptID forName(String str)
/* 112:    */     throws IllegalArgumentException
/* 113:    */   {
/* 114:174 */     if (str == null) {
/* 115:175 */       return null;
/* 116:    */     }
/* 117:176 */     String exceptionMsg = null;
/* 118:    */     try
/* 119:    */     {
/* 120:178 */       String[] parts = str.split(Character.toString('_'));
/* 121:179 */       if ((parts.length == 6) && 
/* 122:180 */         (parts[0].equals("attempt")))
/* 123:    */       {
/* 124:181 */         String type = parts[3];
/* 125:182 */         TaskType t = TaskID.getTaskType(type.charAt(0));
/* 126:183 */         if (t != null) {
/* 127:184 */           return new org.apache.hadoop.mapred.TaskAttemptID(parts[1], Integer.parseInt(parts[2]), t, Integer.parseInt(parts[4]), Integer.parseInt(parts[5]));
/* 128:    */         }
/* 129:190 */         exceptionMsg = "Bad TaskType identifier. TaskAttemptId string : " + str + " is not properly formed.";
/* 130:    */       }
/* 131:    */     }
/* 132:    */     catch (Exception ex) {}
/* 133:197 */     if (exceptionMsg == null) {
/* 134:198 */       exceptionMsg = "TaskAttemptId string : " + str + " is not properly formed";
/* 135:    */     }
/* 136:201 */     throw new IllegalArgumentException(exceptionMsg);
/* 137:    */   }
/* 138:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.TaskAttemptID
 * JD-Core Version:    0.7.0.1
 */