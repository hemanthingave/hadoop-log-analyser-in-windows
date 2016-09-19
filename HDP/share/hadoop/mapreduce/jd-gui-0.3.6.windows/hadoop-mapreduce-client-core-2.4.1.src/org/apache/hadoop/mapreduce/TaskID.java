/*   1:    */ package org.apache.hadoop.mapreduce;
/*   2:    */ 
/*   3:    */ import java.io.DataInput;
/*   4:    */ import java.io.DataOutput;
/*   5:    */ import java.io.IOException;
/*   6:    */ import java.text.NumberFormat;
/*   7:    */ import java.util.EnumMap;
/*   8:    */ import java.util.HashMap;
/*   9:    */ import java.util.Map;
/*  10:    */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*  11:    */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*  12:    */ import org.apache.hadoop.io.WritableUtils;
/*  13:    */ 
/*  14:    */ @InterfaceAudience.Public
/*  15:    */ @InterfaceStability.Stable
/*  16:    */ public class TaskID
/*  17:    */   extends org.apache.hadoop.mapred.ID
/*  18:    */ {
/*  19:    */   protected static final String TASK = "task";
/*  20: 60 */   protected static final NumberFormat idFormat = ;
/*  21:    */   private JobID jobId;
/*  22:    */   private TaskType type;
/*  23:    */   
/*  24:    */   static
/*  25:    */   {
/*  26: 62 */     idFormat.setGroupingUsed(false);
/*  27: 63 */     idFormat.setMinimumIntegerDigits(6);
/*  28:    */   }
/*  29:    */   
/*  30:    */   public TaskID(JobID jobId, TaskType type, int id)
/*  31:    */   {
/*  32: 76 */     super(id);
/*  33: 77 */     if (jobId == null) {
/*  34: 78 */       throw new IllegalArgumentException("jobId cannot be null");
/*  35:    */     }
/*  36: 80 */     this.jobId = jobId;
/*  37: 81 */     this.type = type;
/*  38:    */   }
/*  39:    */   
/*  40:    */   public TaskID(String jtIdentifier, int jobId, TaskType type, int id)
/*  41:    */   {
/*  42: 92 */     this(new JobID(jtIdentifier, jobId), type, id);
/*  43:    */   }
/*  44:    */   
/*  45:    */   @Deprecated
/*  46:    */   public TaskID(JobID jobId, boolean isMap, int id)
/*  47:    */   {
/*  48:103 */     this(jobId, isMap ? TaskType.MAP : TaskType.REDUCE, id);
/*  49:    */   }
/*  50:    */   
/*  51:    */   @Deprecated
/*  52:    */   public TaskID(String jtIdentifier, int jobId, boolean isMap, int id)
/*  53:    */   {
/*  54:115 */     this(new JobID(jtIdentifier, jobId), isMap, id);
/*  55:    */   }
/*  56:    */   
/*  57:    */   public TaskID()
/*  58:    */   {
/*  59:119 */     this.jobId = new JobID();
/*  60:    */   }
/*  61:    */   
/*  62:    */   public JobID getJobID()
/*  63:    */   {
/*  64:124 */     return this.jobId;
/*  65:    */   }
/*  66:    */   
/*  67:    */   @Deprecated
/*  68:    */   public boolean isMap()
/*  69:    */   {
/*  70:130 */     return this.type == TaskType.MAP;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public TaskType getTaskType()
/*  74:    */   {
/*  75:137 */     return this.type;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public boolean equals(Object o)
/*  79:    */   {
/*  80:142 */     if (!super.equals(o)) {
/*  81:143 */       return false;
/*  82:    */     }
/*  83:145 */     TaskID that = (TaskID)o;
/*  84:146 */     return (this.type == that.type) && (this.jobId.equals(that.jobId));
/*  85:    */   }
/*  86:    */   
/*  87:    */   public int compareTo(ID o)
/*  88:    */   {
/*  89:153 */     TaskID that = (TaskID)o;
/*  90:154 */     int jobComp = this.jobId.compareTo(that.jobId);
/*  91:155 */     if (jobComp == 0)
/*  92:    */     {
/*  93:156 */       if (this.type == that.type) {
/*  94:157 */         return this.id - that.id;
/*  95:    */       }
/*  96:160 */       return this.type.compareTo(that.type);
/*  97:    */     }
/*  98:163 */     return jobComp;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public String toString()
/* 102:    */   {
/* 103:167 */     return appendTo(new StringBuilder("task")).toString();
/* 104:    */   }
/* 105:    */   
/* 106:    */   protected StringBuilder appendTo(StringBuilder builder)
/* 107:    */   {
/* 108:176 */     return this.jobId.appendTo(builder).append('_').append(CharTaskTypeMaps.getRepresentingCharacter(this.type)).append('_').append(idFormat.format(this.id));
/* 109:    */   }
/* 110:    */   
/* 111:    */   public int hashCode()
/* 112:    */   {
/* 113:185 */     return this.jobId.hashCode() * 524287 + this.id;
/* 114:    */   }
/* 115:    */   
/* 116:    */   public void readFields(DataInput in)
/* 117:    */     throws IOException
/* 118:    */   {
/* 119:190 */     super.readFields(in);
/* 120:191 */     this.jobId.readFields(in);
/* 121:192 */     this.type = ((TaskType)WritableUtils.readEnum(in, TaskType.class));
/* 122:    */   }
/* 123:    */   
/* 124:    */   public void write(DataOutput out)
/* 125:    */     throws IOException
/* 126:    */   {
/* 127:197 */     super.write(out);
/* 128:198 */     this.jobId.write(out);
/* 129:199 */     WritableUtils.writeEnum(out, this.type);
/* 130:    */   }
/* 131:    */   
/* 132:    */   public static TaskID forName(String str)
/* 133:    */     throws IllegalArgumentException
/* 134:    */   {
/* 135:208 */     if (str == null) {
/* 136:209 */       return null;
/* 137:    */     }
/* 138:210 */     String exceptionMsg = null;
/* 139:    */     try
/* 140:    */     {
/* 141:212 */       String[] parts = str.split("_");
/* 142:213 */       if ((parts.length == 5) && 
/* 143:214 */         (parts[0].equals("task")))
/* 144:    */       {
/* 145:215 */         String type = parts[3];
/* 146:216 */         TaskType t = CharTaskTypeMaps.getTaskType(type.charAt(0));
/* 147:217 */         if (t != null) {
/* 148:219 */           return new org.apache.hadoop.mapred.TaskID(parts[1], Integer.parseInt(parts[2]), t, Integer.parseInt(parts[4]));
/* 149:    */         }
/* 150:224 */         exceptionMsg = "Bad TaskType identifier. TaskId string : " + str + " is not properly formed.";
/* 151:    */       }
/* 152:    */     }
/* 153:    */     catch (Exception ex) {}
/* 154:230 */     if (exceptionMsg == null) {
/* 155:231 */       exceptionMsg = "TaskId string : " + str + " is not properly formed";
/* 156:    */     }
/* 157:233 */     throw new IllegalArgumentException(exceptionMsg);
/* 158:    */   }
/* 159:    */   
/* 160:    */   public static char getRepresentingCharacter(TaskType type)
/* 161:    */   {
/* 162:241 */     return CharTaskTypeMaps.getRepresentingCharacter(type);
/* 163:    */   }
/* 164:    */   
/* 165:    */   public static TaskType getTaskType(char c)
/* 166:    */   {
/* 167:249 */     return CharTaskTypeMaps.getTaskType(c);
/* 168:    */   }
/* 169:    */   
/* 170:    */   public static String getAllTaskTypes()
/* 171:    */   {
/* 172:253 */     return CharTaskTypeMaps.allTaskTypes;
/* 173:    */   }
/* 174:    */   
/* 175:    */   static class CharTaskTypeMaps
/* 176:    */   {
/* 177:261 */     private static EnumMap<TaskType, Character> typeToCharMap = new EnumMap(TaskType.class);
/* 178:263 */     private static Map<Character, TaskType> charToTypeMap = new HashMap();
/* 179:265 */     static String allTaskTypes = "(m|r|s|c|t)";
/* 180:    */     
/* 181:    */     static
/* 182:    */     {
/* 183:267 */       setupTaskTypeToCharMapping();
/* 184:268 */       setupCharToTaskTypeMapping();
/* 185:    */     }
/* 186:    */     
/* 187:    */     private static void setupTaskTypeToCharMapping()
/* 188:    */     {
/* 189:272 */       typeToCharMap.put(TaskType.MAP, Character.valueOf('m'));
/* 190:273 */       typeToCharMap.put(TaskType.REDUCE, Character.valueOf('r'));
/* 191:274 */       typeToCharMap.put(TaskType.JOB_SETUP, Character.valueOf('s'));
/* 192:275 */       typeToCharMap.put(TaskType.JOB_CLEANUP, Character.valueOf('c'));
/* 193:276 */       typeToCharMap.put(TaskType.TASK_CLEANUP, Character.valueOf('t'));
/* 194:    */     }
/* 195:    */     
/* 196:    */     private static void setupCharToTaskTypeMapping()
/* 197:    */     {
/* 198:280 */       charToTypeMap.put(Character.valueOf('m'), TaskType.MAP);
/* 199:281 */       charToTypeMap.put(Character.valueOf('r'), TaskType.REDUCE);
/* 200:282 */       charToTypeMap.put(Character.valueOf('s'), TaskType.JOB_SETUP);
/* 201:283 */       charToTypeMap.put(Character.valueOf('c'), TaskType.JOB_CLEANUP);
/* 202:284 */       charToTypeMap.put(Character.valueOf('t'), TaskType.TASK_CLEANUP);
/* 203:    */     }
/* 204:    */     
/* 205:    */     static char getRepresentingCharacter(TaskType type)
/* 206:    */     {
/* 207:288 */       return ((Character)typeToCharMap.get(type)).charValue();
/* 208:    */     }
/* 209:    */     
/* 210:    */     static TaskType getTaskType(char c)
/* 211:    */     {
/* 212:291 */       return (TaskType)charToTypeMap.get(Character.valueOf(c));
/* 213:    */     }
/* 214:    */   }
/* 215:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.TaskID
 * JD-Core Version:    0.7.0.1
 */