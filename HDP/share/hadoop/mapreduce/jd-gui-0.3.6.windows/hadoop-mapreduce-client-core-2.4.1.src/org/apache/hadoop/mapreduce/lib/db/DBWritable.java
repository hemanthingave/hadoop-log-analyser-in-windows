package org.apache.hadoop.mapreduce.lib.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.hadoop.classification.InterfaceAudience.Public;
import org.apache.hadoop.classification.InterfaceStability.Stable;

@InterfaceAudience.Public
@InterfaceStability.Stable
public abstract interface DBWritable
{
  public abstract void write(PreparedStatement paramPreparedStatement)
    throws SQLException;
  
  public abstract void readFields(ResultSet paramResultSet)
    throws SQLException;
}


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.lib.db.DBWritable
 * JD-Core Version:    0.7.0.1
 */