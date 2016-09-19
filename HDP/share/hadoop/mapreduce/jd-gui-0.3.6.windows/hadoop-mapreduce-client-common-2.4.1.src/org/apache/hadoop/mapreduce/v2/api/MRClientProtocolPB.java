package org.apache.hadoop.mapreduce.v2.api;

import org.apache.hadoop.ipc.ProtocolInfo;
import org.apache.hadoop.yarn.proto.MRClientProtocol.MRClientProtocolService.BlockingInterface;

@ProtocolInfo(protocolName="org.apache.hadoop.mapreduce.v2.api.MRClientProtocolPB", protocolVersion=1L)
public abstract interface MRClientProtocolPB
  extends MRClientProtocol.MRClientProtocolService.BlockingInterface
{}


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-common-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.v2.api.MRClientProtocolPB
 * JD-Core Version:    0.7.0.1
 */