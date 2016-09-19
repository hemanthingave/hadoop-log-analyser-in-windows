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
/*  12:    */ import org.apache.hadoop.mapreduce.Mapper;
/*  13:    */ import org.apache.hadoop.mapreduce.Mapper.Context;
/*  14:    */ import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
/*  15:    */ 
/*  16:    */ @InterfaceAudience.Public
/*  17:    */ @InterfaceStability.Stable
/*  18:    */ public class FieldSelectionMapper<K, V>
/*  19:    */   extends Mapper<K, V, Text, Text>
/*  20:    */ {
/*  21:    */   private String mapOutputKeyValueSpec;
/*  22:    */   private boolean ignoreInputKey;
/*  23: 68 */   private String fieldSeparator = "\t";
/*  24: 70 */   private List<Integer> mapOutputKeyFieldList = new ArrayList();
/*  25: 72 */   private List<Integer> mapOutputValueFieldList = new ArrayList();
/*  26: 74 */   private int allMapValueFieldsFrom = -1;
/*  27: 76 */   public static final Log LOG = LogFactory.getLog("FieldSelectionMapReduce");
/*  28:    */   
/*  29:    */   public void setup(Mapper<K, V, Text, Text>.Context context)
/*  30:    */     throws IOException, InterruptedException
/*  31:    */   {
/*  32: 80 */     Configuration conf = context.getConfiguration();
/*  33: 81 */     this.fieldSeparator = conf.get("mapreduce.fieldsel.data.field.separator", "\t");
/*  34:    */     
/*  35: 83 */     this.mapOutputKeyValueSpec = conf.get("mapreduce.fieldsel.map.output.key.value.fields.spec", "0-:");
/*  36:    */     try
/*  37:    */     {
/*  38: 86 */       this.ignoreInputKey = TextInputFormat.class.getCanonicalName().equals(context.getInputFormatClass().getCanonicalName());
/*  39:    */     }
/*  40:    */     catch (ClassNotFoundException e)
/*  41:    */     {
/*  42: 89 */       throw new IOException("Input format class not found", e);
/*  43:    */     }
/*  44: 91 */     this.allMapValueFieldsFrom = FieldSelectionHelper.parseOutputKeyValueSpec(this.mapOutputKeyValueSpec, this.mapOutputKeyFieldList, this.mapOutputValueFieldList);
/*  45:    */     
/*  46: 93 */     LOG.info(FieldSelectionHelper.specToString(this.fieldSeparator, this.mapOutputKeyValueSpec, this.allMapValueFieldsFrom, this.mapOutputKeyFieldList, this.mapOutputValueFieldList) + "\nignoreInputKey:" + this.ignoreInputKey);
/*  47:    */   }
/*  48:    */   
/*  49:    */   public void map(K key, V val, Mapper<K, V, Text, Text>.Context context)
/*  50:    */     throws IOException, InterruptedException
/*  51:    */   {
/*  52:103 */     FieldSelectionHelper helper = new FieldSelectionHelper(FieldSelectionHelper.emptyText, FieldSelectionHelper.emptyText);
/*  53:    */     
/*  54:105 */     helper.extractOutputKeyValue(key.toString(), val.toString(), this.fieldSeparator, this.mapOutputKeyFieldList, this.mapOutputValueFieldList, this.allMapValueFieldsFrom, this.ignoreInputKey, true);
/*  55:    */     
/*  56:    */ 
/*  57:108 */     context.write(helper.getKey(), helper.getValue());
/*  58:    */   }
/*  59:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.lib.fieldsel.FieldSelectionMapper
 * JD-Core Version:    0.7.0.1
 */