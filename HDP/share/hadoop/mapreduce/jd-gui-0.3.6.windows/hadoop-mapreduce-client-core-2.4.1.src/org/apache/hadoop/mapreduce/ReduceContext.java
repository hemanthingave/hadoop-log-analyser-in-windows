package org.apache.hadoop.mapreduce;

import java.io.IOException;
import org.apache.hadoop.classification.InterfaceAudience.Public;
import org.apache.hadoop.classification.InterfaceStability.Evolving;

@InterfaceAudience.Public
@InterfaceStability.Evolving
public abstract interface ReduceContext<KEYIN, VALUEIN, KEYOUT, VALUEOUT>
  extends TaskInputOutputContext<KEYIN, VALUEIN, KEYOUT, VALUEOUT>
{
  public abstract boolean nextKey()
    throws IOException, InterruptedException;
  
  public abstract Iterable<VALUEIN> getValues()
    throws IOException, InterruptedException;
  
  public static abstract interface ValueIterator<VALUEIN>
    extends MarkableIteratorInterface<VALUEIN>
  {
    public abstract void resetBackupStore()
      throws IOException;
  }
}


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.ReduceContext
 * JD-Core Version:    0.7.0.1
 */