/*   1:    */ package org.apache.hadoop.mapred;
/*   2:    */ 
/*   3:    */ import java.io.DataInput;
/*   4:    */ import java.io.DataOutput;
/*   5:    */ import java.io.IOException;
/*   6:    */ import java.text.NumberFormat;
/*   7:    */ 
/*   8:    */ class JVMId
/*   9:    */   extends ID
/*  10:    */ {
/*  11:    */   boolean isMap;
/*  12:    */   JobID jobId;
/*  13:    */   private static final String JVM = "jvm";
/*  14: 30 */   private static NumberFormat idFormat = ;
/*  15:    */   
/*  16:    */   static
/*  17:    */   {
/*  18: 32 */     idFormat.setGroupingUsed(false);
/*  19: 33 */     idFormat.setMinimumIntegerDigits(6);
/*  20:    */   }
/*  21:    */   
/*  22:    */   public JVMId(JobID jobId, boolean isMap, int id)
/*  23:    */   {
/*  24: 37 */     super(id);
/*  25: 38 */     this.isMap = isMap;
/*  26: 39 */     this.jobId = jobId;
/*  27:    */   }
/*  28:    */   
/*  29:    */   public JVMId(String jtIdentifier, int jobId, boolean isMap, int id)
/*  30:    */   {
/*  31: 43 */     this(new JobID(jtIdentifier, jobId), isMap, id);
/*  32:    */   }
/*  33:    */   
/*  34:    */   public JVMId()
/*  35:    */   {
/*  36: 47 */     this.jobId = new JobID();
/*  37:    */   }
/*  38:    */   
/*  39:    */   public boolean isMapJVM()
/*  40:    */   {
/*  41: 51 */     return this.isMap;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public JobID getJobId()
/*  45:    */   {
/*  46: 54 */     return this.jobId;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public boolean equals(Object o)
/*  50:    */   {
/*  51: 57 */     if (o == null) {
/*  52: 58 */       return false;
/*  53:    */     }
/*  54: 59 */     if (o.getClass().equals(getClass()))
/*  55:    */     {
/*  56: 60 */       JVMId that = (JVMId)o;
/*  57: 61 */       return (this.id == that.id) && (this.isMap == that.isMap) && (this.jobId.equals(that.jobId));
/*  58:    */     }
/*  59: 65 */     return false;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public int compareTo(org.apache.hadoop.mapreduce.ID o)
/*  63:    */   {
/*  64: 72 */     JVMId that = (JVMId)o;
/*  65: 73 */     int jobComp = this.jobId.compareTo(that.jobId);
/*  66: 74 */     if (jobComp == 0)
/*  67:    */     {
/*  68: 75 */       if (this.isMap == that.isMap) {
/*  69: 76 */         return this.id - that.id;
/*  70:    */       }
/*  71: 78 */       return this.isMap ? -1 : 1;
/*  72:    */     }
/*  73: 81 */     return jobComp;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public String toString()
/*  77:    */   {
/*  78: 87 */     return appendTo(new StringBuilder("jvm")).toString();
/*  79:    */   }
/*  80:    */   
/*  81:    */   protected StringBuilder appendTo(StringBuilder builder)
/*  82:    */   {
/*  83: 96 */     return this.jobId.appendTo(builder).append('_').append(this.isMap ? 'm' : 'r').append('_').append(idFormat.format(this.id));
/*  84:    */   }
/*  85:    */   
/*  86:    */   public int hashCode()
/*  87:    */   {
/*  88:105 */     return this.jobId.hashCode() * 11 + this.id;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public void readFields(DataInput in)
/*  92:    */     throws IOException
/*  93:    */   {
/*  94:110 */     super.readFields(in);
/*  95:111 */     this.jobId.readFields(in);
/*  96:112 */     this.isMap = in.readBoolean();
/*  97:    */   }
/*  98:    */   
/*  99:    */   public void write(DataOutput out)
/* 100:    */     throws IOException
/* 101:    */   {
/* 102:117 */     super.write(out);
/* 103:118 */     this.jobId.write(out);
/* 104:119 */     out.writeBoolean(this.isMap);
/* 105:    */   }
/* 106:    */   
/* 107:    */   public static JVMId forName(String str)
/* 108:    */     throws IllegalArgumentException
/* 109:    */   {
/* 110:128 */     if (str == null) {
/* 111:129 */       return null;
/* 112:    */     }
/* 113:    */     try
/* 114:    */     {
/* 115:131 */       String[] parts = str.split("_");
/* 116:132 */       if ((parts.length == 5) && 
/* 117:133 */         (parts[0].equals("jvm")))
/* 118:    */       {
/* 119:134 */         boolean isMap = false;
/* 120:135 */         if (parts[3].equals("m")) {
/* 121:135 */           isMap = true;
/* 122:136 */         } else if (parts[3].equals("r")) {
/* 123:136 */           isMap = false;
/* 124:    */         } else {
/* 125:137 */           throw new Exception();
/* 126:    */         }
/* 127:138 */         return new JVMId(parts[1], Integer.parseInt(parts[2]), isMap, Integer.parseInt(parts[4]));
/* 128:    */       }
/* 129:    */     }
/* 130:    */     catch (Exception ex) {}
/* 131:144 */     throw new IllegalArgumentException("TaskId string : " + str + " is not properly formed");
/* 132:    */   }
/* 133:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.JVMId
 * JD-Core Version:    0.7.0.1
 */