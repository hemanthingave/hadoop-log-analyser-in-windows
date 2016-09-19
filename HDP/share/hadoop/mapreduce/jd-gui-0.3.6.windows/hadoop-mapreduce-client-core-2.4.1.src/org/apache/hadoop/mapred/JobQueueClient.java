/*   1:    */ package org.apache.hadoop.mapred;
/*   2:    */ 
/*   3:    */ import com.google.common.base.Charsets;
/*   4:    */ import java.io.IOException;
/*   5:    */ import java.io.OutputStreamWriter;
/*   6:    */ import java.io.PrintStream;
/*   7:    */ import java.io.PrintWriter;
/*   8:    */ import java.io.Writer;
/*   9:    */ import java.util.ArrayList;
/*  10:    */ import java.util.Arrays;
/*  11:    */ import java.util.List;
/*  12:    */ import org.apache.hadoop.conf.Configured;
/*  13:    */ import org.apache.hadoop.mapreduce.JobStatus;
/*  14:    */ import org.apache.hadoop.security.UserGroupInformation;
/*  15:    */ import org.apache.hadoop.util.Tool;
/*  16:    */ import org.apache.hadoop.util.ToolRunner;
/*  17:    */ 
/*  18:    */ class JobQueueClient
/*  19:    */   extends Configured
/*  20:    */   implements Tool
/*  21:    */ {
/*  22:    */   JobClient jc;
/*  23:    */   
/*  24:    */   public JobQueueClient() {}
/*  25:    */   
/*  26:    */   public JobQueueClient(JobConf conf)
/*  27:    */     throws IOException
/*  28:    */   {
/*  29: 53 */     setConf(conf);
/*  30:    */   }
/*  31:    */   
/*  32:    */   private void init(JobConf conf)
/*  33:    */     throws IOException
/*  34:    */   {
/*  35: 57 */     setConf(conf);
/*  36: 58 */     this.jc = new JobClient(conf);
/*  37:    */   }
/*  38:    */   
/*  39:    */   public int run(String[] argv)
/*  40:    */     throws Exception
/*  41:    */   {
/*  42: 63 */     int exitcode = -1;
/*  43: 65 */     if (argv.length < 1)
/*  44:    */     {
/*  45: 66 */       displayUsage("");
/*  46: 67 */       return exitcode;
/*  47:    */     }
/*  48: 69 */     String cmd = argv[0];
/*  49: 70 */     boolean displayQueueList = false;
/*  50: 71 */     boolean displayQueueInfoWithJobs = false;
/*  51: 72 */     boolean displayQueueInfoWithoutJobs = false;
/*  52: 73 */     boolean displayQueueAclsInfoForCurrentUser = false;
/*  53: 75 */     if ("-list".equals(cmd))
/*  54:    */     {
/*  55: 76 */       displayQueueList = true;
/*  56:    */     }
/*  57: 77 */     else if ("-showacls".equals(cmd))
/*  58:    */     {
/*  59: 78 */       displayQueueAclsInfoForCurrentUser = true;
/*  60:    */     }
/*  61: 79 */     else if ("-info".equals(cmd))
/*  62:    */     {
/*  63: 80 */       if ((argv.length == 2) && (!argv[1].equals("-showJobs")))
/*  64:    */       {
/*  65: 81 */         displayQueueInfoWithoutJobs = true;
/*  66:    */       }
/*  67: 82 */       else if (argv.length == 3)
/*  68:    */       {
/*  69: 83 */         if (argv[2].equals("-showJobs"))
/*  70:    */         {
/*  71: 84 */           displayQueueInfoWithJobs = true;
/*  72:    */         }
/*  73:    */         else
/*  74:    */         {
/*  75: 86 */           displayUsage(cmd);
/*  76: 87 */           return exitcode;
/*  77:    */         }
/*  78:    */       }
/*  79:    */       else
/*  80:    */       {
/*  81: 90 */         displayUsage(cmd);
/*  82: 91 */         return exitcode;
/*  83:    */       }
/*  84:    */     }
/*  85:    */     else
/*  86:    */     {
/*  87: 94 */       displayUsage(cmd);
/*  88: 95 */       return exitcode;
/*  89:    */     }
/*  90: 98 */     JobConf conf = new JobConf(getConf());
/*  91: 99 */     init(conf);
/*  92:100 */     if (displayQueueList)
/*  93:    */     {
/*  94:101 */       displayQueueList();
/*  95:102 */       exitcode = 0;
/*  96:    */     }
/*  97:103 */     else if (displayQueueInfoWithoutJobs)
/*  98:    */     {
/*  99:104 */       displayQueueInfo(argv[1], false);
/* 100:105 */       exitcode = 0;
/* 101:    */     }
/* 102:106 */     else if (displayQueueInfoWithJobs)
/* 103:    */     {
/* 104:107 */       displayQueueInfo(argv[1], true);
/* 105:108 */       exitcode = 0;
/* 106:    */     }
/* 107:109 */     else if (displayQueueAclsInfoForCurrentUser)
/* 108:    */     {
/* 109:110 */       displayQueueAclsInfoForCurrentUser();
/* 110:111 */       exitcode = 0;
/* 111:    */     }
/* 112:113 */     return exitcode;
/* 113:    */   }
/* 114:    */   
/* 115:    */   void printJobQueueInfo(JobQueueInfo jobQueueInfo, Writer writer)
/* 116:    */     throws IOException
/* 117:    */   {
/* 118:119 */     printJobQueueInfo(jobQueueInfo, writer, "");
/* 119:    */   }
/* 120:    */   
/* 121:    */   void printJobQueueInfo(JobQueueInfo jobQueueInfo, Writer writer, String prefix)
/* 122:    */     throws IOException
/* 123:    */   {
/* 124:126 */     if (jobQueueInfo == null)
/* 125:    */     {
/* 126:127 */       writer.write("No queue found.\n");
/* 127:128 */       writer.flush();
/* 128:129 */       return;
/* 129:    */     }
/* 130:131 */     writer.write(String.format(prefix + "======================\n", new Object[0]));
/* 131:132 */     writer.write(String.format(prefix + "Queue Name : %s \n", new Object[] { jobQueueInfo.getQueueName() }));
/* 132:    */     
/* 133:134 */     writer.write(String.format(prefix + "Queue State : %s \n", new Object[] { jobQueueInfo.getQueueState() }));
/* 134:    */     
/* 135:136 */     writer.write(String.format(prefix + "Scheduling Info : %s \n", new Object[] { jobQueueInfo.getSchedulingInfo() }));
/* 136:    */     
/* 137:138 */     List<JobQueueInfo> childQueues = jobQueueInfo.getChildren();
/* 138:139 */     if ((childQueues != null) && (childQueues.size() > 0)) {
/* 139:140 */       for (int i = 0; i < childQueues.size(); i++) {
/* 140:141 */         printJobQueueInfo((JobQueueInfo)childQueues.get(i), writer, "    " + prefix);
/* 141:    */       }
/* 142:    */     }
/* 143:144 */     writer.flush();
/* 144:    */   }
/* 145:    */   
/* 146:    */   private void displayQueueList()
/* 147:    */     throws IOException
/* 148:    */   {
/* 149:148 */     JobQueueInfo[] rootQueues = this.jc.getRootQueues();
/* 150:149 */     for (JobQueueInfo queue : rootQueues) {
/* 151:150 */       printJobQueueInfo(queue, new PrintWriter(new OutputStreamWriter(System.out, Charsets.UTF_8)));
/* 152:    */     }
/* 153:    */   }
/* 154:    */   
/* 155:    */   List<JobQueueInfo> expandQueueList(JobQueueInfo[] rootQueues)
/* 156:    */   {
/* 157:162 */     List<JobQueueInfo> allQueues = new ArrayList();
/* 158:163 */     for (JobQueueInfo queue : rootQueues)
/* 159:    */     {
/* 160:164 */       allQueues.add(queue);
/* 161:165 */       if (queue.getChildren() != null)
/* 162:    */       {
/* 163:166 */         JobQueueInfo[] childQueues = (JobQueueInfo[])queue.getChildren().toArray(new JobQueueInfo[0]);
/* 164:    */         
/* 165:168 */         allQueues.addAll(expandQueueList(childQueues));
/* 166:    */       }
/* 167:    */     }
/* 168:171 */     return allQueues;
/* 169:    */   }
/* 170:    */   
/* 171:    */   private void displayQueueInfo(String queue, boolean showJobs)
/* 172:    */     throws IOException, InterruptedException
/* 173:    */   {
/* 174:183 */     JobQueueInfo jobQueueInfo = this.jc.getQueueInfo(queue);
/* 175:185 */     if (jobQueueInfo == null)
/* 176:    */     {
/* 177:186 */       System.out.println("Queue \"" + queue + "\" does not exist.");
/* 178:187 */       return;
/* 179:    */     }
/* 180:189 */     printJobQueueInfo(jobQueueInfo, new PrintWriter(new OutputStreamWriter(System.out, Charsets.UTF_8)));
/* 181:191 */     if ((showJobs) && ((jobQueueInfo.getChildren() == null) || (jobQueueInfo.getChildren().size() == 0)))
/* 182:    */     {
/* 183:193 */       JobStatus[] jobs = jobQueueInfo.getJobStatuses();
/* 184:194 */       if (jobs == null) {
/* 185:195 */         jobs = new JobStatus[0];
/* 186:    */       }
/* 187:196 */       this.jc.displayJobList(jobs);
/* 188:    */     }
/* 189:    */   }
/* 190:    */   
/* 191:    */   private void displayQueueAclsInfoForCurrentUser()
/* 192:    */     throws IOException
/* 193:    */   {
/* 194:201 */     QueueAclsInfo[] queueAclsInfoList = this.jc.getQueueAclsForCurrentUser();
/* 195:202 */     UserGroupInformation ugi = UserGroupInformation.getCurrentUser();
/* 196:203 */     if (queueAclsInfoList.length > 0)
/* 197:    */     {
/* 198:204 */       System.out.println("Queue acls for user :  " + ugi.getShortUserName());
/* 199:205 */       System.out.println("\nQueue  Operations");
/* 200:206 */       System.out.println("=====================");
/* 201:207 */       for (QueueAclsInfo queueInfo : queueAclsInfoList)
/* 202:    */       {
/* 203:208 */         System.out.print(queueInfo.getQueueName() + "  ");
/* 204:209 */         String[] ops = queueInfo.getOperations();
/* 205:210 */         Arrays.sort(ops);
/* 206:211 */         int max = ops.length - 1;
/* 207:212 */         for (int j = 0; j < ops.length; j++)
/* 208:    */         {
/* 209:213 */           System.out.print(ops[j].replaceFirst("acl-", ""));
/* 210:214 */           if (j < max) {
/* 211:215 */             System.out.print(",");
/* 212:    */           }
/* 213:    */         }
/* 214:218 */         System.out.println();
/* 215:    */       }
/* 216:    */     }
/* 217:    */     else
/* 218:    */     {
/* 219:221 */       System.out.println("User " + ugi.getShortUserName() + " does not have access to any queue. \n");
/* 220:    */     }
/* 221:    */   }
/* 222:    */   
/* 223:    */   private void displayUsage(String cmd)
/* 224:    */   {
/* 225:227 */     String prefix = "Usage: JobQueueClient ";
/* 226:228 */     if ("-queueinfo".equals(cmd))
/* 227:    */     {
/* 228:229 */       System.err.println(prefix + "[" + cmd + "<job-queue-name> [-showJobs]]");
/* 229:    */     }
/* 230:    */     else
/* 231:    */     {
/* 232:231 */       System.err.printf(prefix + "<command> <args>%n", new Object[0]);
/* 233:232 */       System.err.printf("\t[-list]%n", new Object[0]);
/* 234:233 */       System.err.printf("\t[-info <job-queue-name> [-showJobs]]%n", new Object[0]);
/* 235:234 */       System.err.printf("\t[-showacls] %n%n", new Object[0]);
/* 236:235 */       ToolRunner.printGenericCommandUsage(System.out);
/* 237:    */     }
/* 238:    */   }
/* 239:    */   
/* 240:    */   public static void main(String[] argv)
/* 241:    */     throws Exception
/* 242:    */   {
/* 243:240 */     int res = ToolRunner.run(new JobQueueClient(), argv);
/* 244:241 */     System.exit(res);
/* 245:    */   }
/* 246:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.JobQueueClient
 * JD-Core Version:    0.7.0.1
 */