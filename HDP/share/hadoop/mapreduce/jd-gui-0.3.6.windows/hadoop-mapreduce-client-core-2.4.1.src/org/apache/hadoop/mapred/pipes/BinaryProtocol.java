/*   1:    */ package org.apache.hadoop.mapred.pipes;
/*   2:    */ 
/*   3:    */ import java.io.BufferedInputStream;
/*   4:    */ import java.io.BufferedOutputStream;
/*   5:    */ import java.io.DataInputStream;
/*   6:    */ import java.io.DataOutputStream;
/*   7:    */ import java.io.FileOutputStream;
/*   8:    */ import java.io.FilterOutputStream;
/*   9:    */ import java.io.IOException;
/*  10:    */ import java.io.InputStream;
/*  11:    */ import java.io.OutputStream;
/*  12:    */ import java.net.Socket;
/*  13:    */ import java.util.ArrayList;
/*  14:    */ import java.util.List;
/*  15:    */ import java.util.Map.Entry;
/*  16:    */ import org.apache.commons.logging.Log;
/*  17:    */ import org.apache.commons.logging.LogFactory;
/*  18:    */ import org.apache.hadoop.io.BytesWritable;
/*  19:    */ import org.apache.hadoop.io.DataOutputBuffer;
/*  20:    */ import org.apache.hadoop.io.Text;
/*  21:    */ import org.apache.hadoop.io.Writable;
/*  22:    */ import org.apache.hadoop.io.WritableComparable;
/*  23:    */ import org.apache.hadoop.io.WritableUtils;
/*  24:    */ import org.apache.hadoop.mapred.InputSplit;
/*  25:    */ import org.apache.hadoop.mapred.JobConf;
/*  26:    */ import org.apache.hadoop.util.StringUtils;
/*  27:    */ 
/*  28:    */ class BinaryProtocol<K1 extends WritableComparable, V1 extends Writable, K2 extends WritableComparable, V2 extends Writable>
/*  29:    */   implements DownwardProtocol<K1, V1>
/*  30:    */ {
/*  31:    */   public static final int CURRENT_PROTOCOL_VERSION = 0;
/*  32:    */   private static final int BUFFER_SIZE = 131072;
/*  33:    */   private DataOutputStream stream;
/*  34: 61 */   private DataOutputBuffer buffer = new DataOutputBuffer();
/*  35: 62 */   private static final Log LOG = LogFactory.getLog(BinaryProtocol.class.getName());
/*  36:    */   private UplinkReaderThread uplink;
/*  37:    */   
/*  38:    */   private static enum MessageType
/*  39:    */   {
/*  40: 70 */     START(0),  SET_JOB_CONF(1),  SET_INPUT_TYPES(2),  RUN_MAP(3),  MAP_ITEM(4),  RUN_REDUCE(5),  REDUCE_KEY(6),  REDUCE_VALUE(7),  CLOSE(8),  ABORT(9),  AUTHENTICATION_REQ(10),  OUTPUT(50),  PARTITIONED_OUTPUT(51),  STATUS(52),  PROGRESS(53),  DONE(54),  REGISTER_COUNTER(55),  INCREMENT_COUNTER(56),  AUTHENTICATION_RESP(57);
/*  41:    */     
/*  42:    */     final int code;
/*  43:    */     
/*  44:    */     private MessageType(int code)
/*  45:    */     {
/*  46: 91 */       this.code = code;
/*  47:    */     }
/*  48:    */   }
/*  49:    */   
/*  50:    */   private static class UplinkReaderThread<K2 extends WritableComparable, V2 extends Writable>
/*  51:    */     extends Thread
/*  52:    */   {
/*  53:    */     private DataInputStream inStream;
/*  54:    */     private UpwardProtocol<K2, V2> handler;
/*  55:    */     private K2 key;
/*  56:    */     private V2 value;
/*  57:103 */     private boolean authPending = true;
/*  58:    */     
/*  59:    */     public UplinkReaderThread(InputStream stream, UpwardProtocol<K2, V2> handler, K2 key, V2 value)
/*  60:    */       throws IOException
/*  61:    */     {
/*  62:108 */       this.inStream = new DataInputStream(new BufferedInputStream(stream, 131072));
/*  63:    */       
/*  64:110 */       this.handler = handler;
/*  65:111 */       this.key = key;
/*  66:112 */       this.value = value;
/*  67:    */     }
/*  68:    */     
/*  69:    */     public void closeConnection()
/*  70:    */       throws IOException
/*  71:    */     {
/*  72:116 */       this.inStream.close();
/*  73:    */     }
/*  74:    */     
/*  75:    */     public void run()
/*  76:    */     {
/*  77:    */       try
/*  78:    */       {
/*  79:    */         for (;;)
/*  80:    */         {
/*  81:122 */           if (Thread.currentThread().isInterrupted()) {
/*  82:123 */             throw new InterruptedException();
/*  83:    */           }
/*  84:125 */           int cmd = WritableUtils.readVInt(this.inStream);
/*  85:126 */           BinaryProtocol.LOG.debug("Handling uplink command " + cmd);
/*  86:127 */           if (cmd == BinaryProtocol.MessageType.AUTHENTICATION_RESP.code)
/*  87:    */           {
/*  88:128 */             String digest = Text.readString(this.inStream);
/*  89:129 */             this.authPending = (!this.handler.authenticate(digest));
/*  90:    */           }
/*  91:130 */           else if (this.authPending)
/*  92:    */           {
/*  93:131 */             BinaryProtocol.LOG.warn("Message " + cmd + " received before authentication is " + "complete. Ignoring");
/*  94:    */           }
/*  95:134 */           else if (cmd == BinaryProtocol.MessageType.OUTPUT.code)
/*  96:    */           {
/*  97:135 */             readObject(this.key);
/*  98:136 */             readObject(this.value);
/*  99:137 */             this.handler.output(this.key, this.value);
/* 100:    */           }
/* 101:138 */           else if (cmd == BinaryProtocol.MessageType.PARTITIONED_OUTPUT.code)
/* 102:    */           {
/* 103:139 */             int part = WritableUtils.readVInt(this.inStream);
/* 104:140 */             readObject(this.key);
/* 105:141 */             readObject(this.value);
/* 106:142 */             this.handler.partitionedOutput(part, this.key, this.value);
/* 107:    */           }
/* 108:143 */           else if (cmd == BinaryProtocol.MessageType.STATUS.code)
/* 109:    */           {
/* 110:144 */             this.handler.status(Text.readString(this.inStream));
/* 111:    */           }
/* 112:145 */           else if (cmd == BinaryProtocol.MessageType.PROGRESS.code)
/* 113:    */           {
/* 114:146 */             this.handler.progress(this.inStream.readFloat());
/* 115:    */           }
/* 116:147 */           else if (cmd == BinaryProtocol.MessageType.REGISTER_COUNTER.code)
/* 117:    */           {
/* 118:148 */             int id = WritableUtils.readVInt(this.inStream);
/* 119:149 */             String group = Text.readString(this.inStream);
/* 120:150 */             String name = Text.readString(this.inStream);
/* 121:151 */             this.handler.registerCounter(id, group, name);
/* 122:    */           }
/* 123:152 */           else if (cmd == BinaryProtocol.MessageType.INCREMENT_COUNTER.code)
/* 124:    */           {
/* 125:153 */             int id = WritableUtils.readVInt(this.inStream);
/* 126:154 */             long amount = WritableUtils.readVLong(this.inStream);
/* 127:155 */             this.handler.incrementCounter(id, amount);
/* 128:    */           }
/* 129:    */           else
/* 130:    */           {
/* 131:156 */             if (cmd == BinaryProtocol.MessageType.DONE.code)
/* 132:    */             {
/* 133:157 */               BinaryProtocol.LOG.debug("Pipe child done");
/* 134:158 */               this.handler.done();
/* 135:159 */               return;
/* 136:    */             }
/* 137:161 */             throw new IOException("Bad command code: " + cmd);
/* 138:    */           }
/* 139:    */         }
/* 140:168 */         return;
/* 141:    */       }
/* 142:    */       catch (InterruptedException e)
/* 143:    */       {
/* 144:164 */         return;
/* 145:    */       }
/* 146:    */       catch (Throwable e)
/* 147:    */       {
/* 148:166 */         BinaryProtocol.LOG.error(StringUtils.stringifyException(e));
/* 149:167 */         this.handler.failed(e);
/* 150:    */       }
/* 151:    */     }
/* 152:    */     
/* 153:    */     private void readObject(Writable obj)
/* 154:    */       throws IOException
/* 155:    */     {
/* 156:174 */       int numBytes = WritableUtils.readVInt(this.inStream);
/* 157:179 */       if ((obj instanceof BytesWritable))
/* 158:    */       {
/* 159:180 */         byte[] buffer = new byte[numBytes];
/* 160:181 */         this.inStream.readFully(buffer);
/* 161:182 */         ((BytesWritable)obj).set(buffer, 0, numBytes);
/* 162:    */       }
/* 163:183 */       else if ((obj instanceof Text))
/* 164:    */       {
/* 165:184 */         byte[] buffer = new byte[numBytes];
/* 166:185 */         this.inStream.readFully(buffer);
/* 167:186 */         ((Text)obj).set(buffer);
/* 168:    */       }
/* 169:    */       else
/* 170:    */       {
/* 171:188 */         obj.readFields(this.inStream);
/* 172:    */       }
/* 173:    */     }
/* 174:    */   }
/* 175:    */   
/* 176:    */   private static class TeeOutputStream
/* 177:    */     extends FilterOutputStream
/* 178:    */   {
/* 179:    */     private OutputStream file;
/* 180:    */     
/* 181:    */     TeeOutputStream(String filename, OutputStream base)
/* 182:    */       throws IOException
/* 183:    */     {
/* 184:199 */       super();
/* 185:200 */       this.file = new FileOutputStream(filename);
/* 186:    */     }
/* 187:    */     
/* 188:    */     public void write(byte[] b, int off, int len)
/* 189:    */       throws IOException
/* 190:    */     {
/* 191:203 */       this.file.write(b, off, len);
/* 192:204 */       this.out.write(b, off, len);
/* 193:    */     }
/* 194:    */     
/* 195:    */     public void write(int b)
/* 196:    */       throws IOException
/* 197:    */     {
/* 198:208 */       this.file.write(b);
/* 199:209 */       this.out.write(b);
/* 200:    */     }
/* 201:    */     
/* 202:    */     public void flush()
/* 203:    */       throws IOException
/* 204:    */     {
/* 205:213 */       this.file.flush();
/* 206:214 */       this.out.flush();
/* 207:    */     }
/* 208:    */     
/* 209:    */     public void close()
/* 210:    */       throws IOException
/* 211:    */     {
/* 212:218 */       flush();
/* 213:219 */       this.file.close();
/* 214:220 */       this.out.close();
/* 215:    */     }
/* 216:    */   }
/* 217:    */   
/* 218:    */   public BinaryProtocol(Socket sock, UpwardProtocol<K2, V2> handler, K2 key, V2 value, JobConf config)
/* 219:    */     throws IOException
/* 220:    */   {
/* 221:240 */     OutputStream raw = sock.getOutputStream();
/* 222:242 */     if (Submitter.getKeepCommandFile(config)) {
/* 223:243 */       raw = new TeeOutputStream("downlink.data", raw);
/* 224:    */     }
/* 225:245 */     this.stream = new DataOutputStream(new BufferedOutputStream(raw, 131072));
/* 226:    */     
/* 227:247 */     this.uplink = new UplinkReaderThread(sock.getInputStream(), handler, key, value);
/* 228:    */     
/* 229:249 */     this.uplink.setName("pipe-uplink-handler");
/* 230:250 */     this.uplink.start();
/* 231:    */   }
/* 232:    */   
/* 233:    */   public void close()
/* 234:    */     throws IOException, InterruptedException
/* 235:    */   {
/* 236:259 */     LOG.debug("closing connection");
/* 237:260 */     this.stream.close();
/* 238:261 */     this.uplink.closeConnection();
/* 239:262 */     this.uplink.interrupt();
/* 240:263 */     this.uplink.join();
/* 241:    */   }
/* 242:    */   
/* 243:    */   public void authenticate(String digest, String challenge)
/* 244:    */     throws IOException
/* 245:    */   {
/* 246:268 */     LOG.debug("Sending AUTHENTICATION_REQ, digest=" + digest + ", challenge=" + challenge);
/* 247:    */     
/* 248:270 */     WritableUtils.writeVInt(this.stream, MessageType.AUTHENTICATION_REQ.code);
/* 249:271 */     Text.writeString(this.stream, digest);
/* 250:272 */     Text.writeString(this.stream, challenge);
/* 251:    */   }
/* 252:    */   
/* 253:    */   public void start()
/* 254:    */     throws IOException
/* 255:    */   {
/* 256:276 */     LOG.debug("starting downlink");
/* 257:277 */     WritableUtils.writeVInt(this.stream, MessageType.START.code);
/* 258:278 */     WritableUtils.writeVInt(this.stream, 0);
/* 259:    */   }
/* 260:    */   
/* 261:    */   public void setJobConf(JobConf job)
/* 262:    */     throws IOException
/* 263:    */   {
/* 264:282 */     WritableUtils.writeVInt(this.stream, MessageType.SET_JOB_CONF.code);
/* 265:283 */     List<String> list = new ArrayList();
/* 266:284 */     for (Map.Entry<String, String> itm : job)
/* 267:    */     {
/* 268:285 */       list.add(itm.getKey());
/* 269:286 */       list.add(itm.getValue());
/* 270:    */     }
/* 271:288 */     WritableUtils.writeVInt(this.stream, list.size());
/* 272:289 */     for (String entry : list) {
/* 273:290 */       Text.writeString(this.stream, entry);
/* 274:    */     }
/* 275:    */   }
/* 276:    */   
/* 277:    */   public void setInputTypes(String keyType, String valueType)
/* 278:    */     throws IOException
/* 279:    */   {
/* 280:296 */     WritableUtils.writeVInt(this.stream, MessageType.SET_INPUT_TYPES.code);
/* 281:297 */     Text.writeString(this.stream, keyType);
/* 282:298 */     Text.writeString(this.stream, valueType);
/* 283:    */   }
/* 284:    */   
/* 285:    */   public void runMap(InputSplit split, int numReduces, boolean pipedInput)
/* 286:    */     throws IOException
/* 287:    */   {
/* 288:303 */     WritableUtils.writeVInt(this.stream, MessageType.RUN_MAP.code);
/* 289:304 */     writeObject(split);
/* 290:305 */     WritableUtils.writeVInt(this.stream, numReduces);
/* 291:306 */     WritableUtils.writeVInt(this.stream, pipedInput ? 1 : 0);
/* 292:    */   }
/* 293:    */   
/* 294:    */   public void mapItem(WritableComparable key, Writable value)
/* 295:    */     throws IOException
/* 296:    */   {
/* 297:311 */     WritableUtils.writeVInt(this.stream, MessageType.MAP_ITEM.code);
/* 298:312 */     writeObject(key);
/* 299:313 */     writeObject(value);
/* 300:    */   }
/* 301:    */   
/* 302:    */   public void runReduce(int reduce, boolean pipedOutput)
/* 303:    */     throws IOException
/* 304:    */   {
/* 305:317 */     WritableUtils.writeVInt(this.stream, MessageType.RUN_REDUCE.code);
/* 306:318 */     WritableUtils.writeVInt(this.stream, reduce);
/* 307:319 */     WritableUtils.writeVInt(this.stream, pipedOutput ? 1 : 0);
/* 308:    */   }
/* 309:    */   
/* 310:    */   public void reduceKey(WritableComparable key)
/* 311:    */     throws IOException
/* 312:    */   {
/* 313:323 */     WritableUtils.writeVInt(this.stream, MessageType.REDUCE_KEY.code);
/* 314:324 */     writeObject(key);
/* 315:    */   }
/* 316:    */   
/* 317:    */   public void reduceValue(Writable value)
/* 318:    */     throws IOException
/* 319:    */   {
/* 320:328 */     WritableUtils.writeVInt(this.stream, MessageType.REDUCE_VALUE.code);
/* 321:329 */     writeObject(value);
/* 322:    */   }
/* 323:    */   
/* 324:    */   public void endOfInput()
/* 325:    */     throws IOException
/* 326:    */   {
/* 327:333 */     WritableUtils.writeVInt(this.stream, MessageType.CLOSE.code);
/* 328:334 */     LOG.debug("Sent close command");
/* 329:    */   }
/* 330:    */   
/* 331:    */   public void abort()
/* 332:    */     throws IOException
/* 333:    */   {
/* 334:338 */     WritableUtils.writeVInt(this.stream, MessageType.ABORT.code);
/* 335:339 */     LOG.debug("Sent abort command");
/* 336:    */   }
/* 337:    */   
/* 338:    */   public void flush()
/* 339:    */     throws IOException
/* 340:    */   {
/* 341:343 */     this.stream.flush();
/* 342:    */   }
/* 343:    */   
/* 344:    */   private void writeObject(Writable obj)
/* 345:    */     throws IOException
/* 346:    */   {
/* 347:356 */     if ((obj instanceof Text))
/* 348:    */     {
/* 349:357 */       Text t = (Text)obj;
/* 350:358 */       int len = t.getLength();
/* 351:359 */       WritableUtils.writeVInt(this.stream, len);
/* 352:360 */       this.stream.write(t.getBytes(), 0, len);
/* 353:    */     }
/* 354:361 */     else if ((obj instanceof BytesWritable))
/* 355:    */     {
/* 356:362 */       BytesWritable b = (BytesWritable)obj;
/* 357:363 */       int len = b.getLength();
/* 358:364 */       WritableUtils.writeVInt(this.stream, len);
/* 359:365 */       this.stream.write(b.getBytes(), 0, len);
/* 360:    */     }
/* 361:    */     else
/* 362:    */     {
/* 363:367 */       this.buffer.reset();
/* 364:368 */       obj.write(this.buffer);
/* 365:369 */       int length = this.buffer.getLength();
/* 366:370 */       WritableUtils.writeVInt(this.stream, length);
/* 367:371 */       this.stream.write(this.buffer.getData(), 0, length);
/* 368:    */     }
/* 369:    */   }
/* 370:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.pipes.BinaryProtocol
 * JD-Core Version:    0.7.0.1
 */