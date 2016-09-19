/*   1:    */ package org.apache.hadoop.mapred.lib;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*   6:    */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*   7:    */ import org.apache.hadoop.fs.FileStatus;
/*   8:    */ import org.apache.hadoop.fs.Path;
/*   9:    */ import org.apache.hadoop.io.LongWritable;
/*  10:    */ import org.apache.hadoop.io.Text;
/*  11:    */ import org.apache.hadoop.mapred.FileInputFormat;
/*  12:    */ import org.apache.hadoop.mapred.InputSplit;
/*  13:    */ import org.apache.hadoop.mapred.JobConf;
/*  14:    */ import org.apache.hadoop.mapred.JobConfigurable;
/*  15:    */ import org.apache.hadoop.mapred.LineRecordReader;
/*  16:    */ import org.apache.hadoop.mapred.RecordReader;
/*  17:    */ import org.apache.hadoop.mapred.Reporter;
/*  18:    */ 
/*  19:    */ @InterfaceAudience.Public
/*  20:    */ @InterfaceStability.Stable
/*  21:    */ public class NLineInputFormat
/*  22:    */   extends FileInputFormat<LongWritable, Text>
/*  23:    */   implements JobConfigurable
/*  24:    */ {
/*  25: 61 */   private int N = 1;
/*  26:    */   
/*  27:    */   public RecordReader<LongWritable, Text> getRecordReader(InputSplit genericSplit, JobConf job, Reporter reporter)
/*  28:    */     throws IOException
/*  29:    */   {
/*  30: 68 */     reporter.setStatus(genericSplit.toString());
/*  31: 69 */     return new LineRecordReader(job, (org.apache.hadoop.mapred.FileSplit)genericSplit);
/*  32:    */   }
/*  33:    */   
/*  34:    */   public InputSplit[] getSplits(JobConf job, int numSplits)
/*  35:    */     throws IOException
/*  36:    */   {
/*  37: 80 */     ArrayList<org.apache.hadoop.mapred.FileSplit> splits = new ArrayList();
/*  38: 81 */     for (FileStatus status : listStatus(job)) {
/*  39: 83 */       for (org.apache.hadoop.mapreduce.lib.input.FileSplit split : org.apache.hadoop.mapreduce.lib.input.NLineInputFormat.getSplitsForFile(status, job, this.N)) {
/*  40: 85 */         splits.add(new org.apache.hadoop.mapred.FileSplit(split));
/*  41:    */       }
/*  42:    */     }
/*  43: 88 */     return (InputSplit[])splits.toArray(new org.apache.hadoop.mapred.FileSplit[splits.size()]);
/*  44:    */   }
/*  45:    */   
/*  46:    */   public void configure(JobConf conf)
/*  47:    */   {
/*  48: 92 */     this.N = conf.getInt("mapreduce.input.lineinputformat.linespermap", 1);
/*  49:    */   }
/*  50:    */   
/*  51:    */   protected static org.apache.hadoop.mapred.FileSplit createFileSplit(Path fileName, long begin, long length)
/*  52:    */   {
/*  53:107 */     return begin == 0L ? new org.apache.hadoop.mapred.FileSplit(fileName, begin, length - 1L, new String[0]) : new org.apache.hadoop.mapred.FileSplit(fileName, begin - 1L, length, new String[0]);
/*  54:    */   }
/*  55:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.lib.NLineInputFormat
 * JD-Core Version:    0.7.0.1
 */