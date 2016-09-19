package org.apache.hadoop.mapreduce.lib.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.apache.hadoop.classification.InterfaceAudience.Public;
import org.apache.hadoop.classification.InterfaceStability.Evolving;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.mapreduce.InputSplit;

@InterfaceAudience.Public
@InterfaceStability.Evolving
public abstract interface DBSplitter
{
  public abstract List<InputSplit> split(Configuration paramConfiguration, ResultSet paramResultSet, String paramString)
    throws SQLException;
}


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.lib.db.DBSplitter
 * JD-Core Version:    0.7.0.1
 */