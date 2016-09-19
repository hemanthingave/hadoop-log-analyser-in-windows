/*  1:   */ package org.apache.hadoop.mapreduce.security.token;
/*  2:   */ 
/*  3:   */ import java.io.DataInput;
/*  4:   */ import java.io.DataOutput;
/*  5:   */ import java.io.IOException;
/*  6:   */ import org.apache.hadoop.classification.InterfaceAudience.Private;
/*  7:   */ import org.apache.hadoop.classification.InterfaceStability.Unstable;
/*  8:   */ import org.apache.hadoop.io.Text;
/*  9:   */ import org.apache.hadoop.security.UserGroupInformation;
/* 10:   */ import org.apache.hadoop.security.token.Token.TrivialRenewer;
/* 11:   */ import org.apache.hadoop.security.token.TokenIdentifier;
/* 12:   */ 
/* 13:   */ @InterfaceAudience.Private
/* 14:   */ @InterfaceStability.Unstable
/* 15:   */ public class JobTokenIdentifier
/* 16:   */   extends TokenIdentifier
/* 17:   */ {
/* 18:   */   private Text jobid;
/* 19:39 */   public static final Text KIND_NAME = new Text("mapreduce.job");
/* 20:   */   
/* 21:   */   public JobTokenIdentifier()
/* 22:   */   {
/* 23:45 */     this.jobid = new Text();
/* 24:   */   }
/* 25:   */   
/* 26:   */   public JobTokenIdentifier(Text jobid)
/* 27:   */   {
/* 28:53 */     this.jobid = jobid;
/* 29:   */   }
/* 30:   */   
/* 31:   */   public Text getKind()
/* 32:   */   {
/* 33:59 */     return KIND_NAME;
/* 34:   */   }
/* 35:   */   
/* 36:   */   public UserGroupInformation getUser()
/* 37:   */   {
/* 38:65 */     if ((this.jobid == null) || ("".equals(this.jobid.toString()))) {
/* 39:66 */       return null;
/* 40:   */     }
/* 41:68 */     return UserGroupInformation.createRemoteUser(this.jobid.toString());
/* 42:   */   }
/* 43:   */   
/* 44:   */   public Text getJobId()
/* 45:   */   {
/* 46:76 */     return this.jobid;
/* 47:   */   }
/* 48:   */   
/* 49:   */   public void readFields(DataInput in)
/* 50:   */     throws IOException
/* 51:   */   {
/* 52:82 */     this.jobid.readFields(in);
/* 53:   */   }
/* 54:   */   
/* 55:   */   public void write(DataOutput out)
/* 56:   */     throws IOException
/* 57:   */   {
/* 58:88 */     this.jobid.write(out);
/* 59:   */   }
/* 60:   */   
/* 61:   */   @InterfaceAudience.Private
/* 62:   */   public static class Renewer
/* 63:   */     extends Token.TrivialRenewer
/* 64:   */   {
/* 65:   */     protected Text getKind()
/* 66:   */     {
/* 67:95 */       return JobTokenIdentifier.KIND_NAME;
/* 68:   */     }
/* 69:   */   }
/* 70:   */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.security.token.JobTokenIdentifier
 * JD-Core Version:    0.7.0.1
 */