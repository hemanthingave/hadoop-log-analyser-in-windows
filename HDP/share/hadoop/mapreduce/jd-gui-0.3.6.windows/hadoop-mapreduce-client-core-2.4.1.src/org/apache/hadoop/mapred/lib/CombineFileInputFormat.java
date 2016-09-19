/*   1:    */ package org.apache.hadoop.mapred.lib;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.util.List;
/*   5:    */ import org.apache.hadoop.classification.InterfaceAudience.Private;
/*   6:    */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*   7:    */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*   8:    */ import org.apache.hadoop.fs.FileStatus;
/*   9:    */ import org.apache.hadoop.fs.FileSystem;
/*  10:    */ import org.apache.hadoop.fs.Path;
/*  11:    */ import org.apache.hadoop.fs.PathFilter;
/*  12:    */ import org.apache.hadoop.io.compress.CompressionCodec;
/*  13:    */ import org.apache.hadoop.io.compress.CompressionCodecFactory;
/*  14:    */ import org.apache.hadoop.io.compress.SplittableCompressionCodec;
/*  15:    */ import org.apache.hadoop.mapred.InputFormat;
/*  16:    */ import org.apache.hadoop.mapred.JobConf;
/*  17:    */ import org.apache.hadoop.mapred.Reporter;
/*  18:    */ import org.apache.hadoop.mapreduce.Job;
/*  19:    */ import org.apache.hadoop.mapreduce.JobContext;
/*  20:    */ import org.apache.hadoop.mapreduce.TaskAttemptContext;
/*  21:    */ 
/*  22:    */ @InterfaceAudience.Public
/*  23:    */ @InterfaceStability.Stable
/*  24:    */ public abstract class CombineFileInputFormat<K, V>
/*  25:    */   extends org.apache.hadoop.mapreduce.lib.input.CombineFileInputFormat<K, V>
/*  26:    */   implements InputFormat<K, V>
/*  27:    */ {
/*  28:    */   public org.apache.hadoop.mapred.InputSplit[] getSplits(JobConf job, int numSplits)
/*  29:    */     throws IOException
/*  30:    */   {
/*  31: 75 */     List<org.apache.hadoop.mapreduce.InputSplit> newStyleSplits = super.getSplits(new Job(job));
/*  32:    */     
/*  33: 77 */     org.apache.hadoop.mapred.InputSplit[] ret = new org.apache.hadoop.mapred.InputSplit[newStyleSplits.size()];
/*  34: 78 */     for (int pos = 0; pos < newStyleSplits.size(); pos++)
/*  35:    */     {
/*  36: 79 */       org.apache.hadoop.mapreduce.lib.input.CombineFileSplit newStyleSplit = (org.apache.hadoop.mapreduce.lib.input.CombineFileSplit)newStyleSplits.get(pos);
/*  37:    */       
/*  38: 81 */       ret[pos] = new CombineFileSplit(job, newStyleSplit.getPaths(), newStyleSplit.getStartOffsets(), newStyleSplit.getLengths(), newStyleSplit.getLocations());
/*  39:    */     }
/*  40: 85 */     return ret;
/*  41:    */   }
/*  42:    */   
/*  43:    */   @Deprecated
/*  44:    */   protected void createPool(JobConf conf, List<PathFilter> filters)
/*  45:    */   {
/*  46: 95 */     createPool(filters);
/*  47:    */   }
/*  48:    */   
/*  49:    */   @Deprecated
/*  50:    */   protected void createPool(JobConf conf, PathFilter... filters)
/*  51:    */   {
/*  52:106 */     createPool(filters);
/*  53:    */   }
/*  54:    */   
/*  55:    */   public abstract org.apache.hadoop.mapred.RecordReader<K, V> getRecordReader(org.apache.hadoop.mapred.InputSplit paramInputSplit, JobConf paramJobConf, Reporter paramReporter)
/*  56:    */     throws IOException;
/*  57:    */   
/*  58:    */   public org.apache.hadoop.mapreduce.RecordReader<K, V> createRecordReader(org.apache.hadoop.mapreduce.InputSplit split, TaskAttemptContext context)
/*  59:    */     throws IOException
/*  60:    */   {
/*  61:120 */     return null;
/*  62:    */   }
/*  63:    */   
/*  64:    */   protected FileStatus[] listStatus(JobConf job)
/*  65:    */     throws IOException
/*  66:    */   {
/*  67:132 */     List<FileStatus> result = super.listStatus(new Job(job));
/*  68:133 */     return (FileStatus[])result.toArray(new FileStatus[result.size()]);
/*  69:    */   }
/*  70:    */   
/*  71:    */   @InterfaceAudience.Private
/*  72:    */   protected boolean isSplitable(JobContext context, Path file)
/*  73:    */   {
/*  74:    */     try
/*  75:    */     {
/*  76:151 */       return isSplitable(FileSystem.get(context.getConfiguration()), file);
/*  77:    */     }
/*  78:    */     catch (IOException ioe)
/*  79:    */     {
/*  80:154 */       throw new RuntimeException(ioe);
/*  81:    */     }
/*  82:    */   }
/*  83:    */   
/*  84:    */   protected boolean isSplitable(FileSystem fs, Path file)
/*  85:    */   {
/*  86:159 */     CompressionCodec codec = new CompressionCodecFactory(fs.getConf()).getCodec(file);
/*  87:161 */     if (null == codec) {
/*  88:162 */       return true;
/*  89:    */     }
/*  90:164 */     return codec instanceof SplittableCompressionCodec;
/*  91:    */   }
/*  92:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.lib.CombineFileInputFormat
 * JD-Core Version:    0.7.0.1
 */