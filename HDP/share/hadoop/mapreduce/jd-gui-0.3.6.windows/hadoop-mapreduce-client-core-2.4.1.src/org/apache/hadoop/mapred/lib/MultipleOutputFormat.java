/*   1:    */ package org.apache.hadoop.mapred.lib;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.util.Iterator;
/*   5:    */ import java.util.Set;
/*   6:    */ import java.util.TreeMap;
/*   7:    */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*   8:    */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*   9:    */ import org.apache.hadoop.fs.FileSystem;
/*  10:    */ import org.apache.hadoop.fs.Path;
/*  11:    */ import org.apache.hadoop.mapred.FileOutputFormat;
/*  12:    */ import org.apache.hadoop.mapred.JobConf;
/*  13:    */ import org.apache.hadoop.mapred.RecordWriter;
/*  14:    */ import org.apache.hadoop.mapred.Reporter;
/*  15:    */ import org.apache.hadoop.util.Progressable;
/*  16:    */ 
/*  17:    */ @InterfaceAudience.Public
/*  18:    */ @InterfaceStability.Stable
/*  19:    */ public abstract class MultipleOutputFormat<K, V>
/*  20:    */   extends FileOutputFormat<K, V>
/*  21:    */ {
/*  22:    */   public RecordWriter<K, V> getRecordWriter(FileSystem fs, JobConf job, String name, Progressable arg3)
/*  23:    */     throws IOException
/*  24:    */   {
/*  25: 77 */     final FileSystem myFS = fs;
/*  26: 78 */     final String myName = generateLeafFileName(name);
/*  27: 79 */     final JobConf myJob = job;
/*  28: 80 */     final Progressable myProgressable = arg3;
/*  29:    */     
/*  30: 82 */     new RecordWriter()
/*  31:    */     {
/*  32: 85 */       TreeMap<String, RecordWriter<K, V>> recordWriters = new TreeMap();
/*  33:    */       
/*  34:    */       public void write(K key, V value)
/*  35:    */         throws IOException
/*  36:    */       {
/*  37: 90 */         String keyBasedPath = MultipleOutputFormat.this.generateFileNameForKeyValue(key, value, myName);
/*  38:    */         
/*  39:    */ 
/*  40: 93 */         String finalPath = MultipleOutputFormat.this.getInputFileBasedOutputFileName(myJob, keyBasedPath);
/*  41:    */         
/*  42:    */ 
/*  43: 96 */         K actualKey = MultipleOutputFormat.this.generateActualKey(key, value);
/*  44: 97 */         V actualValue = MultipleOutputFormat.this.generateActualValue(key, value);
/*  45:    */         
/*  46: 99 */         RecordWriter<K, V> rw = (RecordWriter)this.recordWriters.get(finalPath);
/*  47:100 */         if (rw == null)
/*  48:    */         {
/*  49:104 */           rw = MultipleOutputFormat.this.getBaseRecordWriter(myFS, myJob, finalPath, myProgressable);
/*  50:105 */           this.recordWriters.put(finalPath, rw);
/*  51:    */         }
/*  52:107 */         rw.write(actualKey, actualValue);
/*  53:    */       }
/*  54:    */       
/*  55:    */       public void close(Reporter reporter)
/*  56:    */         throws IOException
/*  57:    */       {
/*  58:111 */         Iterator<String> keys = this.recordWriters.keySet().iterator();
/*  59:112 */         while (keys.hasNext())
/*  60:    */         {
/*  61:113 */           RecordWriter<K, V> rw = (RecordWriter)this.recordWriters.get(keys.next());
/*  62:114 */           rw.close(reporter);
/*  63:    */         }
/*  64:116 */         this.recordWriters.clear();
/*  65:    */       }
/*  66:    */     };
/*  67:    */   }
/*  68:    */   
/*  69:    */   protected String generateLeafFileName(String name)
/*  70:    */   {
/*  71:130 */     return name;
/*  72:    */   }
/*  73:    */   
/*  74:    */   protected String generateFileNameForKeyValue(K key, V value, String name)
/*  75:    */   {
/*  76:145 */     return name;
/*  77:    */   }
/*  78:    */   
/*  79:    */   protected K generateActualKey(K key, V value)
/*  80:    */   {
/*  81:159 */     return key;
/*  82:    */   }
/*  83:    */   
/*  84:    */   protected V generateActualValue(K key, V value)
/*  85:    */   {
/*  86:173 */     return value;
/*  87:    */   }
/*  88:    */   
/*  89:    */   protected String getInputFileBasedOutputFileName(JobConf job, String name)
/*  90:    */   {
/*  91:193 */     String infilepath = job.get("mapreduce.map.input.file");
/*  92:194 */     if (infilepath == null) {
/*  93:197 */       return name;
/*  94:    */     }
/*  95:199 */     int numOfTrailingLegsToUse = job.getInt("mapred.outputformat.numOfTrailingLegs", 0);
/*  96:200 */     if (numOfTrailingLegsToUse <= 0) {
/*  97:201 */       return name;
/*  98:    */     }
/*  99:203 */     Path infile = new Path(infilepath);
/* 100:204 */     Path parent = infile.getParent();
/* 101:205 */     String midName = infile.getName();
/* 102:206 */     Path outPath = new Path(midName);
/* 103:207 */     for (int i = 1; i < numOfTrailingLegsToUse; i++)
/* 104:    */     {
/* 105:208 */       if (parent == null) {
/* 106:    */         break;
/* 107:    */       }
/* 108:209 */       midName = parent.getName();
/* 109:210 */       if (midName.length() == 0) {
/* 110:    */         break;
/* 111:    */       }
/* 112:211 */       parent = parent.getParent();
/* 113:212 */       outPath = new Path(midName, outPath);
/* 114:    */     }
/* 115:214 */     return outPath.toString();
/* 116:    */   }
/* 117:    */   
/* 118:    */   protected abstract RecordWriter<K, V> getBaseRecordWriter(FileSystem paramFileSystem, JobConf paramJobConf, String paramString, Progressable paramProgressable)
/* 119:    */     throws IOException;
/* 120:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.lib.MultipleOutputFormat
 * JD-Core Version:    0.7.0.1
 */