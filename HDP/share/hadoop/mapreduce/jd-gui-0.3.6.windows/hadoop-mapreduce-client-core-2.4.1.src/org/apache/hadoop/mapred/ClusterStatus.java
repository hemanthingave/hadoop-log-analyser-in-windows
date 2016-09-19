/*   1:    */ package org.apache.hadoop.mapred;
/*   2:    */ 
/*   3:    */ import java.io.DataInput;
/*   4:    */ import java.io.DataOutput;
/*   5:    */ import java.io.IOException;
/*   6:    */ import java.util.ArrayList;
/*   7:    */ import java.util.Collection;
/*   8:    */ import java.util.Collections;
/*   9:    */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*  10:    */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*  11:    */ import org.apache.hadoop.io.Text;
/*  12:    */ import org.apache.hadoop.io.Writable;
/*  13:    */ import org.apache.hadoop.io.WritableUtils;
/*  14:    */ import org.apache.hadoop.mapreduce.Cluster.JobTrackerStatus;
/*  15:    */ import org.apache.hadoop.util.StringInterner;
/*  16:    */ 
/*  17:    */ @InterfaceAudience.Public
/*  18:    */ @InterfaceStability.Stable
/*  19:    */ public class ClusterStatus
/*  20:    */   implements Writable
/*  21:    */ {
/*  22:    */   public static final long UNINITIALIZED_MEMORY_VALUE = -1L;
/*  23:    */   private int numActiveTrackers;
/*  24:    */   ClusterStatus() {}
/*  25:    */   
/*  26:    */   public static class BlackListInfo
/*  27:    */     implements Writable
/*  28:    */   {
/*  29:    */     private String trackerName;
/*  30:    */     private String reasonForBlackListing;
/*  31:    */     private String blackListReport;
/*  32:    */     
/*  33:    */     public String getTrackerName()
/*  34:    */     {
/*  35: 94 */       return this.trackerName;
/*  36:    */     }
/*  37:    */     
/*  38:    */     public String getReasonForBlackListing()
/*  39:    */     {
/*  40:103 */       return this.reasonForBlackListing;
/*  41:    */     }
/*  42:    */     
/*  43:    */     void setTrackerName(String trackerName)
/*  44:    */     {
/*  45:112 */       this.trackerName = trackerName;
/*  46:    */     }
/*  47:    */     
/*  48:    */     void setReasonForBlackListing(String reasonForBlackListing)
/*  49:    */     {
/*  50:121 */       this.reasonForBlackListing = reasonForBlackListing;
/*  51:    */     }
/*  52:    */     
/*  53:    */     public String getBlackListReport()
/*  54:    */     {
/*  55:130 */       return this.blackListReport;
/*  56:    */     }
/*  57:    */     
/*  58:    */     void setBlackListReport(String blackListReport)
/*  59:    */     {
/*  60:139 */       this.blackListReport = blackListReport;
/*  61:    */     }
/*  62:    */     
/*  63:    */     public void readFields(DataInput in)
/*  64:    */       throws IOException
/*  65:    */     {
/*  66:144 */       this.trackerName = StringInterner.weakIntern(Text.readString(in));
/*  67:145 */       this.reasonForBlackListing = StringInterner.weakIntern(Text.readString(in));
/*  68:146 */       this.blackListReport = StringInterner.weakIntern(Text.readString(in));
/*  69:    */     }
/*  70:    */     
/*  71:    */     public void write(DataOutput out)
/*  72:    */       throws IOException
/*  73:    */     {
/*  74:151 */       Text.writeString(out, this.trackerName);
/*  75:152 */       Text.writeString(out, this.reasonForBlackListing);
/*  76:153 */       Text.writeString(out, this.blackListReport);
/*  77:    */     }
/*  78:    */     
/*  79:    */     public String toString()
/*  80:    */     {
/*  81:166 */       StringBuilder sb = new StringBuilder();
/*  82:167 */       sb.append(this.trackerName);
/*  83:168 */       sb.append("\t");
/*  84:169 */       sb.append(this.reasonForBlackListing);
/*  85:170 */       sb.append("\t");
/*  86:171 */       sb.append(this.blackListReport.replace("\n", ":"));
/*  87:172 */       return sb.toString();
/*  88:    */     }
/*  89:    */   }
/*  90:    */   
/*  91:180 */   private Collection<String> activeTrackers = new ArrayList();
/*  92:    */   private int numBlacklistedTrackers;
/*  93:    */   private int numExcludedNodes;
/*  94:    */   private long ttExpiryInterval;
/*  95:    */   private int map_tasks;
/*  96:    */   private int reduce_tasks;
/*  97:    */   private int max_map_tasks;
/*  98:    */   private int max_reduce_tasks;
/*  99:    */   private Cluster.JobTrackerStatus status;
/* 100:189 */   private Collection<BlackListInfo> blacklistedTrackersInfo = new ArrayList();
/* 101:    */   private int grayListedTrackers;
/* 102:    */   
/* 103:    */   ClusterStatus(int trackers, int blacklists, long ttExpiryInterval, int maps, int reduces, int maxMaps, int maxReduces, Cluster.JobTrackerStatus status)
/* 104:    */   {
/* 105:210 */     this(trackers, blacklists, ttExpiryInterval, maps, reduces, maxMaps, maxReduces, status, 0);
/* 106:    */   }
/* 107:    */   
/* 108:    */   ClusterStatus(int trackers, int blacklists, long ttExpiryInterval, int maps, int reduces, int maxMaps, int maxReduces, Cluster.JobTrackerStatus status, int numDecommissionedNodes)
/* 109:    */   {
/* 110:230 */     this(trackers, blacklists, ttExpiryInterval, maps, reduces, maxMaps, maxReduces, status, numDecommissionedNodes, 0);
/* 111:    */   }
/* 112:    */   
/* 113:    */   ClusterStatus(int trackers, int blacklists, long ttExpiryInterval, int maps, int reduces, int maxMaps, int maxReduces, Cluster.JobTrackerStatus status, int numDecommissionedNodes, int numGrayListedTrackers)
/* 114:    */   {
/* 115:251 */     this.numActiveTrackers = trackers;
/* 116:252 */     this.numBlacklistedTrackers = blacklists;
/* 117:253 */     this.numExcludedNodes = numDecommissionedNodes;
/* 118:254 */     this.ttExpiryInterval = ttExpiryInterval;
/* 119:255 */     this.map_tasks = maps;
/* 120:256 */     this.reduce_tasks = reduces;
/* 121:257 */     this.max_map_tasks = maxMaps;
/* 122:258 */     this.max_reduce_tasks = maxReduces;
/* 123:259 */     this.status = status;
/* 124:260 */     this.grayListedTrackers = numGrayListedTrackers;
/* 125:    */   }
/* 126:    */   
/* 127:    */   ClusterStatus(Collection<String> activeTrackers, Collection<BlackListInfo> blacklistedTrackers, long ttExpiryInterval, int maps, int reduces, int maxMaps, int maxReduces, Cluster.JobTrackerStatus status)
/* 128:    */   {
/* 129:280 */     this(activeTrackers, blacklistedTrackers, ttExpiryInterval, maps, reduces, maxMaps, maxReduces, status, 0);
/* 130:    */   }
/* 131:    */   
/* 132:    */   ClusterStatus(Collection<String> activeTrackers, Collection<BlackListInfo> blackListedTrackerInfo, long ttExpiryInterval, int maps, int reduces, int maxMaps, int maxReduces, Cluster.JobTrackerStatus status, int numDecommissionNodes)
/* 133:    */   {
/* 134:304 */     this(activeTrackers.size(), blackListedTrackerInfo.size(), ttExpiryInterval, maps, reduces, maxMaps, maxReduces, status, numDecommissionNodes);
/* 135:    */     
/* 136:    */ 
/* 137:307 */     this.activeTrackers = activeTrackers;
/* 138:308 */     this.blacklistedTrackersInfo = blackListedTrackerInfo;
/* 139:    */   }
/* 140:    */   
/* 141:    */   public int getTaskTrackers()
/* 142:    */   {
/* 143:317 */     return this.numActiveTrackers;
/* 144:    */   }
/* 145:    */   
/* 146:    */   public Collection<String> getActiveTrackerNames()
/* 147:    */   {
/* 148:326 */     return this.activeTrackers;
/* 149:    */   }
/* 150:    */   
/* 151:    */   public Collection<String> getBlacklistedTrackerNames()
/* 152:    */   {
/* 153:335 */     ArrayList<String> blacklistedTrackers = new ArrayList();
/* 154:336 */     for (BlackListInfo bi : this.blacklistedTrackersInfo) {
/* 155:337 */       blacklistedTrackers.add(bi.getTrackerName());
/* 156:    */     }
/* 157:339 */     return blacklistedTrackers;
/* 158:    */   }
/* 159:    */   
/* 160:    */   @Deprecated
/* 161:    */   public Collection<String> getGraylistedTrackerNames()
/* 162:    */   {
/* 163:352 */     return Collections.emptySet();
/* 164:    */   }
/* 165:    */   
/* 166:    */   @Deprecated
/* 167:    */   public int getGraylistedTrackers()
/* 168:    */   {
/* 169:365 */     return this.grayListedTrackers;
/* 170:    */   }
/* 171:    */   
/* 172:    */   public int getBlacklistedTrackers()
/* 173:    */   {
/* 174:374 */     return this.numBlacklistedTrackers;
/* 175:    */   }
/* 176:    */   
/* 177:    */   public int getNumExcludedNodes()
/* 178:    */   {
/* 179:382 */     return this.numExcludedNodes;
/* 180:    */   }
/* 181:    */   
/* 182:    */   public long getTTExpiryInterval()
/* 183:    */   {
/* 184:390 */     return this.ttExpiryInterval;
/* 185:    */   }
/* 186:    */   
/* 187:    */   public int getMapTasks()
/* 188:    */   {
/* 189:399 */     return this.map_tasks;
/* 190:    */   }
/* 191:    */   
/* 192:    */   public int getReduceTasks()
/* 193:    */   {
/* 194:408 */     return this.reduce_tasks;
/* 195:    */   }
/* 196:    */   
/* 197:    */   public int getMaxMapTasks()
/* 198:    */   {
/* 199:417 */     return this.max_map_tasks;
/* 200:    */   }
/* 201:    */   
/* 202:    */   public int getMaxReduceTasks()
/* 203:    */   {
/* 204:426 */     return this.max_reduce_tasks;
/* 205:    */   }
/* 206:    */   
/* 207:    */   public Cluster.JobTrackerStatus getJobTrackerStatus()
/* 208:    */   {
/* 209:435 */     return this.status;
/* 210:    */   }
/* 211:    */   
/* 212:    */   @Deprecated
/* 213:    */   public long getMaxMemory()
/* 214:    */   {
/* 215:443 */     return -1L;
/* 216:    */   }
/* 217:    */   
/* 218:    */   @Deprecated
/* 219:    */   public long getUsedMemory()
/* 220:    */   {
/* 221:451 */     return -1L;
/* 222:    */   }
/* 223:    */   
/* 224:    */   public Collection<BlackListInfo> getBlackListedTrackersInfo()
/* 225:    */   {
/* 226:461 */     return this.blacklistedTrackersInfo;
/* 227:    */   }
/* 228:    */   
/* 229:    */   @Deprecated
/* 230:    */   public JobTracker.State getJobTrackerState()
/* 231:    */   {
/* 232:475 */     return JobTracker.State.RUNNING;
/* 233:    */   }
/* 234:    */   
/* 235:    */   public void write(DataOutput out)
/* 236:    */     throws IOException
/* 237:    */   {
/* 238:479 */     if (this.activeTrackers.size() == 0)
/* 239:    */     {
/* 240:480 */       out.writeInt(this.numActiveTrackers);
/* 241:481 */       out.writeInt(0);
/* 242:    */     }
/* 243:    */     else
/* 244:    */     {
/* 245:483 */       out.writeInt(this.activeTrackers.size());
/* 246:484 */       out.writeInt(this.activeTrackers.size());
/* 247:485 */       for (String tracker : this.activeTrackers) {
/* 248:486 */         Text.writeString(out, tracker);
/* 249:    */       }
/* 250:    */     }
/* 251:489 */     if (this.blacklistedTrackersInfo.size() == 0)
/* 252:    */     {
/* 253:490 */       out.writeInt(this.numBlacklistedTrackers);
/* 254:491 */       out.writeInt(this.blacklistedTrackersInfo.size());
/* 255:    */     }
/* 256:    */     else
/* 257:    */     {
/* 258:493 */       out.writeInt(this.blacklistedTrackersInfo.size());
/* 259:494 */       out.writeInt(this.blacklistedTrackersInfo.size());
/* 260:495 */       for (BlackListInfo tracker : this.blacklistedTrackersInfo) {
/* 261:496 */         tracker.write(out);
/* 262:    */       }
/* 263:    */     }
/* 264:499 */     out.writeInt(this.numExcludedNodes);
/* 265:500 */     out.writeLong(this.ttExpiryInterval);
/* 266:501 */     out.writeInt(this.map_tasks);
/* 267:502 */     out.writeInt(this.reduce_tasks);
/* 268:503 */     out.writeInt(this.max_map_tasks);
/* 269:504 */     out.writeInt(this.max_reduce_tasks);
/* 270:505 */     WritableUtils.writeEnum(out, this.status);
/* 271:506 */     out.writeInt(this.grayListedTrackers);
/* 272:    */   }
/* 273:    */   
/* 274:    */   public void readFields(DataInput in)
/* 275:    */     throws IOException
/* 276:    */   {
/* 277:510 */     this.numActiveTrackers = in.readInt();
/* 278:511 */     int numTrackerNames = in.readInt();
/* 279:512 */     if (numTrackerNames > 0) {
/* 280:513 */       for (int i = 0; i < numTrackerNames; i++)
/* 281:    */       {
/* 282:514 */         String name = StringInterner.weakIntern(Text.readString(in));
/* 283:515 */         this.activeTrackers.add(name);
/* 284:    */       }
/* 285:    */     }
/* 286:518 */     this.numBlacklistedTrackers = in.readInt();
/* 287:519 */     int blackListTrackerInfoSize = in.readInt();
/* 288:520 */     if (blackListTrackerInfoSize > 0) {
/* 289:521 */       for (int i = 0; i < blackListTrackerInfoSize; i++)
/* 290:    */       {
/* 291:522 */         BlackListInfo info = new BlackListInfo();
/* 292:523 */         info.readFields(in);
/* 293:524 */         this.blacklistedTrackersInfo.add(info);
/* 294:    */       }
/* 295:    */     }
/* 296:527 */     this.numExcludedNodes = in.readInt();
/* 297:528 */     this.ttExpiryInterval = in.readLong();
/* 298:529 */     this.map_tasks = in.readInt();
/* 299:530 */     this.reduce_tasks = in.readInt();
/* 300:531 */     this.max_map_tasks = in.readInt();
/* 301:532 */     this.max_reduce_tasks = in.readInt();
/* 302:533 */     this.status = ((Cluster.JobTrackerStatus)WritableUtils.readEnum(in, Cluster.JobTrackerStatus.class));
/* 303:534 */     this.grayListedTrackers = in.readInt();
/* 304:    */   }
/* 305:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.ClusterStatus
 * JD-Core Version:    0.7.0.1
 */