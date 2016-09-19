package org.apache.hadoop.mapreduce.v2.api.protocolrecords;

import org.apache.hadoop.classification.InterfaceAudience.Public;
import org.apache.hadoop.classification.InterfaceStability.Evolving;
import org.apache.hadoop.yarn.api.records.Token;

@InterfaceAudience.Public
@InterfaceStability.Evolving
public abstract interface CancelDelegationTokenRequest
{
  public abstract Token getDelegationToken();
  
  public abstract void setDelegationToken(Token paramToken);
}


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-common-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.v2.api.protocolrecords.CancelDelegationTokenRequest
 * JD-Core Version:    0.7.0.1
 */