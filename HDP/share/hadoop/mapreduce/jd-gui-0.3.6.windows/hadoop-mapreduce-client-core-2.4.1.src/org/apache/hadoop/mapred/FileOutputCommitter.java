/*   1:    */ package org.apache.hadoop.mapred;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import org.apache.commons.logging.Log;
/*   5:    */ import org.apache.commons.logging.LogFactory;
/*   6:    */ import org.apache.hadoop.classification.InterfaceAudience.Private;
/*   7:    */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*   8:    */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*   9:    */ import org.apache.hadoop.fs.Path;
/*  10:    */ import org.apache.hadoop.mapreduce.JobStatus.State;
/*  11:    */ 
/*  12:    */ @InterfaceAudience.Public
/*  13:    */ @InterfaceStability.Stable
/*  14:    */ public class FileOutputCommitter
/*  15:    */   extends OutputCommitter
/*  16:    */ {
/*  17: 37 */   public static final Log LOG = LogFactory.getLog("org.apache.hadoop.mapred.FileOutputCommitter");
/*  18:    */   public static final String TEMP_DIR_NAME = "_temporary";
/*  19:    */   public static final String SUCCEEDED_FILE_NAME = "_SUCCESS";
/*  20:    */   static final String SUCCESSFUL_JOB_OUTPUT_DIR_MARKER = "mapreduce.fileoutputcommitter.marksuccessfuljobs";
/*  21:    */   
/*  22:    */   private static Path getOutputPath(JobContext context)
/*  23:    */   {
/*  24: 51 */     JobConf conf = context.getJobConf();
/*  25: 52 */     return FileOutputFormat.getOutputPath(conf);
/*  26:    */   }
/*  27:    */   
/*  28:    */   private static Path getOutputPath(TaskAttemptContext context)
/*  29:    */   {
/*  30: 56 */     JobConf conf = context.getJobConf();
/*  31: 57 */     return FileOutputFormat.getOutputPath(conf);
/*  32:    */   }
/*  33:    */   
/*  34: 60 */   private org.apache.hadoop.mapreduce.lib.output.FileOutputCommitter wrapped = null;
/*  35:    */   
/*  36:    */   private org.apache.hadoop.mapreduce.lib.output.FileOutputCommitter getWrapped(JobContext context)
/*  37:    */     throws IOException
/*  38:    */   {
/*  39: 64 */     if (this.wrapped == null) {
/*  40: 65 */       this.wrapped = new org.apache.hadoop.mapreduce.lib.output.FileOutputCommitter(getOutputPath(context), context);
/*  41:    */     }
/*  42: 68 */     return this.wrapped;
/*  43:    */   }
/*  44:    */   
/*  45:    */   private org.apache.hadoop.mapreduce.lib.output.FileOutputCommitter getWrapped(TaskAttemptContext context)
/*  46:    */     throws IOException
/*  47:    */   {
/*  48: 73 */     if (this.wrapped == null) {
/*  49: 74 */       this.wrapped = new org.apache.hadoop.mapreduce.lib.output.FileOutputCommitter(getOutputPath(context), context);
/*  50:    */     }
/*  51: 77 */     return this.wrapped;
/*  52:    */   }
/*  53:    */   
/*  54:    */   @InterfaceAudience.Private
/*  55:    */   Path getJobAttemptPath(JobContext context)
/*  56:    */   {
/*  57: 88 */     Path out = getOutputPath(context);
/*  58: 89 */     return out == null ? null : org.apache.hadoop.mapreduce.lib.output.FileOutputCommitter.getJobAttemptPath(context, out);
/*  59:    */   }
/*  60:    */   
/*  61:    */   @InterfaceAudience.Private
/*  62:    */   public Path getTaskAttemptPath(TaskAttemptContext context)
/*  63:    */     throws IOException
/*  64:    */   {
/*  65: 96 */     Path out = getOutputPath(context);
/*  66: 97 */     return out == null ? null : getTaskAttemptPath(context, out);
/*  67:    */   }
/*  68:    */   
/*  69:    */   private Path getTaskAttemptPath(TaskAttemptContext context, Path out)
/*  70:    */     throws IOException
/*  71:    */   {
/*  72:101 */     Path workPath = FileOutputFormat.getWorkOutputPath(context.getJobConf());
/*  73:102 */     if ((workPath == null) && (out != null)) {
/*  74:103 */       return org.apache.hadoop.mapreduce.lib.output.FileOutputCommitter.getTaskAttemptPath(context, out);
/*  75:    */     }
/*  76:106 */     return workPath;
/*  77:    */   }
/*  78:    */   
/*  79:    */   @InterfaceAudience.Private
/*  80:    */   Path getCommittedTaskPath(TaskAttemptContext context)
/*  81:    */   {
/*  82:118 */     Path out = getOutputPath(context);
/*  83:119 */     return out == null ? null : org.apache.hadoop.mapreduce.lib.output.FileOutputCommitter.getCommittedTaskPath(context, out);
/*  84:    */   }
/*  85:    */   
/*  86:    */   public Path getWorkPath(TaskAttemptContext context, Path outputPath)
/*  87:    */     throws IOException
/*  88:    */   {
/*  89:126 */     return outputPath == null ? null : getTaskAttemptPath(context, outputPath);
/*  90:    */   }
/*  91:    */   
/*  92:    */   public void setupJob(JobContext context)
/*  93:    */     throws IOException
/*  94:    */   {
/*  95:131 */     getWrapped(context).setupJob(context);
/*  96:    */   }
/*  97:    */   
/*  98:    */   public void commitJob(JobContext context)
/*  99:    */     throws IOException
/* 100:    */   {
/* 101:136 */     getWrapped(context).commitJob(context);
/* 102:    */   }
/* 103:    */   
/* 104:    */   @Deprecated
/* 105:    */   public void cleanupJob(JobContext context)
/* 106:    */     throws IOException
/* 107:    */   {
/* 108:142 */     getWrapped(context).cleanupJob(context);
/* 109:    */   }
/* 110:    */   
/* 111:    */   public void abortJob(JobContext context, int runState)
/* 112:    */     throws IOException
/* 113:    */   {
/* 114:    */     JobStatus.State state;
/* 115:149 */     if (runState == JobStatus.State.RUNNING.getValue())
/* 116:    */     {
/* 117:150 */       state = JobStatus.State.RUNNING;
/* 118:    */     }
/* 119:    */     else
/* 120:    */     {
/* 121:    */       JobStatus.State state;
/* 122:151 */       if (runState == JobStatus.State.SUCCEEDED.getValue())
/* 123:    */       {
/* 124:152 */         state = JobStatus.State.SUCCEEDED;
/* 125:    */       }
/* 126:    */       else
/* 127:    */       {
/* 128:    */         JobStatus.State state;
/* 129:153 */         if (runState == JobStatus.State.FAILED.getValue())
/* 130:    */         {
/* 131:154 */           state = JobStatus.State.FAILED;
/* 132:    */         }
/* 133:    */         else
/* 134:    */         {
/* 135:    */           JobStatus.State state;
/* 136:155 */           if (runState == JobStatus.State.PREP.getValue())
/* 137:    */           {
/* 138:156 */             state = JobStatus.State.PREP;
/* 139:    */           }
/* 140:    */           else
/* 141:    */           {
/* 142:    */             JobStatus.State state;
/* 143:157 */             if (runState == JobStatus.State.KILLED.getValue()) {
/* 144:158 */               state = JobStatus.State.KILLED;
/* 145:    */             } else {
/* 146:160 */               throw new IllegalArgumentException(runState + " is not a valid runState.");
/* 147:    */             }
/* 148:    */           }
/* 149:    */         }
/* 150:    */       }
/* 151:    */     }
/* 152:    */     JobStatus.State state;
/* 153:162 */     getWrapped(context).abortJob(context, state);
/* 154:    */   }
/* 155:    */   
/* 156:    */   public void setupTask(TaskAttemptContext context)
/* 157:    */     throws IOException
/* 158:    */   {
/* 159:167 */     getWrapped(context).setupTask(context);
/* 160:    */   }
/* 161:    */   
/* 162:    */   public void commitTask(TaskAttemptContext context)
/* 163:    */     throws IOException
/* 164:    */   {
/* 165:172 */     getWrapped(context).commitTask(context, getTaskAttemptPath(context));
/* 166:    */   }
/* 167:    */   
/* 168:    */   public void abortTask(TaskAttemptContext context)
/* 169:    */     throws IOException
/* 170:    */   {
/* 171:177 */     getWrapped(context).abortTask(context, getTaskAttemptPath(context));
/* 172:    */   }
/* 173:    */   
/* 174:    */   public boolean needsTaskCommit(TaskAttemptContext context)
/* 175:    */     throws IOException
/* 176:    */   {
/* 177:183 */     return getWrapped(context).needsTaskCommit(context, getTaskAttemptPath(context));
/* 178:    */   }
/* 179:    */   
/* 180:    */   public boolean isRecoverySupported()
/* 181:    */   {
/* 182:188 */     return true;
/* 183:    */   }
/* 184:    */   
/* 185:    */   public void recoverTask(TaskAttemptContext context)
/* 186:    */     throws IOException
/* 187:    */   {
/* 188:194 */     getWrapped(context).recoverTask(context);
/* 189:    */   }
/* 190:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.FileOutputCommitter
 * JD-Core Version:    0.7.0.1
 */