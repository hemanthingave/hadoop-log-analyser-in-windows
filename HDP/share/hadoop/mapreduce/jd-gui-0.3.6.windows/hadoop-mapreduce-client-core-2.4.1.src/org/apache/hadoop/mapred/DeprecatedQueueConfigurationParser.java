/*   1:    */ package org.apache.hadoop.mapred;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.HashMap;
/*   5:    */ import java.util.List;
/*   6:    */ import java.util.Map;
/*   7:    */ import org.apache.commons.logging.Log;
/*   8:    */ import org.apache.commons.logging.LogFactory;
/*   9:    */ import org.apache.hadoop.conf.Configuration;
/*  10:    */ import org.apache.hadoop.mapreduce.QueueState;
/*  11:    */ import org.apache.hadoop.security.authorize.AccessControlList;
/*  12:    */ 
/*  13:    */ class DeprecatedQueueConfigurationParser
/*  14:    */   extends QueueConfigurationParser
/*  15:    */ {
/*  16: 40 */   private static final Log LOG = LogFactory.getLog(DeprecatedQueueConfigurationParser.class);
/*  17:    */   static final String MAPRED_QUEUE_NAMES_KEY = "mapred.queue.names";
/*  18:    */   
/*  19:    */   DeprecatedQueueConfigurationParser(Configuration conf)
/*  20:    */   {
/*  21: 46 */     if (!deprecatedConf(conf)) {
/*  22: 47 */       return;
/*  23:    */     }
/*  24: 49 */     List<Queue> listq = createQueues(conf);
/*  25: 50 */     setAclsEnabled(conf.getBoolean("mapreduce.cluster.acls.enabled", false));
/*  26: 51 */     this.root = new Queue();
/*  27: 52 */     this.root.setName("");
/*  28: 53 */     for (Queue q : listq) {
/*  29: 54 */       this.root.addChild(q);
/*  30:    */     }
/*  31:    */   }
/*  32:    */   
/*  33:    */   private List<Queue> createQueues(Configuration conf)
/*  34:    */   {
/*  35: 59 */     String[] queueNameValues = conf.getStrings("mapred.queue.names");
/*  36:    */     
/*  37: 61 */     List<Queue> list = new ArrayList();
/*  38: 62 */     for (String name : queueNameValues) {
/*  39:    */       try
/*  40:    */       {
/*  41: 64 */         Map<String, AccessControlList> acls = getQueueAcls(name, conf);
/*  42:    */         
/*  43: 66 */         QueueState state = getQueueState(name, conf);
/*  44: 67 */         Queue q = new Queue(name, acls, state);
/*  45: 68 */         list.add(q);
/*  46:    */       }
/*  47:    */       catch (Throwable t)
/*  48:    */       {
/*  49: 70 */         LOG.warn("Not able to initialize queue " + name);
/*  50:    */       }
/*  51:    */     }
/*  52: 73 */     return list;
/*  53:    */   }
/*  54:    */   
/*  55:    */   private QueueState getQueueState(String name, Configuration conf)
/*  56:    */   {
/*  57: 81 */     String stateVal = conf.get(QueueManager.toFullPropertyName(name, "state"), QueueState.RUNNING.getStateName());
/*  58:    */     
/*  59:    */ 
/*  60: 84 */     return QueueState.getState(stateVal);
/*  61:    */   }
/*  62:    */   
/*  63:    */   private boolean deprecatedConf(Configuration conf)
/*  64:    */   {
/*  65: 92 */     String[] queues = null;
/*  66: 93 */     String queueNameValues = getQueueNames(conf);
/*  67: 94 */     if (queueNameValues == null) {
/*  68: 95 */       return false;
/*  69:    */     }
/*  70: 97 */     LOG.warn("Configuring \"mapred.queue.names\" in mapred-site.xml or hadoop-site.xml is deprecated and will overshadow mapred-queues.xml. Remove this property and configure queue hierarchy in mapred-queues.xml");
/*  71:    */     
/*  72:    */ 
/*  73:    */ 
/*  74:    */ 
/*  75:    */ 
/*  76:    */ 
/*  77:    */ 
/*  78:105 */     queues = conf.getStrings("mapred.queue.names");
/*  79:109 */     if (queues != null) {
/*  80:110 */       for (String queue : queues) {
/*  81:111 */         for (QueueACL qAcl : QueueACL.values())
/*  82:    */         {
/*  83:112 */           String key = QueueManager.toFullPropertyName(queue, qAcl.getAclName());
/*  84:113 */           String aclString = conf.get(key);
/*  85:114 */           if (aclString != null)
/*  86:    */           {
/*  87:115 */             LOG.warn("Configuring queue ACLs in mapred-site.xml or hadoop-site.xml is deprecated. Configure queue ACLs in mapred-queues.xml");
/*  88:    */             
/*  89:    */ 
/*  90:    */ 
/*  91:    */ 
/*  92:    */ 
/*  93:121 */             return true;
/*  94:    */           }
/*  95:    */         }
/*  96:    */       }
/*  97:    */     }
/*  98:126 */     return true;
/*  99:    */   }
/* 100:    */   
/* 101:    */   private String getQueueNames(Configuration conf)
/* 102:    */   {
/* 103:130 */     String queueNameValues = conf.get("mapred.queue.names");
/* 104:131 */     return queueNameValues;
/* 105:    */   }
/* 106:    */   
/* 107:    */   private Map<String, AccessControlList> getQueueAcls(String name, Configuration conf)
/* 108:    */   {
/* 109:140 */     HashMap<String, AccessControlList> map = new HashMap();
/* 110:142 */     for (QueueACL qAcl : QueueACL.values())
/* 111:    */     {
/* 112:143 */       String aclKey = QueueManager.toFullPropertyName(name, qAcl.getAclName());
/* 113:144 */       map.put(aclKey, new AccessControlList(conf.get(aclKey, "*")));
/* 114:    */     }
/* 115:149 */     return map;
/* 116:    */   }
/* 117:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.DeprecatedQueueConfigurationParser
 * JD-Core Version:    0.7.0.1
 */