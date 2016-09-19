/*   1:    */ package org.apache.hadoop.mapreduce.lib.fieldsel;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import java.util.List;
/*   6:    */ import org.apache.commons.logging.Log;
/*   7:    */ import org.apache.commons.logging.LogFactory;
/*   8:    */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*   9:    */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*  10:    */ import org.apache.hadoop.conf.Configuration;
/*  11:    */ import org.apache.hadoop.io.Text;
/*  12:    */ import org.apache.hadoop.mapreduce.Reducer;
/*  13:    */ import org.apache.hadoop.mapreduce.Reducer.Context;
/*  14:    */ 
/*  15:    */ @InterfaceAudience.Public
/*  16:    */ @InterfaceStability.Stable
/*  17:    */ public class FieldSelectionReducer<K, V>
/*  18:    */   extends Reducer<Text, Text, Text, Text>
/*  19:    */ {
/*  20: 63 */   private String fieldSeparator = "\t";
/*  21:    */   private String reduceOutputKeyValueSpec;
/*  22: 67 */   private List<Integer> reduceOutputKeyFieldList = new ArrayList();
/*  23: 69 */   private List<Integer> reduceOutputValueFieldList = new ArrayList();
/*  24: 71 */   private int allReduceValueFieldsFrom = -1;
/*  25: 73 */   public static final Log LOG = LogFactory.getLog("FieldSelectionMapReduce");
/*  26:    */   
/*  27:    */   public void setup(Reducer<Text, Text, Text, Text>.Context context)
/*  28:    */     throws IOException, InterruptedException
/*  29:    */   {
/*  30: 77 */     Configuration conf = context.getConfiguration();
/*  31:    */     
/*  32: 79 */     this.fieldSeparator = conf.get("mapreduce.fieldsel.data.field.separator", "\t");
/*  33:    */     
/*  34:    */ 
/*  35: 82 */     this.reduceOutputKeyValueSpec = conf.get("mapreduce.fieldsel.reduce.output.key.value.fields.spec", "0-:");
/*  36:    */     
/*  37:    */ 
/*  38: 85 */     this.allReduceValueFieldsFrom = FieldSelectionHelper.parseOutputKeyValueSpec(this.reduceOutputKeyValueSpec, this.reduceOutputKeyFieldList, this.reduceOutputValueFieldList);
/*  39:    */     
/*  40:    */ 
/*  41:    */ 
/*  42: 89 */     LOG.info(FieldSelectionHelper.specToString(this.fieldSeparator, this.reduceOutputKeyValueSpec, this.allReduceValueFieldsFrom, this.reduceOutputKeyFieldList, this.reduceOutputValueFieldList));
/*  43:    */   }
/*  44:    */   
/*  45:    */   public void reduce(Text key, Iterable<Text> values, Reducer<Text, Text, Text, Text>.Context context)
/*  46:    */     throws IOException, InterruptedException
/*  47:    */   {
/*  48: 96 */     String keyStr = key.toString() + this.fieldSeparator;
/*  49: 98 */     for (Text val : values)
/*  50:    */     {
/*  51: 99 */       FieldSelectionHelper helper = new FieldSelectionHelper();
/*  52:100 */       helper.extractOutputKeyValue(keyStr, val.toString(), this.fieldSeparator, this.reduceOutputKeyFieldList, this.reduceOutputValueFieldList, this.allReduceValueFieldsFrom, false, false);
/*  53:    */       
/*  54:    */ 
/*  55:103 */       context.write(helper.getKey(), helper.getValue());
/*  56:    */     }
/*  57:    */   }
/*  58:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.lib.fieldsel.FieldSelectionReducer
 * JD-Core Version:    0.7.0.1
 */