/*   1:    */ package org.apache.hadoop.mapred;
/*   2:    */ 
/*   3:    */ import java.util.HashMap;
/*   4:    */ import java.util.Map;
/*   5:    */ import org.apache.commons.logging.Log;
/*   6:    */ import org.apache.commons.logging.LogFactory;
/*   7:    */ import org.apache.hadoop.classification.InterfaceAudience.Private;
/*   8:    */ import org.apache.hadoop.conf.Configuration;
/*   9:    */ import org.apache.hadoop.mapreduce.JobACL;
/*  10:    */ import org.apache.hadoop.security.UserGroupInformation;
/*  11:    */ import org.apache.hadoop.security.authorize.AccessControlList;
/*  12:    */ 
/*  13:    */ @InterfaceAudience.Private
/*  14:    */ public class JobACLsManager
/*  15:    */ {
/*  16: 36 */   static final Log LOG = LogFactory.getLog(JobACLsManager.class);
/*  17:    */   Configuration conf;
/*  18:    */   private final AccessControlList adminAcl;
/*  19:    */   
/*  20:    */   public JobACLsManager(Configuration conf)
/*  21:    */   {
/*  22: 41 */     this.adminAcl = new AccessControlList(conf.get("mapreduce.cluster.administrators", " "));
/*  23: 42 */     this.conf = conf;
/*  24:    */   }
/*  25:    */   
/*  26:    */   public boolean areACLsEnabled()
/*  27:    */   {
/*  28: 46 */     return this.conf.getBoolean("mapreduce.cluster.acls.enabled", false);
/*  29:    */   }
/*  30:    */   
/*  31:    */   public Map<JobACL, AccessControlList> constructJobACLs(Configuration conf)
/*  32:    */   {
/*  33: 58 */     Map<JobACL, AccessControlList> acls = new HashMap();
/*  34: 62 */     if (!areACLsEnabled()) {
/*  35: 63 */       return acls;
/*  36:    */     }
/*  37: 66 */     for (JobACL aclName : JobACL.values())
/*  38:    */     {
/*  39: 67 */       String aclConfigName = aclName.getAclName();
/*  40: 68 */       String aclConfigured = conf.get(aclConfigName);
/*  41: 69 */       if (aclConfigured == null) {
/*  42: 72 */         aclConfigured = " ";
/*  43:    */       }
/*  44: 74 */       acls.put(aclName, new AccessControlList(aclConfigured));
/*  45:    */     }
/*  46: 76 */     return acls;
/*  47:    */   }
/*  48:    */   
/*  49:    */   boolean isMRAdmin(UserGroupInformation callerUGI)
/*  50:    */   {
/*  51: 85 */     if (this.adminAcl.isUserAllowed(callerUGI)) {
/*  52: 86 */       return true;
/*  53:    */     }
/*  54: 88 */     return false;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public boolean checkAccess(UserGroupInformation callerUGI, JobACL jobOperation, String jobOwner, AccessControlList jobACL)
/*  58:    */   {
/*  59:109 */     if (LOG.isDebugEnabled()) {
/*  60:110 */       LOG.debug("checkAccess job acls, jobOwner: " + jobOwner + " jobacl: " + jobOperation.toString() + " user: " + callerUGI.getShortUserName());
/*  61:    */     }
/*  62:113 */     String user = callerUGI.getShortUserName();
/*  63:114 */     if (!areACLsEnabled()) {
/*  64:115 */       return true;
/*  65:    */     }
/*  66:119 */     if ((isMRAdmin(callerUGI)) || (user.equals(jobOwner)) || (jobACL.isUserAllowed(callerUGI))) {
/*  67:122 */       return true;
/*  68:    */     }
/*  69:125 */     return false;
/*  70:    */   }
/*  71:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.JobACLsManager
 * JD-Core Version:    0.7.0.1
 */