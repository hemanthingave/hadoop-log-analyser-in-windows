/*  1:   */ package org.apache.hadoop.mapred;
/*  2:   */ 
/*  3:   */ import java.io.DataInput;
/*  4:   */ import java.io.DataOutput;
/*  5:   */ import java.io.IOException;
/*  6:   */ import org.apache.hadoop.fs.Path;
/*  7:   */ import org.apache.hadoop.io.Text;
/*  8:   */ import org.apache.hadoop.io.Writable;
/*  9:   */ import org.apache.hadoop.io.WritableUtils;
/* 10:   */ import org.apache.hadoop.mapreduce.JobID;
/* 11:   */ 
/* 12:   */ class JobInfo
/* 13:   */   implements Writable
/* 14:   */ {
/* 15:   */   private JobID id;
/* 16:   */   private Text user;
/* 17:   */   private Path jobSubmitDir;
/* 18:   */   
/* 19:   */   public JobInfo() {}
/* 20:   */   
/* 21:   */   public JobInfo(JobID id, Text user, Path jobSubmitDir)
/* 22:   */   {
/* 23:44 */     this.id = id;
/* 24:45 */     this.user = user;
/* 25:46 */     this.jobSubmitDir = jobSubmitDir;
/* 26:   */   }
/* 27:   */   
/* 28:   */   public JobID getJobID()
/* 29:   */   {
/* 30:53 */     return this.id;
/* 31:   */   }
/* 32:   */   
/* 33:   */   public Text getUser()
/* 34:   */   {
/* 35:60 */     return this.user;
/* 36:   */   }
/* 37:   */   
/* 38:   */   public Path getJobSubmitDir()
/* 39:   */   {
/* 40:67 */     return this.jobSubmitDir;
/* 41:   */   }
/* 42:   */   
/* 43:   */   public void readFields(DataInput in)
/* 44:   */     throws IOException
/* 45:   */   {
/* 46:71 */     this.id = new JobID();
/* 47:72 */     this.id.readFields(in);
/* 48:73 */     this.user = new Text();
/* 49:74 */     this.user.readFields(in);
/* 50:75 */     this.jobSubmitDir = new Path(WritableUtils.readString(in));
/* 51:   */   }
/* 52:   */   
/* 53:   */   public void write(DataOutput out)
/* 54:   */     throws IOException
/* 55:   */   {
/* 56:79 */     this.id.write(out);
/* 57:80 */     this.user.write(out);
/* 58:81 */     WritableUtils.writeString(out, this.jobSubmitDir.toString());
/* 59:   */   }
/* 60:   */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.JobInfo
 * JD-Core Version:    0.7.0.1
 */