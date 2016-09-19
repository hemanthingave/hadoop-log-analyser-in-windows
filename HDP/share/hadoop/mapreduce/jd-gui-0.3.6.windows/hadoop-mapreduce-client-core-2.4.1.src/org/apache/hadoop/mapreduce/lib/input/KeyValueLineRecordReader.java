/*   1:    */ package org.apache.hadoop.mapreduce.lib.input;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*   5:    */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*   6:    */ import org.apache.hadoop.conf.Configuration;
/*   7:    */ import org.apache.hadoop.io.Text;
/*   8:    */ import org.apache.hadoop.mapreduce.InputSplit;
/*   9:    */ import org.apache.hadoop.mapreduce.RecordReader;
/*  10:    */ import org.apache.hadoop.mapreduce.TaskAttemptContext;
/*  11:    */ 
/*  12:    */ @InterfaceAudience.Public
/*  13:    */ @InterfaceStability.Stable
/*  14:    */ public class KeyValueLineRecordReader
/*  15:    */   extends RecordReader<Text, Text>
/*  16:    */ {
/*  17:    */   public static final String KEY_VALUE_SEPERATOR = "mapreduce.input.keyvaluelinerecordreader.key.value.separator";
/*  18:    */   private final LineRecordReader lineRecordReader;
/*  19: 45 */   private byte separator = 9;
/*  20:    */   private Text innerValue;
/*  21:    */   private Text key;
/*  22:    */   private Text value;
/*  23:    */   
/*  24:    */   public Class getKeyClass()
/*  25:    */   {
/*  26: 53 */     return Text.class;
/*  27:    */   }
/*  28:    */   
/*  29:    */   public KeyValueLineRecordReader(Configuration conf)
/*  30:    */     throws IOException
/*  31:    */   {
/*  32: 58 */     this.lineRecordReader = new LineRecordReader();
/*  33: 59 */     String sepStr = conf.get("mapreduce.input.keyvaluelinerecordreader.key.value.separator", "\t");
/*  34: 60 */     this.separator = ((byte)sepStr.charAt(0));
/*  35:    */   }
/*  36:    */   
/*  37:    */   public void initialize(InputSplit genericSplit, TaskAttemptContext context)
/*  38:    */     throws IOException
/*  39:    */   {
/*  40: 65 */     this.lineRecordReader.initialize(genericSplit, context);
/*  41:    */   }
/*  42:    */   
/*  43:    */   public static int findSeparator(byte[] utf, int start, int length, byte sep)
/*  44:    */   {
/*  45: 70 */     for (int i = start; i < start + length; i++) {
/*  46: 71 */       if (utf[i] == sep) {
/*  47: 72 */         return i;
/*  48:    */       }
/*  49:    */     }
/*  50: 75 */     return -1;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public static void setKeyValue(Text key, Text value, byte[] line, int lineLen, int pos)
/*  54:    */   {
/*  55: 80 */     if (pos == -1)
/*  56:    */     {
/*  57: 81 */       key.set(line, 0, lineLen);
/*  58: 82 */       value.set("");
/*  59:    */     }
/*  60:    */     else
/*  61:    */     {
/*  62: 84 */       key.set(line, 0, pos);
/*  63: 85 */       value.set(line, pos + 1, lineLen - pos - 1);
/*  64:    */     }
/*  65:    */   }
/*  66:    */   
/*  67:    */   public synchronized boolean nextKeyValue()
/*  68:    */     throws IOException
/*  69:    */   {
/*  70: 91 */     byte[] line = null;
/*  71: 92 */     int lineLen = -1;
/*  72: 93 */     if (this.lineRecordReader.nextKeyValue())
/*  73:    */     {
/*  74: 94 */       this.innerValue = this.lineRecordReader.getCurrentValue();
/*  75: 95 */       line = this.innerValue.getBytes();
/*  76: 96 */       lineLen = this.innerValue.getLength();
/*  77:    */     }
/*  78:    */     else
/*  79:    */     {
/*  80: 98 */       return false;
/*  81:    */     }
/*  82:100 */     if (line == null) {
/*  83:101 */       return false;
/*  84:    */     }
/*  85:102 */     if (this.key == null) {
/*  86:103 */       this.key = new Text();
/*  87:    */     }
/*  88:105 */     if (this.value == null) {
/*  89:106 */       this.value = new Text();
/*  90:    */     }
/*  91:108 */     int pos = findSeparator(line, 0, lineLen, this.separator);
/*  92:109 */     setKeyValue(this.key, this.value, line, lineLen, pos);
/*  93:110 */     return true;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public Text getCurrentKey()
/*  97:    */   {
/*  98:114 */     return this.key;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public Text getCurrentValue()
/* 102:    */   {
/* 103:118 */     return this.value;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public float getProgress()
/* 107:    */     throws IOException
/* 108:    */   {
/* 109:122 */     return this.lineRecordReader.getProgress();
/* 110:    */   }
/* 111:    */   
/* 112:    */   public synchronized void close()
/* 113:    */     throws IOException
/* 114:    */   {
/* 115:126 */     this.lineRecordReader.close();
/* 116:    */   }
/* 117:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.lib.input.KeyValueLineRecordReader
 * JD-Core Version:    0.7.0.1
 */