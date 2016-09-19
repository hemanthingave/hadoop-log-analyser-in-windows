/*  1:   */ package org.apache.hadoop.mapred;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import java.net.InetSocketAddress;
/*  5:   */ import org.apache.hadoop.classification.InterfaceAudience.Private;
/*  6:   */ import org.apache.hadoop.classification.InterfaceStability.Unstable;
/*  7:   */ import org.apache.hadoop.conf.Configuration;
/*  8:   */ import org.apache.hadoop.net.NetUtils;
/*  9:   */ import org.apache.hadoop.security.SecurityUtil;
/* 10:   */ 
/* 11:   */ @InterfaceAudience.Private
/* 12:   */ @InterfaceStability.Unstable
/* 13:   */ public class Master
/* 14:   */ {
/* 15:   */   public static enum State
/* 16:   */   {
/* 17:37 */     INITIALIZING,  RUNNING;
/* 18:   */     
/* 19:   */     private State() {}
/* 20:   */   }
/* 21:   */   
/* 22:   */   public static String getMasterUserName(Configuration conf)
/* 23:   */   {
/* 24:41 */     String framework = conf.get("mapreduce.framework.name", "yarn");
/* 25:42 */     if (framework.equals("classic")) {
/* 26:43 */       return conf.get("mapreduce.jobtracker.kerberos.principal");
/* 27:   */     }
/* 28:46 */     return conf.get("yarn.resourcemanager.principal");
/* 29:   */   }
/* 30:   */   
/* 31:   */   public static InetSocketAddress getMasterAddress(Configuration conf)
/* 32:   */   {
/* 33:52 */     String framework = conf.get("mapreduce.framework.name", "yarn");
/* 34:53 */     if (framework.equals("classic"))
/* 35:   */     {
/* 36:54 */       String masterAddress = conf.get("mapreduce.jobtracker.address", "localhost:8012");
/* 37:55 */       return NetUtils.createSocketAddr(masterAddress, 8012, "mapreduce.jobtracker.address");
/* 38:   */     }
/* 39:58 */     return conf.getSocketAddr("yarn.resourcemanager.address", "0.0.0.0:8032", 8032);
/* 40:   */   }
/* 41:   */   
/* 42:   */   public static String getMasterPrincipal(Configuration conf)
/* 43:   */     throws IOException
/* 44:   */   {
/* 45:67 */     String masterHostname = getMasterAddress(conf).getHostName();
/* 46:   */     
/* 47:69 */     return SecurityUtil.getServerPrincipal(getMasterUserName(conf), masterHostname);
/* 48:   */   }
/* 49:   */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.Master
 * JD-Core Version:    0.7.0.1
 */