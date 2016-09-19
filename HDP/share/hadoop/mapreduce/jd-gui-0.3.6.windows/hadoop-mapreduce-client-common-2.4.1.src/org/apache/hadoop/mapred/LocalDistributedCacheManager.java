/*   1:    */ package org.apache.hadoop.mapred;
/*   2:    */ 
/*   3:    */ import com.google.common.collect.Maps;
/*   4:    */ import com.google.common.util.concurrent.ThreadFactoryBuilder;
/*   5:    */ import java.io.File;
/*   6:    */ import java.io.IOException;
/*   7:    */ import java.net.MalformedURLException;
/*   8:    */ import java.net.URI;
/*   9:    */ import java.net.URISyntaxException;
/*  10:    */ import java.net.URL;
/*  11:    */ import java.net.URLClassLoader;
/*  12:    */ import java.security.AccessController;
/*  13:    */ import java.security.PrivilegedAction;
/*  14:    */ import java.util.ArrayList;
/*  15:    */ import java.util.HashMap;
/*  16:    */ import java.util.LinkedHashMap;
/*  17:    */ import java.util.List;
/*  18:    */ import java.util.Map;
/*  19:    */ import java.util.Map.Entry;
/*  20:    */ import java.util.Set;
/*  21:    */ import java.util.concurrent.Callable;
/*  22:    */ import java.util.concurrent.ExecutionException;
/*  23:    */ import java.util.concurrent.ExecutorService;
/*  24:    */ import java.util.concurrent.Executors;
/*  25:    */ import java.util.concurrent.Future;
/*  26:    */ import java.util.concurrent.ThreadFactory;
/*  27:    */ import java.util.concurrent.atomic.AtomicLong;
/*  28:    */ import org.apache.commons.logging.Log;
/*  29:    */ import org.apache.commons.logging.LogFactory;
/*  30:    */ import org.apache.hadoop.fs.FileContext;
/*  31:    */ import org.apache.hadoop.fs.FileSystem;
/*  32:    */ import org.apache.hadoop.fs.FileUtil;
/*  33:    */ import org.apache.hadoop.fs.LocalDirAllocator;
/*  34:    */ import org.apache.hadoop.fs.Path;
/*  35:    */ import org.apache.hadoop.mapreduce.filecache.DistributedCache;
/*  36:    */ import org.apache.hadoop.mapreduce.v2.util.MRApps;
/*  37:    */ import org.apache.hadoop.security.UserGroupInformation;
/*  38:    */ import org.apache.hadoop.util.StringUtils;
/*  39:    */ import org.apache.hadoop.yarn.api.records.LocalResource;
/*  40:    */ import org.apache.hadoop.yarn.api.records.LocalResourceType;
/*  41:    */ import org.apache.hadoop.yarn.util.ConverterUtils;
/*  42:    */ import org.apache.hadoop.yarn.util.FSDownload;
/*  43:    */ 
/*  44:    */ class LocalDistributedCacheManager
/*  45:    */ {
/*  46: 69 */   public static final Log LOG = LogFactory.getLog(LocalDistributedCacheManager.class);
/*  47: 72 */   private List<String> localArchives = new ArrayList();
/*  48: 73 */   private List<String> localFiles = new ArrayList();
/*  49: 74 */   private List<String> localClasspaths = new ArrayList();
/*  50: 76 */   private List<File> symlinksCreated = new ArrayList();
/*  51: 78 */   private boolean setupCalled = false;
/*  52:    */   
/*  53:    */   public void setup(JobConf conf)
/*  54:    */     throws IOException
/*  55:    */   {
/*  56: 87 */     File workDir = new File(System.getProperty("user.dir"));
/*  57:    */     
/*  58:    */ 
/*  59:    */ 
/*  60: 91 */     Map<String, LocalResource> localResources = new LinkedHashMap();
/*  61:    */     
/*  62: 93 */     MRApps.setupDistributedCache(conf, localResources);
/*  63:    */     
/*  64: 95 */     AtomicLong uniqueNumberGenerator = new AtomicLong(System.currentTimeMillis());
/*  65:    */     
/*  66:    */ 
/*  67:    */ 
/*  68: 99 */     Map<String, Path> classpaths = new HashMap();
/*  69:100 */     Path[] archiveClassPaths = DistributedCache.getArchiveClassPaths(conf);
/*  70:101 */     if (archiveClassPaths != null) {
/*  71:102 */       for (Path p : archiveClassPaths)
/*  72:    */       {
/*  73:103 */         FileSystem remoteFS = p.getFileSystem(conf);
/*  74:104 */         p = remoteFS.resolvePath(p.makeQualified(remoteFS.getUri(), remoteFS.getWorkingDirectory()));
/*  75:    */         
/*  76:106 */         classpaths.put(p.toUri().getPath().toString(), p);
/*  77:    */       }
/*  78:    */     }
/*  79:109 */     Path[] fileClassPaths = DistributedCache.getFileClassPaths(conf);
/*  80:110 */     if (fileClassPaths != null) {
/*  81:111 */       for (Path p : fileClassPaths)
/*  82:    */       {
/*  83:112 */         FileSystem remoteFS = p.getFileSystem(conf);
/*  84:113 */         p = remoteFS.resolvePath(p.makeQualified(remoteFS.getUri(), remoteFS.getWorkingDirectory()));
/*  85:    */         
/*  86:115 */         classpaths.put(p.toUri().getPath().toString(), p);
/*  87:    */       }
/*  88:    */     }
/*  89:120 */     LocalDirAllocator localDirAllocator = new LocalDirAllocator("mapreduce.cluster.local.dir");
/*  90:    */     
/*  91:122 */     FileContext localFSFileContext = FileContext.getLocalFSFileContext();
/*  92:123 */     UserGroupInformation ugi = UserGroupInformation.getCurrentUser();
/*  93:    */     
/*  94:125 */     ExecutorService exec = null;
/*  95:    */     try
/*  96:    */     {
/*  97:127 */       ThreadFactory tf = new ThreadFactoryBuilder().setNameFormat("LocalDistributedCacheManager Downloader #%d").build();
/*  98:    */       
/*  99:    */ 
/* 100:130 */       exec = Executors.newCachedThreadPool(tf);
/* 101:131 */       Path destPath = localDirAllocator.getLocalPathForWrite(".", conf);
/* 102:132 */       resourcesToPaths = Maps.newHashMap();
/* 103:133 */       for (LocalResource resource : localResources.values())
/* 104:    */       {
/* 105:134 */         Callable<Path> download = new FSDownload(localFSFileContext, ugi, conf, new Path(destPath, Long.toString(uniqueNumberGenerator.incrementAndGet())), resource);
/* 106:    */         
/* 107:    */ 
/* 108:    */ 
/* 109:138 */         Future<Path> future = exec.submit(download);
/* 110:139 */         resourcesToPaths.put(resource, future);
/* 111:    */       }
/* 112:141 */       for (Map.Entry<String, LocalResource> entry : localResources.entrySet())
/* 113:    */       {
/* 114:142 */         LocalResource resource = (LocalResource)entry.getValue();
/* 115:    */         Path path;
/* 116:    */         try
/* 117:    */         {
/* 118:145 */           path = (Path)((Future)resourcesToPaths.get(resource)).get();
/* 119:    */         }
/* 120:    */         catch (InterruptedException e)
/* 121:    */         {
/* 122:147 */           throw new IOException(e);
/* 123:    */         }
/* 124:    */         catch (ExecutionException e)
/* 125:    */         {
/* 126:149 */           throw new IOException(e);
/* 127:    */         }
/* 128:151 */         String pathString = path.toUri().toString();
/* 129:152 */         String link = (String)entry.getKey();
/* 130:153 */         String target = new File(path.toUri()).getPath();
/* 131:154 */         symlink(workDir, target, link);
/* 132:156 */         if (resource.getType() == LocalResourceType.ARCHIVE) {
/* 133:157 */           this.localArchives.add(pathString);
/* 134:158 */         } else if (resource.getType() == LocalResourceType.FILE) {
/* 135:159 */           this.localFiles.add(pathString);
/* 136:160 */         } else if (resource.getType() == LocalResourceType.PATTERN) {
/* 137:162 */           throw new IllegalArgumentException("Resource type PATTERN is not implemented yet. " + resource.getResource());
/* 138:    */         }
/* 139:    */         Path resourcePath;
/* 140:    */         try
/* 141:    */         {
/* 142:167 */           resourcePath = ConverterUtils.getPathFromYarnURL(resource.getResource());
/* 143:    */         }
/* 144:    */         catch (URISyntaxException e)
/* 145:    */         {
/* 146:169 */           throw new IOException(e);
/* 147:    */         }
/* 148:171 */         LOG.info(String.format("Localized %s as %s", new Object[] { resourcePath, path }));
/* 149:172 */         String cp = resourcePath.toUri().getPath();
/* 150:173 */         if (classpaths.keySet().contains(cp)) {
/* 151:174 */           this.localClasspaths.add(path.toUri().getPath().toString());
/* 152:    */         }
/* 153:    */       }
/* 154:    */     }
/* 155:    */     finally
/* 156:    */     {
/* 157:    */       Map<LocalResource, Future<Path>> resourcesToPaths;
/* 158:178 */       if (exec != null) {
/* 159:179 */         exec.shutdown();
/* 160:    */       }
/* 161:    */     }
/* 162:183 */     if (!this.localArchives.isEmpty()) {
/* 163:184 */       conf.set("mapreduce.job.cache.local.archives", StringUtils.arrayToString((String[])this.localArchives.toArray(new String[this.localArchives.size()])));
/* 164:    */     }
/* 165:188 */     if (!this.localFiles.isEmpty()) {
/* 166:189 */       conf.set("mapreduce.job.cache.local.files", StringUtils.arrayToString((String[])this.localFiles.toArray(new String[this.localArchives.size()])));
/* 167:    */     }
/* 168:193 */     this.setupCalled = true;
/* 169:    */   }
/* 170:    */   
/* 171:    */   private void symlink(File workDir, String target, String link)
/* 172:    */     throws IOException
/* 173:    */   {
/* 174:203 */     if (link != null)
/* 175:    */     {
/* 176:204 */       link = workDir.toString() + "/" + link;
/* 177:205 */       File flink = new File(link);
/* 178:206 */       if (!flink.exists())
/* 179:    */       {
/* 180:207 */         LOG.info(String.format("Creating symlink: %s <- %s", new Object[] { target, link }));
/* 181:208 */         if (0 != FileUtil.symLink(target, link)) {
/* 182:209 */           LOG.warn(String.format("Failed to create symlink: %s <- %s", new Object[] { target, link }));
/* 183:    */         } else {
/* 184:212 */           this.symlinksCreated.add(new File(link));
/* 185:    */         }
/* 186:    */       }
/* 187:    */     }
/* 188:    */   }
/* 189:    */   
/* 190:    */   public boolean hasLocalClasspaths()
/* 191:    */   {
/* 192:224 */     if (!this.setupCalled) {
/* 193:225 */       throw new IllegalStateException("hasLocalClasspaths() should be called after setup()");
/* 194:    */     }
/* 195:228 */     return !this.localClasspaths.isEmpty();
/* 196:    */   }
/* 197:    */   
/* 198:    */   public ClassLoader makeClassLoader(final ClassLoader parent)
/* 199:    */     throws MalformedURLException
/* 200:    */   {
/* 201:237 */     final URL[] urls = new URL[this.localClasspaths.size()];
/* 202:238 */     for (int i = 0; i < this.localClasspaths.size(); i++)
/* 203:    */     {
/* 204:239 */       urls[i] = new File((String)this.localClasspaths.get(i)).toURI().toURL();
/* 205:240 */       LOG.info(urls[i]);
/* 206:    */     }
/* 207:242 */     (ClassLoader)AccessController.doPrivileged(new PrivilegedAction()
/* 208:    */     {
/* 209:    */       public ClassLoader run()
/* 210:    */       {
/* 211:245 */         return new URLClassLoader(urls, parent);
/* 212:    */       }
/* 213:    */     });
/* 214:    */   }
/* 215:    */   
/* 216:    */   public void close()
/* 217:    */     throws IOException
/* 218:    */   {
/* 219:251 */     for (File symlink : this.symlinksCreated) {
/* 220:252 */       if (!symlink.delete()) {
/* 221:253 */         LOG.warn("Failed to delete symlink created by the local job runner: " + symlink);
/* 222:    */       }
/* 223:    */     }
/* 224:257 */     FileContext localFSFileContext = FileContext.getLocalFSFileContext();
/* 225:258 */     for (String archive : this.localArchives) {
/* 226:259 */       localFSFileContext.delete(new Path(archive), true);
/* 227:    */     }
/* 228:261 */     for (String file : this.localFiles) {
/* 229:262 */       localFSFileContext.delete(new Path(file), true);
/* 230:    */     }
/* 231:    */   }
/* 232:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-common-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.LocalDistributedCacheManager
 * JD-Core Version:    0.7.0.1
 */