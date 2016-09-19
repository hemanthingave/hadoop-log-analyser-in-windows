/*   1:    */ package org.apache.hadoop.mapreduce.lib.aggregate;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.PrintStream;
/*   5:    */ import java.util.ArrayList;
/*   6:    */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*   7:    */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*   8:    */ import org.apache.hadoop.conf.Configuration;
/*   9:    */ import org.apache.hadoop.fs.Path;
/*  10:    */ import org.apache.hadoop.io.Text;
/*  11:    */ import org.apache.hadoop.mapreduce.InputFormat;
/*  12:    */ import org.apache.hadoop.mapreduce.Job;
/*  13:    */ import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
/*  14:    */ import org.apache.hadoop.mapreduce.lib.input.SequenceFileInputFormat;
/*  15:    */ import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
/*  16:    */ import org.apache.hadoop.mapreduce.lib.jobcontrol.ControlledJob;
/*  17:    */ import org.apache.hadoop.mapreduce.lib.jobcontrol.JobControl;
/*  18:    */ import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
/*  19:    */ import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
/*  20:    */ import org.apache.hadoop.util.GenericOptionsParser;
/*  21:    */ 
/*  22:    */ @InterfaceAudience.Public
/*  23:    */ @InterfaceStability.Stable
/*  24:    */ public class ValueAggregatorJob
/*  25:    */ {
/*  26:    */   public static JobControl createValueAggregatorJobs(String[] args, Class<? extends ValueAggregatorDescriptor>[] descriptors)
/*  27:    */     throws IOException
/*  28:    */   {
/*  29: 91 */     JobControl theControl = new JobControl("ValueAggregatorJobs");
/*  30: 92 */     ArrayList<ControlledJob> dependingJobs = new ArrayList();
/*  31: 93 */     Configuration conf = new Configuration();
/*  32: 94 */     if (descriptors != null) {
/*  33: 95 */       conf = setAggregatorDescriptors(descriptors);
/*  34:    */     }
/*  35: 97 */     Job job = createValueAggregatorJob(conf, args);
/*  36: 98 */     ControlledJob cjob = new ControlledJob(job, dependingJobs);
/*  37: 99 */     theControl.addJob(cjob);
/*  38:100 */     return theControl;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public static JobControl createValueAggregatorJobs(String[] args)
/*  42:    */     throws IOException
/*  43:    */   {
/*  44:105 */     return createValueAggregatorJobs(args, null);
/*  45:    */   }
/*  46:    */   
/*  47:    */   public static Job createValueAggregatorJob(Configuration conf, String[] args)
/*  48:    */     throws IOException
/*  49:    */   {
/*  50:122 */     GenericOptionsParser genericParser = new GenericOptionsParser(conf, args);
/*  51:    */     
/*  52:124 */     args = genericParser.getRemainingArgs();
/*  53:126 */     if (args.length < 2)
/*  54:    */     {
/*  55:127 */       System.out.println("usage: inputDirs outDir [numOfReducer [textinputformat|seq [specfile [jobName]]]]");
/*  56:    */       
/*  57:129 */       GenericOptionsParser.printGenericCommandUsage(System.out);
/*  58:130 */       System.exit(2);
/*  59:    */     }
/*  60:132 */     String inputDir = args[0];
/*  61:133 */     String outputDir = args[1];
/*  62:134 */     int numOfReducers = 1;
/*  63:135 */     if (args.length > 2) {
/*  64:136 */       numOfReducers = Integer.parseInt(args[2]);
/*  65:    */     }
/*  66:139 */     Class<? extends InputFormat> theInputFormat = null;
/*  67:140 */     if ((args.length > 3) && (args[3].compareToIgnoreCase("textinputformat") == 0)) {
/*  68:142 */       theInputFormat = TextInputFormat.class;
/*  69:    */     } else {
/*  70:144 */       theInputFormat = SequenceFileInputFormat.class;
/*  71:    */     }
/*  72:147 */     Path specFile = null;
/*  73:149 */     if (args.length > 4) {
/*  74:150 */       specFile = new Path(args[4]);
/*  75:    */     }
/*  76:153 */     String jobName = "";
/*  77:155 */     if (args.length > 5) {
/*  78:156 */       jobName = args[5];
/*  79:    */     }
/*  80:159 */     if (specFile != null) {
/*  81:160 */       conf.addResource(specFile);
/*  82:    */     }
/*  83:162 */     String userJarFile = conf.get("mapreduce.aggregate.user.jar.file");
/*  84:163 */     if (userJarFile != null) {
/*  85:164 */       conf.set("mapreduce.job.jar", userJarFile);
/*  86:    */     }
/*  87:167 */     Job theJob = new Job(conf);
/*  88:168 */     if (userJarFile == null) {
/*  89:169 */       theJob.setJarByClass(ValueAggregator.class);
/*  90:    */     }
/*  91:171 */     theJob.setJobName("ValueAggregatorJob: " + jobName);
/*  92:    */     
/*  93:173 */     FileInputFormat.addInputPaths(theJob, inputDir);
/*  94:    */     
/*  95:175 */     theJob.setInputFormatClass(theInputFormat);
/*  96:    */     
/*  97:177 */     theJob.setMapperClass(ValueAggregatorMapper.class);
/*  98:178 */     FileOutputFormat.setOutputPath(theJob, new Path(outputDir));
/*  99:179 */     theJob.setOutputFormatClass(TextOutputFormat.class);
/* 100:180 */     theJob.setMapOutputKeyClass(Text.class);
/* 101:181 */     theJob.setMapOutputValueClass(Text.class);
/* 102:182 */     theJob.setOutputKeyClass(Text.class);
/* 103:183 */     theJob.setOutputValueClass(Text.class);
/* 104:184 */     theJob.setReducerClass(ValueAggregatorReducer.class);
/* 105:185 */     theJob.setCombinerClass(ValueAggregatorCombiner.class);
/* 106:186 */     theJob.setNumReduceTasks(numOfReducers);
/* 107:187 */     return theJob;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public static Job createValueAggregatorJob(String[] args, Class<? extends ValueAggregatorDescriptor>[] descriptors)
/* 111:    */     throws IOException
/* 112:    */   {
/* 113:193 */     return createValueAggregatorJob(setAggregatorDescriptors(descriptors), args);
/* 114:    */   }
/* 115:    */   
/* 116:    */   public static Configuration setAggregatorDescriptors(Class<? extends ValueAggregatorDescriptor>[] descriptors)
/* 117:    */   {
/* 118:199 */     Configuration conf = new Configuration();
/* 119:200 */     conf.setInt("mapreduce.aggregate.descriptor.num", descriptors.length);
/* 120:202 */     for (int i = 0; i < descriptors.length; i++) {
/* 121:203 */       conf.set("mapreduce.aggregate.descriptor" + i, "UserDefined," + descriptors[i].getName());
/* 122:    */     }
/* 123:206 */     return conf;
/* 124:    */   }
/* 125:    */   
/* 126:    */   public static void main(String[] args)
/* 127:    */     throws IOException, InterruptedException, ClassNotFoundException
/* 128:    */   {
/* 129:217 */     Job job = createValueAggregatorJob(new Configuration(), args);
/* 130:    */     
/* 131:219 */     int ret = job.waitForCompletion(true) ? 0 : 1;
/* 132:220 */     System.exit(ret);
/* 133:    */   }
/* 134:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.lib.aggregate.ValueAggregatorJob
 * JD-Core Version:    0.7.0.1
 */