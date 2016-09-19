package org.apache.hadoop.mapreduce.server.tasktracker;

import org.apache.hadoop.classification.InterfaceAudience.Private;
import org.apache.hadoop.classification.InterfaceStability.Evolving;
import org.apache.hadoop.mapreduce.MRConfig;

@InterfaceAudience.Private
@InterfaceStability.Evolving
public abstract interface TTConfig
  extends MRConfig
{
  public static final String TT_HEALTH_CHECKER_INTERVAL = "mapreduce.tasktracker.healthchecker.interval";
  public static final String TT_HEALTH_CHECKER_SCRIPT_ARGS = "mapreduce.tasktracker.healthchecker.script.args";
  public static final String TT_HEALTH_CHECKER_SCRIPT_PATH = "mapreduce.tasktracker.healthchecker.script.path";
  public static final String TT_HEALTH_CHECKER_SCRIPT_TIMEOUT = "mapreduce.tasktracker.healthchecker.script.timeout";
  public static final String TT_LOCAL_DIR_MINSPACE_KILL = "mapreduce.tasktracker.local.dir.minspacekill";
  public static final String TT_LOCAL_DIR_MINSPACE_START = "mapreduce.tasktracker.local.dir.minspacestart";
  public static final String TT_HTTP_ADDRESS = "mapreduce.tasktracker.http.address";
  public static final String TT_REPORT_ADDRESS = "mapreduce.tasktracker.report.address";
  public static final String TT_TASK_CONTROLLER = "mapreduce.tasktracker.taskcontroller";
  public static final String TT_CONTENTION_TRACKING = "mapreduce.tasktracker.contention.tracking";
  public static final String TT_STATIC_RESOLUTIONS = "mapreduce.tasktracker.net.static.resolutions";
  public static final String TT_HTTP_THREADS = "mapreduce.tasktracker.http.threads";
  public static final String TT_HOST_NAME = "mapreduce.tasktracker.host.name";
  public static final String TT_SLEEP_TIME_BEFORE_SIG_KILL = "mapreduce.tasktracker.tasks.sleeptimebeforesigkill";
  public static final String TT_DNS_INTERFACE = "mapreduce.tasktracker.dns.interface";
  public static final String TT_DNS_NAMESERVER = "mapreduce.tasktracker.dns.nameserver";
  public static final String TT_MAX_TASK_COMPLETION_EVENTS_TO_POLL = "mapreduce.tasktracker.events.batchsize";
  public static final String TT_INDEX_CACHE = "mapreduce.tasktracker.indexcache.mb";
  public static final String TT_INSTRUMENTATION = "mapreduce.tasktracker.instrumentation";
  public static final String TT_MAP_SLOTS = "mapreduce.tasktracker.map.tasks.maximum";
  @Deprecated
  public static final String TT_MEMORY_CALCULATOR_PLUGIN = "mapreduce.tasktracker.memorycalculatorplugin";
  public static final String TT_RESOURCE_CALCULATOR_PLUGIN = "mapreduce.tasktracker.resourcecalculatorplugin";
  public static final String TT_REDUCE_SLOTS = "mapreduce.tasktracker.reduce.tasks.maximum";
  public static final String TT_MEMORY_MANAGER_MONITORING_INTERVAL = "mapreduce.tasktracker.taskmemorymanager.monitoringinterval";
  public static final String TT_LOCAL_CACHE_SIZE = "mapreduce.tasktracker.cache.local.size";
  public static final String TT_LOCAL_CACHE_SUBDIRS_LIMIT = "mapreduce.tasktracker.cache.local.numberdirectories";
  public static final String TT_OUTOFBAND_HEARBEAT = "mapreduce.tasktracker.outofband.heartbeat";
  public static final String TT_RESERVED_PHYSCIALMEMORY_MB = "mapreduce.tasktracker.reserved.physicalmemory.mb";
  public static final String TT_USER_NAME = "mapreduce.tasktracker.kerberos.principal";
  public static final String TT_KEYTAB_FILE = "mapreduce.tasktracker.keytab.file";
  public static final String TT_GROUP = "mapreduce.tasktracker.group";
  public static final String TT_USERLOGCLEANUP_SLEEPTIME = "mapreduce.tasktracker.userlogcleanup.sleeptime";
  public static final String TT_DISTRIBUTED_CACHE_CHECK_PERIOD = "mapreduce.tasktracker.distributedcache.checkperiod";
  public static final String TT_LOCAL_CACHE_KEEP_AROUND_PCT = "mapreduce.tasktracker.cache.local.keep.pct";
}


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.server.tasktracker.TTConfig
 * JD-Core Version:    0.7.0.1
 */