/*   1:    */ package org.apache.hadoop.mapred;
/*   2:    */ 
/*   3:    */ import java.io.DataInput;
/*   4:    */ import java.io.DataOutput;
/*   5:    */ import java.io.IOException;
/*   6:    */ import java.net.URL;
/*   7:    */ import org.apache.hadoop.classification.InterfaceAudience.LimitedPrivate;
/*   8:    */ import org.apache.hadoop.classification.InterfaceStability.Unstable;
/*   9:    */ import org.apache.hadoop.io.Text;
/*  10:    */ import org.apache.hadoop.io.Writable;
/*  11:    */ import org.apache.hadoop.io.WritableFactories;
/*  12:    */ import org.apache.hadoop.io.WritableFactory;
/*  13:    */ import org.apache.hadoop.util.StringInterner;
/*  14:    */ 
/*  15:    */ @InterfaceAudience.LimitedPrivate({"MapReduce"})
/*  16:    */ @InterfaceStability.Unstable
/*  17:    */ public class JobProfile
/*  18:    */   implements Writable
/*  19:    */ {
/*  20:    */   String user;
/*  21:    */   final JobID jobid;
/*  22:    */   String jobFile;
/*  23:    */   String url;
/*  24:    */   String name;
/*  25:    */   String queueName;
/*  26:    */   
/*  27:    */   static
/*  28:    */   {
/*  29: 43 */     WritableFactories.setFactory(JobProfile.class, new WritableFactory()
/*  30:    */     {
/*  31:    */       public Writable newInstance()
/*  32:    */       {
/*  33: 46 */         return new JobProfile();
/*  34:    */       }
/*  35:    */     });
/*  36:    */   }
/*  37:    */   
/*  38:    */   public JobProfile()
/*  39:    */   {
/*  40: 61 */     this.jobid = new JobID();
/*  41:    */   }
/*  42:    */   
/*  43:    */   public JobProfile(String user, org.apache.hadoop.mapreduce.JobID jobid, String jobFile, String url, String name)
/*  44:    */   {
/*  45: 77 */     this(user, jobid, jobFile, url, name, "default");
/*  46:    */   }
/*  47:    */   
/*  48:    */   public JobProfile(String user, org.apache.hadoop.mapreduce.JobID jobid, String jobFile, String url, String name, String queueName)
/*  49:    */   {
/*  50: 94 */     this.user = user;
/*  51: 95 */     this.jobid = JobID.downgrade(jobid);
/*  52: 96 */     this.jobFile = jobFile;
/*  53: 97 */     this.url = url;
/*  54: 98 */     this.name = name;
/*  55: 99 */     this.queueName = queueName;
/*  56:    */   }
/*  57:    */   
/*  58:    */   @Deprecated
/*  59:    */   public JobProfile(String user, String jobid, String jobFile, String url, String name)
/*  60:    */   {
/*  61:108 */     this(user, JobID.forName(jobid), jobFile, url, name);
/*  62:    */   }
/*  63:    */   
/*  64:    */   public String getUser()
/*  65:    */   {
/*  66:115 */     return this.user;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public JobID getJobID()
/*  70:    */   {
/*  71:122 */     return this.jobid;
/*  72:    */   }
/*  73:    */   
/*  74:    */   @Deprecated
/*  75:    */   public String getJobId()
/*  76:    */   {
/*  77:130 */     return this.jobid.toString();
/*  78:    */   }
/*  79:    */   
/*  80:    */   public String getJobFile()
/*  81:    */   {
/*  82:137 */     return this.jobFile;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public URL getURL()
/*  86:    */   {
/*  87:    */     try
/*  88:    */     {
/*  89:145 */       return new URL(this.url);
/*  90:    */     }
/*  91:    */     catch (IOException ie) {}
/*  92:147 */     return null;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public String getJobName()
/*  96:    */   {
/*  97:155 */     return this.name;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public String getQueueName()
/* 101:    */   {
/* 102:163 */     return this.queueName;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public void write(DataOutput out)
/* 106:    */     throws IOException
/* 107:    */   {
/* 108:170 */     this.jobid.write(out);
/* 109:171 */     Text.writeString(out, this.jobFile);
/* 110:172 */     Text.writeString(out, this.url);
/* 111:173 */     Text.writeString(out, this.user);
/* 112:174 */     Text.writeString(out, this.name);
/* 113:175 */     Text.writeString(out, this.queueName);
/* 114:    */   }
/* 115:    */   
/* 116:    */   public void readFields(DataInput in)
/* 117:    */     throws IOException
/* 118:    */   {
/* 119:179 */     this.jobid.readFields(in);
/* 120:180 */     this.jobFile = StringInterner.weakIntern(Text.readString(in));
/* 121:181 */     this.url = StringInterner.weakIntern(Text.readString(in));
/* 122:182 */     this.user = StringInterner.weakIntern(Text.readString(in));
/* 123:183 */     this.name = StringInterner.weakIntern(Text.readString(in));
/* 124:184 */     this.queueName = StringInterner.weakIntern(Text.readString(in));
/* 125:    */   }
/* 126:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.JobProfile
 * JD-Core Version:    0.7.0.1
 */