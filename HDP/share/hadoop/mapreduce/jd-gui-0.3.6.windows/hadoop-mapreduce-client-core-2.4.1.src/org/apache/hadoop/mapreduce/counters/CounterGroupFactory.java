/*   1:    */ package org.apache.hadoop.mapreduce.counters;
/*   2:    */ 
/*   3:    */ import com.google.common.collect.Lists;
/*   4:    */ import com.google.common.collect.Maps;
/*   5:    */ import java.util.List;
/*   6:    */ import java.util.Map;
/*   7:    */ import org.apache.hadoop.classification.InterfaceAudience.Private;
/*   8:    */ import org.apache.hadoop.mapreduce.Counter;
/*   9:    */ import org.apache.hadoop.mapreduce.FileSystemCounter;
/*  10:    */ import org.apache.hadoop.mapreduce.JobCounter;
/*  11:    */ import org.apache.hadoop.mapreduce.TaskCounter;
/*  12:    */ import org.apache.hadoop.mapreduce.util.ResourceBundles;
/*  13:    */ 
/*  14:    */ @InterfaceAudience.Private
/*  15:    */ public abstract class CounterGroupFactory<C extends Counter, G extends CounterGroupBase<C>>
/*  16:    */ {
/*  17: 50 */   private static final Map<String, Integer> s2i = ;
/*  18: 51 */   private static final List<String> i2s = Lists.newArrayList();
/*  19:    */   private static final int VERSION = 1;
/*  20: 53 */   private static final String FS_GROUP_NAME = FileSystemCounter.class.getName();
/*  21:    */   private final Map<String, FrameworkGroupFactory<G>> fmap;
/*  22:    */   
/*  23:    */   public CounterGroupFactory()
/*  24:    */   {
/*  25: 55 */     this.fmap = Maps.newHashMap();
/*  26:    */     
/*  27:    */ 
/*  28: 58 */     addFrameworkGroup(TaskCounter.class);
/*  29: 59 */     addFrameworkGroup(JobCounter.class);
/*  30:    */   }
/*  31:    */   
/*  32:    */   private synchronized <T extends Enum<T>> void addFrameworkGroup(Class<T> cls)
/*  33:    */   {
/*  34: 65 */     updateFrameworkGroupMapping(cls);
/*  35: 66 */     this.fmap.put(cls.getName(), newFrameworkGroupFactory(cls));
/*  36:    */   }
/*  37:    */   
/*  38:    */   private static synchronized void updateFrameworkGroupMapping(Class<?> cls)
/*  39:    */   {
/*  40: 71 */     String name = cls.getName();
/*  41: 72 */     Integer i = (Integer)s2i.get(name);
/*  42: 73 */     if (i != null) {
/*  43: 73 */       return;
/*  44:    */     }
/*  45: 74 */     i2s.add(name);
/*  46: 75 */     s2i.put(name, Integer.valueOf(i2s.size() - 1));
/*  47:    */   }
/*  48:    */   
/*  49:    */   protected abstract <T extends Enum<T>> FrameworkGroupFactory<G> newFrameworkGroupFactory(Class<T> paramClass);
/*  50:    */   
/*  51:    */   public G newGroup(String name, Limits limits)
/*  52:    */   {
/*  53: 94 */     return newGroup(name, ResourceBundles.getCounterGroupName(name, name), limits);
/*  54:    */   }
/*  55:    */   
/*  56:    */   public G newGroup(String name, String displayName, Limits limits)
/*  57:    */   {
/*  58:106 */     FrameworkGroupFactory<G> gf = (FrameworkGroupFactory)this.fmap.get(name);
/*  59:107 */     if (gf != null) {
/*  60:107 */       return (CounterGroupBase)gf.newGroup(name);
/*  61:    */     }
/*  62:108 */     if (name.equals(FS_GROUP_NAME)) {
/*  63:109 */       return newFileSystemGroup();
/*  64:    */     }
/*  65:110 */     if (s2i.get(name) != null) {
/*  66:111 */       return newFrameworkGroup(((Integer)s2i.get(name)).intValue());
/*  67:    */     }
/*  68:113 */     return newGenericGroup(name, displayName, limits);
/*  69:    */   }
/*  70:    */   
/*  71:    */   public G newFrameworkGroup(int id)
/*  72:    */   {
/*  73:    */     String name;
/*  74:123 */     synchronized (CounterGroupFactory.class)
/*  75:    */     {
/*  76:124 */       if ((id < 0) || (id >= i2s.size())) {
/*  77:124 */         throwBadFrameGroupIdException(id);
/*  78:    */       }
/*  79:125 */       name = (String)i2s.get(id);
/*  80:    */     }
/*  81:127 */     FrameworkGroupFactory<G> gf = (FrameworkGroupFactory)this.fmap.get(name);
/*  82:128 */     if (gf == null) {
/*  83:128 */       throwBadFrameGroupIdException(id);
/*  84:    */     }
/*  85:129 */     return (CounterGroupBase)gf.newGroup(name);
/*  86:    */   }
/*  87:    */   
/*  88:    */   public static synchronized int getFrameworkGroupId(String name)
/*  89:    */   {
/*  90:138 */     Integer i = (Integer)s2i.get(name);
/*  91:139 */     if (i == null) {
/*  92:139 */       throwBadFrameworkGroupNameException(name);
/*  93:    */     }
/*  94:140 */     return i.intValue();
/*  95:    */   }
/*  96:    */   
/*  97:    */   public int version()
/*  98:    */   {
/*  99:147 */     return 1;
/* 100:    */   }
/* 101:    */   
/* 102:    */   public static synchronized boolean isFrameworkGroup(String name)
/* 103:    */   {
/* 104:158 */     return (s2i.get(name) != null) || (name.equals(FS_GROUP_NAME));
/* 105:    */   }
/* 106:    */   
/* 107:    */   private static void throwBadFrameGroupIdException(int id)
/* 108:    */   {
/* 109:162 */     throw new IllegalArgumentException("bad framework group id: " + id);
/* 110:    */   }
/* 111:    */   
/* 112:    */   private static void throwBadFrameworkGroupNameException(String name)
/* 113:    */   {
/* 114:166 */     throw new IllegalArgumentException("bad framework group name: " + name);
/* 115:    */   }
/* 116:    */   
/* 117:    */   protected abstract G newGenericGroup(String paramString1, String paramString2, Limits paramLimits);
/* 118:    */   
/* 119:    */   protected abstract G newFileSystemGroup();
/* 120:    */   
/* 121:    */   public static abstract interface FrameworkGroupFactory<F>
/* 122:    */   {
/* 123:    */     public abstract F newGroup(String paramString);
/* 124:    */   }
/* 125:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.counters.CounterGroupFactory
 * JD-Core Version:    0.7.0.1
 */