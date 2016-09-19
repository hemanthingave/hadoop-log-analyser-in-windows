package org.apache.hadoop.mapreduce.protocol;

import java.io.IOException;
import java.net.InetSocketAddress;
import org.apache.hadoop.classification.InterfaceAudience.Private;
import org.apache.hadoop.conf.Configuration;

@InterfaceAudience.Private
public abstract class ClientProtocolProvider
{
  public abstract ClientProtocol create(Configuration paramConfiguration)
    throws IOException;
  
  public abstract ClientProtocol create(InetSocketAddress paramInetSocketAddress, Configuration paramConfiguration)
    throws IOException;
  
  public abstract void close(ClientProtocol paramClientProtocol)
    throws IOException;
}


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.protocol.ClientProtocolProvider
 * JD-Core Version:    0.7.0.1
 */