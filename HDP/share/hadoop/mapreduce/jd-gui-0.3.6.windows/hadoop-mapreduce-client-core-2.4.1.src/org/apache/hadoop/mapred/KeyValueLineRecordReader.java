/*   1:    */ package org.apache.hadoop.mapred;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*   5:    */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*   6:    */ import org.apache.hadoop.conf.Configuration;
/*   7:    */ import org.apache.hadoop.io.LongWritable;
/*   8:    */ import org.apache.hadoop.io.Text;
/*   9:    */ 
/*  10:    */ @InterfaceAudience.Public
/*  11:    */ @InterfaceStability.Stable
/*  12:    */ public class KeyValueLineRecordReader
/*  13:    */   implements RecordReader<Text, Text>
/*  14:    */ {
/*  15:    */   private final LineRecordReader lineRecordReader;
/*  16: 41 */   private byte separator = 9;
/*  17:    */   private LongWritable dummyKey;
/*  18:    */   private Text innerValue;
/*  19:    */   
/*  20:    */   public Class getKeyClass()
/*  21:    */   {
/*  22: 47 */     return Text.class;
/*  23:    */   }
/*  24:    */   
/*  25:    */   public Text createKey()
/*  26:    */   {
/*  27: 50 */     return new Text();
/*  28:    */   }
/*  29:    */   
/*  30:    */   public Text createValue()
/*  31:    */   {
/*  32: 54 */     return new Text();
/*  33:    */   }
/*  34:    */   
/*  35:    */   public KeyValueLineRecordReader(Configuration job, FileSplit split)
/*  36:    */     throws IOException
/*  37:    */   {
/*  38: 60 */     this.lineRecordReader = new LineRecordReader(job, split);
/*  39: 61 */     this.dummyKey = this.lineRecordReader.createKey();
/*  40: 62 */     this.innerValue = this.lineRecordReader.createValue();
/*  41: 63 */     String sepStr = job.get("mapreduce.input.keyvaluelinerecordreader.key.value.separator", "\t");
/*  42: 64 */     this.separator = ((byte)sepStr.charAt(0));
/*  43:    */   }
/*  44:    */   
/*  45:    */   public static int findSeparator(byte[] utf, int start, int length, byte sep)
/*  46:    */   {
/*  47: 69 */     return org.apache.hadoop.mapreduce.lib.input.KeyValueLineRecordReader.findSeparator(utf, start, length, sep);
/*  48:    */   }
/*  49:    */   
/*  50:    */   public synchronized boolean next(Text key, Text value)
/*  51:    */     throws IOException
/*  52:    */   {
/*  53: 76 */     byte[] line = null;
/*  54: 77 */     int lineLen = -1;
/*  55: 78 */     if (this.lineRecordReader.next(this.dummyKey, this.innerValue))
/*  56:    */     {
/*  57: 79 */       line = this.innerValue.getBytes();
/*  58: 80 */       lineLen = this.innerValue.getLength();
/*  59:    */     }
/*  60:    */     else
/*  61:    */     {
/*  62: 82 */       return false;
/*  63:    */     }
/*  64: 84 */     if (line == null) {
/*  65: 85 */       return false;
/*  66:    */     }
/*  67: 86 */     int pos = findSeparator(line, 0, lineLen, this.separator);
/*  68: 87 */     org.apache.hadoop.mapreduce.lib.input.KeyValueLineRecordReader.setKeyValue(key, value, line, lineLen, pos);
/*  69:    */     
/*  70: 89 */     return true;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public float getProgress()
/*  74:    */     throws IOException
/*  75:    */   {
/*  76: 93 */     return this.lineRecordReader.getProgress();
/*  77:    */   }
/*  78:    */   
/*  79:    */   public synchronized long getPos()
/*  80:    */     throws IOException
/*  81:    */   {
/*  82: 97 */     return this.lineRecordReader.getPos();
/*  83:    */   }
/*  84:    */   
/*  85:    */   public synchronized void close()
/*  86:    */     throws IOException
/*  87:    */   {
/*  88:101 */     this.lineRecordReader.close();
/*  89:    */   }
/*  90:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.KeyValueLineRecordReader
 * JD-Core Version:    0.7.0.1
 */