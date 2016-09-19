/*   1:    */ package org.apache.hadoop.mapreduce;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.lang.reflect.Constructor;
/*   5:    */ import java.lang.reflect.Field;
/*   6:    */ import java.lang.reflect.InvocationTargetException;
/*   7:    */ import org.apache.hadoop.conf.Configuration;
/*   8:    */ 
/*   9:    */ public class ContextFactory
/*  10:    */ {
/*  11:    */   private static final Constructor<?> JOB_CONTEXT_CONSTRUCTOR;
/*  12:    */   private static final Constructor<?> TASK_CONTEXT_CONSTRUCTOR;
/*  13:    */   private static final Constructor<?> MAP_CONTEXT_CONSTRUCTOR;
/*  14:    */   private static final Constructor<?> MAP_CONTEXT_IMPL_CONSTRUCTOR;
/*  15:    */   private static final boolean useV21;
/*  16:    */   private static final Field REPORTER_FIELD;
/*  17:    */   private static final Field READER_FIELD;
/*  18:    */   private static final Field WRITER_FIELD;
/*  19:    */   private static final Field OUTER_MAP_FIELD;
/*  20:    */   private static final Field WRAPPED_CONTEXT_FIELD;
/*  21:    */   
/*  22:    */   static
/*  23:    */   {
/*  24: 47 */     boolean v21 = true;
/*  25: 48 */     String PACKAGE = "org.apache.hadoop.mapreduce";
/*  26:    */     try
/*  27:    */     {
/*  28: 50 */       Class.forName("org.apache.hadoop.mapreduce.task.JobContextImpl");
/*  29:    */     }
/*  30:    */     catch (ClassNotFoundException cnfe)
/*  31:    */     {
/*  32: 52 */       v21 = false;
/*  33:    */     }
/*  34: 54 */     useV21 = v21;
/*  35:    */     Class<?> jobContextCls;
/*  36:    */     Class<?> taskContextCls;
/*  37:    */     Class<?> taskIOContextCls;
/*  38:    */     Class<?> mapContextCls;
/*  39:    */     Class<?> mapCls;
/*  40:    */     Class<?> innerMapContextCls;
/*  41:    */     try
/*  42:    */     {
/*  43:    */       Class<?> innerMapContextCls;
/*  44: 62 */       if (v21)
/*  45:    */       {
/*  46: 63 */         Class<?> jobContextCls = Class.forName("org.apache.hadoop.mapreduce.task.JobContextImpl");
/*  47:    */         
/*  48: 65 */         Class<?> taskContextCls = Class.forName("org.apache.hadoop.mapreduce.task.TaskAttemptContextImpl");
/*  49:    */         
/*  50: 67 */         Class<?> taskIOContextCls = Class.forName("org.apache.hadoop.mapreduce.task.TaskInputOutputContextImpl");
/*  51:    */         
/*  52: 69 */         Class<?> mapContextCls = Class.forName("org.apache.hadoop.mapreduce.task.MapContextImpl");
/*  53: 70 */         Class<?> mapCls = Class.forName("org.apache.hadoop.mapreduce.lib.map.WrappedMapper");
/*  54: 71 */         innerMapContextCls = Class.forName("org.apache.hadoop.mapreduce.lib.map.WrappedMapper$Context");
/*  55:    */       }
/*  56:    */       else
/*  57:    */       {
/*  58: 74 */         jobContextCls = Class.forName("org.apache.hadoop.mapreduce.JobContext");
/*  59:    */         
/*  60: 76 */         taskContextCls = Class.forName("org.apache.hadoop.mapreduce.TaskAttemptContext");
/*  61:    */         
/*  62: 78 */         taskIOContextCls = Class.forName("org.apache.hadoop.mapreduce.TaskInputOutputContext");
/*  63:    */         
/*  64: 80 */         mapContextCls = Class.forName("org.apache.hadoop.mapreduce.MapContext");
/*  65: 81 */         mapCls = Class.forName("org.apache.hadoop.mapreduce.Mapper");
/*  66: 82 */         innerMapContextCls = Class.forName("org.apache.hadoop.mapreduce.Mapper$Context");
/*  67:    */       }
/*  68:    */     }
/*  69:    */     catch (ClassNotFoundException e)
/*  70:    */     {
/*  71: 86 */       throw new IllegalArgumentException("Can't find class", e);
/*  72:    */     }
/*  73:    */     try
/*  74:    */     {
/*  75: 89 */       JOB_CONTEXT_CONSTRUCTOR = jobContextCls.getConstructor(new Class[] { Configuration.class, JobID.class });
/*  76:    */       
/*  77: 91 */       JOB_CONTEXT_CONSTRUCTOR.setAccessible(true);
/*  78: 92 */       TASK_CONTEXT_CONSTRUCTOR = taskContextCls.getConstructor(new Class[] { Configuration.class, TaskAttemptID.class });
/*  79:    */       
/*  80:    */ 
/*  81: 95 */       TASK_CONTEXT_CONSTRUCTOR.setAccessible(true);
/*  82: 96 */       if (useV21)
/*  83:    */       {
/*  84: 97 */         MAP_CONTEXT_CONSTRUCTOR = innerMapContextCls.getConstructor(new Class[] { mapCls, MapContext.class });
/*  85:    */         
/*  86:    */ 
/*  87:100 */         MAP_CONTEXT_IMPL_CONSTRUCTOR = mapContextCls.getDeclaredConstructor(new Class[] { Configuration.class, TaskAttemptID.class, RecordReader.class, RecordWriter.class, OutputCommitter.class, StatusReporter.class, InputSplit.class });
/*  88:    */         
/*  89:    */ 
/*  90:    */ 
/*  91:    */ 
/*  92:    */ 
/*  93:    */ 
/*  94:    */ 
/*  95:108 */         MAP_CONTEXT_IMPL_CONSTRUCTOR.setAccessible(true);
/*  96:109 */         WRAPPED_CONTEXT_FIELD = innerMapContextCls.getDeclaredField("mapContext");
/*  97:    */         
/*  98:111 */         WRAPPED_CONTEXT_FIELD.setAccessible(true);
/*  99:    */       }
/* 100:    */       else
/* 101:    */       {
/* 102:113 */         MAP_CONTEXT_CONSTRUCTOR = innerMapContextCls.getConstructor(new Class[] { mapCls, Configuration.class, TaskAttemptID.class, RecordReader.class, RecordWriter.class, OutputCommitter.class, StatusReporter.class, InputSplit.class });
/* 103:    */         
/* 104:    */ 
/* 105:    */ 
/* 106:    */ 
/* 107:    */ 
/* 108:    */ 
/* 109:    */ 
/* 110:    */ 
/* 111:122 */         MAP_CONTEXT_IMPL_CONSTRUCTOR = null;
/* 112:123 */         WRAPPED_CONTEXT_FIELD = null;
/* 113:    */       }
/* 114:125 */       MAP_CONTEXT_CONSTRUCTOR.setAccessible(true);
/* 115:126 */       REPORTER_FIELD = taskContextCls.getDeclaredField("reporter");
/* 116:127 */       REPORTER_FIELD.setAccessible(true);
/* 117:128 */       READER_FIELD = mapContextCls.getDeclaredField("reader");
/* 118:129 */       READER_FIELD.setAccessible(true);
/* 119:130 */       WRITER_FIELD = taskIOContextCls.getDeclaredField("output");
/* 120:131 */       WRITER_FIELD.setAccessible(true);
/* 121:132 */       OUTER_MAP_FIELD = innerMapContextCls.getDeclaredField("this$0");
/* 122:133 */       OUTER_MAP_FIELD.setAccessible(true);
/* 123:    */     }
/* 124:    */     catch (SecurityException e)
/* 125:    */     {
/* 126:135 */       throw new IllegalArgumentException("Can't run constructor ", e);
/* 127:    */     }
/* 128:    */     catch (NoSuchMethodException e)
/* 129:    */     {
/* 130:137 */       throw new IllegalArgumentException("Can't find constructor ", e);
/* 131:    */     }
/* 132:    */     catch (NoSuchFieldException e)
/* 133:    */     {
/* 134:139 */       throw new IllegalArgumentException("Can't find field ", e);
/* 135:    */     }
/* 136:    */   }
/* 137:    */   
/* 138:    */   public static JobContext cloneContext(JobContext original, Configuration conf)
/* 139:    */     throws IOException, InterruptedException
/* 140:    */   {
/* 141:    */     try
/* 142:    */     {
/* 143:158 */       if ((original instanceof MapContext)) {
/* 144:159 */         return cloneMapContext((Mapper.Context)original, conf, null, null);
/* 145:    */       }
/* 146:160 */       if ((original instanceof ReduceContext)) {
/* 147:161 */         throw new IllegalArgumentException("can't clone ReduceContext");
/* 148:    */       }
/* 149:162 */       if ((original instanceof TaskAttemptContext))
/* 150:    */       {
/* 151:163 */         TaskAttemptContext spec = (TaskAttemptContext)original;
/* 152:164 */         return (JobContext)TASK_CONTEXT_CONSTRUCTOR.newInstance(new Object[] { conf, spec.getTaskAttemptID() });
/* 153:    */       }
/* 154:167 */       return (JobContext)JOB_CONTEXT_CONSTRUCTOR.newInstance(new Object[] { conf, original.getJobID() });
/* 155:    */     }
/* 156:    */     catch (InstantiationException e)
/* 157:    */     {
/* 158:171 */       throw new IllegalArgumentException("Can't clone object", e);
/* 159:    */     }
/* 160:    */     catch (IllegalAccessException e)
/* 161:    */     {
/* 162:173 */       throw new IllegalArgumentException("Can't clone object", e);
/* 163:    */     }
/* 164:    */     catch (InvocationTargetException e)
/* 165:    */     {
/* 166:175 */       throw new IllegalArgumentException("Can't clone object", e);
/* 167:    */     }
/* 168:    */   }
/* 169:    */   
/* 170:    */   public static <K1, V1, K2, V2> Mapper<K1, V1, K2, V2>.Context cloneMapContext(MapContext<K1, V1, K2, V2> context, Configuration conf, RecordReader<K1, V1> reader, RecordWriter<K2, V2> writer)
/* 171:    */     throws IOException, InterruptedException
/* 172:    */   {
/* 173:    */     try
/* 174:    */     {
/* 175:203 */       Object outer = OUTER_MAP_FIELD.get(context);
/* 176:205 */       if ("org.apache.hadoop.mapreduce.lib.map.WrappedMapper$Context".equals(context.getClass().getName())) {
/* 177:207 */         context = (MapContext)WRAPPED_CONTEXT_FIELD.get(context);
/* 178:    */       }
/* 179:210 */       if (reader == null) {
/* 180:211 */         reader = (RecordReader)READER_FIELD.get(context);
/* 181:    */       }
/* 182:213 */       if (writer == null) {
/* 183:214 */         writer = (RecordWriter)WRITER_FIELD.get(context);
/* 184:    */       }
/* 185:216 */       if (useV21)
/* 186:    */       {
/* 187:217 */         Object basis = MAP_CONTEXT_IMPL_CONSTRUCTOR.newInstance(new Object[] { conf, context.getTaskAttemptID(), reader, writer, context.getOutputCommitter(), REPORTER_FIELD.get(context), context.getInputSplit() });
/* 188:    */         
/* 189:    */ 
/* 190:    */ 
/* 191:    */ 
/* 192:    */ 
/* 193:    */ 
/* 194:224 */         return (Mapper.Context)MAP_CONTEXT_CONSTRUCTOR.newInstance(new Object[] { outer, basis });
/* 195:    */       }
/* 196:227 */       return (Mapper.Context)MAP_CONTEXT_CONSTRUCTOR.newInstance(new Object[] { outer, conf, context.getTaskAttemptID(), reader, writer, context.getOutputCommitter(), REPORTER_FIELD.get(context), context.getInputSplit() });
/* 197:    */     }
/* 198:    */     catch (IllegalAccessException e)
/* 199:    */     {
/* 200:236 */       throw new IllegalArgumentException("Can't access field", e);
/* 201:    */     }
/* 202:    */     catch (InstantiationException e)
/* 203:    */     {
/* 204:238 */       throw new IllegalArgumentException("Can't create object", e);
/* 205:    */     }
/* 206:    */     catch (InvocationTargetException e)
/* 207:    */     {
/* 208:240 */       throw new IllegalArgumentException("Can't invoke constructor", e);
/* 209:    */     }
/* 210:    */   }
/* 211:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.ContextFactory
 * JD-Core Version:    0.7.0.1
 */