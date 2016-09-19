/*   1:    */ package org.apache.hadoop.mapreduce.jobhistory;
/*   2:    */ 
/*   3:    */ import org.apache.avro.util.Utf8;
/*   4:    */ import org.apache.hadoop.classification.InterfaceAudience.Private;
/*   5:    */ import org.apache.hadoop.classification.InterfaceStability.Unstable;
/*   6:    */ import org.apache.hadoop.yarn.api.records.ApplicationAttemptId;
/*   7:    */ import org.apache.hadoop.yarn.api.records.ContainerId;
/*   8:    */ import org.apache.hadoop.yarn.util.ConverterUtils;
/*   9:    */ 
/*  10:    */ @InterfaceAudience.Private
/*  11:    */ @InterfaceStability.Unstable
/*  12:    */ public class AMStartedEvent
/*  13:    */   implements HistoryEvent
/*  14:    */ {
/*  15: 36 */   private AMStarted datum = new AMStarted();
/*  16:    */   private String forcedJobStateOnShutDown;
/*  17:    */   
/*  18:    */   public AMStartedEvent(ApplicationAttemptId appAttemptId, long startTime, ContainerId containerId, String nodeManagerHost, int nodeManagerPort, int nodeManagerHttpPort)
/*  19:    */   {
/*  20: 58 */     this(appAttemptId, startTime, containerId, nodeManagerHost, nodeManagerPort, nodeManagerHttpPort, null);
/*  21:    */   }
/*  22:    */   
/*  23:    */   public AMStartedEvent(ApplicationAttemptId appAttemptId, long startTime, ContainerId containerId, String nodeManagerHost, int nodeManagerPort, int nodeManagerHttpPort, String forcedJobStateOnShutDown)
/*  24:    */   {
/*  25: 83 */     this.datum.applicationAttemptId = new Utf8(appAttemptId.toString());
/*  26: 84 */     this.datum.startTime = startTime;
/*  27: 85 */     this.datum.containerId = new Utf8(containerId.toString());
/*  28: 86 */     this.datum.nodeManagerHost = new Utf8(nodeManagerHost);
/*  29: 87 */     this.datum.nodeManagerPort = nodeManagerPort;
/*  30: 88 */     this.datum.nodeManagerHttpPort = nodeManagerHttpPort;
/*  31: 89 */     this.forcedJobStateOnShutDown = forcedJobStateOnShutDown;
/*  32:    */   }
/*  33:    */   
/*  34:    */   AMStartedEvent() {}
/*  35:    */   
/*  36:    */   public Object getDatum()
/*  37:    */   {
/*  38: 96 */     return this.datum;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public void setDatum(Object datum)
/*  42:    */   {
/*  43:100 */     this.datum = ((AMStarted)datum);
/*  44:    */   }
/*  45:    */   
/*  46:    */   public ApplicationAttemptId getAppAttemptId()
/*  47:    */   {
/*  48:107 */     return ConverterUtils.toApplicationAttemptId(this.datum.applicationAttemptId.toString());
/*  49:    */   }
/*  50:    */   
/*  51:    */   public long getStartTime()
/*  52:    */   {
/*  53:115 */     return this.datum.startTime;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public ContainerId getContainerId()
/*  57:    */   {
/*  58:122 */     return ConverterUtils.toContainerId(this.datum.containerId.toString());
/*  59:    */   }
/*  60:    */   
/*  61:    */   public String getNodeManagerHost()
/*  62:    */   {
/*  63:129 */     return this.datum.nodeManagerHost.toString();
/*  64:    */   }
/*  65:    */   
/*  66:    */   public int getNodeManagerPort()
/*  67:    */   {
/*  68:136 */     return this.datum.nodeManagerPort;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public int getNodeManagerHttpPort()
/*  72:    */   {
/*  73:143 */     return this.datum.nodeManagerHttpPort;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public String getForcedJobStateOnShutDown()
/*  77:    */   {
/*  78:150 */     return this.forcedJobStateOnShutDown;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public EventType getEventType()
/*  82:    */   {
/*  83:157 */     return EventType.AM_STARTED;
/*  84:    */   }
/*  85:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.jobhistory.AMStartedEvent
 * JD-Core Version:    0.7.0.1
 */