/*   1:    */ package org.apache.hadoop.mapreduce.lib.join;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import java.util.List;
/*   6:    */ import java.util.Map.Entry;
/*   7:    */ import java.util.regex.Matcher;
/*   8:    */ import java.util.regex.Pattern;
/*   9:    */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*  10:    */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*  11:    */ import org.apache.hadoop.conf.Configuration;
/*  12:    */ import org.apache.hadoop.fs.Path;
/*  13:    */ import org.apache.hadoop.io.WritableComparable;
/*  14:    */ import org.apache.hadoop.mapreduce.InputFormat;
/*  15:    */ import org.apache.hadoop.mapreduce.InputSplit;
/*  16:    */ import org.apache.hadoop.mapreduce.JobContext;
/*  17:    */ import org.apache.hadoop.mapreduce.RecordReader;
/*  18:    */ import org.apache.hadoop.mapreduce.TaskAttemptContext;
/*  19:    */ 
/*  20:    */ @InterfaceAudience.Public
/*  21:    */ @InterfaceStability.Stable
/*  22:    */ public class CompositeInputFormat<K extends WritableComparable>
/*  23:    */   extends InputFormat<K, TupleWritable>
/*  24:    */ {
/*  25:    */   public static final String JOIN_EXPR = "mapreduce.join.expr";
/*  26:    */   public static final String JOIN_COMPARATOR = "mapreduce.join.keycomparator";
/*  27:    */   private Parser.Node root;
/*  28:    */   
/*  29:    */   public void setFormat(Configuration conf)
/*  30:    */     throws IOException
/*  31:    */   {
/*  32: 83 */     addDefaults();
/*  33: 84 */     addUserIdentifiers(conf);
/*  34: 85 */     this.root = Parser.parse(conf.get("mapreduce.join.expr", null), conf);
/*  35:    */   }
/*  36:    */   
/*  37:    */   protected void addDefaults()
/*  38:    */   {
/*  39:    */     try
/*  40:    */     {
/*  41: 93 */       Parser.CNode.addIdentifier("inner", InnerJoinRecordReader.class);
/*  42: 94 */       Parser.CNode.addIdentifier("outer", OuterJoinRecordReader.class);
/*  43: 95 */       Parser.CNode.addIdentifier("override", OverrideRecordReader.class);
/*  44: 96 */       Parser.WNode.addIdentifier("tbl", WrappedRecordReader.class);
/*  45:    */     }
/*  46:    */     catch (NoSuchMethodException e)
/*  47:    */     {
/*  48: 98 */       throw new RuntimeException("FATAL: Failed to init defaults", e);
/*  49:    */     }
/*  50:    */   }
/*  51:    */   
/*  52:    */   private void addUserIdentifiers(Configuration conf)
/*  53:    */     throws IOException
/*  54:    */   {
/*  55:106 */     Pattern x = Pattern.compile("^mapreduce\\.join\\.define\\.(\\w+)$");
/*  56:107 */     for (Map.Entry<String, String> kv : conf)
/*  57:    */     {
/*  58:108 */       Matcher m = x.matcher((CharSequence)kv.getKey());
/*  59:109 */       if (m.matches()) {
/*  60:    */         try
/*  61:    */         {
/*  62:111 */           Parser.CNode.addIdentifier(m.group(1), conf.getClass(m.group(0), null, ComposableRecordReader.class));
/*  63:    */         }
/*  64:    */         catch (NoSuchMethodException e)
/*  65:    */         {
/*  66:114 */           throw new IOException("Invalid define for " + m.group(1), e);
/*  67:    */         }
/*  68:    */       }
/*  69:    */     }
/*  70:    */   }
/*  71:    */   
/*  72:    */   public List<InputSplit> getSplits(JobContext job)
/*  73:    */     throws IOException, InterruptedException
/*  74:    */   {
/*  75:127 */     setFormat(job.getConfiguration());
/*  76:128 */     job.getConfiguration().setLong("mapreduce.input.fileinputformat.split.minsize", 9223372036854775807L);
/*  77:129 */     return this.root.getSplits(job);
/*  78:    */   }
/*  79:    */   
/*  80:    */   public RecordReader<K, TupleWritable> createRecordReader(InputSplit split, TaskAttemptContext taskContext)
/*  81:    */     throws IOException, InterruptedException
/*  82:    */   {
/*  83:142 */     setFormat(taskContext.getConfiguration());
/*  84:143 */     return this.root.createRecordReader(split, taskContext);
/*  85:    */   }
/*  86:    */   
/*  87:    */   public static String compose(Class<? extends InputFormat> inf, String path)
/*  88:    */   {
/*  89:153 */     return compose(inf.getName().intern(), path, new StringBuffer()).toString();
/*  90:    */   }
/*  91:    */   
/*  92:    */   public static String compose(String op, Class<? extends InputFormat> inf, String... path)
/*  93:    */   {
/*  94:164 */     String infname = inf.getName();
/*  95:165 */     StringBuffer ret = new StringBuffer(op + '(');
/*  96:166 */     for (String p : path)
/*  97:    */     {
/*  98:167 */       compose(infname, p, ret);
/*  99:168 */       ret.append(',');
/* 100:    */     }
/* 101:170 */     ret.setCharAt(ret.length() - 1, ')');
/* 102:171 */     return ret.toString();
/* 103:    */   }
/* 104:    */   
/* 105:    */   public static String compose(String op, Class<? extends InputFormat> inf, Path... path)
/* 106:    */   {
/* 107:181 */     ArrayList<String> tmp = new ArrayList(path.length);
/* 108:182 */     for (Path p : path) {
/* 109:183 */       tmp.add(p.toString());
/* 110:    */     }
/* 111:185 */     return compose(op, inf, (String[])tmp.toArray(new String[0]));
/* 112:    */   }
/* 113:    */   
/* 114:    */   private static StringBuffer compose(String inf, String path, StringBuffer sb)
/* 115:    */   {
/* 116:190 */     sb.append("tbl(" + inf + ",\"");
/* 117:191 */     sb.append(path);
/* 118:192 */     sb.append("\")");
/* 119:193 */     return sb;
/* 120:    */   }
/* 121:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.lib.join.CompositeInputFormat
 * JD-Core Version:    0.7.0.1
 */