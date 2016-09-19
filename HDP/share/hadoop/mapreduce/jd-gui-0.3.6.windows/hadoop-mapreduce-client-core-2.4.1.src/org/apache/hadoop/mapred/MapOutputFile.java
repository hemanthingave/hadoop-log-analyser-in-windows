/*   1:    */ package org.apache.hadoop.mapred;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import org.apache.hadoop.classification.InterfaceAudience.Private;
/*   5:    */ import org.apache.hadoop.classification.InterfaceStability.Unstable;
/*   6:    */ import org.apache.hadoop.conf.Configurable;
/*   7:    */ import org.apache.hadoop.conf.Configuration;
/*   8:    */ import org.apache.hadoop.fs.Path;
/*   9:    */ import org.apache.hadoop.mapreduce.TaskID;
/*  10:    */ 
/*  11:    */ @InterfaceAudience.Private
/*  12:    */ @InterfaceStability.Unstable
/*  13:    */ public abstract class MapOutputFile
/*  14:    */   implements Configurable
/*  15:    */ {
/*  16:    */   private Configuration conf;
/*  17:    */   static final String MAP_OUTPUT_FILENAME_STRING = "file.out";
/*  18:    */   static final String MAP_OUTPUT_INDEX_SUFFIX_STRING = ".index";
/*  19:    */   static final String REDUCE_INPUT_FILE_FORMAT_STRING = "%s/map_%d.out";
/*  20:    */   
/*  21:    */   public abstract Path getOutputFile()
/*  22:    */     throws IOException;
/*  23:    */   
/*  24:    */   public abstract Path getOutputFileForWrite(long paramLong)
/*  25:    */     throws IOException;
/*  26:    */   
/*  27:    */   public abstract Path getOutputFileForWriteInVolume(Path paramPath);
/*  28:    */   
/*  29:    */   public abstract Path getOutputIndexFile()
/*  30:    */     throws IOException;
/*  31:    */   
/*  32:    */   public abstract Path getOutputIndexFileForWrite(long paramLong)
/*  33:    */     throws IOException;
/*  34:    */   
/*  35:    */   public abstract Path getOutputIndexFileForWriteInVolume(Path paramPath);
/*  36:    */   
/*  37:    */   public abstract Path getSpillFile(int paramInt)
/*  38:    */     throws IOException;
/*  39:    */   
/*  40:    */   public abstract Path getSpillFileForWrite(int paramInt, long paramLong)
/*  41:    */     throws IOException;
/*  42:    */   
/*  43:    */   public abstract Path getSpillIndexFile(int paramInt)
/*  44:    */     throws IOException;
/*  45:    */   
/*  46:    */   public abstract Path getSpillIndexFileForWrite(int paramInt, long paramLong)
/*  47:    */     throws IOException;
/*  48:    */   
/*  49:    */   public abstract Path getInputFile(int paramInt)
/*  50:    */     throws IOException;
/*  51:    */   
/*  52:    */   public abstract Path getInputFileForWrite(TaskID paramTaskID, long paramLong)
/*  53:    */     throws IOException;
/*  54:    */   
/*  55:    */   public abstract void removeAll()
/*  56:    */     throws IOException;
/*  57:    */   
/*  58:    */   public void setConf(Configuration conf)
/*  59:    */   {
/*  60:160 */     this.conf = conf;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public Configuration getConf()
/*  64:    */   {
/*  65:165 */     return this.conf;
/*  66:    */   }
/*  67:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.MapOutputFile
 * JD-Core Version:    0.7.0.1
 */