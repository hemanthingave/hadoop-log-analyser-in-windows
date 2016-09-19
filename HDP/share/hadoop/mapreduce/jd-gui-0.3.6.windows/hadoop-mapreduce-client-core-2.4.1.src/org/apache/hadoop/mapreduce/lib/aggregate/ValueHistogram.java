/*   1:    */ package org.apache.hadoop.mapreduce.lib.aggregate;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.Arrays;
/*   5:    */ import java.util.Collection;
/*   6:    */ import java.util.Iterator;
/*   7:    */ import java.util.Map.Entry;
/*   8:    */ import java.util.Set;
/*   9:    */ import java.util.TreeMap;
/*  10:    */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*  11:    */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*  12:    */ 
/*  13:    */ @InterfaceAudience.Public
/*  14:    */ @InterfaceStability.Stable
/*  15:    */ public class ValueHistogram
/*  16:    */   implements ValueAggregator<String>
/*  17:    */ {
/*  18: 40 */   TreeMap<Object, Object> items = null;
/*  19:    */   
/*  20:    */   public ValueHistogram()
/*  21:    */   {
/*  22: 43 */     this.items = new TreeMap();
/*  23:    */   }
/*  24:    */   
/*  25:    */   public void addNextValue(Object val)
/*  26:    */   {
/*  27: 53 */     String valCountStr = val.toString();
/*  28: 54 */     int pos = valCountStr.lastIndexOf("\t");
/*  29: 55 */     String valStr = valCountStr;
/*  30: 56 */     String countStr = "1";
/*  31: 57 */     if (pos >= 0)
/*  32:    */     {
/*  33: 58 */       valStr = valCountStr.substring(0, pos);
/*  34: 59 */       countStr = valCountStr.substring(pos + 1);
/*  35:    */     }
/*  36: 62 */     Long count = (Long)this.items.get(valStr);
/*  37: 63 */     long inc = Long.parseLong(countStr);
/*  38: 65 */     if (count == null) {
/*  39: 66 */       count = Long.valueOf(inc);
/*  40:    */     } else {
/*  41: 68 */       count = Long.valueOf(count.longValue() + inc);
/*  42:    */     }
/*  43: 70 */     this.items.put(valStr, count);
/*  44:    */   }
/*  45:    */   
/*  46:    */   public String getReport()
/*  47:    */   {
/*  48: 84 */     long[] counts = new long[this.items.size()];
/*  49:    */     
/*  50: 86 */     StringBuffer sb = new StringBuffer();
/*  51: 87 */     Iterator<Object> iter = this.items.values().iterator();
/*  52: 88 */     int i = 0;
/*  53: 89 */     while (iter.hasNext())
/*  54:    */     {
/*  55: 90 */       Long count = (Long)iter.next();
/*  56: 91 */       counts[i] = count.longValue();
/*  57: 92 */       i++;
/*  58:    */     }
/*  59: 94 */     Arrays.sort(counts);
/*  60: 95 */     sb.append(counts.length);
/*  61: 96 */     i = 0;
/*  62: 97 */     long acc = 0L;
/*  63: 98 */     while (i < counts.length)
/*  64:    */     {
/*  65: 99 */       long nextVal = counts[i];
/*  66:100 */       int j = i + 1;
/*  67:101 */       while ((j < counts.length) && (counts[j] == nextVal)) {
/*  68:102 */         j++;
/*  69:    */       }
/*  70:104 */       acc += nextVal * (j - i);
/*  71:105 */       i = j;
/*  72:    */     }
/*  73:107 */     double average = 0.0D;
/*  74:108 */     double sd = 0.0D;
/*  75:109 */     if (counts.length > 0)
/*  76:    */     {
/*  77:110 */       sb.append("\t").append(counts[0]);
/*  78:111 */       sb.append("\t").append(counts[(counts.length / 2)]);
/*  79:112 */       sb.append("\t").append(counts[(counts.length - 1)]);
/*  80:    */       
/*  81:114 */       average = acc * 1.0D / counts.length;
/*  82:115 */       sb.append("\t").append(average);
/*  83:    */       
/*  84:117 */       i = 0;
/*  85:118 */       while (i < counts.length)
/*  86:    */       {
/*  87:119 */         double nextDiff = counts[i] - average;
/*  88:120 */         sd += nextDiff * nextDiff;
/*  89:121 */         i++;
/*  90:    */       }
/*  91:123 */       sd = Math.sqrt(sd / counts.length);
/*  92:124 */       sb.append("\t").append(sd);
/*  93:    */     }
/*  94:127 */     return sb.toString();
/*  95:    */   }
/*  96:    */   
/*  97:    */   public String getReportDetails()
/*  98:    */   {
/*  99:136 */     StringBuffer sb = new StringBuffer();
/* 100:137 */     Iterator<Map.Entry<Object, Object>> iter = this.items.entrySet().iterator();
/* 101:138 */     while (iter.hasNext())
/* 102:    */     {
/* 103:139 */       Map.Entry<Object, Object> en = (Map.Entry)iter.next();
/* 104:140 */       Object val = en.getKey();
/* 105:141 */       Long count = (Long)en.getValue();
/* 106:142 */       sb.append("\t").append(val.toString()).append("\t").append(count.longValue()).append("\n");
/* 107:    */     }
/* 108:145 */     return sb.toString();
/* 109:    */   }
/* 110:    */   
/* 111:    */   public ArrayList<String> getCombinerOutput()
/* 112:    */   {
/* 113:153 */     ArrayList<String> retv = new ArrayList();
/* 114:154 */     Iterator<Map.Entry<Object, Object>> iter = this.items.entrySet().iterator();
/* 115:156 */     while (iter.hasNext())
/* 116:    */     {
/* 117:157 */       Map.Entry<Object, Object> en = (Map.Entry)iter.next();
/* 118:158 */       Object val = en.getKey();
/* 119:159 */       Long count = (Long)en.getValue();
/* 120:160 */       retv.add(val.toString() + "\t" + count.longValue());
/* 121:    */     }
/* 122:162 */     return retv;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public TreeMap<Object, Object> getReportItems()
/* 126:    */   {
/* 127:170 */     return this.items;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public void reset()
/* 131:    */   {
/* 132:177 */     this.items = new TreeMap();
/* 133:    */   }
/* 134:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.lib.aggregate.ValueHistogram
 * JD-Core Version:    0.7.0.1
 */