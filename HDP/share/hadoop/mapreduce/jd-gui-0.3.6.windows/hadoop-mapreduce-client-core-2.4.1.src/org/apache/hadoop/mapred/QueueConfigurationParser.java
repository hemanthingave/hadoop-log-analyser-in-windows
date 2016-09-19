/*   1:    */ package org.apache.hadoop.mapred;
/*   2:    */ 
/*   3:    */ import java.io.BufferedInputStream;
/*   4:    */ import java.io.File;
/*   5:    */ import java.io.FileInputStream;
/*   6:    */ import java.io.IOException;
/*   7:    */ import java.io.InputStream;
/*   8:    */ import java.util.ArrayList;
/*   9:    */ import java.util.HashMap;
/*  10:    */ import java.util.HashSet;
/*  11:    */ import java.util.List;
/*  12:    */ import java.util.Map;
/*  13:    */ import java.util.Properties;
/*  14:    */ import java.util.Set;
/*  15:    */ import javax.xml.parsers.DocumentBuilder;
/*  16:    */ import javax.xml.parsers.DocumentBuilderFactory;
/*  17:    */ import javax.xml.parsers.ParserConfigurationException;
/*  18:    */ import org.apache.commons.logging.Log;
/*  19:    */ import org.apache.commons.logging.LogFactory;
/*  20:    */ import org.apache.hadoop.io.IOUtils;
/*  21:    */ import org.apache.hadoop.mapreduce.QueueState;
/*  22:    */ import org.apache.hadoop.security.authorize.AccessControlList;
/*  23:    */ import org.w3c.dom.DOMException;
/*  24:    */ import org.w3c.dom.Document;
/*  25:    */ import org.w3c.dom.Element;
/*  26:    */ import org.w3c.dom.NamedNodeMap;
/*  27:    */ import org.w3c.dom.Node;
/*  28:    */ import org.w3c.dom.NodeList;
/*  29:    */ import org.xml.sax.SAXException;
/*  30:    */ 
/*  31:    */ class QueueConfigurationParser
/*  32:    */ {
/*  33: 62 */   private static final Log LOG = LogFactory.getLog(QueueConfigurationParser.class);
/*  34: 65 */   private boolean aclsEnabled = false;
/*  35: 68 */   protected Queue root = null;
/*  36:    */   static final String NAME_SEPARATOR = ":";
/*  37:    */   static final String QUEUE_TAG = "queue";
/*  38:    */   static final String ACL_SUBMIT_JOB_TAG = "acl-submit-job";
/*  39:    */   static final String ACL_ADMINISTER_JOB_TAG = "acl-administer-jobs";
/*  40:    */   @Deprecated
/*  41:    */   static final String ACLS_ENABLED_TAG = "aclsEnabled";
/*  42:    */   static final String PROPERTIES_TAG = "properties";
/*  43:    */   static final String STATE_TAG = "state";
/*  44:    */   static final String QUEUE_NAME_TAG = "name";
/*  45:    */   static final String QUEUES_TAG = "queues";
/*  46:    */   static final String PROPERTY_TAG = "property";
/*  47:    */   static final String KEY_TAG = "key";
/*  48:    */   static final String VALUE_TAG = "value";
/*  49:    */   
/*  50:    */   QueueConfigurationParser() {}
/*  51:    */   
/*  52:    */   QueueConfigurationParser(String confFile, boolean areAclsEnabled)
/*  53:    */   {
/*  54: 98 */     this.aclsEnabled = areAclsEnabled;
/*  55: 99 */     File file = new File(confFile).getAbsoluteFile();
/*  56:100 */     if (!file.exists()) {
/*  57:101 */       throw new RuntimeException("Configuration file not found at " + confFile);
/*  58:    */     }
/*  59:104 */     InputStream in = null;
/*  60:    */     try
/*  61:    */     {
/*  62:106 */       in = new BufferedInputStream(new FileInputStream(file));
/*  63:107 */       loadFrom(in);
/*  64:    */     }
/*  65:    */     catch (IOException ioe)
/*  66:    */     {
/*  67:109 */       throw new RuntimeException(ioe);
/*  68:    */     }
/*  69:    */     finally
/*  70:    */     {
/*  71:111 */       IOUtils.closeStream(in);
/*  72:    */     }
/*  73:    */   }
/*  74:    */   
/*  75:    */   QueueConfigurationParser(InputStream xmlInput, boolean areAclsEnabled)
/*  76:    */   {
/*  77:116 */     this.aclsEnabled = areAclsEnabled;
/*  78:117 */     loadFrom(xmlInput);
/*  79:    */   }
/*  80:    */   
/*  81:    */   private void loadFrom(InputStream xmlInput)
/*  82:    */   {
/*  83:    */     try
/*  84:    */     {
/*  85:122 */       this.root = loadResource(xmlInput);
/*  86:    */     }
/*  87:    */     catch (ParserConfigurationException e)
/*  88:    */     {
/*  89:124 */       throw new RuntimeException(e);
/*  90:    */     }
/*  91:    */     catch (SAXException e)
/*  92:    */     {
/*  93:126 */       throw new RuntimeException(e);
/*  94:    */     }
/*  95:    */     catch (IOException e)
/*  96:    */     {
/*  97:128 */       throw new RuntimeException(e);
/*  98:    */     }
/*  99:    */   }
/* 100:    */   
/* 101:    */   void setAclsEnabled(boolean aclsEnabled)
/* 102:    */   {
/* 103:134 */     this.aclsEnabled = aclsEnabled;
/* 104:    */   }
/* 105:    */   
/* 106:    */   boolean isAclsEnabled()
/* 107:    */   {
/* 108:138 */     return this.aclsEnabled;
/* 109:    */   }
/* 110:    */   
/* 111:    */   Queue getRoot()
/* 112:    */   {
/* 113:142 */     return this.root;
/* 114:    */   }
/* 115:    */   
/* 116:    */   void setRoot(Queue root)
/* 117:    */   {
/* 118:146 */     this.root = root;
/* 119:    */   }
/* 120:    */   
/* 121:    */   protected Queue loadResource(InputStream resourceInput)
/* 122:    */     throws ParserConfigurationException, SAXException, IOException
/* 123:    */   {
/* 124:161 */     DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
/* 125:    */     
/* 126:    */ 
/* 127:164 */     docBuilderFactory.setIgnoringComments(true);
/* 128:    */     
/* 129:    */ 
/* 130:167 */     docBuilderFactory.setNamespaceAware(true);
/* 131:    */     try
/* 132:    */     {
/* 133:169 */       docBuilderFactory.setXIncludeAware(true);
/* 134:    */     }
/* 135:    */     catch (UnsupportedOperationException e)
/* 136:    */     {
/* 137:171 */       LOG.info("Failed to set setXIncludeAware(true) for parser " + docBuilderFactory + ":" + e);
/* 138:    */     }
/* 139:176 */     DocumentBuilder builder = docBuilderFactory.newDocumentBuilder();
/* 140:177 */     Document doc = null;
/* 141:178 */     Element queuesNode = null;
/* 142:    */     
/* 143:180 */     doc = builder.parse(resourceInput);
/* 144:181 */     queuesNode = doc.getDocumentElement();
/* 145:182 */     return parseResource(queuesNode);
/* 146:    */   }
/* 147:    */   
/* 148:    */   private Queue parseResource(Element queuesNode)
/* 149:    */   {
/* 150:186 */     Queue rootNode = null;
/* 151:    */     try
/* 152:    */     {
/* 153:188 */       if (!"queues".equals(queuesNode.getTagName()))
/* 154:    */       {
/* 155:189 */         LOG.info("Bad conf file: top-level element not <queues>");
/* 156:190 */         throw new RuntimeException("No queues defined ");
/* 157:    */       }
/* 158:192 */       NamedNodeMap nmp = queuesNode.getAttributes();
/* 159:193 */       Node acls = nmp.getNamedItem("aclsEnabled");
/* 160:195 */       if (acls != null) {
/* 161:196 */         LOG.warn("Configuring aclsEnabled flag in mapred-queues.xml is not valid. This tag is ignored. Configure mapreduce.cluster.acls.enabled in mapred-site.xml. See the  documentation of mapreduce.cluster.acls.enabled, which is used for enabling job level authorization and  queue level authorization.");
/* 162:    */       }
/* 163:205 */       NodeList props = queuesNode.getChildNodes();
/* 164:206 */       if ((props == null) || (props.getLength() <= 0))
/* 165:    */       {
/* 166:207 */         LOG.info(" Bad configuration no queues defined ");
/* 167:208 */         throw new RuntimeException(" No queues defined ");
/* 168:    */       }
/* 169:212 */       for (int i = 0; i < props.getLength(); i++)
/* 170:    */       {
/* 171:213 */         Node propNode = props.item(i);
/* 172:214 */         if ((propNode instanceof Element))
/* 173:    */         {
/* 174:218 */           if (!propNode.getNodeName().equals("queue"))
/* 175:    */           {
/* 176:219 */             LOG.info("At root level only \" queue \" tags are allowed ");
/* 177:220 */             throw new RuntimeException("Malformed xml document no queue defined ");
/* 178:    */           }
/* 179:224 */           Element prop = (Element)propNode;
/* 180:    */           
/* 181:226 */           Queue q = createHierarchy("", prop);
/* 182:227 */           if (rootNode == null)
/* 183:    */           {
/* 184:228 */             rootNode = new Queue();
/* 185:229 */             rootNode.setName("");
/* 186:    */           }
/* 187:231 */           rootNode.addChild(q);
/* 188:    */         }
/* 189:    */       }
/* 190:233 */       return rootNode;
/* 191:    */     }
/* 192:    */     catch (DOMException e)
/* 193:    */     {
/* 194:235 */       LOG.info("Error parsing conf file: " + e);
/* 195:236 */       throw new RuntimeException(e);
/* 196:    */     }
/* 197:    */   }
/* 198:    */   
/* 199:    */   private Queue createHierarchy(String parent, Element queueNode)
/* 200:    */   {
/* 201:247 */     if (queueNode == null) {
/* 202:248 */       return null;
/* 203:    */     }
/* 204:252 */     String name = "";
/* 205:253 */     Queue newQueue = new Queue();
/* 206:254 */     Map<String, AccessControlList> acls = new HashMap();
/* 207:    */     
/* 208:    */ 
/* 209:257 */     NodeList fields = queueNode.getChildNodes();
/* 210:258 */     validate(queueNode);
/* 211:259 */     List<Element> subQueues = new ArrayList();
/* 212:    */     
/* 213:261 */     String submitKey = "";
/* 214:262 */     String adminKey = "";
/* 215:264 */     for (int j = 0; j < fields.getLength(); j++)
/* 216:    */     {
/* 217:265 */       Node fieldNode = fields.item(j);
/* 218:266 */       if ((fieldNode instanceof Element))
/* 219:    */       {
/* 220:269 */         Element field = (Element)fieldNode;
/* 221:270 */         if ("name".equals(field.getTagName()))
/* 222:    */         {
/* 223:271 */           String nameValue = field.getTextContent();
/* 224:272 */           if ((field.getTextContent() == null) || (field.getTextContent().trim().equals("")) || (field.getTextContent().contains(":"))) {
/* 225:275 */             throw new RuntimeException("Improper queue name : " + nameValue);
/* 226:    */           }
/* 227:278 */           if (!parent.equals("")) {
/* 228:279 */             name = name + parent + ":";
/* 229:    */           }
/* 230:283 */           name = name + nameValue;
/* 231:284 */           newQueue.setName(name);
/* 232:285 */           submitKey = QueueManager.toFullPropertyName(name, QueueACL.SUBMIT_JOB.getAclName());
/* 233:    */           
/* 234:287 */           adminKey = QueueManager.toFullPropertyName(name, QueueACL.ADMINISTER_JOBS.getAclName());
/* 235:    */         }
/* 236:291 */         if (("queue".equals(field.getTagName())) && (field.hasChildNodes())) {
/* 237:292 */           subQueues.add(field);
/* 238:    */         }
/* 239:294 */         if (isAclsEnabled())
/* 240:    */         {
/* 241:295 */           if ("acl-submit-job".equals(field.getTagName())) {
/* 242:296 */             acls.put(submitKey, new AccessControlList(field.getTextContent()));
/* 243:    */           }
/* 244:299 */           if ("acl-administer-jobs".equals(field.getTagName())) {
/* 245:300 */             acls.put(adminKey, new AccessControlList(field.getTextContent()));
/* 246:    */           }
/* 247:    */         }
/* 248:304 */         if ("properties".equals(field.getTagName()))
/* 249:    */         {
/* 250:305 */           Properties properties = populateProperties(field);
/* 251:306 */           newQueue.setProperties(properties);
/* 252:    */         }
/* 253:309 */         if ("state".equals(field.getTagName()))
/* 254:    */         {
/* 255:310 */           String state = field.getTextContent();
/* 256:311 */           newQueue.setState(QueueState.getState(state));
/* 257:    */         }
/* 258:    */       }
/* 259:    */     }
/* 260:315 */     if (!acls.containsKey(submitKey)) {
/* 261:316 */       acls.put(submitKey, new AccessControlList(" "));
/* 262:    */     }
/* 263:319 */     if (!acls.containsKey(adminKey)) {
/* 264:320 */       acls.put(adminKey, new AccessControlList(" "));
/* 265:    */     }
/* 266:324 */     newQueue.setAcls(acls);
/* 267:328 */     for (Element field : subQueues) {
/* 268:329 */       newQueue.addChild(createHierarchy(newQueue.getName(), field));
/* 269:    */     }
/* 270:331 */     return newQueue;
/* 271:    */   }
/* 272:    */   
/* 273:    */   private Properties populateProperties(Element field)
/* 274:    */   {
/* 275:341 */     Properties props = new Properties();
/* 276:    */     
/* 277:343 */     NodeList propfields = field.getChildNodes();
/* 278:345 */     for (int i = 0; i < propfields.getLength(); i++)
/* 279:    */     {
/* 280:346 */       Node prop = propfields.item(i);
/* 281:350 */       if ((prop instanceof Element)) {
/* 282:354 */         if (("property".equals(prop.getNodeName())) && 
/* 283:355 */           (prop.hasAttributes()))
/* 284:    */         {
/* 285:356 */           NamedNodeMap nmp = prop.getAttributes();
/* 286:357 */           if ((nmp.getNamedItem("key") != null) && (nmp.getNamedItem("value") != null)) {
/* 287:359 */             props.setProperty(nmp.getNamedItem("key").getTextContent(), nmp.getNamedItem("value").getTextContent());
/* 288:    */           }
/* 289:    */         }
/* 290:    */       }
/* 291:    */     }
/* 292:366 */     return props;
/* 293:    */   }
/* 294:    */   
/* 295:    */   private void validate(Node node)
/* 296:    */   {
/* 297:381 */     NodeList fields = node.getChildNodes();
/* 298:    */     
/* 299:    */ 
/* 300:    */ 
/* 301:385 */     Set<String> siblings = new HashSet();
/* 302:386 */     for (int i = 0; i < fields.getLength(); i++) {
/* 303:387 */       if ((fields.item(i) instanceof Element)) {
/* 304:390 */         siblings.add(fields.item(i).getNodeName());
/* 305:    */       }
/* 306:    */     }
/* 307:393 */     if (!siblings.contains("name")) {
/* 308:394 */       throw new RuntimeException(" Malformed xml formation queue name not specified ");
/* 309:    */     }
/* 310:398 */     if ((siblings.contains("queue")) && ((siblings.contains("acl-administer-jobs")) || (siblings.contains("acl-submit-job")) || (siblings.contains("state")))) {
/* 311:403 */       throw new RuntimeException(" Malformed xml formation queue tag and acls tags or state tags are siblings ");
/* 312:    */     }
/* 313:    */   }
/* 314:    */   
/* 315:    */   private static String getSimpleQueueName(String fullQName)
/* 316:    */   {
/* 317:411 */     int index = fullQName.lastIndexOf(":");
/* 318:412 */     if (index < 0) {
/* 319:413 */       return fullQName;
/* 320:    */     }
/* 321:415 */     return fullQName.substring(index + 1, fullQName.length());
/* 322:    */   }
/* 323:    */   
/* 324:    */   static Element getQueueElement(Document document, JobQueueInfo jqi)
/* 325:    */   {
/* 326:430 */     Element q = document.createElement("queue");
/* 327:    */     
/* 328:    */ 
/* 329:433 */     Element qName = document.createElement("name");
/* 330:434 */     qName.setTextContent(getSimpleQueueName(jqi.getQueueName()));
/* 331:435 */     q.appendChild(qName);
/* 332:    */     
/* 333:    */ 
/* 334:438 */     Properties props = jqi.getProperties();
/* 335:439 */     Element propsElement = document.createElement("properties");
/* 336:440 */     if (props != null)
/* 337:    */     {
/* 338:441 */       Set<String> propList = props.stringPropertyNames();
/* 339:442 */       for (String prop : propList)
/* 340:    */       {
/* 341:443 */         Element propertyElement = document.createElement("property");
/* 342:444 */         propertyElement.setAttribute("key", prop);
/* 343:445 */         propertyElement.setAttribute("value", (String)props.get(prop));
/* 344:446 */         propsElement.appendChild(propertyElement);
/* 345:    */       }
/* 346:    */     }
/* 347:449 */     q.appendChild(propsElement);
/* 348:    */     
/* 349:    */ 
/* 350:452 */     String queueState = jqi.getState().getStateName();
/* 351:453 */     if ((queueState != null) && (!queueState.equals(QueueState.UNDEFINED.getStateName())))
/* 352:    */     {
/* 353:455 */       Element qStateElement = document.createElement("state");
/* 354:456 */       qStateElement.setTextContent(queueState);
/* 355:457 */       q.appendChild(qStateElement);
/* 356:    */     }
/* 357:461 */     List<JobQueueInfo> children = jqi.getChildren();
/* 358:462 */     if (children != null) {
/* 359:463 */       for (JobQueueInfo child : children) {
/* 360:464 */         q.appendChild(getQueueElement(document, child));
/* 361:    */       }
/* 362:    */     }
/* 363:468 */     return q;
/* 364:    */   }
/* 365:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.QueueConfigurationParser
 * JD-Core Version:    0.7.0.1
 */