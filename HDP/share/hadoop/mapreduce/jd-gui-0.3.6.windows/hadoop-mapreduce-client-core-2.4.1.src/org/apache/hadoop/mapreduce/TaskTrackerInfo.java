/*   1:    */ package org.apache.hadoop.mapreduce;
/*   2:    */ 
/*   3:    */ import java.io.DataInput;
/*   4:    */ import java.io.DataOutput;
/*   5:    */ import java.io.IOException;
/*   6:    */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*   7:    */ import org.apache.hadoop.classification.InterfaceStability.Evolving;
/*   8:    */ import org.apache.hadoop.io.Text;
/*   9:    */ import org.apache.hadoop.io.Writable;
/*  10:    */ 
/*  11:    */ @InterfaceAudience.Public
/*  12:    */ @InterfaceStability.Evolving
/*  13:    */ public class TaskTrackerInfo
/*  14:    */   implements Writable
/*  15:    */ {
/*  16:    */   String name;
/*  17: 36 */   boolean isBlacklisted = false;
/*  18: 37 */   String reasonForBlacklist = "";
/*  19: 38 */   String blacklistReport = "";
/*  20:    */   
/*  21:    */   public TaskTrackerInfo() {}
/*  22:    */   
/*  23:    */   public TaskTrackerInfo(String name)
/*  24:    */   {
/*  25: 44 */     this.name = name;
/*  26:    */   }
/*  27:    */   
/*  28:    */   public TaskTrackerInfo(String name, String reasonForBlacklist, String report)
/*  29:    */   {
/*  30: 50 */     this.name = name;
/*  31: 51 */     this.isBlacklisted = true;
/*  32: 52 */     this.reasonForBlacklist = reasonForBlacklist;
/*  33: 53 */     this.blacklistReport = report;
/*  34:    */   }
/*  35:    */   
/*  36:    */   public String getTaskTrackerName()
/*  37:    */   {
/*  38: 62 */     return this.name;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public boolean isBlacklisted()
/*  42:    */   {
/*  43: 71 */     return this.isBlacklisted;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public String getReasonForBlacklist()
/*  47:    */   {
/*  48: 80 */     return this.reasonForBlacklist;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public String getBlacklistReport()
/*  52:    */   {
/*  53: 89 */     return this.blacklistReport;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public void readFields(DataInput in)
/*  57:    */     throws IOException
/*  58:    */   {
/*  59: 94 */     this.name = Text.readString(in);
/*  60: 95 */     this.isBlacklisted = in.readBoolean();
/*  61: 96 */     this.reasonForBlacklist = Text.readString(in);
/*  62: 97 */     this.blacklistReport = Text.readString(in);
/*  63:    */   }
/*  64:    */   
/*  65:    */   public void write(DataOutput out)
/*  66:    */     throws IOException
/*  67:    */   {
/*  68:102 */     Text.writeString(out, this.name);
/*  69:103 */     out.writeBoolean(this.isBlacklisted);
/*  70:104 */     Text.writeString(out, this.reasonForBlacklist);
/*  71:105 */     Text.writeString(out, this.blacklistReport);
/*  72:    */   }
/*  73:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.TaskTrackerInfo
 * JD-Core Version:    0.7.0.1
 */