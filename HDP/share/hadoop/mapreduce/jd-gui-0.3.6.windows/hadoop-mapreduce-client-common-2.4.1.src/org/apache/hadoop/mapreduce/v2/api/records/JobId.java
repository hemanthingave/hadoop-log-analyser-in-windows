/*   1:    */ package org.apache.hadoop.mapreduce.v2.api.records;
/*   2:    */ 
/*   3:    */ import java.text.NumberFormat;
/*   4:    */ import org.apache.hadoop.yarn.api.records.ApplicationId;
/*   5:    */ 
/*   6:    */ public abstract class JobId
/*   7:    */   implements Comparable<JobId>
/*   8:    */ {
/*   9:    */   protected static final String JOB = "job";
/*  10:    */   protected static final char SEPARATOR = '_';
/*  11: 57 */   static final ThreadLocal<NumberFormat> jobIdFormat = new ThreadLocal()
/*  12:    */   {
/*  13:    */     public NumberFormat initialValue()
/*  14:    */     {
/*  15: 61 */       NumberFormat fmt = NumberFormat.getInstance();
/*  16: 62 */       fmt.setGroupingUsed(false);
/*  17: 63 */       fmt.setMinimumIntegerDigits(4);
/*  18: 64 */       return fmt;
/*  19:    */     }
/*  20:    */   };
/*  21:    */   
/*  22:    */   public abstract ApplicationId getAppId();
/*  23:    */   
/*  24:    */   public abstract int getId();
/*  25:    */   
/*  26:    */   public abstract void setAppId(ApplicationId paramApplicationId);
/*  27:    */   
/*  28:    */   public abstract void setId(int paramInt);
/*  29:    */   
/*  30:    */   public String toString()
/*  31:    */   {
/*  32: 70 */     StringBuilder builder = new StringBuilder("job");
/*  33: 71 */     builder.append('_');
/*  34: 72 */     builder.append(getAppId().getClusterTimestamp());
/*  35: 73 */     builder.append('_');
/*  36: 74 */     builder.append(((NumberFormat)jobIdFormat.get()).format(getId()));
/*  37: 75 */     return builder.toString();
/*  38:    */   }
/*  39:    */   
/*  40:    */   public int hashCode()
/*  41:    */   {
/*  42: 80 */     int prime = 31;
/*  43: 81 */     int result = 1;
/*  44: 82 */     result = 31 * result + getAppId().hashCode();
/*  45: 83 */     result = 31 * result + getId();
/*  46: 84 */     return result;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public boolean equals(Object obj)
/*  50:    */   {
/*  51: 89 */     if (this == obj) {
/*  52: 90 */       return true;
/*  53:    */     }
/*  54: 91 */     if (obj == null) {
/*  55: 92 */       return false;
/*  56:    */     }
/*  57: 93 */     if (getClass() != obj.getClass()) {
/*  58: 94 */       return false;
/*  59:    */     }
/*  60: 95 */     JobId other = (JobId)obj;
/*  61: 96 */     if (!getAppId().equals(other.getAppId())) {
/*  62: 97 */       return false;
/*  63:    */     }
/*  64: 98 */     if (getId() != other.getId()) {
/*  65: 99 */       return false;
/*  66:    */     }
/*  67:100 */     return true;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public int compareTo(JobId other)
/*  71:    */   {
/*  72:105 */     int appIdComp = getAppId().compareTo(other.getAppId());
/*  73:106 */     if (appIdComp == 0) {
/*  74:107 */       return getId() - other.getId();
/*  75:    */     }
/*  76:109 */     return appIdComp;
/*  77:    */   }
/*  78:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-common-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.v2.api.records.JobId
 * JD-Core Version:    0.7.0.1
 */