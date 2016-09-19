/*   1:    */ package org.apache.hadoop.mapreduce.task.reduce;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import org.apache.commons.logging.Log;
/*   5:    */ import org.apache.commons.logging.LogFactory;
/*   6:    */ import org.apache.hadoop.mapred.JobID;
/*   7:    */ import org.apache.hadoop.mapred.MapTaskCompletionEventsUpdate;
/*   8:    */ import org.apache.hadoop.mapred.TaskCompletionEvent;
/*   9:    */ import org.apache.hadoop.mapred.TaskCompletionEvent.Status;
/*  10:    */ import org.apache.hadoop.mapred.TaskUmbilicalProtocol;
/*  11:    */ 
/*  12:    */ class EventFetcher<K, V>
/*  13:    */   extends Thread
/*  14:    */ {
/*  15:    */   private static final long SLEEP_TIME = 1000L;
/*  16:    */   private static final int MAX_RETRIES = 10;
/*  17:    */   private static final int RETRY_PERIOD = 5000;
/*  18: 33 */   private static final Log LOG = LogFactory.getLog(EventFetcher.class);
/*  19:    */   private final org.apache.hadoop.mapreduce.TaskAttemptID reduce;
/*  20:    */   private final TaskUmbilicalProtocol umbilical;
/*  21:    */   private final ShuffleScheduler<K, V> scheduler;
/*  22: 38 */   private int fromEventIdx = 0;
/*  23:    */   private final int maxEventsToFetch;
/*  24:    */   private final ExceptionReporter exceptionReporter;
/*  25: 42 */   private volatile boolean stopped = false;
/*  26:    */   
/*  27:    */   public EventFetcher(org.apache.hadoop.mapreduce.TaskAttemptID reduce, TaskUmbilicalProtocol umbilical, ShuffleScheduler<K, V> scheduler, ExceptionReporter reporter, int maxEventsToFetch)
/*  28:    */   {
/*  29: 49 */     setName("EventFetcher for fetching Map Completion Events");
/*  30: 50 */     setDaemon(true);
/*  31: 51 */     this.reduce = reduce;
/*  32: 52 */     this.umbilical = umbilical;
/*  33: 53 */     this.scheduler = scheduler;
/*  34: 54 */     this.exceptionReporter = reporter;
/*  35: 55 */     this.maxEventsToFetch = maxEventsToFetch;
/*  36:    */   }
/*  37:    */   
/*  38:    */   public void run()
/*  39:    */   {
/*  40: 60 */     int failures = 0;
/*  41: 61 */     LOG.info(this.reduce + " Thread started: " + getName());
/*  42:    */     try
/*  43:    */     {
/*  44: 64 */       while ((!this.stopped) && (!Thread.currentThread().isInterrupted())) {
/*  45:    */         try
/*  46:    */         {
/*  47: 66 */           int numNewMaps = getMapCompletionEvents();
/*  48: 67 */           failures = 0;
/*  49: 68 */           if (numNewMaps > 0) {
/*  50: 69 */             LOG.info(this.reduce + ": " + "Got " + numNewMaps + " new map-outputs");
/*  51:    */           }
/*  52: 71 */           LOG.debug("GetMapEventsThread about to sleep for 1000");
/*  53: 72 */           if (!Thread.currentThread().isInterrupted()) {
/*  54: 73 */             Thread.sleep(1000L);
/*  55:    */           }
/*  56:    */         }
/*  57:    */         catch (InterruptedException e)
/*  58:    */         {
/*  59: 76 */           LOG.info("EventFetcher is interrupted.. Returning");
/*  60: 77 */           return;
/*  61:    */         }
/*  62:    */         catch (IOException ie)
/*  63:    */         {
/*  64: 79 */           LOG.info("Exception in getting events", ie);
/*  65:    */           
/*  66: 81 */           failures++;
/*  67: 81 */           if (failures >= 10) {
/*  68: 82 */             throw new IOException("too many failures downloading events", ie);
/*  69:    */           }
/*  70: 85 */           if (!Thread.currentThread().isInterrupted()) {
/*  71: 86 */             Thread.sleep(5000L);
/*  72:    */           }
/*  73:    */         }
/*  74:    */       }
/*  75:    */     }
/*  76:    */     catch (InterruptedException e) {}catch (Throwable t)
/*  77:    */     {
/*  78: 93 */       this.exceptionReporter.reportException(t);
/*  79: 94 */       return;
/*  80:    */     }
/*  81:    */   }
/*  82:    */   
/*  83:    */   public void shutDown()
/*  84:    */   {
/*  85: 99 */     this.stopped = true;
/*  86:100 */     interrupt();
/*  87:    */     try
/*  88:    */     {
/*  89:102 */       join(5000L);
/*  90:    */     }
/*  91:    */     catch (InterruptedException ie)
/*  92:    */     {
/*  93:104 */       LOG.warn("Got interrupted while joining " + getName(), ie);
/*  94:    */     }
/*  95:    */   }
/*  96:    */   
/*  97:    */   protected int getMapCompletionEvents()
/*  98:    */     throws IOException, InterruptedException
/*  99:    */   {
/* 100:116 */     int numNewMaps = 0;
/* 101:117 */     TaskCompletionEvent[] events = null;
/* 102:    */     do
/* 103:    */     {
/* 104:120 */       MapTaskCompletionEventsUpdate update = this.umbilical.getMapCompletionEvents((JobID)this.reduce.getJobID(), this.fromEventIdx, this.maxEventsToFetch, (org.apache.hadoop.mapred.TaskAttemptID)this.reduce);
/* 105:    */       
/* 106:    */ 
/* 107:    */ 
/* 108:    */ 
/* 109:    */ 
/* 110:126 */       events = update.getMapTaskCompletionEvents();
/* 111:127 */       LOG.debug("Got " + events.length + " map completion events from " + this.fromEventIdx);
/* 112:    */       
/* 113:    */ 
/* 114:130 */       assert (!update.shouldReset()) : "Unexpected legacy state";
/* 115:    */       
/* 116:    */ 
/* 117:133 */       this.fromEventIdx += events.length;
/* 118:141 */       for (TaskCompletionEvent event : events)
/* 119:    */       {
/* 120:142 */         this.scheduler.resolve(event);
/* 121:143 */         if (TaskCompletionEvent.Status.SUCCEEDED == event.getTaskStatus()) {
/* 122:144 */           numNewMaps++;
/* 123:    */         }
/* 124:    */       }
/* 125:147 */     } while (events.length == this.maxEventsToFetch);
/* 126:149 */     return numNewMaps;
/* 127:    */   }
/* 128:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.task.reduce.EventFetcher
 * JD-Core Version:    0.7.0.1
 */