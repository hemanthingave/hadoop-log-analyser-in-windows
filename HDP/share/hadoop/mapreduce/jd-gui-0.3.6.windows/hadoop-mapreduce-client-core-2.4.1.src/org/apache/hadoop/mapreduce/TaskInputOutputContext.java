package org.apache.hadoop.mapreduce;

import java.io.IOException;
import org.apache.hadoop.classification.InterfaceAudience.Public;
import org.apache.hadoop.classification.InterfaceStability.Evolving;

@InterfaceAudience.Public
@InterfaceStability.Evolving
public abstract interface TaskInputOutputContext<KEYIN, VALUEIN, KEYOUT, VALUEOUT>
  extends TaskAttemptContext
{
  public abstract boolean nextKeyValue()
    throws IOException, InterruptedException;
  
  public abstract KEYIN getCurrentKey()
    throws IOException, InterruptedException;
  
  public abstract VALUEIN getCurrentValue()
    throws IOException, InterruptedException;
  
  public abstract void write(KEYOUT paramKEYOUT, VALUEOUT paramVALUEOUT)
    throws IOException, InterruptedException;
  
  public abstract OutputCommitter getOutputCommitter();
}


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.TaskInputOutputContext
 * JD-Core Version:    0.7.0.1
 */