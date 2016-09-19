/*   1:    */ package org.apache.hadoop.mapreduce.counters;
/*   2:    */ 
/*   3:    */ import org.apache.hadoop.classification.InterfaceAudience.Private;
/*   4:    */ import org.apache.hadoop.conf.Configuration;
/*   5:    */ import org.apache.hadoop.mapred.JobConf;
/*   6:    */ 
/*   7:    */ @InterfaceAudience.Private
/*   8:    */ public class Limits
/*   9:    */ {
/*  10: 30 */   static final Configuration conf = new JobConf();
/*  11:    */   private int totalCounters;
/*  12:    */   private LimitExceededException firstViolation;
/*  13:    */   private static boolean isInited;
/*  14:    */   private static int GROUP_NAME_MAX;
/*  15:    */   private static int COUNTER_NAME_MAX;
/*  16:    */   private static int GROUPS_MAX;
/*  17:    */   private static int COUNTERS_MAX;
/*  18:    */   
/*  19:    */   public static synchronized void init(Configuration conf)
/*  20:    */   {
/*  21: 43 */     if (!isInited)
/*  22:    */     {
/*  23: 44 */       if (conf == null) {
/*  24: 45 */         conf = new JobConf();
/*  25:    */       }
/*  26: 47 */       GROUP_NAME_MAX = conf.getInt("mapreduce.job.counters.group.name.max", 128);
/*  27:    */       
/*  28: 49 */       COUNTER_NAME_MAX = conf.getInt("mapreduce.job.counters.counter.name.max", 64);
/*  29:    */       
/*  30: 51 */       GROUPS_MAX = conf.getInt("mapreduce.job.counters.groups.max", 50);
/*  31: 52 */       COUNTERS_MAX = conf.getInt("mapreduce.job.counters.max", 120);
/*  32:    */     }
/*  33: 54 */     isInited = true;
/*  34:    */   }
/*  35:    */   
/*  36:    */   public static int getGroupNameMax()
/*  37:    */   {
/*  38: 58 */     if (!isInited) {
/*  39: 59 */       init(null);
/*  40:    */     }
/*  41: 61 */     return GROUP_NAME_MAX;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public static int getCounterNameMax()
/*  45:    */   {
/*  46: 65 */     if (!isInited) {
/*  47: 66 */       init(null);
/*  48:    */     }
/*  49: 68 */     return COUNTER_NAME_MAX;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public static int getGroupsMax()
/*  53:    */   {
/*  54: 72 */     if (!isInited) {
/*  55: 73 */       init(null);
/*  56:    */     }
/*  57: 75 */     return GROUPS_MAX;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public static int getCountersMax()
/*  61:    */   {
/*  62: 79 */     if (!isInited) {
/*  63: 80 */       init(null);
/*  64:    */     }
/*  65: 82 */     return COUNTERS_MAX;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public static String filterName(String name, int maxLen)
/*  69:    */   {
/*  70: 86 */     return name.length() > maxLen ? name.substring(0, maxLen - 1) : name;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public static String filterCounterName(String name)
/*  74:    */   {
/*  75: 90 */     return filterName(name, getCounterNameMax());
/*  76:    */   }
/*  77:    */   
/*  78:    */   public static String filterGroupName(String name)
/*  79:    */   {
/*  80: 94 */     return filterName(name, getGroupNameMax());
/*  81:    */   }
/*  82:    */   
/*  83:    */   public synchronized void checkCounters(int size)
/*  84:    */   {
/*  85: 98 */     if (this.firstViolation != null) {
/*  86: 99 */       throw new LimitExceededException(this.firstViolation);
/*  87:    */     }
/*  88:101 */     int countersMax = getCountersMax();
/*  89:102 */     if (size > countersMax)
/*  90:    */     {
/*  91:103 */       this.firstViolation = new LimitExceededException("Too many counters: " + size + " max=" + countersMax);
/*  92:    */       
/*  93:105 */       throw this.firstViolation;
/*  94:    */     }
/*  95:    */   }
/*  96:    */   
/*  97:    */   public synchronized void incrCounters()
/*  98:    */   {
/*  99:110 */     checkCounters(this.totalCounters + 1);
/* 100:111 */     this.totalCounters += 1;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public synchronized void checkGroups(int size)
/* 104:    */   {
/* 105:115 */     if (this.firstViolation != null) {
/* 106:116 */       throw new LimitExceededException(this.firstViolation);
/* 107:    */     }
/* 108:118 */     int groupsMax = getGroupsMax();
/* 109:119 */     if (size > groupsMax) {
/* 110:120 */       this.firstViolation = new LimitExceededException("Too many counter groups: " + size + " max=" + groupsMax);
/* 111:    */     }
/* 112:    */   }
/* 113:    */   
/* 114:    */   public synchronized LimitExceededException violation()
/* 115:    */   {
/* 116:126 */     return this.firstViolation;
/* 117:    */   }
/* 118:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.counters.Limits
 * JD-Core Version:    0.7.0.1
 */