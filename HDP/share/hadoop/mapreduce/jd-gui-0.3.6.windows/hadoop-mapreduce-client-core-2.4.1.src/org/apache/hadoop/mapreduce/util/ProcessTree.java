/*   1:    */ package org.apache.hadoop.mapreduce.util;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.util.Arrays;
/*   5:    */ import org.apache.commons.logging.Log;
/*   6:    */ import org.apache.commons.logging.LogFactory;
/*   7:    */ import org.apache.hadoop.classification.InterfaceAudience.Private;
/*   8:    */ import org.apache.hadoop.classification.InterfaceStability.Unstable;
/*   9:    */ import org.apache.hadoop.util.Shell.ExitCodeException;
/*  10:    */ import org.apache.hadoop.util.Shell.ShellCommandExecutor;
/*  11:    */ 
/*  12:    */ @InterfaceAudience.Private
/*  13:    */ @InterfaceStability.Unstable
/*  14:    */ public class ProcessTree
/*  15:    */ {
/*  16: 39 */   private static final Log LOG = LogFactory.getLog(ProcessTree.class);
/*  17:    */   public static final long DEFAULT_SLEEPTIME_BEFORE_SIGKILL = 5000L;
/*  18:    */   private static final int SIGQUIT = 3;
/*  19:    */   private static final int SIGTERM = 15;
/*  20:    */   private static final int SIGKILL = 9;
/*  21:    */   private static final String SIGQUIT_STR = "SIGQUIT";
/*  22:    */   private static final String SIGTERM_STR = "SIGTERM";
/*  23:    */   private static final String SIGKILL_STR = "SIGKILL";
/*  24: 52 */   public static final boolean isSetsidAvailable = isSetsidSupported();
/*  25:    */   
/*  26:    */   private static boolean isSetsidSupported()
/*  27:    */   {
/*  28: 54 */     Shell.ShellCommandExecutor shexec = null;
/*  29: 55 */     boolean setsidSupported = true;
/*  30:    */     try
/*  31:    */     {
/*  32: 57 */       String[] args = { "setsid", "bash", "-c", "echo $$" };
/*  33: 58 */       shexec = new Shell.ShellCommandExecutor(args);
/*  34: 59 */       shexec.execute();
/*  35:    */     }
/*  36:    */     catch (IOException ioe)
/*  37:    */     {
/*  38: 61 */       LOG.warn("setsid is not available on this machine. So not using it.");
/*  39: 62 */       setsidSupported = false;
/*  40:    */     }
/*  41:    */     finally
/*  42:    */     {
/*  43: 64 */       LOG.info("setsid exited with exit code " + shexec.getExitCode());
/*  44:    */     }
/*  45: 66 */     return setsidSupported;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public static void destroy(String pid, long sleeptimeBeforeSigkill, boolean isProcessGroup, boolean inBackground)
/*  49:    */   {
/*  50: 81 */     if (isProcessGroup) {
/*  51: 82 */       destroyProcessGroup(pid, sleeptimeBeforeSigkill, inBackground);
/*  52:    */     } else {
/*  53: 87 */       destroyProcess(pid, sleeptimeBeforeSigkill, inBackground);
/*  54:    */     }
/*  55:    */   }
/*  56:    */   
/*  57:    */   protected static void destroyProcess(String pid, long sleeptimeBeforeSigkill, boolean inBackground)
/*  58:    */   {
/*  59:100 */     terminateProcess(pid);
/*  60:101 */     sigKill(pid, false, sleeptimeBeforeSigkill, inBackground);
/*  61:    */   }
/*  62:    */   
/*  63:    */   protected static void destroyProcessGroup(String pgrpId, long sleeptimeBeforeSigkill, boolean inBackground)
/*  64:    */   {
/*  65:113 */     terminateProcessGroup(pgrpId);
/*  66:114 */     sigKill(pgrpId, true, sleeptimeBeforeSigkill, inBackground);
/*  67:    */   }
/*  68:    */   
/*  69:    */   private static void sendSignal(String pid, int signalNum, String signalName)
/*  70:    */   {
/*  71:127 */     Shell.ShellCommandExecutor shexec = null;
/*  72:    */     try
/*  73:    */     {
/*  74:129 */       String[] args = { "kill", "-" + signalNum, pid };
/*  75:130 */       shexec = new Shell.ShellCommandExecutor(args);
/*  76:131 */       shexec.execute();
/*  77:    */     }
/*  78:    */     catch (IOException ioe)
/*  79:    */     {
/*  80:133 */       LOG.warn("Error executing shell command " + ioe);
/*  81:    */     }
/*  82:    */     finally
/*  83:    */     {
/*  84:135 */       if (pid.startsWith("-")) {
/*  85:136 */         LOG.info("Sending signal to all members of process group " + pid + ": " + signalName + ". Exit code " + shexec.getExitCode());
/*  86:    */       } else {
/*  87:139 */         LOG.info("Signaling process " + pid + " with " + signalName + ". Exit code " + shexec.getExitCode());
/*  88:    */       }
/*  89:    */     }
/*  90:    */   }
/*  91:    */   
/*  92:    */   private static void maybeSignalProcess(String pid, int signalNum, String signalName, boolean alwaysSignal)
/*  93:    */   {
/*  94:158 */     if ((alwaysSignal) || (isAlive(pid))) {
/*  95:159 */       sendSignal(pid, signalNum, signalName);
/*  96:    */     }
/*  97:    */   }
/*  98:    */   
/*  99:    */   private static void maybeSignalProcessGroup(String pgrpId, int signalNum, String signalName, boolean alwaysSignal)
/* 100:    */   {
/* 101:166 */     if ((alwaysSignal) || (isProcessGroupAlive(pgrpId))) {
/* 102:168 */       sendSignal("-" + pgrpId, signalNum, signalName);
/* 103:    */     }
/* 104:    */   }
/* 105:    */   
/* 106:    */   public static void terminateProcess(String pid)
/* 107:    */   {
/* 108:178 */     maybeSignalProcess(pid, 15, "SIGTERM", true);
/* 109:    */   }
/* 110:    */   
/* 111:    */   public static void terminateProcessGroup(String pgrpId)
/* 112:    */   {
/* 113:188 */     maybeSignalProcessGroup(pgrpId, 15, "SIGTERM", true);
/* 114:    */   }
/* 115:    */   
/* 116:    */   private static void sigKillInCurrentThread(String pid, boolean isProcessGroup, long sleepTimeBeforeSigKill)
/* 117:    */   {
/* 118:203 */     if ((isProcessGroup) || (isAlive(pid)))
/* 119:    */     {
/* 120:    */       try
/* 121:    */       {
/* 122:206 */         Thread.sleep(sleepTimeBeforeSigKill);
/* 123:    */       }
/* 124:    */       catch (InterruptedException i)
/* 125:    */       {
/* 126:208 */         LOG.warn("Thread sleep is interrupted.");
/* 127:    */       }
/* 128:210 */       if (isProcessGroup) {
/* 129:211 */         killProcessGroup(pid);
/* 130:    */       } else {
/* 131:213 */         killProcess(pid);
/* 132:    */       }
/* 133:    */     }
/* 134:    */   }
/* 135:    */   
/* 136:    */   private static void sigKill(String pid, boolean isProcessGroup, long sleeptimeBeforeSigkill, boolean inBackground)
/* 137:    */   {
/* 138:230 */     if (inBackground)
/* 139:    */     {
/* 140:231 */       SigKillThread sigKillThread = new SigKillThread(pid, isProcessGroup, sleeptimeBeforeSigkill, null);
/* 141:    */       
/* 142:233 */       sigKillThread.setDaemon(true);
/* 143:234 */       sigKillThread.start();
/* 144:    */     }
/* 145:    */     else
/* 146:    */     {
/* 147:237 */       sigKillInCurrentThread(pid, isProcessGroup, sleeptimeBeforeSigkill);
/* 148:    */     }
/* 149:    */   }
/* 150:    */   
/* 151:    */   public static void killProcess(String pid)
/* 152:    */   {
/* 153:247 */     maybeSignalProcess(pid, 9, "SIGKILL", false);
/* 154:    */   }
/* 155:    */   
/* 156:    */   public static void sigQuitProcess(String pid)
/* 157:    */   {
/* 158:257 */     maybeSignalProcess(pid, 3, "SIGQUIT", false);
/* 159:    */   }
/* 160:    */   
/* 161:    */   public static void killProcessGroup(String pgrpId)
/* 162:    */   {
/* 163:267 */     maybeSignalProcessGroup(pgrpId, 9, "SIGKILL", false);
/* 164:    */   }
/* 165:    */   
/* 166:    */   public static void sigQuitProcessGroup(String pgrpId)
/* 167:    */   {
/* 168:278 */     maybeSignalProcessGroup(pgrpId, 3, "SIGQUIT", false);
/* 169:    */   }
/* 170:    */   
/* 171:    */   public static boolean isAlive(String pid)
/* 172:    */   {
/* 173:290 */     Shell.ShellCommandExecutor shexec = null;
/* 174:    */     try
/* 175:    */     {
/* 176:292 */       String[] args = { "kill", "-0", pid };
/* 177:293 */       shexec = new Shell.ShellCommandExecutor(args);
/* 178:294 */       shexec.execute();
/* 179:    */     }
/* 180:    */     catch (Shell.ExitCodeException ee)
/* 181:    */     {
/* 182:296 */       return false;
/* 183:    */     }
/* 184:    */     catch (IOException ioe)
/* 185:    */     {
/* 186:298 */       LOG.warn("Error executing shell command " + Arrays.toString(shexec.getExecString()) + ioe);
/* 187:    */       
/* 188:300 */       return false;
/* 189:    */     }
/* 190:302 */     return shexec.getExitCode() == 0;
/* 191:    */   }
/* 192:    */   
/* 193:    */   public static boolean isProcessGroupAlive(String pgrpId)
/* 194:    */   {
/* 195:315 */     Shell.ShellCommandExecutor shexec = null;
/* 196:    */     try
/* 197:    */     {
/* 198:317 */       String[] args = { "kill", "-0", "-" + pgrpId };
/* 199:318 */       shexec = new Shell.ShellCommandExecutor(args);
/* 200:319 */       shexec.execute();
/* 201:    */     }
/* 202:    */     catch (Shell.ExitCodeException ee)
/* 203:    */     {
/* 204:321 */       return false;
/* 205:    */     }
/* 206:    */     catch (IOException ioe)
/* 207:    */     {
/* 208:323 */       LOG.warn("Error executing shell command " + Arrays.toString(shexec.getExecString()) + ioe);
/* 209:    */       
/* 210:325 */       return false;
/* 211:    */     }
/* 212:327 */     return shexec.getExitCode() == 0;
/* 213:    */   }
/* 214:    */   
/* 215:    */   static class SigKillThread
/* 216:    */     extends Thread
/* 217:    */   {
/* 218:335 */     private String pid = null;
/* 219:336 */     private boolean isProcessGroup = false;
/* 220:338 */     private long sleepTimeBeforeSigKill = 5000L;
/* 221:    */     
/* 222:    */     private SigKillThread(String pid, boolean isProcessGroup, long interval)
/* 223:    */     {
/* 224:341 */       this.pid = pid;
/* 225:342 */       this.isProcessGroup = isProcessGroup;
/* 226:343 */       setName(getClass().getName() + "-" + pid);
/* 227:344 */       this.sleepTimeBeforeSigKill = interval;
/* 228:    */     }
/* 229:    */     
/* 230:    */     public void run()
/* 231:    */     {
/* 232:348 */       ProcessTree.sigKillInCurrentThread(this.pid, this.isProcessGroup, this.sleepTimeBeforeSigKill);
/* 233:    */     }
/* 234:    */   }
/* 235:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.util.ProcessTree
 * JD-Core Version:    0.7.0.1
 */