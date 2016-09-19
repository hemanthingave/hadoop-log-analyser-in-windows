/*   1:    */ package org.apache.hadoop.mapreduce;
/*   2:    */ 
/*   3:    */ import java.io.DataInput;
/*   4:    */ import java.io.DataOutput;
/*   5:    */ import java.io.IOException;
/*   6:    */ import java.util.ArrayList;
/*   7:    */ import java.util.Arrays;
/*   8:    */ import java.util.Collection;
/*   9:    */ import org.apache.hadoop.classification.InterfaceAudience.Private;
/*  10:    */ import org.apache.hadoop.classification.InterfaceStability.Evolving;
/*  11:    */ import org.apache.hadoop.io.Text;
/*  12:    */ import org.apache.hadoop.io.Writable;
/*  13:    */ import org.apache.hadoop.io.WritableUtils;
/*  14:    */ import org.apache.hadoop.mapred.TIPStatus;
/*  15:    */ import org.apache.hadoop.mapred.TaskID;
/*  16:    */ import org.apache.hadoop.util.StringInterner;
/*  17:    */ 
/*  18:    */ @InterfaceAudience.Private
/*  19:    */ @InterfaceStability.Evolving
/*  20:    */ public class TaskReport
/*  21:    */   implements Writable
/*  22:    */ {
/*  23:    */   private TaskID taskid;
/*  24:    */   private float progress;
/*  25:    */   private String state;
/*  26:    */   private String[] diagnostics;
/*  27:    */   private long startTime;
/*  28:    */   private long finishTime;
/*  29:    */   private Counters counters;
/*  30:    */   private TIPStatus currentStatus;
/*  31: 49 */   private Collection<TaskAttemptID> runningAttempts = new ArrayList();
/*  32: 51 */   private TaskAttemptID successfulAttempt = new TaskAttemptID();
/*  33:    */   
/*  34:    */   public TaskReport()
/*  35:    */   {
/*  36: 53 */     this.taskid = new TaskID();
/*  37:    */   }
/*  38:    */   
/*  39:    */   public TaskReport(TaskID taskid, float progress, String state, String[] diagnostics, TIPStatus currentStatus, long startTime, long finishTime, Counters counters)
/*  40:    */   {
/*  41: 71 */     this.taskid = taskid;
/*  42: 72 */     this.progress = progress;
/*  43: 73 */     this.state = state;
/*  44: 74 */     this.diagnostics = diagnostics;
/*  45: 75 */     this.currentStatus = currentStatus;
/*  46: 76 */     this.startTime = startTime;
/*  47: 77 */     this.finishTime = finishTime;
/*  48: 78 */     this.counters = counters;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public String getTaskId()
/*  52:    */   {
/*  53: 83 */     return this.taskid.toString();
/*  54:    */   }
/*  55:    */   
/*  56:    */   public TaskID getTaskID()
/*  57:    */   {
/*  58: 88 */     return this.taskid;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public float getProgress()
/*  62:    */   {
/*  63: 92 */     return this.progress;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public String getState()
/*  67:    */   {
/*  68: 95 */     return this.state;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public String[] getDiagnostics()
/*  72:    */   {
/*  73: 98 */     return this.diagnostics;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public Counters getTaskCounters()
/*  77:    */   {
/*  78:101 */     return this.counters;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public TIPStatus getCurrentStatus()
/*  82:    */   {
/*  83:105 */     return this.currentStatus;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public long getFinishTime()
/*  87:    */   {
/*  88:113 */     return this.finishTime;
/*  89:    */   }
/*  90:    */   
/*  91:    */   protected void setFinishTime(long finishTime)
/*  92:    */   {
/*  93:121 */     this.finishTime = finishTime;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public long getStartTime()
/*  97:    */   {
/*  98:129 */     return this.startTime;
/*  99:    */   }
/* 100:    */   
/* 101:    */   protected void setStartTime(long startTime)
/* 102:    */   {
/* 103:136 */     this.startTime = startTime;
/* 104:    */   }
/* 105:    */   
/* 106:    */   protected void setSuccessfulAttemptId(TaskAttemptID t)
/* 107:    */   {
/* 108:143 */     this.successfulAttempt = t;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public TaskAttemptID getSuccessfulTaskAttemptId()
/* 112:    */   {
/* 113:150 */     return this.successfulAttempt;
/* 114:    */   }
/* 115:    */   
/* 116:    */   protected void setRunningTaskAttemptIds(Collection<TaskAttemptID> runningAttempts)
/* 117:    */   {
/* 118:158 */     this.runningAttempts = runningAttempts;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public Collection<TaskAttemptID> getRunningTaskAttemptIds()
/* 122:    */   {
/* 123:165 */     return this.runningAttempts;
/* 124:    */   }
/* 125:    */   
/* 126:    */   public boolean equals(Object o)
/* 127:    */   {
/* 128:171 */     if (o == null) {
/* 129:172 */       return false;
/* 130:    */     }
/* 131:173 */     if (o.getClass().equals(getClass()))
/* 132:    */     {
/* 133:174 */       TaskReport report = (TaskReport)o;
/* 134:175 */       return (this.counters.equals(report.getTaskCounters())) && (Arrays.toString(this.diagnostics).equals(Arrays.toString(report.getDiagnostics()))) && (this.finishTime == report.getFinishTime()) && (this.progress == report.getProgress()) && (this.startTime == report.getStartTime()) && (this.state.equals(report.getState())) && (this.taskid.equals(report.getTaskID()));
/* 135:    */     }
/* 136:184 */     return false;
/* 137:    */   }
/* 138:    */   
/* 139:    */   public int hashCode()
/* 140:    */   {
/* 141:189 */     return (this.counters.toString() + Arrays.toString(this.diagnostics) + this.finishTime + this.progress + this.startTime + this.state + this.taskid.toString()).hashCode();
/* 142:    */   }
/* 143:    */   
/* 144:    */   public void write(DataOutput out)
/* 145:    */     throws IOException
/* 146:    */   {
/* 147:197 */     this.taskid.write(out);
/* 148:198 */     out.writeFloat(this.progress);
/* 149:199 */     Text.writeString(out, this.state);
/* 150:200 */     out.writeLong(this.startTime);
/* 151:201 */     out.writeLong(this.finishTime);
/* 152:202 */     WritableUtils.writeStringArray(out, this.diagnostics);
/* 153:203 */     this.counters.write(out);
/* 154:204 */     WritableUtils.writeEnum(out, this.currentStatus);
/* 155:205 */     if (this.currentStatus == TIPStatus.RUNNING)
/* 156:    */     {
/* 157:206 */       WritableUtils.writeVInt(out, this.runningAttempts.size());
/* 158:207 */       TaskAttemptID[] t = new TaskAttemptID[0];
/* 159:208 */       t = (TaskAttemptID[])this.runningAttempts.toArray(t);
/* 160:209 */       for (int i = 0; i < t.length; i++) {
/* 161:210 */         t[i].write(out);
/* 162:    */       }
/* 163:    */     }
/* 164:212 */     else if (this.currentStatus == TIPStatus.COMPLETE)
/* 165:    */     {
/* 166:213 */       this.successfulAttempt.write(out);
/* 167:    */     }
/* 168:    */   }
/* 169:    */   
/* 170:    */   public void readFields(DataInput in)
/* 171:    */     throws IOException
/* 172:    */   {
/* 173:218 */     this.taskid.readFields(in);
/* 174:219 */     this.progress = in.readFloat();
/* 175:220 */     this.state = StringInterner.weakIntern(Text.readString(in));
/* 176:221 */     this.startTime = in.readLong();
/* 177:222 */     this.finishTime = in.readLong();
/* 178:    */     
/* 179:224 */     this.diagnostics = WritableUtils.readStringArray(in);
/* 180:225 */     this.counters = new Counters();
/* 181:226 */     this.counters.readFields(in);
/* 182:227 */     this.currentStatus = ((TIPStatus)WritableUtils.readEnum(in, TIPStatus.class));
/* 183:228 */     if (this.currentStatus == TIPStatus.RUNNING)
/* 184:    */     {
/* 185:229 */       int num = WritableUtils.readVInt(in);
/* 186:230 */       for (int i = 0; i < num; i++)
/* 187:    */       {
/* 188:231 */         TaskAttemptID t = new TaskAttemptID();
/* 189:232 */         t.readFields(in);
/* 190:233 */         this.runningAttempts.add(t);
/* 191:    */       }
/* 192:    */     }
/* 193:235 */     else if (this.currentStatus == TIPStatus.COMPLETE)
/* 194:    */     {
/* 195:236 */       this.successfulAttempt.readFields(in);
/* 196:    */     }
/* 197:    */   }
/* 198:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.TaskReport
 * JD-Core Version:    0.7.0.1
 */