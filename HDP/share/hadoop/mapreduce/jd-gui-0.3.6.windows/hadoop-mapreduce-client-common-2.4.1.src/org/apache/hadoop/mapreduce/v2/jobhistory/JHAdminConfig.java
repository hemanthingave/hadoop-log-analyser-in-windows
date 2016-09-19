/*   1:    */ package org.apache.hadoop.mapreduce.v2.jobhistory;
/*   2:    */ 
/*   3:    */ import org.apache.hadoop.classification.InterfaceAudience.Private;
/*   4:    */ import org.apache.hadoop.classification.InterfaceStability.Evolving;
/*   5:    */ import org.apache.hadoop.http.HttpConfig.Policy;
/*   6:    */ 
/*   7:    */ @InterfaceAudience.Private
/*   8:    */ @InterfaceStability.Evolving
/*   9:    */ public class JHAdminConfig
/*  10:    */ {
/*  11:    */   public static final String MR_HISTORY_PREFIX = "mapreduce.jobhistory.";
/*  12:    */   public static final String MR_HISTORY_ADDRESS = "mapreduce.jobhistory.address";
/*  13:    */   public static final int DEFAULT_MR_HISTORY_PORT = 10020;
/*  14:    */   public static final String DEFAULT_MR_HISTORY_ADDRESS = "0.0.0.0:10020";
/*  15:    */   public static final String JHS_ADMIN_ADDRESS = "mapreduce.jobhistory.admin.address";
/*  16:    */   public static final int DEFAULT_JHS_ADMIN_PORT = 10033;
/*  17:    */   public static final String DEFAULT_JHS_ADMIN_ADDRESS = "0.0.0.0:10033";
/*  18:    */   public static final String JHS_ADMIN_ACL = "mapreduce.jobhistory.admin.acl";
/*  19:    */   public static final String DEFAULT_JHS_ADMIN_ACL = "*";
/*  20:    */   public static final String MR_HISTORY_CLEANER_ENABLE = "mapreduce.jobhistory.cleaner.enable";
/*  21:    */   public static final String MR_HISTORY_CLEANER_INTERVAL_MS = "mapreduce.jobhistory.cleaner.interval-ms";
/*  22:    */   public static final long DEFAULT_MR_HISTORY_CLEANER_INTERVAL_MS = 86400000L;
/*  23:    */   public static final String MR_HISTORY_CLIENT_THREAD_COUNT = "mapreduce.jobhistory.client.thread-count";
/*  24:    */   public static final int DEFAULT_MR_HISTORY_CLIENT_THREAD_COUNT = 10;
/*  25:    */   public static final String MR_HISTORY_DATESTRING_CACHE_SIZE = "mapreduce.jobhistory.datestring.cache.size";
/*  26:    */   public static final int DEFAULT_MR_HISTORY_DATESTRING_CACHE_SIZE = 200000;
/*  27:    */   public static final String MR_HISTORY_DONE_DIR = "mapreduce.jobhistory.done-dir";
/*  28:    */   public static final String MR_HISTORY_MAX_START_WAIT_TIME = "mapreduce.jobhistory.maximum-start-wait-time-millis";
/*  29:    */   public static final long DEFAULT_MR_HISTORY_MAX_START_WAIT_TIME = -1L;
/*  30:    */   public static final String MR_HISTORY_INTERMEDIATE_DONE_DIR = "mapreduce.jobhistory.intermediate-done-dir";
/*  31:    */   public static final String MR_HISTORY_JOBLIST_CACHE_SIZE = "mapreduce.jobhistory.joblist.cache.size";
/*  32:    */   public static final int DEFAULT_MR_HISTORY_JOBLIST_CACHE_SIZE = 20000;
/*  33:    */   public static final String MR_HISTORY_KEYTAB = "mapreduce.jobhistory.keytab";
/*  34:    */   public static final String MR_HISTORY_LOADED_JOB_CACHE_SIZE = "mapreduce.jobhistory.loadedjobs.cache.size";
/*  35:    */   public static final int DEFAULT_MR_HISTORY_LOADED_JOB_CACHE_SIZE = 5;
/*  36:    */   public static final String MR_HISTORY_MAX_AGE_MS = "mapreduce.jobhistory.max-age-ms";
/*  37:    */   public static final long DEFAULT_MR_HISTORY_MAX_AGE = 604800000L;
/*  38:    */   public static final String MR_HISTORY_MOVE_INTERVAL_MS = "mapreduce.jobhistory.move.interval-ms";
/*  39:    */   public static final long DEFAULT_MR_HISTORY_MOVE_INTERVAL_MS = 180000L;
/*  40:    */   public static final String MR_HISTORY_MOVE_THREAD_COUNT = "mapreduce.jobhistory.move.thread-count";
/*  41:    */   public static final int DEFAULT_MR_HISTORY_MOVE_THREAD_COUNT = 3;
/*  42:    */   public static final String MR_HISTORY_PRINCIPAL = "mapreduce.jobhistory.principal";
/*  43:    */   public static final String MR_HS_HTTP_POLICY = "mapreduce.jobhistory.http.policy";
/*  44:138 */   public static String DEFAULT_MR_HS_HTTP_POLICY = HttpConfig.Policy.HTTP_ONLY.name();
/*  45:    */   public static final String MR_HISTORY_WEBAPP_ADDRESS = "mapreduce.jobhistory.webapp.address";
/*  46:    */   public static final int DEFAULT_MR_HISTORY_WEBAPP_PORT = 19888;
/*  47:    */   public static final String DEFAULT_MR_HISTORY_WEBAPP_ADDRESS = "0.0.0.0:19888";
/*  48:    */   public static final String MR_HISTORY_WEBAPP_HTTPS_ADDRESS = "mapreduce.jobhistory.webapp.https.address";
/*  49:    */   public static final int DEFAULT_MR_HISTORY_WEBAPP_HTTPS_PORT = 19890;
/*  50:    */   public static final String DEFAULT_MR_HISTORY_WEBAPP_HTTPS_ADDRESS = "0.0.0.0:19890";
/*  51:    */   public static final String MR_WEBAPP_SPNEGO_USER_NAME_KEY = "mapreduce.jobhistory.webapp.spnego-principal";
/*  52:    */   public static final String MR_WEBAPP_SPNEGO_KEYTAB_FILE_KEY = "mapreduce.jobhistory.webapp.spnego-keytab-file";
/*  53:    */   public static final String MR_HS_SECURITY_SERVICE_AUTHORIZATION = "security.mrhs.client.protocol.acl";
/*  54:    */   public static final String MR_HISTORY_STORAGE = "mapreduce.jobhistory.store.class";
/*  55:    */   public static final String MR_HS_RECOVERY_ENABLE = "mapreduce.jobhistory.recovery.enable";
/*  56:    */   public static final boolean DEFAULT_MR_HS_RECOVERY_ENABLE = false;
/*  57:    */   public static final String MR_HS_STATE_STORE = "mapreduce.jobhistory.recovery.store.class";
/*  58:    */   public static final String MR_HS_FS_STATE_STORE_URI = "mapreduce.jobhistory.recovery.store.fs.uri";
/*  59:    */   public static final String MR_HISTORY_MINICLUSTER_FIXED_PORTS = "mapreduce.jobhistory.minicluster.fixed.ports";
/*  60:204 */   public static boolean DEFAULT_MR_HISTORY_MINICLUSTER_FIXED_PORTS = false;
/*  61:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-common-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.v2.jobhistory.JHAdminConfig
 * JD-Core Version:    0.7.0.1
 */