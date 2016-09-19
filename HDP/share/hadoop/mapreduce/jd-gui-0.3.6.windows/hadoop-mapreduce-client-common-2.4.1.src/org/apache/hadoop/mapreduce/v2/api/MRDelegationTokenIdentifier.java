/*  1:   */ package org.apache.hadoop.mapreduce.v2.api;
/*  2:   */ 
/*  3:   */ import org.apache.hadoop.classification.InterfaceAudience.Private;
/*  4:   */ import org.apache.hadoop.io.Text;
/*  5:   */ import org.apache.hadoop.security.token.Token.TrivialRenewer;
/*  6:   */ import org.apache.hadoop.security.token.delegation.AbstractDelegationTokenIdentifier;
/*  7:   */ 
/*  8:   */ @InterfaceAudience.Private
/*  9:   */ public class MRDelegationTokenIdentifier
/* 10:   */   extends AbstractDelegationTokenIdentifier
/* 11:   */ {
/* 12:38 */   public static final Text KIND_NAME = new Text("MR_DELEGATION_TOKEN");
/* 13:   */   
/* 14:   */   public MRDelegationTokenIdentifier() {}
/* 15:   */   
/* 16:   */   public MRDelegationTokenIdentifier(Text owner, Text renewer, Text realUser)
/* 17:   */   {
/* 18:51 */     super(owner, renewer, realUser);
/* 19:   */   }
/* 20:   */   
/* 21:   */   public Text getKind()
/* 22:   */   {
/* 23:57 */     return KIND_NAME;
/* 24:   */   }
/* 25:   */   
/* 26:   */   @InterfaceAudience.Private
/* 27:   */   public static class Renewer
/* 28:   */     extends Token.TrivialRenewer
/* 29:   */   {
/* 30:   */     protected Text getKind()
/* 31:   */     {
/* 32:64 */       return MRDelegationTokenIdentifier.KIND_NAME;
/* 33:   */     }
/* 34:   */   }
/* 35:   */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-common-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.v2.api.MRDelegationTokenIdentifier
 * JD-Core Version:    0.7.0.1
 */