/*   1:    */ package org.apache.hadoop.mapred.lib.aggregate;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.PrintStream;
/*   5:    */ import java.util.ArrayList;
/*   6:    */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*   7:    */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*   8:    */ import org.apache.hadoop.conf.Configuration;
/*   9:    */ import org.apache.hadoop.fs.Path;
/*  10:    */ import org.apache.hadoop.io.Text;
/*  11:    */ import org.apache.hadoop.mapred.FileInputFormat;
/*  12:    */ import org.apache.hadoop.mapred.FileOutputFormat;
/*  13:    */ import org.apache.hadoop.mapred.InputFormat;
/*  14:    */ import org.apache.hadoop.mapred.JobClient;
/*  15:    */ import org.apache.hadoop.mapred.JobConf;
/*  16:    */ import org.apache.hadoop.mapred.SequenceFileInputFormat;
/*  17:    */ import org.apache.hadoop.mapred.TextInputFormat;
/*  18:    */ import org.apache.hadoop.mapred.TextOutputFormat;
/*  19:    */ import org.apache.hadoop.mapred.jobcontrol.Job;
/*  20:    */ import org.apache.hadoop.mapred.jobcontrol.JobControl;
/*  21:    */ import org.apache.hadoop.util.GenericOptionsParser;
/*  22:    */ 
/*  23:    */ @InterfaceAudience.Public
/*  24:    */ @InterfaceStability.Stable
/*  25:    */ public class ValueAggregatorJob
/*  26:    */ {
/*  27:    */   public static JobControl createValueAggregatorJobs(String[] args, Class<? extends ValueAggregatorDescriptor>[] descriptors)
/*  28:    */     throws IOException
/*  29:    */   {
/*  30: 89 */     JobControl theControl = new JobControl("ValueAggregatorJobs");
/*  31: 90 */     ArrayList<Job> dependingJobs = new ArrayList();
/*  32: 91 */     JobConf aJobConf = createValueAggregatorJob(args);
/*  33: 92 */     if (descriptors != null) {
/*  34: 93 */       setAggregatorDescriptors(aJobConf, descriptors);
/*  35:    */     }
/*  36: 94 */     Job aJob = new Job(aJobConf, dependingJobs);
/*  37: 95 */     theControl.addJob(aJob);
/*  38: 96 */     return theControl;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public static JobControl createValueAggregatorJobs(String[] args)
/*  42:    */     throws IOException
/*  43:    */   {
/*  44:100 */     return createValueAggregatorJobs(args, null);
/*  45:    */   }
/*  46:    */   
/*  47:    */   public static JobConf createValueAggregatorJob(String[] args, Class<?> caller)
/*  48:    */     throws IOException
/*  49:    */   {
/*  50:118 */     Configuration conf = new Configuration();
/*  51:    */     
/*  52:120 */     GenericOptionsParser genericParser = new GenericOptionsParser(conf, args);
/*  53:    */     
/*  54:122 */     args = genericParser.getRemainingArgs();
/*  55:124 */     if (args.length < 2)
/*  56:    */     {
/*  57:125 */       System.out.println("usage: inputDirs outDir [numOfReducer [textinputformat|seq [specfile [jobName]]]]");
/*  58:    */       
/*  59:127 */       GenericOptionsParser.printGenericCommandUsage(System.out);
/*  60:128 */       System.exit(1);
/*  61:    */     }
/*  62:130 */     String inputDir = args[0];
/*  63:131 */     String outputDir = args[1];
/*  64:132 */     int numOfReducers = 1;
/*  65:133 */     if (args.length > 2) {
/*  66:134 */       numOfReducers = Integer.parseInt(args[2]);
/*  67:    */     }
/*  68:137 */     Class<? extends InputFormat> theInputFormat = TextInputFormat.class;
/*  69:139 */     if ((args.length > 3) && (args[3].compareToIgnoreCase("textinputformat") == 0)) {
/*  70:141 */       theInputFormat = TextInputFormat.class;
/*  71:    */     } else {
/*  72:143 */       theInputFormat = SequenceFileInputFormat.class;
/*  73:    */     }
/*  74:146 */     Path specFile = null;
/*  75:148 */     if (args.length > 4) {
/*  76:149 */       specFile = new Path(args[4]);
/*  77:    */     }
/*  78:152 */     String jobName = "";
/*  79:154 */     if (args.length > 5) {
/*  80:155 */       jobName = args[5];
/*  81:    */     }
/*  82:158 */     JobConf theJob = new JobConf(conf);
/*  83:159 */     if (specFile != null) {
/*  84:160 */       theJob.addResource(specFile);
/*  85:    */     }
/*  86:162 */     String userJarFile = theJob.get("user.jar.file");
/*  87:163 */     if (userJarFile == null) {
/*  88:164 */       theJob.setJarByClass(caller != null ? caller : ValueAggregatorJob.class);
/*  89:    */     } else {
/*  90:166 */       theJob.setJar(userJarFile);
/*  91:    */     }
/*  92:168 */     theJob.setJobName("ValueAggregatorJob: " + jobName);
/*  93:    */     
/*  94:170 */     FileInputFormat.addInputPaths(theJob, inputDir);
/*  95:    */     
/*  96:172 */     theJob.setInputFormat(theInputFormat);
/*  97:    */     
/*  98:174 */     theJob.setMapperClass(ValueAggregatorMapper.class);
/*  99:175 */     FileOutputFormat.setOutputPath(theJob, new Path(outputDir));
/* 100:176 */     theJob.setOutputFormat(TextOutputFormat.class);
/* 101:177 */     theJob.setMapOutputKeyClass(Text.class);
/* 102:178 */     theJob.setMapOutputValueClass(Text.class);
/* 103:179 */     theJob.setOutputKeyClass(Text.class);
/* 104:180 */     theJob.setOutputValueClass(Text.class);
/* 105:181 */     theJob.setReducerClass(ValueAggregatorReducer.class);
/* 106:182 */     theJob.setCombinerClass(ValueAggregatorCombiner.class);
/* 107:183 */     theJob.setNumMapTasks(1);
/* 108:184 */     theJob.setNumReduceTasks(numOfReducers);
/* 109:185 */     return theJob;
/* 110:    */   }
/* 111:    */   
/* 112:    */   public static JobConf createValueAggregatorJob(String[] args)
/* 113:    */     throws IOException
/* 114:    */   {
/* 115:200 */     return createValueAggregatorJob(args, ValueAggregator.class);
/* 116:    */   }
/* 117:    */   
/* 118:    */   public static JobConf createValueAggregatorJob(String[] args, Class<? extends ValueAggregatorDescriptor>[] descriptors)
/* 119:    */     throws IOException
/* 120:    */   {
/* 121:206 */     JobConf job = createValueAggregatorJob(args);
/* 122:207 */     setAggregatorDescriptors(job, descriptors);
/* 123:208 */     return job;
/* 124:    */   }
/* 125:    */   
/* 126:    */   public static void setAggregatorDescriptors(JobConf job, Class<? extends ValueAggregatorDescriptor>[] descriptors)
/* 127:    */   {
/* 128:213 */     job.setInt("aggregator.descriptor.num", descriptors.length);
/* 129:215 */     for (int i = 0; i < descriptors.length; i++) {
/* 130:216 */       job.set("aggregator.descriptor." + i, "UserDefined," + descriptors[i].getName());
/* 131:    */     }
/* 132:    */   }
/* 133:    */   
/* 134:    */   public static JobConf createValueAggregatorJob(String[] args, Class<? extends ValueAggregatorDescriptor>[] descriptors, Class<?> caller)
/* 135:    */     throws IOException
/* 136:    */   {
/* 137:223 */     JobConf job = createValueAggregatorJob(args, caller);
/* 138:224 */     setAggregatorDescriptors(job, descriptors);
/* 139:225 */     return job;
/* 140:    */   }
/* 141:    */   
/* 142:    */   public static void main(String[] args)
/* 143:    */     throws IOException
/* 144:    */   {
/* 145:235 */     JobConf job = createValueAggregatorJob(args);
/* 146:236 */     JobClient.runJob(job);
/* 147:    */   }
/* 148:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.lib.aggregate.ValueAggregatorJob
 * JD-Core Version:    0.7.0.1
 */