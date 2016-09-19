/*   1:    */ package org.apache.hadoop.mapreduce;
/*   2:    */ 
/*   3:    */ import java.io.DataInput;
/*   4:    */ import java.io.DataOutput;
/*   5:    */ import java.io.IOException;
/*   6:    */ import java.text.NumberFormat;
/*   7:    */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*   8:    */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*   9:    */ import org.apache.hadoop.io.Text;
/*  10:    */ 
/*  11:    */ @InterfaceAudience.Public
/*  12:    */ @InterfaceStability.Stable
/*  13:    */ public class JobID
/*  14:    */   extends org.apache.hadoop.mapred.ID
/*  15:    */   implements Comparable<ID>
/*  16:    */ {
/*  17:    */   protected static final String JOB = "job";
/*  18:    */   public static final String JOBID_REGEX = "job_[0-9]+_[0-9]+";
/*  19:    */   private final Text jtIdentifier;
/*  20: 59 */   protected static final NumberFormat idFormat = ;
/*  21:    */   
/*  22:    */   static
/*  23:    */   {
/*  24: 61 */     idFormat.setGroupingUsed(false);
/*  25: 62 */     idFormat.setMinimumIntegerDigits(4);
/*  26:    */   }
/*  27:    */   
/*  28:    */   public JobID(String jtIdentifier, int id)
/*  29:    */   {
/*  30: 71 */     super(id);
/*  31: 72 */     this.jtIdentifier = new Text(jtIdentifier);
/*  32:    */   }
/*  33:    */   
/*  34:    */   public JobID()
/*  35:    */   {
/*  36: 76 */     this.jtIdentifier = new Text();
/*  37:    */   }
/*  38:    */   
/*  39:    */   public String getJtIdentifier()
/*  40:    */   {
/*  41: 80 */     return this.jtIdentifier.toString();
/*  42:    */   }
/*  43:    */   
/*  44:    */   public boolean equals(Object o)
/*  45:    */   {
/*  46: 85 */     if (!super.equals(o)) {
/*  47: 86 */       return false;
/*  48:    */     }
/*  49: 88 */     JobID that = (JobID)o;
/*  50: 89 */     return this.jtIdentifier.equals(that.jtIdentifier);
/*  51:    */   }
/*  52:    */   
/*  53:    */   public int compareTo(ID o)
/*  54:    */   {
/*  55: 95 */     JobID that = (JobID)o;
/*  56: 96 */     int jtComp = this.jtIdentifier.compareTo(that.jtIdentifier);
/*  57: 97 */     if (jtComp == 0) {
/*  58: 98 */       return this.id - that.id;
/*  59:    */     }
/*  60:100 */     return jtComp;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public StringBuilder appendTo(StringBuilder builder)
/*  64:    */   {
/*  65:110 */     builder.append('_');
/*  66:111 */     builder.append(this.jtIdentifier);
/*  67:112 */     builder.append('_');
/*  68:113 */     builder.append(idFormat.format(this.id));
/*  69:114 */     return builder;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public int hashCode()
/*  73:    */   {
/*  74:119 */     return this.jtIdentifier.hashCode() + this.id;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public String toString()
/*  78:    */   {
/*  79:124 */     return appendTo(new StringBuilder("job")).toString();
/*  80:    */   }
/*  81:    */   
/*  82:    */   public void readFields(DataInput in)
/*  83:    */     throws IOException
/*  84:    */   {
/*  85:129 */     super.readFields(in);
/*  86:130 */     this.jtIdentifier.readFields(in);
/*  87:    */   }
/*  88:    */   
/*  89:    */   public void write(DataOutput out)
/*  90:    */     throws IOException
/*  91:    */   {
/*  92:135 */     super.write(out);
/*  93:136 */     this.jtIdentifier.write(out);
/*  94:    */   }
/*  95:    */   
/*  96:    */   public static JobID forName(String str)
/*  97:    */     throws IllegalArgumentException
/*  98:    */   {
/*  99:144 */     if (str == null) {
/* 100:145 */       return null;
/* 101:    */     }
/* 102:    */     try
/* 103:    */     {
/* 104:147 */       String[] parts = str.split("_");
/* 105:148 */       if ((parts.length == 3) && 
/* 106:149 */         (parts[0].equals("job"))) {
/* 107:150 */         return new org.apache.hadoop.mapred.JobID(parts[1], Integer.parseInt(parts[2]));
/* 108:    */       }
/* 109:    */     }
/* 110:    */     catch (Exception ex) {}
/* 111:156 */     throw new IllegalArgumentException("JobId string : " + str + " is not properly formed");
/* 112:    */   }
/* 113:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.JobID
 * JD-Core Version:    0.7.0.1
 */