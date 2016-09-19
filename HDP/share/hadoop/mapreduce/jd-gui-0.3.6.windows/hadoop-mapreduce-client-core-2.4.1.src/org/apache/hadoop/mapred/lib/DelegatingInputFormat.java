/*   1:    */ package org.apache.hadoop.mapred.lib;
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
/*  12:    */ import org.apache.hadoop.fs.Path;
/*  13:    */ import org.apache.hadoop.mapred.FileInputFormat;
/*  14:    */ import org.apache.hadoop.mapred.InputFormat;
/*  15:    */ import org.apache.hadoop.mapred.InputSplit;
/*  16:    */ import org.apache.hadoop.mapred.JobConf;
/*  17:    */ import org.apache.hadoop.mapred.Mapper;
/*  18:    */ import org.apache.hadoop.mapred.RecordReader;
/*  19:    */ import org.apache.hadoop.mapred.Reporter;
/*  20:    */ import org.apache.hadoop.util.ReflectionUtils;
/*  21:    */ 
/*  22:    */ @InterfaceAudience.Private
/*  23:    */ @InterfaceStability.Unstable
/*  24:    */ public class DelegatingInputFormat<K, V>
/*  25:    */   implements InputFormat<K, V>
/*  26:    */ {
/*  27:    */   public InputSplit[] getSplits(JobConf conf, int numSplits)
/*  28:    */     throws IOException
/*  29:    */   {
/*  30: 53 */     JobConf confCopy = new JobConf(conf);
/*  31: 54 */     List<InputSplit> splits = new ArrayList();
/*  32: 55 */     Map<Path, InputFormat> formatMap = MultipleInputs.getInputFormatMap(conf);
/*  33: 56 */     Map<Path, Class<? extends Mapper>> mapperMap = MultipleInputs.getMapperTypeMap(conf);
/*  34:    */     
/*  35: 58 */     Map<Class<? extends InputFormat>, List<Path>> formatPaths = new HashMap();
/*  36: 62 */     for (Map.Entry<Path, InputFormat> entry : formatMap.entrySet())
/*  37:    */     {
/*  38: 63 */       if (!formatPaths.containsKey(((InputFormat)entry.getValue()).getClass())) {
/*  39: 64 */         formatPaths.put(((InputFormat)entry.getValue()).getClass(), new LinkedList());
/*  40:    */       }
/*  41: 67 */       ((List)formatPaths.get(((InputFormat)entry.getValue()).getClass())).add(entry.getKey());
/*  42:    */     }
/*  43: 71 */     for (Map.Entry<Class<? extends InputFormat>, List<Path>> formatEntry : formatPaths.entrySet())
/*  44:    */     {
/*  45: 72 */       Class<? extends InputFormat> formatClass = (Class)formatEntry.getKey();
/*  46: 73 */       format = (InputFormat)ReflectionUtils.newInstance(formatClass, conf);
/*  47:    */       
/*  48: 75 */       paths = (List)formatEntry.getValue();
/*  49:    */       
/*  50: 77 */       Map<Class<? extends Mapper>, List<Path>> mapperPaths = new HashMap();
/*  51: 82 */       for (Path path : paths)
/*  52:    */       {
/*  53: 83 */         Class<? extends Mapper> mapperClass = (Class)mapperMap.get(path);
/*  54: 84 */         if (!mapperPaths.containsKey(mapperClass)) {
/*  55: 85 */           mapperPaths.put(mapperClass, new LinkedList());
/*  56:    */         }
/*  57: 88 */         ((List)mapperPaths.get(mapperClass)).add(path);
/*  58:    */       }
/*  59: 93 */       for (Map.Entry<Class<? extends Mapper>, List<Path>> mapEntry : mapperPaths.entrySet())
/*  60:    */       {
/*  61: 95 */         paths = (List)mapEntry.getValue();
/*  62: 96 */         Class<? extends Mapper> mapperClass = (Class)mapEntry.getKey();
/*  63: 98 */         if (mapperClass == null) {
/*  64: 99 */           mapperClass = conf.getMapperClass();
/*  65:    */         }
/*  66:102 */         FileInputFormat.setInputPaths(confCopy, (Path[])paths.toArray(new Path[paths.size()]));
/*  67:    */         
/*  68:    */ 
/*  69:    */ 
/*  70:    */ 
/*  71:107 */         InputSplit[] pathSplits = format.getSplits(confCopy, numSplits);
/*  72:108 */         for (InputSplit pathSplit : pathSplits) {
/*  73:109 */           splits.add(new TaggedInputSplit(pathSplit, conf, format.getClass(), mapperClass));
/*  74:    */         }
/*  75:    */       }
/*  76:    */     }
/*  77:    */     InputFormat format;
/*  78:    */     List<Path> paths;
/*  79:115 */     return (InputSplit[])splits.toArray(new InputSplit[splits.size()]);
/*  80:    */   }
/*  81:    */   
/*  82:    */   public RecordReader<K, V> getRecordReader(InputSplit split, JobConf conf, Reporter reporter)
/*  83:    */     throws IOException
/*  84:    */   {
/*  85:125 */     TaggedInputSplit taggedInputSplit = (TaggedInputSplit)split;
/*  86:126 */     InputFormat<K, V> inputFormat = (InputFormat)ReflectionUtils.newInstance(taggedInputSplit.getInputFormatClass(), conf);
/*  87:    */     
/*  88:128 */     return inputFormat.getRecordReader(taggedInputSplit.getInputSplit(), conf, reporter);
/*  89:    */   }
/*  90:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.lib.DelegatingInputFormat
 * JD-Core Version:    0.7.0.1
 */