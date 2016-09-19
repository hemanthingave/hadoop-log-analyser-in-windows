/*   1:    */ package org.apache.hadoop.mapreduce.lib.input;
/*   2:    */ 
/*   3:    */ import java.util.Collections;
/*   4:    */ import java.util.HashMap;
/*   5:    */ import java.util.Map;
/*   6:    */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*   7:    */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*   8:    */ import org.apache.hadoop.conf.Configuration;
/*   9:    */ import org.apache.hadoop.fs.Path;
/*  10:    */ import org.apache.hadoop.mapreduce.InputFormat;
/*  11:    */ import org.apache.hadoop.mapreduce.Job;
/*  12:    */ import org.apache.hadoop.mapreduce.JobContext;
/*  13:    */ import org.apache.hadoop.mapreduce.Mapper;
/*  14:    */ import org.apache.hadoop.util.ReflectionUtils;
/*  15:    */ 
/*  16:    */ @InterfaceAudience.Public
/*  17:    */ @InterfaceStability.Stable
/*  18:    */ public class MultipleInputs
/*  19:    */ {
/*  20:    */   public static final String DIR_FORMATS = "mapreduce.input.multipleinputs.dir.formats";
/*  21:    */   public static final String DIR_MAPPERS = "mapreduce.input.multipleinputs.dir.mappers";
/*  22:    */   
/*  23:    */   public static void addInputPath(Job job, Path path, Class<? extends InputFormat> inputFormatClass)
/*  24:    */   {
/*  25: 57 */     String inputFormatMapping = path.toString() + ";" + inputFormatClass.getName();
/*  26:    */     
/*  27: 59 */     Configuration conf = job.getConfiguration();
/*  28: 60 */     String inputFormats = conf.get("mapreduce.input.multipleinputs.dir.formats");
/*  29: 61 */     conf.set("mapreduce.input.multipleinputs.dir.formats", inputFormats + "," + inputFormatMapping);
/*  30:    */     
/*  31:    */ 
/*  32:    */ 
/*  33: 65 */     job.setInputFormatClass(DelegatingInputFormat.class);
/*  34:    */   }
/*  35:    */   
/*  36:    */   public static void addInputPath(Job job, Path path, Class<? extends InputFormat> inputFormatClass, Class<? extends Mapper> mapperClass)
/*  37:    */   {
/*  38: 82 */     addInputPath(job, path, inputFormatClass);
/*  39: 83 */     Configuration conf = job.getConfiguration();
/*  40: 84 */     String mapperMapping = path.toString() + ";" + mapperClass.getName();
/*  41: 85 */     String mappers = conf.get("mapreduce.input.multipleinputs.dir.mappers");
/*  42: 86 */     conf.set("mapreduce.input.multipleinputs.dir.mappers", mappers + "," + mapperMapping);
/*  43:    */     
/*  44:    */ 
/*  45: 89 */     job.setMapperClass(DelegatingMapper.class);
/*  46:    */   }
/*  47:    */   
/*  48:    */   static Map<Path, InputFormat> getInputFormatMap(JobContext job)
/*  49:    */   {
/*  50:102 */     Map<Path, InputFormat> m = new HashMap();
/*  51:103 */     Configuration conf = job.getConfiguration();
/*  52:104 */     String[] pathMappings = conf.get("mapreduce.input.multipleinputs.dir.formats").split(",");
/*  53:105 */     for (String pathMapping : pathMappings)
/*  54:    */     {
/*  55:106 */       String[] split = pathMapping.split(";");
/*  56:    */       InputFormat inputFormat;
/*  57:    */       try
/*  58:    */       {
/*  59:109 */         inputFormat = (InputFormat)ReflectionUtils.newInstance(conf.getClassByName(split[1]), conf);
/*  60:    */       }
/*  61:    */       catch (ClassNotFoundException e)
/*  62:    */       {
/*  63:112 */         throw new RuntimeException(e);
/*  64:    */       }
/*  65:114 */       m.put(new Path(split[0]), inputFormat);
/*  66:    */     }
/*  67:116 */     return m;
/*  68:    */   }
/*  69:    */   
/*  70:    */   static Map<Path, Class<? extends Mapper>> getMapperTypeMap(JobContext job)
/*  71:    */   {
/*  72:130 */     Configuration conf = job.getConfiguration();
/*  73:131 */     if (conf.get("mapreduce.input.multipleinputs.dir.mappers") == null) {
/*  74:132 */       return Collections.emptyMap();
/*  75:    */     }
/*  76:134 */     Map<Path, Class<? extends Mapper>> m = new HashMap();
/*  77:    */     
/*  78:136 */     String[] pathMappings = conf.get("mapreduce.input.multipleinputs.dir.mappers").split(",");
/*  79:137 */     for (String pathMapping : pathMappings)
/*  80:    */     {
/*  81:138 */       String[] split = pathMapping.split(";");
/*  82:    */       Class<? extends Mapper> mapClass;
/*  83:    */       try
/*  84:    */       {
/*  85:141 */         mapClass = conf.getClassByName(split[1]);
/*  86:    */       }
/*  87:    */       catch (ClassNotFoundException e)
/*  88:    */       {
/*  89:144 */         throw new RuntimeException(e);
/*  90:    */       }
/*  91:146 */       m.put(new Path(split[0]), mapClass);
/*  92:    */     }
/*  93:148 */     return m;
/*  94:    */   }
/*  95:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.lib.input.MultipleInputs
 * JD-Core Version:    0.7.0.1
 */