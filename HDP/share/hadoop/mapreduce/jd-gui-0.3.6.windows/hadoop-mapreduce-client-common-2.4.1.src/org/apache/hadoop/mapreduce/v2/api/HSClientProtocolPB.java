package org.apache.hadoop.mapreduce.v2.api;

import org.apache.hadoop.ipc.ProtocolInfo;
import org.apache.hadoop.yarn.proto.HSClientProtocol.HSClientProtocolService.BlockingInterface;

@ProtocolInfo(protocolName="org.apache.hadoop.mapreduce.v2.api.HSClientProtocolPB", protocolVersion=1L)
public abstract interface HSClientProtocolPB
  extends HSClientProtocol.HSClientProtocolService.BlockingInterface
{}


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-common-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.v2.api.HSClientProtocolPB
 * JD-Core Version:    0.7.0.1
 */