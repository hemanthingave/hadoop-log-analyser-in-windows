package org.apache.hadoop.mapreduce;

import java.io.IOException;
import java.net.URI;
import org.apache.hadoop.classification.InterfaceAudience.Public;
import org.apache.hadoop.classification.InterfaceStability.Evolving;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configuration.IntegerRanges;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.RawComparator;
import org.apache.hadoop.security.Credentials;

@InterfaceAudience.Public
@InterfaceStability.Evolving
public abstract interface JobContext
  extends MRJobConfig
{
  public abstract Configuration getConfiguration();
  
  public abstract Credentials getCredentials();
  
  public abstract JobID getJobID();
  
  public abstract int getNumReduceTasks();
  
  public abstract Path getWorkingDirectory()
    throws IOException;
  
  public abstract Class<?> getOutputKeyClass();
  
  public abstract Class<?> getOutputValueClass();
  
  public abstract Class<?> getMapOutputKeyClass();
  
  public abstract Class<?> getMapOutputValueClass();
  
  public abstract String getJobName();
  
  public abstract Class<? extends InputFormat<?, ?>> getInputFormatClass()
    throws ClassNotFoundException;
  
  public abstract Class<? extends Mapper<?, ?, ?, ?>> getMapperClass()
    throws ClassNotFoundException;
  
  public abstract Class<? extends Reducer<?, ?, ?, ?>> getCombinerClass()
    throws ClassNotFoundException;
  
  public abstract Class<? extends Reducer<?, ?, ?, ?>> getReducerClass()
    throws ClassNotFoundException;
  
  public abstract Class<? extends OutputFormat<?, ?>> getOutputFormatClass()
    throws ClassNotFoundException;
  
  public abstract Class<? extends Partitioner<?, ?>> getPartitionerClass()
    throws ClassNotFoundException;
  
  public abstract RawComparator<?> getSortComparator();
  
  public abstract String getJar();
  
  public abstract RawComparator<?> getCombinerKeyGroupingComparator();
  
  public abstract RawComparator<?> getGroupingComparator();
  
  public abstract boolean getJobSetupCleanupNeeded();
  
  public abstract boolean getTaskCleanupNeeded();
  
  public abstract boolean getProfileEnabled();
  
  public abstract String getProfileParams();
  
  public abstract Configuration.IntegerRanges getProfileTaskRange(boolean paramBoolean);
  
  public abstract String getUser();
  
  @Deprecated
  public abstract boolean getSymlink();
  
  public abstract Path[] getArchiveClassPaths();
  
  public abstract URI[] getCacheArchives()
    throws IOException;
  
  public abstract URI[] getCacheFiles()
    throws IOException;
  
  @Deprecated
  public abstract Path[] getLocalCacheArchives()
    throws IOException;
  
  @Deprecated
  public abstract Path[] getLocalCacheFiles()
    throws IOException;
  
  public abstract Path[] getFileClassPaths();
  
  public abstract String[] getArchiveTimestamps();
  
  public abstract String[] getFileTimestamps();
  
  public abstract int getMaxMapAttempts();
  
  public abstract int getMaxReduceAttempts();
}


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.JobContext
 * JD-Core Version:    0.7.0.1
 */