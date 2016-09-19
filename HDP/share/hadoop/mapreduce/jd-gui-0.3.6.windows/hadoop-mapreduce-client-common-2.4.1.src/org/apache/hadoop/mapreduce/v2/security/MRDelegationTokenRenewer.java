/*   1:    */ package org.apache.hadoop.mapreduce.v2.security;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.net.InetSocketAddress;
/*   5:    */ import java.security.PrivilegedAction;
/*   6:    */ import org.apache.commons.logging.Log;
/*   7:    */ import org.apache.commons.logging.LogFactory;
/*   8:    */ import org.apache.hadoop.classification.InterfaceAudience.Private;
/*   9:    */ import org.apache.hadoop.conf.Configuration;
/*  10:    */ import org.apache.hadoop.io.Text;
/*  11:    */ import org.apache.hadoop.ipc.RPC;
/*  12:    */ import org.apache.hadoop.mapreduce.v2.api.HSClientProtocol;
/*  13:    */ import org.apache.hadoop.mapreduce.v2.api.MRClientProtocol;
/*  14:    */ import org.apache.hadoop.mapreduce.v2.api.MRDelegationTokenIdentifier;
/*  15:    */ import org.apache.hadoop.mapreduce.v2.api.protocolrecords.CancelDelegationTokenRequest;
/*  16:    */ import org.apache.hadoop.mapreduce.v2.api.protocolrecords.RenewDelegationTokenRequest;
/*  17:    */ import org.apache.hadoop.mapreduce.v2.api.protocolrecords.RenewDelegationTokenResponse;
/*  18:    */ import org.apache.hadoop.security.SecurityUtil;
/*  19:    */ import org.apache.hadoop.security.UserGroupInformation;
/*  20:    */ import org.apache.hadoop.security.token.TokenRenewer;
/*  21:    */ import org.apache.hadoop.yarn.ipc.YarnRPC;
/*  22:    */ import org.apache.hadoop.yarn.util.Records;
/*  23:    */ 
/*  24:    */ @InterfaceAudience.Private
/*  25:    */ public class MRDelegationTokenRenewer
/*  26:    */   extends TokenRenewer
/*  27:    */ {
/*  28: 45 */   private static final Log LOG = LogFactory.getLog(MRDelegationTokenRenewer.class);
/*  29:    */   
/*  30:    */   public boolean handleKind(Text kind)
/*  31:    */   {
/*  32: 50 */     return MRDelegationTokenIdentifier.KIND_NAME.equals(kind);
/*  33:    */   }
/*  34:    */   
/*  35:    */   public long renew(org.apache.hadoop.security.token.Token<?> token, Configuration conf)
/*  36:    */     throws IOException, InterruptedException
/*  37:    */   {
/*  38: 57 */     org.apache.hadoop.yarn.api.records.Token dToken = org.apache.hadoop.yarn.api.records.Token.newInstance(token.getIdentifier(), token.getKind().toString(), token.getPassword(), token.getService().toString());
/*  39:    */     
/*  40:    */ 
/*  41:    */ 
/*  42:    */ 
/*  43: 62 */     MRClientProtocol histProxy = instantiateHistoryProxy(conf, SecurityUtil.getTokenServiceAddr(token));
/*  44:    */     try
/*  45:    */     {
/*  46: 65 */       RenewDelegationTokenRequest request = (RenewDelegationTokenRequest)Records.newRecord(RenewDelegationTokenRequest.class);
/*  47:    */       
/*  48: 67 */       request.setDelegationToken(dToken);
/*  49: 68 */       return histProxy.renewDelegationToken(request).getNextExpirationTime();
/*  50:    */     }
/*  51:    */     finally
/*  52:    */     {
/*  53: 70 */       stopHistoryProxy(histProxy);
/*  54:    */     }
/*  55:    */   }
/*  56:    */   
/*  57:    */   public void cancel(org.apache.hadoop.security.token.Token<?> token, Configuration conf)
/*  58:    */     throws IOException, InterruptedException
/*  59:    */   {
/*  60: 79 */     org.apache.hadoop.yarn.api.records.Token dToken = org.apache.hadoop.yarn.api.records.Token.newInstance(token.getIdentifier(), token.getKind().toString(), token.getPassword(), token.getService().toString());
/*  61:    */     
/*  62:    */ 
/*  63:    */ 
/*  64:    */ 
/*  65: 84 */     MRClientProtocol histProxy = instantiateHistoryProxy(conf, SecurityUtil.getTokenServiceAddr(token));
/*  66:    */     try
/*  67:    */     {
/*  68: 87 */       CancelDelegationTokenRequest request = (CancelDelegationTokenRequest)Records.newRecord(CancelDelegationTokenRequest.class);
/*  69:    */       
/*  70: 89 */       request.setDelegationToken(dToken);
/*  71: 90 */       histProxy.cancelDelegationToken(request);
/*  72:    */     }
/*  73:    */     finally
/*  74:    */     {
/*  75: 92 */       stopHistoryProxy(histProxy);
/*  76:    */     }
/*  77:    */   }
/*  78:    */   
/*  79:    */   public boolean isManaged(org.apache.hadoop.security.token.Token<?> token)
/*  80:    */     throws IOException
/*  81:    */   {
/*  82: 98 */     return true;
/*  83:    */   }
/*  84:    */   
/*  85:    */   protected void stopHistoryProxy(MRClientProtocol proxy)
/*  86:    */   {
/*  87:102 */     RPC.stopProxy(proxy);
/*  88:    */   }
/*  89:    */   
/*  90:    */   protected MRClientProtocol instantiateHistoryProxy(final Configuration conf, final InetSocketAddress hsAddress)
/*  91:    */     throws IOException
/*  92:    */   {
/*  93:108 */     if (LOG.isDebugEnabled()) {
/*  94:109 */       LOG.debug("Connecting to MRHistoryServer at: " + hsAddress);
/*  95:    */     }
/*  96:111 */     final YarnRPC rpc = YarnRPC.create(conf);
/*  97:112 */     UserGroupInformation currentUser = UserGroupInformation.getCurrentUser();
/*  98:113 */     (MRClientProtocol)currentUser.doAs(new PrivilegedAction()
/*  99:    */     {
/* 100:    */       public MRClientProtocol run()
/* 101:    */       {
/* 102:116 */         return (MRClientProtocol)rpc.getProxy(HSClientProtocol.class, hsAddress, conf);
/* 103:    */       }
/* 104:    */     });
/* 105:    */   }
/* 106:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-common-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.v2.security.MRDelegationTokenRenewer
 * JD-Core Version:    0.7.0.1
 */