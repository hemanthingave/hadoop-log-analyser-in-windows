/*   1:    */ package org.apache.hadoop.mapreduce.split;
/*   2:    */ 
/*   3:    */ import java.io.DataInput;
/*   4:    */ import java.io.DataOutput;
/*   5:    */ import java.io.IOException;
/*   6:    */ import java.io.UnsupportedEncodingException;
/*   7:    */ import org.apache.hadoop.classification.InterfaceAudience.Private;
/*   8:    */ import org.apache.hadoop.classification.InterfaceStability.Unstable;
/*   9:    */ import org.apache.hadoop.io.Text;
/*  10:    */ import org.apache.hadoop.io.Writable;
/*  11:    */ import org.apache.hadoop.io.WritableUtils;
/*  12:    */ import org.apache.hadoop.mapreduce.InputSplit;
/*  13:    */ 
/*  14:    */ @InterfaceAudience.Private
/*  15:    */ @InterfaceStability.Unstable
/*  16:    */ public class JobSplit
/*  17:    */ {
/*  18:    */   static final int META_SPLIT_VERSION = 1;
/*  19:    */   static final byte[] META_SPLIT_FILE_HEADER;
/*  20:    */   
/*  21:    */   static
/*  22:    */   {
/*  23:    */     try
/*  24:    */     {
/*  25: 53 */       META_SPLIT_FILE_HEADER = "META-SPL".getBytes("UTF-8");
/*  26:    */     }
/*  27:    */     catch (UnsupportedEncodingException u)
/*  28:    */     {
/*  29: 55 */       throw new RuntimeException(u);
/*  30:    */     }
/*  31:    */   }
/*  32:    */   
/*  33: 58 */   public static final TaskSplitMetaInfo EMPTY_TASK_SPLIT = new TaskSplitMetaInfo();
/*  34:    */   
/*  35:    */   public static class SplitMetaInfo
/*  36:    */     implements Writable
/*  37:    */   {
/*  38:    */     private long startOffset;
/*  39:    */     private long inputDataLength;
/*  40:    */     private String[] locations;
/*  41:    */     
/*  42:    */     public SplitMetaInfo() {}
/*  43:    */     
/*  44:    */     public SplitMetaInfo(String[] locations, long startOffset, long inputDataLength)
/*  45:    */     {
/*  46: 77 */       this.locations = locations;
/*  47: 78 */       this.startOffset = startOffset;
/*  48: 79 */       this.inputDataLength = inputDataLength;
/*  49:    */     }
/*  50:    */     
/*  51:    */     public SplitMetaInfo(InputSplit split, long startOffset)
/*  52:    */       throws IOException
/*  53:    */     {
/*  54:    */       try
/*  55:    */       {
/*  56: 84 */         this.locations = split.getLocations();
/*  57: 85 */         this.inputDataLength = split.getLength();
/*  58: 86 */         this.startOffset = startOffset;
/*  59:    */       }
/*  60:    */       catch (InterruptedException ie)
/*  61:    */       {
/*  62: 88 */         throw new IOException(ie);
/*  63:    */       }
/*  64:    */     }
/*  65:    */     
/*  66:    */     public String[] getLocations()
/*  67:    */     {
/*  68: 93 */       return this.locations;
/*  69:    */     }
/*  70:    */     
/*  71:    */     public long getStartOffset()
/*  72:    */     {
/*  73: 97 */       return this.startOffset;
/*  74:    */     }
/*  75:    */     
/*  76:    */     public long getInputDataLength()
/*  77:    */     {
/*  78:101 */       return this.inputDataLength;
/*  79:    */     }
/*  80:    */     
/*  81:    */     public void setInputDataLocations(String[] locations)
/*  82:    */     {
/*  83:105 */       this.locations = locations;
/*  84:    */     }
/*  85:    */     
/*  86:    */     public void setInputDataLength(long length)
/*  87:    */     {
/*  88:109 */       this.inputDataLength = length;
/*  89:    */     }
/*  90:    */     
/*  91:    */     public void readFields(DataInput in)
/*  92:    */       throws IOException
/*  93:    */     {
/*  94:113 */       int len = WritableUtils.readVInt(in);
/*  95:114 */       this.locations = new String[len];
/*  96:115 */       for (int i = 0; i < this.locations.length; i++) {
/*  97:116 */         this.locations[i] = Text.readString(in);
/*  98:    */       }
/*  99:118 */       this.startOffset = WritableUtils.readVLong(in);
/* 100:119 */       this.inputDataLength = WritableUtils.readVLong(in);
/* 101:    */     }
/* 102:    */     
/* 103:    */     public void write(DataOutput out)
/* 104:    */       throws IOException
/* 105:    */     {
/* 106:123 */       WritableUtils.writeVInt(out, this.locations.length);
/* 107:124 */       for (int i = 0; i < this.locations.length; i++) {
/* 108:125 */         Text.writeString(out, this.locations[i]);
/* 109:    */       }
/* 110:127 */       WritableUtils.writeVLong(out, this.startOffset);
/* 111:128 */       WritableUtils.writeVLong(out, this.inputDataLength);
/* 112:    */     }
/* 113:    */     
/* 114:    */     public String toString()
/* 115:    */     {
/* 116:133 */       StringBuffer buf = new StringBuffer();
/* 117:134 */       buf.append("data-size : " + this.inputDataLength + "\n");
/* 118:135 */       buf.append("start-offset : " + this.startOffset + "\n");
/* 119:136 */       buf.append("locations : \n");
/* 120:137 */       for (String loc : this.locations) {
/* 121:138 */         buf.append("  " + loc + "\n");
/* 122:    */       }
/* 123:140 */       return buf.toString();
/* 124:    */     }
/* 125:    */   }
/* 126:    */   
/* 127:    */   public static class TaskSplitMetaInfo
/* 128:    */   {
/* 129:    */     private JobSplit.TaskSplitIndex splitIndex;
/* 130:    */     private long inputDataLength;
/* 131:    */     private String[] locations;
/* 132:    */     
/* 133:    */     public TaskSplitMetaInfo()
/* 134:    */     {
/* 135:152 */       this.splitIndex = new JobSplit.TaskSplitIndex();
/* 136:153 */       this.locations = new String[0];
/* 137:    */     }
/* 138:    */     
/* 139:    */     public TaskSplitMetaInfo(JobSplit.TaskSplitIndex splitIndex, String[] locations, long inputDataLength)
/* 140:    */     {
/* 141:157 */       this.splitIndex = splitIndex;
/* 142:158 */       this.locations = locations;
/* 143:159 */       this.inputDataLength = inputDataLength;
/* 144:    */     }
/* 145:    */     
/* 146:    */     public TaskSplitMetaInfo(InputSplit split, long startOffset)
/* 147:    */       throws InterruptedException, IOException
/* 148:    */     {
/* 149:163 */       this(new JobSplit.TaskSplitIndex("", startOffset), split.getLocations(), split.getLength());
/* 150:    */     }
/* 151:    */     
/* 152:    */     public TaskSplitMetaInfo(String[] locations, long startOffset, long inputDataLength)
/* 153:    */     {
/* 154:169 */       this(new JobSplit.TaskSplitIndex("", startOffset), locations, inputDataLength);
/* 155:    */     }
/* 156:    */     
/* 157:    */     public JobSplit.TaskSplitIndex getSplitIndex()
/* 158:    */     {
/* 159:173 */       return this.splitIndex;
/* 160:    */     }
/* 161:    */     
/* 162:    */     public String getSplitLocation()
/* 163:    */     {
/* 164:177 */       return this.splitIndex.getSplitLocation();
/* 165:    */     }
/* 166:    */     
/* 167:    */     public long getInputDataLength()
/* 168:    */     {
/* 169:180 */       return this.inputDataLength;
/* 170:    */     }
/* 171:    */     
/* 172:    */     public String[] getLocations()
/* 173:    */     {
/* 174:183 */       return this.locations;
/* 175:    */     }
/* 176:    */     
/* 177:    */     public long getStartOffset()
/* 178:    */     {
/* 179:186 */       return this.splitIndex.getStartOffset();
/* 180:    */     }
/* 181:    */   }
/* 182:    */   
/* 183:    */   public static class TaskSplitIndex
/* 184:    */   {
/* 185:    */     private String splitLocation;
/* 186:    */     private long startOffset;
/* 187:    */     
/* 188:    */     public TaskSplitIndex()
/* 189:    */     {
/* 190:198 */       this("", 0L);
/* 191:    */     }
/* 192:    */     
/* 193:    */     public TaskSplitIndex(String splitLocation, long startOffset)
/* 194:    */     {
/* 195:201 */       this.splitLocation = splitLocation;
/* 196:202 */       this.startOffset = startOffset;
/* 197:    */     }
/* 198:    */     
/* 199:    */     public long getStartOffset()
/* 200:    */     {
/* 201:205 */       return this.startOffset;
/* 202:    */     }
/* 203:    */     
/* 204:    */     public String getSplitLocation()
/* 205:    */     {
/* 206:208 */       return this.splitLocation;
/* 207:    */     }
/* 208:    */     
/* 209:    */     public void readFields(DataInput in)
/* 210:    */       throws IOException
/* 211:    */     {
/* 212:211 */       this.splitLocation = Text.readString(in);
/* 213:212 */       this.startOffset = WritableUtils.readVLong(in);
/* 214:    */     }
/* 215:    */     
/* 216:    */     public void write(DataOutput out)
/* 217:    */       throws IOException
/* 218:    */     {
/* 219:215 */       Text.writeString(out, this.splitLocation);
/* 220:216 */       WritableUtils.writeVLong(out, this.startOffset);
/* 221:    */     }
/* 222:    */   }
/* 223:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.split.JobSplit
 * JD-Core Version:    0.7.0.1
 */