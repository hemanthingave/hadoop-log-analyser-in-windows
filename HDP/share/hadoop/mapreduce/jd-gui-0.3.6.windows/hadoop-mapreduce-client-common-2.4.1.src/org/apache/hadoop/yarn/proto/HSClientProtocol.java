/*  1:   */ package org.apache.hadoop.yarn.proto;
/*  2:   */ 
/*  3:   */ import com.google.protobuf.BlockingService;
/*  4:   */ import org.apache.hadoop.mapreduce.v2.api.MRClientProtocolPB;
/*  5:   */ 
/*  6:   */ public abstract interface HSClientProtocol
/*  7:   */ {
/*  8:   */   public static abstract class HSClientProtocolService
/*  9:   */   {
/* 10:   */     public static BlockingService newReflectiveBlockingService(BlockingInterface impl)
/* 11:   */     {
/* 12:36 */       return MRClientProtocol.MRClientProtocolService.newReflectiveBlockingService(impl);
/* 13:   */     }
/* 14:   */     
/* 15:   */     public static abstract interface BlockingInterface
/* 16:   */       extends MRClientProtocolPB
/* 17:   */     {}
/* 18:   */   }
/* 19:   */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-common-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.yarn.proto.HSClientProtocol
 * JD-Core Version:    0.7.0.1
 */