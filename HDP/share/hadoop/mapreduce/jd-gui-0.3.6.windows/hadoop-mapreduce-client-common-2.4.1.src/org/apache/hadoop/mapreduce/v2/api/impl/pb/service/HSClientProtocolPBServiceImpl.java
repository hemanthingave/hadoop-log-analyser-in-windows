/*  1:   */ package org.apache.hadoop.mapreduce.v2.api.impl.pb.service;
/*  2:   */ 
/*  3:   */ import org.apache.hadoop.mapreduce.v2.api.HSClientProtocol;
/*  4:   */ import org.apache.hadoop.mapreduce.v2.api.HSClientProtocolPB;
/*  5:   */ 
/*  6:   */ public class HSClientProtocolPBServiceImpl
/*  7:   */   extends MRClientProtocolPBServiceImpl
/*  8:   */   implements HSClientProtocolPB
/*  9:   */ {
/* 10:   */   public HSClientProtocolPBServiceImpl(HSClientProtocol impl)
/* 11:   */   {
/* 12:27 */     super(impl);
/* 13:   */   }
/* 14:   */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-common-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.v2.api.impl.pb.service.HSClientProtocolPBServiceImpl
 * JD-Core Version:    0.7.0.1
 */