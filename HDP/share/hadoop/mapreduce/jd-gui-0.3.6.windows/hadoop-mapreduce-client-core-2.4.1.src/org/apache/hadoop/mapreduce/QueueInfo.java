/*   1:    */ package org.apache.hadoop.mapreduce;
/*   2:    */ 
/*   3:    */ import java.io.DataInput;
/*   4:    */ import java.io.DataOutput;
/*   5:    */ import java.io.IOException;
/*   6:    */ import java.util.ArrayList;
/*   7:    */ import java.util.List;
/*   8:    */ import java.util.Properties;
/*   9:    */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*  10:    */ import org.apache.hadoop.classification.InterfaceStability.Evolving;
/*  11:    */ import org.apache.hadoop.io.Text;
/*  12:    */ import org.apache.hadoop.io.Writable;
/*  13:    */ import org.apache.hadoop.io.WritableUtils;
/*  14:    */ import org.apache.hadoop.util.StringInterner;
/*  15:    */ 
/*  16:    */ @InterfaceAudience.Public
/*  17:    */ @InterfaceStability.Evolving
/*  18:    */ public class QueueInfo
/*  19:    */   implements Writable
/*  20:    */ {
/*  21: 43 */   private String queueName = "";
/*  22:    */   private String schedulingInfo;
/*  23:    */   private QueueState queueState;
/*  24:    */   private JobStatus[] stats;
/*  25:    */   private List<QueueInfo> children;
/*  26:    */   private Properties props;
/*  27:    */   
/*  28:    */   public QueueInfo()
/*  29:    */   {
/*  30: 64 */     this.queueState = QueueState.RUNNING;
/*  31: 65 */     this.children = new ArrayList();
/*  32: 66 */     this.props = new Properties();
/*  33:    */   }
/*  34:    */   
/*  35:    */   public QueueInfo(String queueName, String schedulingInfo)
/*  36:    */   {
/*  37: 78 */     this();
/*  38: 79 */     this.queueName = queueName;
/*  39: 80 */     this.schedulingInfo = schedulingInfo;
/*  40:    */   }
/*  41:    */   
/*  42:    */   public QueueInfo(String queueName, String schedulingInfo, QueueState state, JobStatus[] stats)
/*  43:    */   {
/*  44: 92 */     this(queueName, schedulingInfo);
/*  45: 93 */     this.queueState = state;
/*  46: 94 */     this.stats = stats;
/*  47:    */   }
/*  48:    */   
/*  49:    */   protected void setQueueName(String queueName)
/*  50:    */   {
/*  51:103 */     this.queueName = queueName;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public String getQueueName()
/*  55:    */   {
/*  56:112 */     return this.queueName;
/*  57:    */   }
/*  58:    */   
/*  59:    */   protected void setSchedulingInfo(String schedulingInfo)
/*  60:    */   {
/*  61:121 */     this.schedulingInfo = schedulingInfo;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public String getSchedulingInfo()
/*  65:    */   {
/*  66:131 */     if (this.schedulingInfo != null) {
/*  67:132 */       return this.schedulingInfo;
/*  68:    */     }
/*  69:134 */     return "N/A";
/*  70:    */   }
/*  71:    */   
/*  72:    */   protected void setState(QueueState state)
/*  73:    */   {
/*  74:143 */     this.queueState = state;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public QueueState getState()
/*  78:    */   {
/*  79:151 */     return this.queueState;
/*  80:    */   }
/*  81:    */   
/*  82:    */   protected void setJobStatuses(JobStatus[] stats)
/*  83:    */   {
/*  84:155 */     this.stats = stats;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public List<QueueInfo> getQueueChildren()
/*  88:    */   {
/*  89:164 */     return this.children;
/*  90:    */   }
/*  91:    */   
/*  92:    */   protected void setQueueChildren(List<QueueInfo> children)
/*  93:    */   {
/*  94:168 */     this.children = children;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public Properties getProperties()
/*  98:    */   {
/*  99:177 */     return this.props;
/* 100:    */   }
/* 101:    */   
/* 102:    */   protected void setProperties(Properties props)
/* 103:    */   {
/* 104:181 */     this.props = props;
/* 105:    */   }
/* 106:    */   
/* 107:    */   public JobStatus[] getJobStatuses()
/* 108:    */   {
/* 109:189 */     return this.stats;
/* 110:    */   }
/* 111:    */   
/* 112:    */   public void readFields(DataInput in)
/* 113:    */     throws IOException
/* 114:    */   {
/* 115:194 */     this.queueName = StringInterner.weakIntern(Text.readString(in));
/* 116:195 */     this.queueState = ((QueueState)WritableUtils.readEnum(in, QueueState.class));
/* 117:196 */     this.schedulingInfo = StringInterner.weakIntern(Text.readString(in));
/* 118:197 */     int length = in.readInt();
/* 119:198 */     this.stats = new JobStatus[length];
/* 120:199 */     for (int i = 0; i < length; i++)
/* 121:    */     {
/* 122:200 */       this.stats[i] = new JobStatus();
/* 123:201 */       this.stats[i].readFields(in);
/* 124:    */     }
/* 125:203 */     int count = in.readInt();
/* 126:204 */     this.children.clear();
/* 127:205 */     for (int i = 0; i < count; i++)
/* 128:    */     {
/* 129:206 */       QueueInfo childQueueInfo = new QueueInfo();
/* 130:207 */       childQueueInfo.readFields(in);
/* 131:208 */       this.children.add(childQueueInfo);
/* 132:    */     }
/* 133:    */   }
/* 134:    */   
/* 135:    */   public void write(DataOutput out)
/* 136:    */     throws IOException
/* 137:    */   {
/* 138:214 */     Text.writeString(out, this.queueName);
/* 139:215 */     WritableUtils.writeEnum(out, this.queueState);
/* 140:217 */     if (this.schedulingInfo != null) {
/* 141:218 */       Text.writeString(out, this.schedulingInfo);
/* 142:    */     } else {
/* 143:220 */       Text.writeString(out, "N/A");
/* 144:    */     }
/* 145:222 */     out.writeInt(this.stats.length);
/* 146:223 */     for (JobStatus stat : this.stats) {
/* 147:224 */       stat.write(out);
/* 148:    */     }
/* 149:226 */     out.writeInt(this.children.size());
/* 150:227 */     for (QueueInfo childQueueInfo : this.children) {
/* 151:228 */       childQueueInfo.write(out);
/* 152:    */     }
/* 153:    */   }
/* 154:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.QueueInfo
 * JD-Core Version:    0.7.0.1
 */