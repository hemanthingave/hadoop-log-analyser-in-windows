/*   1:    */ package org.apache.hadoop.mapred.lib;
/*   2:    */ 
/*   3:    */ import java.util.Collections;
/*   4:    */ import java.util.HashMap;
/*   5:    */ import java.util.Map;
/*   6:    */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*   7:    */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*   8:    */ import org.apache.hadoop.fs.Path;
/*   9:    */ import org.apache.hadoop.mapred.InputFormat;
/*  10:    */ import org.apache.hadoop.mapred.JobConf;
/*  11:    */ import org.apache.hadoop.mapred.Mapper;
/*  12:    */ import org.apache.hadoop.util.ReflectionUtils;
/*  13:    */ 
/*  14:    */ @InterfaceAudience.Public
/*  15:    */ @InterfaceStability.Stable
/*  16:    */ public class MultipleInputs
/*  17:    */ {
/*  18:    */   public static void addInputPath(JobConf conf, Path path, Class<? extends InputFormat> inputFormatClass)
/*  19:    */   {
/*  20: 50 */     String inputFormatMapping = path.toString() + ";" + inputFormatClass.getName();
/*  21:    */     
/*  22: 52 */     String inputFormats = conf.get("mapreduce.input.multipleinputs.dir.formats");
/*  23: 53 */     conf.set("mapreduce.input.multipleinputs.dir.formats", inputFormats + "," + inputFormatMapping);
/*  24:    */     
/*  25:    */ 
/*  26:    */ 
/*  27: 57 */     conf.setInputFormat(DelegatingInputFormat.class);
/*  28:    */   }
/*  29:    */   
/*  30:    */   public static void addInputPath(JobConf conf, Path path, Class<? extends InputFormat> inputFormatClass, Class<? extends Mapper> mapperClass)
/*  31:    */   {
/*  32: 73 */     addInputPath(conf, path, inputFormatClass);
/*  33:    */     
/*  34: 75 */     String mapperMapping = path.toString() + ";" + mapperClass.getName();
/*  35: 76 */     String mappers = conf.get("mapreduce.input.multipleinputs.dir.mappers");
/*  36: 77 */     conf.set("mapreduce.input.multipleinputs.dir.mappers", mappers + "," + mapperMapping);
/*  37:    */     
/*  38:    */ 
/*  39: 80 */     conf.setMapperClass(DelegatingMapper.class);
/*  40:    */   }
/*  41:    */   
/*  42:    */   static Map<Path, InputFormat> getInputFormatMap(JobConf conf)
/*  43:    */   {
/*  44: 92 */     Map<Path, InputFormat> m = new HashMap();
/*  45: 93 */     String[] pathMappings = conf.get("mapreduce.input.multipleinputs.dir.formats").split(",");
/*  46: 94 */     for (String pathMapping : pathMappings)
/*  47:    */     {
/*  48: 95 */       String[] split = pathMapping.split(";");
/*  49:    */       InputFormat inputFormat;
/*  50:    */       try
/*  51:    */       {
/*  52: 98 */         inputFormat = (InputFormat)ReflectionUtils.newInstance(conf.getClassByName(split[1]), conf);
/*  53:    */       }
/*  54:    */       catch (ClassNotFoundException e)
/*  55:    */       {
/*  56:101 */         throw new RuntimeException(e);
/*  57:    */       }
/*  58:103 */       m.put(new Path(split[0]), inputFormat);
/*  59:    */     }
/*  60:105 */     return m;
/*  61:    */   }
/*  62:    */   
/*  63:    */   static Map<Path, Class<? extends Mapper>> getMapperTypeMap(JobConf conf)
/*  64:    */   {
/*  65:118 */     if (conf.get("mapreduce.input.multipleinputs.dir.mappers") == null) {
/*  66:119 */       return Collections.emptyMap();
/*  67:    */     }
/*  68:121 */     Map<Path, Class<? extends Mapper>> m = new HashMap();
/*  69:122 */     String[] pathMappings = conf.get("mapreduce.input.multipleinputs.dir.mappers").split(",");
/*  70:123 */     for (String pathMapping : pathMappings)
/*  71:    */     {
/*  72:124 */       String[] split = pathMapping.split(";");
/*  73:    */       Class<? extends Mapper> mapClass;
/*  74:    */       try
/*  75:    */       {
/*  76:127 */         mapClass = conf.getClassByName(split[1]);
/*  77:    */       }
/*  78:    */       catch (ClassNotFoundException e)
/*  79:    */       {
/*  80:129 */         throw new RuntimeException(e);
/*  81:    */       }
/*  82:131 */       m.put(new Path(split[0]), mapClass);
/*  83:    */     }
/*  84:133 */     return m;
/*  85:    */   }
/*  86:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.lib.MultipleInputs
 * JD-Core Version:    0.7.0.1
 */