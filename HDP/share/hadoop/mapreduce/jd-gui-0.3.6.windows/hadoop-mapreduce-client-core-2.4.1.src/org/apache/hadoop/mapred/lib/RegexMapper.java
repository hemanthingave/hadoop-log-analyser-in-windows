/*  1:   */ package org.apache.hadoop.mapred.lib;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import java.util.regex.Matcher;
/*  5:   */ import java.util.regex.Pattern;
/*  6:   */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*  7:   */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*  8:   */ import org.apache.hadoop.io.LongWritable;
/*  9:   */ import org.apache.hadoop.io.Text;
/* 10:   */ import org.apache.hadoop.mapred.JobConf;
/* 11:   */ import org.apache.hadoop.mapred.MapReduceBase;
/* 12:   */ import org.apache.hadoop.mapred.Mapper;
/* 13:   */ import org.apache.hadoop.mapred.OutputCollector;
/* 14:   */ import org.apache.hadoop.mapred.Reporter;
/* 15:   */ 
/* 16:   */ @InterfaceAudience.Public
/* 17:   */ @InterfaceStability.Stable
/* 18:   */ public class RegexMapper<K>
/* 19:   */   extends MapReduceBase
/* 20:   */   implements Mapper<K, Text, Text, LongWritable>
/* 21:   */ {
/* 22:   */   private Pattern pattern;
/* 23:   */   private int group;
/* 24:   */   
/* 25:   */   public void configure(JobConf job)
/* 26:   */   {
/* 27:48 */     this.pattern = Pattern.compile(job.get(org.apache.hadoop.mapreduce.lib.map.RegexMapper.PATTERN));
/* 28:   */     
/* 29:50 */     this.group = job.getInt(org.apache.hadoop.mapreduce.lib.map.RegexMapper.GROUP, 0);
/* 30:   */   }
/* 31:   */   
/* 32:   */   public void map(K key, Text value, OutputCollector<Text, LongWritable> output, Reporter reporter)
/* 33:   */     throws IOException
/* 34:   */   {
/* 35:58 */     String text = value.toString();
/* 36:59 */     Matcher matcher = this.pattern.matcher(text);
/* 37:60 */     while (matcher.find()) {
/* 38:61 */       output.collect(new Text(matcher.group(this.group)), new LongWritable(1L));
/* 39:   */     }
/* 40:   */   }
/* 41:   */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.lib.RegexMapper
 * JD-Core Version:    0.7.0.1
 */