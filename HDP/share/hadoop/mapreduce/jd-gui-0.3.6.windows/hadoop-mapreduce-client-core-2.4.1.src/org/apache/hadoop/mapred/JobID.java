/*   1:    */ package org.apache.hadoop.mapred;
/*   2:    */ 
/*   3:    */ import java.io.DataInput;
/*   4:    */ import java.io.IOException;
/*   5:    */ import java.text.NumberFormat;
/*   6:    */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*   7:    */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*   8:    */ 
/*   9:    */ @InterfaceAudience.Public
/*  10:    */ @InterfaceStability.Stable
/*  11:    */ public class JobID
/*  12:    */   extends org.apache.hadoop.mapreduce.JobID
/*  13:    */ {
/*  14:    */   public JobID(String jtIdentifier, int id)
/*  15:    */   {
/*  16: 53 */     super(jtIdentifier, id);
/*  17:    */   }
/*  18:    */   
/*  19:    */   public JobID() {}
/*  20:    */   
/*  21:    */   public static JobID downgrade(org.apache.hadoop.mapreduce.JobID old)
/*  22:    */   {
/*  23: 64 */     if ((old instanceof JobID)) {
/*  24: 65 */       return (JobID)old;
/*  25:    */     }
/*  26: 67 */     return new JobID(old.getJtIdentifier(), old.getId());
/*  27:    */   }
/*  28:    */   
/*  29:    */   @Deprecated
/*  30:    */   public static JobID read(DataInput in)
/*  31:    */     throws IOException
/*  32:    */   {
/*  33: 73 */     JobID jobId = new JobID();
/*  34: 74 */     jobId.readFields(in);
/*  35: 75 */     return jobId;
/*  36:    */   }
/*  37:    */   
/*  38:    */   public static JobID forName(String str)
/*  39:    */     throws IllegalArgumentException
/*  40:    */   {
/*  41: 83 */     return (JobID)org.apache.hadoop.mapreduce.JobID.forName(str);
/*  42:    */   }
/*  43:    */   
/*  44:    */   @Deprecated
/*  45:    */   public static String getJobIDsPattern(String jtIdentifier, Integer jobId)
/*  46:    */   {
/*  47:102 */     StringBuilder builder = new StringBuilder("job").append('_');
/*  48:103 */     builder.append(getJobIDsPatternWOPrefix(jtIdentifier, jobId));
/*  49:104 */     return builder.toString();
/*  50:    */   }
/*  51:    */   
/*  52:    */   @Deprecated
/*  53:    */   static StringBuilder getJobIDsPatternWOPrefix(String jtIdentifier, Integer jobId)
/*  54:    */   {
/*  55:110 */     StringBuilder builder = new StringBuilder();
/*  56:111 */     if (jtIdentifier != null) {
/*  57:112 */       builder.append(jtIdentifier);
/*  58:    */     } else {
/*  59:114 */       builder.append("[^").append('_').append("]*");
/*  60:    */     }
/*  61:116 */     builder.append('_').append(jobId != null ? idFormat.format(jobId) : "[0-9]*");
/*  62:    */     
/*  63:118 */     return builder;
/*  64:    */   }
/*  65:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.JobID
 * JD-Core Version:    0.7.0.1
 */