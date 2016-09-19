/*   1:    */ package org.apache.hadoop.mapreduce;
/*   2:    */ 
/*   3:    */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*   4:    */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*   5:    */ import org.apache.hadoop.mapreduce.counters.AbstractCounterGroup;
/*   6:    */ import org.apache.hadoop.mapreduce.counters.AbstractCounters;
/*   7:    */ import org.apache.hadoop.mapreduce.counters.CounterGroupBase;
/*   8:    */ import org.apache.hadoop.mapreduce.counters.CounterGroupFactory;
/*   9:    */ import org.apache.hadoop.mapreduce.counters.CounterGroupFactory.FrameworkGroupFactory;
/*  10:    */ import org.apache.hadoop.mapreduce.counters.FileSystemCounterGroup;
/*  11:    */ import org.apache.hadoop.mapreduce.counters.FileSystemCounterGroup.FSCounter;
/*  12:    */ import org.apache.hadoop.mapreduce.counters.FrameworkCounterGroup;
/*  13:    */ import org.apache.hadoop.mapreduce.counters.FrameworkCounterGroup.FrameworkCounter;
/*  14:    */ import org.apache.hadoop.mapreduce.counters.GenericCounter;
/*  15:    */ import org.apache.hadoop.mapreduce.counters.Limits;
/*  16:    */ 
/*  17:    */ @InterfaceAudience.Public
/*  18:    */ @InterfaceStability.Stable
/*  19:    */ public class Counters
/*  20:    */   extends AbstractCounters<Counter, CounterGroup>
/*  21:    */ {
/*  22:    */   private static class FrameworkGroupImpl<T extends Enum<T>>
/*  23:    */     extends FrameworkCounterGroup<T, Counter>
/*  24:    */     implements CounterGroup
/*  25:    */   {
/*  26:    */     FrameworkGroupImpl(Class<T> cls)
/*  27:    */     {
/*  28: 48 */       super();
/*  29:    */     }
/*  30:    */     
/*  31:    */     protected FrameworkCounterGroup.FrameworkCounter<T> newCounter(T key)
/*  32:    */     {
/*  33: 53 */       return new FrameworkCounterGroup.FrameworkCounter(key, getName());
/*  34:    */     }
/*  35:    */     
/*  36:    */     public CounterGroupBase<Counter> getUnderlyingGroup()
/*  37:    */     {
/*  38: 58 */       return this;
/*  39:    */     }
/*  40:    */   }
/*  41:    */   
/*  42:    */   private static class GenericGroup
/*  43:    */     extends AbstractCounterGroup<Counter>
/*  44:    */     implements CounterGroup
/*  45:    */   {
/*  46:    */     GenericGroup(String name, String displayName, Limits limits)
/*  47:    */     {
/*  48: 68 */       super(displayName, limits);
/*  49:    */     }
/*  50:    */     
/*  51:    */     protected Counter newCounter(String name, String displayName, long value)
/*  52:    */     {
/*  53: 73 */       return new GenericCounter(name, displayName, value);
/*  54:    */     }
/*  55:    */     
/*  56:    */     protected Counter newCounter()
/*  57:    */     {
/*  58: 78 */       return new GenericCounter();
/*  59:    */     }
/*  60:    */     
/*  61:    */     public CounterGroupBase<Counter> getUnderlyingGroup()
/*  62:    */     {
/*  63: 83 */       return this;
/*  64:    */     }
/*  65:    */   }
/*  66:    */   
/*  67:    */   private static class FileSystemGroup
/*  68:    */     extends FileSystemCounterGroup<Counter>
/*  69:    */     implements CounterGroup
/*  70:    */   {
/*  71:    */     protected Counter newCounter(String scheme, FileSystemCounter key)
/*  72:    */     {
/*  73: 93 */       return new FileSystemCounterGroup.FSCounter(scheme, key);
/*  74:    */     }
/*  75:    */     
/*  76:    */     public CounterGroupBase<Counter> getUnderlyingGroup()
/*  77:    */     {
/*  78: 98 */       return this;
/*  79:    */     }
/*  80:    */   }
/*  81:    */   
/*  82:    */   private static class GroupFactory
/*  83:    */     extends CounterGroupFactory<Counter, CounterGroup>
/*  84:    */   {
/*  85:    */     protected <T extends Enum<T>> CounterGroupFactory.FrameworkGroupFactory<CounterGroup> newFrameworkGroupFactory(final Class<T> cls)
/*  86:    */     {
/*  87:114 */       new CounterGroupFactory.FrameworkGroupFactory()
/*  88:    */       {
/*  89:    */         public CounterGroup newGroup(String name)
/*  90:    */         {
/*  91:116 */           return new Counters.FrameworkGroupImpl(cls);
/*  92:    */         }
/*  93:    */       };
/*  94:    */     }
/*  95:    */     
/*  96:    */     protected CounterGroup newGenericGroup(String name, String displayName, Limits limits)
/*  97:    */     {
/*  98:124 */       return new Counters.GenericGroup(name, displayName, limits);
/*  99:    */     }
/* 100:    */     
/* 101:    */     protected CounterGroup newFileSystemGroup()
/* 102:    */     {
/* 103:129 */       return new Counters.FileSystemGroup(null);
/* 104:    */     }
/* 105:    */   }
/* 106:    */   
/* 107:133 */   private static final GroupFactory groupFactory = new GroupFactory(null);
/* 108:    */   
/* 109:    */   public Counters()
/* 110:    */   {
/* 111:139 */     super(groupFactory);
/* 112:    */   }
/* 113:    */   
/* 114:    */   public <C extends Counter, G extends CounterGroupBase<C>> Counters(AbstractCounters<C, G> counters)
/* 115:    */   {
/* 116:150 */     super(counters, groupFactory);
/* 117:    */   }
/* 118:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.Counters
 * JD-Core Version:    0.7.0.1
 */