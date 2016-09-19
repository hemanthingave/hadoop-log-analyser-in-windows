/*    1:     */ package org.apache.hadoop.mapred;
/*    2:     */ 
/*    3:     */ import java.io.IOException;
/*    4:     */ import java.util.regex.Pattern;
/*    5:     */ import org.apache.commons.logging.Log;
/*    6:     */ import org.apache.commons.logging.LogFactory;
/*    7:     */ import org.apache.hadoop.classification.InterfaceAudience.Private;
/*    8:     */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*    9:     */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*   10:     */ import org.apache.hadoop.conf.Configuration;
/*   11:     */ import org.apache.hadoop.conf.Configuration.IntegerRanges;
/*   12:     */ import org.apache.hadoop.fs.FileSystem;
/*   13:     */ import org.apache.hadoop.fs.LocalFileSystem;
/*   14:     */ import org.apache.hadoop.fs.Path;
/*   15:     */ import org.apache.hadoop.io.LongWritable;
/*   16:     */ import org.apache.hadoop.io.RawComparator;
/*   17:     */ import org.apache.hadoop.io.Text;
/*   18:     */ import org.apache.hadoop.io.WritableComparable;
/*   19:     */ import org.apache.hadoop.io.WritableComparator;
/*   20:     */ import org.apache.hadoop.io.compress.CompressionCodec;
/*   21:     */ import org.apache.hadoop.mapred.lib.HashPartitioner;
/*   22:     */ import org.apache.hadoop.mapred.lib.IdentityMapper;
/*   23:     */ import org.apache.hadoop.mapred.lib.IdentityReducer;
/*   24:     */ import org.apache.hadoop.mapred.lib.KeyFieldBasedComparator;
/*   25:     */ import org.apache.hadoop.mapred.lib.KeyFieldBasedPartitioner;
/*   26:     */ import org.apache.hadoop.mapreduce.util.ConfigUtil;
/*   27:     */ import org.apache.hadoop.security.Credentials;
/*   28:     */ import org.apache.hadoop.util.ClassUtil;
/*   29:     */ import org.apache.hadoop.util.ReflectionUtils;
/*   30:     */ import org.apache.log4j.Level;
/*   31:     */ 
/*   32:     */ @InterfaceAudience.Public
/*   33:     */ @InterfaceStability.Stable
/*   34:     */ public class JobConf
/*   35:     */   extends Configuration
/*   36:     */ {
/*   37: 115 */   private static final Log LOG = LogFactory.getLog(JobConf.class);
/*   38:     */   @Deprecated
/*   39:     */   public static final String MAPRED_TASK_MAXVMEM_PROPERTY = "mapred.task.maxvmem";
/*   40:     */   @Deprecated
/*   41:     */   public static final String UPPER_LIMIT_ON_TASK_VMEM_PROPERTY = "mapred.task.limit.maxvmem";
/*   42:     */   @Deprecated
/*   43:     */   public static final String MAPRED_TASK_DEFAULT_MAXVMEM_PROPERTY = "mapred.task.default.maxvmem";
/*   44:     */   @Deprecated
/*   45:     */   public static final String MAPRED_TASK_MAXPMEM_PROPERTY = "mapred.task.maxpmem";
/*   46:     */   public static final long DISABLED_MEMORY_LIMIT = -1L;
/*   47:     */   public static final String MAPRED_LOCAL_DIR_PROPERTY = "mapreduce.cluster.local.dir";
/*   48:     */   public static final String DEFAULT_QUEUE_NAME = "default";
/*   49:     */   static final String MAPREDUCE_JOB_MAP_MEMORY_MB_PROPERTY = "mapreduce.map.memory.mb";
/*   50:     */   static final String MAPREDUCE_JOB_REDUCE_MEMORY_MB_PROPERTY = "mapreduce.reduce.memory.mb";
/*   51:     */   @Deprecated
/*   52:     */   public static final String MAPRED_JOB_MAP_MEMORY_MB_PROPERTY = "mapred.job.map.memory.mb";
/*   53:     */   @Deprecated
/*   54:     */   public static final String MAPRED_JOB_REDUCE_MEMORY_MB_PROPERTY = "mapred.job.reduce.memory.mb";
/*   55:     */   
/*   56:     */   static
/*   57:     */   {
/*   58: 118 */     ConfigUtil.loadResources();
/*   59:     */   }
/*   60:     */   
/*   61: 190 */   public static final Pattern UNPACK_JAR_PATTERN_DEFAULT = Pattern.compile("(?:classes/|lib/).*");
/*   62:     */   @Deprecated
/*   63:     */   public static final String MAPRED_TASK_JAVA_OPTS = "mapred.child.java.opts";
/*   64:     */   public static final String MAPRED_MAP_TASK_JAVA_OPTS = "mapreduce.map.java.opts";
/*   65:     */   public static final String MAPRED_REDUCE_TASK_JAVA_OPTS = "mapreduce.reduce.java.opts";
/*   66:     */   public static final String DEFAULT_MAPRED_TASK_JAVA_OPTS = "-Xmx200m";
/*   67:     */   @Deprecated
/*   68:     */   public static final String MAPRED_TASK_ULIMIT = "mapred.child.ulimit";
/*   69:     */   @Deprecated
/*   70:     */   public static final String MAPRED_MAP_TASK_ULIMIT = "mapreduce.map.ulimit";
/*   71:     */   @Deprecated
/*   72:     */   public static final String MAPRED_REDUCE_TASK_ULIMIT = "mapreduce.reduce.ulimit";
/*   73:     */   @Deprecated
/*   74:     */   public static final String MAPRED_TASK_ENV = "mapred.child.env";
/*   75:     */   public static final String MAPRED_MAP_TASK_ENV = "mapreduce.map.env";
/*   76:     */   public static final String MAPRED_REDUCE_TASK_ENV = "mapreduce.reduce.env";
/*   77: 331 */   private Credentials credentials = new Credentials();
/*   78:     */   public static final String MAPRED_MAP_TASK_LOG_LEVEL = "mapreduce.map.log.level";
/*   79:     */   public static final String MAPRED_REDUCE_TASK_LOG_LEVEL = "mapreduce.reduce.log.level";
/*   80: 354 */   public static final Level DEFAULT_LOG_LEVEL = Level.INFO;
/*   81:     */   @Deprecated
/*   82:     */   public static final String WORKFLOW_ID = "mapreduce.workflow.id";
/*   83:     */   @Deprecated
/*   84:     */   public static final String WORKFLOW_NAME = "mapreduce.workflow.name";
/*   85:     */   @Deprecated
/*   86:     */   public static final String WORKFLOW_NODE_NAME = "mapreduce.workflow.node.name";
/*   87:     */   @Deprecated
/*   88:     */   public static final String WORKFLOW_ADJACENCY_PREFIX_STRING = "mapreduce.workflow.adjacency.";
/*   89:     */   @Deprecated
/*   90:     */   public static final String WORKFLOW_ADJACENCY_PREFIX_PATTERN = "^mapreduce\\.workflow\\.adjacency\\..+";
/*   91:     */   @Deprecated
/*   92:     */   public static final String WORKFLOW_TAGS = "mapreduce.workflow.tags";
/*   93:     */   @Deprecated
/*   94:     */   public static final String MAPREDUCE_RECOVER_JOB = "mapreduce.job.restart.recover";
/*   95:     */   @Deprecated
/*   96:     */   public static final boolean DEFAULT_MAPREDUCE_RECOVER_JOB = true;
/*   97:     */   
/*   98:     */   public JobConf()
/*   99:     */   {
/*  100: 420 */     checkAndWarnDeprecation();
/*  101:     */   }
/*  102:     */   
/*  103:     */   public JobConf(Class exampleClass)
/*  104:     */   {
/*  105: 429 */     setJarByClass(exampleClass);
/*  106: 430 */     checkAndWarnDeprecation();
/*  107:     */   }
/*  108:     */   
/*  109:     */   public JobConf(Configuration conf)
/*  110:     */   {
/*  111: 439 */     super(conf);
/*  112: 441 */     if ((conf instanceof JobConf))
/*  113:     */     {
/*  114: 442 */       JobConf that = (JobConf)conf;
/*  115: 443 */       this.credentials = that.credentials;
/*  116:     */     }
/*  117: 446 */     checkAndWarnDeprecation();
/*  118:     */   }
/*  119:     */   
/*  120:     */   public JobConf(Configuration conf, Class exampleClass)
/*  121:     */   {
/*  122: 456 */     this(conf);
/*  123: 457 */     setJarByClass(exampleClass);
/*  124:     */   }
/*  125:     */   
/*  126:     */   public JobConf(String config)
/*  127:     */   {
/*  128: 466 */     this(new Path(config));
/*  129:     */   }
/*  130:     */   
/*  131:     */   public JobConf(Path config)
/*  132:     */   {
/*  133: 475 */     addResource(config);
/*  134: 476 */     checkAndWarnDeprecation();
/*  135:     */   }
/*  136:     */   
/*  137:     */   public JobConf(boolean loadDefaults)
/*  138:     */   {
/*  139: 488 */     super(loadDefaults);
/*  140: 489 */     checkAndWarnDeprecation();
/*  141:     */   }
/*  142:     */   
/*  143:     */   public Credentials getCredentials()
/*  144:     */   {
/*  145: 497 */     return this.credentials;
/*  146:     */   }
/*  147:     */   
/*  148:     */   @InterfaceAudience.Private
/*  149:     */   public void setCredentials(Credentials credentials)
/*  150:     */   {
/*  151: 502 */     this.credentials = credentials;
/*  152:     */   }
/*  153:     */   
/*  154:     */   public String getJar()
/*  155:     */   {
/*  156: 510 */     return get("mapreduce.job.jar");
/*  157:     */   }
/*  158:     */   
/*  159:     */   public void setJar(String jar)
/*  160:     */   {
/*  161: 517 */     set("mapreduce.job.jar", jar);
/*  162:     */   }
/*  163:     */   
/*  164:     */   public Pattern getJarUnpackPattern()
/*  165:     */   {
/*  166: 523 */     return getPattern("mapreduce.job.jar.unpack.pattern", UNPACK_JAR_PATTERN_DEFAULT);
/*  167:     */   }
/*  168:     */   
/*  169:     */   public void setJarByClass(Class cls)
/*  170:     */   {
/*  171: 533 */     String jar = ClassUtil.findContainingJar(cls);
/*  172: 534 */     if (jar != null) {
/*  173: 535 */       setJar(jar);
/*  174:     */     }
/*  175:     */   }
/*  176:     */   
/*  177:     */   public String[] getLocalDirs()
/*  178:     */     throws IOException
/*  179:     */   {
/*  180: 540 */     return getTrimmedStrings("mapreduce.cluster.local.dir");
/*  181:     */   }
/*  182:     */   
/*  183:     */   @Deprecated
/*  184:     */   public void deleteLocalFiles()
/*  185:     */     throws IOException
/*  186:     */   {
/*  187: 548 */     String[] localDirs = getLocalDirs();
/*  188: 549 */     for (int i = 0; i < localDirs.length; i++) {
/*  189: 550 */       FileSystem.getLocal(this).delete(new Path(localDirs[i]), true);
/*  190:     */     }
/*  191:     */   }
/*  192:     */   
/*  193:     */   public void deleteLocalFiles(String subdir)
/*  194:     */     throws IOException
/*  195:     */   {
/*  196: 555 */     String[] localDirs = getLocalDirs();
/*  197: 556 */     for (int i = 0; i < localDirs.length; i++) {
/*  198: 557 */       FileSystem.getLocal(this).delete(new Path(localDirs[i], subdir), true);
/*  199:     */     }
/*  200:     */   }
/*  201:     */   
/*  202:     */   public Path getLocalPath(String pathString)
/*  203:     */     throws IOException
/*  204:     */   {
/*  205: 566 */     return getLocalPath("mapreduce.cluster.local.dir", pathString);
/*  206:     */   }
/*  207:     */   
/*  208:     */   public String getUser()
/*  209:     */   {
/*  210: 575 */     return get("mapreduce.job.user.name");
/*  211:     */   }
/*  212:     */   
/*  213:     */   public void setUser(String user)
/*  214:     */   {
/*  215: 584 */     set("mapreduce.job.user.name", user);
/*  216:     */   }
/*  217:     */   
/*  218:     */   public void setKeepFailedTaskFiles(boolean keep)
/*  219:     */   {
/*  220: 598 */     setBoolean("mapreduce.task.files.preserve.failedtasks", keep);
/*  221:     */   }
/*  222:     */   
/*  223:     */   public boolean getKeepFailedTaskFiles()
/*  224:     */   {
/*  225: 607 */     return getBoolean("mapreduce.task.files.preserve.failedtasks", false);
/*  226:     */   }
/*  227:     */   
/*  228:     */   public void setKeepTaskFilesPattern(String pattern)
/*  229:     */   {
/*  230: 619 */     set("mapreduce.task.files.preserve.filepattern", pattern);
/*  231:     */   }
/*  232:     */   
/*  233:     */   public String getKeepTaskFilesPattern()
/*  234:     */   {
/*  235: 629 */     return get("mapreduce.task.files.preserve.filepattern");
/*  236:     */   }
/*  237:     */   
/*  238:     */   public void setWorkingDirectory(Path dir)
/*  239:     */   {
/*  240: 638 */     dir = new Path(getWorkingDirectory(), dir);
/*  241: 639 */     set("mapreduce.job.working.dir", dir.toString());
/*  242:     */   }
/*  243:     */   
/*  244:     */   public Path getWorkingDirectory()
/*  245:     */   {
/*  246: 648 */     String name = get("mapreduce.job.working.dir");
/*  247: 649 */     if (name != null) {
/*  248: 650 */       return new Path(name);
/*  249:     */     }
/*  250:     */     try
/*  251:     */     {
/*  252: 653 */       Path dir = FileSystem.get(this).getWorkingDirectory();
/*  253: 654 */       set("mapreduce.job.working.dir", dir.toString());
/*  254: 655 */       return dir;
/*  255:     */     }
/*  256:     */     catch (IOException e)
/*  257:     */     {
/*  258: 657 */       throw new RuntimeException(e);
/*  259:     */     }
/*  260:     */   }
/*  261:     */   
/*  262:     */   public void setNumTasksToExecutePerJvm(int numTasks)
/*  263:     */   {
/*  264: 669 */     setInt("mapreduce.job.jvm.numtasks", numTasks);
/*  265:     */   }
/*  266:     */   
/*  267:     */   public int getNumTasksToExecutePerJvm()
/*  268:     */   {
/*  269: 676 */     return getInt("mapreduce.job.jvm.numtasks", 1);
/*  270:     */   }
/*  271:     */   
/*  272:     */   public InputFormat getInputFormat()
/*  273:     */   {
/*  274: 686 */     return (InputFormat)ReflectionUtils.newInstance(getClass("mapred.input.format.class", TextInputFormat.class, InputFormat.class), this);
/*  275:     */   }
/*  276:     */   
/*  277:     */   public void setInputFormat(Class<? extends InputFormat> theClass)
/*  278:     */   {
/*  279: 699 */     setClass("mapred.input.format.class", theClass, InputFormat.class);
/*  280:     */   }
/*  281:     */   
/*  282:     */   public OutputFormat getOutputFormat()
/*  283:     */   {
/*  284: 709 */     return (OutputFormat)ReflectionUtils.newInstance(getClass("mapred.output.format.class", TextOutputFormat.class, OutputFormat.class), this);
/*  285:     */   }
/*  286:     */   
/*  287:     */   public OutputCommitter getOutputCommitter()
/*  288:     */   {
/*  289: 722 */     return (OutputCommitter)ReflectionUtils.newInstance(getClass("mapred.output.committer.class", FileOutputCommitter.class, OutputCommitter.class), this);
/*  290:     */   }
/*  291:     */   
/*  292:     */   public void setOutputCommitter(Class<? extends OutputCommitter> theClass)
/*  293:     */   {
/*  294: 734 */     setClass("mapred.output.committer.class", theClass, OutputCommitter.class);
/*  295:     */   }
/*  296:     */   
/*  297:     */   public void setOutputFormat(Class<? extends OutputFormat> theClass)
/*  298:     */   {
/*  299: 744 */     setClass("mapred.output.format.class", theClass, OutputFormat.class);
/*  300:     */   }
/*  301:     */   
/*  302:     */   public void setCompressMapOutput(boolean compress)
/*  303:     */   {
/*  304: 753 */     setBoolean("mapreduce.map.output.compress", compress);
/*  305:     */   }
/*  306:     */   
/*  307:     */   public boolean getCompressMapOutput()
/*  308:     */   {
/*  309: 763 */     return getBoolean("mapreduce.map.output.compress", false);
/*  310:     */   }
/*  311:     */   
/*  312:     */   public void setMapOutputCompressorClass(Class<? extends CompressionCodec> codecClass)
/*  313:     */   {
/*  314: 774 */     setCompressMapOutput(true);
/*  315: 775 */     setClass("mapreduce.map.output.compress.codec", codecClass, CompressionCodec.class);
/*  316:     */   }
/*  317:     */   
/*  318:     */   public Class<? extends CompressionCodec> getMapOutputCompressorClass(Class<? extends CompressionCodec> defaultValue)
/*  319:     */   {
/*  320: 789 */     Class<? extends CompressionCodec> codecClass = defaultValue;
/*  321: 790 */     String name = get("mapreduce.map.output.compress.codec");
/*  322: 791 */     if (name != null) {
/*  323:     */       try
/*  324:     */       {
/*  325: 793 */         codecClass = getClassByName(name).asSubclass(CompressionCodec.class);
/*  326:     */       }
/*  327:     */       catch (ClassNotFoundException e)
/*  328:     */       {
/*  329: 795 */         throw new IllegalArgumentException("Compression codec " + name + " was not found.", e);
/*  330:     */       }
/*  331:     */     }
/*  332: 799 */     return codecClass;
/*  333:     */   }
/*  334:     */   
/*  335:     */   public Class<?> getMapOutputKeyClass()
/*  336:     */   {
/*  337: 810 */     Class<?> retv = getClass("mapreduce.map.output.key.class", null, Object.class);
/*  338: 811 */     if (retv == null) {
/*  339: 812 */       retv = getOutputKeyClass();
/*  340:     */     }
/*  341: 814 */     return retv;
/*  342:     */   }
/*  343:     */   
/*  344:     */   public void setMapOutputKeyClass(Class<?> theClass)
/*  345:     */   {
/*  346: 825 */     setClass("mapreduce.map.output.key.class", theClass, Object.class);
/*  347:     */   }
/*  348:     */   
/*  349:     */   public Class<?> getMapOutputValueClass()
/*  350:     */   {
/*  351: 836 */     Class<?> retv = getClass("mapreduce.map.output.value.class", null, Object.class);
/*  352: 838 */     if (retv == null) {
/*  353: 839 */       retv = getOutputValueClass();
/*  354:     */     }
/*  355: 841 */     return retv;
/*  356:     */   }
/*  357:     */   
/*  358:     */   public void setMapOutputValueClass(Class<?> theClass)
/*  359:     */   {
/*  360: 852 */     setClass("mapreduce.map.output.value.class", theClass, Object.class);
/*  361:     */   }
/*  362:     */   
/*  363:     */   public Class<?> getOutputKeyClass()
/*  364:     */   {
/*  365: 861 */     return getClass("mapreduce.job.output.key.class", LongWritable.class, Object.class);
/*  366:     */   }
/*  367:     */   
/*  368:     */   public void setOutputKeyClass(Class<?> theClass)
/*  369:     */   {
/*  370: 871 */     setClass("mapreduce.job.output.key.class", theClass, Object.class);
/*  371:     */   }
/*  372:     */   
/*  373:     */   public RawComparator getOutputKeyComparator()
/*  374:     */   {
/*  375: 880 */     Class<? extends RawComparator> theClass = getClass("mapreduce.job.output.key.comparator.class", null, RawComparator.class);
/*  376: 882 */     if (theClass != null) {
/*  377: 883 */       return (RawComparator)ReflectionUtils.newInstance(theClass, this);
/*  378:     */     }
/*  379: 884 */     return WritableComparator.get(getMapOutputKeyClass().asSubclass(WritableComparable.class));
/*  380:     */   }
/*  381:     */   
/*  382:     */   public void setOutputKeyComparatorClass(Class<? extends RawComparator> theClass)
/*  383:     */   {
/*  384: 895 */     setClass("mapreduce.job.output.key.comparator.class", theClass, RawComparator.class);
/*  385:     */   }
/*  386:     */   
/*  387:     */   public void setKeyFieldComparatorOptions(String keySpec)
/*  388:     */   {
/*  389: 915 */     setOutputKeyComparatorClass(KeyFieldBasedComparator.class);
/*  390: 916 */     set(KeyFieldBasedComparator.COMPARATOR_OPTIONS, keySpec);
/*  391:     */   }
/*  392:     */   
/*  393:     */   public String getKeyFieldComparatorOption()
/*  394:     */   {
/*  395: 923 */     return get(KeyFieldBasedComparator.COMPARATOR_OPTIONS);
/*  396:     */   }
/*  397:     */   
/*  398:     */   public void setKeyFieldPartitionerOptions(String keySpec)
/*  399:     */   {
/*  400: 940 */     setPartitionerClass(KeyFieldBasedPartitioner.class);
/*  401: 941 */     set(KeyFieldBasedPartitioner.PARTITIONER_OPTIONS, keySpec);
/*  402:     */   }
/*  403:     */   
/*  404:     */   public String getKeyFieldPartitionerOption()
/*  405:     */   {
/*  406: 948 */     return get(KeyFieldBasedPartitioner.PARTITIONER_OPTIONS);
/*  407:     */   }
/*  408:     */   
/*  409:     */   public RawComparator getCombinerKeyGroupingComparator()
/*  410:     */   {
/*  411: 959 */     Class<? extends RawComparator> theClass = getClass("mapreduce.job.combiner.group.comparator.class", null, RawComparator.class);
/*  412: 961 */     if (theClass == null) {
/*  413: 962 */       return getOutputKeyComparator();
/*  414:     */     }
/*  415: 965 */     return (RawComparator)ReflectionUtils.newInstance(theClass, this);
/*  416:     */   }
/*  417:     */   
/*  418:     */   public RawComparator getOutputValueGroupingComparator()
/*  419:     */   {
/*  420: 976 */     Class<? extends RawComparator> theClass = getClass("mapreduce.job.output.group.comparator.class", null, RawComparator.class);
/*  421: 978 */     if (theClass == null) {
/*  422: 979 */       return getOutputKeyComparator();
/*  423:     */     }
/*  424: 982 */     return (RawComparator)ReflectionUtils.newInstance(theClass, this);
/*  425:     */   }
/*  426:     */   
/*  427:     */   public void setCombinerKeyGroupingComparator(Class<? extends RawComparator> theClass)
/*  428:     */   {
/*  429:1012 */     setClass("mapreduce.job.combiner.group.comparator.class", theClass, RawComparator.class);
/*  430:     */   }
/*  431:     */   
/*  432:     */   public void setOutputValueGroupingComparator(Class<? extends RawComparator> theClass)
/*  433:     */   {
/*  434:1044 */     setClass("mapreduce.job.output.group.comparator.class", theClass, RawComparator.class);
/*  435:     */   }
/*  436:     */   
/*  437:     */   public boolean getUseNewMapper()
/*  438:     */   {
/*  439:1054 */     return getBoolean("mapred.mapper.new-api", false);
/*  440:     */   }
/*  441:     */   
/*  442:     */   public void setUseNewMapper(boolean flag)
/*  443:     */   {
/*  444:1062 */     setBoolean("mapred.mapper.new-api", flag);
/*  445:     */   }
/*  446:     */   
/*  447:     */   public boolean getUseNewReducer()
/*  448:     */   {
/*  449:1071 */     return getBoolean("mapred.reducer.new-api", false);
/*  450:     */   }
/*  451:     */   
/*  452:     */   public void setUseNewReducer(boolean flag)
/*  453:     */   {
/*  454:1079 */     setBoolean("mapred.reducer.new-api", flag);
/*  455:     */   }
/*  456:     */   
/*  457:     */   public Class<?> getOutputValueClass()
/*  458:     */   {
/*  459:1088 */     return getClass("mapreduce.job.output.value.class", Text.class, Object.class);
/*  460:     */   }
/*  461:     */   
/*  462:     */   public void setOutputValueClass(Class<?> theClass)
/*  463:     */   {
/*  464:1097 */     setClass("mapreduce.job.output.value.class", theClass, Object.class);
/*  465:     */   }
/*  466:     */   
/*  467:     */   public Class<? extends Mapper> getMapperClass()
/*  468:     */   {
/*  469:1106 */     return getClass("mapred.mapper.class", IdentityMapper.class, Mapper.class);
/*  470:     */   }
/*  471:     */   
/*  472:     */   public void setMapperClass(Class<? extends Mapper> theClass)
/*  473:     */   {
/*  474:1115 */     setClass("mapred.mapper.class", theClass, Mapper.class);
/*  475:     */   }
/*  476:     */   
/*  477:     */   public Class<? extends MapRunnable> getMapRunnerClass()
/*  478:     */   {
/*  479:1124 */     return getClass("mapred.map.runner.class", MapRunner.class, MapRunnable.class);
/*  480:     */   }
/*  481:     */   
/*  482:     */   public void setMapRunnerClass(Class<? extends MapRunnable> theClass)
/*  483:     */   {
/*  484:1136 */     setClass("mapred.map.runner.class", theClass, MapRunnable.class);
/*  485:     */   }
/*  486:     */   
/*  487:     */   public Class<? extends Partitioner> getPartitionerClass()
/*  488:     */   {
/*  489:1146 */     return getClass("mapred.partitioner.class", HashPartitioner.class, Partitioner.class);
/*  490:     */   }
/*  491:     */   
/*  492:     */   public void setPartitionerClass(Class<? extends Partitioner> theClass)
/*  493:     */   {
/*  494:1157 */     setClass("mapred.partitioner.class", theClass, Partitioner.class);
/*  495:     */   }
/*  496:     */   
/*  497:     */   public Class<? extends Reducer> getReducerClass()
/*  498:     */   {
/*  499:1166 */     return getClass("mapred.reducer.class", IdentityReducer.class, Reducer.class);
/*  500:     */   }
/*  501:     */   
/*  502:     */   public void setReducerClass(Class<? extends Reducer> theClass)
/*  503:     */   {
/*  504:1176 */     setClass("mapred.reducer.class", theClass, Reducer.class);
/*  505:     */   }
/*  506:     */   
/*  507:     */   public Class<? extends Reducer> getCombinerClass()
/*  508:     */   {
/*  509:1187 */     return getClass("mapred.combiner.class", null, Reducer.class);
/*  510:     */   }
/*  511:     */   
/*  512:     */   public void setCombinerClass(Class<? extends Reducer> theClass)
/*  513:     */   {
/*  514:1214 */     setClass("mapred.combiner.class", theClass, Reducer.class);
/*  515:     */   }
/*  516:     */   
/*  517:     */   public boolean getSpeculativeExecution()
/*  518:     */   {
/*  519:1225 */     return (getMapSpeculativeExecution()) || (getReduceSpeculativeExecution());
/*  520:     */   }
/*  521:     */   
/*  522:     */   public void setSpeculativeExecution(boolean speculativeExecution)
/*  523:     */   {
/*  524:1235 */     setMapSpeculativeExecution(speculativeExecution);
/*  525:1236 */     setReduceSpeculativeExecution(speculativeExecution);
/*  526:     */   }
/*  527:     */   
/*  528:     */   public boolean getMapSpeculativeExecution()
/*  529:     */   {
/*  530:1248 */     return getBoolean("mapreduce.map.speculative", true);
/*  531:     */   }
/*  532:     */   
/*  533:     */   public void setMapSpeculativeExecution(boolean speculativeExecution)
/*  534:     */   {
/*  535:1259 */     setBoolean("mapreduce.map.speculative", speculativeExecution);
/*  536:     */   }
/*  537:     */   
/*  538:     */   public boolean getReduceSpeculativeExecution()
/*  539:     */   {
/*  540:1271 */     return getBoolean("mapreduce.reduce.speculative", true);
/*  541:     */   }
/*  542:     */   
/*  543:     */   public void setReduceSpeculativeExecution(boolean speculativeExecution)
/*  544:     */   {
/*  545:1282 */     setBoolean("mapreduce.reduce.speculative", speculativeExecution);
/*  546:     */   }
/*  547:     */   
/*  548:     */   public int getNumMapTasks()
/*  549:     */   {
/*  550:1292 */     return getInt("mapreduce.job.maps", 1);
/*  551:     */   }
/*  552:     */   
/*  553:     */   public void setNumMapTasks(int n)
/*  554:     */   {
/*  555:1332 */     setInt("mapreduce.job.maps", n);
/*  556:     */   }
/*  557:     */   
/*  558:     */   public int getNumReduceTasks()
/*  559:     */   {
/*  560:1340 */     return getInt("mapreduce.job.reduces", 1);
/*  561:     */   }
/*  562:     */   
/*  563:     */   public void setNumReduceTasks(int n)
/*  564:     */   {
/*  565:1376 */     setInt("mapreduce.job.reduces", n);
/*  566:     */   }
/*  567:     */   
/*  568:     */   public int getMaxMapAttempts()
/*  569:     */   {
/*  570:1386 */     return getInt("mapreduce.map.maxattempts", 4);
/*  571:     */   }
/*  572:     */   
/*  573:     */   public void setMaxMapAttempts(int n)
/*  574:     */   {
/*  575:1396 */     setInt("mapreduce.map.maxattempts", n);
/*  576:     */   }
/*  577:     */   
/*  578:     */   public int getMaxReduceAttempts()
/*  579:     */   {
/*  580:1407 */     return getInt("mapreduce.reduce.maxattempts", 4);
/*  581:     */   }
/*  582:     */   
/*  583:     */   public void setMaxReduceAttempts(int n)
/*  584:     */   {
/*  585:1416 */     setInt("mapreduce.reduce.maxattempts", n);
/*  586:     */   }
/*  587:     */   
/*  588:     */   public String getJobName()
/*  589:     */   {
/*  590:1426 */     return get("mapreduce.job.name", "");
/*  591:     */   }
/*  592:     */   
/*  593:     */   public void setJobName(String name)
/*  594:     */   {
/*  595:1435 */     set("mapreduce.job.name", name);
/*  596:     */   }
/*  597:     */   
/*  598:     */   @Deprecated
/*  599:     */   public String getSessionId()
/*  600:     */   {
/*  601:1455 */     return get("session.id", "");
/*  602:     */   }
/*  603:     */   
/*  604:     */   @Deprecated
/*  605:     */   public void setSessionId(String sessionId)
/*  606:     */   {
/*  607:1465 */     set("session.id", sessionId);
/*  608:     */   }
/*  609:     */   
/*  610:     */   public void setMaxTaskFailuresPerTracker(int noFailures)
/*  611:     */   {
/*  612:1476 */     setInt("mapreduce.job.maxtaskfailures.per.tracker", noFailures);
/*  613:     */   }
/*  614:     */   
/*  615:     */   public int getMaxTaskFailuresPerTracker()
/*  616:     */   {
/*  617:1487 */     return getInt("mapreduce.job.maxtaskfailures.per.tracker", 3);
/*  618:     */   }
/*  619:     */   
/*  620:     */   public int getMaxMapTaskFailuresPercent()
/*  621:     */   {
/*  622:1504 */     return getInt("mapreduce.map.failures.maxpercent", 0);
/*  623:     */   }
/*  624:     */   
/*  625:     */   public void setMaxMapTaskFailuresPercent(int percent)
/*  626:     */   {
/*  627:1518 */     setInt("mapreduce.map.failures.maxpercent", percent);
/*  628:     */   }
/*  629:     */   
/*  630:     */   public int getMaxReduceTaskFailuresPercent()
/*  631:     */   {
/*  632:1535 */     return getInt("mapreduce.reduce.failures.maxpercent", 0);
/*  633:     */   }
/*  634:     */   
/*  635:     */   public void setMaxReduceTaskFailuresPercent(int percent)
/*  636:     */   {
/*  637:1549 */     setInt("mapreduce.reduce.failures.maxpercent", percent);
/*  638:     */   }
/*  639:     */   
/*  640:     */   public void setJobPriority(JobPriority prio)
/*  641:     */   {
/*  642:1558 */     set("mapreduce.job.priority", prio.toString());
/*  643:     */   }
/*  644:     */   
/*  645:     */   public JobPriority getJobPriority()
/*  646:     */   {
/*  647:1567 */     String prio = get("mapreduce.job.priority");
/*  648:1568 */     if (prio == null) {
/*  649:1569 */       return JobPriority.NORMAL;
/*  650:     */     }
/*  651:1572 */     return JobPriority.valueOf(prio);
/*  652:     */   }
/*  653:     */   
/*  654:     */   void setJobSubmitHostName(String hostname)
/*  655:     */   {
/*  656:1581 */     set("mapreduce.job.submithostname", hostname);
/*  657:     */   }
/*  658:     */   
/*  659:     */   String getJobSubmitHostName()
/*  660:     */   {
/*  661:1590 */     String hostname = get("mapreduce.job.submithostname");
/*  662:     */     
/*  663:1592 */     return hostname;
/*  664:     */   }
/*  665:     */   
/*  666:     */   void setJobSubmitHostAddress(String hostadd)
/*  667:     */   {
/*  668:1601 */     set("mapreduce.job.submithostaddress", hostadd);
/*  669:     */   }
/*  670:     */   
/*  671:     */   String getJobSubmitHostAddress()
/*  672:     */   {
/*  673:1610 */     String hostadd = get("mapreduce.job.submithostaddress");
/*  674:     */     
/*  675:1612 */     return hostadd;
/*  676:     */   }
/*  677:     */   
/*  678:     */   public boolean getProfileEnabled()
/*  679:     */   {
/*  680:1620 */     return getBoolean("mapreduce.task.profile", false);
/*  681:     */   }
/*  682:     */   
/*  683:     */   public void setProfileEnabled(boolean newValue)
/*  684:     */   {
/*  685:1630 */     setBoolean("mapreduce.task.profile", newValue);
/*  686:     */   }
/*  687:     */   
/*  688:     */   public String getProfileParams()
/*  689:     */   {
/*  690:1642 */     return get("mapreduce.task.profile.params", "-agentlib:hprof=cpu=samples,heap=sites,force=n,thread=y,verbose=n,file=%s");
/*  691:     */   }
/*  692:     */   
/*  693:     */   public void setProfileParams(String value)
/*  694:     */   {
/*  695:1657 */     set("mapreduce.task.profile.params", value);
/*  696:     */   }
/*  697:     */   
/*  698:     */   public Configuration.IntegerRanges getProfileTaskRange(boolean isMap)
/*  699:     */   {
/*  700:1666 */     return getRange(isMap ? "mapreduce.task.profile.maps" : "mapreduce.task.profile.reduces", "0-2");
/*  701:     */   }
/*  702:     */   
/*  703:     */   public void setProfileTaskRange(boolean isMap, String newValue)
/*  704:     */   {
/*  705:1677 */     new Configuration.IntegerRanges(newValue);
/*  706:1678 */     set(isMap ? "mapreduce.task.profile.maps" : "mapreduce.task.profile.reduces", newValue);
/*  707:     */   }
/*  708:     */   
/*  709:     */   public void setMapDebugScript(String mDbgScript)
/*  710:     */   {
/*  711:1706 */     set("mapreduce.map.debug.script", mDbgScript);
/*  712:     */   }
/*  713:     */   
/*  714:     */   public String getMapDebugScript()
/*  715:     */   {
/*  716:1716 */     return get("mapreduce.map.debug.script");
/*  717:     */   }
/*  718:     */   
/*  719:     */   public void setReduceDebugScript(String rDbgScript)
/*  720:     */   {
/*  721:1743 */     set("mapreduce.reduce.debug.script", rDbgScript);
/*  722:     */   }
/*  723:     */   
/*  724:     */   public String getReduceDebugScript()
/*  725:     */   {
/*  726:1753 */     return get("mapreduce.reduce.debug.script");
/*  727:     */   }
/*  728:     */   
/*  729:     */   public String getJobEndNotificationURI()
/*  730:     */   {
/*  731:1765 */     return get("mapreduce.job.end-notification.url");
/*  732:     */   }
/*  733:     */   
/*  734:     */   public void setJobEndNotificationURI(String uri)
/*  735:     */   {
/*  736:1785 */     set("mapreduce.job.end-notification.url", uri);
/*  737:     */   }
/*  738:     */   
/*  739:     */   public String getJobLocalDir()
/*  740:     */   {
/*  741:1804 */     return get("mapreduce.job.local.dir");
/*  742:     */   }
/*  743:     */   
/*  744:     */   public long getMemoryForMapTask()
/*  745:     */   {
/*  746:1821 */     long value = getDeprecatedMemoryValue();
/*  747:1822 */     if (value == -1L) {
/*  748:1823 */       value = normalizeMemoryConfigValue(getLong("mapreduce.map.memory.mb", -1L));
/*  749:     */     }
/*  750:1828 */     if (value == -1L) {
/*  751:1829 */       value = normalizeMemoryConfigValue(getLong("mapred.job.map.memory.mb", -1L));
/*  752:     */     }
/*  753:1833 */     return value;
/*  754:     */   }
/*  755:     */   
/*  756:     */   public void setMemoryForMapTask(long mem)
/*  757:     */   {
/*  758:1837 */     setLong("mapreduce.map.memory.mb", mem);
/*  759:     */     
/*  760:1839 */     setLong("mapred.job.map.memory.mb", mem);
/*  761:     */   }
/*  762:     */   
/*  763:     */   public long getMemoryForReduceTask()
/*  764:     */   {
/*  765:1856 */     long value = getDeprecatedMemoryValue();
/*  766:1857 */     if (value == -1L) {
/*  767:1858 */       value = normalizeMemoryConfigValue(getLong("mapreduce.reduce.memory.mb", -1L));
/*  768:     */     }
/*  769:1863 */     if (value == -1L) {
/*  770:1864 */       value = normalizeMemoryConfigValue(getLong("mapred.job.reduce.memory.mb", -1L));
/*  771:     */     }
/*  772:1868 */     return value;
/*  773:     */   }
/*  774:     */   
/*  775:     */   private long getDeprecatedMemoryValue()
/*  776:     */   {
/*  777:1876 */     long oldValue = getLong("mapred.task.maxvmem", -1L);
/*  778:     */     
/*  779:1878 */     oldValue = normalizeMemoryConfigValue(oldValue);
/*  780:1879 */     if (oldValue != -1L) {
/*  781:1880 */       oldValue /= 1048576L;
/*  782:     */     }
/*  783:1882 */     return oldValue;
/*  784:     */   }
/*  785:     */   
/*  786:     */   public void setMemoryForReduceTask(long mem)
/*  787:     */   {
/*  788:1886 */     setLong("mapreduce.reduce.memory.mb", mem);
/*  789:     */     
/*  790:1888 */     setLong("mapred.job.reduce.memory.mb", mem);
/*  791:     */   }
/*  792:     */   
/*  793:     */   public String getQueueName()
/*  794:     */   {
/*  795:1898 */     return get("mapreduce.job.queuename", "default");
/*  796:     */   }
/*  797:     */   
/*  798:     */   public void setQueueName(String queueName)
/*  799:     */   {
/*  800:1907 */     set("mapreduce.job.queuename", queueName);
/*  801:     */   }
/*  802:     */   
/*  803:     */   public static long normalizeMemoryConfigValue(long val)
/*  804:     */   {
/*  805:1917 */     if (val < 0L) {
/*  806:1918 */       val = -1L;
/*  807:     */     }
/*  808:1920 */     return val;
/*  809:     */   }
/*  810:     */   
/*  811:     */   int computeNumSlotsPerMap(long slotSizePerMap)
/*  812:     */   {
/*  813:1932 */     if ((slotSizePerMap == -1L) || (getMemoryForMapTask() == -1L)) {
/*  814:1934 */       return 1;
/*  815:     */     }
/*  816:1936 */     return (int)Math.ceil((float)getMemoryForMapTask() / (float)slotSizePerMap);
/*  817:     */   }
/*  818:     */   
/*  819:     */   int computeNumSlotsPerReduce(long slotSizePerReduce)
/*  820:     */   {
/*  821:1948 */     if ((slotSizePerReduce == -1L) || (getMemoryForReduceTask() == -1L)) {
/*  822:1950 */       return 1;
/*  823:     */     }
/*  824:1952 */     return (int)Math.ceil((float)getMemoryForReduceTask() / (float)slotSizePerReduce);
/*  825:     */   }
/*  826:     */   
/*  827:     */   public static String findContainingJar(Class my_class)
/*  828:     */   {
/*  829:1966 */     return ClassUtil.findContainingJar(my_class);
/*  830:     */   }
/*  831:     */   
/*  832:     */   @Deprecated
/*  833:     */   public long getMaxVirtualMemoryForTask()
/*  834:     */   {
/*  835:1991 */     LOG.warn("getMaxVirtualMemoryForTask() is deprecated. Instead use getMemoryForMapTask() and getMemoryForReduceTask()");
/*  836:     */     
/*  837:     */ 
/*  838:     */ 
/*  839:1995 */     long value = getLong("mapred.task.maxvmem", -1L);
/*  840:1996 */     value = normalizeMemoryConfigValue(value);
/*  841:1997 */     if (value == -1L)
/*  842:     */     {
/*  843:1998 */       value = Math.max(getMemoryForMapTask(), getMemoryForReduceTask());
/*  844:1999 */       value = normalizeMemoryConfigValue(value);
/*  845:2000 */       if (value != -1L) {
/*  846:2001 */         value *= 1048576L;
/*  847:     */       }
/*  848:     */     }
/*  849:2004 */     return value;
/*  850:     */   }
/*  851:     */   
/*  852:     */   @Deprecated
/*  853:     */   public void setMaxVirtualMemoryForTask(long vmem)
/*  854:     */   {
/*  855:2027 */     LOG.warn("setMaxVirtualMemoryForTask() is deprecated.Instead use setMemoryForMapTask() and setMemoryForReduceTask()");
/*  856:2029 */     if ((vmem != -1L) && (vmem < 0L))
/*  857:     */     {
/*  858:2030 */       setMemoryForMapTask(-1L);
/*  859:2031 */       setMemoryForReduceTask(-1L);
/*  860:     */     }
/*  861:2034 */     if (get("mapred.task.maxvmem") == null)
/*  862:     */     {
/*  863:2035 */       setMemoryForMapTask(vmem / 1048576L);
/*  864:2036 */       setMemoryForReduceTask(vmem / 1048576L);
/*  865:     */     }
/*  866:     */     else
/*  867:     */     {
/*  868:2038 */       setLong("mapred.task.maxvmem", vmem);
/*  869:     */     }
/*  870:     */   }
/*  871:     */   
/*  872:     */   @Deprecated
/*  873:     */   public long getMaxPhysicalMemoryForTask()
/*  874:     */   {
/*  875:2047 */     LOG.warn("The API getMaxPhysicalMemoryForTask() is deprecated. Refer to the APIs getMemoryForMapTask() and getMemoryForReduceTask() for details.");
/*  876:     */     
/*  877:     */ 
/*  878:2050 */     return -1L;
/*  879:     */   }
/*  880:     */   
/*  881:     */   @Deprecated
/*  882:     */   public void setMaxPhysicalMemoryForTask(long mem)
/*  883:     */   {
/*  884:2058 */     LOG.warn("The API setMaxPhysicalMemoryForTask() is deprecated. The value set is ignored. Refer to  setMemoryForMapTask() and setMemoryForReduceTask() for details.");
/*  885:     */   }
/*  886:     */   
/*  887:     */   static String deprecatedString(String key)
/*  888:     */   {
/*  889:2064 */     return "The variable " + key + " is no longer used.";
/*  890:     */   }
/*  891:     */   
/*  892:     */   private void checkAndWarnDeprecation()
/*  893:     */   {
/*  894:2068 */     if (get("mapred.task.maxvmem") != null) {
/*  895:2069 */       LOG.warn(deprecatedString("mapred.task.maxvmem") + " Instead use " + "mapreduce.map.memory.mb" + " and " + "mapreduce.reduce.memory.mb");
/*  896:     */     }
/*  897:2073 */     if (get("mapred.child.ulimit") != null) {
/*  898:2074 */       LOG.warn(deprecatedString("mapred.child.ulimit"));
/*  899:     */     }
/*  900:2076 */     if (get("mapreduce.map.ulimit") != null) {
/*  901:2077 */       LOG.warn(deprecatedString("mapreduce.map.ulimit"));
/*  902:     */     }
/*  903:2079 */     if (get("mapreduce.reduce.ulimit") != null) {
/*  904:2080 */       LOG.warn(deprecatedString("mapreduce.reduce.ulimit"));
/*  905:     */     }
/*  906:     */   }
/*  907:     */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.JobConf
 * JD-Core Version:    0.7.0.1
 */