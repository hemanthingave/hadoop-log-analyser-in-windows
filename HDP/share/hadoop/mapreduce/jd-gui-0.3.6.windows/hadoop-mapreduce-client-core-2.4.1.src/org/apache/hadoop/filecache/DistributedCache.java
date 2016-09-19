/*   1:    */ package org.apache.hadoop.filecache;
/*   2:    */ 
/*   3:    */ import java.io.File;
/*   4:    */ import java.io.IOException;
/*   5:    */ import java.net.URI;
/*   6:    */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*   7:    */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*   8:    */ import org.apache.hadoop.conf.Configuration;
/*   9:    */ import org.apache.hadoop.fs.FileStatus;
/*  10:    */ import org.apache.hadoop.fs.FileSystem;
/*  11:    */ import org.apache.hadoop.fs.Path;
/*  12:    */ 
/*  13:    */ @InterfaceAudience.Public
/*  14:    */ @InterfaceStability.Stable
/*  15:    */ @Deprecated
/*  16:    */ public class DistributedCache
/*  17:    */   extends org.apache.hadoop.mapreduce.filecache.DistributedCache
/*  18:    */ {
/*  19:    */   @Deprecated
/*  20:    */   public static final String CACHE_FILES_SIZES = "mapred.cache.files.filesizes";
/*  21:    */   @Deprecated
/*  22:    */   public static final String CACHE_ARCHIVES_SIZES = "mapred.cache.archives.filesizes";
/*  23:    */   @Deprecated
/*  24:    */   public static final String CACHE_ARCHIVES_TIMESTAMPS = "mapred.cache.archives.timestamps";
/*  25:    */   @Deprecated
/*  26:    */   public static final String CACHE_FILES_TIMESTAMPS = "mapred.cache.files.timestamps";
/*  27:    */   @Deprecated
/*  28:    */   public static final String CACHE_ARCHIVES = "mapred.cache.archives";
/*  29:    */   @Deprecated
/*  30:    */   public static final String CACHE_FILES = "mapred.cache.files";
/*  31:    */   @Deprecated
/*  32:    */   public static final String CACHE_LOCALARCHIVES = "mapred.cache.localArchives";
/*  33:    */   @Deprecated
/*  34:    */   public static final String CACHE_LOCALFILES = "mapred.cache.localFiles";
/*  35:    */   @Deprecated
/*  36:    */   public static final String CACHE_SYMLINK = "mapred.create.symlink";
/*  37:    */   
/*  38:    */   @Deprecated
/*  39:    */   public static void addLocalArchives(Configuration conf, String str)
/*  40:    */   {
/*  41:222 */     String archives = conf.get("mapred.cache.localArchives");
/*  42:223 */     conf.set("mapred.cache.localArchives", archives + "," + str);
/*  43:    */   }
/*  44:    */   
/*  45:    */   @Deprecated
/*  46:    */   public static void addLocalFiles(Configuration conf, String str)
/*  47:    */   {
/*  48:235 */     String files = conf.get("mapred.cache.localFiles");
/*  49:236 */     conf.set("mapred.cache.localFiles", files + "," + str);
/*  50:    */   }
/*  51:    */   
/*  52:    */   @Deprecated
/*  53:    */   public static void createAllSymlink(Configuration conf, File jobCacheDir, File workDir)
/*  54:    */     throws IOException
/*  55:    */   {}
/*  56:    */   
/*  57:    */   @Deprecated
/*  58:    */   public static FileStatus getFileStatus(Configuration conf, URI cache)
/*  59:    */     throws IOException
/*  60:    */   {
/*  61:269 */     FileSystem fileSystem = FileSystem.get(cache, conf);
/*  62:270 */     return fileSystem.getFileStatus(new Path(cache.getPath()));
/*  63:    */   }
/*  64:    */   
/*  65:    */   @Deprecated
/*  66:    */   public static long getTimestamp(Configuration conf, URI cache)
/*  67:    */     throws IOException
/*  68:    */   {
/*  69:283 */     return getFileStatus(conf, cache).getModificationTime();
/*  70:    */   }
/*  71:    */   
/*  72:    */   @Deprecated
/*  73:    */   public static void setArchiveTimestamps(Configuration conf, String timestamps)
/*  74:    */   {
/*  75:295 */     conf.set("mapred.cache.archives.timestamps", timestamps);
/*  76:    */   }
/*  77:    */   
/*  78:    */   @Deprecated
/*  79:    */   public static void setFileTimestamps(Configuration conf, String timestamps)
/*  80:    */   {
/*  81:307 */     conf.set("mapred.cache.files.timestamps", timestamps);
/*  82:    */   }
/*  83:    */   
/*  84:    */   @Deprecated
/*  85:    */   public static void setLocalArchives(Configuration conf, String str)
/*  86:    */   {
/*  87:318 */     conf.set("mapred.cache.localArchives", str);
/*  88:    */   }
/*  89:    */   
/*  90:    */   @Deprecated
/*  91:    */   public static void setLocalFiles(Configuration conf, String str)
/*  92:    */   {
/*  93:329 */     conf.set("mapred.cache.localFiles", str);
/*  94:    */   }
/*  95:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.filecache.DistributedCache
 * JD-Core Version:    0.7.0.1
 */