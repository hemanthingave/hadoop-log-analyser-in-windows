/*   1:    */ package org.apache.hadoop.mapreduce.lib.input;
/*   2:    */ 
/*   3:    */ import java.io.DataInput;
/*   4:    */ import java.io.DataInputStream;
/*   5:    */ import java.io.DataOutput;
/*   6:    */ import java.io.DataOutputStream;
/*   7:    */ import java.io.IOException;
/*   8:    */ import org.apache.hadoop.conf.Configurable;
/*   9:    */ import org.apache.hadoop.conf.Configuration;
/*  10:    */ import org.apache.hadoop.io.Text;
/*  11:    */ import org.apache.hadoop.io.Writable;
/*  12:    */ import org.apache.hadoop.io.serializer.Deserializer;
/*  13:    */ import org.apache.hadoop.io.serializer.SerializationFactory;
/*  14:    */ import org.apache.hadoop.io.serializer.Serializer;
/*  15:    */ import org.apache.hadoop.mapreduce.InputFormat;
/*  16:    */ import org.apache.hadoop.mapreduce.InputSplit;
/*  17:    */ import org.apache.hadoop.mapreduce.Mapper;
/*  18:    */ import org.apache.hadoop.util.ReflectionUtils;
/*  19:    */ import org.apache.hadoop.util.StringInterner;
/*  20:    */ 
/*  21:    */ class TaggedInputSplit
/*  22:    */   extends InputSplit
/*  23:    */   implements Configurable, Writable
/*  24:    */ {
/*  25:    */   private Class<? extends InputSplit> inputSplitClass;
/*  26:    */   private InputSplit inputSplit;
/*  27:    */   private Class<? extends InputFormat> inputFormatClass;
/*  28:    */   private Class<? extends Mapper> mapperClass;
/*  29:    */   private Configuration conf;
/*  30:    */   
/*  31:    */   public TaggedInputSplit() {}
/*  32:    */   
/*  33:    */   public TaggedInputSplit(InputSplit inputSplit, Configuration conf, Class<? extends InputFormat> inputFormatClass, Class<? extends Mapper> mapperClass)
/*  34:    */   {
/*  35: 74 */     this.inputSplitClass = inputSplit.getClass();
/*  36: 75 */     this.inputSplit = inputSplit;
/*  37: 76 */     this.conf = conf;
/*  38: 77 */     this.inputFormatClass = inputFormatClass;
/*  39: 78 */     this.mapperClass = mapperClass;
/*  40:    */   }
/*  41:    */   
/*  42:    */   public InputSplit getInputSplit()
/*  43:    */   {
/*  44: 87 */     return this.inputSplit;
/*  45:    */   }
/*  46:    */   
/*  47:    */   public Class<? extends InputFormat> getInputFormatClass()
/*  48:    */   {
/*  49: 97 */     return this.inputFormatClass;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public Class<? extends Mapper> getMapperClass()
/*  53:    */   {
/*  54:107 */     return this.mapperClass;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public long getLength()
/*  58:    */     throws IOException, InterruptedException
/*  59:    */   {
/*  60:111 */     return this.inputSplit.getLength();
/*  61:    */   }
/*  62:    */   
/*  63:    */   public String[] getLocations()
/*  64:    */     throws IOException, InterruptedException
/*  65:    */   {
/*  66:115 */     return this.inputSplit.getLocations();
/*  67:    */   }
/*  68:    */   
/*  69:    */   public void readFields(DataInput in)
/*  70:    */     throws IOException
/*  71:    */   {
/*  72:120 */     this.inputSplitClass = readClass(in);
/*  73:121 */     this.inputFormatClass = readClass(in);
/*  74:122 */     this.mapperClass = readClass(in);
/*  75:123 */     this.inputSplit = ((InputSplit)ReflectionUtils.newInstance(this.inputSplitClass, this.conf));
/*  76:    */     
/*  77:125 */     SerializationFactory factory = new SerializationFactory(this.conf);
/*  78:126 */     Deserializer deserializer = factory.getDeserializer(this.inputSplitClass);
/*  79:127 */     deserializer.open((DataInputStream)in);
/*  80:128 */     this.inputSplit = ((InputSplit)deserializer.deserialize(this.inputSplit));
/*  81:    */   }
/*  82:    */   
/*  83:    */   private Class<?> readClass(DataInput in)
/*  84:    */     throws IOException
/*  85:    */   {
/*  86:132 */     String className = StringInterner.weakIntern(Text.readString(in));
/*  87:    */     try
/*  88:    */     {
/*  89:134 */       return this.conf.getClassByName(className);
/*  90:    */     }
/*  91:    */     catch (ClassNotFoundException e)
/*  92:    */     {
/*  93:136 */       throw new RuntimeException("readObject can't find class", e);
/*  94:    */     }
/*  95:    */   }
/*  96:    */   
/*  97:    */   public void write(DataOutput out)
/*  98:    */     throws IOException
/*  99:    */   {
/* 100:142 */     Text.writeString(out, this.inputSplitClass.getName());
/* 101:143 */     Text.writeString(out, this.inputFormatClass.getName());
/* 102:144 */     Text.writeString(out, this.mapperClass.getName());
/* 103:145 */     SerializationFactory factory = new SerializationFactory(this.conf);
/* 104:146 */     Serializer serializer = factory.getSerializer(this.inputSplitClass);
/* 105:    */     
/* 106:148 */     serializer.open((DataOutputStream)out);
/* 107:149 */     serializer.serialize(this.inputSplit);
/* 108:    */   }
/* 109:    */   
/* 110:    */   public Configuration getConf()
/* 111:    */   {
/* 112:153 */     return this.conf;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public void setConf(Configuration conf)
/* 116:    */   {
/* 117:157 */     this.conf = conf;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public String toString()
/* 121:    */   {
/* 122:162 */     return this.inputSplit.toString();
/* 123:    */   }
/* 124:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.lib.input.TaggedInputSplit
 * JD-Core Version:    0.7.0.1
 */