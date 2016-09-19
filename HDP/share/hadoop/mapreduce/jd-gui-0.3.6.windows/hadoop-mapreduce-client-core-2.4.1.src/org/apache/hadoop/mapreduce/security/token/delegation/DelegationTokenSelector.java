/*  1:   */ package org.apache.hadoop.mapreduce.security.token.delegation;
/*  2:   */ 
/*  3:   */ import org.apache.hadoop.classification.InterfaceAudience.Private;
/*  4:   */ import org.apache.hadoop.classification.InterfaceStability.Unstable;
/*  5:   */ import org.apache.hadoop.security.token.delegation.AbstractDelegationTokenSelector;
/*  6:   */ 
/*  7:   */ @InterfaceAudience.Private
/*  8:   */ @InterfaceStability.Unstable
/*  9:   */ public class DelegationTokenSelector
/* 10:   */   extends AbstractDelegationTokenSelector<DelegationTokenIdentifier>
/* 11:   */ {
/* 12:   */   public DelegationTokenSelector()
/* 13:   */   {
/* 14:33 */     super(DelegationTokenIdentifier.MAPREDUCE_DELEGATION_KIND);
/* 15:   */   }
/* 16:   */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.security.token.delegation.DelegationTokenSelector
 * JD-Core Version:    0.7.0.1
 */