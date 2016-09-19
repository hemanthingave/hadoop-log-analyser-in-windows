/*  1:   */ package org.apache.hadoop.mapreduce.v2.api.impl.pb.client;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import java.net.InetSocketAddress;
/*  5:   */ import org.apache.hadoop.conf.Configuration;
/*  6:   */ import org.apache.hadoop.ipc.ProtobufRpcEngine;
/*  7:   */ import org.apache.hadoop.ipc.RPC;
/*  8:   */ import org.apache.hadoop.mapreduce.v2.api.HSClientProtocol;
/*  9:   */ import org.apache.hadoop.mapreduce.v2.api.HSClientProtocolPB;
/* 10:   */ 
/* 11:   */ public class HSClientProtocolPBClientImpl
/* 12:   */   extends MRClientProtocolPBClientImpl
/* 13:   */   implements HSClientProtocol
/* 14:   */ {
/* 15:   */   public HSClientProtocolPBClientImpl(long clientVersion, InetSocketAddress addr, Configuration conf)
/* 16:   */     throws IOException
/* 17:   */   {
/* 18:36 */     RPC.setProtocolEngine(conf, HSClientProtocolPB.class, ProtobufRpcEngine.class);
/* 19:   */     
/* 20:38 */     this.proxy = ((HSClientProtocolPB)RPC.getProxy(HSClientProtocolPB.class, clientVersion, addr, conf));
/* 21:   */   }
/* 22:   */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-common-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.v2.api.impl.pb.client.HSClientProtocolPBClientImpl
 * JD-Core Version:    0.7.0.1
 */