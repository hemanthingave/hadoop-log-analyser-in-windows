/*   1:    */ package org.apache.hadoop.mapreduce.jobhistory;
/*   2:    */ 
/*   3:    */ import org.apache.avro.util.Utf8;
/*   4:    */ import org.apache.hadoop.classification.InterfaceAudience.Private;
/*   5:    */ import org.apache.hadoop.classification.InterfaceStability.Unstable;
/*   6:    */ import org.apache.hadoop.mapreduce.Counters;
/*   7:    */ import org.apache.hadoop.mapreduce.JobID;
/*   8:    */ 
/*   9:    */ @InterfaceAudience.Private
/*  10:    */ @InterfaceStability.Unstable
/*  11:    */ public class JobFinishedEvent
/*  12:    */   implements HistoryEvent
/*  13:    */ {
/*  14: 35 */   private JobFinished datum = null;
/*  15:    */   private JobID jobId;
/*  16:    */   private long finishTime;
/*  17:    */   private int finishedMaps;
/*  18:    */   private int finishedReduces;
/*  19:    */   private int failedMaps;
/*  20:    */   private int failedReduces;
/*  21:    */   private Counters mapCounters;
/*  22:    */   private Counters reduceCounters;
/*  23:    */   private Counters totalCounters;
/*  24:    */   
/*  25:    */   public JobFinishedEvent(JobID id, long finishTime, int finishedMaps, int finishedReduces, int failedMaps, int failedReduces, Counters mapCounters, Counters reduceCounters, Counters totalCounters)
/*  26:    */   {
/*  27: 64 */     this.jobId = id;
/*  28: 65 */     this.finishTime = finishTime;
/*  29: 66 */     this.finishedMaps = finishedMaps;
/*  30: 67 */     this.finishedReduces = finishedReduces;
/*  31: 68 */     this.failedMaps = failedMaps;
/*  32: 69 */     this.failedReduces = failedReduces;
/*  33: 70 */     this.mapCounters = mapCounters;
/*  34: 71 */     this.reduceCounters = reduceCounters;
/*  35: 72 */     this.totalCounters = totalCounters;
/*  36:    */   }
/*  37:    */   
/*  38:    */   JobFinishedEvent() {}
/*  39:    */   
/*  40:    */   public Object getDatum()
/*  41:    */   {
/*  42: 78 */     if (this.datum == null)
/*  43:    */     {
/*  44: 79 */       this.datum = new JobFinished();
/*  45: 80 */       this.datum.jobid = new Utf8(this.jobId.toString());
/*  46: 81 */       this.datum.finishTime = this.finishTime;
/*  47: 82 */       this.datum.finishedMaps = this.finishedMaps;
/*  48: 83 */       this.datum.finishedReduces = this.finishedReduces;
/*  49: 84 */       this.datum.failedMaps = this.failedMaps;
/*  50: 85 */       this.datum.failedReduces = this.failedReduces;
/*  51: 86 */       this.datum.mapCounters = EventWriter.toAvro(this.mapCounters, "MAP_COUNTERS");
/*  52: 87 */       this.datum.reduceCounters = EventWriter.toAvro(this.reduceCounters, "REDUCE_COUNTERS");
/*  53:    */       
/*  54: 89 */       this.datum.totalCounters = EventWriter.toAvro(this.totalCounters, "TOTAL_COUNTERS");
/*  55:    */     }
/*  56: 91 */     return this.datum;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public void setDatum(Object oDatum)
/*  60:    */   {
/*  61: 95 */     this.datum = ((JobFinished)oDatum);
/*  62: 96 */     this.jobId = JobID.forName(this.datum.jobid.toString());
/*  63: 97 */     this.finishTime = this.datum.finishTime;
/*  64: 98 */     this.finishedMaps = this.datum.finishedMaps;
/*  65: 99 */     this.finishedReduces = this.datum.finishedReduces;
/*  66:100 */     this.failedMaps = this.datum.failedMaps;
/*  67:101 */     this.failedReduces = this.datum.failedReduces;
/*  68:102 */     this.mapCounters = EventReader.fromAvro(this.datum.mapCounters);
/*  69:103 */     this.reduceCounters = EventReader.fromAvro(this.datum.reduceCounters);
/*  70:104 */     this.totalCounters = EventReader.fromAvro(this.datum.totalCounters);
/*  71:    */   }
/*  72:    */   
/*  73:    */   public EventType getEventType()
/*  74:    */   {
/*  75:108 */     return EventType.JOB_FINISHED;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public JobID getJobid()
/*  79:    */   {
/*  80:112 */     return this.jobId;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public long getFinishTime()
/*  84:    */   {
/*  85:114 */     return this.finishTime;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public int getFinishedMaps()
/*  89:    */   {
/*  90:116 */     return this.finishedMaps;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public int getFinishedReduces()
/*  94:    */   {
/*  95:118 */     return this.finishedReduces;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public int getFailedMaps()
/*  99:    */   {
/* 100:120 */     return this.failedMaps;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public int getFailedReduces()
/* 104:    */   {
/* 105:122 */     return this.failedReduces;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public Counters getTotalCounters()
/* 109:    */   {
/* 110:125 */     return this.totalCounters;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public Counters getMapCounters()
/* 114:    */   {
/* 115:129 */     return this.mapCounters;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public Counters getReduceCounters()
/* 119:    */   {
/* 120:133 */     return this.reduceCounters;
/* 121:    */   }
/* 122:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.jobhistory.JobFinishedEvent
 * JD-Core Version:    0.7.0.1
 */