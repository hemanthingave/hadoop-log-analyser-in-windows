/*   1:    */ package org.apache.hadoop.mapred;
/*   2:    */ 
/*   3:    */ import java.io.BufferedInputStream;
/*   4:    */ import java.io.IOException;
/*   5:    */ import java.io.InputStream;
/*   6:    */ import java.io.Writer;
/*   7:    */ import java.net.URL;
/*   8:    */ import java.util.ArrayList;
/*   9:    */ import java.util.HashMap;
/*  10:    */ import java.util.Map;
/*  11:    */ import java.util.Map.Entry;
/*  12:    */ import java.util.Properties;
/*  13:    */ import java.util.Set;
/*  14:    */ import org.apache.commons.logging.Log;
/*  15:    */ import org.apache.commons.logging.LogFactory;
/*  16:    */ import org.apache.hadoop.classification.InterfaceAudience.Private;
/*  17:    */ import org.apache.hadoop.conf.Configuration;
/*  18:    */ import org.apache.hadoop.io.IOUtils;
/*  19:    */ import org.apache.hadoop.mapreduce.QueueState;
/*  20:    */ import org.apache.hadoop.security.UserGroupInformation;
/*  21:    */ import org.apache.hadoop.security.authorize.AccessControlList;
/*  22:    */ import org.apache.hadoop.util.StringUtils;
/*  23:    */ import org.codehaus.jackson.JsonFactory;
/*  24:    */ import org.codehaus.jackson.JsonGenerationException;
/*  25:    */ import org.codehaus.jackson.JsonGenerator;
/*  26:    */ 
/*  27:    */ @InterfaceAudience.Private
/*  28:    */ public class QueueManager
/*  29:    */ {
/*  30: 85 */   private static final Log LOG = LogFactory.getLog(QueueManager.class);
/*  31: 88 */   private Map<String, Queue> leafQueues = new HashMap();
/*  32: 89 */   private Map<String, Queue> allQueues = new HashMap();
/*  33:    */   public static final String QUEUE_CONF_FILE_NAME = "mapred-queues.xml";
/*  34:    */   static final String QUEUE_CONF_DEFAULT_FILE_NAME = "mapred-queues-default.xml";
/*  35:    */   static final String QUEUE_CONF_PROPERTY_NAME_PREFIX = "mapred.queue.";
/*  36: 97 */   private Queue root = null;
/*  37:100 */   private boolean areAclsEnabled = false;
/*  38:    */   static final String MSG_REFRESH_FAILURE_WITH_CHANGE_OF_HIERARCHY = "Unable to refresh queues because queue-hierarchy changed. Retaining existing configuration. ";
/*  39:    */   static final String MSG_REFRESH_FAILURE_WITH_SCHEDULER_FAILURE = "Scheduler couldn't refresh it's queues with the new configuration properties. Retaining existing configuration throughout the system.";
/*  40:    */   
/*  41:    */   static QueueConfigurationParser getQueueConfigurationParser(Configuration conf, boolean reloadConf, boolean areAclsEnabled)
/*  42:    */   {
/*  43:119 */     if ((conf != null) && (conf.get("mapred.queue.names") != null))
/*  44:    */     {
/*  45:121 */       if (reloadConf) {
/*  46:122 */         conf.reloadConfiguration();
/*  47:    */       }
/*  48:124 */       return new DeprecatedQueueConfigurationParser(conf);
/*  49:    */     }
/*  50:126 */     URL xmlInUrl = Thread.currentThread().getContextClassLoader().getResource("mapred-queues.xml");
/*  51:129 */     if (xmlInUrl == null)
/*  52:    */     {
/*  53:130 */       xmlInUrl = Thread.currentThread().getContextClassLoader().getResource("mapred-queues-default.xml");
/*  54:    */       
/*  55:132 */       assert (xmlInUrl != null);
/*  56:    */     }
/*  57:134 */     InputStream stream = null;
/*  58:    */     try
/*  59:    */     {
/*  60:136 */       stream = xmlInUrl.openStream();
/*  61:137 */       return new QueueConfigurationParser(new BufferedInputStream(stream), areAclsEnabled);
/*  62:    */     }
/*  63:    */     catch (IOException ioe)
/*  64:    */     {
/*  65:140 */       throw new RuntimeException("Couldn't open queue configuration at " + xmlInUrl, ioe);
/*  66:    */     }
/*  67:    */     finally
/*  68:    */     {
/*  69:143 */       IOUtils.closeStream(stream);
/*  70:    */     }
/*  71:    */   }
/*  72:    */   
/*  73:    */   QueueManager()
/*  74:    */   {
/*  75:149 */     this(false);
/*  76:    */   }
/*  77:    */   
/*  78:    */   QueueManager(boolean areAclsEnabled)
/*  79:    */   {
/*  80:153 */     this.areAclsEnabled = areAclsEnabled;
/*  81:154 */     initialize(getQueueConfigurationParser(null, false, areAclsEnabled));
/*  82:    */   }
/*  83:    */   
/*  84:    */   public QueueManager(Configuration clusterConf)
/*  85:    */   {
/*  86:169 */     this.areAclsEnabled = clusterConf.getBoolean("mapreduce.cluster.acls.enabled", false);
/*  87:170 */     initialize(getQueueConfigurationParser(clusterConf, false, this.areAclsEnabled));
/*  88:    */   }
/*  89:    */   
/*  90:    */   QueueManager(String confFile, boolean areAclsEnabled)
/*  91:    */   {
/*  92:183 */     this.areAclsEnabled = areAclsEnabled;
/*  93:184 */     QueueConfigurationParser cp = new QueueConfigurationParser(confFile, areAclsEnabled);
/*  94:    */     
/*  95:186 */     initialize(cp);
/*  96:    */   }
/*  97:    */   
/*  98:    */   private void initialize(QueueConfigurationParser cp)
/*  99:    */   {
/* 100:196 */     this.root = cp.getRoot();
/* 101:197 */     this.leafQueues.clear();
/* 102:198 */     this.allQueues.clear();
/* 103:    */     
/* 104:    */ 
/* 105:201 */     this.leafQueues = getRoot().getLeafQueues();
/* 106:202 */     this.allQueues.putAll(getRoot().getInnerQueues());
/* 107:203 */     this.allQueues.putAll(this.leafQueues);
/* 108:    */     
/* 109:205 */     LOG.info("AllQueues : " + this.allQueues + "; LeafQueues : " + this.leafQueues);
/* 110:    */   }
/* 111:    */   
/* 112:    */   public synchronized Set<String> getLeafQueueNames()
/* 113:    */   {
/* 114:219 */     return this.leafQueues.keySet();
/* 115:    */   }
/* 116:    */   
/* 117:    */   public synchronized boolean hasAccess(String queueName, QueueACL qACL, UserGroupInformation ugi)
/* 118:    */   {
/* 119:238 */     Queue q = (Queue)this.leafQueues.get(queueName);
/* 120:240 */     if (q == null)
/* 121:    */     {
/* 122:241 */       LOG.info("Queue " + queueName + " is not present");
/* 123:242 */       return false;
/* 124:    */     }
/* 125:245 */     if ((q.getChildren() != null) && (!q.getChildren().isEmpty()))
/* 126:    */     {
/* 127:246 */       LOG.info("Cannot submit job to parent queue " + q.getName());
/* 128:247 */       return false;
/* 129:    */     }
/* 130:250 */     if (!areAclsEnabled()) {
/* 131:251 */       return true;
/* 132:    */     }
/* 133:254 */     if (LOG.isDebugEnabled()) {
/* 134:255 */       LOG.debug("Checking access for the acl " + toFullPropertyName(queueName, qACL.getAclName()) + " for user " + ugi.getShortUserName());
/* 135:    */     }
/* 136:259 */     AccessControlList acl = (AccessControlList)q.getAcls().get(toFullPropertyName(queueName, qACL.getAclName()));
/* 137:261 */     if (acl == null) {
/* 138:262 */       return false;
/* 139:    */     }
/* 140:266 */     return acl.isUserAllowed(ugi);
/* 141:    */   }
/* 142:    */   
/* 143:    */   synchronized boolean isRunning(String queueName)
/* 144:    */   {
/* 145:276 */     Queue q = (Queue)this.leafQueues.get(queueName);
/* 146:277 */     if (q != null) {
/* 147:278 */       return q.getState().equals(QueueState.RUNNING);
/* 148:    */     }
/* 149:280 */     return false;
/* 150:    */   }
/* 151:    */   
/* 152:    */   public synchronized void setSchedulerInfo(String queueName, Object queueInfo)
/* 153:    */   {
/* 154:297 */     if (this.allQueues.get(queueName) != null) {
/* 155:298 */       ((Queue)this.allQueues.get(queueName)).setSchedulingInfo(queueInfo);
/* 156:    */     }
/* 157:    */   }
/* 158:    */   
/* 159:    */   public synchronized Object getSchedulerInfo(String queueName)
/* 160:    */   {
/* 161:309 */     if (this.allQueues.get(queueName) != null) {
/* 162:310 */       return ((Queue)this.allQueues.get(queueName)).getSchedulingInfo();
/* 163:    */     }
/* 164:312 */     return null;
/* 165:    */   }
/* 166:    */   
/* 167:    */   synchronized void refreshQueues(Configuration conf, QueueRefresher schedulerRefresher)
/* 168:    */     throws IOException
/* 169:    */   {
/* 170:342 */     QueueConfigurationParser cp = getQueueConfigurationParser(conf, true, this.areAclsEnabled);
/* 171:351 */     if (!this.root.isHierarchySameAs(cp.getRoot()))
/* 172:    */     {
/* 173:352 */       LOG.warn("Unable to refresh queues because queue-hierarchy changed. Retaining existing configuration. ");
/* 174:353 */       throw new IOException("Unable to refresh queues because queue-hierarchy changed. Retaining existing configuration. ");
/* 175:    */     }
/* 176:360 */     if (schedulerRefresher != null) {
/* 177:    */       try
/* 178:    */       {
/* 179:362 */         schedulerRefresher.refreshQueues(cp.getRoot().getJobQueueInfo().getChildren());
/* 180:    */       }
/* 181:    */       catch (Throwable e)
/* 182:    */       {
/* 183:364 */         StringBuilder msg = new StringBuilder("Scheduler's refresh-queues failed with the exception : " + StringUtils.stringifyException(e));
/* 184:    */         
/* 185:    */ 
/* 186:    */ 
/* 187:368 */         msg.append("\n");
/* 188:369 */         msg.append("Scheduler couldn't refresh it's queues with the new configuration properties. Retaining existing configuration throughout the system.");
/* 189:370 */         LOG.error(msg.toString());
/* 190:371 */         throw new IOException(msg.toString());
/* 191:    */       }
/* 192:    */     }
/* 193:384 */     cp.getRoot().copySchedulingInfo(this.root);
/* 194:    */     
/* 195:    */ 
/* 196:387 */     initialize(cp);
/* 197:    */     
/* 198:389 */     LOG.info("Queue configuration is refreshed successfully.");
/* 199:    */   }
/* 200:    */   
/* 201:    */   public static final String toFullPropertyName(String queue, String property)
/* 202:    */   {
/* 203:396 */     return "mapred.queue." + queue + "." + property;
/* 204:    */   }
/* 205:    */   
/* 206:    */   synchronized JobQueueInfo[] getJobQueueInfos()
/* 207:    */   {
/* 208:406 */     ArrayList<JobQueueInfo> queueInfoList = new ArrayList();
/* 209:407 */     for (String queue : this.allQueues.keySet())
/* 210:    */     {
/* 211:408 */       JobQueueInfo queueInfo = getJobQueueInfo(queue);
/* 212:409 */       if (queueInfo != null) {
/* 213:410 */         queueInfoList.add(queueInfo);
/* 214:    */       }
/* 215:    */     }
/* 216:413 */     return (JobQueueInfo[])queueInfoList.toArray(new JobQueueInfo[queueInfoList.size()]);
/* 217:    */   }
/* 218:    */   
/* 219:    */   synchronized JobQueueInfo getJobQueueInfo(String queue)
/* 220:    */   {
/* 221:425 */     if (this.allQueues.containsKey(queue)) {
/* 222:426 */       return ((Queue)this.allQueues.get(queue)).getJobQueueInfo();
/* 223:    */     }
/* 224:429 */     return null;
/* 225:    */   }
/* 226:    */   
/* 227:    */   synchronized Map<String, JobQueueInfo> getJobQueueInfoMapping()
/* 228:    */   {
/* 229:442 */     Map<String, JobQueueInfo> m = new HashMap();
/* 230:444 */     for (String key : this.allQueues.keySet()) {
/* 231:445 */       m.put(key, ((Queue)this.allQueues.get(key)).getJobQueueInfo());
/* 232:    */     }
/* 233:448 */     return m;
/* 234:    */   }
/* 235:    */   
/* 236:    */   synchronized QueueAclsInfo[] getQueueAcls(UserGroupInformation ugi)
/* 237:    */     throws IOException
/* 238:    */   {
/* 239:462 */     ArrayList<QueueAclsInfo> queueAclsInfolist = new ArrayList();
/* 240:    */     
/* 241:464 */     QueueACL[] qAcls = QueueACL.values();
/* 242:465 */     for (String queueName : this.leafQueues.keySet())
/* 243:    */     {
/* 244:466 */       QueueAclsInfo queueAclsInfo = null;
/* 245:467 */       ArrayList<String> operationsAllowed = null;
/* 246:468 */       for (QueueACL qAcl : qAcls) {
/* 247:469 */         if (hasAccess(queueName, qAcl, ugi))
/* 248:    */         {
/* 249:470 */           if (operationsAllowed == null) {
/* 250:471 */             operationsAllowed = new ArrayList();
/* 251:    */           }
/* 252:473 */           operationsAllowed.add(qAcl.getAclName());
/* 253:    */         }
/* 254:    */       }
/* 255:476 */       if (operationsAllowed != null)
/* 256:    */       {
/* 257:479 */         queueAclsInfo = new QueueAclsInfo(queueName, (String[])operationsAllowed.toArray(new String[operationsAllowed.size()]));
/* 258:    */         
/* 259:    */ 
/* 260:482 */         queueAclsInfolist.add(queueAclsInfo);
/* 261:    */       }
/* 262:    */     }
/* 263:485 */     return (QueueAclsInfo[])queueAclsInfolist.toArray(new QueueAclsInfo[queueAclsInfolist.size()]);
/* 264:    */   }
/* 265:    */   
/* 266:    */   boolean areAclsEnabled()
/* 267:    */   {
/* 268:498 */     return this.areAclsEnabled;
/* 269:    */   }
/* 270:    */   
/* 271:    */   Queue getRoot()
/* 272:    */   {
/* 273:507 */     return this.root;
/* 274:    */   }
/* 275:    */   
/* 276:    */   static void dumpConfiguration(Writer out, Configuration conf)
/* 277:    */     throws IOException
/* 278:    */   {
/* 279:517 */     dumpConfiguration(out, null, conf);
/* 280:    */   }
/* 281:    */   
/* 282:    */   static void dumpConfiguration(Writer out, String configFile, Configuration conf)
/* 283:    */     throws IOException
/* 284:    */   {
/* 285:529 */     if ((conf != null) && (conf.get("mapred.queue.names") != null)) {
/* 286:531 */       return;
/* 287:    */     }
/* 288:534 */     JsonFactory dumpFactory = new JsonFactory();
/* 289:535 */     JsonGenerator dumpGenerator = dumpFactory.createJsonGenerator(out);
/* 290:    */     
/* 291:537 */     boolean aclsEnabled = false;
/* 292:538 */     if (conf != null) {
/* 293:539 */       aclsEnabled = conf.getBoolean("mapreduce.cluster.acls.enabled", false);
/* 294:    */     }
/* 295:    */     QueueConfigurationParser parser;
/* 296:    */     QueueConfigurationParser parser;
/* 297:541 */     if ((configFile != null) && (!"".equals(configFile))) {
/* 298:542 */       parser = new QueueConfigurationParser(configFile, aclsEnabled);
/* 299:    */     } else {
/* 300:545 */       parser = getQueueConfigurationParser(null, false, aclsEnabled);
/* 301:    */     }
/* 302:547 */     dumpGenerator.writeStartObject();
/* 303:548 */     dumpGenerator.writeFieldName("queues");
/* 304:549 */     dumpGenerator.writeStartArray();
/* 305:550 */     dumpConfiguration(dumpGenerator, parser.getRoot().getChildren());
/* 306:551 */     dumpGenerator.writeEndArray();
/* 307:552 */     dumpGenerator.writeEndObject();
/* 308:553 */     dumpGenerator.flush();
/* 309:    */   }
/* 310:    */   
/* 311:    */   private static void dumpConfiguration(JsonGenerator dumpGenerator, Set<Queue> rootQueues)
/* 312:    */     throws JsonGenerationException, IOException
/* 313:    */   {
/* 314:567 */     for (Queue queue : rootQueues)
/* 315:    */     {
/* 316:568 */       dumpGenerator.writeStartObject();
/* 317:569 */       dumpGenerator.writeStringField("name", queue.getName());
/* 318:570 */       dumpGenerator.writeStringField("state", queue.getState().toString());
/* 319:571 */       AccessControlList submitJobList = null;
/* 320:572 */       AccessControlList administerJobsList = null;
/* 321:573 */       if (queue.getAcls() != null)
/* 322:    */       {
/* 323:574 */         submitJobList = (AccessControlList)queue.getAcls().get(toFullPropertyName(queue.getName(), QueueACL.SUBMIT_JOB.getAclName()));
/* 324:    */         
/* 325:    */ 
/* 326:577 */         administerJobsList = (AccessControlList)queue.getAcls().get(toFullPropertyName(queue.getName(), QueueACL.ADMINISTER_JOBS.getAclName()));
/* 327:    */       }
/* 328:581 */       String aclsSubmitJobValue = " ";
/* 329:582 */       if (submitJobList != null) {
/* 330:583 */         aclsSubmitJobValue = submitJobList.getAclString();
/* 331:    */       }
/* 332:585 */       dumpGenerator.writeStringField("acl_submit_job", aclsSubmitJobValue);
/* 333:586 */       String aclsAdministerValue = " ";
/* 334:587 */       if (administerJobsList != null) {
/* 335:588 */         aclsAdministerValue = administerJobsList.getAclString();
/* 336:    */       }
/* 337:590 */       dumpGenerator.writeStringField("acl_administer_jobs", aclsAdministerValue);
/* 338:    */       
/* 339:592 */       dumpGenerator.writeFieldName("properties");
/* 340:593 */       dumpGenerator.writeStartArray();
/* 341:594 */       if (queue.getProperties() != null) {
/* 342:596 */         for (Map.Entry<Object, Object> property : queue.getProperties().entrySet())
/* 343:    */         {
/* 344:597 */           dumpGenerator.writeStartObject();
/* 345:598 */           dumpGenerator.writeStringField("key", (String)property.getKey());
/* 346:599 */           dumpGenerator.writeStringField("value", (String)property.getValue());
/* 347:600 */           dumpGenerator.writeEndObject();
/* 348:    */         }
/* 349:    */       }
/* 350:603 */       dumpGenerator.writeEndArray();
/* 351:604 */       Set<Queue> childQueues = queue.getChildren();
/* 352:605 */       dumpGenerator.writeFieldName("children");
/* 353:606 */       dumpGenerator.writeStartArray();
/* 354:607 */       if ((childQueues != null) && (childQueues.size() > 0)) {
/* 355:608 */         dumpConfiguration(dumpGenerator, childQueues);
/* 356:    */       }
/* 357:610 */       dumpGenerator.writeEndArray();
/* 358:611 */       dumpGenerator.writeEndObject();
/* 359:    */     }
/* 360:    */   }
/* 361:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.QueueManager
 * JD-Core Version:    0.7.0.1
 */