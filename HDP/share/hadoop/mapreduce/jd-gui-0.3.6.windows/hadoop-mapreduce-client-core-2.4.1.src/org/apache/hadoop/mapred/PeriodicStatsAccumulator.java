/*   1:    */ package org.apache.hadoop.mapred;
/*   2:    */ 
/*   3:    */ import org.apache.hadoop.classification.InterfaceAudience.Private;
/*   4:    */ import org.apache.hadoop.classification.InterfaceStability.Unstable;
/*   5:    */ 
/*   6:    */ @InterfaceAudience.Private
/*   7:    */ @InterfaceStability.Unstable
/*   8:    */ public abstract class PeriodicStatsAccumulator
/*   9:    */ {
/*  10:    */   protected final int count;
/*  11:    */   protected final int[] values;
/*  12:    */   
/*  13:    */   static class StatsetState
/*  14:    */   {
/*  15: 62 */     int oldValue = 0;
/*  16: 63 */     double oldProgress = 0.0D;
/*  17: 65 */     double currentAccumulation = 0.0D;
/*  18:    */   }
/*  19:    */   
/*  20: 71 */   StatsetState state = new StatsetState();
/*  21:    */   
/*  22:    */   PeriodicStatsAccumulator(int count)
/*  23:    */   {
/*  24: 74 */     this.count = count;
/*  25: 75 */     this.values = new int[count];
/*  26: 76 */     for (int i = 0; i < count; i++) {
/*  27: 77 */       this.values[i] = -1;
/*  28:    */     }
/*  29:    */   }
/*  30:    */   
/*  31:    */   protected int[] getValues()
/*  32:    */   {
/*  33: 82 */     return this.values;
/*  34:    */   }
/*  35:    */   
/*  36:    */   protected abstract void extendInternal(double paramDouble, int paramInt);
/*  37:    */   
/*  38:    */   protected void initializeInterval()
/*  39:    */   {
/*  40:119 */     this.state.currentAccumulation = 0.0D;
/*  41:    */   }
/*  42:    */   
/*  43:    */   protected void extend(double newProgress, int newValue)
/*  44:    */   {
/*  45:146 */     if ((this.state == null) || (newProgress < this.state.oldProgress)) {
/*  46:147 */       return;
/*  47:    */     }
/*  48:151 */     int oldIndex = (int)(this.state.oldProgress * this.count);
/*  49:152 */     int newIndex = (int)(newProgress * this.count);
/*  50:153 */     int originalOldValue = this.state.oldValue;
/*  51:    */     
/*  52:155 */     double fullValueDistance = newValue - this.state.oldValue;
/*  53:156 */     double fullProgressDistance = newProgress - this.state.oldProgress;
/*  54:157 */     double originalOldProgress = this.state.oldProgress;
/*  55:166 */     for (int closee = oldIndex; closee < newIndex; closee++)
/*  56:    */     {
/*  57:167 */       double interpolationProgress = (closee + 1) / this.count;
/*  58:    */       
/*  59:169 */       interpolationProgress = Math.min(interpolationProgress, newProgress);
/*  60:    */       
/*  61:171 */       double progressLength = interpolationProgress - originalOldProgress;
/*  62:172 */       double interpolationProportion = progressLength / fullProgressDistance;
/*  63:    */       
/*  64:174 */       double interpolationValueDistance = fullValueDistance * interpolationProportion;
/*  65:    */       
/*  66:    */ 
/*  67:    */ 
/*  68:178 */       int interpolationValue = (int)interpolationValueDistance + originalOldValue;
/*  69:    */       
/*  70:    */ 
/*  71:181 */       extendInternal(interpolationProgress, interpolationValue);
/*  72:    */       
/*  73:183 */       advanceState(interpolationProgress, interpolationValue);
/*  74:    */       
/*  75:185 */       this.values[closee] = ((int)this.state.currentAccumulation);
/*  76:186 */       initializeInterval();
/*  77:    */     }
/*  78:190 */     extendInternal(newProgress, newValue);
/*  79:191 */     advanceState(newProgress, newValue);
/*  80:193 */     if (newIndex == this.count) {
/*  81:194 */       this.state = null;
/*  82:    */     }
/*  83:    */   }
/*  84:    */   
/*  85:    */   protected void advanceState(double newProgress, int newValue)
/*  86:    */   {
/*  87:199 */     this.state.oldValue = newValue;
/*  88:200 */     this.state.oldProgress = newProgress;
/*  89:    */   }
/*  90:    */   
/*  91:    */   int getCount()
/*  92:    */   {
/*  93:204 */     return this.count;
/*  94:    */   }
/*  95:    */   
/*  96:    */   int get(int index)
/*  97:    */   {
/*  98:208 */     return this.values[index];
/*  99:    */   }
/* 100:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.PeriodicStatsAccumulator
 * JD-Core Version:    0.7.0.1
 */