package org.apache.hadoop.mapreduce.v2.api.protocolrecords;

import org.apache.hadoop.classification.InterfaceAudience.Public;
import org.apache.hadoop.classification.InterfaceStability.Evolving;

@InterfaceAudience.Public
@InterfaceStability.Evolving
public abstract interface GetDelegationTokenRequest
{
  public abstract String getRenewer();
  
  public abstract void setRenewer(String paramString);
}


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-common-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.v2.api.protocolrecords.GetDelegationTokenRequest
 * JD-Core Version:    0.7.0.1
 */