/*   1:    */ package org.apache.hadoop.mapreduce.lib.input;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import java.util.HashMap;
/*   6:    */ import java.util.LinkedList;
/*   7:    */ import java.util.List;
/*   8:    */ import java.util.Map;
/*   9:    */ import java.util.Map.Entry;
/*  10:    */ import org.apache.hadoop.classification.InterfaceAudience.Private;
/*  11:    */ import org.apache.hadoop.classification.InterfaceStability.Unstable;
/*  12:    */ import org.apache.hadoop.conf.Configuration;
/*  13:    */ import org.apache.hadoop.fs.Path;
/*  14:    */ import org.apache.hadoop.mapreduce.InputFormat;
/*  15:    */ import org.apache.hadoop.mapreduce.InputSplit;
/*  16:    */ import org.apache.hadoop.mapreduce.Job;
/*  17:    */ import org.apache.hadoop.mapreduce.JobContext;
/*  18:    */ import org.apache.hadoop.mapreduce.Mapper;
/*  19:    */ import org.apache.hadoop.mapreduce.RecordReader;
/*  20:    */ import org.apache.hadoop.mapreduce.TaskAttemptContext;
/*  21:    */ import org.apache.hadoop.util.ReflectionUtils;
/*  22:    */ 
/*  23:    */ @InterfaceAudience.Private
/*  24:    */ @InterfaceStability.Unstable
/*  25:    */ public class DelegatingInputFormat<K, V>
/*  26:    */   extends InputFormat<K, V>
/*  27:    */ {
/*  28:    */   public List<InputSplit> getSplits(JobContext job)
/*  29:    */     throws IOException, InterruptedException
/*  30:    */   {
/*  31: 55 */     Configuration conf = job.getConfiguration();
/*  32: 56 */     Job jobCopy = new Job(conf);
/*  33: 57 */     List<InputSplit> splits = new ArrayList();
/*  34: 58 */     Map<Path, InputFormat> formatMap = MultipleInputs.getInputFormatMap(job);
/*  35:    */     
/*  36: 60 */     Map<Path, Class<? extends Mapper>> mapperMap = MultipleInputs.getMapperTypeMap(job);
/*  37:    */     
/*  38: 62 */     Map<Class<? extends InputFormat>, List<Path>> formatPaths = new HashMap();
/*  39: 66 */     for (Map.Entry<Path, InputFormat> entry : formatMap.entrySet())
/*  40:    */     {
/*  41: 67 */       if (!formatPaths.containsKey(((InputFormat)entry.getValue()).getClass())) {
/*  42: 68 */         formatPaths.put(((InputFormat)entry.getValue()).getClass(), new LinkedList());
/*  43:    */       }
/*  44: 71 */       ((List)formatPaths.get(((InputFormat)entry.getValue()).getClass())).add(entry.getKey());
/*  45:    */     }
/*  46: 75 */     for (Map.Entry<Class<? extends InputFormat>, List<Path>> formatEntry : formatPaths.entrySet())
/*  47:    */     {
/*  48: 76 */       Class<? extends InputFormat> formatClass = (Class)formatEntry.getKey();
/*  49: 77 */       format = (InputFormat)ReflectionUtils.newInstance(formatClass, conf);
/*  50:    */       
/*  51: 79 */       paths = (List)formatEntry.getValue();
/*  52:    */       
/*  53: 81 */       Map<Class<? extends Mapper>, List<Path>> mapperPaths = new HashMap();
/*  54: 86 */       for (Path path : paths)
/*  55:    */       {
/*  56: 87 */         Class<? extends Mapper> mapperClass = (Class)mapperMap.get(path);
/*  57: 88 */         if (!mapperPaths.containsKey(mapperClass)) {
/*  58: 89 */           mapperPaths.put(mapperClass, new LinkedList());
/*  59:    */         }
/*  60: 92 */         ((List)mapperPaths.get(mapperClass)).add(path);
/*  61:    */       }
/*  62: 98 */       for (Map.Entry<Class<? extends Mapper>, List<Path>> mapEntry : mapperPaths.entrySet())
/*  63:    */       {
/*  64: 99 */         paths = (List)mapEntry.getValue();
/*  65:100 */         mapperClass = (Class)mapEntry.getKey();
/*  66:102 */         if (mapperClass == null) {
/*  67:    */           try
/*  68:    */           {
/*  69:104 */             mapperClass = job.getMapperClass();
/*  70:    */           }
/*  71:    */           catch (ClassNotFoundException e)
/*  72:    */           {
/*  73:106 */             throw new IOException("Mapper class is not found", e);
/*  74:    */           }
/*  75:    */         }
/*  76:110 */         FileInputFormat.setInputPaths(jobCopy, (Path[])paths.toArray(new Path[paths.size()]));
/*  77:    */         
/*  78:    */ 
/*  79:    */ 
/*  80:    */ 
/*  81:115 */         List<InputSplit> pathSplits = format.getSplits(jobCopy);
/*  82:116 */         for (InputSplit pathSplit : pathSplits) {
/*  83:117 */           splits.add(new TaggedInputSplit(pathSplit, conf, format.getClass(), mapperClass));
/*  84:    */         }
/*  85:    */       }
/*  86:    */     }
/*  87:    */     InputFormat format;
/*  88:    */     List<Path> paths;
/*  89:    */     Class<? extends Mapper> mapperClass;
/*  90:123 */     return splits;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public RecordReader<K, V> createRecordReader(InputSplit split, TaskAttemptContext context)
/*  94:    */     throws IOException, InterruptedException
/*  95:    */   {
/*  96:129 */     return new DelegatingRecordReader(split, context);
/*  97:    */   }
/*  98:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.lib.input.DelegatingInputFormat
 * JD-Core Version:    0.7.0.1
 */