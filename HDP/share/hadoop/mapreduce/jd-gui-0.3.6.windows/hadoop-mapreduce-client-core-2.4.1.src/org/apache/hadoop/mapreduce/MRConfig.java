package org.apache.hadoop.mapreduce;

import org.apache.hadoop.classification.InterfaceAudience.Private;
import org.apache.hadoop.classification.InterfaceAudience.Public;
import org.apache.hadoop.classification.InterfaceStability.Unstable;

@InterfaceAudience.Private
public abstract interface MRConfig
{
  public static final String TEMP_DIR = "mapreduce.cluster.temp.dir";
  public static final String LOCAL_DIR = "mapreduce.cluster.local.dir";
  public static final String MAPMEMORY_MB = "mapreduce.cluster.mapmemory.mb";
  public static final String REDUCEMEMORY_MB = "mapreduce.cluster.reducememory.mb";
  public static final String MR_ACLS_ENABLED = "mapreduce.cluster.acls.enabled";
  public static final String MR_ADMINS = "mapreduce.cluster.administrators";
  @Deprecated
  public static final String MR_SUPERGROUP = "mapreduce.cluster.permissions.supergroup";
  public static final String DELEGATION_KEY_UPDATE_INTERVAL_KEY = "mapreduce.cluster.delegation.key.update-interval";
  public static final long DELEGATION_KEY_UPDATE_INTERVAL_DEFAULT = 86400000L;
  public static final String DELEGATION_TOKEN_RENEW_INTERVAL_KEY = "mapreduce.cluster.delegation.token.renew-interval";
  public static final long DELEGATION_TOKEN_RENEW_INTERVAL_DEFAULT = 86400000L;
  public static final String DELEGATION_TOKEN_MAX_LIFETIME_KEY = "mapreduce.cluster.delegation.token.max-lifetime";
  public static final long DELEGATION_TOKEN_MAX_LIFETIME_DEFAULT = 604800000L;
  public static final String RESOURCE_CALCULATOR_PROCESS_TREE = "mapreduce.job.process-tree.class";
  public static final String STATIC_RESOLUTIONS = "mapreduce.job.net.static.resolutions";
  public static final String MASTER_ADDRESS = "mapreduce.jobtracker.address";
  public static final String MASTER_USER_NAME = "mapreduce.jobtracker.kerberos.principal";
  public static final String FRAMEWORK_NAME = "mapreduce.framework.name";
  public static final String CLASSIC_FRAMEWORK_NAME = "classic";
  public static final String YARN_FRAMEWORK_NAME = "yarn";
  public static final String LOCAL_FRAMEWORK_NAME = "local";
  public static final String TASK_LOCAL_OUTPUT_CLASS = "mapreduce.task.local.output.class";
  public static final String PROGRESS_STATUS_LEN_LIMIT_KEY = "mapreduce.task.max.status.length";
  public static final int PROGRESS_STATUS_LEN_LIMIT_DEFAULT = 512;
  public static final int MAX_BLOCK_LOCATIONS_DEFAULT = 10;
  public static final String MAX_BLOCK_LOCATIONS_KEY = "mapreduce.job.max.split.locations";
  public static final String SHUFFLE_SSL_ENABLED_KEY = "mapreduce.shuffle.ssl.enabled";
  public static final boolean SHUFFLE_SSL_ENABLED_DEFAULT = false;
  public static final String SHUFFLE_CONSUMER_PLUGIN = "mapreduce.job.reduce.shuffle.consumer.plugin.class";
  public static final String MAPRED_IFILE_READAHEAD = "mapreduce.ifile.readahead";
  public static final boolean DEFAULT_MAPRED_IFILE_READAHEAD = true;
  public static final String MAPRED_IFILE_READAHEAD_BYTES = "mapreduce.ifile.readahead.bytes";
  public static final int DEFAULT_MAPRED_IFILE_READAHEAD_BYTES = 4194304;
  public static final String MAPREDUCE_MINICLUSTER_CONTROL_RESOURCE_MONITORING = "mapreduce.minicluster.control-resource-monitoring";
  public static final boolean DEFAULT_MAPREDUCE_MINICLUSTER_CONTROL_RESOURCE_MONITORING = false;
  @InterfaceAudience.Public
  @InterfaceStability.Unstable
  public static final String MAPREDUCE_APP_SUBMISSION_CROSS_PLATFORM = "mapreduce.app-submission.cross-platform";
  @InterfaceAudience.Public
  @InterfaceStability.Unstable
  public static final boolean DEFAULT_MAPREDUCE_APP_SUBMISSION_CROSS_PLATFORM = false;
}


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.MRConfig
 * JD-Core Version:    0.7.0.1
 */