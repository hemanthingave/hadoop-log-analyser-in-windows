package org.apache.hadoop.mapreduce.v2.api.protocolrecords;

import org.apache.hadoop.yarn.api.records.Token;

public abstract interface GetDelegationTokenResponse
{
  public abstract void setDelegationToken(Token paramToken);
  
  public abstract Token getDelegationToken();
}


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-common-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.v2.api.protocolrecords.GetDelegationTokenResponse
 * JD-Core Version:    0.7.0.1
 */