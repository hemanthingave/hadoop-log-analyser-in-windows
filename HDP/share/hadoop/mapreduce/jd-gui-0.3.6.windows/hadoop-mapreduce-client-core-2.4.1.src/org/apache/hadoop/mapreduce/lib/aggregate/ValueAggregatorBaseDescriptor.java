/*   1:    */ package org.apache.hadoop.mapreduce.lib.aggregate;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.Map.Entry;
/*   5:    */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*   6:    */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*   7:    */ import org.apache.hadoop.conf.Configuration;
/*   8:    */ import org.apache.hadoop.io.Text;
/*   9:    */ 
/*  10:    */ @InterfaceAudience.Public
/*  11:    */ @InterfaceStability.Stable
/*  12:    */ public class ValueAggregatorBaseDescriptor
/*  13:    */   implements ValueAggregatorDescriptor
/*  14:    */ {
/*  15:    */   public static final String UNIQ_VALUE_COUNT = "UniqValueCount";
/*  16:    */   public static final String LONG_VALUE_SUM = "LongValueSum";
/*  17:    */   public static final String DOUBLE_VALUE_SUM = "DoubleValueSum";
/*  18:    */   public static final String VALUE_HISTOGRAM = "ValueHistogram";
/*  19:    */   public static final String LONG_VALUE_MAX = "LongValueMax";
/*  20:    */   public static final String LONG_VALUE_MIN = "LongValueMin";
/*  21:    */   public static final String STRING_VALUE_MAX = "StringValueMax";
/*  22:    */   public static final String STRING_VALUE_MIN = "StringValueMin";
/*  23:    */   public String inputFile;
/*  24:    */   
/*  25:    */   public ValueAggregatorBaseDescriptor()
/*  26:    */   {
/*  27: 55 */     this.inputFile = null;
/*  28:    */   }
/*  29:    */   
/*  30:    */   private static class MyEntry
/*  31:    */     implements Map.Entry<Text, Text>
/*  32:    */   {
/*  33:    */     Text key;
/*  34:    */     Text val;
/*  35:    */     
/*  36:    */     public Text getKey()
/*  37:    */     {
/*  38: 63 */       return this.key;
/*  39:    */     }
/*  40:    */     
/*  41:    */     public Text getValue()
/*  42:    */     {
/*  43: 67 */       return this.val;
/*  44:    */     }
/*  45:    */     
/*  46:    */     public Text setValue(Text val)
/*  47:    */     {
/*  48: 71 */       this.val = val;
/*  49: 72 */       return val;
/*  50:    */     }
/*  51:    */     
/*  52:    */     public MyEntry(Text key, Text val)
/*  53:    */     {
/*  54: 76 */       this.key = key;
/*  55: 77 */       this.val = val;
/*  56:    */     }
/*  57:    */   }
/*  58:    */   
/*  59:    */   public static Map.Entry<Text, Text> generateEntry(String type, String id, Text val)
/*  60:    */   {
/*  61: 91 */     Text key = new Text(type + ":" + id);
/*  62: 92 */     return new MyEntry(key, val);
/*  63:    */   }
/*  64:    */   
/*  65:    */   public static ValueAggregator generateValueAggregator(String type, long uniqCount)
/*  66:    */   {
/*  67:103 */     if (type.compareToIgnoreCase("LongValueSum") == 0) {
/*  68:104 */       return new LongValueSum();
/*  69:    */     }
/*  70:105 */     if (type.compareToIgnoreCase("LongValueMax") == 0) {
/*  71:106 */       return new LongValueMax();
/*  72:    */     }
/*  73:107 */     if (type.compareToIgnoreCase("LongValueMin") == 0) {
/*  74:108 */       return new LongValueMin();
/*  75:    */     }
/*  76:109 */     if (type.compareToIgnoreCase("StringValueMax") == 0) {
/*  77:110 */       return new StringValueMax();
/*  78:    */     }
/*  79:111 */     if (type.compareToIgnoreCase("StringValueMin") == 0) {
/*  80:112 */       return new StringValueMin();
/*  81:    */     }
/*  82:113 */     if (type.compareToIgnoreCase("DoubleValueSum") == 0) {
/*  83:114 */       return new DoubleValueSum();
/*  84:    */     }
/*  85:115 */     if (type.compareToIgnoreCase("UniqValueCount") == 0) {
/*  86:116 */       return new UniqValueCount(uniqCount);
/*  87:    */     }
/*  88:117 */     if (type.compareToIgnoreCase("ValueHistogram") == 0) {
/*  89:118 */       return new ValueHistogram();
/*  90:    */     }
/*  91:120 */     return null;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public ArrayList<Map.Entry<Text, Text>> generateKeyValPairs(Object key, Object val)
/*  95:    */   {
/*  96:142 */     ArrayList<Map.Entry<Text, Text>> retv = new ArrayList();
/*  97:143 */     String countType = "LongValueSum";
/*  98:144 */     String id = "record_count";
/*  99:145 */     Map.Entry<Text, Text> e = generateEntry(countType, id, ONE);
/* 100:146 */     if (e != null) {
/* 101:147 */       retv.add(e);
/* 102:    */     }
/* 103:149 */     if (this.inputFile != null)
/* 104:    */     {
/* 105:150 */       e = generateEntry(countType, this.inputFile, ONE);
/* 106:151 */       if (e != null) {
/* 107:152 */         retv.add(e);
/* 108:    */       }
/* 109:    */     }
/* 110:155 */     return retv;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public void configure(Configuration conf)
/* 114:    */   {
/* 115:164 */     this.inputFile = conf.get("mapreduce.map.input.file");
/* 116:    */   }
/* 117:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.lib.aggregate.ValueAggregatorBaseDescriptor
 * JD-Core Version:    0.7.0.1
 */