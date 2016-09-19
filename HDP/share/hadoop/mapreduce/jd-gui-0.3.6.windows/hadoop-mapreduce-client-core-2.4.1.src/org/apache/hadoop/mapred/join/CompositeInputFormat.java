/*   1:    */ package org.apache.hadoop.mapred.join;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import java.util.Map.Entry;
/*   6:    */ import java.util.regex.Matcher;
/*   7:    */ import java.util.regex.Pattern;
/*   8:    */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*   9:    */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*  10:    */ import org.apache.hadoop.fs.Path;
/*  11:    */ import org.apache.hadoop.io.WritableComparable;
/*  12:    */ import org.apache.hadoop.mapred.InputFormat;
/*  13:    */ import org.apache.hadoop.mapred.InputSplit;
/*  14:    */ import org.apache.hadoop.mapred.JobConf;
/*  15:    */ import org.apache.hadoop.mapred.Reporter;
/*  16:    */ 
/*  17:    */ @InterfaceAudience.Public
/*  18:    */ @InterfaceStability.Stable
/*  19:    */ public class CompositeInputFormat<K extends WritableComparable>
/*  20:    */   implements ComposableInputFormat<K, TupleWritable>
/*  21:    */ {
/*  22:    */   private Parser.Node root;
/*  23:    */   
/*  24:    */   public void setFormat(JobConf job)
/*  25:    */     throws IOException
/*  26:    */   {
/*  27: 76 */     addDefaults();
/*  28: 77 */     addUserIdentifiers(job);
/*  29: 78 */     this.root = Parser.parse(job.get("mapred.join.expr", null), job);
/*  30:    */   }
/*  31:    */   
/*  32:    */   protected void addDefaults()
/*  33:    */   {
/*  34:    */     try
/*  35:    */     {
/*  36: 86 */       Parser.CNode.addIdentifier("inner", InnerJoinRecordReader.class);
/*  37: 87 */       Parser.CNode.addIdentifier("outer", OuterJoinRecordReader.class);
/*  38: 88 */       Parser.CNode.addIdentifier("override", OverrideRecordReader.class);
/*  39: 89 */       Parser.WNode.addIdentifier("tbl", WrappedRecordReader.class);
/*  40:    */     }
/*  41:    */     catch (NoSuchMethodException e)
/*  42:    */     {
/*  43: 91 */       throw new RuntimeException("FATAL: Failed to init defaults", e);
/*  44:    */     }
/*  45:    */   }
/*  46:    */   
/*  47:    */   private void addUserIdentifiers(JobConf job)
/*  48:    */     throws IOException
/*  49:    */   {
/*  50: 99 */     Pattern x = Pattern.compile("^mapred\\.join\\.define\\.(\\w+)$");
/*  51:100 */     for (Map.Entry<String, String> kv : job)
/*  52:    */     {
/*  53:101 */       Matcher m = x.matcher((CharSequence)kv.getKey());
/*  54:102 */       if (m.matches()) {
/*  55:    */         try
/*  56:    */         {
/*  57:104 */           Parser.CNode.addIdentifier(m.group(1), job.getClass(m.group(0), null, ComposableRecordReader.class));
/*  58:    */         }
/*  59:    */         catch (NoSuchMethodException e)
/*  60:    */         {
/*  61:107 */           throw ((IOException)new IOException("Invalid define for " + m.group(1)).initCause(e));
/*  62:    */         }
/*  63:    */       }
/*  64:    */     }
/*  65:    */   }
/*  66:    */   
/*  67:    */   public InputSplit[] getSplits(JobConf job, int numSplits)
/*  68:    */     throws IOException
/*  69:    */   {
/*  70:119 */     setFormat(job);
/*  71:120 */     job.setLong("mapred.min.split.size", 9223372036854775807L);
/*  72:121 */     return this.root.getSplits(job, numSplits);
/*  73:    */   }
/*  74:    */   
/*  75:    */   public ComposableRecordReader<K, TupleWritable> getRecordReader(InputSplit split, JobConf job, Reporter reporter)
/*  76:    */     throws IOException
/*  77:    */   {
/*  78:133 */     setFormat(job);
/*  79:134 */     return this.root.getRecordReader(split, job, reporter);
/*  80:    */   }
/*  81:    */   
/*  82:    */   public static String compose(Class<? extends InputFormat> inf, String path)
/*  83:    */   {
/*  84:143 */     return compose(inf.getName().intern(), path, new StringBuffer()).toString();
/*  85:    */   }
/*  86:    */   
/*  87:    */   public static String compose(String op, Class<? extends InputFormat> inf, String... path)
/*  88:    */   {
/*  89:153 */     String infname = inf.getName();
/*  90:154 */     StringBuffer ret = new StringBuffer(op + '(');
/*  91:155 */     for (String p : path)
/*  92:    */     {
/*  93:156 */       compose(infname, p, ret);
/*  94:157 */       ret.append(',');
/*  95:    */     }
/*  96:159 */     ret.setCharAt(ret.length() - 1, ')');
/*  97:160 */     return ret.toString();
/*  98:    */   }
/*  99:    */   
/* 100:    */   public static String compose(String op, Class<? extends InputFormat> inf, Path... path)
/* 101:    */   {
/* 102:170 */     ArrayList<String> tmp = new ArrayList(path.length);
/* 103:171 */     for (Path p : path) {
/* 104:172 */       tmp.add(p.toString());
/* 105:    */     }
/* 106:174 */     return compose(op, inf, (String[])tmp.toArray(new String[0]));
/* 107:    */   }
/* 108:    */   
/* 109:    */   private static StringBuffer compose(String inf, String path, StringBuffer sb)
/* 110:    */   {
/* 111:179 */     sb.append("tbl(" + inf + ",\"");
/* 112:180 */     sb.append(path);
/* 113:181 */     sb.append("\")");
/* 114:182 */     return sb;
/* 115:    */   }
/* 116:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.join.CompositeInputFormat
 * JD-Core Version:    0.7.0.1
 */