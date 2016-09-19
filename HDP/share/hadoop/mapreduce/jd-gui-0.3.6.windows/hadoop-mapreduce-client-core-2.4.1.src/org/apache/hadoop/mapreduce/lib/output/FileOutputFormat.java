/*   1:    */ package org.apache.hadoop.mapreduce.lib.output;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.text.NumberFormat;
/*   5:    */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*   6:    */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*   7:    */ import org.apache.hadoop.conf.Configuration;
/*   8:    */ import org.apache.hadoop.fs.FileSystem;
/*   9:    */ import org.apache.hadoop.fs.Path;
/*  10:    */ import org.apache.hadoop.io.compress.CompressionCodec;
/*  11:    */ import org.apache.hadoop.mapred.FileAlreadyExistsException;
/*  12:    */ import org.apache.hadoop.mapred.InvalidJobConfException;
/*  13:    */ import org.apache.hadoop.mapreduce.Job;
/*  14:    */ import org.apache.hadoop.mapreduce.JobContext;
/*  15:    */ import org.apache.hadoop.mapreduce.OutputCommitter;
/*  16:    */ import org.apache.hadoop.mapreduce.OutputFormat;
/*  17:    */ import org.apache.hadoop.mapreduce.RecordWriter;
/*  18:    */ import org.apache.hadoop.mapreduce.TaskAttemptContext;
/*  19:    */ import org.apache.hadoop.mapreduce.TaskAttemptID;
/*  20:    */ import org.apache.hadoop.mapreduce.TaskID;
/*  21:    */ import org.apache.hadoop.mapreduce.TaskInputOutputContext;
/*  22:    */ import org.apache.hadoop.mapreduce.security.TokenCache;
/*  23:    */ 
/*  24:    */ @InterfaceAudience.Public
/*  25:    */ @InterfaceStability.Stable
/*  26:    */ public abstract class FileOutputFormat<K, V>
/*  27:    */   extends OutputFormat<K, V>
/*  28:    */ {
/*  29: 49 */   private static final NumberFormat NUMBER_FORMAT = ;
/*  30:    */   protected static final String BASE_OUTPUT_NAME = "mapreduce.output.basename";
/*  31:    */   protected static final String PART = "part";
/*  32:    */   private FileOutputCommitter committer;
/*  33:    */   public static final String COMPRESS = "mapreduce.output.fileoutputformat.compress";
/*  34:    */   public static final String COMPRESS_CODEC = "mapreduce.output.fileoutputformat.compress.codec";
/*  35:    */   public static final String COMPRESS_TYPE = "mapreduce.output.fileoutputformat.compress.type";
/*  36:    */   public static final String OUTDIR = "mapreduce.output.fileoutputformat.outputdir";
/*  37:    */   
/*  38:    */   static
/*  39:    */   {
/*  40: 53 */     NUMBER_FORMAT.setMinimumIntegerDigits(5);
/*  41: 54 */     NUMBER_FORMAT.setGroupingUsed(false);
/*  42:    */   }
/*  43:    */   
/*  44:    */   public FileOutputFormat()
/*  45:    */   {
/*  46: 56 */     this.committer = null;
/*  47:    */   }
/*  48:    */   
/*  49:    */   @Deprecated
/*  50:    */   public static enum Counter
/*  51:    */   {
/*  52: 65 */     BYTES_WRITTEN;
/*  53:    */     
/*  54:    */     private Counter() {}
/*  55:    */   }
/*  56:    */   
/*  57:    */   public static void setCompressOutput(Job job, boolean compress)
/*  58:    */   {
/*  59: 74 */     job.getConfiguration().setBoolean("mapreduce.output.fileoutputformat.compress", compress);
/*  60:    */   }
/*  61:    */   
/*  62:    */   public static boolean getCompressOutput(JobContext job)
/*  63:    */   {
/*  64: 84 */     return job.getConfiguration().getBoolean("mapreduce.output.fileoutputformat.compress", false);
/*  65:    */   }
/*  66:    */   
/*  67:    */   public static void setOutputCompressorClass(Job job, Class<? extends CompressionCodec> codecClass)
/*  68:    */   {
/*  69: 97 */     setCompressOutput(job, true);
/*  70: 98 */     job.getConfiguration().setClass("mapreduce.output.fileoutputformat.compress.codec", codecClass, CompressionCodec.class);
/*  71:    */   }
/*  72:    */   
/*  73:    */   public static Class<? extends CompressionCodec> getOutputCompressorClass(JobContext job, Class<? extends CompressionCodec> defaultValue)
/*  74:    */   {
/*  75:114 */     Class<? extends CompressionCodec> codecClass = defaultValue;
/*  76:115 */     Configuration conf = job.getConfiguration();
/*  77:116 */     String name = conf.get("mapreduce.output.fileoutputformat.compress.codec");
/*  78:117 */     if (name != null) {
/*  79:    */       try
/*  80:    */       {
/*  81:119 */         codecClass = conf.getClassByName(name).asSubclass(CompressionCodec.class);
/*  82:    */       }
/*  83:    */       catch (ClassNotFoundException e)
/*  84:    */       {
/*  85:122 */         throw new IllegalArgumentException("Compression codec " + name + " was not found.", e);
/*  86:    */       }
/*  87:    */     }
/*  88:126 */     return codecClass;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public void checkOutputSpecs(JobContext job)
/*  92:    */     throws FileAlreadyExistsException, IOException
/*  93:    */   {
/*  94:136 */     Path outDir = getOutputPath(job);
/*  95:137 */     if (outDir == null) {
/*  96:138 */       throw new InvalidJobConfException("Output directory not set.");
/*  97:    */     }
/*  98:142 */     TokenCache.obtainTokensForNamenodes(job.getCredentials(), new Path[] { outDir }, job.getConfiguration());
/*  99:145 */     if (outDir.getFileSystem(job.getConfiguration()).exists(outDir)) {
/* 100:146 */       throw new FileAlreadyExistsException("Output directory " + outDir + " already exists");
/* 101:    */     }
/* 102:    */   }
/* 103:    */   
/* 104:    */   public static void setOutputPath(Job job, Path outputDir)
/* 105:    */   {
/* 106:    */     try
/* 107:    */     {
/* 108:160 */       outputDir = outputDir.getFileSystem(job.getConfiguration()).makeQualified(outputDir);
/* 109:    */     }
/* 110:    */     catch (IOException e)
/* 111:    */     {
/* 112:164 */       throw new RuntimeException(e);
/* 113:    */     }
/* 114:166 */     job.getConfiguration().set("mapreduce.output.fileoutputformat.outputdir", outputDir.toString());
/* 115:    */   }
/* 116:    */   
/* 117:    */   public static Path getOutputPath(JobContext job)
/* 118:    */   {
/* 119:176 */     String name = job.getConfiguration().get("mapreduce.output.fileoutputformat.outputdir");
/* 120:177 */     return name == null ? null : new Path(name);
/* 121:    */   }
/* 122:    */   
/* 123:    */   public static Path getWorkOutputPath(TaskInputOutputContext<?, ?, ?, ?> context)
/* 124:    */     throws IOException, InterruptedException
/* 125:    */   {
/* 126:222 */     FileOutputCommitter committer = (FileOutputCommitter)context.getOutputCommitter();
/* 127:    */     
/* 128:224 */     return committer.getWorkPath();
/* 129:    */   }
/* 130:    */   
/* 131:    */   public static Path getPathForWorkFile(TaskInputOutputContext<?, ?, ?, ?> context, String name, String extension)
/* 132:    */     throws IOException, InterruptedException
/* 133:    */   {
/* 134:248 */     return new Path(getWorkOutputPath(context), getUniqueFile(context, name, extension));
/* 135:    */   }
/* 136:    */   
/* 137:    */   public static synchronized String getUniqueFile(TaskAttemptContext context, String name, String extension)
/* 138:    */   {
/* 139:262 */     TaskID taskId = context.getTaskAttemptID().getTaskID();
/* 140:263 */     int partition = taskId.getId();
/* 141:264 */     StringBuilder result = new StringBuilder();
/* 142:265 */     result.append(name);
/* 143:266 */     result.append('-');
/* 144:267 */     result.append(TaskID.getRepresentingCharacter(taskId.getTaskType()));
/* 145:    */     
/* 146:269 */     result.append('-');
/* 147:270 */     result.append(NUMBER_FORMAT.format(partition));
/* 148:271 */     result.append(extension);
/* 149:272 */     return result.toString();
/* 150:    */   }
/* 151:    */   
/* 152:    */   public Path getDefaultWorkFile(TaskAttemptContext context, String extension)
/* 153:    */     throws IOException
/* 154:    */   {
/* 155:284 */     FileOutputCommitter committer = (FileOutputCommitter)getOutputCommitter(context);
/* 156:    */     
/* 157:286 */     return new Path(committer.getWorkPath(), getUniqueFile(context, getOutputName(context), extension));
/* 158:    */   }
/* 159:    */   
/* 160:    */   protected static String getOutputName(JobContext job)
/* 161:    */   {
/* 162:294 */     return job.getConfiguration().get("mapreduce.output.basename", "part");
/* 163:    */   }
/* 164:    */   
/* 165:    */   protected static void setOutputName(JobContext job, String name)
/* 166:    */   {
/* 167:301 */     job.getConfiguration().set("mapreduce.output.basename", name);
/* 168:    */   }
/* 169:    */   
/* 170:    */   public synchronized OutputCommitter getOutputCommitter(TaskAttemptContext context)
/* 171:    */     throws IOException
/* 172:    */   {
/* 173:307 */     if (this.committer == null)
/* 174:    */     {
/* 175:308 */       Path output = getOutputPath(context);
/* 176:309 */       this.committer = new FileOutputCommitter(output, context);
/* 177:    */     }
/* 178:311 */     return this.committer;
/* 179:    */   }
/* 180:    */   
/* 181:    */   public abstract RecordWriter<K, V> getRecordWriter(TaskAttemptContext paramTaskAttemptContext)
/* 182:    */     throws IOException, InterruptedException;
/* 183:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.lib.output.FileOutputFormat
 * JD-Core Version:    0.7.0.1
 */