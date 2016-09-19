/*   1:    */ package org.apache.hadoop.mapred;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.HashMap;
/*   5:    */ import java.util.Iterator;
/*   6:    */ import java.util.List;
/*   7:    */ import java.util.Map;
/*   8:    */ import java.util.Properties;
/*   9:    */ import java.util.Set;
/*  10:    */ import java.util.TreeSet;
/*  11:    */ import org.apache.commons.logging.Log;
/*  12:    */ import org.apache.commons.logging.LogFactory;
/*  13:    */ import org.apache.hadoop.mapreduce.QueueState;
/*  14:    */ import org.apache.hadoop.security.authorize.AccessControlList;
/*  15:    */ 
/*  16:    */ class Queue
/*  17:    */   implements Comparable<Queue>
/*  18:    */ {
/*  19: 39 */   private static final Log LOG = LogFactory.getLog(Queue.class);
/*  20: 42 */   private String name = null;
/*  21:    */   private Map<String, AccessControlList> acls;
/*  22: 48 */   private QueueState state = QueueState.RUNNING;
/*  23:    */   private Object schedulingInfo;
/*  24:    */   private Set<Queue> children;
/*  25:    */   private Properties props;
/*  26:    */   
/*  27:    */   Queue() {}
/*  28:    */   
/*  29:    */   Queue(String name, Map<String, AccessControlList> acls, QueueState state)
/*  30:    */   {
/*  31: 75 */     this.name = name;
/*  32: 76 */     this.acls = acls;
/*  33: 77 */     this.state = state;
/*  34:    */   }
/*  35:    */   
/*  36:    */   String getName()
/*  37:    */   {
/*  38: 86 */     return this.name;
/*  39:    */   }
/*  40:    */   
/*  41:    */   void setName(String name)
/*  42:    */   {
/*  43: 94 */     this.name = name;
/*  44:    */   }
/*  45:    */   
/*  46:    */   Map<String, AccessControlList> getAcls()
/*  47:    */   {
/*  48:108 */     return this.acls;
/*  49:    */   }
/*  50:    */   
/*  51:    */   void setAcls(Map<String, AccessControlList> acls)
/*  52:    */   {
/*  53:117 */     this.acls = acls;
/*  54:    */   }
/*  55:    */   
/*  56:    */   QueueState getState()
/*  57:    */   {
/*  58:125 */     return this.state;
/*  59:    */   }
/*  60:    */   
/*  61:    */   void setState(QueueState state)
/*  62:    */   {
/*  63:133 */     this.state = state;
/*  64:    */   }
/*  65:    */   
/*  66:    */   Object getSchedulingInfo()
/*  67:    */   {
/*  68:141 */     return this.schedulingInfo;
/*  69:    */   }
/*  70:    */   
/*  71:    */   void setSchedulingInfo(Object schedulingInfo)
/*  72:    */   {
/*  73:149 */     this.schedulingInfo = schedulingInfo;
/*  74:    */   }
/*  75:    */   
/*  76:    */   void copySchedulingInfo(Queue sourceQueue)
/*  77:    */   {
/*  78:160 */     Set<Queue> destChildren = getChildren();
/*  79:161 */     if (destChildren != null)
/*  80:    */     {
/*  81:162 */       Iterator<Queue> itr1 = destChildren.iterator();
/*  82:163 */       Iterator<Queue> itr2 = sourceQueue.getChildren().iterator();
/*  83:164 */       while (itr1.hasNext()) {
/*  84:165 */         ((Queue)itr1.next()).copySchedulingInfo((Queue)itr2.next());
/*  85:    */       }
/*  86:    */     }
/*  87:170 */     setSchedulingInfo(sourceQueue.getSchedulingInfo());
/*  88:    */   }
/*  89:    */   
/*  90:    */   void addChild(Queue child)
/*  91:    */   {
/*  92:177 */     if (this.children == null) {
/*  93:178 */       this.children = new TreeSet();
/*  94:    */     }
/*  95:181 */     this.children.add(child);
/*  96:    */   }
/*  97:    */   
/*  98:    */   Set<Queue> getChildren()
/*  99:    */   {
/* 100:189 */     return this.children;
/* 101:    */   }
/* 102:    */   
/* 103:    */   void setProperties(Properties props)
/* 104:    */   {
/* 105:197 */     this.props = props;
/* 106:    */   }
/* 107:    */   
/* 108:    */   Properties getProperties()
/* 109:    */   {
/* 110:205 */     return this.props;
/* 111:    */   }
/* 112:    */   
/* 113:    */   Map<String, Queue> getInnerQueues()
/* 114:    */   {
/* 115:220 */     Map<String, Queue> l = new HashMap();
/* 116:224 */     if (this.children == null) {
/* 117:225 */       return l;
/* 118:    */     }
/* 119:229 */     for (Queue child : this.children) {
/* 120:231 */       if ((child.getChildren() != null) && (child.getChildren().size() > 0))
/* 121:    */       {
/* 122:232 */         l.put(child.getName(), child);
/* 123:233 */         l.putAll(child.getInnerQueues());
/* 124:    */       }
/* 125:    */     }
/* 126:236 */     return l;
/* 127:    */   }
/* 128:    */   
/* 129:    */   Map<String, Queue> getLeafQueues()
/* 130:    */   {
/* 131:251 */     Map<String, Queue> l = new HashMap();
/* 132:252 */     if (this.children == null)
/* 133:    */     {
/* 134:253 */       l.put(this.name, this);
/* 135:254 */       return l;
/* 136:    */     }
/* 137:257 */     for (Queue child : this.children) {
/* 138:258 */       l.putAll(child.getLeafQueues());
/* 139:    */     }
/* 140:260 */     return l;
/* 141:    */   }
/* 142:    */   
/* 143:    */   public int compareTo(Queue queue)
/* 144:    */   {
/* 145:266 */     return this.name.compareTo(queue.getName());
/* 146:    */   }
/* 147:    */   
/* 148:    */   public boolean equals(Object o)
/* 149:    */   {
/* 150:271 */     if (o == this) {
/* 151:272 */       return true;
/* 152:    */     }
/* 153:274 */     if (!(o instanceof Queue)) {
/* 154:275 */       return false;
/* 155:    */     }
/* 156:278 */     return ((Queue)o).getName().equals(this.name);
/* 157:    */   }
/* 158:    */   
/* 159:    */   public String toString()
/* 160:    */   {
/* 161:283 */     return getName();
/* 162:    */   }
/* 163:    */   
/* 164:    */   public int hashCode()
/* 165:    */   {
/* 166:288 */     return getName().hashCode();
/* 167:    */   }
/* 168:    */   
/* 169:    */   JobQueueInfo getJobQueueInfo()
/* 170:    */   {
/* 171:298 */     JobQueueInfo queueInfo = new JobQueueInfo();
/* 172:299 */     queueInfo.setQueueName(this.name);
/* 173:300 */     LOG.debug("created jobQInfo " + queueInfo.getQueueName());
/* 174:301 */     queueInfo.setQueueState(this.state.getStateName());
/* 175:302 */     if (this.schedulingInfo != null) {
/* 176:303 */       queueInfo.setSchedulingInfo(this.schedulingInfo.toString());
/* 177:    */     }
/* 178:306 */     if (this.props != null)
/* 179:    */     {
/* 180:308 */       Properties newProps = new Properties();
/* 181:309 */       for (Object key : this.props.keySet()) {
/* 182:310 */         newProps.setProperty(key.toString(), this.props.getProperty(key.toString()));
/* 183:    */       }
/* 184:312 */       queueInfo.setProperties(newProps);
/* 185:    */     }
/* 186:315 */     if ((this.children != null) && (this.children.size() > 0))
/* 187:    */     {
/* 188:316 */       List<JobQueueInfo> list = new ArrayList();
/* 189:317 */       for (Queue child : this.children) {
/* 190:318 */         list.add(child.getJobQueueInfo());
/* 191:    */       }
/* 192:320 */       queueInfo.setChildren(list);
/* 193:    */     }
/* 194:322 */     return queueInfo;
/* 195:    */   }
/* 196:    */   
/* 197:    */   boolean isHierarchySameAs(Queue newState)
/* 198:    */   {
/* 199:333 */     if (newState == null) {
/* 200:334 */       return false;
/* 201:    */     }
/* 202:337 */     if (!this.name.equals(newState.getName()))
/* 203:    */     {
/* 204:338 */       LOG.info(" current name " + this.name + " not equal to " + newState.getName());
/* 205:339 */       return false;
/* 206:    */     }
/* 207:342 */     if ((this.children == null) || (this.children.size() == 0))
/* 208:    */     {
/* 209:343 */       if ((newState.getChildren() != null) && (newState.getChildren().size() > 0))
/* 210:    */       {
/* 211:344 */         LOG.info(newState + " has added children in refresh ");
/* 212:345 */         return false;
/* 213:    */       }
/* 214:    */     }
/* 215:347 */     else if (this.children.size() > 0)
/* 216:    */     {
/* 217:350 */       if (newState.getChildren() == null)
/* 218:    */       {
/* 219:351 */         LOG.fatal("In the current state, queue " + getName() + " has " + this.children.size() + " but the new state has none!");
/* 220:    */         
/* 221:353 */         return false;
/* 222:    */       }
/* 223:355 */       int childrenSize = this.children.size();
/* 224:356 */       int newChildrenSize = newState.getChildren().size();
/* 225:357 */       if (childrenSize != newChildrenSize)
/* 226:    */       {
/* 227:358 */         LOG.fatal("Number of children for queue " + newState.getName() + " in newState is " + newChildrenSize + " which is not equal to " + childrenSize + " in the current state.");
/* 228:    */         
/* 229:    */ 
/* 230:361 */         return false;
/* 231:    */       }
/* 232:365 */       Iterator<Queue> itr1 = this.children.iterator();
/* 233:366 */       Iterator<Queue> itr2 = newState.getChildren().iterator();
/* 234:368 */       while (itr1.hasNext())
/* 235:    */       {
/* 236:369 */         Queue q = (Queue)itr1.next();
/* 237:370 */         Queue newq = (Queue)itr2.next();
/* 238:371 */         if (!q.isHierarchySameAs(newq))
/* 239:    */         {
/* 240:372 */           LOG.info(" Queue " + q.getName() + " not equal to " + newq.getName());
/* 241:373 */           return false;
/* 242:    */         }
/* 243:    */       }
/* 244:    */     }
/* 245:377 */     return true;
/* 246:    */   }
/* 247:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.Queue
 * JD-Core Version:    0.7.0.1
 */