/*  1:   */ package org.apache.hadoop.mapreduce.security.token.delegation;
/*  2:   */ 
/*  3:   */ import org.apache.hadoop.classification.InterfaceAudience.Private;
/*  4:   */ import org.apache.hadoop.classification.InterfaceStability.Unstable;
/*  5:   */ import org.apache.hadoop.security.token.delegation.AbstractDelegationTokenSecretManager;
/*  6:   */ 
/*  7:   */ @InterfaceAudience.Private
/*  8:   */ @InterfaceStability.Unstable
/*  9:   */ public class DelegationTokenSecretManager
/* 10:   */   extends AbstractDelegationTokenSecretManager<DelegationTokenIdentifier>
/* 11:   */ {
/* 12:   */   public DelegationTokenSecretManager(long delegationKeyUpdateInterval, long delegationTokenMaxLifetime, long delegationTokenRenewInterval, long delegationTokenRemoverScanInterval)
/* 13:   */   {
/* 14:49 */     super(delegationKeyUpdateInterval, delegationTokenMaxLifetime, delegationTokenRenewInterval, delegationTokenRemoverScanInterval);
/* 15:   */   }
/* 16:   */   
/* 17:   */   public DelegationTokenIdentifier createIdentifier()
/* 18:   */   {
/* 19:55 */     return new DelegationTokenIdentifier();
/* 20:   */   }
/* 21:   */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.security.token.delegation.DelegationTokenSecretManager
 * JD-Core Version:    0.7.0.1
 */