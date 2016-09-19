/*   1:    */ package org.apache.hadoop.mapred;
/*   2:    */ 
/*   3:    */ import java.io.File;
/*   4:    */ import java.io.Flushable;
/*   5:    */ import java.util.LinkedList;
/*   6:    */ import java.util.Queue;
/*   7:    */ import org.apache.hadoop.classification.InterfaceStability.Unstable;
/*   8:    */ import org.apache.log4j.FileAppender;
/*   9:    */ import org.apache.log4j.helpers.QuietWriter;
/*  10:    */ import org.apache.log4j.spi.LoggingEvent;
/*  11:    */ 
/*  12:    */ @InterfaceStability.Unstable
/*  13:    */ public class TaskLogAppender
/*  14:    */   extends FileAppender
/*  15:    */   implements Flushable
/*  16:    */ {
/*  17:    */   private String taskId;
/*  18:    */   private Integer maxEvents;
/*  19: 39 */   private Queue<LoggingEvent> tail = null;
/*  20:    */   private Boolean isCleanup;
/*  21:    */   static final String ISCLEANUP_PROPERTY = "hadoop.tasklog.iscleanup";
/*  22:    */   static final String LOGSIZE_PROPERTY = "hadoop.tasklog.totalLogFileSize";
/*  23:    */   static final String TASKID_PROPERTY = "hadoop.tasklog.taskid";
/*  24:    */   private static final int EVENT_SIZE = 100;
/*  25:    */   
/*  26:    */   public void activateOptions()
/*  27:    */   {
/*  28: 49 */     synchronized (this)
/*  29:    */     {
/*  30: 50 */       setOptionsFromSystemProperties();
/*  31: 52 */       if (this.maxEvents.intValue() > 0) {
/*  32: 53 */         this.tail = new LinkedList();
/*  33:    */       }
/*  34: 55 */       setFile(TaskLog.getTaskLogFile(TaskAttemptID.forName(this.taskId), this.isCleanup.booleanValue(), TaskLog.LogName.SYSLOG).toString());
/*  35:    */       
/*  36: 57 */       setAppend(true);
/*  37: 58 */       super.activateOptions();
/*  38:    */     }
/*  39:    */   }
/*  40:    */   
/*  41:    */   private synchronized void setOptionsFromSystemProperties()
/*  42:    */   {
/*  43: 67 */     if (this.isCleanup == null)
/*  44:    */     {
/*  45: 68 */       String propValue = System.getProperty("hadoop.tasklog.iscleanup", "false");
/*  46: 69 */       this.isCleanup = Boolean.valueOf(propValue);
/*  47:    */     }
/*  48: 72 */     if (this.taskId == null) {
/*  49: 73 */       this.taskId = System.getProperty("hadoop.tasklog.taskid");
/*  50:    */     }
/*  51: 76 */     if (this.maxEvents == null)
/*  52:    */     {
/*  53: 77 */       String propValue = System.getProperty("hadoop.tasklog.totalLogFileSize", "0");
/*  54: 78 */       setTotalLogFileSize(Long.valueOf(propValue).longValue());
/*  55:    */     }
/*  56:    */   }
/*  57:    */   
/*  58:    */   public void append(LoggingEvent event)
/*  59:    */   {
/*  60: 84 */     synchronized (this)
/*  61:    */     {
/*  62: 85 */       if (this.tail == null)
/*  63:    */       {
/*  64: 86 */         super.append(event);
/*  65:    */       }
/*  66:    */       else
/*  67:    */       {
/*  68: 88 */         if (this.tail.size() >= this.maxEvents.intValue()) {
/*  69: 89 */           this.tail.remove();
/*  70:    */         }
/*  71: 91 */         this.tail.add(event);
/*  72:    */       }
/*  73:    */     }
/*  74:    */   }
/*  75:    */   
/*  76:    */   public void flush()
/*  77:    */   {
/*  78: 98 */     if (this.qw != null) {
/*  79: 99 */       this.qw.flush();
/*  80:    */     }
/*  81:    */   }
/*  82:    */   
/*  83:    */   public synchronized void close()
/*  84:    */   {
/*  85:105 */     if (this.tail != null) {
/*  86:106 */       for (LoggingEvent event : this.tail) {
/*  87:107 */         super.append(event);
/*  88:    */       }
/*  89:    */     }
/*  90:110 */     super.close();
/*  91:    */   }
/*  92:    */   
/*  93:    */   public synchronized String getTaskId()
/*  94:    */   {
/*  95:118 */     return this.taskId;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public synchronized void setTaskId(String taskId)
/*  99:    */   {
/* 100:122 */     this.taskId = taskId;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public synchronized long getTotalLogFileSize()
/* 104:    */   {
/* 105:128 */     return this.maxEvents.intValue() * 100;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public synchronized void setTotalLogFileSize(long logSize)
/* 109:    */   {
/* 110:132 */     this.maxEvents = Integer.valueOf((int)logSize / 100);
/* 111:    */   }
/* 112:    */   
/* 113:    */   public synchronized void setIsCleanup(boolean isCleanup)
/* 114:    */   {
/* 115:142 */     this.isCleanup = Boolean.valueOf(isCleanup);
/* 116:    */   }
/* 117:    */   
/* 118:    */   public synchronized boolean getIsCleanup()
/* 119:    */   {
/* 120:151 */     return this.isCleanup.booleanValue();
/* 121:    */   }
/* 122:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.TaskLogAppender
 * JD-Core Version:    0.7.0.1
 */