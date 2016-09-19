/*   1:    */ package org.apache.hadoop.mapred;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.util.concurrent.LinkedBlockingQueue;
/*   5:    */ import org.apache.commons.logging.Log;
/*   6:    */ import org.apache.commons.logging.LogFactory;
/*   7:    */ import org.apache.hadoop.fs.FileSystem;
/*   8:    */ import org.apache.hadoop.fs.Path;
/*   9:    */ 
/*  10:    */ class CleanupQueue
/*  11:    */ {
/*  12: 32 */   public static final Log LOG = LogFactory.getLog(CleanupQueue.class);
/*  13:    */   private static PathCleanupThread cleanupThread;
/*  14:    */   
/*  15:    */   public CleanupQueue()
/*  16:    */   {
/*  17: 46 */     synchronized (PathCleanupThread.class)
/*  18:    */     {
/*  19: 47 */       if (cleanupThread == null) {
/*  20: 48 */         cleanupThread = new PathCleanupThread();
/*  21:    */       }
/*  22:    */     }
/*  23:    */   }
/*  24:    */   
/*  25:    */   static class PathDeletionContext
/*  26:    */   {
/*  27:    */     String fullPath;
/*  28:    */     FileSystem fs;
/*  29:    */     
/*  30:    */     public PathDeletionContext(FileSystem fs, String fullPath)
/*  31:    */     {
/*  32: 61 */       this.fs = fs;
/*  33: 62 */       this.fullPath = fullPath;
/*  34:    */     }
/*  35:    */     
/*  36:    */     protected String getPathForCleanup()
/*  37:    */     {
/*  38: 66 */       return this.fullPath;
/*  39:    */     }
/*  40:    */     
/*  41:    */     protected void enablePathForCleanup()
/*  42:    */       throws IOException
/*  43:    */     {}
/*  44:    */   }
/*  45:    */   
/*  46:    */   void addToQueue(PathDeletionContext... contexts)
/*  47:    */   {
/*  48: 82 */     cleanupThread.addToQueue(contexts);
/*  49:    */   }
/*  50:    */   
/*  51:    */   protected static boolean deletePath(PathDeletionContext context)
/*  52:    */     throws IOException
/*  53:    */   {
/*  54: 87 */     context.enablePathForCleanup();
/*  55: 89 */     if (LOG.isDebugEnabled()) {
/*  56: 90 */       LOG.debug("Trying to delete " + context.fullPath);
/*  57:    */     }
/*  58: 92 */     if (context.fs.exists(new Path(context.fullPath))) {
/*  59: 93 */       return context.fs.delete(new Path(context.fullPath), true);
/*  60:    */     }
/*  61: 95 */     return true;
/*  62:    */   }
/*  63:    */   
/*  64:    */   protected boolean isQueueEmpty()
/*  65:    */   {
/*  66:100 */     return cleanupThread.queue.size() == 0;
/*  67:    */   }
/*  68:    */   
/*  69:    */   private static class PathCleanupThread
/*  70:    */     extends Thread
/*  71:    */   {
/*  72:106 */     private LinkedBlockingQueue<CleanupQueue.PathDeletionContext> queue = new LinkedBlockingQueue();
/*  73:    */     
/*  74:    */     public PathCleanupThread()
/*  75:    */     {
/*  76:110 */       setName("Directory/File cleanup thread");
/*  77:111 */       setDaemon(true);
/*  78:112 */       start();
/*  79:    */     }
/*  80:    */     
/*  81:    */     void addToQueue(CleanupQueue.PathDeletionContext[] contexts)
/*  82:    */     {
/*  83:116 */       for (CleanupQueue.PathDeletionContext context : contexts) {
/*  84:    */         try
/*  85:    */         {
/*  86:118 */           this.queue.put(context);
/*  87:    */         }
/*  88:    */         catch (InterruptedException ie) {}
/*  89:    */       }
/*  90:    */     }
/*  91:    */     
/*  92:    */     public void run()
/*  93:    */     {
/*  94:124 */       if (CleanupQueue.LOG.isDebugEnabled()) {
/*  95:125 */         CleanupQueue.LOG.debug(getName() + " started.");
/*  96:    */       }
/*  97:127 */       CleanupQueue.PathDeletionContext context = null;
/*  98:    */       try
/*  99:    */       {
/* 100:    */         for (;;)
/* 101:    */         {
/* 102:130 */           context = (CleanupQueue.PathDeletionContext)this.queue.take();
/* 103:132 */           if (!CleanupQueue.deletePath(context)) {
/* 104:133 */             CleanupQueue.LOG.warn("CleanupThread:Unable to delete path " + context.fullPath);
/* 105:135 */           } else if (CleanupQueue.LOG.isDebugEnabled()) {
/* 106:136 */             CleanupQueue.LOG.debug("DELETED " + context.fullPath);
/* 107:    */           }
/* 108:    */         }
/* 109:    */       }
/* 110:    */       catch (InterruptedException t)
/* 111:    */       {
/* 112:139 */         CleanupQueue.LOG.warn("Interrupted deletion of " + context.fullPath);
/* 113:140 */         return;
/* 114:    */       }
/* 115:    */       catch (Exception e)
/* 116:    */       {
/* 117:142 */         CleanupQueue.LOG.warn("Error deleting path " + context.fullPath + ": " + e);
/* 118:    */       }
/* 119:    */     }
/* 120:    */   }
/* 121:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.CleanupQueue
 * JD-Core Version:    0.7.0.1
 */