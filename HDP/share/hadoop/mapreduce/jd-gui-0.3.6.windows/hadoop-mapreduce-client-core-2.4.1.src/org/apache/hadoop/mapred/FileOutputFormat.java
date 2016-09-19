/*   1:    */ package org.apache.hadoop.mapred;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.text.NumberFormat;
/*   5:    */ import org.apache.hadoop.classification.InterfaceAudience.Private;
/*   6:    */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*   7:    */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*   8:    */ import org.apache.hadoop.fs.FileSystem;
/*   9:    */ import org.apache.hadoop.fs.Path;
/*  10:    */ import org.apache.hadoop.io.compress.CompressionCodec;
/*  11:    */ import org.apache.hadoop.mapreduce.security.TokenCache;
/*  12:    */ import org.apache.hadoop.util.Progressable;
/*  13:    */ 
/*  14:    */ @InterfaceAudience.Public
/*  15:    */ @InterfaceStability.Stable
/*  16:    */ public abstract class FileOutputFormat<K, V>
/*  17:    */   implements OutputFormat<K, V>
/*  18:    */ {
/*  19:    */   @Deprecated
/*  20:    */   public static enum Counter
/*  21:    */   {
/*  22: 41 */     BYTES_WRITTEN;
/*  23:    */     
/*  24:    */     private Counter() {}
/*  25:    */   }
/*  26:    */   
/*  27:    */   public static void setCompressOutput(JobConf conf, boolean compress)
/*  28:    */   {
/*  29: 50 */     conf.setBoolean("mapreduce.output.fileoutputformat.compress", compress);
/*  30:    */   }
/*  31:    */   
/*  32:    */   public static boolean getCompressOutput(JobConf conf)
/*  33:    */   {
/*  34: 61 */     return conf.getBoolean("mapreduce.output.fileoutputformat.compress", false);
/*  35:    */   }
/*  36:    */   
/*  37:    */   public static void setOutputCompressorClass(JobConf conf, Class<? extends CompressionCodec> codecClass)
/*  38:    */   {
/*  39: 74 */     setCompressOutput(conf, true);
/*  40: 75 */     conf.setClass("mapreduce.output.fileoutputformat.compress.codec", codecClass, CompressionCodec.class);
/*  41:    */   }
/*  42:    */   
/*  43:    */   public static Class<? extends CompressionCodec> getOutputCompressorClass(JobConf conf, Class<? extends CompressionCodec> defaultValue)
/*  44:    */   {
/*  45: 91 */     Class<? extends CompressionCodec> codecClass = defaultValue;
/*  46:    */     
/*  47: 93 */     String name = conf.get("mapreduce.output.fileoutputformat.compress.codec");
/*  48: 95 */     if (name != null) {
/*  49:    */       try
/*  50:    */       {
/*  51: 97 */         codecClass = conf.getClassByName(name).asSubclass(CompressionCodec.class);
/*  52:    */       }
/*  53:    */       catch (ClassNotFoundException e)
/*  54:    */       {
/*  55:100 */         throw new IllegalArgumentException("Compression codec " + name + " was not found.", e);
/*  56:    */       }
/*  57:    */     }
/*  58:104 */     return codecClass;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public abstract RecordWriter<K, V> getRecordWriter(FileSystem paramFileSystem, JobConf paramJobConf, String paramString, Progressable paramProgressable)
/*  62:    */     throws IOException;
/*  63:    */   
/*  64:    */   public void checkOutputSpecs(FileSystem ignored, JobConf job)
/*  65:    */     throws FileAlreadyExistsException, InvalidJobConfException, IOException
/*  66:    */   {
/*  67:116 */     Path outDir = getOutputPath(job);
/*  68:117 */     if ((outDir == null) && (job.getNumReduceTasks() != 0)) {
/*  69:118 */       throw new InvalidJobConfException("Output directory not set in JobConf.");
/*  70:    */     }
/*  71:120 */     if (outDir != null)
/*  72:    */     {
/*  73:121 */       FileSystem fs = outDir.getFileSystem(job);
/*  74:    */       
/*  75:123 */       outDir = fs.makeQualified(outDir);
/*  76:124 */       setOutputPath(job, outDir);
/*  77:    */       
/*  78:    */ 
/*  79:127 */       TokenCache.obtainTokensForNamenodes(job.getCredentials(), new Path[] { outDir }, job);
/*  80:131 */       if (fs.exists(outDir)) {
/*  81:132 */         throw new FileAlreadyExistsException("Output directory " + outDir + " already exists");
/*  82:    */       }
/*  83:    */     }
/*  84:    */   }
/*  85:    */   
/*  86:    */   public static void setOutputPath(JobConf conf, Path outputDir)
/*  87:    */   {
/*  88:146 */     outputDir = new Path(conf.getWorkingDirectory(), outputDir);
/*  89:147 */     conf.set("mapreduce.output.fileoutputformat.outputdir", outputDir.toString());
/*  90:    */   }
/*  91:    */   
/*  92:    */   @InterfaceAudience.Private
/*  93:    */   public static void setWorkOutputPath(JobConf conf, Path outputDir)
/*  94:    */   {
/*  95:163 */     outputDir = new Path(conf.getWorkingDirectory(), outputDir);
/*  96:164 */     conf.set("mapreduce.task.output.dir", outputDir.toString());
/*  97:    */   }
/*  98:    */   
/*  99:    */   public static Path getOutputPath(JobConf conf)
/* 100:    */   {
/* 101:174 */     String name = conf.get("mapreduce.output.fileoutputformat.outputdir");
/* 102:    */     
/* 103:176 */     return name == null ? null : new Path(name);
/* 104:    */   }
/* 105:    */   
/* 106:    */   public static Path getWorkOutputPath(JobConf conf)
/* 107:    */   {
/* 108:231 */     String name = conf.get("mapreduce.task.output.dir");
/* 109:232 */     return name == null ? null : new Path(name);
/* 110:    */   }
/* 111:    */   
/* 112:    */   public static Path getTaskOutputPath(JobConf conf, String name)
/* 113:    */     throws IOException
/* 114:    */   {
/* 115:247 */     Path outputPath = getOutputPath(conf);
/* 116:248 */     if (outputPath == null) {
/* 117:249 */       throw new IOException("Undefined job output-path");
/* 118:    */     }
/* 119:252 */     OutputCommitter committer = conf.getOutputCommitter();
/* 120:253 */     Path workPath = outputPath;
/* 121:254 */     TaskAttemptContext context = new TaskAttemptContextImpl(conf, TaskAttemptID.forName(conf.get("mapreduce.task.attempt.id")));
/* 122:258 */     if ((committer instanceof FileOutputCommitter)) {
/* 123:259 */       workPath = ((FileOutputCommitter)committer).getWorkPath(context, outputPath);
/* 124:    */     }
/* 125:264 */     return new Path(workPath, name);
/* 126:    */   }
/* 127:    */   
/* 128:    */   public static String getUniqueName(JobConf conf, String name)
/* 129:    */   {
/* 130:284 */     int partition = conf.getInt("mapreduce.task.partition", -1);
/* 131:285 */     if (partition == -1) {
/* 132:286 */       throw new IllegalArgumentException("This method can only be called from within a Job");
/* 133:    */     }
/* 134:290 */     String taskType = conf.getBoolean("mapreduce.task.ismap", true) ? "m" : "r";
/* 135:    */     
/* 136:292 */     NumberFormat numberFormat = NumberFormat.getInstance();
/* 137:293 */     numberFormat.setMinimumIntegerDigits(5);
/* 138:294 */     numberFormat.setGroupingUsed(false);
/* 139:    */     
/* 140:296 */     return name + "-" + taskType + "-" + numberFormat.format(partition);
/* 141:    */   }
/* 142:    */   
/* 143:    */   public static Path getPathForCustomFile(JobConf conf, String name)
/* 144:    */   {
/* 145:315 */     return new Path(getWorkOutputPath(conf), getUniqueName(conf, name));
/* 146:    */   }
/* 147:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.FileOutputFormat
 * JD-Core Version:    0.7.0.1
 */