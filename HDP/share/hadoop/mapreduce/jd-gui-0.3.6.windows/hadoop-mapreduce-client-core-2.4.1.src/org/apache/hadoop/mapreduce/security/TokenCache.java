/*   1:    */ package org.apache.hadoop.mapreduce.security;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.net.URI;
/*   5:    */ import java.util.HashSet;
/*   6:    */ import java.util.Set;
/*   7:    */ import org.apache.commons.logging.Log;
/*   8:    */ import org.apache.commons.logging.LogFactory;
/*   9:    */ import org.apache.hadoop.classification.InterfaceAudience.Private;
/*  10:    */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*  11:    */ import org.apache.hadoop.classification.InterfaceStability.Evolving;
/*  12:    */ import org.apache.hadoop.conf.Configuration;
/*  13:    */ import org.apache.hadoop.fs.FileSystem;
/*  14:    */ import org.apache.hadoop.fs.Path;
/*  15:    */ import org.apache.hadoop.io.Text;
/*  16:    */ import org.apache.hadoop.mapred.JobConf;
/*  17:    */ import org.apache.hadoop.mapred.Master;
/*  18:    */ import org.apache.hadoop.mapreduce.security.token.JobTokenIdentifier;
/*  19:    */ import org.apache.hadoop.security.Credentials;
/*  20:    */ import org.apache.hadoop.security.UserGroupInformation;
/*  21:    */ import org.apache.hadoop.security.token.Token;
/*  22:    */ import org.apache.hadoop.security.token.TokenIdentifier;
/*  23:    */ 
/*  24:    */ @InterfaceAudience.Public
/*  25:    */ @InterfaceStability.Evolving
/*  26:    */ public class TokenCache
/*  27:    */ {
/*  28: 53 */   private static final Log LOG = LogFactory.getLog(TokenCache.class);
/*  29:    */   @InterfaceAudience.Private
/*  30:    */   public static final String JOB_TOKEN_HDFS_FILE = "jobToken";
/*  31:    */   @InterfaceAudience.Private
/*  32:    */   public static final String JOB_TOKENS_FILENAME = "mapreduce.job.jobTokenFile";
/*  33:    */   
/*  34:    */   public static byte[] getSecretKey(Credentials credentials, Text alias)
/*  35:    */   {
/*  36: 62 */     if (credentials == null) {
/*  37: 63 */       return null;
/*  38:    */     }
/*  39: 64 */     return credentials.getSecretKey(alias);
/*  40:    */   }
/*  41:    */   
/*  42:    */   public static void obtainTokensForNamenodes(Credentials credentials, Path[] ps, Configuration conf)
/*  43:    */     throws IOException
/*  44:    */   {
/*  45: 77 */     if (!UserGroupInformation.isSecurityEnabled()) {
/*  46: 78 */       return;
/*  47:    */     }
/*  48: 80 */     obtainTokensForNamenodesInternal(credentials, ps, conf);
/*  49:    */   }
/*  50:    */   
/*  51:    */   public static void cleanUpTokenReferral(Configuration conf)
/*  52:    */   {
/*  53: 90 */     conf.unset("mapreduce.job.credentials.binary");
/*  54:    */   }
/*  55:    */   
/*  56:    */   static void obtainTokensForNamenodesInternal(Credentials credentials, Path[] ps, Configuration conf)
/*  57:    */     throws IOException
/*  58:    */   {
/*  59: 95 */     Set<FileSystem> fsSet = new HashSet();
/*  60: 96 */     for (Path p : ps) {
/*  61: 97 */       fsSet.add(p.getFileSystem(conf));
/*  62:    */     }
/*  63: 99 */     for (FileSystem fs : fsSet) {
/*  64:100 */       obtainTokensForNamenodesInternal(fs, credentials, conf);
/*  65:    */     }
/*  66:    */   }
/*  67:    */   
/*  68:    */   static void obtainTokensForNamenodesInternal(FileSystem fs, Credentials credentials, Configuration conf)
/*  69:    */     throws IOException
/*  70:    */   {
/*  71:114 */     String delegTokenRenewer = Master.getMasterPrincipal(conf);
/*  72:115 */     if ((delegTokenRenewer == null) || (delegTokenRenewer.length() == 0)) {
/*  73:116 */       throw new IOException("Can't get Master Kerberos principal for use as renewer");
/*  74:    */     }
/*  75:119 */     mergeBinaryTokens(credentials, conf);
/*  76:    */     
/*  77:121 */     Token<?>[] tokens = fs.addDelegationTokens(delegTokenRenewer, credentials);
/*  78:123 */     if (tokens != null) {
/*  79:124 */       for (Token<?> token : tokens) {
/*  80:125 */         LOG.info("Got dt for " + fs.getUri() + "; " + token);
/*  81:    */       }
/*  82:    */     }
/*  83:    */   }
/*  84:    */   
/*  85:    */   private static void mergeBinaryTokens(Credentials creds, Configuration conf)
/*  86:    */   {
/*  87:131 */     String binaryTokenFilename = conf.get("mapreduce.job.credentials.binary");
/*  88:133 */     if (binaryTokenFilename != null)
/*  89:    */     {
/*  90:    */       Credentials binary;
/*  91:    */       try
/*  92:    */       {
/*  93:136 */         binary = Credentials.readTokenStorageFile(new Path("file:///" + binaryTokenFilename), conf);
/*  94:    */       }
/*  95:    */       catch (IOException e)
/*  96:    */       {
/*  97:139 */         throw new RuntimeException(e);
/*  98:    */       }
/*  99:142 */       creds.mergeAll(binary);
/* 100:    */     }
/* 101:    */   }
/* 102:    */   
/* 103:157 */   private static final Text JOB_TOKEN = new Text("JobToken");
/* 104:158 */   private static final Text SHUFFLE_TOKEN = new Text("MapReduceShuffleToken");
/* 105:    */   
/* 106:    */   @InterfaceAudience.Private
/* 107:    */   @Deprecated
/* 108:    */   public static Credentials loadTokens(String jobTokenFile, JobConf conf)
/* 109:    */     throws IOException
/* 110:    */   {
/* 111:171 */     Path localJobTokenFile = new Path("file:///" + jobTokenFile);
/* 112:    */     
/* 113:173 */     Credentials ts = Credentials.readTokenStorageFile(localJobTokenFile, conf);
/* 114:175 */     if (LOG.isDebugEnabled()) {
/* 115:176 */       LOG.debug("Task: Loaded jobTokenFile from: " + localJobTokenFile.toUri().getPath() + "; num of sec keys  = " + ts.numberOfSecretKeys() + " Number of tokens " + ts.numberOfTokens());
/* 116:    */     }
/* 117:181 */     return ts;
/* 118:    */   }
/* 119:    */   
/* 120:    */   @InterfaceAudience.Private
/* 121:    */   @Deprecated
/* 122:    */   public static Credentials loadTokens(String jobTokenFile, Configuration conf)
/* 123:    */     throws IOException
/* 124:    */   {
/* 125:195 */     return loadTokens(jobTokenFile, new JobConf(conf));
/* 126:    */   }
/* 127:    */   
/* 128:    */   @InterfaceAudience.Private
/* 129:    */   public static void setJobToken(Token<? extends TokenIdentifier> t, Credentials credentials)
/* 130:    */   {
/* 131:205 */     credentials.addToken(JOB_TOKEN, t);
/* 132:    */   }
/* 133:    */   
/* 134:    */   @InterfaceAudience.Private
/* 135:    */   public static Token<JobTokenIdentifier> getJobToken(Credentials credentials)
/* 136:    */   {
/* 137:214 */     return credentials.getToken(JOB_TOKEN);
/* 138:    */   }
/* 139:    */   
/* 140:    */   @InterfaceAudience.Private
/* 141:    */   public static void setShuffleSecretKey(byte[] key, Credentials credentials)
/* 142:    */   {
/* 143:219 */     credentials.addSecretKey(SHUFFLE_TOKEN, key);
/* 144:    */   }
/* 145:    */   
/* 146:    */   @InterfaceAudience.Private
/* 147:    */   public static byte[] getShuffleSecretKey(Credentials credentials)
/* 148:    */   {
/* 149:224 */     return getSecretKey(credentials, SHUFFLE_TOKEN);
/* 150:    */   }
/* 151:    */   
/* 152:    */   @InterfaceAudience.Private
/* 153:    */   @Deprecated
/* 154:    */   public static Token<?> getDelegationToken(Credentials credentials, String namenode)
/* 155:    */   {
/* 156:238 */     return credentials.getToken(new Text(namenode));
/* 157:    */   }
/* 158:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.security.TokenCache
 * JD-Core Version:    0.7.0.1
 */