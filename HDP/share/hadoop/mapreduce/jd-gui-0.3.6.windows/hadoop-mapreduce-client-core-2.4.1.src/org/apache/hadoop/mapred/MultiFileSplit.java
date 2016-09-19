/*  1:   */ package org.apache.hadoop.mapred;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import java.net.URI;
/*  5:   */ import java.util.HashSet;
/*  6:   */ import java.util.Set;
/*  7:   */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*  8:   */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*  9:   */ import org.apache.hadoop.fs.BlockLocation;
/* 10:   */ import org.apache.hadoop.fs.FileStatus;
/* 11:   */ import org.apache.hadoop.fs.FileSystem;
/* 12:   */ import org.apache.hadoop.fs.Path;
/* 13:   */ import org.apache.hadoop.mapred.lib.CombineFileSplit;
/* 14:   */ 
/* 15:   */ @InterfaceAudience.Public
/* 16:   */ @InterfaceStability.Stable
/* 17:   */ public class MultiFileSplit
/* 18:   */   extends CombineFileSplit
/* 19:   */ {
/* 20:   */   MultiFileSplit() {}
/* 21:   */   
/* 22:   */   public MultiFileSplit(JobConf job, Path[] files, long[] lengths)
/* 23:   */   {
/* 24:49 */     super(job, files, lengths);
/* 25:   */   }
/* 26:   */   
/* 27:   */   public String[] getLocations()
/* 28:   */     throws IOException
/* 29:   */   {
/* 30:53 */     HashSet<String> hostSet = new HashSet();
/* 31:54 */     for (Path file : getPaths())
/* 32:   */     {
/* 33:55 */       FileSystem fs = file.getFileSystem(getJob());
/* 34:56 */       FileStatus status = fs.getFileStatus(file);
/* 35:57 */       BlockLocation[] blkLocations = fs.getFileBlockLocations(status, 0L, status.getLen());
/* 36:59 */       if ((blkLocations != null) && (blkLocations.length > 0)) {
/* 37:60 */         addToSet(hostSet, blkLocations[0].getHosts());
/* 38:   */       }
/* 39:   */     }
/* 40:63 */     return (String[])hostSet.toArray(new String[hostSet.size()]);
/* 41:   */   }
/* 42:   */   
/* 43:   */   private void addToSet(Set<String> set, String[] array)
/* 44:   */   {
/* 45:67 */     for (String s : array) {
/* 46:68 */       set.add(s);
/* 47:   */     }
/* 48:   */   }
/* 49:   */   
/* 50:   */   public String toString()
/* 51:   */   {
/* 52:73 */     StringBuffer sb = new StringBuffer();
/* 53:74 */     for (int i = 0; i < getPaths().length; i++)
/* 54:   */     {
/* 55:75 */       sb.append(getPath(i).toUri().getPath() + ":0+" + getLength(i));
/* 56:76 */       if (i < getPaths().length - 1) {
/* 57:77 */         sb.append("\n");
/* 58:   */       }
/* 59:   */     }
/* 60:81 */     return sb.toString();
/* 61:   */   }
/* 62:   */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.MultiFileSplit
 * JD-Core Version:    0.7.0.1
 */