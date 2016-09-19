/*   1:    */ package org.apache.hadoop.mapred;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.util.regex.PatternSyntaxException;
/*   5:    */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*   6:    */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*   7:    */ import org.apache.hadoop.conf.Configuration;
/*   8:    */ import org.apache.hadoop.util.ReflectionUtils;
/*   9:    */ 
/*  10:    */ @InterfaceAudience.Public
/*  11:    */ @InterfaceStability.Stable
/*  12:    */ public class SequenceFileInputFilter<K, V>
/*  13:    */   extends SequenceFileInputFormat<K, V>
/*  14:    */ {
/*  15:    */   private static final String FILTER_CLASS = "mapreduce.input.sequencefileinputfilter.class";
/*  16:    */   
/*  17:    */   public RecordReader<K, V> getRecordReader(InputSplit split, JobConf job, Reporter reporter)
/*  18:    */     throws IOException
/*  19:    */   {
/*  20: 54 */     reporter.setStatus(split.toString());
/*  21:    */     
/*  22: 56 */     return new FilterRecordReader(job, (FileSplit)split);
/*  23:    */   }
/*  24:    */   
/*  25:    */   public static void setFilterClass(Configuration conf, Class filterClass)
/*  26:    */   {
/*  27: 66 */     conf.set("mapreduce.input.sequencefileinputfilter.class", filterClass.getName());
/*  28:    */   }
/*  29:    */   
/*  30:    */   public static abstract interface Filter
/*  31:    */     extends org.apache.hadoop.mapreduce.lib.input.SequenceFileInputFilter.Filter
/*  32:    */   {}
/*  33:    */   
/*  34:    */   public static abstract class FilterBase
/*  35:    */     extends org.apache.hadoop.mapreduce.lib.input.SequenceFileInputFilter.FilterBase
/*  36:    */     implements SequenceFileInputFilter.Filter
/*  37:    */   {}
/*  38:    */   
/*  39:    */   public static class RegexFilter
/*  40:    */     extends SequenceFileInputFilter.FilterBase
/*  41:    */   {
/*  42:    */     org.apache.hadoop.mapreduce.lib.input.SequenceFileInputFilter.RegexFilter rf;
/*  43:    */     
/*  44:    */     public static void setPattern(Configuration conf, String regex)
/*  45:    */       throws PatternSyntaxException
/*  46:    */     {
/*  47: 92 */       org.apache.hadoop.mapreduce.lib.input.SequenceFileInputFilter.RegexFilter.setPattern(conf, regex);
/*  48:    */     }
/*  49:    */     
/*  50:    */     public RegexFilter()
/*  51:    */     {
/*  52: 97 */       this.rf = new org.apache.hadoop.mapreduce.lib.input.SequenceFileInputFilter.RegexFilter();
/*  53:    */     }
/*  54:    */     
/*  55:    */     public void setConf(Configuration conf)
/*  56:    */     {
/*  57:104 */       this.rf.setConf(conf);
/*  58:    */     }
/*  59:    */     
/*  60:    */     public boolean accept(Object key)
/*  61:    */     {
/*  62:113 */       return this.rf.accept(key);
/*  63:    */     }
/*  64:    */   }
/*  65:    */   
/*  66:    */   public static class PercentFilter
/*  67:    */     extends SequenceFileInputFilter.FilterBase
/*  68:    */   {
/*  69:    */     org.apache.hadoop.mapreduce.lib.input.SequenceFileInputFilter.PercentFilter pf;
/*  70:    */     
/*  71:    */     public static void setFrequency(Configuration conf, int frequency)
/*  72:    */     {
/*  73:130 */       org.apache.hadoop.mapreduce.lib.input.SequenceFileInputFilter.PercentFilter.setFrequency(conf, frequency);
/*  74:    */     }
/*  75:    */     
/*  76:    */     public PercentFilter()
/*  77:    */     {
/*  78:135 */       this.pf = new org.apache.hadoop.mapreduce.lib.input.SequenceFileInputFilter.PercentFilter();
/*  79:    */     }
/*  80:    */     
/*  81:    */     public void setConf(Configuration conf)
/*  82:    */     {
/*  83:144 */       this.pf.setConf(conf);
/*  84:    */     }
/*  85:    */     
/*  86:    */     public boolean accept(Object key)
/*  87:    */     {
/*  88:152 */       return this.pf.accept(key);
/*  89:    */     }
/*  90:    */   }
/*  91:    */   
/*  92:    */   public static class MD5Filter
/*  93:    */     extends SequenceFileInputFilter.FilterBase
/*  94:    */   {
/*  95:    */     public static final int MD5_LEN = 16;
/*  96:    */     org.apache.hadoop.mapreduce.lib.input.SequenceFileInputFilter.MD5Filter mf;
/*  97:    */     
/*  98:    */     public static void setFrequency(Configuration conf, int frequency)
/*  99:    */     {
/* 100:170 */       org.apache.hadoop.mapreduce.lib.input.SequenceFileInputFilter.MD5Filter.setFrequency(conf, frequency);
/* 101:    */     }
/* 102:    */     
/* 103:    */     public MD5Filter()
/* 104:    */     {
/* 105:175 */       this.mf = new org.apache.hadoop.mapreduce.lib.input.SequenceFileInputFilter.MD5Filter();
/* 106:    */     }
/* 107:    */     
/* 108:    */     public void setConf(Configuration conf)
/* 109:    */     {
/* 110:184 */       this.mf.setConf(conf);
/* 111:    */     }
/* 112:    */     
/* 113:    */     public boolean accept(Object key)
/* 114:    */     {
/* 115:192 */       return this.mf.accept(key);
/* 116:    */     }
/* 117:    */   }
/* 118:    */   
/* 119:    */   private static class FilterRecordReader<K, V>
/* 120:    */     extends SequenceFileRecordReader<K, V>
/* 121:    */   {
/* 122:    */     private SequenceFileInputFilter.Filter filter;
/* 123:    */     
/* 124:    */     public FilterRecordReader(Configuration conf, FileSplit split)
/* 125:    */       throws IOException
/* 126:    */     {
/* 127:203 */       super(split);
/* 128:    */       
/* 129:205 */       this.filter = ((SequenceFileInputFilter.Filter)ReflectionUtils.newInstance(conf.getClass("mapreduce.input.sequencefileinputfilter.class", SequenceFileInputFilter.PercentFilter.class), conf));
/* 130:    */     }
/* 131:    */     
/* 132:    */     public synchronized boolean next(K key, V value)
/* 133:    */       throws IOException
/* 134:    */     {
/* 135:211 */       while (next(key)) {
/* 136:212 */         if (this.filter.accept(key))
/* 137:    */         {
/* 138:213 */           getCurrentValue(value);
/* 139:214 */           return true;
/* 140:    */         }
/* 141:    */       }
/* 142:218 */       return false;
/* 143:    */     }
/* 144:    */   }
/* 145:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.SequenceFileInputFilter
 * JD-Core Version:    0.7.0.1
 */