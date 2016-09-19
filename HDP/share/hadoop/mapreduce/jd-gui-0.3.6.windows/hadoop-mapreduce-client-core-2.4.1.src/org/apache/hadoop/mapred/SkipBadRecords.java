/*   1:    */ package org.apache.hadoop.mapred;
/*   2:    */ 
/*   3:    */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*   4:    */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*   5:    */ import org.apache.hadoop.conf.Configuration;
/*   6:    */ import org.apache.hadoop.fs.Path;
/*   7:    */ 
/*   8:    */ @InterfaceAudience.Public
/*   9:    */ @InterfaceStability.Stable
/*  10:    */ public class SkipBadRecords
/*  11:    */ {
/*  12:    */   public static final String COUNTER_GROUP = "SkippingTaskCounters";
/*  13:    */   public static final String COUNTER_MAP_PROCESSED_RECORDS = "MapProcessedRecords";
/*  14:    */   public static final String COUNTER_REDUCE_PROCESSED_GROUPS = "ReduceProcessedGroups";
/*  15:    */   private static final String ATTEMPTS_TO_START_SKIPPING = "mapreduce.task.skip.start.attempts";
/*  16:    */   private static final String AUTO_INCR_MAP_PROC_COUNT = "mapreduce.map.skip.proc-count.auto-incr";
/*  17:    */   private static final String AUTO_INCR_REDUCE_PROC_COUNT = "mapreduce.reduce.skip.proc-count.auto-incr";
/*  18:    */   private static final String OUT_PATH = "mapreduce.job.skip.outdir";
/*  19:    */   private static final String MAPPER_MAX_SKIP_RECORDS = "mapreduce.map.skip.maxrecords";
/*  20:    */   private static final String REDUCER_MAX_SKIP_GROUPS = "mapreduce.reduce.skip.maxgroups";
/*  21:    */   
/*  22:    */   public static int getAttemptsToStartSkipping(Configuration conf)
/*  23:    */   {
/*  24:103 */     return conf.getInt("mapreduce.task.skip.start.attempts", 2);
/*  25:    */   }
/*  26:    */   
/*  27:    */   public static void setAttemptsToStartSkipping(Configuration conf, int attemptsToStartSkipping)
/*  28:    */   {
/*  29:120 */     conf.setInt("mapreduce.task.skip.start.attempts", attemptsToStartSkipping);
/*  30:    */   }
/*  31:    */   
/*  32:    */   public static boolean getAutoIncrMapperProcCount(Configuration conf)
/*  33:    */   {
/*  34:138 */     return conf.getBoolean("mapreduce.map.skip.proc-count.auto-incr", true);
/*  35:    */   }
/*  36:    */   
/*  37:    */   public static void setAutoIncrMapperProcCount(Configuration conf, boolean autoIncr)
/*  38:    */   {
/*  39:156 */     conf.setBoolean("mapreduce.map.skip.proc-count.auto-incr", autoIncr);
/*  40:    */   }
/*  41:    */   
/*  42:    */   public static boolean getAutoIncrReducerProcCount(Configuration conf)
/*  43:    */   {
/*  44:174 */     return conf.getBoolean("mapreduce.reduce.skip.proc-count.auto-incr", true);
/*  45:    */   }
/*  46:    */   
/*  47:    */   public static void setAutoIncrReducerProcCount(Configuration conf, boolean autoIncr)
/*  48:    */   {
/*  49:192 */     conf.setBoolean("mapreduce.reduce.skip.proc-count.auto-incr", autoIncr);
/*  50:    */   }
/*  51:    */   
/*  52:    */   public static Path getSkipOutputPath(Configuration conf)
/*  53:    */   {
/*  54:205 */     String name = conf.get("mapreduce.job.skip.outdir");
/*  55:206 */     if (name != null)
/*  56:    */     {
/*  57:207 */       if ("none".equals(name)) {
/*  58:208 */         return null;
/*  59:    */       }
/*  60:210 */       return new Path(name);
/*  61:    */     }
/*  62:212 */     Path outPath = FileOutputFormat.getOutputPath(new JobConf(conf));
/*  63:213 */     return outPath == null ? null : new Path(outPath, "_logs/skip");
/*  64:    */   }
/*  65:    */   
/*  66:    */   public static void setSkipOutputPath(JobConf conf, Path path)
/*  67:    */   {
/*  68:226 */     String pathStr = null;
/*  69:227 */     if (path == null) {
/*  70:228 */       pathStr = "none";
/*  71:    */     } else {
/*  72:230 */       pathStr = path.toString();
/*  73:    */     }
/*  74:232 */     conf.set("mapreduce.job.skip.outdir", pathStr);
/*  75:    */   }
/*  76:    */   
/*  77:    */   public static long getMapperMaxSkipRecords(Configuration conf)
/*  78:    */   {
/*  79:251 */     return conf.getLong("mapreduce.map.skip.maxrecords", 0L);
/*  80:    */   }
/*  81:    */   
/*  82:    */   public static void setMapperMaxSkipRecords(Configuration conf, long maxSkipRecs)
/*  83:    */   {
/*  84:271 */     conf.setLong("mapreduce.map.skip.maxrecords", maxSkipRecs);
/*  85:    */   }
/*  86:    */   
/*  87:    */   public static long getReducerMaxSkipGroups(Configuration conf)
/*  88:    */   {
/*  89:290 */     return conf.getLong("mapreduce.reduce.skip.maxgroups", 0L);
/*  90:    */   }
/*  91:    */   
/*  92:    */   public static void setReducerMaxSkipGroups(Configuration conf, long maxSkipGrps)
/*  93:    */   {
/*  94:310 */     conf.setLong("mapreduce.reduce.skip.maxgroups", maxSkipGrps);
/*  95:    */   }
/*  96:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.SkipBadRecords
 * JD-Core Version:    0.7.0.1
 */