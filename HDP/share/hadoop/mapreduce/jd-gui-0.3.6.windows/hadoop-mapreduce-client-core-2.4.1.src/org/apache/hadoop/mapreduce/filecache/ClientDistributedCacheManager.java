/*   1:    */ package org.apache.hadoop.mapreduce.filecache;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.net.URI;
/*   5:    */ import java.util.HashMap;
/*   6:    */ import java.util.Map;
/*   7:    */ import org.apache.hadoop.classification.InterfaceAudience.Private;
/*   8:    */ import org.apache.hadoop.conf.Configuration;
/*   9:    */ import org.apache.hadoop.fs.FileStatus;
/*  10:    */ import org.apache.hadoop.fs.FileSystem;
/*  11:    */ import org.apache.hadoop.fs.Path;
/*  12:    */ import org.apache.hadoop.fs.permission.FsAction;
/*  13:    */ import org.apache.hadoop.fs.permission.FsPermission;
/*  14:    */ import org.apache.hadoop.mapreduce.security.TokenCache;
/*  15:    */ import org.apache.hadoop.security.Credentials;
/*  16:    */ 
/*  17:    */ @InterfaceAudience.Private
/*  18:    */ public class ClientDistributedCacheManager
/*  19:    */ {
/*  20:    */   public static void determineTimestampsAndCacheVisibilities(Configuration job)
/*  21:    */     throws IOException
/*  22:    */   {
/*  23: 56 */     Map<URI, FileStatus> statCache = new HashMap();
/*  24: 57 */     determineTimestamps(job, statCache);
/*  25: 58 */     determineCacheVisibilities(job, statCache);
/*  26:    */   }
/*  27:    */   
/*  28:    */   public static void determineTimestamps(Configuration job, Map<URI, FileStatus> statCache)
/*  29:    */     throws IOException
/*  30:    */   {
/*  31: 73 */     URI[] tarchives = DistributedCache.getCacheArchives(job);
/*  32: 74 */     if (tarchives != null)
/*  33:    */     {
/*  34: 75 */       FileStatus status = getFileStatus(job, tarchives[0], statCache);
/*  35: 76 */       StringBuilder archiveFileSizes = new StringBuilder(String.valueOf(status.getLen()));
/*  36:    */       
/*  37: 78 */       StringBuilder archiveTimestamps = new StringBuilder(String.valueOf(status.getModificationTime()));
/*  38: 80 */       for (int i = 1; i < tarchives.length; i++)
/*  39:    */       {
/*  40: 81 */         status = getFileStatus(job, tarchives[i], statCache);
/*  41: 82 */         archiveFileSizes.append(",");
/*  42: 83 */         archiveFileSizes.append(String.valueOf(status.getLen()));
/*  43: 84 */         archiveTimestamps.append(",");
/*  44: 85 */         archiveTimestamps.append(String.valueOf(status.getModificationTime()));
/*  45:    */       }
/*  46: 87 */       job.set("mapreduce.job.cache.archives.filesizes", archiveFileSizes.toString());
/*  47: 88 */       setArchiveTimestamps(job, archiveTimestamps.toString());
/*  48:    */     }
/*  49: 91 */     URI[] tfiles = DistributedCache.getCacheFiles(job);
/*  50: 92 */     if (tfiles != null)
/*  51:    */     {
/*  52: 93 */       FileStatus status = getFileStatus(job, tfiles[0], statCache);
/*  53: 94 */       StringBuilder fileSizes = new StringBuilder(String.valueOf(status.getLen()));
/*  54:    */       
/*  55: 96 */       StringBuilder fileTimestamps = new StringBuilder(String.valueOf(status.getModificationTime()));
/*  56: 98 */       for (int i = 1; i < tfiles.length; i++)
/*  57:    */       {
/*  58: 99 */         status = getFileStatus(job, tfiles[i], statCache);
/*  59:100 */         fileSizes.append(",");
/*  60:101 */         fileSizes.append(String.valueOf(status.getLen()));
/*  61:102 */         fileTimestamps.append(",");
/*  62:103 */         fileTimestamps.append(String.valueOf(status.getModificationTime()));
/*  63:    */       }
/*  64:105 */       job.set("mapreduce.job.cache.files.filesizes", fileSizes.toString());
/*  65:106 */       setFileTimestamps(job, fileTimestamps.toString());
/*  66:    */     }
/*  67:    */   }
/*  68:    */   
/*  69:    */   public static void getDelegationTokens(Configuration job, Credentials credentials)
/*  70:    */     throws IOException
/*  71:    */   {
/*  72:118 */     URI[] tarchives = DistributedCache.getCacheArchives(job);
/*  73:119 */     URI[] tfiles = DistributedCache.getCacheFiles(job);
/*  74:    */     
/*  75:121 */     int size = (tarchives != null ? tarchives.length : 0) + (tfiles != null ? tfiles.length : 0);
/*  76:122 */     Path[] ps = new Path[size];
/*  77:    */     
/*  78:124 */     int i = 0;
/*  79:125 */     if (tarchives != null) {
/*  80:126 */       for (i = 0; i < tarchives.length; i++) {
/*  81:127 */         ps[i] = new Path(tarchives[i].toString());
/*  82:    */       }
/*  83:    */     }
/*  84:131 */     if (tfiles != null) {
/*  85:132 */       for (int j = 0; j < tfiles.length; j++) {
/*  86:133 */         ps[(i + j)] = new Path(tfiles[j].toString());
/*  87:    */       }
/*  88:    */     }
/*  89:137 */     TokenCache.obtainTokensForNamenodes(credentials, ps, job);
/*  90:    */   }
/*  91:    */   
/*  92:    */   public static void determineCacheVisibilities(Configuration job, Map<URI, FileStatus> statCache)
/*  93:    */     throws IOException
/*  94:    */   {
/*  95:150 */     URI[] tarchives = DistributedCache.getCacheArchives(job);
/*  96:151 */     if (tarchives != null)
/*  97:    */     {
/*  98:152 */       StringBuilder archiveVisibilities = new StringBuilder(String.valueOf(isPublic(job, tarchives[0], statCache)));
/*  99:154 */       for (int i = 1; i < tarchives.length; i++)
/* 100:    */       {
/* 101:155 */         archiveVisibilities.append(",");
/* 102:156 */         archiveVisibilities.append(String.valueOf(isPublic(job, tarchives[i], statCache)));
/* 103:    */       }
/* 104:158 */       setArchiveVisibilities(job, archiveVisibilities.toString());
/* 105:    */     }
/* 106:160 */     URI[] tfiles = DistributedCache.getCacheFiles(job);
/* 107:161 */     if (tfiles != null)
/* 108:    */     {
/* 109:162 */       StringBuilder fileVisibilities = new StringBuilder(String.valueOf(isPublic(job, tfiles[0], statCache)));
/* 110:164 */       for (int i = 1; i < tfiles.length; i++)
/* 111:    */       {
/* 112:165 */         fileVisibilities.append(",");
/* 113:166 */         fileVisibilities.append(String.valueOf(isPublic(job, tfiles[i], statCache)));
/* 114:    */       }
/* 115:168 */       setFileVisibilities(job, fileVisibilities.toString());
/* 116:    */     }
/* 117:    */   }
/* 118:    */   
/* 119:    */   static void setArchiveVisibilities(Configuration conf, String booleans)
/* 120:    */   {
/* 121:181 */     conf.set("mapreduce.job.cache.archives.visibilities", booleans);
/* 122:    */   }
/* 123:    */   
/* 124:    */   static void setFileVisibilities(Configuration conf, String booleans)
/* 125:    */   {
/* 126:192 */     conf.set("mapreduce.job.cache.files.visibilities", booleans);
/* 127:    */   }
/* 128:    */   
/* 129:    */   static void setArchiveTimestamps(Configuration conf, String timestamps)
/* 130:    */   {
/* 131:203 */     conf.set("mapreduce.job.cache.archives.timestamps", timestamps);
/* 132:    */   }
/* 133:    */   
/* 134:    */   static void setFileTimestamps(Configuration conf, String timestamps)
/* 135:    */   {
/* 136:214 */     conf.set("mapreduce.job.cache.files.timestamps", timestamps);
/* 137:    */   }
/* 138:    */   
/* 139:    */   private static FileStatus getFileStatus(Configuration job, URI uri, Map<URI, FileStatus> statCache)
/* 140:    */     throws IOException
/* 141:    */   {
/* 142:223 */     FileSystem fileSystem = FileSystem.get(uri, job);
/* 143:224 */     return getFileStatus(fileSystem, uri, statCache);
/* 144:    */   }
/* 145:    */   
/* 146:    */   static boolean isPublic(Configuration conf, URI uri, Map<URI, FileStatus> statCache)
/* 147:    */     throws IOException
/* 148:    */   {
/* 149:237 */     FileSystem fs = FileSystem.get(uri, conf);
/* 150:238 */     Path current = new Path(uri.getPath());
/* 151:240 */     if (!checkPermissionOfOther(fs, current, FsAction.READ, statCache)) {
/* 152:241 */       return false;
/* 153:    */     }
/* 154:243 */     return ancestorsHaveExecutePermissions(fs, current.getParent(), statCache);
/* 155:    */   }
/* 156:    */   
/* 157:    */   static boolean ancestorsHaveExecutePermissions(FileSystem fs, Path path, Map<URI, FileStatus> statCache)
/* 158:    */     throws IOException
/* 159:    */   {
/* 160:253 */     Path current = path;
/* 161:254 */     while (current != null)
/* 162:    */     {
/* 163:256 */       if (!checkPermissionOfOther(fs, current, FsAction.EXECUTE, statCache)) {
/* 164:257 */         return false;
/* 165:    */       }
/* 166:259 */       current = current.getParent();
/* 167:    */     }
/* 168:261 */     return true;
/* 169:    */   }
/* 170:    */   
/* 171:    */   private static boolean checkPermissionOfOther(FileSystem fs, Path path, FsAction action, Map<URI, FileStatus> statCache)
/* 172:    */     throws IOException
/* 173:    */   {
/* 174:275 */     FileStatus status = getFileStatus(fs, path.toUri(), statCache);
/* 175:276 */     FsPermission perms = status.getPermission();
/* 176:277 */     FsAction otherAction = perms.getOtherAction();
/* 177:278 */     if (otherAction.implies(action)) {
/* 178:279 */       return true;
/* 179:    */     }
/* 180:281 */     return false;
/* 181:    */   }
/* 182:    */   
/* 183:    */   private static FileStatus getFileStatus(FileSystem fs, URI uri, Map<URI, FileStatus> statCache)
/* 184:    */     throws IOException
/* 185:    */   {
/* 186:286 */     FileStatus stat = (FileStatus)statCache.get(uri);
/* 187:287 */     if (stat == null)
/* 188:    */     {
/* 189:288 */       stat = fs.getFileStatus(new Path(uri));
/* 190:289 */       statCache.put(uri, stat);
/* 191:    */     }
/* 192:291 */     return stat;
/* 193:    */   }
/* 194:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.filecache.ClientDistributedCacheManager
 * JD-Core Version:    0.7.0.1
 */