/*   1:    */ package org.apache.hadoop.mapreduce.v2.util;
/*   2:    */ 
/*   3:    */ import com.google.common.base.Joiner;
/*   4:    */ import com.google.common.base.Splitter;
/*   5:    */ import java.net.InetAddress;
/*   6:    */ import java.net.InetSocketAddress;
/*   7:    */ import java.net.UnknownHostException;
/*   8:    */ import java.util.Iterator;
/*   9:    */ import org.apache.hadoop.classification.InterfaceAudience.Private;
/*  10:    */ import org.apache.hadoop.classification.InterfaceStability.Evolving;
/*  11:    */ import org.apache.hadoop.conf.Configuration;
/*  12:    */ import org.apache.hadoop.http.HttpConfig.Policy;
/*  13:    */ import org.apache.hadoop.mapreduce.JobID;
/*  14:    */ import org.apache.hadoop.mapreduce.TypeConverter;
/*  15:    */ import org.apache.hadoop.mapreduce.v2.jobhistory.JHAdminConfig;
/*  16:    */ import org.apache.hadoop.net.NetUtils;
/*  17:    */ import org.apache.hadoop.yarn.api.records.ApplicationId;
/*  18:    */ import org.apache.hadoop.yarn.conf.YarnConfiguration;
/*  19:    */ 
/*  20:    */ @InterfaceAudience.Private
/*  21:    */ @InterfaceStability.Evolving
/*  22:    */ public class MRWebAppUtil
/*  23:    */ {
/*  24: 43 */   private static final Splitter ADDR_SPLITTER = Splitter.on(':').trimResults();
/*  25: 44 */   private static final Joiner JOINER = Joiner.on("");
/*  26:    */   private static HttpConfig.Policy httpPolicyInYarn;
/*  27:    */   private static HttpConfig.Policy httpPolicyInJHS;
/*  28:    */   
/*  29:    */   public static void initialize(Configuration conf)
/*  30:    */   {
/*  31: 50 */     setHttpPolicyInYARN(conf.get("yarn.http.policy", YarnConfiguration.YARN_HTTP_POLICY_DEFAULT));
/*  32:    */     
/*  33:    */ 
/*  34: 53 */     setHttpPolicyInJHS(conf.get("mapreduce.jobhistory.http.policy", JHAdminConfig.DEFAULT_MR_HS_HTTP_POLICY));
/*  35:    */   }
/*  36:    */   
/*  37:    */   private static void setHttpPolicyInJHS(String policy)
/*  38:    */   {
/*  39: 58 */     httpPolicyInJHS = HttpConfig.Policy.fromString(policy);
/*  40:    */   }
/*  41:    */   
/*  42:    */   private static void setHttpPolicyInYARN(String policy)
/*  43:    */   {
/*  44: 62 */     httpPolicyInYarn = HttpConfig.Policy.fromString(policy);
/*  45:    */   }
/*  46:    */   
/*  47:    */   public static HttpConfig.Policy getJHSHttpPolicy()
/*  48:    */   {
/*  49: 66 */     return httpPolicyInJHS;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public static HttpConfig.Policy getYARNHttpPolicy()
/*  53:    */   {
/*  54: 70 */     return httpPolicyInYarn;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public static String getYARNWebappScheme()
/*  58:    */   {
/*  59: 74 */     return httpPolicyInYarn == HttpConfig.Policy.HTTPS_ONLY ? "https://" : "http://";
/*  60:    */   }
/*  61:    */   
/*  62:    */   public static String getJHSWebappScheme()
/*  63:    */   {
/*  64: 79 */     return httpPolicyInJHS == HttpConfig.Policy.HTTPS_ONLY ? "https://" : "http://";
/*  65:    */   }
/*  66:    */   
/*  67:    */   public static void setJHSWebappURLWithoutScheme(Configuration conf, String hostAddress)
/*  68:    */   {
/*  69: 85 */     if (httpPolicyInJHS == HttpConfig.Policy.HTTPS_ONLY) {
/*  70: 86 */       conf.set("mapreduce.jobhistory.webapp.https.address", hostAddress);
/*  71:    */     } else {
/*  72: 88 */       conf.set("mapreduce.jobhistory.webapp.address", hostAddress);
/*  73:    */     }
/*  74:    */   }
/*  75:    */   
/*  76:    */   public static String getJHSWebappURLWithoutScheme(Configuration conf)
/*  77:    */   {
/*  78: 93 */     if (httpPolicyInJHS == HttpConfig.Policy.HTTPS_ONLY) {
/*  79: 94 */       return conf.get("mapreduce.jobhistory.webapp.https.address", "0.0.0.0:19890");
/*  80:    */     }
/*  81: 97 */     return conf.get("mapreduce.jobhistory.webapp.address", "0.0.0.0:19888");
/*  82:    */   }
/*  83:    */   
/*  84:    */   public static String getJHSWebappURLWithScheme(Configuration conf)
/*  85:    */   {
/*  86:103 */     return getJHSWebappScheme() + getJHSWebappURLWithoutScheme(conf);
/*  87:    */   }
/*  88:    */   
/*  89:    */   public static InetSocketAddress getJHSWebBindAddress(Configuration conf)
/*  90:    */   {
/*  91:107 */     if (httpPolicyInJHS == HttpConfig.Policy.HTTPS_ONLY) {
/*  92:108 */       return conf.getSocketAddr("mapreduce.jobhistory.webapp.https.address", "0.0.0.0:19890", 19890);
/*  93:    */     }
/*  94:112 */     return conf.getSocketAddr("mapreduce.jobhistory.webapp.address", "0.0.0.0:19888", 19888);
/*  95:    */   }
/*  96:    */   
/*  97:    */   public static String getApplicationWebURLOnJHSWithoutScheme(Configuration conf, ApplicationId appId)
/*  98:    */     throws UnknownHostException
/*  99:    */   {
/* 100:122 */     String addr = getJHSWebappURLWithoutScheme(conf);
/* 101:123 */     Iterator<String> it = ADDR_SPLITTER.split(addr).iterator();
/* 102:124 */     it.next();
/* 103:125 */     String port = (String)it.next();
/* 104:    */     
/* 105:127 */     addr = conf.get("mapreduce.jobhistory.address", "0.0.0.0:10020");
/* 106:    */     
/* 107:129 */     String host = (String)ADDR_SPLITTER.split(addr).iterator().next();
/* 108:130 */     String hsAddress = JOINER.join(host, ":", new Object[] { port });
/* 109:131 */     InetSocketAddress address = NetUtils.createSocketAddr(hsAddress, getDefaultJHSWebappPort(), getDefaultJHSWebappURLWithoutScheme());
/* 110:    */     
/* 111:    */ 
/* 112:134 */     StringBuffer sb = new StringBuffer();
/* 113:135 */     if ((address.getAddress().isAnyLocalAddress()) || (address.getAddress().isLoopbackAddress())) {
/* 114:137 */       sb.append(InetAddress.getLocalHost().getCanonicalHostName());
/* 115:    */     } else {
/* 116:139 */       sb.append(address.getHostName());
/* 117:    */     }
/* 118:141 */     sb.append(":").append(address.getPort());
/* 119:142 */     sb.append("/jobhistory/job/");
/* 120:143 */     JobID jobId = TypeConverter.fromYarn(appId);
/* 121:144 */     sb.append(jobId.toString());
/* 122:145 */     return sb.toString();
/* 123:    */   }
/* 124:    */   
/* 125:    */   public static String getApplicationWebURLOnJHSWithScheme(Configuration conf, ApplicationId appId)
/* 126:    */     throws UnknownHostException
/* 127:    */   {
/* 128:150 */     return getJHSWebappScheme() + getApplicationWebURLOnJHSWithoutScheme(conf, appId);
/* 129:    */   }
/* 130:    */   
/* 131:    */   private static int getDefaultJHSWebappPort()
/* 132:    */   {
/* 133:155 */     return httpPolicyInJHS == HttpConfig.Policy.HTTPS_ONLY ? 19890 : 19888;
/* 134:    */   }
/* 135:    */   
/* 136:    */   private static String getDefaultJHSWebappURLWithoutScheme()
/* 137:    */   {
/* 138:161 */     return httpPolicyInJHS == HttpConfig.Policy.HTTPS_ONLY ? "0.0.0.0:19890" : "0.0.0.0:19888";
/* 139:    */   }
/* 140:    */   
/* 141:    */   public static String getAMWebappScheme(Configuration conf)
/* 142:    */   {
/* 143:167 */     return "http://";
/* 144:    */   }
/* 145:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-common-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.v2.util.MRWebAppUtil
 * JD-Core Version:    0.7.0.1
 */