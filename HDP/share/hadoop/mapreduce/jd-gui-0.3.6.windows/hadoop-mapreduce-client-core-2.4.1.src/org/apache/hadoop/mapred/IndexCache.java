/*   1:    */ package org.apache.hadoop.mapred;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.util.concurrent.ConcurrentHashMap;
/*   5:    */ import java.util.concurrent.LinkedBlockingQueue;
/*   6:    */ import java.util.concurrent.atomic.AtomicInteger;
/*   7:    */ import org.apache.commons.logging.Log;
/*   8:    */ import org.apache.commons.logging.LogFactory;
/*   9:    */ import org.apache.hadoop.fs.Path;
/*  10:    */ 
/*  11:    */ class IndexCache
/*  12:    */ {
/*  13:    */   private final JobConf conf;
/*  14:    */   private final int totalMemoryAllowed;
/*  15: 34 */   private AtomicInteger totalMemoryUsed = new AtomicInteger();
/*  16: 35 */   private static final Log LOG = LogFactory.getLog(IndexCache.class);
/*  17: 37 */   private final ConcurrentHashMap<String, IndexInformation> cache = new ConcurrentHashMap();
/*  18: 40 */   private final LinkedBlockingQueue<String> queue = new LinkedBlockingQueue();
/*  19:    */   
/*  20:    */   public IndexCache(JobConf conf)
/*  21:    */   {
/*  22: 44 */     this.conf = conf;
/*  23: 45 */     this.totalMemoryAllowed = (conf.getInt("mapreduce.tasktracker.indexcache.mb", 10) * 1024 * 1024);
/*  24:    */     
/*  25: 47 */     LOG.info("IndexCache created with max memory = " + this.totalMemoryAllowed);
/*  26:    */   }
/*  27:    */   
/*  28:    */   public IndexRecord getIndexInformation(String mapId, int reduce, Path fileName, String expectedIndexOwner)
/*  29:    */     throws IOException
/*  30:    */   {
/*  31: 65 */     IndexInformation info = (IndexInformation)this.cache.get(mapId);
/*  32: 67 */     if (info == null)
/*  33:    */     {
/*  34: 68 */       info = readIndexFileToCache(fileName, mapId, expectedIndexOwner);
/*  35:    */     }
/*  36:    */     else
/*  37:    */     {
/*  38: 70 */       synchronized (info)
/*  39:    */       {
/*  40: 71 */         while (isUnderConstruction(info)) {
/*  41:    */           try
/*  42:    */           {
/*  43: 73 */             info.wait();
/*  44:    */           }
/*  45:    */           catch (InterruptedException e)
/*  46:    */           {
/*  47: 75 */             throw new IOException("Interrupted waiting for construction", e);
/*  48:    */           }
/*  49:    */         }
/*  50:    */       }
/*  51: 79 */       LOG.debug("IndexCache HIT: MapId " + mapId + " found");
/*  52:    */     }
/*  53: 82 */     if ((info.mapSpillRecord.size() == 0) || (info.mapSpillRecord.size() <= reduce)) {
/*  54: 84 */       throw new IOException("Invalid request  Map Id = " + mapId + " Reducer = " + reduce + " Index Info Length = " + info.mapSpillRecord.size());
/*  55:    */     }
/*  56: 88 */     return info.mapSpillRecord.getIndex(reduce);
/*  57:    */   }
/*  58:    */   
/*  59:    */   private boolean isUnderConstruction(IndexInformation info)
/*  60:    */   {
/*  61: 92 */     synchronized (info)
/*  62:    */     {
/*  63: 93 */       return null == info.mapSpillRecord;
/*  64:    */     }
/*  65:    */   }
/*  66:    */   
/*  67:    */   private IndexInformation readIndexFileToCache(Path indexFileName, String mapId, String expectedIndexOwner)
/*  68:    */     throws IOException
/*  69:    */   {
/*  70:102 */     IndexInformation newInd = new IndexInformation(null);
/*  71:    */     IndexInformation info;
/*  72:103 */     if ((info = (IndexInformation)this.cache.putIfAbsent(mapId, newInd)) != null)
/*  73:    */     {
/*  74:104 */       synchronized (info)
/*  75:    */       {
/*  76:105 */         while (isUnderConstruction(info)) {
/*  77:    */           try
/*  78:    */           {
/*  79:107 */             info.wait();
/*  80:    */           }
/*  81:    */           catch (InterruptedException e)
/*  82:    */           {
/*  83:109 */             throw new IOException("Interrupted waiting for construction", e);
/*  84:    */           }
/*  85:    */         }
/*  86:    */       }
/*  87:113 */       LOG.debug("IndexCache HIT: MapId " + mapId + " found");
/*  88:114 */       return info;
/*  89:    */     }
/*  90:116 */     LOG.debug("IndexCache MISS: MapId " + mapId + " not found");
/*  91:117 */     SpillRecord tmp = null;
/*  92:    */     try
/*  93:    */     {
/*  94:119 */       tmp = new SpillRecord(indexFileName, this.conf, expectedIndexOwner);
/*  95:    */     }
/*  96:    */     catch (Throwable e)
/*  97:    */     {
/*  98:121 */       tmp = new SpillRecord(0);
/*  99:122 */       this.cache.remove(mapId);
/* 100:123 */       throw new IOException("Error Reading IndexFile", e);
/* 101:    */     }
/* 102:    */     finally
/* 103:    */     {
/* 104:125 */       synchronized (newInd)
/* 105:    */       {
/* 106:126 */         newInd.mapSpillRecord = tmp;
/* 107:127 */         newInd.notifyAll();
/* 108:    */       }
/* 109:    */     }
/* 110:130 */     this.queue.add(mapId);
/* 111:132 */     if (this.totalMemoryUsed.addAndGet(newInd.getSize()) > this.totalMemoryAllowed) {
/* 112:133 */       freeIndexInformation();
/* 113:    */     }
/* 114:135 */     return newInd;
/* 115:    */   }
/* 116:    */   
/* 117:    */   public void removeMap(String mapId)
/* 118:    */   {
/* 119:147 */     IndexInformation info = (IndexInformation)this.cache.get(mapId);
/* 120:148 */     if ((info == null) || ((info != null) && (isUnderConstruction(info)))) {
/* 121:149 */       return;
/* 122:    */     }
/* 123:151 */     info = (IndexInformation)this.cache.remove(mapId);
/* 124:152 */     if (info != null)
/* 125:    */     {
/* 126:153 */       this.totalMemoryUsed.addAndGet(-info.getSize());
/* 127:154 */       if (!this.queue.remove(mapId)) {
/* 128:155 */         LOG.warn("Map ID" + mapId + " not found in queue!!");
/* 129:    */       }
/* 130:    */     }
/* 131:    */     else
/* 132:    */     {
/* 133:158 */       LOG.info("Map ID " + mapId + " not found in cache");
/* 134:    */     }
/* 135:    */   }
/* 136:    */   
/* 137:    */   boolean checkTotalMemoryUsed()
/* 138:    */   {
/* 139:168 */     int totalSize = 0;
/* 140:169 */     for (IndexInformation info : this.cache.values()) {
/* 141:170 */       totalSize += info.getSize();
/* 142:    */     }
/* 143:172 */     return totalSize == this.totalMemoryUsed.get();
/* 144:    */   }
/* 145:    */   
/* 146:    */   private synchronized void freeIndexInformation()
/* 147:    */   {
/* 148:179 */     while (this.totalMemoryUsed.get() > this.totalMemoryAllowed)
/* 149:    */     {
/* 150:180 */       String s = (String)this.queue.remove();
/* 151:181 */       IndexInformation info = (IndexInformation)this.cache.remove(s);
/* 152:182 */       if (info != null) {
/* 153:183 */         this.totalMemoryUsed.addAndGet(-info.getSize());
/* 154:    */       }
/* 155:    */     }
/* 156:    */   }
/* 157:    */   
/* 158:    */   private static class IndexInformation
/* 159:    */   {
/* 160:    */     SpillRecord mapSpillRecord;
/* 161:    */     
/* 162:    */     int getSize()
/* 163:    */     {
/* 164:192 */       return this.mapSpillRecord == null ? 0 : this.mapSpillRecord.size() * 24;
/* 165:    */     }
/* 166:    */   }
/* 167:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.IndexCache
 * JD-Core Version:    0.7.0.1
 */