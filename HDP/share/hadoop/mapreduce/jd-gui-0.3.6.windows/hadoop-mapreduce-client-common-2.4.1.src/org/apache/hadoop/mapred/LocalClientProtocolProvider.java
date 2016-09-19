/*  1:   */ package org.apache.hadoop.mapred;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import java.net.InetSocketAddress;
/*  5:   */ import org.apache.hadoop.classification.InterfaceAudience.Private;
/*  6:   */ import org.apache.hadoop.conf.Configuration;
/*  7:   */ import org.apache.hadoop.mapreduce.protocol.ClientProtocol;
/*  8:   */ import org.apache.hadoop.mapreduce.protocol.ClientProtocolProvider;
/*  9:   */ 
/* 10:   */ @InterfaceAudience.Private
/* 11:   */ public class LocalClientProtocolProvider
/* 12:   */   extends ClientProtocolProvider
/* 13:   */ {
/* 14:   */   public ClientProtocol create(Configuration conf)
/* 15:   */     throws IOException
/* 16:   */   {
/* 17:35 */     String framework = conf.get("mapreduce.framework.name", "local");
/* 18:37 */     if (!"local".equals(framework)) {
/* 19:38 */       return null;
/* 20:   */     }
/* 21:40 */     conf.setInt("mapreduce.job.maps", 1);
/* 22:   */     
/* 23:42 */     return new LocalJobRunner(conf);
/* 24:   */   }
/* 25:   */   
/* 26:   */   public ClientProtocol create(InetSocketAddress addr, Configuration conf)
/* 27:   */   {
/* 28:47 */     return null;
/* 29:   */   }
/* 30:   */   
/* 31:   */   public void close(ClientProtocol clientProtocol) {}
/* 32:   */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-common-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.LocalClientProtocolProvider
 * JD-Core Version:    0.7.0.1
 */