/*   1:    */ package org.apache.hadoop.mapreduce.lib.partition;
/*   2:    */ 
/*   3:    */ import java.io.UnsupportedEncodingException;
/*   4:    */ import java.util.List;
/*   5:    */ import org.apache.commons.logging.Log;
/*   6:    */ import org.apache.commons.logging.LogFactory;
/*   7:    */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*   8:    */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*   9:    */ import org.apache.hadoop.conf.Configurable;
/*  10:    */ import org.apache.hadoop.conf.Configuration;
/*  11:    */ import org.apache.hadoop.mapreduce.Job;
/*  12:    */ import org.apache.hadoop.mapreduce.JobContext;
/*  13:    */ import org.apache.hadoop.mapreduce.Partitioner;
/*  14:    */ 
/*  15:    */ @InterfaceAudience.Public
/*  16:    */ @InterfaceStability.Stable
/*  17:    */ public class KeyFieldBasedPartitioner<K2, V2>
/*  18:    */   extends Partitioner<K2, V2>
/*  19:    */   implements Configurable
/*  20:    */ {
/*  21: 54 */   private static final Log LOG = LogFactory.getLog(KeyFieldBasedPartitioner.class.getName());
/*  22: 56 */   public static String PARTITIONER_OPTIONS = "mapreduce.partition.keypartitioner.options";
/*  23:    */   private int numOfPartitionFields;
/*  24: 60 */   private KeyFieldHelper keyFieldHelper = new KeyFieldHelper();
/*  25:    */   private Configuration conf;
/*  26:    */   
/*  27:    */   public void setConf(Configuration conf)
/*  28:    */   {
/*  29: 65 */     this.conf = conf;
/*  30: 66 */     this.keyFieldHelper = new KeyFieldHelper();
/*  31: 67 */     String keyFieldSeparator = conf.get("mapreduce.map.output.key.field.separator", "\t");
/*  32:    */     
/*  33: 69 */     this.keyFieldHelper.setKeyFieldSeparator(keyFieldSeparator);
/*  34: 70 */     if (conf.get("num.key.fields.for.partition") != null)
/*  35:    */     {
/*  36: 71 */       LOG.warn("Using deprecated num.key.fields.for.partition. Use mapreduce.partition.keypartitioner.options instead");
/*  37:    */       
/*  38: 73 */       this.numOfPartitionFields = conf.getInt("num.key.fields.for.partition", 0);
/*  39: 74 */       this.keyFieldHelper.setKeyFieldSpec(1, this.numOfPartitionFields);
/*  40:    */     }
/*  41:    */     else
/*  42:    */     {
/*  43: 76 */       String option = conf.get(PARTITIONER_OPTIONS);
/*  44: 77 */       this.keyFieldHelper.parseOption(option);
/*  45:    */     }
/*  46:    */   }
/*  47:    */   
/*  48:    */   public Configuration getConf()
/*  49:    */   {
/*  50: 82 */     return this.conf;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public int getPartition(K2 key, V2 value, int numReduceTasks)
/*  54:    */   {
/*  55: 88 */     List<KeyFieldHelper.KeyDescription> allKeySpecs = this.keyFieldHelper.keySpecs();
/*  56: 89 */     if (allKeySpecs.size() == 0) {
/*  57: 90 */       return getPartition(key.toString().hashCode(), numReduceTasks);
/*  58:    */     }
/*  59:    */     byte[] keyBytes;
/*  60:    */     try
/*  61:    */     {
/*  62: 94 */       keyBytes = key.toString().getBytes("UTF-8");
/*  63:    */     }
/*  64:    */     catch (UnsupportedEncodingException e)
/*  65:    */     {
/*  66: 96 */       throw new RuntimeException("The current system does not support UTF-8 encoding!", e);
/*  67:    */     }
/*  68:100 */     if (keyBytes.length == 0) {
/*  69:101 */       return 0;
/*  70:    */     }
/*  71:104 */     int[] lengthIndicesFirst = this.keyFieldHelper.getWordLengths(keyBytes, 0, keyBytes.length);
/*  72:    */     
/*  73:106 */     int currentHash = 0;
/*  74:107 */     for (KeyFieldHelper.KeyDescription keySpec : allKeySpecs)
/*  75:    */     {
/*  76:108 */       int startChar = this.keyFieldHelper.getStartOffset(keyBytes, 0, keyBytes.length, lengthIndicesFirst, keySpec);
/*  77:111 */       if (startChar >= 0)
/*  78:    */       {
/*  79:114 */         int endChar = this.keyFieldHelper.getEndOffset(keyBytes, 0, keyBytes.length, lengthIndicesFirst, keySpec);
/*  80:    */         
/*  81:116 */         currentHash = hashCode(keyBytes, startChar, endChar, currentHash);
/*  82:    */       }
/*  83:    */     }
/*  84:119 */     return getPartition(currentHash, numReduceTasks);
/*  85:    */   }
/*  86:    */   
/*  87:    */   protected int hashCode(byte[] b, int start, int end, int currentHash)
/*  88:    */   {
/*  89:123 */     for (int i = start; i <= end; i++) {
/*  90:124 */       currentHash = 31 * currentHash + b[i];
/*  91:    */     }
/*  92:126 */     return currentHash;
/*  93:    */   }
/*  94:    */   
/*  95:    */   protected int getPartition(int hash, int numReduceTasks)
/*  96:    */   {
/*  97:130 */     return (hash & 0x7FFFFFFF) % numReduceTasks;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public void setKeyFieldPartitionerOptions(Job job, String keySpec)
/* 101:    */   {
/* 102:147 */     job.getConfiguration().set(PARTITIONER_OPTIONS, keySpec);
/* 103:    */   }
/* 104:    */   
/* 105:    */   public String getKeyFieldPartitionerOption(JobContext job)
/* 106:    */   {
/* 107:154 */     return job.getConfiguration().get(PARTITIONER_OPTIONS);
/* 108:    */   }
/* 109:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.lib.partition.KeyFieldBasedPartitioner
 * JD-Core Version:    0.7.0.1
 */