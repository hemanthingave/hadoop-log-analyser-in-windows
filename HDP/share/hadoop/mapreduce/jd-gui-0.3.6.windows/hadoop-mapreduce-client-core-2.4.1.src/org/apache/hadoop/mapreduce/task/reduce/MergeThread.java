/*   1:    */ package org.apache.hadoop.mapreduce.task.reduce;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import java.util.Iterator;
/*   6:    */ import java.util.LinkedList;
/*   7:    */ import java.util.List;
/*   8:    */ import java.util.Set;
/*   9:    */ import java.util.concurrent.atomic.AtomicInteger;
/*  10:    */ import org.apache.commons.logging.Log;
/*  11:    */ import org.apache.commons.logging.LogFactory;
/*  12:    */ 
/*  13:    */ abstract class MergeThread<T, K, V>
/*  14:    */   extends Thread
/*  15:    */ {
/*  16: 33 */   private static final Log LOG = LogFactory.getLog(MergeThread.class);
/*  17: 35 */   private AtomicInteger numPending = new AtomicInteger(0);
/*  18:    */   private LinkedList<List<T>> pendingToBeMerged;
/*  19:    */   protected final MergeManagerImpl<K, V> manager;
/*  20:    */   private final ExceptionReporter reporter;
/*  21: 39 */   private boolean closed = false;
/*  22:    */   private final int mergeFactor;
/*  23:    */   
/*  24:    */   public MergeThread(MergeManagerImpl<K, V> manager, int mergeFactor, ExceptionReporter reporter)
/*  25:    */   {
/*  26: 44 */     this.pendingToBeMerged = new LinkedList();
/*  27: 45 */     this.manager = manager;
/*  28: 46 */     this.mergeFactor = mergeFactor;
/*  29: 47 */     this.reporter = reporter;
/*  30:    */   }
/*  31:    */   
/*  32:    */   public synchronized void close()
/*  33:    */     throws InterruptedException
/*  34:    */   {
/*  35: 51 */     this.closed = true;
/*  36: 52 */     waitForMerge();
/*  37: 53 */     interrupt();
/*  38:    */   }
/*  39:    */   
/*  40:    */   public void startMerge(Set<T> inputs)
/*  41:    */   {
/*  42: 57 */     if (!this.closed)
/*  43:    */     {
/*  44: 58 */       this.numPending.incrementAndGet();
/*  45: 59 */       List<T> toMergeInputs = new ArrayList();
/*  46: 60 */       Iterator<T> iter = inputs.iterator();
/*  47: 61 */       for (int ctr = 0; (iter.hasNext()) && (ctr < this.mergeFactor); ctr++)
/*  48:    */       {
/*  49: 62 */         toMergeInputs.add(iter.next());
/*  50: 63 */         iter.remove();
/*  51:    */       }
/*  52: 65 */       LOG.info(getName() + ": Starting merge with " + toMergeInputs.size() + " segments, while ignoring " + inputs.size() + " segments");
/*  53: 67 */       synchronized (this.pendingToBeMerged)
/*  54:    */       {
/*  55: 68 */         this.pendingToBeMerged.addLast(toMergeInputs);
/*  56: 69 */         this.pendingToBeMerged.notifyAll();
/*  57:    */       }
/*  58:    */     }
/*  59:    */   }
/*  60:    */   
/*  61:    */   public synchronized void waitForMerge()
/*  62:    */     throws InterruptedException
/*  63:    */   {
/*  64: 75 */     while (this.numPending.get() > 0) {
/*  65: 76 */       wait();
/*  66:    */     }
/*  67:    */   }
/*  68:    */   
/*  69:    */   public void run()
/*  70:    */   {
/*  71:    */     for (;;)
/*  72:    */     {
/*  73: 82 */       List<T> inputs = null;
/*  74:    */       try
/*  75:    */       {
/*  76: 85 */         synchronized (this.pendingToBeMerged)
/*  77:    */         {
/*  78: 86 */           while (this.pendingToBeMerged.size() <= 0) {
/*  79: 87 */             this.pendingToBeMerged.wait();
/*  80:    */           }
/*  81: 90 */           inputs = (List)this.pendingToBeMerged.removeFirst();
/*  82:    */         }
/*  83: 94 */         merge(inputs);
/*  84:    */       }
/*  85:    */       catch (InterruptedException ie)
/*  86:    */       {
/*  87: 96 */         this.numPending.set(0); return;
/*  88:    */       }
/*  89:    */       catch (Throwable t)
/*  90:    */       {
/*  91: 99 */         this.numPending.set(0);
/*  92:100 */         this.reporter.reportException(t); return;
/*  93:    */       }
/*  94:    */       finally
/*  95:    */       {
/*  96:103 */         synchronized (this)
/*  97:    */         {
/*  98:104 */           this.numPending.decrementAndGet();
/*  99:105 */           notifyAll();
/* 100:    */         }
/* 101:    */       }
/* 102:    */     }
/* 103:    */   }
/* 104:    */   
/* 105:    */   public abstract void merge(List<T> paramList)
/* 106:    */     throws IOException;
/* 107:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.task.reduce.MergeThread
 * JD-Core Version:    0.7.0.1
 */