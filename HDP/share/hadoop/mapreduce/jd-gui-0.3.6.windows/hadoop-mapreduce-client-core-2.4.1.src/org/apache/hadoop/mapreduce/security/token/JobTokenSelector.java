/*  1:   */ package org.apache.hadoop.mapreduce.security.token;
/*  2:   */ 
/*  3:   */ import java.util.Collection;
/*  4:   */ import org.apache.hadoop.classification.InterfaceAudience.Private;
/*  5:   */ import org.apache.hadoop.classification.InterfaceStability.Unstable;
/*  6:   */ import org.apache.hadoop.io.Text;
/*  7:   */ import org.apache.hadoop.security.token.Token;
/*  8:   */ import org.apache.hadoop.security.token.TokenIdentifier;
/*  9:   */ import org.apache.hadoop.security.token.TokenSelector;
/* 10:   */ 
/* 11:   */ @InterfaceAudience.Private
/* 12:   */ @InterfaceStability.Unstable
/* 13:   */ public class JobTokenSelector
/* 14:   */   implements TokenSelector<JobTokenIdentifier>
/* 15:   */ {
/* 16:   */   public Token<JobTokenIdentifier> selectToken(Text service, Collection<Token<? extends TokenIdentifier>> tokens)
/* 17:   */   {
/* 18:42 */     if (service == null) {
/* 19:43 */       return null;
/* 20:   */     }
/* 21:45 */     for (Token<? extends TokenIdentifier> token : tokens) {
/* 22:46 */       if ((JobTokenIdentifier.KIND_NAME.equals(token.getKind())) && (service.equals(token.getService()))) {
/* 23:48 */         return token;
/* 24:   */       }
/* 25:   */     }
/* 26:51 */     return null;
/* 27:   */   }
/* 28:   */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.security.token.JobTokenSelector
 * JD-Core Version:    0.7.0.1
 */