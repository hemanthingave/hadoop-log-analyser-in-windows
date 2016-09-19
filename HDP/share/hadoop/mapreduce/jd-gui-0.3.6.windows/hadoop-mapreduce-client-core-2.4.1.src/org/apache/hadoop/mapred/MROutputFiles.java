/*   1:    */ package org.apache.hadoop.mapred;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import org.apache.hadoop.classification.InterfaceAudience.Private;
/*   5:    */ import org.apache.hadoop.classification.InterfaceStability.Unstable;
/*   6:    */ import org.apache.hadoop.conf.Configuration;
/*   7:    */ import org.apache.hadoop.fs.LocalDirAllocator;
/*   8:    */ import org.apache.hadoop.fs.Path;
/*   9:    */ import org.apache.hadoop.mapreduce.TaskID;
/*  10:    */ 
/*  11:    */ @InterfaceAudience.Private
/*  12:    */ @InterfaceStability.Unstable
/*  13:    */ public class MROutputFiles
/*  14:    */   extends MapOutputFile
/*  15:    */ {
/*  16: 42 */   private LocalDirAllocator lDirAlloc = new LocalDirAllocator("mapreduce.cluster.local.dir");
/*  17:    */   
/*  18:    */   public Path getOutputFile()
/*  19:    */     throws IOException
/*  20:    */   {
/*  21: 57 */     return this.lDirAlloc.getLocalPathToRead("output/file.out", getConf());
/*  22:    */   }
/*  23:    */   
/*  24:    */   public Path getOutputFileForWrite(long size)
/*  25:    */     throws IOException
/*  26:    */   {
/*  27: 71 */     return this.lDirAlloc.getLocalPathForWrite("output/file.out", size, getConf());
/*  28:    */   }
/*  29:    */   
/*  30:    */   public Path getOutputFileForWriteInVolume(Path existing)
/*  31:    */   {
/*  32: 80 */     return new Path(existing.getParent(), "file.out");
/*  33:    */   }
/*  34:    */   
/*  35:    */   public Path getOutputIndexFile()
/*  36:    */     throws IOException
/*  37:    */   {
/*  38: 92 */     return this.lDirAlloc.getLocalPathToRead("output/file.out.index", getConf());
/*  39:    */   }
/*  40:    */   
/*  41:    */   public Path getOutputIndexFileForWrite(long size)
/*  42:    */     throws IOException
/*  43:    */   {
/*  44:107 */     return this.lDirAlloc.getLocalPathForWrite("output/file.out.index", size, getConf());
/*  45:    */   }
/*  46:    */   
/*  47:    */   public Path getOutputIndexFileForWriteInVolume(Path existing)
/*  48:    */   {
/*  49:117 */     return new Path(existing.getParent(), "file.out.index");
/*  50:    */   }
/*  51:    */   
/*  52:    */   public Path getSpillFile(int spillNumber)
/*  53:    */     throws IOException
/*  54:    */   {
/*  55:131 */     return this.lDirAlloc.getLocalPathToRead("output/spill" + spillNumber + ".out", getConf());
/*  56:    */   }
/*  57:    */   
/*  58:    */   public Path getSpillFileForWrite(int spillNumber, long size)
/*  59:    */     throws IOException
/*  60:    */   {
/*  61:146 */     return this.lDirAlloc.getLocalPathForWrite("output/spill" + spillNumber + ".out", size, getConf());
/*  62:    */   }
/*  63:    */   
/*  64:    */   public Path getSpillIndexFile(int spillNumber)
/*  65:    */     throws IOException
/*  66:    */   {
/*  67:160 */     return this.lDirAlloc.getLocalPathToRead("output/spill" + spillNumber + ".out.index", getConf());
/*  68:    */   }
/*  69:    */   
/*  70:    */   public Path getSpillIndexFileForWrite(int spillNumber, long size)
/*  71:    */     throws IOException
/*  72:    */   {
/*  73:175 */     return this.lDirAlloc.getLocalPathForWrite("output/spill" + spillNumber + ".out.index", size, getConf());
/*  74:    */   }
/*  75:    */   
/*  76:    */   public Path getInputFile(int mapId)
/*  77:    */     throws IOException
/*  78:    */   {
/*  79:189 */     return this.lDirAlloc.getLocalPathToRead(String.format("%s/map_%d.out", new Object[] { "output", Integer.valueOf(mapId) }), getConf());
/*  80:    */   }
/*  81:    */   
/*  82:    */   public Path getInputFileForWrite(TaskID mapId, long size)
/*  83:    */     throws IOException
/*  84:    */   {
/*  85:206 */     return this.lDirAlloc.getLocalPathForWrite(String.format("%s/map_%d.out", new Object[] { "output", Integer.valueOf(mapId.getId()) }), size, getConf());
/*  86:    */   }
/*  87:    */   
/*  88:    */   public void removeAll()
/*  89:    */     throws IOException
/*  90:    */   {
/*  91:215 */     ((JobConf)getConf()).deleteLocalFiles("output");
/*  92:    */   }
/*  93:    */   
/*  94:    */   public void setConf(Configuration conf)
/*  95:    */   {
/*  96:220 */     if (!(conf instanceof JobConf)) {
/*  97:221 */       conf = new JobConf(conf);
/*  98:    */     }
/*  99:223 */     super.setConf(conf);
/* 100:    */   }
/* 101:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.MROutputFiles
 * JD-Core Version:    0.7.0.1
 */