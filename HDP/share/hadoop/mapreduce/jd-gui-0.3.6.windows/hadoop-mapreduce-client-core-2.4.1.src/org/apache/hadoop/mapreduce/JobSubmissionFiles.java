/*   1:    */ package org.apache.hadoop.mapreduce;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import org.apache.commons.logging.Log;
/*   5:    */ import org.apache.commons.logging.LogFactory;
/*   6:    */ import org.apache.hadoop.classification.InterfaceAudience.Private;
/*   7:    */ import org.apache.hadoop.conf.Configuration;
/*   8:    */ import org.apache.hadoop.fs.FileStatus;
/*   9:    */ import org.apache.hadoop.fs.FileSystem;
/*  10:    */ import org.apache.hadoop.fs.Path;
/*  11:    */ import org.apache.hadoop.fs.permission.FsPermission;
/*  12:    */ import org.apache.hadoop.security.UserGroupInformation;
/*  13:    */ 
/*  14:    */ @InterfaceAudience.Private
/*  15:    */ public class JobSubmissionFiles
/*  16:    */ {
/*  17: 40 */   private static final Log LOG = LogFactory.getLog(JobSubmissionFiles.class);
/*  18: 43 */   public static final FsPermission JOB_DIR_PERMISSION = FsPermission.createImmutable((short)448);
/*  19: 46 */   public static final FsPermission JOB_FILE_PERMISSION = FsPermission.createImmutable((short)420);
/*  20:    */   
/*  21:    */   public static Path getJobSplitFile(Path jobSubmissionDir)
/*  22:    */   {
/*  23: 50 */     return new Path(jobSubmissionDir, "job.split");
/*  24:    */   }
/*  25:    */   
/*  26:    */   public static Path getJobSplitMetaFile(Path jobSubmissionDir)
/*  27:    */   {
/*  28: 54 */     return new Path(jobSubmissionDir, "job.splitmetainfo");
/*  29:    */   }
/*  30:    */   
/*  31:    */   public static Path getJobConfPath(Path jobSubmitDir)
/*  32:    */   {
/*  33: 61 */     return new Path(jobSubmitDir, "job.xml");
/*  34:    */   }
/*  35:    */   
/*  36:    */   public static Path getJobJar(Path jobSubmitDir)
/*  37:    */   {
/*  38: 68 */     return new Path(jobSubmitDir, "job.jar");
/*  39:    */   }
/*  40:    */   
/*  41:    */   public static Path getJobDistCacheFiles(Path jobSubmitDir)
/*  42:    */   {
/*  43: 76 */     return new Path(jobSubmitDir, "files");
/*  44:    */   }
/*  45:    */   
/*  46:    */   public static Path getJobDistCacheArchives(Path jobSubmitDir)
/*  47:    */   {
/*  48: 83 */     return new Path(jobSubmitDir, "archives");
/*  49:    */   }
/*  50:    */   
/*  51:    */   public static Path getJobDistCacheLibjars(Path jobSubmitDir)
/*  52:    */   {
/*  53: 90 */     return new Path(jobSubmitDir, "libjars");
/*  54:    */   }
/*  55:    */   
/*  56:    */   public static Path getStagingDir(Cluster cluster, Configuration conf)
/*  57:    */     throws IOException, InterruptedException
/*  58:    */   {
/*  59:101 */     Path stagingArea = cluster.getStagingAreaDir();
/*  60:102 */     FileSystem fs = stagingArea.getFileSystem(conf);
/*  61:    */     
/*  62:    */ 
/*  63:105 */     UserGroupInformation ugi = UserGroupInformation.getLoginUser();
/*  64:106 */     String realUser = ugi.getShortUserName();
/*  65:107 */     String currentUser = UserGroupInformation.getCurrentUser().getShortUserName();
/*  66:108 */     if (fs.exists(stagingArea))
/*  67:    */     {
/*  68:109 */       FileStatus fsStatus = fs.getFileStatus(stagingArea);
/*  69:110 */       String owner = fsStatus.getOwner();
/*  70:111 */       if ((!owner.equals(currentUser)) && (!owner.equals(realUser))) {
/*  71:112 */         throw new IOException("The ownership on the staging directory " + stagingArea + " is not as expected. " + "It is owned by " + owner + ". The directory must " + "be owned by the submitter " + currentUser + " or " + "by " + realUser);
/*  72:    */       }
/*  73:118 */       if (!fsStatus.getPermission().equals(JOB_DIR_PERMISSION))
/*  74:    */       {
/*  75:119 */         LOG.info("Permissions on staging directory " + stagingArea + " are " + "incorrect: " + fsStatus.getPermission() + ". Fixing permissions " + "to correct value " + JOB_DIR_PERMISSION);
/*  76:    */         
/*  77:    */ 
/*  78:122 */         fs.setPermission(stagingArea, JOB_DIR_PERMISSION);
/*  79:    */       }
/*  80:    */     }
/*  81:    */     else
/*  82:    */     {
/*  83:125 */       fs.mkdirs(stagingArea, new FsPermission(JOB_DIR_PERMISSION));
/*  84:    */     }
/*  85:128 */     return stagingArea;
/*  86:    */   }
/*  87:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.JobSubmissionFiles
 * JD-Core Version:    0.7.0.1
 */