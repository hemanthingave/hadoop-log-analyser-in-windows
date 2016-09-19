/*   1:    */ package org.apache.hadoop.mapreduce.task.reduce;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.List;
/*   5:    */ import org.apache.hadoop.classification.InterfaceAudience.LimitedPrivate;
/*   6:    */ import org.apache.hadoop.classification.InterfaceStability.Unstable;
/*   7:    */ import org.apache.hadoop.mapreduce.TaskAttemptID;
/*   8:    */ 
/*   9:    */ @InterfaceAudience.LimitedPrivate({"MapReduce"})
/*  10:    */ @InterfaceStability.Unstable
/*  11:    */ public class MapHost
/*  12:    */ {
/*  13:    */   public static enum State
/*  14:    */   {
/*  15: 33 */     IDLE,  BUSY,  PENDING,  PENALIZED;
/*  16:    */     
/*  17:    */     private State() {}
/*  18:    */   }
/*  19:    */   
/*  20: 39 */   private State state = State.IDLE;
/*  21:    */   private final String hostName;
/*  22:    */   private final String baseUrl;
/*  23: 42 */   private List<TaskAttemptID> maps = new ArrayList();
/*  24:    */   
/*  25:    */   public MapHost(String hostName, String baseUrl)
/*  26:    */   {
/*  27: 45 */     this.hostName = hostName;
/*  28: 46 */     this.baseUrl = baseUrl;
/*  29:    */   }
/*  30:    */   
/*  31:    */   public State getState()
/*  32:    */   {
/*  33: 50 */     return this.state;
/*  34:    */   }
/*  35:    */   
/*  36:    */   public String getHostName()
/*  37:    */   {
/*  38: 54 */     return this.hostName;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public String getBaseUrl()
/*  42:    */   {
/*  43: 58 */     return this.baseUrl;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public synchronized void addKnownMap(TaskAttemptID mapId)
/*  47:    */   {
/*  48: 62 */     this.maps.add(mapId);
/*  49: 63 */     if (this.state == State.IDLE) {
/*  50: 64 */       this.state = State.PENDING;
/*  51:    */     }
/*  52:    */   }
/*  53:    */   
/*  54:    */   public synchronized List<TaskAttemptID> getAndClearKnownMaps()
/*  55:    */   {
/*  56: 69 */     List<TaskAttemptID> currentKnownMaps = this.maps;
/*  57: 70 */     this.maps = new ArrayList();
/*  58: 71 */     return currentKnownMaps;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public synchronized void markBusy()
/*  62:    */   {
/*  63: 75 */     this.state = State.BUSY;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public synchronized void markPenalized()
/*  67:    */   {
/*  68: 79 */     this.state = State.PENALIZED;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public synchronized int getNumKnownMapOutputs()
/*  72:    */   {
/*  73: 83 */     return this.maps.size();
/*  74:    */   }
/*  75:    */   
/*  76:    */   public synchronized State markAvailable()
/*  77:    */   {
/*  78: 91 */     if (this.maps.isEmpty()) {
/*  79: 92 */       this.state = State.IDLE;
/*  80:    */     } else {
/*  81: 94 */       this.state = State.PENDING;
/*  82:    */     }
/*  83: 96 */     return this.state;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public String toString()
/*  87:    */   {
/*  88:101 */     return this.hostName;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public synchronized void penalize()
/*  92:    */   {
/*  93:108 */     this.state = State.PENALIZED;
/*  94:    */   }
/*  95:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.task.reduce.MapHost
 * JD-Core Version:    0.7.0.1
 */