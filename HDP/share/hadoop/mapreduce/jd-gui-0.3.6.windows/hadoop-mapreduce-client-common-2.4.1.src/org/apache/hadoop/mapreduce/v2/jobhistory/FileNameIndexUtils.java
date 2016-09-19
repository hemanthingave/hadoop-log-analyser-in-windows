/*   1:    */ package org.apache.hadoop.mapreduce.v2.jobhistory;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.UnsupportedEncodingException;
/*   5:    */ import java.net.URLDecoder;
/*   6:    */ import java.net.URLEncoder;
/*   7:    */ import org.apache.commons.logging.Log;
/*   8:    */ import org.apache.commons.logging.LogFactory;
/*   9:    */ import org.apache.hadoop.mapreduce.TypeConverter;
/*  10:    */ import org.apache.hadoop.mapreduce.v2.api.records.JobId;
/*  11:    */ 
/*  12:    */ public class FileNameIndexUtils
/*  13:    */ {
/*  14:    */   static final int JOB_NAME_TRIM_LENGTH = 50;
/*  15:    */   static final String DELIMITER = "-";
/*  16:    */   static final String DELIMITER_ESCAPE = "%2D";
/*  17: 41 */   private static final Log LOG = LogFactory.getLog(FileNameIndexUtils.class);
/*  18:    */   private static final int JOB_ID_INDEX = 0;
/*  19:    */   private static final int SUBMIT_TIME_INDEX = 1;
/*  20:    */   private static final int USER_INDEX = 2;
/*  21:    */   private static final int JOB_NAME_INDEX = 3;
/*  22:    */   private static final int FINISH_TIME_INDEX = 4;
/*  23:    */   private static final int NUM_MAPS_INDEX = 5;
/*  24:    */   private static final int NUM_REDUCES_INDEX = 6;
/*  25:    */   private static final int JOB_STATUS_INDEX = 7;
/*  26:    */   private static final int QUEUE_NAME_INDEX = 8;
/*  27:    */   private static final int JOB_START_TIME_INDEX = 9;
/*  28:    */   
/*  29:    */   public static String getDoneFileName(JobIndexInfo indexInfo)
/*  30:    */     throws IOException
/*  31:    */   {
/*  32: 63 */     StringBuilder sb = new StringBuilder();
/*  33:    */     
/*  34: 65 */     sb.append(escapeDelimiters(TypeConverter.fromYarn(indexInfo.getJobId()).toString()));
/*  35: 66 */     sb.append("-");
/*  36:    */     
/*  37:    */ 
/*  38: 69 */     sb.append(indexInfo.getSubmitTime());
/*  39: 70 */     sb.append("-");
/*  40:    */     
/*  41:    */ 
/*  42: 73 */     sb.append(escapeDelimiters(getUserName(indexInfo)));
/*  43: 74 */     sb.append("-");
/*  44:    */     
/*  45:    */ 
/*  46: 77 */     sb.append(escapeDelimiters(trimJobName(getJobName(indexInfo))));
/*  47: 78 */     sb.append("-");
/*  48:    */     
/*  49:    */ 
/*  50: 81 */     sb.append(indexInfo.getFinishTime());
/*  51: 82 */     sb.append("-");
/*  52:    */     
/*  53:    */ 
/*  54: 85 */     sb.append(indexInfo.getNumMaps());
/*  55: 86 */     sb.append("-");
/*  56:    */     
/*  57:    */ 
/*  58: 89 */     sb.append(indexInfo.getNumReduces());
/*  59: 90 */     sb.append("-");
/*  60:    */     
/*  61:    */ 
/*  62: 93 */     sb.append(indexInfo.getJobStatus());
/*  63: 94 */     sb.append("-");
/*  64:    */     
/*  65:    */ 
/*  66: 97 */     sb.append(escapeDelimiters(getQueueName(indexInfo)));
/*  67: 98 */     sb.append("-");
/*  68:    */     
/*  69:    */ 
/*  70:101 */     sb.append(indexInfo.getJobStartTime());
/*  71:    */     
/*  72:103 */     sb.append(".jhist");
/*  73:104 */     return encodeJobHistoryFileName(sb.toString());
/*  74:    */   }
/*  75:    */   
/*  76:    */   public static JobIndexInfo getIndexInfo(String jhFileName)
/*  77:    */     throws IOException
/*  78:    */   {
/*  79:115 */     String fileName = jhFileName.substring(0, jhFileName.indexOf(".jhist"));
/*  80:116 */     JobIndexInfo indexInfo = new JobIndexInfo();
/*  81:    */     
/*  82:118 */     String[] jobDetails = fileName.split("-");
/*  83:    */     
/*  84:120 */     org.apache.hadoop.mapreduce.JobID oldJobId = org.apache.hadoop.mapreduce.JobID.forName(decodeJobHistoryFileName(jobDetails[0]));
/*  85:121 */     JobId jobId = TypeConverter.toYarn(oldJobId);
/*  86:122 */     indexInfo.setJobId(jobId);
/*  87:    */     try
/*  88:    */     {
/*  89:    */       try
/*  90:    */       {
/*  91:127 */         indexInfo.setSubmitTime(Long.parseLong(decodeJobHistoryFileName(jobDetails[1])));
/*  92:    */       }
/*  93:    */       catch (NumberFormatException e)
/*  94:    */       {
/*  95:130 */         LOG.warn("Unable to parse submit time from job history file " + jhFileName + " : " + e);
/*  96:    */       }
/*  97:134 */       indexInfo.setUser(decodeJobHistoryFileName(jobDetails[2]));
/*  98:    */       
/*  99:    */ 
/* 100:137 */       indexInfo.setJobName(decodeJobHistoryFileName(jobDetails[3]));
/* 101:    */       try
/* 102:    */       {
/* 103:141 */         indexInfo.setFinishTime(Long.parseLong(decodeJobHistoryFileName(jobDetails[4])));
/* 104:    */       }
/* 105:    */       catch (NumberFormatException e)
/* 106:    */       {
/* 107:144 */         LOG.warn("Unable to parse finish time from job history file " + jhFileName + " : " + e);
/* 108:    */       }
/* 109:    */       try
/* 110:    */       {
/* 111:149 */         indexInfo.setNumMaps(Integer.parseInt(decodeJobHistoryFileName(jobDetails[5])));
/* 112:    */       }
/* 113:    */       catch (NumberFormatException e)
/* 114:    */       {
/* 115:152 */         LOG.warn("Unable to parse num maps from job history file " + jhFileName + " : " + e);
/* 116:    */       }
/* 117:    */       try
/* 118:    */       {
/* 119:157 */         indexInfo.setNumReduces(Integer.parseInt(decodeJobHistoryFileName(jobDetails[6])));
/* 120:    */       }
/* 121:    */       catch (NumberFormatException e)
/* 122:    */       {
/* 123:160 */         LOG.warn("Unable to parse num reduces from job history file " + jhFileName + " : " + e);
/* 124:    */       }
/* 125:164 */       indexInfo.setJobStatus(decodeJobHistoryFileName(jobDetails[7]));
/* 126:    */       
/* 127:    */ 
/* 128:167 */       indexInfo.setQueueName(decodeJobHistoryFileName(jobDetails[8]));
/* 129:    */       try
/* 130:    */       {
/* 131:171 */         indexInfo.setJobStartTime(Long.parseLong(decodeJobHistoryFileName(jobDetails[9])));
/* 132:    */       }
/* 133:    */       catch (NumberFormatException e)
/* 134:    */       {
/* 135:174 */         LOG.warn("Unable to parse launch time from job history file " + jhFileName + " : " + e);
/* 136:    */       }
/* 137:    */     }
/* 138:    */     catch (IndexOutOfBoundsException e)
/* 139:    */     {
/* 140:178 */       LOG.warn("Parsing job history file with partial data encoded into name: " + jhFileName);
/* 141:    */     }
/* 142:182 */     return indexInfo;
/* 143:    */   }
/* 144:    */   
/* 145:    */   public static String encodeJobHistoryFileName(String logFileName)
/* 146:    */     throws IOException
/* 147:    */   {
/* 148:196 */     String replacementDelimiterEscape = null;
/* 149:199 */     if (logFileName.contains("%2D"))
/* 150:    */     {
/* 151:200 */       replacementDelimiterEscape = nonOccursString(logFileName);
/* 152:    */       
/* 153:202 */       logFileName = logFileName.replaceAll("%2D", replacementDelimiterEscape);
/* 154:    */     }
/* 155:205 */     String encodedFileName = null;
/* 156:    */     try
/* 157:    */     {
/* 158:207 */       encodedFileName = URLEncoder.encode(logFileName, "UTF-8");
/* 159:    */     }
/* 160:    */     catch (UnsupportedEncodingException uee)
/* 161:    */     {
/* 162:209 */       IOException ioe = new IOException();
/* 163:210 */       ioe.initCause(uee);
/* 164:211 */       ioe.setStackTrace(uee.getStackTrace());
/* 165:212 */       throw ioe;
/* 166:    */     }
/* 167:216 */     if (replacementDelimiterEscape != null) {
/* 168:217 */       encodedFileName = encodedFileName.replaceAll(replacementDelimiterEscape, "%2D");
/* 169:    */     }
/* 170:220 */     return encodedFileName;
/* 171:    */   }
/* 172:    */   
/* 173:    */   public static String decodeJobHistoryFileName(String logFileName)
/* 174:    */     throws IOException
/* 175:    */   {
/* 176:233 */     String decodedFileName = null;
/* 177:    */     try
/* 178:    */     {
/* 179:235 */       decodedFileName = URLDecoder.decode(logFileName, "UTF-8");
/* 180:    */     }
/* 181:    */     catch (UnsupportedEncodingException uee)
/* 182:    */     {
/* 183:237 */       IOException ioe = new IOException();
/* 184:238 */       ioe.initCause(uee);
/* 185:239 */       ioe.setStackTrace(uee.getStackTrace());
/* 186:240 */       throw ioe;
/* 187:    */     }
/* 188:242 */     return decodedFileName;
/* 189:    */   }
/* 190:    */   
/* 191:    */   static String nonOccursString(String logFileName)
/* 192:    */   {
/* 193:246 */     int adHocIndex = 0;
/* 194:    */     
/* 195:248 */     String unfoundString = "q" + adHocIndex;
/* 196:250 */     while (logFileName.contains(unfoundString)) {
/* 197:251 */       unfoundString = "q" + ++adHocIndex;
/* 198:    */     }
/* 199:254 */     return unfoundString + "q";
/* 200:    */   }
/* 201:    */   
/* 202:    */   private static String getUserName(JobIndexInfo indexInfo)
/* 203:    */   {
/* 204:258 */     return getNonEmptyString(indexInfo.getUser());
/* 205:    */   }
/* 206:    */   
/* 207:    */   private static String getJobName(JobIndexInfo indexInfo)
/* 208:    */   {
/* 209:262 */     return getNonEmptyString(indexInfo.getJobName());
/* 210:    */   }
/* 211:    */   
/* 212:    */   private static String getQueueName(JobIndexInfo indexInfo)
/* 213:    */   {
/* 214:266 */     return getNonEmptyString(indexInfo.getQueueName());
/* 215:    */   }
/* 216:    */   
/* 217:    */   private static String getNonEmptyString(String in)
/* 218:    */   {
/* 219:272 */     if ((in == null) || (in.length() == 0)) {
/* 220:273 */       in = "NA";
/* 221:    */     }
/* 222:275 */     return in;
/* 223:    */   }
/* 224:    */   
/* 225:    */   private static String escapeDelimiters(String escapee)
/* 226:    */   {
/* 227:279 */     return escapee.replaceAll("-", "%2D");
/* 228:    */   }
/* 229:    */   
/* 230:    */   private static String trimJobName(String jobName)
/* 231:    */   {
/* 232:286 */     if (jobName.length() > 50) {
/* 233:287 */       jobName = jobName.substring(0, 50);
/* 234:    */     }
/* 235:289 */     return jobName;
/* 236:    */   }
/* 237:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-common-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.v2.jobhistory.FileNameIndexUtils
 * JD-Core Version:    0.7.0.1
 */