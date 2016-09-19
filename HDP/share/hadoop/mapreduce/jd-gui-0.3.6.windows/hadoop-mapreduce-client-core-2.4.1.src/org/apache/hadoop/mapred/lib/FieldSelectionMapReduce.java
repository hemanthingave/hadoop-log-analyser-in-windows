/*   1:    */ package org.apache.hadoop.mapred.lib;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import java.util.Iterator;
/*   6:    */ import java.util.List;
/*   7:    */ import org.apache.commons.logging.Log;
/*   8:    */ import org.apache.commons.logging.LogFactory;
/*   9:    */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*  10:    */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*  11:    */ import org.apache.hadoop.io.Text;
/*  12:    */ import org.apache.hadoop.mapred.JobConf;
/*  13:    */ import org.apache.hadoop.mapred.Mapper;
/*  14:    */ import org.apache.hadoop.mapred.OutputCollector;
/*  15:    */ import org.apache.hadoop.mapred.Reducer;
/*  16:    */ import org.apache.hadoop.mapred.Reporter;
/*  17:    */ import org.apache.hadoop.mapred.TextInputFormat;
/*  18:    */ import org.apache.hadoop.mapreduce.lib.fieldsel.FieldSelectionHelper;
/*  19:    */ 
/*  20:    */ @InterfaceAudience.Public
/*  21:    */ @InterfaceStability.Stable
/*  22:    */ public class FieldSelectionMapReduce<K, V>
/*  23:    */   implements Mapper<K, V, Text, Text>, Reducer<Text, Text, Text, Text>
/*  24:    */ {
/*  25:    */   private String mapOutputKeyValueSpec;
/*  26:    */   private boolean ignoreInputKey;
/*  27: 78 */   private String fieldSeparator = "\t";
/*  28: 80 */   private List<Integer> mapOutputKeyFieldList = new ArrayList();
/*  29: 82 */   private List<Integer> mapOutputValueFieldList = new ArrayList();
/*  30: 84 */   private int allMapValueFieldsFrom = -1;
/*  31:    */   private String reduceOutputKeyValueSpec;
/*  32: 88 */   private List<Integer> reduceOutputKeyFieldList = new ArrayList();
/*  33: 90 */   private List<Integer> reduceOutputValueFieldList = new ArrayList();
/*  34: 92 */   private int allReduceValueFieldsFrom = -1;
/*  35: 95 */   public static final Log LOG = LogFactory.getLog("FieldSelectionMapReduce");
/*  36:    */   
/*  37:    */   private String specToString()
/*  38:    */   {
/*  39: 98 */     StringBuffer sb = new StringBuffer();
/*  40: 99 */     sb.append("fieldSeparator: ").append(this.fieldSeparator).append("\n");
/*  41:    */     
/*  42:101 */     sb.append("mapOutputKeyValueSpec: ").append(this.mapOutputKeyValueSpec).append("\n");
/*  43:    */     
/*  44:103 */     sb.append("reduceOutputKeyValueSpec: ").append(this.reduceOutputKeyValueSpec).append("\n");
/*  45:    */     
/*  46:    */ 
/*  47:106 */     sb.append("allMapValueFieldsFrom: ").append(this.allMapValueFieldsFrom).append("\n");
/*  48:    */     
/*  49:    */ 
/*  50:109 */     sb.append("allReduceValueFieldsFrom: ").append(this.allReduceValueFieldsFrom).append("\n");
/*  51:    */     
/*  52:    */ 
/*  53:112 */     int i = 0;
/*  54:    */     
/*  55:114 */     sb.append("mapOutputKeyFieldList.length: ").append(this.mapOutputKeyFieldList.size()).append("\n");
/*  56:116 */     for (i = 0; i < this.mapOutputKeyFieldList.size(); i++) {
/*  57:117 */       sb.append("\t").append(this.mapOutputKeyFieldList.get(i)).append("\n");
/*  58:    */     }
/*  59:119 */     sb.append("mapOutputValueFieldList.length: ").append(this.mapOutputValueFieldList.size()).append("\n");
/*  60:121 */     for (i = 0; i < this.mapOutputValueFieldList.size(); i++) {
/*  61:122 */       sb.append("\t").append(this.mapOutputValueFieldList.get(i)).append("\n");
/*  62:    */     }
/*  63:125 */     sb.append("reduceOutputKeyFieldList.length: ").append(this.reduceOutputKeyFieldList.size()).append("\n");
/*  64:127 */     for (i = 0; i < this.reduceOutputKeyFieldList.size(); i++) {
/*  65:128 */       sb.append("\t").append(this.reduceOutputKeyFieldList.get(i)).append("\n");
/*  66:    */     }
/*  67:130 */     sb.append("reduceOutputValueFieldList.length: ").append(this.reduceOutputValueFieldList.size()).append("\n");
/*  68:132 */     for (i = 0; i < this.reduceOutputValueFieldList.size(); i++) {
/*  69:133 */       sb.append("\t").append(this.reduceOutputValueFieldList.get(i)).append("\n");
/*  70:    */     }
/*  71:135 */     return sb.toString();
/*  72:    */   }
/*  73:    */   
/*  74:    */   public void map(K key, V val, OutputCollector<Text, Text> output, Reporter reporter)
/*  75:    */     throws IOException
/*  76:    */   {
/*  77:144 */     FieldSelectionHelper helper = new FieldSelectionHelper(FieldSelectionHelper.emptyText, FieldSelectionHelper.emptyText);
/*  78:    */     
/*  79:146 */     helper.extractOutputKeyValue(key.toString(), val.toString(), this.fieldSeparator, this.mapOutputKeyFieldList, this.mapOutputValueFieldList, this.allMapValueFieldsFrom, this.ignoreInputKey, true);
/*  80:    */     
/*  81:    */ 
/*  82:149 */     output.collect(helper.getKey(), helper.getValue());
/*  83:    */   }
/*  84:    */   
/*  85:    */   private void parseOutputKeyValueSpec()
/*  86:    */   {
/*  87:153 */     this.allMapValueFieldsFrom = FieldSelectionHelper.parseOutputKeyValueSpec(this.mapOutputKeyValueSpec, this.mapOutputKeyFieldList, this.mapOutputValueFieldList);
/*  88:    */     
/*  89:    */ 
/*  90:156 */     this.allReduceValueFieldsFrom = FieldSelectionHelper.parseOutputKeyValueSpec(this.reduceOutputKeyValueSpec, this.reduceOutputKeyFieldList, this.reduceOutputValueFieldList);
/*  91:    */   }
/*  92:    */   
/*  93:    */   public void configure(JobConf job)
/*  94:    */   {
/*  95:162 */     this.fieldSeparator = job.get("mapreduce.fieldsel.data.field.separator", "\t");
/*  96:    */     
/*  97:164 */     this.mapOutputKeyValueSpec = job.get("mapreduce.fieldsel.map.output.key.value.fields.spec", "0-:");
/*  98:    */     
/*  99:166 */     this.ignoreInputKey = TextInputFormat.class.getCanonicalName().equals(job.getInputFormat().getClass().getCanonicalName());
/* 100:    */     
/* 101:168 */     this.reduceOutputKeyValueSpec = job.get("mapreduce.fieldsel.reduce.output.key.value.fields.spec", "0-:");
/* 102:    */     
/* 103:170 */     parseOutputKeyValueSpec();
/* 104:171 */     LOG.info(specToString());
/* 105:    */   }
/* 106:    */   
/* 107:    */   public void close()
/* 108:    */     throws IOException
/* 109:    */   {}
/* 110:    */   
/* 111:    */   public void reduce(Text key, Iterator<Text> values, OutputCollector<Text, Text> output, Reporter reporter)
/* 112:    */     throws IOException
/* 113:    */   {
/* 114:182 */     String keyStr = key.toString() + this.fieldSeparator;
/* 115:183 */     while (values.hasNext())
/* 116:    */     {
/* 117:184 */       FieldSelectionHelper helper = new FieldSelectionHelper();
/* 118:185 */       helper.extractOutputKeyValue(keyStr, ((Text)values.next()).toString(), this.fieldSeparator, this.reduceOutputKeyFieldList, this.reduceOutputValueFieldList, this.allReduceValueFieldsFrom, false, false);
/* 119:    */       
/* 120:    */ 
/* 121:188 */       output.collect(helper.getKey(), helper.getValue());
/* 122:    */     }
/* 123:    */   }
/* 124:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.lib.FieldSelectionMapReduce
 * JD-Core Version:    0.7.0.1
 */