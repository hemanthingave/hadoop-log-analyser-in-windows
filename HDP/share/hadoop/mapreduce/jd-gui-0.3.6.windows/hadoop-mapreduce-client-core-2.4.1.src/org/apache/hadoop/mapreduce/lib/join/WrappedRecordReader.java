/*   1:    */ package org.apache.hadoop.mapreduce.lib.join;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*   5:    */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*   6:    */ import org.apache.hadoop.conf.Configuration;
/*   7:    */ import org.apache.hadoop.io.NullWritable;
/*   8:    */ import org.apache.hadoop.io.Writable;
/*   9:    */ import org.apache.hadoop.io.WritableComparable;
/*  10:    */ import org.apache.hadoop.io.WritableComparator;
/*  11:    */ import org.apache.hadoop.mapreduce.InputSplit;
/*  12:    */ import org.apache.hadoop.mapreduce.RecordReader;
/*  13:    */ import org.apache.hadoop.mapreduce.TaskAttemptContext;
/*  14:    */ import org.apache.hadoop.util.ReflectionUtils;
/*  15:    */ 
/*  16:    */ @InterfaceAudience.Public
/*  17:    */ @InterfaceStability.Stable
/*  18:    */ public class WrappedRecordReader<K extends WritableComparable<?>, U extends Writable>
/*  19:    */   extends ComposableRecordReader<K, U>
/*  20:    */ {
/*  21: 47 */   protected boolean empty = false;
/*  22:    */   private RecordReader<K, U> rr;
/*  23:    */   private int id;
/*  24: 51 */   protected WritableComparator cmp = null;
/*  25:    */   private K key;
/*  26:    */   private U value;
/*  27:    */   private ResetableIterator<U> vjoin;
/*  28: 55 */   private Configuration conf = new Configuration();
/*  29: 56 */   private Class<? extends WritableComparable> keyclass = null;
/*  30: 58 */   private Class<? extends Writable> valueclass = null;
/*  31:    */   
/*  32:    */   protected WrappedRecordReader(int id)
/*  33:    */   {
/*  34: 61 */     this.id = id;
/*  35: 62 */     this.vjoin = new StreamBackedIterator();
/*  36:    */   }
/*  37:    */   
/*  38:    */   WrappedRecordReader(int id, RecordReader<K, U> rr, Class<? extends WritableComparator> cmpcl)
/*  39:    */     throws IOException, InterruptedException
/*  40:    */   {
/*  41: 71 */     this.id = id;
/*  42: 72 */     this.rr = rr;
/*  43: 73 */     if (cmpcl != null) {
/*  44:    */       try
/*  45:    */       {
/*  46: 75 */         this.cmp = ((WritableComparator)cmpcl.newInstance());
/*  47:    */       }
/*  48:    */       catch (InstantiationException e)
/*  49:    */       {
/*  50: 77 */         throw new IOException(e);
/*  51:    */       }
/*  52:    */       catch (IllegalAccessException e)
/*  53:    */       {
/*  54: 79 */         throw new IOException(e);
/*  55:    */       }
/*  56:    */     }
/*  57: 82 */     this.vjoin = new StreamBackedIterator();
/*  58:    */   }
/*  59:    */   
/*  60:    */   public void initialize(InputSplit split, TaskAttemptContext context)
/*  61:    */     throws IOException, InterruptedException
/*  62:    */   {
/*  63: 88 */     this.rr.initialize(split, context);
/*  64: 89 */     this.conf = context.getConfiguration();
/*  65: 90 */     nextKeyValue();
/*  66: 91 */     if (!this.empty)
/*  67:    */     {
/*  68: 92 */       this.keyclass = this.key.getClass().asSubclass(WritableComparable.class);
/*  69: 93 */       this.valueclass = this.value.getClass();
/*  70: 94 */       if (this.cmp == null) {
/*  71: 95 */         this.cmp = WritableComparator.get(this.keyclass);
/*  72:    */       }
/*  73:    */     }
/*  74:    */   }
/*  75:    */   
/*  76:    */   public K createKey()
/*  77:    */   {
/*  78:105 */     if (this.keyclass != null) {
/*  79:106 */       return (WritableComparable)ReflectionUtils.newInstance(this.keyclass, this.conf);
/*  80:    */     }
/*  81:108 */     return NullWritable.get();
/*  82:    */   }
/*  83:    */   
/*  84:    */   public U createValue()
/*  85:    */   {
/*  86:113 */     if (this.valueclass != null) {
/*  87:114 */       return (Writable)ReflectionUtils.newInstance(this.valueclass, this.conf);
/*  88:    */     }
/*  89:116 */     return NullWritable.get();
/*  90:    */   }
/*  91:    */   
/*  92:    */   public int id()
/*  93:    */   {
/*  94:121 */     return this.id;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public K key()
/*  98:    */   {
/*  99:128 */     return this.key;
/* 100:    */   }
/* 101:    */   
/* 102:    */   public void key(K qkey)
/* 103:    */     throws IOException
/* 104:    */   {
/* 105:135 */     ReflectionUtils.copy(this.conf, this.key, qkey);
/* 106:    */   }
/* 107:    */   
/* 108:    */   public boolean hasNext()
/* 109:    */   {
/* 110:143 */     return !this.empty;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public void skip(K key)
/* 114:    */     throws IOException, InterruptedException
/* 115:    */   {
/* 116:150 */     while ((hasNext()) && 
/* 117:151 */       (this.cmp.compare(key(), key) <= 0) && (next())) {}
/* 118:    */   }
/* 119:    */   
/* 120:    */   public void accept(CompositeRecordReader.JoinCollector i, K key)
/* 121:    */     throws IOException, InterruptedException
/* 122:    */   {
/* 123:164 */     this.vjoin.clear();
/* 124:165 */     if ((key() != null) && (0 == this.cmp.compare(key, key()))) {
/* 125:    */       do
/* 126:    */       {
/* 127:167 */         this.vjoin.add(this.value);
/* 128:168 */       } while ((next()) && (0 == this.cmp.compare(key, key())));
/* 129:    */     }
/* 130:170 */     i.add(this.id, this.vjoin);
/* 131:    */   }
/* 132:    */   
/* 133:    */   public boolean nextKeyValue()
/* 134:    */     throws IOException, InterruptedException
/* 135:    */   {
/* 136:178 */     if (hasNext())
/* 137:    */     {
/* 138:179 */       next();
/* 139:180 */       return true;
/* 140:    */     }
/* 141:182 */     return false;
/* 142:    */   }
/* 143:    */   
/* 144:    */   private boolean next()
/* 145:    */     throws IOException, InterruptedException
/* 146:    */   {
/* 147:190 */     this.empty = (!this.rr.nextKeyValue());
/* 148:191 */     this.key = ((WritableComparable)this.rr.getCurrentKey());
/* 149:192 */     this.value = ((Writable)this.rr.getCurrentValue());
/* 150:193 */     return !this.empty;
/* 151:    */   }
/* 152:    */   
/* 153:    */   public K getCurrentKey()
/* 154:    */     throws IOException, InterruptedException
/* 155:    */   {
/* 156:200 */     return (WritableComparable)this.rr.getCurrentKey();
/* 157:    */   }
/* 158:    */   
/* 159:    */   public U getCurrentValue()
/* 160:    */     throws IOException, InterruptedException
/* 161:    */   {
/* 162:207 */     return (Writable)this.rr.getCurrentValue();
/* 163:    */   }
/* 164:    */   
/* 165:    */   public float getProgress()
/* 166:    */     throws IOException, InterruptedException
/* 167:    */   {
/* 168:214 */     return this.rr.getProgress();
/* 169:    */   }
/* 170:    */   
/* 171:    */   public void close()
/* 172:    */     throws IOException
/* 173:    */   {
/* 174:221 */     this.rr.close();
/* 175:    */   }
/* 176:    */   
/* 177:    */   public int compareTo(ComposableRecordReader<K, ?> other)
/* 178:    */   {
/* 179:229 */     return this.cmp.compare(key(), other.key());
/* 180:    */   }
/* 181:    */   
/* 182:    */   public boolean equals(Object other)
/* 183:    */   {
/* 184:237 */     return ((other instanceof ComposableRecordReader)) && (0 == compareTo((ComposableRecordReader)other));
/* 185:    */   }
/* 186:    */   
/* 187:    */   public int hashCode()
/* 188:    */   {
/* 189:242 */     if (!$assertionsDisabled) {
/* 190:242 */       throw new AssertionError("hashCode not designed");
/* 191:    */     }
/* 192:243 */     return 42;
/* 193:    */   }
/* 194:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.lib.join.WrappedRecordReader
 * JD-Core Version:    0.7.0.1
 */