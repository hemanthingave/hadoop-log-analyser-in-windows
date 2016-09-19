package org.apache.hadoop.mapreduce.v2.api.protocolrecords;

import java.util.List;

public abstract interface GetDiagnosticsResponse
{
  public abstract List<String> getDiagnosticsList();
  
  public abstract String getDiagnostics(int paramInt);
  
  public abstract int getDiagnosticsCount();
  
  public abstract void addAllDiagnostics(List<String> paramList);
  
  public abstract void addDiagnostics(String paramString);
  
  public abstract void removeDiagnostics(int paramInt);
  
  public abstract void clearDiagnostics();
}


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-common-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.v2.api.protocolrecords.GetDiagnosticsResponse
 * JD-Core Version:    0.7.0.1
 */