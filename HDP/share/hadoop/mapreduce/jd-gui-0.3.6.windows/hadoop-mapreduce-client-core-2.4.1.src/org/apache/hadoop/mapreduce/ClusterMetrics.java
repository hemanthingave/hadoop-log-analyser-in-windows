/*   1:    */ package org.apache.hadoop.mapreduce;
/*   2:    */ 
/*   3:    */ import java.io.DataInput;
/*   4:    */ import java.io.DataOutput;
/*   5:    */ import java.io.IOException;
/*   6:    */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*   7:    */ import org.apache.hadoop.classification.InterfaceStability.Evolving;
/*   8:    */ import org.apache.hadoop.io.Writable;
/*   9:    */ 
/*  10:    */ @InterfaceAudience.Public
/*  11:    */ @InterfaceStability.Evolving
/*  12:    */ public class ClusterMetrics
/*  13:    */   implements Writable
/*  14:    */ {
/*  15:    */   private int runningMaps;
/*  16:    */   private int runningReduces;
/*  17:    */   private int occupiedMapSlots;
/*  18:    */   private int occupiedReduceSlots;
/*  19:    */   private int reservedMapSlots;
/*  20:    */   private int reservedReduceSlots;
/*  21:    */   private int totalMapSlots;
/*  22:    */   private int totalReduceSlots;
/*  23:    */   private int totalJobSubmissions;
/*  24:    */   private int numTrackers;
/*  25:    */   private int numBlacklistedTrackers;
/*  26:    */   private int numGraylistedTrackers;
/*  27:    */   private int numDecommissionedTrackers;
/*  28:    */   
/*  29:    */   public ClusterMetrics() {}
/*  30:    */   
/*  31:    */   public ClusterMetrics(int runningMaps, int runningReduces, int occupiedMapSlots, int occupiedReduceSlots, int reservedMapSlots, int reservedReduceSlots, int mapSlots, int reduceSlots, int totalJobSubmissions, int numTrackers, int numBlacklistedTrackers, int numDecommissionedNodes)
/*  32:    */   {
/*  33: 83 */     this(runningMaps, runningReduces, occupiedMapSlots, occupiedReduceSlots, reservedMapSlots, reservedReduceSlots, mapSlots, reduceSlots, totalJobSubmissions, numTrackers, numBlacklistedTrackers, 0, numDecommissionedNodes);
/*  34:    */   }
/*  35:    */   
/*  36:    */   public ClusterMetrics(int runningMaps, int runningReduces, int occupiedMapSlots, int occupiedReduceSlots, int reservedMapSlots, int reservedReduceSlots, int mapSlots, int reduceSlots, int totalJobSubmissions, int numTrackers, int numBlacklistedTrackers, int numGraylistedTrackers, int numDecommissionedNodes)
/*  37:    */   {
/*  38: 94 */     this.runningMaps = runningMaps;
/*  39: 95 */     this.runningReduces = runningReduces;
/*  40: 96 */     this.occupiedMapSlots = occupiedMapSlots;
/*  41: 97 */     this.occupiedReduceSlots = occupiedReduceSlots;
/*  42: 98 */     this.reservedMapSlots = reservedMapSlots;
/*  43: 99 */     this.reservedReduceSlots = reservedReduceSlots;
/*  44:100 */     this.totalMapSlots = mapSlots;
/*  45:101 */     this.totalReduceSlots = reduceSlots;
/*  46:102 */     this.totalJobSubmissions = totalJobSubmissions;
/*  47:103 */     this.numTrackers = numTrackers;
/*  48:104 */     this.numBlacklistedTrackers = numBlacklistedTrackers;
/*  49:105 */     this.numGraylistedTrackers = numGraylistedTrackers;
/*  50:106 */     this.numDecommissionedTrackers = numDecommissionedNodes;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public int getRunningMaps()
/*  54:    */   {
/*  55:115 */     return this.runningMaps;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public int getRunningReduces()
/*  59:    */   {
/*  60:124 */     return this.runningReduces;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public int getOccupiedMapSlots()
/*  64:    */   {
/*  65:133 */     return this.occupiedMapSlots;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public int getOccupiedReduceSlots()
/*  69:    */   {
/*  70:142 */     return this.occupiedReduceSlots;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public int getReservedMapSlots()
/*  74:    */   {
/*  75:151 */     return this.reservedMapSlots;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public int getReservedReduceSlots()
/*  79:    */   {
/*  80:160 */     return this.reservedReduceSlots;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public int getMapSlotCapacity()
/*  84:    */   {
/*  85:169 */     return this.totalMapSlots;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public int getReduceSlotCapacity()
/*  89:    */   {
/*  90:178 */     return this.totalReduceSlots;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public int getTotalJobSubmissions()
/*  94:    */   {
/*  95:187 */     return this.totalJobSubmissions;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public int getTaskTrackerCount()
/*  99:    */   {
/* 100:196 */     return this.numTrackers;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public int getBlackListedTaskTrackerCount()
/* 104:    */   {
/* 105:205 */     return this.numBlacklistedTrackers;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public int getGrayListedTaskTrackerCount()
/* 109:    */   {
/* 110:214 */     return this.numGraylistedTrackers;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public int getDecommissionedTaskTrackerCount()
/* 114:    */   {
/* 115:223 */     return this.numDecommissionedTrackers;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public void readFields(DataInput in)
/* 119:    */     throws IOException
/* 120:    */   {
/* 121:228 */     this.runningMaps = in.readInt();
/* 122:229 */     this.runningReduces = in.readInt();
/* 123:230 */     this.occupiedMapSlots = in.readInt();
/* 124:231 */     this.occupiedReduceSlots = in.readInt();
/* 125:232 */     this.reservedMapSlots = in.readInt();
/* 126:233 */     this.reservedReduceSlots = in.readInt();
/* 127:234 */     this.totalMapSlots = in.readInt();
/* 128:235 */     this.totalReduceSlots = in.readInt();
/* 129:236 */     this.totalJobSubmissions = in.readInt();
/* 130:237 */     this.numTrackers = in.readInt();
/* 131:238 */     this.numBlacklistedTrackers = in.readInt();
/* 132:239 */     this.numGraylistedTrackers = in.readInt();
/* 133:240 */     this.numDecommissionedTrackers = in.readInt();
/* 134:    */   }
/* 135:    */   
/* 136:    */   public void write(DataOutput out)
/* 137:    */     throws IOException
/* 138:    */   {
/* 139:245 */     out.writeInt(this.runningMaps);
/* 140:246 */     out.writeInt(this.runningReduces);
/* 141:247 */     out.writeInt(this.occupiedMapSlots);
/* 142:248 */     out.writeInt(this.occupiedReduceSlots);
/* 143:249 */     out.writeInt(this.reservedMapSlots);
/* 144:250 */     out.writeInt(this.reservedReduceSlots);
/* 145:251 */     out.writeInt(this.totalMapSlots);
/* 146:252 */     out.writeInt(this.totalReduceSlots);
/* 147:253 */     out.writeInt(this.totalJobSubmissions);
/* 148:254 */     out.writeInt(this.numTrackers);
/* 149:255 */     out.writeInt(this.numBlacklistedTrackers);
/* 150:256 */     out.writeInt(this.numGraylistedTrackers);
/* 151:257 */     out.writeInt(this.numDecommissionedTrackers);
/* 152:    */   }
/* 153:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.ClusterMetrics
 * JD-Core Version:    0.7.0.1
 */