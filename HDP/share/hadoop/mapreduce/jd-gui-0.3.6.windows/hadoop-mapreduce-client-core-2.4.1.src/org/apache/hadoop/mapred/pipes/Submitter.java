/*   1:    */ package org.apache.hadoop.mapred.pipes;
/*   2:    */ 
/*   3:    */ import java.io.File;
/*   4:    */ import java.io.IOException;
/*   5:    */ import java.io.PrintStream;
/*   6:    */ import java.net.URI;
/*   7:    */ import java.net.URISyntaxException;
/*   8:    */ import java.net.URL;
/*   9:    */ import java.net.URLClassLoader;
/*  10:    */ import java.security.AccessController;
/*  11:    */ import java.security.PrivilegedAction;
/*  12:    */ import java.util.StringTokenizer;
/*  13:    */ import org.apache.commons.cli.BasicParser;
/*  14:    */ import org.apache.commons.cli.CommandLine;
/*  15:    */ import org.apache.commons.cli.Option;
/*  16:    */ import org.apache.commons.cli.OptionBuilder;
/*  17:    */ import org.apache.commons.cli.Options;
/*  18:    */ import org.apache.commons.cli.ParseException;
/*  19:    */ import org.apache.commons.cli.Parser;
/*  20:    */ import org.apache.commons.logging.Log;
/*  21:    */ import org.apache.commons.logging.LogFactory;
/*  22:    */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*  23:    */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*  24:    */ import org.apache.hadoop.conf.Configuration;
/*  25:    */ import org.apache.hadoop.conf.Configured;
/*  26:    */ import org.apache.hadoop.fs.FileSystem;
/*  27:    */ import org.apache.hadoop.fs.LocalFileSystem;
/*  28:    */ import org.apache.hadoop.fs.Path;
/*  29:    */ import org.apache.hadoop.io.Text;
/*  30:    */ import org.apache.hadoop.mapred.FileInputFormat;
/*  31:    */ import org.apache.hadoop.mapred.FileOutputFormat;
/*  32:    */ import org.apache.hadoop.mapred.InputFormat;
/*  33:    */ import org.apache.hadoop.mapred.JobClient;
/*  34:    */ import org.apache.hadoop.mapred.JobConf;
/*  35:    */ import org.apache.hadoop.mapred.Mapper;
/*  36:    */ import org.apache.hadoop.mapred.OutputFormat;
/*  37:    */ import org.apache.hadoop.mapred.Partitioner;
/*  38:    */ import org.apache.hadoop.mapred.Reducer;
/*  39:    */ import org.apache.hadoop.mapred.RunningJob;
/*  40:    */ import org.apache.hadoop.mapred.lib.HashPartitioner;
/*  41:    */ import org.apache.hadoop.mapred.lib.LazyOutputFormat;
/*  42:    */ import org.apache.hadoop.mapred.lib.NullOutputFormat;
/*  43:    */ import org.apache.hadoop.mapreduce.filecache.DistributedCache;
/*  44:    */ import org.apache.hadoop.util.ExitUtil;
/*  45:    */ import org.apache.hadoop.util.GenericOptionsParser;
/*  46:    */ import org.apache.hadoop.util.Tool;
/*  47:    */ 
/*  48:    */ @InterfaceAudience.Public
/*  49:    */ @InterfaceStability.Stable
/*  50:    */ public class Submitter
/*  51:    */   extends Configured
/*  52:    */   implements Tool
/*  53:    */ {
/*  54: 73 */   protected static final Log LOG = LogFactory.getLog(Submitter.class);
/*  55:    */   public static final String PRESERVE_COMMANDFILE = "mapreduce.pipes.commandfile.preserve";
/*  56:    */   public static final String EXECUTABLE = "mapreduce.pipes.executable";
/*  57:    */   public static final String INTERPRETOR = "mapreduce.pipes.executable.interpretor";
/*  58:    */   public static final String IS_JAVA_MAP = "mapreduce.pipes.isjavamapper";
/*  59:    */   public static final String IS_JAVA_RR = "mapreduce.pipes.isjavarecordreader";
/*  60:    */   public static final String IS_JAVA_RW = "mapreduce.pipes.isjavarecordwriter";
/*  61:    */   public static final String IS_JAVA_REDUCE = "mapreduce.pipes.isjavareducer";
/*  62:    */   public static final String PARTITIONER = "mapreduce.pipes.partitioner";
/*  63:    */   public static final String INPUT_FORMAT = "mapreduce.pipes.inputformat";
/*  64:    */   public static final String PORT = "mapreduce.pipes.command.port";
/*  65:    */   
/*  66:    */   public Submitter()
/*  67:    */   {
/*  68: 88 */     this(new Configuration());
/*  69:    */   }
/*  70:    */   
/*  71:    */   public Submitter(Configuration conf)
/*  72:    */   {
/*  73: 92 */     setConf(conf);
/*  74:    */   }
/*  75:    */   
/*  76:    */   public static String getExecutable(JobConf conf)
/*  77:    */   {
/*  78:101 */     return conf.get("mapreduce.pipes.executable");
/*  79:    */   }
/*  80:    */   
/*  81:    */   public static void setExecutable(JobConf conf, String executable)
/*  82:    */   {
/*  83:111 */     conf.set("mapreduce.pipes.executable", executable);
/*  84:    */   }
/*  85:    */   
/*  86:    */   public static void setIsJavaRecordReader(JobConf conf, boolean value)
/*  87:    */   {
/*  88:120 */     conf.setBoolean("mapreduce.pipes.isjavarecordreader", value);
/*  89:    */   }
/*  90:    */   
/*  91:    */   public static boolean getIsJavaRecordReader(JobConf conf)
/*  92:    */   {
/*  93:129 */     return conf.getBoolean("mapreduce.pipes.isjavarecordreader", false);
/*  94:    */   }
/*  95:    */   
/*  96:    */   public static void setIsJavaMapper(JobConf conf, boolean value)
/*  97:    */   {
/*  98:138 */     conf.setBoolean("mapreduce.pipes.isjavamapper", value);
/*  99:    */   }
/* 100:    */   
/* 101:    */   public static boolean getIsJavaMapper(JobConf conf)
/* 102:    */   {
/* 103:147 */     return conf.getBoolean("mapreduce.pipes.isjavamapper", false);
/* 104:    */   }
/* 105:    */   
/* 106:    */   public static void setIsJavaReducer(JobConf conf, boolean value)
/* 107:    */   {
/* 108:156 */     conf.setBoolean("mapreduce.pipes.isjavareducer", value);
/* 109:    */   }
/* 110:    */   
/* 111:    */   public static boolean getIsJavaReducer(JobConf conf)
/* 112:    */   {
/* 113:165 */     return conf.getBoolean("mapreduce.pipes.isjavareducer", false);
/* 114:    */   }
/* 115:    */   
/* 116:    */   public static void setIsJavaRecordWriter(JobConf conf, boolean value)
/* 117:    */   {
/* 118:174 */     conf.setBoolean("mapreduce.pipes.isjavarecordwriter", value);
/* 119:    */   }
/* 120:    */   
/* 121:    */   public static boolean getIsJavaRecordWriter(JobConf conf)
/* 122:    */   {
/* 123:183 */     return conf.getBoolean("mapreduce.pipes.isjavarecordwriter", false);
/* 124:    */   }
/* 125:    */   
/* 126:    */   private static void setIfUnset(JobConf conf, String key, String value)
/* 127:    */   {
/* 128:194 */     if (conf.get(key) == null) {
/* 129:195 */       conf.set(key, value);
/* 130:    */     }
/* 131:    */   }
/* 132:    */   
/* 133:    */   static void setJavaPartitioner(JobConf conf, Class cls)
/* 134:    */   {
/* 135:205 */     conf.set("mapreduce.pipes.partitioner", cls.getName());
/* 136:    */   }
/* 137:    */   
/* 138:    */   static Class<? extends Partitioner> getJavaPartitioner(JobConf conf)
/* 139:    */   {
/* 140:214 */     return conf.getClass("mapreduce.pipes.partitioner", HashPartitioner.class, Partitioner.class);
/* 141:    */   }
/* 142:    */   
/* 143:    */   public static boolean getKeepCommandFile(JobConf conf)
/* 144:    */   {
/* 145:232 */     return conf.getBoolean("mapreduce.pipes.commandfile.preserve", false);
/* 146:    */   }
/* 147:    */   
/* 148:    */   public static void setKeepCommandFile(JobConf conf, boolean keep)
/* 149:    */   {
/* 150:241 */     conf.setBoolean("mapreduce.pipes.commandfile.preserve", keep);
/* 151:    */   }
/* 152:    */   
/* 153:    */   @Deprecated
/* 154:    */   public static RunningJob submitJob(JobConf conf)
/* 155:    */     throws IOException
/* 156:    */   {
/* 157:253 */     return runJob(conf);
/* 158:    */   }
/* 159:    */   
/* 160:    */   public static RunningJob runJob(JobConf conf)
/* 161:    */     throws IOException
/* 162:    */   {
/* 163:263 */     setupPipesJob(conf);
/* 164:264 */     return JobClient.runJob(conf);
/* 165:    */   }
/* 166:    */   
/* 167:    */   public static RunningJob jobSubmit(JobConf conf)
/* 168:    */     throws IOException
/* 169:    */   {
/* 170:278 */     setupPipesJob(conf);
/* 171:279 */     return new JobClient(conf).submitJob(conf);
/* 172:    */   }
/* 173:    */   
/* 174:    */   private static void setupPipesJob(JobConf conf)
/* 175:    */     throws IOException
/* 176:    */   {
/* 177:284 */     if (!getIsJavaMapper(conf))
/* 178:    */     {
/* 179:285 */       conf.setMapRunnerClass(PipesMapRunner.class);
/* 180:    */       
/* 181:287 */       setJavaPartitioner(conf, conf.getPartitionerClass());
/* 182:288 */       conf.setPartitionerClass(PipesPartitioner.class);
/* 183:    */     }
/* 184:290 */     if (!getIsJavaReducer(conf))
/* 185:    */     {
/* 186:291 */       conf.setReducerClass(PipesReducer.class);
/* 187:292 */       if (!getIsJavaRecordWriter(conf)) {
/* 188:293 */         conf.setOutputFormat(NullOutputFormat.class);
/* 189:    */       }
/* 190:    */     }
/* 191:296 */     String textClassname = Text.class.getName();
/* 192:297 */     setIfUnset(conf, "mapreduce.map.output.key.class", textClassname);
/* 193:298 */     setIfUnset(conf, "mapreduce.map.output.value.class", textClassname);
/* 194:299 */     setIfUnset(conf, "mapreduce.job.output.key.class", textClassname);
/* 195:300 */     setIfUnset(conf, "mapreduce.job.output.value.class", textClassname);
/* 196:304 */     if ((!getIsJavaRecordReader(conf)) && (!getIsJavaMapper(conf)))
/* 197:    */     {
/* 198:305 */       conf.setClass("mapreduce.pipes.inputformat", conf.getInputFormat().getClass(), InputFormat.class);
/* 199:    */       
/* 200:307 */       conf.setInputFormat(PipesNonJavaInputFormat.class);
/* 201:    */     }
/* 202:310 */     String exec = getExecutable(conf);
/* 203:311 */     if (exec == null) {
/* 204:312 */       throw new IllegalArgumentException("No application program defined.");
/* 205:    */     }
/* 206:316 */     if (exec.contains("#"))
/* 207:    */     {
/* 208:318 */       String defScript = "$HADOOP_PREFIX/src/c++/pipes/debug/pipes-default-script";
/* 209:319 */       setIfUnset(conf, "mapreduce.map.debug.script", defScript);
/* 210:320 */       setIfUnset(conf, "mapreduce.reduce.debug.script", defScript);
/* 211:    */     }
/* 212:322 */     URI[] fileCache = DistributedCache.getCacheFiles(conf);
/* 213:323 */     if (fileCache == null)
/* 214:    */     {
/* 215:324 */       fileCache = new URI[1];
/* 216:    */     }
/* 217:    */     else
/* 218:    */     {
/* 219:326 */       URI[] tmp = new URI[fileCache.length + 1];
/* 220:327 */       System.arraycopy(fileCache, 0, tmp, 1, fileCache.length);
/* 221:328 */       fileCache = tmp;
/* 222:    */     }
/* 223:    */     try
/* 224:    */     {
/* 225:331 */       fileCache[0] = new URI(exec);
/* 226:    */     }
/* 227:    */     catch (URISyntaxException e)
/* 228:    */     {
/* 229:333 */       IOException ie = new IOException("Problem parsing execable URI " + exec);
/* 230:334 */       ie.initCause(e);
/* 231:335 */       throw ie;
/* 232:    */     }
/* 233:337 */     DistributedCache.setCacheFiles(fileCache, conf);
/* 234:    */   }
/* 235:    */   
/* 236:    */   static class CommandLineParser
/* 237:    */   {
/* 238:344 */     private Options options = new Options();
/* 239:    */     
/* 240:    */     void addOption(String longName, boolean required, String description, String paramName)
/* 241:    */     {
/* 242:348 */       OptionBuilder.withArgName(paramName);OptionBuilder.hasArgs(1);OptionBuilder.withDescription(description);OptionBuilder.isRequired(required);Option option = OptionBuilder.create(longName);
/* 243:349 */       this.options.addOption(option);
/* 244:    */     }
/* 245:    */     
/* 246:    */     void addArgument(String name, boolean required, String description)
/* 247:    */     {
/* 248:353 */       OptionBuilder.withArgName(name);OptionBuilder.hasArgs(1);OptionBuilder.withDescription(description);OptionBuilder.isRequired(required);Option option = OptionBuilder.create();
/* 249:354 */       this.options.addOption(option);
/* 250:    */     }
/* 251:    */     
/* 252:    */     Parser createParser()
/* 253:    */     {
/* 254:359 */       Parser result = new BasicParser();
/* 255:360 */       return result;
/* 256:    */     }
/* 257:    */     
/* 258:    */     void printUsage()
/* 259:    */     {
/* 260:366 */       System.out.println("bin/hadoop pipes");
/* 261:367 */       System.out.println("  [-input <path>] // Input directory");
/* 262:368 */       System.out.println("  [-output <path>] // Output directory");
/* 263:369 */       System.out.println("  [-jar <jar file> // jar filename");
/* 264:370 */       System.out.println("  [-inputformat <class>] // InputFormat class");
/* 265:371 */       System.out.println("  [-map <class>] // Java Map class");
/* 266:372 */       System.out.println("  [-partitioner <class>] // Java Partitioner");
/* 267:373 */       System.out.println("  [-reduce <class>] // Java Reduce class");
/* 268:374 */       System.out.println("  [-writer <class>] // Java RecordWriter");
/* 269:375 */       System.out.println("  [-program <executable>] // executable URI");
/* 270:376 */       System.out.println("  [-reduces <num>] // number of reduces");
/* 271:377 */       System.out.println("  [-lazyOutput <true/false>] // createOutputLazily");
/* 272:378 */       System.out.println();
/* 273:379 */       GenericOptionsParser.printGenericCommandUsage(System.out);
/* 274:    */     }
/* 275:    */   }
/* 276:    */   
/* 277:    */   private static <InterfaceType> Class<? extends InterfaceType> getClass(CommandLine cl, String key, JobConf conf, Class<InterfaceType> cls)
/* 278:    */     throws ClassNotFoundException
/* 279:    */   {
/* 280:388 */     return conf.getClassByName(cl.getOptionValue(key)).asSubclass(cls);
/* 281:    */   }
/* 282:    */   
/* 283:    */   public int run(String[] args)
/* 284:    */     throws Exception
/* 285:    */   {
/* 286:393 */     CommandLineParser cli = new CommandLineParser();
/* 287:394 */     if (args.length == 0)
/* 288:    */     {
/* 289:395 */       cli.printUsage();
/* 290:396 */       return 1;
/* 291:    */     }
/* 292:398 */     cli.addOption("input", false, "input path to the maps", "path");
/* 293:399 */     cli.addOption("output", false, "output path from the reduces", "path");
/* 294:    */     
/* 295:401 */     cli.addOption("jar", false, "job jar file", "path");
/* 296:402 */     cli.addOption("inputformat", false, "java classname of InputFormat", "class");
/* 297:    */     
/* 298:    */ 
/* 299:405 */     cli.addOption("map", false, "java classname of Mapper", "class");
/* 300:406 */     cli.addOption("partitioner", false, "java classname of Partitioner", "class");
/* 301:    */     
/* 302:408 */     cli.addOption("reduce", false, "java classname of Reducer", "class");
/* 303:409 */     cli.addOption("writer", false, "java classname of OutputFormat", "class");
/* 304:410 */     cli.addOption("program", false, "URI to application executable", "class");
/* 305:411 */     cli.addOption("reduces", false, "number of reduces", "num");
/* 306:412 */     cli.addOption("jobconf", false, "\"n1=v1,n2=v2,..\" (Deprecated) Optional. Add or override a JobConf property.", "key=val");
/* 307:    */     
/* 308:    */ 
/* 309:415 */     cli.addOption("lazyOutput", false, "Optional. Create output lazily", "boolean");
/* 310:    */     
/* 311:417 */     Parser parser = cli.createParser();
/* 312:    */     try
/* 313:    */     {
/* 314:420 */       GenericOptionsParser genericParser = new GenericOptionsParser(getConf(), args);
/* 315:421 */       CommandLine results = parser.parse(cli.options, genericParser.getRemainingArgs());
/* 316:    */       
/* 317:423 */       JobConf job = new JobConf(getConf());
/* 318:425 */       if (results.hasOption("input")) {
/* 319:426 */         FileInputFormat.setInputPaths(job, results.getOptionValue("input"));
/* 320:    */       }
/* 321:428 */       if (results.hasOption("output")) {
/* 322:429 */         FileOutputFormat.setOutputPath(job, new Path(results.getOptionValue("output")));
/* 323:    */       }
/* 324:432 */       if (results.hasOption("jar")) {
/* 325:433 */         job.setJar(results.getOptionValue("jar"));
/* 326:    */       }
/* 327:435 */       if (results.hasOption("inputformat"))
/* 328:    */       {
/* 329:436 */         setIsJavaRecordReader(job, true);
/* 330:437 */         job.setInputFormat(getClass(results, "inputformat", job, InputFormat.class));
/* 331:    */       }
/* 332:440 */       if (results.hasOption("javareader")) {
/* 333:441 */         setIsJavaRecordReader(job, true);
/* 334:    */       }
/* 335:443 */       if (results.hasOption("map"))
/* 336:    */       {
/* 337:444 */         setIsJavaMapper(job, true);
/* 338:445 */         job.setMapperClass(getClass(results, "map", job, Mapper.class));
/* 339:    */       }
/* 340:447 */       if (results.hasOption("partitioner")) {
/* 341:448 */         job.setPartitionerClass(getClass(results, "partitioner", job, Partitioner.class));
/* 342:    */       }
/* 343:451 */       if (results.hasOption("reduce"))
/* 344:    */       {
/* 345:452 */         setIsJavaReducer(job, true);
/* 346:453 */         job.setReducerClass(getClass(results, "reduce", job, Reducer.class));
/* 347:    */       }
/* 348:455 */       if (results.hasOption("reduces")) {
/* 349:456 */         job.setNumReduceTasks(Integer.parseInt(results.getOptionValue("reduces")));
/* 350:    */       }
/* 351:459 */       if (results.hasOption("writer"))
/* 352:    */       {
/* 353:460 */         setIsJavaRecordWriter(job, true);
/* 354:461 */         job.setOutputFormat(getClass(results, "writer", job, OutputFormat.class));
/* 355:    */       }
/* 356:465 */       if ((results.hasOption("lazyOutput")) && 
/* 357:466 */         (Boolean.parseBoolean(results.getOptionValue("lazyOutput")))) {
/* 358:467 */         LazyOutputFormat.setOutputFormatClass(job, job.getOutputFormat().getClass());
/* 359:    */       }
/* 360:472 */       if (results.hasOption("program")) {
/* 361:473 */         setExecutable(job, results.getOptionValue("program"));
/* 362:    */       }
/* 363:475 */       if (results.hasOption("jobconf"))
/* 364:    */       {
/* 365:476 */         LOG.warn("-jobconf option is deprecated, please use -D instead.");
/* 366:477 */         String options = results.getOptionValue("jobconf");
/* 367:478 */         StringTokenizer tokenizer = new StringTokenizer(options, ",");
/* 368:479 */         while (tokenizer.hasMoreTokens())
/* 369:    */         {
/* 370:480 */           String keyVal = tokenizer.nextToken().trim();
/* 371:481 */           String[] keyValSplit = keyVal.split("=");
/* 372:482 */           job.set(keyValSplit[0], keyValSplit[1]);
/* 373:    */         }
/* 374:    */       }
/* 375:486 */       String jarFile = job.getJar();
/* 376:487 */       if (jarFile != null)
/* 377:    */       {
/* 378:488 */         final URL[] urls = { FileSystem.getLocal(job).pathToFile(new Path(jarFile)).toURL() };
/* 379:    */         
/* 380:    */ 
/* 381:    */ 
/* 382:492 */         ClassLoader loader = (ClassLoader)AccessController.doPrivileged(new PrivilegedAction()
/* 383:    */         {
/* 384:    */           public ClassLoader run()
/* 385:    */           {
/* 386:496 */             return new URLClassLoader(urls);
/* 387:    */           }
/* 388:499 */         });
/* 389:500 */         job.setClassLoader(loader);
/* 390:    */       }
/* 391:503 */       runJob(job);
/* 392:504 */       return 0;
/* 393:    */     }
/* 394:    */     catch (ParseException pe)
/* 395:    */     {
/* 396:506 */       LOG.info("Error : " + pe);
/* 397:507 */       cli.printUsage();
/* 398:    */     }
/* 399:508 */     return 1;
/* 400:    */   }
/* 401:    */   
/* 402:    */   public static void main(String[] args)
/* 403:    */     throws Exception
/* 404:    */   {
/* 405:518 */     int exitCode = new Submitter().run(args);
/* 406:519 */     ExitUtil.terminate(exitCode);
/* 407:    */   }
/* 408:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.pipes.Submitter
 * JD-Core Version:    0.7.0.1
 */