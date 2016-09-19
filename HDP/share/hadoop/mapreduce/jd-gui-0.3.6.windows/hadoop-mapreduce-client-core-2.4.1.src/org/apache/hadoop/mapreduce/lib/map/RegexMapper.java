/*  1:   */ package org.apache.hadoop.mapreduce.lib.map;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import java.util.regex.Matcher;
/*  5:   */ import java.util.regex.Pattern;
/*  6:   */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*  7:   */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*  8:   */ import org.apache.hadoop.conf.Configuration;
/*  9:   */ import org.apache.hadoop.io.LongWritable;
/* 10:   */ import org.apache.hadoop.io.Text;
/* 11:   */ import org.apache.hadoop.mapreduce.Mapper;
/* 12:   */ import org.apache.hadoop.mapreduce.Mapper.Context;
/* 13:   */ 
/* 14:   */ @InterfaceAudience.Public
/* 15:   */ @InterfaceStability.Stable
/* 16:   */ public class RegexMapper<K>
/* 17:   */   extends Mapper<K, Text, Text, LongWritable>
/* 18:   */ {
/* 19:38 */   public static String PATTERN = "mapreduce.mapper.regex";
/* 20:39 */   public static String GROUP = "mapreduce.mapper.regexmapper..group";
/* 21:   */   private Pattern pattern;
/* 22:   */   private int group;
/* 23:   */   
/* 24:   */   public void setup(Mapper<K, Text, Text, LongWritable>.Context context)
/* 25:   */   {
/* 26:44 */     Configuration conf = context.getConfiguration();
/* 27:45 */     this.pattern = Pattern.compile(conf.get(PATTERN));
/* 28:46 */     this.group = conf.getInt(GROUP, 0);
/* 29:   */   }
/* 30:   */   
/* 31:   */   public void map(K key, Text value, Mapper<K, Text, Text, LongWritable>.Context context)
/* 32:   */     throws IOException, InterruptedException
/* 33:   */   {
/* 34:52 */     String text = value.toString();
/* 35:53 */     Matcher matcher = this.pattern.matcher(text);
/* 36:54 */     while (matcher.find()) {
/* 37:55 */       context.write(new Text(matcher.group(this.group)), new LongWritable(1L));
/* 38:   */     }
/* 39:   */   }
/* 40:   */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.lib.map.RegexMapper
 * JD-Core Version:    0.7.0.1
 */