/*   1:    */ package org.apache.hadoop.mapred.lib;
/*   2:    */ 
/*   3:    */ import java.io.DataInput;
/*   4:    */ import java.io.DataOutput;
/*   5:    */ import java.io.IOException;
/*   6:    */ import org.apache.hadoop.conf.Configurable;
/*   7:    */ import org.apache.hadoop.conf.Configuration;
/*   8:    */ import org.apache.hadoop.io.Text;
/*   9:    */ import org.apache.hadoop.mapred.InputFormat;
/*  10:    */ import org.apache.hadoop.mapred.InputSplit;
/*  11:    */ import org.apache.hadoop.mapred.Mapper;
/*  12:    */ import org.apache.hadoop.util.ReflectionUtils;
/*  13:    */ import org.apache.hadoop.util.StringInterner;
/*  14:    */ 
/*  15:    */ class TaggedInputSplit
/*  16:    */   implements Configurable, InputSplit
/*  17:    */ {
/*  18:    */   private Class<? extends InputSplit> inputSplitClass;
/*  19:    */   private InputSplit inputSplit;
/*  20:    */   private Class<? extends InputFormat> inputFormatClass;
/*  21:    */   private Class<? extends Mapper> mapperClass;
/*  22:    */   private Configuration conf;
/*  23:    */   
/*  24:    */   public TaggedInputSplit() {}
/*  25:    */   
/*  26:    */   public TaggedInputSplit(InputSplit inputSplit, Configuration conf, Class<? extends InputFormat> inputFormatClass, Class<? extends Mapper> mapperClass)
/*  27:    */   {
/*  28: 65 */     this.inputSplitClass = inputSplit.getClass();
/*  29: 66 */     this.inputSplit = inputSplit;
/*  30: 67 */     this.conf = conf;
/*  31: 68 */     this.inputFormatClass = inputFormatClass;
/*  32: 69 */     this.mapperClass = mapperClass;
/*  33:    */   }
/*  34:    */   
/*  35:    */   public InputSplit getInputSplit()
/*  36:    */   {
/*  37: 78 */     return this.inputSplit;
/*  38:    */   }
/*  39:    */   
/*  40:    */   public Class<? extends InputFormat> getInputFormatClass()
/*  41:    */   {
/*  42: 87 */     return this.inputFormatClass;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public Class<? extends Mapper> getMapperClass()
/*  46:    */   {
/*  47: 96 */     return this.mapperClass;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public long getLength()
/*  51:    */     throws IOException
/*  52:    */   {
/*  53:100 */     return this.inputSplit.getLength();
/*  54:    */   }
/*  55:    */   
/*  56:    */   public String[] getLocations()
/*  57:    */     throws IOException
/*  58:    */   {
/*  59:104 */     return this.inputSplit.getLocations();
/*  60:    */   }
/*  61:    */   
/*  62:    */   public void readFields(DataInput in)
/*  63:    */     throws IOException
/*  64:    */   {
/*  65:109 */     this.inputSplitClass = readClass(in);
/*  66:110 */     this.inputSplit = ((InputSplit)ReflectionUtils.newInstance(this.inputSplitClass, this.conf));
/*  67:    */     
/*  68:112 */     this.inputSplit.readFields(in);
/*  69:113 */     this.inputFormatClass = readClass(in);
/*  70:114 */     this.mapperClass = readClass(in);
/*  71:    */   }
/*  72:    */   
/*  73:    */   private Class<?> readClass(DataInput in)
/*  74:    */     throws IOException
/*  75:    */   {
/*  76:118 */     String className = StringInterner.weakIntern(Text.readString(in));
/*  77:    */     try
/*  78:    */     {
/*  79:120 */       return this.conf.getClassByName(className);
/*  80:    */     }
/*  81:    */     catch (ClassNotFoundException e)
/*  82:    */     {
/*  83:122 */       throw new RuntimeException("readObject can't find class", e);
/*  84:    */     }
/*  85:    */   }
/*  86:    */   
/*  87:    */   public void write(DataOutput out)
/*  88:    */     throws IOException
/*  89:    */   {
/*  90:127 */     Text.writeString(out, this.inputSplitClass.getName());
/*  91:128 */     this.inputSplit.write(out);
/*  92:129 */     Text.writeString(out, this.inputFormatClass.getName());
/*  93:130 */     Text.writeString(out, this.mapperClass.getName());
/*  94:    */   }
/*  95:    */   
/*  96:    */   public Configuration getConf()
/*  97:    */   {
/*  98:134 */     return this.conf;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public void setConf(Configuration conf)
/* 102:    */   {
/* 103:138 */     this.conf = conf;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public String toString()
/* 107:    */   {
/* 108:143 */     return this.inputSplit.toString();
/* 109:    */   }
/* 110:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.lib.TaggedInputSplit
 * JD-Core Version:    0.7.0.1
 */