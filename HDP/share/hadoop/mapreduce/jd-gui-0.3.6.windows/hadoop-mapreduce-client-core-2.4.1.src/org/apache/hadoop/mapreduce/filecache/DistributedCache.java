/*   1:    */ package org.apache.hadoop.mapreduce.filecache;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.net.URI;
/*   5:    */ import java.util.ArrayList;
/*   6:    */ import java.util.HashSet;
/*   7:    */ import java.util.Set;
/*   8:    */ import org.apache.hadoop.classification.InterfaceAudience.Private;
/*   9:    */ import org.apache.hadoop.conf.Configuration;
/*  10:    */ import org.apache.hadoop.fs.FileSystem;
/*  11:    */ import org.apache.hadoop.fs.Path;
/*  12:    */ import org.apache.hadoop.util.StringUtils;
/*  13:    */ 
/*  14:    */ @Deprecated
/*  15:    */ @InterfaceAudience.Private
/*  16:    */ public class DistributedCache
/*  17:    */ {
/*  18:    */   @Deprecated
/*  19:    */   public static void setCacheArchives(URI[] archives, Configuration conf)
/*  20:    */   {
/*  21:145 */     String sarchives = StringUtils.uriToString(archives);
/*  22:146 */     conf.set("mapreduce.job.cache.archives", sarchives);
/*  23:    */   }
/*  24:    */   
/*  25:    */   @Deprecated
/*  26:    */   public static void setCacheFiles(URI[] files, Configuration conf)
/*  27:    */   {
/*  28:158 */     String sfiles = StringUtils.uriToString(files);
/*  29:159 */     conf.set("mapreduce.job.cache.files", sfiles);
/*  30:    */   }
/*  31:    */   
/*  32:    */   @Deprecated
/*  33:    */   public static URI[] getCacheArchives(Configuration conf)
/*  34:    */     throws IOException
/*  35:    */   {
/*  36:172 */     return StringUtils.stringToURI(conf.getStrings("mapreduce.job.cache.archives"));
/*  37:    */   }
/*  38:    */   
/*  39:    */   @Deprecated
/*  40:    */   public static URI[] getCacheFiles(Configuration conf)
/*  41:    */     throws IOException
/*  42:    */   {
/*  43:185 */     return StringUtils.stringToURI(conf.getStrings("mapreduce.job.cache.files"));
/*  44:    */   }
/*  45:    */   
/*  46:    */   @Deprecated
/*  47:    */   public static Path[] getLocalCacheArchives(Configuration conf)
/*  48:    */     throws IOException
/*  49:    */   {
/*  50:199 */     return StringUtils.stringToPath(conf.getStrings("mapreduce.job.cache.local.archives"));
/*  51:    */   }
/*  52:    */   
/*  53:    */   @Deprecated
/*  54:    */   public static Path[] getLocalCacheFiles(Configuration conf)
/*  55:    */     throws IOException
/*  56:    */   {
/*  57:214 */     return StringUtils.stringToPath(conf.getStrings("mapreduce.job.cache.local.files"));
/*  58:    */   }
/*  59:    */   
/*  60:    */   private static long[] parseTimestamps(String[] strs)
/*  61:    */   {
/*  62:223 */     if (strs == null) {
/*  63:224 */       return null;
/*  64:    */     }
/*  65:226 */     long[] result = new long[strs.length];
/*  66:227 */     for (int i = 0; i < strs.length; i++) {
/*  67:228 */       result[i] = Long.parseLong(strs[i]);
/*  68:    */     }
/*  69:230 */     return result;
/*  70:    */   }
/*  71:    */   
/*  72:    */   @Deprecated
/*  73:    */   public static long[] getArchiveTimestamps(Configuration conf)
/*  74:    */   {
/*  75:243 */     return parseTimestamps(conf.getStrings("mapreduce.job.cache.archives.timestamps"));
/*  76:    */   }
/*  77:    */   
/*  78:    */   @Deprecated
/*  79:    */   public static long[] getFileTimestamps(Configuration conf)
/*  80:    */   {
/*  81:258 */     return parseTimestamps(conf.getStrings("mapreduce.job.cache.files.timestamps"));
/*  82:    */   }
/*  83:    */   
/*  84:    */   @Deprecated
/*  85:    */   public static void addCacheArchive(URI uri, Configuration conf)
/*  86:    */   {
/*  87:271 */     String archives = conf.get("mapreduce.job.cache.archives");
/*  88:272 */     conf.set("mapreduce.job.cache.archives", archives + "," + uri.toString());
/*  89:    */   }
/*  90:    */   
/*  91:    */   @Deprecated
/*  92:    */   public static void addCacheFile(URI uri, Configuration conf)
/*  93:    */   {
/*  94:285 */     String files = conf.get("mapreduce.job.cache.files");
/*  95:286 */     conf.set("mapreduce.job.cache.files", files + "," + uri.toString());
/*  96:    */   }
/*  97:    */   
/*  98:    */   @Deprecated
/*  99:    */   public static void addFileToClassPath(Path file, Configuration conf)
/* 100:    */     throws IOException
/* 101:    */   {
/* 102:301 */     addFileToClassPath(file, conf, file.getFileSystem(conf));
/* 103:    */   }
/* 104:    */   
/* 105:    */   public static void addFileToClassPath(Path file, Configuration conf, FileSystem fs)
/* 106:    */     throws IOException
/* 107:    */   {
/* 108:316 */     String classpath = conf.get("mapreduce.job.classpath.files");
/* 109:317 */     conf.set("mapreduce.job.classpath.files", classpath + "," + file.toString());
/* 110:    */     
/* 111:319 */     URI uri = fs.makeQualified(file).toUri();
/* 112:320 */     addCacheFile(uri, conf);
/* 113:    */   }
/* 114:    */   
/* 115:    */   @Deprecated
/* 116:    */   public static Path[] getFileClassPaths(Configuration conf)
/* 117:    */   {
/* 118:332 */     ArrayList<String> list = (ArrayList)conf.getStringCollection("mapreduce.job.classpath.files");
/* 119:334 */     if (list.size() == 0) {
/* 120:335 */       return null;
/* 121:    */     }
/* 122:337 */     Path[] paths = new Path[list.size()];
/* 123:338 */     for (int i = 0; i < list.size(); i++) {
/* 124:339 */       paths[i] = new Path((String)list.get(i));
/* 125:    */     }
/* 126:341 */     return paths;
/* 127:    */   }
/* 128:    */   
/* 129:    */   @Deprecated
/* 130:    */   public static void addArchiveToClassPath(Path archive, Configuration conf)
/* 131:    */     throws IOException
/* 132:    */   {
/* 133:355 */     addArchiveToClassPath(archive, conf, archive.getFileSystem(conf));
/* 134:    */   }
/* 135:    */   
/* 136:    */   public static void addArchiveToClassPath(Path archive, Configuration conf, FileSystem fs)
/* 137:    */     throws IOException
/* 138:    */   {
/* 139:369 */     String classpath = conf.get("mapreduce.job.classpath.archives");
/* 140:370 */     conf.set("mapreduce.job.classpath.archives", classpath + "," + archive.toString());
/* 141:    */     
/* 142:372 */     URI uri = fs.makeQualified(archive).toUri();
/* 143:    */     
/* 144:374 */     addCacheArchive(uri, conf);
/* 145:    */   }
/* 146:    */   
/* 147:    */   @Deprecated
/* 148:    */   public static Path[] getArchiveClassPaths(Configuration conf)
/* 149:    */   {
/* 150:386 */     ArrayList<String> list = (ArrayList)conf.getStringCollection("mapreduce.job.classpath.archives");
/* 151:388 */     if (list.size() == 0) {
/* 152:389 */       return null;
/* 153:    */     }
/* 154:391 */     Path[] paths = new Path[list.size()];
/* 155:392 */     for (int i = 0; i < list.size(); i++) {
/* 156:393 */       paths[i] = new Path((String)list.get(i));
/* 157:    */     }
/* 158:395 */     return paths;
/* 159:    */   }
/* 160:    */   
/* 161:    */   @Deprecated
/* 162:    */   public static void createSymlink(Configuration conf) {}
/* 163:    */   
/* 164:    */   @Deprecated
/* 165:    */   public static boolean getSymlink(Configuration conf)
/* 166:    */   {
/* 167:418 */     return true;
/* 168:    */   }
/* 169:    */   
/* 170:    */   private static boolean[] parseBooleans(String[] strs)
/* 171:    */   {
/* 172:422 */     if (null == strs) {
/* 173:423 */       return null;
/* 174:    */     }
/* 175:425 */     boolean[] result = new boolean[strs.length];
/* 176:426 */     for (int i = 0; i < strs.length; i++) {
/* 177:427 */       result[i] = Boolean.parseBoolean(strs[i]);
/* 178:    */     }
/* 179:429 */     return result;
/* 180:    */   }
/* 181:    */   
/* 182:    */   public static boolean[] getFileVisibilities(Configuration conf)
/* 183:    */   {
/* 184:440 */     return parseBooleans(conf.getStrings("mapreduce.job.cache.files.visibilities"));
/* 185:    */   }
/* 186:    */   
/* 187:    */   public static boolean[] getArchiveVisibilities(Configuration conf)
/* 188:    */   {
/* 189:450 */     return parseBooleans(conf.getStrings("mapreduce.job.cache.archives.visibilities"));
/* 190:    */   }
/* 191:    */   
/* 192:    */   public static boolean checkURIs(URI[] uriFiles, URI[] uriArchives)
/* 193:    */   {
/* 194:462 */     if ((uriFiles == null) && (uriArchives == null)) {
/* 195:463 */       return true;
/* 196:    */     }
/* 197:467 */     Set<String> fragments = new HashSet();
/* 198:470 */     if (uriFiles != null) {
/* 199:471 */       for (int i = 0; i < uriFiles.length; i++)
/* 200:    */       {
/* 201:472 */         String fragment = uriFiles[i].getFragment();
/* 202:473 */         if (fragment == null) {
/* 203:474 */           return false;
/* 204:    */         }
/* 205:476 */         String lowerCaseFragment = fragment.toLowerCase();
/* 206:477 */         if (fragments.contains(lowerCaseFragment)) {
/* 207:478 */           return false;
/* 208:    */         }
/* 209:480 */         fragments.add(lowerCaseFragment);
/* 210:    */       }
/* 211:    */     }
/* 212:485 */     if (uriArchives != null) {
/* 213:486 */       for (int i = 0; i < uriArchives.length; i++)
/* 214:    */       {
/* 215:487 */         String fragment = uriArchives[i].getFragment();
/* 216:488 */         if (fragment == null) {
/* 217:489 */           return false;
/* 218:    */         }
/* 219:491 */         String lowerCaseFragment = fragment.toLowerCase();
/* 220:492 */         if (fragments.contains(lowerCaseFragment)) {
/* 221:493 */           return false;
/* 222:    */         }
/* 223:495 */         fragments.add(lowerCaseFragment);
/* 224:    */       }
/* 225:    */     }
/* 226:498 */     return true;
/* 227:    */   }
/* 228:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.filecache.DistributedCache
 * JD-Core Version:    0.7.0.1
 */