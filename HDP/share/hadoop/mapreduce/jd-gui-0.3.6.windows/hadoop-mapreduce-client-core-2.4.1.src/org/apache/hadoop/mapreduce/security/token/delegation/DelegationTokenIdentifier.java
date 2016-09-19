/*  1:   */ package org.apache.hadoop.mapreduce.security.token.delegation;
/*  2:   */ 
/*  3:   */ import org.apache.hadoop.classification.InterfaceAudience.Private;
/*  4:   */ import org.apache.hadoop.classification.InterfaceStability.Unstable;
/*  5:   */ import org.apache.hadoop.io.Text;
/*  6:   */ import org.apache.hadoop.security.token.delegation.AbstractDelegationTokenIdentifier;
/*  7:   */ 
/*  8:   */ @InterfaceAudience.Private
/*  9:   */ @InterfaceStability.Unstable
/* 10:   */ public class DelegationTokenIdentifier
/* 11:   */   extends AbstractDelegationTokenIdentifier
/* 12:   */ {
/* 13:33 */   public static final Text MAPREDUCE_DELEGATION_KIND = new Text("MAPREDUCE_DELEGATION_TOKEN");
/* 14:   */   
/* 15:   */   public DelegationTokenIdentifier() {}
/* 16:   */   
/* 17:   */   public DelegationTokenIdentifier(Text owner, Text renewer, Text realUser)
/* 18:   */   {
/* 19:49 */     super(owner, renewer, realUser);
/* 20:   */   }
/* 21:   */   
/* 22:   */   public Text getKind()
/* 23:   */   {
/* 24:54 */     return MAPREDUCE_DELEGATION_KIND;
/* 25:   */   }
/* 26:   */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.security.token.delegation.DelegationTokenIdentifier
 * JD-Core Version:    0.7.0.1
 */