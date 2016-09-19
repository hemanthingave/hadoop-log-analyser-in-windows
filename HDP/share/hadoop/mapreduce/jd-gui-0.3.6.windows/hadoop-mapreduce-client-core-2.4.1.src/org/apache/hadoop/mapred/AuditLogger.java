/*   1:    */ package org.apache.hadoop.mapred;
/*   2:    */ 
/*   3:    */ import java.net.InetAddress;
/*   4:    */ import org.apache.commons.logging.Log;
/*   5:    */ import org.apache.commons.logging.LogFactory;
/*   6:    */ import org.apache.hadoop.ipc.Server;
/*   7:    */ 
/*   8:    */ class AuditLogger
/*   9:    */ {
/*  10: 32 */   private static final Log LOG = LogFactory.getLog(AuditLogger.class);
/*  11:    */   
/*  12:    */   static class Constants
/*  13:    */   {
/*  14:    */     static final String SUCCESS = "SUCCESS";
/*  15:    */     static final String FAILURE = "FAILURE";
/*  16:    */     static final String KEY_VAL_SEPARATOR = "=";
/*  17:    */     static final char PAIR_SEPARATOR = '\t';
/*  18:    */     static final String JOBTRACKER = "JobTracker";
/*  19:    */     static final String REFRESH_QUEUE = "REFRESH_QUEUE";
/*  20:    */     static final String REFRESH_NODES = "REFRESH_NODES";
/*  21:    */     static final String UNAUTHORIZED_USER = "Unauthorized user";
/*  22:    */   }
/*  23:    */   
/*  24:    */   static enum Keys
/*  25:    */   {
/*  26: 34 */     USER,  OPERATION,  TARGET,  RESULT,  IP,  PERMISSIONS,  DESCRIPTION;
/*  27:    */     
/*  28:    */     private Keys() {}
/*  29:    */   }
/*  30:    */   
/*  31:    */   static String createSuccessLog(String user, String operation, String target)
/*  32:    */   {
/*  33: 61 */     StringBuilder b = new StringBuilder();
/*  34: 62 */     start(Keys.USER, user, b);
/*  35: 63 */     addRemoteIP(b);
/*  36: 64 */     add(Keys.OPERATION, operation, b);
/*  37: 65 */     add(Keys.TARGET, target, b);
/*  38: 66 */     add(Keys.RESULT, "SUCCESS", b);
/*  39: 67 */     return b.toString();
/*  40:    */   }
/*  41:    */   
/*  42:    */   static void logSuccess(String user, String operation, String target)
/*  43:    */   {
/*  44: 83 */     if (LOG.isInfoEnabled()) {
/*  45: 84 */       LOG.info(createSuccessLog(user, operation, target));
/*  46:    */     }
/*  47:    */   }
/*  48:    */   
/*  49:    */   static String createFailureLog(String user, String operation, String perm, String target, String description)
/*  50:    */   {
/*  51: 94 */     StringBuilder b = new StringBuilder();
/*  52: 95 */     start(Keys.USER, user, b);
/*  53: 96 */     addRemoteIP(b);
/*  54: 97 */     add(Keys.OPERATION, operation, b);
/*  55: 98 */     add(Keys.TARGET, target, b);
/*  56: 99 */     add(Keys.RESULT, "FAILURE", b);
/*  57:100 */     add(Keys.DESCRIPTION, description, b);
/*  58:101 */     add(Keys.PERMISSIONS, perm, b);
/*  59:102 */     return b.toString();
/*  60:    */   }
/*  61:    */   
/*  62:    */   static void logFailure(String user, String operation, String perm, String target, String description)
/*  63:    */   {
/*  64:122 */     if (LOG.isWarnEnabled()) {
/*  65:123 */       LOG.warn(createFailureLog(user, operation, perm, target, description));
/*  66:    */     }
/*  67:    */   }
/*  68:    */   
/*  69:    */   static void addRemoteIP(StringBuilder b)
/*  70:    */   {
/*  71:131 */     InetAddress ip = Server.getRemoteIp();
/*  72:133 */     if (ip != null) {
/*  73:134 */       add(Keys.IP, ip.getHostAddress(), b);
/*  74:    */     }
/*  75:    */   }
/*  76:    */   
/*  77:    */   static void start(Keys key, String value, StringBuilder b)
/*  78:    */   {
/*  79:143 */     b.append(key.name()).append("=").append(value);
/*  80:    */   }
/*  81:    */   
/*  82:    */   static void add(Keys key, String value, StringBuilder b)
/*  83:    */   {
/*  84:151 */     b.append('\t').append(key.name()).append("=").append(value);
/*  85:    */   }
/*  86:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.AuditLogger
 * JD-Core Version:    0.7.0.1
 */