/*   1:    */ package org.apache.hadoop.mapred.pipes;
/*   2:    */ 
/*   3:    */ import java.io.File;
/*   4:    */ import java.io.IOException;
/*   5:    */ import java.net.ServerSocket;
/*   6:    */ import java.net.Socket;
/*   7:    */ import java.util.ArrayList;
/*   8:    */ import java.util.HashMap;
/*   9:    */ import java.util.List;
/*  10:    */ import java.util.Map;
/*  11:    */ import java.util.Random;
/*  12:    */ import javax.crypto.SecretKey;
/*  13:    */ import org.apache.commons.logging.Log;
/*  14:    */ import org.apache.commons.logging.LogFactory;
/*  15:    */ import org.apache.hadoop.fs.FSDataOutputStream;
/*  16:    */ import org.apache.hadoop.fs.FileSystem;
/*  17:    */ import org.apache.hadoop.fs.FileUtil;
/*  18:    */ import org.apache.hadoop.fs.Path;
/*  19:    */ import org.apache.hadoop.fs.permission.FsPermission;
/*  20:    */ import org.apache.hadoop.io.FloatWritable;
/*  21:    */ import org.apache.hadoop.io.NullWritable;
/*  22:    */ import org.apache.hadoop.io.Writable;
/*  23:    */ import org.apache.hadoop.io.WritableComparable;
/*  24:    */ import org.apache.hadoop.mapred.JobConf;
/*  25:    */ import org.apache.hadoop.mapred.OutputCollector;
/*  26:    */ import org.apache.hadoop.mapred.RecordReader;
/*  27:    */ import org.apache.hadoop.mapred.Reporter;
/*  28:    */ import org.apache.hadoop.mapred.TaskAttemptID;
/*  29:    */ import org.apache.hadoop.mapred.TaskLog;
/*  30:    */ import org.apache.hadoop.mapred.TaskLog.LogName;
/*  31:    */ import org.apache.hadoop.mapreduce.security.SecureShuffleUtils;
/*  32:    */ import org.apache.hadoop.mapreduce.security.TokenCache;
/*  33:    */ import org.apache.hadoop.mapreduce.security.token.JobTokenIdentifier;
/*  34:    */ import org.apache.hadoop.mapreduce.security.token.JobTokenSecretManager;
/*  35:    */ import org.apache.hadoop.security.token.Token;
/*  36:    */ import org.apache.hadoop.util.ReflectionUtils;
/*  37:    */ import org.apache.hadoop.util.StringUtils;
/*  38:    */ 
/*  39:    */ class Application<K1 extends WritableComparable, V1 extends Writable, K2 extends WritableComparable, V2 extends Writable>
/*  40:    */ {
/*  41: 66 */   private static final Log LOG = LogFactory.getLog(Application.class.getName());
/*  42:    */   private ServerSocket serverSocket;
/*  43:    */   private Process process;
/*  44:    */   private Socket clientSocket;
/*  45:    */   private OutputHandler<K2, V2> handler;
/*  46:    */   private DownwardProtocol<K1, V1> downlink;
/*  47: 72 */   static final boolean WINDOWS = System.getProperty("os.name").startsWith("Windows");
/*  48:    */   
/*  49:    */   Application(JobConf conf, RecordReader<FloatWritable, NullWritable> recordReader, OutputCollector<K2, V2> output, Reporter reporter, Class<? extends K2> outputKeyClass, Class<? extends V2> outputValueClass)
/*  50:    */     throws IOException, InterruptedException
/*  51:    */   {
/*  52: 92 */     this.serverSocket = new ServerSocket(0);
/*  53: 93 */     Map<String, String> env = new HashMap();
/*  54:    */     
/*  55: 95 */     env.put("TMPDIR", System.getProperty("java.io.tmpdir"));
/*  56: 96 */     env.put("mapreduce.pipes.command.port", Integer.toString(this.serverSocket.getLocalPort()));
/*  57:    */     
/*  58:    */ 
/*  59:    */ 
/*  60:100 */     Token<JobTokenIdentifier> jobToken = TokenCache.getJobToken(conf.getCredentials());
/*  61:    */     
/*  62:    */ 
/*  63:    */ 
/*  64:104 */     byte[] password = jobToken.getPassword();
/*  65:105 */     String localPasswordFile = new File(".") + "/" + "jobTokenPassword";
/*  66:    */     
/*  67:107 */     writePasswordToLocalFile(localPasswordFile, password, conf);
/*  68:108 */     env.put("hadoop.pipes.shared.secret.location", localPasswordFile);
/*  69:    */     
/*  70:110 */     List<String> cmd = new ArrayList();
/*  71:111 */     String interpretor = conf.get("mapreduce.pipes.executable.interpretor");
/*  72:112 */     if (interpretor != null) {
/*  73:113 */       cmd.add(interpretor);
/*  74:    */     }
/*  75:115 */     String executable = org.apache.hadoop.mapreduce.filecache.DistributedCache.getLocalCacheFiles(conf)[0].toString();
/*  76:116 */     if (!FileUtil.canExecute(new File(executable))) {
/*  77:119 */       FileUtil.chmod(executable, "u+x");
/*  78:    */     }
/*  79:121 */     cmd.add(executable);
/*  80:    */     
/*  81:    */ 
/*  82:    */ 
/*  83:125 */     TaskAttemptID taskid = TaskAttemptID.forName(conf.get("mapreduce.task.attempt.id"));
/*  84:    */     
/*  85:127 */     File stdout = TaskLog.getTaskLogFile(taskid, false, TaskLog.LogName.STDOUT);
/*  86:128 */     File stderr = TaskLog.getTaskLogFile(taskid, false, TaskLog.LogName.STDERR);
/*  87:129 */     long logLength = TaskLog.getTaskLogLength(conf);
/*  88:130 */     cmd = TaskLog.captureOutAndError(null, cmd, stdout, stderr, logLength, false);
/*  89:    */     
/*  90:    */ 
/*  91:133 */     this.process = runClient(cmd, env);
/*  92:134 */     this.clientSocket = this.serverSocket.accept();
/*  93:    */     
/*  94:136 */     String challenge = getSecurityChallenge();
/*  95:137 */     String digestToSend = createDigest(password, challenge);
/*  96:138 */     String digestExpected = createDigest(password, digestToSend);
/*  97:    */     
/*  98:140 */     this.handler = new OutputHandler(output, reporter, recordReader, digestExpected);
/*  99:    */     
/* 100:142 */     K2 outputKey = (WritableComparable)ReflectionUtils.newInstance(outputKeyClass, conf);
/* 101:    */     
/* 102:144 */     V2 outputValue = (Writable)ReflectionUtils.newInstance(outputValueClass, conf);
/* 103:    */     
/* 104:146 */     this.downlink = new BinaryProtocol(this.clientSocket, this.handler, outputKey, outputValue, conf);
/* 105:    */     
/* 106:    */ 
/* 107:149 */     this.downlink.authenticate(digestToSend, challenge);
/* 108:150 */     waitForAuthentication();
/* 109:151 */     LOG.debug("Authentication succeeded");
/* 110:152 */     this.downlink.start();
/* 111:153 */     this.downlink.setJobConf(conf);
/* 112:    */   }
/* 113:    */   
/* 114:    */   private String getSecurityChallenge()
/* 115:    */   {
/* 116:157 */     Random rand = new Random(System.currentTimeMillis());
/* 117:    */     
/* 118:159 */     StringBuilder strBuilder = new StringBuilder();
/* 119:160 */     strBuilder.append(rand.nextInt(2147483647));
/* 120:161 */     strBuilder.append(rand.nextInt(2147483647));
/* 121:162 */     strBuilder.append(rand.nextInt(2147483647));
/* 122:163 */     strBuilder.append(rand.nextInt(2147483647));
/* 123:164 */     return strBuilder.toString();
/* 124:    */   }
/* 125:    */   
/* 126:    */   private void writePasswordToLocalFile(String localPasswordFile, byte[] password, JobConf conf)
/* 127:    */     throws IOException
/* 128:    */   {
/* 129:169 */     FileSystem localFs = FileSystem.getLocal(conf);
/* 130:170 */     Path localPath = new Path(localPasswordFile);
/* 131:171 */     FSDataOutputStream out = FileSystem.create(localFs, localPath, new FsPermission("400"));
/* 132:    */     
/* 133:173 */     out.write(password);
/* 134:174 */     out.close();
/* 135:    */   }
/* 136:    */   
/* 137:    */   DownwardProtocol<K1, V1> getDownlink()
/* 138:    */   {
/* 139:183 */     return this.downlink;
/* 140:    */   }
/* 141:    */   
/* 142:    */   void waitForAuthentication()
/* 143:    */     throws IOException, InterruptedException
/* 144:    */   {
/* 145:193 */     this.downlink.flush();
/* 146:194 */     LOG.debug("Waiting for authentication response");
/* 147:195 */     this.handler.waitForAuthentication();
/* 148:    */   }
/* 149:    */   
/* 150:    */   boolean waitForFinish()
/* 151:    */     throws Throwable
/* 152:    */   {
/* 153:204 */     this.downlink.flush();
/* 154:205 */     return this.handler.waitForFinish();
/* 155:    */   }
/* 156:    */   
/* 157:    */   void abort(Throwable t)
/* 158:    */     throws IOException
/* 159:    */   {
/* 160:214 */     LOG.info("Aborting because of " + StringUtils.stringifyException(t));
/* 161:    */     try
/* 162:    */     {
/* 163:216 */       this.downlink.abort();
/* 164:217 */       this.downlink.flush();
/* 165:    */     }
/* 166:    */     catch (IOException e) {}
/* 167:    */     try
/* 168:    */     {
/* 169:222 */       this.handler.waitForFinish();
/* 170:    */     }
/* 171:    */     catch (Throwable ignored)
/* 172:    */     {
/* 173:224 */       this.process.destroy();
/* 174:    */     }
/* 175:226 */     IOException wrapper = new IOException("pipe child exception");
/* 176:227 */     wrapper.initCause(t);
/* 177:228 */     throw wrapper;
/* 178:    */   }
/* 179:    */   
/* 180:    */   void cleanup()
/* 181:    */     throws IOException
/* 182:    */   {
/* 183:236 */     this.serverSocket.close();
/* 184:    */     try
/* 185:    */     {
/* 186:238 */       this.downlink.close();
/* 187:    */     }
/* 188:    */     catch (InterruptedException ie)
/* 189:    */     {
/* 190:240 */       Thread.currentThread().interrupt();
/* 191:    */     }
/* 192:    */   }
/* 193:    */   
/* 194:    */   static Process runClient(List<String> command, Map<String, String> env)
/* 195:    */     throws IOException
/* 196:    */   {
/* 197:254 */     ProcessBuilder builder = new ProcessBuilder(command);
/* 198:255 */     if (env != null) {
/* 199:256 */       builder.environment().putAll(env);
/* 200:    */     }
/* 201:258 */     Process result = builder.start();
/* 202:259 */     return result;
/* 203:    */   }
/* 204:    */   
/* 205:    */   public static String createDigest(byte[] password, String data)
/* 206:    */     throws IOException
/* 207:    */   {
/* 208:264 */     SecretKey key = JobTokenSecretManager.createSecretKey(password);
/* 209:265 */     return SecureShuffleUtils.hashFromString(data, key);
/* 210:    */   }
/* 211:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.pipes.Application
 * JD-Core Version:    0.7.0.1
 */