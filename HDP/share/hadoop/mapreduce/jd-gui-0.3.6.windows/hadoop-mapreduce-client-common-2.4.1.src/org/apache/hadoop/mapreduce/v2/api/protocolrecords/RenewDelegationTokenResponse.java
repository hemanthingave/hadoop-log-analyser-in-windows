package org.apache.hadoop.mapreduce.v2.api.protocolrecords;

import org.apache.hadoop.classification.InterfaceAudience.Public;
import org.apache.hadoop.classification.InterfaceStability.Evolving;

@InterfaceAudience.Public
@InterfaceStability.Evolving
public abstract interface RenewDelegationTokenResponse
{
  public abstract long getNextExpirationTime();
  
  public abstract void setNextExpirationTime(long paramLong);
}


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-common-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.v2.api.protocolrecords.RenewDelegationTokenResponse
 * JD-Core Version:    0.7.0.1
 */