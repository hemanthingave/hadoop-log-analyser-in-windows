/*  1:   */ package org.apache.hadoop.mapreduce.v2.security.client;
/*  2:   */ 
/*  3:   */ import java.util.Collection;
/*  4:   */ import org.apache.commons.logging.Log;
/*  5:   */ import org.apache.commons.logging.LogFactory;
/*  6:   */ import org.apache.hadoop.io.Text;
/*  7:   */ import org.apache.hadoop.mapreduce.v2.api.MRDelegationTokenIdentifier;
/*  8:   */ import org.apache.hadoop.security.token.Token;
/*  9:   */ import org.apache.hadoop.security.token.TokenIdentifier;
/* 10:   */ import org.apache.hadoop.security.token.TokenSelector;
/* 11:   */ 
/* 12:   */ public class ClientHSTokenSelector
/* 13:   */   implements TokenSelector<MRDelegationTokenIdentifier>
/* 14:   */ {
/* 15:34 */   private static final Log LOG = LogFactory.getLog(ClientHSTokenSelector.class);
/* 16:   */   
/* 17:   */   public Token<MRDelegationTokenIdentifier> selectToken(Text service, Collection<Token<? extends TokenIdentifier>> tokens)
/* 18:   */   {
/* 19:40 */     if (service == null) {
/* 20:41 */       return null;
/* 21:   */     }
/* 22:43 */     LOG.debug("Looking for a token with service " + service.toString());
/* 23:44 */     for (Token<? extends TokenIdentifier> token : tokens)
/* 24:   */     {
/* 25:45 */       if (LOG.isDebugEnabled()) {
/* 26:46 */         LOG.debug("Token kind is " + token.getKind().toString() + " and the token's service name is " + token.getService());
/* 27:   */       }
/* 28:49 */       if ((MRDelegationTokenIdentifier.KIND_NAME.equals(token.getKind())) && (service.equals(token.getService()))) {
/* 29:51 */         return token;
/* 30:   */       }
/* 31:   */     }
/* 32:54 */     return null;
/* 33:   */   }
/* 34:   */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-common-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.v2.security.client.ClientHSTokenSelector
 * JD-Core Version:    0.7.0.1
 */