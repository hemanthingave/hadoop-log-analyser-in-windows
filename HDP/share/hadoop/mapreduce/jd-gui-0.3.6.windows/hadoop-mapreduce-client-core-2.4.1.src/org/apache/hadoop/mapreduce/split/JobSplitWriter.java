/*   1:    */ package org.apache.hadoop.mapreduce.split;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.UnsupportedEncodingException;
/*   5:    */ import java.util.Arrays;
/*   6:    */ import java.util.List;
/*   7:    */ import org.apache.commons.logging.Log;
/*   8:    */ import org.apache.commons.logging.LogFactory;
/*   9:    */ import org.apache.hadoop.classification.InterfaceAudience.Private;
/*  10:    */ import org.apache.hadoop.classification.InterfaceStability.Unstable;
/*  11:    */ import org.apache.hadoop.conf.Configuration;
/*  12:    */ import org.apache.hadoop.fs.FSDataOutputStream;
/*  13:    */ import org.apache.hadoop.fs.FileSystem;
/*  14:    */ import org.apache.hadoop.fs.Path;
/*  15:    */ import org.apache.hadoop.fs.permission.FsPermission;
/*  16:    */ import org.apache.hadoop.io.Text;
/*  17:    */ import org.apache.hadoop.io.WritableUtils;
/*  18:    */ import org.apache.hadoop.io.serializer.SerializationFactory;
/*  19:    */ import org.apache.hadoop.io.serializer.Serializer;
/*  20:    */ import org.apache.hadoop.mapreduce.JobSubmissionFiles;
/*  21:    */ 
/*  22:    */ @InterfaceAudience.Private
/*  23:    */ @InterfaceStability.Unstable
/*  24:    */ public class JobSplitWriter
/*  25:    */ {
/*  26: 54 */   private static final Log LOG = LogFactory.getLog(JobSplitWriter.class);
/*  27:    */   private static final int splitVersion = 1;
/*  28:    */   private static final byte[] SPLIT_FILE_HEADER;
/*  29:    */   
/*  30:    */   static
/*  31:    */   {
/*  32:    */     try
/*  33:    */     {
/*  34: 60 */       SPLIT_FILE_HEADER = "SPL".getBytes("UTF-8");
/*  35:    */     }
/*  36:    */     catch (UnsupportedEncodingException u)
/*  37:    */     {
/*  38: 62 */       throw new RuntimeException(u);
/*  39:    */     }
/*  40:    */   }
/*  41:    */   
/*  42:    */   public static <T extends org.apache.hadoop.mapreduce.InputSplit> void createSplitFiles(Path jobSubmitDir, Configuration conf, FileSystem fs, List<org.apache.hadoop.mapreduce.InputSplit> splits)
/*  43:    */     throws IOException, InterruptedException
/*  44:    */   {
/*  45: 70 */     T[] array = (org.apache.hadoop.mapreduce.InputSplit[])splits.toArray(new org.apache.hadoop.mapreduce.InputSplit[splits.size()]);
/*  46: 71 */     createSplitFiles(jobSubmitDir, conf, fs, array);
/*  47:    */   }
/*  48:    */   
/*  49:    */   public static <T extends org.apache.hadoop.mapreduce.InputSplit> void createSplitFiles(Path jobSubmitDir, Configuration conf, FileSystem fs, T[] splits)
/*  50:    */     throws IOException, InterruptedException
/*  51:    */   {
/*  52: 77 */     FSDataOutputStream out = createFile(fs, JobSubmissionFiles.getJobSplitFile(jobSubmitDir), conf);
/*  53:    */     
/*  54: 79 */     JobSplit.SplitMetaInfo[] info = writeNewSplits(conf, splits, out);
/*  55: 80 */     out.close();
/*  56: 81 */     writeJobSplitMetaInfo(fs, JobSubmissionFiles.getJobSplitMetaFile(jobSubmitDir), new FsPermission(JobSubmissionFiles.JOB_FILE_PERMISSION), 1, info);
/*  57:    */   }
/*  58:    */   
/*  59:    */   public static void createSplitFiles(Path jobSubmitDir, Configuration conf, FileSystem fs, org.apache.hadoop.mapred.InputSplit[] splits)
/*  60:    */     throws IOException
/*  61:    */   {
/*  62: 90 */     FSDataOutputStream out = createFile(fs, JobSubmissionFiles.getJobSplitFile(jobSubmitDir), conf);
/*  63:    */     
/*  64: 92 */     JobSplit.SplitMetaInfo[] info = writeOldSplits(splits, out, conf);
/*  65: 93 */     out.close();
/*  66: 94 */     writeJobSplitMetaInfo(fs, JobSubmissionFiles.getJobSplitMetaFile(jobSubmitDir), new FsPermission(JobSubmissionFiles.JOB_FILE_PERMISSION), 1, info);
/*  67:    */   }
/*  68:    */   
/*  69:    */   private static FSDataOutputStream createFile(FileSystem fs, Path splitFile, Configuration job)
/*  70:    */     throws IOException
/*  71:    */   {
/*  72:101 */     FSDataOutputStream out = FileSystem.create(fs, splitFile, new FsPermission(JobSubmissionFiles.JOB_FILE_PERMISSION));
/*  73:    */     
/*  74:103 */     int replication = job.getInt("mapreduce.client.submit.file.replication", 10);
/*  75:104 */     fs.setReplication(splitFile, (short)replication);
/*  76:105 */     writeSplitHeader(out);
/*  77:106 */     return out;
/*  78:    */   }
/*  79:    */   
/*  80:    */   private static void writeSplitHeader(FSDataOutputStream out)
/*  81:    */     throws IOException
/*  82:    */   {
/*  83:110 */     out.write(SPLIT_FILE_HEADER);
/*  84:111 */     out.writeInt(1);
/*  85:    */   }
/*  86:    */   
/*  87:    */   private static <T extends org.apache.hadoop.mapreduce.InputSplit> JobSplit.SplitMetaInfo[] writeNewSplits(Configuration conf, T[] array, FSDataOutputStream out)
/*  88:    */     throws IOException, InterruptedException
/*  89:    */   {
/*  90:120 */     JobSplit.SplitMetaInfo[] info = new JobSplit.SplitMetaInfo[array.length];
/*  91:121 */     if (array.length != 0)
/*  92:    */     {
/*  93:122 */       SerializationFactory factory = new SerializationFactory(conf);
/*  94:123 */       int i = 0;
/*  95:124 */       int maxBlockLocations = conf.getInt("mapreduce.job.max.split.locations", 10);
/*  96:    */       
/*  97:126 */       long offset = out.getPos();
/*  98:127 */       for (T split : array)
/*  99:    */       {
/* 100:128 */         long prevCount = out.getPos();
/* 101:129 */         Text.writeString(out, split.getClass().getName());
/* 102:130 */         Serializer<T> serializer = factory.getSerializer(split.getClass());
/* 103:    */         
/* 104:132 */         serializer.open(out);
/* 105:133 */         serializer.serialize(split);
/* 106:134 */         long currCount = out.getPos();
/* 107:135 */         String[] locations = split.getLocations();
/* 108:136 */         if (locations.length > maxBlockLocations)
/* 109:    */         {
/* 110:137 */           LOG.warn("Max block location exceeded for split: " + split + " splitsize: " + locations.length + " maxsize: " + maxBlockLocations);
/* 111:    */           
/* 112:    */ 
/* 113:140 */           locations = (String[])Arrays.copyOf(locations, maxBlockLocations);
/* 114:    */         }
/* 115:142 */         info[(i++)] = new JobSplit.SplitMetaInfo(locations, offset, split.getLength());
/* 116:    */         
/* 117:    */ 
/* 118:    */ 
/* 119:146 */         offset += currCount - prevCount;
/* 120:    */       }
/* 121:    */     }
/* 122:149 */     return info;
/* 123:    */   }
/* 124:    */   
/* 125:    */   private static JobSplit.SplitMetaInfo[] writeOldSplits(org.apache.hadoop.mapred.InputSplit[] splits, FSDataOutputStream out, Configuration conf)
/* 126:    */     throws IOException
/* 127:    */   {
/* 128:155 */     JobSplit.SplitMetaInfo[] info = new JobSplit.SplitMetaInfo[splits.length];
/* 129:156 */     if (splits.length != 0)
/* 130:    */     {
/* 131:157 */       int i = 0;
/* 132:158 */       long offset = out.getPos();
/* 133:159 */       int maxBlockLocations = conf.getInt("mapreduce.job.max.split.locations", 10);
/* 134:161 */       for (org.apache.hadoop.mapred.InputSplit split : splits)
/* 135:    */       {
/* 136:162 */         long prevLen = out.getPos();
/* 137:163 */         Text.writeString(out, split.getClass().getName());
/* 138:164 */         split.write(out);
/* 139:165 */         long currLen = out.getPos();
/* 140:166 */         String[] locations = split.getLocations();
/* 141:167 */         if (locations.length > maxBlockLocations)
/* 142:    */         {
/* 143:168 */           LOG.warn("Max block location exceeded for split: " + split + " splitsize: " + locations.length + " maxsize: " + maxBlockLocations);
/* 144:    */           
/* 145:    */ 
/* 146:171 */           locations = (String[])Arrays.copyOf(locations, maxBlockLocations);
/* 147:    */         }
/* 148:173 */         info[(i++)] = new JobSplit.SplitMetaInfo(locations, offset, split.getLength());
/* 149:    */         
/* 150:    */ 
/* 151:176 */         offset += currLen - prevLen;
/* 152:    */       }
/* 153:    */     }
/* 154:179 */     return info;
/* 155:    */   }
/* 156:    */   
/* 157:    */   private static void writeJobSplitMetaInfo(FileSystem fs, Path filename, FsPermission p, int splitMetaInfoVersion, JobSplit.SplitMetaInfo[] allSplitMetaInfo)
/* 158:    */     throws IOException
/* 159:    */   {
/* 160:187 */     FSDataOutputStream out = FileSystem.create(fs, filename, p);
/* 161:    */     
/* 162:189 */     out.write(JobSplit.META_SPLIT_FILE_HEADER);
/* 163:190 */     WritableUtils.writeVInt(out, splitMetaInfoVersion);
/* 164:191 */     WritableUtils.writeVInt(out, allSplitMetaInfo.length);
/* 165:192 */     for (JobSplit.SplitMetaInfo splitMetaInfo : allSplitMetaInfo) {
/* 166:193 */       splitMetaInfo.write(out);
/* 167:    */     }
/* 168:195 */     out.close();
/* 169:    */   }
/* 170:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.split.JobSplitWriter
 * JD-Core Version:    0.7.0.1
 */