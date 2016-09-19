/*   1:    */ package org.apache.hadoop.mapreduce.lib.input;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import java.util.List;
/*   6:    */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*   7:    */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*   8:    */ import org.apache.hadoop.conf.Configuration;
/*   9:    */ import org.apache.hadoop.fs.FSDataInputStream;
/*  10:    */ import org.apache.hadoop.fs.FileStatus;
/*  11:    */ import org.apache.hadoop.fs.FileSystem;
/*  12:    */ import org.apache.hadoop.fs.Path;
/*  13:    */ import org.apache.hadoop.io.LongWritable;
/*  14:    */ import org.apache.hadoop.io.Text;
/*  15:    */ import org.apache.hadoop.mapreduce.InputSplit;
/*  16:    */ import org.apache.hadoop.mapreduce.Job;
/*  17:    */ import org.apache.hadoop.mapreduce.JobContext;
/*  18:    */ import org.apache.hadoop.mapreduce.RecordReader;
/*  19:    */ import org.apache.hadoop.mapreduce.TaskAttemptContext;
/*  20:    */ import org.apache.hadoop.util.LineReader;
/*  21:    */ 
/*  22:    */ @InterfaceAudience.Public
/*  23:    */ @InterfaceStability.Stable
/*  24:    */ public class NLineInputFormat
/*  25:    */   extends FileInputFormat<LongWritable, Text>
/*  26:    */ {
/*  27:    */   public static final String LINES_PER_MAP = "mapreduce.input.lineinputformat.linespermap";
/*  28:    */   
/*  29:    */   public RecordReader<LongWritable, Text> createRecordReader(InputSplit genericSplit, TaskAttemptContext context)
/*  30:    */     throws IOException
/*  31:    */   {
/*  32: 68 */     context.setStatus(genericSplit.toString());
/*  33: 69 */     return new LineRecordReader();
/*  34:    */   }
/*  35:    */   
/*  36:    */   public List<InputSplit> getSplits(JobContext job)
/*  37:    */     throws IOException
/*  38:    */   {
/*  39: 80 */     List<InputSplit> splits = new ArrayList();
/*  40: 81 */     int numLinesPerSplit = getNumLinesPerSplit(job);
/*  41: 82 */     for (FileStatus status : listStatus(job)) {
/*  42: 83 */       splits.addAll(getSplitsForFile(status, job.getConfiguration(), numLinesPerSplit));
/*  43:    */     }
/*  44: 86 */     return splits;
/*  45:    */   }
/*  46:    */   
/*  47:    */   public static List<FileSplit> getSplitsForFile(FileStatus status, Configuration conf, int numLinesPerSplit)
/*  48:    */     throws IOException
/*  49:    */   {
/*  50: 91 */     List<FileSplit> splits = new ArrayList();
/*  51: 92 */     Path fileName = status.getPath();
/*  52: 93 */     if (status.isDirectory()) {
/*  53: 94 */       throw new IOException("Not a file: " + fileName);
/*  54:    */     }
/*  55: 96 */     FileSystem fs = fileName.getFileSystem(conf);
/*  56: 97 */     LineReader lr = null;
/*  57:    */     try
/*  58:    */     {
/*  59: 99 */       FSDataInputStream in = fs.open(fileName);
/*  60:100 */       lr = new LineReader(in, conf);
/*  61:101 */       Text line = new Text();
/*  62:102 */       int numLines = 0;
/*  63:103 */       long begin = 0L;
/*  64:104 */       long length = 0L;
/*  65:105 */       int num = -1;
/*  66:106 */       while ((num = lr.readLine(line)) > 0)
/*  67:    */       {
/*  68:107 */         numLines++;
/*  69:108 */         length += num;
/*  70:109 */         if (numLines == numLinesPerSplit)
/*  71:    */         {
/*  72:110 */           splits.add(createFileSplit(fileName, begin, length));
/*  73:111 */           begin += length;
/*  74:112 */           length = 0L;
/*  75:113 */           numLines = 0;
/*  76:    */         }
/*  77:    */       }
/*  78:116 */       if (numLines != 0) {
/*  79:117 */         splits.add(createFileSplit(fileName, begin, length));
/*  80:    */       }
/*  81:    */     }
/*  82:    */     finally
/*  83:    */     {
/*  84:120 */       if (lr != null) {
/*  85:121 */         lr.close();
/*  86:    */       }
/*  87:    */     }
/*  88:124 */     return splits;
/*  89:    */   }
/*  90:    */   
/*  91:    */   protected static FileSplit createFileSplit(Path fileName, long begin, long length)
/*  92:    */   {
/*  93:139 */     return begin == 0L ? new FileSplit(fileName, begin, length - 1L, new String[0]) : new FileSplit(fileName, begin - 1L, length, new String[0]);
/*  94:    */   }
/*  95:    */   
/*  96:    */   public static void setNumLinesPerSplit(Job job, int numLines)
/*  97:    */   {
/*  98:150 */     job.getConfiguration().setInt("mapreduce.input.lineinputformat.linespermap", numLines);
/*  99:    */   }
/* 100:    */   
/* 101:    */   public static int getNumLinesPerSplit(JobContext job)
/* 102:    */   {
/* 103:159 */     return job.getConfiguration().getInt("mapreduce.input.lineinputformat.linespermap", 1);
/* 104:    */   }
/* 105:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.lib.input.NLineInputFormat
 * JD-Core Version:    0.7.0.1
 */