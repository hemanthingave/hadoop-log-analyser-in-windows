/*  1:   */ package org.apache.hadoop.mapreduce.split;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import java.util.Arrays;
/*  5:   */ import org.apache.hadoop.classification.InterfaceAudience.Private;
/*  6:   */ import org.apache.hadoop.classification.InterfaceStability.Unstable;
/*  7:   */ import org.apache.hadoop.conf.Configuration;
/*  8:   */ import org.apache.hadoop.fs.FSDataInputStream;
/*  9:   */ import org.apache.hadoop.fs.FileStatus;
/* 10:   */ import org.apache.hadoop.fs.FileSystem;
/* 11:   */ import org.apache.hadoop.fs.Path;
/* 12:   */ import org.apache.hadoop.io.WritableUtils;
/* 13:   */ import org.apache.hadoop.mapreduce.JobID;
/* 14:   */ import org.apache.hadoop.mapreduce.JobSubmissionFiles;
/* 15:   */ 
/* 16:   */ @InterfaceAudience.Private
/* 17:   */ @InterfaceStability.Unstable
/* 18:   */ public class SplitMetaInfoReader
/* 19:   */ {
/* 20:   */   public static JobSplit.TaskSplitMetaInfo[] readSplitMetaInfo(JobID jobId, FileSystem fs, Configuration conf, Path jobSubmitDir)
/* 21:   */     throws IOException
/* 22:   */   {
/* 23:47 */     long maxMetaInfoSize = conf.getLong("mapreduce.job.split.metainfo.maxsize", 10000000L);
/* 24:   */     
/* 25:49 */     Path metaSplitFile = JobSubmissionFiles.getJobSplitMetaFile(jobSubmitDir);
/* 26:50 */     String jobSplitFile = JobSubmissionFiles.getJobSplitFile(jobSubmitDir).toString();
/* 27:51 */     FileStatus fStatus = fs.getFileStatus(metaSplitFile);
/* 28:52 */     if ((maxMetaInfoSize > 0L) && (fStatus.getLen() > maxMetaInfoSize)) {
/* 29:53 */       throw new IOException("Split metadata size exceeded " + maxMetaInfoSize + ". Aborting job " + jobId);
/* 30:   */     }
/* 31:56 */     FSDataInputStream in = fs.open(metaSplitFile);
/* 32:57 */     byte[] header = new byte[JobSplit.META_SPLIT_FILE_HEADER.length];
/* 33:58 */     in.readFully(header);
/* 34:59 */     if (!Arrays.equals(JobSplit.META_SPLIT_FILE_HEADER, header)) {
/* 35:60 */       throw new IOException("Invalid header on split file");
/* 36:   */     }
/* 37:62 */     int vers = WritableUtils.readVInt(in);
/* 38:63 */     if (vers != 1)
/* 39:   */     {
/* 40:64 */       in.close();
/* 41:65 */       throw new IOException("Unsupported split version " + vers);
/* 42:   */     }
/* 43:67 */     int numSplits = WritableUtils.readVInt(in);
/* 44:68 */     JobSplit.TaskSplitMetaInfo[] allSplitMetaInfo = new JobSplit.TaskSplitMetaInfo[numSplits];
/* 45:70 */     for (int i = 0; i < numSplits; i++)
/* 46:   */     {
/* 47:71 */       JobSplit.SplitMetaInfo splitMetaInfo = new JobSplit.SplitMetaInfo();
/* 48:72 */       splitMetaInfo.readFields(in);
/* 49:73 */       JobSplit.TaskSplitIndex splitIndex = new JobSplit.TaskSplitIndex(jobSplitFile, splitMetaInfo.getStartOffset());
/* 50:   */       
/* 51:   */ 
/* 52:76 */       allSplitMetaInfo[i] = new JobSplit.TaskSplitMetaInfo(splitIndex, splitMetaInfo.getLocations(), splitMetaInfo.getInputDataLength());
/* 53:   */     }
/* 54:80 */     in.close();
/* 55:81 */     return allSplitMetaInfo;
/* 56:   */   }
/* 57:   */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.split.SplitMetaInfoReader
 * JD-Core Version:    0.7.0.1
 */